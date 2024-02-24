/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class ConstantFloat
/*    */   implements IExpressionFloat
/*    */ {
/*    */   private final float value;
/*    */   
/*    */   public ConstantFloat(float value) {
/*  9 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 14 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 19 */     return ExpressionType.FLOAT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 24 */     return String.valueOf(this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\ConstantFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */