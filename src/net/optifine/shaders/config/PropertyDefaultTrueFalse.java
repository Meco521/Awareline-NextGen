/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import net.optifine.Lang;
/*    */ 
/*    */ public class PropertyDefaultTrueFalse
/*    */   extends Property {
/*  7 */   public static final String[] PROPERTY_VALUES = new String[] { "default", "true", "false" };
/*  8 */   public static final String[] USER_VALUES = new String[] { "Default", "ON", "OFF" };
/*    */ 
/*    */   
/*    */   public PropertyDefaultTrueFalse(String propertyName, String userName, int defaultValue) {
/* 12 */     super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUserValue() {
/* 17 */     return isDefault() ? Lang.getDefault() : (isTrue() ? Lang.getOn() : (isFalse() ? Lang.getOff() : super.getUserValue()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDefault() {
/* 22 */     return (getValue() == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTrue() {
/* 27 */     return (getValue() == 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFalse() {
/* 32 */     return (getValue() == 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\PropertyDefaultTrueFalse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */