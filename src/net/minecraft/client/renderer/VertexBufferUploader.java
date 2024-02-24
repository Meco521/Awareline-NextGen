/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class VertexBufferUploader
/*    */   extends WorldVertexBufferUploader {
/*  8 */   private VertexBuffer vertexBuffer = null;
/*    */ 
/*    */   
/*    */   public void draw(WorldRenderer p_181679_1_) {
/* 12 */     if (p_181679_1_.getDrawMode() == 7 && Config.isQuadsToTriangles()) {
/*    */       
/* 14 */       p_181679_1_.quadsToTriangles();
/* 15 */       this.vertexBuffer.setDrawMode(p_181679_1_.getDrawMode());
/*    */     } 
/*    */     
/* 18 */     this.vertexBuffer.bufferData(p_181679_1_.getByteBuffer());
/* 19 */     p_181679_1_.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVertexBuffer(VertexBuffer vertexBufferIn) {
/* 24 */     this.vertexBuffer = vertexBufferIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\VertexBufferUploader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */