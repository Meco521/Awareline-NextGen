/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDirt;
/*    */ import net.minecraft.block.BlockDoublePlant;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*    */ 
/*    */ public class BiomeGenSavanna
/*    */   extends BiomeGenBase {
/* 17 */   private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);
/*    */ 
/*    */   
/*    */   protected BiomeGenSavanna(int id) {
/* 21 */     super(id);
/* 22 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityHorse.class, 1, 2, 6));
/* 23 */     this.theBiomeDecorator.treesPerChunk = 1;
/* 24 */     this.theBiomeDecorator.flowersPerChunk = 4;
/* 25 */     this.theBiomeDecorator.grassPerChunk = 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 30 */     return (rand.nextInt(5) > 0) ? (WorldGenAbstractTree)field_150627_aC : (WorldGenAbstractTree)this.worldGeneratorTrees;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 35 */     BiomeGenBase biomegenbase = new Mutated(p_180277_1_, this);
/* 36 */     biomegenbase.temperature = (this.temperature + 1.0F) * 0.5F;
/* 37 */     biomegenbase.minHeight = this.minHeight * 0.5F + 0.3F;
/* 38 */     biomegenbase.maxHeight = this.maxHeight * 0.5F + 1.2F;
/* 39 */     return biomegenbase;
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 44 */     DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
/*    */     
/* 46 */     for (int i = 0; i < 7; i++) {
/*    */       
/* 48 */       int j = rand.nextInt(16) + 8;
/* 49 */       int k = rand.nextInt(16) + 8;
/* 50 */       int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
/* 51 */       DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
/*    */     } 
/*    */     
/* 54 */     super.decorate(worldIn, rand, pos);
/*    */   }
/*    */   
/*    */   public static class Mutated
/*    */     extends BiomeGenMutated
/*    */   {
/*    */     public Mutated(int p_i45382_1_, BiomeGenBase p_i45382_2_) {
/* 61 */       super(p_i45382_1_, p_i45382_2_);
/* 62 */       this.theBiomeDecorator.treesPerChunk = 2;
/* 63 */       this.theBiomeDecorator.flowersPerChunk = 2;
/* 64 */       this.theBiomeDecorator.grassPerChunk = 5;
/*    */     }
/*    */ 
/*    */     
/*    */     public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 69 */       this.topBlock = Blocks.grass.getDefaultState();
/* 70 */       this.fillerBlock = Blocks.dirt.getDefaultState();
/*    */       
/* 72 */       if (noiseVal > 1.75D) {
/*    */         
/* 74 */         this.topBlock = Blocks.stone.getDefaultState();
/* 75 */         this.fillerBlock = Blocks.stone.getDefaultState();
/*    */       }
/* 77 */       else if (noiseVal > -0.5D) {
/*    */         
/* 79 */         this.topBlock = Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT);
/*    */       } 
/*    */       
/* 82 */       generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */     }
/*    */ 
/*    */     
/*    */     public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 87 */       this.theBiomeDecorator.decorate(worldIn, rand, this, pos);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenSavanna.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */