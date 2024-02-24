/*    */ package com.me.guichaguri.betterfps.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RivensMath
/*    */ {
/* 14 */   private static final int BF_SIN_BITS = 12;
/* 15 */   private static final int BF_SIN_MASK = -1 << BF_SIN_BITS ^ 0xFFFFFFFF;
/* 16 */   private static final int BF_SIN_COUNT = BF_SIN_MASK + 1;
/*    */   
/* 18 */   private static final float BF_radFull = 6.2831855F; private static final float BF_radToIndex;
/* 19 */   private static final float BF_degFull = 360.0F; private static final float BF_degToIndex; static {
/* 20 */     BF_radToIndex = BF_SIN_COUNT / BF_radFull;
/* 21 */     BF_degToIndex = BF_SIN_COUNT / BF_degFull;
/*    */     
/* 23 */     BF_sin = new float[BF_SIN_COUNT];
/* 24 */     BF_cos = new float[BF_SIN_COUNT];
/*    */     int i;
/* 26 */     for (i = 0; i < BF_SIN_COUNT; i++) {
/* 27 */       BF_sin[i] = (float)Math.sin(((i + 0.5F) / BF_SIN_COUNT * BF_radFull));
/* 28 */       BF_cos[i] = (float)Math.cos(((i + 0.5F) / BF_SIN_COUNT * BF_radFull));
/*    */     } 
/*    */ 
/*    */     
/* 32 */     for (i = 0; i < 360; i += 90) {
/* 33 */       BF_sin[(int)(i * BF_degToIndex) & BF_SIN_MASK] = (float)Math.sin(i * Math.PI / 180.0D);
/* 34 */       BF_cos[(int)(i * BF_degToIndex) & BF_SIN_MASK] = (float)Math.cos(i * Math.PI / 180.0D);
/*    */     } 
/*    */   }
/*    */   private static final float[] BF_sin; private static final float[] BF_cos;
/*    */   public static float sin(float rad) {
/* 39 */     return BF_sin[(int)(rad * BF_radToIndex) & BF_SIN_MASK];
/*    */   }
/*    */   
/*    */   public static float cos(float rad) {
/* 43 */     return BF_cos[(int)(rad * BF_radToIndex) & BF_SIN_MASK];
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\math\RivensMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */