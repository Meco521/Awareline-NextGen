/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ public class C03PacketPlayer
/*     */   implements Packet<INetHandlerPlayServer> {
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public float yaw;
/*     */   public float pitch;
/*     */   public boolean onGround;
/*     */   public boolean moving;
/*     */   public boolean rotating;
/*     */   
/*     */   public C03PacketPlayer() {}
/*     */   
/*     */   public C03PacketPlayer(boolean isOnGround) {
/*  23 */     this.onGround = isOnGround;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  30 */     handler.processPlayer(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  37 */     this.onGround = (buf.readUnsignedByte() != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  44 */     buf.writeByte(this.onGround ? 1 : 0);
/*     */   }
/*     */   
/*     */   public double getPositionX() {
/*  48 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getPositionY() {
/*  52 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getPositionZ() {
/*  56 */     return this.z;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  60 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  64 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/*  68 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public boolean isMoving() {
/*  72 */     return this.moving;
/*     */   }
/*     */   
/*     */   public boolean getRotating() {
/*  76 */     return this.rotating;
/*     */   }
/*     */   
/*     */   public void setMoving(boolean isMoving) {
/*  80 */     this.moving = isMoving;
/*     */   }
/*     */   
/*     */   public void setOnGround(boolean onGround) {
/*  84 */     this.onGround = onGround;
/*     */   }
/*     */   
/*     */   public boolean isRotating() {
/*  88 */     return this.rotating;
/*     */   }
/*     */   
/*     */   public double getX() {
/*  92 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  96 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 100 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setY(double v) {
/* 104 */     this.y = v;
/*     */   }
/*     */   
/*     */   public static class C04PacketPlayerPosition extends C03PacketPlayer {
/*     */     public C04PacketPlayerPosition() {
/* 109 */       this.moving = true;
/*     */     }
/*     */     
/*     */     public C04PacketPlayerPosition(double posX, double posY, double posZ, boolean isOnGround) {
/* 113 */       this.x = posX;
/* 114 */       this.y = posY;
/* 115 */       this.z = posZ;
/* 116 */       this.onGround = isOnGround;
/* 117 */       this.moving = true;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 121 */       this.x = buf.readDouble();
/* 122 */       this.y = buf.readDouble();
/* 123 */       this.z = buf.readDouble();
/* 124 */       super.readPacketData(buf);
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 128 */       buf.writeDouble(this.x);
/* 129 */       buf.writeDouble(this.y);
/* 130 */       buf.writeDouble(this.z);
/* 131 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C05PacketPlayerLook extends C03PacketPlayer {
/*     */     public C05PacketPlayerLook() {
/* 137 */       this.rotating = true;
/*     */     }
/*     */     
/*     */     public C05PacketPlayerLook(float playerYaw, float playerPitch, boolean isOnGround) {
/* 141 */       this.yaw = playerYaw;
/* 142 */       this.pitch = playerPitch;
/* 143 */       this.onGround = isOnGround;
/* 144 */       this.rotating = true;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 148 */       this.yaw = buf.readFloat();
/* 149 */       this.pitch = buf.readFloat();
/* 150 */       super.readPacketData(buf);
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 154 */       buf.writeFloat(this.yaw);
/* 155 */       buf.writeFloat(this.pitch);
/* 156 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C06PacketPlayerPosLook extends C03PacketPlayer {
/*     */     public C06PacketPlayerPosLook() {
/* 162 */       this.moving = true;
/* 163 */       this.rotating = true;
/*     */     }
/*     */     
/*     */     public C06PacketPlayerPosLook(double playerX, double playerY, double playerZ, float playerYaw, float playerPitch, boolean playerIsOnGround) {
/* 167 */       this.x = playerX;
/* 168 */       this.y = playerY;
/* 169 */       this.z = playerZ;
/* 170 */       this.yaw = playerYaw;
/* 171 */       this.pitch = playerPitch;
/* 172 */       this.onGround = playerIsOnGround;
/* 173 */       this.rotating = true;
/* 174 */       this.moving = true;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 178 */       this.x = buf.readDouble();
/* 179 */       this.y = buf.readDouble();
/* 180 */       this.z = buf.readDouble();
/* 181 */       this.yaw = buf.readFloat();
/* 182 */       this.pitch = buf.readFloat();
/* 183 */       super.readPacketData(buf);
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 187 */       buf.writeDouble(this.x);
/* 188 */       buf.writeDouble(this.y);
/* 189 */       buf.writeDouble(this.z);
/* 190 */       buf.writeFloat(this.yaw);
/* 191 */       buf.writeFloat(this.pitch);
/* 192 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C03PacketPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */