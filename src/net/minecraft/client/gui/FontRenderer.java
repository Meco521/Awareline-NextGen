/*      */ package net.minecraft.client.gui;
/*      */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*      */ import com.ibm.icu.text.ArabicShaping;
/*      */ import com.ibm.icu.text.ArabicShapingException;
/*      */ import com.ibm.icu.text.Bidi;
/*      */ import java.awt.Color;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.Arrays;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.util.FontUtils;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class FontRenderer implements IResourceManagerReloadListener {
/*   33 */   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   38 */   private final int[] charWidth = new int[256];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   43 */   public int FONT_HEIGHT = 9;
/*   44 */   public final Random fontRandom = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   49 */   private final byte[] glyphWidth = new byte[65536];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   public final int[] colorCode = new int[32];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResourceLocation locationFontTexture;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float posX;
/*      */ 
/*      */ 
/*      */   
/*      */   private float posY;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean unicodeFlag;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean bidiFlag;
/*      */ 
/*      */ 
/*      */   
/*      */   private float red;
/*      */ 
/*      */ 
/*      */   
/*      */   private float blue;
/*      */ 
/*      */ 
/*      */   
/*      */   private float green;
/*      */ 
/*      */ 
/*      */   
/*      */   private float alpha;
/*      */ 
/*      */ 
/*      */   
/*      */   private int textColor;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean randomStyle;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean boldStyle;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean italicStyle;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean underlineStyle;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean strikethroughStyle;
/*      */ 
/*      */ 
/*      */   
/*      */   public final GameSettings gameSettings;
/*      */ 
/*      */ 
/*      */   
/*      */   public final ResourceLocation locationFontTextureBase;
/*      */ 
/*      */ 
/*      */   
/*  134 */   public float offsetBold = 1.0F;
/*  135 */   private final float[] charWidthFloat = new float[256];
/*      */   private boolean blend;
/*  137 */   private final GlBlendState oldBlendState = new GlBlendState();
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
/*      */   public final boolean enabled = true;
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
/*      */   public static void drawOutlinedStringCock(FontRenderer fr, String s, float x, float y, int color, int outlineColor) {
/*  180 */     fr.drawString(ColorUtils.stripColor(s), (int)(x - 1.0F), (int)y, outlineColor);
/*  181 */     fr.drawString(ColorUtils.stripColor(s), (int)x, (int)(y - 1.0F), outlineColor);
/*  182 */     fr.drawString(ColorUtils.stripColor(s), (int)(x + 1.0F), (int)y, outlineColor);
/*  183 */     fr.drawString(ColorUtils.stripColor(s), (int)x, (int)(y + 1.0F), outlineColor);
/*  184 */     fr.drawString(s, (int)x, (int)y, color);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  189 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*      */     
/*  191 */     for (int i = 0; i < unicodePageLocations.length; i++) {
/*  192 */       unicodePageLocations[i] = null;
/*      */     }
/*      */     
/*  195 */     readFontTexture();
/*  196 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readFontTexture() {
/*      */     BufferedImage bufferedimage;
/*      */     try {
/*  203 */       bufferedimage = TextureUtil.readBufferedImage(getResourceInputStream(this.locationFontTexture));
/*  204 */     } catch (IOException ioexception1) {
/*  205 */       throw new RuntimeException(ioexception1);
/*      */     } 
/*      */     
/*  208 */     Properties properties = FontUtils.readFontProperties(this.locationFontTexture);
/*  209 */     this.blend = FontUtils.readBoolean(properties, "blend", false);
/*  210 */     int i = bufferedimage.getWidth();
/*  211 */     int j = bufferedimage.getHeight();
/*  212 */     int k = i / 16;
/*  213 */     int l = j / 16;
/*  214 */     float f = i / 128.0F;
/*  215 */     float f1 = Config.limit(f, 1.0F, 2.0F);
/*  216 */     this.offsetBold = 1.0F / f1;
/*  217 */     float f2 = FontUtils.readFloat(properties, "offsetBold", -1.0F);
/*      */     
/*  219 */     if (f2 >= 0.0F) {
/*  220 */       this.offsetBold = f2;
/*      */     }
/*      */     
/*  223 */     int[] aint = new int[i * j];
/*  224 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/*      */     
/*  226 */     for (int i1 = 0; i1 < 256; i1++) {
/*  227 */       int j1 = i1 % 16;
/*  228 */       int k1 = i1 / 16;
/*  229 */       int l1 = 0;
/*      */       
/*  231 */       for (l1 = k - 1; l1 >= 0; l1--) {
/*  232 */         int i2 = j1 * k + l1;
/*  233 */         boolean flag = true;
/*      */         
/*  235 */         for (int j2 = 0; j2 < l && flag; j2++) {
/*  236 */           int k2 = (k1 * l + j2) * i;
/*  237 */           int l2 = aint[i2 + k2];
/*  238 */           int i3 = l2 >> 24 & 0xFF;
/*      */           
/*  240 */           if (i3 > 16) {
/*  241 */             flag = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  246 */         if (!flag) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  251 */       if (i1 == 65) {
/*  252 */         i1 = i1;
/*      */       }
/*      */       
/*  255 */       if (i1 == 32) {
/*  256 */         if (k <= 8) {
/*  257 */           l1 = (int)(2.0F * f);
/*      */         } else {
/*  259 */           l1 = (int)(1.5F * f);
/*      */         } 
/*      */       }
/*      */       
/*  263 */       this.charWidthFloat[i1] = (l1 + 1) / f + 1.0F;
/*      */     } 
/*      */     
/*  266 */     FontUtils.readCustomCharWidths(properties, this.charWidthFloat);
/*      */     
/*  268 */     for (int j3 = 0; j3 < this.charWidth.length; j3++) {
/*  269 */       this.charWidth[j3] = Math.round(this.charWidthFloat[j3]);
/*      */     }
/*      */   }
/*      */   
/*      */   private void readGlyphSizes() {
/*  274 */     InputStream inputstream = null;
/*      */     
/*      */     try {
/*  277 */       inputstream = getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
/*  278 */       inputstream.read(this.glyphWidth);
/*  279 */     } catch (IOException ioexception) {
/*  280 */       throw new RuntimeException(ioexception);
/*      */     } finally {
/*  282 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderChar(char ch, boolean italic) {
/*  290 */     if (ch != ' ' && ch != ' ') {
/*  291 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(ch);
/*  292 */       return (i != -1 && !this.unicodeFlag) ? renderDefaultChar(i, italic) : renderUnicodeChar(ch, italic);
/*      */     } 
/*  294 */     return !this.unicodeFlag ? this.charWidthFloat[ch] : 4.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderDefaultChar(int ch, boolean italic) {
/*  302 */     int i = ch % 16 << 3;
/*  303 */     int j = ch / 16 << 3;
/*  304 */     int k = italic ? 1 : 0;
/*  305 */     bindTexture(this.locationFontTexture);
/*  306 */     float f = this.charWidthFloat[ch];
/*  307 */     float f1 = 7.99F;
/*  308 */     GL11.glBegin(5);
/*  309 */     GL11.glTexCoord2f(i / 128.0F, j / 128.0F);
/*  310 */     GL11.glVertex3f(this.posX + k, this.posY, 0.0F);
/*  311 */     GL11.glTexCoord2f(i / 128.0F, (j + 7.99F) / 128.0F);
/*  312 */     GL11.glVertex3f(this.posX - k, this.posY + 7.99F, 0.0F);
/*  313 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, j / 128.0F);
/*  314 */     GL11.glVertex3f(this.posX + f1 - 1.0F + k, this.posY, 0.0F);
/*  315 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, (j + 7.99F) / 128.0F);
/*  316 */     GL11.glVertex3f(this.posX + f1 - 1.0F - k, this.posY + 7.99F, 0.0F);
/*  317 */     GL11.glEnd();
/*  318 */     return f;
/*      */   }
/*      */   
/*      */   private ResourceLocation getUnicodePageLocation(int page) {
/*  322 */     if (unicodePageLocations[page] == null) {
/*  323 */       unicodePageLocations[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(page) }));
/*  324 */       unicodePageLocations[page] = FontUtils.getHdFontLocation(unicodePageLocations[page]);
/*      */     } 
/*      */     
/*  327 */     return unicodePageLocations[page];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadGlyphTexture(int page) {
/*  334 */     bindTexture(getUnicodePageLocation(page));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderUnicodeChar(char ch, boolean italic) {
/*  341 */     if (this.glyphWidth[ch] == 0) {
/*  342 */       return 0.0F;
/*      */     }
/*  344 */     int i = ch / 256;
/*  345 */     loadGlyphTexture(i);
/*  346 */     int j = this.glyphWidth[ch] >>> 4;
/*  347 */     int k = this.glyphWidth[ch] & 0xF;
/*  348 */     float f = j;
/*  349 */     float f1 = (k + 1);
/*  350 */     float f2 = (ch % 16 << 4) + f;
/*  351 */     float f3 = ((ch & 0xFF) / 16 << 4);
/*  352 */     float f4 = f1 - f - 0.02F;
/*  353 */     float f5 = italic ? 1.0F : 0.0F;
/*  354 */     GL11.glBegin(5);
/*  355 */     GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
/*  356 */     GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
/*  357 */     GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
/*  358 */     GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
/*  359 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
/*  360 */     GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
/*  361 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
/*  362 */     GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
/*  363 */     GL11.glEnd();
/*  364 */     return (f1 - f) / 2.0F + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawStringWithShadow(String text, float x, float y, int color) {
/*  372 */     return drawString(text, x, y, color, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, int x, int y, int color) {
/*  379 */     return drawString(text, x, y, color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, float x, float y, int color) {
/*  386 */     return drawString(text, x, y, color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, float x, float y, int color, boolean dropShadow) {
/*      */     int i;
/*  393 */     enableAlpha();
/*      */     
/*  395 */     if (this.blend) {
/*  396 */       GlStateManager.getBlendState(this.oldBlendState);
/*  397 */       GlStateManager.enableBlend();
/*  398 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */     
/*  401 */     resetStyles();
/*      */ 
/*      */     
/*  404 */     if (dropShadow) {
/*  405 */       i = renderString(text, x + 1.0F, y + 1.0F, color, true);
/*  406 */       i = Math.max(i, renderString(text, x, y, color, false));
/*      */     } else {
/*  408 */       i = renderString(text, x, y, color, false);
/*      */     } 
/*      */     
/*  411 */     if (this.blend) {
/*  412 */       GlStateManager.setBlendState(this.oldBlendState);
/*      */     }
/*      */     
/*  415 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String bidiReorder(String text) {
/*      */     try {
/*  423 */       Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
/*  424 */       bidi.setReorderingMode(0);
/*  425 */       return bidi.writeReordered(2);
/*  426 */     } catch (ArabicShapingException var3) {
/*  427 */       return text;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetStyles() {
/*  435 */     this.randomStyle = false;
/*  436 */     this.boldStyle = false;
/*  437 */     this.italicStyle = false;
/*  438 */     this.underlineStyle = false;
/*  439 */     this.strikethroughStyle = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderStringAtPos(String text, boolean shadow) {
/*  446 */     for (int i = 0; i < text.length(); i++) {
/*  447 */       char c0 = text.charAt(i);
/*      */       
/*  449 */       if (c0 == '§' && i + 1 < text.length()) {
/*  450 */         int l = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
/*      */         
/*  452 */         if (l < 16) {
/*  453 */           this.randomStyle = false;
/*  454 */           this.boldStyle = false;
/*  455 */           this.strikethroughStyle = false;
/*  456 */           this.underlineStyle = false;
/*  457 */           this.italicStyle = false;
/*      */           
/*  459 */           if (l < 0 || l > 15) {
/*  460 */             l = 15;
/*      */           }
/*      */           
/*  463 */           if (shadow) {
/*  464 */             l += 16;
/*      */           }
/*      */           
/*  467 */           int i1 = this.colorCode[l];
/*      */           
/*  469 */           if (Config.isCustomColors()) {
/*  470 */             i1 = CustomColors.getTextColor(l, i1);
/*      */           }
/*      */           
/*  473 */           this.textColor = i1;
/*  474 */           setColor((i1 >> 16) / 255.0F, (i1 >> 8 & 0xFF) / 255.0F, (i1 & 0xFF) / 255.0F, this.alpha);
/*  475 */         } else if (l == 16) {
/*  476 */           this.randomStyle = true;
/*  477 */         } else if (l == 17) {
/*  478 */           this.boldStyle = true;
/*  479 */         } else if (l == 18) {
/*  480 */           this.strikethroughStyle = true;
/*  481 */         } else if (l == 19) {
/*  482 */           this.underlineStyle = true;
/*  483 */         } else if (l == 20) {
/*  484 */           this.italicStyle = true;
/*  485 */         } else if (l == 21) {
/*  486 */           this.randomStyle = false;
/*  487 */           this.boldStyle = false;
/*  488 */           this.strikethroughStyle = false;
/*  489 */           this.underlineStyle = false;
/*  490 */           this.italicStyle = false;
/*  491 */           setColor(this.red, this.blue, this.green, this.alpha);
/*      */         } 
/*      */         
/*  494 */         i++;
/*      */       } else {
/*  496 */         int j = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c0);
/*      */         
/*  498 */         if (this.randomStyle && j != -1) {
/*  499 */           char c1; int k = getCharWidth(c0);
/*      */ 
/*      */           
/*      */           do {
/*  503 */             j = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".length());
/*  504 */             c1 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".charAt(j);
/*      */           }
/*  506 */           while (k != getCharWidth(c1));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  511 */           c0 = c1;
/*      */         } 
/*      */         
/*  514 */         float f1 = (j != -1 && !this.unicodeFlag) ? this.offsetBold : 0.5F;
/*  515 */         boolean flag = ((c0 == '\000' || j == -1 || this.unicodeFlag) && shadow);
/*      */         
/*  517 */         if (flag) {
/*  518 */           this.posX -= f1;
/*  519 */           this.posY -= f1;
/*      */         } 
/*      */         
/*  522 */         float f = renderChar(c0, this.italicStyle);
/*      */         
/*  524 */         if (flag) {
/*  525 */           this.posX += f1;
/*  526 */           this.posY += f1;
/*      */         } 
/*      */         
/*  529 */         if (this.boldStyle) {
/*  530 */           this.posX += f1;
/*      */           
/*  532 */           if (flag) {
/*  533 */             this.posX -= f1;
/*  534 */             this.posY -= f1;
/*      */           } 
/*      */           
/*  537 */           renderChar(c0, this.italicStyle);
/*  538 */           this.posX -= f1;
/*      */           
/*  540 */           if (flag) {
/*  541 */             this.posX += f1;
/*  542 */             this.posY += f1;
/*      */           } 
/*      */           
/*  545 */           f += f1;
/*      */         } 
/*      */         
/*  548 */         doDraw(f);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void doDraw(float p_doDraw_1_) {
/*  554 */     if (this.strikethroughStyle) {
/*  555 */       Tessellator tessellator = Tessellator.getInstance();
/*  556 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  557 */       GlStateManager.disableTexture2D();
/*  558 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  559 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  560 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  561 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  562 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  563 */       tessellator.draw();
/*  564 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  567 */     if (this.underlineStyle) {
/*  568 */       Tessellator tessellator1 = Tessellator.getInstance();
/*  569 */       WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
/*  570 */       GlStateManager.disableTexture2D();
/*  571 */       worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
/*  572 */       int i = this.underlineStyle ? -1 : 0;
/*  573 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  574 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  575 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  576 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  577 */       tessellator1.draw();
/*  578 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  581 */     this.posX += p_doDraw_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderStringAligned(String text, int x, int y, int width, int color, boolean dropShadow) {
/*  588 */     if (this.bidiFlag) {
/*  589 */       int i = getStringWidth(bidiReorder(text));
/*  590 */       x = x + width - i;
/*      */     } 
/*      */     
/*  593 */     return renderString(text, x, y, color, dropShadow);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderString(String text, float x, float y, int color, boolean dropShadow) {
/*  600 */     if (text == null) {
/*  601 */       return 0;
/*      */     }
/*  603 */     if (this.bidiFlag) {
/*  604 */       text = bidiReorder(text);
/*      */     }
/*      */     
/*  607 */     if ((color & 0xFC000000) == 0) {
/*  608 */       color |= 0xFF000000;
/*      */     }
/*      */     
/*  611 */     if (dropShadow) {
/*  612 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*      */     }
/*      */     
/*  615 */     this.red = (color >> 16 & 0xFF) / 255.0F;
/*  616 */     this.blue = (color >> 8 & 0xFF) / 255.0F;
/*  617 */     this.green = (color & 0xFF) / 255.0F;
/*  618 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/*  619 */     setColor(this.red, this.blue, this.green, this.alpha);
/*  620 */     this.posX = x;
/*  621 */     this.posY = y;
/*      */     
/*  623 */     renderStringAtPos(text, dropShadow);
/*  624 */     return (int)this.posX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringWidth(String text) {
/*  632 */     if (text == null) {
/*  633 */       return 0;
/*      */     }
/*  635 */     float f = 0.0F;
/*  636 */     boolean flag = false;
/*      */     
/*  638 */     for (int i = 0; i < text.length(); i++) {
/*  639 */       char c0 = text.charAt(i);
/*  640 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  642 */       if (f1 < 0.0F && i < text.length() - 1) {
/*  643 */         i++;
/*  644 */         c0 = text.charAt(i);
/*      */         
/*  646 */         if (c0 != 'l' && c0 != 'L') {
/*  647 */           if (c0 == 'r' || c0 == 'R') {
/*  648 */             flag = false;
/*      */           }
/*      */         } else {
/*  651 */           flag = true;
/*      */         } 
/*      */         
/*  654 */         f1 = 0.0F;
/*      */       } 
/*      */       
/*  657 */       f += f1;
/*      */       
/*  659 */       if (flag && f1 > 0.0F) {
/*  660 */         f += this.unicodeFlag ? 1.0F : this.offsetBold;
/*      */       }
/*      */     } 
/*      */     
/*  664 */     return Math.round(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharWidth(char character) {
/*  672 */     return Math.round(getCharWidthFloat(character));
/*      */   }
/*      */   
/*      */   private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
/*  676 */     if (p_getCharWidthFloat_1_ == '§')
/*  677 */       return -1.0F; 
/*  678 */     if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != ' ') {
/*  679 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_getCharWidthFloat_1_);
/*      */       
/*  681 */       if (p_getCharWidthFloat_1_ > '\000' && i != -1 && !this.unicodeFlag)
/*  682 */         return this.charWidthFloat[i]; 
/*  683 */       if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
/*  684 */         int j = this.glyphWidth[p_getCharWidthFloat_1_] >>> 4;
/*  685 */         int k = this.glyphWidth[p_getCharWidthFloat_1_] & 0xF;
/*      */         
/*  687 */         if (k > 7) {
/*  688 */           k = 15;
/*  689 */           j = 0;
/*      */         } 
/*      */         
/*  692 */         k++;
/*  693 */         return ((k - j) / 2 + 1);
/*      */       } 
/*  695 */       return 0.0F;
/*      */     } 
/*      */     
/*  698 */     return this.charWidthFloat[32];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width) {
/*  706 */     return trimStringToWidth(text, width, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width, boolean reverse) {
/*  713 */     StringBuilder stringbuilder = new StringBuilder();
/*  714 */     float f = 0.0F;
/*  715 */     int i = reverse ? (text.length() - 1) : 0;
/*  716 */     int j = reverse ? -1 : 1;
/*  717 */     boolean flag = false;
/*  718 */     boolean flag1 = false;
/*      */     int k;
/*  720 */     for (k = i; k >= 0 && k < text.length() && f < width; k += j) {
/*  721 */       char c0 = text.charAt(k);
/*  722 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  724 */       if (flag) {
/*  725 */         flag = false;
/*      */         
/*  727 */         if (c0 != 'l' && c0 != 'L') {
/*  728 */           if (c0 == 'r' || c0 == 'R') {
/*  729 */             flag1 = false;
/*      */           }
/*      */         } else {
/*  732 */           flag1 = true;
/*      */         } 
/*  734 */       } else if (f1 < 0.0F) {
/*  735 */         flag = true;
/*      */       } else {
/*  737 */         f += f1;
/*      */         
/*  739 */         if (flag1) {
/*  740 */           f++;
/*      */         }
/*      */       } 
/*      */       
/*  744 */       if (f > width) {
/*      */         break;
/*      */       }
/*      */       
/*  748 */       if (reverse) {
/*  749 */         stringbuilder.insert(0, c0);
/*      */       } else {
/*  751 */         stringbuilder.append(c0);
/*      */       } 
/*      */     } 
/*      */     
/*  755 */     return stringbuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String trimStringNewline(String text) {
/*  762 */     while (text != null && !text.isEmpty() && text.charAt(text.length() - 1) == '\n') {
/*  763 */       text = text.substring(0, text.length() - 1);
/*      */     }
/*      */     
/*  766 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
/*  773 */     if (this.blend) {
/*  774 */       GlStateManager.getBlendState(this.oldBlendState);
/*  775 */       GlStateManager.enableBlend();
/*  776 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */     
/*  779 */     resetStyles();
/*  780 */     this.textColor = textColor;
/*  781 */     str = trimStringNewline(str);
/*  782 */     renderSplitString(str, x, y, wrapWidth, false);
/*      */     
/*  784 */     if (this.blend) {
/*  785 */       GlStateManager.setBlendState(this.oldBlendState);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
/*  794 */     for (String s : listFormattedStringToWidth(str, wrapWidth)) {
/*  795 */       renderStringAligned(s, x, y, wrapWidth, this.textColor, addShadow);
/*  796 */       y += this.FONT_HEIGHT;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int splitStringWidth(String str, int maxLength) {
/*  807 */     return this.FONT_HEIGHT * listFormattedStringToWidth(str, maxLength).size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnicodeFlag(boolean unicodeFlagIn) {
/*  815 */     this.unicodeFlag = unicodeFlagIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUnicodeFlag() {
/*  823 */     return this.unicodeFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBidiFlag(boolean bidiFlagIn) {
/*  830 */     this.bidiFlag = bidiFlagIn;
/*      */   }
/*      */   
/*      */   public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
/*  834 */     return Arrays.asList(wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String wrapFormattedStringToWidth(String str, int wrapWidth) {
/*  841 */     if (str.length() <= 1) {
/*  842 */       return str;
/*      */     }
/*  844 */     int i = sizeStringToWidth(str, wrapWidth);
/*      */     
/*  846 */     if (str.length() <= i) {
/*  847 */       return str;
/*      */     }
/*  849 */     String s = str.substring(0, i);
/*  850 */     char c0 = str.charAt(i);
/*  851 */     boolean flag = (c0 == ' ' || c0 == '\n');
/*  852 */     String s1 = getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
/*  853 */     return s + "\n" + wrapFormattedStringToWidth(s1, wrapWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sizeStringToWidth(String str, int wrapWidth) {
/*  862 */     int i = str.length();
/*  863 */     float f = 0.0F;
/*  864 */     int j = 0;
/*  865 */     int k = -1;
/*      */     
/*  867 */     for (boolean flag = false; j < i; j++) {
/*  868 */       char c0 = str.charAt(j);
/*      */       
/*  870 */       switch (c0) {
/*      */         case '\n':
/*  872 */           j--;
/*      */           break;
/*      */         
/*      */         case ' ':
/*  876 */           k = j;
/*      */         
/*      */         default:
/*  879 */           f += getCharWidth(c0);
/*      */           
/*  881 */           if (flag) {
/*  882 */             f++;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case '§':
/*  888 */           if (j < i - 1) {
/*  889 */             j++;
/*  890 */             char c1 = str.charAt(j);
/*      */             
/*  892 */             if (c1 != 'l' && c1 != 'L') {
/*  893 */               if (c1 == 'r' || c1 == 'R' || isFormatColor(c1))
/*  894 */                 flag = false; 
/*      */               break;
/*      */             } 
/*  897 */             flag = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */       
/*  902 */       if (c0 == '\n') {
/*      */         
/*  904 */         k = ++j;
/*      */         
/*      */         break;
/*      */       } 
/*  908 */       if (Math.round(f) > wrapWidth) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/*  913 */     return (j != i && k != -1 && k < j) ? k : j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFormatColor(char colorChar) {
/*  920 */     return ((colorChar >= '0' && colorChar <= '9') || (colorChar >= 'a' && colorChar <= 'f') || (colorChar >= 'A' && colorChar <= 'F'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatSpecial(char formatChar) {
/*  927 */     return ((formatChar >= 'k' && formatChar <= 'o') || (formatChar >= 'K' && formatChar <= 'O') || formatChar == 'r' || formatChar == 'R');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFormatFromString(String text) {
/*  934 */     StringBuilder s = new StringBuilder();
/*  935 */     int i = -1;
/*  936 */     int j = text.length();
/*      */     
/*  938 */     while ((i = text.indexOf('§', i + 1)) != -1) {
/*  939 */       if (i < j - 1) {
/*  940 */         char c0 = text.charAt(i + 1);
/*      */         
/*  942 */         if (isFormatColor(c0)) {
/*  943 */           s = new StringBuilder("§" + c0); continue;
/*  944 */         }  if (isFormatSpecial(c0)) {
/*  945 */           s.append("§").append(c0);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  950 */     return s.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBidiFlag() {
/*  957 */     return this.bidiFlag;
/*      */   }
/*      */   
/*      */   public int getColorCode(char character) {
/*  961 */     int i = "0123456789abcdef".indexOf(character);
/*      */     
/*  963 */     if (i >= 0 && i < this.colorCode.length) {
/*  964 */       int j = this.colorCode[i];
/*      */       
/*  966 */       if (Config.isCustomColors()) {
/*  967 */         j = CustomColors.getTextColor(i, j);
/*      */       }
/*      */       
/*  970 */       return j;
/*      */     } 
/*  972 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setColor(float p_setColor_1_, float p_setColor_2_, float p_setColor_3_, float p_setColor_4_) {
/*  977 */     GlStateManager.color(p_setColor_1_, p_setColor_2_, p_setColor_3_, p_setColor_4_);
/*      */   }
/*      */   
/*      */   protected void enableAlpha() {
/*  981 */     GlStateManager.enableAlpha();
/*      */   }
/*      */   
/*      */   protected void bindTexture(ResourceLocation p_bindTexture_1_) {
/*  985 */     this.renderEngine.bindTexture(p_bindTexture_1_);
/*      */   }
/*      */   
/*      */   protected InputStream getResourceInputStream(ResourceLocation p_getResourceInputStream_1_) throws IOException {
/*  989 */     return Minecraft.getMinecraft().getResourceManager().getResource(p_getResourceInputStream_1_).getInputStream();
/*      */   }
/*      */   
/*      */   public void drawCenteredString(String s, float f, float g, int color) {
/*  993 */     drawString(s, f - (getStringWidth(s) / 2), (float)(g + 1.5D), color, false);
/*      */   }
/*      */   
/*      */   public int getHeight() {
/*  997 */     return this.FONT_HEIGHT;
/*      */   }
/*      */   
/*      */   private int renderStringNoColor(String text, float x, float y, int color, boolean dropShadow) {
/* 1001 */     if (text == null) {
/* 1002 */       return 0;
/*      */     }
/* 1004 */     if (this.bidiFlag) {
/* 1005 */       text = bidiReorder(text);
/*      */     }
/*      */     
/* 1008 */     if ((color & 0xFC000000) == 0) {
/* 1009 */       color |= 0xFF000000;
/*      */     }
/*      */     
/* 1012 */     if (dropShadow) {
/* 1013 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*      */     }
/* 1015 */     this.red = (color >> 16 & 0xFF) / 255.0F;
/* 1016 */     this.blue = (color >> 8 & 0xFF) / 255.0F;
/* 1017 */     this.green = (color & 0xFF) / 255.0F;
/* 1018 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/* 1019 */     setColor(this.red, this.blue, this.green, this.alpha);
/* 1020 */     this.posX = x;
/* 1021 */     this.posY = y;
/* 1022 */     renderStringAtPos(text, dropShadow);
/* 1023 */     return (int)this.posX;
/*      */   }
/*      */   
/*      */   public int drawStringNoColor(String text, float x, float y, int color, boolean dropShadow) {
/*      */     int i;
/* 1028 */     enableAlpha();
/* 1029 */     resetStyles();
/*      */ 
/*      */     
/* 1032 */     if (dropShadow) {
/* 1033 */       i = renderStringNoColor(text, x + 1.0F, y + 1.0F, color, true);
/* 1034 */       i = Math.max(i, renderStringNoColor(text, x, y, color, false));
/*      */     } else {
/* 1036 */       i = renderStringNoColor(text, x, y, color, false);
/*      */     } 
/* 1038 */     return i;
/*      */   }
/*      */   
/* 1041 */   public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) { this.enabled = true; this.gameSettings = gameSettingsIn; this.locationFontTextureBase = location; this.locationFontTexture = location; this.renderEngine = textureManagerIn; this.unicodeFlag = unicode; this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase); bindTexture(this.locationFontTexture); for (int i = 0; i < 32; i++) { int j = (i >> 3 & 0x1) * 85; int k = (i >> 2 & 0x1) * 170 + j; int l = (i >> 1 & 0x1) * 170 + j; int i1 = (i & 0x1) * 170 + j; if (i == 6)
/*      */         k += 85;  if (gameSettingsIn.anaglyph) { int j1 = (k * 30 + l * 59 + i1 * 11) / 100; int k1 = (k * 30 + l * 70) / 100; int l1 = (k * 30 + i1 * 70) / 100; k = j1; l = k1; i1 = l1; }  if (i >= 16) { k /= 4; l /= 4; i1 /= 4; }
/*      */        this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF; }
/* 1044 */      readGlyphSizes(); } public int drawStringNoColor(String text, int x, int y, int color) { getClass(); return drawStringNoColor(text, x, y, color, false); }
/*      */ 
/*      */   
/*      */   public void drawCenteredStringWithShadowX2(String s, float f, float g, int color) {
/* 1048 */     drawStringWithShadowX2(s, f - (getStringWidth(s) / 2), (float)(g + 1.5D), color);
/*      */   }
/*      */   
/*      */   public int drawStringWithShadowX2(String text, float x, float y, int color) {
/* 1052 */     int c = (new Color(63, 63, 63)).getRGB();
/* 1053 */     if (text.contains("锟斤拷")) {
/* 1054 */       return drawStringWithShadow(text, x, y, color);
/*      */     }
/* 1056 */     drawStringNoColor(text, x, y + 1.0F, c, false);
/* 1057 */     drawStringNoColor(text, x, y - 1.0F, c, false);
/* 1058 */     drawStringNoColor(text, x - 1.0F, y, c, false);
/* 1059 */     drawStringNoColor(text, x + 1.0F, y, c, false);
/*      */ 
/*      */     
/* 1062 */     return drawString(text, x, y, color, false);
/*      */   }
/*      */   
/*      */   public void drawCenteredStringWithShadow(String s, float f, float g, int color) {
/* 1066 */     drawStringWithShadow(s, f - (getStringWidth(s) / 2), (float)(g + 1.5D), color);
/*      */   }
/*      */   
/*      */   public float getStringWidth_float(String text) {
/* 1070 */     if (text == null)
/*      */     {
/* 1072 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/* 1076 */     float f = 0.0F;
/* 1077 */     boolean flag = false;
/*      */     
/* 1079 */     for (int i = 0; i < text.length(); i++) {
/*      */       
/* 1081 */       char c0 = text.charAt(i);
/* 1082 */       float f1 = getCharWidthFloat(c0);
/*      */       
/* 1084 */       if (f1 < 0.0F && i < text.length() - 1) {
/*      */         
/* 1086 */         i++;
/* 1087 */         c0 = text.charAt(i);
/*      */         
/* 1089 */         if (c0 != 'l' && c0 != 'L') {
/*      */           
/* 1091 */           if (c0 == 'r' || c0 == 'R')
/*      */           {
/* 1093 */             flag = false;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1098 */           flag = true;
/*      */         } 
/*      */         
/* 1101 */         f1 = 0.0F;
/*      */       } 
/*      */       
/* 1104 */       f += f1;
/*      */       
/* 1106 */       if (flag && f1 > 0.0F)
/*      */       {
/* 1108 */         f += this.unicodeFlag ? 1.0F : this.offsetBold;
/*      */       }
/*      */     } 
/*      */     
/* 1112 */     return f;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\FontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */