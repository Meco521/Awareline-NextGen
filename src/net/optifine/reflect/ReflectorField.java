/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class ReflectorField
/*    */   implements IResolvable
/*    */ {
/*    */   private IFieldLocator fieldLocator;
/*    */   private boolean checked;
/*    */   private Field targetField;
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, String targetFieldName) {
/* 13 */     this(new FieldLocatorName(reflectorClass, targetFieldName));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType) {
/* 18 */     this(reflectorClass, targetFieldType, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/* 23 */     this(new FieldLocatorType(reflectorClass, targetFieldType, targetFieldIndex));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(Field field) {
/* 28 */     this(new FieldLocatorFixed(field));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(IFieldLocator fieldLocator) {
/* 33 */     this.fieldLocator = null;
/* 34 */     this.checked = false;
/* 35 */     this.targetField = null;
/* 36 */     this.fieldLocator = fieldLocator;
/* 37 */     ReflectorResolver.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getTargetField() {
/* 42 */     if (this.checked)
/*    */     {
/* 44 */       return this.targetField;
/*    */     }
/*    */ 
/*    */     
/* 48 */     this.checked = true;
/* 49 */     this.targetField = this.fieldLocator.getField();
/*    */     
/* 51 */     if (this.targetField != null)
/*    */     {
/* 53 */       this.targetField.setAccessible(true);
/*    */     }
/*    */     
/* 56 */     return this.targetField;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 62 */     return Reflector.getFieldValue((Object)null, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 67 */     Reflector.setFieldValue(null, this, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object obj, Object value) {
/* 72 */     Reflector.setFieldValue(obj, this, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 77 */     return (getTargetField() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void resolve() {
/* 82 */     Field field = getTargetField();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\ReflectorField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */