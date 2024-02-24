/*     */ package net.minecraft.block;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneWire extends Block {
/*  25 */   public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
/*  26 */   public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
/*  27 */   public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
/*  28 */   public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);
/*  29 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*     */   private boolean canProvidePower = true;
/*  31 */   private final Set<BlockPos> blocksNeedingUpdate = Sets.newHashSet();
/*     */ 
/*     */   
/*     */   public BlockRedstoneWire() {
/*  35 */     super(Material.circuits);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, EnumAttachPosition.NONE).withProperty((IProperty)EAST, EnumAttachPosition.NONE).withProperty((IProperty)SOUTH, EnumAttachPosition.NONE).withProperty((IProperty)WEST, EnumAttachPosition.NONE).withProperty((IProperty)POWER, Integer.valueOf(0)));
/*  37 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  46 */     state = state.withProperty((IProperty)WEST, getAttachPosition(worldIn, pos, EnumFacing.WEST));
/*  47 */     state = state.withProperty((IProperty)EAST, getAttachPosition(worldIn, pos, EnumFacing.EAST));
/*  48 */     state = state.withProperty((IProperty)NORTH, getAttachPosition(worldIn, pos, EnumFacing.NORTH));
/*  49 */     state = state.withProperty((IProperty)SOUTH, getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
/*  50 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
/*  55 */     BlockPos blockpos = pos.offset(direction);
/*  56 */     Block block = worldIn.getBlockState(pos.offset(direction)).getBlock();
/*     */     
/*  58 */     if (!canConnectTo(worldIn.getBlockState(blockpos), direction) && (block.isBlockNormalCube() || !canConnectUpwardsTo(worldIn.getBlockState(blockpos.down())))) {
/*     */       
/*  60 */       Block block1 = worldIn.getBlockState(pos.up()).getBlock();
/*  61 */       return (!block1.isBlockNormalCube() && block.isBlockNormalCube() && canConnectUpwardsTo(worldIn.getBlockState(blockpos.up()))) ? EnumAttachPosition.UP : EnumAttachPosition.NONE;
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return EnumAttachPosition.SIDE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  89 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  90 */     return (iblockstate.getBlock() != this) ? super.colorMultiplier(worldIn, pos, renderPass) : colorMultiplier(((Integer)iblockstate.getValue((IProperty)POWER)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  95 */     return (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) || worldIn.getBlockState(pos.down()).getBlock() == Blocks.glowstone);
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState updateSurroundingRedstone(World worldIn, BlockPos pos, IBlockState state) {
/* 100 */     state = calculateCurrentChanges(worldIn, pos, pos, state);
/* 101 */     List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
/* 102 */     this.blocksNeedingUpdate.clear();
/*     */     
/* 104 */     for (BlockPos blockpos : list)
/*     */     {
/* 106 */       worldIn.notifyNeighborsOfStateChange(blockpos, this);
/*     */     }
/*     */     
/* 109 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState calculateCurrentChanges(World worldIn, BlockPos pos1, BlockPos pos2, IBlockState state) {
/* 114 */     IBlockState iblockstate = state;
/* 115 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/* 116 */     int j = 0;
/* 117 */     j = getMaxCurrentStrength(worldIn, pos2, j);
/* 118 */     this.canProvidePower = false;
/* 119 */     int k = worldIn.isBlockIndirectlyGettingPowered(pos1);
/* 120 */     this.canProvidePower = true;
/*     */     
/* 122 */     if (k > 0 && k > j - 1)
/*     */     {
/* 124 */       j = k;
/*     */     }
/*     */     
/* 127 */     int l = 0;
/*     */     
/* 129 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 131 */       BlockPos blockpos = pos1.offset(enumfacing);
/* 132 */       boolean flag = (blockpos.getX() != pos2.getX() || blockpos.getZ() != pos2.getZ());
/*     */       
/* 134 */       if (flag)
/*     */       {
/* 136 */         l = getMaxCurrentStrength(worldIn, blockpos, l);
/*     */       }
/*     */       
/* 139 */       if (worldIn.getBlockState(blockpos).getBlock().isNormalCube() && !worldIn.getBlockState(pos1.up()).getBlock().isNormalCube()) {
/*     */         
/* 141 */         if (flag && pos1.getY() >= pos2.getY())
/*     */         {
/* 143 */           l = getMaxCurrentStrength(worldIn, blockpos.up(), l); } 
/*     */         continue;
/*     */       } 
/* 146 */       if (!worldIn.getBlockState(blockpos).getBlock().isNormalCube() && flag && pos1.getY() <= pos2.getY())
/*     */       {
/* 148 */         l = getMaxCurrentStrength(worldIn, blockpos.down(), l);
/*     */       }
/*     */     } 
/*     */     
/* 152 */     if (l > j) {
/*     */       
/* 154 */       j = l - 1;
/*     */     }
/* 156 */     else if (j > 0) {
/*     */       
/* 158 */       j--;
/*     */     }
/*     */     else {
/*     */       
/* 162 */       j = 0;
/*     */     } 
/*     */     
/* 165 */     if (k > j - 1)
/*     */     {
/* 167 */       j = k;
/*     */     }
/*     */     
/* 170 */     if (i != j) {
/*     */       
/* 172 */       state = state.withProperty((IProperty)POWER, Integer.valueOf(j));
/*     */       
/* 174 */       if (worldIn.getBlockState(pos1) == iblockstate)
/*     */       {
/* 176 */         worldIn.setBlockState(pos1, state, 2);
/*     */       }
/*     */       
/* 179 */       this.blocksNeedingUpdate.add(pos1);
/*     */       
/* 181 */       for (EnumFacing enumfacing1 : EnumFacing.values())
/*     */       {
/* 183 */         this.blocksNeedingUpdate.add(pos1.offset(enumfacing1));
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
/* 196 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/*     */       
/* 198 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */       
/* 200 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 202 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 209 */     if (!worldIn.isRemote) {
/*     */       
/* 211 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 213 */       for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL)
/*     */       {
/* 215 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */       
/* 218 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/* 220 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 223 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 225 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 227 */         if (worldIn.getBlockState(blockpos).getBlock().isNormalCube()) {
/*     */           
/* 229 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
/*     */           
/*     */           continue;
/*     */         } 
/* 233 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 241 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 243 */     if (!worldIn.isRemote) {
/*     */       
/* 245 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 247 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */       
/* 250 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 252 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/* 254 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 257 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 259 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 261 */         if (worldIn.getBlockState(blockpos).getBlock().isNormalCube()) {
/*     */           
/* 263 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
/*     */           
/*     */           continue;
/*     */         } 
/* 267 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getMaxCurrentStrength(World worldIn, BlockPos pos, int strength) {
/* 275 */     if (worldIn.getBlockState(pos).getBlock() != this)
/*     */     {
/* 277 */       return strength;
/*     */     }
/*     */ 
/*     */     
/* 281 */     int i = ((Integer)worldIn.getBlockState(pos).getValue((IProperty)POWER)).intValue();
/* 282 */     return (i > strength) ? i : strength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 291 */     if (!worldIn.isRemote)
/*     */     {
/* 293 */       if (canPlaceBlockAt(worldIn, pos)) {
/*     */         
/* 295 */         updateSurroundingRedstone(worldIn, pos, state);
/*     */       }
/*     */       else {
/*     */         
/* 299 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 300 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 310 */     return Items.redstone;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 315 */     return !this.canProvidePower ? 0 : getWeakPower(worldIn, pos, state, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 320 */     if (!this.canProvidePower)
/*     */     {
/* 322 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 326 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */     
/* 328 */     if (i == 0)
/*     */     {
/* 330 */       return 0;
/*     */     }
/* 332 */     if (side == EnumFacing.UP)
/*     */     {
/* 334 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 338 */     EnumSet<EnumFacing> enumset = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 340 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 342 */       if (func_176339_d(worldIn, pos, enumfacing))
/*     */       {
/* 344 */         enumset.add(enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 348 */     if (side.getAxis().isHorizontal() && enumset.isEmpty())
/*     */     {
/* 350 */       return i;
/*     */     }
/* 352 */     if (enumset.contains(side) && !enumset.contains(side.rotateYCCW()) && !enumset.contains(side.rotateY()))
/*     */     {
/* 354 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 358 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_176339_d(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 366 */     BlockPos blockpos = pos.offset(side);
/* 367 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 368 */     Block block = iblockstate.getBlock();
/* 369 */     boolean flag = block.isNormalCube();
/* 370 */     boolean flag1 = worldIn.getBlockState(pos.up()).getBlock().isNormalCube();
/* 371 */     return (!flag1 && flag && canConnectUpwardsTo(worldIn, blockpos.up())) ? true : (canConnectTo(iblockstate, side) ? true : ((block == Blocks.powered_repeater && iblockstate.getValue((IProperty)BlockRedstoneDiode.FACING) == side) ? true : ((!flag && canConnectUpwardsTo(worldIn, blockpos.down())))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
/* 376 */     return canConnectUpwardsTo(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockState state) {
/* 381 */     return canConnectTo(state, (EnumFacing)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectTo(IBlockState blockState, EnumFacing side) {
/* 386 */     Block block = blockState.getBlock();
/*     */     
/* 388 */     if (block == Blocks.redstone_wire)
/*     */     {
/* 390 */       return true;
/*     */     }
/* 392 */     if (Blocks.unpowered_repeater.isAssociated(block)) {
/*     */       
/* 394 */       EnumFacing enumfacing = (EnumFacing)blockState.getValue((IProperty)BlockRedstoneRepeater.FACING);
/* 395 */       return (enumfacing == side || enumfacing.getOpposite() == side);
/*     */     } 
/*     */ 
/*     */     
/* 399 */     return (block.canProvidePower() && side != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 408 */     return this.canProvidePower;
/*     */   }
/*     */ 
/*     */   
/*     */   private int colorMultiplier(int powerLevel) {
/* 413 */     float f = powerLevel / 15.0F;
/* 414 */     float f1 = f * 0.6F + 0.4F;
/*     */     
/* 416 */     if (powerLevel == 0)
/*     */     {
/* 418 */       f1 = 0.3F;
/*     */     }
/*     */     
/* 421 */     float f2 = f * f * 0.7F - 0.5F;
/* 422 */     float f3 = f * f * 0.6F - 0.7F;
/*     */     
/* 424 */     if (f2 < 0.0F)
/*     */     {
/* 426 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 429 */     if (f3 < 0.0F)
/*     */     {
/* 431 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 434 */     int i = MathHelper.clamp_int((int)(f1 * 255.0F), 0, 255);
/* 435 */     int j = MathHelper.clamp_int((int)(f2 * 255.0F), 0, 255);
/* 436 */     int k = MathHelper.clamp_int((int)(f3 * 255.0F), 0, 255);
/* 437 */     return 0xFF000000 | i << 16 | j << 8 | k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 442 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */     
/* 444 */     if (i != 0) {
/*     */       
/* 446 */       double d0 = pos.getX() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 447 */       double d1 = (pos.getY() + 0.0625F);
/* 448 */       double d2 = pos.getZ() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 449 */       float f = i / 15.0F;
/* 450 */       float f1 = f * 0.6F + 0.4F;
/* 451 */       float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
/* 452 */       float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
/* 453 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, f1, f2, f3, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 459 */     return Items.redstone;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 464 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 472 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 480 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 485 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)POWER });
/*     */   }
/*     */   
/*     */   enum EnumAttachPosition
/*     */     implements IStringSerializable {
/* 490 */     UP("up"),
/* 491 */     SIDE("side"),
/* 492 */     NONE("none");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumAttachPosition(String name) {
/* 498 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 503 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 508 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockRedstoneWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */