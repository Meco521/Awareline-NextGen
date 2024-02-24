/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.expr.ConstantFloat;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionResolver;
/*     */ import net.optifine.shaders.SMCLog;
/*     */ 
/*     */ public class ShaderExpressionResolver
/*     */   implements IExpressionResolver
/*     */ {
/*  14 */   private final Map<String, IExpression> mapExpressions = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ShaderExpressionResolver(Map<String, IExpression> map) {
/*  18 */     registerExpressions();
/*     */     
/*  20 */     for (Map.Entry<String, IExpression> entry : map.entrySet()) {
/*     */       
/*  22 */       IExpression iexpression = entry.getValue();
/*  23 */       registerExpression(entry.getKey(), iexpression);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerExpressions() {
/*  29 */     ShaderParameterFloat[] ashaderparameterfloat = ShaderParameterFloat.values();
/*     */     
/*  31 */     for (int i = 0; i < ashaderparameterfloat.length; i++) {
/*     */       
/*  33 */       ShaderParameterFloat shaderparameterfloat = ashaderparameterfloat[i];
/*  34 */       addParameterFloat(this.mapExpressions, shaderparameterfloat);
/*     */     } 
/*     */     
/*  37 */     ShaderParameterBool[] ashaderparameterbool = ShaderParameterBool.values();
/*     */     
/*  39 */     for (int k = 0; k < ashaderparameterbool.length; k++) {
/*     */       
/*  41 */       ShaderParameterBool shaderparameterbool = ashaderparameterbool[k];
/*  42 */       this.mapExpressions.put(shaderparameterbool.getName(), shaderparameterbool);
/*     */     } 
/*     */     
/*  45 */     for (BiomeGenBase biomegenbase : BiomeGenBase.BIOME_ID_MAP.values()) {
/*     */       
/*  47 */       String s = biomegenbase.biomeName.trim();
/*  48 */       s = "BIOME_" + s.toUpperCase().replace(' ', '_');
/*  49 */       int j = biomegenbase.biomeID;
/*  50 */       ConstantFloat constantFloat = new ConstantFloat(j);
/*  51 */       registerExpression(s, (IExpression)constantFloat);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addParameterFloat(Map<String, IExpression> map, ShaderParameterFloat spf) {
/*  57 */     String[] astring = spf.getIndexNames1();
/*     */     
/*  59 */     if (astring == null) {
/*     */       
/*  61 */       map.put(spf.getName(), new ShaderParameterIndexed(spf));
/*     */     }
/*     */     else {
/*     */       
/*  65 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/*  67 */         String s = astring[i];
/*  68 */         String[] astring1 = spf.getIndexNames2();
/*     */         
/*  70 */         if (astring1 == null) {
/*     */           
/*  72 */           map.put(spf.getName() + "." + s, new ShaderParameterIndexed(spf, i));
/*     */         }
/*     */         else {
/*     */           
/*  76 */           for (int j = 0; j < astring1.length; j++) {
/*     */             
/*  78 */             String s1 = astring1[j];
/*  79 */             map.put(spf.getName() + "." + s + "." + s1, new ShaderParameterIndexed(spf, i, j));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean registerExpression(String name, IExpression expr) {
/*  88 */     if (this.mapExpressions.containsKey(name)) {
/*     */       
/*  90 */       SMCLog.warning("Expression already defined: " + name);
/*  91 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  95 */     this.mapExpressions.put(name, expr);
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpression getExpression(String name) {
/* 102 */     return this.mapExpressions.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasExpression(String name) {
/* 107 */     return this.mapExpressions.containsKey(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderExpressionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */