/*    */ package net.optifine.util;
/*    */ 
/*    */ public class CacheLocal
/*    */ {
/*  5 */   private int maxX = 18;
/*  6 */   private int maxY = 128;
/*  7 */   private int maxZ = 18;
/*  8 */   private int offsetX = 0;
/*  9 */   private int offsetY = 0;
/* 10 */   private int offsetZ = 0;
/* 11 */   private int[][][] cache = (int[][][])null;
/* 12 */   private int[] lastZs = null;
/* 13 */   private int lastDz = 0;
/*    */ 
/*    */   
/*    */   public CacheLocal(int maxX, int maxY, int maxZ) {
/* 17 */     this.maxX = maxX;
/* 18 */     this.maxY = maxY;
/* 19 */     this.maxZ = maxZ;
/* 20 */     this.cache = new int[maxX][maxY][maxZ];
/* 21 */     resetCache();
/*    */   }
/*    */ 
/*    */   
/*    */   public void resetCache() {
/* 26 */     for (int i = 0; i < this.maxX; i++) {
/*    */       
/* 28 */       int[][] aint = this.cache[i];
/*    */       
/* 30 */       for (int j = 0; j < this.maxY; j++) {
/*    */         
/* 32 */         int[] aint1 = aint[j];
/*    */         
/* 34 */         for (int k = 0; k < this.maxZ; k++)
/*    */         {
/* 36 */           aint1[k] = -1;
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOffset(int x, int y, int z) {
/* 44 */     this.offsetX = x;
/* 45 */     this.offsetY = y;
/* 46 */     this.offsetZ = z;
/* 47 */     resetCache();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int get(int x, int y, int z) {
/*    */     try {
/* 54 */       this.lastZs = this.cache[x - this.offsetX][y - this.offsetY];
/* 55 */       this.lastDz = z - this.offsetZ;
/* 56 */       return this.lastZs[this.lastDz];
/*    */     }
/* 58 */     catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
/*    */       
/* 60 */       arrayindexoutofboundsexception.printStackTrace();
/* 61 */       return -1;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLast(int val) {
/*    */     try {
/* 69 */       this.lastZs[this.lastDz] = val;
/*    */     }
/* 71 */     catch (Exception exception) {
/*    */       
/* 73 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\CacheLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */