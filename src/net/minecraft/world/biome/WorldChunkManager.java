/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.gen.layer.GenLayer;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldChunkManager
/*     */ {
/*     */   private GenLayer genBiomes;
/*     */   private GenLayer biomeIndexLayer;
/*     */   private final BiomeCache biomeCache;
/*     */   private final List<BiomeGenBase> biomesToSpawnIn;
/*     */   private String generatorOptions;
/*     */   
/*     */   protected WorldChunkManager() {
/*  30 */     this.biomeCache = new BiomeCache(this);
/*  31 */     this.generatorOptions = "";
/*  32 */     this.biomesToSpawnIn = Lists.newArrayList();
/*  33 */     this.biomesToSpawnIn.add(BiomeGenBase.forest);
/*  34 */     this.biomesToSpawnIn.add(BiomeGenBase.plains);
/*  35 */     this.biomesToSpawnIn.add(BiomeGenBase.taiga);
/*  36 */     this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
/*  37 */     this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
/*  38 */     this.biomesToSpawnIn.add(BiomeGenBase.jungle);
/*  39 */     this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldChunkManager(long seed, WorldType worldTypeIn, String options) {
/*  44 */     this();
/*  45 */     this.generatorOptions = options;
/*  46 */     GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, worldTypeIn, options);
/*  47 */     this.genBiomes = agenlayer[0];
/*  48 */     this.biomeIndexLayer = agenlayer[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldChunkManager(World worldIn) {
/*  53 */     this(worldIn.getSeed(), worldIn.getWorldInfo().getTerrainType(), worldIn.getWorldInfo().getGeneratorOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase> getBiomesToSpawnIn() {
/*  58 */     return this.biomesToSpawnIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiomeGenerator(BlockPos pos) {
/*  66 */     return getBiomeGenerator(pos, (BiomeGenBase)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiomeGenerator(BlockPos pos, BiomeGenBase biomeGenBaseIn) {
/*  71 */     return this.biomeCache.func_180284_a(pos.getX(), pos.getZ(), biomeGenBaseIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
/*  79 */     IntCache.resetIntCache();
/*     */     
/*  81 */     if (listToReuse == null || listToReuse.length < width * length)
/*     */     {
/*  83 */       listToReuse = new float[width * length];
/*     */     }
/*     */     
/*  86 */     int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
/*     */     
/*  88 */     for (int i = 0; i < width * length; i++) {
/*     */ 
/*     */       
/*     */       try {
/*  92 */         float f = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad).getIntRainfall() / 65536.0F;
/*     */         
/*  94 */         if (f > 1.0F)
/*     */         {
/*  96 */           f = 1.0F;
/*     */         }
/*     */         
/*  99 */         listToReuse[i] = f;
/*     */       }
/* 101 */       catch (Throwable throwable) {
/*     */         
/* 103 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 104 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
/* 105 */         crashreportcategory.addCrashSection("biome id", Integer.valueOf(i));
/* 106 */         crashreportcategory.addCrashSection("downfalls[] size", Integer.valueOf(listToReuse.length));
/* 107 */         crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/* 108 */         crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 109 */         crashreportcategory.addCrashSection("w", Integer.valueOf(width));
/* 110 */         crashreportcategory.addCrashSection("h", Integer.valueOf(length));
/* 111 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return listToReuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTemperatureAtHeight(float p_76939_1_, int p_76939_2_) {
/* 123 */     return p_76939_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int height) {
/* 131 */     IntCache.resetIntCache();
/*     */     
/* 133 */     if (biomes == null || biomes.length < width * height)
/*     */     {
/* 135 */       biomes = new BiomeGenBase[width * height];
/*     */     }
/*     */     
/* 138 */     int[] aint = this.genBiomes.getInts(x, z, width, height);
/*     */ 
/*     */     
/*     */     try {
/* 142 */       for (int i = 0; i < width * height; i++)
/*     */       {
/* 144 */         biomes[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
/*     */       }
/*     */       
/* 147 */       return biomes;
/*     */     }
/* 149 */     catch (Throwable throwable) {
/*     */       
/* 151 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 152 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
/* 153 */       crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
/* 154 */       crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/* 155 */       crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 156 */       crashreportcategory.addCrashSection("w", Integer.valueOf(width));
/* 157 */       crashreportcategory.addCrashSection("h", Integer.valueOf(height));
/* 158 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
/* 168 */     return getBiomeGenAt(oldBiomeList, x, z, width, depth, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
/* 177 */     IntCache.resetIntCache();
/*     */     
/* 179 */     if (listToReuse == null || listToReuse.length < width * length)
/*     */     {
/* 181 */       listToReuse = new BiomeGenBase[width * length];
/*     */     }
/*     */     
/* 184 */     if (cacheFlag && width == 16 && length == 16 && (x & 0xF) == 0 && (z & 0xF) == 0) {
/*     */       
/* 186 */       BiomeGenBase[] abiomegenbase = this.biomeCache.getCachedBiomes(x, z);
/* 187 */       System.arraycopy(abiomegenbase, 0, listToReuse, 0, width * length);
/* 188 */       return listToReuse;
/*     */     } 
/*     */ 
/*     */     
/* 192 */     int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
/*     */     
/* 194 */     for (int i = 0; i < width * length; i++)
/*     */     {
/* 196 */       listToReuse[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
/*     */     }
/*     */     
/* 199 */     return listToReuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List<BiomeGenBase> p_76940_4_) {
/* 208 */     IntCache.resetIntCache();
/* 209 */     int i = p_76940_1_ - p_76940_3_ >> 2;
/* 210 */     int j = p_76940_2_ - p_76940_3_ >> 2;
/* 211 */     int k = p_76940_1_ + p_76940_3_ >> 2;
/* 212 */     int l = p_76940_2_ + p_76940_3_ >> 2;
/* 213 */     int i1 = k - i + 1;
/* 214 */     int j1 = l - j + 1;
/* 215 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/*     */ 
/*     */     
/*     */     try {
/* 219 */       for (int k1 = 0; k1 < i1 * j1; k1++) {
/*     */         
/* 221 */         BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k1]);
/*     */         
/* 223 */         if (!p_76940_4_.contains(biomegenbase))
/*     */         {
/* 225 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 229 */       return true;
/*     */     }
/* 231 */     catch (Throwable throwable) {
/*     */       
/* 233 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 234 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
/* 235 */       crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
/* 236 */       crashreportcategory.addCrashSection("x", Integer.valueOf(p_76940_1_));
/* 237 */       crashreportcategory.addCrashSection("z", Integer.valueOf(p_76940_2_));
/* 238 */       crashreportcategory.addCrashSection("radius", Integer.valueOf(p_76940_3_));
/* 239 */       crashreportcategory.addCrashSection("allowed", p_76940_4_);
/* 240 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos findBiomePosition(int x, int z, int range, List<BiomeGenBase> biomes, Random random) {
/* 246 */     IntCache.resetIntCache();
/* 247 */     int i = x - range >> 2;
/* 248 */     int j = z - range >> 2;
/* 249 */     int k = x + range >> 2;
/* 250 */     int l = z + range >> 2;
/* 251 */     int i1 = k - i + 1;
/* 252 */     int j1 = l - j + 1;
/* 253 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/* 254 */     BlockPos blockpos = null;
/* 255 */     int k1 = 0;
/*     */     
/* 257 */     for (int l1 = 0; l1 < i1 * j1; l1++) {
/*     */       
/* 259 */       int i2 = i + l1 % i1 << 2;
/* 260 */       int j2 = j + l1 / i1 << 2;
/* 261 */       BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[l1]);
/*     */       
/* 263 */       if (biomes.contains(biomegenbase) && (blockpos == null || random.nextInt(k1 + 1) == 0)) {
/*     */         
/* 265 */         blockpos = new BlockPos(i2, 0, j2);
/* 266 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 270 */     return blockpos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupCache() {
/* 278 */     this.biomeCache.cleanupCache();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\WorldChunkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */