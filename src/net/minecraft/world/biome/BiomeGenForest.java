/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenForest;
/*     */ 
/*     */ public class BiomeGenForest
/*     */   extends BiomeGenBase
/*     */ {
/*     */   private final int field_150632_aF;
/*  19 */   protected static final WorldGenForest field_150629_aC = new WorldGenForest(false, true);
/*  20 */   protected static final WorldGenForest field_150630_aD = new WorldGenForest(false, false);
/*  21 */   protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);
/*     */ 
/*     */   
/*     */   public BiomeGenForest(int id, int p_i45377_2_) {
/*  25 */     super(id);
/*  26 */     this.field_150632_aF = p_i45377_2_;
/*  27 */     this.theBiomeDecorator.treesPerChunk = 10;
/*  28 */     this.theBiomeDecorator.grassPerChunk = 2;
/*     */     
/*  30 */     if (this.field_150632_aF == 1) {
/*     */       
/*  32 */       this.theBiomeDecorator.treesPerChunk = 6;
/*  33 */       this.theBiomeDecorator.flowersPerChunk = 100;
/*  34 */       this.theBiomeDecorator.grassPerChunk = 1;
/*     */     } 
/*     */     
/*  37 */     setFillerBlockMetadata(5159473);
/*  38 */     setTemperatureRainfall(0.7F, 0.8F);
/*     */     
/*  40 */     if (this.field_150632_aF == 2) {
/*     */       
/*  42 */       this.field_150609_ah = 353825;
/*  43 */       this.color = 3175492;
/*  44 */       setTemperatureRainfall(0.6F, 0.6F);
/*     */     } 
/*     */     
/*  47 */     if (this.field_150632_aF == 0)
/*     */     {
/*  49 */       this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 5, 4, 4));
/*     */     }
/*     */     
/*  52 */     if (this.field_150632_aF == 3)
/*     */     {
/*  54 */       this.theBiomeDecorator.treesPerChunk = -999;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase func_150557_a(int colorIn, boolean p_150557_2_) {
/*  60 */     if (this.field_150632_aF == 2) {
/*     */       
/*  62 */       this.field_150609_ah = 353825;
/*  63 */       this.color = colorIn;
/*     */       
/*  65 */       if (p_150557_2_)
/*     */       {
/*  67 */         this.field_150609_ah = (this.field_150609_ah & 0xFEFEFE) >> 1;
/*     */       }
/*     */       
/*  70 */       return this;
/*     */     } 
/*     */ 
/*     */     
/*  74 */     return super.func_150557_a(colorIn, p_150557_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  80 */     return (this.field_150632_aF == 3 && rand.nextInt(3) > 0) ? (WorldGenAbstractTree)field_150631_aE : ((this.field_150632_aF != 2 && rand.nextInt(5) != 0) ? (WorldGenAbstractTree)this.worldGeneratorTrees : (WorldGenAbstractTree)field_150630_aD);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/*  85 */     if (this.field_150632_aF == 1) {
/*     */       
/*  87 */       double d0 = MathHelper.clamp_double((1.0D + GRASS_COLOR_NOISE.func_151601_a(pos.getX() / 48.0D, pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
/*  88 */       BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (BlockFlower.EnumFlowerType.values()).length)];
/*  89 */       return (blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID) ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
/*     */     } 
/*     */ 
/*     */     
/*  93 */     return super.pickRandomFlower(rand, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  99 */     if (this.field_150632_aF == 3)
/*     */     {
/* 101 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 103 */         for (int j = 0; j < 4; j++) {
/*     */           
/* 105 */           int k = (i << 2) + 1 + 8 + rand.nextInt(3);
/* 106 */           int l = (j << 2) + 1 + 8 + rand.nextInt(3);
/* 107 */           BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
/*     */           
/* 109 */           if (rand.nextInt(20) == 0) {
/*     */             
/* 111 */             WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
/* 112 */             worldgenbigmushroom.generate(worldIn, rand, blockpos);
/*     */           }
/*     */           else {
/*     */             
/* 116 */             WorldGenAbstractTree worldgenabstracttree = genBigTreeChance(rand);
/* 117 */             worldgenabstracttree.func_175904_e();
/*     */             
/* 119 */             if (worldgenabstracttree.generate(worldIn, rand, blockpos))
/*     */             {
/* 121 */               worldgenabstracttree.func_180711_a(worldIn, rand, blockpos);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 128 */     int j1 = rand.nextInt(5) - 3;
/*     */     
/* 130 */     if (this.field_150632_aF == 1)
/*     */     {
/* 132 */       j1 += 2;
/*     */     }
/*     */     
/* 135 */     for (int k1 = 0; k1 < j1; k1++) {
/*     */       
/* 137 */       int l1 = rand.nextInt(3);
/*     */       
/* 139 */       if (l1 == 0) {
/*     */         
/* 141 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
/*     */       }
/* 143 */       else if (l1 == 1) {
/*     */         
/* 145 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
/*     */       }
/* 147 */       else if (l1 == 2) {
/*     */         
/* 149 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
/*     */       } 
/*     */       
/* 152 */       for (int i2 = 0; i2 < 5; i2++) {
/*     */         
/* 154 */         int j2 = rand.nextInt(16) + 8;
/* 155 */         int k2 = rand.nextInt(16) + 8;
/* 156 */         int i1 = rand.nextInt(worldIn.getHeight(pos.add(j2, 0, k2)).getY() + 32);
/*     */         
/* 158 */         if (DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, new BlockPos(pos.getX() + j2, i1, pos.getZ() + k2))) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 165 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/* 170 */     int i = super.getGrassColorAtPos(pos);
/* 171 */     return (this.field_150632_aF == 3) ? ((i & 0xFEFEFE) + 2634762 >> 1) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 176 */     if (this.biomeID == BiomeGenBase.forest.biomeID) {
/*     */       
/* 178 */       BiomeGenForest biomegenforest = new BiomeGenForest(p_180277_1_, 1);
/* 179 */       biomegenforest.setHeight(new BiomeGenBase.Height(this.minHeight, this.maxHeight + 0.2F));
/* 180 */       biomegenforest.setBiomeName("Flower Forest");
/* 181 */       biomegenforest.func_150557_a(6976549, true);
/* 182 */       biomegenforest.setFillerBlockMetadata(8233509);
/* 183 */       return biomegenforest;
/*     */     } 
/*     */ 
/*     */     
/* 187 */     return (this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID) ? new BiomeGenMutated(p_180277_1_, this)
/*     */       {
/*     */         public void decorate(World worldIn, Random rand, BlockPos pos)
/*     */         {
/* 191 */           this.baseBiome.decorate(worldIn, rand, pos);
/*     */         }
/*     */       } : new BiomeGenMutated(p_180277_1_, this)
/*     */       {
/*     */         public WorldGenAbstractTree genBigTreeChance(Random rand)
/*     */         {
/* 197 */           return rand.nextBoolean() ? (WorldGenAbstractTree)BiomeGenForest.field_150629_aC : (WorldGenAbstractTree)BiomeGenForest.field_150630_aD;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenForest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */