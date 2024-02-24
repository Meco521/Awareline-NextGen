/*    */ package awareline.main.utility.tuples;
/*    */ 
/*    */ import awareline.main.utility.tuples.immutable.ImmutableTriplet;
/*    */ import java.io.Serializable;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Triplet<A, B, C>
/*    */   implements Serializable
/*    */ {
/*    */   public static <A, B, C> Triplet<A, B, C> of(A a, B b, C c) {
/* 16 */     return (Triplet<A, B, C>)ImmutableTriplet.of(a, b, c);
/*    */   }
/*    */   
/*    */   public static <A> Triplet<A, A, A> of(A a) {
/* 20 */     return (Triplet<A, A, A>)ImmutableTriplet.of(a, a, a);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract A getFirst();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract B getSecond();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract C getThird();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract <R> R apply(TriFunction<? super A, ? super B, ? super C, ? extends R> paramTriFunction);
/*    */ 
/*    */   
/*    */   public abstract void use(TriConsumer<? super A, ? super B, ? super C> paramTriConsumer);
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 44 */     return Objects.hash(new Object[] { getFirst(), getSecond(), getThird() });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object that) {
/* 49 */     if (this == that) return true; 
/* 50 */     if (that instanceof Triplet) {
/* 51 */       Triplet<?, ?, ?> other = (Triplet<?, ?, ?>)that;
/* 52 */       return (Objects.equals(getFirst(), other.getFirst()) && Objects.equals(getSecond(), other.getSecond()) && Objects.equals(getThird(), other.getThird()));
/*    */     } 
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public static interface TriConsumer<T, U, V> {
/*    */     void accept(T param1T, U param1U, V param1V);
/*    */   }
/*    */   
/*    */   public static interface TriFunction<T, U, V, R> {
/*    */     R apply(T param1T, U param1U, V param1V);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\Triplet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */