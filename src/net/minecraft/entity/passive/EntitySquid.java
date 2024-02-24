/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntitySquid
/*     */   extends EntityWaterMob
/*     */ {
/*     */   public float squidPitch;
/*     */   public float prevSquidPitch;
/*     */   public float squidYaw;
/*     */   public float prevSquidYaw;
/*     */   public float squidRotation;
/*     */   public float prevSquidRotation;
/*     */   public float tentacleAngle;
/*     */   public float lastTentacleAngle;
/*     */   private float randomMotionSpeed;
/*     */   private float rotationVelocity;
/*     */   private float field_70871_bB;
/*     */   private float randomMotionVecX;
/*     */   private float randomMotionVecY;
/*     */   private float randomMotionVecZ;
/*     */   
/*     */   public EntitySquid(World worldIn) {
/*  44 */     super(worldIn);
/*  45 */     setSize(0.95F, 0.95F);
/*  46 */     this.rand.setSeed((1 + getEntityId()));
/*  47 */     this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
/*  48 */     this.tasks.addTask(0, new AIMoveRandom(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  53 */     super.applyEntityAttributes();
/*  54 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  59 */     return this.height * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  91 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 105 */     return false;
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
/* 117 */     int i = this.rand.nextInt(3 + lootingModifier) + 1;
/*     */     
/* 119 */     for (int j = 0; j < i; j++)
/*     */     {
/* 121 */       entityDropItem(new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInWater() {
/* 131 */     return this.worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, (Entity)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 140 */     super.onLivingUpdate();
/* 141 */     this.prevSquidPitch = this.squidPitch;
/* 142 */     this.prevSquidYaw = this.squidYaw;
/* 143 */     this.prevSquidRotation = this.squidRotation;
/* 144 */     this.lastTentacleAngle = this.tentacleAngle;
/* 145 */     this.squidRotation += this.rotationVelocity;
/*     */     
/* 147 */     if (this.squidRotation > 6.283185307179586D)
/*     */     {
/* 149 */       if (this.worldObj.isRemote) {
/*     */         
/* 151 */         this.squidRotation = 6.2831855F;
/*     */       }
/*     */       else {
/*     */         
/* 155 */         this.squidRotation = (float)(this.squidRotation - 6.283185307179586D);
/*     */         
/* 157 */         if (this.rand.nextInt(10) == 0)
/*     */         {
/* 159 */           this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
/*     */         }
/*     */         
/* 162 */         this.worldObj.setEntityState((Entity)this, (byte)19);
/*     */       } 
/*     */     }
/*     */     
/* 166 */     if (this.inWater) {
/*     */       
/* 168 */       if (this.squidRotation < 3.1415927F) {
/*     */         
/* 170 */         float f = this.squidRotation / 3.1415927F;
/* 171 */         this.tentacleAngle = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
/*     */         
/* 173 */         if (f > 0.75D)
/*     */         {
/* 175 */           this.randomMotionSpeed = 1.0F;
/* 176 */           this.field_70871_bB = 1.0F;
/*     */         }
/*     */         else
/*     */         {
/* 180 */           this.field_70871_bB *= 0.8F;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 185 */         this.tentacleAngle = 0.0F;
/* 186 */         this.randomMotionSpeed *= 0.9F;
/* 187 */         this.field_70871_bB *= 0.99F;
/*     */       } 
/*     */       
/* 190 */       if (!this.worldObj.isRemote) {
/*     */         
/* 192 */         this.motionX = (this.randomMotionVecX * this.randomMotionSpeed);
/* 193 */         this.motionY = (this.randomMotionVecY * this.randomMotionSpeed);
/* 194 */         this.motionZ = (this.randomMotionVecZ * this.randomMotionSpeed);
/*     */       } 
/*     */       
/* 197 */       float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 198 */       this.renderYawOffset += (-((float)MathHelper.atan2(this.motionX, this.motionZ)) * 180.0F / 3.1415927F - this.renderYawOffset) * 0.1F;
/* 199 */       this.rotationYaw = this.renderYawOffset;
/* 200 */       this.squidYaw = (float)(this.squidYaw + Math.PI * this.field_70871_bB * 1.5D);
/* 201 */       this.squidPitch += (-((float)MathHelper.atan2(f1, this.motionY)) * 180.0F / 3.1415927F - this.squidPitch) * 0.1F;
/*     */     }
/*     */     else {
/*     */       
/* 205 */       this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927F * 0.25F;
/*     */       
/* 207 */       if (!this.worldObj.isRemote) {
/*     */         
/* 209 */         this.motionX = 0.0D;
/* 210 */         this.motionY -= 0.08D;
/* 211 */         this.motionY *= 0.9800000190734863D;
/* 212 */         this.motionZ = 0.0D;
/*     */       } 
/*     */       
/* 215 */       this.squidPitch = (float)(this.squidPitch + (-90.0F - this.squidPitch) * 0.02D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 224 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 232 */     return (this.posY > 45.0D && this.posY < this.worldObj.getSeaLevel() && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 237 */     if (id == 19) {
/*     */       
/* 239 */       this.squidRotation = 0.0F;
/*     */     }
/*     */     else {
/*     */       
/* 243 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175568_b(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
/* 249 */     this.randomMotionVecX = randomMotionVecXIn;
/* 250 */     this.randomMotionVecY = randomMotionVecYIn;
/* 251 */     this.randomMotionVecZ = randomMotionVecZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175567_n() {
/* 256 */     return (this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F);
/*     */   }
/*     */   
/*     */   static class AIMoveRandom
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySquid squid;
/*     */     
/*     */     public AIMoveRandom(EntitySquid p_i45859_1_) {
/* 265 */       this.squid = p_i45859_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 270 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 275 */       int i = this.squid.getAge();
/*     */       
/* 277 */       if (i > 100) {
/*     */         
/* 279 */         this.squid.func_175568_b(0.0F, 0.0F, 0.0F);
/*     */       }
/* 281 */       else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.inWater || !this.squid.func_175567_n()) {
/*     */         
/* 283 */         float f = this.squid.getRNG().nextFloat() * 3.1415927F * 2.0F;
/* 284 */         float f1 = MathHelper.cos(f) * 0.2F;
/* 285 */         float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
/* 286 */         float f3 = MathHelper.sin(f) * 0.2F;
/* 287 */         this.squid.func_175568_b(f1, f2, f3);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntitySquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */