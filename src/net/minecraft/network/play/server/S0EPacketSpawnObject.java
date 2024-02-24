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
/*     */ public class S0EPacketSpawnObject
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityId;
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   private int speedX;
/*     */   private int speedY;
/*     */   private int speedZ;
/*     */   private int pitch;
/*     */   private int yaw;
/*     */   private int type;
/*     */   private int field_149020_k;
/*     */   
/*     */   public S0EPacketSpawnObject() {}
/*     */   
/*     */   public S0EPacketSpawnObject(Entity entityIn, int typeIn) {
/*  29 */     this(entityIn, typeIn, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public S0EPacketSpawnObject(Entity entityIn, int typeIn, int p_i45166_3_) {
/*  34 */     this.entityId = entityIn.getEntityId();
/*  35 */     this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
/*  36 */     this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
/*  37 */     this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*  38 */     this.pitch = MathHelper.floor_float(entityIn.rotationPitch * 256.0F / 360.0F);
/*  39 */     this.yaw = MathHelper.floor_float(entityIn.rotationYaw * 256.0F / 360.0F);
/*  40 */     this.type = typeIn;
/*  41 */     this.field_149020_k = p_i45166_3_;
/*     */     
/*  43 */     if (p_i45166_3_ > 0) {
/*     */       
/*  45 */       double d0 = entityIn.motionX;
/*  46 */       double d1 = entityIn.motionY;
/*  47 */       double d2 = entityIn.motionZ;
/*  48 */       double d3 = 3.9D;
/*     */       
/*  50 */       if (d0 < -d3)
/*     */       {
/*  52 */         d0 = -d3;
/*     */       }
/*     */       
/*  55 */       if (d1 < -d3)
/*     */       {
/*  57 */         d1 = -d3;
/*     */       }
/*     */       
/*  60 */       if (d2 < -d3)
/*     */       {
/*  62 */         d2 = -d3;
/*     */       }
/*     */       
/*  65 */       if (d0 > d3)
/*     */       {
/*  67 */         d0 = d3;
/*     */       }
/*     */       
/*  70 */       if (d1 > d3)
/*     */       {
/*  72 */         d1 = d3;
/*     */       }
/*     */       
/*  75 */       if (d2 > d3)
/*     */       {
/*  77 */         d2 = d3;
/*     */       }
/*     */       
/*  80 */       this.speedX = (int)(d0 * 8000.0D);
/*  81 */       this.speedY = (int)(d1 * 8000.0D);
/*  82 */       this.speedZ = (int)(d2 * 8000.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  90 */     this.entityId = buf.readVarIntFromBuffer();
/*  91 */     this.type = buf.readByte();
/*  92 */     this.x = buf.readInt();
/*  93 */     this.y = buf.readInt();
/*  94 */     this.z = buf.readInt();
/*  95 */     this.pitch = buf.readByte();
/*  96 */     this.yaw = buf.readByte();
/*  97 */     this.field_149020_k = buf.readInt();
/*     */     
/*  99 */     if (this.field_149020_k > 0) {
/*     */       
/* 101 */       this.speedX = buf.readShort();
/* 102 */       this.speedY = buf.readShort();
/* 103 */       this.speedZ = buf.readShort();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/* 111 */     buf.writeVarIntToBuffer(this.entityId);
/* 112 */     buf.writeByte(this.type);
/* 113 */     buf.writeInt(this.x);
/* 114 */     buf.writeInt(this.y);
/* 115 */     buf.writeInt(this.z);
/* 116 */     buf.writeByte(this.pitch);
/* 117 */     buf.writeByte(this.yaw);
/* 118 */     buf.writeInt(this.field_149020_k);
/*     */     
/* 120 */     if (this.field_149020_k > 0) {
/*     */       
/* 122 */       buf.writeShort(this.speedX);
/* 123 */       buf.writeShort(this.speedY);
/* 124 */       buf.writeShort(this.speedZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 133 */     handler.handleSpawnObject(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/* 138 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/* 143 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/* 148 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/* 153 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeedX() {
/* 158 */     return this.speedX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeedY() {
/* 163 */     return this.speedY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeedZ() {
/* 168 */     return this.speedZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPitch() {
/* 173 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYaw() {
/* 178 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 183 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149009_m() {
/* 188 */     return this.field_149020_k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setX(int newX) {
/* 193 */     this.x = newX;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setY(int newY) {
/* 198 */     this.y = newY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZ(int newZ) {
/* 203 */     this.z = newZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpeedX(int newSpeedX) {
/* 208 */     this.speedX = newSpeedX;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpeedY(int newSpeedY) {
/* 213 */     this.speedY = newSpeedY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpeedZ(int newSpeedZ) {
/* 218 */     this.speedZ = newSpeedZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149002_g(int p_149002_1_) {
/* 223 */     this.field_149020_k = p_149002_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S0EPacketSpawnObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */