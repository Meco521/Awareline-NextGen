/*     */ package awareline.main.utility;
/*     */ 
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovementInput;
/*     */ 
/*     */ public enum MoveUtils implements Utils {
/*  20 */   INSTANCE; public final double MIN_DIST = 0.001D; private final double[] DEPTH_STRIDER_VALUES; private final double SWIM_MOD = 0.5203620003898759D; public final double ICE_MOD = 2.5D; public final double SNEAK_MOD = 0.30000001192092896D; public final double SPRINTING_MOD = 0.7692307974459868D; public final double HEAD_HITTER_MOTION = -0.0784000015258789D; public final double UNLOADED_CHUNK_MOTION = -0.09800000190735147D; public final double[] MOD_DEPTH_STRIDER; public final double MOD_SWIM = 0.5203620003898759D; public final double LAVA_FRICTION = 0.5D; public final double WATER_FRICTION = 0.800000011920929D; public final double AIR_FRICTION = 0.9800000190734863D; public final double Y_ON_GROUND_MAX = 0.0626D; public final double Y_ON_GROUND_MIN = 1.0E-5D; public final double BUNNY_FRICTION = 159.89999389648438D; public final double JUMP_HEIGHT = 0.41999998688697815D; public final double MOD_WEB = 0.4751131221719457D; public final double MOD_ICE = 2.5D; public final double MOD_SNEAK = 0.30000001192092896D; public final double MOD_SPRINTING = 1.2999999523162842D; public final double BUNNY_SLOPE = 0.66D; public final double WALK_SPEED = 0.221D;
/*     */   
/*  22 */   MoveUtils() { this.WALK_SPEED = 0.221D;
/*  23 */     this.BUNNY_SLOPE = 0.66D;
/*  24 */     this.MOD_SPRINTING = 1.2999999523162842D;
/*  25 */     this.MOD_SNEAK = 0.30000001192092896D;
/*  26 */     this.MOD_ICE = 2.5D;
/*  27 */     this.MOD_WEB = 0.4751131221719457D;
/*  28 */     this.JUMP_HEIGHT = 0.41999998688697815D;
/*  29 */     this.BUNNY_FRICTION = 159.89999389648438D;
/*  30 */     this.Y_ON_GROUND_MIN = 1.0E-5D;
/*  31 */     this.Y_ON_GROUND_MAX = 0.0626D;
/*  32 */     this.AIR_FRICTION = 0.9800000190734863D;
/*  33 */     this.WATER_FRICTION = 0.800000011920929D;
/*  34 */     this.LAVA_FRICTION = 0.5D;
/*  35 */     this.MOD_SWIM = 0.5203620003898759D;
/*  36 */     this.MOD_DEPTH_STRIDER = new double[] { 1.0D, 1.4304347400741908D, 1.7347825295420372D, 1.9217390955733897D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     this.UNLOADED_CHUNK_MOTION = -0.09800000190735147D;
/*  43 */     this.HEAD_HITTER_MOTION = -0.0784000015258789D;
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
/*     */     
/* 261 */     this.SPRINTING_MOD = 0.7692307974459868D;
/* 262 */     this.SNEAK_MOD = 0.30000001192092896D;
/* 263 */     this.ICE_MOD = 2.5D;
/* 264 */     this.SWIM_MOD = 0.5203620003898759D;
/* 265 */     this.DEPTH_STRIDER_VALUES = new double[] { 1.0D, 1.4304347400741908D, 1.7347825295420372D, 1.9217390955733897D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     this.MIN_DIST = 0.001D; }
/*     */   public boolean MovementInput() { return (mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindBack.pressed); }
/* 273 */   public boolean isOnGround(double height) { return !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty(); } public boolean isMoving() { return (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F); } public void strafe(double speed) { if (!isMoving()) return;  double yaw = direction(); mc.thePlayer.motionX = -Math.sin((float)yaw) * speed; mc.thePlayer.motionZ = Math.cos((float)yaw) * speed; } public boolean isOnGround() { return (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically); } public int getJumpEffect() { if (mc.thePlayer.isPotionActive(Potion.jump)) return mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;  return 0; } public int getSpeedEffect() { if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;  return 0; } public void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) { double forward = pseudoForward; double strafe = pseudoStrafe; float yaw = pseudoYaw; if (forward != 0.0D) { if (strafe > 0.0D) { yaw += (forward > 0.0D) ? -45.0F : 45.0F; } else if (strafe < 0.0D) { yaw += (forward > 0.0D) ? 45.0F : -45.0F; }  strafe = 0.0D; if (forward > 0.0D) { forward = 1.0D; } else if (forward < 0.0D) { forward = -1.0D; }  }  if (strafe > 0.0D) { strafe = 1.0D; } else if (strafe < 0.0D) { strafe = -1.0D; }  double mx = Math.cos(Math.toRadians((yaw + 90.0F))); double mz = Math.sin(Math.toRadians((yaw + 90.0F))); moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz; moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx; } public void setSpeed(EventMove moveEvent, double moveSpeed) { setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward); } public double getDirection() { float rotationYaw = mc.thePlayer.rotationYaw; if (mc.thePlayer.moveForward < 0.0F) rotationYaw += 180.0F;  float forward = 1.0F; if (mc.thePlayer.moveForward < 0.0F) { forward = -0.5F; } else if (mc.thePlayer.moveForward > 0.0F) { forward = 0.5F; }  if (mc.thePlayer.moveStrafing > 0.0F) rotationYaw -= 90.0F * forward;  if (mc.thePlayer.moveStrafing < 0.0F) rotationYaw += 90.0F * forward;  return Math.toRadians(rotationYaw); } public float getyaw() { float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw); MovementInput movementInput = mc.thePlayer.movementInput; float a = movementInput.getMoveStrafe(), b = movementInput.getMoveForward(); if (a != 0.0F) { if (b < 0.0F) { wrapAngleTo180_float += (a < 0.0F) ? 135.0F : 45.0F; } else if (b > 0.0F) { wrapAngleTo180_float -= (a < 0.0F) ? 135.0F : 45.0F; } else if (a < 0.0F) { wrapAngleTo180_float -= 180.0F; }  } else if (b < 0.0F) { wrapAngleTo180_float += 90.0F; } else if (b > 0.0F) { wrapAngleTo180_float -= 90.0F; }  return MathHelper.wrapAngleTo180_float(wrapAngleTo180_float); } public boolean isMoving(EntityPlayerSP player) { return (player.moveForward != 0.0F || player.moveStrafing != 0.0F); }
/*     */   public double getDirectionForAura() { float rotationYaw = mc.thePlayer.rotationYawHead; if (mc.thePlayer.moveForward < 0.0F) rotationYaw += 180.0F;  float forward = 1.0F; if (mc.thePlayer.moveForward < 0.0F) { forward = -0.5F; } else if (mc.thePlayer.moveForward > 0.0F) { forward = 0.5F; }  if (mc.thePlayer.moveStrafing > 0.0F) rotationYaw -= 90.0F * forward;  if (mc.thePlayer.moveStrafing < 0.0F) rotationYaw += 90.0F * forward;  return Math.toRadians(rotationYaw); }
/*     */   public float getDirection2() { float yaw = mc.thePlayer.rotationYaw; float forward = mc.thePlayer.moveForward; float strafe = mc.thePlayer.moveStrafing; yaw += ((forward < 0.0F) ? '´' : false); if (strafe < 0.0F) yaw += (forward < 0.0F) ? -45.0F : ((forward == 0.0F) ? 90.0F : 45.0F);  if (strafe > 0.0F) yaw -= (forward < 0.0F) ? -45.0F : ((forward == 0.0F) ? 90.0F : 45.0F);  return yaw * 0.017453292F; }
/* 276 */   public void strafe() { strafe(getSpeed()); } public final void strafe(float speed) { mc.thePlayer.motionX = -Math.sin(getDirection()) * speed; mc.thePlayer.motionZ = Math.cos(getDirection()) * speed; } public boolean isMovingKeyBindingActive() { return (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()); } public double getJumpBoostModifier(double baseJumpHeight) { if (mc.thePlayer.isPotionActive(Potion.jump)) { int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier(); baseJumpHeight += ((amplifier + 1) * 0.1F); }  return baseJumpHeight; } public float getSpeed() { return (float)Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ); } public void setSpeed(double speed) { mc.thePlayer.motionX = -Math.sin(getDirection2()) * speed; mc.thePlayer.motionZ = Math.cos(getDirection2()) * speed; } public void toFwd(double speed) { float yaw = mc.thePlayer.rotationYaw * 0.017453292F; mc.thePlayer.motionX -= Math.sin(yaw) * speed; mc.thePlayer.motionZ += Math.cos(yaw) * speed; } public double defaultSpeed() { double baseSpeed = 0.2873D; if ((Minecraft.getMinecraft()).thePlayer.isPotionActive(Potion.moveSpeed)) { int amplifier = (Minecraft.getMinecraft()).thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier(); baseSpeed *= 1.0D + 0.2D * (amplifier + 1); }  return baseSpeed; } public double getBaseMovementSpeed() { double baseSpeed = 0.2873D; if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) { int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier(); baseSpeed *= 1.0D + 0.2D * (amplifier + 1); }  return baseSpeed; } public double getBaseMoveSpeed() { double baseSpeed = 0.2873D; if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) { int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier(); baseSpeed *= 1.0D + 0.2D * (amplifier + 1); }  return baseSpeed; } public boolean canSprint(EntityPlayerSP player, boolean omni) { return ((player.movementInput.moveForward >= 0.8F || (omni && isMoving(player))) && (player
/* 277 */       .getFoodStats().getFoodLevel() > 6.0F || player.capabilities.allowFlying) && 
/* 278 */       !player.isPotionActive(Potion.blindness) && !player.isCollidedHorizontally && 
/*     */       
/* 280 */       !player.isSneaking()); }
/*     */ 
/*     */   
/*     */   public boolean canSprint(EntityPlayerSP player) {
/* 284 */     return canSprint(player, true);
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed(EntityPlayerSP player) {
/* 288 */     double base = player.isSneaking() ? 0.0663000026345253D : (canSprint(player) ? 0.2872999894618988D : 0.221D);
/*     */     
/* 290 */     PotionEffect speed = player.getActivePotionEffect(Potion.moveSpeed);
/* 291 */     int moveSpeedAmp = (speed == null || speed.getDuration() < 3) ? 0 : (speed.getAmplifier() + 1);
/*     */     
/* 293 */     if (moveSpeedAmp > 0) {
/* 294 */       base *= 1.0D + 0.2D * moveSpeedAmp;
/*     */     }
/* 296 */     if (player.isInWater()) {
/* 297 */       base *= 0.5203620003898759D;
/* 298 */       int depthStriderLevel = EnchantmentHelper.getDepthStriderModifier((Entity)player);
/* 299 */       if (depthStriderLevel > 0) {
/* 300 */         base *= this.DEPTH_STRIDER_VALUES[depthStriderLevel];
/*     */       }
/*     */       
/* 303 */       return base * 0.5203620003898759D;
/* 304 */     }  if (player.isInLava()) {
/* 305 */       return base * 0.5203620003898759D;
/*     */     }
/* 307 */     return base;
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed(float customSpeed) {
/* 311 */     double baseSpeed = customSpeed;
/* 312 */     if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && 
/* 313 */       baseSpeed > 0.0D) {
/* 314 */       baseSpeed *= 1.0D + 0.2D * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
/*     */     }
/*     */     
/* 317 */     return baseSpeed;
/*     */   }
/*     */   
/*     */   public void setSpeedEvent(EventMove event, double speed) {
/* 321 */     double forward = mc.thePlayer.movementInput.moveForward;
/* 322 */     double strafe = mc.thePlayer.movementInput.moveStrafe;
/* 323 */     float yaw = mc.thePlayer.rotationYaw;
/* 324 */     if (forward == 0.0D && strafe == 0.0D) {
/* 325 */       event.setX(0.0D);
/* 326 */       event.setZ(0.0D);
/*     */     } else {
/* 328 */       if (forward != 0.0D) {
/* 329 */         if (strafe > 0.0D) {
/* 330 */           yaw += ((forward > 0.0D) ? -45 : 45);
/* 331 */         } else if (strafe < 0.0D) {
/* 332 */           yaw += ((forward > 0.0D) ? 45 : -45);
/*     */         } 
/* 334 */         strafe = 0.0D;
/* 335 */         if (forward > 0.0D) {
/* 336 */           forward = 1.0D;
/* 337 */         } else if (forward < 0.0D) {
/* 338 */           forward = -1.0D;
/*     */         } 
/*     */       } 
/* 341 */       event.setX(forward * speed * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F))));
/* 342 */       event.setZ(forward * speed * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((yaw + 90.0F))));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pause(EventMove e) {
/* 347 */     setSpeedEvent(e, 0.0D);
/* 348 */     setSpeed(0.0D);
/*     */   }
/*     */   
/*     */   public void setMotion(double speed, boolean smoothStrafe) {
/* 352 */     double forward = mc.thePlayer.movementInput.moveForward;
/* 353 */     double strafe = mc.thePlayer.movementInput.moveStrafe;
/* 354 */     float yaw = mc.thePlayer.rotationYaw;
/* 355 */     int direction = smoothStrafe ? 45 : 90;
/* 356 */     if (forward == 0.0D && strafe == 0.0D) {
/* 357 */       mc.thePlayer.motionX = 0.0D;
/* 358 */       mc.thePlayer.motionZ = 0.0D;
/*     */     } else {
/* 360 */       if (forward != 0.0D) {
/* 361 */         if (strafe > 0.0D) {
/* 362 */           yaw += ((forward > 0.0D) ? -direction : direction);
/* 363 */         } else if (strafe < 0.0D) {
/* 364 */           yaw += ((forward > 0.0D) ? direction : -direction);
/*     */         } 
/*     */         
/* 367 */         strafe = 0.0D;
/* 368 */         if (forward > 0.0D) {
/* 369 */           forward = 1.0D;
/* 370 */         } else if (forward < 0.0D) {
/* 371 */           forward = -1.0D;
/*     */         } 
/*     */       } 
/*     */       
/* 375 */       mc.thePlayer.motionX = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));
/* 376 */       mc.thePlayer.motionZ = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMotion(EventMove event, double speed, double motion, boolean smoothStrafe) {
/* 382 */     double forward = mc.thePlayer.movementInput.moveForward;
/* 383 */     double strafe = mc.thePlayer.movementInput.moveStrafe;
/* 384 */     double yaw = mc.thePlayer.rotationYaw;
/* 385 */     int direction = smoothStrafe ? 45 : 90;
/* 386 */     if (forward == 0.0D && strafe == 0.0D) {
/* 387 */       event.setX(0.0D);
/* 388 */       event.setZ(0.0D);
/*     */     } else {
/* 390 */       if (forward != 0.0D) {
/* 391 */         if (strafe > 0.0D) {
/* 392 */           yaw += ((forward > 0.0D) ? -direction : direction);
/* 393 */         } else if (strafe < 0.0D) {
/* 394 */           yaw += ((forward > 0.0D) ? direction : -direction);
/*     */         } 
/*     */         
/* 397 */         strafe = 0.0D;
/* 398 */         if (forward > 0.0D) {
/* 399 */           forward = 1.0D;
/* 400 */         } else if (forward < 0.0D) {
/* 401 */           forward = -1.0D;
/*     */         } 
/*     */       } 
/*     */       
/* 405 */       double cos = Math.cos(Math.toRadians(yaw + 90.0D));
/* 406 */       double sin = Math.sin(Math.toRadians(yaw + 90.0D));
/* 407 */       event.setX((forward * speed * cos + strafe * speed * sin) * motion);
/* 408 */       event.setZ((forward * speed * sin - strafe * speed * cos) * motion);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMotion(EventMove event, double speed) {
/* 414 */     double forward = mc.thePlayer.movementInput.moveForward;
/* 415 */     double strafe = mc.thePlayer.movementInput.moveStrafe;
/* 416 */     float yaw = mc.thePlayer.rotationYaw;
/*     */     
/* 418 */     event.setX(mc.thePlayer.motionX = 0.0D);
/* 419 */     event.setZ(mc.thePlayer.motionZ = 0.0D);
/*     */     
/* 421 */     if (forward != 0.0D) {
/* 422 */       if (strafe > 0.0D) {
/* 423 */         yaw += ((forward > 0.0D) ? -45 : 45);
/* 424 */       } else if (strafe < 0.0D) {
/* 425 */         yaw += ((forward > 0.0D) ? 45 : -45);
/*     */       } 
/* 427 */       strafe = 0.0D;
/* 428 */       if (forward > 0.0D) {
/* 429 */         forward = 1.0D;
/* 430 */       } else if (forward < 0.0D) {
/* 431 */         forward = -1.0D;
/*     */       } 
/*     */     } 
/* 434 */     event.setX(mc.thePlayer.motionX = forward * speed * Math.cos((float)Math.toRadians((yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F))));
/* 435 */     event.setZ(mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * Math.cos((float)Math.toRadians((yaw + 90.0F))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMotion(double speed) {
/* 441 */     double forward = mc.thePlayer.movementInput.moveForward;
/* 442 */     double strafe = mc.thePlayer.movementInput.moveStrafe;
/* 443 */     float yaw = mc.thePlayer.rotationYaw;
/* 444 */     if (forward == 0.0D && strafe == 0.0D) {
/* 445 */       mc.thePlayer.motionX = 0.0D;
/* 446 */       mc.thePlayer.motionZ = 0.0D;
/*     */     } else {
/* 448 */       if (forward != 0.0D) {
/* 449 */         if (strafe > 0.0D) {
/* 450 */           yaw += ((forward > 0.0D) ? -45 : 45);
/* 451 */         } else if (strafe < 0.0D) {
/* 452 */           yaw += ((forward > 0.0D) ? 45 : -45);
/*     */         } 
/* 454 */         strafe = 0.0D;
/* 455 */         if (forward > 0.0D) {
/* 456 */           forward = 1.0D;
/* 457 */         } else if (forward < 0.0D) {
/* 458 */           forward = -1.0D;
/*     */         } 
/*     */       } 
/* 461 */       double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 462 */       double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 463 */       mc.thePlayer.motionX = forward * speed * cos + strafe * speed * sin;
/* 464 */       mc.thePlayer.motionZ = forward * speed * sin - strafe * speed * cos;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMoveSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
/* 469 */     double forward = pseudoForward;
/* 470 */     double strafe = pseudoStrafe;
/* 471 */     float yaw = pseudoYaw;
/* 472 */     if (forward != 0.0D) {
/* 473 */       if (strafe > 0.0D) {
/* 474 */         yaw += ((forward > 0.0D) ? -45 : 45);
/* 475 */       } else if (strafe < 0.0D) {
/* 476 */         yaw += ((forward > 0.0D) ? 45 : -45);
/*     */       } 
/* 478 */       strafe = 0.0D;
/* 479 */       if (forward > 0.0D) {
/* 480 */         forward = 1.0D;
/* 481 */       } else if (forward < 0.0D) {
/* 482 */         forward = -1.0D;
/*     */       } 
/*     */     } 
/* 485 */     if (strafe > 0.0D) {
/* 486 */       strafe = 1.0D;
/* 487 */     } else if (strafe < 0.0D) {
/* 488 */       strafe = -1.0D;
/*     */     } 
/* 490 */     double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 491 */     double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 492 */     moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
/* 493 */     moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
/*     */   }
/*     */   
/*     */   public void setMoveSpeed(EventMove moveEvent, double moveSpeed) {
/* 497 */     float rotationYaw = (Minecraft.getMinecraft()).thePlayer.rotationYaw;
/* 498 */     double pseudoStrafe = mc.thePlayer.movementInput.moveStrafe;
/* 499 */     setMoveSpeed(moveEvent, moveSpeed, rotationYaw, pseudoStrafe, mc.thePlayer.movementInput.moveForward);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 504 */     mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
/*     */   }
/*     */   
/*     */   public double getPredictedMotionY(double motionY) {
/* 508 */     return (motionY - 0.08D) * 0.9800000190734863D;
/*     */   }
/*     */   
/*     */   public void forward(double length) {
/* 512 */     double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
/* 513 */     mc.thePlayer.setPosition(mc.thePlayer.posX + -Math.sin(yaw) * length, mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(yaw) * length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMovementDirection(float forward, float strafing, float yaw) {
/* 519 */     if (forward == 0.0F && strafing == 0.0F) return yaw;
/*     */     
/* 521 */     boolean reversed = (forward < 0.0F);
/* 522 */     float strafingYaw = 90.0F * ((forward > 0.0F) ? 0.5F : (reversed ? -0.5F : 1.0F));
/*     */ 
/*     */     
/* 525 */     if (reversed)
/* 526 */       yaw += 180.0F; 
/* 527 */     if (strafing > 0.0F) {
/* 528 */       yaw -= strafingYaw;
/* 529 */     } else if (strafing < 0.0F) {
/* 530 */       yaw += strafingYaw;
/*     */     } 
/* 532 */     return yaw;
/*     */   }
/*     */   
/*     */   public float fallDistanceForDamage() {
/* 536 */     float fallDistanceReq = 3.0F;
/*     */     
/* 538 */     if (mc.thePlayer.isPotionActive(Potion.jump)) {
/* 539 */       int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
/* 540 */       fallDistanceReq += (amplifier + 1);
/*     */     } 
/*     */     
/* 543 */     return fallDistanceReq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double direction() {
/* 550 */     float rotationYaw = mc.thePlayer.movementYaw.floatValue();
/*     */     
/* 552 */     if (mc.thePlayer.moveForward < 0.0F) {
/* 553 */       rotationYaw += 180.0F;
/*     */     }
/*     */     
/* 556 */     float forward = 1.0F;
/*     */     
/* 558 */     if (mc.thePlayer.moveForward < 0.0F) {
/* 559 */       forward = -0.5F;
/* 560 */     } else if (mc.thePlayer.moveForward > 0.0F) {
/* 561 */       forward = 0.5F;
/*     */     } 
/*     */     
/* 564 */     if (mc.thePlayer.moveStrafing > 0.0F) {
/* 565 */       rotationYaw -= 90.0F * forward;
/*     */     }
/*     */     
/* 568 */     if (mc.thePlayer.moveStrafing < 0.0F) {
/* 569 */       rotationYaw += 90.0F * forward;
/*     */     }
/*     */     
/* 572 */     return Math.toRadians(rotationYaw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enoughMovementForSprinting() {
/* 581 */     return (Math.abs(mc.thePlayer.moveForward) >= 0.8F || Math.abs(mc.thePlayer.moveStrafing) >= 0.8F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSprint(boolean legit) {
/* 591 */     return legit ? ((mc.thePlayer.moveForward >= 0.8F && !mc.thePlayer.isCollidedHorizontally && (mc.thePlayer
/*     */       
/* 593 */       .getFoodStats().getFoodLevel() > 6 || mc.thePlayer.capabilities.allowFlying) && 
/* 594 */       !mc.thePlayer.isPotionActive(Potion.blindness) && 
/* 595 */       !mc.thePlayer.isUsingItem() && 
/* 596 */       !mc.thePlayer.isSneaking())) : 
/* 597 */       enoughMovementForSprinting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int depthStriderLevel() {
/* 606 */     return EnchantmentHelper.getDepthStriderModifier((Entity)mc.thePlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAllowedHorizontalDistance() {
/*     */     double horizontalDistance;
/* 616 */     boolean useBaseModifiers = false;
/*     */     
/* 618 */     if (mc.thePlayer.isInWeb) {
/* 619 */       horizontalDistance = 0.105D;
/* 620 */     } else if (PlayerUtil.inLiquid()) {
/* 621 */       horizontalDistance = 0.11500000208616258D;
/*     */       
/* 623 */       int depthStriderLevel = depthStriderLevel();
/* 624 */       if (depthStriderLevel > 0) {
/* 625 */         horizontalDistance *= this.MOD_DEPTH_STRIDER[depthStriderLevel];
/* 626 */         useBaseModifiers = true;
/*     */       }
/*     */     
/* 629 */     } else if (mc.thePlayer.isSneaking()) {
/* 630 */       horizontalDistance = 0.0663000026345253D;
/*     */     } else {
/* 632 */       horizontalDistance = 0.221D;
/* 633 */       useBaseModifiers = true;
/*     */     } 
/*     */     
/* 636 */     if (useBaseModifiers) {
/* 637 */       if (canSprint(false)) {
/* 638 */         horizontalDistance *= 1.2999999523162842D;
/*     */       }
/*     */       
/* 641 */       Scaffold scaffold = Scaffold.getInstance;
/*     */       
/* 643 */       if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getDuration() > 0 && !scaffold.isEnabled()) {
/* 644 */         horizontalDistance *= 1.0D + 0.2D * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
/*     */       }
/*     */       
/* 647 */       if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
/* 648 */         horizontalDistance = 0.29D;
/*     */       }
/*     */     } 
/*     */     
/* 652 */     Block below = PlayerUtil.blockRelativeToPlayer(0.0D, -1.0D, 0.0D);
/* 653 */     if (below == Blocks.ice || below == Blocks.packed_ice) {
/* 654 */       horizontalDistance *= 1.2D;
/*     */     }
/*     */     
/* 657 */     return horizontalDistance;
/*     */   }
/*     */   
/*     */   public double jumpMotion() {
/* 661 */     return jumpBoostMotion(0.41999998688697815D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double jumpBoostMotion(double motionY) {
/* 671 */     if (mc.thePlayer.isPotionActive(Potion.jump)) {
/* 672 */       return motionY + ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
/*     */     }
/*     */     
/* 675 */     return motionY;
/*     */   }
/*     */   
/*     */   public static boolean isOverVoid(Minecraft mc) {
/* 679 */     AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox();
/* 680 */     double height = bb.maxY - bb.minY;
/*     */     
/* 682 */     double offset = height;
/*     */     
/*     */     AxisAlignedBB bbPos;
/*     */     
/* 686 */     while (!mc.theWorld.checkBlockCollision(bbPos = bb.addCoord(0.0D, -offset, 0.0D))) {
/* 687 */       if (bbPos.minY <= 0.0D) return true;
/*     */       
/* 689 */       offset += height;
/*     */     } 
/*     */     
/* 692 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isOverVoid() {
/* 696 */     for (int i = (int)(mc.thePlayer.posY - 1.0D); i > 0; i--) {
/* 697 */       BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
/* 698 */       if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir)) {
/* 699 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 703 */     return true;
/*     */   }
/*     */   
/*     */   public Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
/* 707 */     return mc.theWorld.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double predictedMotion(double motion) {
/* 717 */     return (motion - 0.08D) * 0.9800000190734863D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double predictedMotion(double motion, int ticks) {
/* 726 */     if (ticks == 0) return motion; 
/* 727 */     double predicted = motion;
/*     */     
/* 729 */     for (int i = 0; i < ticks; i++) {
/* 730 */       predicted = (predicted - 0.08D) * 0.9800000190734863D;
/*     */     }
/*     */     
/* 733 */     return predicted;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\MoveUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */