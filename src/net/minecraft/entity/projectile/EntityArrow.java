/*     */ package net.minecraft.entity.projectile;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityArrow extends Entity implements IProjectile {
/*  24 */   private int xTile = -1;
/*  25 */   private int yTile = -1;
/*  26 */   private int zTile = -1;
/*     */   
/*     */   private Block inTile;
/*     */   
/*     */   private int inData;
/*     */   
/*     */   public boolean inGround;
/*     */   
/*     */   public int canBePickedUp;
/*     */   
/*     */   public int arrowShake;
/*     */   
/*     */   public Entity shootingEntity;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*  41 */   private double damage = 2.0D;
/*     */ 
/*     */   
/*     */   private int knockbackStrength;
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn) {
/*  48 */     super(worldIn);
/*  49 */     this.renderDistanceWeight = 10.0D;
/*  50 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, double x, double y, double z) {
/*  55 */     super(worldIn);
/*  56 */     this.renderDistanceWeight = 10.0D;
/*  57 */     setSize(0.5F, 0.5F);
/*  58 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, EntityLivingBase shooter, EntityLivingBase target, float velocity, float innacuracy) {
/*  63 */     super(worldIn);
/*  64 */     this.renderDistanceWeight = 10.0D;
/*  65 */     this.shootingEntity = (Entity)shooter;
/*     */     
/*  67 */     if (shooter instanceof EntityPlayer)
/*     */     {
/*  69 */       this.canBePickedUp = 1;
/*     */     }
/*     */     
/*  72 */     this.posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
/*  73 */     double d0 = target.posX - shooter.posX;
/*  74 */     double d1 = (target.getEntityBoundingBox()).minY + (target.height / 3.0F) - this.posY;
/*  75 */     double d2 = target.posZ - shooter.posZ;
/*  76 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/*     */     
/*  78 */     if (d3 >= 1.0E-7D) {
/*     */       
/*  80 */       float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
/*  81 */       float f1 = (float)-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI);
/*  82 */       double d4 = d0 / d3;
/*  83 */       double d5 = d2 / d3;
/*  84 */       setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f, f1);
/*  85 */       float f2 = (float)(d3 * 0.20000000298023224D);
/*  86 */       setThrowableHeading(d0, d1 + f2, d2, velocity, innacuracy);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, EntityLivingBase shooter, float velocity) {
/*  92 */     super(worldIn);
/*  93 */     this.renderDistanceWeight = 10.0D;
/*  94 */     this.shootingEntity = (Entity)shooter;
/*     */     
/*  96 */     if (shooter instanceof EntityPlayer)
/*     */     {
/*  98 */       this.canBePickedUp = 1;
/*     */     }
/*     */     
/* 101 */     setSize(0.5F, 0.5F);
/* 102 */     setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
/* 103 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/* 104 */     this.posY -= 0.10000000149011612D;
/* 105 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/* 106 */     setPosition(this.posX, this.posY, this.posZ);
/* 107 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 108 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 109 */     this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F);
/* 110 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 1.5F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 115 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 123 */     float f = MathHelper.sqrt_double(x * x + y * y + z * z);
/* 124 */     x /= f;
/* 125 */     y /= f;
/* 126 */     z /= f;
/* 127 */     x += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 128 */     y += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 129 */     z += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 130 */     x *= velocity;
/* 131 */     y *= velocity;
/* 132 */     z *= velocity;
/* 133 */     this.motionX = x;
/* 134 */     this.motionY = y;
/* 135 */     this.motionZ = z;
/* 136 */     float f1 = MathHelper.sqrt_double(x * x + z * z);
/* 137 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 138 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 180.0D / Math.PI);
/* 139 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 144 */     setPosition(x, y, z);
/* 145 */     setRotation(yaw, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 153 */     this.motionX = x;
/* 154 */     this.motionY = y;
/* 155 */     this.motionZ = z;
/*     */     
/* 157 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 159 */       float f = MathHelper.sqrt_double(x * x + z * z);
/* 160 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 161 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f) * 180.0D / Math.PI);
/* 162 */       this.prevRotationPitch = this.rotationPitch;
/* 163 */       this.prevRotationYaw = this.rotationYaw;
/* 164 */       setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 165 */       this.ticksInGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 174 */     super.onUpdate();
/*     */     
/* 176 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 178 */       float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 179 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/* 180 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 183 */     BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
/* 184 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 185 */     Block block = iblockstate.getBlock();
/*     */     
/* 187 */     if (block.getMaterial() != Material.air) {
/*     */       
/* 189 */       block.setBlockBoundsBasedOnState((IBlockAccess)this.worldObj, blockpos);
/* 190 */       AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);
/*     */       
/* 192 */       if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ)))
/*     */       {
/* 194 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 198 */     if (this.arrowShake > 0)
/*     */     {
/* 200 */       this.arrowShake--;
/*     */     }
/*     */     
/* 203 */     if (this.inGround) {
/*     */       
/* 205 */       int j = block.getMetaFromState(iblockstate);
/*     */       
/* 207 */       if (block == this.inTile && j == this.inData)
/*     */       {
/* 209 */         this.ticksInGround++;
/*     */         
/* 211 */         if (this.ticksInGround >= 1200)
/*     */         {
/* 213 */           setDead();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 218 */         this.inGround = false;
/* 219 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 220 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 221 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 222 */         this.ticksInGround = 0;
/* 223 */         this.ticksInAir = 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 228 */       this.ticksInAir++;
/* 229 */       Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 230 */       Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 231 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
/* 232 */       vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 233 */       vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 235 */       if (movingobjectposition != null)
/*     */       {
/* 237 */         vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 240 */       Entity entity = null;
/* 241 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 242 */       double d0 = 0.0D;
/*     */       
/* 244 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 246 */         Entity entity1 = list.get(i);
/*     */         
/* 248 */         if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
/*     */           
/* 250 */           float f1 = 0.3F;
/* 251 */           AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/* 252 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
/*     */           
/* 254 */           if (movingobjectposition1 != null) {
/*     */             
/* 256 */             double d1 = vec31.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 258 */             if (d1 < d0 || d0 == 0.0D) {
/*     */               
/* 260 */               entity = entity1;
/* 261 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 267 */       if (entity != null)
/*     */       {
/* 269 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 272 */       if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
/*     */         
/* 274 */         EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
/*     */         
/* 276 */         if (entityplayer.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer)))
/*     */         {
/* 278 */           movingobjectposition = null;
/*     */         }
/*     */       } 
/*     */       
/* 282 */       if (movingobjectposition != null)
/*     */       {
/* 284 */         if (movingobjectposition.entityHit != null) {
/*     */           DamageSource damagesource;
/* 286 */           float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 287 */           int l = MathHelper.ceiling_double_int(f2 * this.damage);
/*     */           
/* 289 */           if (getIsCritical())
/*     */           {
/* 291 */             l += this.rand.nextInt(l / 2 + 2);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 296 */           if (this.shootingEntity == null) {
/*     */             
/* 298 */             damagesource = DamageSource.causeArrowDamage(this, this);
/*     */           }
/*     */           else {
/*     */             
/* 302 */             damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
/*     */           } 
/*     */           
/* 305 */           if (isBurning() && !(movingobjectposition.entityHit instanceof net.minecraft.entity.monster.EntityEnderman))
/*     */           {
/* 307 */             movingobjectposition.entityHit.setFire(5);
/*     */           }
/*     */           
/* 310 */           if (movingobjectposition.entityHit.attackEntityFrom(damagesource, l))
/*     */           {
/* 312 */             if (movingobjectposition.entityHit instanceof EntityLivingBase) {
/*     */               
/* 314 */               EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
/*     */               
/* 316 */               if (!this.worldObj.isRemote)
/*     */               {
/* 318 */                 entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
/*     */               }
/*     */               
/* 321 */               if (this.knockbackStrength > 0) {
/*     */                 
/* 323 */                 float f7 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */                 
/* 325 */                 if (f7 > 0.0F)
/*     */                 {
/* 327 */                   movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f7, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f7);
/*     */                 }
/*     */               } 
/*     */               
/* 331 */               if (this.shootingEntity instanceof EntityLivingBase) {
/*     */                 
/* 333 */                 EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
/* 334 */                 EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, (Entity)entitylivingbase);
/*     */               } 
/*     */               
/* 337 */               if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
/*     */               {
/* 339 */                 ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(6, 0.0F));
/*     */               }
/*     */             } 
/*     */             
/* 343 */             playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/*     */             
/* 345 */             if (!(movingobjectposition.entityHit instanceof net.minecraft.entity.monster.EntityEnderman))
/*     */             {
/* 347 */               setDead();
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 352 */             this.motionX *= -0.10000000149011612D;
/* 353 */             this.motionY *= -0.10000000149011612D;
/* 354 */             this.motionZ *= -0.10000000149011612D;
/* 355 */             this.rotationYaw += 180.0F;
/* 356 */             this.prevRotationYaw += 180.0F;
/* 357 */             this.ticksInAir = 0;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 362 */           BlockPos blockpos1 = movingobjectposition.getBlockPos();
/* 363 */           this.xTile = blockpos1.getX();
/* 364 */           this.yTile = blockpos1.getY();
/* 365 */           this.zTile = blockpos1.getZ();
/* 366 */           IBlockState iblockstate1 = this.worldObj.getBlockState(blockpos1);
/* 367 */           this.inTile = iblockstate1.getBlock();
/* 368 */           this.inData = this.inTile.getMetaFromState(iblockstate1);
/* 369 */           this.motionX = (float)(movingobjectposition.hitVec.xCoord - this.posX);
/* 370 */           this.motionY = (float)(movingobjectposition.hitVec.yCoord - this.posY);
/* 371 */           this.motionZ = (float)(movingobjectposition.hitVec.zCoord - this.posZ);
/* 372 */           float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 373 */           this.posX -= this.motionX / f5 * 0.05000000074505806D;
/* 374 */           this.posY -= this.motionY / f5 * 0.05000000074505806D;
/* 375 */           this.posZ -= this.motionZ / f5 * 0.05000000074505806D;
/* 376 */           playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 377 */           this.inGround = true;
/* 378 */           this.arrowShake = 7;
/* 379 */           setIsCritical(false);
/*     */           
/* 381 */           if (this.inTile.getMaterial() != Material.air)
/*     */           {
/* 383 */             this.inTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate1, this);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 388 */       if (getIsCritical())
/*     */       {
/* 390 */         for (int k = 0; k < 4; k++)
/*     */         {
/* 392 */           this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * k / 4.0D, this.posY + this.motionY * k / 4.0D, this.posZ + this.motionZ * k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
/*     */         }
/*     */       }
/*     */       
/* 396 */       this.posX += this.motionX;
/* 397 */       this.posY += this.motionY;
/* 398 */       this.posZ += this.motionZ;
/* 399 */       float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 400 */       this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */       
/* 402 */       for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f3) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 407 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */       {
/* 409 */         this.prevRotationPitch += 360.0F;
/*     */       }
/*     */       
/* 412 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */       {
/* 414 */         this.prevRotationYaw -= 360.0F;
/*     */       }
/*     */       
/* 417 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */       {
/* 419 */         this.prevRotationYaw += 360.0F;
/*     */       }
/*     */       
/* 422 */       this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 423 */       this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 424 */       float f4 = 0.99F;
/* 425 */       float f6 = 0.05F;
/*     */       
/* 427 */       if (isInWater()) {
/*     */         
/* 429 */         for (int i1 = 0; i1 < 4; i1++) {
/*     */           
/* 431 */           float f8 = 0.25F;
/* 432 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f8, this.posY - this.motionY * f8, this.posZ - this.motionZ * f8, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */         
/* 435 */         f4 = 0.6F;
/*     */       } 
/*     */       
/* 438 */       if (isWet())
/*     */       {
/* 440 */         extinguish();
/*     */       }
/*     */       
/* 443 */       this.motionX *= f4;
/* 444 */       this.motionY *= f4;
/* 445 */       this.motionZ *= f4;
/* 446 */       this.motionY -= f6;
/* 447 */       setPosition(this.posX, this.posY, this.posZ);
/* 448 */       doBlockCollisions();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 457 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 458 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 459 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 460 */     tagCompound.setShort("life", (short)this.ticksInGround);
/* 461 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 462 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 463 */     tagCompound.setByte("inData", (byte)this.inData);
/* 464 */     tagCompound.setByte("shake", (byte)this.arrowShake);
/* 465 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 466 */     tagCompound.setByte("pickup", (byte)this.canBePickedUp);
/* 467 */     tagCompound.setDouble("damage", this.damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 475 */     this.xTile = tagCompund.getShort("xTile");
/* 476 */     this.yTile = tagCompund.getShort("yTile");
/* 477 */     this.zTile = tagCompund.getShort("zTile");
/* 478 */     this.ticksInGround = tagCompund.getShort("life");
/*     */     
/* 480 */     if (tagCompund.hasKey("inTile", 8)) {
/*     */       
/* 482 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 486 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 489 */     this.inData = tagCompund.getByte("inData") & 0xFF;
/* 490 */     this.arrowShake = tagCompund.getByte("shake") & 0xFF;
/* 491 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/*     */     
/* 493 */     if (tagCompund.hasKey("damage", 99))
/*     */     {
/* 495 */       this.damage = tagCompund.getDouble("damage");
/*     */     }
/*     */     
/* 498 */     if (tagCompund.hasKey("pickup", 99)) {
/*     */       
/* 500 */       this.canBePickedUp = tagCompund.getByte("pickup");
/*     */     }
/* 502 */     else if (tagCompund.hasKey("player", 99)) {
/*     */       
/* 504 */       this.canBePickedUp = tagCompund.getBoolean("player") ? 1 : 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 513 */     if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
/*     */       
/* 515 */       boolean flag = (this.canBePickedUp == 1 || (this.canBePickedUp == 2 && entityIn.capabilities.isCreativeMode));
/*     */       
/* 517 */       if (this.canBePickedUp == 1 && !entityIn.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1)))
/*     */       {
/* 519 */         flag = false;
/*     */       }
/*     */       
/* 522 */       if (flag) {
/*     */         
/* 524 */         playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 525 */         entityIn.onItemPickup(this, 1);
/* 526 */         setDead();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 537 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(double damageIn) {
/* 542 */     this.damage = damageIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamage() {
/* 547 */     return this.damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKnockbackStrength(int knockbackStrengthIn) {
/* 555 */     this.knockbackStrength = knockbackStrengthIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackWithItem() {
/* 563 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 568 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsCritical(boolean critical) {
/* 576 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 578 */     if (critical) {
/*     */       
/* 580 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 584 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsCritical() {
/* 593 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/* 594 */     return ((b0 & 0x1) != 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\projectile\EntityArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */