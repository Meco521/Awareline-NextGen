/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ 
/*     */ public class MapGenCavesHell
/*     */   extends MapGenBase
/*     */ {
/*     */   protected void func_180705_a(long p_180705_1_, int p_180705_3_, int p_180705_4_, ChunkPrimer p_180705_5_, double p_180705_6_, double p_180705_8_, double p_180705_10_) {
/*  15 */     func_180704_a(p_180705_1_, p_180705_3_, p_180705_4_, p_180705_5_, p_180705_6_, p_180705_8_, p_180705_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_180704_a(long p_180704_1_, int p_180704_3_, int p_180704_4_, ChunkPrimer p_180704_5_, double p_180704_6_, double p_180704_8_, double p_180704_10_, float p_180704_12_, float p_180704_13_, float p_180704_14_, int p_180704_15_, int p_180704_16_, double p_180704_17_) {
/*  20 */     double d0 = ((p_180704_3_ << 4) + 8);
/*  21 */     double d1 = ((p_180704_4_ << 4) + 8);
/*  22 */     float f = 0.0F;
/*  23 */     float f1 = 0.0F;
/*  24 */     Random random = new Random(p_180704_1_);
/*     */     
/*  26 */     if (p_180704_16_ <= 0) {
/*     */       
/*  28 */       int i = (this.range << 4) - 16;
/*  29 */       p_180704_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  32 */     boolean flag1 = false;
/*     */     
/*  34 */     if (p_180704_15_ == -1) {
/*     */       
/*  36 */       p_180704_15_ = p_180704_16_ / 2;
/*  37 */       flag1 = true;
/*     */     } 
/*     */     
/*  40 */     int j = random.nextInt(p_180704_16_ / 2) + p_180704_16_ / 4;
/*     */     
/*  42 */     for (boolean flag = (random.nextInt(6) == 0); p_180704_15_ < p_180704_16_; p_180704_15_++) {
/*     */       
/*  44 */       double d2 = 1.5D + (MathHelper.sin(p_180704_15_ * 3.1415927F / p_180704_16_) * p_180704_12_ * 1.0F);
/*  45 */       double d3 = d2 * p_180704_17_;
/*  46 */       float f2 = MathHelper.cos(p_180704_14_);
/*  47 */       float f3 = MathHelper.sin(p_180704_14_);
/*  48 */       p_180704_6_ += (MathHelper.cos(p_180704_13_) * f2);
/*  49 */       p_180704_8_ += f3;
/*  50 */       p_180704_10_ += (MathHelper.sin(p_180704_13_) * f2);
/*     */       
/*  52 */       if (flag) {
/*     */         
/*  54 */         p_180704_14_ *= 0.92F;
/*     */       }
/*     */       else {
/*     */         
/*  58 */         p_180704_14_ *= 0.7F;
/*     */       } 
/*     */       
/*  61 */       p_180704_14_ += f1 * 0.1F;
/*  62 */       p_180704_13_ += f * 0.1F;
/*  63 */       f1 *= 0.9F;
/*  64 */       f *= 0.75F;
/*  65 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  66 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  68 */       if (!flag1 && p_180704_15_ == j && p_180704_12_ > 1.0F) {
/*     */         
/*  70 */         func_180704_a(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ - 1.5707964F, p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
/*  71 */         func_180704_a(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ + 1.5707964F, p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
/*     */         
/*     */         return;
/*     */       } 
/*  75 */       if (flag1 || random.nextInt(4) != 0) {
/*     */         
/*  77 */         double d4 = p_180704_6_ - d0;
/*  78 */         double d5 = p_180704_10_ - d1;
/*  79 */         double d6 = (p_180704_16_ - p_180704_15_);
/*  80 */         double d7 = (p_180704_12_ + 2.0F + 16.0F);
/*     */         
/*  82 */         if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  87 */         if (p_180704_6_ >= d0 - 16.0D - d2 * 2.0D && p_180704_10_ >= d1 - 16.0D - d2 * 2.0D && p_180704_6_ <= d0 + 16.0D + d2 * 2.0D && p_180704_10_ <= d1 + 16.0D + d2 * 2.0D) {
/*     */           
/*  89 */           int j2 = MathHelper.floor_double(p_180704_6_ - d2) - (p_180704_3_ << 4) - 1;
/*  90 */           int k = MathHelper.floor_double(p_180704_6_ + d2) - (p_180704_3_ << 4) + 1;
/*  91 */           int k2 = MathHelper.floor_double(p_180704_8_ - d3) - 1;
/*  92 */           int l = MathHelper.floor_double(p_180704_8_ + d3) + 1;
/*  93 */           int l2 = MathHelper.floor_double(p_180704_10_ - d2) - (p_180704_4_ << 4) - 1;
/*  94 */           int i1 = MathHelper.floor_double(p_180704_10_ + d2) - (p_180704_4_ << 4) + 1;
/*     */           
/*  96 */           if (j2 < 0)
/*     */           {
/*  98 */             j2 = 0;
/*     */           }
/*     */           
/* 101 */           if (k > 16)
/*     */           {
/* 103 */             k = 16;
/*     */           }
/*     */           
/* 106 */           if (k2 < 1)
/*     */           {
/* 108 */             k2 = 1;
/*     */           }
/*     */           
/* 111 */           if (l > 120)
/*     */           {
/* 113 */             l = 120;
/*     */           }
/*     */           
/* 116 */           if (l2 < 0)
/*     */           {
/* 118 */             l2 = 0;
/*     */           }
/*     */           
/* 121 */           if (i1 > 16)
/*     */           {
/* 123 */             i1 = 16;
/*     */           }
/*     */           
/* 126 */           boolean flag2 = false;
/*     */           
/* 128 */           for (int j1 = j2; !flag2 && j1 < k; j1++) {
/*     */             
/* 130 */             for (int k1 = l2; !flag2 && k1 < i1; k1++) {
/*     */               
/* 132 */               for (int l1 = l + 1; !flag2 && l1 >= k2 - 1; l1--) {
/*     */                 
/* 134 */                 if (l1 >= 0 && l1 < 128) {
/*     */                   
/* 136 */                   IBlockState iblockstate = p_180704_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 138 */                   if (iblockstate.getBlock() == Blocks.flowing_lava || iblockstate.getBlock() == Blocks.lava)
/*     */                   {
/* 140 */                     flag2 = true;
/*     */                   }
/*     */                   
/* 143 */                   if (l1 != k2 - 1 && j1 != j2 && j1 != k - 1 && k1 != l2 && k1 != i1 - 1)
/*     */                   {
/* 145 */                     l1 = k2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 152 */           if (!flag2) {
/*     */             
/* 154 */             for (int i3 = j2; i3 < k; i3++) {
/*     */               
/* 156 */               double d10 = ((i3 + (p_180704_3_ << 4)) + 0.5D - p_180704_6_) / d2;
/*     */               
/* 158 */               for (int j3 = l2; j3 < i1; j3++) {
/*     */                 
/* 160 */                 double d8 = ((j3 + (p_180704_4_ << 4)) + 0.5D - p_180704_10_) / d2;
/*     */                 
/* 162 */                 for (int i2 = l; i2 > k2; i2--) {
/*     */                   
/* 164 */                   double d9 = ((i2 - 1) + 0.5D - p_180704_8_) / d3;
/*     */                   
/* 166 */                   if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
/*     */                     
/* 168 */                     IBlockState iblockstate1 = p_180704_5_.getBlockState(i3, i2, j3);
/*     */                     
/* 170 */                     if (iblockstate1.getBlock() == Blocks.netherrack || iblockstate1.getBlock() == Blocks.dirt || iblockstate1.getBlock() == Blocks.grass)
/*     */                     {
/* 172 */                       p_180704_5_.setBlockState(i3, i2, j3, Blocks.air.getDefaultState());
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 179 */             if (flag1) {
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
/* 194 */     int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
/*     */     
/* 196 */     if (this.rand.nextInt(5) != 0)
/*     */     {
/* 198 */       i = 0;
/*     */     }
/*     */     
/* 201 */     for (int j = 0; j < i; j++) {
/*     */       
/* 203 */       double d0 = ((chunkX << 4) + this.rand.nextInt(16));
/* 204 */       double d1 = this.rand.nextInt(128);
/* 205 */       double d2 = ((chunkZ << 4) + this.rand.nextInt(16));
/* 206 */       int k = 1;
/*     */       
/* 208 */       if (this.rand.nextInt(4) == 0) {
/*     */         
/* 210 */         func_180705_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2);
/* 211 */         k += this.rand.nextInt(4);
/*     */       } 
/*     */       
/* 214 */       for (int l = 0; l < k; l++) {
/*     */         
/* 216 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 217 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 218 */         float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/* 219 */         func_180704_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2 * 2.0F, f, f1, 0, 0, 0.5D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\MapGenCavesHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */