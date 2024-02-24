/*    */ package awareline.main.mod.implement.player;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class Respawn
/*    */   extends Module {
/*    */   public Respawn() {
/* 11 */     super("Respawn", ModuleType.Player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(MotionUpdateEvent event) {
/* 16 */     if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 17 */       this; if (mc.currentScreen instanceof net.minecraft.client.gui.GuiGameOver) {
/* 18 */         this; mc.thePlayer.respawnPlayer();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\Respawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */