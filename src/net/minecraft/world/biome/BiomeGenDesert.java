/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenDesertWells;
/*    */ 
/*    */ 
/*    */ public class BiomeGenDesert
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenDesert(int id) {
/* 14 */     super(id);
/* 15 */     this.spawnableCreatureList.clear();
/* 16 */     this.topBlock = Blocks.sand.getDefaultState();
/* 17 */     this.fillerBlock = Blocks.sand.getDefaultState();
/* 18 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 19 */     this.theBiomeDecorator.deadBushPerChunk = 2;
/* 20 */     this.theBiomeDecorator.reedsPerChunk = 50;
/* 21 */     this.theBiomeDecorator.cactiPerChunk = 10;
/* 22 */     this.spawnableCreatureList.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 27 */     super.decorate(worldIn, rand, pos);
/*    */     
/* 29 */     if (rand.nextInt(1000) == 0) {
/*    */       
/* 31 */       int i = rand.nextInt(16) + 8;
/* 32 */       int j = rand.nextInt(16) + 8;
/* 33 */       BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
/* 34 */       (new WorldGenDesertWells()).generate(worldIn, rand, blockpos);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenDesert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */