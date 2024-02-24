/*    */ package com.me.guichaguri.betterfps.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiRestartDialog
/*    */   extends GuiScreen
/*    */ {
/* 13 */   private GuiScreen parent = null;
/*    */   
/* 15 */   private final String[] message = new String[] { "You need to restart your game to apply some changes", "Do you want restart now?" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiRestartDialog(GuiScreen parent) {
/* 21 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 26 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 205, this.height - 27, I18n.format("gui.yes", new Object[0])));
/* 27 */     this.buttonList.add(new GuiButton(2, this.width / 2 + 5, this.height - 27, I18n.format("gui.no", new Object[0])));
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 32 */     drawDefaultBackground();
/* 33 */     int centerX = this.width / 2;
/* 34 */     int centerY = this.height / 2;
/* 35 */     int i = 0;
/* 36 */     for (String msg : this.message) {
/* 37 */       drawCenteredString(this.fontRendererObj, msg, centerX, centerY - (this.message.length - i) * this.fontRendererObj.FONT_HEIGHT, 16777215);
/*    */       
/* 39 */       i++;
/*    */     } 
/* 41 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 51 */     super.actionPerformed(button);
/* 52 */     if (button.id == 1) {
/* 53 */       this.mc.shutdown();
/* 54 */     } else if (button.id == 2) {
/* 55 */       this.mc.displayGuiScreen(this.parent);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\gui\GuiRestartDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */