/*    */ package awareline.main.mod.values;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class Numbers<T extends Number>
/*    */   extends Value<T> {
/*    */   public final T min;
/*    */   public final T max;
/*    */   
/*    */   public boolean isInteger() {
/* 11 */     return this.integer;
/*    */   }
/*    */   public T inc; private final boolean integer; public float smoothAnim;
/*    */   
/*    */   public Numbers(String name, T value, T min, T max, T inc) {
/* 16 */     super(name, value, () -> Boolean.valueOf(true), () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/* 17 */     this.min = min;
/* 18 */     this.max = max;
/* 19 */     this.inc = inc;
/* 20 */     this.integer = false;
/*    */   }
/*    */   
/*    */   public Numbers(String name, T value, T min, T max, T inc, Supplier<Boolean> visitable) {
/* 24 */     super(name, value, visitable, () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/* 25 */     this.min = min;
/* 26 */     this.max = max;
/* 27 */     this.inc = inc;
/* 28 */     this.integer = false;
/*    */   }
/*    */   
/*    */   public Numbers(String name, T value, T min, T max, T inc, Supplier<Boolean> visitable, Supplier<Boolean> visitable2) {
/* 32 */     super(name, value, visitable, visitable2, () -> Boolean.valueOf(true));
/* 33 */     this.min = min;
/* 34 */     this.max = max;
/* 35 */     this.inc = inc;
/* 36 */     this.integer = false;
/*    */   }
/*    */   
/*    */   public Numbers(String name, T value, T min, T max, T inc, Supplier<Boolean> visitable, Supplier<Boolean> visitable2, Supplier<Boolean> visitable3) {
/* 40 */     super(name, value, visitable, visitable2, visitable3);
/* 41 */     this.min = min;
/* 42 */     this.max = max;
/* 43 */     this.inc = inc;
/* 44 */     this.integer = false;
/*    */   }
/*    */   
/*    */   public T getMinimum() {
/* 48 */     return this.min;
/*    */   }
/*    */   
/*    */   public T getMaximum() {
/* 52 */     return this.max;
/*    */   }
/*    */   
/*    */   public void setIncrement(T inc) {
/* 56 */     this.inc = inc;
/*    */   }
/*    */   
/*    */   public T getIncrement() {
/* 60 */     return this.inc;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\values\Numbers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */