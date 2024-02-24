/*    */ package awareline.main.utility.animations.impl;
/*    */ 
/*    */ import awareline.main.utility.animations.Animation;
/*    */ import awareline.main.utility.animations.Direction;
/*    */ 
/*    */ public class EaseBackIn
/*    */   extends Animation {
/*    */   private final float easeAmount;
/*    */   
/*    */   public EaseBackIn(int ms, double endPoint, float easeAmount) {
/* 11 */     super(ms, endPoint);
/* 12 */     this.easeAmount = easeAmount;
/*    */   }
/*    */   
/*    */   public EaseBackIn(int ms, double endPoint, float easeAmount, Direction direction) {
/* 16 */     super(ms, endPoint, direction);
/* 17 */     this.easeAmount = easeAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean correctOutput() {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getEquation(double x) {
/* 27 */     float shrink = this.easeAmount + 1.0F;
/* 28 */     return Math.max(0.0D, 1.0D + shrink * Math.pow(x - 1.0D, 3.0D) + this.easeAmount * Math.pow(x - 1.0D, 2.0D));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\impl\EaseBackIn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */