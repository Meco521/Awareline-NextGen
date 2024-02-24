/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityCow extends EntityAnimal {
/*     */   public EntityCow(World worldIn) {
/*  19 */     super(worldIn);
/*  20 */     setSize(0.9F, 1.3F);
/*  21 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  22 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  23 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 2.0D));
/*  24 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  25 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25D, Items.wheat, false));
/*  26 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.25D));
/*  27 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  28 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  29 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  34 */     super.applyEntityAttributes();
/*  35 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  36 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  44 */     return "mob.cow.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  52 */     return "mob.cow.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  60 */     return "mob.cow.hurt";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  65 */     playSound("mob.cow.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  73 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/*  78 */     return Items.leather;
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
/*  90 */     int i = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/*  92 */     for (int j = 0; j < i; j++)
/*     */     {
/*  94 */       dropItem(Items.leather, 1);
/*     */     }
/*     */     
/*  97 */     i = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + lootingModifier);
/*     */     
/*  99 */     for (int k = 0; k < i; k++) {
/*     */       
/* 101 */       if (isBurning()) {
/*     */         
/* 103 */         dropItem(Items.cooked_beef, 1);
/*     */       }
/*     */       else {
/*     */         
/* 107 */         dropItem(Items.beef, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 117 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 119 */     if (itemstack != null && itemstack.getItem() == Items.bucket && !player.capabilities.isCreativeMode && !isChild()) {
/*     */       
/* 121 */       if (itemstack.stackSize-- == 1) {
/*     */         
/* 123 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.milk_bucket));
/*     */       }
/* 125 */       else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
/*     */         
/* 127 */         player.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
/*     */       } 
/*     */       
/* 130 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityCow createChild(EntityAgeable ageable) {
/* 140 */     return new EntityCow(this.worldObj);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 145 */     return this.height;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */