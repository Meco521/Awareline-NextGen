/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockPistonStructureHelper;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonBase
/*     */   extends Block
/*     */ {
/*  28 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  29 */   public static final PropertyBool EXTENDED = PropertyBool.create("extended");
/*     */ 
/*     */   
/*     */   private final boolean isSticky;
/*     */ 
/*     */   
/*     */   public BlockPistonBase(boolean isSticky) {
/*  36 */     super(Material.piston);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)EXTENDED, Boolean.valueOf(false)));
/*  38 */     this.isSticky = isSticky;
/*  39 */     setStepSound(soundTypePiston);
/*  40 */     setHardness(0.5F);
/*  41 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  57 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)getFacingFromEntity(worldIn, pos, placer)), 2);
/*     */     
/*  59 */     if (!worldIn.isRemote)
/*     */     {
/*  61 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  70 */     if (!worldIn.isRemote)
/*     */     {
/*  72 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  78 */     if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null)
/*     */     {
/*  80 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  90 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacingFromEntity(worldIn, pos, placer)).withProperty((IProperty)EXTENDED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkForMove(World worldIn, BlockPos pos, IBlockState state) {
/*  95 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  96 */     boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */     
/*  98 */     if (flag && !((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/* 100 */       if ((new BlockPistonStructureHelper(worldIn, pos, enumfacing, true)).canMove())
/*     */       {
/* 102 */         worldIn.addBlockEvent(pos, this, 0, enumfacing.getIndex());
/*     */       }
/*     */     }
/* 105 */     else if (!flag && ((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/* 107 */       worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(false)), 2);
/* 108 */       worldIn.addBlockEvent(pos, this, 1, enumfacing.getIndex());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldBeExtended(World worldIn, BlockPos pos, EnumFacing facing) {
/* 114 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */       
/* 116 */       if (enumfacing != facing && worldIn.isSidePowered(pos.offset(enumfacing), enumfacing))
/*     */       {
/* 118 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 122 */     if (worldIn.isSidePowered(pos, EnumFacing.DOWN))
/*     */     {
/* 124 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 128 */     BlockPos blockpos = pos.up();
/*     */     
/* 130 */     for (EnumFacing enumfacing1 : EnumFacing.values()) {
/*     */       
/* 132 */       if (enumfacing1 != EnumFacing.DOWN && worldIn.isSidePowered(blockpos.offset(enumfacing1), enumfacing1))
/*     */       {
/* 134 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 147 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 149 */     if (!worldIn.isRemote) {
/*     */       
/* 151 */       boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */       
/* 153 */       if (flag && eventID == 1) {
/*     */         
/* 155 */         worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(true)), 2);
/* 156 */         return false;
/*     */       } 
/*     */       
/* 159 */       if (!flag && eventID == 0)
/*     */       {
/* 161 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 165 */     if (eventID == 0) {
/*     */       
/* 167 */       if (!doMove(worldIn, pos, enumfacing, true))
/*     */       {
/* 169 */         return false;
/*     */       }
/*     */       
/* 172 */       worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(true)), 2);
/* 173 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.out", 0.5F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
/*     */     }
/* 175 */     else if (eventID == 1) {
/*     */       
/* 177 */       TileEntity tileentity1 = worldIn.getTileEntity(pos.offset(enumfacing));
/*     */       
/* 179 */       if (tileentity1 instanceof TileEntityPiston)
/*     */       {
/* 181 */         ((TileEntityPiston)tileentity1).clearPistonTileEntity();
/*     */       }
/*     */       
/* 184 */       worldIn.setBlockState(pos, Blocks.piston_extension.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
/* 185 */       worldIn.setTileEntity(pos, BlockPistonMoving.newTileEntity(getStateFromMeta(eventParam), enumfacing, false, true));
/*     */       
/* 187 */       if (this.isSticky) {
/*     */         
/* 189 */         BlockPos blockpos = pos.add(enumfacing.getFrontOffsetX() << 1, enumfacing.getFrontOffsetY() << 1, enumfacing.getFrontOffsetZ() << 1);
/* 190 */         Block block = worldIn.getBlockState(blockpos).getBlock();
/* 191 */         boolean flag1 = false;
/*     */         
/* 193 */         if (block == Blocks.piston_extension) {
/*     */           
/* 195 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */           
/* 197 */           if (tileentity instanceof TileEntityPiston) {
/*     */             
/* 199 */             TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity;
/*     */             
/* 201 */             if (tileentitypiston.getFacing() == enumfacing && tileentitypiston.isExtending()) {
/*     */               
/* 203 */               tileentitypiston.clearPistonTileEntity();
/* 204 */               flag1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 209 */         if (!flag1 && block.getMaterial() != Material.air && canPush(block, worldIn, blockpos, enumfacing.getOpposite(), false) && (block.getMobilityFlag() == 0 || block == Blocks.piston || block == Blocks.sticky_piston))
/*     */         {
/* 211 */           doMove(worldIn, pos, enumfacing, false);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 216 */         worldIn.setBlockToAir(pos.offset(enumfacing));
/*     */       } 
/*     */       
/* 219 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.in", 0.5F, worldIn.rand.nextFloat() * 0.15F + 0.6F);
/*     */     } 
/*     */     
/* 222 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 227 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 229 */     if (iblockstate.getBlock() == this && ((Boolean)iblockstate.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/* 231 */       float f = 0.25F;
/* 232 */       EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       
/* 234 */       if (enumfacing != null)
/*     */       {
/* 236 */         switch (enumfacing) {
/*     */           
/*     */           case DOWN:
/* 239 */             setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case UP:
/* 243 */             setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*     */             break;
/*     */           
/*     */           case NORTH:
/* 247 */             setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 251 */             setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
/*     */             break;
/*     */           
/*     */           case WEST:
/* 255 */             setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case EAST:
/* 259 */             setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/*     */     } else {
/* 265 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 274 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 282 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 283 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 288 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 289 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 294 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 299 */     int i = meta & 0x7;
/* 300 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
/* 305 */     if (MathHelper.abs((float)entityIn.posX - clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - clickedBlock.getZ()) < 2.0F) {
/*     */       
/* 307 */       double d0 = entityIn.posY + entityIn.getEyeHeight();
/*     */       
/* 309 */       if (d0 - clickedBlock.getY() > 2.0D)
/*     */       {
/* 311 */         return EnumFacing.UP;
/*     */       }
/*     */       
/* 314 */       if (clickedBlock.getY() - d0 > 0.0D)
/*     */       {
/* 316 */         return EnumFacing.DOWN;
/*     */       }
/*     */     } 
/*     */     
/* 320 */     return entityIn.getHorizontalFacing().getOpposite();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canPush(Block blockIn, World worldIn, BlockPos pos, EnumFacing direction, boolean allowDestroy) {
/* 325 */     if (blockIn == Blocks.obsidian)
/*     */     {
/* 327 */       return false;
/*     */     }
/* 329 */     if (!worldIn.getWorldBorder().contains(pos))
/*     */     {
/* 331 */       return false;
/*     */     }
/* 333 */     if (pos.getY() >= 0 && (direction != EnumFacing.DOWN || pos.getY() != 0)) {
/*     */       
/* 335 */       if (pos.getY() <= worldIn.getHeight() - 1 && (direction != EnumFacing.UP || pos.getY() != worldIn.getHeight() - 1)) {
/*     */         
/* 337 */         if (blockIn != Blocks.piston && blockIn != Blocks.sticky_piston) {
/*     */           
/* 339 */           if (blockIn.getBlockHardness(worldIn, pos) == -1.0F)
/*     */           {
/* 341 */             return false;
/*     */           }
/*     */           
/* 344 */           if (blockIn.getMobilityFlag() == 2)
/*     */           {
/* 346 */             return false;
/*     */           }
/*     */           
/* 349 */           if (blockIn.getMobilityFlag() == 1)
/*     */           {
/* 351 */             if (!allowDestroy)
/*     */             {
/* 353 */               return false;
/*     */             }
/*     */             
/* 356 */             return true;
/*     */           }
/*     */         
/* 359 */         } else if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */           
/* 361 */           return false;
/*     */         } 
/*     */         
/* 364 */         return !(blockIn instanceof ITileEntityProvider);
/*     */       } 
/*     */ 
/*     */       
/* 368 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 373 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doMove(World worldIn, BlockPos pos, EnumFacing direction, boolean extending) {
/* 379 */     if (!extending)
/*     */     {
/* 381 */       worldIn.setBlockToAir(pos.offset(direction));
/*     */     }
/*     */     
/* 384 */     BlockPistonStructureHelper blockpistonstructurehelper = new BlockPistonStructureHelper(worldIn, pos, direction, extending);
/* 385 */     List<BlockPos> list = blockpistonstructurehelper.getBlocksToMove();
/* 386 */     List<BlockPos> list1 = blockpistonstructurehelper.getBlocksToDestroy();
/*     */     
/* 388 */     if (!blockpistonstructurehelper.canMove())
/*     */     {
/* 390 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 394 */     int i = list.size() + list1.size();
/* 395 */     Block[] ablock = new Block[i];
/* 396 */     EnumFacing enumfacing = extending ? direction : direction.getOpposite();
/*     */     
/* 398 */     for (int j = list1.size() - 1; j >= 0; j--) {
/*     */       
/* 400 */       BlockPos blockpos = list1.get(j);
/* 401 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/* 402 */       block.dropBlockAsItem(worldIn, blockpos, worldIn.getBlockState(blockpos), 0);
/* 403 */       worldIn.setBlockToAir(blockpos);
/* 404 */       i--;
/* 405 */       ablock[i] = block;
/*     */     } 
/*     */     
/* 408 */     for (int k = list.size() - 1; k >= 0; k--) {
/*     */       
/* 410 */       BlockPos blockpos2 = list.get(k);
/* 411 */       IBlockState iblockstate = worldIn.getBlockState(blockpos2);
/* 412 */       Block block1 = iblockstate.getBlock();
/* 413 */       block1.getMetaFromState(iblockstate);
/* 414 */       worldIn.setBlockToAir(blockpos2);
/* 415 */       blockpos2 = blockpos2.offset(enumfacing);
/* 416 */       worldIn.setBlockState(blockpos2, Blocks.piston_extension.getDefaultState().withProperty((IProperty)FACING, (Comparable)direction), 4);
/* 417 */       worldIn.setTileEntity(blockpos2, BlockPistonMoving.newTileEntity(iblockstate, direction, extending, false));
/* 418 */       i--;
/* 419 */       ablock[i] = block1;
/*     */     } 
/*     */     
/* 422 */     BlockPos blockpos1 = pos.offset(direction);
/*     */     
/* 424 */     if (extending) {
/*     */       
/* 426 */       BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/* 427 */       IBlockState iblockstate1 = Blocks.piston_head.getDefaultState().withProperty((IProperty)BlockPistonExtension.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonExtension.TYPE, blockpistonextension$enumpistontype);
/* 428 */       IBlockState iblockstate2 = Blocks.piston_extension.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/* 429 */       worldIn.setBlockState(blockpos1, iblockstate2, 4);
/* 430 */       worldIn.setTileEntity(blockpos1, BlockPistonMoving.newTileEntity(iblockstate1, direction, true, false));
/*     */     } 
/*     */     
/* 433 */     for (int l = list1.size() - 1; l >= 0; l--)
/*     */     {
/* 435 */       worldIn.notifyNeighborsOfStateChange(list1.get(l), ablock[i++]);
/*     */     }
/*     */     
/* 438 */     for (int i1 = list.size() - 1; i1 >= 0; i1--)
/*     */     {
/* 440 */       worldIn.notifyNeighborsOfStateChange(list.get(i1), ablock[i++]);
/*     */     }
/*     */     
/* 443 */     if (extending) {
/*     */       
/* 445 */       worldIn.notifyNeighborsOfStateChange(blockpos1, Blocks.piston_head);
/* 446 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */     } 
/*     */     
/* 449 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 458 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 466 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)EXTENDED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 474 */     int i = 0;
/* 475 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 477 */     if (((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue())
/*     */     {
/* 479 */       i |= 0x8;
/*     */     }
/*     */     
/* 482 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 487 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)EXTENDED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockPistonBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */