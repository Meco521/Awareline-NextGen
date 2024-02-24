/*    */ package awareline.main.utility.animations.impl;
/*    */ 
/*    */ import awareline.main.utility.animations.Animation;
/*    */ import awareline.main.utility.animations.Direction;
/*    */ 
/*    */ 
/*    */ public class EaseOutSine
/*    */   extends Animation
/*    */ {
/*    */   public EaseOutSine(int ms, double endPoint) {
/* 11 */     super(ms, endPoint);
/*    */   }
/*    */   
/*    */   public EaseOutSine(int ms, double endPoint, Direction direction) {
/* 15 */     super(ms, endPoint, direction);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean correctOutput() {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getEquation(double x) {
/* 25 */     return Math.sin(x * 1.5707963267948966D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\impl\EaseOutSine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */