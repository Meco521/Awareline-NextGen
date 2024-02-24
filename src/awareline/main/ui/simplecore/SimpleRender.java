/*     */ package awareline.main.ui.simplecore;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.InstanceAccess;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import awareline.main.utility.render.render.blur.util.GLUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class SimpleRender
/*     */   implements InstanceAccess
/*     */ {
/*     */   public static double delta;
/*     */   
/*     */   public static int width() {
/*  23 */     return Client.instance.getScaledResolution().getScaledWidth();
/*     */   }
/*     */   
/*     */   public static int height() {
/*  27 */     return Client.instance.getScaledResolution().getScaledHeight();
/*     */   }
/*     */   
/*     */   public static double interpolate(double current, double old, double scale) {
/*  31 */     return old + (current - old) * scale;
/*     */   }
/*     */   
/*     */   public static float processFPS(float defV) {
/*  35 */     float defF = 1000.0F;
/*  36 */     int limitFPS = Math.abs(Minecraft.getDebugFPS());
/*  37 */     return defV / ((limitFPS <= 0) ? 1.0F : (limitFPS / 1000.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCircle(float x, float y, float r, float lineWidth, boolean isFull, int color) {
/*  42 */     drawCircle(x, y, r, 10, lineWidth, 360, isFull, color);
/*     */   }
/*     */   
/*     */   public static void drawCircle(float cx, float cy, double r, int segments, float lineWidth, int part, boolean isFull, int c) {
/*  46 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  47 */     r *= 2.0D;
/*  48 */     cx *= 2.0F;
/*  49 */     cy *= 2.0F;
/*  50 */     float f2 = (c >> 24 & 0xFF) / 255.0F;
/*  51 */     float f3 = (c >> 16 & 0xFF) / 255.0F;
/*  52 */     float f4 = (c >> 8 & 0xFF) / 255.0F;
/*  53 */     float f5 = (c & 0xFF) / 255.0F;
/*  54 */     GL11.glEnable(3042);
/*  55 */     GL11.glLineWidth(lineWidth);
/*  56 */     GL11.glDisable(3553);
/*  57 */     GL11.glEnable(2848);
/*  58 */     GL11.glBlendFunc(770, 771);
/*  59 */     GL11.glColor4f(f3, f4, f5, f2);
/*  60 */     GL11.glBegin(3);
/*  61 */     for (int i = segments - part; i <= segments; i++) {
/*  62 */       double x = Math.sin(i * Math.PI / 180.0D) * r;
/*  63 */       double y = Math.cos(i * Math.PI / 180.0D) * r;
/*  64 */       GL11.glVertex2d(cx + x, cy + y);
/*  65 */       if (isFull)
/*  66 */         GL11.glVertex2d(cx, cy); 
/*     */     } 
/*  68 */     GL11.glEnd();
/*  69 */     GL11.glDisable(2848);
/*  70 */     GL11.glEnable(3553);
/*  71 */     GL11.glDisable(3042);
/*  72 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/*     */   }
/*     */   
/*     */   public static Color getBlendColor(double current, double max) {
/*  76 */     long base = Math.round(max / 5.0D);
/*  77 */     if (current >= (base * 5L)) {
/*  78 */       return new Color(15, 255, 15);
/*     */     }
/*  80 */     if (current >= (base << 2L)) {
/*  81 */       return new Color(166, 255, 0);
/*     */     }
/*  83 */     if (current >= (base * 3L)) {
/*  84 */       return new Color(255, 191, 0);
/*     */     }
/*  86 */     if (current >= (base << 1L)) {
/*  87 */       return new Color(255, 89, 0);
/*     */     }
/*  89 */     return new Color(255, 0, 0);
/*     */   }
/*     */   
/*     */   public static void enableRender2D() {
/*  93 */     GL11.glEnable(3042);
/*  94 */     GL11.glDisable(2884);
/*  95 */     GL11.glDisable(3553);
/*  96 */     GL11.glEnable(2848);
/*  97 */     GL11.glBlendFunc(770, 771);
/*  98 */     GL11.glLineWidth(1.0F);
/*     */   }
/*     */   
/*     */   public static void disableRender2D() {
/* 102 */     GL11.glDisable(3042);
/* 103 */     GL11.glEnable(2884);
/* 104 */     GL11.glEnable(3553);
/* 105 */     GL11.glDisable(2848);
/* 106 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 107 */     GlStateManager.shadeModel(7424);
/* 108 */     GlStateManager.disableBlend();
/* 109 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */   
/*     */   public static void setColor(int colorHex) {
/* 113 */     float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
/* 114 */     float red = (colorHex >> 16 & 0xFF) / 255.0F;
/* 115 */     float green = (colorHex >> 8 & 0xFF) / 255.0F;
/* 116 */     float blue = (colorHex & 0xFF) / 255.0F;
/* 117 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect(double left, double top, double right, double bottom, int color) {
/* 122 */     RenderUtil.drawRect(left, top, right, bottom, color);
/*     */   }
/*     */   
/*     */   public static void drawRectFloat(float left, float top, float right, float bottom, int color) {
/* 126 */     RenderUtil.drawRect(left, top, right, bottom, color);
/*     */   }
/*     */   
/*     */   public static void drawRect3(float d, float e, float g, float h, int color) {
/* 130 */     if (d < g) {
/* 131 */       int i = (int)d;
/* 132 */       d = g;
/* 133 */       g = i;
/*     */     } 
/* 135 */     if (e < h) {
/* 136 */       int j = (int)e;
/* 137 */       e = h;
/* 138 */       h = j;
/*     */     } 
/* 140 */     RenderUtil.resetColor();
/* 141 */     RenderUtil.setAlphaLimit(0.0F);
/* 142 */     GLUtil.setup2DRendering(true);
/* 143 */     Tessellator tessellator = Tessellator.getInstance();
/* 144 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 145 */     GlStateManager.enableBlend();
/* 146 */     GlStateManager.disableTexture2D();
/* 147 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 148 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 149 */     worldrenderer.pos(d, h, 0.0D).color(color).endVertex();
/* 150 */     worldrenderer.pos(g, h, 0.0D).color(color).endVertex();
/* 151 */     worldrenderer.pos(g, e, 0.0D).color(color).endVertex();
/* 152 */     worldrenderer.pos(d, e, 0.0D).color(color).endVertex();
/* 153 */     tessellator.draw();
/* 154 */     GlStateManager.enableTexture2D();
/* 155 */     GlStateManager.disableBlend();
/* 156 */     GLUtil.end2DRendering();
/*     */   }
/*     */   public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
/* 159 */     drawRect(left - (borderIncludedInBounds ? 0.0D : borderWidth), top - (borderIncludedInBounds ? 0.0D : borderWidth), right + (borderIncludedInBounds ? 0.0D : borderWidth), bottom + (borderIncludedInBounds ? 0.0D : borderWidth), borderColor);
/* 160 */     drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0D), top + (borderIncludedInBounds ? borderWidth : 0.0D), right - (borderIncludedInBounds ? borderWidth : 0.0D), bottom - (borderIncludedInBounds ? borderWidth : 0.0D), insideColor);
/*     */   }
/*     */   
/*     */   public static int reAlpha(int color, float alpha) {
/* 164 */     Color c = new Color(color);
/* 165 */     float r = 0.003921569F * c.getRed();
/* 166 */     float g = 0.003921569F * c.getGreen();
/* 167 */     float b = 0.003921569F * c.getBlue();
/* 168 */     return (new Color(r, g, b, alpha)).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getArrayRainbow(int counter, int alpha) {
/* 173 */     int colorDelay = 11;
/* 174 */     int colorLength = 110;
/* 175 */     double rainbowState = Math.ceil((System.currentTimeMillis() - counter * colorLength)) / colorDelay;
/* 176 */     rainbowState %= 360.0D;
/*     */     
/* 178 */     Color color = Color.getHSBColor((float)(rainbowState / 360.0D), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue());
/* 179 */     return (new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha)).getRGB();
/*     */   }
/*     */   
/*     */   public static String abcdefg() {
/* 183 */     String[] abc = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
/* 184 */     String[] ABC = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
/* 185 */     String[] aBc = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
/*     */     try {
/* 187 */       int var0 = (int)MathUtil.randomDouble(0.0D, (aBc.length - 1));
/* 188 */       return aBc[var0] + var0 + abc[abc.length - 1] + ABC[ABC.length - 1] + aBc[abc.length] + abc[ABC.length - 1];
/* 189 */     } catch (Exception e) {
/* 190 */       e.printStackTrace();
/* 191 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\simplecore\SimpleRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */