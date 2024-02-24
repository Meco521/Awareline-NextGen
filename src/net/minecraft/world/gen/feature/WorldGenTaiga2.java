/*     */ package net.minecraft.world.gen.feature;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenTaiga2 extends WorldGenAbstractTree {
/*  14 */   private static final IBlockState field_181645_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  15 */   private static final IBlockState field_181646_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenTaiga2(boolean p_i2025_1_) {
/*  19 */     super(p_i2025_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  24 */     int i = rand.nextInt(4) + 6;
/*  25 */     int j = 1 + rand.nextInt(2);
/*  26 */     int k = i - j;
/*  27 */     int l = 2 + rand.nextInt(2);
/*  28 */     boolean flag = true;
/*     */     
/*  30 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  32 */       for (int i1 = position.getY(); i1 <= position.getY() + 1 + i && flag; i1++) {
/*     */         
/*  34 */         int j1 = 1;
/*     */         
/*  36 */         if (i1 - position.getY() < j) {
/*     */           
/*  38 */           j1 = 0;
/*     */         }
/*     */         else {
/*     */           
/*  42 */           j1 = l;
/*     */         } 
/*     */         
/*  45 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  47 */         for (int k1 = position.getX() - j1; k1 <= position.getX() + j1 && flag; k1++) {
/*     */           
/*  49 */           for (int l1 = position.getZ() - j1; l1 <= position.getZ() + j1 && flag; l1++) {
/*     */             
/*  51 */             if (i1 >= 0 && i1 < 256) {
/*     */               
/*  53 */               Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, i1, l1)).getBlock();
/*     */               
/*  55 */               if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves)
/*     */               {
/*  57 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  62 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  68 */       if (!flag)
/*     */       {
/*  70 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  74 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  76 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt || block1 == Blocks.farmland) && position.getY() < 256 - i - 1) {
/*     */         
/*  78 */         func_175921_a(worldIn, position.down());
/*  79 */         int i3 = rand.nextInt(2);
/*  80 */         int j3 = 1;
/*  81 */         int k3 = 0;
/*     */         
/*  83 */         for (int l3 = 0; l3 <= k; l3++) {
/*     */           
/*  85 */           int j4 = position.getY() + i - l3;
/*     */           
/*  87 */           for (int i2 = position.getX() - i3; i2 <= position.getX() + i3; i2++) {
/*     */             
/*  89 */             int j2 = i2 - position.getX();
/*     */             
/*  91 */             for (int k2 = position.getZ() - i3; k2 <= position.getZ() + i3; k2++) {
/*     */               
/*  93 */               int l2 = k2 - position.getZ();
/*     */               
/*  95 */               if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
/*     */                 
/*  97 */                 BlockPos blockpos = new BlockPos(i2, j4, k2);
/*     */                 
/*  99 */                 if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock())
/*     */                 {
/* 101 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181646_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 107 */           if (i3 >= j3) {
/*     */             
/* 109 */             i3 = k3;
/* 110 */             k3 = 1;
/* 111 */             j3++;
/*     */             
/* 113 */             if (j3 > l)
/*     */             {
/* 115 */               j3 = l;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 120 */             i3++;
/*     */           } 
/*     */         } 
/*     */         
/* 124 */         int i4 = rand.nextInt(3);
/*     */         
/* 126 */         for (int k4 = 0; k4 < i - i4; k4++) {
/*     */           
/* 128 */           Block block2 = worldIn.getBlockState(position.up(k4)).getBlock();
/*     */           
/* 130 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves)
/*     */           {
/* 132 */             setBlockAndNotifyAdequately(worldIn, position.up(k4), field_181645_a);
/*     */           }
/*     */         } 
/*     */         
/* 136 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 140 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenTaiga2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */