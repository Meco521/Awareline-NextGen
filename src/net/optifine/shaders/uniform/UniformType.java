/*     */ package net.optifine.shaders.uniform;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionFloatArray;
/*     */ 
/*     */ public enum UniformType {
/*   7 */   BOOL,
/*   8 */   INT,
/*   9 */   FLOAT,
/*  10 */   VEC2,
/*  11 */   VEC3,
/*  12 */   VEC4;
/*     */ 
/*     */   
/*     */   public ShaderUniformBase makeShaderUniform(String name) {
/*  16 */     switch (this) {
/*     */       
/*     */       case BOOL:
/*  19 */         return new ShaderUniform1i(name);
/*     */       
/*     */       case INT:
/*  22 */         return new ShaderUniform1i(name);
/*     */       
/*     */       case FLOAT:
/*  25 */         return new ShaderUniform1f(name);
/*     */       
/*     */       case VEC2:
/*  28 */         return new ShaderUniform2f(name);
/*     */       
/*     */       case VEC3:
/*  31 */         return new ShaderUniform3f(name);
/*     */       
/*     */       case VEC4:
/*  34 */         return new ShaderUniform4f(name);
/*     */     } 
/*     */     
/*  37 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUniform(IExpression expression, ShaderUniformBase uniform) {
/*  43 */     switch (this) {
/*     */       
/*     */       case BOOL:
/*  46 */         updateUniformBool((IExpressionBool)expression, (ShaderUniform1i)uniform);
/*     */         return;
/*     */       
/*     */       case INT:
/*  50 */         updateUniformInt((IExpressionFloat)expression, (ShaderUniform1i)uniform);
/*     */         return;
/*     */       
/*     */       case FLOAT:
/*  54 */         updateUniformFloat((IExpressionFloat)expression, (ShaderUniform1f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC2:
/*  58 */         updateUniformFloat2((IExpressionFloatArray)expression, (ShaderUniform2f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC3:
/*  62 */         updateUniformFloat3((IExpressionFloatArray)expression, (ShaderUniform3f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC4:
/*  66 */         updateUniformFloat4((IExpressionFloatArray)expression, (ShaderUniform4f)uniform);
/*     */         return;
/*     */     } 
/*     */     
/*  70 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformBool(IExpressionBool expression, ShaderUniform1i uniform) {
/*  76 */     boolean flag = expression.eval();
/*  77 */     int i = flag ? 1 : 0;
/*  78 */     uniform.setValue(i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformInt(IExpressionFloat expression, ShaderUniform1i uniform) {
/*  83 */     int i = (int)expression.eval();
/*  84 */     uniform.setValue(i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformFloat(IExpressionFloat expression, ShaderUniform1f uniform) {
/*  89 */     float f = expression.eval();
/*  90 */     uniform.setValue(f);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformFloat2(IExpressionFloatArray expression, ShaderUniform2f uniform) {
/*  95 */     float[] afloat = expression.eval();
/*     */     
/*  97 */     if (afloat.length != 2)
/*     */     {
/*  99 */       throw new RuntimeException("Value length is not 2, length: " + afloat.length);
/*     */     }
/*     */ 
/*     */     
/* 103 */     uniform.setValue(afloat[0], afloat[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformFloat3(IExpressionFloatArray expression, ShaderUniform3f uniform) {
/* 109 */     float[] afloat = expression.eval();
/*     */     
/* 111 */     if (afloat.length != 3)
/*     */     {
/* 113 */       throw new RuntimeException("Value length is not 3, length: " + afloat.length);
/*     */     }
/*     */ 
/*     */     
/* 117 */     uniform.setValue(afloat[0], afloat[1], afloat[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformFloat4(IExpressionFloatArray expression, ShaderUniform4f uniform) {
/* 123 */     float[] afloat = expression.eval();
/*     */     
/* 125 */     if (afloat.length != 4)
/*     */     {
/* 127 */       throw new RuntimeException("Value length is not 4, length: " + afloat.length);
/*     */     }
/*     */ 
/*     */     
/* 131 */     uniform.setValue(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesExpressionType(ExpressionType expressionType) {
/* 137 */     switch (this) {
/*     */       
/*     */       case BOOL:
/* 140 */         return (expressionType == ExpressionType.BOOL);
/*     */       
/*     */       case INT:
/* 143 */         return (expressionType == ExpressionType.FLOAT);
/*     */       
/*     */       case FLOAT:
/* 146 */         return (expressionType == ExpressionType.FLOAT);
/*     */       
/*     */       case VEC2:
/*     */       case VEC3:
/*     */       case VEC4:
/* 151 */         return (expressionType == ExpressionType.FLOAT_ARRAY);
/*     */     } 
/*     */     
/* 154 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UniformType parse(String type) {
/* 160 */     UniformType[] auniformtype = values();
/*     */     
/* 162 */     for (int i = 0; i < auniformtype.length; i++) {
/*     */       
/* 164 */       UniformType uniformtype = auniformtype[i];
/*     */       
/* 166 */       if (uniformtype.name().toLowerCase().equals(type))
/*     */       {
/* 168 */         return uniformtype;
/*     */       }
/*     */     } 
/*     */     
/* 172 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\UniformType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */