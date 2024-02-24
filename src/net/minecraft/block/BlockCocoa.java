/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCocoa
/*     */   extends BlockDirectional
/*     */   implements IGrowable {
/*  25 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
/*     */ 
/*     */   
/*     */   public BlockCocoa() {
/*  29 */     super(Material.plants);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  31 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  36 */     if (!canBlockStay(worldIn, pos, state)) {
/*     */       
/*  38 */       dropBlock(worldIn, pos, state);
/*     */     }
/*  40 */     else if (worldIn.rand.nextInt(5) == 0) {
/*     */       
/*  42 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/*  44 */       if (i < 2)
/*     */       {
/*  46 */         worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i + 1)), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  53 */     pos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*  54 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  55 */     return (iblockstate.getBlock() == Blocks.log && iblockstate.getValue((IProperty)BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  60 */     return false;
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
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  73 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  74 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  79 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  80 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  86 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  87 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*  88 */     int i = ((Integer)iblockstate.getValue((IProperty)AGE)).intValue();
/*  89 */     int j = 4 + (i << 1);
/*  90 */     int k = 5 + (i << 1);
/*  91 */     float f = j / 2.0F;
/*     */     
/*  93 */     switch (enumfacing) {
/*     */       
/*     */       case SOUTH:
/*  96 */         setBlockBounds((8.0F - f) / 16.0F, (12.0F - k) / 16.0F, (15.0F - j) / 16.0F, (8.0F + f) / 16.0F, 0.75F, 0.9375F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 100 */         setBlockBounds((8.0F - f) / 16.0F, (12.0F - k) / 16.0F, 0.0625F, (8.0F + f) / 16.0F, 0.75F, (1.0F + j) / 16.0F);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 104 */         setBlockBounds(0.0625F, (12.0F - k) / 16.0F, (8.0F - f) / 16.0F, (1.0F + j) / 16.0F, 0.75F, (8.0F + f) / 16.0F);
/*     */         break;
/*     */       
/*     */       case EAST:
/* 108 */         setBlockBounds((15.0F - j) / 16.0F, (12.0F - k) / 16.0F, (8.0F - f) / 16.0F, 0.9375F, 0.75F, (8.0F + f) / 16.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 117 */     EnumFacing enumfacing = EnumFacing.fromAngle(placer.rotationYaw);
/* 118 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing), 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 127 */     if (!facing.getAxis().isHorizontal())
/*     */     {
/* 129 */       facing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 132 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing.getOpposite()).withProperty((IProperty)AGE, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 140 */     if (!canBlockStay(worldIn, pos, state))
/*     */     {
/* 142 */       dropBlock(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 148 */     worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 3);
/* 149 */     dropBlockAsItem(worldIn, pos, state, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 157 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/* 158 */     int j = 1;
/*     */     
/* 160 */     if (i >= 2)
/*     */     {
/* 162 */       j = 3;
/*     */     }
/*     */     
/* 165 */     for (int k = 0; k < j; k++)
/*     */     {
/* 167 */       spawnAsEntity(worldIn, pos, new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeDamage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 173 */     return Items.dye;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 181 */     return EnumDyeColor.BROWN.getDyeDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 189 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 199 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(((Integer)state.getValue((IProperty)AGE)).intValue() + 1)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 204 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 212 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)AGE, Integer.valueOf((meta & 0xF) >> 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 220 */     int i = 0;
/* 221 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 222 */     i |= ((Integer)state.getValue((IProperty)AGE)).intValue() << 2;
/* 223 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 228 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockCocoa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */