/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerEnchantment
/*     */   extends Container
/*     */ {
/*     */   public IInventory tableInventory;
/*     */   private final World worldPointer;
/*     */   private final BlockPos position;
/*     */   private final Random rand;
/*     */   public int xpSeed;
/*     */   public int[] enchantLevels;
/*     */   public int[] enchantmentIds;
/*     */   
/*     */   public ContainerEnchantment(InventoryPlayer playerInv, World worldIn) {
/*  35 */     this(playerInv, worldIn, BlockPos.ORIGIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerEnchantment(InventoryPlayer playerInv, World worldIn, BlockPos pos) {
/*  40 */     this.tableInventory = new InventoryBasic("Enchant", true, 2)
/*     */       {
/*     */         public int getInventoryStackLimit()
/*     */         {
/*  44 */           return 64;
/*     */         }
/*     */         
/*     */         public void markDirty() {
/*  48 */           super.markDirty();
/*  49 */           ContainerEnchantment.this.onCraftMatrixChanged(this);
/*     */         }
/*     */       };
/*  52 */     this.rand = new Random();
/*  53 */     this.enchantLevels = new int[3];
/*  54 */     this.enchantmentIds = new int[] { -1, -1, -1 };
/*  55 */     this.worldPointer = worldIn;
/*  56 */     this.position = pos;
/*  57 */     this.xpSeed = playerInv.player.getXPSeed();
/*  58 */     addSlotToContainer(new Slot(this.tableInventory, 0, 15, 47)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  62 */             return true;
/*     */           }
/*     */           
/*     */           public int getSlotStackLimit() {
/*  66 */             return 1;
/*     */           }
/*     */         });
/*  69 */     addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  73 */             return (stack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(stack.getMetadata()) == EnumDyeColor.BLUE);
/*     */           }
/*     */         });
/*     */     
/*  77 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  79 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  81 */         addSlotToContainer(new Slot((IInventory)playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  85 */     for (int k = 0; k < 9; k++)
/*     */     {
/*  87 */       addSlotToContainer(new Slot((IInventory)playerInv, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  93 */     super.onCraftGuiOpened(listener);
/*  94 */     listener.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
/*  95 */     listener.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
/*  96 */     listener.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
/*  97 */     listener.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
/*  98 */     listener.sendProgressBarUpdate(this, 4, this.enchantmentIds[0]);
/*  99 */     listener.sendProgressBarUpdate(this, 5, this.enchantmentIds[1]);
/* 100 */     listener.sendProgressBarUpdate(this, 6, this.enchantmentIds[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/* 108 */     super.detectAndSendChanges();
/*     */     
/* 110 */     for (int i = 0; i < this.crafters.size(); i++) {
/*     */       
/* 112 */       ICrafting icrafting = this.crafters.get(i);
/* 113 */       icrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
/* 114 */       icrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
/* 115 */       icrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
/* 116 */       icrafting.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
/* 117 */       icrafting.sendProgressBarUpdate(this, 4, this.enchantmentIds[0]);
/* 118 */       icrafting.sendProgressBarUpdate(this, 5, this.enchantmentIds[1]);
/* 119 */       icrafting.sendProgressBarUpdate(this, 6, this.enchantmentIds[2]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/* 125 */     if (id >= 0 && id <= 2) {
/*     */       
/* 127 */       this.enchantLevels[id] = data;
/*     */     }
/* 129 */     else if (id == 3) {
/*     */       
/* 131 */       this.xpSeed = data;
/*     */     }
/* 133 */     else if (id >= 4 && id <= 6) {
/*     */       
/* 135 */       this.enchantmentIds[id - 4] = data;
/*     */     }
/*     */     else {
/*     */       
/* 139 */       super.updateProgressBar(id, data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 148 */     if (inventoryIn == this.tableInventory) {
/*     */       
/* 150 */       ItemStack itemstack = inventoryIn.getStackInSlot(0);
/*     */       
/* 152 */       if (itemstack != null && itemstack.isItemEnchantable()) {
/*     */         
/* 154 */         if (!this.worldPointer.isRemote)
/*     */         {
/* 156 */           int l = 0;
/*     */           
/* 158 */           for (int j = -1; j <= 1; j++) {
/*     */             
/* 160 */             for (int k = -1; k <= 1; k++) {
/*     */               
/* 162 */               if ((j != 0 || k != 0) && this.worldPointer.isAirBlock(this.position.add(k, 0, j)) && this.worldPointer.isAirBlock(this.position.add(k, 1, j))) {
/*     */                 
/* 164 */                 if (this.worldPointer.getBlockState(this.position.add(k << 1, 0, j << 1)).getBlock() == Blocks.bookshelf)
/*     */                 {
/* 166 */                   l++;
/*     */                 }
/*     */                 
/* 169 */                 if (this.worldPointer.getBlockState(this.position.add(k << 1, 1, j << 1)).getBlock() == Blocks.bookshelf)
/*     */                 {
/* 171 */                   l++;
/*     */                 }
/*     */                 
/* 174 */                 if (k != 0 && j != 0) {
/*     */                   
/* 176 */                   if (this.worldPointer.getBlockState(this.position.add(k << 1, 0, j)).getBlock() == Blocks.bookshelf)
/*     */                   {
/* 178 */                     l++;
/*     */                   }
/*     */                   
/* 181 */                   if (this.worldPointer.getBlockState(this.position.add(k << 1, 1, j)).getBlock() == Blocks.bookshelf)
/*     */                   {
/* 183 */                     l++;
/*     */                   }
/*     */                   
/* 186 */                   if (this.worldPointer.getBlockState(this.position.add(k, 0, j << 1)).getBlock() == Blocks.bookshelf)
/*     */                   {
/* 188 */                     l++;
/*     */                   }
/*     */                   
/* 191 */                   if (this.worldPointer.getBlockState(this.position.add(k, 1, j << 1)).getBlock() == Blocks.bookshelf)
/*     */                   {
/* 193 */                     l++;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 200 */           this.rand.setSeed(this.xpSeed);
/*     */           
/* 202 */           for (int i1 = 0; i1 < 3; i1++) {
/*     */             
/* 204 */             this.enchantLevels[i1] = EnchantmentHelper.calcItemStackEnchantability(this.rand, i1, l, itemstack);
/* 205 */             this.enchantmentIds[i1] = -1;
/*     */             
/* 207 */             if (this.enchantLevels[i1] < i1 + 1)
/*     */             {
/* 209 */               this.enchantLevels[i1] = 0;
/*     */             }
/*     */           } 
/*     */           
/* 213 */           for (int j1 = 0; j1 < 3; j1++) {
/*     */             
/* 215 */             if (this.enchantLevels[j1] > 0) {
/*     */               
/* 217 */               List<EnchantmentData> list = func_178148_a(itemstack, j1, this.enchantLevels[j1]);
/*     */               
/* 219 */               if (list != null && !list.isEmpty()) {
/*     */                 
/* 221 */                 EnchantmentData enchantmentdata = list.get(this.rand.nextInt(list.size()));
/* 222 */                 this.enchantmentIds[j1] = enchantmentdata.enchantmentobj.effectId | enchantmentdata.enchantmentLevel << 8;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 227 */           detectAndSendChanges();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 232 */         for (int i = 0; i < 3; i++) {
/*     */           
/* 234 */           this.enchantLevels[i] = 0;
/* 235 */           this.enchantmentIds[i] = -1;
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
/* 246 */     ItemStack itemstack = this.tableInventory.getStackInSlot(0);
/* 247 */     ItemStack itemstack1 = this.tableInventory.getStackInSlot(1);
/* 248 */     int i = id + 1;
/*     */     
/* 250 */     if ((itemstack1 == null || itemstack1.stackSize < i) && !playerIn.capabilities.isCreativeMode)
/*     */     {
/* 252 */       return false;
/*     */     }
/* 254 */     if (this.enchantLevels[id] > 0 && itemstack != null && ((playerIn.experienceLevel >= i && playerIn.experienceLevel >= this.enchantLevels[id]) || playerIn.capabilities.isCreativeMode)) {
/*     */       
/* 256 */       if (!this.worldPointer.isRemote) {
/*     */         
/* 258 */         List<EnchantmentData> list = func_178148_a(itemstack, id, this.enchantLevels[id]);
/* 259 */         boolean flag = (itemstack.getItem() == Items.book);
/*     */         
/* 261 */         if (list != null) {
/*     */           
/* 263 */           playerIn.removeExperienceLevel(i);
/*     */           
/* 265 */           if (flag)
/*     */           {
/* 267 */             itemstack.setItem((Item)Items.enchanted_book);
/*     */           }
/*     */           
/* 270 */           for (int j = 0; j < list.size(); j++) {
/*     */             
/* 272 */             EnchantmentData enchantmentdata = list.get(j);
/*     */             
/* 274 */             if (flag) {
/*     */               
/* 276 */               Items.enchanted_book.addEnchantment(itemstack, enchantmentdata);
/*     */             }
/*     */             else {
/*     */               
/* 280 */               itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
/*     */             } 
/*     */           } 
/*     */           
/* 284 */           if (!playerIn.capabilities.isCreativeMode) {
/*     */             
/* 286 */             itemstack1.stackSize -= i;
/*     */             
/* 288 */             if (itemstack1.stackSize <= 0)
/*     */             {
/* 290 */               this.tableInventory.setInventorySlotContents(1, (ItemStack)null);
/*     */             }
/*     */           } 
/*     */           
/* 294 */           playerIn.triggerAchievement(StatList.field_181739_W);
/* 295 */           this.tableInventory.markDirty();
/* 296 */           this.xpSeed = playerIn.getXPSeed();
/* 297 */           onCraftMatrixChanged(this.tableInventory);
/*     */         } 
/*     */       } 
/*     */       
/* 301 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 305 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<EnchantmentData> func_178148_a(ItemStack stack, int p_178148_2_, int p_178148_3_) {
/* 311 */     this.rand.setSeed((this.xpSeed + p_178148_2_));
/* 312 */     List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(this.rand, stack, p_178148_3_);
/*     */     
/* 314 */     if (stack.getItem() == Items.book && list != null && list.size() > 1)
/*     */     {
/* 316 */       list.remove(this.rand.nextInt(list.size()));
/*     */     }
/*     */     
/* 319 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLapisAmount() {
/* 324 */     ItemStack itemstack = this.tableInventory.getStackInSlot(1);
/* 325 */     return (itemstack == null) ? 0 : itemstack.stackSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 333 */     super.onContainerClosed(playerIn);
/*     */     
/* 335 */     if (!this.worldPointer.isRemote)
/*     */     {
/* 337 */       for (int i = 0; i < this.tableInventory.getSizeInventory(); i++) {
/*     */         
/* 339 */         ItemStack itemstack = this.tableInventory.removeStackFromSlot(i);
/*     */         
/* 341 */         if (itemstack != null)
/*     */         {
/* 343 */           playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 351 */     return (this.worldPointer.getBlockState(this.position).getBlock() != Blocks.enchanting_table) ? false : ((playerIn.getDistanceSq(this.position.getX() + 0.5D, this.position.getY() + 0.5D, this.position.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 359 */     ItemStack itemstack = null;
/* 360 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 362 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 364 */       ItemStack itemstack1 = slot.getStack();
/* 365 */       itemstack = itemstack1.copy();
/*     */       
/* 367 */       if (index == 0) {
/*     */         
/* 369 */         if (!mergeItemStack(itemstack1, 2, 38, true))
/*     */         {
/* 371 */           return null;
/*     */         }
/*     */       }
/* 374 */       else if (index == 1) {
/*     */         
/* 376 */         if (!mergeItemStack(itemstack1, 2, 38, true))
/*     */         {
/* 378 */           return null;
/*     */         }
/*     */       }
/* 381 */       else if (itemstack1.getItem() == Items.dye && EnumDyeColor.byDyeDamage(itemstack1.getMetadata()) == EnumDyeColor.BLUE) {
/*     */         
/* 383 */         if (!mergeItemStack(itemstack1, 1, 2, true))
/*     */         {
/* 385 */           return null;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 390 */         if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1))
/*     */         {
/* 392 */           return null;
/*     */         }
/*     */         
/* 395 */         if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1) {
/*     */           
/* 397 */           ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
/* 398 */           itemstack1.stackSize = 0;
/*     */         }
/* 400 */         else if (itemstack1.stackSize >= 1) {
/*     */           
/* 402 */           ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
/* 403 */           itemstack1.stackSize--;
/*     */         } 
/*     */       } 
/*     */       
/* 407 */       if (itemstack1.stackSize == 0) {
/*     */         
/* 409 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else {
/*     */         
/* 413 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 416 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 418 */         return null;
/*     */       }
/*     */       
/* 421 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 424 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\inventory\ContainerEnchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */