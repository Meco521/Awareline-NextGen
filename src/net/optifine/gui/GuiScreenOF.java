/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiVideoSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiScreenOF
/*    */   extends GuiScreen
/*    */ {
/*    */   protected void actionPerformedRightClick(GuiButton button) {}
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 20 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */     
/* 22 */     if (mouseButton == 1) {
/*    */       
/* 24 */       GuiButton guibutton = getSelectedButton(mouseX, mouseY, this.buttonList);
/*    */       
/* 26 */       if (guibutton != null && guibutton.enabled) {
/*    */         
/* 28 */         guibutton.playPressSound(this.mc.getSoundHandler());
/* 29 */         actionPerformedRightClick(guibutton);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static GuiButton getSelectedButton(int x, int y, List<GuiButton> listButtons) {
/* 36 */     for (int i = 0; i < listButtons.size(); i++) {
/*    */       
/* 38 */       GuiButton guibutton = listButtons.get(i);
/*    */       
/* 40 */       if (guibutton.visible) {
/*    */         
/* 42 */         int j = GuiVideoSettings.getButtonWidth(guibutton);
/* 43 */         int k = GuiVideoSettings.getButtonHeight(guibutton);
/*    */         
/* 45 */         if (x >= guibutton.xPosition && y >= guibutton.yPosition && x < guibutton.xPosition + j && y < guibutton.yPosition + k)
/*    */         {
/* 47 */           return guibutton;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiScreenOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */