/*    */ package awareline.main.component.impl;
/*    */ 
/*    */ import awareline.main.InstanceAccess;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S2APacketParticles;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ParticleDistanceComponent
/*    */   implements InstanceAccess
/*    */ {
/*    */   @EventHandler
/*    */   public void onPacketReceiveEvent(EventPacketReceive event) {
/* 17 */     Packet<?> packet = event.getPacket();
/*    */     
/* 19 */     if (packet instanceof S2APacketParticles) {
/* 20 */       S2APacketParticles wrapper = (S2APacketParticles)packet;
/*    */       
/* 22 */       double distance = mc.thePlayer.getDistanceSq(wrapper.getXCoordinate(), wrapper.getYCoordinate(), wrapper.getZCoordinate());
/*    */       
/* 24 */       if (distance > 36.0D)
/* 25 */         event.setCancelled(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\component\impl\ParticleDistanceComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */