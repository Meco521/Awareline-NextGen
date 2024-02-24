/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockHelper;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.feature.WorldGenFire;
/*     */ import net.minecraft.world.gen.feature.WorldGenGlowStone1;
/*     */ import net.minecraft.world.gen.feature.WorldGenGlowStone2;
/*     */ import net.minecraft.world.gen.feature.WorldGenHellLava;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import net.minecraft.world.gen.structure.MapGenNetherBridge;
/*     */ 
/*     */ public class ChunkProviderHell implements IChunkProvider {
/*     */   private final World worldObj;
/*     */   private final boolean field_177466_i;
/*     */   private final Random hellRNG;
/*  34 */   private double[] slowsandNoise = new double[256];
/*  35 */   private double[] gravelNoise = new double[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private double[] netherrackExclusivityNoise = new double[256];
/*     */   
/*     */   private double[] noiseField;
/*     */   
/*     */   private final NoiseGeneratorOctaves netherNoiseGen1;
/*     */   
/*     */   private final NoiseGeneratorOctaves netherNoiseGen2;
/*     */   
/*     */   private final NoiseGeneratorOctaves netherNoiseGen3;
/*     */   
/*     */   private final NoiseGeneratorOctaves slowsandGravelNoiseGen;
/*     */   
/*     */   private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
/*     */   
/*     */   public final NoiseGeneratorOctaves netherNoiseGen6;
/*     */   
/*     */   public final NoiseGeneratorOctaves netherNoiseGen7;
/*  57 */   private final WorldGenFire field_177470_t = new WorldGenFire();
/*  58 */   private final WorldGenGlowStone1 field_177469_u = new WorldGenGlowStone1();
/*  59 */   private final WorldGenGlowStone2 field_177468_v = new WorldGenGlowStone2();
/*  60 */   private final WorldGenerator field_177467_w = (WorldGenerator)new WorldGenMinable(Blocks.quartz_ore.getDefaultState(), 14, (Predicate)BlockHelper.forBlock(Blocks.netherrack));
/*  61 */   private final WorldGenHellLava field_177473_x = new WorldGenHellLava((Block)Blocks.flowing_lava, true);
/*  62 */   private final WorldGenHellLava field_177472_y = new WorldGenHellLava((Block)Blocks.flowing_lava, false);
/*  63 */   private final GeneratorBushFeature field_177471_z = new GeneratorBushFeature(Blocks.brown_mushroom);
/*  64 */   private final GeneratorBushFeature field_177465_A = new GeneratorBushFeature(Blocks.red_mushroom);
/*  65 */   private final MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
/*  66 */   private final MapGenBase netherCaveGenerator = new MapGenCavesHell();
/*     */   
/*     */   double[] noiseData1;
/*     */   double[] noiseData2;
/*     */   double[] noiseData3;
/*     */   double[] noiseData4;
/*     */   double[] noiseData5;
/*     */   
/*     */   public ChunkProviderHell(World worldIn, boolean p_i45637_2_, long seed) {
/*  75 */     this.worldObj = worldIn;
/*  76 */     this.field_177466_i = p_i45637_2_;
/*  77 */     this.hellRNG = new Random(seed);
/*  78 */     this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  79 */     this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  80 */     this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
/*  81 */     this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
/*  82 */     this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
/*  83 */     this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
/*  84 */     this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  85 */     worldIn.setSeaLevel(63);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180515_a(int p_180515_1_, int p_180515_2_, ChunkPrimer p_180515_3_) {
/*  90 */     int i = 4;
/*  91 */     int j = this.worldObj.getSeaLevel() / 2 + 1;
/*  92 */     int k = i + 1;
/*  93 */     int l = 17;
/*  94 */     int i1 = i + 1;
/*  95 */     this.noiseField = initializeNoiseField(this.noiseField, p_180515_1_ * i, 0, p_180515_2_ * i, k, l, i1);
/*     */     
/*  97 */     for (int j1 = 0; j1 < i; j1++) {
/*     */       
/*  99 */       for (int k1 = 0; k1 < i; k1++) {
/*     */         
/* 101 */         for (int l1 = 0; l1 < 16; l1++) {
/*     */           
/* 103 */           double d0 = 0.125D;
/* 104 */           double d1 = this.noiseField[((j1 + 0) * i1 + k1 + 0) * l + l1 + 0];
/* 105 */           double d2 = this.noiseField[((j1 + 0) * i1 + k1 + 1) * l + l1 + 0];
/* 106 */           double d3 = this.noiseField[((j1 + 1) * i1 + k1 + 0) * l + l1 + 0];
/* 107 */           double d4 = this.noiseField[((j1 + 1) * i1 + k1 + 1) * l + l1 + 0];
/* 108 */           double d5 = (this.noiseField[((j1 + 0) * i1 + k1 + 0) * l + l1 + 1] - d1) * d0;
/* 109 */           double d6 = (this.noiseField[((j1 + 0) * i1 + k1 + 1) * l + l1 + 1] - d2) * d0;
/* 110 */           double d7 = (this.noiseField[((j1 + 1) * i1 + k1 + 0) * l + l1 + 1] - d3) * d0;
/* 111 */           double d8 = (this.noiseField[((j1 + 1) * i1 + k1 + 1) * l + l1 + 1] - d4) * d0;
/*     */           
/* 113 */           for (int i2 = 0; i2 < 8; i2++) {
/*     */             
/* 115 */             double d9 = 0.25D;
/* 116 */             double d10 = d1;
/* 117 */             double d11 = d2;
/* 118 */             double d12 = (d3 - d1) * d9;
/* 119 */             double d13 = (d4 - d2) * d9;
/*     */             
/* 121 */             for (int j2 = 0; j2 < 4; j2++) {
/*     */               
/* 123 */               double d14 = 0.25D;
/* 124 */               double d15 = d10;
/* 125 */               double d16 = (d11 - d10) * d14;
/*     */               
/* 127 */               for (int k2 = 0; k2 < 4; k2++) {
/*     */                 
/* 129 */                 IBlockState iblockstate = null;
/*     */                 
/* 131 */                 if (l1 * 8 + i2 < j)
/*     */                 {
/* 133 */                   iblockstate = Blocks.lava.getDefaultState();
/*     */                 }
/*     */                 
/* 136 */                 if (d15 > 0.0D)
/*     */                 {
/* 138 */                   iblockstate = Blocks.netherrack.getDefaultState();
/*     */                 }
/*     */                 
/* 141 */                 int l2 = j2 + j1 * 4;
/* 142 */                 int i3 = i2 + l1 * 8;
/* 143 */                 int j3 = k2 + k1 * 4;
/* 144 */                 p_180515_3_.setBlockState(l2, i3, j3, iblockstate);
/* 145 */                 d15 += d16;
/*     */               } 
/*     */               
/* 148 */               d10 += d12;
/* 149 */               d11 += d13;
/*     */             } 
/*     */             
/* 152 */             d1 += d5;
/* 153 */             d2 += d6;
/* 154 */             d3 += d7;
/* 155 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180516_b(int p_180516_1_, int p_180516_2_, ChunkPrimer p_180516_3_) {
/* 164 */     int i = this.worldObj.getSeaLevel() + 1;
/* 165 */     double d0 = 0.03125D;
/* 166 */     this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_180516_1_ * 16, p_180516_2_ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
/* 167 */     this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_180516_1_ * 16, 109, p_180516_2_ * 16, 16, 1, 16, d0, 1.0D, d0);
/* 168 */     this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, p_180516_1_ * 16, p_180516_2_ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);
/*     */     
/* 170 */     for (int j = 0; j < 16; j++) {
/*     */       
/* 172 */       for (int k = 0; k < 16; k++) {
/*     */         
/* 174 */         boolean flag = (this.slowsandNoise[j + k * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D);
/* 175 */         boolean flag1 = (this.gravelNoise[j + k * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D);
/* 176 */         int l = (int)(this.netherrackExclusivityNoise[j + k * 16] / 3.0D + 3.0D + this.hellRNG.nextDouble() * 0.25D);
/* 177 */         int i1 = -1;
/* 178 */         IBlockState iblockstate = Blocks.netherrack.getDefaultState();
/* 179 */         IBlockState iblockstate1 = Blocks.netherrack.getDefaultState();
/*     */         
/* 181 */         for (int j1 = 127; j1 >= 0; j1--) {
/*     */           
/* 183 */           if (j1 < 127 - this.hellRNG.nextInt(5) && j1 > this.hellRNG.nextInt(5)) {
/*     */             
/* 185 */             IBlockState iblockstate2 = p_180516_3_.getBlockState(k, j1, j);
/*     */             
/* 187 */             if (iblockstate2.getBlock() != null && iblockstate2.getBlock().getMaterial() != Material.air) {
/*     */               
/* 189 */               if (iblockstate2.getBlock() == Blocks.netherrack)
/*     */               {
/* 191 */                 if (i1 == -1) {
/*     */                   
/* 193 */                   if (l <= 0) {
/*     */                     
/* 195 */                     iblockstate = null;
/* 196 */                     iblockstate1 = Blocks.netherrack.getDefaultState();
/*     */                   }
/* 198 */                   else if (j1 >= i - 4 && j1 <= i + 1) {
/*     */                     
/* 200 */                     iblockstate = Blocks.netherrack.getDefaultState();
/* 201 */                     iblockstate1 = Blocks.netherrack.getDefaultState();
/*     */                     
/* 203 */                     if (flag1) {
/*     */                       
/* 205 */                       iblockstate = Blocks.gravel.getDefaultState();
/* 206 */                       iblockstate1 = Blocks.netherrack.getDefaultState();
/*     */                     } 
/*     */                     
/* 209 */                     if (flag) {
/*     */                       
/* 211 */                       iblockstate = Blocks.soul_sand.getDefaultState();
/* 212 */                       iblockstate1 = Blocks.soul_sand.getDefaultState();
/*     */                     } 
/*     */                   } 
/*     */                   
/* 216 */                   if (j1 < i && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air))
/*     */                   {
/* 218 */                     iblockstate = Blocks.lava.getDefaultState();
/*     */                   }
/*     */                   
/* 221 */                   i1 = l;
/*     */                   
/* 223 */                   if (j1 >= i - 1)
/*     */                   {
/* 225 */                     p_180516_3_.setBlockState(k, j1, j, iblockstate);
/*     */                   }
/*     */                   else
/*     */                   {
/* 229 */                     p_180516_3_.setBlockState(k, j1, j, iblockstate1);
/*     */                   }
/*     */                 
/* 232 */                 } else if (i1 > 0) {
/*     */                   
/* 234 */                   i1--;
/* 235 */                   p_180516_3_.setBlockState(k, j1, j, iblockstate1);
/*     */                 }
/*     */               
/*     */               }
/*     */             } else {
/*     */               
/* 241 */               i1 = -1;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 246 */             p_180516_3_.setBlockState(k, j1, j, Blocks.bedrock.getDefaultState());
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
/*     */   public Chunk provideChunk(int x, int z) {
/* 259 */     this.hellRNG.setSeed(x * 341873128712L + z * 132897987541L);
/* 260 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 261 */     func_180515_a(x, z, chunkprimer);
/* 262 */     func_180516_b(x, z, chunkprimer);
/* 263 */     this.netherCaveGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     
/* 265 */     if (this.field_177466_i)
/*     */     {
/* 267 */       this.genNetherBridge.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 270 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 271 */     BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, x * 16, z * 16, 16, 16);
/* 272 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 274 */     for (int i = 0; i < abyte.length; i++)
/*     */     {
/* 276 */       abyte[i] = (byte)(abiomegenbase[i]).biomeID;
/*     */     }
/*     */     
/* 279 */     chunk.resetRelightChecks();
/* 280 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] initializeNoiseField(double[] p_73164_1_, int p_73164_2_, int p_73164_3_, int p_73164_4_, int p_73164_5_, int p_73164_6_, int p_73164_7_) {
/* 289 */     if (p_73164_1_ == null)
/*     */     {
/* 291 */       p_73164_1_ = new double[p_73164_5_ * p_73164_6_ * p_73164_7_];
/*     */     }
/*     */     
/* 294 */     double d0 = 684.412D;
/* 295 */     double d1 = 2053.236D;
/* 296 */     this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 1.0D, 0.0D, 1.0D);
/* 297 */     this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 100.0D, 0.0D, 100.0D);
/* 298 */     this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
/* 299 */     this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
/* 300 */     this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
/* 301 */     int i = 0;
/* 302 */     double[] adouble = new double[p_73164_6_];
/*     */     
/* 304 */     for (int j = 0; j < p_73164_6_; j++) {
/*     */       
/* 306 */       adouble[j] = Math.cos(j * Math.PI * 6.0D / p_73164_6_) * 2.0D;
/* 307 */       double d2 = j;
/*     */       
/* 309 */       if (j > p_73164_6_ / 2)
/*     */       {
/* 311 */         d2 = (p_73164_6_ - 1 - j);
/*     */       }
/*     */       
/* 314 */       if (d2 < 4.0D) {
/*     */         
/* 316 */         d2 = 4.0D - d2;
/* 317 */         adouble[j] = adouble[j] - d2 * d2 * d2 * 10.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 321 */     for (int l = 0; l < p_73164_5_; l++) {
/*     */       
/* 323 */       for (int i1 = 0; i1 < p_73164_7_; i1++) {
/*     */         
/* 325 */         double d3 = 0.0D;
/*     */         
/* 327 */         for (int k = 0; k < p_73164_6_; k++) {
/*     */           
/* 329 */           double d4 = 0.0D;
/* 330 */           double d5 = adouble[k];
/* 331 */           double d6 = this.noiseData2[i] / 512.0D;
/* 332 */           double d7 = this.noiseData3[i] / 512.0D;
/* 333 */           double d8 = (this.noiseData1[i] / 10.0D + 1.0D) / 2.0D;
/*     */           
/* 335 */           if (d8 < 0.0D) {
/*     */             
/* 337 */             d4 = d6;
/*     */           }
/* 339 */           else if (d8 > 1.0D) {
/*     */             
/* 341 */             d4 = d7;
/*     */           }
/*     */           else {
/*     */             
/* 345 */             d4 = d6 + (d7 - d6) * d8;
/*     */           } 
/*     */           
/* 348 */           d4 -= d5;
/*     */           
/* 350 */           if (k > p_73164_6_ - 4) {
/*     */             
/* 352 */             double d9 = ((k - p_73164_6_ - 4) / 3.0F);
/* 353 */             d4 = d4 * (1.0D - d9) + -10.0D * d9;
/*     */           } 
/*     */           
/* 356 */           if (k < d3) {
/*     */             
/* 358 */             double d10 = (d3 - k) / 4.0D;
/* 359 */             d10 = MathHelper.clamp_double(d10, 0.0D, 1.0D);
/* 360 */             d4 = d4 * (1.0D - d10) + -10.0D * d10;
/*     */           } 
/*     */           
/* 363 */           p_73164_1_[i] = d4;
/* 364 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 369 */     return p_73164_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 377 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 385 */     BlockFalling.fallInstantly = true;
/* 386 */     BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
/* 387 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/* 388 */     this.genNetherBridge.generateStructure(this.worldObj, this.hellRNG, chunkcoordintpair);
/*     */     
/* 390 */     for (int i = 0; i < 8; i++)
/*     */     {
/* 392 */       this.field_177472_y.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 395 */     for (int j = 0; j < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1; j++)
/*     */     {
/* 397 */       this.field_177470_t.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 400 */     for (int k = 0; k < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1); k++)
/*     */     {
/* 402 */       this.field_177469_u.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 405 */     for (int l = 0; l < 10; l++)
/*     */     {
/* 407 */       this.field_177468_v.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 410 */     if (this.hellRNG.nextBoolean())
/*     */     {
/* 412 */       this.field_177471_z.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 415 */     if (this.hellRNG.nextBoolean())
/*     */     {
/* 417 */       this.field_177465_A.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 420 */     for (int i1 = 0; i1 < 16; i1++)
/*     */     {
/* 422 */       this.field_177467_w.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
/*     */     }
/*     */     
/* 425 */     for (int j1 = 0; j1 < 16; j1++)
/*     */     {
/* 427 */       this.field_177473_x.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
/*     */     }
/*     */     
/* 430 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 435 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 444 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 460 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 468 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 476 */     return "HellRandomLevelSource";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 481 */     if (creatureType == EnumCreatureType.MONSTER) {
/*     */       
/* 483 */       if (this.genNetherBridge.func_175795_b(pos))
/*     */       {
/* 485 */         return this.genNetherBridge.getSpawnList();
/*     */       }
/*     */       
/* 488 */       if (this.genNetherBridge.isPositionInStructure(this.worldObj, pos) && this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.nether_brick)
/*     */       {
/* 490 */         return this.genNetherBridge.getSpawnList();
/*     */       }
/*     */     } 
/*     */     
/* 494 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
/* 495 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 500 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 505 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 510 */     this.genNetherBridge.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 515 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\ChunkProviderHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */