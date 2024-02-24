/*    */ package net.optifine.util;
/*    */ 
/*    */ 
/*    */ public class CounterInt
/*    */ {
/*    */   private final int startValue;
/*    */   private int value;
/*    */   
/*    */   public CounterInt(int startValue) {
/* 10 */     this.startValue = startValue;
/* 11 */     this.value = startValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized int nextValue() {
/* 16 */     int i = this.value++;
/* 17 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void reset() {
/* 22 */     this.value = this.startValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 27 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\CounterInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */