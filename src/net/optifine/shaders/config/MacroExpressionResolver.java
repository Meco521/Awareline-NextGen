/*    */ package net.optifine.shaders.config;
/*    */ import java.util.Map;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.expr.ConstantFloat;
/*    */ import net.optifine.expr.FunctionBool;
/*    */ import net.optifine.expr.FunctionType;
/*    */ import net.optifine.expr.IExpression;
/*    */ 
/*    */ public class MacroExpressionResolver implements IExpressionResolver {
/* 10 */   private Map<String, String> mapMacroValues = null;
/*    */ 
/*    */   
/*    */   public MacroExpressionResolver(Map<String, String> mapMacroValues) {
/* 14 */     this.mapMacroValues = mapMacroValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 19 */     String s = "defined_";
/*    */     
/* 21 */     if (name.startsWith(s)) {
/*    */       
/* 23 */       String s2 = name.substring(s.length());
/* 24 */       return this.mapMacroValues.containsKey(s2) ? (IExpression)new FunctionBool(FunctionType.TRUE, (IExpression[])null) : (IExpression)new FunctionBool(FunctionType.FALSE, (IExpression[])null);
/*    */     } 
/*    */ 
/*    */     
/* 28 */     while (this.mapMacroValues.containsKey(name)) {
/*    */       
/* 30 */       String s1 = this.mapMacroValues.get(name);
/*    */       
/* 32 */       if (s1 == null || s1.equals(name)) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 37 */       name = s1;
/*    */     } 
/*    */     
/* 40 */     int i = Config.parseInt(name, -2147483648);
/*    */     
/* 42 */     if (i == Integer.MIN_VALUE) {
/*    */       
/* 44 */       Config.warn("Unknown macro value: " + name);
/* 45 */       return (IExpression)new ConstantFloat(0.0F);
/*    */     } 
/*    */ 
/*    */     
/* 49 */     return (IExpression)new ConstantFloat(i);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\MacroExpressionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */