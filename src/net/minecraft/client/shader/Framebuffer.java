/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Framebuffer
/*     */ {
/*     */   public int framebufferTextureWidth;
/*     */   public int framebufferTextureHeight;
/*     */   public int framebufferWidth;
/*     */   public int framebufferHeight;
/*     */   public boolean useDepth;
/*     */   public int framebufferObject;
/*     */   public int framebufferTexture;
/*     */   public int depthBuffer;
/*     */   public float[] framebufferColor;
/*     */   public int framebufferFilter;
/*     */   
/*     */   public Framebuffer(int p_i45078_1_, int p_i45078_2_, boolean p_i45078_3_) {
/*  28 */     this.useDepth = p_i45078_3_;
/*  29 */     this.framebufferObject = -1;
/*  30 */     this.framebufferTexture = -1;
/*  31 */     this.depthBuffer = -1;
/*  32 */     this.framebufferColor = new float[4];
/*  33 */     this.framebufferColor[0] = 1.0F;
/*  34 */     this.framebufferColor[1] = 1.0F;
/*  35 */     this.framebufferColor[2] = 1.0F;
/*  36 */     this.framebufferColor[3] = 0.0F;
/*  37 */     createBindFramebuffer(p_i45078_1_, p_i45078_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void createBindFramebuffer(int width, int height) {
/*  42 */     if (!OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/*  44 */       this.framebufferWidth = width;
/*  45 */       this.framebufferHeight = height;
/*     */     }
/*     */     else {
/*     */       
/*  49 */       GlStateManager.enableDepth();
/*     */       
/*  51 */       if (this.framebufferObject >= 0)
/*     */       {
/*  53 */         deleteFramebuffer();
/*     */       }
/*     */       
/*  56 */       createFramebuffer(width, height);
/*  57 */       checkFramebufferComplete();
/*  58 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteFramebuffer() {
/*  64 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/*  66 */       unbindFramebufferTexture();
/*  67 */       unbindFramebuffer();
/*     */       
/*  69 */       if (this.depthBuffer > -1) {
/*     */         
/*  71 */         OpenGlHelper.glDeleteRenderbuffers(this.depthBuffer);
/*  72 */         this.depthBuffer = -1;
/*     */       } 
/*     */       
/*  75 */       if (this.framebufferTexture > -1) {
/*     */         
/*  77 */         TextureUtil.deleteTexture(this.framebufferTexture);
/*  78 */         this.framebufferTexture = -1;
/*     */       } 
/*     */       
/*  81 */       if (this.framebufferObject > -1) {
/*     */         
/*  83 */         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*  84 */         OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
/*  85 */         this.framebufferObject = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createFramebuffer(int width, int height) {
/*  92 */     this.framebufferWidth = width;
/*  93 */     this.framebufferHeight = height;
/*  94 */     this.framebufferTextureWidth = width;
/*  95 */     this.framebufferTextureHeight = height;
/*     */     
/*  97 */     if (!OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/*  99 */       framebufferClear();
/*     */     }
/*     */     else {
/*     */       
/* 103 */       this.framebufferObject = OpenGlHelper.glGenFramebuffers();
/* 104 */       this.framebufferTexture = TextureUtil.glGenTextures();
/*     */       
/* 106 */       if (this.useDepth)
/*     */       {
/* 108 */         this.depthBuffer = OpenGlHelper.glGenRenderbuffers();
/*     */       }
/*     */       
/* 111 */       setFramebufferFilter(9728);
/* 112 */       GlStateManager.bindTexture(this.framebufferTexture);
/* 113 */       GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (ByteBuffer)null);
/* 114 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
/* 115 */       OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.framebufferTexture, 0);
/*     */       
/* 117 */       if (this.useDepth) {
/*     */         
/* 119 */         OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
/* 120 */         OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
/* 121 */         OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
/*     */       } 
/*     */       
/* 124 */       framebufferClear();
/* 125 */       unbindFramebufferTexture();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramebufferFilter(int p_147607_1_) {
/* 131 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 133 */       this.framebufferFilter = p_147607_1_;
/* 134 */       GlStateManager.bindTexture(this.framebufferTexture);
/* 135 */       GL11.glTexParameterf(3553, 10241, p_147607_1_);
/* 136 */       GL11.glTexParameterf(3553, 10240, p_147607_1_);
/* 137 */       GL11.glTexParameterf(3553, 10242, 10496.0F);
/* 138 */       GL11.glTexParameterf(3553, 10243, 10496.0F);
/* 139 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkFramebufferComplete() {
/* 145 */     int i = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
/*     */     
/* 147 */     if (i != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
/*     */       
/* 149 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT)
/*     */       {
/* 151 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
/*     */       }
/* 153 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH)
/*     */       {
/* 155 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
/*     */       }
/* 157 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER)
/*     */       {
/* 159 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
/*     */       }
/* 161 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER)
/*     */       {
/* 163 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
/*     */       }
/*     */ 
/*     */       
/* 167 */       throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindFramebufferTexture() {
/* 174 */     if (OpenGlHelper.isFramebufferEnabled())
/*     */     {
/* 176 */       GlStateManager.bindTexture(this.framebufferTexture);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindFramebufferTexture() {
/* 182 */     if (OpenGlHelper.isFramebufferEnabled())
/*     */     {
/* 184 */       GlStateManager.bindTexture(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindFramebuffer(boolean p_147610_1_) {
/* 190 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 192 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
/*     */       
/* 194 */       if (p_147610_1_)
/*     */       {
/* 196 */         GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindFramebuffer() {
/* 203 */     if (OpenGlHelper.isFramebufferEnabled())
/*     */     {
/* 205 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramebufferColor(float p_147604_1_, float p_147604_2_, float p_147604_3_, float p_147604_4_) {
/* 211 */     this.framebufferColor[0] = p_147604_1_;
/* 212 */     this.framebufferColor[1] = p_147604_2_;
/* 213 */     this.framebufferColor[2] = p_147604_3_;
/* 214 */     this.framebufferColor[3] = p_147604_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void framebufferRender(int p_147615_1_, int p_147615_2_) {
/* 219 */     framebufferRenderExt(p_147615_1_, p_147615_2_, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void framebufferRenderExt(int p_178038_1_, int p_178038_2_, boolean p_178038_3_) {
/* 224 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 226 */       GlStateManager.colorMask(true, true, true, false);
/* 227 */       GlStateManager.disableDepth();
/* 228 */       GlStateManager.depthMask(false);
/* 229 */       GlStateManager.matrixMode(5889);
/* 230 */       GlStateManager.loadIdentity();
/* 231 */       GlStateManager.ortho(0.0D, p_178038_1_, p_178038_2_, 0.0D, 1000.0D, 3000.0D);
/* 232 */       GlStateManager.matrixMode(5888);
/* 233 */       GlStateManager.loadIdentity();
/* 234 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 235 */       GlStateManager.viewport(0, 0, p_178038_1_, p_178038_2_);
/* 236 */       GlStateManager.enableTexture2D();
/* 237 */       GlStateManager.disableLighting();
/* 238 */       GlStateManager.disableAlpha();
/*     */       
/* 240 */       if (p_178038_3_) {
/*     */         
/* 242 */         GlStateManager.disableBlend();
/* 243 */         GlStateManager.enableColorMaterial();
/*     */       } 
/*     */       
/* 246 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 247 */       bindFramebufferTexture();
/* 248 */       float f = p_178038_1_;
/* 249 */       float f1 = p_178038_2_;
/* 250 */       float f2 = this.framebufferWidth / this.framebufferTextureWidth;
/* 251 */       float f3 = this.framebufferHeight / this.framebufferTextureHeight;
/* 252 */       Tessellator tessellator = Tessellator.getInstance();
/* 253 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 254 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 255 */       worldrenderer.pos(0.0D, f1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 256 */       worldrenderer.pos(f, f1, 0.0D).tex(f2, 0.0D).color(255, 255, 255, 255).endVertex();
/* 257 */       worldrenderer.pos(f, 0.0D, 0.0D).tex(f2, f3).color(255, 255, 255, 255).endVertex();
/* 258 */       worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, f3).color(255, 255, 255, 255).endVertex();
/* 259 */       tessellator.draw();
/* 260 */       unbindFramebufferTexture();
/* 261 */       GlStateManager.depthMask(true);
/* 262 */       GlStateManager.colorMask(true, true, true, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void framebufferClear() {
/* 268 */     bindFramebuffer(true);
/* 269 */     GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
/* 270 */     int i = 16384;
/*     */     
/* 272 */     if (this.useDepth) {
/*     */       
/* 274 */       GlStateManager.clearDepth(1.0D);
/* 275 */       i |= 0x100;
/*     */     } 
/*     */     
/* 278 */     GlStateManager.clear(i);
/* 279 */     unbindFramebuffer();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\shader\Framebuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */