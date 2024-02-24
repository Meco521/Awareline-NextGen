/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WorldGenShrub
/*    */   extends WorldGenTrees
/*    */ {
/*    */   private final IBlockState leavesMetadata;
/*    */   private final IBlockState woodMetadata;
/*    */   
/*    */   public WorldGenShrub(IBlockState p_i46450_1_, IBlockState p_i46450_2_) {
/* 19 */     super(false);
/* 20 */     this.woodMetadata = p_i46450_1_;
/* 21 */     this.leavesMetadata = p_i46450_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*    */     Block block;
/* 28 */     while (((block = worldIn.getBlockState(position).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && position.getY() > 0)
/*    */     {
/* 30 */       position = position.down();
/*    */     }
/*    */     
/* 33 */     Block block1 = worldIn.getBlockState(position).getBlock();
/*    */     
/* 35 */     if (block1 == Blocks.dirt || block1 == Blocks.grass) {
/*    */       
/* 37 */       position = position.up();
/* 38 */       setBlockAndNotifyAdequately(worldIn, position, this.woodMetadata);
/*    */       
/* 40 */       for (int i = position.getY(); i <= position.getY() + 2; i++) {
/*    */         
/* 42 */         int j = i - position.getY();
/* 43 */         int k = 2 - j;
/*    */         
/* 45 */         for (int l = position.getX() - k; l <= position.getX() + k; l++) {
/*    */           
/* 47 */           int i1 = l - position.getX();
/*    */           
/* 49 */           for (int j1 = position.getZ() - k; j1 <= position.getZ() + k; j1++) {
/*    */             
/* 51 */             int k1 = j1 - position.getZ();
/*    */             
/* 53 */             if (Math.abs(i1) != k || Math.abs(k1) != k || rand.nextInt(2) != 0) {
/*    */               
/* 55 */               BlockPos blockpos = new BlockPos(l, i, j1);
/*    */               
/* 57 */               if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock())
/*    */               {
/* 59 */                 setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenShrub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */