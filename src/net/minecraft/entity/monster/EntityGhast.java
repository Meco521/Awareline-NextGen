/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityGhast extends EntityFlying implements IMob {
/*  24 */   private int explosionStrength = 1;
/*     */ 
/*     */   
/*     */   public EntityGhast(World worldIn) {
/*  28 */     super(worldIn);
/*  29 */     setSize(4.0F, 4.0F);
/*  30 */     this.isImmuneToFire = true;
/*  31 */     this.experienceValue = 5;
/*  32 */     this.moveHelper = new GhastMoveHelper(this);
/*  33 */     this.tasks.addTask(5, new AIRandomFly(this));
/*  34 */     this.tasks.addTask(7, new AILookAround(this));
/*  35 */     this.tasks.addTask(7, new AIFireballAttack(this));
/*  36 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIFindEntityNearestPlayer((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttacking() {
/*  41 */     return (this.dataWatcher.getWatchableObjectByte(16) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttacking(boolean attacking) {
/*  46 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)(attacking ? 1 : 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFireballStrength() {
/*  51 */     return this.explosionStrength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  59 */     super.onUpdate();
/*     */     
/*  61 */     if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
/*     */     {
/*  63 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  72 */     if (isEntityInvulnerable(source))
/*     */     {
/*  74 */       return false;
/*     */     }
/*  76 */     if ("fireball".equals(source.getDamageType()) && source.getEntity() instanceof EntityPlayer) {
/*     */       
/*  78 */       super.attackEntityFrom(source, 1000.0F);
/*  79 */       ((EntityPlayer)source.getEntity()).triggerAchievement((StatBase)AchievementList.ghast);
/*  80 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  84 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  90 */     super.entityInit();
/*  91 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  96 */     super.applyEntityAttributes();
/*  97 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  98 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 106 */     return "mob.ghast.moan";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 114 */     return "mob.ghast.scream";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 122 */     return "mob.ghast.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 127 */     return Items.gunpowder;
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
/* 139 */     int i = this.rand.nextInt(2) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 141 */     for (int j = 0; j < i; j++)
/*     */     {
/* 143 */       dropItem(Items.ghast_tear, 1);
/*     */     }
/*     */     
/* 146 */     i = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 148 */     for (int k = 0; k < i; k++)
/*     */     {
/* 150 */       dropItem(Items.gunpowder, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 159 */     return 10.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 167 */     return (this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSpawnedInChunk() {
/* 175 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 183 */     super.writeEntityToNBT(tagCompound);
/* 184 */     tagCompound.setInteger("ExplosionPower", this.explosionStrength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 192 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 194 */     if (tagCompund.hasKey("ExplosionPower", 99))
/*     */     {
/* 196 */       this.explosionStrength = tagCompund.getInteger("ExplosionPower");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 202 */     return 2.6F;
/*     */   }
/*     */   
/*     */   static class AIFireballAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     public int attackTimer;
/*     */     
/*     */     public AIFireballAttack(EntityGhast ghast) {
/* 212 */       this.parentEntity = ghast;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 217 */       return (this.parentEntity.getAttackTarget() != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 222 */       this.attackTimer = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 227 */       this.parentEntity.setAttacking(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 232 */       EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
/* 233 */       double d0 = 64.0D;
/*     */       
/* 235 */       if (entitylivingbase.getDistanceSqToEntity((Entity)this.parentEntity) < d0 * d0 && this.parentEntity.canEntityBeSeen((Entity)entitylivingbase)) {
/*     */         
/* 237 */         World world = this.parentEntity.worldObj;
/* 238 */         this.attackTimer++;
/*     */         
/* 240 */         if (this.attackTimer == 10)
/*     */         {
/* 242 */           world.playAuxSFXAtEntity((EntityPlayer)null, 1007, new BlockPos((Entity)this.parentEntity), 0);
/*     */         }
/*     */         
/* 245 */         if (this.attackTimer == 20)
/*     */         {
/* 247 */           double d1 = 4.0D;
/* 248 */           Vec3 vec3 = this.parentEntity.getLook(1.0F);
/* 249 */           double d2 = entitylivingbase.posX - this.parentEntity.posX + vec3.xCoord * d1;
/* 250 */           double d3 = (entitylivingbase.getEntityBoundingBox()).minY + (entitylivingbase.height / 2.0F) - 0.5D + this.parentEntity.posY + (this.parentEntity.height / 2.0F);
/* 251 */           double d4 = entitylivingbase.posZ - this.parentEntity.posZ + vec3.zCoord * d1;
/* 252 */           world.playAuxSFXAtEntity((EntityPlayer)null, 1008, new BlockPos((Entity)this.parentEntity), 0);
/* 253 */           EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, (EntityLivingBase)this.parentEntity, d2, d3, d4);
/* 254 */           entitylargefireball.explosionPower = this.parentEntity.getFireballStrength();
/* 255 */           entitylargefireball.posX = this.parentEntity.posX + vec3.xCoord * d1;
/* 256 */           entitylargefireball.posY = this.parentEntity.posY + (this.parentEntity.height / 2.0F) + 0.5D;
/* 257 */           entitylargefireball.posZ = this.parentEntity.posZ + vec3.zCoord * d1;
/* 258 */           world.spawnEntityInWorld((Entity)entitylargefireball);
/* 259 */           this.attackTimer = -40;
/*     */         }
/*     */       
/* 262 */       } else if (this.attackTimer > 0) {
/*     */         
/* 264 */         this.attackTimer--;
/*     */       } 
/*     */       
/* 267 */       this.parentEntity.setAttacking((this.attackTimer > 10));
/*     */     }
/*     */   }
/*     */   
/*     */   static class AILookAround
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     
/*     */     public AILookAround(EntityGhast ghast) {
/* 277 */       this.parentEntity = ghast;
/* 278 */       setMutexBits(2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 283 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 288 */       if (this.parentEntity.getAttackTarget() == null) {
/*     */         
/* 290 */         this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * 180.0F / 3.1415927F;
/*     */       }
/*     */       else {
/*     */         
/* 294 */         EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
/* 295 */         double d0 = 64.0D;
/*     */         
/* 297 */         if (entitylivingbase.getDistanceSqToEntity((Entity)this.parentEntity) < d0 * d0) {
/*     */           
/* 299 */           double d1 = entitylivingbase.posX - this.parentEntity.posX;
/* 300 */           double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
/* 301 */           this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * 180.0F / 3.1415927F;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIRandomFly
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     
/*     */     public AIRandomFly(EntityGhast ghast) {
/* 313 */       this.parentEntity = ghast;
/* 314 */       setMutexBits(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 319 */       EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();
/*     */       
/* 321 */       if (!entitymovehelper.isUpdating())
/*     */       {
/* 323 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 327 */       double d0 = entitymovehelper.getX() - this.parentEntity.posX;
/* 328 */       double d1 = entitymovehelper.getY() - this.parentEntity.posY;
/* 329 */       double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
/* 330 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 331 */       return (d3 < 1.0D || d3 > 3600.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 337 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 342 */       Random random = this.parentEntity.getRNG();
/* 343 */       double d0 = this.parentEntity.posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 344 */       double d1 = this.parentEntity.posY + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 345 */       double d2 = this.parentEntity.posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 346 */       this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class GhastMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     private int courseChangeCooldown;
/*     */     
/*     */     public GhastMoveHelper(EntityGhast ghast) {
/* 357 */       super((EntityLiving)ghast);
/* 358 */       this.parentEntity = ghast;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 363 */       if (this.update) {
/*     */         
/* 365 */         double d0 = this.posX - this.parentEntity.posX;
/* 366 */         double d1 = this.posY - this.parentEntity.posY;
/* 367 */         double d2 = this.posZ - this.parentEntity.posZ;
/* 368 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */         
/* 370 */         if (this.courseChangeCooldown-- <= 0) {
/*     */           
/* 372 */           this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
/* 373 */           d3 = MathHelper.sqrt_double(d3);
/*     */           
/* 375 */           if (isNotColliding(this.posX, this.posY, this.posZ, d3)) {
/*     */             
/* 377 */             this.parentEntity.motionX += d0 / d3 * 0.1D;
/* 378 */             this.parentEntity.motionY += d1 / d3 * 0.1D;
/* 379 */             this.parentEntity.motionZ += d2 / d3 * 0.1D;
/*     */           }
/*     */           else {
/*     */             
/* 383 */             this.update = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
/* 391 */       double d0 = (x - this.parentEntity.posX) / p_179926_7_;
/* 392 */       double d1 = (y - this.parentEntity.posY) / p_179926_7_;
/* 393 */       double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
/* 394 */       AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();
/*     */       
/* 396 */       for (int i = 1; i < p_179926_7_; i++) {
/*     */         
/* 398 */         axisalignedbb = axisalignedbb.offset(d0, d1, d2);
/*     */         
/* 400 */         if (!this.parentEntity.worldObj.getCollidingBoundingBoxes((Entity)this.parentEntity, axisalignedbb).isEmpty())
/*     */         {
/* 402 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 406 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */