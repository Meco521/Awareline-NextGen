/*    */ package com.me.guichaguri.betterfps.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaylorMath
/*    */ {
/* 10 */   private static final float BF_SIN_TO_COS = 1.5707964F;
/*    */ 
/*    */   
/*    */   public static float sin(float rad) {
/* 14 */     double x = rad;
/*    */     
/* 16 */     double x2 = x * x;
/* 17 */     double x3 = x2 * x;
/* 18 */     double x5 = x2 * x3;
/* 19 */     double x7 = x2 * x5;
/* 20 */     double x9 = x2 * x7;
/* 21 */     double x11 = x2 * x9;
/* 22 */     double x13 = x2 * x11;
/* 23 */     double x15 = x2 * x13;
/* 24 */     double x17 = x2 * x15;
/*    */     
/* 26 */     double val = x;
/* 27 */     val -= x3 * 0.16666666666666666D;
/* 28 */     val += x5 * 0.008333333333333333D;
/* 29 */     val -= x7 * 1.984126984126984E-4D;
/* 30 */     val += x9 * 2.7557319223985893E-6D;
/* 31 */     val -= x11 * 2.505210838544172E-8D;
/* 32 */     val += x13 * 1.6059043836821613E-10D;
/* 33 */     val -= x15 * 7.647163731819816E-13D;
/* 34 */     val += x17 * 2.8114572543455206E-15D;
/* 35 */     return (float)val;
/*    */   }
/*    */   
/*    */   public static float cos(float rad) {
/* 39 */     return sin(rad + BF_SIN_TO_COS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\math\TaylorMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */