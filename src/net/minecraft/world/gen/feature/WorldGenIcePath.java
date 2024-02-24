/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenIcePath
/*    */   extends WorldGenerator
/*    */ {
/* 12 */   private final Block block = Blocks.packed_ice;
/*    */   
/*    */   private final int basePathWidth;
/*    */   
/*    */   public WorldGenIcePath(int p_i45454_1_) {
/* 17 */     this.basePathWidth = p_i45454_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 22 */     while (worldIn.isAirBlock(position) && position.getY() > 2)
/*    */     {
/* 24 */       position = position.down();
/*    */     }
/*    */     
/* 27 */     if (worldIn.getBlockState(position).getBlock() != Blocks.snow)
/*    */     {
/* 29 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 33 */     int i = rand.nextInt(this.basePathWidth - 2) + 2;
/* 34 */     int j = 1;
/*    */     
/* 36 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/*    */       
/* 38 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/*    */         
/* 40 */         int i1 = k - position.getX();
/* 41 */         int j1 = l - position.getZ();
/*    */         
/* 43 */         if (i1 * i1 + j1 * j1 <= i * i)
/*    */         {
/* 45 */           for (int k1 = position.getY() - j; k1 <= position.getY() + j; k1++) {
/*    */             
/* 47 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 48 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 50 */             if (block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice)
/*    */             {
/* 52 */               worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 59 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenIcePath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */