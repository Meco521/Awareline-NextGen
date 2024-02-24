/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockLog;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenBigTree
/*     */   extends WorldGenAbstractTree {
/*     */   private Random rand;
/*     */   private World world;
/*  21 */   private BlockPos basePos = BlockPos.ORIGIN;
/*     */   int heightLimit;
/*     */   int height;
/*  24 */   double heightAttenuation = 0.618D;
/*  25 */   double branchSlope = 0.381D;
/*  26 */   double scaleWidth = 1.0D;
/*  27 */   double leafDensity = 1.0D;
/*  28 */   int trunkSize = 1;
/*  29 */   int heightLimitLimit = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   int leafDistanceLimit = 4;
/*     */   
/*     */   List<FoliageCoordinates> field_175948_j;
/*     */   
/*     */   public WorldGenBigTree(boolean p_i2008_1_) {
/*  39 */     super(p_i2008_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNodeList() {
/*  47 */     this.height = (int)(this.heightLimit * this.heightAttenuation);
/*     */     
/*  49 */     if (this.height >= this.heightLimit)
/*     */     {
/*  51 */       this.height = this.heightLimit - 1;
/*     */     }
/*     */     
/*  54 */     int i = (int)(1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));
/*     */     
/*  56 */     if (i < 1)
/*     */     {
/*  58 */       i = 1;
/*     */     }
/*     */     
/*  61 */     int j = this.basePos.getY() + this.height;
/*  62 */     int k = this.heightLimit - this.leafDistanceLimit;
/*  63 */     this.field_175948_j = Lists.newArrayList();
/*  64 */     this.field_175948_j.add(new FoliageCoordinates(this.basePos.up(k), j));
/*     */     
/*  66 */     for (; k >= 0; k--) {
/*     */       
/*  68 */       float f = layerSize(k);
/*     */       
/*  70 */       if (f >= 0.0F)
/*     */       {
/*  72 */         for (int l = 0; l < i; l++) {
/*     */           
/*  74 */           double d0 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328D);
/*  75 */           double d1 = (this.rand.nextFloat() * 2.0F) * Math.PI;
/*  76 */           double d2 = d0 * Math.sin(d1) + 0.5D;
/*  77 */           double d3 = d0 * Math.cos(d1) + 0.5D;
/*  78 */           BlockPos blockpos = this.basePos.add(d2, (k - 1), d3);
/*  79 */           BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);
/*     */           
/*  81 */           if (checkBlockLine(blockpos, blockpos1) == -1) {
/*     */             
/*  83 */             int i1 = this.basePos.getX() - blockpos.getX();
/*  84 */             int j1 = this.basePos.getZ() - blockpos.getZ();
/*  85 */             double d4 = blockpos.getY() - Math.sqrt((i1 * i1 + j1 * j1)) * this.branchSlope;
/*  86 */             int k1 = (d4 > j) ? j : (int)d4;
/*  87 */             BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());
/*     */             
/*  89 */             if (checkBlockLine(blockpos2, blockpos) == -1)
/*     */             {
/*  91 */               this.field_175948_j.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void func_181631_a(BlockPos p_181631_1_, float p_181631_2_, IBlockState p_181631_3_) {
/* 101 */     int i = (int)(p_181631_2_ + 0.618D);
/*     */     
/* 103 */     for (int j = -i; j <= i; j++) {
/*     */       
/* 105 */       for (int k = -i; k <= i; k++) {
/*     */         
/* 107 */         if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= (p_181631_2_ * p_181631_2_)) {
/*     */           
/* 109 */           BlockPos blockpos = p_181631_1_.add(j, 0, k);
/* 110 */           Material material = this.world.getBlockState(blockpos).getBlock().getMaterial();
/*     */           
/* 112 */           if (material == Material.air || material == Material.leaves)
/*     */           {
/* 114 */             setBlockAndNotifyAdequately(this.world, blockpos, p_181631_3_);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float layerSize(int p_76490_1_) {
/* 126 */     if (p_76490_1_ < this.heightLimit * 0.3F)
/*     */     {
/* 128 */       return -1.0F;
/*     */     }
/*     */ 
/*     */     
/* 132 */     float f = this.heightLimit / 2.0F;
/* 133 */     float f1 = f - p_76490_1_;
/* 134 */     float f2 = MathHelper.sqrt_float(f * f - f1 * f1);
/*     */     
/* 136 */     if (f1 == 0.0F) {
/*     */       
/* 138 */       f2 = f;
/*     */     }
/* 140 */     else if (Math.abs(f1) >= f) {
/*     */       
/* 142 */       return 0.0F;
/*     */     } 
/*     */     
/* 145 */     return f2 * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   float leafSize(int p_76495_1_) {
/* 151 */     return (p_76495_1_ >= 0 && p_76495_1_ < this.leafDistanceLimit) ? ((p_76495_1_ != 0 && p_76495_1_ != this.leafDistanceLimit - 1) ? 3.0F : 2.0F) : -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNode(BlockPos pos) {
/* 159 */     for (int i = 0; i < this.leafDistanceLimit; i++)
/*     */     {
/* 161 */       func_181631_a(pos.up(i), leafSize(i), Blocks.leaves.getDefaultState().withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void func_175937_a(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_) {
/* 167 */     BlockPos blockpos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
/* 168 */     int i = getGreatestDistance(blockpos);
/* 169 */     float f = blockpos.getX() / i;
/* 170 */     float f1 = blockpos.getY() / i;
/* 171 */     float f2 = blockpos.getZ() / i;
/*     */     
/* 173 */     for (int j = 0; j <= i; j++) {
/*     */       
/* 175 */       BlockPos blockpos1 = p_175937_1_.add((0.5F + j * f), (0.5F + j * f1), (0.5F + j * f2));
/* 176 */       BlockLog.EnumAxis blocklog$enumaxis = func_175938_b(p_175937_1_, blockpos1);
/* 177 */       setBlockAndNotifyAdequately(this.world, blockpos1, p_175937_3_.getDefaultState().withProperty((IProperty)BlockLog.LOG_AXIS, (Comparable)blocklog$enumaxis));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getGreatestDistance(BlockPos posIn) {
/* 186 */     int i = MathHelper.abs_int(posIn.getX());
/* 187 */     int j = MathHelper.abs_int(posIn.getY());
/* 188 */     int k = MathHelper.abs_int(posIn.getZ());
/* 189 */     return (k > i && k > j) ? k : ((j > i) ? j : i);
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockLog.EnumAxis func_175938_b(BlockPos p_175938_1_, BlockPos p_175938_2_) {
/* 194 */     BlockLog.EnumAxis blocklog$enumaxis = BlockLog.EnumAxis.Y;
/* 195 */     int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
/* 196 */     int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
/* 197 */     int k = Math.max(i, j);
/*     */     
/* 199 */     if (k > 0)
/*     */     {
/* 201 */       if (i == k) {
/*     */         
/* 203 */         blocklog$enumaxis = BlockLog.EnumAxis.X;
/*     */       }
/* 205 */       else if (j == k) {
/*     */         
/* 207 */         blocklog$enumaxis = BlockLog.EnumAxis.Z;
/*     */       } 
/*     */     }
/*     */     
/* 211 */     return blocklog$enumaxis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeaves() {
/* 219 */     for (FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j)
/*     */     {
/* 221 */       generateLeafNode(worldgenbigtree$foliagecoordinates);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean leafNodeNeedsBase(int p_76493_1_) {
/* 230 */     return (p_76493_1_ >= this.heightLimit * 0.2D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateTrunk() {
/* 239 */     BlockPos blockpos = this.basePos;
/* 240 */     BlockPos blockpos1 = this.basePos.up(this.height);
/* 241 */     Block block = Blocks.log;
/* 242 */     func_175937_a(blockpos, blockpos1, block);
/*     */     
/* 244 */     if (this.trunkSize == 2) {
/*     */       
/* 246 */       func_175937_a(blockpos.east(), blockpos1.east(), block);
/* 247 */       func_175937_a(blockpos.east().south(), blockpos1.east().south(), block);
/* 248 */       func_175937_a(blockpos.south(), blockpos1.south(), block);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNodeBases() {
/* 257 */     for (FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j) {
/*     */       
/* 259 */       int i = worldgenbigtree$foliagecoordinates.func_177999_q();
/* 260 */       BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());
/*     */       
/* 262 */       if (!blockpos.equals(worldgenbigtree$foliagecoordinates) && leafNodeNeedsBase(i - this.basePos.getY()))
/*     */       {
/* 264 */         func_175937_a(blockpos, worldgenbigtree$foliagecoordinates, Blocks.log);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int checkBlockLine(BlockPos posOne, BlockPos posTwo) {
/* 275 */     BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
/* 276 */     int i = getGreatestDistance(blockpos);
/* 277 */     float f = blockpos.getX() / i;
/* 278 */     float f1 = blockpos.getY() / i;
/* 279 */     float f2 = blockpos.getZ() / i;
/*     */     
/* 281 */     if (i == 0)
/*     */     {
/* 283 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 287 */     for (int j = 0; j <= i; j++) {
/*     */       
/* 289 */       BlockPos blockpos1 = posOne.add((0.5F + j * f), (0.5F + j * f1), (0.5F + j * f2));
/*     */       
/* 291 */       if (!func_150523_a(this.world.getBlockState(blockpos1).getBlock()))
/*     */       {
/* 293 */         return j;
/*     */       }
/*     */     } 
/*     */     
/* 297 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_175904_e() {
/* 303 */     this.leafDistanceLimit = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 308 */     this.world = worldIn;
/* 309 */     this.basePos = position;
/* 310 */     this.rand = new Random(rand.nextLong());
/*     */     
/* 312 */     if (this.heightLimit == 0)
/*     */     {
/* 314 */       this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
/*     */     }
/*     */     
/* 317 */     if (!validTreeLocation())
/*     */     {
/* 319 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 323 */     generateLeafNodeList();
/* 324 */     generateLeaves();
/* 325 */     generateTrunk();
/* 326 */     generateLeafNodeBases();
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validTreeLocation() {
/* 337 */     Block block = this.world.getBlockState(this.basePos.down()).getBlock();
/*     */     
/* 339 */     if (block != Blocks.dirt && block != Blocks.grass && block != Blocks.farmland)
/*     */     {
/* 341 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 345 */     int i = checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));
/*     */     
/* 347 */     if (i == -1)
/*     */     {
/* 349 */       return true;
/*     */     }
/* 351 */     if (i < 6)
/*     */     {
/* 353 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 357 */     this.heightLimit = i;
/* 358 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static class FoliageCoordinates
/*     */     extends BlockPos
/*     */   {
/*     */     private final int field_178000_b;
/*     */ 
/*     */     
/*     */     public FoliageCoordinates(BlockPos p_i45635_1_, int p_i45635_2_) {
/* 369 */       super(p_i45635_1_.getX(), p_i45635_1_.getY(), p_i45635_1_.getZ());
/* 370 */       this.field_178000_b = p_i45635_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_177999_q() {
/* 375 */       return this.field_178000_b;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenBigTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */