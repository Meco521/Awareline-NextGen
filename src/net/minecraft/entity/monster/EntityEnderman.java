/*     */ package net.minecraft.entity.monster;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityEnderman extends EntityMob {
/*  31 */   private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
/*  32 */   static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448D, 0)).setSaved(false);
/*  33 */   static final Set<Block> carriableBlocks = Sets.newIdentityHashSet();
/*     */   
/*     */   boolean isAggressive;
/*     */   
/*     */   public EntityEnderman(World worldIn) {
/*  38 */     super(worldIn);
/*  39 */     setSize(0.6F, 2.9F);
/*  40 */     this.stepHeight = 1.0F;
/*  41 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  42 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackOnCollide(this, 1.0D, false));
/*  43 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  44 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  45 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  46 */     this.tasks.addTask(10, new AIPlaceBlock(this));
/*  47 */     this.tasks.addTask(11, new AITakeBlock(this));
/*  48 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  49 */     this.targetTasks.addTask(2, (EntityAIBase)new AIFindPlayer(this));
/*  50 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate<EntityEndermite>()
/*     */           {
/*     */             public boolean apply(EntityEndermite p_apply_1_)
/*     */             {
/*  54 */               return p_apply_1_.isSpawnedByPlayer();
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  61 */     super.applyEntityAttributes();
/*  62 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
/*  63 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*  64 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
/*  65 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  70 */     super.entityInit();
/*  71 */     this.dataWatcher.addObject(16, new Short((short)0));
/*  72 */     this.dataWatcher.addObject(17, new Byte((byte)0));
/*  73 */     this.dataWatcher.addObject(18, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  81 */     super.writeEntityToNBT(tagCompound);
/*  82 */     IBlockState iblockstate = getHeldBlockState();
/*  83 */     tagCompound.setShort("carried", (short)Block.getIdFromBlock(iblockstate.getBlock()));
/*  84 */     tagCompound.setShort("carriedData", (short)iblockstate.getBlock().getMetaFromState(iblockstate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*     */     IBlockState iblockstate;
/*  92 */     super.readEntityFromNBT(tagCompund);
/*     */ 
/*     */     
/*  95 */     if (tagCompund.hasKey("carried", 8)) {
/*     */       
/*  97 */       iblockstate = Block.getBlockFromName(tagCompund.getString("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
/*     */     }
/*     */     else {
/*     */       
/* 101 */       iblockstate = Block.getBlockById(tagCompund.getShort("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
/*     */     } 
/*     */     
/* 104 */     setHeldBlockState(iblockstate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean shouldAttackPlayer(EntityPlayer player) {
/* 112 */     ItemStack itemstack = player.inventory.armorInventory[3];
/*     */     
/* 114 */     if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
/*     */     {
/* 116 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 120 */     Vec3 vec3 = player.getLook(1.0F).normalize();
/* 121 */     Vec3 vec31 = new Vec3(this.posX - player.posX, (getEntityBoundingBox()).minY + (this.height / 2.0F) - player.posY + player.getEyeHeight(), this.posZ - player.posZ);
/* 122 */     double d0 = vec31.lengthVector();
/* 123 */     vec31 = vec31.normalize();
/* 124 */     double d1 = vec3.dotProduct(vec31);
/* 125 */     return (d1 > 1.0D - 0.025D / d0) ? player.canEntityBeSeen((Entity)this) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 131 */     return 2.55F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 140 */     if (this.worldObj.isRemote)
/*     */     {
/* 142 */       for (int i = 0; i < 2; i++)
/*     */       {
/* 144 */         this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     
/* 148 */     this.isJumping = false;
/* 149 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 154 */     if (isWet())
/*     */     {
/* 156 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*     */     }
/*     */     
/* 159 */     if (isScreaming() && !this.isAggressive && this.rand.nextInt(100) == 0)
/*     */     {
/* 161 */       setScreaming(false);
/*     */     }
/*     */     
/* 164 */     if (this.worldObj.isDaytime()) {
/*     */       
/* 166 */       float f = getBrightness(1.0F);
/*     */       
/* 168 */       if (f > 0.5F && this.worldObj.canSeeSky(new BlockPos((Entity)this)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
/*     */         
/* 170 */         setAttackTarget((EntityLivingBase)null);
/* 171 */         setScreaming(false);
/* 172 */         this.isAggressive = false;
/* 173 */         teleportRandomly();
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportRandomly() {
/* 185 */     double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 186 */     double d1 = this.posY + (this.rand.nextInt(64) - 32);
/* 187 */     double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 188 */     return teleportTo(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportToEntity(Entity p_70816_1_) {
/* 196 */     Vec3 vec3 = new Vec3(this.posX - p_70816_1_.posX, (getEntityBoundingBox()).minY + (this.height / 2.0F) - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
/* 197 */     vec3 = vec3.normalize();
/* 198 */     double d0 = 16.0D;
/* 199 */     double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
/* 200 */     double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
/* 201 */     double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
/* 202 */     return teleportTo(d1, d2, d3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportTo(double x, double y, double z) {
/* 210 */     double d0 = this.posX;
/* 211 */     double d1 = this.posY;
/* 212 */     double d2 = this.posZ;
/* 213 */     this.posX = x;
/* 214 */     this.posY = y;
/* 215 */     this.posZ = z;
/* 216 */     boolean flag = false;
/* 217 */     BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
/*     */     
/* 219 */     if (this.worldObj.isBlockLoaded(blockpos)) {
/*     */       
/* 221 */       boolean flag1 = false;
/*     */       
/* 223 */       while (!flag1 && blockpos.getY() > 0) {
/*     */         
/* 225 */         BlockPos blockpos1 = blockpos.down();
/* 226 */         Block block = this.worldObj.getBlockState(blockpos1).getBlock();
/*     */         
/* 228 */         if (block.getMaterial().blocksMovement()) {
/*     */           
/* 230 */           flag1 = true;
/*     */           
/*     */           continue;
/*     */         } 
/* 234 */         this.posY--;
/* 235 */         blockpos = blockpos1;
/*     */       } 
/*     */ 
/*     */       
/* 239 */       if (flag1) {
/*     */         
/* 241 */         setPositionAndUpdate(this.posX, this.posY, this.posZ);
/*     */         
/* 243 */         if (this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()))
/*     */         {
/* 245 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     if (!flag) {
/*     */       
/* 252 */       setPosition(d0, d1, d2);
/* 253 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 257 */     int i = 128;
/*     */     
/* 259 */     for (int j = 0; j < i; j++) {
/*     */       
/* 261 */       double d6 = j / (i - 1.0D);
/* 262 */       float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 263 */       float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 264 */       float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 265 */       double d3 = d0 + (this.posX - d0) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 266 */       double d4 = d1 + (this.posY - d1) * d6 + this.rand.nextDouble() * this.height;
/* 267 */       double d5 = d2 + (this.posZ - d2) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 268 */       this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, f, f1, f2, new int[0]);
/*     */     } 
/*     */     
/* 271 */     this.worldObj.playSoundEffect(d0, d1, d2, "mob.endermen.portal", 1.0F, 1.0F);
/* 272 */     playSound("mob.endermen.portal", 1.0F, 1.0F);
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 282 */     return isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 290 */     return "mob.endermen.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 298 */     return "mob.endermen.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 303 */     return Items.ender_pearl;
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
/* 315 */     Item item = getDropItem();
/*     */     
/* 317 */     if (item != null) {
/*     */       
/* 319 */       int i = this.rand.nextInt(2 + lootingModifier);
/*     */       
/* 321 */       for (int j = 0; j < i; j++)
/*     */       {
/* 323 */         dropItem(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeldBlockState(IBlockState state) {
/* 333 */     this.dataWatcher.updateObject(16, Short.valueOf((short)(Block.getStateId(state) & 0xFFFF)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getHeldBlockState() {
/* 341 */     return Block.getStateById(this.dataWatcher.getWatchableObjectShort(16) & 0xFFFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 349 */     if (isEntityInvulnerable(source))
/*     */     {
/* 351 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 355 */     if (source.getEntity() == null || !(source.getEntity() instanceof EntityEndermite)) {
/*     */       
/* 357 */       if (!this.worldObj.isRemote)
/*     */       {
/* 359 */         setScreaming(true);
/*     */       }
/*     */       
/* 362 */       if (source instanceof net.minecraft.util.EntityDamageSource && source.getEntity() instanceof EntityPlayer)
/*     */       {
/* 364 */         if (source.getEntity() instanceof EntityPlayerMP && ((EntityPlayerMP)source.getEntity()).theItemInWorldManager.isCreative()) {
/*     */           
/* 366 */           setScreaming(false);
/*     */         }
/*     */         else {
/*     */           
/* 370 */           this.isAggressive = true;
/*     */         } 
/*     */       }
/*     */       
/* 374 */       if (source instanceof net.minecraft.util.EntityDamageSourceIndirect) {
/*     */         
/* 376 */         this.isAggressive = false;
/*     */         
/* 378 */         for (int i = 0; i < 64; i++) {
/*     */           
/* 380 */           if (teleportRandomly())
/*     */           {
/* 382 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 386 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 390 */     boolean flag = super.attackEntityFrom(source, amount);
/*     */     
/* 392 */     if (source.isUnblockable() && this.rand.nextInt(10) != 0)
/*     */     {
/* 394 */       teleportRandomly();
/*     */     }
/*     */     
/* 397 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScreaming() {
/* 403 */     return (this.dataWatcher.getWatchableObjectByte(18) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScreaming(boolean screaming) {
/* 408 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)(screaming ? 1 : 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 413 */     carriableBlocks.add(Blocks.grass);
/* 414 */     carriableBlocks.add(Blocks.dirt);
/* 415 */     carriableBlocks.add(Blocks.sand);
/* 416 */     carriableBlocks.add(Blocks.gravel);
/* 417 */     carriableBlocks.add(Blocks.yellow_flower);
/* 418 */     carriableBlocks.add(Blocks.red_flower);
/* 419 */     carriableBlocks.add(Blocks.brown_mushroom);
/* 420 */     carriableBlocks.add(Blocks.red_mushroom);
/* 421 */     carriableBlocks.add(Blocks.tnt);
/* 422 */     carriableBlocks.add(Blocks.cactus);
/* 423 */     carriableBlocks.add(Blocks.clay);
/* 424 */     carriableBlocks.add(Blocks.pumpkin);
/* 425 */     carriableBlocks.add(Blocks.melon_block);
/* 426 */     carriableBlocks.add(Blocks.mycelium);
/*     */   }
/*     */   
/*     */   static class AIFindPlayer
/*     */     extends EntityAINearestAttackableTarget
/*     */   {
/*     */     private EntityPlayer player;
/*     */     private int field_179450_h;
/*     */     private int field_179451_i;
/*     */     private final EntityEnderman enderman;
/*     */     
/*     */     public AIFindPlayer(EntityEnderman p_i45842_1_) {
/* 438 */       super(p_i45842_1_, EntityPlayer.class, true);
/* 439 */       this.enderman = p_i45842_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 444 */       double d0 = getTargetDistance();
/* 445 */       List<EntityPlayer> list = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), this.targetEntitySelector);
/* 446 */       list.sort((Comparator<? super EntityPlayer>)this.theNearestAttackableTargetSorter);
/*     */       
/* 448 */       if (list.isEmpty())
/*     */       {
/* 450 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 454 */       this.player = list.get(0);
/* 455 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 461 */       this.field_179450_h = 5;
/* 462 */       this.field_179451_i = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 467 */       this.player = null;
/* 468 */       this.enderman.setScreaming(false);
/* 469 */       IAttributeInstance iattributeinstance = this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 470 */       iattributeinstance.removeModifier(EntityEnderman.attackingSpeedBoostModifier);
/* 471 */       super.resetTask();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 476 */       if (this.player != null) {
/*     */         
/* 478 */         if (!this.enderman.shouldAttackPlayer(this.player))
/*     */         {
/* 480 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 484 */         this.enderman.isAggressive = true;
/* 485 */         this.enderman.faceEntity((Entity)this.player, 10.0F, 10.0F);
/* 486 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 491 */       return super.continueExecuting();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 497 */       if (this.player != null) {
/*     */         
/* 499 */         if (--this.field_179450_h <= 0)
/*     */         {
/* 501 */           this.targetEntity = (EntityLivingBase)this.player;
/* 502 */           this.player = null;
/* 503 */           super.startExecuting();
/* 504 */           this.enderman.playSound("mob.endermen.stare", 1.0F, 1.0F);
/* 505 */           this.enderman.setScreaming(true);
/* 506 */           IAttributeInstance iattributeinstance = this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 507 */           iattributeinstance.applyModifier(EntityEnderman.attackingSpeedBoostModifier);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 512 */         if (this.targetEntity != null)
/*     */         {
/* 514 */           if (this.targetEntity instanceof EntityPlayer && this.enderman.shouldAttackPlayer((EntityPlayer)this.targetEntity)) {
/*     */             
/* 516 */             if (this.targetEntity.getDistanceSqToEntity((Entity)this.enderman) < 16.0D)
/*     */             {
/* 518 */               this.enderman.teleportRandomly();
/*     */             }
/*     */             
/* 521 */             this.field_179451_i = 0;
/*     */           }
/* 523 */           else if (this.targetEntity.getDistanceSqToEntity((Entity)this.enderman) > 256.0D && this.field_179451_i++ >= 30 && this.enderman.teleportToEntity((Entity)this.targetEntity)) {
/*     */             
/* 525 */             this.field_179451_i = 0;
/*     */           } 
/*     */         }
/*     */         
/* 529 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIPlaceBlock
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityEnderman enderman;
/*     */     
/*     */     public AIPlaceBlock(EntityEnderman p_i45843_1_) {
/* 540 */       this.enderman = p_i45843_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 545 */       return !this.enderman.worldObj.getGameRules().getBoolean("mobGriefing") ? false : ((this.enderman.getHeldBlockState().getBlock().getMaterial() == Material.air) ? false : ((this.enderman.getRNG().nextInt(2000) == 0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 550 */       Random random = this.enderman.getRNG();
/* 551 */       World world = this.enderman.worldObj;
/* 552 */       int i = MathHelper.floor_double(this.enderman.posX - 1.0D + random.nextDouble() * 2.0D);
/* 553 */       int j = MathHelper.floor_double(this.enderman.posY + random.nextDouble() * 2.0D);
/* 554 */       int k = MathHelper.floor_double(this.enderman.posZ - 1.0D + random.nextDouble() * 2.0D);
/* 555 */       BlockPos blockpos = new BlockPos(i, j, k);
/* 556 */       Block block = world.getBlockState(blockpos).getBlock();
/* 557 */       Block block1 = world.getBlockState(blockpos.down()).getBlock();
/*     */       
/* 559 */       if (func_179474_a(world, blockpos, this.enderman.getHeldBlockState().getBlock(), block, block1)) {
/*     */         
/* 561 */         world.setBlockState(blockpos, this.enderman.getHeldBlockState(), 3);
/* 562 */         this.enderman.setHeldBlockState(Blocks.air.getDefaultState());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_179474_a(World worldIn, BlockPos p_179474_2_, Block p_179474_3_, Block p_179474_4_, Block p_179474_5_) {
/* 568 */       return !p_179474_3_.canPlaceBlockAt(worldIn, p_179474_2_) ? false : ((p_179474_4_.getMaterial() != Material.air) ? false : ((p_179474_5_.getMaterial() == Material.air) ? false : p_179474_5_.isFullCube()));
/*     */     }
/*     */   }
/*     */   
/*     */   static class AITakeBlock
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityEnderman enderman;
/*     */     
/*     */     public AITakeBlock(EntityEnderman p_i45841_1_) {
/* 578 */       this.enderman = p_i45841_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 583 */       return !this.enderman.worldObj.getGameRules().getBoolean("mobGriefing") ? false : ((this.enderman.getHeldBlockState().getBlock().getMaterial() != Material.air) ? false : ((this.enderman.getRNG().nextInt(20) == 0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 588 */       Random random = this.enderman.getRNG();
/* 589 */       World world = this.enderman.worldObj;
/* 590 */       int i = MathHelper.floor_double(this.enderman.posX - 2.0D + random.nextDouble() * 4.0D);
/* 591 */       int j = MathHelper.floor_double(this.enderman.posY + random.nextDouble() * 3.0D);
/* 592 */       int k = MathHelper.floor_double(this.enderman.posZ - 2.0D + random.nextDouble() * 4.0D);
/* 593 */       BlockPos blockpos = new BlockPos(i, j, k);
/* 594 */       IBlockState iblockstate = world.getBlockState(blockpos);
/* 595 */       Block block = iblockstate.getBlock();
/*     */       
/* 597 */       if (EntityEnderman.carriableBlocks.contains(block)) {
/*     */         
/* 599 */         this.enderman.setHeldBlockState(iblockstate);
/* 600 */         world.setBlockState(blockpos, Blocks.air.getDefaultState());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */