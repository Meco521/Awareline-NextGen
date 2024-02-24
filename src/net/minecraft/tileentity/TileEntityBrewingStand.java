/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockBrewingStand;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBrewingStand;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory {
/*  27 */   private static final int[] inputSlots = new int[] { 3 };
/*     */ 
/*     */   
/*  30 */   private static final int[] outputSlots = new int[] { 0, 1, 2 };
/*     */ 
/*     */   
/*  33 */   private ItemStack[] brewingItemStacks = new ItemStack[4];
/*     */ 
/*     */ 
/*     */   
/*     */   private int brewTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] filledSlots;
/*     */ 
/*     */   
/*     */   private Item ingredientID;
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  52 */     return hasCustomName() ? this.customName : "container.brewing";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  60 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  65 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  73 */     return this.brewingItemStacks.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  81 */     if (this.brewTime > 0) {
/*     */       
/*  83 */       this.brewTime--;
/*     */       
/*  85 */       if (this.brewTime == 0)
/*     */       {
/*  87 */         brewPotions();
/*  88 */         markDirty();
/*     */       }
/*  90 */       else if (!canBrew())
/*     */       {
/*  92 */         this.brewTime = 0;
/*  93 */         markDirty();
/*     */       }
/*  95 */       else if (this.ingredientID != this.brewingItemStacks[3].getItem())
/*     */       {
/*  97 */         this.brewTime = 0;
/*  98 */         markDirty();
/*     */       }
/*     */     
/* 101 */     } else if (canBrew()) {
/*     */       
/* 103 */       this.brewTime = 400;
/* 104 */       this.ingredientID = this.brewingItemStacks[3].getItem();
/*     */     } 
/*     */     
/* 107 */     if (!this.worldObj.isRemote) {
/*     */       
/* 109 */       boolean[] aboolean = func_174902_m();
/*     */       
/* 111 */       if (!Arrays.equals(aboolean, this.filledSlots)) {
/*     */         
/* 113 */         this.filledSlots = aboolean;
/* 114 */         IBlockState iblockstate = this.worldObj.getBlockState(getPos());
/*     */         
/* 116 */         if (!(iblockstate.getBlock() instanceof BlockBrewingStand)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 121 */         for (int i = 0; i < BlockBrewingStand.HAS_BOTTLE.length; i++)
/*     */         {
/* 123 */           iblockstate = iblockstate.withProperty((IProperty)BlockBrewingStand.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
/*     */         }
/*     */         
/* 126 */         this.worldObj.setBlockState(this.pos, iblockstate, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canBrew() {
/* 133 */     if (this.brewingItemStacks[3] != null && (this.brewingItemStacks[3]).stackSize > 0) {
/*     */       
/* 135 */       ItemStack itemstack = this.brewingItemStacks[3];
/*     */       
/* 137 */       if (!itemstack.getItem().isPotionIngredient(itemstack))
/*     */       {
/* 139 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 143 */       boolean flag = false;
/*     */       
/* 145 */       for (int i = 0; i < 3; i++) {
/*     */         
/* 147 */         if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
/*     */           
/* 149 */           int j = this.brewingItemStacks[i].getMetadata();
/* 150 */           int k = getPotionResult(j, itemstack);
/*     */           
/* 152 */           if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
/*     */             
/* 154 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/* 158 */           List<PotionEffect> list = Items.potionitem.getEffects(j);
/* 159 */           List<PotionEffect> list1 = Items.potionitem.getEffects(k);
/*     */           
/* 161 */           if ((j <= 0 || list != list1) && (list == null || (!list.equals(list1) && list1 != null)) && j != k) {
/*     */             
/* 163 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 169 */       return flag;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void brewPotions() {
/* 180 */     if (canBrew()) {
/*     */       
/* 182 */       ItemStack itemstack = this.brewingItemStacks[3];
/*     */       
/* 184 */       for (int i = 0; i < 3; i++) {
/*     */         
/* 186 */         if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
/*     */           
/* 188 */           int j = this.brewingItemStacks[i].getMetadata();
/* 189 */           int k = getPotionResult(j, itemstack);
/* 190 */           List<PotionEffect> list = Items.potionitem.getEffects(j);
/* 191 */           List<PotionEffect> list1 = Items.potionitem.getEffects(k);
/*     */           
/* 193 */           if ((j > 0 && list == list1) || (list != null && (list.equals(list1) || list1 == null))) {
/*     */             
/* 195 */             if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
/*     */             {
/* 197 */               this.brewingItemStacks[i].setItemDamage(k);
/*     */             }
/*     */           }
/* 200 */           else if (j != k) {
/*     */             
/* 202 */             this.brewingItemStacks[i].setItemDamage(k);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 207 */       if (itemstack.getItem().hasContainerItem()) {
/*     */         
/* 209 */         this.brewingItemStacks[3] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */       else {
/*     */         
/* 213 */         (this.brewingItemStacks[3]).stackSize--;
/*     */         
/* 215 */         if ((this.brewingItemStacks[3]).stackSize <= 0)
/*     */         {
/* 217 */           this.brewingItemStacks[3] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPotionResult(int meta, ItemStack stack) {
/* 228 */     return (stack == null) ? meta : (stack.getItem().isPotionIngredient(stack) ? PotionHelper.applyIngredient(meta, stack.getItem().getPotionEffect(stack)) : meta);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 233 */     super.readFromNBT(compound);
/* 234 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 235 */     this.brewingItemStacks = new ItemStack[getSizeInventory()];
/*     */     
/* 237 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 239 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 240 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/* 242 */       if (j >= 0 && j < this.brewingItemStacks.length)
/*     */       {
/* 244 */         this.brewingItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 248 */     this.brewTime = compound.getShort("BrewTime");
/*     */     
/* 250 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 252 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 258 */     super.writeToNBT(compound);
/* 259 */     compound.setShort("BrewTime", (short)this.brewTime);
/* 260 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 262 */     for (int i = 0; i < this.brewingItemStacks.length; i++) {
/*     */       
/* 264 */       if (this.brewingItemStacks[i] != null) {
/*     */         
/* 266 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 267 */         nbttagcompound.setByte("Slot", (byte)i);
/* 268 */         this.brewingItemStacks[i].writeToNBT(nbttagcompound);
/* 269 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 275 */     if (hasCustomName())
/*     */     {
/* 277 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 286 */     return (index >= 0 && index < this.brewingItemStacks.length) ? this.brewingItemStacks[index] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 294 */     if (index >= 0 && index < this.brewingItemStacks.length) {
/*     */       
/* 296 */       ItemStack itemstack = this.brewingItemStacks[index];
/* 297 */       this.brewingItemStacks[index] = null;
/* 298 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 311 */     if (index >= 0 && index < this.brewingItemStacks.length) {
/*     */       
/* 313 */       ItemStack itemstack = this.brewingItemStacks[index];
/* 314 */       this.brewingItemStacks[index] = null;
/* 315 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 328 */     if (index >= 0 && index < this.brewingItemStacks.length)
/*     */     {
/* 330 */       this.brewingItemStacks[index] = stack;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 339 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 347 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 363 */     return (index == 3) ? stack.getItem().isPotionIngredient(stack) : ((stack.getItem() == Items.potionitem || stack.getItem() == Items.glass_bottle));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] func_174902_m() {
/* 368 */     boolean[] aboolean = new boolean[3];
/*     */     
/* 370 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 372 */       if (this.brewingItemStacks[i] != null)
/*     */       {
/* 374 */         aboolean[i] = true;
/*     */       }
/*     */     } 
/*     */     
/* 378 */     return aboolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 383 */     return (side == EnumFacing.UP) ? inputSlots : outputSlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 392 */     return isItemValidForSlot(index, itemStackIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 401 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 406 */     return "minecraft:brewing_stand";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 411 */     return (Container)new ContainerBrewingStand(playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 416 */     switch (id) {
/*     */       
/*     */       case 0:
/* 419 */         return this.brewTime;
/*     */     } 
/*     */     
/* 422 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 428 */     switch (id) {
/*     */       
/*     */       case 0:
/* 431 */         this.brewTime = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 439 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 444 */     for (int i = 0; i < this.brewingItemStacks.length; i++)
/*     */     {
/* 446 */       this.brewingItemStacks[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */