/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class GlErrors
/*    */ {
/*    */   private static boolean frameStarted = false;
/*  8 */   private static long timeCheckStartMs = -1L;
/*  9 */   private static int countErrors = 0;
/* 10 */   private static int countErrorsSuppressed = 0;
/*    */   
/*    */   private static boolean suppressed = false;
/*    */   private static boolean oneErrorEnabled = false;
/*    */   private static final long CHECK_INTERVAL_MS = 3000L;
/*    */   private static final int CHECK_ERROR_MAX = 10;
/*    */   
/*    */   public static void frameStart() {
/* 18 */     frameStarted = true;
/*    */     
/* 20 */     if (timeCheckStartMs < 0L)
/*    */     {
/* 22 */       timeCheckStartMs = System.currentTimeMillis();
/*    */     }
/*    */     
/* 25 */     if (System.currentTimeMillis() > timeCheckStartMs + 3000L) {
/*    */       
/* 27 */       if (countErrorsSuppressed > 0)
/*    */       {
/* 29 */         Config.error("Suppressed " + countErrors + " OpenGL errors");
/*    */       }
/*    */       
/* 32 */       suppressed = (countErrors > 10);
/* 33 */       timeCheckStartMs = System.currentTimeMillis();
/* 34 */       countErrors = 0;
/* 35 */       countErrorsSuppressed = 0;
/* 36 */       oneErrorEnabled = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isEnabled(int error) {
/* 42 */     if (!frameStarted)
/*    */     {
/* 44 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 48 */     countErrors++;
/*    */     
/* 50 */     if (oneErrorEnabled) {
/*    */       
/* 52 */       oneErrorEnabled = false;
/* 53 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 57 */     if (suppressed)
/*    */     {
/* 59 */       countErrorsSuppressed++;
/*    */     }
/*    */     
/* 62 */     return !suppressed;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\GlErrors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */