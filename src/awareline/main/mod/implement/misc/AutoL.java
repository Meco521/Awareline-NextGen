/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.combat.advanced.sucks.utils.AbuseUtil;
/*    */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ 
/*    */ public class AutoL extends Module {
/* 16 */   private final String[] modes = new String[] { "English", "Chinese" };
/* 17 */   public final Mode<String> mode = new Mode("Language", this.modes, this.modes[0]);
/* 18 */   public final Option<Boolean> autoKouZi = new Option("AutoFuck", Boolean.valueOf(false));
/* 19 */   private final Numbers<Double> delay = new Numbers("FuckDelay", 
/* 20 */       Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(10.0D), Double.valueOf(0.1D), this.autoKouZi::get);
/* 21 */   private final TimeHelper timer = new TimeHelper();
/*    */   public static AutoL getInstance;
/*    */   
/*    */   public AutoL() {
/* 25 */     super("AutoL", ModuleType.Misc);
/* 26 */     addSettings(new Value[] { (Value)this.mode, (Value)this.autoKouZi, (Value)this.delay });
/* 27 */     getInstance = this;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 32 */     if (((Boolean)this.autoKouZi.get()).booleanValue()) {
/* 33 */       long delayNew = (long)(((Double)this.delay.getValue()).doubleValue() * 1000.0D);
/* 34 */       if (this.timer.isDelayComplete(delayNew)) {
/* 35 */         mc.thePlayer.sendChatMessage(AbuseUtil.getKouZiList());
/* 36 */         this.timer.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getAutoLMessage(String PlayerName) {
/* 42 */     return PlayerName + (ThreadLocalRandom.current().nextBoolean() ? " fw, " : " L, ") + (this.mode.is("English") ? AbuseUtil.getAbuseEnglish() : AbuseUtil.getAbuseChinese());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\AutoL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */