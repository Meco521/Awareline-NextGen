/*    */ package awareline.main.event.events.world.packetEvents;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ public class PacketEvent extends Event {
/*    */   private final State state;
/*    */   private final Packet<?> packet;
/*    */   
/* 10 */   public State getState() { return this.state; } public Packet<?> getPacket() {
/* 11 */     return this.packet;
/*    */   }
/*    */   public PacketEvent(Packet<?> packet, State state) {
/* 14 */     this.state = state;
/* 15 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public boolean isOutgoing() {
/* 19 */     return this.state.equals(State.INCOMING);
/*    */   }
/*    */   
/*    */   public enum State
/*    */   {
/* 24 */     INCOMING,
/* 25 */     OUTGOING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\packetEvents\PacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */