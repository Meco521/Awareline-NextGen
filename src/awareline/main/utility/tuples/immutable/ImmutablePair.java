/*    */ package awareline.main.utility.tuples.immutable;
/*    */ 
/*    */ import awareline.main.utility.tuples.Pair;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.BiFunction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ImmutablePair<A, B>
/*    */   extends Pair<A, B>
/*    */ {
/*    */   private final A a;
/*    */   private final B b;
/*    */   
/*    */   ImmutablePair(A a, B b) {
/* 18 */     this.a = a;
/* 19 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static <A, B> ImmutablePair<A, B> of(A a, B b) {
/* 23 */     return new ImmutablePair<>(a, b);
/*    */   }
/*    */   
/*    */   public Pair<A, A> pairOfFirst() {
/* 27 */     return Pair.of(this.a);
/*    */   }
/*    */   
/*    */   public Pair<B, B> pairOfSecond() {
/* 31 */     return Pair.of(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public A getFirst() {
/* 36 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public B getSecond() {
/* 41 */     return this.b;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <R> R apply(BiFunction<? super A, ? super B, ? extends R> func) {
/* 47 */     return func.apply(this.a, this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void use(BiConsumer<? super A, ? super B> func) {
/* 52 */     func.accept(this.a, this.b);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\immutable\ImmutablePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */