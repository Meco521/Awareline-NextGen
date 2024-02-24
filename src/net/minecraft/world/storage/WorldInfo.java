/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.Atmosphere;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ public class WorldInfo
/*     */ {
/*  17 */   public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
/*     */   
/*     */   private long randomSeed;
/*     */   
/*  21 */   WorldType terrainType = WorldType.DEFAULT;
/*  22 */   String generatorOptions = "";
/*     */ 
/*     */   
/*     */   int spawnX;
/*     */ 
/*     */   
/*     */   int spawnY;
/*     */ 
/*     */   
/*     */   int spawnZ;
/*     */ 
/*     */   
/*     */   long totalTime;
/*     */ 
/*     */   
/*     */   long worldTime;
/*     */ 
/*     */   
/*     */   private long lastTimePlayed;
/*     */ 
/*     */   
/*     */   private long sizeOnDisk;
/*     */ 
/*     */   
/*     */   private NBTTagCompound playerTag;
/*     */ 
/*     */   
/*     */   int dimension;
/*     */ 
/*     */   
/*     */   private String levelName;
/*     */   
/*     */   int saveVersion;
/*     */   
/*     */   private int cleanWeatherTime;
/*     */   
/*     */   boolean raining;
/*     */   
/*     */   int rainTime;
/*     */   
/*     */   boolean thundering;
/*     */   
/*     */   int thunderTime;
/*     */   
/*     */   WorldSettings.GameType theGameType;
/*     */   
/*     */   boolean mapFeaturesEnabled;
/*     */   
/*     */   boolean hardcore;
/*     */   
/*     */   boolean allowCommands;
/*     */   
/*     */   private boolean initialized;
/*     */   
/*     */   private EnumDifficulty difficulty;
/*     */   
/*     */   private boolean difficultyLocked;
/*     */   
/*  80 */   private double borderCenterX = 0.0D;
/*  81 */   private double borderCenterZ = 0.0D;
/*  82 */   private double borderSize = 6.0E7D;
/*  83 */   private long borderSizeLerpTime = 0L;
/*  84 */   private double borderSizeLerpTarget = 0.0D;
/*  85 */   private double borderSafeZone = 5.0D;
/*  86 */   private double borderDamagePerBlock = 0.2D;
/*  87 */   private int borderWarningDistance = 5;
/*  88 */   private int borderWarningTime = 15;
/*  89 */   private GameRules theGameRules = new GameRules();
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldInfo() {}
/*     */ 
/*     */   
/*     */   public WorldInfo(NBTTagCompound nbt) {
/*  97 */     this.randomSeed = nbt.getLong("RandomSeed");
/*     */     
/*  99 */     if (nbt.hasKey("generatorName", 8)) {
/*     */       
/* 101 */       String s = nbt.getString("generatorName");
/* 102 */       this.terrainType = WorldType.parseWorldType(s);
/*     */       
/* 104 */       if (this.terrainType == null) {
/*     */         
/* 106 */         this.terrainType = WorldType.DEFAULT;
/*     */       }
/* 108 */       else if (this.terrainType.isVersioned()) {
/*     */         
/* 110 */         int i = 0;
/*     */         
/* 112 */         if (nbt.hasKey("generatorVersion", 99))
/*     */         {
/* 114 */           i = nbt.getInteger("generatorVersion");
/*     */         }
/*     */         
/* 117 */         this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(i);
/*     */       } 
/*     */       
/* 120 */       if (nbt.hasKey("generatorOptions", 8))
/*     */       {
/* 122 */         this.generatorOptions = nbt.getString("generatorOptions");
/*     */       }
/*     */     } 
/*     */     
/* 126 */     this.theGameType = WorldSettings.GameType.getByID(nbt.getInteger("GameType"));
/*     */     
/* 128 */     if (nbt.hasKey("MapFeatures", 99)) {
/*     */       
/* 130 */       this.mapFeaturesEnabled = nbt.getBoolean("MapFeatures");
/*     */     }
/*     */     else {
/*     */       
/* 134 */       this.mapFeaturesEnabled = true;
/*     */     } 
/*     */     
/* 137 */     this.spawnX = nbt.getInteger("SpawnX");
/* 138 */     this.spawnY = nbt.getInteger("SpawnY");
/* 139 */     this.spawnZ = nbt.getInteger("SpawnZ");
/* 140 */     this.totalTime = nbt.getLong("Time");
/*     */     
/* 142 */     if (nbt.hasKey("DayTime", 99)) {
/*     */       
/* 144 */       this.worldTime = nbt.getLong("DayTime");
/*     */     }
/*     */     else {
/*     */       
/* 148 */       this.worldTime = this.totalTime;
/*     */     } 
/*     */     
/* 151 */     this.lastTimePlayed = nbt.getLong("LastPlayed");
/* 152 */     this.sizeOnDisk = nbt.getLong("SizeOnDisk");
/* 153 */     this.levelName = nbt.getString("LevelName");
/* 154 */     this.saveVersion = nbt.getInteger("version");
/* 155 */     this.cleanWeatherTime = nbt.getInteger("clearWeatherTime");
/* 156 */     this.rainTime = nbt.getInteger("rainTime");
/* 157 */     this.raining = nbt.getBoolean("raining");
/* 158 */     this.thunderTime = nbt.getInteger("thunderTime");
/* 159 */     this.thundering = nbt.getBoolean("thundering");
/* 160 */     this.hardcore = nbt.getBoolean("hardcore");
/*     */     
/* 162 */     if (nbt.hasKey("initialized", 99)) {
/*     */       
/* 164 */       this.initialized = nbt.getBoolean("initialized");
/*     */     }
/*     */     else {
/*     */       
/* 168 */       this.initialized = true;
/*     */     } 
/*     */     
/* 171 */     if (nbt.hasKey("allowCommands", 99)) {
/*     */       
/* 173 */       this.allowCommands = nbt.getBoolean("allowCommands");
/*     */     }
/*     */     else {
/*     */       
/* 177 */       this.allowCommands = (this.theGameType == WorldSettings.GameType.CREATIVE);
/*     */     } 
/*     */     
/* 180 */     if (nbt.hasKey("Player", 10)) {
/*     */       
/* 182 */       this.playerTag = nbt.getCompoundTag("Player");
/* 183 */       this.dimension = this.playerTag.getInteger("Dimension");
/*     */     } 
/*     */     
/* 186 */     if (nbt.hasKey("GameRules", 10))
/*     */     {
/* 188 */       this.theGameRules.readFromNBT(nbt.getCompoundTag("GameRules"));
/*     */     }
/*     */     
/* 191 */     if (nbt.hasKey("Difficulty", 99))
/*     */     {
/* 193 */       this.difficulty = EnumDifficulty.getDifficultyEnum(nbt.getByte("Difficulty"));
/*     */     }
/*     */     
/* 196 */     if (nbt.hasKey("DifficultyLocked", 1))
/*     */     {
/* 198 */       this.difficultyLocked = nbt.getBoolean("DifficultyLocked");
/*     */     }
/*     */     
/* 201 */     if (nbt.hasKey("BorderCenterX", 99))
/*     */     {
/* 203 */       this.borderCenterX = nbt.getDouble("BorderCenterX");
/*     */     }
/*     */     
/* 206 */     if (nbt.hasKey("BorderCenterZ", 99))
/*     */     {
/* 208 */       this.borderCenterZ = nbt.getDouble("BorderCenterZ");
/*     */     }
/*     */     
/* 211 */     if (nbt.hasKey("BorderSize", 99))
/*     */     {
/* 213 */       this.borderSize = nbt.getDouble("BorderSize");
/*     */     }
/*     */     
/* 216 */     if (nbt.hasKey("BorderSizeLerpTime", 99))
/*     */     {
/* 218 */       this.borderSizeLerpTime = nbt.getLong("BorderSizeLerpTime");
/*     */     }
/*     */     
/* 221 */     if (nbt.hasKey("BorderSizeLerpTarget", 99))
/*     */     {
/* 223 */       this.borderSizeLerpTarget = nbt.getDouble("BorderSizeLerpTarget");
/*     */     }
/*     */     
/* 226 */     if (nbt.hasKey("BorderSafeZone", 99))
/*     */     {
/* 228 */       this.borderSafeZone = nbt.getDouble("BorderSafeZone");
/*     */     }
/*     */     
/* 231 */     if (nbt.hasKey("BorderDamagePerBlock", 99))
/*     */     {
/* 233 */       this.borderDamagePerBlock = nbt.getDouble("BorderDamagePerBlock");
/*     */     }
/*     */     
/* 236 */     if (nbt.hasKey("BorderWarningBlocks", 99))
/*     */     {
/* 238 */       this.borderWarningDistance = nbt.getInteger("BorderWarningBlocks");
/*     */     }
/*     */     
/* 241 */     if (nbt.hasKey("BorderWarningTime", 99))
/*     */     {
/* 243 */       this.borderWarningTime = nbt.getInteger("BorderWarningTime");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldInfo(WorldSettings settings, String name) {
/* 249 */     populateFromWorldSettings(settings);
/* 250 */     this.levelName = name;
/* 251 */     this.difficulty = DEFAULT_DIFFICULTY;
/* 252 */     this.initialized = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void populateFromWorldSettings(WorldSettings settings) {
/* 257 */     this.randomSeed = settings.getSeed();
/* 258 */     this.theGameType = settings.getGameType();
/* 259 */     this.mapFeaturesEnabled = settings.isMapFeaturesEnabled();
/* 260 */     this.hardcore = settings.getHardcoreEnabled();
/* 261 */     this.terrainType = settings.getTerrainType();
/* 262 */     this.generatorOptions = settings.getWorldName();
/* 263 */     this.allowCommands = settings.areCommandsAllowed();
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldInfo(WorldInfo worldInformation) {
/* 268 */     this.randomSeed = worldInformation.randomSeed;
/* 269 */     this.terrainType = worldInformation.terrainType;
/* 270 */     this.generatorOptions = worldInformation.generatorOptions;
/* 271 */     this.theGameType = worldInformation.theGameType;
/* 272 */     this.mapFeaturesEnabled = worldInformation.mapFeaturesEnabled;
/* 273 */     this.spawnX = worldInformation.spawnX;
/* 274 */     this.spawnY = worldInformation.spawnY;
/* 275 */     this.spawnZ = worldInformation.spawnZ;
/* 276 */     this.totalTime = worldInformation.totalTime;
/* 277 */     this.worldTime = worldInformation.worldTime;
/* 278 */     this.lastTimePlayed = worldInformation.lastTimePlayed;
/* 279 */     this.sizeOnDisk = worldInformation.sizeOnDisk;
/* 280 */     this.playerTag = worldInformation.playerTag;
/* 281 */     this.dimension = worldInformation.dimension;
/* 282 */     this.levelName = worldInformation.levelName;
/* 283 */     this.saveVersion = worldInformation.saveVersion;
/* 284 */     this.rainTime = worldInformation.rainTime;
/* 285 */     this.raining = worldInformation.raining;
/* 286 */     this.thunderTime = worldInformation.thunderTime;
/* 287 */     this.thundering = worldInformation.thundering;
/* 288 */     this.hardcore = worldInformation.hardcore;
/* 289 */     this.allowCommands = worldInformation.allowCommands;
/* 290 */     this.initialized = worldInformation.initialized;
/* 291 */     this.theGameRules = worldInformation.theGameRules;
/* 292 */     this.difficulty = worldInformation.difficulty;
/* 293 */     this.difficultyLocked = worldInformation.difficultyLocked;
/* 294 */     this.borderCenterX = worldInformation.borderCenterX;
/* 295 */     this.borderCenterZ = worldInformation.borderCenterZ;
/* 296 */     this.borderSize = worldInformation.borderSize;
/* 297 */     this.borderSizeLerpTime = worldInformation.borderSizeLerpTime;
/* 298 */     this.borderSizeLerpTarget = worldInformation.borderSizeLerpTarget;
/* 299 */     this.borderSafeZone = worldInformation.borderSafeZone;
/* 300 */     this.borderDamagePerBlock = worldInformation.borderDamagePerBlock;
/* 301 */     this.borderWarningTime = worldInformation.borderWarningTime;
/* 302 */     this.borderWarningDistance = worldInformation.borderWarningDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBTTagCompound() {
/* 310 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 311 */     updateTagCompound(nbttagcompound, this.playerTag);
/* 312 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound cloneNBTCompound(NBTTagCompound nbt) {
/* 320 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 321 */     updateTagCompound(nbttagcompound, nbt);
/* 322 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt) {
/* 327 */     nbt.setLong("RandomSeed", this.randomSeed);
/* 328 */     nbt.setString("generatorName", this.terrainType.getWorldTypeName());
/* 329 */     nbt.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
/* 330 */     nbt.setString("generatorOptions", this.generatorOptions);
/* 331 */     nbt.setInteger("GameType", this.theGameType.getID());
/* 332 */     nbt.setBoolean("MapFeatures", this.mapFeaturesEnabled);
/* 333 */     nbt.setInteger("SpawnX", this.spawnX);
/* 334 */     nbt.setInteger("SpawnY", this.spawnY);
/* 335 */     nbt.setInteger("SpawnZ", this.spawnZ);
/* 336 */     nbt.setLong("Time", this.totalTime);
/* 337 */     nbt.setLong("DayTime", this.worldTime);
/* 338 */     nbt.setLong("SizeOnDisk", this.sizeOnDisk);
/* 339 */     nbt.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
/* 340 */     nbt.setString("LevelName", this.levelName);
/* 341 */     nbt.setInteger("version", this.saveVersion);
/* 342 */     nbt.setInteger("clearWeatherTime", this.cleanWeatherTime);
/* 343 */     nbt.setInteger("rainTime", this.rainTime);
/* 344 */     nbt.setBoolean("raining", this.raining);
/* 345 */     nbt.setInteger("thunderTime", this.thunderTime);
/* 346 */     nbt.setBoolean("thundering", this.thundering);
/* 347 */     nbt.setBoolean("hardcore", this.hardcore);
/* 348 */     nbt.setBoolean("allowCommands", this.allowCommands);
/* 349 */     nbt.setBoolean("initialized", this.initialized);
/* 350 */     nbt.setDouble("BorderCenterX", this.borderCenterX);
/* 351 */     nbt.setDouble("BorderCenterZ", this.borderCenterZ);
/* 352 */     nbt.setDouble("BorderSize", this.borderSize);
/* 353 */     nbt.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
/* 354 */     nbt.setDouble("BorderSafeZone", this.borderSafeZone);
/* 355 */     nbt.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
/* 356 */     nbt.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
/* 357 */     nbt.setDouble("BorderWarningBlocks", this.borderWarningDistance);
/* 358 */     nbt.setDouble("BorderWarningTime", this.borderWarningTime);
/*     */     
/* 360 */     if (this.difficulty != null)
/*     */     {
/* 362 */       nbt.setByte("Difficulty", (byte)this.difficulty.getDifficultyId());
/*     */     }
/*     */     
/* 365 */     nbt.setBoolean("DifficultyLocked", this.difficultyLocked);
/* 366 */     nbt.setTag("GameRules", (NBTBase)this.theGameRules.writeToNBT());
/*     */     
/* 368 */     if (playerNbt != null)
/*     */     {
/* 370 */       nbt.setTag("Player", (NBTBase)playerNbt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSeed() {
/* 379 */     return this.randomSeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnX() {
/* 387 */     return this.spawnX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnY() {
/* 395 */     return this.spawnY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnZ() {
/* 403 */     return this.spawnZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getWorldTotalTime() {
/* 408 */     return this.totalTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWorldTime() {
/* 416 */     return this.worldTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSizeOnDisk() {
/* 421 */     return this.sizeOnDisk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getPlayerNBTTagCompound() {
/* 429 */     return this.playerTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnX(int x) {
/* 437 */     this.spawnX = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnY(int y) {
/* 445 */     this.spawnY = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnZ(int z) {
/* 453 */     this.spawnZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldTotalTime(long time) {
/* 458 */     this.totalTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {
/* 466 */     if (Atmosphere.getInstance.isEnabled()) {
/* 467 */       this.worldTime = Atmosphere.getInstance.getTime();
/*     */     } else {
/* 469 */       this.worldTime = time;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpawn(BlockPos spawnPoint) {
/* 475 */     this.spawnX = spawnPoint.getX();
/* 476 */     this.spawnY = spawnPoint.getY();
/* 477 */     this.spawnZ = spawnPoint.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWorldName() {
/* 485 */     return this.levelName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldName(String worldName) {
/* 490 */     this.levelName = worldName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaveVersion() {
/* 498 */     return this.saveVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaveVersion(int version) {
/* 506 */     this.saveVersion = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastTimePlayed() {
/* 514 */     return this.lastTimePlayed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCleanWeatherTime() {
/* 519 */     return this.cleanWeatherTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCleanWeatherTime(int cleanWeatherTimeIn) {
/* 524 */     this.cleanWeatherTime = cleanWeatherTimeIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/* 532 */     return this.thundering;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThundering(boolean thunderingIn) {
/* 540 */     this.thundering = thunderingIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThunderTime() {
/* 548 */     return this.thunderTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThunderTime(int time) {
/* 556 */     this.thunderTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRaining() {
/* 564 */     return this.raining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRaining(boolean isRaining) {
/* 572 */     this.raining = isRaining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRainTime() {
/* 580 */     return this.rainTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRainTime(int time) {
/* 588 */     this.rainTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/* 596 */     return this.theGameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMapFeaturesEnabled() {
/* 604 */     return this.mapFeaturesEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMapFeaturesEnabled(boolean enabled) {
/* 609 */     this.mapFeaturesEnabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType type) {
/* 617 */     this.theGameType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcoreModeEnabled() {
/* 625 */     return this.hardcore;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHardcore(boolean hardcoreIn) {
/* 630 */     this.hardcore = hardcoreIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getTerrainType() {
/* 635 */     return this.terrainType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTerrainType(WorldType type) {
/* 640 */     this.terrainType = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGeneratorOptions() {
/* 645 */     return this.generatorOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areCommandsAllowed() {
/* 653 */     return this.allowCommands;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllowCommands(boolean allow) {
/* 658 */     this.allowCommands = allow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 666 */     return this.initialized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerInitialized(boolean initializedIn) {
/* 674 */     this.initialized = initializedIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameRules getGameRulesInstance() {
/* 682 */     return this.theGameRules;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderCenterX() {
/* 690 */     return this.borderCenterX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderCenterZ() {
/* 698 */     return this.borderCenterZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getBorderSize() {
/* 703 */     return this.borderSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderSize(double size) {
/* 711 */     this.borderSize = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getBorderLerpTime() {
/* 719 */     return this.borderSizeLerpTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderLerpTime(long time) {
/* 727 */     this.borderSizeLerpTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderLerpTarget() {
/* 735 */     return this.borderSizeLerpTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderLerpTarget(double lerpSize) {
/* 743 */     this.borderSizeLerpTarget = lerpSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getBorderCenterZ(double posZ) {
/* 751 */     this.borderCenterZ = posZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getBorderCenterX(double posX) {
/* 759 */     this.borderCenterX = posX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderSafeZone() {
/* 767 */     return this.borderSafeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderSafeZone(double amount) {
/* 775 */     this.borderSafeZone = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderDamagePerBlock() {
/* 783 */     return this.borderDamagePerBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderDamagePerBlock(double damage) {
/* 791 */     this.borderDamagePerBlock = damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBorderWarningDistance() {
/* 799 */     return this.borderWarningDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBorderWarningTime() {
/* 807 */     return this.borderWarningTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderWarningDistance(int amountOfBlocks) {
/* 815 */     this.borderWarningDistance = amountOfBlocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderWarningTime(int ticks) {
/* 823 */     this.borderWarningTime = ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 828 */     return this.difficulty;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficulty(EnumDifficulty newDifficulty) {
/* 833 */     this.difficulty = newDifficulty;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDifficultyLocked() {
/* 838 */     return this.difficultyLocked;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficultyLocked(boolean locked) {
/* 843 */     this.difficultyLocked = locked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToCrashReport(CrashReportCategory category) {
/* 851 */     category.addCrashSectionCallable("Level seed", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 854 */             return String.valueOf(WorldInfo.this.getSeed());
/*     */           }
/*     */         });
/* 857 */     category.addCrashSectionCallable("Level generator", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 860 */             return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(this.this$0.terrainType.getWorldTypeID()), this.this$0.terrainType.getWorldTypeName(), Integer.valueOf(this.this$0.terrainType.getGeneratorVersion()), Boolean.valueOf(this.this$0.mapFeaturesEnabled) });
/*     */           }
/*     */         });
/* 863 */     category.addCrashSectionCallable("Level generator options", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 866 */             return WorldInfo.this.generatorOptions;
/*     */           }
/*     */         });
/* 869 */     category.addCrashSectionCallable("Level spawn location", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 872 */             return CrashReportCategory.getCoordinateInfo(WorldInfo.this.spawnX, WorldInfo.this.spawnY, WorldInfo.this.spawnZ);
/*     */           }
/*     */         });
/* 875 */     category.addCrashSectionCallable("Level time", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 878 */             return String.format("%d game time, %d day time", new Object[] { Long.valueOf(this.this$0.totalTime), Long.valueOf(this.this$0.worldTime) });
/*     */           }
/*     */         });
/* 881 */     category.addCrashSectionCallable("Level dimension", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 884 */             return String.valueOf(WorldInfo.this.dimension);
/*     */           }
/*     */         });
/* 887 */     category.addCrashSectionCallable("Level storage version", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 890 */             String s = "Unknown?";
/*     */ 
/*     */             
/*     */             try {
/* 894 */               switch (WorldInfo.this.saveVersion) {
/*     */                 
/*     */                 case 19132:
/* 897 */                   s = "McRegion";
/*     */                   break;
/*     */                 
/*     */                 case 19133:
/* 901 */                   s = "Anvil";
/*     */                   break;
/*     */               } 
/* 904 */             } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 909 */             return String.format("0x%05X - %s", new Object[] { Integer.valueOf(this.this$0.saveVersion), s });
/*     */           }
/*     */         });
/* 912 */     category.addCrashSectionCallable("Level weather", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 915 */             return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(this.this$0.rainTime), Boolean.valueOf(this.this$0.raining), Integer.valueOf(this.this$0.thunderTime), Boolean.valueOf(this.this$0.thundering) });
/*     */           }
/*     */         });
/* 918 */     category.addCrashSectionCallable("Level game mode", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 921 */             return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { this.this$0.theGameType.getName(), Integer.valueOf(this.this$0.theGameType.getID()), Boolean.valueOf(this.this$0.hardcore), Boolean.valueOf(this.this$0.allowCommands) });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\WorldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */