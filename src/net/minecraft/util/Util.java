/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static EnumOS getOSType() {
/* 12 */     String s = System.getProperty("os.name").toLowerCase();
/* 13 */     return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <V> V runTask(FutureTask<V> task, Logger logger) {
/*    */     try {
/* 20 */       task.run();
/* 21 */       return task.get();
/*    */     }
/* 23 */     catch (ExecutionException executionexception) {
/*    */       
/* 25 */       logger.fatal("Error executing task", executionexception);
/*    */       
/* 27 */       if (executionexception.getCause() instanceof OutOfMemoryError)
/*    */       {
/* 29 */         OutOfMemoryError outofmemoryerror = (OutOfMemoryError)executionexception.getCause();
/* 30 */         throw outofmemoryerror;
/*    */       }
/*    */     
/* 33 */     } catch (InterruptedException interruptedexception) {
/*    */       
/* 35 */       logger.fatal("Error executing task", interruptedexception);
/*    */     } 
/*    */     
/* 38 */     return null;
/*    */   }
/*    */   
/*    */   public enum EnumOS
/*    */   {
/* 43 */     LINUX,
/* 44 */     SOLARIS,
/* 45 */     WINDOWS,
/* 46 */     OSX,
/* 47 */     UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */