/*     */ package awareline.main.mod.implement.visual.sucks.WingRenderer;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ public class ColorUtils
/*     */ {
/*     */   public static Color rainbow(long offset, float fade) {
/*  10 */     float hue = (float)(System.nanoTime() + offset) / 1.0E10F % 1.0F;
/*  11 */     long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
/*  12 */     Color c = new Color((int)color);
/*  13 */     return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getFractionIndices(float[] fractions, float progress) {
/*  18 */     int[] range = new int[2]; int startPoint;
/*  19 */     for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; startPoint++);
/*     */     
/*  21 */     if (startPoint >= fractions.length) {
/*  22 */       startPoint = fractions.length - 1;
/*     */     }
/*  24 */     range[0] = startPoint - 1;
/*  25 */     range[1] = startPoint;
/*  26 */     return range;
/*     */   }
/*     */   
/*     */   public static Color blendColors(float[] fractions, Color[] colors, float progress) {
/*  30 */     if (fractions.length == colors.length) {
/*  31 */       int[] indices = getFractionIndices(fractions, progress);
/*  32 */       float[] range = { fractions[indices[0]], fractions[indices[1]] };
/*  33 */       Color[] colorRange = { colors[indices[0]], colors[indices[1]] };
/*  34 */       float max = range[1] - range[0];
/*  35 */       float value = progress - range[0];
/*  36 */       float weight = value / max;
/*  37 */       return blend(colorRange[0], colorRange[1], (1.0F - weight));
/*     */     } 
/*  39 */     throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
/*     */   }
/*     */   
/*     */   public static Color blend(Color color1, Color color2, double ratio) {
/*  43 */     float r = (float)ratio;
/*  44 */     float ir = 1.0F - r;
/*  45 */     float[] rgb1 = new float[3];
/*  46 */     float[] rgb2 = new float[3];
/*  47 */     color1.getColorComponents(rgb1);
/*  48 */     color2.getColorComponents(rgb2);
/*  49 */     return new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
/*     */   }
/*     */   
/*     */   public static Color getHealthColorint(float health, float maxHealth) {
/*  53 */     float[] fractions = { 0.0F, 0.5F, 1.0F };
/*  54 */     Color[] colors = { new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN };
/*  55 */     float progress = health / maxHealth;
/*  56 */     return blendColors(fractions, colors, progress).brighter();
/*     */   }
/*     */   
/*     */   public static int getHealthColor(float health, float maxHealth) {
/*  60 */     float percentage = health / maxHealth;
/*  61 */     if (percentage >= 0.75F) {
/*  62 */       return (new Color(100, 200, 100)).getRGB();
/*     */     }
/*  64 */     if (percentage < 0.75D && percentage >= 0.25D) {
/*  65 */       return (new Color(200, 200, 100)).getRGB();
/*     */     }
/*  67 */     return (new Color(200, 75, 75)).getRGB();
/*     */   }
/*     */   
/*     */   public static Color mixColors(Color color1, Color color2, double percent) {
/*  71 */     double inverse_percent = 1.0D - percent;
/*  72 */     int redPart = (int)(color1.getRed() * percent + color2.getRed() * inverse_percent);
/*  73 */     int greenPart = (int)(color1.getGreen() * percent + color2.getGreen() * inverse_percent);
/*  74 */     int bluePart = (int)(color1.getBlue() * percent + color2.getBlue() * inverse_percent);
/*  75 */     return new Color(redPart, greenPart, bluePart);
/*     */   }
/*     */   
/*     */   public static Color getDarker(Color before, int dark, int alpha) {
/*  79 */     int rDank = Math.max(before.getRed() - dark, 0);
/*  80 */     int gDank = Math.max(before.getGreen() - dark, 0);
/*  81 */     int bDank = Math.max(before.getBlue() - dark, 0);
/*  82 */     return new Color(rDank, gDank, bDank, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getAstolfoRainbow(int v1) {
/*  87 */     double d = 0.0D;
/*  88 */     double delay = Math.ceil((System.currentTimeMillis() + v1 * 70L) / 5.0D);
/*  89 */     float rainbow = ((float)(d / 420.0D) < 0.5D) ? -((float)(delay / 420.0D)) : (float)(delay % 420.0D / 420.0D);
/*  90 */     return Color.getHSBColor(rainbow, 0.5F, 1.0F);
/*     */   }
/*     */   
/*     */   public static Color getRainbow(float second, float sat, float bright) {
/*  94 */     float hue = (float)(System.currentTimeMillis() % (int)(second * 1000.0F)) / second * 1000.0F;
/*  95 */     return new Color(Color.HSBtoRGB(hue, sat, bright));
/*     */   }
/*     */   
/*     */   public static Color getBlendColor(double current, double max) {
/*  99 */     long base = Math.round(max / 5.0D);
/* 100 */     if (current >= (base * 5L)) {
/* 101 */       return new Color(15, 255, 15);
/*     */     }
/* 103 */     if (current >= (base << 2L)) {
/* 104 */       return new Color(166, 255, 0);
/*     */     }
/* 106 */     if (current >= (base * 3L)) {
/* 107 */       return new Color(255, 191, 0);
/*     */     }
/* 109 */     if (current >= (base << 1L)) {
/* 110 */       return new Color(255, 89, 0);
/*     */     }
/* 112 */     return new Color(255, 0, 0);
/*     */   }
/*     */   
/*     */   public static Color fastrainbow() {
/* 116 */     int rainbowTick = 0;
/* 117 */     return new Color(Color.HSBtoRGB((float)((Minecraft.getMinecraft()).thePlayer.ticksExisted / 50.0D + Math.sin(rainbowTick / 50.0D * 1.6D)) % 1.0F, 0.5F, 1.0F));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\sucks\WingRenderer\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */