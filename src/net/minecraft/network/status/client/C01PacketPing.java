/*    */ package net.minecraft.network.status.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.status.INetHandlerStatusServer;
/*    */ 
/*    */ 
/*    */ public class C01PacketPing
/*    */   implements Packet<INetHandlerStatusServer>
/*    */ {
/*    */   private long clientTime;
/*    */   
/*    */   public C01PacketPing() {}
/*    */   
/*    */   public C01PacketPing(long ping) {
/* 17 */     this.clientTime = ping;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 24 */     this.clientTime = buf.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 31 */     buf.writeLong(this.clientTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerStatusServer handler) {
/* 39 */     handler.processPing(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getClientTime() {
/* 44 */     return this.clientTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\status\client\C01PacketPing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */