/*    */ package awareline.main.mod.implement.move;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class NoJumpDelay extends Module {
/* 10 */   private final Numbers<Double> jumpDelay = new Numbers("JumpDelay", 
/* 11 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/*    */   public static NoJumpDelay getInstance;
/*    */   
/*    */   public NoJumpDelay() {
/* 15 */     super("NoJumpDelay", ModuleType.Movement);
/* 16 */     addSettings(new Value[] { (Value)this.jumpDelay });
/* 17 */     getInstance = this;
/* 18 */     setEnabledByConvention(false);
/*    */   }
/*    */   
/*    */   public int getJumpDelay() {
/* 22 */     return getInstance.isEnabled() ? ((Double)this.jumpDelay.get()).intValue() : 10;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(EventTick e) {
/* 27 */     setSuffix(Integer.valueOf(((Double)this.jumpDelay.get()).intValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\NoJumpDelay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */