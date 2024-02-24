/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ 
/*     */ public class ContainerFurnace
/*     */   extends Container
/*     */ {
/*     */   private final IInventory tileFurnace;
/*     */   private int cookTime;
/*     */   private int totalCookTime;
/*     */   private int furnaceBurnTime;
/*     */   private int currentItemBurnTime;
/*     */   
/*     */   public ContainerFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory) {
/*  19 */     this.tileFurnace = furnaceInventory;
/*  20 */     addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17));
/*  21 */     addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 56, 53));
/*  22 */     addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 116, 35));
/*     */     
/*  24 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  26 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  28 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  32 */     for (int k = 0; k < 9; k++)
/*     */     {
/*  34 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  40 */     super.onCraftGuiOpened(listener);
/*  41 */     listener.sendAllWindowProperties(this, this.tileFurnace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  49 */     super.detectAndSendChanges();
/*     */     
/*  51 */     for (int i = 0; i < this.crafters.size(); i++) {
/*     */       
/*  53 */       ICrafting icrafting = this.crafters.get(i);
/*     */       
/*  55 */       if (this.cookTime != this.tileFurnace.getField(2))
/*     */       {
/*  57 */         icrafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
/*     */       }
/*     */       
/*  60 */       if (this.furnaceBurnTime != this.tileFurnace.getField(0))
/*     */       {
/*  62 */         icrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
/*     */       }
/*     */       
/*  65 */       if (this.currentItemBurnTime != this.tileFurnace.getField(1))
/*     */       {
/*  67 */         icrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
/*     */       }
/*     */       
/*  70 */       if (this.totalCookTime != this.tileFurnace.getField(3))
/*     */       {
/*  72 */         icrafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
/*     */       }
/*     */     } 
/*     */     
/*  76 */     this.cookTime = this.tileFurnace.getField(2);
/*  77 */     this.furnaceBurnTime = this.tileFurnace.getField(0);
/*  78 */     this.currentItemBurnTime = this.tileFurnace.getField(1);
/*  79 */     this.totalCookTime = this.tileFurnace.getField(3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/*  84 */     this.tileFurnace.setField(id, data);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  89 */     return this.tileFurnace.isUseableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  97 */     ItemStack itemstack = null;
/*  98 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 100 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 102 */       ItemStack itemstack1 = slot.getStack();
/* 103 */       itemstack = itemstack1.copy();
/*     */       
/* 105 */       if (index == 2) {
/*     */         
/* 107 */         if (!mergeItemStack(itemstack1, 3, 39, true))
/*     */         {
/* 109 */           return null;
/*     */         }
/*     */         
/* 112 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/* 114 */       else if (index != 1 && index != 0) {
/*     */         
/* 116 */         if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null)
/*     */         {
/* 118 */           if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */           {
/* 120 */             return null;
/*     */           }
/*     */         }
/* 123 */         else if (TileEntityFurnace.isItemFuel(itemstack1))
/*     */         {
/* 125 */           if (!mergeItemStack(itemstack1, 1, 2, false))
/*     */           {
/* 127 */             return null;
/*     */           }
/*     */         }
/* 130 */         else if (index >= 3 && index < 30)
/*     */         {
/* 132 */           if (!mergeItemStack(itemstack1, 30, 39, false))
/*     */           {
/* 134 */             return null;
/*     */           }
/*     */         }
/* 137 */         else if (index >= 30 && index < 39 && !mergeItemStack(itemstack1, 3, 30, false))
/*     */         {
/* 139 */           return null;
/*     */         }
/*     */       
/* 142 */       } else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/*     */         
/* 144 */         return null;
/*     */       } 
/*     */       
/* 147 */       if (itemstack1.stackSize == 0) {
/*     */         
/* 149 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else {
/*     */         
/* 153 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 156 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 158 */         return null;
/*     */       }
/*     */       
/* 161 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 164 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\inventory\ContainerFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */