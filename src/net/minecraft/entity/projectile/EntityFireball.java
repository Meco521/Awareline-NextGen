/*     */ package net.minecraft.entity.projectile;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityFireball extends Entity {
/*  15 */   private int xTile = -1;
/*  16 */   private int yTile = -1;
/*  17 */   private int zTile = -1;
/*     */   
/*     */   private Block inTile;
/*     */   private boolean inGround;
/*     */   public EntityLivingBase shootingEntity;
/*     */   private int ticksAlive;
/*     */   private int ticksInAir;
/*     */   public double accelerationX;
/*     */   public double accelerationY;
/*     */   public double accelerationZ;
/*     */   
/*     */   public EntityFireball(World worldIn) {
/*  29 */     super(worldIn);
/*  30 */     setSize(1.0F, 1.0F);
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
/*  43 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  45 */     if (Double.isNaN(d0))
/*     */     {
/*  47 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  50 */     d0 *= 64.0D;
/*  51 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/*  56 */     super(worldIn);
/*  57 */     setSize(1.0F, 1.0F);
/*  58 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*  59 */     setPosition(x, y, z);
/*  60 */     double d0 = MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
/*  61 */     this.accelerationX = accelX / d0 * 0.1D;
/*  62 */     this.accelerationY = accelY / d0 * 0.1D;
/*  63 */     this.accelerationZ = accelZ / d0 * 0.1D;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/*  68 */     super(worldIn);
/*  69 */     this.shootingEntity = shooter;
/*  70 */     setSize(1.0F, 1.0F);
/*  71 */     setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
/*  72 */     setPosition(this.posX, this.posY, this.posZ);
/*  73 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/*  74 */     accelX += this.rand.nextGaussian() * 0.4D;
/*  75 */     accelY += this.rand.nextGaussian() * 0.4D;
/*  76 */     accelZ += this.rand.nextGaussian() * 0.4D;
/*  77 */     double d0 = MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
/*  78 */     this.accelerationX = accelX / d0 * 0.1D;
/*  79 */     this.accelerationY = accelY / d0 * 0.1D;
/*  80 */     this.accelerationZ = accelZ / d0 * 0.1D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  88 */     if (this.worldObj.isRemote || ((this.shootingEntity == null || !this.shootingEntity.isDead) && this.worldObj.isBlockLoaded(new BlockPos(this)))) {
/*     */       
/*  90 */       super.onUpdate();
/*  91 */       setFire(1);
/*     */       
/*  93 */       if (this.inGround) {
/*     */         
/*  95 */         if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/*     */           
/*  97 */           this.ticksAlive++;
/*     */           
/*  99 */           if (this.ticksAlive == 600)
/*     */           {
/* 101 */             setDead();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 107 */         this.inGround = false;
/* 108 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 109 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 110 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 111 */         this.ticksAlive = 0;
/* 112 */         this.ticksInAir = 0;
/*     */       }
/*     */       else {
/*     */         
/* 116 */         this.ticksInAir++;
/*     */       } 
/*     */       
/* 119 */       Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 120 */       Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 121 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
/* 122 */       vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 123 */       vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 125 */       if (movingobjectposition != null)
/*     */       {
/* 127 */         vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 130 */       Entity entity = null;
/* 131 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 132 */       double d0 = 0.0D;
/*     */       
/* 134 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 136 */         Entity entity1 = list.get(i);
/*     */         
/* 138 */         if (entity1.canBeCollidedWith() && (!entity1.isEntityEqual((Entity)this.shootingEntity) || this.ticksInAir >= 25)) {
/*     */           
/* 140 */           float f = 0.3F;
/* 141 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
/* 142 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
/*     */           
/* 144 */           if (movingobjectposition1 != null) {
/*     */             
/* 146 */             double d1 = vec3.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 148 */             if (d1 < d0 || d0 == 0.0D) {
/*     */               
/* 150 */               entity = entity1;
/* 151 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 157 */       if (entity != null)
/*     */       {
/* 159 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 162 */       if (movingobjectposition != null)
/*     */       {
/* 164 */         onImpact(movingobjectposition);
/*     */       }
/*     */       
/* 167 */       this.posX += this.motionX;
/* 168 */       this.posY += this.motionY;
/* 169 */       this.posZ += this.motionZ;
/* 170 */       float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 171 */       this.rotationYaw = (float)(MathHelper.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;
/*     */       
/* 173 */       for (this.rotationPitch = (float)(MathHelper.atan2(f1, this.motionY) * 180.0D / Math.PI) - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 178 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */       {
/* 180 */         this.prevRotationPitch += 360.0F;
/*     */       }
/*     */       
/* 183 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */       {
/* 185 */         this.prevRotationYaw -= 360.0F;
/*     */       }
/*     */       
/* 188 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */       {
/* 190 */         this.prevRotationYaw += 360.0F;
/*     */       }
/*     */       
/* 193 */       this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 194 */       this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 195 */       float f2 = getMotionFactor();
/*     */       
/* 197 */       if (isInWater()) {
/*     */         
/* 199 */         for (int j = 0; j < 4; j++) {
/*     */           
/* 201 */           float f3 = 0.25F;
/* 202 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */         
/* 205 */         f2 = 0.8F;
/*     */       } 
/*     */       
/* 208 */       this.motionX += this.accelerationX;
/* 209 */       this.motionY += this.accelerationY;
/* 210 */       this.motionZ += this.accelerationZ;
/* 211 */       this.motionX *= f2;
/* 212 */       this.motionY *= f2;
/* 213 */       this.motionZ *= f2;
/* 214 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 215 */       setPosition(this.posX, this.posY, this.posZ);
/*     */     }
/*     */     else {
/*     */       
/* 219 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getMotionFactor() {
/* 228 */     return 0.95F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onImpact(MovingObjectPosition paramMovingObjectPosition);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 241 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 242 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 243 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 244 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 245 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 246 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 247 */     tagCompound.setTag("direction", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 255 */     this.xTile = tagCompund.getShort("xTile");
/* 256 */     this.yTile = tagCompund.getShort("yTile");
/* 257 */     this.zTile = tagCompund.getShort("zTile");
/*     */     
/* 259 */     if (tagCompund.hasKey("inTile", 8)) {
/*     */       
/* 261 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 265 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 268 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/*     */     
/* 270 */     if (tagCompund.hasKey("direction", 9)) {
/*     */       
/* 272 */       NBTTagList nbttaglist = tagCompund.getTagList("direction", 6);
/* 273 */       this.motionX = nbttaglist.getDoubleAt(0);
/* 274 */       this.motionY = nbttaglist.getDoubleAt(1);
/* 275 */       this.motionZ = nbttaglist.getDoubleAt(2);
/*     */     }
/*     */     else {
/*     */       
/* 279 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCollisionBorderSize() {
/* 293 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 301 */     if (isEntityInvulnerable(source))
/*     */     {
/* 303 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 307 */     setBeenAttacked();
/*     */     
/* 309 */     if (source.getEntity() != null) {
/*     */       
/* 311 */       Vec3 vec3 = source.getEntity().getLookVec();
/*     */       
/* 313 */       if (vec3 != null) {
/*     */         
/* 315 */         this.motionX = vec3.xCoord;
/* 316 */         this.motionY = vec3.yCoord;
/* 317 */         this.motionZ = vec3.zCoord;
/* 318 */         this.accelerationX = this.motionX * 0.1D;
/* 319 */         this.accelerationY = this.motionY * 0.1D;
/* 320 */         this.accelerationZ = this.motionZ * 0.1D;
/*     */       } 
/*     */       
/* 323 */       if (source.getEntity() instanceof EntityLivingBase)
/*     */       {
/* 325 */         this.shootingEntity = (EntityLivingBase)source.getEntity();
/*     */       }
/*     */       
/* 328 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 332 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/* 342 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 347 */     return 15728880;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\projectile\EntityFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */