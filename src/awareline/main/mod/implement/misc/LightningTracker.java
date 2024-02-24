/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*    */ 
/*    */ public class LightningTracker extends Module {
/*    */   public LightningTracker() {
/* 13 */     super("LightningTracker", ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacketReceive(EventPacketReceive e) {
/* 18 */     if (e.getPacket() instanceof S2CPacketSpawnGlobalEntity) {
/* 19 */       S2CPacketSpawnGlobalEntity packetIn = (S2CPacketSpawnGlobalEntity)e.getPacket();
/* 20 */       if (packetIn.func_149053_g() == 1) {
/* 21 */         int x = packetIn.func_149051_d() / 32;
/* 22 */         int y = packetIn.func_149050_e() / 32;
/* 23 */         int z = packetIn.func_149049_f() / 32;
/* 24 */         Helper.sendMessageWithoutPrefix("[Lightningtracker] X:" + x + " Y:" + y + " Z:" + z);
/* 25 */         ClientNotification.sendClientMessage("Lightningtracker", "X:" + x + " Y:" + y + " Z:" + z, 3500L, ClientNotification.Type.WARNING);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\LightningTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */