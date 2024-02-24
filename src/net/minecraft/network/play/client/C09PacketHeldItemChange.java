/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C09PacketHeldItemChange
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int slotId;
/*    */   
/*    */   public C09PacketHeldItemChange() {}
/*    */   
/*    */   public C09PacketHeldItemChange(int slotId) {
/* 17 */     this.slotId = slotId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 24 */     this.slotId = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 31 */     buf.writeShort(this.slotId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 39 */     handler.processHeldItemChange(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSlotId() {
/* 44 */     return this.slotId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C09PacketHeldItemChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */