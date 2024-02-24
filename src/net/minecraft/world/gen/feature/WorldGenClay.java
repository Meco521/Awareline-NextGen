/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenClay
/*    */   extends WorldGenerator
/*    */ {
/* 13 */   private final Block field_150546_a = Blocks.clay;
/*    */ 
/*    */   
/*    */   private final int numberOfBlocks;
/*    */ 
/*    */   
/*    */   public WorldGenClay(int p_i2011_1_) {
/* 20 */     this.numberOfBlocks = p_i2011_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 25 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.water)
/*    */     {
/* 27 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 31 */     int i = rand.nextInt(this.numberOfBlocks - 2) + 2;
/* 32 */     int j = 1;
/*    */     
/* 34 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/*    */       
/* 36 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/*    */         
/* 38 */         int i1 = k - position.getX();
/* 39 */         int j1 = l - position.getZ();
/*    */         
/* 41 */         if (i1 * i1 + j1 * j1 <= i * i)
/*    */         {
/* 43 */           for (int k1 = position.getY() - j; k1 <= position.getY() + j; k1++) {
/*    */             
/* 45 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 46 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 48 */             if (block == Blocks.dirt || block == Blocks.clay)
/*    */             {
/* 50 */               worldIn.setBlockState(blockpos, this.field_150546_a.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenClay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */