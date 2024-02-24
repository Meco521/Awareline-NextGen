/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C16PacketClientStatus
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public EnumState status;
/*    */   
/*    */   public C16PacketClientStatus() {}
/*    */   
/*    */   public C16PacketClientStatus(EnumState statusIn) {
/* 17 */     this.status = statusIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 24 */     this.status = (EnumState)buf.readEnumValue(EnumState.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 31 */     buf.writeEnumValue(this.status);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 39 */     handler.processClientStatus(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumState getStatus() {
/* 44 */     return this.status;
/*    */   }
/*    */   
/*    */   public enum EnumState
/*    */   {
/* 49 */     PERFORM_RESPAWN,
/* 50 */     REQUEST_STATS,
/* 51 */     OPEN_INVENTORY_ACHIEVEMENT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C16PacketClientStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */