/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.ChunkCoordIntPair;
/*    */ 
/*    */ 
/*    */ public class ChunkPosComparator
/*    */   implements Comparator<ChunkCoordIntPair>
/*    */ {
/*    */   private final int chunkPosX;
/*    */   private final int chunkPosZ;
/*    */   private final double yawRad;
/*    */   private final double pitchNorm;
/*    */   
/*    */   public ChunkPosComparator(int chunkPosX, int chunkPosZ, double yawRad, double pitchRad) {
/* 17 */     this.chunkPosX = chunkPosX;
/* 18 */     this.chunkPosZ = chunkPosZ;
/* 19 */     this.yawRad = yawRad;
/* 20 */     this.pitchNorm = 1.0D - MathHelper.clamp_double(Math.abs(pitchRad) / 1.5707963267948966D, 0.0D, 1.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(ChunkCoordIntPair cp1, ChunkCoordIntPair cp2) {
/* 25 */     int i = getDistSq(cp1);
/* 26 */     int j = getDistSq(cp2);
/* 27 */     return i - j;
/*    */   }
/*    */ 
/*    */   
/*    */   private int getDistSq(ChunkCoordIntPair cp) {
/* 32 */     int i = cp.chunkXPos - this.chunkPosX;
/* 33 */     int j = cp.chunkZPos - this.chunkPosZ;
/* 34 */     int k = i * i + j * j;
/* 35 */     double d0 = MathHelper.atan2(j, i);
/* 36 */     double d1 = Math.abs(d0 - this.yawRad);
/*    */     
/* 38 */     if (d1 > Math.PI)
/*    */     {
/* 40 */       d1 = 6.283185307179586D - d1;
/*    */     }
/*    */     
/* 43 */     k = (int)(k * 1000.0D * this.pitchNorm * d1 * d1);
/* 44 */     return k;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\ChunkPosComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */