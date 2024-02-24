/*    */ package awareline.main.utility.render.gl;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class GLUtil
/*    */ {
/*    */   public static void enableDepth() {
/* 10 */     GlStateManager.enableDepth();
/* 11 */     GlStateManager.depthMask(true);
/*    */   }
/*    */   
/*    */   public static void disableDepth() {
/* 15 */     GlStateManager.disableDepth();
/* 16 */     GlStateManager.depthMask(false);
/*    */   }
/*    */   
/* 19 */   public static int[] enabledCaps = new int[32];
/*    */   
/*    */   public static void enableCaps(int... caps) {
/* 22 */     for (int cap : caps) GL11.glEnable(cap); 
/* 23 */     enabledCaps = caps;
/*    */   }
/*    */   
/*    */   public static void disableCaps() {
/* 27 */     for (int cap : enabledCaps) GL11.glDisable(cap); 
/*    */   }
/*    */   
/*    */   public static void startBlend() {
/* 31 */     GlStateManager.enableBlend();
/* 32 */     GlStateManager.blendFunc(770, 771);
/*    */   }
/*    */   
/*    */   public static void endBlend() {
/* 36 */     GlStateManager.disableBlend();
/*    */   }
/*    */   
/*    */   public static void setup2DRendering(boolean blend) {
/* 40 */     if (blend) {
/* 41 */       startBlend();
/*    */     }
/* 43 */     GlStateManager.disableTexture2D();
/*    */   }
/*    */   
/*    */   public static void setup2DRendering() {
/* 47 */     setup2DRendering(true);
/*    */   }
/*    */   
/*    */   public static void end2DRendering() {
/* 51 */     GlStateManager.enableTexture2D();
/* 52 */     endBlend();
/*    */   }
/*    */   
/*    */   public static void startRotate(float x, float y, float rotate) {
/* 56 */     GlStateManager.pushMatrix();
/* 57 */     GlStateManager.translate(x, y, 0.0F);
/* 58 */     GlStateManager.rotate(rotate, 0.0F, 0.0F, -1.0F);
/* 59 */     GlStateManager.translate(-x, -y, 0.0F);
/*    */   }
/*    */   
/*    */   public static void endRotate() {
/* 63 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\gl\GLUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */