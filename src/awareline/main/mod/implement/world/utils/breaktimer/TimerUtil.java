/*    */ package awareline.main.mod.implement.world.utils.breaktimer;
/*    */ 
/*    */ public class TimerUtil {
/*    */   private long lastMS;
/*    */   
/*    */   private long getCurrentMS() {
/*  7 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public boolean hasReached(double milliseconds) {
/* 11 */     return ((getCurrentMS() - this.lastMS) >= milliseconds);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 15 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */   
/*    */   public final long getElapsedTime() {
/* 19 */     return getCurrentMS() - this.lastMS;
/*    */   }
/*    */   
/*    */   public boolean delay(float milliSec) {
/* 23 */     return ((float)(getTime() - this.lastMS) >= milliSec);
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 27 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public boolean isDelayComplete(long delay) {
/* 31 */     return (System.currentTimeMillis() - this.lastMS > delay);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\breaktimer\TimerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */