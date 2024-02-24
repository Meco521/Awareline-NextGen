/*    */ package awareline.main.ui.animations;
/*    */ public class TranslateUtil {
/*    */   private float x;
/*    */   private float y;
/*    */   private long lastMS;
/*    */   
/*  7 */   public float getX() { return this.x; } public float getY() {
/*  8 */     return this.y;
/*    */   } public long getLastMS() {
/* 10 */     return this.lastMS;
/*    */   }
/*    */   public TranslateUtil(float x, float y) {
/* 13 */     this.x = x;
/* 14 */     this.y = y;
/* 15 */     this.lastMS = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void interpolate(float targetX, float targetY, float smoothing) {
/* 19 */     long currentMS = System.currentTimeMillis();
/* 20 */     long delta = currentMS - this.lastMS;
/* 21 */     this.lastMS = currentMS;
/* 22 */     int deltaX = (int)(Math.abs(targetX - this.x) * smoothing);
/* 23 */     int deltaY = (int)(Math.abs(targetY - this.y) * smoothing);
/* 24 */     this.x = AnimationUtil.calculateCompensation(targetX, this.x, delta, deltaX);
/* 25 */     this.y = AnimationUtil.calculateCompensation(targetY, this.y, delta, deltaY);
/*    */   }
/*    */   
/*    */   public void setX(float x) {
/* 29 */     this.x = x;
/*    */   }
/*    */   
/*    */   public void setY(float y) {
/* 33 */     this.y = y;
/*    */   }
/*    */   
/*    */   public float getValue() {
/* 37 */     return this.y;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\animations\TranslateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */