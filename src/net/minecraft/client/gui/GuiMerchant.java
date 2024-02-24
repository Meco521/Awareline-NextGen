/*     */ package net.minecraft.client.gui;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerMerchant;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiMerchant extends GuiContainer {
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*  28 */   static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMerchant merchant;
/*     */ 
/*     */   
/*     */   private MerchantButton nextButton;
/*     */ 
/*     */   
/*     */   private MerchantButton previousButton;
/*     */ 
/*     */   
/*     */   private int selectedMerchantRecipe;
/*     */ 
/*     */   
/*     */   private final IChatComponent chatComponent;
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMerchant(InventoryPlayer p_i45500_1_, IMerchant p_i45500_2_, World worldIn) {
/*  49 */     super((Container)new ContainerMerchant(p_i45500_1_, p_i45500_2_, worldIn));
/*  50 */     this.merchant = p_i45500_2_;
/*  51 */     this.chatComponent = p_i45500_2_.getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  60 */     super.initGui();
/*  61 */     int i = (this.width - this.xSize) / 2;
/*  62 */     int j = (this.height - this.ySize) / 2;
/*  63 */     this.buttonList.add(this.nextButton = new MerchantButton(1, i + 120 + 27, j + 24 - 1, true));
/*  64 */     this.buttonList.add(this.previousButton = new MerchantButton(2, i + 36 - 19, j + 24 - 1, false));
/*  65 */     this.nextButton.enabled = false;
/*  66 */     this.previousButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  74 */     String s = this.chatComponent.getUnformattedText();
/*  75 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/*  76 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  84 */     super.updateScreen();
/*  85 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */     
/*  87 */     if (merchantrecipelist != null) {
/*     */       
/*  89 */       this.nextButton.enabled = (this.selectedMerchantRecipe < merchantrecipelist.size() - 1);
/*  90 */       this.previousButton.enabled = (this.selectedMerchantRecipe > 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  98 */     boolean flag = false;
/*     */     
/* 100 */     if (button == this.nextButton) {
/*     */       
/* 102 */       this.selectedMerchantRecipe++;
/* 103 */       MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */       
/* 105 */       if (merchantrecipelist != null && this.selectedMerchantRecipe >= merchantrecipelist.size())
/*     */       {
/* 107 */         this.selectedMerchantRecipe = merchantrecipelist.size() - 1;
/*     */       }
/*     */       
/* 110 */       flag = true;
/*     */     }
/* 112 */     else if (button == this.previousButton) {
/*     */       
/* 114 */       this.selectedMerchantRecipe--;
/*     */       
/* 116 */       if (this.selectedMerchantRecipe < 0)
/*     */       {
/* 118 */         this.selectedMerchantRecipe = 0;
/*     */       }
/*     */       
/* 121 */       flag = true;
/*     */     } 
/*     */     
/* 124 */     if (flag) {
/*     */       
/* 126 */       ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
/* 127 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 128 */       packetbuffer.writeInt(this.selectedMerchantRecipe);
/* 129 */       this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload("MC|TrSel", packetbuffer));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 138 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 139 */     this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
/* 140 */     int i = (this.width - this.xSize) / 2;
/* 141 */     int j = (this.height - this.ySize) / 2;
/* 142 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 143 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */     
/* 145 */     if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
/*     */       
/* 147 */       int k = this.selectedMerchantRecipe;
/*     */       
/* 149 */       if (k < 0 || k >= merchantrecipelist.size()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 154 */       MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);
/*     */       
/* 156 */       if (merchantrecipe.isRecipeDisabled()) {
/*     */         
/* 158 */         this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
/* 159 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 160 */         GlStateManager.disableLighting();
/* 161 */         drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
/* 162 */         drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 172 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 173 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */     
/* 175 */     if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
/*     */       
/* 177 */       int i = (this.width - this.xSize) / 2;
/* 178 */       int j = (this.height - this.ySize) / 2;
/* 179 */       int k = this.selectedMerchantRecipe;
/* 180 */       MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);
/* 181 */       ItemStack itemstack = merchantrecipe.getItemToBuy();
/* 182 */       ItemStack itemstack1 = merchantrecipe.getSecondItemToBuy();
/* 183 */       ItemStack itemstack2 = merchantrecipe.getItemToSell();
/* 184 */       GlStateManager.pushMatrix();
/* 185 */       RenderHelper.enableGUIStandardItemLighting();
/* 186 */       GlStateManager.disableLighting();
/* 187 */       GlStateManager.enableRescaleNormal();
/* 188 */       GlStateManager.enableColorMaterial();
/* 189 */       GlStateManager.enableLighting();
/* 190 */       this.itemRender.zLevel = 100.0F;
/* 191 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack, i + 36, j + 24);
/* 192 */       this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack, i + 36, j + 24);
/*     */       
/* 194 */       if (itemstack1 != null) {
/*     */         
/* 196 */         this.itemRender.renderItemAndEffectIntoGUI(itemstack1, i + 62, j + 24);
/* 197 */         this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack1, i + 62, j + 24);
/*     */       } 
/*     */       
/* 200 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack2, i + 120, j + 24);
/* 201 */       this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack2, i + 120, j + 24);
/* 202 */       this.itemRender.zLevel = 0.0F;
/* 203 */       GlStateManager.disableLighting();
/*     */       
/* 205 */       if (isPointInRegion(36, 24, 16, 16, mouseX, mouseY) && itemstack != null) {
/*     */         
/* 207 */         renderToolTip(itemstack, mouseX, mouseY);
/*     */       }
/* 209 */       else if (itemstack1 != null && isPointInRegion(62, 24, 16, 16, mouseX, mouseY) && itemstack1 != null) {
/*     */         
/* 211 */         renderToolTip(itemstack1, mouseX, mouseY);
/*     */       }
/* 213 */       else if (itemstack2 != null && isPointInRegion(120, 24, 16, 16, mouseX, mouseY) && itemstack2 != null) {
/*     */         
/* 215 */         renderToolTip(itemstack2, mouseX, mouseY);
/*     */       }
/* 217 */       else if (merchantrecipe.isRecipeDisabled() && (isPointInRegion(83, 21, 28, 21, mouseX, mouseY) || isPointInRegion(83, 51, 28, 21, mouseX, mouseY))) {
/*     */         
/* 219 */         drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), mouseX, mouseY);
/*     */       } 
/*     */       
/* 222 */       GlStateManager.popMatrix();
/* 223 */       GlStateManager.enableLighting();
/* 224 */       GlStateManager.enableDepth();
/* 225 */       RenderHelper.enableStandardItemLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IMerchant getMerchant() {
/* 231 */     return this.merchant;
/*     */   }
/*     */   
/*     */   static class MerchantButton
/*     */     extends GuiButton
/*     */   {
/*     */     private final boolean field_146157_o;
/*     */     
/*     */     public MerchantButton(int buttonID, int x, int y, boolean p_i1095_4_) {
/* 240 */       super(buttonID, x, y, 12, 19, "");
/* 241 */       this.field_146157_o = p_i1095_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 246 */       if (this.visible) {
/*     */         
/* 248 */         mc.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
/* 249 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 250 */         boolean flag = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 251 */         int i = 0;
/* 252 */         int j = 176;
/*     */         
/* 254 */         if (!this.enabled) {
/*     */           
/* 256 */           j += this.width << 1;
/*     */         }
/* 258 */         else if (flag) {
/*     */           
/* 260 */           j += this.width;
/*     */         } 
/*     */         
/* 263 */         if (!this.field_146157_o)
/*     */         {
/* 265 */           i += this.height;
/*     */         }
/*     */         
/* 268 */         drawTexturedModalRect(this.xPosition, this.yPosition, j, i, this.width, this.height);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */