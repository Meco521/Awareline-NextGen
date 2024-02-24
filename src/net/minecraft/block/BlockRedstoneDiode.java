/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public abstract class BlockRedstoneDiode
/*     */   extends BlockDirectional
/*     */ {
/*     */   protected final boolean isRepeaterPowered;
/*     */   
/*     */   protected BlockRedstoneDiode(boolean powered) {
/*  23 */     super(Material.circuits);
/*  24 */     this.isRepeaterPowered = powered;
/*  25 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  35 */     return World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) ? super.canPlaceBlockAt(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/*  40 */     return World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
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
/*  52 */     if (!isLocked((IBlockAccess)worldIn, pos, state)) {
/*     */       
/*  54 */       boolean flag = shouldBePowered(worldIn, pos, state);
/*     */       
/*  56 */       if (this.isRepeaterPowered && !flag) {
/*     */         
/*  58 */         worldIn.setBlockState(pos, getUnpoweredState(state), 2);
/*     */       }
/*  60 */       else if (!this.isRepeaterPowered) {
/*     */         
/*  62 */         worldIn.setBlockState(pos, getPoweredState(state), 2);
/*     */         
/*  64 */         if (!flag)
/*     */         {
/*  66 */           worldIn.updateBlockTick(pos, getPoweredState(state).getBlock(), getTickDelay(state), -1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  74 */     return (side.getAxis() != EnumFacing.Axis.Y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isPowered(IBlockState state) {
/*  79 */     return this.isRepeaterPowered;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  84 */     return getWeakPower(worldIn, pos, state, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  89 */     return !isPowered(state) ? 0 : ((state.getValue((IProperty)FACING) == side) ? getActiveSignal(worldIn, pos, state) : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  97 */     if (canBlockStay(worldIn, pos)) {
/*     */       
/*  99 */       updateState(worldIn, pos, state);
/*     */     }
/*     */     else {
/*     */       
/* 103 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 104 */       worldIn.setBlockToAir(pos);
/*     */       
/* 106 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 108 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 115 */     if (!isLocked((IBlockAccess)worldIn, pos, state)) {
/*     */       
/* 117 */       boolean flag = shouldBePowered(worldIn, pos, state);
/*     */       
/* 119 */       if (((this.isRepeaterPowered && !flag) || (!this.isRepeaterPowered && flag)) && !worldIn.isBlockTickPending(pos, this)) {
/*     */         
/* 121 */         int i = -1;
/*     */         
/* 123 */         if (isFacingTowardsRepeater(worldIn, pos, state)) {
/*     */           
/* 125 */           i = -3;
/*     */         }
/* 127 */         else if (this.isRepeaterPowered) {
/*     */           
/* 129 */           i = -2;
/*     */         } 
/*     */         
/* 132 */         worldIn.updateBlockTick(pos, this, getDelay(state), i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
/* 144 */     return (calculateInputStrength(worldIn, pos, state) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
/* 149 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 150 */     BlockPos blockpos = pos.offset(enumfacing);
/* 151 */     int i = worldIn.getRedstonePower(blockpos, enumfacing);
/*     */     
/* 153 */     if (i >= 15)
/*     */     {
/* 155 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 159 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 160 */     return Math.max(i, (iblockstate.getBlock() == Blocks.redstone_wire) ? ((Integer)iblockstate.getValue((IProperty)BlockRedstoneWire.POWER)).intValue() : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 166 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 167 */     EnumFacing enumfacing1 = enumfacing.rotateY();
/* 168 */     EnumFacing enumfacing2 = enumfacing.rotateYCCW();
/* 169 */     return Math.max(getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1), getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 174 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 175 */     Block block = iblockstate.getBlock();
/* 176 */     return canPowerSide(block) ? ((block == Blocks.redstone_wire) ? ((Integer)iblockstate.getValue((IProperty)BlockRedstoneWire.POWER)).intValue() : worldIn.getStrongPower(pos, side)) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 193 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 201 */     if (shouldBePowered(worldIn, pos, state))
/*     */     {
/* 203 */       worldIn.scheduleUpdate(pos, this, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 209 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
/* 214 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 215 */     BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/* 216 */     worldIn.notifyBlockOfStateChange(blockpos, this);
/* 217 */     worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/* 225 */     if (this.isRepeaterPowered)
/*     */     {
/* 227 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 229 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     }
/*     */     
/* 233 */     super.onBlockDestroyedByPlayer(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canPowerSide(Block blockIn) {
/* 246 */     return blockIn.canProvidePower();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 251 */     return 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isRedstoneRepeaterBlockID(Block blockIn) {
/* 256 */     return (Blocks.unpowered_repeater.isAssociated(blockIn) || Blocks.unpowered_comparator.isAssociated(blockIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAssociated(Block other) {
/* 261 */     return (other == getPoweredState(getDefaultState()).getBlock() || other == getUnpoweredState(getDefaultState()).getBlock());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFacingTowardsRepeater(World worldIn, BlockPos pos, IBlockState state) {
/* 266 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/* 267 */     BlockPos blockpos = pos.offset(enumfacing);
/* 268 */     return isRedstoneRepeaterBlockID(worldIn.getBlockState(blockpos).getBlock()) ? ((worldIn.getBlockState(blockpos).getValue((IProperty)FACING) != enumfacing)) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getTickDelay(IBlockState state) {
/* 273 */     return getDelay(state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int getDelay(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState getPoweredState(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState getUnpoweredState(IBlockState paramIBlockState);
/*     */   
/*     */   public boolean isAssociatedBlock(Block other) {
/* 284 */     return isAssociated(other);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 289 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockRedstoneDiode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */