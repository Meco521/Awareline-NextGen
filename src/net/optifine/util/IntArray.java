/*    */ package net.optifine.util;
/*    */ 
/*    */ public class IntArray
/*    */ {
/*  5 */   private int[] array = null;
/*  6 */   private int position = 0;
/*  7 */   private int limit = 0;
/*    */ 
/*    */   
/*    */   public IntArray(int size) {
/* 11 */     this.array = new int[size];
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(int x) {
/* 16 */     this.array[this.position] = x;
/* 17 */     this.position++;
/*    */     
/* 19 */     if (this.limit < this.position)
/*    */     {
/* 21 */       this.limit = this.position;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(int pos, int x) {
/* 27 */     this.array[pos] = x;
/*    */     
/* 29 */     if (this.limit < pos)
/*    */     {
/* 31 */       this.limit = pos;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void position(int pos) {
/* 37 */     this.position = pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(int[] ints) {
/* 42 */     int i = ints.length;
/*    */     
/* 44 */     for (int j = 0; j < i; j++) {
/*    */       
/* 46 */       this.array[this.position] = ints[j];
/* 47 */       this.position++;
/*    */     } 
/*    */     
/* 50 */     if (this.limit < this.position)
/*    */     {
/* 52 */       this.limit = this.position;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(int pos) {
/* 58 */     return this.array[pos];
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getArray() {
/* 63 */     return this.array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 68 */     this.position = 0;
/* 69 */     this.limit = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLimit() {
/* 74 */     return this.limit;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPosition() {
/* 79 */     return this.position;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\IntArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */