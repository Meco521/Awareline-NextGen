/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIControlledByPlayer;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPig extends EntityAnimal {
/*     */   public EntityPig(World worldIn) {
/*  26 */     super(worldIn);
/*  27 */     setSize(0.9F, 0.9F);
/*  28 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  29 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  30 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/*  31 */     this.tasks.addTask(2, (EntityAIBase)(this.aiControlledByPlayer = new EntityAIControlledByPlayer((EntityLiving)this, 0.3F)));
/*  32 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  33 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, Items.carrot_on_a_stick, false));
/*  34 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, Items.carrot, false));
/*  35 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  36 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  37 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  38 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */   private final EntityAIControlledByPlayer aiControlledByPlayer;
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  43 */     super.applyEntityAttributes();
/*  44 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  45 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeSteered() {
/*  54 */     ItemStack itemstack = ((EntityPlayer)this.riddenByEntity).getHeldItem();
/*  55 */     return (itemstack != null && itemstack.getItem() == Items.carrot_on_a_stick);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  60 */     super.entityInit();
/*  61 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  69 */     super.writeEntityToNBT(tagCompound);
/*  70 */     tagCompound.setBoolean("Saddle", getSaddled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  78 */     super.readEntityFromNBT(tagCompund);
/*  79 */     setSaddled(tagCompund.getBoolean("Saddle"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  87 */     return "mob.pig.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  95 */     return "mob.pig.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 103 */     return "mob.pig.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 108 */     playSound("mob.pig.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 116 */     if (super.interact(player))
/*     */     {
/* 118 */       return true;
/*     */     }
/* 120 */     if (!getSaddled() || this.worldObj.isRemote || (this.riddenByEntity != null && this.riddenByEntity != player))
/*     */     {
/* 122 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 126 */     player.mountEntity((Entity)this);
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 133 */     return isBurning() ? Items.cooked_porkchop : Items.porkchop;
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
/* 145 */     int i = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 147 */     for (int j = 0; j < i; j++) {
/*     */       
/* 149 */       if (isBurning()) {
/*     */         
/* 151 */         dropItem(Items.cooked_porkchop, 1);
/*     */       }
/*     */       else {
/*     */         
/* 155 */         dropItem(Items.porkchop, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     if (getSaddled())
/*     */     {
/* 161 */       dropItem(Items.saddle, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSaddled() {
/* 170 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaddled(boolean saddled) {
/* 178 */     if (saddled) {
/*     */       
/* 180 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
/*     */     }
/*     */     else {
/*     */       
/* 184 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 193 */     if (!this.worldObj.isRemote && !this.isDead) {
/*     */       
/* 195 */       EntityPigZombie entitypigzombie = new EntityPigZombie(this.worldObj);
/* 196 */       entitypigzombie.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
/* 197 */       entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 198 */       entitypigzombie.setNoAI(isAIDisabled());
/*     */       
/* 200 */       if (hasCustomName()) {
/*     */         
/* 202 */         entitypigzombie.setCustomNameTag(getCustomNameTag());
/* 203 */         entitypigzombie.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 206 */       this.worldObj.spawnEntityInWorld((Entity)entitypigzombie);
/* 207 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 213 */     super.fall(distance, damageMultiplier);
/*     */     
/* 215 */     if (distance > 5.0F && this.riddenByEntity instanceof EntityPlayer)
/*     */     {
/* 217 */       ((EntityPlayer)this.riddenByEntity).triggerAchievement((StatBase)AchievementList.flyPig);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPig createChild(EntityAgeable ageable) {
/* 223 */     return new EntityPig(this.worldObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 232 */     return (stack != null && stack.getItem() == Items.carrot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAIControlledByPlayer getAIControlledByPlayer() {
/* 240 */     return this.aiControlledByPlayer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */