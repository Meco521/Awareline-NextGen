/*    */ package awareline.main.utility.animations.impl;
/*    */ 
/*    */ import awareline.main.utility.animations.Animation;
/*    */ import awareline.main.utility.animations.Direction;
/*    */ 
/*    */ public class ElasticAnimation
/*    */   extends Animation
/*    */ {
/*    */   final float easeAmount;
/*    */   final float smooth;
/*    */   final boolean reallyElastic;
/*    */   
/*    */   public ElasticAnimation(int ms, double endPoint, float elasticity, float smooth, boolean moreElasticity) {
/* 14 */     super(ms, endPoint);
/* 15 */     this.easeAmount = elasticity;
/* 16 */     this.smooth = smooth;
/* 17 */     this.reallyElastic = moreElasticity;
/*    */   }
/*    */   
/*    */   public ElasticAnimation(int ms, double endPoint, float elasticity, float smooth, boolean moreElasticity, Direction direction) {
/* 21 */     super(ms, endPoint, direction);
/* 22 */     this.easeAmount = elasticity;
/* 23 */     this.smooth = smooth;
/* 24 */     this.reallyElastic = moreElasticity;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getEquation(double x) {
/* 29 */     x = Math.pow(x, this.smooth);
/* 30 */     double elasticity = (this.easeAmount * 0.1F);
/* 31 */     return Math.pow(2.0D, -10.0D * (this.reallyElastic ? Math.sqrt(x) : x)) * Math.sin((x - elasticity / 4.0D) * 6.283185307179586D / elasticity) + 1.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\impl\ElasticAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */