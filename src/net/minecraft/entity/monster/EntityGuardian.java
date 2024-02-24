/*     */ package net.minecraft.entity.monster;
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.WeightedRandomFishable;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityGuardian extends EntityMob {
/*     */   private float field_175482_b;
/*     */   private float field_175484_c;
/*     */   private float field_175483_bk;
/*     */   private float field_175485_bl;
/*     */   
/*     */   public EntityGuardian(World worldIn) {
/*  41 */     super(worldIn);
/*  42 */     this.experienceValue = 10;
/*  43 */     setSize(0.85F, 0.85F);
/*  44 */     this.tasks.addTask(4, new AIGuardianAttack(this));
/*     */     EntityAIMoveTowardsRestriction entityaimovetowardsrestriction;
/*  46 */     this.tasks.addTask(5, (EntityAIBase)(entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D)));
/*  47 */     this.tasks.addTask(7, (EntityAIBase)(this.wander = new EntityAIWander(this, 1.0D, 80)));
/*  48 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  49 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityGuardian.class, 12.0F, 0.01F));
/*  50 */     this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  51 */     this.wander.setMutexBits(3);
/*  52 */     entityaimovetowardsrestriction.setMutexBits(3);
/*  53 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new GuardianTargetSelector(this)));
/*  54 */     this.moveHelper = new GuardianMoveHelper(this);
/*  55 */     this.field_175484_c = this.field_175482_b = this.rand.nextFloat();
/*     */   }
/*     */   private float field_175486_bm; private EntityLivingBase targetedEntity; private int field_175479_bo; private boolean field_175480_bp; EntityAIWander wander;
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  60 */     super.applyEntityAttributes();
/*  61 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
/*  62 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*  63 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
/*  64 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  72 */     super.readEntityFromNBT(tagCompund);
/*  73 */     setElder(tagCompund.getBoolean("Elder"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  81 */     super.writeEntityToNBT(tagCompound);
/*  82 */     tagCompound.setBoolean("Elder", isElder());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/*  90 */     return (PathNavigate)new PathNavigateSwimmer((EntityLiving)this, worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  95 */     super.entityInit();
/*  96 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*  97 */     this.dataWatcher.addObject(17, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSyncedFlagSet(int flagId) {
/* 105 */     return ((this.dataWatcher.getWatchableObjectInt(16) & flagId) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSyncedFlag(int flagId, boolean state) {
/* 113 */     int i = this.dataWatcher.getWatchableObjectInt(16);
/*     */     
/* 115 */     if (state) {
/*     */       
/* 117 */       this.dataWatcher.updateObject(16, Integer.valueOf(i | flagId));
/*     */     }
/*     */     else {
/*     */       
/* 121 */       this.dataWatcher.updateObject(16, Integer.valueOf(i & (flagId ^ 0xFFFFFFFF)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175472_n() {
/* 127 */     return isSyncedFlagSet(2);
/*     */   }
/*     */ 
/*     */   
/*     */   void func_175476_l(boolean p_175476_1_) {
/* 132 */     setSyncedFlag(2, p_175476_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_175464_ck() {
/* 137 */     return isElder() ? 60 : 80;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isElder() {
/* 142 */     return isSyncedFlagSet(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElder(boolean elder) {
/* 150 */     setSyncedFlag(4, elder);
/*     */     
/* 152 */     if (elder) {
/*     */       
/* 154 */       setSize(1.9975F, 1.9975F);
/* 155 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/* 156 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
/* 157 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
/* 158 */       enablePersistence();
/* 159 */       this.wander.setExecutionChance(400);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElder() {
/* 165 */     setElder(true);
/* 166 */     this.field_175486_bm = this.field_175485_bl = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   void setTargetedEntity(int entityId) {
/* 171 */     this.dataWatcher.updateObject(17, Integer.valueOf(entityId));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTargetedEntity() {
/* 176 */     return (this.dataWatcher.getWatchableObjectInt(17) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLivingBase getTargetedEntity() {
/* 181 */     if (!hasTargetedEntity())
/*     */     {
/* 183 */       return null;
/*     */     }
/* 185 */     if (this.worldObj.isRemote) {
/*     */       
/* 187 */       if (this.targetedEntity != null)
/*     */       {
/* 189 */         return this.targetedEntity;
/*     */       }
/*     */ 
/*     */       
/* 193 */       Entity entity = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
/*     */       
/* 195 */       if (entity instanceof EntityLivingBase) {
/*     */         
/* 197 */         this.targetedEntity = (EntityLivingBase)entity;
/* 198 */         return this.targetedEntity;
/*     */       } 
/*     */ 
/*     */       
/* 202 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     return getAttackTarget();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDataWatcherUpdate(int dataID) {
/* 214 */     super.onDataWatcherUpdate(dataID);
/*     */     
/* 216 */     if (dataID == 16) {
/*     */       
/* 218 */       if (isElder() && this.width < 1.0F)
/*     */       {
/* 220 */         setSize(1.9975F, 1.9975F);
/*     */       }
/*     */     }
/* 223 */     else if (dataID == 17) {
/*     */       
/* 225 */       this.field_175479_bo = 0;
/* 226 */       this.targetedEntity = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTalkInterval() {
/* 235 */     return 160;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 243 */     return !isInWater() ? "mob.guardian.land.idle" : (isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 251 */     return !isInWater() ? "mob.guardian.land.hit" : (isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 259 */     return !isInWater() ? "mob.guardian.land.death" : (isElder() ? "mob.guardian.elder.death" : "mob.guardian.death");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 273 */     return this.height * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 278 */     return (this.worldObj.getBlockState(pos).getBlock().getMaterial() == Material.water) ? (10.0F + this.worldObj.getLightBrightness(pos) - 0.5F) : super.getBlockPathWeight(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 287 */     if (this.worldObj.isRemote) {
/*     */       
/* 289 */       this.field_175484_c = this.field_175482_b;
/*     */       
/* 291 */       if (!isInWater()) {
/*     */         
/* 293 */         this.field_175483_bk = 2.0F;
/*     */         
/* 295 */         if (this.motionY > 0.0D && this.field_175480_bp && !isSilent())
/*     */         {
/* 297 */           this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.guardian.flop", 1.0F, 1.0F, false);
/*     */         }
/*     */         
/* 300 */         this.field_175480_bp = (this.motionY < 0.0D && this.worldObj.isBlockNormalCube((new BlockPos((Entity)this)).down(), false));
/*     */       }
/* 302 */       else if (func_175472_n()) {
/*     */         
/* 304 */         if (this.field_175483_bk < 0.5F)
/*     */         {
/* 306 */           this.field_175483_bk = 4.0F;
/*     */         }
/*     */         else
/*     */         {
/* 310 */           this.field_175483_bk += (0.5F - this.field_175483_bk) * 0.1F;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 315 */         this.field_175483_bk += (0.125F - this.field_175483_bk) * 0.2F;
/*     */       } 
/*     */       
/* 318 */       this.field_175482_b += this.field_175483_bk;
/* 319 */       this.field_175486_bm = this.field_175485_bl;
/*     */       
/* 321 */       if (!isInWater()) {
/*     */         
/* 323 */         this.field_175485_bl = this.rand.nextFloat();
/*     */       }
/* 325 */       else if (func_175472_n()) {
/*     */         
/* 327 */         this.field_175485_bl += (0.0F - this.field_175485_bl) * 0.25F;
/*     */       }
/*     */       else {
/*     */         
/* 331 */         this.field_175485_bl += (1.0F - this.field_175485_bl) * 0.06F;
/*     */       } 
/*     */       
/* 334 */       if (func_175472_n() && isInWater()) {
/*     */         
/* 336 */         Vec3 vec3 = getLook(0.0F);
/*     */         
/* 338 */         for (int i = 0; i < 2; i++)
/*     */         {
/* 340 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width - vec3.xCoord * 1.5D, this.posY + this.rand.nextDouble() * this.height - vec3.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width - vec3.zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */       } 
/*     */       
/* 344 */       if (hasTargetedEntity()) {
/*     */         
/* 346 */         if (this.field_175479_bo < func_175464_ck())
/*     */         {
/* 348 */           this.field_175479_bo++;
/*     */         }
/*     */         
/* 351 */         EntityLivingBase entitylivingbase = getTargetedEntity();
/*     */         
/* 353 */         if (entitylivingbase != null) {
/*     */           
/* 355 */           getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/* 356 */           getLookHelper().onUpdateLook();
/* 357 */           double d5 = func_175477_p(0.0F);
/* 358 */           double d0 = entitylivingbase.posX - this.posX;
/* 359 */           double d1 = entitylivingbase.posY + (entitylivingbase.height * 0.5F) - this.posY + getEyeHeight();
/* 360 */           double d2 = entitylivingbase.posZ - this.posZ;
/* 361 */           double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 362 */           d0 /= d3;
/* 363 */           d1 /= d3;
/* 364 */           d2 /= d3;
/* 365 */           double d4 = this.rand.nextDouble();
/*     */           
/* 367 */           while (d4 < d3) {
/*     */             
/* 369 */             d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
/* 370 */             this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4, this.posY + d1 * d4 + getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 376 */     if (this.inWater) {
/*     */       
/* 378 */       setAir(300);
/*     */     }
/* 380 */     else if (this.onGround) {
/*     */       
/* 382 */       this.motionY += 0.5D;
/* 383 */       this.motionX += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 384 */       this.motionZ += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 385 */       this.rotationYaw = this.rand.nextFloat() * 360.0F;
/* 386 */       this.onGround = false;
/* 387 */       this.isAirBorne = true;
/*     */     } 
/*     */     
/* 390 */     if (hasTargetedEntity())
/*     */     {
/* 392 */       this.rotationYaw = this.rotationYawHead;
/*     */     }
/*     */     
/* 395 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175471_a(float p_175471_1_) {
/* 400 */     return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * p_175471_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175469_o(float p_175469_1_) {
/* 405 */     return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * p_175469_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175477_p(float p_175477_1_) {
/* 410 */     return (this.field_175479_bo + p_175477_1_) / func_175464_ck();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 415 */     super.updateAITasks();
/*     */     
/* 417 */     if (isElder()) {
/*     */       
/* 419 */       int i = 1200;
/* 420 */       int j = 1200;
/* 421 */       int k = 6000;
/* 422 */       int l = 2;
/*     */       
/* 424 */       if ((this.ticksExisted + getEntityId()) % 1200 == 0) {
/*     */         
/* 426 */         Potion potion = Potion.digSlowdown;
/*     */         
/* 428 */         for (EntityPlayerMP entityplayermp : this.worldObj.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>()
/*     */             {
/*     */               public boolean apply(EntityPlayerMP p_apply_1_)
/*     */               {
/* 432 */                 return (EntityGuardian.this.getDistanceSqToEntity((Entity)p_apply_1_) < 2500.0D && p_apply_1_.theItemInWorldManager.survivalOrAdventure());
/*     */               }
/*     */             })) {
/*     */           
/* 436 */           if (!entityplayermp.isPotionActive(potion) || entityplayermp.getActivePotionEffect(potion).getAmplifier() < 2 || entityplayermp.getActivePotionEffect(potion).getDuration() < 1200) {
/*     */             
/* 438 */             entityplayermp.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(10, 0.0F));
/* 439 */             entityplayermp.addPotionEffect(new PotionEffect(potion.id, 6000, 2));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 444 */       if (!hasHome())
/*     */       {
/* 446 */         setHomePosAndDistance(new BlockPos((Entity)this), 16);
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
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 460 */     int i = this.rand.nextInt(3) + this.rand.nextInt(lootingModifier + 1);
/*     */     
/* 462 */     if (i > 0)
/*     */     {
/* 464 */       entityDropItem(new ItemStack(Items.prismarine_shard, i, 0), 1.0F);
/*     */     }
/*     */     
/* 467 */     if (this.rand.nextInt(3 + lootingModifier) > 1) {
/*     */       
/* 469 */       entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 1.0F);
/*     */     }
/* 471 */     else if (this.rand.nextInt(3 + lootingModifier) > 1) {
/*     */       
/* 473 */       entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
/*     */     } 
/*     */     
/* 476 */     if (wasRecentlyHit && isElder())
/*     */     {
/* 478 */       entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 487 */     ItemStack itemstack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j())).getItemStack(this.rand);
/* 488 */     entityDropItem(itemstack, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 496 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 504 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 512 */     return ((this.rand.nextInt(20) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos((Entity)this))) && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 520 */     if (!func_175472_n() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
/*     */       
/* 522 */       EntityLivingBase entitylivingbase = (EntityLivingBase)source.getSourceOfDamage();
/*     */       
/* 524 */       if (!source.isExplosion()) {
/*     */         
/* 526 */         entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage((Entity)this), 2.0F);
/* 527 */         entitylivingbase.playSound("damage.thorns", 0.5F, 1.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 531 */     this.wander.makeUpdate();
/* 532 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 541 */     return 180;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 549 */     if (isServerWorld()) {
/*     */       
/* 551 */       if (isInWater())
/*     */       {
/* 553 */         moveFlying(strafe, forward, 0.1F);
/* 554 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 555 */         this.motionX *= 0.8999999761581421D;
/* 556 */         this.motionY *= 0.8999999761581421D;
/* 557 */         this.motionZ *= 0.8999999761581421D;
/*     */         
/* 559 */         if (!func_175472_n() && getAttackTarget() == null)
/*     */         {
/* 561 */           this.motionY -= 0.005D;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 566 */         super.moveEntityWithHeading(strafe, forward);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 571 */       super.moveEntityWithHeading(strafe, forward);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AIGuardianAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGuardian theEntity;
/*     */     private int tickCounter;
/*     */     
/*     */     public AIGuardianAttack(EntityGuardian guardian) {
/* 582 */       this.theEntity = guardian;
/* 583 */       setMutexBits(3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 588 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 589 */       return (entitylivingbase != null && entitylivingbase.isEntityAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 594 */       return (super.continueExecuting() && (this.theEntity.isElder() || this.theEntity.getDistanceSqToEntity((Entity)this.theEntity.getAttackTarget()) > 9.0D));
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 599 */       this.tickCounter = -10;
/* 600 */       this.theEntity.getNavigator().clearPathEntity();
/* 601 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)this.theEntity.getAttackTarget(), 90.0F, 90.0F);
/* 602 */       this.theEntity.isAirBorne = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 607 */       this.theEntity.setTargetedEntity(0);
/* 608 */       this.theEntity.setAttackTarget((EntityLivingBase)null);
/* 609 */       this.theEntity.wander.makeUpdate();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 614 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 615 */       this.theEntity.getNavigator().clearPathEntity();
/* 616 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/*     */       
/* 618 */       if (!this.theEntity.canEntityBeSeen((Entity)entitylivingbase)) {
/*     */         
/* 620 */         this.theEntity.setAttackTarget((EntityLivingBase)null);
/*     */       }
/*     */       else {
/*     */         
/* 624 */         this.tickCounter++;
/*     */         
/* 626 */         if (this.tickCounter == 0) {
/*     */           
/* 628 */           this.theEntity.setTargetedEntity(this.theEntity.getAttackTarget().getEntityId());
/* 629 */           this.theEntity.worldObj.setEntityState((Entity)this.theEntity, (byte)21);
/*     */         }
/* 631 */         else if (this.tickCounter >= this.theEntity.func_175464_ck()) {
/*     */           
/* 633 */           float f = 1.0F;
/*     */           
/* 635 */           if (this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */           {
/* 637 */             f += 2.0F;
/*     */           }
/*     */           
/* 640 */           if (this.theEntity.isElder())
/*     */           {
/* 642 */             f += 2.0F;
/*     */           }
/*     */           
/* 645 */           entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage((Entity)this.theEntity, (Entity)this.theEntity), f);
/* 646 */           entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.theEntity), (float)this.theEntity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
/* 647 */           this.theEntity.setAttackTarget((EntityLivingBase)null);
/*     */         }
/* 649 */         else if (this.tickCounter < 60 || this.tickCounter % 20 == 0) {
/*     */         
/*     */         } 
/*     */ 
/*     */         
/* 654 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private final EntityGuardian entityGuardian;
/*     */     
/*     */     public GuardianMoveHelper(EntityGuardian guardian) {
/* 665 */       super((EntityLiving)guardian);
/* 666 */       this.entityGuardian = guardian;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 671 */       if (this.update && !this.entityGuardian.getNavigator().noPath()) {
/*     */         
/* 673 */         double d0 = this.posX - this.entityGuardian.posX;
/* 674 */         double d1 = this.posY - this.entityGuardian.posY;
/* 675 */         double d2 = this.posZ - this.entityGuardian.posZ;
/* 676 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 677 */         d3 = MathHelper.sqrt_double(d3);
/* 678 */         d1 /= d3;
/* 679 */         float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
/* 680 */         this.entityGuardian.rotationYaw = limitAngle(this.entityGuardian.rotationYaw, f, 30.0F);
/* 681 */         this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
/* 682 */         float f1 = (float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 683 */         this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + (f1 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
/* 684 */         double d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5D) * 0.05D;
/* 685 */         double d5 = Math.cos((this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
/* 686 */         double d6 = Math.sin((this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
/* 687 */         this.entityGuardian.motionX += d4 * d5;
/* 688 */         this.entityGuardian.motionZ += d4 * d6;
/* 689 */         d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75D) * 0.05D;
/* 690 */         this.entityGuardian.motionY += d4 * (d6 + d5) * 0.25D;
/* 691 */         this.entityGuardian.motionY += this.entityGuardian.getAIMoveSpeed() * d1 * 0.1D;
/* 692 */         EntityLookHelper entitylookhelper = this.entityGuardian.getLookHelper();
/* 693 */         double d7 = this.entityGuardian.posX + d0 / d3 * 2.0D;
/* 694 */         double d8 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + d1 / d3;
/* 695 */         double d9 = this.entityGuardian.posZ + d2 / d3 * 2.0D;
/* 696 */         double d10 = entitylookhelper.getLookPosX();
/* 697 */         double d11 = entitylookhelper.getLookPosY();
/* 698 */         double d12 = entitylookhelper.getLookPosZ();
/*     */         
/* 700 */         if (!entitylookhelper.getIsLooking()) {
/*     */           
/* 702 */           d10 = d7;
/* 703 */           d11 = d8;
/* 704 */           d12 = d9;
/*     */         } 
/*     */         
/* 707 */         this.entityGuardian.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
/* 708 */         this.entityGuardian.func_175476_l(true);
/*     */       }
/*     */       else {
/*     */         
/* 712 */         this.entityGuardian.setAIMoveSpeed(0.0F);
/* 713 */         this.entityGuardian.func_175476_l(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianTargetSelector
/*     */     implements Predicate<EntityLivingBase>
/*     */   {
/*     */     private final EntityGuardian parentEntity;
/*     */     
/*     */     public GuardianTargetSelector(EntityGuardian guardian) {
/* 724 */       this.parentEntity = guardian;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(EntityLivingBase p_apply_1_) {
/* 729 */       return ((p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof net.minecraft.entity.passive.EntitySquid) && p_apply_1_.getDistanceSqToEntity((Entity)this.parentEntity) > 9.0D);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */