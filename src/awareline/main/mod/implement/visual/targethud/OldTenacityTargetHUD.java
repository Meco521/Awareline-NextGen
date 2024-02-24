/*    */ package awareline.main.mod.implement.visual.targethud;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import awareline.main.utility.animations.ContinualAnimation;
/*    */ import awareline.main.utility.math.MathUtil;
/*    */ import awareline.main.utility.render.RoundedUtil;
/*    */ import awareline.main.utility.render.render.blur.util.StencilUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class OldTenacityTargetHUD
/*    */   extends TargetHUD
/*    */ {
/* 19 */   private final ContinualAnimation animation = new ContinualAnimation();
/*    */   
/*    */   public OldTenacityTargetHUD() {
/* 22 */     super("Classic");
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float x, float y, float alpha, EntityLivingBase target) {
/* 27 */     setWidth(Math.max(145, Client.instance.FontLoaders.regular32.getStringWidth(target.getName()) + 40));
/* 28 */     setHeight(37.0F);
/* 29 */     if (alpha < 0.23D) {
/* 30 */       alpha = 0.23F;
/*    */     }
/* 32 */     Color c1 = ColorUtil.applyOpacity(Client.instance.getClientColorNoHash(255), alpha);
/* 33 */     Color c2 = ColorUtil.applyOpacity(Client.instance.getClientColorNoHash(255), alpha);
/* 34 */     Color color = new Color(20, 18, 18, (int)(90.0F * alpha));
/*    */ 
/*    */     
/* 37 */     int textColor = ColorUtil.applyOpacity(-1, alpha);
/* 38 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 39 */     RoundedUtil.drawRound(x, y, getWidth(), getHeight(), 4.0F, color);
/*    */     
/* 41 */     if (target instanceof AbstractClientPlayer) {
/* 42 */       StencilUtil.initStencilToWrite();
/* 43 */       RenderUtil.renderRoundedRect(x + 3.0F, y + 3.0F, 31.0F, 31.0F, 4.0F, -1);
/* 44 */       StencilUtil.readStencilBuffer(1);
/*    */       
/* 46 */       RenderUtil.color(-1, alpha);
/*    */       
/* 48 */       renderPlayer2D(x + 3.0F, y + 3.0F, 31.0F, 31.0F, (AbstractClientPlayer)target);
/* 49 */       StencilUtil.uninitStencilBuffer();
/* 50 */       GlStateManager.disableBlend();
/*    */     } else {
/*    */       
/* 53 */       Client.instance.FontLoaders.regular32.drawCenteredStringWithShadow("?", x + 20.0F, y + 17.0F - Client.instance.FontLoaders.regular32.getHeight() / 2.0F, textColor);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 58 */     Client.instance.FontLoaders.regular26.drawStringWithShadow(target.getName(), (x + 39.0F), (y + 5.0F), textColor);
/*    */     
/* 60 */     float healthPercent = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0.0F, 1.0F);
/*    */     
/* 62 */     float realHealthWidth = getWidth() - 44.0F;
/* 63 */     float realHealthHeight = 3.0F;
/* 64 */     this.animation.animate(realHealthWidth * healthPercent, 18);
/* 65 */     Color backgroundHealthColor = new Color(0, 0, 0, (int)alpha * 110);
/* 66 */     float healthWidth = this.animation.getOutput();
/* 67 */     RoundedUtil.drawRound(x + 39.0F, y + getHeight() - 12.0F, 98.0F, realHealthHeight, 1.5F, backgroundHealthColor);
/* 68 */     RoundedUtil.drawGradientHorizontal(x + 39.0F, y + getHeight() - 12.0F, healthWidth, realHealthHeight, 1.5F, c1, c2);
/*    */     
/* 70 */     String healthText = (int)MathUtil.round((healthPercent * 100.0F), 0.01D) + "%";
/*    */     
/* 72 */     Client.instance.FontLoaders.SF16.drawStringWithShadow(healthText, (x + 34.0F + Math.min(Math.max(1.0F, healthWidth), realHealthWidth - 11.0F)), (y + getHeight() - (14 + Client.instance.FontLoaders.SF16
/* 73 */         .getHeight())), textColor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderEffects(float x, float y, float alpha, boolean glow) {
/* 80 */     RoundedUtil.drawRound(x, y, getWidth(), getHeight(), 4.0F, ColorUtil.applyOpacity(Color.BLACK, alpha));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\targethud\OldTenacityTargetHUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */