/*     */ package net.minecraft.entity.projectile;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.WeightedRandomFishable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class EntityFishHook extends Entity {
/*  26 */   private static final List<WeightedRandomFishable> JUNK = Arrays.asList(new WeightedRandomFishable[] { (new WeightedRandomFishable(new ItemStack((Item)Items.leather_boots), 10)).setMaxDamagePercent(0.9F), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack((Item)Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), (new WeightedRandomFishable(new ItemStack((Item)Items.fishing_rod), 2)).setMaxDamagePercent(0.9F), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, EnumDyeColor.BLACK.getDyeDamage()), 1), new WeightedRandomFishable(new ItemStack((Block)Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10) });
/*  27 */   private static final List<WeightedRandomFishable> TREASURE = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), (new WeightedRandomFishable(new ItemStack((Item)Items.bow), 1)).setMaxDamagePercent(0.25F).setEnchantable(), (new WeightedRandomFishable(new ItemStack((Item)Items.fishing_rod), 1)).setMaxDamagePercent(0.25F).setEnchantable(), (new WeightedRandomFishable(new ItemStack(Items.book), 1)).setEnchantable() });
/*  28 */   private static final List<WeightedRandomFishable> FISH = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getMetadata()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()), 13) });
/*     */   
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private Block inTile;
/*     */   private boolean inGround;
/*     */   public int shake;
/*     */   public EntityPlayer angler;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   private int ticksCatchable;
/*     */   private int ticksCaughtDelay;
/*     */   private int ticksCatchableDelay;
/*     */   private float fishApproachAngle;
/*     */   public Entity caughtEntity;
/*     */   private int fishPosRotationIncrements;
/*     */   private double fishX;
/*     */   private double fishY;
/*     */   private double fishZ;
/*     */   private double fishYaw;
/*     */   private double fishPitch;
/*     */   private double clientMotionX;
/*     */   private double clientMotionY;
/*     */   private double clientMotionZ;
/*     */   
/*     */   public static List<WeightedRandomFishable> func_174855_j() {
/*  55 */     return FISH;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFishHook(World worldIn) {
/*  60 */     super(worldIn);
/*  61 */     this.xTile = -1;
/*  62 */     this.yTile = -1;
/*  63 */     this.zTile = -1;
/*  64 */     setSize(0.25F, 0.25F);
/*  65 */     this.ignoreFrustumCheck = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFishHook(World worldIn, double x, double y, double z, EntityPlayer anglerIn) {
/*  70 */     this(worldIn);
/*  71 */     setPosition(x, y, z);
/*  72 */     this.ignoreFrustumCheck = true;
/*  73 */     this.angler = anglerIn;
/*  74 */     anglerIn.fishEntity = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFishHook(World worldIn, EntityPlayer fishingPlayer) {
/*  79 */     super(worldIn);
/*  80 */     this.xTile = -1;
/*  81 */     this.yTile = -1;
/*  82 */     this.zTile = -1;
/*  83 */     this.ignoreFrustumCheck = true;
/*  84 */     this.angler = fishingPlayer;
/*  85 */     this.angler.fishEntity = this;
/*  86 */     setSize(0.25F, 0.25F);
/*  87 */     setLocationAndAngles(fishingPlayer.posX, fishingPlayer.posY + fishingPlayer.getEyeHeight(), fishingPlayer.posZ, fishingPlayer.rotationYaw, fishingPlayer.rotationPitch);
/*  88 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  89 */     this.posY -= 0.10000000149011612D;
/*  90 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  91 */     setPosition(this.posX, this.posY, this.posZ);
/*  92 */     float f = 0.4F;
/*  93 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  94 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  95 */     this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  96 */     handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 109 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/* 111 */     if (Double.isNaN(d0))
/*     */     {
/* 113 */       d0 = 4.0D;
/*     */     }
/*     */     
/* 116 */     d0 *= 64.0D;
/* 117 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleHookCasting(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_) {
/* 122 */     float f = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
/* 123 */     p_146035_1_ /= f;
/* 124 */     p_146035_3_ /= f;
/* 125 */     p_146035_5_ /= f;
/* 126 */     p_146035_1_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 127 */     p_146035_3_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 128 */     p_146035_5_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 129 */     p_146035_1_ *= p_146035_7_;
/* 130 */     p_146035_3_ *= p_146035_7_;
/* 131 */     p_146035_5_ *= p_146035_7_;
/* 132 */     this.motionX = p_146035_1_;
/* 133 */     this.motionY = p_146035_3_;
/* 134 */     this.motionZ = p_146035_5_;
/* 135 */     float f1 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
/* 136 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(p_146035_1_, p_146035_5_) * 180.0D / Math.PI);
/* 137 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(p_146035_3_, f1) * 180.0D / Math.PI);
/* 138 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 143 */     this.fishX = x;
/* 144 */     this.fishY = y;
/* 145 */     this.fishZ = z;
/* 146 */     this.fishYaw = yaw;
/* 147 */     this.fishPitch = pitch;
/* 148 */     this.fishPosRotationIncrements = posRotationIncrements;
/* 149 */     this.motionX = this.clientMotionX;
/* 150 */     this.motionY = this.clientMotionY;
/* 151 */     this.motionZ = this.clientMotionZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 159 */     this.clientMotionX = this.motionX = x;
/* 160 */     this.clientMotionY = this.motionY = y;
/* 161 */     this.clientMotionZ = this.motionZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 169 */     super.onUpdate();
/*     */     
/* 171 */     if (this.fishPosRotationIncrements > 0) {
/*     */       
/* 173 */       double d7 = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
/* 174 */       double d8 = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
/* 175 */       double d9 = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
/* 176 */       double d1 = MathHelper.wrapAngleTo180_double(this.fishYaw - this.rotationYaw);
/* 177 */       this.rotationYaw = (float)(this.rotationYaw + d1 / this.fishPosRotationIncrements);
/* 178 */       this.rotationPitch = (float)(this.rotationPitch + (this.fishPitch - this.rotationPitch) / this.fishPosRotationIncrements);
/* 179 */       this.fishPosRotationIncrements--;
/* 180 */       setPosition(d7, d8, d9);
/* 181 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */     }
/*     */     else {
/*     */       
/* 185 */       if (!this.worldObj.isRemote) {
/*     */         
/* 187 */         ItemStack itemstack = this.angler.getCurrentEquippedItem();
/*     */         
/* 189 */         if (this.angler.isDead || !this.angler.isEntityAlive() || itemstack == null || itemstack.getItem() != Items.fishing_rod || getDistanceSqToEntity((Entity)this.angler) > 1024.0D) {
/*     */           
/* 191 */           setDead();
/* 192 */           this.angler.fishEntity = null;
/*     */           
/*     */           return;
/*     */         } 
/* 196 */         if (this.caughtEntity != null) {
/*     */           
/* 198 */           if (!this.caughtEntity.isDead) {
/*     */             
/* 200 */             this.posX = this.caughtEntity.posX;
/* 201 */             double d17 = this.caughtEntity.height;
/* 202 */             this.posY = (this.caughtEntity.getEntityBoundingBox()).minY + d17 * 0.8D;
/* 203 */             this.posZ = this.caughtEntity.posZ;
/*     */             
/*     */             return;
/*     */           } 
/* 207 */           this.caughtEntity = null;
/*     */         } 
/*     */       } 
/*     */       
/* 211 */       if (this.shake > 0)
/*     */       {
/* 213 */         this.shake--;
/*     */       }
/*     */       
/* 216 */       if (this.inGround) {
/*     */         
/* 218 */         if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/*     */           
/* 220 */           this.ticksInGround++;
/*     */           
/* 222 */           if (this.ticksInGround == 1200)
/*     */           {
/* 224 */             setDead();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 230 */         this.inGround = false;
/* 231 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 232 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 233 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 234 */         this.ticksInGround = 0;
/* 235 */         this.ticksInAir = 0;
/*     */       }
/*     */       else {
/*     */         
/* 239 */         this.ticksInAir++;
/*     */       } 
/*     */       
/* 242 */       Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 243 */       Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 244 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3);
/* 245 */       vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 246 */       vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 248 */       if (movingobjectposition != null)
/*     */       {
/* 250 */         vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 253 */       Entity entity = null;
/* 254 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 255 */       double d0 = 0.0D;
/*     */       
/* 257 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 259 */         Entity entity1 = list.get(i);
/*     */         
/* 261 */         if (entity1.canBeCollidedWith() && (entity1 != this.angler || this.ticksInAir >= 5)) {
/*     */           
/* 263 */           float f = 0.3F;
/* 264 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
/* 265 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec31, vec3);
/*     */           
/* 267 */           if (movingobjectposition1 != null) {
/*     */             
/* 269 */             double d2 = vec31.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 271 */             if (d2 < d0 || d0 == 0.0D) {
/*     */               
/* 273 */               entity = entity1;
/* 274 */               d0 = d2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 280 */       if (entity != null)
/*     */       {
/* 282 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 285 */       if (movingobjectposition != null)
/*     */       {
/* 287 */         if (movingobjectposition.entityHit != null) {
/*     */           
/* 289 */           if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, (Entity)this.angler), 0.0F))
/*     */           {
/* 291 */             this.caughtEntity = movingobjectposition.entityHit;
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 296 */           this.inGround = true;
/*     */         } 
/*     */       }
/*     */       
/* 300 */       if (!this.inGround) {
/*     */         
/* 302 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 303 */         float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 304 */         this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */         
/* 306 */         for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f5) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 311 */         while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */         {
/* 313 */           this.prevRotationPitch += 360.0F;
/*     */         }
/*     */         
/* 316 */         while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */         {
/* 318 */           this.prevRotationYaw -= 360.0F;
/*     */         }
/*     */         
/* 321 */         while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */         {
/* 323 */           this.prevRotationYaw += 360.0F;
/*     */         }
/*     */         
/* 326 */         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 327 */         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 328 */         float f6 = 0.92F;
/*     */         
/* 330 */         if (this.onGround || this.isCollidedHorizontally)
/*     */         {
/* 332 */           f6 = 0.5F;
/*     */         }
/*     */         
/* 335 */         int j = 5;
/* 336 */         double d10 = 0.0D;
/*     */         
/* 338 */         for (int k = 0; k < j; k++) {
/*     */           
/* 340 */           AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
/* 341 */           double d3 = axisalignedbb1.maxY - axisalignedbb1.minY;
/* 342 */           double d4 = axisalignedbb1.minY + d3 * k / j;
/* 343 */           double d5 = axisalignedbb1.minY + d3 * (k + 1) / j;
/* 344 */           AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb1.minX, d4, axisalignedbb1.minZ, axisalignedbb1.maxX, d5, axisalignedbb1.maxZ);
/*     */           
/* 346 */           if (this.worldObj.isAABBInMaterial(axisalignedbb2, Material.water))
/*     */           {
/* 348 */             d10 += 1.0D / j;
/*     */           }
/*     */         } 
/*     */         
/* 352 */         if (!this.worldObj.isRemote && d10 > 0.0D) {
/*     */           
/* 354 */           WorldServer worldserver = (WorldServer)this.worldObj;
/* 355 */           int l = 1;
/* 356 */           BlockPos blockpos = (new BlockPos(this)).up();
/*     */           
/* 358 */           if (this.rand.nextFloat() < 0.25F && this.worldObj.isRainingAt(blockpos))
/*     */           {
/* 360 */             l = 2;
/*     */           }
/*     */           
/* 363 */           if (this.rand.nextFloat() < 0.5F && !this.worldObj.canSeeSky(blockpos))
/*     */           {
/* 365 */             l--;
/*     */           }
/*     */           
/* 368 */           if (this.ticksCatchable > 0) {
/*     */             
/* 370 */             this.ticksCatchable--;
/*     */             
/* 372 */             if (this.ticksCatchable <= 0)
/*     */             {
/* 374 */               this.ticksCaughtDelay = 0;
/* 375 */               this.ticksCatchableDelay = 0;
/*     */             }
/*     */           
/* 378 */           } else if (this.ticksCatchableDelay > 0) {
/*     */             
/* 380 */             this.ticksCatchableDelay -= l;
/*     */             
/* 382 */             if (this.ticksCatchableDelay <= 0) {
/*     */               
/* 384 */               this.motionY -= 0.20000000298023224D;
/* 385 */               playSound("random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 386 */               float f8 = MathHelper.floor_double((getEntityBoundingBox()).minY);
/* 387 */               worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX, (f8 + 1.0F), this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 388 */               worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, this.posX, (f8 + 1.0F), this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 389 */               this.ticksCatchable = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
/*     */             }
/*     */             else {
/*     */               
/* 393 */               this.fishApproachAngle = (float)(this.fishApproachAngle + this.rand.nextGaussian() * 4.0D);
/* 394 */               float f7 = this.fishApproachAngle * 0.017453292F;
/* 395 */               float f10 = MathHelper.sin(f7);
/* 396 */               float f11 = MathHelper.cos(f7);
/* 397 */               double d13 = this.posX + (f10 * this.ticksCatchableDelay * 0.1F);
/* 398 */               double d15 = (MathHelper.floor_double((getEntityBoundingBox()).minY) + 1.0F);
/* 399 */               double d16 = this.posZ + (f11 * this.ticksCatchableDelay * 0.1F);
/* 400 */               Block block1 = worldserver.getBlockState(new BlockPos((int)d13, (int)d15 - 1, (int)d16)).getBlock();
/*     */               
/* 402 */               if (block1 == Blocks.water || block1 == Blocks.flowing_water)
/*     */               {
/* 404 */                 if (this.rand.nextFloat() < 0.15F)
/*     */                 {
/* 406 */                   worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d13, d15 - 0.10000000149011612D, d16, 1, f10, 0.1D, f11, 0.0D, new int[0]);
/*     */                 }
/*     */                 
/* 409 */                 float f3 = f10 * 0.04F;
/* 410 */                 float f4 = f11 * 0.04F;
/* 411 */                 worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d13, d15, d16, 0, f4, 0.01D, -f3, 1.0D, new int[0]);
/* 412 */                 worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d13, d15, d16, 0, -f4, 0.01D, f3, 1.0D, new int[0]);
/*     */               }
/*     */             
/*     */             } 
/* 416 */           } else if (this.ticksCaughtDelay > 0) {
/*     */             
/* 418 */             this.ticksCaughtDelay -= l;
/* 419 */             float f1 = 0.15F;
/*     */             
/* 421 */             if (this.ticksCaughtDelay < 20) {
/*     */               
/* 423 */               f1 = (float)(f1 + (20 - this.ticksCaughtDelay) * 0.05D);
/*     */             }
/* 425 */             else if (this.ticksCaughtDelay < 40) {
/*     */               
/* 427 */               f1 = (float)(f1 + (40 - this.ticksCaughtDelay) * 0.02D);
/*     */             }
/* 429 */             else if (this.ticksCaughtDelay < 60) {
/*     */               
/* 431 */               f1 = (float)(f1 + (60 - this.ticksCaughtDelay) * 0.01D);
/*     */             } 
/*     */             
/* 434 */             if (this.rand.nextFloat() < f1) {
/*     */               
/* 436 */               float f9 = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F) * 0.017453292F;
/* 437 */               float f2 = MathHelper.randomFloatClamp(this.rand, 25.0F, 60.0F);
/* 438 */               double d12 = this.posX + (MathHelper.sin(f9) * f2 * 0.1F);
/* 439 */               double d14 = (MathHelper.floor_double((getEntityBoundingBox()).minY) + 1.0F);
/* 440 */               double d6 = this.posZ + (MathHelper.cos(f9) * f2 * 0.1F);
/* 441 */               Block block = worldserver.getBlockState(new BlockPos((int)d12, (int)d14 - 1, (int)d6)).getBlock();
/*     */               
/* 443 */               if (block == Blocks.water || block == Blocks.flowing_water)
/*     */               {
/* 445 */                 worldserver.spawnParticle(EnumParticleTypes.WATER_SPLASH, d12, d14, d6, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
/*     */               }
/*     */             } 
/*     */             
/* 449 */             if (this.ticksCaughtDelay <= 0)
/*     */             {
/* 451 */               this.fishApproachAngle = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F);
/* 452 */               this.ticksCatchableDelay = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 457 */             this.ticksCaughtDelay = MathHelper.getRandomIntegerInRange(this.rand, 100, 900);
/* 458 */             this.ticksCaughtDelay -= EnchantmentHelper.getLureModifier((EntityLivingBase)this.angler) * 20 * 5;
/*     */           } 
/*     */           
/* 461 */           if (this.ticksCatchable > 0)
/*     */           {
/* 463 */             this.motionY -= (this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2D;
/*     */           }
/*     */         } 
/*     */         
/* 467 */         double d11 = d10 * 2.0D - 1.0D;
/* 468 */         this.motionY += 0.03999999910593033D * d11;
/*     */         
/* 470 */         if (d10 > 0.0D) {
/*     */           
/* 472 */           f6 = (float)(f6 * 0.9D);
/* 473 */           this.motionY *= 0.8D;
/*     */         } 
/*     */         
/* 476 */         this.motionX *= f6;
/* 477 */         this.motionY *= f6;
/* 478 */         this.motionZ *= f6;
/* 479 */         setPosition(this.posX, this.posY, this.posZ);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 489 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 490 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 491 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 492 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 493 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 494 */     tagCompound.setByte("shake", (byte)this.shake);
/* 495 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 503 */     this.xTile = tagCompund.getShort("xTile");
/* 504 */     this.yTile = tagCompund.getShort("yTile");
/* 505 */     this.zTile = tagCompund.getShort("zTile");
/*     */     
/* 507 */     if (tagCompund.hasKey("inTile", 8)) {
/*     */       
/* 509 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 513 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 516 */     this.shake = tagCompund.getByte("shake") & 0xFF;
/* 517 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int handleHookRetraction() {
/* 522 */     if (this.worldObj.isRemote)
/*     */     {
/* 524 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 528 */     int i = 0;
/*     */     
/* 530 */     if (this.caughtEntity != null) {
/*     */       
/* 532 */       double d0 = this.angler.posX - this.posX;
/* 533 */       double d2 = this.angler.posY - this.posY;
/* 534 */       double d4 = this.angler.posZ - this.posZ;
/* 535 */       double d6 = MathHelper.sqrt_double(d0 * d0 + d2 * d2 + d4 * d4);
/* 536 */       double d8 = 0.1D;
/* 537 */       this.caughtEntity.motionX += d0 * d8;
/* 538 */       this.caughtEntity.motionY += d2 * d8 + MathHelper.sqrt_double(d6) * 0.08D;
/* 539 */       this.caughtEntity.motionZ += d4 * d8;
/* 540 */       i = 3;
/*     */     }
/* 542 */     else if (this.ticksCatchable > 0) {
/*     */       
/* 544 */       EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, getFishingResult());
/* 545 */       double d1 = this.angler.posX - this.posX;
/* 546 */       double d3 = this.angler.posY - this.posY;
/* 547 */       double d5 = this.angler.posZ - this.posZ;
/* 548 */       double d7 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
/* 549 */       double d9 = 0.1D;
/* 550 */       entityitem.motionX = d1 * d9;
/* 551 */       entityitem.motionY = d3 * d9 + MathHelper.sqrt_double(d7) * 0.08D;
/* 552 */       entityitem.motionZ = d5 * d9;
/* 553 */       this.worldObj.spawnEntityInWorld((Entity)entityitem);
/* 554 */       this.angler.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5D, this.angler.posZ + 0.5D, this.rand.nextInt(6) + 1));
/* 555 */       i = 1;
/*     */     } 
/*     */     
/* 558 */     if (this.inGround)
/*     */     {
/* 560 */       i = 2;
/*     */     }
/*     */     
/* 563 */     setDead();
/* 564 */     this.angler.fishEntity = null;
/* 565 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack getFishingResult() {
/* 571 */     float f = this.worldObj.rand.nextFloat();
/* 572 */     int i = EnchantmentHelper.getLuckOfSeaModifier((EntityLivingBase)this.angler);
/* 573 */     int j = EnchantmentHelper.getLureModifier((EntityLivingBase)this.angler);
/* 574 */     float f1 = 0.1F - i * 0.025F - j * 0.01F;
/* 575 */     float f2 = 0.05F + i * 0.01F - j * 0.01F;
/* 576 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 577 */     f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
/*     */     
/* 579 */     if (f < f1) {
/*     */       
/* 581 */       this.angler.triggerAchievement(StatList.junkFishedStat);
/* 582 */       return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, JUNK)).getItemStack(this.rand);
/*     */     } 
/*     */ 
/*     */     
/* 586 */     f -= f1;
/*     */     
/* 588 */     if (f < f2) {
/*     */       
/* 590 */       this.angler.triggerAchievement(StatList.treasureFishedStat);
/* 591 */       return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, TREASURE)).getItemStack(this.rand);
/*     */     } 
/*     */ 
/*     */     
/* 595 */     float f3 = f - f2;
/* 596 */     this.angler.triggerAchievement(StatList.fishCaughtStat);
/* 597 */     return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, FISH)).getItemStack(this.rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 607 */     super.setDead();
/*     */     
/* 609 */     if (this.angler != null)
/*     */     {
/* 611 */       this.angler.fishEntity = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\projectile\EntityFishHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */