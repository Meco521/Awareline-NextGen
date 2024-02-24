/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockButton
/*     */   extends Block
/*     */ {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  26 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */   
/*     */   private final boolean wooden;
/*     */   
/*     */   protected BlockButton(boolean wooden) {
/*  31 */     super(Material.circuits);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  33 */     setTickRandomly(true);
/*  34 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  35 */     this.wooden = wooden;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  48 */     return this.wooden ? 30 : 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  69 */     return func_181088_a(worldIn, pos, side.getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  74 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */       
/*  76 */       if (func_181088_a(worldIn, pos, enumfacing))
/*     */       {
/*  78 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_181088_a(World p_181088_0_, BlockPos p_181088_1_, EnumFacing p_181088_2_) {
/*  87 */     BlockPos blockpos = p_181088_1_.offset(p_181088_2_);
/*  88 */     return (p_181088_2_ == EnumFacing.DOWN) ? World.doesBlockHaveSolidTopSurface((IBlockAccess)p_181088_0_, blockpos) : p_181088_0_.getBlockState(blockpos).getBlock().isNormalCube();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  97 */     return func_181088_a(worldIn, pos, facing.getOpposite()) ? getDefaultState().withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)POWERED, Boolean.valueOf(false)) : getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.DOWN).withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 105 */     if (checkForDrop(worldIn, pos, state) && !func_181088_a(worldIn, pos, ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite())) {
/*     */       
/* 107 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 108 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 114 */     if (canPlaceBlockAt(worldIn, pos))
/*     */     {
/* 116 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 120 */     dropBlockAsItem(worldIn, pos, state, 0);
/* 121 */     worldIn.setBlockToAir(pos);
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 128 */     updateBlockBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateBlockBounds(IBlockState state) {
/* 133 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 134 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/* 135 */     float f = 0.25F;
/* 136 */     float f1 = 0.375F;
/* 137 */     float f2 = (flag ? true : 2) / 16.0F;
/* 138 */     float f3 = 0.125F;
/* 139 */     float f4 = 0.1875F;
/*     */     
/* 141 */     switch (enumfacing) {
/*     */       
/*     */       case EAST:
/* 144 */         setBlockBounds(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 148 */         setBlockBounds(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 152 */         setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 156 */         setBlockBounds(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
/*     */         break;
/*     */       
/*     */       case UP:
/* 160 */         setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
/*     */         break;
/*     */       
/*     */       case DOWN:
/* 164 */         setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 170 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 172 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 176 */     worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 3);
/* 177 */     worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 178 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/* 179 */     notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 180 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 187 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 189 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/*     */     }
/*     */     
/* 192 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 197 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 202 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((state.getValue((IProperty)FACING) == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 210 */     return true;
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
/* 222 */     if (!worldIn.isRemote)
/*     */     {
/* 224 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 226 */         if (this.wooden) {
/*     */           
/* 228 */           checkForArrows(worldIn, pos, state);
/*     */         }
/*     */         else {
/*     */           
/* 232 */           worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 233 */           notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 234 */           worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/* 235 */           worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 246 */     float f = 0.1875F;
/* 247 */     float f1 = 0.125F;
/* 248 */     float f2 = 0.125F;
/* 249 */     setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 257 */     if (!worldIn.isRemote)
/*     */     {
/* 259 */       if (this.wooden)
/*     */       {
/* 261 */         if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */         {
/* 263 */           checkForArrows(worldIn, pos, state);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkForArrows(World worldIn, BlockPos pos, IBlockState state) {
/* 271 */     updateBlockBounds(state);
/* 272 */     List<? extends Entity> list = worldIn.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ));
/* 273 */     boolean flag = !list.isEmpty();
/* 274 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 276 */     if (flag && !flag1) {
/*     */       
/* 278 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)));
/* 279 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 280 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 281 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 284 */     if (!flag && flag1) {
/*     */       
/* 286 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 287 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 288 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 289 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     } 
/*     */     
/* 292 */     if (flag)
/*     */     {
/* 294 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
/* 300 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 301 */     worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*     */     EnumFacing enumfacing;
/* 311 */     switch (meta & 0x7) {
/*     */       
/*     */       case 0:
/* 314 */         enumfacing = EnumFacing.DOWN;
/*     */         break;
/*     */       
/*     */       case 1:
/* 318 */         enumfacing = EnumFacing.EAST;
/*     */         break;
/*     */       
/*     */       case 2:
/* 322 */         enumfacing = EnumFacing.WEST;
/*     */         break;
/*     */       
/*     */       case 3:
/* 326 */         enumfacing = EnumFacing.SOUTH;
/*     */         break;
/*     */       
/*     */       case 4:
/* 330 */         enumfacing = EnumFacing.NORTH;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 335 */         enumfacing = EnumFacing.UP;
/*     */         break;
/*     */     } 
/* 338 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*     */     int i;
/* 348 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/* 351 */         i = 1;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 355 */         i = 2;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 359 */         i = 3;
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 363 */         i = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 368 */         i = 5;
/*     */         break;
/*     */       
/*     */       case DOWN:
/* 372 */         i = 0;
/*     */         break;
/*     */     } 
/* 375 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 377 */       i |= 0x8;
/*     */     }
/*     */     
/* 380 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 385 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */