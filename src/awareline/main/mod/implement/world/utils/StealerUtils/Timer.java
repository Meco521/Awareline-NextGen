/*    */ package awareline.main.mod.implement.world.utils.StealerUtils;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Timer
/*    */ {
/* 13 */   private long lastMS = 0L;
/* 14 */   private long previousTime = -1L;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sleep(long time) {
/* 19 */     if (time() >= time) {
/* 20 */       reset();
/* 21 */       return true;
/*    */     } 
/*    */     
/* 24 */     return false;
/*    */   }
/*    */   
/*    */   public boolean check(float milliseconds) {
/* 28 */     return ((float)(System.currentTimeMillis() - this.previousTime) >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean delay(double milliseconds) {
/* 32 */     return (MathHelper.clamp_float((float)(getCurrentMS() - this.lastMS), 0.0F, (float)milliseconds) >= milliseconds);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 36 */     this.previousTime = System.currentTimeMillis();
/* 37 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */   
/*    */   public long time() {
/* 41 */     return System.nanoTime() / 1000000L - this.lastMS;
/*    */   }
/*    */   
/*    */   public long getCurrentMS() {
/* 45 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public double getLastDelay() {
/* 49 */     return (getCurrentMS() - this.lastMS);
/*    */   }
/*    */   
/*    */   public long getLastMS() {
/* 53 */     return this.lastMS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\StealerUtils\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */