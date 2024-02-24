/*     */ package net.minecraft.world.gen.feature;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenSavannaTree extends WorldGenAbstractTree {
/*  15 */   private static final IBlockState field_181643_a = Blocks.log2.getDefaultState().withProperty((IProperty)BlockNewLog.VARIANT, (Comparable)BlockPlanks.EnumType.ACACIA);
/*  16 */   private static final IBlockState field_181644_b = Blocks.leaves2.getDefaultState().withProperty((IProperty)BlockNewLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.ACACIA).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenSavannaTree(boolean p_i45463_1_) {
/*  20 */     super(p_i45463_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  25 */     int i = rand.nextInt(3) + rand.nextInt(3) + 5;
/*  26 */     boolean flag = true;
/*     */     
/*  28 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  30 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  32 */         int k = 1;
/*     */         
/*  34 */         if (j == position.getY())
/*     */         {
/*  36 */           k = 0;
/*     */         }
/*     */         
/*  39 */         if (j >= position.getY() + 1 + i - 2)
/*     */         {
/*  41 */           k = 2;
/*     */         }
/*     */         
/*  44 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  46 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  48 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  50 */             if (j >= 0 && j < 256) {
/*     */               
/*  52 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock()))
/*     */               {
/*  54 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  59 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  65 */       if (!flag)
/*     */       {
/*  67 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  71 */       Block block = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  73 */       if ((block == Blocks.grass || block == Blocks.dirt) && position.getY() < 256 - i - 1) {
/*     */         
/*  75 */         func_175921_a(worldIn, position.down());
/*  76 */         EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
/*  77 */         int k2 = i - rand.nextInt(4) - 1;
/*  78 */         int l2 = 3 - rand.nextInt(3);
/*  79 */         int i3 = position.getX();
/*  80 */         int j1 = position.getZ();
/*  81 */         int k1 = 0;
/*     */         
/*  83 */         for (int l1 = 0; l1 < i; l1++) {
/*     */           
/*  85 */           int i2 = position.getY() + l1;
/*     */           
/*  87 */           if (l1 >= k2 && l2 > 0) {
/*     */             
/*  89 */             i3 += enumfacing.getFrontOffsetX();
/*  90 */             j1 += enumfacing.getFrontOffsetZ();
/*  91 */             l2--;
/*     */           } 
/*     */           
/*  94 */           BlockPos blockpos = new BlockPos(i3, i2, j1);
/*  95 */           Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*     */           
/*  97 */           if (material == Material.air || material == Material.leaves) {
/*     */             
/*  99 */             func_181642_b(worldIn, blockpos);
/* 100 */             k1 = i2;
/*     */           } 
/*     */         } 
/*     */         
/* 104 */         BlockPos blockpos2 = new BlockPos(i3, k1, j1);
/*     */         
/* 106 */         for (int j3 = -3; j3 <= 3; j3++) {
/*     */           
/* 108 */           for (int i4 = -3; i4 <= 3; i4++) {
/*     */             
/* 110 */             if (Math.abs(j3) != 3 || Math.abs(i4) != 3)
/*     */             {
/* 112 */               func_175924_b(worldIn, blockpos2.add(j3, 0, i4));
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 117 */         blockpos2 = blockpos2.up();
/*     */         
/* 119 */         for (int k3 = -1; k3 <= 1; k3++) {
/*     */           
/* 121 */           for (int j4 = -1; j4 <= 1; j4++)
/*     */           {
/* 123 */             func_175924_b(worldIn, blockpos2.add(k3, 0, j4));
/*     */           }
/*     */         } 
/*     */         
/* 127 */         func_175924_b(worldIn, blockpos2.east(2));
/* 128 */         func_175924_b(worldIn, blockpos2.west(2));
/* 129 */         func_175924_b(worldIn, blockpos2.south(2));
/* 130 */         func_175924_b(worldIn, blockpos2.north(2));
/* 131 */         i3 = position.getX();
/* 132 */         j1 = position.getZ();
/* 133 */         EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.random(rand);
/*     */         
/* 135 */         if (enumfacing1 != enumfacing) {
/*     */           
/* 137 */           int l3 = k2 - rand.nextInt(2) - 1;
/* 138 */           int k4 = 1 + rand.nextInt(3);
/* 139 */           k1 = 0;
/*     */           
/* 141 */           for (int l4 = l3; l4 < i && k4 > 0; k4--) {
/*     */             
/* 143 */             if (l4 >= 1) {
/*     */               
/* 145 */               int j2 = position.getY() + l4;
/* 146 */               i3 += enumfacing1.getFrontOffsetX();
/* 147 */               j1 += enumfacing1.getFrontOffsetZ();
/* 148 */               BlockPos blockpos1 = new BlockPos(i3, j2, j1);
/* 149 */               Material material1 = worldIn.getBlockState(blockpos1).getBlock().getMaterial();
/*     */               
/* 151 */               if (material1 == Material.air || material1 == Material.leaves) {
/*     */                 
/* 153 */                 func_181642_b(worldIn, blockpos1);
/* 154 */                 k1 = j2;
/*     */               } 
/*     */             } 
/*     */             
/* 158 */             l4++;
/*     */           } 
/*     */           
/* 161 */           if (k1 > 0) {
/*     */             
/* 163 */             BlockPos blockpos3 = new BlockPos(i3, k1, j1);
/*     */             
/* 165 */             for (int i5 = -2; i5 <= 2; i5++) {
/*     */               
/* 167 */               for (int k5 = -2; k5 <= 2; k5++) {
/*     */                 
/* 169 */                 if (Math.abs(i5) != 2 || Math.abs(k5) != 2)
/*     */                 {
/* 171 */                   func_175924_b(worldIn, blockpos3.add(i5, 0, k5));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 176 */             blockpos3 = blockpos3.up();
/*     */             
/* 178 */             for (int j5 = -1; j5 <= 1; j5++) {
/*     */               
/* 180 */               for (int l5 = -1; l5 <= 1; l5++)
/*     */               {
/* 182 */                 func_175924_b(worldIn, blockpos3.add(j5, 0, l5));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 188 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 192 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_181642_b(World p_181642_1_, BlockPos p_181642_2_) {
/* 204 */     setBlockAndNotifyAdequately(p_181642_1_, p_181642_2_, field_181643_a);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175924_b(World worldIn, BlockPos p_175924_2_) {
/* 209 */     Material material = worldIn.getBlockState(p_175924_2_).getBlock().getMaterial();
/*     */     
/* 211 */     if (material == Material.air || material == Material.leaves)
/*     */     {
/* 213 */       setBlockAndNotifyAdequately(worldIn, p_175924_2_, field_181644_b);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenSavannaTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */