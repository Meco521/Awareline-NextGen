/*      */ package awareline.main.ui.font.fastuni;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Point;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.text.Bidi;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.WeakHashMap;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StringCache
/*      */ {
/*      */   private static final int BASELINE_OFFSET = 7;
/*      */   private static final int UNDERLINE_OFFSET = 1;
/*      */   private static final int UNDERLINE_THICKNESS = 2;
/*      */   private static final int STRIKETHROUGH_OFFSET = -6;
/*      */   private static final int STRIKETHROUGH_THICKNESS = 2;
/*      */   private GlyphCache glyphCache;
/*      */   private int[] colorTable;
/*   77 */   private WeakHashMap<Key, Entry> stringCache = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   private WeakHashMap<String, Key> weakRefCache = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   92 */   private Key lookupKey = new Key();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   private Glyph[][] digitGlyphs = new Glyph[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean digitGlyphsReady = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean antiAliasEnabled = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Thread mainThread;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Key
/*      */   {
/*      */     public String str;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Key() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  141 */       int code = 0, length = this.str.length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  148 */       boolean colorCode = false;
/*      */       
/*  150 */       for (int index = 0; index < length; index++) {
/*      */         
/*  152 */         char c = this.str.charAt(index);
/*  153 */         if (c >= '0' && c <= '9' && !colorCode)
/*      */         {
/*  155 */           c = '0';
/*      */         }
/*  157 */         code = code * 31 + c;
/*  158 */         colorCode = (c == '§');
/*      */       } 
/*      */       
/*  161 */       return code;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  178 */       if (o == null)
/*      */       {
/*  180 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  184 */       String other = o.toString();
/*  185 */       int length = this.str.length();
/*      */       
/*  187 */       if (length != other.length())
/*      */       {
/*  189 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  197 */       boolean colorCode = false;
/*      */       
/*  199 */       for (int index = 0; index < length; index++) {
/*      */         
/*  201 */         char c1 = this.str.charAt(index);
/*  202 */         char c2 = other.charAt(index);
/*      */         
/*  204 */         if (c1 != c2 && (c1 < '0' || c1 > '9' || c2 < '0' || c2 > '9' || colorCode))
/*      */         {
/*  206 */           return false;
/*      */         }
/*  208 */         colorCode = (c1 == '§');
/*      */       } 
/*      */       
/*  211 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  222 */       return this.str;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Entry
/*      */   {
/*      */     public WeakReference<StringCache.Key> keyRef;
/*      */ 
/*      */     
/*      */     public int advance;
/*      */ 
/*      */     
/*      */     public StringCache.Glyph[] glyphs;
/*      */ 
/*      */     
/*      */     public StringCache.ColorCode[] colors;
/*      */ 
/*      */     
/*      */     public boolean specialRender;
/*      */ 
/*      */ 
/*      */     
/*      */     private Entry() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ColorCode
/*      */     implements Comparable<Integer>
/*      */   {
/*      */     public static final byte UNDERLINE = 1;
/*      */ 
/*      */     
/*      */     public static final byte STRIKETHROUGH = 2;
/*      */ 
/*      */     
/*      */     public int stringIndex;
/*      */ 
/*      */     
/*      */     public int stripIndex;
/*      */ 
/*      */     
/*      */     public byte colorCode;
/*      */ 
/*      */     
/*      */     public byte fontStyle;
/*      */     
/*      */     public byte renderStyle;
/*      */ 
/*      */     
/*      */     private ColorCode() {}
/*      */ 
/*      */     
/*      */     public int compareTo(Integer i) {
/*  278 */       return (this.stringIndex == i.intValue()) ? 0 : ((this.stringIndex < i.intValue()) ? -1 : 1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Glyph
/*      */     implements Comparable<Glyph>
/*      */   {
/*      */     public int stringIndex;
/*      */ 
/*      */ 
/*      */     
/*      */     public GlyphCache.Entry texture;
/*      */ 
/*      */ 
/*      */     
/*      */     public int x;
/*      */ 
/*      */ 
/*      */     
/*      */     public int y;
/*      */ 
/*      */ 
/*      */     
/*      */     public int advance;
/*      */ 
/*      */ 
/*      */     
/*      */     private Glyph() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public int compareTo(Glyph o) {
/*  313 */       return (this.stringIndex == o.stringIndex) ? 0 : ((this.stringIndex < o.stringIndex) ? -1 : 1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringCache(int[] colors) {
/*  327 */     this.mainThread = Thread.currentThread();
/*      */     
/*  329 */     this.glyphCache = new GlyphCache();
/*  330 */     this.colorTable = colors;
/*      */ 
/*      */     
/*  333 */     cacheDightGlyphs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultFont(String fontName, int fontSize, boolean antiAlias) {
/*  347 */     this.glyphCache.setDefaultFont(fontName, fontSize, antiAlias);
/*  348 */     this.antiAliasEnabled = antiAlias;
/*  349 */     this.weakRefCache.clear();
/*  350 */     this.stringCache.clear();
/*      */ 
/*      */     
/*  353 */     cacheDightGlyphs();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultFont(Font font, int fontSize, boolean antiAlias) {
/*  359 */     this.glyphCache.setDefaultFont(font, fontSize, true);
/*  360 */     this.antiAliasEnabled = true;
/*  361 */     this.weakRefCache.clear();
/*  362 */     this.stringCache.clear();
/*      */ 
/*      */     
/*  365 */     cacheDightGlyphs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cacheDightGlyphs() {
/*  375 */     this.digitGlyphsReady = false;
/*  376 */     this.digitGlyphs[0] = (cacheString("0123456789")).glyphs;
/*  377 */     this.digitGlyphs[1] = (cacheString("§l0123456789")).glyphs;
/*  378 */     this.digitGlyphs[2] = (cacheString("§o0123456789")).glyphs;
/*  379 */     this.digitGlyphs[3] = (cacheString("§l§o0123456789")).glyphs;
/*  380 */     this.digitGlyphsReady = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int renderString(String str, float startX, float startY, int initialColor, boolean shadowFlag) {
/*  402 */     if (str == null || str.isEmpty())
/*      */     {
/*  404 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  408 */     GL11.glTexEnvi(8960, 8704, 8448);
/*      */ 
/*      */     
/*  411 */     Entry entry = cacheString(str);
/*      */ 
/*      */     
/*  414 */     startY += 7.0F;
/*      */ 
/*      */     
/*  417 */     int color = initialColor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  424 */     GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  434 */     if (this.antiAliasEnabled) {
/*      */       
/*  436 */       GlStateManager.enableBlend();
/*  437 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */ 
/*      */     
/*  441 */     Tessellator tessellator = Tessellator.getInstance();
/*  442 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*      */     
/*  444 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  445 */     GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
/*      */ 
/*      */     
/*  448 */     int fontStyle = 0;
/*      */     
/*  450 */     for (int glyphIndex = 0, colorIndex = 0; glyphIndex < entry.glyphs.length; glyphIndex++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  458 */       while (colorIndex < entry.colors.length && (entry.glyphs[glyphIndex]).stringIndex >= (entry.colors[colorIndex]).stringIndex) {
/*      */         
/*  460 */         color = applyColorCode((entry.colors[colorIndex]).colorCode, initialColor, shadowFlag);
/*  461 */         fontStyle = (entry.colors[colorIndex]).fontStyle;
/*  462 */         colorIndex++;
/*      */       } 
/*      */ 
/*      */       
/*  466 */       Glyph glyph = entry.glyphs[glyphIndex];
/*  467 */       GlyphCache.Entry texture = glyph.texture;
/*  468 */       int glyphX = glyph.x;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  476 */       char c = str.charAt(glyph.stringIndex);
/*  477 */       if (c >= '0' && c <= '9') {
/*      */         
/*  479 */         int oldWidth = texture.width;
/*  480 */         texture = (this.digitGlyphs[fontStyle][c - 48]).texture;
/*  481 */         int newWidth = texture.width;
/*  482 */         glyphX += oldWidth - newWidth >> 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  490 */       GlStateManager.enableTexture2D();
/*  491 */       tessellator.draw();
/*  492 */       worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*      */ 
/*      */       
/*  495 */       GlStateManager.bindTexture(texture.textureName);
/*      */ 
/*      */ 
/*      */       
/*  499 */       float x1 = startX + glyphX / 2.0F;
/*  500 */       float x2 = startX + (glyphX + texture.width) / 2.0F;
/*  501 */       float y1 = startY + glyph.y / 2.0F;
/*  502 */       float y2 = startY + (glyph.y + texture.height) / 2.0F;
/*      */       
/*  504 */       int a = color >> 24 & 0xFF;
/*  505 */       int r = color >> 16 & 0xFF;
/*  506 */       int g = color >> 8 & 0xFF;
/*  507 */       int b = color & 0xFF;
/*      */       
/*  509 */       worldRenderer.pos(x1, y1, 0.0D).tex(texture.u1, texture.v1).color(r, g, b, a).endVertex();
/*  510 */       worldRenderer.pos(x1, y2, 0.0D).tex(texture.u1, texture.v2).color(r, g, b, a).endVertex();
/*  511 */       worldRenderer.pos(x2, y2, 0.0D).tex(texture.u2, texture.v2).color(r, g, b, a).endVertex();
/*  512 */       worldRenderer.pos(x2, y1, 0.0D).tex(texture.u2, texture.v1).color(r, g, b, a).endVertex();
/*      */     } 
/*      */ 
/*      */     
/*  516 */     tessellator.draw();
/*      */ 
/*      */ 
/*      */     
/*  520 */     if (entry.specialRender) {
/*      */       
/*  522 */       int renderStyle = 0;
/*      */ 
/*      */       
/*  525 */       color = initialColor;
/*  526 */       GlStateManager.disableTexture2D();
/*  527 */       worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  528 */       GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
/*      */       
/*  530 */       for (int i = 0, j = 0; i < entry.glyphs.length; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  537 */         while (j < entry.colors.length && (entry.glyphs[i]).stringIndex >= (entry.colors[j]).stringIndex) {
/*      */           
/*  539 */           color = applyColorCode((entry.colors[j]).colorCode, initialColor, shadowFlag);
/*  540 */           renderStyle = (entry.colors[j]).renderStyle;
/*  541 */           j++;
/*      */         } 
/*      */ 
/*      */         
/*  545 */         Glyph glyph = entry.glyphs[i];
/*      */ 
/*      */         
/*  548 */         int glyphSpace = glyph.advance - glyph.texture.width;
/*      */ 
/*      */         
/*  551 */         if ((renderStyle & 0x1) != 0) {
/*      */ 
/*      */           
/*  554 */           float x1 = startX + (glyph.x - glyphSpace) / 2.0F;
/*  555 */           float x2 = startX + (glyph.x + glyph.advance) / 2.0F;
/*  556 */           float y1 = startY + 0.5F;
/*  557 */           float y2 = startY + 1.5F;
/*      */           
/*  559 */           worldRenderer.pos(x1, y1, 0.0D).endVertex();
/*  560 */           worldRenderer.pos(x1, y2, 0.0D).endVertex();
/*  561 */           worldRenderer.pos(x2, y2, 0.0D).endVertex();
/*  562 */           worldRenderer.pos(x2, y1, 0.0D).endVertex();
/*      */         } 
/*      */ 
/*      */         
/*  566 */         if ((renderStyle & 0x2) != 0) {
/*      */ 
/*      */           
/*  569 */           float x1 = startX + (glyph.x - glyphSpace) / 2.0F;
/*  570 */           float x2 = startX + (glyph.x + glyph.advance) / 2.0F;
/*  571 */           float y1 = startY + -3.0F;
/*  572 */           float y2 = startY + -2.0F;
/*      */           
/*  574 */           worldRenderer.pos(x1, y1, 0.0D).endVertex();
/*  575 */           worldRenderer.pos(x1, y2, 0.0D).endVertex();
/*  576 */           worldRenderer.pos(x2, y2, 0.0D).endVertex();
/*  577 */           worldRenderer.pos(x2, y1, 0.0D).endVertex();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  582 */       tessellator.draw();
/*  583 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  588 */     return entry.advance / 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringWidth(String str) {
/*  600 */     if (str == null || str.isEmpty())
/*      */     {
/*  602 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  606 */     Entry entry = cacheString(str);
/*      */ 
/*      */     
/*  609 */     return entry.advance / 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sizeString(String str, int width, boolean breakAtSpaces) {
/*  625 */     if (str == null || str.isEmpty())
/*      */     {
/*  627 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  631 */     width += width;
/*      */ 
/*      */     
/*  634 */     Glyph[] glyphs = (cacheString(str)).glyphs;
/*      */ 
/*      */     
/*  637 */     int wsIndex = -1;
/*      */ 
/*      */     
/*  640 */     int advance = 0, index = 0;
/*  641 */     while (index < glyphs.length && advance <= width) {
/*      */ 
/*      */       
/*  644 */       if (breakAtSpaces) {
/*      */         
/*  646 */         char c = str.charAt((glyphs[index]).stringIndex);
/*  647 */         if (c == ' ') {
/*      */           
/*  649 */           wsIndex = index;
/*  650 */         } else if (c == '\n') {
/*      */           
/*  652 */           wsIndex = index;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  657 */       advance += (glyphs[index]).advance;
/*  658 */       index++;
/*      */     } 
/*      */ 
/*      */     
/*  662 */     if (index < glyphs.length && wsIndex != -1 && wsIndex < index)
/*      */     {
/*  664 */       index = wsIndex;
/*      */     }
/*      */ 
/*      */     
/*  668 */     return (index < glyphs.length) ? (glyphs[index]).stringIndex : str.length();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int sizeStringToWidth(String str, int width) {
/*  680 */     return sizeString(str, width, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String str, int width, boolean reverse) {
/*  693 */     int length = sizeString(str, width, false);
/*  694 */     str = str.substring(0, length);
/*      */     
/*  696 */     if (reverse)
/*      */     {
/*  698 */       str = (new StringBuilder(str)).reverse().toString();
/*      */     }
/*      */     
/*  701 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int applyColorCode(int colorCode, int color, boolean shadowFlag) {
/*  716 */     if (colorCode != -1) {
/*      */       
/*  718 */       colorCode = shadowFlag ? (colorCode + 16) : colorCode;
/*  719 */       color = this.colorTable[colorCode] & 0xFFFFFF | color & 0xFF000000;
/*      */     } 
/*      */ 
/*      */     
/*  723 */     Tessellator.getInstance().getWorldRenderer().color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, shadowFlag ? 80 : (color >> 24 & 0xFF));
/*  724 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry cacheString(String str) {
/*  747 */     Entry entry = null;
/*      */ 
/*      */     
/*  750 */     if (this.mainThread == Thread.currentThread()) {
/*      */ 
/*      */       
/*  753 */       this.lookupKey.str = str;
/*      */ 
/*      */       
/*  756 */       entry = this.stringCache.get(this.lookupKey);
/*      */     } 
/*      */ 
/*      */     
/*  760 */     if (entry == null) {
/*      */ 
/*      */       
/*  763 */       char[] text = str.toCharArray();
/*      */ 
/*      */       
/*  766 */       entry = new Entry();
/*  767 */       int length = stripColorCodes(entry, str, text);
/*      */ 
/*      */       
/*  770 */       List<Glyph> glyphList = new ArrayList<>();
/*  771 */       entry.advance = layoutBidiString(glyphList, text, 0, length, entry.colors);
/*      */ 
/*      */       
/*  774 */       entry.glyphs = new Glyph[glyphList.size()];
/*  775 */       entry.glyphs = glyphList.<Glyph>toArray(entry.glyphs);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  781 */       Arrays.sort((Object[])entry.glyphs);
/*      */ 
/*      */       
/*  784 */       int colorIndex = 0, shift = 0;
/*  785 */       for (int glyphIndex = 0; glyphIndex < entry.glyphs.length; glyphIndex++) {
/*      */         
/*  787 */         Glyph glyph = entry.glyphs[glyphIndex];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  795 */         while (colorIndex < entry.colors.length && glyph.stringIndex + shift >= (entry.colors[colorIndex]).stringIndex) {
/*      */           
/*  797 */           shift += 2;
/*  798 */           colorIndex++;
/*      */         } 
/*  800 */         glyph.stringIndex += shift;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  807 */       if (this.mainThread == Thread.currentThread()) {
/*      */ 
/*      */         
/*  810 */         Key key = new Key();
/*      */ 
/*      */         
/*  813 */         key.str = str;
/*  814 */         entry.keyRef = new WeakReference<>(key);
/*  815 */         this.stringCache.put(key, entry);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  820 */     if (this.mainThread == Thread.currentThread()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  828 */       Key oldKey = entry.keyRef.get();
/*  829 */       if (oldKey != null)
/*      */       {
/*  831 */         this.weakRefCache.put(str, oldKey);
/*      */       }
/*  833 */       this.lookupKey.str = null;
/*      */     } 
/*      */ 
/*      */     
/*  837 */     return entry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int stripColorCodes(Entry cacheEntry, String str, char[] text) {
/*  852 */     List<ColorCode> colorList = new ArrayList<>();
/*  853 */     int start = 0, shift = 0;
/*      */     
/*  855 */     byte fontStyle = 0;
/*  856 */     byte renderStyle = 0;
/*  857 */     byte colorCode = -1;
/*      */     
/*      */     int next;
/*  860 */     while ((next = str.indexOf('§', start)) != -1 && next + 1 < str.length()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  868 */       System.arraycopy(text, next - shift + 2, text, next - shift, text.length - next - 2);
/*      */ 
/*      */       
/*  871 */       int code = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(str.charAt(next + 1)));
/*  872 */       switch (code) {
/*      */         case 16:
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 17:
/*  880 */           fontStyle = (byte)(fontStyle | 0x1);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 18:
/*  885 */           renderStyle = (byte)(renderStyle | 0x2);
/*  886 */           cacheEntry.specialRender = true;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 19:
/*  891 */           renderStyle = (byte)(renderStyle | 0x1);
/*  892 */           cacheEntry.specialRender = true;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 20:
/*  897 */           fontStyle = (byte)(fontStyle | 0x2);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 21:
/*  902 */           fontStyle = 0;
/*  903 */           renderStyle = 0;
/*  904 */           colorCode = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  909 */           if (code >= 0 && code <= 15) {
/*      */             
/*  911 */             colorCode = (byte)code;
/*  912 */             fontStyle = 0;
/*  913 */             renderStyle = 0;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/*  919 */       ColorCode entry = new ColorCode();
/*  920 */       entry.stringIndex = next;
/*  921 */       entry.stripIndex = next - shift;
/*  922 */       entry.colorCode = colorCode;
/*  923 */       entry.fontStyle = fontStyle;
/*  924 */       entry.renderStyle = renderStyle;
/*  925 */       colorList.add(entry);
/*      */ 
/*      */       
/*  928 */       start = next + 2;
/*  929 */       shift += 2;
/*      */     } 
/*      */ 
/*      */     
/*  933 */     cacheEntry.colors = new ColorCode[colorList.size()];
/*  934 */     cacheEntry.colors = colorList.<ColorCode>toArray(cacheEntry.colors);
/*      */ 
/*      */     
/*  937 */     return text.length - shift;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int layoutBidiString(List<Glyph> glyphList, char[] text, int start, int limit, ColorCode[] colors) {
/*  952 */     int advance = 0;
/*      */ 
/*      */     
/*  955 */     if (Bidi.requiresBidi(text, start, limit)) {
/*      */ 
/*      */       
/*  958 */       Bidi bidi = new Bidi(text, start, null, 0, limit - start, -2);
/*      */ 
/*      */       
/*  961 */       if (bidi.isRightToLeft())
/*      */       {
/*  963 */         return layoutStyle(glyphList, text, start, limit, 1, advance, colors);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  969 */       int runCount = bidi.getRunCount();
/*  970 */       byte[] levels = new byte[runCount];
/*  971 */       Integer[] ranges = new Integer[runCount];
/*      */ 
/*      */       
/*  974 */       for (int index = 0; index < runCount; index++) {
/*      */         
/*  976 */         levels[index] = (byte)bidi.getRunLevel(index);
/*  977 */         ranges[index] = Integer.valueOf(index);
/*      */       } 
/*  979 */       Bidi.reorderVisually(levels, 0, (Object[])ranges, 0, runCount);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  986 */       for (int visualIndex = 0; visualIndex < runCount; visualIndex++) {
/*      */         
/*  988 */         int logicalIndex = ranges[visualIndex].intValue();
/*      */ 
/*      */         
/*  991 */         int layoutFlag = ((bidi.getRunLevel(logicalIndex) & 0x1) == 1) ? 1 : 0;
/*  992 */         advance = layoutStyle(glyphList, text, start + bidi.getRunStart(logicalIndex), start + bidi.getRunLimit(logicalIndex), layoutFlag, advance, colors);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  997 */       return advance;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1003 */     return layoutStyle(glyphList, text, start, limit, 0, advance, colors);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int layoutStyle(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, ColorCode[] colors) {
/* 1009 */     int currentFontStyle = 0;
/*      */ 
/*      */     
/* 1012 */     int colorIndex = Arrays.binarySearch((Object[])colors, Integer.valueOf(start));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1019 */     if (colorIndex < 0)
/*      */     {
/* 1021 */       colorIndex = -colorIndex - 2;
/*      */     }
/*      */ 
/*      */     
/* 1025 */     while (start < limit) {
/*      */       
/* 1027 */       int next = limit;
/*      */ 
/*      */       
/* 1030 */       while (colorIndex >= 0 && colorIndex < colors.length - 1 && (colors[colorIndex]).stripIndex == (colors[colorIndex + 1]).stripIndex)
/*      */       {
/* 1032 */         colorIndex++;
/*      */       }
/*      */ 
/*      */       
/* 1036 */       if (colorIndex >= 0 && colorIndex < colors.length)
/*      */       {
/* 1038 */         currentFontStyle = (colors[colorIndex]).fontStyle;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1045 */       while (++colorIndex < colors.length) {
/*      */         
/* 1047 */         if ((colors[colorIndex]).fontStyle != currentFontStyle) {
/*      */           
/* 1049 */           next = (colors[colorIndex]).stripIndex;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/* 1055 */       advance = layoutString(glyphList, text, start, next, layoutFlags, advance, currentFontStyle);
/* 1056 */       start = next;
/*      */     } 
/*      */     
/* 1059 */     return advance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int layoutString(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, int style) {
/* 1087 */     if (this.digitGlyphsReady)
/*      */     {
/* 1089 */       for (int index = start; index < limit; index++) {
/*      */         
/* 1091 */         if (text[index] >= '0' && text[index] <= '9')
/*      */         {
/* 1093 */           text[index] = '0';
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1099 */     while (start < limit) {
/*      */       
/* 1101 */       Font font = this.glyphCache.lookupFont(text, start, limit, style);
/* 1102 */       int next = font.canDisplayUpTo(text, start, limit);
/*      */ 
/*      */       
/* 1105 */       if (next == -1)
/*      */       {
/* 1107 */         next = limit;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1115 */       if (next == start)
/*      */       {
/* 1117 */         next++;
/*      */       }
/*      */       
/* 1120 */       advance = layoutFont(glyphList, text, start, next, layoutFlags, advance, font);
/* 1121 */       start = next;
/*      */     } 
/*      */     
/* 1124 */     return advance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int layoutFont(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, Font font) {
/* 1149 */     if (this.mainThread == Thread.currentThread())
/*      */     {
/* 1151 */       this.glyphCache.cacheGlyphs(font, text, start, limit, layoutFlags);
/*      */     }
/*      */ 
/*      */     
/* 1155 */     GlyphVector vector = this.glyphCache.layoutGlyphVector(font, text, start, limit, layoutFlags);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1163 */     Glyph glyph = null;
/* 1164 */     int numGlyphs = vector.getNumGlyphs();
/* 1165 */     for (int index = 0; index < numGlyphs; index++) {
/*      */       
/* 1167 */       Point position = vector.getGlyphPixelBounds(index, null, advance, 0.0F).getLocation();
/*      */ 
/*      */       
/* 1170 */       if (glyph != null)
/*      */       {
/* 1172 */         glyph.advance = position.x - glyph.x;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1179 */       glyph = new Glyph();
/* 1180 */       glyph.stringIndex = start + vector.getGlyphCharIndex(index);
/* 1181 */       glyph.texture = this.glyphCache.lookupGlyph(font, vector.getGlyphCode(index));
/* 1182 */       glyph.x = position.x;
/* 1183 */       glyph.y = position.y;
/* 1184 */       glyphList.add(glyph);
/*      */     } 
/*      */ 
/*      */     
/* 1188 */     advance += (int)vector.getGlyphPosition(numGlyphs).getX();
/* 1189 */     if (glyph != null)
/*      */     {
/* 1191 */       glyph.advance = advance - glyph.x;
/*      */     }
/*      */ 
/*      */     
/* 1195 */     return advance;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fastuni\StringCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */