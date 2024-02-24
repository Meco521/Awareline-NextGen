/*     */ package net.minecraft.block;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCactus extends Block {
/*  18 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*     */ 
/*     */   
/*     */   protected BlockCactus() {
/*  22 */     super(Material.cactus);
/*  23 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  24 */     setTickRandomly(true);
/*  25 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  30 */     BlockPos blockpos = pos.up();
/*     */     
/*  32 */     if (worldIn.isAirBlock(blockpos)) {
/*     */       int i;
/*     */ 
/*     */       
/*  36 */       for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; i++);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  41 */       if (i < 3) {
/*     */         
/*  43 */         int j = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/*  45 */         if (j == 15) {
/*     */           
/*  47 */           worldIn.setBlockState(blockpos, getDefaultState());
/*  48 */           IBlockState iblockstate = state.withProperty((IProperty)AGE, Integer.valueOf(0));
/*  49 */           worldIn.setBlockState(pos, iblockstate, 4);
/*  50 */           onNeighborBlockChange(worldIn, blockpos, iblockstate, this);
/*     */         }
/*     */         else {
/*     */           
/*  54 */           worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(j + 1)), 4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  62 */     float f = 0.0625F;
/*  63 */     return new AxisAlignedBB((pos.getX() + f), pos.getY(), (pos.getZ() + f), ((pos.getX() + 1) - f), ((pos.getY() + 1) - f), ((pos.getZ() + 1) - f));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  68 */     float f = 0.0625F;
/*  69 */     return new AxisAlignedBB((pos.getX() + f), pos.getY(), (pos.getZ() + f), ((pos.getX() + 1) - f), (pos.getY() + 1), ((pos.getZ() + 1) - f));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  87 */     return super.canPlaceBlockAt(worldIn, pos) ? canBlockStay(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  95 */     if (!canBlockStay(worldIn, pos))
/*     */     {
/*  97 */       worldIn.destroyBlock(pos, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/* 103 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 105 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getMaterial().isSolid())
/*     */       {
/* 107 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     Block block = worldIn.getBlockState(pos.down()).getBlock();
/* 112 */     return (block == Blocks.cactus || block == Blocks.sand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 120 */     entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 125 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 133 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 141 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 146 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockCactus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */