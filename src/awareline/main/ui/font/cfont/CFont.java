/*     */ package awareline.main.ui.font.cfont;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CFont {
/*     */   protected final CharData[] charData;
/*     */   protected Font font;
/*     */   
/*     */   public Font getFont() {
/*  13 */     return this.font;
/*     */   } protected boolean antiAlias; protected boolean fractionalMetrics; public boolean isAntiAlias() {
/*  15 */     return this.antiAlias;
/*     */   } public boolean isFractionalMetrics() {
/*  17 */     return this.fractionalMetrics;
/*     */   }
/*  19 */   protected int fontHeight = -1;
/*     */   protected int charOffset;
/*     */   protected DynamicTexture tex;
/*     */   
/*     */   public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  24 */     this.charData = new CharData[256];
/*  25 */     this.font = font;
/*  26 */     this.antiAlias = antiAlias;
/*  27 */     this.fractionalMetrics = fractionalMetrics;
/*  28 */     this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  32 */     BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);
/*     */     try {
/*  34 */       return new DynamicTexture(img);
/*  35 */     } catch (Exception e) {
/*  36 */       e.printStackTrace();
/*  37 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  42 */     int imgSize = 512;
/*  43 */     BufferedImage bufferedImage = new BufferedImage(512, 512, 2);
/*  44 */     Graphics2D g = (Graphics2D)bufferedImage.getGraphics();
/*  45 */     g.setFont(font);
/*  46 */     g.setColor(new Color(255, 255, 255, 0));
/*  47 */     g.fillRect(0, 0, 512, 512);
/*  48 */     g.setColor(Color.WHITE);
/*  49 */     g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*  50 */     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*  51 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*  52 */     FontMetrics fontMetrics = g.getFontMetrics();
/*  53 */     int charHeight = 0;
/*  54 */     int positionX = 0;
/*  55 */     int positionY = 1;
/*  56 */     for (int i = 0; i < chars.length; i++) {
/*  57 */       char ch = (char)i;
/*  58 */       CharData charData = new CharData();
/*  59 */       Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
/*  60 */       charData.width = (dimensions.getBounds()).width + 8;
/*  61 */       charData.height = (dimensions.getBounds()).height;
/*  62 */       if (positionX + charData.width >= 512) {
/*  63 */         positionX = 0;
/*  64 */         positionY += charHeight;
/*  65 */         charHeight = 0;
/*     */       } 
/*  67 */       if (charData.height > charHeight) {
/*  68 */         charHeight = charData.height;
/*     */       }
/*  70 */       charData.storedX = positionX;
/*  71 */       charData.storedY = positionY;
/*  72 */       if (charData.height > this.fontHeight) {
/*  73 */         this.fontHeight = charData.height;
/*     */       }
/*  75 */       chars[i] = charData;
/*  76 */       g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
/*  77 */       positionX += charData.width;
/*     */     } 
/*  79 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public void drawChar(CharData[] chars, char c, float x, float y) {
/*     */     try {
/*  84 */       drawQuad(x, y, (chars[c]).width, (chars[c]).height, (chars[c]).storedX, (chars[c]).storedY, (chars[c]).width, (chars[c]).height);
/*  85 */     } catch (Exception e) {
/*  86 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
/*  92 */     int IMG_SIZE = 512;
/*  93 */     float renderSRCX = srcX / 512.0F;
/*  94 */     float renderSRCY = srcY / 512.0F;
/*  95 */     float renderSRCWidth = srcWidth / 512.0F;
/*  96 */     float renderSRCHeight = srcHeight / 512.0F;
/*     */     
/*  98 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/*  99 */     GL11.glVertex2d((x + width), y);
/* 100 */     GL11.glTexCoord2f(renderSRCX, renderSRCY);
/* 101 */     GL11.glVertex2d(x, y);
/* 102 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 103 */     GL11.glVertex2d(x, (y + height));
/* 104 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 105 */     GL11.glVertex2d(x, (y + height));
/* 106 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
/* 107 */     GL11.glVertex2d((x + width), (y + height));
/* 108 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 109 */     GL11.glVertex2d((x + width), y);
/*     */   }
/*     */   
/*     */   public int getStringHeight(String text) {
/* 113 */     return getHeight();
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 117 */     return (this.fontHeight - 8) / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 121 */     int width = 0;
/* 122 */     char[] arrc = text.toCharArray();
/* 123 */     int n = arrc.length;
/* 124 */     int n2 = 0;
/* 125 */     while (n2 < n) {
/* 126 */       char c = arrc[n2];
/* 127 */       if (c < this.charData.length) {
/* 128 */         width += (this.charData[c]).width - 8 + this.charOffset;
/*     */       }
/* 130 */       n2++;
/*     */     } 
/* 132 */     return width / 2;
/*     */   }
/*     */   
/*     */   public int getCharWidth(char c) {
/* 136 */     return getStringWidth(Character.toString(c));
/*     */   }
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 140 */     if (this.antiAlias != antiAlias) {
/* 141 */       this.antiAlias = antiAlias;
/* 142 */       this.tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 147 */     if (this.fractionalMetrics != fractionalMetrics) {
/* 148 */       this.fractionalMetrics = fractionalMetrics;
/* 149 */       this.tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFont(Font font) {
/* 154 */     this.font = font;
/* 155 */     this.tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   public float getMiddleOfBox(float height) {
/* 159 */     return height / 2.0F - getHeight() / 2.0F;
/*     */   }
/*     */   
/*     */   protected class CharData {
/*     */     public int width;
/*     */     public int height;
/*     */     public int storedX;
/*     */     public int storedY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\cfont\CFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */