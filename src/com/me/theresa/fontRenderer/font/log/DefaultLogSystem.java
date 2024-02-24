/*    */ package com.me.theresa.fontRenderer.font.log;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class DefaultLogSystem
/*    */   implements LogSystem
/*    */ {
/*  9 */   public static PrintStream out = System.out;
/*    */ 
/*    */   
/*    */   public void error(String message, Throwable e) {
/* 13 */     error(message);
/* 14 */     error(e);
/*    */   }
/*    */ 
/*    */   
/*    */   public void error(Throwable e) {
/* 19 */     out.println(new Date() + " ERROR:" + e.getMessage());
/* 20 */     e.printStackTrace(out);
/*    */   }
/*    */ 
/*    */   
/*    */   public void error(String message) {
/* 25 */     out.println(new Date() + " ERROR:" + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void warn(String message) {
/* 30 */     out.println(new Date() + " WARN:" + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void info(String message) {
/* 35 */     out.println(new Date() + " INFO:" + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void debug(String message) {
/* 40 */     out.println(new Date() + " DEBUG:" + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void warn(String message, Throwable e) {
/* 45 */     warn(message);
/* 46 */     e.printStackTrace(out);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\log\DefaultLogSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */