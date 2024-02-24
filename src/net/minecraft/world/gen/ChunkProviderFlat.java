/*     */ package net.minecraft.world.gen;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*     */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*     */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*     */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*     */ import net.minecraft.world.gen.structure.MapGenStructure;
/*     */ import net.minecraft.world.gen.structure.MapGenVillage;
/*     */ 
/*     */ public class ChunkProviderFlat implements IChunkProvider {
/*     */   private final World worldObj;
/*  27 */   private final IBlockState[] cachedBlockIDs = new IBlockState[256]; private final Random random;
/*     */   private final FlatGeneratorInfo flatWorldGenInfo;
/*  29 */   private final List<MapGenStructure> structureGenerators = Lists.newArrayList();
/*     */   
/*     */   private final boolean hasDecoration;
/*     */   private final boolean hasDungeons;
/*     */   private WorldGenLakes waterLakeGenerator;
/*     */   private WorldGenLakes lavaLakeGenerator;
/*     */   
/*     */   public ChunkProviderFlat(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings) {
/*  37 */     this.worldObj = worldIn;
/*  38 */     this.random = new Random(seed);
/*  39 */     this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(flatGeneratorSettings);
/*     */     
/*  41 */     if (generateStructures) {
/*     */       
/*  43 */       Map<String, Map<String, String>> map = this.flatWorldGenInfo.getWorldFeatures();
/*     */       
/*  45 */       if (map.containsKey("village")) {
/*     */         
/*  47 */         Map<String, String> map1 = map.get("village");
/*     */         
/*  49 */         if (!map1.containsKey("size"))
/*     */         {
/*  51 */           map1.put("size", "1");
/*     */         }
/*     */         
/*  54 */         this.structureGenerators.add(new MapGenVillage(map1));
/*     */       } 
/*     */       
/*  57 */       if (map.containsKey("biome_1"))
/*     */       {
/*  59 */         this.structureGenerators.add(new MapGenScatteredFeature(map.get("biome_1")));
/*     */       }
/*     */       
/*  62 */       if (map.containsKey("mineshaft"))
/*     */       {
/*  64 */         this.structureGenerators.add(new MapGenMineshaft(map.get("mineshaft")));
/*     */       }
/*     */       
/*  67 */       if (map.containsKey("stronghold"))
/*     */       {
/*  69 */         this.structureGenerators.add(new MapGenStronghold(map.get("stronghold")));
/*     */       }
/*     */       
/*  72 */       if (map.containsKey("oceanmonument"))
/*     */       {
/*  74 */         this.structureGenerators.add(new StructureOceanMonument(map.get("oceanmonument")));
/*     */       }
/*     */     } 
/*     */     
/*  78 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake"))
/*     */     {
/*  80 */       this.waterLakeGenerator = new WorldGenLakes((Block)Blocks.water);
/*     */     }
/*     */     
/*  83 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake"))
/*     */     {
/*  85 */       this.lavaLakeGenerator = new WorldGenLakes((Block)Blocks.lava);
/*     */     }
/*     */     
/*  88 */     this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
/*  89 */     int j = 0;
/*  90 */     int k = 0;
/*  91 */     boolean flag = true;
/*     */     
/*  93 */     for (FlatLayerInfo flatlayerinfo : this.flatWorldGenInfo.getFlatLayers()) {
/*     */       
/*  95 */       for (int i = flatlayerinfo.getMinY(); i < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); i++) {
/*     */         
/*  97 */         IBlockState iblockstate = flatlayerinfo.getLayerMaterial();
/*     */         
/*  99 */         if (iblockstate.getBlock() != Blocks.air) {
/*     */           
/* 101 */           flag = false;
/* 102 */           this.cachedBlockIDs[i] = iblockstate;
/*     */         } 
/*     */       } 
/*     */       
/* 106 */       if (flatlayerinfo.getLayerMaterial().getBlock() == Blocks.air) {
/*     */         
/* 108 */         k += flatlayerinfo.getLayerCount();
/*     */         
/*     */         continue;
/*     */       } 
/* 112 */       j += flatlayerinfo.getLayerCount() + k;
/* 113 */       k = 0;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     worldIn.setSeaLevel(j);
/* 118 */     this.hasDecoration = flag ? false : this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 127 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*     */     
/* 129 */     for (int i = 0; i < this.cachedBlockIDs.length; i++) {
/*     */       
/* 131 */       IBlockState iblockstate = this.cachedBlockIDs[i];
/*     */       
/* 133 */       if (iblockstate != null)
/*     */       {
/* 135 */         for (int j = 0; j < 16; j++) {
/*     */           
/* 137 */           for (int k = 0; k < 16; k++)
/*     */           {
/* 139 */             chunkprimer.setBlockState(j, i, k, iblockstate);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 145 */     for (MapGenBase mapgenbase : this.structureGenerators)
/*     */     {
/* 147 */       mapgenbase.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 150 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 151 */     BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, x << 4, z << 4, 16, 16);
/* 152 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 154 */     for (int l = 0; l < abyte.length; l++)
/*     */     {
/* 156 */       abyte[l] = (byte)(abiomegenbase[l]).biomeID;
/*     */     }
/*     */     
/* 159 */     chunk.generateSkylightMap();
/* 160 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 176 */     int i = x << 4;
/* 177 */     int j = z << 4;
/* 178 */     BlockPos blockpos = new BlockPos(i, 0, j);
/* 179 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(i + 16, 0, j + 16));
/* 180 */     boolean flag = false;
/* 181 */     this.random.setSeed(this.worldObj.getSeed());
/* 182 */     long k = (this.random.nextLong() / 2L << 1L) + 1L;
/* 183 */     long l = (this.random.nextLong() / 2L << 1L) + 1L;
/* 184 */     this.random.setSeed(x * k + z * l ^ this.worldObj.getSeed());
/* 185 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*     */     
/* 187 */     for (MapGenStructure mapgenstructure : this.structureGenerators) {
/*     */       
/* 189 */       boolean flag1 = mapgenstructure.generateStructure(this.worldObj, this.random, chunkcoordintpair);
/*     */       
/* 191 */       if (mapgenstructure instanceof MapGenVillage)
/*     */       {
/* 193 */         flag |= flag1;
/*     */       }
/*     */     } 
/*     */     
/* 197 */     if (this.waterLakeGenerator != null && !flag && this.random.nextInt(4) == 0)
/*     */     {
/* 199 */       this.waterLakeGenerator.generate(this.worldObj, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
/*     */     }
/*     */     
/* 202 */     if (this.lavaLakeGenerator != null && !flag && this.random.nextInt(8) == 0) {
/*     */       
/* 204 */       BlockPos blockpos1 = blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(this.random.nextInt(248) + 8), this.random.nextInt(16) + 8);
/*     */       
/* 206 */       if (blockpos1.getY() < this.worldObj.getSeaLevel() || this.random.nextInt(10) == 0)
/*     */       {
/* 208 */         this.lavaLakeGenerator.generate(this.worldObj, this.random, blockpos1);
/*     */       }
/*     */     } 
/*     */     
/* 212 */     if (this.hasDungeons)
/*     */     {
/* 214 */       for (int i1 = 0; i1 < 8; i1++)
/*     */       {
/* 216 */         (new WorldGenDungeons()).generate(this.worldObj, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
/*     */       }
/*     */     }
/*     */     
/* 220 */     if (this.hasDecoration)
/*     */     {
/* 222 */       biomegenbase.decorate(this.worldObj, this.random, blockpos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 237 */     return true;
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
/* 253 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 261 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 269 */     return "FlatLevelSource";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 274 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
/* 275 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 280 */     if ("Stronghold".equals(structureName))
/*     */     {
/* 282 */       for (MapGenStructure mapgenstructure : this.structureGenerators) {
/*     */         
/* 284 */         if (mapgenstructure instanceof MapGenStronghold)
/*     */         {
/* 286 */           return mapgenstructure.getClosestStrongholdPos(worldIn, position);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 296 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 301 */     for (MapGenStructure mapgenstructure : this.structureGenerators)
/*     */     {
/* 303 */       mapgenstructure.generate(this, this.worldObj, x, z, (ChunkPrimer)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 309 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\ChunkProviderFlat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */