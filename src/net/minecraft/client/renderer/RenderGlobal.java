/*      */ package net.minecraft.client.renderer;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.audio.PositionedSoundRecord;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
/*      */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*      */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.chunk.VboChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.VisGraph;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemRecord;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Matrix4f;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.util.Vector3d;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.DynamicLights;
/*      */ import net.optifine.Lagometer;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.ChunkVisibility;
/*      */ import net.optifine.render.CloudRenderer;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ import net.optifine.shaders.gui.GuiShaderOptions;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.vector.Matrix4f;
/*      */ import org.lwjgl.util.vector.Vector3f;
/*      */ import org.lwjgl.util.vector.Vector4f;
/*      */ 
/*      */ public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {
/*   81 */   private static final Logger logger = LogManager.getLogger();
/*   82 */   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
/*   83 */   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
/*   84 */   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
/*   85 */   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
/*   86 */   private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
/*      */   
/*      */   public final Minecraft mc;
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */   
/*      */   private final RenderManager renderManager;
/*      */   
/*      */   private WorldClient theWorld;
/*   95 */   private Set<RenderChunk> chunksToUpdate = Sets.newLinkedHashSet();
/*   96 */   private List<ContainerLocalRenderInformation> renderInfos = Lists.newArrayListWithCapacity(69696);
/*   97 */   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
/*      */   
/*      */   private ViewFrustum viewFrustum;
/*      */   
/*  101 */   private int starGLCallList = -1;
/*      */ 
/*      */   
/*  104 */   private int glSkyList = -1;
/*      */ 
/*      */   
/*  107 */   private int glSkyList2 = -1;
/*      */   
/*      */   private VertexFormat vertexBufferFormat;
/*      */   
/*      */   private VertexBuffer starVBO;
/*      */   
/*      */   private VertexBuffer skyVBO;
/*      */   
/*      */   private VertexBuffer sky2VBO;
/*      */   private int cloudTickCounter;
/*  117 */   public final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.newHashMap();
/*  118 */   private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
/*  119 */   private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
/*      */   
/*      */   private Framebuffer entityOutlineFramebuffer;
/*      */   
/*      */   private ShaderGroup entityOutlineShader;
/*  124 */   private double frustumUpdatePosX = Double.MIN_VALUE;
/*  125 */   private double frustumUpdatePosY = Double.MIN_VALUE;
/*  126 */   private double frustumUpdatePosZ = Double.MIN_VALUE;
/*  127 */   private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  128 */   private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  129 */   private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  130 */   private double lastViewEntityX = Double.MIN_VALUE;
/*  131 */   private double lastViewEntityY = Double.MIN_VALUE;
/*  132 */   private double lastViewEntityZ = Double.MIN_VALUE;
/*  133 */   private double lastViewEntityPitch = Double.MIN_VALUE;
/*  134 */   private double lastViewEntityYaw = Double.MIN_VALUE;
/*  135 */   private final ChunkRenderDispatcher renderDispatcher = new ChunkRenderDispatcher();
/*      */   private ChunkRenderContainer renderContainer;
/*  137 */   private int renderDistanceChunks = -1;
/*      */ 
/*      */   
/*  140 */   private int renderEntitiesStartupCounter = 2;
/*      */   
/*      */   private int countEntitiesTotal;
/*      */   
/*      */   private int countEntitiesRendered;
/*      */   
/*      */   private int countEntitiesHidden;
/*      */   
/*      */   private boolean debugFixTerrainFrustum = false;
/*      */   
/*      */   private ClippingHelper debugFixedClippingHelper;
/*      */   
/*  152 */   private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
/*  153 */   private final Vector3d debugTerrainFrustumPosition = new Vector3d();
/*      */   private boolean vboEnabled = false;
/*      */   IRenderChunkFactory renderChunkFactory;
/*      */   private double prevRenderSortX;
/*      */   private double prevRenderSortY;
/*      */   private double prevRenderSortZ;
/*      */   public boolean displayListEntitiesDirty = true;
/*      */   private CloudRenderer cloudRenderer;
/*      */   public Entity renderedEntity;
/*  162 */   public Set chunksToResortTransparency = new LinkedHashSet();
/*  163 */   public Set chunksToUpdateForced = new LinkedHashSet();
/*  164 */   private Deque visibilityDeque = new ArrayDeque();
/*  165 */   private List renderInfosEntities = new ArrayList(1024);
/*  166 */   private List renderInfosTileEntities = new ArrayList(1024);
/*  167 */   private List renderInfosNormal = new ArrayList(1024);
/*  168 */   private List renderInfosEntitiesNormal = new ArrayList(1024);
/*  169 */   private List renderInfosTileEntitiesNormal = new ArrayList(1024);
/*  170 */   private List renderInfosShadow = new ArrayList(1024);
/*  171 */   private List renderInfosEntitiesShadow = new ArrayList(1024);
/*  172 */   private List renderInfosTileEntitiesShadow = new ArrayList(1024);
/*  173 */   private int renderDistance = 0;
/*  174 */   private int renderDistanceSq = 0;
/*  175 */   private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList((Object[])EnumFacing.VALUES)));
/*      */   private int countTileEntitiesRendered;
/*  177 */   private IChunkProvider worldChunkProvider = null;
/*  178 */   private LongHashMap worldChunkProviderMap = null;
/*  179 */   private int countLoadedChunksPrev = 0;
/*  180 */   private RenderEnv renderEnv = new RenderEnv(Blocks.air.getDefaultState(), new BlockPos(0, 0, 0));
/*      */   public boolean renderOverlayDamaged = false;
/*      */   public boolean renderOverlayEyes = false;
/*      */   private boolean firstWorldLoad = false;
/*  184 */   private static int renderEntitiesCounter = 0;
/*      */ 
/*      */   
/*      */   public RenderGlobal(Minecraft mcIn) {
/*  188 */     this.cloudRenderer = new CloudRenderer(mcIn);
/*  189 */     this.mc = mcIn;
/*  190 */     this.renderManager = mcIn.getRenderManager();
/*  191 */     this.renderEngine = mcIn.getTextureManager();
/*  192 */     this.renderEngine.bindTexture(locationForcefieldPng);
/*  193 */     GL11.glTexParameteri(3553, 10242, 10497);
/*  194 */     GL11.glTexParameteri(3553, 10243, 10497);
/*  195 */     GlStateManager.bindTexture(0);
/*  196 */     updateDestroyBlockIcons();
/*  197 */     this.vboEnabled = OpenGlHelper.useVbo();
/*      */     
/*  199 */     if (this.vboEnabled) {
/*      */       
/*  201 */       this.renderContainer = new VboRenderList();
/*  202 */       this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */     }
/*      */     else {
/*      */       
/*  206 */       this.renderContainer = new RenderList();
/*  207 */       this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */     } 
/*      */     
/*  210 */     this.vertexBufferFormat = new VertexFormat();
/*  211 */     this.vertexBufferFormat.addElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/*  212 */     generateStars();
/*  213 */     generateSky();
/*  214 */     generateSky2();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  219 */     updateDestroyBlockIcons();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDestroyBlockIcons() {
/*  224 */     TextureMap texturemap = this.mc.getTextureMapBlocks();
/*      */     
/*  226 */     for (int i = 0; i < this.destroyBlockIcons.length; i++)
/*      */     {
/*  228 */       this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeEntityOutlineShader() {
/*  237 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  239 */       if (ShaderLinkHelper.getStaticShaderLinkHelper() == null)
/*      */       {
/*  241 */         ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */       }
/*      */       
/*  244 */       ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");
/*      */ 
/*      */       
/*      */       try {
/*  248 */         this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocation);
/*  249 */         this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  250 */         this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
/*      */       }
/*  252 */       catch (IOException ioexception) {
/*      */         
/*  254 */         logger.warn("Failed to load shader: " + resourcelocation, ioexception);
/*  255 */         this.entityOutlineShader = null;
/*  256 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*  258 */       catch (JsonSyntaxException jsonsyntaxexception) {
/*      */         
/*  260 */         logger.warn("Failed to load shader: " + resourcelocation, (Throwable)jsonsyntaxexception);
/*  261 */         this.entityOutlineShader = null;
/*  262 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  267 */       this.entityOutlineShader = null;
/*  268 */       this.entityOutlineFramebuffer = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderEntityOutlineFramebuffer() {
/*  274 */     if (isRenderEntityOutlines()) {
/*      */       
/*  276 */       GlStateManager.enableBlend();
/*  277 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  278 */       this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
/*  279 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isRenderEntityOutlines() {
/*  285 */     return (!Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing()) ? ((this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.thePlayer != null && this.mc.thePlayer.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown())) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateSky2() {
/*  290 */     Tessellator tessellator = Tessellator.getInstance();
/*  291 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  293 */     if (this.sky2VBO != null)
/*      */     {
/*  295 */       this.sky2VBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  298 */     if (this.glSkyList2 >= 0) {
/*      */       
/*  300 */       GLAllocation.deleteDisplayLists(this.glSkyList2);
/*  301 */       this.glSkyList2 = -1;
/*      */     } 
/*      */     
/*  304 */     if (this.vboEnabled) {
/*      */       
/*  306 */       this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
/*  307 */       renderSky(worldrenderer, -16.0F, true);
/*  308 */       worldrenderer.finishDrawing();
/*  309 */       worldrenderer.reset();
/*  310 */       this.sky2VBO.bufferData(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  314 */       this.glSkyList2 = GLAllocation.generateDisplayLists(1);
/*  315 */       GL11.glNewList(this.glSkyList2, 4864);
/*  316 */       renderSky(worldrenderer, -16.0F, true);
/*  317 */       tessellator.draw();
/*  318 */       GL11.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateSky() {
/*  324 */     Tessellator tessellator = Tessellator.getInstance();
/*  325 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  327 */     if (this.skyVBO != null)
/*      */     {
/*  329 */       this.skyVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  332 */     if (this.glSkyList >= 0) {
/*      */       
/*  334 */       GLAllocation.deleteDisplayLists(this.glSkyList);
/*  335 */       this.glSkyList = -1;
/*      */     } 
/*      */     
/*  338 */     if (this.vboEnabled) {
/*      */       
/*  340 */       this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
/*  341 */       renderSky(worldrenderer, 16.0F, false);
/*  342 */       worldrenderer.finishDrawing();
/*  343 */       worldrenderer.reset();
/*  344 */       this.skyVBO.bufferData(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  348 */       this.glSkyList = GLAllocation.generateDisplayLists(1);
/*  349 */       GL11.glNewList(this.glSkyList, 4864);
/*  350 */       renderSky(worldrenderer, 16.0F, false);
/*  351 */       tessellator.draw();
/*  352 */       GL11.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSky(WorldRenderer worldRendererIn, float posY, boolean reverseX) {
/*  358 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
/*  359 */     int k = (this.renderDistance / 64 + 1) * 64 + 64;
/*      */     
/*  361 */     for (int l = -k; l <= k; l += 64) {
/*      */       
/*  363 */       for (int i1 = -k; i1 <= k; i1 += 64) {
/*      */         
/*  365 */         float f = l;
/*  366 */         float f1 = (l + 64);
/*      */         
/*  368 */         if (reverseX) {
/*      */           
/*  370 */           f1 = l;
/*  371 */           f = (l + 64);
/*      */         } 
/*      */         
/*  374 */         worldRendererIn.pos(f, posY, i1).endVertex();
/*  375 */         worldRendererIn.pos(f1, posY, i1).endVertex();
/*  376 */         worldRendererIn.pos(f1, posY, (i1 + 64)).endVertex();
/*  377 */         worldRendererIn.pos(f, posY, (i1 + 64)).endVertex();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateStars() {
/*  384 */     Tessellator tessellator = Tessellator.getInstance();
/*  385 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  387 */     if (this.starVBO != null)
/*      */     {
/*  389 */       this.starVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  392 */     if (this.starGLCallList >= 0) {
/*      */       
/*  394 */       GLAllocation.deleteDisplayLists(this.starGLCallList);
/*  395 */       this.starGLCallList = -1;
/*      */     } 
/*      */     
/*  398 */     if (this.vboEnabled) {
/*      */       
/*  400 */       this.starVBO = new VertexBuffer(this.vertexBufferFormat);
/*  401 */       renderStars(worldrenderer);
/*  402 */       worldrenderer.finishDrawing();
/*  403 */       worldrenderer.reset();
/*  404 */       this.starVBO.bufferData(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  408 */       this.starGLCallList = GLAllocation.generateDisplayLists(1);
/*  409 */       GlStateManager.pushMatrix();
/*  410 */       GL11.glNewList(this.starGLCallList, 4864);
/*  411 */       renderStars(worldrenderer);
/*  412 */       tessellator.draw();
/*  413 */       GL11.glEndList();
/*  414 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderStars(WorldRenderer worldRendererIn) {
/*  420 */     Random random = new Random(10842L);
/*  421 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
/*      */     
/*  423 */     for (int i = 0; i < 1500; i++) {
/*      */       
/*  425 */       double d0 = (random.nextFloat() * 2.0F - 1.0F);
/*  426 */       double d1 = (random.nextFloat() * 2.0F - 1.0F);
/*  427 */       double d2 = (random.nextFloat() * 2.0F - 1.0F);
/*  428 */       double d3 = (0.15F + random.nextFloat() * 0.1F);
/*  429 */       double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */       
/*  431 */       if (d4 < 1.0D && d4 > 0.01D) {
/*      */         
/*  433 */         d4 = 1.0D / Math.sqrt(d4);
/*  434 */         d0 *= d4;
/*  435 */         d1 *= d4;
/*  436 */         d2 *= d4;
/*  437 */         double d5 = d0 * 100.0D;
/*  438 */         double d6 = d1 * 100.0D;
/*  439 */         double d7 = d2 * 100.0D;
/*  440 */         double d8 = Math.atan2(d0, d2);
/*  441 */         double d9 = Math.sin(d8);
/*  442 */         double d10 = Math.cos(d8);
/*  443 */         double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
/*  444 */         double d12 = Math.sin(d11);
/*  445 */         double d13 = Math.cos(d11);
/*  446 */         double d14 = random.nextDouble() * Math.PI * 2.0D;
/*  447 */         double d15 = Math.sin(d14);
/*  448 */         double d16 = Math.cos(d14);
/*      */         
/*  450 */         for (int j = 0; j < 4; j++) {
/*      */           
/*  452 */           double d17 = 0.0D;
/*  453 */           double d18 = ((j & 0x2) - 1) * d3;
/*  454 */           double d19 = ((j + 1 & 0x2) - 1) * d3;
/*  455 */           double d20 = 0.0D;
/*  456 */           double d21 = d18 * d16 - d19 * d15;
/*  457 */           double d22 = d19 * d16 + d18 * d15;
/*  458 */           double d23 = d21 * d12 + 0.0D * d13;
/*  459 */           double d24 = 0.0D * d12 - d21 * d13;
/*  460 */           double d25 = d24 * d9 - d22 * d10;
/*  461 */           double d26 = d22 * d9 + d24 * d10;
/*  462 */           worldRendererIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldAndLoadRenderers(WorldClient worldClientIn) {
/*  473 */     if (this.theWorld != null)
/*      */     {
/*  475 */       this.theWorld.removeWorldAccess(this);
/*      */     }
/*      */     
/*  478 */     this.frustumUpdatePosX = Double.MIN_VALUE;
/*  479 */     this.frustumUpdatePosY = Double.MIN_VALUE;
/*  480 */     this.frustumUpdatePosZ = Double.MIN_VALUE;
/*  481 */     this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  482 */     this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  483 */     this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  484 */     this.renderManager.set((World)worldClientIn);
/*  485 */     this.theWorld = worldClientIn;
/*      */     
/*  487 */     if (Config.isDynamicLights())
/*      */     {
/*  489 */       DynamicLights.clear();
/*      */     }
/*      */     
/*  492 */     ChunkVisibility.reset();
/*  493 */     this.worldChunkProvider = null;
/*  494 */     this.worldChunkProviderMap = null;
/*  495 */     this.renderEnv.reset((IBlockState)null, (BlockPos)null);
/*  496 */     Shaders.checkWorldChanged((World)this.theWorld);
/*      */     
/*  498 */     if (worldClientIn != null) {
/*      */       
/*  500 */       worldClientIn.addWorldAccess(this);
/*  501 */       loadRenderers();
/*      */     }
/*      */     else {
/*      */       
/*  505 */       this.chunksToUpdate.clear();
/*  506 */       clearRenderInfos();
/*      */       
/*  508 */       if (this.viewFrustum != null)
/*      */       {
/*  510 */         this.viewFrustum.deleteGlResources();
/*      */       }
/*      */       
/*  513 */       this.viewFrustum = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadRenderers() {
/*  522 */     if (this.theWorld != null) {
/*      */       
/*  524 */       this.displayListEntitiesDirty = true;
/*  525 */       Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
/*  526 */       Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
/*  527 */       BlockModelRenderer.updateAoLightValue();
/*      */       
/*  529 */       if (Config.isDynamicLights())
/*      */       {
/*  531 */         DynamicLights.clear();
/*      */       }
/*      */       
/*  534 */       SmartAnimations.update();
/*  535 */       this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
/*  536 */       this.renderDistance = this.renderDistanceChunks * 16;
/*  537 */       this.renderDistanceSq = this.renderDistance * this.renderDistance;
/*  538 */       boolean flag = this.vboEnabled;
/*  539 */       this.vboEnabled = OpenGlHelper.useVbo();
/*      */       
/*  541 */       if (flag && !this.vboEnabled) {
/*      */         
/*  543 */         this.renderContainer = new RenderList();
/*  544 */         this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */       }
/*  546 */       else if (!flag && this.vboEnabled) {
/*      */         
/*  548 */         this.renderContainer = new VboRenderList();
/*  549 */         this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */       } 
/*      */       
/*  552 */       generateStars();
/*  553 */       generateSky();
/*  554 */       generateSky2();
/*      */       
/*  556 */       if (this.viewFrustum != null)
/*      */       {
/*  558 */         this.viewFrustum.deleteGlResources();
/*      */       }
/*      */       
/*  561 */       stopChunkUpdates();
/*      */       
/*  563 */       synchronized (this.setTileEntities) {
/*      */         
/*  565 */         this.setTileEntities.clear();
/*      */       } 
/*      */       
/*  568 */       this.viewFrustum = new ViewFrustum((World)this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
/*      */       
/*  570 */       if (this.theWorld != null) {
/*      */         
/*  572 */         Entity entity = this.mc.getRenderViewEntity();
/*      */         
/*  574 */         if (entity != null)
/*      */         {
/*  576 */           this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
/*      */         }
/*      */       } 
/*      */       
/*  580 */       this.renderEntitiesStartupCounter = 2;
/*      */     } 
/*      */     
/*  583 */     if (this.mc.thePlayer == null)
/*      */     {
/*  585 */       this.firstWorldLoad = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void stopChunkUpdates() {
/*  591 */     this.chunksToUpdate.clear();
/*  592 */     this.renderDispatcher.stopChunkUpdates();
/*      */   }
/*      */ 
/*      */   
/*      */   public void createBindEntityOutlineFbs(int width, int height) {
/*  597 */     if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null)
/*      */     {
/*  599 */       this.entityOutlineShader.createBindFramebuffers(width, height);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore #4
/*      */     //   3: getstatic net/optifine/reflect/Reflector.MinecraftForgeClient_getRenderPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   6: invokevirtual exists : ()Z
/*      */     //   9: ifeq -> 24
/*      */     //   12: getstatic net/optifine/reflect/Reflector.MinecraftForgeClient_getRenderPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   15: iconst_0
/*      */     //   16: anewarray java/lang/Object
/*      */     //   19: invokestatic callInt : (Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)I
/*      */     //   22: istore #4
/*      */     //   24: aload_0
/*      */     //   25: getfield renderEntitiesStartupCounter : I
/*      */     //   28: ifle -> 50
/*      */     //   31: iload #4
/*      */     //   33: ifle -> 37
/*      */     //   36: return
/*      */     //   37: aload_0
/*      */     //   38: dup
/*      */     //   39: getfield renderEntitiesStartupCounter : I
/*      */     //   42: iconst_1
/*      */     //   43: isub
/*      */     //   44: putfield renderEntitiesStartupCounter : I
/*      */     //   47: goto -> 2068
/*      */     //   50: aload_1
/*      */     //   51: getfield prevPosX : D
/*      */     //   54: aload_1
/*      */     //   55: getfield posX : D
/*      */     //   58: aload_1
/*      */     //   59: getfield prevPosX : D
/*      */     //   62: dsub
/*      */     //   63: fload_3
/*      */     //   64: f2d
/*      */     //   65: dmul
/*      */     //   66: dadd
/*      */     //   67: dstore #5
/*      */     //   69: aload_1
/*      */     //   70: getfield prevPosY : D
/*      */     //   73: aload_1
/*      */     //   74: getfield posY : D
/*      */     //   77: aload_1
/*      */     //   78: getfield prevPosY : D
/*      */     //   81: dsub
/*      */     //   82: fload_3
/*      */     //   83: f2d
/*      */     //   84: dmul
/*      */     //   85: dadd
/*      */     //   86: dstore #7
/*      */     //   88: aload_1
/*      */     //   89: getfield prevPosZ : D
/*      */     //   92: aload_1
/*      */     //   93: getfield posZ : D
/*      */     //   96: aload_1
/*      */     //   97: getfield prevPosZ : D
/*      */     //   100: dsub
/*      */     //   101: fload_3
/*      */     //   102: f2d
/*      */     //   103: dmul
/*      */     //   104: dadd
/*      */     //   105: dstore #9
/*      */     //   107: aload_0
/*      */     //   108: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   111: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   114: ldc 'prepare'
/*      */     //   116: invokevirtual startSection : (Ljava/lang/String;)V
/*      */     //   119: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   122: aload_0
/*      */     //   123: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   126: aload_0
/*      */     //   127: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   130: invokevirtual getTextureManager : ()Lnet/minecraft/client/renderer/texture/TextureManager;
/*      */     //   133: aload_0
/*      */     //   134: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   137: getfield fontRendererObj : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   140: aload_0
/*      */     //   141: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   144: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   147: fload_3
/*      */     //   148: invokevirtual cacheActiveRenderInfo : (Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;F)V
/*      */     //   151: aload_0
/*      */     //   152: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   155: aload_0
/*      */     //   156: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   159: aload_0
/*      */     //   160: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   163: getfield fontRendererObj : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   166: aload_0
/*      */     //   167: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   170: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   173: aload_0
/*      */     //   174: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   177: getfield pointedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   180: aload_0
/*      */     //   181: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   184: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   187: fload_3
/*      */     //   188: invokevirtual cacheActiveRenderInfo : (Lnet/minecraft/world/World;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V
/*      */     //   191: getstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   194: iconst_1
/*      */     //   195: iadd
/*      */     //   196: putstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   199: iload #4
/*      */     //   201: ifne -> 224
/*      */     //   204: aload_0
/*      */     //   205: iconst_0
/*      */     //   206: putfield countEntitiesTotal : I
/*      */     //   209: aload_0
/*      */     //   210: iconst_0
/*      */     //   211: putfield countEntitiesRendered : I
/*      */     //   214: aload_0
/*      */     //   215: iconst_0
/*      */     //   216: putfield countEntitiesHidden : I
/*      */     //   219: aload_0
/*      */     //   220: iconst_0
/*      */     //   221: putfield countTileEntitiesRendered : I
/*      */     //   224: aload_0
/*      */     //   225: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   228: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   231: astore #11
/*      */     //   233: aload #11
/*      */     //   235: getfield lastTickPosX : D
/*      */     //   238: aload #11
/*      */     //   240: getfield posX : D
/*      */     //   243: aload #11
/*      */     //   245: getfield lastTickPosX : D
/*      */     //   248: dsub
/*      */     //   249: fload_3
/*      */     //   250: f2d
/*      */     //   251: dmul
/*      */     //   252: dadd
/*      */     //   253: dstore #12
/*      */     //   255: aload #11
/*      */     //   257: getfield lastTickPosY : D
/*      */     //   260: aload #11
/*      */     //   262: getfield posY : D
/*      */     //   265: aload #11
/*      */     //   267: getfield lastTickPosY : D
/*      */     //   270: dsub
/*      */     //   271: fload_3
/*      */     //   272: f2d
/*      */     //   273: dmul
/*      */     //   274: dadd
/*      */     //   275: dstore #14
/*      */     //   277: aload #11
/*      */     //   279: getfield lastTickPosZ : D
/*      */     //   282: aload #11
/*      */     //   284: getfield posZ : D
/*      */     //   287: aload #11
/*      */     //   289: getfield lastTickPosZ : D
/*      */     //   292: dsub
/*      */     //   293: fload_3
/*      */     //   294: f2d
/*      */     //   295: dmul
/*      */     //   296: dadd
/*      */     //   297: dstore #16
/*      */     //   299: dload #12
/*      */     //   301: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerX : D
/*      */     //   304: dload #14
/*      */     //   306: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerY : D
/*      */     //   309: dload #16
/*      */     //   311: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerZ : D
/*      */     //   314: aload_0
/*      */     //   315: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   318: dload #12
/*      */     //   320: dload #14
/*      */     //   322: dload #16
/*      */     //   324: invokevirtual setRenderPosition : (DDD)V
/*      */     //   327: aload_0
/*      */     //   328: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   331: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   334: invokevirtual enableLightmap : ()V
/*      */     //   337: aload_0
/*      */     //   338: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   341: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   344: ldc_w 'global'
/*      */     //   347: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   350: aload_0
/*      */     //   351: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   354: invokevirtual getLoadedEntityList : ()Ljava/util/List;
/*      */     //   357: astore #18
/*      */     //   359: iload #4
/*      */     //   361: ifne -> 375
/*      */     //   364: aload_0
/*      */     //   365: aload #18
/*      */     //   367: invokeinterface size : ()I
/*      */     //   372: putfield countEntitiesTotal : I
/*      */     //   375: invokestatic isFogOff : ()Z
/*      */     //   378: ifeq -> 397
/*      */     //   381: aload_0
/*      */     //   382: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   385: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   388: getfield fogStandard : Z
/*      */     //   391: ifeq -> 397
/*      */     //   394: invokestatic disableFog : ()V
/*      */     //   397: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   400: invokevirtual exists : ()Z
/*      */     //   403: istore #19
/*      */     //   405: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   408: invokevirtual exists : ()Z
/*      */     //   411: istore #20
/*      */     //   413: iconst_0
/*      */     //   414: istore #21
/*      */     //   416: iload #21
/*      */     //   418: aload_0
/*      */     //   419: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   422: getfield weatherEffects : Ljava/util/List;
/*      */     //   425: invokeinterface size : ()I
/*      */     //   430: if_icmpge -> 521
/*      */     //   433: aload_0
/*      */     //   434: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   437: getfield weatherEffects : Ljava/util/List;
/*      */     //   440: iload #21
/*      */     //   442: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   447: checkcast net/minecraft/entity/Entity
/*      */     //   450: astore #22
/*      */     //   452: iload #19
/*      */     //   454: ifeq -> 480
/*      */     //   457: aload #22
/*      */     //   459: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   462: iconst_1
/*      */     //   463: anewarray java/lang/Object
/*      */     //   466: dup
/*      */     //   467: iconst_0
/*      */     //   468: iload #4
/*      */     //   470: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   473: aastore
/*      */     //   474: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   477: ifeq -> 515
/*      */     //   480: aload_0
/*      */     //   481: dup
/*      */     //   482: getfield countEntitiesRendered : I
/*      */     //   485: iconst_1
/*      */     //   486: iadd
/*      */     //   487: putfield countEntitiesRendered : I
/*      */     //   490: aload #22
/*      */     //   492: dload #5
/*      */     //   494: dload #7
/*      */     //   496: dload #9
/*      */     //   498: invokevirtual isInRangeToRender3d : (DDD)Z
/*      */     //   501: ifeq -> 515
/*      */     //   504: aload_0
/*      */     //   505: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   508: aload #22
/*      */     //   510: fload_3
/*      */     //   511: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   514: pop
/*      */     //   515: iinc #21, 1
/*      */     //   518: goto -> 416
/*      */     //   521: aload_0
/*      */     //   522: invokevirtual isRenderEntityOutlines : ()Z
/*      */     //   525: ifeq -> 821
/*      */     //   528: sipush #519
/*      */     //   531: invokestatic depthFunc : (I)V
/*      */     //   534: invokestatic disableFog : ()V
/*      */     //   537: aload_0
/*      */     //   538: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   541: invokevirtual framebufferClear : ()V
/*      */     //   544: aload_0
/*      */     //   545: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   548: iconst_0
/*      */     //   549: invokevirtual bindFramebuffer : (Z)V
/*      */     //   552: aload_0
/*      */     //   553: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   556: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   559: ldc_w 'entityOutlines'
/*      */     //   562: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   565: invokestatic disableStandardItemLighting : ()V
/*      */     //   568: aload_0
/*      */     //   569: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   572: iconst_1
/*      */     //   573: invokevirtual setRenderOutlines : (Z)V
/*      */     //   576: iconst_0
/*      */     //   577: istore #21
/*      */     //   579: iload #21
/*      */     //   581: aload #18
/*      */     //   583: invokeinterface size : ()I
/*      */     //   588: if_icmpge -> 759
/*      */     //   591: aload #18
/*      */     //   593: iload #21
/*      */     //   595: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   600: checkcast net/minecraft/entity/Entity
/*      */     //   603: astore #22
/*      */     //   605: aload_0
/*      */     //   606: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   609: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   612: instanceof net/minecraft/entity/EntityLivingBase
/*      */     //   615: ifeq -> 638
/*      */     //   618: aload_0
/*      */     //   619: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   622: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   625: checkcast net/minecraft/entity/EntityLivingBase
/*      */     //   628: invokevirtual isPlayerSleeping : ()Z
/*      */     //   631: ifeq -> 638
/*      */     //   634: iconst_1
/*      */     //   635: goto -> 639
/*      */     //   638: iconst_0
/*      */     //   639: istore #23
/*      */     //   641: aload #22
/*      */     //   643: dload #5
/*      */     //   645: dload #7
/*      */     //   647: dload #9
/*      */     //   649: invokevirtual isInRangeToRender3d : (DDD)Z
/*      */     //   652: ifeq -> 704
/*      */     //   655: aload #22
/*      */     //   657: getfield ignoreFrustumCheck : Z
/*      */     //   660: ifne -> 692
/*      */     //   663: aload_2
/*      */     //   664: aload #22
/*      */     //   666: invokevirtual getEntityBoundingBox : ()Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   669: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   674: ifne -> 692
/*      */     //   677: aload #22
/*      */     //   679: getfield riddenByEntity : Lnet/minecraft/entity/Entity;
/*      */     //   682: aload_0
/*      */     //   683: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   686: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   689: if_acmpne -> 704
/*      */     //   692: aload #22
/*      */     //   694: instanceof net/minecraft/entity/player/EntityPlayer
/*      */     //   697: ifeq -> 704
/*      */     //   700: iconst_1
/*      */     //   701: goto -> 705
/*      */     //   704: iconst_0
/*      */     //   705: istore #24
/*      */     //   707: aload #22
/*      */     //   709: aload_0
/*      */     //   710: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   713: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   716: if_acmpne -> 737
/*      */     //   719: aload_0
/*      */     //   720: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   723: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   726: getfield thirdPersonView : I
/*      */     //   729: ifne -> 737
/*      */     //   732: iload #23
/*      */     //   734: ifeq -> 753
/*      */     //   737: iload #24
/*      */     //   739: ifeq -> 753
/*      */     //   742: aload_0
/*      */     //   743: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   746: aload #22
/*      */     //   748: fload_3
/*      */     //   749: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   752: pop
/*      */     //   753: iinc #21, 1
/*      */     //   756: goto -> 579
/*      */     //   759: aload_0
/*      */     //   760: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   763: iconst_0
/*      */     //   764: invokevirtual setRenderOutlines : (Z)V
/*      */     //   767: invokestatic enableStandardItemLighting : ()V
/*      */     //   770: iconst_0
/*      */     //   771: invokestatic depthMask : (Z)V
/*      */     //   774: aload_0
/*      */     //   775: getfield entityOutlineShader : Lnet/minecraft/client/shader/ShaderGroup;
/*      */     //   778: fload_3
/*      */     //   779: invokevirtual loadShaderGroup : (F)V
/*      */     //   782: invokestatic enableLighting : ()V
/*      */     //   785: iconst_1
/*      */     //   786: invokestatic depthMask : (Z)V
/*      */     //   789: aload_0
/*      */     //   790: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   793: invokevirtual getFramebuffer : ()Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   796: iconst_0
/*      */     //   797: invokevirtual bindFramebuffer : (Z)V
/*      */     //   800: invokestatic enableFog : ()V
/*      */     //   803: invokestatic enableBlend : ()V
/*      */     //   806: invokestatic enableColorMaterial : ()V
/*      */     //   809: sipush #515
/*      */     //   812: invokestatic depthFunc : (I)V
/*      */     //   815: invokestatic enableDepth : ()V
/*      */     //   818: invokestatic enableAlpha : ()V
/*      */     //   821: aload_0
/*      */     //   822: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   825: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   828: ldc_w 'entities'
/*      */     //   831: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   834: invokestatic isShaders : ()Z
/*      */     //   837: istore #21
/*      */     //   839: iload #21
/*      */     //   841: ifeq -> 847
/*      */     //   844: invokestatic beginEntities : ()V
/*      */     //   847: invokestatic updateItemRenderDistance : ()V
/*      */     //   850: aload_0
/*      */     //   851: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   854: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   857: getfield fancyGraphics : Z
/*      */     //   860: istore #22
/*      */     //   862: aload_0
/*      */     //   863: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   866: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   869: invokestatic isDroppedItemsFancy : ()Z
/*      */     //   872: putfield fancyGraphics : Z
/*      */     //   875: getstatic net/optifine/shaders/Shaders.isShadowPass : Z
/*      */     //   878: ifeq -> 898
/*      */     //   881: aload_0
/*      */     //   882: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   885: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   888: invokevirtual isSpectator : ()Z
/*      */     //   891: ifne -> 898
/*      */     //   894: iconst_1
/*      */     //   895: goto -> 899
/*      */     //   898: iconst_0
/*      */     //   899: istore #23
/*      */     //   901: aload_0
/*      */     //   902: getfield renderInfosEntities : Ljava/util/List;
/*      */     //   905: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   910: astore #24
/*      */     //   912: aload #24
/*      */     //   914: invokeinterface hasNext : ()Z
/*      */     //   919: ifeq -> 1324
/*      */     //   922: aload #24
/*      */     //   924: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   929: astore #25
/*      */     //   931: aload #25
/*      */     //   933: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   936: astore #26
/*      */     //   938: aload #26
/*      */     //   940: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   943: invokevirtual getChunk : ()Lnet/minecraft/world/chunk/Chunk;
/*      */     //   946: astore #27
/*      */     //   948: aload #27
/*      */     //   950: invokevirtual getEntityLists : ()[Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   953: aload #26
/*      */     //   955: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   958: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   961: invokevirtual getY : ()I
/*      */     //   964: bipush #16
/*      */     //   966: idiv
/*      */     //   967: aaload
/*      */     //   968: astore #28
/*      */     //   970: aload #28
/*      */     //   972: invokevirtual isEmpty : ()Z
/*      */     //   975: ifne -> 1321
/*      */     //   978: aload #28
/*      */     //   980: invokevirtual iterator : ()Ljava/util/Iterator;
/*      */     //   983: astore #29
/*      */     //   985: aload #29
/*      */     //   987: invokeinterface hasNext : ()Z
/*      */     //   992: ifne -> 998
/*      */     //   995: goto -> 912
/*      */     //   998: aload #29
/*      */     //   1000: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1005: checkcast net/minecraft/entity/Entity
/*      */     //   1008: astore #30
/*      */     //   1010: iload #19
/*      */     //   1012: ifeq -> 1038
/*      */     //   1015: aload #30
/*      */     //   1017: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1020: iconst_1
/*      */     //   1021: anewarray java/lang/Object
/*      */     //   1024: dup
/*      */     //   1025: iconst_0
/*      */     //   1026: iload #4
/*      */     //   1028: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1031: aastore
/*      */     //   1032: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1035: ifeq -> 985
/*      */     //   1038: aload_0
/*      */     //   1039: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1042: aload #30
/*      */     //   1044: aload_2
/*      */     //   1045: dload #5
/*      */     //   1047: dload #7
/*      */     //   1049: dload #9
/*      */     //   1051: invokevirtual shouldRender : (Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z
/*      */     //   1054: ifne -> 1072
/*      */     //   1057: aload #30
/*      */     //   1059: getfield riddenByEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1062: aload_0
/*      */     //   1063: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1066: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1069: if_acmpne -> 1076
/*      */     //   1072: iconst_1
/*      */     //   1073: goto -> 1077
/*      */     //   1076: iconst_0
/*      */     //   1077: istore #31
/*      */     //   1079: iload #31
/*      */     //   1081: ifne -> 1087
/*      */     //   1084: goto -> 1243
/*      */     //   1087: aload_0
/*      */     //   1088: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1091: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1094: instanceof net/minecraft/entity/EntityLivingBase
/*      */     //   1097: ifeq -> 1116
/*      */     //   1100: aload_0
/*      */     //   1101: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1104: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1107: checkcast net/minecraft/entity/EntityLivingBase
/*      */     //   1110: invokevirtual isPlayerSleeping : ()Z
/*      */     //   1113: goto -> 1117
/*      */     //   1116: iconst_0
/*      */     //   1117: istore #32
/*      */     //   1119: aload #30
/*      */     //   1121: aload_0
/*      */     //   1122: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1125: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1128: if_acmpne -> 1154
/*      */     //   1131: iload #23
/*      */     //   1133: ifne -> 1154
/*      */     //   1136: aload_0
/*      */     //   1137: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1140: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1143: getfield thirdPersonView : I
/*      */     //   1146: ifne -> 1154
/*      */     //   1149: iload #32
/*      */     //   1151: ifeq -> 1240
/*      */     //   1154: aload #30
/*      */     //   1156: getfield posY : D
/*      */     //   1159: dconst_0
/*      */     //   1160: dcmpg
/*      */     //   1161: iflt -> 1195
/*      */     //   1164: aload #30
/*      */     //   1166: getfield posY : D
/*      */     //   1169: ldc2_w 256.0
/*      */     //   1172: dcmpl
/*      */     //   1173: ifge -> 1195
/*      */     //   1176: aload_0
/*      */     //   1177: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1180: new net/minecraft/util/BlockPos
/*      */     //   1183: dup
/*      */     //   1184: aload #30
/*      */     //   1186: invokespecial <init> : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1189: invokevirtual isBlockLoaded : (Lnet/minecraft/util/BlockPos;)Z
/*      */     //   1192: ifeq -> 1240
/*      */     //   1195: aload_0
/*      */     //   1196: dup
/*      */     //   1197: getfield countEntitiesRendered : I
/*      */     //   1200: iconst_1
/*      */     //   1201: iadd
/*      */     //   1202: putfield countEntitiesRendered : I
/*      */     //   1205: aload_0
/*      */     //   1206: aload #30
/*      */     //   1208: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1211: iload #21
/*      */     //   1213: ifeq -> 1221
/*      */     //   1216: aload #30
/*      */     //   1218: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1221: aload_0
/*      */     //   1222: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1225: aload #30
/*      */     //   1227: fload_3
/*      */     //   1228: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   1231: pop
/*      */     //   1232: aload_0
/*      */     //   1233: aconst_null
/*      */     //   1234: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1237: goto -> 1243
/*      */     //   1240: goto -> 985
/*      */     //   1243: iload #31
/*      */     //   1245: ifne -> 1318
/*      */     //   1248: aload #30
/*      */     //   1250: instanceof net/minecraft/entity/projectile/EntityWitherSkull
/*      */     //   1253: ifeq -> 1318
/*      */     //   1256: iload #19
/*      */     //   1258: ifeq -> 1284
/*      */     //   1261: aload #30
/*      */     //   1263: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1266: iconst_1
/*      */     //   1267: anewarray java/lang/Object
/*      */     //   1270: dup
/*      */     //   1271: iconst_0
/*      */     //   1272: iload #4
/*      */     //   1274: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1277: aastore
/*      */     //   1278: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1281: ifeq -> 1318
/*      */     //   1284: aload_0
/*      */     //   1285: aload #30
/*      */     //   1287: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1290: iload #21
/*      */     //   1292: ifeq -> 1300
/*      */     //   1295: aload #30
/*      */     //   1297: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1300: aload_0
/*      */     //   1301: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1304: invokevirtual getRenderManager : ()Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1307: aload #30
/*      */     //   1309: fload_3
/*      */     //   1310: invokevirtual renderWitherSkull : (Lnet/minecraft/entity/Entity;F)V
/*      */     //   1313: aload_0
/*      */     //   1314: aconst_null
/*      */     //   1315: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1318: goto -> 985
/*      */     //   1321: goto -> 912
/*      */     //   1324: aload_0
/*      */     //   1325: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1328: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1331: iload #22
/*      */     //   1333: putfield fancyGraphics : Z
/*      */     //   1336: iload #21
/*      */     //   1338: ifeq -> 1347
/*      */     //   1341: invokestatic endEntities : ()V
/*      */     //   1344: invokestatic beginBlockEntities : ()V
/*      */     //   1347: aload_0
/*      */     //   1348: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1351: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   1354: ldc_w 'blockentities'
/*      */     //   1357: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   1360: invokestatic enableStandardItemLighting : ()V
/*      */     //   1363: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_hasFastRenderer : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1366: invokevirtual exists : ()Z
/*      */     //   1369: ifeq -> 1378
/*      */     //   1372: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1375: invokevirtual preDrawBatch : ()V
/*      */     //   1378: invokestatic updateTextRenderDistance : ()V
/*      */     //   1381: aload_0
/*      */     //   1382: getfield renderInfosTileEntities : Ljava/util/List;
/*      */     //   1385: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1390: astore #24
/*      */     //   1392: aload #24
/*      */     //   1394: invokeinterface hasNext : ()Z
/*      */     //   1399: ifeq -> 1581
/*      */     //   1402: aload #24
/*      */     //   1404: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1409: astore #25
/*      */     //   1411: aload #25
/*      */     //   1413: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   1416: astore #26
/*      */     //   1418: aload #26
/*      */     //   1420: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   1423: invokevirtual getCompiledChunk : ()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
/*      */     //   1426: invokevirtual getTileEntities : ()Ljava/util/List;
/*      */     //   1429: astore #27
/*      */     //   1431: aload #27
/*      */     //   1433: invokeinterface isEmpty : ()Z
/*      */     //   1438: ifne -> 1578
/*      */     //   1441: aload #27
/*      */     //   1443: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1448: astore #28
/*      */     //   1450: aload #28
/*      */     //   1452: invokeinterface hasNext : ()Z
/*      */     //   1457: ifne -> 1463
/*      */     //   1460: goto -> 1392
/*      */     //   1463: aload #28
/*      */     //   1465: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1470: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1473: astore #29
/*      */     //   1475: iload #20
/*      */     //   1477: ifne -> 1483
/*      */     //   1480: goto -> 1545
/*      */     //   1483: aload #29
/*      */     //   1485: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1488: iconst_1
/*      */     //   1489: anewarray java/lang/Object
/*      */     //   1492: dup
/*      */     //   1493: iconst_0
/*      */     //   1494: iload #4
/*      */     //   1496: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1499: aastore
/*      */     //   1500: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1503: ifeq -> 1450
/*      */     //   1506: aload #29
/*      */     //   1508: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_getRenderBoundingBox : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1511: iconst_0
/*      */     //   1512: anewarray java/lang/Object
/*      */     //   1515: invokestatic call : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1518: checkcast net/minecraft/util/AxisAlignedBB
/*      */     //   1521: astore #30
/*      */     //   1523: aload #30
/*      */     //   1525: ifnull -> 1545
/*      */     //   1528: aload_2
/*      */     //   1529: aload #30
/*      */     //   1531: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   1536: ifeq -> 1542
/*      */     //   1539: goto -> 1545
/*      */     //   1542: goto -> 1450
/*      */     //   1545: iload #21
/*      */     //   1547: ifeq -> 1555
/*      */     //   1550: aload #29
/*      */     //   1552: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   1555: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1558: aload #29
/*      */     //   1560: fload_3
/*      */     //   1561: iconst_m1
/*      */     //   1562: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1565: aload_0
/*      */     //   1566: dup
/*      */     //   1567: getfield countTileEntitiesRendered : I
/*      */     //   1570: iconst_1
/*      */     //   1571: iadd
/*      */     //   1572: putfield countTileEntitiesRendered : I
/*      */     //   1575: goto -> 1450
/*      */     //   1578: goto -> 1392
/*      */     //   1581: aload_0
/*      */     //   1582: getfield setTileEntities : Ljava/util/Set;
/*      */     //   1585: dup
/*      */     //   1586: astore #24
/*      */     //   1588: monitorenter
/*      */     //   1589: aload_0
/*      */     //   1590: getfield setTileEntities : Ljava/util/Set;
/*      */     //   1593: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1598: astore #25
/*      */     //   1600: aload #25
/*      */     //   1602: invokeinterface hasNext : ()Z
/*      */     //   1607: ifeq -> 1673
/*      */     //   1610: aload #25
/*      */     //   1612: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1617: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1620: astore #26
/*      */     //   1622: iload #20
/*      */     //   1624: ifeq -> 1650
/*      */     //   1627: aload #26
/*      */     //   1629: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1632: iconst_1
/*      */     //   1633: anewarray java/lang/Object
/*      */     //   1636: dup
/*      */     //   1637: iconst_0
/*      */     //   1638: iload #4
/*      */     //   1640: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1643: aastore
/*      */     //   1644: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1647: ifeq -> 1670
/*      */     //   1650: iload #21
/*      */     //   1652: ifeq -> 1660
/*      */     //   1655: aload #26
/*      */     //   1657: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   1660: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1663: aload #26
/*      */     //   1665: fload_3
/*      */     //   1666: iconst_m1
/*      */     //   1667: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1670: goto -> 1600
/*      */     //   1673: aload #24
/*      */     //   1675: monitorexit
/*      */     //   1676: goto -> 1687
/*      */     //   1679: astore #33
/*      */     //   1681: aload #24
/*      */     //   1683: monitorexit
/*      */     //   1684: aload #33
/*      */     //   1686: athrow
/*      */     //   1687: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_hasFastRenderer : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1690: invokevirtual exists : ()Z
/*      */     //   1693: ifeq -> 1704
/*      */     //   1696: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1699: iload #4
/*      */     //   1701: invokevirtual drawBatch : (I)V
/*      */     //   1704: aload_0
/*      */     //   1705: iconst_1
/*      */     //   1706: putfield renderOverlayDamaged : Z
/*      */     //   1709: aload_0
/*      */     //   1710: invokespecial preRenderDamagedBlocks : ()V
/*      */     //   1713: aload_0
/*      */     //   1714: getfield damagedBlocks : Ljava/util/Map;
/*      */     //   1717: invokeinterface values : ()Ljava/util/Collection;
/*      */     //   1722: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1727: astore #24
/*      */     //   1729: aload #24
/*      */     //   1731: invokeinterface hasNext : ()Z
/*      */     //   1736: ifeq -> 2023
/*      */     //   1739: aload #24
/*      */     //   1741: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1746: checkcast net/minecraft/client/renderer/DestroyBlockProgress
/*      */     //   1749: astore #25
/*      */     //   1751: aload #25
/*      */     //   1753: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   1756: astore #26
/*      */     //   1758: aload_0
/*      */     //   1759: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1762: aload #26
/*      */     //   1764: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1767: astore #27
/*      */     //   1769: aload #27
/*      */     //   1771: instanceof net/minecraft/tileentity/TileEntityChest
/*      */     //   1774: ifeq -> 1845
/*      */     //   1777: aload #27
/*      */     //   1779: checkcast net/minecraft/tileentity/TileEntityChest
/*      */     //   1782: astore #28
/*      */     //   1784: aload #28
/*      */     //   1786: getfield adjacentChestXNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1789: ifnull -> 1816
/*      */     //   1792: aload #26
/*      */     //   1794: getstatic net/minecraft/util/EnumFacing.WEST : Lnet/minecraft/util/EnumFacing;
/*      */     //   1797: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
/*      */     //   1800: astore #26
/*      */     //   1802: aload_0
/*      */     //   1803: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1806: aload #26
/*      */     //   1808: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1811: astore #27
/*      */     //   1813: goto -> 1845
/*      */     //   1816: aload #28
/*      */     //   1818: getfield adjacentChestZNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1821: ifnull -> 1845
/*      */     //   1824: aload #26
/*      */     //   1826: getstatic net/minecraft/util/EnumFacing.NORTH : Lnet/minecraft/util/EnumFacing;
/*      */     //   1829: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
/*      */     //   1832: astore #26
/*      */     //   1834: aload_0
/*      */     //   1835: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1838: aload #26
/*      */     //   1840: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1843: astore #27
/*      */     //   1845: aload_0
/*      */     //   1846: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1849: aload #26
/*      */     //   1851: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   1854: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*      */     //   1859: astore #28
/*      */     //   1861: iload #20
/*      */     //   1863: ifeq -> 1947
/*      */     //   1866: iconst_0
/*      */     //   1867: istore #29
/*      */     //   1869: aload #27
/*      */     //   1871: ifnull -> 1991
/*      */     //   1874: aload #27
/*      */     //   1876: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1879: iconst_1
/*      */     //   1880: anewarray java/lang/Object
/*      */     //   1883: dup
/*      */     //   1884: iconst_0
/*      */     //   1885: iload #4
/*      */     //   1887: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1890: aastore
/*      */     //   1891: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1894: ifeq -> 1991
/*      */     //   1897: aload #27
/*      */     //   1899: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_canRenderBreaking : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1902: iconst_0
/*      */     //   1903: anewarray java/lang/Object
/*      */     //   1906: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1909: ifeq -> 1991
/*      */     //   1912: aload #27
/*      */     //   1914: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_getRenderBoundingBox : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1917: iconst_0
/*      */     //   1918: anewarray java/lang/Object
/*      */     //   1921: invokestatic call : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1924: checkcast net/minecraft/util/AxisAlignedBB
/*      */     //   1927: astore #30
/*      */     //   1929: aload #30
/*      */     //   1931: ifnull -> 1944
/*      */     //   1934: aload_2
/*      */     //   1935: aload #30
/*      */     //   1937: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   1942: istore #29
/*      */     //   1944: goto -> 1991
/*      */     //   1947: aload #27
/*      */     //   1949: ifnull -> 1988
/*      */     //   1952: aload #28
/*      */     //   1954: instanceof net/minecraft/block/BlockChest
/*      */     //   1957: ifne -> 1984
/*      */     //   1960: aload #28
/*      */     //   1962: instanceof net/minecraft/block/BlockEnderChest
/*      */     //   1965: ifne -> 1984
/*      */     //   1968: aload #28
/*      */     //   1970: instanceof net/minecraft/block/BlockSign
/*      */     //   1973: ifne -> 1984
/*      */     //   1976: aload #28
/*      */     //   1978: instanceof net/minecraft/block/BlockSkull
/*      */     //   1981: ifeq -> 1988
/*      */     //   1984: iconst_1
/*      */     //   1985: goto -> 1989
/*      */     //   1988: iconst_0
/*      */     //   1989: istore #29
/*      */     //   1991: iload #29
/*      */     //   1993: ifeq -> 2020
/*      */     //   1996: iload #21
/*      */     //   1998: ifeq -> 2006
/*      */     //   2001: aload #27
/*      */     //   2003: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   2006: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   2009: aload #27
/*      */     //   2011: fload_3
/*      */     //   2012: aload #25
/*      */     //   2014: invokevirtual getPartialBlockDamage : ()I
/*      */     //   2017: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   2020: goto -> 1729
/*      */     //   2023: aload_0
/*      */     //   2024: invokespecial postRenderDamagedBlocks : ()V
/*      */     //   2027: aload_0
/*      */     //   2028: iconst_0
/*      */     //   2029: putfield renderOverlayDamaged : Z
/*      */     //   2032: iload #21
/*      */     //   2034: ifeq -> 2040
/*      */     //   2037: invokestatic endBlockEntities : ()V
/*      */     //   2040: getstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   2043: iconst_1
/*      */     //   2044: isub
/*      */     //   2045: putstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   2048: aload_0
/*      */     //   2049: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2052: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   2055: invokevirtual disableLightmap : ()V
/*      */     //   2058: aload_0
/*      */     //   2059: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2062: getfield mcProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   2065: invokevirtual endSection : ()V
/*      */     //   2068: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #605	-> 0
/*      */     //   #607	-> 3
/*      */     //   #609	-> 12
/*      */     //   #612	-> 24
/*      */     //   #614	-> 31
/*      */     //   #616	-> 36
/*      */     //   #619	-> 37
/*      */     //   #623	-> 50
/*      */     //   #624	-> 69
/*      */     //   #625	-> 88
/*      */     //   #626	-> 107
/*      */     //   #627	-> 119
/*      */     //   #628	-> 151
/*      */     //   #629	-> 191
/*      */     //   #631	-> 199
/*      */     //   #633	-> 204
/*      */     //   #634	-> 209
/*      */     //   #635	-> 214
/*      */     //   #636	-> 219
/*      */     //   #639	-> 224
/*      */     //   #640	-> 233
/*      */     //   #641	-> 255
/*      */     //   #642	-> 277
/*      */     //   #643	-> 299
/*      */     //   #644	-> 304
/*      */     //   #645	-> 309
/*      */     //   #646	-> 314
/*      */     //   #647	-> 327
/*      */     //   #648	-> 337
/*      */     //   #649	-> 350
/*      */     //   #651	-> 359
/*      */     //   #653	-> 364
/*      */     //   #656	-> 375
/*      */     //   #658	-> 394
/*      */     //   #661	-> 397
/*      */     //   #662	-> 405
/*      */     //   #664	-> 413
/*      */     //   #666	-> 433
/*      */     //   #668	-> 452
/*      */     //   #670	-> 480
/*      */     //   #672	-> 490
/*      */     //   #674	-> 504
/*      */     //   #664	-> 515
/*      */     //   #679	-> 521
/*      */     //   #681	-> 528
/*      */     //   #682	-> 534
/*      */     //   #683	-> 537
/*      */     //   #684	-> 544
/*      */     //   #685	-> 552
/*      */     //   #686	-> 565
/*      */     //   #687	-> 568
/*      */     //   #689	-> 576
/*      */     //   #691	-> 591
/*      */     //   #692	-> 605
/*      */     //   #693	-> 641
/*      */     //   #695	-> 707
/*      */     //   #697	-> 742
/*      */     //   #689	-> 753
/*      */     //   #701	-> 759
/*      */     //   #702	-> 767
/*      */     //   #703	-> 770
/*      */     //   #704	-> 774
/*      */     //   #705	-> 782
/*      */     //   #706	-> 785
/*      */     //   #707	-> 789
/*      */     //   #708	-> 800
/*      */     //   #709	-> 803
/*      */     //   #710	-> 806
/*      */     //   #711	-> 809
/*      */     //   #712	-> 815
/*      */     //   #713	-> 818
/*      */     //   #716	-> 821
/*      */     //   #717	-> 834
/*      */     //   #719	-> 839
/*      */     //   #721	-> 844
/*      */     //   #724	-> 847
/*      */     //   #725	-> 850
/*      */     //   #726	-> 862
/*      */     //   #727	-> 875
/*      */     //   #730	-> 901
/*      */     //   #732	-> 931
/*      */     //   #733	-> 938
/*      */     //   #734	-> 948
/*      */     //   #736	-> 970
/*      */     //   #738	-> 978
/*      */     //   #747	-> 985
/*      */     //   #749	-> 995
/*      */     //   #752	-> 998
/*      */     //   #754	-> 1010
/*      */     //   #756	-> 1038
/*      */     //   #758	-> 1079
/*      */     //   #760	-> 1084
/*      */     //   #763	-> 1087
/*      */     //   #765	-> 1119
/*      */     //   #767	-> 1195
/*      */     //   #768	-> 1205
/*      */     //   #770	-> 1211
/*      */     //   #772	-> 1216
/*      */     //   #775	-> 1221
/*      */     //   #776	-> 1232
/*      */     //   #777	-> 1237
/*      */     //   #779	-> 1240
/*      */     //   #782	-> 1243
/*      */     //   #784	-> 1284
/*      */     //   #786	-> 1290
/*      */     //   #788	-> 1295
/*      */     //   #791	-> 1300
/*      */     //   #792	-> 1313
/*      */     //   #794	-> 1318
/*      */     //   #796	-> 1321
/*      */     //   #798	-> 1324
/*      */     //   #800	-> 1336
/*      */     //   #802	-> 1341
/*      */     //   #803	-> 1344
/*      */     //   #806	-> 1347
/*      */     //   #807	-> 1360
/*      */     //   #809	-> 1363
/*      */     //   #811	-> 1372
/*      */     //   #814	-> 1378
/*      */     //   #817	-> 1381
/*      */     //   #819	-> 1411
/*      */     //   #820	-> 1418
/*      */     //   #822	-> 1431
/*      */     //   #824	-> 1441
/*      */     //   #832	-> 1450
/*      */     //   #834	-> 1460
/*      */     //   #837	-> 1463
/*      */     //   #839	-> 1475
/*      */     //   #841	-> 1480
/*      */     //   #844	-> 1483
/*      */     //   #846	-> 1506
/*      */     //   #848	-> 1523
/*      */     //   #850	-> 1539
/*      */     //   #852	-> 1542
/*      */     //   #855	-> 1545
/*      */     //   #857	-> 1550
/*      */     //   #860	-> 1555
/*      */     //   #861	-> 1565
/*      */     //   #862	-> 1575
/*      */     //   #864	-> 1578
/*      */     //   #866	-> 1581
/*      */     //   #868	-> 1589
/*      */     //   #870	-> 1622
/*      */     //   #872	-> 1650
/*      */     //   #874	-> 1655
/*      */     //   #877	-> 1660
/*      */     //   #879	-> 1670
/*      */     //   #880	-> 1673
/*      */     //   #882	-> 1687
/*      */     //   #884	-> 1696
/*      */     //   #887	-> 1704
/*      */     //   #888	-> 1709
/*      */     //   #890	-> 1713
/*      */     //   #892	-> 1751
/*      */     //   #893	-> 1758
/*      */     //   #895	-> 1769
/*      */     //   #897	-> 1777
/*      */     //   #899	-> 1784
/*      */     //   #901	-> 1792
/*      */     //   #902	-> 1802
/*      */     //   #904	-> 1816
/*      */     //   #906	-> 1824
/*      */     //   #907	-> 1834
/*      */     //   #911	-> 1845
/*      */     //   #914	-> 1861
/*      */     //   #916	-> 1866
/*      */     //   #918	-> 1869
/*      */     //   #920	-> 1912
/*      */     //   #922	-> 1929
/*      */     //   #924	-> 1934
/*      */     //   #926	-> 1944
/*      */     //   #930	-> 1947
/*      */     //   #933	-> 1991
/*      */     //   #935	-> 1996
/*      */     //   #937	-> 2001
/*      */     //   #940	-> 2006
/*      */     //   #942	-> 2020
/*      */     //   #944	-> 2023
/*      */     //   #945	-> 2027
/*      */     //   #947	-> 2032
/*      */     //   #949	-> 2037
/*      */     //   #952	-> 2040
/*      */     //   #953	-> 2048
/*      */     //   #954	-> 2058
/*      */     //   #956	-> 2068
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   452	63	22	entity1	Lnet/minecraft/entity/Entity;
/*      */     //   416	105	21	j	I
/*      */     //   605	148	22	entity3	Lnet/minecraft/entity/Entity;
/*      */     //   641	112	23	flag2	Z
/*      */     //   707	46	24	flag3	Z
/*      */     //   579	180	21	k	I
/*      */     //   1119	121	32	flag5	Z
/*      */     //   1010	308	30	entity2	Lnet/minecraft/entity/Entity;
/*      */     //   1079	239	31	flag4	Z
/*      */     //   985	336	29	iterator	Ljava/util/Iterator;
/*      */     //   938	383	26	renderglobal$containerlocalrenderinformation	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   948	373	27	chunk	Lnet/minecraft/world/chunk/Chunk;
/*      */     //   970	351	28	classinheritancemultimap	Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   931	390	25	o	Ljava/lang/Object;
/*      */     //   1523	19	30	axisalignedbb1	Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   1475	100	29	tileentity1	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1450	128	28	iterator1	Ljava/util/Iterator;
/*      */     //   1418	160	26	renderglobal$containerlocalrenderinformation1	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   1431	147	27	list1	Ljava/util/List;
/*      */     //   1411	167	25	o	Ljava/lang/Object;
/*      */     //   1622	48	26	tileentity	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1784	61	28	tileentitychest	Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1929	15	30	axisalignedbb	Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   1869	78	29	flag9	Z
/*      */     //   1758	262	26	blockpos	Lnet/minecraft/util/BlockPos;
/*      */     //   1769	251	27	tileentity2	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1861	159	28	block	Lnet/minecraft/block/Block;
/*      */     //   1991	29	29	flag9	Z
/*      */     //   1751	269	25	destroyblockprogress	Lnet/minecraft/client/renderer/DestroyBlockProgress;
/*      */     //   69	1999	5	d0	D
/*      */     //   88	1980	7	d1	D
/*      */     //   107	1961	9	d2	D
/*      */     //   233	1835	11	entity	Lnet/minecraft/entity/Entity;
/*      */     //   255	1813	12	d3	D
/*      */     //   277	1791	14	d4	D
/*      */     //   299	1769	16	d5	D
/*      */     //   359	1709	18	list	Ljava/util/List;
/*      */     //   405	1663	19	flag	Z
/*      */     //   413	1655	20	flag1	Z
/*      */     //   839	1229	21	flag6	Z
/*      */     //   862	1206	22	flag7	Z
/*      */     //   901	1167	23	flag8	Z
/*      */     //   0	2069	0	this	Lnet/minecraft/client/renderer/RenderGlobal;
/*      */     //   0	2069	1	renderViewEntity	Lnet/minecraft/entity/Entity;
/*      */     //   0	2069	2	camera	Lnet/minecraft/client/renderer/culling/ICamera;
/*      */     //   0	2069	3	partialTicks	F
/*      */     //   3	2066	4	i	I
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   970	351	28	classinheritancemultimap	Lnet/minecraft/util/ClassInheritanceMultiMap<Lnet/minecraft/entity/Entity;>;
/*      */     //   1431	147	27	list1	Ljava/util/List<Lnet/minecraft/tileentity/TileEntity;>;
/*      */     //   359	1709	18	list	Ljava/util/List<Lnet/minecraft/entity/Entity;>;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   1589	1676	1679	finally
/*      */     //   1679	1684	1679	finally
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoRenders() {
/*  963 */     int i = this.viewFrustum.renderChunks.length;
/*  964 */     int j = 0;
/*      */     
/*  966 */     for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
/*      */       
/*  968 */       CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
/*      */       
/*  970 */       if (compiledchunk != CompiledChunk.DUMMY && !compiledchunk.isEmpty())
/*      */       {
/*  972 */         j++;
/*      */       }
/*      */     } 
/*      */     
/*  976 */     return String.format("C: %d/%d %sD: %d, %s", new Object[] { Integer.valueOf(j), Integer.valueOf(i), this.mc.renderChunksMany ? "(s) " : "", Integer.valueOf(this.renderDistanceChunks), this.renderDispatcher.getDebugInfo() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoEntities() {
/*  984 */     return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersionDebug();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*  989 */     if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks)
/*      */     {
/*  991 */       loadRenderers();
/*      */     }
/*      */     
/*  994 */     this.theWorld.theProfiler.startSection("camera");
/*  995 */     double d0 = viewEntity.posX - this.frustumUpdatePosX;
/*  996 */     double d1 = viewEntity.posY - this.frustumUpdatePosY;
/*  997 */     double d2 = viewEntity.posZ - this.frustumUpdatePosZ;
/*      */     
/*  999 */     if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX || this.frustumUpdatePosChunkY != viewEntity.chunkCoordY || this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0D) {
/*      */       
/* 1001 */       this.frustumUpdatePosX = viewEntity.posX;
/* 1002 */       this.frustumUpdatePosY = viewEntity.posY;
/* 1003 */       this.frustumUpdatePosZ = viewEntity.posZ;
/* 1004 */       this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
/* 1005 */       this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
/* 1006 */       this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
/* 1007 */       this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
/*      */     } 
/*      */     
/* 1010 */     if (Config.isDynamicLights())
/*      */     {
/* 1012 */       DynamicLights.update(this);
/*      */     }
/*      */     
/* 1015 */     this.theWorld.theProfiler.endStartSection("renderlistcamera");
/* 1016 */     double d3 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
/* 1017 */     double d4 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
/* 1018 */     double d5 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
/* 1019 */     this.renderContainer.initialize(d3, d4, d5);
/* 1020 */     this.theWorld.theProfiler.endStartSection("cull");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1031 */     this.mc.mcProfiler.endStartSection("culling");
/* 1032 */     BlockPos blockpos = new BlockPos(d3, d4 + viewEntity.getEyeHeight(), d5);
/* 1033 */     RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos);
/* 1034 */     new BlockPos(MathHelper.floor_double(d3 / 16.0D) * 16, MathHelper.floor_double(d4 / 16.0D) * 16, MathHelper.floor_double(d5 / 16.0D) * 16);
/* 1035 */     this.displayListEntitiesDirty = (this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || viewEntity.posX != this.lastViewEntityX || viewEntity.posY != this.lastViewEntityY || viewEntity.posZ != this.lastViewEntityZ || viewEntity.rotationPitch != this.lastViewEntityPitch || viewEntity.rotationYaw != this.lastViewEntityYaw);
/* 1036 */     this.lastViewEntityX = viewEntity.posX;
/* 1037 */     this.lastViewEntityY = viewEntity.posY;
/* 1038 */     this.lastViewEntityZ = viewEntity.posZ;
/* 1039 */     this.lastViewEntityPitch = viewEntity.rotationPitch;
/* 1040 */     this.lastViewEntityYaw = viewEntity.rotationYaw;
/* 1041 */     boolean flag = (this.debugFixedClippingHelper != null);
/* 1042 */     this.mc.mcProfiler.endStartSection("update");
/* 1043 */     Lagometer.timerVisibility.start();
/* 1044 */     int i = getCountLoadedChunks();
/*      */     
/* 1046 */     if (i != this.countLoadedChunksPrev) {
/*      */       
/* 1048 */       this.countLoadedChunksPrev = i;
/* 1049 */       this.displayListEntitiesDirty = true;
/*      */     } 
/*      */     
/* 1052 */     int j = 256;
/*      */     
/* 1054 */     if (!ChunkVisibility.isFinished())
/*      */     {
/* 1056 */       this.displayListEntitiesDirty = true;
/*      */     }
/*      */     
/* 1059 */     if (!flag && this.displayListEntitiesDirty && Config.isIntegratedServerRunning())
/*      */     {
/* 1061 */       j = ChunkVisibility.getMaxChunkY((World)this.theWorld, viewEntity, this.renderDistanceChunks);
/*      */     }
/*      */     
/* 1064 */     RenderChunk renderchunk1 = this.viewFrustum.getRenderChunk(new BlockPos(viewEntity.posX, viewEntity.posY, viewEntity.posZ));
/*      */     
/* 1066 */     if (Shaders.isShadowPass) {
/*      */       
/* 1068 */       this.renderInfos = this.renderInfosShadow;
/* 1069 */       this.renderInfosEntities = this.renderInfosEntitiesShadow;
/* 1070 */       this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
/*      */       
/* 1072 */       if (!flag && this.displayListEntitiesDirty) {
/*      */         
/* 1074 */         clearRenderInfos();
/*      */         
/* 1076 */         if (renderchunk1 != null && renderchunk1.getPosition().getY() > j)
/*      */         {
/* 1078 */           this.renderInfosEntities.add(renderchunk1.getRenderInfo());
/*      */         }
/*      */         
/* 1081 */         Iterator<RenderChunk> iterator = ShadowUtils.makeShadowChunkIterator(this.theWorld, partialTicks, viewEntity, this.renderDistanceChunks, this.viewFrustum);
/*      */         
/* 1083 */         while (iterator.hasNext()) {
/*      */           
/* 1085 */           RenderChunk renderchunk2 = iterator.next();
/*      */           
/* 1087 */           if (renderchunk2 != null && renderchunk2.getPosition().getY() <= j)
/*      */           {
/* 1089 */             ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = renderchunk2.getRenderInfo();
/*      */             
/* 1091 */             if (!renderchunk2.compiledChunk.isEmpty() || renderchunk2.isNeedsUpdate())
/*      */             {
/* 1093 */               this.renderInfos.add(renderglobal$containerlocalrenderinformation);
/*      */             }
/*      */             
/* 1096 */             if (ChunkUtils.hasEntities(renderchunk2.getChunk()))
/*      */             {
/* 1098 */               this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation);
/*      */             }
/*      */             
/* 1101 */             if (renderchunk2.getCompiledChunk().getTileEntities().size() > 0)
/*      */             {
/* 1103 */               this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation);
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1111 */       this.renderInfos = this.renderInfosNormal;
/* 1112 */       this.renderInfosEntities = this.renderInfosEntitiesNormal;
/* 1113 */       this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
/*      */     } 
/*      */     
/* 1116 */     if (!flag && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
/*      */       
/* 1118 */       this.displayListEntitiesDirty = false;
/* 1119 */       clearRenderInfos();
/* 1120 */       this.visibilityDeque.clear();
/* 1121 */       Deque<ContainerLocalRenderInformation> deque = this.visibilityDeque;
/* 1122 */       boolean flag1 = this.mc.renderChunksMany;
/*      */       
/* 1124 */       if (renderchunk != null && renderchunk.getPosition().getY() <= j) {
/*      */         
/* 1126 */         boolean flag2 = false;
/* 1127 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation4 = new ContainerLocalRenderInformation(renderchunk, (EnumFacing)null, 0);
/* 1128 */         Set set1 = SET_ALL_FACINGS;
/*      */         
/* 1130 */         if (set1.size() == 1) {
/*      */           
/* 1132 */           Vector3f vector3f = getViewVector(viewEntity, partialTicks);
/* 1133 */           EnumFacing enumfacing2 = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
/* 1134 */           set1.remove(enumfacing2);
/*      */         } 
/*      */         
/* 1137 */         if (set1.isEmpty())
/*      */         {
/* 1139 */           flag2 = true;
/*      */         }
/*      */         
/* 1142 */         if (flag2 && !playerSpectator)
/*      */         {
/* 1144 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation4);
/*      */         }
/*      */         else
/*      */         {
/* 1148 */           if (playerSpectator && this.theWorld.getBlockState(blockpos).getBlock().isOpaqueCube())
/*      */           {
/* 1150 */             flag1 = false;
/*      */           }
/*      */           
/* 1153 */           renderchunk.setFrameIndex(frameCount);
/* 1154 */           deque.add(renderglobal$containerlocalrenderinformation4);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1159 */         int j1 = (blockpos.getY() > 0) ? Math.min(j, 248) : 8;
/*      */         
/* 1161 */         if (renderchunk1 != null)
/*      */         {
/* 1163 */           this.renderInfosEntities.add(renderchunk1.getRenderInfo());
/*      */         }
/*      */         
/* 1166 */         for (int k = -this.renderDistanceChunks; k <= this.renderDistanceChunks; k++) {
/*      */           
/* 1168 */           for (int l = -this.renderDistanceChunks; l <= this.renderDistanceChunks; l++) {
/*      */             
/* 1170 */             RenderChunk renderchunk3 = this.viewFrustum.getRenderChunk(new BlockPos((k << 4) + 8, j1, (l << 4) + 8));
/*      */             
/* 1172 */             if (renderchunk3 != null && renderchunk3.isBoundingBoxInFrustum(camera, frameCount)) {
/*      */               
/* 1174 */               renderchunk3.setFrameIndex(frameCount);
/* 1175 */               ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = renderchunk3.getRenderInfo();
/* 1176 */               renderglobal$containerlocalrenderinformation1.initialize((EnumFacing)null, 0);
/* 1177 */               deque.add(renderglobal$containerlocalrenderinformation1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1183 */       this.mc.mcProfiler.startSection("iteration");
/* 1184 */       boolean flag3 = Config.isFogOn();
/*      */       
/* 1186 */       while (!deque.isEmpty()) {
/*      */         
/* 1188 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation5 = deque.poll();
/* 1189 */         RenderChunk renderchunk6 = renderglobal$containerlocalrenderinformation5.renderChunk;
/* 1190 */         EnumFacing enumfacing1 = renderglobal$containerlocalrenderinformation5.facing;
/* 1191 */         CompiledChunk compiledchunk = renderchunk6.compiledChunk;
/*      */         
/* 1193 */         if (!compiledchunk.isEmpty() || renderchunk6.isNeedsUpdate())
/*      */         {
/* 1195 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation5);
/*      */         }
/*      */         
/* 1198 */         if (ChunkUtils.hasEntities(renderchunk6.getChunk()))
/*      */         {
/* 1200 */           this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation5);
/*      */         }
/*      */         
/* 1203 */         if (compiledchunk.getTileEntities().size() > 0)
/*      */         {
/* 1205 */           this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation5);
/*      */         }
/*      */         
/* 1208 */         for (EnumFacing enumfacing : flag1 ? ChunkVisibility.getFacingsNotOpposite(renderglobal$containerlocalrenderinformation5.setFacing) : EnumFacing.VALUES) {
/*      */           
/* 1210 */           if (!flag1 || enumfacing1 == null || compiledchunk.isVisible(enumfacing1.getOpposite(), enumfacing)) {
/*      */             
/* 1212 */             RenderChunk renderchunk4 = getRenderChunkOffset(blockpos, renderchunk6, enumfacing, flag3, j);
/*      */             
/* 1214 */             if (renderchunk4 != null && renderchunk4.setFrameIndex(frameCount) && renderchunk4.isBoundingBoxInFrustum(camera, frameCount)) {
/*      */               
/* 1216 */               int i1 = renderglobal$containerlocalrenderinformation5.setFacing | 1 << enumfacing.ordinal();
/* 1217 */               ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 = renderchunk4.getRenderInfo();
/* 1218 */               renderglobal$containerlocalrenderinformation2.initialize(enumfacing, i1);
/* 1219 */               deque.add(renderglobal$containerlocalrenderinformation2);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1225 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1228 */     this.mc.mcProfiler.endStartSection("captureFrustum");
/*      */     
/* 1230 */     if (this.debugFixTerrainFrustum) {
/*      */       
/* 1232 */       fixTerrainFrustum(d3, d4, d5);
/* 1233 */       this.debugFixTerrainFrustum = false;
/*      */     } 
/*      */     
/* 1236 */     Lagometer.timerVisibility.end();
/*      */     
/* 1238 */     if (Shaders.isShadowPass) {
/*      */       
/* 1240 */       Shaders.mcProfilerEndSection();
/*      */     }
/*      */     else {
/*      */       
/* 1244 */       this.mc.mcProfiler.endStartSection("rebuildNear");
/* 1245 */       this.renderDispatcher.clearChunkUpdates();
/* 1246 */       Set<RenderChunk> set = this.chunksToUpdate;
/* 1247 */       this.chunksToUpdate = Sets.newLinkedHashSet();
/* 1248 */       Lagometer.timerChunkUpdate.start();
/*      */       
/* 1250 */       for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 : this.renderInfos) {
/*      */         
/* 1252 */         RenderChunk renderchunk5 = renderglobal$containerlocalrenderinformation3.renderChunk;
/*      */         
/* 1254 */         if (renderchunk5.isNeedsUpdate() || set.contains(renderchunk5)) {
/*      */           
/* 1256 */           this.displayListEntitiesDirty = true;
/* 1257 */           BlockPos blockpos1 = renderchunk5.getPosition();
/* 1258 */           boolean flag4 = (blockpos.distanceSq((blockpos1.getX() + 8), (blockpos1.getY() + 8), (blockpos1.getZ() + 8)) < 768.0D);
/*      */           
/* 1260 */           if (!flag4) {
/*      */             
/* 1262 */             this.chunksToUpdate.add(renderchunk5); continue;
/*      */           } 
/* 1264 */           if (!renderchunk5.isPlayerUpdate()) {
/*      */             
/* 1266 */             this.chunksToUpdateForced.add(renderchunk5);
/*      */             
/*      */             continue;
/*      */           } 
/* 1270 */           this.mc.mcProfiler.startSection("build near");
/* 1271 */           this.renderDispatcher.updateChunkNow(renderchunk5);
/* 1272 */           renderchunk5.setNeedsUpdate(false);
/* 1273 */           this.mc.mcProfiler.endSection();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1278 */       Lagometer.timerChunkUpdate.end();
/* 1279 */       this.chunksToUpdate.addAll(set);
/* 1280 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isPositionInRenderChunk(BlockPos pos, RenderChunk renderChunkIn) {
/* 1286 */     BlockPos blockpos = renderChunkIn.getPosition();
/* 1287 */     return (MathHelper.abs_int(pos.getX() - blockpos.getX()) > 16) ? false : ((MathHelper.abs_int(pos.getY() - blockpos.getY()) > 16) ? false : ((MathHelper.abs_int(pos.getZ() - blockpos.getZ()) <= 16)));
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<EnumFacing> getVisibleFacings(BlockPos pos) {
/* 1292 */     VisGraph visgraph = new VisGraph();
/* 1293 */     BlockPos blockpos = new BlockPos(pos.getX() >> 4 << 4, pos.getY() >> 4 << 4, pos.getZ() >> 4 << 4);
/* 1294 */     Chunk chunk = this.theWorld.getChunkFromBlockCoords(blockpos);
/*      */     
/* 1296 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos.add(15, 15, 15))) {
/*      */       
/* 1298 */       if (chunk.getBlock((BlockPos)blockpos$mutableblockpos).isOpaqueCube())
/*      */       {
/* 1300 */         visgraph.func_178606_a((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/* 1303 */     return visgraph.func_178609_b(pos);
/*      */   }
/*      */ 
/*      */   
/*      */   private RenderChunk getRenderChunkOffset(BlockPos p_getRenderChunkOffset_1_, RenderChunk p_getRenderChunkOffset_2_, EnumFacing p_getRenderChunkOffset_3_, boolean p_getRenderChunkOffset_4_, int p_getRenderChunkOffset_5_) {
/* 1308 */     RenderChunk renderchunk = p_getRenderChunkOffset_2_.getRenderChunkNeighbour(p_getRenderChunkOffset_3_);
/*      */     
/* 1310 */     if (renderchunk == null)
/*      */     {
/* 1312 */       return null;
/*      */     }
/* 1314 */     if (renderchunk.getPosition().getY() > p_getRenderChunkOffset_5_)
/*      */     {
/* 1316 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1320 */     if (p_getRenderChunkOffset_4_) {
/*      */       
/* 1322 */       BlockPos blockpos = renderchunk.getPosition();
/* 1323 */       int i = p_getRenderChunkOffset_1_.getX() - blockpos.getX();
/* 1324 */       int j = p_getRenderChunkOffset_1_.getZ() - blockpos.getZ();
/* 1325 */       int k = i * i + j * j;
/*      */       
/* 1327 */       if (k > this.renderDistanceSq)
/*      */       {
/* 1329 */         return null;
/*      */       }
/*      */     } 
/*      */     
/* 1333 */     return renderchunk;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fixTerrainFrustum(double x, double y, double z) {
/* 1339 */     this.debugFixedClippingHelper = (ClippingHelper)new ClippingHelperImpl();
/* 1340 */     ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
/* 1341 */     Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
/* 1342 */     matrix4f.transpose();
/* 1343 */     Matrix4f matrix4f1 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
/* 1344 */     matrix4f1.transpose();
/* 1345 */     Matrix4f matrix4f2 = new Matrix4f();
/* 1346 */     Matrix4f.mul((Matrix4f)matrix4f1, (Matrix4f)matrix4f, (Matrix4f)matrix4f2);
/* 1347 */     matrix4f2.invert();
/* 1348 */     this.debugTerrainFrustumPosition.x = x;
/* 1349 */     this.debugTerrainFrustumPosition.y = y;
/* 1350 */     this.debugTerrainFrustumPosition.z = z;
/* 1351 */     this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
/* 1352 */     this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
/* 1353 */     this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
/* 1354 */     this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
/* 1355 */     this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
/* 1356 */     this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
/* 1357 */     this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1358 */     this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/* 1360 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1362 */       Matrix4f.transform((Matrix4f)matrix4f2, this.debugTerrainMatrix[i], this.debugTerrainMatrix[i]);
/* 1363 */       (this.debugTerrainMatrix[i]).x /= (this.debugTerrainMatrix[i]).w;
/* 1364 */       (this.debugTerrainMatrix[i]).y /= (this.debugTerrainMatrix[i]).w;
/* 1365 */       (this.debugTerrainMatrix[i]).z /= (this.debugTerrainMatrix[i]).w;
/* 1366 */       (this.debugTerrainMatrix[i]).w = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
/* 1372 */     float f = (float)(entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks);
/* 1373 */     float f1 = (float)(entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks);
/*      */     
/* 1375 */     if ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2)
/*      */     {
/* 1377 */       f += 180.0F;
/*      */     }
/*      */     
/* 1380 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 1381 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 1382 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 1383 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/* 1384 */     return new Vector3f(f3 * f4, f5, f2 * f4);
/*      */   }
/*      */ 
/*      */   
/*      */   public int renderBlockLayer(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
/* 1389 */     RenderHelper.disableStandardItemLighting();
/*      */     
/* 1391 */     if (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT && !Shaders.isShadowPass) {
/*      */       
/* 1393 */       this.mc.mcProfiler.startSection("translucent_sort");
/* 1394 */       double d0 = entityIn.posX - this.prevRenderSortX;
/* 1395 */       double d1 = entityIn.posY - this.prevRenderSortY;
/* 1396 */       double d2 = entityIn.posZ - this.prevRenderSortZ;
/*      */       
/* 1398 */       if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
/*      */         
/* 1400 */         this.prevRenderSortX = entityIn.posX;
/* 1401 */         this.prevRenderSortY = entityIn.posY;
/* 1402 */         this.prevRenderSortZ = entityIn.posZ;
/* 1403 */         int k = 0;
/* 1404 */         this.chunksToResortTransparency.clear();
/*      */         
/* 1406 */         for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
/*      */           
/* 1408 */           if (renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) && k++ < 15)
/*      */           {
/* 1410 */             this.chunksToResortTransparency.add(renderglobal$containerlocalrenderinformation.renderChunk);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1415 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1418 */     this.mc.mcProfiler.startSection("filterempty");
/* 1419 */     int l = 0;
/* 1420 */     boolean flag = (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT);
/* 1421 */     int i1 = flag ? (this.renderInfos.size() - 1) : 0;
/* 1422 */     int i = flag ? -1 : this.renderInfos.size();
/* 1423 */     int j1 = flag ? -1 : 1;
/*      */     int j;
/* 1425 */     for (j = i1; j != i; j += j1) {
/*      */       
/* 1427 */       RenderChunk renderchunk = ((ContainerLocalRenderInformation)this.renderInfos.get(j)).renderChunk;
/*      */       
/* 1429 */       if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) {
/*      */         
/* 1431 */         l++;
/* 1432 */         this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
/*      */       } 
/*      */     } 
/*      */     
/* 1436 */     if (l == 0) {
/*      */       
/* 1438 */       this.mc.mcProfiler.endSection();
/* 1439 */       return l;
/*      */     } 
/*      */ 
/*      */     
/* 1443 */     if (Config.isFogOff() && this.mc.entityRenderer.fogStandard)
/*      */     {
/* 1445 */       GlStateManager.disableFog();
/*      */     }
/*      */     
/* 1448 */     this.mc.mcProfiler.endStartSection("render_" + blockLayerIn);
/* 1449 */     renderBlockLayer(blockLayerIn);
/* 1450 */     this.mc.mcProfiler.endSection();
/* 1451 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderBlockLayer(EnumWorldBlockLayer blockLayerIn) {
/* 1458 */     this.mc.entityRenderer.enableLightmap();
/*      */     
/* 1460 */     if (OpenGlHelper.useVbo()) {
/*      */       
/* 1462 */       GL11.glEnableClientState(32884);
/* 1463 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1464 */       GL11.glEnableClientState(32888);
/* 1465 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1466 */       GL11.glEnableClientState(32888);
/* 1467 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1468 */       GL11.glEnableClientState(32886);
/*      */     } 
/*      */     
/* 1471 */     if (Config.isShaders())
/*      */     {
/* 1473 */       ShadersRender.preRenderChunkLayer(blockLayerIn);
/*      */     }
/*      */     
/* 1476 */     this.renderContainer.renderChunkLayer(blockLayerIn);
/*      */     
/* 1478 */     if (Config.isShaders())
/*      */     {
/* 1480 */       ShadersRender.postRenderChunkLayer(blockLayerIn);
/*      */     }
/*      */     
/* 1483 */     if (OpenGlHelper.useVbo())
/*      */     {
/* 1485 */       for (VertexFormatElement vertexformatelement : DefaultVertexFormats.BLOCK.getElements()) {
/*      */         
/* 1487 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
/* 1488 */         int i = vertexformatelement.getIndex();
/*      */         
/* 1490 */         switch (vertexformatelement$enumusage) {
/*      */           
/*      */           case POSITION:
/* 1493 */             GL11.glDisableClientState(32884);
/*      */ 
/*      */           
/*      */           case UV:
/* 1497 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 1498 */             GL11.glDisableClientState(32888);
/* 1499 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */ 
/*      */           
/*      */           case COLOR:
/* 1503 */             GL11.glDisableClientState(32886);
/* 1504 */             GlStateManager.resetColor();
/*      */         } 
/*      */       
/*      */       } 
/*      */     }
/* 1509 */     this.mc.entityRenderer.disableLightmap();
/*      */   }
/*      */ 
/*      */   
/*      */   private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iteratorIn) {
/* 1514 */     while (iteratorIn.hasNext()) {
/*      */       
/* 1516 */       DestroyBlockProgress destroyblockprogress = iteratorIn.next();
/* 1517 */       int i = destroyblockprogress.getCreationCloudUpdateTick();
/*      */       
/* 1519 */       if (this.cloudTickCounter - i > 400)
/*      */       {
/* 1521 */         iteratorIn.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateClouds() {
/* 1528 */     if (Config.isShaders()) {
/*      */       
/* 1530 */       if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(24)) {
/*      */         
/* 1532 */         GuiShaderOptions guishaderoptions = new GuiShaderOptions((GuiScreen)null, Config.getGameSettings());
/* 1533 */         Config.getMinecraft().displayGuiScreen((GuiScreen)guishaderoptions);
/*      */       } 
/*      */       
/* 1536 */       if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
/*      */         
/* 1538 */         Shaders.uninit();
/* 1539 */         Shaders.loadShaderPack();
/*      */       } 
/*      */     } 
/*      */     
/* 1543 */     this.cloudTickCounter++;
/*      */     
/* 1545 */     if (this.cloudTickCounter % 20 == 0)
/*      */     {
/* 1547 */       cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSkyEnd() {
/* 1553 */     if (Config.isSkyEnabled()) {
/*      */       
/* 1555 */       GlStateManager.disableFog();
/* 1556 */       GlStateManager.disableAlpha();
/* 1557 */       GlStateManager.enableBlend();
/* 1558 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1559 */       RenderHelper.disableStandardItemLighting();
/* 1560 */       GlStateManager.depthMask(false);
/* 1561 */       this.renderEngine.bindTexture(locationEndSkyPng);
/* 1562 */       Tessellator tessellator = Tessellator.getInstance();
/* 1563 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */       
/* 1565 */       for (int i = 0; i < 6; i++) {
/*      */         
/* 1567 */         GlStateManager.pushMatrix();
/*      */         
/* 1569 */         if (i == 1)
/*      */         {
/* 1571 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1574 */         if (i == 2)
/*      */         {
/* 1576 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1579 */         if (i == 3)
/*      */         {
/* 1581 */           GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1584 */         if (i == 4)
/*      */         {
/* 1586 */           GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1589 */         if (i == 5)
/*      */         {
/* 1591 */           GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1594 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 1595 */         int j = 40;
/* 1596 */         int k = 40;
/* 1597 */         int l = 40;
/*      */         
/* 1599 */         if (Config.isCustomColors()) {
/*      */           
/* 1601 */           Vec3 vec3 = new Vec3(j / 255.0D, k / 255.0D, l / 255.0D);
/* 1602 */           vec3 = CustomColors.getWorldSkyColor(vec3, (World)this.theWorld, this.mc.getRenderViewEntity(), 0.0F);
/* 1603 */           j = (int)(vec3.xCoord * 255.0D);
/* 1604 */           k = (int)(vec3.yCoord * 255.0D);
/* 1605 */           l = (int)(vec3.zCoord * 255.0D);
/*      */         } 
/*      */         
/* 1608 */         worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(j, k, l, 255).endVertex();
/* 1609 */         worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(j, k, l, 255).endVertex();
/* 1610 */         worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(j, k, l, 255).endVertex();
/* 1611 */         worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(j, k, l, 255).endVertex();
/* 1612 */         tessellator.draw();
/* 1613 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/* 1616 */       GlStateManager.depthMask(true);
/* 1617 */       GlStateManager.enableTexture2D();
/* 1618 */       GlStateManager.enableAlpha();
/* 1619 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderSky(float partialTicks, int pass) {
/* 1625 */     if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
/*      */       
/* 1627 */       WorldProvider worldprovider = this.mc.theWorld.provider;
/* 1628 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);
/*      */       
/* 1630 */       if (object != null) {
/*      */         
/* 1632 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1637 */     if (this.mc.theWorld.provider.getDimensionId() == 1) {
/*      */       
/* 1639 */       renderSkyEnd();
/*      */     }
/* 1641 */     else if (this.mc.theWorld.provider.isSurfaceWorld()) {
/*      */       
/* 1643 */       GlStateManager.disableTexture2D();
/* 1644 */       boolean flag = Config.isShaders();
/*      */       
/* 1646 */       if (flag)
/*      */       {
/* 1648 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1651 */       Vec3 vec3 = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 1652 */       vec3 = CustomColors.getSkyColor(vec3, (IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 1654 */       if (flag)
/*      */       {
/* 1656 */         Shaders.setSkyColor(vec3);
/*      */       }
/*      */       
/* 1659 */       float f = (float)vec3.xCoord;
/* 1660 */       float f1 = (float)vec3.yCoord;
/* 1661 */       float f2 = (float)vec3.zCoord;
/*      */       
/* 1663 */       if (pass != 2) {
/*      */         
/* 1665 */         float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 1666 */         float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 1667 */         float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 1668 */         f = f3;
/* 1669 */         f1 = f4;
/* 1670 */         f2 = f5;
/*      */       } 
/*      */       
/* 1673 */       GlStateManager.color(f, f1, f2);
/* 1674 */       Tessellator tessellator = Tessellator.getInstance();
/* 1675 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1676 */       GlStateManager.depthMask(false);
/* 1677 */       GlStateManager.enableFog();
/*      */       
/* 1679 */       if (flag)
/*      */       {
/* 1681 */         Shaders.enableFog();
/*      */       }
/*      */       
/* 1684 */       GlStateManager.color(f, f1, f2);
/*      */       
/* 1686 */       if (flag)
/*      */       {
/* 1688 */         Shaders.preSkyList();
/*      */       }
/*      */       
/* 1691 */       if (Config.isSkyEnabled())
/*      */       {
/* 1693 */         if (this.vboEnabled) {
/*      */           
/* 1695 */           this.skyVBO.bindBuffer();
/* 1696 */           GL11.glEnableClientState(32884);
/* 1697 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1698 */           this.skyVBO.drawArrays(7);
/* 1699 */           this.skyVBO.unbindBuffer();
/* 1700 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1704 */           GlStateManager.callList(this.glSkyList);
/*      */         } 
/*      */       }
/*      */       
/* 1708 */       GlStateManager.disableFog();
/*      */       
/* 1710 */       if (flag)
/*      */       {
/* 1712 */         Shaders.disableFog();
/*      */       }
/*      */       
/* 1715 */       GlStateManager.disableAlpha();
/* 1716 */       GlStateManager.enableBlend();
/* 1717 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1718 */       RenderHelper.disableStandardItemLighting();
/* 1719 */       float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);
/*      */       
/* 1721 */       if (afloat != null && Config.isSunMoonEnabled()) {
/*      */         
/* 1723 */         GlStateManager.disableTexture2D();
/*      */         
/* 1725 */         if (flag)
/*      */         {
/* 1727 */           Shaders.disableTexture2D();
/*      */         }
/*      */         
/* 1730 */         GlStateManager.shadeModel(7425);
/* 1731 */         GlStateManager.pushMatrix();
/* 1732 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 1733 */         GlStateManager.rotate((MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F) ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
/* 1734 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 1735 */         float f6 = afloat[0];
/* 1736 */         float f7 = afloat[1];
/* 1737 */         float f8 = afloat[2];
/*      */         
/* 1739 */         if (pass != 2) {
/*      */           
/* 1741 */           float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
/* 1742 */           float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
/* 1743 */           float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
/* 1744 */           f6 = f9;
/* 1745 */           f7 = f10;
/* 1746 */           f8 = f11;
/*      */         } 
/*      */         
/* 1749 */         worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 1750 */         worldrenderer.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
/*      */         
/* 1752 */         for (int l = 0; l <= 16; l++) {
/*      */           
/* 1754 */           float f18 = l * 3.1415927F * 2.0F / 16.0F;
/* 1755 */           float f12 = MathHelper.sin(f18);
/* 1756 */           float f13 = MathHelper.cos(f18);
/* 1757 */           worldrenderer.pos((f12 * 120.0F), (f13 * 120.0F), (-f13 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
/*      */         } 
/*      */         
/* 1760 */         tessellator.draw();
/* 1761 */         GlStateManager.popMatrix();
/* 1762 */         GlStateManager.shadeModel(7424);
/*      */       } 
/*      */       
/* 1765 */       GlStateManager.enableTexture2D();
/*      */       
/* 1767 */       if (flag)
/*      */       {
/* 1769 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 1772 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 1773 */       GlStateManager.pushMatrix();
/* 1774 */       float f15 = 1.0F - this.theWorld.getRainStrength(partialTicks);
/* 1775 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f15);
/* 1776 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 1777 */       CustomSky.renderSky((World)this.theWorld, this.renderEngine, partialTicks);
/*      */       
/* 1779 */       if (flag)
/*      */       {
/* 1781 */         Shaders.preCelestialRotate();
/*      */       }
/*      */       
/* 1784 */       GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
/*      */       
/* 1786 */       if (flag)
/*      */       {
/* 1788 */         Shaders.postCelestialRotate();
/*      */       }
/*      */       
/* 1791 */       float f16 = 30.0F;
/*      */       
/* 1793 */       if (Config.isSunTexture()) {
/*      */         
/* 1795 */         this.renderEngine.bindTexture(locationSunPng);
/* 1796 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1797 */         worldrenderer.pos(-f16, 100.0D, -f16).tex(0.0D, 0.0D).endVertex();
/* 1798 */         worldrenderer.pos(f16, 100.0D, -f16).tex(1.0D, 0.0D).endVertex();
/* 1799 */         worldrenderer.pos(f16, 100.0D, f16).tex(1.0D, 1.0D).endVertex();
/* 1800 */         worldrenderer.pos(-f16, 100.0D, f16).tex(0.0D, 1.0D).endVertex();
/* 1801 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1804 */       f16 = 20.0F;
/*      */       
/* 1806 */       if (Config.isMoonTexture()) {
/*      */         
/* 1808 */         this.renderEngine.bindTexture(locationMoonPhasesPng);
/* 1809 */         int i = this.theWorld.getMoonPhase();
/* 1810 */         int k = i % 4;
/* 1811 */         int i1 = i / 4 % 2;
/* 1812 */         float f19 = (k + 0) / 4.0F;
/* 1813 */         float f21 = (i1 + 0) / 2.0F;
/* 1814 */         float f23 = (k + 1) / 4.0F;
/* 1815 */         float f14 = (i1 + 1) / 2.0F;
/* 1816 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1817 */         worldrenderer.pos(-f16, -100.0D, f16).tex(f23, f14).endVertex();
/* 1818 */         worldrenderer.pos(f16, -100.0D, f16).tex(f19, f14).endVertex();
/* 1819 */         worldrenderer.pos(f16, -100.0D, -f16).tex(f19, f21).endVertex();
/* 1820 */         worldrenderer.pos(-f16, -100.0D, -f16).tex(f23, f21).endVertex();
/* 1821 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1824 */       GlStateManager.disableTexture2D();
/*      */       
/* 1826 */       if (flag)
/*      */       {
/* 1828 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1831 */       float f17 = this.theWorld.getStarBrightness(partialTicks) * f15;
/*      */       
/* 1833 */       if (f17 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers((World)this.theWorld)) {
/*      */         
/* 1835 */         GlStateManager.color(f17, f17, f17, f17);
/*      */         
/* 1837 */         if (this.vboEnabled) {
/*      */           
/* 1839 */           this.starVBO.bindBuffer();
/* 1840 */           GL11.glEnableClientState(32884);
/* 1841 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1842 */           this.starVBO.drawArrays(7);
/* 1843 */           this.starVBO.unbindBuffer();
/* 1844 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1848 */           GlStateManager.callList(this.starGLCallList);
/*      */         } 
/*      */       } 
/*      */       
/* 1852 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1853 */       GlStateManager.disableBlend();
/* 1854 */       GlStateManager.enableAlpha();
/* 1855 */       GlStateManager.enableFog();
/*      */       
/* 1857 */       if (flag)
/*      */       {
/* 1859 */         Shaders.enableFog();
/*      */       }
/*      */       
/* 1862 */       GlStateManager.popMatrix();
/* 1863 */       GlStateManager.disableTexture2D();
/*      */       
/* 1865 */       if (flag)
/*      */       {
/* 1867 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1870 */       GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 1871 */       double d0 = (this.mc.thePlayer.getPositionEyes(partialTicks)).yCoord - this.theWorld.getHorizon();
/*      */       
/* 1873 */       if (d0 < 0.0D) {
/*      */         
/* 1875 */         GlStateManager.pushMatrix();
/* 1876 */         GlStateManager.translate(0.0F, 12.0F, 0.0F);
/*      */         
/* 1878 */         if (this.vboEnabled) {
/*      */           
/* 1880 */           this.sky2VBO.bindBuffer();
/* 1881 */           GL11.glEnableClientState(32884);
/* 1882 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1883 */           this.sky2VBO.drawArrays(7);
/* 1884 */           this.sky2VBO.unbindBuffer();
/* 1885 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1889 */           GlStateManager.callList(this.glSkyList2);
/*      */         } 
/*      */         
/* 1892 */         GlStateManager.popMatrix();
/* 1893 */         float f22 = -((float)(d0 + 65.0D));
/* 1894 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1895 */         worldrenderer.pos(-1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1896 */         worldrenderer.pos(1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1897 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1898 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1899 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1900 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1901 */         worldrenderer.pos(1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1902 */         worldrenderer.pos(-1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1903 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1904 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1905 */         worldrenderer.pos(1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1906 */         worldrenderer.pos(1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1907 */         worldrenderer.pos(-1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1908 */         worldrenderer.pos(-1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1909 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1910 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1911 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1912 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1913 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1914 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1915 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1918 */       if (this.theWorld.provider.isSkyColored()) {
/*      */         
/* 1920 */         GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
/*      */       }
/*      */       else {
/*      */         
/* 1924 */         GlStateManager.color(f, f1, f2);
/*      */       } 
/*      */       
/* 1927 */       if (this.mc.gameSettings.renderDistanceChunks <= 4)
/*      */       {
/* 1929 */         GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
/*      */       }
/*      */       
/* 1932 */       GlStateManager.pushMatrix();
/* 1933 */       GlStateManager.translate(0.0F, -((float)(d0 - 16.0D)), 0.0F);
/*      */       
/* 1935 */       if (Config.isSkyEnabled())
/*      */       {
/* 1937 */         if (this.vboEnabled) {
/*      */           
/* 1939 */           this.sky2VBO.bindBuffer();
/* 1940 */           GlStateManager.glEnableClientState(32884);
/* 1941 */           GlStateManager.glVertexPointer(3, 5126, 12, 0);
/* 1942 */           this.sky2VBO.drawArrays(7);
/* 1943 */           this.sky2VBO.unbindBuffer();
/* 1944 */           GlStateManager.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1948 */           GlStateManager.callList(this.glSkyList2);
/*      */         } 
/*      */       }
/*      */       
/* 1952 */       GlStateManager.popMatrix();
/* 1953 */       GlStateManager.enableTexture2D();
/*      */       
/* 1955 */       if (flag)
/*      */       {
/* 1957 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 1960 */       GlStateManager.depthMask(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderClouds(float partialTicks, int pass) {
/* 1966 */     if (!Config.isCloudsOff()) {
/*      */       
/* 1968 */       if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
/*      */         
/* 1970 */         WorldProvider worldprovider = this.mc.theWorld.provider;
/* 1971 */         Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
/*      */         
/* 1973 */         if (object != null) {
/*      */           
/* 1975 */           Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 1980 */       if (this.mc.theWorld.provider.isSurfaceWorld()) {
/*      */         
/* 1982 */         if (Config.isShaders())
/*      */         {
/* 1984 */           Shaders.beginClouds();
/*      */         }
/*      */         
/* 1987 */         if (Config.isCloudsFancy()) {
/*      */           
/* 1989 */           renderCloudsFancy(partialTicks, pass);
/*      */         }
/*      */         else {
/*      */           
/* 1993 */           float f9 = partialTicks;
/* 1994 */           partialTicks = 0.0F;
/* 1995 */           GlStateManager.disableCull();
/* 1996 */           float f10 = (float)((this.mc.getRenderViewEntity()).lastTickPosY + ((this.mc.getRenderViewEntity()).posY - (this.mc.getRenderViewEntity()).lastTickPosY) * partialTicks);
/* 1997 */           Tessellator tessellator = Tessellator.getInstance();
/* 1998 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1999 */           this.renderEngine.bindTexture(locationCloudsPng);
/* 2000 */           GlStateManager.enableBlend();
/* 2001 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2002 */           Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
/* 2003 */           float f = (float)vec3.xCoord;
/* 2004 */           float f1 = (float)vec3.yCoord;
/* 2005 */           float f2 = (float)vec3.zCoord;
/* 2006 */           this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, f9, vec3);
/*      */           
/* 2008 */           if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */             
/* 2010 */             this.cloudRenderer.startUpdateGlList();
/*      */             
/* 2012 */             if (pass != 2) {
/*      */               
/* 2014 */               float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 2015 */               float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 2016 */               float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 2017 */               f = f3;
/* 2018 */               f1 = f4;
/* 2019 */               f2 = f5;
/*      */             } 
/*      */             
/* 2022 */             float f11 = 4.8828125E-4F;
/* 2023 */             double d2 = (this.cloudTickCounter + partialTicks);
/* 2024 */             double d0 = (this.mc.getRenderViewEntity()).prevPosX + ((this.mc.getRenderViewEntity()).posX - (this.mc.getRenderViewEntity()).prevPosX) * partialTicks + d2 * 0.029999999329447746D;
/* 2025 */             double d1 = (this.mc.getRenderViewEntity()).prevPosZ + ((this.mc.getRenderViewEntity()).posZ - (this.mc.getRenderViewEntity()).prevPosZ) * partialTicks;
/* 2026 */             int k = MathHelper.floor_double(d0 / 2048.0D);
/* 2027 */             int l = MathHelper.floor_double(d1 / 2048.0D);
/* 2028 */             d0 -= (k * 2048);
/* 2029 */             d1 -= (l * 2048);
/* 2030 */             float f6 = this.theWorld.provider.getCloudHeight() - f10 + 0.33F;
/* 2031 */             f6 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 2032 */             float f7 = (float)(d0 * 4.8828125E-4D);
/* 2033 */             float f8 = (float)(d1 * 4.8828125E-4D);
/* 2034 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*      */             
/* 2036 */             for (int i1 = -256; i1 < 256; i1 += 32) {
/*      */               
/* 2038 */               for (int j1 = -256; j1 < 256; j1 += 32) {
/*      */                 
/* 2040 */                 worldrenderer.pos((i1 + 0), f6, (j1 + 32)).tex(((i1 + 0) * 4.8828125E-4F + f7), ((j1 + 32) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 2041 */                 worldrenderer.pos((i1 + 32), f6, (j1 + 32)).tex(((i1 + 32) * 4.8828125E-4F + f7), ((j1 + 32) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 2042 */                 worldrenderer.pos((i1 + 32), f6, (j1 + 0)).tex(((i1 + 32) * 4.8828125E-4F + f7), ((j1 + 0) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 2043 */                 worldrenderer.pos((i1 + 0), f6, (j1 + 0)).tex(((i1 + 0) * 4.8828125E-4F + f7), ((j1 + 0) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/*      */               } 
/*      */             } 
/*      */             
/* 2047 */             tessellator.draw();
/* 2048 */             this.cloudRenderer.endUpdateGlList();
/*      */           } 
/*      */           
/* 2051 */           this.cloudRenderer.renderGlList();
/* 2052 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2053 */           GlStateManager.disableBlend();
/* 2054 */           GlStateManager.enableCull();
/*      */         } 
/*      */         
/* 2057 */         if (Config.isShaders())
/*      */         {
/* 2059 */           Shaders.endClouds();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
/* 2070 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCloudsFancy(float partialTicks, int pass) {
/* 2075 */     partialTicks = 0.0F;
/* 2076 */     GlStateManager.disableCull();
/* 2077 */     float f = (float)((this.mc.getRenderViewEntity()).lastTickPosY + ((this.mc.getRenderViewEntity()).posY - (this.mc.getRenderViewEntity()).lastTickPosY) * partialTicks);
/* 2078 */     Tessellator tessellator = Tessellator.getInstance();
/* 2079 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/* 2081 */     double d0 = (this.cloudTickCounter + partialTicks);
/* 2082 */     double d1 = ((this.mc.getRenderViewEntity()).prevPosX + ((this.mc.getRenderViewEntity()).posX - (this.mc.getRenderViewEntity()).prevPosX) * partialTicks + d0 * 0.029999999329447746D) / 12.0D;
/* 2083 */     double d2 = ((this.mc.getRenderViewEntity()).prevPosZ + ((this.mc.getRenderViewEntity()).posZ - (this.mc.getRenderViewEntity()).prevPosZ) * partialTicks) / 12.0D + 0.33000001311302185D;
/* 2084 */     float f3 = this.theWorld.provider.getCloudHeight() - f + 0.33F;
/* 2085 */     f3 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 2086 */     int i = MathHelper.floor_double(d1 / 2048.0D);
/* 2087 */     int j = MathHelper.floor_double(d2 / 2048.0D);
/* 2088 */     d1 -= (i * 2048);
/* 2089 */     d2 -= (j * 2048);
/* 2090 */     this.renderEngine.bindTexture(locationCloudsPng);
/* 2091 */     GlStateManager.enableBlend();
/* 2092 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2093 */     Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
/* 2094 */     float f4 = (float)vec3.xCoord;
/* 2095 */     float f5 = (float)vec3.yCoord;
/* 2096 */     float f6 = (float)vec3.zCoord;
/* 2097 */     this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks, vec3);
/*      */     
/* 2099 */     if (pass != 2) {
/*      */       
/* 2101 */       float f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
/* 2102 */       float f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
/* 2103 */       float f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
/* 2104 */       f4 = f7;
/* 2105 */       f5 = f8;
/* 2106 */       f6 = f9;
/*      */     } 
/*      */     
/* 2109 */     float f26 = f4 * 0.9F;
/* 2110 */     float f27 = f5 * 0.9F;
/* 2111 */     float f28 = f6 * 0.9F;
/* 2112 */     float f10 = f4 * 0.7F;
/* 2113 */     float f11 = f5 * 0.7F;
/* 2114 */     float f12 = f6 * 0.7F;
/* 2115 */     float f13 = f4 * 0.8F;
/* 2116 */     float f14 = f5 * 0.8F;
/* 2117 */     float f15 = f6 * 0.8F;
/*      */     
/* 2119 */     float f17 = MathHelper.floor_double(d1) * 0.00390625F;
/* 2120 */     float f18 = MathHelper.floor_double(d2) * 0.00390625F;
/* 2121 */     float f19 = (float)(d1 - MathHelper.floor_double(d1));
/* 2122 */     float f20 = (float)(d2 - MathHelper.floor_double(d2));
/*      */     
/* 2124 */     GlStateManager.scale(12.0F, 1.0F, 12.0F);
/*      */     
/* 2126 */     for (int i1 = 0; i1 < 2; i1++) {
/*      */       
/* 2128 */       if (i1 == 0) {
/*      */         
/* 2130 */         GlStateManager.colorMask(false, false, false, false);
/*      */       }
/*      */       else {
/*      */         
/* 2134 */         switch (pass) {
/*      */           
/*      */           case 0:
/* 2137 */             GlStateManager.colorMask(false, true, true, true);
/*      */             break;
/*      */           
/*      */           case 1:
/* 2141 */             GlStateManager.colorMask(true, false, false, true);
/*      */             break;
/*      */           
/*      */           case 2:
/* 2145 */             GlStateManager.colorMask(true, true, true, true);
/*      */             break;
/*      */         } 
/*      */       } 
/* 2149 */       this.cloudRenderer.renderGlList();
/*      */     } 
/*      */     
/* 2152 */     if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */       
/* 2154 */       this.cloudRenderer.startUpdateGlList();
/*      */       
/* 2156 */       for (int l1 = -3; l1 <= 4; l1++) {
/*      */         
/* 2158 */         for (int j1 = -3; j1 <= 4; j1++) {
/*      */           
/* 2160 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/* 2161 */           float f22 = (l1 * 8);
/* 2162 */           float f23 = (j1 * 8);
/* 2163 */           float f24 = f22 - f19;
/* 2164 */           float f25 = f23 - f20;
/*      */           
/* 2166 */           if (f3 > -5.0F) {
/*      */             
/* 2168 */             worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2169 */             worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2170 */             worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2171 */             worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2174 */           if (f3 <= 5.0F) {
/*      */             
/* 2176 */             worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 8.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2177 */             worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 8.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2178 */             worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2179 */             worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2182 */           if (l1 > -1)
/*      */           {
/* 2184 */             for (int k1 = 0; k1 < 8; k1++) {
/*      */               
/* 2186 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2187 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 4.0F), (f25 + 8.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2188 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 4.0F), (f25 + 0.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2189 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2193 */           if (l1 <= 1)
/*      */           {
/* 2195 */             for (int i2 = 0; i2 < 8; i2++) {
/*      */               
/* 2197 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2198 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 4.0F), (f25 + 8.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2199 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 4.0F), (f25 + 0.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2200 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2204 */           if (j1 > -1)
/*      */           {
/* 2206 */             for (int j2 = 0; j2 < 8; j2++) {
/*      */               
/* 2208 */               worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F), (f25 + j2 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2209 */               worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F), (f25 + j2 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2210 */               worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + j2 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2211 */               worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + j2 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2215 */           if (j1 <= 1)
/*      */           {
/* 2217 */             for (int k2 = 0; k2 < 8; k2++) {
/*      */               
/* 2219 */               worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2220 */               worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2221 */               worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2222 */               worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2226 */           tessellator.draw();
/*      */         } 
/*      */       } 
/*      */       
/* 2230 */       this.cloudRenderer.endUpdateGlList();
/*      */     } 
/*      */     
/* 2233 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2234 */     GlStateManager.disableBlend();
/* 2235 */     GlStateManager.enableCull();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateChunks(long finishTimeNano) {
/* 2240 */     finishTimeNano = (long)(finishTimeNano + 1.0E8D);
/* 2241 */     this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(finishTimeNano);
/*      */     
/* 2243 */     if (this.chunksToUpdateForced.size() > 0) {
/*      */       
/* 2245 */       Iterator<RenderChunk> iterator = this.chunksToUpdateForced.iterator();
/*      */       
/* 2247 */       while (iterator.hasNext()) {
/*      */         
/* 2249 */         RenderChunk renderchunk = iterator.next();
/*      */         
/* 2251 */         if (!this.renderDispatcher.updateChunkLater(renderchunk)) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2256 */         renderchunk.setNeedsUpdate(false);
/* 2257 */         iterator.remove();
/* 2258 */         this.chunksToUpdate.remove(renderchunk);
/* 2259 */         this.chunksToResortTransparency.remove(renderchunk);
/*      */       } 
/*      */     } 
/*      */     
/* 2263 */     if (this.chunksToResortTransparency.size() > 0) {
/*      */       
/* 2265 */       Iterator<RenderChunk> iterator2 = this.chunksToResortTransparency.iterator();
/*      */       
/* 2267 */       if (iterator2.hasNext()) {
/*      */         
/* 2269 */         RenderChunk renderchunk2 = iterator2.next();
/*      */         
/* 2271 */         if (this.renderDispatcher.updateTransparencyLater(renderchunk2))
/*      */         {
/* 2273 */           iterator2.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2278 */     double d1 = 0.0D;
/* 2279 */     int i = Config.getUpdatesPerFrame();
/*      */     
/* 2281 */     if (!this.chunksToUpdate.isEmpty()) {
/*      */       
/* 2283 */       Iterator<RenderChunk> iterator1 = this.chunksToUpdate.iterator();
/*      */       
/* 2285 */       while (iterator1.hasNext()) {
/*      */         boolean flag1;
/* 2287 */         RenderChunk renderchunk1 = iterator1.next();
/* 2288 */         boolean flag = renderchunk1.isChunkRegionEmpty();
/*      */ 
/*      */         
/* 2291 */         if (flag) {
/*      */           
/* 2293 */           flag1 = this.renderDispatcher.updateChunkNow(renderchunk1);
/*      */         }
/*      */         else {
/*      */           
/* 2297 */           flag1 = this.renderDispatcher.updateChunkLater(renderchunk1);
/*      */         } 
/*      */         
/* 2300 */         if (!flag1) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2305 */         renderchunk1.setNeedsUpdate(false);
/* 2306 */         iterator1.remove();
/*      */         
/* 2308 */         if (!flag) {
/*      */           
/* 2310 */           double d0 = 2.0D * RenderChunkUtils.getRelativeBufferSize(renderchunk1);
/* 2311 */           d1 += d0;
/*      */           
/* 2313 */           if (d1 > i) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderWorldBorder(Entity entityIn, float partialTicks) {
/* 2324 */     Tessellator tessellator = Tessellator.getInstance();
/* 2325 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2326 */     WorldBorder worldborder = this.theWorld.getWorldBorder();
/* 2327 */     double d0 = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/* 2329 */     if (entityIn.posX >= worldborder.maxX() - d0 || entityIn.posX <= worldborder.minX() + d0 || entityIn.posZ >= worldborder.maxZ() - d0 || entityIn.posZ <= worldborder.minZ() + d0) {
/*      */       
/* 2331 */       if (Config.isShaders()) {
/*      */         
/* 2333 */         Shaders.pushProgram();
/* 2334 */         Shaders.useProgram(Shaders.ProgramTexturedLit);
/*      */       } 
/*      */       
/* 2337 */       double d1 = 1.0D - worldborder.getClosestDistance(entityIn) / d0;
/* 2338 */       d1 = Math.pow(d1, 4.0D);
/* 2339 */       double d2 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 2340 */       double d3 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 2341 */       double d4 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 2342 */       GlStateManager.enableBlend();
/* 2343 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 2344 */       this.renderEngine.bindTexture(locationForcefieldPng);
/* 2345 */       GlStateManager.depthMask(false);
/* 2346 */       GlStateManager.pushMatrix();
/* 2347 */       int i = worldborder.getStatus().getID();
/* 2348 */       float f = (i >> 16 & 0xFF) / 255.0F;
/* 2349 */       float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 2350 */       float f2 = (i & 0xFF) / 255.0F;
/* 2351 */       GlStateManager.color(f, f1, f2, (float)d1);
/* 2352 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 2353 */       GlStateManager.enablePolygonOffset();
/* 2354 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2355 */       GlStateManager.enableAlpha();
/* 2356 */       GlStateManager.disableCull();
/* 2357 */       float f3 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F;
/*      */       
/* 2359 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 2360 */       worldrenderer.setTranslation(-d2, -d3, -d4);
/* 2361 */       double d5 = Math.max(MathHelper.floor_double(d4 - d0), worldborder.minZ());
/* 2362 */       double d6 = Math.min(MathHelper.ceiling_double_int(d4 + d0), worldborder.maxZ());
/*      */       
/* 2364 */       if (d2 > worldborder.maxX() - d0) {
/*      */         
/* 2366 */         float f7 = 0.0F;
/*      */         
/* 2368 */         for (double d7 = d5; d7 < d6; f7 += 0.5F) {
/*      */           
/* 2370 */           double d8 = Math.min(1.0D, d6 - d7);
/* 2371 */           float f8 = (float)d8 * 0.5F;
/* 2372 */           worldrenderer.pos(worldborder.maxX(), 256.0D, d7).tex((f3 + f7), (f3 + 0.0F)).endVertex();
/* 2373 */           worldrenderer.pos(worldborder.maxX(), 256.0D, d7 + d8).tex((f3 + f8 + f7), (f3 + 0.0F)).endVertex();
/* 2374 */           worldrenderer.pos(worldborder.maxX(), 0.0D, d7 + d8).tex((f3 + f8 + f7), (f3 + 128.0F)).endVertex();
/* 2375 */           worldrenderer.pos(worldborder.maxX(), 0.0D, d7).tex((f3 + f7), (f3 + 128.0F)).endVertex();
/* 2376 */           d7++;
/*      */         } 
/*      */       } 
/*      */       
/* 2380 */       if (d2 < worldborder.minX() + d0) {
/*      */         
/* 2382 */         float f9 = 0.0F;
/*      */         
/* 2384 */         for (double d9 = d5; d9 < d6; f9 += 0.5F) {
/*      */           
/* 2386 */           double d12 = Math.min(1.0D, d6 - d9);
/* 2387 */           float f12 = (float)d12 * 0.5F;
/* 2388 */           worldrenderer.pos(worldborder.minX(), 256.0D, d9).tex((f3 + f9), (f3 + 0.0F)).endVertex();
/* 2389 */           worldrenderer.pos(worldborder.minX(), 256.0D, d9 + d12).tex((f3 + f12 + f9), (f3 + 0.0F)).endVertex();
/* 2390 */           worldrenderer.pos(worldborder.minX(), 0.0D, d9 + d12).tex((f3 + f12 + f9), (f3 + 128.0F)).endVertex();
/* 2391 */           worldrenderer.pos(worldborder.minX(), 0.0D, d9).tex((f3 + f9), (f3 + 128.0F)).endVertex();
/* 2392 */           d9++;
/*      */         } 
/*      */       } 
/*      */       
/* 2396 */       d5 = Math.max(MathHelper.floor_double(d2 - d0), worldborder.minX());
/* 2397 */       d6 = Math.min(MathHelper.ceiling_double_int(d2 + d0), worldborder.maxX());
/*      */       
/* 2399 */       if (d4 > worldborder.maxZ() - d0) {
/*      */         
/* 2401 */         float f10 = 0.0F;
/*      */         
/* 2403 */         for (double d10 = d5; d10 < d6; f10 += 0.5F) {
/*      */           
/* 2405 */           double d13 = Math.min(1.0D, d6 - d10);
/* 2406 */           float f13 = (float)d13 * 0.5F;
/* 2407 */           worldrenderer.pos(d10, 256.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 0.0F)).endVertex();
/* 2408 */           worldrenderer.pos(d10 + d13, 256.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 0.0F)).endVertex();
/* 2409 */           worldrenderer.pos(d10 + d13, 0.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 128.0F)).endVertex();
/* 2410 */           worldrenderer.pos(d10, 0.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 128.0F)).endVertex();
/* 2411 */           d10++;
/*      */         } 
/*      */       } 
/*      */       
/* 2415 */       if (d4 < worldborder.minZ() + d0) {
/*      */         
/* 2417 */         float f11 = 0.0F;
/*      */         
/* 2419 */         for (double d11 = d5; d11 < d6; f11 += 0.5F) {
/*      */           
/* 2421 */           double d14 = Math.min(1.0D, d6 - d11);
/* 2422 */           float f14 = (float)d14 * 0.5F;
/* 2423 */           worldrenderer.pos(d11, 256.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 0.0F)).endVertex();
/* 2424 */           worldrenderer.pos(d11 + d14, 256.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 0.0F)).endVertex();
/* 2425 */           worldrenderer.pos(d11 + d14, 0.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 128.0F)).endVertex();
/* 2426 */           worldrenderer.pos(d11, 0.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 128.0F)).endVertex();
/* 2427 */           d11++;
/*      */         } 
/*      */       } 
/*      */       
/* 2431 */       tessellator.draw();
/* 2432 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 2433 */       GlStateManager.enableCull();
/* 2434 */       GlStateManager.disableAlpha();
/* 2435 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2436 */       GlStateManager.disablePolygonOffset();
/* 2437 */       GlStateManager.enableAlpha();
/* 2438 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2439 */       GlStateManager.disableBlend();
/* 2440 */       GlStateManager.popMatrix();
/* 2441 */       GlStateManager.depthMask(true);
/*      */       
/* 2443 */       if (Config.isShaders())
/*      */       {
/* 2445 */         Shaders.popProgram();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void preRenderDamagedBlocks() {
/* 2452 */     GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
/* 2453 */     GlStateManager.enableBlend();
/* 2454 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
/* 2455 */     GlStateManager.doPolygonOffset(-1.0F, -10.0F);
/* 2456 */     GlStateManager.enablePolygonOffset();
/* 2457 */     GlStateManager.alphaFunc(516, 0.1F);
/* 2458 */     GlStateManager.enableAlpha();
/* 2459 */     GlStateManager.pushMatrix();
/*      */     
/* 2461 */     if (Config.isShaders())
/*      */     {
/* 2463 */       ShadersRender.beginBlockDamage();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void postRenderDamagedBlocks() {
/* 2469 */     GlStateManager.disableAlpha();
/* 2470 */     GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2471 */     GlStateManager.disablePolygonOffset();
/* 2472 */     GlStateManager.enableAlpha();
/* 2473 */     GlStateManager.depthMask(true);
/* 2474 */     GlStateManager.popMatrix();
/*      */     
/* 2476 */     if (Config.isShaders())
/*      */     {
/* 2478 */       ShadersRender.endBlockDamage();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawBlockDamageTexture(Tessellator tessellatorIn, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks) {
/* 2484 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 2485 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 2486 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/*      */     
/* 2488 */     if (!this.damagedBlocks.isEmpty()) {
/*      */       
/* 2490 */       this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 2491 */       preRenderDamagedBlocks();
/* 2492 */       worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/* 2493 */       worldRendererIn.setTranslation(-d0, -d1, -d2);
/* 2494 */       worldRendererIn.noColor();
/* 2495 */       Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
/*      */       
/* 2497 */       while (iterator.hasNext()) {
/*      */         boolean flag;
/* 2499 */         DestroyBlockProgress destroyblockprogress = iterator.next();
/* 2500 */         BlockPos blockpos = destroyblockprogress.getPosition();
/* 2501 */         double d3 = blockpos.getX() - d0;
/* 2502 */         double d4 = blockpos.getY() - d1;
/* 2503 */         double d5 = blockpos.getZ() - d2;
/* 2504 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */ 
/*      */         
/* 2507 */         if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
/*      */           
/* 2509 */           boolean flag1 = (block instanceof net.minecraft.block.BlockChest || block instanceof net.minecraft.block.BlockEnderChest || block instanceof net.minecraft.block.BlockSign || block instanceof net.minecraft.block.BlockSkull);
/*      */           
/* 2511 */           if (!flag1) {
/*      */             
/* 2513 */             TileEntity tileentity = this.theWorld.getTileEntity(blockpos);
/*      */             
/* 2515 */             if (tileentity != null)
/*      */             {
/* 2517 */               flag1 = Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
/*      */             }
/*      */           } 
/*      */           
/* 2521 */           flag = !flag1;
/*      */         }
/*      */         else {
/*      */           
/* 2525 */           flag = (!(block instanceof net.minecraft.block.BlockChest) && !(block instanceof net.minecraft.block.BlockEnderChest) && !(block instanceof net.minecraft.block.BlockSign) && !(block instanceof net.minecraft.block.BlockSkull));
/*      */         } 
/*      */         
/* 2528 */         if (flag) {
/*      */           
/* 2530 */           if (d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D) {
/*      */             
/* 2532 */             iterator.remove();
/*      */             
/*      */             continue;
/*      */           } 
/* 2536 */           IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
/*      */           
/* 2538 */           if (iblockstate.getBlock().getMaterial() != Material.air) {
/*      */             
/* 2540 */             int i = destroyblockprogress.getPartialBlockDamage();
/* 2541 */             TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[i];
/* 2542 */             BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/* 2543 */             blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite, (IBlockAccess)this.theWorld);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2549 */       tessellatorIn.draw();
/* 2550 */       worldRendererIn.setTranslation(0.0D, 0.0D, 0.0D);
/* 2551 */       postRenderDamagedBlocks();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int execute, float partialTicks) {
/* 2562 */     if (execute == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */       
/* 2564 */       GlStateManager.enableBlend();
/* 2565 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2566 */       GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
/* 2567 */       GL11.glLineWidth(2.0F);
/* 2568 */       GlStateManager.disableTexture2D();
/*      */       
/* 2570 */       if (Config.isShaders())
/*      */       {
/* 2572 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 2575 */       GlStateManager.depthMask(false);
/*      */       
/* 2577 */       BlockPos blockpos = movingObjectPositionIn.getBlockPos();
/* 2578 */       Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */       
/* 2580 */       if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockpos)) {
/*      */         
/* 2582 */         block.setBlockBoundsBasedOnState((IBlockAccess)this.theWorld, blockpos);
/* 2583 */         double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
/* 2584 */         double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
/* 2585 */         double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
/* 2586 */         AxisAlignedBB axisalignedbb = block.getSelectedBoundingBox((World)this.theWorld, blockpos);
/* 2587 */         Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*      */         
/* 2589 */         if (block$enumoffsettype != Block.EnumOffsetType.NONE)
/*      */         {
/* 2591 */           axisalignedbb = BlockModelUtils.getOffsetBoundingBox(axisalignedbb, block$enumoffsettype, blockpos);
/*      */         }
/*      */         
/* 2594 */         drawSelectionBoundingBox(axisalignedbb.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2));
/*      */       } 
/*      */       
/* 2597 */       GlStateManager.depthMask(true);
/* 2598 */       GlStateManager.enableTexture2D();
/*      */       
/* 2600 */       if (Config.isShaders())
/*      */       {
/* 2602 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 2605 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
/* 2611 */     Tessellator tessellator = Tessellator.getInstance();
/* 2612 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2613 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION);
/* 2614 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
/* 2615 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
/* 2616 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
/* 2617 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
/* 2618 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
/* 2619 */     tessellator.draw();
/* 2620 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION);
/* 2621 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
/* 2622 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
/* 2623 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/* 2624 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/* 2625 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
/* 2626 */     tessellator.draw();
/* 2627 */     worldrenderer.begin(1, DefaultVertexFormats.POSITION);
/* 2628 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
/* 2629 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
/* 2630 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
/* 2631 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
/* 2632 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
/* 2633 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/* 2634 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
/* 2635 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/* 2636 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawOutlinedBoundingBox(AxisAlignedBB boundingBox, int red, int green, int blue, int alpha) {
/* 2641 */     Tessellator tessellator = Tessellator.getInstance();
/* 2642 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2643 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 2644 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2645 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2646 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2647 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2648 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2649 */     tessellator.draw();
/* 2650 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 2651 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2652 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2653 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2654 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2655 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2656 */     tessellator.draw();
/* 2657 */     worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
/* 2658 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2659 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2660 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2661 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
/* 2662 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2663 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2664 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2665 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
/* 2666 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void markBlocksForUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 2674 */     this.viewFrustum.markBlocksForUpdate(x1, y1, z1, x2, y2, z2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markBlockForUpdate(BlockPos pos) {
/* 2679 */     int i = pos.getX();
/* 2680 */     int j = pos.getY();
/* 2681 */     int k = pos.getZ();
/* 2682 */     markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/* 2687 */     int i = pos.getX();
/* 2688 */     int j = pos.getY();
/* 2689 */     int k = pos.getZ();
/* 2690 */     markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 2699 */     markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playRecord(String recordName, BlockPos blockPosIn) {
/* 2704 */     ISound isound = this.mapSoundPositions.get(blockPosIn);
/*      */     
/* 2706 */     if (isound != null) {
/*      */       
/* 2708 */       this.mc.getSoundHandler().stopSound(isound);
/* 2709 */       this.mapSoundPositions.remove(blockPosIn);
/*      */     } 
/*      */     
/* 2712 */     if (recordName != null) {
/*      */       
/* 2714 */       ItemRecord itemrecord = ItemRecord.getRecord(recordName);
/*      */       
/* 2716 */       if (itemrecord != null)
/*      */       {
/* 2718 */         this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
/*      */       }
/*      */       
/* 2721 */       PositionedSoundRecord positionedsoundrecord = PositionedSoundRecord.create(new ResourceLocation(recordName), blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
/* 2722 */       this.mapSoundPositions.put(blockPosIn, positionedsoundrecord);
/* 2723 */       this.mc.getSoundHandler().playSound((ISound)positionedsoundrecord);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(int particleID, boolean ignoreRange, final double xCoord, final double yCoord, final double zCoord, double xOffset, double yOffset, double zOffset, int... parameters) {
/*      */     try {
/* 2745 */       spawnEntityFX(particleID, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, parameters);
/*      */     }
/* 2747 */     catch (Throwable throwable) {
/*      */       
/* 2749 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
/* 2750 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
/* 2751 */       crashreportcategory.addCrashSection("ID", Integer.valueOf(particleID));
/*      */       
/* 2753 */       if (parameters != null)
/*      */       {
/* 2755 */         crashreportcategory.addCrashSection("Parameters", parameters);
/*      */       }
/*      */       
/* 2758 */       crashreportcategory.addCrashSectionCallable("Position", new Callable<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/* 2762 */               return CrashReportCategory.getCoordinateInfo(xCoord, yCoord, zCoord);
/*      */             }
/*      */           });
/* 2765 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticle(EnumParticleTypes particleIn, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... parameters) {
/* 2771 */     spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, parameters);
/*      */   }
/*      */ 
/*      */   
/*      */   private EntityFX spawnEntityFX(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... parameters) {
/* 2776 */     if (this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
/*      */       
/* 2778 */       int i = this.mc.gameSettings.particleSetting;
/*      */       
/* 2780 */       if (i == 1 && this.theWorld.rand.nextInt(3) == 0)
/*      */       {
/* 2782 */         i = 2;
/*      */       }
/*      */       
/* 2785 */       double d0 = (this.mc.getRenderViewEntity()).posX - xCoord;
/* 2786 */       double d1 = (this.mc.getRenderViewEntity()).posY - yCoord;
/* 2787 */       double d2 = (this.mc.getRenderViewEntity()).posZ - zCoord;
/*      */       
/* 2789 */       if (particleID == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2791 */         return null;
/*      */       }
/* 2793 */       if (particleID == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2795 */         return null;
/*      */       }
/* 2797 */       if (particleID == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2799 */         return null;
/*      */       }
/* 2801 */       if (particleID == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles())
/*      */       {
/* 2803 */         return null;
/*      */       }
/* 2805 */       if (particleID == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles())
/*      */       {
/* 2807 */         return null;
/*      */       }
/* 2809 */       if (particleID == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2811 */         return null;
/*      */       }
/* 2813 */       if (particleID == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2815 */         return null;
/*      */       }
/* 2817 */       if (particleID == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2819 */         return null;
/*      */       }
/* 2821 */       if (particleID == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2823 */         return null;
/*      */       }
/* 2825 */       if (particleID == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2827 */         return null;
/*      */       }
/* 2829 */       if (particleID == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2831 */         return null;
/*      */       }
/* 2833 */       if (particleID == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2835 */         return null;
/*      */       }
/* 2837 */       if (particleID == EnumParticleTypes.PORTAL.getParticleID() && !Config.isPortalParticles())
/*      */       {
/* 2839 */         return null;
/*      */       }
/* 2841 */       if (particleID == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame())
/*      */       {
/* 2843 */         return null;
/*      */       }
/* 2845 */       if (particleID == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone())
/*      */       {
/* 2847 */         return null;
/*      */       }
/* 2849 */       if (particleID == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2851 */         return null;
/*      */       }
/* 2853 */       if (particleID == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2855 */         return null;
/*      */       }
/* 2857 */       if (particleID == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles())
/*      */       {
/* 2859 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 2863 */       if (!ignoreRange) {
/*      */         
/* 2865 */         double d3 = 256.0D;
/*      */         
/* 2867 */         if (particleID == EnumParticleTypes.CRIT.getParticleID())
/*      */         {
/* 2869 */           d3 = 38416.0D;
/*      */         }
/*      */         
/* 2872 */         if (d0 * d0 + d1 * d1 + d2 * d2 > d3)
/*      */         {
/* 2874 */           return null;
/*      */         }
/*      */         
/* 2877 */         if (i > 1)
/*      */         {
/* 2879 */           return null;
/*      */         }
/*      */       } 
/*      */       
/* 2883 */       EntityFX entityfx = this.mc.effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, parameters);
/*      */       
/* 2885 */       if (particleID == EnumParticleTypes.WATER_BUBBLE.getParticleID())
/*      */       {
/* 2887 */         CustomColors.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, xCoord, yCoord, zCoord, this.renderEnv);
/*      */       }
/*      */       
/* 2890 */       if (particleID == EnumParticleTypes.WATER_SPLASH.getParticleID())
/*      */       {
/* 2892 */         CustomColors.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, xCoord, yCoord, zCoord, this.renderEnv);
/*      */       }
/*      */       
/* 2895 */       if (particleID == EnumParticleTypes.WATER_DROP.getParticleID())
/*      */       {
/* 2897 */         CustomColors.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, xCoord, yCoord, zCoord, this.renderEnv);
/*      */       }
/*      */       
/* 2900 */       if (particleID == EnumParticleTypes.TOWN_AURA.getParticleID())
/*      */       {
/* 2902 */         CustomColors.updateMyceliumFX(entityfx);
/*      */       }
/*      */       
/* 2905 */       if (particleID == EnumParticleTypes.PORTAL.getParticleID())
/*      */       {
/* 2907 */         CustomColors.updatePortalFX(entityfx);
/*      */       }
/*      */       
/* 2910 */       if (particleID == EnumParticleTypes.REDSTONE.getParticleID())
/*      */       {
/* 2912 */         CustomColors.updateReddustFX(entityfx, (IBlockAccess)this.theWorld, xCoord, yCoord, zCoord);
/*      */       }
/*      */       
/* 2915 */       return entityfx;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2920 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityAdded(Entity entityIn) {
/* 2930 */     RandomEntities.entityLoaded(entityIn, (World)this.theWorld);
/*      */     
/* 2932 */     if (Config.isDynamicLights())
/*      */     {
/* 2934 */       DynamicLights.entityAdded(entityIn, this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityRemoved(Entity entityIn) {
/* 2944 */     RandomEntities.entityUnloaded(entityIn, (World)this.theWorld);
/*      */     
/* 2946 */     if (Config.isDynamicLights())
/*      */     {
/* 2948 */       DynamicLights.entityRemoved(entityIn, this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteAllDisplayLists() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void broadcastSound(int soundID, BlockPos pos, int data) {
/* 2961 */     switch (soundID) {
/*      */       
/*      */       case 1013:
/*      */       case 1018:
/* 2965 */         if (this.mc.getRenderViewEntity() != null) {
/*      */           
/* 2967 */           double d0 = pos.getX() - (this.mc.getRenderViewEntity()).posX;
/* 2968 */           double d1 = pos.getY() - (this.mc.getRenderViewEntity()).posY;
/* 2969 */           double d2 = pos.getZ() - (this.mc.getRenderViewEntity()).posZ;
/* 2970 */           double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 2971 */           double d4 = (this.mc.getRenderViewEntity()).posX;
/* 2972 */           double d5 = (this.mc.getRenderViewEntity()).posY;
/* 2973 */           double d6 = (this.mc.getRenderViewEntity()).posZ;
/*      */           
/* 2975 */           if (d3 > 0.0D) {
/*      */             
/* 2977 */             d4 += d0 / d3 * 2.0D;
/* 2978 */             d5 += d1 / d3 * 2.0D;
/* 2979 */             d6 += d2 / d3 * 2.0D;
/*      */           } 
/*      */           
/* 2982 */           if (soundID == 1013) {
/*      */             
/* 2984 */             this.theWorld.playSound(d4, d5, d6, "mob.wither.spawn", 1.0F, 1.0F, false);
/*      */             
/*      */             break;
/*      */           } 
/* 2988 */           this.theWorld.playSound(d4, d5, d6, "mob.enderdragon.end", 5.0F, 1.0F, false);
/*      */         }  break;
/*      */     }  } public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int data) { int i, j; double d0, d1, d2; int i1; Block block; double d3, d4, d5; int k, j1;
/*      */     float f, f1, f2;
/*      */     EnumParticleTypes enumparticletypes;
/*      */     int k1;
/*      */     double d6, d8, d10;
/*      */     int l1;
/*      */     double d22;
/*      */     int l;
/* 2998 */     Random random = this.theWorld.rand;
/*      */     
/* 3000 */     switch (sfxType) {
/*      */       
/*      */       case 1000:
/* 3003 */         this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0F, 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1001:
/* 3007 */         this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1002:
/* 3011 */         this.theWorld.playSoundAtPos(blockPosIn, "random.bow", 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1003:
/* 3015 */         this.theWorld.playSoundAtPos(blockPosIn, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1004:
/* 3019 */         this.theWorld.playSoundAtPos(blockPosIn, "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
/*      */         break;
/*      */       
/*      */       case 1005:
/* 3023 */         if (Item.getItemById(data) instanceof ItemRecord) {
/*      */           
/* 3025 */           this.theWorld.playRecord(blockPosIn, "records." + ((ItemRecord)Item.getItemById(data)).recordName);
/*      */           
/*      */           break;
/*      */         } 
/* 3029 */         this.theWorld.playRecord(blockPosIn, (String)null);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1006:
/* 3035 */         this.theWorld.playSoundAtPos(blockPosIn, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1007:
/* 3039 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.charge", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1008:
/* 3043 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1009:
/* 3047 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1010:
/* 3051 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.wood", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1011:
/* 3055 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.metal", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1012:
/* 3059 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.woodbreak", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1014:
/* 3063 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.wither.shoot", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1015:
/* 3067 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.bat.takeoff", 0.05F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1016:
/* 3071 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.infect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1017:
/* 3075 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.unfect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1020:
/* 3079 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1021:
/* 3083 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1022:
/* 3087 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2000:
/* 3091 */         i = data % 3 - 1;
/* 3092 */         j = data / 3 % 3 - 1;
/* 3093 */         d0 = blockPosIn.getX() + i * 0.6D + 0.5D;
/* 3094 */         d1 = blockPosIn.getY() + 0.5D;
/* 3095 */         d2 = blockPosIn.getZ() + j * 0.6D + 0.5D;
/*      */         
/* 3097 */         for (i1 = 0; i1 < 10; i1++) {
/*      */           
/* 3099 */           double d15 = random.nextDouble() * 0.2D + 0.01D;
/* 3100 */           double d16 = d0 + i * 0.01D + (random.nextDouble() - 0.5D) * j * 0.5D;
/* 3101 */           double d17 = d1 + (random.nextDouble() - 0.5D) * 0.5D;
/* 3102 */           double d18 = d2 + j * 0.01D + (random.nextDouble() - 0.5D) * i * 0.5D;
/* 3103 */           double d19 = i * d15 + random.nextGaussian() * 0.01D;
/* 3104 */           double d20 = -0.03D + random.nextGaussian() * 0.01D;
/* 3105 */           double d21 = j * d15 + random.nextGaussian() * 0.01D;
/* 3106 */           spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d16, d17, d18, d19, d20, d21, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2001:
/* 3112 */         block = Block.getBlockById(data & 0xFFF);
/*      */         
/* 3114 */         if (block.getMaterial() != Material.air)
/*      */         {
/* 3116 */           this.mc.getSoundHandler().playSound((ISound)new PositionedSoundRecord(new ResourceLocation(block.stepSound.getBreakSound()), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F, blockPosIn.getX() + 0.5F, blockPosIn.getY() + 0.5F, blockPosIn.getZ() + 0.5F));
/*      */         }
/*      */         
/* 3119 */         this.mc.effectRenderer.addBlockDestroyEffects(blockPosIn, block.getStateFromMeta(data >> 12 & 0xFF));
/*      */         break;
/*      */       
/*      */       case 2002:
/* 3123 */         d3 = blockPosIn.getX();
/* 3124 */         d4 = blockPosIn.getY();
/* 3125 */         d5 = blockPosIn.getZ();
/*      */         
/* 3127 */         for (k = 0; k < 8; k++) {
/*      */           
/* 3129 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, d3, d4, d5, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem((Item)Items.potionitem), data });
/*      */         } 
/*      */         
/* 3132 */         j1 = Items.potionitem.getColorFromDamage(data);
/* 3133 */         f = (j1 >> 16 & 0xFF) / 255.0F;
/* 3134 */         f1 = (j1 >> 8 & 0xFF) / 255.0F;
/* 3135 */         f2 = (j1 >> 0 & 0xFF) / 255.0F;
/* 3136 */         enumparticletypes = EnumParticleTypes.SPELL;
/*      */         
/* 3138 */         if (Items.potionitem.isEffectInstant(data))
/*      */         {
/* 3140 */           enumparticletypes = EnumParticleTypes.SPELL_INSTANT;
/*      */         }
/*      */         
/* 3143 */         for (k1 = 0; k1 < 100; k1++) {
/*      */           
/* 3145 */           double d7 = random.nextDouble() * 4.0D;
/* 3146 */           double d9 = random.nextDouble() * Math.PI * 2.0D;
/* 3147 */           double d11 = Math.cos(d9) * d7;
/* 3148 */           double d23 = 0.01D + random.nextDouble() * 0.5D;
/* 3149 */           double d24 = Math.sin(d9) * d7;
/* 3150 */           EntityFX entityfx = spawnEntityFX(enumparticletypes.getParticleID(), enumparticletypes.getShouldIgnoreRange(), d3 + d11 * 0.1D, d4 + 0.3D, d5 + d24 * 0.1D, d11, d23, d24, new int[0]);
/*      */           
/* 3152 */           if (entityfx != null) {
/*      */             
/* 3154 */             float f3 = 0.75F + random.nextFloat() * 0.25F;
/* 3155 */             entityfx.setRBGColorF(f * f3, f1 * f3, f2 * f3);
/* 3156 */             entityfx.multiplyVelocity((float)d7);
/*      */           } 
/*      */         } 
/*      */         
/* 3160 */         this.theWorld.playSoundAtPos(blockPosIn, "game.potion.smash", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2003:
/* 3164 */         d6 = blockPosIn.getX() + 0.5D;
/* 3165 */         d8 = blockPosIn.getY();
/* 3166 */         d10 = blockPosIn.getZ() + 0.5D;
/*      */         
/* 3168 */         for (l1 = 0; l1 < 8; l1++) {
/*      */           
/* 3170 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, d6, d8, d10, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem(Items.ender_eye) });
/*      */         } 
/*      */         
/* 3173 */         for (d22 = 0.0D; d22 < 6.283185307179586D; d22 += 0.15707963267948966D) {
/*      */           
/* 3175 */           spawnParticle(EnumParticleTypes.PORTAL, d6 + Math.cos(d22) * 5.0D, d8 - 0.4D, d10 + Math.sin(d22) * 5.0D, Math.cos(d22) * -5.0D, 0.0D, Math.sin(d22) * -5.0D, new int[0]);
/* 3176 */           spawnParticle(EnumParticleTypes.PORTAL, d6 + Math.cos(d22) * 5.0D, d8 - 0.4D, d10 + Math.sin(d22) * 5.0D, Math.cos(d22) * -7.0D, 0.0D, Math.sin(d22) * -7.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2004:
/* 3182 */         for (l = 0; l < 20; l++) {
/*      */           
/* 3184 */           double d12 = blockPosIn.getX() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3185 */           double d13 = blockPosIn.getY() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3186 */           double d14 = blockPosIn.getZ() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3187 */           this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d12, d13, d14, 0.0D, 0.0D, 0.0D, new int[0]);
/* 3188 */           this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d12, d13, d14, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2005:
/* 3194 */         ItemDye.spawnBonemealParticles((World)this.theWorld, blockPosIn, data);
/*      */         break;
/*      */     }  }
/*      */ 
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 3200 */     if (progress >= 0 && progress < 10) {
/*      */       
/* 3202 */       DestroyBlockProgress destroyblockprogress = this.damagedBlocks.get(Integer.valueOf(breakerId));
/*      */       
/* 3204 */       if (destroyblockprogress == null || destroyblockprogress.getPosition().getX() != pos.getX() || destroyblockprogress.getPosition().getY() != pos.getY() || destroyblockprogress.getPosition().getZ() != pos.getZ()) {
/*      */         
/* 3206 */         destroyblockprogress = new DestroyBlockProgress(breakerId, pos);
/* 3207 */         this.damagedBlocks.put(Integer.valueOf(breakerId), destroyblockprogress);
/*      */       } 
/*      */       
/* 3210 */       destroyblockprogress.setPartialBlockDamage(progress);
/* 3211 */       destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
/*      */     }
/*      */     else {
/*      */       
/* 3215 */       this.damagedBlocks.remove(Integer.valueOf(breakerId));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisplayListEntitiesDirty() {
/* 3221 */     this.displayListEntitiesDirty = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasNoChunkUpdates() {
/* 3226 */     return (this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasChunkUpdates());
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetClouds() {
/* 3231 */     this.cloudRenderer.reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountRenderers() {
/* 3236 */     return this.viewFrustum.renderChunks.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountActiveRenderers() {
/* 3241 */     return this.renderInfos.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountEntitiesRendered() {
/* 3246 */     return this.countEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountTileEntitiesRendered() {
/* 3251 */     return this.countTileEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountLoadedChunks() {
/* 3256 */     if (this.theWorld == null)
/*      */     {
/* 3258 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 3262 */     IChunkProvider ichunkprovider = this.theWorld.getChunkProvider();
/*      */     
/* 3264 */     if (ichunkprovider == null)
/*      */     {
/* 3266 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 3270 */     if (ichunkprovider != this.worldChunkProvider) {
/*      */       
/* 3272 */       this.worldChunkProvider = ichunkprovider;
/* 3273 */       this.worldChunkProviderMap = (LongHashMap)Reflector.getFieldValue(ichunkprovider, Reflector.ChunkProviderClient_chunkMapping);
/*      */     } 
/*      */     
/* 3276 */     return (this.worldChunkProviderMap == null) ? 0 : this.worldChunkProviderMap.getNumHashElements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCountChunksToUpdate() {
/* 3283 */     return this.chunksToUpdate.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderChunk getRenderChunk(BlockPos p_getRenderChunk_1_) {
/* 3288 */     return this.viewFrustum.getRenderChunk(p_getRenderChunk_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldClient getWorld() {
/* 3293 */     return this.theWorld;
/*      */   }
/*      */ 
/*      */   
/*      */   private void clearRenderInfos() {
/* 3298 */     if (renderEntitiesCounter > 0) {
/*      */       
/* 3300 */       this.renderInfos = new ArrayList<>(this.renderInfos.size() + 16);
/* 3301 */       this.renderInfosEntities = new ArrayList(this.renderInfosEntities.size() + 16);
/* 3302 */       this.renderInfosTileEntities = new ArrayList(this.renderInfosTileEntities.size() + 16);
/*      */     }
/*      */     else {
/*      */       
/* 3306 */       this.renderInfos.clear();
/* 3307 */       this.renderInfosEntities.clear();
/* 3308 */       this.renderInfosTileEntities.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onPlayerPositionSet() {
/* 3314 */     if (this.firstWorldLoad) {
/*      */       
/* 3316 */       loadRenderers();
/* 3317 */       this.firstWorldLoad = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void pauseChunkUpdates() {
/* 3323 */     if (this.renderDispatcher != null)
/*      */     {
/* 3325 */       this.renderDispatcher.pauseChunkUpdates();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void resumeChunkUpdates() {
/* 3331 */     if (this.renderDispatcher != null)
/*      */     {
/* 3333 */       this.renderDispatcher.resumeChunkUpdates();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTileEntities(Collection<TileEntity> tileEntitiesToRemove, Collection<TileEntity> tileEntitiesToAdd) {
/* 3339 */     synchronized (this.setTileEntities) {
/*      */       
/* 3341 */       this.setTileEntities.removeAll(tileEntitiesToRemove);
/* 3342 */       this.setTileEntities.addAll(tileEntitiesToAdd);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ContainerLocalRenderInformation
/*      */   {
/*      */     final RenderChunk renderChunk;
/*      */     EnumFacing facing;
/*      */     int setFacing;
/*      */     
/*      */     public ContainerLocalRenderInformation(RenderChunk p_i2_1_, EnumFacing p_i2_2_, int p_i2_3_) {
/* 3354 */       this.renderChunk = p_i2_1_;
/* 3355 */       this.facing = p_i2_2_;
/* 3356 */       this.setFacing = p_i2_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setFacingBit(byte p_setFacingBit_1_, EnumFacing p_setFacingBit_2_) {
/* 3361 */       this.setFacing = this.setFacing | p_setFacingBit_1_ | 1 << p_setFacingBit_2_.ordinal();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isFacingBit(EnumFacing p_isFacingBit_1_) {
/* 3366 */       return ((this.setFacing & 1 << p_isFacingBit_1_.ordinal()) > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     private void initialize(EnumFacing p_initialize_1_, int p_initialize_2_) {
/* 3371 */       this.facing = p_initialize_1_;
/* 3372 */       this.setFacing = p_initialize_2_;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\RenderGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */