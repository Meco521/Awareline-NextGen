/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ import net.minecraft.world.WorldType;
/*    */ 
/*    */ 
/*    */ public class S07PacketRespawn
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int dimensionID;
/*    */   private EnumDifficulty difficulty;
/*    */   private WorldSettings.GameType gameType;
/*    */   private WorldType worldType;
/*    */   
/*    */   public S07PacketRespawn() {}
/*    */   
/*    */   public S07PacketRespawn(int dimensionIDIn, EnumDifficulty difficultyIn, WorldType worldTypeIn, WorldSettings.GameType gameTypeIn) {
/* 23 */     this.dimensionID = dimensionIDIn;
/* 24 */     this.difficulty = difficultyIn;
/* 25 */     this.gameType = gameTypeIn;
/* 26 */     this.worldType = worldTypeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 34 */     handler.handleRespawn(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 41 */     this.dimensionID = buf.readInt();
/* 42 */     this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
/* 43 */     this.gameType = WorldSettings.GameType.getByID(buf.readUnsignedByte());
/* 44 */     this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));
/*    */     
/* 46 */     if (this.worldType == null)
/*    */     {
/* 48 */       this.worldType = WorldType.DEFAULT;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 56 */     buf.writeInt(this.dimensionID);
/* 57 */     buf.writeByte(this.difficulty.getDifficultyId());
/* 58 */     buf.writeByte(this.gameType.getID());
/* 59 */     buf.writeString(this.worldType.getWorldTypeName());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDimensionID() {
/* 64 */     return this.dimensionID;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumDifficulty getDifficulty() {
/* 69 */     return this.difficulty;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldSettings.GameType getGameType() {
/* 74 */     return this.gameType;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldType getWorldType() {
/* 79 */     return this.worldType;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S07PacketRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */