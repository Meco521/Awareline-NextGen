/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
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
/*     */ public class ChunkProviderSettings
/*     */ {
/*     */   public final float coordinateScale;
/*     */   public final float heightScale;
/*     */   public final float upperLimitScale;
/*     */   public final float lowerLimitScale;
/*     */   public final float depthNoiseScaleX;
/*     */   public final float depthNoiseScaleZ;
/*     */   public final float depthNoiseScaleExponent;
/*     */   public final float mainNoiseScaleX;
/*     */   public final float mainNoiseScaleY;
/*     */   public final float mainNoiseScaleZ;
/*     */   public final float baseSize;
/*     */   public final float stretchY;
/*     */   public final float biomeDepthWeight;
/*     */   public final float biomeDepthOffSet;
/*     */   public final float biomeScaleWeight;
/*     */   public final float biomeScaleOffset;
/*     */   public final int seaLevel;
/*     */   public final boolean useCaves;
/*     */   public final boolean useDungeons;
/*     */   public final int dungeonChance;
/*     */   public final boolean useStrongholds;
/*     */   public final boolean useVillages;
/*     */   public final boolean useMineShafts;
/*     */   public final boolean useTemples;
/*     */   public final boolean useMonuments;
/*     */   public final boolean useRavines;
/*     */   public final boolean useWaterLakes;
/*     */   public final int waterLakeChance;
/*     */   public final boolean useLavaLakes;
/*     */   public final int lavaLakeChance;
/*     */   public final boolean useLavaOceans;
/*     */   public final int fixedBiome;
/*     */   public final int biomeSize;
/*     */   public final int riverSize;
/*     */   public final int dirtSize;
/*     */   public final int dirtCount;
/*     */   public final int dirtMinHeight;
/*     */   public final int dirtMaxHeight;
/*     */   public final int gravelSize;
/*     */   
/*     */   ChunkProviderSettings(Factory settingsFactory) {
/*  92 */     this.coordinateScale = settingsFactory.coordinateScale;
/*  93 */     this.heightScale = settingsFactory.heightScale;
/*  94 */     this.upperLimitScale = settingsFactory.upperLimitScale;
/*  95 */     this.lowerLimitScale = settingsFactory.lowerLimitScale;
/*  96 */     this.depthNoiseScaleX = settingsFactory.depthNoiseScaleX;
/*  97 */     this.depthNoiseScaleZ = settingsFactory.depthNoiseScaleZ;
/*  98 */     this.depthNoiseScaleExponent = settingsFactory.depthNoiseScaleExponent;
/*  99 */     this.mainNoiseScaleX = settingsFactory.mainNoiseScaleX;
/* 100 */     this.mainNoiseScaleY = settingsFactory.mainNoiseScaleY;
/* 101 */     this.mainNoiseScaleZ = settingsFactory.mainNoiseScaleZ;
/* 102 */     this.baseSize = settingsFactory.baseSize;
/* 103 */     this.stretchY = settingsFactory.stretchY;
/* 104 */     this.biomeDepthWeight = settingsFactory.biomeDepthWeight;
/* 105 */     this.biomeDepthOffSet = settingsFactory.biomeDepthOffset;
/* 106 */     this.biomeScaleWeight = settingsFactory.biomeScaleWeight;
/* 107 */     this.biomeScaleOffset = settingsFactory.biomeScaleOffset;
/* 108 */     this.seaLevel = settingsFactory.seaLevel;
/* 109 */     this.useCaves = settingsFactory.useCaves;
/* 110 */     this.useDungeons = settingsFactory.useDungeons;
/* 111 */     this.dungeonChance = settingsFactory.dungeonChance;
/* 112 */     this.useStrongholds = settingsFactory.useStrongholds;
/* 113 */     this.useVillages = settingsFactory.useVillages;
/* 114 */     this.useMineShafts = settingsFactory.useMineShafts;
/* 115 */     this.useTemples = settingsFactory.useTemples;
/* 116 */     this.useMonuments = settingsFactory.useMonuments;
/* 117 */     this.useRavines = settingsFactory.useRavines;
/* 118 */     this.useWaterLakes = settingsFactory.useWaterLakes;
/* 119 */     this.waterLakeChance = settingsFactory.waterLakeChance;
/* 120 */     this.useLavaLakes = settingsFactory.useLavaLakes;
/* 121 */     this.lavaLakeChance = settingsFactory.lavaLakeChance;
/* 122 */     this.useLavaOceans = settingsFactory.useLavaOceans;
/* 123 */     this.fixedBiome = settingsFactory.fixedBiome;
/* 124 */     this.biomeSize = settingsFactory.biomeSize;
/* 125 */     this.riverSize = settingsFactory.riverSize;
/* 126 */     this.dirtSize = settingsFactory.dirtSize;
/* 127 */     this.dirtCount = settingsFactory.dirtCount;
/* 128 */     this.dirtMinHeight = settingsFactory.dirtMinHeight;
/* 129 */     this.dirtMaxHeight = settingsFactory.dirtMaxHeight;
/* 130 */     this.gravelSize = settingsFactory.gravelSize;
/* 131 */     this.gravelCount = settingsFactory.gravelCount;
/* 132 */     this.gravelMinHeight = settingsFactory.gravelMinHeight;
/* 133 */     this.gravelMaxHeight = settingsFactory.gravelMaxHeight;
/* 134 */     this.graniteSize = settingsFactory.graniteSize;
/* 135 */     this.graniteCount = settingsFactory.graniteCount;
/* 136 */     this.graniteMinHeight = settingsFactory.graniteMinHeight;
/* 137 */     this.graniteMaxHeight = settingsFactory.graniteMaxHeight;
/* 138 */     this.dioriteSize = settingsFactory.dioriteSize;
/* 139 */     this.dioriteCount = settingsFactory.dioriteCount;
/* 140 */     this.dioriteMinHeight = settingsFactory.dioriteMinHeight;
/* 141 */     this.dioriteMaxHeight = settingsFactory.dioriteMaxHeight;
/* 142 */     this.andesiteSize = settingsFactory.andesiteSize;
/* 143 */     this.andesiteCount = settingsFactory.andesiteCount;
/* 144 */     this.andesiteMinHeight = settingsFactory.andesiteMinHeight;
/* 145 */     this.andesiteMaxHeight = settingsFactory.andesiteMaxHeight;
/* 146 */     this.coalSize = settingsFactory.coalSize;
/* 147 */     this.coalCount = settingsFactory.coalCount;
/* 148 */     this.coalMinHeight = settingsFactory.coalMinHeight;
/* 149 */     this.coalMaxHeight = settingsFactory.coalMaxHeight;
/* 150 */     this.ironSize = settingsFactory.ironSize;
/* 151 */     this.ironCount = settingsFactory.ironCount;
/* 152 */     this.ironMinHeight = settingsFactory.ironMinHeight;
/* 153 */     this.ironMaxHeight = settingsFactory.ironMaxHeight;
/* 154 */     this.goldSize = settingsFactory.goldSize;
/* 155 */     this.goldCount = settingsFactory.goldCount;
/* 156 */     this.goldMinHeight = settingsFactory.goldMinHeight;
/* 157 */     this.goldMaxHeight = settingsFactory.goldMaxHeight;
/* 158 */     this.redstoneSize = settingsFactory.redstoneSize;
/* 159 */     this.redstoneCount = settingsFactory.redstoneCount;
/* 160 */     this.redstoneMinHeight = settingsFactory.redstoneMinHeight;
/* 161 */     this.redstoneMaxHeight = settingsFactory.redstoneMaxHeight;
/* 162 */     this.diamondSize = settingsFactory.diamondSize;
/* 163 */     this.diamondCount = settingsFactory.diamondCount;
/* 164 */     this.diamondMinHeight = settingsFactory.diamondMinHeight;
/* 165 */     this.diamondMaxHeight = settingsFactory.diamondMaxHeight;
/* 166 */     this.lapisSize = settingsFactory.lapisSize;
/* 167 */     this.lapisCount = settingsFactory.lapisCount;
/* 168 */     this.lapisCenterHeight = settingsFactory.lapisCenterHeight;
/* 169 */     this.lapisSpread = settingsFactory.lapisSpread;
/*     */   }
/*     */   public final int gravelCount; public final int gravelMinHeight; public final int gravelMaxHeight; public final int graniteSize; public final int graniteCount; public final int graniteMinHeight; public final int graniteMaxHeight; public final int dioriteSize; public final int dioriteCount; public final int dioriteMinHeight; public final int dioriteMaxHeight; public final int andesiteSize; public final int andesiteCount; public final int andesiteMinHeight; public final int andesiteMaxHeight; public final int coalSize; public final int coalCount; public final int coalMinHeight; public final int coalMaxHeight; public final int ironSize; public final int ironCount; public final int ironMinHeight; public final int ironMaxHeight; public final int goldSize; public final int goldCount; public final int goldMinHeight; public final int goldMaxHeight; public final int redstoneSize; public final int redstoneCount; public final int redstoneMinHeight; public final int redstoneMaxHeight; public final int diamondSize; public final int diamondCount; public final int diamondMinHeight; public final int diamondMaxHeight; public final int lapisSize; public final int lapisCount; public final int lapisCenterHeight;
/*     */   public final int lapisSpread;
/*     */   
/* 174 */   public static class Factory { static final Gson JSON_ADAPTER = (new GsonBuilder()).registerTypeAdapter(Factory.class, new ChunkProviderSettings.Serializer()).create();
/* 175 */     public float coordinateScale = 684.412F;
/* 176 */     public float heightScale = 684.412F;
/* 177 */     public float upperLimitScale = 512.0F;
/* 178 */     public float lowerLimitScale = 512.0F;
/* 179 */     public float depthNoiseScaleX = 200.0F;
/* 180 */     public float depthNoiseScaleZ = 200.0F;
/* 181 */     public float depthNoiseScaleExponent = 0.5F;
/* 182 */     public float mainNoiseScaleX = 80.0F;
/* 183 */     public float mainNoiseScaleY = 160.0F;
/* 184 */     public float mainNoiseScaleZ = 80.0F;
/* 185 */     public float baseSize = 8.5F;
/* 186 */     public float stretchY = 12.0F;
/* 187 */     public float biomeDepthWeight = 1.0F;
/* 188 */     public float biomeDepthOffset = 0.0F;
/* 189 */     public float biomeScaleWeight = 1.0F;
/* 190 */     public float biomeScaleOffset = 0.0F;
/* 191 */     public int seaLevel = 63;
/*     */     public boolean useCaves = true;
/*     */     public boolean useDungeons = true;
/* 194 */     public int dungeonChance = 8;
/*     */     public boolean useStrongholds = true;
/*     */     public boolean useVillages = true;
/*     */     public boolean useMineShafts = true;
/*     */     public boolean useTemples = true;
/*     */     public boolean useMonuments = true;
/*     */     public boolean useRavines = true;
/*     */     public boolean useWaterLakes = true;
/* 202 */     public int waterLakeChance = 4;
/*     */     public boolean useLavaLakes = true;
/* 204 */     public int lavaLakeChance = 80;
/*     */     public boolean useLavaOceans = false;
/* 206 */     public int fixedBiome = -1;
/* 207 */     public int biomeSize = 4;
/* 208 */     public int riverSize = 4;
/* 209 */     public int dirtSize = 33;
/* 210 */     public int dirtCount = 10;
/* 211 */     public int dirtMinHeight = 0;
/* 212 */     public int dirtMaxHeight = 256;
/* 213 */     public int gravelSize = 33;
/* 214 */     public int gravelCount = 8;
/* 215 */     public int gravelMinHeight = 0;
/* 216 */     public int gravelMaxHeight = 256;
/* 217 */     public int graniteSize = 33;
/* 218 */     public int graniteCount = 10;
/* 219 */     public int graniteMinHeight = 0;
/* 220 */     public int graniteMaxHeight = 80;
/* 221 */     public int dioriteSize = 33;
/* 222 */     public int dioriteCount = 10;
/* 223 */     public int dioriteMinHeight = 0;
/* 224 */     public int dioriteMaxHeight = 80;
/* 225 */     public int andesiteSize = 33;
/* 226 */     public int andesiteCount = 10;
/* 227 */     public int andesiteMinHeight = 0;
/* 228 */     public int andesiteMaxHeight = 80;
/* 229 */     public int coalSize = 17;
/* 230 */     public int coalCount = 20;
/* 231 */     public int coalMinHeight = 0;
/* 232 */     public int coalMaxHeight = 128;
/* 233 */     public int ironSize = 9;
/* 234 */     public int ironCount = 20;
/* 235 */     public int ironMinHeight = 0;
/* 236 */     public int ironMaxHeight = 64;
/* 237 */     public int goldSize = 9;
/* 238 */     public int goldCount = 2;
/* 239 */     public int goldMinHeight = 0;
/* 240 */     public int goldMaxHeight = 32;
/* 241 */     public int redstoneSize = 8;
/* 242 */     public int redstoneCount = 8;
/* 243 */     public int redstoneMinHeight = 0;
/* 244 */     public int redstoneMaxHeight = 16;
/* 245 */     public int diamondSize = 8;
/* 246 */     public int diamondCount = 1;
/* 247 */     public int diamondMinHeight = 0;
/* 248 */     public int diamondMaxHeight = 16;
/* 249 */     public int lapisSize = 7;
/* 250 */     public int lapisCount = 1;
/* 251 */     public int lapisCenterHeight = 16;
/* 252 */     public int lapisSpread = 16;
/*     */ 
/*     */     
/*     */     public static Factory jsonToFactory(String p_177865_0_) {
/* 256 */       if (p_177865_0_.isEmpty())
/*     */       {
/* 258 */         return new Factory();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 264 */         return (Factory)JSON_ADAPTER.fromJson(p_177865_0_, Factory.class);
/*     */       }
/* 266 */       catch (Exception var2) {
/*     */         
/* 268 */         return new Factory();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 275 */       return JSON_ADAPTER.toJson(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Factory() {
/* 280 */       func_177863_a();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_177863_a() {
/* 285 */       this.coordinateScale = 684.412F;
/* 286 */       this.heightScale = 684.412F;
/* 287 */       this.upperLimitScale = 512.0F;
/* 288 */       this.lowerLimitScale = 512.0F;
/* 289 */       this.depthNoiseScaleX = 200.0F;
/* 290 */       this.depthNoiseScaleZ = 200.0F;
/* 291 */       this.depthNoiseScaleExponent = 0.5F;
/* 292 */       this.mainNoiseScaleX = 80.0F;
/* 293 */       this.mainNoiseScaleY = 160.0F;
/* 294 */       this.mainNoiseScaleZ = 80.0F;
/* 295 */       this.baseSize = 8.5F;
/* 296 */       this.stretchY = 12.0F;
/* 297 */       this.biomeDepthWeight = 1.0F;
/* 298 */       this.biomeDepthOffset = 0.0F;
/* 299 */       this.biomeScaleWeight = 1.0F;
/* 300 */       this.biomeScaleOffset = 0.0F;
/* 301 */       this.seaLevel = 63;
/* 302 */       this.useCaves = true;
/* 303 */       this.useDungeons = true;
/* 304 */       this.dungeonChance = 8;
/* 305 */       this.useStrongholds = true;
/* 306 */       this.useVillages = true;
/* 307 */       this.useMineShafts = true;
/* 308 */       this.useTemples = true;
/* 309 */       this.useMonuments = true;
/* 310 */       this.useRavines = true;
/* 311 */       this.useWaterLakes = true;
/* 312 */       this.waterLakeChance = 4;
/* 313 */       this.useLavaLakes = true;
/* 314 */       this.lavaLakeChance = 80;
/* 315 */       this.useLavaOceans = false;
/* 316 */       this.fixedBiome = -1;
/* 317 */       this.biomeSize = 4;
/* 318 */       this.riverSize = 4;
/* 319 */       this.dirtSize = 33;
/* 320 */       this.dirtCount = 10;
/* 321 */       this.dirtMinHeight = 0;
/* 322 */       this.dirtMaxHeight = 256;
/* 323 */       this.gravelSize = 33;
/* 324 */       this.gravelCount = 8;
/* 325 */       this.gravelMinHeight = 0;
/* 326 */       this.gravelMaxHeight = 256;
/* 327 */       this.graniteSize = 33;
/* 328 */       this.graniteCount = 10;
/* 329 */       this.graniteMinHeight = 0;
/* 330 */       this.graniteMaxHeight = 80;
/* 331 */       this.dioriteSize = 33;
/* 332 */       this.dioriteCount = 10;
/* 333 */       this.dioriteMinHeight = 0;
/* 334 */       this.dioriteMaxHeight = 80;
/* 335 */       this.andesiteSize = 33;
/* 336 */       this.andesiteCount = 10;
/* 337 */       this.andesiteMinHeight = 0;
/* 338 */       this.andesiteMaxHeight = 80;
/* 339 */       this.coalSize = 17;
/* 340 */       this.coalCount = 20;
/* 341 */       this.coalMinHeight = 0;
/* 342 */       this.coalMaxHeight = 128;
/* 343 */       this.ironSize = 9;
/* 344 */       this.ironCount = 20;
/* 345 */       this.ironMinHeight = 0;
/* 346 */       this.ironMaxHeight = 64;
/* 347 */       this.goldSize = 9;
/* 348 */       this.goldCount = 2;
/* 349 */       this.goldMinHeight = 0;
/* 350 */       this.goldMaxHeight = 32;
/* 351 */       this.redstoneSize = 8;
/* 352 */       this.redstoneCount = 8;
/* 353 */       this.redstoneMinHeight = 0;
/* 354 */       this.redstoneMaxHeight = 16;
/* 355 */       this.diamondSize = 8;
/* 356 */       this.diamondCount = 1;
/* 357 */       this.diamondMinHeight = 0;
/* 358 */       this.diamondMaxHeight = 16;
/* 359 */       this.lapisSize = 7;
/* 360 */       this.lapisCount = 1;
/* 361 */       this.lapisCenterHeight = 16;
/* 362 */       this.lapisSpread = 16;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 367 */       if (this == p_equals_1_)
/*     */       {
/* 369 */         return true;
/*     */       }
/* 371 */       if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */         
/* 373 */         Factory chunkprovidersettings$factory = (Factory)p_equals_1_;
/* 374 */         return (this.andesiteCount != chunkprovidersettings$factory.andesiteCount) ? false : ((this.andesiteMaxHeight != chunkprovidersettings$factory.andesiteMaxHeight) ? false : ((this.andesiteMinHeight != chunkprovidersettings$factory.andesiteMinHeight) ? false : ((this.andesiteSize != chunkprovidersettings$factory.andesiteSize) ? false : ((Float.compare(chunkprovidersettings$factory.baseSize, this.baseSize) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeDepthOffset, this.biomeDepthOffset) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeDepthWeight, this.biomeDepthWeight) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeScaleOffset, this.biomeScaleOffset) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeScaleWeight, this.biomeScaleWeight) != 0) ? false : ((this.biomeSize != chunkprovidersettings$factory.biomeSize) ? false : ((this.coalCount != chunkprovidersettings$factory.coalCount) ? false : ((this.coalMaxHeight != chunkprovidersettings$factory.coalMaxHeight) ? false : ((this.coalMinHeight != chunkprovidersettings$factory.coalMinHeight) ? false : ((this.coalSize != chunkprovidersettings$factory.coalSize) ? false : ((Float.compare(chunkprovidersettings$factory.coordinateScale, this.coordinateScale) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.depthNoiseScaleExponent, this.depthNoiseScaleExponent) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.depthNoiseScaleX, this.depthNoiseScaleX) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.depthNoiseScaleZ, this.depthNoiseScaleZ) != 0) ? false : ((this.diamondCount != chunkprovidersettings$factory.diamondCount) ? false : ((this.diamondMaxHeight != chunkprovidersettings$factory.diamondMaxHeight) ? false : ((this.diamondMinHeight != chunkprovidersettings$factory.diamondMinHeight) ? false : ((this.diamondSize != chunkprovidersettings$factory.diamondSize) ? false : ((this.dioriteCount != chunkprovidersettings$factory.dioriteCount) ? false : ((this.dioriteMaxHeight != chunkprovidersettings$factory.dioriteMaxHeight) ? false : ((this.dioriteMinHeight != chunkprovidersettings$factory.dioriteMinHeight) ? false : ((this.dioriteSize != chunkprovidersettings$factory.dioriteSize) ? false : ((this.dirtCount != chunkprovidersettings$factory.dirtCount) ? false : ((this.dirtMaxHeight != chunkprovidersettings$factory.dirtMaxHeight) ? false : ((this.dirtMinHeight != chunkprovidersettings$factory.dirtMinHeight) ? false : ((this.dirtSize != chunkprovidersettings$factory.dirtSize) ? false : ((this.dungeonChance != chunkprovidersettings$factory.dungeonChance) ? false : ((this.fixedBiome != chunkprovidersettings$factory.fixedBiome) ? false : ((this.goldCount != chunkprovidersettings$factory.goldCount) ? false : ((this.goldMaxHeight != chunkprovidersettings$factory.goldMaxHeight) ? false : ((this.goldMinHeight != chunkprovidersettings$factory.goldMinHeight) ? false : ((this.goldSize != chunkprovidersettings$factory.goldSize) ? false : ((this.graniteCount != chunkprovidersettings$factory.graniteCount) ? false : ((this.graniteMaxHeight != chunkprovidersettings$factory.graniteMaxHeight) ? false : ((this.graniteMinHeight != chunkprovidersettings$factory.graniteMinHeight) ? false : ((this.graniteSize != chunkprovidersettings$factory.graniteSize) ? false : ((this.gravelCount != chunkprovidersettings$factory.gravelCount) ? false : ((this.gravelMaxHeight != chunkprovidersettings$factory.gravelMaxHeight) ? false : ((this.gravelMinHeight != chunkprovidersettings$factory.gravelMinHeight) ? false : ((this.gravelSize != chunkprovidersettings$factory.gravelSize) ? false : ((Float.compare(chunkprovidersettings$factory.heightScale, this.heightScale) != 0) ? false : ((this.ironCount != chunkprovidersettings$factory.ironCount) ? false : ((this.ironMaxHeight != chunkprovidersettings$factory.ironMaxHeight) ? false : ((this.ironMinHeight != chunkprovidersettings$factory.ironMinHeight) ? false : ((this.ironSize != chunkprovidersettings$factory.ironSize) ? false : ((this.lapisCenterHeight != chunkprovidersettings$factory.lapisCenterHeight) ? false : ((this.lapisCount != chunkprovidersettings$factory.lapisCount) ? false : ((this.lapisSize != chunkprovidersettings$factory.lapisSize) ? false : ((this.lapisSpread != chunkprovidersettings$factory.lapisSpread) ? false : ((this.lavaLakeChance != chunkprovidersettings$factory.lavaLakeChance) ? false : ((Float.compare(chunkprovidersettings$factory.lowerLimitScale, this.lowerLimitScale) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.mainNoiseScaleX, this.mainNoiseScaleX) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.mainNoiseScaleY, this.mainNoiseScaleY) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.mainNoiseScaleZ, this.mainNoiseScaleZ) != 0) ? false : ((this.redstoneCount != chunkprovidersettings$factory.redstoneCount) ? false : ((this.redstoneMaxHeight != chunkprovidersettings$factory.redstoneMaxHeight) ? false : ((this.redstoneMinHeight != chunkprovidersettings$factory.redstoneMinHeight) ? false : ((this.redstoneSize != chunkprovidersettings$factory.redstoneSize) ? false : ((this.riverSize != chunkprovidersettings$factory.riverSize) ? false : ((this.seaLevel != chunkprovidersettings$factory.seaLevel) ? false : ((Float.compare(chunkprovidersettings$factory.stretchY, this.stretchY) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.upperLimitScale, this.upperLimitScale) != 0) ? false : ((this.useCaves != chunkprovidersettings$factory.useCaves) ? false : ((this.useDungeons != chunkprovidersettings$factory.useDungeons) ? false : ((this.useLavaLakes != chunkprovidersettings$factory.useLavaLakes) ? false : ((this.useLavaOceans != chunkprovidersettings$factory.useLavaOceans) ? false : ((this.useMineShafts != chunkprovidersettings$factory.useMineShafts) ? false : ((this.useRavines != chunkprovidersettings$factory.useRavines) ? false : ((this.useStrongholds != chunkprovidersettings$factory.useStrongholds) ? false : ((this.useTemples != chunkprovidersettings$factory.useTemples) ? false : ((this.useMonuments != chunkprovidersettings$factory.useMonuments) ? false : ((this.useVillages != chunkprovidersettings$factory.useVillages) ? false : ((this.useWaterLakes != chunkprovidersettings$factory.useWaterLakes) ? false : ((this.waterLakeChance == chunkprovidersettings$factory.waterLakeChance))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))));
/*     */       } 
/*     */ 
/*     */       
/* 378 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 384 */       int i = (this.coordinateScale != 0.0F) ? Float.floatToIntBits(this.coordinateScale) : 0;
/* 385 */       i = 31 * i + ((this.heightScale != 0.0F) ? Float.floatToIntBits(this.heightScale) : 0);
/* 386 */       i = 31 * i + ((this.upperLimitScale != 0.0F) ? Float.floatToIntBits(this.upperLimitScale) : 0);
/* 387 */       i = 31 * i + ((this.lowerLimitScale != 0.0F) ? Float.floatToIntBits(this.lowerLimitScale) : 0);
/* 388 */       i = 31 * i + ((this.depthNoiseScaleX != 0.0F) ? Float.floatToIntBits(this.depthNoiseScaleX) : 0);
/* 389 */       i = 31 * i + ((this.depthNoiseScaleZ != 0.0F) ? Float.floatToIntBits(this.depthNoiseScaleZ) : 0);
/* 390 */       i = 31 * i + ((this.depthNoiseScaleExponent != 0.0F) ? Float.floatToIntBits(this.depthNoiseScaleExponent) : 0);
/* 391 */       i = 31 * i + ((this.mainNoiseScaleX != 0.0F) ? Float.floatToIntBits(this.mainNoiseScaleX) : 0);
/* 392 */       i = 31 * i + ((this.mainNoiseScaleY != 0.0F) ? Float.floatToIntBits(this.mainNoiseScaleY) : 0);
/* 393 */       i = 31 * i + ((this.mainNoiseScaleZ != 0.0F) ? Float.floatToIntBits(this.mainNoiseScaleZ) : 0);
/* 394 */       i = 31 * i + ((this.baseSize != 0.0F) ? Float.floatToIntBits(this.baseSize) : 0);
/* 395 */       i = 31 * i + ((this.stretchY != 0.0F) ? Float.floatToIntBits(this.stretchY) : 0);
/* 396 */       i = 31 * i + ((this.biomeDepthWeight != 0.0F) ? Float.floatToIntBits(this.biomeDepthWeight) : 0);
/* 397 */       i = 31 * i + ((this.biomeDepthOffset != 0.0F) ? Float.floatToIntBits(this.biomeDepthOffset) : 0);
/* 398 */       i = 31 * i + ((this.biomeScaleWeight != 0.0F) ? Float.floatToIntBits(this.biomeScaleWeight) : 0);
/* 399 */       i = 31 * i + ((this.biomeScaleOffset != 0.0F) ? Float.floatToIntBits(this.biomeScaleOffset) : 0);
/* 400 */       i = 31 * i + this.seaLevel;
/* 401 */       i = 31 * i + (this.useCaves ? 1 : 0);
/* 402 */       i = 31 * i + (this.useDungeons ? 1 : 0);
/* 403 */       i = 31 * i + this.dungeonChance;
/* 404 */       i = 31 * i + (this.useStrongholds ? 1 : 0);
/* 405 */       i = 31 * i + (this.useVillages ? 1 : 0);
/* 406 */       i = 31 * i + (this.useMineShafts ? 1 : 0);
/* 407 */       i = 31 * i + (this.useTemples ? 1 : 0);
/* 408 */       i = 31 * i + (this.useMonuments ? 1 : 0);
/* 409 */       i = 31 * i + (this.useRavines ? 1 : 0);
/* 410 */       i = 31 * i + (this.useWaterLakes ? 1 : 0);
/* 411 */       i = 31 * i + this.waterLakeChance;
/* 412 */       i = 31 * i + (this.useLavaLakes ? 1 : 0);
/* 413 */       i = 31 * i + this.lavaLakeChance;
/* 414 */       i = 31 * i + (this.useLavaOceans ? 1 : 0);
/* 415 */       i = 31 * i + this.fixedBiome;
/* 416 */       i = 31 * i + this.biomeSize;
/* 417 */       i = 31 * i + this.riverSize;
/* 418 */       i = 31 * i + this.dirtSize;
/* 419 */       i = 31 * i + this.dirtCount;
/* 420 */       i = 31 * i + this.dirtMinHeight;
/* 421 */       i = 31 * i + this.dirtMaxHeight;
/* 422 */       i = 31 * i + this.gravelSize;
/* 423 */       i = 31 * i + this.gravelCount;
/* 424 */       i = 31 * i + this.gravelMinHeight;
/* 425 */       i = 31 * i + this.gravelMaxHeight;
/* 426 */       i = 31 * i + this.graniteSize;
/* 427 */       i = 31 * i + this.graniteCount;
/* 428 */       i = 31 * i + this.graniteMinHeight;
/* 429 */       i = 31 * i + this.graniteMaxHeight;
/* 430 */       i = 31 * i + this.dioriteSize;
/* 431 */       i = 31 * i + this.dioriteCount;
/* 432 */       i = 31 * i + this.dioriteMinHeight;
/* 433 */       i = 31 * i + this.dioriteMaxHeight;
/* 434 */       i = 31 * i + this.andesiteSize;
/* 435 */       i = 31 * i + this.andesiteCount;
/* 436 */       i = 31 * i + this.andesiteMinHeight;
/* 437 */       i = 31 * i + this.andesiteMaxHeight;
/* 438 */       i = 31 * i + this.coalSize;
/* 439 */       i = 31 * i + this.coalCount;
/* 440 */       i = 31 * i + this.coalMinHeight;
/* 441 */       i = 31 * i + this.coalMaxHeight;
/* 442 */       i = 31 * i + this.ironSize;
/* 443 */       i = 31 * i + this.ironCount;
/* 444 */       i = 31 * i + this.ironMinHeight;
/* 445 */       i = 31 * i + this.ironMaxHeight;
/* 446 */       i = 31 * i + this.goldSize;
/* 447 */       i = 31 * i + this.goldCount;
/* 448 */       i = 31 * i + this.goldMinHeight;
/* 449 */       i = 31 * i + this.goldMaxHeight;
/* 450 */       i = 31 * i + this.redstoneSize;
/* 451 */       i = 31 * i + this.redstoneCount;
/* 452 */       i = 31 * i + this.redstoneMinHeight;
/* 453 */       i = 31 * i + this.redstoneMaxHeight;
/* 454 */       i = 31 * i + this.diamondSize;
/* 455 */       i = 31 * i + this.diamondCount;
/* 456 */       i = 31 * i + this.diamondMinHeight;
/* 457 */       i = 31 * i + this.diamondMaxHeight;
/* 458 */       i = 31 * i + this.lapisSize;
/* 459 */       i = 31 * i + this.lapisCount;
/* 460 */       i = 31 * i + this.lapisCenterHeight;
/* 461 */       i = 31 * i + this.lapisSpread;
/* 462 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChunkProviderSettings func_177864_b() {
/* 467 */       return new ChunkProviderSettings(this);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<Factory>, JsonSerializer<Factory>
/*     */   {
/*     */     public ChunkProviderSettings.Factory deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 475 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 476 */       ChunkProviderSettings.Factory chunkprovidersettings$factory = new ChunkProviderSettings.Factory();
/*     */ 
/*     */       
/*     */       try {
/* 480 */         chunkprovidersettings$factory.coordinateScale = JsonUtils.getFloat(jsonobject, "coordinateScale", chunkprovidersettings$factory.coordinateScale);
/* 481 */         chunkprovidersettings$factory.heightScale = JsonUtils.getFloat(jsonobject, "heightScale", chunkprovidersettings$factory.heightScale);
/* 482 */         chunkprovidersettings$factory.lowerLimitScale = JsonUtils.getFloat(jsonobject, "lowerLimitScale", chunkprovidersettings$factory.lowerLimitScale);
/* 483 */         chunkprovidersettings$factory.upperLimitScale = JsonUtils.getFloat(jsonobject, "upperLimitScale", chunkprovidersettings$factory.upperLimitScale);
/* 484 */         chunkprovidersettings$factory.depthNoiseScaleX = JsonUtils.getFloat(jsonobject, "depthNoiseScaleX", chunkprovidersettings$factory.depthNoiseScaleX);
/* 485 */         chunkprovidersettings$factory.depthNoiseScaleZ = JsonUtils.getFloat(jsonobject, "depthNoiseScaleZ", chunkprovidersettings$factory.depthNoiseScaleZ);
/* 486 */         chunkprovidersettings$factory.depthNoiseScaleExponent = JsonUtils.getFloat(jsonobject, "depthNoiseScaleExponent", chunkprovidersettings$factory.depthNoiseScaleExponent);
/* 487 */         chunkprovidersettings$factory.mainNoiseScaleX = JsonUtils.getFloat(jsonobject, "mainNoiseScaleX", chunkprovidersettings$factory.mainNoiseScaleX);
/* 488 */         chunkprovidersettings$factory.mainNoiseScaleY = JsonUtils.getFloat(jsonobject, "mainNoiseScaleY", chunkprovidersettings$factory.mainNoiseScaleY);
/* 489 */         chunkprovidersettings$factory.mainNoiseScaleZ = JsonUtils.getFloat(jsonobject, "mainNoiseScaleZ", chunkprovidersettings$factory.mainNoiseScaleZ);
/* 490 */         chunkprovidersettings$factory.baseSize = JsonUtils.getFloat(jsonobject, "baseSize", chunkprovidersettings$factory.baseSize);
/* 491 */         chunkprovidersettings$factory.stretchY = JsonUtils.getFloat(jsonobject, "stretchY", chunkprovidersettings$factory.stretchY);
/* 492 */         chunkprovidersettings$factory.biomeDepthWeight = JsonUtils.getFloat(jsonobject, "biomeDepthWeight", chunkprovidersettings$factory.biomeDepthWeight);
/* 493 */         chunkprovidersettings$factory.biomeDepthOffset = JsonUtils.getFloat(jsonobject, "biomeDepthOffset", chunkprovidersettings$factory.biomeDepthOffset);
/* 494 */         chunkprovidersettings$factory.biomeScaleWeight = JsonUtils.getFloat(jsonobject, "biomeScaleWeight", chunkprovidersettings$factory.biomeScaleWeight);
/* 495 */         chunkprovidersettings$factory.biomeScaleOffset = JsonUtils.getFloat(jsonobject, "biomeScaleOffset", chunkprovidersettings$factory.biomeScaleOffset);
/* 496 */         chunkprovidersettings$factory.seaLevel = JsonUtils.getInt(jsonobject, "seaLevel", chunkprovidersettings$factory.seaLevel);
/* 497 */         chunkprovidersettings$factory.useCaves = JsonUtils.getBoolean(jsonobject, "useCaves", chunkprovidersettings$factory.useCaves);
/* 498 */         chunkprovidersettings$factory.useDungeons = JsonUtils.getBoolean(jsonobject, "useDungeons", chunkprovidersettings$factory.useDungeons);
/* 499 */         chunkprovidersettings$factory.dungeonChance = JsonUtils.getInt(jsonobject, "dungeonChance", chunkprovidersettings$factory.dungeonChance);
/* 500 */         chunkprovidersettings$factory.useStrongholds = JsonUtils.getBoolean(jsonobject, "useStrongholds", chunkprovidersettings$factory.useStrongholds);
/* 501 */         chunkprovidersettings$factory.useVillages = JsonUtils.getBoolean(jsonobject, "useVillages", chunkprovidersettings$factory.useVillages);
/* 502 */         chunkprovidersettings$factory.useMineShafts = JsonUtils.getBoolean(jsonobject, "useMineShafts", chunkprovidersettings$factory.useMineShafts);
/* 503 */         chunkprovidersettings$factory.useTemples = JsonUtils.getBoolean(jsonobject, "useTemples", chunkprovidersettings$factory.useTemples);
/* 504 */         chunkprovidersettings$factory.useMonuments = JsonUtils.getBoolean(jsonobject, "useMonuments", chunkprovidersettings$factory.useMonuments);
/* 505 */         chunkprovidersettings$factory.useRavines = JsonUtils.getBoolean(jsonobject, "useRavines", chunkprovidersettings$factory.useRavines);
/* 506 */         chunkprovidersettings$factory.useWaterLakes = JsonUtils.getBoolean(jsonobject, "useWaterLakes", chunkprovidersettings$factory.useWaterLakes);
/* 507 */         chunkprovidersettings$factory.waterLakeChance = JsonUtils.getInt(jsonobject, "waterLakeChance", chunkprovidersettings$factory.waterLakeChance);
/* 508 */         chunkprovidersettings$factory.useLavaLakes = JsonUtils.getBoolean(jsonobject, "useLavaLakes", chunkprovidersettings$factory.useLavaLakes);
/* 509 */         chunkprovidersettings$factory.lavaLakeChance = JsonUtils.getInt(jsonobject, "lavaLakeChance", chunkprovidersettings$factory.lavaLakeChance);
/* 510 */         chunkprovidersettings$factory.useLavaOceans = JsonUtils.getBoolean(jsonobject, "useLavaOceans", chunkprovidersettings$factory.useLavaOceans);
/* 511 */         chunkprovidersettings$factory.fixedBiome = JsonUtils.getInt(jsonobject, "fixedBiome", chunkprovidersettings$factory.fixedBiome);
/*     */         
/* 513 */         if (chunkprovidersettings$factory.fixedBiome < 38 && chunkprovidersettings$factory.fixedBiome >= -1) {
/*     */           
/* 515 */           if (chunkprovidersettings$factory.fixedBiome >= BiomeGenBase.hell.biomeID)
/*     */           {
/* 517 */             chunkprovidersettings$factory.fixedBiome += 2;
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 522 */           chunkprovidersettings$factory.fixedBiome = -1;
/*     */         } 
/*     */         
/* 525 */         chunkprovidersettings$factory.biomeSize = JsonUtils.getInt(jsonobject, "biomeSize", chunkprovidersettings$factory.biomeSize);
/* 526 */         chunkprovidersettings$factory.riverSize = JsonUtils.getInt(jsonobject, "riverSize", chunkprovidersettings$factory.riverSize);
/* 527 */         chunkprovidersettings$factory.dirtSize = JsonUtils.getInt(jsonobject, "dirtSize", chunkprovidersettings$factory.dirtSize);
/* 528 */         chunkprovidersettings$factory.dirtCount = JsonUtils.getInt(jsonobject, "dirtCount", chunkprovidersettings$factory.dirtCount);
/* 529 */         chunkprovidersettings$factory.dirtMinHeight = JsonUtils.getInt(jsonobject, "dirtMinHeight", chunkprovidersettings$factory.dirtMinHeight);
/* 530 */         chunkprovidersettings$factory.dirtMaxHeight = JsonUtils.getInt(jsonobject, "dirtMaxHeight", chunkprovidersettings$factory.dirtMaxHeight);
/* 531 */         chunkprovidersettings$factory.gravelSize = JsonUtils.getInt(jsonobject, "gravelSize", chunkprovidersettings$factory.gravelSize);
/* 532 */         chunkprovidersettings$factory.gravelCount = JsonUtils.getInt(jsonobject, "gravelCount", chunkprovidersettings$factory.gravelCount);
/* 533 */         chunkprovidersettings$factory.gravelMinHeight = JsonUtils.getInt(jsonobject, "gravelMinHeight", chunkprovidersettings$factory.gravelMinHeight);
/* 534 */         chunkprovidersettings$factory.gravelMaxHeight = JsonUtils.getInt(jsonobject, "gravelMaxHeight", chunkprovidersettings$factory.gravelMaxHeight);
/* 535 */         chunkprovidersettings$factory.graniteSize = JsonUtils.getInt(jsonobject, "graniteSize", chunkprovidersettings$factory.graniteSize);
/* 536 */         chunkprovidersettings$factory.graniteCount = JsonUtils.getInt(jsonobject, "graniteCount", chunkprovidersettings$factory.graniteCount);
/* 537 */         chunkprovidersettings$factory.graniteMinHeight = JsonUtils.getInt(jsonobject, "graniteMinHeight", chunkprovidersettings$factory.graniteMinHeight);
/* 538 */         chunkprovidersettings$factory.graniteMaxHeight = JsonUtils.getInt(jsonobject, "graniteMaxHeight", chunkprovidersettings$factory.graniteMaxHeight);
/* 539 */         chunkprovidersettings$factory.dioriteSize = JsonUtils.getInt(jsonobject, "dioriteSize", chunkprovidersettings$factory.dioriteSize);
/* 540 */         chunkprovidersettings$factory.dioriteCount = JsonUtils.getInt(jsonobject, "dioriteCount", chunkprovidersettings$factory.dioriteCount);
/* 541 */         chunkprovidersettings$factory.dioriteMinHeight = JsonUtils.getInt(jsonobject, "dioriteMinHeight", chunkprovidersettings$factory.dioriteMinHeight);
/* 542 */         chunkprovidersettings$factory.dioriteMaxHeight = JsonUtils.getInt(jsonobject, "dioriteMaxHeight", chunkprovidersettings$factory.dioriteMaxHeight);
/* 543 */         chunkprovidersettings$factory.andesiteSize = JsonUtils.getInt(jsonobject, "andesiteSize", chunkprovidersettings$factory.andesiteSize);
/* 544 */         chunkprovidersettings$factory.andesiteCount = JsonUtils.getInt(jsonobject, "andesiteCount", chunkprovidersettings$factory.andesiteCount);
/* 545 */         chunkprovidersettings$factory.andesiteMinHeight = JsonUtils.getInt(jsonobject, "andesiteMinHeight", chunkprovidersettings$factory.andesiteMinHeight);
/* 546 */         chunkprovidersettings$factory.andesiteMaxHeight = JsonUtils.getInt(jsonobject, "andesiteMaxHeight", chunkprovidersettings$factory.andesiteMaxHeight);
/* 547 */         chunkprovidersettings$factory.coalSize = JsonUtils.getInt(jsonobject, "coalSize", chunkprovidersettings$factory.coalSize);
/* 548 */         chunkprovidersettings$factory.coalCount = JsonUtils.getInt(jsonobject, "coalCount", chunkprovidersettings$factory.coalCount);
/* 549 */         chunkprovidersettings$factory.coalMinHeight = JsonUtils.getInt(jsonobject, "coalMinHeight", chunkprovidersettings$factory.coalMinHeight);
/* 550 */         chunkprovidersettings$factory.coalMaxHeight = JsonUtils.getInt(jsonobject, "coalMaxHeight", chunkprovidersettings$factory.coalMaxHeight);
/* 551 */         chunkprovidersettings$factory.ironSize = JsonUtils.getInt(jsonobject, "ironSize", chunkprovidersettings$factory.ironSize);
/* 552 */         chunkprovidersettings$factory.ironCount = JsonUtils.getInt(jsonobject, "ironCount", chunkprovidersettings$factory.ironCount);
/* 553 */         chunkprovidersettings$factory.ironMinHeight = JsonUtils.getInt(jsonobject, "ironMinHeight", chunkprovidersettings$factory.ironMinHeight);
/* 554 */         chunkprovidersettings$factory.ironMaxHeight = JsonUtils.getInt(jsonobject, "ironMaxHeight", chunkprovidersettings$factory.ironMaxHeight);
/* 555 */         chunkprovidersettings$factory.goldSize = JsonUtils.getInt(jsonobject, "goldSize", chunkprovidersettings$factory.goldSize);
/* 556 */         chunkprovidersettings$factory.goldCount = JsonUtils.getInt(jsonobject, "goldCount", chunkprovidersettings$factory.goldCount);
/* 557 */         chunkprovidersettings$factory.goldMinHeight = JsonUtils.getInt(jsonobject, "goldMinHeight", chunkprovidersettings$factory.goldMinHeight);
/* 558 */         chunkprovidersettings$factory.goldMaxHeight = JsonUtils.getInt(jsonobject, "goldMaxHeight", chunkprovidersettings$factory.goldMaxHeight);
/* 559 */         chunkprovidersettings$factory.redstoneSize = JsonUtils.getInt(jsonobject, "redstoneSize", chunkprovidersettings$factory.redstoneSize);
/* 560 */         chunkprovidersettings$factory.redstoneCount = JsonUtils.getInt(jsonobject, "redstoneCount", chunkprovidersettings$factory.redstoneCount);
/* 561 */         chunkprovidersettings$factory.redstoneMinHeight = JsonUtils.getInt(jsonobject, "redstoneMinHeight", chunkprovidersettings$factory.redstoneMinHeight);
/* 562 */         chunkprovidersettings$factory.redstoneMaxHeight = JsonUtils.getInt(jsonobject, "redstoneMaxHeight", chunkprovidersettings$factory.redstoneMaxHeight);
/* 563 */         chunkprovidersettings$factory.diamondSize = JsonUtils.getInt(jsonobject, "diamondSize", chunkprovidersettings$factory.diamondSize);
/* 564 */         chunkprovidersettings$factory.diamondCount = JsonUtils.getInt(jsonobject, "diamondCount", chunkprovidersettings$factory.diamondCount);
/* 565 */         chunkprovidersettings$factory.diamondMinHeight = JsonUtils.getInt(jsonobject, "diamondMinHeight", chunkprovidersettings$factory.diamondMinHeight);
/* 566 */         chunkprovidersettings$factory.diamondMaxHeight = JsonUtils.getInt(jsonobject, "diamondMaxHeight", chunkprovidersettings$factory.diamondMaxHeight);
/* 567 */         chunkprovidersettings$factory.lapisSize = JsonUtils.getInt(jsonobject, "lapisSize", chunkprovidersettings$factory.lapisSize);
/* 568 */         chunkprovidersettings$factory.lapisCount = JsonUtils.getInt(jsonobject, "lapisCount", chunkprovidersettings$factory.lapisCount);
/* 569 */         chunkprovidersettings$factory.lapisCenterHeight = JsonUtils.getInt(jsonobject, "lapisCenterHeight", chunkprovidersettings$factory.lapisCenterHeight);
/* 570 */         chunkprovidersettings$factory.lapisSpread = JsonUtils.getInt(jsonobject, "lapisSpread", chunkprovidersettings$factory.lapisSpread);
/*     */       }
/* 572 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 577 */       return chunkprovidersettings$factory;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ChunkProviderSettings.Factory p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 582 */       JsonObject jsonobject = new JsonObject();
/* 583 */       jsonobject.addProperty("coordinateScale", Float.valueOf(p_serialize_1_.coordinateScale));
/* 584 */       jsonobject.addProperty("heightScale", Float.valueOf(p_serialize_1_.heightScale));
/* 585 */       jsonobject.addProperty("lowerLimitScale", Float.valueOf(p_serialize_1_.lowerLimitScale));
/* 586 */       jsonobject.addProperty("upperLimitScale", Float.valueOf(p_serialize_1_.upperLimitScale));
/* 587 */       jsonobject.addProperty("depthNoiseScaleX", Float.valueOf(p_serialize_1_.depthNoiseScaleX));
/* 588 */       jsonobject.addProperty("depthNoiseScaleZ", Float.valueOf(p_serialize_1_.depthNoiseScaleZ));
/* 589 */       jsonobject.addProperty("depthNoiseScaleExponent", Float.valueOf(p_serialize_1_.depthNoiseScaleExponent));
/* 590 */       jsonobject.addProperty("mainNoiseScaleX", Float.valueOf(p_serialize_1_.mainNoiseScaleX));
/* 591 */       jsonobject.addProperty("mainNoiseScaleY", Float.valueOf(p_serialize_1_.mainNoiseScaleY));
/* 592 */       jsonobject.addProperty("mainNoiseScaleZ", Float.valueOf(p_serialize_1_.mainNoiseScaleZ));
/* 593 */       jsonobject.addProperty("baseSize", Float.valueOf(p_serialize_1_.baseSize));
/* 594 */       jsonobject.addProperty("stretchY", Float.valueOf(p_serialize_1_.stretchY));
/* 595 */       jsonobject.addProperty("biomeDepthWeight", Float.valueOf(p_serialize_1_.biomeDepthWeight));
/* 596 */       jsonobject.addProperty("biomeDepthOffset", Float.valueOf(p_serialize_1_.biomeDepthOffset));
/* 597 */       jsonobject.addProperty("biomeScaleWeight", Float.valueOf(p_serialize_1_.biomeScaleWeight));
/* 598 */       jsonobject.addProperty("biomeScaleOffset", Float.valueOf(p_serialize_1_.biomeScaleOffset));
/* 599 */       jsonobject.addProperty("seaLevel", Integer.valueOf(p_serialize_1_.seaLevel));
/* 600 */       jsonobject.addProperty("useCaves", Boolean.valueOf(p_serialize_1_.useCaves));
/* 601 */       jsonobject.addProperty("useDungeons", Boolean.valueOf(p_serialize_1_.useDungeons));
/* 602 */       jsonobject.addProperty("dungeonChance", Integer.valueOf(p_serialize_1_.dungeonChance));
/* 603 */       jsonobject.addProperty("useStrongholds", Boolean.valueOf(p_serialize_1_.useStrongholds));
/* 604 */       jsonobject.addProperty("useVillages", Boolean.valueOf(p_serialize_1_.useVillages));
/* 605 */       jsonobject.addProperty("useMineShafts", Boolean.valueOf(p_serialize_1_.useMineShafts));
/* 606 */       jsonobject.addProperty("useTemples", Boolean.valueOf(p_serialize_1_.useTemples));
/* 607 */       jsonobject.addProperty("useMonuments", Boolean.valueOf(p_serialize_1_.useMonuments));
/* 608 */       jsonobject.addProperty("useRavines", Boolean.valueOf(p_serialize_1_.useRavines));
/* 609 */       jsonobject.addProperty("useWaterLakes", Boolean.valueOf(p_serialize_1_.useWaterLakes));
/* 610 */       jsonobject.addProperty("waterLakeChance", Integer.valueOf(p_serialize_1_.waterLakeChance));
/* 611 */       jsonobject.addProperty("useLavaLakes", Boolean.valueOf(p_serialize_1_.useLavaLakes));
/* 612 */       jsonobject.addProperty("lavaLakeChance", Integer.valueOf(p_serialize_1_.lavaLakeChance));
/* 613 */       jsonobject.addProperty("useLavaOceans", Boolean.valueOf(p_serialize_1_.useLavaOceans));
/* 614 */       jsonobject.addProperty("fixedBiome", Integer.valueOf(p_serialize_1_.fixedBiome));
/* 615 */       jsonobject.addProperty("biomeSize", Integer.valueOf(p_serialize_1_.biomeSize));
/* 616 */       jsonobject.addProperty("riverSize", Integer.valueOf(p_serialize_1_.riverSize));
/* 617 */       jsonobject.addProperty("dirtSize", Integer.valueOf(p_serialize_1_.dirtSize));
/* 618 */       jsonobject.addProperty("dirtCount", Integer.valueOf(p_serialize_1_.dirtCount));
/* 619 */       jsonobject.addProperty("dirtMinHeight", Integer.valueOf(p_serialize_1_.dirtMinHeight));
/* 620 */       jsonobject.addProperty("dirtMaxHeight", Integer.valueOf(p_serialize_1_.dirtMaxHeight));
/* 621 */       jsonobject.addProperty("gravelSize", Integer.valueOf(p_serialize_1_.gravelSize));
/* 622 */       jsonobject.addProperty("gravelCount", Integer.valueOf(p_serialize_1_.gravelCount));
/* 623 */       jsonobject.addProperty("gravelMinHeight", Integer.valueOf(p_serialize_1_.gravelMinHeight));
/* 624 */       jsonobject.addProperty("gravelMaxHeight", Integer.valueOf(p_serialize_1_.gravelMaxHeight));
/* 625 */       jsonobject.addProperty("graniteSize", Integer.valueOf(p_serialize_1_.graniteSize));
/* 626 */       jsonobject.addProperty("graniteCount", Integer.valueOf(p_serialize_1_.graniteCount));
/* 627 */       jsonobject.addProperty("graniteMinHeight", Integer.valueOf(p_serialize_1_.graniteMinHeight));
/* 628 */       jsonobject.addProperty("graniteMaxHeight", Integer.valueOf(p_serialize_1_.graniteMaxHeight));
/* 629 */       jsonobject.addProperty("dioriteSize", Integer.valueOf(p_serialize_1_.dioriteSize));
/* 630 */       jsonobject.addProperty("dioriteCount", Integer.valueOf(p_serialize_1_.dioriteCount));
/* 631 */       jsonobject.addProperty("dioriteMinHeight", Integer.valueOf(p_serialize_1_.dioriteMinHeight));
/* 632 */       jsonobject.addProperty("dioriteMaxHeight", Integer.valueOf(p_serialize_1_.dioriteMaxHeight));
/* 633 */       jsonobject.addProperty("andesiteSize", Integer.valueOf(p_serialize_1_.andesiteSize));
/* 634 */       jsonobject.addProperty("andesiteCount", Integer.valueOf(p_serialize_1_.andesiteCount));
/* 635 */       jsonobject.addProperty("andesiteMinHeight", Integer.valueOf(p_serialize_1_.andesiteMinHeight));
/* 636 */       jsonobject.addProperty("andesiteMaxHeight", Integer.valueOf(p_serialize_1_.andesiteMaxHeight));
/* 637 */       jsonobject.addProperty("coalSize", Integer.valueOf(p_serialize_1_.coalSize));
/* 638 */       jsonobject.addProperty("coalCount", Integer.valueOf(p_serialize_1_.coalCount));
/* 639 */       jsonobject.addProperty("coalMinHeight", Integer.valueOf(p_serialize_1_.coalMinHeight));
/* 640 */       jsonobject.addProperty("coalMaxHeight", Integer.valueOf(p_serialize_1_.coalMaxHeight));
/* 641 */       jsonobject.addProperty("ironSize", Integer.valueOf(p_serialize_1_.ironSize));
/* 642 */       jsonobject.addProperty("ironCount", Integer.valueOf(p_serialize_1_.ironCount));
/* 643 */       jsonobject.addProperty("ironMinHeight", Integer.valueOf(p_serialize_1_.ironMinHeight));
/* 644 */       jsonobject.addProperty("ironMaxHeight", Integer.valueOf(p_serialize_1_.ironMaxHeight));
/* 645 */       jsonobject.addProperty("goldSize", Integer.valueOf(p_serialize_1_.goldSize));
/* 646 */       jsonobject.addProperty("goldCount", Integer.valueOf(p_serialize_1_.goldCount));
/* 647 */       jsonobject.addProperty("goldMinHeight", Integer.valueOf(p_serialize_1_.goldMinHeight));
/* 648 */       jsonobject.addProperty("goldMaxHeight", Integer.valueOf(p_serialize_1_.goldMaxHeight));
/* 649 */       jsonobject.addProperty("redstoneSize", Integer.valueOf(p_serialize_1_.redstoneSize));
/* 650 */       jsonobject.addProperty("redstoneCount", Integer.valueOf(p_serialize_1_.redstoneCount));
/* 651 */       jsonobject.addProperty("redstoneMinHeight", Integer.valueOf(p_serialize_1_.redstoneMinHeight));
/* 652 */       jsonobject.addProperty("redstoneMaxHeight", Integer.valueOf(p_serialize_1_.redstoneMaxHeight));
/* 653 */       jsonobject.addProperty("diamondSize", Integer.valueOf(p_serialize_1_.diamondSize));
/* 654 */       jsonobject.addProperty("diamondCount", Integer.valueOf(p_serialize_1_.diamondCount));
/* 655 */       jsonobject.addProperty("diamondMinHeight", Integer.valueOf(p_serialize_1_.diamondMinHeight));
/* 656 */       jsonobject.addProperty("diamondMaxHeight", Integer.valueOf(p_serialize_1_.diamondMaxHeight));
/* 657 */       jsonobject.addProperty("lapisSize", Integer.valueOf(p_serialize_1_.lapisSize));
/* 658 */       jsonobject.addProperty("lapisCount", Integer.valueOf(p_serialize_1_.lapisCount));
/* 659 */       jsonobject.addProperty("lapisCenterHeight", Integer.valueOf(p_serialize_1_.lapisCenterHeight));
/* 660 */       jsonobject.addProperty("lapisSpread", Integer.valueOf(p_serialize_1_.lapisSpread));
/* 661 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\ChunkProviderSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */