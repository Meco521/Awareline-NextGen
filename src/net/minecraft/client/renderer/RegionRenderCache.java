/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.DynamicLights;
/*     */ 
/*     */ public class RegionRenderCache
/*     */   extends ChunkCache
/*     */ {
/*  19 */   private static final IBlockState DEFAULT_STATE = Blocks.air.getDefaultState();
/*     */   private final BlockPos position;
/*     */   private final int[] combinedLights;
/*     */   private final IBlockState[] blockStates;
/*  23 */   private static final ArrayDeque<int[]> cacheLights = (ArrayDeque)new ArrayDeque<>();
/*  24 */   private static final ArrayDeque<IBlockState[]> cacheStates = (ArrayDeque)new ArrayDeque<>();
/*  25 */   private static final int maxCacheSize = Config.limit(Runtime.getRuntime().availableProcessors(), 1, 32);
/*     */ 
/*     */   
/*     */   public RegionRenderCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  29 */     super(worldIn, posFromIn, posToIn, subIn);
/*  30 */     this.position = posFromIn.subtract(new Vec3i(subIn, subIn, subIn));
/*  31 */     int i = 8000;
/*  32 */     this.combinedLights = allocateLights(8000);
/*  33 */     Arrays.fill(this.combinedLights, -1);
/*  34 */     this.blockStates = allocateStates(8000);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/*  39 */     int i = (pos.getX() >> 4) - this.chunkX;
/*  40 */     int j = (pos.getZ() >> 4) - this.chunkZ;
/*  41 */     return this.chunkArray[i][j].getTileEntity(pos, Chunk.EnumCreateEntityType.QUEUED);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  46 */     int i = getPositionIndex(pos);
/*  47 */     int j = this.combinedLights[i];
/*     */     
/*  49 */     if (j == -1) {
/*     */       
/*  51 */       j = super.getCombinedLight(pos, lightValue);
/*     */       
/*  53 */       if (Config.isDynamicLights() && !getBlockState(pos).getBlock().isOpaqueCube())
/*     */       {
/*  55 */         j = DynamicLights.getCombinedLight(pos, j);
/*     */       }
/*     */       
/*  58 */       this.combinedLights[i] = j;
/*     */     } 
/*     */     
/*  61 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/*  66 */     int i = getPositionIndex(pos);
/*  67 */     IBlockState iblockstate = this.blockStates[i];
/*     */     
/*  69 */     if (iblockstate == null) {
/*     */       
/*  71 */       iblockstate = getBlockStateRaw(pos);
/*  72 */       this.blockStates[i] = iblockstate;
/*     */     } 
/*     */     
/*  75 */     return iblockstate;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState getBlockStateRaw(BlockPos pos) {
/*  80 */     return super.getBlockState(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getPositionIndex(BlockPos p_175630_1_) {
/*  85 */     int i = p_175630_1_.getX() - this.position.getX();
/*  86 */     int j = p_175630_1_.getY() - this.position.getY();
/*  87 */     int k = p_175630_1_.getZ() - this.position.getZ();
/*  88 */     return i * 400 + k * 20 + j;
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeBuffers() {
/*  93 */     freeLights(this.combinedLights);
/*  94 */     freeStates(this.blockStates);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] allocateLights(int p_allocateLights_0_) {
/*  99 */     synchronized (cacheLights) {
/*     */       
/* 101 */       int[] aint = cacheLights.pollLast();
/*     */       
/* 103 */       if (aint == null || aint.length < p_allocateLights_0_)
/*     */       {
/* 105 */         aint = new int[p_allocateLights_0_];
/*     */       }
/*     */       
/* 108 */       return aint;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void freeLights(int[] p_freeLights_0_) {
/* 114 */     synchronized (cacheLights) {
/*     */       
/* 116 */       if (cacheLights.size() < maxCacheSize)
/*     */       {
/* 118 */         cacheLights.add(p_freeLights_0_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IBlockState[] allocateStates(int p_allocateStates_0_) {
/* 125 */     synchronized (cacheStates) {
/*     */       
/* 127 */       IBlockState[] aiblockstate = cacheStates.pollLast();
/*     */       
/* 129 */       if (aiblockstate != null && aiblockstate.length >= p_allocateStates_0_) {
/*     */         
/* 131 */         Arrays.fill((Object[])aiblockstate, (Object)null);
/*     */       }
/*     */       else {
/*     */         
/* 135 */         aiblockstate = new IBlockState[p_allocateStates_0_];
/*     */       } 
/*     */       
/* 138 */       return aiblockstate;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void freeStates(IBlockState[] p_freeStates_0_) {
/* 144 */     synchronized (cacheStates) {
/*     */       
/* 146 */       if (cacheStates.size() < maxCacheSize)
/*     */       {
/* 148 */         cacheStates.add(p_freeStates_0_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\RegionRenderCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */