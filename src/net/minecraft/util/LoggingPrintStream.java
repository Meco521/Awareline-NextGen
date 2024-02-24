/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LoggingPrintStream
/*    */   extends PrintStream
/*    */ {
/* 11 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private final String domain;
/*    */   
/*    */   public LoggingPrintStream(String domainIn, OutputStream outStream) {
/* 16 */     super(outStream);
/* 17 */     this.domain = domainIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void println(String p_println_1_) {
/* 22 */     logString(p_println_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void println(Object p_println_1_) {
/* 27 */     logString(String.valueOf(p_println_1_));
/*    */   }
/*    */ 
/*    */   
/*    */   private void logString(String string) {
/* 32 */     StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
/* 33 */     StackTraceElement stacktraceelement = astacktraceelement[Math.min(3, astacktraceelement.length)];
/* 34 */     LOGGER.info("[{}]@.({}:{}): {}", new Object[] { this.domain, stacktraceelement.getFileName(), Integer.valueOf(stacktraceelement.getLineNumber()), string });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\LoggingPrintStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */