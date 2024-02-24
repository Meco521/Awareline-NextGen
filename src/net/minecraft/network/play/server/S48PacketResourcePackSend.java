/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S48PacketResourcePackSend
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String url;
/*    */   private String hash;
/*    */   
/*    */   public S48PacketResourcePackSend() {}
/*    */   
/*    */   public S48PacketResourcePackSend(String url, String hash) {
/* 18 */     this.url = url;
/* 19 */     this.hash = hash;
/*    */     
/* 21 */     if (hash.length() > 40)
/*    */     {
/* 23 */       throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 31 */     this.url = buf.readStringFromBuffer(32767);
/* 32 */     this.hash = buf.readStringFromBuffer(40);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 39 */     buf.writeString(this.url);
/* 40 */     buf.writeString(this.hash);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 48 */     handler.handleResourcePack(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getURL() {
/* 53 */     return this.url;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHash() {
/* 58 */     return this.hash;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S48PacketResourcePackSend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */