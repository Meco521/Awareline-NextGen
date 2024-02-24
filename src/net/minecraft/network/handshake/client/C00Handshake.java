/*    */ package net.minecraft.network.handshake.client;
/*    */ 
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*    */ 
/*    */ 
/*    */ public class C00Handshake
/*    */   implements Packet<INetHandlerHandshakeServer>
/*    */ {
/*    */   private int protocolVersion;
/*    */   private String ip;
/*    */   private int port;
/*    */   private EnumConnectionState requestedState;
/*    */   
/*    */   public C00Handshake() {}
/*    */   
/*    */   public C00Handshake(int version, String ip, int port, EnumConnectionState requestedState) {
/* 21 */     this.protocolVersion = version;
/* 22 */     this.ip = ip;
/* 23 */     this.port = port;
/* 24 */     this.requestedState = requestedState;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 31 */     this.protocolVersion = buf.readVarIntFromBuffer();
/* 32 */     this.ip = buf.readStringFromBuffer(255);
/* 33 */     this.port = buf.readUnsignedShort();
/* 34 */     this.requestedState = EnumConnectionState.getById(buf.readVarIntFromBuffer());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 41 */     buf.writeVarIntToBuffer(this.protocolVersion);
/* 42 */     buf.writeString(this.ip);
/* 43 */     buf.writeShort(this.port);
/* 44 */     buf.writeVarIntToBuffer(this.requestedState.getId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerHandshakeServer handler) {
/* 52 */     handler.processHandshake(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumConnectionState getRequestedState() {
/* 57 */     return this.requestedState;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getProtocolVersion() {
/* 62 */     return this.protocolVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\handshake\client\C00Handshake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */