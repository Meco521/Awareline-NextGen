/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ 
/*     */ public class S3CPacketUpdateScore implements Packet<INetHandlerPlayClient> {
/*  11 */   private String name = "";
/*  12 */   private String objective = "";
/*     */ 
/*     */   
/*     */   private int value;
/*     */ 
/*     */   
/*     */   private Action action;
/*     */ 
/*     */   
/*     */   public S3CPacketUpdateScore(Score scoreIn) {
/*  22 */     this.name = scoreIn.getPlayerName();
/*  23 */     this.objective = scoreIn.getObjective().getName();
/*  24 */     this.value = scoreIn.getScorePoints();
/*  25 */     this.action = Action.CHANGE;
/*     */   }
/*     */ 
/*     */   
/*     */   public S3CPacketUpdateScore(String nameIn) {
/*  30 */     this.name = nameIn;
/*  31 */     this.objective = "";
/*  32 */     this.value = 0;
/*  33 */     this.action = Action.REMOVE;
/*     */   }
/*     */ 
/*     */   
/*     */   public S3CPacketUpdateScore(String nameIn, ScoreObjective objectiveIn) {
/*  38 */     this.name = nameIn;
/*  39 */     this.objective = objectiveIn.getName();
/*  40 */     this.value = 0;
/*  41 */     this.action = Action.REMOVE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  48 */     this.name = buf.readStringFromBuffer(40);
/*  49 */     this.action = (Action)buf.readEnumValue(Action.class);
/*  50 */     this.objective = buf.readStringFromBuffer(16);
/*     */     
/*  52 */     if (this.action != Action.REMOVE)
/*     */     {
/*  54 */       this.value = buf.readVarIntFromBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  62 */     buf.writeString(this.name);
/*  63 */     buf.writeEnumValue(this.action);
/*  64 */     buf.writeString(this.objective);
/*     */     
/*  66 */     if (this.action != Action.REMOVE)
/*     */     {
/*  68 */       buf.writeVarIntToBuffer(this.value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  77 */     handler.handleUpdateScore(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerName() {
/*  82 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getObjectiveName() {
/*  87 */     return this.objective;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScoreValue() {
/*  92 */     return this.value;
/*     */   }
/*     */   public S3CPacketUpdateScore() {}
/*     */   
/*     */   public Action getScoreAction() {
/*  97 */     return this.action;
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/* 102 */     CHANGE,
/* 103 */     REMOVE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S3CPacketUpdateScore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */