/*    */ package awareline.main.utility.tuples.mutable;
/*    */ 
/*    */ import awareline.main.utility.tuples.Triplet;
/*    */ import java.util.function.UnaryOperator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MutableTriplet<A, B, C>
/*    */   extends Triplet<A, B, C>
/*    */ {
/*    */   private A a;
/*    */   private B b;
/*    */   private C c;
/*    */   
/*    */   MutableTriplet(A a, B b, C c) {
/* 18 */     this.a = a;
/* 19 */     this.b = b;
/* 20 */     this.c = c;
/*    */   }
/*    */   
/*    */   public static <A, B, C> MutableTriplet<A, B, C> of(A a, B b, C c) {
/* 24 */     return new MutableTriplet<>(a, b, c);
/*    */   }
/*    */   
/*    */   public static <A> MutableTriplet<A, A, A> of(A a) {
/* 28 */     return new MutableTriplet<>(a, a, a);
/*    */   }
/*    */   
/*    */   public MutableTriplet<A, A, A> pairOfFirst() {
/* 32 */     return of(this.a);
/*    */   }
/*    */   
/*    */   public MutableTriplet<B, B, B> pairOfSecond() {
/* 36 */     return of(this.b);
/*    */   }
/*    */   
/*    */   public MutableTriplet<C, C, C> pairOfThird() {
/* 40 */     return of(this.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public A getFirst() {
/* 45 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public B getSecond() {
/* 50 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public C getThird() {
/* 55 */     return this.c;
/*    */   }
/*    */   
/*    */   public void setFirst(A a) {
/* 59 */     this.a = a;
/*    */   }
/*    */   
/*    */   public void setSecond(B b) {
/* 63 */     this.b = b;
/*    */   }
/*    */   
/*    */   public void setThird(C c) {
/* 67 */     this.c = c;
/*    */   }
/*    */ 
/*    */   
/*    */   public <R> R apply(Triplet.TriFunction<? super A, ? super B, ? super C, ? extends R> func) {
/* 72 */     return (R)func.apply(this.a, this.b, this.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public void use(Triplet.TriConsumer<? super A, ? super B, ? super C> func) {
/* 77 */     func.accept(this.a, this.b, this.c);
/*    */   }
/*    */   
/*    */   public void computeFirst(UnaryOperator<A> operator) {
/* 81 */     this.a = operator.apply(this.a);
/*    */   }
/*    */   
/*    */   public void computeSecond(UnaryOperator<B> operator) {
/* 85 */     this.b = operator.apply(this.b);
/*    */   }
/*    */   
/*    */   public void computeThird(UnaryOperator<C> operator) {
/* 89 */     this.c = operator.apply(this.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\mutable\MutableTriplet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */