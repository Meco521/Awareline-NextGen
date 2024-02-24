/*    */ package awareline.main.mod.implement.visual;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.combat.KillAura;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.timer.TimerUtil;
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class Animations extends Module {
/* 15 */   private final String[] renderMode = new String[] { "1.7", "Slide", "Smooth", "Smooth2", "Stella", "Exhibition", "Jigsaw", "Avatar", "Remix", "Sigma", "Astro", "Jello", "Jello2", "Rainy", "Target", "LiquidBounce", "Push", "Lucky", "Swank", "Swang", "Thinking", "Stitch", "Circle" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public final Mode<String> mode = new Mode("Mode", this.renderMode, this.renderMode[0]);
/* 22 */   public final Numbers<Double> speed = new Numbers("SwingSpeed", Double.valueOf(1.0D), Double.valueOf(0.01D), Double.valueOf(1.5D), Double.valueOf(0.01D));
/* 23 */   public final Numbers<Double> slowSpeedInt = new Numbers("Slow", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/* 24 */   public final Numbers<Double> x = new Numbers("X", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
/* 25 */   public final Numbers<Double> y = new Numbers("Y", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
/* 26 */   public final Numbers<Double> z = new Numbers("Z", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
/* 27 */   public final Numbers<Double> swingx = new Numbers("SwingX", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
/* 28 */   public final Numbers<Double> swingy = new Numbers("SwingY", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
/* 29 */   public final Numbers<Double> swingz = new Numbers("SwingZ", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
/* 30 */   public final Option<Boolean> Smooth = new Option("SmoothSwing", Boolean.valueOf(false));
/* 31 */   public final Option<Boolean> highHand = new Option("HighHand", Boolean.valueOf(false));
/* 32 */   public final Option<Boolean> LeftHand = new Option("LeftHand", Boolean.valueOf(false));
/* 33 */   public final Option<Boolean> betterThirdPersonBlock = new Option("BetterBlock", Boolean.valueOf(true));
/* 34 */   private final Option<Boolean> resetAnimation = new Option("ResetAnimation", Boolean.valueOf(false));
/* 35 */   private final Numbers<Double> resetDelay = new Numbers("ResetDelay", 
/* 36 */       Double.valueOf(300.0D), Double.valueOf(0.0D), Double.valueOf(2000.0D), Double.valueOf(5.0D), this.resetAnimation::get);
/* 37 */   private final TimerUtil animationsTimer = new TimerUtil();
/*    */   public static Animations getInstance;
/*    */   
/*    */   public Animations() {
/* 41 */     super("Animations", ModuleType.Render);
/* 42 */     addSettings(new Value[] { (Value)this.mode, (Value)this.speed, (Value)this.slowSpeedInt, (Value)this.x, (Value)this.y, (Value)this.z, (Value)this.swingx, (Value)this.swingy, (Value)this.swingz, (Value)this.Smooth, (Value)this.highHand, (Value)this.LeftHand, (Value)this.betterThirdPersonBlock, (Value)this.resetAnimation, (Value)this.resetDelay });
/*    */ 
/*    */     
/* 45 */     getInstance = this;
/* 46 */     setEnabledByConvention(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 51 */     this.animationsTimer.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 56 */     this.animationsTimer.reset();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onUpdate(EventPreUpdate e) {
/* 61 */     setSuffix((Serializable)this.mode.get());
/* 62 */     if (((Boolean)this.resetAnimation.get()).booleanValue() && (
/* 63 */       mc.thePlayer.isBlocking() || GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem) || KillAura.getInstance.isBlocking) && this.animationsTimer.hasReached(((Double)this.resetDelay.get()).intValue())) {
/* 64 */       mc.getItemRenderer().resetEquippedProgress2();
/* 65 */       this.animationsTimer.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Animations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */