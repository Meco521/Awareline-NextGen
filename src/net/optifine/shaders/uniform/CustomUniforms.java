/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionCached;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomUniforms
/*    */ {
/*    */   private final CustomUniform[] uniforms;
/*    */   private final IExpressionCached[] expressionsCached;
/*    */   
/*    */   public CustomUniforms(CustomUniform[] uniforms, Map<String, IExpression> mapExpressions) {
/* 17 */     this.uniforms = uniforms;
/* 18 */     List<IExpressionCached> list = new ArrayList<>();
/*    */     
/* 20 */     for (IExpression iexpression : mapExpressions.values()) {
/*    */ 
/*    */       
/* 23 */       if (iexpression instanceof IExpressionCached) {
/*    */         
/* 25 */         IExpressionCached iexpressioncached = (IExpressionCached)iexpression;
/* 26 */         list.add(iexpressioncached);
/*    */       } 
/*    */     } 
/*    */     
/* 30 */     this.expressionsCached = list.<IExpressionCached>toArray(new IExpressionCached[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 35 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 37 */       CustomUniform customuniform = this.uniforms[i];
/* 38 */       customuniform.setProgram(program);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 44 */     resetCache();
/*    */     
/* 46 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 48 */       CustomUniform customuniform = this.uniforms[i];
/* 49 */       customuniform.update();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void resetCache() {
/* 55 */     for (int i = 0; i < this.expressionsCached.length; i++) {
/*    */       
/* 57 */       IExpressionCached iexpressioncached = this.expressionsCached[i];
/* 58 */       iexpressioncached.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 64 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 66 */       CustomUniform customuniform = this.uniforms[i];
/* 67 */       customuniform.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\CustomUniforms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */