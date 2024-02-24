/*     */ package awareline.main.utility.render;
/*     */ 
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.render.render.blur.util.GLUtil;
/*     */ import awareline.main.utility.render.render.blur.util.ShaderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoundedUtil
/*     */ {
/*  22 */   public static ShaderUtils roundedShader = new ShaderUtils("client/advancedshader/shaders/roundedRect.frag");
/*  23 */   public static ShaderUtils roundedOutlineShader = new ShaderUtils("client/advancedshader/shaders/roundRectOutline.frag");
/*  24 */   private static final ShaderUtils roundedTexturedShader = new ShaderUtils("client/advancedshader/shaders/roundRectTextured.frag");
/*  25 */   private static final ShaderUtils roundedGradientShader = new ShaderUtils("client/advancedshader/shaders/roundedRectGradient.frag");
/*     */   
/*     */   public static void drawGradientRoundLR(float x, float y, float width, float height, float radius, Color color1, Color color2) {
/*  28 */     drawGradientRound(x, y, width, height, radius, color1, color2, color2, color1);
/*     */   }
/*     */   
/*     */   public static void drawRound(float x, float y, float width, float height, float radius, boolean blur, Color color) {
/*  32 */     RenderUtil.resetColor();
/*  33 */     GLUtil.startBlend();
/*  34 */     GL11.glBlendFunc(770, 771);
/*  35 */     RenderUtil.setAlphaLimit(0.0F);
/*  36 */     roundedShader.init();
/*     */     
/*  38 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
/*  39 */     roundedShader.setUniformi("blur", new int[] { blur ? 1 : 0 });
/*  40 */     roundedShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/*     */     
/*  42 */     ShaderUtil.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/*  43 */     roundedShader.unload();
/*  44 */     GLUtil.endBlend();
/*     */   }
/*     */   
/*     */   public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
/*  48 */     ColorUtils.resetColor();
/*  49 */     GlStateManager.enableBlend();
/*  50 */     GlStateManager.blendFunc(770, 771);
/*  51 */     roundedShader.init();
/*  52 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
/*  53 */     roundedShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/*  54 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/*  55 */     roundedShader.unload();
/*  56 */     GlStateManager.disableBlend();
/*     */   }
/*     */   public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
/*  59 */     RenderUtil.setAlphaLimit(0.0F);
/*  60 */     ColorUtils.resetColor();
/*  61 */     GLUtil.startBlend();
/*  62 */     roundedGradientShader.init();
/*  63 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
/*     */     
/*  65 */     roundedGradientShader.setUniformf("color1", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F, topLeft.getAlpha() / 255.0F });
/*     */     
/*  67 */     roundedGradientShader.setUniformf("color2", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F, bottomLeft.getAlpha() / 255.0F });
/*     */     
/*  69 */     roundedGradientShader.setUniformf("color3", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F, topRight.getAlpha() / 255.0F });
/*     */     
/*  71 */     roundedGradientShader.setUniformf("color4", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F, bottomRight.getAlpha() / 255.0F });
/*  72 */     ShaderUtil.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/*  73 */     roundedGradientShader.unload();
/*  74 */     GLUtil.endBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawGradientRound2(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
/*  79 */     ColorUtils.resetColor();
/*  80 */     GlStateManager.enableBlend();
/*  81 */     GlStateManager.blendFunc(770, 771);
/*  82 */     roundedGradientShader.init();
/*  83 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
/*  84 */     roundedGradientShader.setUniformf("color1", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F, topLeft.getAlpha() / 255.0F });
/*  85 */     roundedGradientShader.setUniformf("color2", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F, bottomRight.getAlpha() / 255.0F });
/*  86 */     roundedGradientShader.setUniformf("color3", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F, bottomLeft.getAlpha() / 255.0F });
/*  87 */     roundedGradientShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F, topRight.getAlpha() / 255.0F });
/*  88 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/*  89 */     roundedGradientShader.unload();
/*  90 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, Color color, Color outlineColor) {
/*  94 */     ColorUtils.resetColor();
/*  95 */     GlStateManager.enableBlend();
/*  96 */     GlStateManager.blendFunc(770, 771);
/*  97 */     roundedOutlineShader.init();
/*  98 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*  99 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
/* 100 */     roundedOutlineShader.setUniformf("outlineThickness", new float[] { outlineThickness * sr.getScaleFactor() });
/* 101 */     roundedOutlineShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/* 102 */     roundedOutlineShader.setUniformf("outlineColor", new float[] { outlineColor.getRed() / 255.0F, outlineColor.getGreen() / 255.0F, outlineColor.getBlue() / 255.0F, outlineColor.getAlpha() / 255.0F });
/* 103 */     ShaderUtils.drawQuads(x - 2.0F + outlineThickness, y - 2.0F + outlineThickness, width + 4.0F + outlineThickness * 2.0F, height + 4.0F + outlineThickness * 2.0F);
/* 104 */     roundedOutlineShader.unload();
/* 105 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawGradientCornerLR(float x, float y, float width, float height, float radius, Color topLeft, Color bottomRight) {
/* 109 */     Color mixedColor = ColorUtils.interpolateColorC(topLeft, bottomRight, 0.5F);
/* 110 */     drawGradientRound(x, y, width, height, radius, mixedColor, topLeft, bottomRight, mixedColor);
/*     */   }
/*     */   
/*     */   public static void drawGradientCornerRL(float x, float y, float width, float height, float radius, Color bottomLeft, Color topRight) {
/* 114 */     Color mixedColor = ColorUtils.interpolateColorC(topRight, bottomLeft, 0.5F);
/* 115 */     drawGradientRound(x, y, width, height, radius, bottomLeft, mixedColor, mixedColor, topRight);
/*     */   }
/*     */   
/*     */   public static void drawRoundTextured(float x, float y, float width, float height, float radius, float alpha) {
/* 119 */     ColorUtils.resetColor();
/* 120 */     roundedTexturedShader.init();
/* 121 */     roundedTexturedShader.setUniformi("textureIn", new int[] { 0 });
/* 122 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedTexturedShader);
/* 123 */     roundedTexturedShader.setUniformf("alpha", new float[] { alpha });
/* 124 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/* 125 */     roundedTexturedShader.unload();
/* 126 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtils roundedTexturedShader) {
/* 130 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/* 131 */     roundedTexturedShader.setUniformf("location", new float[] { x * sr.getScaleFactor(), (Minecraft.getMinecraft()).displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor() });
/* 132 */     roundedTexturedShader.setUniformf("rectSize", new float[] { width * sr.getScaleFactor(), height * sr.getScaleFactor() });
/* 133 */     roundedTexturedShader.setUniformf("radius", new float[] { radius * sr.getScaleFactor() });
/*     */   }
/*     */   
/*     */   public static void round(float x, float y, float width, float height, float radius, Color color) {
/* 137 */     GlStateManager.resetColor();
/* 138 */     GlStateManager.enableBlend();
/* 139 */     GlStateManager.disableTexture2D();
/* 140 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 141 */     roundedShader.init();
/* 142 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
/* 143 */     roundedShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/* 144 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/* 145 */     roundedShader.unload();
/* 146 */     rect(x, y, width, height);
/* 147 */     GlStateManager.enableTexture2D();
/* 148 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void rect(float x, float y, float width, float height) {
/* 152 */     GL11.glBegin(7);
/* 153 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 154 */     GL11.glVertex2f(x, y);
/* 155 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 156 */     GL11.glVertex2f(x, y + height);
/* 157 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 158 */     GL11.glVertex2f(x + width, y + height);
/* 159 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 160 */     GL11.glVertex2f(x + width, y);
/* 161 */     GL11.glEnd();
/*     */   }
/*     */   public static void drawGradientHorizontal(float x, float y, float width, float height, float radius, Color left, Color right) {
/* 164 */     drawGradientRound(x, y, width, height, radius, left, left, right, right);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\RoundedUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */