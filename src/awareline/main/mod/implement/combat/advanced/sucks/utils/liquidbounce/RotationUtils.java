/*     */ package awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.RaycastUtils;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RotationUtils
/*     */ {
/*  26 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*  28 */   private static final Random random = new Random();
/*     */   
/*     */   private static int keepLength;
/*     */   
/*     */   public static Rotation targetRotation;
/*  33 */   public static Rotation serverRotation = new Rotation(0.0F, 0.0F);
/*     */   
/*     */   public static boolean keepCurrentRotation;
/*     */   
/*  37 */   private static double x = random.nextDouble();
/*  38 */   private static double y = random.nextDouble();
/*  39 */   private static double z = random.nextDouble();
/*     */   
/*     */   public RotationUtils() {
/*  42 */     EventManager.register(new Object[] { this });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFaced(Entity targetEntity, double blockReachDistance) {
/*  53 */     return (RaycastUtils.raycastEntity(blockReachDistance, entity -> (entity == targetEntity)) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Rotation toRotation(Vec3 vec, boolean predict) {
/*  65 */     Vec3 eyesPos = new Vec3(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/*     */     
/*  67 */     if (predict) {
/*  68 */       eyesPos.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
/*     */     }
/*  70 */     double diffX = vec.getX() - eyesPos.getX();
/*  71 */     double diffY = vec.getY() - eyesPos.getY();
/*  72 */     double diffZ = vec.getZ() - eyesPos.getZ();
/*     */     
/*  74 */     return new Rotation(MathHelper.wrapAngleTo180_float(
/*  75 */           (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), 
/*  76 */         MathHelper.wrapAngleTo180_float(
/*  77 */           (float)-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 getCenter(AxisAlignedBB bb) {
/*  88 */     return new Vec3(bb.minX + bb.maxX - bb.minX * 0.5D, bb.minY + (bb.maxY - bb.minY) * 0.5D, bb.minZ + (bb.maxZ - bb.minZ) * 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VecRotation searchCenter(AxisAlignedBB bb, boolean outborder, boolean random, boolean predict, boolean throughWalls, float distance) {
/* 103 */     if (outborder) {
/* 104 */       Vec3 vec3 = new Vec3(bb.minX + (bb.maxX - bb.minX) * (x * 0.3D + 1.0D), bb.minY + (bb.maxY - bb.minY) * (y * 0.3D + 1.0D), bb.minZ + (bb.maxZ - bb.minZ) * (z * 0.3D + 1.0D));
/* 105 */       return new VecRotation(vec3, toRotation(vec3, predict));
/*     */     } 
/*     */     
/* 108 */     Vec3 randomVec = new Vec3(bb.minX + (bb.maxX - bb.minX) * x * 0.8D, bb.minY + (bb.maxY - bb.minY) * y * 0.8D, bb.minZ + (bb.maxZ - bb.minZ) * z * 0.8D);
/* 109 */     Rotation randomRotation = toRotation(randomVec, predict);
/*     */     
/* 111 */     Vec3 eyes = mc.thePlayer.getPositionEyes(1.0F);
/*     */     
/* 113 */     VecRotation vecRotation = null;
/*     */     double xSearch;
/* 115 */     for (xSearch = 0.15D; xSearch < 0.85D; xSearch += 0.1D) {
/* 116 */       double ySearch; for (ySearch = 0.15D; ySearch < 1.0D; ySearch += 0.1D) {
/* 117 */         double zSearch; for (zSearch = 0.15D; zSearch < 0.85D; zSearch += 0.1D) {
/* 118 */           Vec3 vec3 = new Vec3(bb.minX + (bb.maxX - bb.minX) * xSearch, bb.minY + (bb.maxY - bb.minY) * ySearch, bb.minZ + (bb.maxZ - bb.minZ) * zSearch);
/*     */           
/* 120 */           Rotation rotation = toRotation(vec3, predict);
/* 121 */           double vecDist = eyes.distanceTo(vec3);
/*     */           
/* 123 */           if (vecDist <= distance)
/*     */           {
/*     */             
/* 126 */             if (throughWalls || isVisible(vec3)) {
/* 127 */               VecRotation currentVec = new VecRotation(vec3, rotation);
/*     */               
/* 129 */               if (vecRotation == null || (random ? (getRotationDifference(currentVec.getRotation(), randomRotation) < getRotationDifference(vecRotation.getRotation(), randomRotation)) : (getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation()))))
/* 130 */                 vecRotation = currentVec; 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 136 */     return vecRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VecRotation calculateCenter(String calMode, String randMode, double randomRange, AxisAlignedBB bb, boolean predict, boolean throughWalls) {
/* 148 */     VecRotation vecRotation = null;
/*     */     
/* 150 */     double xMin = 0.0D;
/* 151 */     double yMin = 0.0D;
/* 152 */     double zMin = 0.0D;
/* 153 */     double xMax = 0.0D;
/* 154 */     double yMax = 0.0D;
/* 155 */     double zMax = 0.0D;
/* 156 */     double xDist = 0.0D;
/* 157 */     double yDist = 0.0D;
/* 158 */     double zDist = 0.0D;
/*     */     
/* 160 */     xMin = 0.15D;
/* 161 */     xMax = 0.85D;
/* 162 */     xDist = 0.1D;
/* 163 */     yMin = 0.15D;
/* 164 */     yMax = 1.0D;
/* 165 */     yDist = 0.1D;
/* 166 */     zMin = 0.15D;
/* 167 */     zMax = 0.85D;
/* 168 */     zDist = 0.1D;
/*     */     
/* 170 */     Vec3 curVec3 = null;
/*     */     
/* 172 */     switch (calMode) {
/*     */       case "LiquidBounce":
/* 174 */         xMin = 0.15D;
/* 175 */         xMax = 0.85D;
/* 176 */         xDist = 0.1D;
/* 177 */         yMin = 0.15D;
/* 178 */         yMax = 1.0D;
/* 179 */         yDist = 0.1D;
/* 180 */         zMin = 0.15D;
/* 181 */         zMax = 0.85D;
/* 182 */         zDist = 0.1D;
/*     */         break;
/*     */       case "Full":
/* 185 */         xMin = 0.0D;
/* 186 */         xMax = 1.0D;
/* 187 */         xDist = 0.1D;
/* 188 */         yMin = 0.0D;
/* 189 */         yMax = 1.0D;
/* 190 */         yDist = 0.1D;
/* 191 */         zMin = 0.0D;
/* 192 */         zMax = 1.0D;
/* 193 */         zDist = 0.1D;
/*     */         break;
/*     */       case "HalfUp":
/* 196 */         xMin = 0.1D;
/* 197 */         xMax = 0.9D;
/* 198 */         xDist = 0.1D;
/* 199 */         yMin = 0.5D;
/* 200 */         yMax = 0.9D;
/* 201 */         yDist = 0.1D;
/* 202 */         zMin = 0.1D;
/* 203 */         zMax = 0.9D;
/* 204 */         zDist = 0.1D;
/*     */         break;
/*     */       case "HalfDown":
/* 207 */         xMin = 0.1D;
/* 208 */         xMax = 0.9D;
/* 209 */         xDist = 0.1D;
/* 210 */         yMin = 0.1D;
/* 211 */         yMax = 0.5D;
/* 212 */         yDist = 0.1D;
/* 213 */         zMin = 0.1D;
/* 214 */         zMax = 0.9D;
/* 215 */         zDist = 0.1D;
/*     */         break;
/*     */       case "CenterSimple":
/* 218 */         xMin = 0.45D;
/* 219 */         xMax = 0.55D;
/* 220 */         xDist = 0.0125D;
/* 221 */         yMin = 0.65D;
/* 222 */         yMax = 0.75D;
/* 223 */         yDist = 0.0125D;
/* 224 */         zMin = 0.45D;
/* 225 */         zMax = 0.55D;
/* 226 */         zDist = 0.0125D;
/*     */         break;
/*     */       case "CenterLine":
/* 229 */         xMin = 0.45D;
/* 230 */         xMax = 0.55D;
/* 231 */         xDist = 0.0125D;
/* 232 */         yMin = 0.1D;
/* 233 */         yMax = 0.9D;
/* 234 */         yDist = 0.1D;
/* 235 */         zMin = 0.45D;
/* 236 */         zMax = 0.55D;
/* 237 */         zDist = 0.0125D;
/*     */         break;
/*     */     } 
/*     */     double xSearch;
/* 241 */     for (xSearch = xMin; xSearch < xMax; xSearch += xDist) {
/* 242 */       double ySearch; for (ySearch = yMin; ySearch < yMax; ySearch += yDist) {
/* 243 */         double zSearch; for (zSearch = zMin; zSearch < zMax; zSearch += zDist) {
/* 244 */           Vec3 vec3 = new Vec3(bb.minX + (bb.maxX - bb.minX) * xSearch, bb.minY + (bb.maxY - bb.minY) * ySearch, bb.minZ + (bb.maxZ - bb.minZ) * zSearch);
/* 245 */           Rotation rotation = toRotation(vec3, predict);
/*     */           
/* 247 */           if (throughWalls || isVisible(vec3)) {
/* 248 */             VecRotation currentVec = new VecRotation(vec3, rotation);
/*     */             
/* 250 */             if (vecRotation == null || getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
/* 251 */               vecRotation = currentVec;
/* 252 */               curVec3 = vec3;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     if (vecRotation == null || Objects.equals(randMode, "Off")) {
/* 260 */       return vecRotation;
/*     */     }
/* 262 */     double rand1 = random.nextDouble();
/* 263 */     double rand2 = random.nextDouble();
/* 264 */     double rand3 = random.nextDouble();
/*     */     
/* 266 */     double xRange = bb.maxX - bb.minX;
/* 267 */     double yRange = bb.maxY - bb.minY;
/* 268 */     double zRange = bb.maxZ - bb.minZ;
/* 269 */     double minRange = 999999.0D;
/*     */     
/* 271 */     if (xRange <= minRange) minRange = xRange; 
/* 272 */     if (yRange <= minRange) minRange = yRange; 
/* 273 */     if (zRange <= minRange) minRange = zRange;
/*     */     
/* 275 */     rand1 = rand1 * minRange * randomRange;
/* 276 */     rand2 = rand2 * minRange * randomRange;
/* 277 */     rand3 = rand3 * minRange * randomRange;
/*     */     
/* 279 */     double xPrecent = minRange * randomRange / xRange;
/* 280 */     double yPrecent = minRange * randomRange / yRange;
/* 281 */     double zPrecent = minRange * randomRange / zRange;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     Vec3 randomVec3 = new Vec3(curVec3.getX() - xPrecent * (curVec3.getX() - bb.minX) + rand1, curVec3.getY() - yPrecent * (curVec3.getY() - bb.minY) + rand2, curVec3.getZ() - zPrecent * (curVec3.getZ() - bb.minZ) + rand3);
/*     */     
/* 288 */     switch (randMode) {
/*     */ 
/*     */ 
/*     */       
/*     */       case "Horizonal":
/* 293 */         randomVec3 = new Vec3(curVec3.getX() - xPrecent * (curVec3.getX() - bb.minX) + rand1, curVec3.getY(), curVec3.getZ() - zPrecent * (curVec3.getZ() - bb.minZ) + rand3);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case "Vertical":
/* 300 */         randomVec3 = new Vec3(curVec3.getX(), curVec3.getY() - yPrecent * (curVec3.getY() - bb.minY) + rand2, curVec3.getZ());
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 305 */     Rotation randomRotation = toRotation(randomVec3, predict);
/* 306 */     vecRotation = new VecRotation(randomVec3, randomRotation);
/*     */     
/* 308 */     return vecRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static VecRotation searchCenterLnk(AxisAlignedBB bb, boolean throughWalls, float distance) {
/* 314 */     boolean entityonl = false;
/* 315 */     boolean entityonr = false;
/* 316 */     VecRotation vecRotation = null;
/* 317 */     if (bb.maxX - bb.minX < bb.maxZ - bb.minZ) {
/* 318 */       entityonr = true;
/*     */     }
/* 320 */     if (bb.maxX - bb.minX > bb.maxZ - bb.minZ) {
/* 321 */       entityonl = true;
/*     */     }
/* 323 */     if (bb.maxX - bb.minX == bb.maxZ - bb.minZ) {
/* 324 */       entityonr = false;
/* 325 */       entityonl = false;
/*     */     } 
/* 327 */     double x = bb.minX + (bb.maxX - bb.minX) * (entityonl ? 0.25D : 0.5D);
/* 328 */     double z = bb.minZ + (bb.maxZ - bb.minZ) * (entityonr ? 0.25D : 0.5D); double xSearch;
/* 329 */     for (xSearch = 0.1D; xSearch < 0.9D; xSearch += 0.15D) {
/* 330 */       for (double ySearch = 0.1D; ySearch < 0.9D; ySearch += 0.15D) {
/* 331 */         double zSearch; for (zSearch = 0.1D; zSearch < 0.9D; zSearch += 0.15D) {
/* 332 */           double pitch = bb.minY + (bb.maxY - bb.minY) * ySearch;
/*     */           
/* 334 */           Vec3 vec3 = new Vec3(x, pitch, z);
/* 335 */           Rotation rotation = toRotation(vec3, false);
/* 336 */           Vec3 eyes = mc.thePlayer.getPositionEyes(1.0F);
/*     */           
/* 338 */           double vecDist = eyes.distanceTo(vec3);
/* 339 */           if (vecDist <= distance)
/*     */           {
/*     */             
/* 342 */             if (vecDist <= distance && (throughWalls || isVisible(vec3))) {
/* 343 */               VecRotation currentVec = new VecRotation(vec3, rotation);
/* 344 */               if (vecRotation == null || getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation()))
/* 345 */                 vecRotation = currentVec; 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 351 */     return vecRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getRotationDifference(Entity entity) {
/* 362 */     Rotation rotation = toRotation(getCenter(entity.getEntityBoundingBox()), true);
/*     */     
/* 364 */     return getRotationDifference(rotation, new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getRotationDifference(Rotation rotation) {
/* 374 */     return (serverRotation == null) ? 0.0D : getRotationDifference(rotation, serverRotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getRotationDifference(Rotation a, Rotation b) {
/* 385 */     return Math.hypot(getAngleDifference(a.getYaw(), b.getYaw()), (a.getPitch() - b.getPitch()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Rotation limitAngleChange(Rotation currentRotation, Rotation targetRotation, float turnSpeed) {
/* 397 */     float yawDifference = getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
/* 398 */     float pitchDifference = getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());
/*     */     
/* 400 */     return new Rotation(currentRotation
/* 401 */         .getYaw() + ((yawDifference > turnSpeed) ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation
/* 402 */         .getPitch() + ((pitchDifference > turnSpeed) ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getAngleDifference(float a, float b) {
/* 414 */     return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 getVectorForRotation(Rotation rotation) {
/* 424 */     float yawCos = MathHelper.cos(-rotation.getYaw() * 0.017453292F - 3.1415927F);
/* 425 */     float yawSin = MathHelper.sin(-rotation.getYaw() * 0.017453292F - 3.1415927F);
/* 426 */     float pitchCos = -MathHelper.cos(-rotation.getPitch() * 0.017453292F);
/* 427 */     float pitchSin = MathHelper.sin(-rotation.getPitch() * 0.017453292F);
/* 428 */     return new Vec3((yawSin * pitchCos), pitchSin, (yawCos * pitchCos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVisible(Vec3 vec3) {
/* 435 */     Vec3 eyesPos = new Vec3(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ); return 
/*     */       
/* 437 */       (mc.theWorld.rayTraceBlocks(eyesPos, new Vec3(vec3.getX(), vec3.getY(), vec3.getZ())) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onTick(EventTick event) {
/* 447 */     if (targetRotation != null) {
/* 448 */       keepLength--;
/*     */       
/* 450 */       if (keepLength <= 0) {
/* 451 */         reset();
/*     */       }
/*     */     } 
/* 454 */     if (random.nextGaussian() > 0.8D) x = Math.random(); 
/* 455 */     if (random.nextGaussian() > 0.8D) y = Math.random(); 
/* 456 */     if (random.nextGaussian() > 0.8D) z = Math.random();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setTargetRotation(Rotation rotation, int keepLength) {
/* 465 */     if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation
/* 466 */       .getPitch() > 90.0F || rotation.getPitch() < -90.0F)
/*     */       return; 
/* 468 */     rotation.fixedSensitivity(mc.gameSettings.mouseSensitivity);
/* 469 */     targetRotation = rotation;
/* 470 */     RotationUtils.keepLength = keepLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setTargetRotation(Rotation rotation) {
/* 479 */     setTargetRotation(rotation, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(EventPacketSend event) {
/* 489 */     Packet<?> packet = event.getPacket();
/* 490 */     if (packet instanceof C03PacketPlayer) {
/* 491 */       C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
/* 492 */       if (targetRotation != null && !keepCurrentRotation && (targetRotation.getYaw() != serverRotation.getYaw() || targetRotation.getPitch() != serverRotation.getPitch())) {
/* 493 */         packetPlayer.yaw = targetRotation.getYaw();
/* 494 */         packetPlayer.pitch = targetRotation.getPitch();
/* 495 */         packetPlayer.rotating = true;
/*     */       } 
/*     */       
/* 498 */       if (packetPlayer.rotating) {
/* 499 */         serverRotation = new Rotation(packetPlayer.getYaw(), packetPlayer.getPitch());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] rotateNCP(Entity a1) {
/* 512 */     if (a1 == null) {
/* 513 */       return null;
/*     */     }
/* 515 */     double v1 = a1.posX - (Minecraft.getMinecraft()).thePlayer.posX;
/* 516 */     double v3 = a1.posY + a1.getEyeHeight() * 0.9D - (Minecraft.getMinecraft()).thePlayer.posY + (Minecraft.getMinecraft()).thePlayer.getEyeHeight();
/* 517 */     double v5 = a1.posZ - (Minecraft.getMinecraft()).thePlayer.posZ;
/* 518 */     double v7 = MathHelper.ceiling_float_int((float)(v1 * v1 + v5 * v5));
/* 519 */     float v9 = (float)(Math.atan2(v5, v1) * 180.0D / Math.PI) - 90.0F;
/* 520 */     float v10 = (float)-(Math.atan2(v3, v7) * 180.0D / Math.PI);
/* 521 */     return new float[] { (Minecraft.getMinecraft()).thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(v9 - (Minecraft.getMinecraft()).thePlayer.rotationYaw), (Minecraft.getMinecraft()).thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(v10 - (Minecraft.getMinecraft()).thePlayer.rotationPitch) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 529 */     keepLength = 0;
/* 530 */     targetRotation = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\liquidbounce\RotationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */