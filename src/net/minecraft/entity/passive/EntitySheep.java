/*     */ package net.minecraft.entity.passive;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIEatGrass;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySheep extends EntityAnimal {
/*  35 */   private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container()
/*     */       {
/*     */         public boolean canInteractWith(EntityPlayer playerIn)
/*     */         {
/*  39 */           return false;
/*     */         }
/*     */       },  2, 1);
/*  42 */   private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private int sheepTimer;
/*     */ 
/*     */   
/*  49 */   private final EntityAIEatGrass entityAIEatGrass = new EntityAIEatGrass((EntityLiving)this);
/*     */ 
/*     */   
/*     */   public static float[] getDyeRgb(EnumDyeColor dyeColor) {
/*  53 */     return DYE_TO_RGB.get(dyeColor);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntitySheep(World worldIn) {
/*  58 */     super(worldIn);
/*  59 */     setSize(0.9F, 1.3F);
/*  60 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  61 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  62 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/*  63 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  64 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.1D, Items.wheat, false));
/*  65 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  66 */     this.tasks.addTask(5, (EntityAIBase)this.entityAIEatGrass);
/*  67 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  68 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  69 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  70 */     this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
/*  71 */     this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  76 */     this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
/*  77 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  86 */     if (this.worldObj.isRemote)
/*     */     {
/*  88 */       this.sheepTimer = Math.max(0, this.sheepTimer - 1);
/*     */     }
/*     */     
/*  91 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  96 */     super.applyEntityAttributes();
/*  97 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  98 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 103 */     super.entityInit();
/* 104 */     this.dataWatcher.addObject(16, new Byte((byte)0));
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
/* 116 */     if (!getSheared())
/*     */     {
/* 118 */       entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, getFleeceColor().getMetadata()), 0.0F);
/*     */     }
/*     */     
/* 121 */     int i = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 123 */     for (int j = 0; j < i; j++) {
/*     */       
/* 125 */       if (isBurning()) {
/*     */         
/* 127 */         dropItem(Items.cooked_mutton, 1);
/*     */       }
/*     */       else {
/*     */         
/* 131 */         dropItem(Items.mutton, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 138 */     return Item.getItemFromBlock(Blocks.wool);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 143 */     if (id == 10) {
/*     */       
/* 145 */       this.sheepTimer = 40;
/*     */     }
/*     */     else {
/*     */       
/* 149 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeadRotationPointY(float p_70894_1_) {
/* 155 */     return (this.sheepTimer <= 0) ? 0.0F : ((this.sheepTimer >= 4 && this.sheepTimer <= 36) ? 1.0F : ((this.sheepTimer < 4) ? ((this.sheepTimer - p_70894_1_) / 4.0F) : (-((this.sheepTimer - 40) - p_70894_1_) / 4.0F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeadRotationAngleX(float p_70890_1_) {
/* 160 */     if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
/*     */       
/* 162 */       float f = ((this.sheepTimer - 4) - p_70890_1_) / 32.0F;
/* 163 */       return 0.62831855F + 0.2199115F * MathHelper.sin(f * 28.7F);
/*     */     } 
/*     */ 
/*     */     
/* 167 */     return (this.sheepTimer > 0) ? 0.62831855F : (this.rotationPitch / 57.295776F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 176 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 178 */     if (itemstack != null && itemstack.getItem() == Items.shears && !getSheared() && !isChild()) {
/*     */       
/* 180 */       if (!this.worldObj.isRemote) {
/*     */         
/* 182 */         setSheared(true);
/* 183 */         int i = 1 + this.rand.nextInt(3);
/*     */         
/* 185 */         for (int j = 0; j < i; j++) {
/*     */           
/* 187 */           EntityItem entityitem = entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, getFleeceColor().getMetadata()), 1.0F);
/* 188 */           entityitem.motionY += (this.rand.nextFloat() * 0.05F);
/* 189 */           entityitem.motionX += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/* 190 */           entityitem.motionZ += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/*     */         } 
/*     */       } 
/*     */       
/* 194 */       itemstack.damageItem(1, (EntityLivingBase)player);
/* 195 */       playSound("mob.sheep.shear", 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 198 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 206 */     super.writeEntityToNBT(tagCompound);
/* 207 */     tagCompound.setBoolean("Sheared", getSheared());
/* 208 */     tagCompound.setByte("Color", (byte)getFleeceColor().getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 216 */     super.readEntityFromNBT(tagCompund);
/* 217 */     setSheared(tagCompund.getBoolean("Sheared"));
/* 218 */     setFleeceColor(EnumDyeColor.byMetadata(tagCompund.getByte("Color")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 226 */     return "mob.sheep.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 234 */     return "mob.sheep.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 242 */     return "mob.sheep.say";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 247 */     playSound("mob.sheep.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDyeColor getFleeceColor() {
/* 255 */     return EnumDyeColor.byMetadata(this.dataWatcher.getWatchableObjectByte(16) & 0xF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFleeceColor(EnumDyeColor color) {
/* 263 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/* 264 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xF0 | color.getMetadata() & 0xF)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSheared() {
/* 272 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSheared(boolean sheared) {
/* 280 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 282 */     if (sheared) {
/*     */       
/* 284 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x10)));
/*     */     }
/*     */     else {
/*     */       
/* 288 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumDyeColor getRandomSheepColor(Random random) {
/* 297 */     int i = random.nextInt(100);
/* 298 */     return (i < 5) ? EnumDyeColor.BLACK : ((i < 10) ? EnumDyeColor.GRAY : ((i < 15) ? EnumDyeColor.SILVER : ((i < 18) ? EnumDyeColor.BROWN : ((random.nextInt(500) == 0) ? EnumDyeColor.PINK : EnumDyeColor.WHITE))));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntitySheep createChild(EntityAgeable ageable) {
/* 303 */     EntitySheep entitysheep = (EntitySheep)ageable;
/* 304 */     EntitySheep entitysheep1 = new EntitySheep(this.worldObj);
/* 305 */     entitysheep1.setFleeceColor(getDyeColorMixFromParents(this, entitysheep));
/* 306 */     return entitysheep1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eatGrassBonus() {
/* 315 */     setSheared(false);
/*     */     
/* 317 */     if (isChild())
/*     */     {
/* 319 */       addGrowth(60);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 329 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 330 */     setFleeceColor(getRandomSheepColor(this.worldObj.rand));
/* 331 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumDyeColor getDyeColorMixFromParents(EntityAnimal father, EntityAnimal mother) {
/* 339 */     int k, i = ((EntitySheep)father).getFleeceColor().getDyeDamage();
/* 340 */     int j = ((EntitySheep)mother).getFleeceColor().getDyeDamage();
/* 341 */     this.inventoryCrafting.getStackInSlot(0).setItemDamage(i);
/* 342 */     this.inventoryCrafting.getStackInSlot(1).setItemDamage(j);
/* 343 */     ItemStack itemstack = CraftingManager.getInstance().findMatchingRecipe(this.inventoryCrafting, ((EntitySheep)father).worldObj);
/*     */ 
/*     */     
/* 346 */     if (itemstack != null && itemstack.getItem() == Items.dye) {
/*     */       
/* 348 */       k = itemstack.getMetadata();
/*     */     }
/*     */     else {
/*     */       
/* 352 */       k = this.worldObj.rand.nextBoolean() ? i : j;
/*     */     } 
/*     */     
/* 355 */     return EnumDyeColor.byDyeDamage(k);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 360 */     return 0.95F * this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 365 */     DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] { 1.0F, 1.0F, 1.0F });
/* 366 */     DYE_TO_RGB.put(EnumDyeColor.ORANGE, new float[] { 0.85F, 0.5F, 0.2F });
/* 367 */     DYE_TO_RGB.put(EnumDyeColor.MAGENTA, new float[] { 0.7F, 0.3F, 0.85F });
/* 368 */     DYE_TO_RGB.put(EnumDyeColor.LIGHT_BLUE, new float[] { 0.4F, 0.6F, 0.85F });
/* 369 */     DYE_TO_RGB.put(EnumDyeColor.YELLOW, new float[] { 0.9F, 0.9F, 0.2F });
/* 370 */     DYE_TO_RGB.put(EnumDyeColor.LIME, new float[] { 0.5F, 0.8F, 0.1F });
/* 371 */     DYE_TO_RGB.put(EnumDyeColor.PINK, new float[] { 0.95F, 0.5F, 0.65F });
/* 372 */     DYE_TO_RGB.put(EnumDyeColor.GRAY, new float[] { 0.3F, 0.3F, 0.3F });
/* 373 */     DYE_TO_RGB.put(EnumDyeColor.SILVER, new float[] { 0.6F, 0.6F, 0.6F });
/* 374 */     DYE_TO_RGB.put(EnumDyeColor.CYAN, new float[] { 0.3F, 0.5F, 0.6F });
/* 375 */     DYE_TO_RGB.put(EnumDyeColor.PURPLE, new float[] { 0.5F, 0.25F, 0.7F });
/* 376 */     DYE_TO_RGB.put(EnumDyeColor.BLUE, new float[] { 0.2F, 0.3F, 0.7F });
/* 377 */     DYE_TO_RGB.put(EnumDyeColor.BROWN, new float[] { 0.4F, 0.3F, 0.2F });
/* 378 */     DYE_TO_RGB.put(EnumDyeColor.GREEN, new float[] { 0.4F, 0.5F, 0.2F });
/* 379 */     DYE_TO_RGB.put(EnumDyeColor.RED, new float[] { 0.6F, 0.2F, 0.2F });
/* 380 */     DYE_TO_RGB.put(EnumDyeColor.BLACK, new float[] { 0.1F, 0.1F, 0.1F });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntitySheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */