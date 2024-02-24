/*      */ package net.minecraft.entity.item;
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.Map;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockRailBase;
/*      */ import net.minecraft.block.BlockRailPowered;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IWorldNameable;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ public abstract class EntityMinecart extends Entity implements IWorldNameable {
/*   32 */   private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
/*      */   
/*      */   private boolean isInReverse;
/*      */   private String entityName;
/*      */   private int turnProgress;
/*      */   private double minecartX;
/*      */   private double minecartY;
/*      */   private double minecartZ;
/*      */   private double minecartYaw;
/*      */   private double minecartPitch;
/*      */   private double velocityX;
/*      */   private double velocityY;
/*      */   private double velocityZ;
/*      */   
/*      */   public EntityMinecart(World worldIn) {
/*   47 */     super(worldIn);
/*   48 */     this.preventEntitySpawning = true;
/*   49 */     setSize(0.98F, 0.7F);
/*      */   }
/*      */ 
/*      */   
/*      */   public static EntityMinecart getMinecart(World worldIn, double x, double y, double z, EnumMinecartType type) {
/*   54 */     switch (type) {
/*      */       
/*      */       case ASCENDING_EAST:
/*   57 */         return new EntityMinecartChest(worldIn, x, y, z);
/*      */       
/*      */       case ASCENDING_WEST:
/*   60 */         return new EntityMinecartFurnace(worldIn, x, y, z);
/*      */       
/*      */       case ASCENDING_NORTH:
/*   63 */         return new EntityMinecartTNT(worldIn, x, y, z);
/*      */       
/*      */       case ASCENDING_SOUTH:
/*   66 */         return (EntityMinecart)new EntityMinecartMobSpawner(worldIn, x, y, z);
/*      */       
/*      */       case null:
/*   69 */         return new EntityMinecartHopper(worldIn, x, y, z);
/*      */       
/*      */       case null:
/*   72 */         return (EntityMinecart)new EntityMinecartCommandBlock(worldIn, x, y, z);
/*      */     } 
/*      */     
/*   75 */     return new EntityMinecartEmpty(worldIn, x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/*   85 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*   90 */     this.dataWatcher.addObject(17, new Integer(0));
/*   91 */     this.dataWatcher.addObject(18, new Integer(1));
/*   92 */     this.dataWatcher.addObject(19, new Float(0.0F));
/*   93 */     this.dataWatcher.addObject(20, new Integer(0));
/*   94 */     this.dataWatcher.addObject(21, new Integer(6));
/*   95 */     this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/*  104 */     return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/*  112 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/*  120 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityMinecart(World worldIn, double x, double y, double z) {
/*  125 */     this(worldIn);
/*  126 */     setPosition(x, y, z);
/*  127 */     this.motionX = 0.0D;
/*  128 */     this.motionY = 0.0D;
/*  129 */     this.motionZ = 0.0D;
/*  130 */     this.prevPosX = x;
/*  131 */     this.prevPosY = y;
/*  132 */     this.prevPosZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/*  140 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  148 */     if (!this.worldObj.isRemote && !this.isDead) {
/*      */       
/*  150 */       if (isEntityInvulnerable(source))
/*      */       {
/*  152 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  156 */       setRollingDirection(-getRollingDirection());
/*  157 */       setRollingAmplitude(10);
/*  158 */       setBeenAttacked();
/*  159 */       setDamage(getDamage() + amount * 10.0F);
/*  160 */       boolean flag = (source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode);
/*      */       
/*  162 */       if (flag || getDamage() > 40.0F) {
/*      */         
/*  164 */         if (this.riddenByEntity != null)
/*      */         {
/*  166 */           this.riddenByEntity.mountEntity((Entity)null);
/*      */         }
/*      */         
/*  169 */         if (flag && !hasCustomName()) {
/*      */           
/*  171 */           setDead();
/*      */         }
/*      */         else {
/*      */           
/*  175 */           killMinecart(source);
/*      */         } 
/*      */       } 
/*      */       
/*  179 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  184 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void killMinecart(DamageSource source) {
/*  190 */     setDead();
/*      */     
/*  192 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*      */       
/*  194 */       ItemStack itemstack = new ItemStack(Items.minecart, 1);
/*      */       
/*  196 */       if (this.entityName != null)
/*      */       {
/*  198 */         itemstack.setStackDisplayName(this.entityName);
/*      */       }
/*      */       
/*  201 */       entityDropItem(itemstack, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {
/*  210 */     setRollingDirection(-getRollingDirection());
/*  211 */     setRollingAmplitude(10);
/*  212 */     setDamage(getDamage() + getDamage() * 10.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/*  220 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/*  228 */     super.setDead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  236 */     if (getRollingAmplitude() > 0)
/*      */     {
/*  238 */       setRollingAmplitude(getRollingAmplitude() - 1);
/*      */     }
/*      */     
/*  241 */     if (getDamage() > 0.0F)
/*      */     {
/*  243 */       setDamage(getDamage() - 1.0F);
/*      */     }
/*      */     
/*  246 */     if (this.posY < -64.0D)
/*      */     {
/*  248 */       kill();
/*      */     }
/*      */     
/*  251 */     if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
/*      */       
/*  253 */       this.worldObj.theProfiler.startSection("portal");
/*  254 */       MinecraftServer minecraftserver = ((WorldServer)this.worldObj).getMinecraftServer();
/*  255 */       int i = getMaxInPortalTime();
/*      */       
/*  257 */       if (this.inPortal) {
/*      */         
/*  259 */         if (minecraftserver.getAllowNether())
/*      */         {
/*  261 */           if (this.ridingEntity == null && this.portalCounter++ >= i) {
/*      */             int j;
/*  263 */             this.portalCounter = i;
/*  264 */             this.timeUntilPortal = getPortalCooldown();
/*      */ 
/*      */             
/*  267 */             if (this.worldObj.provider.getDimensionId() == -1) {
/*      */               
/*  269 */               j = 0;
/*      */             }
/*      */             else {
/*      */               
/*  273 */               j = -1;
/*      */             } 
/*      */             
/*  276 */             travelToDimension(j);
/*      */           } 
/*      */           
/*  279 */           this.inPortal = false;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  284 */         if (this.portalCounter > 0)
/*      */         {
/*  286 */           this.portalCounter -= 4;
/*      */         }
/*      */         
/*  289 */         if (this.portalCounter < 0)
/*      */         {
/*  291 */           this.portalCounter = 0;
/*      */         }
/*      */       } 
/*      */       
/*  295 */       if (this.timeUntilPortal > 0)
/*      */       {
/*  297 */         this.timeUntilPortal--;
/*      */       }
/*      */       
/*  300 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/*  303 */     if (this.worldObj.isRemote) {
/*      */       
/*  305 */       if (this.turnProgress > 0)
/*      */       {
/*  307 */         double d4 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
/*  308 */         double d5 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
/*  309 */         double d6 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
/*  310 */         double d1 = MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw);
/*  311 */         this.rotationYaw = (float)(this.rotationYaw + d1 / this.turnProgress);
/*  312 */         this.rotationPitch = (float)(this.rotationPitch + (this.minecartPitch - this.rotationPitch) / this.turnProgress);
/*  313 */         this.turnProgress--;
/*  314 */         setPosition(d4, d5, d6);
/*  315 */         setRotation(this.rotationYaw, this.rotationPitch);
/*      */       }
/*      */       else
/*      */       {
/*  319 */         setPosition(this.posX, this.posY, this.posZ);
/*  320 */         setRotation(this.rotationYaw, this.rotationPitch);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  325 */       this.prevPosX = this.posX;
/*  326 */       this.prevPosY = this.posY;
/*  327 */       this.prevPosZ = this.posZ;
/*  328 */       this.motionY -= 0.03999999910593033D;
/*  329 */       int k = MathHelper.floor_double(this.posX);
/*  330 */       int l = MathHelper.floor_double(this.posY);
/*  331 */       int i1 = MathHelper.floor_double(this.posZ);
/*      */       
/*  333 */       if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(k, l - 1, i1)))
/*      */       {
/*  335 */         l--;
/*      */       }
/*      */       
/*  338 */       BlockPos blockpos = new BlockPos(k, l, i1);
/*  339 */       IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/*      */       
/*  341 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*      */         
/*  343 */         func_180460_a(blockpos, iblockstate);
/*      */         
/*  345 */         if (iblockstate.getBlock() == Blocks.activator_rail)
/*      */         {
/*  347 */           onActivatorRailPass(k, l, i1, ((Boolean)iblockstate.getValue((IProperty)BlockRailPowered.POWERED)).booleanValue());
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  352 */         moveDerailedMinecart();
/*      */       } 
/*      */       
/*  355 */       doBlockCollisions();
/*  356 */       this.rotationPitch = 0.0F;
/*  357 */       double d0 = this.prevPosX - this.posX;
/*  358 */       double d2 = this.prevPosZ - this.posZ;
/*      */       
/*  360 */       if (d0 * d0 + d2 * d2 > 0.001D) {
/*      */         
/*  362 */         this.rotationYaw = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI);
/*      */         
/*  364 */         if (this.isInReverse)
/*      */         {
/*  366 */           this.rotationYaw += 180.0F;
/*      */         }
/*      */       } 
/*      */       
/*  370 */       double d3 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
/*      */       
/*  372 */       if (d3 < -170.0D || d3 >= 170.0D) {
/*      */         
/*  374 */         this.rotationYaw += 180.0F;
/*  375 */         this.isInReverse = !this.isInReverse;
/*      */       } 
/*      */       
/*  378 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */       
/*  380 */       for (Entity entity : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
/*      */         
/*  382 */         if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart)
/*      */         {
/*  384 */           entity.applyEntityCollision(this);
/*      */         }
/*      */       } 
/*      */       
/*  388 */       if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
/*      */         
/*  390 */         if (this.riddenByEntity.ridingEntity == this)
/*      */         {
/*  392 */           this.riddenByEntity.ridingEntity = null;
/*      */         }
/*      */         
/*  395 */         this.riddenByEntity = null;
/*      */       } 
/*      */       
/*  398 */       handleWaterMovement();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getMaximumSpeed() {
/*  407 */     return 0.4D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void moveDerailedMinecart() {
/*  422 */     double d0 = getMaximumSpeed();
/*  423 */     this.motionX = MathHelper.clamp_double(this.motionX, -d0, d0);
/*  424 */     this.motionZ = MathHelper.clamp_double(this.motionZ, -d0, d0);
/*      */     
/*  426 */     if (this.onGround) {
/*      */       
/*  428 */       this.motionX *= 0.5D;
/*  429 */       this.motionY *= 0.5D;
/*  430 */       this.motionZ *= 0.5D;
/*      */     } 
/*      */     
/*  433 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*      */     
/*  435 */     if (!this.onGround) {
/*      */       
/*  437 */       this.motionX *= 0.949999988079071D;
/*  438 */       this.motionY *= 0.949999988079071D;
/*  439 */       this.motionZ *= 0.949999988079071D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_) {
/*  446 */     this.fallDistance = 0.0F;
/*  447 */     Vec3 vec3 = func_70489_a(this.posX, this.posY, this.posZ);
/*  448 */     this.posY = p_180460_1_.getY();
/*  449 */     boolean flag = false;
/*  450 */     boolean flag1 = false;
/*  451 */     BlockRailBase blockrailbase = (BlockRailBase)p_180460_2_.getBlock();
/*      */     
/*  453 */     if (blockrailbase == Blocks.golden_rail) {
/*      */       
/*  455 */       flag = ((Boolean)p_180460_2_.getValue((IProperty)BlockRailPowered.POWERED)).booleanValue();
/*  456 */       flag1 = !flag;
/*      */     } 
/*      */     
/*  459 */     double d0 = 0.0078125D;
/*  460 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)p_180460_2_.getValue(blockrailbase.getShapeProperty());
/*      */     
/*  462 */     switch (blockrailbase$enumraildirection) {
/*      */       
/*      */       case ASCENDING_EAST:
/*  465 */         this.motionX -= 0.0078125D;
/*  466 */         this.posY++;
/*      */         break;
/*      */       
/*      */       case ASCENDING_WEST:
/*  470 */         this.motionX += 0.0078125D;
/*  471 */         this.posY++;
/*      */         break;
/*      */       
/*      */       case ASCENDING_NORTH:
/*  475 */         this.motionZ += 0.0078125D;
/*  476 */         this.posY++;
/*      */         break;
/*      */       
/*      */       case ASCENDING_SOUTH:
/*  480 */         this.motionZ -= 0.0078125D;
/*  481 */         this.posY++;
/*      */         break;
/*      */     } 
/*  484 */     int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
/*  485 */     double d1 = (aint[1][0] - aint[0][0]);
/*  486 */     double d2 = (aint[1][2] - aint[0][2]);
/*  487 */     double d3 = Math.sqrt(d1 * d1 + d2 * d2);
/*  488 */     double d4 = this.motionX * d1 + this.motionZ * d2;
/*      */     
/*  490 */     if (d4 < 0.0D) {
/*      */       
/*  492 */       d1 = -d1;
/*  493 */       d2 = -d2;
/*      */     } 
/*      */     
/*  496 */     double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */     
/*  498 */     if (d5 > 2.0D)
/*      */     {
/*  500 */       d5 = 2.0D;
/*      */     }
/*      */     
/*  503 */     this.motionX = d5 * d1 / d3;
/*  504 */     this.motionZ = d5 * d2 / d3;
/*      */     
/*  506 */     if (this.riddenByEntity instanceof EntityLivingBase) {
/*      */       
/*  508 */       double d6 = ((EntityLivingBase)this.riddenByEntity).moveForward;
/*      */       
/*  510 */       if (d6 > 0.0D) {
/*      */         
/*  512 */         double d7 = -Math.sin((this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F));
/*  513 */         double d8 = Math.cos((this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F));
/*  514 */         double d9 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*      */         
/*  516 */         if (d9 < 0.01D) {
/*      */           
/*  518 */           this.motionX += d7 * 0.1D;
/*  519 */           this.motionZ += d8 * 0.1D;
/*  520 */           flag1 = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  525 */     if (flag1) {
/*      */       
/*  527 */       double d17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */       
/*  529 */       if (d17 < 0.03D) {
/*      */         
/*  531 */         this.motionX *= 0.0D;
/*  532 */         this.motionY *= 0.0D;
/*  533 */         this.motionZ *= 0.0D;
/*      */       }
/*      */       else {
/*      */         
/*  537 */         this.motionX *= 0.5D;
/*  538 */         this.motionY *= 0.0D;
/*  539 */         this.motionZ *= 0.5D;
/*      */       } 
/*      */     } 
/*      */     
/*  543 */     double d18 = 0.0D;
/*  544 */     double d19 = p_180460_1_.getX() + 0.5D + aint[0][0] * 0.5D;
/*  545 */     double d20 = p_180460_1_.getZ() + 0.5D + aint[0][2] * 0.5D;
/*  546 */     double d21 = p_180460_1_.getX() + 0.5D + aint[1][0] * 0.5D;
/*  547 */     double d10 = p_180460_1_.getZ() + 0.5D + aint[1][2] * 0.5D;
/*  548 */     d1 = d21 - d19;
/*  549 */     d2 = d10 - d20;
/*      */     
/*  551 */     if (d1 == 0.0D) {
/*      */       
/*  553 */       this.posX = p_180460_1_.getX() + 0.5D;
/*  554 */       d18 = this.posZ - p_180460_1_.getZ();
/*      */     }
/*  556 */     else if (d2 == 0.0D) {
/*      */       
/*  558 */       this.posZ = p_180460_1_.getZ() + 0.5D;
/*  559 */       d18 = this.posX - p_180460_1_.getX();
/*      */     }
/*      */     else {
/*      */       
/*  563 */       double d11 = this.posX - d19;
/*  564 */       double d12 = this.posZ - d20;
/*  565 */       d18 = (d11 * d1 + d12 * d2) * 2.0D;
/*      */     } 
/*      */     
/*  568 */     this.posX = d19 + d1 * d18;
/*  569 */     this.posZ = d20 + d2 * d18;
/*  570 */     setPosition(this.posX, this.posY, this.posZ);
/*  571 */     double d22 = this.motionX;
/*  572 */     double d23 = this.motionZ;
/*      */     
/*  574 */     if (this.riddenByEntity != null) {
/*      */       
/*  576 */       d22 *= 0.75D;
/*  577 */       d23 *= 0.75D;
/*      */     } 
/*      */     
/*  580 */     double d13 = getMaximumSpeed();
/*  581 */     d22 = MathHelper.clamp_double(d22, -d13, d13);
/*  582 */     d23 = MathHelper.clamp_double(d23, -d13, d13);
/*  583 */     moveEntity(d22, 0.0D, d23);
/*      */     
/*  585 */     if (aint[0][1] != 0 && MathHelper.floor_double(this.posX) - p_180460_1_.getX() == aint[0][0] && MathHelper.floor_double(this.posZ) - p_180460_1_.getZ() == aint[0][2]) {
/*      */       
/*  587 */       setPosition(this.posX, this.posY + aint[0][1], this.posZ);
/*      */     }
/*  589 */     else if (aint[1][1] != 0 && MathHelper.floor_double(this.posX) - p_180460_1_.getX() == aint[1][0] && MathHelper.floor_double(this.posZ) - p_180460_1_.getZ() == aint[1][2]) {
/*      */       
/*  591 */       setPosition(this.posX, this.posY + aint[1][1], this.posZ);
/*      */     } 
/*      */     
/*  594 */     applyDrag();
/*  595 */     Vec3 vec31 = func_70489_a(this.posX, this.posY, this.posZ);
/*      */     
/*  597 */     if (vec31 != null && vec3 != null) {
/*      */       
/*  599 */       double d14 = (vec3.yCoord - vec31.yCoord) * 0.05D;
/*  600 */       d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */       
/*  602 */       if (d5 > 0.0D) {
/*      */         
/*  604 */         this.motionX = this.motionX / d5 * (d5 + d14);
/*  605 */         this.motionZ = this.motionZ / d5 * (d5 + d14);
/*      */       } 
/*      */       
/*  608 */       setPosition(this.posX, vec31.yCoord, this.posZ);
/*      */     } 
/*      */     
/*  611 */     int j = MathHelper.floor_double(this.posX);
/*  612 */     int i = MathHelper.floor_double(this.posZ);
/*      */     
/*  614 */     if (j != p_180460_1_.getX() || i != p_180460_1_.getZ()) {
/*      */       
/*  616 */       d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  617 */       this.motionX = d5 * (j - p_180460_1_.getX());
/*  618 */       this.motionZ = d5 * (i - p_180460_1_.getZ());
/*      */     } 
/*      */     
/*  621 */     if (flag) {
/*      */       
/*  623 */       double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */       
/*  625 */       if (d15 > 0.01D) {
/*      */         
/*  627 */         double d16 = 0.06D;
/*  628 */         this.motionX += this.motionX / d15 * d16;
/*  629 */         this.motionZ += this.motionZ / d15 * d16;
/*      */       }
/*  631 */       else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*      */         
/*  633 */         if (this.worldObj.getBlockState(p_180460_1_.west()).getBlock().isNormalCube())
/*      */         {
/*  635 */           this.motionX = 0.02D;
/*      */         }
/*  637 */         else if (this.worldObj.getBlockState(p_180460_1_.east()).getBlock().isNormalCube())
/*      */         {
/*  639 */           this.motionX = -0.02D;
/*      */         }
/*      */       
/*  642 */       } else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*      */         
/*  644 */         if (this.worldObj.getBlockState(p_180460_1_.north()).getBlock().isNormalCube()) {
/*      */           
/*  646 */           this.motionZ = 0.02D;
/*      */         }
/*  648 */         else if (this.worldObj.getBlockState(p_180460_1_.south()).getBlock().isNormalCube()) {
/*      */           
/*  650 */           this.motionZ = -0.02D;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyDrag() {
/*  658 */     if (this.riddenByEntity != null) {
/*      */       
/*  660 */       this.motionX *= 0.996999979019165D;
/*  661 */       this.motionY *= 0.0D;
/*  662 */       this.motionZ *= 0.996999979019165D;
/*      */     }
/*      */     else {
/*      */       
/*  666 */       this.motionX *= 0.9599999785423279D;
/*  667 */       this.motionY *= 0.0D;
/*  668 */       this.motionZ *= 0.9599999785423279D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(double x, double y, double z) {
/*  677 */     this.posX = x;
/*  678 */     this.posY = y;
/*  679 */     this.posZ = z;
/*  680 */     float f = this.width / 2.0F;
/*  681 */     float f1 = this.height;
/*  682 */     setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 func_70495_a(double p_70495_1_, double p_70495_3_, double p_70495_5_, double p_70495_7_) {
/*  687 */     int i = MathHelper.floor_double(p_70495_1_);
/*  688 */     int j = MathHelper.floor_double(p_70495_3_);
/*  689 */     int k = MathHelper.floor_double(p_70495_5_);
/*      */     
/*  691 */     if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k)))
/*      */     {
/*  693 */       j--;
/*      */     }
/*      */     
/*  696 */     IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
/*      */     
/*  698 */     if (BlockRailBase.isRailBlock(iblockstate)) {
/*      */       
/*  700 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty());
/*  701 */       p_70495_3_ = j;
/*      */       
/*  703 */       if (blockrailbase$enumraildirection.isAscending())
/*      */       {
/*  705 */         p_70495_3_ = (j + 1);
/*      */       }
/*      */       
/*  708 */       int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
/*  709 */       double d0 = (aint[1][0] - aint[0][0]);
/*  710 */       double d1 = (aint[1][2] - aint[0][2]);
/*  711 */       double d2 = Math.sqrt(d0 * d0 + d1 * d1);
/*  712 */       d0 /= d2;
/*  713 */       d1 /= d2;
/*  714 */       p_70495_1_ += d0 * p_70495_7_;
/*  715 */       p_70495_5_ += d1 * p_70495_7_;
/*      */       
/*  717 */       if (aint[0][1] != 0 && MathHelper.floor_double(p_70495_1_) - i == aint[0][0] && MathHelper.floor_double(p_70495_5_) - k == aint[0][2]) {
/*      */         
/*  719 */         p_70495_3_ += aint[0][1];
/*      */       }
/*  721 */       else if (aint[1][1] != 0 && MathHelper.floor_double(p_70495_1_) - i == aint[1][0] && MathHelper.floor_double(p_70495_5_) - k == aint[1][2]) {
/*      */         
/*  723 */         p_70495_3_ += aint[1][1];
/*      */       } 
/*      */       
/*  726 */       return func_70489_a(p_70495_1_, p_70495_3_, p_70495_5_);
/*      */     } 
/*      */ 
/*      */     
/*  730 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 func_70489_a(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
/*  736 */     int i = MathHelper.floor_double(p_70489_1_);
/*  737 */     int j = MathHelper.floor_double(p_70489_3_);
/*  738 */     int k = MathHelper.floor_double(p_70489_5_);
/*      */     
/*  740 */     if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k)))
/*      */     {
/*  742 */       j--;
/*      */     }
/*      */     
/*  745 */     IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
/*      */     
/*  747 */     if (BlockRailBase.isRailBlock(iblockstate)) {
/*      */       
/*  749 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty());
/*  750 */       int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
/*  751 */       double d0 = 0.0D;
/*  752 */       double d1 = i + 0.5D + aint[0][0] * 0.5D;
/*  753 */       double d2 = j + 0.0625D + aint[0][1] * 0.5D;
/*  754 */       double d3 = k + 0.5D + aint[0][2] * 0.5D;
/*  755 */       double d4 = i + 0.5D + aint[1][0] * 0.5D;
/*  756 */       double d5 = j + 0.0625D + aint[1][1] * 0.5D;
/*  757 */       double d6 = k + 0.5D + aint[1][2] * 0.5D;
/*  758 */       double d7 = d4 - d1;
/*  759 */       double d8 = (d5 - d2) * 2.0D;
/*  760 */       double d9 = d6 - d3;
/*      */       
/*  762 */       if (d7 == 0.0D) {
/*      */         
/*  764 */         p_70489_1_ = i + 0.5D;
/*  765 */         d0 = p_70489_5_ - k;
/*      */       }
/*  767 */       else if (d9 == 0.0D) {
/*      */         
/*  769 */         p_70489_5_ = k + 0.5D;
/*  770 */         d0 = p_70489_1_ - i;
/*      */       }
/*      */       else {
/*      */         
/*  774 */         double d10 = p_70489_1_ - d1;
/*  775 */         double d11 = p_70489_5_ - d3;
/*  776 */         d0 = (d10 * d7 + d11 * d9) * 2.0D;
/*      */       } 
/*      */       
/*  779 */       p_70489_1_ = d1 + d7 * d0;
/*  780 */       p_70489_3_ = d2 + d8 * d0;
/*  781 */       p_70489_5_ = d3 + d9 * d0;
/*      */       
/*  783 */       if (d8 < 0.0D)
/*      */       {
/*  785 */         p_70489_3_++;
/*      */       }
/*      */       
/*  788 */       if (d8 > 0.0D)
/*      */       {
/*  790 */         p_70489_3_ += 0.5D;
/*      */       }
/*      */       
/*  793 */       return new Vec3(p_70489_1_, p_70489_3_, p_70489_5_);
/*      */     } 
/*      */ 
/*      */     
/*  797 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  806 */     if (tagCompund.getBoolean("CustomDisplayTile")) {
/*      */       
/*  808 */       int i = tagCompund.getInteger("DisplayData");
/*      */       
/*  810 */       if (tagCompund.hasKey("DisplayTile", 8)) {
/*      */         
/*  812 */         Block block = Block.getBlockFromName(tagCompund.getString("DisplayTile"));
/*      */         
/*  814 */         if (block == null)
/*      */         {
/*  816 */           func_174899_a(Blocks.air.getDefaultState());
/*      */         }
/*      */         else
/*      */         {
/*  820 */           func_174899_a(block.getStateFromMeta(i));
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  825 */         Block block1 = Block.getBlockById(tagCompund.getInteger("DisplayTile"));
/*      */         
/*  827 */         if (block1 == null) {
/*      */           
/*  829 */           func_174899_a(Blocks.air.getDefaultState());
/*      */         }
/*      */         else {
/*      */           
/*  833 */           func_174899_a(block1.getStateFromMeta(i));
/*      */         } 
/*      */       } 
/*      */       
/*  837 */       setDisplayTileOffset(tagCompund.getInteger("DisplayOffset"));
/*      */     } 
/*      */     
/*  840 */     if (tagCompund.hasKey("CustomName", 8) && !tagCompund.getString("CustomName").isEmpty())
/*      */     {
/*  842 */       this.entityName = tagCompund.getString("CustomName");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  851 */     if (hasDisplayTile()) {
/*      */       
/*  853 */       tagCompound.setBoolean("CustomDisplayTile", true);
/*  854 */       IBlockState iblockstate = getDisplayTile();
/*  855 */       ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(iblockstate.getBlock());
/*  856 */       tagCompound.setString("DisplayTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/*  857 */       tagCompound.setInteger("DisplayData", iblockstate.getBlock().getMetaFromState(iblockstate));
/*  858 */       tagCompound.setInteger("DisplayOffset", getDisplayTileOffset());
/*      */     } 
/*      */     
/*  861 */     if (this.entityName != null && !this.entityName.isEmpty())
/*      */     {
/*  863 */       tagCompound.setString("CustomName", this.entityName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/*  872 */     if (!this.worldObj.isRemote)
/*      */     {
/*  874 */       if (!entityIn.noClip && !this.noClip)
/*      */       {
/*  876 */         if (entityIn != this.riddenByEntity) {
/*      */           
/*  878 */           if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer) && !(entityIn instanceof net.minecraft.entity.monster.EntityIronGolem) && getMinecartType() == EnumMinecartType.RIDEABLE && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D && this.riddenByEntity == null && entityIn.ridingEntity == null)
/*      */           {
/*  880 */             entityIn.mountEntity(this);
/*      */           }
/*      */           
/*  883 */           double d0 = entityIn.posX - this.posX;
/*  884 */           double d1 = entityIn.posZ - this.posZ;
/*  885 */           double d2 = d0 * d0 + d1 * d1;
/*      */           
/*  887 */           if (d2 >= 9.999999747378752E-5D) {
/*      */             
/*  889 */             d2 = MathHelper.sqrt_double(d2);
/*  890 */             d0 /= d2;
/*  891 */             d1 /= d2;
/*  892 */             double d3 = 1.0D / d2;
/*      */             
/*  894 */             if (d3 > 1.0D)
/*      */             {
/*  896 */               d3 = 1.0D;
/*      */             }
/*      */             
/*  899 */             d0 *= d3;
/*  900 */             d1 *= d3;
/*  901 */             d0 *= 0.10000000149011612D;
/*  902 */             d1 *= 0.10000000149011612D;
/*  903 */             d0 *= (1.0F - this.entityCollisionReduction);
/*  904 */             d1 *= (1.0F - this.entityCollisionReduction);
/*  905 */             d0 *= 0.5D;
/*  906 */             d1 *= 0.5D;
/*      */             
/*  908 */             if (entityIn instanceof EntityMinecart) {
/*      */               
/*  910 */               double d4 = entityIn.posX - this.posX;
/*  911 */               double d5 = entityIn.posZ - this.posZ;
/*  912 */               Vec3 vec3 = (new Vec3(d4, 0.0D, d5)).normalize();
/*  913 */               Vec3 vec31 = (new Vec3(MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F), 0.0D, Math.sin((this.rotationYaw * 3.1415927F / 180.0F)))).normalize();
/*  914 */               double d6 = Math.abs(vec3.dotProduct(vec31));
/*      */               
/*  916 */               if (d6 < 0.800000011920929D) {
/*      */                 return;
/*      */               }
/*      */ 
/*      */               
/*  921 */               double d7 = entityIn.motionX + this.motionX;
/*  922 */               double d8 = entityIn.motionZ + this.motionZ;
/*      */               
/*  924 */               if (((EntityMinecart)entityIn).getMinecartType() == EnumMinecartType.FURNACE && getMinecartType() != EnumMinecartType.FURNACE)
/*      */               {
/*  926 */                 this.motionX *= 0.20000000298023224D;
/*  927 */                 this.motionZ *= 0.20000000298023224D;
/*  928 */                 addVelocity(entityIn.motionX - d0, 0.0D, entityIn.motionZ - d1);
/*  929 */                 entityIn.motionX *= 0.949999988079071D;
/*  930 */                 entityIn.motionZ *= 0.949999988079071D;
/*      */               }
/*  932 */               else if (((EntityMinecart)entityIn).getMinecartType() != EnumMinecartType.FURNACE && getMinecartType() == EnumMinecartType.FURNACE)
/*      */               {
/*  934 */                 entityIn.motionX *= 0.20000000298023224D;
/*  935 */                 entityIn.motionZ *= 0.20000000298023224D;
/*  936 */                 entityIn.addVelocity(this.motionX + d0, 0.0D, this.motionZ + d1);
/*  937 */                 this.motionX *= 0.949999988079071D;
/*  938 */                 this.motionZ *= 0.949999988079071D;
/*      */               }
/*      */               else
/*      */               {
/*  942 */                 d7 /= 2.0D;
/*  943 */                 d8 /= 2.0D;
/*  944 */                 this.motionX *= 0.20000000298023224D;
/*  945 */                 this.motionZ *= 0.20000000298023224D;
/*  946 */                 addVelocity(d7 - d0, 0.0D, d8 - d1);
/*  947 */                 entityIn.motionX *= 0.20000000298023224D;
/*  948 */                 entityIn.motionZ *= 0.20000000298023224D;
/*  949 */                 entityIn.addVelocity(d7 + d0, 0.0D, d8 + d1);
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/*  954 */               addVelocity(-d0, 0.0D, -d1);
/*  955 */               entityIn.addVelocity(d0 / 4.0D, 0.0D, d1 / 4.0D);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/*  965 */     this.minecartX = x;
/*  966 */     this.minecartY = y;
/*  967 */     this.minecartZ = z;
/*  968 */     this.minecartYaw = yaw;
/*  969 */     this.minecartPitch = pitch;
/*  970 */     this.turnProgress = posRotationIncrements + 2;
/*  971 */     this.motionX = this.velocityX;
/*  972 */     this.motionY = this.velocityY;
/*  973 */     this.motionZ = this.velocityZ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVelocity(double x, double y, double z) {
/*  981 */     this.velocityX = this.motionX = x;
/*  982 */     this.velocityY = this.motionY = y;
/*  983 */     this.velocityZ = this.motionZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDamage(float p_70492_1_) {
/*  992 */     this.dataWatcher.updateObject(19, Float.valueOf(p_70492_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDamage() {
/* 1001 */     return this.dataWatcher.getWatchableObjectFloat(19);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRollingAmplitude(int p_70497_1_) {
/* 1009 */     this.dataWatcher.updateObject(17, Integer.valueOf(p_70497_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRollingAmplitude() {
/* 1017 */     return this.dataWatcher.getWatchableObjectInt(17);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRollingDirection(int p_70494_1_) {
/* 1025 */     this.dataWatcher.updateObject(18, Integer.valueOf(p_70494_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRollingDirection() {
/* 1033 */     return this.dataWatcher.getWatchableObjectInt(18);
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract EnumMinecartType getMinecartType();
/*      */   
/*      */   public IBlockState getDisplayTile() {
/* 1040 */     return !hasDisplayTile() ? getDefaultDisplayTile() : Block.getStateById(getDataWatcher().getWatchableObjectInt(20));
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getDefaultDisplayTile() {
/* 1045 */     return Blocks.air.getDefaultState();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDisplayTileOffset() {
/* 1050 */     return !hasDisplayTile() ? getDefaultDisplayTileOffset() : getDataWatcher().getWatchableObjectInt(21);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDefaultDisplayTileOffset() {
/* 1055 */     return 6;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_174899_a(IBlockState p_174899_1_) {
/* 1060 */     getDataWatcher().updateObject(20, Integer.valueOf(Block.getStateId(p_174899_1_)));
/* 1061 */     setHasDisplayTile(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisplayTileOffset(int p_94086_1_) {
/* 1066 */     getDataWatcher().updateObject(21, Integer.valueOf(p_94086_1_));
/* 1067 */     setHasDisplayTile(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasDisplayTile() {
/* 1072 */     return (getDataWatcher().getWatchableObjectByte(22) == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHasDisplayTile(boolean p_94096_1_) {
/* 1077 */     getDataWatcher().updateObject(22, Byte.valueOf((byte)(p_94096_1_ ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomNameTag(String name) {
/* 1085 */     this.entityName = name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1093 */     return (this.entityName != null) ? this.entityName : super.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCustomName() {
/* 1101 */     return (this.entityName != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCustomNameTag() {
/* 1106 */     return this.entityName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 1114 */     if (hasCustomName()) {
/*      */       
/* 1116 */       ChatComponentText chatcomponenttext = new ChatComponentText(this.entityName);
/* 1117 */       chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 1118 */       chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/* 1119 */       return (IChatComponent)chatcomponenttext;
/*      */     } 
/*      */ 
/*      */     
/* 1123 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(getName(), new Object[0]);
/* 1124 */     chatcomponenttranslation.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 1125 */     chatcomponenttranslation.getChatStyle().setInsertion(getUniqueID().toString());
/* 1126 */     return (IChatComponent)chatcomponenttranslation;
/*      */   }
/*      */ 
/*      */   
/*      */   public enum EnumMinecartType
/*      */   {
/* 1132 */     RIDEABLE(0, "MinecartRideable"),
/* 1133 */     CHEST(1, "MinecartChest"),
/* 1134 */     FURNACE(2, "MinecartFurnace"),
/* 1135 */     TNT(3, "MinecartTNT"),
/* 1136 */     SPAWNER(4, "MinecartSpawner"),
/* 1137 */     HOPPER(5, "MinecartHopper"),
/* 1138 */     COMMAND_BLOCK(6, "MinecartCommandBlock");
/*      */     
/* 1140 */     private static final Map<Integer, EnumMinecartType> ID_LOOKUP = Maps.newHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int networkID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 1167 */       for (EnumMinecartType entityminecart$enumminecarttype : values())
/*      */       {
/* 1169 */         ID_LOOKUP.put(Integer.valueOf(entityminecart$enumminecarttype.networkID), entityminecart$enumminecarttype);
/*      */       }
/*      */     }
/*      */     
/*      */     EnumMinecartType(int networkID, String name) {
/*      */       this.networkID = networkID;
/*      */       this.name = name;
/*      */     }
/*      */     
/*      */     public int getNetworkID() {
/*      */       return this.networkID;
/*      */     }
/*      */     
/*      */     public String getName() {
/*      */       return this.name;
/*      */     }
/*      */     
/*      */     public static EnumMinecartType byNetworkID(int id) {
/*      */       EnumMinecartType entityminecart$enumminecarttype = ID_LOOKUP.get(Integer.valueOf(id));
/*      */       return (entityminecart$enumminecarttype == null) ? RIDEABLE : entityminecart$enumminecarttype;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */