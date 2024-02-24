/*     */ package net.minecraft.tileentity;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerDispenser;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ public class TileEntityDispenser extends TileEntityLockable {
/*  14 */   private static final Random RNG = new Random();
/*  15 */   private ItemStack[] stacks = new ItemStack[9];
/*     */ 
/*     */   
/*     */   protected String customName;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  23 */     return 9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  31 */     return this.stacks[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  39 */     if (this.stacks[index] != null) {
/*     */       
/*  41 */       if ((this.stacks[index]).stackSize <= count) {
/*     */         
/*  43 */         ItemStack itemstack1 = this.stacks[index];
/*  44 */         this.stacks[index] = null;
/*  45 */         markDirty();
/*  46 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  50 */       ItemStack itemstack = this.stacks[index].splitStack(count);
/*     */       
/*  52 */       if ((this.stacks[index]).stackSize == 0)
/*     */       {
/*  54 */         this.stacks[index] = null;
/*     */       }
/*     */       
/*  57 */       markDirty();
/*  58 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  72 */     if (this.stacks[index] != null) {
/*     */       
/*  74 */       ItemStack itemstack = this.stacks[index];
/*  75 */       this.stacks[index] = null;
/*  76 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDispenseSlot() {
/*  86 */     int i = -1;
/*  87 */     int j = 1;
/*     */     
/*  89 */     for (int k = 0; k < this.stacks.length; k++) {
/*     */       
/*  91 */       if (this.stacks[k] != null && RNG.nextInt(j++) == 0)
/*     */       {
/*  93 */         i = k;
/*     */       }
/*     */     } 
/*     */     
/*  97 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 105 */     this.stacks[index] = stack;
/*     */     
/* 107 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 109 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 112 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addItemStack(ItemStack stack) {
/* 121 */     for (int i = 0; i < this.stacks.length; i++) {
/*     */       
/* 123 */       if (this.stacks[i] == null || this.stacks[i].getItem() == null) {
/*     */         
/* 125 */         setInventorySlotContents(i, stack);
/* 126 */         return i;
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 138 */     return hasCustomName() ? this.customName : "container.dispenser";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String customName) {
/* 143 */     this.customName = customName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 151 */     return (this.customName != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 156 */     super.readFromNBT(compound);
/* 157 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 158 */     this.stacks = new ItemStack[getSizeInventory()];
/*     */     
/* 160 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 162 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 163 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 165 */       if (j >= 0 && j < this.stacks.length)
/*     */       {
/* 167 */         this.stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 171 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 173 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 179 */     super.writeToNBT(compound);
/* 180 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 182 */     for (int i = 0; i < this.stacks.length; i++) {
/*     */       
/* 184 */       if (this.stacks[i] != null) {
/*     */         
/* 186 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 187 */         nbttagcompound.setByte("Slot", (byte)i);
/* 188 */         this.stacks[i].writeToNBT(nbttagcompound);
/* 189 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 195 */     if (hasCustomName())
/*     */     {
/* 197 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 206 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 214 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 230 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 235 */     return "minecraft:dispenser";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 240 */     return (Container)new ContainerDispenser((IInventory)playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 254 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 259 */     for (int i = 0; i < this.stacks.length; i++)
/*     */     {
/* 261 */       this.stacks[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */