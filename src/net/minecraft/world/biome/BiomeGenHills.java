/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockSilverfish;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BiomeGenHills
/*     */   extends BiomeGenBase {
/*  17 */   private final WorldGenerator theWorldGenerator = (WorldGenerator)new WorldGenMinable(Blocks.monster_egg.getDefaultState().withProperty((IProperty)BlockSilverfish.VARIANT, (Comparable)BlockSilverfish.EnumType.STONE), 9);
/*  18 */   private final WorldGenTaiga2 field_150634_aD = new WorldGenTaiga2(false);
/*  19 */   private final int field_150635_aE = 0;
/*  20 */   private final int field_150636_aF = 1;
/*  21 */   private final int field_150637_aG = 2;
/*     */   
/*     */   private int field_150638_aH;
/*     */   
/*     */   protected BiomeGenHills(int id, boolean p_i45373_2_) {
/*  26 */     super(id);
/*  27 */     getClass(); this.field_150638_aH = 0;
/*     */     
/*  29 */     if (p_i45373_2_) {
/*     */       
/*  31 */       this.theBiomeDecorator.treesPerChunk = 3;
/*  32 */       getClass(); this.field_150638_aH = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  38 */     return (rand.nextInt(3) > 0) ? (WorldGenAbstractTree)this.field_150634_aD : super.genBigTreeChance(rand);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  43 */     super.decorate(worldIn, rand, pos);
/*  44 */     int i = 3 + rand.nextInt(6);
/*     */     
/*  46 */     for (int j = 0; j < i; j++) {
/*     */       
/*  48 */       int k = rand.nextInt(16);
/*  49 */       int l = rand.nextInt(28) + 4;
/*  50 */       int i1 = rand.nextInt(16);
/*  51 */       BlockPos blockpos = pos.add(k, l, i1);
/*     */       
/*  53 */       if (worldIn.getBlockState(blockpos).getBlock() == Blocks.stone)
/*     */       {
/*  55 */         worldIn.setBlockState(blockpos, Blocks.emerald_ore.getDefaultState(), 2);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     for (i = 0; i < 7; i++) {
/*     */       
/*  61 */       int j1 = rand.nextInt(16);
/*  62 */       int k1 = rand.nextInt(64);
/*  63 */       int l1 = rand.nextInt(16);
/*  64 */       this.theWorldGenerator.generate(worldIn, rand, pos.add(j1, k1, l1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/*  70 */     this.topBlock = Blocks.grass.getDefaultState();
/*  71 */     this.fillerBlock = Blocks.dirt.getDefaultState();
/*     */     
/*  73 */     getClass(); if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.field_150638_aH == 2) {
/*     */       
/*  75 */       this.topBlock = Blocks.gravel.getDefaultState();
/*  76 */       this.fillerBlock = Blocks.gravel.getDefaultState();
/*     */     } else {
/*  78 */       getClass(); if (noiseVal > 1.0D && this.field_150638_aH != 1) {
/*     */         
/*  80 */         this.topBlock = Blocks.stone.getDefaultState();
/*  81 */         this.fillerBlock = Blocks.stone.getDefaultState();
/*     */       } 
/*     */     } 
/*  84 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BiomeGenHills mutateHills(BiomeGenBase p_150633_1_) {
/*  92 */     getClass(); this.field_150638_aH = 2;
/*  93 */     func_150557_a(p_150633_1_.color, true);
/*  94 */     setBiomeName(p_150633_1_.biomeName + " M");
/*  95 */     setHeight(new BiomeGenBase.Height(p_150633_1_.minHeight, p_150633_1_.maxHeight));
/*  96 */     setTemperatureRainfall(p_150633_1_.temperature, p_150633_1_.rainfall);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 102 */     return (new BiomeGenHills(p_180277_1_, false)).mutateHills(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenHills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */