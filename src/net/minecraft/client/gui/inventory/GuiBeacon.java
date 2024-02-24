/*     */ package net.minecraft.client.gui.inventory;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiBeacon extends GuiContainer {
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*  26 */   static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
/*     */   
/*     */   private final IInventory tileBeacon;
/*     */   private ConfirmButton beaconConfirmButton;
/*     */   private boolean buttonsNotDrawn;
/*     */   
/*     */   public GuiBeacon(InventoryPlayer playerInventory, IInventory tileBeaconIn) {
/*  33 */     super((Container)new ContainerBeacon((IInventory)playerInventory, tileBeaconIn));
/*  34 */     this.tileBeacon = tileBeaconIn;
/*  35 */     this.xSize = 230;
/*  36 */     this.ySize = 219;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  45 */     super.initGui();
/*  46 */     this.buttonList.add(this.beaconConfirmButton = new ConfirmButton(-1, this.guiLeft + 164, this.guiTop + 107));
/*  47 */     this.buttonList.add(new CancelButton(-2, this.guiLeft + 190, this.guiTop + 107));
/*  48 */     this.buttonsNotDrawn = true;
/*  49 */     this.beaconConfirmButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  57 */     super.updateScreen();
/*  58 */     int i = this.tileBeacon.getField(0);
/*  59 */     int j = this.tileBeacon.getField(1);
/*  60 */     int k = this.tileBeacon.getField(2);
/*     */     
/*  62 */     if (this.buttonsNotDrawn && i >= 0) {
/*     */       
/*  64 */       this.buttonsNotDrawn = false;
/*     */       
/*  66 */       for (int l = 0; l <= 2; l++) {
/*     */         
/*  68 */         int i1 = (TileEntityBeacon.effectsList[l]).length;
/*  69 */         int j1 = i1 * 22 + (i1 - 1 << 1);
/*     */         
/*  71 */         for (int k1 = 0; k1 < i1; k1++) {
/*     */           
/*  73 */           int l1 = (TileEntityBeacon.effectsList[l][k1]).id;
/*  74 */           PowerButton guibeacon$powerbutton = new PowerButton(l << 8 | l1, this.guiLeft + 76 + k1 * 24 - j1 / 2, this.guiTop + 22 + l * 25, l1, l);
/*  75 */           this.buttonList.add(guibeacon$powerbutton);
/*     */           
/*  77 */           if (l >= i) {
/*     */             
/*  79 */             guibeacon$powerbutton.enabled = false;
/*     */           }
/*  81 */           else if (l1 == j) {
/*     */             
/*  83 */             guibeacon$powerbutton.func_146140_b(true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  88 */       int i2 = 3;
/*  89 */       int j2 = (TileEntityBeacon.effectsList[i2]).length + 1;
/*  90 */       int k2 = j2 * 22 + (j2 - 1 << 1);
/*     */       
/*  92 */       for (int l2 = 0; l2 < j2 - 1; l2++) {
/*     */         
/*  94 */         int i3 = (TileEntityBeacon.effectsList[i2][l2]).id;
/*  95 */         PowerButton guibeacon$powerbutton2 = new PowerButton(i2 << 8 | i3, this.guiLeft + 167 + l2 * 24 - k2 / 2, this.guiTop + 47, i3, i2);
/*  96 */         this.buttonList.add(guibeacon$powerbutton2);
/*     */         
/*  98 */         if (i2 >= i) {
/*     */           
/* 100 */           guibeacon$powerbutton2.enabled = false;
/*     */         }
/* 102 */         else if (i3 == k) {
/*     */           
/* 104 */           guibeacon$powerbutton2.func_146140_b(true);
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       if (j > 0) {
/*     */         
/* 110 */         PowerButton guibeacon$powerbutton1 = new PowerButton(i2 << 8 | j, this.guiLeft + 167 + (j2 - 1) * 24 - k2 / 2, this.guiTop + 47, j, i2);
/* 111 */         this.buttonList.add(guibeacon$powerbutton1);
/*     */         
/* 113 */         if (i2 >= i) {
/*     */           
/* 115 */           guibeacon$powerbutton1.enabled = false;
/*     */         }
/* 117 */         else if (j == k) {
/*     */           
/* 119 */           guibeacon$powerbutton1.func_146140_b(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     this.beaconConfirmButton.enabled = (this.tileBeacon.getStackInSlot(0) != null && j > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 131 */     if (button.id == -2) {
/*     */       
/* 133 */       this.mc.displayGuiScreen((GuiScreen)null);
/*     */     }
/* 135 */     else if (button.id == -1) {
/*     */       
/* 137 */       String s = "MC|Beacon";
/* 138 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 139 */       packetbuffer.writeInt(this.tileBeacon.getField(1));
/* 140 */       packetbuffer.writeInt(this.tileBeacon.getField(2));
/* 141 */       this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload(s, packetbuffer));
/* 142 */       this.mc.displayGuiScreen((GuiScreen)null);
/*     */     }
/* 144 */     else if (button instanceof PowerButton) {
/*     */       
/* 146 */       if (((PowerButton)button).func_146141_c()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 151 */       int j = button.id;
/* 152 */       int k = j & 0xFF;
/* 153 */       int i = j >> 8;
/*     */       
/* 155 */       if (i < 3) {
/*     */         
/* 157 */         this.tileBeacon.setField(1, k);
/*     */       }
/*     */       else {
/*     */         
/* 161 */         this.tileBeacon.setField(2, k);
/*     */       } 
/*     */       
/* 164 */       this.buttonList.clear();
/* 165 */       initGui();
/* 166 */       updateScreen();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 175 */     RenderHelper.disableStandardItemLighting();
/* 176 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
/* 177 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
/*     */     
/* 179 */     for (GuiButton guibutton : this.buttonList) {
/*     */       
/* 181 */       if (guibutton.isMouseOver()) {
/*     */         
/* 183 */         guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 188 */     RenderHelper.enableGUIStandardItemLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 196 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 197 */     this.mc.getTextureManager().bindTexture(beaconGuiTextures);
/* 198 */     int i = (this.width - this.xSize) / 2;
/* 199 */     int j = (this.height - this.ySize) / 2;
/* 200 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 201 */     this.itemRender.zLevel = 100.0F;
/* 202 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), i + 42, j + 109);
/* 203 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.diamond), i + 42 + 22, j + 109);
/* 204 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.gold_ingot), i + 42 + 44, j + 109);
/* 205 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.iron_ingot), i + 42 + 66, j + 109);
/* 206 */     this.itemRender.zLevel = 0.0F;
/*     */   }
/*     */   
/*     */   static class Button
/*     */     extends GuiButton
/*     */   {
/*     */     private final ResourceLocation field_146145_o;
/*     */     private final int field_146144_p;
/*     */     private final int field_146143_q;
/*     */     private boolean field_146142_r;
/*     */     
/*     */     protected Button(int p_i1077_1_, int p_i1077_2_, int p_i1077_3_, ResourceLocation p_i1077_4_, int p_i1077_5_, int p_i1077_6_) {
/* 218 */       super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
/* 219 */       this.field_146145_o = p_i1077_4_;
/* 220 */       this.field_146144_p = p_i1077_5_;
/* 221 */       this.field_146143_q = p_i1077_6_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 226 */       if (this.visible) {
/*     */         
/* 228 */         mc.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
/* 229 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 230 */         this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 231 */         int i = 219;
/* 232 */         int j = 0;
/*     */         
/* 234 */         if (!this.enabled) {
/*     */           
/* 236 */           j += this.width << 1;
/*     */         }
/* 238 */         else if (this.field_146142_r) {
/*     */           
/* 240 */           j += this.width;
/*     */         }
/* 242 */         else if (this.hovered) {
/*     */           
/* 244 */           j += this.width * 3;
/*     */         } 
/*     */         
/* 247 */         drawTexturedModalRect(this.xPosition, this.yPosition, j, i, this.width, this.height);
/*     */         
/* 249 */         if (!GuiBeacon.beaconGuiTextures.equals(this.field_146145_o))
/*     */         {
/* 251 */           mc.getTextureManager().bindTexture(this.field_146145_o);
/*     */         }
/*     */         
/* 254 */         drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_146141_c() {
/* 260 */       return this.field_146142_r;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_146140_b(boolean p_146140_1_) {
/* 265 */       this.field_146142_r = p_146140_1_;
/*     */     }
/*     */   }
/*     */   
/*     */   class CancelButton
/*     */     extends Button
/*     */   {
/*     */     public CancelButton(int p_i1074_2_, int p_i1074_3_, int p_i1074_4_) {
/* 273 */       super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.beaconGuiTextures, 112, 220);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 278 */       GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.cancel", new Object[0]), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   class ConfirmButton
/*     */     extends Button
/*     */   {
/*     */     public ConfirmButton(int p_i1075_2_, int p_i1075_3_, int p_i1075_4_) {
/* 286 */       super(p_i1075_2_, p_i1075_3_, p_i1075_4_, GuiBeacon.beaconGuiTextures, 90, 220);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 291 */       GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.done", new Object[0]), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   class PowerButton
/*     */     extends Button
/*     */   {
/*     */     private final int field_146149_p;
/*     */     private final int field_146148_q;
/*     */     
/*     */     public PowerButton(int p_i1076_2_, int p_i1076_3_, int p_i1076_4_, int p_i1076_5_, int p_i1076_6_) {
/* 302 */       super(p_i1076_2_, p_i1076_3_, p_i1076_4_, GuiContainer.inventoryBackground, Potion.potionTypes[p_i1076_5_].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() / 8 * 18);
/* 303 */       this.field_146149_p = p_i1076_5_;
/* 304 */       this.field_146148_q = p_i1076_6_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 309 */       String s = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object[0]);
/*     */       
/* 311 */       if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id)
/*     */       {
/* 313 */         s = s + " II";
/*     */       }
/*     */       
/* 316 */       GuiBeacon.this.drawCreativeTabHoveringText(s, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\inventory\GuiBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */