/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkCoordIntPair
/*     */ {
/*     */   public final int chunkXPos;
/*     */   public final int chunkZPos;
/*  12 */   private int cachedHashCode = 0;
/*     */ 
/*     */   
/*     */   public ChunkCoordIntPair(int x, int z) {
/*  16 */     this.chunkXPos = x;
/*  17 */     this.chunkZPos = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long chunkXZ2Int(int x, int z) {
/*  25 */     return x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32L;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  30 */     if (this.cachedHashCode == 0) {
/*     */       
/*  32 */       int i = 1664525 * this.chunkXPos + 1013904223;
/*  33 */       int j = 1664525 * (this.chunkZPos ^ 0xDEADBEEF) + 1013904223;
/*  34 */       this.cachedHashCode = i ^ j;
/*     */     } 
/*     */     
/*  37 */     return this.cachedHashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  42 */     if (this == p_equals_1_)
/*     */     {
/*  44 */       return true;
/*     */     }
/*  46 */     if (!(p_equals_1_ instanceof ChunkCoordIntPair))
/*     */     {
/*  48 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  52 */     ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)p_equals_1_;
/*  53 */     return (this.chunkXPos == chunkcoordintpair.chunkXPos && this.chunkZPos == chunkcoordintpair.chunkZPos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCenterXPos() {
/*  59 */     return (this.chunkXPos << 4) + 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCenterZPosition() {
/*  64 */     return (this.chunkZPos << 4) + 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXStart() {
/*  72 */     return this.chunkXPos << 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZStart() {
/*  80 */     return this.chunkZPos << 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXEnd() {
/*  88 */     return (this.chunkXPos << 4) + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZEnd() {
/*  96 */     return (this.chunkZPos << 4) + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getBlock(int x, int y, int z) {
/* 104 */     return new BlockPos((this.chunkXPos << 4) + x, y, (this.chunkZPos << 4) + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getCenterBlock(int y) {
/* 112 */     return new BlockPos(getCenterXPos(), y, getCenterZPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\ChunkCoordIntPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */