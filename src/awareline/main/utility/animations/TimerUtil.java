/*    */ package awareline.main.utility.animations;
/*    */ 
/*    */ 
/*    */ public class TimerUtil
/*    */ {
/*  6 */   public long lastMS = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public void reset() {
/* 10 */     this.lastMS = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasTimeElapsed(long time, boolean reset) {
/* 15 */     if (System.currentTimeMillis() - this.lastMS > time) {
/* 16 */       if (reset) reset(); 
/* 17 */       return true;
/*    */     } 
/*    */     
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasTimeElapsed(long time) {
/* 25 */     return (System.currentTimeMillis() - this.lastMS > time);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasTimeElapsed(double time) {
/* 30 */     return hasTimeElapsed((long)time);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getTime() {
/* 35 */     return System.currentTimeMillis() - this.lastMS;
/*    */   }
/*    */   
/*    */   public void setTime(long time) {
/* 39 */     this.lastMS = time;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\TimerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */