/*     */ package net.minecraft.entity.monster;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityBlaze extends EntityMob {
/*  19 */   private float heightOffset = 0.5F;
/*     */ 
/*     */   
/*     */   private int heightOffsetUpdateTime;
/*     */ 
/*     */   
/*     */   public EntityBlaze(World worldIn) {
/*  26 */     super(worldIn);
/*  27 */     this.isImmuneToFire = true;
/*  28 */     this.experienceValue = 10;
/*  29 */     this.tasks.addTask(4, new AIFireballAttack(this));
/*  30 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  31 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  32 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  33 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  34 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  35 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  40 */     super.applyEntityAttributes();
/*  41 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
/*  42 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*  43 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  48 */     super.entityInit();
/*  49 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  57 */     return "mob.blaze.breathe";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  65 */     return "mob.blaze.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  73 */     return "mob.blaze.death";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  78 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/*  86 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  95 */     if (!this.onGround && this.motionY < 0.0D)
/*     */     {
/*  97 */       this.motionY *= 0.6D;
/*     */     }
/*     */     
/* 100 */     if (this.worldObj.isRemote) {
/*     */       
/* 102 */       if (this.rand.nextInt(24) == 0 && !isSilent())
/*     */       {
/* 104 */         this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.fire", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*     */       
/* 107 */       for (int i = 0; i < 2; i++)
/*     */       {
/* 109 */         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 118 */     if (isWet())
/*     */     {
/* 120 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*     */     }
/*     */     
/* 123 */     this.heightOffsetUpdateTime--;
/*     */     
/* 125 */     if (this.heightOffsetUpdateTime <= 0) {
/*     */       
/* 127 */       this.heightOffsetUpdateTime = 100;
/* 128 */       this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
/*     */     } 
/*     */     
/* 131 */     EntityLivingBase entitylivingbase = getAttackTarget();
/*     */     
/* 133 */     if (entitylivingbase != null && entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + getEyeHeight() + this.heightOffset) {
/*     */       
/* 135 */       this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
/* 136 */       this.isAirBorne = true;
/*     */     } 
/*     */     
/* 139 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 148 */     return Items.blaze_rod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 156 */     return func_70845_n();
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
/* 168 */     if (wasRecentlyHit) {
/*     */       
/* 170 */       int i = this.rand.nextInt(2 + lootingModifier);
/*     */       
/* 172 */       for (int j = 0; j < i; j++)
/*     */       {
/* 174 */         dropItem(Items.blaze_rod, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70845_n() {
/* 181 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnFire(boolean onFire) {
/* 186 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 188 */     if (onFire) {
/*     */       
/* 190 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     else {
/*     */       
/* 194 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 197 */     this.dataWatcher.updateObject(16, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 205 */     return true;
/*     */   }
/*     */   
/*     */   static class AIFireballAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityBlaze blaze;
/*     */     private int field_179467_b;
/*     */     private int field_179468_c;
/*     */     
/*     */     public AIFireballAttack(EntityBlaze p_i45846_1_) {
/* 216 */       this.blaze = p_i45846_1_;
/* 217 */       setMutexBits(3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 222 */       EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
/* 223 */       return (entitylivingbase != null && entitylivingbase.isEntityAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 228 */       this.field_179467_b = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 233 */       this.blaze.setOnFire(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 238 */       this.field_179468_c--;
/* 239 */       EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
/* 240 */       double d0 = this.blaze.getDistanceSqToEntity((Entity)entitylivingbase);
/*     */       
/* 242 */       if (d0 < 4.0D) {
/*     */         
/* 244 */         if (this.field_179468_c <= 0) {
/*     */           
/* 246 */           this.field_179468_c = 20;
/* 247 */           this.blaze.attackEntityAsMob((Entity)entitylivingbase);
/*     */         } 
/*     */         
/* 250 */         this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
/*     */       }
/* 252 */       else if (d0 < 256.0D) {
/*     */         
/* 254 */         double d1 = entitylivingbase.posX - this.blaze.posX;
/* 255 */         double d2 = (entitylivingbase.getEntityBoundingBox()).minY + (entitylivingbase.height / 2.0F) - this.blaze.posY + (this.blaze.height / 2.0F);
/* 256 */         double d3 = entitylivingbase.posZ - this.blaze.posZ;
/*     */         
/* 258 */         if (this.field_179468_c <= 0) {
/*     */           
/* 260 */           this.field_179467_b++;
/*     */           
/* 262 */           if (this.field_179467_b == 1) {
/*     */             
/* 264 */             this.field_179468_c = 60;
/* 265 */             this.blaze.setOnFire(true);
/*     */           }
/* 267 */           else if (this.field_179467_b <= 4) {
/*     */             
/* 269 */             this.field_179468_c = 6;
/*     */           }
/*     */           else {
/*     */             
/* 273 */             this.field_179468_c = 100;
/* 274 */             this.field_179467_b = 0;
/* 275 */             this.blaze.setOnFire(false);
/*     */           } 
/*     */           
/* 278 */           if (this.field_179467_b > 1) {
/*     */             
/* 280 */             float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.5F;
/* 281 */             this.blaze.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);
/*     */             
/* 283 */             for (int i = 0; i < 1; i++) {
/*     */               
/* 285 */               EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.blaze.worldObj, (EntityLivingBase)this.blaze, d1 + this.blaze.getRNG().nextGaussian() * f, d2, d3 + this.blaze.getRNG().nextGaussian() * f);
/* 286 */               entitysmallfireball.posY = this.blaze.posY + (this.blaze.height / 2.0F) + 0.5D;
/* 287 */               this.blaze.worldObj.spawnEntityInWorld((Entity)entitysmallfireball);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 292 */         this.blaze.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 10.0F, 10.0F);
/*     */       }
/*     */       else {
/*     */         
/* 296 */         this.blaze.getNavigator().clearPathEntity();
/* 297 */         this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
/*     */       } 
/*     */       
/* 300 */       super.updateTask();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityBlaze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */