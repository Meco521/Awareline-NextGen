/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class EntityPotion
/*     */   extends EntityThrowable
/*     */ {
/*     */   private ItemStack potionDamage;
/*     */   
/*     */   public EntityPotion(World worldIn) {
/*  25 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPotion(World worldIn, EntityLivingBase throwerIn, int meta) {
/*  30 */     this(worldIn, throwerIn, new ItemStack((Item)Items.potionitem, 1, meta));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPotion(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
/*  35 */     super(worldIn, throwerIn);
/*  36 */     this.potionDamage = potionDamageIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPotion(World worldIn, double x, double y, double z, int p_i1791_8_) {
/*  41 */     this(worldIn, x, y, z, new ItemStack((Item)Items.potionitem, 1, p_i1791_8_));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPotion(World worldIn, double x, double y, double z, ItemStack potionDamageIn) {
/*  46 */     super(worldIn, x, y, z);
/*  47 */     this.potionDamage = potionDamageIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getGravityVelocity() {
/*  55 */     return 0.05F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getVelocity() {
/*  60 */     return 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getInaccuracy() {
/*  65 */     return -20.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPotionDamage(int potionId) {
/*  73 */     if (this.potionDamage == null)
/*     */     {
/*  75 */       this.potionDamage = new ItemStack((Item)Items.potionitem, 1, 0);
/*     */     }
/*     */     
/*  78 */     this.potionDamage.setItemDamage(potionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPotionDamage() {
/*  86 */     if (this.potionDamage == null)
/*     */     {
/*  88 */       this.potionDamage = new ItemStack((Item)Items.potionitem, 1, 0);
/*     */     }
/*     */     
/*  91 */     return this.potionDamage.getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(MovingObjectPosition p_70184_1_) {
/*  99 */     if (!this.worldObj.isRemote) {
/*     */       
/* 101 */       List<PotionEffect> list = Items.potionitem.getEffects(this.potionDamage);
/*     */       
/* 103 */       if (list != null && !list.isEmpty()) {
/*     */         
/* 105 */         AxisAlignedBB axisalignedbb = getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
/* 106 */         List<EntityLivingBase> list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
/*     */         
/* 108 */         if (!list1.isEmpty())
/*     */         {
/* 110 */           for (EntityLivingBase entitylivingbase : list1) {
/*     */             
/* 112 */             double d0 = getDistanceSqToEntity((Entity)entitylivingbase);
/*     */             
/* 114 */             if (d0 < 16.0D) {
/*     */               
/* 116 */               double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
/*     */               
/* 118 */               if (entitylivingbase == p_70184_1_.entityHit)
/*     */               {
/* 120 */                 d1 = 1.0D;
/*     */               }
/*     */               
/* 123 */               for (PotionEffect potioneffect : list) {
/*     */                 
/* 125 */                 int i = potioneffect.getPotionID();
/*     */                 
/* 127 */                 if (Potion.potionTypes[i].isInstant()) {
/*     */                   
/* 129 */                   Potion.potionTypes[i].affectEntity(this, (Entity)getThrower(), entitylivingbase, potioneffect.getAmplifier(), d1);
/*     */                   
/*     */                   continue;
/*     */                 } 
/* 133 */                 int j = (int)(d1 * potioneffect.getDuration() + 0.5D);
/*     */                 
/* 135 */                 if (j > 20)
/*     */                 {
/* 137 */                   entitylivingbase.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 146 */       this.worldObj.playAuxSFX(2002, new BlockPos(this), getPotionDamage());
/* 147 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 156 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 158 */     if (tagCompund.hasKey("Potion", 10)) {
/*     */       
/* 160 */       this.potionDamage = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("Potion"));
/*     */     }
/*     */     else {
/*     */       
/* 164 */       setPotionDamage(tagCompund.getInteger("potionValue"));
/*     */     } 
/*     */     
/* 167 */     if (this.potionDamage == null)
/*     */     {
/* 169 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 178 */     super.writeEntityToNBT(tagCompound);
/*     */     
/* 180 */     if (this.potionDamage != null)
/*     */     {
/* 182 */       tagCompound.setTag("Potion", (NBTBase)this.potionDamage.writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\projectile\EntityPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */