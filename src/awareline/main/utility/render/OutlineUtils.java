/*    */ package awareline.main.utility.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import org.lwjgl.opengl.EXTFramebufferObject;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class OutlineUtils
/*    */ {
/*    */   public static void renderOne() {
/* 13 */     checkSetupFBO();
/* 14 */     GL11.glPushAttrib(1048575);
/* 15 */     GL11.glDisable(3008);
/* 16 */     GL11.glDisable(3553);
/* 17 */     GL11.glDisable(2896);
/* 18 */     GL11.glEnable(3042);
/* 19 */     GL11.glBlendFunc(770, 771);
/* 20 */     GL11.glLineWidth(3.0F);
/* 21 */     GL11.glEnable(2848);
/* 22 */     GL11.glEnable(2960);
/* 23 */     GL11.glClear(1024);
/* 24 */     GL11.glClearStencil(15);
/* 25 */     GL11.glStencilFunc(512, 1, 15);
/* 26 */     GL11.glStencilOp(7681, 7681, 7681);
/* 27 */     GL11.glPolygonMode(1032, 6913);
/*    */   }
/*    */   
/*    */   public static void renderTwo() {
/* 31 */     GL11.glStencilFunc(512, 0, 15);
/* 32 */     GL11.glStencilOp(7681, 7681, 7681);
/* 33 */     GL11.glPolygonMode(1032, 6914);
/*    */   }
/*    */   
/*    */   public static void renderThree() {
/* 37 */     GL11.glStencilFunc(514, 1, 15);
/* 38 */     GL11.glStencilOp(7680, 7680, 7680);
/* 39 */     GL11.glPolygonMode(1032, 6913);
/*    */   }
/*    */   
/*    */   public static void renderFour() {
/* 43 */     setColor(new Color(255, 255, 255));
/* 44 */     GL11.glDepthMask(false);
/* 45 */     GL11.glDisable(2929);
/* 46 */     GL11.glEnable(10754);
/* 47 */     GL11.glPolygonOffset(1.0F, -2000000.0F);
/* 48 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/*    */   }
/*    */   
/*    */   public static void renderFive() {
/* 52 */     GL11.glPolygonOffset(1.0F, 2000000.0F);
/* 53 */     GL11.glDisable(10754);
/* 54 */     GL11.glEnable(2929);
/* 55 */     GL11.glDepthMask(true);
/* 56 */     GL11.glDisable(2960);
/* 57 */     GL11.glDisable(2848);
/* 58 */     GL11.glHint(3154, 4352);
/* 59 */     GL11.glEnable(3042);
/* 60 */     GL11.glEnable(2896);
/* 61 */     GL11.glEnable(3553);
/* 62 */     GL11.glEnable(3008);
/* 63 */     GL11.glPopAttrib();
/*    */   }
/*    */   
/*    */   public static void setColor(Color c) {
/* 67 */     GL11.glColor4d((c.getRed() / 255.0F), (c.getGreen() / 255.0F), (c.getBlue() / 255.0F), (c.getAlpha() / 255.0F));
/*    */   }
/*    */   
/*    */   public static void checkSetupFBO() {
/* 71 */     Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
/* 72 */     if (fbo != null && fbo.depthBuffer > -1) {
/* 73 */       setupFBO(fbo);
/* 74 */       fbo.depthBuffer = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setupFBO(Framebuffer fbo) {
/* 79 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
/* 80 */     int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 81 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
/* 82 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, (Minecraft.getMinecraft()).displayWidth, (Minecraft.getMinecraft()).displayHeight);
/* 83 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
/* 84 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\OutlineUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */