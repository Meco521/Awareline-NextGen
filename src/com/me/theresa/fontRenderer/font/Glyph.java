/*    */ package com.me.theresa.fontRenderer.font;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.Shape;
/*    */ import java.awt.font.GlyphMetrics;
/*    */ import java.awt.font.GlyphVector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Glyph
/*    */ {
/*    */   private final int codePoint;
/*    */   private short width;
/*    */   private short height;
/*    */   private short yOffset;
/*    */   private final boolean isMissing;
/*    */   private Shape shape;
/*    */   private Image image;
/*    */   
/*    */   public Glyph(int codePoint, Rectangle bounds, GlyphVector vector, int index, UnicodeFont unicodeFont) {
/* 26 */     this.codePoint = codePoint;
/*    */     
/* 28 */     GlyphMetrics metrics = vector.getGlyphMetrics(index);
/* 29 */     int lsb = (int)metrics.getLSB();
/* 30 */     if (lsb > 0) lsb = 0; 
/* 31 */     int rsb = (int)metrics.getRSB();
/* 32 */     if (rsb > 0) rsb = 0;
/*    */     
/* 34 */     int glyphWidth = bounds.width - lsb - rsb;
/* 35 */     int glyphHeight = bounds.height;
/* 36 */     if (glyphWidth > 0 && glyphHeight > 0) {
/* 37 */       int padTop = unicodeFont.getPaddingTop();
/* 38 */       int padRight = unicodeFont.getPaddingRight();
/* 39 */       int padBottom = unicodeFont.getPaddingBottom();
/* 40 */       int padLeft = unicodeFont.getPaddingLeft();
/* 41 */       int glyphSpacing = 1;
/* 42 */       this.width = (short)(glyphWidth + padLeft + padRight + glyphSpacing);
/* 43 */       this.height = (short)(glyphHeight + padTop + padBottom + glyphSpacing);
/* 44 */       this.yOffset = (short)(unicodeFont.getAscent() + bounds.y - padTop);
/*    */     } 
/*    */     
/* 47 */     this.shape = vector.getGlyphOutline(index, (-bounds.x + unicodeFont.getPaddingLeft()), (-bounds.y + unicodeFont.getPaddingTop()));
/*    */     
/* 49 */     this.isMissing = !unicodeFont.getFont().canDisplay((char)codePoint);
/*    */   }
/*    */   
/*    */   public int getCodePoint() {
/* 53 */     return this.codePoint;
/*    */   }
/*    */   
/*    */   public boolean isMissing() {
/* 57 */     return this.isMissing;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 61 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 65 */     return this.height;
/*    */   }
/*    */   
/*    */   public Shape getShape() {
/* 69 */     return this.shape;
/*    */   }
/*    */   
/*    */   public void setShape(Shape shape) {
/* 73 */     this.shape = shape;
/*    */   }
/*    */   
/*    */   public Image getImage() {
/* 77 */     return this.image;
/*    */   }
/*    */   
/*    */   public void setImage(Image image) {
/* 81 */     this.image = image;
/*    */   }
/*    */   
/*    */   public int getYOffset() {
/* 85 */     return this.yOffset;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\Glyph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */