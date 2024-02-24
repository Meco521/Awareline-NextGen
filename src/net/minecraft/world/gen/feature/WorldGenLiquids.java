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
/*    */ public class WorldGenLiquids
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block block;
/*    */   
/*    */   public WorldGenLiquids(Block p_i45465_1_) {
/* 17 */     this.block = p_i45465_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 22 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.stone)
/*    */     {
/* 24 */       return false;
/*    */     }
/* 26 */     if (worldIn.getBlockState(position.down()).getBlock() != Blocks.stone)
/*    */     {
/* 28 */       return false;
/*    */     }
/* 30 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.air && worldIn.getBlockState(position).getBlock() != Blocks.stone)
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 36 */     int i = 0;
/*    */     
/* 38 */     if (worldIn.getBlockState(position.west()).getBlock() == Blocks.stone)
/*    */     {
/* 40 */       i++;
/*    */     }
/*    */     
/* 43 */     if (worldIn.getBlockState(position.east()).getBlock() == Blocks.stone)
/*    */     {
/* 45 */       i++;
/*    */     }
/*    */     
/* 48 */     if (worldIn.getBlockState(position.north()).getBlock() == Blocks.stone)
/*    */     {
/* 50 */       i++;
/*    */     }
/*    */     
/* 53 */     if (worldIn.getBlockState(position.south()).getBlock() == Blocks.stone)
/*    */     {
/* 55 */       i++;
/*    */     }
/*    */     
/* 58 */     int j = 0;
/*    */     
/* 60 */     if (worldIn.isAirBlock(position.west()))
/*    */     {
/* 62 */       j++;
/*    */     }
/*    */     
/* 65 */     if (worldIn.isAirBlock(position.east()))
/*    */     {
/* 67 */       j++;
/*    */     }
/*    */     
/* 70 */     if (worldIn.isAirBlock(position.north()))
/*    */     {
/* 72 */       j++;
/*    */     }
/*    */     
/* 75 */     if (worldIn.isAirBlock(position.south()))
/*    */     {
/* 77 */       j++;
/*    */     }
/*    */     
/* 80 */     if (i == 3 && j == 1) {
/*    */       
/* 82 */       worldIn.setBlockState(position, this.block.getDefaultState(), 2);
/* 83 */       worldIn.forceBlockUpdateTick(this.block, position, rand);
/*    */     } 
/*    */     
/* 86 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenLiquids.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */