/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class GuiScreenServerList
/*    */   extends GuiScreen {
/*    */   private final GuiScreen field_146303_a;
/*    */   private final ServerData field_146301_f;
/*    */   private GuiTextField field_146302_g;
/*    */   
/*    */   public GuiScreenServerList(GuiScreen p_i1031_1_, ServerData p_i1031_2_) {
/* 15 */     this.field_146303_a = p_i1031_1_;
/* 16 */     this.field_146301_f = p_i1031_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 23 */     this.field_146302_g.updateCursorCounter();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 31 */     Keyboard.enableRepeatEvents(true);
/* 32 */     this.buttonList.clear();
/* 33 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectServer.select", new Object[0])));
/* 34 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/* 35 */     this.field_146302_g = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
/* 36 */     this.field_146302_g.setMaxStringLength(128);
/* 37 */     this.field_146302_g.setFocused(true);
/* 38 */     this.field_146302_g.setText(this.mc.gameSettings.lastServer);
/* 39 */     ((GuiButton)this.buttonList.get(0)).enabled = (!this.field_146302_g.getText().isEmpty() && (this.field_146302_g.getText().split(":")).length > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onGuiClosed() {
/* 46 */     Keyboard.enableRepeatEvents(false);
/* 47 */     this.mc.gameSettings.lastServer = this.field_146302_g.getText();
/* 48 */     this.mc.gameSettings.saveOptions();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 55 */     if (button.enabled) {
/* 56 */       if (button.id == 1) {
/* 57 */         this.field_146303_a.confirmClicked(false, 0);
/* 58 */       } else if (button.id == 0) {
/* 59 */         this.field_146301_f.serverIP = this.field_146302_g.getText();
/* 60 */         this.field_146303_a.confirmClicked(true, 0);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 70 */     if (this.field_146302_g.textboxKeyTyped(typedChar, keyCode)) {
/* 71 */       ((GuiButton)this.buttonList.get(0)).enabled = (!this.field_146302_g.getText().isEmpty() && (this.field_146302_g.getText().split(":")).length > 0);
/* 72 */     } else if (keyCode == 28 || keyCode == 156) {
/* 73 */       actionPerformed(this.buttonList.get(0));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 81 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 82 */     this.field_146302_g.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 89 */     drawDefaultBackground();
/* 90 */     drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct", new Object[0]), this.width / 2, 20, 16777215);
/* 91 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 100, 10526880);
/* 92 */     this.field_146302_g.drawTextBox();
/* 93 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiScreenServerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */