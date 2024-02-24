/*      */ package net.minecraft.entity.player;
/*      */ import awareline.main.event.Event;
/*      */ import awareline.main.event.EventManager;
/*      */ import awareline.main.event.events.world.moveEvents.EventJump;
/*      */ import awareline.main.mod.implement.combat.KillAura;
/*      */ import awareline.main.mod.implement.combat.advanced.AdvancedAura;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IEntityMultiPart;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityMob;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.event.ClickEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryEnderChest;
/*      */ import net.minecraft.item.EnumAction;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.LockCode;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public abstract class EntityPlayer extends EntityLivingBase {
/*   61 */   public PlayerDetect detect = new PlayerDetect();
/*      */   
/*      */   public Float movementYaw;
/*   64 */   public InventoryPlayer inventory = new InventoryPlayer(this);
/*   65 */   private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
/*      */ 
/*      */ 
/*      */   
/*      */   public Container inventoryContainer;
/*      */ 
/*      */ 
/*      */   
/*      */   public Container openContainer;
/*      */ 
/*      */   
/*   76 */   protected FoodStats foodStats = new FoodStats();
/*      */ 
/*      */   
/*      */   protected int flyToggleTimer;
/*      */ 
/*      */   
/*      */   public float prevCameraYaw;
/*      */ 
/*      */   
/*      */   public float cameraYaw;
/*      */   
/*      */   public int xpCooldown;
/*      */   
/*      */   public double prevChasingPosX;
/*      */   
/*      */   public double prevChasingPosY;
/*      */   
/*      */   public double prevChasingPosZ;
/*      */   
/*      */   public double chasingPosX;
/*      */   
/*      */   public double chasingPosY;
/*      */   
/*      */   public double chasingPosZ;
/*      */   
/*      */   protected boolean sleeping;
/*      */   
/*      */   public BlockPos playerLocation;
/*      */   
/*      */   private int sleepTimer;
/*      */   
/*      */   public float renderOffsetX;
/*      */   
/*      */   public float renderOffsetY;
/*      */   
/*      */   public float renderOffsetZ;
/*      */   
/*      */   private BlockPos spawnChunk;
/*      */   
/*      */   private boolean spawnForced;
/*      */   
/*      */   private BlockPos startMinecartRidingCoordinate;
/*      */   
/*  119 */   public PlayerCapabilities capabilities = new PlayerCapabilities();
/*      */ 
/*      */ 
/*      */   
/*      */   public int experienceLevel;
/*      */ 
/*      */ 
/*      */   
/*      */   public int experienceTotal;
/*      */ 
/*      */ 
/*      */   
/*      */   public float experience;
/*      */ 
/*      */ 
/*      */   
/*      */   private int xpSeed;
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack itemInUse;
/*      */ 
/*      */   
/*      */   private int itemInUseCount;
/*      */ 
/*      */   
/*  145 */   protected float speedOnGround = 0.1F;
/*  146 */   public float speedInAir = 0.02F;
/*      */ 
/*      */   
/*      */   private int lastXPSound;
/*      */ 
/*      */   
/*      */   private final GameProfile gameProfile;
/*      */   
/*      */   private boolean hasReducedDebug = false;
/*      */   
/*      */   public EntityFishHook fishEntity;
/*      */ 
/*      */   
/*      */   public EntityPlayer(World worldIn, GameProfile gameProfileIn) {
/*  160 */     super(worldIn);
/*  161 */     this.entityUniqueID = getUUID(gameProfileIn);
/*  162 */     this.gameProfile = gameProfileIn;
/*  163 */     this.inventoryContainer = (Container)new ContainerPlayer(this.inventory, !worldIn.isRemote, this);
/*  164 */     this.openContainer = this.inventoryContainer;
/*  165 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*  166 */     setLocationAndAngles(blockpos.getX() + 0.5D, (blockpos.getY() + 1), blockpos.getZ() + 0.5D, 0.0F, 0.0F);
/*  167 */     this.unused180 = 180.0F;
/*  168 */     this.fireResistance = 20;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  173 */     super.applyEntityAttributes();
/*  174 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*  175 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  180 */     super.entityInit();
/*  181 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  182 */     this.dataWatcher.addObject(17, Float.valueOf(0.0F));
/*  183 */     this.dataWatcher.addObject(18, Integer.valueOf(0));
/*  184 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getItemInUse() {
/*  192 */     return this.itemInUse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemInUseCount() {
/*  200 */     return this.itemInUseCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingItem() {
/*  208 */     return (this.itemInUse != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemInUseDuration() {
/*  216 */     return isUsingItem() ? (this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount) : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopUsingItem() {
/*  221 */     if (this.itemInUse != null)
/*      */     {
/*  223 */       this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
/*      */     }
/*      */     
/*  226 */     clearItemInUse();
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearItemInUse() {
/*  231 */     this.itemInUse = null;
/*  232 */     this.itemInUseCount = 0;
/*      */     
/*  234 */     if (!this.worldObj.isRemote)
/*      */     {
/*  236 */       setEating(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlocking() {
/*  242 */     return (isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  250 */     this.noClip = isSpectator();
/*      */     
/*  252 */     if (isSpectator())
/*      */     {
/*  254 */       this.onGround = false;
/*      */     }
/*      */     
/*  257 */     if (this.itemInUse != null) {
/*      */       
/*  259 */       ItemStack itemstack = this.inventory.getCurrentItem();
/*      */       
/*  261 */       if (itemstack == this.itemInUse) {
/*      */         
/*  263 */         if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0)
/*      */         {
/*  265 */           updateItemUse(itemstack, 5);
/*      */         }
/*      */         
/*  268 */         if (--this.itemInUseCount == 0 && !this.worldObj.isRemote)
/*      */         {
/*  270 */           onItemUseFinish();
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  275 */         clearItemInUse();
/*      */       } 
/*      */     } 
/*      */     
/*  279 */     if (this.xpCooldown > 0)
/*      */     {
/*  281 */       this.xpCooldown--;
/*      */     }
/*      */     
/*  284 */     if (this.sleeping) {
/*      */       
/*  286 */       this.sleepTimer++;
/*      */       
/*  288 */       if (this.sleepTimer > 100)
/*      */       {
/*  290 */         this.sleepTimer = 100;
/*      */       }
/*      */       
/*  293 */       if (!this.worldObj.isRemote)
/*      */       {
/*  295 */         if (!isInBed())
/*      */         {
/*  297 */           wakeUpPlayer(true, true, false);
/*      */         }
/*  299 */         else if (this.worldObj.isDaytime())
/*      */         {
/*  301 */           wakeUpPlayer(false, true, true);
/*      */         }
/*      */       
/*      */       }
/*  305 */     } else if (this.sleepTimer > 0) {
/*      */       
/*  307 */       this.sleepTimer++;
/*      */       
/*  309 */       if (this.sleepTimer >= 110)
/*      */       {
/*  311 */         this.sleepTimer = 0;
/*      */       }
/*      */     } 
/*      */     
/*  315 */     super.onUpdate();
/*      */     
/*  317 */     if (!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
/*      */       
/*  319 */       closeScreen();
/*  320 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  323 */     if (isBurning() && this.capabilities.disableDamage)
/*      */     {
/*  325 */       extinguish();
/*      */     }
/*      */     
/*  328 */     this.prevChasingPosX = this.chasingPosX;
/*  329 */     this.prevChasingPosY = this.chasingPosY;
/*  330 */     this.prevChasingPosZ = this.chasingPosZ;
/*  331 */     double d5 = this.posX - this.chasingPosX;
/*  332 */     double d0 = this.posY - this.chasingPosY;
/*  333 */     double d1 = this.posZ - this.chasingPosZ;
/*  334 */     double d2 = 10.0D;
/*      */     
/*  336 */     if (d5 > d2)
/*      */     {
/*  338 */       this.prevChasingPosX = this.chasingPosX = this.posX;
/*      */     }
/*      */     
/*  341 */     if (d1 > d2)
/*      */     {
/*  343 */       this.prevChasingPosZ = this.chasingPosZ = this.posZ;
/*      */     }
/*      */     
/*  346 */     if (d0 > d2)
/*      */     {
/*  348 */       this.prevChasingPosY = this.chasingPosY = this.posY;
/*      */     }
/*      */     
/*  351 */     if (d5 < -d2)
/*      */     {
/*  353 */       this.prevChasingPosX = this.chasingPosX = this.posX;
/*      */     }
/*      */     
/*  356 */     if (d1 < -d2)
/*      */     {
/*  358 */       this.prevChasingPosZ = this.chasingPosZ = this.posZ;
/*      */     }
/*      */     
/*  361 */     if (d0 < -d2)
/*      */     {
/*  363 */       this.prevChasingPosY = this.chasingPosY = this.posY;
/*      */     }
/*      */     
/*  366 */     this.chasingPosX += d5 * 0.25D;
/*  367 */     this.chasingPosZ += d1 * 0.25D;
/*  368 */     this.chasingPosY += d0 * 0.25D;
/*      */     
/*  370 */     if (this.ridingEntity == null)
/*      */     {
/*  372 */       this.startMinecartRidingCoordinate = null;
/*      */     }
/*      */     
/*  375 */     if (!this.worldObj.isRemote) {
/*      */       
/*  377 */       this.foodStats.onUpdate(this);
/*  378 */       triggerAchievement(StatList.minutesPlayedStat);
/*      */       
/*  380 */       if (isEntityAlive())
/*      */       {
/*  382 */         triggerAchievement(StatList.timeSinceDeathStat);
/*      */       }
/*      */     } 
/*      */     
/*  386 */     int i = 29999999;
/*  387 */     double d3 = MathHelper.clamp_double(this.posX, -2.9999999E7D, 2.9999999E7D);
/*  388 */     double d4 = MathHelper.clamp_double(this.posZ, -2.9999999E7D, 2.9999999E7D);
/*      */     
/*  390 */     if (d3 != this.posX || d4 != this.posZ)
/*      */     {
/*  392 */       setPosition(d3, this.posY, d4);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  401 */     return this.capabilities.disableDamage ? 0 : 80;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSwimSound() {
/*  406 */     return "game.player.swim";
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSplashSound() {
/*  411 */     return "game.player.swim.splash";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/*  419 */     return 10;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/*  424 */     this.worldObj.playSoundToNearExcept(this, name, volume, pitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateItemUse(ItemStack itemStackIn, int p_71010_2_) {
/*  432 */     if (itemStackIn.getItemUseAction() == EnumAction.DRINK)
/*      */     {
/*  434 */       playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */     
/*  437 */     if (itemStackIn.getItemUseAction() == EnumAction.EAT) {
/*      */       
/*  439 */       for (int i = 0; i < p_71010_2_; i++) {
/*      */         
/*  441 */         Vec3 vec3 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*  442 */         vec3 = vec3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  443 */         vec3 = vec3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  444 */         double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/*  445 */         Vec3 vec31 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/*  446 */         vec31 = vec31.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  447 */         vec31 = vec31.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  448 */         vec31 = vec31.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */         
/*  450 */         if (itemStackIn.getHasSubtypes()) {
/*      */           
/*  452 */           this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(itemStackIn.getItem()), itemStackIn.getMetadata() });
/*      */         }
/*      */         else {
/*      */           
/*  456 */           this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(itemStackIn.getItem()) });
/*      */         } 
/*      */       } 
/*      */       
/*  460 */       playSound("random.eat", 0.5F + 0.5F * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/*  469 */     if (this.itemInUse != null) {
/*      */       
/*  471 */       updateItemUse(this.itemInUse, 16);
/*  472 */       int i = this.itemInUse.stackSize;
/*  473 */       ItemStack itemstack = this.itemInUse.onItemUseFinish(this.worldObj, this);
/*      */       
/*  475 */       if (itemstack != this.itemInUse || (itemstack != null && itemstack.stackSize != i)) {
/*      */         
/*  477 */         this.inventory.mainInventory[this.inventory.currentItem] = itemstack;
/*      */         
/*  479 */         if (itemstack.stackSize == 0)
/*      */         {
/*  481 */           this.inventory.mainInventory[this.inventory.currentItem] = null;
/*      */         }
/*      */       } 
/*      */       
/*  485 */       clearItemInUse();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  491 */     if (id == 9) {
/*      */       
/*  493 */       onItemUseFinish();
/*      */     }
/*  495 */     else if (id == 23) {
/*      */       
/*  497 */       this.hasReducedDebug = false;
/*      */     }
/*  499 */     else if (id == 22) {
/*      */       
/*  501 */       this.hasReducedDebug = true;
/*      */     }
/*      */     else {
/*      */       
/*  505 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/*  514 */     return (getHealth() <= 0.0F || this.sleeping);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeScreen() {
/*  522 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/*  530 */     if (!this.worldObj.isRemote && isSneaking()) {
/*      */       
/*  532 */       mountEntity((Entity)null);
/*  533 */       setSneaking(false);
/*      */     }
/*      */     else {
/*      */       
/*  537 */       double d0 = this.posX;
/*  538 */       double d1 = this.posY;
/*  539 */       double d2 = this.posZ;
/*  540 */       float f = this.rotationYaw;
/*  541 */       float f1 = this.rotationPitch;
/*  542 */       super.updateRidden();
/*  543 */       this.prevCameraYaw = this.cameraYaw;
/*  544 */       this.cameraYaw = 0.0F;
/*  545 */       addMountedMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */       
/*  547 */       if (this.ridingEntity instanceof EntityPig) {
/*      */         
/*  549 */         this.rotationPitch = f1;
/*  550 */         this.rotationYaw = f;
/*  551 */         this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preparePlayerToSpawn() {
/*  562 */     setSize(0.6F, 1.8F);
/*  563 */     super.preparePlayerToSpawn();
/*  564 */     setHealth(getMaxHealth());
/*  565 */     this.deathTime = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {
/*  570 */     super.updateEntityActionState();
/*  571 */     updateArmSwingProgress();
/*  572 */     this.rotationYawHead = this.rotationYaw;
/*  573 */     this.rotationPitchHead = this.rotationPitch;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  582 */     if (this.flyToggleTimer > 0)
/*      */     {
/*  584 */       this.flyToggleTimer--;
/*      */     }
/*      */     
/*  587 */     if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getBoolean("naturalRegeneration")) {
/*      */       
/*  589 */       if (getHealth() < getMaxHealth() && this.ticksExisted % 20 == 0)
/*      */       {
/*  591 */         heal(1.0F);
/*      */       }
/*      */       
/*  594 */       if (this.foodStats.needFood() && this.ticksExisted % 10 == 0)
/*      */       {
/*  596 */         this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
/*      */       }
/*      */     } 
/*      */     
/*  600 */     this.inventory.decrementAnimations();
/*  601 */     this.prevCameraYaw = this.cameraYaw;
/*  602 */     super.onLivingUpdate();
/*  603 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*      */     
/*  605 */     if (!this.worldObj.isRemote)
/*      */     {
/*  607 */       iattributeinstance.setBaseValue(this.capabilities.getWalkSpeed());
/*      */     }
/*      */     
/*  610 */     this.jumpMovementFactor = this.speedInAir;
/*      */     
/*  612 */     if (isSprinting())
/*      */     {
/*  614 */       this.jumpMovementFactor = (float)(this.jumpMovementFactor + this.speedInAir * 0.3D);
/*      */     }
/*      */     
/*  617 */     setAIMoveSpeed((float)iattributeinstance.getAttributeValue());
/*  618 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  619 */     float f1 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);
/*      */     
/*  621 */     if (f > 0.1F)
/*      */     {
/*  623 */       f = 0.1F;
/*      */     }
/*      */     
/*  626 */     if (!this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  628 */       f = 0.0F;
/*      */     }
/*      */     
/*  631 */     if (this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  633 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  636 */     this.cameraYaw += (f - this.cameraYaw) * 0.4F;
/*  637 */     this.cameraPitch += (f1 - this.cameraPitch) * 0.8F;
/*      */     
/*  639 */     if (getHealth() > 0.0F && !isSpectator()) {
/*      */       
/*  641 */       AxisAlignedBB axisalignedbb = null;
/*      */       
/*  643 */       if (this.ridingEntity != null && !this.ridingEntity.isDead) {
/*      */         
/*  645 */         axisalignedbb = getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
/*      */       }
/*      */       else {
/*      */         
/*  649 */         axisalignedbb = getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
/*      */       } 
/*      */       
/*  652 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, axisalignedbb);
/*      */       
/*  654 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  656 */         Entity entity = list.get(i);
/*      */         
/*  658 */         if (!entity.isDead)
/*      */         {
/*  660 */           collideWithPlayer(entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void collideWithPlayer(Entity p_71044_1_) {
/*  668 */     p_71044_1_.onCollideWithPlayer(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getScore() {
/*  673 */     return this.dataWatcher.getWatchableObjectInt(18);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScore(int p_85040_1_) {
/*  681 */     this.dataWatcher.updateObject(18, Integer.valueOf(p_85040_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addScore(int p_85039_1_) {
/*  689 */     int i = getScore();
/*  690 */     this.dataWatcher.updateObject(18, Integer.valueOf(i + p_85039_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  698 */     super.onDeath(cause);
/*  699 */     setSize(0.2F, 0.2F);
/*  700 */     setPosition(this.posX, this.posY, this.posZ);
/*  701 */     this.motionY = 0.10000000149011612D;
/*      */     
/*  703 */     if (getName().equals("Notch"))
/*      */     {
/*  705 */       dropItem(new ItemStack(Items.apple, 1), true, false);
/*      */     }
/*      */     
/*  708 */     if (!this.worldObj.getGameRules().getBoolean("keepInventory"))
/*      */     {
/*  710 */       this.inventory.dropAllItems();
/*      */     }
/*      */     
/*  713 */     if (cause != null) {
/*      */       
/*  715 */       this.motionX = (-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
/*  716 */       this.motionZ = (-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
/*      */     }
/*      */     else {
/*      */       
/*  720 */       this.motionX = this.motionZ = 0.0D;
/*      */     } 
/*      */     
/*  723 */     triggerAchievement(StatList.deathsStat);
/*  724 */     func_175145_a(StatList.timeSinceDeathStat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  732 */     return "game.player.hurt";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  740 */     return "game.player.die";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToPlayerScore(Entity entityIn, int amount) {
/*  749 */     addScore(amount);
/*  750 */     Collection<ScoreObjective> collection = getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
/*      */     
/*  752 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/*  754 */       triggerAchievement(StatList.playerKillsStat);
/*  755 */       collection.addAll(getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
/*  756 */       collection.addAll(func_175137_e(entityIn));
/*      */     }
/*      */     else {
/*      */       
/*  760 */       triggerAchievement(StatList.mobKillsStat);
/*      */     } 
/*      */     
/*  763 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/*  765 */       Score score = getWorldScoreboard().getValueFromObjective(getName(), scoreobjective);
/*  766 */       score.func_96648_a();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<ScoreObjective> func_175137_e(Entity p_175137_1_) {
/*  772 */     ScorePlayerTeam scoreplayerteam = getWorldScoreboard().getPlayersTeam(getName());
/*      */     
/*  774 */     if (scoreplayerteam != null) {
/*      */       
/*  776 */       int i = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  778 */       if (i >= 0 && i < IScoreObjectiveCriteria.field_178793_i.length)
/*      */       {
/*  780 */         for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178793_i[i])) {
/*      */           
/*  782 */           Score score = getWorldScoreboard().getValueFromObjective(p_175137_1_.getName(), scoreobjective);
/*  783 */           score.func_96648_a();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  788 */     ScorePlayerTeam scoreplayerteam1 = getWorldScoreboard().getPlayersTeam(p_175137_1_.getName());
/*      */     
/*  790 */     if (scoreplayerteam1 != null) {
/*      */       
/*  792 */       int j = scoreplayerteam1.getChatFormat().getColorIndex();
/*      */       
/*  794 */       if (j >= 0 && j < IScoreObjectiveCriteria.field_178792_h.length)
/*      */       {
/*  796 */         return getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178792_h[j]);
/*      */       }
/*      */     } 
/*      */     
/*  800 */     return Lists.newArrayList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem dropOneItem(boolean dropAll) {
/*  808 */     return dropItem(this.inventory.decrStackSize(this.inventory.currentItem, (dropAll && this.inventory.getCurrentItem() != null) ? (this.inventory.getCurrentItem()).stackSize : 1), false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem dropPlayerItemWithRandomChoice(ItemStack itemStackIn, boolean unused) {
/*  816 */     return dropItem(itemStackIn, false, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem) {
/*  821 */     if (droppedItem == null)
/*      */     {
/*  823 */       return null;
/*      */     }
/*  825 */     if (droppedItem.stackSize == 0)
/*      */     {
/*  827 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  831 */     double d0 = this.posY - 0.30000001192092896D + getEyeHeight();
/*  832 */     EntityItem entityitem = new EntityItem(this.worldObj, this.posX, d0, this.posZ, droppedItem);
/*  833 */     entityitem.setPickupDelay(40);
/*      */     
/*  835 */     if (traceItem)
/*      */     {
/*  837 */       entityitem.setThrower(getName());
/*      */     }
/*      */     
/*  840 */     if (dropAround) {
/*      */       
/*  842 */       float f = this.rand.nextFloat() * 0.5F;
/*  843 */       float f1 = this.rand.nextFloat() * 3.1415927F * 2.0F;
/*  844 */       entityitem.motionX = (-MathHelper.sin(f1) * f);
/*  845 */       entityitem.motionZ = (MathHelper.cos(f1) * f);
/*  846 */       entityitem.motionY = 0.20000000298023224D;
/*      */     }
/*      */     else {
/*      */       
/*  850 */       float f2 = 0.3F;
/*  851 */       entityitem.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f2);
/*  852 */       entityitem.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f2);
/*  853 */       entityitem.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f2 + 0.1F);
/*  854 */       float f3 = this.rand.nextFloat() * 3.1415927F * 2.0F;
/*  855 */       f2 = 0.02F * this.rand.nextFloat();
/*  856 */       entityitem.motionX += Math.cos(f3) * f2;
/*  857 */       entityitem.motionY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/*  858 */       entityitem.motionZ += Math.sin(f3) * f2;
/*      */     } 
/*      */     
/*  861 */     joinEntityItemWithWorld(entityitem);
/*      */     
/*  863 */     if (traceItem)
/*      */     {
/*  865 */       triggerAchievement(StatList.dropStat);
/*      */     }
/*      */     
/*  868 */     return entityitem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void joinEntityItemWithWorld(EntityItem itemIn) {
/*  877 */     this.worldObj.spawnEntityInWorld((Entity)itemIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getToolDigEfficiency(Block p_180471_1_) {
/*  886 */     float f = this.inventory.getStrVsBlock(p_180471_1_);
/*      */     
/*  888 */     if (f > 1.0F) {
/*      */       
/*  890 */       int i = EnchantmentHelper.getEfficiencyModifier(this);
/*  891 */       ItemStack itemstack = this.inventory.getCurrentItem();
/*      */       
/*  893 */       if (i > 0 && itemstack != null)
/*      */       {
/*  895 */         f += (i * i + 1);
/*      */       }
/*      */     } 
/*      */     
/*  899 */     if (isPotionActive(Potion.digSpeed))
/*      */     {
/*  901 */       f *= 1.0F + (getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
/*      */     }
/*      */     
/*  904 */     if (isPotionActive(Potion.digSlowdown)) {
/*      */       
/*  906 */       float f1 = 1.0F;
/*      */       
/*  908 */       switch (getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
/*      */         
/*      */         case 0:
/*  911 */           f1 = 0.3F;
/*      */           break;
/*      */         
/*      */         case 1:
/*  915 */           f1 = 0.09F;
/*      */           break;
/*      */         
/*      */         case 2:
/*  919 */           f1 = 0.0027F;
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  924 */           f1 = 8.1E-4F;
/*      */           break;
/*      */       } 
/*  927 */       f *= f1;
/*      */     } 
/*      */     
/*  930 */     if (isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this))
/*      */     {
/*  932 */       f /= 5.0F;
/*      */     }
/*      */     
/*  935 */     if (!this.onGround)
/*      */     {
/*  937 */       f /= 5.0F;
/*      */     }
/*      */     
/*  940 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockToHarvest) {
/*  948 */     return this.inventory.canHeldItemHarvest(blockToHarvest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  956 */     super.readEntityFromNBT(tagCompund);
/*  957 */     this.entityUniqueID = getUUID(this.gameProfile);
/*  958 */     NBTTagList nbttaglist = tagCompund.getTagList("Inventory", 10);
/*  959 */     this.inventory.readFromNBT(nbttaglist);
/*  960 */     this.inventory.currentItem = tagCompund.getInteger("SelectedItemSlot");
/*  961 */     this.sleeping = tagCompund.getBoolean("Sleeping");
/*  962 */     this.sleepTimer = tagCompund.getShort("SleepTimer");
/*  963 */     this.experience = tagCompund.getFloat("XpP");
/*  964 */     this.experienceLevel = tagCompund.getInteger("XpLevel");
/*  965 */     this.experienceTotal = tagCompund.getInteger("XpTotal");
/*  966 */     this.xpSeed = tagCompund.getInteger("XpSeed");
/*      */     
/*  968 */     if (this.xpSeed == 0)
/*      */     {
/*  970 */       this.xpSeed = this.rand.nextInt();
/*      */     }
/*      */     
/*  973 */     setScore(tagCompund.getInteger("Score"));
/*      */     
/*  975 */     if (this.sleeping) {
/*      */       
/*  977 */       this.playerLocation = new BlockPos((Entity)this);
/*  978 */       wakeUpPlayer(true, true, false);
/*      */     } 
/*      */     
/*  981 */     if (tagCompund.hasKey("SpawnX", 99) && tagCompund.hasKey("SpawnY", 99) && tagCompund.hasKey("SpawnZ", 99)) {
/*      */       
/*  983 */       this.spawnChunk = new BlockPos(tagCompund.getInteger("SpawnX"), tagCompund.getInteger("SpawnY"), tagCompund.getInteger("SpawnZ"));
/*  984 */       this.spawnForced = tagCompund.getBoolean("SpawnForced");
/*      */     } 
/*      */     
/*  987 */     this.foodStats.readNBT(tagCompund);
/*  988 */     this.capabilities.readCapabilitiesFromNBT(tagCompund);
/*      */     
/*  990 */     if (tagCompund.hasKey("EnderItems", 9)) {
/*      */       
/*  992 */       NBTTagList nbttaglist1 = tagCompund.getTagList("EnderItems", 10);
/*  993 */       this.theInventoryEnderChest.loadInventoryFromNBT(nbttaglist1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 1002 */     super.writeEntityToNBT(tagCompound);
/* 1003 */     tagCompound.setTag("Inventory", (NBTBase)this.inventory.writeToNBT(new NBTTagList()));
/* 1004 */     tagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
/* 1005 */     tagCompound.setBoolean("Sleeping", this.sleeping);
/* 1006 */     tagCompound.setShort("SleepTimer", (short)this.sleepTimer);
/* 1007 */     tagCompound.setFloat("XpP", this.experience);
/* 1008 */     tagCompound.setInteger("XpLevel", this.experienceLevel);
/* 1009 */     tagCompound.setInteger("XpTotal", this.experienceTotal);
/* 1010 */     tagCompound.setInteger("XpSeed", this.xpSeed);
/* 1011 */     tagCompound.setInteger("Score", getScore());
/*      */     
/* 1013 */     if (this.spawnChunk != null) {
/*      */       
/* 1015 */       tagCompound.setInteger("SpawnX", this.spawnChunk.getX());
/* 1016 */       tagCompound.setInteger("SpawnY", this.spawnChunk.getY());
/* 1017 */       tagCompound.setInteger("SpawnZ", this.spawnChunk.getZ());
/* 1018 */       tagCompound.setBoolean("SpawnForced", this.spawnForced);
/*      */     } 
/*      */     
/* 1021 */     this.foodStats.writeNBT(tagCompound);
/* 1022 */     this.capabilities.writeCapabilitiesToNBT(tagCompound);
/* 1023 */     tagCompound.setTag("EnderItems", (NBTBase)this.theInventoryEnderChest.saveInventoryToNBT());
/* 1024 */     ItemStack itemstack = this.inventory.getCurrentItem();
/*      */     
/* 1026 */     if (itemstack != null && itemstack.getItem() != null)
/*      */     {
/* 1028 */       tagCompound.setTag("SelectedItem", (NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1037 */     if (isEntityInvulnerable(source))
/*      */     {
/* 1039 */       return false;
/*      */     }
/* 1041 */     if (this.capabilities.disableDamage && !source.canHarmInCreative())
/*      */     {
/* 1043 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1047 */     this.entityAge = 0;
/*      */     
/* 1049 */     if (getHealth() <= 0.0F)
/*      */     {
/* 1051 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1055 */     if (this.sleeping && !this.worldObj.isRemote)
/*      */     {
/* 1057 */       wakeUpPlayer(true, true, false);
/*      */     }
/*      */     
/* 1060 */     if (source.isDifficultyScaled()) {
/*      */       
/* 1062 */       if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
/*      */       {
/* 1064 */         amount = 0.0F;
/*      */       }
/*      */       
/* 1067 */       if (this.worldObj.getDifficulty() == EnumDifficulty.EASY)
/*      */       {
/* 1069 */         amount = amount / 2.0F + 1.0F;
/*      */       }
/*      */       
/* 1072 */       if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*      */       {
/* 1074 */         amount = amount * 3.0F / 2.0F;
/*      */       }
/*      */     } 
/*      */     
/* 1078 */     if (amount == 0.0F)
/*      */     {
/* 1080 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1084 */     Entity entity = source.getEntity();
/*      */     
/* 1086 */     if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null)
/*      */     {
/* 1088 */       entity = ((EntityArrow)entity).shootingEntity;
/*      */     }
/*      */     
/* 1091 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/* 1099 */     Team team = getTeam();
/* 1100 */     Team team1 = other.getTeam();
/* 1101 */     return (team == null) ? true : (!team.isSameTeam(team1) ? true : team.getAllowFriendlyFire());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void damageArmor(float p_70675_1_) {
/* 1106 */     this.inventory.damageArmor(p_70675_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/* 1114 */     return this.inventory.getTotalArmorValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getArmorVisibility() {
/* 1123 */     int i = 0;
/*      */     
/* 1125 */     for (ItemStack itemstack : this.inventory.armorInventory) {
/*      */       
/* 1127 */       if (itemstack != null)
/*      */       {
/* 1129 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 1133 */     return i / this.inventory.armorInventory.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1142 */     if (!isEntityInvulnerable(damageSrc)) {
/*      */       
/* 1144 */       if (!damageSrc.isUnblockable() && isBlocking() && damageAmount > 0.0F)
/*      */       {
/* 1146 */         damageAmount = (1.0F + damageAmount) * 0.5F;
/*      */       }
/*      */       
/* 1149 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1150 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1151 */       float f = damageAmount;
/* 1152 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1153 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1155 */       if (damageAmount != 0.0F) {
/*      */         
/* 1157 */         addExhaustion(damageSrc.getHungerDamage());
/* 1158 */         float f1 = getHealth();
/* 1159 */         setHealth(getHealth() - damageAmount);
/* 1160 */         getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
/*      */         
/* 1162 */         if (damageAmount < 3.4028235E37F)
/*      */         {
/* 1164 */           addStat(StatList.damageTakenStat, Math.round(damageAmount * 10.0F));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIBook(ItemStack bookStack) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactWith(Entity targetEntity) {
/* 1206 */     if (isSpectator()) {
/*      */       
/* 1208 */       if (targetEntity instanceof IInventory)
/*      */       {
/* 1210 */         displayGUIChest((IInventory)targetEntity);
/*      */       }
/*      */       
/* 1213 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1217 */     ItemStack itemstack = getCurrentEquippedItem();
/* 1218 */     ItemStack itemstack1 = (itemstack != null) ? itemstack.copy() : null;
/*      */     
/* 1220 */     if (!targetEntity.interactFirst(this)) {
/*      */       
/* 1222 */       if (itemstack != null && targetEntity instanceof EntityLivingBase) {
/*      */         
/* 1224 */         if (this.capabilities.isCreativeMode)
/*      */         {
/* 1226 */           itemstack = itemstack1;
/*      */         }
/*      */         
/* 1229 */         if (itemstack.interactWithEntity(this, (EntityLivingBase)targetEntity)) {
/*      */           
/* 1231 */           if (itemstack.stackSize <= 0 && !this.capabilities.isCreativeMode)
/*      */           {
/* 1233 */             destroyCurrentEquippedItem();
/*      */           }
/*      */           
/* 1236 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/* 1240 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1244 */     if (itemstack != null && itemstack == getCurrentEquippedItem())
/*      */     {
/* 1246 */       if (itemstack.stackSize <= 0 && !this.capabilities.isCreativeMode) {
/*      */         
/* 1248 */         destroyCurrentEquippedItem();
/*      */       }
/* 1250 */       else if (itemstack.stackSize < itemstack1.stackSize && this.capabilities.isCreativeMode) {
/*      */         
/* 1252 */         itemstack.stackSize = itemstack1.stackSize;
/*      */       } 
/*      */     }
/*      */     
/* 1256 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentEquippedItem() {
/* 1266 */     return this.inventory.getCurrentItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroyCurrentEquippedItem() {
/* 1274 */     this.inventory.setInventorySlotContents(this.inventory.currentItem, (ItemStack)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 1282 */     return -0.35D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1291 */     if (targetEntity.canAttackWithItem()) {
/*      */       
/* 1293 */       if (targetEntity != (Minecraft.getMinecraft()).thePlayer) EventManager.call((Event)new EventAttack(targetEntity)); 
/* 1294 */       if (!targetEntity.hitByEntity((Entity)this)) {
/*      */         
/* 1296 */         float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/* 1297 */         int i = 0;
/* 1298 */         float f1 = 0.0F;
/*      */         
/* 1300 */         if (targetEntity instanceof EntityLivingBase) {
/*      */           
/* 1302 */           f1 = EnchantmentHelper.getModifierForCreature(getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
/*      */         }
/*      */         else {
/*      */           
/* 1306 */           f1 = EnchantmentHelper.getModifierForCreature(getHeldItem(), EnumCreatureAttribute.UNDEFINED);
/*      */         } 
/*      */         
/* 1309 */         i += EnchantmentHelper.getKnockbackModifier(this);
/*      */         
/* 1311 */         if (isSprinting())
/*      */         {
/* 1313 */           i++;
/*      */         }
/*      */         
/* 1316 */         if (f > 0.0F || f1 > 0.0F) {
/*      */           
/* 1318 */           boolean flag = (this.fallDistance > 0.0F && !this.onGround && !isOnLadder() && !isInWater() && !isPotionActive(Potion.blindness) && this.ridingEntity == null && targetEntity instanceof EntityLivingBase);
/*      */           
/* 1320 */           if (flag && f > 0.0F)
/*      */           {
/* 1322 */             f *= 1.5F;
/*      */           }
/*      */           
/* 1325 */           f += f1;
/* 1326 */           boolean flag1 = false;
/* 1327 */           int j = EnchantmentHelper.getFireAspectModifier(this);
/*      */           
/* 1329 */           if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.isBurning()) {
/*      */             
/* 1331 */             flag1 = true;
/* 1332 */             targetEntity.setFire(1);
/*      */           } 
/*      */           
/* 1335 */           double d0 = targetEntity.motionX;
/* 1336 */           double d1 = targetEntity.motionY;
/* 1337 */           double d2 = targetEntity.motionZ;
/* 1338 */           boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f);
/*      */           
/* 1340 */           if (flag2) {
/*      */             EntityLivingBase entityLivingBase;
/* 1342 */             if (i > 0) {
/*      */               
/* 1344 */               targetEntity.addVelocity((-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F));
/* 1345 */               KillAura killAura = KillAura.getInstance;
/* 1346 */               AdvancedAura customAura = AdvancedAura.getInstance;
/* 1347 */               if (killAura.getTarget() != null) {
/* 1348 */                 if (!((Boolean)killAura.keepSprint.get()).booleanValue() || !((Boolean)customAura.keepSprintValue.get()).booleanValue()) {
/* 1349 */                   this.motionX *= 0.6D;
/* 1350 */                   this.motionZ *= 0.6D;
/*      */                 } 
/* 1352 */                 if (!Sprint.getInstance.isEnabled()) {
/* 1353 */                   setSprinting(false);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             
/* 1358 */             if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
/*      */               
/* 1360 */               ((EntityPlayerMP)targetEntity).playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(targetEntity));
/* 1361 */               targetEntity.velocityChanged = false;
/* 1362 */               targetEntity.motionX = d0;
/* 1363 */               targetEntity.motionY = d1;
/* 1364 */               targetEntity.motionZ = d2;
/*      */             } 
/*      */             
/* 1367 */             if (flag)
/*      */             {
/* 1369 */               onCriticalHit(targetEntity);
/*      */             }
/*      */             
/* 1372 */             if (f1 > 0.0F)
/*      */             {
/* 1374 */               onEnchantmentCritical(targetEntity);
/*      */             }
/*      */             
/* 1377 */             if (f >= 18.0F)
/*      */             {
/* 1379 */               triggerAchievement((StatBase)AchievementList.overkill);
/*      */             }
/*      */             
/* 1382 */             setLastAttacker(targetEntity);
/*      */             
/* 1384 */             if (targetEntity instanceof EntityLivingBase)
/*      */             {
/* 1386 */               EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, (Entity)this);
/*      */             }
/*      */             
/* 1389 */             EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
/* 1390 */             ItemStack itemstack = getCurrentEquippedItem();
/* 1391 */             Entity entity = targetEntity;
/*      */             
/* 1393 */             if (targetEntity instanceof EntityDragonPart) {
/*      */               
/* 1395 */               IEntityMultiPart ientitymultipart = ((EntityDragonPart)targetEntity).entityDragonObj;
/*      */               
/* 1397 */               if (ientitymultipart instanceof EntityLivingBase)
/*      */               {
/* 1399 */                 entityLivingBase = (EntityLivingBase)ientitymultipart;
/*      */               }
/*      */             } 
/*      */             
/* 1403 */             if (itemstack != null && entityLivingBase instanceof EntityLivingBase) {
/*      */               
/* 1405 */               itemstack.hitEntity(entityLivingBase, this);
/*      */               
/* 1407 */               if (itemstack.stackSize <= 0)
/*      */               {
/* 1409 */                 destroyCurrentEquippedItem();
/*      */               }
/*      */             } 
/*      */             
/* 1413 */             if (targetEntity instanceof EntityLivingBase) {
/*      */               
/* 1415 */               addStat(StatList.damageDealtStat, Math.round(f * 10.0F));
/*      */               
/* 1417 */               if (j > 0)
/*      */               {
/* 1419 */                 targetEntity.setFire(j << 2);
/*      */               }
/*      */             } 
/*      */             
/* 1423 */             addExhaustion(0.3F);
/*      */           }
/* 1425 */           else if (flag1) {
/*      */             
/* 1427 */             targetEntity.extinguish();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void respawnPlayer() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/* 1454 */     super.setDead();
/* 1455 */     this.inventoryContainer.onContainerClosed(this);
/*      */     
/* 1457 */     if (this.openContainer != null)
/*      */     {
/* 1459 */       this.openContainer.onContainerClosed(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1468 */     return (!this.sleeping && super.isEntityInsideOpaqueBlock());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUser() {
/* 1476 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1484 */     return this.gameProfile;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumStatus trySleep(BlockPos bedLocation) {
/* 1489 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1491 */       if (this.sleeping || !isEntityAlive())
/*      */       {
/* 1493 */         return EnumStatus.OTHER_PROBLEM;
/*      */       }
/*      */       
/* 1496 */       if (!this.worldObj.provider.isSurfaceWorld())
/*      */       {
/* 1498 */         return EnumStatus.NOT_POSSIBLE_HERE;
/*      */       }
/*      */       
/* 1501 */       if (this.worldObj.isDaytime())
/*      */       {
/* 1503 */         return EnumStatus.NOT_POSSIBLE_NOW;
/*      */       }
/*      */       
/* 1506 */       if (Math.abs(this.posX - bedLocation.getX()) > 3.0D || Math.abs(this.posY - bedLocation.getY()) > 2.0D || Math.abs(this.posZ - bedLocation.getZ()) > 3.0D)
/*      */       {
/* 1508 */         return EnumStatus.TOO_FAR_AWAY;
/*      */       }
/*      */       
/* 1511 */       double d0 = 8.0D;
/* 1512 */       double d1 = 5.0D;
/* 1513 */       List<EntityMob> list = this.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(bedLocation.getX() - d0, bedLocation.getY() - d1, bedLocation.getZ() - d0, bedLocation.getX() + d0, bedLocation.getY() + d1, bedLocation.getZ() + d0));
/*      */       
/* 1515 */       if (!list.isEmpty())
/*      */       {
/* 1517 */         return EnumStatus.NOT_SAFE;
/*      */       }
/*      */     } 
/*      */     
/* 1521 */     if (isRiding())
/*      */     {
/* 1523 */       mountEntity((Entity)null);
/*      */     }
/*      */     
/* 1526 */     setSize(0.2F, 0.2F);
/*      */     
/* 1528 */     if (this.worldObj.isBlockLoaded(bedLocation)) {
/*      */       
/* 1530 */       EnumFacing enumfacing = (EnumFacing)this.worldObj.getBlockState(bedLocation).getValue((IProperty)BlockDirectional.FACING);
/* 1531 */       float f = 0.5F;
/* 1532 */       float f1 = 0.5F;
/*      */       
/* 1534 */       switch (enumfacing) {
/*      */         
/*      */         case SOUTH:
/* 1537 */           f1 = 0.9F;
/*      */           break;
/*      */         
/*      */         case NORTH:
/* 1541 */           f1 = 0.1F;
/*      */           break;
/*      */         
/*      */         case WEST:
/* 1545 */           f = 0.1F;
/*      */           break;
/*      */         
/*      */         case EAST:
/* 1549 */           f = 0.9F;
/*      */           break;
/*      */       } 
/* 1552 */       func_175139_a(enumfacing);
/* 1553 */       setPosition((bedLocation.getX() + f), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + f1));
/*      */     }
/*      */     else {
/*      */       
/* 1557 */       setPosition((bedLocation.getX() + 0.5F), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1560 */     this.sleeping = true;
/* 1561 */     this.sleepTimer = 0;
/* 1562 */     this.playerLocation = bedLocation;
/* 1563 */     this.motionX = this.motionZ = this.motionY = 0.0D;
/*      */     
/* 1565 */     if (!this.worldObj.isRemote)
/*      */     {
/* 1567 */       this.worldObj.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1570 */     return EnumStatus.OK;
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175139_a(EnumFacing p_175139_1_) {
/* 1575 */     this.renderOffsetX = 0.0F;
/* 1576 */     this.renderOffsetZ = 0.0F;
/*      */     
/* 1578 */     switch (p_175139_1_) {
/*      */       
/*      */       case SOUTH:
/* 1581 */         this.renderOffsetZ = -1.8F;
/*      */         break;
/*      */       
/*      */       case NORTH:
/* 1585 */         this.renderOffsetZ = 1.8F;
/*      */         break;
/*      */       
/*      */       case WEST:
/* 1589 */         this.renderOffsetX = 1.8F;
/*      */         break;
/*      */       
/*      */       case EAST:
/* 1593 */         this.renderOffsetX = -1.8F;
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn) {
/* 1602 */     setSize(0.6F, 1.8F);
/* 1603 */     IBlockState iblockstate = this.worldObj.getBlockState(this.playerLocation);
/*      */     
/* 1605 */     if (this.playerLocation != null && iblockstate.getBlock() == Blocks.bed) {
/*      */       
/* 1607 */       this.worldObj.setBlockState(this.playerLocation, iblockstate.withProperty((IProperty)BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
/* 1608 */       BlockPos blockpos = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
/*      */       
/* 1610 */       if (blockpos == null)
/*      */       {
/* 1612 */         blockpos = this.playerLocation.up();
/*      */       }
/*      */       
/* 1615 */       setPosition((blockpos.getX() + 0.5F), (blockpos.getY() + 0.1F), (blockpos.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1618 */     this.sleeping = false;
/*      */     
/* 1620 */     if (!this.worldObj.isRemote && updateWorldFlag)
/*      */     {
/* 1622 */       this.worldObj.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1625 */     this.sleepTimer = immediately ? 0 : 100;
/*      */     
/* 1627 */     if (setSpawn)
/*      */     {
/* 1629 */       setSpawnPoint(this.playerLocation, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isInBed() {
/* 1635 */     return (this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BlockPos getBedSpawnLocation(World worldIn, BlockPos bedLocation, boolean forceSpawn) {
/* 1643 */     Block block = worldIn.getBlockState(bedLocation).getBlock();
/*      */     
/* 1645 */     if (block != Blocks.bed) {
/*      */       
/* 1647 */       if (!forceSpawn)
/*      */       {
/* 1649 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1653 */       boolean flag = block.canSpawnInBlock();
/* 1654 */       boolean flag1 = worldIn.getBlockState(bedLocation.up()).getBlock().canSpawnInBlock();
/* 1655 */       return (flag && flag1) ? bedLocation : null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1660 */     return BlockBed.getSafeExitLocation(worldIn, bedLocation, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBedOrientationInDegrees() {
/* 1669 */     if (this.playerLocation != null) {
/*      */       
/* 1671 */       EnumFacing enumfacing = (EnumFacing)this.worldObj.getBlockState(this.playerLocation).getValue((IProperty)BlockDirectional.FACING);
/*      */       
/* 1673 */       switch (enumfacing) {
/*      */         
/*      */         case SOUTH:
/* 1676 */           return 90.0F;
/*      */         
/*      */         case NORTH:
/* 1679 */           return 270.0F;
/*      */         
/*      */         case WEST:
/* 1682 */           return 0.0F;
/*      */         
/*      */         case EAST:
/* 1685 */           return 180.0F;
/*      */       } 
/*      */     
/*      */     } 
/* 1689 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 1697 */     return this.sleeping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerFullyAsleep() {
/* 1705 */     return (this.sleeping && this.sleepTimer >= 100);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSleepTimer() {
/* 1710 */     return this.sleepTimer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(IChatComponent chatComponent) {}
/*      */ 
/*      */   
/*      */   public BlockPos getBedLocation() {
/* 1719 */     return this.spawnChunk;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpawnForced() {
/* 1724 */     return this.spawnForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos, boolean forced) {
/* 1729 */     if (pos != null) {
/*      */       
/* 1731 */       this.spawnChunk = pos;
/* 1732 */       this.spawnForced = forced;
/*      */     }
/*      */     else {
/*      */       
/* 1736 */       this.spawnChunk = null;
/* 1737 */       this.spawnForced = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void triggerAchievement(StatBase achievementIn) {
/* 1746 */     addStat(achievementIn, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_175145_a(StatBase p_175145_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void jump() {
/* 1765 */     if (this == (Minecraft.getMinecraft()).thePlayer) {
/* 1766 */       EventJump event = new EventJump();
/* 1767 */       EventManager.call((Event)event);
/* 1768 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/*      */     } 
/* 1772 */     super.jump();
/* 1773 */     triggerAchievement(StatList.jumpStat);
/*      */     
/* 1775 */     if (isSprinting()) {
/*      */       
/* 1777 */       addExhaustion(0.8F);
/*      */     }
/*      */     else {
/*      */       
/* 1781 */       addExhaustion(0.2F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1790 */     double d0 = this.posX;
/* 1791 */     double d1 = this.posY;
/* 1792 */     double d2 = this.posZ;
/*      */     
/* 1794 */     if (this.capabilities.isFlying && this.ridingEntity == null) {
/*      */       
/* 1796 */       double d3 = this.motionY;
/* 1797 */       float f = this.jumpMovementFactor;
/* 1798 */       this.jumpMovementFactor = this.capabilities.getFlySpeed() * (isSprinting() ? 2 : true);
/* 1799 */       super.moveEntityWithHeading(strafe, forward);
/* 1800 */       this.motionY = d3 * 0.6D;
/* 1801 */       this.jumpMovementFactor = f;
/*      */     }
/*      */     else {
/*      */       
/* 1805 */       super.moveEntityWithHeading(strafe, forward);
/*      */     } 
/*      */     
/* 1808 */     addMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 1816 */     return (float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_) {
/* 1824 */     if (this.ridingEntity == null)
/*      */     {
/* 1826 */       if (isInsideOfMaterial(Material.water)) {
/*      */         
/* 1828 */         int i = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1830 */         if (i > 0)
/*      */         {
/* 1832 */           addStat(StatList.distanceDoveStat, i);
/* 1833 */           addExhaustion(0.015F * i * 0.01F);
/*      */         }
/*      */       
/* 1836 */       } else if (isInWater()) {
/*      */         
/* 1838 */         int j = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1840 */         if (j > 0)
/*      */         {
/* 1842 */           addStat(StatList.distanceSwumStat, j);
/* 1843 */           addExhaustion(0.015F * j * 0.01F);
/*      */         }
/*      */       
/* 1846 */       } else if (isOnLadder()) {
/*      */         
/* 1848 */         if (p_71000_3_ > 0.0D)
/*      */         {
/* 1850 */           addStat(StatList.distanceClimbedStat, (int)Math.round(p_71000_3_ * 100.0D));
/*      */         }
/*      */       }
/* 1853 */       else if (this.onGround) {
/*      */         
/* 1855 */         int k = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1857 */         if (k > 0) {
/*      */           
/* 1859 */           addStat(StatList.distanceWalkedStat, k);
/*      */           
/* 1861 */           if (isSprinting())
/*      */           {
/* 1863 */             addStat(StatList.distanceSprintedStat, k);
/* 1864 */             addExhaustion(0.099999994F * k * 0.01F);
/*      */           }
/*      */           else
/*      */           {
/* 1868 */             if (isSneaking())
/*      */             {
/* 1870 */               addStat(StatList.distanceCrouchedStat, k);
/*      */             }
/*      */             
/* 1873 */             addExhaustion(0.01F * k * 0.01F);
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/* 1879 */         int l = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1881 */         if (l > 25)
/*      */         {
/* 1883 */           addStat(StatList.distanceFlownStat, l);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMountedMovementStat(double p_71015_1_, double p_71015_3_, double p_71015_5_) {
/* 1894 */     if (this.ridingEntity != null) {
/*      */       
/* 1896 */       int i = Math.round(MathHelper.sqrt_double(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0F);
/*      */       
/* 1898 */       if (i > 0)
/*      */       {
/* 1900 */         if (this.ridingEntity instanceof net.minecraft.entity.item.EntityMinecart) {
/*      */           
/* 1902 */           addStat(StatList.distanceByMinecartStat, i);
/*      */           
/* 1904 */           if (this.startMinecartRidingCoordinate == null)
/*      */           {
/* 1906 */             this.startMinecartRidingCoordinate = new BlockPos((Entity)this);
/*      */           }
/* 1908 */           else if (this.startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0D)
/*      */           {
/* 1910 */             triggerAchievement((StatBase)AchievementList.onARail);
/*      */           }
/*      */         
/* 1913 */         } else if (this.ridingEntity instanceof net.minecraft.entity.item.EntityBoat) {
/*      */           
/* 1915 */           addStat(StatList.distanceByBoatStat, i);
/*      */         }
/* 1917 */         else if (this.ridingEntity instanceof EntityPig) {
/*      */           
/* 1919 */           addStat(StatList.distanceByPigStat, i);
/*      */         }
/* 1921 */         else if (this.ridingEntity instanceof EntityHorse) {
/*      */           
/* 1923 */           addStat(StatList.distanceByHorseStat, i);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1931 */     if (!this.capabilities.allowFlying) {
/*      */       
/* 1933 */       if (distance >= 2.0F)
/*      */       {
/* 1935 */         addStat(StatList.distanceFallenStat, (int)Math.round(distance * 100.0D));
/*      */       }
/*      */       
/* 1938 */       super.fall(distance, damageMultiplier);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1947 */     if (!isSpectator())
/*      */     {
/* 1949 */       super.resetHeight();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getFallSoundString(int damageValue) {
/* 1955 */     return (damageValue > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {
/* 1963 */     if (entityLivingIn instanceof net.minecraft.entity.monster.IMob)
/*      */     {
/* 1965 */       triggerAchievement((StatBase)AchievementList.killEnemy);
/*      */     }
/*      */     
/* 1968 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID((Entity)entityLivingIn)));
/*      */     
/* 1970 */     if (entitylist$entityegginfo != null)
/*      */     {
/* 1972 */       triggerAchievement(entitylist$entityegginfo.field_151512_d);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 1981 */     if (!this.capabilities.isFlying)
/*      */     {
/* 1983 */       super.setInWeb();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentArmor(int slotIn) {
/* 1989 */     return this.inventory.armorItemInSlot(slotIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperience(int amount) {
/* 1997 */     addScore(amount);
/* 1998 */     int i = Integer.MAX_VALUE - this.experienceTotal;
/*      */     
/* 2000 */     if (amount > i)
/*      */     {
/* 2002 */       amount = i;
/*      */     }
/*      */     
/* 2005 */     this.experience += amount / xpBarCap();
/*      */     
/* 2007 */     for (this.experienceTotal += amount; this.experience >= 1.0F; this.experience /= xpBarCap()) {
/*      */       
/* 2009 */       this.experience = (this.experience - 1.0F) * xpBarCap();
/* 2010 */       addExperienceLevel(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getXPSeed() {
/* 2016 */     return this.xpSeed;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeExperienceLevel(int levels) {
/* 2021 */     this.experienceLevel -= levels;
/*      */     
/* 2023 */     if (this.experienceLevel < 0) {
/*      */       
/* 2025 */       this.experienceLevel = 0;
/* 2026 */       this.experience = 0.0F;
/* 2027 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2030 */     this.xpSeed = this.rand.nextInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/* 2038 */     this.experienceLevel += levels;
/*      */     
/* 2040 */     if (this.experienceLevel < 0) {
/*      */       
/* 2042 */       this.experienceLevel = 0;
/* 2043 */       this.experience = 0.0F;
/* 2044 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2047 */     if (levels > 0 && this.experienceLevel % 5 == 0 && this.lastXPSound < this.ticksExisted - 100.0F) {
/*      */       
/* 2049 */       float f = (this.experienceLevel > 30) ? 1.0F : (this.experienceLevel / 30.0F);
/* 2050 */       this.worldObj.playSoundAtEntity((Entity)this, "random.levelup", f * 0.75F, 1.0F);
/* 2051 */       this.lastXPSound = this.ticksExisted;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int xpBarCap() {
/* 2061 */     return (this.experienceLevel >= 30) ? (112 + (this.experienceLevel - 30) * 9) : ((this.experienceLevel >= 15) ? (37 + (this.experienceLevel - 15) * 5) : (7 + (this.experienceLevel << 1)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExhaustion(float p_71020_1_) {
/* 2069 */     if (!this.capabilities.disableDamage)
/*      */     {
/* 2071 */       if (!this.worldObj.isRemote)
/*      */       {
/* 2073 */         this.foodStats.addExhaustion(p_71020_1_);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FoodStats getFoodStats() {
/* 2083 */     return this.foodStats;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEat(boolean ignoreHunger) {
/* 2088 */     return ((ignoreHunger || this.foodStats.needFood()) && !this.capabilities.disableDamage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldHeal() {
/* 2096 */     return (getHealth() > 0.0F && getHealth() < getMaxHealth());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemInUse(ItemStack stack, int duration) {
/* 2104 */     if (stack != this.itemInUse) {
/*      */       
/* 2106 */       this.itemInUse = stack;
/* 2107 */       this.itemInUseCount = duration;
/*      */       
/* 2109 */       if (!this.worldObj.isRemote)
/*      */       {
/* 2111 */         setEating(true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAllowEdit() {
/* 2118 */     return this.capabilities.allowEdit;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlayerEdit(BlockPos p_175151_1_, EnumFacing p_175151_2_, ItemStack p_175151_3_) {
/* 2123 */     if (this.capabilities.allowEdit)
/*      */     {
/* 2125 */       return true;
/*      */     }
/* 2127 */     if (p_175151_3_ == null)
/*      */     {
/* 2129 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2133 */     BlockPos blockpos = p_175151_1_.offset(p_175151_2_.getOpposite());
/* 2134 */     Block block = this.worldObj.getBlockState(blockpos).getBlock();
/* 2135 */     return (p_175151_3_.canPlaceOn(block) || p_175151_3_.canEditBlocks());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/* 2144 */     if (this.worldObj.getGameRules().getBoolean("keepInventory"))
/*      */     {
/* 2146 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2150 */     int i = this.experienceLevel * 7;
/* 2151 */     return (i > 100) ? 100 : i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/* 2160 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2165 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clonePlayer(EntityPlayer oldPlayer, boolean respawnFromEnd) {
/* 2174 */     if (respawnFromEnd) {
/*      */       
/* 2176 */       this.inventory.copyInventory(oldPlayer.inventory);
/* 2177 */       setHealth(oldPlayer.getHealth());
/* 2178 */       this.foodStats = oldPlayer.foodStats;
/* 2179 */       this.experienceLevel = oldPlayer.experienceLevel;
/* 2180 */       this.experienceTotal = oldPlayer.experienceTotal;
/* 2181 */       this.experience = oldPlayer.experience;
/* 2182 */       setScore(oldPlayer.getScore());
/* 2183 */       this.lastPortalPos = oldPlayer.lastPortalPos;
/* 2184 */       this.lastPortalVec = oldPlayer.lastPortalVec;
/* 2185 */       this.teleportDirection = oldPlayer.teleportDirection;
/*      */     }
/* 2187 */     else if (this.worldObj.getGameRules().getBoolean("keepInventory")) {
/*      */       
/* 2189 */       this.inventory.copyInventory(oldPlayer.inventory);
/* 2190 */       this.experienceLevel = oldPlayer.experienceLevel;
/* 2191 */       this.experienceTotal = oldPlayer.experienceTotal;
/* 2192 */       this.experience = oldPlayer.experience;
/* 2193 */       setScore(oldPlayer.getScore());
/*      */     } 
/*      */     
/* 2196 */     this.xpSeed = oldPlayer.xpSeed;
/* 2197 */     this.theInventoryEnderChest = oldPlayer.theInventoryEnderChest;
/* 2198 */     getDataWatcher().updateObject(10, Byte.valueOf(oldPlayer.getDataWatcher().getWatchableObjectByte(10)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 2207 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameType) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 2229 */     return this.gameProfile.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InventoryEnderChest getInventoryEnderChest() {
/* 2237 */     return this.theInventoryEnderChest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getEquipmentInSlot(int slotIn) {
/* 2245 */     return (slotIn == 0) ? this.inventory.getCurrentItem() : this.inventory.armorInventory[slotIn - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItem() {
/* 2253 */     return this.inventory.getCurrentItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 2261 */     this.inventory.armorInventory[slotIn] = stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 2271 */     if (!isInvisible())
/*      */     {
/* 2273 */       return false;
/*      */     }
/* 2275 */     if (player.isSpectator())
/*      */     {
/* 2277 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2281 */     Team team = getTeam();
/* 2282 */     return (team == null || player == null || player.getTeam() != team || !team.getSeeFriendlyInvisiblesEnabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isSpectator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/* 2296 */     return this.inventory.armorInventory;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2301 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */   
/*      */   public Scoreboard getWorldScoreboard() {
/* 2306 */     return this.worldObj.getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 2311 */     return (Team)getWorldScoreboard().getPlayersTeam(getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 2319 */     ChatComponentText chatComponentText = new ChatComponentText(ScorePlayerTeam.formatPlayerName(getTeam(), getName()));
/* 2320 */     chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + getName() + " "));
/* 2321 */     chatComponentText.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 2322 */     chatComponentText.getChatStyle().setInsertion(getName());
/* 2323 */     return (IChatComponent)chatComponentText;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 2328 */     float f = 1.62F;
/*      */     
/* 2330 */     if (this.sleeping)
/*      */     {
/* 2332 */       f = 0.2F;
/*      */     }
/*      */     
/* 2335 */     if (isSneaking())
/*      */     {
/* 2337 */       f -= 0.08F;
/*      */     }
/*      */     
/* 2340 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 2345 */     if (amount < 0.0F)
/*      */     {
/* 2347 */       amount = 0.0F;
/*      */     }
/*      */     
/* 2350 */     getDataWatcher().updateObject(17, Float.valueOf(amount));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 2355 */     return getDataWatcher().getWatchableObjectFloat(17);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UUID getUUID(GameProfile profile) {
/* 2363 */     UUID uuid = profile.getId();
/*      */     
/* 2365 */     if (uuid == null)
/*      */     {
/* 2367 */       uuid = getOfflineUUID(profile.getName());
/*      */     }
/*      */     
/* 2370 */     return uuid;
/*      */   }
/*      */ 
/*      */   
/*      */   public static UUID getOfflineUUID(String username) {
/* 2375 */     return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canOpen(LockCode code) {
/* 2383 */     if (code.isEmpty())
/*      */     {
/* 2385 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2389 */     ItemStack itemstack = getCurrentEquippedItem();
/* 2390 */     return (itemstack != null && itemstack.hasDisplayName()) ? itemstack.getDisplayName().equals(code.getLock()) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWearing(EnumPlayerModelParts p_175148_1_) {
/* 2396 */     return ((getDataWatcher().getWatchableObjectByte(10) & p_175148_1_.getPartMask()) == p_175148_1_.getPartMask());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2404 */     return (MinecraftServer.getServer()).worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 2409 */     if (inventorySlot >= 0 && inventorySlot < this.inventory.mainInventory.length) {
/*      */       
/* 2411 */       this.inventory.setInventorySlotContents(inventorySlot, itemStackIn);
/* 2412 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2416 */     int i = inventorySlot - 100;
/*      */     
/* 2418 */     if (i >= 0 && i < this.inventory.armorInventory.length) {
/*      */       
/* 2420 */       int k = i + 1;
/*      */       
/* 2422 */       if (itemStackIn != null && itemStackIn.getItem() != null)
/*      */       {
/* 2424 */         if (itemStackIn.getItem() instanceof net.minecraft.item.ItemArmor) {
/*      */           
/* 2426 */           if (EntityLiving.getArmorPosition(itemStackIn) != k)
/*      */           {
/* 2428 */             return false;
/*      */           }
/*      */         }
/* 2431 */         else if (k != 4 || (itemStackIn.getItem() != Items.skull && !(itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock))) {
/*      */           
/* 2433 */           return false;
/*      */         } 
/*      */       }
/*      */       
/* 2437 */       this.inventory.setInventorySlotContents(i + this.inventory.mainInventory.length, itemStackIn);
/* 2438 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2442 */     int j = inventorySlot - 200;
/*      */     
/* 2444 */     if (j >= 0 && j < this.theInventoryEnderChest.getSizeInventory()) {
/*      */       
/* 2446 */       this.theInventoryEnderChest.setInventorySlotContents(j, itemStackIn);
/* 2447 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2451 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasReducedDebug() {
/* 2462 */     return this.hasReducedDebug;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReducedDebug(boolean reducedDebug) {
/* 2467 */     this.hasReducedDebug = reducedDebug;
/*      */   }
/*      */   
/*      */   public void setItemInUseCount(int count) {
/* 2471 */     this.itemInUseCount = count;
/*      */   }
/*      */   
/*      */   public double getSpeed() {
/* 2475 */     double motionX = this.lastTickPosX - this.posX;
/*      */     
/* 2477 */     double motionZ = this.lastTickPosZ - this.posZ;
/* 2478 */     return Math.sqrt(motionX * motionX + motionZ * motionZ);
/*      */   }
/*      */   
/*      */   public double getMotionY() {
/* 2482 */     return this.lastTickPosY - this.posY;
/*      */   }
/*      */   
/*      */   public double getBaseMoveSpeed() {
/* 2486 */     double baseSpeed = 0.2873D;
/* 2487 */     if (isPotionActive(Potion.moveSpeed)) {
/* 2488 */       int amplifier = getActivePotionEffect(Potion.moveSpeed).getAmplifier();
/* 2489 */       baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
/*      */     } 
/* 2491 */     return baseSpeed;
/*      */   }
/*      */ 
/*      */   
/*      */   public enum EnumChatVisibility
/*      */   {
/* 2497 */     FULL(0, "options.chat.visibility.full"),
/* 2498 */     SYSTEM(1, "options.chat.visibility.system"),
/* 2499 */     HIDDEN(2, "options.chat.visibility.hidden");
/*      */     
/* 2501 */     private static final EnumChatVisibility[] ID_LOOKUP = new EnumChatVisibility[(values()).length];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int chatVisibility;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final String resourceKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 2527 */       for (EnumChatVisibility entityplayer$enumchatvisibility : values())
/*      */       {
/* 2529 */         ID_LOOKUP[entityplayer$enumchatvisibility.chatVisibility] = entityplayer$enumchatvisibility;
/*      */       }
/*      */     } EnumChatVisibility(int id, String resourceKey) { this.chatVisibility = id;
/*      */       this.resourceKey = resourceKey; } public int getChatVisibility() { return this.chatVisibility; } public static EnumChatVisibility getEnumChatVisibility(int id) {
/*      */       return ID_LOOKUP[id % ID_LOOKUP.length];
/*      */     } public String getResourceKey() {
/*      */       return this.resourceKey;
/* 2536 */     } } public enum EnumStatus { OK,
/* 2537 */     NOT_POSSIBLE_HERE,
/* 2538 */     NOT_POSSIBLE_NOW,
/* 2539 */     TOO_FAR_AWAY,
/* 2540 */     OTHER_PROBLEM,
/* 2541 */     NOT_SAFE; }
/*      */ 
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\player\EntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */