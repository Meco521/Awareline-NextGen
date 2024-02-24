/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WorldGenWaterlily
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 13 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 15 */       int j = position.getX() + rand.nextInt(8) - rand.nextInt(8);
/* 16 */       int k = position.getY() + rand.nextInt(4) - rand.nextInt(4);
/* 17 */       int l = position.getZ() + rand.nextInt(8) - rand.nextInt(8);
/*    */       
/* 19 */       if (worldIn.isAirBlock(new BlockPos(j, k, l)) && Blocks.waterlily.canPlaceBlockAt(worldIn, new BlockPos(j, k, l)))
/*    */       {
/* 21 */         worldIn.setBlockState(new BlockPos(j, k, l), Blocks.waterlily.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenWaterlily.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */