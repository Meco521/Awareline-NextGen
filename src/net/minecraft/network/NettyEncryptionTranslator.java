/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.ShortBufferException;
/*    */ 
/*    */ 
/*    */ public class NettyEncryptionTranslator
/*    */ {
/*    */   private final Cipher cipher;
/* 12 */   private byte[] field_150505_b = new byte[0];
/* 13 */   private byte[] field_150506_c = new byte[0];
/*    */ 
/*    */   
/*    */   protected NettyEncryptionTranslator(Cipher cipherIn) {
/* 17 */     this.cipher = cipherIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private byte[] func_150502_a(ByteBuf buf) {
/* 22 */     int i = buf.readableBytes();
/*    */     
/* 24 */     if (this.field_150505_b.length < i)
/*    */     {
/* 26 */       this.field_150505_b = new byte[i];
/*    */     }
/*    */     
/* 29 */     buf.readBytes(this.field_150505_b, 0, i);
/* 30 */     return this.field_150505_b;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ByteBuf decipher(ChannelHandlerContext ctx, ByteBuf buffer) throws ShortBufferException {
/* 35 */     int i = buffer.readableBytes();
/* 36 */     byte[] abyte = func_150502_a(buffer);
/* 37 */     ByteBuf bytebuf = ctx.alloc().heapBuffer(this.cipher.getOutputSize(i));
/* 38 */     bytebuf.writerIndex(this.cipher.update(abyte, 0, i, bytebuf.array(), bytebuf.arrayOffset()));
/* 39 */     return bytebuf;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void cipher(ByteBuf in, ByteBuf out) throws ShortBufferException {
/* 44 */     int i = in.readableBytes();
/* 45 */     byte[] abyte = func_150502_a(in);
/* 46 */     int j = this.cipher.getOutputSize(i);
/*    */     
/* 48 */     if (this.field_150506_c.length < j)
/*    */     {
/* 50 */       this.field_150506_c = new byte[j];
/*    */     }
/*    */     
/* 53 */     out.writeBytes(this.field_150506_c, 0, this.cipher.update(abyte, 0, i, this.field_150506_c));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\NettyEncryptionTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */