/*    */ package awareline.main.component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StopWatch
/*    */ {
/*    */   private long millis;
/*    */   
/*    */   public long getMillis() {
/* 14 */     return this.millis;
/*    */   }
/*    */   public StopWatch() {
/* 17 */     reset();
/*    */   }
/*    */   
/*    */   public boolean finished(long delay) {
/* 21 */     return (System.currentTimeMillis() - delay >= this.millis);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 25 */     this.millis = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 29 */     return System.currentTimeMillis() - this.millis;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\component\StopWatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */