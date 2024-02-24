/*     */ package awareline.main.ui.font.fontmanager.color;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public enum ColorUtils
/*     */ {
/*   8 */   BLACK(-16711423),
/*   9 */   BLUE(-12028161),
/*  10 */   DARKBLUE(-12621684),
/*  11 */   GREEN(-9830551),
/*  12 */   DARKGREEN(-9320847),
/*  13 */   WHITE(-65794),
/*  14 */   AQUA(-7820064),
/*  15 */   DARKAQUA(-12621684),
/*  16 */   GREY(-9868951),
/*  17 */   DARKGREY(-14342875),
/*  18 */   RED(-65536),
/*  19 */   DARKRED(-8388608),
/*  20 */   ORANGE(-29696),
/*  21 */   DARKORANGE(-2263808),
/*  22 */   YELLOW(-256),
/*  23 */   DARKYELLOW(-2702025),
/*  24 */   MAGENTA(-18751),
/*  25 */   DARKMAGENTA(-2252579);
/*     */   public final int c;
/*     */   private static final Pattern COLOR_PATTERN;
/*     */   
/*     */   ColorUtils(int co) {
/*  30 */     this.c = co;
/*     */   }
/*     */   
/*     */   public static int getColor(Color color) {
/*  34 */     return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness) {
/*  38 */     return getColor(brightness, brightness, brightness, 255);
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness, int alpha) {
/*  42 */     return getColor(brightness, brightness, brightness, alpha);
/*     */   }
/*     */   
/*     */   public static int fadeBetween(int startColour, int endColour, double progress) {
/*  46 */     if (progress > 1.0D) progress = 1.0D - progress % 1.0D; 
/*  47 */     return fadeTo(startColour, endColour, progress);
/*     */   }
/*     */   
/*     */   public static int fadeBetween(int startColour, int endColour) {
/*  51 */     return fadeBetween(startColour, endColour, 0.0D);
/*     */   }
/*     */   
/*     */   public static int rainbow(int delay) {
/*  55 */     double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0D);
/*  56 */     rainbowState %= 360.0D;
/*  57 */     return Color.getHSBColor((float)(rainbowState / 360.0D), 0.8F, 0.7F).brighter().getRGB();
/*     */   }
/*     */   
/*     */   public static int getColor(int red, int green, int blue) {
/*  61 */     return getColor(red, green, blue, 255);
/*     */   }
/*     */   
/*     */   public static int fadeTo(int startColour, int endColour, double progress) {
/*  65 */     double invert = 1.0D - progress;
/*  66 */     int r = (int)((startColour >> 16 & 0xFF) * invert + (endColour >> 16 & 0xFF) * progress);
/*     */     
/*  68 */     int g = (int)((startColour >> 8 & 0xFF) * invert + (endColour >> 8 & 0xFF) * progress);
/*     */     
/*  70 */     int b = (int)((startColour & 0xFF) * invert + (endColour & 0xFF) * progress);
/*     */     
/*  72 */     int a = (int)((startColour >> 24 & 0xFF) * invert + (endColour >> 24 & 0xFF) * progress);
/*     */     
/*  74 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getColor(int red, int green, int blue, int alpha) {
/*  81 */     byte color = 0;
/*  82 */     int color1 = color | alpha << 24;
/*  83 */     color1 |= red << 16;
/*  84 */     color1 |= green << 8;
/*  85 */     color1 |= blue;
/*  86 */     return color1;
/*     */   } static {
/*  88 */     COLOR_PATTERN = Pattern.compile("(?i)搂[0-9A-FK-OR]");
/*     */   }
/*     */   public static String stripColor(String input) {
/*  91 */     return COLOR_PATTERN.matcher(input).replaceAll("");
/*     */   }
/*     */   
/*     */   public static Color tripleColor(int rgbValue) {
/*  95 */     return tripleColor(rgbValue, 1.0F);
/*     */   }
/*     */   
/*     */   public static Color tripleColor(int rgbValue, float alpha) {
/*  99 */     alpha = Math.min(1.0F, Math.max(0.0F, alpha));
/* 100 */     return new Color(rgbValue, rgbValue, rgbValue, (int)(255.0F * alpha));
/*     */   }
/*     */   
/*     */   public static int applyOpacity(int color, float opacity) {
/* 104 */     Color old = new Color(color);
/* 105 */     return applyOpacity(old, opacity).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color applyOpacity(Color color, float opacity) {
/* 110 */     opacity = Math.min(1.0F, Math.max(0.0F, opacity));
/* 111 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\color\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */