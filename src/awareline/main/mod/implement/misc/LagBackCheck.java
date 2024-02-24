/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.combat.KillAura;
/*    */ import awareline.main.mod.implement.combat.TPAura;
/*    */ import awareline.main.mod.implement.move.Flight;
/*    */ import awareline.main.mod.implement.move.Longjump;
/*    */ import awareline.main.mod.implement.move.Speed;
/*    */ import awareline.main.mod.implement.move.Step;
/*    */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*    */ import awareline.main.mod.implement.world.GameSpeed;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class LagBackCheck
/*    */   extends Module {
/* 21 */   private final Numbers<Double> disdelay = new Numbers("DisableDelay", Double.valueOf(200.0D), Double.valueOf(0.0D), Double.valueOf(750.0D), Double.valueOf(50.0D));
/* 22 */   private final Option<Boolean> fly = new Option("FlyCheck", Boolean.valueOf(true));
/* 23 */   private final Option<Boolean> speed = new Option("SpeedCheck", Boolean.valueOf(true));
/* 24 */   private final Option<Boolean> lj = new Option("LongJumpCheck", Boolean.valueOf(true));
/* 25 */   private final Option<Boolean> step = new Option("StepCheck", Boolean.valueOf(false));
/* 26 */   private final Option<Boolean> timer = new Option("TimerCheck", Boolean.valueOf(false));
/* 27 */   private final Option<Boolean> auraCheck = new Option("AuraCheck", Boolean.valueOf(false));
/* 28 */   private final TimeHelper time = new TimeHelper();
/*    */   
/*    */   public LagBackCheck() {
/* 31 */     super("LagBackCheck", ModuleType.Misc);
/* 32 */     addSettings(new Value[] { (Value)this.disdelay, (Value)this.fly, (Value)this.speed, (Value)this.lj, (Value)this.step, (Value)this.timer, (Value)this.auraCheck });
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onPacket(EventPacketReceive e) {
/* 38 */     if (e.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
/* 39 */       if (((Boolean)this.fly.get()).booleanValue() && Flight.getInstance.isEnabled() && 
/* 40 */         this.time.delay(((Double)this.disdelay.get()).intValue())) {
/* 41 */         checkModule(Flight.class);
/* 42 */         this.time.reset();
/*    */       } 
/*    */       
/* 45 */       if (((Boolean)this.speed.get()).booleanValue() && Speed.getInstance.isEnabled() && mc.thePlayer
/* 46 */         .moving() && 
/* 47 */         this.time.delay(((Double)this.disdelay.get()).intValue())) {
/* 48 */         checkModule(Speed.class);
/* 49 */         this.time.reset();
/*    */       } 
/*    */       
/* 52 */       if (((Boolean)this.lj.get()).booleanValue() && Longjump.getInstance.isEnabled() && mc.thePlayer
/* 53 */         .moving() && 
/* 54 */         this.time.delay(((Double)this.disdelay.get()).intValue())) {
/* 55 */         checkModule(Longjump.class);
/* 56 */         this.time.reset();
/*    */       } 
/*    */       
/* 59 */       if (((Boolean)this.timer.get()).booleanValue() && GameSpeed.getInstance
/* 60 */         .isEnabled() && 
/* 61 */         this.time.delay(((Double)this.disdelay.get()).intValue())) {
/* 62 */         checkModule(GameSpeed.class);
/* 63 */         mc.timer.timerSpeed = 1.0F;
/* 64 */         this.time.reset();
/*    */       } 
/*    */       
/* 67 */       if (((Boolean)this.auraCheck.get()).booleanValue()) {
/* 68 */         if (KillAura.getInstance.isEnabled() && 
/* 69 */           KillAura.getInstance.target != null && mc.thePlayer.moving() && e
/* 70 */           .getPacket() instanceof net.minecraft.network.play.client.C02PacketUseEntity && 
/* 71 */           this.time.delay(((Double)this.disdelay.get()).intValue())) {
/* 72 */           checkModule(KillAura.class);
/* 73 */           this.time.reset();
/*    */         } 
/*    */ 
/*    */         
/* 77 */         if (TPAura.getInstance.isEnabled() && 
/* 78 */           !TPAura.getInstance.targets.isEmpty() && 
/* 79 */           this.time.delay(((Double)this.disdelay.get()).intValue())) {
/* 80 */           checkModule(TPAura.class);
/* 81 */           this.time.reset();
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 86 */       if (((Boolean)this.step.get()).booleanValue() && Step.getInstance
/* 87 */         .isEnabled() && mc.thePlayer.isCollidedHorizontally && 
/* 88 */         this.time.delay(((Double)this.disdelay.get()).intValue())) {
/* 89 */         checkModule(Step.class);
/* 90 */         this.time.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\LagBackCheck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */