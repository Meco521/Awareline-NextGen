/*     */ package com.me.theresa.fontRenderer.font;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.effect.Effect;
/*     */ import com.me.theresa.fontRenderer.font.opengl.TextureImpl;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class GlyphPage {
/*  23 */   private static final SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   public static final int MAX_GLYPH_SIZE = 256;
/*     */ 
/*     */   
/*  29 */   private static final ByteBuffer scratchByteBuffer = ByteBuffer.allocateDirect(262144);
/*     */   
/*     */   static {
/*  32 */     scratchByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*     */   }
/*     */ 
/*     */   
/*  36 */   private static final IntBuffer scratchIntBuffer = scratchByteBuffer.asIntBuffer();
/*     */ 
/*     */   
/*  39 */   private static final BufferedImage scratchImage = new BufferedImage(256, 256, 2);
/*     */   
/*  41 */   private static final Graphics2D scratchGraphics = (Graphics2D)scratchImage.getGraphics();
/*     */   
/*     */   static {
/*  44 */     scratchGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  45 */     scratchGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  46 */     scratchGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*     */   }
/*     */ 
/*     */   
/*  50 */   public static FontRenderContext renderContext = scratchGraphics.getFontRenderContext();
/*     */   private final UnicodeFont unicodeFont;
/*     */   
/*     */   public static Graphics2D getScratchGraphics() {
/*  54 */     return scratchGraphics;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final int pageWidth;
/*     */ 
/*     */   
/*     */   private final int pageHeight;
/*     */   
/*     */   private final Image pageImage;
/*     */   
/*     */   private int pageX;
/*     */   
/*     */   private int pageY;
/*     */   
/*     */   private int rowHeight;
/*     */   
/*     */   private boolean orderAscending;
/*     */   
/*  74 */   private final List pageGlyphs = new ArrayList(32);
/*     */ 
/*     */   
/*     */   public GlyphPage(UnicodeFont unicodeFont, int pageWidth, int pageHeight) throws SlickException {
/*  78 */     this.unicodeFont = unicodeFont;
/*  79 */     this.pageWidth = pageWidth;
/*  80 */     this.pageHeight = pageHeight;
/*     */     
/*  82 */     this.pageImage = new Image(pageWidth, pageHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public int loadGlyphs(List glyphs, int maxGlyphsToLoad) throws SlickException {
/*  87 */     if (this.rowHeight != 0 && maxGlyphsToLoad == -1) {
/*     */       
/*  89 */       int testX = this.pageX;
/*  90 */       int testY = this.pageY;
/*  91 */       int testRowHeight = this.rowHeight;
/*  92 */       for (Iterator<Glyph> iterator = getIterator(glyphs); iterator.hasNext(); ) {
/*  93 */         Glyph glyph = iterator.next();
/*  94 */         int width = glyph.getWidth();
/*  95 */         int height = glyph.getHeight();
/*  96 */         if (testX + width >= this.pageWidth) {
/*  97 */           testX = 0;
/*  98 */           testY += testRowHeight;
/*  99 */           testRowHeight = height;
/* 100 */         } else if (height > testRowHeight) {
/* 101 */           testRowHeight = height;
/*     */         } 
/* 103 */         if (testY + testRowHeight >= this.pageWidth) return 0; 
/* 104 */         testX += width;
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     Color.white.bind();
/* 109 */     this.pageImage.bind();
/*     */     
/* 111 */     int i = 0;
/* 112 */     for (Iterator<Glyph> iter = getIterator(glyphs); iter.hasNext(); ) {
/* 113 */       Glyph glyph = iter.next();
/* 114 */       int width = Math.min(256, glyph.getWidth());
/* 115 */       int height = Math.min(256, glyph.getHeight());
/*     */       
/* 117 */       if (this.rowHeight == 0) {
/*     */         
/* 119 */         this.rowHeight = height;
/*     */       
/*     */       }
/* 122 */       else if (this.pageX + width >= this.pageWidth) {
/* 123 */         if (this.pageY + this.rowHeight + height >= this.pageHeight)
/* 124 */           break;  this.pageX = 0;
/* 125 */         this.pageY += this.rowHeight;
/* 126 */         this.rowHeight = height;
/* 127 */       } else if (height > this.rowHeight) {
/* 128 */         if (this.pageY + height >= this.pageHeight)
/* 129 */           break;  this.rowHeight = height;
/*     */       } 
/*     */ 
/*     */       
/* 133 */       renderGlyph(glyph, width, height);
/* 134 */       this.pageGlyphs.add(glyph);
/*     */       
/* 136 */       this.pageX += width;
/*     */       
/* 138 */       iter.remove();
/* 139 */       i++;
/* 140 */       if (i == maxGlyphsToLoad) {
/*     */         
/* 142 */         this.orderAscending = !this.orderAscending;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 147 */     TextureImpl.bindNone();
/*     */ 
/*     */     
/* 150 */     this.orderAscending = !this.orderAscending;
/*     */     
/* 152 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderGlyph(Glyph glyph, int width, int height) {
/* 158 */     scratchGraphics.setComposite(AlphaComposite.Clear);
/* 159 */     scratchGraphics.fillRect(0, 0, 256, 256);
/* 160 */     scratchGraphics.setComposite(AlphaComposite.SrcOver);
/* 161 */     scratchGraphics.setColor(Color.white);
/* 162 */     for (Iterator<Effect> iter = this.unicodeFont.getEffects().iterator(); iter.hasNext();)
/* 163 */       ((Effect)iter.next()).draw(scratchImage, scratchGraphics, this.unicodeFont, glyph); 
/* 164 */     glyph.setShape(null);
/*     */     
/* 166 */     WritableRaster raster = scratchImage.getRaster();
/* 167 */     int[] row = new int[width];
/* 168 */     for (int y = 0; y < height; y++) {
/* 169 */       raster.getDataElements(0, y, width, 1, row);
/* 170 */       scratchIntBuffer.put(row);
/*     */     } 
/* 172 */     GL.glTexSubImage2D(3553, 0, this.pageX, this.pageY, width, height, 32993, 5121, scratchByteBuffer);
/*     */     
/* 174 */     scratchIntBuffer.clear();
/*     */     
/* 176 */     glyph.setImage(this.pageImage.getSubImage(this.pageX, this.pageY, width, height));
/*     */   }
/*     */ 
/*     */   
/*     */   private Iterator getIterator(List glyphs) {
/* 181 */     if (this.orderAscending) return glyphs.iterator(); 
/* 182 */     final ListIterator iter = glyphs.listIterator(glyphs.size());
/* 183 */     return new Iterator() {
/*     */         public boolean hasNext() {
/* 185 */           return iter.hasPrevious();
/*     */         }
/*     */         
/*     */         public Object next() {
/* 189 */           return iter.previous();
/*     */         }
/*     */         
/*     */         public void remove() {
/* 193 */           iter.remove();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public List getGlyphs() {
/* 199 */     return this.pageGlyphs;
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 204 */     return this.pageImage;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\GlyphPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */