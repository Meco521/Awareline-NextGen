/*     */ package net.minecraft.world.gen.feature;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockVine;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenSwamp extends WorldGenAbstractTree {
/*  15 */   private static final IBlockState field_181648_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.OAK);
/*  16 */   private static final IBlockState field_181649_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.OAK).withProperty((IProperty)BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenSwamp() {
/*  20 */     super(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*     */     int i;
/*  27 */     for (i = rand.nextInt(4) + 5; worldIn.getBlockState(position.down()).getBlock().getMaterial() == Material.water; position = position.down());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     boolean flag = true;
/*     */     
/*  34 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  36 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  38 */         int k = 1;
/*     */         
/*  40 */         if (j == position.getY())
/*     */         {
/*  42 */           k = 0;
/*     */         }
/*     */         
/*  45 */         if (j >= position.getY() + 1 + i - 2)
/*     */         {
/*  47 */           k = 3;
/*     */         }
/*     */         
/*  50 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  52 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  54 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  56 */             if (j >= 0 && j < 256) {
/*     */               
/*  58 */               Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock();
/*     */               
/*  60 */               if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves)
/*     */               {
/*  62 */                 if (block != Blocks.water && block != Blocks.flowing_water)
/*     */                 {
/*  64 */                   flag = false;
/*     */                 }
/*  66 */                 else if (j > position.getY())
/*     */                 {
/*  68 */                   flag = false;
/*     */                 }
/*     */               
/*     */               }
/*     */             } else {
/*     */               
/*  74 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       if (!flag)
/*     */       {
/*  82 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  86 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  88 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt) && position.getY() < 256 - i - 1) {
/*     */         
/*  90 */         func_175921_a(worldIn, position.down());
/*     */         
/*  92 */         for (int l1 = position.getY() - 3 + i; l1 <= position.getY() + i; l1++) {
/*     */           
/*  94 */           int k2 = l1 - position.getY() + i;
/*  95 */           int i3 = 2 - k2 / 2;
/*     */           
/*  97 */           for (int k3 = position.getX() - i3; k3 <= position.getX() + i3; k3++) {
/*     */             
/*  99 */             int l3 = k3 - position.getX();
/*     */             
/* 101 */             for (int j1 = position.getZ() - i3; j1 <= position.getZ() + i3; j1++) {
/*     */               
/* 103 */               int k1 = j1 - position.getZ();
/*     */               
/* 105 */               if (Math.abs(l3) != i3 || Math.abs(k1) != i3 || (rand.nextInt(2) != 0 && k2 != 0)) {
/*     */                 
/* 107 */                 BlockPos blockpos = new BlockPos(k3, l1, j1);
/*     */                 
/* 109 */                 if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock())
/*     */                 {
/* 111 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181649_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 118 */         for (int i2 = 0; i2 < i; i2++) {
/*     */           
/* 120 */           Block block2 = worldIn.getBlockState(position.up(i2)).getBlock();
/*     */           
/* 122 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves || block2 == Blocks.flowing_water || block2 == Blocks.water)
/*     */           {
/* 124 */             setBlockAndNotifyAdequately(worldIn, position.up(i2), field_181648_a);
/*     */           }
/*     */         } 
/*     */         
/* 128 */         for (int j2 = position.getY() - 3 + i; j2 <= position.getY() + i; j2++) {
/*     */           
/* 130 */           int l2 = j2 - position.getY() + i;
/* 131 */           int j3 = 2 - l2 / 2;
/* 132 */           BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */           
/* 134 */           for (int i4 = position.getX() - j3; i4 <= position.getX() + j3; i4++) {
/*     */             
/* 136 */             for (int j4 = position.getZ() - j3; j4 <= position.getZ() + j3; j4++) {
/*     */               
/* 138 */               blockpos$mutableblockpos1.set(i4, j2, j4);
/*     */               
/* 140 */               if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos1).getBlock().getMaterial() == Material.leaves) {
/*     */                 
/* 142 */                 BlockPos blockpos3 = blockpos$mutableblockpos1.west();
/* 143 */                 BlockPos blockpos4 = blockpos$mutableblockpos1.east();
/* 144 */                 BlockPos blockpos1 = blockpos$mutableblockpos1.north();
/* 145 */                 BlockPos blockpos2 = blockpos$mutableblockpos1.south();
/*     */                 
/* 147 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getBlock().getMaterial() == Material.air)
/*     */                 {
/* 149 */                   func_181647_a(worldIn, blockpos3, BlockVine.EAST);
/*     */                 }
/*     */                 
/* 152 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getBlock().getMaterial() == Material.air)
/*     */                 {
/* 154 */                   func_181647_a(worldIn, blockpos4, BlockVine.WEST);
/*     */                 }
/*     */                 
/* 157 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air)
/*     */                 {
/* 159 */                   func_181647_a(worldIn, blockpos1, BlockVine.SOUTH);
/*     */                 }
/*     */                 
/* 162 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getBlock().getMaterial() == Material.air)
/*     */                 {
/* 164 */                   func_181647_a(worldIn, blockpos2, BlockVine.NORTH);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 171 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 175 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_181647_a(World p_181647_1_, BlockPos p_181647_2_, PropertyBool p_181647_3_) {
/* 187 */     IBlockState iblockstate = Blocks.vine.getDefaultState().withProperty((IProperty)p_181647_3_, Boolean.valueOf(true));
/* 188 */     setBlockAndNotifyAdequately(p_181647_1_, p_181647_2_, iblockstate);
/* 189 */     int i = 4;
/*     */     
/* 191 */     for (p_181647_2_ = p_181647_2_.down(); p_181647_1_.getBlockState(p_181647_2_).getBlock().getMaterial() == Material.air && i > 0; i--) {
/*     */       
/* 193 */       setBlockAndNotifyAdequately(p_181647_1_, p_181647_2_, iblockstate);
/* 194 */       p_181647_2_ = p_181647_2_.down();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenSwamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */