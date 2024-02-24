/*     */ package awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce;
/*     */ 
/*     */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public final class Rotation {
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   
/*     */   public void toPlayer(EntityPlayer player) {
/*  15 */     float var2 = this.yaw;
/*  16 */     if (!Float.isNaN(var2)) {
/*  17 */       var2 = this.pitch;
/*  18 */       if (!Float.isNaN(var2)) {
/*  19 */         fixedSensitivity((Minecraft.getMinecraft()).gameSettings.mouseSensitivity);
/*  20 */         player.rotationYaw = this.yaw;
/*  21 */         player.rotationPitch = this.pitch;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fixedSensitivity(float sensitivity) {
/*  27 */     float f = sensitivity * 0.6F + 0.2F;
/*  28 */     float gcd = f * f * f * 1.2F;
/*  29 */     this.yaw -= this.yaw % gcd;
/*  30 */     this.pitch -= this.pitch % gcd;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  34 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public void setYaw(float var1) {
/*  38 */     this.yaw = var1;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  42 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public void setPitch(float var1) {
/*  46 */     this.pitch = var1;
/*     */   }
/*     */   
/*     */   public Rotation(float yaw, float pitch) {
/*  50 */     this.yaw = yaw;
/*  51 */     this.pitch = pitch;
/*     */   }
/*     */   
/*     */   public Rotation copy(float yaw, float pitch) {
/*  55 */     return new Rotation(yaw, pitch);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyStrafeToPlayer(EventStrafe event) {
/*  60 */     EntityPlayerSP player = (Minecraft.getMinecraft()).thePlayer;
/*  61 */     int dif = (int)((MathHelper.wrapAngleTo180_float(player.rotationYaw - this.yaw - 23.5F - 135.0F) + 180.0F) / 45.0F);
/*     */ 
/*     */ 
/*     */     
/*  65 */     float yaw = this.yaw;
/*     */     
/*  67 */     float strafe = event.getStrafe();
/*  68 */     float forward = event.getForward();
/*  69 */     float friction = event.getFriction();
/*     */     
/*  71 */     float calcForward = 0.0F;
/*  72 */     float calcStrafe = 0.0F;
/*     */     
/*  74 */     switch (dif) {
/*     */       case 0:
/*  76 */         calcForward = forward;
/*  77 */         calcStrafe = strafe;
/*     */         break;
/*     */       
/*     */       case 1:
/*  81 */         calcForward += forward;
/*  82 */         calcStrafe -= forward;
/*  83 */         calcForward += strafe;
/*  84 */         calcStrafe += strafe;
/*     */         break;
/*     */       
/*     */       case 2:
/*  88 */         calcForward = strafe;
/*  89 */         calcStrafe = -forward;
/*     */         break;
/*     */       
/*     */       case 3:
/*  93 */         calcForward -= forward;
/*  94 */         calcStrafe -= forward;
/*  95 */         calcForward += strafe;
/*  96 */         calcStrafe -= strafe;
/*     */         break;
/*     */       
/*     */       case 4:
/* 100 */         calcForward = -forward;
/* 101 */         calcStrafe = -strafe;
/*     */         break;
/*     */       
/*     */       case 5:
/* 105 */         calcForward -= forward;
/* 106 */         calcStrafe += forward;
/* 107 */         calcForward -= strafe;
/* 108 */         calcStrafe -= strafe;
/*     */         break;
/*     */       
/*     */       case 6:
/* 112 */         calcForward = -strafe;
/* 113 */         calcStrafe = forward;
/*     */         break;
/*     */       
/*     */       case 7:
/* 117 */         calcForward += forward;
/* 118 */         calcStrafe += forward;
/* 119 */         calcForward -= strafe;
/* 120 */         calcStrafe += strafe;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 125 */     if (calcForward > 1.0F || (calcForward < 0.9F && calcForward > 0.3F) || calcForward < -1.0F || (calcForward > -0.9F && calcForward < -0.3F)) {
/* 126 */       calcForward *= 0.5F;
/*     */     }
/*     */     
/* 129 */     if (calcStrafe > 1.0F || (calcStrafe < 0.9F && calcStrafe > 0.3F) || calcStrafe < -1.0F || (calcStrafe > -0.9F && calcStrafe < -0.3F)) {
/* 130 */       calcStrafe *= 0.5F;
/*     */     }
/*     */     
/* 133 */     float d = calcStrafe * calcStrafe + calcForward * calcForward;
/*     */     
/* 135 */     if (d >= 1.0E-4F) {
/* 136 */       d = MathHelper.sqrt_float(d);
/* 137 */       if (d < 1.0F) d = 1.0F; 
/* 138 */       d = friction / d;
/* 139 */       calcStrafe *= d;
/* 140 */       calcForward *= d;
/* 141 */       float yawSin = MathHelper.sin((float)(yaw * Math.PI / 180.0D));
/* 142 */       float yawCos = MathHelper.cos((float)(yaw * Math.PI / 180.0D));
/* 143 */       player.motionX += (calcStrafe * yawCos - calcForward * yawSin);
/* 144 */       player.motionZ += (calcForward * yawCos + calcStrafe * yawSin);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void applyStrafeToPlayerForKillAura(EventStrafe event) {
/* 149 */     KillAura killAura = KillAura.getInstance;
/* 150 */     EntityPlayerSP player = (Minecraft.getMinecraft()).thePlayer;
/* 151 */     int dif = (int)((MathHelper.wrapAngleTo180_float(player.rotationYaw - killAura.yaw - 23.5F - 135.0F) + 180.0F) / 45.0F);
/*     */ 
/*     */ 
/*     */     
/* 155 */     float yaw = killAura.yaw;
/*     */     
/* 157 */     float strafe = event.getStrafe();
/* 158 */     float forward = event.getForward();
/* 159 */     float friction = event.getFriction();
/*     */     
/* 161 */     float calcForward = 0.0F;
/* 162 */     float calcStrafe = 0.0F;
/*     */     
/* 164 */     switch (dif) {
/*     */       case 0:
/* 166 */         calcForward = forward;
/* 167 */         calcStrafe = strafe;
/*     */         break;
/*     */       
/*     */       case 1:
/* 171 */         calcForward += forward;
/* 172 */         calcStrafe -= forward;
/* 173 */         calcForward += strafe;
/* 174 */         calcStrafe += strafe;
/*     */         break;
/*     */       
/*     */       case 2:
/* 178 */         calcForward = strafe;
/* 179 */         calcStrafe = -forward;
/*     */         break;
/*     */       
/*     */       case 3:
/* 183 */         calcForward -= forward;
/* 184 */         calcStrafe -= forward;
/* 185 */         calcForward += strafe;
/* 186 */         calcStrafe -= strafe;
/*     */         break;
/*     */       
/*     */       case 4:
/* 190 */         calcForward = -forward;
/* 191 */         calcStrafe = -strafe;
/*     */         break;
/*     */       
/*     */       case 5:
/* 195 */         calcForward -= forward;
/* 196 */         calcStrafe += forward;
/* 197 */         calcForward -= strafe;
/* 198 */         calcStrafe -= strafe;
/*     */         break;
/*     */       
/*     */       case 6:
/* 202 */         calcForward = -strafe;
/* 203 */         calcStrafe = forward;
/*     */         break;
/*     */       
/*     */       case 7:
/* 207 */         calcForward += forward;
/* 208 */         calcStrafe += forward;
/* 209 */         calcForward -= strafe;
/* 210 */         calcStrafe += strafe;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 215 */     if (calcForward > 1.0F || (calcForward < 0.9F && calcForward > 0.3F) || calcForward < -1.0F || (calcForward > -0.9F && calcForward < -0.3F)) {
/* 216 */       calcForward *= 0.5F;
/*     */     }
/*     */     
/* 219 */     if (calcStrafe > 1.0F || (calcStrafe < 0.9F && calcStrafe > 0.3F) || calcStrafe < -1.0F || (calcStrafe > -0.9F && calcStrafe < -0.3F)) {
/* 220 */       calcStrafe *= 0.5F;
/*     */     }
/*     */     
/* 223 */     float d = calcStrafe * calcStrafe + calcForward * calcForward;
/*     */     
/* 225 */     if (d >= 1.0E-4F) {
/* 226 */       d = MathHelper.sqrt_float(d);
/* 227 */       if (d < 1.0F) d = 1.0F; 
/* 228 */       d = friction / d;
/* 229 */       calcStrafe *= d;
/* 230 */       calcForward *= d;
/* 231 */       float yawSin = MathHelper.sin((float)(yaw * Math.PI / 180.0D));
/* 232 */       float yawCos = MathHelper.cos((float)(yaw * Math.PI / 180.0D));
/* 233 */       player.motionX += (calcStrafe * yawCos - calcForward * yawSin);
/* 234 */       player.motionZ += (calcForward * yawCos + calcStrafe * yawSin);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 239 */     return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\liquidbounce\Rotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */