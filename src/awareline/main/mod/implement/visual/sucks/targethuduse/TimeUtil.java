/*    */ package awareline.main.mod.implement.visual.sucks.targethuduse;
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
/*    */ public final class TimeUtil
/*    */ {
/*    */   public long lastMS;
/*    */   
/*    */   public int convertToMS(int d) {
/* 19 */     return 1000 / d;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getCurrentMS() {
/* 26 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 30 */     return System.currentTimeMillis() - this.lastMS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasReached(long milliseconds) {
/* 37 */     return (getCurrentMS() - this.lastMS >= milliseconds);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getDelay() {
/* 44 */     return System.currentTimeMillis() - this.lastMS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 51 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLastMS() {
/* 58 */     this.lastMS = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLastMS(long lastMS) {
/* 65 */     this.lastMS = lastMS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\sucks\targethuduse\TimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */