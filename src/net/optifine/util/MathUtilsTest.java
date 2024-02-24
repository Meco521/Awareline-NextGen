/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class MathUtilsTest
/*    */ {
/*    */   public static void main(String[] args) {
/*  8 */     OPER[] amathutilstest$oper = OPER.values();
/*    */     
/* 10 */     for (int i = 0; i < amathutilstest$oper.length; i++) {
/*    */       
/* 12 */       OPER mathutilstest$oper = amathutilstest$oper[i];
/* 13 */       dbg("******** " + mathutilstest$oper + " ***********");
/* 14 */       test(mathutilstest$oper, false);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void test(OPER oper, boolean fast) {
/*    */     double d0, d1;
/* 20 */     MathHelper.fastMath = fast;
/*    */ 
/*    */ 
/*    */     
/* 24 */     switch (oper) {
/*    */       
/*    */       case SIN:
/*    */       case COS:
/* 28 */         d0 = -MathHelper.PI;
/* 29 */         d1 = MathHelper.PI;
/*    */         break;
/*    */       
/*    */       case ASIN:
/*    */       case ACOS:
/* 34 */         d0 = -1.0D;
/* 35 */         d1 = 1.0D;
/*    */         break;
/*    */       
/*    */       default:
/*    */         return;
/*    */     } 
/*    */     
/* 42 */     int i = 10;
/*    */     
/* 44 */     for (int j = 0; j <= i; j++) {
/*    */       float f, f1;
/* 46 */       double d2 = d0 + j * (d1 - d0) / i;
/*    */ 
/*    */ 
/*    */       
/* 50 */       switch (oper) {
/*    */         
/*    */         case SIN:
/* 53 */           f = (float)Math.sin(d2);
/* 54 */           f1 = MathHelper.sin((float)d2);
/*    */           break;
/*    */         
/*    */         case COS:
/* 58 */           f = (float)Math.cos(d2);
/* 59 */           f1 = MathHelper.cos((float)d2);
/*    */           break;
/*    */         
/*    */         case ASIN:
/* 63 */           f = (float)Math.asin(d2);
/* 64 */           f1 = MathUtils.asin((float)d2);
/*    */           break;
/*    */         
/*    */         case ACOS:
/* 68 */           f = (float)Math.acos(d2);
/* 69 */           f1 = MathUtils.acos((float)d2);
/*    */           break;
/*    */         
/*    */         default:
/*    */           return;
/*    */       } 
/*    */       
/* 76 */       dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", new Object[] { Double.valueOf(d2), Float.valueOf(f), Float.valueOf(f1), Float.valueOf(Math.abs(f - f1)) }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void dbg(String str) {
/* 82 */     System.out.println(str);
/*    */   }
/*    */   
/*    */   private enum OPER
/*    */   {
/* 87 */     SIN,
/* 88 */     COS,
/* 89 */     ASIN,
/* 90 */     ACOS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\MathUtilsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */