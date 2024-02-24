/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.implement.globals.NoBackground;
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientSetting;
/*     */ import awareline.main.ui.gui.guimainmenu.shader.MenuShader;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public abstract class GuiScreen
/*     */   extends Gui
/*     */   implements GuiYesNoCallback {
/*  53 */   private static final Logger LOGGER = LogManager.getLogger();
/*  54 */   private static final Set<String> PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
/*  55 */   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
/*  56 */   protected final List<GuiButton> buttonList = Lists.newArrayList();
/*  57 */   protected final List<GuiLabel> labelList = Lists.newArrayList();
/*  58 */   private final MenuShader menuShader = new MenuShader();
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */   
/*     */   public int height;
/*     */ 
/*     */   
/*     */   public boolean allowUserInput;
/*     */ 
/*     */   
/*     */   protected Minecraft mc;
/*     */ 
/*     */   
/*     */   protected RenderItem itemRender;
/*     */ 
/*     */   
/*     */   protected FontRenderer fontRendererObj;
/*     */ 
/*     */   
/*     */   protected GuiButton selectedButton;
/*     */ 
/*     */   
/*     */   protected int eventButton;
/*     */ 
/*     */   
/*     */   private long lastMouseEvent;
/*     */ 
/*     */   
/*     */   private int touchValue;
/*     */ 
/*     */   
/*     */   private URI clickedLinkURI;
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClipboardString() {
/*     */     try {
/*  97 */       Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/*     */       
/*  99 */       if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
/* 100 */         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
/*     */       }
/* 102 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 105 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setClipboardString(String copyText) {
/* 112 */     if (!StringUtils.isEmpty(copyText)) {
/*     */       try {
/* 114 */         StringSelection stringselection = new StringSelection(copyText);
/* 115 */         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
/* 116 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCtrlKeyDown() {
/* 125 */     return Minecraft.isRunningOnMac ? ((Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220))) : ((Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/* 132 */     return (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAltKeyDown() {
/* 139 */     return (Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184));
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlX(int keyID) {
/* 143 */     return (keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlV(int keyID) {
/* 147 */     return (keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlC(int keyID) {
/* 151 */     return (keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlA(int keyID) {
/* 155 */     return (keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 162 */     for (int i = 0; i < this.buttonList.size(); i++) {
/* 163 */       ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
/*     */     }
/*     */     
/* 166 */     for (int j = 0; j < this.labelList.size(); j++) {
/* 167 */       ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 176 */     if (keyCode == 1) {
/* 177 */       this.mc.displayGuiScreen(null);
/*     */       
/* 179 */       if (this.mc.currentScreen == null) {
/* 180 */         this.mc.setIngameFocus();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void renderToolTip(ItemStack stack, int x, int y) {
/* 186 */     List<String> list = stack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/*     */     
/* 188 */     int size = list.size();
/* 189 */     for (int i = 0; i < size; i++) {
/* 190 */       if (i == 0) {
/* 191 */         list.set(i, (stack.getRarity()).rarityColor + (String)list.get(i));
/*     */       } else {
/* 193 */         list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     drawHoveringText(list, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/* 206 */     drawHoveringText(Collections.singletonList(tabName), mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHoveringText(List<String> textLines, int x, int y) {
/* 213 */     if (!textLines.isEmpty()) {
/* 214 */       GlStateManager.disableRescaleNormal();
/* 215 */       RenderHelper.disableStandardItemLighting();
/* 216 */       GlStateManager.disableLighting();
/* 217 */       GlStateManager.disableDepth();
/* 218 */       int i = 0;
/*     */       
/* 220 */       for (String s : textLines) {
/*     */         
/* 222 */         int j = FontLoader.PF16.getStringWidth(s);
/* 223 */         if (j > i) {
/* 224 */           i = j;
/*     */         }
/*     */       } 
/*     */       
/* 228 */       int l1 = x + 12;
/* 229 */       int i2 = y - 12;
/* 230 */       int k = 8;
/*     */       
/* 232 */       int size = textLines.size();
/*     */       
/* 234 */       if (size > 1) {
/* 235 */         k += 2 + (size - 1) * 10;
/*     */       }
/*     */       
/* 238 */       if (l1 + i > this.width) {
/* 239 */         l1 -= 28 + i;
/*     */       }
/*     */       
/* 242 */       if (i2 + k + 6 > this.height) {
/* 243 */         i2 = this.height - k - 6;
/*     */       }
/*     */       
/* 246 */       this.zLevel = 300.0F;
/* 247 */       this.itemRender.zLevel = 300.0F;
/* 248 */       int l = -267386864;
/* 249 */       drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
/* 250 */       drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
/* 251 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
/* 252 */       drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
/* 253 */       drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
/* 254 */       int i1 = 1347420415;
/* 255 */       int j1 = (i1 & 0xFEFEFE) >> 1 | i1 & 0xFF000000;
/* 256 */       drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
/* 257 */       drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
/* 258 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
/* 259 */       drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);
/*     */       
/* 261 */       for (int k1 = 0; k1 < size; k1++) {
/* 262 */         String s1 = textLines.get(k1);
/* 263 */         FontLoader.PF16.drawStringWithShadow(s1, l1, (i2 + 2), -1);
/*     */ 
/*     */         
/* 266 */         if (k1 == 0) {
/* 267 */           i2 += 2;
/*     */         }
/*     */         
/* 270 */         i2 += 10;
/*     */       } 
/*     */       
/* 273 */       this.zLevel = 0.0F;
/* 274 */       this.itemRender.zLevel = 0.0F;
/* 275 */       GlStateManager.enableLighting();
/* 276 */       GlStateManager.enableDepth();
/* 277 */       RenderHelper.enableStandardItemLighting();
/* 278 */       GlStateManager.enableRescaleNormal();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleComponentHover(IChatComponent component, int x, int y) {
/* 290 */     if (component != null && component.getChatStyle().getChatHoverEvent() != null) {
/* 291 */       HoverEvent hoverevent = component.getChatStyle().getChatHoverEvent();
/*     */       
/* 293 */       if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
/* 294 */         ItemStack itemstack = null;
/*     */         
/*     */         try {
/* 297 */           NBTTagCompound nbtbase = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */           
/* 299 */           if (nbtbase != null) {
/* 300 */             itemstack = ItemStack.loadItemStackFromNBT(nbtbase);
/*     */           }
/* 302 */         } catch (NBTException nBTException) {}
/*     */ 
/*     */         
/* 305 */         if (itemstack != null) {
/* 306 */           renderToolTip(itemstack, x, y);
/*     */         } else {
/* 308 */           drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", x, y);
/*     */         } 
/* 310 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
/* 311 */         if (this.mc.gameSettings.advancedItemTooltips) {
/*     */           try {
/* 313 */             NBTTagCompound nbtbase1 = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */             
/* 315 */             if (nbtbase1 != null) {
/* 316 */               List<String> list1 = Lists.newArrayList();
/* 317 */               list1.add(nbtbase1.getString("name"));
/*     */               
/* 319 */               if (nbtbase1.hasKey("type", 8)) {
/* 320 */                 String s = nbtbase1.getString("type");
/* 321 */                 list1.add("Type: " + s + " (" + EntityList.getIDFromString(s) + ")");
/*     */               } 
/*     */               
/* 324 */               list1.add(nbtbase1.getString("id"));
/* 325 */               drawHoveringText(list1, x, y);
/*     */             } else {
/* 327 */               drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
/*     */             } 
/* 329 */           } catch (NBTException var10) {
/* 330 */             drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
/*     */           } 
/*     */         }
/* 333 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
/* 334 */         drawHoveringText(NEWLINE_SPLITTER.splitToList(hoverevent.getValue().getFormattedText()), x, y);
/* 335 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
/* 336 */         StatBase statbase = StatList.getOneShotStat(hoverevent.getValue().getUnformattedText());
/*     */         
/* 338 */         if (statbase != null) {
/* 339 */           IChatComponent ichatcomponent = statbase.getStatName();
/* 340 */           ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stats.tooltip.type." + (statbase.isAchievement() ? "achievement" : "statistic"), new Object[0]);
/* 341 */           chatComponentTranslation.getChatStyle().setItalic(Boolean.TRUE);
/* 342 */           String s1 = (statbase instanceof Achievement) ? ((Achievement)statbase).getDescription() : null;
/* 343 */           List<String> list = Lists.newArrayList((Object[])new String[] { ichatcomponent.getFormattedText(), chatComponentTranslation.getFormattedText() });
/*     */           
/* 345 */           if (s1 != null) {
/* 346 */             list.addAll(this.fontRendererObj.listFormattedStringToWidth(s1, 150));
/*     */           }
/*     */           
/* 349 */           drawHoveringText(list, x, y);
/*     */         } else {
/* 351 */           drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", x, y);
/*     */         } 
/*     */       } 
/*     */       
/* 355 */       GlStateManager.disableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleComponentClick(IChatComponent component) {
/* 371 */     if (component == null) {
/* 372 */       return false;
/*     */     }
/* 374 */     ClickEvent clickevent = component.getChatStyle().getChatClickEvent();
/*     */     
/* 376 */     if (isShiftKeyDown()) {
/* 377 */       if (component.getChatStyle().getInsertion() != null) {
/* 378 */         setText(component.getChatStyle().getInsertion(), false);
/*     */       }
/* 380 */     } else if (clickevent != null) {
/* 381 */       if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
/* 382 */         if (!this.mc.gameSettings.chatLinks) {
/* 383 */           return false;
/*     */         }
/*     */         
/*     */         try {
/* 387 */           URI uri = new URI(clickevent.getValue());
/* 388 */           String s = uri.getScheme();
/*     */           
/* 390 */           if (s == null) {
/* 391 */             throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
/*     */           }
/*     */           
/* 394 */           if (!PROTOCOLS.contains(s.toLowerCase())) {
/* 395 */             throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase());
/*     */           }
/*     */           
/* 398 */           if (this.mc.gameSettings.chatLinksPrompt) {
/* 399 */             this.clickedLinkURI = uri;
/* 400 */             this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
/*     */           } else {
/* 402 */             openWebLink(uri);
/*     */           } 
/* 404 */         } catch (URISyntaxException urisyntaxexception) {
/* 405 */           LOGGER.error("Can't open url for " + clickevent, urisyntaxexception);
/*     */         } 
/* 407 */       } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
/* 408 */         URI uri1 = (new File(clickevent.getValue())).toURI();
/* 409 */         openWebLink(uri1);
/* 410 */       } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
/* 411 */         setText(clickevent.getValue(), true);
/* 412 */       } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 413 */         sendChatMessage(clickevent.getValue(), false);
/*     */       } else {
/* 415 */         LOGGER.error("Don't know how to handle " + clickevent);
/*     */       } 
/*     */       
/* 418 */       return true;
/*     */     } 
/*     */     
/* 421 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String msg) {
/* 429 */     sendChatMessage(msg, true);
/*     */   }
/*     */   
/*     */   public void sendChatMessage(String msg, boolean addToChat) {
/* 433 */     if (addToChat) {
/* 434 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*     */     }
/*     */     
/* 437 */     this.mc.thePlayer.sendChatMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 444 */     if (mouseButton == 0) {
/* 445 */       int size = this.buttonList.size();
/* 446 */       for (int i = 0; i < size; i++) {
/* 447 */         GuiButton guibutton = this.buttonList.get(i);
/*     */         
/* 449 */         if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
/* 450 */           this.selectedButton = guibutton;
/* 451 */           guibutton.playPressSound(this.mc.getSoundHandler());
/* 452 */           actionPerformed(guibutton);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 462 */     if (this.selectedButton != null && state == 0) {
/* 463 */       this.selectedButton.mouseReleased(mouseX, mouseY);
/* 464 */       this.selectedButton = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldAndResolution(Minecraft mc, int width, int height) {
/* 486 */     this.mc = mc;
/* 487 */     this.itemRender = mc.getRenderItem();
/* 488 */     this.fontRendererObj = mc.fontRendererObj;
/* 489 */     this.width = width;
/* 490 */     this.height = height;
/* 491 */     this.buttonList.clear();
/* 492 */     initGui();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGuiSize(int w, int h) {
/* 502 */     this.width = w;
/* 503 */     this.height = h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleInput() throws IOException {
/* 519 */     if (Mouse.isCreated()) {
/*     */       while (true) {
/* 521 */         boolean next = Mouse.next();
/* 522 */         if (!next)
/* 523 */           break;  handleMouseInput();
/*     */       } 
/*     */     }
/*     */     
/* 527 */     if (Keyboard.isCreated()) {
/*     */       while (true) {
/* 529 */         boolean next = Keyboard.next();
/* 530 */         if (!next)
/* 531 */           break;  handleKeyboardInput();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 540 */     int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
/* 541 */     int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
/* 542 */     int k = Mouse.getEventButton();
/*     */     
/* 544 */     if (Mouse.getEventButtonState()) {
/* 545 */       if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 549 */       this.eventButton = k;
/* 550 */       this.lastMouseEvent = Minecraft.getSystemTime();
/* 551 */       mouseClicked(i, j, this.eventButton);
/* 552 */     } else if (k != -1) {
/* 553 */       if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 557 */       this.eventButton = -1;
/* 558 */       mouseReleased(i, j, k);
/* 559 */     } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
/* 560 */       long l = Minecraft.getSystemTime() - this.lastMouseEvent;
/* 561 */       mouseClickMove(i, j, this.eventButton, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 569 */     char c0 = Keyboard.getEventCharacter();
/* 570 */     if ((Keyboard.getEventKey() == 0 && c0 >= ' ') || Keyboard.getEventKeyState()) {
/* 571 */       keyTyped(c0, Keyboard.getEventKey());
/*     */     }
/* 573 */     this.mc.dispatchKeypresses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/* 589 */     drawWorldBackground();
/*     */   }
/*     */   
/*     */   public void drawClientBackground() {
/* 593 */     GlStateManager.disableLighting();
/* 594 */     GlStateManager.disableFog();
/*     */     
/* 596 */     if (((Boolean)ClientSetting.shaderBG.get()).booleanValue()) {
/* 597 */       this.menuShader.render(new ScaledResolution(this.mc), true);
/*     */     } else {
/* 599 */       if (Client.instance.getCustomBackground() != null) {
/* 600 */         Client.instance.getCustomBackground().load().bind().draw(0.0D, 0.0D, this.width, this.height);
/*     */       } else {
/* 602 */         this.mc.getTextureManager().bindTexture(new ResourceLocation("client/guimainmenu/4.png"));
/*     */       } 
/* 604 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 605 */       drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height, this.width, this.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawWorldBackground() {
/* 611 */     if (this.mc.theWorld != null) {
/* 612 */       if (NoBackground.getInstance.isEnabled()) {
/* 613 */         if (((Boolean)NoBackground.getInstance.allGui.get()).booleanValue()) {
/*     */           return;
/*     */         }
/* 616 */         if (this instanceof GuiIngameMenu && ((Boolean)NoBackground.getInstance.gameMenu.getValue()).booleanValue())
/* 617 */           return;  if (this instanceof net.minecraft.client.gui.inventory.GuiInventory && ((Boolean)NoBackground.getInstance.inventory.getValue()).booleanValue())
/* 618 */           return;  if (this instanceof net.minecraft.client.gui.inventory.GuiChest && ((Boolean)NoBackground.getInstance.chest.getValue()).booleanValue())
/*     */           return; 
/* 620 */       }  drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
/*     */     } else {
/* 622 */       drawClientBackground();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int tint) {
/* 630 */     GlStateManager.disableLighting();
/* 631 */     GlStateManager.disableFog();
/* 632 */     Tessellator tessellator = Tessellator.getInstance();
/* 633 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 634 */     this.mc.getTextureManager().bindTexture(optionsBackground);
/* 635 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 636 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 637 */     worldrenderer.pos(0.0D, this.height, 0.0D).tex(0.0D, (this.height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 638 */     worldrenderer.pos(this.width, this.height, 0.0D).tex((this.width / 32.0F), (this.height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 639 */     worldrenderer.pos(this.width, 0.0D, 0.0D).tex((this.width / 32.0F), tint).color(64, 64, 64, 255).endVertex();
/* 640 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, tint).color(64, 64, 64, 255).endVertex();
/* 641 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 648 */     return true;
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 652 */     if (id == 31102009) {
/* 653 */       if (result) {
/* 654 */         openWebLink(this.clickedLinkURI);
/*     */       }
/*     */       
/* 657 */       this.clickedLinkURI = null;
/* 658 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void openWebLink(URI url) {
/*     */     try {
/* 664 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 665 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 666 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { url });
/* 667 */     } catch (Throwable throwable) {
/* 668 */       LOGGER.error("Couldn't open link", throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResize(Minecraft mcIn, int w, int h) {
/* 679 */     setWorldAndResolution(mcIn, w, h);
/*     */   }
/*     */   
/*     */   public void InventoryClicks() throws IOException {
/* 683 */     int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
/* 684 */     int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
/* 685 */     int var3 = 0;
/* 686 */     this.eventButton = 0;
/* 687 */     this.lastMouseEvent = Minecraft.getSystemTime();
/* 688 */     mouseClicked(var1, var2, this.eventButton);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */