/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.event.events.world.updateEvents.EventUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.math.MathUtil;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ public class PingSpoof
/*    */   extends Module
/*    */ {
/* 18 */   private final Mode<String> mode = new Mode("Mode", new String[] { "0Ping", "Ping" }, "Ping");
/* 19 */   private final Numbers<Double> maxdelay = new Numbers("MaxDelay", Double.valueOf(1200.0D), Double.valueOf(100.0D), Double.valueOf(3000.0D), Double.valueOf(100.0D), () -> Boolean.valueOf(this.mode.isCurrentMode("Ping")));
/* 20 */   private final Numbers<Double> mindelay = new Numbers("MinDelay", Double.valueOf(1200.0D), Double.valueOf(100.0D), Double.valueOf(3000.0D), Double.valueOf(100.0D), () -> Boolean.valueOf(this.mode.isCurrentMode("Ping")));
/* 21 */   private final HashMap<Packet, Long> packets = new HashMap<>();
/*    */   
/*    */   public PingSpoof() {
/* 24 */     super("PingSpoofer", new String[] { "pingspoof" }, ModuleType.Misc);
/* 25 */     addSettings(new Value[] { (Value)this.mode, (Value)this.maxdelay, (Value)this.mindelay });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 30 */     super.onEnable();
/* 31 */     this.packets.clear();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPacketSend(EventPacketSend e) {
/* 36 */     if (this.mode.isCurrentMode("0Ping")) {
/* 37 */       if (e.getPacket() instanceof net.minecraft.network.play.client.C00PacketKeepAlive && mc.thePlayer.isEntityAlive()) {
/* 38 */         e.setCancelled(true);
/*    */       }
/*    */     }
/* 41 */     else if ((e.getPacket() instanceof net.minecraft.network.play.client.C00PacketKeepAlive || e.getPacket() instanceof net.minecraft.network.play.client.C16PacketClientStatus) && (
/* 42 */       !mc.thePlayer.isDead || mc.thePlayer.getHealth() > 0.0F) && !this.packets.containsKey(e.getPacket())) {
/* 43 */       e.setCancelled(true);
/* 44 */       synchronized (this.packets) {
/* 45 */         this.packets.put(e.getPacket(), Long.valueOf(System.currentTimeMillis()));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventUpdate e) {
/* 54 */     synchronized (this.packets) {
/* 55 */       this.packets.forEach((packet, time) -> {
/*    */             if ((System.currentTimeMillis() - time.longValue()) >= MathUtil.randomNumber(((Double)this.maxdelay.getValue()).doubleValue(), ((Double)this.mindelay.getValue()).doubleValue())) {
/*    */               mc.thePlayer.sendQueue.addToSendQueue(packet);
/*    */               this.packets.remove(packet);
/*    */             } 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\PingSpoof.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */