/*    */ package awareline.main.ui.gui.hud.minekey;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.ui.animations.AnimationUtil;
/*    */ import awareline.main.ui.gui.BlurBuffer;
/*    */ import awareline.main.ui.simplecore.SimpleRender;
/*    */ import awareline.main.utility.render.RenderUtil;
/*    */ import awareline.main.utility.render.color.Colors;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class SimpleWhiteKey
/*    */ {
/*    */   private final KeyBinding key;
/*    */   private final float xOffset;
/*    */   private final float yOffset;
/*    */   private float aniRadius;
/*    */   private float aniOp;
/* 22 */   Color textColor2 = new Color(255, 255, 255);
/*    */   
/*    */   public SimpleWhiteKey(KeyBinding key, float xOffset, float yOffset) {
/* 25 */     this.key = key;
/* 26 */     this.xOffset = xOffset;
/* 27 */     this.yOffset = yOffset;
/*    */   }
/*    */   
/*    */   public void renderKey(float x, float y) {
/* 31 */     boolean pressed = this.key.isKeyDown();
/* 32 */     String name = Keyboard.getKeyName(this.key.getKeyCode());
/* 33 */     if (pressed) {
/* 34 */       this.textColor2 = AnimationUtil.getColorAnimationState(this.textColor2, new Color(20, 20, 20), 800.0D);
/* 35 */       this.aniRadius = AnimationUtil.moveUD(this.aniRadius, 16.0F, 
/* 36 */           SimpleRender.processFPS(0.011F), SimpleRender.processFPS(0.009F));
/* 37 */       this.aniOp = AnimationUtil.getAnimationStateFlux(this.aniOp, 0.55F, SimpleRender.processFPS(4.0F));
/*    */     } else {
/* 39 */       this.textColor2 = AnimationUtil.getColorAnimationState(this.textColor2, new Color(255, 255, 255), 1000.0D);
/* 40 */       this.aniRadius = AnimationUtil.moveUD(this.aniRadius, 0.0F, SimpleRender.processFPS(0.011F), SimpleRender.processFPS(0.009F));
/* 41 */       this.aniOp = AnimationUtil.getAnimationStateFlux(this.aniOp, 0.0F, SimpleRender.processFPS(0.1F));
/*    */     } 
/*    */     
/* 44 */     BlurBuffer.blurArea(x + this.xOffset, y + this.yOffset, 22.0F, 22.0F, true);
/* 45 */     SimpleRender.drawRectFloat(x + this.xOffset, y + this.yOffset, x + this.xOffset + 22.0F, y + this.yOffset + 22.0F, (new Color(0, 0, 0, 120)).getRGB());
/* 46 */     GL11.glPushMatrix();
/* 47 */     GL11.glEnable(3089);
/* 48 */     RenderUtil.doGlScissor((int)(x + this.xOffset), (int)(y + this.yOffset), 22, 22);
/* 49 */     RenderUtil.circle(x + this.xOffset + 11.0F, y + this.yOffset + 11.0F, this.aniRadius, SimpleRender.reAlpha(Colors.WHITE.c, this.aniOp));
/* 50 */     GL11.glDisable(3089);
/* 51 */     GL11.glPopMatrix();
/* 52 */     if (name.contains("W")) {
/* 53 */       Client.instance.FontLoaders.regular17.drawString(name, x + this.xOffset + 7.0F, y + this.yOffset + 8.0F, SimpleRender.reAlpha(this.textColor2.getRGB(), 0.9F));
/*    */     } else {
/* 55 */       Client.instance.FontLoaders.regular17.drawString(name, x + this.xOffset + 8.0F, y + this.yOffset + 8.0F, SimpleRender.reAlpha(this.textColor2.getRGB(), 0.9F));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\hud\minekey\SimpleWhiteKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */