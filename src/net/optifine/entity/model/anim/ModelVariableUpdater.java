/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.expr.ExpressionParser;
/*    */ import net.optifine.expr.IExpressionFloat;
/*    */ import net.optifine.expr.ParseException;
/*    */ 
/*    */ 
/*    */ public class ModelVariableUpdater
/*    */ {
/*    */   private final String modelVariableName;
/*    */   private final String expressionText;
/*    */   private ModelVariableFloat modelVariable;
/*    */   private IExpressionFloat expression;
/*    */   
/*    */   public boolean initialize(IModelResolver mr) {
/* 17 */     this.modelVariable = mr.getModelVariable(this.modelVariableName);
/*    */     
/* 19 */     if (this.modelVariable == null) {
/*    */       
/* 21 */       Config.warn("Model variable not found: " + this.modelVariableName);
/* 22 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 28 */       ExpressionParser expressionparser = new ExpressionParser(mr);
/* 29 */       this.expression = expressionparser.parseFloat(this.expressionText);
/* 30 */       return true;
/*    */     }
/* 32 */     catch (ParseException parseexception) {
/*    */       
/* 34 */       Config.warn("Error parsing expression: " + this.expressionText);
/* 35 */       Config.warn(parseexception.getClass().getName() + ": " + parseexception.getMessage());
/* 36 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelVariableUpdater(String modelVariableName, String expressionText) {
/* 43 */     this.modelVariableName = modelVariableName;
/* 44 */     this.expressionText = expressionText;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 49 */     float f = this.expression.eval();
/* 50 */     this.modelVariable.setValue(f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\anim\ModelVariableUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */