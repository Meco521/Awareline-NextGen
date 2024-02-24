/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class S18PacketEntityTeleport
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityId;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int posZ;
/*     */   public byte yaw;
/*     */   public byte pitch;
/*     */   private boolean onGround;
/*     */   
/*     */   public S18PacketEntityTeleport() {}
/*     */   
/*     */   public S18PacketEntityTeleport(Entity entityIn) {
/*  25 */     this.entityId = entityIn.getEntityId();
/*  26 */     this.posX = MathHelper.floor_double(entityIn.posX * 32.0D);
/*  27 */     this.posY = MathHelper.floor_double(entityIn.posY * 32.0D);
/*  28 */     this.posZ = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*  29 */     this.yaw = (byte)(int)(entityIn.rotationYaw * 256.0F / 360.0F);
/*  30 */     this.pitch = (byte)(int)(entityIn.rotationPitch * 256.0F / 360.0F);
/*  31 */     this.onGround = entityIn.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public S18PacketEntityTeleport(int entityIdIn, int posXIn, int posYIn, int posZIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
/*  36 */     this.entityId = entityIdIn;
/*  37 */     this.posX = posXIn;
/*  38 */     this.posY = posYIn;
/*  39 */     this.posZ = posZIn;
/*  40 */     this.yaw = yawIn;
/*  41 */     this.pitch = pitchIn;
/*  42 */     this.onGround = onGroundIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  49 */     this.entityId = buf.readVarIntFromBuffer();
/*  50 */     this.posX = buf.readInt();
/*  51 */     this.posY = buf.readInt();
/*  52 */     this.posZ = buf.readInt();
/*  53 */     this.yaw = buf.readByte();
/*  54 */     this.pitch = buf.readByte();
/*  55 */     this.onGround = buf.readBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  62 */     buf.writeVarIntToBuffer(this.entityId);
/*  63 */     buf.writeInt(this.posX);
/*  64 */     buf.writeInt(this.posY);
/*  65 */     buf.writeInt(this.posZ);
/*  66 */     buf.writeByte(this.yaw);
/*  67 */     buf.writeByte(this.pitch);
/*  68 */     buf.writeBoolean(this.onGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  76 */     handler.handleEntityTeleport(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/*  81 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/*  86 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/*  91 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  96 */     return this.posZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getYaw() {
/* 101 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPitch() {
/* 106 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOnGround() {
/* 111 */     return this.onGround;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S18PacketEntityTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */