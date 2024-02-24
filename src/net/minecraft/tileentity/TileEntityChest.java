/*     */ package net.minecraft.tileentity;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerChest;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityChest extends TileEntityLockable implements ITickable {
/*  20 */   private ItemStack[] chestContents = new ItemStack[27];
/*     */ 
/*     */   
/*     */   public boolean adjacentChestChecked;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZNeg;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXPos;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXNeg;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZPos;
/*     */ 
/*     */   
/*     */   public float lidAngle;
/*     */ 
/*     */   
/*     */   public float prevLidAngle;
/*     */   
/*     */   public int numPlayersUsing;
/*     */   
/*     */   private int ticksSinceSync;
/*     */   
/*     */   private int cachedChestType;
/*     */   
/*     */   private String customName;
/*     */ 
/*     */   
/*     */   public TileEntityChest() {
/*  53 */     this.cachedChestType = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityChest(int chestType) {
/*  58 */     this.cachedChestType = chestType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  66 */     return 27;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  74 */     return this.chestContents[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  82 */     if (this.chestContents[index] != null) {
/*     */       
/*  84 */       if ((this.chestContents[index]).stackSize <= count) {
/*     */         
/*  86 */         ItemStack itemstack1 = this.chestContents[index];
/*  87 */         this.chestContents[index] = null;
/*  88 */         markDirty();
/*  89 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  93 */       ItemStack itemstack = this.chestContents[index].splitStack(count);
/*     */       
/*  95 */       if ((this.chestContents[index]).stackSize == 0)
/*     */       {
/*  97 */         this.chestContents[index] = null;
/*     */       }
/*     */       
/* 100 */       markDirty();
/* 101 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 115 */     if (this.chestContents[index] != null) {
/*     */       
/* 117 */       ItemStack itemstack = this.chestContents[index];
/* 118 */       this.chestContents[index] = null;
/* 119 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 132 */     this.chestContents[index] = stack;
/*     */     
/* 134 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 136 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 139 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 147 */     return hasCustomName() ? this.customName : "container.chest";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 155 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String name) {
/* 160 */     this.customName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 165 */     super.readFromNBT(compound);
/* 166 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 167 */     this.chestContents = new ItemStack[getSizeInventory()];
/*     */     
/* 169 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 171 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */     
/* 174 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 176 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 177 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 179 */       if (j >= 0 && j < this.chestContents.length)
/*     */       {
/* 181 */         this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 188 */     super.writeToNBT(compound);
/* 189 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 191 */     for (int i = 0; i < this.chestContents.length; i++) {
/*     */       
/* 193 */       if (this.chestContents[i] != null) {
/*     */         
/* 195 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 196 */         nbttagcompound.setByte("Slot", (byte)i);
/* 197 */         this.chestContents[i].writeToNBT(nbttagcompound);
/* 198 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 204 */     if (hasCustomName())
/*     */     {
/* 206 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 215 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 223 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 228 */     super.updateContainingBlockInfo();
/* 229 */     this.adjacentChestChecked = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_174910_a(TileEntityChest chestTe, EnumFacing side) {
/* 235 */     if (chestTe.isInvalid()) {
/*     */       
/* 237 */       this.adjacentChestChecked = false;
/*     */     }
/* 239 */     else if (this.adjacentChestChecked) {
/*     */       
/* 241 */       switch (side) {
/*     */         
/*     */         case NORTH:
/* 244 */           if (this.adjacentChestZNeg != chestTe)
/*     */           {
/* 246 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case SOUTH:
/* 252 */           if (this.adjacentChestZPos != chestTe)
/*     */           {
/* 254 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case EAST:
/* 260 */           if (this.adjacentChestXPos != chestTe)
/*     */           {
/* 262 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case WEST:
/* 268 */           if (this.adjacentChestXNeg != chestTe)
/*     */           {
/* 270 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkForAdjacentChests() {
/* 281 */     if (!this.adjacentChestChecked) {
/*     */       
/* 283 */       this.adjacentChestChecked = true;
/* 284 */       this.adjacentChestXNeg = getAdjacentChest(EnumFacing.WEST);
/* 285 */       this.adjacentChestXPos = getAdjacentChest(EnumFacing.EAST);
/* 286 */       this.adjacentChestZNeg = getAdjacentChest(EnumFacing.NORTH);
/* 287 */       this.adjacentChestZPos = getAdjacentChest(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected TileEntityChest getAdjacentChest(EnumFacing side) {
/* 293 */     BlockPos blockpos = this.pos.offset(side);
/*     */     
/* 295 */     if (isChestAt(blockpos)) {
/*     */       
/* 297 */       TileEntity tileentity = this.worldObj.getTileEntity(blockpos);
/*     */       
/* 299 */       if (tileentity instanceof TileEntityChest) {
/*     */         
/* 301 */         TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 302 */         tileentitychest.func_174910_a(this, side.getOpposite());
/* 303 */         return tileentitychest;
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isChestAt(BlockPos posIn) {
/* 312 */     if (this.worldObj == null)
/*     */     {
/* 314 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 318 */     Block block = this.worldObj.getBlockState(posIn).getBlock();
/* 319 */     return (block instanceof BlockChest && ((BlockChest)block).chestType == getChestType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 328 */     checkForAdjacentChests();
/* 329 */     int i = this.pos.getX();
/* 330 */     int j = this.pos.getY();
/* 331 */     int k = this.pos.getZ();
/* 332 */     this.ticksSinceSync++;
/*     */     
/* 334 */     if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
/*     */       
/* 336 */       this.numPlayersUsing = 0;
/* 337 */       float f = 5.0F;
/*     */       
/* 339 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((i - f), (j - f), (k - f), ((i + 1) + f), ((j + 1) + f), ((k + 1) + f)))) {
/*     */         
/* 341 */         if (entityplayer.openContainer instanceof ContainerChest) {
/*     */           
/* 343 */           IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
/*     */           
/* 345 */           if (iinventory == this || (iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest((IInventory)this)))
/*     */           {
/* 347 */             this.numPlayersUsing++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 353 */     this.prevLidAngle = this.lidAngle;
/* 354 */     float f1 = 0.1F;
/*     */     
/* 356 */     if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/*     */       
/* 358 */       double d1 = i + 0.5D;
/* 359 */       double d2 = k + 0.5D;
/*     */       
/* 361 */       if (this.adjacentChestZPos != null)
/*     */       {
/* 363 */         d2 += 0.5D;
/*     */       }
/*     */       
/* 366 */       if (this.adjacentChestXPos != null)
/*     */       {
/* 368 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 371 */       this.worldObj.playSoundEffect(d1, j + 0.5D, d2, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */     
/* 374 */     if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0F) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0F)) {
/*     */       
/* 376 */       float f2 = this.lidAngle;
/*     */       
/* 378 */       if (this.numPlayersUsing > 0) {
/*     */         
/* 380 */         this.lidAngle += f1;
/*     */       }
/*     */       else {
/*     */         
/* 384 */         this.lidAngle -= f1;
/*     */       } 
/*     */       
/* 387 */       if (this.lidAngle > 1.0F)
/*     */       {
/* 389 */         this.lidAngle = 1.0F;
/*     */       }
/*     */       
/* 392 */       float f3 = 0.5F;
/*     */       
/* 394 */       if (this.lidAngle < f3 && f2 >= f3 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/*     */         
/* 396 */         double d3 = i + 0.5D;
/* 397 */         double d0 = k + 0.5D;
/*     */         
/* 399 */         if (this.adjacentChestZPos != null)
/*     */         {
/* 401 */           d0 += 0.5D;
/*     */         }
/*     */         
/* 404 */         if (this.adjacentChestXPos != null)
/*     */         {
/* 406 */           d3 += 0.5D;
/*     */         }
/*     */         
/* 409 */         this.worldObj.playSoundEffect(d3, j + 0.5D, d0, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */       
/* 412 */       if (this.lidAngle < 0.0F)
/*     */       {
/* 414 */         this.lidAngle = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 421 */     if (id == 1) {
/*     */       
/* 423 */       this.numPlayersUsing = type;
/* 424 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 428 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 434 */     if (!player.isSpectator()) {
/*     */       
/* 436 */       if (this.numPlayersUsing < 0)
/*     */       {
/* 438 */         this.numPlayersUsing = 0;
/*     */       }
/*     */       
/* 441 */       this.numPlayersUsing++;
/* 442 */       this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 443 */       this.worldObj.notifyNeighborsOfStateChange(this.pos, getBlockType());
/* 444 */       this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), getBlockType());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 450 */     if (!player.isSpectator() && getBlockType() instanceof BlockChest) {
/*     */       
/* 452 */       this.numPlayersUsing--;
/* 453 */       this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 454 */       this.worldObj.notifyNeighborsOfStateChange(this.pos, getBlockType());
/* 455 */       this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), getBlockType());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 464 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 472 */     super.invalidate();
/* 473 */     updateContainingBlockInfo();
/* 474 */     checkForAdjacentChests();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChestType() {
/* 479 */     if (this.cachedChestType == -1) {
/*     */       
/* 481 */       if (this.worldObj == null || !(getBlockType() instanceof BlockChest))
/*     */       {
/* 483 */         return 0;
/*     */       }
/*     */       
/* 486 */       this.cachedChestType = ((BlockChest)getBlockType()).chestType;
/*     */     } 
/*     */     
/* 489 */     return this.cachedChestType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 494 */     return "minecraft:chest";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 499 */     return (Container)new ContainerChest((IInventory)playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 504 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 513 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 518 */     for (int i = 0; i < this.chestContents.length; i++)
/*     */     {
/* 520 */       this.chestContents[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */