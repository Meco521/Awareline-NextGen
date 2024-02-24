/*     */ package net.minecraft.entity.item;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.LockCode;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
/*  16 */   private ItemStack[] minecartContainerItems = new ItemStack[36];
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dropContentsWhenDead = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World worldIn) {
/*  26 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World worldIn, double x, double y, double z) {
/*  31 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  36 */     super.killMinecart(source);
/*     */     
/*  38 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops"))
/*     */     {
/*  40 */       InventoryHelper.dropInventoryItems(this.worldObj, this, (IInventory)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  49 */     return this.minecartContainerItems[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  57 */     if (this.minecartContainerItems[index] != null) {
/*     */       
/*  59 */       if ((this.minecartContainerItems[index]).stackSize <= count) {
/*     */         
/*  61 */         ItemStack itemstack1 = this.minecartContainerItems[index];
/*  62 */         this.minecartContainerItems[index] = null;
/*  63 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  67 */       ItemStack itemstack = this.minecartContainerItems[index].splitStack(count);
/*     */       
/*  69 */       if ((this.minecartContainerItems[index]).stackSize == 0)
/*     */       {
/*  71 */         this.minecartContainerItems[index] = null;
/*     */       }
/*     */       
/*  74 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  88 */     if (this.minecartContainerItems[index] != null) {
/*     */       
/*  90 */       ItemStack itemstack = this.minecartContainerItems[index];
/*  91 */       this.minecartContainerItems[index] = null;
/*  92 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 105 */     this.minecartContainerItems[index] = stack;
/*     */     
/* 107 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 109 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 126 */     return this.isDead ? false : ((player.getDistanceSqToEntity(this) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 150 */     return hasCustomName() ? getCustomNameTag() : "container.minecart";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 158 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void travelToDimension(int dimensionId) {
/* 166 */     this.dropContentsWhenDead = false;
/* 167 */     super.travelToDimension(dimensionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 175 */     if (this.dropContentsWhenDead)
/*     */     {
/* 177 */       InventoryHelper.dropInventoryItems(this.worldObj, this, (IInventory)this);
/*     */     }
/*     */     
/* 180 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 188 */     super.writeEntityToNBT(tagCompound);
/* 189 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 191 */     for (int i = 0; i < this.minecartContainerItems.length; i++) {
/*     */       
/* 193 */       if (this.minecartContainerItems[i] != null) {
/*     */         
/* 195 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 196 */         nbttagcompound.setByte("Slot", (byte)i);
/* 197 */         this.minecartContainerItems[i].writeToNBT(nbttagcompound);
/* 198 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     tagCompound.setTag("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 210 */     super.readEntityFromNBT(tagCompund);
/* 211 */     NBTTagList nbttaglist = tagCompund.getTagList("Items", 10);
/* 212 */     this.minecartContainerItems = new ItemStack[getSizeInventory()];
/*     */     
/* 214 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 216 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 217 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 219 */       if (j >= 0 && j < this.minecartContainerItems.length)
/*     */       {
/* 221 */         this.minecartContainerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 231 */     if (!this.worldObj.isRemote)
/*     */     {
/* 233 */       playerIn.displayGUIChest((IInventory)this);
/*     */     }
/*     */     
/* 236 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyDrag() {
/* 241 */     int i = 15 - Container.calcRedstoneFromInventory((IInventory)this);
/* 242 */     float f = 0.98F + i * 0.001F;
/* 243 */     this.motionX *= f;
/* 244 */     this.motionY *= 0.0D;
/* 245 */     this.motionZ *= f;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 250 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 259 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLockCode(LockCode code) {}
/*     */ 
/*     */   
/*     */   public LockCode getLockCode() {
/* 273 */     return LockCode.EMPTY_CODE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 278 */     for (int i = 0; i < this.minecartContainerItems.length; i++)
/*     */     {
/* 280 */       this.minecartContainerItems[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityMinecartContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */