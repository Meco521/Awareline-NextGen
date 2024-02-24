/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.misc.EventChat;
/*     */ import awareline.main.mod.implement.globals.Chat;
/*     */ import awareline.main.mod.implement.globals.Shader;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.FontUtils;
/*     */ import awareline.main.ui.font.fastuni.FastUniFontRenderer;
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiNewChat extends Gui {
/*  30 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final Minecraft mc;
/*  32 */   private final List<String> sentMessages = Lists.newArrayList();
/*  33 */   private final List<ChatLine> chatLines = Lists.newArrayList();
/*  34 */   private final List<ChatLine> drawnChatLines = Lists.newArrayList();
/*     */   public float percentComplete;
/*     */   public int newLines;
/*  37 */   public long prevMillis = -1L;
/*  38 */   public float chatPos = 20.0F;
/*     */   
/*     */   FastUniFontRenderer chatHDFont;
/*     */   private int scrollPos;
/*     */   
/*     */   public GuiNewChat(Minecraft mcIn) {
/*  44 */     this.mc = mcIn;
/*     */   }
/*     */   private boolean isScrolled; float percent;
/*     */   public static int calculateChatboxWidth(float scale) {
/*  48 */     int i = 320;
/*  49 */     int j = 40;
/*  50 */     return MathHelper.floor_float(scale * (i - j) + j);
/*     */   }
/*     */   
/*     */   public static int calculateChatboxHeight(float scale) {
/*  54 */     int i = 180;
/*  55 */     int j = 20;
/*  56 */     return MathHelper.floor_float(scale * (i - j) + j);
/*     */   }
/*     */   
/*     */   public void updatePercentage(long diff) {
/*  60 */     if (this.percentComplete < 1.0F) {
/*  61 */       this.percentComplete += 0.004F * (float)diff;
/*     */     }
/*  63 */     this.percentComplete = AnimationUtil.clamp(this.percentComplete, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void renderChatBox() {
/*  67 */     ScaledResolution sr = new ScaledResolution(this.mc);
/*  68 */     int updateCounter = this.mc.ingameGUI.getUpdateCounter();
/*     */     
/*  70 */     if (this.drawnChatLines.size() == 0) {
/*     */       return;
/*     */     }
/*  73 */     FastUniFontRenderer fr = this.chatHDFont;
/*     */     
/*  75 */     if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
/*  76 */       int i = getLineCount();
/*  77 */       boolean flag = false;
/*  78 */       int j = 0;
/*  79 */       int k = this.drawnChatLines.size();
/*  80 */       float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
/*     */       
/*  82 */       if (k > 0) {
/*  83 */         if (getChatOpen()) {
/*  84 */           flag = true;
/*     */         }
/*     */         
/*  87 */         float f1 = getChatScale();
/*  88 */         int l = MathHelper.ceiling_float_int(getChatWidth() / f1);
/*  89 */         GlStateManager.pushMatrix();
/*  90 */         if (!this.isScrolled) {
/*  91 */           GlStateManager.translate(2.0F, (sr.getScaledHeight() - 48) - 2.5F + this.chatPos - 16.0F * GuiChat.openingAnimation
/*  92 */               .getOutput().floatValue() + (9.0F - 9.0F * this.percent) * f1, 0.0F);
/*     */         }
/*  94 */         GlStateManager.scale(f1, f1, 1.0F); int i1;
/*  95 */         for (i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; i1++) {
/*  96 */           ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos);
/*  97 */           if (chatline != null && (updateCounter - chatline.getUpdatedCounter() < 200 || flag)) {
/*  98 */             double d0 = (updateCounter - chatline.getUpdatedCounter()) / 200.0D;
/*  99 */             d0 = 1.0D - d0;
/* 100 */             d0 *= 10.0D;
/* 101 */             d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
/* 102 */             d0 *= d0;
/* 103 */             int l1 = (int)(255.0D * d0);
/*     */             
/* 105 */             if (flag) {
/* 106 */               l1 = 255;
/*     */             }
/*     */             
/* 109 */             l1 = (int)(l1 * f);
/* 110 */             j++;
/*     */             
/* 112 */             if (l1 > 3) {
/* 113 */               int i2 = 0;
/* 114 */               int m = -i1 * 9;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         j = 0;
/* 126 */         for (i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; i1++) {
/* 127 */           ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos);
/*     */           
/* 129 */           if (chatline != null) {
/* 130 */             int j1 = updateCounter - chatline.getUpdatedCounter();
/*     */             
/* 132 */             if (j1 < 200 || flag) {
/* 133 */               double d0 = j1 / 200.0D;
/* 134 */               d0 = 1.0D - d0;
/* 135 */               d0 *= 10.0D;
/* 136 */               d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
/* 137 */               d0 *= d0;
/* 138 */               int l1 = (int)(255.0D * d0);
/*     */               
/* 140 */               if (flag) {
/* 141 */                 l1 = 255;
/*     */               }
/*     */               
/* 144 */               l1 = (int)(l1 * f);
/* 145 */               j++;
/*     */               
/* 147 */               if (l1 > 3) {
/* 148 */                 int i2 = 0;
/* 149 */                 int j2 = -i1 * 9;
/*     */                 
/* 151 */                 drawRect(i2, j2 - 9, i2 + l + 4, j2, ColorUtil.applyOpacity(Color.BLACK, Math.max(1.0F, l1 / 127.5F) - 1.0F).getRGB());
/* 152 */                 String str = chatline.getChatComponent().getFormattedText();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 159 */         if (flag) {
/* 160 */           int k2 = (int)fr.getHeight();
/* 161 */           GlStateManager.translate(-3.0F, 0.0F, 0.0F);
/* 162 */           int l2 = k * k2 + k;
/* 163 */           int i3 = j * k2 + j;
/* 164 */           int j3 = this.scrollPos * i3 / k;
/* 165 */           int k1 = i3 * i3 / l2;
/*     */           
/* 167 */           if (l2 != i3) {
/* 168 */             int k3 = (j3 > 0) ? 170 : 96;
/* 169 */             int l3 = this.isScrolled ? 13382451 : 3355562;
/* 170 */             drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
/* 171 */             drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
/*     */           } 
/*     */         } 
/*     */         
/* 175 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawChat(int updateCounter) {
/* 183 */     this.chatPos = ((Boolean)HUD.getInstance.music.get()).booleanValue() ? (this.mc.ingameGUI.getChatGUI().getChatOpen() ? -17.0F : -20.0F) : 20.0F;
/* 184 */     if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
/* 185 */       if (Chat.getInstance.isEnabled() && Chat.getInstance.animations.is("Height") && 
/* 186 */         this.prevMillis == -1L) {
/* 187 */         this.prevMillis = System.currentTimeMillis();
/*     */         
/*     */         return;
/*     */       } 
/* 191 */       if (((Boolean)Chat.getInstance.enableFont.get()).booleanValue()) {
/* 192 */         this.chatHDFont = FontLoader.PF17;
/*     */       } else {
/* 194 */         this.chatHDFont = null;
/*     */       } 
/* 196 */       long current = System.currentTimeMillis();
/* 197 */       long diff = current - this.prevMillis;
/* 198 */       if (Chat.getInstance.isEnabled() && Chat.getInstance.animations.is("Height")) {
/* 199 */         this.prevMillis = current;
/* 200 */         updatePercentage(diff);
/*     */       } 
/* 202 */       float t = this.percentComplete;
/* 203 */       this.percent = 1.0F - --t * t * t * t;
/* 204 */       if (Chat.getInstance.isEnabled() && Chat.getInstance.animations.is("Height")) {
/* 205 */         this.percent = AnimationUtil.clamp(this.percent, 0.0F, 1.0F);
/*     */       }
/* 207 */       int i = getLineCount();
/* 208 */       boolean flag = false;
/* 209 */       int j = 0;
/* 210 */       float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
/*     */       
/* 212 */       if (!this.drawnChatLines.isEmpty()) {
/* 213 */         if (getChatOpen()) {
/* 214 */           flag = true;
/*     */         }
/*     */         
/* 217 */         float f1 = getChatScale();
/* 218 */         int l = MathHelper.ceiling_float_int(getChatWidth() / f1);
/* 219 */         GlStateManager.pushMatrix();
/* 220 */         if (Chat.getInstance.isEnabled()) {
/* 221 */           if (Chat.getInstance.animations.is("Height")) {
/* 222 */             if (!this.isScrolled) {
/* 223 */               GlStateManager.translate(2.0F, this.chatPos - 16.0F * GuiChat.openingAnimation.getOutput().floatValue() + (9.0F - 9.0F * this.percent) * f1, 0.0F);
/*     */             }
/*     */           } else {
/*     */             
/* 227 */             GlStateManager.translate(2.0F, this.chatPos - 16.0F * GuiChat.openingAnimation.getOutput().floatValue(), 0.0F);
/*     */           } 
/*     */         } else {
/* 230 */           GlStateManager.translate(2.0F, this.chatPos - 16.0F * GuiChat.openingAnimation.getOutput().floatValue(), 0.0F);
/*     */         } 
/* 232 */         GlStateManager.scale(f1, f1, 1.0F);
/*     */         
/* 234 */         int size = this.drawnChatLines.size();
/* 235 */         for (int i1 = 0; i1 + this.scrollPos < size && i1 < i; i1++) {
/* 236 */           ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos);
/*     */           
/* 238 */           if (chatline != null) {
/* 239 */             int j1 = updateCounter - chatline.getUpdatedCounter();
/* 240 */             if (j1 < 200 || flag) {
/* 241 */               double d0 = j1 / 200.0D;
/* 242 */               d0 = 1.0D - d0;
/* 243 */               d0 *= 10.0D;
/* 244 */               d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
/* 245 */               d0 *= d0;
/* 246 */               int l1 = (int)(255.0D * d0);
/*     */               
/* 248 */               if (flag) {
/* 249 */                 l1 = 255;
/*     */               }
/*     */               
/* 252 */               l1 = (int)(l1 * f);
/* 253 */               j++;
/* 254 */               float x = 0.0F, y = 0.0F;
/* 255 */               if (l1 > 3) {
/*     */                 
/* 257 */                 int i2 = 0;
/* 258 */                 int j2 = -i1 * 9;
/* 259 */                 if (!Chat.getInstance.isEnabled()) {
/* 260 */                   x = i2;
/* 261 */                   y = j2;
/* 262 */                   drawRect(x, (y - 9.0F), (x + l + 4.0F), y, l1 / 2 << 24);
/*     */                 } else {
/* 264 */                   if (Chat.getInstance.animations.is("Off") || Chat.getInstance.animations.is("Height")) {
/* 265 */                     x = i2;
/* 266 */                     y = j2;
/*     */                   } 
/* 268 */                   if (Chat.getInstance.backgroundShadow.is("Normal")) {
/* 269 */                     if (!Shader.getInstance.isEnabled()) {
/* 270 */                       drawRect(x, (y - (((Boolean)Chat.getInstance.enableFont.get()).booleanValue() ? 11.0F : 9.0F)), (x + l + 4.0F), (y - (((Boolean)Chat.getInstance.enableFont.get()).booleanValue() ? 2.0F : 0.0F)), l1 / 2 << 24);
/*     */                     }
/* 272 */                   } else if (Chat.getInstance.backgroundShadow.is("Visuals")) {
/* 273 */                     String str = chatline.getChatComponent().getFormattedText();
/* 274 */                     if (((Boolean)Chat.getInstance.enableFont.get()).booleanValue()) {
/* 275 */                       RenderUtil.drawCustomImage(x + 1.0F, y - 17.0F, (this.chatHDFont
/* 276 */                           .getStringWidth(str.replaceAll("搂.", "")) + 15), 20.0F, (this.chatHDFont
/* 277 */                           .getStringWidth(str.replaceAll("搂.", "")) + 17), 23.0F, new ResourceLocation("client/arraylistshadows.png"));
/*     */                     } else {
/* 279 */                       RenderUtil.drawCustomImage(x, y - 15.0F, (this.mc.fontRendererObj.getStringWidth(str.replaceAll("搂.", "")) + 15), 20.0F, (this.mc.fontRendererObj
/* 280 */                           .getStringWidth(str.replaceAll("搂.", "")) + 17), 23.0F, new ResourceLocation("client/arraylistshadows.png"));
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/* 285 */                 String s = chatline.getChatComponent().getFormattedText();
/* 286 */                 GlStateManager.enableBlend();
/* 287 */                 if (!Chat.getInstance.isEnabled()) {
/* 288 */                   this.mc.fontRendererObj.drawStringWithShadow(s, (i2 + 2), (j2 - 9), 16777215 + (l1 << 24));
/*     */                 }
/* 290 */                 else if (Chat.getInstance.animations.is("Height")) {
/* 291 */                   if (i1 <= this.newLines) {
/* 292 */                     if (((Boolean)Chat.getInstance.enableFont.get()).booleanValue()) {
/* 293 */                       this.chatHDFont.drawString(s, i2 + 3.0F, (j2 - 12) + (
/* 294 */                           Shader.getInstance.isEnabled() ? 1.0F : 2.0F), 16777215 + ((int)(l1 * this.percent) << 24));
/*     */                     } else {
/*     */                       
/* 297 */                       drawString(s, x + 2.0F, j2 - 9.0F, 16777215 + ((int)(l1 * this.percent) << 24));
/*     */                     }
/*     */                   
/* 300 */                   } else if (((Boolean)Chat.getInstance.enableFont.get()).booleanValue()) {
/* 301 */                     this.chatHDFont.drawString(s, i2 + 3.0F, (j2 - 12) + (
/* 302 */                         Shader.getInstance.isEnabled() ? 1.0F : 2.0F), 16777215 + (l1 << 24));
/*     */                   } else {
/* 304 */                     drawString(s, x + 2.0F, j2 - 9.0F, 16777215 + (l1 << 24));
/*     */                   }
/*     */                 
/* 307 */                 } else if (Chat.getInstance.animations.is("Off")) {
/* 308 */                   if (((Boolean)Chat.getInstance.enableFont.get()).booleanValue()) {
/* 309 */                     this.chatHDFont.drawString(s, i2 + 1.0F, (j2 - 12) + (
/* 310 */                         Shader.getInstance.isEnabled() ? 1.0F : 2.0F), 16777215 + (l1 << 24));
/*     */                   } else {
/* 312 */                     drawString(s, x + 2.0F, j2 - 9.0F, 16777215 + (l1 << 24));
/*     */                   } 
/*     */                 } 
/*     */                 
/* 316 */                 GlStateManager.disableAlpha();
/* 317 */                 GlStateManager.disableBlend();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 323 */         if (flag) {
/* 324 */           float k2 = ((Boolean)Chat.getInstance.enableFont.get()).booleanValue() ? this.chatHDFont.getHeight() : this.mc.fontRendererObj.FONT_HEIGHT;
/* 325 */           GlStateManager.translate(-3.0F, 0.0F, 0.0F);
/* 326 */           float l2 = size * k2 + size;
/* 327 */           float i3 = j * k2 + j;
/* 328 */           float j3 = this.scrollPos * i3 / size;
/* 329 */           float k1 = i3 * i3 / l2;
/*     */           
/* 331 */           if (l2 != i3) {
/* 332 */             int k3 = (j3 > 0.0F) ? 170 : 96;
/* 333 */             int l3 = this.isScrolled ? 13382451 : 3355562;
/* 334 */             if (!Chat.getInstance.isEnabled()) {
/* 335 */               drawRect(0.0D, -j3, 2.0D, (-j3 - k1), l3 + (k3 << 24));
/* 336 */               drawRect(2.0D, -j3, 1.0D, (-j3 - k1), 13421772 + (k3 << 24));
/*     */             }
/* 338 */             else if (Chat.getInstance.backgroundShadow.is("Normal") && !Shader.getInstance.isEnabled()) {
/* 339 */               drawRect(0.0D, -j3, 2.0D, (-j3 - k1), l3 + (k3 << 24));
/* 340 */               drawRect(2.0D, -j3, 1.0D, (-j3 - k1), 13421772 + (k3 << 24));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 346 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int drawString(String text, float x, float y, int color) {
/* 352 */     return this.mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearChatMessages() {
/* 359 */     this.drawnChatLines.clear();
/* 360 */     this.chatLines.clear();
/* 361 */     this.sentMessages.clear();
/*     */   }
/*     */   
/*     */   public void printChatMessage(IChatComponent chatComponent) {
/* 365 */     printChatMessageWithOptionalDeletion(chatComponent, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId) {
/* 372 */     EventChat e = new EventChat(chatComponent.getUnformattedText(), chatComponent, this.drawnChatLines);
/* 373 */     EventManager.call((Event)e);
/* 374 */     if (e.isCancelled()) {
/*     */       return;
/*     */     }
/* 377 */     if (Chat.getInstance.animations.is("Height")) {
/* 378 */       this.percentComplete = 0.0F;
/*     */     }
/* 380 */     setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
/* 381 */     logger.info("[CHAT] " + chatComponent.getUnformattedText());
/*     */   }
/*     */   private void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
/*     */     List<IChatComponent> list;
/* 385 */     if (chatLineId != 0) {
/* 386 */       deleteChatLine(chatLineId);
/*     */     }
/*     */     
/* 389 */     int i = MathHelper.floor_float(getChatWidth() / getChatScale());
/*     */     
/* 391 */     if (((Boolean)Chat.getInstance.enableFont.get()).booleanValue() && Chat.getInstance.isEnabled()) {
/* 392 */       list = FontUtils.splitText(chatComponent, i, this.chatHDFont, false, false);
/*     */     } else {
/* 394 */       list = GuiUtilRenderComponents.splitText(chatComponent, i, this.mc.fontRendererObj, false, false);
/*     */     } 
/*     */     
/* 397 */     if (list == null) {
/*     */       return;
/*     */     }
/* 400 */     boolean flag = getChatOpen();
/* 401 */     if (Chat.getInstance.animations.is("Height")) {
/* 402 */       this.newLines = list.size() - 1;
/*     */     }
/* 404 */     for (IChatComponent ichatcomponent : list) {
/* 405 */       if (flag && this.scrollPos > 0) {
/* 406 */         this.isScrolled = true;
/* 407 */         scroll(1);
/*     */       } 
/*     */       
/* 410 */       this.drawnChatLines.add(0, new ChatLine(updateCounter, ichatcomponent, chatLineId));
/*     */     } 
/*     */     
/*     */     while (true) {
/* 414 */       int size = this.drawnChatLines.size();
/* 415 */       if (size <= 100)
/* 416 */         break;  this.drawnChatLines.remove(size - 1);
/*     */     } 
/*     */     
/* 419 */     if (!displayOnly) {
/* 420 */       this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
/*     */       while (true) {
/* 422 */         int size = this.chatLines.size();
/* 423 */         if (size <= 100)
/* 424 */           break;  this.chatLines.remove(size - 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshChat() {
/* 430 */     this.drawnChatLines.clear();
/* 431 */     resetScroll();
/*     */     
/* 433 */     for (int i = this.chatLines.size() - 1; i >= 0; i--) {
/* 434 */       ChatLine chatline = this.chatLines.get(i);
/* 435 */       setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getSentMessages() {
/* 440 */     return this.sentMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToSentMessages(String message) {
/* 449 */     if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(message)) {
/* 450 */       this.sentMessages.add(message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetScroll() {
/* 458 */     this.scrollPos = 0;
/* 459 */     this.isScrolled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scroll(int amount) {
/* 467 */     this.scrollPos += amount;
/* 468 */     int i = this.drawnChatLines.size();
/*     */     
/* 470 */     if (this.scrollPos > i - getLineCount()) {
/* 471 */       this.scrollPos = i - getLineCount();
/*     */     }
/*     */     
/* 474 */     if (this.scrollPos <= 0) {
/* 475 */       this.scrollPos = 0;
/* 476 */       this.isScrolled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getChatComponent(int mouseX, int mouseY) {
/* 487 */     if (!getChatOpen()) {
/* 488 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 499 */     float i = scaledresolution.getScaleFactor();
/* 500 */     float f = getChatScale();
/* 501 */     float j = mouseX / i - 3.0F;
/* 502 */     float k = mouseY / i - 29.0F;
/* 503 */     j = MathHelper.floor_float(j / f);
/* 504 */     k = MathHelper.floor_float(k / f);
/*     */     
/* 506 */     if (j >= 0.0F && k >= 0.0F) {
/* 507 */       int l = Math.min(getLineCount(), this.drawnChatLines.size());
/*     */       
/* 509 */       if (j <= MathHelper.floor_float(getChatWidth() / getChatScale()) && k < (this.mc.fontRendererObj.FONT_HEIGHT * l + l)) {
/* 510 */         float i1 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
/*     */         
/* 512 */         if (i1 >= 0.0F && i1 < this.drawnChatLines.size()) {
/* 513 */           ChatLine chatline = this.drawnChatLines.get((int)i1);
/* 514 */           int j1 = 0;
/*     */           
/* 516 */           if (((Boolean)Chat.getInstance.enableFont.get()).booleanValue()) {
/* 517 */             for (IChatComponent ichatcomponent : chatline.getChatComponent()) {
/* 518 */               if (ichatcomponent instanceof ChatComponentText) {
/* 519 */                 j1 += this.chatHDFont.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent)
/* 520 */                       .getChatComponentText_TextValue(), false));
/* 521 */                 if (j1 > j) {
/* 522 */                   return ichatcomponent;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } else {
/* 527 */             for (IChatComponent ichatcomponent : chatline.getChatComponent()) {
/* 528 */               if (ichatcomponent instanceof ChatComponentText) {
/* 529 */                 j1 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent)
/* 530 */                       .getChatComponentText_TextValue(), false));
/* 531 */                 if (j1 > j) {
/* 532 */                   return ichatcomponent;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 539 */         return null;
/*     */       } 
/* 541 */       return null;
/*     */     } 
/*     */     
/* 544 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getChatOpen() {
/* 553 */     return this.mc.currentScreen instanceof GuiChat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteChatLine(int id) {
/* 562 */     Iterator<ChatLine> iterator = this.drawnChatLines.iterator();
/*     */     
/* 564 */     while (iterator.hasNext()) {
/* 565 */       ChatLine chatline = iterator.next();
/*     */       
/* 567 */       if (chatline.getChatLineID() == id) {
/* 568 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */     
/* 572 */     iterator = this.chatLines.iterator();
/*     */     
/* 574 */     while (iterator.hasNext()) {
/* 575 */       ChatLine chatline1 = iterator.next();
/*     */       
/* 577 */       if (chatline1.getChatLineID() == id) {
/* 578 */         iterator.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getChatWidth() {
/* 585 */     return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
/*     */   }
/*     */   
/*     */   public int getChatHeight() {
/* 589 */     return calculateChatboxHeight(getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getChatScale() {
/* 596 */     return this.mc.gameSettings.chatScale;
/*     */   }
/*     */   
/*     */   public int getLineCount() {
/* 600 */     return getChatHeight() / 9;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiNewChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */