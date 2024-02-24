/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import net.optifine.Log;
/*    */ 
/*    */ public class ReflectorClass
/*    */   implements IResolvable {
/*  7 */   private String targetClassName = null;
/*    */   private boolean checked = false;
/*  9 */   private Class targetClass = null;
/*    */ 
/*    */   
/*    */   public ReflectorClass(String targetClassName) {
/* 13 */     this.targetClassName = targetClassName;
/* 14 */     ReflectorResolver.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass(Class targetClass) {
/* 19 */     this.targetClass = targetClass;
/* 20 */     this.targetClassName = targetClass.getName();
/* 21 */     this.checked = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getTargetClass() {
/* 26 */     if (this.checked)
/*    */     {
/* 28 */       return this.targetClass;
/*    */     }
/*    */ 
/*    */     
/* 32 */     this.checked = true;
/*    */ 
/*    */     
/*    */     try {
/* 36 */       this.targetClass = Class.forName(this.targetClassName);
/*    */     }
/* 38 */     catch (ClassNotFoundException var2) {
/*    */       
/* 40 */       Log.log("(Reflector) Class not present: " + this.targetClassName);
/*    */     }
/* 42 */     catch (Throwable throwable) {
/*    */       
/* 44 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 47 */     return this.targetClass;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 53 */     return (getTargetClass() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTargetClassName() {
/* 58 */     return this.targetClassName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInstance(Object obj) {
/* 63 */     return (getTargetClass() == null) ? false : getTargetClass().isInstance(obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField makeField(String name) {
/* 68 */     return new ReflectorField(this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String name) {
/* 73 */     return new ReflectorMethod(this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String name, Class[] paramTypes) {
/* 78 */     return new ReflectorMethod(this, name, paramTypes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void resolve() {
/* 83 */     Class oclass = getTargetClass();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\ReflectorClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */