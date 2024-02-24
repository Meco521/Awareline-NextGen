/*    */ package net.minecraft.network.login.client;
/*    */ 
/*    */ import java.security.PrivateKey;
/*    */ import java.security.PublicKey;
/*    */ import javax.crypto.SecretKey;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginServer;
/*    */ import net.minecraft.util.CryptManager;
/*    */ 
/*    */ public class C01PacketEncryptionResponse
/*    */   implements Packet<INetHandlerLoginServer> {
/* 14 */   private byte[] secretKeyEncrypted = new byte[0];
/* 15 */   private byte[] verifyTokenEncrypted = new byte[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public C01PacketEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] verifyToken) {
/* 23 */     this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
/* 24 */     this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, verifyToken);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 31 */     this.secretKeyEncrypted = buf.readByteArray();
/* 32 */     this.verifyTokenEncrypted = buf.readByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 39 */     buf.writeByteArray(this.secretKeyEncrypted);
/* 40 */     buf.writeByteArray(this.verifyTokenEncrypted);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginServer handler) {
/* 48 */     handler.processEncryptionResponse(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public SecretKey getSecretKey(PrivateKey key) {
/* 53 */     return CryptManager.decryptSharedKey(key, this.secretKeyEncrypted);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getVerifyToken(PrivateKey key) {
/* 58 */     return (key == null) ? this.verifyTokenEncrypted : CryptManager.decryptData(key, this.verifyTokenEncrypted);
/*    */   }
/*    */   
/*    */   public C01PacketEncryptionResponse() {}
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\login\client\C01PacketEncryptionResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */