/*    */ package awareline.main.utility.render.render;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import org.lwjgl.opengl.EXTFramebufferObject;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StencilUtils
/*    */ {
/* 17 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*    */ 
/*    */   
/*    */   public void StencilUtil() {}
/*    */   
/*    */   public static void dispose() {
/* 23 */     GL11.glDisable(2960);
/* 24 */     GlStateManager.disableAlpha();
/* 25 */     GlStateManager.disableBlend();
/*    */   }
/*    */   
/*    */   public static void erase(boolean invert) {
/* 29 */     GL11.glStencilFunc(invert ? 514 : 517, 1, 65535);
/* 30 */     GL11.glStencilOp(7680, 7680, 7681);
/* 31 */     GlStateManager.colorMask(true, true, true, true);
/* 32 */     GlStateManager.enableAlpha();
/* 33 */     GlStateManager.enableBlend();
/* 34 */     GL11.glAlphaFunc(516, 0.0F);
/*    */   }
/*    */   
/*    */   public static void write(boolean renderClipLayer) {
/* 38 */     checkSetupFBO(mc.getFramebuffer());
/* 39 */     GL11.glClearStencil(0);
/* 40 */     GL11.glClear(1024);
/* 41 */     GL11.glEnable(2960);
/* 42 */     GL11.glStencilFunc(519, 1, 65535);
/* 43 */     GL11.glStencilOp(7680, 7680, 7681);
/* 44 */     if (!renderClipLayer) {
/* 45 */       GlStateManager.colorMask(false, false, false, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void checkSetupFBO(Framebuffer framebuffer) {
/* 50 */     if (framebuffer != null && framebuffer.depthBuffer > -1) {
/* 51 */       setupFBO(framebuffer);
/* 52 */       framebuffer.depthBuffer = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setupFBO(Framebuffer framebuffer) {
/* 57 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
/* 58 */     int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 59 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
/* 60 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/* 61 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
/* 62 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
/*    */   }
/*    */   
/*    */   public static void initStencil() {
/* 66 */     initStencil(mc.getFramebuffer());
/*    */   }
/*    */   
/*    */   public static void initStencil(Framebuffer framebuffer) {
/* 70 */     framebuffer.bindFramebuffer(false);
/* 71 */     checkSetupFBO(framebuffer);
/* 72 */     GL11.glClear(1024);
/* 73 */     GL11.glEnable(2960);
/*    */   }
/*    */   
/*    */   public static void bindWriteStencilBuffer() {
/* 77 */     GL11.glStencilFunc(519, 1, 1);
/* 78 */     GL11.glStencilOp(7681, 7681, 7681);
/* 79 */     GL11.glColorMask(false, false, false, false);
/*    */   }
/*    */   
/*    */   public static void bindReadStencilBuffer(int ref) {
/* 83 */     GL11.glColorMask(true, true, true, true);
/* 84 */     GL11.glStencilFunc(514, ref, 1);
/* 85 */     GL11.glStencilOp(7680, 7680, 7680);
/*    */   }
/*    */   
/*    */   public static void uninitStencilBuffer() {
/* 89 */     GL11.glDisable(2960);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\StencilUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */