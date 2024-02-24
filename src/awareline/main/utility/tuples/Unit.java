/*    */ package awareline.main.utility.tuples;
/*    */ 
/*    */ import awareline.main.utility.tuples.immutable.ImmutableUnit;
/*    */ import java.io.Serializable;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Unit<A>
/*    */   implements Serializable
/*    */ {
/*    */   public static <A> Unit<A> of(A a) {
/* 18 */     return (Unit<A>)ImmutableUnit.of(a);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract A get();
/*    */   
/*    */   public abstract <R> R apply(Function<? super A, ? extends R> paramFunction);
/*    */   
/*    */   public abstract void use(Consumer<? super A> paramConsumer);
/*    */   
/*    */   public int hashCode() {
/* 29 */     return Objects.hash(new Object[] { get() });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object that) {
/* 34 */     if (this == that) return true; 
/* 35 */     if (that instanceof Unit) {
/* 36 */       Unit<?> other = (Unit)that;
/* 37 */       return Objects.equals(get(), other.get());
/*    */     } 
/* 39 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\Unit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */