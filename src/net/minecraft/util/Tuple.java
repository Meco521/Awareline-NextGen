/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public class Tuple<A, B>
/*    */ {
/*    */   private final A a;
/*    */   private final B b;
/*    */   
/*    */   public Tuple(A aIn, B bIn) {
/* 10 */     this.a = aIn;
/* 11 */     this.b = bIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public A getFirst() {
/* 19 */     return this.a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public B getSecond() {
/* 27 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\Tuple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */