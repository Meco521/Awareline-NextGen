/*    */ package com.github.creeper123123321.viafabric.handler.clientside;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import java.util.List;
/*    */ import us.myles.ViaVersion.api.data.UserConnection;
/*    */ import us.myles.ViaVersion.exception.CancelEncoderException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VREncodeHandler
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final UserConnection info;
/*    */   
/*    */   public VREncodeHandler(UserConnection info) {
/* 41 */     this.info = info;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
/* 46 */     if (!this.info.checkIncomingPacket()) throw CancelEncoderException.generate(null); 
/* 47 */     if (!this.info.shouldTransformPacket()) {
/* 48 */       out.add(bytebuf.retain());
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
/*    */     try {
/* 54 */       this.info.transformIncoming(transformedBuf, CancelEncoderException::generate);
/* 55 */       out.add(transformedBuf.retain());
/*    */     } finally {
/* 57 */       transformedBuf.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 63 */     if (cause instanceof us.myles.ViaVersion.exception.CancelCodecException)
/* 64 */       return;  super.exceptionCaught(ctx, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\handler\clientside\VREncodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */