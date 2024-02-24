/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class FieldLocatorFixed
/*    */   implements IFieldLocator
/*    */ {
/*    */   private final Field field;
/*    */   
/*    */   public FieldLocatorFixed(Field field) {
/* 11 */     this.field = field;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 16 */     return this.field;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\FieldLocatorFixed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */