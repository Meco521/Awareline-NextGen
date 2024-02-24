/*     */ package com.me.theresa.fontRenderer.font;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.impl.Font;
/*     */ import com.me.theresa.fontRenderer.font.opengl.Texture;
/*     */ import com.me.theresa.fontRenderer.font.opengl.TextureImpl;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*     */ import com.me.theresa.fontRenderer.font.util.HieroSettings;
/*     */ import com.me.theresa.fontRenderer.font.util.ResourceLoader;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class UnicodeFont implements Font {
/*     */   private static final int DISPLAY_LIST_CACHE_SIZE = 200;
/*     */   private static final int MAX_GLYPH_CODE = 1114111;
/*     */   private static final int PAGE_SIZE = 512;
/*     */   private static final int PAGES = 2175;
/*  30 */   private static final SGL GL = Renderer.get();
/*     */   
/*  32 */   private static final DisplayList EMPTY_DISPLAY_LIST = new DisplayList();
/*     */ 
/*     */   
/*     */   private static Font createFont(String ttfFileRef) throws SlickException {
/*     */     try {
/*  37 */       return Font.createFont(0, ResourceLoader.getResourceAsStream(ttfFileRef));
/*  38 */     } catch (FontFormatException ex) {
/*  39 */       throw new SlickException("Invalid font: " + ttfFileRef, ex);
/*  40 */     } catch (IOException ex) {
/*  41 */       throw new SlickException("Error reading font: " + ttfFileRef, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  46 */   private static final Comparator heightComparator = new Comparator() {
/*     */       public int compare(Object o1, Object o2) {
/*  48 */         return ((Glyph)o1).getHeight() - ((Glyph)o2).getHeight();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private Font font;
/*     */   
/*     */   private String ttfFileRef;
/*     */   
/*     */   private int ascent;
/*     */   
/*     */   private int descent;
/*     */   
/*     */   private int leading;
/*     */   
/*     */   private int spaceWidth;
/*     */   
/*  65 */   private final Glyph[][] glyphs = new Glyph[2175][];
/*     */   
/*  67 */   private final List glyphPages = new ArrayList();
/*     */   
/*  69 */   private final List queuedGlyphs = new ArrayList(256);
/*     */   
/*  71 */   private final List effects = new ArrayList();
/*     */ 
/*     */   
/*     */   private int paddingTop;
/*     */ 
/*     */   
/*     */   private int paddingLeft;
/*     */   
/*     */   private int paddingBottom;
/*     */   
/*     */   private int paddingRight;
/*     */   
/*     */   private int paddingAdvanceX;
/*     */   
/*     */   private int paddingAdvanceY;
/*     */   
/*     */   private Glyph missingGlyph;
/*     */   
/*  89 */   private int glyphPageWidth = 512;
/*     */   
/*  91 */   private int glyphPageHeight = 512;
/*     */ 
/*     */   
/*     */   private boolean displayListCaching = true;
/*     */   
/*  96 */   private int baseDisplayListID = -1;
/*     */   
/*     */   int eldestDisplayListID;
/*     */   
/*     */   private DisplayList eldestDisplayList;
/*     */ 
/*     */   
/* 103 */   private final LinkedHashMap displayLists = new LinkedHashMap<Object, Object>(200, 1.0F, true) {
/*     */       protected boolean removeEldestEntry(Map.Entry eldest) {
/* 105 */         UnicodeFont.DisplayList displayList = (UnicodeFont.DisplayList)eldest.getValue();
/* 106 */         if (displayList != null) UnicodeFont.this.eldestDisplayListID = displayList.id; 
/* 107 */         return (size() > 200);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public UnicodeFont(String ttfFileRef, String hieroFileRef) throws SlickException {
/* 113 */     this(ttfFileRef, new HieroSettings(hieroFileRef));
/*     */   }
/*     */ 
/*     */   
/*     */   public UnicodeFont(String ttfFileRef, HieroSettings settings) throws SlickException {
/* 118 */     this.ttfFileRef = ttfFileRef;
/* 119 */     Font font = createFont(ttfFileRef);
/* 120 */     initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
/* 121 */     loadSettings(settings);
/*     */   }
/*     */ 
/*     */   
/*     */   public UnicodeFont(String ttfFileRef, int size, boolean bold, boolean italic) throws SlickException {
/* 126 */     this.ttfFileRef = ttfFileRef;
/* 127 */     initializeFont(createFont(ttfFileRef), size, bold, italic);
/*     */   }
/*     */ 
/*     */   
/*     */   public UnicodeFont(Font font, String hieroFileRef) throws SlickException {
/* 132 */     this(font, new HieroSettings(hieroFileRef));
/*     */   }
/*     */ 
/*     */   
/*     */   public UnicodeFont(Font font, HieroSettings settings) {
/* 137 */     initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
/* 138 */     loadSettings(settings);
/*     */   }
/*     */ 
/*     */   
/*     */   public UnicodeFont(Font font) {
/* 143 */     initializeFont(font, font.getSize(), font.isBold(), font.isItalic());
/*     */   }
/*     */ 
/*     */   
/*     */   public UnicodeFont(Font font, int size, boolean bold, boolean italic) {
/* 148 */     initializeFont(font, size, bold, italic);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeFont(Font baseFont, int size, boolean bold, boolean italic) {
/* 153 */     Map<TextAttribute, ?> attributes = baseFont.getAttributes();
/* 154 */     attributes.put(TextAttribute.SIZE, new Float(size));
/* 155 */     attributes.put(TextAttribute.WEIGHT, bold ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
/* 156 */     attributes.put(TextAttribute.POSTURE, italic ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
/*     */     try {
/* 158 */       attributes.put(TextAttribute.class.getDeclaredField("KERNING").get(null), TextAttribute.class.getDeclaredField("KERNING_ON")
/* 159 */           .get(null));
/* 160 */     } catch (Exception exception) {}
/*     */     
/* 162 */     this.font = baseFont.deriveFont((Map)attributes);
/*     */     
/* 164 */     FontMetrics metrics = GlyphPage.getScratchGraphics().getFontMetrics(this.font);
/* 165 */     this.ascent = metrics.getAscent();
/* 166 */     this.descent = metrics.getDescent();
/* 167 */     this.leading = metrics.getLeading();
/*     */ 
/*     */     
/* 170 */     char[] chars = " ".toCharArray();
/* 171 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 172 */     this.spaceWidth = (vector.getGlyphLogicalBounds(0).getBounds()).width;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadSettings(HieroSettings settings) {
/* 177 */     this.paddingTop = settings.getPaddingTop();
/* 178 */     this.paddingLeft = settings.getPaddingLeft();
/* 179 */     this.paddingBottom = settings.getPaddingBottom();
/* 180 */     this.paddingRight = settings.getPaddingRight();
/* 181 */     this.paddingAdvanceX = settings.getPaddingAdvanceX();
/* 182 */     this.paddingAdvanceY = settings.getPaddingAdvanceY();
/* 183 */     this.glyphPageWidth = settings.getGlyphPageWidth();
/* 184 */     this.glyphPageHeight = settings.getGlyphPageHeight();
/* 185 */     this.effects.addAll(settings.getEffects());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addGlyphs(int startCodePoint, int endCodePoint) {
/* 190 */     for (int codePoint = startCodePoint; codePoint <= endCodePoint; codePoint++) {
/* 191 */       addGlyphs(new String(Character.toChars(codePoint)));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addGlyphs(String text) {
/* 196 */     if (text == null) throw new IllegalArgumentException("text cannot be null.");
/*     */     
/* 198 */     char[] chars = text.toCharArray();
/* 199 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 200 */     for (int i = 0, n = vector.getNumGlyphs(); i < n; i++) {
/* 201 */       int codePoint = text.codePointAt(vector.getGlyphCharIndex(i));
/* 202 */       Rectangle bounds = getGlyphBounds(vector, i, codePoint);
/* 203 */       getGlyph(vector.getGlyphCode(i), codePoint, bounds, vector, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAsciiGlyphs() {
/* 209 */     addGlyphs(32, 255);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNeheGlyphs() {
/* 214 */     addGlyphs(32, 128);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadGlyphs() throws SlickException {
/* 219 */     return loadGlyphs(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadGlyphs(int maxGlyphsToLoad) throws SlickException {
/* 224 */     if (this.queuedGlyphs.isEmpty()) return false;
/*     */     
/* 226 */     if (this.effects.isEmpty()) {
/* 227 */       throw new IllegalStateException("The UnicodeFont must have at least one effect before any glyphs can be loaded.");
/*     */     }
/* 229 */     for (Iterator<Glyph> iterator = this.queuedGlyphs.iterator(); iterator.hasNext(); ) {
/* 230 */       Glyph glyph = iterator.next();
/* 231 */       int codePoint = glyph.getCodePoint();
/*     */       
/* 233 */       if (glyph.getWidth() == 0 || codePoint == 32) {
/* 234 */         iterator.remove();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 239 */       if (glyph.isMissing()) {
/* 240 */         if (this.missingGlyph != null) {
/* 241 */           if (glyph != this.missingGlyph) iterator.remove(); 
/*     */           continue;
/*     */         } 
/* 244 */         this.missingGlyph = glyph;
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     this.queuedGlyphs.sort(heightComparator);
/*     */ 
/*     */     
/* 251 */     for (Iterator<GlyphPage> iter = this.glyphPages.iterator(); iter.hasNext(); ) {
/* 252 */       GlyphPage glyphPage = iter.next();
/* 253 */       maxGlyphsToLoad -= glyphPage.loadGlyphs(this.queuedGlyphs, maxGlyphsToLoad);
/* 254 */       if (maxGlyphsToLoad == 0 || this.queuedGlyphs.isEmpty()) {
/* 255 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 259 */     while (!this.queuedGlyphs.isEmpty()) {
/* 260 */       GlyphPage glyphPage = new GlyphPage(this, this.glyphPageWidth, this.glyphPageHeight);
/* 261 */       this.glyphPages.add(glyphPage);
/* 262 */       maxGlyphsToLoad -= glyphPage.loadGlyphs(this.queuedGlyphs, maxGlyphsToLoad);
/* 263 */       if (maxGlyphsToLoad == 0) return true;
/*     */     
/*     */     } 
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearGlyphs() {
/* 271 */     for (int i = 0; i < 2175; i++) {
/* 272 */       this.glyphs[i] = null;
/*     */     }
/* 274 */     for (Iterator<GlyphPage> iter = this.glyphPages.iterator(); iter.hasNext(); ) {
/* 275 */       GlyphPage page = iter.next();
/*     */       try {
/* 277 */         page.getImage().destroy();
/* 278 */       } catch (SlickException slickException) {}
/*     */     } 
/*     */     
/* 281 */     this.glyphPages.clear();
/*     */     
/* 283 */     if (this.baseDisplayListID != -1) {
/* 284 */       GL.glDeleteLists(this.baseDisplayListID, this.displayLists.size());
/* 285 */       this.baseDisplayListID = -1;
/*     */     } 
/*     */     
/* 288 */     this.queuedGlyphs.clear();
/* 289 */     this.missingGlyph = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 295 */     clearGlyphs();
/*     */   }
/*     */ 
/*     */   
/*     */   public DisplayList drawDisplayList(float x, float y, String text, Color color, int startIndex, int endIndex) {
/* 300 */     if (text == null) throw new IllegalArgumentException("text cannot be null."); 
/* 301 */     if (text.length() == 0) return EMPTY_DISPLAY_LIST; 
/* 302 */     if (color == null) throw new IllegalArgumentException("color cannot be null.");
/*     */     
/* 304 */     x -= this.paddingLeft;
/* 305 */     y -= this.paddingTop;
/*     */     
/* 307 */     String displayListKey = text.substring(startIndex, endIndex);
/*     */     
/* 309 */     color.bind();
/* 310 */     TextureImpl.bindNone();
/*     */     
/* 312 */     DisplayList displayList = null;
/* 313 */     if (this.displayListCaching && this.queuedGlyphs.isEmpty()) {
/* 314 */       if (this.baseDisplayListID == -1) {
/* 315 */         this.baseDisplayListID = GL.glGenLists(200);
/* 316 */         if (this.baseDisplayListID == 0) {
/* 317 */           this.baseDisplayListID = -1;
/* 318 */           this.displayListCaching = false;
/* 319 */           return new DisplayList();
/*     */         } 
/*     */       } 
/*     */       
/* 323 */       displayList = (DisplayList)this.displayLists.get(displayListKey);
/* 324 */       if (displayList != null) {
/* 325 */         if (displayList.invalid) {
/* 326 */           displayList.invalid = false;
/*     */         } else {
/* 328 */           GL.glTranslatef(x, y, 0.0F);
/* 329 */           GL.glCallList(displayList.id);
/* 330 */           GL.glTranslatef(-x, -y, 0.0F);
/* 331 */           return displayList;
/*     */         } 
/* 333 */       } else if (displayList == null) {
/*     */         
/* 335 */         displayList = new DisplayList();
/* 336 */         int displayListCount = this.displayLists.size();
/* 337 */         this.displayLists.put(displayListKey, displayList);
/* 338 */         if (displayListCount < 200) {
/* 339 */           displayList.id = this.baseDisplayListID + displayListCount;
/*     */         } else {
/* 341 */           displayList.id = this.eldestDisplayListID;
/*     */         } 
/* 343 */       }  this.displayLists.put(displayListKey, displayList);
/*     */     } 
/*     */     
/* 346 */     GL.glTranslatef(x, y, 0.0F);
/*     */     
/* 348 */     if (displayList != null) GL.glNewList(displayList.id, 4865);
/*     */     
/* 350 */     char[] chars = text.substring(0, endIndex).toCharArray();
/* 351 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/*     */     
/* 353 */     int maxWidth = 0, totalHeight = 0, lines = 0;
/* 354 */     int extraX = 0, extraY = this.ascent;
/* 355 */     boolean startNewLine = false;
/* 356 */     Texture lastBind = null;
/* 357 */     for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++) {
/* 358 */       int charIndex = vector.getGlyphCharIndex(glyphIndex);
/* 359 */       if (charIndex >= startIndex) {
/* 360 */         if (charIndex > endIndex)
/*     */           break; 
/* 362 */         int codePoint = text.codePointAt(charIndex);
/*     */         
/* 364 */         Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
/* 365 */         Glyph glyph = getGlyph(vector.getGlyphCode(glyphIndex), codePoint, bounds, vector, glyphIndex);
/*     */         
/* 367 */         if (startNewLine && codePoint != 10) {
/* 368 */           extraX = -bounds.x;
/* 369 */           startNewLine = false;
/*     */         } 
/*     */         
/* 372 */         Image image = glyph.getImage();
/* 373 */         if (image == null && this.missingGlyph != null && glyph.isMissing()) image = this.missingGlyph.getImage(); 
/* 374 */         if (image != null) {
/*     */           
/* 376 */           Texture texture = image.getTexture();
/* 377 */           if (lastBind != null && lastBind != texture) {
/* 378 */             GL.glEnd();
/* 379 */             lastBind = null;
/*     */           } 
/* 381 */           if (lastBind == null) {
/* 382 */             texture.bind();
/* 383 */             GL.glBegin(7);
/* 384 */             lastBind = texture;
/*     */           } 
/* 386 */           image.drawEmbedded((bounds.x + extraX), (bounds.y + extraY), image.getWidth(), image.getHeight());
/*     */         } 
/*     */         
/* 389 */         if (glyphIndex >= 0) extraX += this.paddingRight + this.paddingLeft + this.paddingAdvanceX; 
/* 390 */         maxWidth = Math.max(maxWidth, bounds.x + extraX + bounds.width);
/* 391 */         totalHeight = Math.max(totalHeight, this.ascent + bounds.y + bounds.height);
/*     */         
/* 393 */         if (codePoint == 10) {
/* 394 */           startNewLine = true;
/* 395 */           extraY += getLineHeight();
/* 396 */           lines++;
/* 397 */           totalHeight = 0;
/*     */         } 
/*     */       } 
/* 400 */     }  if (lastBind != null) GL.glEnd();
/*     */     
/* 402 */     if (displayList != null) {
/* 403 */       GL.glEndList();
/*     */       
/* 405 */       if (!this.queuedGlyphs.isEmpty()) displayList.invalid = true;
/*     */     
/*     */     } 
/* 408 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */     
/* 410 */     if (displayList == null) displayList = new DisplayList(); 
/* 411 */     displayList.width = (short)maxWidth;
/* 412 */     displayList.height = (short)(lines * getLineHeight() + totalHeight);
/* 413 */     return displayList;
/*     */   }
/*     */   
/*     */   public void drawString(float x, float y, String text, Color color, int startIndex, int endIndex) {
/* 417 */     drawDisplayList(x, y, text, color, startIndex, endIndex);
/*     */   }
/*     */   
/*     */   public void drawString(float x, float y, String text) {
/* 421 */     drawString(x, y, text, Color.white);
/*     */   }
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col) {
/* 425 */     drawString(x, y, text, col, 0, text.length());
/*     */   }
/*     */ 
/*     */   
/*     */   private Glyph getGlyph(int glyphCode, int codePoint, Rectangle bounds, GlyphVector vector, int index) {
/* 430 */     if (glyphCode < 0 || glyphCode >= 1114111)
/*     */     {
/* 432 */       return new Glyph(codePoint, bounds, vector, index, this) {
/*     */           public boolean isMissing() {
/* 434 */             return true;
/*     */           }
/*     */         };
/*     */     }
/* 438 */     int pageIndex = glyphCode / 512;
/* 439 */     int glyphIndex = glyphCode & 0x1FF;
/* 440 */     Glyph glyph = null;
/* 441 */     Glyph[] page = this.glyphs[pageIndex];
/* 442 */     if (page != null) {
/* 443 */       glyph = page[glyphIndex];
/* 444 */       if (glyph != null) return glyph; 
/*     */     } else {
/* 446 */       page = this.glyphs[pageIndex] = new Glyph[512];
/*     */     } 
/* 448 */     glyph = page[glyphIndex] = new Glyph(codePoint, bounds, vector, index, this);
/* 449 */     this.queuedGlyphs.add(glyph);
/* 450 */     return glyph;
/*     */   }
/*     */ 
/*     */   
/*     */   private Rectangle getGlyphBounds(GlyphVector vector, int index, int codePoint) {
/* 455 */     Rectangle bounds = vector.getGlyphPixelBounds(index, GlyphPage.renderContext, 0.0F, 0.0F);
/* 456 */     if (codePoint == 32) bounds.width = this.spaceWidth; 
/* 457 */     return bounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpaceWidth() {
/* 462 */     return this.spaceWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/* 467 */     if (text == null) throw new IllegalArgumentException("text cannot be null."); 
/* 468 */     if (text.length() == 0) return 0;
/*     */     
/* 470 */     if (this.displayListCaching) {
/* 471 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 472 */       if (displayList != null) return displayList.width;
/*     */     
/*     */     } 
/* 475 */     char[] chars = text.toCharArray();
/* 476 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/*     */     
/* 478 */     int width = 0;
/* 479 */     int extraX = 0;
/* 480 */     boolean startNewLine = false;
/* 481 */     for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++) {
/* 482 */       int charIndex = vector.getGlyphCharIndex(glyphIndex);
/* 483 */       int codePoint = text.codePointAt(charIndex);
/* 484 */       Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
/*     */       
/* 486 */       if (startNewLine && codePoint != 10) extraX = -bounds.x;
/*     */       
/* 488 */       if (glyphIndex > 0) extraX += this.paddingLeft + this.paddingRight + this.paddingAdvanceX; 
/* 489 */       width = Math.max(width, bounds.x + extraX + bounds.width);
/*     */       
/* 491 */       if (codePoint == 10) startNewLine = true;
/*     */     
/*     */     } 
/* 494 */     return width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight(String text) {
/* 499 */     if (text == null) throw new IllegalArgumentException("text cannot be null."); 
/* 500 */     if (text.length() == 0) return 0;
/*     */     
/* 502 */     if (this.displayListCaching) {
/* 503 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 504 */       if (displayList != null) return displayList.height;
/*     */     
/*     */     } 
/* 507 */     char[] chars = text.toCharArray();
/* 508 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/*     */     
/* 510 */     int lines = 0, height = 0;
/* 511 */     for (int i = 0, n = vector.getNumGlyphs(); i < n; i++) {
/* 512 */       int charIndex = vector.getGlyphCharIndex(i);
/* 513 */       int codePoint = text.codePointAt(charIndex);
/* 514 */       if (codePoint != 32) {
/* 515 */         Rectangle bounds = getGlyphBounds(vector, i, codePoint);
/*     */         
/* 517 */         height = Math.max(height, this.ascent + bounds.y + bounds.height);
/*     */         
/* 519 */         if (codePoint == 10) {
/* 520 */           lines++;
/* 521 */           height = 0;
/*     */         } 
/*     */       } 
/* 524 */     }  return lines * getLineHeight() + height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYOffset(String text) {
/* 529 */     if (text == null) throw new IllegalArgumentException("text cannot be null.");
/*     */     
/* 531 */     DisplayList displayList = null;
/* 532 */     if (this.displayListCaching) {
/* 533 */       displayList = (DisplayList)this.displayLists.get(text);
/* 534 */       if (displayList != null && displayList.yOffset != null) return displayList.yOffset.intValue();
/*     */     
/*     */     } 
/* 537 */     int index = text.indexOf('\n');
/* 538 */     if (index != -1) text = text.substring(0, index); 
/* 539 */     char[] chars = text.toCharArray();
/* 540 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 541 */     int yOffset = this.ascent + (vector.getPixelBounds(null, 0.0F, 0.0F)).y;
/*     */     
/* 543 */     if (displayList != null) displayList.yOffset = new Short((short)yOffset);
/*     */     
/* 545 */     return yOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 550 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingTop() {
/* 555 */     return this.paddingTop;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingTop(int paddingTop) {
/* 560 */     this.paddingTop = paddingTop;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingLeft() {
/* 565 */     return this.paddingLeft;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingLeft(int paddingLeft) {
/* 570 */     this.paddingLeft = paddingLeft;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingBottom() {
/* 575 */     return this.paddingBottom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingBottom(int paddingBottom) {
/* 580 */     this.paddingBottom = paddingBottom;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingRight() {
/* 585 */     return this.paddingRight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingRight(int paddingRight) {
/* 590 */     this.paddingRight = paddingRight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingAdvanceX() {
/* 595 */     return this.paddingAdvanceX;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingAdvanceX(int paddingAdvanceX) {
/* 600 */     this.paddingAdvanceX = paddingAdvanceX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingAdvanceY() {
/* 605 */     return this.paddingAdvanceY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingAdvanceY(int paddingAdvanceY) {
/* 610 */     this.paddingAdvanceY = paddingAdvanceY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLineHeight() {
/* 615 */     return this.descent + this.ascent + this.leading + this.paddingTop + this.paddingBottom + this.paddingAdvanceY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAscent() {
/* 620 */     return this.ascent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDescent() {
/* 625 */     return this.descent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLeading() {
/* 630 */     return this.leading;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlyphPageWidth() {
/* 635 */     return this.glyphPageWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlyphPageWidth(int glyphPageWidth) {
/* 640 */     this.glyphPageWidth = glyphPageWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlyphPageHeight() {
/* 645 */     return this.glyphPageHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlyphPageHeight(int glyphPageHeight) {
/* 650 */     this.glyphPageHeight = glyphPageHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getGlyphPages() {
/* 655 */     return this.glyphPages;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getEffects() {
/* 660 */     return this.effects;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCaching() {
/* 665 */     return this.displayListCaching;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisplayListCaching(boolean displayListCaching) {
/* 670 */     this.displayListCaching = displayListCaching;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFontFile() {
/* 675 */     if (this.ttfFileRef == null) {
/*     */ 
/*     */       
/*     */       try {
/* 679 */         Object font2D = Class.forName("sun.font.FontManager").getDeclaredMethod("getFont2D", new Class[] { Font.class }).invoke(null, new Object[] { this.font });
/* 680 */         Field platNameField = Class.forName("sun.font.PhysicalFont").getDeclaredField("platName");
/* 681 */         platNameField.setAccessible(true);
/* 682 */         this.ttfFileRef = (String)platNameField.get(font2D);
/* 683 */       } catch (Throwable throwable) {}
/*     */       
/* 685 */       if (this.ttfFileRef == null) this.ttfFileRef = ""; 
/*     */     } 
/* 687 */     if (this.ttfFileRef.length() == 0) return null; 
/* 688 */     return this.ttfFileRef;
/*     */   }
/*     */   
/*     */   public static class DisplayList {
/*     */     boolean invalid;
/*     */     int id;
/*     */     Short yOffset;
/*     */     public short width;
/*     */     public short height;
/*     */     public Object userData;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\UnicodeFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */