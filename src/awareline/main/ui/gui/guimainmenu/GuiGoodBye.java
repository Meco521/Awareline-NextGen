/*    */ package awareline.main.ui.gui.guimainmenu;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.ui.font.fontmanager.UnicodeFontRenderer;
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import java.awt.Color;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiGoodBye
/*    */   extends GuiScreen
/*    */ {
/*    */   private long startTime;
/* 18 */   private int alpha = 0; public GuiGoodBye() {
/* 19 */     String[] bye2 = { "See you next time" };
/* 20 */     Random random = new Random();
/* 21 */     this.text = bye2[random.nextInt(bye2.length)];
/*    */   }
/*    */   private final String text;
/* 24 */   final UnicodeFontRenderer fontwel = Client.instance.FontManager.RobotoLight40;
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 28 */     if (this.startTime == 0L) {
/* 29 */       this.startTime = System.currentTimeMillis();
/*    */     }
/* 31 */     RenderUtil.drawImage(new ResourceLocation("client/guimainmenu/BG.mc"), 0.0F, 0.0F, this.width, this.height);
/* 32 */     Client.instance.FontManager.RobotoLight40.drawCenteredStringWithShadow(this.text, (this.width / 2), (this.height / 2 - 24), (new Color(255, 255, 255, Math.abs(Math.min(this.alpha, 255)))).getRGB());
/* 33 */     drawRect(-100, -100, this.width + 100, this.height + 100, (new Color(0, 0, 0, this.alpha)).getRGB());
/* 34 */     if (this.startTime + 2000L <= System.currentTimeMillis() && 
/* 35 */       this.alpha != 255) {
/* 36 */       this.alpha++;
/*    */     }
/*    */     
/* 39 */     if (this.alpha >= 255 && this.startTime + 3000L <= System.currentTimeMillis()) {
/* 40 */       this.mc.shutdown();
/*    */     }
/* 42 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\GuiGoodBye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */