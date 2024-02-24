/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWitch extends EntityMob implements IRangedAttackMob {
/*  27 */   private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
/*  28 */   private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, 0)).setSaved(false);
/*     */ 
/*     */   
/*  31 */   private static final Item[] witchDrops = new Item[] { Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
/*     */ 
/*     */ 
/*     */   
/*     */   private int witchAttackTimer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityWitch(World worldIn) {
/*  41 */     super(worldIn);
/*  42 */     setSize(0.6F, 1.95F);
/*  43 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  44 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIArrowAttack(this, 1.0D, 60, 10.0F));
/*  45 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  46 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  47 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  48 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  49 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  54 */     super.entityInit();
/*  55 */     getDataWatcher().addObject(21, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAggressive(boolean aggressive) {
/*  87 */     getDataWatcher().updateObject(21, Byte.valueOf((byte)(aggressive ? 1 : 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAggressive() {
/*  95 */     return (getDataWatcher().getWatchableObjectByte(21) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 100 */     super.applyEntityAttributes();
/* 101 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
/* 102 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 111 */     if (!this.worldObj.isRemote) {
/*     */       
/* 113 */       if (getAggressive()) {
/*     */         
/* 115 */         if (this.witchAttackTimer-- <= 0)
/*     */         {
/* 117 */           setAggressive(false);
/* 118 */           ItemStack itemstack = getHeldItem();
/* 119 */           setCurrentItemOrArmor(0, (ItemStack)null);
/*     */           
/* 121 */           if (itemstack != null && itemstack.getItem() == Items.potionitem) {
/*     */             
/* 123 */             List<PotionEffect> list = Items.potionitem.getEffects(itemstack);
/*     */             
/* 125 */             if (list != null)
/*     */             {
/* 127 */               for (PotionEffect potioneffect : list)
/*     */               {
/* 129 */                 addPotionEffect(new PotionEffect(potioneffect));
/*     */               }
/*     */             }
/*     */           } 
/*     */           
/* 134 */           getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(MODIFIER);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 139 */         int i = -1;
/*     */         
/* 141 */         if (this.rand.nextFloat() < 0.15F && isInsideOfMaterial(Material.water) && !isPotionActive(Potion.waterBreathing)) {
/*     */           
/* 143 */           i = 8237;
/*     */         }
/* 145 */         else if (this.rand.nextFloat() < 0.15F && isBurning() && !isPotionActive(Potion.fireResistance)) {
/*     */           
/* 147 */           i = 16307;
/*     */         }
/* 149 */         else if (this.rand.nextFloat() < 0.05F && getHealth() < getMaxHealth()) {
/*     */           
/* 151 */           i = 16341;
/*     */         }
/* 153 */         else if (this.rand.nextFloat() < 0.25F && getAttackTarget() != null && !isPotionActive(Potion.moveSpeed) && getAttackTarget().getDistanceSqToEntity((Entity)this) > 121.0D) {
/*     */           
/* 155 */           i = 16274;
/*     */         }
/* 157 */         else if (this.rand.nextFloat() < 0.25F && getAttackTarget() != null && !isPotionActive(Potion.moveSpeed) && getAttackTarget().getDistanceSqToEntity((Entity)this) > 121.0D) {
/*     */           
/* 159 */           i = 16274;
/*     */         } 
/*     */         
/* 162 */         if (i > -1) {
/*     */           
/* 164 */           setCurrentItemOrArmor(0, new ItemStack((Item)Items.potionitem, 1, i));
/* 165 */           this.witchAttackTimer = getHeldItem().getMaxItemUseDuration();
/* 166 */           setAggressive(true);
/* 167 */           IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 168 */           iattributeinstance.removeModifier(MODIFIER);
/* 169 */           iattributeinstance.applyModifier(MODIFIER);
/*     */         } 
/*     */       } 
/*     */       
/* 173 */       if (this.rand.nextFloat() < 7.5E-4F)
/*     */       {
/* 175 */         this.worldObj.setEntityState((Entity)this, (byte)15);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 184 */     if (id == 15) {
/*     */       
/* 186 */       for (int i = 0; i < this.rand.nextInt(35) + 10; i++)
/*     */       {
/* 188 */         this.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842D, (getEntityBoundingBox()).maxY + 0.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 193 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
/* 202 */     damage = super.applyPotionDamageCalculations(source, damage);
/*     */     
/* 204 */     if (source.getEntity() == this)
/*     */     {
/* 206 */       damage = 0.0F;
/*     */     }
/*     */     
/* 209 */     if (source.isMagicDamage())
/*     */     {
/* 211 */       damage = (float)(damage * 0.15D);
/*     */     }
/*     */     
/* 214 */     return damage;
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
/* 226 */     int i = this.rand.nextInt(3) + 1;
/*     */     
/* 228 */     for (int j = 0; j < i; j++) {
/*     */       
/* 230 */       int k = this.rand.nextInt(3);
/* 231 */       Item item = witchDrops[this.rand.nextInt(witchDrops.length)];
/*     */       
/* 233 */       if (lootingModifier > 0)
/*     */       {
/* 235 */         k += this.rand.nextInt(lootingModifier + 1);
/*     */       }
/*     */       
/* 238 */       for (int l = 0; l < k; l++)
/*     */       {
/* 240 */         dropItem(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
/* 250 */     if (!getAggressive()) {
/*     */       
/* 252 */       EntityPotion entitypotion = new EntityPotion(this.worldObj, (EntityLivingBase)this, 32732);
/* 253 */       double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
/* 254 */       entitypotion.rotationPitch -= -20.0F;
/* 255 */       double d1 = target.posX + target.motionX - this.posX;
/* 256 */       double d2 = d0 - this.posY;
/* 257 */       double d3 = target.posZ + target.motionZ - this.posZ;
/* 258 */       float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
/*     */       
/* 260 */       if (f >= 8.0F && !target.isPotionActive(Potion.moveSlowdown)) {
/*     */         
/* 262 */         entitypotion.setPotionDamage(32698);
/*     */       }
/* 264 */       else if (target.getHealth() >= 8.0F && !target.isPotionActive(Potion.poison)) {
/*     */         
/* 266 */         entitypotion.setPotionDamage(32660);
/*     */       }
/* 268 */       else if (f <= 3.0F && !target.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25F) {
/*     */         
/* 270 */         entitypotion.setPotionDamage(32696);
/*     */       } 
/*     */       
/* 273 */       entitypotion.setThrowableHeading(d1, d2 + (f * 0.2F), d3, 0.75F, 8.0F);
/* 274 */       this.worldObj.spawnEntityInWorld((Entity)entitypotion);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 280 */     return 1.62F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */