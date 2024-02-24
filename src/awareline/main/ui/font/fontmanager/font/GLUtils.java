/*    */ package awareline.main.ui.font.fontmanager.font;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public final class GLUtils
/*    */ {
/*    */   public static void startScissor(int x, int y, int width, int height) {
/* 11 */     int scaleFactor = Client.instance.getScaledResolution().getScaleFactor();
/*    */     
/* 13 */     GL11.glPushMatrix();
/* 14 */     GL11.glEnable(3089);
/* 15 */     GL11.glScissor(x * scaleFactor, (Minecraft.getMinecraft()).displayHeight - (y + height) * scaleFactor, width * scaleFactor, height * scaleFactor);
/*    */   }
/*    */   
/*    */   public static void stopScissor() {
/* 19 */     GL11.glDisable(3089);
/* 20 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public static void scale(double x, double y, double z) {
/* 24 */     GL11.glScaled(x, y, z);
/*    */   }
/*    */   
/*    */   public static void pushMatrix() {
/* 28 */     GL11.glPushMatrix();
/*    */   }
/*    */   
/*    */   public static void popMatrix() {
/* 32 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public static void enable(int cap) {
/* 36 */     GL11.glEnable(cap);
/*    */   }
/*    */   
/*    */   public static void disable(int cap) {
/* 40 */     GL11.glDisable(cap);
/*    */   }
/*    */   
/*    */   public static void blendFunc(int sFactor, int dFactor) {
/* 44 */     GL11.glBlendFunc(sFactor, dFactor);
/*    */   }
/*    */   
/*    */   public static void translated(double x, double y, double z) {
/* 48 */     GL11.glTranslated(x, y, z);
/*    */   }
/*    */   
/*    */   public static void rotated(double angle, double x, double y, double z) {
/* 52 */     GL11.glRotated(angle, x, y, z);
/*    */   }
/*    */   
/*    */   public static void depthMask(boolean flag) {
/* 56 */     GL11.glDepthMask(flag);
/*    */   }
/*    */   
/*    */   public static void color(int r, int g, int b) {
/* 60 */     color(r, g, b, 255);
/*    */   }
/*    */   
/*    */   public static void color(int r, int g, int b, int a) {
/* 64 */     GlStateManager.color(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
/*    */   }
/*    */   
/*    */   public static void color(int hex) {
/* 68 */     GlStateManager.color((hex >> 16 & 0xFF) / 255.0F, (hex >> 8 & 0xFF) / 255.0F, (hex & 0xFF) / 255.0F, (hex >> 24 & 0xFF) / 255.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void resetColor() {
/* 76 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\font\GLUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */