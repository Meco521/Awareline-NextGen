/*     */ package awareline.main.utility.render.render;
/*     */ 
/*     */ import awareline.main.utility.render.render.shader.ShaderUtils;
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
/*     */ public class RoundedUtils
/*     */ {
/*  18 */   public static ShaderUtils roundedShader = new ShaderUtils("client/advancedshader/shaders/roundedRect.frag");
/*  19 */   public static ShaderUtils roundedOutlineShader = new ShaderUtils("client/advancedshader/shaders/roundRectOutline.frag");
/*  20 */   private static final ShaderUtils roundedTexturedShader = new ShaderUtils("client/advancedshader/shaders/roundRectTextured.frag");
/*  21 */   private static final ShaderUtils roundedGradientShader = new ShaderUtils("client/advancedshader/shaders/roundedRectGradient.frag");
/*     */   
/*     */   public static void drawGradientRoundLR(float x, float y, float width, float height, float radius, Color color1, Color color2) {
/*  24 */     drawGradientRound(x, y, width, height, radius, color1, color2, color2, color1);
/*     */   }
/*     */   
/*     */   public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
/*  28 */     ColorUtils.resetColor();
/*  29 */     GlStateManager.enableBlend();
/*  30 */     GlStateManager.blendFunc(770, 771);
/*  31 */     roundedShader.init();
/*  32 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
/*  33 */     roundedShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/*  34 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/*  35 */     roundedShader.unload();
/*  36 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
/*  40 */     ColorUtils.resetColor();
/*  41 */     GlStateManager.enableBlend();
/*  42 */     GlStateManager.blendFunc(770, 771);
/*  43 */     roundedGradientShader.init();
/*  44 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
/*  45 */     roundedGradientShader.setUniformf("color1", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F, topLeft.getAlpha() / 255.0F });
/*  46 */     roundedGradientShader.setUniformf("color2", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F, bottomRight.getAlpha() / 255.0F });
/*  47 */     roundedGradientShader.setUniformf("color3", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F, bottomLeft.getAlpha() / 255.0F });
/*  48 */     roundedGradientShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F, topRight.getAlpha() / 255.0F });
/*  49 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/*  50 */     roundedGradientShader.unload();
/*  51 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, Color color, Color outlineColor) {
/*  55 */     ColorUtils.resetColor();
/*  56 */     GlStateManager.enableBlend();
/*  57 */     GlStateManager.blendFunc(770, 771);
/*  58 */     roundedOutlineShader.init();
/*  59 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*  60 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
/*  61 */     roundedOutlineShader.setUniformf("outlineThickness", new float[] { outlineThickness * sr.getScaleFactor() });
/*  62 */     roundedOutlineShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/*  63 */     roundedOutlineShader.setUniformf("outlineColor", new float[] { outlineColor.getRed() / 255.0F, outlineColor.getGreen() / 255.0F, outlineColor.getBlue() / 255.0F, outlineColor.getAlpha() / 255.0F });
/*  64 */     ShaderUtils.drawQuads(x - 2.0F + outlineThickness, y - 2.0F + outlineThickness, width + 4.0F + outlineThickness * 2.0F, height + 4.0F + outlineThickness * 2.0F);
/*  65 */     roundedOutlineShader.unload();
/*  66 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawGradientCornerLR(float x, float y, float width, float height, float radius, Color topLeft, Color bottomRight) {
/*  70 */     Color mixedColor = ColorUtils.interpolateColorC(topLeft, bottomRight, 0.5F);
/*  71 */     drawGradientRound(x, y, width, height, radius, mixedColor, topLeft, bottomRight, mixedColor);
/*     */   }
/*     */   
/*     */   public static void drawGradientCornerRL(float x, float y, float width, float height, float radius, Color bottomLeft, Color topRight) {
/*  75 */     Color mixedColor = ColorUtils.interpolateColorC(topRight, bottomLeft, 0.5F);
/*  76 */     drawGradientRound(x, y, width, height, radius, bottomLeft, mixedColor, mixedColor, topRight);
/*     */   }
/*     */   
/*     */   public static void drawRoundTextured(float x, float y, float width, float height, float radius, float alpha) {
/*  80 */     ColorUtils.resetColor();
/*  81 */     roundedTexturedShader.init();
/*  82 */     roundedTexturedShader.setUniformi("textureIn", new int[] { 0 });
/*  83 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedTexturedShader);
/*  84 */     roundedTexturedShader.setUniformf("alpha", new float[] { alpha });
/*  85 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/*  86 */     roundedTexturedShader.unload();
/*  87 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtils roundedTexturedShader) {
/*  91 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*  92 */     roundedTexturedShader.setUniformf("location", new float[] { x * sr.getScaleFactor(), (Minecraft.getMinecraft()).displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor() });
/*  93 */     roundedTexturedShader.setUniformf("rectSize", new float[] { width * sr.getScaleFactor(), height * sr.getScaleFactor() });
/*  94 */     roundedTexturedShader.setUniformf("radius", new float[] { radius * sr.getScaleFactor() });
/*     */   }
/*     */   
/*     */   public static void round(float x, float y, float width, float height, float radius, Color color) {
/*  98 */     GlStateManager.resetColor();
/*  99 */     GlStateManager.enableBlend();
/* 100 */     GlStateManager.disableTexture2D();
/* 101 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 102 */     roundedShader.init();
/* 103 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
/* 104 */     roundedShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/* 105 */     ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/* 106 */     roundedShader.unload();
/* 107 */     rect(x, y, width, height);
/* 108 */     GlStateManager.enableTexture2D();
/* 109 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void rect(float x, float y, float width, float height) {
/* 113 */     GL11.glBegin(7);
/* 114 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 115 */     GL11.glVertex2f(x, y);
/* 116 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 117 */     GL11.glVertex2f(x, y + height);
/* 118 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 119 */     GL11.glVertex2f(x + width, y + height);
/* 120 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 121 */     GL11.glVertex2f(x + width, y);
/* 122 */     GL11.glEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\RoundedUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */