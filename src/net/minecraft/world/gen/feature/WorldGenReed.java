/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WorldGenReed
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 14 */     for (int i = 0; i < 20; i++) {
/*    */       
/* 16 */       BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
/*    */       
/* 18 */       if (worldIn.isAirBlock(blockpos)) {
/*    */         
/* 20 */         BlockPos blockpos1 = blockpos.down();
/*    */         
/* 22 */         if (worldIn.getBlockState(blockpos1.west()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos1.east()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos1.north()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos1.south()).getBlock().getMaterial() == Material.water) {
/*    */           
/* 24 */           int j = 2 + rand.nextInt(rand.nextInt(3) + 1);
/*    */           
/* 26 */           for (int k = 0; k < j; k++) {
/*    */             
/* 28 */             if (Blocks.reeds.canBlockStay(worldIn, blockpos))
/*    */             {
/* 30 */               worldIn.setBlockState(blockpos.up(k), Blocks.reeds.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */