/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.EmptyChunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkProviderClient
/*     */   implements IChunkProvider
/*     */ {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private final Chunk blankChunk;
/*     */ 
/*     */   
/*  28 */   private final LongHashMap<Chunk> chunkMapping = new LongHashMap();
/*  29 */   private final List<Chunk> chunkListing = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private final World worldObj;
/*     */ 
/*     */   
/*     */   public ChunkProviderClient(World worldIn) {
/*  36 */     this.blankChunk = (Chunk)new EmptyChunk(worldIn, 0, 0);
/*  37 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadChunk(int x, int z) {
/*  54 */     Chunk chunk = provideChunk(x, z);
/*     */     
/*  56 */     if (!chunk.isEmpty())
/*     */     {
/*  58 */       chunk.onChunkUnload();
/*     */     }
/*     */     
/*  61 */     this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(x, z));
/*  62 */     this.chunkListing.remove(chunk);
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
/*  73 */     Chunk chunk = new Chunk(this.worldObj, chunkX, chunkZ);
/*  74 */     this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ), chunk);
/*  75 */     this.chunkListing.add(chunk);
/*  76 */     chunk.setChunkLoaded(true);
/*  77 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/*  86 */     Chunk chunk = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(x, z));
/*  87 */     return (chunk == null) ? this.blankChunk : chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 112 */     long i = System.currentTimeMillis();
/*     */     
/* 114 */     for (Chunk chunk : this.chunkListing)
/*     */     {
/* 116 */       chunk.func_150804_b((System.currentTimeMillis() - i > 5L));
/*     */     }
/*     */     
/* 119 */     if (System.currentTimeMillis() - i > 100L)
/*     */     {
/* 121 */       logger.info("Warning: Clientside chunk ticking took {} ms", new Object[] { Long.valueOf(System.currentTimeMillis() - i) });
/*     */     }
/*     */     
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 152 */     return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 167 */     return this.chunkListing.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 176 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\multiplayer\ChunkProviderClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */