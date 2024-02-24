/*    */ package awareline.main.ui.gui.hud.minekey;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.mod.implement.visual.HUD;
/*    */ import awareline.main.ui.animations.AnimationUtil;
/*    */ import awareline.main.ui.gui.BlurBuffer;
/*    */ import awareline.main.ui.simplecore.SimpleRender;
/*    */ import awareline.main.utility.render.RenderUtil;
/*    */ import awareline.main.utility.render.color.Colors;
/*    */ import java.awt.Color;
/*    */ import org.lwjgl.input.Mouse;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class SimpleWhiteMouseButton
/*    */ {
/* 16 */   private final String[] BUTTONS = new String[] { "LMB", "RMB" };
/*    */   private final int button;
/*    */   private final float xOffset;
/*    */   private final float yOffset;
/*    */   private float aniRadius;
/*    */   private float aniOp;
/* 22 */   Color textColor2 = new Color(255, 255, 255);
/*    */   
/*    */   public SimpleWhiteMouseButton(int button, float xOffset) {
/* 25 */     this.button = button;
/* 26 */     this.xOffset = xOffset;
/* 27 */     this.yOffset = 50.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderMouseButton(float x, float y) {
/* 33 */     boolean pressed = Mouse.isButtonDown(this.button);
/* 34 */     String name = this.BUTTONS[this.button];
/* 35 */     if (pressed) {
/* 36 */       this.textColor2 = AnimationUtil.getColorAnimationState(this.textColor2, new Color(20, 20, 20), 800.0D);
/* 37 */       this.aniRadius = AnimationUtil.moveUD(this.aniRadius, 20.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 38 */       this.aniOp = AnimationUtil.getAnimationStateFlux(this.aniOp, 0.55F, 5.0F);
/*    */     } else {
/* 40 */       this.textColor2 = AnimationUtil.getColorAnimationState(this.textColor2, new Color(255, 255, 255), 1000.0D);
/* 41 */       this.aniRadius = AnimationUtil.moveUD(this.aniRadius, 0.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 42 */       this.aniOp = AnimationUtil.getAnimationStateFlux(this.aniOp, 0.0F, 5.0F);
/*    */     } 
/* 44 */     if (HUD.getInstance.isEnabled() && ((Boolean)HUD.getInstance.blur.get()).booleanValue()) {
/* 45 */       BlurBuffer.blurArea(x + this.xOffset, y + this.yOffset, 34.0F, 22.0F, true);
/*    */     }
/* 47 */     SimpleRender.drawRectFloat(x + this.xOffset, y + this.yOffset, x + this.xOffset + 34.0F, y + this.yOffset + 22.0F, (new Color(0, 0, 0, 120)).getRGB());
/* 48 */     GL11.glPushMatrix();
/* 49 */     GL11.glEnable(3089);
/* 50 */     RenderUtil.doGlScissor((int)(x + this.xOffset), (int)(y + this.yOffset), 34, 22);
/* 51 */     RenderUtil.circle(x + this.xOffset + 17.0F, y + this.yOffset + 11.0F, this.aniRadius, SimpleRender.reAlpha(Colors.WHITE.c, this.aniOp));
/* 52 */     GL11.glDisable(3089);
/* 53 */     GL11.glPopMatrix();
/* 54 */     String realname = name.substring(0, 1);
/* 55 */     Client.instance.FontLoaders.Comfortaa22.drawString(realname, x + this.xOffset + 13.0F + (name.equals("LMB") ? 1.0F : 0.5F), y + this.yOffset + 8.0F, SimpleRender.reAlpha(this.textColor2.getRGB(), 0.9F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\hud\minekey\SimpleWhiteMouseButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */