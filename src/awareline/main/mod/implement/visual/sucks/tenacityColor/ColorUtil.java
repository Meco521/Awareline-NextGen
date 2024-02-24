/*     */ package awareline.main.mod.implement.visual.sucks.tenacityColor;
/*     */ 
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorUtil
/*     */ {
/*     */   public static Color[] getAnalogousColor(Color color) {
/*  12 */     Color[] colors = new Color[2];
/*  13 */     float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
/*     */     
/*  15 */     float degree = 0.083333336F;
/*     */     
/*  17 */     float newHueAdded = hsb[0] + degree;
/*  18 */     colors[0] = new Color(Color.HSBtoRGB(newHueAdded, hsb[1], hsb[2]));
/*     */     
/*  20 */     float newHueSubtracted = hsb[0] - degree;
/*     */     
/*  22 */     colors[1] = new Color(Color.HSBtoRGB(newHueSubtracted, hsb[1], hsb[2]));
/*     */     
/*  24 */     return colors;
/*     */   }
/*     */   
/*     */   public static int transparency(int color, double alpha) {
/*  28 */     Color c = new Color(color);
/*  29 */     float r = 0.003921569F * c.getRed();
/*  30 */     float g = 0.003921569F * c.getGreen();
/*  31 */     float b = 0.003921569F * c.getBlue();
/*  32 */     return (new Color(r, g, b, (float)alpha)).getRGB();
/*     */   }
/*     */   
/*     */   public static int transparency(Color color, double alpha) {
/*  36 */     return (new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)alpha))
/*  37 */       .getRGB();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color hslToRGB(float[] hsl) {
/*     */     float red, green, blue;
/*  44 */     if (hsl[1] == 0.0F) {
/*  45 */       red = green = blue = 1.0F;
/*     */     } else {
/*  47 */       float q = (hsl[2] < 0.5D) ? (hsl[2] * (1.0F + hsl[1])) : (hsl[2] + hsl[1] - hsl[2] * hsl[1]);
/*  48 */       float p = 2.0F * hsl[2] - q;
/*     */       
/*  50 */       red = hueToRGB(p, q, hsl[0] + 0.33333334F);
/*  51 */       green = hueToRGB(p, q, hsl[0]);
/*  52 */       blue = hueToRGB(p, q, hsl[0] - 0.33333334F);
/*     */     } 
/*     */     
/*  55 */     red *= 255.0F;
/*  56 */     green *= 255.0F;
/*  57 */     blue *= 255.0F;
/*     */     
/*  59 */     return new Color((int)red, (int)green, (int)blue);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float hueToRGB(float p, float q, float t) {
/*  64 */     float newT = t;
/*  65 */     if (newT < 0.0F) newT++; 
/*  66 */     if (newT > 1.0F) newT--; 
/*  67 */     if (newT < 0.16666667F) return p + (q - p) * 6.0F * newT; 
/*  68 */     if (newT < 0.5F) return q; 
/*  69 */     if (newT < 0.6666667F) return p + (q - p) * (0.6666667F - newT) * 6.0F; 
/*  70 */     return p;
/*     */   }
/*     */   
/*     */   public static float[] rgbToHSL(Color rgb) {
/*  74 */     float red = rgb.getRed() / 255.0F;
/*  75 */     float green = rgb.getGreen() / 255.0F;
/*  76 */     float blue = rgb.getBlue() / 255.0F;
/*     */     
/*  78 */     float max = Math.max(Math.max(red, green), blue);
/*  79 */     float min = Math.min(Math.min(red, green), blue);
/*  80 */     float c = (max + min) / 2.0F;
/*  81 */     float[] hsl = { c, c, c };
/*     */     
/*  83 */     if (max == min) {
/*  84 */       hsl[1] = 0.0F; hsl[0] = 0.0F;
/*     */     } else {
/*  86 */       float d = max - min;
/*  87 */       hsl[1] = (hsl[2] > 0.5D) ? (d / (2.0F - max - min)) : (d / (max + min));
/*     */       
/*  89 */       if (max == red) {
/*  90 */         hsl[0] = (green - blue) / d + ((green < blue) ? 6 : false);
/*  91 */       } else if (max == blue) {
/*  92 */         hsl[0] = (blue - red) / d + 2.0F;
/*  93 */       } else if (max == green) {
/*  94 */         hsl[0] = (red - green) / d + 4.0F;
/*     */       } 
/*  96 */       hsl[0] = hsl[0] / 6.0F;
/*     */     } 
/*  98 */     return hsl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color imitateTransparency(Color backgroundColor, Color accentColor, float percentage) {
/* 103 */     return new Color(interpolateColor(backgroundColor, accentColor, 255.0F * percentage / 255.0F));
/*     */   }
/*     */   
/*     */   public static int applyOpacity(int color, float opacity) {
/* 107 */     Color old = new Color(color);
/* 108 */     return applyOpacity(old, opacity).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color applyOpacity(Color color, float opacity) {
/* 113 */     opacity = Math.min(1.0F, Math.max(0.0F, opacity));
/* 114 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
/*     */   }
/*     */   
/*     */   public static Color darker(Color color, float FACTOR) {
/* 118 */     return new Color(Math.max((int)(color.getRed() * FACTOR), 0), 
/* 119 */         Math.max((int)(color.getGreen() * FACTOR), 0), 
/* 120 */         Math.max((int)(color.getBlue() * FACTOR), 0), color
/* 121 */         .getAlpha());
/*     */   }
/*     */   
/*     */   public static Color brighter(Color color, float FACTOR) {
/* 125 */     int r = color.getRed();
/* 126 */     int g = color.getGreen();
/* 127 */     int b = color.getBlue();
/* 128 */     int alpha = color.getAlpha();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     int i = (int)(1.0D / (1.0D - FACTOR));
/* 136 */     if (r == 0 && g == 0 && b == 0) {
/* 137 */       return new Color(i, i, i, alpha);
/*     */     }
/* 139 */     if (r > 0 && r < i) r = i; 
/* 140 */     if (g > 0 && g < i) g = i; 
/* 141 */     if (b > 0 && b < i) b = i;
/*     */     
/* 143 */     return new Color(Math.min((int)(r / FACTOR), 255), 
/* 144 */         Math.min((int)(g / FACTOR), 255), 
/* 145 */         Math.min((int)(b / FACTOR), 255), alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color averageColor(BufferedImage bi, int width, int height, int pixelStep) {
/* 154 */     int[] color = new int[3]; int x;
/* 155 */     for (x = 0; x < width; x += pixelStep) {
/* 156 */       int y; for (y = 0; y < height; y += pixelStep) {
/* 157 */         Color pixel = new Color(bi.getRGB(x, y));
/* 158 */         color[0] = color[0] + pixel.getRed();
/* 159 */         color[1] = color[1] + pixel.getGreen();
/* 160 */         color[2] = color[2] + pixel.getBlue();
/*     */       } 
/*     */     } 
/* 163 */     int num = width * height / pixelStep * pixelStep;
/* 164 */     return new Color(color[0] / num, color[1] / num, color[2] / num);
/*     */   }
/*     */   
/*     */   public static Color rainbow(int speed, int index, float saturation, float brightness, float opacity) {
/* 168 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 169 */     float hue = angle / 360.0F;
/* 170 */     Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
/* 171 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int)(opacity * 255.0F))));
/*     */   }
/*     */   
/*     */   public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
/* 175 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 176 */     angle = ((angle >= 180) ? (360 - angle) : angle) << 1;
/* 177 */     return trueColor ? interpolateColorHue(start, end, angle / 360.0F) : interpolateColorC(start, end, angle / 360.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int interpolateColor(Color color1, Color color2, float amount) {
/* 182 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 183 */     return interpolateColorC(color1, color2, amount).getRGB();
/*     */   }
/*     */   
/*     */   public static int interpolateColor(int color1, int color2, float amount) {
/* 187 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 188 */     Color cColor1 = new Color(color1);
/* 189 */     Color cColor2 = new Color(color2);
/* 190 */     return interpolateColorC(cColor1, cColor2, amount).getRGB();
/*     */   }
/*     */   
/*     */   public static Color interpolateColorC(Color color1, Color color2, float amount) {
/* 194 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 195 */     return new Color(MathUtil.interpolateInt(color1.getRed(), color2.getRed(), amount), 
/* 196 */         MathUtil.interpolateInt(color1.getGreen(), color2.getGreen(), amount), 
/* 197 */         MathUtil.interpolateInt(color1.getBlue(), color2.getBlue(), amount), 
/* 198 */         MathUtil.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
/*     */   }
/*     */   
/*     */   public static Color interpolateColorHue(Color color1, Color color2, float amount) {
/* 202 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/*     */     
/* 204 */     float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
/* 205 */     float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
/*     */     
/* 207 */     Color resultColor = Color.getHSBColor(MathUtil.interpolateFloat(color1HSB[0], color2HSB[0], amount), 
/* 208 */         MathUtil.interpolateFloat(color1HSB[1], color2HSB[1], amount), MathUtil.interpolateFloat(color1HSB[2], color2HSB[2], amount));
/*     */     
/* 210 */     return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), 
/* 211 */         MathUtil.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color fade(int speed, int index, Color color, float alpha) {
/* 217 */     float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
/* 218 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 219 */     angle = ((angle > 180) ? (360 - angle) : angle) + 180;
/*     */     
/* 221 */     Color colorHSB = new Color(Color.HSBtoRGB(hsb[0], hsb[1], angle / 360.0F));
/*     */     
/* 223 */     return new Color(colorHSB.getRed(), colorHSB.getGreen(), colorHSB.getBlue(), Math.max(0, Math.min(255, (int)(alpha * 255.0F))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getAnimationEquation(int index, int speed) {
/* 228 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 229 */     return (((angle > 180) ? (360 - angle) : angle) + 180) / 360.0F;
/*     */   }
/*     */   
/*     */   public static int[] createColorArray(int color) {
/* 233 */     return new int[] { bitChangeColor(color, 16), bitChangeColor(color, 8), bitChangeColor(color, 0), bitChangeColor(color, 24) };
/*     */   }
/*     */   
/*     */   public static int getOppositeColor(int color) {
/* 237 */     int R = bitChangeColor(color, 0);
/* 238 */     int G = bitChangeColor(color, 8);
/* 239 */     int B = bitChangeColor(color, 16);
/* 240 */     int A = bitChangeColor(color, 24);
/* 241 */     R = 255 - R;
/* 242 */     G = 255 - G;
/* 243 */     B = 255 - B;
/* 244 */     return R + (G << 8) + (B << 16) + (A << 24);
/*     */   }
/*     */   
/*     */   private static int bitChangeColor(int color, int bitChange) {
/* 248 */     return color >> bitChange & 0xFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\sucks\tenacityColor\ColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */