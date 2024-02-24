/*      */ package net.minecraft.entity.passive;
/*      */ import com.google.common.base.Predicate;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityAgeable;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IEntityLivingData;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.EntityAIBase;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.AnimalChest;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ 
/*      */ public class EntityHorse extends EntityAnimal implements IInvBasic {
/*   30 */   private static final Predicate<Entity> horseBreedingSelector = new Predicate<Entity>()
/*      */     {
/*      */       public boolean apply(Entity p_apply_1_)
/*      */       {
/*   34 */         return (p_apply_1_ instanceof EntityHorse && ((EntityHorse)p_apply_1_).isBreeding());
/*      */       }
/*      */     };
/*   37 */   private static final IAttribute horseJumpStrength = (IAttribute)(new RangedAttribute((IAttribute)null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);
/*   38 */   private static final String[] horseArmorTextures = new String[] { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
/*   39 */   private static final String[] HORSE_ARMOR_TEXTURES_ABBR = new String[] { "", "meo", "goo", "dio" };
/*   40 */   private static final int[] armorValues = new int[] { 0, 5, 7, 11 };
/*   41 */   private static final String[] horseTextures = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
/*   42 */   private static final String[] HORSE_TEXTURES_ABBR = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
/*   43 */   private static final String[] horseMarkingTextures = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
/*   44 */   private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
/*      */   
/*      */   private int eatingHaystackCounter;
/*      */   
/*      */   private int openMouthCounter;
/*      */   
/*      */   private int jumpRearingCounter;
/*      */   
/*      */   public int field_110278_bp;
/*      */   
/*      */   public int field_110279_bq;
/*      */   
/*      */   protected boolean horseJumping;
/*      */   private AnimalChest horseChest;
/*      */   private boolean hasReproduced;
/*      */   protected int temper;
/*      */   protected float jumpPower;
/*      */   private boolean field_110294_bI;
/*      */   private float headLean;
/*      */   private float prevHeadLean;
/*      */   private float rearingAmount;
/*      */   private float prevRearingAmount;
/*      */   private float mouthOpenness;
/*      */   private float prevMouthOpenness;
/*      */   private int gallopTime;
/*      */   private String texturePrefix;
/*   70 */   private final String[] horseTexturesArray = new String[3];
/*      */   
/*      */   private boolean field_175508_bO = false;
/*      */   
/*      */   public EntityHorse(World worldIn) {
/*   75 */     super(worldIn);
/*   76 */     setSize(1.4F, 1.6F);
/*   77 */     this.isImmuneToFire = false;
/*   78 */     setChested(false);
/*   79 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*   80 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*   81 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.2D));
/*   82 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIRunAroundLikeCrazy(this, 1.2D));
/*   83 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*   84 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.0D));
/*   85 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.7D));
/*   86 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*   87 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*   88 */     initHorseChest();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*   93 */     super.entityInit();
/*   94 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*   95 */     this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
/*   96 */     this.dataWatcher.addObject(20, Integer.valueOf(0));
/*   97 */     this.dataWatcher.addObject(21, String.valueOf(""));
/*   98 */     this.dataWatcher.addObject(22, Integer.valueOf(0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseType(int type) {
/*  103 */     this.dataWatcher.updateObject(19, Byte.valueOf((byte)type));
/*  104 */     resetTexturePrefix();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHorseType() {
/*  112 */     return this.dataWatcher.getWatchableObjectByte(19);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseVariant(int variant) {
/*  117 */     this.dataWatcher.updateObject(20, Integer.valueOf(variant));
/*  118 */     resetTexturePrefix();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHorseVariant() {
/*  123 */     return this.dataWatcher.getWatchableObjectInt(20);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  131 */     if (hasCustomName())
/*      */     {
/*  133 */       return getCustomNameTag();
/*      */     }
/*      */ 
/*      */     
/*  137 */     int i = getHorseType();
/*      */     
/*  139 */     switch (i) {
/*      */ 
/*      */       
/*      */       default:
/*  143 */         return StatCollector.translateToLocal("entity.horse.name");
/*      */       
/*      */       case 1:
/*  146 */         return StatCollector.translateToLocal("entity.donkey.name");
/*      */       
/*      */       case 2:
/*  149 */         return StatCollector.translateToLocal("entity.mule.name");
/*      */       
/*      */       case 3:
/*  152 */         return StatCollector.translateToLocal("entity.zombiehorse.name");
/*      */       case 4:
/*      */         break;
/*  155 */     }  return StatCollector.translateToLocal("entity.skeletonhorse.name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getHorseWatchableBoolean(int p_110233_1_) {
/*  162 */     return ((this.dataWatcher.getWatchableObjectInt(16) & p_110233_1_) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setHorseWatchableBoolean(int p_110208_1_, boolean p_110208_2_) {
/*  167 */     int i = this.dataWatcher.getWatchableObjectInt(16);
/*      */     
/*  169 */     if (p_110208_2_) {
/*      */       
/*  171 */       this.dataWatcher.updateObject(16, Integer.valueOf(i | p_110208_1_));
/*      */     }
/*      */     else {
/*      */       
/*  175 */       this.dataWatcher.updateObject(16, Integer.valueOf(i & (p_110208_1_ ^ 0xFFFFFFFF)));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAdultHorse() {
/*  181 */     return !isChild();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTame() {
/*  186 */     return getHorseWatchableBoolean(2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_110253_bW() {
/*  191 */     return isAdultHorse();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOwnerId() {
/*  199 */     return this.dataWatcher.getWatchableObjectString(21);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOwnerId(String id) {
/*  204 */     this.dataWatcher.updateObject(21, id);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getHorseSize() {
/*  209 */     return 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScaleForAge(boolean p_98054_1_) {
/*  217 */     if (p_98054_1_) {
/*      */       
/*  219 */       setScale(getHorseSize());
/*      */     }
/*      */     else {
/*      */       
/*  223 */       setScale(1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHorseJumping() {
/*  229 */     return this.horseJumping;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseTamed(boolean tamed) {
/*  234 */     setHorseWatchableBoolean(2, tamed);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseJumping(boolean jumping) {
/*  239 */     this.horseJumping = jumping;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean allowLeashing() {
/*  244 */     return (!isUndead() && super.allowLeashing());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_142017_o(float p_142017_1_) {
/*  249 */     if (p_142017_1_ > 6.0F && isEatingHaystack())
/*      */     {
/*  251 */       setEatingHaystack(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isChested() {
/*  257 */     return getHorseWatchableBoolean(8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHorseArmorIndexSynced() {
/*  265 */     return this.dataWatcher.getWatchableObjectInt(22);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getHorseArmorIndex(ItemStack itemStackIn) {
/*  273 */     if (itemStackIn == null)
/*      */     {
/*  275 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  279 */     Item item = itemStackIn.getItem();
/*  280 */     return (item == Items.iron_horse_armor) ? 1 : ((item == Items.golden_horse_armor) ? 2 : ((item == Items.diamond_horse_armor) ? 3 : 0));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEatingHaystack() {
/*  286 */     return getHorseWatchableBoolean(32);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRearing() {
/*  291 */     return getHorseWatchableBoolean(64);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBreeding() {
/*  296 */     return getHorseWatchableBoolean(16);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getHasReproduced() {
/*  301 */     return this.hasReproduced;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHorseArmorStack(ItemStack itemStackIn) {
/*  309 */     this.dataWatcher.updateObject(22, Integer.valueOf(getHorseArmorIndex(itemStackIn)));
/*  310 */     resetTexturePrefix();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBreeding(boolean breeding) {
/*  315 */     setHorseWatchableBoolean(16, breeding);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChested(boolean chested) {
/*  320 */     setHorseWatchableBoolean(8, chested);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHasReproduced(boolean hasReproducedIn) {
/*  325 */     this.hasReproduced = hasReproducedIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseSaddled(boolean saddled) {
/*  330 */     setHorseWatchableBoolean(4, saddled);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTemper() {
/*  335 */     return this.temper;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTemper(int temperIn) {
/*  340 */     this.temper = temperIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int increaseTemper(int p_110198_1_) {
/*  345 */     int i = MathHelper.clamp_int(this.temper + p_110198_1_, 0, getMaxTemper());
/*  346 */     this.temper = i;
/*  347 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  355 */     Entity entity = source.getEntity();
/*  356 */     return (this.riddenByEntity != null && this.riddenByEntity.equals(entity)) ? false : super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/*  364 */     return armorValues[getHorseArmorIndexSynced()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/*  372 */     return (this.riddenByEntity == null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean prepareChunkForSpawn() {
/*  377 */     int i = MathHelper.floor_double(this.posX);
/*  378 */     int j = MathHelper.floor_double(this.posZ);
/*  379 */     this.worldObj.getBiomeGenForCoords(new BlockPos(i, 0, j));
/*  380 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void dropChests() {
/*  385 */     if (!this.worldObj.isRemote && isChested()) {
/*      */       
/*  387 */       dropItem(Item.getItemFromBlock((Block)Blocks.chest), 1);
/*  388 */       setChested(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_110266_cB() {
/*  394 */     openHorseMouth();
/*      */     
/*  396 */     if (!isSilent())
/*      */     {
/*  398 */       this.worldObj.playSoundAtEntity((Entity)this, "eating", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/*  404 */     if (distance > 1.0F)
/*      */     {
/*  406 */       playSound("mob.horse.land", 0.4F, 1.0F);
/*      */     }
/*      */     
/*  409 */     int i = MathHelper.ceiling_float_int((distance * 0.5F - 3.0F) * damageMultiplier);
/*      */     
/*  411 */     if (i > 0) {
/*      */       
/*  413 */       attackEntityFrom(DamageSource.fall, i);
/*      */       
/*  415 */       if (this.riddenByEntity != null)
/*      */       {
/*  417 */         this.riddenByEntity.attackEntityFrom(DamageSource.fall, i);
/*      */       }
/*      */       
/*  420 */       Block block = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - this.prevRotationYaw, this.posZ)).getBlock();
/*      */       
/*  422 */       if (block.getMaterial() != Material.air && !isSilent()) {
/*      */         
/*  424 */         Block.SoundType block$soundtype = block.stepSound;
/*  425 */         this.worldObj.playSoundAtEntity((Entity)this, block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.5F, block$soundtype.getFrequency() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getChestSize() {
/*  435 */     int i = getHorseType();
/*  436 */     return (!isChested() || (i != 1 && i != 2)) ? 2 : 17;
/*      */   }
/*      */ 
/*      */   
/*      */   private void initHorseChest() {
/*  441 */     AnimalChest animalchest = this.horseChest;
/*  442 */     this.horseChest = new AnimalChest("HorseChest", getChestSize());
/*  443 */     this.horseChest.setCustomName(getName());
/*      */     
/*  445 */     if (animalchest != null) {
/*      */       
/*  447 */       animalchest.removeInventoryChangeListener(this);
/*  448 */       int i = Math.min(animalchest.getSizeInventory(), this.horseChest.getSizeInventory());
/*      */       
/*  450 */       for (int j = 0; j < i; j++) {
/*      */         
/*  452 */         ItemStack itemstack = animalchest.getStackInSlot(j);
/*      */         
/*  454 */         if (itemstack != null)
/*      */         {
/*  456 */           this.horseChest.setInventorySlotContents(j, itemstack.copy());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  461 */     this.horseChest.addInventoryChangeListener(this);
/*  462 */     updateHorseSlots();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateHorseSlots() {
/*  470 */     if (!this.worldObj.isRemote) {
/*      */       
/*  472 */       setHorseSaddled((this.horseChest.getStackInSlot(0) != null));
/*      */       
/*  474 */       if (canWearArmor())
/*      */       {
/*  476 */         setHorseArmorStack(this.horseChest.getStackInSlot(1));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInventoryChanged(InventoryBasic p_76316_1_) {
/*  486 */     int i = getHorseArmorIndexSynced();
/*  487 */     boolean flag = isHorseSaddled();
/*  488 */     updateHorseSlots();
/*      */     
/*  490 */     if (this.ticksExisted > 20) {
/*      */       
/*  492 */       if (i == 0 && i != getHorseArmorIndexSynced()) {
/*      */         
/*  494 */         playSound("mob.horse.armor", 0.5F, 1.0F);
/*      */       }
/*  496 */       else if (i != getHorseArmorIndexSynced()) {
/*      */         
/*  498 */         playSound("mob.horse.armor", 0.5F, 1.0F);
/*      */       } 
/*      */       
/*  501 */       if (!flag && isHorseSaddled())
/*      */       {
/*  503 */         playSound("mob.horse.leather", 0.5F, 1.0F);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnHere() {
/*  513 */     prepareChunkForSpawn();
/*  514 */     return super.getCanSpawnHere();
/*      */   }
/*      */ 
/*      */   
/*      */   protected EntityHorse getClosestHorse(Entity entityIn, double distance) {
/*  519 */     double d0 = Double.MAX_VALUE;
/*  520 */     Entity entity = null;
/*      */     
/*  522 */     for (Entity entity1 : this.worldObj.getEntitiesInAABBexcluding(entityIn, entityIn.getEntityBoundingBox().addCoord(distance, distance, distance), horseBreedingSelector)) {
/*      */       
/*  524 */       double d1 = entity1.getDistanceSq(entityIn.posX, entityIn.posY, entityIn.posZ);
/*      */       
/*  526 */       if (d1 < d0) {
/*      */         
/*  528 */         entity = entity1;
/*  529 */         d0 = d1;
/*      */       } 
/*      */     } 
/*      */     
/*  533 */     return (EntityHorse)entity;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getHorseJumpStrength() {
/*  538 */     return getEntityAttribute(horseJumpStrength).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  546 */     openHorseMouth();
/*  547 */     int i = getHorseType();
/*  548 */     return (i == 3) ? "mob.horse.zombie.death" : ((i == 4) ? "mob.horse.skeleton.death" : ((i != 1 && i != 2) ? "mob.horse.death" : "mob.horse.donkey.death"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Item getDropItem() {
/*  553 */     boolean flag = (this.rand.nextInt(4) == 0);
/*  554 */     int i = getHorseType();
/*  555 */     return (i == 4) ? Items.bone : ((i == 3) ? (flag ? null : Items.rotten_flesh) : Items.leather);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  563 */     openHorseMouth();
/*      */     
/*  565 */     if (this.rand.nextInt(3) == 0)
/*      */     {
/*  567 */       makeHorseRear();
/*      */     }
/*      */     
/*  570 */     int i = getHorseType();
/*  571 */     return (i == 3) ? "mob.horse.zombie.hit" : ((i == 4) ? "mob.horse.skeleton.hit" : ((i != 1 && i != 2) ? "mob.horse.hit" : "mob.horse.donkey.hit"));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHorseSaddled() {
/*  576 */     return getHorseWatchableBoolean(4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLivingSound() {
/*  584 */     openHorseMouth();
/*      */     
/*  586 */     if (this.rand.nextInt(10) == 0 && !isMovementBlocked())
/*      */     {
/*  588 */       makeHorseRear();
/*      */     }
/*      */     
/*  591 */     int i = getHorseType();
/*  592 */     return (i == 3) ? "mob.horse.zombie.idle" : ((i == 4) ? "mob.horse.skeleton.idle" : ((i != 1 && i != 2) ? "mob.horse.idle" : "mob.horse.donkey.idle"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getAngrySoundName() {
/*  597 */     openHorseMouth();
/*  598 */     makeHorseRear();
/*  599 */     int i = getHorseType();
/*  600 */     return (i != 3 && i != 4) ? ((i != 1 && i != 2) ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  605 */     Block.SoundType block$soundtype = blockIn.stepSound;
/*      */     
/*  607 */     if (this.worldObj.getBlockState(pos.up()).getBlock() == Blocks.snow_layer)
/*      */     {
/*  609 */       block$soundtype = Blocks.snow_layer.stepSound;
/*      */     }
/*      */     
/*  612 */     if (!blockIn.getMaterial().isLiquid()) {
/*      */       
/*  614 */       int i = getHorseType();
/*      */       
/*  616 */       if (this.riddenByEntity != null && i != 1 && i != 2) {
/*      */         
/*  618 */         this.gallopTime++;
/*      */         
/*  620 */         if (this.gallopTime > 5 && this.gallopTime % 3 == 0)
/*      */         {
/*  622 */           playSound("mob.horse.gallop", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */           
/*  624 */           if (i == 0 && this.rand.nextInt(10) == 0)
/*      */           {
/*  626 */             playSound("mob.horse.breathe", block$soundtype.getVolume() * 0.6F, block$soundtype.getFrequency());
/*      */           }
/*      */         }
/*  629 */         else if (this.gallopTime <= 5)
/*      */         {
/*  631 */           playSound("mob.horse.wood", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */         }
/*      */       
/*  634 */       } else if (block$soundtype == Block.soundTypeWood) {
/*      */         
/*  636 */         playSound("mob.horse.wood", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */       }
/*      */       else {
/*      */         
/*  640 */         playSound("mob.horse.soft", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  647 */     super.applyEntityAttributes();
/*  648 */     getAttributeMap().registerAttribute(horseJumpStrength);
/*  649 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0D);
/*  650 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSpawnedInChunk() {
/*  658 */     return 6;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxTemper() {
/*  663 */     return 100;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/*  671 */     return 0.8F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTalkInterval() {
/*  679 */     return 400;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_110239_cn() {
/*  684 */     return (getHorseType() == 0 || getHorseArmorIndexSynced() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void resetTexturePrefix() {
/*  689 */     this.texturePrefix = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_175507_cI() {
/*  694 */     return this.field_175508_bO;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setHorseTexturePaths() {
/*  699 */     this.texturePrefix = "horse/";
/*  700 */     this.horseTexturesArray[0] = null;
/*  701 */     this.horseTexturesArray[1] = null;
/*  702 */     this.horseTexturesArray[2] = null;
/*  703 */     int i = getHorseType();
/*  704 */     int j = getHorseVariant();
/*      */     
/*  706 */     if (i == 0) {
/*      */       
/*  708 */       int k = j & 0xFF;
/*  709 */       int l = (j & 0xFF00) >> 8;
/*      */       
/*  711 */       if (k >= horseTextures.length) {
/*      */         
/*  713 */         this.field_175508_bO = false;
/*      */         
/*      */         return;
/*      */       } 
/*  717 */       this.horseTexturesArray[0] = horseTextures[k];
/*  718 */       this.texturePrefix += HORSE_TEXTURES_ABBR[k];
/*      */       
/*  720 */       if (l >= horseMarkingTextures.length) {
/*      */         
/*  722 */         this.field_175508_bO = false;
/*      */         
/*      */         return;
/*      */       } 
/*  726 */       this.horseTexturesArray[1] = horseMarkingTextures[l];
/*  727 */       this.texturePrefix += HORSE_MARKING_TEXTURES_ABBR[l];
/*      */     }
/*      */     else {
/*      */       
/*  731 */       this.horseTexturesArray[0] = "";
/*  732 */       this.texturePrefix += "_" + i + "_";
/*      */     } 
/*      */     
/*  735 */     int i1 = getHorseArmorIndexSynced();
/*      */     
/*  737 */     if (i1 >= horseArmorTextures.length) {
/*      */       
/*  739 */       this.field_175508_bO = false;
/*      */     }
/*      */     else {
/*      */       
/*  743 */       this.horseTexturesArray[2] = horseArmorTextures[i1];
/*  744 */       this.texturePrefix += HORSE_ARMOR_TEXTURES_ABBR[i1];
/*  745 */       this.field_175508_bO = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getHorseTexture() {
/*  751 */     if (this.texturePrefix == null)
/*      */     {
/*  753 */       setHorseTexturePaths();
/*      */     }
/*      */     
/*  756 */     return this.texturePrefix;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getVariantTexturePaths() {
/*  761 */     if (this.texturePrefix == null)
/*      */     {
/*  763 */       setHorseTexturePaths();
/*      */     }
/*      */     
/*  766 */     return this.horseTexturesArray;
/*      */   }
/*      */ 
/*      */   
/*      */   public void openGUI(EntityPlayer playerEntity) {
/*  771 */     if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == playerEntity) && isTame()) {
/*      */       
/*  773 */       this.horseChest.setCustomName(getName());
/*  774 */       playerEntity.displayGUIHorse(this, (IInventory)this.horseChest);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interact(EntityPlayer player) {
/*  783 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*      */     
/*  785 */     if (itemstack != null && itemstack.getItem() == Items.spawn_egg)
/*      */     {
/*  787 */       return super.interact(player);
/*      */     }
/*  789 */     if (!isTame() && isUndead())
/*      */     {
/*  791 */       return false;
/*      */     }
/*  793 */     if (isTame() && isAdultHorse() && player.isSneaking()) {
/*      */       
/*  795 */       openGUI(player);
/*  796 */       return true;
/*      */     } 
/*  798 */     if (func_110253_bW() && this.riddenByEntity != null)
/*      */     {
/*  800 */       return super.interact(player);
/*      */     }
/*      */ 
/*      */     
/*  804 */     if (itemstack != null) {
/*      */       
/*  806 */       boolean flag = false;
/*      */       
/*  808 */       if (canWearArmor()) {
/*      */         
/*  810 */         int i = -1;
/*      */         
/*  812 */         if (itemstack.getItem() == Items.iron_horse_armor) {
/*      */           
/*  814 */           i = 1;
/*      */         }
/*  816 */         else if (itemstack.getItem() == Items.golden_horse_armor) {
/*      */           
/*  818 */           i = 2;
/*      */         }
/*  820 */         else if (itemstack.getItem() == Items.diamond_horse_armor) {
/*      */           
/*  822 */           i = 3;
/*      */         } 
/*      */         
/*  825 */         if (i >= 0) {
/*      */           
/*  827 */           if (!isTame()) {
/*      */             
/*  829 */             makeHorseRearWithSound();
/*  830 */             return true;
/*      */           } 
/*      */           
/*  833 */           openGUI(player);
/*  834 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/*  838 */       if (!flag && !isUndead()) {
/*      */         
/*  840 */         float f = 0.0F;
/*  841 */         int j = 0;
/*  842 */         int k = 0;
/*      */         
/*  844 */         if (itemstack.getItem() == Items.wheat) {
/*      */           
/*  846 */           f = 2.0F;
/*  847 */           j = 20;
/*  848 */           k = 3;
/*      */         }
/*  850 */         else if (itemstack.getItem() == Items.sugar) {
/*      */           
/*  852 */           f = 1.0F;
/*  853 */           j = 30;
/*  854 */           k = 3;
/*      */         }
/*  856 */         else if (Block.getBlockFromItem(itemstack.getItem()) == Blocks.hay_block) {
/*      */           
/*  858 */           f = 20.0F;
/*  859 */           j = 180;
/*      */         }
/*  861 */         else if (itemstack.getItem() == Items.apple) {
/*      */           
/*  863 */           f = 3.0F;
/*  864 */           j = 60;
/*  865 */           k = 3;
/*      */         }
/*  867 */         else if (itemstack.getItem() == Items.golden_carrot) {
/*      */           
/*  869 */           f = 4.0F;
/*  870 */           j = 60;
/*  871 */           k = 5;
/*      */           
/*  873 */           if (isTame() && getGrowingAge() == 0)
/*      */           {
/*  875 */             flag = true;
/*  876 */             setInLove(player);
/*      */           }
/*      */         
/*  879 */         } else if (itemstack.getItem() == Items.golden_apple) {
/*      */           
/*  881 */           f = 10.0F;
/*  882 */           j = 240;
/*  883 */           k = 10;
/*      */           
/*  885 */           if (isTame() && getGrowingAge() == 0) {
/*      */             
/*  887 */             flag = true;
/*  888 */             setInLove(player);
/*      */           } 
/*      */         } 
/*      */         
/*  892 */         if (getHealth() < getMaxHealth() && f > 0.0F) {
/*      */           
/*  894 */           heal(f);
/*  895 */           flag = true;
/*      */         } 
/*      */         
/*  898 */         if (!isAdultHorse() && j > 0) {
/*      */           
/*  900 */           addGrowth(j);
/*  901 */           flag = true;
/*      */         } 
/*      */         
/*  904 */         if (k > 0 && (flag || !isTame()) && k < getMaxTemper()) {
/*      */           
/*  906 */           flag = true;
/*  907 */           increaseTemper(k);
/*      */         } 
/*      */         
/*  910 */         if (flag)
/*      */         {
/*  912 */           func_110266_cB();
/*      */         }
/*      */       } 
/*      */       
/*  916 */       if (!isTame() && !flag) {
/*      */         
/*  918 */         if (itemstack != null && itemstack.interactWithEntity(player, (EntityLivingBase)this))
/*      */         {
/*  920 */           return true;
/*      */         }
/*      */         
/*  923 */         makeHorseRearWithSound();
/*  924 */         return true;
/*      */       } 
/*      */       
/*  927 */       if (!flag && canCarryChest() && !isChested() && itemstack.getItem() == Item.getItemFromBlock((Block)Blocks.chest)) {
/*      */         
/*  929 */         setChested(true);
/*  930 */         playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  931 */         flag = true;
/*  932 */         initHorseChest();
/*      */       } 
/*      */       
/*  935 */       if (!flag && func_110253_bW() && !isHorseSaddled() && itemstack.getItem() == Items.saddle) {
/*      */         
/*  937 */         openGUI(player);
/*  938 */         return true;
/*      */       } 
/*      */       
/*  941 */       if (flag) {
/*      */         
/*  943 */         if (!player.capabilities.isCreativeMode && --itemstack.stackSize == 0)
/*      */         {
/*  945 */           player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
/*      */         }
/*      */         
/*  948 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  952 */     if (func_110253_bW() && this.riddenByEntity == null) {
/*      */       
/*  954 */       if (itemstack != null && itemstack.interactWithEntity(player, (EntityLivingBase)this))
/*      */       {
/*  956 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  960 */       mountTo(player);
/*  961 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  966 */     return super.interact(player);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mountTo(EntityPlayer player) {
/*  973 */     player.rotationYaw = this.rotationYaw;
/*  974 */     player.rotationPitch = this.rotationPitch;
/*  975 */     setEatingHaystack(false);
/*  976 */     setRearing(false);
/*      */     
/*  978 */     if (!this.worldObj.isRemote)
/*      */     {
/*  980 */       player.mountEntity((Entity)this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWearArmor() {
/*  989 */     return (getHorseType() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCarryChest() {
/*  997 */     int i = getHorseType();
/*  998 */     return (i == 2 || i == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/* 1006 */     return (this.riddenByEntity != null && isHorseSaddled()) ? true : ((isEatingHaystack() || isRearing()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUndead() {
/* 1014 */     int i = getHorseType();
/* 1015 */     return (i == 3 || i == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSterile() {
/* 1023 */     return (isUndead() || getHorseType() == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBreedingItem(ItemStack stack) {
/* 1032 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_110210_cH() {
/* 1037 */     this.field_110278_bp = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/* 1045 */     super.onDeath(cause);
/*      */     
/* 1047 */     if (!this.worldObj.isRemote)
/*      */     {
/* 1049 */       dropChestItems();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/* 1059 */     if (this.rand.nextInt(200) == 0)
/*      */     {
/* 1061 */       func_110210_cH();
/*      */     }
/*      */     
/* 1064 */     super.onLivingUpdate();
/*      */     
/* 1066 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1068 */       if (this.rand.nextInt(900) == 0 && this.deathTime == 0)
/*      */       {
/* 1070 */         heal(1.0F);
/*      */       }
/*      */       
/* 1073 */       if (!isEatingHaystack() && this.riddenByEntity == null && this.rand.nextInt(300) == 0 && this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ))).getBlock() == Blocks.grass)
/*      */       {
/* 1075 */         setEatingHaystack(true);
/*      */       }
/*      */       
/* 1078 */       if (isEatingHaystack() && ++this.eatingHaystackCounter > 50) {
/*      */         
/* 1080 */         this.eatingHaystackCounter = 0;
/* 1081 */         setEatingHaystack(false);
/*      */       } 
/*      */       
/* 1084 */       if (isBreeding() && !isAdultHorse() && !isEatingHaystack()) {
/*      */         
/* 1086 */         EntityHorse entityhorse = getClosestHorse((Entity)this, 16.0D);
/*      */         
/* 1088 */         if (entityhorse != null && getDistanceSqToEntity((Entity)entityhorse) > 4.0D)
/*      */         {
/* 1090 */           this.navigator.getPathToEntityLiving((Entity)entityhorse);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/* 1101 */     super.onUpdate();
/*      */     
/* 1103 */     if (this.worldObj.isRemote && this.dataWatcher.hasObjectChanged()) {
/*      */       
/* 1105 */       this.dataWatcher.func_111144_e();
/* 1106 */       resetTexturePrefix();
/*      */     } 
/*      */     
/* 1109 */     if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
/*      */       
/* 1111 */       this.openMouthCounter = 0;
/* 1112 */       setHorseWatchableBoolean(128, false);
/*      */     } 
/*      */     
/* 1115 */     if (!this.worldObj.isRemote && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
/*      */       
/* 1117 */       this.jumpRearingCounter = 0;
/* 1118 */       setRearing(false);
/*      */     } 
/*      */     
/* 1121 */     if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8)
/*      */     {
/* 1123 */       this.field_110278_bp = 0;
/*      */     }
/*      */     
/* 1126 */     if (this.field_110279_bq > 0) {
/*      */       
/* 1128 */       this.field_110279_bq++;
/*      */       
/* 1130 */       if (this.field_110279_bq > 300)
/*      */       {
/* 1132 */         this.field_110279_bq = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1136 */     this.prevHeadLean = this.headLean;
/*      */     
/* 1138 */     if (isEatingHaystack()) {
/*      */       
/* 1140 */       this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;
/*      */       
/* 1142 */       if (this.headLean > 1.0F)
/*      */       {
/* 1144 */         this.headLean = 1.0F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1149 */       this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
/*      */       
/* 1151 */       if (this.headLean < 0.0F)
/*      */       {
/* 1153 */         this.headLean = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/* 1157 */     this.prevRearingAmount = this.rearingAmount;
/*      */     
/* 1159 */     if (isRearing()) {
/*      */       
/* 1161 */       this.prevHeadLean = this.headLean = 0.0F;
/* 1162 */       this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
/*      */       
/* 1164 */       if (this.rearingAmount > 1.0F)
/*      */       {
/* 1166 */         this.rearingAmount = 1.0F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1171 */       this.field_110294_bI = false;
/* 1172 */       this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;
/*      */       
/* 1174 */       if (this.rearingAmount < 0.0F)
/*      */       {
/* 1176 */         this.rearingAmount = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/* 1180 */     this.prevMouthOpenness = this.mouthOpenness;
/*      */     
/* 1182 */     if (getHorseWatchableBoolean(128)) {
/*      */       
/* 1184 */       this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;
/*      */       
/* 1186 */       if (this.mouthOpenness > 1.0F)
/*      */       {
/* 1188 */         this.mouthOpenness = 1.0F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1193 */       this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
/*      */       
/* 1195 */       if (this.mouthOpenness < 0.0F)
/*      */       {
/* 1197 */         this.mouthOpenness = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void openHorseMouth() {
/* 1204 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1206 */       this.openMouthCounter = 1;
/* 1207 */       setHorseWatchableBoolean(128, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canMate() {
/* 1216 */     return (this.riddenByEntity == null && this.ridingEntity == null && isTame() && isAdultHorse() && !isSterile() && getHealth() >= getMaxHealth() && isInLove());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEating(boolean eating) {
/* 1221 */     setHorseWatchableBoolean(32, eating);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEatingHaystack(boolean p_110227_1_) {
/* 1226 */     setEating(p_110227_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRearing(boolean rearing) {
/* 1231 */     if (rearing)
/*      */     {
/* 1233 */       setEatingHaystack(false);
/*      */     }
/*      */     
/* 1236 */     setHorseWatchableBoolean(64, rearing);
/*      */   }
/*      */ 
/*      */   
/*      */   private void makeHorseRear() {
/* 1241 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1243 */       this.jumpRearingCounter = 1;
/* 1244 */       setRearing(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeHorseRearWithSound() {
/* 1250 */     makeHorseRear();
/* 1251 */     String s = getAngrySoundName();
/*      */     
/* 1253 */     if (s != null)
/*      */     {
/* 1255 */       playSound(s, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void dropChestItems() {
/* 1261 */     dropItemsInChest((Entity)this, this.horseChest);
/* 1262 */     dropChests();
/*      */   }
/*      */ 
/*      */   
/*      */   private void dropItemsInChest(Entity entityIn, AnimalChest animalChestIn) {
/* 1267 */     if (animalChestIn != null && !this.worldObj.isRemote)
/*      */     {
/* 1269 */       for (int i = 0; i < animalChestIn.getSizeInventory(); i++) {
/*      */         
/* 1271 */         ItemStack itemstack = animalChestIn.getStackInSlot(i);
/*      */         
/* 1273 */         if (itemstack != null)
/*      */         {
/* 1275 */           entityDropItem(itemstack, 0.0F);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setTamedBy(EntityPlayer player) {
/* 1283 */     setOwnerId(player.getUniqueID().toString());
/* 1284 */     setHorseTamed(true);
/* 1285 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1293 */     if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && isHorseSaddled()) {
/*      */       
/* 1295 */       this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
/* 1296 */       this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
/* 1297 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 1298 */       this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
/* 1299 */       strafe = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
/* 1300 */       forward = ((EntityLivingBase)this.riddenByEntity).moveForward;
/*      */       
/* 1302 */       if (forward <= 0.0F) {
/*      */         
/* 1304 */         forward *= 0.25F;
/* 1305 */         this.gallopTime = 0;
/*      */       } 
/*      */       
/* 1308 */       if (this.onGround && this.jumpPower == 0.0F && isRearing() && !this.field_110294_bI) {
/*      */         
/* 1310 */         strafe = 0.0F;
/* 1311 */         forward = 0.0F;
/*      */       } 
/*      */       
/* 1314 */       if (this.jumpPower > 0.0F && !this.horseJumping && this.onGround) {
/*      */         
/* 1316 */         this.motionY = getHorseJumpStrength() * this.jumpPower;
/*      */         
/* 1318 */         if (isPotionActive(Potion.jump))
/*      */         {
/* 1320 */           this.motionY += ((getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
/*      */         }
/*      */         
/* 1323 */         this.horseJumping = true;
/* 1324 */         this.isAirBorne = true;
/*      */         
/* 1326 */         if (forward > 0.0F) {
/*      */           
/* 1328 */           float f = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F);
/* 1329 */           float f1 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
/* 1330 */           this.motionX += (-0.4F * f * this.jumpPower);
/* 1331 */           this.motionZ += (0.4F * f1 * this.jumpPower);
/* 1332 */           playSound("mob.horse.jump", 0.4F, 1.0F);
/*      */         } 
/*      */         
/* 1335 */         this.jumpPower = 0.0F;
/*      */       } 
/*      */       
/* 1338 */       this.stepHeight = 1.0F;
/* 1339 */       this.jumpMovementFactor = getAIMoveSpeed() * 0.1F;
/*      */       
/* 1341 */       if (!this.worldObj.isRemote) {
/*      */         
/* 1343 */         setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 1344 */         super.moveEntityWithHeading(strafe, forward);
/*      */       } 
/*      */       
/* 1347 */       if (this.onGround) {
/*      */         
/* 1349 */         this.jumpPower = 0.0F;
/* 1350 */         this.horseJumping = false;
/*      */       } 
/*      */       
/* 1353 */       this.prevLimbSwingAmount = this.limbSwingAmount;
/* 1354 */       double d1 = this.posX - this.prevPosX;
/* 1355 */       double d0 = this.posZ - this.prevPosZ;
/* 1356 */       float f2 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
/*      */       
/* 1358 */       if (f2 > 1.0F)
/*      */       {
/* 1360 */         f2 = 1.0F;
/*      */       }
/*      */       
/* 1363 */       this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
/* 1364 */       this.limbSwing += this.limbSwingAmount;
/*      */     }
/*      */     else {
/*      */       
/* 1368 */       this.stepHeight = 0.5F;
/* 1369 */       this.jumpMovementFactor = 0.02F;
/* 1370 */       super.moveEntityWithHeading(strafe, forward);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 1379 */     super.writeEntityToNBT(tagCompound);
/* 1380 */     tagCompound.setBoolean("EatingHaystack", isEatingHaystack());
/* 1381 */     tagCompound.setBoolean("ChestedHorse", isChested());
/* 1382 */     tagCompound.setBoolean("HasReproduced", this.hasReproduced);
/* 1383 */     tagCompound.setBoolean("Bred", isBreeding());
/* 1384 */     tagCompound.setInteger("Type", getHorseType());
/* 1385 */     tagCompound.setInteger("Variant", getHorseVariant());
/* 1386 */     tagCompound.setInteger("Temper", this.temper);
/* 1387 */     tagCompound.setBoolean("Tame", isTame());
/* 1388 */     tagCompound.setString("OwnerUUID", getOwnerId());
/*      */     
/* 1390 */     if (isChested()) {
/*      */       
/* 1392 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/* 1394 */       for (int i = 2; i < this.horseChest.getSizeInventory(); i++) {
/*      */         
/* 1396 */         ItemStack itemstack = this.horseChest.getStackInSlot(i);
/*      */         
/* 1398 */         if (itemstack != null) {
/*      */           
/* 1400 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 1401 */           nbttagcompound.setByte("Slot", (byte)i);
/* 1402 */           itemstack.writeToNBT(nbttagcompound);
/* 1403 */           nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */         } 
/*      */       } 
/*      */       
/* 1407 */       tagCompound.setTag("Items", (NBTBase)nbttaglist);
/*      */     } 
/*      */     
/* 1410 */     if (this.horseChest.getStackInSlot(1) != null)
/*      */     {
/* 1412 */       tagCompound.setTag("ArmorItem", (NBTBase)this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */     
/* 1415 */     if (this.horseChest.getStackInSlot(0) != null)
/*      */     {
/* 1417 */       tagCompound.setTag("SaddleItem", (NBTBase)this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 1426 */     super.readEntityFromNBT(tagCompund);
/* 1427 */     setEatingHaystack(tagCompund.getBoolean("EatingHaystack"));
/* 1428 */     setBreeding(tagCompund.getBoolean("Bred"));
/* 1429 */     setChested(tagCompund.getBoolean("ChestedHorse"));
/* 1430 */     this.hasReproduced = tagCompund.getBoolean("HasReproduced");
/* 1431 */     setHorseType(tagCompund.getInteger("Type"));
/* 1432 */     setHorseVariant(tagCompund.getInteger("Variant"));
/* 1433 */     this.temper = tagCompund.getInteger("Temper");
/* 1434 */     setHorseTamed(tagCompund.getBoolean("Tame"));
/* 1435 */     String s = "";
/*      */     
/* 1437 */     if (tagCompund.hasKey("OwnerUUID", 8)) {
/*      */       
/* 1439 */       s = tagCompund.getString("OwnerUUID");
/*      */     }
/*      */     else {
/*      */       
/* 1443 */       String s1 = tagCompund.getString("Owner");
/* 1444 */       s = PreYggdrasilConverter.getStringUUIDFromName(s1);
/*      */     } 
/*      */     
/* 1447 */     if (!s.isEmpty())
/*      */     {
/* 1449 */       setOwnerId(s);
/*      */     }
/*      */     
/* 1452 */     IAttributeInstance iattributeinstance = getAttributeMap().getAttributeInstanceByName("Speed");
/*      */     
/* 1454 */     if (iattributeinstance != null)
/*      */     {
/* 1456 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(iattributeinstance.getBaseValue() * 0.25D);
/*      */     }
/*      */     
/* 1459 */     if (isChested()) {
/*      */       
/* 1461 */       NBTTagList nbttaglist = tagCompund.getTagList("Items", 10);
/* 1462 */       initHorseChest();
/*      */       
/* 1464 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/* 1466 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 1467 */         int j = nbttagcompound.getByte("Slot") & 0xFF;
/*      */         
/* 1469 */         if (j >= 2 && j < this.horseChest.getSizeInventory())
/*      */         {
/* 1471 */           this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1476 */     if (tagCompund.hasKey("ArmorItem", 10)) {
/*      */       
/* 1478 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("ArmorItem"));
/*      */       
/* 1480 */       if (itemstack != null && isArmorItem(itemstack.getItem()))
/*      */       {
/* 1482 */         this.horseChest.setInventorySlotContents(1, itemstack);
/*      */       }
/*      */     } 
/*      */     
/* 1486 */     if (tagCompund.hasKey("SaddleItem", 10)) {
/*      */       
/* 1488 */       ItemStack itemstack1 = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("SaddleItem"));
/*      */       
/* 1490 */       if (itemstack1 != null && itemstack1.getItem() == Items.saddle)
/*      */       {
/* 1492 */         this.horseChest.setInventorySlotContents(0, itemstack1);
/*      */       }
/*      */     }
/* 1495 */     else if (tagCompund.getBoolean("Saddle")) {
/*      */       
/* 1497 */       this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
/*      */     } 
/*      */     
/* 1500 */     updateHorseSlots();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 1508 */     if (otherAnimal == this)
/*      */     {
/* 1510 */       return false;
/*      */     }
/* 1512 */     if (otherAnimal.getClass() != getClass())
/*      */     {
/* 1514 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1518 */     EntityHorse entityhorse = (EntityHorse)otherAnimal;
/*      */     
/* 1520 */     if (canMate() && entityhorse.canMate()) {
/*      */       
/* 1522 */       int i = getHorseType();
/* 1523 */       int j = entityhorse.getHorseType();
/* 1524 */       return (i == j || (i == 0 && j == 1) || (i == 1 && j == 0));
/*      */     } 
/*      */ 
/*      */     
/* 1528 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityAgeable createChild(EntityAgeable ageable) {
/* 1535 */     EntityHorse entityhorse = (EntityHorse)ageable;
/* 1536 */     EntityHorse entityhorse1 = new EntityHorse(this.worldObj);
/* 1537 */     int i = getHorseType();
/* 1538 */     int j = entityhorse.getHorseType();
/* 1539 */     int k = 0;
/*      */     
/* 1541 */     if (i == j) {
/*      */       
/* 1543 */       k = i;
/*      */     }
/* 1545 */     else if ((i == 0 && j == 1) || (i == 1 && j == 0)) {
/*      */       
/* 1547 */       k = 2;
/*      */     } 
/*      */     
/* 1550 */     if (k == 0) {
/*      */       
/* 1552 */       int l, i1 = this.rand.nextInt(9);
/*      */ 
/*      */       
/* 1555 */       if (i1 < 4) {
/*      */         
/* 1557 */         l = getHorseVariant() & 0xFF;
/*      */       }
/* 1559 */       else if (i1 < 8) {
/*      */         
/* 1561 */         l = entityhorse.getHorseVariant() & 0xFF;
/*      */       }
/*      */       else {
/*      */         
/* 1565 */         l = this.rand.nextInt(7);
/*      */       } 
/*      */       
/* 1568 */       int j1 = this.rand.nextInt(5);
/*      */       
/* 1570 */       if (j1 < 2) {
/*      */         
/* 1572 */         l |= getHorseVariant() & 0xFF00;
/*      */       }
/* 1574 */       else if (j1 < 4) {
/*      */         
/* 1576 */         l |= entityhorse.getHorseVariant() & 0xFF00;
/*      */       }
/*      */       else {
/*      */         
/* 1580 */         l |= this.rand.nextInt(5) << 8 & 0xFF00;
/*      */       } 
/*      */       
/* 1583 */       entityhorse1.setHorseVariant(l);
/*      */     } 
/*      */     
/* 1586 */     entityhorse1.setHorseType(k);
/* 1587 */     double d1 = getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + ageable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + getModifiedMaxHealth();
/* 1588 */     entityhorse1.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(d1 / 3.0D);
/* 1589 */     double d2 = getEntityAttribute(horseJumpStrength).getBaseValue() + ageable.getEntityAttribute(horseJumpStrength).getBaseValue() + getModifiedJumpStrength();
/* 1590 */     entityhorse1.getEntityAttribute(horseJumpStrength).setBaseValue(d2 / 3.0D);
/* 1591 */     double d0 = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + ageable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + getModifiedMovementSpeed();
/* 1592 */     entityhorse1.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(d0 / 3.0D);
/* 1593 */     return entityhorse1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 1602 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 1603 */     int i = 0;
/* 1604 */     int j = 0;
/*      */     
/* 1606 */     if (livingdata instanceof GroupData) {
/*      */       
/* 1608 */       i = ((GroupData)livingdata).horseType;
/* 1609 */       j = ((GroupData)livingdata).horseVariant & 0xFF | this.rand.nextInt(5) << 8;
/*      */     }
/*      */     else {
/*      */       
/* 1613 */       if (this.rand.nextInt(10) == 0) {
/*      */         
/* 1615 */         i = 1;
/*      */       }
/*      */       else {
/*      */         
/* 1619 */         int k = this.rand.nextInt(7);
/* 1620 */         int l = this.rand.nextInt(5);
/* 1621 */         i = 0;
/* 1622 */         j = k | l << 8;
/*      */       } 
/*      */       
/* 1625 */       livingdata = new GroupData(i, j);
/*      */     } 
/*      */     
/* 1628 */     setHorseType(i);
/* 1629 */     setHorseVariant(j);
/*      */     
/* 1631 */     if (this.rand.nextInt(5) == 0)
/*      */     {
/* 1633 */       setGrowingAge(-24000);
/*      */     }
/*      */     
/* 1636 */     if (i != 4 && i != 3) {
/*      */       
/* 1638 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getModifiedMaxHealth());
/*      */       
/* 1640 */       if (i == 0)
/*      */       {
/* 1642 */         getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(getModifiedMovementSpeed());
/*      */       }
/*      */       else
/*      */       {
/* 1646 */         getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776D);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1651 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
/* 1652 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
/*      */     } 
/*      */     
/* 1655 */     if (i != 2 && i != 1) {
/*      */       
/* 1657 */       getEntityAttribute(horseJumpStrength).setBaseValue(getModifiedJumpStrength());
/*      */     }
/*      */     else {
/*      */       
/* 1661 */       getEntityAttribute(horseJumpStrength).setBaseValue(0.5D);
/*      */     } 
/*      */     
/* 1664 */     setHealth(getMaxHealth());
/* 1665 */     return livingdata;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getGrassEatingAmount(float p_110258_1_) {
/* 1670 */     return this.prevHeadLean + (this.headLean - this.prevHeadLean) * p_110258_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRearingAmount(float p_110223_1_) {
/* 1675 */     return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * p_110223_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMouthOpennessAngle(float p_110201_1_) {
/* 1680 */     return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * p_110201_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setJumpPower(int jumpPowerIn) {
/* 1685 */     if (isHorseSaddled()) {
/*      */       
/* 1687 */       if (jumpPowerIn < 0) {
/*      */         
/* 1689 */         jumpPowerIn = 0;
/*      */       }
/*      */       else {
/*      */         
/* 1693 */         this.field_110294_bI = true;
/* 1694 */         makeHorseRear();
/*      */       } 
/*      */       
/* 1697 */       if (jumpPowerIn >= 90) {
/*      */         
/* 1699 */         this.jumpPower = 1.0F;
/*      */       }
/*      */       else {
/*      */         
/* 1703 */         this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void spawnHorseParticles(boolean p_110216_1_) {
/* 1713 */     EnumParticleTypes enumparticletypes = p_110216_1_ ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;
/*      */     
/* 1715 */     for (int i = 0; i < 7; i++) {
/*      */       
/* 1717 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 1718 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 1719 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 1720 */       this.worldObj.spawnParticle(enumparticletypes, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/* 1726 */     if (id == 7) {
/*      */       
/* 1728 */       spawnHorseParticles(true);
/*      */     }
/* 1730 */     else if (id == 6) {
/*      */       
/* 1732 */       spawnHorseParticles(false);
/*      */     }
/*      */     else {
/*      */       
/* 1736 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateRiderPosition() {
/* 1742 */     super.updateRiderPosition();
/*      */     
/* 1744 */     if (this.prevRearingAmount > 0.0F) {
/*      */       
/* 1746 */       float f = MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F);
/* 1747 */       float f1 = MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F);
/* 1748 */       float f2 = 0.7F * this.prevRearingAmount;
/* 1749 */       float f3 = 0.15F * this.prevRearingAmount;
/* 1750 */       this.riddenByEntity.setPosition(this.posX + (f2 * f), this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset() + f3, this.posZ - (f2 * f1));
/*      */       
/* 1752 */       if (this.riddenByEntity instanceof EntityLivingBase)
/*      */       {
/* 1754 */         ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getModifiedMaxHealth() {
/* 1764 */     return 15.0F + this.rand.nextInt(8) + this.rand.nextInt(9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double getModifiedJumpStrength() {
/* 1772 */     return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double getModifiedMovementSpeed() {
/* 1780 */     return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isArmorItem(Item p_146085_0_) {
/* 1788 */     return (p_146085_0_ == Items.iron_horse_armor || p_146085_0_ == Items.golden_horse_armor || p_146085_0_ == Items.diamond_horse_armor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnLadder() {
/* 1796 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 1801 */     return this.height;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 1806 */     if (inventorySlot == 499 && canCarryChest()) {
/*      */       
/* 1808 */       if (itemStackIn == null && isChested()) {
/*      */         
/* 1810 */         setChested(false);
/* 1811 */         initHorseChest();
/* 1812 */         return true;
/*      */       } 
/*      */       
/* 1815 */       if (itemStackIn != null && itemStackIn.getItem() == Item.getItemFromBlock((Block)Blocks.chest) && !isChested()) {
/*      */         
/* 1817 */         setChested(true);
/* 1818 */         initHorseChest();
/* 1819 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1823 */     int i = inventorySlot - 400;
/*      */     
/* 1825 */     if (i >= 0 && i < 2 && i < this.horseChest.getSizeInventory()) {
/*      */       
/* 1827 */       if (i == 0 && itemStackIn != null && itemStackIn.getItem() != Items.saddle)
/*      */       {
/* 1829 */         return false;
/*      */       }
/* 1831 */       if (i != 1 || ((itemStackIn == null || isArmorItem(itemStackIn.getItem())) && canWearArmor())) {
/*      */         
/* 1833 */         this.horseChest.setInventorySlotContents(i, itemStackIn);
/* 1834 */         updateHorseSlots();
/* 1835 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1839 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1844 */     int j = inventorySlot - 500 + 2;
/*      */     
/* 1846 */     if (j >= 2 && j < this.horseChest.getSizeInventory()) {
/*      */       
/* 1848 */       this.horseChest.setInventorySlotContents(j, itemStackIn);
/* 1849 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1853 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class GroupData
/*      */     implements IEntityLivingData
/*      */   {
/*      */     public int horseType;
/*      */     
/*      */     public int horseVariant;
/*      */     
/*      */     public GroupData(int type, int variant) {
/* 1865 */       this.horseType = type;
/* 1866 */       this.horseVariant = variant;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */