/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemDoor;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StructureComponent
/*     */ {
/*     */   protected StructureBoundingBox boundingBox;
/*     */   protected EnumFacing coordBaseMode;
/*     */   protected int componentType;
/*     */   
/*     */   public StructureComponent() {}
/*     */   
/*     */   protected StructureComponent(int type) {
/*  38 */     this.componentType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound createStructureBaseNBT() {
/*  49 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  50 */     nbttagcompound.setString("id", MapGenStructureIO.getStructureComponentName(this));
/*  51 */     nbttagcompound.setTag("BB", (NBTBase)this.boundingBox.toNBTTagIntArray());
/*  52 */     nbttagcompound.setInteger("O", (this.coordBaseMode == null) ? -1 : this.coordBaseMode.getHorizontalIndex());
/*  53 */     nbttagcompound.setInteger("GD", this.componentType);
/*  54 */     writeStructureToNBT(nbttagcompound);
/*  55 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void writeStructureToNBT(NBTTagCompound paramNBTTagCompound);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readStructureBaseNBT(World worldIn, NBTTagCompound tagCompound) {
/*  70 */     if (tagCompound.hasKey("BB"))
/*     */     {
/*  72 */       this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/*  75 */     int i = tagCompound.getInteger("O");
/*  76 */     this.coordBaseMode = (i == -1) ? null : EnumFacing.getHorizontal(i);
/*  77 */     this.componentType = tagCompound.getInteger("GD");
/*  78 */     readStructureFromNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void readStructureFromNBT(NBTTagCompound paramNBTTagCompound);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean addComponentParts(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureBoundingBox getBoundingBox() {
/* 101 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentType() {
/* 109 */     return this.componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureComponent findIntersecting(List<StructureComponent> listIn, StructureBoundingBox boundingboxIn) {
/* 117 */     for (StructureComponent structurecomponent : listIn) {
/*     */       
/* 119 */       if (structurecomponent.boundingBox != null && structurecomponent.boundingBox.intersectsWith(boundingboxIn))
/*     */       {
/* 121 */         return structurecomponent;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getBoundingBoxCenter() {
/* 130 */     return new BlockPos(this.boundingBox.getCenter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLiquidInStructureBoundingBox(World worldIn, StructureBoundingBox boundingboxIn) {
/* 138 */     int i = Math.max(this.boundingBox.minX - 1, boundingboxIn.minX);
/* 139 */     int j = Math.max(this.boundingBox.minY - 1, boundingboxIn.minY);
/* 140 */     int k = Math.max(this.boundingBox.minZ - 1, boundingboxIn.minZ);
/* 141 */     int l = Math.min(this.boundingBox.maxX + 1, boundingboxIn.maxX);
/* 142 */     int i1 = Math.min(this.boundingBox.maxY + 1, boundingboxIn.maxY);
/* 143 */     int j1 = Math.min(this.boundingBox.maxZ + 1, boundingboxIn.maxZ);
/* 144 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 146 */     for (int k1 = i; k1 <= l; k1++) {
/*     */       
/* 148 */       for (int l1 = k; l1 <= j1; l1++) {
/*     */         
/* 150 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, j, l1)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 152 */           return true;
/*     */         }
/*     */         
/* 155 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, i1, l1)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 157 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     for (int i2 = i; i2 <= l; i2++) {
/*     */       
/* 164 */       for (int k2 = j; k2 <= i1; k2++) {
/*     */         
/* 166 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(i2, k2, k)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 168 */           return true;
/*     */         }
/*     */         
/* 171 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(i2, k2, j1)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 173 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     for (int j2 = k; j2 <= j1; j2++) {
/*     */       
/* 180 */       for (int l2 = j; l2 <= i1; l2++) {
/*     */         
/* 182 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(i, l2, j2)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 184 */           return true;
/*     */         }
/*     */         
/* 187 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, l2, j2)).getBlock().getMaterial().isLiquid())
/*     */         {
/* 189 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getXWithOffset(int x, int z) {
/* 199 */     if (this.coordBaseMode == null)
/*     */     {
/* 201 */       return x;
/*     */     }
/*     */ 
/*     */     
/* 205 */     switch (this.coordBaseMode) {
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 209 */         return this.boundingBox.minX + x;
/*     */       
/*     */       case WEST:
/* 212 */         return this.boundingBox.maxX - z;
/*     */       
/*     */       case EAST:
/* 215 */         return this.boundingBox.minX + z;
/*     */     } 
/*     */     
/* 218 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getYWithOffset(int y) {
/* 225 */     return (this.coordBaseMode == null) ? y : (y + this.boundingBox.minY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getZWithOffset(int x, int z) {
/* 230 */     if (this.coordBaseMode == null)
/*     */     {
/* 232 */       return z;
/*     */     }
/*     */ 
/*     */     
/* 236 */     switch (this.coordBaseMode) {
/*     */       
/*     */       case NORTH:
/* 239 */         return this.boundingBox.maxZ - z;
/*     */       
/*     */       case SOUTH:
/* 242 */         return this.boundingBox.minZ + z;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 246 */         return this.boundingBox.minZ + x;
/*     */     } 
/*     */     
/* 249 */     return z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMetadataWithOffset(Block blockIn, int meta) {
/* 259 */     if (blockIn == Blocks.rail) {
/*     */       
/* 261 */       if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.EAST)
/*     */       {
/* 263 */         if (meta == 1)
/*     */         {
/* 265 */           return 0;
/*     */         }
/*     */         
/* 268 */         return 1;
/*     */       }
/*     */     
/* 271 */     } else if (blockIn instanceof net.minecraft.block.BlockDoor) {
/*     */       
/* 273 */       if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */       {
/* 275 */         if (meta == 0)
/*     */         {
/* 277 */           return 2;
/*     */         }
/*     */         
/* 280 */         if (meta == 2)
/*     */         {
/* 282 */           return 0;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 287 */         if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 289 */           return meta + 1 & 0x3;
/*     */         }
/*     */         
/* 292 */         if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 294 */           return meta + 3 & 0x3;
/*     */         }
/*     */       }
/*     */     
/* 298 */     } else if (blockIn != Blocks.stone_stairs && blockIn != Blocks.oak_stairs && blockIn != Blocks.nether_brick_stairs && blockIn != Blocks.stone_brick_stairs && blockIn != Blocks.sandstone_stairs) {
/*     */       
/* 300 */       if (blockIn == Blocks.ladder) {
/*     */         
/* 302 */         if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */         {
/* 304 */           if (meta == EnumFacing.NORTH.getIndex())
/*     */           {
/* 306 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */           
/* 309 */           if (meta == EnumFacing.SOUTH.getIndex())
/*     */           {
/* 311 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */         }
/* 314 */         else if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 316 */           if (meta == EnumFacing.NORTH.getIndex())
/*     */           {
/* 318 */             return EnumFacing.WEST.getIndex();
/*     */           }
/*     */           
/* 321 */           if (meta == EnumFacing.SOUTH.getIndex())
/*     */           {
/* 323 */             return EnumFacing.EAST.getIndex();
/*     */           }
/*     */           
/* 326 */           if (meta == EnumFacing.WEST.getIndex())
/*     */           {
/* 328 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */           
/* 331 */           if (meta == EnumFacing.EAST.getIndex())
/*     */           {
/* 333 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */         }
/* 336 */         else if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 338 */           if (meta == EnumFacing.NORTH.getIndex())
/*     */           {
/* 340 */             return EnumFacing.EAST.getIndex();
/*     */           }
/*     */           
/* 343 */           if (meta == EnumFacing.SOUTH.getIndex())
/*     */           {
/* 345 */             return EnumFacing.WEST.getIndex();
/*     */           }
/*     */           
/* 348 */           if (meta == EnumFacing.WEST.getIndex())
/*     */           {
/* 350 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */           
/* 353 */           if (meta == EnumFacing.EAST.getIndex())
/*     */           {
/* 355 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */         }
/*     */       
/* 359 */       } else if (blockIn == Blocks.stone_button) {
/*     */         
/* 361 */         if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */         {
/* 363 */           if (meta == 3)
/*     */           {
/* 365 */             return 4;
/*     */           }
/*     */           
/* 368 */           if (meta == 4)
/*     */           {
/* 370 */             return 3;
/*     */           }
/*     */         }
/* 373 */         else if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 375 */           if (meta == 3)
/*     */           {
/* 377 */             return 1;
/*     */           }
/*     */           
/* 380 */           if (meta == 4)
/*     */           {
/* 382 */             return 2;
/*     */           }
/*     */           
/* 385 */           if (meta == 2)
/*     */           {
/* 387 */             return 3;
/*     */           }
/*     */           
/* 390 */           if (meta == 1)
/*     */           {
/* 392 */             return 4;
/*     */           }
/*     */         }
/* 395 */         else if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 397 */           if (meta == 3)
/*     */           {
/* 399 */             return 2;
/*     */           }
/*     */           
/* 402 */           if (meta == 4)
/*     */           {
/* 404 */             return 1;
/*     */           }
/*     */           
/* 407 */           if (meta == 2)
/*     */           {
/* 409 */             return 3;
/*     */           }
/*     */           
/* 412 */           if (meta == 1)
/*     */           {
/* 414 */             return 4;
/*     */           }
/*     */         }
/*     */       
/* 418 */       } else if (blockIn != Blocks.tripwire_hook && !(blockIn instanceof net.minecraft.block.BlockDirectional)) {
/*     */         
/* 420 */         if (blockIn == Blocks.piston || blockIn == Blocks.sticky_piston || blockIn == Blocks.lever || blockIn == Blocks.dispenser)
/*     */         {
/* 422 */           if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */           {
/* 424 */             if (meta == EnumFacing.NORTH.getIndex() || meta == EnumFacing.SOUTH.getIndex())
/*     */             {
/* 426 */               return EnumFacing.getFront(meta).getOpposite().getIndex();
/*     */             }
/*     */           }
/* 429 */           else if (this.coordBaseMode == EnumFacing.WEST)
/*     */           {
/* 431 */             if (meta == EnumFacing.NORTH.getIndex())
/*     */             {
/* 433 */               return EnumFacing.WEST.getIndex();
/*     */             }
/*     */             
/* 436 */             if (meta == EnumFacing.SOUTH.getIndex())
/*     */             {
/* 438 */               return EnumFacing.EAST.getIndex();
/*     */             }
/*     */             
/* 441 */             if (meta == EnumFacing.WEST.getIndex())
/*     */             {
/* 443 */               return EnumFacing.NORTH.getIndex();
/*     */             }
/*     */             
/* 446 */             if (meta == EnumFacing.EAST.getIndex())
/*     */             {
/* 448 */               return EnumFacing.SOUTH.getIndex();
/*     */             }
/*     */           }
/* 451 */           else if (this.coordBaseMode == EnumFacing.EAST)
/*     */           {
/* 453 */             if (meta == EnumFacing.NORTH.getIndex())
/*     */             {
/* 455 */               return EnumFacing.EAST.getIndex();
/*     */             }
/*     */             
/* 458 */             if (meta == EnumFacing.SOUTH.getIndex())
/*     */             {
/* 460 */               return EnumFacing.WEST.getIndex();
/*     */             }
/*     */             
/* 463 */             if (meta == EnumFacing.WEST.getIndex())
/*     */             {
/* 465 */               return EnumFacing.NORTH.getIndex();
/*     */             }
/*     */             
/* 468 */             if (meta == EnumFacing.EAST.getIndex())
/*     */             {
/* 470 */               return EnumFacing.SOUTH.getIndex();
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       } else {
/*     */         
/* 477 */         EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/*     */         
/* 479 */         if (this.coordBaseMode == EnumFacing.SOUTH)
/*     */         {
/* 481 */           if (enumfacing == EnumFacing.SOUTH || enumfacing == EnumFacing.NORTH)
/*     */           {
/* 483 */             return enumfacing.getOpposite().getHorizontalIndex();
/*     */           }
/*     */         }
/* 486 */         else if (this.coordBaseMode == EnumFacing.WEST)
/*     */         {
/* 488 */           if (enumfacing == EnumFacing.NORTH)
/*     */           {
/* 490 */             return EnumFacing.WEST.getHorizontalIndex();
/*     */           }
/*     */           
/* 493 */           if (enumfacing == EnumFacing.SOUTH)
/*     */           {
/* 495 */             return EnumFacing.EAST.getHorizontalIndex();
/*     */           }
/*     */           
/* 498 */           if (enumfacing == EnumFacing.WEST)
/*     */           {
/* 500 */             return EnumFacing.NORTH.getHorizontalIndex();
/*     */           }
/*     */           
/* 503 */           if (enumfacing == EnumFacing.EAST)
/*     */           {
/* 505 */             return EnumFacing.SOUTH.getHorizontalIndex();
/*     */           }
/*     */         }
/* 508 */         else if (this.coordBaseMode == EnumFacing.EAST)
/*     */         {
/* 510 */           if (enumfacing == EnumFacing.NORTH)
/*     */           {
/* 512 */             return EnumFacing.EAST.getHorizontalIndex();
/*     */           }
/*     */           
/* 515 */           if (enumfacing == EnumFacing.SOUTH)
/*     */           {
/* 517 */             return EnumFacing.WEST.getHorizontalIndex();
/*     */           }
/*     */           
/* 520 */           if (enumfacing == EnumFacing.WEST)
/*     */           {
/* 522 */             return EnumFacing.NORTH.getHorizontalIndex();
/*     */           }
/*     */           
/* 525 */           if (enumfacing == EnumFacing.EAST)
/*     */           {
/* 527 */             return EnumFacing.SOUTH.getHorizontalIndex();
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 532 */     } else if (this.coordBaseMode == EnumFacing.SOUTH) {
/*     */       
/* 534 */       if (meta == 2)
/*     */       {
/* 536 */         return 3;
/*     */       }
/*     */       
/* 539 */       if (meta == 3)
/*     */       {
/* 541 */         return 2;
/*     */       }
/*     */     }
/* 544 */     else if (this.coordBaseMode == EnumFacing.WEST) {
/*     */       
/* 546 */       if (meta == 0)
/*     */       {
/* 548 */         return 2;
/*     */       }
/*     */       
/* 551 */       if (meta == 1)
/*     */       {
/* 553 */         return 3;
/*     */       }
/*     */       
/* 556 */       if (meta == 2)
/*     */       {
/* 558 */         return 0;
/*     */       }
/*     */       
/* 561 */       if (meta == 3)
/*     */       {
/* 563 */         return 1;
/*     */       }
/*     */     }
/* 566 */     else if (this.coordBaseMode == EnumFacing.EAST) {
/*     */       
/* 568 */       if (meta == 0)
/*     */       {
/* 570 */         return 2;
/*     */       }
/*     */       
/* 573 */       if (meta == 1)
/*     */       {
/* 575 */         return 3;
/*     */       }
/*     */       
/* 578 */       if (meta == 2)
/*     */       {
/* 580 */         return 1;
/*     */       }
/*     */       
/* 583 */       if (meta == 3)
/*     */       {
/* 585 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 589 */     return meta;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 594 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 596 */     if (boundingboxIn.isVecInside((Vec3i)blockpos))
/*     */     {
/* 598 */       worldIn.setBlockState(blockpos, blockstateIn, 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getBlockStateFromPos(World worldIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 604 */     int i = getXWithOffset(x, z);
/* 605 */     int j = getYWithOffset(y);
/* 606 */     int k = getZWithOffset(x, z);
/* 607 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 608 */     return !boundingboxIn.isVecInside((Vec3i)blockpos) ? Blocks.air.getDefaultState() : worldIn.getBlockState(blockpos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithAir(World worldIn, StructureBoundingBox structurebb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 617 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 619 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 621 */         for (int k = minZ; k <= maxZ; k++)
/*     */         {
/* 623 */           setBlockState(worldIn, Blocks.air.getDefaultState(), j, i, k, structurebb);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
/* 634 */     for (int i = yMin; i <= yMax; i++) {
/*     */       
/* 636 */       for (int j = xMin; j <= xMax; j++) {
/*     */         
/* 638 */         for (int k = zMin; k <= zMax; k++) {
/*     */           
/* 640 */           if (!existingOnly || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air)
/*     */           {
/* 642 */             if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax) {
/*     */               
/* 644 */               setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
/*     */             }
/*     */             else {
/*     */               
/* 648 */               setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, BlockSelector blockselector) {
/* 662 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 664 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 666 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 668 */           if (!alwaysReplace || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air) {
/*     */             
/* 670 */             blockselector.selectBlocks(rand, j, i, k, (i == minY || i == maxY || j == minX || j == maxX || k == minZ || k == maxZ));
/* 671 */             setBlockState(worldIn, blockselector.getBlockState(), j, i, k, boundingboxIn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175805_a(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstate1, IBlockState blockstate2, boolean p_175805_13_) {
/* 680 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 682 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 684 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 686 */           if (rand.nextFloat() <= chance && (!p_175805_13_ || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air))
/*     */           {
/* 688 */             if (i != minY && i != maxY && j != minX && j != maxX && k != minZ && k != maxZ) {
/*     */               
/* 690 */               setBlockState(worldIn, blockstate2, j, i, k, boundingboxIn);
/*     */             }
/*     */             else {
/*     */               
/* 694 */               setBlockState(worldIn, blockstate1, j, i, k, boundingboxIn);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomlyPlaceBlock(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int x, int y, int z, IBlockState blockstateIn) {
/* 704 */     if (rand.nextFloat() < chance)
/*     */     {
/* 706 */       setBlockState(worldIn, blockstateIn, x, y, z, boundingboxIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomlyRareFillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstateIn, boolean p_180777_10_) {
/* 712 */     float f = (maxX - minX + 1);
/* 713 */     float f1 = (maxY - minY + 1);
/* 714 */     float f2 = (maxZ - minZ + 1);
/* 715 */     float f3 = minX + f / 2.0F;
/* 716 */     float f4 = minZ + f2 / 2.0F;
/*     */     
/* 718 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 720 */       float f5 = (i - minY) / f1;
/*     */       
/* 722 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 724 */         float f6 = (j - f3) / f * 0.5F;
/*     */         
/* 726 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 728 */           float f7 = (k - f4) / f2 * 0.5F;
/*     */           
/* 730 */           if (!p_180777_10_ || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air) {
/*     */             
/* 732 */             float f8 = f6 * f6 + f5 * f5 + f7 * f7;
/*     */             
/* 734 */             if (f8 <= 1.05F)
/*     */             {
/* 736 */               setBlockState(worldIn, blockstateIn, j, i, k, boundingboxIn);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearCurrentPositionBlocksUpwards(World worldIn, int x, int y, int z, StructureBoundingBox structurebb) {
/* 749 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 751 */     if (structurebb.isVecInside((Vec3i)blockpos))
/*     */     {
/* 753 */       while (!worldIn.isAirBlock(blockpos) && blockpos.getY() < 255) {
/*     */         
/* 755 */         worldIn.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/* 756 */         blockpos = blockpos.up();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 766 */     int i = getXWithOffset(x, z);
/* 767 */     int j = getYWithOffset(y);
/* 768 */     int k = getZWithOffset(x, z);
/*     */     
/* 770 */     if (boundingboxIn.isVecInside((Vec3i)new BlockPos(i, j, k)))
/*     */     {
/* 772 */       while ((worldIn.isAirBlock(new BlockPos(i, j, k)) || worldIn.getBlockState(new BlockPos(i, j, k)).getBlock().getMaterial().isLiquid()) && j > 1) {
/*     */         
/* 774 */         worldIn.setBlockState(new BlockPos(i, j, k), blockstateIn, 2);
/* 775 */         j--;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean generateChestContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, List<WeightedRandomChestContent> listIn, int max) {
/* 782 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 784 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.chest) {
/*     */       
/* 786 */       IBlockState iblockstate = Blocks.chest.getDefaultState();
/* 787 */       worldIn.setBlockState(blockpos, Blocks.chest.correctFacing(worldIn, blockpos, iblockstate), 2);
/* 788 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 790 */       if (tileentity instanceof net.minecraft.tileentity.TileEntityChest)
/*     */       {
/* 792 */         WeightedRandomChestContent.generateChestContents(rand, listIn, (IInventory)tileentity, max);
/*     */       }
/*     */       
/* 795 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 799 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean generateDispenserContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, int meta, List<WeightedRandomChestContent> listIn, int max) {
/* 805 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 807 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.dispenser) {
/*     */       
/* 809 */       worldIn.setBlockState(blockpos, Blocks.dispenser.getStateFromMeta(getMetadataWithOffset(Blocks.dispenser, meta)), 2);
/* 810 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 812 */       if (tileentity instanceof TileEntityDispenser)
/*     */       {
/* 814 */         WeightedRandomChestContent.generateDispenserContents(rand, listIn, (TileEntityDispenser)tileentity, max);
/*     */       }
/*     */       
/* 817 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 821 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void placeDoorCurrentPosition(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 830 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 832 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos))
/*     */     {
/* 834 */       ItemDoor.placeDoor(worldIn, blockpos, facing.rotateYCCW(), Blocks.oak_door);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181138_a(int p_181138_1_, int p_181138_2_, int p_181138_3_) {
/* 840 */     this.boundingBox.offset(p_181138_1_, p_181138_2_, p_181138_3_);
/*     */   }
/*     */   
/*     */   public static abstract class BlockSelector
/*     */   {
/* 845 */     protected IBlockState blockstate = Blocks.air.getDefaultState();
/*     */ 
/*     */     
/*     */     public abstract void selectBlocks(Random param1Random, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean);
/*     */     
/*     */     public IBlockState getBlockState() {
/* 851 */       return this.blockstate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\StructureComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */