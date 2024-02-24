/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.client.renderer.ViewFrustum;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.optifine.BlockPosM;
/*    */ 
/*    */ public class IteratorRenderChunks
/*    */   implements Iterator<RenderChunk>
/*    */ {
/*    */   private final ViewFrustum viewFrustum;
/*    */   private final Iterator3d Iterator3d;
/* 14 */   private final BlockPosM posBlock = new BlockPosM(0, 0, 0);
/*    */ 
/*    */   
/*    */   public IteratorRenderChunks(ViewFrustum viewFrustum, BlockPos posStart, BlockPos posEnd, int width, int height) {
/* 18 */     this.viewFrustum = viewFrustum;
/* 19 */     this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 24 */     return this.Iterator3d.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderChunk next() {
/* 29 */     BlockPos blockpos = this.Iterator3d.next();
/* 30 */     this.posBlock.setXyz(blockpos.getX() << 4, blockpos.getY() << 4, blockpos.getZ() << 4);
/* 31 */     RenderChunk renderchunk = this.viewFrustum.getRenderChunk((BlockPos)this.posBlock);
/* 32 */     return renderchunk;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 37 */     throw new RuntimeException("Not implemented");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\IteratorRenderChunks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */