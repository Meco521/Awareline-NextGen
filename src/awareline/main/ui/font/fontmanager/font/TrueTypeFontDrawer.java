/*     */ package awareline.main.ui.font.fontmanager.font;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public final class TrueTypeFontDrawer implements IFont {
/*  20 */   private static final int[] COLORS = new int[32]; private static final String COLOR_CODE = "0123456789abcdefklmnor";
/*     */   private static final String RANDOM_STRING = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000";
/*  22 */   private final Char2ObjectMap<Glyph> glyphs = (Char2ObjectMap<Glyph>)new Char2ObjectOpenHashMap();
/*     */   private final Glyph unsupportedGlyph;
/*     */   private final FontRecorder[] fonts;
/*     */   private final int fontSize;
/*     */   private final int imageSize;
/*     */   private final double halfImageSize;
/*     */   private final int height;
/*     */   
/*     */   static {
/*  31 */     for (int i = 0; i < COLORS.length; i++) {
/*  32 */       int offset = (i >> 3 & 0x1) * 85;
/*     */       
/*  34 */       int red = (i >> 2 & 0x1) * 170 + offset;
/*  35 */       int green = (i >> 1 & 0x1) * 170 + offset;
/*  36 */       int blue = (i & 0x1) * 170 + offset;
/*     */       
/*  38 */       if (i == 6) {
/*  39 */         red += 85;
/*     */       }
/*     */       
/*  42 */       if (i >= 16) {
/*  43 */         red /= 4;
/*  44 */         green /= 4;
/*  45 */         blue /= 4;
/*     */       } 
/*     */       
/*  48 */       COLORS[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   public TrueTypeFontDrawer(FontRecorder font) {
/*  53 */     this(new FontRecorder[] { font });
/*     */   }
/*     */   
/*     */   public TrueTypeFontDrawer(FontRecorder[] fonts) {
/*  57 */     if (fonts.length == 0)
/*  58 */       throw new IllegalStateException("Fonts is empty"); 
/*  59 */     if (fonts.length > 1) {
/*  60 */       for (FontRecorder font : fonts) {
/*  61 */         if (font.getFont().getSize() != fonts[0].getFont().getSize()) {
/*  62 */           throw new IllegalStateException("Font size: " + font.getFont().getFontName() + font.getFont().getSize());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  67 */     this.fonts = fonts;
/*     */     
/*  69 */     Font f = fonts[0].getFont();
/*     */     
/*  71 */     this.fontSize = f.getSize();
/*  72 */     this.imageSize = f.getSize() + MathHelper.floor_double(f.getSize() / 8.0D) + 2;
/*  73 */     this.halfImageSize = this.imageSize / 2.0D;
/*  74 */     this.height = f.getSize() / 2;
/*     */     
/*  76 */     this.unsupportedGlyph = genUnsupportedGlyph();
/*     */   }
/*     */   
/*     */   private Glyph genUnsupportedGlyph() {
/*  80 */     BufferedImage image = new BufferedImage(this.imageSize, this.imageSize, 2);
/*  81 */     Graphics2D g = image.createGraphics();
/*     */     
/*  83 */     g.setColor(Color.WHITE);
/*  84 */     g.drawRect(0, 0, this.imageSize - 1, this.imageSize - 1);
/*  85 */     g.dispose();
/*     */     
/*  87 */     return new Glyph(image, this.imageSize, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStringWidth(String s) {
/*  92 */     return getRawStringWidth(s) >> 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getStringWidth_double(String s) {
/*  97 */     return getRawStringWidth(s) / 2.0D;
/*     */   }
/*     */   
/*     */   public int getRawStringWidth(String s) {
/* 101 */     if (s == null || s.isEmpty()) return 0;
/*     */     
/* 103 */     int ret = 0;
/*     */     
/* 105 */     for (int i = 0; i < s.length(); i++) {
/* 106 */       char c = s.charAt(i);
/*     */       
/* 108 */       if ((c == '§' || isEmojiCharacter(c)) && i < s.length() - 1) {
/* 109 */         i++;
/*     */       } else {
/* 111 */         ret += (getGlyph(c)).width;
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return ret;
/*     */   }
/*     */   
/*     */   public int getStringWidthIgnoreCode(String s) {
/* 119 */     if (s == null || s.isEmpty()) return 0;
/*     */     
/* 121 */     int ret = 0;
/*     */     
/* 123 */     for (int i = 0; i < s.length(); i++) {
/* 124 */       ret += (getGlyph(s.charAt(i))).width;
/*     */     }
/*     */     
/* 127 */     return ret >> 1;
/*     */   }
/*     */   
/*     */   public int getRawCharWidth(char c) {
/* 131 */     return (getGlyph(c)).width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCharWidth(char c) {
/* 136 */     return (getGlyph(c)).width >> 1;
/*     */   }
/*     */   
/*     */   public void drawStringWithShadowIgnoreCode(String s, double x, double y, int color) {
/* 140 */     drawStringIgnoreCode(s, x + 0.5D, y + 0.5D, color, true);
/* 141 */     drawStringIgnoreCode(s, x, y, color, false);
/*     */   }
/*     */   
/*     */   public void drawStringIgnoreCode(String s, double x, double y, int color) {
/* 145 */     drawStringIgnoreCode(s, x, y, color, false);
/*     */   }
/*     */   
/*     */   public void drawStringIgnoreCode(String s, double x, double y, int color, boolean shadow) {
/* 149 */     if (s == null || s.isEmpty())
/*     */       return; 
/* 151 */     if ((color & 0xFC000000) == 0) {
/* 152 */       color |= 0xFF000000;
/*     */     }
/*     */     
/* 155 */     if (shadow) {
/* 156 */       color = getShadowColor(color);
/*     */     }
/*     */     
/* 159 */     preDraw();
/* 160 */     GLUtils.color(color);
/*     */     
/* 162 */     for (int i = 0; i < s.length(); i++) {
/* 163 */       Glyph glyph = getGlyph(s.charAt(i));
/*     */       
/* 165 */       glyph.draw(x, y, false);
/*     */       
/* 167 */       x += glyph.width / 2.0D;
/*     */     } 
/*     */     
/* 170 */     postDraw();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredStringWithShadow(String s, double x, double y, int color) {
/* 175 */     drawStringWithShadow(s, x - getStringWidth_double(s) / 2.0D, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String s, double x, double y, int color) {
/* 180 */     drawString(s, x - getStringWidth_double(s) / 2.0D, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawStringWithOutline(String s, double x, double y, int color) {
/* 185 */     drawString(s, x + 0.5D, y, color, true);
/* 186 */     drawString(s, x - 0.5D, y, color, true);
/* 187 */     drawString(s, x, y + 0.5D, color, true);
/* 188 */     drawString(s, x, y - 0.5D, color, true);
/* 189 */     drawString(s, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawStringWithShadow(String s, double x, double y, int color) {
/* 194 */     drawString(s, x + 0.5D, y + 0.5D, color, true);
/* 195 */     drawString(s, x, y, color, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(String s, double x, double y, int color) {
/* 200 */     drawString(s, x, y, color, false);
/*     */   }
/*     */   
/*     */   private String processString(String text) {
/* 204 */     String str = "";
/* 205 */     for (char c : text.toCharArray()) {
/* 206 */       if ((c < '썐' || c > '') && c != '⚽') str = str + c; 
/*     */     } 
/* 208 */     text = str.replace("§r", "").replace('?', '=').replace('?', '?').replace('?', '☆').replace('?', '☆').replace('?', '☆').replace("?", "☆").replace("?", "+");
/* 209 */     text = text.replace('?', '←').replace('?', '↑').replace('?', '↓').replace('?', '→').replace('?', '↗').replace('?', '↙').replace('?', '↖').replace('?', '↘');
/* 210 */     return text;
/*     */   }
/*     */   
/*     */   public void drawString(String s2, double x, double y, int color, boolean shadow) {
/* 214 */     if (s2 == null || s2.isEmpty())
/* 215 */       return;  String s = processString(s2);
/* 216 */     if ((color & 0xFC000000) == 0) {
/* 217 */       color |= 0xFF000000;
/*     */     }
/*     */     
/* 220 */     int rawColor = color;
/* 221 */     int len = s.length();
/*     */     
/* 223 */     preDraw();
/*     */     
/* 225 */     boolean randomStyle = false;
/* 226 */     boolean bold = false;
/* 227 */     boolean italic = false;
/* 228 */     boolean strikethrough = false;
/* 229 */     boolean underline = false;
/*     */     
/* 231 */     for (int i = 0; i < len; i++) {
/* 232 */       char c = s.charAt(i);
/* 233 */       boolean isEmojiCharacter = isEmojiCharacter(c);
/*     */       
/* 235 */       if ((c == '§' || isEmojiCharacter) && i < len - 1) {
/* 236 */         i++;
/*     */         
/* 238 */         if (!isEmojiCharacter) {
/* 239 */           int finalColor, colorIndex = "0123456789abcdefklmnor".indexOf(s.charAt(i));
/*     */           
/* 241 */           switch (colorIndex) {
/*     */             case 16:
/* 243 */               randomStyle = true;
/*     */               break;
/*     */             
/*     */             case 17:
/* 247 */               bold = true;
/*     */               break;
/*     */             
/*     */             case 18:
/* 251 */               strikethrough = true;
/*     */               break;
/*     */             
/*     */             case 19:
/* 255 */               underline = true;
/*     */               break;
/*     */             
/*     */             case 20:
/* 259 */               italic = true;
/*     */               break;
/*     */             
/*     */             case 21:
/* 263 */               randomStyle = false;
/* 264 */               bold = false;
/* 265 */               italic = false;
/* 266 */               underline = false;
/* 267 */               strikethrough = false;
/* 268 */               color = rawColor;
/*     */               break;
/*     */             
/*     */             default:
/* 272 */               randomStyle = false;
/* 273 */               bold = false;
/* 274 */               italic = false;
/* 275 */               underline = false;
/* 276 */               strikethrough = false;
/*     */               
/* 278 */               if (colorIndex == -1) {
/* 279 */                 colorIndex = 15;
/*     */               }
/*     */               
/* 282 */               finalColor = COLORS[colorIndex];
/* 283 */               color = ColorUtils.getRGB(ColorUtils.getRed(finalColor), ColorUtils.getGreen(finalColor), ColorUtils.getBlue(finalColor), ColorUtils.getAlpha(color));
/*     */               break;
/*     */           } 
/*     */         
/*     */         } 
/*     */       } else {
/* 289 */         char targetChar = c;
/*     */         
/* 291 */         if (randomStyle && "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c) != -1) {
/* 292 */           int charWidth = (getGlyph(c)).width;
/*     */ 
/*     */           
/*     */           do {
/* 296 */             int index = ThreadLocalRandom.current().nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".length());
/* 297 */             targetChar = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".charAt(index);
/* 298 */           } while (charWidth != (getGlyph(targetChar)).width);
/*     */         } 
/*     */         
/* 301 */         Glyph glyph = getGlyph(targetChar);
/*     */         
/* 303 */         if (Character.isWhitespace(targetChar)) {
/* 304 */           if (strikethrough) {
/* 305 */             drawStrikethrough(glyph, x, y);
/*     */           }
/*     */           
/* 308 */           if (underline) {
/* 309 */             drawUnderLine(glyph, x, y);
/*     */           }
/*     */         }
/* 312 */         else if (shadow) {
/* 313 */           drawGlyph(glyph, x, y, bold, false, false, italic, true, color);
/*     */         } else {
/* 315 */           drawGlyph(glyph, x, y, bold, strikethrough, underline, italic, false, color);
/*     */         } 
/*     */ 
/*     */         
/* 319 */         x += glyph.width / 2.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     postDraw();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCharWithShadow(char c, double x, double y, int color) {
/* 328 */     drawChar(c, x + 0.5D, y + 0.5D, getShadowColor(color));
/* 329 */     drawChar(c, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawChar(char c, double x, double y, int color) {
/* 334 */     preDraw();
/* 335 */     GLUtils.color(color);
/*     */     
/* 337 */     getGlyph(c).draw(x, y, false);
/*     */     
/* 339 */     postDraw();
/*     */   }
/*     */   
/*     */   private void drawGlyph(Glyph glyph, double x, double y, boolean bold, boolean strikethrough, boolean underline, boolean italic, boolean shadow, int color) {
/* 343 */     if (shadow) {
/* 344 */       color = getShadowColor(color);
/*     */     }
/*     */     
/* 347 */     GLUtils.color(color);
/*     */     
/* 349 */     if (bold);
/*     */ 
/*     */ 
/*     */     
/* 353 */     glyph.draw(x, y, italic);
/*     */     
/* 355 */     if (strikethrough) {
/* 356 */       drawStrikethrough(glyph, x, y);
/*     */     }
/*     */     
/* 359 */     if (underline) {
/* 360 */       drawUnderLine(glyph, x, y);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawStrikethrough(Glyph glyph, double x, double y) {
/* 365 */     double mid = y + this.height;
/*     */     
/* 367 */     drawLine(x, mid - 0.5D, x + glyph.width, mid + 0.5D);
/*     */   }
/*     */   
/*     */   private void drawUnderLine(Glyph glyph, double x, double y) {
/* 371 */     drawLine(x, y + this.fontSize, x + glyph.width, y + this.fontSize + 1.0D);
/*     */   }
/*     */   
/*     */   private Glyph getGlyph(char c) {
/* 375 */     return (Glyph)this.glyphs.computeIfAbsent(Character.valueOf(c), this::createGlyph);
/*     */   }
/*     */   
/*     */   private Glyph createGlyph(char c) {
/* 379 */     FontRecorder recorder = null;
/*     */     
/* 381 */     for (FontRecorder fr : this.fonts) {
/* 382 */       if (fr.getFont().canDisplay(c)) {
/* 383 */         recorder = fr;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 388 */     if (recorder == null) {
/* 389 */       return this.unsupportedGlyph;
/*     */     }
/*     */     
/* 392 */     String s = String.valueOf(c);
/* 393 */     BufferedImage image = new BufferedImage(this.imageSize, this.imageSize, 2);
/* 394 */     Graphics2D g = image.createGraphics();
/*     */     
/* 396 */     setRenderingHints(g, recorder.isAntiAliasing(), recorder.isFractionalMetrics());
/* 397 */     g.setFont(recorder.getFont());
/* 398 */     g.setColor(Color.WHITE);
/* 399 */     g.drawString(s, 0, this.fontSize - this.fontSize / 8);
/* 400 */     g.dispose();
/*     */     
/* 402 */     FontMetrics fontMetrics = g.getFontMetrics();
/*     */     
/* 404 */     return new Glyph(image, (fontMetrics.getStringBounds(s, g).getBounds()).width, recorder.isTextureBlurred());
/*     */   }
/*     */   
/*     */   private static void preDraw() {
/* 408 */     GlStateManager.enableBlend();
/* 409 */     GlStateManager.blendFunc(770, 771);
/* 410 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */   
/*     */   private static void postDraw() {
/* 414 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 415 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   private static void setRenderingHints(Graphics2D g, boolean antiAliasing, boolean fractionalMetrics) {
/* 419 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/* 420 */     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAliasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/* 421 */     g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*     */   }
/*     */   
/*     */   private static boolean isEmojiCharacter(char codePoint) {
/* 425 */     return (codePoint != '\000' && codePoint != '\t' && codePoint != '\n' && codePoint != '\r' && (codePoint < ' ' || codePoint > '퟿') && (codePoint < '' || codePoint > '�'));
/*     */   }
/*     */   
/*     */   private static int getShadowColor(int color) {
/* 429 */     return (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */   }
/*     */   
/*     */   private static void drawLine(double left, double top, double right, double bottom) {
/* 433 */     if (left < right) {
/* 434 */       double i = left;
/* 435 */       left = right;
/* 436 */       right = i;
/*     */     } 
/*     */     
/* 439 */     if (top < bottom) {
/* 440 */       double j = top;
/* 441 */       top = bottom;
/* 442 */       bottom = j;
/*     */     } 
/*     */     
/* 445 */     Tessellator tessellator = Tessellator.getInstance();
/* 446 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 447 */     GlStateManager.disableTexture2D();
/* 448 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 449 */     worldrenderer.pos(left, bottom, 0.0D).endVertex();
/* 450 */     worldrenderer.pos(right, bottom, 0.0D).endVertex();
/* 451 */     worldrenderer.pos(right, top, 0.0D).endVertex();
/* 452 */     worldrenderer.pos(left, top, 0.0D).endVertex();
/* 453 */     tessellator.draw();
/* 454 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 459 */     return this.height;
/*     */   }
/*     */   
/*     */   private final class Glyph {
/*     */     final int textureID;
/*     */     final int width;
/*     */     
/*     */     Glyph(BufferedImage image, int width, boolean textureBlurred) {
/* 467 */       this.textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), image, textureBlurred, true);
/* 468 */       this.width = width;
/*     */     }
/*     */     
/*     */     void draw(double x, double y, boolean italic) {
/* 472 */       GlStateManager.bindTexture(this.textureID);
/*     */       
/* 474 */       double offset = italic ? 2.0D : 0.0D;
/*     */       
/* 476 */       Tessellator tessellator = Tessellator.getInstance();
/* 477 */       WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*     */       
/* 479 */       worldRenderer.begin(5, DefaultVertexFormats.POSITION_TEX);
/* 480 */       worldRenderer.pos(x + offset, y, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 481 */       worldRenderer.pos(x - offset, y + TrueTypeFontDrawer.this.halfImageSize, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 482 */       worldRenderer.pos(x + offset + TrueTypeFontDrawer.this.halfImageSize, y, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 483 */       worldRenderer.pos(x - offset + TrueTypeFontDrawer.this.halfImageSize, y + TrueTypeFontDrawer.this.halfImageSize, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 484 */       tessellator.draw();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\font\TrueTypeFontDrawer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */