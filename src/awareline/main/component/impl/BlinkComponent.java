/*    */ package awareline.main.component.impl;
/*    */ 
/*    */ import awareline.main.InstanceAccess;
/*    */ import awareline.main.component.StopWatch;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*    */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BlinkComponent
/*    */   implements InstanceAccess
/*    */ {
/* 22 */   public static final ConcurrentLinkedQueue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
/*    */   public static boolean blinking;
/* 24 */   public static ArrayList<Class<?>> exemptedPackets = new ArrayList<>(); public static boolean dispatch;
/* 25 */   public static StopWatch exemptionWatch = new StopWatch();
/*    */   
/*    */   public static void setExempt(Class<?>... packets) {
/* 28 */     exemptedPackets = new ArrayList<>(Arrays.asList(packets));
/* 29 */     exemptionWatch.reset();
/*    */   }
/*    */   
/*    */   public void sendNoEvent(Packet<?> packet) {
/* 33 */     if (mc.thePlayer != null) {
/* 34 */       mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(4)
/*    */   public void onPacketSend(PacketEvent event) {
/* 41 */     if (mc.thePlayer == null) {
/* 42 */       packets.clear();
/* 43 */       exemptedPackets.clear();
/*    */       
/*    */       return;
/*    */     } 
/* 47 */     if (mc.thePlayer.isDead || mc.isSingleplayer()) {
/* 48 */       packets.forEach(this::sendNoEvent);
/* 49 */       packets.clear();
/* 50 */       blinking = false;
/* 51 */       exemptedPackets.clear();
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     Packet<?> packet = event.getPacket();
/*    */     
/* 57 */     if (blinking && !dispatch) {
/* 58 */       if (exemptionWatch.finished(100L)) {
/* 59 */         exemptionWatch.reset();
/* 60 */         exemptedPackets.clear();
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 65 */       if (!event.isCancelled() && exemptedPackets.stream().noneMatch(packetClass -> (packetClass == packet.getClass()))) {
/*    */         
/* 67 */         packets.add(packet);
/* 68 */         event.setCancelled(true);
/*    */       } 
/* 70 */     } else if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/* 71 */       packets.forEach(this::sendNoEvent);
/* 72 */       packets.clear();
/* 73 */       dispatch = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void dispatch() {
/* 78 */     dispatch = true;
/*    */   }
/*    */   
/*    */   @EventHandler(4)
/*    */   public void onWorldChange(EventWorldChanged eventWorldChanged) {
/* 83 */     packets.clear();
/* 84 */     blinking = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\component\impl\BlinkComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */