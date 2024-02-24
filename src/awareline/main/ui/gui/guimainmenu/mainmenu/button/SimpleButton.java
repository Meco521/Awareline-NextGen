/*    */ package awareline.main.ui.gui.guimainmenu.mainmenu.button;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.SoundHandler;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ public final class SimpleButton
/*    */   extends GuiButton {
/*    */   public SimpleButton(int buttonId, int x, int y, String buttonText) {
/* 11 */     super(buttonId, x - Client.instance.FontLoaders.regular15.getStringWidth(buttonText) / 2, y, Client.instance.FontLoaders.regular15
/* 12 */         .getStringWidth(buttonText), 10, buttonText);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void playPressSound(SoundHandler soundHandlerIn) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 22 */     this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 23 */     mouseDragged(mc, mouseX, mouseY);
/* 24 */     drawRect(0, 0, 0, 0, 0);
/* 25 */     Client.instance.FontLoaders.regular15.drawCenteredString(this.displayString, (this.xPosition + this.width / 2), (this.yPosition + (this.height - 8) / 2), this.hovered ? Client.instance
/* 26 */         .getClientColor(255) : -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\mainmenu\button\SimpleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */