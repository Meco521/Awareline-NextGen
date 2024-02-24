/*     */ package net.minecraft.block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDoor extends Block {
/*  22 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  23 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  24 */   public static final PropertyEnum<EnumHingePosition> HINGE = PropertyEnum.create("hinge", EnumHingePosition.class);
/*  25 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  26 */   public static final PropertyEnum<EnumDoorHalf> HALF = PropertyEnum.create("half", EnumDoorHalf.class);
/*     */ 
/*     */   
/*     */   protected BlockDoor(Material materialIn) {
/*  30 */     super(materialIn);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)HINGE, EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)HALF, EnumDoorHalf.LOWER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  39 */     return StatCollector.translateToLocal((getUnlocalizedName() + ".name").replaceAll("tile", "item"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  52 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  62 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  63 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  68 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  69 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  74 */     setBoundBasedOnMeta(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBoundBasedOnMeta(int combinedMeta) {
/*  79 */     float f = 0.1875F;
/*  80 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/*  81 */     EnumFacing enumfacing = getFacing(combinedMeta);
/*  82 */     boolean flag = isOpen(combinedMeta);
/*  83 */     boolean flag1 = isHingeLeft(combinedMeta);
/*     */     
/*  85 */     if (flag) {
/*     */       
/*  87 */       if (enumfacing == EnumFacing.EAST) {
/*     */         
/*  89 */         if (!flag1)
/*     */         {
/*  91 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         }
/*     */         else
/*     */         {
/*  95 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       
/*  98 */       } else if (enumfacing == EnumFacing.SOUTH) {
/*     */         
/* 100 */         if (!flag1)
/*     */         {
/* 102 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         else
/*     */         {
/* 106 */           setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         }
/*     */       
/* 109 */       } else if (enumfacing == EnumFacing.WEST) {
/*     */         
/* 111 */         if (!flag1)
/*     */         {
/* 113 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         else
/*     */         {
/* 117 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         }
/*     */       
/* 120 */       } else if (enumfacing == EnumFacing.NORTH) {
/*     */         
/* 122 */         if (!flag1)
/*     */         {
/* 124 */           setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         }
/*     */         else
/*     */         {
/* 128 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       
/*     */       } 
/* 132 */     } else if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 134 */       setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */     }
/* 136 */     else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 138 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */     }
/* 140 */     else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 142 */       setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/* 144 */     else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 146 */       setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 152 */     if (this.blockMaterial == Material.iron)
/*     */     {
/* 154 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 158 */     BlockPos blockpos = (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 159 */     IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);
/*     */     
/* 161 */     if (iblockstate.getBlock() != this)
/*     */     {
/* 163 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 167 */     state = iblockstate.cycleProperty((IProperty)OPEN);
/* 168 */     worldIn.setBlockState(blockpos, state, 2);
/* 169 */     worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 170 */     worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
/* 178 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 180 */     if (iblockstate.getBlock() == this) {
/*     */       
/* 182 */       BlockPos blockpos = (iblockstate.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 183 */       IBlockState iblockstate1 = (pos == blockpos) ? iblockstate : worldIn.getBlockState(blockpos);
/*     */       
/* 185 */       if (iblockstate1.getBlock() == this && ((Boolean)iblockstate1.getValue((IProperty)OPEN)).booleanValue() != open) {
/*     */         
/* 187 */         worldIn.setBlockState(blockpos, iblockstate1.withProperty((IProperty)OPEN, Boolean.valueOf(open)), 2);
/* 188 */         worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 189 */         worldIn.playAuxSFXAtEntity((EntityPlayer)null, open ? 1003 : 1006, pos, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 199 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/*     */       
/* 201 */       BlockPos blockpos = pos.down();
/* 202 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 204 */       if (iblockstate.getBlock() != this)
/*     */       {
/* 206 */         worldIn.setBlockToAir(pos);
/*     */       }
/* 208 */       else if (neighborBlock != this)
/*     */       {
/* 210 */         onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 215 */       boolean flag1 = false;
/* 216 */       BlockPos blockpos1 = pos.up();
/* 217 */       IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */       
/* 219 */       if (iblockstate1.getBlock() != this) {
/*     */         
/* 221 */         worldIn.setBlockToAir(pos);
/* 222 */         flag1 = true;
/*     */       } 
/*     */       
/* 225 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/*     */         
/* 227 */         worldIn.setBlockToAir(pos);
/* 228 */         flag1 = true;
/*     */         
/* 230 */         if (iblockstate1.getBlock() == this)
/*     */         {
/* 232 */           worldIn.setBlockToAir(blockpos1);
/*     */         }
/*     */       } 
/*     */       
/* 236 */       if (flag1) {
/*     */         
/* 238 */         if (!worldIn.isRemote)
/*     */         {
/* 240 */           dropBlockAsItem(worldIn, pos, state, 0);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 245 */         boolean flag = (worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1));
/*     */         
/* 247 */         if ((flag || neighborBlock.canProvidePower()) && neighborBlock != this && flag != ((Boolean)iblockstate1.getValue((IProperty)POWERED)).booleanValue()) {
/*     */           
/* 249 */           worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)POWERED, Boolean.valueOf(flag)), 2);
/*     */           
/* 251 */           if (flag != ((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/*     */             
/* 253 */             worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 254 */             worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 255 */             worldIn.playAuxSFXAtEntity((EntityPlayer)null, flag ? 1003 : 1006, pos, 0);
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
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 267 */     return (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) ? null : getItem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 275 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 276 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 281 */     return (pos.getY() >= 255) ? false : ((World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up())));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMobilityFlag() {
/* 286 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
/* 291 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 292 */     int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 293 */     boolean flag = isTop(i);
/* 294 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/* 295 */     int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
/* 296 */     int k = flag ? j : i;
/* 297 */     IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
/* 298 */     int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
/* 299 */     int i1 = flag ? i : l;
/* 300 */     boolean flag1 = ((i1 & 0x1) != 0);
/* 301 */     boolean flag2 = ((i1 & 0x2) != 0);
/* 302 */     return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 307 */     return getItem();
/*     */   }
/*     */ 
/*     */   
/*     */   private Item getItem() {
/* 312 */     return (this == Blocks.iron_door) ? Items.iron_door : ((this == Blocks.spruce_door) ? Items.spruce_door : ((this == Blocks.birch_door) ? Items.birch_door : ((this == Blocks.jungle_door) ? Items.jungle_door : ((this == Blocks.acacia_door) ? Items.acacia_door : ((this == Blocks.dark_oak_door) ? Items.dark_oak_door : Items.oak_door)))));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 317 */     BlockPos blockpos = pos.down();
/*     */     
/* 319 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this)
/*     */     {
/* 321 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 327 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 336 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) {
/*     */       
/* 338 */       IBlockState iblockstate = worldIn.getBlockState(pos.up());
/*     */       
/* 340 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 342 */         state = state.withProperty((IProperty)HINGE, iblockstate.getValue((IProperty)HINGE)).withProperty((IProperty)POWERED, iblockstate.getValue((IProperty)POWERED));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 347 */       IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */       
/* 349 */       if (iblockstate1.getBlock() == this)
/*     */       {
/* 351 */         state = state.withProperty((IProperty)FACING, iblockstate1.getValue((IProperty)FACING)).withProperty((IProperty)OPEN, iblockstate1.getValue((IProperty)OPEN));
/*     */       }
/*     */     } 
/*     */     
/* 355 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 363 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.UPPER).withProperty((IProperty)HINGE, ((meta & 0x1) > 0) ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x2) > 0))) : getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.LOWER).withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3).rotateYCCW()).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 371 */     int i = 0;
/*     */     
/* 373 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/*     */       
/* 375 */       i |= 0x8;
/*     */       
/* 377 */       if (state.getValue((IProperty)HINGE) == EnumHingePosition.RIGHT)
/*     */       {
/* 379 */         i |= 0x1;
/*     */       }
/*     */       
/* 382 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 384 */         i |= 0x2;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 389 */       i |= ((EnumFacing)state.getValue((IProperty)FACING)).rotateY().getHorizontalIndex();
/*     */       
/* 391 */       if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */       {
/* 393 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 397 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int removeHalfBit(int meta) {
/* 402 */     return meta & 0x7;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isOpen(IBlockAccess worldIn, BlockPos pos) {
/* 407 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
/* 412 */     return getFacing(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int combinedMeta) {
/* 417 */     return EnumFacing.getHorizontal(combinedMeta & 0x3).rotateYCCW();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isOpen(int combinedMeta) {
/* 422 */     return ((combinedMeta & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isTop(int meta) {
/* 427 */     return ((meta & 0x8) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isHingeLeft(int combinedMeta) {
/* 432 */     return ((combinedMeta & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 437 */     return new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)FACING, (IProperty)OPEN, (IProperty)HINGE, (IProperty)POWERED });
/*     */   }
/*     */   
/*     */   public enum EnumDoorHalf
/*     */     implements IStringSerializable {
/* 442 */     UPPER,
/* 443 */     LOWER;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 447 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 452 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumHingePosition
/*     */     implements IStringSerializable {
/* 458 */     LEFT,
/* 459 */     RIGHT;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 463 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 468 */       return (this == LEFT) ? "left" : "right";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */