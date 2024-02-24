/*    */ package awareline.main.ui.gui.clickgui.mode.astolfo;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.ui.font.cfont.CFontRenderer;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class KeyBindButton
/*    */   extends ValueButton
/*    */ {
/*    */   public final Module cheat;
/*    */   public boolean bind;
/*    */   final CFontRenderer mfont;
/*    */   
/*    */   public KeyBindButton(Module cheat, int x, int y) {
/* 19 */     super(null, x, y);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     this.mfont = Client.instance.FontLoaders.bold15;
/*    */     this.custom = true;
/*    */     this.bind = false;
/*    */     this.cheat = cheat; } public void render(int mouseX, int mouseY, Limitation limitation, Window parent) {
/* 29 */     GL11.glEnable(3089);
/* 30 */     limitation.cut();
/* 31 */     Gui.drawRect(0.0D, 0.0D, 0.0D, 0.0D, 0);
/* 32 */     Gui.drawRect((this.x - 10.0F), (this.y - 4.0F), (this.x + 80.0F + parent.x), (this.y + 11.0F), (new Color(20, 20, 20)).getRGB());
/* 33 */     this.mfont.drawString("Bind:", this.x - 7.0F, this.y + 2.0F, (new Color(108, 108, 108)).getRGB());
/* 34 */     this.mfont.drawString(Keyboard.getKeyName(this.cheat.getKey()), this.x + 77.0F - this.mfont.getStringWidth(Keyboard.getKeyName(this.cheat.getKey())) + parent.x, this.y + 2.0F, (new Color(108, 108, 108)).getRGB());
/* 35 */     GL11.glDisable(3089);
/*    */   }
/*    */ 
/*    */   
/*    */   public void key(char typedChar, int keyCode) {
/* 40 */     if (this.bind) {
/* 41 */       this.cheat.setKey(keyCode);
/* 42 */       if (keyCode == 1) {
/* 43 */         this.cheat.setKey(0);
/*    */       }
/* 45 */       asClickgui.binding = false;
/* 46 */       this.bind = false;
/*    */     } 
/* 48 */     super.key(typedChar, keyCode);
/*    */   }
/*    */ 
/*    */   
/*    */   public void click(int mouseX, int mouseY, int button, Window parent) {
/* 53 */     if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + parent.allX && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.Comfortaa18.getStringHeight() + 5.0F && button == 0) {
/* 54 */       this.bind = !this.bind;
/* 55 */       asClickgui.binding = this.bind;
/*    */     } 
/* 57 */     super.click(mouseX, mouseY, button, parent);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\astolfo\KeyBindButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */