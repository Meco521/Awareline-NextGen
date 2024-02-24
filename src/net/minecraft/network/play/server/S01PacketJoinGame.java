/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ 
/*     */ public class S01PacketJoinGame
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityId;
/*     */   private boolean hardcoreMode;
/*     */   private WorldSettings.GameType gameType;
/*     */   private int dimension;
/*     */   private EnumDifficulty difficulty;
/*     */   private int maxPlayers;
/*     */   private WorldType worldType;
/*     */   private boolean reducedDebugInfo;
/*     */   
/*     */   public S01PacketJoinGame() {}
/*     */   
/*     */   public S01PacketJoinGame(int entityIdIn, WorldSettings.GameType gameTypeIn, boolean hardcoreModeIn, int dimensionIn, EnumDifficulty difficultyIn, int maxPlayersIn, WorldType worldTypeIn, boolean reducedDebugInfoIn) {
/*  27 */     this.entityId = entityIdIn;
/*  28 */     this.dimension = dimensionIn;
/*  29 */     this.difficulty = difficultyIn;
/*  30 */     this.gameType = gameTypeIn;
/*  31 */     this.maxPlayers = maxPlayersIn;
/*  32 */     this.hardcoreMode = hardcoreModeIn;
/*  33 */     this.worldType = worldTypeIn;
/*  34 */     this.reducedDebugInfo = reducedDebugInfoIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  41 */     this.entityId = buf.readInt();
/*  42 */     int i = buf.readUnsignedByte();
/*  43 */     this.hardcoreMode = ((i & 0x8) == 8);
/*  44 */     i &= 0xFFFFFFF7;
/*  45 */     this.gameType = WorldSettings.GameType.getByID(i);
/*  46 */     this.dimension = buf.readByte();
/*  47 */     this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
/*  48 */     this.maxPlayers = buf.readUnsignedByte();
/*  49 */     this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));
/*     */     
/*  51 */     if (this.worldType == null)
/*     */     {
/*  53 */       this.worldType = WorldType.DEFAULT;
/*     */     }
/*     */     
/*  56 */     this.reducedDebugInfo = buf.readBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  63 */     buf.writeInt(this.entityId);
/*  64 */     int i = this.gameType.getID();
/*     */     
/*  66 */     if (this.hardcoreMode)
/*     */     {
/*  68 */       i |= 0x8;
/*     */     }
/*     */     
/*  71 */     buf.writeByte(i);
/*  72 */     buf.writeByte(this.dimension);
/*  73 */     buf.writeByte(this.difficulty.getDifficultyId());
/*  74 */     buf.writeByte(this.maxPlayers);
/*  75 */     buf.writeString(this.worldType.getWorldTypeName());
/*  76 */     buf.writeBoolean(this.reducedDebugInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  84 */     handler.handleJoinGame(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/*  89 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHardcoreMode() {
/*  94 */     return this.hardcoreMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/*  99 */     return this.gameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 104 */     return this.dimension;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 109 */     return this.difficulty;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPlayers() {
/* 114 */     return this.maxPlayers;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 119 */     return this.worldType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReducedDebugInfo() {
/* 124 */     return this.reducedDebugInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S01PacketJoinGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */