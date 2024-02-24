/*     */ package awareline.main.mod.implement.world.utils;
/*     */ 
/*     */ import awareline.main.InstanceAccess;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotationUtil
/*     */   implements InstanceAccess
/*     */ {
/*     */   public static Vector2f calculate(Vector3d from, Vector3d to) {
/*  21 */     Vector3d diff = to.subtract(from);
/*  22 */     double distance = Math.hypot(diff.getX(), diff.getZ());
/*  23 */     float yaw = (float)(MathHelper.atan2(diff.getZ(), diff.getX()) * 57.2957763671875D) - 90.0F;
/*  24 */     float pitch = (float)-(MathHelper.atan2(diff.getY(), distance) * 57.2957763671875D);
/*  25 */     return new Vector2f(yaw, pitch);
/*     */   }
/*     */   
/*     */   public Vector2f calculate(Entity entity) {
/*  29 */     return calculate(entity.getCustomPositionVector().add(0.0D, Math.max(0.0D, Math.min(mc.thePlayer.posY - entity.posY + mc.thePlayer
/*  30 */               .getEyeHeight(), ((entity.getEntityBoundingBox()).maxY - (entity.getEntityBoundingBox()).minY) * 0.9D)), 0.0D));
/*     */   }
/*     */   
/*     */   public Vector2f calculate(Entity entity, boolean adaptive, double range) {
/*  34 */     Vector2f normalRotations = calculate(entity);
/*  35 */     if (!adaptive || (RayCastUtil.rayCast(normalRotations, range)).typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
/*  36 */       return normalRotations;
/*     */     }
/*     */     double yPercent;
/*  39 */     for (yPercent = 1.0D; yPercent >= 0.0D; yPercent -= 0.25D) {
/*  40 */       double xPercent; for (xPercent = 1.0D; xPercent >= -0.5D; xPercent -= 0.5D) {
/*  41 */         double zPercent; for (zPercent = 1.0D; zPercent >= -0.5D; zPercent -= 0.5D) {
/*  42 */           Vector2f adaptiveRotations = calculate(entity.getCustomPositionVector().add((
/*  43 */                 (entity.getEntityBoundingBox()).maxX - (entity.getEntityBoundingBox()).minX) * xPercent, (
/*  44 */                 (entity.getEntityBoundingBox()).maxY - (entity.getEntityBoundingBox()).minY) * yPercent, (
/*  45 */                 (entity.getEntityBoundingBox()).maxZ - (entity.getEntityBoundingBox()).minZ) * zPercent));
/*     */           
/*  47 */           if ((RayCastUtil.rayCast(adaptiveRotations, range)).typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
/*  48 */             return adaptiveRotations;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  54 */     return normalRotations;
/*     */   }
/*     */   
/*     */   public Vector2f calculate(Vec3 to, EnumFacing enumFacing) {
/*  58 */     return calculate(new Vector3d(to.xCoord, to.yCoord, to.zCoord), enumFacing);
/*     */   }
/*     */   
/*     */   public Vector2f calculate(Vec3 to) {
/*  62 */     return calculate(mc.thePlayer.getCustomPositionVector().add(0.0D, mc.thePlayer.getEyeHeight(), 0.0D), new Vector3d(to.xCoord, to.yCoord, to.zCoord));
/*     */   }
/*     */   
/*     */   public static Vector2f calculate(Vector3d to) {
/*  66 */     return calculate(mc.thePlayer.getCustomPositionVector().add(0.0D, mc.thePlayer.getEyeHeight(), 0.0D), to);
/*     */   }
/*     */   
/*     */   public static Vector2f calculate(Vector3d position, EnumFacing enumFacing) {
/*  70 */     double x = position.getX() + 0.5D;
/*  71 */     double y = position.getY() + 0.5D;
/*  72 */     double z = position.getZ() + 0.5D;
/*     */     
/*  74 */     x += enumFacing.getDirectionVec().getX() * 0.5D;
/*  75 */     y += enumFacing.getDirectionVec().getY() * 0.5D;
/*  76 */     z += enumFacing.getDirectionVec().getZ() * 0.5D;
/*  77 */     return calculate(new Vector3d(x, y, z));
/*     */   }
/*     */   
/*     */   public Vector2f applySensitivityPatch(Vector2f rotation) {
/*  81 */     Vector2f previousRotation = mc.thePlayer.getPreviousRotation();
/*  82 */     float mouseSensitivity = (float)(mc.gameSettings.mouseSensitivity * (1.0D + Math.random() / 1.0E7D) * 0.6000000238418579D + 0.20000000298023224D);
/*  83 */     double multiplier = (mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F) * 0.15D;
/*  84 */     float yaw = previousRotation.x + (float)(Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
/*  85 */     float pitch = previousRotation.y + (float)(Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
/*  86 */     return new Vector2f(yaw, MathHelper.clamp_float(pitch, -90.0F, 90.0F));
/*     */   }
/*     */   
/*     */   public Vector2f applySensitivityPatch(Vector2f rotation, Vector2f previousRotation) {
/*  90 */     float mouseSensitivity = (float)(mc.gameSettings.mouseSensitivity * (1.0D + Math.random() / 1.0E7D) * 0.6000000238418579D + 0.20000000298023224D);
/*  91 */     double multiplier = (mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F) * 0.15D;
/*  92 */     float yaw = previousRotation.x + (float)(Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
/*  93 */     float pitch = previousRotation.y + (float)(Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
/*  94 */     return new Vector2f(yaw, MathHelper.clamp_float(pitch, -90.0F, 90.0F));
/*     */   }
/*     */   
/*     */   public Vector2f relateToPlayerRotation(Vector2f rotation) {
/*  98 */     Vector2f previousRotation = mc.thePlayer.getPreviousRotation();
/*  99 */     float yaw = previousRotation.x + MathHelper.wrapAngleTo180_float(rotation.x - previousRotation.x);
/* 100 */     float pitch = MathHelper.clamp_float(rotation.y, -90.0F, 90.0F);
/* 101 */     return new Vector2f(yaw, pitch);
/*     */   }
/*     */   
/*     */   public Vector2f resetRotation(Vector2f rotation) {
/* 105 */     if (rotation == null) {
/* 106 */       return null;
/*     */     }
/*     */     
/* 109 */     float yaw = rotation.x + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - rotation.x);
/* 110 */     float pitch = mc.thePlayer.rotationPitch;
/* 111 */     return new Vector2f(yaw, pitch);
/*     */   }
/*     */   
/*     */   public Vector2f smooth(Vector2f lastRotation, Vector2f targetRotation, double speed) {
/* 115 */     float yaw = targetRotation.x;
/* 116 */     float pitch = targetRotation.y;
/* 117 */     float lastYaw = lastRotation.x;
/* 118 */     float lastPitch = lastRotation.y;
/*     */     
/* 120 */     if (speed != 0.0D) {
/* 121 */       float rotationSpeed = (float)speed;
/*     */       
/* 123 */       double deltaYaw = MathHelper.wrapAngleTo180_float(targetRotation.x - lastRotation.x);
/* 124 */       double deltaPitch = (pitch - lastPitch);
/*     */       
/* 126 */       double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
/* 127 */       double distributionYaw = Math.abs(deltaYaw / distance);
/* 128 */       double distributionPitch = Math.abs(deltaPitch / distance);
/*     */       
/* 130 */       double maxYaw = rotationSpeed * distributionYaw;
/* 131 */       double maxPitch = rotationSpeed * distributionPitch;
/*     */       
/* 133 */       float moveYaw = (float)Math.max(Math.min(deltaYaw, maxYaw), -maxYaw);
/* 134 */       float movePitch = (float)Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);
/*     */       
/* 136 */       yaw = lastYaw + moveYaw;
/* 137 */       pitch = lastPitch + movePitch;
/*     */     } 
/*     */     
/* 140 */     boolean randomise = (Math.random() > 0.8D);
/*     */     
/* 142 */     for (int i = 1; i <= (int)(2.0D + Math.random() * 2.0D); i++) {
/*     */       
/* 144 */       if (randomise) {
/* 145 */         yaw = (float)(yaw + (Math.random() - 0.5D) / 1.0E8D);
/* 146 */         pitch = (float)(pitch - Math.random() / 2.0E8D);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       Vector2f rotations = new Vector2f(yaw, pitch);
/* 153 */       Vector2f fixedRotations = applySensitivityPatch(rotations);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       yaw = fixedRotations.x;
/* 159 */       pitch = Math.max(-90.0F, Math.min(90.0F, fixedRotations.y));
/*     */     } 
/*     */     
/* 162 */     return new Vector2f(yaw, pitch);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */