/*     */ package net.minecraft.world.biome;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/*     */ import net.minecraft.world.gen.feature.WorldGenSwamp;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public abstract class BiomeGenBase {
/*  32 */   private static final Logger logger = LogManager.getLogger();
/*  33 */   protected static final Height height_Default = new Height(0.1F, 0.2F);
/*  34 */   protected static final Height height_ShallowWaters = new Height(-0.5F, 0.0F);
/*  35 */   protected static final Height height_Oceans = new Height(-1.0F, 0.1F);
/*  36 */   protected static final Height height_DeepOceans = new Height(-1.8F, 0.1F);
/*  37 */   protected static final Height height_LowPlains = new Height(0.125F, 0.05F);
/*  38 */   protected static final Height height_MidPlains = new Height(0.2F, 0.2F);
/*  39 */   protected static final Height height_LowHills = new Height(0.45F, 0.3F);
/*  40 */   protected static final Height height_HighPlateaus = new Height(1.5F, 0.025F);
/*  41 */   protected static final Height height_MidHills = new Height(1.0F, 0.5F);
/*  42 */   protected static final Height height_Shores = new Height(0.0F, 0.025F);
/*  43 */   protected static final Height height_RockyWaters = new Height(0.1F, 0.8F);
/*  44 */   protected static final Height height_LowIslands = new Height(0.2F, 0.3F);
/*  45 */   protected static final Height height_PartiallySubmerged = new Height(-0.2F, 0.1F);
/*     */ 
/*     */   
/*  48 */   private static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
/*  49 */   public static final Set<BiomeGenBase> explorationBiomesList = Sets.newHashSet();
/*  50 */   public static final Map<String, BiomeGenBase> BIOME_ID_MAP = Maps.newHashMap();
/*  51 */   public static final BiomeGenBase ocean = (new BiomeGenOcean(0)).setColor(112).setBiomeName("Ocean").setHeight(height_Oceans);
/*  52 */   public static final BiomeGenBase plains = (new BiomeGenPlains(1)).setColor(9286496).setBiomeName("Plains");
/*  53 */   public static final BiomeGenBase desert = (new BiomeGenDesert(2)).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowPlains);
/*  54 */   public static final BiomeGenBase extremeHills = (new BiomeGenHills(3, false)).setColor(6316128).setBiomeName("Extreme Hills").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
/*  55 */   public static final BiomeGenBase forest = (new BiomeGenForest(4, 0)).setColor(353825).setBiomeName("Forest");
/*  56 */   public static final BiomeGenBase taiga = (new BiomeGenTaiga(5, 0)).setColor(747097).setBiomeName("Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_MidPlains);
/*  57 */   public static final BiomeGenBase swampland = (new BiomeGenSwamp(6)).setColor(522674).setBiomeName("Swampland").setFillerBlockMetadata(9154376).setHeight(height_PartiallySubmerged).setTemperatureRainfall(0.8F, 0.9F);
/*  58 */   public static final BiomeGenBase river = (new BiomeGenRiver(7)).setColor(255).setBiomeName("River").setHeight(height_ShallowWaters);
/*  59 */   public static final BiomeGenBase hell = (new BiomeGenHell(8)).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
/*     */ 
/*     */   
/*  62 */   public static final BiomeGenBase sky = (new BiomeGenEnd(9)).setColor(8421631).setBiomeName("The End").setDisableRain();
/*  63 */   public static final BiomeGenBase frozenOcean = (new BiomeGenOcean(10)).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setHeight(height_Oceans).setTemperatureRainfall(0.0F, 0.5F);
/*  64 */   public static final BiomeGenBase frozenRiver = (new BiomeGenRiver(11)).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setHeight(height_ShallowWaters).setTemperatureRainfall(0.0F, 0.5F);
/*  65 */   public static final BiomeGenBase icePlains = (new BiomeGenSnow(12, false)).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F).setHeight(height_LowPlains);
/*  66 */   public static final BiomeGenBase iceMountains = (new BiomeGenSnow(13, false)).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setHeight(height_LowHills).setTemperatureRainfall(0.0F, 0.5F);
/*  67 */   public static final BiomeGenBase mushroomIsland = (new BiomeGenMushroomIsland(14)).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_LowIslands);
/*  68 */   public static final BiomeGenBase mushroomIslandShore = (new BiomeGenMushroomIsland(15)).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_Shores);
/*     */ 
/*     */   
/*  71 */   public static final BiomeGenBase beach = (new BiomeGenBeach(16)).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8F, 0.4F).setHeight(height_Shores);
/*     */ 
/*     */   
/*  74 */   public static final BiomeGenBase desertHills = (new BiomeGenDesert(17)).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowHills);
/*     */ 
/*     */   
/*  77 */   public static final BiomeGenBase forestHills = (new BiomeGenForest(18, 0)).setColor(2250012).setBiomeName("ForestHills").setHeight(height_LowHills);
/*     */ 
/*     */   
/*  80 */   public static final BiomeGenBase taigaHills = (new BiomeGenTaiga(19, 0)).setColor(1456435).setBiomeName("TaigaHills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_LowHills);
/*     */ 
/*     */   
/*  83 */   public static final BiomeGenBase extremeHillsEdge = (new BiomeGenHills(20, true)).setColor(7501978).setBiomeName("Extreme Hills Edge").setHeight(height_MidHills.attenuate()).setTemperatureRainfall(0.2F, 0.3F);
/*     */ 
/*     */   
/*  86 */   public static final BiomeGenBase jungle = (new BiomeGenJungle(21, false)).setColor(5470985).setBiomeName("Jungle").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F);
/*  87 */   public static final BiomeGenBase jungleHills = (new BiomeGenJungle(22, false)).setColor(2900485).setBiomeName("JungleHills").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F).setHeight(height_LowHills);
/*  88 */   public static final BiomeGenBase jungleEdge = (new BiomeGenJungle(23, true)).setColor(6458135).setBiomeName("JungleEdge").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.8F);
/*  89 */   public static final BiomeGenBase deepOcean = (new BiomeGenOcean(24)).setColor(48).setBiomeName("Deep Ocean").setHeight(height_DeepOceans);
/*  90 */   public static final BiomeGenBase stoneBeach = (new BiomeGenStoneBeach(25)).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2F, 0.3F).setHeight(height_RockyWaters);
/*  91 */   public static final BiomeGenBase coldBeach = (new BiomeGenBeach(26)).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05F, 0.3F).setHeight(height_Shores).setEnableSnow();
/*  92 */   public static final BiomeGenBase birchForest = (new BiomeGenForest(27, 2)).setBiomeName("Birch Forest").setColor(3175492);
/*  93 */   public static final BiomeGenBase birchForestHills = (new BiomeGenForest(28, 2)).setBiomeName("Birch Forest Hills").setColor(2055986).setHeight(height_LowHills);
/*  94 */   public static final BiomeGenBase roofedForest = (new BiomeGenForest(29, 3)).setColor(4215066).setBiomeName("Roofed Forest");
/*  95 */   public static final BiomeGenBase coldTaiga = (new BiomeGenTaiga(30, 0)).setColor(3233098).setBiomeName("Cold Taiga").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_MidPlains).func_150563_c(16777215);
/*  96 */   public static final BiomeGenBase coldTaigaHills = (new BiomeGenTaiga(31, 0)).setColor(2375478).setBiomeName("Cold Taiga Hills").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_LowHills).func_150563_c(16777215);
/*  97 */   public static final BiomeGenBase megaTaiga = (new BiomeGenTaiga(32, 1)).setColor(5858897).setBiomeName("Mega Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_MidPlains);
/*  98 */   public static final BiomeGenBase megaTaigaHills = (new BiomeGenTaiga(33, 1)).setColor(4542270).setBiomeName("Mega Taiga Hills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_LowHills);
/*  99 */   public static final BiomeGenBase extremeHillsPlus = (new BiomeGenHills(34, true)).setColor(5271632).setBiomeName("Extreme Hills+").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
/* 100 */   public static final BiomeGenBase savanna = (new BiomeGenSavanna(35)).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2F, 0.0F).setDisableRain().setHeight(height_LowPlains);
/* 101 */   public static final BiomeGenBase savannaPlateau = (new BiomeGenSavanna(36)).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0F, 0.0F).setDisableRain().setHeight(height_HighPlateaus);
/* 102 */   public static final BiomeGenBase mesa = (new BiomeGenMesa(37, false, false)).setColor(14238997).setBiomeName("Mesa");
/* 103 */   public static final BiomeGenBase mesaPlateau_F = (new BiomeGenMesa(38, false, true)).setColor(11573093).setBiomeName("Mesa Plateau F").setHeight(height_HighPlateaus);
/* 104 */   public static final BiomeGenBase mesaPlateau = (new BiomeGenMesa(39, false, false)).setColor(13274213).setBiomeName("Mesa Plateau").setHeight(height_HighPlateaus);
/* 105 */   public static final BiomeGenBase field_180279_ad = ocean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public IBlockState topBlock = Blocks.grass.getDefaultState();
/*     */ 
/*     */   
/* 117 */   public IBlockState fillerBlock = Blocks.dirt.getDefaultState();
/* 118 */   public int fillerBlockMetadata = 5169201;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase(int id) {
/* 164 */     this.minHeight = height_Default.rootHeight;
/* 165 */     this.maxHeight = height_Default.variation;
/* 166 */     this.temperature = 0.5F;
/* 167 */     this.rainfall = 0.5F;
/* 168 */     this.waterColorMultiplier = 16777215;
/* 169 */     this.spawnableMonsterList = Lists.newArrayList();
/* 170 */     this.spawnableCreatureList = Lists.newArrayList();
/* 171 */     this.spawnableWaterCreatureList = Lists.newArrayList();
/* 172 */     this.spawnableCaveCreatureList = Lists.newArrayList();
/* 173 */     this.enableRain = true;
/* 174 */     this.worldGeneratorTrees = new WorldGenTrees(false);
/* 175 */     this.worldGeneratorBigTree = new WorldGenBigTree(false);
/* 176 */     this.worldGeneratorSwamp = new WorldGenSwamp();
/* 177 */     this.biomeID = id;
/* 178 */     biomeList[id] = this;
/* 179 */     this.theBiomeDecorator = createBiomeDecorator();
/* 180 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntitySheep.class, 12, 4, 4));
/* 181 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityRabbit.class, 10, 3, 3));
/* 182 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityPig.class, 10, 4, 4));
/* 183 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityChicken.class, 10, 4, 4));
/* 184 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityCow.class, 8, 4, 4));
/* 185 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySpider.class, 100, 4, 4));
/* 186 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityZombie.class, 100, 4, 4));
/* 187 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySkeleton.class, 100, 4, 4));
/* 188 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityCreeper.class, 100, 4, 4));
/* 189 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySlime.class, 100, 4, 4));
/* 190 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityEnderman.class, 10, 1, 4));
/* 191 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityWitch.class, 5, 1, 1));
/* 192 */     this.spawnableWaterCreatureList.add(new SpawnListEntry((Class)EntitySquid.class, 10, 4, 4));
/* 193 */     this.spawnableCaveCreatureList.add(new SpawnListEntry((Class)EntityBat.class, 10, 8, 8));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeDecorator createBiomeDecorator() {
/* 201 */     return new BiomeDecorator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setTemperatureRainfall(float temperatureIn, float rainfallIn) {
/* 209 */     if (temperatureIn > 0.1F && temperatureIn < 0.2F)
/*     */     {
/* 211 */       throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
/*     */     }
/*     */ 
/*     */     
/* 215 */     this.temperature = temperatureIn;
/* 216 */     this.rainfall = rainfallIn;
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BiomeGenBase setHeight(Height heights) {
/* 223 */     this.minHeight = heights.rootHeight;
/* 224 */     this.maxHeight = heights.variation;
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setDisableRain() {
/* 233 */     this.enableRain = false;
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 239 */     return (rand.nextInt(10) == 0) ? (WorldGenAbstractTree)this.worldGeneratorBigTree : (WorldGenAbstractTree)this.worldGeneratorTrees;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenerator getRandomWorldGenForGrass(Random rand) {
/* 247 */     return (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 252 */     return (rand.nextInt(3) > 0) ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setEnableSnow() {
/* 260 */     this.enableSnow = true;
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setBiomeName(String name) {
/* 266 */     this.biomeName = name;
/* 267 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setFillerBlockMetadata(int meta) {
/* 272 */     this.fillerBlockMetadata = meta;
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setColor(int colorIn) {
/* 278 */     func_150557_a(colorIn, false);
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase func_150563_c(int p_150563_1_) {
/* 284 */     this.field_150609_ah = p_150563_1_;
/* 285 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase func_150557_a(int colorIn, boolean p_150557_2_) {
/* 290 */     this.color = colorIn;
/*     */     
/* 292 */     if (p_150557_2_) {
/*     */       
/* 294 */       this.field_150609_ah = (colorIn & 0xFEFEFE) >> 1;
/*     */     }
/*     */     else {
/*     */       
/* 298 */       this.field_150609_ah = colorIn;
/*     */     } 
/*     */     
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkyColorByTemp(float p_76731_1_) {
/* 309 */     p_76731_1_ /= 3.0F;
/* 310 */     p_76731_1_ = MathHelper.clamp_float(p_76731_1_, -1.0F, 1.0F);
/* 311 */     return MathHelper.hsvToRGB(0.62222224F - p_76731_1_ * 0.05F, 0.5F + p_76731_1_ * 0.1F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
/* 316 */     switch (creatureType) {
/*     */       
/*     */       case MONSTER:
/* 319 */         return this.spawnableMonsterList;
/*     */       
/*     */       case CREATURE:
/* 322 */         return this.spawnableCreatureList;
/*     */       
/*     */       case WATER_CREATURE:
/* 325 */         return this.spawnableWaterCreatureList;
/*     */       
/*     */       case AMBIENT:
/* 328 */         return this.spawnableCaveCreatureList;
/*     */     } 
/*     */     
/* 331 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getEnableSnow() {
/* 340 */     return this.enableSnow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRain() {
/* 348 */     return this.enableSnow ? false : this.enableRain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHighHumidity() {
/* 356 */     return (this.rainfall > 0.85F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpawningChance() {
/* 364 */     return 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getIntRainfall() {
/* 372 */     return (int)(this.rainfall * 65536.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getFloatRainfall() {
/* 380 */     return this.rainfall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getFloatTemperature(BlockPos pos) {
/* 388 */     if (pos.getY() > 64) {
/*     */       
/* 390 */       float f = (float)(temperatureNoise.func_151601_a(pos.getX() / 8.0D, pos.getZ() / 8.0D) * 4.0D);
/* 391 */       return this.temperature - (f + pos.getY() - 64.0F) * 0.05F / 30.0F;
/*     */     } 
/*     */ 
/*     */     
/* 395 */     return this.temperature;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 401 */     this.theBiomeDecorator.decorate(worldIn, rand, this, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/* 406 */     double d0 = MathHelper.clamp_float(getFloatTemperature(pos), 0.0F, 1.0F);
/* 407 */     double d1 = MathHelper.clamp_float(this.rainfall, 0.0F, 1.0F);
/* 408 */     return ColorizerGrass.getGrassColor(d0, d1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFoliageColorAtPos(BlockPos pos) {
/* 413 */     double d0 = MathHelper.clamp_float(getFloatTemperature(pos), 0.0F, 1.0F);
/* 414 */     double d1 = MathHelper.clamp_float(this.rainfall, 0.0F, 1.0F);
/* 415 */     return ColorizerFoliage.getFoliageColor(d0, d1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSnowyBiome() {
/* 420 */     return this.enableSnow;
/*     */   }
/*     */ 
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 425 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*     */   }
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
/*     */   public final void generateBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 440 */     int i = worldIn.getSeaLevel();
/* 441 */     IBlockState iblockstate = this.topBlock;
/* 442 */     IBlockState iblockstate1 = this.fillerBlock;
/* 443 */     int j = -1;
/* 444 */     int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
/* 445 */     int l = x & 0xF;
/* 446 */     int i1 = z & 0xF;
/* 447 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 449 */     for (int j1 = 255; j1 >= 0; j1--) {
/*     */       
/* 451 */       if (j1 <= rand.nextInt(5)) {
/*     */         
/* 453 */         chunkPrimerIn.setBlockState(i1, j1, l, Blocks.bedrock.getDefaultState());
/*     */       }
/*     */       else {
/*     */         
/* 457 */         IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
/*     */         
/* 459 */         if (iblockstate2.getBlock().getMaterial() == Material.air) {
/*     */           
/* 461 */           j = -1;
/*     */         }
/* 463 */         else if (iblockstate2.getBlock() == Blocks.stone) {
/*     */           
/* 465 */           if (j == -1) {
/*     */             
/* 467 */             if (k <= 0) {
/*     */               
/* 469 */               iblockstate = null;
/* 470 */               iblockstate1 = Blocks.stone.getDefaultState();
/*     */             }
/* 472 */             else if (j1 >= i - 4 && j1 <= i + 1) {
/*     */               
/* 474 */               iblockstate = this.topBlock;
/* 475 */               iblockstate1 = this.fillerBlock;
/*     */             } 
/*     */             
/* 478 */             if (j1 < i && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air))
/*     */             {
/* 480 */               if (getFloatTemperature((BlockPos)blockpos$mutableblockpos.set(x, j1, z)) < 0.15F) {
/*     */                 
/* 482 */                 iblockstate = Blocks.ice.getDefaultState();
/*     */               }
/*     */               else {
/*     */                 
/* 486 */                 iblockstate = Blocks.water.getDefaultState();
/*     */               } 
/*     */             }
/*     */             
/* 490 */             j = k;
/*     */             
/* 492 */             if (j1 >= i - 1)
/*     */             {
/* 494 */               chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
/*     */             }
/* 496 */             else if (j1 < i - 7 - k)
/*     */             {
/* 498 */               iblockstate = null;
/* 499 */               iblockstate1 = Blocks.stone.getDefaultState();
/* 500 */               chunkPrimerIn.setBlockState(i1, j1, l, Blocks.gravel.getDefaultState());
/*     */             }
/*     */             else
/*     */             {
/* 504 */               chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
/*     */             }
/*     */           
/* 507 */           } else if (j > 0) {
/*     */             
/* 509 */             j--;
/* 510 */             chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
/*     */             
/* 512 */             if (j == 0 && iblockstate1.getBlock() == Blocks.sand) {
/*     */               
/* 514 */               j = rand.nextInt(4) + Math.max(0, j1 - 63);
/* 515 */               iblockstate1 = (iblockstate1.getValue((IProperty)BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState();
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
/*     */   protected BiomeGenBase createMutation() {
/* 529 */     return createMutatedBiome(this.biomeID + 128);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 534 */     return new BiomeGenMutated(p_180277_1_, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends BiomeGenBase> getBiomeClass() {
/* 539 */     return (Class)getClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqualTo(BiomeGenBase biome) {
/* 547 */     return (biome == this) ? true : ((biome == null) ? false : ((getBiomeClass() == biome.getBiomeClass())));
/*     */   }
/*     */ 
/*     */   
/*     */   public TempCategory getTempCategory() {
/* 552 */     return (this.temperature < 0.2D) ? TempCategory.COLD : ((this.temperature < 1.0D) ? TempCategory.MEDIUM : TempCategory.WARM);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BiomeGenBase[] getBiomeGenArray() {
/* 557 */     return biomeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BiomeGenBase getBiome(int id) {
/* 565 */     return getBiomeFromBiomeList(id, (BiomeGenBase)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BiomeGenBase getBiomeFromBiomeList(int biomeId, BiomeGenBase biome) {
/* 570 */     if (biomeId >= 0 && biomeId <= biomeList.length) {
/*     */       
/* 572 */       BiomeGenBase biomegenbase = biomeList[biomeId];
/* 573 */       return (biomegenbase == null) ? biome : biomegenbase;
/*     */     } 
/*     */ 
/*     */     
/* 577 */     logger.warn("Biome ID is out of bounds: " + biomeId + ", defaulting to 0 (Ocean)");
/* 578 */     return ocean;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 584 */     plains.createMutation();
/* 585 */     desert.createMutation();
/* 586 */     forest.createMutation();
/* 587 */     taiga.createMutation();
/* 588 */     swampland.createMutation();
/* 589 */     icePlains.createMutation();
/* 590 */     jungle.createMutation();
/* 591 */     jungleEdge.createMutation();
/* 592 */     coldTaiga.createMutation();
/* 593 */     savanna.createMutation();
/* 594 */     savannaPlateau.createMutation();
/* 595 */     mesa.createMutation();
/* 596 */     mesaPlateau_F.createMutation();
/* 597 */     mesaPlateau.createMutation();
/* 598 */     birchForest.createMutation();
/* 599 */     birchForestHills.createMutation();
/* 600 */     roofedForest.createMutation();
/* 601 */     megaTaiga.createMutation();
/* 602 */     extremeHills.createMutation();
/* 603 */     extremeHillsPlus.createMutation();
/* 604 */     megaTaiga.createMutatedBiome(megaTaigaHills.biomeID + 128).setBiomeName("Redwood Taiga Hills M");
/*     */     
/* 606 */     for (BiomeGenBase biomegenbase : biomeList) {
/*     */       
/* 608 */       if (biomegenbase != null) {
/*     */         
/* 610 */         if (BIOME_ID_MAP.containsKey(biomegenbase.biomeName))
/*     */         {
/* 612 */           throw new Error("Biome \"" + biomegenbase.biomeName + "\" is defined as both ID " + ((BiomeGenBase)BIOME_ID_MAP.get(biomegenbase.biomeName)).biomeID + " and " + biomegenbase.biomeID);
/*     */         }
/*     */         
/* 615 */         BIOME_ID_MAP.put(biomegenbase.biomeName, biomegenbase);
/*     */         
/* 617 */         if (biomegenbase.biomeID < 128)
/*     */         {
/* 619 */           explorationBiomesList.add(biomegenbase);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 624 */     explorationBiomesList.remove(hell);
/* 625 */     explorationBiomesList.remove(sky);
/* 626 */     explorationBiomesList.remove(frozenOcean);
/* 627 */     explorationBiomesList.remove(extremeHillsEdge);
/* 628 */   } protected static final NoiseGeneratorPerlin temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), 1);
/* 629 */   protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), 1);
/* 630 */   protected static final WorldGenDoublePlant DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant(); public String biomeName; public int color; public int field_150609_ah; public float minHeight; public float maxHeight; public float temperature; public float rainfall; public int waterColorMultiplier; public BiomeDecorator theBiomeDecorator; protected List<SpawnListEntry> spawnableMonsterList; protected List<SpawnListEntry> spawnableCreatureList; protected List<SpawnListEntry> spawnableWaterCreatureList; protected List<SpawnListEntry> spawnableCaveCreatureList; protected boolean enableSnow; protected boolean enableRain;
/*     */   public final int biomeID;
/*     */   protected WorldGenTrees worldGeneratorTrees;
/*     */   protected WorldGenBigTree worldGeneratorBigTree;
/*     */   protected WorldGenSwamp worldGeneratorSwamp;
/*     */   
/*     */   public static class Height { public float rootHeight;
/*     */     public float variation;
/*     */     
/*     */     public Height(float rootHeightIn, float variationIn) {
/* 640 */       this.rootHeight = rootHeightIn;
/* 641 */       this.variation = variationIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public Height attenuate() {
/* 646 */       return new Height(this.rootHeight * 0.8F, this.variation * 0.6F);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class SpawnListEntry
/*     */     extends WeightedRandom.Item
/*     */   {
/*     */     public Class<? extends EntityLiving> entityClass;
/*     */     public int minGroupCount;
/*     */     public int maxGroupCount;
/*     */     
/*     */     public SpawnListEntry(Class<? extends EntityLiving> entityclassIn, int weight, int groupCountMin, int groupCountMax) {
/* 658 */       super(weight);
/* 659 */       this.entityClass = entityclassIn;
/* 660 */       this.minGroupCount = groupCountMin;
/* 661 */       this.maxGroupCount = groupCountMax;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 666 */       return this.entityClass.getSimpleName() + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum TempCategory
/*     */   {
/* 672 */     OCEAN,
/* 673 */     COLD,
/* 674 */     MEDIUM,
/* 675 */     WARM;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */