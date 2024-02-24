/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class ExpressionFloatArrayCached
/*    */   implements IExpressionFloatArray, IExpressionCached
/*    */ {
/*    */   private final IExpressionFloatArray expression;
/*    */   private boolean cached;
/*    */   private float[] value;
/*    */   
/*    */   public ExpressionFloatArrayCached(IExpressionFloatArray expression) {
/* 11 */     this.expression = expression;
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] eval() {
/* 16 */     if (!this.cached) {
/*    */       
/* 18 */       this.value = this.expression.eval();
/* 19 */       this.cached = true;
/*    */     } 
/*    */     
/* 22 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 27 */     this.cached = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 32 */     return ExpressionType.FLOAT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "cached(" + this.expression + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\ExpressionFloatArrayCached.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */