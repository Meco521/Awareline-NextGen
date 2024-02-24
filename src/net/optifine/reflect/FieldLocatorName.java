/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import net.optifine.Log;
/*    */ 
/*    */ public class FieldLocatorName
/*    */   implements IFieldLocator
/*    */ {
/*  9 */   private ReflectorClass reflectorClass = null;
/* 10 */   private String targetFieldName = null;
/*    */ 
/*    */   
/*    */   public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
/* 14 */     this.reflectorClass = reflectorClass;
/* 15 */     this.targetFieldName = targetFieldName;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 20 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 22 */     if (oclass == null)
/*    */     {
/* 24 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 30 */       Field field = getDeclaredField(oclass, this.targetFieldName);
/* 31 */       field.setAccessible(true);
/* 32 */       return field;
/*    */     }
/* 34 */     catch (NoSuchFieldException var3) {
/*    */       
/* 36 */       Log.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
/* 37 */       return null;
/*    */     }
/* 39 */     catch (SecurityException securityexception) {
/*    */       
/* 41 */       securityexception.printStackTrace();
/* 42 */       return null;
/*    */     }
/* 44 */     catch (Throwable throwable) {
/*    */       
/* 46 */       throwable.printStackTrace();
/* 47 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getDeclaredField(Class<Object> cls, String name) throws NoSuchFieldException {
/* 54 */     Field[] afield = cls.getDeclaredFields();
/*    */     
/* 56 */     for (int i = 0; i < afield.length; i++) {
/*    */       
/* 58 */       Field field = afield[i];
/*    */       
/* 60 */       if (field.getName().equals(name))
/*    */       {
/* 62 */         return field;
/*    */       }
/*    */     } 
/*    */     
/* 66 */     if (cls == Object.class)
/*    */     {
/* 68 */       throw new NoSuchFieldException(name);
/*    */     }
/*    */ 
/*    */     
/* 72 */     return getDeclaredField(cls.getSuperclass(), name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\FieldLocatorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */