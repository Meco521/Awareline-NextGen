/*    */ package awareline.main.utility.animations.impl;
/*    */ 
/*    */ import awareline.main.utility.animations.Animation;
/*    */ import awareline.main.utility.animations.Direction;
/*    */ 
/*    */ public class SmoothStepAnimation
/*    */   extends Animation
/*    */ {
/*    */   public SmoothStepAnimation(int ms, double endPoint) {
/* 10 */     super(ms, endPoint);
/*    */   }
/*    */   
/*    */   public SmoothStepAnimation(int ms, double endPoint, Direction direction) {
/* 14 */     super(ms, endPoint, direction);
/*    */   }
/*    */   
/*    */   protected double getEquation(double x) {
/* 18 */     return -2.0D * Math.pow(x, 3.0D) + 3.0D * Math.pow(x, 2.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\impl\SmoothStepAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */