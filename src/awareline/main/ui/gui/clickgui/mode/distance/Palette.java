/*    */ package awareline.main.ui.gui.clickgui.mode.distance;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Palette
/*    */ {
/* 10 */   GREEN(() -> new Color(0, 255, 138)),
/* 11 */   WHITE(() -> Color.WHITE),
/* 12 */   PURPLE(() -> new Color(198, 139, 255)),
/* 13 */   DARK_PURPLE(() -> new Color(133, 46, 215)),
/* 14 */   BLUE(() -> new Color(116, 202, 255));
/*    */   
/*    */   private final Supplier<? extends Color> colorSupplier;
/*    */   
/*    */   Palette(Supplier<? extends Color> colorSupplier) {
/* 19 */     this.colorSupplier = colorSupplier;
/*    */   }
/*    */   
/*    */   public static Color fade(Color color) {
/* 23 */     return fade(color, 2, 100);
/*    */   }
/*    */   
/*    */   public static Color fade(Color color, int index, int count) {
/* 27 */     float[] hsb = new float[3];
/* 28 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
/* 29 */     float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + index / count * 2.0F) % 2.0F - 1.0F);
/* 30 */     brightness = 0.5F + 0.5F * brightness;
/* 31 */     hsb[2] = brightness % 2.0F;
/* 32 */     return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 36 */     return this.colorSupplier.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\distance\Palette.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */