/*     */ package awareline.main.utility.math;
/*     */ import awareline.main.event.events.world.EventRotation;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.VecRotation;
/*     */ import awareline.main.mod.implement.visual.sucks.ParticlesUtils.Location;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.Utils;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public class RotationUtil implements Utils {
/*     */   public static float[] faceTarget(Entity target, float p_706252, float p_706253) {
/*  27 */     double var6, var4 = target.posX - mc.thePlayer.posX;
/*  28 */     double var8 = target.posZ - mc.thePlayer.posZ;
/*  29 */     if (target instanceof EntityLivingBase) {
/*  30 */       var6 = target.posY + target.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } else {
/*  32 */       var6 = ((target.getEntityBoundingBox()).minY + (target.getEntityBoundingBox()).maxY) / 2.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } 
/*  34 */     double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
/*  35 */     float var12 = (float)(Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
/*  36 */     float var13 = (float)(-Math.atan2(var6 - ((target instanceof EntityPlayer) ? 0.25D : 0.0D), var14) * 180.0D / Math.PI);
/*  37 */     float pitch = changeRotation(mc.thePlayer.rotationPitch, var13, p_706253);
/*  38 */     float yaw = changeRotation(mc.thePlayer.rotationYaw, var12, p_706252);
/*  39 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static Rotation convert(float[] rot) {
/*  43 */     return new Rotation(rot[0], rot[1]);
/*     */   }
/*     */   
/*     */   public static float[] convertBack(Rotation rotation) {
/*  47 */     return new float[] { rotation.getYaw(), rotation.getPitch() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity raycastEntity(double range, Rotation rotation, IEntityFilter entityFilter) {
/*  55 */     return raycastEntity(range, rotation.getYaw(), rotation.getPitch(), entityFilter);
/*     */   }
/*     */   
/*     */   private static Entity raycastEntity(double range, float yaw, float pitch, IEntityFilter entityFilter) {
/*  59 */     Entity renderViewEntity = mc.getRenderViewEntity();
/*  60 */     if (renderViewEntity != null && mc.theWorld != null) {
/*  61 */       double blockReachDistance = range;
/*  62 */       Vec3 eyePosition = renderViewEntity.getPositionEyes(1.0F);
/*  63 */       float yawCos = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/*  64 */       float yawSin = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/*  65 */       float pitchCos = -MathHelper.cos(-pitch * 0.017453292F);
/*  66 */       float pitchSin = MathHelper.sin(-pitch * 0.017453292F);
/*  67 */       Vec3 entityLook = new Vec3((yawSin * pitchCos), pitchSin, (yawCos * pitchCos));
/*  68 */       Vec3 vector = eyePosition.addVector(entityLook.xCoord * range, entityLook.yCoord * range, entityLook.zCoord * range);
/*  69 */       List entityList = mc.theWorld.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook.xCoord * range, entityLook.yCoord * range, entityLook.zCoord * range).expand(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
/*  70 */       Entity pointedEntity = null;
/*  71 */       Iterator<Entity> var17 = entityList.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/*  77 */         if (!var17.hasNext()) {
/*  78 */           return pointedEntity;
/*     */         }
/*     */         
/*  81 */         Entity entity = var17.next();
/*  82 */         if (!entityFilter.canRaycast(entity)) {
/*     */           
/*  84 */           float collisionBorderSize = entity.getCollisionBorderSize();
/*  85 */           AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
/*  86 */           MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);
/*  87 */           if (axisAlignedBB.isVecInside(eyePosition)) {
/*  88 */             if (blockReachDistance >= 0.0D) {
/*  89 */               pointedEntity = entity;
/*  90 */               blockReachDistance = 0.0D;
/*     */             }  continue;
/*  92 */           }  if (movingObjectPosition != null) {
/*  93 */             double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);
/*  94 */             if (eyeDistance < blockReachDistance || blockReachDistance == 0.0D) {
/*  95 */               if (entity == renderViewEntity.ridingEntity && !renderViewEntity.isRiding()) {
/*  96 */                 if (blockReachDistance == 0.0D)
/*  97 */                   pointedEntity = entity; 
/*     */                 continue;
/*     */               } 
/* 100 */               pointedEntity = entity;
/* 101 */               blockReachDistance = eyeDistance;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFaced(Entity targetEntity, double blockReachDistance, Rotation rotation) {
/* 113 */     return (raycastEntity(blockReachDistance, rotation, entity -> (entity == targetEntity)) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] teleportRot(Entity entity, Entity target, float p_706252, float p_706253) {
/* 118 */     double y, xDist = target.posX - entity.posX;
/* 119 */     double zDist = target.posZ - entity.posZ;
/* 120 */     if (target instanceof EntityLivingBase) {
/* 121 */       y = target.posY + target.getEyeHeight() - entity.posY + entity.getEyeHeight();
/*     */     } else {
/* 123 */       y = ((target.getEntityBoundingBox()).minY + (target.getEntityBoundingBox()).maxY) / 2.0D - entity.posY + entity.getEyeHeight();
/*     */     } 
/* 125 */     double distance = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
/* 126 */     float realYaw = (float)(Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;
/* 127 */     float realPitch = (float)(-Math.atan2(y - ((target instanceof EntityPlayer) ? 0.25D : 0.0D), distance) * 180.0D / Math.PI);
/* 128 */     float yaw = changeRotation(entity.rotationYaw, realYaw, p_706252);
/* 129 */     float pitch = changeRotation(entity.rotationPitch, realPitch, p_706253);
/* 130 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float changeRotation(float p_706631, float p_706632, float p_706633) {
/* 134 */     float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
/* 135 */     if (var4 > p_706633) {
/* 136 */       var4 = p_706633;
/*     */     }
/* 138 */     if (var4 < -p_706633) {
/* 139 */       var4 = -p_706633;
/*     */     }
/* 141 */     return p_706631 + var4;
/*     */   }
/*     */   
/*     */   public static float[] getRotateForReturn(float lastYaw, float lastPitch, float backspeed) {
/* 145 */     EventRotation smoothAngle = smoothAngle(new EventRotation((Minecraft.getMinecraft()).thePlayer.rotationYaw, (Minecraft.getMinecraft()).thePlayer.rotationPitch), new EventRotation(lastYaw, lastPitch), backspeed);
/* 146 */     return new float[] { (Minecraft.getMinecraft()).thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(smoothAngle.getYaw() - (Minecraft.getMinecraft()).thePlayer.rotationYaw), smoothAngle.getPitch() };
/*     */   }
/*     */   
/*     */   public static float[] getRotateForScaffold(float needYaw, float needPitch, float yaw, float pitch, float minturnspeed, float maxturnspeed) {
/* 150 */     EventRotation smoothAngle = smoothAngle(new EventRotation(needYaw, needPitch), new EventRotation(yaw, pitch), 
/* 151 */         MathUtil.getRandomInRange(minturnspeed, maxturnspeed));
/* 152 */     return new float[] { mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(smoothAngle.getYaw() - mc.thePlayer.rotationYaw), smoothAngle.getPitch() };
/*     */   }
/*     */   
/*     */   private static EventRotation smoothAngle(EventRotation e, EventRotation e2, float turnSpeed) {
/* 156 */     EventRotation angle = (new EventRotation(e2.getYaw() - e.getYaw(), e2.getPitch() - e.getPitch())).getAngle();
/* 157 */     angle.setYaw(e2.getYaw() - angle.getYaw() / 180.0F * turnSpeed);
/* 158 */     angle.setPitch(e2.getPitch() - angle.getPitch() / 180.0F * turnSpeed);
/* 159 */     return angle.getAngle();
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getRotationsForDown(Entity entity) {
/*     */     double diffY;
/* 165 */     if (entity == null) {
/* 166 */       return null;
/*     */     }
/* 168 */     double diffX = entity.posX - mc.thePlayer.posX;
/* 169 */     double diffZ = entity.posZ - mc.thePlayer.posZ;
/* 170 */     if (entity instanceof EntityLivingBase) {
/* 171 */       EntityLivingBase elb = (EntityLivingBase)entity;
/* 172 */       diffY = elb.posY + elb.getEyeHeight() - 0.4D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } else {
/* 174 */       diffY = ((entity.getEntityBoundingBox()).minY + (entity.getEntityBoundingBox()).maxY) / 2.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } 
/* 176 */     double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/* 177 */     float yaw = (float)(-Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 178 */     float pitch = (float)(-Math.atan2(diffY, dist) * 180.0D / Math.PI);
/* 179 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float[] getRotations(Entity entity) {
/*     */     double diffY;
/* 184 */     if (entity == null) {
/* 185 */       return null;
/*     */     }
/* 187 */     double diffX = entity.posX - mc.thePlayer.posX;
/* 188 */     double diffZ = entity.posZ - mc.thePlayer.posZ;
/* 189 */     if (entity instanceof EntityLivingBase) {
/* 190 */       EntityLivingBase elb = (EntityLivingBase)entity;
/* 191 */       diffY = elb.posY + elb.getEyeHeight() - 0.4D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } else {
/* 193 */       diffY = ((entity.getEntityBoundingBox()).minY + (entity.getEntityBoundingBox()).maxY) / 2.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } 
/* 195 */     double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/* 196 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 197 */     float pitch = (float)(-Math.atan2(diffY, dist) * 180.0D / Math.PI);
/* 198 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float[] getRotations(double posX, double posY, double posZ, double eyeHeight, BlockPos blockPos, EnumFacing enumFacing) {
/* 202 */     double n = blockPos.getX() + 0.5D - posX + enumFacing.getFrontOffsetX() / 2.0D;
/* 203 */     double n2 = blockPos.getZ() + 0.5D - posZ + enumFacing.getFrontOffsetZ() / 2.0D;
/* 204 */     double n3 = posY + eyeHeight - blockPos.getY() + 0.5D;
/* 205 */     double n4 = MathHelper.sqrt_double(n * n + n2 * n2);
/* 206 */     float n5 = (float)(Math.atan2(n2, n) * 180.0D / Math.PI) - 90.0F;
/* 207 */     float n6 = (float)(Math.atan2(n3, n4) * 180.0D / Math.PI);
/* 208 */     if (n5 < 0.0F) {
/* 209 */       n5 += 360.0F;
/*     */     }
/* 211 */     return new float[] { n5, n6 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity getMouseOver(float partialTicks, Entity entity) {
/* 217 */     Entity mcpointedentity = null;
/* 218 */     if (entity != null && mc.theWorld != null) {
/* 219 */       double d0 = mc.playerController.getBlockReachDistance();
/* 220 */       MovingObjectPosition objectMouseOver = entity.rayTrace(d0, partialTicks);
/*     */       
/* 222 */       Vec3 vec3 = entity.getPositionEyes(partialTicks);
/* 223 */       d0 = 6.0D;
/* 224 */       double d1 = 6.0D;
/*     */       
/* 226 */       if (objectMouseOver != null) {
/* 227 */         d1 = objectMouseOver.hitVec.distanceTo(vec3);
/*     */       }
/*     */       
/* 230 */       Vec3 vec31 = entity.getLook(partialTicks);
/* 231 */       Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
/* 232 */       Entity pointedEntity = null;
/* 233 */       Vec3 vec33 = null;
/* 234 */       float f = 1.0F;
/* 235 */       List list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
/* 236 */       double d2 = d1;
/*     */       
/* 238 */       for (Object o : list) {
/* 239 */         Entity entity1 = (Entity)o;
/* 240 */         float f1 = entity1.getCollisionBorderSize();
/* 241 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/* 242 */         MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
/*     */         
/* 244 */         if (axisalignedbb.isVecInside(vec3)) {
/* 245 */           if (d2 >= 0.0D) {
/* 246 */             pointedEntity = entity1;
/* 247 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.hitVec;
/* 248 */             d2 = 0.0D;
/*     */           }  continue;
/* 250 */         }  if (movingobjectposition != null) {
/* 251 */           double d3 = vec3.distanceTo(movingobjectposition.hitVec);
/*     */           
/* 253 */           if (d3 < d2 || d2 == 0.0D) {
/* 254 */             boolean flag2 = false;
/*     */             
/* 256 */             if (Reflector.ForgeEntity_canRiderInteract.exists()) {
/* 257 */               flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*     */             }
/*     */             
/* 260 */             if (entity1 == entity.ridingEntity && !flag2) {
/* 261 */               if (d2 == 0.0D) {
/* 262 */                 pointedEntity = entity1;
/* 263 */                 vec33 = movingobjectposition.hitVec;
/*     */               }  continue;
/*     */             } 
/* 266 */             pointedEntity = entity1;
/* 267 */             vec33 = movingobjectposition.hitVec;
/* 268 */             d2 = d3;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 274 */       if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
/* 275 */         new MovingObjectPosition(pointedEntity, vec33);
/*     */         
/* 277 */         if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame) {
/* 278 */           mcpointedentity = pointedEntity;
/*     */         }
/*     */       } 
/*     */     } 
/* 282 */     return mcpointedentity;
/*     */   }
/*     */   
/*     */   public static float[] getRotations(double posX, double posY, double posZ) {
/* 286 */     EntityPlayerSP player = mc.thePlayer;
/* 287 */     double x = posX - player.posX;
/* 288 */     double y = posY - player.posY + player.getEyeHeight();
/* 289 */     double z = posZ - player.posZ;
/* 290 */     double dist = MathHelper.sqrt_double(x * x + z * z);
/* 291 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/* 292 */     float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
/* 293 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float getYawChangeToEntity(Entity entity) {
/* 297 */     double deltaX = entity.posX - mc.thePlayer.posX;
/* 298 */     double deltaZ = entity.posZ - mc.thePlayer.posZ;
/* 299 */     double yawToEntity = (deltaZ < 0.0D && deltaX < 0.0D) ? (90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX))) : ((deltaZ < 0.0D && deltaX > 0.0D) ? (-90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX))) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
/* 300 */     return Double.isNaN(mc.thePlayer.rotationYaw - yawToEntity) ? 0.0F : MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)yawToEntity));
/*     */   }
/*     */   
/*     */   public static float getPitchChangeToEntity(Entity entity) {
/* 304 */     double deltaX = entity.posX - mc.thePlayer.posX;
/* 305 */     double deltaZ = entity.posZ - mc.thePlayer.posZ;
/* 306 */     double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - mc.thePlayer.posY;
/* 307 */     double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ)));
/* 308 */     return Double.isNaN(mc.thePlayer.rotationPitch - pitchToEntity) ? 0.0F : -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)pitchToEntity);
/*     */   }
/*     */   
/*     */   public static float[] getAnglesWatchdog(Entity e) {
/* 312 */     return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
/*     */   }
/*     */   
/*     */   public static float[] getAngles(EntityLivingBase entity) {
/* 316 */     if (entity == null) return null; 
/* 317 */     EntityPlayerSP player = mc.thePlayer;
/*     */     
/* 319 */     double diffX = entity.posX - player.posX;
/* 320 */     double diffY = entity.posY + entity.getEyeHeight() * 0.9D - player.posY + player.getEyeHeight();
/* 321 */     double diffZ = entity.posZ - player.posZ, dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/*     */     
/* 323 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 324 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/*     */     
/* 326 */     return new float[] { player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - player.rotationYaw), player.rotationPitch + 
/* 327 */         MathHelper.wrapAngleTo180_float(pitch - player.rotationPitch) };
/*     */   }
/*     */   
/*     */   public static Vec3 getVectorForRotation(float pitch, float yaw) {
/* 331 */     float f = (float)Math.cos(Math.toRadians(-yaw) - 3.1415927410125732D);
/* 332 */     float f1 = (float)Math.sin(Math.toRadians(-yaw) - 3.1415927410125732D);
/* 333 */     float f2 = (float)-Math.cos(Math.toRadians(-pitch));
/* 334 */     float f3 = (float)Math.sin(Math.toRadians(-pitch));
/* 335 */     return new Vec3((f1 * f2), f3, (f * f2));
/*     */   }
/*     */   
/*     */   public static float getYawToPoint(double posX, double posZ) {
/* 339 */     double xDiff = posX - mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * mc.timer.elapsedPartialTicks;
/* 340 */     double zDiff = posZ - mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * mc.timer.elapsedPartialTicks, dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
/*     */     
/* 342 */     return (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getOffsetRotations(EntityLivingBase curTarget) {
/* 349 */     double diffX = curTarget.posX - (Minecraft.getMinecraft()).thePlayer.posX;
/* 350 */     double diffZ = curTarget.posZ - (Minecraft.getMinecraft()).thePlayer.posZ;
/* 351 */     double diffY = (curTarget instanceof EntityPlayer) ? (curTarget.posY - (Minecraft.getMinecraft()).thePlayer.posY - (curTarget.isSneaking() ? 0.3D : 0.1D)) : (curTarget.posY - (Minecraft.getMinecraft()).thePlayer.posY - 1.2D);
/* 352 */     double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/* 353 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 354 */     float pitch = (float)(-Math.atan2(diffY, dist) * 180.0D / Math.PI);
/* 355 */     return new float[] { yaw, pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRemixRotations(Entity target) {
/* 362 */     double xDiff = target.posX - mc.thePlayer.posX;
/* 363 */     double yDiff = target.posY - mc.thePlayer.posY;
/* 364 */     double zDiff = target.posZ - mc.thePlayer.posZ;
/* 365 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/* 366 */     float pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / 0.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/*     */     
/* 368 */     if (yDiff > -0.2D && yDiff < 0.2D) {
/* 369 */       pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / 1.5D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/* 370 */     } else if (yDiff > -0.2D) {
/* 371 */       pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / 3.5D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/* 372 */     } else if (yDiff < 0.3D) {
/* 373 */       pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 376 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   public static interface IEntityFilter {
/*     */     boolean canRaycast(Entity param1Entity); }
/*     */   
/* 381 */   private enum HitLocation { AUTO(0.0D),
/* 382 */     HEAD(1.0D),
/* 383 */     CHEST(1.5D),
/* 384 */     FEET(3.5D); private final double offset;
/*     */     public double getOffset() {
/* 386 */       return this.offset;
/*     */     }
/*     */     HitLocation(double offset) {
/* 389 */       this.offset = offset;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] getRemixRotation(Entity target) {
/* 395 */     double xDiff = target.posX - mc.thePlayer.posX;
/* 396 */     double yDiff = target.posY - mc.thePlayer.posY;
/* 397 */     double zDiff = target.posZ - mc.thePlayer.posZ;
/* 398 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/* 399 */     float pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / 0.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/*     */     
/* 401 */     if (yDiff > -0.2D && yDiff < 0.2D) {
/* 402 */       pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / HitLocation.CHEST.getOffset() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/* 403 */     } else if (yDiff > -0.2D) {
/* 404 */       pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / HitLocation.FEET.getOffset() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/* 405 */     } else if (yDiff < 0.3D) {
/* 406 */       pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / HitLocation.HEAD.getOffset() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), Math.hypot(xDiff, zDiff)) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 409 */     return new float[] { yaw, pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getFluxRotations(Entity entity, double maxRange) {
/* 416 */     if (entity == null) {
/* 417 */       return null;
/*     */     }
/* 419 */     double diffX = entity.posX - mc.thePlayer.posX;
/* 420 */     double diffZ = entity.posZ - mc.thePlayer.posZ;
/* 421 */     Location BestPos = new Location(entity.posX, entity.posY, entity.posZ);
/*     */     
/* 423 */     Location myEyePos = new Location((Minecraft.getMinecraft()).thePlayer.posX, (Minecraft.getMinecraft()).thePlayer.posY + mc.thePlayer.getEyeHeight(), (Minecraft.getMinecraft()).thePlayer.posZ);
/*     */     
/*     */     double diffY;
/* 426 */     for (diffY = entity.boundingBox.minY + 0.7D; diffY < entity.boundingBox.maxY - 0.1D; diffY += 0.1D) {
/* 427 */       if (myEyePos.distanceTo(new Location(entity.posX, diffY, entity.posZ)) < myEyePos.distanceTo(BestPos)) {
/* 428 */         BestPos = new Location(entity.posX, diffY, entity.posZ);
/*     */       }
/*     */     } 
/*     */     
/* 432 */     if (myEyePos.distanceTo(BestPos) > maxRange) {
/* 433 */       return null;
/*     */     }
/* 435 */     diffY = BestPos.getY() - (Minecraft.getMinecraft()).thePlayer.posY + (Minecraft.getMinecraft()).thePlayer.getEyeHeight();
/* 436 */     double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/* 437 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 438 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/* 439 */     return new float[] { yaw, pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotationsFromAutumn(double posX, double posY, double posZ) {
/* 448 */     EntityPlayerSP player = mc.thePlayer;
/* 449 */     double x = posX - player.posX;
/* 450 */     double y = posY - player.posY + player.getEyeHeight();
/* 451 */     double z = posZ - player.posZ;
/* 452 */     double dist = MathHelper.sqrt_double(x * x + z * z);
/* 453 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/* 454 */     float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
/* 455 */     return new float[] { yaw, pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getAutumnRotationsEntity(EntityLivingBase entity) {
/* 462 */     if (mc.thePlayer.moving()) {
/* 463 */       return getRotationsFromAutumn(entity.posX + MathUtils.randomNumber(0.03D, -0.03D), entity.posY + entity.getEyeHeight() - 0.4D + MathUtils.randomNumber(0.07D, -0.07D), entity.posZ + MathUtils.randomNumber(0.03D, -0.03D));
/*     */     }
/* 465 */     return getRotationsFromAutumn(entity.posX, entity.posY + entity.getEyeHeight() - 0.4D, entity.posZ);
/*     */   }
/*     */   
/*     */   public static float[] getWatchdogZitterRotation(EntityLivingBase entity) {
/* 469 */     if (mc.thePlayer.moving()) {
/* 470 */       return getRotationsFromAutumn(entity.posX + MoveUtils.INSTANCE.getDirection() / entity.posZ / 100.0D, entity.posY + (entity
/* 471 */           .getEyeHeight() / 2.0F), entity.posZ + MoveUtils.INSTANCE
/* 472 */           .getDirection() / entity.posX / 100.0D);
/*     */     }
/* 474 */     return getRotationsFromAutumn(entity.posX, entity.posY + entity.getEyeHeight() - 0.4D, entity.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotationsToEnt(Entity ent) {
/* 481 */     double differenceX = ent.posX - mc.thePlayer.posX;
/* 482 */     double differenceY = ent.posY + ent.height - mc.thePlayer.posY + mc.thePlayer.height - 0.5D;
/* 483 */     double differenceZ = ent.posZ - mc.thePlayer.posZ;
/* 484 */     float rotationYaw = (float)(Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0F;
/* 485 */     float rotationPitch = (float)(Math.atan2(differenceY, mc.thePlayer.getDistanceToEntity(ent)) * 180.0D / Math.PI);
/*     */ 
/*     */     
/* 488 */     float finishedYaw = mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - mc.thePlayer.rotationYaw);
/*     */     
/* 490 */     float finishedPitch = mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - mc.thePlayer.rotationPitch);
/* 491 */     return new float[] { finishedYaw, -MathHelper.clamp_float(finishedPitch, -90.0F, 90.0F) };
/*     */   }
/*     */   
/*     */   public static float[] PredicteRot(double x, double z, double y) {
/* 495 */     double xDiff = x - mc.thePlayer.posX;
/* 496 */     double zDiff = z - mc.thePlayer.posZ;
/* 497 */     double yDiff = y - mc.thePlayer.posY - 1.2D;
/* 498 */     double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
/* 499 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/* 500 */     float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/* 501 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float[] getSmoothRot(EntityLivingBase ent) {
/* 505 */     double x = ent.posX + ent.posX - ent.lastTickPosX;
/* 506 */     double z = ent.posZ + ent.posZ - ent.lastTickPosZ;
/* 507 */     double y = ent.posY + (ent.getEyeHeight() / 6.0F);
/* 508 */     return PredicteRot(x + Math.min(MoveUtils.INSTANCE.getDirectionForAura() / 100.0D, -MoveUtils.INSTANCE.getDirectionForAura() / 100.0D), z + 
/* 509 */         Math.min(MoveUtils.INSTANCE.getDirectionForAura() / 100.0D, -MoveUtils.INSTANCE.getDirectionForAura() / 100.0D), y);
/*     */   }
/*     */   
/*     */   public static float[] getPredictedRotations(EntityLivingBase ent) {
/* 513 */     double x = ent.posX + ent.posX - ent.lastTickPosX;
/* 514 */     double z = ent.posZ + ent.posZ - ent.lastTickPosZ;
/* 515 */     double y = ent.posY + (ent.getEyeHeight() / 2.0F);
/* 516 */     return PredicteRot(x, z, y);
/*     */   }
/*     */   
/*     */   public static Vec3 getPositionEyes(float partialTicks) {
/* 520 */     return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer
/* 521 */         .getEyeHeight(), mc.thePlayer.posZ);
/*     */   }
/*     */   
/*     */   public static float[] getRotationFromPosition(double x, double z, double y) {
/* 525 */     double xDiff = x - (Minecraft.getMinecraft()).thePlayer.posX;
/* 526 */     double zDiff = z - (Minecraft.getMinecraft()).thePlayer.posZ;
/* 527 */     double yDiff = y - (Minecraft.getMinecraft()).thePlayer.posY;
/*     */     
/* 529 */     double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
/* 530 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/* 531 */     float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/* 532 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float getSensitivityMultiplier() {
/* 536 */     float f = (Minecraft.getMinecraft()).gameSettings.mouseSensitivity * 0.6F + 0.2F;
/* 537 */     return f * f * f * 8.0F * 0.15F;
/*     */   }
/*     */   
/*     */   public static awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation toRotationMisc(Vec3 vec, boolean predict) {
/* 541 */     Vec3 eyesPos = new Vec3(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/*     */     
/* 543 */     if (predict) {
/* 544 */       eyesPos.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
/*     */     }
/* 546 */     double diffX = vec.xCoord - eyesPos.xCoord;
/* 547 */     double diffY = vec.yCoord - eyesPos.yCoord;
/* 548 */     double diffZ = vec.zCoord - eyesPos.zCoord;
/*     */     
/* 550 */     return new awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), 
/* 551 */         MathHelper.wrapAngleTo180_float(
/* 552 */           (float)-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)))));
/*     */   }
/*     */   
/*     */   public static Rotation toRotation(Vec3 vec, boolean predict, boolean hypixel) {
/* 556 */     Vec3 eyesPos = new Vec3(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/*     */     
/* 558 */     if (predict) {
/* 559 */       eyesPos.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
/*     */     }
/* 561 */     double diffX = vec.xCoord - eyesPos.xCoord;
/* 562 */     double diffY = vec.yCoord - eyesPos.yCoord;
/* 563 */     double diffZ = vec.zCoord - eyesPos.zCoord;
/*     */     
/* 565 */     return new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - (hypixel ? '' : 90)), 
/* 566 */         MathHelper.wrapAngleTo180_float(
/* 567 */           (float)-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)))));
/*     */   }
/*     */   
/*     */   public static float[] getAnglesAGC(Entity e) {
/* 571 */     EntityPlayerSP playerSP = mc.thePlayer;
/* 572 */     double differenceX = e.posX - playerSP.posX;
/* 573 */     double differenceY = e.posY + e.height - playerSP.posY + playerSP.height;
/* 574 */     double differenceZ = e.posZ - playerSP.posZ;
/* 575 */     float rotationYaw = (float)(Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0F;
/* 576 */     float rotationPitch = (float)(Math.atan2(differenceY, playerSP.getDistanceToEntity(e)) * 180.0D / Math.PI);
/* 577 */     float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
/* 578 */     float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
/* 579 */     return new float[] { finishedYaw, -finishedPitch };
/*     */   }
/*     */   
/*     */   public static float[] getCustomRotation(Vec3 vec) {
/* 583 */     Vec3 playerVector = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/* 584 */     double y = vec.yCoord - playerVector.yCoord;
/* 585 */     double x = vec.xCoord - playerVector.xCoord;
/* 586 */     double z = vec.zCoord - playerVector.zCoord;
/* 587 */     double dff = Math.sqrt(x * x + z * z);
/* 588 */     float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
/* 589 */     float pitch = (float)-Math.toDegrees(Math.atan2(y, dff));
/* 590 */     return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
/*     */   }
/*     */   
/*     */   public static Vec3 getLocation(AxisAlignedBB bb) {
/* 594 */     double yaw = 0.5D;
/* 595 */     double pitch = 0.5D;
/* 596 */     VecRotation rotation = searchCenter(bb, true);
/* 597 */     return (rotation != null) ? rotation.getVec() : new Vec3(bb.minX + (bb.maxX - bb.minX) * yaw, bb.minY + (bb.maxY - bb.minY) * pitch, bb.minZ + (bb.maxZ - bb.minZ) * yaw);
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getAngleDifference(float a, float b) {
/* 602 */     return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
/*     */   }
/*     */   
/*     */   public static double getRotationDifference(awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation a, awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation b) {
/* 606 */     return Math.hypot(getAngleDifference(a.getYaw(), b.getYaw()), (a.getPitch() - b.getPitch()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static VecRotation searchCenter(AxisAlignedBB bb, boolean predict) {
/* 611 */     VecRotation vecRotation = null;
/*     */     
/* 613 */     for (double xSearch = 0.15D; xSearch < 0.85D; xSearch += 0.1D) {
/* 614 */       double ySearch; for (ySearch = 0.15D; ySearch < 1.0D; ySearch += 0.1D) {
/* 615 */         double zSearch; for (zSearch = 0.15D; zSearch < 0.85D; zSearch += 0.1D) {
/* 616 */           Vec3 vec3 = new Vec3(bb.minX + (bb.maxX - bb.minX) * xSearch, bb.minY + (bb.maxY - bb.minY) * ySearch, bb.minZ + (bb.maxZ - bb.minZ) * zSearch);
/*     */           
/* 618 */           awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation rotation = toRotationMisc(vec3, predict);
/*     */           
/* 620 */           VecRotation currentVec = new VecRotation(vec3, rotation);
/*     */           
/* 622 */           if (vecRotation == null || getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
/* 623 */             vecRotation = currentVec;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 628 */     return vecRotation;
/*     */   }
/*     */   
/* 631 */   public static final awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation serverRotation = new awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation(0.0F, 0.0F);
/*     */   
/*     */   public static double getRotationDifference(awareline.main.mod.implement.combat.advanced.sucks.utils.Rotation rotation) {
/* 634 */     return getRotationDifference(rotation, serverRotation);
/*     */   }
/*     */   
/*     */   public static float[] getAverageRotations(List<? extends EntityLivingBase> targetList) {
/* 638 */     double posX = 0.0D;
/* 639 */     double posY = 0.0D;
/* 640 */     double posZ = 0.0D;
/* 641 */     for (Entity ent : targetList) {
/* 642 */       posX += ent.posX;
/* 643 */       posY += ent.boundingBox.maxY - 2.0D;
/* 644 */       posZ += ent.posZ;
/*     */     } 
/* 646 */     return new float[] { getRotationFromPosition(posX /= targetList.size(), posZ /= targetList.size(), posY /= targetList.size())[0], getRotationFromPosition(posX, posZ, posY)[1] };
/*     */   }
/*     */   public static class Rotation {
/*     */     float yaw; float pitch;
/*     */     
/* 651 */     public float getYaw() { return this.yaw; } public float getPitch() { return this.pitch; }
/*     */     
/*     */     public Rotation(float yaw, float pitch) {
/* 654 */       this.yaw = yaw;
/* 655 */       this.pitch = pitch;
/*     */     }
/*     */     
/*     */     public void setYaw(float yaw) {
/* 659 */       this.yaw = yaw;
/*     */     }
/*     */     
/*     */     public void setPitch(float pitch) {
/* 663 */       this.pitch = pitch;
/*     */     }
/*     */     
/*     */     public void toPlayer(EntityPlayer player) {
/* 667 */       if (Float.isNaN(this.yaw) || Float.isNaN(this.pitch)) {
/*     */         return;
/*     */       }
/* 670 */       fixedSensitivity(Float.valueOf(Utils.mc.gameSettings.mouseSensitivity));
/*     */       
/* 672 */       player.rotationYaw = this.yaw;
/* 673 */       player.rotationPitch = this.pitch;
/*     */     }
/*     */     
/*     */     public void fixedSensitivity(Float sensitivity) {
/* 677 */       float f = sensitivity.floatValue() * 0.6F + 0.2F;
/* 678 */       float gcd = f * f * f * 1.2F;
/*     */       
/* 680 */       this.yaw -= this.yaw % gcd;
/* 681 */       this.pitch -= this.pitch % gcd;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\math\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */