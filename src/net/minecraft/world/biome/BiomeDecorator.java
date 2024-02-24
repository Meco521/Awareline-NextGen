/*     */ package net.minecraft.world.biome;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.ChunkProviderSettings;
/*     */ import net.minecraft.world.gen.GeneratorBushFeature;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenCactus;
/*     */ import net.minecraft.world.gen.feature.WorldGenClay;
/*     */ import net.minecraft.world.gen.feature.WorldGenDeadBush;
/*     */ import net.minecraft.world.gen.feature.WorldGenFlowers;
/*     */ import net.minecraft.world.gen.feature.WorldGenLiquids;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraft.world.gen.feature.WorldGenPumpkin;
/*     */ import net.minecraft.world.gen.feature.WorldGenSand;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BiomeDecorator {
/*     */   protected World currentWorld;
/*  26 */   protected WorldGenerator clayGen = (WorldGenerator)new WorldGenClay(4); protected Random randomGenerator;
/*     */   protected BlockPos field_180294_c;
/*     */   protected ChunkProviderSettings chunkProviderSettings;
/*  29 */   protected WorldGenerator sandGen = (WorldGenerator)new WorldGenSand((Block)Blocks.sand, 7);
/*     */ 
/*     */   
/*  32 */   protected WorldGenerator gravelAsSandGen = (WorldGenerator)new WorldGenSand(Blocks.gravel, 6);
/*     */   
/*     */   protected WorldGenerator dirtGen;
/*     */   
/*     */   protected WorldGenerator gravelGen;
/*     */   
/*     */   protected WorldGenerator graniteGen;
/*     */   
/*     */   protected WorldGenerator dioriteGen;
/*     */   
/*     */   protected WorldGenerator andesiteGen;
/*     */   
/*     */   protected WorldGenerator coalGen;
/*     */   protected WorldGenerator ironGen;
/*     */   protected WorldGenerator goldGen;
/*     */   protected WorldGenerator redstoneGen;
/*     */   protected WorldGenerator diamondGen;
/*     */   protected WorldGenerator lapisGen;
/*  50 */   protected WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
/*     */ 
/*     */   
/*  53 */   protected WorldGenerator mushroomBrownGen = (WorldGenerator)new GeneratorBushFeature(Blocks.brown_mushroom);
/*     */ 
/*     */   
/*  56 */   protected WorldGenerator mushroomRedGen = (WorldGenerator)new GeneratorBushFeature(Blocks.red_mushroom);
/*     */ 
/*     */   
/*  59 */   protected WorldGenerator bigMushroomGen = (WorldGenerator)new WorldGenBigMushroom();
/*     */ 
/*     */   
/*  62 */   protected WorldGenerator reedGen = (WorldGenerator)new WorldGenReed();
/*     */ 
/*     */   
/*  65 */   protected WorldGenerator cactusGen = (WorldGenerator)new WorldGenCactus();
/*     */ 
/*     */   
/*  68 */   protected WorldGenerator waterlilyGen = (WorldGenerator)new WorldGenWaterlily();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int waterlilyPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int treesPerChunk;
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected int flowersPerChunk = 2;
/*     */ 
/*     */   
/*  85 */   protected int grassPerChunk = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int deadBushPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int mushroomsPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int reedsPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int cactiPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   protected int sandPerChunk = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   protected int sandPerChunk2 = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   protected int clayPerChunk = 1;
/*     */ 
/*     */   
/*     */   protected int bigMushroomsPerChunk;
/*     */ 
/*     */   
/*     */   public boolean generateLakes = true;
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random random, BiomeGenBase biome, BlockPos p_180292_4_) {
/* 132 */     if (this.currentWorld != null)
/*     */     {
/* 134 */       throw new RuntimeException("Already decorating");
/*     */     }
/*     */ 
/*     */     
/* 138 */     this.currentWorld = worldIn;
/* 139 */     String s = worldIn.getWorldInfo().getGeneratorOptions();
/*     */     
/* 141 */     if (s != null) {
/*     */       
/* 143 */       this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
/*     */     }
/*     */     else {
/*     */       
/* 147 */       this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory("").func_177864_b();
/*     */     } 
/*     */     
/* 150 */     this.randomGenerator = random;
/* 151 */     this.field_180294_c = p_180292_4_;
/* 152 */     this.dirtGen = (WorldGenerator)new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
/* 153 */     this.gravelGen = (WorldGenerator)new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
/* 154 */     this.graniteGen = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
/* 155 */     this.dioriteGen = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
/* 156 */     this.andesiteGen = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
/* 157 */     this.coalGen = (WorldGenerator)new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
/* 158 */     this.ironGen = (WorldGenerator)new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
/* 159 */     this.goldGen = (WorldGenerator)new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
/* 160 */     this.redstoneGen = (WorldGenerator)new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
/* 161 */     this.diamondGen = (WorldGenerator)new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
/* 162 */     this.lapisGen = (WorldGenerator)new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
/* 163 */     genDecorations(biome);
/* 164 */     this.currentWorld = null;
/* 165 */     this.randomGenerator = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genDecorations(BiomeGenBase biomeGenBaseIn) {
/* 171 */     generateOres();
/*     */     
/* 173 */     for (int i = 0; i < this.sandPerChunk2; i++) {
/*     */       
/* 175 */       int j = this.randomGenerator.nextInt(16) + 8;
/* 176 */       int k = this.randomGenerator.nextInt(16) + 8;
/* 177 */       this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(j, 0, k)));
/*     */     } 
/*     */     
/* 180 */     for (int i1 = 0; i1 < this.clayPerChunk; i1++) {
/*     */       
/* 182 */       int l1 = this.randomGenerator.nextInt(16) + 8;
/* 183 */       int i6 = this.randomGenerator.nextInt(16) + 8;
/* 184 */       this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(l1, 0, i6)));
/*     */     } 
/*     */     
/* 187 */     for (int j1 = 0; j1 < this.sandPerChunk; j1++) {
/*     */       
/* 189 */       int i2 = this.randomGenerator.nextInt(16) + 8;
/* 190 */       int j6 = this.randomGenerator.nextInt(16) + 8;
/* 191 */       this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i2, 0, j6)));
/*     */     } 
/*     */     
/* 194 */     int k1 = this.treesPerChunk;
/*     */     
/* 196 */     if (this.randomGenerator.nextInt(10) == 0)
/*     */     {
/* 198 */       k1++;
/*     */     }
/*     */     
/* 201 */     for (int j2 = 0; j2 < k1; j2++) {
/*     */       
/* 203 */       int k6 = this.randomGenerator.nextInt(16) + 8;
/* 204 */       int l = this.randomGenerator.nextInt(16) + 8;
/* 205 */       WorldGenAbstractTree worldgenabstracttree = biomeGenBaseIn.genBigTreeChance(this.randomGenerator);
/* 206 */       worldgenabstracttree.func_175904_e();
/* 207 */       BlockPos blockpos = this.currentWorld.getHeight(this.field_180294_c.add(k6, 0, l));
/*     */       
/* 209 */       if (worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, blockpos))
/*     */       {
/* 211 */         worldgenabstracttree.func_180711_a(this.currentWorld, this.randomGenerator, blockpos);
/*     */       }
/*     */     } 
/*     */     
/* 215 */     for (int k2 = 0; k2 < this.bigMushroomsPerChunk; k2++) {
/*     */       
/* 217 */       int l6 = this.randomGenerator.nextInt(16) + 8;
/* 218 */       int k10 = this.randomGenerator.nextInt(16) + 8;
/* 219 */       this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.add(l6, 0, k10)));
/*     */     } 
/*     */     
/* 222 */     for (int l2 = 0; l2 < this.flowersPerChunk; l2++) {
/*     */       
/* 224 */       int i7 = this.randomGenerator.nextInt(16) + 8;
/* 225 */       int l10 = this.randomGenerator.nextInt(16) + 8;
/* 226 */       int j14 = this.currentWorld.getHeight(this.field_180294_c.add(i7, 0, l10)).getY() + 32;
/*     */       
/* 228 */       if (j14 > 0) {
/*     */         
/* 230 */         int k17 = this.randomGenerator.nextInt(j14);
/* 231 */         BlockPos blockpos1 = this.field_180294_c.add(i7, k17, l10);
/* 232 */         BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeGenBaseIn.pickRandomFlower(this.randomGenerator, blockpos1);
/* 233 */         BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
/*     */         
/* 235 */         if (blockflower.getMaterial() != Material.air) {
/*     */           
/* 237 */           this.yellowFlowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
/* 238 */           this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, blockpos1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     for (int i3 = 0; i3 < this.grassPerChunk; i3++) {
/*     */       
/* 245 */       int j7 = this.randomGenerator.nextInt(16) + 8;
/* 246 */       int i11 = this.randomGenerator.nextInt(16) + 8;
/* 247 */       int k14 = this.currentWorld.getHeight(this.field_180294_c.add(j7, 0, i11)).getY() << 1;
/*     */       
/* 249 */       if (k14 > 0) {
/*     */         
/* 251 */         int l17 = this.randomGenerator.nextInt(k14);
/* 252 */         biomeGenBaseIn.getRandomWorldGenForGrass(this.randomGenerator).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j7, l17, i11));
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     for (int j3 = 0; j3 < this.deadBushPerChunk; j3++) {
/*     */       
/* 258 */       int k7 = this.randomGenerator.nextInt(16) + 8;
/* 259 */       int j11 = this.randomGenerator.nextInt(16) + 8;
/* 260 */       int l14 = this.currentWorld.getHeight(this.field_180294_c.add(k7, 0, j11)).getY() << 1;
/*     */       
/* 262 */       if (l14 > 0) {
/*     */         
/* 264 */         int i18 = this.randomGenerator.nextInt(l14);
/* 265 */         (new WorldGenDeadBush()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(k7, i18, j11));
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     for (int k3 = 0; k3 < this.waterlilyPerChunk; k3++) {
/*     */       
/* 271 */       int l7 = this.randomGenerator.nextInt(16) + 8;
/* 272 */       int k11 = this.randomGenerator.nextInt(16) + 8;
/* 273 */       int i15 = this.currentWorld.getHeight(this.field_180294_c.add(l7, 0, k11)).getY() << 1;
/*     */       
/* 275 */       if (i15 > 0) {
/*     */         
/* 277 */         int j18 = this.randomGenerator.nextInt(i15);
/*     */         
/*     */         BlockPos blockpos4;
/*     */         
/* 281 */         for (blockpos4 = this.field_180294_c.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7) {
/*     */           
/* 283 */           BlockPos blockpos7 = blockpos4.down();
/*     */           
/* 285 */           if (!this.currentWorld.isAirBlock(blockpos7)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 291 */         this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, blockpos4);
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     for (int l3 = 0; l3 < this.mushroomsPerChunk; l3++) {
/*     */       
/* 297 */       if (this.randomGenerator.nextInt(4) == 0) {
/*     */         
/* 299 */         int i8 = this.randomGenerator.nextInt(16) + 8;
/* 300 */         int l11 = this.randomGenerator.nextInt(16) + 8;
/* 301 */         BlockPos blockpos2 = this.currentWorld.getHeight(this.field_180294_c.add(i8, 0, l11));
/* 302 */         this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, blockpos2);
/*     */       } 
/*     */       
/* 305 */       if (this.randomGenerator.nextInt(8) == 0) {
/*     */         
/* 307 */         int j8 = this.randomGenerator.nextInt(16) + 8;
/* 308 */         int i12 = this.randomGenerator.nextInt(16) + 8;
/* 309 */         int j15 = this.currentWorld.getHeight(this.field_180294_c.add(j8, 0, i12)).getY() << 1;
/*     */         
/* 311 */         if (j15 > 0) {
/*     */           
/* 313 */           int k18 = this.randomGenerator.nextInt(j15);
/* 314 */           BlockPos blockpos5 = this.field_180294_c.add(j8, k18, i12);
/* 315 */           this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, blockpos5);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     if (this.randomGenerator.nextInt(4) == 0) {
/*     */       
/* 322 */       int i4 = this.randomGenerator.nextInt(16) + 8;
/* 323 */       int k8 = this.randomGenerator.nextInt(16) + 8;
/* 324 */       int j12 = this.currentWorld.getHeight(this.field_180294_c.add(i4, 0, k8)).getY() << 1;
/*     */       
/* 326 */       if (j12 > 0) {
/*     */         
/* 328 */         int k15 = this.randomGenerator.nextInt(j12);
/* 329 */         this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i4, k15, k8));
/*     */       } 
/*     */     } 
/*     */     
/* 333 */     if (this.randomGenerator.nextInt(8) == 0) {
/*     */       
/* 335 */       int j4 = this.randomGenerator.nextInt(16) + 8;
/* 336 */       int l8 = this.randomGenerator.nextInt(16) + 8;
/* 337 */       int k12 = this.currentWorld.getHeight(this.field_180294_c.add(j4, 0, l8)).getY() << 1;
/*     */       
/* 339 */       if (k12 > 0) {
/*     */         
/* 341 */         int l15 = this.randomGenerator.nextInt(k12);
/* 342 */         this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j4, l15, l8));
/*     */       } 
/*     */     } 
/*     */     
/* 346 */     for (int k4 = 0; k4 < this.reedsPerChunk; k4++) {
/*     */       
/* 348 */       int i9 = this.randomGenerator.nextInt(16) + 8;
/* 349 */       int l12 = this.randomGenerator.nextInt(16) + 8;
/* 350 */       int i16 = this.currentWorld.getHeight(this.field_180294_c.add(i9, 0, l12)).getY() << 1;
/*     */       
/* 352 */       if (i16 > 0) {
/*     */         
/* 354 */         int l18 = this.randomGenerator.nextInt(i16);
/* 355 */         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i9, l18, l12));
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     for (int l4 = 0; l4 < 10; l4++) {
/*     */       
/* 361 */       int j9 = this.randomGenerator.nextInt(16) + 8;
/* 362 */       int i13 = this.randomGenerator.nextInt(16) + 8;
/* 363 */       int j16 = this.currentWorld.getHeight(this.field_180294_c.add(j9, 0, i13)).getY() << 1;
/*     */       
/* 365 */       if (j16 > 0) {
/*     */         
/* 367 */         int i19 = this.randomGenerator.nextInt(j16);
/* 368 */         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j9, i19, i13));
/*     */       } 
/*     */     } 
/*     */     
/* 372 */     if (this.randomGenerator.nextInt(32) == 0) {
/*     */       
/* 374 */       int i5 = this.randomGenerator.nextInt(16) + 8;
/* 375 */       int k9 = this.randomGenerator.nextInt(16) + 8;
/* 376 */       int j13 = this.currentWorld.getHeight(this.field_180294_c.add(i5, 0, k9)).getY() << 1;
/*     */       
/* 378 */       if (j13 > 0) {
/*     */         
/* 380 */         int k16 = this.randomGenerator.nextInt(j13);
/* 381 */         (new WorldGenPumpkin()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i5, k16, k9));
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     for (int j5 = 0; j5 < this.cactiPerChunk; j5++) {
/*     */       
/* 387 */       int l9 = this.randomGenerator.nextInt(16) + 8;
/* 388 */       int k13 = this.randomGenerator.nextInt(16) + 8;
/* 389 */       int l16 = this.currentWorld.getHeight(this.field_180294_c.add(l9, 0, k13)).getY() << 1;
/*     */       
/* 391 */       if (l16 > 0) {
/*     */         
/* 393 */         int j19 = this.randomGenerator.nextInt(l16);
/* 394 */         this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(l9, j19, k13));
/*     */       } 
/*     */     } 
/*     */     
/* 398 */     if (this.generateLakes) {
/*     */       
/* 400 */       for (int k5 = 0; k5 < 50; k5++) {
/*     */         
/* 402 */         int i10 = this.randomGenerator.nextInt(16) + 8;
/* 403 */         int l13 = this.randomGenerator.nextInt(16) + 8;
/* 404 */         int i17 = this.randomGenerator.nextInt(248) + 8;
/*     */         
/* 406 */         if (i17 > 0) {
/*     */           
/* 408 */           int k19 = this.randomGenerator.nextInt(i17);
/* 409 */           BlockPos blockpos6 = this.field_180294_c.add(i10, k19, l13);
/* 410 */           (new WorldGenLiquids((Block)Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, blockpos6);
/*     */         } 
/*     */       } 
/*     */       
/* 414 */       for (int l5 = 0; l5 < 20; l5++) {
/*     */         
/* 416 */         int j10 = this.randomGenerator.nextInt(16) + 8;
/* 417 */         int i14 = this.randomGenerator.nextInt(16) + 8;
/* 418 */         int j17 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
/* 419 */         BlockPos blockpos3 = this.field_180294_c.add(j10, j17, i14);
/* 420 */         (new WorldGenLiquids((Block)Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, blockpos3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genStandardOre1(int blockCount, WorldGenerator generator, int minHeight, int maxHeight) {
/* 430 */     if (maxHeight < minHeight) {
/*     */       
/* 432 */       int i = minHeight;
/* 433 */       minHeight = maxHeight;
/* 434 */       maxHeight = i;
/*     */     }
/* 436 */     else if (maxHeight == minHeight) {
/*     */       
/* 438 */       if (minHeight < 255) {
/*     */         
/* 440 */         maxHeight++;
/*     */       }
/*     */       else {
/*     */         
/* 444 */         minHeight--;
/*     */       } 
/*     */     } 
/*     */     
/* 448 */     for (int j = 0; j < blockCount; j++) {
/*     */       
/* 450 */       BlockPos blockpos = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(maxHeight - minHeight) + minHeight, this.randomGenerator.nextInt(16));
/* 451 */       generator.generate(this.currentWorld, this.randomGenerator, blockpos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genStandardOre2(int blockCount, WorldGenerator generator, int centerHeight, int spread) {
/* 460 */     for (int i = 0; i < blockCount; i++) {
/*     */       
/* 462 */       BlockPos blockpos = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(spread) + this.randomGenerator.nextInt(spread) + centerHeight - spread, this.randomGenerator.nextInt(16));
/* 463 */       generator.generate(this.currentWorld, this.randomGenerator, blockpos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateOres() {
/* 472 */     genStandardOre1(this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
/* 473 */     genStandardOre1(this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
/* 474 */     genStandardOre1(this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
/* 475 */     genStandardOre1(this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
/* 476 */     genStandardOre1(this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
/* 477 */     genStandardOre1(this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
/* 478 */     genStandardOre1(this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
/* 479 */     genStandardOre1(this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
/* 480 */     genStandardOre1(this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
/* 481 */     genStandardOre1(this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
/* 482 */     genStandardOre2(this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */