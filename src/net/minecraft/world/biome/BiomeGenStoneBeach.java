/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenStoneBeach
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenStoneBeach(int id) {
/*  9 */     super(id);
/* 10 */     this.spawnableCreatureList.clear();
/* 11 */     this.topBlock = Blocks.stone.getDefaultState();
/* 12 */     this.fillerBlock = Blocks.stone.getDefaultState();
/* 13 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 14 */     this.theBiomeDecorator.deadBushPerChunk = 0;
/* 15 */     this.theBiomeDecorator.reedsPerChunk = 0;
/* 16 */     this.theBiomeDecorator.cactiPerChunk = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenStoneBeach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */