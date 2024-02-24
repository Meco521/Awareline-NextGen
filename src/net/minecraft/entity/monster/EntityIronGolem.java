/*     */ package net.minecraft.entity.monster;
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityIronGolem extends EntityGolem {
/*     */   private int homeCheckTimer;
/*     */   Village villageObj;
/*     */   
/*     */   public EntityIronGolem(World worldIn) {
/*  33 */     super(worldIn);
/*  34 */     setSize(1.4F, 2.9F);
/*  35 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  36 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackOnCollide(this, 1.0D, true));
/*  37 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
/*  38 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveThroughVillage(this, 0.6D, true));
/*  39 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  40 */     this.tasks.addTask(5, (EntityAIBase)new EntityAILookAtVillager(this));
/*  41 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander(this, 0.6D));
/*  42 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  43 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  44 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIDefendVillage(this));
/*  45 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  46 */     this.targetTasks.addTask(3, (EntityAIBase)new AINearestAttackableTargetNonCreeper(this, EntityLiving.class, 10, false, true, IMob.VISIBLE_MOB_SELECTOR));
/*     */   }
/*     */   private int attackTimer; private int holdRoseTick;
/*     */   
/*     */   protected void entityInit() {
/*  51 */     super.entityInit();
/*  52 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  57 */     if (--this.homeCheckTimer <= 0) {
/*     */       
/*  59 */       this.homeCheckTimer = 70 + this.rand.nextInt(50);
/*  60 */       this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos((Entity)this), 32);
/*     */       
/*  62 */       if (this.villageObj == null) {
/*     */         
/*  64 */         detachHome();
/*     */       }
/*     */       else {
/*     */         
/*  68 */         BlockPos blockpos = this.villageObj.getCenter();
/*  69 */         setHomePosAndDistance(blockpos, (int)(this.villageObj.getVillageRadius() * 0.6F));
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  78 */     super.applyEntityAttributes();
/*  79 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
/*  80 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int decreaseAirSupply(int p_70682_1_) {
/*  88 */     return p_70682_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {
/*  93 */     if (entityIn instanceof IMob && !(entityIn instanceof EntityCreeper) && getRNG().nextInt(20) == 0)
/*     */     {
/*  95 */       setAttackTarget((EntityLivingBase)entityIn);
/*     */     }
/*     */     
/*  98 */     super.collideWithEntity(entityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 107 */     super.onLivingUpdate();
/*     */     
/* 109 */     if (this.attackTimer > 0)
/*     */     {
/* 111 */       this.attackTimer--;
/*     */     }
/*     */     
/* 114 */     if (this.holdRoseTick > 0)
/*     */     {
/* 116 */       this.holdRoseTick--;
/*     */     }
/*     */     
/* 119 */     if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0) {
/*     */       
/* 121 */       int i = MathHelper.floor_double(this.posX);
/* 122 */       int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/* 123 */       int k = MathHelper.floor_double(this.posZ);
/* 124 */       IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
/* 125 */       Block block = iblockstate.getBlock();
/*     */       
/* 127 */       if (block.getMaterial() != Material.air)
/*     */       {
/* 129 */         this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
/* 139 */     return (isPlayerCreated() && EntityPlayer.class.isAssignableFrom(cls)) ? false : ((cls == EntityCreeper.class) ? false : super.canAttackClass(cls));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 147 */     super.writeEntityToNBT(tagCompound);
/* 148 */     tagCompound.setBoolean("PlayerCreated", isPlayerCreated());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 156 */     super.readEntityFromNBT(tagCompund);
/* 157 */     setPlayerCreated(tagCompund.getBoolean("PlayerCreated"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 162 */     this.attackTimer = 10;
/* 163 */     this.worldObj.setEntityState((Entity)this, (byte)4);
/* 164 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (7 + this.rand.nextInt(15)));
/*     */     
/* 166 */     if (flag) {
/*     */       
/* 168 */       entityIn.motionY += 0.4000000059604645D;
/* 169 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     } 
/*     */     
/* 172 */     playSound("mob.irongolem.throw", 1.0F, 1.0F);
/* 173 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 178 */     if (id == 4) {
/*     */       
/* 180 */       this.attackTimer = 10;
/* 181 */       playSound("mob.irongolem.throw", 1.0F, 1.0F);
/*     */     }
/* 183 */     else if (id == 11) {
/*     */       
/* 185 */       this.holdRoseTick = 400;
/*     */     }
/*     */     else {
/*     */       
/* 189 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Village getVillage() {
/* 195 */     return this.villageObj;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAttackTimer() {
/* 200 */     return this.attackTimer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHoldingRose(boolean p_70851_1_) {
/* 205 */     this.holdRoseTick = p_70851_1_ ? 400 : 0;
/* 206 */     this.worldObj.setEntityState((Entity)this, (byte)11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 214 */     return "mob.irongolem.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 222 */     return "mob.irongolem.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 227 */     playSound("mob.irongolem.walk", 1.0F, 1.0F);
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
/* 239 */     int i = this.rand.nextInt(3);
/*     */     
/* 241 */     for (int j = 0; j < i; j++)
/*     */     {
/* 243 */       dropItemWithOffset(Item.getItemFromBlock((Block)Blocks.red_flower), 1, BlockFlower.EnumFlowerType.POPPY.getMeta());
/*     */     }
/*     */     
/* 246 */     int l = 3 + this.rand.nextInt(3);
/*     */     
/* 248 */     for (int k = 0; k < l; k++)
/*     */     {
/* 250 */       dropItem(Items.iron_ingot, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHoldRoseTick() {
/* 256 */     return this.holdRoseTick;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerCreated() {
/* 261 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerCreated(boolean p_70849_1_) {
/* 266 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 268 */     if (p_70849_1_) {
/*     */       
/* 270 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 274 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 283 */     if (!isPlayerCreated() && this.attackingPlayer != null && this.villageObj != null)
/*     */     {
/* 285 */       this.villageObj.setReputationForPlayer(this.attackingPlayer.getName(), -5);
/*     */     }
/*     */     
/* 288 */     super.onDeath(cause);
/*     */   }
/*     */   
/*     */   static class AINearestAttackableTargetNonCreeper<T extends EntityLivingBase>
/*     */     extends EntityAINearestAttackableTarget<T>
/*     */   {
/*     */     public AINearestAttackableTargetNonCreeper(final EntityCreature creature, Class<T> classTarget, int chance, boolean p_i45858_4_, boolean p_i45858_5_, final Predicate<? super T> p_i45858_6_) {
/* 295 */       super(creature, classTarget, chance, p_i45858_4_, p_i45858_5_, p_i45858_6_);
/* 296 */       this.targetEntitySelector = new Predicate<T>()
/*     */         {
/*     */           public boolean apply(T p_apply_1_)
/*     */           {
/* 300 */             if (p_i45858_6_ != null && !p_i45858_6_.apply(p_apply_1_))
/*     */             {
/* 302 */               return false;
/*     */             }
/* 304 */             if (p_apply_1_ instanceof EntityCreeper)
/*     */             {
/* 306 */               return false;
/*     */             }
/*     */ 
/*     */             
/* 310 */             if (p_apply_1_ instanceof EntityPlayer) {
/*     */               
/* 312 */               double d0 = EntityIronGolem.AINearestAttackableTargetNonCreeper.this.getTargetDistance();
/*     */               
/* 314 */               if (p_apply_1_.isSneaking())
/*     */               {
/* 316 */                 d0 *= 0.800000011920929D;
/*     */               }
/*     */               
/* 319 */               if (p_apply_1_.isInvisible()) {
/*     */                 
/* 321 */                 float f = ((EntityPlayer)p_apply_1_).getArmorVisibility();
/*     */                 
/* 323 */                 if (f < 0.1F)
/*     */                 {
/* 325 */                   f = 0.1F;
/*     */                 }
/*     */                 
/* 328 */                 d0 *= (0.7F * f);
/*     */               } 
/*     */               
/* 331 */               if (p_apply_1_.getDistanceToEntity((Entity)creature) > d0)
/*     */               {
/* 333 */                 return false;
/*     */               }
/*     */             } 
/*     */             
/* 337 */             return EntityIronGolem.AINearestAttackableTargetNonCreeper.this.isSuitableTarget((EntityLivingBase)p_apply_1_, false);
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */