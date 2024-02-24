/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class PerfectHorseJump extends Module {
/*    */   public PerfectHorseJump() {
/* 10 */     super("PerfectHorseJump", ModuleType.Movement);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick e) {
/* 15 */     mc.thePlayer.horseJumpPowerCounter = 9;
/* 16 */     mc.thePlayer.horseJumpPower = 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\PerfectHorseJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */