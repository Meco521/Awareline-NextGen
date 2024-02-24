/*    */ package com.profesorfalken.jpowershell;
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
/*    */ class OSDetector
/*    */ {
/* 23 */   private static final String OS = System.getProperty("os.name").toLowerCase();
/*    */   
/*    */   public static boolean isWindows() {
/* 26 */     return OS.contains("win");
/*    */   }
/*    */   
/*    */   public static boolean isMac() {
/* 30 */     return OS.contains("mac");
/*    */   }
/*    */   
/*    */   public static boolean isUnix() {
/* 34 */     return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
/*    */   }
/*    */   
/*    */   public static boolean isSolaris() {
/* 38 */     return OS.contains("sunos");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\jpowershell\OSDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */