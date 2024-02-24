/*    */ package net.optifine.expr;
/*    */ 
/*    */ import net.optifine.shaders.uniform.Smoother;
/*    */ 
/*    */ public class FunctionFloat
/*    */   implements IExpressionFloat {
/*    */   private final FunctionType type;
/*    */   private final IExpression[] arguments;
/*  9 */   private int smoothId = -1;
/*    */ 
/*    */   
/*    */   public FunctionFloat(FunctionType type, IExpression[] arguments) {
/* 13 */     this.type = type;
/* 14 */     this.arguments = arguments;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 19 */     IExpression iexpression, aiexpression[] = this.arguments;
/*    */     
/* 21 */     switch (this.type) {
/*    */       
/*    */       case SMOOTH:
/* 24 */         iexpression = aiexpression[0];
/*    */         
/* 26 */         if (!(iexpression instanceof ConstantFloat)) {
/*    */           
/* 28 */           float f = evalFloat(aiexpression, 0);
/* 29 */           float f1 = (aiexpression.length > 1) ? evalFloat(aiexpression, 1) : 1.0F;
/* 30 */           float f2 = (aiexpression.length > 2) ? evalFloat(aiexpression, 2) : f1;
/*    */           
/* 32 */           if (this.smoothId < 0)
/*    */           {
/* 34 */             this.smoothId = Smoother.getNextId();
/*    */           }
/*    */           
/* 37 */           float f3 = Smoother.getSmoothValue(this.smoothId, f, f1, f2);
/* 38 */           return f3;
/*    */         } 
/*    */         break;
/*    */     } 
/* 42 */     return this.type.evalFloat(this.arguments);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static float evalFloat(IExpression[] exprs, int index) {
/* 48 */     IExpressionFloat iexpressionfloat = (IExpressionFloat)exprs[index];
/* 49 */     float f = iexpressionfloat.eval();
/* 50 */     return f;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 55 */     return ExpressionType.FLOAT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\FunctionFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */