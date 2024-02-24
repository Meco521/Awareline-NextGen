/*     */ package awareline.main.ui.font.fastuni;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GlyphCache
/*     */ {
/*     */   private static final int TEXTURE_WIDTH = 256;
/*     */   private static final int TEXTURE_HEIGHT = 256;
/*     */   private static final int STRING_WIDTH = 256;
/*     */   private static final int STRING_HEIGHT = 64;
/*     */   private static final int GLYPH_BORDER = 1;
/*  58 */   private static Color BACK_COLOR = new Color(255, 255, 255, 0);
/*     */ 
/*     */   
/*  61 */   private int fontSize = 18;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean antiAliasEnabled = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage stringImage;
/*     */ 
/*     */   
/*     */   private Graphics2D stringGraphics;
/*     */ 
/*     */   
/*  75 */   private BufferedImage glyphCacheImage = new BufferedImage(256, 256, 2);
/*     */ 
/*     */   
/*  78 */   private Graphics2D glyphCacheGraphics = this.glyphCacheImage.createGraphics();
/*     */ 
/*     */   
/*  81 */   private FontRenderContext fontRenderContext = this.glyphCacheGraphics.getFontRenderContext();
/*     */ 
/*     */ 
/*     */   
/*  85 */   private int[] imageData = new int[65536];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private IntBuffer imageBuffer = ByteBuffer.allocateDirect(262144).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
/*     */ 
/*     */   
/*  95 */   private IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
/*     */ 
/*     */   
/*  98 */   private List<Font> allFonts = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private List<Font> usedFonts = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int textureName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private LinkedHashMap<Font, Integer> fontCache = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   private LinkedHashMap<Long, Entry> glyphCache = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private int cachePosX = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private int cachePosY = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   private int cacheLineHeight = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Entry
/*     */   {
/*     */     public int textureName;
/*     */ 
/*     */ 
/*     */     
/*     */     public int width;
/*     */ 
/*     */ 
/*     */     
/*     */     public int height;
/*     */ 
/*     */ 
/*     */     
/*     */     public float u1;
/*     */ 
/*     */ 
/*     */     
/*     */     public float v1;
/*     */ 
/*     */ 
/*     */     
/*     */     public float u2;
/*     */ 
/*     */     
/*     */     public float v2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphCache() {
/* 182 */     this.glyphCacheGraphics.setBackground(BACK_COLOR);
/*     */ 
/*     */     
/* 185 */     this.glyphCacheGraphics.setComposite(AlphaComposite.Src);
/*     */     
/* 187 */     allocateGlyphCacheTexture();
/* 188 */     allocateStringImage(256, 64);
/*     */ 
/*     */     
/* 191 */     GraphicsEnvironment.getLocalGraphicsEnvironment().preferLocaleFonts();
/* 192 */     this.usedFonts.add(new Font("SansSerif", 0, 72));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setDefaultFont(String name, int size, boolean antiAlias) {
/* 206 */     this.usedFonts.clear();
/* 207 */     this.usedFonts.add(FontLoader.getFonts(46, "HarmonyOS_Sans_SC_Regular.ttf"));
/*     */     
/* 209 */     this.fontSize = size;
/* 210 */     this.antiAliasEnabled = antiAlias;
/* 211 */     setRenderingHints();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setDefaultFont(Font font, int size, boolean antiAlias) {
/* 217 */     this.usedFonts.clear();
/* 218 */     this.usedFonts.add(font);
/*     */     
/* 220 */     this.fontSize = size;
/* 221 */     this.antiAliasEnabled = antiAlias;
/* 222 */     setRenderingHints();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GlyphVector layoutGlyphVector(Font font, char[] text, int start, int limit, int layoutFlags) {
/* 237 */     if (!this.fontCache.containsKey(font))
/*     */     {
/* 239 */       this.fontCache.put(font, Integer.valueOf(this.fontCache.size()));
/*     */     }
/* 241 */     return font.layoutGlyphVector(this.fontRenderContext, text, start, limit, layoutFlags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Font lookupFont(char[] text, int start, int limit, int style) {
/* 257 */     Iterator<Font> iterator = this.usedFonts.iterator();
/* 258 */     while (iterator.hasNext()) {
/*     */ 
/*     */       
/* 261 */       Font font1 = iterator.next();
/* 262 */       if (font1.canDisplayUpTo(text, start, limit) != start)
/*     */       {
/*     */         
/* 265 */         return font1.deriveFont(style, this.fontSize);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 270 */     iterator = this.allFonts.iterator();
/* 271 */     while (iterator.hasNext()) {
/*     */ 
/*     */       
/* 274 */       Font font1 = iterator.next();
/* 275 */       if (font1.canDisplayUpTo(text, start, limit) != start) {
/*     */ 
/*     */ 
/*     */         
/* 279 */         this.usedFonts.add(font1);
/*     */ 
/*     */         
/* 282 */         return font1.deriveFont(style, this.fontSize);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 287 */     Font font = this.usedFonts.get(0);
/*     */ 
/*     */     
/* 290 */     return font.deriveFont(style, this.fontSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Entry lookupGlyph(Font font, int glyphCode) {
/* 305 */     long fontKey = ((Integer)this.fontCache.get(font)).intValue() << 32L;
/* 306 */     return this.glyphCache.get(Long.valueOf(fontKey | glyphCode));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cacheGlyphs(Font font, char[] text, int start, int limit, int layoutFlags) {
/* 325 */     GlyphVector vector = layoutGlyphVector(font, text, start, limit, layoutFlags);
/*     */ 
/*     */     
/* 328 */     Rectangle vectorBounds = null;
/*     */ 
/*     */     
/* 331 */     long fontKey = ((Integer)this.fontCache.get(font)).intValue() << 32L;
/*     */     
/* 333 */     int numGlyphs = vector.getNumGlyphs();
/* 334 */     Rectangle dirty = null;
/* 335 */     boolean vectorRendered = false;
/*     */     
/* 337 */     for (int index = 0; index < numGlyphs; index++) {
/*     */ 
/*     */       
/* 340 */       int glyphCode = vector.getGlyphCode(index);
/* 341 */       if (!this.glyphCache.containsKey(Long.valueOf(fontKey | glyphCode))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 353 */         if (!vectorRendered) {
/*     */           
/* 355 */           vectorRendered = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 363 */           for (int i = 0; i < numGlyphs; i++) {
/*     */             
/* 365 */             Point2D pos = vector.getGlyphPosition(i);
/* 366 */             pos.setLocation(pos.getX() + (2 * i), pos.getY());
/* 367 */             vector.setGlyphPosition(i, pos);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 376 */           vectorBounds = vector.getPixelBounds(this.fontRenderContext, 0.0F, 0.0F);
/*     */ 
/*     */           
/* 379 */           if (this.stringImage == null || vectorBounds.width > this.stringImage.getWidth() || vectorBounds.height > this.stringImage.getHeight()) {
/*     */             
/* 381 */             int width = Math.max(vectorBounds.width, this.stringImage.getWidth());
/* 382 */             int height = Math.max(vectorBounds.height, this.stringImage.getHeight());
/* 383 */             allocateStringImage(width, height);
/*     */           } 
/*     */ 
/*     */           
/* 387 */           this.stringGraphics.clearRect(0, 0, vectorBounds.width, vectorBounds.height);
/*     */ 
/*     */           
/* 390 */           this.stringGraphics.drawGlyphVector(vector, -vectorBounds.x, -vectorBounds.y);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 399 */         Rectangle rect = vector.getGlyphPixelBounds(index, null, -vectorBounds.x, -vectorBounds.y);
/*     */ 
/*     */         
/* 402 */         if (this.cachePosX + rect.width + 1 > 256) {
/*     */           
/* 404 */           this.cachePosX = 1;
/* 405 */           this.cachePosY += this.cacheLineHeight + 1;
/* 406 */           this.cacheLineHeight = 0;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 414 */         if (this.cachePosY + rect.height + 1 > 256) {
/*     */           
/* 416 */           updateTexture(dirty);
/* 417 */           dirty = null;
/*     */ 
/*     */           
/* 420 */           allocateGlyphCacheTexture();
/* 421 */           this.cachePosY = this.cachePosX = 1;
/* 422 */           this.cacheLineHeight = 0;
/*     */         } 
/*     */ 
/*     */         
/* 426 */         if (rect.height > this.cacheLineHeight)
/*     */         {
/* 428 */           this.cacheLineHeight = rect.height;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 436 */         this.glyphCacheGraphics.drawImage(this.stringImage, this.cachePosX, this.cachePosY, this.cachePosX + rect.width, this.cachePosY + rect.height, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 444 */         rect.setLocation(this.cachePosX, this.cachePosY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 450 */         Entry entry = new Entry();
/* 451 */         entry.textureName = this.textureName;
/* 452 */         entry.width = rect.width;
/* 453 */         entry.height = rect.height;
/* 454 */         entry.u1 = rect.x / 256.0F;
/* 455 */         entry.v1 = rect.y / 256.0F;
/* 456 */         entry.u2 = (rect.x + rect.width) / 256.0F;
/* 457 */         entry.v2 = (rect.y + rect.height) / 256.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 463 */         this.glyphCache.put(Long.valueOf(fontKey | glyphCode), entry);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 471 */         if (dirty == null) {
/*     */           
/* 473 */           dirty = new Rectangle(this.cachePosX, this.cachePosY, rect.width, rect.height);
/*     */         }
/*     */         else {
/*     */           
/* 477 */           dirty.add(rect);
/*     */         } 
/*     */ 
/*     */         
/* 481 */         this.cachePosX += rect.width + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 485 */     updateTexture(dirty);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateTexture(Rectangle dirty) {
/* 500 */     if (dirty != null) {
/*     */ 
/*     */       
/* 503 */       updateImageBuffer(dirty.x, dirty.y, dirty.width, dirty.height);
/*     */       
/* 505 */       GlStateManager.bindTexture(this.textureName);
/* 506 */       GL11.glTexSubImage2D(3553, 0, dirty.x, dirty.y, dirty.width, dirty.height, 6408, 5121, this.imageBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void allocateStringImage(int width, int height) {
/* 517 */     this.stringImage = new BufferedImage(width, height, 2);
/* 518 */     this.stringGraphics = this.stringImage.createGraphics();
/* 519 */     setRenderingHints();
/*     */ 
/*     */     
/* 522 */     this.stringGraphics.setBackground(BACK_COLOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 528 */     this.stringGraphics.setPaint(Color.WHITE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setRenderingHints() {
/* 538 */     this.stringGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAliasEnabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*     */     
/* 540 */     this.stringGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAliasEnabled ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*     */ 
/*     */     
/* 543 */     this.stringGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void allocateGlyphCacheTexture() {
/* 556 */     this.glyphCacheGraphics.clearRect(0, 0, 256, 256);
/*     */ 
/*     */     
/* 559 */     this.singleIntBuffer.clear();
/* 560 */     GL11.glGenTextures(this.singleIntBuffer);
/* 561 */     this.textureName = this.singleIntBuffer.get(0);
/*     */ 
/*     */     
/* 564 */     updateImageBuffer(0, 0, 256, 256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 570 */     GlStateManager.bindTexture(this.textureName);
/* 571 */     GL11.glTexImage2D(3553, 0, 32828, 256, 256, 0, 6408, 5121, this.imageBuffer);
/*     */ 
/*     */ 
/*     */     
/* 575 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 576 */     GL11.glTexParameteri(3553, 10240, 9728);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateImageBuffer(int x, int y, int width, int height) {
/* 591 */     this.glyphCacheImage.getRGB(x, y, width, height, this.imageData, 0, width);
/*     */ 
/*     */     
/* 594 */     for (int i = 0; i < width * height; i++) {
/*     */       
/* 596 */       int color = this.imageData[i];
/* 597 */       this.imageData[i] = color << 8 | color >>> 24;
/*     */     } 
/*     */ 
/*     */     
/* 601 */     this.imageBuffer.clear();
/* 602 */     this.imageBuffer.put(this.imageData);
/* 603 */     this.imageBuffer.flip();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fastuni\GlyphCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */