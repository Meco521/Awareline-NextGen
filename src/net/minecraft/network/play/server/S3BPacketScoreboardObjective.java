/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ 
/*    */ 
/*    */ public class S3BPacketScoreboardObjective
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String objectiveName;
/*    */   private String objectiveValue;
/*    */   private IScoreObjectiveCriteria.EnumRenderType type;
/*    */   private int field_149342_c;
/*    */   
/*    */   public S3BPacketScoreboardObjective() {}
/*    */   
/*    */   public S3BPacketScoreboardObjective(ScoreObjective p_i45224_1_, int p_i45224_2_) {
/* 22 */     this.objectiveName = p_i45224_1_.getName();
/* 23 */     this.objectiveValue = p_i45224_1_.getDisplayName();
/* 24 */     this.type = p_i45224_1_.getCriteria().getRenderType();
/* 25 */     this.field_149342_c = p_i45224_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 32 */     this.objectiveName = buf.readStringFromBuffer(16);
/* 33 */     this.field_149342_c = buf.readByte();
/*    */     
/* 35 */     if (this.field_149342_c == 0 || this.field_149342_c == 2) {
/*    */       
/* 37 */       this.objectiveValue = buf.readStringFromBuffer(32);
/* 38 */       this.type = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(buf.readStringFromBuffer(16));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 46 */     buf.writeString(this.objectiveName);
/* 47 */     buf.writeByte(this.field_149342_c);
/*    */     
/* 49 */     if (this.field_149342_c == 0 || this.field_149342_c == 2) {
/*    */       
/* 51 */       buf.writeString(this.objectiveValue);
/* 52 */       buf.writeString(this.type.func_178796_a());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 61 */     handler.handleScoreboardObjective(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_149339_c() {
/* 66 */     return this.objectiveName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_149337_d() {
/* 71 */     return this.objectiveValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149338_e() {
/* 76 */     return this.field_149342_c;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
/* 81 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S3BPacketScoreboardObjective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */