/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S3FPacketCustomPayload
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String channel;
/*    */   private PacketBuffer data;
/*    */   
/*    */   public S3FPacketCustomPayload() {}
/*    */   
/*    */   public S3FPacketCustomPayload(String channelName, PacketBuffer dataIn) {
/* 21 */     this.channel = channelName;
/* 22 */     this.data = dataIn;
/*    */     
/* 24 */     if (dataIn.writerIndex() > 1048576)
/*    */     {
/* 26 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 35 */     this.channel = buf.readStringFromBuffer(20);
/* 36 */     int i = buf.readableBytes();
/*    */     
/* 38 */     if (i >= 0 && i <= 1048576) {
/*    */       
/* 40 */       this.data = new PacketBuffer(buf.readBytes(i));
/*    */     }
/*    */     else {
/*    */       
/* 44 */       throw new IOException("Payload may not be larger than 1048576 bytes");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 52 */     buf.writeString(this.channel);
/* 53 */     buf.writeBytes((ByteBuf)this.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 61 */     handler.handleCustomPayload(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getChannelName() {
/* 66 */     return this.channel;
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketBuffer getBufferData() {
/* 71 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S3FPacketCustomPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */