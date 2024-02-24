/*      */ package net.minecraft.world.chunk;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Queues;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.ITileEntityProvider;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ClassInheritanceMultiMap;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.EnumSkyBlock;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class Chunk {
/*   36 */   private static final Logger logger = LogManager.getLogger();
/*      */ 
/*      */   
/*      */   private final ExtendedBlockStorage[] storageArrays;
/*      */ 
/*      */   
/*      */   private final byte[] blockBiomeArray;
/*      */ 
/*      */   
/*      */   private final int[] precipitationHeightMap;
/*      */ 
/*      */   
/*      */   private final boolean[] updateSkylightColumns;
/*      */ 
/*      */   
/*      */   private boolean isChunkLoaded;
/*      */ 
/*      */   
/*      */   private final World worldObj;
/*      */ 
/*      */   
/*      */   private final int[] heightMap;
/*      */ 
/*      */   
/*      */   public final int xPosition;
/*      */ 
/*      */   
/*      */   public final int zPosition;
/*      */ 
/*      */   
/*      */   private boolean isGapLightingUpdated;
/*      */ 
/*      */   
/*      */   private final Map<BlockPos, TileEntity> chunkTileEntityMap;
/*      */ 
/*      */   
/*      */   private final ClassInheritanceMultiMap<Entity>[] entityLists;
/*      */ 
/*      */   
/*      */   private boolean isTerrainPopulated;
/*      */ 
/*      */   
/*      */   private boolean isLightPopulated;
/*      */ 
/*      */   
/*      */   private boolean field_150815_m;
/*      */ 
/*      */   
/*      */   private boolean isModified;
/*      */ 
/*      */   
/*      */   private boolean hasEntities;
/*      */ 
/*      */   
/*      */   private long lastSaveTime;
/*      */ 
/*      */   
/*      */   private int heightMapMinimum;
/*      */ 
/*      */   
/*      */   private long inhabitedTime;
/*      */ 
/*      */   
/*      */   private int queuedLightChecks;
/*      */   
/*      */   private final ConcurrentLinkedQueue<BlockPos> tileEntityPosQueue;
/*      */ 
/*      */   
/*      */   public Chunk(World worldIn, int x, int z) {
/*  105 */     this.storageArrays = new ExtendedBlockStorage[16];
/*  106 */     this.blockBiomeArray = new byte[256];
/*  107 */     this.precipitationHeightMap = new int[256];
/*  108 */     this.updateSkylightColumns = new boolean[256];
/*  109 */     this.chunkTileEntityMap = Maps.newHashMap();
/*  110 */     this.queuedLightChecks = 4096;
/*  111 */     this.tileEntityPosQueue = Queues.newConcurrentLinkedQueue();
/*  112 */     this.entityLists = (ClassInheritanceMultiMap<Entity>[])new ClassInheritanceMultiMap[16];
/*  113 */     this.worldObj = worldIn;
/*  114 */     this.xPosition = x;
/*  115 */     this.zPosition = z;
/*  116 */     this.heightMap = new int[256];
/*      */     
/*  118 */     for (int i = 0; i < this.entityLists.length; i++)
/*      */     {
/*  120 */       this.entityLists[i] = new ClassInheritanceMultiMap(Entity.class);
/*      */     }
/*      */     
/*  123 */     Arrays.fill(this.precipitationHeightMap, -999);
/*  124 */     Arrays.fill(this.blockBiomeArray, (byte)-1);
/*      */   }
/*      */ 
/*      */   
/*      */   public Chunk(World worldIn, ChunkPrimer primer, int x, int z) {
/*  129 */     this(worldIn, x, z);
/*  130 */     int i = 256;
/*  131 */     boolean flag = !worldIn.provider.getHasNoSky();
/*      */     
/*  133 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  135 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  137 */         for (int l = 0; l < i; l++) {
/*      */           
/*  139 */           int i1 = j * i * 16 | k * i | l;
/*  140 */           IBlockState iblockstate = primer.getBlockState(i1);
/*      */           
/*  142 */           if (iblockstate.getBlock().getMaterial() != Material.air) {
/*      */             
/*  144 */             int j1 = l >> 4;
/*      */             
/*  146 */             if (this.storageArrays[j1] == null)
/*      */             {
/*  148 */               this.storageArrays[j1] = new ExtendedBlockStorage(j1 << 4, flag);
/*      */             }
/*      */             
/*  151 */             this.storageArrays[j1].set(j, l & 0xF, k, iblockstate);
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
/*      */   public boolean isAtLocation(int x, int z) {
/*  163 */     return (x == this.xPosition && z == this.zPosition);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHeight(BlockPos pos) {
/*  168 */     return getHeightValue(pos.getX() & 0xF, pos.getZ() & 0xF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeightValue(int x, int z) {
/*  176 */     return this.heightMap[z << 4 | x];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTopFilledSegment() {
/*  184 */     for (int i = this.storageArrays.length - 1; i >= 0; i--) {
/*      */       
/*  186 */       if (this.storageArrays[i] != null)
/*      */       {
/*  188 */         return this.storageArrays[i].getYLocation();
/*      */       }
/*      */     } 
/*      */     
/*  192 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtendedBlockStorage[] getBlockStorageArray() {
/*  200 */     return this.storageArrays;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateHeightMap() {
/*  208 */     int i = getTopFilledSegment();
/*  209 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  211 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  213 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  215 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  217 */         for (int l = i + 16; l > 0; l--) {
/*      */           
/*  219 */           Block block = getBlock0(j, l - 1, k);
/*      */           
/*  221 */           if (block.getLightOpacity() != 0) {
/*      */             
/*  223 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  225 */             if (l < this.heightMapMinimum)
/*      */             {
/*  227 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  236 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void generateSkylightMap() {
/*  244 */     int i = getTopFilledSegment();
/*  245 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  247 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  249 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  251 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  253 */         for (int l = i + 16; l > 0; l--) {
/*      */           
/*  255 */           if (getBlockLightOpacity(j, l - 1, k) != 0) {
/*      */             
/*  257 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  259 */             if (l < this.heightMapMinimum)
/*      */             {
/*  261 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/*  268 */         if (!this.worldObj.provider.getHasNoSky()) {
/*      */           
/*  270 */           int k1 = 15;
/*  271 */           int i1 = i + 16 - 1;
/*      */ 
/*      */           
/*      */           do {
/*  275 */             int j1 = getBlockLightOpacity(j, i1, k);
/*      */             
/*  277 */             if (j1 == 0 && k1 != 15)
/*      */             {
/*  279 */               j1 = 1;
/*      */             }
/*      */             
/*  282 */             k1 -= j1;
/*      */             
/*  284 */             if (k1 <= 0)
/*      */               continue; 
/*  286 */             ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  288 */             if (extendedblockstorage == null)
/*      */               continue; 
/*  290 */             extendedblockstorage.setExtSkylightValue(j, i1 & 0xF, k, k1);
/*  291 */             this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + j, i1, (this.zPosition << 4) + k));
/*      */ 
/*      */ 
/*      */             
/*  295 */             --i1;
/*      */           }
/*  297 */           while (i1 > 0 && k1 > 0);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  306 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateSkylightOcclusion(int x, int z) {
/*  314 */     this.updateSkylightColumns[x + (z << 4)] = true;
/*  315 */     this.isGapLightingUpdated = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void recheckGaps(boolean p_150803_1_) {
/*  320 */     this.worldObj.theProfiler.startSection("recheckGaps");
/*      */     
/*  322 */     if (this.worldObj.isAreaLoaded(new BlockPos((this.xPosition << 4) + 8, 0, (this.zPosition << 4) + 8), 16)) {
/*      */       
/*  324 */       for (int i = 0; i < 16; i++) {
/*      */         
/*  326 */         for (int j = 0; j < 16; j++) {
/*      */           
/*  328 */           if (this.updateSkylightColumns[i + (j << 4)]) {
/*      */             
/*  330 */             this.updateSkylightColumns[i + (j << 4)] = false;
/*  331 */             int k = getHeightValue(i, j);
/*  332 */             int l = (this.xPosition << 4) + i;
/*  333 */             int i1 = (this.zPosition << 4) + j;
/*  334 */             int j1 = Integer.MAX_VALUE;
/*      */             
/*  336 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*      */             {
/*  338 */               j1 = Math.min(j1, this.worldObj.getChunksLowestHorizon(l + enumfacing.getFrontOffsetX(), i1 + enumfacing.getFrontOffsetZ()));
/*      */             }
/*      */             
/*  341 */             checkSkylightNeighborHeight(l, i1, j1);
/*      */             
/*  343 */             for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*      */             {
/*  345 */               checkSkylightNeighborHeight(l + enumfacing1.getFrontOffsetX(), i1 + enumfacing1.getFrontOffsetZ(), k);
/*      */             }
/*      */             
/*  348 */             if (p_150803_1_) {
/*      */               
/*  350 */               this.worldObj.theProfiler.endSection();
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  357 */       this.isGapLightingUpdated = false;
/*      */     } 
/*      */     
/*  360 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSkylightNeighborHeight(int x, int z, int maxValue) {
/*  368 */     int i = this.worldObj.getHeight(new BlockPos(x, 0, z)).getY();
/*      */     
/*  370 */     if (i > maxValue) {
/*      */       
/*  372 */       updateSkylightNeighborHeight(x, z, maxValue, i + 1);
/*      */     }
/*  374 */     else if (i < maxValue) {
/*      */       
/*  376 */       updateSkylightNeighborHeight(x, z, i, maxValue + 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateSkylightNeighborHeight(int x, int z, int startY, int endY) {
/*  382 */     if (endY > startY && this.worldObj.isAreaLoaded(new BlockPos(x, 0, z), 16)) {
/*      */       
/*  384 */       for (int i = startY; i < endY; i++)
/*      */       {
/*  386 */         this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(x, i, z));
/*      */       }
/*      */       
/*  389 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void relightBlock(int x, int y, int z) {
/*  398 */     int i = this.heightMap[z << 4 | x] & 0xFF;
/*  399 */     int j = i;
/*      */     
/*  401 */     if (y > i)
/*      */     {
/*  403 */       j = y;
/*      */     }
/*      */     
/*  406 */     while (j > 0 && getBlockLightOpacity(x, j - 1, z) == 0)
/*      */     {
/*  408 */       j--;
/*      */     }
/*      */     
/*  411 */     if (j != i) {
/*      */       
/*  413 */       this.worldObj.markBlocksDirtyVertical(x + (this.xPosition << 4), z + (this.zPosition << 4), j, i);
/*  414 */       this.heightMap[z << 4 | x] = j;
/*  415 */       int k = (this.xPosition << 4) + x;
/*  416 */       int l = (this.zPosition << 4) + z;
/*      */       
/*  418 */       if (!this.worldObj.provider.getHasNoSky()) {
/*      */         
/*  420 */         if (j < i) {
/*      */           
/*  422 */           for (int j1 = j; j1 < i; j1++) {
/*      */             
/*  424 */             ExtendedBlockStorage extendedblockstorage2 = this.storageArrays[j1 >> 4];
/*      */             
/*  426 */             if (extendedblockstorage2 != null)
/*      */             {
/*  428 */               extendedblockstorage2.setExtSkylightValue(x, j1 & 0xF, z, 15);
/*  429 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, j1, (this.zPosition << 4) + z));
/*      */             }
/*      */           
/*      */           } 
/*      */         } else {
/*      */           
/*  435 */           for (int i1 = i; i1 < j; i1++) {
/*      */             
/*  437 */             ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  439 */             if (extendedblockstorage != null) {
/*      */               
/*  441 */               extendedblockstorage.setExtSkylightValue(x, i1 & 0xF, z, 0);
/*  442 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, i1, (this.zPosition << 4) + z));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  447 */         int k1 = 15;
/*      */         
/*  449 */         while (j > 0 && k1 > 0) {
/*      */           
/*  451 */           j--;
/*  452 */           int i2 = getBlockLightOpacity(x, j, z);
/*      */           
/*  454 */           if (i2 == 0)
/*      */           {
/*  456 */             i2 = 1;
/*      */           }
/*      */           
/*  459 */           k1 -= i2;
/*      */           
/*  461 */           if (k1 < 0)
/*      */           {
/*  463 */             k1 = 0;
/*      */           }
/*      */           
/*  466 */           ExtendedBlockStorage extendedblockstorage1 = this.storageArrays[j >> 4];
/*      */           
/*  468 */           if (extendedblockstorage1 != null)
/*      */           {
/*  470 */             extendedblockstorage1.setExtSkylightValue(x, j & 0xF, z, k1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  475 */       int l1 = this.heightMap[z << 4 | x];
/*  476 */       int j2 = i;
/*  477 */       int k2 = l1;
/*      */       
/*  479 */       if (l1 < i) {
/*      */         
/*  481 */         j2 = l1;
/*  482 */         k2 = i;
/*      */       } 
/*      */       
/*  485 */       if (l1 < this.heightMapMinimum)
/*      */       {
/*  487 */         this.heightMapMinimum = l1;
/*      */       }
/*      */       
/*  490 */       if (!this.worldObj.provider.getHasNoSky()) {
/*      */         
/*  492 */         for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*      */         {
/*  494 */           updateSkylightNeighborHeight(k + enumfacing.getFrontOffsetX(), l + enumfacing.getFrontOffsetZ(), j2, k2);
/*      */         }
/*      */         
/*  497 */         updateSkylightNeighborHeight(k, l, j2, k2);
/*      */       } 
/*      */       
/*  500 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlockLightOpacity(BlockPos pos) {
/*  506 */     return getBlock(pos).getLightOpacity();
/*      */   }
/*      */ 
/*      */   
/*      */   private int getBlockLightOpacity(int x, int y, int z) {
/*  511 */     return getBlock0(x, y, z).getLightOpacity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Block getBlock0(int x, int y, int z) {
/*  519 */     Block block = Blocks.air;
/*      */     
/*  521 */     if (y >= 0 && y >> 4 < this.storageArrays.length) {
/*      */       
/*  523 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4];
/*      */       
/*  525 */       if (extendedblockstorage != null) {
/*      */         
/*      */         try {
/*      */           
/*  529 */           block = extendedblockstorage.getBlockByExtId(x, y & 0xF, z);
/*      */         }
/*  531 */         catch (Throwable throwable) {
/*      */           
/*  533 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block");
/*  534 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  539 */     return block;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getBlock(final int x, final int y, final int z) {
/*      */     try {
/*  546 */       return getBlock0(x & 0xF, y, z & 0xF);
/*      */     }
/*  548 */     catch (ReportedException reportedexception) {
/*      */       
/*  550 */       CrashReportCategory crashreportcategory = reportedexception.getCrashReport().makeCategory("Block being got");
/*  551 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>()
/*      */           {
/*      */             public String call() {
/*  554 */               return CrashReportCategory.getCoordinateInfo(new BlockPos((Chunk.this.xPosition << 4) + x, y, (Chunk.this.zPosition << 4) + z));
/*      */             }
/*      */           });
/*  557 */       throw reportedexception;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getBlock(final BlockPos pos) {
/*      */     try {
/*  565 */       return getBlock0(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*      */     }
/*  567 */     catch (ReportedException reportedexception) {
/*      */       
/*  569 */       CrashReportCategory crashreportcategory = reportedexception.getCrashReport().makeCategory("Block being got");
/*  570 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>()
/*      */           {
/*      */             public String call() {
/*  573 */               return CrashReportCategory.getCoordinateInfo(pos);
/*      */             }
/*      */           });
/*  576 */       throw reportedexception;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getBlockState(final BlockPos pos) {
/*  582 */     if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
/*      */       
/*  584 */       IBlockState iblockstate = null;
/*      */       
/*  586 */       if (pos.getY() == 60)
/*      */       {
/*  588 */         iblockstate = Blocks.barrier.getDefaultState();
/*      */       }
/*      */       
/*  591 */       if (pos.getY() == 70)
/*      */       {
/*  593 */         iblockstate = ChunkProviderDebug.func_177461_b(pos.getX(), pos.getZ());
/*      */       }
/*      */       
/*  596 */       return (iblockstate == null) ? Blocks.air.getDefaultState() : iblockstate;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  602 */       if (pos.getY() >= 0 && pos.getY() >> 4 < this.storageArrays.length) {
/*      */         
/*  604 */         ExtendedBlockStorage extendedblockstorage = this.storageArrays[pos.getY() >> 4];
/*      */         
/*  606 */         if (extendedblockstorage != null) {
/*      */           
/*  608 */           int j = pos.getX() & 0xF;
/*  609 */           int k = pos.getY() & 0xF;
/*  610 */           int i = pos.getZ() & 0xF;
/*  611 */           return extendedblockstorage.get(j, k, i);
/*      */         } 
/*      */       } 
/*      */       
/*  615 */       return Blocks.air.getDefaultState();
/*      */     }
/*  617 */     catch (Throwable throwable) {
/*      */       
/*  619 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block state");
/*  620 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being got");
/*  621 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>()
/*      */           {
/*      */             public String call() {
/*  624 */               return CrashReportCategory.getCoordinateInfo(pos);
/*      */             }
/*      */           });
/*  627 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getBlockMetadata(int x, int y, int z) {
/*  637 */     if (y >> 4 >= this.storageArrays.length)
/*      */     {
/*  639 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  643 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4];
/*  644 */     return (extendedblockstorage != null) ? extendedblockstorage.getExtBlockMetadata(x, y & 0xF, z) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBlockMetadata(BlockPos pos) {
/*  650 */     return getBlockMetadata(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState setBlockState(BlockPos pos, IBlockState state) {
/*  655 */     int i = pos.getX() & 0xF;
/*  656 */     int j = pos.getY();
/*  657 */     int k = pos.getZ() & 0xF;
/*  658 */     int l = k << 4 | i;
/*      */     
/*  660 */     if (j >= this.precipitationHeightMap[l] - 1)
/*      */     {
/*  662 */       this.precipitationHeightMap[l] = -999;
/*      */     }
/*      */     
/*  665 */     int i1 = this.heightMap[l];
/*  666 */     IBlockState iblockstate = getBlockState(pos);
/*      */     
/*  668 */     if (iblockstate == state)
/*      */     {
/*  670 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  674 */     Block block = state.getBlock();
/*  675 */     Block block1 = iblockstate.getBlock();
/*  676 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*  677 */     boolean flag = false;
/*      */     
/*  679 */     if (extendedblockstorage == null) {
/*      */       
/*  681 */       if (block == Blocks.air)
/*      */       {
/*  683 */         return null;
/*      */       }
/*      */       
/*  686 */       extendedblockstorage = this.storageArrays[j >> 4] = new ExtendedBlockStorage(j >> 4 << 4, !this.worldObj.provider.getHasNoSky());
/*  687 */       flag = (j >= i1);
/*      */     } 
/*      */     
/*  690 */     extendedblockstorage.set(i, j & 0xF, k, state);
/*      */     
/*  692 */     if (block1 != block)
/*      */     {
/*  694 */       if (!this.worldObj.isRemote) {
/*      */         
/*  696 */         block1.breakBlock(this.worldObj, pos, iblockstate);
/*      */       }
/*  698 */       else if (block1 instanceof ITileEntityProvider) {
/*      */         
/*  700 */         this.worldObj.removeTileEntity(pos);
/*      */       } 
/*      */     }
/*      */     
/*  704 */     if (extendedblockstorage.getBlockByExtId(i, j & 0xF, k) != block)
/*      */     {
/*  706 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  710 */     if (flag) {
/*      */       
/*  712 */       generateSkylightMap();
/*      */     }
/*      */     else {
/*      */       
/*  716 */       int j1 = block.getLightOpacity();
/*  717 */       int k1 = block1.getLightOpacity();
/*      */       
/*  719 */       if (j1 > 0) {
/*      */         
/*  721 */         if (j >= i1)
/*      */         {
/*  723 */           relightBlock(i, j + 1, k);
/*      */         }
/*      */       }
/*  726 */       else if (j == i1 - 1) {
/*      */         
/*  728 */         relightBlock(i, j, k);
/*      */       } 
/*      */       
/*  731 */       if (j1 != k1 && (j1 < k1 || getLightFor(EnumSkyBlock.SKY, pos) > 0 || getLightFor(EnumSkyBlock.BLOCK, pos) > 0))
/*      */       {
/*  733 */         propagateSkylightOcclusion(i, k);
/*      */       }
/*      */     } 
/*      */     
/*  737 */     if (block1 instanceof ITileEntityProvider) {
/*      */       
/*  739 */       TileEntity tileentity = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  741 */       if (tileentity != null)
/*      */       {
/*  743 */         tileentity.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  747 */     if (!this.worldObj.isRemote && block1 != block)
/*      */     {
/*  749 */       block.onBlockAdded(this.worldObj, pos, state);
/*      */     }
/*      */     
/*  752 */     if (block instanceof ITileEntityProvider) {
/*      */       
/*  754 */       TileEntity tileentity1 = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  756 */       if (tileentity1 == null) {
/*      */         
/*  758 */         tileentity1 = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, block.getMetaFromState(state));
/*  759 */         this.worldObj.setTileEntity(pos, tileentity1);
/*      */       } 
/*      */       
/*  762 */       if (tileentity1 != null)
/*      */       {
/*  764 */         tileentity1.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  768 */     this.isModified = true;
/*  769 */     return iblockstate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock p_177413_1_, BlockPos pos) {
/*  776 */     int i = pos.getX() & 0xF;
/*  777 */     int j = pos.getY();
/*  778 */     int k = pos.getZ() & 0xF;
/*  779 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*  780 */     return (extendedblockstorage == null) ? (canSeeSky(pos) ? p_177413_1_.defaultLightValue : 0) : ((p_177413_1_ == EnumSkyBlock.SKY) ? (this.worldObj.provider.getHasNoSky() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k)) : ((p_177413_1_ == EnumSkyBlock.BLOCK) ? extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k) : p_177413_1_.defaultLightValue));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLightFor(EnumSkyBlock p_177431_1_, BlockPos pos, int value) {
/*  785 */     int i = pos.getX() & 0xF;
/*  786 */     int j = pos.getY();
/*  787 */     int k = pos.getZ() & 0xF;
/*  788 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  790 */     if (extendedblockstorage == null) {
/*      */       
/*  792 */       extendedblockstorage = this.storageArrays[j >> 4] = new ExtendedBlockStorage(j >> 4 << 4, !this.worldObj.provider.getHasNoSky());
/*  793 */       generateSkylightMap();
/*      */     } 
/*      */     
/*  796 */     this.isModified = true;
/*      */     
/*  798 */     if (p_177431_1_ == EnumSkyBlock.SKY) {
/*      */       
/*  800 */       if (!this.worldObj.provider.getHasNoSky())
/*      */       {
/*  802 */         extendedblockstorage.setExtSkylightValue(i, j & 0xF, k, value);
/*      */       }
/*      */     }
/*  805 */     else if (p_177431_1_ == EnumSkyBlock.BLOCK) {
/*      */       
/*  807 */       extendedblockstorage.setExtBlocklightValue(i, j & 0xF, k, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightSubtracted(BlockPos pos, int amount) {
/*  813 */     int i = pos.getX() & 0xF;
/*  814 */     int j = pos.getY();
/*  815 */     int k = pos.getZ() & 0xF;
/*  816 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  818 */     if (extendedblockstorage == null)
/*      */     {
/*  820 */       return (!this.worldObj.provider.getHasNoSky() && amount < EnumSkyBlock.SKY.defaultLightValue) ? (EnumSkyBlock.SKY.defaultLightValue - amount) : 0;
/*      */     }
/*      */ 
/*      */     
/*  824 */     int l = this.worldObj.provider.getHasNoSky() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k);
/*  825 */     l -= amount;
/*  826 */     int i1 = extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k);
/*      */     
/*  828 */     if (i1 > l)
/*      */     {
/*  830 */       l = i1;
/*      */     }
/*      */     
/*  833 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEntity(Entity entityIn) {
/*  842 */     this.hasEntities = true;
/*  843 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/*  844 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*      */     
/*  846 */     if (i != this.xPosition || j != this.zPosition) {
/*      */       
/*  848 */       logger.warn("Wrong location! (" + i + ", " + j + ") should be (" + this.xPosition + ", " + this.zPosition + "), " + entityIn, new Object[] { entityIn });
/*  849 */       entityIn.setDead();
/*      */     } 
/*      */     
/*  852 */     int k = MathHelper.floor_double(entityIn.posY / 16.0D);
/*      */     
/*  854 */     if (k < 0)
/*      */     {
/*  856 */       k = 0;
/*      */     }
/*      */     
/*  859 */     if (k >= this.entityLists.length)
/*      */     {
/*  861 */       k = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  864 */     entityIn.addedToChunk = true;
/*  865 */     entityIn.chunkCoordX = this.xPosition;
/*  866 */     entityIn.chunkCoordY = k;
/*  867 */     entityIn.chunkCoordZ = this.zPosition;
/*  868 */     this.entityLists[k].add(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/*  876 */     removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntityAtIndex(Entity entityIn, int p_76608_2_) {
/*  884 */     if (p_76608_2_ < 0)
/*      */     {
/*  886 */       p_76608_2_ = 0;
/*      */     }
/*      */     
/*  889 */     if (p_76608_2_ >= this.entityLists.length)
/*      */     {
/*  891 */       p_76608_2_ = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  894 */     this.entityLists[p_76608_2_].remove(entityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  899 */     int i = pos.getX() & 0xF;
/*  900 */     int j = pos.getY();
/*  901 */     int k = pos.getZ() & 0xF;
/*  902 */     return (j >= this.heightMap[k << 4 | i]);
/*      */   }
/*      */ 
/*      */   
/*      */   private TileEntity createNewTileEntity(BlockPos pos) {
/*  907 */     Block block = getBlock(pos);
/*  908 */     return !block.hasTileEntity() ? null : ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, getBlockMetadata(pos));
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity getTileEntity(BlockPos pos, EnumCreateEntityType p_177424_2_) {
/*  913 */     TileEntity tileentity = this.chunkTileEntityMap.get(pos);
/*      */     
/*  915 */     if (tileentity == null) {
/*      */       
/*  917 */       if (p_177424_2_ == EnumCreateEntityType.IMMEDIATE)
/*      */       {
/*  919 */         tileentity = createNewTileEntity(pos);
/*  920 */         this.worldObj.setTileEntity(pos, tileentity);
/*      */       }
/*  922 */       else if (p_177424_2_ == EnumCreateEntityType.QUEUED)
/*      */       {
/*  924 */         this.tileEntityPosQueue.add(pos);
/*      */       }
/*      */     
/*  927 */     } else if (tileentity.isInvalid()) {
/*      */       
/*  929 */       this.chunkTileEntityMap.remove(pos);
/*  930 */       return null;
/*      */     } 
/*      */     
/*  933 */     return tileentity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntity(TileEntity tileEntityIn) {
/*  938 */     addTileEntity(tileEntityIn.getPos(), tileEntityIn);
/*      */     
/*  940 */     if (this.isChunkLoaded)
/*      */     {
/*  942 */       this.worldObj.addTileEntity(tileEntityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntity(BlockPos pos, TileEntity tileEntityIn) {
/*  948 */     tileEntityIn.setWorldObj(this.worldObj);
/*  949 */     tileEntityIn.setPos(pos);
/*      */     
/*  951 */     if (getBlock(pos) instanceof ITileEntityProvider) {
/*      */       
/*  953 */       if (this.chunkTileEntityMap.containsKey(pos))
/*      */       {
/*  955 */         ((TileEntity)this.chunkTileEntityMap.get(pos)).invalidate();
/*      */       }
/*      */       
/*  958 */       tileEntityIn.validate();
/*  959 */       this.chunkTileEntityMap.put(pos, tileEntityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/*  965 */     if (this.isChunkLoaded) {
/*      */       
/*  967 */       TileEntity tileentity = this.chunkTileEntityMap.remove(pos);
/*      */       
/*  969 */       if (tileentity != null)
/*      */       {
/*  971 */         tileentity.invalidate();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {
/*  981 */     this.isChunkLoaded = true;
/*  982 */     this.worldObj.addTileEntities(this.chunkTileEntityMap.values());
/*      */     
/*  984 */     for (int i = 0; i < this.entityLists.length; i++) {
/*      */       
/*  986 */       for (Entity entity : this.entityLists[i])
/*      */       {
/*  988 */         entity.onChunkLoad();
/*      */       }
/*      */       
/*  991 */       this.worldObj.loadEntities((Collection)this.entityLists[i]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkUnload() {
/* 1000 */     this.isChunkLoaded = false;
/*      */     
/* 1002 */     for (TileEntity tileentity : this.chunkTileEntityMap.values())
/*      */     {
/* 1004 */       this.worldObj.markTileEntityForRemoval(tileentity);
/*      */     }
/*      */     
/* 1007 */     for (int i = 0; i < this.entityLists.length; i++)
/*      */     {
/* 1009 */       this.worldObj.unloadEntities((Collection)this.entityLists[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChunkModified() {
/* 1018 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getEntitiesWithinAABBForEntity(Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill, Predicate<? super Entity> p_177414_4_) {
/* 1026 */     int i = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
/* 1027 */     int j = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
/* 1028 */     i = MathHelper.clamp_int(i, 0, this.entityLists.length - 1);
/* 1029 */     j = MathHelper.clamp_int(j, 0, this.entityLists.length - 1);
/*      */     
/* 1031 */     for (int k = i; k <= j; k++) {
/*      */       
/* 1033 */       if (!this.entityLists[k].isEmpty())
/*      */       {
/* 1035 */         for (Entity entity : this.entityLists[k]) {
/*      */           
/* 1037 */           if (entity.getEntityBoundingBox().intersectsWith(aabb) && entity != entityIn) {
/*      */             
/* 1039 */             if (p_177414_4_ == null || p_177414_4_.apply(entity))
/*      */             {
/* 1041 */               listToFill.add(entity);
/*      */             }
/*      */             
/* 1044 */             Entity[] aentity = entity.getParts();
/*      */             
/* 1046 */             if (aentity != null)
/*      */             {
/* 1048 */               for (int l = 0; l < aentity.length; l++) {
/*      */                 
/* 1050 */                 entity = aentity[l];
/*      */                 
/* 1052 */                 if (entity != entityIn && entity.getEntityBoundingBox().intersectsWith(aabb) && (p_177414_4_ == null || p_177414_4_.apply(entity)))
/*      */                 {
/* 1054 */                   listToFill.add(entity);
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> void getEntitiesOfTypeWithinAAAB(Class<? extends T> entityClass, AxisAlignedBB aabb, List<T> listToFill, Predicate<? super T> p_177430_4_) {
/* 1066 */     int i = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
/* 1067 */     int j = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
/* 1068 */     i = MathHelper.clamp_int(i, 0, this.entityLists.length - 1);
/* 1069 */     j = MathHelper.clamp_int(j, 0, this.entityLists.length - 1);
/*      */     
/* 1071 */     for (int k = i; k <= j; k++) {
/*      */       
/* 1073 */       for (Entity entity : this.entityLists[k].getByClass(entityClass)) {
/*      */         
/* 1075 */         if (entity.getEntityBoundingBox().intersectsWith(aabb) && (p_177430_4_ == null || p_177430_4_.apply(entity)))
/*      */         {
/* 1077 */           listToFill.add((T)entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needsSaving(boolean p_76601_1_) {
/* 1088 */     if (p_76601_1_) {
/*      */       
/* 1090 */       if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified)
/*      */       {
/* 1092 */         return true;
/*      */       }
/*      */     }
/* 1095 */     else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
/*      */       
/* 1097 */       return true;
/*      */     } 
/*      */     
/* 1100 */     return this.isModified;
/*      */   }
/*      */ 
/*      */   
/*      */   public Random getRandomWithSeed(long seed) {
/* 1105 */     return new Random(this.worldObj.getSeed() + (this.xPosition * this.xPosition * 4987142) + (this.xPosition * 5947611) + (this.zPosition * this.zPosition) * 4392871L + (this.zPosition * 389711) ^ seed);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1110 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void populateChunk(IChunkProvider p_76624_1_, IChunkProvider p_76624_2_, int x, int z) {
/* 1115 */     boolean flag = p_76624_1_.chunkExists(x, z - 1);
/* 1116 */     boolean flag1 = p_76624_1_.chunkExists(x + 1, z);
/* 1117 */     boolean flag2 = p_76624_1_.chunkExists(x, z + 1);
/* 1118 */     boolean flag3 = p_76624_1_.chunkExists(x - 1, z);
/* 1119 */     boolean flag4 = p_76624_1_.chunkExists(x - 1, z - 1);
/* 1120 */     boolean flag5 = p_76624_1_.chunkExists(x + 1, z + 1);
/* 1121 */     boolean flag6 = p_76624_1_.chunkExists(x - 1, z + 1);
/* 1122 */     boolean flag7 = p_76624_1_.chunkExists(x + 1, z - 1);
/*      */     
/* 1124 */     if (flag1 && flag2 && flag5)
/*      */     {
/* 1126 */       if (!this.isTerrainPopulated) {
/*      */         
/* 1128 */         p_76624_1_.populate(p_76624_2_, x, z);
/*      */       }
/*      */       else {
/*      */         
/* 1132 */         p_76624_1_.populateChunk(p_76624_2_, this, x, z);
/*      */       } 
/*      */     }
/*      */     
/* 1136 */     if (flag3 && flag2 && flag6) {
/*      */       
/* 1138 */       Chunk chunk = p_76624_1_.provideChunk(x - 1, z);
/*      */       
/* 1140 */       if (!chunk.isTerrainPopulated) {
/*      */         
/* 1142 */         p_76624_1_.populate(p_76624_2_, x - 1, z);
/*      */       }
/*      */       else {
/*      */         
/* 1146 */         p_76624_1_.populateChunk(p_76624_2_, chunk, x - 1, z);
/*      */       } 
/*      */     } 
/*      */     
/* 1150 */     if (flag && flag1 && flag7) {
/*      */       
/* 1152 */       Chunk chunk1 = p_76624_1_.provideChunk(x, z - 1);
/*      */       
/* 1154 */       if (!chunk1.isTerrainPopulated) {
/*      */         
/* 1156 */         p_76624_1_.populate(p_76624_2_, x, z - 1);
/*      */       }
/*      */       else {
/*      */         
/* 1160 */         p_76624_1_.populateChunk(p_76624_2_, chunk1, x, z - 1);
/*      */       } 
/*      */     } 
/*      */     
/* 1164 */     if (flag4 && flag && flag3) {
/*      */       
/* 1166 */       Chunk chunk2 = p_76624_1_.provideChunk(x - 1, z - 1);
/*      */       
/* 1168 */       if (!chunk2.isTerrainPopulated) {
/*      */         
/* 1170 */         p_76624_1_.populate(p_76624_2_, x - 1, z - 1);
/*      */       }
/*      */       else {
/*      */         
/* 1174 */         p_76624_1_.populateChunk(p_76624_2_, chunk2, x - 1, z - 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/* 1181 */     int i = pos.getX() & 0xF;
/* 1182 */     int j = pos.getZ() & 0xF;
/* 1183 */     int k = i | j << 4;
/* 1184 */     BlockPos blockpos = new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */     
/* 1186 */     if (blockpos.getY() == -999) {
/*      */       
/* 1188 */       int l = getTopFilledSegment() + 15;
/* 1189 */       blockpos = new BlockPos(pos.getX(), l, pos.getZ());
/* 1190 */       int i1 = -1;
/*      */       
/* 1192 */       while (blockpos.getY() > 0 && i1 == -1) {
/*      */         
/* 1194 */         Block block = getBlock(blockpos);
/* 1195 */         Material material = block.getMaterial();
/*      */         
/* 1197 */         if (!material.blocksMovement() && !material.isLiquid()) {
/*      */           
/* 1199 */           blockpos = blockpos.down();
/*      */           
/*      */           continue;
/*      */         } 
/* 1203 */         i1 = blockpos.getY() + 1;
/*      */       } 
/*      */ 
/*      */       
/* 1207 */       this.precipitationHeightMap[k] = i1;
/*      */     } 
/*      */     
/* 1210 */     return new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_150804_b(boolean p_150804_1_) {
/* 1215 */     if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !p_150804_1_)
/*      */     {
/* 1217 */       recheckGaps(this.worldObj.isRemote);
/*      */     }
/*      */     
/* 1220 */     this.field_150815_m = true;
/*      */     
/* 1222 */     if (!this.isLightPopulated && this.isTerrainPopulated)
/*      */     {
/* 1224 */       func_150809_p();
/*      */     }
/*      */     
/* 1227 */     while (!this.tileEntityPosQueue.isEmpty()) {
/*      */       
/* 1229 */       BlockPos blockpos = this.tileEntityPosQueue.poll();
/*      */       
/* 1231 */       if (getTileEntity(blockpos, EnumCreateEntityType.CHECK) == null && getBlock(blockpos).hasTileEntity()) {
/*      */         
/* 1233 */         TileEntity tileentity = createNewTileEntity(blockpos);
/* 1234 */         this.worldObj.setTileEntity(blockpos, tileentity);
/* 1235 */         this.worldObj.markBlockRangeForRenderUpdate(blockpos, blockpos);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPopulated() {
/* 1242 */     return (this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChunkCoordIntPair getChunkCoordIntPair() {
/* 1250 */     return new ChunkCoordIntPair(this.xPosition, this.zPosition);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAreLevelsEmpty(int startY, int endY) {
/* 1259 */     if (startY < 0)
/*      */     {
/* 1261 */       startY = 0;
/*      */     }
/*      */     
/* 1264 */     if (endY >= 256)
/*      */     {
/* 1266 */       endY = 255;
/*      */     }
/*      */     
/* 1269 */     for (int i = startY; i <= endY; i += 16) {
/*      */       
/* 1271 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[i >> 4];
/*      */       
/* 1273 */       if (extendedblockstorage != null && !extendedblockstorage.isEmpty())
/*      */       {
/* 1275 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1279 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStorageArrays(ExtendedBlockStorage[] newStorageArrays) {
/* 1284 */     if (this.storageArrays.length != newStorageArrays.length) {
/*      */       
/* 1286 */       logger.warn("Could not set level chunk sections, array length is " + newStorageArrays.length + " instead of " + this.storageArrays.length);
/*      */     }
/*      */     else {
/*      */       
/* 1290 */       System.arraycopy(newStorageArrays, 0, this.storageArrays, 0, this.storageArrays.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillChunk(byte[] p_177439_1_, int p_177439_2_, boolean p_177439_3_) {
/* 1299 */     int i = 0;
/* 1300 */     boolean flag = !this.worldObj.provider.getHasNoSky();
/*      */     
/* 1302 */     for (int j = 0; j < this.storageArrays.length; j++) {
/*      */       
/* 1304 */       if ((p_177439_2_ & 1 << j) != 0) {
/*      */         
/* 1306 */         if (this.storageArrays[j] == null)
/*      */         {
/* 1308 */           this.storageArrays[j] = new ExtendedBlockStorage(j << 4, flag);
/*      */         }
/*      */         
/* 1311 */         char[] achar = this.storageArrays[j].getData();
/*      */         
/* 1313 */         for (int k = 0; k < achar.length; k++)
/*      */         {
/* 1315 */           achar[k] = (char)((p_177439_1_[i + 1] & 0xFF) << 8 | p_177439_1_[i] & 0xFF);
/* 1316 */           i += 2;
/*      */         }
/*      */       
/* 1319 */       } else if (p_177439_3_ && this.storageArrays[j] != null) {
/*      */         
/* 1321 */         this.storageArrays[j] = null;
/*      */       } 
/*      */     } 
/*      */     int l;
/* 1325 */     for (l = 0; l < this.storageArrays.length; l++) {
/*      */       
/* 1327 */       if ((p_177439_2_ & 1 << l) != 0 && this.storageArrays[l] != null) {
/*      */         
/* 1329 */         NibbleArray nibblearray = this.storageArrays[l].getBlocklightArray();
/* 1330 */         System.arraycopy(p_177439_1_, i, nibblearray.getData(), 0, (nibblearray.getData()).length);
/* 1331 */         i += (nibblearray.getData()).length;
/*      */       } 
/*      */     } 
/*      */     
/* 1335 */     if (flag)
/*      */     {
/* 1337 */       for (int i1 = 0; i1 < this.storageArrays.length; i1++) {
/*      */         
/* 1339 */         if ((p_177439_2_ & 1 << i1) != 0 && this.storageArrays[i1] != null) {
/*      */           
/* 1341 */           NibbleArray nibblearray1 = this.storageArrays[i1].getSkylightArray();
/* 1342 */           System.arraycopy(p_177439_1_, i, nibblearray1.getData(), 0, (nibblearray1.getData()).length);
/* 1343 */           i += (nibblearray1.getData()).length;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1348 */     if (p_177439_3_) {
/*      */       
/* 1350 */       System.arraycopy(p_177439_1_, i, this.blockBiomeArray, 0, this.blockBiomeArray.length);
/* 1351 */       l = i + this.blockBiomeArray.length;
/*      */     } 
/*      */     
/* 1354 */     for (int j1 = 0; j1 < this.storageArrays.length; j1++) {
/*      */       
/* 1356 */       if (this.storageArrays[j1] != null && (p_177439_2_ & 1 << j1) != 0)
/*      */       {
/* 1358 */         this.storageArrays[j1].removeInvalidBlocks();
/*      */       }
/*      */     } 
/*      */     
/* 1362 */     this.isLightPopulated = true;
/* 1363 */     this.isTerrainPopulated = true;
/* 1364 */     generateHeightMap();
/*      */     
/* 1366 */     for (TileEntity tileentity : this.chunkTileEntityMap.values())
/*      */     {
/* 1368 */       tileentity.updateContainingBlockInfo();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BiomeGenBase getBiome(BlockPos pos, WorldChunkManager chunkManager) {
/* 1374 */     int i = pos.getX() & 0xF;
/* 1375 */     int j = pos.getZ() & 0xF;
/* 1376 */     int k = this.blockBiomeArray[j << 4 | i] & 0xFF;
/*      */     
/* 1378 */     if (k == 255) {
/*      */       
/* 1380 */       BiomeGenBase biomegenbase = chunkManager.getBiomeGenerator(pos, BiomeGenBase.plains);
/* 1381 */       k = biomegenbase.biomeID;
/* 1382 */       this.blockBiomeArray[j << 4 | i] = (byte)(k & 0xFF);
/*      */     } 
/*      */     
/* 1385 */     BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(k);
/* 1386 */     return (biomegenbase1 == null) ? BiomeGenBase.plains : biomegenbase1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBiomeArray() {
/* 1394 */     return this.blockBiomeArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBiomeArray(byte[] biomeArray) {
/* 1403 */     if (this.blockBiomeArray.length != biomeArray.length) {
/*      */       
/* 1405 */       logger.warn("Could not set level chunk biomes, array length is " + biomeArray.length + " instead of " + this.blockBiomeArray.length);
/*      */     }
/*      */     else {
/*      */       
/* 1409 */       System.arraycopy(biomeArray, 0, this.blockBiomeArray, 0, this.blockBiomeArray.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetRelightChecks() {
/* 1418 */     this.queuedLightChecks = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enqueueRelightChecks() {
/* 1428 */     BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */     
/* 1430 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1432 */       if (this.queuedLightChecks >= 4096) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1437 */       int j = this.queuedLightChecks % 16;
/* 1438 */       int k = this.queuedLightChecks / 16 % 16;
/* 1439 */       int l = this.queuedLightChecks / 256;
/* 1440 */       this.queuedLightChecks++;
/*      */       
/* 1442 */       for (int i1 = 0; i1 < 16; i1++) {
/*      */         
/* 1444 */         BlockPos blockpos1 = blockpos.add(k, (j << 4) + i1, l);
/* 1445 */         boolean flag = (i1 == 0 || i1 == 15 || k == 0 || k == 15 || l == 0 || l == 15);
/*      */         
/* 1447 */         if ((this.storageArrays[j] == null && flag) || (this.storageArrays[j] != null && this.storageArrays[j].getBlockByExtId(k, i1, l).getMaterial() == Material.air)) {
/*      */           
/* 1449 */           for (EnumFacing enumfacing : EnumFacing.values()) {
/*      */             
/* 1451 */             BlockPos blockpos2 = blockpos1.offset(enumfacing);
/*      */             
/* 1453 */             if (this.worldObj.getBlockState(blockpos2).getBlock().getLightValue() > 0)
/*      */             {
/* 1455 */               this.worldObj.checkLight(blockpos2);
/*      */             }
/*      */           } 
/*      */           
/* 1459 */           this.worldObj.checkLight(blockpos1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_150809_p() {
/* 1467 */     this.isTerrainPopulated = true;
/* 1468 */     this.isLightPopulated = true;
/* 1469 */     BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */     
/* 1471 */     if (!this.worldObj.provider.getHasNoSky())
/*      */     {
/* 1473 */       if (this.worldObj.isAreaLoaded(blockpos.add(-1, 0, -1), blockpos.add(16, this.worldObj.getSeaLevel(), 16))) {
/*      */         int i;
/*      */ 
/*      */         
/* 1477 */         label31: for (i = 0; i < 16; i++) {
/*      */           
/* 1479 */           for (int j = 0; j < 16; j++) {
/*      */             
/* 1481 */             if (!func_150811_f(i, j)) {
/*      */               
/* 1483 */               this.isLightPopulated = false;
/*      */               
/*      */               break label31;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1489 */         if (this.isLightPopulated)
/*      */         {
/* 1491 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*      */             
/* 1493 */             int k = (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? 16 : 1;
/* 1494 */             this.worldObj.getChunkFromBlockCoords(blockpos.offset(enumfacing, k)).func_180700_a(enumfacing.getOpposite());
/*      */           } 
/*      */           
/* 1497 */           func_177441_y();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1502 */         this.isLightPopulated = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_177441_y() {
/* 1509 */     for (int i = 0; i < this.updateSkylightColumns.length; i++)
/*      */     {
/* 1511 */       this.updateSkylightColumns[i] = true;
/*      */     }
/*      */     
/* 1514 */     recheckGaps(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_180700_a(EnumFacing facing) {
/* 1519 */     if (this.isTerrainPopulated)
/*      */     {
/* 1521 */       if (facing == EnumFacing.EAST) {
/*      */         
/* 1523 */         for (int i = 0; i < 16; i++)
/*      */         {
/* 1525 */           func_150811_f(15, i);
/*      */         }
/*      */       }
/* 1528 */       else if (facing == EnumFacing.WEST) {
/*      */         
/* 1530 */         for (int j = 0; j < 16; j++)
/*      */         {
/* 1532 */           func_150811_f(0, j);
/*      */         }
/*      */       }
/* 1535 */       else if (facing == EnumFacing.SOUTH) {
/*      */         
/* 1537 */         for (int k = 0; k < 16; k++)
/*      */         {
/* 1539 */           func_150811_f(k, 15);
/*      */         }
/*      */       }
/* 1542 */       else if (facing == EnumFacing.NORTH) {
/*      */         
/* 1544 */         for (int l = 0; l < 16; l++)
/*      */         {
/* 1546 */           func_150811_f(l, 0);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_150811_f(int x, int z) {
/* 1554 */     int i = getTopFilledSegment();
/* 1555 */     boolean flag = false;
/* 1556 */     boolean flag1 = false;
/* 1557 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos((this.xPosition << 4) + x, 0, (this.zPosition << 4) + z);
/*      */     
/* 1559 */     for (int j = i + 16 - 1; j > this.worldObj.getSeaLevel() || (j > 0 && !flag1); j--) {
/*      */       
/* 1561 */       blockpos$mutableblockpos.set(blockpos$mutableblockpos.getX(), j, blockpos$mutableblockpos.getZ());
/* 1562 */       int k = getBlockLightOpacity((BlockPos)blockpos$mutableblockpos);
/*      */       
/* 1564 */       if (k == 255 && blockpos$mutableblockpos.getY() < this.worldObj.getSeaLevel())
/*      */       {
/* 1566 */         flag1 = true;
/*      */       }
/*      */       
/* 1569 */       if (!flag && k > 0) {
/*      */         
/* 1571 */         flag = true;
/*      */       }
/* 1573 */       else if (flag && k == 0 && !this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos)) {
/*      */         
/* 1575 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1579 */     for (int l = blockpos$mutableblockpos.getY(); l > 0; l--) {
/*      */       
/* 1581 */       blockpos$mutableblockpos.set(blockpos$mutableblockpos.getX(), l, blockpos$mutableblockpos.getZ());
/*      */       
/* 1583 */       if (getBlock((BlockPos)blockpos$mutableblockpos).getLightValue() > 0)
/*      */       {
/* 1585 */         this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/*      */     
/* 1589 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLoaded() {
/* 1594 */     return this.isChunkLoaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChunkLoaded(boolean loaded) {
/* 1599 */     this.isChunkLoaded = loaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorld() {
/* 1604 */     return this.worldObj;
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] getHeightMap() {
/* 1609 */     return this.heightMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHeightMap(int[] newHeightMap) {
/* 1614 */     if (this.heightMap.length != newHeightMap.length) {
/*      */       
/* 1616 */       logger.warn("Could not set level chunk heightmap, array length is " + newHeightMap.length + " instead of " + this.heightMap.length);
/*      */     }
/*      */     else {
/*      */       
/* 1620 */       System.arraycopy(newHeightMap, 0, this.heightMap, 0, this.heightMap.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<BlockPos, TileEntity> getTileEntityMap() {
/* 1626 */     return this.chunkTileEntityMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public ClassInheritanceMultiMap<Entity>[] getEntityLists() {
/* 1631 */     return this.entityLists;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTerrainPopulated() {
/* 1636 */     return this.isTerrainPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTerrainPopulated(boolean terrainPopulated) {
/* 1641 */     this.isTerrainPopulated = terrainPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLightPopulated() {
/* 1646 */     return this.isLightPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLightPopulated(boolean lightPopulated) {
/* 1651 */     this.isLightPopulated = lightPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setModified(boolean modified) {
/* 1656 */     this.isModified = modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHasEntities(boolean hasEntitiesIn) {
/* 1661 */     this.hasEntities = hasEntitiesIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastSaveTime(long saveTime) {
/* 1666 */     this.lastSaveTime = saveTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLowestHeight() {
/* 1671 */     return this.heightMapMinimum;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getInhabitedTime() {
/* 1676 */     return this.inhabitedTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInhabitedTime(long newInhabitedTime) {
/* 1681 */     this.inhabitedTime = newInhabitedTime;
/*      */   }
/*      */   
/*      */   public enum EnumCreateEntityType
/*      */   {
/* 1686 */     IMMEDIATE,
/* 1687 */     QUEUED,
/* 1688 */     CHECK;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\chunk\Chunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */