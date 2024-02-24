/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class BlockBush
/*    */   extends Block
/*    */ {
/*    */   protected BlockBush() {
/* 19 */     this(Material.plants);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockBush(Material materialIn) {
/* 24 */     this(materialIn, materialIn.getMaterialMapColor());
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockBush(Material p_i46452_1_, MapColor p_i46452_2_) {
/* 29 */     super(p_i46452_1_, p_i46452_2_);
/* 30 */     setTickRandomly(true);
/* 31 */     float f = 0.2F;
/* 32 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
/* 33 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 38 */     return (super.canPlaceBlockAt(worldIn, pos) && canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canPlaceBlockOn(Block ground) {
/* 46 */     return (ground == Blocks.grass || ground == Blocks.dirt || ground == Blocks.farmland);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 54 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/* 55 */     checkAndDropBlock(worldIn, pos, state);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 60 */     checkAndDropBlock(worldIn, pos, state);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 65 */     if (!canBlockStay(worldIn, pos, state)) {
/*    */       
/* 67 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 68 */       worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 74 */     return canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock());
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 87 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFullCube() {
/* 92 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 97 */     return EnumWorldBlockLayer.CUTOUT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockBush.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */