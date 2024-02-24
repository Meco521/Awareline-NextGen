/*    */ package awareline.main.utility.animations.impl;
/*    */ 
/*    */ import awareline.main.utility.animations.Animation;
/*    */ import awareline.main.utility.animations.Direction;
/*    */ 
/*    */ public class DecelerateAnimation
/*    */   extends Animation
/*    */ {
/*    */   public DecelerateAnimation(int ms, double endPoint) {
/* 10 */     super(ms, endPoint);
/*    */   }
/*    */   
/*    */   public DecelerateAnimation(int ms, double endPoint, Direction direction) {
/* 14 */     super(ms, endPoint, direction);
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getEquation(double x) {
/* 19 */     return 1.0D - (x - 1.0D) * (x - 1.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\impl\DecelerateAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */