/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ 
/*    */ 
/*    */ public class S3DPacketDisplayScoreboard
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int position;
/*    */   private String scoreName;
/*    */   
/*    */   public S3DPacketDisplayScoreboard() {}
/*    */   
/*    */   public S3DPacketDisplayScoreboard(int positionIn, ScoreObjective scoreIn) {
/* 19 */     this.position = positionIn;
/*    */     
/* 21 */     if (scoreIn == null) {
/*    */       
/* 23 */       this.scoreName = "";
/*    */     }
/*    */     else {
/*    */       
/* 27 */       this.scoreName = scoreIn.getName();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 35 */     this.position = buf.readByte();
/* 36 */     this.scoreName = buf.readStringFromBuffer(16);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 43 */     buf.writeByte(this.position);
/* 44 */     buf.writeString(this.scoreName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 52 */     handler.handleDisplayScoreboard(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149371_c() {
/* 57 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_149370_d() {
/* 62 */     return this.scoreName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S3DPacketDisplayScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */