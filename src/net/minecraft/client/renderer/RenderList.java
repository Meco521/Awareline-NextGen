/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.renderer.chunk.ListedRenderChunk;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderList
/*     */   extends ChunkRenderContainer
/*     */ {
/*     */   private double viewEntityX;
/*     */   private double viewEntityY;
/*     */   private double viewEntityZ;
/*  16 */   IntBuffer bufferLists = GLAllocation.createDirectIntBuffer(16);
/*     */ 
/*     */   
/*     */   public void renderChunkLayer(EnumWorldBlockLayer layer) {
/*  20 */     if (this.initialized) {
/*     */       
/*  22 */       if (!Config.isRenderRegions()) {
/*     */         
/*  24 */         for (RenderChunk renderchunk1 : this.renderChunks)
/*     */         {
/*  26 */           ListedRenderChunk listedrenderchunk1 = (ListedRenderChunk)renderchunk1;
/*  27 */           GlStateManager.pushMatrix();
/*  28 */           preRenderChunk(renderchunk1);
/*  29 */           GL11.glCallList(listedrenderchunk1.getDisplayList(layer, listedrenderchunk1.getCompiledChunk()));
/*  30 */           GlStateManager.popMatrix();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  35 */         int i = Integer.MIN_VALUE;
/*  36 */         int j = Integer.MIN_VALUE;
/*     */         
/*  38 */         for (RenderChunk renderchunk : this.renderChunks) {
/*     */           
/*  40 */           ListedRenderChunk listedrenderchunk = (ListedRenderChunk)renderchunk;
/*     */           
/*  42 */           if (i != renderchunk.regionX || j != renderchunk.regionZ) {
/*     */             
/*  44 */             if (this.bufferLists.position() > 0)
/*     */             {
/*  46 */               drawRegion(i, j, this.bufferLists);
/*     */             }
/*     */             
/*  49 */             i = renderchunk.regionX;
/*  50 */             j = renderchunk.regionZ;
/*     */           } 
/*     */           
/*  53 */           if (this.bufferLists.position() >= this.bufferLists.capacity()) {
/*     */             
/*  55 */             IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(this.bufferLists.capacity() << 1);
/*  56 */             this.bufferLists.flip();
/*  57 */             intbuffer.put(this.bufferLists);
/*  58 */             this.bufferLists = intbuffer;
/*     */           } 
/*     */           
/*  61 */           this.bufferLists.put(listedrenderchunk.getDisplayList(layer, listedrenderchunk.getCompiledChunk()));
/*     */         } 
/*     */         
/*  64 */         if (this.bufferLists.position() > 0)
/*     */         {
/*  66 */           drawRegion(i, j, this.bufferLists);
/*     */         }
/*     */       } 
/*     */       
/*  70 */       if (Config.isMultiTexture())
/*     */       {
/*  72 */         GlStateManager.bindCurrentTexture();
/*     */       }
/*     */       
/*  75 */       GlStateManager.resetColor();
/*  76 */       this.renderChunks.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/*  82 */     this.viewEntityX = viewEntityXIn;
/*  83 */     this.viewEntityY = viewEntityYIn;
/*  84 */     this.viewEntityZ = viewEntityZIn;
/*  85 */     super.initialize(viewEntityXIn, viewEntityYIn, viewEntityZIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawRegion(int p_drawRegion_1_, int p_drawRegion_2_, IntBuffer p_drawRegion_3_) {
/*  90 */     GlStateManager.pushMatrix();
/*  91 */     preRenderRegion(p_drawRegion_1_, 0, p_drawRegion_2_);
/*  92 */     p_drawRegion_3_.flip();
/*  93 */     GlStateManager.callLists(p_drawRegion_3_);
/*  94 */     p_drawRegion_3_.clear();
/*  95 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void preRenderRegion(int p_preRenderRegion_1_, int p_preRenderRegion_2_, int p_preRenderRegion_3_) {
/* 100 */     GlStateManager.translate((float)(p_preRenderRegion_1_ - this.viewEntityX), (float)(p_preRenderRegion_2_ - this.viewEntityY), (float)(p_preRenderRegion_3_ - this.viewEntityZ));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\RenderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */