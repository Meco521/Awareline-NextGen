/*    */ package awareline.main.utility.tuples.immutable;
/*    */ 
/*    */ import awareline.main.utility.tuples.Unit;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ImmutableUnit<A>
/*    */   extends Unit<A>
/*    */ {
/*    */   private final A a;
/*    */   
/*    */   ImmutableUnit(A a) {
/* 17 */     this.a = a;
/*    */   }
/*    */   
/*    */   public static <A> ImmutableUnit<A> of(A a) {
/* 21 */     return new ImmutableUnit<>(a);
/*    */   }
/*    */ 
/*    */   
/*    */   public A get() {
/* 26 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public <R> R apply(Function<? super A, ? extends R> func) {
/* 31 */     return func.apply(this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public void use(Consumer<? super A> func) {
/* 36 */     func.accept(this.a);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\immutable\ImmutableUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */