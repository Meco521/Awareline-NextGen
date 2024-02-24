/*    */ package awareline.main.event.events.world.packetEvents;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ public class EventPacketReceive
/*    */   extends Event {
/*    */   public Packet getPacket() {
/*  9 */     return this.packet;
/*    */   } public Packet packet;
/*    */   public EventPacketReceive(Packet packet) {
/* 12 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public void setPacket(Packet packet) {
/* 16 */     this.packet = packet;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\packetEvents\EventPacketReceive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */