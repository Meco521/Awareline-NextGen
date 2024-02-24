/*     */ package awareline.main.ui.font.cfont;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CFontRenderer
/*     */   extends CFont
/*     */ {
/*  15 */   public final int FONT_HEIGHT = 0;
/*     */   
/*     */   protected final CFont.CharData[] boldChars;
/*     */   protected final CFont.CharData[] italicChars;
/*     */   protected final CFont.CharData[] boldItalicChars;
/*     */   private final int[] colorCode;
/*     */   protected DynamicTexture texBold;
/*     */   protected DynamicTexture texItalic;
/*     */   protected DynamicTexture texItalicBold;
/*     */   
/*     */   public CFontRenderer(Font font) {
/*  26 */     super(font, true, true);
/*  27 */     this.boldChars = new CFont.CharData[256];
/*  28 */     this.italicChars = new CFont.CharData[256];
/*  29 */     this.boldItalicChars = new CFont.CharData[256];
/*  30 */     this.colorCode = new int[32];
/*  31 */     setupMinecraftColorcodes();
/*  32 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, double d, double x, double y, int color) {
/*  36 */     float shadowWidth = drawString(text, x + 0.5D, y + 0.5D, color, true);
/*  37 */     return Math.max(shadowWidth, drawString(text, d, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, String name, double x, double y, int color) {
/*  41 */     float shadowWidth = drawString(text, x + 0.5D, y + 0.5D, color, true);
/*  42 */     return Math.max(shadowWidth, drawString(text, name, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, double d, double x, double y, int color, int i) {
/*  46 */     float shadowWidth = drawString(text, x + 0.5D, y + 0.5D, color, true);
/*  47 */     return Math.max(shadowWidth, drawString(text, d, x, y, color, false));
/*     */   }
/*     */   
/*     */   public void drawString(String name, float x, float y, Color color) {
/*  51 */     drawString(name, x, y, color.getRGB(), false);
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, float f, double x, double y, int color) {
/*  55 */     float shadowWidth = drawString(text, x + 0.5D, y + 0.5D, color, true);
/*  56 */     return Math.max(shadowWidth, drawString(text, f, x, y, color, false));
/*     */   }
/*     */   
/*     */   public void drawStringWithShadowByCustom(String text, double x, double y, int color) {
/*  60 */     if (HUD.getInstance.isEnabled() && !((Boolean)HUD.getInstance.fontNoShadow.get()).booleanValue()) {
/*  61 */       drawString(text, x + 0.5D, y + 0.5D, color, true);
/*     */     }
/*  63 */     drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, double x, double y, int color) {
/*  67 */     float shadowWidth = drawString(text, x + 0.5D, y + 0.5D, color, true);
/*  68 */     return Math.max(shadowWidth, drawString(text, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawString(String text, float x, float y, int color) {
/*  72 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawString(String text, double x, double y, int color) {
/*  76 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawString(String text, float x, float y, int color, int i, int j) {
/*  80 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawCenteredString(String text, double x, double y, int color) {
/*  84 */     return drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredString(String text, float x, float y, int color) {
/*  88 */     return drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
/*  92 */     return drawStringWithShadow(text, (x - (getStringWidth(text) / 2)), y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
/*  96 */     return drawStringWithShadow(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public float drawString(String text, double x, double y, double y2, int color, boolean shadow) {
/* 100 */     x--;
/* 101 */     if (text == null) {
/* 102 */       return 0.0F;
/*     */     }
/* 104 */     if (color == 553648127) {
/* 105 */       color = 16777215;
/*     */     }
/* 107 */     if ((color & 0xFC000000) == 0) {
/* 108 */       color |= 0xFF000000;
/*     */     }
/* 110 */     if (shadow) {
/* 111 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/* 113 */     CFont.CharData[] currentData = this.charData;
/* 114 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 115 */     boolean bold = false;
/* 116 */     boolean italic = false;
/* 117 */     boolean strikethrough = false;
/* 118 */     boolean underline = false;
/* 119 */     x *= 2.0D;
/* 120 */     y = (y - 3.0D) * 2.0D;
/* 121 */     GL11.glPushMatrix();
/* 122 */     GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 123 */     GlStateManager.enableBlend();
/* 124 */     GlStateManager.blendFunc(770, 771);
/* 125 */     GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 126 */     int size = text.length();
/* 127 */     GlStateManager.enableTexture2D();
/* 128 */     GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 129 */     GL11.glBindTexture(3553, this.tex.getGlTextureId());
/* 130 */     int i = 0;
/* 131 */     while (i < size) {
/* 132 */       char character = text.charAt(i);
/* 133 */       if (character == '§') {
/* 134 */         int colorIndex = 21;
/*     */         try {
/* 136 */           colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/* 137 */         } catch (Exception e) {
/* 138 */           e.printStackTrace();
/*     */         } 
/* 140 */         if (colorIndex < 16) {
/* 141 */           bold = false;
/* 142 */           italic = false;
/* 143 */           underline = false;
/* 144 */           strikethrough = false;
/* 145 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 146 */           currentData = this.charData;
/* 147 */           if (colorIndex < 0) {
/* 148 */             colorIndex = 15;
/*     */           }
/* 150 */           if (shadow) {
/* 151 */             colorIndex += 16;
/*     */           }
/* 153 */           int colorcode = this.colorCode[colorIndex];
/* 154 */           GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 155 */         } else if (colorIndex == 17) {
/* 156 */           bold = true;
/* 157 */           if (italic) {
/* 158 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 159 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 161 */             GlStateManager.bindTexture(this.texBold.getGlTextureId());
/* 162 */             currentData = this.boldChars;
/*     */           } 
/* 164 */         } else if (colorIndex == 18) {
/* 165 */           strikethrough = true;
/* 166 */         } else if (colorIndex == 19) {
/* 167 */           underline = true;
/* 168 */         } else if (colorIndex == 20) {
/* 169 */           italic = true;
/* 170 */           if (bold) {
/* 171 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 172 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 174 */             GlStateManager.bindTexture(this.texItalic.getGlTextureId());
/* 175 */             currentData = this.italicChars;
/*     */           } 
/*     */         } else {
/* 178 */           bold = false;
/* 179 */           italic = false;
/* 180 */           underline = false;
/* 181 */           strikethrough = false;
/* 182 */           GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 183 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 184 */           currentData = this.charData;
/*     */         } 
/* 186 */         i++;
/* 187 */       } else if (character < currentData.length) {
/* 188 */         GL11.glBegin(4);
/* 189 */         drawChar(currentData, character, (float)x, (float)y);
/* 190 */         GL11.glEnd();
/* 191 */         if (strikethrough) {
/* 192 */           drawLine(x, y + ((currentData[character]).height / 2), x + (currentData[character]).width - 8.0D, y + ((currentData[character]).height / 2), 1.0F);
/*     */         }
/* 194 */         if (underline) {
/* 195 */           drawLine(x, y + (currentData[character]).height - 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height - 2.0D, 1.0F);
/*     */         }
/* 197 */         x += ((currentData[character]).width - 8 + this.charOffset);
/*     */       } 
/* 199 */       i++;
/*     */     } 
/* 201 */     GL11.glHint(3155, 4352);
/* 202 */     GL11.glPopMatrix();
/* 203 */     return (float)x / 2.0F;
/*     */   }
/*     */   
/*     */   public float drawString(String text, double x, double y, int color, boolean shadow) {
/* 207 */     x--;
/* 208 */     if (text == null) {
/* 209 */       return 0.0F;
/*     */     }
/* 211 */     if (color == 553648127) {
/* 212 */       color = 16777215;
/*     */     }
/* 214 */     if ((color & 0xFC000000) == 0) {
/* 215 */       color |= 0xFF000000;
/*     */     }
/* 217 */     if (shadow) {
/* 218 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/* 220 */     CFont.CharData[] currentData = this.charData;
/* 221 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 222 */     boolean bold = false;
/* 223 */     boolean italic = false;
/* 224 */     boolean strikethrough = false;
/* 225 */     boolean underline = false;
/* 226 */     x *= 2.0D;
/* 227 */     y = (y - 3.0D) * 2.0D;
/* 228 */     GL11.glPushMatrix();
/* 229 */     GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 230 */     GlStateManager.enableBlend();
/* 231 */     GlStateManager.blendFunc(770, 771);
/* 232 */     GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 233 */     int size = text.length();
/* 234 */     GlStateManager.enableTexture2D();
/* 235 */     GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 236 */     GL11.glBindTexture(3553, this.tex.getGlTextureId());
/* 237 */     int i = 0;
/* 238 */     while (i < size) {
/* 239 */       char character = text.charAt(i);
/* 240 */       if (character == '§') {
/* 241 */         int colorIndex = 21;
/*     */         try {
/* 243 */           colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/* 244 */         } catch (Exception e) {
/* 245 */           e.printStackTrace();
/*     */         } 
/* 247 */         if (colorIndex < 16) {
/* 248 */           bold = false;
/* 249 */           italic = false;
/* 250 */           underline = false;
/* 251 */           strikethrough = false;
/* 252 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 253 */           currentData = this.charData;
/* 254 */           if (colorIndex < 0) {
/* 255 */             colorIndex = 15;
/*     */           }
/* 257 */           if (shadow) {
/* 258 */             colorIndex += 16;
/*     */           }
/* 260 */           int colorcode = this.colorCode[colorIndex];
/* 261 */           GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 262 */         } else if (colorIndex == 17) {
/* 263 */           bold = true;
/* 264 */           if (italic) {
/* 265 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 266 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 268 */             GlStateManager.bindTexture(this.texBold.getGlTextureId());
/* 269 */             currentData = this.boldChars;
/*     */           } 
/* 271 */         } else if (colorIndex == 18) {
/* 272 */           strikethrough = true;
/* 273 */         } else if (colorIndex == 19) {
/* 274 */           underline = true;
/* 275 */         } else if (colorIndex == 20) {
/* 276 */           italic = true;
/* 277 */           if (bold) {
/* 278 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 279 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 281 */             GlStateManager.bindTexture(this.texItalic.getGlTextureId());
/* 282 */             currentData = this.italicChars;
/*     */           } 
/*     */         } else {
/* 285 */           bold = false;
/* 286 */           italic = false;
/* 287 */           underline = false;
/* 288 */           strikethrough = false;
/* 289 */           GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 290 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 291 */           currentData = this.charData;
/*     */         } 
/* 293 */         i++;
/* 294 */       } else if (character < currentData.length) {
/* 295 */         GL11.glBegin(4);
/* 296 */         drawChar(currentData, character, (float)x, (float)y);
/* 297 */         GL11.glEnd();
/* 298 */         if (strikethrough) {
/* 299 */           drawLine(x, y + ((currentData[character]).height / 2), x + (currentData[character]).width - 8.0D, y + ((currentData[character]).height / 2), 1.0F);
/*     */         }
/* 301 */         if (underline) {
/* 302 */           drawLine(x, y + (currentData[character]).height - 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height - 2.0D, 1.0F);
/*     */         }
/* 304 */         x += ((currentData[character]).width - 8 + this.charOffset);
/*     */       } 
/* 306 */       i++;
/*     */     } 
/* 308 */     GL11.glHint(3155, 4352);
/* 309 */     GL11.glPopMatrix();
/* 310 */     return (float)(x / 2.0D);
/*     */   }
/*     */   
/*     */   public float drawStringToFloat(String text, float x, float y, int color, boolean shadow) {
/* 314 */     x--;
/* 315 */     if (text == null) {
/* 316 */       return 0.0F;
/*     */     }
/* 318 */     if (color == 553648127) {
/* 319 */       color = 16777215;
/*     */     }
/* 321 */     if ((color & 0xFC000000) == 0) {
/* 322 */       color |= 0xFF000000;
/*     */     }
/* 324 */     if (shadow) {
/* 325 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/* 327 */     CFont.CharData[] currentData = this.charData;
/* 328 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 329 */     boolean bold = false;
/* 330 */     boolean italic = false;
/* 331 */     boolean strikethrough = false;
/* 332 */     boolean underline = false;
/* 333 */     x *= 2.0F;
/* 334 */     y = (y - 3.0F) * 2.0F;
/* 335 */     GL11.glPushMatrix();
/* 336 */     GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 337 */     GlStateManager.enableBlend();
/* 338 */     GlStateManager.blendFunc(770, 771);
/* 339 */     GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 340 */     int size = text.length();
/* 341 */     GlStateManager.enableTexture2D();
/* 342 */     GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 343 */     GL11.glBindTexture(3553, this.tex.getGlTextureId());
/* 344 */     int i = 0;
/* 345 */     while (i < size) {
/* 346 */       char character = text.charAt(i);
/* 347 */       if (character == '§') {
/* 348 */         int colorIndex = 21;
/*     */         try {
/* 350 */           colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/* 351 */         } catch (Exception e) {
/* 352 */           e.printStackTrace();
/*     */         } 
/* 354 */         if (colorIndex < 16) {
/* 355 */           bold = false;
/* 356 */           italic = false;
/* 357 */           underline = false;
/* 358 */           strikethrough = false;
/* 359 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 360 */           currentData = this.charData;
/* 361 */           if (colorIndex < 0) {
/* 362 */             colorIndex = 15;
/*     */           }
/* 364 */           if (shadow) {
/* 365 */             colorIndex += 16;
/*     */           }
/* 367 */           int colorcode = this.colorCode[colorIndex];
/* 368 */           GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 369 */         } else if (colorIndex == 17) {
/* 370 */           bold = true;
/* 371 */           if (italic) {
/* 372 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 373 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 375 */             GlStateManager.bindTexture(this.texBold.getGlTextureId());
/* 376 */             currentData = this.boldChars;
/*     */           } 
/* 378 */         } else if (colorIndex == 18) {
/* 379 */           strikethrough = true;
/* 380 */         } else if (colorIndex == 19) {
/* 381 */           underline = true;
/* 382 */         } else if (colorIndex == 20) {
/* 383 */           italic = true;
/* 384 */           if (bold) {
/* 385 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 386 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 388 */             GlStateManager.bindTexture(this.texItalic.getGlTextureId());
/* 389 */             currentData = this.italicChars;
/*     */           } 
/*     */         } else {
/* 392 */           bold = false;
/* 393 */           italic = false;
/* 394 */           underline = false;
/* 395 */           strikethrough = false;
/* 396 */           GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 397 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 398 */           currentData = this.charData;
/*     */         } 
/* 400 */         i++;
/* 401 */       } else if (character < currentData.length) {
/* 402 */         GL11.glBegin(4);
/* 403 */         drawChar(currentData, character, x, y);
/* 404 */         GL11.glEnd();
/* 405 */         if (strikethrough) {
/* 406 */           drawLine(x, (y + (currentData[character]).height / 2.0F), (x + (currentData[character]).width - 8.0F), (y + (currentData[character]).height / 2.0F), 1.0F);
/*     */         }
/*     */         
/* 409 */         if (underline) {
/* 410 */           drawLine(x, (y + (currentData[character]).height - 2.0F), (x + (currentData[character]).width - 8.0F), (y + (currentData[character]).height - 2.0F), 1.0F);
/*     */         }
/*     */         
/* 413 */         x += (currentData[character]).width - 8.0F + this.charOffset;
/*     */       } 
/* 415 */       i++;
/*     */     } 
/* 417 */     GL11.glHint(3155, 4352);
/* 418 */     GL11.glPopMatrix();
/* 419 */     return x / 2.0F;
/*     */   }
/*     */   
/*     */   public float drawString(String text, String name, double x, double y, int color, boolean shadow) {
/* 423 */     x--;
/* 424 */     if (text == null) {
/* 425 */       return 0.0F;
/*     */     }
/* 427 */     if (color == 553648127) {
/* 428 */       color = 16777215;
/*     */     }
/* 430 */     if ((color & 0xFC000000) == 0) {
/* 431 */       color |= 0xFF000000;
/*     */     }
/* 433 */     if (shadow) {
/* 434 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/* 436 */     CFont.CharData[] currentData = this.charData;
/* 437 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 438 */     boolean bold = false;
/* 439 */     boolean italic = false;
/* 440 */     boolean strikethrough = false;
/* 441 */     boolean underline = false;
/* 442 */     x *= 2.0D;
/* 443 */     y = (y - 3.0D) * 2.0D;
/* 444 */     GL11.glPushMatrix();
/* 445 */     GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 446 */     GlStateManager.enableBlend();
/* 447 */     GlStateManager.blendFunc(770, 771);
/* 448 */     GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 449 */     int size = text.length();
/* 450 */     GlStateManager.enableTexture2D();
/* 451 */     GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 452 */     GL11.glBindTexture(3553, this.tex.getGlTextureId());
/* 453 */     int i = 0;
/* 454 */     while (i < size) {
/* 455 */       char character = text.charAt(i);
/* 456 */       if (character == '§') {
/* 457 */         int colorIndex = 21;
/*     */         try {
/* 459 */           colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/* 460 */         } catch (Exception e) {
/* 461 */           e.printStackTrace();
/*     */         } 
/* 463 */         if (colorIndex < 16) {
/* 464 */           bold = false;
/* 465 */           italic = false;
/* 466 */           underline = false;
/* 467 */           strikethrough = false;
/* 468 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 469 */           currentData = this.charData;
/* 470 */           if (colorIndex < 0) {
/* 471 */             colorIndex = 15;
/*     */           }
/* 473 */           if (shadow) {
/* 474 */             colorIndex += 16;
/*     */           }
/* 476 */           int colorcode = this.colorCode[colorIndex];
/* 477 */           GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 478 */         } else if (colorIndex != 16) {
/* 479 */           if (colorIndex == 17) {
/* 480 */             bold = true;
/* 481 */             if (italic) {
/* 482 */               GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 483 */               currentData = this.boldItalicChars;
/*     */             } else {
/* 485 */               GlStateManager.bindTexture(this.texBold.getGlTextureId());
/* 486 */               currentData = this.boldChars;
/*     */             } 
/* 488 */           } else if (colorIndex == 18) {
/* 489 */             strikethrough = true;
/* 490 */           } else if (colorIndex == 19) {
/* 491 */             underline = true;
/* 492 */           } else if (colorIndex == 20) {
/* 493 */             italic = true;
/* 494 */             if (bold) {
/* 495 */               GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/* 496 */               currentData = this.boldItalicChars;
/*     */             } else {
/* 498 */               GlStateManager.bindTexture(this.texItalic.getGlTextureId());
/* 499 */               currentData = this.italicChars;
/*     */             } 
/*     */           } else {
/* 502 */             bold = false;
/* 503 */             italic = false;
/* 504 */             underline = false;
/* 505 */             strikethrough = false;
/* 506 */             GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 507 */             GlStateManager.bindTexture(this.tex.getGlTextureId());
/* 508 */             currentData = this.charData;
/*     */           } 
/* 510 */         }  i++;
/* 511 */       } else if (character < currentData.length) {
/* 512 */         GL11.glBegin(4);
/* 513 */         drawChar(currentData, character, (float)x, (float)y);
/* 514 */         GL11.glEnd();
/* 515 */         if (strikethrough) {
/* 516 */           drawLine(x, y + ((currentData[character]).height / 2), x + (currentData[character]).width - 8.0D, y + ((currentData[character]).height / 2), 1.0F);
/*     */         }
/* 518 */         if (underline) {
/* 519 */           drawLine(x, y + (currentData[character]).height - 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height - 2.0D, 1.0F);
/*     */         }
/* 521 */         x += ((currentData[character]).width - 8 + this.charOffset);
/*     */       } 
/* 523 */       i++;
/*     */     } 
/* 525 */     GL11.glHint(3155, 4352);
/* 526 */     GL11.glPopMatrix();
/* 527 */     return (float)x / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStringWidth(String text) {
/* 532 */     if (text == null) {
/* 533 */       return 0;
/*     */     }
/* 535 */     int width = 0;
/* 536 */     CFont.CharData[] currentData = this.charData;
/* 537 */     int size = text.length();
/* 538 */     int i = 0;
/* 539 */     while (i < size) {
/* 540 */       char character = text.charAt(i);
/* 541 */       if (character == '§') {
/* 542 */         i++;
/* 543 */       } else if (character < currentData.length) {
/* 544 */         width += (currentData[character]).width - 8 + this.charOffset;
/*     */       } 
/* 546 */       i++;
/*     */     } 
/* 548 */     return width / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 553 */     super.setFont(font);
/* 554 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 559 */     super.setAntiAlias(antiAlias);
/* 560 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 565 */     super.setFractionalMetrics(fractionalMetrics);
/* 566 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   private void setupBoldItalicIDs() {
/* 570 */     this.texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
/* 571 */     this.texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
/*     */   }
/*     */   
/*     */   private void drawLineToFloat(float x, float y, float x1, float y1, float width) {
/* 575 */     GL11.glDisable(3553);
/* 576 */     GL11.glLineWidth(width);
/* 577 */     GL11.glBegin(1);
/* 578 */     GL11.glVertex2d(x, y);
/* 579 */     GL11.glVertex2d(x1, y1);
/* 580 */     GL11.glEnd();
/* 581 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   private void drawLine(double x, double y, double x1, double y1, float width) {
/* 585 */     GL11.glDisable(3553);
/* 586 */     GL11.glLineWidth(width);
/* 587 */     GL11.glBegin(1);
/* 588 */     GL11.glVertex2d(x, y);
/* 589 */     GL11.glVertex2d(x1, y1);
/* 590 */     GL11.glEnd();
/* 591 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public List<String> formatString(String string, double width) {
/* 595 */     ArrayList<String> finalWords = new ArrayList<>();
/* 596 */     StringBuilder currentWord = new StringBuilder();
/* 597 */     int lastColorCode = 65535;
/* 598 */     char[] chars = string.toCharArray();
/* 599 */     int i = 0;
/* 600 */     while (i < chars.length) {
/* 601 */       char c = chars[i];
/* 602 */       if (c == '§' && i < chars.length - 1) {
/* 603 */         lastColorCode = chars[i + 1];
/*     */       }
/* 605 */       if (getStringWidth(currentWord.toString() + c) < width) {
/* 606 */         currentWord.append(c);
/*     */       } else {
/* 608 */         finalWords.add(currentWord.toString());
/* 609 */         currentWord = new StringBuilder(String.valueOf(167 + lastColorCode) + c);
/*     */       } 
/* 611 */       i++;
/*     */     } 
/* 613 */     if (currentWord.length() > 0) {
/* 614 */       finalWords.add(currentWord.toString());
/*     */     }
/* 616 */     return finalWords;
/*     */   }
/*     */   
/*     */   private void setupMinecraftColorcodes() {
/* 620 */     for (int i = 0; i < 32; i++) {
/* 621 */       int noClue = (i >> 3 & 0x1) * 85;
/* 622 */       int red = (i >> 2 & 0x1) * 170 + noClue;
/* 623 */       int green = (i >> 1 & 0x1) * 170 + noClue;
/* 624 */       int blue = (i & 0x1) * 170 + noClue;
/*     */       
/* 626 */       if (i == 6) {
/* 627 */         red += 85;
/*     */       }
/*     */       
/* 630 */       if (i >= 16) {
/* 631 */         red >>= 2;
/* 632 */         green >>= 2;
/* 633 */         blue >>= 2;
/*     */       } 
/* 635 */       this.colorCode[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int drawString(String text, float x, float y, int color, int alpha) {
/* 640 */     text = "搂r" + text;
/* 641 */     float len = -1.0F;
/* 642 */     String[] split = text.split("搂");
/* 643 */     for (String str : split) {
/* 644 */       if (str.length() >= 1) {
/* 645 */         switch (str.charAt(0)) {
/*     */           case '0':
/* 647 */             color = (new Color(0, 0, 0)).getRGB();
/*     */             break;
/*     */           case '1':
/* 650 */             color = (new Color(0, 0, 170)).getRGB();
/*     */             break;
/*     */           case '2':
/* 653 */             color = (new Color(0, 170, 0)).getRGB();
/*     */             break;
/*     */           case '3':
/* 656 */             color = (new Color(0, 170, 170)).getRGB();
/*     */             break;
/*     */           case '4':
/* 659 */             color = (new Color(170, 0, 0)).getRGB();
/*     */             break;
/*     */           case '5':
/* 662 */             color = (new Color(170, 0, 170)).getRGB();
/*     */             break;
/*     */           case '6':
/* 665 */             color = (new Color(255, 170, 0)).getRGB();
/*     */             break;
/*     */           case '7':
/* 668 */             color = (new Color(170, 170, 170)).getRGB();
/*     */             break;
/*     */           case '8':
/* 671 */             color = (new Color(85, 85, 85)).getRGB();
/*     */             break;
/*     */           case '9':
/* 674 */             color = (new Color(85, 85, 255)).getRGB();
/*     */             break;
/*     */           case 'a':
/* 677 */             color = (new Color(85, 255, 85)).getRGB();
/*     */             break;
/*     */           case 'b':
/* 680 */             color = (new Color(85, 255, 255)).getRGB();
/*     */             break;
/*     */           case 'c':
/* 683 */             color = (new Color(255, 85, 85)).getRGB();
/*     */             break;
/*     */           case 'd':
/* 686 */             color = (new Color(255, 85, 255)).getRGB();
/*     */             break;
/*     */           case 'e':
/* 689 */             color = (new Color(255, 255, 85)).getRGB();
/*     */             break;
/*     */           case 'f':
/* 692 */             color = (new Color(255, 255, 255)).getRGB();
/*     */             break;
/*     */         } 
/* 695 */         Color col = new Color(color);
/* 696 */         str = str.substring(1);
/* 697 */         drawString(str, x + len, y, (new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha)).getRGB());
/* 698 */         len += (getStringWidth(str) + 1);
/*     */       } 
/*     */     } 
/* 701 */     return (int)len;
/*     */   }
/*     */   
/*     */   public int getStringHeight() {
/* 705 */     return getHeight();
/*     */   }
/*     */   
/*     */   public void HUDDrawString(String text, float x2, float y2, int color) {
/* 709 */     if (!((Boolean)HUD.getInstance.fontNoShadow.get()).booleanValue()) {
/* 710 */       drawStringToFloat(text, x2 + 0.5F, y2 + 0.5F, color, true);
/*     */     }
/* 712 */     drawStringToFloat(text, x2, y2, color, false);
/*     */   }
/*     */   
/*     */   public void drawStringWithColor(String text, float x, float y, int color, int alpha) {
/* 716 */     text = "§r" + text;
/* 717 */     float len = -1.0F;
/* 718 */     for (String str : text.split("§")) {
/* 719 */       if (str.length() >= 1) {
/* 720 */         switch (str.charAt(0)) {
/*     */           case '0':
/* 722 */             color = (new Color(0, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '1':
/* 726 */             color = (new Color(0, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '2':
/* 730 */             color = (new Color(0, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '3':
/* 734 */             color = (new Color(0, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '4':
/* 738 */             color = (new Color(170, 0, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '5':
/* 742 */             color = (new Color(170, 0, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '6':
/* 746 */             color = (new Color(255, 170, 0)).getRGB();
/*     */             break;
/*     */           
/*     */           case '7':
/* 750 */             color = (new Color(170, 170, 170)).getRGB();
/*     */             break;
/*     */           
/*     */           case '8':
/* 754 */             color = (new Color(85, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case '9':
/* 758 */             color = (new Color(85, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'a':
/* 762 */             color = (new Color(85, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'b':
/* 766 */             color = (new Color(85, 255, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'c':
/* 770 */             color = (new Color(255, 85, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'd':
/* 774 */             color = (new Color(255, 85, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'e':
/* 778 */             color = (new Color(255, 255, 85)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'f':
/* 782 */             color = (new Color(255, 255, 255)).getRGB();
/*     */             break;
/*     */           
/*     */           case 'r':
/* 786 */             color = (new Color(255, 255, 255)).getRGB();
/*     */             break;
/*     */         } 
/* 789 */         Color col = new Color(color);
/* 790 */         str = str.substring(1);
/* 791 */         drawString(str, x + len + 0.5F, y + 0.5F, Color.BLACK.getRGB());
/* 792 */         drawString(str, x + len, y, getColor(col.getRed(), col.getGreen(), col.getBlue(), alpha));
/* 793 */         len += (getStringWidth(str) + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public int getColor(int red, int green, int blue, int alpha) {
/* 798 */     byte color = 0;
/* 799 */     int color1 = 0x0 | alpha << 24;
/* 800 */     color1 |= red << 16;
/* 801 */     color1 |= green << 8;
/* 802 */     color1 |= blue;
/* 803 */     return color1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String trimStringToWidth(String text, int width) {
/* 808 */     return trimStringToWidth(text, width, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String trimStringToWidth(String text, float width, boolean reverse) {
/* 815 */     StringBuilder stringbuilder = new StringBuilder();
/* 816 */     float f = 0.0F;
/* 817 */     int i = reverse ? (text.length() - 1) : 0;
/* 818 */     int j = reverse ? -1 : 1;
/* 819 */     boolean flag = false;
/* 820 */     boolean flag1 = false;
/*     */     int k;
/* 822 */     for (k = i; k >= 0 && k < text.length() && f < width; k += j) {
/* 823 */       char c0 = text.charAt(k);
/* 824 */       float f1 = getCharWidth(c0);
/*     */       
/* 826 */       if (flag) {
/* 827 */         flag = false;
/*     */         
/* 829 */         if (c0 != 'l' && c0 != 'L') {
/* 830 */           if (c0 == 'r' || c0 == 'R') {
/* 831 */             flag1 = false;
/*     */           }
/*     */         } else {
/* 834 */           flag1 = true;
/*     */         } 
/* 836 */       } else if (f1 < 0.0F) {
/* 837 */         flag = true;
/*     */       } else {
/* 839 */         f = (float)(f + f1 / (text.contains("=====") ? 2.2D : 2.0D));
/*     */         
/* 841 */         if (flag1) {
/* 842 */           f++;
/*     */         }
/*     */       } 
/*     */       
/* 846 */       if (f > width) {
/*     */         break;
/*     */       }
/*     */       
/* 850 */       if (reverse) {
/* 851 */         stringbuilder.insert(0, c0);
/*     */       } else {
/* 853 */         stringbuilder.append(c0);
/*     */       } 
/*     */     } 
/* 856 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\cfont\CFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */