/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C19PacketResourcePackStatus
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String hash;
/*    */   private Action status;
/*    */   
/*    */   public C19PacketResourcePackStatus() {}
/*    */   
/*    */   public C19PacketResourcePackStatus(String hashIn, Action statusIn) {
/* 18 */     if (hashIn.length() > 40)
/*    */     {
/* 20 */       hashIn = hashIn.substring(0, 40);
/*    */     }
/*    */     
/* 23 */     this.hash = hashIn;
/* 24 */     this.status = statusIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 31 */     this.hash = buf.readStringFromBuffer(40);
/* 32 */     this.status = (Action)buf.readEnumValue(Action.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 39 */     buf.writeString(this.hash);
/* 40 */     buf.writeEnumValue(this.status);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 48 */     handler.handleResourcePackStatus(this);
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 53 */     SUCCESSFULLY_LOADED,
/* 54 */     DECLINED,
/* 55 */     FAILED_DOWNLOAD,
/* 56 */     ACCEPTED;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C19PacketResourcePackStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */