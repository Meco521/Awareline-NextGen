/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C01PacketChatMessage
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public String message;
/*    */   
/*    */   public C01PacketChatMessage() {}
/*    */   
/*    */   public C01PacketChatMessage(String messageIn) {
/* 17 */     if (messageIn.length() > 100)
/*    */     {
/* 19 */       messageIn = messageIn.substring(0, 100);
/*    */     }
/*    */     
/* 22 */     this.message = messageIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 29 */     this.message = buf.readStringFromBuffer(100);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 36 */     buf.writeString(this.message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 44 */     handler.processChatMessage(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 49 */     return this.message;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 53 */     this.message = message;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C01PacketChatMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */