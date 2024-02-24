/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMagmaCube
/*     */   extends EntitySlime {
/*     */   public EntityMagmaCube(World worldIn) {
/*  14 */     super(worldIn);
/*  15 */     this.isImmuneToFire = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  20 */     super.applyEntityAttributes();
/*  21 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/*  29 */     return (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/*  37 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/*  45 */     return getSlimeSize() * 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  50 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/*  58 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/*  63 */     return EnumParticleTypes.FLAME;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntitySlime createInstance() {
/*  68 */     return new EntityMagmaCube(this.worldObj);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/*  73 */     return Items.magma_cream;
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
/*  85 */     Item item = getDropItem();
/*     */     
/*  87 */     if (item != null && getSlimeSize() > 1) {
/*     */       
/*  89 */       int i = this.rand.nextInt(4) - 2;
/*     */       
/*  91 */       if (lootingModifier > 0)
/*     */       {
/*  93 */         i += this.rand.nextInt(lootingModifier + 1);
/*     */       }
/*     */       
/*  96 */       for (int j = 0; j < i; j++)
/*     */       {
/*  98 */         dropItem(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getJumpDelay() {
/* 116 */     return super.getJumpDelay() << 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void alterSquishAmount() {
/* 121 */     this.squishAmount *= 0.9F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jump() {
/* 129 */     this.motionY = (0.42F + getSlimeSize() * 0.1F);
/* 130 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleJumpLava() {
/* 135 */     this.motionY = (0.22F + getSlimeSize() * 0.05F);
/* 136 */     this.isAirBorne = true;
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
/*     */   protected boolean canDamagePlayer() {
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getAttackStrength() {
/* 156 */     return super.getAttackStrength() + 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJumpSound() {
/* 164 */     return (getSlimeSize() > 1) ? "mob.magmacube.big" : "mob.magmacube.small";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean makesSoundOnLand() {
/* 172 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntityMagmaCube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */