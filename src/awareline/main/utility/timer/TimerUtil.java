/*    */ package awareline.main.utility.timer;
/*    */ 
/*    */ public class TimerUtil {
/*    */   private long lastMS;
/*    */   
/*    */   public long getLastMS() {
/*  7 */     return this.lastMS;
/*  8 */   } private final long time = -1L; public long lastMs;
/*  9 */   private final long ms = getCurrentMS(); public long getMs() { return this.ms; }
/*    */   
/*    */   private long getCurrentMS() {
/* 12 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasReached(double milliseconds) {
/* 18 */     return ((getCurrentMS() - this.lastMS) >= milliseconds);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 22 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */   
/*    */   public boolean delay(float milliSec) {
/* 26 */     return ((float)(getTime() - this.lastMS) >= milliSec);
/*    */   }
/*    */   
/*    */   public boolean delay(double nextDelay) {
/* 30 */     return ((System.currentTimeMillis() - this.lastMs) >= nextDelay);
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 34 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public boolean isDelayComplete(long l) {
/* 38 */     return (System.currentTimeMillis() - this.lastMs >= l);
/*    */   }
/*    */   
/*    */   public boolean isDelayComplete(float f) {
/* 42 */     return ((float)(System.currentTimeMillis() - this.lastMs) > f);
/*    */   }
/*    */   
/*    */   public boolean hasTimePassed(long MS) {
/* 46 */     return (System.currentTimeMillis() >= -1L + MS);
/*    */   }
/*    */   
/*    */   public final boolean elapsed(long milliseconds) {
/* 50 */     return (getCurrentMS() - this.ms > milliseconds);
/*    */   }
/*    */   
/*    */   public final boolean hasPassed(long milliseconds) {
/* 54 */     return (getCurrentMS() - this.lastMS > milliseconds);
/*    */   }
/*    */   
/*    */   public final long getElapsedTime() {
/* 58 */     return getCurrentMS() - this.lastMS;
/*    */   }
/*    */   
/*    */   public long time() {
/* 62 */     return System.nanoTime() / 1000000L - -1L;
/*    */   }
/*    */   
/*    */   public boolean sleep(long time) {
/* 66 */     if (time() >= time) {
/* 67 */       reset();
/* 68 */       return true;
/*    */     } 
/* 70 */     return false;
/*    */   }
/*    */   
/*    */   public boolean hasTimeElapsed(long time, boolean reset) {
/* 74 */     if (System.currentTimeMillis() - this.lastMS > time) {
/* 75 */       if (reset) reset(); 
/* 76 */       return true;
/*    */     } 
/*    */     
/* 79 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasTimeElapsed(long time) {
/* 84 */     return (System.currentTimeMillis() - this.lastMS > time);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasTimeElapsed(double time) {
/* 89 */     return hasTimeElapsed((long)time);
/*    */   }
/*    */   
/*    */   public void setTime(long time) {
/* 93 */     this.lastMS = time;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\timer\TimerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */