/*    */ package awareline.main.utility.tuples.mutable;
/*    */ 
/*    */ import awareline.main.utility.tuples.Unit;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.UnaryOperator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MutableUnit<A>
/*    */   extends Unit<A>
/*    */ {
/*    */   private A a;
/*    */   
/*    */   MutableUnit(A a) {
/* 18 */     this.a = a;
/*    */   }
/*    */   
/*    */   public static <A> MutableUnit<A> of(A a) {
/* 22 */     return new MutableUnit<>(a);
/*    */   }
/*    */ 
/*    */   
/*    */   public A get() {
/* 27 */     return this.a;
/*    */   }
/*    */   
/*    */   public void set(A a) {
/* 31 */     this.a = a;
/*    */   }
/*    */ 
/*    */   
/*    */   public <R> R apply(Function<? super A, ? extends R> func) {
/* 36 */     return func.apply(this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public void use(Consumer<? super A> func) {
/* 41 */     func.accept(this.a);
/*    */   }
/*    */   
/*    */   public void compute(UnaryOperator<A> mapper) {
/* 45 */     this.a = mapper.apply(this.a);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\tuples\mutable\MutableUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */