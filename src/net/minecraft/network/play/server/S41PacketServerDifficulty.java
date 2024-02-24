/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ 
/*    */ 
/*    */ public class S41PacketServerDifficulty
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private EnumDifficulty difficulty;
/*    */   private boolean difficultyLocked;
/*    */   
/*    */   public S41PacketServerDifficulty() {}
/*    */   
/*    */   public S41PacketServerDifficulty(EnumDifficulty difficultyIn, boolean lockedIn) {
/* 19 */     this.difficulty = difficultyIn;
/* 20 */     this.difficultyLocked = lockedIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 28 */     handler.handleServerDifficulty(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 35 */     this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 42 */     buf.writeByte(this.difficulty.getDifficultyId());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDifficultyLocked() {
/* 47 */     return this.difficultyLocked;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumDifficulty getDifficulty() {
/* 52 */     return this.difficulty;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S41PacketServerDifficulty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */