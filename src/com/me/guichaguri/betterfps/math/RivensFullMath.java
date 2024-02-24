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
/*    */ public class RivensFullMath
/*    */ {
/* 14 */   private static final float BF_SIN_TO_COS = 1.5707964F;
/*    */   
/* 16 */   private static final int BF_SIN_BITS = 12;
/* 17 */   private static final int BF_SIN_MASK = -1 << BF_SIN_BITS ^ 0xFFFFFFFF;
/* 18 */   private static final int BF_SIN_COUNT = BF_SIN_MASK + 1;
/*    */   
/* 20 */   private static final float BF_radFull = 6.2831855F;
/* 21 */   private static final float BF_radToIndex = BF_SIN_COUNT / BF_radFull;
/*    */   
/* 23 */   private static final float[] BF_sinFull = new float[BF_SIN_COUNT]; static {
/* 24 */     for (int i = 0; i < BF_SIN_COUNT; i++) {
/* 25 */       BF_sinFull[i] = (float)Math.sin((i + Math.min(1, i % BF_SIN_COUNT / 4) * 0.5D) / BF_SIN_COUNT * BF_radFull);
/*    */     }
/*    */   }
/*    */   
/*    */   public static float sin(float rad) {
/* 30 */     return BF_sinFull[(int)(rad * BF_radToIndex) & BF_SIN_MASK];
/*    */   }
/*    */   
/*    */   public static float cos(float rad) {
/* 34 */     return sin(rad + BF_SIN_TO_COS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\math\RivensFullMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */