/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAICreeperSwell;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityCreeper extends EntityMob {
/*  28 */   private int fuseTime = 30;
/*     */   private int lastActiveTime;
/*     */   private int timeSinceIgnited;
/*  31 */   private int explosionRadius = 3;
/*  32 */   private int field_175494_bm = 0;
/*     */ 
/*     */   
/*     */   public EntityCreeper(World worldIn) {
/*  36 */     super(worldIn);
/*  37 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  38 */     this.tasks.addTask(2, (EntityAIBase)new EntityAICreeperSwell(this));
/*  39 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
/*  40 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, 1.0D, false));
/*  41 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander(this, 0.8D));
/*  42 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  43 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  44 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  45 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  50 */     super.applyEntityAttributes();
/*  51 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFallHeight() {
/*  59 */     return (getAttackTarget() == null) ? 3 : (3 + (int)(getHealth() - 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/*  64 */     super.fall(distance, damageMultiplier);
/*  65 */     this.timeSinceIgnited = (int)(this.timeSinceIgnited + distance * 1.5F);
/*     */     
/*  67 */     if (this.timeSinceIgnited > this.fuseTime - 5)
/*     */     {
/*  69 */       this.timeSinceIgnited = this.fuseTime - 5;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  75 */     super.entityInit();
/*  76 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)-1));
/*  77 */     this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
/*  78 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  86 */     super.writeEntityToNBT(tagCompound);
/*     */     
/*  88 */     if (this.dataWatcher.getWatchableObjectByte(17) == 1)
/*     */     {
/*  90 */       tagCompound.setBoolean("powered", true);
/*     */     }
/*     */     
/*  93 */     tagCompound.setShort("Fuse", (short)this.fuseTime);
/*  94 */     tagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
/*  95 */     tagCompound.setBoolean("ignited", hasIgnited());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 103 */     super.readEntityFromNBT(tagCompund);
/* 104 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)(tagCompund.getBoolean("powered") ? 1 : 0)));
/*     */     
/* 106 */     if (tagCompund.hasKey("Fuse", 99))
/*     */     {
/* 108 */       this.fuseTime = tagCompund.getShort("Fuse");
/*     */     }
/*     */     
/* 111 */     if (tagCompund.hasKey("ExplosionRadius", 99))
/*     */     {
/* 113 */       this.explosionRadius = tagCompund.getByte("ExplosionRadius");
/*     */     }
/*     */     
/* 116 */     if (tagCompund.getBoolean("ignited"))
/*     */     {
/* 118 */       ignite();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 127 */     if (isEntityAlive()) {
/*     */       
/* 129 */       this.lastActiveTime = this.timeSinceIgnited;
/*     */       
/* 131 */       if (hasIgnited())
/*     */       {
/* 133 */         setCreeperState(1);
/*     */       }
/*     */       
/* 136 */       int i = getCreeperState();
/*     */       
/* 138 */       if (i > 0 && this.timeSinceIgnited == 0)
/*     */       {
/* 140 */         playSound("creeper.primed", 1.0F, 0.5F);
/*     */       }
/*     */       
/* 143 */       this.timeSinceIgnited += i;
/*     */       
/* 145 */       if (this.timeSinceIgnited < 0)
/*     */       {
/* 147 */         this.timeSinceIgnited = 0;
/*     */       }
/*     */       
/* 150 */       if (this.timeSinceIgnited >= this.fuseTime) {
/*     */         
/* 152 */         this.timeSinceIgnited = this.fuseTime;
/* 153 */         explode();
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 165 */     return "mob.creeper.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 173 */     return "mob.creeper.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 181 */     super.onDeath(cause);
/*     */     
/* 183 */     if (cause.getEntity() instanceof EntitySkeleton) {
/*     */       
/* 185 */       int i = Item.getIdFromItem(Items.record_13);
/* 186 */       int j = Item.getIdFromItem(Items.record_wait);
/* 187 */       int k = i + this.rand.nextInt(j - i + 1);
/* 188 */       dropItem(Item.getItemById(k), 1);
/*     */     }
/* 190 */     else if (cause.getEntity() instanceof EntityCreeper && cause.getEntity() != this && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
/*     */       
/* 192 */       ((EntityCreeper)cause.getEntity()).func_175493_co();
/* 193 */       entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPowered() {
/* 207 */     return (this.dataWatcher.getWatchableObjectByte(17) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCreeperFlashIntensity(float p_70831_1_) {
/* 215 */     return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 220 */     return Items.gunpowder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCreeperState() {
/* 228 */     return this.dataWatcher.getWatchableObjectByte(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreeperState(int state) {
/* 236 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 244 */     super.onStruckByLightning(lightningBolt);
/* 245 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean interact(EntityPlayer player) {
/* 253 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 255 */     if (itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
/*     */       
/* 257 */       this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
/* 258 */       player.swingItem();
/*     */       
/* 260 */       if (!this.worldObj.isRemote) {
/*     */         
/* 262 */         ignite();
/* 263 */         itemstack.damageItem(1, (EntityLivingBase)player);
/* 264 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void explode() {
/* 276 */     if (!this.worldObj.isRemote) {
/*     */       
/* 278 */       boolean flag = this.worldObj.getGameRules().getBoolean("mobGriefing");
/* 279 */       float f = getPowered() ? 2.0F : 1.0F;
/* 280 */       this.worldObj.createExplosion((Entity)this, this.posX, this.posY, this.posZ, this.explosionRadius * f, flag);
/* 281 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIgnited() {
/* 287 */     return (this.dataWatcher.getWatchableObjectByte(18) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void ignite() {
/* 292 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAIEnabled() {
/* 300 */     return (this.field_175494_bm < 1 && this.worldObj.getGameRules().getBoolean("doMobLoot"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175493_co() {
/* 305 */     this.field_175494_bm++;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityCreeper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */