/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S0DPacketCollectItem
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int collectedItemEntityId;
/*    */   private int entityId;
/*    */   
/*    */   public S0DPacketCollectItem() {}
/*    */   
/*    */   public S0DPacketCollectItem(int collectedItemEntityIdIn, int entityIdIn) {
/* 18 */     this.collectedItemEntityId = collectedItemEntityIdIn;
/* 19 */     this.entityId = entityIdIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 26 */     this.collectedItemEntityId = buf.readVarIntFromBuffer();
/* 27 */     this.entityId = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 34 */     buf.writeVarIntToBuffer(this.collectedItemEntityId);
/* 35 */     buf.writeVarIntToBuffer(this.entityId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 43 */     handler.handleCollectItem(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCollectedItemEntityID() {
/* 48 */     return this.collectedItemEntityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 53 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S0DPacketCollectItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */