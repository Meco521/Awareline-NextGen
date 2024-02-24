/*    */ package net.optifine.util;
/*    */ 
/*    */ 
/*    */ public class NumUtils
/*    */ {
/*    */   public static float limit(float val, float min, float max) {
/*  7 */     return (val < min) ? min : ((val > max) ? max : val);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int mod(int x, int y) {
/* 12 */     int i = x % y;
/*    */     
/* 14 */     if (i < 0)
/*    */     {
/* 16 */       i += y;
/*    */     }
/*    */     
/* 19 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\NumUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */