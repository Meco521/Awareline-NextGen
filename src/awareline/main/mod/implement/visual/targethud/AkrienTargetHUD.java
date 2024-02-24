/*    */ package awareline.main.mod.implement.visual.targethud;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*    */ import awareline.main.ui.font.cfont.CFontRenderer;
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import awareline.main.utility.animations.ContinualAnimation;
/*    */ import java.awt.Color;
/*    */ import java.text.DecimalFormat;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class AkrienTargetHUD
/*    */   extends TargetHUD {
/* 19 */   private final ContinualAnimation animation = new ContinualAnimation();
/* 20 */   private final DecimalFormat DF_1 = new DecimalFormat("0.0");
/*    */   
/*    */   public AkrienTargetHUD() {
/* 23 */     super("Akrien");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(float x, float y, float alpha, EntityLivingBase target) {
/* 30 */     CFontRenderer rubikBold = Client.instance.FontLoaders.SF18;
/* 31 */     CFontRenderer rubikSmall = Client.instance.FontLoaders.regular13;
/* 32 */     setWidth(Math.max(100, rubikBold.getStringWidth(target.getName()) + 45));
/* 33 */     setHeight(39.5F);
/*    */     
/* 35 */     double healthPercentage = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target
/* 36 */         .getAbsorptionAmount()), 0.0F, 1.0F);
/* 37 */     int bg = (new Color(0.0F, 0.0F, 0.0F, 0.4F * alpha)).getRGB();
/*    */ 
/*    */     
/* 40 */     Gui.drawRect(x, y, (x + getWidth()), (y + 39.5F), bg);
/*    */ 
/*    */     
/* 43 */     Gui.drawRect2(x + 2.5D, (y + 31.0F), getWidth() - 4.5D, 2.5D, bg);
/* 44 */     Gui.drawRect2(x + 2.5D, y + 34.5D, getWidth() - 4.5D, 2.5D, bg);
/*    */ 
/*    */     
/* 47 */     float endWidth = (float)Math.max(0.0D, (getWidth() - 3.5D) * healthPercentage);
/* 48 */     this.animation.animate(endWidth, 18);
/*    */     
/* 50 */     if (this.animation.getOutput() > 0.0F) {
/* 51 */       RenderUtil.drawGradientRectBordered(x + 2.5D, (y + 31.0F), x + 1.5D + this.animation.getOutput(), y + 33.5D, 0.74D, 
/* 52 */           ColorUtil.applyOpacity(-16737215, alpha), 
/* 53 */           ColorUtil.applyOpacity(-7405631, alpha), bg, bg);
/*    */     }
/* 55 */     double armorValue = target.getTotalArmorValue() / 20.0D;
/* 56 */     if (armorValue > 0.0D) {
/* 57 */       RenderUtil.drawGradientRectBordered(x + 2.5D, y + 34.5D, x + 1.5D + (getWidth() - 3.5D) * armorValue, (y + 37.0F), 0.74D, 
/* 58 */           ColorUtil.applyOpacity(-16750672, alpha), 
/* 59 */           ColorUtil.applyOpacity(-12986881, alpha), bg, bg);
/*    */     }
/*    */ 
/*    */     
/* 63 */     GlStateManager.pushMatrix();
/* 64 */     RenderUtil.setAlphaLimit(0.0F);
/* 65 */     int textColor = ColorUtil.applyOpacity(-1, alpha);
/* 66 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 67 */     if (target instanceof AbstractClientPlayer) {
/* 68 */       RenderUtil.color(textColor);
/* 69 */       float f = 0.8125F;
/* 70 */       GlStateManager.scale(f, f, f);
/* 71 */       renderPlayer2D(x / f + 3.0F, y / f + 3.0F, 32.0F, 32.0F, (AbstractClientPlayer)target);
/*    */     } else {
/* 73 */       Gui.drawRect2((x + 3.0F), (y + 3.0F), 25.0D, 25.0D, bg);
/* 74 */       GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*    */       
/* 76 */       rubikBold.drawStringWithShadow("?", ((x + 11.0F) / 2.0F), ((y + 11.0F) / 2.0F), textColor);
/*    */     } 
/* 78 */     GlStateManager.popMatrix();
/*    */ 
/*    */     
/* 81 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 82 */     rubikBold.drawString(target.getName(), x + 31.0F, y + 5.0F, textColor);
/* 83 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 84 */     rubikSmall.drawString("Health: " + this.DF_1.format(target.getHealth()), x + 31.0F, y + 15.0F, textColor);
/* 85 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 86 */     rubikSmall.drawString("Distance: " + this.DF_1.format(mc.thePlayer.getDistanceToEntity((Entity)target)) + "m", x + 31.0F, y + 22.0F, textColor);
/* 87 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderEffects(float x, float y, float alpha, boolean glow) {
/* 93 */     Gui.drawRect(x, y, (x + getWidth()), (y + 39.5F), ColorUtil.applyOpacity(Color.BLACK.getRGB(), alpha));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\targethud\AkrienTargetHUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */