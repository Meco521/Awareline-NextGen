/*     */ package net.minecraft.client.renderer.chunk;
/*     */ import awareline.main.mod.implement.globals.ChunkAnimator;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCache;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.ViewFrustum;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.CustomBlockLayers;
/*     */ import net.optifine.override.ChunkCacheOF;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.render.AabbFrame;
/*     */ import net.optifine.render.RenderEnv;
/*     */ 
/*     */ public class RenderChunk {
/*     */   private final World world;
/*     */   private final RenderGlobal renderGlobal;
/*  48 */   public CompiledChunk compiledChunk = CompiledChunk.DUMMY; public static int renderChunksUpdated; private BlockPos position;
/*  49 */   private final ReentrantLock lockCompileTask = new ReentrantLock();
/*  50 */   private final ReentrantLock lockCompiledChunk = new ReentrantLock();
/*  51 */   private ChunkCompileTaskGenerator compileTask = null;
/*  52 */   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
/*     */   private final int index;
/*  54 */   private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
/*  55 */   private final VertexBuffer[] vertexBuffers = new VertexBuffer[(EnumWorldBlockLayer.values()).length];
/*     */   public AxisAlignedBB boundingBox;
/*  57 */   private int frameIndex = -1;
/*     */   private boolean needsUpdate = true;
/*  59 */   private final EnumMap<EnumFacing, BlockPos> mapEnumFacing = null;
/*  60 */   private final BlockPos[] positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
/*  61 */   public static final EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
/*  62 */   private final EnumWorldBlockLayer[] blockLayersSingle = new EnumWorldBlockLayer[1];
/*  63 */   private final boolean isMipmaps = Config.isMipmaps();
/*  64 */   private final boolean fixBlockLayer = !Reflector.BetterFoliageClient.exists();
/*     */   private boolean playerUpdate = false;
/*     */   public int regionX;
/*     */   public int regionZ;
/*  68 */   private final RenderChunk[] renderChunksOfset16 = new RenderChunk[6];
/*     */   private boolean renderChunksOffset16Updated = false;
/*     */   private Chunk chunk;
/*  71 */   private final RenderChunk[] renderChunkNeighbours = new RenderChunk[EnumFacing.VALUES.length];
/*  72 */   private final RenderChunk[] renderChunkNeighboursValid = new RenderChunk[EnumFacing.VALUES.length];
/*     */   private boolean renderChunkNeighboursUpated = false;
/*  74 */   private final RenderGlobal.ContainerLocalRenderInformation renderInfo = new RenderGlobal.ContainerLocalRenderInformation(this, (EnumFacing)null, 0);
/*     */   
/*     */   public AabbFrame boundingBoxParent;
/*     */   
/*     */   public RenderChunk(World worldIn, RenderGlobal renderGlobalIn, BlockPos blockPosIn, int indexIn) {
/*  79 */     this.world = worldIn;
/*  80 */     this.renderGlobal = renderGlobalIn;
/*  81 */     this.index = indexIn;
/*     */     
/*  83 */     if (!blockPosIn.equals(this.position))
/*     */     {
/*  85 */       setPosition(blockPosIn);
/*     */     }
/*     */     
/*  88 */     if (OpenGlHelper.useVbo())
/*     */     {
/*  90 */       for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++)
/*     */       {
/*  92 */         this.vertexBuffers[i] = new VertexBuffer(DefaultVertexFormats.BLOCK);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setFrameIndex(int frameIndexIn) {
/*  99 */     if (this.frameIndex == frameIndexIn)
/*     */     {
/* 101 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 105 */     this.frameIndex = frameIndexIn;
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexBuffer getVertexBufferByLayer(int layer) {
/* 112 */     return this.vertexBuffers[layer];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(BlockPos pos) {
/* 117 */     if (ChunkAnimator.getInstance.isEnabled()) {
/* 118 */       ChunkAnimator.getInstance.animation.setPosition(this, pos);
/*     */     }
/* 120 */     stopCompileTask();
/* 121 */     this.position = pos;
/* 122 */     int i = 8;
/* 123 */     this.regionX = pos.getX() >> i << i;
/* 124 */     this.regionZ = pos.getZ() >> i << i;
/* 125 */     this.boundingBox = new AxisAlignedBB(pos, pos.add(16, 16, 16));
/* 126 */     initModelviewMatrix();
/*     */     
/* 128 */     for (int j = 0; j < this.positionOffsets16.length; j++)
/*     */     {
/* 130 */       this.positionOffsets16[j] = null;
/*     */     }
/*     */     
/* 133 */     this.renderChunksOffset16Updated = false;
/* 134 */     this.renderChunkNeighboursUpated = false;
/*     */     
/* 136 */     for (int k = 0; k < this.renderChunkNeighbours.length; k++) {
/*     */       
/* 138 */       RenderChunk renderchunk = this.renderChunkNeighbours[k];
/*     */       
/* 140 */       if (renderchunk != null)
/*     */       {
/* 142 */         renderchunk.renderChunkNeighboursUpated = false;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     this.chunk = null;
/* 147 */     this.boundingBoxParent = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resortTransparency(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 152 */     CompiledChunk compiledchunk = generator.getCompiledChunk();
/*     */     
/* 154 */     if (compiledchunk.getState() != null && !compiledchunk.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
/*     */       
/* 156 */       WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
/* 157 */       preRenderBlocks(worldrenderer, this.position);
/* 158 */       worldrenderer.setVertexState(compiledchunk.getState());
/* 159 */       postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, x, y, z, worldrenderer, compiledchunk);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 165 */     CompiledChunk compiledchunk = new CompiledChunk();
/* 166 */     BlockPos blockpos = new BlockPos((Vec3i)this.position);
/* 167 */     BlockPos blockpos1 = blockpos.add(15, 15, 15);
/* 168 */     generator.getLock().lock();
/*     */ 
/*     */     
/*     */     try {
/* 172 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 177 */       generator.setCompiledChunk(compiledchunk);
/*     */     }
/*     */     finally {
/*     */       
/* 181 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/* 184 */     VisGraph lvt_10_1_ = new VisGraph();
/* 185 */     HashSet<TileEntity> lvt_11_1_ = Sets.newHashSet();
/*     */     
/* 187 */     if (!isChunkRegionEmpty(blockpos)) {
/*     */       
/* 189 */       renderChunksUpdated++;
/* 190 */       ChunkCacheOF chunkcacheof = makeChunkCacheOF(blockpos);
/* 191 */       chunkcacheof.renderStart();
/* 192 */       boolean[] aboolean = new boolean[ENUM_WORLD_BLOCK_LAYERS.length];
/* 193 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 194 */       boolean flag = Reflector.ForgeBlock_canRenderInLayer.exists();
/* 195 */       boolean flag1 = Reflector.ForgeHooksClient_setRenderLayer.exists();
/*     */       
/* 197 */       for (Object e : BlockPosM.getAllInBoxMutable(blockpos, blockpos1)) {
/*     */         EnumWorldBlockLayer[] aenumworldblocklayer;
/* 199 */         BlockPosM blockposm = (BlockPosM)e;
/* 200 */         IBlockState iblockstate = chunkcacheof.getBlockState((BlockPos)blockposm);
/* 201 */         Block block = iblockstate.getBlock();
/*     */         
/* 203 */         if (block.isOpaqueCube())
/*     */         {
/* 205 */           lvt_10_1_.func_178606_a((BlockPos)blockposm);
/*     */         }
/*     */         
/* 208 */         if (ReflectorForge.blockHasTileEntity(iblockstate)) {
/*     */           
/* 210 */           TileEntity tileentity = chunkcacheof.getTileEntity(new BlockPos((Vec3i)blockposm));
/* 211 */           TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.getSpecialRenderer(tileentity);
/*     */           
/* 213 */           if (tileentity != null && tileentityspecialrenderer != null) {
/*     */             
/* 215 */             compiledchunk.addTileEntity(tileentity);
/*     */             
/* 217 */             if (tileentityspecialrenderer.forceTileEntityRender())
/*     */             {
/* 219 */               lvt_11_1_.add(tileentity);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 226 */         if (flag) {
/*     */           
/* 228 */           aenumworldblocklayer = ENUM_WORLD_BLOCK_LAYERS;
/*     */         }
/*     */         else {
/*     */           
/* 232 */           aenumworldblocklayer = this.blockLayersSingle;
/* 233 */           aenumworldblocklayer[0] = block.getBlockLayer();
/*     */         } 
/*     */         
/* 236 */         for (int j = 0; j < aenumworldblocklayer.length; j++) {
/*     */           
/* 238 */           EnumWorldBlockLayer enumworldblocklayer = aenumworldblocklayer[j];
/*     */           
/* 240 */           if (flag) {
/*     */             
/* 242 */             boolean flag2 = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInLayer, new Object[] { enumworldblocklayer });
/*     */             
/* 244 */             if (!flag2) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 250 */           if (flag1)
/*     */           {
/* 252 */             Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { enumworldblocklayer });
/*     */           }
/*     */           
/* 255 */           enumworldblocklayer = fixBlockLayer(iblockstate, enumworldblocklayer);
/* 256 */           int k = enumworldblocklayer.ordinal();
/*     */           
/* 258 */           if (block.getRenderType() != -1) {
/*     */             
/* 260 */             WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(k);
/* 261 */             worldrenderer.setBlockLayer(enumworldblocklayer);
/* 262 */             RenderEnv renderenv = worldrenderer.getRenderEnv(iblockstate, (BlockPos)blockposm);
/* 263 */             renderenv.setRegionRenderCacheBuilder(generator.getRegionRenderCacheBuilder());
/*     */             
/* 265 */             if (!compiledchunk.isLayerStarted(enumworldblocklayer)) {
/*     */               
/* 267 */               compiledchunk.setLayerStarted(enumworldblocklayer);
/* 268 */               preRenderBlocks(worldrenderer, blockpos);
/*     */             } 
/*     */             
/* 271 */             aboolean[k] = aboolean[k] | blockrendererdispatcher.renderBlock(iblockstate, (BlockPos)blockposm, (IBlockAccess)chunkcacheof, worldrenderer);
/*     */             
/* 273 */             if (renderenv.isOverlaysRendered()) {
/*     */               
/* 275 */               postRenderOverlays(generator.getRegionRenderCacheBuilder(), compiledchunk, aboolean);
/* 276 */               renderenv.setOverlaysRendered(false);
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 281 */         if (flag1)
/*     */         {
/* 283 */           Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { null });
/*     */         }
/*     */       } 
/*     */       
/* 287 */       for (EnumWorldBlockLayer enumworldblocklayer1 : ENUM_WORLD_BLOCK_LAYERS) {
/*     */         
/* 289 */         if (aboolean[enumworldblocklayer1.ordinal()])
/*     */         {
/* 291 */           compiledchunk.setLayerUsed(enumworldblocklayer1);
/*     */         }
/*     */         
/* 294 */         if (compiledchunk.isLayerStarted(enumworldblocklayer1)) {
/*     */           
/* 296 */           if (Config.isShaders())
/*     */           {
/* 298 */             SVertexBuilder.calcNormalChunkLayer(generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer1));
/*     */           }
/*     */           
/* 301 */           WorldRenderer worldrenderer1 = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer1);
/* 302 */           postRenderBlocks(enumworldblocklayer1, x, y, z, worldrenderer1, compiledchunk);
/*     */           
/* 304 */           if (worldrenderer1.animatedSprites != null)
/*     */           {
/* 306 */             compiledchunk.setAnimatedSprites(enumworldblocklayer1, (BitSet)worldrenderer1.animatedSprites.clone());
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 311 */           compiledchunk.setAnimatedSprites(enumworldblocklayer1, (BitSet)null);
/*     */         } 
/*     */       } 
/*     */       
/* 315 */       chunkcacheof.renderFinish();
/*     */     } 
/*     */     
/* 318 */     compiledchunk.setVisibility(lvt_10_1_.computeVisibility());
/* 319 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 323 */       Set<TileEntity> set = Sets.newHashSet(lvt_11_1_);
/* 324 */       Set<TileEntity> set1 = Sets.newHashSet(this.setTileEntities);
/* 325 */       set.removeAll(this.setTileEntities);
/* 326 */       set1.removeAll(lvt_11_1_);
/* 327 */       this.setTileEntities.clear();
/* 328 */       this.setTileEntities.addAll(lvt_11_1_);
/* 329 */       this.renderGlobal.updateTileEntities(set1, set);
/*     */     }
/*     */     finally {
/*     */       
/* 333 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finishCompileTask() {
/* 339 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 343 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE)
/*     */       {
/* 345 */         this.compileTask.finish();
/* 346 */         this.compileTask = null;
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 351 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantLock getLockCompileTask() {
/* 357 */     return this.lockCompileTask;
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator makeCompileTaskChunk() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator;
/* 362 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 367 */       finishCompileTask();
/* 368 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
/* 369 */       chunkcompiletaskgenerator = this.compileTask;
/*     */     }
/*     */     finally {
/*     */       
/* 373 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */     
/* 376 */     return chunkcompiletaskgenerator;
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator1;
/* 381 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 386 */       if (this.compileTask != null && this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING)
/*     */       {
/* 388 */         return null;
/*     */       }
/*     */       
/* 391 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
/*     */         
/* 393 */         this.compileTask.finish();
/* 394 */         this.compileTask = null;
/*     */       } 
/*     */       
/* 397 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY);
/* 398 */       this.compileTask.setCompiledChunk(this.compiledChunk);
/* 399 */       chunkcompiletaskgenerator1 = this.compileTask;
/*     */     }
/*     */     finally {
/*     */       
/* 403 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */     
/* 406 */     return chunkcompiletaskgenerator1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void preRenderBlocks(WorldRenderer worldRendererIn, BlockPos pos) {
/* 411 */     worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/*     */     
/* 413 */     if (Config.isRenderRegions()) {
/*     */       
/* 415 */       int i = 8;
/* 416 */       int j = pos.getX() >> i << i;
/* 417 */       int k = pos.getY() >> i << i;
/* 418 */       int l = pos.getZ() >> i << i;
/* 419 */       j = this.regionX;
/* 420 */       l = this.regionZ;
/* 421 */       worldRendererIn.setTranslation(-j, -k, -l);
/*     */     }
/*     */     else {
/*     */       
/* 425 */       worldRendererIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderBlocks(EnumWorldBlockLayer layer, float x, float y, float z, WorldRenderer worldRendererIn, CompiledChunk compiledChunkIn) {
/* 431 */     if (layer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunkIn.isLayerEmpty(layer)) {
/*     */       
/* 433 */       worldRendererIn.sortVertexData(x, y, z);
/* 434 */       compiledChunkIn.setState(worldRendererIn.getVertexState());
/*     */     } 
/*     */     
/* 437 */     worldRendererIn.finishDrawing();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initModelviewMatrix() {
/* 442 */     GlStateManager.pushMatrix();
/* 443 */     GlStateManager.loadIdentity();
/* 444 */     float f = 1.000001F;
/* 445 */     GlStateManager.translate(-8.0F, -8.0F, -8.0F);
/* 446 */     GlStateManager.scale(f, f, f);
/* 447 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/* 448 */     GlStateManager.getFloat(2982, this.modelviewMatrix);
/* 449 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void multModelviewMatrix() {
/* 454 */     GlStateManager.multMatrix(this.modelviewMatrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/* 459 */     return this.compiledChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/* 464 */     this.lockCompiledChunk.lock();
/*     */ 
/*     */     
/*     */     try {
/* 468 */       this.compiledChunk = compiledChunkIn;
/*     */     }
/*     */     finally {
/*     */       
/* 472 */       this.lockCompiledChunk.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopCompileTask() {
/* 478 */     finishCompileTask();
/* 479 */     this.compiledChunk = CompiledChunk.DUMMY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlResources() {
/* 484 */     stopCompileTask();
/*     */     
/* 486 */     for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++) {
/*     */       
/* 488 */       if (this.vertexBuffers[i] != null)
/*     */       {
/* 490 */         this.vertexBuffers[i].deleteGlBuffers();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 497 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedsUpdate(boolean needsUpdateIn) {
/* 502 */     this.needsUpdate = needsUpdateIn;
/*     */     
/* 504 */     if (needsUpdateIn) {
/*     */       
/* 506 */       if (isWorldPlayerUpdate())
/*     */       {
/* 508 */         this.playerUpdate = true;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 513 */       this.playerUpdate = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNeedsUpdate() {
/* 519 */     return this.needsUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getBlockPosOffset16(EnumFacing p_181701_1_) {
/* 524 */     return getPositionOffset16(p_181701_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPositionOffset16(EnumFacing p_getPositionOffset16_1_) {
/* 529 */     int i = p_getPositionOffset16_1_.getIndex();
/* 530 */     BlockPos blockpos = this.positionOffsets16[i];
/*     */     
/* 532 */     if (blockpos == null) {
/*     */       
/* 534 */       blockpos = this.position.offset(p_getPositionOffset16_1_, 16);
/* 535 */       this.positionOffsets16[i] = blockpos;
/*     */     } 
/*     */     
/* 538 */     return blockpos;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWorldPlayerUpdate() {
/* 543 */     if (this.world instanceof WorldClient) {
/*     */       
/* 545 */       WorldClient worldclient = (WorldClient)this.world;
/* 546 */       return worldclient.isPlayerUpdate();
/*     */     } 
/*     */ 
/*     */     
/* 550 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 556 */     return this.playerUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   protected RegionRenderCache createRegionRenderCache(World p_createRegionRenderCache_1_, BlockPos p_createRegionRenderCache_2_, BlockPos p_createRegionRenderCache_3_, int p_createRegionRenderCache_4_) {
/* 561 */     return new RegionRenderCache(p_createRegionRenderCache_1_, p_createRegionRenderCache_2_, p_createRegionRenderCache_3_, p_createRegionRenderCache_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumWorldBlockLayer fixBlockLayer(IBlockState p_fixBlockLayer_1_, EnumWorldBlockLayer p_fixBlockLayer_2_) {
/* 566 */     if (CustomBlockLayers.isActive()) {
/*     */       
/* 568 */       EnumWorldBlockLayer enumworldblocklayer = CustomBlockLayers.getRenderLayer(p_fixBlockLayer_1_);
/*     */       
/* 570 */       if (enumworldblocklayer != null)
/*     */       {
/* 572 */         return enumworldblocklayer;
/*     */       }
/*     */     } 
/*     */     
/* 576 */     if (!this.fixBlockLayer)
/*     */     {
/* 578 */       return p_fixBlockLayer_2_;
/*     */     }
/*     */ 
/*     */     
/* 582 */     if (this.isMipmaps) {
/*     */       
/* 584 */       if (p_fixBlockLayer_2_ == EnumWorldBlockLayer.CUTOUT)
/*     */       {
/* 586 */         Block block = p_fixBlockLayer_1_.getBlock();
/*     */         
/* 588 */         if (block instanceof net.minecraft.block.BlockRedstoneWire)
/*     */         {
/* 590 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 593 */         if (block instanceof net.minecraft.block.BlockCactus)
/*     */         {
/* 595 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 598 */         return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */       }
/*     */     
/* 601 */     } else if (p_fixBlockLayer_2_ == EnumWorldBlockLayer.CUTOUT_MIPPED) {
/*     */       
/* 603 */       return EnumWorldBlockLayer.CUTOUT;
/*     */     } 
/*     */     
/* 606 */     return p_fixBlockLayer_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void postRenderOverlays(RegionRenderCacheBuilder p_postRenderOverlays_1_, CompiledChunk p_postRenderOverlays_2_, boolean[] p_postRenderOverlays_3_) {
/* 612 */     postRenderOverlay(EnumWorldBlockLayer.CUTOUT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 613 */     postRenderOverlay(EnumWorldBlockLayer.CUTOUT_MIPPED, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 614 */     postRenderOverlay(EnumWorldBlockLayer.TRANSLUCENT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderOverlay(EnumWorldBlockLayer p_postRenderOverlay_1_, RegionRenderCacheBuilder p_postRenderOverlay_2_, CompiledChunk p_postRenderOverlay_3_, boolean[] p_postRenderOverlay_4_) {
/* 619 */     WorldRenderer worldrenderer = p_postRenderOverlay_2_.getWorldRendererByLayer(p_postRenderOverlay_1_);
/*     */     
/* 621 */     if (worldrenderer.isDrawing()) {
/*     */       
/* 623 */       p_postRenderOverlay_3_.setLayerStarted(p_postRenderOverlay_1_);
/* 624 */       p_postRenderOverlay_4_[p_postRenderOverlay_1_.ordinal()] = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ChunkCacheOF makeChunkCacheOF(BlockPos p_makeChunkCacheOF_1_) {
/* 630 */     BlockPos blockpos = p_makeChunkCacheOF_1_.add(-1, -1, -1);
/* 631 */     BlockPos blockpos1 = p_makeChunkCacheOF_1_.add(16, 16, 16);
/* 632 */     RegionRenderCache regionRenderCache = createRegionRenderCache(this.world, blockpos, blockpos1, 1);
/*     */     
/* 634 */     if (Reflector.MinecraftForgeClient_onRebuildChunk.exists())
/*     */     {
/* 636 */       Reflector.call(Reflector.MinecraftForgeClient_onRebuildChunk, new Object[] { this.world, p_makeChunkCacheOF_1_, regionRenderCache });
/*     */     }
/*     */     
/* 639 */     return new ChunkCacheOF((ChunkCache)regionRenderCache, blockpos, blockpos1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunkOffset16(ViewFrustum p_getRenderChunkOffset16_1_, EnumFacing p_getRenderChunkOffset16_2_) {
/* 644 */     if (!this.renderChunksOffset16Updated) {
/*     */       
/* 646 */       for (int i = 0; i < EnumFacing.VALUES.length; i++) {
/*     */         
/* 648 */         EnumFacing enumfacing = EnumFacing.VALUES[i];
/* 649 */         BlockPos blockpos = getBlockPosOffset16(enumfacing);
/* 650 */         this.renderChunksOfset16[i] = p_getRenderChunkOffset16_1_.getRenderChunk(blockpos);
/*     */       } 
/*     */       
/* 653 */       this.renderChunksOffset16Updated = true;
/*     */     } 
/*     */     
/* 656 */     return this.renderChunksOfset16[p_getRenderChunkOffset16_2_.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getChunk() {
/* 661 */     return getChunk(this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   private Chunk getChunk(BlockPos p_getChunk_1_) {
/* 666 */     Chunk chunk = this.chunk;
/*     */     
/* 668 */     if (chunk != null && chunk.isLoaded())
/*     */     {
/* 670 */       return chunk;
/*     */     }
/*     */ 
/*     */     
/* 674 */     chunk = this.world.getChunkFromBlockCoords(p_getChunk_1_);
/* 675 */     this.chunk = chunk;
/* 676 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChunkRegionEmpty() {
/* 682 */     return isChunkRegionEmpty(this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isChunkRegionEmpty(BlockPos p_isChunkRegionEmpty_1_) {
/* 687 */     int i = p_isChunkRegionEmpty_1_.getY();
/* 688 */     int j = i + 15;
/* 689 */     return getChunk(p_isChunkRegionEmpty_1_).getAreLevelsEmpty(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderChunkNeighbour(EnumFacing p_setRenderChunkNeighbour_1_, RenderChunk p_setRenderChunkNeighbour_2_) {
/* 694 */     this.renderChunkNeighbours[p_setRenderChunkNeighbour_1_.ordinal()] = p_setRenderChunkNeighbour_2_;
/* 695 */     this.renderChunkNeighboursValid[p_setRenderChunkNeighbour_1_.ordinal()] = p_setRenderChunkNeighbour_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunkNeighbour(EnumFacing p_getRenderChunkNeighbour_1_) {
/* 700 */     if (!this.renderChunkNeighboursUpated)
/*     */     {
/* 702 */       updateRenderChunkNeighboursValid();
/*     */     }
/*     */     
/* 705 */     return this.renderChunkNeighboursValid[p_getRenderChunkNeighbour_1_.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderGlobal.ContainerLocalRenderInformation getRenderInfo() {
/* 710 */     return this.renderInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateRenderChunkNeighboursValid() {
/* 715 */     int i = this.position.getX();
/* 716 */     int j = this.position.getZ();
/* 717 */     int k = EnumFacing.NORTH.ordinal();
/* 718 */     int l = EnumFacing.SOUTH.ordinal();
/* 719 */     int i1 = EnumFacing.WEST.ordinal();
/* 720 */     int j1 = EnumFacing.EAST.ordinal();
/* 721 */     this.renderChunkNeighboursValid[k] = ((this.renderChunkNeighbours[k]).position.getZ() == j - 16) ? this.renderChunkNeighbours[k] : null;
/* 722 */     this.renderChunkNeighboursValid[l] = ((this.renderChunkNeighbours[l]).position.getZ() == j + 16) ? this.renderChunkNeighbours[l] : null;
/* 723 */     this.renderChunkNeighboursValid[i1] = ((this.renderChunkNeighbours[i1]).position.getX() == i - 16) ? this.renderChunkNeighbours[i1] : null;
/* 724 */     this.renderChunkNeighboursValid[j1] = ((this.renderChunkNeighbours[j1]).position.getX() == i + 16) ? this.renderChunkNeighbours[j1] : null;
/* 725 */     this.renderChunkNeighboursUpated = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBoundingBoxInFrustum(ICamera p_isBoundingBoxInFrustum_1_, int p_isBoundingBoxInFrustum_2_) {
/* 730 */     return getBoundingBoxParent().isBoundingBoxInFrustumFully(p_isBoundingBoxInFrustum_1_, p_isBoundingBoxInFrustum_2_) ? true : p_isBoundingBoxInFrustum_1_.isBoundingBoxInFrustum(this.boundingBox);
/*     */   }
/*     */ 
/*     */   
/*     */   public AabbFrame getBoundingBoxParent() {
/* 735 */     if (this.boundingBoxParent == null) {
/*     */       
/* 737 */       BlockPos blockpos = this.position;
/* 738 */       int i = blockpos.getX();
/* 739 */       int j = blockpos.getY();
/* 740 */       int k = blockpos.getZ();
/* 741 */       int l = 5;
/* 742 */       int i1 = i >> l << l;
/* 743 */       int j1 = j >> l << l;
/* 744 */       int k1 = k >> l << l;
/*     */       
/* 746 */       if (i1 != i || j1 != j || k1 != k) {
/*     */         
/* 748 */         AabbFrame aabbframe = this.renderGlobal.getRenderChunk(new BlockPos(i1, j1, k1)).getBoundingBoxParent();
/*     */         
/* 750 */         if (aabbframe != null && aabbframe.minX == i1 && aabbframe.minY == j1 && aabbframe.minZ == k1)
/*     */         {
/* 752 */           this.boundingBoxParent = aabbframe;
/*     */         }
/*     */       } 
/*     */       
/* 756 */       if (this.boundingBoxParent == null) {
/*     */         
/* 758 */         int l1 = 1 << l;
/* 759 */         this.boundingBoxParent = new AabbFrame(i1, j1, k1, (i1 + l1), (j1 + l1), (k1 + l1));
/*     */       } 
/*     */     } 
/*     */     
/* 763 */     return this.boundingBoxParent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 768 */     return "pos: " + this.position + ", frameIndex: " + this.frameIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\chunk\RenderChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */