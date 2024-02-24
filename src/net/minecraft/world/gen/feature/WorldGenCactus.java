/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WorldGenCactus
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 13 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 15 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 17 */       if (worldIn.isAirBlock(blockpos)) {
/*    */         
/* 19 */         int j = 1 + rand.nextInt(rand.nextInt(3) + 1);
/*    */         
/* 21 */         for (int k = 0; k < j; k++) {
/*    */           
/* 23 */           if (Blocks.cactus.canBlockStay(worldIn, blockpos))
/*    */           {
/* 25 */             worldIn.setBlockState(blockpos.up(k), Blocks.cactus.getDefaultState(), 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenCactus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */