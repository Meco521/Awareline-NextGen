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
/*     */ public class WorldGenCanopyTree extends WorldGenAbstractTree {
/*  15 */   private static final IBlockState field_181640_a = Blocks.log2.getDefaultState().withProperty((IProperty)BlockNewLog.VARIANT, (Comparable)BlockPlanks.EnumType.DARK_OAK);
/*  16 */   private static final IBlockState field_181641_b = Blocks.leaves2.getDefaultState().withProperty((IProperty)BlockNewLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.DARK_OAK).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenCanopyTree(boolean p_i45461_1_) {
/*  20 */     super(p_i45461_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  25 */     int i = rand.nextInt(3) + rand.nextInt(2) + 6;
/*  26 */     int j = position.getX();
/*  27 */     int k = position.getY();
/*  28 */     int l = position.getZ();
/*     */     
/*  30 */     if (k >= 1 && k + i + 1 < 256) {
/*     */       
/*  32 */       BlockPos blockpos = position.down();
/*  33 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/*  35 */       if (block != Blocks.grass && block != Blocks.dirt)
/*     */       {
/*  37 */         return false;
/*     */       }
/*  39 */       if (!func_181638_a(worldIn, position, i))
/*     */       {
/*  41 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  45 */       func_175921_a(worldIn, blockpos);
/*  46 */       func_175921_a(worldIn, blockpos.east());
/*  47 */       func_175921_a(worldIn, blockpos.south());
/*  48 */       func_175921_a(worldIn, blockpos.south().east());
/*  49 */       EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
/*  50 */       int i1 = i - rand.nextInt(4);
/*  51 */       int j1 = 2 - rand.nextInt(3);
/*  52 */       int k1 = j;
/*  53 */       int l1 = l;
/*  54 */       int i2 = k + i - 1;
/*     */       
/*  56 */       for (int j2 = 0; j2 < i; j2++) {
/*     */         
/*  58 */         if (j2 >= i1 && j1 > 0) {
/*     */           
/*  60 */           k1 += enumfacing.getFrontOffsetX();
/*  61 */           l1 += enumfacing.getFrontOffsetZ();
/*  62 */           j1--;
/*     */         } 
/*     */         
/*  65 */         int k2 = k + j2;
/*  66 */         BlockPos blockpos1 = new BlockPos(k1, k2, l1);
/*  67 */         Material material = worldIn.getBlockState(blockpos1).getBlock().getMaterial();
/*     */         
/*  69 */         if (material == Material.air || material == Material.leaves) {
/*     */           
/*  71 */           func_181639_b(worldIn, blockpos1);
/*  72 */           func_181639_b(worldIn, blockpos1.east());
/*  73 */           func_181639_b(worldIn, blockpos1.south());
/*  74 */           func_181639_b(worldIn, blockpos1.east().south());
/*     */         } 
/*     */       } 
/*     */       
/*  78 */       for (int i3 = -2; i3 <= 0; i3++) {
/*     */         
/*  80 */         for (int l3 = -2; l3 <= 0; l3++) {
/*     */           
/*  82 */           int k4 = -1;
/*  83 */           func_150526_a(worldIn, k1 + i3, i2 + k4, l1 + l3);
/*  84 */           func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
/*  85 */           func_150526_a(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
/*  86 */           func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
/*     */           
/*  88 */           if ((i3 > -2 || l3 > -1) && (i3 != -1 || l3 != -2)) {
/*     */             
/*  90 */             k4 = 1;
/*  91 */             func_150526_a(worldIn, k1 + i3, i2 + k4, l1 + l3);
/*  92 */             func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
/*  93 */             func_150526_a(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
/*  94 */             func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       if (rand.nextBoolean()) {
/*     */         
/* 101 */         func_150526_a(worldIn, k1, i2 + 2, l1);
/* 102 */         func_150526_a(worldIn, k1 + 1, i2 + 2, l1);
/* 103 */         func_150526_a(worldIn, k1 + 1, i2 + 2, l1 + 1);
/* 104 */         func_150526_a(worldIn, k1, i2 + 2, l1 + 1);
/*     */       } 
/*     */       
/* 107 */       for (int j3 = -3; j3 <= 4; j3++) {
/*     */         
/* 109 */         for (int i4 = -3; i4 <= 4; i4++) {
/*     */           
/* 111 */           if ((j3 != -3 || i4 != -3) && (j3 != -3 || i4 != 4) && (j3 != 4 || i4 != -3) && (j3 != 4 || i4 != 4) && (Math.abs(j3) < 3 || Math.abs(i4) < 3))
/*     */           {
/* 113 */             func_150526_a(worldIn, k1 + j3, i2, l1 + i4);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 118 */       for (int k3 = -1; k3 <= 2; k3++) {
/*     */         
/* 120 */         for (int j4 = -1; j4 <= 2; j4++) {
/*     */           
/* 122 */           if ((k3 < 0 || k3 > 1 || j4 < 0 || j4 > 1) && rand.nextInt(3) <= 0) {
/*     */             
/* 124 */             int l4 = rand.nextInt(3) + 2;
/*     */             
/* 126 */             for (int i5 = 0; i5 < l4; i5++)
/*     */             {
/* 128 */               func_181639_b(worldIn, new BlockPos(j + k3, i2 - i5 - 1, l + j4));
/*     */             }
/*     */             
/* 131 */             for (int j5 = -1; j5 <= 1; j5++) {
/*     */               
/* 133 */               for (int l2 = -1; l2 <= 1; l2++)
/*     */               {
/* 135 */                 func_150526_a(worldIn, k1 + k3 + j5, i2, l1 + j4 + l2);
/*     */               }
/*     */             } 
/*     */             
/* 139 */             for (int k5 = -2; k5 <= 2; k5++) {
/*     */               
/* 141 */               for (int l5 = -2; l5 <= 2; l5++) {
/*     */                 
/* 143 */                 if (Math.abs(k5) != 2 || Math.abs(l5) != 2)
/*     */                 {
/* 145 */                   func_150526_a(worldIn, k1 + k3 + k5, i2 - 1, l1 + j4 + l5);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 153 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_181638_a(World p_181638_1_, BlockPos p_181638_2_, int p_181638_3_) {
/* 164 */     int i = p_181638_2_.getX();
/* 165 */     int j = p_181638_2_.getY();
/* 166 */     int k = p_181638_2_.getZ();
/* 167 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 169 */     for (int l = 0; l <= p_181638_3_ + 1; l++) {
/*     */       
/* 171 */       int i1 = 1;
/*     */       
/* 173 */       if (l == 0)
/*     */       {
/* 175 */         i1 = 0;
/*     */       }
/*     */       
/* 178 */       if (l >= p_181638_3_ - 1)
/*     */       {
/* 180 */         i1 = 2;
/*     */       }
/*     */       
/* 183 */       for (int j1 = -i1; j1 <= i1; j1++) {
/*     */         
/* 185 */         for (int k1 = -i1; k1 <= i1; k1++) {
/*     */           
/* 187 */           if (!func_150523_a(p_181638_1_.getBlockState((BlockPos)blockpos$mutableblockpos.set(i + j1, j + l, k + k1)).getBlock()))
/*     */           {
/* 189 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181639_b(World p_181639_1_, BlockPos p_181639_2_) {
/* 200 */     if (func_150523_a(p_181639_1_.getBlockState(p_181639_2_).getBlock()))
/*     */     {
/* 202 */       setBlockAndNotifyAdequately(p_181639_1_, p_181639_2_, field_181640_a);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_150526_a(World worldIn, int p_150526_2_, int p_150526_3_, int p_150526_4_) {
/* 208 */     BlockPos blockpos = new BlockPos(p_150526_2_, p_150526_3_, p_150526_4_);
/* 209 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 211 */     if (block.getMaterial() == Material.air)
/*     */     {
/* 213 */       setBlockAndNotifyAdequately(worldIn, blockpos, field_181641_b);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenCanopyTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */