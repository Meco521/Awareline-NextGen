/*     */ package awareline.main.utility.render;
/*     */ 
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import com.ibm.icu.text.NumberFormat;
/*     */ import java.awt.Color;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorUtils
/*     */ {
/*  21 */   private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)锟斤拷[0-9A-FK-OR]");
/*  22 */   private static final double startTime = System.currentTimeMillis();
/*     */   
/*     */   public static int applyOpacity(int color, float opacity) {
/*  25 */     Color old = new Color(color);
/*  26 */     return applyOpacity(old, opacity).getRGB();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float lerp(float a, float b, float f) {
/*  32 */     return a + f * (b - a);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color astolfo(float yDist, float yTotal, float saturation, float speedt) {
/*  37 */     float speed = 1800.0F; float hue;
/*  38 */     for (hue = (float)(System.currentTimeMillis() % (int)speed) + (yTotal - yDist) * speedt; hue > speed; hue -= speed);
/*     */     
/*  40 */     if ((hue /= speed) > 0.5D) {
/*  41 */       hue = 0.5F - hue - 0.5F;
/*     */     }
/*  43 */     return Color.getHSBColor(hue += 0.5F, saturation, 1.0F);
/*     */   }
/*     */   
/*     */   public static String stripColor(String text) {
/*  47 */     return COLOR_PATTERN.matcher(text).replaceAll("");
/*     */   }
/*     */   
/*     */   public static Color blendColors(float[] fractions, Color[] colors, float progress) {
/*  51 */     if (fractions == null) {
/*  52 */       throw new IllegalArgumentException("Fractions can't be null");
/*     */     }
/*  54 */     if (colors == null) {
/*  55 */       throw new IllegalArgumentException("Colours can't be null");
/*     */     }
/*  57 */     if (fractions.length == colors.length) {
/*  58 */       int[] getFractionBlack = getFraction(fractions, progress);
/*  59 */       float[] range = { fractions[getFractionBlack[0]], fractions[getFractionBlack[1]] };
/*  60 */       Color[] colorRange = { colors[getFractionBlack[0]], colors[getFractionBlack[1]] };
/*  61 */       float max = range[1] - range[0];
/*  62 */       float value = progress - range[0];
/*  63 */       float weight = value / max;
/*  64 */       return blend(colorRange[0], colorRange[1], (1.0F - weight));
/*     */     } 
/*  66 */     throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getFraction(float[] fractions, float progress) {
/*  71 */     int[] range = new int[2]; int startPoint;
/*  72 */     for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; startPoint++);
/*     */     
/*  74 */     if (startPoint >= fractions.length) {
/*  75 */       startPoint = fractions.length - 1;
/*     */     }
/*  77 */     range[0] = startPoint - 1;
/*  78 */     range[1] = startPoint;
/*  79 */     return range;
/*     */   }
/*     */   
/*     */   public static Color blend(Color color1, Color color2, double ratio) {
/*  83 */     float r = (float)ratio;
/*  84 */     float ir = 1.0F - r;
/*  85 */     float[] rgb1 = new float[3];
/*  86 */     float[] rgb2 = new float[3];
/*  87 */     color1.getColorComponents(rgb1);
/*  88 */     color2.getColorComponents(rgb2);
/*  89 */     float red = rgb1[0] * r + rgb2[0] * ir;
/*  90 */     float green = rgb1[1] * r + rgb2[1] * ir;
/*  91 */     float blue = rgb1[2] * r + rgb2[2] * ir;
/*  92 */     if (red < 0.0F) {
/*  93 */       red = 0.0F;
/*  94 */     } else if (red > 255.0F) {
/*  95 */       red = 255.0F;
/*     */     } 
/*  97 */     if (green < 0.0F) {
/*  98 */       green = 0.0F;
/*  99 */     } else if (green > 255.0F) {
/* 100 */       green = 255.0F;
/*     */     } 
/* 102 */     if (blue < 0.0F) {
/* 103 */       blue = 0.0F;
/* 104 */     } else if (blue > 255.0F) {
/* 105 */       blue = 255.0F;
/*     */     } 
/* 107 */     Color color3 = null;
/*     */     try {
/* 109 */       color3 = new Color(red, green, blue);
/*     */     }
/* 111 */     catch (IllegalArgumentException exp) {
/* 112 */       NumberFormat nf = NumberFormat.getNumberInstance();
/* 113 */       exp.printStackTrace();
/*     */     } 
/* 115 */     return color3;
/*     */   }
/*     */   
/*     */   public static Color interpolate(Color from, Color to, double value) {
/* 119 */     double progress = (value > 1.0D) ? 1.0D : ((value < 0.0D) ? 0.0D : value);
/* 120 */     int redDiff = to.getRed() - from.getRed();
/* 121 */     int greenDiff = to.getGreen() - from.getGreen();
/* 122 */     int blueDiff = to.getBlue() - from.getBlue();
/* 123 */     int alphaDiff = to.getAlpha() - from.getAlpha();
/* 124 */     int newRed = (int)(from.getRed() + redDiff * progress);
/* 125 */     int newGreen = (int)(from.getGreen() + greenDiff * progress);
/* 126 */     int newBlue = (int)(from.getBlue() + blueDiff * progress);
/* 127 */     int newAlpha = (int)(from.getAlpha() + alphaDiff * progress);
/* 128 */     return new Color(newRed, newGreen, newBlue, newAlpha);
/*     */   }
/*     */   
/*     */   public static Color[] getClientAccentTheme() {
/* 132 */     return new Color[] { new Color(91, 206, 250), new Color(245, 169, 184) };
/*     */   }
/*     */   
/*     */   public static int fadeBetween(int startColor, int endColor, float progress) {
/* 136 */     if (progress > 1.0F) {
/* 137 */       progress = 1.0F - progress % 1.0F;
/*     */     }
/* 139 */     return fadeTo(startColor, endColor, progress);
/*     */   }
/*     */   
/*     */   public static Color fadeBetween(int speed, int index, Color start, Color end) {
/* 143 */     int tick = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 144 */     tick = ((tick >= 180) ? (360 - tick) : tick) * 2;
/* 145 */     return interpolate(start, end, (tick / 360.0F));
/*     */   }
/*     */   
/*     */   public static int fadeTo(int startColor, int endColor, float progress) {
/* 149 */     float invert = 1.0F - progress;
/* 150 */     int r = (int)((startColor >> 16 & 0xFF) * invert + (endColor >> 16 & 0xFF) * progress);
/* 151 */     int g = (int)((startColor >> 8 & 0xFF) * invert + (endColor >> 8 & 0xFF) * progress);
/* 152 */     int b = (int)((startColor & 0xFF) * invert + (endColor & 0xFF) * progress);
/* 153 */     int a = (int)((startColor >> 24 & 0xFF) * invert + (endColor >> 24 & 0xFF) * progress);
/* 154 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*     */   }
/*     */   
/*     */   public static Color applyOpacity(Color color, float opacity) {
/* 158 */     opacity = Math.min(1.0F, Math.max(0.0F, opacity));
/* 159 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
/*     */   }
/*     */   
/*     */   public static void glColor(int color) {
/* 163 */     int r = color >> 16 & 0xFF;
/* 164 */     int g = color >> 8 & 0xFF;
/* 165 */     int b = color & 0xFF;
/* 166 */     int a = color >> 24 & 0xFF;
/* 167 */     GL11.glColor4f(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
/*     */   }
/*     */   
/*     */   public static Color hslRainbow(Integer index, Float lowest, Float bigest, Integer indexOffset, Integer timeSplit) {
/* 171 */     return Color.getHSBColor((float)(MathHelper.abs((float)((System.currentTimeMillis() - startTime + (index.intValue() * indexOffset.intValue())) / timeSplit.intValue() % 2.0D - 1.0D)) * (bigest.floatValue() - lowest.floatValue()) + lowest.floatValue()), 0.7F, 1.0F);
/*     */   }
/*     */   
/*     */   public static Color tripleColor(int rgbValue) {
/* 175 */     return tripleColor(rgbValue, 1.0F);
/*     */   }
/*     */   
/*     */   public static Color tripleColor(int rgbValue, float alpha) {
/* 179 */     alpha = Math.min(1.0F, Math.max(0.0F, alpha));
/* 180 */     return new Color(rgbValue, rgbValue, rgbValue, (int)(255.0F * alpha));
/*     */   }
/*     */   
/*     */   public static int rainbow(int delay) {
/* 184 */     double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 10.0D);
/* 185 */     return Color.getHSBColor((float)(rainbow % 360.0D / 360.0D), 0.5F, 1.0F).getRGB();
/*     */   }
/*     */   
/*     */   public static Color rainbow(long time, float count, float fade) {
/* 189 */     float hue = ((float)time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
/* 190 */     long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
/* 191 */     Color c = new Color((int)color);
/* 192 */     return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   public static Color getRainbow() {
/* 196 */     return new Color(Color.HSBtoRGB((float)((Minecraft.getMinecraft()).thePlayer.ticksExisted / 50.0D + Math.sin(0.032D)) % 1.0F, 0.5F, 1.0F));
/*     */   }
/*     */   
/*     */   public static void color(int color) {
/* 200 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 201 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 202 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 203 */     float blue = (color & 0xFF) / 255.0F;
/* 204 */     GlStateManager.color(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static int getColor(Color color) {
/* 208 */     return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*     */   }
/*     */   
/*     */   public static int getColor(int red, int green, int blue) {
/* 212 */     return getColor(red, green, blue, 255);
/*     */   }
/*     */   
/*     */   public static int getColor(int red, int green, int blue, int alpha) {
/* 216 */     int color = 0;
/* 217 */     color |= alpha << 24;
/* 218 */     color |= red << 16;
/* 219 */     color |= green << 8;
/* 220 */     return color |= blue;
/*     */   }
/*     */   
/*     */   public static int getAstolfo(int delay, float offset, float hueSetting) {
/* 224 */     float f = 0.0F;
/*     */     
/* 226 */     float speed = 4000.0F; float hue;
/* 227 */     for (hue = (float)(System.currentTimeMillis() % delay) + offset; hue > speed; hue -= speed);
/*     */     
/* 229 */     hue /= speed;
/* 230 */     if (f > 0.5D) {
/* 231 */       hue = 0.5F - hue - 0.5F;
/*     */     }
/* 233 */     return Color.HSBtoRGB(hue + hueSetting, 0.5F, 1.0F);
/*     */   }
/*     */   
/*     */   public static final Color getClientColor() {
/* 237 */     return new Color(250, 248, 190);
/*     */   }
/*     */   
/*     */   public static final Color getAlternateClientColor() {
/* 241 */     return new Color(46, 234, 255);
/*     */   }
/*     */   
/*     */   public static final Color getTenacityColor() {
/* 245 */     return new Color(236, 133, 209);
/*     */   }
/*     */   
/*     */   public static final Color getAlternateTenacityColor() {
/* 249 */     return new Color(28, 167, 222);
/*     */   }
/*     */   
/*     */   public static void resetColor() {
/* 253 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static Color rainbow(int speed, int index, float saturation, float brightness, float opacity) {
/* 257 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 258 */     float hue = angle / 360.0F;
/* 259 */     Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
/* 260 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int)(opacity * 255.0F))));
/*     */   }
/*     */   
/*     */   public static Color rainbow(int speed, int index, float saturation, float brightness, float opacity, int alp) {
/* 264 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 265 */     float hue = angle / 360.0F;
/* 266 */     Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
/* 267 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), alp);
/*     */   }
/*     */   
/*     */   public static int darker(int hexColor, int factor) {
/* 271 */     float alpha = (hexColor >> 24 & 0xFF);
/* 272 */     float red = Math.max((hexColor >> 16 & 0xFF) - (hexColor >> 16 & 0xFF) / 100.0F / factor, 0.0F);
/* 273 */     float green = Math.max((hexColor >> 8 & 0xFF) - (hexColor >> 8 & 0xFF) / 100.0F / factor, 0.0F);
/* 274 */     float blue = Math.max((hexColor & 0xFF) - (hexColor & 0xFF) / 100.0F / factor, 0.0F);
/* 275 */     return (int)((((int)alpha << 24) + ((int)red << 16) + ((int)green << 8)) + blue);
/*     */   }
/*     */   
/*     */   public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
/* 279 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 280 */     angle = ((angle >= 180) ? (360 - angle) : angle) * 2;
/* 281 */     return trueColor ? interpolateColorHue(start, end, angle / 360.0F) : interpolateColorC(start, end, angle / 360.0F);
/*     */   }
/*     */   
/*     */   public static Color interpolateColorC(Color color1, Color color2, float amount) {
/* 285 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 286 */     return new Color(MathUtil.interpolateInt(color1.getRed(), color2.getRed(), amount), MathUtil.interpolateInt(color1.getGreen(), color2.getGreen(), amount), MathUtil.interpolateInt(color1.getBlue(), color2.getBlue(), amount), MathUtil.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
/*     */   }
/*     */   
/*     */   public static int applyOpacityInt(int color, float opacity) {
/* 290 */     Color old = new Color(color);
/* 291 */     return applyOpacityInt(old, opacity).getRGB();
/*     */   }
/*     */   
/*     */   public static Color applyOpacityColor(int color, float opacity) {
/* 295 */     Color old = new Color(color);
/* 296 */     return applyOpacityInt(old, opacity);
/*     */   }
/*     */   
/*     */   public static int interpolateColor(int color1, int color2, float amount) {
/* 300 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 301 */     Color cColor1 = new Color(color1);
/* 302 */     Color cColor2 = new Color(color2);
/* 303 */     return interpolateColorC(cColor1, cColor2, amount).getRGB();
/*     */   }
/*     */   
/*     */   public static Color interpolateColorHue(Color color1, Color color2, float amount) {
/* 307 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 308 */     float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
/* 309 */     float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
/* 310 */     Color resultColor = Color.getHSBColor(MathUtil.interpolateFloat(color1HSB[0], color2HSB[0], amount), MathUtil.interpolateFloat(color1HSB[1], color2HSB[1], amount), MathUtil.interpolateFloat(color1HSB[2], color2HSB[2], amount));
/* 311 */     return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), MathUtil.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
/*     */   }
/*     */   
/*     */   public static Color applyOpacityInt(Color color, float opacity) {
/* 315 */     opacity = Math.min(1.0F, Math.max(0.0F, opacity));
/* 316 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness) {
/* 320 */     return getColor(brightness, brightness, brightness, 255);
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness, int alpha) {
/* 324 */     return getColor(brightness, brightness, brightness, alpha);
/*     */   }
/*     */   
/*     */   public static Color getClientColor(float yStep, float astolfoastep, float yStepFull, int speed) {
/* 328 */     Color color = Color.white;
/* 329 */     color = astolfo(astolfoastep, yStepFull, 0.5F, speed);
/* 330 */     return color;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */