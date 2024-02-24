/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WorldGenHugeTrees
/*     */   extends WorldGenAbstractTree
/*     */ {
/*     */   protected final int baseHeight;
/*     */   protected final IBlockState woodMetadata;
/*     */   protected final IBlockState leavesMetadata;
/*     */   protected int extraRandomHeight;
/*     */   
/*     */   public WorldGenHugeTrees(boolean p_i46447_1_, int p_i46447_2_, int p_i46447_3_, IBlockState p_i46447_4_, IBlockState p_i46447_5_) {
/*  26 */     super(p_i46447_1_);
/*  27 */     this.baseHeight = p_i46447_2_;
/*  28 */     this.extraRandomHeight = p_i46447_3_;
/*  29 */     this.woodMetadata = p_i46447_4_;
/*  30 */     this.leavesMetadata = p_i46447_5_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_150533_a(Random p_150533_1_) {
/*  35 */     int i = p_150533_1_.nextInt(3) + this.baseHeight;
/*     */     
/*  37 */     if (this.extraRandomHeight > 1)
/*     */     {
/*  39 */       i += p_150533_1_.nextInt(this.extraRandomHeight);
/*     */     }
/*     */     
/*  42 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_175926_c(World worldIn, BlockPos p_175926_2_, int p_175926_3_) {
/*  47 */     boolean flag = true;
/*     */     
/*  49 */     if (p_175926_2_.getY() >= 1 && p_175926_2_.getY() + p_175926_3_ + 1 <= 256) {
/*     */       
/*  51 */       for (int i = 0; i <= 1 + p_175926_3_; i++) {
/*     */         
/*  53 */         int j = 2;
/*     */         
/*  55 */         if (i == 0) {
/*     */           
/*  57 */           j = 1;
/*     */         }
/*  59 */         else if (i >= 1 + p_175926_3_ - 2) {
/*     */           
/*  61 */           j = 2;
/*     */         } 
/*     */         
/*  64 */         for (int k = -j; k <= j && flag; k++) {
/*     */           
/*  66 */           for (int l = -j; l <= j && flag; l++) {
/*     */             
/*  68 */             if (p_175926_2_.getY() + i < 0 || p_175926_2_.getY() + i >= 256 || !func_150523_a(worldIn.getBlockState(p_175926_2_.add(k, i, l)).getBlock()))
/*     */             {
/*  70 */               flag = false;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  76 */       return flag;
/*     */     } 
/*     */ 
/*     */     
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_175927_a(BlockPos p_175927_1_, World worldIn) {
/*  86 */     BlockPos blockpos = p_175927_1_.down();
/*  87 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/*  89 */     if ((block == Blocks.grass || block == Blocks.dirt) && p_175927_1_.getY() >= 2) {
/*     */       
/*  91 */       func_175921_a(worldIn, blockpos);
/*  92 */       func_175921_a(worldIn, blockpos.east());
/*  93 */       func_175921_a(worldIn, blockpos.south());
/*  94 */       func_175921_a(worldIn, blockpos.south().east());
/*  95 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_175929_a(World worldIn, Random p_175929_2_, BlockPos p_175929_3_, int p_175929_4_) {
/* 105 */     return (func_175926_c(worldIn, p_175929_3_, p_175929_4_) && func_175927_a(p_175929_3_, worldIn));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175925_a(World worldIn, BlockPos p_175925_2_, int p_175925_3_) {
/* 110 */     int i = p_175925_3_ * p_175925_3_;
/*     */     
/* 112 */     for (int j = -p_175925_3_; j <= p_175925_3_ + 1; j++) {
/*     */       
/* 114 */       for (int k = -p_175925_3_; k <= p_175925_3_ + 1; k++) {
/*     */         
/* 116 */         int l = j - 1;
/* 117 */         int i1 = k - 1;
/*     */         
/* 119 */         if (j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i) {
/*     */           
/* 121 */           BlockPos blockpos = p_175925_2_.add(j, 0, k);
/* 122 */           Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*     */           
/* 124 */           if (material == Material.air || material == Material.leaves)
/*     */           {
/* 126 */             setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175928_b(World worldIn, BlockPos p_175928_2_, int p_175928_3_) {
/* 135 */     int i = p_175928_3_ * p_175928_3_;
/*     */     
/* 137 */     for (int j = -p_175928_3_; j <= p_175928_3_; j++) {
/*     */       
/* 139 */       for (int k = -p_175928_3_; k <= p_175928_3_; k++) {
/*     */         
/* 141 */         if (j * j + k * k <= i) {
/*     */           
/* 143 */           BlockPos blockpos = p_175928_2_.add(j, 0, k);
/* 144 */           Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*     */           
/* 146 */           if (material == Material.air || material == Material.leaves)
/*     */           {
/* 148 */             setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenHugeTrees.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */