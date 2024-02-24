/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class IntegerCache
/*    */ {
/*  5 */   private static final Integer[] CACHE = new Integer[65535];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Integer getInteger(int value) {
/* 12 */     return (value >= 0 && value < CACHE.length) ? CACHE[value] : new Integer(value);
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 17 */     int i = 0;
/*    */     
/* 19 */     for (int j = CACHE.length; i < j; i++)
/*    */     {
/* 21 */       CACHE[i] = Integer.valueOf(i);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\IntegerCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */