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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PBufferUniqueGraphics
/*     */   extends Graphics
/*     */ {
/*     */   private Pbuffer pbuffer;
/*     */   private final Image image;
/*     */   
/*     */   public PBufferUniqueGraphics(Image image) throws SlickException {
/*  26 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  27 */     this.image = image;
/*     */     
/*  29 */     Log.debug("Creating pbuffer(unique) " + image.getWidth() + "x" + image.getHeight());
/*  30 */     if ((Pbuffer.getCapabilities() & 0x1) == 0) {
/*  31 */       throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
/*     */     }
/*     */     
/*  34 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() throws SlickException {
/*     */     try {
/*  40 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*     */       
/*  42 */       this.pbuffer = new Pbuffer(this.screenWidth, this.screenHeight, new PixelFormat(8, 0, 0), null, null);
/*     */       
/*  44 */       this.pbuffer.makeCurrent();
/*     */       
/*  46 */       initGL();
/*  47 */       this.image.draw(0.0F, 0.0F);
/*  48 */       GL11.glBindTexture(3553, tex.getTextureID());
/*  49 */       GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, tex
/*  50 */           .getTextureWidth(), tex
/*  51 */           .getTextureHeight(), 0);
/*  52 */       this.image.setTexture(tex);
/*     */       
/*  54 */       Display.makeCurrent();
/*  55 */     } catch (Exception e) {
/*  56 */       Log.error(e);
/*  57 */       throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disable() {
/*  64 */     GL11.glBindTexture(3553, this.image.getTexture().getTextureID());
/*  65 */     GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, this.image
/*  66 */         .getTexture().getTextureWidth(), this.image
/*  67 */         .getTexture().getTextureHeight(), 0);
/*     */     
/*     */     try {
/*  70 */       Display.makeCurrent();
/*  71 */     } catch (LWJGLException e) {
/*  72 */       Log.error((Throwable)e);
/*     */     } 
/*     */     
/*  75 */     SlickCallable.leaveSafeBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enable() {
/*  80 */     SlickCallable.enterSafeBlock();
/*     */     
/*     */     try {
/*  83 */       if (this.pbuffer.isBufferLost()) {
/*  84 */         this.pbuffer.destroy();
/*  85 */         init();
/*     */       } 
/*     */       
/*  88 */       this.pbuffer.makeCurrent();
/*  89 */     } catch (Exception e) {
/*  90 */       Log.error("Failed to recreate the PBuffer");
/*  91 */       Log.error(e);
/*  92 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/*  96 */     TextureImpl.unbind();
/*  97 */     initGL();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initGL() {
/* 102 */     GL11.glEnable(3553);
/* 103 */     GL11.glShadeModel(7425);
/* 104 */     GL11.glDisable(2929);
/* 105 */     GL11.glDisable(2896);
/*     */     
/* 107 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 108 */     GL11.glClearDepth(1.0D);
/*     */     
/* 110 */     GL11.glEnable(3042);
/* 111 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 113 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 114 */     GL11.glMatrixMode(5888);
/* 115 */     GL11.glLoadIdentity();
/*     */     
/* 117 */     enterOrtho();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enterOrtho() {
/* 122 */     GL11.glMatrixMode(5889);
/* 123 */     GL11.glLoadIdentity();
/* 124 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 125 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 130 */     super.destroy();
/*     */     
/* 132 */     this.pbuffer.destroy();
/*     */   }
/*     */   
/*     */   public void flush() {
/* 136 */     super.flush();
/*     */     
/* 138 */     this.image.flushPixelData();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\pbuffer\PBufferUniqueGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */