/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.gen.ChunkProviderSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GenLayer
/*     */ {
/*     */   private long worldGenSeed;
/*     */   protected GenLayer parent;
/*     */   private long chunkSeed;
/*     */   protected long baseSeed;
/*     */   
/*     */   public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType p_180781_2_, String p_180781_3_) {
/*  31 */     GenLayer genlayer = new GenLayerIsland(1L);
/*  32 */     genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
/*  33 */     GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
/*  34 */     GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
/*  35 */     GenLayerAddIsland genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
/*  36 */     genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
/*  37 */     genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
/*  38 */     GenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
/*  39 */     GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
/*  40 */     GenLayerAddIsland genlayeraddisland2 = new GenLayerAddIsland(3L, genlayeraddsnow);
/*  41 */     GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland2, GenLayerEdge.Mode.COOL_WARM);
/*  42 */     genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
/*  43 */     genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
/*  44 */     GenLayerZoom genlayerzoom1 = new GenLayerZoom(2002L, genlayeredge);
/*  45 */     genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
/*  46 */     GenLayerAddIsland genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
/*  47 */     GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland3);
/*  48 */     GenLayerDeepOcean genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);
/*  49 */     GenLayer genlayer4 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
/*  50 */     ChunkProviderSettings chunkprovidersettings = null;
/*  51 */     int i = 4;
/*  52 */     int j = i;
/*     */     
/*  54 */     if (p_180781_2_ == WorldType.CUSTOMIZED && !p_180781_3_.isEmpty()) {
/*     */       
/*  56 */       chunkprovidersettings = ChunkProviderSettings.Factory.jsonToFactory(p_180781_3_).func_177864_b();
/*  57 */       i = chunkprovidersettings.biomeSize;
/*  58 */       j = chunkprovidersettings.riverSize;
/*     */     } 
/*     */     
/*  61 */     if (p_180781_2_ == WorldType.LARGE_BIOMES)
/*     */     {
/*  63 */       i = 6;
/*     */     }
/*     */     
/*  66 */     GenLayer lvt_8_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
/*  67 */     GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, lvt_8_1_);
/*  68 */     GenLayerBiome lvt_9_1_ = new GenLayerBiome(200L, genlayer4, p_180781_2_, p_180781_3_);
/*  69 */     GenLayer genlayer6 = GenLayerZoom.magnify(1000L, lvt_9_1_, 2);
/*  70 */     GenLayerBiomeEdge genlayerbiomeedge = new GenLayerBiomeEdge(1000L, genlayer6);
/*  71 */     GenLayer lvt_10_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
/*  72 */     GenLayer genlayerhills = new GenLayerHills(1000L, genlayerbiomeedge, lvt_10_1_);
/*  73 */     GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
/*  74 */     genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, j);
/*  75 */     GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer5);
/*  76 */     GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
/*  77 */     genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);
/*     */     
/*  79 */     for (int k = 0; k < i; k++) {
/*     */       
/*  81 */       genlayerhills = new GenLayerZoom((1000 + k), genlayerhills);
/*     */       
/*  83 */       if (k == 0)
/*     */       {
/*  85 */         genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
/*     */       }
/*     */       
/*  88 */       if (k == 1 || i == 1)
/*     */       {
/*  90 */         genlayerhills = new GenLayerShore(1000L, genlayerhills);
/*     */       }
/*     */     } 
/*     */     
/*  94 */     GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
/*  95 */     GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
/*  96 */     GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
/*  97 */     genlayerrivermix.initWorldGenSeed(seed);
/*  98 */     genlayer3.initWorldGenSeed(seed);
/*  99 */     return new GenLayer[] { genlayerrivermix, genlayer3, genlayerrivermix };
/*     */   }
/*     */ 
/*     */   
/*     */   public GenLayer(long p_i2125_1_) {
/* 104 */     this.baseSeed = p_i2125_1_;
/* 105 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 106 */     this.baseSeed += p_i2125_1_;
/* 107 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 108 */     this.baseSeed += p_i2125_1_;
/* 109 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 110 */     this.baseSeed += p_i2125_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWorldGenSeed(long seed) {
/* 119 */     this.worldGenSeed = seed;
/*     */     
/* 121 */     if (this.parent != null)
/*     */     {
/* 123 */       this.parent.initWorldGenSeed(seed);
/*     */     }
/*     */     
/* 126 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 127 */     this.worldGenSeed += this.baseSeed;
/* 128 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 129 */     this.worldGenSeed += this.baseSeed;
/* 130 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 131 */     this.worldGenSeed += this.baseSeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initChunkSeed(long p_75903_1_, long p_75903_3_) {
/* 139 */     this.chunkSeed = this.worldGenSeed;
/* 140 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 141 */     this.chunkSeed += p_75903_1_;
/* 142 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 143 */     this.chunkSeed += p_75903_3_;
/* 144 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 145 */     this.chunkSeed += p_75903_1_;
/* 146 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 147 */     this.chunkSeed += p_75903_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int nextInt(int p_75902_1_) {
/* 155 */     int i = (int)((this.chunkSeed >> 24L) % p_75902_1_);
/*     */     
/* 157 */     if (i < 0)
/*     */     {
/* 159 */       i += p_75902_1_;
/*     */     }
/*     */     
/* 162 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 163 */     this.chunkSeed += this.worldGenSeed;
/* 164 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int[] getInts(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean biomesEqualOrMesaPlateau(int biomeIDA, int biomeIDB) {
/* 175 */     if (biomeIDA == biomeIDB)
/*     */     {
/* 177 */       return true;
/*     */     }
/* 179 */     if (biomeIDA != BiomeGenBase.mesaPlateau_F.biomeID && biomeIDA != BiomeGenBase.mesaPlateau.biomeID) {
/*     */       
/* 181 */       final BiomeGenBase biomegenbase = BiomeGenBase.getBiome(biomeIDA);
/* 182 */       final BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(biomeIDB);
/*     */ 
/*     */       
/*     */       try {
/* 186 */         return (biomegenbase != null && biomegenbase1 != null) ? biomegenbase.isEqualTo(biomegenbase1) : false;
/*     */       }
/* 188 */       catch (Throwable throwable) {
/*     */         
/* 190 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Comparing biomes");
/* 191 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Biomes being compared");
/* 192 */         crashreportcategory.addCrashSection("Biome A ID", Integer.valueOf(biomeIDA));
/* 193 */         crashreportcategory.addCrashSection("Biome B ID", Integer.valueOf(biomeIDB));
/* 194 */         crashreportcategory.addCrashSectionCallable("Biome A", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 197 */                 return String.valueOf(biomegenbase);
/*     */               }
/*     */             });
/* 200 */         crashreportcategory.addCrashSectionCallable("Biome B", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 203 */                 return String.valueOf(biomegenbase1);
/*     */               }
/*     */             });
/* 206 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return (biomeIDB == BiomeGenBase.mesaPlateau_F.biomeID || biomeIDB == BiomeGenBase.mesaPlateau.biomeID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isBiomeOceanic(int p_151618_0_) {
/* 220 */     return (p_151618_0_ == BiomeGenBase.ocean.biomeID || p_151618_0_ == BiomeGenBase.deepOcean.biomeID || p_151618_0_ == BiomeGenBase.frozenOcean.biomeID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int selectRandom(int... p_151619_1_) {
/* 228 */     return p_151619_1_[nextInt(p_151619_1_.length)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_) {
/* 236 */     return (p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_) ? p_151617_2_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_) ? p_151617_1_ : ((p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_) ? p_151617_2_ : ((p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_) ? p_151617_2_ : ((p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_) ? p_151617_3_ : selectRandom(new int[] { p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_ }))))))))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\layer\GenLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */