/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIBeg;
/*     */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITargetNonTamed;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWolf
/*     */   extends EntityTameable {
/*     */   private float headRotationCourse;
/*     */   private float headRotationCourseOld;
/*     */   private boolean isWet;
/*     */   
/*     */   public EntityWolf(World worldIn) {
/*  48 */     super(worldIn);
/*  49 */     setSize(0.6F, 0.8F);
/*  50 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  51 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  52 */     this.tasks.addTask(2, (EntityAIBase)this.aiSit);
/*  53 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4F));
/*  54 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, 1.0D, true));
/*  55 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
/*  56 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  57 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  58 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIBeg(this, 8.0F));
/*  59 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  60 */     this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  61 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIOwnerHurtByTarget(this));
/*  62 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIOwnerHurtTarget(this));
/*  63 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
/*  64 */     this.targetTasks.addTask(4, (EntityAIBase)new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/*  68 */               return (p_apply_1_ instanceof EntitySheep || p_apply_1_ instanceof EntityRabbit);
/*     */             }
/*     */           }));
/*  71 */     this.targetTasks.addTask(5, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntitySkeleton.class, false));
/*  72 */     setTamed(false);
/*     */   }
/*     */   private boolean isShaking; private float timeWolfIsShaking; private float prevTimeWolfIsShaking;
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  77 */     super.applyEntityAttributes();
/*  78 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */     
/*  80 */     if (isTamed()) {
/*     */       
/*  82 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*     */     }
/*     */     else {
/*     */       
/*  86 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*     */     } 
/*     */     
/*  89 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
/*  90 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
/*  98 */     super.setAttackTarget(entitylivingbaseIn);
/*     */     
/* 100 */     if (entitylivingbaseIn == null) {
/*     */       
/* 102 */       setAngry(false);
/*     */     }
/* 104 */     else if (!isTamed()) {
/*     */       
/* 106 */       setAngry(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 112 */     this.dataWatcher.updateObject(18, Float.valueOf(getHealth()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 117 */     super.entityInit();
/* 118 */     this.dataWatcher.addObject(18, new Float(getHealth()));
/* 119 */     this.dataWatcher.addObject(19, new Byte((byte)0));
/* 120 */     this.dataWatcher.addObject(20, new Byte((byte)EnumDyeColor.RED.getMetadata()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 125 */     playSound("mob.wolf.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 133 */     super.writeEntityToNBT(tagCompound);
/* 134 */     tagCompound.setBoolean("Angry", isAngry());
/* 135 */     tagCompound.setByte("CollarColor", (byte)getCollarColor().getDyeDamage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 143 */     super.readEntityFromNBT(tagCompund);
/* 144 */     setAngry(tagCompund.getBoolean("Angry"));
/*     */     
/* 146 */     if (tagCompund.hasKey("CollarColor", 99))
/*     */     {
/* 148 */       setCollarColor(EnumDyeColor.byDyeDamage(tagCompund.getByte("CollarColor")));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 157 */     return isAngry() ? "mob.wolf.growl" : ((this.rand.nextInt(3) == 0) ? ((isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 165 */     return "mob.wolf.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 173 */     return "mob.wolf.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 181 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 186 */     return Item.getItemById(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 195 */     super.onLivingUpdate();
/*     */     
/* 197 */     if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !hasPath() && this.onGround) {
/*     */       
/* 199 */       this.isShaking = true;
/* 200 */       this.timeWolfIsShaking = 0.0F;
/* 201 */       this.prevTimeWolfIsShaking = 0.0F;
/* 202 */       this.worldObj.setEntityState((Entity)this, (byte)8);
/*     */     } 
/*     */     
/* 205 */     if (!this.worldObj.isRemote && getAttackTarget() == null && isAngry())
/*     */     {
/* 207 */       setAngry(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 216 */     super.onUpdate();
/* 217 */     this.headRotationCourseOld = this.headRotationCourse;
/*     */     
/* 219 */     if (isBegging()) {
/*     */       
/* 221 */       this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
/*     */     }
/*     */     else {
/*     */       
/* 225 */       this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
/*     */     } 
/*     */     
/* 228 */     if (isWet()) {
/*     */       
/* 230 */       this.isWet = true;
/* 231 */       this.isShaking = false;
/* 232 */       this.timeWolfIsShaking = 0.0F;
/* 233 */       this.prevTimeWolfIsShaking = 0.0F;
/*     */     }
/* 235 */     else if ((this.isWet || this.isShaking) && this.isShaking) {
/*     */       
/* 237 */       if (this.timeWolfIsShaking == 0.0F)
/*     */       {
/* 239 */         playSound("mob.wolf.shake", getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */       
/* 242 */       this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
/* 243 */       this.timeWolfIsShaking += 0.05F;
/*     */       
/* 245 */       if (this.prevTimeWolfIsShaking >= 2.0F) {
/*     */         
/* 247 */         this.isWet = false;
/* 248 */         this.isShaking = false;
/* 249 */         this.prevTimeWolfIsShaking = 0.0F;
/* 250 */         this.timeWolfIsShaking = 0.0F;
/*     */       } 
/*     */       
/* 253 */       if (this.timeWolfIsShaking > 0.4F) {
/*     */         
/* 255 */         float f = (float)(getEntityBoundingBox()).minY;
/* 256 */         int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);
/*     */         
/* 258 */         for (int j = 0; j < i; j++) {
/*     */           
/* 260 */           float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 261 */           float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 262 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f1, (f + 0.8F), this.posZ + f2, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWolfWet() {
/* 273 */     return this.isWet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getShadingWhileWet(float p_70915_1_) {
/* 281 */     return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
/* 286 */     float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;
/*     */     
/* 288 */     if (f < 0.0F) {
/*     */       
/* 290 */       f = 0.0F;
/*     */     }
/* 292 */     else if (f > 1.0F) {
/*     */       
/* 294 */       f = 1.0F;
/*     */     } 
/*     */     
/* 297 */     return MathHelper.sin(f * 3.1415927F) * MathHelper.sin(f * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getInterestedAngle(float p_70917_1_) {
/* 302 */     return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * 3.1415927F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 307 */     return this.height * 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 316 */     return isSitting() ? 20 : super.getVerticalFaceSpeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 324 */     if (isEntityInvulnerable(source))
/*     */     {
/* 326 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 330 */     Entity entity = source.getEntity();
/* 331 */     this.aiSit.setSitting(false);
/*     */     
/* 333 */     if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof net.minecraft.entity.projectile.EntityArrow))
/*     */     {
/* 335 */       amount = (amount + 1.0F) / 2.0F;
/*     */     }
/*     */     
/* 338 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 344 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (int)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
/*     */     
/* 346 */     if (flag)
/*     */     {
/* 348 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     }
/*     */     
/* 351 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 356 */     super.setTamed(tamed);
/*     */     
/* 358 */     if (tamed) {
/*     */       
/* 360 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*     */     }
/*     */     else {
/*     */       
/* 364 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*     */     } 
/*     */     
/* 367 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 375 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 377 */     if (isTamed()) {
/*     */       
/* 379 */       if (itemstack != null)
/*     */       {
/* 381 */         if (itemstack.getItem() instanceof ItemFood) {
/*     */           
/* 383 */           ItemFood itemfood = (ItemFood)itemstack.getItem();
/*     */           
/* 385 */           if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F)
/*     */           {
/* 387 */             if (!player.capabilities.isCreativeMode)
/*     */             {
/* 389 */               itemstack.stackSize--;
/*     */             }
/*     */             
/* 392 */             heal(itemfood.getHealAmount(itemstack));
/*     */             
/* 394 */             if (itemstack.stackSize <= 0)
/*     */             {
/* 396 */               player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
/*     */             }
/*     */             
/* 399 */             return true;
/*     */           }
/*     */         
/* 402 */         } else if (itemstack.getItem() == Items.dye) {
/*     */           
/* 404 */           EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());
/*     */           
/* 406 */           if (enumdyecolor != getCollarColor()) {
/*     */             
/* 408 */             setCollarColor(enumdyecolor);
/*     */             
/* 410 */             if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
/*     */             {
/* 412 */               player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
/*     */             }
/*     */             
/* 415 */             return true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 420 */       if (isOwner((EntityLivingBase)player) && !this.worldObj.isRemote && !isBreedingItem(itemstack))
/*     */       {
/* 422 */         this.aiSit.setSitting(!isSitting());
/* 423 */         this.isJumping = false;
/* 424 */         this.navigator.clearPathEntity();
/* 425 */         setAttackTarget((EntityLivingBase)null);
/*     */       }
/*     */     
/* 428 */     } else if (itemstack != null && itemstack.getItem() == Items.bone && !isAngry()) {
/*     */       
/* 430 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 432 */         itemstack.stackSize--;
/*     */       }
/*     */       
/* 435 */       if (itemstack.stackSize <= 0)
/*     */       {
/* 437 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
/*     */       }
/*     */       
/* 440 */       if (!this.worldObj.isRemote)
/*     */       {
/* 442 */         if (this.rand.nextInt(3) == 0) {
/*     */           
/* 444 */           setTamed(true);
/* 445 */           this.navigator.clearPathEntity();
/* 446 */           setAttackTarget((EntityLivingBase)null);
/* 447 */           this.aiSit.setSitting(true);
/* 448 */           setHealth(20.0F);
/* 449 */           setOwnerId(player.getUniqueID().toString());
/* 450 */           playTameEffect(true);
/* 451 */           this.worldObj.setEntityState((Entity)this, (byte)7);
/*     */         }
/*     */         else {
/*     */           
/* 455 */           playTameEffect(false);
/* 456 */           this.worldObj.setEntityState((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 460 */       return true;
/*     */     } 
/*     */     
/* 463 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 468 */     if (id == 8) {
/*     */       
/* 470 */       this.isShaking = true;
/* 471 */       this.timeWolfIsShaking = 0.0F;
/* 472 */       this.prevTimeWolfIsShaking = 0.0F;
/*     */     }
/*     */     else {
/*     */       
/* 476 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTailRotation() {
/* 482 */     return isAngry() ? 1.5393804F : (isTamed() ? ((0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * 3.1415927F) : 0.62831855F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 491 */     return (stack == null) ? false : (!(stack.getItem() instanceof ItemFood) ? false : ((ItemFood)stack.getItem()).isWolfsFavoriteMeat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSpawnedInChunk() {
/* 499 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAngry() {
/* 507 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAngry(boolean angry) {
/* 515 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 517 */     if (angry) {
/*     */       
/* 519 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x2)));
/*     */     }
/*     */     else {
/*     */       
/* 523 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumDyeColor getCollarColor() {
/* 529 */     return EnumDyeColor.byDyeDamage(this.dataWatcher.getWatchableObjectByte(20) & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCollarColor(EnumDyeColor collarcolor) {
/* 534 */     this.dataWatcher.updateObject(20, Byte.valueOf((byte)(collarcolor.getDyeDamage() & 0xF)));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityWolf createChild(EntityAgeable ageable) {
/* 539 */     EntityWolf entitywolf = new EntityWolf(this.worldObj);
/* 540 */     String s = getOwnerId();
/*     */     
/* 542 */     if (s != null && !s.trim().isEmpty()) {
/*     */       
/* 544 */       entitywolf.setOwnerId(s);
/* 545 */       entitywolf.setTamed(true);
/*     */     } 
/*     */     
/* 548 */     return entitywolf;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBegging(boolean beg) {
/* 553 */     if (beg) {
/*     */       
/* 555 */       this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
/*     */     }
/*     */     else {
/*     */       
/* 559 */       this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 568 */     if (otherAnimal == this)
/*     */     {
/* 570 */       return false;
/*     */     }
/* 572 */     if (!isTamed())
/*     */     {
/* 574 */       return false;
/*     */     }
/* 576 */     if (!(otherAnimal instanceof EntityWolf))
/*     */     {
/* 578 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 582 */     EntityWolf entitywolf = (EntityWolf)otherAnimal;
/* 583 */     return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : ((isInLove() && entitywolf.isInLove())));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBegging() {
/* 589 */     return (this.dataWatcher.getWatchableObjectByte(19) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 597 */     return (!isTamed() && this.ticksExisted > 2400);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
/* 602 */     if (!(p_142018_1_ instanceof net.minecraft.entity.monster.EntityCreeper) && !(p_142018_1_ instanceof net.minecraft.entity.monster.EntityGhast)) {
/*     */       
/* 604 */       if (p_142018_1_ instanceof EntityWolf) {
/*     */         
/* 606 */         EntityWolf entitywolf = (EntityWolf)p_142018_1_;
/*     */         
/* 608 */         if (entitywolf.isTamed() && entitywolf.getOwner() == p_142018_2_)
/*     */         {
/* 610 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 614 */       return (p_142018_1_ instanceof EntityPlayer && p_142018_2_ instanceof EntityPlayer && !((EntityPlayer)p_142018_2_).canAttackPlayer((EntityPlayer)p_142018_1_)) ? false : ((!(p_142018_1_ instanceof EntityHorse) || !((EntityHorse)p_142018_1_).isTame()));
/*     */     } 
/*     */ 
/*     */     
/* 618 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean allowLeashing() {
/* 624 */     return (!isAngry() && super.allowLeashing());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */