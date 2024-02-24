/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ 
/*    */ 
/*    */ public class BiomeGenMutated
/*    */   extends BiomeGenBase
/*    */ {
/*    */   protected BiomeGenBase baseBiome;
/*    */   
/*    */   public BiomeGenMutated(int id, BiomeGenBase biome) {
/* 17 */     super(id);
/* 18 */     this.baseBiome = biome;
/* 19 */     func_150557_a(biome.color, true);
/* 20 */     this.biomeName = biome.biomeName + " M";
/* 21 */     this.topBlock = biome.topBlock;
/* 22 */     this.fillerBlock = biome.fillerBlock;
/* 23 */     this.fillerBlockMetadata = biome.fillerBlockMetadata;
/* 24 */     this.minHeight = biome.minHeight;
/* 25 */     this.maxHeight = biome.maxHeight;
/* 26 */     this.temperature = biome.temperature;
/* 27 */     this.rainfall = biome.rainfall;
/* 28 */     this.waterColorMultiplier = biome.waterColorMultiplier;
/* 29 */     this.enableSnow = biome.enableSnow;
/* 30 */     this.enableRain = biome.enableRain;
/* 31 */     this.spawnableCreatureList = Lists.newArrayList(biome.spawnableCreatureList);
/* 32 */     this.spawnableMonsterList = Lists.newArrayList(biome.spawnableMonsterList);
/* 33 */     this.spawnableCaveCreatureList = Lists.newArrayList(biome.spawnableCaveCreatureList);
/* 34 */     this.spawnableWaterCreatureList = Lists.newArrayList(biome.spawnableWaterCreatureList);
/* 35 */     this.temperature = biome.temperature;
/* 36 */     this.rainfall = biome.rainfall;
/* 37 */     this.minHeight = biome.minHeight + 0.1F;
/* 38 */     this.maxHeight = biome.maxHeight + 0.2F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 43 */     this.baseBiome.theBiomeDecorator.decorate(worldIn, rand, this, pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 48 */     this.baseBiome.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getSpawningChance() {
/* 56 */     return this.baseBiome.getSpawningChance();
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 61 */     return this.baseBiome.genBigTreeChance(rand);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFoliageColorAtPos(BlockPos pos) {
/* 66 */     return this.baseBiome.getFoliageColorAtPos(pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrassColorAtPos(BlockPos pos) {
/* 71 */     return this.baseBiome.getGrassColorAtPos(pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends BiomeGenBase> getBiomeClass() {
/* 76 */     return this.baseBiome.getBiomeClass();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEqualTo(BiomeGenBase biome) {
/* 84 */     return this.baseBiome.isEqualTo(biome);
/*    */   }
/*    */ 
/*    */   
/*    */   public BiomeGenBase.TempCategory getTempCategory() {
/* 89 */     return this.baseBiome.getTempCategory();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenMutated.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */