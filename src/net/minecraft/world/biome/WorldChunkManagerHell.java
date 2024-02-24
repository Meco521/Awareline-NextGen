/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldChunkManagerHell
/*    */   extends WorldChunkManager
/*    */ {
/*    */   private final BiomeGenBase biomeGenerator;
/*    */   private final float rainfall;
/*    */   
/*    */   public WorldChunkManagerHell(BiomeGenBase p_i45374_1_, float p_i45374_2_) {
/* 19 */     this.biomeGenerator = p_i45374_1_;
/* 20 */     this.rainfall = p_i45374_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase getBiomeGenerator(BlockPos pos) {
/* 28 */     return this.biomeGenerator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int height) {
/* 36 */     if (biomes == null || biomes.length < width * height)
/*    */     {
/* 38 */       biomes = new BiomeGenBase[width * height];
/*    */     }
/*    */     
/* 41 */     Arrays.fill((Object[])biomes, 0, width * height, this.biomeGenerator);
/* 42 */     return biomes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
/* 50 */     if (listToReuse == null || listToReuse.length < width * length)
/*    */     {
/* 52 */       listToReuse = new float[width * length];
/*    */     }
/*    */     
/* 55 */     Arrays.fill(listToReuse, 0, width * length, this.rainfall);
/* 56 */     return listToReuse;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
/* 65 */     if (oldBiomeList == null || oldBiomeList.length < width * depth)
/*    */     {
/* 67 */       oldBiomeList = new BiomeGenBase[width * depth];
/*    */     }
/*    */     
/* 70 */     Arrays.fill((Object[])oldBiomeList, 0, width * depth, this.biomeGenerator);
/* 71 */     return oldBiomeList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
/* 80 */     return loadBlockGeneratorData(listToReuse, x, z, width, length);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos findBiomePosition(int x, int z, int range, List<BiomeGenBase> biomes, Random random) {
/* 85 */     return biomes.contains(this.biomeGenerator) ? new BlockPos(x - range + random.nextInt((range << 1) + 1), 0, z - range + random.nextInt((range << 1) + 1)) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List<BiomeGenBase> p_76940_4_) {
/* 93 */     return p_76940_4_.contains(this.biomeGenerator);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\WorldChunkManagerHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */