/*    */ package net.minecraft.world.gen;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.chunk.IChunkProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapGenBase
/*    */ {
/* 12 */   protected int range = 8;
/*    */ 
/*    */   
/* 15 */   protected Random rand = new Random();
/*    */ 
/*    */   
/*    */   protected World worldObj;
/*    */ 
/*    */   
/*    */   public void generate(IChunkProvider chunkProviderIn, World worldIn, int x, int z, ChunkPrimer chunkPrimerIn) {
/* 22 */     int i = this.range;
/* 23 */     this.worldObj = worldIn;
/* 24 */     this.rand.setSeed(worldIn.getSeed());
/* 25 */     long j = this.rand.nextLong();
/* 26 */     long k = this.rand.nextLong();
/*    */     
/* 28 */     for (int l = x - i; l <= x + i; l++) {
/*    */       
/* 30 */       for (int i1 = z - i; i1 <= z + i; i1++) {
/*    */         
/* 32 */         long j1 = l * j;
/* 33 */         long k1 = i1 * k;
/* 34 */         this.rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());
/* 35 */         recursiveGenerate(worldIn, l, i1, x, z, chunkPrimerIn);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {}
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\MapGenBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */