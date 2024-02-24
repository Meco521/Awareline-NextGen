/*    */ package awareline.main.mod.implement.player.anti;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.LBEvents.EventMotionUpdate;
/*    */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*    */ 
/*    */ public class AntiDesync
/*    */   extends Module {
/*    */   public AntiDesync() {
/* 14 */     super("AntiDesync", ModuleType.Player);
/*    */   }
/*    */   private int slot;
/*    */   
/*    */   @EventHandler
/*    */   public void onPacketSendEvent(PacketEvent event) {
/* 20 */     if (event.getState() == PacketEvent.State.OUTGOING && 
/* 21 */       event.getPacket() instanceof C09PacketHeldItemChange) {
/* 22 */       this.slot = ((C09PacketHeldItemChange)event.getPacket()).getSlotId();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onMotionEvent(EventMotionUpdate event) {
/* 29 */     if (this.slot != mc.thePlayer.inventory.currentItem && this.slot != -1)
/* 30 */       sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\anti\AntiDesync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */