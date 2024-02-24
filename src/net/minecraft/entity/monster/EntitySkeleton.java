/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Calendar;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySkeleton extends EntityMob implements IRangedAttackMob {
/*  29 */   private final EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
/*  30 */   private final EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
/*     */ 
/*     */   
/*     */   public EntitySkeleton(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  36 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIRestrictSun(this));
/*  37 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIFleeSun(this, 1.0D));
/*  38 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
/*  39 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  40 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  41 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  42 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  43 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  44 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
/*     */     
/*  46 */     if (worldIn != null && !worldIn.isRemote)
/*     */     {
/*  48 */       setCombatTask();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  54 */     super.applyEntityAttributes();
/*  55 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  60 */     super.entityInit();
/*  61 */     this.dataWatcher.addObject(13, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  69 */     return "mob.skeleton.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  77 */     return "mob.skeleton.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  85 */     return "mob.skeleton.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  90 */     playSound("mob.skeleton.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/*  95 */     if (super.attackEntityAsMob(entityIn)) {
/*     */       
/*  97 */       if (getSkeletonType() == 1 && entityIn instanceof EntityLivingBase)
/*     */       {
/*  99 */         ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
/*     */       }
/*     */       
/* 102 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 115 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 124 */     if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
/*     */       
/* 126 */       float f = getBrightness(1.0F);
/* 127 */       BlockPos blockpos = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
/*     */       
/* 129 */       if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canSeeSky(blockpos)) {
/*     */         
/* 131 */         boolean flag = true;
/* 132 */         ItemStack itemstack = getEquipmentInSlot(4);
/*     */         
/* 134 */         if (itemstack != null) {
/*     */           
/* 136 */           if (itemstack.isItemStackDamageable()) {
/*     */             
/* 138 */             itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
/*     */             
/* 140 */             if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
/*     */               
/* 142 */               renderBrokenItemStack(itemstack);
/* 143 */               setCurrentItemOrArmor(4, (ItemStack)null);
/*     */             } 
/*     */           } 
/*     */           
/* 147 */           flag = false;
/*     */         } 
/*     */         
/* 150 */         if (flag)
/*     */         {
/* 152 */           setFire(8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     if (this.worldObj.isRemote && getSkeletonType() == 1)
/*     */     {
/* 159 */       setSize(0.72F, 2.535F);
/*     */     }
/*     */     
/* 162 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRidden() {
/* 170 */     super.updateRidden();
/*     */     
/* 172 */     if (this.ridingEntity instanceof EntityCreature) {
/*     */       
/* 174 */       EntityCreature entitycreature = (EntityCreature)this.ridingEntity;
/* 175 */       this.renderYawOffset = entitycreature.renderYawOffset;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 184 */     super.onDeath(cause);
/*     */     
/* 186 */     if (cause.getSourceOfDamage() instanceof EntityArrow && cause.getEntity() instanceof EntityPlayer) {
/*     */       
/* 188 */       EntityPlayer entityplayer = (EntityPlayer)cause.getEntity();
/* 189 */       double d0 = entityplayer.posX - this.posX;
/* 190 */       double d1 = entityplayer.posZ - this.posZ;
/*     */       
/* 192 */       if (d0 * d0 + d1 * d1 >= 2500.0D)
/*     */       {
/* 194 */         entityplayer.triggerAchievement((StatBase)AchievementList.snipeSkeleton);
/*     */       }
/*     */     }
/* 197 */     else if (cause.getEntity() instanceof EntityCreeper && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
/*     */       
/* 199 */       ((EntityCreeper)cause.getEntity()).func_175493_co();
/* 200 */       entityDropItem(new ItemStack(Items.skull, 1, (getSkeletonType() == 1) ? 1 : 0), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 206 */     return Items.arrow;
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
/* 218 */     if (getSkeletonType() == 1) {
/*     */       
/* 220 */       int i = this.rand.nextInt(3 + lootingModifier) - 1;
/*     */       
/* 222 */       for (int j = 0; j < i; j++)
/*     */       {
/* 224 */         dropItem(Items.coal, 1);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 229 */       int k = this.rand.nextInt(3 + lootingModifier);
/*     */       
/* 231 */       for (int i1 = 0; i1 < k; i1++)
/*     */       {
/* 233 */         dropItem(Items.arrow, 1);
/*     */       }
/*     */     } 
/*     */     
/* 237 */     int l = this.rand.nextInt(3 + lootingModifier);
/*     */     
/* 239 */     for (int j1 = 0; j1 < l; j1++)
/*     */     {
/* 241 */       dropItem(Items.bone, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 250 */     if (getSkeletonType() == 1)
/*     */     {
/* 252 */       entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 261 */     super.setEquipmentBasedOnDifficulty(difficulty);
/* 262 */     setCurrentItemOrArmor(0, new ItemStack((Item)Items.bow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 271 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 273 */     if (this.worldObj.provider instanceof net.minecraft.world.WorldProviderHell && getRNG().nextInt(5) > 0) {
/*     */       
/* 275 */       this.tasks.addTask(4, (EntityAIBase)this.aiAttackOnCollide);
/* 276 */       setSkeletonType(1);
/* 277 */       setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
/* 278 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*     */     }
/*     */     else {
/*     */       
/* 282 */       this.tasks.addTask(4, (EntityAIBase)this.aiArrowAttack);
/* 283 */       setEquipmentBasedOnDifficulty(difficulty);
/* 284 */       setEnchantmentBasedOnDifficulty(difficulty);
/*     */     } 
/*     */     
/* 287 */     setCanPickUpLoot((this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty()));
/*     */     
/* 289 */     if (getEquipmentInSlot(4) == null) {
/*     */       
/* 291 */       Calendar calendar = this.worldObj.getCurrentDate();
/*     */       
/* 293 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
/*     */         
/* 295 */         setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.lit_pumpkin : Blocks.pumpkin));
/* 296 */         this.equipmentDropChances[4] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCombatTask() {
/* 308 */     this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
/* 309 */     this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
/* 310 */     ItemStack itemstack = getHeldItem();
/*     */     
/* 312 */     if (itemstack != null && itemstack.getItem() == Items.bow) {
/*     */       
/* 314 */       this.tasks.addTask(4, (EntityAIBase)this.aiArrowAttack);
/*     */     }
/*     */     else {
/*     */       
/* 318 */       this.tasks.addTask(4, (EntityAIBase)this.aiAttackOnCollide);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
/* 327 */     EntityArrow entityarrow = new EntityArrow(this.worldObj, (EntityLivingBase)this, target, 1.6F, (14 - (this.worldObj.getDifficulty().getDifficultyId() << 2)));
/* 328 */     int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
/* 329 */     int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
/* 330 */     entityarrow.setDamage((p_82196_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (this.worldObj.getDifficulty().getDifficultyId() * 0.11F));
/*     */     
/* 332 */     if (i > 0)
/*     */     {
/* 334 */       entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D);
/*     */     }
/*     */     
/* 337 */     if (j > 0)
/*     */     {
/* 339 */       entityarrow.setKnockbackStrength(j);
/*     */     }
/*     */     
/* 342 */     if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0 || getSkeletonType() == 1)
/*     */     {
/* 344 */       entityarrow.setFire(100);
/*     */     }
/*     */     
/* 347 */     playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 348 */     this.worldObj.spawnEntityInWorld((Entity)entityarrow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkeletonType() {
/* 356 */     return this.dataWatcher.getWatchableObjectByte(13);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkeletonType(int p_82201_1_) {
/* 364 */     this.dataWatcher.updateObject(13, Byte.valueOf((byte)p_82201_1_));
/* 365 */     this.isImmuneToFire = (p_82201_1_ == 1);
/*     */     
/* 367 */     if (p_82201_1_ == 1) {
/*     */       
/* 369 */       setSize(0.72F, 2.535F);
/*     */     }
/*     */     else {
/*     */       
/* 373 */       setSize(0.6F, 1.95F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 382 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 384 */     if (tagCompund.hasKey("SkeletonType", 99)) {
/*     */       
/* 386 */       int i = tagCompund.getByte("SkeletonType");
/* 387 */       setSkeletonType(i);
/*     */     } 
/*     */     
/* 390 */     setCombatTask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 398 */     super.writeEntityToNBT(tagCompound);
/* 399 */     tagCompound.setByte("SkeletonType", (byte)getSkeletonType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 407 */     super.setCurrentItemOrArmor(slotIn, stack);
/*     */     
/* 409 */     if (!this.worldObj.isRemote && slotIn == 0)
/*     */     {
/* 411 */       setCombatTask();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 417 */     return (getSkeletonType() == 1) ? super.getEyeHeight() : 1.74F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 425 */     return isChild() ? 0.0D : -0.35D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntitySkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */