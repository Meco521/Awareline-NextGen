/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TimedEvent
/*    */ {
/*  8 */   private static final Map<String, Long> mapEventTimes = new HashMap<>();
/*    */ 
/*    */   
/*    */   public static boolean isActive(String name, long timeIntervalMs) {
/* 12 */     synchronized (mapEventTimes) {
/*    */       
/* 14 */       long i = System.currentTimeMillis();
/* 15 */       Long olong = mapEventTimes.get(name);
/*    */       
/* 17 */       if (olong == null) {
/*    */         
/* 19 */         olong = new Long(i);
/* 20 */         mapEventTimes.put(name, olong);
/*    */       } 
/*    */       
/* 23 */       long j = olong.longValue();
/*    */       
/* 25 */       if (i < j + timeIntervalMs)
/*    */       {
/* 27 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 31 */       mapEventTimes.put(name, new Long(i));
/* 32 */       return true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\TimedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */