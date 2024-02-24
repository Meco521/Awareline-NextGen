/*     */ package net.minecraft.entity.monster;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityEndermite extends EntityMob {
/*  16 */   private int lifetime = 0;
/*     */   
/*     */   private boolean playerSpawned = false;
/*     */   
/*     */   public EntityEndermite(World worldIn) {
/*  21 */     super(worldIn);
/*  22 */     this.experienceValue = 3;
/*  23 */     setSize(0.4F, 0.3F);
/*  24 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  25 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  26 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  27 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  28 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  29 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  30 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  35 */     return 0.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  40 */     super.applyEntityAttributes();
/*  41 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  42 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  43 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  60 */     return "mob.silverfish.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  68 */     return "mob.silverfish.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  76 */     return "mob.silverfish.kill";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  81 */     playSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  94 */     super.readEntityFromNBT(tagCompund);
/*  95 */     this.lifetime = tagCompund.getInteger("Lifetime");
/*  96 */     this.playerSpawned = tagCompund.getBoolean("PlayerSpawned");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 104 */     super.writeEntityToNBT(tagCompound);
/* 105 */     tagCompound.setInteger("Lifetime", this.lifetime);
/* 106 */     tagCompound.setBoolean("PlayerSpawned", this.playerSpawned);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 114 */     this.renderYawOffset = this.rotationYaw;
/* 115 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpawnedByPlayer() {
/* 120 */     return this.playerSpawned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnedByPlayer(boolean spawnedByPlayer) {
/* 128 */     this.playerSpawned = spawnedByPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 137 */     super.onLivingUpdate();
/*     */     
/* 139 */     if (this.worldObj.isRemote) {
/*     */       
/* 141 */       for (int i = 0; i < 2; i++)
/*     */       {
/* 143 */         this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 148 */       if (!isNoDespawnRequired())
/*     */       {
/* 150 */         this.lifetime++;
/*     */       }
/*     */       
/* 153 */       if (this.lifetime >= 2400)
/*     */       {
/* 155 */         setDead();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 173 */     if (super.getCanSpawnHere()) {
/*     */       
/* 175 */       EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 5.0D);
/* 176 */       return (entityplayer == null);
/*     */     } 
/*     */ 
/*     */     
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 189 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityEndermite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */