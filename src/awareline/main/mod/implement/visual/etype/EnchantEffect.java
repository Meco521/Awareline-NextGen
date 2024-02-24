/*    */ package awareline.main.mod.implement.visual.etype;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.visual.HUD;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.awt.Color;
/*    */ 
/*    */ public class EnchantEffect extends Module {
/*    */   public float hue;
/* 15 */   public final Option<Boolean> HUDColor = new Option("HUDColor", Boolean.valueOf(true));
/* 16 */   public final Option<Boolean> Rainbow = new Option("Rainbow", 
/* 17 */       Boolean.valueOf(false), () -> Boolean.valueOf(!((Boolean)this.HUDColor.get()).booleanValue()));
/* 18 */   public final Option<Boolean> fade = new Option("Fade", Boolean.valueOf(false), () -> Boolean.valueOf(!((Boolean)this.Rainbow.get()).booleanValue()), () -> Boolean.valueOf(!((Boolean)this.HUDColor.get()).booleanValue()));
/*    */   
/* 20 */   public final Numbers<Double> RainbowSpeed = new Numbers("RainbowSpeed", Double.valueOf(6.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D), this.Rainbow::get, () -> Boolean.valueOf(!((Boolean)this.HUDColor.get()).booleanValue()));
/*    */   
/* 22 */   public final Numbers<Double> r = new Numbers("Red", Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D), () -> Boolean.valueOf(!((Boolean)this.Rainbow.get()).booleanValue()), () -> Boolean.valueOf(!((Boolean)this.HUDColor.get()).booleanValue()));
/*    */   
/* 24 */   public final Numbers<Double> g = new Numbers("Green", Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D), () -> Boolean.valueOf(!((Boolean)this.Rainbow.get()).booleanValue()), () -> Boolean.valueOf(!((Boolean)this.HUDColor.get()).booleanValue()));
/*    */   
/* 26 */   public final Numbers<Double> b = new Numbers("Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D), () -> Boolean.valueOf(!((Boolean)this.Rainbow.get()).booleanValue()), () -> Boolean.valueOf(!((Boolean)this.HUDColor.get()).booleanValue()));
/*    */   
/*    */   public static EnchantEffect getInstance;
/*    */   
/*    */   public EnchantEffect() {
/* 31 */     super("EnchantEffect", ModuleType.Render);
/* 32 */     addSettings(new Value[] { (Value)this.HUDColor, (Value)this.r, (Value)this.g, (Value)this.b, (Value)this.fade, (Value)this.Rainbow, (Value)this.RainbowSpeed });
/* 33 */     getInstance = this;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void Render2d(EventRender2D e) {
/* 38 */     if (((Boolean)this.HUDColor.get()).booleanValue()) {
/*    */       return;
/*    */     }
/* 41 */     this.hue += ((Double)this.RainbowSpeed.get()).floatValue() / 5.0F;
/* 42 */     if (this.hue > 255.0F) {
/* 43 */       this.hue = 0.0F;
/*    */     }
/*    */   }
/*    */   
/*    */   public Color getEnchantColor() {
/* 48 */     if (((Boolean)this.HUDColor.get()).booleanValue()) {
/* 49 */       if (((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 50 */         return HUD.getInstance.rainbowToEffect();
/*    */       }
/* 52 */       return ((Boolean)HUD.dynamicColor.get()).booleanValue() ? HUD.getInstance.fade(new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue()), 70, 25) : new Color(((Double)HUD.r
/*    */           
/* 54 */           .get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue());
/*    */     } 
/*    */     
/* 57 */     if (((Boolean)this.Rainbow.get()).booleanValue()) {
/* 58 */       return Color.getHSBColor(this.hue / 255.0F, 0.75F, 0.9F);
/*    */     }
/* 60 */     return ((Boolean)this.fade.get()).booleanValue() ? HUD.getInstance.fade(new Color(((Double)this.r.get()).intValue(), ((Double)this.g.get()).intValue(), ((Double)this.b.get()).intValue()), 70, 25) : new Color(((Double)this.r.get()).intValue(), ((Double)this.g.get()).intValue(), ((Double)this.b.get()).intValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\etype\EnchantEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */