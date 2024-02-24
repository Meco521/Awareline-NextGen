/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S02PacketLoginSuccess
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private GameProfile profile;
/*    */   
/*    */   public S02PacketLoginSuccess() {}
/*    */   
/*    */   public S02PacketLoginSuccess(GameProfile profileIn) {
/* 20 */     this.profile = profileIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 27 */     String s = buf.readStringFromBuffer(36);
/* 28 */     String s1 = buf.readStringFromBuffer(16);
/* 29 */     UUID uuid = UUID.fromString(s);
/* 30 */     this.profile = new GameProfile(uuid, s1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 37 */     UUID uuid = this.profile.getId();
/* 38 */     buf.writeString((uuid == null) ? "" : uuid.toString());
/* 39 */     buf.writeString(this.profile.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 47 */     handler.handleLoginSuccess(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile getProfile() {
/* 52 */     return this.profile;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\login\server\S02PacketLoginSuccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */