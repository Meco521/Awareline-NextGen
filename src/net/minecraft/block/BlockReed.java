/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockReed
/*     */   extends Block
/*     */ {
/*  22 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*     */ 
/*     */   
/*     */   protected BlockReed() {
/*  26 */     super(Material.plants);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  28 */     float f = 0.375F;
/*  29 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*  30 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  35 */     if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.reeds || checkForDrop(worldIn, pos, state))
/*     */     {
/*  37 */       if (worldIn.isAirBlock(pos.up())) {
/*     */         int i;
/*     */ 
/*     */         
/*  41 */         for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; i++);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  46 */         if (i < 3) {
/*     */           
/*  48 */           int j = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */           
/*  50 */           if (j == 15) {
/*     */             
/*  52 */             worldIn.setBlockState(pos.up(), getDefaultState());
/*  53 */             worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(0)), 4);
/*     */           }
/*     */           else {
/*     */             
/*  57 */             worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(j + 1)), 4);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  66 */     Block block = worldIn.getBlockState(pos.down()).getBlock();
/*     */     
/*  68 */     if (block == this)
/*     */     {
/*  70 */       return true;
/*     */     }
/*  72 */     if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand)
/*     */     {
/*  74 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  78 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  80 */       if (worldIn.getBlockState(pos.offset(enumfacing).down()).getBlock().getMaterial() == Material.water)
/*     */       {
/*  82 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  95 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 100 */     if (canBlockStay(worldIn, pos))
/*     */     {
/* 102 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 106 */     dropBlockAsItem(worldIn, pos, state, 0);
/* 107 */     worldIn.setBlockToAir(pos);
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/* 114 */     return canPlaceBlockAt(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 127 */     return Items.reeds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 145 */     return Items.reeds;
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 150 */     return worldIn.getBiomeGenForCoords(pos).getGrassColorAtPos(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 155 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 163 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 171 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 176 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */