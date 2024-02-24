/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public abstract class BlockBasePressurePlate
/*     */   extends Block
/*     */ {
/*     */   protected BlockBasePressurePlate(Material materialIn) {
/*  20 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockBasePressurePlate(Material p_i46401_1_, MapColor p_i46401_2_) {
/*  25 */     super(p_i46401_1_, p_i46401_2_);
/*  26 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  27 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  32 */     setBlockBoundsBasedOnState0(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setBlockBoundsBasedOnState0(IBlockState state) {
/*  37 */     boolean flag = (getRedstoneStrength(state) > 0);
/*  38 */     float f = 0.0625F;
/*     */     
/*  40 */     if (flag) {
/*     */       
/*  42 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
/*     */     }
/*     */     else {
/*     */       
/*  46 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  55 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawnInBlock() {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  91 */     return canBePlacedOn(worldIn, pos.down());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  99 */     if (!canBePlacedOn(worldIn, pos.down())) {
/*     */       
/* 101 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 102 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canBePlacedOn(World worldIn, BlockPos pos) {
/* 108 */     return (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos) || worldIn.getBlockState(pos).getBlock() instanceof BlockFence);
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
/* 120 */     if (!worldIn.isRemote) {
/*     */       
/* 122 */       int i = getRedstoneStrength(state);
/*     */       
/* 124 */       if (i > 0)
/*     */       {
/* 126 */         updateState(worldIn, pos, state, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 136 */     if (!worldIn.isRemote) {
/*     */       
/* 138 */       int i = getRedstoneStrength(state);
/*     */       
/* 140 */       if (i == 0)
/*     */       {
/* 142 */         updateState(worldIn, pos, state, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state, int oldRedstoneStrength) {
/* 152 */     int i = computeRedstoneStrength(worldIn, pos);
/* 153 */     boolean flag = (oldRedstoneStrength > 0);
/* 154 */     boolean flag1 = (i > 0);
/*     */     
/* 156 */     if (oldRedstoneStrength != i) {
/*     */       
/* 158 */       state = setRedstoneStrength(state, i);
/* 159 */       worldIn.setBlockState(pos, state, 2);
/* 160 */       updateNeighbors(worldIn, pos);
/* 161 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 164 */     if (!flag1 && flag) {
/*     */       
/* 166 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     }
/* 168 */     else if (flag1 && !flag) {
/*     */       
/* 170 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 173 */     if (flag1)
/*     */     {
/* 175 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AxisAlignedBB getSensitiveAABB(BlockPos pos) {
/* 184 */     float f = 0.125F;
/* 185 */     return new AxisAlignedBB((pos.getX() + 0.125F), pos.getY(), (pos.getZ() + 0.125F), ((pos.getX() + 1) - 0.125F), pos.getY() + 0.25D, ((pos.getZ() + 1) - 0.125F));
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 190 */     if (getRedstoneStrength(state) > 0)
/*     */     {
/* 192 */       updateNeighbors(worldIn, pos);
/*     */     }
/*     */     
/* 195 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateNeighbors(World worldIn, BlockPos pos) {
/* 203 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 204 */     worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 209 */     return getRedstoneStrength(state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 214 */     return (side == EnumFacing.UP) ? getRedstoneStrength(state) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 222 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 230 */     float f = 0.5F;
/* 231 */     float f1 = 0.125F;
/* 232 */     float f2 = 0.5F;
/* 233 */     setBlockBounds(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMobilityFlag() {
/* 238 */     return 1;
/*     */   }
/*     */   
/*     */   protected abstract int computeRedstoneStrength(World paramWorld, BlockPos paramBlockPos);
/*     */   
/*     */   protected abstract int getRedstoneStrength(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState setRedstoneStrength(IBlockState paramIBlockState, int paramInt);
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockBasePressurePlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */