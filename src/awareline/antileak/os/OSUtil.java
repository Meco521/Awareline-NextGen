/*    */ package awareline.antileak.os;
/*    */ 
/*    */ public final class OSUtil {
/*    */   public static OS getPlatform() {
/*  5 */     String s = System.getProperty("os.name").toLowerCase();
/*    */     
/*  7 */     return s.contains("win") ? OS.WINDOWS : (
/*  8 */       s.contains("mac") ? OS.MACOS : (
/*  9 */       s.contains("solaris") ? OS.SOLARIS : (
/* 10 */       s.contains("sunos") ? OS.SOLARIS : (s.contains("linux") ? OS.LINUX : (
/* 11 */       s.contains("unix") ? OS.LINUX : OS.UNKNOWN)))));
/*    */   }
/*    */   
/*    */   public enum OS
/*    */   {
/* 16 */     LINUX,
/* 17 */     SOLARIS,
/* 18 */     WINDOWS,
/* 19 */     MACOS,
/* 20 */     UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\os\OSUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */