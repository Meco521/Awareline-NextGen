/*    */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiTextField;
/*    */ 
/*    */ 
/*    */ public class GuiRenameAlt
/*    */   extends GuiScreen
/*    */ {
/*    */   private final GuiAltManager manager;
/*    */   private GuiTextField nameField;
/* 15 */   private String status = "§eWaiting...";
/*    */   private GuiPasswordField pwField;
/*    */   
/*    */   public GuiRenameAlt(GuiAltManager manager) {
/* 19 */     this.manager = manager;
/*    */   }
/*    */ 
/*    */   
/*    */   public void actionPerformed(GuiButton button) {
/* 24 */     switch (button.id) {
/*    */       case 1:
/* 26 */         this.mc.displayGuiScreen(this.manager);
/*    */         break;
/*    */       
/*    */       case 0:
/* 30 */         this.manager.selectedAlt.setMask(this.nameField.getText());
/* 31 */         if (!this.pwField.getText().isEmpty()) {
/* 32 */           this.manager.selectedAlt.setPassword(this.pwField.getText());
/*    */         }
/* 34 */         this.status = "§aEdited!";
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int par1, int par2, float par3) {
/* 42 */     drawDefaultBackground();
/* 43 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString("Edit Alt", this.width / 2.0F, 10.0F, -1);
/* 44 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString(this.status, this.width / 2.0F, 20.0F, -1);
/* 45 */     this.nameField.drawTextBox();
/* 46 */     this.pwField.drawTextBox();
/* 47 */     if (this.nameField.getText().isEmpty()) {
/* 48 */       (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("New E-Mail", this.width / 2.0F - 96.0F, 66.0F, -7829368);
/*    */     }
/* 50 */     if (this.pwField.getText().isEmpty()) {
/* 51 */       (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("New Password", this.width / 2.0F - 96.0F, 106.0F, -7829368);
/*    */     }
/* 53 */     super.drawScreen(par1, par2, par3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 58 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Finish"));
/* 59 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Cancel"));
/* 60 */     this.nameField = new GuiTextField(this.eventButton, (Minecraft.getMinecraft()).fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/* 61 */     this.pwField = new GuiPasswordField((Minecraft.getMinecraft()).fontRendererObj, this.width / 2 - 100, 100, 200, 20);
/* 62 */     this.nameField.setText(this.manager.selectedAlt.getUsername());
/* 63 */     if (!this.manager.selectedAlt.getUsername().isEmpty()) this.pwField.setText(this.manager.selectedAlt.getPassword());
/*    */   
/*    */   }
/*    */   
/*    */   protected void keyTyped(char par1, int par2) {
/* 68 */     this.nameField.textboxKeyTyped(par1, par2);
/* 69 */     this.pwField.textboxKeyTyped(par1, par2);
/* 70 */     if (par1 == '\t' && (this.nameField.isFocused() || this.pwField.isFocused())) {
/* 71 */       this.nameField.setFocused(!this.nameField.isFocused());
/* 72 */       this.pwField.setFocused(!this.pwField.isFocused());
/*    */     } 
/* 74 */     if (par1 == '\r') {
/* 75 */       actionPerformed(this.buttonList.get(0));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int par1, int par2, int par3) {
/*    */     try {
/* 82 */       super.mouseClicked(par1, par2, par3);
/* 83 */     } catch (IOException e) {
/* 84 */       e.printStackTrace();
/*    */     } 
/* 86 */     this.nameField.mouseClicked(par1, par2, par3);
/* 87 */     this.pwField.mouseClicked(par1, par2, par3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\GuiRenameAlt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */