/*    */ package net.optifine;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class Log
/*    */ {
/*  8 */   private static final Logger LOGGER = LogManager.getLogger();
/*  9 */   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
/*    */ 
/*    */   
/*    */   public static void detail(String s) {
/* 13 */     if (logDetail);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void dbg(String s) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void warn(String s) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void warn(String s, Throwable t) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void error(String s) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void error(String s, Throwable t) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void log(String s) {
/* 46 */     dbg(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */