/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S14PacketEntity
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   protected int entityId;
/*     */   protected byte posX;
/*     */   protected byte posY;
/*     */   protected byte posZ;
/*     */   protected byte yaw;
/*     */   protected byte pitch;
/*     */   protected boolean onGround;
/*     */   protected boolean field_149069_g;
/*     */   
/*     */   public S14PacketEntity() {}
/*     */   
/*     */   public S14PacketEntity(int entityIdIn) {
/*  28 */     this.entityId = entityIdIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  36 */     this.entityId = buf.readVarIntFromBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  44 */     buf.writeVarIntToBuffer(this.entityId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  52 */     handler.handleEntityMovement(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  57 */     return "Entity_" + super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getEntity(World worldIn) {
/*  62 */     return worldIn.getEntityByID(this.entityId);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte func_149062_c() {
/*  67 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte func_149061_d() {
/*  72 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte func_149064_e() {
/*  77 */     return this.posZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte func_149066_f() {
/*  82 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte func_149063_g() {
/*  87 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149060_h() {
/*  92 */     return this.field_149069_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOnGround() {
/*  97 */     return this.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class S15PacketEntityRelMove
/*     */     extends S14PacketEntity
/*     */   {
/*     */     public S15PacketEntityRelMove() {}
/*     */ 
/*     */     
/*     */     public S15PacketEntityRelMove(int entityIdIn, byte x, byte y, byte z, boolean onGroundIn) {
/* 108 */       super(entityIdIn);
/* 109 */       this.posX = x;
/* 110 */       this.posY = y;
/* 111 */       this.posZ = z;
/* 112 */       this.onGround = onGroundIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 117 */       super.readPacketData(buf);
/* 118 */       this.posX = buf.readByte();
/* 119 */       this.posY = buf.readByte();
/* 120 */       this.posZ = buf.readByte();
/* 121 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 126 */       super.writePacketData(buf);
/* 127 */       buf.writeByte(this.posX);
/* 128 */       buf.writeByte(this.posY);
/* 129 */       buf.writeByte(this.posZ);
/* 130 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class S16PacketEntityLook
/*     */     extends S14PacketEntity
/*     */   {
/*     */     public S16PacketEntityLook() {
/* 138 */       this.field_149069_g = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public S16PacketEntityLook(int entityIdIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
/* 143 */       super(entityIdIn);
/* 144 */       this.yaw = yawIn;
/* 145 */       this.pitch = pitchIn;
/* 146 */       this.field_149069_g = true;
/* 147 */       this.onGround = onGroundIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 152 */       super.readPacketData(buf);
/* 153 */       this.yaw = buf.readByte();
/* 154 */       this.pitch = buf.readByte();
/* 155 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 160 */       super.writePacketData(buf);
/* 161 */       buf.writeByte(this.yaw);
/* 162 */       buf.writeByte(this.pitch);
/* 163 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class S17PacketEntityLookMove
/*     */     extends S14PacketEntity
/*     */   {
/*     */     public S17PacketEntityLookMove() {
/* 171 */       this.field_149069_g = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public S17PacketEntityLookMove(int p_i45973_1_, byte p_i45973_2_, byte p_i45973_3_, byte p_i45973_4_, byte p_i45973_5_, byte p_i45973_6_, boolean p_i45973_7_) {
/* 176 */       super(p_i45973_1_);
/* 177 */       this.posX = p_i45973_2_;
/* 178 */       this.posY = p_i45973_3_;
/* 179 */       this.posZ = p_i45973_4_;
/* 180 */       this.yaw = p_i45973_5_;
/* 181 */       this.pitch = p_i45973_6_;
/* 182 */       this.onGround = p_i45973_7_;
/* 183 */       this.field_149069_g = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 188 */       super.readPacketData(buf);
/* 189 */       this.posX = buf.readByte();
/* 190 */       this.posY = buf.readByte();
/* 191 */       this.posZ = buf.readByte();
/* 192 */       this.yaw = buf.readByte();
/* 193 */       this.pitch = buf.readByte();
/* 194 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 199 */       super.writePacketData(buf);
/* 200 */       buf.writeByte(this.posX);
/* 201 */       buf.writeByte(this.posY);
/* 202 */       buf.writeByte(this.posZ);
/* 203 */       buf.writeByte(this.yaw);
/* 204 */       buf.writeByte(this.pitch);
/* 205 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S14PacketEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */