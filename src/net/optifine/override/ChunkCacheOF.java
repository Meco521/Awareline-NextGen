/*     */ package net.optifine.override;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.ArrayCache;
/*     */ 
/*     */ public class ChunkCacheOF
/*     */   implements IBlockAccess
/*     */ {
/*     */   private final ChunkCache chunkCache;
/*     */   private final int posX;
/*     */   private final int posY;
/*     */   private final int posZ;
/*     */   private final int sizeX;
/*     */   private final int sizeY;
/*     */   private final int sizeZ;
/*     */   private final int sizeXY;
/*     */   private int[] combinedLights;
/*     */   private IBlockState[] blockStates;
/*     */   private final int arraySize;
/*  31 */   private final boolean dynamicLights = Config.isDynamicLights();
/*  32 */   private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
/*  33 */   private static final ArrayCache cacheBlockStates = new ArrayCache(IBlockState.class, 16);
/*     */ 
/*     */   
/*     */   public ChunkCacheOF(ChunkCache chunkCache, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  37 */     this.chunkCache = chunkCache;
/*  38 */     int i = posFromIn.getX() - subIn >> 4;
/*  39 */     int j = posFromIn.getY() - subIn >> 4;
/*  40 */     int k = posFromIn.getZ() - subIn >> 4;
/*  41 */     int l = posToIn.getX() + subIn >> 4;
/*  42 */     int i1 = posToIn.getY() + subIn >> 4;
/*  43 */     int j1 = posToIn.getZ() + subIn >> 4;
/*  44 */     this.sizeX = l - i + 1 << 4;
/*  45 */     this.sizeY = i1 - j + 1 << 4;
/*  46 */     this.sizeZ = j1 - k + 1 << 4;
/*  47 */     this.sizeXY = this.sizeX * this.sizeY;
/*  48 */     this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
/*  49 */     this.posX = i << 4;
/*  50 */     this.posY = j << 4;
/*  51 */     this.posZ = k << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getPositionIndex(BlockPos pos) {
/*  56 */     int i = pos.getX() - this.posX;
/*     */     
/*  58 */     if (i >= 0 && i < this.sizeX) {
/*     */       
/*  60 */       int j = pos.getY() - this.posY;
/*     */       
/*  62 */       if (j >= 0 && j < this.sizeY) {
/*     */         
/*  64 */         int k = pos.getZ() - this.posZ;
/*  65 */         return (k >= 0 && k < this.sizeZ) ? (k * this.sizeXY + j * this.sizeX + i) : -1;
/*     */       } 
/*     */ 
/*     */       
/*  69 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  74 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  80 */     int i = getPositionIndex(pos);
/*     */     
/*  82 */     if (i >= 0 && i < this.arraySize && this.combinedLights != null) {
/*     */       
/*  84 */       int j = this.combinedLights[i];
/*     */       
/*  86 */       if (j == -1) {
/*     */         
/*  88 */         j = getCombinedLightRaw(pos, lightValue);
/*  89 */         this.combinedLights[i] = j;
/*     */       } 
/*     */       
/*  92 */       return j;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     return getCombinedLightRaw(pos, lightValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getCombinedLightRaw(BlockPos pos, int lightValue) {
/* 102 */     int i = this.chunkCache.getCombinedLight(pos, lightValue);
/*     */     
/* 104 */     if (this.dynamicLights && !getBlockState(pos).getBlock().isOpaqueCube())
/*     */     {
/* 106 */       i = DynamicLights.getCombinedLight(pos, i);
/*     */     }
/*     */     
/* 109 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/* 114 */     int i = getPositionIndex(pos);
/*     */     
/* 116 */     if (i >= 0 && i < this.arraySize && this.blockStates != null) {
/*     */       
/* 118 */       IBlockState iblockstate = this.blockStates[i];
/*     */       
/* 120 */       if (iblockstate == null) {
/*     */         
/* 122 */         iblockstate = this.chunkCache.getBlockState(pos);
/* 123 */         this.blockStates[i] = iblockstate;
/*     */       } 
/*     */       
/* 126 */       return iblockstate;
/*     */     } 
/*     */ 
/*     */     
/* 130 */     return this.chunkCache.getBlockState(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderStart() {
/* 136 */     if (this.combinedLights == null)
/*     */     {
/* 138 */       this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize);
/*     */     }
/*     */     
/* 141 */     Arrays.fill(this.combinedLights, -1);
/*     */     
/* 143 */     if (this.blockStates == null)
/*     */     {
/* 145 */       this.blockStates = (IBlockState[])cacheBlockStates.allocate(this.arraySize);
/*     */     }
/*     */     
/* 148 */     Arrays.fill((Object[])this.blockStates, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFinish() {
/* 153 */     cacheCombinedLights.free(this.combinedLights);
/* 154 */     this.combinedLights = null;
/* 155 */     cacheBlockStates.free(this.blockStates);
/* 156 */     this.blockStates = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedLevelsInChunkCache() {
/* 164 */     return this.chunkCache.extendedLevelsInChunkCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
/* 169 */     return this.chunkCache.getBiomeGenForCoords(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 174 */     return this.chunkCache.getStrongPower(pos, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/* 179 */     return this.chunkCache.getTileEntity(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 184 */     return this.chunkCache.getWorldType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAirBlock(BlockPos pos) {
/* 193 */     return this.chunkCache.isAirBlock(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
/* 198 */     return Reflector.callBoolean(this.chunkCache, Reflector.ForgeChunkCache_isSideSolid, new Object[] { pos, side, Boolean.valueOf(_default) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\override\ChunkCacheOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */