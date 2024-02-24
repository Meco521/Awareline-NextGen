/*     */ package net.minecraft.block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTrapDoor extends Block {
/*  20 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  21 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  22 */   public static final PropertyEnum<DoorHalf> HALF = PropertyEnum.create("half", DoorHalf.class);
/*     */ 
/*     */   
/*     */   protected BlockTrapDoor(Material materialIn) {
/*  26 */     super(materialIn);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)HALF, DoorHalf.BOTTOM));
/*  28 */     float f = 0.5F;
/*  29 */     float f1 = 1.0F;
/*  30 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  31 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  49 */     return !((Boolean)worldIn.getBlockState(pos).getValue((IProperty)OPEN)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  54 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  55 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  60 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  61 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  66 */     setBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  74 */     float f = 0.1875F;
/*  75 */     setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(IBlockState state) {
/*  80 */     if (state.getBlock() == this) {
/*     */       
/*  82 */       boolean flag = (state.getValue((IProperty)HALF) == DoorHalf.TOP);
/*  83 */       Boolean obool = (Boolean)state.getValue((IProperty)OPEN);
/*  84 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  85 */       float f = 0.1875F;
/*     */       
/*  87 */       if (flag) {
/*     */         
/*  89 */         setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       else {
/*     */         
/*  93 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
/*     */       } 
/*     */       
/*  96 */       if (obool.booleanValue()) {
/*     */         
/*  98 */         if (enumfacing == EnumFacing.NORTH)
/*     */         {
/* 100 */           setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/* 103 */         if (enumfacing == EnumFacing.SOUTH)
/*     */         {
/* 105 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
/*     */         }
/*     */         
/* 108 */         if (enumfacing == EnumFacing.WEST)
/*     */         {
/* 110 */           setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/* 113 */         if (enumfacing == EnumFacing.EAST)
/*     */         {
/* 115 */           setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 123 */     if (this.blockMaterial == Material.iron)
/*     */     {
/* 125 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 129 */     state = state.cycleProperty((IProperty)OPEN);
/* 130 */     worldIn.setBlockState(pos, state, 2);
/* 131 */     worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 141 */     if (!worldIn.isRemote) {
/*     */       
/* 143 */       BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */       
/* 145 */       if (!isValidSupportBlock(worldIn.getBlockState(blockpos).getBlock())) {
/*     */         
/* 147 */         worldIn.setBlockToAir(pos);
/* 148 */         dropBlockAsItem(worldIn, pos, state, 0);
/*     */       }
/*     */       else {
/*     */         
/* 152 */         boolean flag = worldIn.isBlockPowered(pos);
/*     */         
/* 154 */         if (flag || neighborBlock.canProvidePower()) {
/*     */           
/* 156 */           boolean flag1 = ((Boolean)state.getValue((IProperty)OPEN)).booleanValue();
/*     */           
/* 158 */           if (flag1 != flag) {
/*     */             
/* 160 */             worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 161 */             worldIn.playAuxSFXAtEntity((EntityPlayer)null, flag ? 1003 : 1006, pos, 0);
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
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 173 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 174 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 183 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 185 */     if (facing.getAxis().isHorizontal()) {
/*     */       
/* 187 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)OPEN, Boolean.valueOf(false));
/* 188 */       iblockstate = iblockstate.withProperty((IProperty)HALF, (hitY > 0.5F) ? DoorHalf.TOP : DoorHalf.BOTTOM);
/*     */     } 
/*     */     
/* 191 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 199 */     return (!side.getAxis().isVertical() && isValidSupportBlock(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static EnumFacing getFacing(int meta) {
/* 204 */     switch (meta & 0x3) {
/*     */       
/*     */       case 0:
/* 207 */         return EnumFacing.NORTH;
/*     */       
/*     */       case 1:
/* 210 */         return EnumFacing.SOUTH;
/*     */       
/*     */       case 2:
/* 213 */         return EnumFacing.WEST;
/*     */     } 
/*     */ 
/*     */     
/* 217 */     return EnumFacing.EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getMetaForFacing(EnumFacing facing) {
/* 223 */     switch (facing) {
/*     */       
/*     */       case NORTH:
/* 226 */         return 0;
/*     */       
/*     */       case SOUTH:
/* 229 */         return 1;
/*     */       
/*     */       case WEST:
/* 232 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/* 236 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidSupportBlock(Block blockIn) {
/* 242 */     return ((blockIn.blockMaterial.isOpaque() && blockIn.isFullCube()) || blockIn == Blocks.glowstone || blockIn instanceof BlockSlab || blockIn instanceof BlockStairs);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 247 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 255 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? DoorHalf.BOTTOM : DoorHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 263 */     int i = 0;
/* 264 */     i |= getMetaForFacing((EnumFacing)state.getValue((IProperty)FACING));
/*     */     
/* 266 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */     {
/* 268 */       i |= 0x4;
/*     */     }
/*     */     
/* 271 */     if (state.getValue((IProperty)HALF) == DoorHalf.TOP)
/*     */     {
/* 273 */       i |= 0x8;
/*     */     }
/*     */     
/* 276 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 281 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)OPEN, (IProperty)HALF });
/*     */   }
/*     */   
/*     */   public enum DoorHalf
/*     */     implements IStringSerializable {
/* 286 */     TOP("top"),
/* 287 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     DoorHalf(String name) {
/* 293 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 298 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 303 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockTrapDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */