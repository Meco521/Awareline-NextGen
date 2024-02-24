/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenForest
/*     */   extends WorldGenAbstractTree {
/*  17 */   private static final IBlockState field_181629_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.BIRCH);
/*  18 */   private static final IBlockState field_181630_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.BIRCH).withProperty((IProperty)BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   private final boolean useExtraRandomHeight;
/*     */   
/*     */   public WorldGenForest(boolean p_i45449_1_, boolean p_i45449_2_) {
/*  23 */     super(p_i45449_1_);
/*  24 */     this.useExtraRandomHeight = p_i45449_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  29 */     int i = rand.nextInt(3) + 5;
/*     */     
/*  31 */     if (this.useExtraRandomHeight)
/*     */     {
/*  33 */       i += rand.nextInt(7);
/*     */     }
/*     */     
/*  36 */     boolean flag = true;
/*     */     
/*  38 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  40 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  42 */         int k = 1;
/*     */         
/*  44 */         if (j == position.getY())
/*     */         {
/*  46 */           k = 0;
/*     */         }
/*     */         
/*  49 */         if (j >= position.getY() + 1 + i - 2)
/*     */         {
/*  51 */           k = 2;
/*     */         }
/*     */         
/*  54 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  56 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  58 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  60 */             if (j >= 0 && j < 256) {
/*     */               
/*  62 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock()))
/*     */               {
/*  64 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  69 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  75 */       if (!flag)
/*     */       {
/*  77 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  81 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  83 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt || block1 == Blocks.farmland) && position.getY() < 256 - i - 1) {
/*     */         
/*  85 */         func_175921_a(worldIn, position.down());
/*     */         
/*  87 */         for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; i2++) {
/*     */           
/*  89 */           int k2 = i2 - position.getY() + i;
/*  90 */           int l2 = 1 - k2 / 2;
/*     */           
/*  92 */           for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; i3++) {
/*     */             
/*  94 */             int j1 = i3 - position.getX();
/*     */             
/*  96 */             for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; k1++) {
/*     */               
/*  98 */               int l1 = k1 - position.getZ();
/*     */               
/* 100 */               if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || (rand.nextInt(2) != 0 && k2 != 0)) {
/*     */                 
/* 102 */                 BlockPos blockpos = new BlockPos(i3, i2, k1);
/* 103 */                 Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */                 
/* 105 */                 if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves)
/*     */                 {
/* 107 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181630_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 114 */         for (int j2 = 0; j2 < i; j2++) {
/*     */           
/* 116 */           Block block2 = worldIn.getBlockState(position.up(j2)).getBlock();
/*     */           
/* 118 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves)
/*     */           {
/* 120 */             setBlockAndNotifyAdequately(worldIn, position.up(j2), field_181629_a);
/*     */           }
/*     */         } 
/*     */         
/* 124 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 128 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenForest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */