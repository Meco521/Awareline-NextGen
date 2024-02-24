/*    */ package awareline.main.mod.implement.visual;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class Atmosphere extends Module {
/* 12 */   private final Option<Boolean> weather_control = new Option("WeatherControl", Boolean.valueOf(true));
/* 13 */   public final Mode<String> weather_mode = new Mode("WeatherState", new String[] { "Clean", "Rain", "Thunder", "Snowfall", "Snowstorm" }, "Clean");
/*    */ 
/*    */   
/* 16 */   public final Numbers<Double> time = new Numbers("Hour", 
/* 17 */       Double.valueOf(12.0D), Double.valueOf(1.0D), Double.valueOf(24.0D), Double.valueOf(1.0D));
/*    */   public static Atmosphere getInstance;
/*    */   
/*    */   public Atmosphere() {
/* 21 */     super("Atmosphere", ModuleType.Render);
/* 22 */     addSettings(new Value[] { (Value)this.weather_mode, (Value)this.weather_control, (Value)this.time });
/* 23 */     getInstance = this;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 27 */     return ((Double)this.time.get()).longValue() * 1000L;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventUpdate event) {
/* 32 */     if (((Boolean)this.weather_control.get()).booleanValue()) {
/* 33 */       switch ((String)this.weather_mode.get()) {
/*    */         case "Snowfall":
/*    */         case "Rain":
/* 36 */           mc.theWorld.setRainStrength(1.0F);
/* 37 */           mc.theWorld.setThunderStrength(0.0F);
/*    */           return;
/*    */         case "Snowstorm":
/*    */         case "Thunder":
/* 41 */           mc.theWorld.setRainStrength(1.0F);
/* 42 */           mc.theWorld.setThunderStrength(1.0F);
/*    */           return;
/*    */       } 
/* 45 */       mc.theWorld.setRainStrength(0.0F);
/* 46 */       mc.theWorld.setThunderStrength(0.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Atmosphere.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */