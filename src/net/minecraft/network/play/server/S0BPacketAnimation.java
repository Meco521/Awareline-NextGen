/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S0BPacketAnimation
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private int type;
/*    */   
/*    */   public S0BPacketAnimation() {}
/*    */   
/*    */   public S0BPacketAnimation(Entity ent, int animationType) {
/* 19 */     this.entityId = ent.getEntityId();
/* 20 */     this.type = animationType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 27 */     this.entityId = buf.readVarIntFromBuffer();
/* 28 */     this.type = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 35 */     buf.writeVarIntToBuffer(this.entityId);
/* 36 */     buf.writeByte(this.type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 44 */     handler.handleAnimation(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 49 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAnimationType() {
/* 54 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S0BPacketAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */