/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTorch extends Block {
/*  19 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
/*     */       {
/*     */         public boolean apply(EnumFacing p_apply_1_)
/*     */         {
/*  23 */           return (p_apply_1_ != EnumFacing.DOWN);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   protected BlockTorch() {
/*  29 */     super(Material.circuits);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  31 */     setTickRandomly(true);
/*  32 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceOn(World worldIn, BlockPos pos) {
/*  55 */     if (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos))
/*     */     {
/*  57 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  61 */     Block block = worldIn.getBlockState(pos).getBlock();
/*  62 */     return (block instanceof BlockFence || block == Blocks.glass || block == Blocks.cobblestone_wall || block == Blocks.stained_glass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  68 */     for (EnumFacing enumfacing : FACING.getAllowedValues()) {
/*     */       
/*  70 */       if (canPlaceAt(worldIn, pos, enumfacing))
/*     */       {
/*  72 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
/*  81 */     BlockPos blockpos = pos.offset(facing.getOpposite());
/*  82 */     boolean flag = facing.getAxis().isHorizontal();
/*  83 */     return ((flag && worldIn.isBlockNormalCube(blockpos, true)) || (facing.equals(EnumFacing.UP) && canPlaceOn(worldIn, blockpos)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  92 */     if (canPlaceAt(worldIn, pos, facing))
/*     */     {
/*  94 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */ 
/*     */     
/*  98 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 100 */       if (worldIn.isBlockNormalCube(pos.offset(enumfacing.getOpposite()), true))
/*     */       {
/* 102 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 112 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 120 */     onNeighborChangeInternal(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
/* 125 */     if (!checkForDrop(worldIn, pos, state))
/*     */     {
/* 127 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 131 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 132 */     EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
/* 133 */     EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 134 */     boolean flag = false;
/*     */     
/* 136 */     if (enumfacing$axis.isHorizontal() && !worldIn.isBlockNormalCube(pos.offset(enumfacing1), true)) {
/*     */       
/* 138 */       flag = true;
/*     */     }
/* 140 */     else if (enumfacing$axis.isVertical() && !canPlaceOn(worldIn, pos.offset(enumfacing1))) {
/*     */       
/* 142 */       flag = true;
/*     */     } 
/*     */     
/* 145 */     if (flag) {
/*     */       
/* 147 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 148 */       worldIn.setBlockToAir(pos);
/* 149 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 160 */     if (state.getBlock() == this && canPlaceAt(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING)))
/*     */     {
/* 162 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 166 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/*     */       
/* 168 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 169 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 181 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 182 */     float f = 0.15F;
/*     */     
/* 184 */     if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 186 */       setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */     }
/* 188 */     else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 190 */       setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */     }
/* 192 */     else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 194 */       setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */     }
/* 196 */     else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 198 */       setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 202 */       f = 0.1F;
/* 203 */       setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     } 
/*     */     
/* 206 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 211 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 212 */     double d0 = pos.getX() + 0.5D;
/* 213 */     double d1 = pos.getY() + 0.7D;
/* 214 */     double d2 = pos.getZ() + 0.5D;
/* 215 */     double d3 = 0.22D;
/* 216 */     double d4 = 0.27D;
/*     */     
/* 218 */     if (enumfacing.getAxis().isHorizontal()) {
/*     */       
/* 220 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 221 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/* 222 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     else {
/*     */       
/* 226 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/* 227 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 233 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 241 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 243 */     switch (meta)
/*     */     
/*     */     { case 1:
/* 246 */         iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.EAST);
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
/* 266 */         return iblockstate;case 2: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.WEST); return iblockstate;case 3: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH); return iblockstate;case 4: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH); return iblockstate; }  iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP); return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 274 */     int i = 0;
/*     */     
/* 276 */     switch ((EnumFacing)state.getValue((IProperty)FACING))
/*     */     
/*     */     { case EAST:
/* 279 */         i |= 0x1;
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
/* 300 */         return i;case WEST: i |= 0x2; return i;case SOUTH: i |= 0x3; return i;case NORTH: i |= 0x4; return i; }  i |= 0x5; return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 305 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockTorch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */