/*    */ package com.me.theresa.fontRenderer.font.log;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Log
/*    */ {
/*    */   private static boolean verbose = true;
/*    */   private static boolean forcedVerbose = false;
/*    */   private static final String forceVerboseProperty = "org.newdawn.slick.forceVerboseLog";
/*    */   private static final String forceVerbosePropertyOnValue = "true";
/* 20 */   private static LogSystem logSystem = new DefaultLogSystem();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setLogSystem(LogSystem system) {
/* 29 */     logSystem = system;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setVerbose(boolean v) {
/* 34 */     if (forcedVerbose)
/*    */       return; 
/* 36 */     verbose = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void checkVerboseLogSetting() {
/*    */     try {
/* 42 */       AccessController.doPrivileged(new PrivilegedAction() {
/*    */             public Object run() {
/* 44 */               String val = System.getProperty("org.newdawn.slick.forceVerboseLog");
/* 45 */               if (val != null && val.equalsIgnoreCase("true")) {
/* 46 */                 Log.setForcedVerboseOn();
/*    */               }
/*    */               
/* 49 */               return null;
/*    */             }
/*    */           });
/* 52 */     } catch (Throwable throwable) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setForcedVerboseOn() {
/* 59 */     forcedVerbose = true;
/* 60 */     verbose = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void error(String message, Throwable e) {
/* 65 */     logSystem.error(message, e);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void error(Throwable e) {
/* 70 */     logSystem.error(e);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void error(String message) {
/* 75 */     logSystem.error(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void warn(String message) {
/* 80 */     logSystem.warn(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void warn(String message, Throwable e) {
/* 85 */     logSystem.warn(message, e);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void info(String message) {
/* 90 */     if (verbose || forcedVerbose) {
/* 91 */       logSystem.info(message);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void debug(String message) {
/* 97 */     if (verbose || forcedVerbose)
/* 98 */       logSystem.debug(message); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\log\Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */