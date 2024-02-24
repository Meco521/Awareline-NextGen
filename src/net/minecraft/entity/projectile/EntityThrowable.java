/*     */ package net.minecraft.entity.projectile;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public abstract class EntityThrowable extends Entity implements IProjectile {
/*  19 */   private int xTile = -1;
/*  20 */   private int yTile = -1;
/*  21 */   private int zTile = -1;
/*     */   
/*     */   private Block inTile;
/*     */   
/*     */   protected boolean inGround;
/*     */   
/*     */   public int throwableShake;
/*     */   private EntityLivingBase thrower;
/*     */   private String throwerName;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   
/*     */   public EntityThrowable(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     setSize(0.25F, 0.25F);
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
/*  48 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  50 */     if (Double.isNaN(d0))
/*     */     {
/*  52 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  55 */     d0 *= 64.0D;
/*  56 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityThrowable(World worldIn, EntityLivingBase throwerIn) {
/*  61 */     super(worldIn);
/*  62 */     this.thrower = throwerIn;
/*  63 */     setSize(0.25F, 0.25F);
/*  64 */     setLocationAndAngles(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(), throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
/*  65 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  66 */     this.posY -= 0.10000000149011612D;
/*  67 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  68 */     setPosition(this.posX, this.posY, this.posZ);
/*  69 */     float f = 0.4F;
/*  70 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  71 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  72 */     this.motionY = (-MathHelper.sin((this.rotationPitch + getInaccuracy()) / 180.0F * 3.1415927F) * f);
/*  73 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, getVelocity(), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityThrowable(World worldIn, double x, double y, double z) {
/*  78 */     super(worldIn);
/*  79 */     this.ticksInGround = 0;
/*  80 */     setSize(0.25F, 0.25F);
/*  81 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getVelocity() {
/*  86 */     return 1.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getInaccuracy() {
/*  91 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/*  99 */     float f = MathHelper.sqrt_double(x * x + y * y + z * z);
/* 100 */     x /= f;
/* 101 */     y /= f;
/* 102 */     z /= f;
/* 103 */     x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 104 */     y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 105 */     z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 106 */     x *= velocity;
/* 107 */     y *= velocity;
/* 108 */     z *= velocity;
/* 109 */     this.motionX = x;
/* 110 */     this.motionY = y;
/* 111 */     this.motionZ = z;
/* 112 */     float f1 = MathHelper.sqrt_double(x * x + z * z);
/* 113 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 114 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 180.0D / Math.PI);
/* 115 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 123 */     this.motionX = x;
/* 124 */     this.motionY = y;
/* 125 */     this.motionZ = z;
/*     */     
/* 127 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 129 */       float f = MathHelper.sqrt_double(x * x + z * z);
/* 130 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 131 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f) * 180.0D / Math.PI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 140 */     this.lastTickPosX = this.posX;
/* 141 */     this.lastTickPosY = this.posY;
/* 142 */     this.lastTickPosZ = this.posZ;
/* 143 */     super.onUpdate();
/*     */     
/* 145 */     if (this.throwableShake > 0)
/*     */     {
/* 147 */       this.throwableShake--;
/*     */     }
/*     */     
/* 150 */     if (this.inGround) {
/*     */       
/* 152 */       if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/*     */         
/* 154 */         this.ticksInGround++;
/*     */         
/* 156 */         if (this.ticksInGround == 1200)
/*     */         {
/* 158 */           setDead();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 164 */       this.inGround = false;
/* 165 */       this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 166 */       this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 167 */       this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 168 */       this.ticksInGround = 0;
/* 169 */       this.ticksInAir = 0;
/*     */     }
/*     */     else {
/*     */       
/* 173 */       this.ticksInAir++;
/*     */     } 
/*     */     
/* 176 */     Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 177 */     Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 178 */     MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
/* 179 */     vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 180 */     vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */     
/* 182 */     if (movingobjectposition != null)
/*     */     {
/* 184 */       vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */     }
/*     */     
/* 187 */     if (!this.worldObj.isRemote) {
/*     */       
/* 189 */       Entity entity = null;
/* 190 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 191 */       double d0 = 0.0D;
/* 192 */       EntityLivingBase entitylivingbase = getThrower();
/*     */       
/* 194 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/* 196 */         Entity entity1 = list.get(j);
/*     */         
/* 198 */         if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase || this.ticksInAir >= 5)) {
/*     */           
/* 200 */           float f = 0.3F;
/* 201 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
/* 202 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
/*     */           
/* 204 */           if (movingobjectposition1 != null) {
/*     */             
/* 206 */             double d1 = vec3.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 208 */             if (d1 < d0 || d0 == 0.0D) {
/*     */               
/* 210 */               entity = entity1;
/* 211 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 217 */       if (entity != null)
/*     */       {
/* 219 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */     } 
/*     */     
/* 223 */     if (movingobjectposition != null)
/*     */     {
/* 225 */       if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.portal) {
/*     */         
/* 227 */         setPortal(movingobjectposition.getBlockPos());
/*     */       }
/*     */       else {
/*     */         
/* 231 */         onImpact(movingobjectposition);
/*     */       } 
/*     */     }
/*     */     
/* 235 */     this.posX += this.motionX;
/* 236 */     this.posY += this.motionY;
/* 237 */     this.posZ += this.motionZ;
/* 238 */     float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 239 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */     
/* 241 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 248 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 251 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 253 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 256 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 258 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 261 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 262 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 263 */     float f2 = 0.99F;
/* 264 */     float f3 = getGravityVelocity();
/*     */     
/* 266 */     if (isInWater()) {
/*     */       
/* 268 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 270 */         float f4 = 0.25F;
/* 271 */         this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f4, this.posY - this.motionY * f4, this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */       } 
/*     */       
/* 274 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 277 */     this.motionX *= f2;
/* 278 */     this.motionY *= f2;
/* 279 */     this.motionZ *= f2;
/* 280 */     this.motionY -= f3;
/* 281 */     setPosition(this.posX, this.posY, this.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getGravityVelocity() {
/* 289 */     return 0.03F;
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
/* 302 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 303 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 304 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 305 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 306 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 307 */     tagCompound.setByte("shake", (byte)this.throwableShake);
/* 308 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/*     */     
/* 310 */     if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 312 */       this.throwerName = this.thrower.getName();
/*     */     }
/*     */     
/* 315 */     tagCompound.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 323 */     this.xTile = tagCompund.getShort("xTile");
/* 324 */     this.yTile = tagCompund.getShort("yTile");
/* 325 */     this.zTile = tagCompund.getShort("zTile");
/*     */     
/* 327 */     if (tagCompund.hasKey("inTile", 8)) {
/*     */       
/* 329 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 333 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 336 */     this.throwableShake = tagCompund.getByte("shake") & 0xFF;
/* 337 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/* 338 */     this.thrower = null;
/* 339 */     this.throwerName = tagCompund.getString("ownerName");
/*     */     
/* 341 */     if (this.throwerName != null && this.throwerName.isEmpty())
/*     */     {
/* 343 */       this.throwerName = null;
/*     */     }
/*     */     
/* 346 */     this.thrower = getThrower();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLivingBase getThrower() {
/* 351 */     if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty()) {
/*     */       
/* 353 */       this.thrower = (EntityLivingBase)this.worldObj.getPlayerEntityByName(this.throwerName);
/*     */       
/* 355 */       if (this.thrower == null && this.worldObj instanceof WorldServer) {
/*     */         
/*     */         try {
/*     */           
/* 359 */           Entity entity = ((WorldServer)this.worldObj).getEntityFromUuid(UUID.fromString(this.throwerName));
/*     */           
/* 361 */           if (entity instanceof EntityLivingBase)
/*     */           {
/* 363 */             this.thrower = (EntityLivingBase)entity;
/*     */           }
/*     */         }
/* 366 */         catch (Throwable var2) {
/*     */           
/* 368 */           this.thrower = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 373 */     return this.thrower;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\projectile\EntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */