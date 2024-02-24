/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityChicken extends EntityAnimal {
/*  23 */   public float wingRotDelta = 1.0F; public float wingRotation;
/*     */   public float destPos;
/*     */   public float field_70884_g;
/*     */   public float field_70888_h;
/*     */   public int timeUntilNextEgg;
/*     */   public boolean chickenJockey;
/*     */   
/*     */   public EntityChicken(World worldIn) {
/*  31 */     super(worldIn);
/*  32 */     setSize(0.4F, 0.7F);
/*  33 */     this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
/*  34 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  35 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.4D));
/*  36 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  37 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.wheat_seeds, false));
/*  38 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  39 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  40 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  41 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  46 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  51 */     super.applyEntityAttributes();
/*  52 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
/*  53 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  62 */     super.onLivingUpdate();
/*  63 */     this.field_70888_h = this.wingRotation;
/*  64 */     this.field_70884_g = this.destPos;
/*  65 */     this.destPos = (float)(this.destPos + (this.onGround ? -1 : 4) * 0.3D);
/*  66 */     this.destPos = MathHelper.clamp_float(this.destPos, 0.0F, 1.0F);
/*     */     
/*  68 */     if (!this.onGround && this.wingRotDelta < 1.0F)
/*     */     {
/*  70 */       this.wingRotDelta = 1.0F;
/*     */     }
/*     */     
/*  73 */     this.wingRotDelta = (float)(this.wingRotDelta * 0.9D);
/*     */     
/*  75 */     if (!this.onGround && this.motionY < 0.0D)
/*     */     {
/*  77 */       this.motionY *= 0.6D;
/*     */     }
/*     */     
/*  80 */     this.wingRotation += this.wingRotDelta * 2.0F;
/*     */     
/*  82 */     if (!this.worldObj.isRemote && !isChild() && !this.chickenJockey && --this.timeUntilNextEgg <= 0) {
/*     */       
/*  84 */       playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  85 */       dropItem(Items.egg, 1);
/*  86 */       this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
/*     */     } 
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
/*     */   protected String getLivingSound() {
/*  99 */     return "mob.chicken.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 107 */     return "mob.chicken.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 115 */     return "mob.chicken.hurt";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 120 */     playSound("mob.chicken.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 125 */     return Items.feather;
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
/* 137 */     int i = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 139 */     for (int j = 0; j < i; j++)
/*     */     {
/* 141 */       dropItem(Items.feather, 1);
/*     */     }
/*     */     
/* 144 */     if (isBurning()) {
/*     */       
/* 146 */       dropItem(Items.cooked_chicken, 1);
/*     */     }
/*     */     else {
/*     */       
/* 150 */       dropItem(Items.chicken, 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityChicken createChild(EntityAgeable ageable) {
/* 156 */     return new EntityChicken(this.worldObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 165 */     return (stack != null && stack.getItem() == Items.wheat_seeds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 173 */     super.readEntityFromNBT(tagCompund);
/* 174 */     this.chickenJockey = tagCompund.getBoolean("IsChickenJockey");
/*     */     
/* 176 */     if (tagCompund.hasKey("EggLayTime"))
/*     */     {
/* 178 */       this.timeUntilNextEgg = tagCompund.getInteger("EggLayTime");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 187 */     return this.chickenJockey ? 10 : super.getExperiencePoints(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 195 */     super.writeEntityToNBT(tagCompound);
/* 196 */     tagCompound.setBoolean("IsChickenJockey", this.chickenJockey);
/* 197 */     tagCompound.setInteger("EggLayTime", this.timeUntilNextEgg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 205 */     return (this.chickenJockey && this.riddenByEntity == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRiderPosition() {
/* 210 */     super.updateRiderPosition();
/* 211 */     float f = MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F);
/* 212 */     float f1 = MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F);
/* 213 */     float f2 = 0.1F;
/* 214 */     float f3 = 0.0F;
/* 215 */     this.riddenByEntity.setPosition(this.posX + (f2 * f), this.posY + (this.height * 0.5F) + this.riddenByEntity.getYOffset() + f3, this.posZ - (f2 * f1));
/*     */     
/* 217 */     if (this.riddenByEntity instanceof EntityLivingBase)
/*     */     {
/* 219 */       ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChickenJockey() {
/* 228 */     return this.chickenJockey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChickenJockey(boolean jockey) {
/* 236 */     this.chickenJockey = jockey;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */