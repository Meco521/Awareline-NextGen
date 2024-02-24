/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.shaders.SMCLog;
/*    */ 
/*    */ 
/*    */ public class CustomUniform
/*    */ {
/*    */   private final String name;
/*    */   private final UniformType type;
/*    */   private final IExpression expression;
/*    */   private final ShaderUniformBase shaderUniform;
/*    */   
/*    */   public CustomUniform(String name, UniformType type, IExpression expression) {
/* 15 */     this.name = name;
/* 16 */     this.type = type;
/* 17 */     this.expression = expression;
/* 18 */     this.shaderUniform = type.makeShaderUniform(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 23 */     this.shaderUniform.setProgram(program);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 28 */     if (this.shaderUniform.isDefined()) {
/*    */       
/*    */       try {
/*    */         
/* 32 */         this.type.updateUniform(this.expression, this.shaderUniform);
/*    */       }
/* 34 */       catch (RuntimeException runtimeexception) {
/*    */         
/* 36 */         SMCLog.severe("Error updating custom uniform: " + this.shaderUniform.getName());
/* 37 */         SMCLog.severe(runtimeexception.getClass().getName() + ": " + runtimeexception.getMessage());
/* 38 */         this.shaderUniform.disable();
/* 39 */         SMCLog.severe("Custom uniform disabled: " + this.shaderUniform.getName());
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 46 */     this.shaderUniform.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 51 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public UniformType getType() {
/* 56 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public IExpression getExpression() {
/* 61 */     return this.expression;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniformBase getShaderUniform() {
/* 66 */     return this.shaderUniform;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return this.type.name().toLowerCase() + " " + this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\CustomUniform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */