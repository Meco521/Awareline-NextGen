/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.optifine.Log;
/*     */ 
/*     */ 
/*     */ public class ReflectorMethod
/*     */   implements IResolvable
/*     */ {
/*     */   private final ReflectorClass reflectorClass;
/*     */   private final String targetMethodName;
/*     */   private final Class[] targetMethodParameterTypes;
/*     */   private boolean checked;
/*     */   private Method targetMethod;
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName) {
/*  19 */     this(reflectorClass, targetMethodName, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes) {
/*  24 */     this.checked = false;
/*  25 */     this.targetMethod = null;
/*  26 */     this.reflectorClass = reflectorClass;
/*  27 */     this.targetMethodName = targetMethodName;
/*  28 */     this.targetMethodParameterTypes = targetMethodParameterTypes;
/*  29 */     ReflectorResolver.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Method getTargetMethod() {
/*  34 */     if (this.checked)
/*     */     {
/*  36 */       return this.targetMethod;
/*     */     }
/*     */ 
/*     */     
/*  40 */     this.checked = true;
/*  41 */     Class oclass = this.reflectorClass.getTargetClass();
/*     */     
/*  43 */     if (oclass == null)
/*     */     {
/*  45 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  51 */       if (this.targetMethodParameterTypes == null) {
/*     */         
/*  53 */         Method[] amethod = getMethods(oclass, this.targetMethodName);
/*     */         
/*  55 */         if (amethod.length <= 0) {
/*     */           
/*  57 */           Log.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  58 */           return null;
/*     */         } 
/*     */         
/*  61 */         if (amethod.length > 1) {
/*     */           
/*  63 */           Log.warn("(Reflector) More than one method found: " + oclass.getName() + "." + this.targetMethodName);
/*     */           
/*  65 */           for (int i = 0; i < amethod.length; i++) {
/*     */             
/*  67 */             Method method = amethod[i];
/*  68 */             Log.warn("(Reflector)  - " + method);
/*     */           } 
/*     */           
/*  71 */           return null;
/*     */         } 
/*     */         
/*  74 */         this.targetMethod = amethod[0];
/*     */       }
/*     */       else {
/*     */         
/*  78 */         this.targetMethod = getMethod(oclass, this.targetMethodName, this.targetMethodParameterTypes);
/*     */       } 
/*     */       
/*  81 */       if (this.targetMethod == null) {
/*     */         
/*  83 */         Log.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  84 */         return null;
/*     */       } 
/*     */ 
/*     */       
/*  88 */       this.targetMethod.setAccessible(true);
/*  89 */       return this.targetMethod;
/*     */     
/*     */     }
/*  92 */     catch (Throwable throwable) {
/*     */       
/*  94 */       throwable.printStackTrace();
/*  95 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 103 */     return this.checked ? ((this.targetMethod != null)) : ((getTargetMethod() != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getReturnType() {
/* 108 */     Method method = getTargetMethod();
/* 109 */     return (method == null) ? null : method.getReturnType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 114 */     this.checked = true;
/* 115 */     this.targetMethod = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object call(Object... params) {
/* 120 */     return Reflector.call(this, params);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean callBoolean(Object... params) {
/* 125 */     return Reflector.callBoolean(this, params);
/*     */   }
/*     */ 
/*     */   
/*     */   public int callInt(Object... params) {
/* 130 */     return Reflector.callInt(this, params);
/*     */   }
/*     */ 
/*     */   
/*     */   public double callDouble(Object... params) {
/* 135 */     return Reflector.callDouble(this, params);
/*     */   }
/*     */ 
/*     */   
/*     */   public String callString(Object... params) {
/* 140 */     return Reflector.callString(this, params);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object call(Object param) {
/* 145 */     return Reflector.call(this, new Object[] { param });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean callBoolean(Object param) {
/* 150 */     return Reflector.callBoolean(this, new Object[] { param });
/*     */   }
/*     */ 
/*     */   
/*     */   public int callInt(Object param) {
/* 155 */     return Reflector.callInt(this, new Object[] { param });
/*     */   }
/*     */ 
/*     */   
/*     */   public double callDouble(Object param) {
/* 160 */     return Reflector.callDouble(this, new Object[] { param });
/*     */   }
/*     */ 
/*     */   
/*     */   public void callVoid(Object... params) {
/* 165 */     Reflector.callVoid(this, params);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class cls, String methodName, Class[] paramTypes) {
/* 170 */     Method[] amethod = cls.getDeclaredMethods();
/*     */     
/* 172 */     for (int i = 0; i < amethod.length; i++) {
/*     */       
/* 174 */       Method method = amethod[i];
/*     */       
/* 176 */       if (method.getName().equals(methodName)) {
/*     */         
/* 178 */         Class[] aclass = method.getParameterTypes();
/*     */         
/* 180 */         if (Reflector.matchesTypes(paramTypes, aclass))
/*     */         {
/* 182 */           return method;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method[] getMethods(Class cls, String methodName) {
/* 192 */     List<Method> list = new ArrayList();
/* 193 */     Method[] amethod = cls.getDeclaredMethods();
/*     */     
/* 195 */     for (int i = 0; i < amethod.length; i++) {
/*     */       
/* 197 */       Method method = amethod[i];
/*     */       
/* 199 */       if (method.getName().equals(methodName))
/*     */       {
/* 201 */         list.add(method);
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return list.<Method>toArray(new Method[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resolve() {
/* 210 */     getTargetMethod();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\ReflectorMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */