/*    */ package awareline.main.mod.implement.misc;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*    */ 
/*    */ public class ClientSpoofer extends Module {
/* 14 */   private final String[] modes = new String[] { "Forge", "Lunar", "LabyMod", "PvP Lounge", "CheatBreaker", "Geyser" };
/* 15 */   public final Mode<String> mode = new Mode("Mode", this.modes, this.modes[0]);
/*    */   
/*    */   public ClientSpoofer() {
/* 18 */     super("ClientSpoofer", ModuleType.Misc);
/* 19 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onEnable() {
/* 24 */     ClientNotification.sendClientMessage("ClientSpoofer", "Rejoin for " + getHUDName() + " to work.", 4500L, ClientNotification.Type.WARNING);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacketSend(EventPacketSend event) {
/* 29 */     setSuffix((Serializable)this.mode.get());
/* 30 */     if (event.getPacket() instanceof C17PacketCustomPayload) {
/* 31 */       C17PacketCustomPayload packet = (C17PacketCustomPayload)event.getPacket();
/* 32 */       switch (this.mode.getModeAsString()) {
/*    */         case "Forge":
/* 34 */           packet.setData(createPacketBuffer("FML", true));
/*    */           break;
/*    */ 
/*    */         
/*    */         case "Lunar":
/* 39 */           packet.setChannel("REGISTER");
/* 40 */           packet.setData(createPacketBuffer("Lunar-Client", false));
/*    */           break;
/*    */ 
/*    */         
/*    */         case "LabyMod":
/* 45 */           packet.setData(createPacketBuffer("LMC", true));
/*    */           break;
/*    */ 
/*    */         
/*    */         case "PvP Lounge":
/* 50 */           packet.setData(createPacketBuffer("PLC18", false));
/*    */           break;
/*    */ 
/*    */         
/*    */         case "CheatBreaker":
/* 55 */           packet.setData(createPacketBuffer("CB", true));
/*    */           break;
/*    */ 
/*    */         
/*    */         case "Geyser":
/* 60 */           packet.setData(createPacketBuffer("Geyser", false));
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private PacketBuffer createPacketBuffer(String data, boolean string) {
/* 68 */     if (string) {
/* 69 */       return (new PacketBuffer(Unpooled.buffer())).writeString(data);
/*    */     }
/* 71 */     return new PacketBuffer(Unpooled.wrappedBuffer(data.getBytes()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\ClientSpoofer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */