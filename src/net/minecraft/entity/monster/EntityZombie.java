/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIBreakDoor;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityZombie extends EntityMob {
/*  38 */   protected static final IAttribute reinforcementChance = (IAttribute)(new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
/*  39 */   private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
/*  40 */   private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
/*  41 */   private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor((EntityLiving)this);
/*     */ 
/*     */   
/*     */   private int conversionTime;
/*     */ 
/*     */   
/*     */   private boolean isBreakDoorsTaskSet = false;
/*     */ 
/*     */   
/*  50 */   private float zombieWidth = -1.0F;
/*     */ 
/*     */   
/*     */   private float zombieHeight;
/*     */ 
/*     */   
/*     */   public EntityZombie(World worldIn) {
/*  57 */     super(worldIn);
/*  58 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*  59 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  60 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  61 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  62 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  63 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  64 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  65 */     applyEntityAI();
/*  66 */     setSize(0.6F, 1.95F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAI() {
/*  71 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
/*  72 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0D, true));
/*  73 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIMoveThroughVillage(this, 1.0D, false));
/*  74 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
/*  75 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  76 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
/*  77 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  82 */     super.applyEntityAttributes();
/*  83 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
/*  84 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*  85 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
/*  86 */     getAttributeMap().registerAttribute(reinforcementChance).setBaseValue(this.rand.nextDouble() * 0.10000000149011612D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  91 */     super.entityInit();
/*  92 */     getDataWatcher().addObject(12, Byte.valueOf((byte)0));
/*  93 */     getDataWatcher().addObject(13, Byte.valueOf((byte)0));
/*  94 */     getDataWatcher().addObject(14, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 102 */     int i = super.getTotalArmorValue() + 2;
/*     */     
/* 104 */     if (i > 20)
/*     */     {
/* 106 */       i = 20;
/*     */     }
/*     */     
/* 109 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakDoorsTaskSet() {
/* 114 */     return this.isBreakDoorsTaskSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBreakDoorsAItask(boolean par1) {
/* 122 */     if (this.isBreakDoorsTaskSet != par1) {
/*     */       
/* 124 */       this.isBreakDoorsTaskSet = par1;
/*     */       
/* 126 */       if (par1) {
/*     */         
/* 128 */         this.tasks.addTask(1, (EntityAIBase)this.breakDoor);
/*     */       }
/*     */       else {
/*     */         
/* 132 */         this.tasks.removeTask((EntityAIBase)this.breakDoor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 142 */     return (getDataWatcher().getWatchableObjectByte(12) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 150 */     if (isChild())
/*     */     {
/* 152 */       this.experienceValue = (int)(this.experienceValue * 2.5F);
/*     */     }
/*     */     
/* 155 */     return super.getExperiencePoints(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChild(boolean childZombie) {
/* 163 */     getDataWatcher().updateObject(12, Byte.valueOf((byte)(childZombie ? 1 : 0)));
/*     */     
/* 165 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/*     */       
/* 167 */       IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 168 */       iattributeinstance.removeModifier(babySpeedBoostModifier);
/*     */       
/* 170 */       if (childZombie)
/*     */       {
/* 172 */         iattributeinstance.applyModifier(babySpeedBoostModifier);
/*     */       }
/*     */     } 
/*     */     
/* 176 */     setChildSize(childZombie);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVillager() {
/* 184 */     return (getDataWatcher().getWatchableObjectByte(13) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVillager(boolean villager) {
/* 192 */     getDataWatcher().updateObject(13, Byte.valueOf((byte)(villager ? 1 : 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 201 */     if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !isChild()) {
/*     */       
/* 203 */       float f = getBrightness(1.0F);
/* 204 */       BlockPos blockpos = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
/*     */       
/* 206 */       if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canSeeSky(blockpos)) {
/*     */         
/* 208 */         boolean flag = true;
/* 209 */         ItemStack itemstack = getEquipmentInSlot(4);
/*     */         
/* 211 */         if (itemstack != null) {
/*     */           
/* 213 */           if (itemstack.isItemStackDamageable()) {
/*     */             
/* 215 */             itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
/*     */             
/* 217 */             if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
/*     */               
/* 219 */               renderBrokenItemStack(itemstack);
/* 220 */               setCurrentItemOrArmor(4, (ItemStack)null);
/*     */             } 
/*     */           } 
/*     */           
/* 224 */           flag = false;
/*     */         } 
/*     */         
/* 227 */         if (flag)
/*     */         {
/* 229 */           setFire(8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     if (isRiding() && getAttackTarget() != null && this.ridingEntity instanceof EntityChicken)
/*     */     {
/* 236 */       ((EntityLiving)this.ridingEntity).getNavigator().setPath(getNavigator().getPath(), 1.5D);
/*     */     }
/*     */     
/* 239 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 247 */     if (super.attackEntityFrom(source, amount)) {
/*     */       
/* 249 */       EntityLivingBase entitylivingbase = getAttackTarget();
/*     */       
/* 251 */       if (entitylivingbase == null && source.getEntity() instanceof EntityLivingBase)
/*     */       {
/* 253 */         entitylivingbase = (EntityLivingBase)source.getEntity();
/*     */       }
/*     */       
/* 256 */       if (entitylivingbase != null && this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.rand.nextFloat() < getEntityAttribute(reinforcementChance).getAttributeValue()) {
/*     */         
/* 258 */         int i = MathHelper.floor_double(this.posX);
/* 259 */         int j = MathHelper.floor_double(this.posY);
/* 260 */         int k = MathHelper.floor_double(this.posZ);
/* 261 */         EntityZombie entityzombie = new EntityZombie(this.worldObj);
/*     */         
/* 263 */         for (int l = 0; l < 50; l++) {
/*     */           
/* 265 */           int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/* 266 */           int j1 = j + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/* 267 */           int k1 = k + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/*     */           
/* 269 */           if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(i1, j1 - 1, k1)) && this.worldObj.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10) {
/*     */             
/* 271 */             entityzombie.setPosition(i1, j1, k1);
/*     */             
/* 273 */             if (!this.worldObj.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) && this.worldObj.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), (Entity)entityzombie) && this.worldObj.getCollidingBoundingBoxes((Entity)entityzombie, entityzombie.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(entityzombie.getEntityBoundingBox())) {
/*     */               
/* 275 */               this.worldObj.spawnEntityInWorld((Entity)entityzombie);
/* 276 */               entityzombie.setAttackTarget(entitylivingbase);
/* 277 */               entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
/* 278 */               getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
/* 279 */               entityzombie.getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 286 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 299 */     if (!this.worldObj.isRemote && isConverting()) {
/*     */       
/* 301 */       int i = getConversionTimeBoost();
/* 302 */       this.conversionTime -= i;
/*     */       
/* 304 */       if (this.conversionTime <= 0)
/*     */       {
/* 306 */         convertToVillager();
/*     */       }
/*     */     } 
/*     */     
/* 310 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 315 */     boolean flag = super.attackEntityAsMob(entityIn);
/*     */     
/* 317 */     if (flag) {
/*     */       
/* 319 */       int i = this.worldObj.getDifficulty().getDifficultyId();
/*     */       
/* 321 */       if (getHeldItem() == null && isBurning() && this.rand.nextFloat() < i * 0.3F)
/*     */       {
/* 323 */         entityIn.setFire(2 * i);
/*     */       }
/*     */     } 
/*     */     
/* 327 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 335 */     return "mob.zombie.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 343 */     return "mob.zombie.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 351 */     return "mob.zombie.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 356 */     playSound("mob.zombie.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 361 */     return Items.rotten_flesh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 369 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 377 */     switch (this.rand.nextInt(3)) {
/*     */       
/*     */       case 0:
/* 380 */         dropItem(Items.iron_ingot, 1);
/*     */         break;
/*     */       
/*     */       case 1:
/* 384 */         dropItem(Items.carrot, 1);
/*     */         break;
/*     */       
/*     */       case 2:
/* 388 */         dropItem(Items.potato, 1);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 397 */     super.setEquipmentBasedOnDifficulty(difficulty);
/*     */     
/* 399 */     if (this.rand.nextFloat() < ((this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.05F : 0.01F)) {
/*     */       
/* 401 */       int i = this.rand.nextInt(3);
/*     */       
/* 403 */       if (i == 0) {
/*     */         
/* 405 */         setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
/*     */       }
/*     */       else {
/*     */         
/* 409 */         setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 419 */     super.writeEntityToNBT(tagCompound);
/*     */     
/* 421 */     if (isChild())
/*     */     {
/* 423 */       tagCompound.setBoolean("IsBaby", true);
/*     */     }
/*     */     
/* 426 */     if (isVillager())
/*     */     {
/* 428 */       tagCompound.setBoolean("IsVillager", true);
/*     */     }
/*     */     
/* 431 */     tagCompound.setInteger("ConversionTime", isConverting() ? this.conversionTime : -1);
/* 432 */     tagCompound.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 440 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 442 */     if (tagCompund.getBoolean("IsBaby"))
/*     */     {
/* 444 */       setChild(true);
/*     */     }
/*     */     
/* 447 */     if (tagCompund.getBoolean("IsVillager"))
/*     */     {
/* 449 */       setVillager(true);
/*     */     }
/*     */     
/* 452 */     if (tagCompund.hasKey("ConversionTime", 99) && tagCompund.getInteger("ConversionTime") > -1)
/*     */     {
/* 454 */       startConversion(tagCompund.getInteger("ConversionTime"));
/*     */     }
/*     */     
/* 457 */     setBreakDoorsAItask(tagCompund.getBoolean("CanBreakDoors"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillEntity(EntityLivingBase entityLivingIn) {
/* 465 */     super.onKillEntity(entityLivingIn);
/*     */     
/* 467 */     if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager) {
/*     */       
/* 469 */       if (this.worldObj.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 474 */       EntityLiving entityliving = (EntityLiving)entityLivingIn;
/* 475 */       EntityZombie entityzombie = new EntityZombie(this.worldObj);
/* 476 */       entityzombie.copyLocationAndAnglesFrom((Entity)entityLivingIn);
/* 477 */       this.worldObj.removeEntity((Entity)entityLivingIn);
/* 478 */       entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
/* 479 */       entityzombie.setVillager(true);
/*     */       
/* 481 */       if (entityLivingIn.isChild())
/*     */       {
/* 483 */         entityzombie.setChild(true);
/*     */       }
/*     */       
/* 486 */       entityzombie.setNoAI(entityliving.isAIDisabled());
/*     */       
/* 488 */       if (entityliving.hasCustomName()) {
/*     */         
/* 490 */         entityzombie.setCustomNameTag(entityliving.getCustomNameTag());
/* 491 */         entityzombie.setAlwaysRenderNameTag(entityliving.getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 494 */       this.worldObj.spawnEntityInWorld((Entity)entityzombie);
/* 495 */       this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 501 */     float f = 1.74F;
/*     */     
/* 503 */     if (isChild())
/*     */     {
/* 505 */       f = (float)(f - 0.81D);
/*     */     }
/*     */     
/* 508 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_175448_a(ItemStack stack) {
/* 513 */     return (stack.getItem() == Items.egg && isChild() && isRiding()) ? false : super.func_175448_a(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 522 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 523 */     float f = difficulty.getClampedAdditionalDifficulty();
/* 524 */     setCanPickUpLoot((this.rand.nextFloat() < 0.55F * f));
/*     */     
/* 526 */     if (livingdata == null)
/*     */     {
/* 528 */       livingdata = new GroupData((this.worldObj.rand.nextFloat() < 0.05F), (this.worldObj.rand.nextFloat() < 0.05F));
/*     */     }
/*     */     
/* 531 */     if (livingdata instanceof GroupData) {
/*     */       
/* 533 */       GroupData entityzombie$groupdata = (GroupData)livingdata;
/*     */       
/* 535 */       if (entityzombie$groupdata.isVillager)
/*     */       {
/* 537 */         setVillager(true);
/*     */       }
/*     */       
/* 540 */       if (entityzombie$groupdata.isChild) {
/*     */         
/* 542 */         setChild(true);
/*     */         
/* 544 */         if (this.worldObj.rand.nextFloat() < 0.05D) {
/*     */           
/* 546 */           List<EntityChicken> list = this.worldObj.getEntitiesWithinAABB(EntityChicken.class, getEntityBoundingBox().expand(5.0D, 3.0D, 5.0D), EntitySelectors.IS_STANDALONE);
/*     */           
/* 548 */           if (!list.isEmpty())
/*     */           {
/* 550 */             EntityChicken entitychicken = list.get(0);
/* 551 */             entitychicken.setChickenJockey(true);
/* 552 */             mountEntity((Entity)entitychicken);
/*     */           }
/*     */         
/* 555 */         } else if (this.worldObj.rand.nextFloat() < 0.05D) {
/*     */           
/* 557 */           EntityChicken entitychicken1 = new EntityChicken(this.worldObj);
/* 558 */           entitychicken1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 559 */           entitychicken1.onInitialSpawn(difficulty, (IEntityLivingData)null);
/* 560 */           entitychicken1.setChickenJockey(true);
/* 561 */           this.worldObj.spawnEntityInWorld((Entity)entitychicken1);
/* 562 */           mountEntity((Entity)entitychicken1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 567 */     setBreakDoorsAItask((this.rand.nextFloat() < f * 0.1F));
/* 568 */     setEquipmentBasedOnDifficulty(difficulty);
/* 569 */     setEnchantmentBasedOnDifficulty(difficulty);
/*     */     
/* 571 */     if (getEquipmentInSlot(4) == null) {
/*     */       
/* 573 */       Calendar calendar = this.worldObj.getCurrentDate();
/*     */       
/* 575 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
/*     */         
/* 577 */         setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.lit_pumpkin : Blocks.pumpkin));
/* 578 */         this.equipmentDropChances[4] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 582 */     getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
/* 583 */     double d0 = this.rand.nextDouble() * 1.5D * f;
/*     */     
/* 585 */     if (d0 > 1.0D)
/*     */     {
/* 587 */       getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
/*     */     }
/*     */     
/* 590 */     if (this.rand.nextFloat() < f * 0.05F) {
/*     */       
/* 592 */       getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
/* 593 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
/* 594 */       setBreakDoorsAItask(true);
/*     */     } 
/*     */     
/* 597 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 605 */     ItemStack itemstack = player.getCurrentEquippedItem();
/*     */     
/* 607 */     if (itemstack != null && itemstack.getItem() == Items.golden_apple && itemstack.getMetadata() == 0 && isVillager() && isPotionActive(Potion.weakness)) {
/*     */       
/* 609 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 611 */         itemstack.stackSize--;
/*     */       }
/*     */       
/* 614 */       if (itemstack.stackSize <= 0)
/*     */       {
/* 616 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
/*     */       }
/*     */       
/* 619 */       if (!this.worldObj.isRemote)
/*     */       {
/* 621 */         startConversion(this.rand.nextInt(2401) + 3600);
/*     */       }
/*     */       
/* 624 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 628 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void startConversion(int ticks) {
/* 638 */     this.conversionTime = ticks;
/* 639 */     getDataWatcher().updateObject(14, Byte.valueOf((byte)1));
/* 640 */     removePotionEffect(Potion.weakness.id);
/* 641 */     addPotionEffect(new PotionEffect(Potion.damageBoost.id, ticks, Math.min(this.worldObj.getDifficulty().getDifficultyId() - 1, 0)));
/* 642 */     this.worldObj.setEntityState((Entity)this, (byte)16);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 647 */     if (id == 16) {
/*     */       
/* 649 */       if (!isSilent())
/*     */       {
/* 651 */         this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 656 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 665 */     return !isConverting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConverting() {
/* 673 */     return (getDataWatcher().getWatchableObjectByte(14) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void convertToVillager() {
/* 681 */     EntityVillager entityvillager = new EntityVillager(this.worldObj);
/* 682 */     entityvillager.copyLocationAndAnglesFrom((Entity)this);
/* 683 */     entityvillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null);
/* 684 */     entityvillager.setLookingForHome();
/*     */     
/* 686 */     if (isChild())
/*     */     {
/* 688 */       entityvillager.setGrowingAge(-24000);
/*     */     }
/*     */     
/* 691 */     this.worldObj.removeEntity((Entity)this);
/* 692 */     entityvillager.setNoAI(isAIDisabled());
/*     */     
/* 694 */     if (hasCustomName()) {
/*     */       
/* 696 */       entityvillager.setCustomNameTag(getCustomNameTag());
/* 697 */       entityvillager.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */     } 
/*     */     
/* 700 */     this.worldObj.spawnEntityInWorld((Entity)entityvillager);
/* 701 */     entityvillager.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
/* 702 */     this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getConversionTimeBoost() {
/* 710 */     int i = 1;
/*     */     
/* 712 */     if (this.rand.nextFloat() < 0.01F) {
/*     */       
/* 714 */       int j = 0;
/* 715 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */       
/* 717 */       for (int k = (int)this.posX - 4; k < (int)this.posX + 4 && j < 14; k++) {
/*     */         
/* 719 */         for (int l = (int)this.posY - 4; l < (int)this.posY + 4 && j < 14; l++) {
/*     */           
/* 721 */           for (int i1 = (int)this.posZ - 4; i1 < (int)this.posZ + 4 && j < 14; i1++) {
/*     */             
/* 723 */             Block block = this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos.set(k, l, i1)).getBlock();
/*     */             
/* 725 */             if (block == Blocks.iron_bars || block == Blocks.bed) {
/*     */               
/* 727 */               if (this.rand.nextFloat() < 0.3F)
/*     */               {
/* 729 */                 i++;
/*     */               }
/*     */               
/* 732 */               j++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 739 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChildSize(boolean isChild) {
/* 747 */     multiplySize(isChild ? 0.5F : 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setSize(float width, float height) {
/* 755 */     boolean flag = (this.zombieWidth > 0.0F && this.zombieHeight > 0.0F);
/* 756 */     this.zombieWidth = width;
/* 757 */     this.zombieHeight = height;
/*     */     
/* 759 */     if (!flag)
/*     */     {
/* 761 */       multiplySize(1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void multiplySize(float size) {
/* 770 */     super.setSize(this.zombieWidth * size, this.zombieHeight * size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 778 */     return isChild() ? 0.0D : -0.35D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 786 */     super.onDeath(cause);
/*     */     
/* 788 */     if (cause.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
/*     */       
/* 790 */       ((EntityCreeper)cause.getEntity()).func_175493_co();
/* 791 */       entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class GroupData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public boolean isChild;
/*     */     public boolean isVillager;
/*     */     
/*     */     GroupData(boolean isBaby, boolean isVillagerZombie) {
/* 802 */       this.isChild = false;
/* 803 */       this.isVillager = false;
/* 804 */       this.isChild = isBaby;
/* 805 */       this.isVillager = isVillagerZombie;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */