/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class AirJump extends Module {
/*    */   public AirJump() {
/* 10 */     super("AirJump", ModuleType.Movement);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 15 */     this; mc.thePlayer.onGround = true;
/* 16 */     this; mc.thePlayer.isAirBorne = false;
/* 17 */     this; mc.thePlayer.fallDistance = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\AirJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */