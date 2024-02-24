/*    */ package net.minecraft.VLOUBOOS.javax.vecmath;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class VecMathI18N
/*    */ {
/*    */   static String getString(String key) {
/*    */     String s;
/*    */     try {
/* 37 */       s = ResourceBundle.getBundle("javax.vecmath.ExceptionStrings").getString(key);
/*    */     }
/* 39 */     catch (MissingResourceException e) {
/* 40 */       System.err.println("VecMathI18N: Error looking up: " + key);
/* 41 */       s = key;
/*    */     } 
/* 43 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\VecMathI18N.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */