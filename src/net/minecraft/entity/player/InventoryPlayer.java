/*     */ package net.minecraft.entity.player;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ReportedException;
/*     */ 
/*     */ 
/*     */ public class InventoryPlayer
/*     */   implements IInventory
/*     */ {
/*  25 */   public ItemStack[] mainInventory = new ItemStack[36];
/*     */ 
/*     */   
/*  28 */   public ItemStack[] armorInventory = new ItemStack[4];
/*     */ 
/*     */ 
/*     */   
/*     */   public int currentItem;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayer player;
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack itemStack;
/*     */ 
/*     */   
/*     */   public boolean inventoryChanged;
/*     */ 
/*     */ 
/*     */   
/*     */   public InventoryPlayer(EntityPlayer playerIn) {
/*  48 */     this.player = playerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentItem() {
/*  56 */     return (this.currentItem < 9 && this.currentItem >= 0) ? this.mainInventory[this.currentItem] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHotbarSize() {
/*  64 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getInventorySlotContainItem(Item itemIn) {
/*  69 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*     */       
/*  71 */       if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == itemIn)
/*     */       {
/*  73 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getInventorySlotContainItemAndDamage(Item itemIn, int metadataIn) {
/*  82 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*     */       
/*  84 */       if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == itemIn && this.mainInventory[i].getMetadata() == metadataIn)
/*     */       {
/*  86 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  90 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int storeItemStack(ItemStack itemStackIn) {
/*  98 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*     */       
/* 100 */       if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == itemStackIn.getItem() && this.mainInventory[i].isStackable() && (this.mainInventory[i]).stackSize < this.mainInventory[i].getMaxStackSize() && (this.mainInventory[i]).stackSize < getInventoryStackLimit() && (!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getMetadata() == itemStackIn.getMetadata()) && ItemStack.areItemStackTagsEqual(this.mainInventory[i], itemStackIn))
/*     */       {
/* 102 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstEmptyStack() {
/* 114 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*     */       
/* 116 */       if (this.mainInventory[i] == null)
/*     */       {
/* 118 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 122 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentItem(Item itemIn, int metadataIn, boolean isMetaSpecific, boolean p_146030_4_) {
/* 127 */     ItemStack itemstack = getCurrentItem();
/* 128 */     int i = isMetaSpecific ? getInventorySlotContainItemAndDamage(itemIn, metadataIn) : getInventorySlotContainItem(itemIn);
/*     */     
/* 130 */     if (i >= 0 && i < 9) {
/*     */       
/* 132 */       this.currentItem = i;
/*     */     }
/* 134 */     else if (p_146030_4_ && itemIn != null) {
/*     */       
/* 136 */       int j = getFirstEmptyStack();
/*     */       
/* 138 */       if (j >= 0 && j < 9)
/*     */       {
/* 140 */         this.currentItem = j;
/*     */       }
/*     */       
/* 143 */       if (itemstack == null || !itemstack.isItemEnchantable() || getInventorySlotContainItemAndDamage(itemstack.getItem(), itemstack.getItemDamage()) != this.currentItem) {
/*     */         
/* 145 */         int l, k = getInventorySlotContainItemAndDamage(itemIn, metadataIn);
/*     */ 
/*     */         
/* 148 */         if (k >= 0) {
/*     */           
/* 150 */           l = (this.mainInventory[k]).stackSize;
/* 151 */           this.mainInventory[k] = this.mainInventory[this.currentItem];
/*     */         }
/*     */         else {
/*     */           
/* 155 */           l = 1;
/*     */         } 
/*     */         
/* 158 */         this.mainInventory[this.currentItem] = new ItemStack(itemIn, l, metadataIn);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeCurrentItem(int direction) {
/* 171 */     if (direction > 0)
/*     */     {
/* 173 */       direction = 1;
/*     */     }
/*     */     
/* 176 */     if (direction < 0)
/*     */     {
/* 178 */       direction = -1;
/*     */     }
/*     */     
/* 181 */     for (this.currentItem -= direction; this.currentItem < 0; this.currentItem += 9);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     while (this.currentItem >= 9)
/*     */     {
/* 188 */       this.currentItem -= 9;
/*     */     }
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
/*     */   
/*     */   public int clearMatchingItems(Item itemIn, int metadataIn, int removeCount, NBTTagCompound itemNBT) {
/* 202 */     int i = 0;
/*     */     
/* 204 */     for (int j = 0; j < this.mainInventory.length; j++) {
/*     */       
/* 206 */       ItemStack itemstack = this.mainInventory[j];
/*     */       
/* 208 */       if (itemstack != null && (itemIn == null || itemstack.getItem() == itemIn) && (metadataIn <= -1 || itemstack.getMetadata() == metadataIn) && (itemNBT == null || NBTUtil.func_181123_a((NBTBase)itemNBT, (NBTBase)itemstack.getTagCompound(), true))) {
/*     */         
/* 210 */         int k = (removeCount <= 0) ? itemstack.stackSize : Math.min(removeCount - i, itemstack.stackSize);
/* 211 */         i += k;
/*     */         
/* 213 */         if (removeCount != 0) {
/*     */           
/* 215 */           (this.mainInventory[j]).stackSize -= k;
/*     */           
/* 217 */           if ((this.mainInventory[j]).stackSize == 0)
/*     */           {
/* 219 */             this.mainInventory[j] = null;
/*     */           }
/*     */           
/* 222 */           if (removeCount > 0 && i >= removeCount)
/*     */           {
/* 224 */             return i;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     for (int l = 0; l < this.armorInventory.length; l++) {
/*     */       
/* 232 */       ItemStack itemstack1 = this.armorInventory[l];
/*     */       
/* 234 */       if (itemstack1 != null && (itemIn == null || itemstack1.getItem() == itemIn) && (metadataIn <= -1 || itemstack1.getMetadata() == metadataIn) && (itemNBT == null || NBTUtil.func_181123_a((NBTBase)itemNBT, (NBTBase)itemstack1.getTagCompound(), false))) {
/*     */         
/* 236 */         int j1 = (removeCount <= 0) ? itemstack1.stackSize : Math.min(removeCount - i, itemstack1.stackSize);
/* 237 */         i += j1;
/*     */         
/* 239 */         if (removeCount != 0) {
/*     */           
/* 241 */           (this.armorInventory[l]).stackSize -= j1;
/*     */           
/* 243 */           if ((this.armorInventory[l]).stackSize == 0)
/*     */           {
/* 245 */             this.armorInventory[l] = null;
/*     */           }
/*     */           
/* 248 */           if (removeCount > 0 && i >= removeCount)
/*     */           {
/* 250 */             return i;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     if (this.itemStack != null) {
/*     */       
/* 258 */       if (itemIn != null && this.itemStack.getItem() != itemIn)
/*     */       {
/* 260 */         return i;
/*     */       }
/*     */       
/* 263 */       if (metadataIn > -1 && this.itemStack.getMetadata() != metadataIn)
/*     */       {
/* 265 */         return i;
/*     */       }
/*     */       
/* 268 */       if (itemNBT != null && !NBTUtil.func_181123_a((NBTBase)itemNBT, (NBTBase)this.itemStack.getTagCompound(), false))
/*     */       {
/* 270 */         return i;
/*     */       }
/*     */       
/* 273 */       int i1 = (removeCount <= 0) ? this.itemStack.stackSize : Math.min(removeCount - i, this.itemStack.stackSize);
/* 274 */       i += i1;
/*     */       
/* 276 */       if (removeCount != 0) {
/*     */         
/* 278 */         this.itemStack.stackSize -= i1;
/*     */         
/* 280 */         if (this.itemStack.stackSize == 0)
/*     */         {
/* 282 */           this.itemStack = null;
/*     */         }
/*     */         
/* 285 */         if (removeCount > 0 && i >= removeCount)
/*     */         {
/* 287 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int storePartialItemStack(ItemStack itemStackIn) {
/* 301 */     Item item = itemStackIn.getItem();
/* 302 */     int i = itemStackIn.stackSize;
/* 303 */     int j = storeItemStack(itemStackIn);
/*     */     
/* 305 */     if (j < 0)
/*     */     {
/* 307 */       j = getFirstEmptyStack();
/*     */     }
/*     */     
/* 310 */     if (j < 0)
/*     */     {
/* 312 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 316 */     if (this.mainInventory[j] == null) {
/*     */       
/* 318 */       this.mainInventory[j] = new ItemStack(item, 0, itemStackIn.getMetadata());
/*     */       
/* 320 */       if (itemStackIn.hasTagCompound())
/*     */       {
/* 322 */         this.mainInventory[j].setTagCompound((NBTTagCompound)itemStackIn.getTagCompound().copy());
/*     */       }
/*     */     } 
/*     */     
/* 326 */     int k = i;
/*     */     
/* 328 */     if (i > this.mainInventory[j].getMaxStackSize() - (this.mainInventory[j]).stackSize)
/*     */     {
/* 330 */       k = this.mainInventory[j].getMaxStackSize() - (this.mainInventory[j]).stackSize;
/*     */     }
/*     */     
/* 333 */     if (k > getInventoryStackLimit() - (this.mainInventory[j]).stackSize)
/*     */     {
/* 335 */       k = getInventoryStackLimit() - (this.mainInventory[j]).stackSize;
/*     */     }
/*     */     
/* 338 */     if (k == 0)
/*     */     {
/* 340 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 344 */     i -= k;
/* 345 */     (this.mainInventory[j]).stackSize += k;
/* 346 */     (this.mainInventory[j]).animationsToGo = 5;
/* 347 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementAnimations() {
/* 358 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*     */       
/* 360 */       if (this.mainInventory[i] != null)
/*     */       {
/* 362 */         this.mainInventory[i].updateAnimation(this.player.worldObj, (Entity)this.player, i, (this.currentItem == i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean consumeInventoryItem(Item itemIn) {
/* 372 */     int i = getInventorySlotContainItem(itemIn);
/*     */     
/* 374 */     if (i < 0)
/*     */     {
/* 376 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 380 */     if (--(this.mainInventory[i]).stackSize <= 0)
/*     */     {
/* 382 */       this.mainInventory[i] = null;
/*     */     }
/*     */     
/* 385 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItem(Item itemIn) {
/* 394 */     int i = getInventorySlotContainItem(itemIn);
/* 395 */     return (i >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItemStackToInventory(final ItemStack itemStackIn) {
/* 403 */     if (itemStackIn != null && itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
/*     */       try {
/*     */         int i;
/*     */         
/* 407 */         if (itemStackIn.isItemDamaged()) {
/*     */           
/* 409 */           int j = getFirstEmptyStack();
/*     */           
/* 411 */           if (j >= 0) {
/*     */             
/* 413 */             this.mainInventory[j] = ItemStack.copyItemStack(itemStackIn);
/* 414 */             (this.mainInventory[j]).animationsToGo = 5;
/* 415 */             itemStackIn.stackSize = 0;
/* 416 */             return true;
/*     */           } 
/* 418 */           if (this.player.capabilities.isCreativeMode) {
/*     */             
/* 420 */             itemStackIn.stackSize = 0;
/* 421 */             return true;
/*     */           } 
/*     */ 
/*     */           
/* 425 */           return false;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         do {
/* 434 */           i = itemStackIn.stackSize;
/* 435 */           itemStackIn.stackSize = storePartialItemStack(itemStackIn);
/*     */         }
/* 437 */         while (itemStackIn.stackSize > 0 && itemStackIn.stackSize < i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 443 */         if (itemStackIn.stackSize == i && this.player.capabilities.isCreativeMode) {
/*     */           
/* 445 */           itemStackIn.stackSize = 0;
/* 446 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 450 */         return (itemStackIn.stackSize < i);
/*     */ 
/*     */       
/*     */       }
/* 454 */       catch (Throwable throwable) {
/*     */         
/* 456 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
/* 457 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
/* 458 */         crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(itemStackIn.getItem())));
/* 459 */         crashreportcategory.addCrashSection("Item data", Integer.valueOf(itemStackIn.getMetadata()));
/* 460 */         crashreportcategory.addCrashSectionCallable("Item name", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 463 */                 return itemStackIn.getDisplayName();
/*     */               }
/*     */             });
/* 466 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 471 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 480 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 482 */     if (index >= this.mainInventory.length) {
/*     */       
/* 484 */       aitemstack = this.armorInventory;
/* 485 */       index -= this.mainInventory.length;
/*     */     } 
/*     */     
/* 488 */     if (aitemstack[index] != null) {
/*     */       
/* 490 */       if ((aitemstack[index]).stackSize <= count) {
/*     */         
/* 492 */         ItemStack itemstack1 = aitemstack[index];
/* 493 */         aitemstack[index] = null;
/* 494 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/* 498 */       ItemStack itemstack = aitemstack[index].splitStack(count);
/*     */       
/* 500 */       if ((aitemstack[index]).stackSize == 0)
/*     */       {
/* 502 */         aitemstack[index] = null;
/*     */       }
/*     */       
/* 505 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 510 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 519 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 521 */     if (index >= this.mainInventory.length) {
/*     */       
/* 523 */       aitemstack = this.armorInventory;
/* 524 */       index -= this.mainInventory.length;
/*     */     } 
/*     */     
/* 527 */     if (aitemstack[index] != null) {
/*     */       
/* 529 */       ItemStack itemstack = aitemstack[index];
/* 530 */       aitemstack[index] = null;
/* 531 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 535 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 544 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 546 */     if (index >= aitemstack.length) {
/*     */       
/* 548 */       index -= aitemstack.length;
/* 549 */       aitemstack = this.armorInventory;
/*     */     } 
/*     */     
/* 552 */     aitemstack[index] = stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrVsBlock(Block blockIn) {
/* 557 */     float f = 1.0F;
/*     */     
/* 559 */     if (this.mainInventory[this.currentItem] != null)
/*     */     {
/* 561 */       f *= this.mainInventory[this.currentItem].getStrVsBlock(blockIn);
/*     */     }
/*     */     
/* 564 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList writeToNBT(NBTTagList nbtTagListIn) {
/* 575 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*     */       
/* 577 */       if (this.mainInventory[i] != null) {
/*     */         
/* 579 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 580 */         nbttagcompound.setByte("Slot", (byte)i);
/* 581 */         this.mainInventory[i].writeToNBT(nbttagcompound);
/* 582 */         nbtTagListIn.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 586 */     for (int j = 0; j < this.armorInventory.length; j++) {
/*     */       
/* 588 */       if (this.armorInventory[j] != null) {
/*     */         
/* 590 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 591 */         nbttagcompound1.setByte("Slot", (byte)(j + 100));
/* 592 */         this.armorInventory[j].writeToNBT(nbttagcompound1);
/* 593 */         nbtTagListIn.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 597 */     return nbtTagListIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagList nbtTagListIn) {
/* 607 */     this.mainInventory = new ItemStack[36];
/* 608 */     this.armorInventory = new ItemStack[4];
/*     */     
/* 610 */     for (int i = 0; i < nbtTagListIn.tagCount(); i++) {
/*     */       
/* 612 */       NBTTagCompound nbttagcompound = nbtTagListIn.getCompoundTagAt(i);
/* 613 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/* 614 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       
/* 616 */       if (itemstack != null) {
/*     */         
/* 618 */         if (j >= 0 && j < this.mainInventory.length)
/*     */         {
/* 620 */           this.mainInventory[j] = itemstack;
/*     */         }
/*     */         
/* 623 */         if (j >= 100 && j < this.armorInventory.length + 100)
/*     */         {
/* 625 */           this.armorInventory[j - 100] = itemstack;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 636 */     return this.mainInventory.length + 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 644 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 646 */     if (index >= aitemstack.length) {
/*     */       
/* 648 */       index -= aitemstack.length;
/* 649 */       aitemstack = this.armorInventory;
/*     */     } 
/*     */     
/* 652 */     return aitemstack[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 660 */     return "container.inventory";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 668 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 676 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 684 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHeldItemHarvest(Block blockIn) {
/* 689 */     if (blockIn.getMaterial().isToolNotRequired())
/*     */     {
/* 691 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 695 */     ItemStack itemstack = getStackInSlot(this.currentItem);
/* 696 */     return (itemstack != null) ? itemstack.canHarvestBlock(blockIn) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack armorItemInSlot(int slotIn) {
/* 707 */     return this.armorInventory[slotIn];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 715 */     int i = 0;
/*     */     
/* 717 */     for (int j = 0; j < this.armorInventory.length; j++) {
/*     */       
/* 719 */       if (this.armorInventory[j] != null && this.armorInventory[j].getItem() instanceof ItemArmor) {
/*     */         
/* 721 */         int k = ((ItemArmor)this.armorInventory[j].getItem()).damageReduceAmount;
/* 722 */         i += k;
/*     */       } 
/*     */     } 
/*     */     
/* 726 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(float damage) {
/* 734 */     damage /= 4.0F;
/*     */     
/* 736 */     if (damage < 1.0F)
/*     */     {
/* 738 */       damage = 1.0F;
/*     */     }
/*     */     
/* 741 */     for (int i = 0; i < this.armorInventory.length; i++) {
/*     */       
/* 743 */       if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor) {
/*     */         
/* 745 */         this.armorInventory[i].damageItem((int)damage, this.player);
/*     */         
/* 747 */         if ((this.armorInventory[i]).stackSize == 0)
/*     */         {
/* 749 */           this.armorInventory[i] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropAllItems() {
/* 760 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*     */       
/* 762 */       if (this.mainInventory[i] != null) {
/*     */         
/* 764 */         this.player.dropItem(this.mainInventory[i], true, false);
/* 765 */         this.mainInventory[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 769 */     for (int j = 0; j < this.armorInventory.length; j++) {
/*     */       
/* 771 */       if (this.armorInventory[j] != null) {
/*     */         
/* 773 */         this.player.dropItem(this.armorInventory[j], true, false);
/* 774 */         this.armorInventory[j] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 785 */     this.inventoryChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemStack(ItemStack itemStackIn) {
/* 793 */     this.itemStack = itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/* 801 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 809 */     return this.player.isDead ? false : ((player.getDistanceSqToEntity((Entity)this.player) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItemStack(ItemStack itemStackIn) {
/* 817 */     for (int i = 0; i < this.armorInventory.length; i++) {
/*     */       
/* 819 */       if (this.armorInventory[i] != null && this.armorInventory[i].isItemEqual(itemStackIn))
/*     */       {
/* 821 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 825 */     for (int j = 0; j < this.mainInventory.length; j++) {
/*     */       
/* 827 */       if (this.mainInventory[j] != null && this.mainInventory[j].isItemEqual(itemStackIn))
/*     */       {
/* 829 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 833 */     return false;
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
/* 849 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyInventory(InventoryPlayer playerInventory) {
/* 857 */     for (int i = 0; i < this.mainInventory.length; i++)
/*     */     {
/* 859 */       this.mainInventory[i] = ItemStack.copyItemStack(playerInventory.mainInventory[i]);
/*     */     }
/*     */     
/* 862 */     for (int j = 0; j < this.armorInventory.length; j++)
/*     */     {
/* 864 */       this.armorInventory[j] = ItemStack.copyItemStack(playerInventory.armorInventory[j]);
/*     */     }
/*     */     
/* 867 */     this.currentItem = playerInventory.currentItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 872 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 881 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 886 */     for (int i = 0; i < this.mainInventory.length; i++)
/*     */     {
/* 888 */       this.mainInventory[i] = null;
/*     */     }
/*     */     
/* 891 */     for (int j = 0; j < this.armorInventory.length; j++)
/*     */     {
/* 893 */       this.armorInventory[j] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\player\InventoryPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */