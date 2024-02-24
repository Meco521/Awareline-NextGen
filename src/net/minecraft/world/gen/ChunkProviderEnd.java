/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderEnd
/*     */   implements IChunkProvider
/*     */ {
/*     */   private final Random endRNG;
/*     */   private final NoiseGeneratorOctaves noiseGen1;
/*     */   private final NoiseGeneratorOctaves noiseGen2;
/*     */   private final NoiseGeneratorOctaves noiseGen3;
/*     */   public NoiseGeneratorOctaves noiseGen4;
/*     */   public NoiseGeneratorOctaves noiseGen5;
/*     */   private final World endWorld;
/*     */   private double[] densities;
/*     */   private BiomeGenBase[] biomesForGeneration;
/*     */   double[] noiseData1;
/*     */   double[] noiseData2;
/*     */   double[] noiseData3;
/*     */   double[] noiseData4;
/*     */   double[] noiseData5;
/*     */   
/*     */   public ChunkProviderEnd(World worldIn, long seed) {
/*  41 */     this.endWorld = worldIn;
/*  42 */     this.endRNG = new Random(seed);
/*  43 */     this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*  44 */     this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*  45 */     this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
/*  46 */     this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
/*  47 */     this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180520_a(int p_180520_1_, int p_180520_2_, ChunkPrimer p_180520_3_) {
/*  52 */     int i = 2;
/*  53 */     int j = i + 1;
/*  54 */     int k = 33;
/*  55 */     int l = i + 1;
/*  56 */     this.densities = initializeNoiseField(this.densities, p_180520_1_ * i, 0, p_180520_2_ * i, j, k, l);
/*     */     
/*  58 */     for (int i1 = 0; i1 < i; i1++) {
/*     */       
/*  60 */       for (int j1 = 0; j1 < i; j1++) {
/*     */         
/*  62 */         for (int k1 = 0; k1 < 32; k1++) {
/*     */           
/*  64 */           double d0 = 0.25D;
/*  65 */           double d1 = this.densities[(i1 * l + j1) * k + k1];
/*  66 */           double d2 = this.densities[(i1 * l + j1 + 1) * k + k1];
/*  67 */           double d3 = this.densities[((i1 + 1) * l + j1) * k + k1];
/*  68 */           double d4 = this.densities[((i1 + 1) * l + j1 + 1) * k + k1];
/*  69 */           double d5 = (this.densities[(i1 * l + j1) * k + k1 + 1] - d1) * d0;
/*  70 */           double d6 = (this.densities[(i1 * l + j1 + 1) * k + k1 + 1] - d2) * d0;
/*  71 */           double d7 = (this.densities[((i1 + 1) * l + j1) * k + k1 + 1] - d3) * d0;
/*  72 */           double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;
/*     */           
/*  74 */           for (int l1 = 0; l1 < 4; l1++) {
/*     */             
/*  76 */             double d9 = 0.125D;
/*  77 */             double d10 = d1;
/*  78 */             double d11 = d2;
/*  79 */             double d12 = (d3 - d1) * d9;
/*  80 */             double d13 = (d4 - d2) * d9;
/*     */             
/*  82 */             for (int i2 = 0; i2 < 8; i2++) {
/*     */               
/*  84 */               double d14 = 0.125D;
/*  85 */               double d15 = d10;
/*  86 */               double d16 = (d11 - d10) * d14;
/*     */               
/*  88 */               for (int j2 = 0; j2 < 8; j2++) {
/*     */                 
/*  90 */                 IBlockState iblockstate = null;
/*     */                 
/*  92 */                 if (d15 > 0.0D)
/*     */                 {
/*  94 */                   iblockstate = Blocks.end_stone.getDefaultState();
/*     */                 }
/*     */                 
/*  97 */                 int k2 = i2 + (i1 << 3);
/*  98 */                 int l2 = l1 + (k1 << 2);
/*  99 */                 int i3 = j2 + (j1 << 3);
/* 100 */                 p_180520_3_.setBlockState(k2, l2, i3, iblockstate);
/* 101 */                 d15 += d16;
/*     */               } 
/*     */               
/* 104 */               d10 += d12;
/* 105 */               d11 += d13;
/*     */             } 
/*     */             
/* 108 */             d1 += d5;
/* 109 */             d2 += d6;
/* 110 */             d3 += d7;
/* 111 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180519_a(ChunkPrimer p_180519_1_) {
/* 120 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 122 */       for (int j = 0; j < 16; j++) {
/*     */         
/* 124 */         int k = 1;
/* 125 */         int l = -1;
/* 126 */         IBlockState iblockstate = Blocks.end_stone.getDefaultState();
/* 127 */         IBlockState iblockstate1 = Blocks.end_stone.getDefaultState();
/*     */         
/* 129 */         for (int i1 = 127; i1 >= 0; i1--) {
/*     */           
/* 131 */           IBlockState iblockstate2 = p_180519_1_.getBlockState(i, i1, j);
/*     */           
/* 133 */           if (iblockstate2.getBlock().getMaterial() == Material.air) {
/*     */             
/* 135 */             l = -1;
/*     */           }
/* 137 */           else if (iblockstate2.getBlock() == Blocks.stone) {
/*     */             
/* 139 */             if (l == -1) {
/*     */               
/* 141 */               if (k <= 0) {
/*     */                 
/* 143 */                 iblockstate = Blocks.air.getDefaultState();
/* 144 */                 iblockstate1 = Blocks.end_stone.getDefaultState();
/*     */               } 
/*     */               
/* 147 */               l = k;
/*     */               
/* 149 */               if (i1 >= 0)
/*     */               {
/* 151 */                 p_180519_1_.setBlockState(i, i1, j, iblockstate);
/*     */               }
/*     */               else
/*     */               {
/* 155 */                 p_180519_1_.setBlockState(i, i1, j, iblockstate1);
/*     */               }
/*     */             
/* 158 */             } else if (l > 0) {
/*     */               
/* 160 */               l--;
/* 161 */               p_180519_1_.setBlockState(i, i1, j, iblockstate1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 175 */     this.endRNG.setSeed(x * 341873128712L + z * 132897987541L);
/* 176 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 177 */     this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x << 4, z << 4, 16, 16);
/* 178 */     func_180520_a(x, z, chunkprimer);
/* 179 */     func_180519_a(chunkprimer);
/* 180 */     Chunk chunk = new Chunk(this.endWorld, chunkprimer, x, z);
/* 181 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 183 */     for (int i = 0; i < abyte.length; i++)
/*     */     {
/* 185 */       abyte[i] = (byte)(this.biomesForGeneration[i]).biomeID;
/*     */     }
/*     */     
/* 188 */     chunk.generateSkylightMap();
/* 189 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] initializeNoiseField(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_) {
/* 198 */     if (p_73187_1_ == null)
/*     */     {
/* 200 */       p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
/*     */     }
/*     */     
/* 203 */     double d0 = 684.412D;
/* 204 */     double d1 = 684.412D;
/* 205 */     this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121D, 1.121D, 0.5D);
/* 206 */     this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0D, 200.0D, 0.5D);
/* 207 */     d0 *= 2.0D;
/* 208 */     this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
/* 209 */     this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
/* 210 */     this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
/* 211 */     int i = 0;
/*     */     
/* 213 */     for (int j = 0; j < p_73187_5_; j++) {
/*     */       
/* 215 */       for (int k = 0; k < p_73187_7_; k++) {
/*     */         
/* 217 */         float f = (j + p_73187_2_);
/* 218 */         float f1 = (k + p_73187_4_);
/* 219 */         float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;
/*     */         
/* 221 */         if (f2 > 80.0F)
/*     */         {
/* 223 */           f2 = 80.0F;
/*     */         }
/*     */         
/* 226 */         if (f2 < -100.0F)
/*     */         {
/* 228 */           f2 = -100.0F;
/*     */         }
/*     */         
/* 231 */         for (int l = 0; l < p_73187_6_; l++) {
/*     */           
/* 233 */           double d2 = 0.0D;
/* 234 */           double d3 = this.noiseData2[i] / 512.0D;
/* 235 */           double d4 = this.noiseData3[i] / 512.0D;
/* 236 */           double d5 = (this.noiseData1[i] / 10.0D + 1.0D) / 2.0D;
/*     */           
/* 238 */           if (d5 < 0.0D) {
/*     */             
/* 240 */             d2 = d3;
/*     */           }
/* 242 */           else if (d5 > 1.0D) {
/*     */             
/* 244 */             d2 = d4;
/*     */           }
/*     */           else {
/*     */             
/* 248 */             d2 = d3 + (d4 - d3) * d5;
/*     */           } 
/*     */           
/* 251 */           d2 -= 8.0D;
/* 252 */           d2 += f2;
/* 253 */           int i1 = 2;
/*     */           
/* 255 */           if (l > p_73187_6_ / 2 - i1) {
/*     */             
/* 257 */             double d6 = ((l - p_73187_6_ / 2 - i1) / 64.0F);
/* 258 */             d6 = MathHelper.clamp_double(d6, 0.0D, 1.0D);
/* 259 */             d2 = d2 * (1.0D - d6) + -3000.0D * d6;
/*     */           } 
/*     */           
/* 262 */           i1 = 8;
/*     */           
/* 264 */           if (l < i1) {
/*     */             
/* 266 */             double d7 = ((i1 - l) / (i1 - 1.0F));
/* 267 */             d2 = d2 * (1.0D - d7) + -30.0D * d7;
/*     */           } 
/*     */           
/* 270 */           p_73187_1_[i] = d2;
/* 271 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 276 */     return p_73187_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 284 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 292 */     BlockFalling.fallInstantly = true;
/* 293 */     BlockPos blockpos = new BlockPos(x << 4, 0, z << 4);
/* 294 */     this.endWorld.getBiomeGenForCoords(blockpos.add(16, 0, 16)).decorate(this.endWorld, this.endWorld.rand, blockpos);
/* 295 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 300 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 309 */     return true;
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
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 333 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 341 */     return "RandomLevelSource";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 346 */     return this.endWorld.getBiomeGenForCoords(pos).getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 351 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 356 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 365 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\ChunkProviderEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */