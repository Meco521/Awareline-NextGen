/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenCaves
/*     */   extends MapGenBase
/*     */ {
/*     */   protected void func_180703_a(long p_180703_1_, int p_180703_3_, int p_180703_4_, ChunkPrimer p_180703_5_, double p_180703_6_, double p_180703_8_, double p_180703_10_) {
/*  19 */     func_180702_a(p_180703_1_, p_180703_3_, p_180703_4_, p_180703_5_, p_180703_6_, p_180703_8_, p_180703_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_180702_a(long p_180702_1_, int p_180702_3_, int p_180702_4_, ChunkPrimer p_180702_5_, double p_180702_6_, double p_180702_8_, double p_180702_10_, float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, double p_180702_17_) {
/*  24 */     double d0 = ((p_180702_3_ << 4) + 8);
/*  25 */     double d1 = ((p_180702_4_ << 4) + 8);
/*  26 */     float f = 0.0F;
/*  27 */     float f1 = 0.0F;
/*  28 */     Random random = new Random(p_180702_1_);
/*     */     
/*  30 */     if (p_180702_16_ <= 0) {
/*     */       
/*  32 */       int i = (this.range << 4) - 16;
/*  33 */       p_180702_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  36 */     boolean flag2 = false;
/*     */     
/*  38 */     if (p_180702_15_ == -1) {
/*     */       
/*  40 */       p_180702_15_ = p_180702_16_ / 2;
/*  41 */       flag2 = true;
/*     */     } 
/*     */     
/*  44 */     int j = random.nextInt(p_180702_16_ / 2) + p_180702_16_ / 4;
/*     */     
/*  46 */     for (boolean flag = (random.nextInt(6) == 0); p_180702_15_ < p_180702_16_; p_180702_15_++) {
/*     */       
/*  48 */       double d2 = 1.5D + (MathHelper.sin(p_180702_15_ * 3.1415927F / p_180702_16_) * p_180702_12_ * 1.0F);
/*  49 */       double d3 = d2 * p_180702_17_;
/*  50 */       float f2 = MathHelper.cos(p_180702_14_);
/*  51 */       float f3 = MathHelper.sin(p_180702_14_);
/*  52 */       p_180702_6_ += (MathHelper.cos(p_180702_13_) * f2);
/*  53 */       p_180702_8_ += f3;
/*  54 */       p_180702_10_ += (MathHelper.sin(p_180702_13_) * f2);
/*     */       
/*  56 */       if (flag) {
/*     */         
/*  58 */         p_180702_14_ *= 0.92F;
/*     */       }
/*     */       else {
/*     */         
/*  62 */         p_180702_14_ *= 0.7F;
/*     */       } 
/*     */       
/*  65 */       p_180702_14_ += f1 * 0.1F;
/*  66 */       p_180702_13_ += f * 0.1F;
/*  67 */       f1 *= 0.9F;
/*  68 */       f *= 0.75F;
/*  69 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  70 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  72 */       if (!flag2 && p_180702_15_ == j && p_180702_12_ > 1.0F && p_180702_16_ > 0) {
/*     */         
/*  74 */         func_180702_a(random.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ - 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
/*  75 */         func_180702_a(random.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ + 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
/*     */         
/*     */         return;
/*     */       } 
/*  79 */       if (flag2 || random.nextInt(4) != 0) {
/*     */         
/*  81 */         double d4 = p_180702_6_ - d0;
/*  82 */         double d5 = p_180702_10_ - d1;
/*  83 */         double d6 = (p_180702_16_ - p_180702_15_);
/*  84 */         double d7 = (p_180702_12_ + 2.0F + 16.0F);
/*     */         
/*  86 */         if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  91 */         if (p_180702_6_ >= d0 - 16.0D - d2 * 2.0D && p_180702_10_ >= d1 - 16.0D - d2 * 2.0D && p_180702_6_ <= d0 + 16.0D + d2 * 2.0D && p_180702_10_ <= d1 + 16.0D + d2 * 2.0D) {
/*     */           
/*  93 */           int k2 = MathHelper.floor_double(p_180702_6_ - d2) - (p_180702_3_ << 4) - 1;
/*  94 */           int k = MathHelper.floor_double(p_180702_6_ + d2) - (p_180702_3_ << 4) + 1;
/*  95 */           int l2 = MathHelper.floor_double(p_180702_8_ - d3) - 1;
/*  96 */           int l = MathHelper.floor_double(p_180702_8_ + d3) + 1;
/*  97 */           int i3 = MathHelper.floor_double(p_180702_10_ - d2) - (p_180702_4_ << 4) - 1;
/*  98 */           int i1 = MathHelper.floor_double(p_180702_10_ + d2) - (p_180702_4_ << 4) + 1;
/*     */           
/* 100 */           if (k2 < 0)
/*     */           {
/* 102 */             k2 = 0;
/*     */           }
/*     */           
/* 105 */           if (k > 16)
/*     */           {
/* 107 */             k = 16;
/*     */           }
/*     */           
/* 110 */           if (l2 < 1)
/*     */           {
/* 112 */             l2 = 1;
/*     */           }
/*     */           
/* 115 */           if (l > 248)
/*     */           {
/* 117 */             l = 248;
/*     */           }
/*     */           
/* 120 */           if (i3 < 0)
/*     */           {
/* 122 */             i3 = 0;
/*     */           }
/*     */           
/* 125 */           if (i1 > 16)
/*     */           {
/* 127 */             i1 = 16;
/*     */           }
/*     */           
/* 130 */           boolean flag3 = false;
/*     */           
/* 132 */           for (int j1 = k2; !flag3 && j1 < k; j1++) {
/*     */             
/* 134 */             for (int k1 = i3; !flag3 && k1 < i1; k1++) {
/*     */               
/* 136 */               for (int l1 = l + 1; !flag3 && l1 >= l2 - 1; l1--) {
/*     */                 
/* 138 */                 if (l1 >= 0 && l1 < 256) {
/*     */                   
/* 140 */                   IBlockState iblockstate = p_180702_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 142 */                   if (iblockstate.getBlock() == Blocks.flowing_water || iblockstate.getBlock() == Blocks.water)
/*     */                   {
/* 144 */                     flag3 = true;
/*     */                   }
/*     */                   
/* 147 */                   if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
/*     */                   {
/* 149 */                     l1 = l2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 156 */           if (!flag3) {
/*     */             
/* 158 */             BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */             
/* 160 */             for (int j3 = k2; j3 < k; j3++) {
/*     */               
/* 162 */               double d10 = ((j3 + (p_180702_3_ << 4)) + 0.5D - p_180702_6_) / d2;
/*     */               
/* 164 */               for (int i2 = i3; i2 < i1; i2++) {
/*     */                 
/* 166 */                 double d8 = ((i2 + (p_180702_4_ << 4)) + 0.5D - p_180702_10_) / d2;
/* 167 */                 boolean flag1 = false;
/*     */                 
/* 169 */                 if (d10 * d10 + d8 * d8 < 1.0D)
/*     */                 {
/* 171 */                   for (int j2 = l; j2 > l2; j2--) {
/*     */                     
/* 173 */                     double d9 = ((j2 - 1) + 0.5D - p_180702_8_) / d3;
/*     */                     
/* 175 */                     if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
/*     */                       
/* 177 */                       IBlockState iblockstate1 = p_180702_5_.getBlockState(j3, j2, i2);
/* 178 */                       IBlockState iblockstate2 = (IBlockState)Objects.firstNonNull(p_180702_5_.getBlockState(j3, j2 + 1, i2), Blocks.air.getDefaultState());
/*     */                       
/* 180 */                       if (iblockstate1.getBlock() == Blocks.grass || iblockstate1.getBlock() == Blocks.mycelium)
/*     */                       {
/* 182 */                         flag1 = true;
/*     */                       }
/*     */                       
/* 185 */                       if (func_175793_a(iblockstate1, iblockstate2))
/*     */                       {
/* 187 */                         if (j2 - 1 < 10) {
/*     */                           
/* 189 */                           p_180702_5_.setBlockState(j3, j2, i2, Blocks.lava.getDefaultState());
/*     */                         }
/*     */                         else {
/*     */                           
/* 193 */                           p_180702_5_.setBlockState(j3, j2, i2, Blocks.air.getDefaultState());
/*     */                           
/* 195 */                           if (iblockstate2.getBlock() == Blocks.sand)
/*     */                           {
/* 197 */                             p_180702_5_.setBlockState(j3, j2 + 1, i2, (iblockstate2.getValue((IProperty)BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState());
/*     */                           }
/*     */                           
/* 200 */                           if (flag1 && p_180702_5_.getBlockState(j3, j2 - 1, i2).getBlock() == Blocks.dirt) {
/*     */                             
/* 202 */                             blockpos$mutableblockpos.set(j3 + (p_180702_3_ << 4), 0, i2 + (p_180702_4_ << 4));
/* 203 */                             p_180702_5_.setBlockState(j3, j2 - 1, i2, (this.worldObj.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos)).topBlock.getBlock().getDefaultState());
/*     */                           } 
/*     */                         } 
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 213 */             if (flag2) {
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
/*     */   protected boolean func_175793_a(IBlockState p_175793_1_, IBlockState p_175793_2_) {
/* 225 */     return (p_175793_1_.getBlock() == Blocks.stone) ? true : ((p_175793_1_.getBlock() == Blocks.dirt) ? true : ((p_175793_1_.getBlock() == Blocks.grass) ? true : ((p_175793_1_.getBlock() == Blocks.hardened_clay) ? true : ((p_175793_1_.getBlock() == Blocks.stained_hardened_clay) ? true : ((p_175793_1_.getBlock() == Blocks.sandstone) ? true : ((p_175793_1_.getBlock() == Blocks.red_sandstone) ? true : ((p_175793_1_.getBlock() == Blocks.mycelium) ? true : ((p_175793_1_.getBlock() == Blocks.snow_layer) ? true : (((p_175793_1_.getBlock() == Blocks.sand || p_175793_1_.getBlock() == Blocks.gravel) && p_175793_2_.getBlock().getMaterial() != Material.water))))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/* 233 */     int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
/*     */     
/* 235 */     if (this.rand.nextInt(7) != 0)
/*     */     {
/* 237 */       i = 0;
/*     */     }
/*     */     
/* 240 */     for (int j = 0; j < i; j++) {
/*     */       
/* 242 */       double d0 = ((chunkX << 4) + this.rand.nextInt(16));
/* 243 */       double d1 = this.rand.nextInt(this.rand.nextInt(120) + 8);
/* 244 */       double d2 = ((chunkZ << 4) + this.rand.nextInt(16));
/* 245 */       int k = 1;
/*     */       
/* 247 */       if (this.rand.nextInt(4) == 0) {
/*     */         
/* 249 */         func_180703_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2);
/* 250 */         k += this.rand.nextInt(4);
/*     */       } 
/*     */       
/* 253 */       for (int l = 0; l < k; l++) {
/*     */         
/* 255 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 256 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 257 */         float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/*     */         
/* 259 */         if (this.rand.nextInt(10) == 0)
/*     */         {
/* 261 */           f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
/*     */         }
/*     */         
/* 264 */         func_180702_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\MapGenCaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */