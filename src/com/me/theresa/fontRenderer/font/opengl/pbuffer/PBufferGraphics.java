/*     */ package com.me.theresa.fontRenderer.font.opengl.pbuffer;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.Graphics;
/*     */ import com.me.theresa.fontRenderer.font.Image;
/*     */ import com.me.theresa.fontRenderer.font.SlickException;
/*     */ import com.me.theresa.fontRenderer.font.log.Log;
/*     */ import com.me.theresa.fontRenderer.font.opengl.InternalTextureLoader;
/*     */ import com.me.theresa.fontRenderer.font.opengl.SlickCallable;
/*     */ import com.me.theresa.fontRenderer.font.opengl.Texture;
/*     */ import com.me.theresa.fontRenderer.font.opengl.TextureImpl;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.Pbuffer;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ import org.lwjgl.opengl.RenderTexture;
/*     */ 
/*     */ public class PBufferGraphics
/*     */   extends Graphics {
/*     */   private Pbuffer pbuffer;
/*     */   private final Image image;
/*     */   
/*     */   public PBufferGraphics(Image image) throws SlickException {
/*  24 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  25 */     this.image = image;
/*     */     
/*  27 */     Log.debug("Creating pbuffer(rtt) " + image.getWidth() + "x" + image.getHeight());
/*  28 */     if ((Pbuffer.getCapabilities() & 0x1) == 0) {
/*  29 */       throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
/*     */     }
/*  31 */     if ((Pbuffer.getCapabilities() & 0x2) == 0) {
/*  32 */       throw new SlickException("Your OpenGL card does not support Render-To-Texture and hence can't handle the dynamic images required for this application.");
/*     */     }
/*     */     
/*  35 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() throws SlickException {
/*     */     try {
/*  41 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*     */       
/*  43 */       RenderTexture rt = new RenderTexture(false, true, false, false, 8314, 0);
/*  44 */       this.pbuffer = new Pbuffer(this.screenWidth, this.screenHeight, new PixelFormat(8, 0, 0), rt, null);
/*     */ 
/*     */       
/*  47 */       this.pbuffer.makeCurrent();
/*     */       
/*  49 */       initGL();
/*  50 */       GL.glBindTexture(3553, tex.getTextureID());
/*  51 */       this.pbuffer.releaseTexImage(8323);
/*  52 */       this.image.draw(0.0F, 0.0F);
/*  53 */       this.image.setTexture(tex);
/*     */       
/*  55 */       Display.makeCurrent();
/*  56 */     } catch (Exception e) {
/*  57 */       Log.error(e);
/*  58 */       throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void disable() {
/*  64 */     GL.flush();
/*     */ 
/*     */     
/*  67 */     GL.glBindTexture(3553, this.image.getTexture().getTextureID());
/*  68 */     this.pbuffer.bindTexImage(8323);
/*     */     
/*     */     try {
/*  71 */       Display.makeCurrent();
/*  72 */     } catch (LWJGLException e) {
/*  73 */       Log.error((Throwable)e);
/*     */     } 
/*     */     
/*  76 */     SlickCallable.leaveSafeBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enable() {
/*  81 */     SlickCallable.enterSafeBlock();
/*     */     
/*     */     try {
/*  84 */       if (this.pbuffer.isBufferLost()) {
/*  85 */         this.pbuffer.destroy();
/*  86 */         init();
/*     */       } 
/*     */       
/*  89 */       this.pbuffer.makeCurrent();
/*  90 */     } catch (Exception e) {
/*  91 */       Log.error("Failed to recreate the PBuffer");
/*  92 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/*  96 */     GL.glBindTexture(3553, this.image.getTexture().getTextureID());
/*  97 */     this.pbuffer.releaseTexImage(8323);
/*  98 */     TextureImpl.unbind();
/*  99 */     initGL();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initGL() {
/* 104 */     GL11.glEnable(3553);
/* 105 */     GL11.glShadeModel(7425);
/* 106 */     GL11.glDisable(2929);
/* 107 */     GL11.glDisable(2896);
/*     */     
/* 109 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 110 */     GL11.glClearDepth(1.0D);
/*     */     
/* 112 */     GL11.glEnable(3042);
/* 113 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 115 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 116 */     GL11.glMatrixMode(5888);
/* 117 */     GL11.glLoadIdentity();
/*     */     
/* 119 */     enterOrtho();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enterOrtho() {
/* 124 */     GL11.glMatrixMode(5889);
/* 125 */     GL11.glLoadIdentity();
/* 126 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 127 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 132 */     super.destroy();
/*     */     
/* 134 */     this.pbuffer.destroy();
/*     */   }
/*     */   
/*     */   public void flush() {
/* 138 */     super.flush();
/*     */     
/* 140 */     this.image.flushPixelData();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\pbuffer\PBufferGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */