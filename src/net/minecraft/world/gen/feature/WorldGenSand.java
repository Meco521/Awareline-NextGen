/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenSand
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block block;
/*    */   private final int radius;
/*    */   
/*    */   public WorldGenSand(Block p_i45462_1_, int p_i45462_2_) {
/* 20 */     this.block = p_i45462_1_;
/* 21 */     this.radius = p_i45462_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 26 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.water)
/*    */     {
/* 28 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 32 */     int i = rand.nextInt(this.radius - 2) + 2;
/* 33 */     int j = 2;
/*    */     
/* 35 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/*    */       
/* 37 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/*    */         
/* 39 */         int i1 = k - position.getX();
/* 40 */         int j1 = l - position.getZ();
/*    */         
/* 42 */         if (i1 * i1 + j1 * j1 <= i * i)
/*    */         {
/* 44 */           for (int k1 = position.getY() - j; k1 <= position.getY() + j; k1++) {
/*    */             
/* 46 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 47 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 49 */             if (block == Blocks.dirt || block == Blocks.grass)
/*    */             {
/* 51 */               worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenSand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */