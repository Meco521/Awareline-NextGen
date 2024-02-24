/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class S19PacketEntityHeadLook
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private byte yaw;
/*    */   
/*    */   public S19PacketEntityHeadLook() {}
/*    */   
/*    */   public S19PacketEntityHeadLook(Entity entityIn, byte p_i45214_2_) {
/* 20 */     this.entityId = entityIn.getEntityId();
/* 21 */     this.yaw = p_i45214_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 28 */     this.entityId = buf.readVarIntFromBuffer();
/* 29 */     this.yaw = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 36 */     buf.writeVarIntToBuffer(this.entityId);
/* 37 */     buf.writeByte(this.yaw);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 45 */     handler.handleEntityHeadLook(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity(World worldIn) {
/* 50 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getYaw() {
/* 55 */     return this.yaw;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S19PacketEntityHeadLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */