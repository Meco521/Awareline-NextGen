/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPigZombie extends EntityZombie {
/*  21 */   private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
/*  22 */   private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
/*     */ 
/*     */   
/*     */   private int angerLevel;
/*     */   
/*     */   private int randomSoundDelay;
/*     */   
/*     */   private UUID angerTargetUUID;
/*     */ 
/*     */   
/*     */   public EntityPigZombie(World worldIn) {
/*  33 */     super(worldIn);
/*  34 */     this.isImmuneToFire = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRevengeTarget(EntityLivingBase livingBase) {
/*  39 */     super.setRevengeTarget(livingBase);
/*     */     
/*  41 */     if (livingBase != null)
/*     */     {
/*  43 */       this.angerTargetUUID = livingBase.getUniqueID();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAI() {
/*  49 */     this.targetTasks.addTask(1, (EntityAIBase)new AIHurtByAggressor(this));
/*  50 */     this.targetTasks.addTask(2, (EntityAIBase)new AITargetAggressor(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  55 */     super.applyEntityAttributes();
/*  56 */     getEntityAttribute(reinforcementChance).setBaseValue(0.0D);
/*  57 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*  58 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  66 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  71 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*     */     
/*  73 */     if (isAngry()) {
/*     */       
/*  75 */       if (!isChild() && !iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
/*     */       {
/*  77 */         iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
/*     */       }
/*     */       
/*  80 */       this.angerLevel--;
/*     */     }
/*  82 */     else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
/*     */       
/*  84 */       iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
/*     */     } 
/*     */     
/*  87 */     if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0)
/*     */     {
/*  89 */       playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
/*     */     }
/*     */     
/*  92 */     if (this.angerLevel > 0 && this.angerTargetUUID != null && getAITarget() == null) {
/*     */       
/*  94 */       EntityPlayer entityplayer = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
/*  95 */       setRevengeTarget((EntityLivingBase)entityplayer);
/*  96 */       this.attackingPlayer = entityplayer;
/*  97 */       this.recentlyHit = getRevengeTimer();
/*     */     } 
/*     */     
/* 100 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 108 */     return (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 116 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 124 */     super.writeEntityToNBT(tagCompound);
/* 125 */     tagCompound.setShort("Anger", (short)this.angerLevel);
/*     */     
/* 127 */     if (this.angerTargetUUID != null) {
/*     */       
/* 129 */       tagCompound.setString("HurtBy", this.angerTargetUUID.toString());
/*     */     }
/*     */     else {
/*     */       
/* 133 */       tagCompound.setString("HurtBy", "");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 142 */     super.readEntityFromNBT(tagCompund);
/* 143 */     this.angerLevel = tagCompund.getShort("Anger");
/* 144 */     String s = tagCompund.getString("HurtBy");
/*     */     
/* 146 */     if (!s.isEmpty()) {
/*     */       
/* 148 */       this.angerTargetUUID = UUID.fromString(s);
/* 149 */       EntityPlayer entityplayer = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
/* 150 */       setRevengeTarget((EntityLivingBase)entityplayer);
/*     */       
/* 152 */       if (entityplayer != null) {
/*     */         
/* 154 */         this.attackingPlayer = entityplayer;
/* 155 */         this.recentlyHit = getRevengeTimer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 165 */     if (isEntityInvulnerable(source))
/*     */     {
/* 167 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 171 */     Entity entity = source.getEntity();
/*     */     
/* 173 */     if (entity instanceof EntityPlayer)
/*     */     {
/* 175 */       becomeAngryAt(entity);
/*     */     }
/*     */     
/* 178 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void becomeAngryAt(Entity p_70835_1_) {
/* 187 */     this.angerLevel = 400 + this.rand.nextInt(400);
/* 188 */     this.randomSoundDelay = this.rand.nextInt(40);
/*     */     
/* 190 */     if (p_70835_1_ instanceof EntityLivingBase)
/*     */     {
/* 192 */       setRevengeTarget((EntityLivingBase)p_70835_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAngry() {
/* 198 */     return (this.angerLevel > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 206 */     return "mob.zombiepig.zpig";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 214 */     return "mob.zombiepig.zpighurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 222 */     return "mob.zombiepig.zpigdeath";
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
/* 234 */     int i = this.rand.nextInt(2 + lootingModifier);
/*     */     
/* 236 */     for (int j = 0; j < i; j++)
/*     */     {
/* 238 */       dropItem(Items.rotten_flesh, 1);
/*     */     }
/*     */     
/* 241 */     i = this.rand.nextInt(2 + lootingModifier);
/*     */     
/* 243 */     for (int k = 0; k < i; k++)
/*     */     {
/* 245 */       dropItem(Items.gold_nugget, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 262 */     dropItem(Items.gold_ingot, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 270 */     setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 279 */     super.onInitialSpawn(difficulty, livingdata);
/* 280 */     setVillager(false);
/* 281 */     return livingdata;
/*     */   }
/*     */   
/*     */   static class AIHurtByAggressor
/*     */     extends EntityAIHurtByTarget
/*     */   {
/*     */     public AIHurtByAggressor(EntityPigZombie p_i45828_1_) {
/* 288 */       super(p_i45828_1_, true, new Class[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
/* 293 */       super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
/*     */       
/* 295 */       if (creatureIn instanceof EntityPigZombie)
/*     */       {
/* 297 */         ((EntityPigZombie)creatureIn).becomeAngryAt((Entity)entityLivingBaseIn);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static class AITargetAggressor
/*     */     extends EntityAINearestAttackableTarget<EntityPlayer>
/*     */   {
/*     */     public AITargetAggressor(EntityPigZombie p_i45829_1_) {
/* 306 */       super(p_i45829_1_, EntityPlayer.class, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 311 */       return (((EntityPigZombie)this.taskOwner).isAngry() && super.shouldExecute());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityPigZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */