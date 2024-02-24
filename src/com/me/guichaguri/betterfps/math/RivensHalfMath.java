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
/*    */ public class RivensHalfMath
/*    */ {
/* 14 */   private static final float BF_SIN_TO_COS = 1.5707964F;
/*    */   
/* 16 */   private static final int BF_SIN_BITS = 12;
/* 17 */   private static final int BF_SIN_MASK = -1 << BF_SIN_BITS ^ 0xFFFFFFFF;
/* 18 */   private static final int BF_SIN_MASK2 = BF_SIN_MASK >> 1;
/* 19 */   private static final int BF_SIN_COUNT = BF_SIN_MASK + 1;
/* 20 */   private static final int BF_SIN_COUNT2 = BF_SIN_MASK2 + 1;
/*    */   
/* 22 */   private static final float BF_radFull = 6.2831855F;
/* 23 */   private static final float BF_radToIndex = BF_SIN_COUNT / BF_radFull;
/*    */   
/* 25 */   private static final float[] BF_sinHalf = new float[BF_SIN_COUNT2]; static {
/* 26 */     for (int i = 0; i < BF_SIN_COUNT2; i++) {
/* 27 */       BF_sinHalf[i] = (float)Math.sin((i + Math.min(1, i % BF_SIN_COUNT / 4) * 0.5D) / BF_SIN_COUNT * BF_radFull);
/*    */     }
/*    */   }
/*    */   
/*    */   public static float sin(float rad) {
/* 32 */     int index1 = (int)(rad * BF_radToIndex) & BF_SIN_MASK;
/* 33 */     int index2 = index1 & BF_SIN_MASK2;
/*    */     
/* 35 */     int mul = (index1 == index2) ? 1 : -1;
/* 36 */     return BF_sinHalf[index2] * mul;
/*    */   }
/*    */   
/*    */   public static float cos(float rad) {
/* 40 */     return sin(rad + BF_SIN_TO_COS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\math\RivensHalfMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */