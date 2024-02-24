/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public abstract class Container
/*     */ {
/*  17 */   public List<ItemStack> inventoryItemStacks = Lists.newArrayList();
/*  18 */   public List<Slot> inventorySlots = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public int windowId;
/*     */   
/*     */   public short transactionID;
/*     */   
/*  25 */   private int dragMode = -1;
/*     */   
/*     */   private int dragEvent;
/*     */   
/*  29 */   private final Set<Slot> dragSlots = Sets.newHashSet();
/*  30 */   protected List<ICrafting> crafters = Lists.newArrayList();
/*  31 */   private final Set<EntityPlayer> playerList = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Slot addSlotToContainer(Slot slotIn) {
/*  38 */     slotIn.slotNumber = this.inventorySlots.size();
/*  39 */     this.inventorySlots.add(slotIn);
/*  40 */     this.inventoryItemStacks.add((ItemStack)null);
/*  41 */     return slotIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  46 */     if (this.crafters.contains(listener))
/*     */     {
/*  48 */       throw new IllegalArgumentException("Listener already listening");
/*     */     }
/*     */ 
/*     */     
/*  52 */     this.crafters.add(listener);
/*  53 */     listener.updateCraftingInventory(this, getInventory());
/*  54 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCraftingFromCrafters(ICrafting listeners) {
/*  63 */     this.crafters.remove(listeners);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getInventory() {
/*  68 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  70 */     for (int i = 0; i < this.inventorySlots.size(); i++)
/*     */     {
/*  72 */       list.add(((Slot)this.inventorySlots.get(i)).getStack());
/*     */     }
/*     */     
/*  75 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  83 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*     */       
/*  85 */       ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
/*  86 */       ItemStack itemstack1 = this.inventoryItemStacks.get(i);
/*     */       
/*  88 */       if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/*     */         
/*  90 */         itemstack1 = (itemstack == null) ? null : itemstack.copy();
/*  91 */         this.inventoryItemStacks.set(i, itemstack1);
/*     */         
/*  93 */         for (int j = 0; j < this.crafters.size(); j++)
/*     */         {
/*  95 */           ((ICrafting)this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enchantItem(EntityPlayer playerIn, int id) {
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Slot getSlotFromInventory(IInventory inv, int slotIn) {
/* 111 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*     */       
/* 113 */       Slot slot = this.inventorySlots.get(i);
/*     */       
/* 115 */       if (slot.isHere(inv, slotIn))
/*     */       {
/* 117 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Slot getSlot(int slotId) {
/* 126 */     return this.inventorySlots.get(slotId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 134 */     Slot slot = this.inventorySlots.get(index);
/* 135 */     return (slot != null) ? slot.getStack() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
/* 143 */     ItemStack itemstack = null;
/* 144 */     InventoryPlayer inventoryplayer = playerIn.inventory;
/*     */     
/* 146 */     if (mode == 5) {
/*     */       
/* 148 */       int i = this.dragEvent;
/* 149 */       this.dragEvent = getDragEvent(clickedButton);
/*     */       
/* 151 */       if ((i != 1 || this.dragEvent != 2) && i != this.dragEvent) {
/*     */         
/* 153 */         resetDrag();
/*     */       }
/* 155 */       else if (inventoryplayer.getItemStack() == null) {
/*     */         
/* 157 */         resetDrag();
/*     */       }
/* 159 */       else if (this.dragEvent == 0) {
/*     */         
/* 161 */         this.dragMode = extractDragMode(clickedButton);
/*     */         
/* 163 */         if (isValidDragMode(this.dragMode, playerIn))
/*     */         {
/* 165 */           this.dragEvent = 1;
/* 166 */           this.dragSlots.clear();
/*     */         }
/*     */         else
/*     */         {
/* 170 */           resetDrag();
/*     */         }
/*     */       
/* 173 */       } else if (this.dragEvent == 1) {
/*     */         
/* 175 */         Slot slot = this.inventorySlots.get(slotId);
/*     */         
/* 177 */         if (slot != null && canAddItemToSlot(slot, inventoryplayer.getItemStack(), true) && slot.isItemValid(inventoryplayer.getItemStack()) && (inventoryplayer.getItemStack()).stackSize > this.dragSlots.size() && canDragIntoSlot(slot))
/*     */         {
/* 179 */           this.dragSlots.add(slot);
/*     */         }
/*     */       }
/* 182 */       else if (this.dragEvent == 2) {
/*     */         
/* 184 */         if (!this.dragSlots.isEmpty()) {
/*     */           
/* 186 */           ItemStack itemstack3 = inventoryplayer.getItemStack().copy();
/* 187 */           int j = (inventoryplayer.getItemStack()).stackSize;
/*     */           
/* 189 */           for (Slot slot1 : this.dragSlots) {
/*     */             
/* 191 */             if (slot1 != null && canAddItemToSlot(slot1, inventoryplayer.getItemStack(), true) && slot1.isItemValid(inventoryplayer.getItemStack()) && (inventoryplayer.getItemStack()).stackSize >= this.dragSlots.size() && canDragIntoSlot(slot1)) {
/*     */               
/* 193 */               ItemStack itemstack1 = itemstack3.copy();
/* 194 */               int k = slot1.getHasStack() ? (slot1.getStack()).stackSize : 0;
/* 195 */               computeStackSize(this.dragSlots, this.dragMode, itemstack1, k);
/*     */               
/* 197 */               if (itemstack1.stackSize > itemstack1.getMaxStackSize())
/*     */               {
/* 199 */                 itemstack1.stackSize = itemstack1.getMaxStackSize();
/*     */               }
/*     */               
/* 202 */               if (itemstack1.stackSize > slot1.getItemStackLimit(itemstack1))
/*     */               {
/* 204 */                 itemstack1.stackSize = slot1.getItemStackLimit(itemstack1);
/*     */               }
/*     */               
/* 207 */               j -= itemstack1.stackSize - k;
/* 208 */               slot1.putStack(itemstack1);
/*     */             } 
/*     */           } 
/*     */           
/* 212 */           itemstack3.stackSize = j;
/*     */           
/* 214 */           if (itemstack3.stackSize <= 0)
/*     */           {
/* 216 */             itemstack3 = null;
/*     */           }
/*     */           
/* 219 */           inventoryplayer.setItemStack(itemstack3);
/*     */         } 
/*     */         
/* 222 */         resetDrag();
/*     */       }
/*     */       else {
/*     */         
/* 226 */         resetDrag();
/*     */       }
/*     */     
/* 229 */     } else if (this.dragEvent != 0) {
/*     */       
/* 231 */       resetDrag();
/*     */     }
/* 233 */     else if ((mode == 0 || mode == 1) && (clickedButton == 0 || clickedButton == 1)) {
/*     */       
/* 235 */       if (slotId == -999) {
/*     */         
/* 237 */         if (inventoryplayer.getItemStack() != null) {
/*     */           
/* 239 */           if (clickedButton == 0) {
/*     */             
/* 241 */             playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
/* 242 */             inventoryplayer.setItemStack((ItemStack)null);
/*     */           } 
/*     */           
/* 245 */           if (clickedButton == 1)
/*     */           {
/* 247 */             playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);
/*     */             
/* 249 */             if ((inventoryplayer.getItemStack()).stackSize == 0)
/*     */             {
/* 251 */               inventoryplayer.setItemStack((ItemStack)null);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 256 */       } else if (mode == 1) {
/*     */         
/* 258 */         if (slotId < 0)
/*     */         {
/* 260 */           return null;
/*     */         }
/*     */         
/* 263 */         Slot slot6 = this.inventorySlots.get(slotId);
/*     */         
/* 265 */         if (slot6 != null && slot6.canTakeStack(playerIn)) {
/*     */           
/* 267 */           ItemStack itemstack8 = transferStackInSlot(playerIn, slotId);
/*     */           
/* 269 */           if (itemstack8 != null)
/*     */           {
/* 271 */             Item item = itemstack8.getItem();
/* 272 */             itemstack = itemstack8.copy();
/*     */             
/* 274 */             if (slot6.getStack() != null && slot6.getStack().getItem() == item)
/*     */             {
/* 276 */               retrySlotClick(slotId, clickedButton, true, playerIn);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 283 */         if (slotId < 0)
/*     */         {
/* 285 */           return null;
/*     */         }
/*     */         
/* 288 */         Slot slot7 = this.inventorySlots.get(slotId);
/*     */         
/* 290 */         if (slot7 != null)
/*     */         {
/* 292 */           ItemStack itemstack9 = slot7.getStack();
/* 293 */           ItemStack itemstack10 = inventoryplayer.getItemStack();
/*     */           
/* 295 */           if (itemstack9 != null)
/*     */           {
/* 297 */             itemstack = itemstack9.copy();
/*     */           }
/*     */           
/* 300 */           if (itemstack9 == null) {
/*     */             
/* 302 */             if (itemstack10 != null && slot7.isItemValid(itemstack10))
/*     */             {
/* 304 */               int k2 = (clickedButton == 0) ? itemstack10.stackSize : 1;
/*     */               
/* 306 */               if (k2 > slot7.getItemStackLimit(itemstack10))
/*     */               {
/* 308 */                 k2 = slot7.getItemStackLimit(itemstack10);
/*     */               }
/*     */               
/* 311 */               if (itemstack10.stackSize >= k2)
/*     */               {
/* 313 */                 slot7.putStack(itemstack10.splitStack(k2));
/*     */               }
/*     */               
/* 316 */               if (itemstack10.stackSize == 0)
/*     */               {
/* 318 */                 inventoryplayer.setItemStack((ItemStack)null);
/*     */               }
/*     */             }
/*     */           
/* 322 */           } else if (slot7.canTakeStack(playerIn)) {
/*     */             
/* 324 */             if (itemstack10 == null) {
/*     */               
/* 326 */               int j2 = (clickedButton == 0) ? itemstack9.stackSize : ((itemstack9.stackSize + 1) / 2);
/* 327 */               ItemStack itemstack12 = slot7.decrStackSize(j2);
/* 328 */               inventoryplayer.setItemStack(itemstack12);
/*     */               
/* 330 */               if (itemstack9.stackSize == 0)
/*     */               {
/* 332 */                 slot7.putStack((ItemStack)null);
/*     */               }
/*     */               
/* 335 */               slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
/*     */             }
/* 337 */             else if (slot7.isItemValid(itemstack10)) {
/*     */               
/* 339 */               if (itemstack9.getItem() == itemstack10.getItem() && itemstack9.getMetadata() == itemstack10.getMetadata() && ItemStack.areItemStackTagsEqual(itemstack9, itemstack10))
/*     */               {
/* 341 */                 int i2 = (clickedButton == 0) ? itemstack10.stackSize : 1;
/*     */                 
/* 343 */                 if (i2 > slot7.getItemStackLimit(itemstack10) - itemstack9.stackSize)
/*     */                 {
/* 345 */                   i2 = slot7.getItemStackLimit(itemstack10) - itemstack9.stackSize;
/*     */                 }
/*     */                 
/* 348 */                 if (i2 > itemstack10.getMaxStackSize() - itemstack9.stackSize)
/*     */                 {
/* 350 */                   i2 = itemstack10.getMaxStackSize() - itemstack9.stackSize;
/*     */                 }
/*     */                 
/* 353 */                 itemstack10.splitStack(i2);
/*     */                 
/* 355 */                 if (itemstack10.stackSize == 0)
/*     */                 {
/* 357 */                   inventoryplayer.setItemStack((ItemStack)null);
/*     */                 }
/*     */                 
/* 360 */                 itemstack9.stackSize += i2;
/*     */               }
/* 362 */               else if (itemstack10.stackSize <= slot7.getItemStackLimit(itemstack10))
/*     */               {
/* 364 */                 slot7.putStack(itemstack10);
/* 365 */                 inventoryplayer.setItemStack(itemstack9);
/*     */               }
/*     */             
/* 368 */             } else if (itemstack9.getItem() == itemstack10.getItem() && itemstack10.getMaxStackSize() > 1 && (!itemstack9.getHasSubtypes() || itemstack9.getMetadata() == itemstack10.getMetadata()) && ItemStack.areItemStackTagsEqual(itemstack9, itemstack10)) {
/*     */               
/* 370 */               int l1 = itemstack9.stackSize;
/*     */               
/* 372 */               if (l1 > 0 && l1 + itemstack10.stackSize <= itemstack10.getMaxStackSize()) {
/*     */                 
/* 374 */                 itemstack10.stackSize += l1;
/* 375 */                 itemstack9 = slot7.decrStackSize(l1);
/*     */                 
/* 377 */                 if (itemstack9.stackSize == 0)
/*     */                 {
/* 379 */                   slot7.putStack((ItemStack)null);
/*     */                 }
/*     */                 
/* 382 */                 slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 387 */           slot7.onSlotChanged();
/*     */         }
/*     */       
/*     */       } 
/* 391 */     } else if (mode == 2 && clickedButton >= 0 && clickedButton < 9) {
/*     */       
/* 393 */       Slot slot5 = this.inventorySlots.get(slotId);
/*     */       
/* 395 */       if (slot5.canTakeStack(playerIn)) {
/*     */         int i;
/* 397 */         ItemStack itemstack7 = inventoryplayer.getStackInSlot(clickedButton);
/* 398 */         boolean flag = (itemstack7 == null || (slot5.inventory == inventoryplayer && slot5.isItemValid(itemstack7)));
/* 399 */         int k1 = -1;
/*     */         
/* 401 */         if (!flag) {
/*     */           
/* 403 */           k1 = inventoryplayer.getFirstEmptyStack();
/* 404 */           i = flag | ((k1 > -1) ? 1 : 0);
/*     */         } 
/*     */         
/* 407 */         if (slot5.getHasStack() && i != 0) {
/*     */           
/* 409 */           ItemStack itemstack11 = slot5.getStack();
/* 410 */           inventoryplayer.setInventorySlotContents(clickedButton, itemstack11.copy());
/*     */           
/* 412 */           if ((slot5.inventory != inventoryplayer || !slot5.isItemValid(itemstack7)) && itemstack7 != null) {
/*     */             
/* 414 */             if (k1 > -1)
/*     */             {
/* 416 */               inventoryplayer.addItemStackToInventory(itemstack7);
/* 417 */               slot5.decrStackSize(itemstack11.stackSize);
/* 418 */               slot5.putStack((ItemStack)null);
/* 419 */               slot5.onPickupFromSlot(playerIn, itemstack11);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 424 */             slot5.decrStackSize(itemstack11.stackSize);
/* 425 */             slot5.putStack(itemstack7);
/* 426 */             slot5.onPickupFromSlot(playerIn, itemstack11);
/*     */           }
/*     */         
/* 429 */         } else if (!slot5.getHasStack() && itemstack7 != null && slot5.isItemValid(itemstack7)) {
/*     */           
/* 431 */           inventoryplayer.setInventorySlotContents(clickedButton, (ItemStack)null);
/* 432 */           slot5.putStack(itemstack7);
/*     */         }
/*     */       
/*     */       } 
/* 436 */     } else if (mode == 3 && playerIn.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotId >= 0) {
/*     */       
/* 438 */       Slot slot4 = this.inventorySlots.get(slotId);
/*     */       
/* 440 */       if (slot4 != null && slot4.getHasStack())
/*     */       {
/* 442 */         ItemStack itemstack6 = slot4.getStack().copy();
/* 443 */         itemstack6.stackSize = itemstack6.getMaxStackSize();
/* 444 */         inventoryplayer.setItemStack(itemstack6);
/*     */       }
/*     */     
/* 447 */     } else if (mode == 4 && inventoryplayer.getItemStack() == null && slotId >= 0) {
/*     */       
/* 449 */       Slot slot3 = this.inventorySlots.get(slotId);
/*     */       
/* 451 */       if (slot3 != null && slot3.getHasStack() && slot3.canTakeStack(playerIn))
/*     */       {
/* 453 */         ItemStack itemstack5 = slot3.decrStackSize((clickedButton == 0) ? 1 : (slot3.getStack()).stackSize);
/* 454 */         slot3.onPickupFromSlot(playerIn, itemstack5);
/* 455 */         playerIn.dropPlayerItemWithRandomChoice(itemstack5, true);
/*     */       }
/*     */     
/* 458 */     } else if (mode == 6 && slotId >= 0) {
/*     */       
/* 460 */       Slot slot2 = this.inventorySlots.get(slotId);
/* 461 */       ItemStack itemstack4 = inventoryplayer.getItemStack();
/*     */       
/* 463 */       if (itemstack4 != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(playerIn))) {
/*     */         
/* 465 */         int i1 = (clickedButton == 0) ? 0 : (this.inventorySlots.size() - 1);
/* 466 */         int j1 = (clickedButton == 0) ? 1 : -1;
/*     */         
/* 468 */         for (int l2 = 0; l2 < 2; l2++) {
/*     */           int i3;
/* 470 */           for (i3 = i1; i3 >= 0 && i3 < this.inventorySlots.size() && itemstack4.stackSize < itemstack4.getMaxStackSize(); i3 += j1) {
/*     */             
/* 472 */             Slot slot8 = this.inventorySlots.get(i3);
/*     */             
/* 474 */             if (slot8.getHasStack() && canAddItemToSlot(slot8, itemstack4, true) && slot8.canTakeStack(playerIn) && canMergeSlot(itemstack4, slot8) && (l2 != 0 || (slot8.getStack()).stackSize != slot8.getStack().getMaxStackSize())) {
/*     */               
/* 476 */               int l = Math.min(itemstack4.getMaxStackSize() - itemstack4.stackSize, (slot8.getStack()).stackSize);
/* 477 */               ItemStack itemstack2 = slot8.decrStackSize(l);
/* 478 */               itemstack4.stackSize += l;
/*     */               
/* 480 */               if (itemstack2.stackSize <= 0)
/*     */               {
/* 482 */                 slot8.putStack((ItemStack)null);
/*     */               }
/*     */               
/* 485 */               slot8.onPickupFromSlot(playerIn, itemstack2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 491 */       detectAndSendChanges();
/*     */     } 
/*     */     
/* 494 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 503 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer playerIn) {
/* 511 */     slotClick(slotId, clickedButton, 1, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 519 */     InventoryPlayer inventoryplayer = playerIn.inventory;
/*     */     
/* 521 */     if (inventoryplayer.getItemStack() != null) {
/*     */       
/* 523 */       playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
/* 524 */       inventoryplayer.setItemStack((ItemStack)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 533 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStackInSlot(int slotID, ItemStack stack) {
/* 541 */     getSlot(slotID).putStack(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStacksInSlots(ItemStack[] p_75131_1_) {
/* 549 */     for (int i = 0; i < p_75131_1_.length; i++)
/*     */     {
/* 551 */       getSlot(i).putStack(p_75131_1_[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getNextTransactionID(InventoryPlayer p_75136_1_) {
/* 564 */     this.transactionID = (short)(this.transactionID + 1);
/* 565 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanCraft(EntityPlayer p_75129_1_) {
/* 573 */     return !this.playerList.contains(p_75129_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanCraft(EntityPlayer p_75128_1_, boolean p_75128_2_) {
/* 581 */     if (p_75128_2_) {
/*     */       
/* 583 */       this.playerList.remove(p_75128_1_);
/*     */     }
/*     */     else {
/*     */       
/* 587 */       this.playerList.add(p_75128_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean canInteractWith(EntityPlayer paramEntityPlayer);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
/* 600 */     boolean flag = false;
/* 601 */     int i = startIndex;
/*     */     
/* 603 */     if (reverseDirection)
/*     */     {
/* 605 */       i = endIndex - 1;
/*     */     }
/*     */     
/* 608 */     if (stack.isStackable())
/*     */     {
/* 610 */       while (stack.stackSize > 0 && ((!reverseDirection && i < endIndex) || (reverseDirection && i >= startIndex))) {
/*     */         
/* 612 */         Slot slot = this.inventorySlots.get(i);
/* 613 */         ItemStack itemstack = slot.getStack();
/*     */         
/* 615 */         if (itemstack != null && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
/*     */           
/* 617 */           int j = itemstack.stackSize + stack.stackSize;
/*     */           
/* 619 */           if (j <= stack.getMaxStackSize()) {
/*     */             
/* 621 */             stack.stackSize = 0;
/* 622 */             itemstack.stackSize = j;
/* 623 */             slot.onSlotChanged();
/* 624 */             flag = true;
/*     */           }
/* 626 */           else if (itemstack.stackSize < stack.getMaxStackSize()) {
/*     */             
/* 628 */             stack.stackSize -= stack.getMaxStackSize() - itemstack.stackSize;
/* 629 */             itemstack.stackSize = stack.getMaxStackSize();
/* 630 */             slot.onSlotChanged();
/* 631 */             flag = true;
/*     */           } 
/*     */         } 
/*     */         
/* 635 */         if (reverseDirection) {
/*     */           
/* 637 */           i--;
/*     */           
/*     */           continue;
/*     */         } 
/* 641 */         i++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 646 */     if (stack.stackSize > 0) {
/*     */       
/* 648 */       if (reverseDirection) {
/*     */         
/* 650 */         i = endIndex - 1;
/*     */       }
/*     */       else {
/*     */         
/* 654 */         i = startIndex;
/*     */       } 
/*     */       
/* 657 */       while ((!reverseDirection && i < endIndex) || (reverseDirection && i >= startIndex)) {
/*     */         
/* 659 */         Slot slot1 = this.inventorySlots.get(i);
/* 660 */         ItemStack itemstack1 = slot1.getStack();
/*     */         
/* 662 */         if (itemstack1 == null) {
/*     */           
/* 664 */           slot1.putStack(stack.copy());
/* 665 */           slot1.onSlotChanged();
/* 666 */           stack.stackSize = 0;
/* 667 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/* 671 */         if (reverseDirection) {
/*     */           
/* 673 */           i--;
/*     */           
/*     */           continue;
/*     */         } 
/* 677 */         i++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 682 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int extractDragMode(int p_94529_0_) {
/* 690 */     return p_94529_0_ >> 2 & 0x3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDragEvent(int p_94532_0_) {
/* 698 */     return p_94532_0_ & 0x3;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_94534_d(int p_94534_0_, int p_94534_1_) {
/* 703 */     return p_94534_0_ & 0x3 | (p_94534_1_ & 0x3) << 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidDragMode(int dragModeIn, EntityPlayer player) {
/* 708 */     return (dragModeIn == 0) ? true : ((dragModeIn == 1) ? true : ((dragModeIn == 2 && player.capabilities.isCreativeMode)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetDrag() {
/* 716 */     this.dragEvent = 0;
/* 717 */     this.dragSlots.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canAddItemToSlot(Slot slotIn, ItemStack stack, boolean stackSizeMatters) {
/*     */     int i;
/* 725 */     boolean flag = (slotIn == null || !slotIn.getHasStack());
/*     */     
/* 727 */     if (slotIn != null && slotIn.getHasStack() && stack != null && stack.isItemEqual(slotIn.getStack()) && ItemStack.areItemStackTagsEqual(slotIn.getStack(), stack))
/*     */     {
/* 729 */       i = flag | (((slotIn.getStack()).stackSize + (stackSizeMatters ? 0 : stack.stackSize) <= stack.getMaxStackSize()) ? 1 : 0);
/*     */     }
/*     */     
/* 732 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeStackSize(Set<Slot> p_94525_0_, int p_94525_1_, ItemStack p_94525_2_, int p_94525_3_) {
/* 741 */     switch (p_94525_1_) {
/*     */       
/*     */       case 0:
/* 744 */         p_94525_2_.stackSize = MathHelper.floor_float(p_94525_2_.stackSize / p_94525_0_.size());
/*     */         break;
/*     */       
/*     */       case 1:
/* 748 */         p_94525_2_.stackSize = 1;
/*     */         break;
/*     */       
/*     */       case 2:
/* 752 */         p_94525_2_.stackSize = p_94525_2_.getItem().getItemStackLimit();
/*     */         break;
/*     */     } 
/* 755 */     p_94525_2_.stackSize += p_94525_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDragIntoSlot(Slot p_94531_1_) {
/* 764 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcRedstone(TileEntity te) {
/* 772 */     return (te instanceof IInventory) ? calcRedstoneFromInventory((IInventory)te) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calcRedstoneFromInventory(IInventory inv) {
/* 777 */     if (inv == null)
/*     */     {
/* 779 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 783 */     int i = 0;
/* 784 */     float f = 0.0F;
/*     */     
/* 786 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */       
/* 788 */       ItemStack itemstack = inv.getStackInSlot(j);
/*     */       
/* 790 */       if (itemstack != null) {
/*     */         
/* 792 */         f += itemstack.stackSize / Math.min(inv.getInventoryStackLimit(), itemstack.getMaxStackSize());
/* 793 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 797 */     f /= inv.getSizeInventory();
/* 798 */     return MathHelper.floor_float(f * 14.0F) + ((i > 0) ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\inventory\Container.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */