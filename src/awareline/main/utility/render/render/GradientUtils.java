/*    */ package awareline.main.utility.render.render;
/*    */ 
/*    */ import awareline.main.utility.render.render.shader.ShaderUtils;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GradientUtils
/*    */ {
/* 13 */   private static final Minecraft mc = Minecraft.getMinecraft();
/* 14 */   private static final ShaderUtils gradientMaskShader = new ShaderUtils("gradientMask");
/* 15 */   private static final ShaderUtils gradientShader = new ShaderUtils("gradient");
/*    */   
/*    */   public static void drawGradient(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
/* 18 */     ScaledResolution sr = new ScaledResolution(mc);
/* 19 */     RenderUtils.setAlphaLimit(0.0F);
/* 20 */     RenderUtils.resetColor();
/* 21 */     GLUtils.startBlend();
/* 22 */     gradientShader.init();
/* 23 */     gradientShader.setUniformf("location", new float[] { x * sr.getScaleFactor(), (Minecraft.getMinecraft()).displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor() });
/* 24 */     gradientShader.setUniformf("rectSize", new float[] { width * sr.getScaleFactor(), height * sr.getScaleFactor() });
/* 25 */     gradientShader.setUniformf("color1", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F, alpha });
/* 26 */     gradientShader.setUniformf("color2", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F, alpha });
/* 27 */     gradientShader.setUniformf("color3", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F, alpha });
/* 28 */     gradientShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F, alpha });
/* 29 */     ShaderUtils.drawQuads(x, y, width, height);
/* 30 */     gradientShader.unload();
/* 31 */     GLUtils.endBlend();
/*    */   }
/*    */   
/*    */   public static void drawGradient(float x, float y, float width, float height, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
/* 35 */     ScaledResolution sr = new ScaledResolution(mc);
/* 36 */     RenderUtils.resetColor();
/* 37 */     GLUtils.startBlend();
/* 38 */     gradientShader.init();
/* 39 */     gradientShader.setUniformf("location", new float[] { x * sr.getScaleFactor(), (Minecraft.getMinecraft()).displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor() });
/* 40 */     gradientShader.setUniformf("rectSize", new float[] { width * sr.getScaleFactor(), height * sr.getScaleFactor() });
/* 41 */     gradientShader.setUniformf("color1", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F, bottomLeft.getAlpha() / 255.0F });
/* 42 */     gradientShader.setUniformf("color2", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F, topLeft.getAlpha() / 255.0F });
/* 43 */     gradientShader.setUniformf("color3", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F, bottomRight.getAlpha() / 255.0F });
/* 44 */     gradientShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F, topRight.getAlpha() / 255.0F });
/* 45 */     ShaderUtils.drawQuads(x, y, width, height);
/* 46 */     gradientShader.unload();
/* 47 */     GLUtils.endBlend();
/*    */   }
/*    */   
/*    */   public static void drawGradientLR(float x, float y, float width, float height, float alpha, Color left, Color right) {
/* 51 */     drawGradient(x, y, width, height, alpha, left, left, right, right);
/*    */   }
/*    */   
/*    */   public static void drawGradientTB(float x, float y, float width, float height, float alpha, Color top, Color bottom) {
/* 55 */     drawGradient(x, y, width, height, alpha, bottom, top, bottom, top);
/*    */   }
/*    */   
/*    */   public static void applyGradientHorizontal(float x, float y, float width, float height, float alpha, Color left, Color right, Runnable content) {
/* 59 */     applyGradient(x, y, width, height, alpha, left, left, right, right, content);
/*    */   }
/*    */   
/*    */   public static void applyGradientVertical(float x, float y, float width, float height, float alpha, Color top, Color bottom, Runnable content) {
/* 63 */     applyGradient(x, y, width, height, alpha, bottom, top, bottom, top, content);
/*    */   }
/*    */   
/*    */   public static void applyGradientCornerRL(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topRight, Runnable content) {
/* 67 */     Color mixedColor = ColorUtils.interpolateColorC(topRight, bottomLeft, 0.5F);
/* 68 */     applyGradient(x, y, width, height, alpha, bottomLeft, mixedColor, mixedColor, topRight, content);
/*    */   }
/*    */   
/*    */   public static void applyGradientCornerLR(float x, float y, float width, float height, float alpha, Color bottomRight, Color topLeft, Runnable content) {
/* 72 */     Color mixedColor = ColorUtils.interpolateColorC(bottomRight, topLeft, 0.5F);
/* 73 */     applyGradient(x, y, width, height, alpha, mixedColor, topLeft, bottomRight, mixedColor, content);
/*    */   }
/*    */   
/*    */   public static void applyGradient(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight, Runnable content) {
/* 77 */     RenderUtils.resetColor();
/* 78 */     GLUtils.startBlend();
/* 79 */     gradientMaskShader.init();
/* 80 */     ScaledResolution sr = new ScaledResolution(mc);
/* 81 */     gradientMaskShader.setUniformf("location", new float[] { x * sr.getScaleFactor(), (Minecraft.getMinecraft()).displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor() });
/* 82 */     gradientMaskShader.setUniformf("rectSize", new float[] { width * sr.getScaleFactor(), height * sr.getScaleFactor() });
/* 83 */     gradientMaskShader.setUniformf("alpha", new float[] { alpha });
/* 84 */     gradientMaskShader.setUniformi("tex", new int[] { 0 });
/* 85 */     gradientMaskShader.setUniformf("color1", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F });
/* 86 */     gradientMaskShader.setUniformf("color2", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F });
/* 87 */     gradientMaskShader.setUniformf("color3", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F });
/* 88 */     gradientMaskShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F });
/* 89 */     content.run();
/* 90 */     gradientMaskShader.unload();
/* 91 */     GLUtils.endBlend();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\GradientUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */