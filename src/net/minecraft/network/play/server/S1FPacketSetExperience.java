/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S1FPacketSetExperience
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private float field_149401_a;
/*    */   private int totalExperience;
/*    */   private int level;
/*    */   
/*    */   public S1FPacketSetExperience() {}
/*    */   
/*    */   public S1FPacketSetExperience(float p_i45222_1_, int totalExperienceIn, int levelIn) {
/* 19 */     this.field_149401_a = p_i45222_1_;
/* 20 */     this.totalExperience = totalExperienceIn;
/* 21 */     this.level = levelIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 28 */     this.field_149401_a = buf.readFloat();
/* 29 */     this.level = buf.readVarIntFromBuffer();
/* 30 */     this.totalExperience = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 37 */     buf.writeFloat(this.field_149401_a);
/* 38 */     buf.writeVarIntToBuffer(this.level);
/* 39 */     buf.writeVarIntToBuffer(this.totalExperience);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handleSetExperience(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_149397_c() {
/* 52 */     return this.field_149401_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTotalExperience() {
/* 57 */     return this.totalExperience;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLevel() {
/* 62 */     return this.level;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S1FPacketSetExperience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */