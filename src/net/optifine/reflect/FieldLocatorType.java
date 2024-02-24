/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import net.optifine.Log;
/*    */ 
/*    */ 
/*    */ public class FieldLocatorType
/*    */   implements IFieldLocator
/*    */ {
/*    */   private ReflectorClass reflectorClass;
/*    */   private Class targetFieldType;
/*    */   private final int targetFieldIndex;
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType) {
/* 15 */     this(reflectorClass, targetFieldType, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/* 20 */     this.reflectorClass = null;
/* 21 */     this.targetFieldType = null;
/* 22 */     this.reflectorClass = reflectorClass;
/* 23 */     this.targetFieldType = targetFieldType;
/* 24 */     this.targetFieldIndex = targetFieldIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 29 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 31 */     if (oclass == null)
/*    */     {
/* 33 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 39 */       Field[] afield = oclass.getDeclaredFields();
/* 40 */       int i = 0;
/*    */       
/* 42 */       for (int j = 0; j < afield.length; j++) {
/*    */         
/* 44 */         Field field = afield[j];
/*    */         
/* 46 */         if (field.getType() == this.targetFieldType) {
/*    */           
/* 48 */           if (i == this.targetFieldIndex) {
/*    */             
/* 50 */             field.setAccessible(true);
/* 51 */             return field;
/*    */           } 
/*    */           
/* 54 */           i++;
/*    */         } 
/*    */       } 
/*    */       
/* 58 */       Log.log("(Reflector) Field not present: " + oclass.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
/* 59 */       return null;
/*    */     }
/* 61 */     catch (SecurityException securityexception) {
/*    */       
/* 63 */       securityexception.printStackTrace();
/* 64 */       return null;
/*    */     }
/* 66 */     catch (Throwable throwable) {
/*    */       
/* 68 */       throwable.printStackTrace();
/* 69 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\FieldLocatorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */