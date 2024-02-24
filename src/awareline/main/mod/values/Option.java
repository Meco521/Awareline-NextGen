/*    */ package awareline.main.mod.values;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class Option<V> extends Value<V> {
/*  6 */   public float anim = 0.1F;
/*    */   public float enableAnim;
/*    */   
/*    */   public Option(String name, V enabled) {
/* 10 */     super(name, enabled, () -> Boolean.valueOf(true), () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/*    */   }
/*    */   public float disableAnim;
/*    */   public Option(String name, V enabled, Supplier<Boolean> visitable) {
/* 14 */     super(name, enabled, visitable, () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/*    */   }
/*    */   
/*    */   public Option(String name, V enabled, Supplier<Boolean> visitable, Supplier<Boolean> visitable2) {
/* 18 */     super(name, enabled, visitable, visitable2, () -> Boolean.valueOf(true));
/*    */   }
/*    */   
/*    */   public Option(String name, V enabled, Supplier<Boolean> visitable, Supplier<Boolean> visitable2, Supplier<Boolean> visitable3) {
/* 22 */     super(name, enabled, visitable, visitable2, visitable3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\values\Option.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */