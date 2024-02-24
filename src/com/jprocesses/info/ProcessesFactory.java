/*    */ package com.jprocesses.info;
/*    */ 
/*    */ import com.jprocesses.util.OSDetector;
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
/*    */ public class ProcessesFactory
/*    */ {
/*    */   public static ProcessesService getService() {
/* 33 */     if (OSDetector.isWindows())
/* 34 */       return new WindowsProcessesService(); 
/* 35 */     if (OSDetector.isUnix()) {
/* 36 */       return new UnixProcessesService();
/*    */     }
/*    */     
/* 39 */     throw new UnsupportedOperationException("Your Operating System is not supported by this library.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesses\info\ProcessesFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */