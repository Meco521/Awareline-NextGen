/*    */ package awareline.main.mod.implement.world.utils;
/*    */ 
/*    */ public class DelayTimer {
/*    */   private long prevTime;
/*    */   
/*    */   public DelayTimer() {
/*  7 */     reset();
/*    */   }
/*    */   
/*    */   public DelayTimer(long def) {
/* 11 */     this.prevTime = System.currentTimeMillis() - def;
/*    */   }
/*    */   
/*    */   public boolean hasPassed(double milli) {
/* 15 */     return ((System.currentTimeMillis() - this.prevTime) >= milli);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 19 */     this.prevTime = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public long getPassed() {
/* 23 */     return System.currentTimeMillis() - this.prevTime;
/*    */   }
/*    */   
/*    */   public void reset(long def) {
/* 27 */     this.prevTime = System.currentTimeMillis() - def;
/*    */   }
/*    */   
/*    */   public boolean isDelayComplete(double d) {
/* 31 */     return hasPassed(d);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\DelayTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */