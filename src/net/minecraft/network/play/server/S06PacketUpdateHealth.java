/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S06PacketUpdateHealth
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private float health;
/*    */   private int foodLevel;
/*    */   private float saturationLevel;
/*    */   
/*    */   public S06PacketUpdateHealth() {}
/*    */   
/*    */   public S06PacketUpdateHealth(float healthIn, int foodLevelIn, float saturationIn) {
/* 19 */     this.health = healthIn;
/* 20 */     this.foodLevel = foodLevelIn;
/* 21 */     this.saturationLevel = saturationIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 28 */     this.health = buf.readFloat();
/* 29 */     this.foodLevel = buf.readVarIntFromBuffer();
/* 30 */     this.saturationLevel = buf.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 37 */     buf.writeFloat(this.health);
/* 38 */     buf.writeVarIntToBuffer(this.foodLevel);
/* 39 */     buf.writeFloat(this.saturationLevel);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handleUpdateHealth(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getHealth() {
/* 52 */     return this.health;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFoodLevel() {
/* 57 */     return this.foodLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getSaturationLevel() {
/* 62 */     return this.saturationLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S06PacketUpdateHealth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */