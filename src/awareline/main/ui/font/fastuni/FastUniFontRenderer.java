/*      */ package awareline.main.ui.font.fastuni;
/*      */ import java.awt.Font;
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
/*      */ public class FastUniFontRenderer implements IResourceManagerReloadListener, IBFFontRenderer {
/*   29 */   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   34 */   private final int[] charWidth = new int[256];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   39 */   public int FONT_HEIGHT = 8;
/*   40 */   public Random fontRandom = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   45 */   private byte[] glyphWidth = new byte[65536];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   51 */   public int[] colorCode = new int[32];
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
/*      */   public GameSettings gameSettings;
/*      */ 
/*      */ 
/*      */   
/*      */   public ResourceLocation locationFontTextureBase;
/*      */ 
/*      */ 
/*      */   
/*  130 */   public float offsetBold = 1.0F;
/*  131 */   private float[] charWidthFloat = new float[256];
/*      */   private boolean blend = false;
/*  133 */   private GlBlendState oldBlendState = new GlBlendState();
/*      */   
/*      */   private StringCache stringCache;
/*      */   
/*      */   public FastUniFontRenderer(Font font, int size, boolean antiAlias) {
/*  138 */     ResourceLocation res = new ResourceLocation("textures/font/ascii.png");
/*  139 */     this.gameSettings = (Minecraft.getMinecraft()).gameSettings;
/*  140 */     this.locationFontTextureBase = res;
/*  141 */     this.locationFontTexture = res;
/*  142 */     this.renderEngine = (Minecraft.getMinecraft()).renderEngine;
/*  143 */     this.unicodeFlag = false;
/*  144 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*  145 */     for (int i = 0; i < 32; i++) {
/*  146 */       int j = (i >> 3 & 0x1) * 85;
/*  147 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  148 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  149 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  151 */       if (i == 6) {
/*  152 */         k += 85;
/*      */       }
/*      */       
/*  155 */       if (this.gameSettings.anaglyph) {
/*  156 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  157 */         int k1 = (k * 30 + l * 70) / 100;
/*  158 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  159 */         k = j1;
/*  160 */         l = k1;
/*  161 */         i1 = l1;
/*      */       } 
/*      */       
/*  164 */       if (i >= 16) {
/*  165 */         k /= 4;
/*  166 */         l /= 4;
/*  167 */         i1 /= 4;
/*      */       } 
/*      */       
/*  170 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/*  174 */     if (res.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && getStringCache() == null) {
/*  175 */       setStringCache(new StringCache(this.colorCode));
/*  176 */       getStringCache().setDefaultFont(font, size, antiAlias);
/*      */     } 
/*  178 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */   
/*      */   public FastUniFontRenderer(int size, boolean antiAlias) {
/*  183 */     ResourceLocation res = new ResourceLocation("textures/font/ascii.png");
/*  184 */     this.gameSettings = (Minecraft.getMinecraft()).gameSettings;
/*  185 */     this.locationFontTextureBase = res;
/*  186 */     this.locationFontTexture = res;
/*  187 */     this.renderEngine = (Minecraft.getMinecraft()).renderEngine;
/*  188 */     this.unicodeFlag = false;
/*  189 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*  190 */     for (int i = 0; i < 32; i++) {
/*  191 */       int j = (i >> 3 & 0x1) * 85;
/*  192 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  193 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  194 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  196 */       if (i == 6) {
/*  197 */         k += 85;
/*      */       }
/*      */       
/*  200 */       if (this.gameSettings.anaglyph) {
/*  201 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  202 */         int k1 = (k * 30 + l * 70) / 100;
/*  203 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  204 */         k = j1;
/*  205 */         l = k1;
/*  206 */         i1 = l1;
/*      */       } 
/*      */       
/*  209 */       if (i >= 16) {
/*  210 */         k /= 4;
/*  211 */         l /= 4;
/*  212 */         i1 /= 4;
/*      */       } 
/*      */       
/*  215 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/*  219 */     if (res.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && getStringCache() == null) {
/*  220 */       setStringCache(new StringCache(this.colorCode));
/*  221 */       getStringCache().setDefaultFont("Lucida Sans Regular", size, antiAlias);
/*      */     } 
/*  223 */     readGlyphSizes();
/*      */   }
/*      */   
/*      */   public FastUniFontRenderer(String name, int size, boolean antiAlias) {
/*  227 */     ResourceLocation res = new ResourceLocation("textures/font/ascii.png");
/*  228 */     this.gameSettings = (Minecraft.getMinecraft()).gameSettings;
/*  229 */     this.locationFontTextureBase = res;
/*  230 */     this.locationFontTexture = res;
/*  231 */     this.renderEngine = (Minecraft.getMinecraft()).renderEngine;
/*  232 */     this.unicodeFlag = false;
/*  233 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*  234 */     for (int i = 0; i < 32; i++) {
/*  235 */       int j = (i >> 3 & 0x1) * 85;
/*  236 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  237 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  238 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  240 */       if (i == 6) {
/*  241 */         k += 85;
/*      */       }
/*      */       
/*  244 */       if (this.gameSettings.anaglyph) {
/*  245 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  246 */         int k1 = (k * 30 + l * 70) / 100;
/*  247 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  248 */         k = j1;
/*  249 */         l = k1;
/*  250 */         i1 = l1;
/*      */       } 
/*      */       
/*  253 */       if (i >= 16) {
/*  254 */         k /= 4;
/*  255 */         l /= 4;
/*  256 */         i1 /= 4;
/*      */       } 
/*      */       
/*  259 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/*  263 */     if (res.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && getStringCache() == null) {
/*  264 */       setStringCache(new StringCache(this.colorCode));
/*  265 */       getStringCache().setDefaultFont(name, size, antiAlias);
/*      */     } 
/*  267 */     readGlyphSizes();
/*      */   }
/*      */   
/*      */   public FastUniFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode, String name, int size, boolean antiAlias) {
/*  271 */     this.gameSettings = gameSettingsIn;
/*  272 */     this.locationFontTextureBase = location;
/*  273 */     this.locationFontTexture = location;
/*  274 */     this.renderEngine = textureManagerIn;
/*  275 */     this.unicodeFlag = unicode;
/*  276 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*  277 */     bindTexture(this.locationFontTexture);
/*      */     
/*  279 */     for (int i = 0; i < 32; i++) {
/*  280 */       int j = (i >> 3 & 0x1) * 85;
/*  281 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  282 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  283 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  285 */       if (i == 6) {
/*  286 */         k += 85;
/*      */       }
/*      */       
/*  289 */       if (gameSettingsIn.anaglyph) {
/*  290 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  291 */         int k1 = (k * 30 + l * 70) / 100;
/*  292 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  293 */         k = j1;
/*  294 */         l = k1;
/*  295 */         i1 = l1;
/*      */       } 
/*      */       
/*  298 */       if (i >= 16) {
/*  299 */         k /= 4;
/*  300 */         l /= 4;
/*  301 */         i1 /= 4;
/*      */       } 
/*      */       
/*  304 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/*  308 */     if (location.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && getStringCache() == null) {
/*  309 */       setStringCache(new StringCache(this.colorCode));
/*  310 */       getStringCache().setDefaultFont(name, size, antiAlias);
/*      */     } 
/*  312 */     readGlyphSizes();
/*      */   }
/*      */   
/*      */   public FastUniFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
/*  316 */     this.gameSettings = gameSettingsIn;
/*  317 */     this.locationFontTextureBase = location;
/*  318 */     this.locationFontTexture = location;
/*  319 */     this.renderEngine = textureManagerIn;
/*  320 */     this.unicodeFlag = unicode;
/*  321 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*  322 */     bindTexture(this.locationFontTexture);
/*      */     
/*  324 */     for (int i = 0; i < 32; i++) {
/*  325 */       int j = (i >> 3 & 0x1) * 85;
/*  326 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  327 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  328 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  330 */       if (i == 6) {
/*  331 */         k += 85;
/*      */       }
/*      */       
/*  334 */       if (gameSettingsIn.anaglyph) {
/*  335 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  336 */         int k1 = (k * 30 + l * 70) / 100;
/*  337 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  338 */         k = j1;
/*  339 */         l = k1;
/*  340 */         i1 = l1;
/*      */       } 
/*      */       
/*  343 */       if (i >= 16) {
/*  344 */         k /= 4;
/*  345 */         l /= 4;
/*  346 */         i1 /= 4;
/*      */       } 
/*      */       
/*  349 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/*  353 */     if (location.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && getStringCache() == null) {
/*  354 */       setStringCache(new StringCache(this.colorCode));
/*  355 */       getStringCache().setDefaultFont("Lucida Sans Regular", 18, false);
/*      */     } 
/*  357 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  362 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*      */     
/*  364 */     Arrays.fill((Object[])unicodePageLocations, (Object)null);
/*      */     
/*  366 */     readFontTexture();
/*  367 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readFontTexture() {
/*      */     BufferedImage bufferedimage;
/*      */     try {
/*  374 */       bufferedimage = TextureUtil.readBufferedImage(getResourceInputStream(this.locationFontTexture));
/*  375 */     } catch (IOException ioexception1) {
/*  376 */       throw new RuntimeException(ioexception1);
/*      */     } 
/*      */     
/*  379 */     Properties properties = FontUtils.readFontProperties(this.locationFontTexture);
/*  380 */     this.blend = FontUtils.readBoolean(properties, "blend", false);
/*  381 */     int i = bufferedimage.getWidth();
/*  382 */     int j = bufferedimage.getHeight();
/*  383 */     int k = i / 16;
/*  384 */     int l = j / 16;
/*  385 */     float f = i / 128.0F;
/*  386 */     float f1 = Config.limit(f, 1.0F, 2.0F);
/*  387 */     this.offsetBold = 1.0F / f1;
/*  388 */     float f2 = FontUtils.readFloat(properties, "offsetBold", -1.0F);
/*      */     
/*  390 */     if (f2 >= 0.0F) {
/*  391 */       this.offsetBold = f2;
/*      */     }
/*      */     
/*  394 */     int[] aint = new int[i * j];
/*  395 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/*      */     
/*  397 */     for (int i1 = 0; i1 < 256; i1++) {
/*  398 */       int j1 = i1 % 16;
/*  399 */       int k1 = i1 / 16;
/*  400 */       int l1 = 0;
/*      */       
/*  402 */       for (l1 = k - 1; l1 >= 0; l1--) {
/*  403 */         int i2 = j1 * k + l1;
/*  404 */         boolean flag = true;
/*      */         
/*  406 */         for (int j2 = 0; j2 < l && flag; j2++) {
/*  407 */           int k2 = (k1 * l + j2) * i;
/*  408 */           int l2 = aint[i2 + k2];
/*  409 */           int i3 = l2 >> 24 & 0xFF;
/*      */           
/*  411 */           if (i3 > 16) {
/*  412 */             flag = false;
/*      */           }
/*      */         } 
/*      */         
/*  416 */         if (!flag) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  421 */       if (i1 == 65) {
/*  422 */         i1 = i1;
/*      */       }
/*      */       
/*  425 */       if (i1 == 32) {
/*  426 */         if (k <= 8) {
/*  427 */           l1 = (int)(2.0F * f);
/*      */         } else {
/*  429 */           l1 = (int)(1.5F * f);
/*      */         } 
/*      */       }
/*      */       
/*  433 */       this.charWidthFloat[i1] = (l1 + 1) / f + 1.0F;
/*      */     } 
/*      */     
/*  436 */     FontUtils.readCustomCharWidths(properties, this.charWidthFloat);
/*      */     
/*  438 */     for (int j3 = 0; j3 < this.charWidth.length; j3++) {
/*  439 */       this.charWidth[j3] = Math.round(this.charWidthFloat[j3]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readGlyphSizes() {
/*  446 */     InputStream inputstream = null;
/*      */     
/*      */     try {
/*  449 */       inputstream = getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
/*  450 */       inputstream.read(this.glyphWidth);
/*  451 */     } catch (IOException ioexception) {
/*  452 */       throw new RuntimeException(ioexception);
/*      */     } finally {
/*  454 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderChar(char ch, boolean italic) {
/*  462 */     if (ch != ' ' && ch != ' ') {
/*  463 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(ch);
/*  464 */       return (i != -1 && !this.unicodeFlag) ? renderDefaultChar(i, italic) : renderUnicodeChar(ch, italic);
/*      */     } 
/*  466 */     return !this.unicodeFlag ? this.charWidthFloat[ch] : 4.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderDefaultChar(int ch, boolean italic) {
/*  474 */     int i = ch % 16 * 8;
/*  475 */     int j = ch / 16 * 8;
/*  476 */     int k = italic ? 1 : 0;
/*  477 */     bindTexture(this.locationFontTexture);
/*  478 */     float f = this.charWidthFloat[ch];
/*  479 */     float f1 = 7.99F;
/*  480 */     GL11.glBegin(5);
/*  481 */     GL11.glTexCoord2f(i / 128.0F, j / 128.0F);
/*  482 */     GL11.glVertex3f(this.posX + k, this.posY, 0.0F);
/*  483 */     GL11.glTexCoord2f(i / 128.0F, (j + 7.99F) / 128.0F);
/*  484 */     GL11.glVertex3f(this.posX - k, this.posY + 7.99F, 0.0F);
/*  485 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, j / 128.0F);
/*  486 */     GL11.glVertex3f(this.posX + f1 - 1.0F + k, this.posY, 0.0F);
/*  487 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, (j + 7.99F) / 128.0F);
/*  488 */     GL11.glVertex3f(this.posX + f1 - 1.0F - k, this.posY + 7.99F, 0.0F);
/*  489 */     GL11.glEnd();
/*  490 */     return f;
/*      */   }
/*      */   
/*      */   private ResourceLocation getUnicodePageLocation(int page) {
/*  494 */     if (unicodePageLocations[page] == null) {
/*  495 */       unicodePageLocations[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(page) }));
/*  496 */       unicodePageLocations[page] = FontUtils.getHdFontLocation(unicodePageLocations[page]);
/*      */     } 
/*      */     
/*  499 */     return unicodePageLocations[page];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadGlyphTexture(int page) {
/*  506 */     bindTexture(getUnicodePageLocation(page));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderUnicodeChar(char ch, boolean italic) {
/*  513 */     if (this.glyphWidth[ch] == 0) {
/*  514 */       return 0.0F;
/*      */     }
/*  516 */     int i = ch / 256;
/*  517 */     loadGlyphTexture(i);
/*  518 */     int j = this.glyphWidth[ch] >>> 4;
/*  519 */     int k = this.glyphWidth[ch] & 0xF;
/*  520 */     float f = j;
/*  521 */     float f1 = (k + 1);
/*  522 */     float f2 = (ch % 16 * 16) + f;
/*  523 */     float f3 = ((ch & 0xFF) / 16 * 16);
/*  524 */     float f4 = f1 - f - 0.02F;
/*  525 */     float f5 = italic ? 1.0F : 0.0F;
/*  526 */     GL11.glBegin(5);
/*  527 */     GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
/*  528 */     GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
/*  529 */     GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
/*  530 */     GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
/*  531 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
/*  532 */     GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
/*  533 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
/*  534 */     GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
/*  535 */     GL11.glEnd();
/*  536 */     return (f1 - f) / 2.0F + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawStringWithShadow(String text, float x, float y, int color) {
/*  544 */     return drawString(text, x, y, color, true);
/*      */   }
/*      */   
/*      */   public void drawOutlinedString(String text, float x, float y, int borderColor, int color) {
/*  548 */     drawString(text, x - 0.5F, y, borderColor);
/*  549 */     drawString(text, x + 0.5F, y, borderColor);
/*  550 */     drawString(text, x, y - 0.5F, borderColor);
/*  551 */     drawString(text, x, y + 0.5F, borderColor);
/*  552 */     drawString(text, x, y, color);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawCenterOutlinedString(String text, float x, float y, int borderColor, int color) {
/*  557 */     drawString(text, x - (getStringWidth(text) / 2) - 0.5F, y, borderColor);
/*  558 */     drawString(text, x - (getStringWidth(text) / 2) + 0.5F, y, borderColor);
/*  559 */     drawString(text, x - (getStringWidth(text) / 2), y - 0.5F, borderColor);
/*  560 */     drawString(text, x - (getStringWidth(text) / 2), y + 0.5F, borderColor);
/*  561 */     drawString(text, x - (getStringWidth(text) / 2), y, color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawStringWithShadow(String text, float x, float y, int color, int shadowColor, float shadowShift) {
/*  568 */     drawString(text, x + shadowShift, y + shadowShift, shadowColor, false);
/*  569 */     return drawString(text, x, y, color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, float x, int y, int color) {
/*  576 */     return drawString(text, x, y, color, false);
/*      */   }
/*      */   
/*      */   public int drawString(String text, float x, float y, int color) {
/*  580 */     drawString(text, x, y + 2.0F, color, false);
/*  581 */     return getStringWidth(text);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, float x, float y, int color, boolean dropShadow) {
/*      */     int i;
/*  588 */     y -= 2.0F;
/*  589 */     enableAlpha();
/*  590 */     resetStyles();
/*      */     
/*  592 */     GL11.glEnable(3042);
/*      */     
/*  594 */     if (dropShadow) {
/*  595 */       i = renderString(text, x + 0.5F, y + 0.5F, color, true);
/*  596 */       i = Math.max(i, renderString(text, x, y, color, false));
/*      */     } else {
/*  598 */       i = renderString(text, x, y, color, false);
/*      */     } 
/*  600 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String bidiReorder(String text) {
/*  607 */     return FontRendererCallback.bidiReorder(this, text);
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
/*      */   private void resetStyles() {
/*  624 */     this.randomStyle = false;
/*  625 */     this.boldStyle = false;
/*  626 */     this.italicStyle = false;
/*  627 */     this.underlineStyle = false;
/*  628 */     this.strikethroughStyle = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderStringAtPos(String text, boolean shadow) {
/*  635 */     for (int i = 0; i < text.length(); i++) {
/*  636 */       char c0 = text.charAt(i);
/*      */       
/*  638 */       if (c0 == '§' && i + 1 < text.length()) {
/*  639 */         int l = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
/*      */         
/*  641 */         if (l < 16) {
/*  642 */           this.randomStyle = false;
/*  643 */           this.boldStyle = false;
/*  644 */           this.strikethroughStyle = false;
/*  645 */           this.underlineStyle = false;
/*  646 */           this.italicStyle = false;
/*      */           
/*  648 */           if (l < 0 || l > 15) {
/*  649 */             l = 15;
/*      */           }
/*      */           
/*  652 */           if (shadow) {
/*  653 */             l += 16;
/*      */           }
/*      */           
/*  656 */           int i1 = this.colorCode[l];
/*      */           
/*  658 */           if (Config.isCustomColors()) {
/*  659 */             i1 = CustomColors.getTextColor(l, i1);
/*      */           }
/*      */           
/*  662 */           this.textColor = i1;
/*  663 */           setColor((i1 >> 16) / 255.0F, (i1 >> 8 & 0xFF) / 255.0F, (i1 & 0xFF) / 255.0F, this.alpha);
/*  664 */         } else if (l == 16) {
/*  665 */           this.randomStyle = true;
/*  666 */         } else if (l == 17) {
/*  667 */           this.boldStyle = true;
/*  668 */         } else if (l == 18) {
/*  669 */           this.strikethroughStyle = true;
/*  670 */         } else if (l == 19) {
/*  671 */           this.underlineStyle = true;
/*  672 */         } else if (l == 20) {
/*  673 */           this.italicStyle = true;
/*  674 */         } else if (l == 21) {
/*  675 */           this.randomStyle = false;
/*  676 */           this.boldStyle = false;
/*  677 */           this.strikethroughStyle = false;
/*  678 */           this.underlineStyle = false;
/*  679 */           this.italicStyle = false;
/*  680 */           setColor(this.red, this.blue, this.green, this.alpha);
/*      */         } 
/*      */         
/*  683 */         i++;
/*      */       } else {
/*  685 */         int j = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c0);
/*      */         
/*  687 */         if (this.randomStyle && j != -1) {
/*  688 */           char c1; int k = getCharWidth(c0);
/*      */ 
/*      */           
/*      */           do {
/*  692 */             j = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".length());
/*  693 */             c1 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".charAt(j);
/*      */           }
/*  695 */           while (k != getCharWidth(c1));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  700 */           c0 = c1;
/*      */         } 
/*      */         
/*  703 */         float f1 = (j != -1 && !this.unicodeFlag) ? this.offsetBold : 0.5F;
/*  704 */         boolean flag = ((c0 == '\000' || j == -1 || this.unicodeFlag) && shadow);
/*      */         
/*  706 */         if (flag) {
/*  707 */           this.posX -= f1;
/*  708 */           this.posY -= f1;
/*      */         } 
/*      */         
/*  711 */         float f = renderChar(c0, this.italicStyle);
/*      */         
/*  713 */         if (flag) {
/*  714 */           this.posX += f1;
/*  715 */           this.posY += f1;
/*      */         } 
/*      */         
/*  718 */         if (this.boldStyle) {
/*  719 */           this.posX += f1;
/*      */           
/*  721 */           if (flag) {
/*  722 */             this.posX -= f1;
/*  723 */             this.posY -= f1;
/*      */           } 
/*      */           
/*  726 */           renderChar(c0, this.italicStyle);
/*  727 */           this.posX -= f1;
/*      */           
/*  729 */           if (flag) {
/*  730 */             this.posX += f1;
/*  731 */             this.posY += f1;
/*      */           } 
/*      */           
/*  734 */           f += f1;
/*      */         } 
/*      */         
/*  737 */         doDraw(f);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void doDraw(float p_doDraw_1_) {
/*  743 */     if (this.strikethroughStyle) {
/*  744 */       Tessellator tessellator = Tessellator.getInstance();
/*  745 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  746 */       GlStateManager.disableTexture2D();
/*  747 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  748 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  749 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  750 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  751 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  752 */       tessellator.draw();
/*  753 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  756 */     if (this.underlineStyle) {
/*  757 */       Tessellator tessellator1 = Tessellator.getInstance();
/*  758 */       WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
/*  759 */       GlStateManager.disableTexture2D();
/*  760 */       worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
/*  761 */       int i = this.underlineStyle ? -1 : 0;
/*  762 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  763 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  764 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  765 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  766 */       tessellator1.draw();
/*  767 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  770 */     this.posX += p_doDraw_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderStringAligned(String text, int x, int y, int width, int color, boolean dropShadow) {
/*  777 */     if (this.bidiFlag) {
/*  778 */       int i = getStringWidth(bidiReorder(text));
/*  779 */       x = x + width - i;
/*      */     } 
/*      */     
/*  782 */     return renderString(text, x, y, color, dropShadow);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderString(String text, float x, float y, int color, boolean dropShadow) {
/*  789 */     if (text == null) {
/*  790 */       return 0;
/*      */     }
/*  792 */     if (this.bidiFlag) {
/*  793 */       text = bidiReorder(text);
/*      */     }
/*      */     
/*  796 */     if ((color & 0xFC000000) == 0) {
/*  797 */       color |= 0xFF000000;
/*      */     }
/*      */     
/*  800 */     if (dropShadow) {
/*  801 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*      */     }
/*      */     
/*  804 */     this.red = (color >> 16 & 0xFF) / 255.0F;
/*  805 */     this.blue = (color >> 8 & 0xFF) / 255.0F;
/*  806 */     this.green = (color & 0xFF) / 255.0F;
/*  807 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/*  808 */     setColor(this.red, this.blue, this.green, this.alpha);
/*  809 */     this.posX = x;
/*  810 */     this.posY = y;
/*      */     
/*  812 */     getStringCache().renderString(text, x, y, color, dropShadow);
/*      */     
/*  814 */     return (int)this.posX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringWidth(String text) {
/*  822 */     if (text == null) {
/*  823 */       return 0;
/*      */     }
/*  825 */     return this.stringCache.getStringWidth(text);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharWidth(char character) {
/*  833 */     return Math.round(getCharWidthFloat(character));
/*      */   }
/*      */   
/*      */   private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
/*  837 */     if (p_getCharWidthFloat_1_ == '§')
/*  838 */       return -1.0F; 
/*  839 */     if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != ' ') {
/*  840 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_getCharWidthFloat_1_);
/*      */       
/*  842 */       if (p_getCharWidthFloat_1_ > '\000' && i != -1 && !this.unicodeFlag)
/*  843 */         return this.charWidthFloat[i]; 
/*  844 */       if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
/*  845 */         int j = this.glyphWidth[p_getCharWidthFloat_1_] >>> 4;
/*  846 */         int k = this.glyphWidth[p_getCharWidthFloat_1_] & 0xF;
/*      */         
/*  848 */         if (k > 7) {
/*  849 */           k = 15;
/*  850 */           j = 0;
/*      */         } 
/*      */         
/*  853 */         k++;
/*  854 */         return ((k - j) / 2 + 1);
/*      */       } 
/*  856 */       return 0.0F;
/*      */     } 
/*      */     
/*  859 */     return this.charWidthFloat[32];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width) {
/*  868 */     return this.stringCache.trimStringToWidth(text, width, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width, boolean reverse) {
/*  875 */     StringBuilder stringbuilder = new StringBuilder();
/*  876 */     float f = 0.0F;
/*  877 */     int i = reverse ? (text.length() - 1) : 0;
/*  878 */     int j = reverse ? -1 : 1;
/*  879 */     boolean flag = false;
/*  880 */     boolean flag1 = false;
/*      */     int k;
/*  882 */     for (k = i; k >= 0 && k < text.length() && f < width; k += j) {
/*  883 */       char c0 = text.charAt(k);
/*  884 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  886 */       if (flag) {
/*  887 */         flag = false;
/*      */         
/*  889 */         if (c0 != 'l' && c0 != 'L') {
/*  890 */           if (c0 == 'r' || c0 == 'R') {
/*  891 */             flag1 = false;
/*      */           }
/*      */         } else {
/*  894 */           flag1 = true;
/*      */         } 
/*  896 */       } else if (f1 < 0.0F) {
/*  897 */         flag = true;
/*      */       } else {
/*  899 */         f += f1;
/*      */         
/*  901 */         if (flag1) {
/*  902 */           f++;
/*      */         }
/*      */       } 
/*      */       
/*  906 */       if (f > width) {
/*      */         break;
/*      */       }
/*      */       
/*  910 */       if (reverse) {
/*  911 */         stringbuilder.insert(0, c0);
/*      */       } else {
/*  913 */         stringbuilder.append(c0);
/*      */       } 
/*      */     } 
/*      */     
/*  917 */     return stringbuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String trimStringNewline(String text) {
/*  924 */     while (text != null && text.endsWith("\n")) {
/*  925 */       text = text.substring(0, text.length() - 1);
/*      */     }
/*      */     
/*  928 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
/*  935 */     if (this.blend) {
/*  936 */       GlStateManager.getBlendState(this.oldBlendState);
/*  937 */       GlStateManager.enableBlend();
/*  938 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */     
/*  941 */     resetStyles();
/*  942 */     this.textColor = textColor;
/*  943 */     str = trimStringNewline(str);
/*  944 */     renderSplitString(str, x, y, wrapWidth, false);
/*      */     
/*  946 */     if (this.blend) {
/*  947 */       GlStateManager.setBlendState(this.oldBlendState);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
/*  956 */     for (String s : listFormattedStringToWidth(str, wrapWidth)) {
/*  957 */       renderStringAligned(s, x, y, wrapWidth, this.textColor, addShadow);
/*  958 */       y += this.FONT_HEIGHT;
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
/*  969 */     return this.FONT_HEIGHT * listFormattedStringToWidth(str, maxLength).size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnicodeFlag(boolean unicodeFlagIn) {
/*  977 */     this.unicodeFlag = unicodeFlagIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUnicodeFlag() {
/*  985 */     return this.unicodeFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBidiFlag(boolean bidiFlagIn) {
/*  992 */     this.bidiFlag = bidiFlagIn;
/*      */   }
/*      */   
/*      */   public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
/*  996 */     return Arrays.asList(wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String wrapFormattedStringToWidth(String str, int wrapWidth) {
/* 1003 */     if (str.length() <= 1) {
/* 1004 */       return str;
/*      */     }
/* 1006 */     int i = sizeStringToWidth(str, wrapWidth);
/*      */     
/* 1008 */     if (str.length() <= i) {
/* 1009 */       return str;
/*      */     }
/* 1011 */     String s = str.substring(0, i);
/* 1012 */     char c0 = str.charAt(i);
/* 1013 */     boolean flag = (c0 == ' ' || c0 == '\n');
/* 1014 */     String s1 = getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
/* 1015 */     return s + "\n" + wrapFormattedStringToWidth(s1, wrapWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sizeStringToWidth(String str, int wrapWidth) {
/* 1024 */     int i = str.length();
/* 1025 */     float f = 0.0F;
/* 1026 */     int j = 0;
/* 1027 */     int k = -1;
/*      */     
/* 1029 */     for (boolean flag = false; j < i; j++) {
/* 1030 */       char c0 = str.charAt(j);
/*      */       
/* 1032 */       switch (c0) {
/*      */         case '\n':
/* 1034 */           j--;
/*      */           break;
/*      */         
/*      */         case ' ':
/* 1038 */           k = j;
/*      */         
/*      */         default:
/* 1041 */           f += getCharWidth(c0);
/*      */           
/* 1043 */           if (flag) {
/* 1044 */             f++;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case '§':
/* 1050 */           if (j < i - 1) {
/* 1051 */             j++;
/* 1052 */             char c1 = str.charAt(j);
/*      */             
/* 1054 */             if (c1 != 'l' && c1 != 'L') {
/* 1055 */               if (c1 == 'r' || c1 == 'R' || isFormatColor(c1))
/* 1056 */                 flag = false; 
/*      */               break;
/*      */             } 
/* 1059 */             flag = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */       
/* 1064 */       if (c0 == '\n') {
/*      */         
/* 1066 */         k = ++j;
/*      */         
/*      */         break;
/*      */       } 
/* 1070 */       if (Math.round(f) > wrapWidth) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1075 */     return (j != i && k != -1 && k < j) ? k : j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatColor(char colorChar) {
/* 1082 */     return ((colorChar >= '0' && colorChar <= '9') || (colorChar >= 'a' && colorChar <= 'f') || (colorChar >= 'A' && colorChar <= 'F'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatSpecial(char formatChar) {
/* 1089 */     return ((formatChar >= 'k' && formatChar <= 'o') || (formatChar >= 'K' && formatChar <= 'O') || formatChar == 'r' || formatChar == 'R');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFormatFromString(String text) {
/* 1096 */     String s = "";
/* 1097 */     int i = -1;
/* 1098 */     int j = text.length();
/*      */     
/* 1100 */     while ((i = text.indexOf('§', i + 1)) != -1) {
/* 1101 */       if (i < j - 1) {
/* 1102 */         char c0 = text.charAt(i + 1);
/*      */         
/* 1104 */         if (isFormatColor(c0)) {
/* 1105 */           s = "§" + c0; continue;
/* 1106 */         }  if (isFormatSpecial(c0)) {
/* 1107 */           s = s + "§" + c0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1112 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBidiFlag() {
/* 1119 */     return this.bidiFlag;
/*      */   }
/*      */   
/*      */   public int getColorCode(char character) {
/* 1123 */     int i = "0123456789abcdef".indexOf(character);
/*      */     
/* 1125 */     if (i >= 0 && i < this.colorCode.length) {
/* 1126 */       int j = this.colorCode[i];
/*      */       
/* 1128 */       if (Config.isCustomColors()) {
/* 1129 */         j = CustomColors.getTextColor(i, j);
/*      */       }
/*      */       
/* 1132 */       return j;
/*      */     } 
/* 1134 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setColor(float p_setColor_1_, float p_setColor_2_, float p_setColor_3_, float p_setColor_4_) {
/* 1139 */     GlStateManager.color(p_setColor_1_, p_setColor_2_, p_setColor_3_, p_setColor_4_);
/*      */   }
/*      */   
/*      */   protected void enableAlpha() {
/* 1143 */     GlStateManager.enableAlpha();
/*      */   }
/*      */   
/*      */   protected void bindTexture(ResourceLocation p_bindTexture_1_) {
/* 1147 */     this.renderEngine.bindTexture(p_bindTexture_1_);
/*      */   }
/*      */   
/*      */   protected InputStream getResourceInputStream(ResourceLocation p_getResourceInputStream_1_) throws IOException {
/* 1151 */     return Minecraft.getMinecraft().getResourceManager().getResource(p_getResourceInputStream_1_).getInputStream();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public StringCache getStringCache() {
/* 1157 */     return this.stringCache;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStringCache(StringCache value) {
/* 1162 */     this.stringCache = value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDropShadowEnabled() {
/* 1167 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDropShadowEnabled(boolean value) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled() {
/* 1177 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean value) {}
/*      */ 
/*      */   
/*      */   public float getHeight() {
/* 1186 */     return this.FONT_HEIGHT;
/*      */   }
/*      */   
/*      */   public void drawCenteredString(String s, int x, float y, int textColor) {
/* 1190 */     drawString(s, x - getStringWidth(s) / 2.0F, y, textColor);
/*      */   }
/*      */   
/*      */   public void drawCenteredString(String s, float x, float y, int textColor) {
/* 1194 */     drawString(s, x - getStringWidth(s) / 2.0F, y, textColor);
/*      */   }
/*      */   public void drawCenteredStringWithShadow(String s, float x, float y, int textColor) {
/* 1197 */     drawString(s, x - getStringWidth(s) / 2.0F, y, textColor, true);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fastuni\FastUniFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */