/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySpider extends EntityMob {
/*     */   public EntitySpider(World worldIn) {
/*  24 */     super(worldIn);
/*  25 */     setSize(1.4F, 0.9F);
/*  26 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  27 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4F));
/*  28 */     this.tasks.addTask(4, (EntityAIBase)new AISpiderAttack(this, (Class)EntityPlayer.class));
/*  29 */     this.tasks.addTask(4, (EntityAIBase)new AISpiderAttack(this, (Class)EntityIronGolem.class));
/*  30 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander(this, 0.8D));
/*  31 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  32 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  33 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  34 */     this.targetTasks.addTask(2, (EntityAIBase)new AISpiderTarget<>(this, EntityPlayer.class));
/*  35 */     this.targetTasks.addTask(3, (EntityAIBase)new AISpiderTarget<>(this, EntityIronGolem.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/*  43 */     return (this.height * 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/*  51 */     return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  56 */     super.entityInit();
/*  57 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  65 */     super.onUpdate();
/*     */     
/*  67 */     if (!this.worldObj.isRemote)
/*     */     {
/*  69 */       setBesideClimbableBlock(this.isCollidedHorizontally);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  75 */     super.applyEntityAttributes();
/*  76 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
/*  77 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  85 */     return "mob.spider.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  93 */     return "mob.spider.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 101 */     return "mob.spider.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 106 */     playSound("mob.spider.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 111 */     return Items.string;
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
/* 123 */     super.dropFewItems(wasRecentlyHit, lootingModifier);
/*     */     
/* 125 */     if (wasRecentlyHit && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + lootingModifier) > 0))
/*     */     {
/* 127 */       dropItem(Items.spider_eye, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnLadder() {
/* 136 */     return isBesideClimbableBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInWeb() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 151 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
/* 156 */     return (potioneffectIn.getPotionID() == Potion.poison.id) ? false : super.isPotionApplicable(potioneffectIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBesideClimbableBlock() {
/* 165 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBesideClimbableBlock(boolean p_70839_1_) {
/* 174 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 176 */     if (p_70839_1_) {
/*     */       
/* 178 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     else {
/*     */       
/* 182 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 185 */     this.dataWatcher.updateObject(16, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 194 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 196 */     if (this.worldObj.rand.nextInt(100) == 0) {
/*     */       
/* 198 */       EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
/* 199 */       entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 200 */       entityskeleton.onInitialSpawn(difficulty, (IEntityLivingData)null);
/* 201 */       this.worldObj.spawnEntityInWorld((Entity)entityskeleton);
/* 202 */       entityskeleton.mountEntity((Entity)this);
/*     */     } 
/*     */     
/* 205 */     if (livingdata == null) {
/*     */       
/* 207 */       livingdata = new GroupData();
/*     */       
/* 209 */       if (this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1F * difficulty.getClampedAdditionalDifficulty())
/*     */       {
/* 211 */         ((GroupData)livingdata).func_111104_a(this.worldObj.rand);
/*     */       }
/*     */     } 
/*     */     
/* 215 */     if (livingdata instanceof GroupData) {
/*     */       
/* 217 */       int i = ((GroupData)livingdata).potionEffectId;
/*     */       
/* 219 */       if (i > 0 && Potion.potionTypes[i] != null)
/*     */       {
/* 221 */         addPotionEffect(new PotionEffect(i, 2147483647));
/*     */       }
/*     */     } 
/*     */     
/* 225 */     return livingdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 230 */     return 0.65F;
/*     */   }
/*     */   
/*     */   static class AISpiderAttack
/*     */     extends EntityAIAttackOnCollide
/*     */   {
/*     */     public AISpiderAttack(EntitySpider spider, Class<? extends Entity> targetClass) {
/* 237 */       super(spider, targetClass, 1.0D, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 242 */       float f = this.attacker.getBrightness(1.0F);
/*     */       
/* 244 */       if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
/*     */         
/* 246 */         this.attacker.setAttackTarget((EntityLivingBase)null);
/* 247 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 251 */       return super.continueExecuting();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected double func_179512_a(EntityLivingBase attackTarget) {
/* 257 */       return (4.0F + attackTarget.width);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISpiderTarget<T extends EntityLivingBase>
/*     */     extends EntityAINearestAttackableTarget
/*     */   {
/*     */     public AISpiderTarget(EntitySpider spider, Class<T> classTarget) {
/* 265 */       super(spider, classTarget, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 270 */       float f = this.taskOwner.getBrightness(1.0F);
/* 271 */       return (f >= 0.5F) ? false : super.shouldExecute();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GroupData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public int potionEffectId;
/*     */     
/*     */     public void func_111104_a(Random rand) {
/* 281 */       int i = rand.nextInt(5);
/*     */       
/* 283 */       if (i <= 1) {
/*     */         
/* 285 */         this.potionEffectId = Potion.moveSpeed.id;
/*     */       }
/* 287 */       else if (i <= 2) {
/*     */         
/* 289 */         this.potionEffectId = Potion.damageBoost.id;
/*     */       }
/* 291 */       else if (i <= 3) {
/*     */         
/* 293 */         this.potionEffectId = Potion.regeneration.id;
/*     */       }
/* 295 */       else if (i <= 4) {
/*     */         
/* 297 */         this.potionEffectId = Potion.invisibility.id;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntitySpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */