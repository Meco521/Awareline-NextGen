/*      */ package net.minecraft.entity;
/*      */ import awareline.main.mod.implement.move.NoJumpDelay;
/*      */ import awareline.main.mod.implement.visual.Animations;
/*      */ import com.google.common.base.Predicate;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.passive.EntityWolf;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.potion.PotionHelper;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.CombatTracker;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ public abstract class EntityLivingBase extends Entity {
/*   44 */   private static final UUID sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
/*   45 */   private static final AttributeModifier sprintingSpeedBoostModifier = (new AttributeModifier(sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896D, 2)).setSaved(false);
/*   46 */   private final CombatTracker _combatTracker = new CombatTracker(this);
/*   47 */   private final Map<Integer, PotionEffect> activePotionsMap = Maps.newHashMap();
/*      */ 
/*      */ 
/*      */   
/*   51 */   private final ItemStack[] previousEquipment = new ItemStack[5];
/*      */ 
/*      */   
/*      */   public boolean isSwingInProgress;
/*      */ 
/*      */   
/*      */   public int swingProgressInt;
/*      */ 
/*      */   
/*      */   public int arrowHitTimer;
/*      */ 
/*      */   
/*      */   public int hurtTime;
/*      */ 
/*      */   
/*      */   public int maxHurtTime;
/*      */ 
/*      */   
/*      */   public float attackedAtYaw;
/*      */ 
/*      */   
/*      */   public float rotationPitchHead;
/*      */ 
/*      */   
/*      */   public int deathTime;
/*      */   
/*      */   public float prevSwingProgress;
/*      */   
/*      */   public float swingProgress;
/*      */   
/*      */   public float prevLimbSwingAmount;
/*      */   
/*      */   public float limbSwingAmount;
/*      */   
/*      */   public float limbSwing;
/*      */   
/*   87 */   public int maxHurtResistantTime = 20;
/*      */ 
/*      */   
/*      */   public float prevCameraPitch;
/*      */ 
/*      */   
/*      */   public float cameraPitch;
/*      */   
/*      */   public float randomUnused2;
/*      */   
/*      */   public float randomUnused1;
/*      */   
/*      */   public float renderYawOffset;
/*      */   
/*      */   public float prevRenderYawOffset;
/*      */   
/*      */   public float rotationYawHead;
/*      */   
/*      */   public float prevRotationPitchHead;
/*      */   
/*      */   public float prevRotationYawHead;
/*      */   
/*  109 */   public float jumpMovementFactor = 0.02F;
/*      */ 
/*      */   
/*      */   public float moveStrafing;
/*      */ 
/*      */   
/*      */   public float moveForward;
/*      */ 
/*      */   
/*      */   protected EntityPlayer attackingPlayer;
/*      */ 
/*      */   
/*      */   protected int recentlyHit;
/*      */ 
/*      */   
/*      */   protected boolean dead;
/*      */ 
/*      */   
/*      */   protected int entityAge;
/*      */ 
/*      */   
/*      */   protected float prevOnGroundSpeedFactor;
/*      */ 
/*      */   
/*      */   protected float onGroundSpeedFactor;
/*      */ 
/*      */   
/*      */   protected float movedDistance;
/*      */ 
/*      */   
/*      */   protected float prevMovedDistance;
/*      */ 
/*      */   
/*      */   protected float unused180;
/*      */ 
/*      */   
/*      */   protected int scoreValue;
/*      */ 
/*      */   
/*      */   protected float lastDamage;
/*      */ 
/*      */   
/*      */   public boolean isJumping;
/*      */ 
/*      */   
/*      */   protected float randomYawVelocity;
/*      */ 
/*      */   
/*      */   protected int newPosRotationIncrements;
/*      */ 
/*      */   
/*      */   protected double newPosX;
/*      */ 
/*      */   
/*      */   protected double newPosY;
/*      */ 
/*      */   
/*      */   protected double newPosZ;
/*      */ 
/*      */   
/*      */   protected double newRotationYaw;
/*      */ 
/*      */   
/*      */   protected double newRotationPitch;
/*      */ 
/*      */   
/*      */   private BaseAttributeMap attributeMap;
/*      */ 
/*      */   
/*      */   private boolean potionsNeedUpdate = true;
/*      */ 
/*      */   
/*      */   private EntityLivingBase entityLivingToAttack;
/*      */ 
/*      */   
/*      */   private int revengeTimer;
/*      */ 
/*      */   
/*      */   private EntityLivingBase lastAttacker;
/*      */   
/*      */   private int lastAttackerTime;
/*      */   
/*      */   private float landMovementFactor;
/*      */   
/*      */   private int jumpTicks;
/*      */   
/*      */   private float absorptionAmount;
/*      */ 
/*      */   
/*      */   public EntityLivingBase(World worldIn) {
/*  199 */     super(worldIn);
/*  200 */     applyEntityAttributes();
/*  201 */     setHealth(getMaxHealth());
/*  202 */     this.preventEntitySpawning = true;
/*  203 */     this.randomUnused1 = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
/*  204 */     setPosition(this.posX, this.posY, this.posZ);
/*  205 */     this.randomUnused2 = (float)Math.random() * 12398.0F;
/*  206 */     this.rotationYaw = (float)(Math.random() * Math.PI * 2.0D);
/*  207 */     this.rotationYawHead = this.rotationYaw;
/*  208 */     this.stepHeight = 0.6F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  215 */     attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
/*      */   }
/*      */   
/*      */   protected void entityInit() {
/*  219 */     this.dataWatcher.addObject(7, Integer.valueOf(0));
/*  220 */     this.dataWatcher.addObject(8, Byte.valueOf((byte)0));
/*  221 */     this.dataWatcher.addObject(9, Byte.valueOf((byte)0));
/*  222 */     this.dataWatcher.addObject(6, Float.valueOf(1.0F));
/*      */   }
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  226 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
/*  227 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
/*  228 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
/*      */   }
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/*  232 */     if (!isInWater()) {
/*  233 */       handleWaterMovement();
/*      */     }
/*      */     
/*  236 */     if (!this.worldObj.isRemote && this.fallDistance > 3.0F && onGroundIn) {
/*  237 */       IBlockState iblockstate = this.worldObj.getBlockState(pos);
/*  238 */       Block block = iblockstate.getBlock();
/*  239 */       float f = MathHelper.ceiling_float_int(this.fallDistance - 3.0F);
/*      */       
/*  241 */       if (block.getMaterial() != Material.air) {
/*  242 */         double d0 = Math.min(0.2F + f / 15.0F, 10.0F);
/*      */         
/*  244 */         if (d0 > 2.5D) {
/*  245 */           d0 = 2.5D;
/*      */         }
/*      */         
/*  248 */         int i = (int)(150.0D * d0);
/*  249 */         ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(iblockstate) });
/*      */       } 
/*      */     } 
/*      */     
/*  253 */     super.updateFallState(y, onGroundIn, blockIn, pos);
/*      */   }
/*      */   
/*      */   public boolean canBreatheUnderwater() {
/*  257 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  264 */     this.prevSwingProgress = this.swingProgress;
/*  265 */     this.prevRotationPitchHead = this.rotationPitchHead;
/*  266 */     super.onEntityUpdate();
/*  267 */     this.worldObj.theProfiler.startSection("livingEntityBaseTick");
/*  268 */     boolean flag = this instanceof EntityPlayer;
/*      */     
/*  270 */     if (isEntityAlive()) {
/*  271 */       if (isEntityInsideOpaqueBlock()) {
/*  272 */         attackEntityFrom(DamageSource.inWall, 1.0F);
/*  273 */       } else if (flag && !this.worldObj.getWorldBorder().contains(getEntityBoundingBox())) {
/*  274 */         double d0 = this.worldObj.getWorldBorder().getClosestDistance(this) + this.worldObj.getWorldBorder().getDamageBuffer();
/*      */         
/*  276 */         if (d0 < 0.0D) {
/*  277 */           attackEntityFrom(DamageSource.inWall, Math.max(1, MathHelper.floor_double(-d0 * this.worldObj.getWorldBorder().getDamageAmount())));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  282 */     if (isImmuneToFire() || this.worldObj.isRemote) {
/*  283 */       extinguish();
/*      */     }
/*      */     
/*  286 */     boolean flag1 = (flag && ((EntityPlayer)this).capabilities.disableDamage);
/*      */     
/*  288 */     if (isEntityAlive()) {
/*  289 */       if (isInsideOfMaterial(Material.water)) {
/*  290 */         if (!canBreatheUnderwater() && !isPotionActive(Potion.waterBreathing.id) && !flag1) {
/*  291 */           setAir(decreaseAirSupply(getAir()));
/*      */           
/*  293 */           if (getAir() == -20) {
/*  294 */             setAir(0);
/*      */             
/*  296 */             for (int i = 0; i < 8; i++) {
/*  297 */               float f = this.rand.nextFloat() - this.rand.nextFloat();
/*  298 */               float f1 = this.rand.nextFloat() - this.rand.nextFloat();
/*  299 */               float f2 = this.rand.nextFloat() - this.rand.nextFloat();
/*  300 */               this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f, this.posY + f1, this.posZ + f2, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */             } 
/*      */             
/*  303 */             attackEntityFrom(DamageSource.drown, 2.0F);
/*      */           } 
/*      */         } 
/*      */         
/*  307 */         if (!this.worldObj.isRemote && isRiding() && this.ridingEntity instanceof EntityLivingBase) {
/*  308 */           mountEntity((Entity)null);
/*      */         }
/*      */       } else {
/*  311 */         setAir(300);
/*      */       } 
/*      */     }
/*      */     
/*  315 */     if (isEntityAlive() && isWet()) {
/*  316 */       extinguish();
/*      */     }
/*      */     
/*  319 */     this.prevCameraPitch = this.cameraPitch;
/*      */     
/*  321 */     if (this.hurtTime > 0) {
/*  322 */       this.hurtTime--;
/*      */     }
/*      */     
/*  325 */     if (this.hurtResistantTime > 0 && !(this instanceof net.minecraft.entity.player.EntityPlayerMP)) {
/*  326 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  329 */     if (getHealth() <= 0.0F) {
/*  330 */       onDeathUpdate();
/*      */     }
/*      */     
/*  333 */     if (this.recentlyHit > 0) {
/*  334 */       this.recentlyHit--;
/*      */     } else {
/*  336 */       this.attackingPlayer = null;
/*      */     } 
/*      */     
/*  339 */     if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
/*  340 */       this.lastAttacker = null;
/*      */     }
/*      */     
/*  343 */     if (this.entityLivingToAttack != null) {
/*  344 */       if (!this.entityLivingToAttack.isEntityAlive()) {
/*  345 */         setRevengeTarget((EntityLivingBase)null);
/*  346 */       } else if (this.ticksExisted - this.revengeTimer > 100) {
/*  347 */         setRevengeTarget((EntityLivingBase)null);
/*      */       } 
/*      */     }
/*  350 */     EventManager.call((Event)new EventLivingUpdate(this));
/*      */     
/*  352 */     updatePotionEffects();
/*  353 */     this.prevMovedDistance = this.movedDistance;
/*  354 */     this.prevRenderYawOffset = this.renderYawOffset;
/*  355 */     this.prevRotationYawHead = this.rotationYawHead;
/*  356 */     this.prevRotationYaw = this.rotationYaw;
/*  357 */     this.prevRotationPitch = this.rotationPitch;
/*  358 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChild() {
/*  365 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onDeathUpdate() {
/*  372 */     this.deathTime++;
/*      */     
/*  374 */     if (this.deathTime == 20) {
/*  375 */       if (!this.worldObj.isRemote && (this.recentlyHit > 0 || isPlayer()) && canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
/*  376 */         int i = getExperiencePoints(this.attackingPlayer);
/*      */         
/*  378 */         while (i > 0) {
/*  379 */           int j = EntityXPOrb.getXPSplit(i);
/*  380 */           i -= j;
/*  381 */           this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
/*      */         } 
/*      */       } 
/*      */       
/*  385 */       setDead();
/*      */       
/*  387 */       for (int k = 0; k < 20; k++) {
/*  388 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  389 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  390 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  391 */         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, d0, d1, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDropLoot() {
/*  400 */     return !isChild();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int decreaseAirSupply(int p_70682_1_) {
/*  407 */     int i = EnchantmentHelper.getRespiration(this);
/*  408 */     return (i > 0 && this.rand.nextInt(i + 1) > 0) ? p_70682_1_ : (p_70682_1_ - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  415 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/*  422 */     return false;
/*      */   }
/*      */   
/*      */   public Random getRNG() {
/*  426 */     return this.rand;
/*      */   }
/*      */   
/*      */   public EntityLivingBase getAITarget() {
/*  430 */     return this.entityLivingToAttack;
/*      */   }
/*      */   
/*      */   public int getRevengeTimer() {
/*  434 */     return this.revengeTimer;
/*      */   }
/*      */   
/*      */   public void setRevengeTarget(EntityLivingBase livingBase) {
/*  438 */     this.entityLivingToAttack = livingBase;
/*  439 */     this.revengeTimer = this.ticksExisted;
/*      */   }
/*      */   
/*      */   public EntityLivingBase getLastAttacker() {
/*  443 */     return this.lastAttacker;
/*      */   }
/*      */   
/*      */   public void setLastAttacker(Entity entityIn) {
/*  447 */     if (entityIn instanceof EntityLivingBase) {
/*  448 */       this.lastAttacker = (EntityLivingBase)entityIn;
/*      */     } else {
/*  450 */       this.lastAttacker = null;
/*      */     } 
/*      */     
/*  453 */     this.lastAttackerTime = this.ticksExisted;
/*      */   }
/*      */   
/*      */   public int getLastAttackerTime() {
/*  457 */     return this.lastAttackerTime;
/*      */   }
/*      */   
/*      */   public int getAge() {
/*  461 */     return this.entityAge;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  468 */     tagCompound.setFloat("HealF", getHealth());
/*  469 */     tagCompound.setShort("Health", (short)(int)Math.ceil(getHealth()));
/*  470 */     tagCompound.setShort("HurtTime", (short)this.hurtTime);
/*  471 */     tagCompound.setInteger("HurtByTimestamp", this.revengeTimer);
/*  472 */     tagCompound.setShort("DeathTime", (short)this.deathTime);
/*  473 */     tagCompound.setFloat("AbsorptionAmount", getAbsorptionAmount());
/*      */     
/*  475 */     for (ItemStack itemstack : getInventory()) {
/*  476 */       if (itemstack != null) {
/*  477 */         this.attributeMap.removeAttributeModifiers(itemstack.getAttributeModifiers());
/*      */       }
/*      */     } 
/*      */     
/*  481 */     tagCompound.setTag("Attributes", (NBTBase)SharedMonsterAttributes.writeBaseAttributeMapToNBT(getAttributeMap()));
/*      */     
/*  483 */     for (ItemStack itemstack1 : getInventory()) {
/*  484 */       if (itemstack1 != null) {
/*  485 */         this.attributeMap.applyAttributeModifiers(itemstack1.getAttributeModifiers());
/*      */       }
/*      */     } 
/*      */     
/*  489 */     if (!this.activePotionsMap.isEmpty()) {
/*  490 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/*  492 */       for (PotionEffect potioneffect : this.activePotionsMap.values()) {
/*  493 */         nbttaglist.appendTag((NBTBase)potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*      */       }
/*      */       
/*  496 */       tagCompound.setTag("ActiveEffects", (NBTBase)nbttaglist);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  504 */     setAbsorptionAmount(tagCompund.getFloat("AbsorptionAmount"));
/*      */     
/*  506 */     if (tagCompund.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote) {
/*  507 */       SharedMonsterAttributes.setAttributeModifiers(getAttributeMap(), tagCompund.getTagList("Attributes", 10));
/*      */     }
/*      */     
/*  510 */     if (tagCompund.hasKey("ActiveEffects", 9)) {
/*  511 */       NBTTagList nbttaglist = tagCompund.getTagList("ActiveEffects", 10);
/*      */       
/*  513 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  514 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  515 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*      */         
/*  517 */         if (potioneffect != null) {
/*  518 */           this.activePotionsMap.put(Integer.valueOf(potioneffect.getPotionID()), potioneffect);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  523 */     if (tagCompund.hasKey("HealF", 99)) {
/*  524 */       setHealth(tagCompund.getFloat("HealF"));
/*      */     } else {
/*  526 */       NBTBase nbtbase = tagCompund.getTag("Health");
/*      */       
/*  528 */       if (nbtbase == null) {
/*  529 */         setHealth(getMaxHealth());
/*  530 */       } else if (nbtbase.getId() == 5) {
/*  531 */         setHealth(((NBTTagFloat)nbtbase).getFloat());
/*  532 */       } else if (nbtbase.getId() == 2) {
/*  533 */         setHealth(((NBTTagShort)nbtbase).getShort());
/*      */       } 
/*      */     } 
/*      */     
/*  537 */     this.hurtTime = tagCompund.getShort("HurtTime");
/*  538 */     this.deathTime = tagCompund.getShort("DeathTime");
/*  539 */     this.revengeTimer = tagCompund.getInteger("HurtByTimestamp");
/*      */   }
/*      */   
/*      */   protected void updatePotionEffects() {
/*  543 */     Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
/*      */     
/*  545 */     while (iterator.hasNext()) {
/*  546 */       Integer integer = iterator.next();
/*  547 */       PotionEffect potioneffect = this.activePotionsMap.get(integer);
/*      */       
/*  549 */       if (!potioneffect.onUpdate(this)) {
/*  550 */         if (!this.worldObj.isRemote) {
/*  551 */           iterator.remove();
/*  552 */           onFinishedPotionEffect(potioneffect);
/*      */         }  continue;
/*  554 */       }  if (potioneffect.getDuration() % 600 == 0) {
/*  555 */         onChangedPotionEffect(potioneffect, false);
/*      */       }
/*      */     } 
/*      */     
/*  559 */     if (this.potionsNeedUpdate) {
/*  560 */       if (!this.worldObj.isRemote) {
/*  561 */         updatePotionMetadata();
/*      */       }
/*      */       
/*  564 */       this.potionsNeedUpdate = false;
/*      */     } 
/*      */     
/*  567 */     int i = this.dataWatcher.getWatchableObjectInt(7);
/*  568 */     boolean flag1 = (this.dataWatcher.getWatchableObjectByte(8) > 0);
/*      */     
/*  570 */     if (i > 0) {
/*  571 */       int j; boolean flag = false;
/*      */       
/*  573 */       if (!isInvisible()) {
/*  574 */         flag = this.rand.nextBoolean();
/*      */       } else {
/*  576 */         flag = (this.rand.nextInt(15) == 0);
/*      */       } 
/*      */       
/*  579 */       if (flag1) {
/*  580 */         j = flag & ((this.rand.nextInt(5) == 0) ? 1 : 0);
/*      */       }
/*      */       
/*  583 */       if (j != 0 && i > 0) {
/*  584 */         double d0 = (i >> 16 & 0xFF) / 255.0D;
/*  585 */         double d1 = (i >> 8 & 0xFF) / 255.0D;
/*  586 */         double d2 = (i >> 0 & 0xFF) / 255.0D;
/*  587 */         this.worldObj.spawnParticle(flag1 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/*  597 */     if (this.activePotionsMap.isEmpty()) {
/*  598 */       resetPotionEffectMetadata();
/*  599 */       setInvisible(false);
/*      */     } else {
/*  601 */       int i = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
/*  602 */       this.dataWatcher.updateObject(8, Byte.valueOf((byte)(PotionHelper.getAreAmbient(this.activePotionsMap.values()) ? 1 : 0)));
/*  603 */       this.dataWatcher.updateObject(7, Integer.valueOf(i));
/*  604 */       setInvisible(isPotionActive(Potion.invisibility.id));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetPotionEffectMetadata() {
/*  612 */     this.dataWatcher.updateObject(8, Byte.valueOf((byte)0));
/*  613 */     this.dataWatcher.updateObject(7, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   public void clearActivePotions() {
/*  617 */     Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
/*      */     
/*  619 */     while (iterator.hasNext()) {
/*  620 */       Integer integer = iterator.next();
/*  621 */       PotionEffect potioneffect = this.activePotionsMap.get(integer);
/*      */       
/*  623 */       if (!this.worldObj.isRemote) {
/*  624 */         iterator.remove();
/*  625 */         onFinishedPotionEffect(potioneffect);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Collection<PotionEffect> getActivePotionEffects() {
/*  631 */     return this.activePotionsMap.values();
/*      */   }
/*      */   
/*      */   public boolean isPotionActive(int potionId) {
/*  635 */     return this.activePotionsMap.containsKey(Integer.valueOf(potionId));
/*      */   }
/*      */   
/*      */   public boolean isPotionActive(Potion potionIn) {
/*  639 */     return this.activePotionsMap.containsKey(Integer.valueOf(potionIn.id));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PotionEffect getActivePotionEffect(Potion potionIn) {
/*  646 */     return this.activePotionsMap.get(Integer.valueOf(potionIn.id));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPotionEffect(PotionEffect potioneffectIn) {
/*  653 */     if (isPotionApplicable(potioneffectIn)) {
/*  654 */       if (this.activePotionsMap.containsKey(Integer.valueOf(potioneffectIn.getPotionID()))) {
/*  655 */         ((PotionEffect)this.activePotionsMap.get(Integer.valueOf(potioneffectIn.getPotionID()))).combine(potioneffectIn);
/*  656 */         onChangedPotionEffect(this.activePotionsMap.get(Integer.valueOf(potioneffectIn.getPotionID())), true);
/*      */       } else {
/*  658 */         this.activePotionsMap.put(Integer.valueOf(potioneffectIn.getPotionID()), potioneffectIn);
/*  659 */         onNewPotionEffect(potioneffectIn);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
/*  665 */     if (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
/*  666 */       int i = potioneffectIn.getPotionID();
/*      */       
/*  668 */       if (i == Potion.regeneration.id || i == Potion.poison.id) {
/*  669 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  673 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityUndead() {
/*  680 */     return (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePotionEffectClient(int potionId) {
/*  687 */     this.activePotionsMap.remove(Integer.valueOf(potionId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePotionEffect(int potionId) {
/*  694 */     PotionEffect potioneffect = this.activePotionsMap.remove(Integer.valueOf(potionId));
/*      */     
/*  696 */     if (potioneffect != null) {
/*  697 */       onFinishedPotionEffect(potioneffect);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/*  702 */     this.potionsNeedUpdate = true;
/*      */     
/*  704 */     if (!this.worldObj.isRemote) {
/*  705 */       Potion.potionTypes[id.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     }
/*      */   }
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/*  710 */     this.potionsNeedUpdate = true;
/*      */     
/*  712 */     if (p_70695_2_ && !this.worldObj.isRemote) {
/*  713 */       Potion.potionTypes[id.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), id.getAmplifier());
/*  714 */       Potion.potionTypes[id.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect effect) {
/*  719 */     this.potionsNeedUpdate = true;
/*      */     
/*  721 */     if (!this.worldObj.isRemote) {
/*  722 */       Potion.potionTypes[effect.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), effect.getAmplifier());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void heal(float healAmount) {
/*  730 */     float f = getHealth();
/*      */     
/*  732 */     if (f > 0.0F) {
/*  733 */       setHealth(f + healAmount);
/*      */     }
/*      */   }
/*      */   
/*      */   public final float getHealth() {
/*  738 */     return this.dataWatcher.getWatchableObjectFloat(6);
/*      */   }
/*      */   
/*      */   public void setHealth(float health) {
/*  742 */     this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(health, 0.0F, getMaxHealth())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  749 */     if (isEntityInvulnerable(source))
/*  750 */       return false; 
/*  751 */     if (this.worldObj.isRemote) {
/*  752 */       return false;
/*      */     }
/*  754 */     this.entityAge = 0;
/*      */     
/*  756 */     if (getHealth() <= 0.0F)
/*  757 */       return false; 
/*  758 */     if (source.isFireDamage() && isPotionActive(Potion.fireResistance)) {
/*  759 */       return false;
/*      */     }
/*  761 */     if ((source == DamageSource.anvil || source == DamageSource.fallingBlock) && getEquipmentInSlot(4) != null) {
/*  762 */       getEquipmentInSlot(4).damageItem((int)(amount * 4.0F + this.rand.nextFloat() * amount * 2.0F), this);
/*  763 */       amount *= 0.75F;
/*      */     } 
/*      */     
/*  766 */     this.limbSwingAmount = 1.5F;
/*  767 */     boolean flag = true;
/*      */     
/*  769 */     if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F) {
/*  770 */       if (amount <= this.lastDamage) {
/*  771 */         return false;
/*      */       }
/*      */       
/*  774 */       damageEntity(source, amount - this.lastDamage);
/*  775 */       this.lastDamage = amount;
/*  776 */       flag = false;
/*      */     } else {
/*  778 */       this.lastDamage = amount;
/*  779 */       this.hurtResistantTime = this.maxHurtResistantTime;
/*  780 */       damageEntity(source, amount);
/*  781 */       this.hurtTime = this.maxHurtTime = 10;
/*      */     } 
/*      */     
/*  784 */     this.attackedAtYaw = 0.0F;
/*  785 */     Entity entity = source.getEntity();
/*      */     
/*  787 */     if (entity != null) {
/*  788 */       if (entity instanceof EntityLivingBase) {
/*  789 */         setRevengeTarget((EntityLivingBase)entity);
/*      */       }
/*      */       
/*  792 */       if (entity instanceof EntityPlayer) {
/*  793 */         this.recentlyHit = 100;
/*  794 */         this.attackingPlayer = (EntityPlayer)entity;
/*  795 */       } else if (entity instanceof EntityWolf) {
/*  796 */         EntityWolf entitywolf = (EntityWolf)entity;
/*      */         
/*  798 */         if (entitywolf.isTamed()) {
/*  799 */           this.recentlyHit = 100;
/*  800 */           this.attackingPlayer = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  805 */     if (flag) {
/*  806 */       this.worldObj.setEntityState(this, (byte)2);
/*      */       
/*  808 */       if (source != DamageSource.drown) {
/*  809 */         setBeenAttacked();
/*      */       }
/*      */       
/*  812 */       if (entity != null) {
/*  813 */         double d1 = entity.posX - this.posX;
/*      */         
/*      */         double d0;
/*  816 */         for (d0 = entity.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
/*  817 */           d1 = (Math.random() - Math.random()) * 0.01D;
/*      */         }
/*      */         
/*  820 */         this.attackedAtYaw = (float)(MathHelper.atan2(d0, d1) * 180.0D / Math.PI - this.rotationYaw);
/*  821 */         knockBack(entity, amount, d1, d0);
/*      */       } else {
/*  823 */         this.attackedAtYaw = ((int)(Math.random() * 2.0D) * 180);
/*      */       } 
/*      */     } 
/*      */     
/*  827 */     if (getHealth() <= 0.0F) {
/*  828 */       String s = getDeathSound();
/*      */       
/*  830 */       if (flag && s != null) {
/*  831 */         playSound(s, getSoundVolume(), getSoundPitch());
/*      */       }
/*      */       
/*  834 */       onDeath(source);
/*      */     } else {
/*  836 */       String s1 = getHurtSound();
/*      */       
/*  838 */       if (flag && s1 != null) {
/*  839 */         playSound(s1, getSoundVolume(), getSoundPitch());
/*      */       }
/*      */     } 
/*      */     
/*  843 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderBrokenItemStack(ItemStack stack) {
/*  852 */     playSound("random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);
/*      */     
/*  854 */     for (int i = 0; i < 5; i++) {
/*  855 */       Vec3 vec3 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*  856 */       vec3 = vec3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  857 */       vec3 = vec3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  858 */       double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/*  859 */       Vec3 vec31 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/*  860 */       vec31 = vec31.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  861 */       vec31 = vec31.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  862 */       vec31 = vec31.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*  863 */       this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(stack.getItem()) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  871 */     Entity entity = cause.getEntity();
/*  872 */     EntityLivingBase entitylivingbase = getAttackingEntity();
/*      */     
/*  874 */     if (this.scoreValue >= 0 && entitylivingbase != null) {
/*  875 */       entitylivingbase.addToPlayerScore(this, this.scoreValue);
/*      */     }
/*      */     
/*  878 */     if (entity != null) {
/*  879 */       entity.onKillEntity(this);
/*      */     }
/*      */     
/*  882 */     this.dead = true;
/*  883 */     this._combatTracker.reset();
/*      */     
/*  885 */     if (!this.worldObj.isRemote) {
/*  886 */       int i = 0;
/*      */       
/*  888 */       if (entity instanceof EntityPlayer) {
/*  889 */         i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
/*      */       }
/*      */       
/*  892 */       if (canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
/*  893 */         dropFewItems((this.recentlyHit > 0), i);
/*  894 */         dropEquipment((this.recentlyHit > 0), i);
/*      */         
/*  896 */         if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025F + i * 0.01F) {
/*  897 */           addRandomDrop();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  902 */     this.worldObj.setEntityState(this, (byte)3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void knockBack(Entity entityIn, float p_70653_2_, double p_70653_3_, double p_70653_5_) {
/*  919 */     if (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
/*  920 */       this.isAirBorne = true;
/*  921 */       float f = MathHelper.sqrt_double(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
/*  922 */       float f1 = 0.4F;
/*  923 */       this.motionX /= 2.0D;
/*  924 */       this.motionY /= 2.0D;
/*  925 */       this.motionZ /= 2.0D;
/*  926 */       this.motionX -= p_70653_3_ / f * f1;
/*  927 */       this.motionY += f1;
/*  928 */       this.motionZ -= p_70653_5_ / f * f1;
/*      */       
/*  930 */       if (this.motionY > 0.4000000059604645D) {
/*  931 */         this.motionY = 0.4000000059604645D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  940 */     return "game.neutral.hurt";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  947 */     return "game.neutral.die";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addRandomDrop() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnLadder() {
/*  970 */     int i = MathHelper.floor_double(this.posX);
/*  971 */     int j = MathHelper.floor_double((getEntityBoundingBox()).minY);
/*  972 */     int k = MathHelper.floor_double(this.posZ);
/*  973 */     Block block = this.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();
/*  974 */     return ((block == Blocks.ladder || block == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).isSpectator()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/*  981 */     return (!this.isDead && getHealth() > 0.0F);
/*      */   }
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/*  985 */     super.fall(distance, damageMultiplier);
/*  986 */     PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
/*  987 */     float f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0.0F;
/*  988 */     int i = MathHelper.ceiling_float_int((distance - 3.0F - f) * damageMultiplier);
/*      */     
/*  990 */     if (i > 0) {
/*  991 */       playSound(getFallSoundString(i), 1.0F, 1.0F);
/*  992 */       attackEntityFrom(DamageSource.fall, i);
/*  993 */       int j = MathHelper.floor_double(this.posX);
/*  994 */       int k = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  995 */       int l = MathHelper.floor_double(this.posZ);
/*  996 */       Block block = this.worldObj.getBlockState(new BlockPos(j, k, l)).getBlock();
/*      */       
/*  998 */       if (block.getMaterial() != Material.air) {
/*  999 */         Block.SoundType block$soundtype = block.stepSound;
/* 1000 */         playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.5F, block$soundtype.getFrequency() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String getFallSoundString(int damageValue) {
/* 1006 */     return (damageValue > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {
/* 1013 */     this.hurtTime = this.maxHurtTime = 10;
/* 1014 */     this.attackedAtYaw = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/* 1021 */     int i = 0;
/*      */     
/* 1023 */     for (ItemStack itemstack : getInventory()) {
/* 1024 */       if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
/* 1025 */         int j = ((ItemArmor)itemstack.getItem()).damageReduceAmount;
/* 1026 */         i += j;
/*      */       } 
/*      */     } 
/*      */     
/* 1030 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageArmor(float p_70675_1_) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyArmorCalculations(DamageSource source, float damage) {
/* 1040 */     if (!source.isUnblockable()) {
/* 1041 */       int i = 25 - getTotalArmorValue();
/* 1042 */       float f = damage * i;
/* 1043 */       damageArmor(damage);
/* 1044 */       damage = f / 25.0F;
/*      */     } 
/*      */     
/* 1047 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
/* 1054 */     if (source.isDamageAbsolute()) {
/* 1055 */       return damage;
/*      */     }
/* 1057 */     if (isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
/* 1058 */       int i = (getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
/* 1059 */       int j = 25 - i;
/* 1060 */       float f = damage * j;
/* 1061 */       damage = f / 25.0F;
/*      */     } 
/*      */     
/* 1064 */     if (damage <= 0.0F) {
/* 1065 */       return 0.0F;
/*      */     }
/* 1067 */     int k = EnchantmentHelper.getEnchantmentModifierDamage(getInventory(), source);
/*      */     
/* 1069 */     if (k > 20) {
/* 1070 */       k = 20;
/*      */     }
/*      */     
/* 1073 */     if (k > 0 && k <= 20) {
/* 1074 */       int l = 25 - k;
/* 1075 */       float f1 = damage * l;
/* 1076 */       damage = f1 / 25.0F;
/*      */     } 
/*      */     
/* 1079 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1089 */     if (!isEntityInvulnerable(damageSrc)) {
/* 1090 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1091 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1092 */       float f = damageAmount;
/* 1093 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1094 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1096 */       if (damageAmount != 0.0F) {
/* 1097 */         float f1 = getHealth();
/* 1098 */         setHealth(f1 - damageAmount);
/* 1099 */         this._combatTracker.trackDamage(damageSrc, f1, damageAmount);
/* 1100 */         setAbsorptionAmount(getAbsorptionAmount() - damageAmount);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CombatTracker getCombatTracker() {
/* 1109 */     return this._combatTracker;
/*      */   }
/*      */   
/*      */   public EntityLivingBase getAttackingEntity() {
/* 1113 */     return (this._combatTracker.func_94550_c() != null) ? this._combatTracker.func_94550_c() : ((this.attackingPlayer != null) ? (EntityLivingBase)this.attackingPlayer : ((this.entityLivingToAttack != null) ? this.entityLivingToAttack : null));
/*      */   }
/*      */   
/*      */   public final float getMaxHealth() {
/* 1117 */     return (float)getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getArrowCountInEntity() {
/* 1124 */     return this.dataWatcher.getWatchableObjectByte(9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setArrowCountInEntity(int count) {
/* 1131 */     this.dataWatcher.updateObject(9, Byte.valueOf((byte)count));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getArmSwingAnimationEnd() {
/* 1139 */     if (isPotionActive(Potion.digSpeed)) {
/* 1140 */       if (Animations.getInstance.isEnabled()) {
/* 1141 */         return 6 + ((int)(6.0D - 6.0D * ((Double)Animations.getInstance.speed.getValue()).doubleValue()) << ((Double)Animations.getInstance.slowSpeedInt.get()).intValue());
/*      */       }
/* 1143 */       return 6 - 1 + getActivePotionEffect(Potion.digSpeed).getAmplifier();
/*      */     } 
/*      */     
/* 1146 */     if (Animations.getInstance.isEnabled()) {
/* 1147 */       return 6 + ((int)(6.0D - 6.0D * ((Double)Animations.getInstance.speed.getValue()).doubleValue()) << ((Double)Animations.getInstance.slowSpeedInt.get()).intValue());
/*      */     }
/* 1149 */     return isPotionActive(Potion.digSlowdown) ? (6 + (1 + 
/* 1150 */       getActivePotionEffect(Potion.digSlowdown).getAmplifier() << 1)) : 6;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swingItem() {
/* 1160 */     if (!this.isSwingInProgress || this.swingProgressInt >= getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
/* 1161 */       this.swingProgressInt = -1;
/* 1162 */       this.isSwingInProgress = true;
/*      */       
/* 1164 */       if (this.worldObj instanceof WorldServer) {
/* 1165 */         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S0BPacketAnimation(this, 0));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/* 1171 */     if (id == 2) {
/* 1172 */       this.limbSwingAmount = 1.5F;
/* 1173 */       this.hurtResistantTime = this.maxHurtResistantTime;
/* 1174 */       this.hurtTime = this.maxHurtTime = 10;
/* 1175 */       this.attackedAtYaw = 0.0F;
/* 1176 */       String s = getHurtSound();
/*      */       
/* 1178 */       if (s != null) {
/* 1179 */         playSound(getHurtSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */       
/* 1182 */       attackEntityFrom(DamageSource.generic, 0.0F);
/* 1183 */     } else if (id == 3) {
/* 1184 */       String s1 = getDeathSound();
/*      */       
/* 1186 */       if (s1 != null) {
/* 1187 */         playSound(getDeathSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */       
/* 1190 */       setHealth(0.0F);
/* 1191 */       onDeath(DamageSource.generic);
/*      */     } else {
/* 1193 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/* 1201 */     attackEntityFrom(DamageSource.outOfWorld, 4.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateArmSwingProgress() {
/* 1208 */     int i = getArmSwingAnimationEnd();
/*      */     
/* 1210 */     if (this.isSwingInProgress) {
/* 1211 */       this.swingProgressInt++;
/*      */       
/* 1213 */       if (this.swingProgressInt >= i) {
/* 1214 */         this.swingProgressInt = 0;
/* 1215 */         this.isSwingInProgress = false;
/*      */       } 
/*      */     } else {
/* 1218 */       this.swingProgressInt = 0;
/*      */     } 
/*      */     
/* 1221 */     this.swingProgress = this.swingProgressInt / i;
/*      */   }
/*      */   
/*      */   public IAttributeInstance getEntityAttribute(IAttribute attribute) {
/* 1225 */     return getAttributeMap().getAttributeInstance(attribute);
/*      */   }
/*      */   
/*      */   public BaseAttributeMap getAttributeMap() {
/* 1229 */     if (this.attributeMap == null) {
/* 1230 */       this.attributeMap = (BaseAttributeMap)new ServersideAttributeMap();
/*      */     }
/*      */     
/* 1233 */     return this.attributeMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumCreatureAttribute getCreatureAttribute() {
/* 1240 */     return EnumCreatureAttribute.UNDEFINED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getHeldItem();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getEquipmentInSlot(int paramInt);
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getCurrentArmor(int paramInt);
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setCurrentItemOrArmor(int paramInt, ItemStack paramItemStack);
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 1264 */     super.setSprinting(sprinting);
/* 1265 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*      */     
/* 1267 */     if (iattributeinstance.getModifier(sprintingSpeedBoostModifierUUID) != null) {
/* 1268 */       iattributeinstance.removeModifier(sprintingSpeedBoostModifier);
/*      */     }
/*      */     
/* 1271 */     if (sprinting) {
/* 1272 */       iattributeinstance.applyModifier(sprintingSpeedBoostModifier);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack[] getInventory();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/* 1285 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundPitch() {
/* 1292 */     return isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/* 1299 */     return (getHealth() <= 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismountEntity(Entity entityIn) {
/* 1306 */     double d0 = entityIn.posX;
/* 1307 */     double d1 = (entityIn.getEntityBoundingBox()).minY + entityIn.height;
/* 1308 */     double d2 = entityIn.posZ;
/* 1309 */     int i = 1;
/*      */     
/* 1311 */     for (int j = -i; j <= i; j++) {
/* 1312 */       for (int k = -i; k < i; k++) {
/* 1313 */         if (j != 0 || k != 0) {
/* 1314 */           int l = (int)(this.posX + j);
/* 1315 */           int i1 = (int)(this.posZ + k);
/* 1316 */           AxisAlignedBB axisalignedbb = getEntityBoundingBox().offset(j, 1.0D, k);
/*      */           
/* 1318 */           if (this.worldObj.getCollisionBoxes(axisalignedbb).isEmpty()) {
/* 1319 */             if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(l, (int)this.posY, i1))) {
/* 1320 */               setPositionAndUpdate(this.posX + j, this.posY + 1.0D, this.posZ + k);
/*      */               
/*      */               return;
/*      */             } 
/* 1324 */             if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(l, (int)this.posY - 1, i1)) || this.worldObj.getBlockState(new BlockPos(l, (int)this.posY - 1, i1)).getBlock().getMaterial() == Material.water) {
/* 1325 */               d0 = this.posX + j;
/* 1326 */               d1 = this.posY + 1.0D;
/* 1327 */               d2 = this.posZ + k;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1334 */     setPositionAndUpdate(d0, d1, d2);
/*      */   }
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 1338 */     return false;
/*      */   }
/*      */   
/*      */   protected float getJumpUpwardsMotion() {
/* 1342 */     return 0.42F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void jump() {
/* 1349 */     this.motionY = getJumpUpwardsMotion();
/*      */     
/* 1351 */     if (isPotionActive(Potion.jump)) {
/* 1352 */       this.motionY += ((getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
/*      */     }
/*      */     
/* 1355 */     if (isSprinting()) {
/* 1356 */       float f = this.rotationYaw * 0.017453292F;
/* 1357 */       if (this == (Minecraft.getMinecraft()).thePlayer && 
/* 1358 */         (Minecraft.getMinecraft()).thePlayer.movementYaw != null) {
/* 1359 */         f = (Minecraft.getMinecraft()).thePlayer.movementYaw.floatValue() * 0.017453292F;
/*      */       }
/*      */       
/* 1362 */       this.motionX -= (MathHelper.sin(f) * 0.2F);
/* 1363 */       this.motionZ += (MathHelper.cos(f) * 0.2F);
/*      */     } 
/*      */     
/* 1366 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAITick() {
/* 1373 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */   
/*      */   protected void handleJumpLava() {
/* 1377 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1384 */     if (isServerWorld()) {
/* 1385 */       if (!isInWater() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
/* 1386 */         if (!isInLava() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
/* 1387 */           float f5, f4 = 0.91F;
/*      */           
/* 1389 */           if (this.onGround) {
/* 1390 */             f4 = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*      */           }
/*      */           
/* 1393 */           float f = 0.16277136F / f4 * f4 * f4;
/*      */ 
/*      */           
/* 1396 */           if (this.onGround) {
/* 1397 */             f5 = getAIMoveSpeed() * f;
/*      */           } else {
/* 1399 */             f5 = this.jumpMovementFactor;
/*      */           } 
/*      */           
/* 1402 */           moveFlying(strafe, forward, f5);
/* 1403 */           f4 = 0.91F;
/*      */           
/* 1405 */           if (this.onGround) {
/* 1406 */             f4 = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*      */           }
/*      */           
/* 1409 */           if (isOnLadder()) {
/* 1410 */             float f6 = 0.15F;
/* 1411 */             this.motionX = MathHelper.clamp_double(this.motionX, -f6, f6);
/* 1412 */             this.motionZ = MathHelper.clamp_double(this.motionZ, -f6, f6);
/* 1413 */             this.fallDistance = 0.0F;
/*      */             
/* 1415 */             if (this.motionY < -0.15D) {
/* 1416 */               this.motionY = -0.15D;
/*      */             }
/*      */             
/* 1419 */             boolean flag = (isSneaking() && this instanceof EntityPlayer);
/*      */             
/* 1421 */             if (flag && this.motionY < 0.0D) {
/* 1422 */               this.motionY = 0.0D;
/*      */             }
/*      */           } 
/*      */           
/* 1426 */           moveEntity(this.motionX, this.motionY, this.motionZ);
/*      */           
/* 1428 */           if (this.isCollidedHorizontally && isOnLadder()) {
/* 1429 */             this.motionY = 0.2D;
/*      */           }
/*      */           
/* 1432 */           if (this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, 0, (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded())) {
/* 1433 */             if (this.posY > 0.0D) {
/* 1434 */               this.motionY = -0.1D;
/*      */             } else {
/* 1436 */               this.motionY = 0.0D;
/*      */             } 
/*      */           } else {
/* 1439 */             this.motionY -= 0.08D;
/*      */           } 
/*      */           
/* 1442 */           this.motionY *= 0.9800000190734863D;
/* 1443 */           this.motionX *= f4;
/* 1444 */           this.motionZ *= f4;
/*      */         } else {
/* 1446 */           double d1 = this.posY;
/* 1447 */           moveFlying(strafe, forward, 0.02F);
/* 1448 */           moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1449 */           this.motionX *= 0.5D;
/* 1450 */           this.motionY *= 0.5D;
/* 1451 */           this.motionZ *= 0.5D;
/* 1452 */           this.motionY -= 0.02D;
/*      */           
/* 1454 */           if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d1, this.motionZ)) {
/* 1455 */             this.motionY = 0.30000001192092896D;
/*      */           }
/*      */         } 
/*      */       } else {
/* 1459 */         double d0 = this.posY;
/* 1460 */         float f1 = 0.8F;
/* 1461 */         float f2 = 0.02F;
/* 1462 */         float f3 = EnchantmentHelper.getDepthStriderModifier(this);
/*      */         
/* 1464 */         if (f3 > 3.0F) {
/* 1465 */           f3 = 3.0F;
/*      */         }
/*      */         
/* 1468 */         if (!this.onGround) {
/* 1469 */           f3 *= 0.5F;
/*      */         }
/*      */         
/* 1472 */         if (f3 > 0.0F) {
/* 1473 */           f1 += (0.54600006F - f1) * f3 / 3.0F;
/* 1474 */           f2 += (getAIMoveSpeed() - f2) * f3 / 3.0F;
/*      */         } 
/*      */         
/* 1477 */         moveFlying(strafe, forward, f2);
/* 1478 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1479 */         this.motionX *= f1;
/* 1480 */         this.motionY *= 0.800000011920929D;
/* 1481 */         this.motionZ *= f1;
/* 1482 */         this.motionY -= 0.02D;
/*      */         
/* 1484 */         if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ)) {
/* 1485 */           this.motionY = 0.30000001192092896D;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1490 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 1491 */     double d2 = this.posX - this.prevPosX;
/* 1492 */     double d3 = this.posZ - this.prevPosZ;
/* 1493 */     float f7 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4.0F;
/*      */     
/* 1495 */     if (f7 > 1.0F) {
/* 1496 */       f7 = 1.0F;
/*      */     }
/*      */     
/* 1499 */     this.limbSwingAmount += (f7 - this.limbSwingAmount) * 0.4F;
/* 1500 */     this.limbSwing += this.limbSwingAmount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 1507 */     return this.landMovementFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/* 1514 */     this.landMovementFactor = speedIn;
/*      */   }
/*      */   
/*      */   public boolean attackEntityAsMob(Entity entityIn) {
/* 1518 */     setLastAttacker(entityIn);
/* 1519 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 1526 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/* 1533 */     super.onUpdate();
/*      */     
/* 1535 */     if (!this.worldObj.isRemote) {
/* 1536 */       int i = getArrowCountInEntity();
/*      */       
/* 1538 */       if (i > 0) {
/* 1539 */         if (this.arrowHitTimer <= 0) {
/* 1540 */           this.arrowHitTimer = 20 * (30 - i);
/*      */         }
/*      */         
/* 1543 */         this.arrowHitTimer--;
/*      */         
/* 1545 */         if (this.arrowHitTimer <= 0) {
/* 1546 */           setArrowCountInEntity(i - 1);
/*      */         }
/*      */       } 
/*      */       
/* 1550 */       for (int j = 0; j < 5; j++) {
/* 1551 */         ItemStack itemstack = this.previousEquipment[j];
/* 1552 */         ItemStack itemstack1 = getEquipmentInSlot(j);
/*      */         
/* 1554 */         if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/* 1555 */           ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S04PacketEntityEquipment(getEntityId(), j, itemstack1));
/*      */           
/* 1557 */           if (itemstack != null) {
/* 1558 */             this.attributeMap.removeAttributeModifiers(itemstack.getAttributeModifiers());
/*      */           }
/*      */           
/* 1561 */           if (itemstack1 != null) {
/* 1562 */             this.attributeMap.applyAttributeModifiers(itemstack1.getAttributeModifiers());
/*      */           }
/*      */           
/* 1565 */           this.previousEquipment[j] = (itemstack1 == null) ? null : itemstack1.copy();
/*      */         } 
/*      */       } 
/*      */       
/* 1569 */       if (this.ticksExisted % 20 == 0) {
/* 1570 */         this._combatTracker.reset();
/*      */       }
/*      */     } 
/*      */     
/* 1574 */     onLivingUpdate();
/* 1575 */     double d0 = this.posX - this.prevPosX;
/* 1576 */     double d1 = this.posZ - this.prevPosZ;
/* 1577 */     float f = (float)(d0 * d0 + d1 * d1);
/* 1578 */     float f1 = this.renderYawOffset;
/* 1579 */     float f2 = 0.0F;
/* 1580 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 1581 */     float f3 = 0.0F;
/*      */     
/* 1583 */     if (f > 0.0025000002F) {
/* 1584 */       f3 = 1.0F;
/* 1585 */       f2 = (float)Math.sqrt(f) * 3.0F;
/* 1586 */       f1 = (float)MathHelper.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
/*      */     } 
/*      */     
/* 1589 */     if (this.swingProgress > 0.0F) {
/* 1590 */       f1 = this.rotationYaw;
/*      */     }
/*      */     
/* 1593 */     if (!this.onGround) {
/* 1594 */       f3 = 0.0F;
/*      */     }
/*      */     
/* 1597 */     this.onGroundSpeedFactor += (f3 - this.onGroundSpeedFactor) * 0.3F;
/* 1598 */     this.worldObj.theProfiler.startSection("headTurn");
/* 1599 */     f2 = updateDistance(f1, f2);
/* 1600 */     this.worldObj.theProfiler.endSection();
/* 1601 */     this.worldObj.theProfiler.startSection("rangeChecks");
/*      */     
/* 1603 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 1604 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1607 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 1608 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1611 */     while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
/* 1612 */       this.prevRenderYawOffset -= 360.0F;
/*      */     }
/*      */     
/* 1615 */     while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
/* 1616 */       this.prevRenderYawOffset += 360.0F;
/*      */     }
/*      */     
/* 1619 */     while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
/* 1620 */       this.prevRotationPitch -= 360.0F;
/*      */     }
/*      */     
/* 1623 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 1624 */       this.prevRotationPitch += 360.0F;
/*      */     }
/*      */     
/* 1627 */     while (this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
/* 1628 */       this.prevRotationYawHead -= 360.0F;
/*      */     }
/*      */     
/* 1631 */     while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
/* 1632 */       this.prevRotationYawHead += 360.0F;
/*      */     }
/*      */     
/* 1635 */     this.worldObj.theProfiler.endSection();
/* 1636 */     this.movedDistance += f2;
/*      */   }
/*      */   
/*      */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/* 1640 */     float f = MathHelper.wrapAngleTo180_float(p_110146_1_ - this.renderYawOffset);
/* 1641 */     this.renderYawOffset += f * 0.3F;
/* 1642 */     float f1 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
/* 1643 */     boolean flag = (f1 < -90.0F || f1 >= 90.0F);
/*      */     
/* 1645 */     if (f1 < -75.0F) {
/* 1646 */       f1 = -75.0F;
/*      */     }
/*      */     
/* 1649 */     if (f1 >= 75.0F) {
/* 1650 */       f1 = 75.0F;
/*      */     }
/*      */     
/* 1653 */     this.renderYawOffset = this.rotationYaw - f1;
/*      */     
/* 1655 */     if (f1 * f1 > 2500.0F) {
/* 1656 */       this.renderYawOffset += f1 * 0.2F;
/*      */     }
/*      */     
/* 1659 */     if (flag) {
/* 1660 */       p_110146_2_ *= -1.0F;
/*      */     }
/*      */     
/* 1663 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/* 1671 */     if (this.jumpTicks > 0) {
/* 1672 */       this.jumpTicks--;
/*      */     }
/*      */     
/* 1675 */     if (this.newPosRotationIncrements > 0) {
/* 1676 */       double d0 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
/* 1677 */       double d1 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
/* 1678 */       double d2 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
/* 1679 */       double d3 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
/* 1680 */       this.rotationYaw = (float)(this.rotationYaw + d3 / this.newPosRotationIncrements);
/* 1681 */       this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
/* 1682 */       this.newPosRotationIncrements--;
/* 1683 */       setPosition(d0, d1, d2);
/* 1684 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 1685 */     } else if (!isServerWorld()) {
/* 1686 */       this.motionX *= 0.98D;
/* 1687 */       this.motionY *= 0.98D;
/* 1688 */       this.motionZ *= 0.98D;
/*      */     } 
/*      */     
/* 1691 */     if (Math.abs(this.motionX) < 0.005D) {
/* 1692 */       this.motionX = 0.0D;
/*      */     }
/*      */     
/* 1695 */     if (Math.abs(this.motionY) < 0.005D) {
/* 1696 */       this.motionY = 0.0D;
/*      */     }
/*      */     
/* 1699 */     if (Math.abs(this.motionZ) < 0.005D) {
/* 1700 */       this.motionZ = 0.0D;
/*      */     }
/*      */     
/* 1703 */     this.worldObj.theProfiler.startSection("ai");
/*      */     
/* 1705 */     if (isMovementBlocked()) {
/* 1706 */       this.isJumping = false;
/* 1707 */       this.moveStrafing = 0.0F;
/* 1708 */       this.moveForward = 0.0F;
/* 1709 */       this.randomYawVelocity = 0.0F;
/* 1710 */     } else if (isServerWorld()) {
/* 1711 */       this.worldObj.theProfiler.startSection("newAi");
/* 1712 */       updateEntityActionState();
/* 1713 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/* 1716 */     this.worldObj.theProfiler.endSection();
/* 1717 */     this.worldObj.theProfiler.startSection("jump");
/*      */     
/* 1719 */     if (this.isJumping) {
/* 1720 */       if (isInWater()) {
/* 1721 */         updateAITick();
/* 1722 */       } else if (isInLava()) {
/* 1723 */         handleJumpLava();
/* 1724 */       } else if (this.onGround && this.jumpTicks == 0) {
/* 1725 */         jump();
/* 1726 */         this
/*      */           
/* 1728 */           .jumpTicks = NoJumpDelay.getInstance.isEnabled() ? NoJumpDelay.getInstance.getJumpDelay() : 10;
/*      */       } 
/*      */     } else {
/* 1731 */       this.jumpTicks = 0;
/*      */     } 
/*      */     
/* 1734 */     this.worldObj.theProfiler.endSection();
/* 1735 */     this.worldObj.theProfiler.startSection("travel");
/* 1736 */     this.moveStrafing *= 0.98F;
/* 1737 */     this.moveForward *= 0.98F;
/* 1738 */     this.randomYawVelocity *= 0.9F;
/* 1739 */     moveEntityWithHeading(this.moveStrafing, this.moveForward);
/* 1740 */     this.worldObj.theProfiler.endSection();
/* 1741 */     this.worldObj.theProfiler.startSection("push");
/*      */     
/* 1743 */     if (!this.worldObj.isRemote) {
/* 1744 */       collideWithNearbyEntities();
/*      */     }
/*      */     
/* 1747 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {}
/*      */   
/*      */   protected void collideWithNearbyEntities() {
/* 1754 */     List<Entity> list = this.worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
/*      */             public boolean apply(Entity p_apply_1_) {
/* 1756 */               return p_apply_1_.canBePushed();
/*      */             }
/*      */           }));
/*      */     
/* 1760 */     if (!list.isEmpty()) {
/* 1761 */       for (int i = 0; i < list.size(); i++) {
/* 1762 */         Entity entity = list.get(i);
/* 1763 */         collideWithEntity(entity);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void collideWithEntity(Entity entityIn) {
/* 1769 */     entityIn.applyEntityCollision(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/* 1776 */     if (this.ridingEntity != null && entityIn == null) {
/* 1777 */       if (!this.worldObj.isRemote) {
/* 1778 */         dismountEntity(this.ridingEntity);
/*      */       }
/*      */       
/* 1781 */       if (this.ridingEntity != null) {
/* 1782 */         this.ridingEntity.riddenByEntity = null;
/*      */       }
/*      */       
/* 1785 */       this.ridingEntity = null;
/*      */     } else {
/* 1787 */       super.mountEntity(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 1795 */     super.updateRidden();
/* 1796 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 1797 */     this.onGroundSpeedFactor = 0.0F;
/* 1798 */     this.fallDistance = 0.0F;
/*      */   }
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 1802 */     this.newPosX = x;
/* 1803 */     this.newPosY = y;
/* 1804 */     this.newPosZ = z;
/* 1805 */     this.newRotationYaw = yaw;
/* 1806 */     this.newRotationPitch = pitch;
/* 1807 */     this.newPosRotationIncrements = posRotationIncrements;
/*      */   }
/*      */   
/*      */   public void setJumping(boolean jumping) {
/* 1811 */     this.isJumping = jumping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
/* 1818 */     if (!p_71001_1_.isDead && !this.worldObj.isRemote) {
/* 1819 */       EntityTracker entitytracker = ((WorldServer)this.worldObj).getEntityTracker();
/*      */       
/* 1821 */       if (p_71001_1_ instanceof net.minecraft.entity.item.EntityItem) {
/* 1822 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */       
/* 1825 */       if (p_71001_1_ instanceof net.minecraft.entity.projectile.EntityArrow) {
/* 1826 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */       
/* 1829 */       if (p_71001_1_ instanceof EntityXPOrb) {
/* 1830 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canEntityBeSeen(Entity entityIn) {
/* 1839 */     return (this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ), new Vec3(entityIn.posX, entityIn.posY + entityIn.getEyeHeight(), entityIn.posZ)) == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLookVec() {
/* 1846 */     return getLook(1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLook(float partialTicks) {
/* 1853 */     if (MouseDelayFix.getInstance.isEnabled() && this instanceof net.minecraft.client.entity.EntityPlayerSP) {
/* 1854 */       return super.getLook(partialTicks);
/*      */     }
/* 1856 */     if (partialTicks == 1.0F) {
/* 1857 */       return getVectorForRotation(this.rotationPitch, this.rotationYawHead);
/*      */     }
/* 1859 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 1860 */     float f1 = this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * partialTicks;
/* 1861 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSwingProgress(float partialTickTime) {
/* 1869 */     float f = this.swingProgress - this.prevSwingProgress;
/*      */     
/* 1871 */     if (f < 0.0F) {
/* 1872 */       f++;
/*      */     }
/*      */     
/* 1875 */     return this.prevSwingProgress + f * partialTickTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 1882 */     return !this.worldObj.isRemote;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1889 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 1896 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 1903 */     this.velocityChanged = (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
/*      */   }
/*      */   
/*      */   public float getRotationYawHead() {
/* 1907 */     return this.rotationYawHead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {
/* 1914 */     this.rotationYawHead = rotation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderYawOffset(float offset) {
/* 1923 */     this.renderYawOffset = offset;
/*      */   }
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 1927 */     return this.absorptionAmount;
/*      */   }
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 1931 */     if (amount < 0.0F) {
/* 1932 */       amount = 0.0F;
/*      */     }
/*      */     
/* 1935 */     this.absorptionAmount = amount;
/*      */   }
/*      */   
/*      */   public Team getTeam() {
/* 1939 */     return (Team)this.worldObj.getScoreboard().getPlayersTeam(getUniqueID().toString());
/*      */   }
/*      */   
/*      */   public boolean isOnSameTeam(EntityLivingBase otherEntity) {
/* 1943 */     return isOnTeam(otherEntity.getTeam());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnTeam(Team teamIn) {
/* 1950 */     return (getTeam() != null) ? getTeam().isSameTeam(teamIn) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void markPotionsDirty() {
/* 1966 */     this.potionsNeedUpdate = true;
/*      */   }
/*      */   
/*      */   public Map<Integer, PotionEffect> getActivePotionsMap() {
/* 1970 */     return this.activePotionsMap;
/*      */   }
/*      */   
/*      */   public ResourceLocation getLocationSkin() {
/* 1974 */     return DefaultPlayerSkin.getDefaultSkin(getUniqueID());
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\EntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */