/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WorldGenGlowStone1
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 15 */     if (!worldIn.isAirBlock(position))
/*    */     {
/* 17 */       return false;
/*    */     }
/* 19 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.netherrack)
/*    */     {
/* 21 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 25 */     worldIn.setBlockState(position, Blocks.glowstone.getDefaultState(), 2);
/*    */     
/* 27 */     for (int i = 0; i < 1500; i++) {
/*    */       
/* 29 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 31 */       if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
/*    */         
/* 33 */         int j = 0;
/*    */         
/* 35 */         for (EnumFacing enumfacing : EnumFacing.values()) {
/*    */           
/* 37 */           if (worldIn.getBlockState(blockpos.offset(enumfacing)).getBlock() == Blocks.glowstone)
/*    */           {
/* 39 */             j++;
/*    */           }
/*    */           
/* 42 */           if (j > 1) {
/*    */             break;
/*    */           }
/*    */         } 
/*    */ 
/*    */         
/* 48 */         if (j == 1)
/*    */         {
/* 50 */           worldIn.setBlockState(blockpos, Blocks.glowstone.getDefaultState(), 2);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenGlowStone1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */