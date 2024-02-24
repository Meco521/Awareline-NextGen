/*     */ package net.minecraft.client.gui;
/*     */ import com.google.gson.JsonParseException;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemEditableBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenBook extends GuiScreen {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*  30 */   static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
/*     */ 
/*     */   
/*     */   private final EntityPlayer editingPlayer;
/*     */ 
/*     */   
/*     */   private final ItemStack bookObj;
/*     */ 
/*     */   
/*     */   private final boolean bookIsUnsigned;
/*     */ 
/*     */   
/*     */   private boolean bookIsModified;
/*     */ 
/*     */   
/*     */   private boolean bookGettingSigned;
/*     */   
/*     */   private int updateCount;
/*     */   
/*  49 */   private final int bookImageWidth = 192;
/*  50 */   private final int bookImageHeight = 192;
/*  51 */   private int bookTotalPages = 1;
/*     */   private int currPage;
/*     */   private NBTTagList bookPages;
/*  54 */   private String bookTitle = "";
/*     */   private List<IChatComponent> field_175386_A;
/*  56 */   private int field_175387_B = -1;
/*     */   
/*     */   private NextPageButton buttonNextPage;
/*     */   
/*     */   private NextPageButton buttonPreviousPage;
/*     */   
/*     */   private GuiButton buttonDone;
/*     */   private GuiButton buttonSign;
/*     */   private GuiButton buttonFinalize;
/*     */   private GuiButton buttonCancel;
/*     */   
/*     */   public GuiScreenBook(EntityPlayer player, ItemStack book, boolean isUnsigned) {
/*  68 */     this.editingPlayer = player;
/*  69 */     this.bookObj = book;
/*  70 */     this.bookIsUnsigned = isUnsigned;
/*     */     
/*  72 */     if (book.hasTagCompound()) {
/*     */       
/*  74 */       NBTTagCompound nbttagcompound = book.getTagCompound();
/*  75 */       this.bookPages = nbttagcompound.getTagList("pages", 8);
/*     */       
/*  77 */       if (this.bookPages != null) {
/*     */         
/*  79 */         this.bookPages = (NBTTagList)this.bookPages.copy();
/*  80 */         this.bookTotalPages = this.bookPages.tagCount();
/*     */         
/*  82 */         if (this.bookTotalPages < 1)
/*     */         {
/*  84 */           this.bookTotalPages = 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     if (this.bookPages == null && isUnsigned) {
/*     */       
/*  91 */       this.bookPages = new NBTTagList();
/*  92 */       this.bookPages.appendTag((NBTBase)new NBTTagString(""));
/*  93 */       this.bookTotalPages = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 102 */     super.updateScreen();
/* 103 */     this.updateCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 112 */     this.buttonList.clear();
/* 113 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 115 */     if (this.bookIsUnsigned) {
/*     */       
/* 117 */       getClass(); this.buttonList.add(this.buttonSign = new GuiButton(3, this.width / 2 - 100, 4 + 192, 98, 20, I18n.format("book.signButton", new Object[0])));
/* 118 */       getClass(); this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 + 2, 4 + 192, 98, 20, I18n.format("gui.done", new Object[0])));
/* 119 */       getClass(); this.buttonList.add(this.buttonFinalize = new GuiButton(5, this.width / 2 - 100, 4 + 192, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
/* 120 */       getClass(); this.buttonList.add(this.buttonCancel = new GuiButton(4, this.width / 2 + 2, 4 + 192, 98, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     }
/*     */     else {
/*     */       
/* 124 */       getClass(); this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + 192, 200, 20, I18n.format("gui.done", new Object[0])));
/*     */     } 
/*     */     
/* 127 */     getClass(); int i = (this.width - 192) / 2;
/* 128 */     int j = 2;
/* 129 */     this.buttonList.add(this.buttonNextPage = new NextPageButton(1, i + 120, j + 154, true));
/* 130 */     this.buttonList.add(this.buttonPreviousPage = new NextPageButton(2, i + 38, j + 154, false));
/* 131 */     updateButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 139 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateButtons() {
/* 144 */     this.buttonNextPage.visible = (!this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned));
/* 145 */     this.buttonPreviousPage.visible = (!this.bookGettingSigned && this.currPage > 0);
/* 146 */     this.buttonDone.visible = (!this.bookIsUnsigned || !this.bookGettingSigned);
/*     */     
/* 148 */     if (this.bookIsUnsigned) {
/*     */       
/* 150 */       this.buttonSign.visible = !this.bookGettingSigned;
/* 151 */       this.buttonCancel.visible = this.bookGettingSigned;
/* 152 */       this.buttonFinalize.visible = this.bookGettingSigned;
/* 153 */       this.buttonFinalize.enabled = !this.bookTitle.trim().isEmpty();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendBookToServer(boolean publish) {
/* 158 */     if (this.bookIsUnsigned && this.bookIsModified)
/*     */     {
/* 160 */       if (this.bookPages != null) {
/*     */         
/* 162 */         while (this.bookPages.tagCount() > 1) {
/*     */           
/* 164 */           String s = this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1);
/*     */           
/* 166 */           if (!s.isEmpty()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 171 */           this.bookPages.removeTag(this.bookPages.tagCount() - 1);
/*     */         } 
/*     */         
/* 174 */         if (this.bookObj.hasTagCompound()) {
/*     */           
/* 176 */           NBTTagCompound nbttagcompound = this.bookObj.getTagCompound();
/* 177 */           nbttagcompound.setTag("pages", (NBTBase)this.bookPages);
/*     */         }
/*     */         else {
/*     */           
/* 181 */           this.bookObj.setTagInfo("pages", (NBTBase)this.bookPages);
/*     */         } 
/*     */         
/* 184 */         String s2 = "MC|BEdit";
/*     */         
/* 186 */         if (publish) {
/*     */           
/* 188 */           s2 = "MC|BSign";
/* 189 */           this.bookObj.setTagInfo("author", (NBTBase)new NBTTagString(this.editingPlayer.getName()));
/* 190 */           this.bookObj.setTagInfo("title", (NBTBase)new NBTTagString(this.bookTitle.trim()));
/*     */           
/* 192 */           for (int i = 0; i < this.bookPages.tagCount(); i++) {
/*     */             
/* 194 */             String s1 = this.bookPages.getStringTagAt(i);
/* 195 */             ChatComponentText chatComponentText = new ChatComponentText(s1);
/* 196 */             s1 = IChatComponent.Serializer.componentToJson((IChatComponent)chatComponentText);
/* 197 */             this.bookPages.set(i, (NBTBase)new NBTTagString(s1));
/*     */           } 
/*     */           
/* 200 */           this.bookObj.setItem(Items.written_book);
/*     */         } 
/*     */         
/* 203 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 204 */         packetbuffer.writeItemStackToBuffer(this.bookObj);
/* 205 */         this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload(s2, packetbuffer));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 215 */     if (button.enabled) {
/*     */       
/* 217 */       if (button.id == 0) {
/*     */         
/* 219 */         this.mc.displayGuiScreen((GuiScreen)null);
/* 220 */         sendBookToServer(false);
/*     */       }
/* 222 */       else if (button.id == 3 && this.bookIsUnsigned) {
/*     */         
/* 224 */         this.bookGettingSigned = true;
/*     */       }
/* 226 */       else if (button.id == 1) {
/*     */         
/* 228 */         if (this.currPage < this.bookTotalPages - 1)
/*     */         {
/* 230 */           this.currPage++;
/*     */         }
/* 232 */         else if (this.bookIsUnsigned)
/*     */         {
/* 234 */           addNewPage();
/*     */           
/* 236 */           if (this.currPage < this.bookTotalPages - 1)
/*     */           {
/* 238 */             this.currPage++;
/*     */           }
/*     */         }
/*     */       
/* 242 */       } else if (button.id == 2) {
/*     */         
/* 244 */         if (this.currPage > 0)
/*     */         {
/* 246 */           this.currPage--;
/*     */         }
/*     */       }
/* 249 */       else if (button.id == 5 && this.bookGettingSigned) {
/*     */         
/* 251 */         sendBookToServer(true);
/* 252 */         this.mc.displayGuiScreen((GuiScreen)null);
/*     */       }
/* 254 */       else if (button.id == 4 && this.bookGettingSigned) {
/*     */         
/* 256 */         this.bookGettingSigned = false;
/*     */       } 
/*     */       
/* 259 */       updateButtons();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addNewPage() {
/* 265 */     if (this.bookPages != null && this.bookPages.tagCount() < 50) {
/*     */       
/* 267 */       this.bookPages.appendTag((NBTBase)new NBTTagString(""));
/* 268 */       this.bookTotalPages++;
/* 269 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 279 */     super.keyTyped(typedChar, keyCode);
/*     */     
/* 281 */     if (this.bookIsUnsigned)
/*     */     {
/* 283 */       if (this.bookGettingSigned) {
/*     */         
/* 285 */         keyTypedInTitle(typedChar, keyCode);
/*     */       }
/*     */       else {
/*     */         
/* 289 */         keyTypedInBook(typedChar, keyCode);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void keyTypedInBook(char typedChar, int keyCode) {
/* 299 */     if (GuiScreen.isKeyComboCtrlV(keyCode)) {
/*     */       
/* 301 */       pageInsertIntoCurrent(GuiScreen.getClipboardString());
/*     */     } else {
/*     */       String s;
/*     */       
/* 305 */       switch (keyCode) {
/*     */         
/*     */         case 14:
/* 308 */           s = pageGetCurrent();
/*     */           
/* 310 */           if (!s.isEmpty())
/*     */           {
/* 312 */             pageSetCurrent(s.substring(0, s.length() - 1));
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case 28:
/*     */         case 156:
/* 319 */           pageInsertIntoCurrent("\n");
/*     */           return;
/*     */       } 
/*     */       
/* 323 */       if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
/*     */       {
/* 325 */         pageInsertIntoCurrent(Character.toString(typedChar));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void keyTypedInTitle(char p_146460_1_, int p_146460_2_) throws IOException {
/* 336 */     switch (p_146460_2_) {
/*     */       
/*     */       case 14:
/* 339 */         if (!this.bookTitle.isEmpty()) {
/*     */           
/* 341 */           this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
/* 342 */           updateButtons();
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case 28:
/*     */       case 156:
/* 349 */         if (!this.bookTitle.isEmpty()) {
/*     */           
/* 351 */           sendBookToServer(true);
/* 352 */           this.mc.displayGuiScreen((GuiScreen)null);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 358 */     if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)) {
/*     */       
/* 360 */       this.bookTitle += p_146460_1_;
/* 361 */       updateButtons();
/* 362 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pageGetCurrent() {
/* 372 */     return (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) ? this.bookPages.getStringTagAt(this.currPage) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pageSetCurrent(String p_146457_1_) {
/* 380 */     if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
/*     */       
/* 382 */       this.bookPages.set(this.currPage, (NBTBase)new NBTTagString(p_146457_1_));
/* 383 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pageInsertIntoCurrent(String p_146459_1_) {
/* 392 */     String s = pageGetCurrent();
/* 393 */     String s1 = s + p_146459_1_;
/* 394 */     int i = this.fontRendererObj.splitStringWidth(s1 + EnumChatFormatting.BLACK + "_", 118);
/*     */     
/* 396 */     if (i <= 128 && s1.length() < 256)
/*     */     {
/* 398 */       pageSetCurrent(s1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 407 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 408 */     this.mc.getTextureManager().bindTexture(bookGuiTextures);
/* 409 */     getClass(); int i = (this.width - 192) / 2;
/* 410 */     int j = 2;
/* 411 */     getClass(); getClass(); drawTexturedModalRect(i, j, 0, 0, 192, 192);
/*     */     
/* 413 */     if (this.bookGettingSigned) {
/*     */       
/* 415 */       String s = this.bookTitle;
/*     */       
/* 417 */       if (this.bookIsUnsigned)
/*     */       {
/* 419 */         if (this.updateCount / 6 % 2 == 0) {
/*     */           
/* 421 */           s = s + EnumChatFormatting.BLACK + "_";
/*     */         }
/*     */         else {
/*     */           
/* 425 */           s = s + EnumChatFormatting.GRAY + "_";
/*     */         } 
/*     */       }
/*     */       
/* 429 */       String s1 = I18n.format("book.editTitle", new Object[0]);
/* 430 */       int k = this.fontRendererObj.getStringWidth(s1);
/* 431 */       this.fontRendererObj.drawString(s1, i + 36 + (116 - k) / 2, j + 16 + 16, 0);
/* 432 */       int l = this.fontRendererObj.getStringWidth(s);
/* 433 */       this.fontRendererObj.drawString(s, i + 36 + (116 - l) / 2, j + 48, 0);
/* 434 */       String s2 = I18n.format("book.byAuthor", new Object[] { this.editingPlayer.getName() });
/* 435 */       int i1 = this.fontRendererObj.getStringWidth(s2);
/* 436 */       this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + s2, i + 36 + (116 - i1) / 2, j + 48 + 10, 0);
/* 437 */       String s3 = I18n.format("book.finalizeWarning", new Object[0]);
/* 438 */       this.fontRendererObj.drawSplitString(s3, i + 36, j + 80, 116, 0);
/*     */     }
/*     */     else {
/*     */       
/* 442 */       String s4 = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(this.currPage + 1), Integer.valueOf(this.bookTotalPages) });
/* 443 */       String s5 = "";
/*     */       
/* 445 */       if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
/*     */       {
/* 447 */         s5 = this.bookPages.getStringTagAt(this.currPage);
/*     */       }
/*     */       
/* 450 */       if (this.bookIsUnsigned) {
/*     */         
/* 452 */         if (this.fontRendererObj.getBidiFlag())
/*     */         {
/* 454 */           s5 = s5 + "_";
/*     */         }
/* 456 */         else if (this.updateCount / 6 % 2 == 0)
/*     */         {
/* 458 */           s5 = s5 + EnumChatFormatting.BLACK + "_";
/*     */         }
/*     */         else
/*     */         {
/* 462 */           s5 = s5 + EnumChatFormatting.GRAY + "_";
/*     */         }
/*     */       
/* 465 */       } else if (this.field_175387_B != this.currPage) {
/*     */         
/* 467 */         if (ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
/*     */           
/*     */           try
/*     */           {
/* 471 */             IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s5);
/* 472 */             this.field_175386_A = (ichatcomponent != null) ? GuiUtilRenderComponents.splitText(ichatcomponent, 116, this.fontRendererObj, true, true) : null;
/*     */           }
/* 474 */           catch (JsonParseException var13)
/*     */           {
/* 476 */             this.field_175386_A = null;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 481 */           ChatComponentText chatcomponenttext = new ChatComponentText(EnumChatFormatting.DARK_RED.toString() + "* Invalid book tag *");
/* 482 */           this.field_175386_A = Lists.newArrayList((Iterable)chatcomponenttext);
/*     */         } 
/*     */         
/* 485 */         this.field_175387_B = this.currPage;
/*     */       } 
/*     */       
/* 488 */       int j1 = this.fontRendererObj.getStringWidth(s4);
/* 489 */       getClass(); this.fontRendererObj.drawString(s4, i - j1 + 192 - 44, j + 16, 0);
/*     */       
/* 491 */       if (this.field_175386_A == null) {
/*     */         
/* 493 */         this.fontRendererObj.drawSplitString(s5, i + 36, j + 16 + 16, 116, 0);
/*     */       }
/*     */       else {
/*     */         
/* 497 */         int k1 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
/*     */         
/* 499 */         for (int l1 = 0; l1 < k1; l1++) {
/*     */           
/* 501 */           IChatComponent ichatcomponent2 = this.field_175386_A.get(l1);
/* 502 */           this.fontRendererObj.drawString(ichatcomponent2.getUnformattedText(), i + 36, j + 16 + 16 + l1 * this.fontRendererObj.FONT_HEIGHT, 0);
/*     */         } 
/*     */         
/* 505 */         IChatComponent ichatcomponent1 = func_175385_b(mouseX, mouseY);
/*     */         
/* 507 */         if (ichatcomponent1 != null)
/*     */         {
/* 509 */           handleComponentHover(ichatcomponent1, mouseX, mouseY);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 514 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 522 */     if (mouseButton == 0) {
/*     */       
/* 524 */       IChatComponent ichatcomponent = func_175385_b(mouseX, mouseY);
/*     */       
/* 526 */       if (handleComponentClick(ichatcomponent)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 532 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleComponentClick(IChatComponent component) {
/* 542 */     ClickEvent clickevent = (component == null) ? null : component.getChatStyle().getChatClickEvent();
/*     */     
/* 544 */     if (clickevent == null)
/*     */     {
/* 546 */       return false;
/*     */     }
/* 548 */     if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
/*     */       
/* 550 */       String s = clickevent.getValue();
/*     */ 
/*     */       
/*     */       try {
/* 554 */         int i = Integer.parseInt(s) - 1;
/*     */         
/* 556 */         if (i >= 0 && i < this.bookTotalPages && i != this.currPage)
/*     */         {
/* 558 */           this.currPage = i;
/* 559 */           updateButtons();
/* 560 */           return true;
/*     */         }
/*     */       
/* 563 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 568 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 572 */     boolean flag = super.handleComponentClick(component);
/*     */     
/* 574 */     if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
/*     */     {
/* 576 */       this.mc.displayGuiScreen((GuiScreen)null);
/*     */     }
/*     */     
/* 579 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent func_175385_b(int p_175385_1_, int p_175385_2_) {
/* 585 */     if (this.field_175386_A == null)
/*     */     {
/* 587 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 591 */     getClass(); int i = p_175385_1_ - (this.width - 192) / 2 - 36;
/* 592 */     int j = p_175385_2_ - 2 - 16 - 16;
/*     */     
/* 594 */     if (i >= 0 && j >= 0) {
/*     */       
/* 596 */       int k = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
/*     */       
/* 598 */       if (i <= 116 && j < this.mc.fontRendererObj.FONT_HEIGHT * k + k) {
/*     */         
/* 600 */         int l = j / this.mc.fontRendererObj.FONT_HEIGHT;
/*     */         
/* 602 */         if (l >= 0 && l < this.field_175386_A.size()) {
/*     */           
/* 604 */           IChatComponent ichatcomponent = this.field_175386_A.get(l);
/* 605 */           int i1 = 0;
/*     */           
/* 607 */           for (IChatComponent ichatcomponent1 : ichatcomponent) {
/*     */             
/* 609 */             if (ichatcomponent1 instanceof ChatComponentText) {
/*     */               
/* 611 */               i1 += this.mc.fontRendererObj.getStringWidth(((ChatComponentText)ichatcomponent1).getChatComponentText_TextValue());
/*     */               
/* 613 */               if (i1 > i)
/*     */               {
/* 615 */                 return ichatcomponent1;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 621 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 625 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 630 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class NextPageButton
/*     */     extends GuiButton
/*     */   {
/*     */     private final boolean field_146151_o;
/*     */ 
/*     */     
/*     */     public NextPageButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_) {
/* 641 */       super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
/* 642 */       this.field_146151_o = p_i46316_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 647 */       if (this.visible) {
/*     */         
/* 649 */         boolean flag = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 650 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 651 */         mc.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
/* 652 */         int i = 0;
/* 653 */         int j = 192;
/*     */         
/* 655 */         if (flag)
/*     */         {
/* 657 */           i += 23;
/*     */         }
/*     */         
/* 660 */         if (!this.field_146151_o)
/*     */         {
/* 662 */           j += 13;
/*     */         }
/*     */         
/* 665 */         drawTexturedModalRect(this.xPosition, this.yPosition, i, j, 23, 13);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiScreenBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */