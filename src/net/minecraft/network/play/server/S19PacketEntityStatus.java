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
/*    */ public class S19PacketEntityStatus
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private byte logicOpcode;
/*    */   
/*    */   public S19PacketEntityStatus() {}
/*    */   
/*    */   public S19PacketEntityStatus(Entity entityIn, byte opCodeIn) {
/* 20 */     this.entityId = entityIn.getEntityId();
/* 21 */     this.logicOpcode = opCodeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 28 */     this.entityId = buf.readInt();
/* 29 */     this.logicOpcode = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 36 */     buf.writeInt(this.entityId);
/* 37 */     buf.writeByte(this.logicOpcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 45 */     handler.handleEntityStatus(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity(World worldIn) {
/* 50 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getOpCode() {
/* 55 */     return this.logicOpcode;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S19PacketEntityStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */