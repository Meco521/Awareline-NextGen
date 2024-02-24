/*     */ package net.minecraft.world.gen.feature;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCocoa;
/*     */ import net.minecraft.block.BlockVine;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenTrees extends WorldGenAbstractTree {
/*  16 */   private static final IBlockState field_181653_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.OAK);
/*  17 */   private static final IBlockState field_181654_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.OAK).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   private final int minTreeHeight;
/*     */ 
/*     */   
/*     */   private final boolean vinesGrow;
/*     */ 
/*     */   
/*     */   private final IBlockState metaWood;
/*     */ 
/*     */   
/*     */   private final IBlockState metaLeaves;
/*     */ 
/*     */   
/*     */   public WorldGenTrees(boolean p_i2027_1_) {
/*  33 */     this(p_i2027_1_, 4, field_181653_a, field_181654_b, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenTrees(boolean p_i46446_1_, int p_i46446_2_, IBlockState p_i46446_3_, IBlockState p_i46446_4_, boolean p_i46446_5_) {
/*  38 */     super(p_i46446_1_);
/*  39 */     this.minTreeHeight = p_i46446_2_;
/*  40 */     this.metaWood = p_i46446_3_;
/*  41 */     this.metaLeaves = p_i46446_4_;
/*  42 */     this.vinesGrow = p_i46446_5_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  47 */     int i = rand.nextInt(3) + this.minTreeHeight;
/*  48 */     boolean flag = true;
/*     */     
/*  50 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  52 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  54 */         int k = 1;
/*     */         
/*  56 */         if (j == position.getY())
/*     */         {
/*  58 */           k = 0;
/*     */         }
/*     */         
/*  61 */         if (j >= position.getY() + 1 + i - 2)
/*     */         {
/*  63 */           k = 2;
/*     */         }
/*     */         
/*  66 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  68 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  70 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  72 */             if (j >= 0 && j < 256) {
/*     */               
/*  74 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock()))
/*     */               {
/*  76 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  81 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  87 */       if (!flag)
/*     */       {
/*  89 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  93 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  95 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt || block1 == Blocks.farmland) && position.getY() < 256 - i - 1) {
/*     */         
/*  97 */         func_175921_a(worldIn, position.down());
/*  98 */         int k2 = 3;
/*  99 */         int l2 = 0;
/*     */         
/* 101 */         for (int i3 = position.getY() - k2 + i; i3 <= position.getY() + i; i3++) {
/*     */           
/* 103 */           int i4 = i3 - position.getY() + i;
/* 104 */           int j1 = l2 + 1 - i4 / 2;
/*     */           
/* 106 */           for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; k1++) {
/*     */             
/* 108 */             int l1 = k1 - position.getX();
/*     */             
/* 110 */             for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; i2++) {
/*     */               
/* 112 */               int j2 = i2 - position.getZ();
/*     */               
/* 114 */               if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || (rand.nextInt(2) != 0 && i4 != 0)) {
/*     */                 
/* 116 */                 BlockPos blockpos = new BlockPos(k1, i3, i2);
/* 117 */                 Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */                 
/* 119 */                 if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves || block.getMaterial() == Material.vine)
/*     */                 {
/* 121 */                   setBlockAndNotifyAdequately(worldIn, blockpos, this.metaLeaves);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 128 */         for (int j3 = 0; j3 < i; j3++) {
/*     */           
/* 130 */           Block block2 = worldIn.getBlockState(position.up(j3)).getBlock();
/*     */           
/* 132 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves || block2.getMaterial() == Material.vine) {
/*     */             
/* 134 */             setBlockAndNotifyAdequately(worldIn, position.up(j3), this.metaWood);
/*     */             
/* 136 */             if (this.vinesGrow && j3 > 0) {
/*     */               
/* 138 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(-1, j3, 0)))
/*     */               {
/* 140 */                 func_181651_a(worldIn, position.add(-1, j3, 0), BlockVine.EAST);
/*     */               }
/*     */               
/* 143 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(1, j3, 0)))
/*     */               {
/* 145 */                 func_181651_a(worldIn, position.add(1, j3, 0), BlockVine.WEST);
/*     */               }
/*     */               
/* 148 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, -1)))
/*     */               {
/* 150 */                 func_181651_a(worldIn, position.add(0, j3, -1), BlockVine.SOUTH);
/*     */               }
/*     */               
/* 153 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, 1)))
/*     */               {
/* 155 */                 func_181651_a(worldIn, position.add(0, j3, 1), BlockVine.NORTH);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 161 */         if (this.vinesGrow) {
/*     */           
/* 163 */           for (int k3 = position.getY() - 3 + i; k3 <= position.getY() + i; k3++) {
/*     */             
/* 165 */             int j4 = k3 - position.getY() + i;
/* 166 */             int k4 = 2 - j4 / 2;
/* 167 */             BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */             
/* 169 */             for (int l4 = position.getX() - k4; l4 <= position.getX() + k4; l4++) {
/*     */               
/* 171 */               for (int i5 = position.getZ() - k4; i5 <= position.getZ() + k4; i5++) {
/*     */                 
/* 173 */                 blockpos$mutableblockpos1.set(l4, k3, i5);
/*     */                 
/* 175 */                 if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos1).getBlock().getMaterial() == Material.leaves) {
/*     */                   
/* 177 */                   BlockPos blockpos2 = blockpos$mutableblockpos1.west();
/* 178 */                   BlockPos blockpos3 = blockpos$mutableblockpos1.east();
/* 179 */                   BlockPos blockpos4 = blockpos$mutableblockpos1.north();
/* 180 */                   BlockPos blockpos1 = blockpos$mutableblockpos1.south();
/*     */                   
/* 182 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getBlock().getMaterial() == Material.air)
/*     */                   {
/* 184 */                     func_181650_b(worldIn, blockpos2, BlockVine.EAST);
/*     */                   }
/*     */                   
/* 187 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getBlock().getMaterial() == Material.air)
/*     */                   {
/* 189 */                     func_181650_b(worldIn, blockpos3, BlockVine.WEST);
/*     */                   }
/*     */                   
/* 192 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getBlock().getMaterial() == Material.air)
/*     */                   {
/* 194 */                     func_181650_b(worldIn, blockpos4, BlockVine.SOUTH);
/*     */                   }
/*     */                   
/* 197 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air)
/*     */                   {
/* 199 */                     func_181650_b(worldIn, blockpos1, BlockVine.NORTH);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 206 */           if (rand.nextInt(5) == 0 && i > 5)
/*     */           {
/* 208 */             for (int l3 = 0; l3 < 2; l3++) {
/*     */               
/* 210 */               for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */                 
/* 212 */                 if (rand.nextInt(4 - l3) == 0) {
/*     */                   
/* 214 */                   EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 215 */                   func_181652_a(worldIn, rand.nextInt(3), position.add(enumfacing1.getFrontOffsetX(), i - 5 + l3, enumfacing1.getFrontOffsetZ()), enumfacing);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 222 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 226 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_181652_a(World p_181652_1_, int p_181652_2_, BlockPos p_181652_3_, EnumFacing p_181652_4_) {
/* 238 */     setBlockAndNotifyAdequately(p_181652_1_, p_181652_3_, Blocks.cocoa.getDefaultState().withProperty((IProperty)BlockCocoa.AGE, Integer.valueOf(p_181652_2_)).withProperty((IProperty)BlockCocoa.FACING, (Comparable)p_181652_4_));
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181651_a(World p_181651_1_, BlockPos p_181651_2_, PropertyBool p_181651_3_) {
/* 243 */     setBlockAndNotifyAdequately(p_181651_1_, p_181651_2_, Blocks.vine.getDefaultState().withProperty((IProperty)p_181651_3_, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181650_b(World p_181650_1_, BlockPos p_181650_2_, PropertyBool p_181650_3_) {
/* 248 */     func_181651_a(p_181650_1_, p_181650_2_, p_181650_3_);
/* 249 */     int i = 4;
/*     */     
/* 251 */     for (p_181650_2_ = p_181650_2_.down(); p_181650_1_.getBlockState(p_181650_2_).getBlock().getMaterial() == Material.air && i > 0; i--) {
/*     */       
/* 253 */       func_181651_a(p_181650_1_, p_181650_2_, p_181650_3_);
/* 254 */       p_181650_2_ = p_181650_2_.down();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenTrees.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */