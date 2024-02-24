/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S2APacketParticles
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private EnumParticleTypes particleType;
/*     */   private float xCoord;
/*     */   private float yCoord;
/*     */   private float zCoord;
/*     */   private float xOffset;
/*     */   private float yOffset;
/*     */   private float zOffset;
/*     */   private float particleSpeed;
/*     */   private int particleCount;
/*     */   private boolean longDistance;
/*     */   private int[] particleArguments;
/*     */   
/*     */   public S2APacketParticles() {}
/*     */   
/*     */   public S2APacketParticles(EnumParticleTypes particleTypeIn, boolean longDistanceIn, float x, float y, float z, float xOffsetIn, float yOffset, float zOffset, float particleSpeedIn, int particleCountIn, int... particleArgumentsIn) {
/*  32 */     this.particleType = particleTypeIn;
/*  33 */     this.longDistance = longDistanceIn;
/*  34 */     this.xCoord = x;
/*  35 */     this.yCoord = y;
/*  36 */     this.zCoord = z;
/*  37 */     this.xOffset = xOffsetIn;
/*  38 */     this.yOffset = yOffset;
/*  39 */     this.zOffset = zOffset;
/*  40 */     this.particleSpeed = particleSpeedIn;
/*  41 */     this.particleCount = particleCountIn;
/*  42 */     this.particleArguments = particleArgumentsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  49 */     this.particleType = EnumParticleTypes.getParticleFromId(buf.readInt());
/*     */     
/*  51 */     if (this.particleType == null)
/*     */     {
/*  53 */       this.particleType = EnumParticleTypes.BARRIER;
/*     */     }
/*     */     
/*  56 */     this.longDistance = buf.readBoolean();
/*  57 */     this.xCoord = buf.readFloat();
/*  58 */     this.yCoord = buf.readFloat();
/*  59 */     this.zCoord = buf.readFloat();
/*  60 */     this.xOffset = buf.readFloat();
/*  61 */     this.yOffset = buf.readFloat();
/*  62 */     this.zOffset = buf.readFloat();
/*  63 */     this.particleSpeed = buf.readFloat();
/*  64 */     this.particleCount = buf.readInt();
/*  65 */     int i = this.particleType.getArgumentCount();
/*  66 */     this.particleArguments = new int[i];
/*     */     
/*  68 */     for (int j = 0; j < i; j++)
/*     */     {
/*  70 */       this.particleArguments[j] = buf.readVarIntFromBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  78 */     buf.writeInt(this.particleType.getParticleID());
/*  79 */     buf.writeBoolean(this.longDistance);
/*  80 */     buf.writeFloat(this.xCoord);
/*  81 */     buf.writeFloat(this.yCoord);
/*  82 */     buf.writeFloat(this.zCoord);
/*  83 */     buf.writeFloat(this.xOffset);
/*  84 */     buf.writeFloat(this.yOffset);
/*  85 */     buf.writeFloat(this.zOffset);
/*  86 */     buf.writeFloat(this.particleSpeed);
/*  87 */     buf.writeInt(this.particleCount);
/*  88 */     int i = this.particleType.getArgumentCount();
/*     */     
/*  90 */     for (int j = 0; j < i; j++)
/*     */     {
/*  92 */       buf.writeVarIntToBuffer(this.particleArguments[j]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumParticleTypes getParticleType() {
/*  98 */     return this.particleType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLongDistance() {
/* 103 */     return this.longDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXCoordinate() {
/* 111 */     return this.xCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYCoordinate() {
/* 119 */     return this.yCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZCoordinate() {
/* 127 */     return this.zCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getXOffset() {
/* 135 */     return this.xOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getYOffset() {
/* 143 */     return this.yOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getZOffset() {
/* 151 */     return this.zOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getParticleSpeed() {
/* 159 */     return this.particleSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParticleCount() {
/* 167 */     return this.particleCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getParticleArgs() {
/* 176 */     return this.particleArguments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 184 */     handler.handleParticles(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S2APacketParticles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */