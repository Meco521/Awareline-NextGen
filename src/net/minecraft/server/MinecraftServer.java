/*      */ package net.minecraft.server;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.net.Proxy;
/*      */ import java.security.KeyPair;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.command.ICommandManager;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.ServerCommandManager;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.network.NetworkSystem;
/*      */ import net.minecraft.network.ServerStatusResponse;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.management.PlayerProfileCache;
/*      */ import net.minecraft.server.management.ServerConfigurationManager;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IProgressUpdate;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.MinecraftException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldServerMulti;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.demo.DemoWorldServer;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class MinecraftServer implements Runnable, ICommandSender, IThreadListener, IPlayerUsage {
/*   58 */   static final Logger logger = LogManager.getLogger();
/*   59 */   public static final File USER_CACHE_FILE = new File("usercache.json");
/*      */ 
/*      */   
/*      */   private static MinecraftServer mcServer;
/*      */   
/*      */   private final ISaveFormat anvilConverterForAnvilFile;
/*      */   
/*   66 */   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
/*      */   private final File anvilFile;
/*   68 */   private final List<ITickable> playersOnline = Lists.newArrayList();
/*      */   protected final ICommandManager commandManager;
/*   70 */   public final Profiler theProfiler = new Profiler();
/*      */   private final NetworkSystem networkSystem;
/*   72 */   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
/*   73 */   private final Random random = new Random();
/*      */ 
/*      */   
/*   76 */   private final int serverPort = -1;
/*      */ 
/*      */   
/*      */   public WorldServer[] worldServers;
/*      */ 
/*      */   
/*      */   ServerConfigurationManager serverConfigManager;
/*      */ 
/*      */   
/*      */   private boolean serverRunning = true;
/*      */ 
/*      */   
/*      */   private boolean serverStopped;
/*      */ 
/*      */   
/*      */   private int tickCounter;
/*      */ 
/*      */   
/*      */   protected final Proxy serverProxy;
/*      */ 
/*      */   
/*      */   public String currentTask;
/*      */ 
/*      */   
/*      */   public int percentDone;
/*      */ 
/*      */   
/*      */   private boolean onlineMode;
/*      */ 
/*      */   
/*      */   private boolean canSpawnAnimals;
/*      */ 
/*      */   
/*      */   private boolean canSpawnNPCs;
/*      */ 
/*      */   
/*      */   private boolean pvpEnabled;
/*      */ 
/*      */   
/*      */   private boolean allowFlight;
/*      */ 
/*      */   
/*      */   private String motd;
/*      */   
/*      */   private int buildLimit;
/*      */   
/*  122 */   private int maxPlayerIdleMinutes = 0;
/*  123 */   public final long[] tickTimeArray = new long[100];
/*      */ 
/*      */   
/*      */   public long[][] timeOfLastDimensionTick;
/*      */   
/*      */   private KeyPair serverKeyPair;
/*      */   
/*      */   private String serverOwner;
/*      */   
/*      */   private String folderName;
/*      */   
/*      */   private String worldName;
/*      */   
/*      */   private boolean isDemo;
/*      */   
/*      */   private boolean enableBonusChest;
/*      */   
/*      */   private boolean worldIsBeingDeleted;
/*      */   
/*  142 */   private String resourcePackUrl = "";
/*  143 */   private String resourcePackHash = "";
/*      */   
/*      */   private boolean serverIsRunning;
/*      */   
/*      */   private long timeOfLastWarning;
/*      */   
/*      */   private String userMessage;
/*      */   
/*      */   private boolean startProfiling;
/*      */   private boolean isGamemodeForced;
/*      */   private final YggdrasilAuthenticationService authService;
/*      */   private final MinecraftSessionService sessionService;
/*  155 */   private long nanoTimeSinceStatusRefresh = 0L;
/*      */   private final GameProfileRepository profileRepo;
/*      */   private final PlayerProfileCache profileCache;
/*  158 */   protected final Queue<FutureTask<?>> futureTaskQueue = Queues.newArrayDeque();
/*      */   private Thread serverThread;
/*  160 */   private long currentTime = getCurrentTimeMillis();
/*      */ 
/*      */   
/*      */   public MinecraftServer(Proxy proxy, File workDir) {
/*  164 */     this.serverProxy = proxy;
/*  165 */     mcServer = this;
/*  166 */     this.anvilFile = null;
/*  167 */     this.networkSystem = null;
/*  168 */     this.profileCache = new PlayerProfileCache(this, workDir);
/*  169 */     this.commandManager = null;
/*  170 */     this.anvilConverterForAnvilFile = null;
/*  171 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  172 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  173 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftServer(File workDir, Proxy proxy, File profileCacheDir) {
/*  178 */     this.serverProxy = proxy;
/*  179 */     mcServer = this;
/*  180 */     this.anvilFile = workDir;
/*  181 */     this.networkSystem = new NetworkSystem(this);
/*  182 */     this.profileCache = new PlayerProfileCache(this, profileCacheDir);
/*  183 */     this.commandManager = (ICommandManager)createNewCommandManager();
/*  184 */     this.anvilConverterForAnvilFile = (ISaveFormat)new AnvilSaveConverter(workDir);
/*  185 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  186 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  187 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ServerCommandManager createNewCommandManager() {
/*  192 */     return new ServerCommandManager();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract boolean startServer();
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertMapIfNeeded(String worldNameIn) {
/*  202 */     if (this.anvilConverterForAnvilFile.isOldMapFormat(worldNameIn)) {
/*      */       
/*  204 */       logger.info("Converting map!");
/*  205 */       this.userMessage = "menu.convertingLevel";
/*  206 */       this.anvilConverterForAnvilFile.convertMapFormat(worldNameIn, new IProgressUpdate()
/*      */           {
/*  208 */             private long startTime = System.currentTimeMillis();
/*      */ 
/*      */             
/*      */             public void displaySavingString(String message) {}
/*      */ 
/*      */             
/*      */             public void resetProgressAndMessage(String message) {}
/*      */             
/*      */             public void setLoadingProgress(int progress) {
/*  217 */               if (System.currentTimeMillis() - this.startTime >= 1000L) {
/*      */                 
/*  219 */                 this.startTime = System.currentTimeMillis();
/*  220 */                 MinecraftServer.logger.info("Converting... " + progress + "%");
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public void setDoneWorking() {}
/*      */ 
/*      */ 
/*      */             
/*      */             public void displayLoadingString(String message) {}
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void setUserMessage(String message) {
/*  238 */     this.userMessage = message;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized String getUserMessage() {
/*  243 */     return this.userMessage;
/*      */   }
/*      */   
/*      */   protected void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String worldNameIn2) {
/*      */     WorldSettings worldsettings;
/*  248 */     convertMapIfNeeded(saveName);
/*  249 */     this.userMessage = "menu.loadingLevel";
/*  250 */     this.worldServers = new WorldServer[3];
/*  251 */     this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*  252 */     ISaveHandler isavehandler = this.anvilConverterForAnvilFile.getSaveLoader(saveName, true);
/*  253 */     setResourcePackFromWorld(this.folderName, isavehandler);
/*  254 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */ 
/*      */     
/*  257 */     if (worldinfo == null) {
/*      */       
/*  259 */       if (this.isDemo) {
/*      */         
/*  261 */         worldsettings = DemoWorldServer.demoWorldSettings;
/*      */       }
/*      */       else {
/*      */         
/*  265 */         worldsettings = new WorldSettings(seed, getGameType(), canStructuresSpawn(), isHardcore(), type);
/*  266 */         worldsettings.setWorldName(worldNameIn2);
/*      */         
/*  268 */         if (this.enableBonusChest)
/*      */         {
/*  270 */           worldsettings.enableBonusChest();
/*      */         }
/*      */       } 
/*      */       
/*  274 */       worldinfo = new WorldInfo(worldsettings, worldNameIn);
/*      */     }
/*      */     else {
/*      */       
/*  278 */       worldinfo.setWorldName(worldNameIn);
/*  279 */       worldsettings = new WorldSettings(worldinfo);
/*      */     } 
/*      */     
/*  282 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/*  284 */       int j = 0;
/*      */       
/*  286 */       if (i == 1)
/*      */       {
/*  288 */         j = -1;
/*      */       }
/*      */       
/*  291 */       if (i == 2)
/*      */       {
/*  293 */         j = 1;
/*      */       }
/*      */       
/*  296 */       if (i == 0) {
/*      */         
/*  298 */         if (this.isDemo) {
/*      */           
/*  300 */           this.worldServers[i] = (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         }
/*      */         else {
/*      */           
/*  304 */           this.worldServers[i] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         } 
/*      */         
/*  307 */         this.worldServers[i].initialize(worldsettings);
/*      */       }
/*      */       else {
/*      */         
/*  311 */         this.worldServers[i] = (WorldServer)(new WorldServerMulti(this, isavehandler, j, this.worldServers[0], this.theProfiler)).init();
/*      */       } 
/*      */       
/*  314 */       this.worldServers[i].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[i]));
/*      */       
/*  316 */       if (!isSinglePlayer())
/*      */       {
/*  318 */         this.worldServers[i].getWorldInfo().setGameType(getGameType());
/*      */       }
/*      */     } 
/*      */     
/*  322 */     this.serverConfigManager.setPlayerManager(this.worldServers);
/*  323 */     setDifficultyForAllWorlds(getDifficulty());
/*  324 */     initialWorldChunkLoad();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initialWorldChunkLoad() {
/*  329 */     int i = 16;
/*  330 */     int j = 4;
/*  331 */     int k = 192;
/*  332 */     int l = 625;
/*  333 */     int i1 = 0;
/*  334 */     this.userMessage = "menu.generatingTerrain";
/*  335 */     int j1 = 0;
/*  336 */     logger.info("Preparing start region for level " + j1);
/*  337 */     WorldServer worldserver = this.worldServers[j1];
/*  338 */     BlockPos blockpos = worldserver.getSpawnPoint();
/*  339 */     long k1 = getCurrentTimeMillis();
/*      */     
/*  341 */     for (int l1 = -192; l1 <= 192 && this.serverRunning; l1 += 16) {
/*      */       
/*  343 */       for (int i2 = -192; i2 <= 192 && this.serverRunning; i2 += 16) {
/*      */         
/*  345 */         long j2 = getCurrentTimeMillis();
/*      */         
/*  347 */         if (j2 - k1 > 1000L) {
/*      */           
/*  349 */           outputPercentRemaining("Preparing spawn area", i1 * 100 / 625);
/*  350 */           k1 = j2;
/*      */         } 
/*      */         
/*  353 */         i1++;
/*  354 */         worldserver.theChunkProviderServer.loadChunk(blockpos.getX() + l1 >> 4, blockpos.getZ() + i2 >> 4);
/*      */       } 
/*      */     } 
/*      */     
/*  358 */     clearCurrentTask();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setResourcePackFromWorld(String worldNameIn, ISaveHandler saveHandlerIn) {
/*  363 */     File file1 = new File(saveHandlerIn.getWorldDirectory(), "resources.zip");
/*      */     
/*  365 */     if (file1.isFile())
/*      */     {
/*  367 */       setResourcePack("level://" + worldNameIn + "/" + file1.getName(), "");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean canStructuresSpawn();
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract WorldSettings.GameType getGameType();
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract EnumDifficulty getDifficulty();
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isHardcore();
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getOpPermissionLevel();
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean shouldBroadcastRconToOps();
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean shouldBroadcastConsoleToOps();
/*      */ 
/*      */ 
/*      */   
/*      */   protected void outputPercentRemaining(String message, int percent) {
/*  402 */     this.currentTask = message;
/*  403 */     this.percentDone = percent;
/*  404 */     logger.info(message + ": " + percent + "%");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void clearCurrentTask() {
/*  412 */     this.currentTask = null;
/*  413 */     this.percentDone = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveAllWorlds(boolean dontLog) {
/*  421 */     if (!this.worldIsBeingDeleted)
/*      */     {
/*  423 */       for (WorldServer worldserver : this.worldServers) {
/*      */         
/*  425 */         if (worldserver != null) {
/*      */           
/*  427 */           if (!dontLog)
/*      */           {
/*  429 */             logger.info("Saving chunks for level '" + worldserver.getWorldInfo().getWorldName() + "'/" + worldserver.provider.getDimensionName());
/*      */           }
/*      */ 
/*      */           
/*      */           try {
/*  434 */             worldserver.saveAllChunks(true, (IProgressUpdate)null);
/*      */           }
/*  436 */           catch (MinecraftException minecraftexception) {
/*      */             
/*  438 */             logger.warn(minecraftexception.getMessage());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopServer() {
/*  450 */     if (!this.worldIsBeingDeleted) {
/*      */       
/*  452 */       logger.info("Stopping server");
/*      */       
/*  454 */       if (this.networkSystem != null)
/*      */       {
/*  456 */         this.networkSystem.terminateEndpoints();
/*      */       }
/*      */       
/*  459 */       if (this.serverConfigManager != null) {
/*      */         
/*  461 */         logger.info("Saving players");
/*  462 */         this.serverConfigManager.saveAllPlayerData();
/*  463 */         this.serverConfigManager.removeAllPlayers();
/*      */       } 
/*      */       
/*  466 */       if (this.worldServers != null) {
/*      */         
/*  468 */         logger.info("Saving worlds");
/*  469 */         saveAllWorlds(false);
/*      */         
/*  471 */         for (int i = 0; i < this.worldServers.length; i++) {
/*      */           
/*  473 */           WorldServer worldserver = this.worldServers[i];
/*  474 */           worldserver.flush();
/*      */         } 
/*      */       } 
/*      */       
/*  478 */       if (this.usageSnooper.isSnooperRunning())
/*      */       {
/*  480 */         this.usageSnooper.stopSnooper();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerRunning() {
/*  487 */     return this.serverRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initiateShutdown() {
/*  495 */     this.serverRunning = false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setInstance() {
/*  500 */     mcServer = this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*      */     try {
/*  507 */       if (startServer()) {
/*      */         
/*  509 */         this.currentTime = getCurrentTimeMillis();
/*  510 */         long i = 0L;
/*  511 */         this.statusResponse.setServerDescription((IChatComponent)new ChatComponentText(this.motd));
/*  512 */         this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8.9", 47));
/*  513 */         addFaviconToStatusResponse(this.statusResponse);
/*      */         
/*  515 */         while (this.serverRunning)
/*      */         {
/*  517 */           long k = getCurrentTimeMillis();
/*  518 */           long j = k - this.currentTime;
/*      */           
/*  520 */           if (j > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
/*      */             
/*  522 */             logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { Long.valueOf(j), Long.valueOf(j / 50L) });
/*  523 */             j = 2000L;
/*  524 */             this.timeOfLastWarning = this.currentTime;
/*      */           } 
/*      */           
/*  527 */           if (j < 0L) {
/*      */             
/*  529 */             logger.warn("Time ran backwards! Did the system time change?");
/*  530 */             j = 0L;
/*      */           } 
/*      */           
/*  533 */           i += j;
/*  534 */           this.currentTime = k;
/*      */           
/*  536 */           if (this.worldServers[0].areAllPlayersAsleep()) {
/*      */             
/*  538 */             tick();
/*  539 */             i = 0L;
/*      */           }
/*      */           else {
/*      */             
/*  543 */             while (i > 50L) {
/*      */               
/*  545 */               i -= 50L;
/*  546 */               tick();
/*      */             } 
/*      */           } 
/*      */           
/*  550 */           Thread.sleep(Math.max(1L, 50L - i));
/*  551 */           this.serverIsRunning = true;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  556 */         finalTick((CrashReport)null);
/*      */       }
/*      */     
/*  559 */     } catch (Throwable throwable1) {
/*      */       
/*  561 */       logger.error("Encountered an unexpected exception", throwable1);
/*  562 */       CrashReport crashreport = null;
/*      */       
/*  564 */       if (throwable1 instanceof ReportedException) {
/*      */         
/*  566 */         crashreport = addServerInfoToCrashReport(((ReportedException)throwable1).getCrashReport());
/*      */       }
/*      */       else {
/*      */         
/*  570 */         crashreport = addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", throwable1));
/*      */       } 
/*      */       
/*  573 */       File file1 = new File(new File(getDataDirectory(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
/*      */       
/*  575 */       if (crashreport.saveToFile(file1)) {
/*      */         
/*  577 */         logger.error("This crash report has been saved to: " + file1.getAbsolutePath());
/*      */       }
/*      */       else {
/*      */         
/*  581 */         logger.error("We were unable to save this crash report to disk.");
/*      */       } 
/*      */       
/*  584 */       finalTick(crashreport);
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  590 */         this.serverStopped = true;
/*  591 */         stopServer();
/*      */       }
/*  593 */       catch (Throwable throwable) {
/*      */         
/*  595 */         logger.error("Exception stopping the server", throwable);
/*      */       }
/*      */       finally {
/*      */         
/*  599 */         systemExitNow();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addFaviconToStatusResponse(ServerStatusResponse response) {
/*  606 */     File file1 = getFile("server-icon.png");
/*      */     
/*  608 */     if (file1.isFile()) {
/*      */       
/*  610 */       ByteBuf bytebuf = Unpooled.buffer();
/*      */ 
/*      */       
/*      */       try {
/*  614 */         BufferedImage bufferedimage = ImageIO.read(file1);
/*  615 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/*  616 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*  617 */         ImageIO.write(bufferedimage, "PNG", (OutputStream)new ByteBufOutputStream(bytebuf));
/*  618 */         ByteBuf bytebuf1 = Base64.encode(bytebuf);
/*  619 */         response.setFavicon("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
/*      */       }
/*  621 */       catch (Exception exception) {
/*      */         
/*  623 */         logger.error("Couldn't load server icon", exception);
/*      */       }
/*      */       finally {
/*      */         
/*  627 */         bytebuf.release();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public File getDataDirectory() {
/*  634 */     return new File(".");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalTick(CrashReport report) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void systemExitNow() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  656 */     long i = System.nanoTime();
/*  657 */     this.tickCounter++;
/*      */     
/*  659 */     if (this.startProfiling) {
/*      */       
/*  661 */       this.startProfiling = false;
/*  662 */       this.theProfiler.profilingEnabled = true;
/*  663 */       this.theProfiler.clearProfiling();
/*      */     } 
/*      */     
/*  666 */     this.theProfiler.startSection("root");
/*  667 */     updateTimeLightAndEntities();
/*      */     
/*  669 */     if (i - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
/*      */       
/*  671 */       this.nanoTimeSinceStatusRefresh = i;
/*  672 */       this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(getMaxPlayers(), getCurrentPlayerCount()));
/*  673 */       GameProfile[] agameprofile = new GameProfile[Math.min(getCurrentPlayerCount(), 12)];
/*  674 */       int j = MathHelper.getRandomIntegerInRange(this.random, 0, getCurrentPlayerCount() - agameprofile.length);
/*      */       
/*  676 */       for (int k = 0; k < agameprofile.length; k++)
/*      */       {
/*  678 */         agameprofile[k] = ((EntityPlayerMP)this.serverConfigManager.getPlayerList().get(j + k)).getGameProfile();
/*      */       }
/*      */       
/*  681 */       Collections.shuffle(Arrays.asList((Object[])agameprofile));
/*  682 */       this.statusResponse.getPlayerCountData().setPlayers(agameprofile);
/*      */     } 
/*      */     
/*  685 */     if (this.tickCounter % 900 == 0) {
/*      */       
/*  687 */       this.theProfiler.startSection("save");
/*  688 */       this.serverConfigManager.saveAllPlayerData();
/*  689 */       saveAllWorlds(true);
/*  690 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  693 */     this.theProfiler.startSection("tallying");
/*  694 */     this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - i;
/*  695 */     this.theProfiler.endSection();
/*  696 */     this.theProfiler.startSection("snooper");
/*      */     
/*  698 */     if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100)
/*      */     {
/*  700 */       this.usageSnooper.startSnooper();
/*      */     }
/*      */     
/*  703 */     if (this.tickCounter % 6000 == 0)
/*      */     {
/*  705 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */     }
/*      */     
/*  708 */     this.theProfiler.endSection();
/*  709 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTimeLightAndEntities() {
/*  714 */     this.theProfiler.startSection("jobs");
/*      */     
/*  716 */     synchronized (this.futureTaskQueue) {
/*      */       
/*  718 */       while (!this.futureTaskQueue.isEmpty())
/*      */       {
/*  720 */         Util.runTask(this.futureTaskQueue.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/*  724 */     this.theProfiler.endStartSection("levels");
/*      */     
/*  726 */     for (int j = 0; j < this.worldServers.length; j++) {
/*      */       
/*  728 */       long i = System.nanoTime();
/*      */       
/*  730 */       if (j == 0 || getAllowNether()) {
/*      */         
/*  732 */         WorldServer worldserver = this.worldServers[j];
/*  733 */         this.theProfiler.startSection(worldserver.getWorldInfo().getWorldName());
/*      */         
/*  735 */         if (this.tickCounter % 20 == 0) {
/*      */           
/*  737 */           this.theProfiler.startSection("timeSync");
/*  738 */           this.serverConfigManager.sendPacketToAllPlayersInDimension((Packet)new S03PacketTimeUpdate(worldserver.getTotalWorldTime(), worldserver.getWorldTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")), worldserver.provider.getDimensionId());
/*  739 */           this.theProfiler.endSection();
/*      */         } 
/*      */         
/*  742 */         this.theProfiler.startSection("tick");
/*      */ 
/*      */         
/*      */         try {
/*  746 */           worldserver.tick();
/*      */         }
/*  748 */         catch (Throwable throwable1) {
/*      */           
/*  750 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Exception ticking world");
/*  751 */           worldserver.addWorldInfoToCrashReport(crashreport);
/*  752 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  757 */           worldserver.updateEntities();
/*      */         }
/*  759 */         catch (Throwable throwable) {
/*      */           
/*  761 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Exception ticking world entities");
/*  762 */           worldserver.addWorldInfoToCrashReport(crashreport1);
/*  763 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */         
/*  766 */         this.theProfiler.endSection();
/*  767 */         this.theProfiler.startSection("tracker");
/*  768 */         worldserver.getEntityTracker().updateTrackedEntities();
/*  769 */         this.theProfiler.endSection();
/*  770 */         this.theProfiler.endSection();
/*      */       } 
/*      */       
/*  773 */       this.timeOfLastDimensionTick[j][this.tickCounter % 100] = System.nanoTime() - i;
/*      */     } 
/*      */     
/*  776 */     this.theProfiler.endStartSection("connection");
/*  777 */     this.networkSystem.networkTick();
/*  778 */     this.theProfiler.endStartSection("players");
/*  779 */     this.serverConfigManager.onTick();
/*  780 */     this.theProfiler.endStartSection("tickables");
/*      */     
/*  782 */     for (int k = 0; k < this.playersOnline.size(); k++)
/*      */     {
/*  784 */       ((ITickable)this.playersOnline.get(k)).update();
/*      */     }
/*      */     
/*  787 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAllowNether() {
/*  792 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startServerThread() {
/*  797 */     this.serverThread = new Thread(this, "Server thread");
/*  798 */     this.serverThread.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getFile(String fileName) {
/*  806 */     return new File(getDataDirectory(), fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logWarning(String msg) {
/*  814 */     logger.warn(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldServer worldServerForDimension(int dimension) {
/*  822 */     return (dimension == -1) ? this.worldServers[1] : ((dimension == 1) ? this.worldServers[2] : this.worldServers[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMinecraftVersion() {
/*  830 */     return "1.8.9";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/*  838 */     return this.serverConfigManager.getCurrentPlayerCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  846 */     return this.serverConfigManager.getMaxPlayers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  854 */     return this.serverConfigManager.getAllUsernames();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile[] getGameProfiles() {
/*  862 */     return this.serverConfigManager.getAllProfiles();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerModName() {
/*  867 */     return "vanilla";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/*  875 */     report.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>()
/*      */         {
/*      */           public String call() {
/*  878 */             return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/*      */     
/*  882 */     if (this.serverConfigManager != null)
/*      */     {
/*  884 */       report.getCategory().addCrashSectionCallable("Player Count", new Callable<String>()
/*      */           {
/*      */             public String call()
/*      */             {
/*  888 */               return MinecraftServer.this.serverConfigManager.getCurrentPlayerCount() + " / " + MinecraftServer.this.serverConfigManager.getMaxPlayers() + "; " + MinecraftServer.this.serverConfigManager.getPlayerList();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  893 */     return report;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTabCompletions(ICommandSender sender, String input, BlockPos pos) {
/*  898 */     List<String> list = Lists.newArrayList();
/*      */     
/*  900 */     if (!input.isEmpty() && input.charAt(0) == '/') {
/*      */       
/*  902 */       input = input.substring(1);
/*  903 */       boolean flag = !input.contains(" ");
/*  904 */       List<String> list1 = this.commandManager.getTabCompletionOptions(sender, input, pos);
/*      */       
/*  906 */       if (list1 != null)
/*      */       {
/*  908 */         for (String s2 : list1) {
/*      */           
/*  910 */           if (flag) {
/*      */             
/*  912 */             list.add("/" + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  916 */           list.add(s2);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  921 */       return list;
/*      */     } 
/*      */ 
/*      */     
/*  925 */     String[] astring = input.split(" ", -1);
/*  926 */     String s = astring[astring.length - 1];
/*      */     
/*  928 */     for (String s1 : this.serverConfigManager.getAllUsernames()) {
/*      */       
/*  930 */       if (CommandBase.doesStringStartWith(s, s1))
/*      */       {
/*  932 */         list.add(s1);
/*      */       }
/*      */     } 
/*      */     
/*  936 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MinecraftServer getServer() {
/*  945 */     return mcServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnvilFileSet() {
/*  950 */     return (this.anvilFile != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  958 */     return "Server";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/*  966 */     logger.info(component.getUnformattedText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  974 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public ICommandManager getCommandManager() {
/*  979 */     return this.commandManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyPair getKeyPair() {
/*  987 */     return this.serverKeyPair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerOwner() {
/*  995 */     return this.serverOwner;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerOwner(String owner) {
/* 1003 */     this.serverOwner = owner;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSinglePlayer() {
/* 1008 */     return (this.serverOwner != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFolderName() {
/* 1013 */     return this.folderName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFolderName(String name) {
/* 1018 */     this.folderName = name;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldName(String p_71246_1_) {
/* 1023 */     this.worldName = p_71246_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getWorldName() {
/* 1028 */     return this.worldName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setKeyPair(KeyPair keyPair) {
/* 1033 */     this.serverKeyPair = keyPair;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 1038 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/* 1040 */       WorldServer worldServer = this.worldServers[i];
/*      */       
/* 1042 */       if (worldServer != null)
/*      */       {
/* 1044 */         if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
/*      */           
/* 1046 */           worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/* 1047 */           worldServer.setAllowedSpawnTypes(true, true);
/*      */         }
/* 1049 */         else if (isSinglePlayer()) {
/*      */           
/* 1051 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/* 1052 */           worldServer.setAllowedSpawnTypes((worldServer.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */         }
/*      */         else {
/*      */           
/* 1056 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/* 1057 */           worldServer.setAllowedSpawnTypes(allowSpawnMonsters(), this.canSpawnAnimals);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean allowSpawnMonsters() {
/* 1065 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDemo() {
/* 1073 */     return this.isDemo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDemo(boolean demo) {
/* 1081 */     this.isDemo = demo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void canCreateBonusChest(boolean enable) {
/* 1086 */     this.enableBonusChest = enable;
/*      */   }
/*      */ 
/*      */   
/*      */   public ISaveFormat getActiveAnvilConverter() {
/* 1091 */     return this.anvilConverterForAnvilFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteWorldAndStopServer() {
/* 1100 */     this.worldIsBeingDeleted = true;
/* 1101 */     this.anvilConverterForAnvilFile.flushCache();
/*      */     
/* 1103 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/* 1105 */       WorldServer worldserver = this.worldServers[i];
/*      */       
/* 1107 */       if (worldserver != null)
/*      */       {
/* 1109 */         worldserver.flush();
/*      */       }
/*      */     } 
/*      */     
/* 1113 */     this.anvilConverterForAnvilFile.deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
/* 1114 */     initiateShutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackUrl() {
/* 1119 */     return this.resourcePackUrl;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackHash() {
/* 1124 */     return this.resourcePackHash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setResourcePack(String url, String hash) {
/* 1129 */     this.resourcePackUrl = url;
/* 1130 */     this.resourcePackHash = hash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1135 */     playerSnooper.addClientStat("whitelist_enabled", Boolean.valueOf(false));
/* 1136 */     playerSnooper.addClientStat("whitelist_count", Integer.valueOf(0));
/*      */     
/* 1138 */     if (this.serverConfigManager != null) {
/*      */       
/* 1140 */       playerSnooper.addClientStat("players_current", Integer.valueOf(getCurrentPlayerCount()));
/* 1141 */       playerSnooper.addClientStat("players_max", Integer.valueOf(getMaxPlayers()));
/* 1142 */       playerSnooper.addClientStat("players_seen", Integer.valueOf((this.serverConfigManager.getAvailablePlayerDat()).length));
/*      */     } 
/*      */     
/* 1145 */     playerSnooper.addClientStat("uses_auth", Boolean.valueOf(this.onlineMode));
/* 1146 */     playerSnooper.addClientStat("gui_state", getGuiEnabled() ? "enabled" : "disabled");
/* 1147 */     playerSnooper.addClientStat("run_time", Long.valueOf((getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 1148 */     playerSnooper.addClientStat("avg_tick_ms", Integer.valueOf((int)(MathHelper.average(this.tickTimeArray) * 1.0E-6D)));
/* 1149 */     int i = 0;
/*      */     
/* 1151 */     if (this.worldServers != null)
/*      */     {
/* 1153 */       for (int j = 0; j < this.worldServers.length; j++) {
/*      */         
/* 1155 */         if (this.worldServers[j] != null) {
/*      */           
/* 1157 */           WorldServer worldserver = this.worldServers[j];
/* 1158 */           WorldInfo worldinfo = worldserver.getWorldInfo();
/* 1159 */           playerSnooper.addClientStat("world[" + i + "][dimension]", Integer.valueOf(worldserver.provider.getDimensionId()));
/* 1160 */           playerSnooper.addClientStat("world[" + i + "][mode]", worldinfo.getGameType());
/* 1161 */           playerSnooper.addClientStat("world[" + i + "][difficulty]", worldserver.getDifficulty());
/* 1162 */           playerSnooper.addClientStat("world[" + i + "][hardcore]", Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
/* 1163 */           playerSnooper.addClientStat("world[" + i + "][generator_name]", worldinfo.getTerrainType().getWorldTypeName());
/* 1164 */           playerSnooper.addClientStat("world[" + i + "][generator_version]", Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
/* 1165 */           playerSnooper.addClientStat("world[" + i + "][height]", Integer.valueOf(this.buildLimit));
/* 1166 */           playerSnooper.addClientStat("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.getChunkProvider().getLoadedChunkCount()));
/* 1167 */           i++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1172 */     playerSnooper.addClientStat("worlds", Integer.valueOf(i));
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1177 */     playerSnooper.addStatToSnooper("singleplayer", Boolean.valueOf(isSinglePlayer()));
/* 1178 */     playerSnooper.addStatToSnooper("server_brand", getServerModName());
/* 1179 */     playerSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/* 1180 */     playerSnooper.addStatToSnooper("dedicated", Boolean.valueOf(isDedicatedServer()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 1188 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract boolean isDedicatedServer();
/*      */   
/*      */   public boolean isServerInOnlineMode() {
/* 1195 */     return this.onlineMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOnlineMode(boolean online) {
/* 1200 */     this.onlineMode = online;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnAnimals() {
/* 1205 */     return this.canSpawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCanSpawnAnimals(boolean spawnAnimals) {
/* 1210 */     this.canSpawnAnimals = spawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnNPCs() {
/* 1215 */     return this.canSpawnNPCs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean shouldUseNativeTransport();
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanSpawnNPCs(boolean spawnNpcs) {
/* 1226 */     this.canSpawnNPCs = spawnNpcs;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPVPEnabled() {
/* 1231 */     return this.pvpEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowPvp(boolean allowPvp) {
/* 1236 */     this.pvpEnabled = allowPvp;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlightAllowed() {
/* 1241 */     return this.allowFlight;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowFlight(boolean allow) {
/* 1246 */     this.allowFlight = allow;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isCommandBlockEnabled();
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMOTD() {
/* 1256 */     return this.motd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMOTD(String motdIn) {
/* 1261 */     this.motd = motdIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBuildLimit() {
/* 1266 */     return this.buildLimit;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBuildLimit(int maxBuildHeight) {
/* 1271 */     this.buildLimit = maxBuildHeight;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerStopped() {
/* 1276 */     return this.serverStopped;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerConfigurationManager getConfigurationManager() {
/* 1281 */     return this.serverConfigManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setConfigManager(ServerConfigurationManager configManager) {
/* 1286 */     this.serverConfigManager = configManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameMode) {
/* 1294 */     for (int i = 0; i < this.worldServers.length; i++)
/*      */     {
/* 1296 */       mcServer.worldServers[i].getWorldInfo().setGameType(gameMode);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkSystem getNetworkSystem() {
/* 1302 */     return this.networkSystem;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean serverIsInRunLoop() {
/* 1307 */     return this.serverIsRunning;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getGuiEnabled() {
/* 1312 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String shareToLAN(WorldSettings.GameType paramGameType, boolean paramBoolean);
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTickCounter() {
/* 1322 */     return this.tickCounter;
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableProfiling() {
/* 1327 */     this.startProfiling = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 1332 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1341 */     return BlockPos.ORIGIN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 1350 */     return new Vec3(0.0D, 0.0D, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 1359 */     return (World)this.worldServers[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 1367 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSpawnProtectionSize() {
/* 1375 */     return 16;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockProtected(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 1380 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getForceGamemode() {
/* 1385 */     return this.isGamemodeForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public Proxy getServerProxy() {
/* 1390 */     return this.serverProxy;
/*      */   }
/*      */ 
/*      */   
/*      */   public static long getCurrentTimeMillis() {
/* 1395 */     return System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxPlayerIdleMinutes() {
/* 1400 */     return this.maxPlayerIdleMinutes;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerIdleTimeout(int idleTimeout) {
/* 1405 */     this.maxPlayerIdleMinutes = idleTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 1413 */     return (IChatComponent)new ChatComponentText(getName());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnnouncingPlayerAchievements() {
/* 1418 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getMinecraftSessionService() {
/* 1423 */     return this.sessionService;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfileRepository getGameProfileRepository() {
/* 1428 */     return this.profileRepo;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerProfileCache getPlayerProfileCache() {
/* 1433 */     return this.profileCache;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerStatusResponse getServerStatusResponse() {
/* 1438 */     return this.statusResponse;
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshStatusNextTick() {
/* 1443 */     this.nanoTimeSinceStatusRefresh = 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getEntityFromUuid(UUID uuid) {
/* 1448 */     for (WorldServer worldserver : this.worldServers) {
/*      */       
/* 1450 */       if (worldserver != null) {
/*      */         
/* 1452 */         Entity entity = worldserver.getEntityFromUuid(uuid);
/*      */         
/* 1454 */         if (entity != null)
/*      */         {
/* 1456 */           return entity;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1461 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 1469 */     return mcServer.worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*      */ 
/*      */   
/*      */   public int getMaxWorldSize() {
/* 1478 */     return 29999984;
/*      */   }
/*      */ 
/*      */   
/*      */   public <V> ListenableFuture<V> callFromMainThread(Callable<V> callable) {
/* 1483 */     Validate.notNull(callable);
/*      */     
/* 1485 */     if (!isCallingFromMinecraftThread() && !this.serverStopped) {
/*      */       
/* 1487 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callable);
/*      */       
/* 1489 */       synchronized (this.futureTaskQueue) {
/*      */         
/* 1491 */         this.futureTaskQueue.add(listenablefuturetask);
/* 1492 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1499 */       return Futures.immediateFuture(callable.call());
/*      */     }
/* 1501 */     catch (Exception exception) {
/*      */       
/* 1503 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 1510 */     Validate.notNull(runnableToSchedule);
/* 1511 */     return callFromMainThread(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 1516 */     return (Thread.currentThread() == this.serverThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNetworkCompressionTreshold() {
/* 1524 */     return 256;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\MinecraftServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */