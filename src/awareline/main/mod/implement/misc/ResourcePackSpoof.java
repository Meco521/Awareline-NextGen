/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import java.net.URI;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*    */ import net.minecraft.network.play.server.S48PacketResourcePackSend;
/*    */ 
/*    */ public class ResourcePackSpoof
/*    */   extends Module {
/*    */   public ResourcePackSpoof() {
/* 16 */     super("ResourcePackSpoof", ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacket(EventPacketReceive e) {
/* 21 */     if (e.getPacket() instanceof S48PacketResourcePackSend) {
/* 22 */       S48PacketResourcePackSend packet = (S48PacketResourcePackSend)e.getPacket();
/* 23 */       String url = packet.getURL();
/* 24 */       String hash = packet.getHash();
/*    */       try {
/* 26 */         String scheme = (new URI(url)).getScheme();
/* 27 */         boolean isLevelProtocol = "level".equals(scheme);
/*    */         
/* 29 */         if (!"http".equals(scheme) && !"https".equals(scheme) && !isLevelProtocol) {
/* 30 */           Helper.sendMessage(url + " Wrong protocol");
/*    */         }
/*    */         
/* 33 */         if (isLevelProtocol && (url.contains("..") || !url.endsWith("/resources.zip"))) {
/* 34 */           Helper.sendMessage(url + "Invalid levelstorage resourcepack path");
/*    */         }
/*    */         
/* 37 */         sendPacketNoEvent((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 38 */         sendPacketNoEvent((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/* 39 */       } catch (Exception e2) {
/* 40 */         Helper.sendMessage("Failed to handle resource pack");
/* 41 */         sendPacketNoEvent((Packet)new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\ResourcePackSpoof.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */