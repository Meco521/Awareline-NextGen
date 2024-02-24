/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C17PacketCustomPayload
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String channel;
/*    */   private PacketBuffer data;
/*    */   
/*    */   public C17PacketCustomPayload() {}
/*    */   
/*    */   public C17PacketCustomPayload(String channelIn, PacketBuffer dataIn) {
/* 21 */     this.channel = channelIn;
/* 22 */     this.data = dataIn;
/*    */     
/* 24 */     if (dataIn.writerIndex() > 32767)
/*    */     {
/* 26 */       throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
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
/* 38 */     if (i >= 0 && i <= 32767) {
/*    */       
/* 40 */       this.data = new PacketBuffer(buf.readBytes(i));
/*    */     }
/*    */     else {
/*    */       
/* 44 */       throw new IOException("Payload may not be larger than 32767 bytes");
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
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 61 */     handler.processVanilla250Packet(this);
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
/*    */   
/*    */   public void setData(PacketBuffer fml) {
/* 75 */     this.data = fml;
/*    */   }
/*    */   
/*    */   public void setChannel(String register) {
/* 79 */     this.channel = register;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C17PacketCustomPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */