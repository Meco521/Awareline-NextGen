/*     */ package awareline.main.utility.shader.ketaUtils.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ColourUtil
/*     */ {
/*  12 */   private static final int[] HEALTH_COLOURS = new int[] { -16711847, -256, -32768, -65536, -8388608 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   private static final int[] RAINBOW_COLOURS = new int[] { -234868, -234795, -2462980, -7247108, -9794308, -9775620, -2462980, -234868 };
/*     */ 
/*     */ 
/*     */   
/*  24 */   private static final int[] CZECHIA_COLOURS = new int[] { -15645314, -15645314, -2681830, -2681830, -1, -15645314 };
/*     */ 
/*     */ 
/*     */   
/*  28 */   private static final int[] GERMAN_COLOURS = new int[] { -16777216, -131072, -12544, -16777216 };
/*     */ 
/*     */ 
/*     */   
/*     */   public static int blendHealthColours(double progress) {
/*  33 */     return blendColours(HEALTH_COLOURS, progress);
/*     */   }
/*     */   
/*     */   public static int blendRainbowColours(double progress) {
/*  37 */     return blendColours(RAINBOW_COLOURS, progress);
/*     */   }
/*     */   
/*     */   public static int blendRainbowColours(long offset) {
/*  41 */     return blendRainbowColours(getFadingFromSysTime(offset));
/*     */   }
/*     */   
/*     */   public static int blendCzechiaColours(double progress) {
/*  45 */     return blendColours(CZECHIA_COLOURS, progress);
/*     */   }
/*     */   
/*     */   public static int blendCzechiaColours(long offset) {
/*  49 */     return blendCzechiaColours(getFadingFromSysTime(offset));
/*     */   }
/*     */   
/*     */   public static int blendGermanColours(double progress) {
/*  53 */     return blendColours(GERMAN_COLOURS, progress);
/*     */   }
/*     */   
/*     */   public static int blendGermanColours(long offset) {
/*  57 */     return blendGermanColours(getFadingFromSysTime(offset));
/*     */   }
/*     */   
/*     */   public static int blendSpecialRainbow(long offset) {
/*  61 */     float fading = (float)getFadingFromSysTime(offset);
/*  62 */     return Color.HSBtoRGB(1.0F - fading, 0.8F, 1.0F);
/*     */   }
/*     */   
/*     */   public static double getFadingFromSysTime(long offset) {
/*  66 */     return ((System.currentTimeMillis() + offset) % 2000L) / 2000.0D;
/*     */   }
/*     */   
/*     */   public static float getBreathingProgress() {
/*  70 */     float progress = (float)(System.currentTimeMillis() % 2000L) / 1000.0F;
/*  71 */     return (progress > 1.0F) ? (1.0F - progress % 1.0F) : progress;
/*     */   }
/*     */   
/*     */   public static int blendRainbowColours() {
/*  75 */     return blendRainbowColours(0L);
/*     */   }
/*     */   public static int getClientColour() {
/*  78 */     return clientColour;
/*  79 */   } public static int clientColour = -3278336;
/*     */   
/*     */   public static void setClientColour(int colour) {
/*  82 */     clientColour = colour;
/*     */   }
/*     */   public static int getSecondaryColour() {
/*  85 */     return secondaryColour;
/*  86 */   } public static int secondaryColour = -16718593;
/*     */   
/*     */   public static void setSecondaryColour(int secondColour) {
/*  89 */     secondaryColour = secondColour;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int darker(int colour, double factor) {
/*  94 */     int r = (int)((colour >> 16 & 0xFF) * factor);
/*  95 */     int g = (int)((colour >> 8 & 0xFF) * factor);
/*  96 */     int b = (int)((colour & 0xFF) * factor);
/*  97 */     int a = colour >> 24 & 0xFF;
/*     */     
/*  99 */     return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float calculateAverageChannel(int rgb) {
/* 106 */     int red = rgb >> 16 & 0xFF;
/* 107 */     int green = rgb >> 8 & 0xFF;
/* 108 */     int blue = rgb & 0xFF;
/* 109 */     return Math.max(red, Math.max(green, blue)) / 255.0F;
/*     */   }
/*     */   
/*     */   public static int removeAlphaComponent(int colour) {
/* 113 */     int red = colour >> 16 & 0xFF;
/* 114 */     int green = colour >> 8 & 0xFF;
/* 115 */     int blue = colour & 0xFF;
/*     */     
/* 117 */     return (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int overwriteAlphaComponent(int colour, int alphaComponent) {
/* 123 */     int red = colour >> 16 & 0xFF;
/* 124 */     int green = colour >> 8 & 0xFF;
/* 125 */     int blue = colour & 0xFF;
/*     */     
/* 127 */     return (alphaComponent & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color getGradientOffset(Color color1, Color color2, double offset) {
/* 134 */     if (offset > 1.0D) {
/* 135 */       double left = offset % 1.0D;
/* 136 */       int off = (int)offset;
/* 137 */       offset = (off % 2 == 0) ? left : (1.0D - left);
/*     */     } 
/* 139 */     double inverse_percent = 1.0D - offset;
/* 140 */     int redPart = (int)(color1.getRed() * inverse_percent + color2.getRed() * offset);
/* 141 */     int greenPart = (int)(color1.getGreen() * inverse_percent + color2.getGreen() * offset);
/* 142 */     int bluePart = (int)(color1.getBlue() * inverse_percent + color2.getBlue() * offset);
/* 143 */     return new Color(redPart, greenPart, bluePart);
/*     */   }
/*     */   
/*     */   public static int blendOpacityRainbowColours(long offset, int alphaComponent) {
/* 147 */     return overwriteAlphaComponent(blendRainbowColours(getFadingFromSysTime(offset)), alphaComponent);
/*     */   }
/*     */   
/*     */   public static int darker(int color) {
/* 151 */     return darker(color, 0.6D);
/*     */   }
/*     */   
/*     */   public static int blendColours(int[] colours, double progress) {
/* 155 */     int size = colours.length;
/* 156 */     if (progress == 1.0D) return colours[0]; 
/* 157 */     if (progress == 0.0D) return colours[size - 1]; 
/* 158 */     double mulProgress = Math.max(0.0D, (1.0D - progress) * (size - 1));
/* 159 */     int index = (int)mulProgress;
/* 160 */     return fadeBetween(colours[index], colours[index + 1], mulProgress - index);
/*     */   }
/*     */   
/*     */   public static int fadeBetween(int startColour, int endColour, double progress) {
/* 164 */     if (progress > 1.0D) progress = 1.0D - progress % 1.0D; 
/* 165 */     return fadeTo(startColour, endColour, progress);
/*     */   }
/*     */   
/*     */   public static int fadeBetween(int startColour, int endColour, long offset) {
/* 169 */     return fadeBetween(startColour, endColour, ((System.currentTimeMillis() + offset) % 2000L) / 1000.0D);
/*     */   }
/*     */   
/*     */   public static int fadeBetween(int startColour, int endColour) {
/* 173 */     return fadeBetween(startColour, endColour, 0L);
/*     */   }
/*     */   
/*     */   public static int fadeTo(int startColour, int endColour, double progress) {
/* 177 */     double invert = 1.0D - progress;
/* 178 */     int r = (int)((startColour >> 16 & 0xFF) * invert + (endColour >> 16 & 0xFF) * progress);
/*     */     
/* 180 */     int g = (int)((startColour >> 8 & 0xFF) * invert + (endColour >> 8 & 0xFF) * progress);
/*     */     
/* 182 */     int b = (int)((startColour & 0xFF) * invert + (endColour & 0xFF) * progress);
/*     */     
/* 184 */     int a = (int)((startColour >> 24 & 0xFF) * invert + (endColour >> 24 & 0xFF) * progress);
/*     */     
/* 186 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\ketaUtils\render\ColourUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */