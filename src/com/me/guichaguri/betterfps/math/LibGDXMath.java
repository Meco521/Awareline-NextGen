/*    */ package com.me.guichaguri.betterfps.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LibGDXMath
/*    */ {
/*    */   public static final float BF_PI = 3.1415927F;
/*    */   private static final int BF_SIN_BITS = 14;
/*    */   private static final int BF_SIN_MASK = 16383;
/*    */   private static final int BF_SIN_COUNT = 16384;
/*    */   private static final float BF_radFull = 6.2831855F;
/*    */   private static final float BF_degFull = 360.0F;
/*    */   private static final float BF_radToIndex = 2607.5945F;
/*    */   private static final float BF_degToIndex = 45.511112F;
/*    */   public static final float BF_degreesToRadians = 0.017453292F;
/* 20 */   private static final float[] BF_table = new float[16384];
/*    */   static {
/*    */     int i;
/* 23 */     for (i = 0; i < 16384; i++) {
/* 24 */       BF_table[i] = (float)Math.sin(((i + 0.5F) / 16384.0F * 6.2831855F));
/*    */     }
/* 26 */     for (i = 0; i < 360; i += 90) {
/* 27 */       BF_table[(int)(i * 45.511112F) & 0x3FFF] = (float)Math.sin((i * 0.017453292F));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static float sin(float radians) {
/* 33 */     return BF_table[(int)(radians * 2607.5945F) & 0x3FFF];
/*    */   }
/*    */   
/*    */   public static float cos(float radians) {
/* 37 */     return BF_table[(int)((radians + 1.5707964F) * 2607.5945F) & 0x3FFF];
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\math\LibGDXMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */