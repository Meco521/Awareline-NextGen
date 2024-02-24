/*    */ package net.optifine.config;
/*    */ 
/*    */ 
/*    */ public class RangeInt
/*    */ {
/*    */   private final int min;
/*    */   private final int max;
/*    */   
/*    */   public RangeInt(int min, int max) {
/* 10 */     this.min = Math.min(min, max);
/* 11 */     this.max = Math.max(min, max);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInRange(int val) {
/* 16 */     return (val < this.min) ? false : ((val <= this.max));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMin() {
/* 21 */     return this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMax() {
/* 26 */     return this.max;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return "min: " + this.min + ", max: " + this.max;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\RangeInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */