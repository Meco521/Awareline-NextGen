/*      */ package net.minecraft.world;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.TreeSet;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockEventData;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EntityTracker;
/*      */ import net.minecraft.entity.EnumCreatureType;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S2APacketParticles;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.scoreboard.ScoreboardSaveData;
/*      */ import net.minecraft.scoreboard.ServerScoreboard;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.PlayerManager;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IProgressUpdate;
/*      */ import net.minecraft.util.WeightedRandomChestContent;
/*      */ import net.minecraft.village.VillageCollection;
/*      */ import net.minecraft.village.VillageSiege;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*      */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*      */ import net.minecraft.world.gen.ChunkProviderServer;
/*      */ import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
/*      */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ 
/*      */ public class WorldServer extends World implements IThreadListener {
/*   53 */   private static final Logger logger = LogManager.getLogger();
/*      */   private final MinecraftServer mcServer;
/*      */   private final EntityTracker theEntityTracker;
/*      */   private final PlayerManager thePlayerManager;
/*   57 */   private final Set<NextTickListEntry> pendingTickListEntriesHashSet = Sets.newHashSet();
/*   58 */   private final TreeSet<NextTickListEntry> pendingTickListEntriesTreeSet = new TreeSet<>();
/*   59 */   private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
/*      */ 
/*      */   
/*      */   public ChunkProviderServer theChunkProviderServer;
/*      */ 
/*      */   
/*      */   public boolean disableLevelSaving;
/*      */   
/*      */   private boolean allPlayersSleeping;
/*      */   
/*      */   private int updateEntityTick;
/*      */   
/*      */   private final Teleporter worldTeleporter;
/*      */   
/*   73 */   private final SpawnerAnimals mobSpawner = new SpawnerAnimals();
/*   74 */   protected final VillageSiege villageSiege = new VillageSiege(this);
/*   75 */   private final ServerBlockEventList[] blockEventQueue = new ServerBlockEventList[] { new ServerBlockEventList(), new ServerBlockEventList() };
/*      */   private int blockEventCacheIndex;
/*   77 */   private static final List<WeightedRandomChestContent> bonusChestContent = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10) });
/*   78 */   private final List<NextTickListEntry> pendingTickListEntriesThisTick = Lists.newArrayList();
/*      */ 
/*      */   
/*      */   public WorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn) {
/*   82 */     super(saveHandlerIn, info, WorldProvider.getProviderForDimension(dimensionId), profilerIn, false);
/*   83 */     this.mcServer = server;
/*   84 */     this.theEntityTracker = new EntityTracker(this);
/*   85 */     this.thePlayerManager = new PlayerManager(this);
/*   86 */     this.provider.registerWorld(this);
/*   87 */     this.chunkProvider = createChunkProvider();
/*   88 */     this.worldTeleporter = new Teleporter(this);
/*   89 */     calculateInitialSkylight();
/*   90 */     calculateInitialWeather();
/*   91 */     getWorldBorder().setSize(server.getMaxWorldSize());
/*      */   }
/*      */ 
/*      */   
/*      */   public World init() {
/*   96 */     this.mapStorage = new MapStorage(this.saveHandler);
/*   97 */     String s = VillageCollection.fileNameForProvider(this.provider);
/*   98 */     VillageCollection villagecollection = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, s);
/*      */     
/*  100 */     if (villagecollection == null) {
/*      */       
/*  102 */       this.villageCollectionObj = new VillageCollection(this);
/*  103 */       this.mapStorage.setData(s, (WorldSavedData)this.villageCollectionObj);
/*      */     }
/*      */     else {
/*      */       
/*  107 */       this.villageCollectionObj = villagecollection;
/*  108 */       this.villageCollectionObj.setWorldsForAll(this);
/*      */     } 
/*      */     
/*  111 */     this.worldScoreboard = (Scoreboard)new ServerScoreboard(this.mcServer);
/*  112 */     ScoreboardSaveData scoreboardsavedata = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
/*      */     
/*  114 */     if (scoreboardsavedata == null) {
/*      */       
/*  116 */       scoreboardsavedata = new ScoreboardSaveData();
/*  117 */       this.mapStorage.setData("scoreboard", (WorldSavedData)scoreboardsavedata);
/*      */     } 
/*      */     
/*  120 */     scoreboardsavedata.setScoreboard(this.worldScoreboard);
/*  121 */     ((ServerScoreboard)this.worldScoreboard).func_96547_a(scoreboardsavedata);
/*  122 */     getWorldBorder().setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
/*  123 */     getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
/*  124 */     getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
/*  125 */     getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
/*  126 */     getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
/*      */     
/*  128 */     if (this.worldInfo.getBorderLerpTime() > 0L) {
/*      */       
/*  130 */       getWorldBorder().setTransition(this.worldInfo.getBorderSize(), this.worldInfo.getBorderLerpTarget(), this.worldInfo.getBorderLerpTime());
/*      */     }
/*      */     else {
/*      */       
/*  134 */       getWorldBorder().setTransition(this.worldInfo.getBorderSize());
/*      */     } 
/*      */     
/*  137 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  145 */     super.tick();
/*      */     
/*  147 */     if (getWorldInfo().isHardcoreModeEnabled() && getDifficulty() != EnumDifficulty.HARD)
/*      */     {
/*  149 */       getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/*      */     }
/*      */     
/*  152 */     this.provider.getWorldChunkManager().cleanupCache();
/*      */     
/*  154 */     if (areAllPlayersAsleep()) {
/*      */       
/*  156 */       if (getGameRules().getBoolean("doDaylightCycle")) {
/*      */         
/*  158 */         long i = this.worldInfo.getWorldTime() + 24000L;
/*  159 */         this.worldInfo.setWorldTime(i - i % 24000L);
/*      */       } 
/*      */       
/*  162 */       wakeAllPlayers();
/*      */     } 
/*      */     
/*  165 */     this.theProfiler.startSection("mobSpawner");
/*      */     
/*  167 */     if (getGameRules().getBoolean("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD)
/*      */     {
/*  169 */       this.mobSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, (this.worldInfo.getWorldTotalTime() % 400L == 0L));
/*      */     }
/*      */     
/*  172 */     this.theProfiler.endStartSection("chunkSource");
/*  173 */     this.chunkProvider.unloadQueuedChunks();
/*  174 */     int j = calculateSkylightSubtracted(1.0F);
/*      */     
/*  176 */     if (j != getSkylightSubtracted())
/*      */     {
/*  178 */       setSkylightSubtracted(j);
/*      */     }
/*      */     
/*  181 */     this.worldInfo.setWorldTotalTime(this.worldInfo.getWorldTotalTime() + 1L);
/*      */     
/*  183 */     if (getGameRules().getBoolean("doDaylightCycle"))
/*      */     {
/*  185 */       this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
/*      */     }
/*      */     
/*  188 */     this.theProfiler.endStartSection("tickPending");
/*      */     
/*  190 */     this.theProfiler.endStartSection("tickBlocks");
/*  191 */     updateBlocks();
/*  192 */     this.theProfiler.endStartSection("chunkMap");
/*  193 */     this.thePlayerManager.updatePlayerInstances();
/*  194 */     this.theProfiler.endStartSection("village");
/*  195 */     this.villageCollectionObj.tick();
/*  196 */     this.villageSiege.tick();
/*  197 */     this.theProfiler.endStartSection("portalForcer");
/*  198 */     this.worldTeleporter.removeStalePortalLocations(getTotalWorldTime());
/*  199 */     this.theProfiler.endSection();
/*  200 */     sendQueuedBlockEvents();
/*      */   }
/*      */ 
/*      */   
/*      */   public BiomeGenBase.SpawnListEntry getSpawnListEntryForTypeAt(EnumCreatureType creatureType, BlockPos pos) {
/*  205 */     List<BiomeGenBase.SpawnListEntry> list = getChunkProvider().getPossibleCreatures(creatureType, pos);
/*  206 */     return (list != null && !list.isEmpty()) ? (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, list) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canCreatureTypeSpawnHere(EnumCreatureType creatureType, BiomeGenBase.SpawnListEntry spawnListEntry, BlockPos pos) {
/*  211 */     List<BiomeGenBase.SpawnListEntry> list = getChunkProvider().getPossibleCreatures(creatureType, pos);
/*  212 */     return (list != null && !list.isEmpty()) ? list.contains(spawnListEntry) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAllPlayersSleepingFlag() {
/*  220 */     this.allPlayersSleeping = false;
/*      */     
/*  222 */     if (!this.playerEntities.isEmpty()) {
/*      */       
/*  224 */       int i = 0;
/*  225 */       int j = 0;
/*      */       
/*  227 */       for (EntityPlayer entityplayer : this.playerEntities) {
/*      */         
/*  229 */         if (entityplayer.isSpectator()) {
/*      */           
/*  231 */           i++; continue;
/*      */         } 
/*  233 */         if (entityplayer.isPlayerSleeping())
/*      */         {
/*  235 */           j++;
/*      */         }
/*      */       } 
/*      */       
/*  239 */       this.allPlayersSleeping = (j > 0 && j >= this.playerEntities.size() - i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void wakeAllPlayers() {
/*  245 */     this.allPlayersSleeping = false;
/*      */     
/*  247 */     for (EntityPlayer entityplayer : this.playerEntities) {
/*      */       
/*  249 */       if (entityplayer.isPlayerSleeping())
/*      */       {
/*  251 */         entityplayer.wakeUpPlayer(false, false, true);
/*      */       }
/*      */     } 
/*      */     
/*  255 */     resetRainAndThunder();
/*      */   }
/*      */ 
/*      */   
/*      */   private void resetRainAndThunder() {
/*  260 */     this.worldInfo.setRainTime(0);
/*  261 */     this.worldInfo.setRaining(false);
/*  262 */     this.worldInfo.setThunderTime(0);
/*  263 */     this.worldInfo.setThundering(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean areAllPlayersAsleep() {
/*  268 */     if (this.allPlayersSleeping && !this.isRemote) {
/*      */       
/*  270 */       for (EntityPlayer entityplayer : this.playerEntities) {
/*      */         
/*  272 */         if (entityplayer.isSpectator() || !entityplayer.isPlayerFullyAsleep())
/*      */         {
/*  274 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  278 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  282 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialSpawnLocation() {
/*  291 */     if (this.worldInfo.getSpawnY() <= 0)
/*      */     {
/*  293 */       this.worldInfo.setSpawnY(getSeaLevel() + 1);
/*      */     }
/*      */     
/*  296 */     int i = this.worldInfo.getSpawnX();
/*  297 */     int j = this.worldInfo.getSpawnZ();
/*  298 */     int k = 0;
/*      */     
/*  300 */     while (getGroundAboveSeaLevel(new BlockPos(i, 0, j)).getMaterial() == Material.air) {
/*      */       
/*  302 */       i += this.rand.nextInt(8) - this.rand.nextInt(8);
/*  303 */       j += this.rand.nextInt(8) - this.rand.nextInt(8);
/*  304 */       k++;
/*      */       
/*  306 */       if (k == 10000) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  312 */     this.worldInfo.setSpawnX(i);
/*  313 */     this.worldInfo.setSpawnZ(j);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateBlocks() {
/*  318 */     super.updateBlocks();
/*      */     
/*  320 */     if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/*      */       
/*  322 */       for (ChunkCoordIntPair chunkcoordintpair1 : this.activeChunkSet)
/*      */       {
/*  324 */         getChunkFromChunkCoords(chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos).func_150804_b(false);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  329 */       int i = 0;
/*  330 */       int j = 0;
/*      */       
/*  332 */       for (ChunkCoordIntPair chunkcoordintpair : this.activeChunkSet) {
/*      */         
/*  334 */         int k = chunkcoordintpair.chunkXPos << 4;
/*  335 */         int l = chunkcoordintpair.chunkZPos << 4;
/*  336 */         this.theProfiler.startSection("getChunk");
/*  337 */         Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/*  338 */         playMoodSoundAndCheckLight(k, l, chunk);
/*  339 */         this.theProfiler.endStartSection("tickChunk");
/*  340 */         chunk.func_150804_b(false);
/*  341 */         this.theProfiler.endStartSection("thunder");
/*      */         
/*  343 */         if (this.rand.nextInt(100000) == 0 && isRaining() && isThundering()) {
/*      */           
/*  345 */           this.updateLCG = this.updateLCG * 3 + 1013904223;
/*  346 */           int i1 = this.updateLCG >> 2;
/*  347 */           BlockPos blockpos = adjustPosToNearbyEntity(new BlockPos(k + (i1 & 0xF), 0, l + (i1 >> 8 & 0xF)));
/*      */           
/*  349 */           if (isRainingAt(blockpos))
/*      */           {
/*  351 */             addWeatherEffect((Entity)new EntityLightningBolt(this, blockpos.getX(), blockpos.getY(), blockpos.getZ()));
/*      */           }
/*      */         } 
/*      */         
/*  355 */         this.theProfiler.endStartSection("iceandsnow");
/*      */         
/*  357 */         if (this.rand.nextInt(16) == 0) {
/*      */           
/*  359 */           this.updateLCG = this.updateLCG * 3 + 1013904223;
/*  360 */           int k2 = this.updateLCG >> 2;
/*  361 */           BlockPos blockpos2 = getPrecipitationHeight(new BlockPos(k + (k2 & 0xF), 0, l + (k2 >> 8 & 0xF)));
/*  362 */           BlockPos blockpos1 = blockpos2.down();
/*      */           
/*  364 */           if (canBlockFreezeNoWater(blockpos1))
/*      */           {
/*  366 */             setBlockState(blockpos1, Blocks.ice.getDefaultState());
/*      */           }
/*      */           
/*  369 */           if (isRaining() && canSnowAt(blockpos2, true))
/*      */           {
/*  371 */             setBlockState(blockpos2, Blocks.snow_layer.getDefaultState());
/*      */           }
/*      */           
/*  374 */           if (isRaining() && getBiomeGenForCoords(blockpos1).canRain())
/*      */           {
/*  376 */             getBlockState(blockpos1).getBlock().fillWithRain(this, blockpos1);
/*      */           }
/*      */         } 
/*      */         
/*  380 */         this.theProfiler.endStartSection("tickBlocks");
/*  381 */         int l2 = getGameRules().getInt("randomTickSpeed");
/*      */         
/*  383 */         if (l2 > 0)
/*      */         {
/*  385 */           for (ExtendedBlockStorage extendedblockstorage : chunk.getBlockStorageArray()) {
/*      */             
/*  387 */             if (extendedblockstorage != null && extendedblockstorage.getNeedsRandomTick())
/*      */             {
/*  389 */               for (int j1 = 0; j1 < l2; j1++) {
/*      */                 
/*  391 */                 this.updateLCG = this.updateLCG * 3 + 1013904223;
/*  392 */                 int k1 = this.updateLCG >> 2;
/*  393 */                 int l1 = k1 & 0xF;
/*  394 */                 int i2 = k1 >> 8 & 0xF;
/*  395 */                 int j2 = k1 >> 16 & 0xF;
/*  396 */                 j++;
/*  397 */                 IBlockState iblockstate = extendedblockstorage.get(l1, j2, i2);
/*  398 */                 Block block = iblockstate.getBlock();
/*      */                 
/*  400 */                 if (block.getTickRandomly()) {
/*      */                   
/*  402 */                   i++;
/*  403 */                   block.randomTick(this, new BlockPos(l1 + k, j2 + extendedblockstorage.getYLocation(), i2 + l), iblockstate, this.rand);
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/*  410 */         this.theProfiler.endSection();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected BlockPos adjustPosToNearbyEntity(BlockPos pos) {
/*  417 */     BlockPos blockpos = getPrecipitationHeight(pos);
/*  418 */     AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos, new BlockPos(blockpos.getX(), getHeight(), blockpos.getZ()))).expand(3.0D, 3.0D, 3.0D);
/*  419 */     List<EntityLivingBase> list = getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, new Predicate<EntityLivingBase>()
/*      */         {
/*      */           public boolean apply(EntityLivingBase p_apply_1_)
/*      */           {
/*  423 */             return (p_apply_1_ != null && p_apply_1_.isEntityAlive() && WorldServer.this.canSeeSky(p_apply_1_.getPosition()));
/*      */           }
/*      */         });
/*  426 */     return !list.isEmpty() ? ((EntityLivingBase)list.get(this.rand.nextInt(list.size()))).getPosition() : blockpos;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockTickPending(BlockPos pos, Block blockType) {
/*  431 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockType);
/*  432 */     return this.pendingTickListEntriesThisTick.contains(nextticklistentry);
/*      */   }
/*      */ 
/*      */   
/*      */   public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {
/*  437 */     updateBlockTick(pos, blockIn, delay, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {
/*  442 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
/*  443 */     int i = 0;
/*      */     
/*  445 */     if (this.scheduledUpdatesAreImmediate && blockIn.getMaterial() != Material.air) {
/*      */       
/*  447 */       if (blockIn.requiresUpdates()) {
/*      */         
/*  449 */         i = 8;
/*      */         
/*  451 */         if (isAreaLoaded(nextticklistentry.position.add(-i, -i, -i), nextticklistentry.position.add(i, i, i))) {
/*      */           
/*  453 */           IBlockState iblockstate = getBlockState(nextticklistentry.position);
/*      */           
/*  455 */           if (iblockstate.getBlock().getMaterial() != Material.air && iblockstate.getBlock() == nextticklistentry.getBlock())
/*      */           {
/*  457 */             iblockstate.getBlock().updateTick(this, nextticklistentry.position, iblockstate, this.rand);
/*      */           }
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  464 */       delay = 1;
/*      */     } 
/*      */     
/*  467 */     if (isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
/*      */       
/*  469 */       if (blockIn.getMaterial() != Material.air) {
/*      */         
/*  471 */         nextticklistentry.setScheduledTime(delay + this.worldInfo.getWorldTotalTime());
/*  472 */         nextticklistentry.setPriority(priority);
/*      */       } 
/*      */       
/*  475 */       if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
/*      */         
/*  477 */         this.pendingTickListEntriesHashSet.add(nextticklistentry);
/*  478 */         this.pendingTickListEntriesTreeSet.add(nextticklistentry);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {
/*  485 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
/*  486 */     nextticklistentry.setPriority(priority);
/*      */     
/*  488 */     if (blockIn.getMaterial() != Material.air)
/*      */     {
/*  490 */       nextticklistentry.setScheduledTime(delay + this.worldInfo.getWorldTotalTime());
/*      */     }
/*      */     
/*  493 */     if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
/*      */       
/*  495 */       this.pendingTickListEntriesHashSet.add(nextticklistentry);
/*  496 */       this.pendingTickListEntriesTreeSet.add(nextticklistentry);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntities() {
/*  505 */     if (this.playerEntities.isEmpty()) {
/*      */       
/*  507 */       if (this.updateEntityTick++ >= 1200)
/*      */       {
/*      */         return;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  514 */       resetUpdateEntityTick();
/*      */     } 
/*      */     
/*  517 */     super.updateEntities();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetUpdateEntityTick() {
/*  525 */     this.updateEntityTick = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tickUpdates(boolean p_72955_1_) {
/*  533 */     if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
/*      */     {
/*  535 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  539 */     int i = this.pendingTickListEntriesTreeSet.size();
/*      */     
/*  541 */     if (i != this.pendingTickListEntriesHashSet.size())
/*      */     {
/*  543 */       throw new IllegalStateException("TickNextTick list out of synch");
/*      */     }
/*      */ 
/*      */     
/*  547 */     if (i > 1000)
/*      */     {
/*  549 */       i = 1000;
/*      */     }
/*      */     
/*  552 */     this.theProfiler.startSection("cleaning");
/*      */     
/*  554 */     for (int j = 0; j < i; j++) {
/*      */       
/*  556 */       NextTickListEntry nextticklistentry = this.pendingTickListEntriesTreeSet.first();
/*      */       
/*  558 */       if (!p_72955_1_ && nextticklistentry.scheduledTime > this.worldInfo.getWorldTotalTime()) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  563 */       this.pendingTickListEntriesTreeSet.remove(nextticklistentry);
/*  564 */       this.pendingTickListEntriesHashSet.remove(nextticklistentry);
/*  565 */       this.pendingTickListEntriesThisTick.add(nextticklistentry);
/*      */     } 
/*      */     
/*  568 */     this.theProfiler.endSection();
/*  569 */     this.theProfiler.startSection("ticking");
/*  570 */     Iterator<NextTickListEntry> iterator = this.pendingTickListEntriesThisTick.iterator();
/*      */     
/*  572 */     while (iterator.hasNext()) {
/*      */       
/*  574 */       NextTickListEntry nextticklistentry1 = iterator.next();
/*  575 */       iterator.remove();
/*  576 */       int k = 0;
/*      */       
/*  578 */       if (isAreaLoaded(nextticklistentry1.position.add(-k, -k, -k), nextticklistentry1.position.add(k, k, k))) {
/*      */         
/*  580 */         IBlockState iblockstate = getBlockState(nextticklistentry1.position);
/*      */         
/*  582 */         if (iblockstate.getBlock().getMaterial() != Material.air && Block.isEqualTo(iblockstate.getBlock(), nextticklistentry1.getBlock())) {
/*      */           
/*      */           try {
/*      */             
/*  586 */             iblockstate.getBlock().updateTick(this, nextticklistentry1.position, iblockstate, this.rand);
/*      */           }
/*  588 */           catch (Throwable throwable) {
/*      */             
/*  590 */             CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while ticking a block");
/*  591 */             CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being ticked");
/*  592 */             CrashReportCategory.addBlockInfo(crashreportcategory, nextticklistentry1.position, iblockstate);
/*  593 */             throw new ReportedException(crashreport);
/*      */           } 
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  599 */       scheduleUpdate(nextticklistentry1.position, nextticklistentry1.getBlock(), 0);
/*      */     } 
/*      */ 
/*      */     
/*  603 */     this.theProfiler.endSection();
/*  604 */     this.pendingTickListEntriesThisTick.clear();
/*  605 */     return !this.pendingTickListEntriesTreeSet.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
/*  612 */     ChunkCoordIntPair chunkcoordintpair = chunkIn.getChunkCoordIntPair();
/*  613 */     int i = (chunkcoordintpair.chunkXPos << 4) - 2;
/*  614 */     int j = i + 16 + 2;
/*  615 */     int k = (chunkcoordintpair.chunkZPos << 4) - 2;
/*  616 */     int l = k + 16 + 2;
/*  617 */     return func_175712_a(new StructureBoundingBox(i, 0, k, j, 256, l), p_72920_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBB, boolean p_175712_2_) {
/*  622 */     List<NextTickListEntry> list = null;
/*      */     
/*  624 */     for (int i = 0; i < 2; i++) {
/*      */       Iterator<NextTickListEntry> iterator;
/*      */ 
/*      */       
/*  628 */       if (i == 0) {
/*      */         
/*  630 */         iterator = this.pendingTickListEntriesTreeSet.iterator();
/*      */       }
/*      */       else {
/*      */         
/*  634 */         iterator = this.pendingTickListEntriesThisTick.iterator();
/*      */       } 
/*      */       
/*  637 */       while (iterator.hasNext()) {
/*      */         
/*  639 */         NextTickListEntry nextticklistentry = iterator.next();
/*  640 */         BlockPos blockpos = nextticklistentry.position;
/*      */         
/*  642 */         if (blockpos.getX() >= structureBB.minX && blockpos.getX() < structureBB.maxX && blockpos.getZ() >= structureBB.minZ && blockpos.getZ() < structureBB.maxZ) {
/*      */           
/*  644 */           if (p_175712_2_) {
/*      */             
/*  646 */             this.pendingTickListEntriesHashSet.remove(nextticklistentry);
/*  647 */             iterator.remove();
/*      */           } 
/*      */           
/*  650 */           if (list == null)
/*      */           {
/*  652 */             list = Lists.newArrayList();
/*      */           }
/*      */           
/*  655 */           list.add(nextticklistentry);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  660 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
/*  669 */     if (!canSpawnAnimals() && (entityIn instanceof net.minecraft.entity.passive.EntityAnimal || entityIn instanceof net.minecraft.entity.passive.EntityWaterMob))
/*      */     {
/*  671 */       entityIn.setDead();
/*      */     }
/*      */     
/*  674 */     if (!canSpawnNPCs() && entityIn instanceof net.minecraft.entity.INpc)
/*      */     {
/*  676 */       entityIn.setDead();
/*      */     }
/*      */     
/*  679 */     super.updateEntityWithOptionalForce(entityIn, forceUpdate);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSpawnNPCs() {
/*  684 */     return this.mcServer.getCanSpawnNPCs();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSpawnAnimals() {
/*  689 */     return this.mcServer.getCanSpawnAnimals();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IChunkProvider createChunkProvider() {
/*  697 */     IChunkLoader ichunkloader = this.saveHandler.getChunkLoader(this.provider);
/*  698 */     this.theChunkProviderServer = new ChunkProviderServer(this, ichunkloader, this.provider.createChunkGenerator());
/*  699 */     return (IChunkProvider)this.theChunkProviderServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<TileEntity> getTileEntitiesIn(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/*  704 */     List<TileEntity> list = Lists.newArrayList();
/*      */     
/*  706 */     for (int i = 0; i < this.loadedTileEntityList.size(); i++) {
/*      */       
/*  708 */       TileEntity tileentity = this.loadedTileEntityList.get(i);
/*  709 */       BlockPos blockpos = tileentity.getPos();
/*      */       
/*  711 */       if (blockpos.getX() >= minX && blockpos.getY() >= minY && blockpos.getZ() >= minZ && blockpos.getX() < maxX && blockpos.getY() < maxY && blockpos.getZ() < maxZ)
/*      */       {
/*  713 */         list.add(tileentity);
/*      */       }
/*      */     } 
/*      */     
/*  717 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
/*  722 */     return (!this.mcServer.isBlockProtected(this, pos, player) && getWorldBorder().contains(pos));
/*      */   }
/*      */ 
/*      */   
/*      */   public void initialize(WorldSettings settings) {
/*  727 */     if (!this.worldInfo.isInitialized()) {
/*      */ 
/*      */       
/*      */       try {
/*  731 */         createSpawnPosition(settings);
/*      */         
/*  733 */         if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
/*      */         {
/*  735 */           setDebugWorldSettings();
/*      */         }
/*      */         
/*  738 */         super.initialize(settings);
/*      */       }
/*  740 */       catch (Throwable throwable) {
/*      */         
/*  742 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception initializing level");
/*      */ 
/*      */         
/*      */         try {
/*  746 */           addWorldInfoToCrashReport(crashreport);
/*      */         }
/*  748 */         catch (Throwable throwable1) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  753 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  756 */       this.worldInfo.setServerInitialized(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setDebugWorldSettings() {
/*  762 */     this.worldInfo.setMapFeaturesEnabled(false);
/*  763 */     this.worldInfo.setAllowCommands(true);
/*  764 */     this.worldInfo.setRaining(false);
/*  765 */     this.worldInfo.setThundering(false);
/*  766 */     this.worldInfo.setCleanWeatherTime(1000000000);
/*  767 */     this.worldInfo.setWorldTime(6000L);
/*  768 */     this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
/*  769 */     this.worldInfo.setHardcore(false);
/*  770 */     this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
/*  771 */     this.worldInfo.setDifficultyLocked(true);
/*  772 */     getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSpawnPosition(WorldSettings settings) {
/*  780 */     if (!this.provider.canRespawnHere()) {
/*      */       
/*  782 */       this.worldInfo.setSpawn(BlockPos.ORIGIN.up(this.provider.getAverageGroundLevel()));
/*      */     }
/*  784 */     else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/*      */       
/*  786 */       this.worldInfo.setSpawn(BlockPos.ORIGIN.up());
/*      */     }
/*      */     else {
/*      */       
/*  790 */       this.findingSpawnPoint = true;
/*  791 */       WorldChunkManager worldchunkmanager = this.provider.getWorldChunkManager();
/*  792 */       List<BiomeGenBase> list = worldchunkmanager.getBiomesToSpawnIn();
/*  793 */       Random random = new Random(getSeed());
/*  794 */       BlockPos blockpos = worldchunkmanager.findBiomePosition(0, 0, 256, list, random);
/*  795 */       int i = 0;
/*  796 */       int j = this.provider.getAverageGroundLevel();
/*  797 */       int k = 0;
/*      */       
/*  799 */       if (blockpos != null) {
/*      */         
/*  801 */         i = blockpos.getX();
/*  802 */         k = blockpos.getZ();
/*      */       }
/*      */       else {
/*      */         
/*  806 */         logger.warn("Unable to find spawn biome");
/*      */       } 
/*      */       
/*  809 */       int l = 0;
/*      */       
/*  811 */       while (!this.provider.canCoordinateBeSpawn(i, k)) {
/*      */         
/*  813 */         i += random.nextInt(64) - random.nextInt(64);
/*  814 */         k += random.nextInt(64) - random.nextInt(64);
/*  815 */         l++;
/*      */         
/*  817 */         if (l == 1000) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  823 */       this.worldInfo.setSpawn(new BlockPos(i, j, k));
/*  824 */       this.findingSpawnPoint = false;
/*      */       
/*  826 */       if (settings.isBonusChestEnabled())
/*      */       {
/*  828 */         createBonusChest();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createBonusChest() {
/*  838 */     WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest(bonusChestContent, 10);
/*      */     
/*  840 */     for (int i = 0; i < 10; i++) {
/*      */       
/*  842 */       int j = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
/*  843 */       int k = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
/*  844 */       BlockPos blockpos = getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)).up();
/*      */       
/*  846 */       if (worldgeneratorbonuschest.generate(this, this.rand, blockpos)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getSpawnCoordinate() {
/*  858 */     return this.provider.getSpawnCoordinate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAllChunks(boolean p_73044_1_, IProgressUpdate progressCallback) throws MinecraftException {
/*  866 */     if (this.chunkProvider.canSave()) {
/*      */       
/*  868 */       if (progressCallback != null)
/*      */       {
/*  870 */         progressCallback.displaySavingString("Saving level");
/*      */       }
/*      */       
/*  873 */       saveLevel();
/*      */       
/*  875 */       if (progressCallback != null)
/*      */       {
/*  877 */         progressCallback.displayLoadingString("Saving chunks");
/*      */       }
/*      */       
/*  880 */       this.chunkProvider.saveChunks(p_73044_1_, progressCallback);
/*      */       
/*  882 */       for (Chunk chunk : Lists.newArrayList(this.theChunkProviderServer.func_152380_a())) {
/*      */         
/*  884 */         if (chunk != null && !this.thePlayerManager.hasPlayerInstance(chunk.xPosition, chunk.zPosition))
/*      */         {
/*  886 */           this.theChunkProviderServer.dropChunk(chunk.xPosition, chunk.zPosition);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveChunkData() {
/*  897 */     if (this.chunkProvider.canSave())
/*      */     {
/*  899 */       this.chunkProvider.saveExtraData();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveLevel() throws MinecraftException {
/*  908 */     checkSessionLock();
/*  909 */     this.worldInfo.setBorderSize(getWorldBorder().getDiameter());
/*  910 */     this.worldInfo.getBorderCenterX(getWorldBorder().getCenterX());
/*  911 */     this.worldInfo.getBorderCenterZ(getWorldBorder().getCenterZ());
/*  912 */     this.worldInfo.setBorderSafeZone(getWorldBorder().getDamageBuffer());
/*  913 */     this.worldInfo.setBorderDamagePerBlock(getWorldBorder().getDamageAmount());
/*  914 */     this.worldInfo.setBorderWarningDistance(getWorldBorder().getWarningDistance());
/*  915 */     this.worldInfo.setBorderWarningTime(getWorldBorder().getWarningTime());
/*  916 */     this.worldInfo.setBorderLerpTarget(getWorldBorder().getTargetSize());
/*  917 */     this.worldInfo.setBorderLerpTime(getWorldBorder().getTimeUntilTarget());
/*  918 */     this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
/*  919 */     this.mapStorage.saveAllData();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEntityAdded(Entity entityIn) {
/*  924 */     super.onEntityAdded(entityIn);
/*  925 */     this.entitiesById.addKey(entityIn.getEntityId(), entityIn);
/*  926 */     this.entitiesByUuid.put(entityIn.getUniqueID(), entityIn);
/*  927 */     Entity[] aentity = entityIn.getParts();
/*      */     
/*  929 */     if (aentity != null)
/*      */     {
/*  931 */       for (int i = 0; i < aentity.length; i++)
/*      */       {
/*  933 */         this.entitiesById.addKey(aentity[i].getEntityId(), aentity[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEntityRemoved(Entity entityIn) {
/*  940 */     super.onEntityRemoved(entityIn);
/*  941 */     this.entitiesById.removeObject(entityIn.getEntityId());
/*  942 */     this.entitiesByUuid.remove(entityIn.getUniqueID());
/*  943 */     Entity[] aentity = entityIn.getParts();
/*      */     
/*  945 */     if (aentity != null)
/*      */     {
/*  947 */       for (int i = 0; i < aentity.length; i++)
/*      */       {
/*  949 */         this.entitiesById.removeObject(aentity[i].getEntityId());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addWeatherEffect(Entity entityIn) {
/*  959 */     if (super.addWeatherEffect(entityIn)) {
/*      */       
/*  961 */       this.mcServer.getConfigurationManager().sendToAllNear(entityIn.posX, entityIn.posY, entityIn.posZ, 512.0D, this.provider.getDimensionId(), (Packet)new S2CPacketSpawnGlobalEntity(entityIn));
/*  962 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  966 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityState(Entity entityIn, byte state) {
/*  975 */     this.theEntityTracker.func_151248_b(entityIn, (Packet)new S19PacketEntityStatus(entityIn, state));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion newExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
/*  983 */     Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
/*  984 */     explosion.doExplosionA();
/*  985 */     explosion.doExplosionB(false);
/*      */     
/*  987 */     if (!isSmoking)
/*      */     {
/*  989 */       explosion.clearAffectedBlockPositions();
/*      */     }
/*      */     
/*  992 */     for (EntityPlayer entityplayer : this.playerEntities) {
/*      */       
/*  994 */       if (entityplayer.getDistanceSq(x, y, z) < 4096.0D)
/*      */       {
/*  996 */         ((EntityPlayerMP)entityplayer).playerNetServerHandler.sendPacket((Packet)new S27PacketExplosion(x, y, z, strength, explosion.getAffectedBlockPositions(), explosion.getPlayerKnockbackMap().get(entityplayer)));
/*      */       }
/*      */     } 
/*      */     
/* 1000 */     return explosion;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
/* 1005 */     BlockEventData blockeventdata = new BlockEventData(pos, blockIn, eventID, eventParam);
/*      */     
/* 1007 */     for (BlockEventData blockeventdata1 : this.blockEventQueue[this.blockEventCacheIndex]) {
/*      */       
/* 1009 */       if (blockeventdata1.equals(blockeventdata)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1015 */     this.blockEventQueue[this.blockEventCacheIndex].add(blockeventdata);
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendQueuedBlockEvents() {
/* 1020 */     while (!this.blockEventQueue[this.blockEventCacheIndex].isEmpty()) {
/*      */       
/* 1022 */       int i = this.blockEventCacheIndex;
/* 1023 */       this.blockEventCacheIndex ^= 0x1;
/*      */       
/* 1025 */       for (BlockEventData blockeventdata : this.blockEventQueue[i]) {
/*      */         
/* 1027 */         if (fireBlockEvent(blockeventdata))
/*      */         {
/* 1029 */           this.mcServer.getConfigurationManager().sendToAllNear(blockeventdata.getPosition().getX(), blockeventdata.getPosition().getY(), blockeventdata.getPosition().getZ(), 64.0D, this.provider.getDimensionId(), (Packet)new S24PacketBlockAction(blockeventdata.getPosition(), blockeventdata.getBlock(), blockeventdata.getEventID(), blockeventdata.getEventParameter()));
/*      */         }
/*      */       } 
/*      */       
/* 1033 */       this.blockEventQueue[i].clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean fireBlockEvent(BlockEventData event) {
/* 1039 */     IBlockState iblockstate = getBlockState(event.getPosition());
/* 1040 */     return (iblockstate.getBlock() == event.getBlock()) ? iblockstate.getBlock().onBlockEventReceived(this, event.getPosition(), iblockstate, event.getEventID(), event.getEventParameter()) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() {
/* 1048 */     this.saveHandler.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateWeather() {
/* 1056 */     boolean flag = isRaining();
/* 1057 */     super.updateWeather();
/*      */     
/* 1059 */     if (this.prevRainingStrength != this.rainingStrength)
/*      */     {
/* 1061 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension((Packet)new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
/*      */     }
/*      */     
/* 1064 */     if (this.prevThunderingStrength != this.thunderingStrength)
/*      */     {
/* 1066 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension((Packet)new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
/*      */     }
/*      */     
/* 1069 */     if (flag != isRaining()) {
/*      */       
/* 1071 */       if (flag) {
/*      */         
/* 1073 */         this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(2, 0.0F));
/*      */       }
/*      */       else {
/*      */         
/* 1077 */         this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(1, 0.0F));
/*      */       } 
/*      */       
/* 1080 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(7, this.rainingStrength));
/* 1081 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(8, this.thunderingStrength));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getRenderDistanceChunks() {
/* 1087 */     return this.mcServer.getConfigurationManager().getViewDistance();
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftServer getMinecraftServer() {
/* 1092 */     return this.mcServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityTracker getEntityTracker() {
/* 1100 */     return this.theEntityTracker;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerManager getPlayerManager() {
/* 1105 */     return this.thePlayerManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public Teleporter getDefaultTeleporter() {
/* 1110 */     return this.worldTeleporter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... particleArguments) {
/* 1118 */     spawnParticle(particleType, false, xCoord, yCoord, zCoord, numberOfParticles, xOffset, yOffset, zOffset, particleSpeed, particleArguments);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, boolean longDistance, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... particleArguments) {
/* 1126 */     S2APacketParticles s2APacketParticles = new S2APacketParticles(particleType, longDistance, (float)xCoord, (float)yCoord, (float)zCoord, (float)xOffset, (float)yOffset, (float)zOffset, (float)particleSpeed, numberOfParticles, particleArguments);
/*      */     
/* 1128 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/* 1130 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)this.playerEntities.get(i);
/* 1131 */       BlockPos blockpos = entityplayermp.getPosition();
/* 1132 */       double d0 = blockpos.distanceSq(xCoord, yCoord, zCoord);
/*      */       
/* 1134 */       if (d0 <= 256.0D || (longDistance && d0 <= 65536.0D))
/*      */       {
/* 1136 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s2APacketParticles);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getEntityFromUuid(UUID uuid) {
/* 1143 */     return this.entitiesByUuid.get(uuid);
/*      */   }
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 1148 */     return this.mcServer.addScheduledTask(runnableToSchedule);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 1153 */     return this.mcServer.isCallingFromMinecraftThread();
/*      */   }
/*      */   
/*      */   static class ServerBlockEventList extends ArrayList<BlockEventData> {}
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\WorldServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */