/*    */ package awareline.main.mod.implement.move;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class AirLadder extends Module {
/* 10 */   private final Option<Boolean> fast = new Option("Fast", Boolean.valueOf(false));
/*    */   
/*    */   public AirLadder() {
/* 13 */     super("AirLadder", ModuleType.Movement);
/* 14 */     addSettings(new Value[] { (Value)this.fast });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 19 */     if (mc.thePlayer.isOnLadder() && mc.gameSettings.keyBindJump.isKeyDown())
/* 20 */       mc.thePlayer.motionY = ((Boolean)this.fast.get()).booleanValue() ? 0.20000000298023224D : 0.10999999940395355D; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\AirLadder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */