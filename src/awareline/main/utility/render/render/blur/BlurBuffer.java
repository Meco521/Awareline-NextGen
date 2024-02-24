/*    */ package awareline.main.utility.render.render.blur;
/*    */ 
/*    */ import awareline.main.utility.render.render.RenderUtils;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlurBuffer
/*    */ {
/* 20 */   public static Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static void blurArea(float x, float y, float width, float height, float radius) {
/* 23 */     if (!mc.gameSettings.ofFastRender) {
/* 24 */       StencilUtil.initStencilToWrite();
/* 25 */       GL11.glPushMatrix();
/* 26 */       GlStateManager.enableBlend();
/* 27 */       GL11.glEnable(3042);
/* 28 */       GL11.glDisable(3553);
/* 29 */       GL11.glBlendFunc(770, 771);
/* 30 */       GL11.glEnable(2848);
/* 31 */       GL11.glPushMatrix();
/* 32 */       RenderUtils.color((new Color(-2)).getRGB());
/* 33 */       GL11.glBegin(7);
/* 34 */       GL11.glVertex2d(width, y);
/* 35 */       GL11.glVertex2d(x, y);
/* 36 */       GL11.glVertex2d(x, height);
/* 37 */       GL11.glVertex2d(width, height);
/* 38 */       GL11.glEnd();
/* 39 */       GL11.glPopMatrix();
/* 40 */       GL11.glEnable(3553);
/* 41 */       GL11.glDisable(3042);
/* 42 */       GL11.glDisable(2848);
/* 43 */       GlStateManager.disableBlend();
/* 44 */       GL11.glPopMatrix();
/* 45 */       StencilUtil.readStencilBuffer(1);
/* 46 */       GaussianBlur.renderBlur(radius);
/* 47 */       StencilUtil.uninitStencilBuffer();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void blurRoundArea(float x, float y, float width, float height, int radius, float blurRadius) {
/* 52 */     if (!mc.gameSettings.ofFastRender) {
/* 53 */       StencilUtil.initStencilToWrite();
/* 54 */       float f3 = ((new Color(-2)).getRGB() >> 24 & 0xFF) / 255.0F;
/* 55 */       float f = ((new Color(-2)).getRGB() >> 16 & 0xFF) / 255.0F;
/* 56 */       float f1 = ((new Color(-2)).getRGB() >> 8 & 0xFF) / 255.0F;
/* 57 */       float f2 = ((new Color(-2)).getRGB() & 0xFF) / 255.0F;
/* 58 */       if (x < width) {
/* 59 */         float f4 = x + width;
/* 60 */         width = x;
/* 61 */         x = f4 - width;
/*    */       } 
/* 63 */       if (y < height) {
/* 64 */         float f5 = y + height;
/* 65 */         height = y;
/* 66 */         y = f5 - height;
/*    */       } 
/* 68 */       float[][] corners = { { width + radius, y - radius, 270.0F }, { x - radius, y - radius, 360.0F }, { x - radius, height + radius, 90.0F }, { width + radius, height + radius, 180.0F } };
/* 69 */       GlStateManager.enableBlend();
/* 70 */       GlStateManager.disableTexture2D();
/* 71 */       GlStateManager.alphaFunc(516, 0.003921569F);
/* 72 */       GlStateManager.color(f, f1, f2, f3);
/* 73 */       Tessellator tessellator = Tessellator.getInstance();
/* 74 */       WorldRenderer renderer = tessellator.getWorldRenderer();
/* 75 */       renderer.begin(9, DefaultVertexFormats.POSITION);
/* 76 */       for (float[] c : corners) {
/* 77 */         for (int i = 0; i <= 6; i++) {
/* 78 */           double anglerad = Math.PI * (c[2] + i * 90.0F / 6.0F) / 180.0D;
/* 79 */           renderer.pos(c[0] + Math.sin(anglerad) * radius, c[1] + Math.cos(anglerad) * radius, 0.0D).endVertex();
/*    */         } 
/*    */       } 
/* 82 */       tessellator.draw();
/* 83 */       GlStateManager.disableBlend();
/* 84 */       GlStateManager.enableTexture2D();
/* 85 */       StencilUtil.readStencilBuffer(1);
/* 86 */       GaussianBlur.renderBlur(blurRadius);
/* 87 */       StencilUtil.uninitStencilBuffer();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\blur\BlurBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */