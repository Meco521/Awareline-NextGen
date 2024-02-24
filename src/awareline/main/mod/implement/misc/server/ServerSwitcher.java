/*    */ package awareline.main.mod.implement.misc.server;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientMainMenu;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import net.minecraft.client.gui.GuiMultiplayer;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class ServerSwitcher extends Module {
/*    */   public ServerSwitcher() {
/* 13 */     super("ServerSwitcher", ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 18 */     setEnabled(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 23 */     mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new ClientMainMenu()));
/* 24 */     ClientNotification.sendClientMessage(getHUDName(), "Enabled MultiPlayer", 4000L, ClientNotification.Type.INFO);
/* 25 */     super.onEnable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\server\ServerSwitcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */