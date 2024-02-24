/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import java.security.PublicKey;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ import net.minecraft.util.CryptManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S01PacketEncryptionRequest
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private String hashedServerId;
/*    */   private PublicKey publicKey;
/*    */   private byte[] verifyToken;
/*    */   
/*    */   public S01PacketEncryptionRequest() {}
/*    */   
/*    */   public S01PacketEncryptionRequest(String serverId, PublicKey key, byte[] verifyToken) {
/* 22 */     this.hashedServerId = serverId;
/* 23 */     this.publicKey = key;
/* 24 */     this.verifyToken = verifyToken;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 31 */     this.hashedServerId = buf.readStringFromBuffer(20);
/* 32 */     this.publicKey = CryptManager.decodePublicKey(buf.readByteArray());
/* 33 */     this.verifyToken = buf.readByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 40 */     buf.writeString(this.hashedServerId);
/* 41 */     buf.writeByteArray(this.publicKey.getEncoded());
/* 42 */     buf.writeByteArray(this.verifyToken);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 50 */     handler.handleEncryptionRequest(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServerId() {
/* 55 */     return this.hashedServerId;
/*    */   }
/*    */ 
/*    */   
/*    */   public PublicKey getPublicKey() {
/* 60 */     return this.publicKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getVerifyToken() {
/* 65 */     return this.verifyToken;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\login\server\S01PacketEncryptionRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */