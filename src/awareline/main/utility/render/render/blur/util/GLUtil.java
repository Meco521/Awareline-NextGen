/*    */ package awareline.main.utility.render.render.blur.util;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GLUtil
/*    */ {
/*    */   public static void startBlend() {
/* 14 */     GlStateManager.enableBlend();
/* 15 */     GlStateManager.blendFunc(770, 771);
/*    */   }
/*    */   
/*    */   public static void endBlend() {
/* 19 */     GlStateManager.disableBlend();
/*    */   }
/*    */   
/*    */   public static void render(int mode2, Runnable render) {
/* 23 */     GL11.glBegin(mode2);
/* 24 */     render.run();
/* 25 */     GL11.glEnd();
/*    */   }
/*    */   
/*    */   public static void setup2DRendering(Runnable f) {
/* 29 */     GL11.glEnable(3042);
/* 30 */     GL11.glBlendFunc(770, 771);
/* 31 */     GL11.glDisable(3553);
/* 32 */     f.run();
/* 33 */     GL11.glEnable(3553);
/* 34 */     GlStateManager.disableBlend();
/*    */   }
/*    */   
/*    */   public static void setup2DRendering() {
/* 38 */     setup2DRendering(true);
/*    */   }
/*    */   
/*    */   public static void setup2DRendering(boolean blend) {
/* 42 */     if (blend) {
/* 43 */       startBlend();
/*    */     }
/* 45 */     GlStateManager.disableTexture2D();
/*    */   }
/*    */   
/*    */   public static void end2DRendering() {
/* 49 */     GlStateManager.enableTexture2D();
/* 50 */     endBlend();
/*    */   }
/*    */   
/*    */   public static void rotate(float x, float y, float rotate, Runnable f) {
/* 54 */     GlStateManager.pushMatrix();
/* 55 */     GlStateManager.translate(x, y, 0.0F);
/* 56 */     GlStateManager.rotate(rotate, 0.0F, 0.0F, -1.0F);
/* 57 */     GlStateManager.translate(-x, -y, 0.0F);
/* 58 */     f.run();
/* 59 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\blu\\util\GLUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */