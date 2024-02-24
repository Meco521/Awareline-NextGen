/*    */ package awareline.main.utility.tuples;
/*    */ 
/*    */ import awareline.main.utility.tuples.immutable.ImmutablePair;
/*    */ import java.io.Serializable;
/*    */ import java.util.Objects;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.BiFunction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Pair<A, B>
/*    */   implements Serializable
/*    */ {
/*    */   public static <A, B> Pair<A, B> of(A a, B b) {
/* 18 */     return (Pair<A, B>)ImmutablePair.of(a, b);
/*    */   }
/*    */   
/*    */   public static <A> Pair<A, A> of(A a) {
/* 22 */     return (Pair<A, A>)ImmutablePair.of(a, a);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract A getFirst();
/*    */   
/*    */   public abstract B getSecond();
/*    */   
/*    */   public abstract <R> R apply(BiFunction<? super A, ? super B, ? extends R> paramBiFunction);
/*    */   
/*    */   public abstract void use(BiConsumer<? super A, ? super B> paramBiConsumer);
/*    */   
/*    */   public int hashCode() {
/* 35 */     return Objects.hash(new Object[] { getFirst(), getSecond() });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object that) {
/* 40 */     if (this == that) return true; 
/* 41 */     if (that instanceof Pair) {
/* 42 */       Pair<?, ?> other = (Pair<?, ?>)that;
/* 43 */       return (Objects.equals(getFirst(), other.getFirst()) && Objects.equals(getSecond(), other.getSecond()));
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */