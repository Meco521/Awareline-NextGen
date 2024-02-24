/*    */ package net.optifine.util;
/*    */ 
/*    */ public class MemoryMonitor
/*    */ {
/*  5 */   private static long startTimeMs = System.currentTimeMillis();
/*  6 */   private static long startMemory = getMemoryUsed();
/*  7 */   private static long lastTimeMs = startTimeMs;
/*  8 */   private static long lastMemory = startMemory;
/*    */   private static boolean gcEvent = false;
/* 10 */   private static int memBytesSec = 0;
/*    */   
/*    */   private static final long MB = 1048576L;
/*    */   
/*    */   public static void update() {
/* 15 */     long i = System.currentTimeMillis();
/* 16 */     long j = getMemoryUsed();
/* 17 */     gcEvent = (j < lastMemory);
/*    */     
/* 19 */     if (gcEvent) {
/*    */       
/* 21 */       long k = lastTimeMs - startTimeMs;
/* 22 */       long l = lastMemory - startMemory;
/* 23 */       double d0 = k / 1000.0D;
/* 24 */       int i1 = (int)(l / d0);
/*    */       
/* 26 */       if (i1 > 0)
/*    */       {
/* 28 */         memBytesSec = i1;
/*    */       }
/*    */       
/* 31 */       startTimeMs = i;
/* 32 */       startMemory = j;
/*    */     } 
/*    */     
/* 35 */     lastTimeMs = i;
/* 36 */     lastMemory = j;
/*    */   }
/*    */ 
/*    */   
/*    */   private static long getMemoryUsed() {
/* 41 */     Runtime runtime = Runtime.getRuntime();
/* 42 */     return runtime.totalMemory() - runtime.freeMemory();
/*    */   }
/*    */ 
/*    */   
/*    */   public static long getStartTimeMs() {
/* 47 */     return startTimeMs;
/*    */   }
/*    */ 
/*    */   
/*    */   public static long getStartMemoryMb() {
/* 52 */     return startMemory / 1048576L;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isGcEvent() {
/* 57 */     return gcEvent;
/*    */   }
/*    */ 
/*    */   
/*    */   public static long getAllocationRateMb() {
/* 62 */     return memBytesSec / 1048576L;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\MemoryMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */