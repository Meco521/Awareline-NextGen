/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiConfirmOpenLink
/*    */   extends GuiYesNo
/*    */ {
/*    */   private final String openLinkWarning;
/*    */   private final String copyLinkButtonText;
/*    */   private final String linkText;
/*    */   private boolean showSecurityWarning = true;
/*    */   
/*    */   public GuiConfirmOpenLink(GuiYesNoCallback p_i1084_1_, String linkTextIn, int p_i1084_3_, boolean p_i1084_4_) {
/* 17 */     super(p_i1084_1_, I18n.format(p_i1084_4_ ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), linkTextIn, p_i1084_3_);
/* 18 */     this.confirmButtonText = I18n.format(p_i1084_4_ ? "chat.link.open" : "gui.yes", new Object[0]);
/* 19 */     this.cancelButtonText = I18n.format(p_i1084_4_ ? "gui.cancel" : "gui.no", new Object[0]);
/* 20 */     this.copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
/* 21 */     this.openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
/* 22 */     this.linkText = linkTextIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 31 */     super.initGui();
/* 32 */     this.buttonList.clear();
/* 33 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 105, this.height / 6 + 96, 100, 20, this.confirmButtonText));
/* 34 */     this.buttonList.add(new GuiButton(2, this.width / 2 - 50, this.height / 6 + 96, 100, 20, this.copyLinkButtonText));
/* 35 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 50 + 105, this.height / 6 + 96, 100, 20, this.cancelButtonText));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 42 */     if (button.id == 2)
/*    */     {
/* 44 */       copyLinkToClipboard();
/*    */     }
/*    */     
/* 47 */     this.parentScreen.confirmClicked((button.id == 0), this.parentButtonClickedId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void copyLinkToClipboard() {
/* 55 */     setClipboardString(this.linkText);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 63 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     
/* 65 */     if (this.showSecurityWarning)
/*    */     {
/* 67 */       drawCenteredString(this.fontRendererObj, this.openLinkWarning, this.width / 2, 110, 16764108);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void disableSecurityWarning() {
/* 73 */     this.showSecurityWarning = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiConfirmOpenLink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */