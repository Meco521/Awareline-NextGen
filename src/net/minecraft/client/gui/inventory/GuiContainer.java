/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.combat.TPAura;
/*     */ import awareline.main.mod.implement.combat.advanced.AdvancedAura;
/*     */ import awareline.main.mod.implement.globals.ContainerAnimations;
/*     */ import awareline.main.mod.implement.player.InventoryManager;
/*     */ import awareline.main.mod.implement.world.ChestStealer;
/*     */ import awareline.main.ui.animations.TranslateUtil;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ public abstract class GuiContainer
/*     */   extends GuiScreen
/*     */ {
/*  35 */   protected static final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
/*  36 */   protected final Set<Slot> dragSplittingSlots = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */   
/*     */   public Container inventorySlots;
/*     */ 
/*     */ 
/*     */   
/*  44 */   protected int xSize = 176;
/*     */ 
/*     */ 
/*     */   
/*  48 */   protected int ySize = 166;
/*     */   
/*     */   protected int guiLeft;
/*     */   
/*     */   protected int guiTop;
/*     */   
/*     */   protected boolean dragSplitting;
/*     */   
/*     */   private Slot theSlot;
/*     */   
/*     */   private Slot clickedSlot;
/*     */   
/*     */   private boolean isRightMouseClick;
/*     */   
/*     */   private ItemStack draggedStack;
/*     */   
/*     */   private int touchUpX;
/*     */   
/*     */   private int touchUpY;
/*     */   
/*     */   private Slot returningStackDestSlot;
/*     */   
/*     */   private long returningStackTime;
/*     */   
/*     */   private ItemStack returningStack;
/*     */   
/*     */   private Slot currentDragTargetSlot;
/*     */   
/*     */   private long dragItemDropDelay;
/*     */   
/*     */   private int dragSplittingLimit;
/*     */   
/*     */   private int dragSplittingButton;
/*     */   
/*     */   private boolean ignoreMouseUp;
/*     */   
/*     */   private int dragSplittingRemnant;
/*     */   
/*     */   private long lastClickTime;
/*     */   
/*     */   private Slot lastClickSlot;
/*     */   
/*     */   private int lastClickButton;
/*     */   private boolean doubleClick;
/*     */   private ItemStack shiftClickedSlot;
/*     */   private TranslateUtil translate;
/*     */   
/*     */   public GuiContainer(Container inventorySlotsIn) {
/*  96 */     this.inventorySlots = inventorySlotsIn;
/*  97 */     this.ignoreMouseUp = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 105 */     super.initGui();
/* 106 */     this.mc.thePlayer.openContainer = this.inventorySlots;
/* 107 */     this.guiLeft = (this.width - this.xSize) / 2;
/* 108 */     this.guiTop = (this.height - this.ySize) / 2;
/* 109 */     this.buttonList.add(new GuiButton(6969, 3, 3, 110, 20, "Disable KillAura"));
/* 110 */     this.buttonList.add(new GuiButton(696969, 3, 25, 110, 20, "Disable TPAura"));
/* 111 */     this.buttonList.add(new GuiButton(69696969, 2, 46, 110, 20, "Disable InvManager"));
/* 112 */     this.buttonList.add(new GuiButton(696969969, 2, 67, 110, 20, "Disable ChestStealer"));
/* 113 */     this.translate = new TranslateUtil(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 120 */     drawDefaultBackground();
/* 121 */     if (ContainerAnimations.getInstance.isEnabled()) {
/* 122 */       this.translate.interpolate(this.width, this.height, 0.3F);
/*     */     }
/* 124 */     int i = this.guiLeft;
/* 125 */     int j = this.guiTop;
/* 126 */     double xmod = ((this.width / 2) - this.translate.getX() / 2.0F);
/* 127 */     double ymod = ((this.height / 2) - this.translate.getY() / 2.0F);
/* 128 */     if (ContainerAnimations.getInstance.isEnabled()) {
/* 129 */       GlStateManager.translate(xmod, ymod, 0.0D);
/* 130 */       GlStateManager.scale(this.translate.getX() / this.width, this.translate.getY() / this.height, 1.0F);
/*     */     } 
/*     */     
/* 133 */     drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
/* 134 */     GlStateManager.disableRescaleNormal();
/* 135 */     RenderHelper.disableStandardItemLighting();
/* 136 */     GlStateManager.disableLighting();
/* 137 */     GlStateManager.disableDepth();
/* 138 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 139 */     RenderHelper.enableGUIStandardItemLighting();
/* 140 */     GlStateManager.pushMatrix();
/* 141 */     GlStateManager.translate(i, j, 0.0F);
/* 142 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 143 */     GlStateManager.enableRescaleNormal();
/* 144 */     this.theSlot = null;
/* 145 */     int k = 240;
/* 146 */     int l = 240;
/* 147 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/* 148 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 150 */     for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); i1++) {
/* 151 */       Slot slot = this.inventorySlots.inventorySlots.get(i1);
/* 152 */       drawSlot(slot);
/*     */       
/* 154 */       if (isMouseOverSlot(slot, mouseX, mouseY) && slot.canBeHovered()) {
/* 155 */         this.theSlot = slot;
/* 156 */         GlStateManager.disableLighting();
/* 157 */         GlStateManager.disableDepth();
/* 158 */         int j1 = slot.xDisplayPosition;
/* 159 */         int k1 = slot.yDisplayPosition;
/* 160 */         GlStateManager.colorMask(true, true, true, false);
/* 161 */         drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
/* 162 */         GlStateManager.colorMask(true, true, true, true);
/* 163 */         GlStateManager.enableLighting();
/* 164 */         GlStateManager.enableDepth();
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     RenderHelper.disableStandardItemLighting();
/* 169 */     drawGuiContainerForegroundLayer(mouseX, mouseY);
/* 170 */     RenderHelper.enableGUIStandardItemLighting();
/* 171 */     InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
/* 172 */     ItemStack itemstack = (this.draggedStack == null) ? inventoryplayer.getItemStack() : this.draggedStack;
/*     */     
/* 174 */     if (itemstack != null) {
/* 175 */       int j2 = 8;
/* 176 */       int k2 = (this.draggedStack == null) ? 8 : 16;
/* 177 */       String s = null;
/*     */       
/* 179 */       if (this.draggedStack != null && this.isRightMouseClick) {
/* 180 */         itemstack = itemstack.copy();
/* 181 */         itemstack.stackSize = MathHelper.ceiling_float_int(itemstack.stackSize / 2.0F);
/* 182 */       } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
/* 183 */         itemstack = itemstack.copy();
/* 184 */         itemstack.stackSize = this.dragSplittingRemnant;
/*     */         
/* 186 */         if (itemstack.stackSize == 0) {
/* 187 */           s = "" + EnumChatFormatting.YELLOW + "0";
/*     */         }
/*     */       } 
/*     */       
/* 191 */       drawItemStack(itemstack, mouseX - i - j2, mouseY - j - k2, s);
/*     */     } 
/*     */     
/* 194 */     if (this.returningStack != null) {
/* 195 */       float f = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
/*     */       
/* 197 */       if (f >= 1.0F) {
/* 198 */         f = 1.0F;
/* 199 */         this.returningStack = null;
/*     */       } 
/*     */       
/* 202 */       int l2 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
/* 203 */       int i3 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
/* 204 */       int l1 = this.touchUpX + (int)(l2 * f);
/* 205 */       int i2 = this.touchUpY + (int)(i3 * f);
/* 206 */       drawItemStack(this.returningStack, l1, i2, (String)null);
/*     */     } 
/*     */     
/* 209 */     GlStateManager.popMatrix();
/*     */     
/* 211 */     if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
/* 212 */       ItemStack itemstack1 = this.theSlot.getStack();
/* 213 */       renderToolTip(itemstack1, mouseX, mouseY);
/*     */     } 
/*     */     
/* 216 */     GlStateManager.enableLighting();
/* 217 */     GlStateManager.enableDepth();
/* 218 */     RenderHelper.enableStandardItemLighting();
/* 219 */     if (ContainerAnimations.getInstance.isEnabled()) {
/* 220 */       GlStateManager.scale(this.width / this.translate.getX(), this.height / this.translate.getY(), 1.0F);
/* 221 */       GlStateManager.translate(-xmod, -ymod, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawItemStack(ItemStack stack, int x, int y, String altText) {
/* 229 */     GlStateManager.translate(0.0F, 0.0F, 32.0F);
/* 230 */     this.zLevel = 200.0F;
/* 231 */     this.itemRender.zLevel = 200.0F;
/* 232 */     this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
/* 233 */     this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, stack, x, y - ((this.draggedStack == null) ? 0 : 8), altText);
/* 234 */     this.zLevel = 0.0F;
/* 235 */     this.itemRender.zLevel = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawGuiContainerBackgroundLayer(float paramFloat, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawSlot(Slot slotIn) {
/* 250 */     int i = slotIn.xDisplayPosition;
/* 251 */     int j = slotIn.yDisplayPosition;
/* 252 */     ItemStack itemstack = slotIn.getStack();
/* 253 */     boolean flag = false;
/* 254 */     boolean flag1 = (slotIn == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick);
/* 255 */     ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
/* 256 */     String s = null;
/*     */     
/* 258 */     if (slotIn == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null) {
/* 259 */       itemstack = itemstack.copy();
/* 260 */       itemstack.stackSize /= 2;
/* 261 */     } else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && itemstack1 != null) {
/* 262 */       if (this.dragSplittingSlots.size() == 1) {
/*     */         return;
/*     */       }
/*     */       
/* 266 */       if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
/* 267 */         itemstack = itemstack1.copy();
/* 268 */         flag = true;
/* 269 */         Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, (slotIn.getStack() == null) ? 0 : (slotIn.getStack()).stackSize);
/*     */         
/* 271 */         if (itemstack.stackSize > itemstack.getMaxStackSize()) {
/* 272 */           s = EnumChatFormatting.YELLOW + "" + itemstack.getMaxStackSize();
/* 273 */           itemstack.stackSize = itemstack.getMaxStackSize();
/*     */         } 
/*     */         
/* 276 */         if (itemstack.stackSize > slotIn.getItemStackLimit(itemstack)) {
/* 277 */           s = EnumChatFormatting.YELLOW + "" + slotIn.getItemStackLimit(itemstack);
/* 278 */           itemstack.stackSize = slotIn.getItemStackLimit(itemstack);
/*     */         } 
/*     */       } else {
/* 281 */         this.dragSplittingSlots.remove(slotIn);
/* 282 */         updateDragSplitting();
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     this.zLevel = 100.0F;
/* 287 */     this.itemRender.zLevel = 100.0F;
/*     */     
/* 289 */     if (itemstack == null) {
/* 290 */       String s1 = slotIn.getSlotTexture();
/*     */       
/* 292 */       if (s1 != null) {
/* 293 */         TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite(s1);
/* 294 */         GlStateManager.disableLighting();
/* 295 */         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 296 */         drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
/* 297 */         GlStateManager.enableLighting();
/* 298 */         flag1 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     if (!flag1) {
/* 303 */       if (flag) {
/* 304 */         drawRect(i, j, i + 16, j + 16, -2130706433);
/*     */       }
/*     */       
/* 307 */       GlStateManager.enableDepth();
/* 308 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack, i, j);
/* 309 */       this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, i, j, s);
/*     */     } 
/*     */     
/* 312 */     this.itemRender.zLevel = 0.0F;
/* 313 */     this.zLevel = 0.0F;
/*     */   }
/*     */   
/*     */   private void updateDragSplitting() {
/* 317 */     ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();
/*     */     
/* 319 */     if (itemstack != null && this.dragSplitting) {
/* 320 */       this.dragSplittingRemnant = itemstack.stackSize;
/*     */       
/* 322 */       for (Slot slot : this.dragSplittingSlots) {
/* 323 */         ItemStack itemstack1 = itemstack.copy();
/* 324 */         int i = (slot.getStack() == null) ? 0 : (slot.getStack()).stackSize;
/* 325 */         Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
/*     */         
/* 327 */         if (itemstack1.stackSize > itemstack1.getMaxStackSize()) {
/* 328 */           itemstack1.stackSize = itemstack1.getMaxStackSize();
/*     */         }
/*     */         
/* 331 */         if (itemstack1.stackSize > slot.getItemStackLimit(itemstack1)) {
/* 332 */           itemstack1.stackSize = slot.getItemStackLimit(itemstack1);
/*     */         }
/*     */         
/* 335 */         this.dragSplittingRemnant -= itemstack1.stackSize - i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Slot getSlotAtPosition(int x, int y) {
/* 344 */     for (int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
/* 345 */       Slot slot = this.inventorySlots.inventorySlots.get(i);
/*     */       
/* 347 */       if (isMouseOverSlot(slot, x, y)) {
/* 348 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 352 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 359 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 360 */     boolean flag = (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100);
/* 361 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 362 */     long i = Minecraft.getSystemTime();
/* 363 */     this.doubleClick = (this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == mouseButton);
/* 364 */     this.ignoreMouseUp = false;
/*     */     
/* 366 */     for (GuiButton aButtonList : this.buttonList) {
/*     */       
/* 368 */       if (aButtonList.mousePressed(this.mc, mouseX, mouseY) && aButtonList.id == 6969) {
/* 369 */         KillAura.getInstance.setEnabled(false);
/* 370 */         AdvancedAura.getInstance.setEnabled(false); continue;
/* 371 */       }  if (aButtonList.mousePressed(this.mc, mouseX, mouseY) && aButtonList.id == 696969) {
/* 372 */         TPAura.getInstance.setEnabled(false); continue;
/* 373 */       }  if (aButtonList.mousePressed(this.mc, mouseX, mouseY) && aButtonList.id == 69696969) {
/* 374 */         InventoryManager.getInstance.setEnabled(false); continue;
/* 375 */       }  if (aButtonList.mousePressed(this.mc, mouseX, mouseY) && aButtonList.id == 696969969) {
/* 376 */         ChestStealer.getInstance.setEnabled(false);
/*     */       }
/*     */     } 
/* 379 */     if (mouseButton == 0 || mouseButton == 1 || flag) {
/* 380 */       int j = this.guiLeft;
/* 381 */       int k = this.guiTop;
/* 382 */       boolean flag1 = (mouseX < j || mouseY < k || mouseX >= j + this.xSize || mouseY >= k + this.ySize);
/* 383 */       int l = -1;
/*     */       
/* 385 */       if (slot != null) {
/* 386 */         l = slot.slotNumber;
/*     */       }
/*     */       
/* 389 */       if (flag1) {
/* 390 */         l = -999;
/*     */       }
/*     */       
/* 393 */       if (this.mc.gameSettings.touchscreen && flag1 && this.mc.thePlayer.inventory.getItemStack() == null) {
/* 394 */         this.mc.displayGuiScreen((GuiScreen)null);
/*     */         
/*     */         return;
/*     */       } 
/* 398 */       if (l != -1) {
/* 399 */         if (this.mc.gameSettings.touchscreen) {
/* 400 */           if (slot != null && slot.getHasStack()) {
/* 401 */             this.clickedSlot = slot;
/* 402 */             this.draggedStack = null;
/* 403 */             this.isRightMouseClick = (mouseButton == 1);
/*     */           } else {
/* 405 */             this.clickedSlot = null;
/*     */           } 
/* 407 */         } else if (!this.dragSplitting) {
/* 408 */           if (this.mc.thePlayer.inventory.getItemStack() == null) {
/* 409 */             if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
/* 410 */               handleMouseClick(slot, l, mouseButton, 3);
/*     */             } else {
/* 412 */               boolean flag2 = (l != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/* 413 */               int i1 = 0;
/*     */               
/* 415 */               if (flag2) {
/* 416 */                 this.shiftClickedSlot = (slot != null && slot.getHasStack()) ? slot.getStack() : null;
/* 417 */                 i1 = 1;
/* 418 */               } else if (l == -999) {
/* 419 */                 i1 = 4;
/*     */               } 
/*     */               
/* 422 */               handleMouseClick(slot, l, mouseButton, i1);
/*     */             } 
/*     */             
/* 425 */             this.ignoreMouseUp = true;
/*     */           } else {
/* 427 */             this.dragSplitting = true;
/* 428 */             this.dragSplittingButton = mouseButton;
/* 429 */             this.dragSplittingSlots.clear();
/*     */             
/* 431 */             if (mouseButton == 0) {
/* 432 */               this.dragSplittingLimit = 0;
/* 433 */             } else if (mouseButton == 1) {
/* 434 */               this.dragSplittingLimit = 1;
/* 435 */             } else if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
/* 436 */               this.dragSplittingLimit = 2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 443 */     this.lastClickSlot = slot;
/* 444 */     this.lastClickTime = i;
/* 445 */     this.lastClickButton = mouseButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 453 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 454 */     ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();
/*     */     
/* 456 */     if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
/* 457 */       if (clickedMouseButton == 0 || clickedMouseButton == 1) {
/* 458 */         if (this.draggedStack == null) {
/* 459 */           if (slot != this.clickedSlot && this.clickedSlot.getStack() != null) {
/* 460 */             this.draggedStack = this.clickedSlot.getStack().copy();
/*     */           }
/* 462 */         } else if (this.draggedStack.stackSize > 1 && slot != null && Container.canAddItemToSlot(slot, this.draggedStack, false)) {
/* 463 */           long i = Minecraft.getSystemTime();
/*     */           
/* 465 */           if (this.currentDragTargetSlot == slot) {
/* 466 */             if (i - this.dragItemDropDelay > 500L) {
/* 467 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
/* 468 */               handleMouseClick(slot, slot.slotNumber, 1, 0);
/* 469 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
/* 470 */               this.dragItemDropDelay = i + 750L;
/* 471 */               this.draggedStack.stackSize--;
/*     */             } 
/*     */           } else {
/* 474 */             this.currentDragTargetSlot = slot;
/* 475 */             this.dragItemDropDelay = i;
/*     */           } 
/*     */         } 
/*     */       }
/* 479 */     } else if (this.dragSplitting && slot != null && itemstack != null && itemstack.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.inventorySlots.canDragIntoSlot(slot)) {
/* 480 */       this.dragSplittingSlots.add(slot);
/* 481 */       updateDragSplitting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 489 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 490 */     int i = this.guiLeft;
/* 491 */     int j = this.guiTop;
/* 492 */     boolean flag = (mouseX < i || mouseY < j || mouseX >= i + this.xSize || mouseY >= j + this.ySize);
/* 493 */     int k = -1;
/*     */     
/* 495 */     if (slot != null) {
/* 496 */       k = slot.slotNumber;
/*     */     }
/*     */     
/* 499 */     if (flag) {
/* 500 */       k = -999;
/*     */     }
/*     */     
/* 503 */     if (this.doubleClick && slot != null && state == 0 && this.inventorySlots.canMergeSlot((ItemStack)null, slot)) {
/* 504 */       if (isShiftKeyDown()) {
/* 505 */         if (slot != null && slot.inventory != null && this.shiftClickedSlot != null) {
/* 506 */           for (Slot slot2 : this.inventorySlots.inventorySlots) {
/* 507 */             if (slot2 != null && slot2.canTakeStack((EntityPlayer)this.mc.thePlayer) && slot2.getHasStack() && slot2.inventory == slot.inventory && Container.canAddItemToSlot(slot2, this.shiftClickedSlot, true)) {
/* 508 */               handleMouseClick(slot2, slot2.slotNumber, state, 1);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } else {
/* 513 */         handleMouseClick(slot, k, state, 6);
/*     */       } 
/*     */       
/* 516 */       this.doubleClick = false;
/* 517 */       this.lastClickTime = 0L;
/*     */     } else {
/* 519 */       if (this.dragSplitting && this.dragSplittingButton != state) {
/* 520 */         this.dragSplitting = false;
/* 521 */         this.dragSplittingSlots.clear();
/* 522 */         this.ignoreMouseUp = true;
/*     */         
/*     */         return;
/*     */       } 
/* 526 */       if (this.ignoreMouseUp) {
/* 527 */         this.ignoreMouseUp = false;
/*     */         
/*     */         return;
/*     */       } 
/* 531 */       if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
/* 532 */         if (state == 0 || state == 1) {
/* 533 */           if (this.draggedStack == null && slot != this.clickedSlot) {
/* 534 */             this.draggedStack = this.clickedSlot.getStack();
/*     */           }
/*     */           
/* 537 */           boolean flag2 = Container.canAddItemToSlot(slot, this.draggedStack, false);
/*     */           
/* 539 */           if (k != -1 && this.draggedStack != null && flag2) {
/* 540 */             handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0);
/* 541 */             handleMouseClick(slot, k, 0, 0);
/*     */             
/* 543 */             if (this.mc.thePlayer.inventory.getItemStack() != null) {
/* 544 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0);
/* 545 */               this.touchUpX = mouseX - i;
/* 546 */               this.touchUpY = mouseY - j;
/* 547 */               this.returningStackDestSlot = this.clickedSlot;
/* 548 */               this.returningStack = this.draggedStack;
/* 549 */               this.returningStackTime = Minecraft.getSystemTime();
/*     */             } else {
/* 551 */               this.returningStack = null;
/*     */             } 
/* 553 */           } else if (this.draggedStack != null) {
/* 554 */             this.touchUpX = mouseX - i;
/* 555 */             this.touchUpY = mouseY - j;
/* 556 */             this.returningStackDestSlot = this.clickedSlot;
/* 557 */             this.returningStack = this.draggedStack;
/* 558 */             this.returningStackTime = Minecraft.getSystemTime();
/*     */           } 
/*     */           
/* 561 */           this.draggedStack = null;
/* 562 */           this.clickedSlot = null;
/*     */         } 
/* 564 */       } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
/* 565 */         handleMouseClick((Slot)null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);
/*     */         
/* 567 */         for (Slot slot1 : this.dragSplittingSlots) {
/* 568 */           handleMouseClick(slot1, slot1.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
/*     */         }
/*     */         
/* 571 */         handleMouseClick((Slot)null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
/* 572 */       } else if (this.mc.thePlayer.inventory.getItemStack() != null) {
/* 573 */         if (state == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
/* 574 */           handleMouseClick(slot, k, state, 3);
/*     */         } else {
/* 576 */           boolean flag1 = (k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/*     */           
/* 578 */           if (flag1) {
/* 579 */             this.shiftClickedSlot = (slot != null && slot.getHasStack()) ? slot.getStack() : null;
/*     */           }
/*     */           
/* 582 */           handleMouseClick(slot, k, state, flag1 ? 1 : 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 587 */     if (this.mc.thePlayer.inventory.getItemStack() == null) {
/* 588 */       this.lastClickTime = 0L;
/*     */     }
/*     */     
/* 591 */     this.dragSplitting = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
/* 598 */     return isPointInRegion(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isPointInRegion(int left, int top, int right, int bottom, int pointX, int pointY) {
/* 606 */     int i = this.guiLeft;
/* 607 */     int j = this.guiTop;
/* 608 */     pointX -= i;
/* 609 */     pointY -= j;
/* 610 */     return (pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
/* 617 */     if (slotIn != null) {
/* 618 */       slotId = slotIn.slotNumber;
/*     */     }
/*     */     
/* 621 */     this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, clickedButton, clickType, (EntityPlayer)this.mc.thePlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 629 */     if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
/* 630 */       this.mc.thePlayer.closeScreen();
/*     */     }
/*     */     
/* 633 */     checkHotbarKeys(keyCode);
/*     */     
/* 635 */     if (this.theSlot != null && this.theSlot.getHasStack()) {
/* 636 */       if (keyCode == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
/* 637 */         handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
/* 638 */       } else if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
/* 639 */         handleMouseClick(this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkHotbarKeys(int keyCode) {
/* 649 */     if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
/* 650 */       for (int i = 0; i < 9; i++) {
/* 651 */         if (keyCode == this.mc.gameSettings.keyBindsHotbar[i].getKeyCode()) {
/* 652 */           handleMouseClick(this.theSlot, this.theSlot.slotNumber, i, 2);
/* 653 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 658 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 665 */     if (this.mc.thePlayer != null) {
/* 666 */       this.inventorySlots.onContainerClosed((EntityPlayer)this.mc.thePlayer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 674 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 681 */     super.updateScreen();
/*     */     
/* 683 */     if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
/* 684 */       this.mc.thePlayer.closeScreen(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\inventory\GuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */