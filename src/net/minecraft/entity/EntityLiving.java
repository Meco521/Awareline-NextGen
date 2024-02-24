/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import java.util.UUID;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.EntityAITasks;
/*      */ import net.minecraft.entity.ai.EntityJumpHelper;
/*      */ import net.minecraft.entity.ai.EntityLookHelper;
/*      */ import net.minecraft.entity.ai.EntityMoveHelper;
/*      */ import net.minecraft.entity.ai.EntitySenses;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityGhast;
/*      */ import net.minecraft.entity.passive.EntityTameable;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemSword;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.pathfinding.PathNavigate;
/*      */ import net.minecraft.pathfinding.PathNavigateGround;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.optifine.reflect.Reflector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class EntityLiving
/*      */   extends EntityLivingBase
/*      */ {
/*      */   public int livingSoundTime;
/*      */   protected int experienceValue;
/*      */   private final EntityLookHelper lookHelper;
/*      */   protected EntityMoveHelper moveHelper;
/*      */   protected EntityJumpHelper jumpHelper;
/*      */   private final EntityBodyHelper bodyHelper;
/*      */   protected PathNavigate navigator;
/*      */   protected final EntityAITasks tasks;
/*      */   protected final EntityAITasks targetTasks;
/*      */   private EntityLivingBase attackTarget;
/*      */   private final EntitySenses senses;
/*   61 */   private final ItemStack[] equipment = new ItemStack[5];
/*      */ 
/*      */   
/*   64 */   protected float[] equipmentDropChances = new float[5];
/*      */   
/*      */   private boolean canPickUpLoot;
/*      */   
/*      */   private boolean persistenceRequired;
/*      */   
/*      */   private boolean isLeashed;
/*      */   
/*      */   private Entity leashedToEntity;
/*      */   private NBTTagCompound leashNBTTag;
/*   74 */   private UUID teamUuid = null;
/*   75 */   private String teamUuidString = null;
/*      */ 
/*      */   
/*      */   public EntityLiving(World worldIn) {
/*   79 */     super(worldIn);
/*   80 */     this.tasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*   81 */     this.targetTasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*   82 */     this.lookHelper = new EntityLookHelper(this);
/*   83 */     this.moveHelper = new EntityMoveHelper(this);
/*   84 */     this.jumpHelper = new EntityJumpHelper(this);
/*   85 */     this.bodyHelper = new EntityBodyHelper(this);
/*   86 */     this.navigator = getNewNavigator(worldIn);
/*   87 */     this.senses = new EntitySenses(this);
/*      */     
/*   89 */     for (int i = 0; i < this.equipmentDropChances.length; i++)
/*      */     {
/*   91 */       this.equipmentDropChances[i] = 0.085F;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*   97 */     super.applyEntityAttributes();
/*   98 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PathNavigate getNewNavigator(World worldIn) {
/*  106 */     return (PathNavigate)new PathNavigateGround(this, worldIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLookHelper getLookHelper() {
/*  111 */     return this.lookHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityMoveHelper getMoveHelper() {
/*  116 */     return this.moveHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityJumpHelper getJumpHelper() {
/*  121 */     return this.jumpHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public PathNavigate getNavigator() {
/*  126 */     return this.navigator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntitySenses getEntitySenses() {
/*  134 */     return this.senses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityLivingBase getAttackTarget() {
/*  142 */     return this.attackTarget;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
/*  150 */     this.attackTarget = entitylivingbaseIn;
/*  151 */     Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, new Object[] { this, entitylivingbaseIn });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
/*  159 */     return (cls != EntityGhast.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void eatGrassBonus() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  172 */     super.entityInit();
/*  173 */     this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTalkInterval() {
/*  181 */     return 80;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playLivingSound() {
/*  189 */     String s = getLivingSound();
/*      */     
/*  191 */     if (s != null)
/*      */     {
/*  193 */       playSound(s, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  202 */     super.onEntityUpdate();
/*  203 */     this.worldObj.theProfiler.startSection("mobBaseTick");
/*      */     
/*  205 */     if (isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
/*      */       
/*  207 */       this.livingSoundTime = -getTalkInterval();
/*  208 */       playLivingSound();
/*      */     } 
/*      */     
/*  211 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  219 */     if (this.experienceValue > 0) {
/*      */       
/*  221 */       int i = this.experienceValue;
/*  222 */       ItemStack[] aitemstack = getInventory();
/*      */       
/*  224 */       for (int j = 0; j < aitemstack.length; j++) {
/*      */         
/*  226 */         if (aitemstack[j] != null && this.equipmentDropChances[j] <= 1.0F)
/*      */         {
/*  228 */           i += 1 + this.rand.nextInt(3);
/*      */         }
/*      */       } 
/*      */       
/*  232 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  236 */     return this.experienceValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnExplosionParticle() {
/*  245 */     if (this.worldObj.isRemote) {
/*      */       
/*  247 */       for (int i = 0; i < 20; i++)
/*      */       {
/*  249 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  250 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  251 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  252 */         double d3 = 10.0D;
/*  253 */         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d0 * d3, this.posY + (this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d2 * d3, d0, d1, d2, new int[0]);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  258 */       this.worldObj.setEntityState(this, (byte)20);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  264 */     if (id == 20) {
/*      */       
/*  266 */       spawnExplosionParticle();
/*      */     }
/*      */     else {
/*      */       
/*  270 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  279 */     if (Config.isSmoothWorld() && canSkipUpdate()) {
/*      */       
/*  281 */       onUpdateMinimal();
/*      */     }
/*      */     else {
/*      */       
/*  285 */       super.onUpdate();
/*      */       
/*  287 */       if (!this.worldObj.isRemote)
/*      */       {
/*  289 */         updateLeashedState();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/*  296 */     this.bodyHelper.updateRenderAngles();
/*  297 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLivingSound() {
/*  305 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Item getDropItem() {
/*  310 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/*  322 */     Item item = getDropItem();
/*      */     
/*  324 */     if (item != null) {
/*      */       
/*  326 */       int i = this.rand.nextInt(3);
/*      */       
/*  328 */       if (lootingModifier > 0)
/*      */       {
/*  330 */         i += this.rand.nextInt(lootingModifier + 1);
/*      */       }
/*      */       
/*  333 */       for (int j = 0; j < i; j++)
/*      */       {
/*  335 */         dropItem(item, 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  345 */     super.writeEntityToNBT(tagCompound);
/*  346 */     tagCompound.setBoolean("CanPickUpLoot", canPickUpLoot());
/*  347 */     tagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
/*  348 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  350 */     for (int i = 0; i < this.equipment.length; i++) {
/*      */       
/*  352 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */       
/*  354 */       if (this.equipment[i] != null)
/*      */       {
/*  356 */         this.equipment[i].writeToNBT(nbttagcompound);
/*      */       }
/*      */       
/*  359 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */     } 
/*      */     
/*  362 */     tagCompound.setTag("Equipment", (NBTBase)nbttaglist);
/*  363 */     NBTTagList nbttaglist1 = new NBTTagList();
/*      */     
/*  365 */     for (int j = 0; j < this.equipmentDropChances.length; j++)
/*      */     {
/*  367 */       nbttaglist1.appendTag((NBTBase)new NBTTagFloat(this.equipmentDropChances[j]));
/*      */     }
/*      */     
/*  370 */     tagCompound.setTag("DropChances", (NBTBase)nbttaglist1);
/*  371 */     tagCompound.setBoolean("Leashed", this.isLeashed);
/*      */     
/*  373 */     if (this.leashedToEntity != null) {
/*      */       
/*  375 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */       
/*  377 */       if (this.leashedToEntity instanceof EntityLivingBase) {
/*      */         
/*  379 */         nbttagcompound1.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
/*  380 */         nbttagcompound1.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
/*      */       }
/*  382 */       else if (this.leashedToEntity instanceof EntityHanging) {
/*      */         
/*  384 */         BlockPos blockpos = ((EntityHanging)this.leashedToEntity).getHangingPosition();
/*  385 */         nbttagcompound1.setInteger("X", blockpos.getX());
/*  386 */         nbttagcompound1.setInteger("Y", blockpos.getY());
/*  387 */         nbttagcompound1.setInteger("Z", blockpos.getZ());
/*      */       } 
/*      */       
/*  390 */       tagCompound.setTag("Leash", (NBTBase)nbttagcompound1);
/*      */     } 
/*      */     
/*  393 */     if (isAIDisabled())
/*      */     {
/*  395 */       tagCompound.setBoolean("NoAI", isAIDisabled());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  404 */     super.readEntityFromNBT(tagCompund);
/*      */     
/*  406 */     if (tagCompund.hasKey("CanPickUpLoot", 1))
/*      */     {
/*  408 */       this.canPickUpLoot = tagCompund.getBoolean("CanPickUpLoot");
/*      */     }
/*      */     
/*  411 */     this.persistenceRequired = tagCompund.getBoolean("PersistenceRequired");
/*      */     
/*  413 */     if (tagCompund.hasKey("Equipment", 9)) {
/*      */       
/*  415 */       NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);
/*      */       
/*  417 */       for (int i = 0; i < this.equipment.length; i++)
/*      */       {
/*  419 */         this.equipment[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*      */       }
/*      */     } 
/*      */     
/*  423 */     if (tagCompund.hasKey("DropChances", 9)) {
/*      */       
/*  425 */       NBTTagList nbttaglist1 = tagCompund.getTagList("DropChances", 5);
/*      */       
/*  427 */       for (int j = 0; j < nbttaglist1.tagCount(); j++)
/*      */       {
/*  429 */         this.equipmentDropChances[j] = nbttaglist1.getFloatAt(j);
/*      */       }
/*      */     } 
/*      */     
/*  433 */     this.isLeashed = tagCompund.getBoolean("Leashed");
/*      */     
/*  435 */     if (this.isLeashed && tagCompund.hasKey("Leash", 10))
/*      */     {
/*  437 */       this.leashNBTTag = tagCompund.getCompoundTag("Leash");
/*      */     }
/*      */     
/*  440 */     setNoAI(tagCompund.getBoolean("NoAI"));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMoveForward(float p_70657_1_) {
/*  445 */     this.moveForward = p_70657_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/*  453 */     super.setAIMoveSpeed(speedIn);
/*  454 */     this.moveForward = speedIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  463 */     super.onLivingUpdate();
/*  464 */     this.worldObj.theProfiler.startSection("looting");
/*      */     
/*  466 */     if (!this.worldObj.isRemote && canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getBoolean("mobGriefing"))
/*      */     {
/*  468 */       for (EntityItem entityitem : this.worldObj.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D))) {
/*      */         
/*  470 */         if (!entityitem.isDead && entityitem.getEntityItem() != null && !entityitem.cannotPickup())
/*      */         {
/*  472 */           updateEquipmentIfNeeded(entityitem);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  477 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/*  486 */     ItemStack itemstack = itemEntity.getEntityItem();
/*  487 */     int i = getArmorPosition(itemstack);
/*      */     
/*  489 */     if (i > -1) {
/*      */       
/*  491 */       boolean flag = true;
/*  492 */       ItemStack itemstack1 = getEquipmentInSlot(i);
/*      */       
/*  494 */       if (itemstack1 != null)
/*      */       {
/*  496 */         if (i == 0) {
/*      */           
/*  498 */           if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
/*      */             
/*  500 */             flag = true;
/*      */           }
/*  502 */           else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
/*      */             
/*  504 */             ItemSword itemsword = (ItemSword)itemstack.getItem();
/*  505 */             ItemSword itemsword1 = (ItemSword)itemstack1.getItem();
/*      */             
/*  507 */             if (itemsword.getDamageVsEntity() != itemsword1.getDamageVsEntity())
/*      */             {
/*  509 */               flag = (itemsword.getDamageVsEntity() > itemsword1.getDamageVsEntity());
/*      */             }
/*      */             else
/*      */             {
/*  513 */               flag = (itemstack.getMetadata() > itemstack1.getMetadata() || (itemstack.hasTagCompound() && !itemstack1.hasTagCompound()));
/*      */             }
/*      */           
/*  516 */           } else if (itemstack.getItem() instanceof net.minecraft.item.ItemBow && itemstack1.getItem() instanceof net.minecraft.item.ItemBow) {
/*      */             
/*  518 */             flag = (itemstack.hasTagCompound() && !itemstack1.hasTagCompound());
/*      */           }
/*      */           else {
/*      */             
/*  522 */             flag = false;
/*      */           }
/*      */         
/*  525 */         } else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
/*      */           
/*  527 */           flag = true;
/*      */         }
/*  529 */         else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor) {
/*      */           
/*  531 */           ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*  532 */           ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();
/*      */           
/*  534 */           if (itemarmor.damageReduceAmount != itemarmor1.damageReduceAmount)
/*      */           {
/*  536 */             flag = (itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount);
/*      */           }
/*      */           else
/*      */           {
/*  540 */             flag = (itemstack.getMetadata() > itemstack1.getMetadata() || (itemstack.hasTagCompound() && !itemstack1.hasTagCompound()));
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  545 */           flag = false;
/*      */         } 
/*      */       }
/*      */       
/*  549 */       if (flag && func_175448_a(itemstack)) {
/*      */         
/*  551 */         if (itemstack1 != null && this.rand.nextFloat() - 0.1F < this.equipmentDropChances[i])
/*      */         {
/*  553 */           entityDropItem(itemstack1, 0.0F);
/*      */         }
/*      */         
/*  556 */         if (itemstack.getItem() == Items.diamond && itemEntity.getThrower() != null) {
/*      */           
/*  558 */           EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(itemEntity.getThrower());
/*      */           
/*  560 */           if (entityplayer != null)
/*      */           {
/*  562 */             entityplayer.triggerAchievement((StatBase)AchievementList.diamondsToYou);
/*      */           }
/*      */         } 
/*      */         
/*  566 */         setCurrentItemOrArmor(i, itemstack);
/*  567 */         this.equipmentDropChances[i] = 2.0F;
/*  568 */         this.persistenceRequired = true;
/*  569 */         onItemPickup((Entity)itemEntity, 1);
/*  570 */         itemEntity.setDead();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean func_175448_a(ItemStack stack) {
/*  577 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDespawn() {
/*  585 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void despawnEntity() {
/*  593 */     Object object = null;
/*  594 */     Object object1 = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
/*  595 */     Object object2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
/*      */     
/*  597 */     if (this.persistenceRequired) {
/*      */       
/*  599 */       this.entityAge = 0;
/*      */     }
/*  601 */     else if ((this.entityAge & 0x1F) == 31 && (object = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, new Object[] { this })) != object1) {
/*      */       
/*  603 */       if (object == object2)
/*      */       {
/*  605 */         this.entityAge = 0;
/*      */       }
/*      */       else
/*      */       {
/*  609 */         setDead();
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  614 */       EntityPlayer entityPlayer = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
/*      */       
/*  616 */       if (entityPlayer != null) {
/*      */         
/*  618 */         double d0 = ((Entity)entityPlayer).posX - this.posX;
/*  619 */         double d1 = ((Entity)entityPlayer).posY - this.posY;
/*  620 */         double d2 = ((Entity)entityPlayer).posZ - this.posZ;
/*  621 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  623 */         if (canDespawn() && d3 > 16384.0D)
/*      */         {
/*  625 */           setDead();
/*      */         }
/*      */         
/*  628 */         if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0D && canDespawn()) {
/*      */           
/*  630 */           setDead();
/*      */         }
/*  632 */         else if (d3 < 1024.0D) {
/*      */           
/*  634 */           this.entityAge = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void updateEntityActionState() {
/*  642 */     this.entityAge++;
/*  643 */     this.worldObj.theProfiler.startSection("checkDespawn");
/*  644 */     despawnEntity();
/*  645 */     this.worldObj.theProfiler.endSection();
/*  646 */     this.worldObj.theProfiler.startSection("sensing");
/*  647 */     this.senses.clearSensingCache();
/*  648 */     this.worldObj.theProfiler.endSection();
/*  649 */     this.worldObj.theProfiler.startSection("targetSelector");
/*  650 */     this.targetTasks.onUpdateTasks();
/*  651 */     this.worldObj.theProfiler.endSection();
/*  652 */     this.worldObj.theProfiler.startSection("goalSelector");
/*  653 */     this.tasks.onUpdateTasks();
/*  654 */     this.worldObj.theProfiler.endSection();
/*  655 */     this.worldObj.theProfiler.startSection("navigation");
/*  656 */     this.navigator.onUpdateNavigation();
/*  657 */     this.worldObj.theProfiler.endSection();
/*  658 */     this.worldObj.theProfiler.startSection("mob tick");
/*  659 */     updateAITasks();
/*  660 */     this.worldObj.theProfiler.endSection();
/*  661 */     this.worldObj.theProfiler.startSection("controls");
/*  662 */     this.worldObj.theProfiler.startSection("move");
/*  663 */     this.moveHelper.onUpdateMoveHelper();
/*  664 */     this.worldObj.theProfiler.endStartSection("look");
/*  665 */     this.lookHelper.onUpdateLook();
/*  666 */     this.worldObj.theProfiler.endStartSection("jump");
/*  667 */     this.jumpHelper.doJump();
/*  668 */     this.worldObj.theProfiler.endSection();
/*  669 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAITasks() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVerticalFaceSpeed() {
/*  682 */     return 40;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void faceEntity(Entity entityIn, float p_70625_2_, float p_70625_3_) {
/*  690 */     double d2, d0 = entityIn.posX - this.posX;
/*  691 */     double d1 = entityIn.posZ - this.posZ;
/*      */ 
/*      */     
/*  694 */     if (entityIn instanceof EntityLivingBase) {
/*      */       
/*  696 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
/*  697 */       d2 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - this.posY + getEyeHeight();
/*      */     }
/*      */     else {
/*      */       
/*  701 */       d2 = ((entityIn.getEntityBoundingBox()).minY + (entityIn.getEntityBoundingBox()).maxY) / 2.0D - this.posY + getEyeHeight();
/*      */     } 
/*      */     
/*  704 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
/*  705 */     float f = (float)(MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
/*  706 */     float f1 = (float)-(MathHelper.atan2(d2, d3) * 180.0D / Math.PI);
/*  707 */     this.rotationPitch = updateRotation(this.rotationPitch, f1, p_70625_3_);
/*  708 */     this.rotationYaw = updateRotation(this.rotationYaw, f, p_70625_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
/*  716 */     float f = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
/*      */     
/*  718 */     if (f > p_70663_3_)
/*      */     {
/*  720 */       f = p_70663_3_;
/*      */     }
/*      */     
/*  723 */     if (f < -p_70663_3_)
/*      */     {
/*  725 */       f = -p_70663_3_;
/*      */     }
/*      */     
/*  728 */     return p_70663_1_ + f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnHere() {
/*  736 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNotColliding() {
/*  744 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRenderSizeModifier() {
/*  752 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSpawnedInChunk() {
/*  760 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/*  768 */     if (this.attackTarget == null)
/*      */     {
/*  770 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  774 */     int i = (int)(getHealth() - getMaxHealth() * 0.33F);
/*  775 */     i -= 3 - this.worldObj.getDifficulty().getDifficultyId() << 2;
/*      */     
/*  777 */     if (i < 0)
/*      */     {
/*  779 */       i = 0;
/*      */     }
/*      */     
/*  782 */     return i + 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItem() {
/*  791 */     return this.equipment[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getEquipmentInSlot(int slotIn) {
/*  799 */     return this.equipment[slotIn];
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentArmor(int slotIn) {
/*  804 */     return this.equipment[slotIn + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/*  812 */     this.equipment[slotIn] = stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/*  820 */     return this.equipment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
/*  832 */     for (int i = 0; i < (getInventory()).length; i++) {
/*      */       
/*  834 */       ItemStack itemstack = getEquipmentInSlot(i);
/*  835 */       boolean flag = (this.equipmentDropChances[i] > 1.0F);
/*      */       
/*  837 */       if (itemstack != null && (wasRecentlyHit || flag) && this.rand.nextFloat() - lootingModifier * 0.01F < this.equipmentDropChances[i]) {
/*      */         
/*  839 */         if (!flag && itemstack.isItemStackDamageable()) {
/*      */           
/*  841 */           int j = Math.max(itemstack.getMaxDamage() - 25, 1);
/*  842 */           int k = itemstack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(j) + 1);
/*      */           
/*  844 */           if (k > j)
/*      */           {
/*  846 */             k = j;
/*      */           }
/*      */           
/*  849 */           if (k < 1)
/*      */           {
/*  851 */             k = 1;
/*      */           }
/*      */           
/*  854 */           itemstack.setItemDamage(k);
/*      */         } 
/*      */         
/*  857 */         entityDropItem(itemstack, 0.0F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/*  867 */     if (this.rand.nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty()) {
/*      */       
/*  869 */       int i = this.rand.nextInt(2);
/*  870 */       float f = (this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.1F : 0.25F;
/*      */       
/*  872 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/*  874 */         i++;
/*      */       }
/*      */       
/*  877 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/*  879 */         i++;
/*      */       }
/*      */       
/*  882 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/*  884 */         i++;
/*      */       }
/*      */       
/*  887 */       for (int j = 3; j >= 0; j--) {
/*      */         
/*  889 */         ItemStack itemstack = getCurrentArmor(j);
/*      */         
/*  891 */         if (j < 3 && this.rand.nextFloat() < f) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  896 */         if (itemstack == null) {
/*      */           
/*  898 */           Item item = getArmorItemForSlot(j + 1, i);
/*      */           
/*  900 */           if (item != null)
/*      */           {
/*  902 */             setCurrentItemOrArmor(j + 1, new ItemStack(item));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getArmorPosition(ItemStack stack) {
/*  911 */     if (stack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && stack.getItem() != Items.skull) {
/*      */       
/*  913 */       if (stack.getItem() instanceof ItemArmor)
/*      */       {
/*  915 */         switch (((ItemArmor)stack.getItem()).armorType) {
/*      */           
/*      */           case 0:
/*  918 */             return 4;
/*      */           
/*      */           case 1:
/*  921 */             return 3;
/*      */           
/*      */           case 2:
/*  924 */             return 2;
/*      */           
/*      */           case 3:
/*  927 */             return 1;
/*      */         } 
/*      */       
/*      */       }
/*  931 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  935 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Item getArmorItemForSlot(int armorSlot, int itemTier) {
/*  944 */     switch (armorSlot) {
/*      */       
/*      */       case 4:
/*  947 */         if (itemTier == 0)
/*      */         {
/*  949 */           return (Item)Items.leather_helmet;
/*      */         }
/*  951 */         if (itemTier == 1)
/*      */         {
/*  953 */           return (Item)Items.golden_helmet;
/*      */         }
/*  955 */         if (itemTier == 2)
/*      */         {
/*  957 */           return (Item)Items.chainmail_helmet;
/*      */         }
/*  959 */         if (itemTier == 3)
/*      */         {
/*  961 */           return (Item)Items.iron_helmet;
/*      */         }
/*  963 */         if (itemTier == 4)
/*      */         {
/*  965 */           return (Item)Items.diamond_helmet;
/*      */         }
/*      */       
/*      */       case 3:
/*  969 */         if (itemTier == 0)
/*      */         {
/*  971 */           return (Item)Items.leather_chestplate;
/*      */         }
/*  973 */         if (itemTier == 1)
/*      */         {
/*  975 */           return (Item)Items.golden_chestplate;
/*      */         }
/*  977 */         if (itemTier == 2)
/*      */         {
/*  979 */           return (Item)Items.chainmail_chestplate;
/*      */         }
/*  981 */         if (itemTier == 3)
/*      */         {
/*  983 */           return (Item)Items.iron_chestplate;
/*      */         }
/*  985 */         if (itemTier == 4)
/*      */         {
/*  987 */           return (Item)Items.diamond_chestplate;
/*      */         }
/*      */       
/*      */       case 2:
/*  991 */         if (itemTier == 0)
/*      */         {
/*  993 */           return (Item)Items.leather_leggings;
/*      */         }
/*  995 */         if (itemTier == 1)
/*      */         {
/*  997 */           return (Item)Items.golden_leggings;
/*      */         }
/*  999 */         if (itemTier == 2)
/*      */         {
/* 1001 */           return (Item)Items.chainmail_leggings;
/*      */         }
/* 1003 */         if (itemTier == 3)
/*      */         {
/* 1005 */           return (Item)Items.iron_leggings;
/*      */         }
/* 1007 */         if (itemTier == 4)
/*      */         {
/* 1009 */           return (Item)Items.diamond_leggings;
/*      */         }
/*      */       
/*      */       case 1:
/* 1013 */         if (itemTier == 0)
/*      */         {
/* 1015 */           return (Item)Items.leather_boots;
/*      */         }
/* 1017 */         if (itemTier == 1)
/*      */         {
/* 1019 */           return (Item)Items.golden_boots;
/*      */         }
/* 1021 */         if (itemTier == 2)
/*      */         {
/* 1023 */           return (Item)Items.chainmail_boots;
/*      */         }
/* 1025 */         if (itemTier == 3)
/*      */         {
/* 1027 */           return (Item)Items.iron_boots;
/*      */         }
/* 1029 */         if (itemTier == 4)
/*      */         {
/* 1031 */           return (Item)Items.diamond_boots;
/*      */         }
/*      */         break;
/*      */     } 
/* 1035 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 1044 */     float f = difficulty.getClampedAdditionalDifficulty();
/*      */     
/* 1046 */     if (getHeldItem() != null && this.rand.nextFloat() < 0.25F * f)
/*      */     {
/* 1048 */       EnchantmentHelper.addRandomEnchantment(this.rand, getHeldItem(), (int)(5.0F + f * this.rand.nextInt(18)));
/*      */     }
/*      */     
/* 1051 */     for (int i = 0; i < 4; i++) {
/*      */       
/* 1053 */       ItemStack itemstack = getCurrentArmor(i);
/*      */       
/* 1055 */       if (itemstack != null && this.rand.nextFloat() < 0.5F * f)
/*      */       {
/* 1057 */         EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * this.rand.nextInt(18)));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 1068 */     getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
/* 1069 */     return livingdata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeSteered() {
/* 1078 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enablePersistence() {
/* 1086 */     this.persistenceRequired = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEquipmentDropChance(int slotIn, float chance) {
/* 1091 */     this.equipmentDropChances[slotIn] = chance;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPickUpLoot() {
/* 1096 */     return this.canPickUpLoot;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCanPickUpLoot(boolean canPickup) {
/* 1101 */     this.canPickUpLoot = canPickup;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNoDespawnRequired() {
/* 1106 */     return this.persistenceRequired;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean interactFirst(EntityPlayer playerIn) {
/* 1114 */     if (this.isLeashed && this.leashedToEntity == playerIn) {
/*      */       
/* 1116 */       clearLeashed(true, !playerIn.capabilities.isCreativeMode);
/* 1117 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1121 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*      */     
/* 1123 */     if (itemstack != null && itemstack.getItem() == Items.lead && allowLeashing()) {
/*      */       
/* 1125 */       if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
/*      */         
/* 1127 */         setLeashedToEntity((Entity)playerIn, true);
/* 1128 */         itemstack.stackSize--;
/* 1129 */         return true;
/*      */       } 
/*      */       
/* 1132 */       if (((EntityTameable)this).isOwner((EntityLivingBase)playerIn)) {
/*      */         
/* 1134 */         setLeashedToEntity((Entity)playerIn, true);
/* 1135 */         itemstack.stackSize--;
/* 1136 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1140 */     if (interact(playerIn))
/*      */     {
/* 1142 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1146 */     return super.interactFirst(playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean interact(EntityPlayer player) {
/* 1156 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLeashedState() {
/* 1164 */     if (this.leashNBTTag != null)
/*      */     {
/* 1166 */       recreateLeash();
/*      */     }
/*      */     
/* 1169 */     if (this.isLeashed) {
/*      */       
/* 1171 */       if (!isEntityAlive())
/*      */       {
/* 1173 */         clearLeashed(true, true);
/*      */       }
/*      */       
/* 1176 */       if (this.leashedToEntity == null || this.leashedToEntity.isDead)
/*      */       {
/* 1178 */         clearLeashed(true, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearLeashed(boolean sendPacket, boolean dropLead) {
/* 1188 */     if (this.isLeashed) {
/*      */       
/* 1190 */       this.isLeashed = false;
/* 1191 */       this.leashedToEntity = null;
/*      */       
/* 1193 */       if (!this.worldObj.isRemote && dropLead)
/*      */       {
/* 1195 */         dropItem(Items.lead, 1);
/*      */       }
/*      */       
/* 1198 */       if (!this.worldObj.isRemote && sendPacket && this.worldObj instanceof WorldServer)
/*      */       {
/* 1200 */         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S1BPacketEntityAttach(1, this, (Entity)null));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean allowLeashing() {
/* 1207 */     return (!this.isLeashed && !(this instanceof net.minecraft.entity.monster.IMob));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getLeashed() {
/* 1212 */     return this.isLeashed;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getLeashedToEntity() {
/* 1217 */     return this.leashedToEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLeashedToEntity(Entity entityIn, boolean sendAttachNotification) {
/* 1225 */     this.isLeashed = true;
/* 1226 */     this.leashedToEntity = entityIn;
/*      */     
/* 1228 */     if (!this.worldObj.isRemote && sendAttachNotification && this.worldObj instanceof WorldServer)
/*      */     {
/* 1230 */       ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S1BPacketEntityAttach(1, this, this.leashedToEntity));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void recreateLeash() {
/* 1236 */     if (this.isLeashed && this.leashNBTTag != null)
/*      */     {
/* 1238 */       if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
/*      */         
/* 1240 */         UUID uuid = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));
/*      */         
/* 1242 */         for (EntityLivingBase entitylivingbase : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D))) {
/*      */           
/* 1244 */           if (entitylivingbase.getUniqueID().equals(uuid)) {
/*      */             
/* 1246 */             this.leashedToEntity = entitylivingbase;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1251 */       } else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
/*      */         
/* 1253 */         BlockPos blockpos = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
/* 1254 */         EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(this.worldObj, blockpos);
/*      */         
/* 1256 */         if (entityleashknot == null)
/*      */         {
/* 1258 */           entityleashknot = EntityLeashKnot.createKnot(this.worldObj, blockpos);
/*      */         }
/*      */         
/* 1261 */         this.leashedToEntity = entityleashknot;
/*      */       }
/*      */       else {
/*      */         
/* 1265 */         clearLeashed(false, true);
/*      */       } 
/*      */     }
/*      */     
/* 1269 */     this.leashNBTTag = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*      */     int i;
/* 1276 */     if (inventorySlot == 99) {
/*      */       
/* 1278 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 1282 */       i = inventorySlot - 100 + 1;
/*      */       
/* 1284 */       if (i < 0 || i >= this.equipment.length)
/*      */       {
/* 1286 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1290 */     if (itemStackIn == null || getArmorPosition(itemStackIn) == i || (i == 4 && itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock)) {
/*      */       
/* 1292 */       setCurrentItemOrArmor(i, itemStackIn);
/* 1293 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1297 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 1306 */     return (super.isServerWorld() && !isAIDisabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoAI(boolean disable) {
/* 1314 */     this.dataWatcher.updateObject(15, Byte.valueOf((byte)(disable ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAIDisabled() {
/* 1322 */     return (this.dataWatcher.getWatchableObjectByte(15) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSkipUpdate() {
/* 1327 */     if (isChild())
/*      */     {
/* 1329 */       return false;
/*      */     }
/* 1331 */     if (this.hurtTime > 0)
/*      */     {
/* 1333 */       return false;
/*      */     }
/* 1335 */     if (this.ticksExisted < 20)
/*      */     {
/* 1337 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1341 */     World world = getEntityWorld();
/*      */     
/* 1343 */     if (world == null)
/*      */     {
/* 1345 */       return false;
/*      */     }
/* 1347 */     if (world.playerEntities.size() != 1)
/*      */     {
/* 1349 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1353 */     Entity entity = world.playerEntities.get(0);
/* 1354 */     double d0 = Math.max(Math.abs(this.posX - entity.posX) - 16.0D, 0.0D);
/* 1355 */     double d1 = Math.max(Math.abs(this.posZ - entity.posZ) - 16.0D, 0.0D);
/* 1356 */     double d2 = d0 * d0 + d1 * d1;
/* 1357 */     return !isInRangeToRenderDist(d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdateMinimal() {
/* 1364 */     this.entityAge++;
/*      */     
/* 1366 */     if (this instanceof net.minecraft.entity.monster.EntityMob) {
/*      */       
/* 1368 */       float f = getBrightness(1.0F);
/*      */       
/* 1370 */       if (f > 0.5F)
/*      */       {
/* 1372 */         this.entityAge += 2;
/*      */       }
/*      */     } 
/*      */     
/* 1376 */     despawnEntity();
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 1381 */     UUID uuid = getUniqueID();
/*      */     
/* 1383 */     if (this.teamUuid != uuid) {
/*      */       
/* 1385 */       this.teamUuid = uuid;
/* 1386 */       this.teamUuidString = uuid.toString();
/*      */     } 
/*      */     
/* 1389 */     return (Team)this.worldObj.getScoreboard().getPlayersTeam(this.teamUuidString);
/*      */   }
/*      */   
/*      */   public enum SpawnPlacementType
/*      */   {
/* 1394 */     ON_GROUND,
/* 1395 */     IN_AIR,
/* 1396 */     IN_WATER;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\EntityLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */