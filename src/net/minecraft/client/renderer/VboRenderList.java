/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.render.VboRegion;
/*     */ import net.optifine.shaders.ShadersRender;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class VboRenderList
/*     */   extends ChunkRenderContainer
/*     */ {
/*     */   private double viewEntityX;
/*     */   private double viewEntityY;
/*     */   private double viewEntityZ;
/*     */   
/*     */   public void renderChunkLayer(EnumWorldBlockLayer layer) {
/*  19 */     if (this.initialized) {
/*     */       
/*  21 */       if (!Config.isRenderRegions()) {
/*     */         
/*  23 */         for (RenderChunk renderchunk1 : this.renderChunks)
/*     */         {
/*  25 */           VertexBuffer vertexbuffer1 = renderchunk1.getVertexBufferByLayer(layer.ordinal());
/*  26 */           GlStateManager.pushMatrix();
/*  27 */           preRenderChunk(renderchunk1);
/*  28 */           renderchunk1.multModelviewMatrix();
/*  29 */           vertexbuffer1.bindBuffer();
/*  30 */           setupArrayPointers();
/*  31 */           vertexbuffer1.drawArrays(7);
/*  32 */           GlStateManager.popMatrix();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  37 */         int i = Integer.MIN_VALUE;
/*  38 */         int j = Integer.MIN_VALUE;
/*  39 */         VboRegion vboregion = null;
/*     */         
/*  41 */         for (RenderChunk renderchunk : this.renderChunks) {
/*     */           
/*  43 */           VertexBuffer vertexbuffer = renderchunk.getVertexBufferByLayer(layer.ordinal());
/*  44 */           VboRegion vboregion1 = vertexbuffer.getVboRegion();
/*     */           
/*  46 */           if (vboregion1 != vboregion || i != renderchunk.regionX || j != renderchunk.regionZ) {
/*     */             
/*  48 */             if (vboregion != null)
/*     */             {
/*  50 */               drawRegion(i, j, vboregion);
/*     */             }
/*     */             
/*  53 */             i = renderchunk.regionX;
/*  54 */             j = renderchunk.regionZ;
/*  55 */             vboregion = vboregion1;
/*     */           } 
/*     */           
/*  58 */           vertexbuffer.drawArrays(7);
/*     */         } 
/*     */         
/*  61 */         if (vboregion != null)
/*     */         {
/*  63 */           drawRegion(i, j, vboregion);
/*     */         }
/*     */       } 
/*     */       
/*  67 */       OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/*  68 */       GlStateManager.resetColor();
/*  69 */       this.renderChunks.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupArrayPointers() {
/*  75 */     if (Config.isShaders()) {
/*     */       
/*  77 */       ShadersRender.setupArrayPointersVbo();
/*     */     }
/*     */     else {
/*     */       
/*  81 */       GL11.glVertexPointer(3, 5126, 28, 0L);
/*  82 */       GL11.glColorPointer(4, 5121, 28, 12L);
/*  83 */       GL11.glTexCoordPointer(2, 5126, 28, 16L);
/*  84 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  85 */       GL11.glTexCoordPointer(2, 5122, 28, 24L);
/*  86 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/*  92 */     this.viewEntityX = viewEntityXIn;
/*  93 */     this.viewEntityY = viewEntityYIn;
/*  94 */     this.viewEntityZ = viewEntityZIn;
/*  95 */     super.initialize(viewEntityXIn, viewEntityYIn, viewEntityZIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawRegion(int p_drawRegion_1_, int p_drawRegion_2_, VboRegion p_drawRegion_3_) {
/* 100 */     GlStateManager.pushMatrix();
/* 101 */     preRenderRegion(p_drawRegion_1_, 0, p_drawRegion_2_);
/* 102 */     p_drawRegion_3_.finishDraw(this);
/* 103 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void preRenderRegion(int p_preRenderRegion_1_, int p_preRenderRegion_2_, int p_preRenderRegion_3_) {
/* 108 */     GlStateManager.translate((float)(p_preRenderRegion_1_ - this.viewEntityX), (float)(p_preRenderRegion_2_ - this.viewEntityY), (float)(p_preRenderRegion_3_ - this.viewEntityZ));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\VboRenderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */