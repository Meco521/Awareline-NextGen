/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.client.renderer.ViewFrustum;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShadowUtils
/*    */ {
/*    */   public static Iterator<RenderChunk> makeShadowChunkIterator(WorldClient world, double partialTicks, Entity viewEntity, int renderDistanceChunks, ViewFrustum viewFrustum) {
/* 18 */     float f = Shaders.getShadowRenderDistance();
/*    */     
/* 20 */     if (f > 0.0F && f < (renderDistanceChunks - 1 << 4)) {
/*    */       
/* 22 */       int i = MathHelper.ceiling_float_int(f / 16.0F) + 1;
/* 23 */       float f6 = world.getCelestialAngleRadians((float)partialTicks);
/* 24 */       float f1 = Shaders.sunPathRotation * MathHelper.deg2Rad;
/* 25 */       float f2 = (f6 > MathHelper.PId2 && f6 < 3.0F * MathHelper.PId2) ? (f6 + MathHelper.PI) : f6;
/* 26 */       float f3 = -MathHelper.sin(f2);
/* 27 */       float f4 = MathHelper.cos(f2) * MathHelper.cos(f1);
/* 28 */       float f5 = -MathHelper.cos(f2) * MathHelper.sin(f1);
/* 29 */       BlockPos blockpos = new BlockPos(MathHelper.floor_double(viewEntity.posX) >> 4, MathHelper.floor_double(viewEntity.posY) >> 4, MathHelper.floor_double(viewEntity.posZ) >> 4);
/* 30 */       BlockPos blockpos1 = blockpos.add((-f3 * i), (-f4 * i), (-f5 * i));
/* 31 */       BlockPos blockpos2 = blockpos.add((f3 * renderDistanceChunks), (f4 * renderDistanceChunks), (f5 * renderDistanceChunks));
/* 32 */       IteratorRenderChunks iteratorrenderchunks = new IteratorRenderChunks(viewFrustum, blockpos1, blockpos2, i, i);
/* 33 */       return iteratorrenderchunks;
/*    */     } 
/*    */ 
/*    */     
/* 37 */     List<RenderChunk> list = Arrays.asList(viewFrustum.renderChunks);
/* 38 */     Iterator<RenderChunk> iterator = list.iterator();
/* 39 */     return iterator;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ShadowUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */