/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class TeamFucker
/*    */   extends Module {
/*    */   boolean down1;
/*    */   
/*    */   public TeamFucker() {
/* 13 */     super("TeamFucker", ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 18 */     if (!this.down1 && mc.gameSettings.keyBindForward.pressed) {
/* 19 */       mc.thePlayer.sendChatMessage("鎴戝湪鍓嶈繘");
/* 20 */       this.down1 = true;
/*    */     } 
/* 22 */     if (!mc.gameSettings.keyBindForward.pressed)
/* 23 */       this.down1 = false; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\TeamFucker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */