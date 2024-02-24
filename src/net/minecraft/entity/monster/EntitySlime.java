/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearest;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class EntitySlime extends EntityLiving implements IMob {
/*     */   public float squishAmount;
/*     */   public float squishFactor;
/*     */   
/*     */   public EntitySlime(World worldIn) {
/*  33 */     super(worldIn);
/*  34 */     this.moveHelper = new SlimeMoveHelper(this);
/*  35 */     this.tasks.addTask(1, new AISlimeFloat(this));
/*  36 */     this.tasks.addTask(2, new AISlimeAttack(this));
/*  37 */     this.tasks.addTask(3, new AISlimeFaceRandom(this));
/*  38 */     this.tasks.addTask(5, new AISlimeHop(this));
/*  39 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIFindEntityNearestPlayer(this));
/*  40 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAIFindEntityNearest(this, EntityIronGolem.class));
/*     */   }
/*     */   public float prevSquishFactor; private boolean wasOnGround;
/*     */   
/*     */   protected void entityInit() {
/*  45 */     super.entityInit();
/*  46 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSlimeSize(int size) {
/*  51 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)size));
/*  52 */     setSize(0.51000005F * size, 0.51000005F * size);
/*  53 */     setPosition(this.posX, this.posY, this.posZ);
/*  54 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((size * size));
/*  55 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((0.2F + 0.1F * size));
/*  56 */     setHealth(getMaxHealth());
/*  57 */     this.experienceValue = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlimeSize() {
/*  65 */     return this.dataWatcher.getWatchableObjectByte(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  73 */     super.writeEntityToNBT(tagCompound);
/*  74 */     tagCompound.setInteger("Size", getSlimeSize() - 1);
/*  75 */     tagCompound.setBoolean("wasOnGround", this.wasOnGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  83 */     super.readEntityFromNBT(tagCompund);
/*  84 */     int i = tagCompund.getInteger("Size");
/*     */     
/*  86 */     if (i < 0)
/*     */     {
/*  88 */       i = 0;
/*     */     }
/*     */     
/*  91 */     setSlimeSize(i + 1);
/*  92 */     this.wasOnGround = tagCompund.getBoolean("wasOnGround");
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/*  97 */     return EnumParticleTypes.SLIME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJumpSound() {
/* 105 */     return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 113 */     if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && getSlimeSize() > 0)
/*     */     {
/* 115 */       this.isDead = true;
/*     */     }
/*     */     
/* 118 */     this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
/* 119 */     this.prevSquishFactor = this.squishFactor;
/* 120 */     super.onUpdate();
/*     */     
/* 122 */     if (this.onGround && !this.wasOnGround) {
/*     */       
/* 124 */       int i = getSlimeSize();
/*     */       
/* 126 */       for (int j = 0; j < i << 3; j++) {
/*     */         
/* 128 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 129 */         float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
/* 130 */         float f2 = MathHelper.sin(f) * i * 0.5F * f1;
/* 131 */         float f3 = MathHelper.cos(f) * i * 0.5F * f1;
/* 132 */         World world = this.worldObj;
/* 133 */         EnumParticleTypes enumparticletypes = getParticleType();
/* 134 */         double d0 = this.posX + f2;
/* 135 */         double d1 = this.posZ + f3;
/* 136 */         world.spawnParticle(enumparticletypes, d0, (getEntityBoundingBox()).minY, d1, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */       
/* 139 */       if (makesSoundOnLand())
/*     */       {
/* 141 */         playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/*     */       }
/*     */       
/* 144 */       this.squishAmount = -0.5F;
/*     */     }
/* 146 */     else if (!this.onGround && this.wasOnGround) {
/*     */       
/* 148 */       this.squishAmount = 1.0F;
/*     */     } 
/*     */     
/* 151 */     this.wasOnGround = this.onGround;
/* 152 */     alterSquishAmount();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void alterSquishAmount() {
/* 157 */     this.squishAmount *= 0.6F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getJumpDelay() {
/* 165 */     return this.rand.nextInt(20) + 10;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntitySlime createInstance() {
/* 170 */     return new EntitySlime(this.worldObj);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataWatcherUpdate(int dataID) {
/* 175 */     if (dataID == 16) {
/*     */       
/* 177 */       int i = getSlimeSize();
/* 178 */       setSize(0.51000005F * i, 0.51000005F * i);
/* 179 */       this.rotationYaw = this.rotationYawHead;
/* 180 */       this.renderYawOffset = this.rotationYawHead;
/*     */       
/* 182 */       if (isInWater() && this.rand.nextInt(20) == 0)
/*     */       {
/* 184 */         resetHeight();
/*     */       }
/*     */     } 
/*     */     
/* 188 */     super.onDataWatcherUpdate(dataID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 196 */     int i = getSlimeSize();
/*     */     
/* 198 */     if (!this.worldObj.isRemote && i > 1 && getHealth() <= 0.0F) {
/*     */       
/* 200 */       int j = 2 + this.rand.nextInt(3);
/*     */       
/* 202 */       for (int k = 0; k < j; k++) {
/*     */         
/* 204 */         float f = ((k % 2) - 0.5F) * i / 4.0F;
/* 205 */         float f1 = ((k / 2) - 0.5F) * i / 4.0F;
/* 206 */         EntitySlime entityslime = createInstance();
/*     */         
/* 208 */         if (hasCustomName())
/*     */         {
/* 210 */           entityslime.setCustomNameTag(getCustomNameTag());
/*     */         }
/*     */         
/* 213 */         if (isNoDespawnRequired())
/*     */         {
/* 215 */           entityslime.enablePersistence();
/*     */         }
/*     */         
/* 218 */         entityslime.setSlimeSize(i / 2);
/* 219 */         entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
/* 220 */         this.worldObj.spawnEntityInWorld((Entity)entityslime);
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEntityCollision(Entity entityIn) {
/* 232 */     super.applyEntityCollision(entityIn);
/*     */     
/* 234 */     if (entityIn instanceof EntityIronGolem && canDamagePlayer())
/*     */     {
/* 236 */       func_175451_e((EntityLivingBase)entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 245 */     if (canDamagePlayer())
/*     */     {
/* 247 */       func_175451_e((EntityLivingBase)entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175451_e(EntityLivingBase p_175451_1_) {
/* 253 */     int i = getSlimeSize();
/*     */     
/* 255 */     if (canEntityBeSeen((Entity)p_175451_1_) && getDistanceSqToEntity((Entity)p_175451_1_) < 0.6D * i * 0.6D * i && p_175451_1_.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), getAttackStrength())) {
/*     */       
/* 257 */       playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 258 */       applyEnchantments((EntityLivingBase)this, (Entity)p_175451_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 264 */     return 0.625F * this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDamagePlayer() {
/* 272 */     return (getSlimeSize() > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getAttackStrength() {
/* 280 */     return getSlimeSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 288 */     return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 296 */     return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 301 */     return (getSlimeSize() == 1) ? Items.slime_ball : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 309 */     BlockPos blockpos = new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ));
/* 310 */     Chunk chunk = this.worldObj.getChunkFromBlockCoords(blockpos);
/*     */     
/* 312 */     if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1)
/*     */     {
/* 314 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 318 */     if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
/*     */       
/* 320 */       BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(blockpos);
/*     */       
/* 322 */       if (biomegenbase == BiomeGenBase.swampland && this.posY > 50.0D && this.posY < 70.0D && this.rand.nextFloat() < 0.5F && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getLightFromNeighbors(new BlockPos((Entity)this)) <= this.rand.nextInt(8))
/*     */       {
/* 324 */         return super.getCanSpawnHere();
/*     */       }
/*     */       
/* 327 */       if (this.rand.nextInt(10) == 0 && chunk.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0D)
/*     */       {
/* 329 */         return super.getCanSpawnHere();
/*     */       }
/*     */     } 
/*     */     
/* 333 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 342 */     return 0.4F * getSlimeSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 351 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean makesSoundOnJump() {
/* 359 */     return (getSlimeSize() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean makesSoundOnLand() {
/* 367 */     return (getSlimeSize() > 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jump() {
/* 375 */     this.motionY = 0.41999998688697815D;
/* 376 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 385 */     int i = this.rand.nextInt(3);
/*     */     
/* 387 */     if (i < 2 && this.rand.nextFloat() < 0.5F * difficulty.getClampedAdditionalDifficulty())
/*     */     {
/* 389 */       i++;
/*     */     }
/*     */     
/* 392 */     int j = 1 << i;
/* 393 */     setSlimeSize(j);
/* 394 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */   
/*     */   static class AISlimeAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     private int field_179465_b;
/*     */     
/*     */     public AISlimeAttack(EntitySlime slimeIn) {
/* 404 */       this.slime = slimeIn;
/* 405 */       setMutexBits(2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 410 */       EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
/* 411 */       return (entitylivingbase == null) ? false : (!entitylivingbase.isEntityAlive() ? false : ((!(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 416 */       this.field_179465_b = 300;
/* 417 */       super.startExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 422 */       EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
/* 423 */       return (entitylivingbase == null) ? false : (!entitylivingbase.isEntityAlive() ? false : ((entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) ? false : ((--this.field_179465_b > 0))));
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 428 */       this.slime.faceEntity((Entity)this.slime.getAttackTarget(), 10.0F, 10.0F);
/* 429 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.slime.rotationYaw, this.slime.canDamagePlayer());
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeFaceRandom
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     private float field_179459_b;
/*     */     private int field_179460_c;
/*     */     
/*     */     public AISlimeFaceRandom(EntitySlime slimeIn) {
/* 441 */       this.slime = slimeIn;
/* 442 */       setMutexBits(2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 447 */       return (this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 452 */       if (--this.field_179460_c <= 0) {
/*     */         
/* 454 */         this.field_179460_c = 40 + this.slime.getRNG().nextInt(60);
/* 455 */         this.field_179459_b = this.slime.getRNG().nextInt(360);
/*     */       } 
/*     */       
/* 458 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.field_179459_b, false);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeFloat
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     
/*     */     public AISlimeFloat(EntitySlime slimeIn) {
/* 468 */       this.slime = slimeIn;
/* 469 */       setMutexBits(5);
/* 470 */       ((PathNavigateGround)slimeIn.getNavigator()).setCanSwim(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 475 */       return (this.slime.isInWater() || this.slime.isInLava());
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 480 */       if (this.slime.getRNG().nextFloat() < 0.8F)
/*     */       {
/* 482 */         this.slime.getJumpHelper().setJumping();
/*     */       }
/*     */       
/* 485 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeHop
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     
/*     */     public AISlimeHop(EntitySlime slimeIn) {
/* 495 */       this.slime = slimeIn;
/* 496 */       setMutexBits(5);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 501 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 506 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class SlimeMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private float field_179922_g;
/*     */     private int field_179924_h;
/*     */     private final EntitySlime slime;
/*     */     private boolean field_179923_j;
/*     */     
/*     */     public SlimeMoveHelper(EntitySlime slimeIn) {
/* 519 */       super(slimeIn);
/* 520 */       this.slime = slimeIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_179920_a(float p_179920_1_, boolean p_179920_2_) {
/* 525 */       this.field_179922_g = p_179920_1_;
/* 526 */       this.field_179923_j = p_179920_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSpeed(double speedIn) {
/* 531 */       this.speed = speedIn;
/* 532 */       this.update = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 537 */       this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0F);
/* 538 */       this.entity.rotationYawHead = this.entity.rotationYaw;
/* 539 */       this.entity.renderYawOffset = this.entity.rotationYaw;
/*     */       
/* 541 */       if (!this.update) {
/*     */         
/* 543 */         this.entity.setMoveForward(0.0F);
/*     */       }
/*     */       else {
/*     */         
/* 547 */         this.update = false;
/*     */         
/* 549 */         if (this.entity.onGround) {
/*     */           
/* 551 */           this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
/*     */           
/* 553 */           if (this.field_179924_h-- <= 0)
/*     */           {
/* 555 */             this.field_179924_h = this.slime.getJumpDelay();
/*     */             
/* 557 */             if (this.field_179923_j)
/*     */             {
/* 559 */               this.field_179924_h /= 3;
/*     */             }
/*     */             
/* 562 */             this.slime.getJumpHelper().setJumping();
/*     */             
/* 564 */             if (this.slime.makesSoundOnJump())
/*     */             {
/* 566 */               this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 571 */             this.slime.moveStrafing = this.slime.moveForward = 0.0F;
/* 572 */             this.entity.setAIMoveSpeed(0.0F);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 577 */           this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntitySlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */