/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S09PacketHeldItemChange
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int heldItemHotbarIndex;
/*    */   
/*    */   public S09PacketHeldItemChange() {}
/*    */   
/*    */   public S09PacketHeldItemChange(int hotbarIndexIn) {
/* 17 */     this.heldItemHotbarIndex = hotbarIndexIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 24 */     this.heldItemHotbarIndex = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 31 */     buf.writeByte(this.heldItemHotbarIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 39 */     handler.handleHeldItemChange(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeldItemHotbarIndex() {
/* 44 */     return this.heldItemHotbarIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S09PacketHeldItemChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */