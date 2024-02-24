/*     */ package net.minecraft.tileentity;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityHopper extends TileEntityLockable implements IHopper, ITickable {
/*  24 */   private ItemStack[] inventory = new ItemStack[5];
/*     */   private String customName;
/*  26 */   public int transferCooldown = -1;
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  30 */     super.readFromNBT(compound);
/*  31 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/*  32 */     this.inventory = new ItemStack[getSizeInventory()];
/*     */     
/*  34 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/*  36 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */     
/*  39 */     this.transferCooldown = compound.getInteger("TransferCooldown");
/*     */     
/*  41 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  43 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  44 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/*  46 */       if (j >= 0 && j < this.inventory.length)
/*     */       {
/*  48 */         this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  55 */     super.writeToNBT(compound);
/*  56 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  58 */     for (int i = 0; i < this.inventory.length; i++) {
/*     */       
/*  60 */       if (this.inventory[i] != null) {
/*     */         
/*  62 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  63 */         nbttagcompound.setByte("Slot", (byte)i);
/*  64 */         this.inventory[i].writeToNBT(nbttagcompound);
/*  65 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*  70 */     compound.setInteger("TransferCooldown", this.transferCooldown);
/*     */     
/*  72 */     if (hasCustomName())
/*     */     {
/*  74 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/*  84 */     super.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  92 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 100 */     return this.inventory[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 108 */     if (this.inventory[index] != null) {
/*     */       
/* 110 */       if ((this.inventory[index]).stackSize <= count) {
/*     */         
/* 112 */         ItemStack itemstack1 = this.inventory[index];
/* 113 */         this.inventory[index] = null;
/* 114 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/* 118 */       ItemStack itemstack = this.inventory[index].splitStack(count);
/*     */       
/* 120 */       if ((this.inventory[index]).stackSize == 0)
/*     */       {
/* 122 */         this.inventory[index] = null;
/*     */       }
/*     */       
/* 125 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 139 */     if (this.inventory[index] != null) {
/*     */       
/* 141 */       ItemStack itemstack = this.inventory[index];
/* 142 */       this.inventory[index] = null;
/* 143 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 147 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 156 */     this.inventory[index] = stack;
/*     */     
/* 158 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 160 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 169 */     return hasCustomName() ? this.customName : "container.hopper";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 177 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String customNameIn) {
/* 182 */     this.customName = customNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 190 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 198 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 222 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/*     */       
/* 224 */       this.transferCooldown--;
/*     */       
/* 226 */       if (!isOnTransferCooldown()) {
/*     */         
/* 228 */         this.transferCooldown = 0;
/* 229 */         updateHopper();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateHopper() {
/* 236 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/*     */       
/* 238 */       if (!isOnTransferCooldown() && BlockHopper.isEnabled(getBlockMetadata())) {
/*     */         
/* 240 */         boolean flag = false;
/*     */         
/* 242 */         if (!isEmpty())
/*     */         {
/* 244 */           flag = transferItemsOut();
/*     */         }
/*     */         
/* 247 */         if (!isFull())
/*     */         {
/* 249 */           flag = (captureDroppedItems(this) || flag);
/*     */         }
/*     */         
/* 252 */         if (flag) {
/*     */           
/* 254 */           this.transferCooldown = 8;
/* 255 */           markDirty();
/* 256 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 260 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEmpty() {
/* 270 */     for (ItemStack itemstack : this.inventory) {
/*     */       
/* 272 */       if (itemstack != null)
/*     */       {
/* 274 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 278 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFull() {
/* 283 */     for (ItemStack itemstack : this.inventory) {
/*     */       
/* 285 */       if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize())
/*     */       {
/* 287 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean transferItemsOut() {
/* 296 */     IInventory iinventory = getInventoryForHopperTransfer();
/*     */     
/* 298 */     if (iinventory == null)
/*     */     {
/* 300 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 304 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata()).getOpposite();
/*     */     
/* 306 */     if (isInventoryFull(iinventory, enumfacing))
/*     */     {
/* 308 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 312 */     for (int i = 0; i < getSizeInventory(); i++) {
/*     */       
/* 314 */       if (getStackInSlot(i) != null) {
/*     */         
/* 316 */         ItemStack itemstack = getStackInSlot(i).copy();
/* 317 */         ItemStack itemstack1 = putStackInInventoryAllSlots(iinventory, decrStackSize(i, 1), enumfacing);
/*     */         
/* 319 */         if (itemstack1 == null || itemstack1.stackSize == 0) {
/*     */           
/* 321 */           iinventory.markDirty();
/* 322 */           return true;
/*     */         } 
/*     */         
/* 325 */         setInventorySlotContents(i, itemstack);
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInventoryFull(IInventory inventoryIn, EnumFacing side) {
/* 339 */     if (inventoryIn instanceof ISidedInventory) {
/*     */       
/* 341 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 342 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 344 */       for (int k = 0; k < aint.length; k++)
/*     */       {
/* 346 */         ItemStack itemstack1 = isidedinventory.getStackInSlot(aint[k]);
/*     */         
/* 348 */         if (itemstack1 == null || itemstack1.stackSize != itemstack1.getMaxStackSize())
/*     */         {
/* 350 */           return false;
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 356 */       int i = inventoryIn.getSizeInventory();
/*     */       
/* 358 */       for (int j = 0; j < i; j++) {
/*     */         
/* 360 */         ItemStack itemstack = inventoryIn.getStackInSlot(j);
/*     */         
/* 362 */         if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize())
/*     */         {
/* 364 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 369 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isInventoryEmpty(IInventory inventoryIn, EnumFacing side) {
/* 377 */     if (inventoryIn instanceof ISidedInventory) {
/*     */       
/* 379 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 380 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 382 */       for (int i = 0; i < aint.length; i++)
/*     */       {
/* 384 */         if (isidedinventory.getStackInSlot(aint[i]) != null)
/*     */         {
/* 386 */           return false;
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 392 */       int j = inventoryIn.getSizeInventory();
/*     */       
/* 394 */       for (int k = 0; k < j; k++) {
/*     */         
/* 396 */         if (inventoryIn.getStackInSlot(k) != null)
/*     */         {
/* 398 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 403 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean captureDroppedItems(IHopper p_145891_0_) {
/* 408 */     IInventory iinventory = getHopperInventory(p_145891_0_);
/*     */     
/* 410 */     if (iinventory != null) {
/*     */       
/* 412 */       EnumFacing enumfacing = EnumFacing.DOWN;
/*     */       
/* 414 */       if (isInventoryEmpty(iinventory, enumfacing))
/*     */       {
/* 416 */         return false;
/*     */       }
/*     */       
/* 419 */       if (iinventory instanceof ISidedInventory) {
/*     */         
/* 421 */         ISidedInventory isidedinventory = (ISidedInventory)iinventory;
/* 422 */         int[] aint = isidedinventory.getSlotsForFace(enumfacing);
/*     */         
/* 424 */         for (int i = 0; i < aint.length; i++)
/*     */         {
/* 426 */           if (pullItemFromSlot(p_145891_0_, iinventory, aint[i], enumfacing))
/*     */           {
/* 428 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 434 */         int j = iinventory.getSizeInventory();
/*     */         
/* 436 */         for (int k = 0; k < j; k++)
/*     */         {
/* 438 */           if (pullItemFromSlot(p_145891_0_, iinventory, k, enumfacing))
/*     */           {
/* 440 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 447 */       for (EntityItem entityitem : func_181556_a(p_145891_0_.getWorld(), p_145891_0_.getXPos(), p_145891_0_.getYPos() + 1.0D, p_145891_0_.getZPos())) {
/*     */         
/* 449 */         if (putDropInInventoryAllSlots(p_145891_0_, entityitem))
/*     */         {
/* 451 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 456 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean pullItemFromSlot(IHopper hopper, IInventory inventoryIn, int index, EnumFacing direction) {
/* 465 */     ItemStack itemstack = inventoryIn.getStackInSlot(index);
/*     */     
/* 467 */     if (itemstack != null && canExtractItemFromSlot(inventoryIn, itemstack, index, direction)) {
/*     */       
/* 469 */       ItemStack itemstack1 = itemstack.copy();
/* 470 */       ItemStack itemstack2 = putStackInInventoryAllSlots(hopper, inventoryIn.decrStackSize(index, 1), (EnumFacing)null);
/*     */       
/* 472 */       if (itemstack2 == null || itemstack2.stackSize == 0) {
/*     */         
/* 474 */         inventoryIn.markDirty();
/* 475 */         return true;
/*     */       } 
/*     */       
/* 478 */       inventoryIn.setInventorySlotContents(index, itemstack1);
/*     */     } 
/*     */     
/* 481 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean putDropInInventoryAllSlots(IInventory p_145898_0_, EntityItem itemIn) {
/* 490 */     boolean flag = false;
/*     */     
/* 492 */     if (itemIn == null)
/*     */     {
/* 494 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 498 */     ItemStack itemstack = itemIn.getEntityItem().copy();
/* 499 */     ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemstack, (EnumFacing)null);
/*     */     
/* 501 */     if (itemstack1 != null && itemstack1.stackSize != 0) {
/*     */       
/* 503 */       itemIn.setEntityItemStack(itemstack1);
/*     */     }
/*     */     else {
/*     */       
/* 507 */       flag = true;
/* 508 */       itemIn.setDead();
/*     */     } 
/*     */     
/* 511 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, ItemStack stack, EnumFacing side) {
/* 520 */     if (inventoryIn instanceof ISidedInventory && side != null) {
/*     */       
/* 522 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 523 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 525 */       for (int k = 0; k < aint.length && stack != null && stack.stackSize > 0; k++)
/*     */       {
/* 527 */         stack = insertStack(inventoryIn, stack, aint[k], side);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 532 */       int i = inventoryIn.getSizeInventory();
/*     */       
/* 534 */       for (int j = 0; j < i && stack != null && stack.stackSize > 0; j++)
/*     */       {
/* 536 */         stack = insertStack(inventoryIn, stack, j, side);
/*     */       }
/*     */     } 
/*     */     
/* 540 */     if (stack != null && stack.stackSize == 0)
/*     */     {
/* 542 */       stack = null;
/*     */     }
/*     */     
/* 545 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 553 */     return !inventoryIn.isItemValidForSlot(index, stack) ? false : ((!(inventoryIn instanceof ISidedInventory) || ((ISidedInventory)inventoryIn).canInsertItem(index, stack, side)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canExtractItemFromSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 561 */     return (!(inventoryIn instanceof ISidedInventory) || ((ISidedInventory)inventoryIn).canExtractItem(index, stack, side));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ItemStack insertStack(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 569 */     ItemStack itemstack = inventoryIn.getStackInSlot(index);
/*     */     
/* 571 */     if (canInsertItemInSlot(inventoryIn, stack, index, side)) {
/*     */       
/* 573 */       boolean flag = false;
/*     */       
/* 575 */       if (itemstack == null) {
/*     */         
/* 577 */         inventoryIn.setInventorySlotContents(index, stack);
/* 578 */         stack = null;
/* 579 */         flag = true;
/*     */       }
/* 581 */       else if (canCombine(itemstack, stack)) {
/*     */         
/* 583 */         int i = stack.getMaxStackSize() - itemstack.stackSize;
/* 584 */         int j = Math.min(stack.stackSize, i);
/* 585 */         stack.stackSize -= j;
/* 586 */         itemstack.stackSize += j;
/* 587 */         flag = (j > 0);
/*     */       } 
/*     */       
/* 590 */       if (flag) {
/*     */         
/* 592 */         if (inventoryIn instanceof TileEntityHopper) {
/*     */           
/* 594 */           TileEntityHopper tileentityhopper = (TileEntityHopper)inventoryIn;
/*     */           
/* 596 */           if (tileentityhopper.mayTransfer())
/*     */           {
/* 598 */             tileentityhopper.transferCooldown = 8;
/*     */           }
/*     */           
/* 601 */           inventoryIn.markDirty();
/*     */         } 
/*     */         
/* 604 */         inventoryIn.markDirty();
/*     */       } 
/*     */     } 
/*     */     
/* 608 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IInventory getInventoryForHopperTransfer() {
/* 616 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata());
/* 617 */     return getInventoryAtPosition(getWorld(), (this.pos.getX() + enumfacing.getFrontOffsetX()), (this.pos.getY() + enumfacing.getFrontOffsetY()), (this.pos.getZ() + enumfacing.getFrontOffsetZ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getHopperInventory(IHopper hopper) {
/* 625 */     return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EntityItem> func_181556_a(World p_181556_0_, double p_181556_1_, double p_181556_3_, double p_181556_5_) {
/* 630 */     return p_181556_0_.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(p_181556_1_ - 0.5D, p_181556_3_ - 0.5D, p_181556_5_ - 0.5D, p_181556_1_ + 0.5D, p_181556_3_ + 0.5D, p_181556_5_ + 0.5D), EntitySelectors.selectAnything);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
/*     */     ILockableContainer iLockableContainer;
/* 638 */     IInventory iInventory1, iinventory = null;
/* 639 */     int i = MathHelper.floor_double(x);
/* 640 */     int j = MathHelper.floor_double(y);
/* 641 */     int k = MathHelper.floor_double(z);
/* 642 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 643 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 645 */     if (block.hasTileEntity()) {
/*     */       
/* 647 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 649 */       if (tileentity instanceof IInventory) {
/*     */         
/* 651 */         iinventory = (IInventory)tileentity;
/*     */         
/* 653 */         if (iinventory instanceof TileEntityChest && block instanceof BlockChest)
/*     */         {
/* 655 */           iLockableContainer = ((BlockChest)block).getLockableContainer(worldIn, blockpos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 660 */     if (iLockableContainer == null) {
/*     */       
/* 662 */       List<Entity> list = worldIn.getEntitiesInAABBexcluding((Entity)null, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelectors.selectInventories);
/*     */       
/* 664 */       if (!list.isEmpty())
/*     */       {
/* 666 */         iInventory1 = (IInventory)list.get(worldIn.rand.nextInt(list.size()));
/*     */       }
/*     */     } 
/*     */     
/* 670 */     return iInventory1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
/* 675 */     return (stack1.getItem() != stack2.getItem()) ? false : ((stack1.getMetadata() != stack2.getMetadata()) ? false : ((stack1.stackSize > stack1.getMaxStackSize()) ? false : ItemStack.areItemStackTagsEqual(stack1, stack2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXPos() {
/* 683 */     return this.pos.getX() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYPos() {
/* 691 */     return this.pos.getY() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZPos() {
/* 699 */     return this.pos.getZ() + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransferCooldown(int ticks) {
/* 704 */     this.transferCooldown = ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnTransferCooldown() {
/* 709 */     return (this.transferCooldown > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mayTransfer() {
/* 714 */     return (this.transferCooldown <= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 719 */     return "minecraft:hopper";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 724 */     return (Container)new ContainerHopper(playerInventory, this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 729 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 738 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 743 */     for (int i = 0; i < this.inventory.length; i++)
/*     */     {
/* 745 */       this.inventory[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */