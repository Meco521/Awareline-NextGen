/*    */ package awareline.main.ui.font.fontmanager.font;
/*    */ 
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ public final class MCFontDrawer implements IFont {
/*    */   private final FontRenderer fontRenderer;
/*    */   
/*    */   public MCFontDrawer(FontRenderer fontRenderer) {
/* 10 */     this.fontRenderer = fontRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStringWidth(String s) {
/* 15 */     return this.fontRenderer.getStringWidth(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getStringWidth_double(String s) {
/* 20 */     return this.fontRenderer.getStringWidth_float(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCharWidth(char c) {
/* 25 */     return this.fontRenderer.getCharWidth(c);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 30 */     return this.fontRenderer.FONT_HEIGHT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawStringWithShadow(String s, double x, double y, int color) {
/* 35 */     this.fontRenderer.drawStringWithShadow(s, (float)x, (float)y, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawString(String s, double x, double y, int color) {
/* 40 */     this.fontRenderer.drawString(s, (float)x, (float)y, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawCenteredStringWithShadow(String s, double x, double y, int color) {
/* 45 */     drawStringWithShadow(s, x - getStringWidth(s) / 2.0D, y, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawCenteredString(String s, double x, double y, int color) {
/* 50 */     drawString(s, x - getStringWidth(s) / 2.0D, y, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawStringWithOutline(String s, double x, double y, int color) {
/* 55 */     float f = (float)x;
/* 56 */     float f1 = (float)y;
/* 57 */     String s1 = EnumChatFormatting.getTextWithoutFormattingCodesFast(s);
/*    */     
/* 59 */     this.fontRenderer.drawString(s1, f + 1.0F, f1, 0);
/* 60 */     this.fontRenderer.drawString(s1, f - 1.0F, f1, 0);
/* 61 */     this.fontRenderer.drawString(s1, f, f1 + 1.0F, 0);
/* 62 */     this.fontRenderer.drawString(s1, f, f1 - 1.0F, 0);
/* 63 */     this.fontRenderer.drawString(s, f, f1, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawCharWithShadow(char c, double x, double y, int color) {
/* 68 */     drawStringWithShadow(String.valueOf(c), x, y, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawChar(char c, double x, double y, int color) {
/* 73 */     drawString(String.valueOf(c), x, y, color);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\font\MCFontDrawer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */