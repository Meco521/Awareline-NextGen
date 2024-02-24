/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ public class InventoryBasic
/*     */   implements IInventory
/*     */ {
/*     */   private String inventoryTitle;
/*     */   private final int slotsCount;
/*     */   private final ItemStack[] inventoryContents;
/*     */   private List<IInvBasic> changeListeners;
/*     */   private boolean hasCustomName;
/*     */   
/*     */   public InventoryBasic(String title, boolean customName, int slotCount) {
/*  22 */     this.inventoryTitle = title;
/*  23 */     this.hasCustomName = customName;
/*  24 */     this.slotsCount = slotCount;
/*  25 */     this.inventoryContents = new ItemStack[slotCount];
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryBasic(IChatComponent title, int slotCount) {
/*  30 */     this(title.getUnformattedText(), true, slotCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInventoryChangeListener(IInvBasic listener) {
/*  40 */     if (this.changeListeners == null)
/*     */     {
/*  42 */       this.changeListeners = Lists.newArrayList();
/*     */     }
/*     */     
/*  45 */     this.changeListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeInventoryChangeListener(IInvBasic listener) {
/*  55 */     this.changeListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  63 */     return (index >= 0 && index < this.inventoryContents.length) ? this.inventoryContents[index] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  71 */     if (this.inventoryContents[index] != null) {
/*     */       
/*  73 */       if ((this.inventoryContents[index]).stackSize <= count) {
/*     */         
/*  75 */         ItemStack itemstack1 = this.inventoryContents[index];
/*  76 */         this.inventoryContents[index] = null;
/*  77 */         markDirty();
/*  78 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  82 */       ItemStack itemstack = this.inventoryContents[index].splitStack(count);
/*     */       
/*  84 */       if ((this.inventoryContents[index]).stackSize == 0)
/*     */       {
/*  86 */         this.inventoryContents[index] = null;
/*     */       }
/*     */       
/*  89 */       markDirty();
/*  90 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_174894_a(ItemStack stack) {
/* 101 */     ItemStack itemstack = stack.copy();
/*     */     
/* 103 */     for (int i = 0; i < this.slotsCount; i++) {
/*     */       
/* 105 */       ItemStack itemstack1 = getStackInSlot(i);
/*     */       
/* 107 */       if (itemstack1 == null) {
/*     */         
/* 109 */         setInventorySlotContents(i, itemstack);
/* 110 */         markDirty();
/* 111 */         return null;
/*     */       } 
/*     */       
/* 114 */       if (ItemStack.areItemsEqual(itemstack1, itemstack)) {
/*     */         
/* 116 */         int j = Math.min(getInventoryStackLimit(), itemstack1.getMaxStackSize());
/* 117 */         int k = Math.min(itemstack.stackSize, j - itemstack1.stackSize);
/*     */         
/* 119 */         if (k > 0) {
/*     */           
/* 121 */           itemstack1.stackSize += k;
/* 122 */           itemstack.stackSize -= k;
/*     */           
/* 124 */           if (itemstack.stackSize <= 0) {
/*     */             
/* 126 */             markDirty();
/* 127 */             return null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     if (itemstack.stackSize != stack.stackSize)
/*     */     {
/* 135 */       markDirty();
/*     */     }
/*     */     
/* 138 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 146 */     if (this.inventoryContents[index] != null) {
/*     */       
/* 148 */       ItemStack itemstack = this.inventoryContents[index];
/* 149 */       this.inventoryContents[index] = null;
/* 150 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 163 */     this.inventoryContents[index] = stack;
/*     */     
/* 165 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 167 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 170 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 178 */     return this.slotsCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 186 */     return this.inventoryTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 194 */     return this.hasCustomName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomName(String inventoryTitleIn) {
/* 202 */     this.hasCustomName = true;
/* 203 */     this.inventoryTitle = inventoryTitleIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 211 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(this.inventoryTitle) : (IChatComponent)new ChatComponentTranslation(this.inventoryTitle, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 219 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 228 */     if (this.changeListeners != null)
/*     */     {
/* 230 */       for (int i = 0; i < this.changeListeners.size(); i++)
/*     */       {
/* 232 */         ((IInvBasic)this.changeListeners.get(i)).onInventoryChanged(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 242 */     return true;
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
/* 258 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 263 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 272 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 277 */     for (int i = 0; i < this.inventoryContents.length; i++)
/*     */     {
/* 279 */       this.inventoryContents[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\inventory\InventoryBasic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */