/*     */ package net.minecraft.entity.passive;
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIOcelotSit;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityOcelot extends EntityTameable {
/*     */   private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
/*     */   
/*     */   public EntityOcelot(World worldIn) {
/*  35 */     super(worldIn);
/*  36 */     setSize(0.6F, 0.7F);
/*  37 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  38 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  39 */     this.tasks.addTask(2, (EntityAIBase)this.aiSit);
/*  40 */     this.tasks.addTask(3, (EntityAIBase)(this.aiTempt = new EntityAITempt((EntityCreature)this, 0.6D, Items.fish, true)));
/*  41 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
/*  42 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIOcelotSit(this, 0.8D));
/*  43 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.3F));
/*  44 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIOcelotAttack((EntityLiving)this));
/*  45 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIMate(this, 0.8D));
/*  46 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
/*  47 */     this.tasks.addTask(11, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 10.0F));
/*  48 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAITargetNonTamed(this, EntityChicken.class, false, (Predicate)null));
/*     */   }
/*     */   private final EntityAITempt aiTempt;
/*     */   
/*     */   protected void entityInit() {
/*  53 */     super.entityInit();
/*  54 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAITasks() {
/*  59 */     if (getMoveHelper().isUpdating()) {
/*     */       
/*  61 */       double d0 = getMoveHelper().getSpeed();
/*     */       
/*  63 */       if (d0 == 0.6D)
/*     */       {
/*  65 */         setSneaking(true);
/*  66 */         setSprinting(false);
/*     */       }
/*  68 */       else if (d0 == 1.33D)
/*     */       {
/*  70 */         setSneaking(false);
/*  71 */         setSprinting(true);
/*     */       }
/*     */       else
/*     */       {
/*  75 */         setSneaking(false);
/*  76 */         setSprinting(false);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  81 */       setSneaking(false);
/*  82 */       setSprinting(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/*  91 */     return (!isTamed() && this.ticksExisted > 2400);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  96 */     super.applyEntityAttributes();
/*  97 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  98 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 110 */     super.writeEntityToNBT(tagCompound);
/* 111 */     tagCompound.setInteger("CatType", getTameSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 119 */     super.readEntityFromNBT(tagCompund);
/* 120 */     setTameSkin(tagCompund.getInteger("CatType"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 128 */     return isTamed() ? (isInLove() ? "mob.cat.purr" : ((this.rand.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 136 */     return "mob.cat.hitt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 144 */     return "mob.cat.hitt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 152 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 157 */     return Items.leather;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 162 */     return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 170 */     if (isEntityInvulnerable(source))
/*     */     {
/* 172 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 176 */     this.aiSit.setSitting(false);
/* 177 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 197 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 199 */     if (isTamed()) {
/*     */       
/* 201 */       if (isOwner((EntityLivingBase)player) && !this.worldObj.isRemote && !isBreedingItem(itemstack))
/*     */       {
/* 203 */         this.aiSit.setSitting(!isSitting());
/*     */       }
/*     */     }
/* 206 */     else if (this.aiTempt.isRunning() && itemstack != null && itemstack.getItem() == Items.fish && player.getDistanceSqToEntity((Entity)this) < 9.0D) {
/*     */       
/* 208 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 210 */         itemstack.stackSize--;
/*     */       }
/*     */       
/* 213 */       if (itemstack.stackSize <= 0)
/*     */       {
/* 215 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
/*     */       }
/*     */       
/* 218 */       if (!this.worldObj.isRemote)
/*     */       {
/* 220 */         if (this.rand.nextInt(3) == 0) {
/*     */           
/* 222 */           setTamed(true);
/* 223 */           setTameSkin(1 + this.worldObj.rand.nextInt(3));
/* 224 */           setOwnerId(player.getUniqueID().toString());
/* 225 */           playTameEffect(true);
/* 226 */           this.aiSit.setSitting(true);
/* 227 */           this.worldObj.setEntityState((Entity)this, (byte)7);
/*     */         }
/*     */         else {
/*     */           
/* 231 */           playTameEffect(false);
/* 232 */           this.worldObj.setEntityState((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 236 */       return true;
/*     */     } 
/*     */     
/* 239 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityOcelot createChild(EntityAgeable ageable) {
/* 244 */     EntityOcelot entityocelot = new EntityOcelot(this.worldObj);
/*     */     
/* 246 */     if (isTamed()) {
/*     */       
/* 248 */       entityocelot.setOwnerId(getOwnerId());
/* 249 */       entityocelot.setTamed(true);
/* 250 */       entityocelot.setTameSkin(getTameSkin());
/*     */     } 
/*     */     
/* 253 */     return entityocelot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 262 */     return (stack != null && stack.getItem() == Items.fish);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 270 */     if (otherAnimal == this)
/*     */     {
/* 272 */       return false;
/*     */     }
/* 274 */     if (!isTamed())
/*     */     {
/* 276 */       return false;
/*     */     }
/* 278 */     if (!(otherAnimal instanceof EntityOcelot))
/*     */     {
/* 280 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 284 */     EntityOcelot entityocelot = (EntityOcelot)otherAnimal;
/* 285 */     return !entityocelot.isTamed() ? false : ((isInLove() && entityocelot.isInLove()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTameSkin() {
/* 291 */     return this.dataWatcher.getWatchableObjectByte(18);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTameSkin(int skinId) {
/* 296 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)skinId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 304 */     return (this.worldObj.rand.nextInt(3) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 312 */     if (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox())) {
/*     */       
/* 314 */       BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */       
/* 316 */       if (blockpos.getY() < this.worldObj.getSeaLevel())
/*     */       {
/* 318 */         return false;
/*     */       }
/*     */       
/* 321 */       Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*     */       
/* 323 */       if (block == Blocks.grass || block.getMaterial() == Material.leaves)
/*     */       {
/* 325 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 337 */     return hasCustomName() ? getCustomNameTag() : (isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 342 */     super.setTamed(tamed);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupTamedAI() {
/* 347 */     if (this.avoidEntity == null)
/*     */     {
/* 349 */       this.avoidEntity = new EntityAIAvoidEntity((EntityCreature)this, EntityPlayer.class, 16.0F, 0.8D, 1.33D);
/*     */     }
/*     */     
/* 352 */     this.tasks.removeTask((EntityAIBase)this.avoidEntity);
/*     */     
/* 354 */     if (!isTamed())
/*     */     {
/* 356 */       this.tasks.addTask(4, (EntityAIBase)this.avoidEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 366 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 368 */     if (this.worldObj.rand.nextInt(7) == 0)
/*     */     {
/* 370 */       for (int i = 0; i < 2; i++) {
/*     */         
/* 372 */         EntityOcelot entityocelot = new EntityOcelot(this.worldObj);
/* 373 */         entityocelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 374 */         entityocelot.setGrowingAge(-24000);
/* 375 */         this.worldObj.spawnEntityInWorld((Entity)entityocelot);
/*     */       } 
/*     */     }
/*     */     
/* 379 */     return livingdata;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */