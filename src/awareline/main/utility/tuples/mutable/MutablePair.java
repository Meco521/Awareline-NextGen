/*    */ package awareline.main.utility.tuples.mutable;
/*    */ 
/*    */ import awareline.main.utility.tuples.Pair;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.UnaryOperator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MutablePair<A, B>
/*    */   extends Pair<A, B>
/*    */ {
/*    */   private A a;
/*    */   private B b;
/*    */   
/*    */   MutablePair(A a, B b) {
/* 18 */     this.a = a;
/* 19 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static <A, B> MutablePair<A, B> of(A a, B b) {
/* 23 */     return new MutablePair<>(a, b);
/*    */   }
/*    */   
/*    */   public static <A> MutablePair<A, A> of(A a) {
/* 27 */     return new MutablePair<>(a, a);
/*    */   }
/*    */   
/*    */   public MutablePair<A, A> pairOfFirst() {
/* 31 */     return of(this.a);
/*    */   }
/*    */   
/*    */   public MutablePair<B, B> pairOfSecond() {
/* 35 */     return of(this.b);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public A getFirst() {
/* 41 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public B getSecond() {
/* 46 */     return this.b;
/*    */   }
/*    */   
/*    */   public void setFirst(A a) {
/* 50 */     this.a = a;
/*    */   }
/*    */   
/*    */   public void setSecond(B b) {
/* 54 */     this.b = b;
/*    */   }
/*    */ 
/*    */   
/*    */   public <R> R apply(BiFunction<? super A, ? super B, ? extends R> func) {
/* 59 */     return func.apply(this.a, this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void use(BiConsumer<? super A, ? super B> func) {
/* 64 */     func.accept(this.a, this.b);
/*    */   }
/*    */   
/*    */   public void computeFirst(UnaryOperator<A> operator) {
/* 68 */     this.a = operator.apply(this.a);
/*    */   }
/*    */   
/*    */   public void computeSecond(UnaryOperator<B> operator) {
/* 72 */     this.b = operator.apply(this.b);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\mutable\MutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */