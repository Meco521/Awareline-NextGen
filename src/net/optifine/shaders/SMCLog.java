/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public abstract class SMCLog
/*    */ {
/*  8 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String PREFIX = "[Shaders] ";
/*    */ 
/*    */ 
/*    */   
/*    */   public static void severe(String message) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static void warning(String message) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static void info(String message) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static void fine(String message) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static void severe(String format, Object... args) {
/* 33 */     String s = String.format(format, args);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void warning(String format, Object... args) {
/* 39 */     String s = String.format(format, args);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void info(String format, Object... args) {
/* 45 */     String s = String.format(format, args);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void fine(String format, Object... args) {
/* 51 */     String s = String.format(format, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\SMCLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */