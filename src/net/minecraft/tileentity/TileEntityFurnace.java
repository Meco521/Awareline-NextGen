/*     */ package net.minecraft.tileentity;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
/*  21 */   private static final int[] slotsTop = new int[] { 0 };
/*  22 */   private static final int[] slotsBottom = new int[] { 2, 1 };
/*  23 */   private static final int[] slotsSides = new int[] { 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private ItemStack[] furnaceItemStacks = new ItemStack[3];
/*     */ 
/*     */   
/*     */   private int furnaceBurnTime;
/*     */ 
/*     */   
/*     */   private int currentItemBurnTime;
/*     */ 
/*     */   
/*     */   private int cookTime;
/*     */ 
/*     */   
/*     */   private int totalCookTime;
/*     */   
/*     */   private String furnaceCustomName;
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  46 */     return this.furnaceItemStacks.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  54 */     return this.furnaceItemStacks[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  62 */     if (this.furnaceItemStacks[index] != null) {
/*     */       
/*  64 */       if ((this.furnaceItemStacks[index]).stackSize <= count) {
/*     */         
/*  66 */         ItemStack itemstack1 = this.furnaceItemStacks[index];
/*  67 */         this.furnaceItemStacks[index] = null;
/*  68 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  72 */       ItemStack itemstack = this.furnaceItemStacks[index].splitStack(count);
/*     */       
/*  74 */       if ((this.furnaceItemStacks[index]).stackSize == 0)
/*     */       {
/*  76 */         this.furnaceItemStacks[index] = null;
/*     */       }
/*     */       
/*  79 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  93 */     if (this.furnaceItemStacks[index] != null) {
/*     */       
/*  95 */       ItemStack itemstack = this.furnaceItemStacks[index];
/*  96 */       this.furnaceItemStacks[index] = null;
/*  97 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 110 */     boolean flag = (stack != null && stack.isItemEqual(this.furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]));
/* 111 */     this.furnaceItemStacks[index] = stack;
/*     */     
/* 113 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 115 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 118 */     if (index == 0 && !flag) {
/*     */       
/* 120 */       this.totalCookTime = getCookTime(stack);
/* 121 */       this.cookTime = 0;
/* 122 */       markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 131 */     return hasCustomName() ? this.furnaceCustomName : "container.furnace";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 139 */     return (this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomInventoryName(String p_145951_1_) {
/* 144 */     this.furnaceCustomName = p_145951_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 149 */     super.readFromNBT(compound);
/* 150 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 151 */     this.furnaceItemStacks = new ItemStack[getSizeInventory()];
/*     */     
/* 153 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 155 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 156 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/* 158 */       if (j >= 0 && j < this.furnaceItemStacks.length)
/*     */       {
/* 160 */         this.furnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 164 */     this.furnaceBurnTime = compound.getShort("BurnTime");
/* 165 */     this.cookTime = compound.getShort("CookTime");
/* 166 */     this.totalCookTime = compound.getShort("CookTimeTotal");
/* 167 */     this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
/*     */     
/* 169 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 171 */       this.furnaceCustomName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 177 */     super.writeToNBT(compound);
/* 178 */     compound.setShort("BurnTime", (short)this.furnaceBurnTime);
/* 179 */     compound.setShort("CookTime", (short)this.cookTime);
/* 180 */     compound.setShort("CookTimeTotal", (short)this.totalCookTime);
/* 181 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 183 */     for (int i = 0; i < this.furnaceItemStacks.length; i++) {
/*     */       
/* 185 */       if (this.furnaceItemStacks[i] != null) {
/*     */         
/* 187 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 188 */         nbttagcompound.setByte("Slot", (byte)i);
/* 189 */         this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
/* 190 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 196 */     if (hasCustomName())
/*     */     {
/* 198 */       compound.setString("CustomName", this.furnaceCustomName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 207 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 215 */     return (this.furnaceBurnTime > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBurning(IInventory p_174903_0_) {
/* 220 */     return (p_174903_0_.getField(0) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 228 */     boolean flag = isBurning();
/* 229 */     boolean flag1 = false;
/*     */     
/* 231 */     if (isBurning())
/*     */     {
/* 233 */       this.furnaceBurnTime--;
/*     */     }
/*     */     
/* 236 */     if (!this.worldObj.isRemote) {
/*     */       
/* 238 */       if (isBurning() || (this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null)) {
/*     */         
/* 240 */         if (!isBurning() && canSmelt()) {
/*     */           
/* 242 */           this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
/*     */           
/* 244 */           if (isBurning()) {
/*     */             
/* 246 */             flag1 = true;
/*     */             
/* 248 */             if (this.furnaceItemStacks[1] != null) {
/*     */               
/* 250 */               (this.furnaceItemStacks[1]).stackSize--;
/*     */               
/* 252 */               if ((this.furnaceItemStacks[1]).stackSize == 0) {
/*     */                 
/* 254 */                 Item item = this.furnaceItemStacks[1].getItem().getContainerItem();
/* 255 */                 this.furnaceItemStacks[1] = (item != null) ? new ItemStack(item) : null;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 261 */         if (isBurning() && canSmelt()) {
/*     */           
/* 263 */           this.cookTime++;
/*     */           
/* 265 */           if (this.cookTime == this.totalCookTime)
/*     */           {
/* 267 */             this.cookTime = 0;
/* 268 */             this.totalCookTime = getCookTime(this.furnaceItemStacks[0]);
/* 269 */             smeltItem();
/* 270 */             flag1 = true;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 275 */           this.cookTime = 0;
/*     */         }
/*     */       
/* 278 */       } else if (!isBurning() && this.cookTime > 0) {
/*     */         
/* 280 */         this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
/*     */       } 
/*     */       
/* 283 */       if (flag != isBurning()) {
/*     */         
/* 285 */         flag1 = true;
/* 286 */         BlockFurnace.setState(isBurning(), this.worldObj, this.pos);
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     if (flag1)
/*     */     {
/* 292 */       markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCookTime(ItemStack stack) {
/* 298 */     return 200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSmelt() {
/* 306 */     if (this.furnaceItemStacks[0] == null)
/*     */     {
/* 308 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 312 */     ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
/* 313 */     return (itemstack == null) ? false : ((this.furnaceItemStacks[2] == null) ? true : (!this.furnaceItemStacks[2].isItemEqual(itemstack) ? false : (((this.furnaceItemStacks[2]).stackSize < getInventoryStackLimit() && (this.furnaceItemStacks[2]).stackSize < this.furnaceItemStacks[2].getMaxStackSize()) ? true : (((this.furnaceItemStacks[2]).stackSize < itemstack.getMaxStackSize())))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void smeltItem() {
/* 322 */     if (canSmelt()) {
/*     */       
/* 324 */       ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
/*     */       
/* 326 */       if (this.furnaceItemStacks[2] == null) {
/*     */         
/* 328 */         this.furnaceItemStacks[2] = itemstack.copy();
/*     */       }
/* 330 */       else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem()) {
/*     */         
/* 332 */         (this.furnaceItemStacks[2]).stackSize++;
/*     */       } 
/*     */       
/* 335 */       if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.bucket)
/*     */       {
/* 337 */         this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
/*     */       }
/*     */       
/* 340 */       (this.furnaceItemStacks[0]).stackSize--;
/*     */       
/* 342 */       if ((this.furnaceItemStacks[0]).stackSize <= 0)
/*     */       {
/* 344 */         this.furnaceItemStacks[0] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getItemBurnTime(ItemStack p_145952_0_) {
/* 355 */     if (p_145952_0_ == null)
/*     */     {
/* 357 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 361 */     Item item = p_145952_0_.getItem();
/*     */     
/* 363 */     if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
/*     */       
/* 365 */       Block block = Block.getBlockFromItem(item);
/*     */       
/* 367 */       if (block == Blocks.wooden_slab)
/*     */       {
/* 369 */         return 150;
/*     */       }
/*     */       
/* 372 */       if (block.getMaterial() == Material.wood)
/*     */       {
/* 374 */         return 300;
/*     */       }
/*     */       
/* 377 */       if (block == Blocks.coal_block)
/*     */       {
/* 379 */         return 16000;
/*     */       }
/*     */     } 
/*     */     
/* 383 */     return (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) ? 200 : ((item == Items.stick) ? 100 : ((item == Items.coal) ? 1600 : ((item == Items.lava_bucket) ? 20000 : ((item == Item.getItemFromBlock(Blocks.sapling)) ? 100 : ((item == Items.blaze_rod) ? 2400 : 0)))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isItemFuel(ItemStack p_145954_0_) {
/* 389 */     return (getItemBurnTime(p_145954_0_) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 397 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 413 */     return (index == 2) ? false : ((index != 1) ? true : ((isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack))));
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 418 */     return (side == EnumFacing.DOWN) ? slotsBottom : ((side == EnumFacing.UP) ? slotsTop : slotsSides);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 427 */     return isItemValidForSlot(index, itemStackIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 436 */     if (direction == EnumFacing.DOWN && index == 1) {
/*     */       
/* 438 */       Item item = stack.getItem();
/*     */       
/* 440 */       if (item != Items.water_bucket && item != Items.bucket)
/*     */       {
/* 442 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 446 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 451 */     return "minecraft:furnace";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 456 */     return (Container)new ContainerFurnace(playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 461 */     switch (id) {
/*     */       
/*     */       case 0:
/* 464 */         return this.furnaceBurnTime;
/*     */       
/*     */       case 1:
/* 467 */         return this.currentItemBurnTime;
/*     */       
/*     */       case 2:
/* 470 */         return this.cookTime;
/*     */       
/*     */       case 3:
/* 473 */         return this.totalCookTime;
/*     */     } 
/*     */     
/* 476 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 482 */     switch (id) {
/*     */       
/*     */       case 0:
/* 485 */         this.furnaceBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 489 */         this.currentItemBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 2:
/* 493 */         this.cookTime = value;
/*     */         break;
/*     */       
/*     */       case 3:
/* 497 */         this.totalCookTime = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFieldCount() {
/* 503 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 508 */     for (int i = 0; i < this.furnaceItemStacks.length; i++)
/*     */     {
/* 510 */       this.furnaceItemStacks[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */