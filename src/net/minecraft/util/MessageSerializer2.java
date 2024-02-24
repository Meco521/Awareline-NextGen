/*    */ package net.minecraft.util;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public class MessageSerializer2
/*    */   extends MessageToByteEncoder<ByteBuf> {
/*    */   protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) {
/* 11 */     int i = p_encode_2_.readableBytes();
/* 12 */     int j = PacketBuffer.getVarIntSize(i);
/*    */     
/* 14 */     if (j > 3)
/*    */     {
/* 16 */       throw new IllegalArgumentException("unable to fit " + i + " into " + '\003');
/*    */     }
/*    */ 
/*    */     
/* 20 */     PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
/* 21 */     packetbuffer.ensureWritable(j + i);
/* 22 */     packetbuffer.writeVarIntToBuffer(i);
/* 23 */     packetbuffer.writeBytes(p_encode_2_, p_encode_2_.readerIndex(), i);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\MessageSerializer2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */