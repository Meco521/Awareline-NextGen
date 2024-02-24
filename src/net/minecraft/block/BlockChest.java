/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockChest extends BlockContainer {
/*  31 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */ 
/*     */   
/*     */   public final int chestType;
/*     */ 
/*     */   
/*     */   protected BlockChest(int type) {
/*  38 */     super(Material.wood);
/*  39 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  40 */     this.chestType = type;
/*  41 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  42 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  63 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  68 */     if (worldIn.getBlockState(pos.north()).getBlock() == this) {
/*     */       
/*  70 */       setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
/*     */     }
/*  72 */     else if (worldIn.getBlockState(pos.south()).getBlock() == this) {
/*     */       
/*  74 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
/*     */     }
/*  76 */     else if (worldIn.getBlockState(pos.west()).getBlock() == this) {
/*     */       
/*  78 */       setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */     }
/*  80 */     else if (worldIn.getBlockState(pos.east()).getBlock() == this) {
/*     */       
/*  82 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
/*     */     }
/*     */     else {
/*     */       
/*  86 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  92 */     checkForSurroundingChests(worldIn, pos, state);
/*     */     
/*  94 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  96 */       BlockPos blockpos = pos.offset(enumfacing);
/*  97 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/*  99 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 101 */         checkForSurroundingChests(worldIn, blockpos, iblockstate);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 112 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 120 */     EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3).getOpposite();
/* 121 */     state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/* 122 */     BlockPos blockpos = pos.north();
/* 123 */     BlockPos blockpos1 = pos.south();
/* 124 */     BlockPos blockpos2 = pos.west();
/* 125 */     BlockPos blockpos3 = pos.east();
/* 126 */     boolean flag = (this == worldIn.getBlockState(blockpos).getBlock());
/* 127 */     boolean flag1 = (this == worldIn.getBlockState(blockpos1).getBlock());
/* 128 */     boolean flag2 = (this == worldIn.getBlockState(blockpos2).getBlock());
/* 129 */     boolean flag3 = (this == worldIn.getBlockState(blockpos3).getBlock());
/*     */     
/* 131 */     if (!flag && !flag1 && !flag2 && !flag3) {
/*     */       
/* 133 */       worldIn.setBlockState(pos, state, 3);
/*     */     }
/* 135 */     else if (enumfacing.getAxis() != EnumFacing.Axis.X || (!flag && !flag1)) {
/*     */       
/* 137 */       if (enumfacing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3))
/*     */       {
/* 139 */         if (flag2) {
/*     */           
/* 141 */           worldIn.setBlockState(blockpos2, state, 3);
/*     */         }
/*     */         else {
/*     */           
/* 145 */           worldIn.setBlockState(blockpos3, state, 3);
/*     */         } 
/*     */         
/* 148 */         worldIn.setBlockState(pos, state, 3);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 153 */       if (flag) {
/*     */         
/* 155 */         worldIn.setBlockState(blockpos, state, 3);
/*     */       }
/*     */       else {
/*     */         
/* 159 */         worldIn.setBlockState(blockpos1, state, 3);
/*     */       } 
/*     */       
/* 162 */       worldIn.setBlockState(pos, state, 3);
/*     */     } 
/*     */     
/* 165 */     if (stack.hasDisplayName()) {
/*     */       
/* 167 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 169 */       if (tileentity instanceof TileEntityChest)
/*     */       {
/* 171 */         ((TileEntityChest)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState checkForSurroundingChests(World worldIn, BlockPos pos, IBlockState state) {
/* 178 */     if (worldIn.isRemote)
/*     */     {
/* 180 */       return state;
/*     */     }
/*     */ 
/*     */     
/* 184 */     IBlockState iblockstate = worldIn.getBlockState(pos.north());
/* 185 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
/* 186 */     IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
/* 187 */     IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
/* 188 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 189 */     Block block = iblockstate.getBlock();
/* 190 */     Block block1 = iblockstate1.getBlock();
/* 191 */     Block block2 = iblockstate2.getBlock();
/* 192 */     Block block3 = iblockstate3.getBlock();
/*     */     
/* 194 */     if (block != this && block1 != this) {
/*     */       
/* 196 */       boolean flag = block.isFullBlock();
/* 197 */       boolean flag1 = block1.isFullBlock();
/*     */       
/* 199 */       if (block2 == this || block3 == this) {
/*     */         EnumFacing enumfacing2;
/* 201 */         BlockPos blockpos1 = (block2 == this) ? pos.west() : pos.east();
/* 202 */         IBlockState iblockstate6 = worldIn.getBlockState(blockpos1.north());
/* 203 */         IBlockState iblockstate7 = worldIn.getBlockState(blockpos1.south());
/* 204 */         enumfacing = EnumFacing.SOUTH;
/*     */ 
/*     */         
/* 207 */         if (block2 == this) {
/*     */           
/* 209 */           enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         }
/*     */         else {
/*     */           
/* 213 */           enumfacing2 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         } 
/*     */         
/* 216 */         if (enumfacing2 == EnumFacing.NORTH)
/*     */         {
/* 218 */           enumfacing = EnumFacing.NORTH;
/*     */         }
/*     */         
/* 221 */         Block block6 = iblockstate6.getBlock();
/* 222 */         Block block7 = iblockstate7.getBlock();
/*     */         
/* 224 */         if ((flag || block6.isFullBlock()) && !flag1 && !block7.isFullBlock())
/*     */         {
/* 226 */           enumfacing = EnumFacing.SOUTH;
/*     */         }
/*     */         
/* 229 */         if ((flag1 || block7.isFullBlock()) && !flag && !block6.isFullBlock())
/*     */         {
/* 231 */           enumfacing = EnumFacing.NORTH;
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       EnumFacing enumfacing1;
/*     */       
/* 237 */       BlockPos blockpos = (block == this) ? pos.north() : pos.south();
/* 238 */       IBlockState iblockstate4 = worldIn.getBlockState(blockpos.west());
/* 239 */       IBlockState iblockstate5 = worldIn.getBlockState(blockpos.east());
/* 240 */       enumfacing = EnumFacing.EAST;
/*     */ 
/*     */       
/* 243 */       if (block == this) {
/*     */         
/* 245 */         enumfacing1 = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       }
/*     */       else {
/*     */         
/* 249 */         enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */       } 
/*     */       
/* 252 */       if (enumfacing1 == EnumFacing.WEST)
/*     */       {
/* 254 */         enumfacing = EnumFacing.WEST;
/*     */       }
/*     */       
/* 257 */       Block block4 = iblockstate4.getBlock();
/* 258 */       Block block5 = iblockstate5.getBlock();
/*     */       
/* 260 */       if ((block2.isFullBlock() || block4.isFullBlock()) && !block3.isFullBlock() && !block5.isFullBlock())
/*     */       {
/* 262 */         enumfacing = EnumFacing.EAST;
/*     */       }
/*     */       
/* 265 */       if ((block3.isFullBlock() || block5.isFullBlock()) && !block2.isFullBlock() && !block4.isFullBlock())
/*     */       {
/* 267 */         enumfacing = EnumFacing.WEST;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/* 272 */     worldIn.setBlockState(pos, state, 3);
/* 273 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState correctFacing(World worldIn, BlockPos pos, IBlockState state) {
/* 279 */     EnumFacing enumfacing = null;
/*     */     
/* 281 */     for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 283 */       IBlockState iblockstate = worldIn.getBlockState(pos.offset(enumfacing1));
/*     */       
/* 285 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 287 */         return state;
/*     */       }
/*     */       
/* 290 */       if (iblockstate.getBlock().isFullBlock()) {
/*     */         
/* 292 */         if (enumfacing != null) {
/*     */           
/* 294 */           enumfacing = null;
/*     */           
/*     */           break;
/*     */         } 
/* 298 */         enumfacing = enumfacing1;
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     if (enumfacing != null)
/*     */     {
/* 304 */       return state.withProperty((IProperty)FACING, (Comparable)enumfacing.getOpposite());
/*     */     }
/*     */ 
/*     */     
/* 308 */     EnumFacing enumfacing2 = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 310 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).getBlock().isFullBlock())
/*     */     {
/* 312 */       enumfacing2 = enumfacing2.getOpposite();
/*     */     }
/*     */     
/* 315 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).getBlock().isFullBlock())
/*     */     {
/* 317 */       enumfacing2 = enumfacing2.rotateY();
/*     */     }
/*     */     
/* 320 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).getBlock().isFullBlock())
/*     */     {
/* 322 */       enumfacing2 = enumfacing2.getOpposite();
/*     */     }
/*     */     
/* 325 */     return state.withProperty((IProperty)FACING, (Comparable)enumfacing2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 331 */     int i = 0;
/* 332 */     BlockPos blockpos = pos.west();
/* 333 */     BlockPos blockpos1 = pos.east();
/* 334 */     BlockPos blockpos2 = pos.north();
/* 335 */     BlockPos blockpos3 = pos.south();
/*     */     
/* 337 */     if (worldIn.getBlockState(blockpos).getBlock() == this) {
/*     */       
/* 339 */       if (isDoubleChest(worldIn, blockpos))
/*     */       {
/* 341 */         return false;
/*     */       }
/*     */       
/* 344 */       i++;
/*     */     } 
/*     */     
/* 347 */     if (worldIn.getBlockState(blockpos1).getBlock() == this) {
/*     */       
/* 349 */       if (isDoubleChest(worldIn, blockpos1))
/*     */       {
/* 351 */         return false;
/*     */       }
/*     */       
/* 354 */       i++;
/*     */     } 
/*     */     
/* 357 */     if (worldIn.getBlockState(blockpos2).getBlock() == this) {
/*     */       
/* 359 */       if (isDoubleChest(worldIn, blockpos2))
/*     */       {
/* 361 */         return false;
/*     */       }
/*     */       
/* 364 */       i++;
/*     */     } 
/*     */     
/* 367 */     if (worldIn.getBlockState(blockpos3).getBlock() == this) {
/*     */       
/* 369 */       if (isDoubleChest(worldIn, blockpos3))
/*     */       {
/* 371 */         return false;
/*     */       }
/*     */       
/* 374 */       i++;
/*     */     } 
/*     */     
/* 377 */     return (i <= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDoubleChest(World worldIn, BlockPos pos) {
/* 382 */     if (worldIn.getBlockState(pos).getBlock() != this)
/*     */     {
/* 384 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 388 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 390 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this)
/*     */       {
/* 392 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 396 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 405 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/* 406 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 408 */     if (tileentity instanceof TileEntityChest)
/*     */     {
/* 410 */       tileentity.updateContainingBlockInfo();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 416 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 418 */     if (tileentity instanceof IInventory) {
/*     */       
/* 420 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 421 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 424 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 429 */     if (worldIn.isRemote)
/*     */     {
/* 431 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 435 */     ILockableContainer ilockablecontainer = getLockableContainer(worldIn, pos);
/*     */     
/* 437 */     if (ilockablecontainer != null) {
/*     */       
/* 439 */       playerIn.displayGUIChest((IInventory)ilockablecontainer);
/*     */       
/* 441 */       if (this.chestType == 0) {
/*     */         
/* 443 */         playerIn.triggerAchievement(StatList.field_181723_aa);
/*     */       }
/* 445 */       else if (this.chestType == 1) {
/*     */         
/* 447 */         playerIn.triggerAchievement(StatList.field_181737_U);
/*     */       } 
/*     */     } 
/*     */     
/* 451 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ILockableContainer getLockableContainer(World worldIn, BlockPos pos) {
/*     */     InventoryLargeChest inventoryLargeChest;
/* 457 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 459 */     if (!(tileentity instanceof TileEntityChest))
/*     */     {
/* 461 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 465 */     TileEntityChest tileEntityChest = (TileEntityChest)tileentity;
/*     */     
/* 467 */     if (isBlocked(worldIn, pos))
/*     */     {
/* 469 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 473 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 475 */       BlockPos blockpos = pos.offset(enumfacing);
/* 476 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/* 478 */       if (block == this) {
/*     */         
/* 480 */         if (isBlocked(worldIn, blockpos))
/*     */         {
/* 482 */           return null;
/*     */         }
/*     */         
/* 485 */         TileEntity tileentity1 = worldIn.getTileEntity(blockpos);
/*     */         
/* 487 */         if (tileentity1 instanceof TileEntityChest) {
/*     */           
/* 489 */           if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
/*     */             
/* 491 */             inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileEntityChest, (ILockableContainer)tileentity1);
/*     */             
/*     */             continue;
/*     */           } 
/* 495 */           inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileentity1, (ILockableContainer)inventoryLargeChest);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 501 */     return (ILockableContainer)inventoryLargeChest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 511 */     return (TileEntity)new TileEntityChest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 519 */     return (this.chestType == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 524 */     if (!canProvidePower())
/*     */     {
/* 526 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 530 */     int i = 0;
/* 531 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 533 */     if (tileentity instanceof TileEntityChest)
/*     */     {
/* 535 */       i = ((TileEntityChest)tileentity).numPlayersUsing;
/*     */     }
/*     */     
/* 538 */     return MathHelper.clamp_int(i, 0, 15);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 544 */     return (side == EnumFacing.UP) ? getWeakPower(worldIn, pos, state, side) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBlocked(World worldIn, BlockPos pos) {
/* 549 */     return (isBelowSolidBlock(worldIn, pos) || isOcelotSittingOnChest(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
/* 554 */     return worldIn.getBlockState(pos.up()).getBlock().isNormalCube();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos) {
/* 559 */     for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(pos.getX(), (pos.getY() + 1), pos.getZ(), (pos.getX() + 1), (pos.getY() + 2), (pos.getZ() + 1)))) {
/*     */       
/* 561 */       EntityOcelot entityocelot = (EntityOcelot)entity;
/*     */       
/* 563 */       if (entityocelot.isSitting())
/*     */       {
/* 565 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 569 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 574 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 579 */     return Container.calcRedstoneFromInventory((IInventory)getLockableContainer(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 587 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 589 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 591 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 594 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 602 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 607 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */