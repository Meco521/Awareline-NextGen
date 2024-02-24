/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S1BPacketEntityAttach
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int leash;
/*    */   private int entityId;
/*    */   private int vehicleEntityId;
/*    */   
/*    */   public S1BPacketEntityAttach() {}
/*    */   
/*    */   public S1BPacketEntityAttach(int leashIn, Entity entityIn, Entity vehicle) {
/* 20 */     this.leash = leashIn;
/* 21 */     this.entityId = entityIn.getEntityId();
/* 22 */     this.vehicleEntityId = (vehicle != null) ? vehicle.getEntityId() : -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 29 */     this.entityId = buf.readInt();
/* 30 */     this.vehicleEntityId = buf.readInt();
/* 31 */     this.leash = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 38 */     buf.writeInt(this.entityId);
/* 39 */     buf.writeInt(this.vehicleEntityId);
/* 40 */     buf.writeByte(this.leash);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 48 */     handler.handleEntityAttach(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLeash() {
/* 53 */     return this.leash;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId() {
/* 58 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVehicleEntityId() {
/* 63 */     return this.vehicleEntityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S1BPacketEntityAttach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */