/*    */ package awareline.main.utility.render.color;
/*    */ 
/*    */ import awareline.main.mod.implement.visual.HUD;
/*    */ import java.awt.Color;
/*    */ 
/*    */ public class ColorManager
/*    */ {
/*  8 */   public static final ColorObject eVis = new ColorObject(255, 0, 0, 255);
/*  9 */   public static final ColorObject eInvis = new ColorObject(255, 255, 0, 255);
/*    */   
/*    */   public static int HUDColor() {
/* 12 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*    */   }
/*    */   
/*    */   public static Color fade(Color color, int index, int count) {
/* 16 */     float[] hsb = new float[3];
/* 17 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
/* 18 */     float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + index / count * 2.0F) % 2.0F - 1.0F);
/* 19 */     brightness = 0.5F + 0.5F * brightness;
/* 20 */     hsb[2] = brightness % 2.0F;
/* 21 */     return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
/*    */   }
/*    */   
/*    */   public static ColorObject getEnemyVisible() {
/* 25 */     return eVis;
/*    */   }
/*    */   
/*    */   public static ColorObject getEnemyInvisible() {
/* 29 */     return eInvis;
/*    */   }
/*    */   
/*    */   public static int astolfoRainbow(int delay, int offset, int index) {
/* 33 */     double rainbowDelay = Math.ceil((System.currentTimeMillis() + delay * index)) / offset;
/* 34 */     return Color.getHSBColor(((float)((rainbowDelay %= 360.0D) / 360.0D) < 0.5D) ? -((float)(rainbowDelay / 360.0D)) : (float)(rainbowDelay / 360.0D), 0.5F, 1.0F).getRGB();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\color\ColorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */