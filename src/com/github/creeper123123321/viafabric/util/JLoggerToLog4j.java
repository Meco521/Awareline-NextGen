/*    */ package com.github.creeper123123321.viafabric.util;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.logging.Logger;
/*    */ import org.apache.logging.log4j.Logger;
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
/*    */ public class JLoggerToLog4j
/*    */   extends Logger
/*    */ {
/*    */   private final Logger base;
/*    */   
/*    */   public JLoggerToLog4j(Logger logger) {
/* 37 */     super("logger", null);
/* 38 */     this.base = logger;
/*    */   }
/*    */   
/*    */   public void log(LogRecord record) {
/* 42 */     log(record.getLevel(), record.getMessage());
/*    */   }
/*    */   
/*    */   public void log(Level level, String msg) {
/* 46 */     if (level == Level.FINE) {
/* 47 */       this.base.debug(msg);
/* 48 */     } else if (level == Level.WARNING) {
/* 49 */       this.base.warn(msg);
/* 50 */     } else if (level == Level.SEVERE) {
/* 51 */       this.base.error(msg);
/* 52 */     } else if (level == Level.INFO) {
/* 53 */       this.base.info(msg);
/*    */     } else {
/* 55 */       this.base.trace(msg);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(Level level, String msg, Object param1) {
/* 61 */     if (level == Level.FINE) {
/* 62 */       this.base.debug(msg, new Object[] { param1 });
/* 63 */     } else if (level == Level.WARNING) {
/* 64 */       this.base.warn(msg, new Object[] { param1 });
/* 65 */     } else if (level == Level.SEVERE) {
/* 66 */       this.base.error(msg, new Object[] { param1 });
/* 67 */     } else if (level == Level.INFO) {
/* 68 */       this.base.info(msg, new Object[] { param1 });
/*    */     } else {
/* 70 */       this.base.trace(msg, new Object[] { param1 });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(Level level, String msg, Object[] params) {
/* 76 */     log(level, MessageFormat.format(msg, params));
/*    */   }
/*    */   
/*    */   public void log(Level level, String msg, Throwable params) {
/* 80 */     if (level == Level.FINE) {
/* 81 */       this.base.debug(msg, params);
/* 82 */     } else if (level == Level.WARNING) {
/* 83 */       this.base.warn(msg, params);
/* 84 */     } else if (level == Level.SEVERE) {
/* 85 */       this.base.error(msg, params);
/* 86 */     } else if (level == Level.INFO) {
/* 87 */       this.base.info(msg, params);
/*    */     } else {
/* 89 */       this.base.trace(msg, params);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabri\\util\JLoggerToLog4j.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */