/*    */ package awareline.main.utility.timer;
/*    */ 
/*    */ public class TimerUtils {
/*    */   private long prevMS;
/*    */   private final long lastMS;
/*  6 */   private final long ms = getCurrentMS();
/*    */   
/*    */   public TimerUtils() {
/*  9 */     this.prevMS = 0L;
/* 10 */     this.lastMS = -1L;
/*    */   }
/*    */   
/*    */   public boolean delay(float milliSec) {
/* 14 */     return ((float)(getTime() - this.prevMS) >= milliSec);
/*    */   }
/*    */   
/*    */   public boolean delay(double nextDelay) {
/* 18 */     return ((System.currentTimeMillis() - this.lastMS) >= nextDelay);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 22 */     this.prevMS = getTime();
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 26 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public long getCurrentMS() {
/* 30 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public boolean hasReached(long milliseconds) {
/* 34 */     return (getCurrentMS() - this.lastMS >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean hasReached(double milliseconds) {
/* 38 */     return ((getCurrentMS() - this.lastMS) >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean hasTimeElapsed(long time, boolean reset) {
/* 42 */     if (getTime() >= time) {
/* 43 */       if (reset) {
/* 44 */         reset();
/*    */       }
/* 46 */       return true;
/*    */     } 
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public final boolean elapsed(long milliseconds) {
/* 52 */     return (getCurrentMS() - this.ms > milliseconds);
/*    */   }
/*    */   
/*    */   public final boolean hasPassed(long milliseconds) {
/* 56 */     return (getCurrentMS() - this.lastMS > milliseconds);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\timer\TimerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */