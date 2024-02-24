/*    */ package awareline.main.utility.render.gl.flux;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public enum GLUtils {
/*    */   public final Minecraft mc;
/*  9 */   INSTANCE;
/*    */   GLUtils() {
/* 11 */     this.mc = Minecraft.getMinecraft();
/*    */   }
/*    */   public static void startSmooth() {
/* 14 */     GL11.glEnable(2848);
/* 15 */     GL11.glEnable(2881);
/* 16 */     GL11.glEnable(2832);
/* 17 */     GL11.glEnable(3042);
/* 18 */     GL11.glBlendFunc(770, 771);
/* 19 */     GL11.glHint(3154, 4354);
/* 20 */     GL11.glHint(3155, 4354);
/* 21 */     GL11.glHint(3153, 4354);
/*    */   }
/*    */   
/*    */   public static void endSmooth() {
/* 25 */     GL11.glDisable(2848);
/* 26 */     GL11.glDisable(2881);
/* 27 */     GL11.glEnable(2832);
/*    */   }
/*    */   
/*    */   public void rescale(double factor) {
/* 31 */     rescale(this.mc.displayWidth / factor, this.mc.displayHeight / factor);
/*    */   }
/*    */   
/*    */   public void rescaleMC() {
/* 35 */     ScaledResolution resolution = new ScaledResolution(this.mc);
/* 36 */     rescale((this.mc.displayWidth / resolution.getScaleFactor()), (this.mc.displayHeight / resolution.getScaleFactor()));
/*    */   }
/*    */   
/*    */   public void rescale(double width, double height) {
/* 40 */     GlStateManager.clear(256);
/* 41 */     GlStateManager.matrixMode(5889);
/* 42 */     GlStateManager.loadIdentity();
/* 43 */     GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
/* 44 */     GlStateManager.matrixMode(5888);
/* 45 */     GlStateManager.loadIdentity();
/* 46 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\gl\flux\GLUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */