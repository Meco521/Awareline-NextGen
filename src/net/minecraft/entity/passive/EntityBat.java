/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityBat
/*     */   extends EntityAmbientCreature
/*     */ {
/*     */   private BlockPos spawnPosition;
/*     */   
/*     */   public EntityBat(World worldIn) {
/*  22 */     super(worldIn);
/*  23 */     setSize(0.5F, 0.9F);
/*  24 */     setIsBatHanging(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  29 */     super.entityInit();
/*  30 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  38 */     return 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundPitch() {
/*  46 */     return super.getSoundPitch() * 0.95F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  54 */     return (getIsBatHanging() && this.rand.nextInt(4) != 0) ? null : "mob.bat.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  62 */     return "mob.bat.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  70 */     return "mob.bat.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void collideWithNearbyEntities() {}
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  91 */     super.applyEntityAttributes();
/*  92 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsBatHanging() {
/*  97 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIsBatHanging(boolean isHanging) {
/* 102 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 104 */     if (isHanging) {
/*     */       
/* 106 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 110 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 119 */     super.onUpdate();
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/* 124 */     this.posY = MathHelper.floor_double(this.posY) + 1.0D - this.height;
/*     */ 
/*     */ 
/*     */     
/* 128 */     this.motionY *= 0.6000000238418579D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 134 */     super.updateAITasks();
/* 135 */     BlockPos blockpos = new BlockPos((Entity)this);
/* 136 */     BlockPos blockpos1 = blockpos.up();
/*     */     
/* 138 */     if (getIsBatHanging()) {
/*     */       
/* 140 */       if (!this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube()) {
/*     */         
/* 142 */         setIsBatHanging(false);
/* 143 */         this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, blockpos, 0);
/*     */       }
/*     */       else {
/*     */         
/* 147 */         if (this.rand.nextInt(200) == 0)
/*     */         {
/* 149 */           this.rotationYawHead = this.rand.nextInt(360);
/*     */         }
/*     */         
/* 152 */         if (this.worldObj.getClosestPlayerToEntity((Entity)this, 4.0D) != null)
/*     */         {
/* 154 */           setIsBatHanging(false);
/* 155 */           this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, blockpos, 0);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 161 */       if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
/*     */       {
/* 163 */         this.spawnPosition = null;
/*     */       }
/*     */       
/* 166 */       if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0D)
/*     */       {
/* 168 */         this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
/*     */       }
/*     */       
/* 171 */       double d0 = this.spawnPosition.getX() + 0.5D - this.posX;
/* 172 */       double d1 = this.spawnPosition.getY() + 0.1D - this.posY;
/* 173 */       double d2 = this.spawnPosition.getZ() + 0.5D - this.posZ;
/* 174 */       this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
/* 175 */       this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 176 */       this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
/* 177 */       float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
/* 178 */       float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
/* 179 */       this.moveForward = 0.5F;
/* 180 */       this.rotationYaw += f1;
/*     */       
/* 182 */       if (this.rand.nextInt(100) == 0 && this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube())
/*     */       {
/* 184 */         setIsBatHanging(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesEntityNotTriggerPressurePlate() {
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 219 */     if (isEntityInvulnerable(source))
/*     */     {
/* 221 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 225 */     if (!this.worldObj.isRemote && getIsBatHanging())
/*     */     {
/* 227 */       setIsBatHanging(false);
/*     */     }
/*     */     
/* 230 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 239 */     super.readEntityFromNBT(tagCompund);
/* 240 */     this.dataWatcher.updateObject(16, Byte.valueOf(tagCompund.getByte("BatFlags")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 248 */     super.writeEntityToNBT(tagCompound);
/* 249 */     tagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 257 */     BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */     
/* 259 */     if (blockpos.getY() >= this.worldObj.getSeaLevel())
/*     */     {
/* 261 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 265 */     int i = this.worldObj.getLightFromNeighbors(blockpos);
/* 266 */     int j = 4;
/*     */     
/* 268 */     if (isDateAroundHalloween(this.worldObj.getCurrentDate())) {
/*     */       
/* 270 */       j = 7;
/*     */     }
/* 272 */     else if (this.rand.nextBoolean()) {
/*     */       
/* 274 */       return false;
/*     */     } 
/*     */     
/* 277 */     return (i > this.rand.nextInt(j)) ? false : super.getCanSpawnHere();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDateAroundHalloween(Calendar p_175569_1_) {
/* 283 */     return ((p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20) || (p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 288 */     return this.height / 2.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */