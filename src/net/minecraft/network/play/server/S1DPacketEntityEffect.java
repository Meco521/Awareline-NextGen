/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ 
/*    */ public class S1DPacketEntityEffect
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private byte effectId;
/*    */   private byte amplifier;
/*    */   private int duration;
/*    */   private byte hideParticles;
/*    */   
/*    */   public S1DPacketEntityEffect() {}
/*    */   
/*    */   public S1DPacketEntityEffect(int entityIdIn, PotionEffect effect) {
/* 22 */     this.entityId = entityIdIn;
/* 23 */     this.effectId = (byte)(effect.getPotionID() & 0xFF);
/* 24 */     this.amplifier = (byte)(effect.getAmplifier() & 0xFF);
/*    */     
/* 26 */     if (effect.getDuration() > 32767) {
/*    */       
/* 28 */       this.duration = 32767;
/*    */     }
/*    */     else {
/*    */       
/* 32 */       this.duration = effect.getDuration();
/*    */     } 
/*    */     
/* 35 */     this.hideParticles = (byte)(effect.getIsShowParticles() ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 42 */     this.entityId = buf.readVarIntFromBuffer();
/* 43 */     this.effectId = buf.readByte();
/* 44 */     this.amplifier = buf.readByte();
/* 45 */     this.duration = buf.readVarIntFromBuffer();
/* 46 */     this.hideParticles = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 53 */     buf.writeVarIntToBuffer(this.entityId);
/* 54 */     buf.writeByte(this.effectId);
/* 55 */     buf.writeByte(this.amplifier);
/* 56 */     buf.writeVarIntToBuffer(this.duration);
/* 57 */     buf.writeByte(this.hideParticles);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149429_c() {
/* 62 */     return (this.duration == 32767);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 70 */     handler.handleEntityEffect(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId() {
/* 75 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getEffectId() {
/* 80 */     return this.effectId;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getAmplifier() {
/* 85 */     return this.amplifier;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDuration() {
/* 90 */     return this.duration;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_179707_f() {
/* 95 */     return (this.hideParticles != 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S1DPacketEntityEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */