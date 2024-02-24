/*     */ package net.optifine.expr;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.shaders.uniform.Smoother;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ 
/*     */ public enum FunctionType
/*     */ {
/*  15 */   PLUS(10, ExpressionType.FLOAT, "+", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  16 */   MINUS(10, ExpressionType.FLOAT, "-", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  17 */   MUL(11, ExpressionType.FLOAT, "*", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  18 */   DIV(11, ExpressionType.FLOAT, "/", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  19 */   MOD(11, ExpressionType.FLOAT, "%", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  20 */   NEG(12, ExpressionType.FLOAT, "neg", new ExpressionType[] { ExpressionType.FLOAT }),
/*  21 */   PI(ExpressionType.FLOAT, "pi", new ExpressionType[0]),
/*  22 */   SIN(ExpressionType.FLOAT, "sin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  23 */   COS(ExpressionType.FLOAT, "cos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  24 */   ASIN(ExpressionType.FLOAT, "asin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  25 */   ACOS(ExpressionType.FLOAT, "acos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  26 */   TAN(ExpressionType.FLOAT, "tan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  27 */   ATAN(ExpressionType.FLOAT, "atan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  28 */   ATAN2(ExpressionType.FLOAT, "atan2", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  29 */   TORAD(ExpressionType.FLOAT, "torad", new ExpressionType[] { ExpressionType.FLOAT }),
/*  30 */   TODEG(ExpressionType.FLOAT, "todeg", new ExpressionType[] { ExpressionType.FLOAT }),
/*  31 */   MIN(ExpressionType.FLOAT, "min", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  32 */   MAX(ExpressionType.FLOAT, "max", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  33 */   CLAMP(ExpressionType.FLOAT, "clamp", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  34 */   ABS(ExpressionType.FLOAT, "abs", new ExpressionType[] { ExpressionType.FLOAT }),
/*  35 */   FLOOR(ExpressionType.FLOAT, "floor", new ExpressionType[] { ExpressionType.FLOAT }),
/*  36 */   CEIL(ExpressionType.FLOAT, "ceil", new ExpressionType[] { ExpressionType.FLOAT }),
/*  37 */   EXP(ExpressionType.FLOAT, "exp", new ExpressionType[] { ExpressionType.FLOAT }),
/*  38 */   FRAC(ExpressionType.FLOAT, "frac", new ExpressionType[] { ExpressionType.FLOAT }),
/*  39 */   LOG(ExpressionType.FLOAT, "log", new ExpressionType[] { ExpressionType.FLOAT }),
/*  40 */   POW(ExpressionType.FLOAT, "pow", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  41 */   RANDOM(ExpressionType.FLOAT, "random", new ExpressionType[0]),
/*  42 */   ROUND(ExpressionType.FLOAT, "round", new ExpressionType[] { ExpressionType.FLOAT }),
/*  43 */   SIGNUM(ExpressionType.FLOAT, "signum", new ExpressionType[] { ExpressionType.FLOAT }),
/*  44 */   SQRT(ExpressionType.FLOAT, "sqrt", new ExpressionType[] { ExpressionType.FLOAT }),
/*  45 */   FMOD(ExpressionType.FLOAT, "fmod", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  46 */   TIME(ExpressionType.FLOAT, "time", new ExpressionType[0]),
/*  47 */   IF(ExpressionType.FLOAT, "if", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT })),
/*  48 */   NOT(12, ExpressionType.BOOL, "!", new ExpressionType[] { ExpressionType.BOOL }),
/*  49 */   AND(3, ExpressionType.BOOL, "&&", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL }),
/*  50 */   OR(2, ExpressionType.BOOL, "||", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL }),
/*  51 */   GREATER(8, ExpressionType.BOOL, ">", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  52 */   GREATER_OR_EQUAL(8, ExpressionType.BOOL, ">=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  53 */   SMALLER(8, ExpressionType.BOOL, "<", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  54 */   SMALLER_OR_EQUAL(8, ExpressionType.BOOL, "<=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  55 */   EQUAL(7, ExpressionType.BOOL, "==", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  56 */   NOT_EQUAL(7, ExpressionType.BOOL, "!=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  57 */   BETWEEN(7, ExpressionType.BOOL, "between", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  58 */   EQUALS(7, ExpressionType.BOOL, "equals", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  59 */   IN(ExpressionType.BOOL, "in", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT })),
/*  60 */   SMOOTH(ExpressionType.FLOAT, "smooth", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).maxCount(4)),
/*  61 */   TRUE(ExpressionType.BOOL, "true", new ExpressionType[0]),
/*  62 */   FALSE(ExpressionType.BOOL, "false", new ExpressionType[0]),
/*  63 */   VEC2(ExpressionType.FLOAT_ARRAY, "vec2", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  64 */   VEC3(ExpressionType.FLOAT_ARRAY, "vec3", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  65 */   VEC4(ExpressionType.FLOAT_ARRAY, "vec4", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }); private final int precedence; private final ExpressionType expressionType; private final String name;
/*     */   private final IParameters parameters;
/*     */   public static FunctionType[] VALUES;
/*     */   private static final Map<Integer, Float> mapSmooth;
/*     */   
/*     */   static {
/*  71 */     VALUES = values();
/*  72 */     mapSmooth = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FunctionType(int precedence, ExpressionType expressionType, String name, IParameters parameters) {
/*  91 */     this.precedence = precedence;
/*  92 */     this.expressionType = expressionType;
/*  93 */     this.name = name;
/*  94 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  99 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrecedence() {
/* 104 */     return this.precedence;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/* 109 */     return this.expressionType;
/*     */   }
/*     */ 
/*     */   
/*     */   public IParameters getParameters() {
/* 114 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParameterCount(IExpression[] arguments) {
/* 119 */     return (this.parameters.getParameterTypes(arguments)).length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
/* 124 */     return this.parameters.getParameterTypes(arguments); } public float evalFloat(IExpression[] args) { float f, f1, f2, f3;
/*     */     Minecraft minecraft;
/*     */     WorldClient worldClient;
/*     */     int i, k, j;
/*     */     float f4, f5, f6, f7;
/* 129 */     switch (this) {
/*     */       
/*     */       case PLUS:
/* 132 */         return evalFloat(args, 0) + evalFloat(args, 1);
/*     */       
/*     */       case MINUS:
/* 135 */         return evalFloat(args, 0) - evalFloat(args, 1);
/*     */       
/*     */       case MUL:
/* 138 */         return evalFloat(args, 0) * evalFloat(args, 1);
/*     */       
/*     */       case DIV:
/* 141 */         return evalFloat(args, 0) / evalFloat(args, 1);
/*     */       
/*     */       case MOD:
/* 144 */         f = evalFloat(args, 0);
/* 145 */         f1 = evalFloat(args, 1);
/* 146 */         return f - f1 * (int)(f / f1);
/*     */       
/*     */       case NEG:
/* 149 */         return -evalFloat(args, 0);
/*     */       
/*     */       case PI:
/* 152 */         return MathHelper.PI;
/*     */       
/*     */       case SIN:
/* 155 */         return MathHelper.sin(evalFloat(args, 0));
/*     */       
/*     */       case COS:
/* 158 */         return MathHelper.cos(evalFloat(args, 0));
/*     */       
/*     */       case ASIN:
/* 161 */         return MathUtils.asin(evalFloat(args, 0));
/*     */       
/*     */       case ACOS:
/* 164 */         return MathUtils.acos(evalFloat(args, 0));
/*     */       
/*     */       case TAN:
/* 167 */         return (float)Math.tan(evalFloat(args, 0));
/*     */       
/*     */       case ATAN:
/* 170 */         return (float)Math.atan(evalFloat(args, 0));
/*     */       
/*     */       case ATAN2:
/* 173 */         return (float)MathHelper.atan2(evalFloat(args, 0), evalFloat(args, 1));
/*     */       
/*     */       case TORAD:
/* 176 */         return MathUtils.toRad(evalFloat(args, 0));
/*     */       
/*     */       case TODEG:
/* 179 */         return MathUtils.toDeg(evalFloat(args, 0));
/*     */       
/*     */       case MIN:
/* 182 */         return getMin(args);
/*     */       
/*     */       case MAX:
/* 185 */         return getMax(args);
/*     */       
/*     */       case CLAMP:
/* 188 */         return MathHelper.clamp_float(evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2));
/*     */       
/*     */       case ABS:
/* 191 */         return MathHelper.abs(evalFloat(args, 0));
/*     */       
/*     */       case EXP:
/* 194 */         return (float)Math.exp(evalFloat(args, 0));
/*     */       
/*     */       case FLOOR:
/* 197 */         return MathHelper.floor_float(evalFloat(args, 0));
/*     */       
/*     */       case CEIL:
/* 200 */         return MathHelper.ceiling_float_int(evalFloat(args, 0));
/*     */       
/*     */       case FRAC:
/* 203 */         return (float)MathHelper.func_181162_h(evalFloat(args, 0));
/*     */       
/*     */       case LOG:
/* 206 */         return (float)Math.log(evalFloat(args, 0));
/*     */       
/*     */       case POW:
/* 209 */         return (float)Math.pow(evalFloat(args, 0), evalFloat(args, 1));
/*     */       
/*     */       case RANDOM:
/* 212 */         return (float)Math.random();
/*     */       
/*     */       case ROUND:
/* 215 */         return Math.round(evalFloat(args, 0));
/*     */       
/*     */       case SIGNUM:
/* 218 */         return Math.signum(evalFloat(args, 0));
/*     */       
/*     */       case SQRT:
/* 221 */         return MathHelper.sqrt_float(evalFloat(args, 0));
/*     */       
/*     */       case FMOD:
/* 224 */         f2 = evalFloat(args, 0);
/* 225 */         f3 = evalFloat(args, 1);
/* 226 */         return f2 - f3 * MathHelper.floor_float(f2 / f3);
/*     */       
/*     */       case TIME:
/* 229 */         minecraft = Minecraft.getMinecraft();
/* 230 */         worldClient = minecraft.theWorld;
/*     */         
/* 232 */         if (worldClient == null)
/*     */         {
/* 234 */           return 0.0F;
/*     */         }
/*     */         
/* 237 */         return (float)(worldClient.getTotalWorldTime() % 24000L) + Config.renderPartialTicks;
/*     */       
/*     */       case IF:
/* 240 */         i = (args.length - 1) / 2;
/*     */         
/* 242 */         for (k = 0; k < i; k++) {
/*     */           
/* 244 */           int l = k << 1;
/*     */           
/* 246 */           if (evalBool(args, l))
/*     */           {
/* 248 */             return evalFloat(args, l + 1);
/*     */           }
/*     */         } 
/*     */         
/* 252 */         return evalFloat(args, i << 1);
/*     */       
/*     */       case SMOOTH:
/* 255 */         j = (int)evalFloat(args, 0);
/* 256 */         f4 = evalFloat(args, 1);
/* 257 */         f5 = (args.length > 2) ? evalFloat(args, 2) : 1.0F;
/* 258 */         f6 = (args.length > 3) ? evalFloat(args, 3) : f5;
/* 259 */         f7 = Smoother.getSmoothValue(j, f4, f5, f6);
/* 260 */         return f7;
/*     */     } 
/*     */     
/* 263 */     Config.warn("Unknown function type: " + this);
/* 264 */     return 0.0F; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMin(IExpression[] exprs) {
/* 270 */     if (exprs.length == 2)
/*     */     {
/* 272 */       return Math.min(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/*     */ 
/*     */     
/* 276 */     float f = evalFloat(exprs, 0);
/*     */     
/* 278 */     for (int i = 1; i < exprs.length; i++) {
/*     */       
/* 280 */       float f1 = evalFloat(exprs, i);
/*     */       
/* 282 */       if (f1 < f)
/*     */       {
/* 284 */         f = f1;
/*     */       }
/*     */     } 
/*     */     
/* 288 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMax(IExpression[] exprs) {
/* 294 */     if (exprs.length == 2)
/*     */     {
/* 296 */       return Math.max(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/*     */ 
/*     */     
/* 300 */     float f = evalFloat(exprs, 0);
/*     */     
/* 302 */     for (int i = 1; i < exprs.length; i++) {
/*     */       
/* 304 */       float f1 = evalFloat(exprs, i);
/*     */       
/* 306 */       if (f1 > f)
/*     */       {
/* 308 */         f = f1;
/*     */       }
/*     */     } 
/*     */     
/* 312 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float evalFloat(IExpression[] exprs, int index) {
/* 318 */     IExpressionFloat iexpressionfloat = (IExpressionFloat)exprs[index];
/* 319 */     float f = iexpressionfloat.eval();
/* 320 */     return f;
/*     */   }
/*     */   public boolean evalBool(IExpression[] args) {
/*     */     float f, f1, f2, f3;
/*     */     int i;
/* 325 */     switch (this) {
/*     */       
/*     */       case TRUE:
/* 328 */         return true;
/*     */       
/*     */       case FALSE:
/* 331 */         return false;
/*     */       
/*     */       case NOT:
/* 334 */         return !evalBool(args, 0);
/*     */       
/*     */       case AND:
/* 337 */         return (evalBool(args, 0) && evalBool(args, 1));
/*     */       
/*     */       case OR:
/* 340 */         return (evalBool(args, 0) || evalBool(args, 1));
/*     */       
/*     */       case GREATER:
/* 343 */         return (evalFloat(args, 0) > evalFloat(args, 1));
/*     */       
/*     */       case GREATER_OR_EQUAL:
/* 346 */         return (evalFloat(args, 0) >= evalFloat(args, 1));
/*     */       
/*     */       case SMALLER:
/* 349 */         return (evalFloat(args, 0) < evalFloat(args, 1));
/*     */       
/*     */       case SMALLER_OR_EQUAL:
/* 352 */         return (evalFloat(args, 0) <= evalFloat(args, 1));
/*     */       
/*     */       case EQUAL:
/* 355 */         return (evalFloat(args, 0) == evalFloat(args, 1));
/*     */       
/*     */       case NOT_EQUAL:
/* 358 */         return (evalFloat(args, 0) != evalFloat(args, 1));
/*     */       
/*     */       case BETWEEN:
/* 361 */         f = evalFloat(args, 0);
/* 362 */         return (f >= evalFloat(args, 1) && f <= evalFloat(args, 2));
/*     */       
/*     */       case EQUALS:
/* 365 */         f1 = evalFloat(args, 0) - evalFloat(args, 1);
/* 366 */         f2 = evalFloat(args, 2);
/* 367 */         return (Math.abs(f1) <= f2);
/*     */       
/*     */       case IN:
/* 370 */         f3 = evalFloat(args, 0);
/*     */         
/* 372 */         for (i = 1; i < args.length; i++) {
/*     */           
/* 374 */           float f4 = evalFloat(args, i);
/*     */           
/* 376 */           if (f3 == f4)
/*     */           {
/* 378 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 382 */         return false;
/*     */     } 
/*     */     
/* 385 */     Config.warn("Unknown function type: " + this);
/* 386 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean evalBool(IExpression[] exprs, int index) {
/* 392 */     IExpressionBool iexpressionbool = (IExpressionBool)exprs[index];
/* 393 */     boolean flag = iexpressionbool.eval();
/* 394 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] evalFloatArray(IExpression[] args) {
/* 399 */     switch (this) {
/*     */       
/*     */       case VEC2:
/* 402 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1) };
/*     */       case VEC3:
/* 404 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2) };
/*     */       case VEC4:
/* 406 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2), evalFloat(args, 3) };
/*     */     } 
/* 408 */     Config.warn("Unknown function type: " + this);
/* 409 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FunctionType parse(String str) {
/* 415 */     for (FunctionType functiontype : VALUES) {
/* 416 */       if (functiontype.name.equals(str))
/*     */       {
/* 418 */         return functiontype;
/*     */       }
/*     */     } 
/*     */     
/* 422 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\FunctionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */