/*    */ package awareline.main.mod.implement.move;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.moveEvents.EventMove;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.MoveUtils;
/*    */ 
/*    */ public class FastStop extends Module {
/* 11 */   private final Option<Boolean> onlyGround = new Option("OnlyGround", Boolean.valueOf(false));
/*    */   
/*    */   public FastStop() {
/* 14 */     super("FastStop", ModuleType.Movement);
/* 15 */     addSettings(new Value[] { (Value)this.onlyGround });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onMove(EventMove e) {
/* 20 */     if (!isMoving() || !MoveUtils.INSTANCE.isMovingKeyBindingActive())
/* 21 */       if (((Boolean)this.onlyGround.get()).booleanValue()) {
/* 22 */         if (mc.thePlayer.onGround) {
/* 23 */           MoveUtils.INSTANCE.pause(e);
/*    */         }
/*    */       } else {
/* 26 */         MoveUtils.INSTANCE.pause(e);
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\FastStop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */