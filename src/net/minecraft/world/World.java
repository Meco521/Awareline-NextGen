/*      */ package net.minecraft.world;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ 
/*      */ public abstract class World implements IBlockAccess {
/*   38 */   private int seaLevel = 63;
/*      */ 
/*      */   
/*      */   protected boolean scheduledUpdatesAreImmediate;
/*      */ 
/*      */   
/*   44 */   public final List<Entity> loadedEntityList = Lists.newArrayList();
/*   45 */   protected final List<Entity> unloadedEntityList = Lists.newArrayList();
/*   46 */   public final List<TileEntity> loadedTileEntityList = Lists.newArrayList();
/*   47 */   public final List<TileEntity> tickableTileEntities = Lists.newArrayList();
/*   48 */   private final List<TileEntity> addedTileEntityList = Lists.newArrayList();
/*   49 */   private final List<TileEntity> tileEntitiesToBeRemoved = Lists.newArrayList();
/*   50 */   public final List<EntityPlayer> playerEntities = Lists.newArrayList();
/*   51 */   public final List<Entity> weatherEffects = Lists.newArrayList();
/*   52 */   protected final IntHashMap<Entity> entitiesById = new IntHashMap();
/*   53 */   private long cloudColour = 16777215L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skylightSubtracted;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   protected int updateLCG = (new Random()).nextInt();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   70 */   protected final int DIST_HASH_MAGIC = 1013904223;
/*      */ 
/*      */   
/*      */   protected float prevRainingStrength;
/*      */   
/*      */   protected float rainingStrength;
/*      */   
/*      */   protected float prevThunderingStrength;
/*      */   
/*      */   protected float thunderingStrength;
/*      */   
/*      */   private int lastLightningBolt;
/*      */   
/*   83 */   public final Random rand = new Random();
/*      */   
/*      */   public final WorldProvider provider;
/*      */   
/*   87 */   protected List<IWorldAccess> worldAccesses = Lists.newArrayList();
/*      */ 
/*      */   
/*      */   protected IChunkProvider chunkProvider;
/*      */ 
/*      */   
/*      */   protected final ISaveHandler saveHandler;
/*      */ 
/*      */   
/*      */   protected WorldInfo worldInfo;
/*      */   
/*      */   protected boolean findingSpawnPoint;
/*      */   
/*      */   protected MapStorage mapStorage;
/*      */   
/*      */   protected VillageCollection villageCollectionObj;
/*      */   
/*      */   public final Profiler theProfiler;
/*      */   
/*  106 */   private final Calendar theCalendar = Calendar.getInstance();
/*  107 */   protected Scoreboard worldScoreboard = new Scoreboard();
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isRemote;
/*      */ 
/*      */ 
/*      */   
/*  115 */   protected Set<ChunkCoordIntPair> activeChunkSet = Sets.newHashSet();
/*      */ 
/*      */ 
/*      */   
/*      */   private int ambientTickCountdown;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean spawnHostileMobs;
/*      */ 
/*      */   
/*      */   protected boolean spawnPeacefulMobs;
/*      */ 
/*      */   
/*      */   private boolean processingLoadedTiles;
/*      */ 
/*      */   
/*      */   private final WorldBorder worldBorder;
/*      */ 
/*      */   
/*      */   int[] lightUpdateBlockList;
/*      */ 
/*      */ 
/*      */   
/*      */   protected World(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
/*  140 */     this.ambientTickCountdown = this.rand.nextInt(12000);
/*  141 */     this.spawnHostileMobs = true;
/*  142 */     this.spawnPeacefulMobs = true;
/*  143 */     this.lightUpdateBlockList = new int[32768];
/*  144 */     this.saveHandler = saveHandlerIn;
/*  145 */     this.theProfiler = profilerIn;
/*  146 */     this.worldInfo = info;
/*  147 */     this.provider = providerIn;
/*  148 */     this.isRemote = client;
/*  149 */     this.worldBorder = providerIn.getWorldBorder();
/*      */   }
/*      */   
/*      */   public World init() {
/*  153 */     return this;
/*      */   }
/*      */   
/*      */   public BiomeGenBase getBiomeGenForCoords(final BlockPos pos) {
/*  157 */     if (isBlockLoaded(pos)) {
/*  158 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*      */       
/*      */       try {
/*  161 */         return chunk.getBiome(pos, this.provider.getWorldChunkManager());
/*  162 */       } catch (Throwable throwable) {
/*  163 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting biome");
/*  164 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Coordinates of biome request");
/*  165 */         crashreportcategory.addCrashSectionCallable("Location", new Callable<String>() {
/*      */               public String call() throws Exception {
/*  167 */                 return CrashReportCategory.getCoordinateInfo(pos);
/*      */               }
/*      */             });
/*  170 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*  173 */     return this.provider.getWorldChunkManager().getBiomeGenerator(pos, BiomeGenBase.plains);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldChunkManager getWorldChunkManager() {
/*  178 */     return this.provider.getWorldChunkManager();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract IChunkProvider createChunkProvider();
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialize(WorldSettings settings) {
/*  188 */     this.worldInfo.setServerInitialized(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialSpawnLocation() {
/*  196 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getGroundAboveSeaLevel(BlockPos pos) {
/*  202 */     BlockPos blockpos = new BlockPos(pos.getX(), getSeaLevel(), pos.getZ());
/*  203 */     for (; !isAirBlock(blockpos.up()); blockpos = blockpos.up());
/*      */ 
/*      */ 
/*      */     
/*  207 */     return getBlockState(blockpos).getBlock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValid(BlockPos pos) {
/*  214 */     return (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000 && pos
/*  215 */       .getY() >= 0 && pos.getY() < 256);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAirBlock(BlockPos pos) {
/*  224 */     return (getBlockState(pos).getBlock().getMaterial() == Material.air);
/*      */   }
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos) {
/*  228 */     return isBlockLoaded(pos, true);
/*      */   }
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos, boolean allowEmpty) {
/*  232 */     return !isValid(pos) ? false : isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, allowEmpty);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius) {
/*  236 */     return isAreaLoaded(center, radius, true);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty) {
/*  240 */     return isAreaLoaded(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center
/*  241 */         .getX() + radius, center.getY() + radius, center.getZ() + radius, allowEmpty);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to) {
/*  245 */     return isAreaLoaded(from, to, true);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to, boolean allowEmpty) {
/*  249 */     return isAreaLoaded(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ(), allowEmpty);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box) {
/*  253 */     return isAreaLoaded(box, true);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box, boolean allowEmpty) {
/*  257 */     return isAreaLoaded(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, allowEmpty);
/*      */   }
/*      */   
/*      */   private boolean isAreaLoaded(int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd, boolean allowEmpty) {
/*  261 */     if (yEnd >= 0 && yStart < 256) {
/*  262 */       xStart >>= 4;
/*  263 */       zStart >>= 4;
/*  264 */       xEnd >>= 4;
/*  265 */       zEnd >>= 4;
/*      */       
/*  267 */       for (int i = xStart; i <= xEnd; i++) {
/*  268 */         for (int j = zStart; j <= zEnd; j++) {
/*  269 */           if (!isChunkLoaded(i, j, allowEmpty)) {
/*  270 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  275 */       return true;
/*      */     } 
/*  277 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
/*  282 */     return (this.chunkProvider.chunkExists(x, z) && (allowEmpty || !this.chunkProvider.provideChunk(x, z).isEmpty()));
/*      */   }
/*      */   
/*      */   public Chunk getChunkFromBlockCoords(BlockPos pos) {
/*  286 */     return getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ) {
/*  293 */     return this.chunkProvider.provideChunk(chunkX, chunkZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/*  303 */     if (!isValid(pos))
/*  304 */       return false; 
/*  305 */     if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/*  306 */       return false;
/*      */     }
/*  308 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  309 */     Block block = newState.getBlock();
/*  310 */     IBlockState iblockstate = chunk.setBlockState(pos, newState);
/*      */     
/*  312 */     if (iblockstate == null) {
/*  313 */       return false;
/*      */     }
/*  315 */     Block block1 = iblockstate.getBlock();
/*      */     
/*  317 */     if (block.getLightOpacity() != block1.getLightOpacity() || block
/*  318 */       .getLightValue() != block1.getLightValue()) {
/*  319 */       this.theProfiler.startSection("checkLight");
/*  320 */       checkLight(pos);
/*  321 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  324 */     if ((flags & 0x2) != 0 && (!this.isRemote || (flags & 0x4) == 0) && chunk.isPopulated()) {
/*  325 */       markBlockForUpdate(pos);
/*      */     }
/*      */     
/*  328 */     if (!this.isRemote && (flags & 0x1) != 0) {
/*  329 */       notifyNeighborsRespectDebug(pos, iblockstate.getBlock());
/*      */       
/*  331 */       if (block.hasComparatorInputOverride()) {
/*  332 */         updateComparatorOutputLevel(pos, block);
/*      */       }
/*      */     } 
/*      */     
/*  336 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockToAir(BlockPos pos) {
/*  342 */     return setBlockState(pos, Blocks.air.getDefaultState(), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
/*  350 */     IBlockState iblockstate = getBlockState(pos);
/*  351 */     Block block = iblockstate.getBlock();
/*      */     
/*  353 */     if (block.getMaterial() == Material.air) {
/*  354 */       return false;
/*      */     }
/*  356 */     playAuxSFX(2001, pos, Block.getStateId(iblockstate));
/*      */     
/*  358 */     if (dropBlock) {
/*  359 */       block.dropBlockAsItem(this, pos, iblockstate, 0);
/*      */     }
/*      */     
/*  362 */     return setBlockState(pos, Blocks.air.getDefaultState(), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState state) {
/*  370 */     return setBlockState(pos, state, 3);
/*      */   }
/*      */   
/*      */   public void markBlockForUpdate(BlockPos pos) {
/*  374 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  375 */       ((IWorldAccess)this.worldAccesses.get(i)).markBlockForUpdate(pos);
/*      */     }
/*      */   }
/*      */   
/*      */   public void notifyNeighborsRespectDebug(BlockPos pos, Block blockType) {
/*  380 */     if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
/*  381 */       notifyNeighborsOfStateChange(pos, blockType);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2) {
/*  389 */     if (x2 > z2) {
/*  390 */       int i = z2;
/*  391 */       z2 = x2;
/*  392 */       x2 = i;
/*      */     } 
/*      */     
/*  395 */     if (!this.provider.getHasNoSky()) {
/*  396 */       for (int j = x2; j <= z2; j++) {
/*  397 */         checkLightFor(EnumSkyBlock.SKY, new BlockPos(x1, j, z1));
/*      */       }
/*      */     }
/*      */     
/*  401 */     markBlockRangeForRenderUpdate(x1, x2, z1, x1, z2, z1);
/*      */   }
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax) {
/*  405 */     markBlockRangeForRenderUpdate(rangeMin.getX(), rangeMin.getY(), rangeMin.getZ(), rangeMax.getX(), rangeMax
/*  406 */         .getY(), rangeMax.getZ());
/*      */   }
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/*  410 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  411 */       ((IWorldAccess)this.worldAccesses.get(i)).markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType) {
/*  416 */     notifyBlockOfStateChange(pos.west(), blockType);
/*  417 */     notifyBlockOfStateChange(pos.east(), blockType);
/*  418 */     notifyBlockOfStateChange(pos.down(), blockType);
/*  419 */     notifyBlockOfStateChange(pos.up(), blockType);
/*  420 */     notifyBlockOfStateChange(pos.north(), blockType);
/*  421 */     notifyBlockOfStateChange(pos.south(), blockType);
/*      */   }
/*      */   
/*      */   public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide) {
/*  425 */     if (skipSide != EnumFacing.WEST) {
/*  426 */       notifyBlockOfStateChange(pos.west(), blockType);
/*      */     }
/*      */     
/*  429 */     if (skipSide != EnumFacing.EAST) {
/*  430 */       notifyBlockOfStateChange(pos.east(), blockType);
/*      */     }
/*      */     
/*  433 */     if (skipSide != EnumFacing.DOWN) {
/*  434 */       notifyBlockOfStateChange(pos.down(), blockType);
/*      */     }
/*      */     
/*  437 */     if (skipSide != EnumFacing.UP) {
/*  438 */       notifyBlockOfStateChange(pos.up(), blockType);
/*      */     }
/*      */     
/*  441 */     if (skipSide != EnumFacing.NORTH) {
/*  442 */       notifyBlockOfStateChange(pos.north(), blockType);
/*      */     }
/*      */     
/*  445 */     if (skipSide != EnumFacing.SOUTH) {
/*  446 */       notifyBlockOfStateChange(pos.south(), blockType);
/*      */     }
/*      */   }
/*      */   
/*      */   public void notifyBlockOfStateChange(BlockPos pos, final Block blockIn) {
/*  451 */     if (!this.isRemote) {
/*  452 */       IBlockState iblockstate = getBlockState(pos);
/*      */       
/*      */       try {
/*  455 */         iblockstate.getBlock().onNeighborBlockChange(this, pos, iblockstate, blockIn);
/*  456 */       } catch (Throwable throwable) {
/*  457 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while updating neighbours");
/*  458 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being updated");
/*  459 */         crashreportcategory.addCrashSectionCallable("Source block type", new Callable<String>() {
/*      */               public String call() throws Exception {
/*      */                 try {
/*  462 */                   return String.format("ID #%d (%s // %s)", new Object[] {
/*  463 */                         Integer.valueOf(Block.getIdFromBlock(this.val$blockIn)), this.val$blockIn
/*  464 */                         .getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName() });
/*  465 */                 } catch (Throwable var2) {
/*  466 */                   return "ID #" + Block.getIdFromBlock(blockIn);
/*      */                 } 
/*      */               }
/*      */             });
/*  470 */         CrashReportCategory.addBlockInfo(crashreportcategory, pos, iblockstate);
/*  471 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isBlockTickPending(BlockPos pos, Block blockType) {
/*  477 */     return false;
/*      */   }
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  481 */     return getChunkFromBlockCoords(pos).canSeeSky(pos);
/*      */   }
/*      */   
/*      */   public boolean canBlockSeeSky(BlockPos pos) {
/*  485 */     if (pos.getY() >= getSeaLevel()) {
/*  486 */       return canSeeSky(pos);
/*      */     }
/*  488 */     BlockPos blockpos = new BlockPos(pos.getX(), getSeaLevel(), pos.getZ());
/*      */     
/*  490 */     if (!canSeeSky(blockpos)) {
/*  491 */       return false;
/*      */     }
/*  493 */     for (blockpos = blockpos.down(); blockpos.getY() > pos.getY(); blockpos = blockpos.down()) {
/*  494 */       Block block = getBlockState(blockpos).getBlock();
/*      */       
/*  496 */       if (block.getLightOpacity() > 0 && !block.getMaterial().isLiquid()) {
/*  497 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  501 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLight(BlockPos pos) {
/*  507 */     if (pos.getY() < 0) {
/*  508 */       return 0;
/*      */     }
/*  510 */     if (pos.getY() >= 256) {
/*  511 */       pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */     }
/*      */     
/*  514 */     return getChunkFromBlockCoords(pos).getLightSubtracted(pos, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightFromNeighbors(BlockPos pos) {
/*  519 */     return getLight(pos, true);
/*      */   }
/*      */   
/*      */   public int getLight(BlockPos pos, boolean checkNeighbors) {
/*  523 */     if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000) {
/*  524 */       if (checkNeighbors && getBlockState(pos).getBlock().getUseNeighborBrightness()) {
/*  525 */         int i1 = getLight(pos.up(), false);
/*  526 */         int i = getLight(pos.east(), false);
/*  527 */         int j = getLight(pos.west(), false);
/*  528 */         int k = getLight(pos.south(), false);
/*  529 */         int l = getLight(pos.north(), false);
/*      */         
/*  531 */         if (i > i1) {
/*  532 */           i1 = i;
/*      */         }
/*      */         
/*  535 */         if (j > i1) {
/*  536 */           i1 = j;
/*      */         }
/*      */         
/*  539 */         if (k > i1) {
/*  540 */           i1 = k;
/*      */         }
/*      */         
/*  543 */         if (l > i1) {
/*  544 */           i1 = l;
/*      */         }
/*      */         
/*  547 */         return i1;
/*  548 */       }  if (pos.getY() < 0) {
/*  549 */         return 0;
/*      */       }
/*  551 */       if (pos.getY() >= 256) {
/*  552 */         pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */       }
/*      */       
/*  555 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*  556 */       return chunk.getLightSubtracted(pos, this.skylightSubtracted);
/*      */     } 
/*      */     
/*  559 */     return 15;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getHeight(BlockPos pos) {
/*      */     int i;
/*  570 */     if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000) {
/*  571 */       if (isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, true)) {
/*  572 */         i = getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4).getHeightValue(pos.getX() & 0xF, pos
/*  573 */             .getZ() & 0xF);
/*      */       } else {
/*  575 */         i = 0;
/*      */       } 
/*      */     } else {
/*  578 */       i = getSeaLevel() + 1;
/*      */     } 
/*      */     
/*  581 */     return new BlockPos(pos.getX(), i, pos.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChunksLowestHorizon(int x, int z) {
/*  588 */     if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000) {
/*  589 */       if (!isChunkLoaded(x >> 4, z >> 4, true)) {
/*  590 */         return 0;
/*      */       }
/*  592 */       Chunk chunk = getChunkFromChunkCoords(x >> 4, z >> 4);
/*  593 */       return chunk.getLowestHeight();
/*      */     } 
/*      */     
/*  596 */     return getSeaLevel() + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
/*  601 */     if (this.provider.getHasNoSky() && type == EnumSkyBlock.SKY) {
/*  602 */       return 0;
/*      */     }
/*  604 */     if (pos.getY() < 0) {
/*  605 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  608 */     if (!isValid(pos))
/*  609 */       return type.defaultLightValue; 
/*  610 */     if (!isBlockLoaded(pos))
/*  611 */       return type.defaultLightValue; 
/*  612 */     if (getBlockState(pos).getBlock().getUseNeighborBrightness()) {
/*  613 */       int i1 = getLightFor(type, pos.up());
/*  614 */       int i = getLightFor(type, pos.east());
/*  615 */       int j = getLightFor(type, pos.west());
/*  616 */       int k = getLightFor(type, pos.south());
/*  617 */       int l = getLightFor(type, pos.north());
/*      */       
/*  619 */       if (i > i1) {
/*  620 */         i1 = i;
/*      */       }
/*      */       
/*  623 */       if (j > i1) {
/*  624 */         i1 = j;
/*      */       }
/*      */       
/*  627 */       if (k > i1) {
/*  628 */         i1 = k;
/*      */       }
/*      */       
/*  631 */       if (l > i1) {
/*  632 */         i1 = l;
/*      */       }
/*      */       
/*  635 */       return i1;
/*      */     } 
/*  637 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  638 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock type, BlockPos pos) {
/*  644 */     if (pos.getY() < 0) {
/*  645 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  648 */     if (!isValid(pos))
/*  649 */       return type.defaultLightValue; 
/*  650 */     if (!isBlockLoaded(pos)) {
/*  651 */       return type.defaultLightValue;
/*      */     }
/*  653 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  654 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue) {
/*  659 */     if (isValid(pos) && 
/*  660 */       isBlockLoaded(pos)) {
/*  661 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*  662 */       chunk.setLightFor(type, pos, lightValue);
/*  663 */       notifyLightSet(pos);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/*  669 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  670 */       ((IWorldAccess)this.worldAccesses.get(i)).notifyLightSet(pos);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  675 */     int i = getLightFromNeighborsFor(EnumSkyBlock.SKY, pos);
/*  676 */     int j = getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos);
/*      */     
/*  678 */     if (j < lightValue) {
/*  679 */       j = lightValue;
/*      */     }
/*      */     
/*  682 */     return i << 20 | j << 4;
/*      */   }
/*      */   
/*      */   public float getLightBrightness(BlockPos pos) {
/*  686 */     return this.provider.getLightBrightnessTable()[getLightFromNeighbors(pos)];
/*      */   }
/*      */   
/*      */   public IBlockState getBlockState(BlockPos pos) {
/*  690 */     if (!isValid(pos)) {
/*  691 */       return Blocks.air.getDefaultState();
/*      */     }
/*  693 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  694 */     return chunk.getBlockState(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDaytime() {
/*  703 */     return (this.skylightSubtracted < 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_) {
/*  710 */     return rayTraceBlocks(p_72933_1_, p_72933_2_, false, false, false);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 start, Vec3 end, boolean stopOnLiquid) {
/*  714 */     return rayTraceBlocks(start, end, stopOnLiquid, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/*  723 */     if (!Double.isNaN(vec31.xCoord) && !Double.isNaN(vec31.yCoord) && !Double.isNaN(vec31.zCoord)) {
/*  724 */       if (!Double.isNaN(vec32.xCoord) && !Double.isNaN(vec32.yCoord) && !Double.isNaN(vec32.zCoord)) {
/*  725 */         int i = MathHelper.floor_double(vec32.xCoord);
/*  726 */         int j = MathHelper.floor_double(vec32.yCoord);
/*  727 */         int k = MathHelper.floor_double(vec32.zCoord);
/*  728 */         int l = MathHelper.floor_double(vec31.xCoord);
/*  729 */         int i1 = MathHelper.floor_double(vec31.yCoord);
/*  730 */         int j1 = MathHelper.floor_double(vec31.zCoord);
/*  731 */         BlockPos blockpos = new BlockPos(l, i1, j1);
/*  732 */         IBlockState iblockstate = getBlockState(blockpos);
/*  733 */         Block block = iblockstate.getBlock();
/*      */         
/*  735 */         if ((!ignoreBlockWithoutBoundingBox || block
/*  736 */           .getCollisionBoundingBox(this, blockpos, iblockstate) != null) && block
/*  737 */           .canCollideCheck(iblockstate, stopOnLiquid)) {
/*  738 */           MovingObjectPosition movingobjectposition = block.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */           
/*  740 */           if (movingobjectposition != null) {
/*  741 */             return movingobjectposition;
/*      */           }
/*      */         } 
/*      */         
/*  745 */         MovingObjectPosition movingobjectposition2 = null;
/*  746 */         int k1 = 200;
/*      */         
/*  748 */         while (k1-- >= 0) {
/*  749 */           EnumFacing enumfacing; if (Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord)) {
/*  750 */             return null;
/*      */           }
/*      */           
/*  753 */           if (l == i && i1 == j && j1 == k) {
/*  754 */             return returnLastUncollidableBlock ? movingobjectposition2 : null;
/*      */           }
/*      */           
/*  757 */           boolean flag2 = true;
/*  758 */           boolean flag = true;
/*  759 */           boolean flag1 = true;
/*  760 */           double d0 = 999.0D;
/*  761 */           double d1 = 999.0D;
/*  762 */           double d2 = 999.0D;
/*      */           
/*  764 */           if (i > l) {
/*  765 */             d0 = l + 1.0D;
/*  766 */           } else if (i < l) {
/*  767 */             d0 = l + 0.0D;
/*      */           } else {
/*  769 */             flag2 = false;
/*      */           } 
/*      */           
/*  772 */           if (j > i1) {
/*  773 */             d1 = i1 + 1.0D;
/*  774 */           } else if (j < i1) {
/*  775 */             d1 = i1 + 0.0D;
/*      */           } else {
/*  777 */             flag = false;
/*      */           } 
/*      */           
/*  780 */           if (k > j1) {
/*  781 */             d2 = j1 + 1.0D;
/*  782 */           } else if (k < j1) {
/*  783 */             d2 = j1 + 0.0D;
/*      */           } else {
/*  785 */             flag1 = false;
/*      */           } 
/*      */           
/*  788 */           double d3 = 999.0D;
/*  789 */           double d4 = 999.0D;
/*  790 */           double d5 = 999.0D;
/*  791 */           double d6 = vec32.xCoord - vec31.xCoord;
/*  792 */           double d7 = vec32.yCoord - vec31.yCoord;
/*  793 */           double d8 = vec32.zCoord - vec31.zCoord;
/*      */           
/*  795 */           if (flag2) {
/*  796 */             d3 = (d0 - vec31.xCoord) / d6;
/*      */           }
/*      */           
/*  799 */           if (flag) {
/*  800 */             d4 = (d1 - vec31.yCoord) / d7;
/*      */           }
/*      */           
/*  803 */           if (flag1) {
/*  804 */             d5 = (d2 - vec31.zCoord) / d8;
/*      */           }
/*      */           
/*  807 */           if (d3 == -0.0D) {
/*  808 */             d3 = -1.0E-4D;
/*      */           }
/*      */           
/*  811 */           if (d4 == -0.0D) {
/*  812 */             d4 = -1.0E-4D;
/*      */           }
/*      */           
/*  815 */           if (d5 == -0.0D) {
/*  816 */             d5 = -1.0E-4D;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  821 */           if (d3 < d4 && d3 < d5) {
/*  822 */             enumfacing = (i > l) ? EnumFacing.WEST : EnumFacing.EAST;
/*  823 */             vec31 = new Vec3(d0, vec31.yCoord + d7 * d3, vec31.zCoord + d8 * d3);
/*  824 */           } else if (d4 < d5) {
/*  825 */             enumfacing = (j > i1) ? EnumFacing.DOWN : EnumFacing.UP;
/*  826 */             vec31 = new Vec3(vec31.xCoord + d6 * d4, d1, vec31.zCoord + d8 * d4);
/*      */           } else {
/*  828 */             enumfacing = (k > j1) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/*  829 */             vec31 = new Vec3(vec31.xCoord + d6 * d5, vec31.yCoord + d7 * d5, d2);
/*      */           } 
/*      */           
/*  832 */           l = MathHelper.floor_double(vec31.xCoord) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
/*  833 */           i1 = MathHelper.floor_double(vec31.yCoord) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
/*  834 */           j1 = MathHelper.floor_double(vec31.zCoord) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
/*  835 */           blockpos = new BlockPos(l, i1, j1);
/*  836 */           IBlockState iblockstate1 = getBlockState(blockpos);
/*  837 */           Block block1 = iblockstate1.getBlock();
/*      */           
/*  839 */           if (!ignoreBlockWithoutBoundingBox || block1
/*  840 */             .getCollisionBoundingBox(this, blockpos, iblockstate1) != null) {
/*  841 */             if (block1.canCollideCheck(iblockstate1, stopOnLiquid)) {
/*  842 */               MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */ 
/*      */               
/*  845 */               if (movingobjectposition1 != null)
/*  846 */                 return movingobjectposition1; 
/*      */               continue;
/*      */             } 
/*  849 */             movingobjectposition2 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec31, enumfacing, blockpos);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  855 */         return returnLastUncollidableBlock ? movingobjectposition2 : null;
/*      */       } 
/*  857 */       return null;
/*      */     } 
/*      */     
/*  860 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundAtEntity(Entity entityIn, String name, float volume, float pitch) {
/*  869 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  870 */       ((IWorldAccess)this.worldAccesses.get(i)).playSound(name, entityIn.posX, entityIn.posY, entityIn.posZ, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundToNearExcept(EntityPlayer player, String name, float volume, float pitch) {
/*  879 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  880 */       ((IWorldAccess)this.worldAccesses.get(i)).playSoundToNearExcept(player, name, player.posX, player.posY, player.posZ, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch) {
/*  892 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  893 */       ((IWorldAccess)this.worldAccesses.get(i)).playSound(soundName, x, y, z, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void playRecord(BlockPos pos, String name) {
/*  905 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  906 */       ((IWorldAccess)this.worldAccesses.get(i)).playRecord(name, pos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175688_14_) {
/*  912 */     spawnParticle(particleType.getParticleID(), particleType.getShouldIgnoreRange(), xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175682_15_) {
/*  918 */     spawnParticle(particleType.getParticleID(), particleType.getShouldIgnoreRange() | p_175682_2_, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175682_15_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void spawnParticle(int particleID, boolean p_175720_2_, double xCood, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175720_15_) {
/*  924 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  925 */       ((IWorldAccess)this.worldAccesses.get(i)).spawnParticle(particleID, p_175720_2_, xCood, yCoord, zCoord, xOffset, yOffset, zOffset, p_175720_15_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addWeatherEffect(Entity entityIn) {
/*  934 */     this.weatherEffects.add(entityIn);
/*  935 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean spawnEntityInWorld(Entity entityIn) {
/*  942 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/*  943 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*  944 */     boolean flag = entityIn.forceSpawn;
/*      */     
/*  946 */     if (entityIn instanceof EntityPlayer) {
/*  947 */       flag = true;
/*      */     }
/*      */     
/*  950 */     if (!flag && !isChunkLoaded(i, j, true)) {
/*  951 */       return false;
/*      */     }
/*  953 */     if (entityIn instanceof EntityPlayer) {
/*  954 */       EntityPlayer entityplayer = (EntityPlayer)entityIn;
/*  955 */       this.playerEntities.add(entityplayer);
/*  956 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/*  959 */     getChunkFromChunkCoords(i, j).addEntity(entityIn);
/*  960 */     this.loadedEntityList.add(entityIn);
/*  961 */     onEntityAdded(entityIn);
/*  962 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEntityAdded(Entity entityIn) {
/*  967 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  968 */       ((IWorldAccess)this.worldAccesses.get(i)).onEntityAdded(entityIn);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void onEntityRemoved(Entity entityIn) {
/*  973 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  974 */       ((IWorldAccess)this.worldAccesses.get(i)).onEntityRemoved(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/*  983 */     if (entityIn.riddenByEntity != null) {
/*  984 */       entityIn.riddenByEntity.mountEntity((Entity)null);
/*      */     }
/*      */     
/*  987 */     if (entityIn.ridingEntity != null) {
/*  988 */       entityIn.mountEntity((Entity)null);
/*      */     }
/*      */     
/*  991 */     entityIn.setDead();
/*      */     
/*  993 */     if (entityIn instanceof EntityPlayer) {
/*  994 */       this.playerEntities.remove(entityIn);
/*  995 */       updateAllPlayersSleepingFlag();
/*  996 */       onEntityRemoved(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePlayerEntityDangerously(Entity entityIn) {
/* 1004 */     entityIn.setDead();
/*      */     
/* 1006 */     if (entityIn instanceof EntityPlayer) {
/* 1007 */       this.playerEntities.remove(entityIn);
/* 1008 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/* 1011 */     int i = entityIn.chunkCoordX;
/* 1012 */     int j = entityIn.chunkCoordZ;
/*      */     
/* 1014 */     if (entityIn.addedToChunk && isChunkLoaded(i, j, true)) {
/* 1015 */       getChunkFromChunkCoords(i, j).removeEntity(entityIn);
/*      */     }
/*      */     
/* 1018 */     this.loadedEntityList.remove(entityIn);
/* 1019 */     onEntityRemoved(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addWorldAccess(IWorldAccess worldAccess) {
/* 1026 */     this.worldAccesses.add(worldAccess);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeWorldAccess(IWorldAccess worldAccess) {
/* 1033 */     this.worldAccesses.remove(worldAccess);
/*      */   }
/*      */   
/*      */   public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity entityIn, AxisAlignedBB bb) {
/* 1037 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 1038 */     int i = MathHelper.floor_double(bb.minX);
/* 1039 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1040 */     int k = MathHelper.floor_double(bb.minY);
/* 1041 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1042 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1043 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1044 */     WorldBorder worldborder = getWorldBorder();
/* 1045 */     boolean flag = entityIn.isOutsideBorder();
/* 1046 */     boolean flag1 = isInsideBorder(worldborder, entityIn);
/* 1047 */     IBlockState iblockstate = Blocks.stone.getDefaultState();
/* 1048 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1050 */     for (int k1 = i; k1 < j; k1++) {
/* 1051 */       for (int l1 = i1; l1 < j1; l1++) {
/* 1052 */         if (isBlockLoaded((BlockPos)blockpos$mutableblockpos.set(k1, 64, l1))) {
/* 1053 */           for (int i2 = k - 1; i2 < l; i2++) {
/* 1054 */             blockpos$mutableblockpos.set(k1, i2, l1);
/*      */             
/* 1056 */             if (flag && flag1) {
/* 1057 */               entityIn.setOutsideBorder(false);
/* 1058 */             } else if (!flag && !flag1) {
/* 1059 */               entityIn.setOutsideBorder(true);
/*      */             } 
/*      */             
/* 1062 */             IBlockState iblockstate1 = iblockstate;
/*      */             
/* 1064 */             if (worldborder.contains((BlockPos)blockpos$mutableblockpos) || !flag1) {
/* 1065 */               iblockstate1 = getBlockState((BlockPos)blockpos$mutableblockpos);
/*      */             }
/*      */             
/* 1068 */             iblockstate1.getBlock().addCollisionBoxesToList(this, (BlockPos)blockpos$mutableblockpos, iblockstate1, bb, list, entityIn);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1075 */     double d0 = 0.25D;
/* 1076 */     List<Entity> list1 = getEntitiesWithinAABBExcludingEntity(entityIn, bb.expand(d0, d0, d0));
/*      */     
/* 1078 */     for (int j2 = 0; j2 < list1.size(); j2++) {
/* 1079 */       if (entityIn.riddenByEntity != list1 && entityIn.ridingEntity != list1) {
/* 1080 */         AxisAlignedBB axisalignedbb = ((Entity)list1.get(j2)).getCollisionBoundingBox();
/*      */         
/* 1082 */         if (axisalignedbb != null && axisalignedbb.intersectsWith(bb)) {
/* 1083 */           list.add(axisalignedbb);
/*      */         }
/*      */         
/* 1086 */         axisalignedbb = entityIn.getCollisionBox(list1.get(j2));
/*      */         
/* 1088 */         if (axisalignedbb != null && axisalignedbb.intersectsWith(bb)) {
/* 1089 */           list.add(axisalignedbb);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1094 */     return list;
/*      */   }
/*      */   
/*      */   public boolean isInsideBorder(WorldBorder worldBorderIn, Entity entityIn) {
/* 1098 */     double d0 = worldBorderIn.minX();
/* 1099 */     double d1 = worldBorderIn.minZ();
/* 1100 */     double d2 = worldBorderIn.maxX();
/* 1101 */     double d3 = worldBorderIn.maxZ();
/*      */     
/* 1103 */     if (entityIn.isOutsideBorder()) {
/* 1104 */       d0++;
/* 1105 */       d1++;
/* 1106 */       d2--;
/* 1107 */       d3--;
/*      */     } else {
/* 1109 */       d0--;
/* 1110 */       d1--;
/* 1111 */       d2++;
/* 1112 */       d3++;
/*      */     } 
/*      */     
/* 1115 */     return (entityIn.posX > d0 && entityIn.posX < d2 && entityIn.posZ > d1 && entityIn.posZ < d3);
/*      */   }
/*      */   
/*      */   public List<AxisAlignedBB> getCollisionBoxes(AxisAlignedBB bb) {
/* 1119 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 1120 */     int i = MathHelper.floor_double(bb.minX);
/* 1121 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1122 */     int k = MathHelper.floor_double(bb.minY);
/* 1123 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1124 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1125 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1126 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1128 */     for (int k1 = i; k1 < j; k1++) {
/* 1129 */       for (int l1 = i1; l1 < j1; l1++) {
/* 1130 */         if (isBlockLoaded((BlockPos)blockpos$mutableblockpos.set(k1, 64, l1))) {
/* 1131 */           for (int i2 = k - 1; i2 < l; i2++) {
/* 1132 */             IBlockState iblockstate; blockpos$mutableblockpos.set(k1, i2, l1);
/*      */ 
/*      */             
/* 1135 */             if (k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000) {
/* 1136 */               iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/*      */             } else {
/* 1138 */               iblockstate = Blocks.bedrock.getDefaultState();
/*      */             } 
/*      */             
/* 1141 */             iblockstate.getBlock().addCollisionBoxesToList(this, (BlockPos)blockpos$mutableblockpos, iblockstate, bb, list, (Entity)null);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1148 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int calculateSkylightSubtracted(float p_72967_1_) {
/* 1155 */     float f = getCelestialAngle(p_72967_1_);
/* 1156 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1157 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1158 */     f1 = 1.0F - f1;
/* 1159 */     f1 = (float)(f1 * (1.0D - (getRainStrength(p_72967_1_) * 5.0F) / 16.0D));
/* 1160 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(p_72967_1_) * 5.0F) / 16.0D));
/* 1161 */     f1 = 1.0F - f1;
/* 1162 */     return (int)(f1 * 11.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSunBrightness(float p_72971_1_) {
/* 1169 */     float f = getCelestialAngle(p_72971_1_);
/* 1170 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.2F;
/* 1171 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1172 */     f1 = 1.0F - f1;
/* 1173 */     f1 = (float)(f1 * (1.0D - (getRainStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1174 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1175 */     return f1 * 0.8F + 0.2F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getSkyColor(Entity entityIn, float partialTicks) {
/* 1182 */     float f = getCelestialAngle(partialTicks);
/* 1183 */     float f1 = MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1184 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1185 */     int i = MathHelper.floor_double(entityIn.posX);
/* 1186 */     int j = MathHelper.floor_double(entityIn.posY);
/* 1187 */     int k = MathHelper.floor_double(entityIn.posZ);
/* 1188 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1189 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(blockpos);
/* 1190 */     float f2 = biomegenbase.getFloatTemperature(blockpos);
/* 1191 */     int l = biomegenbase.getSkyColorByTemp(f2);
/* 1192 */     float f3 = (l >> 16 & 0xFF) / 255.0F;
/* 1193 */     float f4 = (l >> 8 & 0xFF) / 255.0F;
/* 1194 */     float f5 = (l & 0xFF) / 255.0F;
/* 1195 */     f3 *= f1;
/* 1196 */     f4 *= f1;
/* 1197 */     f5 *= f1;
/* 1198 */     float f6 = getRainStrength(partialTicks);
/*      */     
/* 1200 */     if (f6 > 0.0F) {
/* 1201 */       float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
/* 1202 */       float f8 = 1.0F - f6 * 0.75F;
/* 1203 */       f3 = f3 * f8 + f7 * (1.0F - f8);
/* 1204 */       f4 = f4 * f8 + f7 * (1.0F - f8);
/* 1205 */       f5 = f5 * f8 + f7 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1208 */     float f10 = getThunderStrength(partialTicks);
/*      */     
/* 1210 */     if (f10 > 0.0F) {
/* 1211 */       float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
/* 1212 */       float f9 = 1.0F - f10 * 0.75F;
/* 1213 */       f3 = f3 * f9 + f11 * (1.0F - f9);
/* 1214 */       f4 = f4 * f9 + f11 * (1.0F - f9);
/* 1215 */       f5 = f5 * f9 + f11 * (1.0F - f9);
/*      */     } 
/*      */     
/* 1218 */     if (this.lastLightningBolt > 0) {
/* 1219 */       float f12 = this.lastLightningBolt - partialTicks;
/*      */       
/* 1221 */       if (f12 > 1.0F) {
/* 1222 */         f12 = 1.0F;
/*      */       }
/*      */       
/* 1225 */       f12 *= 0.45F;
/* 1226 */       f3 = f3 * (1.0F - f12) + 0.8F * f12;
/* 1227 */       f4 = f4 * (1.0F - f12) + 0.8F * f12;
/* 1228 */       f5 = f5 * (1.0F - f12) + 1.0F * f12;
/*      */     } 
/*      */     
/* 1231 */     return new Vec3(f3, f4, f5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngle(float partialTicks) {
/* 1238 */     return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), partialTicks);
/*      */   }
/*      */   
/*      */   public int getMoonPhase() {
/* 1242 */     return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCurrentMoonPhaseFactor() {
/* 1250 */     return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngleRadians(float partialTicks) {
/* 1257 */     float f = getCelestialAngle(partialTicks);
/* 1258 */     return f * 3.1415927F * 2.0F;
/*      */   }
/*      */   
/*      */   public Vec3 getCloudColour(float partialTicks) {
/* 1262 */     float f = getCelestialAngle(partialTicks);
/* 1263 */     float f1 = MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1264 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1265 */     float f2 = (float)(this.cloudColour >> 16L & 0xFFL) / 255.0F;
/* 1266 */     float f3 = (float)(this.cloudColour >> 8L & 0xFFL) / 255.0F;
/* 1267 */     float f4 = (float)(this.cloudColour & 0xFFL) / 255.0F;
/* 1268 */     float f5 = getRainStrength(partialTicks);
/*      */     
/* 1270 */     if (f5 > 0.0F) {
/* 1271 */       float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
/* 1272 */       float f7 = 1.0F - f5 * 0.95F;
/* 1273 */       f2 = f2 * f7 + f6 * (1.0F - f7);
/* 1274 */       f3 = f3 * f7 + f6 * (1.0F - f7);
/* 1275 */       f4 = f4 * f7 + f6 * (1.0F - f7);
/*      */     } 
/*      */     
/* 1278 */     f2 *= f1 * 0.9F + 0.1F;
/* 1279 */     f3 *= f1 * 0.9F + 0.1F;
/* 1280 */     f4 *= f1 * 0.85F + 0.15F;
/* 1281 */     float f9 = getThunderStrength(partialTicks);
/*      */     
/* 1283 */     if (f9 > 0.0F) {
/* 1284 */       float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
/* 1285 */       float f8 = 1.0F - f9 * 0.95F;
/* 1286 */       f2 = f2 * f8 + f10 * (1.0F - f8);
/* 1287 */       f3 = f3 * f8 + f10 * (1.0F - f8);
/* 1288 */       f4 = f4 * f8 + f10 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1291 */     return new Vec3(f2, f3, f4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getFogColor(float partialTicks) {
/* 1298 */     float f = getCelestialAngle(partialTicks);
/* 1299 */     return this.provider.getFogColor(f, partialTicks);
/*      */   }
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/* 1303 */     return getChunkFromBlockCoords(pos).getPrecipitationHeight(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
/* 1311 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*      */ 
/*      */ 
/*      */     
/* 1315 */     BlockPos blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ());
/* 1316 */     for (; blockpos.getY() >= 0; blockpos = blockpos1) {
/* 1317 */       BlockPos blockpos1 = blockpos.down();
/* 1318 */       Material material = chunk.getBlock(blockpos1).getMaterial();
/*      */       
/* 1320 */       if (material.blocksMovement() && material != Material.leaves) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1325 */     return blockpos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getStarBrightness(float partialTicks) {
/* 1332 */     float f = getCelestialAngle(partialTicks);
/* 1333 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.25F;
/* 1334 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1335 */     return f1 * f1 * 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */   
/*      */   public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */   
/*      */   public void updateEntities() {
/* 1351 */     this.theProfiler.startSection("entities");
/* 1352 */     this.theProfiler.startSection("global");
/*      */     
/* 1354 */     for (int i = 0; i < this.weatherEffects.size(); i++) {
/* 1355 */       Entity entity = this.weatherEffects.get(i);
/*      */       
/*      */       try {
/* 1358 */         entity.ticksExisted++;
/* 1359 */         entity.onUpdate();
/* 1360 */       } catch (Throwable throwable2) {
/* 1361 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
/* 1362 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being ticked");
/*      */         
/* 1364 */         if (entity == null) {
/* 1365 */           crashreportcategory.addCrashSection("Entity", "~~NULL~~");
/*      */         } else {
/* 1367 */           entity.addEntityCrashInfo(crashreportcategory);
/*      */         } 
/*      */         
/* 1370 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1373 */       if (entity.isDead) {
/* 1374 */         this.weatherEffects.remove(i--);
/*      */       }
/*      */     } 
/*      */     
/* 1378 */     this.theProfiler.endStartSection("remove");
/* 1379 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*      */     
/* 1381 */     for (int k = 0; k < this.unloadedEntityList.size(); k++) {
/* 1382 */       Entity entity1 = this.unloadedEntityList.get(k);
/* 1383 */       int j = entity1.chunkCoordX;
/* 1384 */       int l1 = entity1.chunkCoordZ;
/*      */       
/* 1386 */       if (entity1.addedToChunk && isChunkLoaded(j, l1, true)) {
/* 1387 */         getChunkFromChunkCoords(j, l1).removeEntity(entity1);
/*      */       }
/*      */     } 
/*      */     
/* 1391 */     for (int l = 0; l < this.unloadedEntityList.size(); l++) {
/* 1392 */       onEntityRemoved(this.unloadedEntityList.get(l));
/*      */     }
/*      */     
/* 1395 */     this.unloadedEntityList.clear();
/* 1396 */     this.theProfiler.endStartSection("regular");
/*      */     
/* 1398 */     for (int i1 = 0; i1 < this.loadedEntityList.size(); i1++) {
/* 1399 */       Entity entity2 = this.loadedEntityList.get(i1);
/*      */       
/* 1401 */       if (entity2.ridingEntity != null) {
/* 1402 */         if (!entity2.ridingEntity.isDead && entity2.ridingEntity.riddenByEntity == entity2) {
/*      */           continue;
/*      */         }
/*      */         
/* 1406 */         entity2.ridingEntity.riddenByEntity = null;
/* 1407 */         entity2.ridingEntity = null;
/*      */       } 
/*      */       
/* 1410 */       this.theProfiler.startSection("tick");
/*      */       
/* 1412 */       if (!entity2.isDead) {
/*      */         try {
/* 1414 */           updateEntity(entity2);
/* 1415 */         } catch (Throwable throwable1) {
/* 1416 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable1, "Ticking entity");
/* 1417 */           CrashReportCategory crashreportcategory2 = crashreport1.makeCategory("Entity being ticked");
/* 1418 */           entity2.addEntityCrashInfo(crashreportcategory2);
/* 1419 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */       
/* 1423 */       this.theProfiler.endSection();
/* 1424 */       this.theProfiler.startSection("remove");
/*      */       
/* 1426 */       if (entity2.isDead) {
/* 1427 */         int k1 = entity2.chunkCoordX;
/* 1428 */         int i2 = entity2.chunkCoordZ;
/*      */         
/* 1430 */         if (entity2.addedToChunk && isChunkLoaded(k1, i2, true)) {
/* 1431 */           getChunkFromChunkCoords(k1, i2).removeEntity(entity2);
/*      */         }
/*      */         
/* 1434 */         this.loadedEntityList.remove(i1--);
/* 1435 */         onEntityRemoved(entity2);
/*      */       } 
/*      */       
/* 1438 */       this.theProfiler.endSection();
/*      */       continue;
/*      */     } 
/* 1441 */     this.theProfiler.endStartSection("blockEntities");
/* 1442 */     this.processingLoadedTiles = true;
/* 1443 */     Iterator<TileEntity> iterator = this.tickableTileEntities.iterator();
/*      */     
/* 1445 */     while (iterator.hasNext()) {
/* 1446 */       TileEntity tileentity = iterator.next();
/*      */       
/* 1448 */       if (!tileentity.isInvalid() && tileentity.hasWorldObj()) {
/* 1449 */         BlockPos blockpos = tileentity.getPos();
/*      */         
/* 1451 */         if (isBlockLoaded(blockpos) && this.worldBorder.contains(blockpos)) {
/*      */           try {
/* 1453 */             ((ITickable)tileentity).update();
/* 1454 */           } catch (Throwable throwable) {
/* 1455 */             CrashReport crashreport2 = CrashReport.makeCrashReport(throwable, "Ticking block entity");
/*      */             
/* 1457 */             CrashReportCategory crashreportcategory1 = crashreport2.makeCategory("Block entity being ticked");
/* 1458 */             tileentity.addInfoToCrashReport(crashreportcategory1);
/* 1459 */             throw new ReportedException(crashreport2);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1464 */       if (tileentity.isInvalid()) {
/* 1465 */         iterator.remove();
/* 1466 */         this.loadedTileEntityList.remove(tileentity);
/*      */         
/* 1468 */         if (isBlockLoaded(tileentity.getPos())) {
/* 1469 */           getChunkFromBlockCoords(tileentity.getPos()).removeTileEntity(tileentity.getPos());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1474 */     this.processingLoadedTiles = false;
/*      */     
/* 1476 */     if (!this.tileEntitiesToBeRemoved.isEmpty()) {
/* 1477 */       this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
/* 1478 */       this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
/* 1479 */       this.tileEntitiesToBeRemoved.clear();
/*      */     } 
/*      */     
/* 1482 */     this.theProfiler.endStartSection("pendingBlockEntities");
/*      */     
/* 1484 */     if (!this.addedTileEntityList.isEmpty()) {
/* 1485 */       for (int j1 = 0; j1 < this.addedTileEntityList.size(); j1++) {
/* 1486 */         TileEntity tileentity1 = this.addedTileEntityList.get(j1);
/*      */         
/* 1488 */         if (!tileentity1.isInvalid()) {
/* 1489 */           if (!this.loadedTileEntityList.contains(tileentity1)) {
/* 1490 */             addTileEntity(tileentity1);
/*      */           }
/*      */           
/* 1493 */           if (isBlockLoaded(tileentity1.getPos())) {
/* 1494 */             getChunkFromBlockCoords(tileentity1.getPos()).addTileEntity(tileentity1.getPos(), tileentity1);
/*      */           }
/*      */ 
/*      */           
/* 1498 */           markBlockForUpdate(tileentity1.getPos());
/*      */         } 
/*      */       } 
/*      */       
/* 1502 */       this.addedTileEntityList.clear();
/*      */     } 
/*      */     
/* 1505 */     this.theProfiler.endSection();
/* 1506 */     this.theProfiler.endSection();
/*      */   }
/*      */   
/*      */   public boolean addTileEntity(TileEntity tile) {
/* 1510 */     boolean flag = this.loadedTileEntityList.add(tile);
/*      */     
/* 1512 */     if (flag && tile instanceof ITickable) {
/* 1513 */       this.tickableTileEntities.add(tile);
/*      */     }
/*      */     
/* 1516 */     return flag;
/*      */   }
/*      */   
/*      */   public void addTileEntities(Collection<TileEntity> tileEntityCollection) {
/* 1520 */     if (this.processingLoadedTiles) {
/* 1521 */       this.addedTileEntityList.addAll(tileEntityCollection);
/*      */     } else {
/* 1523 */       for (TileEntity tileentity : tileEntityCollection) {
/* 1524 */         this.loadedTileEntityList.add(tileentity);
/*      */         
/* 1526 */         if (tileentity instanceof ITickable) {
/* 1527 */           this.tickableTileEntities.add(tileentity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntity(Entity ent) {
/* 1538 */     updateEntityWithOptionalForce(ent, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
/* 1546 */     int i = MathHelper.floor_double(entityIn.posX);
/* 1547 */     int j = MathHelper.floor_double(entityIn.posZ);
/* 1548 */     int k = 32;
/*      */     
/* 1550 */     if (!forceUpdate || isAreaLoaded(i - k, 0, j - k, i + k, 0, j + k, true)) {
/* 1551 */       entityIn.lastTickPosX = entityIn.posX;
/* 1552 */       entityIn.lastTickPosY = entityIn.posY;
/* 1553 */       entityIn.lastTickPosZ = entityIn.posZ;
/* 1554 */       entityIn.prevRotationYaw = entityIn.rotationYaw;
/* 1555 */       entityIn.prevRotationPitch = entityIn.rotationPitch;
/*      */       
/* 1557 */       if (forceUpdate && entityIn.addedToChunk) {
/* 1558 */         entityIn.ticksExisted++;
/*      */         
/* 1560 */         if (entityIn.ridingEntity != null) {
/* 1561 */           entityIn.updateRidden();
/*      */         } else {
/* 1563 */           entityIn.onUpdate();
/*      */         } 
/*      */       } 
/*      */       
/* 1567 */       this.theProfiler.startSection("chunkCheck");
/*      */       
/* 1569 */       if (Double.isNaN(entityIn.posX) || Double.isInfinite(entityIn.posX)) {
/* 1570 */         entityIn.posX = entityIn.lastTickPosX;
/*      */       }
/*      */       
/* 1573 */       if (Double.isNaN(entityIn.posY) || Double.isInfinite(entityIn.posY)) {
/* 1574 */         entityIn.posY = entityIn.lastTickPosY;
/*      */       }
/*      */       
/* 1577 */       if (Double.isNaN(entityIn.posZ) || Double.isInfinite(entityIn.posZ)) {
/* 1578 */         entityIn.posZ = entityIn.lastTickPosZ;
/*      */       }
/*      */       
/* 1581 */       if (Double.isNaN(entityIn.rotationPitch) || Double.isInfinite(entityIn.rotationPitch)) {
/* 1582 */         entityIn.rotationPitch = entityIn.prevRotationPitch;
/*      */       }
/*      */       
/* 1585 */       if (Double.isNaN(entityIn.rotationYaw) || Double.isInfinite(entityIn.rotationYaw)) {
/* 1586 */         entityIn.rotationYaw = entityIn.prevRotationYaw;
/*      */       }
/*      */       
/* 1589 */       int l = MathHelper.floor_double(entityIn.posX / 16.0D);
/* 1590 */       int i1 = MathHelper.floor_double(entityIn.posY / 16.0D);
/* 1591 */       int j1 = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*      */       
/* 1593 */       if (!entityIn.addedToChunk || entityIn.chunkCoordX != l || entityIn.chunkCoordY != i1 || entityIn.chunkCoordZ != j1) {
/*      */         
/* 1595 */         if (entityIn.addedToChunk && isChunkLoaded(entityIn.chunkCoordX, entityIn.chunkCoordZ, true)) {
/* 1596 */           getChunkFromChunkCoords(entityIn.chunkCoordX, entityIn.chunkCoordZ)
/* 1597 */             .removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */         }
/*      */         
/* 1600 */         if (isChunkLoaded(l, j1, true)) {
/* 1601 */           entityIn.addedToChunk = true;
/* 1602 */           getChunkFromChunkCoords(l, j1).addEntity(entityIn);
/*      */         } else {
/* 1604 */           entityIn.addedToChunk = false;
/*      */         } 
/*      */       } 
/*      */       
/* 1608 */       this.theProfiler.endSection();
/*      */       
/* 1610 */       if (forceUpdate && entityIn.addedToChunk && entityIn.riddenByEntity != null) {
/* 1611 */         if (!entityIn.riddenByEntity.isDead && entityIn.riddenByEntity.ridingEntity == entityIn) {
/* 1612 */           updateEntity(entityIn.riddenByEntity);
/*      */         } else {
/* 1614 */           entityIn.riddenByEntity.ridingEntity = null;
/* 1615 */           entityIn.riddenByEntity = null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb) {
/* 1626 */     return checkNoEntityCollision(bb, (Entity)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb, Entity entityIn) {
/* 1634 */     List<Entity> list = getEntitiesWithinAABBExcludingEntity((Entity)null, bb);
/*      */     
/* 1636 */     for (int i = 0; i < list.size(); i++) {
/* 1637 */       Entity entity = list.get(i);
/*      */       
/* 1639 */       if (!entity.isDead && entity.preventEntitySpawning && entity != entityIn && (entityIn == null || (entityIn.ridingEntity != entity && entityIn.riddenByEntity != entity)))
/*      */       {
/* 1641 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1645 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkBlockCollision(AxisAlignedBB bb) {
/* 1653 */     int i = MathHelper.floor_double(bb.minX);
/* 1654 */     int j = MathHelper.floor_double(bb.maxX);
/* 1655 */     int k = MathHelper.floor_double(bb.minY);
/* 1656 */     int l = MathHelper.floor_double(bb.maxY);
/* 1657 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1658 */     int j1 = MathHelper.floor_double(bb.maxZ);
/* 1659 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1661 */     for (int k1 = i; k1 <= j; k1++) {
/* 1662 */       for (int l1 = k; l1 <= l; l1++) {
/* 1663 */         for (int i2 = i1; i2 <= j1; i2++) {
/* 1664 */           Block block = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock();
/*      */           
/* 1666 */           if (block.getMaterial() != Material.air) {
/* 1667 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1673 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnyLiquid(AxisAlignedBB bb) {
/* 1680 */     int i = MathHelper.floor_double(bb.minX);
/* 1681 */     int j = MathHelper.floor_double(bb.maxX);
/* 1682 */     int k = MathHelper.floor_double(bb.minY);
/* 1683 */     int l = MathHelper.floor_double(bb.maxY);
/* 1684 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1685 */     int j1 = MathHelper.floor_double(bb.maxZ);
/* 1686 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1688 */     for (int k1 = i; k1 <= j; k1++) {
/* 1689 */       for (int l1 = k; l1 <= l; l1++) {
/* 1690 */         for (int i2 = i1; i2 <= j1; i2++) {
/* 1691 */           Block block = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock();
/*      */           
/* 1693 */           if (block.getMaterial().isLiquid()) {
/* 1694 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1700 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isFlammableWithin(AxisAlignedBB bb) {
/* 1704 */     int i = MathHelper.floor_double(bb.minX);
/* 1705 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1706 */     int k = MathHelper.floor_double(bb.minY);
/* 1707 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1708 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1709 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/*      */     
/* 1711 */     if (isAreaLoaded(i, k, i1, j, l, j1, true)) {
/* 1712 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1714 */       for (int k1 = i; k1 < j; k1++) {
/* 1715 */         for (int l1 = k; l1 < l; l1++) {
/* 1716 */           for (int i2 = i1; i2 < j1; i2++) {
/* 1717 */             Block block = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock();
/*      */             
/* 1719 */             if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava) {
/* 1720 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1727 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleMaterialAcceleration(AxisAlignedBB bb, Material materialIn, Entity entityIn) {
/* 1735 */     int i = MathHelper.floor_double(bb.minX);
/* 1736 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1737 */     int k = MathHelper.floor_double(bb.minY);
/* 1738 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1739 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1740 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/*      */     
/* 1742 */     if (!isAreaLoaded(i, k, i1, j, l, j1, true)) {
/* 1743 */       return false;
/*      */     }
/* 1745 */     boolean flag = false;
/* 1746 */     Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);
/* 1747 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1749 */     for (int k1 = i; k1 < j; k1++) {
/* 1750 */       for (int l1 = k; l1 < l; l1++) {
/* 1751 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1752 */           blockpos$mutableblockpos.set(k1, l1, i2);
/* 1753 */           IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/* 1754 */           Block block = iblockstate.getBlock();
/*      */           
/* 1756 */           if (block.getMaterial() == materialIn) {
/* 1757 */             double d0 = ((l1 + 1) - BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate
/* 1758 */                 .getValue((IProperty)BlockLiquid.LEVEL)).intValue()));
/*      */             
/* 1760 */             if (l >= d0) {
/* 1761 */               flag = true;
/* 1762 */               vec3 = block.modifyAcceleration(this, (BlockPos)blockpos$mutableblockpos, entityIn, vec3);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1769 */     if (vec3.lengthVector() > 0.0D && entityIn.isPushedByWater()) {
/* 1770 */       vec3 = vec3.normalize();
/* 1771 */       double d1 = 0.014D;
/* 1772 */       entityIn.motionX += vec3.xCoord * d1;
/* 1773 */       entityIn.motionY += vec3.yCoord * d1;
/* 1774 */       entityIn.motionZ += vec3.zCoord * d1;
/*      */     } 
/*      */     
/* 1777 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMaterialInBB(AxisAlignedBB bb, Material materialIn) {
/* 1785 */     int i = MathHelper.floor_double(bb.minX);
/* 1786 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1787 */     int k = MathHelper.floor_double(bb.minY);
/* 1788 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1789 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1790 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1791 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1793 */     for (int k1 = i; k1 < j; k1++) {
/* 1794 */       for (int l1 = k; l1 < l; l1++) {
/* 1795 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1796 */           if (getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock()
/* 1797 */             .getMaterial() == materialIn) {
/* 1798 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1804 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAABBInMaterial(AxisAlignedBB bb, Material materialIn) {
/* 1811 */     int i = MathHelper.floor_double(bb.minX);
/* 1812 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1813 */     int k = MathHelper.floor_double(bb.minY);
/* 1814 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1815 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1816 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1817 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1819 */     for (int k1 = i; k1 < j; k1++) {
/* 1820 */       for (int l1 = k; l1 < l; l1++) {
/* 1821 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1822 */           IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2));
/* 1823 */           Block block = iblockstate.getBlock();
/*      */           
/* 1825 */           if (block.getMaterial() == materialIn) {
/* 1826 */             int j2 = ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue();
/* 1827 */             double d0 = (l1 + 1);
/*      */             
/* 1829 */             if (j2 < 8) {
/* 1830 */               d0 = (l1 + 1) - j2 / 8.0D;
/*      */             }
/*      */             
/* 1833 */             if (d0 >= bb.minY) {
/* 1834 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1841 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion createExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
/* 1848 */     return newExplosion(entityIn, x, y, z, strength, false, isSmoking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion newExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
/* 1857 */     Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
/* 1858 */     explosion.doExplosionA();
/* 1859 */     explosion.doExplosionB(true);
/* 1860 */     return explosion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBlockDensity(Vec3 vec, AxisAlignedBB bb) {
/* 1868 */     double d0 = 1.0D / ((bb.maxX - bb.minX) * 2.0D + 1.0D);
/* 1869 */     double d1 = 1.0D / ((bb.maxY - bb.minY) * 2.0D + 1.0D);
/* 1870 */     double d2 = 1.0D / ((bb.maxZ - bb.minZ) * 2.0D + 1.0D);
/* 1871 */     double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
/* 1872 */     double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
/*      */     
/* 1874 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/* 1875 */       int i = 0;
/* 1876 */       int j = 0;
/*      */       float f;
/* 1878 */       for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/* 1879 */         float f1; for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/* 1880 */           float f2; for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/* 1881 */             double d5 = bb.minX + (bb.maxX - bb.minX) * f;
/* 1882 */             double d6 = bb.minY + (bb.maxY - bb.minY) * f1;
/* 1883 */             double d7 = bb.minZ + (bb.maxZ - bb.minZ) * f2;
/*      */             
/* 1885 */             if (rayTraceBlocks(new Vec3(d5 + d3, d6, d7 + d4), vec) == null) {
/* 1886 */               i++;
/*      */             }
/*      */             
/* 1889 */             j++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1894 */       return i / j;
/*      */     } 
/* 1896 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side) {
/* 1904 */     pos = pos.offset(side);
/*      */     
/* 1906 */     if (getBlockState(pos).getBlock() == Blocks.fire) {
/* 1907 */       playAuxSFXAtEntity(player, 1004, pos, 0);
/* 1908 */       setBlockToAir(pos);
/* 1909 */       return true;
/*      */     } 
/* 1911 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugLoadedEntities() {
/* 1919 */     return "All: " + this.loadedEntityList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProviderName() {
/* 1927 */     return this.chunkProvider.makeString();
/*      */   }
/*      */   
/*      */   public TileEntity getTileEntity(BlockPos pos) {
/* 1931 */     if (!isValid(pos)) {
/* 1932 */       return null;
/*      */     }
/* 1934 */     TileEntity tileentity = null;
/*      */     
/* 1936 */     if (this.processingLoadedTiles) {
/* 1937 */       for (int i = 0; i < this.addedTileEntityList.size(); i++) {
/* 1938 */         TileEntity tileentity1 = this.addedTileEntityList.get(i);
/*      */         
/* 1940 */         if (!tileentity1.isInvalid() && tileentity1.getPos().equals(pos)) {
/* 1941 */           tileentity = tileentity1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1947 */     if (tileentity == null) {
/* 1948 */       tileentity = getChunkFromBlockCoords(pos).getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
/*      */     }
/*      */     
/* 1951 */     if (tileentity == null) {
/* 1952 */       for (int j = 0; j < this.addedTileEntityList.size(); j++) {
/* 1953 */         TileEntity tileentity2 = this.addedTileEntityList.get(j);
/*      */         
/* 1955 */         if (!tileentity2.isInvalid() && tileentity2.getPos().equals(pos)) {
/* 1956 */           tileentity = tileentity2;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1962 */     return tileentity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTileEntity(BlockPos pos, TileEntity tileEntityIn) {
/* 1967 */     if (tileEntityIn != null && !tileEntityIn.isInvalid()) {
/* 1968 */       if (this.processingLoadedTiles) {
/* 1969 */         tileEntityIn.setPos(pos);
/* 1970 */         Iterator<TileEntity> iterator = this.addedTileEntityList.iterator();
/*      */         
/* 1972 */         while (iterator.hasNext()) {
/* 1973 */           TileEntity tileentity = iterator.next();
/*      */           
/* 1975 */           if (tileentity.getPos().equals(pos)) {
/* 1976 */             tileentity.invalidate();
/* 1977 */             iterator.remove();
/*      */           } 
/*      */         } 
/*      */         
/* 1981 */         this.addedTileEntityList.add(tileEntityIn);
/*      */       } else {
/* 1983 */         addTileEntity(tileEntityIn);
/* 1984 */         getChunkFromBlockCoords(pos).addTileEntity(pos, tileEntityIn);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/* 1990 */     TileEntity tileentity = getTileEntity(pos);
/*      */     
/* 1992 */     if (tileentity != null && this.processingLoadedTiles) {
/* 1993 */       tileentity.invalidate();
/* 1994 */       this.addedTileEntityList.remove(tileentity);
/*      */     } else {
/* 1996 */       if (tileentity != null) {
/* 1997 */         this.addedTileEntityList.remove(tileentity);
/* 1998 */         this.loadedTileEntityList.remove(tileentity);
/* 1999 */         this.tickableTileEntities.remove(tileentity);
/*      */       } 
/*      */       
/* 2002 */       getChunkFromBlockCoords(pos).removeTileEntity(pos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markTileEntityForRemoval(TileEntity tileEntityIn) {
/* 2010 */     this.tileEntitiesToBeRemoved.add(tileEntityIn);
/*      */   }
/*      */   
/*      */   public boolean isBlockFullCube(BlockPos pos) {
/* 2014 */     IBlockState iblockstate = getBlockState(pos);
/* 2015 */     AxisAlignedBB axisalignedbb = iblockstate.getBlock().getCollisionBoundingBox(this, pos, iblockstate);
/* 2016 */     return (axisalignedbb != null && axisalignedbb.getAverageEdgeLength() >= 1.0D);
/*      */   }
/*      */   
/*      */   public static boolean doesBlockHaveSolidTopSurface(IBlockAccess blockAccess, BlockPos pos) {
/* 2020 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 2021 */     Block block = iblockstate.getBlock();
/* 2022 */     return (block.getMaterial().isOpaque() && block.isFullCube()) ? true : ((block instanceof BlockStairs) ? (
/* 2023 */       (iblockstate.getValue((IProperty)BlockStairs.HALF) == BlockStairs.EnumHalf.TOP)) : ((block instanceof BlockSlab) ? (
/*      */       
/* 2025 */       (iblockstate.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP)) : ((block instanceof net.minecraft.block.BlockHopper) ? true : ((block instanceof BlockSnow) ? (
/*      */ 
/*      */       
/* 2028 */       (((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue() == 7)) : false))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
/* 2036 */     if (!isValid(pos)) {
/* 2037 */       return _default;
/*      */     }
/* 2039 */     Chunk chunk = this.chunkProvider.provideChunk(pos);
/*      */     
/* 2041 */     if (chunk.isEmpty()) {
/* 2042 */       return _default;
/*      */     }
/* 2044 */     Block block = getBlockState(pos).getBlock();
/* 2045 */     return (block.getMaterial().isOpaque() && block.isFullCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void calculateInitialSkylight() {
/* 2055 */     int i = calculateSkylightSubtracted(1.0F);
/*      */     
/* 2057 */     if (i != this.skylightSubtracted) {
/* 2058 */       this.skylightSubtracted = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowedSpawnTypes(boolean hostile, boolean peaceful) {
/* 2066 */     this.spawnHostileMobs = hostile;
/* 2067 */     this.spawnPeacefulMobs = peaceful;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/* 2074 */     updateWeather();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculateInitialWeather() {
/* 2081 */     if (this.worldInfo.isRaining()) {
/* 2082 */       this.rainingStrength = 1.0F;
/*      */       
/* 2084 */       if (this.worldInfo.isThundering()) {
/* 2085 */         this.thunderingStrength = 1.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateWeather() {
/* 2094 */     if (!this.provider.getHasNoSky() && 
/* 2095 */       !this.isRemote) {
/* 2096 */       int i = this.worldInfo.getCleanWeatherTime();
/*      */       
/* 2098 */       if (i > 0) {
/* 2099 */         i--;
/* 2100 */         this.worldInfo.setCleanWeatherTime(i);
/* 2101 */         this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
/* 2102 */         this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
/*      */       } 
/*      */       
/* 2105 */       int j = this.worldInfo.getThunderTime();
/*      */       
/* 2107 */       if (j <= 0) {
/* 2108 */         if (this.worldInfo.isThundering()) {
/* 2109 */           this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
/*      */         } else {
/* 2111 */           this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 2114 */         j--;
/* 2115 */         this.worldInfo.setThunderTime(j);
/*      */         
/* 2117 */         if (j <= 0) {
/* 2118 */           this.worldInfo.setThundering(!this.worldInfo.isThundering());
/*      */         }
/*      */       } 
/*      */       
/* 2122 */       this.prevThunderingStrength = this.thunderingStrength;
/*      */       
/* 2124 */       if (this.worldInfo.isThundering()) {
/* 2125 */         this.thunderingStrength = (float)(this.thunderingStrength + 0.01D);
/*      */       } else {
/* 2127 */         this.thunderingStrength = (float)(this.thunderingStrength - 0.01D);
/*      */       } 
/*      */       
/* 2130 */       this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
/* 2131 */       int k = this.worldInfo.getRainTime();
/*      */       
/* 2133 */       if (k <= 0) {
/* 2134 */         if (this.worldInfo.isRaining()) {
/* 2135 */           this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
/*      */         } else {
/* 2137 */           this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 2140 */         k--;
/* 2141 */         this.worldInfo.setRainTime(k);
/*      */         
/* 2143 */         if (k <= 0) {
/* 2144 */           this.worldInfo.setRaining(!this.worldInfo.isRaining());
/*      */         }
/*      */       } 
/*      */       
/* 2148 */       this.prevRainingStrength = this.rainingStrength;
/*      */       
/* 2150 */       if (this.worldInfo.isRaining()) {
/* 2151 */         this.rainingStrength = (float)(this.rainingStrength + 0.01D);
/*      */       } else {
/* 2153 */         this.rainingStrength = (float)(this.rainingStrength - 0.01D);
/*      */       } 
/*      */       
/* 2156 */       this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setActivePlayerChunksAndCheckLight() {
/* 2162 */     this.activeChunkSet.clear();
/* 2163 */     this.theProfiler.startSection("buildList");
/*      */     
/* 2165 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2166 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/* 2167 */       int j = MathHelper.floor_double(entityplayer.posX / 16.0D);
/* 2168 */       int k = MathHelper.floor_double(entityplayer.posZ / 16.0D);
/* 2169 */       int l = getRenderDistanceChunks();
/*      */       
/* 2171 */       for (int i1 = -l; i1 <= l; i1++) {
/* 2172 */         for (int j1 = -l; j1 <= l; j1++) {
/* 2173 */           this.activeChunkSet.add(new ChunkCoordIntPair(i1 + j, j1 + k));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2178 */     this.theProfiler.endSection();
/*      */     
/* 2180 */     if (this.ambientTickCountdown > 0) {
/* 2181 */       this.ambientTickCountdown--;
/*      */     }
/*      */     
/* 2184 */     this.theProfiler.startSection("playerCheckLight");
/*      */     
/* 2186 */     if (!this.playerEntities.isEmpty()) {
/* 2187 */       int k1 = this.rand.nextInt(this.playerEntities.size());
/* 2188 */       EntityPlayer entityplayer1 = this.playerEntities.get(k1);
/* 2189 */       int l1 = MathHelper.floor_double(entityplayer1.posX) + this.rand.nextInt(11) - 5;
/* 2190 */       int i2 = MathHelper.floor_double(entityplayer1.posY) + this.rand.nextInt(11) - 5;
/* 2191 */       int j2 = MathHelper.floor_double(entityplayer1.posZ) + this.rand.nextInt(11) - 5;
/* 2192 */       checkLight(new BlockPos(l1, i2, j2));
/*      */     } 
/*      */     
/* 2195 */     this.theProfiler.endSection();
/*      */   }
/*      */   
/*      */   protected abstract int getRenderDistanceChunks();
/*      */   
/*      */   protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk chunkIn) {
/* 2201 */     this.theProfiler.endStartSection("moodSound");
/*      */     
/* 2203 */     if (this.ambientTickCountdown == 0 && !this.isRemote) {
/* 2204 */       this.updateLCG = this.updateLCG * 3 + 1013904223;
/* 2205 */       int i = this.updateLCG >> 2;
/* 2206 */       int j = i & 0xF;
/* 2207 */       int k = i >> 8 & 0xF;
/* 2208 */       int l = i >> 16 & 0xFF;
/* 2209 */       BlockPos blockpos = new BlockPos(j, l, k);
/* 2210 */       Block block = chunkIn.getBlock(blockpos);
/* 2211 */       j += p_147467_1_;
/* 2212 */       k += p_147467_2_;
/*      */       
/* 2214 */       if (block.getMaterial() == Material.air && getLight(blockpos) <= this.rand.nextInt(8) && 
/* 2215 */         getLightFor(EnumSkyBlock.SKY, blockpos) <= 0) {
/* 2216 */         EntityPlayer entityplayer = getClosestPlayer(j + 0.5D, l + 0.5D, k + 0.5D, 8.0D);
/*      */ 
/*      */         
/* 2219 */         if (entityplayer != null && entityplayer
/* 2220 */           .getDistanceSq(j + 0.5D, l + 0.5D, k + 0.5D) > 4.0D) {
/* 2221 */           playSoundEffect(j + 0.5D, l + 0.5D, k + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand
/* 2222 */               .nextFloat() * 0.2F);
/* 2223 */           this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2228 */     this.theProfiler.endStartSection("checkLight");
/* 2229 */     chunkIn.enqueueRelightChecks();
/*      */   }
/*      */   
/*      */   protected void updateBlocks() {
/* 2233 */     setActivePlayerChunksAndCheckLight();
/*      */   }
/*      */   
/*      */   public void forceBlockUpdateTick(Block blockType, BlockPos pos, Random random) {
/* 2237 */     this.scheduledUpdatesAreImmediate = true;
/* 2238 */     blockType.updateTick(this, pos, getBlockState(pos), random);
/* 2239 */     this.scheduledUpdatesAreImmediate = false;
/*      */   }
/*      */   
/*      */   public boolean canBlockFreezeWater(BlockPos pos) {
/* 2243 */     return canBlockFreeze(pos, false);
/*      */   }
/*      */   
/*      */   public boolean canBlockFreezeNoWater(BlockPos pos) {
/* 2247 */     return canBlockFreeze(pos, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj) {
/* 2254 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 2255 */     float f = biomegenbase.getFloatTemperature(pos);
/*      */     
/* 2257 */     if (f > 0.15F) {
/* 2258 */       return false;
/*      */     }
/* 2260 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/* 2261 */       IBlockState iblockstate = getBlockState(pos);
/* 2262 */       Block block = iblockstate.getBlock();
/*      */       
/* 2264 */       if ((block == Blocks.water || block == Blocks.flowing_water) && ((Integer)iblockstate
/* 2265 */         .getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/* 2266 */         if (!noWaterAdj) {
/* 2267 */           return true;
/*      */         }
/*      */ 
/*      */         
/* 2271 */         boolean flag = (isWater(pos.west()) && isWater(pos.east()) && isWater(pos.north()) && isWater(pos.south()));
/*      */         
/* 2273 */         if (!flag) {
/* 2274 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2279 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isWater(BlockPos pos) {
/* 2284 */     return (getBlockState(pos).getBlock().getMaterial() == Material.water);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSnowAt(BlockPos pos, boolean checkLight) {
/* 2291 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 2292 */     float f = biomegenbase.getFloatTemperature(pos);
/*      */     
/* 2294 */     if (f > 0.15F)
/* 2295 */       return false; 
/* 2296 */     if (!checkLight) {
/* 2297 */       return true;
/*      */     }
/* 2299 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/* 2300 */       Block block = getBlockState(pos).getBlock();
/*      */       
/* 2302 */       if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, pos)) {
/* 2303 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2307 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkLight(BlockPos pos) {
/* 2312 */     boolean flag = false;
/*      */     
/* 2314 */     if (!this.provider.getHasNoSky()) {
/* 2315 */       flag |= checkLightFor(EnumSkyBlock.SKY, pos);
/*      */     }
/*      */     
/* 2318 */     flag |= checkLightFor(EnumSkyBlock.BLOCK, pos);
/* 2319 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRawLight(BlockPos pos, EnumSkyBlock lightType) {
/* 2326 */     if (lightType == EnumSkyBlock.SKY && canSeeSky(pos)) {
/* 2327 */       return 15;
/*      */     }
/* 2329 */     Block block = getBlockState(pos).getBlock();
/* 2330 */     int i = (lightType == EnumSkyBlock.SKY) ? 0 : block.getLightValue();
/* 2331 */     int j = block.getLightOpacity();
/*      */     
/* 2333 */     if (j >= 15 && block.getLightValue() > 0) {
/* 2334 */       j = 1;
/*      */     }
/*      */     
/* 2337 */     if (j < 1) {
/* 2338 */       j = 1;
/*      */     }
/*      */     
/* 2341 */     if (j >= 15)
/* 2342 */       return 0; 
/* 2343 */     if (i >= 14) {
/* 2344 */       return i;
/*      */     }
/* 2346 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/* 2347 */       BlockPos blockpos = pos.offset(enumfacing);
/* 2348 */       int k = getLightFor(lightType, blockpos) - j;
/*      */       
/* 2350 */       if (k > i) {
/* 2351 */         i = k;
/*      */       }
/*      */       
/* 2354 */       if (i >= 14) {
/* 2355 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 2359 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos) {
/* 2365 */     if (!isAreaLoaded(pos, 17, false)) {
/* 2366 */       return false;
/*      */     }
/* 2368 */     int i = 0;
/* 2369 */     int j = 0;
/* 2370 */     this.theProfiler.startSection("getBrightness");
/* 2371 */     int k = getLightFor(lightType, pos);
/* 2372 */     int l = getRawLight(pos, lightType);
/* 2373 */     int i1 = pos.getX();
/* 2374 */     int j1 = pos.getY();
/* 2375 */     int k1 = pos.getZ();
/*      */     
/* 2377 */     if (l > k) {
/* 2378 */       this.lightUpdateBlockList[j++] = 133152;
/* 2379 */     } else if (l < k) {
/* 2380 */       this.lightUpdateBlockList[j++] = 0x20820 | k << 18;
/*      */       
/* 2382 */       while (i < j) {
/* 2383 */         int l1 = this.lightUpdateBlockList[i++];
/* 2384 */         int i2 = (l1 & 0x3F) - 32 + i1;
/* 2385 */         int j2 = (l1 >> 6 & 0x3F) - 32 + j1;
/* 2386 */         int k2 = (l1 >> 12 & 0x3F) - 32 + k1;
/* 2387 */         int l2 = l1 >> 18 & 0xF;
/* 2388 */         BlockPos blockpos = new BlockPos(i2, j2, k2);
/* 2389 */         int i3 = getLightFor(lightType, blockpos);
/*      */         
/* 2391 */         if (i3 == l2) {
/* 2392 */           setLightFor(lightType, blockpos, 0);
/*      */           
/* 2394 */           if (l2 > 0) {
/* 2395 */             int j3 = MathHelper.abs_int(i2 - i1);
/* 2396 */             int k3 = MathHelper.abs_int(j2 - j1);
/* 2397 */             int l3 = MathHelper.abs_int(k2 - k1);
/*      */             
/* 2399 */             if (j3 + k3 + l3 < 17) {
/* 2400 */               BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */               
/* 2402 */               for (EnumFacing enumfacing : EnumFacing.values()) {
/* 2403 */                 int i4 = i2 + enumfacing.getFrontOffsetX();
/* 2404 */                 int j4 = j2 + enumfacing.getFrontOffsetY();
/* 2405 */                 int k4 = k2 + enumfacing.getFrontOffsetZ();
/* 2406 */                 blockpos$mutableblockpos.set(i4, j4, k4);
/* 2407 */                 int l4 = Math.max(1, 
/* 2408 */                     getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getLightOpacity());
/* 2409 */                 i3 = getLightFor(lightType, (BlockPos)blockpos$mutableblockpos);
/*      */                 
/* 2411 */                 if (i3 == l2 - l4 && j < this.lightUpdateBlockList.length) {
/* 2412 */                   this.lightUpdateBlockList[j++] = i4 - i1 + 32 | j4 - j1 + 32 << 6 | k4 - k1 + 32 << 12 | l2 - l4 << 18;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2421 */       i = 0;
/*      */     } 
/*      */     
/* 2424 */     this.theProfiler.endSection();
/* 2425 */     this.theProfiler.startSection("checkedPosition < toCheckCount");
/*      */     
/* 2427 */     while (i < j) {
/* 2428 */       int i5 = this.lightUpdateBlockList[i++];
/* 2429 */       int j5 = (i5 & 0x3F) - 32 + i1;
/* 2430 */       int k5 = (i5 >> 6 & 0x3F) - 32 + j1;
/* 2431 */       int l5 = (i5 >> 12 & 0x3F) - 32 + k1;
/* 2432 */       BlockPos blockpos1 = new BlockPos(j5, k5, l5);
/* 2433 */       int i6 = getLightFor(lightType, blockpos1);
/* 2434 */       int j6 = getRawLight(blockpos1, lightType);
/*      */       
/* 2436 */       if (j6 != i6) {
/* 2437 */         setLightFor(lightType, blockpos1, j6);
/*      */         
/* 2439 */         if (j6 > i6) {
/* 2440 */           int k6 = Math.abs(j5 - i1);
/* 2441 */           int l6 = Math.abs(k5 - j1);
/* 2442 */           int i7 = Math.abs(l5 - k1);
/* 2443 */           boolean flag = (j < this.lightUpdateBlockList.length - 6);
/*      */           
/* 2445 */           if (k6 + l6 + i7 < 17 && flag) {
/* 2446 */             if (getLightFor(lightType, blockpos1.west()) < j6) {
/* 2447 */               this.lightUpdateBlockList[j++] = j5 - 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */ 
/*      */             
/* 2451 */             if (getLightFor(lightType, blockpos1.east()) < j6) {
/* 2452 */               this.lightUpdateBlockList[j++] = j5 + 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */ 
/*      */             
/* 2456 */             if (getLightFor(lightType, blockpos1.down()) < j6) {
/* 2457 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */ 
/*      */             
/* 2461 */             if (getLightFor(lightType, blockpos1.up()) < j6) {
/* 2462 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 + 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */ 
/*      */             
/* 2466 */             if (getLightFor(lightType, blockpos1.north()) < j6) {
/* 2467 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - 1 - k1 + 32 << 12);
/*      */             }
/*      */ 
/*      */             
/* 2471 */             if (getLightFor(lightType, blockpos1.south()) < j6) {
/* 2472 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 + 1 - k1 + 32 << 12);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2480 */     this.theProfiler.endSection();
/* 2481 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tickUpdates(boolean p_72955_1_) {
/* 2489 */     return false;
/*      */   }
/*      */   
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
/* 2493 */     return null;
/*      */   }
/*      */   
/*      */   public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBB, boolean p_175712_2_) {
/* 2497 */     return null;
/*      */   }
/*      */   
/*      */   public List<Entity> getEntitiesWithinAABBExcludingEntity(Entity entityIn, AxisAlignedBB bb) {
/* 2501 */     return getEntitiesInAABBexcluding(entityIn, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getEntitiesInAABBexcluding(Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate) {
/* 2506 */     List<Entity> list = Lists.newArrayList();
/* 2507 */     int i = MathHelper.floor_double((boundingBox.minX - 2.0D) / 16.0D);
/* 2508 */     int j = MathHelper.floor_double((boundingBox.maxX + 2.0D) / 16.0D);
/* 2509 */     int k = MathHelper.floor_double((boundingBox.minZ - 2.0D) / 16.0D);
/* 2510 */     int l = MathHelper.floor_double((boundingBox.maxZ + 2.0D) / 16.0D);
/*      */     
/* 2512 */     for (int i1 = i; i1 <= j; i1++) {
/* 2513 */       for (int j1 = k; j1 <= l; j1++) {
/* 2514 */         if (isChunkLoaded(i1, j1, true)) {
/* 2515 */           getChunkFromChunkCoords(i1, j1).getEntitiesWithinAABBForEntity(entityIn, boundingBox, list, predicate);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2521 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> getEntities(Class<? extends T> entityType, Predicate<? super T> filter) {
/* 2525 */     List<T> list = Lists.newArrayList();
/*      */     
/* 2527 */     for (Entity entity : this.loadedEntityList) {
/* 2528 */       if (entityType.isAssignableFrom(entity.getClass()) && filter.apply(entity)) {
/* 2529 */         list.add((T)entity);
/*      */       }
/*      */     } 
/*      */     
/* 2533 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> getPlayers(Class<? extends T> playerType, Predicate<? super T> filter) {
/* 2537 */     List<T> list = Lists.newArrayList();
/*      */     
/* 2539 */     for (Entity entity : this.playerEntities) {
/* 2540 */       if (playerType.isAssignableFrom(entity.getClass()) && filter.apply(entity)) {
/* 2541 */         list.add((T)entity);
/*      */       }
/*      */     } 
/*      */     
/* 2545 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> classEntity, AxisAlignedBB bb) {
/* 2549 */     return getEntitiesWithinAABB(classEntity, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, Predicate<? super T> filter) {
/* 2554 */     int i = MathHelper.floor_double((aabb.minX - 2.0D) / 16.0D);
/* 2555 */     int j = MathHelper.floor_double((aabb.maxX + 2.0D) / 16.0D);
/* 2556 */     int k = MathHelper.floor_double((aabb.minZ - 2.0D) / 16.0D);
/* 2557 */     int l = MathHelper.floor_double((aabb.maxZ + 2.0D) / 16.0D);
/* 2558 */     List<T> list = Lists.newArrayList();
/*      */     
/* 2560 */     for (int i1 = i; i1 <= j; i1++) {
/* 2561 */       for (int j1 = k; j1 <= l; j1++) {
/* 2562 */         if (isChunkLoaded(i1, j1, true)) {
/* 2563 */           getChunkFromChunkCoords(i1, j1).getEntitiesOfTypeWithinAAAB(clazz, aabb, list, filter);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2568 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> T findNearestEntityWithinAABB(Class<? extends T> entityType, AxisAlignedBB aabb, T closestTo) {
/*      */     Entity entity;
/* 2573 */     List<T> list = getEntitiesWithinAABB(entityType, aabb);
/* 2574 */     T t = null;
/* 2575 */     double d0 = Double.MAX_VALUE;
/*      */     
/* 2577 */     for (int i = 0; i < list.size(); i++) {
/* 2578 */       Entity entity1 = (Entity)list.get(i);
/*      */       
/* 2580 */       if (entity1 != closestTo && EntitySelectors.NOT_SPECTATING.apply(entity1)) {
/* 2581 */         double d1 = closestTo.getDistanceSqToEntity(entity1);
/*      */         
/* 2583 */         if (d1 <= d0) {
/* 2584 */           entity = entity1;
/* 2585 */           d0 = d1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2590 */     return (T)entity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getEntityByID(int id) {
/* 2598 */     return (Entity)this.entitiesById.lookup(id);
/*      */   }
/*      */   
/*      */   public List<Entity> getLoadedEntityList() {
/* 2602 */     return this.loadedEntityList;
/*      */   }
/*      */   
/*      */   public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {
/* 2606 */     if (isBlockLoaded(pos)) {
/* 2607 */       getChunkFromBlockCoords(pos).setChunkModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countEntities(Class<?> entityType) {
/* 2616 */     int i = 0;
/*      */     
/* 2618 */     for (Entity entity : this.loadedEntityList) {
/* 2619 */       if ((!(entity instanceof EntityLiving) || !((EntityLiving)entity).isNoDespawnRequired()) && entityType
/* 2620 */         .isAssignableFrom(entity.getClass())) {
/* 2621 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 2625 */     return i;
/*      */   }
/*      */   
/*      */   public void loadEntities(Collection<Entity> entityCollection) {
/* 2629 */     this.loadedEntityList.addAll(entityCollection);
/*      */     
/* 2631 */     for (Entity entity : entityCollection) {
/* 2632 */       onEntityAdded(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void unloadEntities(Collection<Entity> entityCollection) {
/* 2637 */     this.unloadedEntityList.addAll(entityCollection);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockBePlaced(Block blockIn, BlockPos pos, boolean p_175716_3_, EnumFacing side, Entity entityIn, ItemStack itemStackIn) {
/* 2642 */     Block block = getBlockState(pos).getBlock();
/*      */     
/* 2644 */     AxisAlignedBB axisalignedbb = p_175716_3_ ? null : blockIn.getCollisionBoundingBox(this, pos, blockIn.getDefaultState());
/* 2645 */     return (axisalignedbb != null && !checkNoEntityCollision(axisalignedbb, entityIn)) ? false : ((block
/* 2646 */       .getMaterial() == Material.circuits && blockIn == Blocks.anvil) ? true : ((block
/* 2647 */       .getMaterial().isReplaceable() && blockIn.canReplace(this, pos, side, itemStackIn))));
/*      */   }
/*      */   
/*      */   public int getSeaLevel() {
/* 2651 */     return this.seaLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeaLevel(int p_181544_1_) {
/* 2659 */     this.seaLevel = p_181544_1_;
/*      */   }
/*      */   
/*      */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 2663 */     IBlockState iblockstate = getBlockState(pos);
/* 2664 */     return iblockstate.getBlock().getStrongPower(this, pos, iblockstate, direction);
/*      */   }
/*      */   
/*      */   public WorldType getWorldType() {
/* 2668 */     return this.worldInfo.getTerrainType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStrongPower(BlockPos pos) {
/* 2676 */     int i = 0;
/* 2677 */     i = Math.max(i, getStrongPower(pos.down(), EnumFacing.DOWN));
/*      */     
/* 2679 */     if (i >= 15) {
/* 2680 */       return i;
/*      */     }
/* 2682 */     i = Math.max(i, getStrongPower(pos.up(), EnumFacing.UP));
/*      */     
/* 2684 */     if (i >= 15) {
/* 2685 */       return i;
/*      */     }
/* 2687 */     i = Math.max(i, getStrongPower(pos.north(), EnumFacing.NORTH));
/*      */     
/* 2689 */     if (i >= 15) {
/* 2690 */       return i;
/*      */     }
/* 2692 */     i = Math.max(i, getStrongPower(pos.south(), EnumFacing.SOUTH));
/*      */     
/* 2694 */     if (i >= 15) {
/* 2695 */       return i;
/*      */     }
/* 2697 */     i = Math.max(i, getStrongPower(pos.west(), EnumFacing.WEST));
/*      */     
/* 2699 */     if (i >= 15) {
/* 2700 */       return i;
/*      */     }
/* 2702 */     i = Math.max(i, getStrongPower(pos.east(), EnumFacing.EAST));
/* 2703 */     return (i >= 15) ? i : i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSidePowered(BlockPos pos, EnumFacing side) {
/* 2712 */     return (getRedstonePower(pos, side) > 0);
/*      */   }
/*      */   
/*      */   public int getRedstonePower(BlockPos pos, EnumFacing facing) {
/* 2716 */     IBlockState iblockstate = getBlockState(pos);
/* 2717 */     Block block = iblockstate.getBlock();
/* 2718 */     return block.isNormalCube() ? getStrongPower(pos) : block.getWeakPower(this, pos, iblockstate, facing);
/*      */   }
/*      */   
/*      */   public boolean isBlockPowered(BlockPos pos) {
/* 2722 */     return (getRedstonePower(pos.down(), EnumFacing.DOWN) > 0) ? true : (
/* 2723 */       (getRedstonePower(pos.up(), EnumFacing.UP) > 0) ? true : (
/* 2724 */       (getRedstonePower(pos.north(), EnumFacing.NORTH) > 0) ? true : (
/* 2725 */       (getRedstonePower(pos.south(), EnumFacing.SOUTH) > 0) ? true : (
/* 2726 */       (getRedstonePower(pos.west(), EnumFacing.WEST) > 0) ? true : (
/* 2727 */       (getRedstonePower(pos.east(), EnumFacing.EAST) > 0))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int isBlockIndirectlyGettingPowered(BlockPos pos) {
/* 2735 */     int i = 0;
/*      */     
/* 2737 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/* 2738 */       int j = getRedstonePower(pos.offset(enumfacing), enumfacing);
/*      */       
/* 2740 */       if (j >= 15) {
/* 2741 */         return 15;
/*      */       }
/*      */       
/* 2744 */       if (j > i) {
/* 2745 */         i = j;
/*      */       }
/*      */     } 
/*      */     
/* 2749 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance) {
/* 2757 */     return getClosestPlayer(entityIn.posX, entityIn.posY, entityIn.posZ, distance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getClosestPlayer(double x, double y, double z, double distance) {
/* 2765 */     double d0 = -1.0D;
/* 2766 */     EntityPlayer entityplayer = null;
/*      */     
/* 2768 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2769 */       EntityPlayer entityplayer1 = this.playerEntities.get(i);
/*      */       
/* 2771 */       if (EntitySelectors.NOT_SPECTATING.apply(entityplayer1)) {
/* 2772 */         double d1 = entityplayer1.getDistanceSq(x, y, z);
/*      */         
/* 2774 */         if ((distance < 0.0D || d1 < distance * distance) && (d0 == -1.0D || d1 < d0)) {
/* 2775 */           d0 = d1;
/* 2776 */           entityplayer = entityplayer1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2781 */     return entityplayer;
/*      */   }
/*      */   
/*      */   public boolean isAnyPlayerWithinRangeAt(double x, double y, double z, double range) {
/* 2785 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2786 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 2788 */       if (EntitySelectors.NOT_SPECTATING.apply(entityplayer)) {
/* 2789 */         double d0 = entityplayer.getDistanceSq(x, y, z);
/*      */         
/* 2791 */         if (range < 0.0D || d0 < range * range) {
/* 2792 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2797 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getPlayerEntityByName(String name) {
/* 2804 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2805 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 2807 */       if (name.equals(entityplayer.getName())) {
/* 2808 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 2812 */     return null;
/*      */   }
/*      */   
/*      */   public EntityPlayer getPlayerEntityByUUID(UUID uuid) {
/* 2816 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2817 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 2819 */       if (uuid.equals(entityplayer.getUniqueID())) {
/* 2820 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 2824 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendQuittingDisconnectingPacket() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSessionLock() throws MinecraftException {
/* 2837 */     this.saveHandler.checkSessionLock();
/*      */   }
/*      */   
/*      */   public void setTotalWorldTime(long worldTime) {
/* 2841 */     this.worldInfo.setWorldTotalTime(worldTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSeed() {
/* 2848 */     return this.worldInfo.getSeed();
/*      */   }
/*      */   
/*      */   public long getTotalWorldTime() {
/* 2852 */     return this.worldInfo.getWorldTotalTime();
/*      */   }
/*      */   
/*      */   public long getWorldTime() {
/* 2856 */     return this.worldInfo.getWorldTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldTime(long time) {
/* 2863 */     this.worldInfo.setWorldTime(time);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getSpawnPoint() {
/* 2871 */     BlockPos blockpos = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
/*      */     
/* 2873 */     if (!getWorldBorder().contains(blockpos)) {
/* 2874 */       blockpos = getHeight(new BlockPos(
/* 2875 */             getWorldBorder().getCenterX(), 0.0D, getWorldBorder().getCenterZ()));
/*      */     }
/*      */     
/* 2878 */     return blockpos;
/*      */   }
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos) {
/* 2882 */     this.worldInfo.setSpawn(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void joinEntityInSurroundings(Entity entityIn) {
/* 2889 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/* 2890 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/* 2891 */     int k = 2;
/*      */     
/* 2893 */     for (int l = i - k; l <= i + k; l++) {
/* 2894 */       for (int i1 = j - k; i1 <= j + k; i1++) {
/* 2895 */         getChunkFromChunkCoords(l, i1);
/*      */       }
/*      */     } 
/*      */     
/* 2899 */     if (!this.loadedEntityList.contains(entityIn)) {
/* 2900 */       this.loadedEntityList.add(entityIn);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
/* 2905 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityState(Entity entityIn, byte state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChunkProvider getChunkProvider() {
/* 2918 */     return this.chunkProvider;
/*      */   }
/*      */   
/*      */   public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
/* 2922 */     blockIn.onBlockEventReceived(this, pos, getBlockState(pos), eventID, eventParam);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISaveHandler getSaveHandler() {
/* 2929 */     return this.saveHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldInfo getWorldInfo() {
/* 2936 */     return this.worldInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameRules getGameRules() {
/* 2943 */     return this.worldInfo.getGameRulesInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAllPlayersSleepingFlag() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public float getThunderStrength(float delta) {
/* 2954 */     return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * delta) * 
/* 2955 */       getRainStrength(delta);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThunderStrength(float strength) {
/* 2962 */     this.prevThunderingStrength = strength;
/* 2963 */     this.thunderingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRainStrength(float delta) {
/* 2970 */     return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * delta;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRainStrength(float strength) {
/* 2977 */     this.prevRainingStrength = strength;
/* 2978 */     this.rainingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isThundering() {
/* 2986 */     return (getThunderStrength(1.0F) > 0.9D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRaining() {
/* 2993 */     return (getRainStrength(1.0F) > 0.2D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRainingAt(BlockPos strikePosition) {
/* 3000 */     if (!isRaining())
/* 3001 */       return false; 
/* 3002 */     if (!canSeeSky(strikePosition))
/* 3003 */       return false; 
/* 3004 */     if (getPrecipitationHeight(strikePosition).getY() > strikePosition.getY()) {
/* 3005 */       return false;
/*      */     }
/* 3007 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(strikePosition);
/* 3008 */     return biomegenbase.getEnableSnow() ? false : (
/* 3009 */       canSnowAt(strikePosition, false) ? false : biomegenbase.canRain());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockinHighHumidity(BlockPos pos) {
/* 3014 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 3015 */     return biomegenbase.isHighHumidity();
/*      */   }
/*      */   
/*      */   public MapStorage getMapStorage() {
/* 3019 */     return this.mapStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemData(String dataID, WorldSavedData worldSavedDataIn) {
/* 3027 */     this.mapStorage.setData(dataID, worldSavedDataIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldSavedData loadItemData(Class<? extends WorldSavedData> clazz, String dataID) {
/* 3036 */     return this.mapStorage.loadData(clazz, dataID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUniqueDataId(String key) {
/* 3044 */     return this.mapStorage.getUniqueDataId(key);
/*      */   }
/*      */   
/*      */   public void playBroadcastSound(int p_175669_1_, BlockPos pos, int p_175669_3_) {
/* 3048 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/* 3049 */       ((IWorldAccess)this.worldAccesses.get(i)).broadcastSound(p_175669_1_, pos, p_175669_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   public void playAuxSFX(int p_175718_1_, BlockPos pos, int p_175718_3_) {
/* 3054 */     playAuxSFXAtEntity((EntityPlayer)null, p_175718_1_, pos, p_175718_3_);
/*      */   }
/*      */   
/*      */   public void playAuxSFXAtEntity(EntityPlayer player, int sfxType, BlockPos pos, int p_180498_4_) {
/*      */     try {
/* 3059 */       for (int i = 0; i < this.worldAccesses.size(); i++) {
/* 3060 */         ((IWorldAccess)this.worldAccesses.get(i)).playAuxSFX(player, sfxType, pos, p_180498_4_);
/*      */       }
/* 3062 */     } catch (Throwable throwable) {
/* 3063 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Playing level event");
/* 3064 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Level event being played");
/* 3065 */       crashreportcategory.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(pos));
/* 3066 */       crashreportcategory.addCrashSection("Event source", player);
/* 3067 */       crashreportcategory.addCrashSection("Event type", Integer.valueOf(sfxType));
/* 3068 */       crashreportcategory.addCrashSection("Event data", Integer.valueOf(p_180498_4_));
/* 3069 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/* 3077 */     return 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActualHeight() {
/* 3084 */     return this.provider.getHasNoSky() ? 128 : 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_) {
/* 3091 */     long i = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + getWorldInfo().getSeed() + p_72843_3_;
/*      */     
/* 3093 */     this.rand.setSeed(i);
/* 3094 */     return this.rand;
/*      */   }
/*      */   
/*      */   public BlockPos getStrongholdPos(String name, BlockPos pos) {
/* 3098 */     return getChunkProvider().getStrongholdGen(this, name, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extendedLevelsInChunkCache() {
/* 3105 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getHorizon() {
/* 3112 */     if (VoidFlickFix.getInstance.isEnabled()) {
/* 3113 */       return 0.0D;
/*      */     }
/* 3115 */     return (this.worldInfo.getTerrainType() == WorldType.FLAT) ? 0.0D : 63.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 3123 */     CrashReportCategory crashreportcategory = report.makeCategoryDepth("Affected level", 1);
/* 3124 */     crashreportcategory.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo
/* 3125 */         .getWorldName());
/* 3126 */     crashreportcategory.addCrashSectionCallable("All players", new Callable<String>() {
/*      */           public String call() {
/* 3128 */             return World.this.playerEntities.size() + " total; " + World.this.playerEntities.toString();
/*      */           }
/*      */         });
/* 3131 */     crashreportcategory.addCrashSectionCallable("Chunk stats", new Callable<String>() {
/*      */           public String call() {
/* 3133 */             return World.this.chunkProvider.makeString();
/*      */           }
/*      */         });
/*      */     
/*      */     try {
/* 3138 */       this.worldInfo.addToCrashReport(crashreportcategory);
/* 3139 */     } catch (Throwable throwable) {
/* 3140 */       crashreportcategory.addCrashSectionThrowable("Level Data Unobtainable", throwable);
/*      */     } 
/*      */     
/* 3143 */     return crashreportcategory;
/*      */   }
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 3147 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/* 3148 */       IWorldAccess iworldaccess = this.worldAccesses.get(i);
/* 3149 */       iworldaccess.sendBlockBreakProgress(breakerId, pos, progress);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getCurrentDate() {
/* 3157 */     if (getTotalWorldTime() % 600L == 0L) {
/* 3158 */       this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
/*      */     }
/*      */     
/* 3161 */     return this.theCalendar;
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {}
/*      */ 
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 3169 */     return this.worldScoreboard;
/*      */   }
/*      */   
/*      */   public void updateComparatorOutputLevel(BlockPos pos, Block blockIn) {
/* 3173 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 3174 */       BlockPos blockpos = pos.offset(enumfacing);
/*      */       
/* 3176 */       if (isBlockLoaded(blockpos)) {
/* 3177 */         IBlockState iblockstate = getBlockState(blockpos);
/*      */         
/* 3179 */         if (Blocks.unpowered_comparator.isAssociated(iblockstate.getBlock())) {
/* 3180 */           iblockstate.getBlock().onNeighborBlockChange(this, blockpos, iblockstate, blockIn); continue;
/* 3181 */         }  if (iblockstate.getBlock().isNormalCube()) {
/* 3182 */           blockpos = blockpos.offset(enumfacing);
/* 3183 */           iblockstate = getBlockState(blockpos);
/*      */           
/* 3185 */           if (Blocks.unpowered_comparator.isAssociated(iblockstate.getBlock())) {
/* 3186 */             iblockstate.getBlock().onNeighborBlockChange(this, blockpos, iblockstate, blockIn);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
/* 3194 */     long i = 0L;
/* 3195 */     float f = 0.0F;
/*      */     
/* 3197 */     if (isBlockLoaded(pos)) {
/* 3198 */       f = getCurrentMoonPhaseFactor();
/* 3199 */       i = getChunkFromBlockCoords(pos).getInhabitedTime();
/*      */     } 
/*      */     
/* 3202 */     return new DifficultyInstance(getDifficulty(), getWorldTime(), i, f);
/*      */   }
/*      */   
/*      */   public EnumDifficulty getDifficulty() {
/* 3206 */     return getWorldInfo().getDifficulty();
/*      */   }
/*      */   
/*      */   public int getSkylightSubtracted() {
/* 3210 */     return this.skylightSubtracted;
/*      */   }
/*      */   
/*      */   public void setSkylightSubtracted(int newSkylightSubtracted) {
/* 3214 */     this.skylightSubtracted = newSkylightSubtracted;
/*      */   }
/*      */   
/*      */   public int getLastLightningBolt() {
/* 3218 */     return this.lastLightningBolt;
/*      */   }
/*      */   
/*      */   public void setLastLightningBolt(int lastLightningBoltIn) {
/* 3222 */     this.lastLightningBolt = lastLightningBoltIn;
/*      */   }
/*      */   
/*      */   public boolean isFindingSpawnPoint() {
/* 3226 */     return this.findingSpawnPoint;
/*      */   }
/*      */   
/*      */   public VillageCollection getVillageCollection() {
/* 3230 */     return this.villageCollectionObj;
/*      */   }
/*      */   
/*      */   public WorldBorder getWorldBorder() {
/* 3234 */     return this.worldBorder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpawnChunk(int x, int z) {
/* 3241 */     BlockPos blockpos = getSpawnPoint();
/* 3242 */     int i = x * 16 + 8 - blockpos.getX();
/* 3243 */     int j = z * 16 + 8 - blockpos.getZ();
/* 3244 */     int k = 128;
/* 3245 */     return (i >= -k && i <= k && j >= -k && j <= k);
/*      */   }
/*      */   
/*      */   public List<TileEntity> getLoadedTileEntityList() {
/* 3249 */     return this.loadedTileEntityList;
/*      */   }
/*      */   
/*      */   public Block getBlock(BlockPos pos) {
/* 3253 */     return getBlockState(pos).getBlock();
/*      */   }
/*      */   
/*      */   public Block getBlock(double x, double y, double z) {
/* 3257 */     return getBlockState(new BlockPos((int)x, (int)y, (int)z)).getBlock();
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\World.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */