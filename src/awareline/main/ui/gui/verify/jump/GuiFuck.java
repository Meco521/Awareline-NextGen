/*    */ package awareline.main.ui.gui.verify.jump;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.ui.animations.AnimationUtil;
/*    */ import awareline.main.ui.font.cfont.CFontRenderer;
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import awareline.main.ui.gui.verify.VerifyLogin;
/*    */ import com.allatori.annotations.ControlFlowObfuscation;
/*    */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ @ControlFlowObfuscation("enable")
/*    */ @ExtensiveFlowObfuscation("maximum")
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class GuiFuck
/*    */   extends GuiScreen {
/*    */   private long startTime;
/* 23 */   float alpha = 1.0F;
/*    */   
/* 25 */   final float Anitext = -10.0F;
/*    */   
/*    */   private boolean i;
/* 28 */   final CFontRenderer fontwel2 = Client.instance.FontLoaders.Comfortaa24;
/* 29 */   final CFontRenderer fontwel = Client.instance.FontLoaders.Comfortaa36;
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 33 */     if (this.startTime == 0L) {
/* 34 */       this.startTime = System.currentTimeMillis();
/*    */     }
/*    */     
/* 37 */     RenderUtil.drawImage(new ResourceLocation("client/guimainmenu/BG.mc"), -30.0F, -30.0F, (this.width + 60), (this.height + 60));
/*    */     
/* 39 */     this.fontwel.drawCenteredString("Some checks is not arrange", this.width / 2.0F, this.height / 2.0F - 3.0F - -10.0F, (new Color(255, 255, 255))
/* 40 */         .getRGB());
/* 41 */     this.fontwel2.drawCenteredString("Please check your computer", this.width / 2.0F, this.height / 2.0F + this.fontwel.getStringHeight() + 15.0F - -10.0F, (new Color(255, 255, 255))
/* 42 */         .getRGB());
/* 43 */     drawRect(0, 0, this.width, this.height, (new Color(0.0F, 0.0F, 0.0F, this.alpha)).getRGB());
/*    */     
/* 45 */     if (this.i) {
/* 46 */       if (this.alpha != 1.0F) {
/* 47 */         this.alpha = AnimationUtil.getAnimationStateFlux(this.alpha, 1.0F, 3.0F);
/*    */       } else {
/* 49 */         this.mc.displayGuiScreen((GuiScreen)new VerifyLogin());
/*    */       } 
/*    */     } else {
/* 52 */       if (this.alpha != 0.0F && this.startTime + 2000L <= System.currentTimeMillis()) {
/* 53 */         this.alpha = AnimationUtil.getAnimationStateFlux(this.alpha, 0.0F, 3.0F);
/*    */       }
/* 55 */       if (this.alpha <= 0.0F && this.startTime + 10000L <= System.currentTimeMillis()) {
/* 56 */         this.i = true;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 63 */     this.i = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\verify\jump\GuiFuck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */