/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class WorldGenIceSpike
/*     */   extends WorldGenerator
/*     */ {
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  16 */     while (worldIn.isAirBlock(position) && position.getY() > 2)
/*     */     {
/*  18 */       position = position.down();
/*     */     }
/*     */     
/*  21 */     if (worldIn.getBlockState(position).getBlock() != Blocks.snow)
/*     */     {
/*  23 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  27 */     position = position.up(rand.nextInt(4));
/*  28 */     int i = rand.nextInt(4) + 7;
/*  29 */     int j = i / 4 + rand.nextInt(2);
/*     */     
/*  31 */     if (j > 1 && rand.nextInt(60) == 0)
/*     */     {
/*  33 */       position = position.up(10 + rand.nextInt(30));
/*     */     }
/*     */     
/*  36 */     for (int k = 0; k < i; k++) {
/*     */       
/*  38 */       float f = (1.0F - k / i) * j;
/*  39 */       int l = MathHelper.ceiling_float_int(f);
/*     */       
/*  41 */       for (int i1 = -l; i1 <= l; i1++) {
/*     */         
/*  43 */         float f1 = MathHelper.abs_int(i1) - 0.25F;
/*     */         
/*  45 */         for (int j1 = -l; j1 <= l; j1++) {
/*     */           
/*  47 */           float f2 = MathHelper.abs_int(j1) - 0.25F;
/*     */           
/*  49 */           if (((i1 == 0 && j1 == 0) || f1 * f1 + f2 * f2 <= f * f) && ((i1 != -l && i1 != l && j1 != -l && j1 != l) || rand.nextFloat() <= 0.75F)) {
/*     */             
/*  51 */             Block block = worldIn.getBlockState(position.add(i1, k, j1)).getBlock();
/*     */             
/*  53 */             if (block.getMaterial() == Material.air || block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice)
/*     */             {
/*  55 */               setBlockAndNotifyAdequately(worldIn, position.add(i1, k, j1), Blocks.packed_ice.getDefaultState());
/*     */             }
/*     */             
/*  58 */             if (k != 0 && l > 1) {
/*     */               
/*  60 */               block = worldIn.getBlockState(position.add(i1, -k, j1)).getBlock();
/*     */               
/*  62 */               if (block.getMaterial() == Material.air || block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice)
/*     */               {
/*  64 */                 setBlockAndNotifyAdequately(worldIn, position.add(i1, -k, j1), Blocks.packed_ice.getDefaultState());
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     int k1 = j - 1;
/*     */     
/*  74 */     if (k1 < 0) {
/*     */       
/*  76 */       k1 = 0;
/*     */     }
/*  78 */     else if (k1 > 1) {
/*     */       
/*  80 */       k1 = 1;
/*     */     } 
/*     */     
/*  83 */     for (int l1 = -k1; l1 <= k1; l1++) {
/*     */       
/*  85 */       for (int i2 = -k1; i2 <= k1; i2++) {
/*     */         
/*  87 */         BlockPos blockpos = position.add(l1, -1, i2);
/*  88 */         int j2 = 50;
/*     */         
/*  90 */         if (Math.abs(l1) == 1 && Math.abs(i2) == 1)
/*     */         {
/*  92 */           j2 = rand.nextInt(5);
/*     */         }
/*     */         
/*  95 */         while (blockpos.getY() > 50) {
/*     */           
/*  97 */           Block block1 = worldIn.getBlockState(blockpos).getBlock();
/*     */           
/*  99 */           if (block1.getMaterial() != Material.air && block1 != Blocks.dirt && block1 != Blocks.snow && block1 != Blocks.ice && block1 != Blocks.packed_ice) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 104 */           setBlockAndNotifyAdequately(worldIn, blockpos, Blocks.packed_ice.getDefaultState());
/* 105 */           blockpos = blockpos.down();
/* 106 */           j2--;
/*     */           
/* 108 */           if (j2 <= 0) {
/*     */             
/* 110 */             blockpos = blockpos.down(rand.nextInt(5) + 1);
/* 111 */             j2 = rand.nextInt(5);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenIceSpike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */