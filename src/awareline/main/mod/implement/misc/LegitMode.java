/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.visual.HUD;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ 
/*    */ public class LegitMode
/*    */   extends Module {
/*    */   public LegitMode() {
/* 14 */     super("LegitMode", ModuleType.Misc);
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(4)
/*    */   public void onUpdate(EventPreUpdate e) {
/* 20 */     setEnabled(false);
/* 21 */     if (!isEnabled(HUD.class)) {
/* 22 */       setEnabled(true, new Class[] { HUD.class });
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 28 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/* 29 */       if (!m.isEnabled()) {
/*    */         continue;
/*    */       }
/* 32 */       m.setEnabled(false);
/*    */     } 
/* 34 */     ClientNotification.sendClientMessage(getHUDName(), "All module has been disabled", 3500L, ClientNotification.Type.WARNING);
/*    */     
/* 36 */     super.onEnable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\LegitMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */