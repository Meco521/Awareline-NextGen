/*    */ package awareline.main.mod.implement.script;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class AutoJump extends Module {
/*    */   public AutoJump() {
/* 10 */     super("AutoJump.js", ModuleType.Script);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 15 */     if (isMoving() && mc.thePlayer.onGround)
/* 16 */       mc.thePlayer.jump(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\script\AutoJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */