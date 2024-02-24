/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class S0FPacketSpawnMob
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityId;
/*     */   private int type;
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   private int velocityX;
/*     */   private int velocityY;
/*     */   private int velocityZ;
/*     */   private byte yaw;
/*     */   private byte pitch;
/*     */   private byte headPitch;
/*     */   private DataWatcher field_149043_l;
/*     */   private List<DataWatcher.WatchableObject> watcher;
/*     */   
/*     */   public S0FPacketSpawnMob() {}
/*     */   
/*     */   public S0FPacketSpawnMob(EntityLivingBase entityIn) {
/*  36 */     this.entityId = entityIn.getEntityId();
/*  37 */     this.type = (byte)EntityList.getEntityID((Entity)entityIn);
/*  38 */     this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
/*  39 */     this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
/*  40 */     this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*  41 */     this.yaw = (byte)(int)(entityIn.rotationYaw * 256.0F / 360.0F);
/*  42 */     this.pitch = (byte)(int)(entityIn.rotationPitch * 256.0F / 360.0F);
/*  43 */     this.headPitch = (byte)(int)(entityIn.rotationYawHead * 256.0F / 360.0F);
/*  44 */     double d0 = 3.9D;
/*  45 */     double d1 = entityIn.motionX;
/*  46 */     double d2 = entityIn.motionY;
/*  47 */     double d3 = entityIn.motionZ;
/*     */     
/*  49 */     if (d1 < -d0)
/*     */     {
/*  51 */       d1 = -d0;
/*     */     }
/*     */     
/*  54 */     if (d2 < -d0)
/*     */     {
/*  56 */       d2 = -d0;
/*     */     }
/*     */     
/*  59 */     if (d3 < -d0)
/*     */     {
/*  61 */       d3 = -d0;
/*     */     }
/*     */     
/*  64 */     if (d1 > d0)
/*     */     {
/*  66 */       d1 = d0;
/*     */     }
/*     */     
/*  69 */     if (d2 > d0)
/*     */     {
/*  71 */       d2 = d0;
/*     */     }
/*     */     
/*  74 */     if (d3 > d0)
/*     */     {
/*  76 */       d3 = d0;
/*     */     }
/*     */     
/*  79 */     this.velocityX = (int)(d1 * 8000.0D);
/*  80 */     this.velocityY = (int)(d2 * 8000.0D);
/*  81 */     this.velocityZ = (int)(d3 * 8000.0D);
/*  82 */     this.field_149043_l = entityIn.getDataWatcher();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  90 */     this.entityId = buf.readVarIntFromBuffer();
/*  91 */     this.type = buf.readByte() & 0xFF;
/*  92 */     this.x = buf.readInt();
/*  93 */     this.y = buf.readInt();
/*  94 */     this.z = buf.readInt();
/*  95 */     this.yaw = buf.readByte();
/*  96 */     this.pitch = buf.readByte();
/*  97 */     this.headPitch = buf.readByte();
/*  98 */     this.velocityX = buf.readShort();
/*  99 */     this.velocityY = buf.readShort();
/* 100 */     this.velocityZ = buf.readShort();
/* 101 */     this.watcher = DataWatcher.readWatchedListFromPacketBuffer(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 109 */     buf.writeVarIntToBuffer(this.entityId);
/* 110 */     buf.writeByte(this.type & 0xFF);
/* 111 */     buf.writeInt(this.x);
/* 112 */     buf.writeInt(this.y);
/* 113 */     buf.writeInt(this.z);
/* 114 */     buf.writeByte(this.yaw);
/* 115 */     buf.writeByte(this.pitch);
/* 116 */     buf.writeByte(this.headPitch);
/* 117 */     buf.writeShort(this.velocityX);
/* 118 */     buf.writeShort(this.velocityY);
/* 119 */     buf.writeShort(this.velocityZ);
/* 120 */     this.field_149043_l.writeTo(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 128 */     handler.handleSpawnMob(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<DataWatcher.WatchableObject> func_149027_c() {
/* 133 */     if (this.watcher == null)
/*     */     {
/* 135 */       this.watcher = this.field_149043_l.getAllWatched();
/*     */     }
/*     */     
/* 138 */     return this.watcher;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/* 143 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityType() {
/* 148 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/* 153 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/* 158 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/* 163 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVelocityX() {
/* 168 */     return this.velocityX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVelocityY() {
/* 173 */     return this.velocityY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVelocityZ() {
/* 178 */     return this.velocityZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getYaw() {
/* 183 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPitch() {
/* 188 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getHeadPitch() {
/* 193 */     return this.headPitch;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S0FPacketSpawnMob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */