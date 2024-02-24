/*     */ package net.minecraft.client.gui;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerRepair;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiRepair extends GuiContainer implements ICrafting {
/*  22 */   private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
/*     */   
/*     */   private final ContainerRepair anvil;
/*     */   private GuiTextField nameField;
/*     */   private final InventoryPlayer playerInventory;
/*     */   
/*     */   public GuiRepair(InventoryPlayer inventoryIn, World worldIn) {
/*  29 */     super((Container)new ContainerRepair(inventoryIn, worldIn, (EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/*  30 */     this.playerInventory = inventoryIn;
/*  31 */     this.anvil = (ContainerRepair)this.inventorySlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  40 */     super.initGui();
/*  41 */     Keyboard.enableRepeatEvents(true);
/*  42 */     int i = (this.width - this.xSize) / 2;
/*  43 */     int j = (this.height - this.ySize) / 2;
/*  44 */     this.nameField = new GuiTextField(0, this.fontRendererObj, i + 62, j + 24, 103, 12);
/*  45 */     this.nameField.setTextColor(-1);
/*  46 */     this.nameField.setDisabledTextColour(-1);
/*  47 */     this.nameField.setEnableBackgroundDrawing(false);
/*  48 */     this.nameField.setMaxStringLength(30);
/*  49 */     this.inventorySlots.removeCraftingFromCrafters(this);
/*  50 */     this.inventorySlots.onCraftGuiOpened(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  58 */     super.onGuiClosed();
/*  59 */     Keyboard.enableRepeatEvents(false);
/*  60 */     this.inventorySlots.removeCraftingFromCrafters(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  68 */     GlStateManager.disableLighting();
/*  69 */     GlStateManager.disableBlend();
/*  70 */     this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60, 6, 4210752);
/*     */     
/*  72 */     if (this.anvil.maximumCost > 0) {
/*     */       
/*  74 */       int i = 8453920;
/*  75 */       boolean flag = true;
/*  76 */       String s = I18n.format("container.repair.cost", new Object[] { Integer.valueOf(this.anvil.maximumCost) });
/*     */       
/*  78 */       if (this.anvil.maximumCost >= 40 && !this.mc.thePlayer.capabilities.isCreativeMode) {
/*     */         
/*  80 */         s = I18n.format("container.repair.expensive", new Object[0]);
/*  81 */         i = 16736352;
/*     */       }
/*  83 */       else if (!this.anvil.getSlot(2).getHasStack()) {
/*     */         
/*  85 */         flag = false;
/*     */       }
/*  87 */       else if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
/*     */         
/*  89 */         i = 16736352;
/*     */       } 
/*     */       
/*  92 */       if (flag) {
/*     */         
/*  94 */         int j = 0xFF000000 | (i & 0xFCFCFC) >> 2 | i & 0xFF000000;
/*  95 */         int k = this.xSize - 8 - this.fontRendererObj.getStringWidth(s);
/*  96 */         int l = 67;
/*     */         
/*  98 */         if (this.fontRendererObj.getUnicodeFlag()) {
/*     */           
/* 100 */           drawRect(k - 3, l - 2, this.xSize - 7, l + 10, -16777216);
/* 101 */           drawRect(k - 2, l - 1, this.xSize - 8, l + 9, -12895429);
/*     */         }
/*     */         else {
/*     */           
/* 105 */           this.fontRendererObj.drawString(s, k, l + 1, j);
/* 106 */           this.fontRendererObj.drawString(s, k + 1, l, j);
/* 107 */           this.fontRendererObj.drawString(s, k + 1, l + 1, j);
/*     */         } 
/*     */         
/* 110 */         this.fontRendererObj.drawString(s, k, l, i);
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 123 */     if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
/*     */       
/* 125 */       renameItem();
/*     */     }
/*     */     else {
/*     */       
/* 129 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renameItem() {
/* 135 */     String s = this.nameField.getText();
/* 136 */     Slot slot = this.anvil.getSlot(0);
/*     */     
/* 138 */     if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && s.equals(slot.getStack().getDisplayName()))
/*     */     {
/* 140 */       s = "";
/*     */     }
/*     */     
/* 143 */     this.anvil.updateItemName(s);
/* 144 */     this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C17PacketCustomPayload("MC|ItemName", (new PacketBuffer(Unpooled.buffer())).writeString(s)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 152 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 153 */     this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 161 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 162 */     GlStateManager.disableLighting();
/* 163 */     GlStateManager.disableBlend();
/* 164 */     this.nameField.drawTextBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 172 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 173 */     this.mc.getTextureManager().bindTexture(anvilResource);
/* 174 */     int i = (this.width - this.xSize) / 2;
/* 175 */     int j = (this.height - this.ySize) / 2;
/* 176 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 177 */     drawTexturedModalRect(i + 59, j + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
/*     */     
/* 179 */     if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack())
/*     */     {
/* 181 */       drawTexturedModalRect(i + 99, j + 45, this.xSize, 0, 28, 21);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
/* 190 */     sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/* 199 */     if (slotInd == 0) {
/*     */       
/* 201 */       this.nameField.setText((stack == null) ? "" : stack.getDisplayName());
/* 202 */       this.nameField.setEnabled((stack != null));
/*     */       
/* 204 */       if (stack != null)
/*     */       {
/* 206 */         renameItem();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {}
/*     */   
/*     */   public void sendAllWindowProperties(Container p_175173_1_, IInventory p_175173_2_) {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiRepair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */