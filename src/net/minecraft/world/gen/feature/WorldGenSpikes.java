/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WorldGenSpikes
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block baseBlockRequired;
/*    */   
/*    */   public WorldGenSpikes(Block p_i45464_1_) {
/* 18 */     this.baseBlockRequired = p_i45464_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 23 */     if (worldIn.isAirBlock(position) && worldIn.getBlockState(position.down()).getBlock() == this.baseBlockRequired) {
/*    */       
/* 25 */       int i = rand.nextInt(32) + 6;
/* 26 */       int j = rand.nextInt(4) + 1;
/* 27 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*    */       
/* 29 */       for (int k = position.getX() - j; k <= position.getX() + j; k++) {
/*    */         
/* 31 */         for (int l = position.getZ() - j; l <= position.getZ() + j; l++) {
/*    */           
/* 33 */           int i1 = k - position.getX();
/* 34 */           int j1 = l - position.getZ();
/*    */           
/* 36 */           if (i1 * i1 + j1 * j1 <= j * j + 1 && worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k, position.getY() - 1, l)).getBlock() != this.baseBlockRequired)
/*    */           {
/* 38 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 43 */       for (int l1 = position.getY(); l1 < position.getY() + i && l1 < 256; l1++) {
/*    */         
/* 45 */         for (int i2 = position.getX() - j; i2 <= position.getX() + j; i2++) {
/*    */           
/* 47 */           for (int j2 = position.getZ() - j; j2 <= position.getZ() + j; j2++) {
/*    */             
/* 49 */             int k2 = i2 - position.getX();
/* 50 */             int k1 = j2 - position.getZ();
/*    */             
/* 52 */             if (k2 * k2 + k1 * k1 <= j * j + 1)
/*    */             {
/* 54 */               worldIn.setBlockState(new BlockPos(i2, l1, j2), Blocks.obsidian.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 60 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal(worldIn);
/* 61 */       entityEnderCrystal.setLocationAndAngles((position.getX() + 0.5F), (position.getY() + i), (position.getZ() + 0.5F), rand.nextFloat() * 360.0F, 0.0F);
/* 62 */       worldIn.spawnEntityInWorld((Entity)entityEnderCrystal);
/* 63 */       worldIn.setBlockState(position.up(i), Blocks.bedrock.getDefaultState(), 2);
/* 64 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 68 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenSpikes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */