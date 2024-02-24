/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import net.optifine.Log;
/*    */ import net.optifine.util.ArrayUtils;
/*    */ 
/*    */ public class ReflectorConstructor
/*    */   implements IResolvable
/*    */ {
/* 10 */   private ReflectorClass reflectorClass = null;
/* 11 */   private Class[] parameterTypes = null;
/*    */   private boolean checked = false;
/* 13 */   private Constructor targetConstructor = null;
/*    */ 
/*    */   
/*    */   public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
/* 17 */     this.reflectorClass = reflectorClass;
/* 18 */     this.parameterTypes = parameterTypes;
/* 19 */     ReflectorResolver.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Constructor getTargetConstructor() {
/* 24 */     if (this.checked)
/*    */     {
/* 26 */       return this.targetConstructor;
/*    */     }
/*    */ 
/*    */     
/* 30 */     this.checked = true;
/* 31 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 33 */     if (oclass == null)
/*    */     {
/* 35 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 41 */       this.targetConstructor = findConstructor(oclass, this.parameterTypes);
/*    */       
/* 43 */       if (this.targetConstructor == null)
/*    */       {
/* 45 */         Log.dbg("(Reflector) Constructor not present: " + oclass.getName() + ", params: " + ArrayUtils.arrayToString((Object[])this.parameterTypes));
/*    */       }
/*    */       
/* 48 */       if (this.targetConstructor != null)
/*    */       {
/* 50 */         this.targetConstructor.setAccessible(true);
/*    */       }
/*    */     }
/* 53 */     catch (Throwable throwable) {
/*    */       
/* 55 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 58 */     return this.targetConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Constructor findConstructor(Class cls, Class[] paramTypes) {
/* 65 */     Constructor[] aconstructor = (Constructor[])cls.getDeclaredConstructors();
/*    */     
/* 67 */     for (int i = 0; i < aconstructor.length; i++) {
/*    */       
/* 69 */       Constructor constructor = aconstructor[i];
/* 70 */       Class[] aclass = constructor.getParameterTypes();
/*    */       
/* 72 */       if (Reflector.matchesTypes(paramTypes, aclass))
/*    */       {
/* 74 */         return constructor;
/*    */       }
/*    */     } 
/*    */     
/* 78 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 83 */     return this.checked ? ((this.targetConstructor != null)) : ((getTargetConstructor() != null));
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 88 */     this.checked = true;
/* 89 */     this.targetConstructor = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object newInstance(Object... params) {
/* 94 */     return Reflector.newInstance(this, params);
/*    */   }
/*    */ 
/*    */   
/*    */   public void resolve() {
/* 99 */     Constructor constructor = getTargetConstructor();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\ReflectorConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */