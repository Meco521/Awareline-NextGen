/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class MCF extends Module {
/*    */   private boolean down;
/*    */   
/*    */   public MCF() {
/* 16 */     super("MiddleClickFriends", new String[] { "mcf" }, ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onClick(EventPreUpdate e) {
/* 21 */     if (Mouse.isButtonDown(2) && !this.down) {
/* 22 */       if (mc.objectMouseOver.entityHit != null) {
/* 23 */         EntityPlayer player = (EntityPlayer)mc.objectMouseOver.entityHit;
/* 24 */         String playerName = player.getName();
/* 25 */         if (!Client.instance.friendManager.isFriend(playerName)) {
/* 26 */           mc.thePlayer.sendChatMessage("-f add " + playerName);
/* 27 */           ClientNotification.sendClientMessage("MCF", "Your are add a friend " + playerName, 5000L, ClientNotification.Type.WARNING);
/*    */         } else {
/* 29 */           mc.thePlayer.sendChatMessage("-f del " + playerName);
/* 30 */           ClientNotification.sendClientMessage("MCF", "Your are remove a friend " + playerName, 5000L, ClientNotification.Type.WARNING);
/*    */         } 
/*    */       } 
/* 33 */       this.down = true;
/*    */     } 
/* 35 */     if (!Mouse.isButtonDown(2))
/* 36 */       this.down = false; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\MCF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */