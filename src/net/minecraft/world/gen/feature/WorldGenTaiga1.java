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
/*     */ public class WorldGenTaiga1 extends WorldGenAbstractTree {
/*  14 */   private static final IBlockState field_181636_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  15 */   private static final IBlockState field_181637_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenTaiga1() {
/*  19 */     super(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  24 */     int i = rand.nextInt(5) + 7;
/*  25 */     int j = i - rand.nextInt(2) - 3;
/*  26 */     int k = i - j;
/*  27 */     int l = 1 + rand.nextInt(k + 1);
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
/*  53 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, i1, l1)).getBlock()))
/*     */               {
/*  55 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  60 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  66 */       if (!flag)
/*     */       {
/*  68 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  72 */       Block block = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  74 */       if ((block == Blocks.grass || block == Blocks.dirt) && position.getY() < 256 - i - 1) {
/*     */         
/*  76 */         func_175921_a(worldIn, position.down());
/*  77 */         int k2 = 0;
/*     */         
/*  79 */         for (int l2 = position.getY() + i; l2 >= position.getY() + j; l2--) {
/*     */           
/*  81 */           for (int j3 = position.getX() - k2; j3 <= position.getX() + k2; j3++) {
/*     */             
/*  83 */             int k3 = j3 - position.getX();
/*     */             
/*  85 */             for (int i2 = position.getZ() - k2; i2 <= position.getZ() + k2; i2++) {
/*     */               
/*  87 */               int j2 = i2 - position.getZ();
/*     */               
/*  89 */               if (Math.abs(k3) != k2 || Math.abs(j2) != k2 || k2 <= 0) {
/*     */                 
/*  91 */                 BlockPos blockpos = new BlockPos(j3, l2, i2);
/*     */                 
/*  93 */                 if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock())
/*     */                 {
/*  95 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181637_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 101 */           if (k2 >= 1 && l2 == position.getY() + j + 1) {
/*     */             
/* 103 */             k2--;
/*     */           }
/* 105 */           else if (k2 < l) {
/*     */             
/* 107 */             k2++;
/*     */           } 
/*     */         } 
/*     */         
/* 111 */         for (int i3 = 0; i3 < i - 1; i3++) {
/*     */           
/* 113 */           Block block1 = worldIn.getBlockState(position.up(i3)).getBlock();
/*     */           
/* 115 */           if (block1.getMaterial() == Material.air || block1.getMaterial() == Material.leaves)
/*     */           {
/* 117 */             setBlockAndNotifyAdequately(worldIn, position.up(i3), field_181636_a);
/*     */           }
/*     */         } 
/*     */         
/* 121 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 125 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenTaiga1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */