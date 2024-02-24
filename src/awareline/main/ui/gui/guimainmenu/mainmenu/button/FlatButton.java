/*    */ package awareline.main.ui.gui.guimainmenu.mainmenu.button;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.ui.animations.AnimationUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.SoundHandler;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ public class FlatButton extends GuiButton {
/*    */   final int buttonId;
/*    */   private int buttonalpha;
/*    */   private int textalpha;
/*    */   private float x2;
/*    */   private int color;
/*    */   
/*    */   public FlatButton(int buttonId, float x, float y, int widthIn, int heightIn, String buttonText) {
/* 18 */     super(buttonId, (int)x, (int)y, widthIn, heightIn, buttonText);
/* 19 */     this.buttonId = buttonId;
/* 20 */     this.x2 = -240.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 25 */     return (this.enabled && this.visible && isMouseOver());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private int rectColor() {
/* 31 */     if (this.color != (this.hovered ? 255 : 233)) {
/* 32 */       this.color = (int)AnimationUtil.moveUD(this.color, this.hovered ? 255.0F : 233.0F, 0.2F, 0.15F);
/*    */     }
/* 34 */     return (new Color(this.color, this.color, this.color)).getRGB();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 40 */     this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width + 40 && mouseY < this.yPosition + this.height);
/* 41 */     updateAlpha();
/* 42 */     this.x2 = AnimationUtil.moveUD(this.x2, this.hovered ? 40.0F : 0.0F);
/*    */ 
/*    */     
/* 45 */     Client.instance.FontLoaders.bold45.drawString(this.displayString, this.xPosition + this.width / 2.0F + this.x2 + 7.0F - Client.instance.FontLoaders.bold50
/* 46 */         .getStringWidth(this.displayString) / 2.0F, this.yPosition + (this.height - 6.0F) / 2.0F, 
/* 47 */         rectColor());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void updateAlpha() {
/* 53 */     this.buttonalpha = (int)AnimationUtil.moveUD(this.buttonalpha, 170.0F, 0.2F, 0.15F);
/* 54 */     this.textalpha = (int)AnimationUtil.moveUD(this.textalpha, 200.0F, 0.2F, 0.15F);
/*    */   }
/*    */   
/*    */   public void playPressSound(SoundHandler soundHandlerIn) {}
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\mainmenu\button\FlatButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */