/*    */ package awareline.main.utility.tuples.immutable;
/*    */ 
/*    */ import awareline.main.utility.tuples.Triplet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ImmutableTriplet<A, B, C>
/*    */   extends Triplet<A, B, C>
/*    */ {
/*    */   private final A a;
/*    */   private final B b;
/*    */   private final C c;
/*    */   
/*    */   ImmutableTriplet(A a, B b, C c) {
/* 16 */     this.a = a;
/* 17 */     this.b = b;
/* 18 */     this.c = c;
/*    */   }
/*    */   
/*    */   public static <A, B, C> ImmutableTriplet<A, B, C> of(A a, B b, C c) {
/* 22 */     return new ImmutableTriplet<>(a, b, c);
/*    */   }
/*    */   
/*    */   public Triplet<A, A, A> pairOfFirst() {
/* 26 */     return Triplet.of(this.a);
/*    */   }
/*    */   
/*    */   public Triplet<B, B, B> pairOfSecond() {
/* 30 */     return Triplet.of(this.b);
/*    */   }
/*    */   
/*    */   public Triplet<C, C, C> pairOfThird() {
/* 34 */     return Triplet.of(this.c);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public A getFirst() {
/* 40 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public B getSecond() {
/* 45 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public C getThird() {
/* 50 */     return this.c;
/*    */   }
/*    */ 
/*    */   
/*    */   public <R> R apply(Triplet.TriFunction<? super A, ? super B, ? super C, ? extends R> func) {
/* 55 */     return (R)func.apply(this.a, this.b, this.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public void use(Triplet.TriConsumer<? super A, ? super B, ? super C> func) {
/* 60 */     func.accept(this.a, this.b, this.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\immutable\ImmutableTriplet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */