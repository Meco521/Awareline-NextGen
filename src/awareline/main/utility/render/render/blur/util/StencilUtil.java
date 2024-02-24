/*    */ package awareline.main.utility.render.render.blur.util;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
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
/*    */ public class StencilUtil
/*    */ {
/* 16 */   static Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static void checkSetupFBO(Framebuffer framebuffer) {
/* 19 */     if (framebuffer != null && framebuffer.depthBuffer > -1) {
/* 20 */       setupFBO(framebuffer);
/* 21 */       framebuffer.depthBuffer = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setupFBO(Framebuffer framebuffer) {
/* 26 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
/* 27 */     int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 28 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
/* 29 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/* 30 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
/* 31 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
/*    */   }
/*    */   
/*    */   public static void initStencilToWrite() {
/* 35 */     mc.getFramebuffer().bindFramebuffer(false);
/* 36 */     checkSetupFBO(mc.getFramebuffer());
/* 37 */     GL11.glClear(1024);
/* 38 */     GL11.glEnable(2960);
/* 39 */     GL11.glStencilFunc(519, 1, 1);
/* 40 */     GL11.glStencilOp(7681, 7681, 7681);
/* 41 */     GL11.glColorMask(false, false, false, false);
/*    */   }
/*    */   
/*    */   public static void readStencilBuffer(int ref) {
/* 45 */     GL11.glColorMask(true, true, true, true);
/* 46 */     GL11.glStencilFunc(514, ref, 1);
/* 47 */     GL11.glStencilOp(7680, 7680, 7680);
/*    */   }
/*    */   
/*    */   public static void uninitStencilBuffer() {
/* 51 */     GL11.glDisable(2960);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\blu\\util\StencilUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */