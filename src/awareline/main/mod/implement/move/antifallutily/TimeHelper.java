/*    */ package awareline.main.mod.implement.move.antifallutily;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeHelper
/*    */ {
/*  8 */   public long lastMs = 0L;
/*    */ 
/*    */   
/*    */   public void reset() {
/* 12 */     this.lastMs = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean delay(long nextDelay) {
/* 16 */     return (System.currentTimeMillis() - this.lastMs >= nextDelay);
/*    */   }
/*    */   
/*    */   public boolean delay(float nextDelay, boolean reset) {
/* 20 */     if ((float)(System.currentTimeMillis() - this.lastMs) >= nextDelay) {
/* 21 */       if (reset) {
/* 22 */         reset();
/*    */       }
/* 24 */       return true;
/*    */     } 
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isDelayComplete(double valueState) {
/* 30 */     return ((System.currentTimeMillis() - this.lastMs) >= valueState);
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 34 */     return System.currentTimeMillis() - this.lastMs;
/*    */   }
/*    */   
/*    */   public long getLastMs() {
/* 38 */     return this.lastMs;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\antifallutily\TimeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */