/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.EmptyChunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkProviderServer
/*     */   implements IChunkProvider
/*     */ {
/*  30 */   private static final Logger logger = LogManager.getLogger();
/*  31 */   private final Set<Long> droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */ 
/*     */ 
/*     */   
/*     */   private final Chunk dummyChunk;
/*     */ 
/*     */   
/*     */   private final IChunkProvider serverChunkGenerator;
/*     */ 
/*     */   
/*     */   private final IChunkLoader chunkLoader;
/*     */ 
/*     */   
/*     */   public boolean chunkLoadOverride = true;
/*     */ 
/*     */   
/*  47 */   private final LongHashMap<Chunk> id2ChunkMap = new LongHashMap();
/*  48 */   private final List<Chunk> loadedChunks = Lists.newArrayList();
/*     */   
/*     */   private final WorldServer worldObj;
/*     */   
/*     */   public ChunkProviderServer(WorldServer p_i1520_1_, IChunkLoader p_i1520_2_, IChunkProvider p_i1520_3_) {
/*  53 */     this.dummyChunk = (Chunk)new EmptyChunk((World)p_i1520_1_, 0, 0);
/*  54 */     this.worldObj = p_i1520_1_;
/*  55 */     this.chunkLoader = p_i1520_2_;
/*  56 */     this.serverChunkGenerator = p_i1520_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/*  64 */     return this.id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Chunk> func_152380_a() {
/*  69 */     return this.loadedChunks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropChunk(int x, int z) {
/*  74 */     if (this.worldObj.provider.canRespawnHere()) {
/*     */       
/*  76 */       if (!this.worldObj.isSpawnChunk(x, z))
/*     */       {
/*  78 */         this.droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(x, z)));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  83 */       this.droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(x, z)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadAllChunks() {
/*  92 */     for (Chunk chunk : this.loadedChunks)
/*     */     {
/*  94 */       dropChunk(chunk.xPosition, chunk.zPosition);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(int chunkX, int chunkZ) {
/* 106 */     long i = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
/* 107 */     this.droppedChunksSet.remove(Long.valueOf(i));
/* 108 */     Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(i);
/*     */     
/* 110 */     if (chunk == null) {
/*     */       
/* 112 */       chunk = loadChunkFromFile(chunkX, chunkZ);
/*     */       
/* 114 */       if (chunk == null)
/*     */       {
/* 116 */         if (this.serverChunkGenerator == null) {
/*     */           
/* 118 */           chunk = this.dummyChunk;
/*     */         } else {
/*     */ 
/*     */           
/*     */           try {
/*     */             
/* 124 */             chunk = this.serverChunkGenerator.provideChunk(chunkX, chunkZ);
/*     */           }
/* 126 */           catch (Throwable throwable) {
/*     */             
/* 128 */             CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception generating new chunk");
/* 129 */             CrashReportCategory crashreportcategory = crashreport.makeCategory("Chunk to be generated");
/* 130 */             crashreportcategory.addCrashSection("Location", String.format("%d,%d", new Object[] { Integer.valueOf(chunkX), Integer.valueOf(chunkZ) }));
/* 131 */             crashreportcategory.addCrashSection("Position hash", Long.valueOf(i));
/* 132 */             crashreportcategory.addCrashSection("Generator", this.serverChunkGenerator.makeString());
/* 133 */             throw new ReportedException(crashreport);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 138 */       this.id2ChunkMap.add(i, chunk);
/* 139 */       this.loadedChunks.add(chunk);
/* 140 */       chunk.onChunkLoad();
/* 141 */       chunk.populateChunk(this, this, chunkX, chunkZ);
/*     */     } 
/*     */     
/* 144 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 153 */     Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(x, z));
/* 154 */     return (chunk == null) ? ((!this.worldObj.isFindingSpawnPoint() && !this.chunkLoadOverride) ? this.dummyChunk : loadChunk(x, z)) : chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   private Chunk loadChunkFromFile(int x, int z) {
/* 159 */     if (this.chunkLoader == null)
/*     */     {
/* 161 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 167 */       Chunk chunk = this.chunkLoader.loadChunk((World)this.worldObj, x, z);
/*     */       
/* 169 */       if (chunk != null) {
/*     */         
/* 171 */         chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
/*     */         
/* 173 */         if (this.serverChunkGenerator != null)
/*     */         {
/* 175 */           this.serverChunkGenerator.recreateStructures(chunk, x, z);
/*     */         }
/*     */       } 
/*     */       
/* 179 */       return chunk;
/*     */     }
/* 181 */     catch (Exception exception) {
/*     */       
/* 183 */       logger.error("Couldn't load chunk", exception);
/* 184 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveChunkExtraData(Chunk chunkIn) {
/* 191 */     if (this.chunkLoader != null) {
/*     */       
/*     */       try {
/*     */         
/* 195 */         this.chunkLoader.saveExtraChunkData((World)this.worldObj, chunkIn);
/*     */       }
/* 197 */       catch (Exception exception) {
/*     */         
/* 199 */         logger.error("Couldn't save entities", exception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void saveChunkData(Chunk chunkIn) {
/* 206 */     if (this.chunkLoader != null) {
/*     */       
/*     */       try {
/*     */         
/* 210 */         chunkIn.setLastSaveTime(this.worldObj.getTotalWorldTime());
/* 211 */         this.chunkLoader.saveChunk((World)this.worldObj, chunkIn);
/* 212 */       } catch (MinecraftException minecraftexception) {
/*     */         
/* 214 */         logger.error("Couldn't save chunk; already in use by another instance of Minecraft?", (Throwable)minecraftexception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 224 */     Chunk chunk = provideChunk(x, z);
/*     */     
/* 226 */     if (!chunk.isTerrainPopulated()) {
/*     */       
/* 228 */       chunk.func_150809_p();
/*     */       
/* 230 */       if (this.serverChunkGenerator != null) {
/*     */         
/* 232 */         this.serverChunkGenerator.populate(chunkProvider, x, z);
/* 233 */         chunk.setChunkModified();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 240 */     if (this.serverChunkGenerator != null && this.serverChunkGenerator.populateChunk(chunkProvider, chunkIn, x, z)) {
/*     */       
/* 242 */       Chunk chunk = provideChunk(x, z);
/* 243 */       chunk.setChunkModified();
/* 244 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 248 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 258 */     int i = 0;
/* 259 */     List<Chunk> list = Lists.newArrayList(this.loadedChunks);
/*     */     
/* 261 */     for (int j = 0; j < list.size(); j++) {
/*     */       
/* 263 */       Chunk chunk = list.get(j);
/*     */       
/* 265 */       if (saveAllChunks)
/*     */       {
/* 267 */         saveChunkExtraData(chunk);
/*     */       }
/*     */       
/* 270 */       if (chunk.needsSaving(saveAllChunks)) {
/*     */         
/* 272 */         saveChunkData(chunk);
/* 273 */         chunk.setModified(false);
/* 274 */         i++;
/*     */         
/* 276 */         if (i == 24 && !saveAllChunks)
/*     */         {
/* 278 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/* 292 */     if (this.chunkLoader != null)
/*     */     {
/* 294 */       this.chunkLoader.saveExtraData();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 303 */     if (!this.worldObj.disableLevelSaving) {
/*     */       
/* 305 */       for (int i = 0; i < 100; i++) {
/*     */         
/* 307 */         if (!this.droppedChunksSet.isEmpty()) {
/*     */           
/* 309 */           Long olong = this.droppedChunksSet.iterator().next();
/* 310 */           Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(olong.longValue());
/*     */           
/* 312 */           if (chunk != null) {
/*     */             
/* 314 */             chunk.onChunkUnload();
/* 315 */             saveChunkData(chunk);
/* 316 */             saveChunkExtraData(chunk);
/* 317 */             this.id2ChunkMap.remove(olong.longValue());
/* 318 */             this.loadedChunks.remove(chunk);
/*     */           } 
/*     */           
/* 321 */           this.droppedChunksSet.remove(olong);
/*     */         } 
/*     */       } 
/*     */       
/* 325 */       if (this.chunkLoader != null)
/*     */       {
/* 327 */         this.chunkLoader.chunkTick();
/*     */       }
/*     */     } 
/*     */     
/* 331 */     return this.serverChunkGenerator.unloadQueuedChunks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 339 */     return !this.worldObj.disableLevelSaving;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 347 */     return "ServerChunkCache: " + this.id2ChunkMap.getNumHashElements() + " Drop: " + this.droppedChunksSet.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 352 */     return this.serverChunkGenerator.getPossibleCreatures(creatureType, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 357 */     return this.serverChunkGenerator.getStrongholdGen(worldIn, structureName, position);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 362 */     return this.id2ChunkMap.getNumHashElements();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 371 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\ChunkProviderServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */