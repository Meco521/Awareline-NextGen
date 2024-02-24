/*    */ package awareline.main.ui.font.obj;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Meter
/*    */ {
/*  7 */   private long time = System.nanoTime();
/*    */ 
/*    */   
/*    */   public void update() {
/* 11 */     this.time = System.nanoTime();
/*    */   }
/*    */   
/*    */   public long getPastTime() {
/* 15 */     return System.nanoTime() - this.time;
/*    */   }
/*    */   
/*    */   public strictfp double getPastTimeMillisecond() {
/* 19 */     return getPastTime() / 1000000.0D;
/*    */   }
/*    */   
/*    */   public strictfp double getPastTimeSecond() {
/* 23 */     return getPastTime() / 1.0E9D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\obj\Meter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */