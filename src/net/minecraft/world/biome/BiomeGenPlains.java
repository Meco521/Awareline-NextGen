/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BiomeGenPlains
/*     */   extends BiomeGenBase
/*     */ {
/*     */   protected boolean field_150628_aC;
/*     */   
/*     */   protected BiomeGenPlains(int id) {
/*  17 */     super(id);
/*  18 */     setTemperatureRainfall(0.8F, 0.4F);
/*  19 */     setHeight(height_LowPlains);
/*  20 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityHorse.class, 5, 2, 6));
/*  21 */     this.theBiomeDecorator.treesPerChunk = -999;
/*  22 */     this.theBiomeDecorator.flowersPerChunk = 4;
/*  23 */     this.theBiomeDecorator.grassPerChunk = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/*  28 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(pos.getX() / 200.0D, pos.getZ() / 200.0D);
/*     */     
/*  30 */     if (d0 < -0.8D) {
/*     */       
/*  32 */       int j = rand.nextInt(4);
/*     */       
/*  34 */       switch (j) {
/*     */         
/*     */         case 0:
/*  37 */           return BlockFlower.EnumFlowerType.ORANGE_TULIP;
/*     */         
/*     */         case 1:
/*  40 */           return BlockFlower.EnumFlowerType.RED_TULIP;
/*     */         
/*     */         case 2:
/*  43 */           return BlockFlower.EnumFlowerType.PINK_TULIP;
/*     */       } 
/*     */ 
/*     */       
/*  47 */       return BlockFlower.EnumFlowerType.WHITE_TULIP;
/*     */     } 
/*     */     
/*  50 */     if (rand.nextInt(3) > 0) {
/*     */       
/*  52 */       int i = rand.nextInt(3);
/*  53 */       return (i == 0) ? BlockFlower.EnumFlowerType.POPPY : ((i == 1) ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY);
/*     */     } 
/*     */ 
/*     */     
/*  57 */     return BlockFlower.EnumFlowerType.DANDELION;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  63 */     double d0 = GRASS_COLOR_NOISE.func_151601_a((pos.getX() + 8) / 200.0D, (pos.getZ() + 8) / 200.0D);
/*     */     
/*  65 */     if (d0 < -0.8D) {
/*     */       
/*  67 */       this.theBiomeDecorator.flowersPerChunk = 15;
/*  68 */       this.theBiomeDecorator.grassPerChunk = 5;
/*     */     }
/*     */     else {
/*     */       
/*  72 */       this.theBiomeDecorator.flowersPerChunk = 4;
/*  73 */       this.theBiomeDecorator.grassPerChunk = 10;
/*  74 */       DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
/*     */       
/*  76 */       for (int i = 0; i < 7; i++) {
/*     */         
/*  78 */         int j = rand.nextInt(16) + 8;
/*  79 */         int k = rand.nextInt(16) + 8;
/*  80 */         int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
/*  81 */         DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     if (this.field_150628_aC) {
/*     */       
/*  87 */       DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
/*     */       
/*  89 */       for (int i1 = 0; i1 < 10; i1++) {
/*     */         
/*  91 */         int j1 = rand.nextInt(16) + 8;
/*  92 */         int k1 = rand.nextInt(16) + 8;
/*  93 */         int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
/*  94 */         DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 103 */     BiomeGenPlains biomegenplains = new BiomeGenPlains(p_180277_1_);
/* 104 */     biomegenplains.setBiomeName("Sunflower Plains");
/* 105 */     biomegenplains.field_150628_aC = true;
/* 106 */     biomegenplains.setColor(9286496);
/* 107 */     biomegenplains.field_150609_ah = 14273354;
/* 108 */     return biomegenplains;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenPlains.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */