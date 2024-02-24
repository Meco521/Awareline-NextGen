/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class C02PacketUseEntity
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int entityId;
/*    */   private Action action;
/*    */   private Vec3 hitVec;
/*    */   
/*    */   public C02PacketUseEntity() {}
/*    */   
/*    */   public C02PacketUseEntity(Entity entity, Action action) {
/* 22 */     this.entityId = entity.getEntityId();
/* 23 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public C02PacketUseEntity(Entity entity, Vec3 hitVec) {
/* 28 */     this(entity, Action.INTERACT_AT);
/* 29 */     this.hitVec = hitVec;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 36 */     this.entityId = buf.readVarIntFromBuffer();
/* 37 */     this.action = (Action)buf.readEnumValue(Action.class);
/*    */     
/* 39 */     if (this.action == Action.INTERACT_AT)
/*    */     {
/* 41 */       this.hitVec = new Vec3(buf.readFloat(), buf.readFloat(), buf.readFloat());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 49 */     buf.writeVarIntToBuffer(this.entityId);
/* 50 */     buf.writeEnumValue(this.action);
/*    */     
/* 52 */     if (this.action == Action.INTERACT_AT) {
/*    */       
/* 54 */       buf.writeFloat((float)this.hitVec.xCoord);
/* 55 */       buf.writeFloat((float)this.hitVec.yCoord);
/* 56 */       buf.writeFloat((float)this.hitVec.zCoord);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 65 */     handler.processUseEntity(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntityFromWorld(World worldIn) {
/* 70 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 75 */     return this.action;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3 getHitVec() {
/* 80 */     return this.hitVec;
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 85 */     INTERACT,
/* 86 */     ATTACK,
/* 87 */     INTERACT_AT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C02PacketUseEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */