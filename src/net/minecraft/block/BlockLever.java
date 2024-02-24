/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockLever
/*     */   extends Block {
/*  21 */   public static final PropertyEnum<EnumOrientation> FACING = PropertyEnum.create("facing", EnumOrientation.class);
/*  22 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */ 
/*     */   
/*     */   protected BlockLever() {
/*  26 */     super(Material.circuits);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, EnumOrientation.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  28 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  33 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  54 */     return func_181090_a(worldIn, pos, side.getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  59 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */       
/*  61 */       if (func_181090_a(worldIn, pos, enumfacing))
/*     */       {
/*  63 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_181090_a(World p_181090_0_, BlockPos p_181090_1_, EnumFacing p_181090_2_) {
/*  72 */     return BlockButton.func_181088_a(p_181090_0_, p_181090_1_, p_181090_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  81 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */     
/*  83 */     if (func_181090_a(worldIn, pos, facing.getOpposite()))
/*     */     {
/*  85 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(facing, placer.getHorizontalFacing()));
/*     */     }
/*     */ 
/*     */     
/*  89 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  91 */       if (enumfacing != facing && func_181090_a(worldIn, pos, enumfacing.getOpposite()))
/*     */       {
/*  93 */         return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(enumfacing, placer.getHorizontalFacing()));
/*     */       }
/*     */     } 
/*     */     
/*  97 */     if (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()))
/*     */     {
/*  99 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(EnumFacing.UP, placer.getHorizontalFacing()));
/*     */     }
/*     */ 
/*     */     
/* 103 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMetadataForFacing(EnumFacing facing) {
/* 110 */     switch (facing) {
/*     */       
/*     */       case X:
/* 113 */         return 0;
/*     */       
/*     */       case Z:
/* 116 */         return 5;
/*     */       
/*     */       case null:
/* 119 */         return 4;
/*     */       
/*     */       case null:
/* 122 */         return 3;
/*     */       
/*     */       case null:
/* 125 */         return 2;
/*     */       
/*     */       case null:
/* 128 */         return 1;
/*     */     } 
/*     */     
/* 131 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 140 */     if (func_181091_e(worldIn, pos, state) && !func_181090_a(worldIn, pos, ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing().getOpposite())) {
/*     */       
/* 142 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 143 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_181091_e(World p_181091_1_, BlockPos p_181091_2_, IBlockState p_181091_3_) {
/* 149 */     if (canPlaceBlockAt(p_181091_1_, p_181091_2_))
/*     */     {
/* 151 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 155 */     dropBlockAsItem(p_181091_1_, p_181091_2_, p_181091_3_, 0);
/* 156 */     p_181091_1_.setBlockToAir(p_181091_2_);
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 163 */     float f = 0.1875F;
/*     */     
/* 165 */     switch ((EnumOrientation)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */       
/*     */       case X:
/* 168 */         setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case Z:
/* 172 */         setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case null:
/* 176 */         setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */         break;
/*     */       
/*     */       case null:
/* 180 */         setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */         break;
/*     */       
/*     */       case null:
/*     */       case null:
/* 185 */         f = 0.25F;
/* 186 */         setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case null:
/*     */       case null:
/* 191 */         f = 0.25F;
/* 192 */         setBlockBounds(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 198 */     if (worldIn.isRemote)
/*     */     {
/* 200 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 204 */     state = state.cycleProperty((IProperty)POWERED);
/* 205 */     worldIn.setBlockState(pos, state, 3);
/* 206 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0.6F : 0.5F);
/* 207 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 208 */     EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 209 */     worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 216 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/*     */       
/* 218 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 219 */       EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 220 */       worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
/*     */     } 
/*     */     
/* 223 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 228 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 233 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((((EnumOrientation)state.getValue((IProperty)FACING)).getFacing() == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 249 */     return getDefaultState().withProperty((IProperty)FACING, EnumOrientation.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 257 */     int i = 0;
/* 258 */     i |= ((EnumOrientation)state.getValue((IProperty)FACING)).getMetadata();
/*     */     
/* 260 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 262 */       i |= 0x8;
/*     */     }
/*     */     
/* 265 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 270 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */   
/*     */   public enum EnumOrientation
/*     */     implements IStringSerializable {
/* 275 */     DOWN_X(0, "down_x", EnumFacing.DOWN),
/* 276 */     EAST(1, "east", EnumFacing.EAST),
/* 277 */     WEST(2, "west", EnumFacing.WEST),
/* 278 */     SOUTH(3, "south", EnumFacing.SOUTH),
/* 279 */     NORTH(4, "north", EnumFacing.NORTH),
/* 280 */     UP_Z(5, "up_z", EnumFacing.UP),
/* 281 */     UP_X(6, "up_x", EnumFacing.UP),
/* 282 */     DOWN_Z(7, "down_z", EnumFacing.DOWN);
/*     */     
/* 284 */     private static final EnumOrientation[] META_LOOKUP = new EnumOrientation[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int meta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final EnumFacing facing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 374 */       for (EnumOrientation blocklever$enumorientation : values())
/*     */       {
/* 376 */         META_LOOKUP[blocklever$enumorientation.meta] = blocklever$enumorientation;
/*     */       }
/*     */     }
/*     */     
/*     */     EnumOrientation(int meta, String name, EnumFacing facing) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.facing = facing;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public EnumFacing getFacing() {
/*     */       return this.facing;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumOrientation byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public static EnumOrientation forFacings(EnumFacing clickedSide, EnumFacing entityFacing) {
/*     */       switch (clickedSide) {
/*     */         case X:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case X:
/*     */               return DOWN_X;
/*     */             case Z:
/*     */               return DOWN_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case Z:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case X:
/*     */               return UP_X;
/*     */             case Z:
/*     */               return UP_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case null:
/*     */           return NORTH;
/*     */         case null:
/*     */           return SOUTH;
/*     */         case null:
/*     */           return WEST;
/*     */         case null:
/*     */           return EAST;
/*     */       } 
/*     */       throw new IllegalArgumentException("Invalid facing: " + clickedSide);
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockLever.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */