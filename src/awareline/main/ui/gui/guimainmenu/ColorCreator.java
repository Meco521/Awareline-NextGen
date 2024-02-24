/*    */ package awareline.main.ui.gui.guimainmenu;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorCreator
/*    */ {
/* 10 */   private static final ArrayList<Color> loggedColors = new ArrayList<>();
/*    */   
/*    */   public static int create(int r, int g, int b) {
/* 13 */     for (Color color1 : loggedColors) {
/* 14 */       if (color1.getRed() == r && color1.getGreen() == g && color1.getBlue() == b && color1.getAlpha() == 255) {
/* 15 */         return color1.getRGB();
/*    */       }
/*    */     } 
/*    */     
/* 19 */     Color color = new Color(r, g, b);
/* 20 */     loggedColors.add(color);
/* 21 */     return color.getRGB();
/*    */   }
/*    */   
/*    */   public static int create(int r, int g, int b, int a) {
/* 25 */     for (Color color1 : loggedColors) {
/* 26 */       if (color1.getRed() == r && color1.getGreen() == g && color1.getBlue() == b && color1.getAlpha() == a) {
/* 27 */         return color1.getRGB();
/*    */       }
/*    */     } 
/*    */     
/* 31 */     Color color = new Color(r, g, b, a);
/* 32 */     loggedColors.add(color);
/* 33 */     return color.getRGB();
/*    */   }
/*    */   
/*    */   public static int createRainbowFromOffset(int speed, int offset) {
/* 37 */     float hue = (float)((System.currentTimeMillis() + offset) % speed);
/* 38 */     return Color.getHSBColor(hue / speed, 0.6F, 1.0F).getRGB();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\ColorCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */