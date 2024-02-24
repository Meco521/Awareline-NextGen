/*     */ package awareline.main.utility.render.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class R2DUtils
/*     */ {
/*     */   public static void enableGL2D() {
/*  19 */     GL11.glDisable(2929);
/*  20 */     GL11.glEnable(3042);
/*  21 */     GL11.glDisable(3553);
/*  22 */     GL11.glBlendFunc(770, 771);
/*  23 */     GL11.glDepthMask(true);
/*  24 */     GL11.glEnable(2848);
/*  25 */     GL11.glHint(3154, 4354);
/*  26 */     GL11.glHint(3155, 4354);
/*     */   }
/*     */   
/*     */   public static void disableGL2D() {
/*  30 */     GL11.glEnable(3553);
/*  31 */     GL11.glDisable(3042);
/*  32 */     GL11.glEnable(2929);
/*  33 */     GL11.glDisable(2848);
/*  34 */     GL11.glHint(3154, 4352);
/*  35 */     GL11.glHint(3155, 4352);
/*     */   }
/*     */   
/*     */   public static void draw2DCorner(Entity e, double posX, double posY, double posZ, int color) {
/*  39 */     GlStateManager.pushMatrix();
/*  40 */     GlStateManager.translate(posX, posY, posZ);
/*  41 */     GL11.glNormal3f(0.0F, 0.0F, 0.0F);
/*  42 */     GlStateManager.rotate(-(Minecraft.getMinecraft().getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/*  43 */     GlStateManager.scale(-0.1D, -0.1D, 0.1D);
/*  44 */     GL11.glDisable(2896);
/*  45 */     GL11.glDisable(2929);
/*  46 */     GL11.glEnable(3042);
/*  47 */     GL11.glBlendFunc(770, 771);
/*  48 */     GlStateManager.depthMask(true);
/*  49 */     drawRect(7.0D, -20.0D, 7.300000190734863D, -17.5D, color);
/*  50 */     drawRect(-7.300000190734863D, -20.0D, -7.0D, -17.5D, color);
/*  51 */     drawRect(4.0D, -20.299999237060547D, 7.300000190734863D, -20.0D, color);
/*  52 */     drawRect(-7.300000190734863D, -20.299999237060547D, -4.0D, -20.0D, color);
/*  53 */     drawRect(-7.0D, 3.0D, -4.0D, 3.299999952316284D, color);
/*  54 */     drawRect(4.0D, 3.0D, 7.0D, 3.299999952316284D, color);
/*  55 */     drawRect(-7.300000190734863D, 0.8D, -7.0D, 3.299999952316284D, color);
/*  56 */     drawRect(7.0D, 0.5D, 7.300000190734863D, 3.299999952316284D, color);
/*  57 */     drawRect(7.0D, -20.0D, 7.300000190734863D, -17.5D, color);
/*  58 */     drawRect(-7.300000190734863D, -20.0D, -7.0D, -17.5D, color);
/*  59 */     drawRect(4.0D, -20.299999237060547D, 7.300000190734863D, -20.0D, color);
/*  60 */     drawRect(-7.300000190734863D, -20.299999237060547D, -4.0D, -20.0D, color);
/*  61 */     drawRect(-7.0D, 3.0D, -4.0D, 3.299999952316284D, color);
/*  62 */     drawRect(4.0D, 3.0D, 7.0D, 3.299999952316284D, color);
/*  63 */     drawRect(-7.300000190734863D, 0.8D, -7.0D, 3.299999952316284D, color);
/*  64 */     drawRect(7.0D, 0.5D, 7.300000190734863D, 3.299999952316284D, color);
/*  65 */     GL11.glDisable(3042);
/*  66 */     GL11.glEnable(2929);
/*  67 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
/*  71 */     enableGL2D();
/*  72 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  73 */     drawVLine(x *= 2.0F, (y *= 2.0F) + 1.0F, (y1 *= 2.0F) - 2.0F, borderC);
/*  74 */     drawVLine((x1 *= 2.0F) - 1.0F, y + 1.0F, y1 - 2.0F, borderC);
/*  75 */     drawHLine(x + 2.0F, x1 - 3.0F, y, borderC);
/*  76 */     drawHLine(x + 2.0F, x1 - 3.0F, y1 - 1.0F, borderC);
/*  77 */     drawHLine(x + 1.0F, x + 1.0F, y + 1.0F, borderC);
/*  78 */     drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
/*  79 */     drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
/*  80 */     drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
/*  81 */     drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
/*  82 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/*  83 */     disableGL2D();
/*  84 */     Gui.drawRect(0, 0, 0, 0, 0);
/*     */   }
/*     */   
/*     */   public static void drawRect(double x2, double y2, double x1, double y1, int color) {
/*  88 */     enableGL2D();
/*  89 */     glColor(color);
/*  90 */     drawRect(x2, y2, x1, y1);
/*  91 */     disableGL2D();
/*     */   }
/*     */   
/*     */   private static void drawRect(double x2, double y2, double x1, double y1) {
/*  95 */     GL11.glBegin(7);
/*  96 */     GL11.glVertex2d(x2, y1);
/*  97 */     GL11.glVertex2d(x1, y1);
/*  98 */     GL11.glVertex2d(x1, y2);
/*  99 */     GL11.glVertex2d(x2, y2);
/* 100 */     GL11.glEnd();
/*     */   }
/*     */   
/*     */   public static void glColor(int hex) {
/* 104 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/* 105 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/* 106 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/* 107 */     float blue = (hex & 0xFF) / 255.0F;
/* 108 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static void drawRect(float x, float y, float x1, float y1, int color) {
/* 112 */     enableGL2D();
/* 113 */     glColor(color);
/* 114 */     drawRect(x, y, x1, y1);
/* 115 */     disableGL2D();
/*     */   }
/*     */   
/*     */   public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
/* 119 */     enableGL2D();
/* 120 */     glColor(borderColor);
/* 121 */     drawRect(x + width, y, x1 - width, y + width);
/* 122 */     drawRect(x, y, x + width, y1);
/* 123 */     drawRect(x1 - width, y, x1, y1);
/* 124 */     drawRect(x + width, y1 - width, x1 - width, y1);
/* 125 */     disableGL2D();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void roundedRect(double x, double y, double width, double height, double edgeRadius, Color color) {
/* 131 */     double halfRadius = edgeRadius / 2.0D;
/* 132 */     width -= halfRadius;
/* 133 */     height -= halfRadius;
/* 134 */     float sideLength = (float)edgeRadius;
/* 135 */     sideLength /= 2.0F;
/* 136 */     RenderUtils.start();
/* 137 */     if (color != null) {
/* 138 */       RenderUtils.color(color);
/*     */     }
/* 140 */     RenderUtils.begin(6); double i;
/* 141 */     for (i = 180.0D; i <= 270.0D; i++) {
/* 142 */       double angle = i * 6.283185307179586D / 360.0D;
/* 143 */       RenderUtils.vertex(x + sideLength * Math.cos(angle) + sideLength, y + sideLength * Math.sin(angle) + sideLength);
/*     */     } 
/* 145 */     RenderUtils.vertex(x + sideLength, y + sideLength);
/* 146 */     RenderUtils.end();
/* 147 */     RenderUtils.stop();
/* 148 */     sideLength = (float)edgeRadius;
/* 149 */     sideLength /= 2.0F;
/* 150 */     RenderUtils.start();
/* 151 */     if (color != null) {
/* 152 */       RenderUtils.color(color);
/*     */     }
/* 154 */     GL11.glEnable(2848);
/* 155 */     RenderUtils.begin(6);
/* 156 */     for (i = 0.0D; i <= 90.0D; i++) {
/* 157 */       double angle = i * 6.283185307179586D / 360.0D;
/* 158 */       RenderUtils.vertex(x + width + sideLength * Math.cos(angle), y + height + sideLength * Math.sin(angle));
/*     */     } 
/* 160 */     RenderUtils.vertex(x + width, y + height);
/* 161 */     RenderUtils.end();
/* 162 */     GL11.glDisable(2848);
/* 163 */     RenderUtils.stop();
/* 164 */     sideLength = (float)edgeRadius;
/* 165 */     sideLength /= 2.0F;
/* 166 */     RenderUtils.start();
/* 167 */     if (color != null) {
/* 168 */       RenderUtils.color(color);
/*     */     }
/* 170 */     GL11.glEnable(2848);
/* 171 */     RenderUtils.begin(6);
/* 172 */     for (i = 270.0D; i <= 360.0D; i++) {
/* 173 */       double angle = i * 6.283185307179586D / 360.0D;
/* 174 */       RenderUtils.vertex(x + width + sideLength * Math.cos(angle), y + sideLength * Math.sin(angle) + sideLength);
/*     */     } 
/* 176 */     RenderUtils.vertex(x + width, y + sideLength);
/* 177 */     RenderUtils.end();
/* 178 */     GL11.glDisable(2848);
/* 179 */     RenderUtils.stop();
/* 180 */     sideLength = (float)edgeRadius;
/* 181 */     sideLength /= 2.0F;
/* 182 */     RenderUtils.start();
/* 183 */     if (color != null) {
/* 184 */       RenderUtils.color(color);
/*     */     }
/* 186 */     GL11.glEnable(2848);
/* 187 */     RenderUtils.begin(6);
/* 188 */     for (i = 90.0D; i <= 180.0D; i++) {
/* 189 */       double angle = i * 6.283185307179586D / 360.0D;
/* 190 */       RenderUtils.vertex(x + sideLength * Math.cos(angle) + sideLength, y + height + sideLength * Math.sin(angle));
/*     */     } 
/* 192 */     RenderUtils.vertex(x + sideLength, y + height);
/* 193 */     RenderUtils.end();
/* 194 */     GL11.glDisable(2848);
/* 195 */     RenderUtils.stop();
/* 196 */     RenderUtils.rect(x + halfRadius, y + halfRadius, width - halfRadius, height - halfRadius, color);
/* 197 */     RenderUtils.rect(x, y + halfRadius, edgeRadius / 2.0D, height - halfRadius, color);
/* 198 */     RenderUtils.rect(x + width, y + halfRadius, edgeRadius / 2.0D, height - halfRadius, color);
/* 199 */     RenderUtils.rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
/* 200 */     RenderUtils.rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
/*     */   }
/*     */   
/*     */   public static void roundedRect(double x, double y, double x2, double y2, double edgeRadius, Color color, int i) {
/* 204 */     roundedRect(x, y, x2 - x, y2 - y, edgeRadius, color);
/*     */   }
/*     */   
/*     */   public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
/* 208 */     enableGL2D();
/* 209 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 210 */     drawVLine(x *= 2.0F, y *= 2.0F, y1 *= 2.0F, borderC);
/* 211 */     drawVLine((x1 *= 2.0F) - 1.0F, y, y1, borderC);
/* 212 */     drawHLine(x, x1 - 1.0F, y, borderC);
/* 213 */     drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
/* 214 */     drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
/* 215 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 216 */     disableGL2D();
/*     */   }
/*     */   
/*     */   public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
/* 220 */     enableGL2D();
/* 221 */     GL11.glShadeModel(7425);
/* 222 */     GL11.glBegin(7);
/* 223 */     glColor(topColor);
/* 224 */     GL11.glVertex2f(x, y1);
/* 225 */     GL11.glVertex2f(x1, y1);
/* 226 */     glColor(bottomColor);
/* 227 */     GL11.glVertex2f(x1, y);
/* 228 */     GL11.glVertex2f(x, y);
/* 229 */     GL11.glEnd();
/* 230 */     GL11.glShadeModel(7424);
/* 231 */     disableGL2D();
/*     */   }
/*     */   
/*     */   public static void drawHLine(float x, float y, float x1, int y1) {
/* 235 */     if (y < x) {
/* 236 */       float var5 = x;
/* 237 */       x = y;
/* 238 */       y = var5;
/*     */     } 
/* 240 */     drawRect(x, x1, y + 1.0F, x1 + 1.0F, y1);
/*     */   }
/*     */   
/*     */   public static void drawVLine(float x, float y, float x1, int y1) {
/* 244 */     if (x1 < y) {
/* 245 */       float var5 = y;
/* 246 */       y = x1;
/* 247 */       x1 = var5;
/*     */     } 
/* 249 */     drawRect(x, y + 1.0F, x + 1.0F, x1, y1);
/*     */   }
/*     */   
/*     */   public static void drawHLine(float x, float y, float x1, int y1, int y2) {
/* 253 */     if (y < x) {
/* 254 */       float var5 = x;
/* 255 */       x = y;
/* 256 */       y = var5;
/*     */     } 
/* 258 */     drawGradientRect(x, x1, y + 1.0F, x1 + 1.0F, y1, y2);
/*     */   }
/*     */   
/*     */   public static void drawRect(float x, float y, float x1, float y1) {
/* 262 */     GL11.glBegin(7);
/* 263 */     GL11.glVertex2f(x, y1);
/* 264 */     GL11.glVertex2f(x1, y1);
/* 265 */     GL11.glVertex2f(x1, y);
/* 266 */     GL11.glVertex2f(x, y);
/* 267 */     GL11.glEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\R2DUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */