/*     */ package net.minecraft.world.gen.feature;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenMegaPineTree extends WorldGenHugeTrees {
/*  15 */   private static final IBlockState field_181633_e = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  16 */   private static final IBlockState field_181634_f = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*  17 */   private static final IBlockState field_181635_g = Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.PODZOL);
/*     */   
/*     */   private final boolean useBaseHeight;
/*     */   
/*     */   public WorldGenMegaPineTree(boolean p_i45457_1_, boolean p_i45457_2_) {
/*  22 */     super(p_i45457_1_, 13, 15, field_181633_e, field_181634_f);
/*  23 */     this.useBaseHeight = p_i45457_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  28 */     int i = func_150533_a(rand);
/*     */     
/*  30 */     if (!func_175929_a(worldIn, rand, position, i))
/*     */     {
/*  32 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  36 */     func_150541_c(worldIn, position.getX(), position.getZ(), position.getY() + i, 0, rand);
/*     */     
/*  38 */     for (int j = 0; j < i; j++) {
/*     */       
/*  40 */       Block block = worldIn.getBlockState(position.up(j)).getBlock();
/*     */       
/*  42 */       if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves)
/*     */       {
/*  44 */         setBlockAndNotifyAdequately(worldIn, position.up(j), this.woodMetadata);
/*     */       }
/*     */       
/*  47 */       if (j < i - 1) {
/*     */         
/*  49 */         block = worldIn.getBlockState(position.add(1, j, 0)).getBlock();
/*     */         
/*  51 */         if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves)
/*     */         {
/*  53 */           setBlockAndNotifyAdequately(worldIn, position.add(1, j, 0), this.woodMetadata);
/*     */         }
/*     */         
/*  56 */         block = worldIn.getBlockState(position.add(1, j, 1)).getBlock();
/*     */         
/*  58 */         if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves)
/*     */         {
/*  60 */           setBlockAndNotifyAdequately(worldIn, position.add(1, j, 1), this.woodMetadata);
/*     */         }
/*     */         
/*  63 */         block = worldIn.getBlockState(position.add(0, j, 1)).getBlock();
/*     */         
/*  65 */         if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves)
/*     */         {
/*  67 */           setBlockAndNotifyAdequately(worldIn, position.add(0, j, 1), this.woodMetadata);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_150541_c(World worldIn, int p_150541_2_, int p_150541_3_, int p_150541_4_, int p_150541_5_, Random p_150541_6_) {
/*  78 */     int i = p_150541_6_.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
/*  79 */     int j = 0;
/*     */     
/*  81 */     for (int k = p_150541_4_ - i; k <= p_150541_4_; k++) {
/*     */       
/*  83 */       int l = p_150541_4_ - k;
/*  84 */       int i1 = p_150541_5_ + MathHelper.floor_float(l / i * 3.5F);
/*  85 */       func_175925_a(worldIn, new BlockPos(p_150541_2_, k, p_150541_3_), i1 + ((l > 0 && i1 == j && (k & 0x1) == 0) ? 1 : 0));
/*  86 */       j = i1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180711_a(World worldIn, Random p_180711_2_, BlockPos p_180711_3_) {
/*  92 */     func_175933_b(worldIn, p_180711_3_.west().north());
/*  93 */     func_175933_b(worldIn, p_180711_3_.east(2).north());
/*  94 */     func_175933_b(worldIn, p_180711_3_.west().south(2));
/*  95 */     func_175933_b(worldIn, p_180711_3_.east(2).south(2));
/*     */     
/*  97 */     for (int i = 0; i < 5; i++) {
/*     */       
/*  99 */       int j = p_180711_2_.nextInt(64);
/* 100 */       int k = j % 8;
/* 101 */       int l = j / 8;
/*     */       
/* 103 */       if (k == 0 || k == 7 || l == 0 || l == 7)
/*     */       {
/* 105 */         func_175933_b(worldIn, p_180711_3_.add(-3 + k, 0, -3 + l));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175933_b(World worldIn, BlockPos p_175933_2_) {
/* 112 */     for (int i = -2; i <= 2; i++) {
/*     */       
/* 114 */       for (int j = -2; j <= 2; j++) {
/*     */         
/* 116 */         if (Math.abs(i) != 2 || Math.abs(j) != 2)
/*     */         {
/* 118 */           func_175934_c(worldIn, p_175933_2_.add(i, 0, j));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175934_c(World worldIn, BlockPos p_175934_2_) {
/* 126 */     for (int i = 2; i >= -3; i--) {
/*     */       
/* 128 */       BlockPos blockpos = p_175934_2_.up(i);
/* 129 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/* 131 */       if (block == Blocks.grass || block == Blocks.dirt) {
/*     */         
/* 133 */         setBlockAndNotifyAdequately(worldIn, blockpos, field_181635_g);
/*     */         
/*     */         break;
/*     */       } 
/* 137 */       if (block.getMaterial() != Material.air && i < 0)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenMegaPineTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */