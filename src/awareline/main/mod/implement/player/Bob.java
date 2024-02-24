/*    */ package awareline.main.mod.implement.player;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class Bob extends Module {
/* 11 */   public static final Option<Boolean> realBobbing = new Option("RealBobbing", Boolean.valueOf(false));
/* 12 */   private final Numbers<Double> boob = new Numbers("Amount", 
/* 13 */       Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), Double.valueOf(0.1D), () -> Boolean.valueOf(!((Boolean)realBobbing.get()).booleanValue()));
/* 14 */   public final Option<Boolean> onlyground = new Option("OnlyGround", 
/* 15 */       Boolean.valueOf(false), () -> Boolean.valueOf(!((Boolean)realBobbing.get()).booleanValue()));
/*    */   public static Bob getInstance;
/*    */   
/*    */   public Bob() {
/* 19 */     super("Bob", ModuleType.Player);
/* 20 */     addSettings(new Value[] { (Value)realBobbing, (Value)this.boob, (Value)this.onlyground });
/* 21 */     getInstance = this;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate event) {
/* 26 */     if (!((Boolean)realBobbing.get()).booleanValue()) {
/* 27 */       if (((Boolean)this.onlyground.get()).booleanValue() && 
/* 28 */         !mc.thePlayer.onGround) {
/*    */         return;
/*    */       }
/*    */       
/* 32 */       mc.thePlayer.cameraYaw = (float)(0.09090908616781235D * ((Double)this.boob.getValue()).doubleValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\Bob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */