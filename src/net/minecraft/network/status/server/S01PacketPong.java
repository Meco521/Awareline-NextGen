/*    */ package net.minecraft.network.status.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.status.INetHandlerStatusClient;
/*    */ 
/*    */ 
/*    */ public class S01PacketPong
/*    */   implements Packet<INetHandlerStatusClient>
/*    */ {
/*    */   private long clientTime;
/*    */   
/*    */   public S01PacketPong() {}
/*    */   
/*    */   public S01PacketPong(long time) {
/* 17 */     this.clientTime = time;
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
/*    */   public void processPacket(INetHandlerStatusClient handler) {
/* 39 */     handler.handlePong(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\status\server\S01PacketPong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */