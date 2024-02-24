/*    */ package awareline.main.mod.implement.move;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.moveEvents.EventMove;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.BlockUtils;
/*    */ import awareline.main.utility.MoveUtils;
/*    */ 
/*    */ public class Strafe extends Module {
/* 12 */   private final Option<Boolean> fullStrafe = new Option("FullStrafe", 
/* 13 */       Boolean.valueOf(false)),
/* 14 */      onlyGround = new Option("OnlyGround", Boolean.valueOf(false)),
/* 15 */      safeStrafe = new Option("SafeStrafe", Boolean.valueOf(false));
/*    */   
/*    */   public Strafe() {
/* 18 */     super("Strafe", ModuleType.Movement);
/* 19 */     addSettings(new Value[] { (Value)this.fullStrafe, (Value)this.onlyGround, (Value)this.safeStrafe });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onUpdate(EventMove e) {
/* 24 */     if (check()) {
/* 25 */       if (((Boolean)this.fullStrafe.get()).booleanValue()) {
/* 26 */         Speed.getInstance.setMotion(Math.max(MoveUtils.INSTANCE.getSpeed(), MoveUtils.INSTANCE.getBaseMoveSpeed()));
/*    */       } else {
/* 28 */         MoveUtils.INSTANCE.strafe();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean check() {
/* 34 */     if (!isMoving()) {
/* 35 */       return false;
/*    */     }
/* 37 */     if (Speed.getInstance.isEnabled() || Longjump.getInstance.isEnabled()) {
/* 38 */       return false;
/*    */     }
/* 40 */     if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/* 41 */       .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer
/* 42 */       .isSneaking() || mc.thePlayer.isCollidedHorizontally || 
/* 43 */       BlockUtils.isInsideBlock() || !mc.gameSettings.keyBindJump.pressed) {
/* 44 */       return false;
/*    */     }
/* 46 */     if (((Boolean)this.onlyGround.get()).booleanValue() && !mc.thePlayer.onGround) {
/* 47 */       return false;
/*    */     }
/* 49 */     if (((Boolean)this.safeStrafe.get()).booleanValue()) {
/* 50 */       return (MoveUtils.INSTANCE.getSpeed() < 0.215D);
/*    */     }
/* 52 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\Strafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */