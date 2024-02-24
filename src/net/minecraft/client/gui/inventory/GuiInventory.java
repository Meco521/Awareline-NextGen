/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ public class GuiInventory
/*     */   extends InventoryEffectRenderer
/*     */ {
/*     */   private float oldMouseX;
/*     */   private float oldMouseY;
/*     */   
/*     */   public GuiInventory(EntityPlayer p_i1094_1_) {
/*  26 */     super(p_i1094_1_.inventoryContainer);
/*  27 */     this.allowUserInput = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  35 */     if (this.mc.playerController.isInCreativeMode())
/*     */     {
/*  37 */       this.mc.displayGuiScreen((GuiScreen)new GuiContainerCreative((EntityPlayer)this.mc.thePlayer));
/*     */     }
/*     */     
/*  40 */     updateActivePotionEffects();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  49 */     this.buttonList.clear();
/*     */     
/*  51 */     if (this.mc.playerController.isInCreativeMode()) {
/*     */       
/*  53 */       this.mc.displayGuiScreen((GuiScreen)new GuiContainerCreative((EntityPlayer)this.mc.thePlayer));
/*     */     }
/*     */     else {
/*     */       
/*  57 */       super.initGui();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  66 */     this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  74 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*  75 */     this.oldMouseX = mouseX;
/*  76 */     this.oldMouseY = mouseY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/*  84 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  85 */     this.mc.getTextureManager().bindTexture(inventoryBackground);
/*  86 */     int i = this.guiLeft;
/*  87 */     int j = this.guiTop;
/*  88 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*  89 */     drawEntityOnScreen(i + 51, j + 75, 30, (i + 51) - this.oldMouseX, (j + 75 - 50) - this.oldMouseY, (EntityLivingBase)this.mc.thePlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
/*  97 */     GlStateManager.enableColorMaterial();
/*  98 */     GlStateManager.pushMatrix();
/*  99 */     GlStateManager.translate(posX, posY, 50.0F);
/* 100 */     GlStateManager.scale(-scale, scale, scale);
/* 101 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 102 */     float f = ent.renderYawOffset;
/* 103 */     float f1 = ent.rotationYaw;
/* 104 */     float f2 = ent.rotationPitch;
/* 105 */     float f3 = ent.prevRotationYawHead;
/* 106 */     float f4 = ent.rotationYawHead;
/* 107 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/* 108 */     RenderHelper.enableStandardItemLighting();
/* 109 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 110 */     GlStateManager.rotate(-((float)Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
/* 111 */     ent.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
/* 112 */     ent.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
/* 113 */     ent.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
/* 114 */     ent.rotationYawHead = ent.rotationYaw;
/* 115 */     ent.prevRotationYawHead = ent.rotationYaw;
/* 116 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 117 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 118 */     rendermanager.setPlayerViewY(180.0F);
/* 119 */     rendermanager.setRenderShadow(false);
/* 120 */     rendermanager.renderEntityWithPosYaw((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
/* 121 */     rendermanager.setRenderShadow(true);
/* 122 */     ent.renderYawOffset = f;
/* 123 */     ent.rotationYaw = f1;
/* 124 */     ent.rotationPitch = f2;
/* 125 */     ent.prevRotationYawHead = f3;
/* 126 */     ent.rotationYawHead = f4;
/* 127 */     GlStateManager.popMatrix();
/* 128 */     RenderHelper.disableStandardItemLighting();
/* 129 */     GlStateManager.disableRescaleNormal();
/* 130 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 131 */     GlStateManager.disableTexture2D();
/* 132 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 139 */     if (button.id == 0)
/*     */     {
/* 141 */       this.mc.displayGuiScreen((GuiScreen)new GuiAchievements((GuiScreen)this, this.mc.thePlayer.getStatFileWriter()));
/*     */     }
/*     */     
/* 144 */     if (button.id == 1)
/*     */     {
/* 146 */       this.mc.displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, this.mc.thePlayer.getStatFileWriter()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\inventory\GuiInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */