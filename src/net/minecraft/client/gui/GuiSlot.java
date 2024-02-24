/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public abstract class GuiSlot
/*     */ {
/*     */   protected final Minecraft mc;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int top;
/*     */   protected int bottom;
/*     */   protected int right;
/*     */   protected int left;
/*     */   protected final int slotHeight;
/*     */   private int scrollUpButtonID;
/*     */   private int scrollDownButtonID;
/*     */   protected int mouseX;
/*     */   protected int mouseY;
/*     */   protected boolean field_148163_i = true;
/*  40 */   protected int initialClickY = -2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float scrollMultiplier;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float amountScrolled;
/*     */ 
/*     */ 
/*     */   
/*  52 */   protected int selectedElement = -1;
/*     */   
/*     */   protected long lastClicked;
/*     */   
/*     */   protected boolean field_178041_q = true;
/*     */   
/*     */   protected boolean showSelectionBox = true;
/*     */   
/*     */   protected boolean hasListHeader;
/*     */   
/*     */   protected int headerPadding;
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */   
/*     */   public GuiSlot(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
/*  68 */     this.mc = mcIn;
/*  69 */     this.width = width;
/*  70 */     this.height = height;
/*  71 */     this.top = topIn;
/*  72 */     this.bottom = bottomIn;
/*  73 */     this.slotHeight = slotHeightIn;
/*  74 */     this.left = 0;
/*  75 */     this.right = width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn) {
/*  80 */     this.width = widthIn;
/*  81 */     this.height = heightIn;
/*  82 */     this.top = topIn;
/*  83 */     this.bottom = bottomIn;
/*  84 */     this.left = 0;
/*  85 */     this.right = widthIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowSelectionBox(boolean showSelectionBoxIn) {
/*  90 */     this.showSelectionBox = showSelectionBoxIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setHasListHeader(boolean hasListHeaderIn, int headerPaddingIn) {
/*  99 */     this.hasListHeader = hasListHeaderIn;
/* 100 */     this.headerPadding = headerPaddingIn;
/*     */     
/* 102 */     if (!hasListHeaderIn)
/*     */     {
/* 104 */       this.headerPadding = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getSize();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void elementClicked(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isSelected(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 125 */     return getSize() * this.slotHeight + this.headerPadding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawBackground();
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_148132_a(int p_148132_1_, int p_148132_2_) {}
/*     */ 
/*     */   
/*     */   protected void func_148142_b(int p_148142_1_, int p_148142_2_) {}
/*     */ 
/*     */   
/*     */   public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_) {
/* 153 */     int i = this.left + this.width / 2 - getListWidth() / 2;
/* 154 */     int j = this.left + this.width / 2 + getListWidth() / 2;
/* 155 */     int k = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 156 */     int l = k / this.slotHeight;
/* 157 */     return (p_148124_1_ < getScrollBarX() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < getSize()) ? l : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScrollButtons(int scrollUpButtonIDIn, int scrollDownButtonIDIn) {
/* 165 */     this.scrollUpButtonID = scrollUpButtonIDIn;
/* 166 */     this.scrollDownButtonID = scrollDownButtonIDIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bindAmountScrolled() {
/* 174 */     this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0F, func_148135_f());
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_148135_f() {
/* 179 */     return Math.max(0, getContentHeight() - this.bottom - this.top - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmountScrolled() {
/* 187 */     return (int)this.amountScrolled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseYWithinSlotBounds(int p_148141_1_) {
/* 192 */     return (p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrollBy(int amount) {
/* 200 */     this.amountScrolled += amount;
/* 201 */     bindAmountScrolled();
/* 202 */     this.initialClickY = -2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(GuiButton button) {
/* 207 */     if (button.enabled)
/*     */     {
/* 209 */       if (button.id == this.scrollUpButtonID) {
/*     */         
/* 211 */         this.amountScrolled -= ((this.slotHeight << 1) / 3);
/* 212 */         this.initialClickY = -2;
/* 213 */         bindAmountScrolled();
/*     */       }
/* 215 */       else if (button.id == this.scrollDownButtonID) {
/*     */         
/* 217 */         this.amountScrolled += ((this.slotHeight << 1) / 3);
/* 218 */         this.initialClickY = -2;
/* 219 */         bindAmountScrolled();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
/* 225 */     if (this.field_178041_q) {
/* 226 */       this.mouseX = mouseXIn;
/* 227 */       this.mouseY = mouseYIn;
/* 228 */       drawBackground();
/* 229 */       int i = getScrollBarX();
/* 230 */       int j = i + 6;
/* 231 */       bindAmountScrolled();
/* 232 */       GlStateManager.disableLighting();
/* 233 */       GlStateManager.disableFog();
/* 234 */       Tessellator tessellator = Tessellator.getInstance();
/* 235 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 236 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 237 */       int l = this.top + 4 - (int)this.amountScrolled;
/* 238 */       if (this.hasListHeader) {
/* 239 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/* 242 */       RenderUtil.makeScissorBox(this.left, this.top, this.right, this.bottom);
/*     */       
/* 244 */       GL11.glEnable(3089);
/*     */       
/* 246 */       drawSelectionBox(k, l + 2, mouseXIn, mouseYIn + 2);
/*     */       
/* 248 */       GL11.glDisable(3089);
/*     */       
/* 250 */       GlStateManager.disableDepth();
/* 251 */       int i1 = 4;
/*     */ 
/*     */       
/* 254 */       ScaledResolution scaledResolution = new ScaledResolution(this.mc);
/* 255 */       Gui.drawRect(0, 0, scaledResolution.getScaledWidth(), this.top, -2147483648);
/* 256 */       Gui.drawRect(0, this.bottom, scaledResolution.getScaledWidth(), this.height, -2147483648);
/*     */       
/* 258 */       GlStateManager.enableBlend();
/* 259 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/* 260 */       GlStateManager.disableAlpha();
/* 261 */       GlStateManager.shadeModel(7425);
/* 262 */       GlStateManager.disableTexture2D();
/* 263 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 264 */       worldrenderer.pos(this.left, (this.top + i1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 265 */       worldrenderer.pos(this.right, (this.top + i1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 266 */       worldrenderer.pos(this.right, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 267 */       worldrenderer.pos(this.left, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 268 */       tessellator.draw();
/* 269 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 270 */       worldrenderer.pos(this.left, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 271 */       worldrenderer.pos(this.right, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 272 */       worldrenderer.pos(this.right, (this.bottom - i1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 273 */       worldrenderer.pos(this.left, (this.bottom - i1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 274 */       tessellator.draw();
/* 275 */       int j1 = func_148135_f();
/* 276 */       if (j1 > 0) {
/* 277 */         int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 278 */         k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
/* 279 */         int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
/* 280 */         if (l1 < this.top) {
/* 281 */           l1 = this.top;
/*     */         }
/*     */         
/* 284 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 285 */         worldrenderer.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 286 */         worldrenderer.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 287 */         worldrenderer.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 288 */         worldrenderer.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 289 */         tessellator.draw();
/* 290 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 291 */         worldrenderer.pos(i, (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 292 */         worldrenderer.pos(j, (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 293 */         worldrenderer.pos(j, l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 294 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 295 */         tessellator.draw();
/* 296 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 297 */         worldrenderer.pos(i, (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 298 */         worldrenderer.pos((j - 1), (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 299 */         worldrenderer.pos((j - 1), l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 300 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 301 */         tessellator.draw();
/*     */       } 
/*     */       
/* 304 */       func_148142_b(mouseXIn, mouseYIn);
/* 305 */       GlStateManager.enableTexture2D();
/* 306 */       GlStateManager.shadeModel(7424);
/* 307 */       GlStateManager.enableAlpha();
/* 308 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() {
/* 314 */     if (isMouseYWithinSlotBounds(this.mouseY)) {
/*     */       
/* 316 */       if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */         
/* 318 */         int i = (this.width - getListWidth()) / 2;
/* 319 */         int j = (this.width + getListWidth()) / 2;
/* 320 */         int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 321 */         int l = k / this.slotHeight;
/*     */         
/* 323 */         if (l < getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
/*     */           
/* 325 */           elementClicked(l, false, this.mouseX, this.mouseY);
/* 326 */           this.selectedElement = l;
/*     */         }
/* 328 */         else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
/*     */           
/* 330 */           func_148132_a(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
/*     */         } 
/*     */       } 
/*     */       
/* 334 */       if (Mouse.isButtonDown(0) && this.enabled) {
/*     */         
/* 336 */         if (this.initialClickY != -1) {
/*     */           
/* 338 */           if (this.initialClickY >= 0)
/*     */           {
/* 340 */             this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
/* 341 */             this.initialClickY = this.mouseY;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 346 */           boolean flag1 = true;
/*     */           
/* 348 */           if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */             
/* 350 */             int j2 = (this.width - getListWidth()) / 2;
/* 351 */             int k2 = (this.width + getListWidth()) / 2;
/* 352 */             int l2 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 353 */             int i1 = l2 / this.slotHeight;
/*     */             
/* 355 */             if (i1 < getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0) {
/*     */               
/* 357 */               boolean flag = (i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L);
/* 358 */               elementClicked(i1, flag, this.mouseX, this.mouseY);
/* 359 */               this.selectedElement = i1;
/* 360 */               this.lastClicked = Minecraft.getSystemTime();
/*     */             }
/* 362 */             else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0) {
/*     */               
/* 364 */               func_148132_a(this.mouseX - j2, this.mouseY - this.top + (int)this.amountScrolled - 4);
/* 365 */               flag1 = false;
/*     */             } 
/*     */             
/* 368 */             int i3 = getScrollBarX();
/* 369 */             int j1 = i3 + 6;
/*     */             
/* 371 */             if (this.mouseX >= i3 && this.mouseX <= j1) {
/*     */               
/* 373 */               this.scrollMultiplier = -1.0F;
/* 374 */               int k1 = func_148135_f();
/*     */               
/* 376 */               if (k1 < 1)
/*     */               {
/* 378 */                 k1 = 1;
/*     */               }
/*     */               
/* 381 */               int l1 = (int)(((this.bottom - this.top) * (this.bottom - this.top)) / getContentHeight());
/* 382 */               l1 = MathHelper.clamp_int(l1, 32, this.bottom - this.top - 8);
/* 383 */               this.scrollMultiplier /= (this.bottom - this.top - l1) / k1;
/*     */             }
/*     */             else {
/*     */               
/* 387 */               this.scrollMultiplier = 1.0F;
/*     */             } 
/*     */             
/* 390 */             if (flag1)
/*     */             {
/* 392 */               this.initialClickY = this.mouseY;
/*     */             }
/*     */             else
/*     */             {
/* 396 */               this.initialClickY = -2;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 401 */             this.initialClickY = -2;
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 407 */         this.initialClickY = -1;
/*     */       } 
/*     */       
/* 410 */       int i2 = Mouse.getEventDWheel();
/*     */       
/* 412 */       if (i2 != 0) {
/*     */         
/* 414 */         if (i2 > 0) {
/*     */           
/* 416 */           i2 = -1;
/*     */         }
/* 418 */         else if (i2 < 0) {
/*     */           
/* 420 */           i2 = 1;
/*     */         } 
/*     */         
/* 423 */         this.amountScrolled += (i2 * this.slotHeight / 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabledIn) {
/* 430 */     this.enabled = enabledIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnabled() {
/* 435 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 443 */     return 220;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn) {
/* 451 */     int i = getSize();
/* 452 */     Tessellator tessellator = Tessellator.getInstance();
/* 453 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 455 */     for (int j = 0; j < i; j++) {
/*     */       
/* 457 */       int k = p_148120_2_ + j * this.slotHeight + this.headerPadding;
/* 458 */       int l = this.slotHeight - 4;
/*     */       
/* 460 */       if (k > this.bottom || k + l < this.top)
/*     */       {
/* 462 */         func_178040_a(j, p_148120_1_, k);
/*     */       }
/*     */       
/* 465 */       if (this.showSelectionBox && isSelected(j)) {
/*     */         
/* 467 */         int i1 = this.left + this.width / 2 - getListWidth() / 2;
/* 468 */         int j1 = this.left + this.width / 2 + getListWidth() / 2;
/* 469 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 470 */         GlStateManager.disableTexture2D();
/* 471 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 472 */         worldrenderer.pos(i1, (k + l + 2), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 473 */         worldrenderer.pos(j1, (k + l + 2), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 474 */         worldrenderer.pos(j1, (k - 2), 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 475 */         worldrenderer.pos(i1, (k - 2), 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 476 */         worldrenderer.pos((i1 + 1), (k + l + 1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 477 */         worldrenderer.pos((j1 - 1), (k + l + 1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 478 */         worldrenderer.pos((j1 - 1), (k - 1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 479 */         worldrenderer.pos((i1 + 1), (k - 1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 480 */         tessellator.draw();
/* 481 */         GlStateManager.enableTexture2D();
/*     */       } 
/*     */       
/* 484 */       if (!(this instanceof GuiResourcePackList) || (k >= this.top - this.slotHeight && k <= this.bottom))
/*     */       {
/* 486 */         drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 493 */     return this.width / 2 + 124;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
/* 501 */     Tessellator tessellator = Tessellator.getInstance();
/* 502 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 503 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 504 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 505 */     float f = 32.0F;
/* 506 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 507 */     worldrenderer.pos(this.left, endY, 0.0D).tex(0.0D, (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 508 */     worldrenderer.pos((this.left + this.width), endY, 0.0D).tex((this.width / 32.0F), (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 509 */     worldrenderer.pos((this.left + this.width), startY, 0.0D).tex((this.width / 32.0F), (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 510 */     worldrenderer.pos(this.left, startY, 0.0D).tex(0.0D, (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 511 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlotXBoundsFromLeft(int leftIn) {
/* 519 */     this.left = leftIn;
/* 520 */     this.right = leftIn + this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotHeight() {
/* 525 */     return this.slotHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawContainerBackground(Tessellator p_drawContainerBackground_1_) {
/* 530 */     WorldRenderer worldrenderer = p_drawContainerBackground_1_.getWorldRenderer();
/* 531 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 532 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 533 */     float f = 32.0F;
/* 534 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 535 */     worldrenderer.pos(this.left, this.bottom, 0.0D).tex((this.left / f), ((this.bottom + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 536 */     worldrenderer.pos(this.right, this.bottom, 0.0D).tex((this.right / f), ((this.bottom + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 537 */     worldrenderer.pos(this.right, this.top, 0.0D).tex((this.right / f), ((this.top + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 538 */     worldrenderer.pos(this.left, this.top, 0.0D).tex((this.left / f), ((this.top + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 539 */     p_drawContainerBackground_1_.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */