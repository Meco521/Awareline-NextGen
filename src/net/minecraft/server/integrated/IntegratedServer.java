/*     */ package net.minecraft.server.integrated;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import net.minecraft.command.ServerCommandManager;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.profiler.PlayerUsageSnooper;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.Util;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IWorldAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldManager;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldServerMulti;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.demo.DemoWorldServer;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.optifine.ClearWater;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class IntegratedServer extends MinecraftServer {
/*  39 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final WorldSettings theWorldSettings;
/*     */   private boolean isGamePaused;
/*     */   private boolean isPublic;
/*     */   private ThreadLanServerPing lanServerPing;
/*  47 */   private long ticksSaveLast = 0L;
/*  48 */   public World difficultyUpdateWorld = null;
/*  49 */   public BlockPos difficultyUpdatePos = null;
/*  50 */   public DifficultyInstance difficultyLast = null;
/*     */ 
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn) {
/*  54 */     super(mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  55 */     this.mc = mcIn;
/*  56 */     this.theWorldSettings = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn, String folderName, String worldName, WorldSettings settings) {
/*  61 */     super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  62 */     setServerOwner(mcIn.getSession().getUsername());
/*  63 */     setFolderName(folderName);
/*  64 */     setWorldName(worldName);
/*  65 */     setDemo(mcIn.isDemo());
/*  66 */     canCreateBonusChest(settings.isBonusChestEnabled());
/*  67 */     setBuildLimit(256);
/*  68 */     setConfigManager(new IntegratedPlayerList(this));
/*  69 */     this.mc = mcIn;
/*  70 */     this.theWorldSettings = isDemo() ? DemoWorldServer.demoWorldSettings : settings;
/*  71 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(folderName, false);
/*  72 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/*  74 */     if (worldinfo != null) {
/*     */       
/*  76 */       NBTTagCompound nbttagcompound = worldinfo.getPlayerNBTTagCompound();
/*     */       
/*  78 */       if (nbttagcompound != null && nbttagcompound.hasKey("Dimension")) {
/*     */         
/*  80 */         int i = nbttagcompound.getInteger("Dimension");
/*  81 */         PacketThreadUtil.lastDimensionId = i;
/*  82 */         this.mc.loadingScreen.setLoadingProgress(-1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerCommandManager createNewCommandManager() {
/*  89 */     return new IntegratedServerCommandManager();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String worldNameIn2) {
/*  94 */     convertMapIfNeeded(saveName);
/*  95 */     boolean flag = Reflector.DimensionManager.exists();
/*     */     
/*  97 */     if (!flag) {
/*     */       
/*  99 */       this.worldServers = new WorldServer[3];
/* 100 */       this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*     */     } 
/*     */     
/* 103 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(saveName, true);
/* 104 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/* 105 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/* 107 */     if (worldinfo == null) {
/*     */       
/* 109 */       worldinfo = new WorldInfo(this.theWorldSettings, worldNameIn);
/*     */     }
/*     */     else {
/*     */       
/* 113 */       worldinfo.setWorldName(worldNameIn);
/*     */     } 
/*     */     
/* 116 */     if (flag) {
/*     */       
/* 118 */       WorldServer worldserver = isDemo() ? (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, 0, this.theProfiler)).init() : (WorldServer)(new WorldServer(this, isavehandler, worldinfo, 0, this.theProfiler)).init();
/* 119 */       worldserver.initialize(this.theWorldSettings);
/* 120 */       Integer[] ainteger = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
/* 121 */       Integer[] ainteger1 = ainteger;
/* 122 */       int i = ainteger.length;
/*     */       
/* 124 */       for (int j = 0; j < i; j++) {
/*     */         
/* 126 */         int k = ainteger1[j].intValue();
/* 127 */         WorldServer worldserver1 = (k == 0) ? worldserver : (WorldServer)(new WorldServerMulti(this, isavehandler, k, worldserver, this.theProfiler)).init();
/* 128 */         worldserver1.addWorldAccess((IWorldAccess)new WorldManager(this, worldserver1));
/*     */         
/* 130 */         if (!isSinglePlayer())
/*     */         {
/* 132 */           worldserver1.getWorldInfo().setGameType(getGameType());
/*     */         }
/*     */         
/* 135 */         if (Reflector.EventBus.exists())
/*     */         {
/* 137 */           Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { worldserver1 });
/*     */         }
/*     */       } 
/*     */       
/* 141 */       getConfigurationManager().setPlayerManager(new WorldServer[] { worldserver });
/*     */       
/* 143 */       if (worldserver.getWorldInfo().getDifficulty() == null)
/*     */       {
/* 145 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 150 */       for (int l = 0; l < this.worldServers.length; l++) {
/*     */         
/* 152 */         int i1 = 0;
/*     */         
/* 154 */         if (l == 1)
/*     */         {
/* 156 */           i1 = -1;
/*     */         }
/*     */         
/* 159 */         if (l == 2)
/*     */         {
/* 161 */           i1 = 1;
/*     */         }
/*     */         
/* 164 */         if (l == 0) {
/*     */           
/* 166 */           if (isDemo()) {
/*     */             
/* 168 */             this.worldServers[l] = (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
/*     */           }
/*     */           else {
/*     */             
/* 172 */             this.worldServers[l] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
/*     */           } 
/*     */           
/* 175 */           this.worldServers[l].initialize(this.theWorldSettings);
/*     */         }
/*     */         else {
/*     */           
/* 179 */           this.worldServers[l] = (WorldServer)(new WorldServerMulti(this, isavehandler, i1, this.worldServers[0], this.theProfiler)).init();
/*     */         } 
/*     */         
/* 182 */         this.worldServers[l].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[l]));
/*     */       } 
/*     */       
/* 185 */       getConfigurationManager().setPlayerManager(this.worldServers);
/*     */       
/* 187 */       if (this.worldServers[0].getWorldInfo().getDifficulty() == null)
/*     */       {
/* 189 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     } 
/*     */     
/* 193 */     initialWorldChunkLoad();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean startServer() {
/* 200 */     logger.info("Starting integrated minecraft server version 1.9");
/* 201 */     setOnlineMode(true);
/* 202 */     setCanSpawnAnimals(true);
/* 203 */     setCanSpawnNPCs(true);
/* 204 */     setAllowPvp(true);
/* 205 */     setAllowFlight(true);
/* 206 */     logger.info("Generating keypair");
/* 207 */     setKeyPair(CryptManager.generateKeyPair());
/*     */     
/* 209 */     if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
/*     */       
/* 211 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 213 */       if (!Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this }))
/*     */       {
/* 215 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 219 */     loadAllWorlds(getFolderName(), getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
/* 220 */     setMOTD(getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
/*     */     
/* 222 */     if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
/*     */       
/* 224 */       Object object1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 226 */       if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == boolean.class)
/*     */       {
/* 228 */         return Reflector.callBoolean(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */       }
/*     */       
/* 231 */       Reflector.callVoid(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */     } 
/*     */     
/* 234 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 242 */     onTick();
/* 243 */     boolean flag = this.isGamePaused;
/* 244 */     this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
/*     */     
/* 246 */     if (!flag && this.isGamePaused) {
/*     */       
/* 248 */       logger.info("Saving and pausing game...");
/* 249 */       getConfigurationManager().saveAllPlayerData();
/* 250 */       saveAllWorlds(false);
/*     */     } 
/*     */     
/* 253 */     if (this.isGamePaused) {
/*     */       
/* 255 */       synchronized (this.futureTaskQueue)
/*     */       {
/* 257 */         while (!this.futureTaskQueue.isEmpty())
/*     */         {
/* 259 */           Util.runTask(this.futureTaskQueue.poll(), logger);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 265 */       super.tick();
/*     */       
/* 267 */       if (this.mc.gameSettings.renderDistanceChunks != getConfigurationManager().getViewDistance()) {
/*     */         
/* 269 */         logger.info("Changing view distance to {}, from {}", new Object[] { Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(getConfigurationManager().getViewDistance()) });
/* 270 */         getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
/*     */       } 
/*     */       
/* 273 */       if (this.mc.theWorld != null) {
/*     */         
/* 275 */         WorldInfo worldinfo1 = this.worldServers[0].getWorldInfo();
/* 276 */         WorldInfo worldinfo = this.mc.theWorld.getWorldInfo();
/*     */         
/* 278 */         if (!worldinfo1.isDifficultyLocked() && worldinfo.getDifficulty() != worldinfo1.getDifficulty()) {
/*     */           
/* 280 */           logger.info("Changing difficulty to {}, from {}", new Object[] { worldinfo.getDifficulty(), worldinfo1.getDifficulty() });
/* 281 */           setDifficultyForAllWorlds(worldinfo.getDifficulty());
/*     */         }
/* 283 */         else if (worldinfo.isDifficultyLocked() && !worldinfo1.isDifficultyLocked()) {
/*     */           
/* 285 */           logger.info("Locking difficulty to {}", new Object[] { worldinfo.getDifficulty() });
/*     */           
/* 287 */           for (WorldServer worldserver : this.worldServers) {
/*     */             
/* 289 */             if (worldserver != null)
/*     */             {
/* 291 */               worldserver.getWorldInfo().setDifficultyLocked(true);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canStructuresSpawn() {
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/* 306 */     return this.theWorldSettings.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 314 */     return (this.mc.theWorld == null) ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 322 */     return this.theWorldSettings.getHardcoreEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldBroadcastRconToOps() {
/* 330 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldBroadcastConsoleToOps() {
/* 338 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllWorlds(boolean dontLog) {
/* 346 */     if (dontLog) {
/*     */       
/* 348 */       int i = getTickCounter();
/* 349 */       int j = this.mc.gameSettings.ofAutoSaveTicks;
/*     */       
/* 351 */       if (i < this.ticksSaveLast + j) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 356 */       this.ticksSaveLast = i;
/*     */     } 
/*     */     
/* 359 */     super.saveAllWorlds(dontLog);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDataDirectory() {
/* 364 */     return this.mc.mcDataDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 369 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseNativeTransport() {
/* 378 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalTick(CrashReport report) {
/* 386 */     this.mc.crashed(report);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/* 394 */     report = super.addServerInfoToCrashReport(report);
/* 395 */     report.getCategory().addCrashSectionCallable("Type", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 398 */             return "Integrated Server (map_client.txt)";
/*     */           }
/*     */         });
/* 401 */     report.getCategory().addCrashSectionCallable("Is Modded", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 404 */             String s = ClientBrandRetriever.getClientModName();
/*     */             
/* 406 */             if (!s.equals("vanilla"))
/*     */             {
/* 408 */               return "Definitely; Client brand changed to '" + s + "'";
/*     */             }
/*     */ 
/*     */             
/* 412 */             s = IntegratedServer.this.getServerModName();
/* 413 */             return !s.equals("vanilla") ? ("Definitely; Server brand changed to '" + s + "'") : ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
/*     */           }
/*     */         });
/*     */     
/* 417 */     return report;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 422 */     super.setDifficultyForAllWorlds(difficulty);
/*     */     
/* 424 */     if (this.mc.theWorld != null)
/*     */     {
/* 426 */       this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 432 */     super.addServerStatsToSnooper(playerSnooper);
/* 433 */     playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSnooperEnabled() {
/* 441 */     return Minecraft.getMinecraft().isSnooperEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String shareToLAN(WorldSettings.GameType type, boolean allowCheats) {
/*     */     try {
/* 451 */       int i = -1;
/*     */ 
/*     */       
/*     */       try {
/* 455 */         i = HttpUtil.getSuitableLanPort();
/*     */       }
/* 457 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 462 */       if (i <= 0)
/*     */       {
/* 464 */         i = 25564;
/*     */       }
/*     */       
/* 467 */       getNetworkSystem().addLanEndpoint((InetAddress)null, i);
/* 468 */       logger.info("Started on " + i);
/* 469 */       this.isPublic = true;
/* 470 */       this.lanServerPing = new ThreadLanServerPing(getMOTD(), String.valueOf(i));
/* 471 */       this.lanServerPing.start();
/* 472 */       getConfigurationManager().setGameType(type);
/* 473 */       getConfigurationManager().setCommandsAllowedForAll(allowCheats);
/* 474 */       return String.valueOf(i);
/*     */     }
/* 476 */     catch (IOException var6) {
/*     */       
/* 478 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopServer() {
/* 487 */     super.stopServer();
/*     */     
/* 489 */     if (this.lanServerPing != null) {
/*     */       
/* 491 */       this.lanServerPing.interrupt();
/* 492 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateShutdown() {
/* 501 */     if (!Reflector.MinecraftForge.exists() || isServerRunning())
/*     */     {
/* 503 */       Futures.getUnchecked((Future)addScheduledTask(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 507 */                 for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getConfigurationManager().getPlayerList()))
/*     */                 {
/* 509 */                   IntegratedServer.this.getConfigurationManager().playerLoggedOut(entityplayermp);
/*     */                 }
/*     */               }
/*     */             }));
/*     */     }
/*     */     
/* 515 */     super.initiateShutdown();
/*     */     
/* 517 */     if (this.lanServerPing != null) {
/*     */       
/* 519 */       this.lanServerPing.interrupt();
/* 520 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStaticInstance() {
/* 526 */     setInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPublic() {
/* 534 */     return this.isPublic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType gameMode) {
/* 542 */     getConfigurationManager().setGameType(gameMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 550 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOpPermissionLevel() {
/* 555 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTick() {
/* 560 */     for (WorldServer worldserver : Arrays.<WorldServer>asList(this.worldServers))
/*     */     {
/* 562 */       onTick(worldserver);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DifficultyInstance getDifficultyAsync(World p_getDifficultyAsync_1_, BlockPos p_getDifficultyAsync_2_) {
/* 568 */     this.difficultyUpdateWorld = p_getDifficultyAsync_1_;
/* 569 */     this.difficultyUpdatePos = p_getDifficultyAsync_2_;
/* 570 */     return this.difficultyLast;
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTick(WorldServer p_onTick_1_) {
/* 575 */     if (!Config.isTimeDefault())
/*     */     {
/* 577 */       fixWorldTime(p_onTick_1_);
/*     */     }
/*     */     
/* 580 */     if (!Config.isWeatherEnabled())
/*     */     {
/* 582 */       fixWorldWeather(p_onTick_1_);
/*     */     }
/*     */     
/* 585 */     if (Config.waterOpacityChanged) {
/*     */       
/* 587 */       Config.waterOpacityChanged = false;
/* 588 */       ClearWater.updateWaterOpacity(Config.getGameSettings(), (World)p_onTick_1_);
/*     */     } 
/*     */     
/* 591 */     if (this.difficultyUpdateWorld == p_onTick_1_ && this.difficultyUpdatePos != null) {
/*     */       
/* 593 */       this.difficultyLast = p_onTick_1_.getDifficultyForLocation(this.difficultyUpdatePos);
/* 594 */       this.difficultyUpdateWorld = null;
/* 595 */       this.difficultyUpdatePos = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixWorldWeather(WorldServer p_fixWorldWeather_1_) {
/* 601 */     WorldInfo worldinfo = p_fixWorldWeather_1_.getWorldInfo();
/*     */     
/* 603 */     if (worldinfo.isRaining() || worldinfo.isThundering()) {
/*     */       
/* 605 */       worldinfo.setRainTime(0);
/* 606 */       worldinfo.setRaining(false);
/* 607 */       p_fixWorldWeather_1_.setRainStrength(0.0F);
/* 608 */       worldinfo.setThunderTime(0);
/* 609 */       worldinfo.setThundering(false);
/* 610 */       p_fixWorldWeather_1_.setThunderStrength(0.0F);
/* 611 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(2, 0.0F));
/* 612 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(7, 0.0F));
/* 613 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(8, 0.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixWorldTime(WorldServer p_fixWorldTime_1_) {
/* 619 */     WorldInfo worldinfo = p_fixWorldTime_1_.getWorldInfo();
/*     */     
/* 621 */     if (worldinfo.getGameType().getID() == 1) {
/*     */       
/* 623 */       long i = p_fixWorldTime_1_.getWorldTime();
/* 624 */       long j = i % 24000L;
/*     */       
/* 626 */       if (Config.isTimeDayOnly()) {
/*     */         
/* 628 */         if (j <= 1000L)
/*     */         {
/* 630 */           p_fixWorldTime_1_.setWorldTime(i - j + 1001L);
/*     */         }
/*     */         
/* 633 */         if (j >= 11000L)
/*     */         {
/* 635 */           p_fixWorldTime_1_.setWorldTime(i - j + 24001L);
/*     */         }
/*     */       } 
/*     */       
/* 639 */       if (Config.isTimeNightOnly()) {
/*     */         
/* 641 */         if (j <= 14000L)
/*     */         {
/* 643 */           p_fixWorldTime_1_.setWorldTime(i - j + 14001L);
/*     */         }
/*     */         
/* 646 */         if (j >= 22000L)
/*     */         {
/* 648 */           p_fixWorldTime_1_.setWorldTime(i - j + 24000L + 14001L);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\integrated\IntegratedServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */