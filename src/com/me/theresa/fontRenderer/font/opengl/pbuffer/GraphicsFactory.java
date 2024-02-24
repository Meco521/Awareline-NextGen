/*    */ package com.me.theresa.fontRenderer.font.opengl.pbuffer;
/*    */ 
/*    */ import com.me.theresa.fontRenderer.font.Graphics;
/*    */ import com.me.theresa.fontRenderer.font.Image;
/*    */ import com.me.theresa.fontRenderer.font.SlickException;
/*    */ import com.me.theresa.fontRenderer.font.log.Log;
/*    */ import java.util.HashMap;
/*    */ import org.lwjgl.opengl.GLContext;
/*    */ import org.lwjgl.opengl.Pbuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GraphicsFactory
/*    */ {
/* 15 */   private static final HashMap graphics = new HashMap<>();
/*    */   
/*    */   private static boolean pbuffer = true;
/*    */   
/*    */   private static boolean pbufferRT = true;
/*    */   
/*    */   private static boolean fbo = true;
/*    */   
/*    */   private static boolean init = false;
/*    */ 
/*    */   
/*    */   private static void init() throws SlickException {
/* 27 */     init = true;
/*    */     
/* 29 */     if (fbo) {
/* 30 */       fbo = (GLContext.getCapabilities()).GL_EXT_framebuffer_object;
/*    */     }
/* 32 */     pbuffer = ((Pbuffer.getCapabilities() & 0x1) != 0);
/* 33 */     pbufferRT = ((Pbuffer.getCapabilities() & 0x2) != 0);
/*    */     
/* 35 */     if (!fbo && !pbuffer && !pbufferRT) {
/* 36 */       throw new SlickException("Your OpenGL card does not support offscreen buffers and hence can't handle the dynamic images required for this application.");
/*    */     }
/*    */     
/* 39 */     Log.info("Offscreen Buffers FBO=" + fbo + " PBUFFER=" + pbuffer + " PBUFFERRT=" + pbufferRT);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setUseFBO(boolean useFBO) {
/* 44 */     fbo = useFBO;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean usingFBO() {
/* 49 */     return fbo;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean usingPBuffer() {
/* 54 */     return (!fbo && pbuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Graphics getGraphicsForImage(Image image) throws SlickException {
/* 59 */     Graphics g = (Graphics)graphics.get(image.getTexture());
/*    */     
/* 61 */     if (g == null) {
/* 62 */       g = createGraphics(image);
/* 63 */       graphics.put(image.getTexture(), g);
/*    */     } 
/*    */     
/* 66 */     return g;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void releaseGraphicsForImage(Image image) {
/* 71 */     Graphics g = (Graphics)graphics.remove(image.getTexture());
/*    */     
/* 73 */     if (g != null) {
/* 74 */       g.destroy();
/*    */     }
/*    */   }
/*    */   
/*    */   private static Graphics createGraphics(Image image) throws SlickException {
/* 79 */     init();
/*    */     
/* 81 */     if (fbo) {
/*    */       try {
/* 83 */         return new FBOGraphics(image);
/* 84 */       } catch (Exception e) {
/* 85 */         fbo = false;
/* 86 */         Log.warn("FBO failed in use, falling back to PBuffer");
/*    */       } 
/*    */     }
/*    */     
/* 90 */     if (pbuffer) {
/* 91 */       if (pbufferRT) {
/* 92 */         return new PBufferGraphics(image);
/*    */       }
/* 94 */       return new PBufferUniqueGraphics(image);
/*    */     } 
/*    */ 
/*    */     
/* 98 */     throw new SlickException("Failed to create offscreen buffer even though the card reports it's possible");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\pbuffer\GraphicsFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */