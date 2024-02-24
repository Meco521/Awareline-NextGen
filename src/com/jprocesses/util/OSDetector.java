/*    */ package com.jprocesses.util;
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
/*    */ public class OSDetector
/*    */ {
/* 25 */   private static final String OS = System.getProperty("os.name").toLowerCase();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isWindows() {
/* 32 */     return OS.contains("win");
/*    */   }
/*    */   
/*    */   public static boolean isMac() {
/* 36 */     return OS.contains("mac");
/*    */   }
/*    */   
/*    */   public static boolean isUnix() {
/* 40 */     return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix") || OS.matches("mac.*os.*x"));
/*    */   }
/*    */   
/*    */   public static boolean isLinux() {
/* 44 */     return OS.contains("linux");
/*    */   }
/*    */   
/*    */   public static boolean isSolaris() {
/* 48 */     return OS.contains("sunos");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesse\\util\OSDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */