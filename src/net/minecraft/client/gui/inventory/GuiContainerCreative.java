/*      */ package net.minecraft.client.gui.inventory;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.io.IOException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import net.minecraft.client.gui.GuiButton;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiTextField;
/*      */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*      */ import net.minecraft.client.gui.achievement.GuiStats;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ 
/*      */ public class GuiContainerCreative
/*      */   extends InventoryEffectRenderer {
/*   39 */   private static final ResourceLocation creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
/*   40 */   static InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);
/*      */ 
/*      */   
/*   43 */   private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
/*      */   
/*      */   private float currentScroll;
/*      */   
/*      */   private boolean isScrolling;
/*      */   
/*      */   private boolean wasClicking;
/*      */   
/*      */   private GuiTextField searchField;
/*      */   
/*      */   private List<Slot> field_147063_B;
/*      */   
/*      */   private Slot field_147064_C;
/*      */   
/*      */   private boolean field_147057_D;
/*      */   
/*      */   private CreativeCrafting field_147059_E;
/*      */ 
/*      */   
/*      */   public GuiContainerCreative(EntityPlayer p_i1088_1_) {
/*   63 */     super(new ContainerCreative(p_i1088_1_));
/*   64 */     p_i1088_1_.openContainer = this.inventorySlots;
/*   65 */     this.allowUserInput = true;
/*   66 */     this.ySize = 136;
/*   67 */     this.xSize = 195;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateScreen() {
/*   75 */     if (!this.mc.playerController.isInCreativeMode())
/*      */     {
/*   77 */       this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.thePlayer));
/*      */     }
/*      */     
/*   80 */     updateActivePotionEffects();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
/*   88 */     this.field_147057_D = true;
/*   89 */     boolean flag = (clickType == 1);
/*   90 */     clickType = (slotId == -999 && clickType == 0) ? 4 : clickType;
/*      */     
/*   92 */     if (slotIn == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && clickType != 5) {
/*      */       
/*   94 */       InventoryPlayer inventoryplayer1 = this.mc.thePlayer.inventory;
/*      */       
/*   96 */       if (inventoryplayer1.getItemStack() != null) {
/*      */         
/*   98 */         if (clickedButton == 0) {
/*      */           
/*  100 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(inventoryplayer1.getItemStack(), true);
/*  101 */           this.mc.playerController.sendPacketDropItem(inventoryplayer1.getItemStack());
/*  102 */           inventoryplayer1.setItemStack((ItemStack)null);
/*      */         } 
/*      */         
/*  105 */         if (clickedButton == 1)
/*      */         {
/*  107 */           ItemStack itemstack5 = inventoryplayer1.getItemStack().splitStack(1);
/*  108 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(itemstack5, true);
/*  109 */           this.mc.playerController.sendPacketDropItem(itemstack5);
/*      */           
/*  111 */           if ((inventoryplayer1.getItemStack()).stackSize == 0)
/*      */           {
/*  113 */             inventoryplayer1.setItemStack((ItemStack)null);
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*  118 */     } else if (slotIn == this.field_147064_C && flag) {
/*      */       
/*  120 */       for (int j = 0; j < this.mc.thePlayer.inventoryContainer.getInventory().size(); j++)
/*      */       {
/*  122 */         this.mc.playerController.sendSlotPacket((ItemStack)null, j);
/*      */       }
/*      */     }
/*  125 */     else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
/*      */       
/*  127 */       if (slotIn == this.field_147064_C)
/*      */       {
/*  129 */         this.mc.thePlayer.inventory.setItemStack((ItemStack)null);
/*      */       }
/*  131 */       else if (clickType == 4 && slotIn != null && slotIn.getHasStack())
/*      */       {
/*  133 */         ItemStack itemstack = slotIn.decrStackSize((clickedButton == 0) ? 1 : slotIn.getStack().getMaxStackSize());
/*  134 */         this.mc.thePlayer.dropPlayerItemWithRandomChoice(itemstack, true);
/*  135 */         this.mc.playerController.sendPacketDropItem(itemstack);
/*      */       }
/*  137 */       else if (clickType == 4 && this.mc.thePlayer.inventory.getItemStack() != null)
/*      */       {
/*  139 */         this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
/*  140 */         this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
/*  141 */         this.mc.thePlayer.inventory.setItemStack((ItemStack)null);
/*      */       }
/*      */       else
/*      */       {
/*  145 */         this.mc.thePlayer.inventoryContainer.slotClick((slotIn == null) ? slotId : ((CreativeSlot)slotIn).slot.slotNumber, clickedButton, clickType, (EntityPlayer)this.mc.thePlayer);
/*  146 */         this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
/*      */       }
/*      */     
/*  149 */     } else if (clickType != 5 && slotIn.inventory == field_147060_v) {
/*      */       
/*  151 */       InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
/*  152 */       ItemStack itemstack1 = inventoryplayer.getItemStack();
/*  153 */       ItemStack itemstack2 = slotIn.getStack();
/*      */       
/*  155 */       if (clickType == 2) {
/*      */         
/*  157 */         if (itemstack2 != null && clickedButton >= 0 && clickedButton < 9) {
/*      */           
/*  159 */           ItemStack itemstack7 = itemstack2.copy();
/*  160 */           itemstack7.stackSize = itemstack7.getMaxStackSize();
/*  161 */           this.mc.thePlayer.inventory.setInventorySlotContents(clickedButton, itemstack7);
/*  162 */           this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  168 */       if (clickType == 3) {
/*      */         
/*  170 */         if (inventoryplayer.getItemStack() == null && slotIn.getHasStack()) {
/*      */           
/*  172 */           ItemStack itemstack6 = slotIn.getStack().copy();
/*  173 */           itemstack6.stackSize = itemstack6.getMaxStackSize();
/*  174 */           inventoryplayer.setItemStack(itemstack6);
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  180 */       if (clickType == 4) {
/*      */         
/*  182 */         if (itemstack2 != null) {
/*      */           
/*  184 */           ItemStack itemstack3 = itemstack2.copy();
/*  185 */           itemstack3.stackSize = (clickedButton == 0) ? 1 : itemstack3.getMaxStackSize();
/*  186 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(itemstack3, true);
/*  187 */           this.mc.playerController.sendPacketDropItem(itemstack3);
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  193 */       if (itemstack1 != null && itemstack2 != null && itemstack1.isItemEqual(itemstack2)) {
/*      */         
/*  195 */         if (clickedButton == 0) {
/*      */           
/*  197 */           if (flag)
/*      */           {
/*  199 */             itemstack1.stackSize = itemstack1.getMaxStackSize();
/*      */           }
/*  201 */           else if (itemstack1.stackSize < itemstack1.getMaxStackSize())
/*      */           {
/*  203 */             itemstack1.stackSize++;
/*      */           }
/*      */         
/*  206 */         } else if (itemstack1.stackSize <= 1) {
/*      */           
/*  208 */           inventoryplayer.setItemStack((ItemStack)null);
/*      */         }
/*      */         else {
/*      */           
/*  212 */           itemstack1.stackSize--;
/*      */         }
/*      */       
/*  215 */       } else if (itemstack2 != null && itemstack1 == null) {
/*      */         
/*  217 */         inventoryplayer.setItemStack(ItemStack.copyItemStack(itemstack2));
/*  218 */         itemstack1 = inventoryplayer.getItemStack();
/*      */         
/*  220 */         if (flag)
/*      */         {
/*  222 */           itemstack1.stackSize = itemstack1.getMaxStackSize();
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  227 */         inventoryplayer.setItemStack((ItemStack)null);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  232 */       this.inventorySlots.slotClick((slotIn == null) ? slotId : slotIn.slotNumber, clickedButton, clickType, (EntityPlayer)this.mc.thePlayer);
/*      */       
/*  234 */       if (Container.getDragEvent(clickedButton) == 2) {
/*      */         
/*  236 */         for (int i = 0; i < 9; i++)
/*      */         {
/*  238 */           this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + i).getStack(), 36 + i);
/*      */         }
/*      */       }
/*  241 */       else if (slotIn != null) {
/*      */         
/*  243 */         ItemStack itemstack4 = this.inventorySlots.getSlot(slotIn.slotNumber).getStack();
/*  244 */         this.mc.playerController.sendSlotPacket(itemstack4, slotIn.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateActivePotionEffects() {
/*  251 */     int i = this.guiLeft;
/*  252 */     super.updateActivePotionEffects();
/*      */     
/*  254 */     if (this.searchField != null && this.guiLeft != i)
/*      */     {
/*  256 */       this.searchField.xPosition = this.guiLeft + 82;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initGui() {
/*  266 */     if (this.mc.playerController.isInCreativeMode()) {
/*      */       
/*  268 */       super.initGui();
/*  269 */       this.buttonList.clear();
/*  270 */       Keyboard.enableRepeatEvents(true);
/*  271 */       this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT);
/*  272 */       this.searchField.setMaxStringLength(15);
/*  273 */       this.searchField.setEnableBackgroundDrawing(false);
/*  274 */       this.searchField.setVisible(false);
/*  275 */       this.searchField.setTextColor(16777215);
/*  276 */       int i = selectedTabIndex;
/*  277 */       selectedTabIndex = -1;
/*  278 */       setCurrentCreativeTab(CreativeTabs.creativeTabArray[i]);
/*  279 */       this.field_147059_E = new CreativeCrafting(this.mc);
/*  280 */       this.mc.thePlayer.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
/*      */     }
/*      */     else {
/*      */       
/*  284 */       this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.thePlayer));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onGuiClosed() {
/*  293 */     super.onGuiClosed();
/*      */     
/*  295 */     if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null)
/*      */     {
/*  297 */       this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
/*      */     }
/*      */     
/*  300 */     Keyboard.enableRepeatEvents(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  309 */     if (selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
/*      */       
/*  311 */       if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat))
/*      */       {
/*  313 */         setCurrentCreativeTab(CreativeTabs.tabAllSearch);
/*      */       }
/*      */       else
/*      */       {
/*  317 */         super.keyTyped(typedChar, keyCode);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  322 */       if (this.field_147057_D) {
/*      */         
/*  324 */         this.field_147057_D = false;
/*  325 */         this.searchField.setText("");
/*      */       } 
/*      */       
/*  328 */       if (!checkHotbarKeys(keyCode))
/*      */       {
/*  330 */         if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
/*      */           
/*  332 */           updateCreativeSearch();
/*      */         }
/*      */         else {
/*      */           
/*  336 */           super.keyTyped(typedChar, keyCode);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateCreativeSearch() {
/*  344 */     ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.inventorySlots;
/*  345 */     guicontainercreative$containercreative.itemList.clear();
/*      */     
/*  347 */     for (Item item : Item.itemRegistry) {
/*      */       
/*  349 */       if (item != null && item.getCreativeTab() != null)
/*      */       {
/*  351 */         item.getSubItems(item, (CreativeTabs)null, guicontainercreative$containercreative.itemList);
/*      */       }
/*      */     } 
/*      */     
/*  355 */     for (Enchantment enchantment : Enchantment.enchantmentsBookList) {
/*      */       
/*  357 */       if (enchantment != null && enchantment.type != null)
/*      */       {
/*  359 */         Items.enchanted_book.getAll(enchantment, guicontainercreative$containercreative.itemList);
/*      */       }
/*      */     } 
/*      */     
/*  363 */     Iterator<ItemStack> iterator = guicontainercreative$containercreative.itemList.iterator();
/*  364 */     String s1 = this.searchField.getText().toLowerCase();
/*      */     
/*  366 */     while (iterator.hasNext()) {
/*      */       
/*  368 */       ItemStack itemstack = iterator.next();
/*  369 */       boolean flag = false;
/*      */       
/*  371 */       for (String s : itemstack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips)) {
/*      */         
/*  373 */         if (EnumChatFormatting.getTextWithoutFormattingCodes(s).toLowerCase().contains(s1)) {
/*      */           
/*  375 */           flag = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  380 */       if (!flag)
/*      */       {
/*  382 */         iterator.remove();
/*      */       }
/*      */     } 
/*      */     
/*  386 */     this.currentScroll = 0.0F;
/*  387 */     guicontainercreative$containercreative.scrollTo(0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  395 */     CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex];
/*      */     
/*  397 */     if (creativetabs.drawInForegroundOfTab()) {
/*      */       
/*  399 */       GlStateManager.disableBlend();
/*  400 */       this.fontRendererObj.drawString(I18n.format(creativetabs.getTranslatedTabLabel(), new Object[0]), 8, 6, 4210752);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  409 */     if (mouseButton == 0) {
/*      */       
/*  411 */       int i = mouseX - this.guiLeft;
/*  412 */       int j = mouseY - this.guiTop;
/*      */       
/*  414 */       for (CreativeTabs creativetabs : CreativeTabs.creativeTabArray) {
/*      */         
/*  416 */         if (func_147049_a(creativetabs, i, j)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  423 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/*  431 */     if (state == 0) {
/*      */       
/*  433 */       int i = mouseX - this.guiLeft;
/*  434 */       int j = mouseY - this.guiTop;
/*      */       
/*  436 */       for (CreativeTabs creativetabs : CreativeTabs.creativeTabArray) {
/*      */         
/*  438 */         if (func_147049_a(creativetabs, i, j)) {
/*      */           
/*  440 */           setCurrentCreativeTab(creativetabs);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  446 */     super.mouseReleased(mouseX, mouseY, state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needsScrollBars() {
/*  454 */     return (selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.inventorySlots).func_148328_e());
/*      */   }
/*      */ 
/*      */   
/*      */   private void setCurrentCreativeTab(CreativeTabs p_147050_1_) {
/*  459 */     int i = selectedTabIndex;
/*  460 */     selectedTabIndex = p_147050_1_.getTabIndex();
/*  461 */     ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.inventorySlots;
/*  462 */     this.dragSplittingSlots.clear();
/*  463 */     guicontainercreative$containercreative.itemList.clear();
/*  464 */     p_147050_1_.displayAllReleventItems(guicontainercreative$containercreative.itemList);
/*      */     
/*  466 */     if (p_147050_1_ == CreativeTabs.tabInventory) {
/*      */       
/*  468 */       Container container = this.mc.thePlayer.inventoryContainer;
/*      */       
/*  470 */       if (this.field_147063_B == null)
/*      */       {
/*  472 */         this.field_147063_B = guicontainercreative$containercreative.inventorySlots;
/*      */       }
/*      */       
/*  475 */       guicontainercreative$containercreative.inventorySlots = Lists.newArrayList();
/*      */       
/*  477 */       for (int j = 0; j < container.inventorySlots.size(); j++) {
/*      */         
/*  479 */         Slot slot = new CreativeSlot(container.inventorySlots.get(j), j);
/*  480 */         guicontainercreative$containercreative.inventorySlots.add(slot);
/*      */         
/*  482 */         if (j >= 5 && j < 9) {
/*      */           
/*  484 */           int j1 = j - 5;
/*  485 */           int k1 = j1 / 2;
/*  486 */           int l1 = j1 % 2;
/*  487 */           slot.xDisplayPosition = 9 + k1 * 54;
/*  488 */           slot.yDisplayPosition = 6 + l1 * 27;
/*      */         }
/*  490 */         else if (j >= 0 && j < 5) {
/*      */           
/*  492 */           slot.yDisplayPosition = -2000;
/*  493 */           slot.xDisplayPosition = -2000;
/*      */         }
/*  495 */         else if (j < container.inventorySlots.size()) {
/*      */           
/*  497 */           int k = j - 9;
/*  498 */           int l = k % 9;
/*  499 */           int i1 = k / 9;
/*  500 */           slot.xDisplayPosition = 9 + l * 18;
/*      */           
/*  502 */           if (j >= 36) {
/*      */             
/*  504 */             slot.yDisplayPosition = 112;
/*      */           }
/*      */           else {
/*      */             
/*  508 */             slot.yDisplayPosition = 54 + i1 * 18;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  513 */       this.field_147064_C = new Slot((IInventory)field_147060_v, 0, 173, 112);
/*  514 */       guicontainercreative$containercreative.inventorySlots.add(this.field_147064_C);
/*      */     }
/*  516 */     else if (i == CreativeTabs.tabInventory.getTabIndex()) {
/*      */       
/*  518 */       guicontainercreative$containercreative.inventorySlots = this.field_147063_B;
/*  519 */       this.field_147063_B = null;
/*      */     } 
/*      */     
/*  522 */     if (this.searchField != null)
/*      */     {
/*  524 */       if (p_147050_1_ == CreativeTabs.tabAllSearch) {
/*      */         
/*  526 */         this.searchField.setVisible(true);
/*  527 */         this.searchField.setCanLoseFocus(false);
/*  528 */         this.searchField.setFocused(true);
/*  529 */         this.searchField.setText("");
/*  530 */         updateCreativeSearch();
/*      */       }
/*      */       else {
/*      */         
/*  534 */         this.searchField.setVisible(false);
/*  535 */         this.searchField.setCanLoseFocus(true);
/*  536 */         this.searchField.setFocused(false);
/*      */       } 
/*      */     }
/*      */     
/*  540 */     this.currentScroll = 0.0F;
/*  541 */     guicontainercreative$containercreative.scrollTo(0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMouseInput() throws IOException {
/*  549 */     super.handleMouseInput();
/*  550 */     int i = Mouse.getEventDWheel();
/*      */     
/*  552 */     if (i != 0 && needsScrollBars()) {
/*      */       
/*  554 */       int j = ((ContainerCreative)this.inventorySlots).itemList.size() / 9 - 5;
/*      */       
/*  556 */       if (i > 0)
/*      */       {
/*  558 */         i = 1;
/*      */       }
/*      */       
/*  561 */       if (i < 0)
/*      */       {
/*  563 */         i = -1;
/*      */       }
/*      */       
/*  566 */       this.currentScroll = (float)(this.currentScroll - i / j);
/*  567 */       this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
/*  568 */       ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  577 */     boolean flag = Mouse.isButtonDown(0);
/*  578 */     int i = this.guiLeft;
/*  579 */     int j = this.guiTop;
/*  580 */     int k = i + 175;
/*  581 */     int l = j + 18;
/*  582 */     int i1 = k + 14;
/*  583 */     int j1 = l + 112;
/*      */     
/*  585 */     if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1)
/*      */     {
/*  587 */       this.isScrolling = needsScrollBars();
/*      */     }
/*      */     
/*  590 */     if (!flag)
/*      */     {
/*  592 */       this.isScrolling = false;
/*      */     }
/*      */     
/*  595 */     this.wasClicking = flag;
/*      */     
/*  597 */     if (this.isScrolling) {
/*      */       
/*  599 */       this.currentScroll = ((mouseY - l) - 7.5F) / ((j1 - l) - 15.0F);
/*  600 */       this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
/*  601 */       ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
/*      */     } 
/*      */     
/*  604 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*      */     
/*  606 */     for (CreativeTabs creativetabs : CreativeTabs.creativeTabArray) {
/*      */       
/*  608 */       if (renderCreativeInventoryHoveringText(creativetabs, mouseX, mouseY)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  614 */     if (this.field_147064_C != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && isPointInRegion(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, mouseX, mouseY))
/*      */     {
/*  616 */       drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), mouseX, mouseY);
/*      */     }
/*      */     
/*  619 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  620 */     GlStateManager.disableLighting();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderToolTip(ItemStack stack, int x, int y) {
/*  625 */     if (selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex()) {
/*      */       
/*  627 */       List<String> list = stack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/*  628 */       CreativeTabs creativetabs = stack.getItem().getCreativeTab();
/*      */       
/*  630 */       if (creativetabs == null && stack.getItem() == Items.enchanted_book) {
/*      */         
/*  632 */         Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(stack);
/*      */         
/*  634 */         if (map.size() == 1) {
/*      */           
/*  636 */           Enchantment enchantment = Enchantment.getEnchantmentById(((Integer)map.keySet().iterator().next()).intValue());
/*      */           
/*  638 */           for (CreativeTabs creativetabs1 : CreativeTabs.creativeTabArray) {
/*      */             
/*  640 */             if (creativetabs1.hasRelevantEnchantmentType(enchantment.type)) {
/*      */               
/*  642 */               creativetabs = creativetabs1;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  649 */       if (creativetabs != null)
/*      */       {
/*  651 */         list.add(1, String.valueOf(EnumChatFormatting.BOLD) + EnumChatFormatting.BLUE + I18n.format(creativetabs.getTranslatedTabLabel(), new Object[0]));
/*      */       }
/*      */       
/*  654 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  656 */         if (i == 0) {
/*      */           
/*  658 */           list.set(i, (stack.getRarity()).rarityColor + (String)list.get(i));
/*      */         }
/*      */         else {
/*      */           
/*  662 */           list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
/*      */         } 
/*      */       } 
/*      */       
/*  666 */       drawHoveringText(list, x, y);
/*      */     }
/*      */     else {
/*      */       
/*  670 */       super.renderToolTip(stack, x, y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/*  679 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  680 */     RenderHelper.enableGUIStandardItemLighting();
/*  681 */     CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex];
/*      */     
/*  683 */     for (CreativeTabs creativetabs1 : CreativeTabs.creativeTabArray) {
/*      */       
/*  685 */       this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
/*      */       
/*  687 */       if (creativetabs1.getTabIndex() != selectedTabIndex)
/*      */       {
/*  689 */         func_147051_a(creativetabs1);
/*      */       }
/*      */     } 
/*      */     
/*  693 */     this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + creativetabs.getBackgroundImageName()));
/*  694 */     drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
/*  695 */     this.searchField.drawTextBox();
/*  696 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  697 */     int i = this.guiLeft + 175;
/*  698 */     int j = this.guiTop + 18;
/*  699 */     int k = j + 112;
/*  700 */     this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
/*      */     
/*  702 */     if (creativetabs.shouldHidePlayerInventory())
/*      */     {
/*  704 */       drawTexturedModalRect(i, j + (int)((k - j - 17) * this.currentScroll), 232 + (needsScrollBars() ? 0 : 12), 0, 12, 15);
/*      */     }
/*      */     
/*  707 */     func_147051_a(creativetabs);
/*      */     
/*  709 */     if (creativetabs == CreativeTabs.tabInventory)
/*      */     {
/*  711 */       GuiInventory.drawEntityOnScreen(this.guiLeft + 43, this.guiTop + 45, 20, (this.guiLeft + 43 - mouseX), (this.guiTop + 45 - 30 - mouseY), (EntityLivingBase)this.mc.thePlayer);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean func_147049_a(CreativeTabs p_147049_1_, int p_147049_2_, int p_147049_3_) {
/*  717 */     int i = p_147049_1_.getTabColumn();
/*  718 */     int j = 28 * i;
/*  719 */     int k = 0;
/*      */     
/*  721 */     if (i == 5) {
/*      */       
/*  723 */       j = this.xSize - 28 + 2;
/*      */     }
/*  725 */     else if (i > 0) {
/*      */       
/*  727 */       j += i;
/*      */     } 
/*      */     
/*  730 */     if (p_147049_1_.isTabInFirstRow()) {
/*      */       
/*  732 */       k -= 32;
/*      */     }
/*      */     else {
/*      */       
/*  736 */       k += this.ySize;
/*      */     } 
/*      */     
/*  739 */     return (p_147049_2_ >= j && p_147049_2_ <= j + 28 && p_147049_3_ >= k && p_147049_3_ <= k + 32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean renderCreativeInventoryHoveringText(CreativeTabs p_147052_1_, int p_147052_2_, int p_147052_3_) {
/*  748 */     int i = p_147052_1_.getTabColumn();
/*  749 */     int j = 28 * i;
/*  750 */     int k = 0;
/*      */     
/*  752 */     if (i == 5) {
/*      */       
/*  754 */       j = this.xSize - 28 + 2;
/*      */     }
/*  756 */     else if (i > 0) {
/*      */       
/*  758 */       j += i;
/*      */     } 
/*      */     
/*  761 */     if (p_147052_1_.isTabInFirstRow()) {
/*      */       
/*  763 */       k -= 32;
/*      */     }
/*      */     else {
/*      */       
/*  767 */       k += this.ySize;
/*      */     } 
/*      */     
/*  770 */     if (isPointInRegion(j + 3, k + 3, 23, 27, p_147052_2_, p_147052_3_)) {
/*      */       
/*  772 */       drawCreativeTabHoveringText(I18n.format(p_147052_1_.getTranslatedTabLabel(), new Object[0]), p_147052_2_, p_147052_3_);
/*  773 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  777 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_147051_a(CreativeTabs p_147051_1_) {
/*  783 */     boolean flag = (p_147051_1_.getTabIndex() == selectedTabIndex);
/*  784 */     boolean flag1 = p_147051_1_.isTabInFirstRow();
/*  785 */     int i = p_147051_1_.getTabColumn();
/*  786 */     int j = i * 28;
/*  787 */     int k = 0;
/*  788 */     int l = this.guiLeft + 28 * i;
/*  789 */     int i1 = this.guiTop;
/*  790 */     int j1 = 32;
/*      */     
/*  792 */     if (flag)
/*      */     {
/*  794 */       k += 32;
/*      */     }
/*      */     
/*  797 */     if (i == 5) {
/*      */       
/*  799 */       l = this.guiLeft + this.xSize - 28;
/*      */     }
/*  801 */     else if (i > 0) {
/*      */       
/*  803 */       l += i;
/*      */     } 
/*      */     
/*  806 */     if (flag1) {
/*      */       
/*  808 */       i1 -= 28;
/*      */     }
/*      */     else {
/*      */       
/*  812 */       k += 64;
/*  813 */       i1 += this.ySize - 4;
/*      */     } 
/*      */     
/*  816 */     GlStateManager.disableLighting();
/*  817 */     drawTexturedModalRect(l, i1, j, k, 28, j1);
/*  818 */     this.zLevel = 100.0F;
/*  819 */     this.itemRender.zLevel = 100.0F;
/*  820 */     l += 6;
/*  821 */     i1 = i1 + 8 + (flag1 ? 1 : -1);
/*  822 */     GlStateManager.enableLighting();
/*  823 */     GlStateManager.enableRescaleNormal();
/*  824 */     ItemStack itemstack = p_147051_1_.getIconItemStack();
/*  825 */     this.itemRender.renderItemAndEffectIntoGUI(itemstack, l, i1);
/*  826 */     this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack, l, i1);
/*  827 */     GlStateManager.disableLighting();
/*  828 */     this.itemRender.zLevel = 0.0F;
/*  829 */     this.zLevel = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void actionPerformed(GuiButton button) {
/*  836 */     if (button.id == 0)
/*      */     {
/*  838 */       this.mc.displayGuiScreen((GuiScreen)new GuiAchievements((GuiScreen)this, this.mc.thePlayer.getStatFileWriter()));
/*      */     }
/*      */     
/*  841 */     if (button.id == 1)
/*      */     {
/*  843 */       this.mc.displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, this.mc.thePlayer.getStatFileWriter()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSelectedTabIndex() {
/*  849 */     return selectedTabIndex;
/*      */   }
/*      */   
/*      */   static class ContainerCreative
/*      */     extends Container {
/*  854 */     public List<ItemStack> itemList = Lists.newArrayList();
/*      */ 
/*      */     
/*      */     public ContainerCreative(EntityPlayer p_i1086_1_) {
/*  858 */       InventoryPlayer inventoryplayer = p_i1086_1_.inventory;
/*      */       
/*  860 */       for (int i = 0; i < 5; i++) {
/*      */         
/*  862 */         for (int j = 0; j < 9; j++)
/*      */         {
/*  864 */           addSlotToContainer(new Slot((IInventory)GuiContainerCreative.field_147060_v, i * 9 + j, 9 + j * 18, 18 + i * 18));
/*      */         }
/*      */       } 
/*      */       
/*  868 */       for (int k = 0; k < 9; k++)
/*      */       {
/*  870 */         addSlotToContainer(new Slot((IInventory)inventoryplayer, k, 9 + k * 18, 112));
/*      */       }
/*      */       
/*  873 */       scrollTo(0.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canInteractWith(EntityPlayer playerIn) {
/*  878 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void scrollTo(float p_148329_1_) {
/*  883 */       int i = (this.itemList.size() + 9 - 1) / 9 - 5;
/*  884 */       int j = (int)((p_148329_1_ * i) + 0.5D);
/*      */       
/*  886 */       if (j < 0)
/*      */       {
/*  888 */         j = 0;
/*      */       }
/*      */       
/*  891 */       for (int k = 0; k < 5; k++) {
/*      */         
/*  893 */         for (int l = 0; l < 9; l++) {
/*      */           
/*  895 */           int i1 = l + (k + j) * 9;
/*      */           
/*  897 */           if (i1 >= 0 && i1 < this.itemList.size()) {
/*      */             
/*  899 */             GuiContainerCreative.field_147060_v.setInventorySlotContents(l + k * 9, this.itemList.get(i1));
/*      */           }
/*      */           else {
/*      */             
/*  903 */             GuiContainerCreative.field_147060_v.setInventorySlotContents(l + k * 9, (ItemStack)null);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean func_148328_e() {
/*  911 */       return (this.itemList.size() > 45);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer playerIn) {}
/*      */ 
/*      */     
/*      */     public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  920 */       if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size()) {
/*      */         
/*  922 */         Slot slot = this.inventorySlots.get(index);
/*      */         
/*  924 */         if (slot != null && slot.getHasStack())
/*      */         {
/*  926 */           slot.putStack((ItemStack)null);
/*      */         }
/*      */       } 
/*      */       
/*  930 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/*  935 */       return (slotIn.yDisplayPosition > 90);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canDragIntoSlot(Slot p_94531_1_) {
/*  940 */       return (p_94531_1_.inventory instanceof InventoryPlayer || (p_94531_1_.yDisplayPosition > 90 && p_94531_1_.xDisplayPosition <= 162));
/*      */     }
/*      */   }
/*      */   
/*      */   static class CreativeSlot
/*      */     extends Slot
/*      */   {
/*      */     final Slot slot;
/*      */     
/*      */     public CreativeSlot(Slot p_i46313_2_, int p_i46313_3_) {
/*  950 */       super(p_i46313_2_.inventory, p_i46313_3_, 0, 0);
/*  951 */       this.slot = p_i46313_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/*  956 */       this.slot.onPickupFromSlot(playerIn, stack);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isItemValid(ItemStack stack) {
/*  961 */       return this.slot.isItemValid(stack);
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack getStack() {
/*  966 */       return this.slot.getStack();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getHasStack() {
/*  971 */       return this.slot.getHasStack();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putStack(ItemStack stack) {
/*  976 */       this.slot.putStack(stack);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onSlotChanged() {
/*  981 */       this.slot.onSlotChanged();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getSlotStackLimit() {
/*  986 */       return this.slot.getSlotStackLimit();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getItemStackLimit(ItemStack stack) {
/*  991 */       return this.slot.getItemStackLimit(stack);
/*      */     }
/*      */ 
/*      */     
/*      */     public String getSlotTexture() {
/*  996 */       return this.slot.getSlotTexture();
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack decrStackSize(int amount) {
/* 1001 */       return this.slot.decrStackSize(amount);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isHere(IInventory inv, int slotIn) {
/* 1006 */       return this.slot.isHere(inv, slotIn);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\inventory\GuiContainerCreative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */