/*    */ package com.me.theresa.fontRenderer.font.log;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastTrig
/*    */ {
/*    */   private static double reduceSinAngle(double radians) {
/*  9 */     double orig = radians;
/* 10 */     radians %= 6.283185307179586D;
/* 11 */     if (Math.abs(radians) > Math.PI) {
/* 12 */       radians -= 6.283185307179586D;
/*    */     }
/* 14 */     if (Math.abs(radians) > 1.5707963267948966D) {
/* 15 */       radians = Math.PI - radians;
/*    */     }
/*    */     
/* 18 */     return radians;
/*    */   }
/*    */   
/*    */   public static double sin(double radians) {
/* 22 */     radians = reduceSinAngle(radians);
/* 23 */     if (Math.abs(radians) <= 0.7853981633974483D) {
/* 24 */       return Math.sin(radians);
/*    */     }
/* 26 */     return Math.cos(1.5707963267948966D - radians);
/*    */   }
/*    */ 
/*    */   
/*    */   public static double cos(double radians) {
/* 31 */     return sin(radians + 1.5707963267948966D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\log\FastTrig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */