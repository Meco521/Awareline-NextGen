/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.world.GameSpeed;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.BlockUtils;
/*    */ import awareline.main.utility.MoveUtils;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class IceSpeed extends Module {
/* 16 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Speed", "Timer", "NCP" }, "Timer");
/* 17 */   private final Numbers<Double> Speed = new Numbers("Speed", Double.valueOf(0.5D), Double.valueOf(0.01D), Double.valueOf(5.0D), Double.valueOf(0.01D));
/*    */   
/*    */   public IceSpeed() {
/* 20 */     super("IceSpeed", ModuleType.Movement);
/* 21 */     addSettings(new Value[] { (Value)this.mode, (Value)this.Speed });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 26 */     setSuffix((Serializable)this.mode.get());
/* 27 */     if (mc.thePlayer == null || mc.theWorld == null) {
/*    */       return;
/*    */     }
/* 30 */     if (!(BlockUtils.getBlock(mc.thePlayer.getPosition().down()) instanceof net.minecraft.block.BlockIce) || !(BlockUtils.getBlock(mc.thePlayer.getPosition().down()) instanceof net.minecraft.block.BlockPackedIce)) {
/* 31 */       if (((Speed)getModule(Speed.class)).isEnabled() || ((Flight)getModule(Flight.class)).isEnabled() || ((GameSpeed)getModule(GameSpeed.class)).isEnabled()) {
/*    */         return;
/*    */       }
/* 34 */       if (((Step)getModule(Step.class)).isEnabled() && mc.thePlayer.isCollidedHorizontally) {
/*    */         return;
/*    */       }
/* 37 */       if (mc.timer.timerSpeed != 1.0F) {
/* 38 */         mc.timer.timerSpeed = 1.0F;
/*    */       }
/*    */     } 
/* 41 */     if (BlockUtils.getBlock(mc.thePlayer.getPosition().down()) instanceof net.minecraft.block.BlockIce) {
/* 42 */       if (this.mode.is("NCP") && isMoving()) {
/* 43 */         MoveUtils.INSTANCE.setSpeed(0.699999988079071D);
/* 44 */       } else if (this.mode.is("Speed") && isMoving()) {
/* 45 */         MoveUtils.INSTANCE.setSpeed(((Double)this.Speed.get()).doubleValue());
/* 46 */       } else if (this.mode.is("Timer") && isMoving()) {
/* 47 */         mc.timer.timerSpeed = ((Double)this.Speed.get()).floatValue();
/*    */       } 
/* 49 */     } else if (BlockUtils.getBlock(mc.thePlayer.getPosition().down()) instanceof net.minecraft.block.BlockPackedIce) {
/* 50 */       if (this.mode.is("NCP") && isMoving()) {
/* 51 */         MoveUtils.INSTANCE.setSpeed(0.699999988079071D);
/* 52 */       } else if (this.mode.is("Speed") && isMoving()) {
/* 53 */         MoveUtils.INSTANCE.setSpeed(((Double)this.Speed.get()).doubleValue());
/* 54 */       } else if (this.mode.is("Timer") && isMoving()) {
/* 55 */         mc.timer.timerSpeed = ((Double)this.Speed.get()).floatValue();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 62 */     MoveUtils.INSTANCE.setSpeed(0.0D);
/* 63 */     mc.timer.timerSpeed = 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\IceSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */