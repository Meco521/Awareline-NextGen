/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ 
/*    */ 
/*    */ public class BiomeGenSwamp
/*    */   extends BiomeGenBase
/*    */ {
/*    */   protected BiomeGenSwamp(int id) {
/* 18 */     super(id);
/* 19 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 20 */     this.theBiomeDecorator.flowersPerChunk = 1;
/* 21 */     this.theBiomeDecorator.deadBushPerChunk = 1;
/* 22 */     this.theBiomeDecorator.mushroomsPerChunk = 8;
/* 23 */     this.theBiomeDecorator.reedsPerChunk = 10;
/* 24 */     this.theBiomeDecorator.clayPerChunk = 1;
/* 25 */     this.theBiomeDecorator.waterlilyPerChunk = 4;
/* 26 */     this.theBiomeDecorator.sandPerChunk2 = 0;
/* 27 */     this.theBiomeDecorator.sandPerChunk = 0;
/* 28 */     this.theBiomeDecorator.grassPerChunk = 5;
/* 29 */     this.waterColorMultiplier = 14745518;
/* 30 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntitySlime.class, 1, 1, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 35 */     return (WorldGenAbstractTree)this.worldGeneratorSwamp;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrassColorAtPos(BlockPos pos) {
/* 40 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
/* 41 */     return (d0 < -0.1D) ? 5011004 : 6975545;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFoliageColorAtPos(BlockPos pos) {
/* 46 */     return 6975545;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 51 */     return BlockFlower.EnumFlowerType.BLUE_ORCHID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 56 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(x * 0.25D, z * 0.25D);
/*    */     
/* 58 */     if (d0 > 0.0D) {
/*    */       
/* 60 */       int i = x & 0xF;
/* 61 */       int j = z & 0xF;
/*    */       
/* 63 */       for (int k = 255; k >= 0; k--) {
/*    */         
/* 65 */         if (chunkPrimerIn.getBlockState(j, k, i).getBlock().getMaterial() != Material.air) {
/*    */           
/* 67 */           if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.water) {
/*    */             
/* 69 */             chunkPrimerIn.setBlockState(j, k, i, Blocks.water.getDefaultState());
/*    */             
/* 71 */             if (d0 < 0.12D)
/*    */             {
/* 73 */               chunkPrimerIn.setBlockState(j, k + 1, i, Blocks.waterlily.getDefaultState());
/*    */             }
/*    */           } 
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 82 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenSwamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */