/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class FunctionFloatArray
/*    */   implements IExpressionFloatArray
/*    */ {
/*    */   private final FunctionType type;
/*    */   private final IExpression[] arguments;
/*    */   
/*    */   public FunctionFloatArray(FunctionType type, IExpression[] arguments) {
/* 10 */     this.type = type;
/* 11 */     this.arguments = arguments;
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] eval() {
/* 16 */     return this.type.evalFloatArray(this.arguments);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 21 */     return ExpressionType.FLOAT_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\FunctionFloatArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */