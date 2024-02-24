/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityRabbit extends EntityAnimal {
/*  23 */   private int field_175540_bm = 0; private final AIAvoidEntity<EntityWolf> aiAvoidWolves;
/*  24 */   private int field_175535_bn = 0;
/*     */   private boolean field_175536_bo = false;
/*     */   private boolean field_175537_bp = false;
/*  27 */   private int currentMoveTypeDuration = 0;
/*  28 */   private EnumMoveType moveType = EnumMoveType.HOP;
/*  29 */   private int carrotTicks = 0;
/*  30 */   private final EntityPlayer field_175543_bt = null;
/*     */ 
/*     */   
/*     */   public EntityRabbit(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     setSize(0.6F, 0.7F);
/*  36 */     this.jumpHelper = new RabbitJumpHelper(this);
/*  37 */     this.moveHelper = new RabbitMoveHelper(this);
/*  38 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  39 */     this.navigator.setHeightRequirement(2.5F);
/*  40 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  41 */     this.tasks.addTask(1, (EntityAIBase)new AIPanic(this, 1.33D));
/*  42 */     this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.carrot, false));
/*  43 */     this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.golden_carrot, false));
/*  44 */     this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Item.getItemFromBlock((Block)Blocks.yellow_flower), false));
/*  45 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMate(this, 0.8D));
/*  46 */     this.tasks.addTask(5, (EntityAIBase)new AIRaidFarm(this));
/*  47 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D));
/*  48 */     this.tasks.addTask(11, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 10.0F));
/*  49 */     this.aiAvoidWolves = new AIAvoidEntity<>(this, EntityWolf.class, 16.0F, 1.33D, 1.33D);
/*  50 */     this.tasks.addTask(4, (EntityAIBase)this.aiAvoidWolves);
/*  51 */     setMovementSpeed(0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getJumpUpwardsMotion() {
/*  56 */     return (this.moveHelper.isUpdating() && this.moveHelper.getY() > this.posY + 0.5D) ? 0.5F : this.moveType.func_180074_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMoveType(EnumMoveType type) {
/*  61 */     this.moveType = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175521_o(float p_175521_1_) {
/*  66 */     return (this.field_175535_bn == 0) ? 0.0F : ((this.field_175540_bm + p_175521_1_) / this.field_175535_bn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMovementSpeed(double newSpeed) {
/*  71 */     getNavigator().setSpeed(newSpeed);
/*  72 */     this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJumping(boolean jump, EnumMoveType moveTypeIn) {
/*  77 */     setJumping(jump);
/*     */     
/*  79 */     if (!jump) {
/*     */       
/*  81 */       if (this.moveType == EnumMoveType.ATTACK)
/*     */       {
/*  83 */         this.moveType = EnumMoveType.HOP;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  88 */       setMovementSpeed(1.5D * moveTypeIn.getSpeed());
/*  89 */       playSound(getJumpingSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */     } 
/*     */     
/*  92 */     this.field_175536_bo = jump;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doMovementAction(EnumMoveType movetype) {
/*  97 */     setJumping(true, movetype);
/*  98 */     this.field_175535_bn = movetype.func_180073_d();
/*  99 */     this.field_175540_bm = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175523_cj() {
/* 104 */     return this.field_175536_bo;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 109 */     super.entityInit();
/* 110 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAITasks() {
/* 115 */     if (this.moveHelper.getSpeed() > 0.8D) {
/*     */       
/* 117 */       this.moveType = EnumMoveType.SPRINT;
/*     */     }
/* 119 */     else if (this.moveType != EnumMoveType.ATTACK) {
/*     */       
/* 121 */       this.moveType = EnumMoveType.HOP;
/*     */     } 
/*     */     
/* 124 */     if (this.currentMoveTypeDuration > 0)
/*     */     {
/* 126 */       this.currentMoveTypeDuration--;
/*     */     }
/*     */     
/* 129 */     if (this.carrotTicks > 0) {
/*     */       
/* 131 */       this.carrotTicks -= this.rand.nextInt(3);
/*     */       
/* 133 */       if (this.carrotTicks < 0)
/*     */       {
/* 135 */         this.carrotTicks = 0;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     if (this.onGround) {
/*     */       
/* 141 */       if (!this.field_175537_bp) {
/*     */         
/* 143 */         setJumping(false, EnumMoveType.NONE);
/* 144 */         func_175517_cu();
/*     */       } 
/*     */       
/* 147 */       if (getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
/*     */         
/* 149 */         EntityLivingBase entitylivingbase = getAttackTarget();
/*     */         
/* 151 */         if (entitylivingbase != null && getDistanceSqToEntity((Entity)entitylivingbase) < 16.0D) {
/*     */           
/* 153 */           calculateRotationYaw(entitylivingbase.posX, entitylivingbase.posZ);
/* 154 */           this.moveHelper.setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, this.moveHelper.getSpeed());
/* 155 */           doMovementAction(EnumMoveType.ATTACK);
/* 156 */           this.field_175537_bp = true;
/*     */         } 
/*     */       } 
/*     */       
/* 160 */       RabbitJumpHelper entityrabbit$rabbitjumphelper = (RabbitJumpHelper)this.jumpHelper;
/*     */       
/* 162 */       if (!entityrabbit$rabbitjumphelper.getIsJumping()) {
/*     */         
/* 164 */         if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0)
/*     */         {
/* 166 */           PathEntity pathentity = this.navigator.getPath();
/* 167 */           Vec3 vec3 = new Vec3(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
/*     */           
/* 169 */           if (pathentity != null && pathentity.getCurrentPathIndex() < pathentity.getCurrentPathLength())
/*     */           {
/* 171 */             vec3 = pathentity.getPosition((Entity)this);
/*     */           }
/*     */           
/* 174 */           calculateRotationYaw(vec3.xCoord, vec3.zCoord);
/* 175 */           doMovementAction(this.moveType);
/*     */         }
/*     */       
/* 178 */       } else if (!entityrabbit$rabbitjumphelper.func_180065_d()) {
/*     */         
/* 180 */         func_175518_cr();
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     this.field_175537_bp = this.onGround;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnRunningParticles() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateRotationYaw(double x, double z) {
/* 196 */     this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * 180.0D / Math.PI) - 90.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175518_cr() {
/* 201 */     ((RabbitJumpHelper)this.jumpHelper).func_180066_a(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175520_cs() {
/* 206 */     ((RabbitJumpHelper)this.jumpHelper).func_180066_a(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMoveTypeDuration() {
/* 211 */     this.currentMoveTypeDuration = getMoveTypeDuration();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175517_cu() {
/* 216 */     updateMoveTypeDuration();
/* 217 */     func_175520_cs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 226 */     super.onLivingUpdate();
/*     */     
/* 228 */     if (this.field_175540_bm != this.field_175535_bn) {
/*     */       
/* 230 */       if (this.field_175540_bm == 0 && !this.worldObj.isRemote)
/*     */       {
/* 232 */         this.worldObj.setEntityState((Entity)this, (byte)1);
/*     */       }
/*     */       
/* 235 */       this.field_175540_bm++;
/*     */     }
/* 237 */     else if (this.field_175535_bn != 0) {
/*     */       
/* 239 */       this.field_175540_bm = 0;
/* 240 */       this.field_175535_bn = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 246 */     super.applyEntityAttributes();
/* 247 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/* 248 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 256 */     super.writeEntityToNBT(tagCompound);
/* 257 */     tagCompound.setInteger("RabbitType", getRabbitType());
/* 258 */     tagCompound.setInteger("MoreCarrotTicks", this.carrotTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 266 */     super.readEntityFromNBT(tagCompund);
/* 267 */     setRabbitType(tagCompund.getInteger("RabbitType"));
/* 268 */     this.carrotTicks = tagCompund.getInteger("MoreCarrotTicks");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getJumpingSound() {
/* 273 */     return "mob.rabbit.hop";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 281 */     return "mob.rabbit.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 289 */     return "mob.rabbit.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 297 */     return "mob.rabbit.death";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 302 */     if (getRabbitType() == 99) {
/*     */       
/* 304 */       playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 305 */       return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 8.0F);
/*     */     } 
/*     */ 
/*     */     
/* 309 */     return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 318 */     return (getRabbitType() == 99) ? 8 : super.getTotalArmorValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 326 */     return isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 334 */     entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0F);
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
/* 346 */     int i = this.rand.nextInt(2) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 348 */     for (int j = 0; j < i; j++)
/*     */     {
/* 350 */       dropItem(Items.rabbit_hide, 1);
/*     */     }
/*     */     
/* 353 */     i = this.rand.nextInt(2);
/*     */     
/* 355 */     for (int k = 0; k < i; k++) {
/*     */       
/* 357 */       if (isBurning()) {
/*     */         
/* 359 */         dropItem(Items.cooked_rabbit, 1);
/*     */       }
/*     */       else {
/*     */         
/* 363 */         dropItem(Items.rabbit, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isRabbitBreedingItem(Item itemIn) {
/* 370 */     return (itemIn == Items.carrot || itemIn == Items.golden_carrot || itemIn == Item.getItemFromBlock((Block)Blocks.yellow_flower));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityRabbit createChild(EntityAgeable ageable) {
/* 375 */     EntityRabbit entityrabbit = new EntityRabbit(this.worldObj);
/*     */     
/* 377 */     if (ageable instanceof EntityRabbit)
/*     */     {
/* 379 */       entityrabbit.setRabbitType(this.rand.nextBoolean() ? getRabbitType() : ((EntityRabbit)ageable).getRabbitType());
/*     */     }
/*     */     
/* 382 */     return entityrabbit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 391 */     return (stack != null && isRabbitBreedingItem(stack.getItem()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRabbitType() {
/* 396 */     return this.dataWatcher.getWatchableObjectByte(18);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRabbitType(int rabbitTypeId) {
/* 401 */     if (rabbitTypeId == 99) {
/*     */       
/* 403 */       this.tasks.removeTask((EntityAIBase)this.aiAvoidWolves);
/* 404 */       this.tasks.addTask(4, (EntityAIBase)new AIEvilAttack(this));
/* 405 */       this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
/* 406 */       this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
/* 407 */       this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityWolf.class, true));
/*     */       
/* 409 */       if (!hasCustomName())
/*     */       {
/* 411 */         setCustomNameTag(StatCollector.translateToLocal("entity.KillerBunny.name"));
/*     */       }
/*     */     } 
/*     */     
/* 415 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)rabbitTypeId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 424 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 425 */     int i = this.rand.nextInt(6);
/* 426 */     boolean flag = false;
/*     */     
/* 428 */     if (livingdata instanceof RabbitTypeData) {
/*     */       
/* 430 */       i = ((RabbitTypeData)livingdata).typeData;
/* 431 */       flag = true;
/*     */     }
/*     */     else {
/*     */       
/* 435 */       livingdata = new RabbitTypeData(i);
/*     */     } 
/*     */     
/* 438 */     setRabbitType(i);
/*     */     
/* 440 */     if (flag)
/*     */     {
/* 442 */       setGrowingAge(-24000);
/*     */     }
/*     */     
/* 445 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCarrotEaten() {
/* 453 */     return (this.carrotTicks == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMoveTypeDuration() {
/* 461 */     return this.moveType.getDuration();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createEatingParticles() {
/* 466 */     this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, new int[] { Block.getStateId(Blocks.carrots.getStateFromMeta(7)) });
/* 467 */     this.carrotTicks = 100;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 472 */     if (id == 1) {
/*     */       
/* 474 */       createRunningParticles();
/* 475 */       this.field_175535_bn = 10;
/* 476 */       this.field_175540_bm = 0;
/*     */     }
/*     */     else {
/*     */       
/* 480 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AIAvoidEntity<T extends Entity>
/*     */     extends EntityAIAvoidEntity<T>
/*     */   {
/*     */     private final EntityRabbit entityInstance;
/*     */     
/*     */     public AIAvoidEntity(EntityRabbit rabbit, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_) {
/* 490 */       super((EntityCreature)rabbit, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
/* 491 */       this.entityInstance = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 496 */       super.updateTask();
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIEvilAttack
/*     */     extends EntityAIAttackOnCollide
/*     */   {
/*     */     public AIEvilAttack(EntityRabbit rabbit) {
/* 504 */       super((EntityCreature)rabbit, EntityLivingBase.class, 1.4D, true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected double func_179512_a(EntityLivingBase attackTarget) {
/* 509 */       return (4.0F + attackTarget.width);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIPanic
/*     */     extends EntityAIPanic
/*     */   {
/*     */     private final EntityRabbit theEntity;
/*     */     
/*     */     public AIPanic(EntityRabbit rabbit, double speedIn) {
/* 519 */       super((EntityCreature)rabbit, speedIn);
/* 520 */       this.theEntity = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 525 */       super.updateTask();
/* 526 */       this.theEntity.setMovementSpeed(this.speed);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIRaidFarm
/*     */     extends EntityAIMoveToBlock
/*     */   {
/*     */     private final EntityRabbit rabbit;
/*     */     private boolean field_179498_d;
/*     */     private boolean field_179499_e = false;
/*     */     
/*     */     public AIRaidFarm(EntityRabbit rabbitIn) {
/* 538 */       super((EntityCreature)rabbitIn, 0.699999988079071D, 16);
/* 539 */       this.rabbit = rabbitIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 544 */       if (this.runDelay <= 0) {
/*     */         
/* 546 */         if (!this.rabbit.worldObj.getGameRules().getBoolean("mobGriefing"))
/*     */         {
/* 548 */           return false;
/*     */         }
/*     */         
/* 551 */         this.field_179499_e = false;
/* 552 */         this.field_179498_d = this.rabbit.isCarrotEaten();
/*     */       } 
/*     */       
/* 555 */       return super.shouldExecute();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 560 */       return (this.field_179499_e && super.continueExecuting());
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 565 */       super.startExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 570 */       super.resetTask();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 575 */       super.updateTask();
/* 576 */       this.rabbit.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, 10.0F, this.rabbit.getVerticalFaceSpeed());
/*     */       
/* 578 */       if (getIsAboveDestination()) {
/*     */         
/* 580 */         World world = this.rabbit.worldObj;
/* 581 */         BlockPos blockpos = this.destinationBlock.up();
/* 582 */         IBlockState iblockstate = world.getBlockState(blockpos);
/* 583 */         Block block = iblockstate.getBlock();
/*     */         
/* 585 */         if (this.field_179499_e && block instanceof BlockCarrot && ((Integer)iblockstate.getValue((IProperty)BlockCarrot.AGE)).intValue() == 7) {
/*     */           
/* 587 */           world.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/* 588 */           world.destroyBlock(blockpos, true);
/* 589 */           this.rabbit.createEatingParticles();
/*     */         } 
/*     */         
/* 592 */         this.field_179499_e = false;
/* 593 */         this.runDelay = 10;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/* 599 */       Block block = worldIn.getBlockState(pos).getBlock();
/*     */       
/* 601 */       if (block == Blocks.farmland) {
/*     */         
/* 603 */         pos = pos.up();
/* 604 */         IBlockState iblockstate = worldIn.getBlockState(pos);
/* 605 */         block = iblockstate.getBlock();
/*     */         
/* 607 */         if (block instanceof BlockCarrot && ((Integer)iblockstate.getValue((IProperty)BlockCarrot.AGE)).intValue() == 7 && this.field_179498_d && !this.field_179499_e) {
/*     */           
/* 609 */           this.field_179499_e = true;
/* 610 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 614 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   enum EnumMoveType
/*     */   {
/* 620 */     NONE(0.0F, 0.0F, 30, 1),
/* 621 */     HOP(0.8F, 0.2F, 20, 10),
/* 622 */     STEP(1.0F, 0.45F, 14, 14),
/* 623 */     SPRINT(1.75F, 0.4F, 1, 8),
/* 624 */     ATTACK(2.0F, 0.7F, 7, 8);
/*     */     
/*     */     private final float speed;
/*     */     
/*     */     private final float field_180077_g;
/*     */     private final int duration;
/*     */     private final int field_180085_i;
/*     */     
/*     */     EnumMoveType(float typeSpeed, float p_i45866_4_, int typeDuration, int p_i45866_6_) {
/* 633 */       this.speed = typeSpeed;
/* 634 */       this.field_180077_g = p_i45866_4_;
/* 635 */       this.duration = typeDuration;
/* 636 */       this.field_180085_i = p_i45866_6_;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getSpeed() {
/* 641 */       return this.speed;
/*     */     }
/*     */ 
/*     */     
/*     */     public float func_180074_b() {
/* 646 */       return this.field_180077_g;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDuration() {
/* 651 */       return this.duration;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_180073_d() {
/* 656 */       return this.field_180085_i;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RabbitJumpHelper
/*     */     extends EntityJumpHelper
/*     */   {
/*     */     private final EntityRabbit theEntity;
/*     */     private boolean field_180068_d = false;
/*     */     
/*     */     public RabbitJumpHelper(EntityRabbit rabbit) {
/* 667 */       super((EntityLiving)rabbit);
/* 668 */       this.theEntity = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getIsJumping() {
/* 673 */       return this.isJumping;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_180065_d() {
/* 678 */       return this.field_180068_d;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_180066_a(boolean p_180066_1_) {
/* 683 */       this.field_180068_d = p_180066_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void doJump() {
/* 688 */       if (this.isJumping) {
/*     */         
/* 690 */         this.theEntity.doMovementAction(EntityRabbit.EnumMoveType.STEP);
/* 691 */         this.isJumping = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class RabbitMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private final EntityRabbit theEntity;
/*     */     
/*     */     public RabbitMoveHelper(EntityRabbit rabbit) {
/* 702 */       super((EntityLiving)rabbit);
/* 703 */       this.theEntity = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 708 */       if (this.theEntity.onGround && !this.theEntity.func_175523_cj())
/*     */       {
/* 710 */         this.theEntity.setMovementSpeed(0.0D);
/*     */       }
/*     */       
/* 713 */       super.onUpdateMoveHelper();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RabbitTypeData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public int typeData;
/*     */     
/*     */     public RabbitTypeData(int type) {
/* 723 */       this.typeData = type;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */