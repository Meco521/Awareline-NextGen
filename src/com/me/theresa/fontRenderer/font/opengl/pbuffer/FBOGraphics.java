/*     */ package com.me.theresa.fontRenderer.font.opengl.pbuffer;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.Graphics;
/*     */ import com.me.theresa.fontRenderer.font.Image;
/*     */ import com.me.theresa.fontRenderer.font.SlickException;
/*     */ import com.me.theresa.fontRenderer.font.log.Log;
/*     */ import com.me.theresa.fontRenderer.font.opengl.InternalTextureLoader;
/*     */ import com.me.theresa.fontRenderer.font.opengl.SlickCallable;
/*     */ import com.me.theresa.fontRenderer.font.opengl.Texture;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBOGraphics
/*     */   extends Graphics
/*     */ {
/*     */   private final Image image;
/*     */   private int FBO;
/*     */   private boolean valid = true;
/*     */   
/*     */   public FBOGraphics(Image image) throws SlickException {
/*  28 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  29 */     this.image = image;
/*     */     
/*  31 */     Log.debug("Creating FBO " + image.getWidth() + "x" + image.getHeight());
/*     */     
/*  33 */     boolean FBOEnabled = (GLContext.getCapabilities()).GL_EXT_framebuffer_object;
/*  34 */     if (!FBOEnabled) {
/*  35 */       throw new SlickException("Your OpenGL card does not support FBO and hence can't handle the dynamic images required for this application.");
/*     */     }
/*     */     
/*  38 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private void completeCheck() throws SlickException {
/*  43 */     int framebuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*  44 */     switch (framebuffer) {
/*     */       case 36053:
/*     */         return;
/*     */       case 36054:
/*  48 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
/*     */       
/*     */       case 36055:
/*  51 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
/*     */       
/*     */       case 36057:
/*  54 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
/*     */       
/*     */       case 36059:
/*  57 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
/*     */       
/*     */       case 36058:
/*  60 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
/*     */       
/*     */       case 36060:
/*  63 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
/*     */     } 
/*     */     
/*  66 */     throw new SlickException("Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() throws SlickException {
/*  72 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/*  73 */     EXTFramebufferObject.glGenFramebuffersEXT(buffer);
/*  74 */     this.FBO = buffer.get();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  79 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*     */       
/*  81 */       EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
/*  82 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, tex
/*     */           
/*  84 */           .getTextureID(), 0);
/*     */       
/*  86 */       completeCheck();
/*  87 */       unbind();
/*     */ 
/*     */       
/*  90 */       clear();
/*  91 */       flush();
/*     */ 
/*     */       
/*  94 */       drawImage(this.image, 0.0F, 0.0F);
/*  95 */       this.image.setTexture(tex);
/*     */     }
/*  97 */     catch (Exception e) {
/*  98 */       throw new SlickException("Failed to create new texture for FBO");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void bind() {
/* 104 */     EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
/* 105 */     GL11.glReadBuffer(36064);
/*     */   }
/*     */ 
/*     */   
/*     */   private void unbind() {
/* 110 */     EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
/* 111 */     GL11.glReadBuffer(1029);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void disable() {
/* 116 */     GL.flush();
/*     */     
/* 118 */     unbind();
/* 119 */     GL11.glPopClientAttrib();
/* 120 */     GL11.glPopAttrib();
/* 121 */     GL11.glMatrixMode(5888);
/* 122 */     GL11.glPopMatrix();
/* 123 */     GL11.glMatrixMode(5889);
/* 124 */     GL11.glPopMatrix();
/* 125 */     GL11.glMatrixMode(5888);
/*     */     
/* 127 */     SlickCallable.leaveSafeBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enable() {
/* 132 */     if (!this.valid) {
/* 133 */       throw new RuntimeException("Attempt to use a destroy()ed offscreen graphics context.");
/*     */     }
/* 135 */     SlickCallable.enterSafeBlock();
/*     */     
/* 137 */     GL11.glPushAttrib(1048575);
/* 138 */     GL11.glPushClientAttrib(-1);
/* 139 */     GL11.glMatrixMode(5889);
/* 140 */     GL11.glPushMatrix();
/* 141 */     GL11.glMatrixMode(5888);
/* 142 */     GL11.glPushMatrix();
/*     */     
/* 144 */     bind();
/* 145 */     initGL();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initGL() {
/* 150 */     GL11.glEnable(3553);
/* 151 */     GL11.glShadeModel(7425);
/* 152 */     GL11.glDisable(2929);
/* 153 */     GL11.glDisable(2896);
/*     */     
/* 155 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 156 */     GL11.glClearDepth(1.0D);
/*     */     
/* 158 */     GL11.glEnable(3042);
/* 159 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 161 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 162 */     GL11.glMatrixMode(5888);
/* 163 */     GL11.glLoadIdentity();
/*     */     
/* 165 */     enterOrtho();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enterOrtho() {
/* 170 */     GL11.glMatrixMode(5889);
/* 171 */     GL11.glLoadIdentity();
/* 172 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 173 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 178 */     super.destroy();
/*     */     
/* 180 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/* 181 */     buffer.put(this.FBO);
/* 182 */     buffer.flip();
/*     */     
/* 184 */     EXTFramebufferObject.glDeleteFramebuffersEXT(buffer);
/* 185 */     this.valid = false;
/*     */   }
/*     */   
/*     */   public void flush() {
/* 189 */     super.flush();
/*     */     
/* 191 */     this.image.flushPixelData();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\pbuffer\FBOGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */