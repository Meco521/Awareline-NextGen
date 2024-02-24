/*    */ package com.github.creeper123123321.viafabric.platform;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import us.myles.ViaVersion.api.data.UserConnection;
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
/*    */ public class VRClientSideUserConnection
/*    */   extends UserConnection
/*    */ {
/*    */   public VRClientSideUserConnection(Channel socketChannel) {
/* 36 */     super(socketChannel);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendRawPacket(ByteBuf packet, boolean currentThread) {
/* 42 */     Runnable act = () -> getChannel().pipeline().context("via-decoder").fireChannelRead(packet);
/*    */     
/* 44 */     if (currentThread) {
/*    */       
/* 46 */       act.run();
/*    */     } else {
/* 48 */       getChannel().eventLoop().execute(act);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
/* 55 */     getChannel().pipeline().context("via-decoder").fireChannelRead(packet);
/* 56 */     return getChannel().newSucceededFuture();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacketToServer(ByteBuf packet, boolean currentThread) {
/* 61 */     if (currentThread) {
/* 62 */       getChannel().pipeline().context("via-encoder").writeAndFlush(packet);
/*    */     } else {
/* 64 */       getChannel().eventLoop().submit(() -> getChannel().pipeline().context("via-encoder").writeAndFlush(packet));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\platform\VRClientSideUserConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */