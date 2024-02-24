/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.component.impl.server.LastConnectionComponent;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientMainMenu;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.multiplayer.GuiConnecting;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiIngameMenu
/*     */   extends GuiScreen
/*     */ {
/*     */   public void initGui() {
/*  24 */     this.buttonList.clear();
/*  25 */     int i = -16;
/*  26 */     int padding = 3;
/*     */     
/*  28 */     if (!this.mc.isIntegratedServerRunning()) {
/*  29 */       this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + -16, 100 - padding, 20, "Disconnect"));
/*     */       
/*  31 */       this.buttonList.add(new GuiButton(8, this.width / 2 + padding, this.height / 4 + 120 + -16, 100 - padding, 20, "Reconnect"));
/*     */ 
/*     */       
/*  34 */       ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
/*     */     } else {
/*  36 */       this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + -16, I18n.format("menu.returnToMenu", new Object[0])));
/*     */     } 
/*     */     
/*  39 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + -16, I18n.format("menu.returnToGame", new Object[0])));
/*  40 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + -16, 98, 20, I18n.format("menu.options", new Object[0])));
/*     */     GuiButton guibutton;
/*  42 */     this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + -16, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
/*  43 */     this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + -16, 98, 20, I18n.format("gui.achievements", new Object[0])));
/*  44 */     this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + -16, 98, 20, I18n.format("gui.stats", new Object[0])));
/*  45 */     guibutton.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
/*     */   }
/*     */   
/*     */   public void initGui2() {
/*  49 */     this.buttonList.clear();
/*  50 */     int i = -16;
/*  51 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));
/*     */     
/*  53 */     if (!this.mc.isIntegratedServerRunning())
/*     */     {
/*  55 */       ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
/*     */     }
/*     */     
/*  58 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
/*  59 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
/*     */     GuiButton guibutton;
/*  61 */     this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
/*  62 */     this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
/*  63 */     this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
/*  64 */     guibutton.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed2(GuiButton button) {
/*     */     boolean flag;
/*  72 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  75 */         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */ 
/*     */       
/*     */       case 1:
/*  79 */         flag = this.mc.isIntegratedServerRunning();
/*  80 */         button.enabled = false;
/*  81 */         this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  82 */         this.mc.loadWorld((WorldClient)null);
/*  83 */         Client.instance.saveConfig();
/*  84 */         if (flag) {
/*  85 */           this.mc.displayGuiScreen((GuiScreen)new ClientMainMenu());
/*     */         } else {
/*  87 */           this.mc.displayGuiScreen(new GuiMultiplayer((GuiScreen)new ClientMainMenu()));
/*     */         } 
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 4:
/*  96 */         this.mc.displayGuiScreen((GuiScreen)null);
/*  97 */         this.mc.setIngameFocus();
/*     */ 
/*     */       
/*     */       case 5:
/* 101 */         this.mc.displayGuiScreen((GuiScreen)new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
/*     */ 
/*     */       
/*     */       case 6:
/* 105 */         this.mc.displayGuiScreen((GuiScreen)new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
/*     */       case 7:
/*     */         break;
/*     */     } 
/* 109 */     this.mc.displayGuiScreen(new GuiShareToLan(this));
/*     */   }
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 113 */     switch (button.id) {
/*     */       case 0:
/* 115 */         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */ 
/*     */       
/*     */       case 1:
/* 119 */         button.enabled = false;
/* 120 */         Client.instance.saveConfig();
/* 121 */         this.mc.leaveServer();
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */       
/*     */       case 4:
/* 128 */         this.mc.displayGuiScreen(null);
/* 129 */         this.mc.setIngameFocus();
/*     */ 
/*     */       
/*     */       case 5:
/* 133 */         this.mc.displayGuiScreen((GuiScreen)new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
/*     */ 
/*     */       
/*     */       case 6:
/* 137 */         this.mc.displayGuiScreen((GuiScreen)new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
/*     */ 
/*     */       
/*     */       case 7:
/* 141 */         this.mc.displayGuiScreen(new GuiShareToLan(this));
/*     */       case 8:
/*     */         break;
/* 144 */     }  this.mc.leaveServer();
/* 145 */     this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(new GuiMultiplayer((GuiScreen)new ClientMainMenu()), this.mc, new ServerData("", LastConnectionComponent.ip, false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 156 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 164 */     drawDefaultBackground();
/* 165 */     drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);
/* 166 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiIngameMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */