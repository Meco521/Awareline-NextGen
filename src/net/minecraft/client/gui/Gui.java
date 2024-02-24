/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.render.gl.GLUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Gui
/*     */ {
/*  17 */   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
/*  18 */   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
/*  19 */   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
/*     */ 
/*     */   
/*     */   protected float zLevel;
/*     */ 
/*     */   
/*     */   protected void drawHorizontalLine(int startX, int endX, int y, int color) {
/*  26 */     if (endX < startX) {
/*  27 */       int i = startX;
/*  28 */       startX = endX;
/*  29 */       endX = i;
/*     */     } 
/*     */     
/*  32 */     drawRect(startX, y, endX + 1, y + 1, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalLine(int x, int startY, int endY, int color) {
/*  39 */     if (endY < startY) {
/*  40 */       int i = startY;
/*  41 */       startY = endY;
/*  42 */       endY = i;
/*     */     } 
/*     */     
/*  45 */     drawRect(x, startY + 1, x + 1, endY, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRect(int left, int top, int right, int bottom, int color) {
/*  52 */     if (left < right) {
/*  53 */       int i = left;
/*  54 */       left = right;
/*  55 */       right = i;
/*     */     } 
/*     */     
/*  58 */     if (top < bottom) {
/*  59 */       int j = top;
/*  60 */       top = bottom;
/*  61 */       bottom = j;
/*     */     } 
/*     */     
/*  64 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  65 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  66 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  67 */     float f2 = (color & 0xFF) / 255.0F;
/*  68 */     Tessellator tessellator = Tessellator.getInstance();
/*  69 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  70 */     GlStateManager.enableBlend();
/*  71 */     GlStateManager.disableTexture2D();
/*  72 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  73 */     GlStateManager.color(f, f1, f2, f3);
/*  74 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  75 */     worldrenderer.pos(left, bottom, 0.0D).endVertex();
/*  76 */     worldrenderer.pos(right, bottom, 0.0D).endVertex();
/*  77 */     worldrenderer.pos(right, top, 0.0D).endVertex();
/*  78 */     worldrenderer.pos(left, top, 0.0D).endVertex();
/*  79 */     tessellator.draw();
/*  80 */     GlStateManager.enableTexture2D();
/*  81 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/*  90 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/*  91 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/*  92 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/*  93 */     float f3 = (startColor & 0xFF) / 255.0F;
/*  94 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/*  95 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/*  96 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/*  97 */     float f7 = (endColor & 0xFF) / 255.0F;
/*  98 */     GlStateManager.disableTexture2D();
/*  99 */     GlStateManager.enableBlend();
/* 100 */     GlStateManager.disableAlpha();
/* 101 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 102 */     GlStateManager.shadeModel(7425);
/* 103 */     Tessellator tessellator = Tessellator.getInstance();
/* 104 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 105 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 106 */     worldrenderer.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 107 */     worldrenderer.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 108 */     worldrenderer.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 109 */     worldrenderer.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 110 */     tessellator.draw();
/* 111 */     GlStateManager.shadeModel(7424);
/* 112 */     GlStateManager.disableBlend();
/* 113 */     GlStateManager.enableAlpha();
/* 114 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect3(double d, double e, double g, double h, int color) {
/* 119 */     if (d < g) {
/* 120 */       int j = (int)d;
/* 121 */       d = g;
/* 122 */       g = j;
/*     */     } 
/*     */     
/* 125 */     if (e < h) {
/* 126 */       int j = (int)e;
/* 127 */       e = h;
/* 128 */       h = j;
/*     */     } 
/*     */     
/* 131 */     RenderUtil.resetColor();
/* 132 */     RenderUtil.setAlphaLimit(0.0F);
/* 133 */     GLUtil.setup2DRendering(true);
/* 134 */     Tessellator tessellator = Tessellator.getInstance();
/* 135 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 136 */     GlStateManager.enableBlend();
/* 137 */     GlStateManager.disableTexture2D();
/* 138 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 139 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 140 */     worldrenderer.pos(d, h, 0.0D).color(color).endVertex();
/* 141 */     worldrenderer.pos(g, h, 0.0D).color(color).endVertex();
/* 142 */     worldrenderer.pos(g, e, 0.0D).color(color).endVertex();
/* 143 */     worldrenderer.pos(d, e, 0.0D).color(color).endVertex();
/* 144 */     tessellator.draw();
/* 145 */     GlStateManager.enableTexture2D();
/* 146 */     GlStateManager.disableBlend();
/* 147 */     GLUtil.end2DRendering();
/*     */   }
/*     */   
/*     */   public static void drawRect2(double x, double y, double width, double height, int color) {
/* 151 */     RenderUtil.resetColor();
/* 152 */     RenderUtil.setAlphaLimit(0.0F);
/* 153 */     GLUtil.setup2DRendering(true);
/*     */     
/* 155 */     Tessellator tessellator = Tessellator.getInstance();
/* 156 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 158 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 159 */     worldrenderer.pos(x, y, 0.0D).color(color).endVertex();
/* 160 */     worldrenderer.pos(x, y + height, 0.0D).color(color).endVertex();
/* 161 */     worldrenderer.pos(x + width, y + height, 0.0D).color(color).endVertex();
/* 162 */     worldrenderer.pos(x + width, y, 0.0D).color(color).endVertex();
/* 163 */     tessellator.draw();
/*     */     
/* 165 */     GLUtil.end2DRendering();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 171 */     fontRendererIn.drawStringWithShadow(text, (x - fontRendererIn.getStringWidth(text) / 2), y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 178 */     fontRendererIn.drawStringWithShadow(text, x, y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 185 */     float f = 0.00390625F;
/* 186 */     float f1 = 0.00390625F;
/* 187 */     Tessellator tessellator = Tessellator.getInstance();
/* 188 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 189 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 190 */     worldrenderer.pos(x, (y + height), this.zLevel).tex((textureX * f), ((textureY + height) * f1)).endVertex();
/* 191 */     worldrenderer.pos((x + width), (y + height), this.zLevel).tex(((textureX + width) * f), ((textureY + height) * f1)).endVertex();
/* 192 */     worldrenderer.pos((x + width), y, this.zLevel).tex(((textureX + width) * f), (textureY * f1)).endVertex();
/* 193 */     worldrenderer.pos(x, y, this.zLevel).tex((textureX * f), (textureY * f1)).endVertex();
/* 194 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
/* 201 */     float f = 0.00390625F;
/* 202 */     float f1 = 0.00390625F;
/* 203 */     Tessellator tessellator = Tessellator.getInstance();
/* 204 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 205 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 206 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + maxV), this.zLevel).tex((minU * f), ((minV + maxV) * f1)).endVertex();
/* 207 */     worldrenderer.pos((xCoord + maxU), (yCoord + maxV), this.zLevel).tex(((minU + maxU) * f), ((minV + maxV) * f1)).endVertex();
/* 208 */     worldrenderer.pos((xCoord + maxU), (yCoord + 0.0F), this.zLevel).tex(((minU + maxU) * f), (minV * f1)).endVertex();
/* 209 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + 0.0F), this.zLevel).tex((minU * f), (minV * f1)).endVertex();
/* 210 */     tessellator.draw();
/*     */   }
/*     */   public void renderGradientRectLeftRight(int left, int top, int right, int bottom, int startColor, int endColor) {
/* 213 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/* 214 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/* 215 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/* 216 */     float f3 = (startColor & 0xFF) / 255.0F;
/* 217 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/* 218 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/* 219 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/* 220 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 221 */     GlStateManager.disableTexture2D();
/* 222 */     GlStateManager.enableBlend();
/* 223 */     GlStateManager.disableAlpha();
/* 224 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 225 */     GlStateManager.shadeModel(7425);
/* 226 */     Tessellator tessellator = Tessellator.getInstance();
/* 227 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 228 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 229 */     worldrenderer.pos(right, bottom, this.zLevel).func_181666_a(f5, f6, f7, f4).endVertex();
/* 230 */     worldrenderer.pos(right, top, this.zLevel).func_181666_a(f5, f6, f7, f4).endVertex();
/* 231 */     worldrenderer.pos(left, top, this.zLevel).func_181666_a(f1, f2, f3, f).endVertex();
/* 232 */     worldrenderer.pos(left, bottom, this.zLevel).func_181666_a(f1, f2, f3, f).endVertex();
/* 233 */     tessellator.draw();
/* 234 */     GlStateManager.shadeModel(7424);
/* 235 */     GlStateManager.disableBlend();
/* 236 */     GlStateManager.enableAlpha();
/* 237 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
/* 243 */     Tessellator tessellator = Tessellator.getInstance();
/* 244 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 245 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 246 */     worldrenderer.pos(xCoord, (yCoord + heightIn), this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
/* 247 */     worldrenderer.pos((xCoord + widthIn), (yCoord + heightIn), this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
/* 248 */     worldrenderer.pos((xCoord + widthIn), yCoord, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
/* 249 */     worldrenderer.pos(xCoord, yCoord, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
/* 250 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public static void drawModalRectWithCustomSizedTexture(float p_drawModalRectWithCustomSizedTexture_0_, double p_drawModalRectWithCustomSizedTexture_1_, float p_drawModalRectWithCustomSizedTexture_3_, float p_drawModalRectWithCustomSizedTexture_4_, double p_drawModalRectWithCustomSizedTexture_5_, double p_drawModalRectWithCustomSizedTexture_7_, double p_drawModalRectWithCustomSizedTexture_9_, double p_drawModalRectWithCustomSizedTexture_11_) {
/* 254 */     double n = 1.0D / p_drawModalRectWithCustomSizedTexture_9_;
/* 255 */     double n2 = 1.0D / p_drawModalRectWithCustomSizedTexture_11_;
/* 256 */     Tessellator instance = Tessellator.getInstance();
/* 257 */     instance.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/* 258 */     instance.getWorldRenderer().addVertexWithUV(p_drawModalRectWithCustomSizedTexture_0_, p_drawModalRectWithCustomSizedTexture_1_ + p_drawModalRectWithCustomSizedTexture_7_, 0.0D, p_drawModalRectWithCustomSizedTexture_3_ * n, (p_drawModalRectWithCustomSizedTexture_4_ + (float)p_drawModalRectWithCustomSizedTexture_7_) * n2);
/* 259 */     instance.getWorldRenderer().addVertexWithUV(p_drawModalRectWithCustomSizedTexture_0_ + p_drawModalRectWithCustomSizedTexture_5_, p_drawModalRectWithCustomSizedTexture_1_ + p_drawModalRectWithCustomSizedTexture_7_, 0.0D, (p_drawModalRectWithCustomSizedTexture_3_ + (float)p_drawModalRectWithCustomSizedTexture_5_) * n, (p_drawModalRectWithCustomSizedTexture_4_ + (float)p_drawModalRectWithCustomSizedTexture_7_) * n2);
/* 260 */     instance.getWorldRenderer().addVertexWithUV(p_drawModalRectWithCustomSizedTexture_0_ + p_drawModalRectWithCustomSizedTexture_5_, p_drawModalRectWithCustomSizedTexture_1_, 0.0D, (p_drawModalRectWithCustomSizedTexture_3_ + (float)p_drawModalRectWithCustomSizedTexture_5_) * n, p_drawModalRectWithCustomSizedTexture_4_ * n2);
/* 261 */     instance.getWorldRenderer().addVertexWithUV(p_drawModalRectWithCustomSizedTexture_0_, p_drawModalRectWithCustomSizedTexture_1_, 0.0D, p_drawModalRectWithCustomSizedTexture_3_ * n, p_drawModalRectWithCustomSizedTexture_4_ * n2);
/* 262 */     instance.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
/* 269 */     float f = 1.0F / textureWidth;
/* 270 */     float f1 = 1.0F / textureHeight;
/* 271 */     Tessellator tessellator = Tessellator.getInstance();
/* 272 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 273 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 274 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
/* 275 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
/* 276 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
/* 277 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 278 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 285 */     float f = 1.0F / tileWidth;
/* 286 */     float f1 = 1.0F / tileHeight;
/* 287 */     Tessellator tessellator = Tessellator.getInstance();
/* 288 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 289 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 290 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + vHeight) * f1)).endVertex();
/* 291 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
/* 292 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + uWidth) * f), (v * f1)).endVertex();
/* 293 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 294 */     tessellator.draw();
/*     */   }
/*     */   public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight) {
/* 297 */     float f = 1.0F / tileWidth;
/* 298 */     float f1 = 1.0F / tileHeight;
/* 299 */     Tessellator tessellator = Tessellator.getInstance();
/* 300 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 301 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 302 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + vHeight) * f1)).endVertex();
/* 303 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
/* 304 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + uWidth) * f), (v * f1)).endVertex();
/* 305 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 306 */     tessellator.draw();
/*     */   }
/*     */   public static void drawRect(double d, double e, double g, double h, int color) {
/* 309 */     if (d < g) {
/* 310 */       int i = (int)d;
/* 311 */       d = g;
/* 312 */       g = i;
/*     */     } 
/*     */     
/* 315 */     if (e < h) {
/* 316 */       int j = (int)e;
/* 317 */       e = h;
/* 318 */       h = j;
/*     */     } 
/*     */     
/* 321 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/* 322 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 323 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 324 */     float f2 = (color & 0xFF) / 255.0F;
/* 325 */     Tessellator tessellator = Tessellator.getInstance();
/* 326 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 327 */     GlStateManager.enableBlend();
/* 328 */     GlStateManager.disableTexture2D();
/* 329 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 330 */     GlStateManager.color(f, f1, f2, f3);
/* 331 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 332 */     worldrenderer.pos(d, h, 0.0D).endVertex();
/* 333 */     worldrenderer.pos(g, h, 0.0D).endVertex();
/* 334 */     worldrenderer.pos(g, e, 0.0D).endVertex();
/* 335 */     worldrenderer.pos(d, e, 0.0D).endVertex();
/* 336 */     tessellator.draw();
/* 337 */     GlStateManager.enableTexture2D();
/* 338 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawFilledCircle(double x, double y, double r, int c, int id) {
/* 342 */     float f = (c >> 24 & 0xFF) / 255.0F;
/* 343 */     float f1 = (c >> 16 & 0xFF) / 255.0F;
/* 344 */     float f2 = (c >> 8 & 0xFF) / 255.0F;
/* 345 */     float f3 = (c & 0xFF) / 255.0F;
/* 346 */     GL11.glEnable(3042);
/* 347 */     GL11.glDisable(3553);
/* 348 */     GL11.glColor4f(f1, f2, f3, f);
/* 349 */     GL11.glBegin(9);
/* 350 */     if (id == 1) {
/* 351 */       GL11.glVertex2d(x, y);
/* 352 */       for (int i = 0; i <= 90; i++) {
/* 353 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/* 354 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/* 355 */         GL11.glVertex2d(x - x2, y - y2);
/*     */       } 
/* 357 */     } else if (id == 2) {
/* 358 */       GL11.glVertex2d(x, y);
/* 359 */       for (int i = 90; i <= 180; i++) {
/* 360 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/* 361 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/* 362 */         GL11.glVertex2d(x - x2, y - y2);
/*     */       } 
/* 364 */     } else if (id == 3) {
/* 365 */       GL11.glVertex2d(x, y);
/* 366 */       for (int i = 270; i <= 360; i++) {
/* 367 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/* 368 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/* 369 */         GL11.glVertex2d(x - x2, y - y2);
/*     */       } 
/* 371 */     } else if (id == 4) {
/* 372 */       GL11.glVertex2d(x, y);
/* 373 */       for (int i = 180; i <= 270; i++) {
/* 374 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/* 375 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/* 376 */         GL11.glVertex2d(x - x2, y - y2);
/*     */       } 
/*     */     } else {
/* 379 */       for (int i = 0; i <= 360; i++) {
/* 380 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/* 381 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/* 382 */         GL11.glVertex2f((float)(x - x2), (float)(y - y2));
/*     */       } 
/*     */     } 
/* 385 */     GL11.glEnd();
/* 386 */     GL11.glEnable(3553);
/* 387 */     GL11.glDisable(3042);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */