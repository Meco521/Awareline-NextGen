/*      */ package awareline.main.utility.render.render;
/*      */ 
/*      */ import awareline.main.utility.render.render.shader.ShaderUtils;
/*      */ import java.awt.Color;
/*      */ import java.lang.reflect.Field;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Timer;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RenderUtils
/*      */ {
/*   31 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*   32 */   private static final Frustum frustum = new Frustum();
/*   33 */   private static final ShaderUtils gradientShader = new ShaderUtils("client/advancedshader/shaders/gradient.frag");
/*      */   static float espanimX;
/*      */   static float espanimY;
/*      */   static float espanimZ;
/*      */   
/*      */   public static int darker(int hexColor, int factor) {
/*   39 */     float alpha = (hexColor >> 24 & 0xFF);
/*   40 */     float red = Math.max((hexColor >> 16 & 0xFF) - (hexColor >> 16 & 0xFF) / 100.0F / factor, 0.0F);
/*   41 */     float green = Math.max((hexColor >> 8 & 0xFF) - (hexColor >> 8 & 0xFF) / 100.0F / factor, 0.0F);
/*   42 */     float blue = Math.max((hexColor & 0xFF) - (hexColor & 0xFF) / 100.0F / factor, 0.0F);
/*   43 */     return (int)((((int)alpha << 24) + ((int)red << 16) + ((int)green << 8)) + blue);
/*      */   }
/*      */   
/*      */   public static int colorSwitch(Color firstColor, Color secondColor, float time, int index, long timePerIndex, double speed) {
/*   47 */     return colorSwitch(firstColor, secondColor, time, index, timePerIndex, speed, 255.0D);
/*      */   }
/*      */   
/*      */   public static Color astolfo(boolean clickgui, int yOffset) {
/*   51 */     float speed = clickgui ? 100.0F : 1000.0F;
/*   52 */     float hue = (float)(System.currentTimeMillis() % (int)speed + yOffset);
/*   53 */     if (hue > speed) {
/*   54 */       hue -= speed;
/*      */     }
/*   56 */     if ((hue /= speed) > 0.5F) {
/*   57 */       hue = 0.5F - hue - 0.5F;
/*      */     }
/*   59 */     return Color.getHSBColor(hue += 0.5F, 0.4F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawCustomImage(int x, int y, int width, int height, ResourceLocation image) {
/*   66 */     ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
/*   67 */     GL11.glDisable(2929);
/*   68 */     GL11.glEnable(3042);
/*   69 */     GL11.glDepthMask(false);
/*   70 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*   71 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*   72 */     Minecraft.getMinecraft().getTextureManager().bindTexture(image);
/*   73 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
/*   74 */     GL11.glDepthMask(true);
/*   75 */     GL11.glDisable(3042);
/*   76 */     GL11.glEnable(2929);
/*      */   }
/*      */   
/*      */   public static int colorSwitch(Color firstColor, Color secondColor, float time, int index, long timePerIndex, double speed, double alpha) {
/*   80 */     long now = (long)(speed * System.currentTimeMillis() + (index * timePerIndex));
/*   81 */     float redDiff = (firstColor.getRed() - secondColor.getRed()) / time;
/*   82 */     float greenDiff = (firstColor.getGreen() - secondColor.getGreen()) / time;
/*   83 */     float blueDiff = (firstColor.getBlue() - secondColor.getBlue()) / time;
/*   84 */     int red = Math.round(secondColor.getRed() + redDiff * (float)(now % (long)time));
/*   85 */     int green = Math.round(secondColor.getGreen() + greenDiff * (float)(now % (long)time));
/*   86 */     int blue = Math.round(secondColor.getBlue() + blueDiff * (float)(now % (long)time));
/*   87 */     float redInverseDiff = (secondColor.getRed() - firstColor.getRed()) / time;
/*   88 */     float greenInverseDiff = (secondColor.getGreen() - firstColor.getGreen()) / time;
/*   89 */     float blueInverseDiff = (secondColor.getBlue() - firstColor.getBlue()) / time;
/*   90 */     int inverseRed = Math.round(firstColor.getRed() + redInverseDiff * (float)(now % (long)time));
/*   91 */     int inverseGreen = Math.round(firstColor.getGreen() + greenInverseDiff * (float)(now % (long)time));
/*   92 */     int inverseBlue = Math.round(firstColor.getBlue() + blueInverseDiff * (float)(now % (long)time));
/*   93 */     if (now % (long)time * 2L < (long)time) {
/*   94 */       return (new Color(inverseRed, inverseGreen, inverseBlue, (int)alpha)).getRGB();
/*      */     }
/*   96 */     return (new Color(red, green, blue, (int)alpha)).getRGB();
/*      */   }
/*      */   
/*      */   public static void drawFilledCircle(double x, double y, double r, int c, int id) {
/*  100 */     float f = (c >> 24 & 0xFF) / 255.0F;
/*  101 */     float f1 = (c >> 16 & 0xFF) / 255.0F;
/*  102 */     float f2 = (c >> 8 & 0xFF) / 255.0F;
/*  103 */     float f3 = (c & 0xFF) / 255.0F;
/*  104 */     GL11.glEnable(3042);
/*  105 */     GL11.glDisable(3553);
/*  106 */     GL11.glColor4f(f1, f2, f3, f);
/*  107 */     GL11.glBegin(9);
/*  108 */     if (id == 1) {
/*  109 */       GL11.glVertex2d(x, y);
/*  110 */       for (int i = 0; i <= 90; i++) {
/*  111 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/*  112 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/*  113 */         GL11.glVertex2d(x - x2, y - y2);
/*      */       } 
/*  115 */     } else if (id == 2) {
/*  116 */       GL11.glVertex2d(x, y);
/*  117 */       for (int i = 90; i <= 180; i++) {
/*  118 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/*  119 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/*  120 */         GL11.glVertex2d(x - x2, y - y2);
/*      */       } 
/*  122 */     } else if (id == 3) {
/*  123 */       GL11.glVertex2d(x, y);
/*  124 */       for (int i = 270; i <= 360; i++) {
/*  125 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/*  126 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/*  127 */         GL11.glVertex2d(x - x2, y - y2);
/*      */       } 
/*  129 */     } else if (id == 4) {
/*  130 */       GL11.glVertex2d(x, y);
/*  131 */       for (int i = 180; i <= 270; i++) {
/*  132 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/*  133 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/*  134 */         GL11.glVertex2d(x - x2, y - y2);
/*      */       } 
/*      */     } else {
/*  137 */       for (int i = 0; i <= 360; i++) {
/*  138 */         double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/*  139 */         double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/*  140 */         GL11.glVertex2f((float)(x - x2), (float)(y - y2));
/*      */       } 
/*      */     } 
/*  143 */     GL11.glEnd();
/*  144 */     GL11.glEnable(3553);
/*  145 */     GL11.glDisable(3042);
/*      */   }
/*      */   
/*      */   public static void bindTexture(int texture) {
/*  149 */     GL11.glBindTexture(3553, texture);
/*      */   }
/*      */   
/*      */   public static void rect(double x, double y, double width, double height, Color color) {
/*  153 */     rect(x, y, width, height, true, color);
/*      */   }
/*      */   
/*      */   public static void rect(double x, double y, double width, double height, boolean filled, Color color) {
/*  157 */     start();
/*  158 */     if (color != null) {
/*  159 */       color(color);
/*      */     }
/*  161 */     begin(filled ? 6 : 1);
/*  162 */     vertex(x, y);
/*  163 */     vertex(x + width, y);
/*  164 */     vertex(x + width, y + height);
/*  165 */     vertex(x, y + height);
/*  166 */     if (!filled) {
/*  167 */       vertex(x, y);
/*  168 */       vertex(x, y + height);
/*  169 */       vertex(x + width, y);
/*  170 */       vertex(x + width, y + height);
/*      */     } 
/*  172 */     end();
/*  173 */     stop();
/*      */   }
/*      */   
/*      */   public static void color(Color color) {
/*  177 */     if (color == null) {
/*  178 */       color = Color.white;
/*      */     }
/*  180 */     color((color.getRed() / 255.0F), (color.getGreen() / 255.0F), (color.getBlue() / 255.0F), (color.getAlpha() / 255.0F));
/*      */   }
/*      */   
/*      */   public static void color(double red, double green, double blue, double alpha) {
/*  184 */     GL11.glColor4d(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   public static void start() {
/*  188 */     enable(3042);
/*  189 */     GL11.glBlendFunc(770, 771);
/*  190 */     disable(3553);
/*  191 */     disable(2884);
/*  192 */     GlStateManager.disableAlpha();
/*  193 */     GlStateManager.disableDepth();
/*      */   }
/*      */   
/*      */   public static void vertex(double x, double y) {
/*  197 */     GL11.glVertex2d(x, y);
/*      */   }
/*      */   
/*      */   public static void stop() {
/*  201 */     GlStateManager.enableAlpha();
/*  202 */     GlStateManager.enableDepth();
/*  203 */     enable(2884);
/*  204 */     enable(3553);
/*  205 */     color((new Color(195, 196, 200, 255)).getRGB());
/*      */   }
/*      */   
/*      */   public static void end() {
/*  209 */     GL11.glEnd();
/*      */   }
/*      */   
/*      */   public static void begin(int glMode) {
/*  213 */     GL11.glBegin(glMode);
/*      */   }
/*      */   
/*      */   public static void disable(int glTarget) {
/*  217 */     GL11.glDisable(glTarget);
/*      */   }
/*      */   
/*      */   public static void enable(int glTarget) {
/*  221 */     GL11.glEnable(glTarget);
/*      */   }
/*      */   
/*      */   public static boolean isHovering(float x, float y, float width, float height, int mouseX, int mouseY) {
/*  225 */     return (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Color effect(long offset, int speed) {
/*  231 */     float hue = (float)(System.nanoTime() + offset * speed) / 7.0E9F % 1.0F;
/*  232 */     Color c = Color.getHSBColor(hue, 0.4F, 0.8F);
/*  233 */     return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, 1.0F, c.getAlpha() / 255.0F);
/*      */   }
/*      */   
/*      */   public static void drawRectSized(float x, float y, float width, float height, int color) {
/*  237 */     drawRect(x, y, (x + width), (y + height), color);
/*      */   }
/*      */   
/*      */   public static void fadeRect(float x, float y, float width, float height) {
/*  241 */     RoundedUtils.drawGradientRound(x, y, width, height, 5.0F, new Color(-2134237707, true), new Color(-2143564841, true), new Color(-2135849259, true), new Color(-2143260205, true));
/*      */   }
/*      */   
/*      */   public static double lerp(double v0, double v1, double t) {
/*  245 */     return (1.0D - t) * v0 + t * v1;
/*      */   }
/*      */   
/*      */   public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
/*  249 */     GLUtils.setup2DRendering();
/*  250 */     GL11.glEnable(2848);
/*  251 */     GL11.glShadeModel(7425);
/*  252 */     GL11.glPushMatrix();
/*  253 */     GL11.glBegin(7);
/*  254 */     color(startColor);
/*  255 */     GL11.glVertex2d(left, top);
/*  256 */     GL11.glVertex2d(left, bottom);
/*  257 */     color(endColor);
/*  258 */     GL11.glVertex2d(right, bottom);
/*  259 */     GL11.glVertex2d(right, top);
/*  260 */     GL11.glEnd();
/*  261 */     GL11.glPopMatrix();
/*  262 */     GL11.glDisable(2848);
/*  263 */     GLUtils.end2DRendering();
/*  264 */     resetColor();
/*      */   }
/*      */   
/*      */   public static void drawGradientRectBordered(double left, double top, double right, double bottom, double width, int startColor, int endColor, int borderStartColor, int borderEndColor) {
/*  268 */     drawGradientRect(left + width, top + width, right - width, bottom - width, startColor, endColor);
/*  269 */     drawGradientRect(left + width, top, right - width, top + width, borderStartColor, borderEndColor);
/*  270 */     drawGradientRect(left, top, left + width, bottom, borderStartColor, borderEndColor);
/*  271 */     drawGradientRect(right - width, top, right, bottom, borderStartColor, borderEndColor);
/*  272 */     drawGradientRect(left + width, bottom - width, right - width, bottom, borderStartColor, borderEndColor);
/*      */   }
/*      */   
/*      */   public static void drawGradientRect(float x, float y, float width, float height, int firstColor, int secondColor, boolean perpendicular) {
/*  276 */     GL11.glPushMatrix();
/*  277 */     GL11.glEnable(3042);
/*  278 */     GL11.glDisable(3553);
/*  279 */     GL11.glBlendFunc(770, 771);
/*  280 */     GL11.glEnable(2848);
/*  281 */     GL11.glPushMatrix();
/*  282 */     GL11.glShadeModel(7425);
/*  283 */     GL11.glBegin(7);
/*  284 */     color(firstColor);
/*  285 */     GL11.glVertex2d(width, y);
/*  286 */     if (perpendicular) {
/*  287 */       color(secondColor);
/*      */     }
/*  289 */     GL11.glVertex2d(x, y);
/*  290 */     color(secondColor);
/*  291 */     GL11.glVertex2d(x, height);
/*  292 */     if (perpendicular) {
/*  293 */       color(firstColor);
/*      */     }
/*  295 */     GL11.glVertex2d(width, height);
/*  296 */     GL11.glEnd();
/*  297 */     GL11.glShadeModel(7424);
/*  298 */     GL11.glPopMatrix();
/*  299 */     GL11.glEnable(3553);
/*  300 */     GL11.glDisable(3042);
/*  301 */     GL11.glDisable(2848);
/*  302 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static Color toColorRGB(int rgb, float alpha) {
/*  306 */     float[] rgba = convertRGB(rgb);
/*  307 */     return new Color(rgba[0], rgba[1], rgba[2], alpha / 255.0F);
/*      */   }
/*      */   
/*      */   public static float[] convertRGB(int rgb) {
/*  311 */     float a = (rgb >> 24 & 0xFF) / 255.0F;
/*  312 */     float r = (rgb >> 16 & 0xFF) / 255.0F;
/*  313 */     float g = (rgb >> 8 & 0xFF) / 255.0F;
/*  314 */     float b = (rgb & 0xFF) / 255.0F;
/*  315 */     return new float[] { r, g, b, a };
/*      */   }
/*      */   
/*      */   public static int reAlpha(int color, float alpha) {
/*  319 */     Color c = new Color(color);
/*  320 */     float r = 0.003921569F * c.getRed();
/*  321 */     float g = 0.003921569F * c.getGreen();
/*  322 */     float b = 0.003921569F * c.getBlue();
/*  323 */     return (new Color(r, g, b, alpha)).getRGB();
/*      */   }
/*      */   
/*      */   public static void scaleStart(float x, float y, float scale) {
/*  327 */     GlStateManager.pushMatrix();
/*  328 */     GlStateManager.translate(x, y, 0.0F);
/*  329 */     GlStateManager.scale(scale, scale, 1.0F);
/*  330 */     GlStateManager.translate(-x, -y, 0.0F);
/*      */   }
/*      */   
/*      */   public static void scaleEnd() {
/*  334 */     GlStateManager.popMatrix();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius) {
/*  341 */     if (paramXStart > paramXEnd) {
/*  342 */       float z = paramXStart;
/*  343 */       paramXStart = paramXEnd;
/*  344 */       paramXEnd = z;
/*      */     } 
/*  346 */     if (paramYStart > paramYEnd) {
/*  347 */       float z = paramYStart;
/*  348 */       paramYStart = paramYEnd;
/*  349 */       paramYEnd = z;
/*      */     } 
/*  351 */     double x1 = (paramXStart + radius);
/*  352 */     double y1 = (paramYStart + radius);
/*  353 */     double x2 = (paramXEnd - radius);
/*  354 */     double y2 = (paramYEnd - radius);
/*  355 */     GL11.glEnable(2848);
/*  356 */     GL11.glLineWidth(1.0F);
/*  357 */     GL11.glBegin(9);
/*  358 */     double degree = 0.017453292519943295D; double i;
/*  359 */     for (i = 0.0D; i <= 90.0D; i++) {
/*  360 */       GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
/*      */     }
/*  362 */     for (i = 90.0D; i <= 180.0D; i++) {
/*  363 */       GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
/*      */     }
/*  365 */     for (i = 180.0D; i <= 270.0D; i++) {
/*  366 */       GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
/*      */     }
/*  368 */     for (i = 270.0D; i <= 360.0D; i++) {
/*  369 */       GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
/*      */     }
/*  371 */     GL11.glEnd();
/*  372 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawHead(ResourceLocation skin, int x, int y, int width, int height, float alpha) {
/*  376 */     GL11.glDisable(2929);
/*  377 */     GL11.glEnable(3042);
/*  378 */     GL11.glDepthMask(false);
/*  379 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  380 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
/*  381 */     mc.getTextureManager().bindTexture(skin);
/*  382 */     Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
/*  383 */     GL11.glDepthMask(true);
/*  384 */     GL11.glDisable(3042);
/*  385 */     GL11.glEnable(2929);
/*      */   }
/*      */   
/*      */   public static Timer getTimer() {
/*      */     try {
/*  390 */       Class<Minecraft> c = Minecraft.class;
/*  391 */       Field f = c.getDeclaredField(new String(new char[] { 't', 'i', 'm', 'e', 'r' }));
/*  392 */       f.setAccessible(true);
/*  393 */       return (Timer)f.get(mc);
/*      */     }
/*  395 */     catch (Exception er) {
/*      */       try {
/*  397 */         Class<Minecraft> c2 = Minecraft.class;
/*  398 */         Field f2 = c2.getDeclaredField(new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', '8', '_', 'T' }));
/*  399 */         f2.setAccessible(true);
/*  400 */         return (Timer)f2.get(mc);
/*      */       }
/*  402 */       catch (Exception er2) {
/*  403 */         return null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawAxisAlignedBB(AxisAlignedBB axisalignedbb, float r, float g, float b) {
/*  409 */     float alpha = 0.25F;
/*  410 */     Tessellator tessellator = Tessellator.getInstance();
/*  411 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*  412 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  413 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  414 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  415 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  416 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  417 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  418 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  419 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  420 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  421 */     tessellator.draw();
/*  422 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  423 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  424 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  425 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  426 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  427 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  428 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  429 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  430 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  431 */     tessellator.draw();
/*  432 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  433 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  434 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  435 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  436 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  437 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  438 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  439 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  440 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  441 */     tessellator.draw();
/*  442 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  443 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  444 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  445 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  446 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  447 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  448 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  449 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  450 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  451 */     tessellator.draw();
/*  452 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  453 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  454 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  455 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  456 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  457 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  458 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  459 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  460 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  461 */     tessellator.draw();
/*  462 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  463 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  464 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  465 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  466 */     worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  467 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  468 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, 0.25F).endVertex();
/*  469 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  470 */     worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, 0.25F).endVertex();
/*  471 */     tessellator.draw();
/*      */   }
/*      */   
/*      */   public static void prepareScissorBox(float x, float y, float x2, float y2) {
/*  475 */     ScaledResolution scale = new ScaledResolution(mc);
/*  476 */     int factor = scale.getScaleFactor();
/*  477 */     GL11.glScissor((int)(x * factor), (int)((scale.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
/*      */   }
/*      */   
/*      */   public static Color blend(Color color1, Color color2, double ratio) {
/*  481 */     float r = (float)ratio;
/*  482 */     float ir = 1.0F - r;
/*  483 */     float[] rgb1 = new float[3];
/*  484 */     float[] rgb2 = new float[3];
/*  485 */     color1.getColorComponents(rgb1);
/*  486 */     color2.getColorComponents(rgb2);
/*  487 */     return new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
/*      */   }
/*      */   
/*      */   public static void drawGoodCircle(double x, double y, float radius, int color) {
/*  491 */     color(color);
/*  492 */     GLUtils.setup2DRendering();
/*  493 */     GL11.glEnable(2832);
/*  494 */     GL11.glHint(3153, 4354);
/*  495 */     GL11.glPointSize(radius * (2 * mc.gameSettings.guiScale));
/*  496 */     GL11.glBegin(0);
/*  497 */     GL11.glVertex2d(x, y);
/*  498 */     GL11.glEnd();
/*  499 */     GLUtils.end2DRendering();
/*      */   }
/*      */   
/*      */   public static Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean depth) {
/*  503 */     if (needsNewFramebuffer(framebuffer)) {
/*  504 */       if (framebuffer != null) {
/*  505 */         framebuffer.deleteFramebuffer();
/*      */       }
/*  507 */       return new Framebuffer(mc.displayWidth, mc.displayHeight, depth);
/*      */     } 
/*  509 */     return framebuffer;
/*      */   }
/*      */   
/*      */   public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
/*  513 */     if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
/*  514 */       if (framebuffer != null) {
/*  515 */         framebuffer.deleteFramebuffer();
/*      */       }
/*  517 */       return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
/*      */     } 
/*  519 */     return framebuffer;
/*      */   }
/*      */   
/*      */   public static void start2D() {
/*  523 */     GL11.glEnable(3042);
/*  524 */     GL11.glDisable(3553);
/*  525 */     GL11.glBlendFunc(770, 771);
/*  526 */     GL11.glEnable(2848);
/*      */   }
/*      */   
/*      */   public static void stop2D() {
/*  530 */     GL11.glEnable(3553);
/*  531 */     GL11.glDisable(3042);
/*  532 */     GL11.glDisable(2848);
/*  533 */     GlStateManager.enableTexture2D();
/*  534 */     GlStateManager.disableBlend();
/*  535 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static int getHealthColor(@NotNull EntityLivingBase player) {
/*  539 */     if (player == null) $$$reportNull$$$1(0);  if (player == null) {
/*  540 */       $$$reportNull$$$0(0);
/*      */     }
/*  542 */     float f = player.getHealth();
/*  543 */     float f1 = player.getMaxHealth();
/*  544 */     float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
/*  545 */     return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
/*      */   }
/*      */   
/*      */   public static void drawCornerBox(double x, double y, double x2, double y2, double lw, Color color) {
/*  549 */     double width = Math.abs(x2 - x);
/*  550 */     double height = Math.abs(y2 - y);
/*  551 */     double halfWidth = width / 4.0D;
/*  552 */     double halfHeight = height / 4.0D;
/*  553 */     start2D();
/*  554 */     GL11.glPushMatrix();
/*  555 */     GL11.glLineWidth((float)lw);
/*  556 */     setColor(color.getRGB());
/*  557 */     GL11.glBegin(3);
/*  558 */     GL11.glVertex2d(x + halfWidth, y);
/*  559 */     GL11.glVertex2d(x, y);
/*  560 */     GL11.glVertex2d(x, y + halfHeight);
/*  561 */     GL11.glEnd();
/*  562 */     GL11.glBegin(3);
/*  563 */     GL11.glVertex2d(x, y + height - halfHeight);
/*  564 */     GL11.glVertex2d(x, y + height);
/*  565 */     GL11.glVertex2d(x + halfWidth, y + height);
/*  566 */     GL11.glEnd();
/*  567 */     GL11.glBegin(3);
/*  568 */     GL11.glVertex2d(x + width - halfWidth, y + height);
/*  569 */     GL11.glVertex2d(x + width, y + height);
/*  570 */     GL11.glVertex2d(x + width, y + height - halfHeight);
/*  571 */     GL11.glEnd();
/*  572 */     GL11.glBegin(3);
/*  573 */     GL11.glVertex2d(x + width, y + halfHeight);
/*  574 */     GL11.glVertex2d(x + width, y);
/*  575 */     GL11.glVertex2d(x + width - halfWidth, y);
/*  576 */     GL11.glEnd();
/*  577 */     GL11.glPopMatrix();
/*  578 */     stop2D();
/*      */   }
/*      */   
/*      */   public static double interpolate(double current, double old, double scale) {
/*  582 */     return old + (current - old) * scale;
/*      */   }
/*      */   
/*      */   public static float toanim(float now, float end, float multiplier, float min) {
/*  586 */     float beterspeedinfps = 120.0F / Minecraft.getDebugFPS();
/*  587 */     float speed = Math.max(Math.abs(now - end) / multiplier, min) * beterspeedinfps;
/*  588 */     if (now < end) {
/*  589 */       now = (now + speed > end) ? end : (now += speed);
/*  590 */     } else if (now > end) {
/*  591 */       now = (now - speed < end) ? end : (now -= speed);
/*      */     } 
/*  593 */     return now;
/*      */   }
/*      */   
/*      */   public static boolean needsNewFramebuffer(Framebuffer framebuffer) {
/*  597 */     return (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight);
/*      */   }
/*      */   
/*      */   public static void resetColor() {
/*  601 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static void setAlphaLimit(float limit) {
/*  605 */     GlStateManager.enableAlpha();
/*  606 */     GlStateManager.alphaFunc(516, (float)(limit * 0.01D));
/*      */   }
/*      */   
/*      */   public static boolean isInViewFrustrum(Entity entity) {
/*  610 */     return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
/*      */   }
/*      */   
/*      */   private static boolean isInViewFrustrum(AxisAlignedBB bb) {
/*  614 */     Entity current = Minecraft.getMinecraft().getRenderViewEntity();
/*  615 */     frustum.setPosition(current.posX, current.posY, current.posZ);
/*  616 */     return frustum.isBoundingBoxInFrustum(bb);
/*      */   }
/*      */   
/*      */   public static void drawBoundingBox(AxisAlignedBB aa) {
/*  620 */     Tessellator tessellator = Tessellator.getInstance();
/*  621 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*  622 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  623 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  624 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  625 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  626 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  627 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  628 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  629 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  630 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  631 */     tessellator.draw();
/*  632 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  633 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  634 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  635 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  636 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  637 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  638 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  639 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  640 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  641 */     tessellator.draw();
/*  642 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  643 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  644 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  645 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  646 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  647 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  648 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  649 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  650 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  651 */     tessellator.draw();
/*  652 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  653 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  654 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  655 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  656 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  657 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  658 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  659 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  660 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  661 */     tessellator.draw();
/*  662 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  663 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  664 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  665 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  666 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  667 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  668 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  669 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  670 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  671 */     tessellator.draw();
/*  672 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  673 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  674 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  675 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  676 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  677 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  678 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  679 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  680 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  681 */     tessellator.draw();
/*      */   }
/*      */   
/*      */   public static int getRGB(int r, int g, int b) {
/*  685 */     return getRGB(r, g, b, 255);
/*      */   }
/*      */   
/*      */   public static int getRGB(int r, int g, int b, int a) {
/*  689 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) << 0;
/*      */   }
/*      */   
/*      */   public static void color(int color) {
/*  693 */     float f = (color >> 24 & 0xFF) / 255.0F;
/*  694 */     float f1 = (color >> 16 & 0xFF) / 255.0F;
/*  695 */     float f2 = (color >> 8 & 0xFF) / 255.0F;
/*  696 */     float f3 = (color & 0xFF) / 255.0F;
/*  697 */     GL11.glColor4f(f1, f2, f3, f);
/*      */   }
/*      */   
/*      */   public static void color(int color, float alpha) {
/*  701 */     float r = (color >> 16 & 0xFF) / 255.0F;
/*  702 */     float g = (color >> 8 & 0xFF) / 255.0F;
/*  703 */     float b = (color & 0xFF) / 255.0F;
/*  704 */     GlStateManager.color(r, g, b, alpha);
/*      */   }
/*      */   
/*      */   public static int width() {
/*  708 */     return (new ScaledResolution(Minecraft.getMinecraft())).getScaledWidth();
/*      */   }
/*      */   
/*      */   public static int height() {
/*  712 */     return (new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight();
/*      */   }
/*      */   
/*      */   public static void glColor(int hex) {
/*  716 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/*  717 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/*  718 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/*  719 */     float blue = (hex & 0xFF) / 255.0F;
/*  720 */     GL11.glColor4f(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   public static void glColor(Color color, int alpha) {
/*  724 */     glColor(color, alpha / 255.0F);
/*      */   }
/*      */   
/*      */   public static void glColor(Color color, float alpha) {
/*  728 */     float red = color.getRed() / 255.0F;
/*  729 */     float green = color.getGreen() / 255.0F;
/*  730 */     float blue = color.getBlue() / 255.0F;
/*  731 */     GlStateManager.color(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   public static void enableGL2D() {
/*  735 */     GL11.glDisable(2929);
/*  736 */     GL11.glEnable(3042);
/*  737 */     GL11.glDisable(3553);
/*  738 */     GL11.glBlendFunc(770, 771);
/*  739 */     GL11.glDepthMask(true);
/*  740 */     GL11.glEnable(2848);
/*  741 */     GL11.glHint(3154, 4354);
/*  742 */     GL11.glHint(3155, 4354);
/*      */   }
/*      */   
/*      */   public static void disableGL2D() {
/*  746 */     GL11.glEnable(3553);
/*  747 */     GL11.glDisable(3042);
/*  748 */     GL11.glEnable(2929);
/*  749 */     GL11.glDisable(2848);
/*  750 */     GL11.glHint(3154, 4352);
/*  751 */     GL11.glHint(3155, 4352);
/*      */   }
/*      */   
/*      */   public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
/*  755 */     enableGL2D();
/*  756 */     GL11.glShadeModel(7425);
/*  757 */     GL11.glBegin(7);
/*  758 */     glColor(topColor);
/*  759 */     GL11.glVertex2f(x, y1);
/*  760 */     GL11.glVertex2f(x1, y1);
/*  761 */     glColor(bottomColor);
/*  762 */     GL11.glVertex2f(x1, y);
/*  763 */     GL11.glVertex2f(x, y);
/*  764 */     GL11.glEnd();
/*  765 */     GL11.glShadeModel(7424);
/*  766 */     disableGL2D();
/*      */   }
/*      */   
/*      */   public static void drawGradient(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
/*  770 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*  771 */     resetColor();
/*  772 */     GlStateManager.enableBlend();
/*  773 */     GlStateManager.blendFunc(770, 771);
/*  774 */     gradientShader.init();
/*  775 */     gradientShader.setUniformf("location", new float[] { x * sr.getScaleFactor(), (Minecraft.getMinecraft()).displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor() });
/*  776 */     gradientShader.setUniformf("rectSize", new float[] { width * sr.getScaleFactor(), height * sr.getScaleFactor() });
/*  777 */     gradientShader.setUniformf("alpha", new float[] { alpha });
/*  778 */     gradientShader.setUniformf("color1", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F });
/*  779 */     gradientShader.setUniformf("color2", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F });
/*  780 */     gradientShader.setUniformf("color3", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F });
/*  781 */     gradientShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F });
/*  782 */     ShaderUtils.drawQuads(x, y, width, height);
/*  783 */     gradientShader.unload();
/*  784 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void startDrawing() {
/*  788 */     GL11.glEnable(3042);
/*  789 */     GL11.glEnable(3042);
/*  790 */     GL11.glBlendFunc(770, 771);
/*  791 */     GL11.glEnable(2848);
/*  792 */     GL11.glDisable(3553);
/*  793 */     GL11.glDisable(2929);
/*  794 */     mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
/*      */   }
/*      */   
/*      */   public static void stopDrawing() {
/*  798 */     GL11.glDisable(3042);
/*  799 */     GL11.glEnable(3553);
/*  800 */     GL11.glDisable(2848);
/*  801 */     GL11.glDisable(3042);
/*  802 */     GL11.glEnable(2929);
/*      */   }
/*      */   
/*      */   public static void pre() {
/*  806 */     GL11.glDisable(2929);
/*  807 */     GL11.glDisable(3553);
/*  808 */     GL11.glEnable(3042);
/*  809 */     GL11.glBlendFunc(770, 771);
/*      */   }
/*      */   
/*      */   public static void post() {
/*  813 */     GL11.glDisable(3042);
/*  814 */     GL11.glEnable(3553);
/*  815 */     GL11.glEnable(2929);
/*  816 */     GL11.glColor3d(1.0D, 1.0D, 1.0D);
/*      */   }
/*      */   
/*      */   public static void drawRect2(double x, double y, double width, double height, int color) {
/*  820 */     Gui.drawRect(x, y, x + width, y + height, color);
/*      */   }
/*      */   
/*      */   public static void drawRect(float x, float y, float x1, float y1, int color) {
/*  824 */     Gui.drawRect(x, y, x1, y1, color);
/*      */   }
/*      */   
/*      */   public static void drawRect(float x, float y, double x1, float y1, Color color) {
/*  828 */     Gui.drawRect(x, y, x1, y1, color.getRGB());
/*      */   }
/*      */   
/*      */   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
/*  832 */     Tessellator tessellator = Tessellator.getInstance();
/*  833 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*  834 */     worldRenderer.begin(3, DefaultVertexFormats.POSITION);
/*  835 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  836 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  837 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  838 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  839 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  840 */     tessellator.draw();
/*  841 */     worldRenderer.begin(3, DefaultVertexFormats.POSITION);
/*  842 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  843 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  844 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  845 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  846 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  847 */     tessellator.draw();
/*  848 */     worldRenderer.begin(1, DefaultVertexFormats.POSITION);
/*  849 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/*  850 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/*  851 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/*  852 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/*  853 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/*  854 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/*  855 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/*  856 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/*  857 */     tessellator.draw();
/*      */   }
/*      */   
/*      */   public static void pre3D() {
/*  861 */     GL11.glPushMatrix();
/*  862 */     GL11.glEnable(3042);
/*  863 */     GL11.glBlendFunc(770, 771);
/*  864 */     GL11.glShadeModel(7425);
/*  865 */     GL11.glDisable(3553);
/*  866 */     GL11.glEnable(2848);
/*  867 */     GL11.glDisable(2929);
/*  868 */     GL11.glDisable(2896);
/*  869 */     GL11.glDepthMask(false);
/*  870 */     GL11.glHint(3154, 4354);
/*      */   }
/*      */   
/*      */   public static void post3D() {
/*  874 */     GL11.glDepthMask(true);
/*  875 */     GL11.glEnable(2929);
/*  876 */     GL11.glDisable(2848);
/*  877 */     GL11.glEnable(3553);
/*  878 */     GL11.glDisable(3042);
/*  879 */     GL11.glPopMatrix();
/*  880 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static void enableRender2D() {
/*  884 */     GL11.glEnable(3042);
/*  885 */     GL11.glDisable(2884);
/*  886 */     GL11.glDisable(3553);
/*  887 */     GL11.glEnable(2848);
/*  888 */     GL11.glBlendFunc(770, 771);
/*  889 */     GL11.glLineWidth(1.0F);
/*      */   }
/*      */   
/*      */   public static void disableRender2D() {
/*  893 */     GL11.glDisable(3042);
/*  894 */     GL11.glEnable(2884);
/*  895 */     GL11.glEnable(3553);
/*  896 */     GL11.glDisable(2848);
/*  897 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  898 */     GlStateManager.shadeModel(7424);
/*  899 */     GlStateManager.disableBlend();
/*  900 */     GlStateManager.enableTexture2D();
/*      */   }
/*      */   
/*      */   public static void drawRect(double x2, double y2, double x1, double y1, int color) {
/*  904 */     enableRender2D();
/*  905 */     setColor(color);
/*  906 */     drawRect(x2, y2, x1, y1);
/*  907 */     disableRender2D();
/*      */   }
/*      */   
/*      */   public static void drawRect(double x2, double y2, double x1, double y1) {
/*  911 */     GL11.glBegin(7);
/*  912 */     GL11.glVertex2d(x2, y1);
/*  913 */     GL11.glVertex2d(x1, y1);
/*  914 */     GL11.glVertex2d(x1, y2);
/*  915 */     GL11.glVertex2d(x2, y2);
/*  916 */     GL11.glEnd();
/*      */   }
/*      */   
/*      */   public static void setColor(int colorHex) {
/*  920 */     float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
/*  921 */     float red = (colorHex >> 16 & 0xFF) / 255.0F;
/*  922 */     float green = (colorHex >> 8 & 0xFF) / 255.0F;
/*  923 */     float blue = (colorHex & 0xFF) / 255.0F;
/*  924 */     GL11.glColor4f(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   public static void drawFilledCircleNoGL(int x, int y, double r, int c, int quality) {
/*  928 */     float f = (c >> 24 & 0xFF) / 255.0F;
/*  929 */     float f1 = (c >> 16 & 0xFF) / 255.0F;
/*  930 */     float f2 = (c >> 8 & 0xFF) / 255.0F;
/*  931 */     float f3 = (c & 0xFF) / 255.0F;
/*  932 */     GL11.glColor4f(f1, f2, f3, f);
/*  933 */     GL11.glBegin(6);
/*  934 */     for (int i = 0; i <= 360 / quality; i++) {
/*  935 */       double x2 = Math.sin((i * quality) * Math.PI / 180.0D) * r;
/*  936 */       double y2 = Math.cos((i * quality) * Math.PI / 180.0D) * r;
/*  937 */       GL11.glVertex2d(x + x2, y + y2);
/*      */     } 
/*  939 */     GL11.glEnd();
/*      */   }
/*      */   
/*      */   public static void drawModel(float yaw, float pitch, EntityLivingBase entityLivingBase) {
/*  943 */     GlStateManager.resetColor();
/*  944 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  945 */     GlStateManager.enableColorMaterial();
/*  946 */     GlStateManager.pushMatrix();
/*  947 */     GlStateManager.translate(0.0F, 0.0F, 50.0F);
/*  948 */     GlStateManager.scale(-50.0F, 50.0F, 50.0F);
/*  949 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*  950 */     float renderYawOffset = entityLivingBase.renderYawOffset;
/*  951 */     float rotationYaw = entityLivingBase.rotationYaw;
/*  952 */     float rotationPitch = entityLivingBase.rotationPitch;
/*  953 */     float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
/*  954 */     float rotationYawHead = entityLivingBase.rotationYawHead;
/*  955 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/*  956 */     RenderHelper.enableStandardItemLighting();
/*  957 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/*  958 */     GlStateManager.rotate((float)(-Math.atan((pitch / 40.0F)) * 20.0D), 1.0F, 0.0F, 0.0F);
/*  959 */     entityLivingBase.renderYawOffset = yaw - yaw / yaw * 0.4F;
/*  960 */     entityLivingBase.rotationYaw = yaw - yaw / yaw * 0.2F;
/*  961 */     entityLivingBase.rotationPitch = pitch;
/*  962 */     entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
/*  963 */     entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
/*  964 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/*  965 */     RenderManager renderManager = mc.getRenderManager();
/*  966 */     renderManager.setPlayerViewY(180.0F);
/*  967 */     renderManager.setRenderShadow(false);
/*  968 */     renderManager.renderEntityWithPosYaw((Entity)entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
/*  969 */     renderManager.setRenderShadow(true);
/*  970 */     entityLivingBase.renderYawOffset = renderYawOffset;
/*  971 */     entityLivingBase.rotationYaw = rotationYaw;
/*  972 */     entityLivingBase.rotationPitch = rotationPitch;
/*  973 */     entityLivingBase.prevRotationYawHead = prevRotationYawHead;
/*  974 */     entityLivingBase.rotationYawHead = rotationYawHead;
/*  975 */     GlStateManager.popMatrix();
/*  976 */     RenderHelper.disableStandardItemLighting();
/*  977 */     GlStateManager.disableRescaleNormal();
/*  978 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  979 */     GlStateManager.disableTexture2D();
/*  980 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  981 */     GlStateManager.resetColor();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
/*  987 */     drawRect(x, y, x2, y2, col2);
/*  988 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  989 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  990 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  991 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  992 */     GL11.glEnable(3042);
/*  993 */     GL11.glDisable(3553);
/*  994 */     GL11.glBlendFunc(770, 771);
/*  995 */     GL11.glEnable(2848);
/*  996 */     GL11.glPushMatrix();
/*  997 */     GL11.glColor4f(f1, f2, f3, f);
/*  998 */     GL11.glLineWidth(l1);
/*  999 */     GL11.glBegin(1);
/* 1000 */     GL11.glVertex2d(x, y);
/* 1001 */     GL11.glVertex2d(x, y2);
/* 1002 */     GL11.glVertex2d(x2, y2);
/* 1003 */     GL11.glVertex2d(x2, y);
/* 1004 */     GL11.glVertex2d(x, y);
/* 1005 */     GL11.glVertex2d(x2, y);
/* 1006 */     GL11.glVertex2d(x, y2);
/* 1007 */     GL11.glVertex2d(x2, y2);
/* 1008 */     GL11.glEnd();
/* 1009 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1010 */     GL11.glPopMatrix();
/* 1011 */     GlStateManager.enableTexture2D();
/* 1012 */     GlStateManager.disableBlend();
/* 1013 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 255.0F);
/* 1014 */     GL11.glEnable(3553);
/* 1015 */     GL11.glDisable(3042);
/* 1016 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   private static void $$$reportNull$$$0(int n) {
/* 1020 */     throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "player", "client/advancedshader/utils/render/RenderUtils", "getHealthColor" }));
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\RenderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */