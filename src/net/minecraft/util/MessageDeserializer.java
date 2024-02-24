/*    */ package net.minecraft.util;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
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
/*    */ public class MessageDeserializer extends ByteToMessageDecoder {
/* 17 */   private static final Logger logger = LogManager.getLogger();
/* 18 */   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
/*    */   
/*    */   private final EnumPacketDirection direction;
/*    */   
/*    */   public MessageDeserializer(EnumPacketDirection direction) {
/* 23 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws Exception {
/* 28 */     if (p_decode_2_.readableBytes() != 0) {
/*    */       
/* 30 */       PacketBuffer packetbuffer = new PacketBuffer(p_decode_2_);
/* 31 */       int i = packetbuffer.readVarIntFromBuffer();
/* 32 */       Packet packet = ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacket(this.direction, i);
/*    */       
/* 34 */       if (packet == null)
/*    */       {
/* 36 */         throw new IOException("Bad packet id " + i);
/*    */       }
/*    */ 
/*    */       
/* 40 */       packet.readPacketData(packetbuffer);
/*    */       
/* 42 */       if (packetbuffer.readableBytes() > 0)
/*    */       {
/* 44 */         throw new IOException("Packet " + ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get()).getId() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + i);
/*    */       }
/*    */ 
/*    */       
/* 48 */       p_decode_3_.add(packet);
/*    */       
/* 50 */       if (logger.isDebugEnabled())
/*    */       {
/* 52 */         logger.debug(RECEIVED_PACKET_MARKER, " IN: [{}:{}] {}", new Object[] { p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), Integer.valueOf(i), packet.getClass().getName() });
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\MessageDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */