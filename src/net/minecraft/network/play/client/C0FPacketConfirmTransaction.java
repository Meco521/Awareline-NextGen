/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C0FPacketConfirmTransaction
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public int windowId;
/*    */   public short uid;
/*    */   public boolean accepted;
/*    */   
/*    */   public C0FPacketConfirmTransaction() {}
/*    */   
/*    */   public C0FPacketConfirmTransaction(int windowId, short uid, boolean accepted) {
/* 19 */     this.windowId = windowId;
/* 20 */     this.uid = uid;
/* 21 */     this.accepted = accepted;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 29 */     handler.processConfirmTransaction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 36 */     this.windowId = buf.readByte();
/* 37 */     this.uid = buf.readShort();
/* 38 */     this.accepted = (buf.readByte() != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 45 */     buf.writeByte(this.windowId);
/* 46 */     buf.writeShort(this.uid);
/* 47 */     buf.writeByte(this.accepted ? 1 : 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 52 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getUid() {
/* 57 */     return this.uid;
/*    */   }
/*    */   
/*    */   public short getID() {
/* 61 */     return this.uid;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C0FPacketConfirmTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */