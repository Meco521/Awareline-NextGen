/*    */ package awareline.main.ui.font.fontmanager.font;
/*    */ 
/*    */ public final class ColorUtils {
/*  4 */   public static final int RED = getRGB(255, 0, 0);
/*  5 */   public static final int GREED = getRGB(0, 255, 0);
/*  6 */   public static final int BLUE = getRGB(0, 0, 255);
/*  7 */   public static final int WHITE = getRGB(255, 255, 255);
/*  8 */   public static final int BLACK = getRGB(0, 0, 0);
/*    */   
/* 10 */   public static final int NO_COLOR = getRGB(0, 0, 0, 0);
/*    */   
/*    */   public static int getRGB(int r, int g, int b) {
/* 13 */     return getRGB(r, g, b, 255);
/*    */   }
/*    */   
/*    */   public static int getRGB(float r, float g, float b) {
/* 17 */     return getRGB((int)((r * 255.0F) + 0.5D), (int)((g * 255.0F) + 0.5D), (int)((b * 255.0F) + 0.5D), 255);
/*    */   }
/*    */   
/*    */   public static int getRGB(int r, int g, int b, int a) {
/* 21 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getRGB(float r, float g, float b, float a) {
/* 28 */     return getRGB((int)((r * 255.0F) + 0.5D), (int)((g * 255.0F) + 0.5D), (int)((b * 255.0F) + 0.5D), (int)((a * 255.0F) + 0.5D));
/*    */   }
/*    */   
/*    */   public static int[] splitRGB(int rgb) {
/* 32 */     int[] ints = new int[3];
/*    */     
/* 34 */     ints[0] = rgb >> 16 & 0xFF;
/* 35 */     ints[1] = rgb >> 8 & 0xFF;
/* 36 */     ints[2] = rgb & 0xFF;
/*    */     
/* 38 */     return ints;
/*    */   }
/*    */   
/*    */   public static int[] splitRGBA(int rgb) {
/* 42 */     int[] ints = new int[4];
/*    */     
/* 44 */     ints[0] = rgb >> 16 & 0xFF;
/* 45 */     ints[1] = rgb >> 8 & 0xFF;
/* 46 */     ints[2] = rgb & 0xFF;
/* 47 */     ints[3] = rgb >> 24 & 0xFF;
/*    */     
/* 49 */     return ints;
/*    */   }
/*    */   
/*    */   public static int getRGB(int rgb) {
/* 53 */     return 0xFF000000 | rgb;
/*    */   }
/*    */   
/*    */   public static int reAlpha(int rgb, int alpha) {
/* 57 */     return getRGB(getRed(rgb), getGreen(rgb), getBlue(rgb), alpha);
/*    */   }
/*    */   
/*    */   public static int getRed(int rgb) {
/* 61 */     return rgb >> 16 & 0xFF;
/*    */   }
/*    */   
/*    */   public static int getGreen(int rgb) {
/* 65 */     return rgb >> 8 & 0xFF;
/*    */   }
/*    */   
/*    */   public static int getBlue(int rgb) {
/* 69 */     return rgb & 0xFF;
/*    */   }
/*    */   
/*    */   public static int getAlpha(int rgb) {
/* 73 */     return rgb >> 24 & 0xFF;
/*    */   }
/*    */   
/*    */   public static int blend(float r1, float g1, float b1, float r2, float g2, float b2, float ratio) {
/* 77 */     float f = 1.0F - ratio;
/*    */     
/* 79 */     return getRGB(r1 * ratio + r2 * f, g1 * ratio + g2 * f, b1 * ratio + b2 * f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\font\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */