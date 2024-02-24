/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import net.optifine.expr.ExpressionType;
/*    */ import net.optifine.expr.IExpressionFloat;
/*    */ 
/*    */ public class ShaderParameterIndexed
/*    */   implements IExpressionFloat
/*    */ {
/*    */   private final ShaderParameterFloat type;
/*    */   private final int index1;
/*    */   private final int index2;
/*    */   
/*    */   public ShaderParameterIndexed(ShaderParameterFloat type) {
/* 14 */     this(type, 0, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderParameterIndexed(ShaderParameterFloat type, int index1) {
/* 19 */     this(type, index1, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderParameterIndexed(ShaderParameterFloat type, int index1, int index2) {
/* 24 */     this.type = type;
/* 25 */     this.index1 = index1;
/* 26 */     this.index2 = index2;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 31 */     return this.type.eval(this.index1, this.index2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 36 */     return ExpressionType.FLOAT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     return (this.type.getIndexNames1() == null) ? String.valueOf(this.type) : ((this.type.getIndexNames2() == null) ? (this.type + "." + this.type.getIndexNames1()[this.index1]) : (this.type + "." + this.type.getIndexNames1()[this.index1] + "." + this.type.getIndexNames2()[this.index2]));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderParameterIndexed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */