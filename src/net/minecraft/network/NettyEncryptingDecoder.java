/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import java.util.List;
/*    */ import javax.crypto.Cipher;
/*    */ 
/*    */ 
/*    */ public class NettyEncryptingDecoder
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final NettyEncryptionTranslator decryptionCodec;
/*    */   
/*    */   public NettyEncryptingDecoder(Cipher cipher) {
/* 16 */     this.decryptionCodec = new NettyEncryptionTranslator(cipher);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws Exception {
/* 21 */     p_decode_3_.add(this.decryptionCodec.decipher(p_decode_1_, p_decode_2_));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\NettyEncryptingDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */