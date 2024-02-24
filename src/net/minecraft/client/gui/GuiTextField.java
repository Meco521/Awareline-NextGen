/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.font.FontUtils;
/*     */ import awareline.main.ui.font.fontmanager.font.FontManager;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiTextField
/*     */   extends Gui
/*     */ {
/*     */   private final int id;
/*     */   private final FontRenderer fontRendererInstance;
/*     */   public int xPosition;
/*     */   public float yPosition;
/*     */   private final int width;
/*     */   private final int height;
/*  27 */   private String text = "";
/*  28 */   private int maxStringLength = 32;
/*     */ 
/*     */   
/*     */   private int cursorCounter;
/*     */ 
/*     */   
/*     */   private boolean enableBackgroundDrawing = true;
/*     */ 
/*     */   
/*     */   private boolean canLoseFocus = true;
/*     */ 
/*     */   
/*     */   private boolean isFocused;
/*     */ 
/*     */   
/*     */   private boolean isEnabled = true;
/*     */ 
/*     */   
/*     */   private int lineScrollOffset;
/*     */ 
/*     */   
/*     */   private int cursorPosition;
/*     */ 
/*     */   
/*     */   private int selectionEnd;
/*     */ 
/*     */   
/*  55 */   private int enabledColor = 14737632;
/*  56 */   private int disabledColor = 7368816;
/*     */   
/*     */   private boolean visible = true;
/*     */   
/*     */   private GuiPageButtonList.GuiResponder field_175210_x;
/*  61 */   private Predicate<String> validator = Predicates.alwaysTrue();
/*     */ 
/*     */   
/*     */   public GuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
/*  65 */     this.id = componentId;
/*  66 */     this.fontRendererInstance = fontrendererObj;
/*  67 */     this.xPosition = x;
/*  68 */     this.yPosition = y;
/*  69 */     this.width = par5Width;
/*  70 */     this.height = par6Height;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175207_a(GuiPageButtonList.GuiResponder p_175207_1_) {
/*  75 */     this.field_175210_x = p_175207_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCursorCounter() {
/*  83 */     this.cursorCounter++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String p_146180_1_) {
/*  91 */     if (this.validator.apply(p_146180_1_)) {
/*     */       
/*  93 */       if (p_146180_1_.length() > this.maxStringLength) {
/*     */         
/*  95 */         this.text = p_146180_1_.substring(0, this.maxStringLength);
/*     */       }
/*     */       else {
/*     */         
/*  99 */         this.text = p_146180_1_;
/*     */       } 
/*     */       
/* 102 */       setCursorPositionEnd();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 111 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectedText() {
/* 119 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/* 120 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/* 121 */     return this.text.substring(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValidator(Predicate<String> theValidator) {
/* 126 */     this.validator = theValidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeText(String p_146191_1_) {
/* 134 */     String s = "";
/* 135 */     String s1 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
/* 136 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/* 137 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/* 138 */     int k = this.maxStringLength - this.text.length() - i - j;
/* 139 */     int l = 0;
/*     */     
/* 141 */     if (!this.text.isEmpty())
/*     */     {
/* 143 */       s = s + this.text.substring(0, i);
/*     */     }
/*     */     
/* 146 */     if (k < s1.length()) {
/*     */       
/* 148 */       s = s + s1.substring(0, k);
/* 149 */       l = k;
/*     */     }
/*     */     else {
/*     */       
/* 153 */       s = s + s1;
/* 154 */       l = s1.length();
/*     */     } 
/*     */     
/* 157 */     if (!this.text.isEmpty() && j < this.text.length())
/*     */     {
/* 159 */       s = s + this.text.substring(j);
/*     */     }
/*     */     
/* 162 */     if (this.validator.apply(s)) {
/*     */       
/* 164 */       this.text = s;
/* 165 */       moveCursorBy(i - this.selectionEnd + l);
/*     */       
/* 167 */       if (this.field_175210_x != null)
/*     */       {
/* 169 */         this.field_175210_x.func_175319_a(this.id, this.text);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteWords(int p_146177_1_) {
/* 180 */     if (!this.text.isEmpty())
/*     */     {
/* 182 */       if (this.selectionEnd != this.cursorPosition) {
/*     */         
/* 184 */         writeText("");
/*     */       }
/*     */       else {
/*     */         
/* 188 */         deleteFromCursor(getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteFromCursor(int p_146175_1_) {
/* 198 */     if (!this.text.isEmpty())
/*     */     {
/* 200 */       if (this.selectionEnd != this.cursorPosition) {
/*     */         
/* 202 */         writeText("");
/*     */       }
/*     */       else {
/*     */         
/* 206 */         boolean flag = (p_146175_1_ < 0);
/* 207 */         int i = flag ? (this.cursorPosition + p_146175_1_) : this.cursorPosition;
/* 208 */         int j = flag ? this.cursorPosition : (this.cursorPosition + p_146175_1_);
/* 209 */         String s = "";
/*     */         
/* 211 */         if (i >= 0)
/*     */         {
/* 213 */           s = this.text.substring(0, i);
/*     */         }
/*     */         
/* 216 */         if (j < this.text.length())
/*     */         {
/* 218 */           s = s + this.text.substring(j);
/*     */         }
/*     */         
/* 221 */         if (this.validator.apply(s)) {
/*     */           
/* 223 */           this.text = s;
/*     */           
/* 225 */           if (flag)
/*     */           {
/* 227 */             moveCursorBy(p_146175_1_);
/*     */           }
/*     */           
/* 230 */           if (this.field_175210_x != null)
/*     */           {
/* 232 */             this.field_175210_x.func_175319_a(this.id, this.text);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 241 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNthWordFromCursor(int p_146187_1_) {
/* 249 */     return getNthWordFromPos(p_146187_1_, this.cursorPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNthWordFromPos(int p_146183_1_, int p_146183_2_) {
/* 257 */     return func_146197_a(p_146183_1_, p_146183_2_, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
/* 262 */     int i = p_146197_2_;
/* 263 */     boolean flag = (p_146197_1_ < 0);
/* 264 */     int j = Math.abs(p_146197_1_);
/*     */     
/* 266 */     for (int k = 0; k < j; k++) {
/*     */       
/* 268 */       if (!flag) {
/*     */         
/* 270 */         int l = this.text.length();
/* 271 */         i = this.text.indexOf(' ', i);
/*     */         
/* 273 */         if (i == -1)
/*     */         {
/* 275 */           i = l;
/*     */         }
/*     */         else
/*     */         {
/* 279 */           while (p_146197_3_ && i < l && this.text.charAt(i) == ' ')
/*     */           {
/* 281 */             i++;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 287 */         while (p_146197_3_ && i > 0 && this.text.charAt(i - 1) == ' ')
/*     */         {
/* 289 */           i--;
/*     */         }
/*     */         
/* 292 */         while (i > 0 && this.text.charAt(i - 1) != ' ')
/*     */         {
/* 294 */           i--;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveCursorBy(int p_146182_1_) {
/* 307 */     setCursorPosition(this.selectionEnd + p_146182_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPosition(int p_146190_1_) {
/* 315 */     this.cursorPosition = p_146190_1_;
/* 316 */     int i = this.text.length();
/* 317 */     this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, i);
/* 318 */     setSelectionPos(this.cursorPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPositionZero() {
/* 326 */     setCursorPosition(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPositionEnd() {
/* 334 */     setCursorPosition(this.text.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
/* 342 */     if (!this.isFocused)
/*     */     {
/* 344 */       return false;
/*     */     }
/* 346 */     if (GuiScreen.isKeyComboCtrlA(p_146201_2_)) {
/*     */       
/* 348 */       setCursorPositionEnd();
/* 349 */       setSelectionPos(0);
/* 350 */       return true;
/*     */     } 
/* 352 */     if (GuiScreen.isKeyComboCtrlC(p_146201_2_)) {
/*     */       
/* 354 */       GuiScreen.setClipboardString(getSelectedText());
/* 355 */       return true;
/*     */     } 
/* 357 */     if (GuiScreen.isKeyComboCtrlV(p_146201_2_)) {
/*     */       
/* 359 */       if (this.isEnabled)
/*     */       {
/* 361 */         writeText(GuiScreen.getClipboardString());
/*     */       }
/*     */       
/* 364 */       return true;
/*     */     } 
/* 366 */     if (GuiScreen.isKeyComboCtrlX(p_146201_2_)) {
/*     */       
/* 368 */       GuiScreen.setClipboardString(getSelectedText());
/*     */       
/* 370 */       if (this.isEnabled)
/*     */       {
/* 372 */         writeText("");
/*     */       }
/*     */       
/* 375 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 379 */     switch (p_146201_2_) {
/*     */       
/*     */       case 14:
/* 382 */         if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 384 */           if (this.isEnabled)
/*     */           {
/* 386 */             deleteWords(-1);
/*     */           }
/*     */         }
/* 389 */         else if (this.isEnabled) {
/*     */           
/* 391 */           deleteFromCursor(-1);
/*     */         } 
/*     */         
/* 394 */         return true;
/*     */       
/*     */       case 199:
/* 397 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 399 */           setSelectionPos(0);
/*     */         }
/*     */         else {
/*     */           
/* 403 */           setCursorPositionZero();
/*     */         } 
/*     */         
/* 406 */         return true;
/*     */       
/*     */       case 203:
/* 409 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 411 */           if (GuiScreen.isCtrlKeyDown())
/*     */           {
/* 413 */             setSelectionPos(getNthWordFromPos(-1, this.selectionEnd));
/*     */           }
/*     */           else
/*     */           {
/* 417 */             setSelectionPos(this.selectionEnd - 1);
/*     */           }
/*     */         
/* 420 */         } else if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 422 */           setCursorPosition(getNthWordFromCursor(-1));
/*     */         }
/*     */         else {
/*     */           
/* 426 */           moveCursorBy(-1);
/*     */         } 
/*     */         
/* 429 */         return true;
/*     */       
/*     */       case 205:
/* 432 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 434 */           if (GuiScreen.isCtrlKeyDown())
/*     */           {
/* 436 */             setSelectionPos(getNthWordFromPos(1, this.selectionEnd));
/*     */           }
/*     */           else
/*     */           {
/* 440 */             setSelectionPos(this.selectionEnd + 1);
/*     */           }
/*     */         
/* 443 */         } else if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 445 */           setCursorPosition(getNthWordFromCursor(1));
/*     */         }
/*     */         else {
/*     */           
/* 449 */           moveCursorBy(1);
/*     */         } 
/*     */         
/* 452 */         return true;
/*     */       
/*     */       case 207:
/* 455 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 457 */           setSelectionPos(this.text.length());
/*     */         }
/*     */         else {
/*     */           
/* 461 */           setCursorPositionEnd();
/*     */         } 
/*     */         
/* 464 */         return true;
/*     */       
/*     */       case 211:
/* 467 */         if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 469 */           if (this.isEnabled)
/*     */           {
/* 471 */             deleteWords(1);
/*     */           }
/*     */         }
/* 474 */         else if (this.isEnabled) {
/*     */           
/* 476 */           deleteFromCursor(1);
/*     */         } 
/*     */         
/* 479 */         return true;
/*     */     } 
/*     */     
/* 482 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_)) {
/*     */       
/* 484 */       if (this.isEnabled)
/*     */       {
/* 486 */         writeText(Character.toString(p_146201_1_));
/*     */       }
/*     */       
/* 489 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 493 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
/* 504 */     boolean flag = (p_146192_1_ >= this.xPosition && p_146192_1_ < this.xPosition + this.width && p_146192_2_ >= this.yPosition && p_146192_2_ < this.yPosition + this.height);
/*     */     
/* 506 */     if (this.canLoseFocus)
/*     */     {
/* 508 */       setFocused(flag);
/*     */     }
/*     */     
/* 511 */     if (this.isFocused && flag && p_146192_3_ == 0) {
/*     */       
/* 513 */       int i = p_146192_1_ - this.xPosition;
/*     */       
/* 515 */       if (this.enableBackgroundDrawing)
/*     */       {
/* 517 */         i -= 4;
/*     */       }
/*     */       
/* 520 */       String s = FontUtils.trimStringToWidth(FontManager.default16, this.text.substring(this.lineScrollOffset), getWidth());
/* 521 */       setCursorPosition(FontUtils.trimStringToWidth(FontManager.default16, s, i).length() + this.lineScrollOffset);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTextBox() {
/* 532 */     if (this.visible) {
/*     */       
/* 534 */       if (this.enableBackgroundDrawing) {
/*     */         
/* 536 */         drawRect((this.xPosition - 1), (this.yPosition - 1.0F), (this.xPosition + this.width + 1), (this.yPosition + this.height + 1.0F), -6250336);
/* 537 */         drawRect(this.xPosition, this.yPosition, (this.xPosition + this.width), (this.yPosition + this.height), -16777216);
/*     */       } 
/*     */       
/* 540 */       int i = this.isEnabled ? this.enabledColor : this.disabledColor;
/* 541 */       int j = this.cursorPosition - this.lineScrollOffset;
/* 542 */       int k = this.selectionEnd - this.lineScrollOffset;
/* 543 */       String s = FontUtils.trimStringToWidth(FontManager.default16, this.text.substring(this.lineScrollOffset), getWidth());
/*     */       
/* 545 */       boolean flag = (j >= 0 && j <= s.length());
/* 546 */       boolean flag1 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag);
/* 547 */       int l = this.enableBackgroundDrawing ? (this.xPosition + 4) : this.xPosition;
/* 548 */       float i1 = this.enableBackgroundDrawing ? (this.yPosition + ((this.height - 8) / 2)) : this.yPosition;
/* 549 */       int j1 = l;
/*     */       
/* 551 */       if (k > s.length())
/*     */       {
/* 553 */         k = s.length();
/*     */       }
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
/* 566 */       if (s.length() > 0) {
/*     */         
/* 568 */         String s1 = flag ? s.substring(0, j) : s;
/*     */         
/* 570 */         FontManager.default16.drawString(s1, l, i1, i);
/* 571 */         j1 += FontManager.default16.getStringWidth(s1);
/*     */       } 
/*     */       
/* 574 */       boolean flag2 = (this.cursorPosition < this.text.length() || this.text.length() >= this.maxStringLength);
/* 575 */       int k1 = j1;
/*     */       
/* 577 */       if (!flag) {
/*     */         
/* 579 */         k1 = (j > 0) ? (l + this.width) : l;
/*     */       }
/* 581 */       else if (flag2) {
/*     */         
/* 583 */         k1 = j1 - 1;
/* 584 */         j1--;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 595 */       if (s.length() > 0 && flag && j < s.length())
/*     */       {
/* 597 */         FontManager.default16.drawString(s.substring(j), (j1 + 1), i1, i);
/*     */       }
/*     */       
/* 600 */       if (flag1)
/*     */       {
/* 602 */         if (flag2) {
/*     */           
/* 604 */           Gui.drawRect(k1, (i1 - 1.0F), (k1 + 1), (i1 + 1.0F + this.fontRendererInstance.FONT_HEIGHT), -3092272);
/*     */         }
/*     */         else {
/*     */           
/* 608 */           FontManager.default16.drawString("_", k1, (i1 - 1.0F), i);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 613 */       if (k != j) {
/*     */         
/* 615 */         double l1 = (l + FontManager.default16.getStringWidth(s.substring(0, k)));
/*     */         
/* 617 */         drawCursorVertical(k1, (i1 - 1.0F), l1 - 1.0D, (i1 + 1.0F + this.fontRendererInstance.FONT_HEIGHT));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawCursorVertical(double p_146188_1_, double p_146188_2_, double p_146188_3_, double p_146188_4_) {
/* 623 */     if (p_146188_1_ < p_146188_3_) {
/*     */       
/* 625 */       double i = p_146188_1_;
/* 626 */       p_146188_1_ = p_146188_3_;
/* 627 */       p_146188_3_ = i;
/*     */     } 
/*     */     
/* 630 */     if (p_146188_2_ < p_146188_4_) {
/*     */       
/* 632 */       double j = p_146188_2_;
/* 633 */       p_146188_2_ = p_146188_4_;
/* 634 */       p_146188_4_ = j;
/*     */     } 
/*     */     
/* 637 */     if (p_146188_3_ > (this.xPosition + this.width))
/*     */     {
/* 639 */       p_146188_3_ = (this.xPosition + this.width);
/*     */     }
/*     */     
/* 642 */     if (p_146188_1_ > (this.xPosition + this.width))
/*     */     {
/* 644 */       p_146188_1_ = (this.xPosition + this.width);
/*     */     }
/*     */     
/* 647 */     Tessellator tessellator = Tessellator.getInstance();
/* 648 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 649 */     GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
/* 650 */     GlStateManager.disableTexture2D();
/* 651 */     GlStateManager.enableColorLogic();
/* 652 */     GlStateManager.colorLogicOp(5387);
/* 653 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 654 */     worldrenderer.pos(p_146188_1_, p_146188_4_, 0.0D).endVertex();
/* 655 */     worldrenderer.pos(p_146188_3_, p_146188_4_, 0.0D).endVertex();
/* 656 */     worldrenderer.pos(p_146188_3_, p_146188_2_, 0.0D).endVertex();
/* 657 */     worldrenderer.pos(p_146188_1_, p_146188_2_, 0.0D).endVertex();
/* 658 */     tessellator.draw();
/* 659 */     GlStateManager.disableColorLogic();
/* 660 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxStringLength(int p_146203_1_) {
/* 666 */     this.maxStringLength = p_146203_1_;
/*     */     
/* 668 */     if (this.text.length() > p_146203_1_)
/*     */     {
/* 670 */       this.text = this.text.substring(0, p_146203_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStringLength() {
/* 679 */     return this.maxStringLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCursorPosition() {
/* 687 */     return this.cursorPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getEnableBackgroundDrawing() {
/* 695 */     return this.enableBackgroundDrawing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnableBackgroundDrawing(boolean p_146185_1_) {
/* 703 */     this.enableBackgroundDrawing = p_146185_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(int p_146193_1_) {
/* 711 */     this.enabledColor = p_146193_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisabledTextColour(int p_146204_1_) {
/* 716 */     this.disabledColor = p_146204_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocused(boolean p_146195_1_) {
/* 724 */     if (p_146195_1_ && !this.isFocused)
/*     */     {
/* 726 */       this.cursorCounter = 0;
/*     */     }
/*     */     
/* 729 */     this.isFocused = p_146195_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/* 737 */     return this.isFocused;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean p_146184_1_) {
/* 742 */     this.isEnabled = p_146184_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectionEnd() {
/* 750 */     return this.selectionEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 758 */     return this.enableBackgroundDrawing ? (this.width - 8) : this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionPos(int p_146199_1_) {
/* 765 */     int i = this.text.length();
/*     */     
/* 767 */     if (p_146199_1_ > i) {
/* 768 */       p_146199_1_ = i;
/*     */     }
/*     */     
/* 771 */     if (p_146199_1_ < 0) {
/* 772 */       p_146199_1_ = 0;
/*     */     }
/*     */     
/* 775 */     this.selectionEnd = p_146199_1_;
/*     */     
/* 777 */     if (this.lineScrollOffset > i) {
/* 778 */       this.lineScrollOffset = i;
/*     */     }
/*     */     
/* 781 */     double j = getWidth();
/* 782 */     String s = FontUtils.trimStringToWidth(FontManager.default16, this.text.substring(this.lineScrollOffset), (int)j);
/* 783 */     int k = s.length() + this.lineScrollOffset;
/*     */     
/* 785 */     if (p_146199_1_ == this.lineScrollOffset) {
/* 786 */       this.lineScrollOffset -= FontUtils.trimStringToWidth(FontManager.default16, this.text, (int)j, true).length();
/*     */     }
/*     */     
/* 789 */     if (p_146199_1_ > k) {
/* 790 */       this.lineScrollOffset += p_146199_1_ - k;
/* 791 */     } else if (p_146199_1_ <= this.lineScrollOffset) {
/* 792 */       this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
/*     */     } 
/*     */     
/* 795 */     this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanLoseFocus(boolean p_146205_1_) {
/* 804 */     this.canLoseFocus = p_146205_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getVisible() {
/* 812 */     return this.visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean p_146189_1_) {
/* 820 */     this.visible = p_146189_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiTextField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */