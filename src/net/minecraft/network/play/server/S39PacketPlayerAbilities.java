/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ public class S39PacketPlayerAbilities
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private boolean invulnerable;
/*     */   private boolean flying;
/*     */   private boolean allowFlying;
/*     */   private boolean creativeMode;
/*     */   private float flySpeed;
/*     */   private float walkSpeed;
/*     */   
/*     */   public S39PacketPlayerAbilities() {}
/*     */   
/*     */   public S39PacketPlayerAbilities(PlayerCapabilities capabilities) {
/*  23 */     this.invulnerable = capabilities.disableDamage;
/*  24 */     this.flying = capabilities.isFlying;
/*  25 */     this.allowFlying = capabilities.allowFlying;
/*  26 */     this.creativeMode = capabilities.isCreativeMode;
/*  27 */     this.flySpeed = capabilities.getFlySpeed();
/*  28 */     this.walkSpeed = capabilities.getWalkSpeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  35 */     byte b0 = buf.readByte();
/*  36 */     this.invulnerable = ((b0 & 0x1) > 0);
/*  37 */     this.flying = ((b0 & 0x2) > 0);
/*  38 */     this.allowFlying = ((b0 & 0x4) > 0);
/*  39 */     this.creativeMode = ((b0 & 0x8) > 0);
/*  40 */     this.flySpeed = buf.readFloat();
/*  41 */     this.walkSpeed = buf.readFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  48 */     byte b0 = 0;
/*     */     
/*  50 */     if (this.invulnerable)
/*     */     {
/*  52 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     
/*  55 */     if (this.flying)
/*     */     {
/*  57 */       b0 = (byte)(b0 | 0x2);
/*     */     }
/*     */     
/*  60 */     if (this.allowFlying)
/*     */     {
/*  62 */       b0 = (byte)(b0 | 0x4);
/*     */     }
/*     */     
/*  65 */     if (this.creativeMode)
/*     */     {
/*  67 */       b0 = (byte)(b0 | 0x8);
/*     */     }
/*     */     
/*  70 */     buf.writeByte(b0);
/*  71 */     buf.writeFloat(this.flySpeed);
/*  72 */     buf.writeFloat(this.walkSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  80 */     handler.handlePlayerAbilities(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvulnerable() {
/*  85 */     return this.invulnerable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvulnerable(boolean isInvulnerable) {
/*  90 */     this.invulnerable = isInvulnerable;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlying() {
/*  95 */     return this.flying;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFlying(boolean isFlying) {
/* 100 */     this.flying = isFlying;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowFlying() {
/* 105 */     return this.allowFlying;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllowFlying(boolean isAllowFlying) {
/* 110 */     this.allowFlying = isAllowFlying;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCreativeMode() {
/* 115 */     return this.creativeMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCreativeMode(boolean isCreativeMode) {
/* 120 */     this.creativeMode = isCreativeMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFlySpeed() {
/* 125 */     return this.flySpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFlySpeed(float flySpeedIn) {
/* 130 */     this.flySpeed = flySpeedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkSpeed() {
/* 135 */     return this.walkSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWalkSpeed(float walkSpeedIn) {
/* 140 */     this.walkSpeed = walkSpeedIn;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S39PacketPlayerAbilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */