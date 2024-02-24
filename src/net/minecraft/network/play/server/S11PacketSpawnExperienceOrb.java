/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class S11PacketSpawnExperienceOrb
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityID;
/*    */   private int posX;
/*    */   private int posY;
/*    */   private int posZ;
/*    */   private int xpValue;
/*    */   
/*    */   public S11PacketSpawnExperienceOrb() {}
/*    */   
/*    */   public S11PacketSpawnExperienceOrb(EntityXPOrb xpOrb) {
/* 23 */     this.entityID = xpOrb.getEntityId();
/* 24 */     this.posX = MathHelper.floor_double(xpOrb.posX * 32.0D);
/* 25 */     this.posY = MathHelper.floor_double(xpOrb.posY * 32.0D);
/* 26 */     this.posZ = MathHelper.floor_double(xpOrb.posZ * 32.0D);
/* 27 */     this.xpValue = xpOrb.getXpValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 34 */     this.entityID = buf.readVarIntFromBuffer();
/* 35 */     this.posX = buf.readInt();
/* 36 */     this.posY = buf.readInt();
/* 37 */     this.posZ = buf.readInt();
/* 38 */     this.xpValue = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 45 */     buf.writeVarIntToBuffer(this.entityID);
/* 46 */     buf.writeInt(this.posX);
/* 47 */     buf.writeInt(this.posY);
/* 48 */     buf.writeInt(this.posZ);
/* 49 */     buf.writeShort(this.xpValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 57 */     handler.handleSpawnExperienceOrb(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 62 */     return this.entityID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getX() {
/* 67 */     return this.posX;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY() {
/* 72 */     return this.posY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getZ() {
/* 77 */     return this.posZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getXPValue() {
/* 82 */     return this.xpValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S11PacketSpawnExperienceOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */