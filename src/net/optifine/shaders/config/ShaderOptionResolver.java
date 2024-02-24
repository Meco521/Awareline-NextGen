/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionResolver;
/*    */ 
/*    */ public class ShaderOptionResolver
/*    */   implements IExpressionResolver
/*    */ {
/* 11 */   private final Map<String, ExpressionShaderOptionSwitch> mapOptions = new HashMap<>();
/*    */ 
/*    */   
/*    */   public ShaderOptionResolver(ShaderOption[] options) {
/* 15 */     for (int i = 0; i < options.length; i++) {
/*    */       
/* 17 */       ShaderOption shaderoption = options[i];
/*    */       
/* 19 */       if (shaderoption instanceof ShaderOptionSwitch) {
/*    */         
/* 21 */         ShaderOptionSwitch shaderoptionswitch = (ShaderOptionSwitch)shaderoption;
/* 22 */         ExpressionShaderOptionSwitch expressionshaderoptionswitch = new ExpressionShaderOptionSwitch(shaderoptionswitch);
/* 23 */         this.mapOptions.put(shaderoption.getName(), expressionshaderoptionswitch);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 30 */     ExpressionShaderOptionSwitch expressionshaderoptionswitch = this.mapOptions.get(name);
/* 31 */     return (IExpression)expressionshaderoptionswitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderOptionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */