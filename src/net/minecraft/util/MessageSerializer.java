/*    */ package net.minecraft.util;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.EnumPacketDirection;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ public class MessageSerializer extends MessageToByteEncoder<Packet> {
/* 16 */   private static final Logger logger = LogManager.getLogger();
/* 17 */   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
/*    */   
/*    */   private final EnumPacketDirection direction;
/*    */   
/*    */   public MessageSerializer(EnumPacketDirection direction) {
/* 22 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext p_encode_1_, Packet p_encode_2_, ByteBuf p_encode_3_) throws Exception {
/* 27 */     Integer integer = ((EnumConnectionState)p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacketId(this.direction, p_encode_2_);
/*    */     
/* 29 */     if (logger.isDebugEnabled())
/*    */     {
/* 31 */       logger.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[] { p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), integer, p_encode_2_.getClass().getName() });
/*    */     }
/*    */     
/* 34 */     if (integer == null)
/*    */     {
/* 36 */       throw new IOException("Can't serialize unregistered packet");
/*    */     }
/*    */ 
/*    */     
/* 40 */     PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
/* 41 */     packetbuffer.writeVarIntToBuffer(integer.intValue());
/*    */ 
/*    */     
/*    */     try {
/* 45 */       p_encode_2_.writePacketData(packetbuffer);
/*    */     }
/* 47 */     catch (Throwable throwable) {
/*    */       
/* 49 */       logger.error(throwable);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\MessageSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */