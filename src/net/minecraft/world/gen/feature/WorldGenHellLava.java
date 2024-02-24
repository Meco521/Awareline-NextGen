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
/*    */ public class WorldGenHellLava
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block field_150553_a;
/*    */   private final boolean field_94524_b;
/*    */   
/*    */   public WorldGenHellLava(Block p_i45453_1_, boolean p_i45453_2_) {
/* 18 */     this.field_150553_a = p_i45453_1_;
/* 19 */     this.field_94524_b = p_i45453_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 24 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.netherrack)
/*    */     {
/* 26 */       return false;
/*    */     }
/* 28 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.air && worldIn.getBlockState(position).getBlock() != Blocks.netherrack)
/*    */     {
/* 30 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 34 */     int i = 0;
/*    */     
/* 36 */     if (worldIn.getBlockState(position.west()).getBlock() == Blocks.netherrack)
/*    */     {
/* 38 */       i++;
/*    */     }
/*    */     
/* 41 */     if (worldIn.getBlockState(position.east()).getBlock() == Blocks.netherrack)
/*    */     {
/* 43 */       i++;
/*    */     }
/*    */     
/* 46 */     if (worldIn.getBlockState(position.north()).getBlock() == Blocks.netherrack)
/*    */     {
/* 48 */       i++;
/*    */     }
/*    */     
/* 51 */     if (worldIn.getBlockState(position.south()).getBlock() == Blocks.netherrack)
/*    */     {
/* 53 */       i++;
/*    */     }
/*    */     
/* 56 */     if (worldIn.getBlockState(position.down()).getBlock() == Blocks.netherrack)
/*    */     {
/* 58 */       i++;
/*    */     }
/*    */     
/* 61 */     int j = 0;
/*    */     
/* 63 */     if (worldIn.isAirBlock(position.west()))
/*    */     {
/* 65 */       j++;
/*    */     }
/*    */     
/* 68 */     if (worldIn.isAirBlock(position.east()))
/*    */     {
/* 70 */       j++;
/*    */     }
/*    */     
/* 73 */     if (worldIn.isAirBlock(position.north()))
/*    */     {
/* 75 */       j++;
/*    */     }
/*    */     
/* 78 */     if (worldIn.isAirBlock(position.south()))
/*    */     {
/* 80 */       j++;
/*    */     }
/*    */     
/* 83 */     if (worldIn.isAirBlock(position.down()))
/*    */     {
/* 85 */       j++;
/*    */     }
/*    */     
/* 88 */     if ((!this.field_94524_b && i == 4 && j == 1) || i == 5) {
/*    */       
/* 90 */       worldIn.setBlockState(position, this.field_150553_a.getDefaultState(), 2);
/* 91 */       worldIn.forceBlockUpdateTick(this.field_150553_a, position, rand);
/*    */     } 
/*    */     
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenHellLava.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */