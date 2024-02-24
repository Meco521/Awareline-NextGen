/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenEnd
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenEnd(int id) {
/* 10 */     super(id);
/* 11 */     this.spawnableMonsterList.clear();
/* 12 */     this.spawnableCreatureList.clear();
/* 13 */     this.spawnableWaterCreatureList.clear();
/* 14 */     this.spawnableCaveCreatureList.clear();
/* 15 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityEnderman.class, 10, 4, 4));
/* 16 */     this.topBlock = Blocks.dirt.getDefaultState();
/* 17 */     this.fillerBlock = Blocks.dirt.getDefaultState();
/* 18 */     this.theBiomeDecorator = new BiomeEndDecorator();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSkyColorByTemp(float p_76731_1_) {
/* 26 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeGenEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */