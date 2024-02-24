/*    */ package awareline.main.mod.implement.visual.ctype;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.globals.Shader;
/*    */ import awareline.main.mod.implement.visual.sucks.CompassUtil.AwarelineCompass;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class Compass extends Module {
/* 13 */   public final Option<Boolean> blur = new Option("Blur", Boolean.valueOf(true)); public final Option<Boolean> shadow = new Option("Shadow", Boolean.valueOf(true));
/*    */   
/* 15 */   private final AwarelineCompass awarelineCompass = new AwarelineCompass();
/*    */   
/*    */   public Compass() {
/* 18 */     super("Compass", ModuleType.Render);
/* 19 */     addSettings(new Value[] { (Value)this.blur, (Value)this.shadow });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {}
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void renderHud(EventRender2D event) {
/* 31 */     if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null) {
/*    */       return;
/*    */     }
/* 34 */     this.awarelineCompass.draw(event.getResolution(), false);
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onBloom(EventShader event) {
/* 40 */     if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null) {
/*    */       return;
/*    */     }
/* 43 */     if (!Shader.getInstance.isEnabled()) {
/*    */       return;
/*    */     }
/*    */     
/* 47 */     if (event.onBloom() && !((Boolean)this.shadow.getValue()).booleanValue()) {
/*    */       return;
/*    */     }
/*    */     
/* 51 */     if (event.onBlur() && !((Boolean)this.blur.getValue()).booleanValue()) {
/*    */       return;
/*    */     }
/* 54 */     this.awarelineCompass.drawBloom(event.getResolution(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\Compass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */