/*      */ package net.minecraft.client.renderer;
/*      */ import awareline.main.event.Event;
/*      */ import awareline.main.event.EventManager;
/*      */ import awareline.main.event.events.world.renderEvents.EventRender;
/*      */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*      */ import awareline.main.mod.implement.combat.Reach;
/*      */ import awareline.main.mod.implement.player.Bob;
/*      */ import awareline.main.mod.implement.visual.Atmosphere;
/*      */ import awareline.main.mod.implement.visual.ViewClip;
/*      */ import awareline.main.mod.implement.visual.ctype.CameraClip;
/*      */ import awareline.main.mod.implement.world.NoWeather;
/*      */ import com.google.common.base.Predicates;
/*      */ import java.io.IOException;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.MapItemRenderer;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.passive.EntityAnimal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.event.ClickEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatStyle;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MouseFilter;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.GlErrors;
/*      */ import net.optifine.Lagometer;
/*      */ import net.optifine.RandomEntities;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.reflect.ReflectorResolver;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ import net.optifine.util.TimedEvent;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.Project;
/*      */ 
/*      */ public class EntityRenderer implements IResourceManagerReloadListener {
/*   94 */   private static final Logger logger = LogManager.getLogger();
/*   95 */   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
/*   96 */   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
/*      */ 
/*      */   
/*      */   public static boolean anaglyphEnable;
/*      */ 
/*      */   
/*      */   public static int anaglyphField;
/*      */ 
/*      */   
/*      */   private final Minecraft mc;
/*      */   
/*      */   private final IResourceManager resourceManager;
/*      */   
/*  109 */   private final Random random = new Random();
/*      */   
/*      */   private float farPlaneDistance;
/*      */   
/*      */   public ItemRenderer itemRenderer;
/*      */   
/*      */   private final MapItemRenderer theMapItemRenderer;
/*      */   
/*      */   private int rendererUpdateCount;
/*      */   
/*  119 */   private MouseFilter mouseFilterXAxis = new MouseFilter();
/*  120 */   private MouseFilter mouseFilterYAxis = new MouseFilter();
/*  121 */   private final float thirdPersonDistance = 4.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   private float thirdPersonDistanceTemp = 4.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamYaw;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamPitch;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamFilterX;
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamFilterY;
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamPartialTicks;
/*      */ 
/*      */ 
/*      */   
/*      */   private float fovModifierHand;
/*      */ 
/*      */ 
/*      */   
/*      */   private float fovModifierHandPrev;
/*      */ 
/*      */ 
/*      */   
/*      */   private float bossColorModifier;
/*      */ 
/*      */ 
/*      */   
/*      */   private float bossColorModifierPrev;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean cloudFog;
/*      */ 
/*      */ 
/*      */   
/*  173 */   private long prevFrameTime = Minecraft.getSystemTime();
/*      */ 
/*      */ 
/*      */   
/*      */   private final DynamicTexture lightmapTexture;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] lightmapColors;
/*      */ 
/*      */ 
/*      */   
/*      */   private final ResourceLocation locationLightMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean lightmapUpdateNeeded;
/*      */ 
/*      */   
/*      */   private float torchFlickerX;
/*      */ 
/*      */   
/*      */   private float torchFlickerDX;
/*      */ 
/*      */   
/*      */   private int rainSoundCounter;
/*      */ 
/*      */   
/*  201 */   private final float[] rainXCoords = new float[1024];
/*  202 */   private final float[] rainYCoords = new float[1024];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  207 */   private final FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
/*      */   
/*      */   public float fogColorRed;
/*      */   
/*      */   public float fogColorGreen;
/*      */   
/*      */   public float fogColorBlue;
/*      */   
/*      */   private float fogColor2;
/*      */   
/*      */   private float fogColor1;
/*      */   
/*      */   private boolean debugView;
/*      */   
/*      */   private double cameraYaw;
/*      */   
/*      */   private double cameraPitch;
/*      */   public ShaderGroup theShaderGroup;
/*  225 */   private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
/*  226 */   public static final int shaderCount = shaderResourceLocations.length;
/*      */   private int shaderIndex;
/*      */   private boolean useShader;
/*      */   public int frameCount;
/*      */   private boolean initialized;
/*      */   private World updatedWorld;
/*      */   public boolean fogStandard;
/*  233 */   private float clipDistance = 128.0F;
/*      */   private long lastServerTime;
/*      */   private int lastServerTicks;
/*      */   private int serverWaitTime;
/*  237 */   private final ShaderGroup[] fxaaShaders = new ShaderGroup[10];
/*      */   private boolean loadVisibleChunks;
/*      */   
/*      */   public EntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn) {
/*  241 */     this.shaderIndex = shaderCount;
/*  242 */     this.useShader = false;
/*  243 */     this.frameCount = 0;
/*  244 */     this.mc = mcIn;
/*  245 */     this.resourceManager = resourceManagerIn;
/*  246 */     this.itemRenderer = mcIn.getItemRenderer();
/*  247 */     this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
/*  248 */     this.lightmapTexture = new DynamicTexture(16, 16);
/*  249 */     this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
/*  250 */     this.lightmapColors = this.lightmapTexture.getTextureData();
/*  251 */     this.theShaderGroup = null;
/*      */     
/*  253 */     for (int i = 0; i < 32; i++) {
/*  254 */       for (int j = 0; j < 32; j++) {
/*  255 */         float f = (j - 16);
/*  256 */         float f1 = (i - 16);
/*  257 */         float f2 = MathHelper.sqrt_float(f * f + f1 * f1);
/*  258 */         this.rainXCoords[i << 5 | j] = -f1 / f2;
/*  259 */         this.rainYCoords[i << 5 | j] = f / f2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isShaderActive() {
/*  265 */     return (OpenGlHelper.shadersSupported && this.theShaderGroup != null);
/*      */   }
/*      */   
/*      */   public void stopUseShader() {
/*  269 */     if (this.theShaderGroup != null) {
/*  270 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  273 */     this.theShaderGroup = null;
/*  274 */     this.shaderIndex = shaderCount;
/*      */   }
/*      */   
/*      */   public void switchUseShader() {
/*  278 */     this.useShader = !this.useShader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadEntityShader(Entity entityIn) {
/*  285 */     if (OpenGlHelper.shadersSupported) {
/*  286 */       if (this.theShaderGroup != null) {
/*  287 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  290 */       this.theShaderGroup = null;
/*      */       
/*  292 */       if (entityIn instanceof net.minecraft.entity.monster.EntityCreeper) {
/*  293 */         loadShader(new ResourceLocation("shaders/post/creeper.json"));
/*  294 */       } else if (entityIn instanceof net.minecraft.entity.monster.EntitySpider) {
/*  295 */         loadShader(new ResourceLocation("shaders/post/spider.json"));
/*  296 */       } else if (entityIn instanceof net.minecraft.entity.monster.EntityEnderman) {
/*  297 */         loadShader(new ResourceLocation("shaders/post/invert.json"));
/*  298 */       } else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
/*  299 */         Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, new Object[] { entityIn, this });
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void activateNextShader() {
/*  305 */     if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  306 */       if (this.theShaderGroup != null) {
/*  307 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  310 */       this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
/*      */       
/*  312 */       if (this.shaderIndex != shaderCount) {
/*  313 */         loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */       } else {
/*  315 */         this.theShaderGroup = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadShader(ResourceLocation resourceLocationIn) {
/*  321 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*      */       try {
/*  323 */         this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn);
/*  324 */         this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  325 */         this.useShader = true;
/*  326 */       } catch (IOException|com.google.gson.JsonSyntaxException ioexception) {
/*  327 */         logger.warn("Failed to load shader: " + resourceLocationIn, ioexception);
/*  328 */         this.shaderIndex = shaderCount;
/*  329 */         this.useShader = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  335 */     if (this.theShaderGroup != null) {
/*  336 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  339 */     this.theShaderGroup = null;
/*      */     
/*  341 */     if (this.shaderIndex != shaderCount) {
/*  342 */       loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */     } else {
/*  344 */       loadEntityShader(this.mc.getRenderViewEntity());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRenderer() {
/*  352 */     if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
/*  353 */       ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */     }
/*      */     
/*  356 */     updateFovModifierHand();
/*  357 */     updateTorchFlicker();
/*  358 */     this.fogColor2 = this.fogColor1;
/*  359 */     getClass(); this.thirdPersonDistanceTemp = 4.0F;
/*      */     
/*  361 */     if (this.mc.gameSettings.smoothCamera) {
/*  362 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/*  363 */       float f1 = f * f * f * 8.0F;
/*  364 */       this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * f1);
/*  365 */       this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * f1);
/*  366 */       this.smoothCamPartialTicks = 0.0F;
/*  367 */       this.smoothCamYaw = 0.0F;
/*  368 */       this.smoothCamPitch = 0.0F;
/*      */     } else {
/*  370 */       this.smoothCamFilterX = 0.0F;
/*  371 */       this.smoothCamFilterY = 0.0F;
/*  372 */       this.mouseFilterXAxis.reset();
/*  373 */       this.mouseFilterYAxis.reset();
/*      */     } 
/*      */     
/*  376 */     if (this.mc.getRenderViewEntity() == null) {
/*  377 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/*  380 */     Entity entity = this.mc.getRenderViewEntity();
/*  381 */     double d2 = entity.posX;
/*  382 */     double d0 = entity.posY + entity.getEyeHeight();
/*  383 */     double d1 = entity.posZ;
/*  384 */     float f2 = this.mc.theWorld.getLightBrightness(new BlockPos(d2, d0, d1));
/*  385 */     float f3 = this.mc.gameSettings.renderDistanceChunks / 16.0F;
/*  386 */     f3 = MathHelper.clamp_float(f3, 0.0F, 1.0F);
/*  387 */     float f4 = f2 * (1.0F - f3) + f3;
/*  388 */     this.fogColor1 += (f4 - this.fogColor1) * 0.1F;
/*  389 */     this.rendererUpdateCount++;
/*  390 */     this.itemRenderer.updateEquippedItem();
/*  391 */     addRainParticles();
/*  392 */     this.bossColorModifierPrev = this.bossColorModifier;
/*      */     
/*  394 */     if (BossStatus.hasColorModifier) {
/*  395 */       this.bossColorModifier += 0.05F;
/*      */       
/*  397 */       if (this.bossColorModifier > 1.0F) {
/*  398 */         this.bossColorModifier = 1.0F;
/*      */       }
/*      */       
/*  401 */       BossStatus.hasColorModifier = false;
/*  402 */     } else if (this.bossColorModifier > 0.0F) {
/*  403 */       this.bossColorModifier -= 0.0125F;
/*      */     } 
/*      */   }
/*      */   
/*      */   public ShaderGroup getShaderGroup() {
/*  408 */     return this.theShaderGroup;
/*      */   }
/*      */   
/*      */   public void updateShaderGroupSize(int width, int height) {
/*  412 */     if (OpenGlHelper.shadersSupported) {
/*  413 */       if (this.theShaderGroup != null) {
/*  414 */         this.theShaderGroup.createBindFramebuffers(width, height);
/*      */       }
/*      */       
/*  417 */       this.mc.renderGlobal.createBindEntityOutlineFbs(width, height);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getMouseOver(float partialTicks) {
/*  425 */     Entity entity = this.mc.getRenderViewEntity();
/*      */     
/*  427 */     if (entity != null && this.mc.theWorld != null) {
/*  428 */       this.mc.mcProfiler.startSection("pick");
/*  429 */       this.mc.pointedEntity = null;
/*  430 */       double d0 = Reach.getInstance.isEnabled() ? Reach.getInstance.getMax() : this.mc.playerController.getBlockReachDistance();
/*  431 */       this.mc.objectMouseOver = entity.rayTrace(Reach.getInstance.isEnabled() ? Reach.getInstance.getMax() : d0, partialTicks);
/*  432 */       double d1 = d0;
/*  433 */       Vec3 vec3 = entity.getPositionEyes(partialTicks);
/*  434 */       boolean flag = false;
/*      */       
/*  436 */       if (this.mc.playerController.extendedReach()) {
/*  437 */         d0 = 6.0D;
/*  438 */         d1 = 6.0D;
/*  439 */       } else if (d0 > 3.0D) {
/*  440 */         flag = true;
/*      */       } 
/*      */       
/*  443 */       if (this.mc.objectMouseOver != null) {
/*  444 */         d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
/*      */       }
/*  446 */       if (Reach.getInstance.isEnabled()) {
/*  447 */         d1 = Reach.getInstance.currentRange;
/*  448 */         MovingObjectPosition vec3d1 = entity.rayTrace(d1, partialTicks);
/*  449 */         if (vec3d1 != null) {
/*  450 */           d1 = vec3d1.hitVec.distanceTo(vec3);
/*      */         }
/*      */       } 
/*  453 */       Vec3 vec31 = entity.getLook(partialTicks);
/*  454 */       Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
/*  455 */       Entity pointedEntity = null;
/*  456 */       Vec3 vec33 = null;
/*  457 */       float f = 1.0F;
/*  458 */       List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
/*  459 */       double d2 = d1;
/*      */       
/*  461 */       for (Entity entity1 : list) {
/*  462 */         float f1 = entity1.getCollisionBorderSize();
/*  463 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/*  464 */         MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
/*      */         
/*  466 */         if (axisalignedbb.isVecInside(vec3)) {
/*  467 */           if (d2 >= 0.0D) {
/*  468 */             pointedEntity = entity1;
/*  469 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.hitVec;
/*  470 */             d2 = 0.0D;
/*      */           }  continue;
/*  472 */         }  if (movingobjectposition != null) {
/*  473 */           double d3 = vec3.distanceTo(movingobjectposition.hitVec);
/*      */           
/*  475 */           if (d3 < d2 || d2 == 0.0D) {
/*  476 */             boolean flag1 = false;
/*      */             
/*  478 */             if (Reflector.ForgeEntity_canRiderInteract.exists()) {
/*  479 */               flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*      */             }
/*      */             
/*  482 */             if (!flag1 && entity1 == entity.ridingEntity) {
/*  483 */               if (d2 == 0.0D) {
/*  484 */                 pointedEntity = entity1;
/*  485 */                 vec33 = movingobjectposition.hitVec;
/*      */               }  continue;
/*      */             } 
/*  488 */             pointedEntity = entity1;
/*  489 */             vec33 = movingobjectposition.hitVec;
/*  490 */             d2 = d3;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  496 */       if (pointedEntity != null && flag && vec3.distanceTo(vec33) > (Reach.getInstance.isEnabled() ? Reach.getInstance.currentRange : 3.0D)) {
/*      */         
/*  498 */         pointedEntity = null;
/*  499 */         this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
/*      */       } 
/*      */       
/*  502 */       if (pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
/*  503 */         this.mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
/*      */         
/*  505 */         if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame) {
/*  506 */           this.mc.pointedEntity = pointedEntity;
/*      */         }
/*      */       } 
/*      */       
/*  510 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFovModifierHand() {
/*  518 */     float f = 1.0F;
/*      */     
/*  520 */     if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
/*  521 */       AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.mc.getRenderViewEntity();
/*  522 */       f = abstractclientplayer.getFovModifier();
/*      */     } 
/*      */     
/*  525 */     this.fovModifierHandPrev = this.fovModifierHand;
/*  526 */     this.fovModifierHand += (f - this.fovModifierHand) * 0.5F;
/*      */     
/*  528 */     if (this.fovModifierHand > 1.5F) {
/*  529 */       this.fovModifierHand = 1.5F;
/*      */     }
/*      */     
/*  532 */     if (this.fovModifierHand < 0.1F) {
/*  533 */       this.fovModifierHand = 0.1F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getFOVModifier(float partialTicks, boolean useFOVSetting) {
/*  543 */     if (this.debugView) {
/*  544 */       return 90.0F;
/*      */     }
/*  546 */     Entity entity = this.mc.getRenderViewEntity();
/*  547 */     float f = 70.0F;
/*      */     
/*  549 */     if (useFOVSetting) {
/*  550 */       f = this.mc.gameSettings.fovSetting;
/*      */       
/*  552 */       if (Config.isDynamicFov()) {
/*  553 */         f *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
/*      */       }
/*      */     } 
/*      */     
/*  557 */     boolean flag = false;
/*      */     
/*  559 */     if (this.mc.currentScreen == null) {
/*  560 */       flag = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
/*      */     }
/*      */     
/*  563 */     if (flag) {
/*  564 */       if (!Config.zoomMode) {
/*  565 */         Config.zoomMode = true;
/*  566 */         Config.zoomSmoothCamera = this.mc.gameSettings.smoothCamera;
/*  567 */         this.mc.gameSettings.smoothCamera = true;
/*  568 */         this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */       } 
/*      */       
/*  571 */       f /= 4.0F;
/*  572 */     } else if (Config.zoomMode) {
/*  573 */       Config.zoomMode = false;
/*  574 */       this.mc.gameSettings.smoothCamera = Config.zoomSmoothCamera;
/*  575 */       this.mouseFilterXAxis = new MouseFilter();
/*  576 */       this.mouseFilterYAxis = new MouseFilter();
/*  577 */       this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */     } 
/*      */     
/*  580 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
/*  581 */       float f1 = ((EntityLivingBase)entity).deathTime + partialTicks;
/*  582 */       f /= (1.0F - 500.0F / (f1 + 500.0F)) * 2.0F + 1.0F;
/*      */     } 
/*      */     
/*  585 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/*  587 */     if (block.getMaterial() == Material.water) {
/*  588 */       f = f * 60.0F / 70.0F;
/*      */     }
/*      */     
/*  591 */     return Reflector.ForgeHooksClient_getFOVModifier.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getFOVModifier, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(f) }) : f;
/*      */   }
/*      */ 
/*      */   
/*      */   private void hurtCameraEffect(float partialTicks) {
/*  596 */     if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
/*  597 */       EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
/*  598 */       if (NoHurtCam.getInstance.isEnabled()) {
/*      */         return;
/*      */       }
/*  601 */       float f = entitylivingbase.hurtTime - partialTicks;
/*      */       
/*  603 */       if (entitylivingbase.getHealth() <= 0.0F) {
/*  604 */         float f1 = entitylivingbase.deathTime + partialTicks;
/*  605 */         GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
/*      */       } 
/*      */       
/*  608 */       if (f < 0.0F) {
/*      */         return;
/*      */       }
/*      */       
/*  612 */       f /= entitylivingbase.maxHurtTime;
/*  613 */       f = MathHelper.sin(f * f * f * f * 3.1415927F);
/*  614 */       float f2 = entitylivingbase.attackedAtYaw;
/*  615 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  616 */       GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
/*  617 */       GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupViewBobbing(float partialTicks) {
/*  625 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  626 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  627 */       float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
/*  628 */       float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
/*  629 */       float f2 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
/*  630 */       float f3 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
/*  631 */       if (!Bob.getInstance.isEnabled()) {
/*  632 */         GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2), 0.0F);
/*      */       }
/*  634 */       else if (!((Boolean)Bob.realBobbing.get()).booleanValue()) {
/*  635 */         GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2), 0.0F);
/*      */       } 
/*      */       
/*  638 */       GlStateManager.rotate(MathHelper.sin(f1 * 3.1415927F) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
/*  639 */       GlStateManager.rotate(Math.abs(MathHelper.cos(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  640 */       GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void orientCamera(float partialTicks) {
/*  648 */     Entity entity = this.mc.getRenderViewEntity();
/*  649 */     float f = entity.getEyeHeight();
/*  650 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  651 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  652 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*      */     
/*  654 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
/*  655 */       f = (float)(f + 1.0D);
/*  656 */       GlStateManager.translate(0.0F, 0.3F, 0.0F);
/*      */       
/*  658 */       if (!this.mc.gameSettings.debugCamEnable) {
/*  659 */         BlockPos blockpos = new BlockPos(entity);
/*  660 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*  661 */         Block block = iblockstate.getBlock();
/*      */         
/*  663 */         if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
/*  664 */           Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { this.mc.theWorld, blockpos, iblockstate, entity });
/*  665 */         } else if (block == Blocks.bed) {
/*  666 */           int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/*  667 */           GlStateManager.rotate((j * 90), 0.0F, 1.0F, 0.0F);
/*      */         } 
/*      */         
/*  670 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
/*  671 */         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
/*      */       } 
/*  673 */     } else if (this.mc.gameSettings.thirdPersonView > 0) {
/*  674 */       getClass(); double d3 = (this.thirdPersonDistanceTemp + (4.0F - this.thirdPersonDistanceTemp) * partialTicks);
/*      */       
/*  676 */       if (this.mc.gameSettings.debugCamEnable) {
/*  677 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*      */       } else {
/*  679 */         float f1 = entity.rotationYaw;
/*  680 */         float f2 = entity.rotationPitch;
/*      */         
/*  682 */         if (this.mc.gameSettings.thirdPersonView == 2) {
/*  683 */           f2 += 180.0F;
/*      */         }
/*  685 */         if (!CameraClip.getInstance.isEnabled()) {
/*  686 */           double d4 = (-MathHelper.sin(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  687 */           double d5 = (MathHelper.cos(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  688 */           double d6 = -MathHelper.sin(f2 / 180.0F * 3.1415927F) * d3;
/*      */           
/*  690 */           for (int i = 0; i < 8; i++) {
/*  691 */             float f3 = (((i & 0x1) << 1) - 1);
/*  692 */             float f4 = (((i >> 1 & 0x1) << 1) - 1);
/*  693 */             float f5 = (((i >> 2 & 0x1) << 1) - 1);
/*  694 */             f3 *= 0.1F;
/*  695 */             f4 *= 0.1F;
/*  696 */             f5 *= 0.1F;
/*  697 */             MovingObjectPosition movingobjectposition = this.mc.theWorld.rayTraceBlocks(new Vec3(d0 + f3, d1 + f4, d2 + f5), new Vec3(d0 - d4 + f3 + f5, d1 - d6 + f4, d2 - d5 + f5));
/*      */             
/*  699 */             if (movingobjectposition != null) {
/*  700 */               double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));
/*      */               
/*  702 */               if (d7 < d3 && !ViewClip.getInstance.isEnabled()) {
/*  703 */                 d3 = d7;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  708 */         if (this.mc.gameSettings.thirdPersonView == 2) {
/*  709 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */         }
/*      */         
/*  712 */         GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
/*  713 */         GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
/*  714 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*  715 */         GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  716 */         GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
/*      */       } 
/*      */     } else {
/*  719 */       GlStateManager.translate(0.0F, 0.0F, -0.1F);
/*      */     } 
/*      */     
/*  722 */     if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
/*  723 */       if (!this.mc.gameSettings.debugCamEnable) {
/*  724 */         float f6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F;
/*  725 */         float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/*  726 */         float f8 = 0.0F;
/*      */         
/*  728 */         if (entity instanceof EntityAnimal) {
/*  729 */           EntityAnimal entityanimal1 = (EntityAnimal)entity;
/*  730 */           f6 = entityanimal1.prevRotationYawHead + (entityanimal1.rotationYawHead - entityanimal1.prevRotationYawHead) * partialTicks + 180.0F;
/*      */         } 
/*      */         
/*  733 */         Block block1 = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*  734 */         Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, new Object[] { this, entity, block1, Float.valueOf(partialTicks), Float.valueOf(f6), Float.valueOf(f7), Float.valueOf(f8) });
/*  735 */         Reflector.postForgeBusEvent(object);
/*  736 */         f8 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_roll, f8);
/*  737 */         f7 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_pitch, f7);
/*  738 */         f6 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_yaw, f6);
/*  739 */         GlStateManager.rotate(f8, 0.0F, 0.0F, 1.0F);
/*  740 */         GlStateManager.rotate(f7, 1.0F, 0.0F, 0.0F);
/*  741 */         GlStateManager.rotate(f6, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*  743 */     } else if (!this.mc.gameSettings.debugCamEnable) {
/*  744 */       GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
/*      */       
/*  746 */       if (entity instanceof EntityAnimal) {
/*  747 */         EntityAnimal entityanimal = (EntityAnimal)entity;
/*  748 */         GlStateManager.rotate(entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       } else {
/*  750 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  754 */     GlStateManager.translate(0.0F, -f, 0.0F);
/*  755 */     d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  756 */     d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  757 */     d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  758 */     this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupCameraTransform(float partialTicks, int pass) {
/*  765 */     this.farPlaneDistance = (this.mc.gameSettings.renderDistanceChunks << 4);
/*      */     
/*  767 */     if (Config.isFogFancy()) {
/*  768 */       this.farPlaneDistance *= 0.95F;
/*      */     }
/*      */     
/*  771 */     if (Config.isFogFast()) {
/*  772 */       this.farPlaneDistance *= 0.83F;
/*      */     }
/*      */     
/*  775 */     GlStateManager.matrixMode(5889);
/*  776 */     GlStateManager.loadIdentity();
/*  777 */     float f = 0.07F;
/*      */     
/*  779 */     if (this.mc.gameSettings.anaglyph) {
/*  780 */       GlStateManager.translate(-((pass << 1) - 1) * f, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  783 */     this.clipDistance = this.farPlaneDistance * 2.0F;
/*      */     
/*  785 */     if (this.clipDistance < 173.0F) {
/*  786 */       this.clipDistance = 173.0F;
/*      */     }
/*      */     
/*  789 */     Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/*  790 */     GlStateManager.matrixMode(5888);
/*  791 */     GlStateManager.loadIdentity();
/*      */     
/*  793 */     if (this.mc.gameSettings.anaglyph) {
/*  794 */       GlStateManager.translate(((pass << 1) - 1) * 0.1F, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  797 */     hurtCameraEffect(partialTicks);
/*      */     
/*  799 */     if (this.mc.gameSettings.viewBobbing) {
/*  800 */       setupViewBobbing(partialTicks);
/*      */     }
/*      */     
/*  803 */     float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/*      */     
/*  805 */     if (f1 > 0.0F) {
/*  806 */       int i = 20;
/*      */       
/*  808 */       if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
/*  809 */         i = 7;
/*      */       }
/*      */       
/*  812 */       float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
/*  813 */       f2 *= f2;
/*  814 */       GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*  815 */       GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
/*  816 */       GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*      */     } 
/*      */     
/*  819 */     orientCamera(partialTicks);
/*      */     
/*  821 */     if (this.debugView) {
/*  822 */       GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderHand(float partialTicks, int xOffset) {
/*  830 */     renderHand(partialTicks, xOffset, true, true, false);
/*      */   }
/*      */   
/*      */   public void renderHand(float p_renderHand_1_, int p_renderHand_2_, boolean p_renderHand_3_, boolean p_renderHand_4_, boolean p_renderHand_5_) {
/*  834 */     if (!this.debugView) {
/*  835 */       GlStateManager.matrixMode(5889);
/*  836 */       GlStateManager.loadIdentity();
/*  837 */       float f = 0.07F;
/*      */       
/*  839 */       if (this.mc.gameSettings.anaglyph) {
/*  840 */         GlStateManager.translate(-((p_renderHand_2_ << 1) - 1) * f, 0.0F, 0.0F);
/*      */       }
/*      */       
/*  843 */       if (Config.isShaders()) {
/*  844 */         Shaders.applyHandDepth();
/*      */       }
/*      */       
/*  847 */       Project.gluPerspective(getFOVModifier(p_renderHand_1_, false), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
/*  848 */       GlStateManager.matrixMode(5888);
/*  849 */       GlStateManager.loadIdentity();
/*      */       
/*  851 */       if (this.mc.gameSettings.anaglyph) {
/*  852 */         GlStateManager.translate(((p_renderHand_2_ << 1) - 1) * 0.1F, 0.0F, 0.0F);
/*      */       }
/*      */       
/*  855 */       boolean flag = false;
/*      */       
/*  857 */       if (p_renderHand_3_) {
/*  858 */         GlStateManager.pushMatrix();
/*  859 */         hurtCameraEffect(p_renderHand_1_);
/*      */         
/*  861 */         if (this.mc.gameSettings.viewBobbing) {
/*  862 */           setupViewBobbing(p_renderHand_1_);
/*      */         }
/*      */         
/*  865 */         flag = (this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping());
/*  866 */         boolean flag1 = !ReflectorForge.renderFirstPersonHand(this.mc.renderGlobal, p_renderHand_1_, p_renderHand_2_);
/*      */         
/*  868 */         if (flag1 && this.mc.gameSettings.thirdPersonView == 0 && !flag && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator()) {
/*  869 */           enableLightmap();
/*      */           
/*  871 */           if (Config.isShaders()) {
/*  872 */             ShadersRender.renderItemFP(this.itemRenderer, p_renderHand_1_, p_renderHand_5_);
/*      */           } else {
/*  874 */             this.itemRenderer.renderItemInFirstPerson(p_renderHand_1_);
/*      */           } 
/*      */           
/*  877 */           disableLightmap();
/*      */         } 
/*      */         
/*  880 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  883 */       if (!p_renderHand_4_) {
/*      */         return;
/*      */       }
/*      */       
/*  887 */       disableLightmap();
/*      */       
/*  889 */       if (this.mc.gameSettings.thirdPersonView == 0 && !flag) {
/*  890 */         this.itemRenderer.renderOverlays(p_renderHand_1_);
/*  891 */         hurtCameraEffect(p_renderHand_1_);
/*      */       } 
/*      */       
/*  894 */       if (this.mc.gameSettings.viewBobbing) {
/*  895 */         setupViewBobbing(p_renderHand_1_);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void disableLightmap() {
/*  901 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  902 */     GlStateManager.disableTexture2D();
/*  903 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/*  905 */     if (Config.isShaders()) {
/*  906 */       Shaders.disableLightmap();
/*      */     }
/*      */   }
/*      */   
/*      */   public void enableLightmap() {
/*  911 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  912 */     GlStateManager.matrixMode(5890);
/*  913 */     GlStateManager.loadIdentity();
/*  914 */     float f = 0.00390625F;
/*  915 */     GlStateManager.scale(f, f, f);
/*  916 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/*  917 */     GlStateManager.matrixMode(5888);
/*  918 */     this.mc.getTextureManager().bindTexture(this.locationLightMap);
/*  919 */     GL11.glTexParameteri(3553, 10241, 9729);
/*  920 */     GL11.glTexParameteri(3553, 10240, 9729);
/*  921 */     GL11.glTexParameteri(3553, 10242, 33071);
/*  922 */     GL11.glTexParameteri(3553, 10243, 33071);
/*  923 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  924 */     GlStateManager.enableTexture2D();
/*  925 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/*  927 */     if (Config.isShaders()) {
/*  928 */       Shaders.enableLightmap();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateTorchFlicker() {
/*  936 */     this.torchFlickerDX = (float)(this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
/*  937 */     this.torchFlickerDX = (float)(this.torchFlickerDX * 0.9D);
/*  938 */     this.torchFlickerX += this.torchFlickerDX - this.torchFlickerX;
/*  939 */     this.lightmapUpdateNeeded = true;
/*      */   }
/*      */   
/*      */   private void updateLightmap(float partialTicks) {
/*  943 */     if (this.lightmapUpdateNeeded) {
/*  944 */       this.mc.mcProfiler.startSection("lightTex");
/*  945 */       WorldClient worldClient = this.mc.theWorld;
/*      */       
/*  947 */       if (worldClient != null) {
/*      */         
/*  949 */         if (Config.isCustomColors() && CustomColors.updateLightmap((World)worldClient, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision), partialTicks)) {
/*  950 */           this.lightmapTexture.updateDynamicTexture();
/*  951 */           this.lightmapUpdateNeeded = false;
/*  952 */           this.mc.mcProfiler.endSection();
/*      */           
/*      */           return;
/*      */         } 
/*  956 */         float f = worldClient.getSunBrightness(1.0F);
/*  957 */         float f1 = f * 0.95F + 0.05F;
/*      */         
/*  959 */         for (int i = 0; i < 256; i++) {
/*  960 */           float f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16] * f1;
/*  961 */           float f3 = ((World)worldClient).provider.getLightBrightnessTable()[i % 16] * (this.torchFlickerX * 0.1F + 1.5F);
/*      */           
/*  963 */           if (worldClient.getLastLightningBolt() > 0) {
/*  964 */             f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16];
/*      */           }
/*      */           
/*  967 */           float f4 = f2 * (f * 0.65F + 0.35F);
/*  968 */           float f5 = f2 * (f * 0.65F + 0.35F);
/*  969 */           float f6 = f3 * ((f3 * 0.6F + 0.4F) * 0.6F + 0.4F);
/*  970 */           float f7 = f3 * (f3 * f3 * 0.6F + 0.4F);
/*  971 */           float f8 = f4 + f3;
/*  972 */           float f9 = f5 + f6;
/*  973 */           float f10 = f2 + f7;
/*  974 */           f8 = f8 * 0.96F + 0.03F;
/*  975 */           f9 = f9 * 0.96F + 0.03F;
/*  976 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/*  978 */           if (this.bossColorModifier > 0.0F) {
/*  979 */             float f11 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/*  980 */             f8 = f8 * (1.0F - f11) + f8 * 0.7F * f11;
/*  981 */             f9 = f9 * (1.0F - f11) + f9 * 0.6F * f11;
/*  982 */             f10 = f10 * (1.0F - f11) + f10 * 0.6F * f11;
/*      */           } 
/*      */           
/*  985 */           if (((World)worldClient).provider.getDimensionId() == 1) {
/*  986 */             f8 = 0.22F + f3 * 0.75F;
/*  987 */             f9 = 0.28F + f6 * 0.75F;
/*  988 */             f10 = 0.25F + f7 * 0.75F;
/*      */           } 
/*      */           
/*  991 */           if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
/*  992 */             float f15 = getNightVisionBrightness((EntityLivingBase)this.mc.thePlayer, partialTicks);
/*  993 */             float f12 = 1.0F / f8;
/*      */             
/*  995 */             if (f12 > 1.0F / f9) {
/*  996 */               f12 = 1.0F / f9;
/*      */             }
/*      */             
/*  999 */             if (f12 > 1.0F / f10) {
/* 1000 */               f12 = 1.0F / f10;
/*      */             }
/*      */             
/* 1003 */             f8 = f8 * (1.0F - f15) + f8 * f12 * f15;
/* 1004 */             f9 = f9 * (1.0F - f15) + f9 * f12 * f15;
/* 1005 */             f10 = f10 * (1.0F - f15) + f10 * f12 * f15;
/*      */           } 
/*      */           
/* 1008 */           if (f8 > 1.0F) {
/* 1009 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1012 */           if (f9 > 1.0F) {
/* 1013 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1016 */           if (f10 > 1.0F) {
/* 1017 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1020 */           float f16 = this.mc.gameSettings.gammaSetting;
/* 1021 */           float f17 = 1.0F - f8;
/* 1022 */           float f13 = 1.0F - f9;
/* 1023 */           float f14 = 1.0F - f10;
/* 1024 */           f17 = 1.0F - f17 * f17 * f17 * f17;
/* 1025 */           f13 = 1.0F - f13 * f13 * f13 * f13;
/* 1026 */           f14 = 1.0F - f14 * f14 * f14 * f14;
/* 1027 */           f8 = f8 * (1.0F - f16) + f17 * f16;
/* 1028 */           f9 = f9 * (1.0F - f16) + f13 * f16;
/* 1029 */           f10 = f10 * (1.0F - f16) + f14 * f16;
/* 1030 */           f8 = f8 * 0.96F + 0.03F;
/* 1031 */           f9 = f9 * 0.96F + 0.03F;
/* 1032 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1034 */           if (f8 > 1.0F) {
/* 1035 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1038 */           if (f9 > 1.0F) {
/* 1039 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1042 */           if (f10 > 1.0F) {
/* 1043 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1046 */           if (f8 < 0.0F) {
/* 1047 */             f8 = 0.0F;
/*      */           }
/*      */           
/* 1050 */           if (f9 < 0.0F) {
/* 1051 */             f9 = 0.0F;
/*      */           }
/*      */           
/* 1054 */           if (f10 < 0.0F) {
/* 1055 */             f10 = 0.0F;
/*      */           }
/*      */           
/* 1058 */           int j = 255;
/* 1059 */           int k = (int)(f8 * 255.0F);
/* 1060 */           int l = (int)(f9 * 255.0F);
/* 1061 */           int i1 = (int)(f10 * 255.0F);
/* 1062 */           this.lightmapColors[i] = j << 24 | k << 16 | l << 8 | i1;
/*      */         } 
/*      */         
/* 1065 */         this.lightmapTexture.updateDynamicTexture();
/* 1066 */         this.lightmapUpdateNeeded = false;
/* 1067 */         this.mc.mcProfiler.endSection();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {
/* 1073 */     int i = entitylivingbaseIn.getActivePotionEffect(Potion.nightVision).getDuration();
/* 1074 */     return (i > 200) ? 1.0F : (0.7F + MathHelper.sin((i - partialTicks) * 3.1415927F * 0.2F) * 0.3F);
/*      */   }
/*      */   
/*      */   public void updateCameraAndRender(float partialTicks, long nanoTime) {
/* 1078 */     Config.renderPartialTicks = partialTicks;
/* 1079 */     frameInit();
/* 1080 */     boolean flag = Display.isActive();
/*      */     
/* 1082 */     if (!flag && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
/* 1083 */       if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
/* 1084 */         this.mc.displayInGameMenu();
/*      */       }
/*      */     } else {
/* 1087 */       this.prevFrameTime = Minecraft.getSystemTime();
/*      */     } 
/*      */     
/* 1090 */     this.mc.mcProfiler.startSection("mouse");
/*      */     
/* 1092 */     if (flag && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
/* 1093 */       Mouse.setGrabbed(false);
/* 1094 */       Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
/* 1095 */       Mouse.setGrabbed(true);
/*      */     } 
/*      */     
/* 1098 */     if (this.mc.inGameHasFocus && flag) {
/* 1099 */       this.mc.mouseHelper.mouseXYChange();
/* 1100 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/* 1101 */       float f1 = f * f * f * 8.0F;
/* 1102 */       float f2 = this.mc.mouseHelper.deltaX * f1;
/* 1103 */       float f3 = this.mc.mouseHelper.deltaY * f1;
/* 1104 */       int i = 1;
/*      */       
/* 1106 */       if (this.mc.gameSettings.invertMouse) {
/* 1107 */         i = -1;
/*      */       }
/*      */       
/* 1110 */       if (this.mc.gameSettings.smoothCamera) {
/* 1111 */         this.smoothCamYaw += f2;
/* 1112 */         this.smoothCamPitch += f3;
/* 1113 */         float f4 = partialTicks - this.smoothCamPartialTicks;
/* 1114 */         this.smoothCamPartialTicks = partialTicks;
/* 1115 */         f2 = this.smoothCamFilterX * f4;
/* 1116 */         f3 = this.smoothCamFilterY * f4;
/*      */       } else {
/* 1118 */         this.smoothCamYaw = 0.0F;
/* 1119 */         this.smoothCamPitch = 0.0F;
/*      */       } 
/* 1121 */       this.mc.thePlayer.setAngles(f2, f3 * i);
/*      */     } 
/*      */     
/* 1124 */     this.mc.mcProfiler.endSection();
/*      */     
/* 1126 */     if (!this.mc.skipRenderWorld) {
/* 1127 */       anaglyphEnable = this.mc.gameSettings.anaglyph;
/* 1128 */       ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1129 */       int i1 = scaledresolution.getScaledWidth();
/* 1130 */       int j1 = scaledresolution.getScaledHeight();
/* 1131 */       int k1 = Mouse.getX() * i1 / this.mc.displayWidth;
/* 1132 */       int l1 = j1 - Mouse.getY() * j1 / this.mc.displayHeight - 1;
/* 1133 */       int i2 = this.mc.gameSettings.limitFramerate;
/*      */ 
/*      */       
/* 1136 */       if (this.mc.theWorld != null) {
/* 1137 */         this.mc.mcProfiler.startSection("level");
/* 1138 */         int j = Math.min(Minecraft.getDebugFPS(), i2);
/* 1139 */         j = Math.max(j, 60);
/* 1140 */         long k = System.nanoTime() - nanoTime;
/* 1141 */         long l = Math.max((1000000000 / j / 4) - k, 0L);
/* 1142 */         renderWorld(partialTicks, System.nanoTime() + l);
/*      */         
/* 1144 */         if (OpenGlHelper.shadersSupported) {
/* 1145 */           this.mc.renderGlobal.renderEntityOutlineFramebuffer();
/*      */           
/* 1147 */           if (this.theShaderGroup != null && this.useShader) {
/* 1148 */             GlStateManager.matrixMode(5890);
/* 1149 */             GlStateManager.pushMatrix();
/* 1150 */             GlStateManager.loadIdentity();
/* 1151 */             this.theShaderGroup.loadShaderGroup(partialTicks);
/* 1152 */             GlStateManager.popMatrix();
/*      */           } 
/*      */           
/* 1155 */           this.mc.getFramebuffer().bindFramebuffer(true);
/*      */         } 
/*      */         
/* 1158 */         this.mc.mcProfiler.endStartSection("gui");
/*      */         
/* 1160 */         if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
/* 1161 */           GlStateManager.alphaFunc(516, 0.1F);
/* 1162 */           this.mc.ingameGUI.renderGameOverlay(partialTicks);
/*      */           
/* 1164 */           if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
/* 1165 */             Config.drawFps();
/*      */           }
/*      */           
/* 1168 */           if (this.mc.gameSettings.showDebugInfo) {
/* 1169 */             Lagometer.showLagometer(scaledresolution);
/*      */           }
/*      */         } 
/*      */         
/* 1173 */         this.mc.mcProfiler.endSection();
/*      */       } else {
/* 1175 */         GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1176 */         GlStateManager.matrixMode(5889);
/* 1177 */         GlStateManager.loadIdentity();
/* 1178 */         GlStateManager.matrixMode(5888);
/* 1179 */         GlStateManager.loadIdentity();
/* 1180 */         setupOverlayRendering();
/* 1181 */         TileEntityRendererDispatcher.instance.renderEngine = this.mc.getTextureManager();
/* 1182 */         TileEntityRendererDispatcher.instance.fontRenderer = this.mc.fontRendererObj;
/*      */       } 
/*      */       
/* 1185 */       if (this.mc.currentScreen != null) {
/* 1186 */         GlStateManager.clear(256);
/*      */         
/*      */         try {
/* 1189 */           if (Reflector.ForgeHooksClient_drawScreen.exists()) {
/* 1190 */             Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, new Object[] { this.mc.currentScreen, Integer.valueOf(k1), Integer.valueOf(l1), Float.valueOf(partialTicks) });
/*      */           } else {
/* 1192 */             this.mc.currentScreen.drawScreen(k1, l1, partialTicks);
/*      */           } 
/* 1194 */         } catch (Throwable throwable) {
/* 1195 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
/* 1196 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
/* 1197 */           crashreportcategory.addCrashSectionCallable("Screen name", () -> this.mc.currentScreen.getClass().getCanonicalName());
/* 1198 */           crashreportcategory.addCrashSectionCallable("Mouse location", () -> String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(k1), Integer.valueOf(l1), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) }));
/* 1199 */           crashreportcategory.addCrashSectionCallable("Screen size", () -> String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] { Integer.valueOf(scaledresolution.getScaledWidth()), Integer.valueOf(scaledresolution.getScaledHeight()), Integer.valueOf(this.mc.displayWidth), Integer.valueOf(this.mc.displayHeight), Integer.valueOf(scaledresolution.getScaleFactor()) }));
/* 1200 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1205 */     frameFinish();
/* 1206 */     waitForServerThread();
/* 1207 */     MemoryMonitor.update();
/* 1208 */     Lagometer.updateLagometer();
/*      */     
/* 1210 */     if (this.mc.gameSettings.ofProfiler) {
/* 1211 */       this.mc.gameSettings.showDebugProfilerChart = true;
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
/*      */   private boolean isDrawBlockOutline() {
/* 1223 */     Entity entity = this.mc.getRenderViewEntity();
/* 1224 */     boolean flag = (entity instanceof EntityPlayer && !this.mc.gameSettings.hideGUI);
/*      */     
/* 1226 */     if (flag && !((EntityPlayer)entity).capabilities.allowEdit) {
/* 1227 */       ItemStack itemstack = ((EntityPlayer)entity).getCurrentEquippedItem();
/*      */       
/* 1229 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 1230 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 1231 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/* 1232 */         Block block = iblockstate.getBlock();
/*      */         
/* 1234 */         if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
/* 1235 */           flag = (ReflectorForge.blockHasTileEntity(iblockstate) && this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory);
/*      */         } else {
/* 1237 */           flag = (itemstack != null && (itemstack.canDestroy(block) || itemstack.canPlaceOn(block)));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1242 */     return flag;
/*      */   }
/*      */   
/*      */   private void renderWorldDirections(float partialTicks) {
/* 1246 */     if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
/* 1247 */       Entity entity = this.mc.getRenderViewEntity();
/* 1248 */       GlStateManager.enableBlend();
/* 1249 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1250 */       GL11.glLineWidth(1.0F);
/* 1251 */       GlStateManager.disableTexture2D();
/* 1252 */       GlStateManager.depthMask(false);
/* 1253 */       GlStateManager.pushMatrix();
/* 1254 */       GlStateManager.matrixMode(5888);
/* 1255 */       GlStateManager.loadIdentity();
/* 1256 */       orientCamera(partialTicks);
/* 1257 */       GlStateManager.translate(0.0F, entity.getEyeHeight(), 0.0F);
/* 1258 */       RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.005D, 1.0E-4D, 1.0E-4D), 255, 0, 0, 255);
/* 1259 */       RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 1.0E-4D, 0.005D), 0, 0, 255, 255);
/* 1260 */       RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 0.0033D, 1.0E-4D), 0, 255, 0, 255);
/* 1261 */       GlStateManager.popMatrix();
/* 1262 */       GlStateManager.depthMask(true);
/* 1263 */       GlStateManager.enableTexture2D();
/* 1264 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderWorld(float partialTicks, long finishTimeNano) {
/* 1269 */     updateLightmap(partialTicks);
/*      */     
/* 1271 */     if (this.mc.getRenderViewEntity() == null) {
/* 1272 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/* 1275 */     getMouseOver(partialTicks);
/*      */     
/* 1277 */     if (Config.isShaders()) {
/* 1278 */       Shaders.beginRender(this.mc, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1281 */     GlStateManager.enableDepth();
/* 1282 */     GlStateManager.enableAlpha();
/* 1283 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1284 */     this.mc.mcProfiler.startSection("center");
/*      */     
/* 1286 */     if (this.mc.gameSettings.anaglyph) {
/* 1287 */       anaglyphField = 0;
/* 1288 */       GlStateManager.colorMask(false, true, true, false);
/* 1289 */       renderWorldPass(0, partialTicks, finishTimeNano);
/* 1290 */       anaglyphField = 1;
/* 1291 */       GlStateManager.colorMask(true, false, false, false);
/* 1292 */       renderWorldPass(1, partialTicks, finishTimeNano);
/* 1293 */       GlStateManager.colorMask(true, true, true, false);
/*      */     } else {
/* 1295 */       renderWorldPass(2, partialTicks, finishTimeNano);
/*      */     } 
/*      */     
/* 1298 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano) {
/* 1302 */     boolean flag = Config.isShaders();
/*      */     
/* 1304 */     if (flag) {
/* 1305 */       Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1308 */     RenderGlobal renderglobal = this.mc.renderGlobal;
/* 1309 */     EffectRenderer effectrenderer = this.mc.effectRenderer;
/* 1310 */     boolean flag1 = isDrawBlockOutline();
/* 1311 */     GlStateManager.enableCull();
/* 1312 */     this.mc.mcProfiler.endStartSection("clear");
/*      */     
/* 1314 */     if (flag) {
/* 1315 */       Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     } else {
/* 1317 */       GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     } 
/*      */     
/* 1320 */     updateFogColor(partialTicks);
/* 1321 */     GlStateManager.clear(16640);
/*      */     
/* 1323 */     if (flag) {
/* 1324 */       Shaders.clearRenderBuffer();
/*      */     }
/*      */     
/* 1327 */     this.mc.mcProfiler.endStartSection("camera");
/* 1328 */     setupCameraTransform(partialTicks, pass);
/*      */     
/* 1330 */     if (flag) {
/* 1331 */       Shaders.setCamera(partialTicks);
/*      */     }
/*      */     
/* 1334 */     ActiveRenderInfo.updateRenderInfo((EntityPlayer)this.mc.thePlayer, (this.mc.gameSettings.thirdPersonView == 2));
/* 1335 */     this.mc.mcProfiler.endStartSection("frustum");
/* 1336 */     ClippingHelper clippinghelper = ClippingHelperImpl.getInstance();
/* 1337 */     this.mc.mcProfiler.endStartSection("culling");
/* 1338 */     clippinghelper.disabled = (Config.isShaders() && !Shaders.isFrustumCulling());
/* 1339 */     Frustum frustum = new Frustum(clippinghelper);
/* 1340 */     Entity entity = this.mc.getRenderViewEntity();
/* 1341 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1342 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1343 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/*      */     
/* 1345 */     if (flag) {
/* 1346 */       ShadersRender.setFrustrumPosition((ICamera)frustum, d0, d1, d2);
/*      */     } else {
/* 1348 */       frustum.setPosition(d0, d1, d2);
/*      */     } 
/*      */     
/* 1351 */     if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
/* 1352 */       setupFog(-1, partialTicks);
/* 1353 */       this.mc.mcProfiler.endStartSection("sky");
/* 1354 */       GlStateManager.matrixMode(5889);
/* 1355 */       GlStateManager.loadIdentity();
/* 1356 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1357 */       GlStateManager.matrixMode(5888);
/*      */       
/* 1359 */       if (flag) {
/* 1360 */         Shaders.beginSky();
/*      */       }
/*      */       
/* 1363 */       renderglobal.renderSky(partialTicks, pass);
/*      */       
/* 1365 */       if (flag) {
/* 1366 */         Shaders.endSky();
/*      */       }
/*      */       
/* 1369 */       GlStateManager.matrixMode(5889);
/* 1370 */       GlStateManager.loadIdentity();
/* 1371 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1372 */       GlStateManager.matrixMode(5888);
/*      */     } else {
/* 1374 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1377 */     setupFog(0, partialTicks);
/* 1378 */     GlStateManager.shadeModel(7425);
/*      */     
/* 1380 */     if (entity.posY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/* 1381 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     }
/*      */     
/* 1384 */     this.mc.mcProfiler.endStartSection("prepareterrain");
/* 1385 */     setupFog(0, partialTicks);
/* 1386 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1387 */     RenderHelper.disableStandardItemLighting();
/* 1388 */     this.mc.mcProfiler.endStartSection("terrain_setup");
/* 1389 */     checkLoadVisibleChunks(entity, partialTicks, (ICamera)frustum, this.mc.thePlayer.isSpectator());
/*      */     
/* 1391 */     if (flag) {
/* 1392 */       ShadersRender.setupTerrain(renderglobal, entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     } else {
/* 1394 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     } 
/*      */     
/* 1397 */     if (pass == 0 || pass == 2) {
/* 1398 */       this.mc.mcProfiler.endStartSection("updatechunks");
/* 1399 */       Lagometer.timerChunkUpload.start();
/* 1400 */       this.mc.renderGlobal.updateChunks(finishTimeNano);
/* 1401 */       Lagometer.timerChunkUpload.end();
/*      */     } 
/*      */     
/* 1404 */     this.mc.mcProfiler.endStartSection("terrain");
/* 1405 */     Lagometer.timerTerrain.start();
/*      */     
/* 1407 */     if (this.mc.gameSettings.ofSmoothFps && pass > 0) {
/* 1408 */       this.mc.mcProfiler.endStartSection("finish");
/* 1409 */       GL11.glFinish();
/* 1410 */       this.mc.mcProfiler.endStartSection("terrain");
/*      */     } 
/*      */     
/* 1413 */     GlStateManager.matrixMode(5888);
/* 1414 */     GlStateManager.pushMatrix();
/* 1415 */     GlStateManager.disableAlpha();
/*      */     
/* 1417 */     if (flag) {
/* 1418 */       ShadersRender.beginTerrainSolid();
/*      */     }
/*      */     
/* 1421 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, pass, entity);
/* 1422 */     GlStateManager.enableAlpha();
/*      */     
/* 1424 */     if (flag) {
/* 1425 */       ShadersRender.beginTerrainCutoutMipped();
/*      */     }
/*      */     
/* 1428 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, (this.mc.gameSettings.mipmapLevels > 0));
/* 1429 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, entity);
/* 1430 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1431 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*      */     
/* 1433 */     if (flag) {
/* 1434 */       ShadersRender.beginTerrainCutout();
/*      */     }
/*      */     
/* 1437 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, entity);
/* 1438 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*      */     
/* 1440 */     if (flag) {
/* 1441 */       ShadersRender.endTerrain();
/*      */     }
/*      */     
/* 1444 */     Lagometer.timerTerrain.end();
/* 1445 */     GlStateManager.shadeModel(7424);
/* 1446 */     GlStateManager.alphaFunc(516, 0.1F);
/*      */     
/* 1448 */     if (!this.debugView) {
/* 1449 */       GlStateManager.matrixMode(5888);
/* 1450 */       GlStateManager.popMatrix();
/* 1451 */       GlStateManager.pushMatrix();
/* 1452 */       RenderHelper.enableStandardItemLighting();
/* 1453 */       this.mc.mcProfiler.endStartSection("entities");
/*      */       
/* 1455 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 1456 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*      */       }
/*      */       
/* 1459 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/*      */       
/* 1461 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 1462 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/*      */       }
/*      */       
/* 1465 */       RenderHelper.disableStandardItemLighting();
/* 1466 */       disableLightmap();
/* 1467 */       GlStateManager.matrixMode(5888);
/* 1468 */       GlStateManager.popMatrix();
/* 1469 */       GlStateManager.pushMatrix();
/*      */       
/* 1471 */       if (this.mc.objectMouseOver != null && entity.isInsideOfMaterial(Material.water) && flag1) {
/* 1472 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/* 1473 */         GlStateManager.disableAlpha();
/* 1474 */         this.mc.mcProfiler.endStartSection("outline");
/* 1475 */         renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
/* 1476 */         GlStateManager.enableAlpha();
/*      */       } 
/*      */     } 
/*      */     
/* 1480 */     GlStateManager.matrixMode(5888);
/* 1481 */     GlStateManager.popMatrix();
/*      */     
/* 1483 */     if (flag1 && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.water)) {
/* 1484 */       EntityPlayer entityplayer1 = (EntityPlayer)entity;
/* 1485 */       GlStateManager.disableAlpha();
/* 1486 */       this.mc.mcProfiler.endStartSection("outline");
/*      */       
/* 1488 */       if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, entityplayer1, this.mc.objectMouseOver, Integer.valueOf(0), entityplayer1.getHeldItem(), Float.valueOf(partialTicks) })) && !this.mc.gameSettings.hideGUI) {
/* 1489 */         renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, partialTicks);
/*      */       }
/* 1491 */       GlStateManager.enableAlpha();
/*      */     } 
/*      */     
/* 1494 */     if (!renderglobal.damagedBlocks.isEmpty()) {
/* 1495 */       this.mc.mcProfiler.endStartSection("destroyProgress");
/* 1496 */       GlStateManager.enableBlend();
/* 1497 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 1498 */       this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 1499 */       renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, partialTicks);
/* 1500 */       this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1501 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1504 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1505 */     GlStateManager.disableBlend();
/*      */     
/* 1507 */     if (!this.debugView) {
/* 1508 */       enableLightmap();
/* 1509 */       this.mc.mcProfiler.endStartSection("litParticles");
/*      */       
/* 1511 */       if (flag) {
/* 1512 */         Shaders.beginLitParticles();
/*      */       }
/*      */ 
/*      */       
/* 1516 */       RenderHelper.disableStandardItemLighting();
/* 1517 */       setupFog(0, partialTicks);
/* 1518 */       this.mc.mcProfiler.endStartSection("particles");
/*      */       
/* 1520 */       if (flag) {
/* 1521 */         Shaders.beginParticles();
/*      */       }
/*      */       
/* 1524 */       effectrenderer.renderParticles(entity, partialTicks);
/*      */       
/* 1526 */       if (flag) {
/* 1527 */         Shaders.endParticles();
/*      */       }
/*      */       
/* 1530 */       disableLightmap();
/*      */     } 
/*      */     
/* 1533 */     GlStateManager.depthMask(false);
/*      */     
/* 1535 */     if (Config.isShaders()) {
/* 1536 */       GlStateManager.depthMask(Shaders.isRainDepth());
/*      */     }
/*      */     
/* 1539 */     GlStateManager.enableCull();
/* 1540 */     this.mc.mcProfiler.endStartSection("weather");
/*      */     
/* 1542 */     if (flag) {
/* 1543 */       Shaders.beginWeather();
/*      */     }
/*      */     
/* 1546 */     renderRainSnow(partialTicks);
/*      */     
/* 1548 */     if (flag) {
/* 1549 */       Shaders.endWeather();
/*      */     }
/*      */     
/* 1552 */     GlStateManager.depthMask(true);
/* 1553 */     renderglobal.renderWorldBorder(entity, partialTicks);
/*      */     
/* 1555 */     if (flag) {
/* 1556 */       ShadersRender.renderHand0(this, partialTicks, pass);
/* 1557 */       Shaders.preWater();
/*      */     } 
/*      */     
/* 1560 */     GlStateManager.disableBlend();
/* 1561 */     GlStateManager.enableCull();
/* 1562 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1563 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1564 */     setupFog(0, partialTicks);
/* 1565 */     GlStateManager.enableBlend();
/* 1566 */     GlStateManager.depthMask(false);
/* 1567 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1568 */     GlStateManager.shadeModel(7425);
/* 1569 */     this.mc.mcProfiler.endStartSection("translucent");
/*      */     
/* 1571 */     if (flag) {
/* 1572 */       Shaders.beginWater();
/*      */     }
/*      */     
/* 1575 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, entity);
/*      */     
/* 1577 */     if (flag) {
/* 1578 */       Shaders.endWater();
/*      */     }
/*      */     
/* 1581 */     if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
/* 1582 */       RenderHelper.enableStandardItemLighting();
/* 1583 */       this.mc.mcProfiler.endStartSection("entities");
/* 1584 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 1585 */       this.mc.renderGlobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 1586 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1587 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 1588 */       RenderHelper.disableStandardItemLighting();
/*      */     } 
/*      */     
/* 1591 */     GlStateManager.shadeModel(7424);
/* 1592 */     GlStateManager.depthMask(true);
/* 1593 */     GlStateManager.enableCull();
/* 1594 */     GlStateManager.disableBlend();
/* 1595 */     GlStateManager.disableFog();
/*      */     
/* 1597 */     if (entity.posY + entity.getEyeHeight() >= 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/* 1598 */       this.mc.mcProfiler.endStartSection("aboveClouds");
/* 1599 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     } 
/*      */     
/* 1602 */     if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
/* 1603 */       this.mc.mcProfiler.endStartSection("forge_render_last");
/* 1604 */       Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { renderglobal, Float.valueOf(partialTicks) });
/*      */     } 
/*      */     
/* 1607 */     this.mc.mcProfiler.endStartSection("hand");
/*      */     
/* 1609 */     EventManager.call((Event)new EventRender(partialTicks));
/* 1610 */     EventManager.call((Event)new EventRender3D(partialTicks));
/*      */     
/* 1612 */     if (!Shaders.isShadowPass) {
/* 1613 */       if (flag) {
/* 1614 */         ShadersRender.renderHand1(this, partialTicks, pass);
/* 1615 */         Shaders.renderCompositeFinal();
/*      */       } 
/*      */       
/* 1618 */       GlStateManager.clear(256);
/*      */       
/* 1620 */       if (flag) {
/* 1621 */         ShadersRender.renderFPOverlay(this, partialTicks, pass);
/*      */       } else {
/* 1623 */         renderHand(partialTicks, pass);
/*      */       } 
/*      */       
/* 1626 */       renderWorldDirections(partialTicks);
/*      */     } 
/*      */     
/* 1629 */     if (flag) {
/* 1630 */       Shaders.endRender();
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderCloudsCheck(RenderGlobal renderGlobalIn, float partialTicks, int pass) {
/* 1635 */     if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
/* 1636 */       this.mc.mcProfiler.endStartSection("clouds");
/* 1637 */       GlStateManager.matrixMode(5889);
/* 1638 */       GlStateManager.loadIdentity();
/* 1639 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
/* 1640 */       GlStateManager.matrixMode(5888);
/* 1641 */       GlStateManager.pushMatrix();
/* 1642 */       setupFog(0, partialTicks);
/* 1643 */       renderGlobalIn.renderClouds(partialTicks, pass);
/* 1644 */       GlStateManager.disableFog();
/* 1645 */       GlStateManager.popMatrix();
/* 1646 */       GlStateManager.matrixMode(5889);
/* 1647 */       GlStateManager.loadIdentity();
/* 1648 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1649 */       GlStateManager.matrixMode(5888);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addRainParticles() {
/* 1654 */     if (NoWeather.getInstance.isEnabled()) {
/*      */       return;
/*      */     }
/* 1657 */     float f = this.mc.theWorld.getRainStrength(1.0F);
/*      */     
/* 1659 */     if (!Config.isRainFancy()) {
/* 1660 */       f /= 2.0F;
/*      */     }
/*      */     
/* 1663 */     if (f != 0.0F && Config.isRainSplash()) {
/* 1664 */       if (Atmosphere.getInstance.isEnabled() && (
/* 1665 */         Atmosphere.getInstance.weather_mode.is("Snowfall") || Atmosphere.getInstance.weather_mode.is("Snowstorm"))) {
/*      */         return;
/*      */       }
/*      */       
/* 1669 */       this.random.setSeed(this.rendererUpdateCount * 312987231L);
/* 1670 */       Entity entity = this.mc.getRenderViewEntity();
/* 1671 */       WorldClient worldClient = this.mc.theWorld;
/* 1672 */       BlockPos blockpos = new BlockPos(entity);
/* 1673 */       int i = 10;
/* 1674 */       double d0 = 0.0D;
/* 1675 */       double d1 = 0.0D;
/* 1676 */       double d2 = 0.0D;
/* 1677 */       int j = 0;
/* 1678 */       int k = (int)(100.0F * f * f);
/*      */       
/* 1680 */       if (this.mc.gameSettings.particleSetting == 1) {
/* 1681 */         k >>= 1;
/* 1682 */       } else if (this.mc.gameSettings.particleSetting == 2) {
/* 1683 */         k = 0;
/*      */       } 
/*      */       
/* 1686 */       for (int l = 0; l < k; l++) {
/* 1687 */         BlockPos blockpos1 = worldClient.getPrecipitationHeight(blockpos.add(this.random.nextInt(i) - this.random.nextInt(i), 0, this.random.nextInt(i) - this.random.nextInt(i)));
/* 1688 */         BiomeGenBase biomegenbase = worldClient.getBiomeGenForCoords(blockpos1);
/* 1689 */         BlockPos blockpos2 = blockpos1.down();
/* 1690 */         Block block = worldClient.getBlockState(blockpos2).getBlock();
/*      */         
/* 1692 */         if (blockpos1.getY() <= blockpos.getY() + i && blockpos1.getY() >= blockpos.getY() - i && biomegenbase.canRain() && biomegenbase.getFloatTemperature(blockpos1) >= 0.15F) {
/* 1693 */           double d3 = this.random.nextDouble();
/* 1694 */           double d4 = this.random.nextDouble();
/*      */           
/* 1696 */           if (block.getMaterial() == Material.lava) {
/* 1697 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, (blockpos1.getY() + 0.1F) - block.getBlockBoundsMinY(), blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 1698 */           } else if (block.getMaterial() != Material.air) {
/* 1699 */             block.setBlockBoundsBasedOnState((IBlockAccess)worldClient, blockpos2);
/* 1700 */             j++;
/*      */             
/* 1702 */             if (this.random.nextInt(j) == 0) {
/* 1703 */               d0 = blockpos2.getX() + d3;
/* 1704 */               d1 = (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY() - 1.0D;
/* 1705 */               d2 = blockpos2.getZ() + d4;
/*      */             } 
/*      */             
/* 1708 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, blockpos2.getX() + d3, (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY(), blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1713 */       if (j > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
/* 1714 */         this.rainSoundCounter = 0;
/*      */         
/* 1716 */         if (d1 > (blockpos.getY() + 1) && worldClient.getPrecipitationHeight(blockpos).getY() > MathHelper.floor_float(blockpos.getY())) {
/* 1717 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.1F, 0.5F, false);
/*      */         } else {
/* 1719 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.2F, 1.0F, false);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderRainSnow(float partialTicks) {
/* 1729 */     if (NoWeather.getInstance.isEnabled()) {
/*      */       return;
/*      */     }
/* 1732 */     if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
/* 1733 */       WorldProvider worldprovider = this.mc.theWorld.provider;
/* 1734 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
/*      */       
/* 1736 */       if (object != null) {
/* 1737 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.mc.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1742 */     float f5 = this.mc.theWorld.getRainStrength(partialTicks);
/*      */     
/* 1744 */     if (f5 > 0.0F) {
/* 1745 */       if (Config.isRainOff()) {
/*      */         return;
/*      */       }
/*      */       
/* 1749 */       enableLightmap();
/* 1750 */       Entity entity = this.mc.getRenderViewEntity();
/* 1751 */       WorldClient worldClient = this.mc.theWorld;
/* 1752 */       int i = MathHelper.floor_double(entity.posX);
/* 1753 */       int j = MathHelper.floor_double(entity.posY);
/* 1754 */       int k = MathHelper.floor_double(entity.posZ);
/* 1755 */       Tessellator tessellator = Tessellator.getInstance();
/* 1756 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1757 */       GlStateManager.disableCull();
/* 1758 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1759 */       GlStateManager.enableBlend();
/* 1760 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1761 */       GlStateManager.alphaFunc(516, 0.1F);
/* 1762 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1763 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1764 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 1765 */       int l = MathHelper.floor_double(d1);
/* 1766 */       int i1 = 5;
/*      */       
/* 1768 */       if (Config.isRainFancy()) {
/* 1769 */         i1 = 10;
/*      */       }
/*      */       
/* 1772 */       int j1 = -1;
/* 1773 */       float f = this.rendererUpdateCount + partialTicks;
/* 1774 */       worldrenderer.setTranslation(-d0, -d1, -d2);
/* 1775 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1776 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1778 */       for (int k1 = k - i1; k1 <= k + i1; k1++) {
/* 1779 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/* 1780 */           int i2 = (k1 - k + 16 << 5) + l1 - i + 16;
/* 1781 */           double d3 = this.rainXCoords[i2] * 0.5D;
/* 1782 */           double d4 = this.rainYCoords[i2] * 0.5D;
/* 1783 */           blockpos$mutableblockpos.set(l1, 0, k1);
/* 1784 */           BiomeGenBase biomegenbase = worldClient.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos);
/*      */           
/* 1786 */           if (biomegenbase.canRain() || biomegenbase.getEnableSnow()) {
/* 1787 */             int j2 = worldClient.getPrecipitationHeight((BlockPos)blockpos$mutableblockpos).getY();
/* 1788 */             int k2 = j - i1;
/* 1789 */             int l2 = j + i1;
/*      */             
/* 1791 */             if (k2 < j2) {
/* 1792 */               k2 = j2;
/*      */             }
/*      */             
/* 1795 */             if (l2 < j2) {
/* 1796 */               l2 = j2;
/*      */             }
/*      */             
/* 1799 */             int i3 = Math.max(j2, l);
/*      */             
/* 1801 */             if (k2 != l2) {
/* 1802 */               this.random.setSeed(l1 * l1 * 3121L + l1 * 45238971L ^ k1 * k1 * 418711L + k1 * 13761L);
/* 1803 */               blockpos$mutableblockpos.set(l1, k2, k1);
/* 1804 */               float f1 = biomegenbase.getFloatTemperature((BlockPos)blockpos$mutableblockpos);
/*      */               
/* 1806 */               boolean snow = (Atmosphere.getInstance.isEnabled() && (Atmosphere.getInstance.weather_mode.is("Snowstorm") || Atmosphere.getInstance.weather_mode.is("Snowfall")));
/* 1807 */               if (worldClient.getWorldChunkManager().getTemperatureAtHeight(f1, j2) >= 0.15F || snow) {
/* 1808 */                 if (j1 != 0) {
/* 1809 */                   if (j1 >= 0) {
/* 1810 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 1813 */                   j1 = 0;
/* 1814 */                   this.mc.getTextureManager().bindTexture(locationRainPng);
/* 1815 */                   worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 1818 */                 double d5 = ((this.rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 0x1F) + partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
/* 1819 */                 double d6 = (l1 + 0.5F) - entity.posX;
/* 1820 */                 double d7 = (k1 + 0.5F) - entity.posZ;
/* 1821 */                 float f2 = MathHelper.sqrt_double(d6 * d6 + d7 * d7) / i1;
/* 1822 */                 float f3 = ((1.0F - f2 * f2) * 0.5F + 0.5F) * f5;
/* 1823 */                 blockpos$mutableblockpos.set(l1, i3, k1);
/* 1824 */                 int j3 = worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0);
/* 1825 */                 int k3 = j3 >> 16 & 0xFFFF;
/* 1826 */                 int l3 = j3 & 0xFFFF;
/* 1827 */                 worldrenderer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/* 1828 */                 worldrenderer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/* 1829 */                 worldrenderer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/* 1830 */                 worldrenderer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/*      */               } else {
/* 1832 */                 if (j1 != 1) {
/* 1833 */                   if (j1 >= 0) {
/* 1834 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 1837 */                   j1 = 1;
/* 1838 */                   this.mc.getTextureManager().bindTexture(locationSnowPng);
/* 1839 */                   worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 1842 */                 double d8 = (((this.rendererUpdateCount & 0x1FF) + partialTicks) / 512.0F);
/* 1843 */                 double d9 = this.random.nextDouble() + f * 0.01D * (float)this.random.nextGaussian();
/* 1844 */                 double d10 = this.random.nextDouble() + (f * (float)this.random.nextGaussian()) * 0.001D;
/* 1845 */                 double d11 = (l1 + 0.5F) - entity.posX;
/* 1846 */                 double d12 = (k1 + 0.5F) - entity.posZ;
/* 1847 */                 float f6 = MathHelper.sqrt_double(d11 * d11 + d12 * d12) / i1;
/* 1848 */                 float f4 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f5;
/* 1849 */                 blockpos$mutableblockpos.set(l1, i3, k1);
/* 1850 */                 int i4 = (worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
/* 1851 */                 int j4 = i4 >> 16 & 0xFFFF;
/* 1852 */                 int k4 = i4 & 0xFFFF;
/* 1853 */                 worldrenderer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/* 1854 */                 worldrenderer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/* 1855 */                 worldrenderer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/* 1856 */                 worldrenderer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1863 */       if (j1 >= 0) {
/* 1864 */         tessellator.draw();
/*      */       }
/*      */       
/* 1867 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 1868 */       GlStateManager.enableCull();
/* 1869 */       GlStateManager.disableBlend();
/* 1870 */       GlStateManager.alphaFunc(516, 0.1F);
/* 1871 */       disableLightmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupOverlayRendering() {
/* 1879 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1880 */     GlStateManager.clear(256);
/* 1881 */     GlStateManager.matrixMode(5889);
/* 1882 */     GlStateManager.loadIdentity();
/* 1883 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
/* 1884 */     GlStateManager.matrixMode(5888);
/* 1885 */     GlStateManager.loadIdentity();
/* 1886 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFogColor(float partialTicks) {
/* 1893 */     WorldClient worldClient = this.mc.theWorld;
/* 1894 */     Entity entity = this.mc.getRenderViewEntity();
/* 1895 */     float f = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 32.0F;
/* 1896 */     f = 1.0F - (float)Math.pow(f, 0.25D);
/* 1897 */     Vec3 vec3 = worldClient.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 1898 */     vec3 = CustomColors.getWorldSkyColor(vec3, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 1899 */     float f1 = (float)vec3.xCoord;
/* 1900 */     float f2 = (float)vec3.yCoord;
/* 1901 */     float f3 = (float)vec3.zCoord;
/* 1902 */     Vec3 vec31 = worldClient.getFogColor(partialTicks);
/* 1903 */     vec31 = CustomColors.getWorldFogColor(vec31, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 1904 */     this.fogColorRed = (float)vec31.xCoord;
/* 1905 */     this.fogColorGreen = (float)vec31.yCoord;
/* 1906 */     this.fogColorBlue = (float)vec31.zCoord;
/*      */     
/* 1908 */     if (this.mc.gameSettings.renderDistanceChunks >= 4) {
/* 1909 */       double d0 = -1.0D;
/* 1910 */       Vec3 vec32 = (MathHelper.sin(worldClient.getCelestialAngleRadians(partialTicks)) > 0.0F) ? new Vec3(d0, 0.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
/* 1911 */       float f5 = (float)entity.getLook(partialTicks).dotProduct(vec32);
/*      */       
/* 1913 */       if (f5 < 0.0F) {
/* 1914 */         f5 = 0.0F;
/*      */       }
/*      */       
/* 1917 */       if (f5 > 0.0F) {
/* 1918 */         float[] afloat = ((World)worldClient).provider.calcSunriseSunsetColors(worldClient.getCelestialAngle(partialTicks), partialTicks);
/*      */         
/* 1920 */         if (afloat != null) {
/* 1921 */           f5 *= afloat[3];
/* 1922 */           this.fogColorRed = this.fogColorRed * (1.0F - f5) + afloat[0] * f5;
/* 1923 */           this.fogColorGreen = this.fogColorGreen * (1.0F - f5) + afloat[1] * f5;
/* 1924 */           this.fogColorBlue = this.fogColorBlue * (1.0F - f5) + afloat[2] * f5;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1929 */     this.fogColorRed += (f1 - this.fogColorRed) * f;
/* 1930 */     this.fogColorGreen += (f2 - this.fogColorGreen) * f;
/* 1931 */     this.fogColorBlue += (f3 - this.fogColorBlue) * f;
/* 1932 */     float f8 = worldClient.getRainStrength(partialTicks);
/*      */     
/* 1934 */     if (f8 > 0.0F) {
/* 1935 */       float f4 = 1.0F - f8 * 0.5F;
/* 1936 */       float f10 = 1.0F - f8 * 0.4F;
/* 1937 */       this.fogColorRed *= f4;
/* 1938 */       this.fogColorGreen *= f4;
/* 1939 */       this.fogColorBlue *= f10;
/*      */     } 
/*      */     
/* 1942 */     float f9 = worldClient.getThunderStrength(partialTicks);
/*      */     
/* 1944 */     if (f9 > 0.0F) {
/* 1945 */       float f11 = 1.0F - f9 * 0.5F;
/* 1946 */       this.fogColorRed *= f11;
/* 1947 */       this.fogColorGreen *= f11;
/* 1948 */       this.fogColorBlue *= f11;
/*      */     } 
/*      */     
/* 1951 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/* 1953 */     if (this.cloudFog) {
/* 1954 */       Vec3 vec33 = worldClient.getCloudColour(partialTicks);
/* 1955 */       this.fogColorRed = (float)vec33.xCoord;
/* 1956 */       this.fogColorGreen = (float)vec33.yCoord;
/* 1957 */       this.fogColorBlue = (float)vec33.zCoord;
/* 1958 */     } else if (block.getMaterial() == Material.water) {
/* 1959 */       float f12 = EnchantmentHelper.getRespiration(entity) * 0.2F;
/* 1960 */       f12 = Config.limit(f12, 0.0F, 0.6F);
/*      */       
/* 1962 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing)) {
/* 1963 */         f12 = f12 * 0.3F + 0.6F;
/*      */       }
/*      */       
/* 1966 */       this.fogColorRed = 0.02F + f12;
/* 1967 */       this.fogColorGreen = 0.02F + f12;
/* 1968 */       this.fogColorBlue = 0.2F + f12;
/* 1969 */       Vec3 vec35 = CustomColors.getUnderwaterColor((IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 1971 */       if (vec35 != null) {
/* 1972 */         this.fogColorRed = (float)vec35.xCoord;
/* 1973 */         this.fogColorGreen = (float)vec35.yCoord;
/* 1974 */         this.fogColorBlue = (float)vec35.zCoord;
/*      */       } 
/* 1976 */     } else if (block.getMaterial() == Material.lava) {
/* 1977 */       this.fogColorRed = 0.6F;
/* 1978 */       this.fogColorGreen = 0.1F;
/* 1979 */       this.fogColorBlue = 0.0F;
/* 1980 */       Vec3 vec34 = CustomColors.getUnderlavaColor((IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 1982 */       if (vec34 != null) {
/* 1983 */         this.fogColorRed = (float)vec34.xCoord;
/* 1984 */         this.fogColorGreen = (float)vec34.yCoord;
/* 1985 */         this.fogColorBlue = (float)vec34.zCoord;
/*      */       } 
/*      */     } 
/*      */     
/* 1989 */     float f13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
/* 1990 */     this.fogColorRed *= f13;
/* 1991 */     this.fogColorGreen *= f13;
/* 1992 */     this.fogColorBlue *= f13;
/* 1993 */     double d1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) * ((World)worldClient).provider.getVoidFogYFactor();
/*      */     
/* 1995 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/* 1996 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 1998 */       if (i < 20) {
/* 1999 */         d1 *= (1.0F - i / 20.0F);
/*      */       } else {
/* 2001 */         d1 = 0.0D;
/*      */       } 
/*      */     } 
/*      */     
/* 2005 */     if (d1 < 1.0D) {
/* 2006 */       if (d1 < 0.0D) {
/* 2007 */         d1 = 0.0D;
/*      */       }
/*      */       
/* 2010 */       d1 *= d1;
/* 2011 */       this.fogColorRed = (float)(this.fogColorRed * d1);
/* 2012 */       this.fogColorGreen = (float)(this.fogColorGreen * d1);
/* 2013 */       this.fogColorBlue = (float)(this.fogColorBlue * d1);
/*      */     } 
/*      */     
/* 2016 */     if (this.bossColorModifier > 0.0F) {
/* 2017 */       float f14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 2018 */       this.fogColorRed = this.fogColorRed * (1.0F - f14) + this.fogColorRed * 0.7F * f14;
/* 2019 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f14) + this.fogColorGreen * 0.6F * f14;
/* 2020 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f14) + this.fogColorBlue * 0.6F * f14;
/*      */     } 
/*      */     
/* 2023 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.nightVision)) {
/* 2024 */       float f15 = getNightVisionBrightness((EntityLivingBase)entity, partialTicks);
/* 2025 */       float f6 = 1.0F / this.fogColorRed;
/*      */       
/* 2027 */       if (f6 > 1.0F / this.fogColorGreen) {
/* 2028 */         f6 = 1.0F / this.fogColorGreen;
/*      */       }
/*      */       
/* 2031 */       if (f6 > 1.0F / this.fogColorBlue) {
/* 2032 */         f6 = 1.0F / this.fogColorBlue;
/*      */       }
/*      */       
/* 2035 */       if (Float.isInfinite(f6)) {
/* 2036 */         f6 = Math.nextAfter(f6, 0.0D);
/*      */       }
/*      */       
/* 2039 */       this.fogColorRed = this.fogColorRed * (1.0F - f15) + this.fogColorRed * f6 * f15;
/* 2040 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f15) + this.fogColorGreen * f6 * f15;
/* 2041 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f15) + this.fogColorBlue * f6 * f15;
/*      */     } 
/*      */     
/* 2044 */     if (this.mc.gameSettings.anaglyph) {
/* 2045 */       float f16 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
/* 2046 */       float f17 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
/* 2047 */       float f7 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
/* 2048 */       this.fogColorRed = f16;
/* 2049 */       this.fogColorGreen = f17;
/* 2050 */       this.fogColorBlue = f7;
/*      */     } 
/*      */     
/* 2053 */     if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
/* 2054 */       Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(this.fogColorRed), Float.valueOf(this.fogColorGreen), Float.valueOf(this.fogColorBlue) });
/* 2055 */       Reflector.postForgeBusEvent(object);
/* 2056 */       this.fogColorRed = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
/* 2057 */       this.fogColorGreen = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
/* 2058 */       this.fogColorBlue = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
/*      */     } 
/*      */     
/* 2061 */     Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setupFog(int startCoords, float partialTicks) {
/* 2071 */     this.fogStandard = false;
/* 2072 */     Entity entity = this.mc.getRenderViewEntity();
/*      */     
/* 2074 */     GL11.glFog(2918, setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
/* 2075 */     GL11.glNormal3f(0.0F, -1.0F, 0.0F);
/* 2076 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2077 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/* 2078 */     float f = -1.0F;
/*      */     
/* 2080 */     if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
/* 2081 */       f = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(0.1F) });
/*      */     }
/*      */     
/* 2084 */     if (f >= 0.0F) {
/* 2085 */       GlStateManager.setFogDensity(f);
/* 2086 */     } else if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/* 2087 */       float f4 = 5.0F;
/* 2088 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 2090 */       if (i < 20) {
/* 2091 */         f4 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - i / 20.0F);
/*      */       }
/*      */       
/* 2094 */       GlStateManager.setFog(9729);
/*      */       
/* 2096 */       if (startCoords == -1) {
/* 2097 */         GlStateManager.setFogStart(0.0F);
/* 2098 */         GlStateManager.setFogEnd(f4 * 0.8F);
/*      */       } else {
/* 2100 */         GlStateManager.setFogStart(f4 * 0.25F);
/* 2101 */         GlStateManager.setFogEnd(f4);
/*      */       } 
/*      */       
/* 2104 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance && Config.isFogFancy()) {
/* 2105 */         GL11.glFogi(34138, 34139);
/*      */       }
/* 2107 */     } else if (this.cloudFog) {
/* 2108 */       GlStateManager.setFog(2048);
/* 2109 */       GlStateManager.setFogDensity(0.1F);
/* 2110 */     } else if (block.getMaterial() == Material.water) {
/* 2111 */       GlStateManager.setFog(2048);
/* 2112 */       float f1 = Config.isClearWater() ? 0.02F : 0.1F;
/*      */       
/* 2114 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing)) {
/* 2115 */         GlStateManager.setFogDensity(0.01F);
/*      */       } else {
/* 2117 */         float f2 = 0.1F - EnchantmentHelper.getRespiration(entity) * 0.03F;
/* 2118 */         GlStateManager.setFogDensity(Config.limit(f2, 0.0F, f1));
/*      */       } 
/* 2120 */     } else if (block.getMaterial() == Material.lava) {
/* 2121 */       GlStateManager.setFog(2048);
/* 2122 */       GlStateManager.setFogDensity(2.0F);
/*      */     } else {
/* 2124 */       float f3 = this.farPlaneDistance;
/* 2125 */       this.fogStandard = true;
/* 2126 */       GlStateManager.setFog(9729);
/*      */       
/* 2128 */       if (startCoords == -1) {
/* 2129 */         GlStateManager.setFogStart(0.0F);
/*      */       } else {
/* 2131 */         GlStateManager.setFogStart(f3 * Config.getFogStart());
/*      */       } 
/* 2133 */       GlStateManager.setFogEnd(f3);
/*      */       
/* 2135 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance) {
/* 2136 */         if (Config.isFogFancy()) {
/* 2137 */           GL11.glFogi(34138, 34139);
/*      */         }
/*      */         
/* 2140 */         if (Config.isFogFast()) {
/* 2141 */           GL11.glFogi(34138, 34140);
/*      */         }
/*      */       } 
/*      */       
/* 2145 */       if (this.mc.theWorld.provider.doesXZShowFog((int)entity.posX, (int)entity.posZ)) {
/* 2146 */         GlStateManager.setFogStart(f3 * 0.05F);
/* 2147 */         GlStateManager.setFogEnd(f3);
/*      */       } 
/*      */       
/* 2150 */       if (Reflector.ForgeHooksClient_onFogRender.exists()) {
/* 2151 */         Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, new Object[] { this, entity, block, Float.valueOf(partialTicks), Integer.valueOf(startCoords), Float.valueOf(f3) });
/*      */       }
/*      */     } 
/*      */     
/* 2155 */     GlStateManager.enableColorMaterial();
/* 2156 */     GlStateManager.enableFog();
/* 2157 */     GlStateManager.colorMaterial(1028, 4608);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha) {
/* 2164 */     if (Config.isShaders()) {
/* 2165 */       Shaders.setFogColor(red, green, blue);
/*      */     }
/*      */     
/* 2168 */     this.fogColorBuffer.clear();
/* 2169 */     this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
/* 2170 */     this.fogColorBuffer.flip();
/* 2171 */     return this.fogColorBuffer;
/*      */   }
/*      */   
/*      */   public MapItemRenderer getMapItemRenderer() {
/* 2175 */     return this.theMapItemRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   private void waitForServerThread() {
/* 2180 */     if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
/* 2181 */       if (this.mc.isIntegratedServerRunning()) {
/* 2182 */         IntegratedServer integratedserver = this.mc.getIntegratedServer();
/*      */         
/* 2184 */         if (integratedserver != null) {
/* 2185 */           boolean flag = this.mc.isGamePaused();
/*      */           
/* 2187 */           if (!flag && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain)) {
/* 2188 */             if (this.serverWaitTime > 0) {
/* 2189 */               Lagometer.timerServer.start();
/* 2190 */               Config.sleep(this.serverWaitTime);
/* 2191 */               Lagometer.timerServer.end();
/*      */             } 
/*      */             
/* 2194 */             long i = System.nanoTime() / 1000000L;
/*      */             
/* 2196 */             if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
/* 2197 */               long j = i - this.lastServerTime;
/*      */               
/* 2199 */               if (j < 0L) {
/* 2200 */                 this.lastServerTime = i;
/* 2201 */                 j = 0L;
/*      */               } 
/*      */               
/* 2204 */               if (j >= 50L) {
/* 2205 */                 this.lastServerTime = i;
/* 2206 */                 int k = integratedserver.getTickCounter();
/* 2207 */                 int l = k - this.lastServerTicks;
/*      */                 
/* 2209 */                 if (l < 0) {
/* 2210 */                   this.lastServerTicks = k;
/* 2211 */                   l = 0;
/*      */                 } 
/*      */                 
/* 2214 */                 if (l < 1 && this.serverWaitTime < 100) {
/* 2215 */                   this.serverWaitTime += 2;
/*      */                 }
/*      */                 
/* 2218 */                 if (l > 1 && this.serverWaitTime > 0) {
/* 2219 */                   this.serverWaitTime--;
/*      */                 }
/*      */                 
/* 2222 */                 this.lastServerTicks = k;
/*      */               } 
/*      */             } else {
/* 2225 */               this.lastServerTime = i;
/* 2226 */               this.lastServerTicks = integratedserver.getTickCounter();
/*      */             } 
/*      */           } else {
/* 2229 */             if (this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain) {
/* 2230 */               Config.sleep(20L);
/*      */             }
/*      */             
/* 2233 */             this.lastServerTime = 0L;
/* 2234 */             this.lastServerTicks = 0;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 2239 */       this.lastServerTime = 0L;
/* 2240 */       this.lastServerTicks = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void frameInit() {
/* 2245 */     GlErrors.frameStart();
/*      */     
/* 2247 */     if (!this.initialized) {
/* 2248 */       ReflectorResolver.resolve();
/* 2249 */       TextureUtils.registerResourceListener();
/*      */       
/* 2251 */       if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
/* 2252 */         Config.setNotify64BitJava(true);
/*      */       }
/*      */       
/* 2255 */       this.initialized = true;
/*      */     } 
/*      */     
/* 2258 */     Config.checkDisplayMode();
/* 2259 */     WorldClient worldClient = this.mc.theWorld;
/*      */     
/* 2261 */     if (worldClient != null) {
/* 2262 */       if (Config.getNewRelease() != null) {
/* 2263 */         String s = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
/* 2264 */         String s1 = s + " " + Config.getNewRelease();
/* 2265 */         ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.newVersion", new Object[] { "§n" + s1 + "§r" }));
/* 2266 */         chatcomponenttext.setChatStyle((new ChatStyle()).setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://optifine.net/downloads")));
/* 2267 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext);
/* 2268 */         Config.setNewRelease(null);
/*      */       } 
/*      */       
/* 2271 */       if (Config.isNotify64BitJava()) {
/* 2272 */         Config.setNotify64BitJava(false);
/* 2273 */         ChatComponentText chatcomponenttext1 = new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0]));
/* 2274 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext1);
/*      */       } 
/*      */     } 
/*      */     
/* 2278 */     if (this.updatedWorld != worldClient) {
/* 2279 */       RandomEntities.worldChanged(this.updatedWorld, (World)worldClient);
/* 2280 */       Config.updateThreadPriorities();
/* 2281 */       this.lastServerTime = 0L;
/* 2282 */       this.lastServerTicks = 0;
/* 2283 */       this.updatedWorld = (World)worldClient;
/*      */     } 
/*      */     
/* 2286 */     if (!setFxaaShader(Shaders.configAntialiasingLevel)) {
/* 2287 */       Shaders.configAntialiasingLevel = 0;
/*      */     }
/*      */     
/* 2290 */     if (this.mc.currentScreen != null && this.mc.currentScreen.getClass() == GuiChat.class) {
/* 2291 */       this.mc.displayGuiScreen((GuiScreen)new GuiChatOF((GuiChat)this.mc.currentScreen));
/*      */     }
/*      */   }
/*      */   
/*      */   private void frameFinish() {
/* 2296 */     if (this.mc.theWorld != null && Config.isShowGlErrors() && TimedEvent.isActive("CheckGlErrorFrameFinish", 10000L)) {
/* 2297 */       int i = GlStateManager.glGetError();
/*      */       
/* 2299 */       if (i != 0 && GlErrors.isEnabled(i)) {
/* 2300 */         String s = Config.getGlErrorString(i);
/* 2301 */         ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s }));
/* 2302 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setFxaaShader(int p_setFxaaShader_1_) {
/* 2309 */     if (!OpenGlHelper.isFramebufferEnabled())
/* 2310 */       return false; 
/* 2311 */     if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4])
/* 2312 */       return true; 
/* 2313 */     if (p_setFxaaShader_1_ != 2 && p_setFxaaShader_1_ != 4) {
/* 2314 */       if (this.theShaderGroup != null) {
/* 2315 */         this.theShaderGroup.deleteShaderGroup();
/* 2316 */         this.theShaderGroup = null;
/*      */       } 
/* 2318 */       return true;
/* 2319 */     }  if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[p_setFxaaShader_1_])
/* 2320 */       return true; 
/* 2321 */     if (this.mc.theWorld == null) {
/* 2322 */       return true;
/*      */     }
/* 2324 */     loadShader(new ResourceLocation("shaders/post/fxaa_of_" + p_setFxaaShader_1_ + "x.json"));
/* 2325 */     this.fxaaShaders[p_setFxaaShader_1_] = this.theShaderGroup;
/* 2326 */     return this.useShader;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkLoadVisibleChunks(Entity p_checkLoadVisibleChunks_1_, float p_checkLoadVisibleChunks_2_, ICamera p_checkLoadVisibleChunks_3_, boolean p_checkLoadVisibleChunks_4_) {
/* 2331 */     int i = 201435902;
/*      */     
/* 2333 */     if (this.loadVisibleChunks) {
/* 2334 */       this.loadVisibleChunks = false;
/* 2335 */       loadAllVisibleChunks(p_checkLoadVisibleChunks_1_, p_checkLoadVisibleChunks_2_, p_checkLoadVisibleChunks_3_, p_checkLoadVisibleChunks_4_);
/* 2336 */       this.mc.ingameGUI.getChatGUI().deleteChatLine(i);
/*      */     } 
/*      */     
/* 2339 */     if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(38)) {
/* 2340 */       if (this.mc.currentScreen != null) {
/*      */         return;
/*      */       }
/*      */       
/* 2344 */       this.loadVisibleChunks = true;
/* 2345 */       ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.loadingVisibleChunks", new Object[0]));
/* 2346 */       this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((IChatComponent)chatcomponenttext, i);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadAllVisibleChunks(Entity p_loadAllVisibleChunks_1_, double p_loadAllVisibleChunks_2_, ICamera p_loadAllVisibleChunks_4_, boolean p_loadAllVisibleChunks_5_) {
/* 2351 */     int i = this.mc.gameSettings.ofChunkUpdates;
/* 2352 */     boolean flag = this.mc.gameSettings.ofLazyChunkLoading;
/*      */     try {
/*      */       boolean flag1;
/* 2355 */       this.mc.gameSettings.ofChunkUpdates = 1000;
/* 2356 */       this.mc.gameSettings.ofLazyChunkLoading = false;
/* 2357 */       RenderGlobal renderglobal = Config.getRenderGlobal();
/* 2358 */       int j = renderglobal.getCountLoadedChunks();
/* 2359 */       Config.dbg("Loading visible chunks");
/* 2360 */       long l = System.currentTimeMillis() + 5000L;
/* 2361 */       int i1 = 0;
/*      */ 
/*      */       
/*      */       do {
/* 2365 */         flag1 = false;
/*      */         
/* 2367 */         for (int j1 = 0; j1 < 100; j1++) {
/* 2368 */           renderglobal.displayListEntitiesDirty = true;
/* 2369 */           renderglobal.setupTerrain(p_loadAllVisibleChunks_1_, p_loadAllVisibleChunks_2_, p_loadAllVisibleChunks_4_, this.frameCount++, p_loadAllVisibleChunks_5_);
/*      */           
/* 2371 */           if (!renderglobal.hasNoChunkUpdates()) {
/* 2372 */             flag1 = true;
/*      */           }
/*      */           
/* 2375 */           i1 += renderglobal.getCountChunksToUpdate();
/*      */           
/* 2377 */           while (!renderglobal.hasNoChunkUpdates()) {
/* 2378 */             renderglobal.updateChunks(System.nanoTime() + 1000000000L);
/*      */           }
/*      */           
/* 2381 */           i1 -= renderglobal.getCountChunksToUpdate();
/*      */           
/* 2383 */           if (!flag1) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/* 2388 */         if (renderglobal.getCountLoadedChunks() != j) {
/* 2389 */           flag1 = true;
/* 2390 */           j = renderglobal.getCountLoadedChunks();
/*      */         } 
/*      */         
/* 2393 */         if (System.currentTimeMillis() <= l)
/* 2394 */           continue;  Config.log("Chunks loaded: " + i1);
/* 2395 */         l = System.currentTimeMillis() + 5000L;
/*      */       
/*      */       }
/* 2398 */       while (flag1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2403 */       Config.log("Chunks loaded: " + i1);
/* 2404 */       Config.log("Finished loading visible chunks");
/* 2405 */       RenderChunk.renderChunksUpdated = 0;
/*      */     } finally {
/* 2407 */       this.mc.gameSettings.ofChunkUpdates = i;
/* 2408 */       this.mc.gameSettings.ofLazyChunkLoading = flag;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\EntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */