/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomeCache
/*     */ {
/*     */   final WorldChunkManager chunkManager;
/*     */   private long lastCleanupTime;
/*  16 */   private final LongHashMap<Block> cacheMap = new LongHashMap();
/*  17 */   private final List<Block> cache = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public BiomeCache(WorldChunkManager chunkManagerIn) {
/*  21 */     this.chunkManager = chunkManagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBiomeCacheBlock(int x, int z) {
/*  29 */     x >>= 4;
/*  30 */     z >>= 4;
/*  31 */     long i = x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32L;
/*  32 */     Block biomecache$block = (Block)this.cacheMap.getValueByKey(i);
/*     */     
/*  34 */     if (biomecache$block == null) {
/*     */       
/*  36 */       biomecache$block = new Block(x, z);
/*  37 */       this.cacheMap.add(i, biomecache$block);
/*  38 */       this.cache.add(biomecache$block);
/*     */     } 
/*     */     
/*  41 */     biomecache$block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
/*  42 */     return biomecache$block;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeGenBase func_180284_a(int x, int z, BiomeGenBase p_180284_3_) {
/*  47 */     BiomeGenBase biomegenbase = getBiomeCacheBlock(x, z).getBiomeGenAt(x, z);
/*  48 */     return (biomegenbase == null) ? p_180284_3_ : biomegenbase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupCache() {
/*  56 */     long i = MinecraftServer.getCurrentTimeMillis();
/*  57 */     long j = i - this.lastCleanupTime;
/*     */     
/*  59 */     if (j > 7500L || j < 0L) {
/*     */       
/*  61 */       this.lastCleanupTime = i;
/*     */       
/*  63 */       for (int k = 0; k < this.cache.size(); k++) {
/*     */         
/*  65 */         Block biomecache$block = this.cache.get(k);
/*  66 */         long l = i - biomecache$block.lastAccessTime;
/*     */         
/*  68 */         if (l > 30000L || l < 0L) {
/*     */           
/*  70 */           this.cache.remove(k--);
/*  71 */           long i1 = biomecache$block.xPosition & 0xFFFFFFFFL | (biomecache$block.zPosition & 0xFFFFFFFFL) << 32L;
/*  72 */           this.cacheMap.remove(i1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] getCachedBiomes(int x, int z) {
/*  83 */     return (getBiomeCacheBlock(x, z)).biomes;
/*     */   }
/*     */   
/*     */   public class Block
/*     */   {
/*  88 */     public float[] rainfallValues = new float[256];
/*  89 */     public BiomeGenBase[] biomes = new BiomeGenBase[256];
/*     */     
/*     */     public int xPosition;
/*     */     public int zPosition;
/*     */     public long lastAccessTime;
/*     */     
/*     */     public Block(int x, int z) {
/*  96 */       this.xPosition = x;
/*  97 */       this.zPosition = z;
/*  98 */       BiomeCache.this.chunkManager.getRainfall(this.rainfallValues, x << 4, z << 4, 16, 16);
/*  99 */       BiomeCache.this.chunkManager.getBiomeGenAt(this.biomes, x << 4, z << 4, 16, 16, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public BiomeGenBase getBiomeGenAt(int x, int z) {
/* 104 */       return this.biomes[x & 0xF | (z & 0xF) << 4];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */