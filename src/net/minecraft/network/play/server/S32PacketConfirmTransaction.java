/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S32PacketConfirmTransaction
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int windowId;
/*    */   private short actionNumber;
/*    */   private boolean field_148893_c;
/*    */   
/*    */   public S32PacketConfirmTransaction() {}
/*    */   
/*    */   public S32PacketConfirmTransaction(int windowIdIn, short actionNumberIn, boolean p_i45182_3_) {
/* 19 */     this.windowId = windowIdIn;
/* 20 */     this.actionNumber = actionNumberIn;
/* 21 */     this.field_148893_c = p_i45182_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 29 */     handler.handleConfirmTransaction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 36 */     this.windowId = buf.readUnsignedByte();
/* 37 */     this.actionNumber = buf.readShort();
/* 38 */     this.field_148893_c = buf.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 45 */     buf.writeByte(this.windowId);
/* 46 */     buf.writeShort(this.actionNumber);
/* 47 */     buf.writeBoolean(this.field_148893_c);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 52 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getActionNumber() {
/* 57 */     return this.actionNumber;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_148888_e() {
/* 62 */     return this.field_148893_c;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S32PacketConfirmTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */