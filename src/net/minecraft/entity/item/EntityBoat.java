/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityBoat extends Entity {
/*     */   private boolean isBoatEmpty;
/*     */   private double speedMultiplier;
/*     */   private int boatPosRotationIncrements;
/*     */   private double boatX;
/*     */   private double boatY;
/*     */   private double boatZ;
/*     */   private double boatYaw;
/*     */   private double boatPitch;
/*     */   private double velocityX;
/*     */   private double velocityY;
/*     */   private double velocityZ;
/*     */   
/*     */   public EntityBoat(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     this.isBoatEmpty = true;
/*  36 */     this.speedMultiplier = 0.07D;
/*  37 */     this.preventEntitySpawning = true;
/*  38 */     setSize(1.5F, 0.6F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  52 */     this.dataWatcher.addObject(17, new Integer(0));
/*  53 */     this.dataWatcher.addObject(18, new Integer(1));
/*  54 */     this.dataWatcher.addObject(19, new Float(0.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/*  63 */     return entityIn.getEntityBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/*  71 */     return getEntityBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityBoat(World worldIn, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
/*  84 */     this(worldIn);
/*  85 */     setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
/*  86 */     this.motionX = 0.0D;
/*  87 */     this.motionY = 0.0D;
/*  88 */     this.motionZ = 0.0D;
/*  89 */     this.prevPosX = p_i1705_2_;
/*  90 */     this.prevPosY = p_i1705_4_;
/*  91 */     this.prevPosZ = p_i1705_6_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/*  99 */     return -0.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 107 */     if (isEntityInvulnerable(source))
/*     */     {
/* 109 */       return false;
/*     */     }
/* 111 */     if (!this.worldObj.isRemote && !this.isDead) {
/*     */       
/* 113 */       if (this.riddenByEntity != null && this.riddenByEntity == source.getEntity() && source instanceof net.minecraft.util.EntityDamageSourceIndirect)
/*     */       {
/* 115 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 119 */       setForwardDirection(-getForwardDirection());
/* 120 */       setTimeSinceHit(10);
/* 121 */       setDamageTaken(getDamageTaken() + amount * 10.0F);
/* 122 */       setBeenAttacked();
/* 123 */       boolean flag = (source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode);
/*     */       
/* 125 */       if (flag || getDamageTaken() > 40.0F) {
/*     */         
/* 127 */         if (this.riddenByEntity != null)
/*     */         {
/* 129 */           this.riddenByEntity.mountEntity(this);
/*     */         }
/*     */         
/* 132 */         if (!flag && this.worldObj.getGameRules().getBoolean("doEntityDrops"))
/*     */         {
/* 134 */           dropItemWithOffset(Items.boat, 1, 0.0F);
/*     */         }
/*     */         
/* 137 */         setDead();
/*     */       } 
/*     */       
/* 140 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performHurtAnimation() {
/* 154 */     setForwardDirection(-getForwardDirection());
/* 155 */     setTimeSinceHit(10);
/* 156 */     setDamageTaken(getDamageTaken() * 11.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 164 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 171 */     this.prevPosX = this.posX = x;
/* 172 */     this.prevPosY = this.posY = y;
/* 173 */     this.prevPosZ = this.posZ = z;
/* 174 */     this.rotationYaw = yaw;
/* 175 */     this.rotationPitch = pitch;
/* 176 */     this.boatPosRotationIncrements = 0;
/* 177 */     setPosition(x, y, z);
/* 178 */     this.motionX = this.velocityX = 0.0D;
/* 179 */     this.motionY = this.velocityY = 0.0D;
/* 180 */     this.motionZ = this.velocityZ = 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (this.isBoatEmpty) {
/*     */       
/* 186 */       this.boatPosRotationIncrements = posRotationIncrements + 5;
/*     */     }
/*     */     else {
/*     */       
/* 190 */       double d0 = x - this.posX;
/* 191 */       double d1 = y - this.posY;
/* 192 */       double d2 = z - this.posZ;
/* 193 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */       
/* 195 */       if (d3 <= 1.0D) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 200 */       this.boatPosRotationIncrements = 3;
/*     */     } 
/*     */     
/* 203 */     this.boatX = x;
/* 204 */     this.boatY = y;
/* 205 */     this.boatZ = z;
/* 206 */     this.boatYaw = yaw;
/* 207 */     this.boatPitch = pitch;
/* 208 */     this.motionX = this.velocityX;
/* 209 */     this.motionY = this.velocityY;
/* 210 */     this.motionZ = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 219 */     this.velocityX = this.motionX = x;
/* 220 */     this.velocityY = this.motionY = y;
/* 221 */     this.velocityZ = this.motionZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 229 */     super.onUpdate();
/*     */     
/* 231 */     if (getTimeSinceHit() > 0)
/*     */     {
/* 233 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 236 */     if (getDamageTaken() > 0.0F)
/*     */     {
/* 238 */       setDamageTaken(getDamageTaken() - 1.0F);
/*     */     }
/*     */     
/* 241 */     this.prevPosX = this.posX;
/* 242 */     this.prevPosY = this.posY;
/* 243 */     this.prevPosZ = this.posZ;
/* 244 */     int i = 5;
/* 245 */     double d0 = 0.0D;
/*     */     
/* 247 */     for (int j = 0; j < i; j++) {
/*     */       
/* 249 */       double d1 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * j / i - 0.125D;
/* 250 */       double d3 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * (j + 1) / i - 0.125D;
/* 251 */       AxisAlignedBB axisalignedbb = new AxisAlignedBB((getEntityBoundingBox()).minX, d1, (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).maxX, d3, (getEntityBoundingBox()).maxZ);
/*     */       
/* 253 */       if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
/*     */       {
/* 255 */         d0 += 1.0D / i;
/*     */       }
/*     */     } 
/*     */     
/* 259 */     double d9 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */     
/* 261 */     if (d9 > 0.2975D) {
/*     */       
/* 263 */       double d2 = Math.cos(this.rotationYaw * Math.PI / 180.0D);
/* 264 */       double d4 = Math.sin(this.rotationYaw * Math.PI / 180.0D);
/*     */       
/* 266 */       for (int k = 0; k < 1.0D + d9 * 60.0D; k++) {
/*     */         
/* 268 */         double d5 = (this.rand.nextFloat() * 2.0F - 1.0F);
/* 269 */         double d6 = ((this.rand.nextInt(2) << 1) - 1) * 0.7D;
/*     */         
/* 271 */         if (this.rand.nextBoolean()) {
/*     */           
/* 273 */           double d7 = this.posX - d2 * d5 * 0.8D + d4 * d6;
/* 274 */           double d8 = this.posZ - d4 * d5 * 0.8D - d2 * d6;
/* 275 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, d7, this.posY - 0.125D, d8, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         }
/*     */         else {
/*     */           
/* 279 */           double d24 = this.posX + d2 + d4 * d5 * 0.7D;
/* 280 */           double d25 = this.posZ + d4 - d2 * d5 * 0.7D;
/* 281 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, d24, this.posY - 0.125D, d25, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     if (this.worldObj.isRemote && this.isBoatEmpty) {
/*     */       
/* 288 */       if (this.boatPosRotationIncrements > 0)
/*     */       {
/* 290 */         double d12 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
/* 291 */         double d16 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
/* 292 */         double d19 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
/* 293 */         double d22 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
/* 294 */         this.rotationYaw = (float)(this.rotationYaw + d22 / this.boatPosRotationIncrements);
/* 295 */         this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
/* 296 */         this.boatPosRotationIncrements--;
/* 297 */         setPosition(d12, d16, d19);
/* 298 */         setRotation(this.rotationYaw, this.rotationPitch);
/*     */       }
/*     */       else
/*     */       {
/* 302 */         double d13 = this.posX + this.motionX;
/* 303 */         double d17 = this.posY + this.motionY;
/* 304 */         double d20 = this.posZ + this.motionZ;
/* 305 */         setPosition(d13, d17, d20);
/*     */         
/* 307 */         if (this.onGround) {
/*     */           
/* 309 */           this.motionX *= 0.5D;
/* 310 */           this.motionY *= 0.5D;
/* 311 */           this.motionZ *= 0.5D;
/*     */         } 
/*     */         
/* 314 */         this.motionX *= 0.9900000095367432D;
/* 315 */         this.motionY *= 0.949999988079071D;
/* 316 */         this.motionZ *= 0.9900000095367432D;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 321 */       if (d0 < 1.0D) {
/*     */         
/* 323 */         double d10 = d0 * 2.0D - 1.0D;
/* 324 */         this.motionY += 0.03999999910593033D * d10;
/*     */       }
/*     */       else {
/*     */         
/* 328 */         if (this.motionY < 0.0D)
/*     */         {
/* 330 */           this.motionY /= 2.0D;
/*     */         }
/*     */         
/* 333 */         this.motionY += 0.007000000216066837D;
/*     */       } 
/*     */       
/* 336 */       if (this.riddenByEntity instanceof EntityLivingBase) {
/*     */         
/* 338 */         EntityLivingBase entitylivingbase = (EntityLivingBase)this.riddenByEntity;
/* 339 */         float f = this.riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
/* 340 */         this.motionX += -Math.sin((f * 3.1415927F / 180.0F)) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
/* 341 */         this.motionZ += Math.cos((f * 3.1415927F / 180.0F)) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
/*     */       } 
/*     */       
/* 344 */       double d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */       
/* 346 */       if (d11 > 0.35D) {
/*     */         
/* 348 */         double d14 = 0.35D / d11;
/* 349 */         this.motionX *= d14;
/* 350 */         this.motionZ *= d14;
/* 351 */         d11 = 0.35D;
/*     */       } 
/*     */       
/* 354 */       if (d11 > d9 && this.speedMultiplier < 0.35D) {
/*     */         
/* 356 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/*     */         
/* 358 */         if (this.speedMultiplier > 0.35D)
/*     */         {
/* 360 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 365 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/*     */         
/* 367 */         if (this.speedMultiplier < 0.07D)
/*     */         {
/* 369 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */       
/* 373 */       for (int i1 = 0; i1 < 4; i1++) {
/*     */         
/* 375 */         int l1 = MathHelper.floor_double(this.posX + ((i1 % 2) - 0.5D) * 0.8D);
/* 376 */         int i2 = MathHelper.floor_double(this.posZ + ((i1 / 2) - 0.5D) * 0.8D);
/*     */         
/* 378 */         for (int j2 = 0; j2 < 2; j2++) {
/*     */           
/* 380 */           int l = MathHelper.floor_double(this.posY) + j2;
/* 381 */           BlockPos blockpos = new BlockPos(l1, l, i2);
/* 382 */           Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */           
/* 384 */           if (block == Blocks.snow_layer) {
/*     */             
/* 386 */             this.worldObj.setBlockToAir(blockpos);
/* 387 */             this.isCollidedHorizontally = false;
/*     */           }
/* 389 */           else if (block == Blocks.waterlily) {
/*     */             
/* 391 */             this.worldObj.destroyBlock(blockpos, true);
/* 392 */             this.isCollidedHorizontally = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 397 */       if (this.onGround) {
/*     */         
/* 399 */         this.motionX *= 0.5D;
/* 400 */         this.motionY *= 0.5D;
/* 401 */         this.motionZ *= 0.5D;
/*     */       } 
/*     */       
/* 404 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */       
/* 406 */       if (this.isCollidedHorizontally && d9 > 0.2975D) {
/*     */         
/* 408 */         if (!this.worldObj.isRemote && !this.isDead) {
/*     */           
/* 410 */           setDead();
/*     */           
/* 412 */           if (this.worldObj.getGameRules().getBoolean("doEntityDrops"))
/*     */           {
/* 414 */             for (int j1 = 0; j1 < 3; j1++)
/*     */             {
/* 416 */               dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
/*     */             }
/*     */             
/* 419 */             for (int k1 = 0; k1 < 2; k1++)
/*     */             {
/* 421 */               dropItemWithOffset(Items.stick, 1, 0.0F);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 428 */         this.motionX *= 0.9900000095367432D;
/* 429 */         this.motionY *= 0.949999988079071D;
/* 430 */         this.motionZ *= 0.9900000095367432D;
/*     */       } 
/*     */       
/* 433 */       this.rotationPitch = 0.0F;
/* 434 */       double d15 = this.rotationYaw;
/* 435 */       double d18 = this.prevPosX - this.posX;
/* 436 */       double d21 = this.prevPosZ - this.posZ;
/*     */       
/* 438 */       if (d18 * d18 + d21 * d21 > 0.001D)
/*     */       {
/* 440 */         d15 = (float)(MathHelper.atan2(d21, d18) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 443 */       double d23 = MathHelper.wrapAngleTo180_double(d15 - this.rotationYaw);
/*     */       
/* 445 */       if (d23 > 20.0D)
/*     */       {
/* 447 */         d23 = 20.0D;
/*     */       }
/*     */       
/* 450 */       if (d23 < -20.0D)
/*     */       {
/* 452 */         d23 = -20.0D;
/*     */       }
/*     */       
/* 455 */       this.rotationYaw = (float)(this.rotationYaw + d23);
/* 456 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */       
/* 458 */       if (!this.worldObj.isRemote) {
/*     */         
/* 460 */         List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */         
/* 462 */         if (list != null && !list.isEmpty())
/*     */         {
/* 464 */           for (int k2 = 0; k2 < list.size(); k2++) {
/*     */             
/* 466 */             Entity entity = list.get(k2);
/*     */             
/* 468 */             if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat)
/*     */             {
/* 470 */               entity.applyEntityCollision(this);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 475 */         if (this.riddenByEntity != null && this.riddenByEntity.isDead)
/*     */         {
/* 477 */           this.riddenByEntity = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRiderPosition() {
/* 485 */     if (this.riddenByEntity != null) {
/*     */       
/* 487 */       double d0 = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
/* 488 */       double d1 = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
/* 489 */       this.riddenByEntity.setPosition(this.posX + d0, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 512 */     if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != playerIn)
/*     */     {
/* 514 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 518 */     if (!this.worldObj.isRemote)
/*     */     {
/* 520 */       playerIn.mountEntity(this);
/*     */     }
/*     */     
/* 523 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/* 529 */     if (onGroundIn) {
/*     */       
/* 531 */       if (this.fallDistance > 3.0F)
/*     */       {
/* 533 */         fall(this.fallDistance, 1.0F);
/*     */         
/* 535 */         if (!this.worldObj.isRemote && !this.isDead) {
/*     */           
/* 537 */           setDead();
/*     */           
/* 539 */           if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*     */             
/* 541 */             for (int i = 0; i < 3; i++)
/*     */             {
/* 543 */               dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
/*     */             }
/*     */             
/* 546 */             for (int j = 0; j < 2; j++)
/*     */             {
/* 548 */               dropItemWithOffset(Items.stick, 1, 0.0F);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 553 */         this.fallDistance = 0.0F;
/*     */       }
/*     */     
/* 556 */     } else if (this.worldObj.getBlockState((new BlockPos(this)).down()).getBlock().getMaterial() != Material.water && y < 0.0D) {
/*     */       
/* 558 */       this.fallDistance = (float)(this.fallDistance - y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamageTaken(float p_70266_1_) {
/* 567 */     this.dataWatcher.updateObject(19, Float.valueOf(p_70266_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamageTaken() {
/* 575 */     return this.dataWatcher.getWatchableObjectFloat(19);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeSinceHit(int p_70265_1_) {
/* 583 */     this.dataWatcher.updateObject(17, Integer.valueOf(p_70265_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimeSinceHit() {
/* 591 */     return this.dataWatcher.getWatchableObjectInt(17);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForwardDirection(int p_70269_1_) {
/* 599 */     this.dataWatcher.updateObject(18, Integer.valueOf(p_70269_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getForwardDirection() {
/* 607 */     return this.dataWatcher.getWatchableObjectInt(18);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsBoatEmpty(boolean p_70270_1_) {
/* 615 */     this.isBoatEmpty = p_70270_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */