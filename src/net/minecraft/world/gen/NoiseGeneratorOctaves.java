/*    */ package net.minecraft.world.gen;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGeneratorOctaves
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private final NoiseGeneratorImproved[] generatorCollection;
/*    */   private final int octaves;
/*    */   
/*    */   public NoiseGeneratorOctaves(Random seed, int octavesIn) {
/* 17 */     this.octaves = octavesIn;
/* 18 */     this.generatorCollection = new NoiseGeneratorImproved[octavesIn];
/*    */     
/* 20 */     for (int i = 0; i < octavesIn; i++)
/*    */     {
/* 22 */       this.generatorCollection[i] = new NoiseGeneratorImproved(seed);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale) {
/* 32 */     if (noiseArray == null) {
/*    */       
/* 34 */       noiseArray = new double[xSize * ySize * zSize];
/*    */     }
/*    */     else {
/*    */       
/* 38 */       for (int i = 0; i < noiseArray.length; i++)
/*    */       {
/* 40 */         noiseArray[i] = 0.0D;
/*    */       }
/*    */     } 
/*    */     
/* 44 */     double d3 = 1.0D;
/*    */     
/* 46 */     for (int j = 0; j < this.octaves; j++) {
/*    */       
/* 48 */       double d0 = xOffset * d3 * xScale;
/* 49 */       double d1 = yOffset * d3 * yScale;
/* 50 */       double d2 = zOffset * d3 * zScale;
/* 51 */       long k = MathHelper.floor_double_long(d0);
/* 52 */       long l = MathHelper.floor_double_long(d2);
/* 53 */       d0 -= k;
/* 54 */       d2 -= l;
/* 55 */       k %= 16777216L;
/* 56 */       l %= 16777216L;
/* 57 */       d0 += k;
/* 58 */       d2 += l;
/* 59 */       this.generatorCollection[j].populateNoiseArray(noiseArray, d0, d1, d2, xSize, ySize, zSize, xScale * d3, yScale * d3, zScale * d3, d3);
/* 60 */       d3 /= 2.0D;
/*    */     } 
/*    */     
/* 63 */     return noiseArray;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int zOffset, int xSize, int zSize, double xScale, double zScale, double p_76305_10_) {
/* 71 */     return generateNoiseOctaves(noiseArray, xOffset, 10, zOffset, xSize, 1, zSize, xScale, 1.0D, zScale);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\NoiseGeneratorOctaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */