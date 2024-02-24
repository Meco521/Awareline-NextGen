/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ 
/*     */ public class BiomeGenMesa
/*     */   extends BiomeGenBase
/*     */ {
/*     */   private IBlockState[] field_150621_aC;
/*     */   private long field_150622_aD;
/*     */   private NoiseGeneratorPerlin field_150623_aE;
/*     */   private NoiseGeneratorPerlin field_150624_aF;
/*     */   private NoiseGeneratorPerlin field_150625_aG;
/*     */   private final boolean field_150626_aH;
/*     */   private final boolean field_150620_aI;
/*     */   
/*     */   public BiomeGenMesa(int id, boolean p_i45380_2_, boolean p_i45380_3_) {
/*  31 */     super(id);
/*  32 */     this.field_150626_aH = p_i45380_2_;
/*  33 */     this.field_150620_aI = p_i45380_3_;
/*  34 */     setDisableRain();
/*  35 */     setTemperatureRainfall(2.0F, 0.0F);
/*  36 */     this.spawnableCreatureList.clear();
/*  37 */     this.topBlock = Blocks.sand.getDefaultState().withProperty((IProperty)BlockSand.VARIANT, (Comparable)BlockSand.EnumType.RED_SAND);
/*  38 */     this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
/*  39 */     this.theBiomeDecorator.treesPerChunk = -999;
/*  40 */     this.theBiomeDecorator.deadBushPerChunk = 20;
/*  41 */     this.theBiomeDecorator.reedsPerChunk = 3;
/*  42 */     this.theBiomeDecorator.cactiPerChunk = 5;
/*  43 */     this.theBiomeDecorator.flowersPerChunk = 0;
/*  44 */     this.spawnableCreatureList.clear();
/*     */     
/*  46 */     if (p_i45380_3_)
/*     */     {
/*  48 */       this.theBiomeDecorator.treesPerChunk = 5;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  54 */     return (WorldGenAbstractTree)this.worldGeneratorTrees;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFoliageColorAtPos(BlockPos pos) {
/*  59 */     return 10387789;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/*  64 */     return 9470285;
/*     */   }
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  69 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/*  74 */     if (this.field_150621_aC == null || this.field_150622_aD != worldIn.getSeed())
/*     */     {
/*  76 */       func_150619_a(worldIn.getSeed());
/*     */     }
/*     */     
/*  79 */     if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != worldIn.getSeed()) {
/*     */       
/*  81 */       Random random = new Random(this.field_150622_aD);
/*  82 */       this.field_150623_aE = new NoiseGeneratorPerlin(random, 4);
/*  83 */       this.field_150624_aF = new NoiseGeneratorPerlin(random, 1);
/*     */     } 
/*     */     
/*  86 */     this.field_150622_aD = worldIn.getSeed();
/*  87 */     double d4 = 0.0D;
/*     */     
/*  89 */     if (this.field_150626_aH) {
/*     */       
/*  91 */       int i = (x & 0xFFFFFFF0) + (z & 0xF);
/*  92 */       int j = (z & 0xFFFFFFF0) + (x & 0xF);
/*  93 */       double d0 = Math.min(Math.abs(noiseVal), this.field_150623_aE.func_151601_a(i * 0.25D, j * 0.25D));
/*     */       
/*  95 */       if (d0 > 0.0D) {
/*     */         
/*  97 */         double d1 = 0.001953125D;
/*  98 */         double d2 = Math.abs(this.field_150624_aF.func_151601_a(i * d1, j * d1));
/*  99 */         d4 = d0 * d0 * 2.5D;
/* 100 */         double d3 = Math.ceil(d2 * 50.0D) + 14.0D;
/*     */         
/* 102 */         if (d4 > d3)
/*     */         {
/* 104 */           d4 = d3;
/*     */         }
/*     */         
/* 107 */         d4 += 64.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     int j1 = x & 0xF;
/* 112 */     int k1 = z & 0xF;
/* 113 */     int l1 = worldIn.getSeaLevel();
/* 114 */     IBlockState iblockstate = Blocks.stained_hardened_clay.getDefaultState();
/* 115 */     IBlockState iblockstate3 = this.fillerBlock;
/* 116 */     int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
/* 117 */     boolean flag = (Math.cos(noiseVal / 3.0D * Math.PI) > 0.0D);
/* 118 */     int l = -1;
/* 119 */     boolean flag1 = false;
/*     */     
/* 121 */     for (int i1 = 255; i1 >= 0; i1--) {
/*     */       
/* 123 */       if (chunkPrimerIn.getBlockState(k1, i1, j1).getBlock().getMaterial() == Material.air && i1 < (int)d4)
/*     */       {
/* 125 */         chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.stone.getDefaultState());
/*     */       }
/*     */       
/* 128 */       if (i1 <= rand.nextInt(5)) {
/*     */         
/* 130 */         chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.bedrock.getDefaultState());
/*     */       }
/*     */       else {
/*     */         
/* 134 */         IBlockState iblockstate1 = chunkPrimerIn.getBlockState(k1, i1, j1);
/*     */         
/* 136 */         if (iblockstate1.getBlock().getMaterial() == Material.air) {
/*     */           
/* 138 */           l = -1;
/*     */         }
/* 140 */         else if (iblockstate1.getBlock() == Blocks.stone) {
/*     */           
/* 142 */           if (l == -1) {
/*     */             
/* 144 */             flag1 = false;
/*     */             
/* 146 */             if (k <= 0) {
/*     */               
/* 148 */               iblockstate = null;
/* 149 */               iblockstate3 = Blocks.stone.getDefaultState();
/*     */             }
/* 151 */             else if (i1 >= l1 - 4 && i1 <= l1 + 1) {
/*     */               
/* 153 */               iblockstate = Blocks.stained_hardened_clay.getDefaultState();
/* 154 */               iblockstate3 = this.fillerBlock;
/*     */             } 
/*     */             
/* 157 */             if (i1 < l1 && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air))
/*     */             {
/* 159 */               iblockstate = Blocks.water.getDefaultState();
/*     */             }
/*     */             
/* 162 */             l = k + Math.max(0, i1 - l1);
/*     */             
/* 164 */             if (i1 < l1 - 1) {
/*     */               
/* 166 */               chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate3);
/*     */               
/* 168 */               if (iblockstate3.getBlock() == Blocks.stained_hardened_clay)
/*     */               {
/* 170 */                 chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate3.getBlock().getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE));
/*     */               }
/*     */             }
/* 173 */             else if (this.field_150620_aI && i1 > 86 + (k << 1)) {
/*     */               
/* 175 */               if (flag)
/*     */               {
/* 177 */                 chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT));
/*     */               }
/*     */               else
/*     */               {
/* 181 */                 chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.grass.getDefaultState());
/*     */               }
/*     */             
/* 184 */             } else if (i1 <= l1 + 3 + k) {
/*     */               
/* 186 */               chunkPrimerIn.setBlockState(k1, i1, j1, this.topBlock);
/* 187 */               flag1 = true;
/*     */             } else {
/*     */               IBlockState iblockstate4;
/*     */ 
/*     */ 
/*     */               
/* 193 */               if (i1 >= 64 && i1 <= 127) {
/*     */                 
/* 195 */                 if (flag)
/*     */                 {
/* 197 */                   iblockstate4 = Blocks.hardened_clay.getDefaultState();
/*     */                 }
/*     */                 else
/*     */                 {
/* 201 */                   iblockstate4 = func_180629_a(x, i1, z);
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 206 */                 iblockstate4 = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE);
/*     */               } 
/*     */               
/* 209 */               chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate4);
/*     */             }
/*     */           
/* 212 */           } else if (l > 0) {
/*     */             
/* 214 */             l--;
/*     */             
/* 216 */             if (flag1) {
/*     */               
/* 218 */               chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE));
/*     */             }
/*     */             else {
/*     */               
/* 222 */               IBlockState iblockstate2 = func_180629_a(x, i1, z);
/* 223 */               chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_150619_a(long p_150619_1_) {
/* 233 */     this.field_150621_aC = new IBlockState[64];
/* 234 */     Arrays.fill((Object[])this.field_150621_aC, Blocks.hardened_clay.getDefaultState());
/* 235 */     Random random = new Random(p_150619_1_);
/* 236 */     this.field_150625_aG = new NoiseGeneratorPerlin(random, 1);
/*     */     
/* 238 */     for (int l1 = 0; l1 < 64; l1++) {
/*     */       
/* 240 */       l1 += random.nextInt(5) + 1;
/*     */       
/* 242 */       if (l1 < 64)
/*     */       {
/* 244 */         this.field_150621_aC[l1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE);
/*     */       }
/*     */     } 
/*     */     
/* 248 */     int i2 = random.nextInt(4) + 2;
/*     */     
/* 250 */     for (int i = 0; i < i2; i++) {
/*     */       
/* 252 */       int j = random.nextInt(3) + 1;
/* 253 */       int k = random.nextInt(64);
/*     */       
/* 255 */       for (int l = 0; k + l < 64 && l < j; l++)
/*     */       {
/* 257 */         this.field_150621_aC[k + l] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.YELLOW);
/*     */       }
/*     */     } 
/*     */     
/* 261 */     int j2 = random.nextInt(4) + 2;
/*     */     
/* 263 */     for (int k2 = 0; k2 < j2; k2++) {
/*     */       
/* 265 */       int i3 = random.nextInt(3) + 2;
/* 266 */       int l3 = random.nextInt(64);
/*     */       
/* 268 */       for (int i1 = 0; l3 + i1 < 64 && i1 < i3; i1++)
/*     */       {
/* 270 */         this.field_150621_aC[l3 + i1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.BROWN);
/*     */       }
/*     */     } 
/*     */     
/* 274 */     int l2 = random.nextInt(4) + 2;
/*     */     
/* 276 */     for (int j3 = 0; j3 < l2; j3++) {
/*     */       
/* 278 */       int i4 = random.nextInt(3) + 1;
/* 279 */       int k4 = random.nextInt(64);
/*     */       
/* 281 */       for (int j1 = 0; k4 + j1 < 64 && j1 < i4; j1++)
/*     */       {
/* 283 */         this.field_150621_aC[k4 + j1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.RED);
/*     */       }
/*     */     } 
/*     */     
/* 287 */     int k3 = random.nextInt(3) + 3;
/* 288 */     int j4 = 0;
/*     */     
/* 290 */     for (int l4 = 0; l4 < k3; l4++) {
/*     */       
/* 292 */       int i5 = 1;
/* 293 */       j4 += random.nextInt(16) + 4;
/*     */       
/* 295 */       for (int k1 = 0; j4 + k1 < 64 && k1 < i5; k1++) {
/*     */         
/* 297 */         this.field_150621_aC[j4 + k1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.WHITE);
/*     */         
/* 299 */         if (j4 + k1 > 1 && random.nextBoolean())
/*     */         {
/* 301 */           this.field_150621_aC[j4 + k1 - 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.SILVER);
/*     */         }
/*     */         
/* 304 */         if (j4 + k1 < 63 && random.nextBoolean())
/*     */         {
/* 306 */           this.field_150621_aC[j4 + k1 + 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.SILVER);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState func_180629_a(int p_180629_1_, int p_180629_2_, int p_180629_3_) {
/* 314 */     int i = (int)Math.round(this.field_150625_aG.func_151601_a(p_180629_1_ / 512.0D, p_180629_1_ / 512.0D) * 2.0D);
/* 315 */     return this.field_150621_aC[(p_180629_2_ + i + 64) % 64];
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 320 */     boolean flag = (this.biomeID == BiomeGenBase.mesa.biomeID);
/* 321 */     BiomeGenMesa biomegenmesa = new BiomeGenMesa(p_180277_1_, flag, this.field_150620_aI);
/*     */     
/* 323 */     if (!flag) {
/*     */       
/* 325 */       biomegenmesa.setHeight(height_LowHills);
/* 326 */       biomegenmesa.setBiomeName(this.biomeName + " M");
/*     */     }
/*     */     else {
/*     */       
/* 330 */       biomegenmesa.setBiomeName(this.biomeName + " (Bryce)");
/*     */     } 
/*     */     
/* 333 */     biomegenmesa.func_150557_a(this.color, true);
/* 334 */     return biomegenmesa;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenMesa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */