/*     */ package awareline.main.utility.render;
/*     */ 
/*     */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiRenderUtils
/*     */ {
/*  17 */   public static final Minecraft mc = Minecraft.getMinecraft();
/*     */ 
/*     */   
/*     */   private static float scissorX;
/*     */   
/*     */   private static float scissorY;
/*     */   
/*     */   private static float scissorWidth;
/*     */   
/*     */   private static float scissorHeight;
/*     */   
/*     */   private static float scissorSF;
/*     */   
/*     */   private static boolean isScissoring;
/*     */ 
/*     */   
/*     */   public static float[] getScissor() {
/*  34 */     if (isScissoring) {
/*  35 */       return new float[] { scissorX, scissorY, scissorWidth, scissorHeight, scissorSF };
/*     */     }
/*  37 */     return new float[] { -1.0F };
/*     */   }
/*     */   
/*     */   public static void beginCrop(float x, float y, float width, float height) {
/*  41 */     float scaleFactor = getScaleFactor();
/*  42 */     GL11.glEnable(3089);
/*  43 */     GL11.glScissor((int)(x * scaleFactor), (int)(Display.getHeight() - y * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
/*  44 */     isScissoring = true;
/*  45 */     scissorX = x;
/*  46 */     scissorY = y;
/*  47 */     scissorWidth = width;
/*  48 */     scissorHeight = height;
/*  49 */     scissorSF = scaleFactor;
/*     */   }
/*     */   
/*     */   public static void beginCropFixed(float x, float y, float width, float height) {
/*  53 */     float scaleFactor = getScaleFactor();
/*  54 */     GL11.glEnable(3089);
/*  55 */     GL11.glScissor((int)(x * scaleFactor), (int)(Display.getHeight() - y * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
/*  56 */     isScissoring = true;
/*  57 */     scissorX = x;
/*  58 */     scissorY = y;
/*  59 */     scissorWidth = width;
/*  60 */     scissorHeight = height;
/*  61 */     scissorSF = scaleFactor;
/*     */   }
/*     */   
/*     */   public static void beginCrop(float x, float y, float width, float height, float scaleFactor) {
/*  65 */     GL11.glEnable(3089);
/*  66 */     GL11.glScissor((int)(x * scaleFactor), (int)(Display.getHeight() - y * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
/*  67 */     isScissoring = true;
/*  68 */     scissorX = x;
/*  69 */     scissorY = y;
/*  70 */     scissorWidth = width;
/*  71 */     scissorHeight = height;
/*  72 */     scissorSF = scaleFactor;
/*     */   }
/*     */   
/*     */   public static void endCrop() {
/*  76 */     GL11.glDisable(3089);
/*  77 */     isScissoring = false;
/*     */   }
/*     */   
/*     */   public static void drawImageSpread(ResourceLocation image, float x, float y, float width, float height, float alpha) {
/*  81 */     GlStateManager.disableDepth();
/*  82 */     GlStateManager.enableBlend();
/*  83 */     GL11.glDepthMask(false);
/*  84 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*     */     
/*  86 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
/*  87 */     mc.getTextureManager().bindTexture(image);
/*  88 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, 25.0D, 25.0D);
/*     */     
/*  90 */     GL11.glDepthMask(true);
/*  91 */     GlStateManager.disableBlend();
/*  92 */     GlStateManager.enableDepth();
/*     */     
/*  94 */     GlStateManager.resetColor();
/*     */   }
/*     */   
/*     */   public static void doGlScissor(int x, int y, float width, float height, float scale) {
/*  98 */     int scaleFactor = 1;
/*     */     
/* 100 */     while (scaleFactor < scale && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
/* 101 */       scaleFactor++;
/*     */     }
/*     */     
/* 104 */     GL11.glScissor(x * scaleFactor, (int)(mc.displayHeight - (y + height) * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
/*     */   }
/*     */   
/*     */   public static void drawLine3D(double x1, double y1, double z1, double x2, double y2, double z2, int color) {
/* 108 */     drawLine3D(x1, y1, z1, x2, y2, z2, color, true);
/*     */   }
/*     */   
/*     */   public static void drawLine3D(double x1, double y1, double z1, double x2, double y2, double z2, int color, boolean disableDepth) {
/* 112 */     enableRender3D(disableDepth);
/* 113 */     setColor(color);
/* 114 */     GL11.glBegin(1);
/* 115 */     GL11.glVertex3d(x1, y1, z1);
/* 116 */     GL11.glVertex3d(x2, y2, z2);
/* 117 */     GL11.glEnd();
/* 118 */     disableRender3D(disableDepth);
/*     */   }
/*     */   
/*     */   public static void drawLine2D(double x1, double y1, double x2, double y2, float width, int color) {
/* 122 */     enableRender2D();
/* 123 */     setColor(color);
/* 124 */     GL11.glLineWidth(width);
/* 125 */     GL11.glBegin(1);
/* 126 */     GL11.glVertex2d(x1, y1);
/* 127 */     GL11.glVertex2d(x2, y2);
/* 128 */     GL11.glEnd();
/* 129 */     disableRender2D();
/*     */   }
/*     */   
/*     */   public static void drawPoint(int x, int y, float size, int color) {
/* 133 */     enableRender2D();
/* 134 */     setColor(color);
/* 135 */     GL11.glPointSize(size);
/* 136 */     GL11.glEnable(2832);
/* 137 */     GL11.glBegin(0);
/* 138 */     GL11.glVertex2d(x, y);
/* 139 */     GL11.glEnd();
/* 140 */     GL11.glDisable(2832);
/* 141 */     disableRender2D();
/*     */   }
/*     */   
/*     */   public static float getScaleFactor() {
/* 145 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/* 146 */     return scaledResolution.getScaleFactor();
/*     */   }
/*     */   
/*     */   public static void drawOutlinedBox(AxisAlignedBB boundingBox, int color) {
/* 150 */     drawOutlinedBox(boundingBox, color, true);
/*     */   }
/*     */   
/*     */   public static void drawOutlinedBox(AxisAlignedBB boundingBox, int color, boolean disableDepth) {
/* 154 */     if (boundingBox != null) {
/* 155 */       enableRender3D(disableDepth);
/* 156 */       setColor(color);
/* 157 */       GL11.glBegin(3);
/* 158 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 159 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 160 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 161 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 162 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 163 */       GL11.glEnd();
/* 164 */       GL11.glBegin(3);
/* 165 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 166 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 167 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 168 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 169 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 170 */       GL11.glEnd();
/* 171 */       GL11.glBegin(1);
/* 172 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 173 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 174 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 175 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 176 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 177 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 178 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 179 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 180 */       GL11.glEnd();
/* 181 */       disableRender3D(disableDepth);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawBox(AxisAlignedBB boundingBox, int color) {
/* 186 */     drawBox(boundingBox, color, true);
/*     */   }
/*     */   
/*     */   public static void drawBox(AxisAlignedBB boundingBox, int color, boolean disableDepth) {
/* 190 */     if (boundingBox != null) {
/* 191 */       enableRender3D(disableDepth);
/* 192 */       setColor(color);
/* 193 */       GL11.glBegin(7);
/* 194 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 195 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 196 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 197 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 198 */       GL11.glEnd();
/* 199 */       GL11.glBegin(7);
/* 200 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 201 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 202 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 203 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 204 */       GL11.glEnd();
/* 205 */       GL11.glBegin(7);
/* 206 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 207 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 208 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 209 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 210 */       GL11.glEnd();
/* 211 */       GL11.glBegin(7);
/* 212 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 213 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 214 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 215 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 216 */       GL11.glEnd();
/* 217 */       GL11.glBegin(7);
/* 218 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 219 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 220 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 221 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 222 */       GL11.glEnd();
/* 223 */       GL11.glBegin(7);
/* 224 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 225 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 226 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 227 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 228 */       GL11.glEnd();
/* 229 */       GL11.glBegin(7);
/* 230 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 231 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 232 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 233 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 234 */       GL11.glEnd();
/* 235 */       GL11.glBegin(7);
/* 236 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 237 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 238 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 239 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 240 */       GL11.glEnd();
/* 241 */       GL11.glBegin(7);
/* 242 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 243 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 244 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 245 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 246 */       GL11.glEnd();
/* 247 */       GL11.glBegin(7);
/* 248 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
/* 249 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
/* 250 */       GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
/* 251 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
/* 252 */       GL11.glEnd();
/* 253 */       GL11.glBegin(7);
/* 254 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 255 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 256 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 257 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 258 */       GL11.glEnd();
/* 259 */       GL11.glBegin(7);
/* 260 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
/* 261 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
/* 262 */       GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
/* 263 */       GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
/* 264 */       GL11.glEnd();
/* 265 */       disableRender3D(disableDepth);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void enableRender3D(boolean disableDepth) {
/* 270 */     if (disableDepth) {
/* 271 */       GL11.glDepthMask(false);
/* 272 */       GL11.glDisable(2929);
/*     */     } 
/*     */     
/* 275 */     GL11.glDisable(3008);
/* 276 */     GL11.glEnable(3042);
/* 277 */     GL11.glDisable(3553);
/* 278 */     GL11.glBlendFunc(770, 771);
/* 279 */     GL11.glEnable(2848);
/* 280 */     GL11.glHint(3154, 4354);
/* 281 */     GL11.glLineWidth(1.0F);
/*     */   }
/*     */   
/*     */   public static void disableRender3D(boolean enableDepth) {
/* 285 */     if (enableDepth) {
/* 286 */       GL11.glDepthMask(true);
/* 287 */       GL11.glEnable(2929);
/*     */     } 
/*     */     
/* 290 */     GL11.glEnable(3553);
/* 291 */     GL11.glDisable(3042);
/* 292 */     GL11.glEnable(3008);
/* 293 */     GL11.glDisable(2848);
/* 294 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void enableRender2D() {
/* 298 */     GL11.glEnable(3042);
/* 299 */     GL11.glDisable(2884);
/* 300 */     GL11.glDisable(3553);
/* 301 */     GL11.glEnable(2848);
/* 302 */     GL11.glBlendFunc(770, 771);
/* 303 */     GL11.glLineWidth(1.0F);
/*     */   }
/*     */   
/*     */   public static void disableRender2D() {
/* 307 */     GL11.glDisable(3042);
/* 308 */     GL11.glEnable(2884);
/* 309 */     GL11.glEnable(3553);
/* 310 */     GL11.glDisable(2848);
/* 311 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 312 */     GlStateManager.shadeModel(7424);
/* 313 */     GlStateManager.disableBlend();
/* 314 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */   
/*     */   public static void setColor(int colorHex) {
/* 318 */     float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
/* 319 */     float red = (colorHex >> 16 & 0xFF) / 255.0F;
/* 320 */     float green = (colorHex >> 8 & 0xFF) / 255.0F;
/* 321 */     float blue = (colorHex & 0xFF) / 255.0F;
/* 322 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static void drawBorderedRect(float x, float y, float width, float height, float borderWidth, Color rectColor, Color borderColor) {
/* 326 */     drawBorderedRect(x, y, width, height, borderWidth, rectColor.getRGB(), borderColor.getRGB());
/*     */   }
/*     */   
/*     */   public static void drawBorderedRect(float x, float y, float width, float height, float borderWidth, int rectColor, int borderColor) {
/* 330 */     drawRect(x + borderWidth, y + borderWidth, width - borderWidth * 2.0F, height - borderWidth * 2.0F, rectColor);
/* 331 */     drawRect(x, y, width, borderWidth, borderColor);
/* 332 */     drawRect(x, y + borderWidth, borderWidth, height - borderWidth, borderColor);
/* 333 */     drawRect(x + width - borderWidth, y + borderWidth, borderWidth, height - borderWidth, borderColor);
/* 334 */     drawRect(x + borderWidth, y + height - borderWidth, width - borderWidth * 2.0F, borderWidth, borderColor);
/*     */   }
/*     */   
/*     */   public static void drawBorder(float x, float y, float width, float height, float borderWidth, int borderColor) {
/* 338 */     drawRect(x + borderWidth, y + borderWidth, width - borderWidth * 2.0F, borderWidth, borderColor);
/* 339 */     drawRect(x, y + borderWidth, borderWidth, height - borderWidth, borderColor);
/* 340 */     drawRect(x + width - borderWidth, y + borderWidth, borderWidth, height - borderWidth, borderColor);
/* 341 */     drawRect(x + borderWidth, y + height - borderWidth, width - borderWidth * 2.0F, borderWidth, borderColor);
/*     */   }
/*     */   
/*     */   public static void drawRect(float x, float y, float width, float height, Color color) {
/* 345 */     drawRect(x, y, width, height, color.getRGB());
/*     */   }
/*     */   
/*     */   public static void drawRect(float x, float y, float width, float height, int color) {
/* 349 */     enableRender2D();
/* 350 */     setColor(color);
/* 351 */     GL11.glBegin(7);
/* 352 */     GL11.glVertex2d(x, y);
/* 353 */     GL11.glVertex2d((x + width), y);
/* 354 */     GL11.glVertex2d((x + width), (y + height));
/* 355 */     GL11.glVertex2d(x, (y + height));
/* 356 */     GL11.glEnd();
/* 357 */     disableRender2D();
/*     */   }
/*     */   
/*     */   public static void drawRoundedRect(float x, float y, float width, float height, float edgeRadius, int color, float borderWidth, int borderColor) {
/* 361 */     if (color == 16777215) color = ColorUtils.WHITE.c; 
/* 362 */     if (borderColor == 16777215) borderColor = ColorUtils.WHITE.c;
/*     */     
/* 364 */     if (edgeRadius < 0.0F) {
/* 365 */       edgeRadius = 0.0F;
/*     */     }
/*     */     
/* 368 */     if (edgeRadius > width / 2.0F) {
/* 369 */       edgeRadius = width / 2.0F;
/*     */     }
/*     */     
/* 372 */     if (edgeRadius > height / 2.0F) {
/* 373 */       edgeRadius = height / 2.0F;
/*     */     }
/*     */     
/* 376 */     drawRect(x + edgeRadius, y + edgeRadius, width - edgeRadius * 2.0F, height - edgeRadius * 2.0F, color);
/* 377 */     drawRect(x + edgeRadius, y, width - edgeRadius * 2.0F, edgeRadius, color);
/* 378 */     drawRect(x + edgeRadius, y + height - edgeRadius, width - edgeRadius * 2.0F, edgeRadius, color);
/* 379 */     drawRect(x, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0F, color);
/* 380 */     drawRect(x + width - edgeRadius, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0F, color);
/* 381 */     enableRender2D();
/* 382 */     RenderUtil.color(color);
/* 383 */     GL11.glBegin(6);
/* 384 */     float centerX = x + edgeRadius;
/* 385 */     float centerY = y + edgeRadius;
/* 386 */     GL11.glVertex2d(centerX, centerY);
/* 387 */     int vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);
/*     */     
/*     */     int i;
/*     */     
/* 391 */     for (i = 0; i < vertices + 1; i++) {
/* 392 */       double angleRadians = 6.283185307179586D * (i + 180) / (vertices << 2);
/* 393 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 396 */     GL11.glEnd();
/* 397 */     GL11.glBegin(6);
/* 398 */     centerX = x + width - edgeRadius;
/* 399 */     centerY = y + edgeRadius;
/* 400 */     GL11.glVertex2d(centerX, centerY);
/* 401 */     vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);
/*     */     
/* 403 */     for (i = 0; i < vertices + 1; i++) {
/* 404 */       double angleRadians = 6.283185307179586D * (i + 90) / (vertices << 2);
/* 405 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 408 */     GL11.glEnd();
/* 409 */     GL11.glBegin(6);
/* 410 */     centerX = x + edgeRadius;
/* 411 */     centerY = y + height - edgeRadius;
/* 412 */     GL11.glVertex2d(centerX, centerY);
/* 413 */     vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);
/*     */     
/* 415 */     for (i = 0; i < vertices + 1; i++) {
/* 416 */       double angleRadians = 6.283185307179586D * (i + 270) / (vertices << 2);
/* 417 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 420 */     GL11.glEnd();
/* 421 */     GL11.glBegin(6);
/* 422 */     centerX = x + width - edgeRadius;
/* 423 */     centerY = y + height - edgeRadius;
/* 424 */     GL11.glVertex2d(centerX, centerY);
/* 425 */     vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);
/*     */     
/* 427 */     for (i = 0; i < vertices + 1; i++) {
/* 428 */       double angleRadians = 6.283185307179586D * i / (vertices << 2);
/* 429 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 432 */     GL11.glEnd();
/* 433 */     RenderUtil.color(borderColor);
/* 434 */     GL11.glLineWidth(borderWidth);
/* 435 */     GL11.glBegin(3);
/* 436 */     centerX = x + edgeRadius;
/* 437 */     centerY = y + edgeRadius;
/* 438 */     vertices = (int)Math.min(Math.max(edgeRadius, 10.0F), 90.0F);
/*     */     
/* 440 */     for (i = vertices; i >= 0; i--) {
/* 441 */       double angleRadians = 6.283185307179586D * (i + 180) / (vertices << 2);
/* 442 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 445 */     GL11.glVertex2d((x + edgeRadius), y);
/* 446 */     GL11.glVertex2d((x + width - edgeRadius), y);
/* 447 */     centerX = x + width - edgeRadius;
/* 448 */     centerY = y + edgeRadius;
/*     */     
/* 450 */     for (i = vertices; i >= 0; i--) {
/* 451 */       double angleRadians = 6.283185307179586D * (i + 90) / (vertices << 2);
/* 452 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 455 */     GL11.glVertex2d((x + width), (y + edgeRadius));
/* 456 */     GL11.glVertex2d((x + width), (y + height - edgeRadius));
/* 457 */     centerX = x + width - edgeRadius;
/* 458 */     centerY = y + height - edgeRadius;
/*     */     
/* 460 */     for (i = vertices; i >= 0; i--) {
/* 461 */       double angleRadians = 6.283185307179586D * i / (vertices << 2);
/* 462 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 465 */     GL11.glVertex2d((x + width - edgeRadius), (y + height));
/* 466 */     GL11.glVertex2d((x + edgeRadius), (y + height));
/* 467 */     centerX = x + edgeRadius;
/* 468 */     centerY = y + height - edgeRadius;
/*     */     
/* 470 */     for (i = vertices; i >= 0; i--) {
/* 471 */       double angleRadians = 6.283185307179586D * (i + 270) / (vertices << 2);
/* 472 */       GL11.glVertex2d(centerX + Math.sin(angleRadians) * edgeRadius, centerY + Math.cos(angleRadians) * edgeRadius);
/*     */     } 
/*     */     
/* 475 */     GL11.glVertex2d(x, (y + height - edgeRadius));
/* 476 */     GL11.glVertex2d(x, (y + edgeRadius));
/* 477 */     GL11.glEnd();
/* 478 */     disableRender2D();
/*     */   }
/*     */   
/*     */   public static int getDisplayWidth() {
/* 482 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/* 483 */     int displayWidth = scaledResolution.getScaledWidth();
/* 484 */     return displayWidth;
/*     */   }
/*     */   
/*     */   public static int getDisplayHeight() {
/* 488 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/* 489 */     int displayHeight = scaledResolution.getScaledHeight();
/* 490 */     return displayHeight;
/*     */   }
/*     */   
/*     */   public static void drawCircle(float x, float y, float radius, float lineWidth, int color) {
/* 494 */     enableRender2D();
/* 495 */     setColor(color);
/* 496 */     GL11.glLineWidth(lineWidth);
/* 497 */     int vertices = (int)Math.min(Math.max(radius, 45.0F), 360.0F);
/* 498 */     GL11.glBegin(2);
/*     */     
/* 500 */     for (int i = 0; i < vertices; i++) {
/* 501 */       double angleRadians = 6.283185307179586D * i / vertices;
/* 502 */       GL11.glVertex2d(x + Math.sin(angleRadians) * radius, y + Math.cos(angleRadians) * radius);
/*     */     } 
/*     */     
/* 505 */     GL11.glEnd();
/* 506 */     disableRender2D();
/*     */   }
/*     */   
/*     */   public static void drawFilledCircle(float x, float y, float radius, int color) {
/* 510 */     enableRender2D();
/* 511 */     setColor(color);
/* 512 */     int vertices = (int)Math.min(Math.max(radius, 45.0F), 360.0F);
/* 513 */     GL11.glBegin(9);
/*     */     
/* 515 */     for (int i = 0; i < vertices; i++) {
/* 516 */       double angleRadians = 6.283185307179586D * i / vertices;
/* 517 */       GL11.glVertex2d(x + Math.sin(angleRadians) * radius, y + Math.cos(angleRadians) * radius);
/*     */     } 
/*     */     
/* 520 */     GL11.glEnd();
/* 521 */     disableRender2D();
/* 522 */     drawCircle(x, y, radius, 1.5F, 16777215);
/*     */   }
/*     */   
/*     */   public static void drawFilledCircleNoBorder(float x, float y, float radius, int color) {
/* 526 */     enableRender2D();
/* 527 */     setColor(color);
/* 528 */     int vertices = (int)Math.min(Math.max(radius, 45.0F), 360.0F);
/* 529 */     GL11.glBegin(9);
/*     */     
/* 531 */     for (int i = 0; i < vertices; i++) {
/* 532 */       double angleRadians = 6.283185307179586D * i / vertices;
/* 533 */       GL11.glVertex2d(x + Math.sin(angleRadians) * radius, y + Math.cos(angleRadians) * radius);
/*     */     } 
/*     */     
/* 536 */     GL11.glEnd();
/* 537 */     disableRender2D();
/*     */   }
/*     */   
/*     */   public static int darker(int hexColor, int factor) {
/* 541 */     float alpha = (hexColor >> 24 & 0xFF);
/* 542 */     float red = Math.max((hexColor >> 16 & 0xFF) - (hexColor >> 16 & 0xFF) / 100.0F / factor, 0.0F);
/* 543 */     float green = Math.max((hexColor >> 8 & 0xFF) - (hexColor >> 8 & 0xFF) / 100.0F / factor, 0.0F);
/* 544 */     float blue = Math.max((hexColor & 0xFF) - (hexColor & 0xFF) / 100.0F / factor, 0.0F);
/* 545 */     return (int)((((int)alpha << 24) + ((int)red << 16) + ((int)green << 8)) + blue);
/*     */   }
/*     */   
/*     */   public static int opacity(int hexColor, int factor) {
/* 549 */     float alpha = Math.max((hexColor >> 24 & 0xFF) - (hexColor >> 24 & 0xFF) / 100.0F / factor, 0.0F);
/* 550 */     float red = (hexColor >> 16 & 0xFF);
/* 551 */     float green = (hexColor >> 8 & 0xFF);
/* 552 */     float blue = (hexColor & 0xFF);
/* 553 */     return (int)((((int)alpha << 24) + ((int)red << 16) + ((int)green << 8)) + blue);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\GuiRenderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */