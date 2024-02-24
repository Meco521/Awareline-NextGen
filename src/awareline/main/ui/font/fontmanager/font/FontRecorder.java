/*    */ package awareline.main.ui.font.fontmanager.font;
/*    */ 
/*    */ import java.awt.Font;
/*    */ 
/*    */ public final class FontRecorder {
/*    */   private final Font font;
/*    */   private final boolean antiAliasing;
/*    */   
/*  9 */   public Font getFont() { return this.font; } private final boolean fractionalMetrics; private final boolean textureBlurred; public boolean isAntiAliasing() {
/* 10 */     return this.antiAliasing; }
/* 11 */   public boolean isFractionalMetrics() { return this.fractionalMetrics; } public boolean isTextureBlurred() {
/* 12 */     return this.textureBlurred;
/*    */   }
/*    */   public FontRecorder(Font font, boolean antiAliasing, boolean fractionalMetrics, boolean textureBlurred) {
/* 15 */     this.font = font;
/* 16 */     this.antiAliasing = antiAliasing;
/* 17 */     this.fractionalMetrics = fractionalMetrics;
/* 18 */     this.textureBlurred = textureBlurred;
/*    */   }
/*    */   
/*    */   public FontRecorder deriveFont(int size) {
/* 22 */     return new FontRecorder(this.font.deriveFont(0, size), this.antiAliasing, this.fractionalMetrics, this.textureBlurred);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\font\FontRecorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */