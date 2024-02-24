/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S2CPacketSpawnGlobalEntity
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */   private int type;
/*    */   
/*    */   public S2CPacketSpawnGlobalEntity() {}
/*    */   
/*    */   public S2CPacketSpawnGlobalEntity(Entity entityIn) {
/* 24 */     this.entityId = entityIn.getEntityId();
/* 25 */     this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
/* 26 */     this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
/* 27 */     this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*    */     
/* 29 */     if (entityIn instanceof net.minecraft.entity.effect.EntityLightningBolt)
/*    */     {
/* 31 */       this.type = 1;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 39 */     this.entityId = buf.readVarIntFromBuffer();
/* 40 */     this.type = buf.readByte();
/* 41 */     this.x = buf.readInt();
/* 42 */     this.y = buf.readInt();
/* 43 */     this.z = buf.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 50 */     buf.writeVarIntToBuffer(this.entityId);
/* 51 */     buf.writeByte(this.type);
/* 52 */     buf.writeInt(this.x);
/* 53 */     buf.writeInt(this.y);
/* 54 */     buf.writeInt(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 62 */     handler.handleSpawnGlobalEntity(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149052_c() {
/* 67 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149051_d() {
/* 72 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149050_e() {
/* 77 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149049_f() {
/* 82 */     return this.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149053_g() {
/* 87 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S2CPacketSpawnGlobalEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */