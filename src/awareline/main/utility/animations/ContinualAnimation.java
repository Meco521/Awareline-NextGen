/*    */ package awareline.main.utility.animations;
/*    */ 
/*    */ import awareline.main.utility.animations.impl.SmoothStepAnimation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContinualAnimation
/*    */ {
/*    */   private float output;
/*    */   private float endpoint;
/* 12 */   private Animation animation = (Animation)new SmoothStepAnimation(0, 0.0D, Direction.BACKWARDS);
/*    */   
/*    */   public void animate(float destination, int ms) {
/* 15 */     this.output = this.endpoint - this.animation.getOutput().floatValue();
/* 16 */     this.endpoint = destination;
/* 17 */     if (this.output != this.endpoint - destination) {
/* 18 */       this.animation = (Animation)new SmoothStepAnimation(ms, (this.endpoint - this.output), Direction.BACKWARDS);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDone() {
/* 24 */     return (this.output == this.endpoint || this.animation.isDone());
/*    */   }
/*    */ 
/*    */   
/*    */   public float getOutput() {
/* 29 */     this.output = this.endpoint - this.animation.getOutput().floatValue();
/* 30 */     return this.output;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\ContinualAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */