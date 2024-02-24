/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenRavine
/*     */   extends MapGenBase
/*     */ {
/*  14 */   private final float[] field_75046_d = new float[1024];
/*     */ 
/*     */   
/*     */   protected void func_180707_a(long p_180707_1_, int p_180707_3_, int p_180707_4_, ChunkPrimer p_180707_5_, double p_180707_6_, double p_180707_8_, double p_180707_10_, float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, double p_180707_17_) {
/*  18 */     Random random = new Random(p_180707_1_);
/*  19 */     double d0 = ((p_180707_3_ << 4) + 8);
/*  20 */     double d1 = ((p_180707_4_ << 4) + 8);
/*  21 */     float f = 0.0F;
/*  22 */     float f1 = 0.0F;
/*     */     
/*  24 */     if (p_180707_16_ <= 0) {
/*     */       
/*  26 */       int i = (this.range << 4) - 16;
/*  27 */       p_180707_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  30 */     boolean flag1 = false;
/*     */     
/*  32 */     if (p_180707_15_ == -1) {
/*     */       
/*  34 */       p_180707_15_ = p_180707_16_ / 2;
/*  35 */       flag1 = true;
/*     */     } 
/*     */     
/*  38 */     float f2 = 1.0F;
/*     */     
/*  40 */     for (int j = 0; j < 256; j++) {
/*     */       
/*  42 */       if (j == 0 || random.nextInt(3) == 0)
/*     */       {
/*  44 */         f2 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
/*     */       }
/*     */       
/*  47 */       this.field_75046_d[j] = f2 * f2;
/*     */     } 
/*     */     
/*  50 */     for (; p_180707_15_ < p_180707_16_; p_180707_15_++) {
/*     */       
/*  52 */       double d9 = 1.5D + (MathHelper.sin(p_180707_15_ * 3.1415927F / p_180707_16_) * p_180707_12_ * 1.0F);
/*  53 */       double d2 = d9 * p_180707_17_;
/*  54 */       d9 *= random.nextFloat() * 0.25D + 0.75D;
/*  55 */       d2 *= random.nextFloat() * 0.25D + 0.75D;
/*  56 */       float f3 = MathHelper.cos(p_180707_14_);
/*  57 */       float f4 = MathHelper.sin(p_180707_14_);
/*  58 */       p_180707_6_ += (MathHelper.cos(p_180707_13_) * f3);
/*  59 */       p_180707_8_ += f4;
/*  60 */       p_180707_10_ += (MathHelper.sin(p_180707_13_) * f3);
/*  61 */       p_180707_14_ *= 0.7F;
/*  62 */       p_180707_14_ += f1 * 0.05F;
/*  63 */       p_180707_13_ += f * 0.05F;
/*  64 */       f1 *= 0.8F;
/*  65 */       f *= 0.5F;
/*  66 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  67 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  69 */       if (flag1 || random.nextInt(4) != 0) {
/*     */         
/*  71 */         double d3 = p_180707_6_ - d0;
/*  72 */         double d4 = p_180707_10_ - d1;
/*  73 */         double d5 = (p_180707_16_ - p_180707_15_);
/*  74 */         double d6 = (p_180707_12_ + 2.0F + 16.0F);
/*     */         
/*  76 */         if (d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  81 */         if (p_180707_6_ >= d0 - 16.0D - d9 * 2.0D && p_180707_10_ >= d1 - 16.0D - d9 * 2.0D && p_180707_6_ <= d0 + 16.0D + d9 * 2.0D && p_180707_10_ <= d1 + 16.0D + d9 * 2.0D) {
/*     */           
/*  83 */           int k2 = MathHelper.floor_double(p_180707_6_ - d9) - (p_180707_3_ << 4) - 1;
/*  84 */           int k = MathHelper.floor_double(p_180707_6_ + d9) - (p_180707_3_ << 4) + 1;
/*  85 */           int l2 = MathHelper.floor_double(p_180707_8_ - d2) - 1;
/*  86 */           int l = MathHelper.floor_double(p_180707_8_ + d2) + 1;
/*  87 */           int i3 = MathHelper.floor_double(p_180707_10_ - d9) - (p_180707_4_ << 4) - 1;
/*  88 */           int i1 = MathHelper.floor_double(p_180707_10_ + d9) - (p_180707_4_ << 4) + 1;
/*     */           
/*  90 */           if (k2 < 0)
/*     */           {
/*  92 */             k2 = 0;
/*     */           }
/*     */           
/*  95 */           if (k > 16)
/*     */           {
/*  97 */             k = 16;
/*     */           }
/*     */           
/* 100 */           if (l2 < 1)
/*     */           {
/* 102 */             l2 = 1;
/*     */           }
/*     */           
/* 105 */           if (l > 248)
/*     */           {
/* 107 */             l = 248;
/*     */           }
/*     */           
/* 110 */           if (i3 < 0)
/*     */           {
/* 112 */             i3 = 0;
/*     */           }
/*     */           
/* 115 */           if (i1 > 16)
/*     */           {
/* 117 */             i1 = 16;
/*     */           }
/*     */           
/* 120 */           boolean flag2 = false;
/*     */           
/* 122 */           for (int j1 = k2; !flag2 && j1 < k; j1++) {
/*     */             
/* 124 */             for (int k1 = i3; !flag2 && k1 < i1; k1++) {
/*     */               
/* 126 */               for (int l1 = l + 1; !flag2 && l1 >= l2 - 1; l1--) {
/*     */                 
/* 128 */                 if (l1 >= 0 && l1 < 256) {
/*     */                   
/* 130 */                   IBlockState iblockstate = p_180707_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 132 */                   if (iblockstate.getBlock() == Blocks.flowing_water || iblockstate.getBlock() == Blocks.water)
/*     */                   {
/* 134 */                     flag2 = true;
/*     */                   }
/*     */                   
/* 137 */                   if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
/*     */                   {
/* 139 */                     l1 = l2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 146 */           if (!flag2) {
/*     */             
/* 148 */             BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */             
/* 150 */             for (int j3 = k2; j3 < k; j3++) {
/*     */               
/* 152 */               double d10 = ((j3 + (p_180707_3_ << 4)) + 0.5D - p_180707_6_) / d9;
/*     */               
/* 154 */               for (int i2 = i3; i2 < i1; i2++) {
/*     */                 
/* 156 */                 double d7 = ((i2 + (p_180707_4_ << 4)) + 0.5D - p_180707_10_) / d9;
/* 157 */                 boolean flag = false;
/*     */                 
/* 159 */                 if (d10 * d10 + d7 * d7 < 1.0D)
/*     */                 {
/* 161 */                   for (int j2 = l; j2 > l2; j2--) {
/*     */                     
/* 163 */                     double d8 = ((j2 - 1) + 0.5D - p_180707_8_) / d2;
/*     */                     
/* 165 */                     if ((d10 * d10 + d7 * d7) * this.field_75046_d[j2 - 1] + d8 * d8 / 6.0D < 1.0D) {
/*     */                       
/* 167 */                       IBlockState iblockstate1 = p_180707_5_.getBlockState(j3, j2, i2);
/*     */                       
/* 169 */                       if (iblockstate1.getBlock() == Blocks.grass)
/*     */                       {
/* 171 */                         flag = true;
/*     */                       }
/*     */                       
/* 174 */                       if (iblockstate1.getBlock() == Blocks.stone || iblockstate1.getBlock() == Blocks.dirt || iblockstate1.getBlock() == Blocks.grass)
/*     */                       {
/* 176 */                         if (j2 - 1 < 10) {
/*     */                           
/* 178 */                           p_180707_5_.setBlockState(j3, j2, i2, Blocks.flowing_lava.getDefaultState());
/*     */                         }
/*     */                         else {
/*     */                           
/* 182 */                           p_180707_5_.setBlockState(j3, j2, i2, Blocks.air.getDefaultState());
/*     */                           
/* 184 */                           if (flag && p_180707_5_.getBlockState(j3, j2 - 1, i2).getBlock() == Blocks.dirt) {
/*     */                             
/* 186 */                             blockpos$mutableblockpos.set(j3 + (p_180707_3_ << 4), 0, i2 + (p_180707_4_ << 4));
/* 187 */                             p_180707_5_.setBlockState(j3, j2 - 1, i2, (this.worldObj.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos)).topBlock);
/*     */                           } 
/*     */                         } 
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 197 */             if (flag1) {
/*     */               break;
/*     */             }
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
/*     */   
/*     */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/* 212 */     if (this.rand.nextInt(50) == 0) {
/*     */       
/* 214 */       double d0 = ((chunkX << 4) + this.rand.nextInt(16));
/* 215 */       double d1 = (this.rand.nextInt(this.rand.nextInt(40) + 8) + 20);
/* 216 */       double d2 = ((chunkZ << 4) + this.rand.nextInt(16));
/* 217 */       int i = 1;
/*     */       
/* 219 */       for (int j = 0; j < i; j++) {
/*     */         
/* 221 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 222 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 223 */         float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
/* 224 */         func_180707_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\MapGenRavine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */