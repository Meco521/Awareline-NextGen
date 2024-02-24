/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class RangeListInt
/*    */ {
/*  7 */   private RangeInt[] ranges = new RangeInt[0];
/*    */ 
/*    */ 
/*    */   
/*    */   public RangeListInt() {}
/*    */ 
/*    */   
/*    */   public RangeListInt(RangeInt ri) {
/* 15 */     addRange(ri);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRange(RangeInt ri) {
/* 20 */     this.ranges = (RangeInt[])Config.addObjectToArray((Object[])this.ranges, ri);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInRange(int val) {
/* 25 */     for (int i = 0; i < this.ranges.length; i++) {
/*    */       
/* 27 */       RangeInt rangeint = this.ranges[i];
/*    */       
/* 29 */       if (rangeint.isInRange(val))
/*    */       {
/* 31 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCountRanges() {
/* 40 */     return this.ranges.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public RangeInt getRange(int i) {
/* 45 */     return this.ranges[i];
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     StringBuffer stringbuffer = new StringBuffer();
/* 51 */     stringbuffer.append("[");
/*    */     
/* 53 */     for (int i = 0; i < this.ranges.length; i++) {
/*    */       
/* 55 */       RangeInt rangeint = this.ranges[i];
/*    */       
/* 57 */       if (i > 0)
/*    */       {
/* 59 */         stringbuffer.append(", ");
/*    */       }
/*    */       
/* 62 */       stringbuffer.append(rangeint.toString());
/*    */     } 
/*    */     
/* 65 */     stringbuffer.append("]");
/* 66 */     return stringbuffer.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\RangeListInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */