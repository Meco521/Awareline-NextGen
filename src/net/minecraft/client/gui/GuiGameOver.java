/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientMainMenu;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiGameOver
/*     */   extends GuiScreen
/*     */ {
/*     */   private int enableButtonsTimer;
/*     */   private final boolean field_146346_f = false;
/*     */   
/*     */   public void initGui() {
/*  23 */     this.buttonList.clear();
/*     */     
/*  25 */     if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*     */       
/*  27 */       if (this.mc.isIntegratedServerRunning())
/*     */       {
/*  29 */         this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
/*     */       }
/*     */       else
/*     */       {
/*  33 */         this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  38 */       this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
/*  39 */       this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
/*     */       
/*  41 */       if (this.mc.getSession() == null)
/*     */       {
/*  43 */         ((GuiButton)this.buttonList.get(1)).enabled = false;
/*     */       }
/*     */     } 
/*     */     
/*  47 */     for (GuiButton guibutton : this.buttonList)
/*     */     {
/*  49 */       guibutton.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*     */     GuiYesNo guiyesno;
/*  64 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  67 */         this.mc.thePlayer.respawnPlayer();
/*  68 */         this.mc.displayGuiScreen((GuiScreen)null);
/*     */         break;
/*     */       
/*     */       case 1:
/*  72 */         if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*     */ 
/*     */           
/*  75 */           this.mc.displayGuiScreen((GuiScreen)new ClientMainMenu());
/*     */           
/*     */           break;
/*     */         } 
/*  79 */         guiyesno = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
/*  80 */         this.mc.displayGuiScreen(guiyesno);
/*  81 */         guiyesno.setButtonDelay(20);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  88 */     if (result) {
/*     */       
/*  90 */       this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  91 */       this.mc.loadWorld((WorldClient)null);
/*  92 */       this.mc.displayGuiScreen((GuiScreen)new ClientMainMenu());
/*     */     }
/*     */     else {
/*     */       
/*  96 */       this.mc.thePlayer.respawnPlayer();
/*  97 */       this.mc.displayGuiScreen((GuiScreen)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 106 */     drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
/* 107 */     boolean flag = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
/* 108 */     String s = flag ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
/* 109 */     FontLoader.PF32.drawCenteredStringWithShadow(s, this.width / 2.0F, 30.0F, 16777215);
/*     */     
/* 111 */     if (flag) {
/* 112 */       FontLoader.PF16.drawCenteredStringWithShadow(I18n.format("deathScreen.hardcoreInfo", new Object[0]), this.width / 2.0F, 144.0F, 16777215);
/*     */     }
/*     */     
/* 115 */     FontLoader.PF16.drawCenteredStringWithShadow(I18n.format("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / 2.0F, 100.0F, 16777215);
/*     */     
/* 117 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen2(int mouseX, int mouseY, float partialTicks) {
/* 122 */     drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
/* 123 */     GlStateManager.pushMatrix();
/* 124 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 125 */     boolean flag = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
/* 126 */     String s = flag ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
/* 127 */     drawCenteredString(this.fontRendererObj, s, this.width / 2 / 2, 30, 16777215);
/* 128 */     GlStateManager.popMatrix();
/*     */     
/* 130 */     if (flag)
/*     */     {
/* 132 */       drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), this.width / 2, 144, 16777215);
/*     */     }
/*     */     
/* 135 */     drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / 2, 100, 16777215);
/* 136 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 152 */     super.updateScreen();
/* 153 */     this.enableButtonsTimer++;
/*     */     
/* 155 */     if (this.enableButtonsTimer == 20)
/*     */     {
/* 157 */       for (GuiButton guibutton : this.buttonList)
/*     */       {
/* 159 */         guibutton.enabled = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiGameOver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */