/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.SpawnerAnimals;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.feature.WorldGenDungeons;
/*     */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*     */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*     */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*     */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*     */ import net.minecraft.world.gen.structure.MapGenVillage;
/*     */ import net.minecraft.world.gen.structure.StructureOceanMonument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderGenerate
/*     */   implements IChunkProvider
/*     */ {
/*     */   private final Random rand;
/*     */   private final NoiseGeneratorOctaves field_147431_j;
/*     */   private final NoiseGeneratorOctaves field_147432_k;
/*     */   private final NoiseGeneratorOctaves field_147429_l;
/*     */   private final NoiseGeneratorPerlin field_147430_m;
/*     */   public NoiseGeneratorOctaves noiseGen5;
/*     */   public NoiseGeneratorOctaves noiseGen6;
/*     */   public NoiseGeneratorOctaves mobSpawnerNoise;
/*     */   private final World worldObj;
/*     */   private final boolean mapFeaturesEnabled;
/*     */   private final WorldType field_177475_o;
/*     */   private final double[] field_147434_q;
/*     */   private final float[] parabolicField;
/*     */   private ChunkProviderSettings settings;
/*  50 */   private Block oceanBlockTmpl = (Block)Blocks.water;
/*  51 */   private double[] stoneNoise = new double[256];
/*  52 */   private final MapGenBase caveGenerator = new MapGenCaves();
/*     */ 
/*     */   
/*  55 */   private final MapGenStronghold strongholdGenerator = new MapGenStronghold();
/*     */ 
/*     */   
/*  58 */   private final MapGenVillage villageGenerator = new MapGenVillage();
/*     */ 
/*     */   
/*  61 */   private final MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
/*  62 */   private final MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
/*     */ 
/*     */   
/*  65 */   private final MapGenBase ravineGenerator = new MapGenRavine();
/*  66 */   private final StructureOceanMonument oceanMonumentGenerator = new StructureOceanMonument();
/*     */   
/*     */   private BiomeGenBase[] biomesForGeneration;
/*     */   
/*     */   double[] mainNoiseArray;
/*     */   
/*     */   double[] lowerLimitNoiseArray;
/*     */   double[] upperLimitNoiseArray;
/*     */   double[] depthNoiseArray;
/*     */   
/*     */   public ChunkProviderGenerate(World worldIn, long seed, boolean generateStructures, String structuresJson) {
/*  77 */     this.worldObj = worldIn;
/*  78 */     this.mapFeaturesEnabled = generateStructures;
/*  79 */     this.field_177475_o = worldIn.getWorldInfo().getTerrainType();
/*  80 */     this.rand = new Random(seed);
/*  81 */     this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
/*  82 */     this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
/*  83 */     this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
/*  84 */     this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
/*  85 */     this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
/*  86 */     this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
/*  87 */     this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
/*  88 */     this.field_147434_q = new double[825];
/*  89 */     this.parabolicField = new float[25];
/*     */     
/*  91 */     for (int i = -2; i <= 2; i++) {
/*     */       
/*  93 */       for (int j = -2; j <= 2; j++) {
/*     */         
/*  95 */         float f = 10.0F / MathHelper.sqrt_float((i * i + j * j) + 0.2F);
/*  96 */         this.parabolicField[i + 2 + (j + 2) * 5] = f;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     if (structuresJson != null) {
/*     */       
/* 102 */       this.settings = ChunkProviderSettings.Factory.jsonToFactory(structuresJson).func_177864_b();
/* 103 */       this.oceanBlockTmpl = this.settings.useLavaOceans ? (Block)Blocks.lava : (Block)Blocks.water;
/* 104 */       worldIn.setSeaLevel(this.settings.seaLevel);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
/* 113 */     this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, (x << 2) - 2, (z << 2) - 2, 10, 10);
/* 114 */     func_147423_a(x << 2, 0, z << 2);
/*     */     
/* 116 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 118 */       int j = i * 5;
/* 119 */       int k = (i + 1) * 5;
/*     */       
/* 121 */       for (int l = 0; l < 4; l++) {
/*     */         
/* 123 */         int i1 = (j + l) * 33;
/* 124 */         int j1 = (j + l + 1) * 33;
/* 125 */         int k1 = (k + l) * 33;
/* 126 */         int l1 = (k + l + 1) * 33;
/*     */         
/* 128 */         for (int i2 = 0; i2 < 32; i2++) {
/*     */           
/* 130 */           double d0 = 0.125D;
/* 131 */           double d1 = this.field_147434_q[i1 + i2];
/* 132 */           double d2 = this.field_147434_q[j1 + i2];
/* 133 */           double d3 = this.field_147434_q[k1 + i2];
/* 134 */           double d4 = this.field_147434_q[l1 + i2];
/* 135 */           double d5 = (this.field_147434_q[i1 + i2 + 1] - d1) * d0;
/* 136 */           double d6 = (this.field_147434_q[j1 + i2 + 1] - d2) * d0;
/* 137 */           double d7 = (this.field_147434_q[k1 + i2 + 1] - d3) * d0;
/* 138 */           double d8 = (this.field_147434_q[l1 + i2 + 1] - d4) * d0;
/*     */           
/* 140 */           for (int j2 = 0; j2 < 8; j2++) {
/*     */             
/* 142 */             double d9 = 0.25D;
/* 143 */             double d10 = d1;
/* 144 */             double d11 = d2;
/* 145 */             double d12 = (d3 - d1) * d9;
/* 146 */             double d13 = (d4 - d2) * d9;
/*     */             
/* 148 */             for (int k2 = 0; k2 < 4; k2++) {
/*     */               
/* 150 */               double d14 = 0.25D;
/* 151 */               double d16 = (d11 - d10) * d14;
/* 152 */               double lvt_45_1_ = d10 - d16;
/*     */               
/* 154 */               for (int l2 = 0; l2 < 4; l2++) {
/*     */                 
/* 156 */                 if ((lvt_45_1_ += d16) > 0.0D) {
/*     */                   
/* 158 */                   primer.setBlockState((i << 2) + k2, (i2 << 3) + j2, (l << 2) + l2, Blocks.stone.getDefaultState());
/*     */                 }
/* 160 */                 else if ((i2 << 3) + j2 < this.settings.seaLevel) {
/*     */                   
/* 162 */                   primer.setBlockState((i << 2) + k2, (i2 << 3) + j2, (l << 2) + l2, this.oceanBlockTmpl.getDefaultState());
/*     */                 } 
/*     */               } 
/*     */               
/* 166 */               d10 += d12;
/* 167 */               d11 += d13;
/*     */             } 
/*     */             
/* 170 */             d1 += d5;
/* 171 */             d2 += d6;
/* 172 */             d3 += d7;
/* 173 */             d4 += d8;
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
/*     */   public void replaceBlocksForBiome(int x, int z, ChunkPrimer primer, BiomeGenBase[] biomeGens) {
/* 186 */     double d0 = 0.03125D;
/* 187 */     this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (x << 4), (z << 4), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);
/*     */     
/* 189 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 191 */       for (int j = 0; j < 16; j++) {
/*     */         
/* 193 */         BiomeGenBase biomegenbase = biomeGens[j + (i << 4)];
/* 194 */         biomegenbase.genTerrainBlocks(this.worldObj, this.rand, primer, (x << 4) + i, (z << 4) + j, this.stoneNoise[j + (i << 4)]);
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
/* 205 */     this.rand.setSeed(x * 341873128712L + z * 132897987541L);
/* 206 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 207 */     setBlocksInChunk(x, z, chunkprimer);
/* 208 */     this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x << 4, z << 4, 16, 16);
/* 209 */     replaceBlocksForBiome(x, z, chunkprimer, this.biomesForGeneration);
/*     */     
/* 211 */     if (this.settings.useCaves)
/*     */     {
/* 213 */       this.caveGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 216 */     if (this.settings.useRavines)
/*     */     {
/* 218 */       this.ravineGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 221 */     if (this.settings.useMineShafts && this.mapFeaturesEnabled)
/*     */     {
/* 223 */       this.mineshaftGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 226 */     if (this.settings.useVillages && this.mapFeaturesEnabled)
/*     */     {
/* 228 */       this.villageGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 231 */     if (this.settings.useStrongholds && this.mapFeaturesEnabled)
/*     */     {
/* 233 */       this.strongholdGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 236 */     if (this.settings.useTemples && this.mapFeaturesEnabled)
/*     */     {
/* 238 */       this.scatteredFeatureGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 241 */     if (this.settings.useMonuments && this.mapFeaturesEnabled)
/*     */     {
/* 243 */       this.oceanMonumentGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 246 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 247 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 249 */     for (int i = 0; i < abyte.length; i++)
/*     */     {
/* 251 */       abyte[i] = (byte)(this.biomesForGeneration[i]).biomeID;
/*     */     }
/*     */     
/* 254 */     chunk.generateSkylightMap();
/* 255 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_147423_a(int x, int y, int z) {
/* 260 */     this.depthNoiseArray = this.noiseGen6.generateNoiseOctaves(this.depthNoiseArray, x, z, 5, 5, this.settings.depthNoiseScaleX, this.settings.depthNoiseScaleZ, this.settings.depthNoiseScaleExponent);
/* 261 */     float f = this.settings.coordinateScale;
/* 262 */     float f1 = this.settings.heightScale;
/* 263 */     this.mainNoiseArray = this.field_147429_l.generateNoiseOctaves(this.mainNoiseArray, x, y, z, 5, 33, 5, (f / this.settings.mainNoiseScaleX), (f1 / this.settings.mainNoiseScaleY), (f / this.settings.mainNoiseScaleZ));
/* 264 */     this.lowerLimitNoiseArray = this.field_147431_j.generateNoiseOctaves(this.lowerLimitNoiseArray, x, y, z, 5, 33, 5, f, f1, f);
/* 265 */     this.upperLimitNoiseArray = this.field_147432_k.generateNoiseOctaves(this.upperLimitNoiseArray, x, y, z, 5, 33, 5, f, f1, f);
/* 266 */     z = 0;
/* 267 */     x = 0;
/* 268 */     int i = 0;
/* 269 */     int j = 0;
/*     */     
/* 271 */     for (int k = 0; k < 5; k++) {
/*     */       
/* 273 */       for (int l = 0; l < 5; l++) {
/*     */         
/* 275 */         float f2 = 0.0F;
/* 276 */         float f3 = 0.0F;
/* 277 */         float f4 = 0.0F;
/* 278 */         int i1 = 2;
/* 279 */         BiomeGenBase biomegenbase = this.biomesForGeneration[k + 2 + (l + 2) * 10];
/*     */         
/* 281 */         for (int j1 = -i1; j1 <= i1; j1++) {
/*     */           
/* 283 */           for (int k1 = -i1; k1 <= i1; k1++) {
/*     */             
/* 285 */             BiomeGenBase biomegenbase1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
/* 286 */             float f5 = this.settings.biomeDepthOffSet + biomegenbase1.minHeight * this.settings.biomeDepthWeight;
/* 287 */             float f6 = this.settings.biomeScaleOffset + biomegenbase1.maxHeight * this.settings.biomeScaleWeight;
/*     */             
/* 289 */             if (this.field_177475_o == WorldType.AMPLIFIED && f5 > 0.0F) {
/*     */               
/* 291 */               f5 = 1.0F + f5 * 2.0F;
/* 292 */               f6 = 1.0F + f6 * 4.0F;
/*     */             } 
/*     */             
/* 295 */             float f7 = this.parabolicField[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
/*     */             
/* 297 */             if (biomegenbase1.minHeight > biomegenbase.minHeight)
/*     */             {
/* 299 */               f7 /= 2.0F;
/*     */             }
/*     */             
/* 302 */             f2 += f6 * f7;
/* 303 */             f3 += f5 * f7;
/* 304 */             f4 += f7;
/*     */           } 
/*     */         } 
/*     */         
/* 308 */         f2 /= f4;
/* 309 */         f3 /= f4;
/* 310 */         f2 = f2 * 0.9F + 0.1F;
/* 311 */         f3 = (f3 * 4.0F - 1.0F) / 8.0F;
/* 312 */         double d7 = this.depthNoiseArray[j] / 8000.0D;
/*     */         
/* 314 */         if (d7 < 0.0D)
/*     */         {
/* 316 */           d7 = -d7 * 0.3D;
/*     */         }
/*     */         
/* 319 */         d7 = d7 * 3.0D - 2.0D;
/*     */         
/* 321 */         if (d7 < 0.0D) {
/*     */           
/* 323 */           d7 /= 2.0D;
/*     */           
/* 325 */           if (d7 < -1.0D)
/*     */           {
/* 327 */             d7 = -1.0D;
/*     */           }
/*     */           
/* 330 */           d7 /= 1.4D;
/* 331 */           d7 /= 2.0D;
/*     */         }
/*     */         else {
/*     */           
/* 335 */           if (d7 > 1.0D)
/*     */           {
/* 337 */             d7 = 1.0D;
/*     */           }
/*     */           
/* 340 */           d7 /= 8.0D;
/*     */         } 
/*     */         
/* 343 */         j++;
/* 344 */         double d8 = f3;
/* 345 */         double d9 = f2;
/* 346 */         d8 += d7 * 0.2D;
/* 347 */         d8 = d8 * this.settings.baseSize / 8.0D;
/* 348 */         double d0 = this.settings.baseSize + d8 * 4.0D;
/*     */         
/* 350 */         for (int l1 = 0; l1 < 33; l1++) {
/*     */           
/* 352 */           double d1 = (l1 - d0) * this.settings.stretchY * 128.0D / 256.0D / d9;
/*     */           
/* 354 */           if (d1 < 0.0D)
/*     */           {
/* 356 */             d1 *= 4.0D;
/*     */           }
/*     */           
/* 359 */           double d2 = this.lowerLimitNoiseArray[i] / this.settings.lowerLimitScale;
/* 360 */           double d3 = this.upperLimitNoiseArray[i] / this.settings.upperLimitScale;
/* 361 */           double d4 = (this.mainNoiseArray[i] / 10.0D + 1.0D) / 2.0D;
/* 362 */           double d5 = MathHelper.denormalizeClamp(d2, d3, d4) - d1;
/*     */           
/* 364 */           if (l1 > 29) {
/*     */             
/* 366 */             double d6 = ((l1 - 29) / 3.0F);
/* 367 */             d5 = d5 * (1.0D - d6) + -10.0D * d6;
/*     */           } 
/*     */           
/* 370 */           this.field_147434_q[i] = d5;
/* 371 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 382 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 390 */     BlockFalling.fallInstantly = true;
/* 391 */     int i = x << 4;
/* 392 */     int j = z << 4;
/* 393 */     BlockPos blockpos = new BlockPos(i, 0, j);
/* 394 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16));
/* 395 */     this.rand.setSeed(this.worldObj.getSeed());
/* 396 */     long k = (this.rand.nextLong() / 2L << 1L) + 1L;
/* 397 */     long l = (this.rand.nextLong() / 2L << 1L) + 1L;
/* 398 */     this.rand.setSeed(x * k + z * l ^ this.worldObj.getSeed());
/* 399 */     boolean flag = false;
/* 400 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*     */     
/* 402 */     if (this.settings.useMineShafts && this.mapFeaturesEnabled)
/*     */     {
/* 404 */       this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 407 */     if (this.settings.useVillages && this.mapFeaturesEnabled)
/*     */     {
/* 409 */       flag = this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 412 */     if (this.settings.useStrongholds && this.mapFeaturesEnabled)
/*     */     {
/* 414 */       this.strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 417 */     if (this.settings.useTemples && this.mapFeaturesEnabled)
/*     */     {
/* 419 */       this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 422 */     if (this.settings.useMonuments && this.mapFeaturesEnabled)
/*     */     {
/* 424 */       this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 427 */     if (biomegenbase != BiomeGenBase.desert && biomegenbase != BiomeGenBase.desertHills && this.settings.useWaterLakes && !flag && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
/*     */       
/* 429 */       int i1 = this.rand.nextInt(16) + 8;
/* 430 */       int j1 = this.rand.nextInt(256);
/* 431 */       int k1 = this.rand.nextInt(16) + 8;
/* 432 */       (new WorldGenLakes((Block)Blocks.water)).generate(this.worldObj, this.rand, blockpos.add(i1, j1, k1));
/*     */     } 
/*     */     
/* 435 */     if (!flag && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes) {
/*     */       
/* 437 */       int i2 = this.rand.nextInt(16) + 8;
/* 438 */       int l2 = this.rand.nextInt(this.rand.nextInt(248) + 8);
/* 439 */       int k3 = this.rand.nextInt(16) + 8;
/*     */       
/* 441 */       if (l2 < this.worldObj.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0)
/*     */       {
/* 443 */         (new WorldGenLakes((Block)Blocks.lava)).generate(this.worldObj, this.rand, blockpos.add(i2, l2, k3));
/*     */       }
/*     */     } 
/*     */     
/* 447 */     if (this.settings.useDungeons)
/*     */     {
/* 449 */       for (int j2 = 0; j2 < this.settings.dungeonChance; j2++) {
/*     */         
/* 451 */         int i3 = this.rand.nextInt(16) + 8;
/* 452 */         int l3 = this.rand.nextInt(256);
/* 453 */         int l1 = this.rand.nextInt(16) + 8;
/* 454 */         (new WorldGenDungeons()).generate(this.worldObj, this.rand, blockpos.add(i3, l3, l1));
/*     */       } 
/*     */     }
/*     */     
/* 458 */     biomegenbase.decorate(this.worldObj, this.rand, new BlockPos(i, 0, j));
/* 459 */     SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, i + 8, j + 8, 16, 16, this.rand);
/* 460 */     blockpos = blockpos.add(8, 0, 8);
/*     */     
/* 462 */     for (int k2 = 0; k2 < 16; k2++) {
/*     */       
/* 464 */       for (int j3 = 0; j3 < 16; j3++) {
/*     */         
/* 466 */         BlockPos blockpos1 = this.worldObj.getPrecipitationHeight(blockpos.add(k2, 0, j3));
/* 467 */         BlockPos blockpos2 = blockpos1.down();
/*     */         
/* 469 */         if (this.worldObj.canBlockFreezeWater(blockpos2))
/*     */         {
/* 471 */           this.worldObj.setBlockState(blockpos2, Blocks.ice.getDefaultState(), 2);
/*     */         }
/*     */         
/* 474 */         if (this.worldObj.canSnowAt(blockpos1, true))
/*     */         {
/* 476 */           this.worldObj.setBlockState(blockpos1, Blocks.snow_layer.getDefaultState(), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 481 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 486 */     boolean flag = false;
/*     */     
/* 488 */     if (this.settings.useMonuments && this.mapFeaturesEnabled && chunkIn.getInhabitedTime() < 3600L)
/*     */     {
/* 490 */       flag |= this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, new ChunkCoordIntPair(x, z));
/*     */     }
/*     */     
/* 493 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 502 */     return true;
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
/* 518 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 526 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 534 */     return "RandomLevelSource";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 539 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
/*     */     
/* 541 */     if (this.mapFeaturesEnabled) {
/*     */       
/* 543 */       if (creatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.func_175798_a(pos))
/*     */       {
/* 545 */         return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
/*     */       }
/*     */       
/* 548 */       if (creatureType == EnumCreatureType.MONSTER && this.settings.useMonuments && this.oceanMonumentGenerator.isPositionInStructure(this.worldObj, pos))
/*     */       {
/* 550 */         return this.oceanMonumentGenerator.getScatteredFeatureSpawnList();
/*     */       }
/*     */     } 
/*     */     
/* 554 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 559 */     return ("Stronghold".equals(structureName) && this.strongholdGenerator != null) ? this.strongholdGenerator.getClosestStrongholdPos(worldIn, position) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 564 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 569 */     if (this.settings.useMineShafts && this.mapFeaturesEnabled)
/*     */     {
/* 571 */       this.mineshaftGenerator.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
/*     */     }
/*     */     
/* 574 */     if (this.settings.useVillages && this.mapFeaturesEnabled)
/*     */     {
/* 576 */       this.villageGenerator.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
/*     */     }
/*     */     
/* 579 */     if (this.settings.useStrongholds && this.mapFeaturesEnabled)
/*     */     {
/* 581 */       this.strongholdGenerator.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
/*     */     }
/*     */     
/* 584 */     if (this.settings.useTemples && this.mapFeaturesEnabled)
/*     */     {
/* 586 */       this.scatteredFeatureGenerator.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
/*     */     }
/*     */     
/* 589 */     if (this.settings.useMonuments && this.mapFeaturesEnabled)
/*     */     {
/* 591 */       this.oceanMonumentGenerator.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 597 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\ChunkProviderGenerate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */