/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*    */ 
/*    */ 
/*    */ public class RenderChunkUtils
/*    */ {
/*    */   public static int getCountBlocks(RenderChunk renderChunk) {
/* 11 */     ExtendedBlockStorage[] aextendedblockstorage = renderChunk.getChunk().getBlockStorageArray();
/*    */     
/* 13 */     if (aextendedblockstorage == null)
/*    */     {
/* 15 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 19 */     int i = renderChunk.getPosition().getY() >> 4;
/* 20 */     ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[i];
/* 21 */     return (extendedblockstorage == null) ? 0 : extendedblockstorage.getBlockRefCount();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static double getRelativeBufferSize(RenderChunk renderChunk) {
/* 27 */     int i = getCountBlocks(renderChunk);
/* 28 */     double d0 = getRelativeBufferSize(i);
/* 29 */     return d0;
/*    */   }
/*    */ 
/*    */   
/*    */   public static double getRelativeBufferSize(int blockCount) {
/* 34 */     double d0 = blockCount / 4096.0D;
/* 35 */     d0 *= 0.995D;
/* 36 */     double d1 = d0 * 2.0D - 1.0D;
/* 37 */     d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
/* 38 */     return MathHelper.sqrt_double(1.0D - d1 * d1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\RenderChunkUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */