/*    */ package awareline.main.utility.timer;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Timer
/*    */ {
/*    */   public long getLastMS() {
/* 10 */     return this.lastMS; } public long getPreviousTime() {
/* 11 */     return this.previousTime;
/*    */   }
/*    */ 
/*    */   
/* 15 */   private long lastMS = 0L;
/* 16 */   private long previousTime = -1L;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sleep(long time) {
/* 21 */     if (time() >= time) {
/* 22 */       reset();
/* 23 */       return true;
/*    */     } 
/*    */     
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   public boolean check(float milliseconds) {
/* 30 */     return ((float)(System.currentTimeMillis() - this.previousTime) >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean delay(double milliseconds) {
/* 34 */     return (MathHelper.clamp_float((float)(getCurrentMS() - this.lastMS), 0.0F, (float)milliseconds) >= milliseconds);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 38 */     this.previousTime = System.currentTimeMillis();
/* 39 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */   
/*    */   public long time() {
/* 43 */     return System.nanoTime() / 1000000L - this.lastMS;
/*    */   }
/*    */   
/*    */   public long getCurrentMS() {
/* 47 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public double getLastDelay() {
/* 51 */     return (getCurrentMS() - this.lastMS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\timer\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */