/*    */ package awareline.main.ui.animations;
/*    */ 
/*    */ 
/*    */ public class SmoothAnimationTimer
/*    */ {
/*    */   public float target;
/*    */   
/*    */   public float getTarget() {
/*  9 */     return this.target;
/*    */   }
/* 11 */   public float speed = 0.3F; public float getSpeed() { return this.speed; }
/*    */    private float value;
/*    */   public SmoothAnimationTimer(float target) {
/* 14 */     this.target = target;
/*    */   }
/*    */   
/*    */   public SmoothAnimationTimer(float target, float speed) {
/* 18 */     this.target = target;
/* 19 */     this.speed = speed;
/*    */   }
/*    */   public float getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean update(boolean increment) {
/* 26 */     this.value = AnimationUtil.getAnimationStateFlux(this.value, increment ? this.target : 0.0F, Math.max(10.0F, Math.abs(this.value - (increment ? this.target : 0.0F)) * 40.0F) * this.speed);
/* 27 */     return (this.value == this.target);
/*    */   }
/*    */   
/*    */   public void setValue(float f) {
/* 31 */     this.value = f;
/*    */   }
/*    */   
/*    */   public void setTarget(float scrollY) {
/* 35 */     this.target = scrollY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\animations\SmoothAnimationTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */