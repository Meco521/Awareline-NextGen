/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TupleIntJsonSerializable
/*    */ {
/*    */   private int integerValue;
/*    */   private IJsonSerializable jsonSerializableValue;
/*    */   
/*    */   public int getIntegerValue() {
/* 13 */     return this.integerValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setIntegerValue(int integerValueIn) {
/* 21 */     this.integerValue = integerValueIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends IJsonSerializable> T getJsonSerializableValue() {
/* 26 */     return (T)this.jsonSerializableValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setJsonSerializableValue(IJsonSerializable jsonSerializableValueIn) {
/* 34 */     this.jsonSerializableValue = jsonSerializableValueIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\TupleIntJsonSerializable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */