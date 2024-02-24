/*     */ package awareline.main.ui.font.fontmanager;
/*     */ 
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import com.me.theresa.fontRenderer.font.Color;
/*     */ import com.me.theresa.fontRenderer.font.SlickException;
/*     */ import com.me.theresa.fontRenderer.font.UnicodeFont;
/*     */ import com.me.theresa.fontRenderer.font.effect.ColorEffect;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class UnicodeFontRenderer
/*     */   extends FontRenderer
/*     */ {
/*     */   public final UnicodeFont unicodeFont;
/*     */   
/*     */   public UnicodeFontRenderer(Font fontIn, boolean useChinese) {
/*  23 */     super((Minecraft.getMinecraft()).gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), true);
/*  24 */     long startTime = System.currentTimeMillis();
/*  25 */     this.unicodeFont = new UnicodeFont(fontIn);
/*  26 */     this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
/*  27 */     if (useChinese) {
/*  28 */       this.unicodeFont.addGlyphs(0, 65535);
/*     */     } else {
/*  30 */       this.unicodeFont.addAsciiGlyphs();
/*     */     } 
/*     */     
/*     */     try {
/*  34 */       this.unicodeFont.loadGlyphs();
/*  35 */     } catch (SlickException e) {
/*  36 */       e.printStackTrace();
/*     */     } 
/*  38 */     this.FONT_HEIGHT = this.unicodeFont.getHeight("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789") / 2;
/*     */ 
/*     */     
/*  41 */     String text = "[DEBUG] Load " + this.unicodeFont.getFont().getFontName() + this.unicodeFont.getFont().getSize() + " Font need " + ((System.currentTimeMillis() - startTime) / 1000.0D) + "s";
/*  42 */     System.err.println(text.toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawStringDirectly(String string, float x, float y, int color) {
/*  47 */     GL11.glPushMatrix();
/*  48 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/*  49 */     boolean blend = GL11.glIsEnabled(3042);
/*  50 */     boolean lighting = GL11.glIsEnabled(2896);
/*  51 */     boolean texture = GL11.glIsEnabled(3553);
/*  52 */     if (!blend) {
/*  53 */       GL11.glEnable(3042);
/*     */     }
/*  55 */     if (lighting) {
/*  56 */       GL11.glDisable(2896);
/*     */     }
/*  58 */     if (texture) {
/*  59 */       GL11.glDisable(3553);
/*     */     }
/*  61 */     this.unicodeFont.drawString(x * 2.0F, y * 2.0F, string, new Color(color));
/*  62 */     if (texture) {
/*  63 */       GL11.glEnable(3553);
/*     */     }
/*  65 */     if (lighting) {
/*  66 */       GL11.glEnable(2896);
/*     */     }
/*  68 */     if (!blend) {
/*  69 */       GL11.glDisable(3042);
/*     */     }
/*  71 */     RenderUtil.resetColor();
/*  72 */     GL11.glPopMatrix();
/*  73 */     GlStateManager.bindTexture(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int drawString(String text, int x, int y, int color) {
/*  78 */     return drawString(text, x, y, color, (new Color(color)).getAlpha());
/*     */   }
/*     */   
/*     */   public int drawString(String text, float x, float y, int color) {
/*  82 */     drawString(text, x, y, color, (new Color(color)).getAlpha());
/*  83 */     return color;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drawStringWithAlpha(String text, float x, float y, int color, float alpha) {
/*  89 */     text = "搂r" + text;
/*     */     
/*  91 */     float len = -1.0F;
/*  92 */     for (String str : text.split("搂")) {
/*  93 */       if (str.length() >= 1) {
/*  94 */         switch (str.charAt(0)) {
/*     */           case '0':
/*  96 */             color = (new Color(0, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '1':
/* 100 */             color = (new Color(0, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '2':
/* 104 */             color = (new Color(0, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '3':
/* 108 */             color = (new Color(0, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '4':
/* 112 */             color = (new Color(170, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '5':
/* 116 */             color = (new Color(170, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '6':
/* 120 */             color = (new Color(255, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '7':
/* 124 */             color = (new Color(170, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '8':
/* 128 */             color = (new Color(85, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case '9':
/* 132 */             color = (new Color(85, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'a':
/* 136 */             color = (new Color(85, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'b':
/* 140 */             color = (new Color(85, 255, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'c':
/* 144 */             color = (new Color(255, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'd':
/* 148 */             color = (new Color(255, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'e':
/* 152 */             color = (new Color(255, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'f':
/* 156 */             color = (new Color(255, 255, 255)).getRGB();
/*     */             break;
/*     */         } 
/* 159 */         Color col = new Color(color);
/* 160 */         str = str.substring(1);
/* 161 */         drawStringDirectly(str, x + len, y, (new Color(col.getRed(), col.getGreen(), col.getBlue(), (int)alpha)).getRGB());
/* 162 */         len += (getStringWidth(str) + 1);
/*     */       } 
/* 164 */     }  return (int)len;
/*     */   }
/*     */   
/*     */   public int drawString(String text, float x, float y, int color, int alpha) {
/* 168 */     text = "搂r" + text;
/* 169 */     float len = -1.0F;
/* 170 */     for (String str : text.split("搂")) {
/* 171 */       if (str.length() >= 1) {
/* 172 */         switch (str.charAt(0)) {
/*     */           case '0':
/* 174 */             color = (new Color(0, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '1':
/* 178 */             color = (new Color(0, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '2':
/* 182 */             color = (new Color(0, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '3':
/* 186 */             color = (new Color(0, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '4':
/* 190 */             color = (new Color(170, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '5':
/* 194 */             color = (new Color(170, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '6':
/* 198 */             color = (new Color(255, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '7':
/* 202 */             color = (new Color(170, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '8':
/* 206 */             color = (new Color(85, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case '9':
/* 210 */             color = (new Color(85, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'a':
/* 214 */             color = (new Color(85, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'b':
/* 218 */             color = (new Color(85, 255, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'c':
/* 222 */             color = (new Color(255, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'd':
/* 226 */             color = (new Color(255, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'e':
/* 230 */             color = (new Color(255, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'f':
/* 234 */             color = (new Color(255, 255, 255)).getRGB();
/*     */             break;
/*     */         } 
/* 237 */         Color col = new Color(color);
/* 238 */         str = str.substring(1);
/* 239 */         drawStringDirectly(str, x + len, y, (new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha)).getRGB());
/* 240 */         len += (getStringWidth(str) + 1);
/*     */       } 
/* 242 */     }  return (int)len;
/*     */   }
/*     */ 
/*     */   
/*     */   public int drawStringWithShadow(String text, float x, float y, int color) {
/* 247 */     return drawStringWithShadow(text, x, y, color, (new Color(color)).getAlpha());
/*     */   }
/*     */   
/*     */   public int drawStringWithHighShadow(String text, float x, float y, int color) {
/* 251 */     return drawStringWithHighShadow(text, x, y, color, (new Color(color)).getAlpha());
/*     */   }
/*     */   
/*     */   public int drawStringWithShadow(String text, int x, int y, int color) {
/* 255 */     return drawStringWithShadow(text, x, y, color, (new Color(color)).getAlpha());
/*     */   }
/*     */   
/*     */   public int drawStringWithShadow(String text, float x, float y, int color, int alpha) {
/* 259 */     text = "搂r" + text;
/* 260 */     float len = -1.0F;
/* 261 */     for (String str : text.split("搂")) {
/* 262 */       if (str.length() >= 1) {
/* 263 */         switch (str.charAt(0)) {
/*     */           case '0':
/* 265 */             color = (new Color(0, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '1':
/* 269 */             color = (new Color(0, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '2':
/* 273 */             color = (new Color(0, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '3':
/* 277 */             color = (new Color(0, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '4':
/* 281 */             color = (new Color(170, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '5':
/* 285 */             color = (new Color(170, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '6':
/* 289 */             color = (new Color(255, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '7':
/* 293 */             color = (new Color(170, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '8':
/* 297 */             color = (new Color(85, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case '9':
/* 301 */             color = (new Color(85, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'a':
/* 305 */             color = (new Color(85, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'b':
/* 309 */             color = (new Color(85, 255, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'c':
/* 313 */             color = (new Color(255, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'd':
/* 317 */             color = (new Color(255, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'e':
/* 321 */             color = (new Color(255, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'f':
/* 325 */             color = (new Color(255, 255, 255)).getRGB();
/*     */             break;
/*     */         } 
/* 328 */         Color col = new Color(color);
/* 329 */         str = str.substring(1);
/* 330 */         drawStringDirectly(str, x + len + 0.5F, y + 0.5F, (new Color(0, 0, 0, 80)).getRGB());
/* 331 */         drawStringDirectly(str, x + len, y, (new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha)).getRGB());
/* 332 */         len += (getStringWidth(str) + 1);
/*     */       } 
/* 334 */     }  return (int)len;
/*     */   }
/*     */   
/*     */   public int drawStringWithHighShadow(String text, float x, float y, int color, int alpha) {
/* 338 */     text = "搂r" + text;
/* 339 */     float len = -1.0F;
/* 340 */     for (String str : text.split("搂")) {
/* 341 */       if (str.length() >= 1) {
/* 342 */         switch (str.charAt(0)) {
/*     */           case '0':
/* 344 */             color = (new Color(0, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '1':
/* 348 */             color = (new Color(0, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '2':
/* 352 */             color = (new Color(0, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '3':
/* 356 */             color = (new Color(0, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '4':
/* 360 */             color = (new Color(170, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '5':
/* 364 */             color = (new Color(170, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '6':
/* 368 */             color = (new Color(255, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '7':
/* 372 */             color = (new Color(170, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '8':
/* 376 */             color = (new Color(85, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case '9':
/* 380 */             color = (new Color(85, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'a':
/* 384 */             color = (new Color(85, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'b':
/* 388 */             color = (new Color(85, 255, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'c':
/* 392 */             color = (new Color(255, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'd':
/* 396 */             color = (new Color(255, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'e':
/* 400 */             color = (new Color(255, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'f':
/* 404 */             color = (new Color(255, 255, 255)).getRGB();
/*     */             break;
/*     */         } 
/* 407 */         Color col = new Color(color);
/* 408 */         str = str.substring(1);
/* 409 */         drawStringDirectly(str, x + len + 0.5F, y + 0.5F, (new Color(0, 0, 0, 120)).getRGB());
/* 410 */         drawStringDirectly(str, x + len, y, (new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha)).getRGB());
/* 411 */         len += (getStringWidth(str) + 1);
/*     */       } 
/* 413 */     }  return (int)len;
/*     */   }
/*     */   
/*     */   public void drawStringOther(String string, double x, double y, int color) {
/* 417 */     if (string == null) {
/*     */       return;
/*     */     }
/* 420 */     GL11.glPushMatrix();
/* 421 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 422 */     boolean blend = GL11.glIsEnabled(3042);
/* 423 */     boolean lighting = GL11.glIsEnabled(2896);
/* 424 */     boolean texture = GL11.glIsEnabled(3553);
/* 425 */     if (!blend) {
/* 426 */       GL11.glEnable(3042);
/*     */     }
/* 428 */     if (lighting) {
/* 429 */       GL11.glDisable(2896);
/*     */     }
/* 431 */     if (texture) {
/* 432 */       GL11.glDisable(3553);
/*     */     }
/* 434 */     this.unicodeFont.drawString((float)(x * 2.0D), (float)(y * 2.0D), string, new Color(color));
/* 435 */     if (texture) {
/* 436 */       GL11.glEnable(3553);
/*     */     }
/* 438 */     if (lighting) {
/* 439 */       GL11.glEnable(2896);
/*     */     }
/* 441 */     if (!blend) {
/* 442 */       GL11.glDisable(3042);
/*     */     }
/* 444 */     GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 445 */     GL11.glPopMatrix();
/* 446 */     GlStateManager.bindTexture(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCharWidth(char c) {
/* 451 */     return getStringWidth(Character.toString(c));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStringWidth(String string) {
/* 457 */     return this.unicodeFont.getWidth(string) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String text, float x, float y, int color) {
/* 462 */     drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
/* 467 */     drawStringWithShadow(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String text, int x, int y, int color) {
/* 471 */     drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public int getStringHeight() {
/* 475 */     return getHeight();
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 479 */     return (this.FONT_HEIGHT - 8) / 2;
/*     */   }
/*     */   
/*     */   public int HUDDrawString(String name, float x, float y, int color) {
/* 483 */     return drawStringWithShadow(name, x, y, color, (new Color(color)).getAlpha());
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredStringWithAlpha(String text, float x, float y, int color, float alpha) {
/* 488 */     drawStringWithAlpha(text, x - (getStringWidth(text) / 2), y, color, alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\UnicodeFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */