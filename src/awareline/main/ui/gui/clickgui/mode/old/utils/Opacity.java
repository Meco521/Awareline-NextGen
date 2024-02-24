/*    */ package awareline.main.ui.gui.clickgui.mode.old.utils;
/*    */ 
/*    */ import awareline.main.ui.animations.AnimationUtil;
/*    */ 
/*    */ public class Opacity
/*    */ {
/*    */   private float opacity;
/*    */   private final float opacity2;
/*    */   private long lastMS;
/*    */   
/*    */   public Opacity(int opacity) {
/* 12 */     this.opacity = opacity;
/* 13 */     this.opacity2 = 120.0F;
/* 14 */     this.lastMS = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void interpolate(float targetOpacity) {
/* 18 */     long currentMS = System.currentTimeMillis();
/* 19 */     long delta = currentMS - this.lastMS;
/* 20 */     this.lastMS = currentMS;
/* 21 */     this.opacity = AnimationUtil.calculateCompensation(targetOpacity, this.opacity, delta, 20);
/*    */   }
/*    */   
/*    */   public float getOpacity() {
/* 25 */     return (int)this.opacity;
/*    */   }
/*    */   
/*    */   public float shadowAnim() {
/* 29 */     return (int)this.opacity2;
/*    */   }
/*    */   
/*    */   public void setOpacity(float opacity) {
/* 33 */     this.opacity = opacity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\ol\\utils\Opacity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */