/*     */ package net.minecraft.entity.boss;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.IMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntityDamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob {
/*  28 */   public double[][] ringBuffer = new double[64][3];
/*     */   
/*     */   public double targetX;
/*     */   public double targetY;
/*     */   public double targetZ;
/*  33 */   public int ringBufferIndex = -1;
/*     */ 
/*     */   
/*     */   public EntityDragonPart[] dragonPartArray;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartHead;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartBody;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartTail1;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartTail2;
/*     */   
/*     */   public EntityDragonPart dragonPartTail3;
/*     */   
/*     */   public EntityDragonPart dragonPartWing1;
/*     */   
/*     */   public EntityDragonPart dragonPartWing2;
/*     */   
/*     */   public float prevAnimTime;
/*     */   
/*     */   public float animTime;
/*     */   
/*     */   public boolean forceNewTarget;
/*     */   
/*     */   public boolean slowed;
/*     */   
/*     */   private Entity target;
/*     */   
/*     */   public int deathTicks;
/*     */   
/*     */   public EntityEnderCrystal healingEnderCrystal;
/*     */ 
/*     */   
/*     */   public EntityDragon(World worldIn) {
/*  72 */     super(worldIn);
/*  73 */     this.dragonPartArray = new EntityDragonPart[] { this.dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F), this.dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F) };
/*  74 */     setHealth(getMaxHealth());
/*  75 */     setSize(16.0F, 8.0F);
/*  76 */     this.noClip = true;
/*  77 */     this.isImmuneToFire = true;
/*  78 */     this.targetY = 100.0D;
/*  79 */     this.ignoreFrustumCheck = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  84 */     super.applyEntityAttributes();
/*  85 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  90 */     super.entityInit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_) {
/*  99 */     if (getHealth() <= 0.0F)
/*     */     {
/* 101 */       p_70974_2_ = 0.0F;
/*     */     }
/*     */     
/* 104 */     p_70974_2_ = 1.0F - p_70974_2_;
/* 105 */     int i = this.ringBufferIndex - p_70974_1_ & 0x3F;
/* 106 */     int j = this.ringBufferIndex - p_70974_1_ - 1 & 0x3F;
/* 107 */     double[] adouble = new double[3];
/* 108 */     double d0 = this.ringBuffer[i][0];
/* 109 */     double d1 = MathHelper.wrapAngleTo180_double(this.ringBuffer[j][0] - d0);
/* 110 */     adouble[0] = d0 + d1 * p_70974_2_;
/* 111 */     d0 = this.ringBuffer[i][1];
/* 112 */     d1 = this.ringBuffer[j][1] - d0;
/* 113 */     adouble[1] = d0 + d1 * p_70974_2_;
/* 114 */     adouble[2] = this.ringBuffer[i][2] + (this.ringBuffer[j][2] - this.ringBuffer[i][2]) * p_70974_2_;
/* 115 */     return adouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 124 */     if (this.worldObj.isRemote) {
/*     */       
/* 126 */       float f = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
/* 127 */       float f1 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
/*     */       
/* 129 */       if (f1 <= -0.3F && f >= -0.3F && !isSilent())
/*     */       {
/* 131 */         this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     this.prevAnimTime = this.animTime;
/*     */     
/* 137 */     if (getHealth() <= 0.0F) {
/*     */       
/* 139 */       float f11 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 140 */       float f13 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/* 141 */       float f14 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 142 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f11, this.posY + 2.0D + f13, this.posZ + f14, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     else {
/*     */       
/* 146 */       updateDragonEnderCrystal();
/* 147 */       float f10 = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
/* 148 */       f10 *= (float)Math.pow(2.0D, this.motionY);
/*     */       
/* 150 */       if (this.slowed) {
/*     */         
/* 152 */         this.animTime += f10 * 0.5F;
/*     */       }
/*     */       else {
/*     */         
/* 156 */         this.animTime += f10;
/*     */       } 
/*     */       
/* 159 */       this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
/*     */       
/* 161 */       if (isAIDisabled()) {
/*     */         
/* 163 */         this.animTime = 0.5F;
/*     */       }
/*     */       else {
/*     */         
/* 167 */         if (this.ringBufferIndex < 0)
/*     */         {
/* 169 */           for (int i = 0; i < this.ringBuffer.length; i++) {
/*     */             
/* 171 */             this.ringBuffer[i][0] = this.rotationYaw;
/* 172 */             this.ringBuffer[i][1] = this.posY;
/*     */           } 
/*     */         }
/*     */         
/* 176 */         if (++this.ringBufferIndex == this.ringBuffer.length)
/*     */         {
/* 178 */           this.ringBufferIndex = 0;
/*     */         }
/*     */         
/* 181 */         this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
/* 182 */         this.ringBuffer[this.ringBufferIndex][1] = this.posY;
/*     */         
/* 184 */         if (this.worldObj.isRemote) {
/*     */           
/* 186 */           if (this.newPosRotationIncrements > 0)
/*     */           {
/* 188 */             double d10 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
/* 189 */             double d0 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
/* 190 */             double d1 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
/* 191 */             double d2 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
/* 192 */             this.rotationYaw = (float)(this.rotationYaw + d2 / this.newPosRotationIncrements);
/* 193 */             this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
/* 194 */             this.newPosRotationIncrements--;
/* 195 */             setPosition(d10, d0, d1);
/* 196 */             setRotation(this.rotationYaw, this.rotationPitch);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 201 */           double d11 = this.targetX - this.posX;
/* 202 */           double d12 = this.targetY - this.posY;
/* 203 */           double d13 = this.targetZ - this.posZ;
/* 204 */           double d14 = d11 * d11 + d12 * d12 + d13 * d13;
/*     */           
/* 206 */           if (this.target != null) {
/*     */             
/* 208 */             this.targetX = this.target.posX;
/* 209 */             this.targetZ = this.target.posZ;
/* 210 */             double d3 = this.targetX - this.posX;
/* 211 */             double d5 = this.targetZ - this.posZ;
/* 212 */             double d7 = Math.sqrt(d3 * d3 + d5 * d5);
/* 213 */             double d8 = 0.4000000059604645D + d7 / 80.0D - 1.0D;
/*     */             
/* 215 */             if (d8 > 10.0D)
/*     */             {
/* 217 */               d8 = 10.0D;
/*     */             }
/*     */             
/* 220 */             this.targetY = (this.target.getEntityBoundingBox()).minY + d8;
/*     */           }
/*     */           else {
/*     */             
/* 224 */             this.targetX += this.rand.nextGaussian() * 2.0D;
/* 225 */             this.targetZ += this.rand.nextGaussian() * 2.0D;
/*     */           } 
/*     */           
/* 228 */           if (this.forceNewTarget || d14 < 100.0D || d14 > 22500.0D || this.isCollidedHorizontally || this.isCollidedVertically)
/*     */           {
/* 230 */             setNewTarget();
/*     */           }
/*     */           
/* 233 */           d12 /= MathHelper.sqrt_double(d11 * d11 + d13 * d13);
/* 234 */           float f17 = 0.6F;
/* 235 */           d12 = MathHelper.clamp_double(d12, -f17, f17);
/* 236 */           this.motionY += d12 * 0.10000000149011612D;
/* 237 */           this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
/* 238 */           double d4 = 180.0D - MathHelper.atan2(d11, d13) * 180.0D / Math.PI;
/* 239 */           double d6 = MathHelper.wrapAngleTo180_double(d4 - this.rotationYaw);
/*     */           
/* 241 */           if (d6 > 50.0D)
/*     */           {
/* 243 */             d6 = 50.0D;
/*     */           }
/*     */           
/* 246 */           if (d6 < -50.0D)
/*     */           {
/* 248 */             d6 = -50.0D;
/*     */           }
/*     */           
/* 251 */           Vec3 vec3 = (new Vec3(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ)).normalize();
/* 252 */           double d15 = -MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
/* 253 */           Vec3 vec31 = (new Vec3(MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), this.motionY, d15)).normalize();
/* 254 */           float f5 = ((float)vec31.dotProduct(vec3) + 0.5F) / 1.5F;
/*     */           
/* 256 */           if (f5 < 0.0F)
/*     */           {
/* 258 */             f5 = 0.0F;
/*     */           }
/*     */           
/* 261 */           this.randomYawVelocity *= 0.8F;
/* 262 */           float f6 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) + 1.0F;
/* 263 */           double d9 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) + 1.0D;
/*     */           
/* 265 */           if (d9 > 40.0D)
/*     */           {
/* 267 */             d9 = 40.0D;
/*     */           }
/*     */           
/* 270 */           this.randomYawVelocity = (float)(this.randomYawVelocity + d6 * 0.699999988079071D / d9 / f6);
/* 271 */           this.rotationYaw += this.randomYawVelocity * 0.1F;
/* 272 */           float f7 = (float)(2.0D / (d9 + 1.0D));
/* 273 */           float f8 = 0.06F;
/* 274 */           moveFlying(0.0F, -1.0F, f8 * (f5 * f7 + 1.0F - f7));
/*     */           
/* 276 */           if (this.slowed) {
/*     */             
/* 278 */             moveEntity(this.motionX * 0.800000011920929D, this.motionY * 0.800000011920929D, this.motionZ * 0.800000011920929D);
/*     */           }
/*     */           else {
/*     */             
/* 282 */             moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */           } 
/*     */           
/* 285 */           Vec3 vec32 = (new Vec3(this.motionX, this.motionY, this.motionZ)).normalize();
/* 286 */           float f9 = ((float)vec32.dotProduct(vec31) + 1.0F) / 2.0F;
/* 287 */           f9 = 0.8F + 0.15F * f9;
/* 288 */           this.motionX *= f9;
/* 289 */           this.motionZ *= f9;
/* 290 */           this.motionY *= 0.9100000262260437D;
/*     */         } 
/*     */         
/* 293 */         this.renderYawOffset = this.rotationYaw;
/* 294 */         this.dragonPartHead.width = this.dragonPartHead.height = 3.0F;
/* 295 */         this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
/* 296 */         this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
/* 297 */         this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
/* 298 */         this.dragonPartBody.height = 3.0F;
/* 299 */         this.dragonPartBody.width = 5.0F;
/* 300 */         this.dragonPartWing1.height = 2.0F;
/* 301 */         this.dragonPartWing1.width = 4.0F;
/* 302 */         this.dragonPartWing2.height = 3.0F;
/* 303 */         this.dragonPartWing2.width = 4.0F;
/* 304 */         float f12 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
/* 305 */         float f2 = MathHelper.cos(f12);
/* 306 */         float f15 = -MathHelper.sin(f12);
/* 307 */         float f3 = this.rotationYaw * 3.1415927F / 180.0F;
/* 308 */         float f16 = MathHelper.sin(f3);
/* 309 */         float f4 = MathHelper.cos(f3);
/* 310 */         this.dragonPartBody.onUpdate();
/* 311 */         this.dragonPartBody.setLocationAndAngles(this.posX + (f16 * 0.5F), this.posY, this.posZ - (f4 * 0.5F), 0.0F, 0.0F);
/* 312 */         this.dragonPartWing1.onUpdate();
/* 313 */         this.dragonPartWing1.setLocationAndAngles(this.posX + (f4 * 4.5F), this.posY + 2.0D, this.posZ + (f16 * 4.5F), 0.0F, 0.0F);
/* 314 */         this.dragonPartWing2.onUpdate();
/* 315 */         this.dragonPartWing2.setLocationAndAngles(this.posX - (f4 * 4.5F), this.posY + 2.0D, this.posZ - (f16 * 4.5F), 0.0F, 0.0F);
/*     */         
/* 317 */         if (!this.worldObj.isRemote && this.hurtTime == 0) {
/*     */           
/* 319 */           collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing1.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/* 320 */           collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing2.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/* 321 */           attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartHead.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D)));
/*     */         } 
/*     */         
/* 324 */         double[] adouble1 = getMovementOffsets(5, 1.0F);
/* 325 */         double[] adouble = getMovementOffsets(0, 1.0F);
/* 326 */         float f18 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
/* 327 */         float f19 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
/* 328 */         this.dragonPartHead.onUpdate();
/* 329 */         this.dragonPartHead.setLocationAndAngles(this.posX + (f18 * 5.5F * f2), this.posY + adouble[1] - adouble1[1] + (f15 * 5.5F), this.posZ - (f19 * 5.5F * f2), 0.0F, 0.0F);
/*     */         
/* 331 */         for (int j = 0; j < 3; j++) {
/*     */           
/* 333 */           EntityDragonPart entitydragonpart = null;
/*     */           
/* 335 */           if (j == 0)
/*     */           {
/* 337 */             entitydragonpart = this.dragonPartTail1;
/*     */           }
/*     */           
/* 340 */           if (j == 1)
/*     */           {
/* 342 */             entitydragonpart = this.dragonPartTail2;
/*     */           }
/*     */           
/* 345 */           if (j == 2)
/*     */           {
/* 347 */             entitydragonpart = this.dragonPartTail3;
/*     */           }
/*     */           
/* 350 */           double[] adouble2 = getMovementOffsets(12 + (j << 1), 1.0F);
/* 351 */           float f20 = this.rotationYaw * 3.1415927F / 180.0F + simplifyAngle(adouble2[0] - adouble1[0]) * 3.1415927F / 180.0F;
/* 352 */           float f21 = MathHelper.sin(f20);
/* 353 */           float f22 = MathHelper.cos(f20);
/* 354 */           float f23 = 1.5F;
/* 355 */           float f24 = (j + 1) * 2.0F;
/* 356 */           entitydragonpart.onUpdate();
/* 357 */           entitydragonpart.setLocationAndAngles(this.posX - ((f16 * f23 + f21 * f24) * f2), this.posY + adouble2[1] - adouble1[1] - ((f24 + f23) * f15) + 1.5D, this.posZ + ((f4 * f23 + f22 * f24) * f2), 0.0F, 0.0F);
/*     */         } 
/*     */         
/* 360 */         if (!this.worldObj.isRemote)
/*     */         {
/* 362 */           this.slowed = destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox()) | destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateDragonEnderCrystal() {
/* 373 */     if (this.healingEnderCrystal != null)
/*     */     {
/* 375 */       if (this.healingEnderCrystal.isDead) {
/*     */         
/* 377 */         if (!this.worldObj.isRemote)
/*     */         {
/* 379 */           attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource((Explosion)null), 10.0F);
/*     */         }
/*     */         
/* 382 */         this.healingEnderCrystal = null;
/*     */       }
/* 384 */       else if (this.ticksExisted % 10 == 0 && getHealth() < getMaxHealth()) {
/*     */         
/* 386 */         setHealth(getHealth() + 1.0F);
/*     */       } 
/*     */     }
/*     */     
/* 390 */     if (this.rand.nextInt(10) == 0) {
/*     */       
/* 392 */       float f = 32.0F;
/* 393 */       List<EntityEnderCrystal> list = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, getEntityBoundingBox().expand(f, f, f));
/* 394 */       EntityEnderCrystal entityendercrystal = null;
/* 395 */       double d0 = Double.MAX_VALUE;
/*     */       
/* 397 */       for (EntityEnderCrystal entityendercrystal1 : list) {
/*     */         
/* 399 */         double d1 = entityendercrystal1.getDistanceSqToEntity((Entity)this);
/*     */         
/* 401 */         if (d1 < d0) {
/*     */           
/* 403 */           d0 = d1;
/* 404 */           entityendercrystal = entityendercrystal1;
/*     */         } 
/*     */       } 
/*     */       
/* 408 */       this.healingEnderCrystal = entityendercrystal;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collideWithEntities(List<Entity> p_70970_1_) {
/* 417 */     double d0 = ((this.dragonPartBody.getEntityBoundingBox()).minX + (this.dragonPartBody.getEntityBoundingBox()).maxX) / 2.0D;
/* 418 */     double d1 = ((this.dragonPartBody.getEntityBoundingBox()).minZ + (this.dragonPartBody.getEntityBoundingBox()).maxZ) / 2.0D;
/*     */     
/* 420 */     for (Entity entity : p_70970_1_) {
/*     */       
/* 422 */       if (entity instanceof EntityLivingBase) {
/*     */         
/* 424 */         double d2 = entity.posX - d0;
/* 425 */         double d3 = entity.posZ - d1;
/* 426 */         double d4 = d2 * d2 + d3 * d3;
/* 427 */         entity.addVelocity(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attackEntitiesInList(List<Entity> p_70971_1_) {
/* 437 */     for (int i = 0; i < p_70971_1_.size(); i++) {
/*     */       
/* 439 */       Entity entity = p_70971_1_.get(i);
/*     */       
/* 441 */       if (entity instanceof EntityLivingBase) {
/*     */         
/* 443 */         entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 10.0F);
/* 444 */         applyEnchantments((EntityLivingBase)this, entity);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNewTarget() {
/* 454 */     this.forceNewTarget = false;
/* 455 */     List<EntityPlayer> list = Lists.newArrayList(this.worldObj.playerEntities);
/*     */     
/* 457 */     list.removeIf(entityPlayer -> entityPlayer.isSpectator());
/*     */     
/* 459 */     if (this.rand.nextInt(2) == 0 && !list.isEmpty()) {
/*     */       
/* 461 */       this.target = (Entity)list.get(this.rand.nextInt(list.size()));
/*     */     } else {
/*     */       boolean flag;
/*     */ 
/*     */       
/*     */       do {
/* 467 */         this.targetX = 0.0D;
/* 468 */         this.targetY = (70.0F + this.rand.nextFloat() * 50.0F);
/* 469 */         this.targetZ = 0.0D;
/* 470 */         this.targetX += (this.rand.nextFloat() * 120.0F - 60.0F);
/* 471 */         this.targetZ += (this.rand.nextFloat() * 120.0F - 60.0F);
/* 472 */         double d0 = this.posX - this.targetX;
/* 473 */         double d1 = this.posY - this.targetY;
/* 474 */         double d2 = this.posZ - this.targetZ;
/* 475 */         flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
/*     */       }
/* 477 */       while (!flag);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 483 */       this.target = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float simplifyAngle(double p_70973_1_) {
/* 492 */     return (float)MathHelper.wrapAngleTo180_double(p_70973_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_) {
/* 500 */     int i = MathHelper.floor_double(p_70972_1_.minX);
/* 501 */     int j = MathHelper.floor_double(p_70972_1_.minY);
/* 502 */     int k = MathHelper.floor_double(p_70972_1_.minZ);
/* 503 */     int l = MathHelper.floor_double(p_70972_1_.maxX);
/* 504 */     int i1 = MathHelper.floor_double(p_70972_1_.maxY);
/* 505 */     int j1 = MathHelper.floor_double(p_70972_1_.maxZ);
/* 506 */     boolean flag = false;
/* 507 */     boolean flag1 = false;
/*     */     
/* 509 */     for (int k1 = i; k1 <= l; k1++) {
/*     */       
/* 511 */       for (int l1 = j; l1 <= i1; l1++) {
/*     */         
/* 513 */         for (int i2 = k; i2 <= j1; i2++) {
/*     */           
/* 515 */           BlockPos blockpos = new BlockPos(k1, l1, i2);
/* 516 */           Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */           
/* 518 */           if (block.getMaterial() != Material.air)
/*     */           {
/* 520 */             if (block != Blocks.barrier && block != Blocks.obsidian && block != Blocks.end_stone && block != Blocks.bedrock && block != Blocks.command_block && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
/*     */               
/* 522 */               flag1 = (this.worldObj.setBlockToAir(blockpos) || flag1);
/*     */             }
/*     */             else {
/*     */               
/* 526 */               flag = true;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 533 */     if (flag1) {
/*     */       
/* 535 */       double d0 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * this.rand.nextFloat();
/* 536 */       double d1 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * this.rand.nextFloat();
/* 537 */       double d2 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * this.rand.nextFloat();
/* 538 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 541 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFromPart(EntityDragonPart dragonPart, DamageSource source, float p_70965_3_) {
/* 546 */     if (dragonPart != this.dragonPartHead)
/*     */     {
/* 548 */       p_70965_3_ = p_70965_3_ / 4.0F + 1.0F;
/*     */     }
/*     */     
/* 551 */     float f = this.rotationYaw * 3.1415927F / 180.0F;
/* 552 */     float f1 = MathHelper.sin(f);
/* 553 */     float f2 = MathHelper.cos(f);
/* 554 */     this.targetX = this.posX + (f1 * 5.0F) + ((this.rand.nextFloat() - 0.5F) * 2.0F);
/* 555 */     this.targetY = this.posY + (this.rand.nextFloat() * 3.0F) + 1.0D;
/* 556 */     this.targetZ = this.posZ - (f2 * 5.0F) + ((this.rand.nextFloat() - 0.5F) * 2.0F);
/* 557 */     this.target = null;
/*     */     
/* 559 */     if (source.getEntity() instanceof EntityPlayer || source.isExplosion())
/*     */     {
/* 561 */       attackDragonFrom(source, p_70965_3_);
/*     */     }
/*     */     
/* 564 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 572 */     if (source instanceof EntityDamageSource && ((EntityDamageSource)source).getIsThornsDamage())
/*     */     {
/* 574 */       attackDragonFrom(source, amount);
/*     */     }
/*     */     
/* 577 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean attackDragonFrom(DamageSource source, float amount) {
/* 585 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillCommand() {
/* 593 */     setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDeathUpdate() {
/* 601 */     this.deathTicks++;
/*     */     
/* 603 */     if (this.deathTicks >= 180 && this.deathTicks <= 200) {
/*     */       
/* 605 */       float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 606 */       float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/* 607 */       float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 608 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 611 */     boolean flag = this.worldObj.getGameRules().getBoolean("doMobLoot");
/*     */     
/* 613 */     if (!this.worldObj.isRemote) {
/*     */       
/* 615 */       if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && flag) {
/*     */         
/* 617 */         int i = 1000;
/*     */         
/* 619 */         while (i > 0) {
/*     */           
/* 621 */           int k = EntityXPOrb.getXPSplit(i);
/* 622 */           i -= k;
/* 623 */           this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, k));
/*     */         } 
/*     */       } 
/*     */       
/* 627 */       if (this.deathTicks == 1)
/*     */       {
/* 629 */         this.worldObj.playBroadcastSound(1018, new BlockPos((Entity)this), 0);
/*     */       }
/*     */     } 
/*     */     
/* 633 */     moveEntity(0.0D, 0.10000000149011612D, 0.0D);
/* 634 */     this.renderYawOffset = this.rotationYaw += 20.0F;
/*     */     
/* 636 */     if (this.deathTicks == 200 && !this.worldObj.isRemote) {
/*     */       
/* 638 */       if (flag) {
/*     */         
/* 640 */         int j = 2000;
/*     */         
/* 642 */         while (j > 0) {
/*     */           
/* 644 */           int l = EntityXPOrb.getXPSplit(j);
/* 645 */           j -= l;
/* 646 */           this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, l));
/*     */         } 
/*     */       } 
/*     */       
/* 650 */       generatePortal(new BlockPos(this.posX, 64.0D, this.posZ));
/* 651 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generatePortal(BlockPos pos) {
/* 660 */     int i = 4;
/* 661 */     double d0 = 12.25D;
/* 662 */     double d1 = 6.25D;
/*     */     
/* 664 */     for (int j = -1; j <= 32; j++) {
/*     */       
/* 666 */       for (int k = -4; k <= 4; k++) {
/*     */         
/* 668 */         for (int l = -4; l <= 4; l++) {
/*     */           
/* 670 */           double d2 = (k * k + l * l);
/*     */           
/* 672 */           if (d2 <= 12.25D) {
/*     */             
/* 674 */             BlockPos blockpos = pos.add(k, j, l);
/*     */             
/* 676 */             if (j < 0) {
/*     */               
/* 678 */               if (d2 <= 6.25D)
/*     */               {
/* 680 */                 this.worldObj.setBlockState(blockpos, Blocks.bedrock.getDefaultState());
/*     */               }
/*     */             }
/* 683 */             else if (j > 0) {
/*     */               
/* 685 */               this.worldObj.setBlockState(blockpos, Blocks.air.getDefaultState());
/*     */             }
/* 687 */             else if (d2 > 6.25D) {
/*     */               
/* 689 */               this.worldObj.setBlockState(blockpos, Blocks.bedrock.getDefaultState());
/*     */             }
/*     */             else {
/*     */               
/* 693 */               this.worldObj.setBlockState(blockpos, Blocks.end_portal.getDefaultState());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 700 */     this.worldObj.setBlockState(pos, Blocks.bedrock.getDefaultState());
/* 701 */     this.worldObj.setBlockState(pos.up(), Blocks.bedrock.getDefaultState());
/* 702 */     BlockPos blockpos1 = pos.up(2);
/* 703 */     this.worldObj.setBlockState(blockpos1, Blocks.bedrock.getDefaultState());
/* 704 */     this.worldObj.setBlockState(blockpos1.west(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.EAST));
/* 705 */     this.worldObj.setBlockState(blockpos1.east(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.WEST));
/* 706 */     this.worldObj.setBlockState(blockpos1.north(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.SOUTH));
/* 707 */     this.worldObj.setBlockState(blockpos1.south(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.NORTH));
/* 708 */     this.worldObj.setBlockState(pos.up(3), Blocks.bedrock.getDefaultState());
/* 709 */     this.worldObj.setBlockState(pos.up(4), Blocks.dragon_egg.getDefaultState());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void despawnEntity() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity[] getParts() {
/* 724 */     return (Entity[])this.dragonPartArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 732 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 737 */     return this.worldObj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 745 */     return "mob.enderdragon.growl";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 753 */     return "mob.enderdragon.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 761 */     return 5.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\boss\EntityDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */