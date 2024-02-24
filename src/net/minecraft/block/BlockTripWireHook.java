/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWireHook
/*     */   extends Block {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  26 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  27 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  28 */   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
/*     */ 
/*     */   
/*     */   public BlockTripWireHook() {
/*  32 */     super(Material.circuits);
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false)));
/*  34 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  35 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  44 */     return state.withProperty((IProperty)SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface(worldIn, pos.down())));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  70 */     return (side.getAxis().isHorizontal() && worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  75 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  77 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube())
/*     */       {
/*  79 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  92 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false));
/*     */     
/*  94 */     if (facing.getAxis().isHorizontal())
/*     */     {
/*  96 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */     
/*  99 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 107 */     func_176260_a(worldIn, pos, state, false, false, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 115 */     if (neighborBlock != this)
/*     */     {
/* 117 */       if (checkForDrop(worldIn, pos, state)) {
/*     */         
/* 119 */         EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */         
/* 121 */         if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().isNormalCube()) {
/*     */           
/* 123 */           dropBlockAsItem(worldIn, pos, state, 0);
/* 124 */           worldIn.setBlockToAir(pos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_176260_a(World worldIn, BlockPos pos, IBlockState hookState, boolean p_176260_4_, boolean p_176260_5_, int p_176260_6_, IBlockState p_176260_7_) {
/*     */     int k, m;
/* 132 */     EnumFacing enumfacing = (EnumFacing)hookState.getValue((IProperty)FACING);
/* 133 */     int flag = ((Boolean)hookState.getValue((IProperty)ATTACHED)).booleanValue();
/* 134 */     boolean flag1 = ((Boolean)hookState.getValue((IProperty)POWERED)).booleanValue();
/* 135 */     boolean flag2 = !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/* 136 */     boolean flag3 = !p_176260_4_;
/* 137 */     boolean flag4 = false;
/* 138 */     int i = 0;
/* 139 */     IBlockState[] aiblockstate = new IBlockState[42];
/*     */     
/* 141 */     for (int j = 1; j < 42; j++) {
/*     */       
/* 143 */       BlockPos blockpos = pos.offset(enumfacing, j);
/* 144 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 146 */       if (iblockstate.getBlock() == Blocks.tripwire_hook) {
/*     */         
/* 148 */         if (iblockstate.getValue((IProperty)FACING) == enumfacing.getOpposite())
/*     */         {
/* 150 */           i = j;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 156 */       if (iblockstate.getBlock() != Blocks.tripwire && j != p_176260_6_) {
/*     */         
/* 158 */         aiblockstate[j] = null;
/* 159 */         flag3 = false;
/*     */       }
/*     */       else {
/*     */         
/* 163 */         if (j == p_176260_6_)
/*     */         {
/* 165 */           iblockstate = (IBlockState)Objects.firstNonNull(p_176260_7_, iblockstate);
/*     */         }
/*     */         
/* 168 */         int flag5 = !((Boolean)iblockstate.getValue((IProperty)BlockTripWire.DISARMED)).booleanValue() ? 1 : 0;
/* 169 */         boolean flag6 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.POWERED)).booleanValue();
/* 170 */         boolean flag7 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.SUSPENDED)).booleanValue();
/* 171 */         int n = flag3 & ((flag7 == flag2) ? 1 : 0);
/* 172 */         m = flag4 | ((flag5 && flag6) ? 1 : 0);
/* 173 */         aiblockstate[j] = iblockstate;
/*     */         
/* 175 */         if (j == p_176260_6_) {
/*     */           
/* 177 */           worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 178 */           k = n & flag5;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     k &= (i > 1) ? 1 : 0;
/* 184 */     m &= k;
/* 185 */     IBlockState iblockstate1 = getDefaultState().withProperty((IProperty)ATTACHED, Boolean.valueOf(k)).withProperty((IProperty)POWERED, Boolean.valueOf(m));
/*     */     
/* 187 */     if (i > 0) {
/*     */       
/* 189 */       BlockPos blockpos1 = pos.offset(enumfacing, i);
/* 190 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 191 */       worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing1), 3);
/* 192 */       func_176262_b(worldIn, blockpos1, enumfacing1);
/* 193 */       func_180694_a(worldIn, blockpos1, k, m, flag, flag1);
/*     */     } 
/*     */     
/* 196 */     func_180694_a(worldIn, pos, k, m, flag, flag1);
/*     */     
/* 198 */     if (!p_176260_4_) {
/*     */       
/* 200 */       worldIn.setBlockState(pos, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing), 3);
/*     */       
/* 202 */       if (p_176260_5_)
/*     */       {
/* 204 */         func_176262_b(worldIn, pos, enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 208 */     if (flag != k)
/*     */     {
/* 210 */       for (int n = 1; n < i; n++) {
/*     */         
/* 212 */         BlockPos blockpos2 = pos.offset(enumfacing, n);
/* 213 */         IBlockState iblockstate2 = aiblockstate[n];
/*     */         
/* 215 */         if (iblockstate2 != null && worldIn.getBlockState(blockpos2).getBlock() != Blocks.air)
/*     */         {
/* 217 */           worldIn.setBlockState(blockpos2, iblockstate2.withProperty((IProperty)ATTACHED, Boolean.valueOf(k)), 3);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 232 */     func_176260_a(worldIn, pos, state, false, true, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_180694_a(World worldIn, BlockPos pos, boolean p_180694_3_, boolean p_180694_4_, boolean p_180694_5_, boolean p_180694_6_) {
/* 237 */     if (p_180694_4_ && !p_180694_6_) {
/*     */       
/* 239 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
/*     */     }
/* 241 */     else if (!p_180694_4_ && p_180694_6_) {
/*     */       
/* 243 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.5F);
/*     */     }
/* 245 */     else if (p_180694_3_ && !p_180694_5_) {
/*     */       
/* 247 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.7F);
/*     */     }
/* 249 */     else if (!p_180694_3_ && p_180694_5_) {
/*     */       
/* 251 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.bowhit", 0.4F, 1.2F / (worldIn.rand.nextFloat() * 0.2F + 0.9F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_176262_b(World worldIn, BlockPos p_176262_2_, EnumFacing p_176262_3_) {
/* 257 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_, this);
/* 258 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_.offset(p_176262_3_.getOpposite()), this);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 263 */     if (!canPlaceBlockAt(worldIn, pos)) {
/*     */       
/* 265 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 266 */       worldIn.setBlockToAir(pos);
/* 267 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 278 */     float f = 0.1875F;
/*     */     
/* 280 */     switch ((EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/* 283 */         setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 287 */         setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 291 */         setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 295 */         setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 301 */     boolean flag = ((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue();
/* 302 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 304 */     if (flag || flag1)
/*     */     {
/* 306 */       func_176260_a(worldIn, pos, state, true, false, -1, (IBlockState)null);
/*     */     }
/*     */     
/* 309 */     if (flag1) {
/*     */       
/* 311 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 312 */       worldIn.notifyNeighborsOfStateChange(pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite()), this);
/*     */     } 
/*     */     
/* 315 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 320 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 325 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((state.getValue((IProperty)FACING) == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 333 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 338 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 346 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 354 */     int i = 0;
/* 355 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 357 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 359 */       i |= 0x8;
/*     */     }
/*     */     
/* 362 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue())
/*     */     {
/* 364 */       i |= 0x4;
/*     */     }
/*     */     
/* 367 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 372 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED, (IProperty)ATTACHED, (IProperty)SUSPENDED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockTripWireHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */