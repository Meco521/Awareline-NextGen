/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class GuiErrorScreen
/*    */   extends GuiScreen
/*    */ {
/*    */   private final String field_146313_a;
/*    */   private final String field_146312_f;
/*    */   
/*    */   public GuiErrorScreen(String p_i46319_1_, String p_i46319_2_) {
/* 12 */     this.field_146313_a = p_i46319_1_;
/* 13 */     this.field_146312_f = p_i46319_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 22 */     super.initGui();
/* 23 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 140, I18n.format("gui.cancel", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 31 */     drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
/* 32 */     drawCenteredString(this.fontRendererObj, this.field_146313_a, this.width / 2, 90, 16777215);
/* 33 */     drawCenteredString(this.fontRendererObj, this.field_146312_f, this.width / 2, 110, 16777215);
/* 34 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 48 */     this.mc.displayGuiScreen((GuiScreen)null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiErrorScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */