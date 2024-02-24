/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.BlockAnvil;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ContainerRepair
/*     */   extends Container {
/*  23 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final IInventory outputSlot;
/*     */ 
/*     */   
/*     */   IInventory inputSlots;
/*     */ 
/*     */   
/*     */   private final World theWorld;
/*     */ 
/*     */   
/*     */   private final BlockPos selfPosition;
/*     */   
/*     */   public int maximumCost;
/*     */   
/*     */   int materialCost;
/*     */   
/*     */   private String repairedItemName;
/*     */   
/*     */   private final EntityPlayer thePlayer;
/*     */ 
/*     */   
/*     */   public ContainerRepair(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {
/*  47 */     this(playerInventory, worldIn, BlockPos.ORIGIN, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerRepair(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn, EntityPlayer player) {
/*  52 */     this.outputSlot = new InventoryCraftResult();
/*  53 */     this.inputSlots = new InventoryBasic("Repair", true, 2)
/*     */       {
/*     */         public void markDirty()
/*     */         {
/*  57 */           super.markDirty();
/*  58 */           ContainerRepair.this.onCraftMatrixChanged(this);
/*     */         }
/*     */       };
/*  61 */     this.selfPosition = blockPosIn;
/*  62 */     this.theWorld = worldIn;
/*  63 */     this.thePlayer = player;
/*  64 */     addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
/*  65 */     addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
/*  66 */     addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  70 */             return false;
/*     */           }
/*     */           
/*     */           public boolean canTakeStack(EntityPlayer playerIn) {
/*  74 */             return ((playerIn.capabilities.isCreativeMode || playerIn.experienceLevel >= ContainerRepair.this.maximumCost) && ContainerRepair.this.maximumCost > 0 && getHasStack());
/*     */           }
/*     */           
/*     */           public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/*  78 */             if (!playerIn.capabilities.isCreativeMode)
/*     */             {
/*  80 */               playerIn.addExperienceLevel(-ContainerRepair.this.maximumCost);
/*     */             }
/*     */             
/*  83 */             ContainerRepair.this.inputSlots.setInventorySlotContents(0, (ItemStack)null);
/*     */             
/*  85 */             if (ContainerRepair.this.materialCost > 0) {
/*     */               
/*  87 */               ItemStack itemstack = ContainerRepair.this.inputSlots.getStackInSlot(1);
/*     */               
/*  89 */               if (itemstack != null && itemstack.stackSize > ContainerRepair.this.materialCost)
/*     */               {
/*  91 */                 itemstack.stackSize -= ContainerRepair.this.materialCost;
/*  92 */                 ContainerRepair.this.inputSlots.setInventorySlotContents(1, itemstack);
/*     */               }
/*     */               else
/*     */               {
/*  96 */                 ContainerRepair.this.inputSlots.setInventorySlotContents(1, (ItemStack)null);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 101 */               ContainerRepair.this.inputSlots.setInventorySlotContents(1, (ItemStack)null);
/*     */             } 
/*     */             
/* 104 */             ContainerRepair.this.maximumCost = 0;
/* 105 */             IBlockState iblockstate = worldIn.getBlockState(blockPosIn);
/*     */             
/* 107 */             if (!playerIn.capabilities.isCreativeMode && !worldIn.isRemote && iblockstate.getBlock() == Blocks.anvil && playerIn.getRNG().nextFloat() < 0.12F) {
/*     */               
/* 109 */               int l = ((Integer)iblockstate.getValue((IProperty)BlockAnvil.DAMAGE)).intValue();
/* 110 */               l++;
/*     */               
/* 112 */               if (l > 2)
/*     */               {
/* 114 */                 worldIn.setBlockToAir(blockPosIn);
/* 115 */                 worldIn.playAuxSFX(1020, blockPosIn, 0);
/*     */               }
/*     */               else
/*     */               {
/* 119 */                 worldIn.setBlockState(blockPosIn, iblockstate.withProperty((IProperty)BlockAnvil.DAMAGE, Integer.valueOf(l)), 2);
/* 120 */                 worldIn.playAuxSFX(1021, blockPosIn, 0);
/*     */               }
/*     */             
/* 123 */             } else if (!worldIn.isRemote) {
/*     */               
/* 125 */               worldIn.playAuxSFX(1021, blockPosIn, 0);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 130 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 132 */       for (int j = 0; j < 9; j++)
/*     */       {
/* 134 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/* 138 */     for (int k = 0; k < 9; k++)
/*     */     {
/* 140 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 149 */     super.onCraftMatrixChanged(inventoryIn);
/*     */     
/* 151 */     if (inventoryIn == this.inputSlots)
/*     */     {
/* 153 */       updateRepairOutput();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRepairOutput() {
/* 162 */     int i = 0;
/* 163 */     int j = 1;
/* 164 */     int k = 1;
/* 165 */     int l = 1;
/* 166 */     int i1 = 2;
/* 167 */     int j1 = 1;
/* 168 */     int k1 = 1;
/* 169 */     ItemStack itemstack = this.inputSlots.getStackInSlot(0);
/* 170 */     this.maximumCost = 1;
/* 171 */     int l1 = 0;
/* 172 */     int i2 = 0;
/* 173 */     int j2 = 0;
/*     */     
/* 175 */     if (itemstack == null) {
/*     */       
/* 177 */       this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
/* 178 */       this.maximumCost = 0;
/*     */     }
/*     */     else {
/*     */       
/* 182 */       ItemStack itemstack1 = itemstack.copy();
/* 183 */       ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
/* 184 */       Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
/* 185 */       boolean flag = false;
/* 186 */       i2 = i2 + itemstack.getRepairCost() + ((itemstack2 == null) ? 0 : itemstack2.getRepairCost());
/* 187 */       this.materialCost = 0;
/*     */       
/* 189 */       if (itemstack2 != null) {
/*     */         
/* 191 */         flag = (itemstack2.getItem() == Items.enchanted_book && Items.enchanted_book.getEnchantments(itemstack2).tagCount() > 0);
/*     */         
/* 193 */         if (itemstack1.isItemStackDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2)) {
/*     */           
/* 195 */           int j4 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
/*     */           
/* 197 */           if (j4 <= 0) {
/*     */             
/* 199 */             this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
/* 200 */             this.maximumCost = 0;
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           int l4;
/* 206 */           for (l4 = 0; j4 > 0 && l4 < itemstack2.stackSize; l4++) {
/*     */             
/* 208 */             int j5 = itemstack1.getItemDamage() - j4;
/* 209 */             itemstack1.setItemDamage(j5);
/* 210 */             l1++;
/* 211 */             j4 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
/*     */           } 
/*     */           
/* 214 */           this.materialCost = l4;
/*     */         }
/*     */         else {
/*     */           
/* 218 */           if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isItemStackDamageable())) {
/*     */             
/* 220 */             this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
/* 221 */             this.maximumCost = 0;
/*     */             
/*     */             return;
/*     */           } 
/* 225 */           if (itemstack1.isItemStackDamageable() && !flag) {
/*     */             
/* 227 */             int k2 = itemstack.getMaxDamage() - itemstack.getItemDamage();
/* 228 */             int l2 = itemstack2.getMaxDamage() - itemstack2.getItemDamage();
/* 229 */             int i3 = l2 + itemstack1.getMaxDamage() * 12 / 100;
/* 230 */             int j3 = k2 + i3;
/* 231 */             int k3 = itemstack1.getMaxDamage() - j3;
/*     */             
/* 233 */             if (k3 < 0)
/*     */             {
/* 235 */               k3 = 0;
/*     */             }
/*     */             
/* 238 */             if (k3 < itemstack1.getMetadata()) {
/*     */               
/* 240 */               itemstack1.setItemDamage(k3);
/* 241 */               l1 += 2;
/*     */             } 
/*     */           } 
/*     */           
/* 245 */           Map<Integer, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
/* 246 */           Iterator<Integer> iterator1 = map1.keySet().iterator();
/*     */           
/* 248 */           while (iterator1.hasNext()) {
/*     */             
/* 250 */             int i5 = ((Integer)iterator1.next()).intValue();
/* 251 */             Enchantment enchantment = Enchantment.getEnchantmentById(i5);
/*     */             
/* 253 */             if (enchantment != null) {
/*     */               
/* 255 */               int i6, k5 = map.containsKey(Integer.valueOf(i5)) ? ((Integer)map.get(Integer.valueOf(i5))).intValue() : 0;
/* 256 */               int l3 = ((Integer)map1.get(Integer.valueOf(i5))).intValue();
/*     */ 
/*     */               
/* 259 */               if (k5 == l3) {
/*     */ 
/*     */                 
/* 262 */                 i6 = ++l3;
/*     */               }
/*     */               else {
/*     */                 
/* 266 */                 i6 = Math.max(l3, k5);
/*     */               } 
/*     */               
/* 269 */               l3 = i6;
/* 270 */               boolean flag1 = enchantment.canApply(itemstack);
/*     */               
/* 272 */               if (this.thePlayer.capabilities.isCreativeMode || itemstack.getItem() == Items.enchanted_book)
/*     */               {
/* 274 */                 flag1 = true;
/*     */               }
/*     */               
/* 277 */               Iterator<Integer> iterator = map.keySet().iterator();
/*     */               
/* 279 */               while (iterator.hasNext()) {
/*     */                 
/* 281 */                 int i4 = ((Integer)iterator.next()).intValue();
/*     */                 
/* 283 */                 if (i4 != i5 && !enchantment.canApplyTogether(Enchantment.getEnchantmentById(i4))) {
/*     */                   
/* 285 */                   flag1 = false;
/* 286 */                   l1++;
/*     */                 } 
/*     */               } 
/*     */               
/* 290 */               if (flag1) {
/*     */                 
/* 292 */                 if (l3 > enchantment.getMaxLevel())
/*     */                 {
/* 294 */                   l3 = enchantment.getMaxLevel();
/*     */                 }
/*     */                 
/* 297 */                 map.put(Integer.valueOf(i5), Integer.valueOf(l3));
/* 298 */                 int l5 = 0;
/*     */                 
/* 300 */                 switch (enchantment.getWeight()) {
/*     */                   
/*     */                   case 1:
/* 303 */                     l5 = 8;
/*     */                     break;
/*     */                   
/*     */                   case 2:
/* 307 */                     l5 = 4;
/*     */                     break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   case 5:
/* 319 */                     l5 = 2;
/*     */                     break;
/*     */                   
/*     */                   case 10:
/* 323 */                     l5 = 1;
/*     */                     break;
/*     */                 } 
/* 326 */                 if (flag)
/*     */                 {
/* 328 */                   l5 = Math.max(1, l5 / 2);
/*     */                 }
/*     */                 
/* 331 */                 l1 += l5 * l3;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 338 */       if (StringUtils.isBlank(this.repairedItemName)) {
/*     */         
/* 340 */         if (itemstack.hasDisplayName())
/*     */         {
/* 342 */           j2 = 1;
/* 343 */           l1 += j2;
/* 344 */           itemstack1.clearCustomName();
/*     */         }
/*     */       
/* 347 */       } else if (!this.repairedItemName.equals(itemstack.getDisplayName())) {
/*     */         
/* 349 */         j2 = 1;
/* 350 */         l1 += j2;
/* 351 */         itemstack1.setStackDisplayName(this.repairedItemName);
/*     */       } 
/*     */       
/* 354 */       this.maximumCost = i2 + l1;
/*     */       
/* 356 */       if (l1 <= 0)
/*     */       {
/* 358 */         itemstack1 = null;
/*     */       }
/*     */       
/* 361 */       if (j2 == l1 && j2 > 0 && this.maximumCost >= 40)
/*     */       {
/* 363 */         this.maximumCost = 39;
/*     */       }
/*     */       
/* 366 */       if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode)
/*     */       {
/* 368 */         itemstack1 = null;
/*     */       }
/*     */       
/* 371 */       if (itemstack1 != null) {
/*     */         
/* 373 */         int k4 = itemstack1.getRepairCost();
/*     */         
/* 375 */         if (itemstack2 != null && k4 < itemstack2.getRepairCost())
/*     */         {
/* 377 */           k4 = itemstack2.getRepairCost();
/*     */         }
/*     */         
/* 380 */         k4 = (k4 << 1) + 1;
/* 381 */         itemstack1.setRepairCost(k4);
/* 382 */         EnchantmentHelper.setEnchantments(map, itemstack1);
/*     */       } 
/*     */       
/* 385 */       this.outputSlot.setInventorySlotContents(0, itemstack1);
/* 386 */       detectAndSendChanges();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/* 392 */     super.onCraftGuiOpened(listener);
/* 393 */     listener.sendProgressBarUpdate(this, 0, this.maximumCost);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/* 398 */     if (id == 0)
/*     */     {
/* 400 */       this.maximumCost = data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 409 */     super.onContainerClosed(playerIn);
/*     */     
/* 411 */     if (!this.theWorld.isRemote)
/*     */     {
/* 413 */       for (int i = 0; i < this.inputSlots.getSizeInventory(); i++) {
/*     */         
/* 415 */         ItemStack itemstack = this.inputSlots.removeStackFromSlot(i);
/*     */         
/* 417 */         if (itemstack != null)
/*     */         {
/* 419 */           playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 427 */     return (this.theWorld.getBlockState(this.selfPosition).getBlock() != Blocks.anvil) ? false : ((playerIn.getDistanceSq(this.selfPosition.getX() + 0.5D, this.selfPosition.getY() + 0.5D, this.selfPosition.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 435 */     ItemStack itemstack = null;
/* 436 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 438 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 440 */       ItemStack itemstack1 = slot.getStack();
/* 441 */       itemstack = itemstack1.copy();
/*     */       
/* 443 */       if (index == 2) {
/*     */         
/* 445 */         if (!mergeItemStack(itemstack1, 3, 39, true))
/*     */         {
/* 447 */           return null;
/*     */         }
/*     */         
/* 450 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/* 452 */       else if (index != 0 && index != 1) {
/*     */         
/* 454 */         if (index >= 3 && index < 39 && !mergeItemStack(itemstack1, 0, 2, false))
/*     */         {
/* 456 */           return null;
/*     */         }
/*     */       }
/* 459 */       else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/*     */         
/* 461 */         return null;
/*     */       } 
/*     */       
/* 464 */       if (itemstack1.stackSize == 0) {
/*     */         
/* 466 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else {
/*     */         
/* 470 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 473 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 475 */         return null;
/*     */       }
/*     */       
/* 478 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 481 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItemName(String newName) {
/* 489 */     this.repairedItemName = newName;
/*     */     
/* 491 */     if (getSlot(2).getHasStack()) {
/*     */       
/* 493 */       ItemStack itemstack = getSlot(2).getStack();
/*     */       
/* 495 */       if (StringUtils.isBlank(newName)) {
/*     */         
/* 497 */         itemstack.clearCustomName();
/*     */       }
/*     */       else {
/*     */         
/* 501 */         itemstack.setStackDisplayName(this.repairedItemName);
/*     */       } 
/*     */     } 
/*     */     
/* 505 */     updateRepairOutput();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\inventory\ContainerRepair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */