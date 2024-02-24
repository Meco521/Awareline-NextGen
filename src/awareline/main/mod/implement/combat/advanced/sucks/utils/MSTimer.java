/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MSTimer
/*    */ {
/* 12 */   private long time = -1L;
/*    */   
/*    */   public boolean hasTimePassed(long MS) {
/* 15 */     return (System.currentTimeMillis() >= this.time + MS);
/*    */   }
/*    */   
/*    */   public long hasTimeLeft(long MS) {
/* 19 */     return MS + this.time - System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 23 */     this.time = System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\MSTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */