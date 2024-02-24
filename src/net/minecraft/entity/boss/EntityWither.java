/*     */ package net.minecraft.entity.boss;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob {
/*  28 */   private final float[] field_82220_d = new float[2];
/*  29 */   private final float[] field_82221_e = new float[2];
/*  30 */   private final float[] field_82217_f = new float[2];
/*  31 */   private final float[] field_82218_g = new float[2];
/*  32 */   private final int[] field_82223_h = new int[2];
/*  33 */   private final int[] field_82224_i = new int[2];
/*     */   
/*     */   private int blockBreakCounter;
/*     */   
/*  37 */   private static final Predicate<Entity> attackEntitySelector = new Predicate<Entity>()
/*     */     {
/*     */       public boolean apply(Entity p_apply_1_)
/*     */       {
/*  41 */         return (p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public EntityWither(World worldIn) {
/*  47 */     super(worldIn);
/*  48 */     setHealth(getMaxHealth());
/*  49 */     setSize(0.9F, 3.5F);
/*  50 */     this.isImmuneToFire = true;
/*  51 */     ((PathNavigateGround)getNavigator()).setCanSwim(true);
/*  52 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  53 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
/*  54 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  55 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  56 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  57 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
/*  58 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 0, false, false, attackEntitySelector));
/*  59 */     this.experienceValue = 50;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  64 */     super.entityInit();
/*  65 */     this.dataWatcher.addObject(17, new Integer(0));
/*  66 */     this.dataWatcher.addObject(18, new Integer(0));
/*  67 */     this.dataWatcher.addObject(19, new Integer(0));
/*  68 */     this.dataWatcher.addObject(20, new Integer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  76 */     super.writeEntityToNBT(tagCompound);
/*  77 */     tagCompound.setInteger("Invul", getInvulTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  85 */     super.readEntityFromNBT(tagCompund);
/*  86 */     setInvulTime(tagCompund.getInteger("Invul"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  94 */     return "mob.wither.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 102 */     return "mob.wither.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 110 */     return "mob.wither.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 119 */     this.motionY *= 0.6000000238418579D;
/*     */     
/* 121 */     if (!this.worldObj.isRemote && getWatchedTargetId(0) > 0) {
/*     */       
/* 123 */       Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
/*     */       
/* 125 */       if (entity != null) {
/*     */         
/* 127 */         if (this.posY < entity.posY || (!isArmored() && this.posY < entity.posY + 5.0D)) {
/*     */           
/* 129 */           if (this.motionY < 0.0D)
/*     */           {
/* 131 */             this.motionY = 0.0D;
/*     */           }
/*     */           
/* 134 */           this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
/*     */         } 
/*     */         
/* 137 */         double d0 = entity.posX - this.posX;
/* 138 */         double d1 = entity.posZ - this.posZ;
/* 139 */         double d3 = d0 * d0 + d1 * d1;
/*     */         
/* 141 */         if (d3 > 9.0D) {
/*     */           
/* 143 */           double d5 = MathHelper.sqrt_double(d3);
/* 144 */           this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
/* 145 */           this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D)
/*     */     {
/* 152 */       this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
/*     */     }
/*     */     
/* 155 */     super.onLivingUpdate();
/*     */     
/* 157 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 159 */       this.field_82218_g[i] = this.field_82221_e[i];
/* 160 */       this.field_82217_f[i] = this.field_82220_d[i];
/*     */     } 
/*     */     
/* 163 */     for (int j = 0; j < 2; j++) {
/*     */       
/* 165 */       int k = getWatchedTargetId(j + 1);
/* 166 */       Entity entity1 = null;
/*     */       
/* 168 */       if (k > 0)
/*     */       {
/* 170 */         entity1 = this.worldObj.getEntityByID(k);
/*     */       }
/*     */       
/* 173 */       if (entity1 != null) {
/*     */         
/* 175 */         double d11 = func_82214_u(j + 1);
/* 176 */         double d12 = func_82208_v(j + 1);
/* 177 */         double d13 = func_82213_w(j + 1);
/* 178 */         double d6 = entity1.posX - d11;
/* 179 */         double d7 = entity1.posY + entity1.getEyeHeight() - d12;
/* 180 */         double d8 = entity1.posZ - d13;
/* 181 */         double d9 = MathHelper.sqrt_double(d6 * d6 + d8 * d8);
/* 182 */         float f = (float)(MathHelper.atan2(d8, d6) * 180.0D / Math.PI) - 90.0F;
/* 183 */         float f1 = (float)-(MathHelper.atan2(d7, d9) * 180.0D / Math.PI);
/* 184 */         this.field_82220_d[j] = func_82204_b(this.field_82220_d[j], f1, 40.0F);
/* 185 */         this.field_82221_e[j] = func_82204_b(this.field_82221_e[j], f, 10.0F);
/*     */       }
/*     */       else {
/*     */         
/* 189 */         this.field_82221_e[j] = func_82204_b(this.field_82221_e[j], this.renderYawOffset, 10.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     boolean flag = isArmored();
/*     */     
/* 195 */     for (int l = 0; l < 3; l++) {
/*     */       
/* 197 */       double d10 = func_82214_u(l);
/* 198 */       double d2 = func_82208_v(l);
/* 199 */       double d4 = func_82213_w(l);
/* 200 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       
/* 202 */       if (flag && this.worldObj.rand.nextInt(4) == 0)
/*     */       {
/* 204 */         this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
/*     */       }
/*     */     } 
/*     */     
/* 208 */     if (getInvulTime() > 0)
/*     */     {
/* 210 */       for (int i1 = 0; i1 < 3; i1++)
/*     */       {
/* 212 */         this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian(), this.posY + (this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian(), 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 219 */     if (getInvulTime() > 0) {
/*     */       
/* 221 */       int j1 = getInvulTime() - 1;
/*     */       
/* 223 */       if (j1 <= 0) {
/*     */         
/* 225 */         this.worldObj.newExplosion((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getBoolean("mobGriefing"));
/* 226 */         this.worldObj.playBroadcastSound(1013, new BlockPos((Entity)this), 0);
/*     */       } 
/*     */       
/* 229 */       setInvulTime(j1);
/*     */       
/* 231 */       if (this.ticksExisted % 10 == 0)
/*     */       {
/* 233 */         heal(10.0F);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 238 */       super.updateAITasks();
/*     */       
/* 240 */       for (int i = 1; i < 3; i++) {
/*     */         
/* 242 */         if (this.ticksExisted >= this.field_82223_h[i - 1]) {
/*     */           
/* 244 */           this.field_82223_h[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
/*     */           
/* 246 */           if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/*     */             
/* 248 */             int j3 = i - 1;
/* 249 */             int k3 = this.field_82224_i[i - 1];
/* 250 */             this.field_82224_i[j3] = this.field_82224_i[i - 1] + 1;
/*     */             
/* 252 */             if (k3 > 15) {
/*     */               
/* 254 */               float f = 10.0F;
/* 255 */               float f1 = 5.0F;
/* 256 */               double d0 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
/* 257 */               double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
/* 258 */               double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
/* 259 */               launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
/* 260 */               this.field_82224_i[i - 1] = 0;
/*     */             } 
/*     */           } 
/*     */           
/* 264 */           int k1 = getWatchedTargetId(i);
/*     */           
/* 266 */           if (k1 > 0) {
/*     */             
/* 268 */             Entity entity = this.worldObj.getEntityByID(k1);
/*     */             
/* 270 */             if (entity != null && entity.isEntityAlive() && getDistanceSqToEntity(entity) <= 900.0D && canEntityBeSeen(entity)) {
/*     */               
/* 272 */               if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage)
/*     */               {
/* 274 */                 updateWatchedTargetId(i, 0);
/*     */               }
/*     */               else
/*     */               {
/* 278 */                 launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
/* 279 */                 this.field_82223_h[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
/* 280 */                 this.field_82224_i[i - 1] = 0;
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 285 */               updateWatchedTargetId(i, 0);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 290 */             List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.and(attackEntitySelector, EntitySelectors.NOT_SPECTATING));
/*     */             
/* 292 */             for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
/*     */               
/* 294 */               EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
/*     */               
/* 296 */               if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase)) {
/*     */                 
/* 298 */                 if (entitylivingbase instanceof EntityPlayer) {
/*     */                   
/* 300 */                   if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage)
/*     */                   {
/* 302 */                     updateWatchedTargetId(i, entitylivingbase.getEntityId());
/*     */                   }
/*     */                   
/*     */                   break;
/*     */                 } 
/* 307 */                 updateWatchedTargetId(i, entitylivingbase.getEntityId());
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 313 */               list.remove(entitylivingbase);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 319 */       if (getAttackTarget() != null) {
/*     */         
/* 321 */         updateWatchedTargetId(0, getAttackTarget().getEntityId());
/*     */       }
/*     */       else {
/*     */         
/* 325 */         updateWatchedTargetId(0, 0);
/*     */       } 
/*     */       
/* 328 */       if (this.blockBreakCounter > 0) {
/*     */         
/* 330 */         this.blockBreakCounter--;
/*     */         
/* 332 */         if (this.blockBreakCounter == 0 && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
/*     */           
/* 334 */           int i1 = MathHelper.floor_double(this.posY);
/* 335 */           int l1 = MathHelper.floor_double(this.posX);
/* 336 */           int i2 = MathHelper.floor_double(this.posZ);
/* 337 */           boolean flag = false;
/*     */           
/* 339 */           for (int k2 = -1; k2 <= 1; k2++) {
/*     */             
/* 341 */             for (int l2 = -1; l2 <= 1; l2++) {
/*     */               
/* 343 */               for (int j = 0; j <= 3; j++) {
/*     */                 
/* 345 */                 int i3 = l1 + k2;
/* 346 */                 int k = i1 + j;
/* 347 */                 int l = i2 + l2;
/* 348 */                 BlockPos blockpos = new BlockPos(i3, k, l);
/* 349 */                 Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */                 
/* 351 */                 if (block.getMaterial() != Material.air && canDestroyBlock(block))
/*     */                 {
/* 353 */                   flag = (this.worldObj.destroyBlock(blockpos, true) || flag);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 359 */           if (flag)
/*     */           {
/* 361 */             this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, new BlockPos((Entity)this), 0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 366 */       if (this.ticksExisted % 20 == 0)
/*     */       {
/* 368 */         heal(1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canDestroyBlock(Block p_181033_0_) {
/* 375 */     return (p_181033_0_ != Blocks.bedrock && p_181033_0_ != Blocks.end_portal && p_181033_0_ != Blocks.end_portal_frame && p_181033_0_ != Blocks.command_block && p_181033_0_ != Blocks.barrier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_82206_m() {
/* 380 */     setInvulTime(220);
/* 381 */     setHealth(getMaxHealth() / 3.0F);
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
/*     */   public int getTotalArmorValue() {
/* 396 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   private double func_82214_u(int p_82214_1_) {
/* 401 */     if (p_82214_1_ <= 0)
/*     */     {
/* 403 */       return this.posX;
/*     */     }
/*     */ 
/*     */     
/* 407 */     float f = (this.renderYawOffset + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
/* 408 */     float f1 = MathHelper.cos(f);
/* 409 */     return this.posX + f1 * 1.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double func_82208_v(int p_82208_1_) {
/* 415 */     return (p_82208_1_ <= 0) ? (this.posY + 3.0D) : (this.posY + 2.2D);
/*     */   }
/*     */ 
/*     */   
/*     */   private double func_82213_w(int p_82213_1_) {
/* 420 */     if (p_82213_1_ <= 0)
/*     */     {
/* 422 */       return this.posZ;
/*     */     }
/*     */ 
/*     */     
/* 426 */     float f = (this.renderYawOffset + (180 * (p_82213_1_ - 1))) / 180.0F * 3.1415927F;
/* 427 */     float f1 = MathHelper.sin(f);
/* 428 */     return this.posZ + f1 * 1.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
/* 434 */     float f = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
/*     */     
/* 436 */     if (f > p_82204_3_)
/*     */     {
/* 438 */       f = p_82204_3_;
/*     */     }
/*     */     
/* 441 */     if (f < -p_82204_3_)
/*     */     {
/* 443 */       f = -p_82204_3_;
/*     */     }
/*     */     
/* 446 */     return p_82204_1_ + f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
/* 451 */     launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0 && this.rand.nextFloat() < 0.001F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void launchWitherSkullToCoords(int p_82209_1_, double x, double y, double z, boolean invulnerable) {
/* 459 */     this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos((Entity)this), 0);
/* 460 */     double d0 = func_82214_u(p_82209_1_);
/* 461 */     double d1 = func_82208_v(p_82209_1_);
/* 462 */     double d2 = func_82213_w(p_82209_1_);
/* 463 */     double d3 = x - d0;
/* 464 */     double d4 = y - d1;
/* 465 */     double d5 = z - d2;
/* 466 */     EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.worldObj, (EntityLivingBase)this, d3, d4, d5);
/*     */     
/* 468 */     if (invulnerable)
/*     */     {
/* 470 */       entitywitherskull.setInvulnerable(true);
/*     */     }
/*     */     
/* 473 */     entitywitherskull.posY = d1;
/* 474 */     entitywitherskull.posX = d0;
/* 475 */     entitywitherskull.posZ = d2;
/* 476 */     this.worldObj.spawnEntityInWorld((Entity)entitywitherskull);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
/* 484 */     launchWitherSkullToEntity(0, target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 492 */     if (isEntityInvulnerable(source))
/*     */     {
/* 494 */       return false;
/*     */     }
/* 496 */     if (source != DamageSource.drown && !(source.getEntity() instanceof EntityWither)) {
/*     */       
/* 498 */       if (getInvulTime() > 0 && source != DamageSource.outOfWorld)
/*     */       {
/* 500 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 504 */       if (isArmored()) {
/*     */         
/* 506 */         Entity entity = source.getSourceOfDamage();
/*     */         
/* 508 */         if (entity instanceof net.minecraft.entity.projectile.EntityArrow)
/*     */         {
/* 510 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 514 */       Entity entity1 = source.getEntity();
/*     */       
/* 516 */       if (entity1 != null && !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase && ((EntityLivingBase)entity1).getCreatureAttribute() == getCreatureAttribute())
/*     */       {
/* 518 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 522 */       if (this.blockBreakCounter <= 0)
/*     */       {
/* 524 */         this.blockBreakCounter = 20;
/*     */       }
/*     */       
/* 527 */       for (int i = 0; i < this.field_82224_i.length; i++)
/*     */       {
/* 529 */         this.field_82224_i[i] = this.field_82224_i[i] + 3;
/*     */       }
/*     */       
/* 532 */       return super.attackEntityFrom(source, amount);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 538 */     return false;
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
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 551 */     EntityItem entityitem = dropItem(Items.nether_star, 1);
/*     */     
/* 553 */     if (entityitem != null)
/*     */     {
/* 555 */       entityitem.setNoDespawn();
/*     */     }
/*     */     
/* 558 */     if (!this.worldObj.isRemote)
/*     */     {
/* 560 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)))
/*     */       {
/* 562 */         entityplayer.triggerAchievement((StatBase)AchievementList.killWither);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void despawnEntity() {
/* 572 */     this.entityAge = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 577 */     return 15728880;
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
/*     */   public void addPotionEffect(PotionEffect potioneffectIn) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 593 */     super.applyEntityAttributes();
/* 594 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
/* 595 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579D);
/* 596 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_82207_a(int p_82207_1_) {
/* 601 */     return this.field_82221_e[p_82207_1_];
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_82210_r(int p_82210_1_) {
/* 606 */     return this.field_82220_d[p_82210_1_];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInvulTime() {
/* 611 */     return this.dataWatcher.getWatchableObjectInt(20);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvulTime(int p_82215_1_) {
/* 616 */     this.dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWatchedTargetId(int p_82203_1_) {
/* 624 */     return this.dataWatcher.getWatchableObjectInt(17 + p_82203_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWatchedTargetId(int targetOffset, int newId) {
/* 632 */     this.dataWatcher.updateObject(17 + targetOffset, Integer.valueOf(newId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmored() {
/* 641 */     return (getHealth() <= getMaxHealth() / 2.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 649 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mountEntity(Entity entityIn) {
/* 657 */     this.ridingEntity = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\boss\EntityWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */