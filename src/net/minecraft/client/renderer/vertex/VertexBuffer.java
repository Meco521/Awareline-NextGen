/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.optifine.render.VboRange;
/*     */ import net.optifine.render.VboRegion;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VertexBuffer
/*     */ {
/*     */   private int glBufferId;
/*     */   private final VertexFormat vertexFormat;
/*     */   private int count;
/*     */   private VboRegion vboRegion;
/*     */   private VboRange vboRange;
/*     */   private int drawMode;
/*     */   
/*     */   public VertexBuffer(VertexFormat vertexFormatIn) {
/*  21 */     this.vertexFormat = vertexFormatIn;
/*  22 */     this.glBufferId = OpenGlHelper.glGenBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindBuffer() {
/*  27 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bufferData(ByteBuffer p_181722_1_) {
/*  32 */     if (this.vboRegion != null) {
/*     */       
/*  34 */       this.vboRegion.bufferData(p_181722_1_, this.vboRange);
/*     */     }
/*     */     else {
/*     */       
/*  38 */       bindBuffer();
/*  39 */       OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, p_181722_1_, 35044);
/*  40 */       unbindBuffer();
/*  41 */       this.count = p_181722_1_.limit() / this.vertexFormat.getNextOffset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawArrays(int mode) {
/*  47 */     if (this.drawMode > 0)
/*     */     {
/*  49 */       mode = this.drawMode;
/*     */     }
/*     */     
/*  52 */     if (this.vboRegion != null) {
/*     */       
/*  54 */       this.vboRegion.drawArrays(mode, this.vboRange);
/*     */     }
/*     */     else {
/*     */       
/*  58 */       GL11.glDrawArrays(mode, 0, this.count);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindBuffer() {
/*  64 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlBuffers() {
/*  69 */     if (this.glBufferId >= 0) {
/*     */       
/*  71 */       OpenGlHelper.glDeleteBuffers(this.glBufferId);
/*  72 */       this.glBufferId = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVboRegion(VboRegion p_setVboRegion_1_) {
/*  78 */     if (p_setVboRegion_1_ != null) {
/*     */       
/*  80 */       deleteGlBuffers();
/*  81 */       this.vboRegion = p_setVboRegion_1_;
/*  82 */       this.vboRange = new VboRange();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public VboRegion getVboRegion() {
/*  88 */     return this.vboRegion;
/*     */   }
/*     */ 
/*     */   
/*     */   public VboRange getVboRange() {
/*  93 */     return this.vboRange;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDrawMode() {
/*  98 */     return this.drawMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDrawMode(int p_setDrawMode_1_) {
/* 103 */     this.drawMode = p_setDrawMode_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\vertex\VertexBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */