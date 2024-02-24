/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ 
/*    */ public class S1EPacketRemoveEntityEffect
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private int effectId;
/*    */   
/*    */   public S1EPacketRemoveEntityEffect() {}
/*    */   
/*    */   public S1EPacketRemoveEntityEffect(int entityIdIn, PotionEffect effect) {
/* 19 */     this.entityId = entityIdIn;
/* 20 */     this.effectId = effect.getPotionID();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 27 */     this.entityId = buf.readVarIntFromBuffer();
/* 28 */     this.effectId = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 35 */     buf.writeVarIntToBuffer(this.entityId);
/* 36 */     buf.writeByte(this.effectId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 44 */     handler.handleRemoveEntityEffect(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId() {
/* 49 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEffectId() {
/* 54 */     return this.effectId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S1EPacketRemoveEntityEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */