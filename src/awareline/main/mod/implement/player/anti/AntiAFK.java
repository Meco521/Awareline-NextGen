/*    */ package awareline.main.mod.implement.player.anti;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ public class AntiAFK extends Module {
/* 12 */   private final Numbers<Double> delay = new Numbers("Delay", Double.valueOf(500.0D), Double.valueOf(0.0D), Double.valueOf(5000.0D), Double.valueOf(100.0D));
/*    */   
/* 14 */   private final TimeHelper antiafkTimer = new TimeHelper();
/*    */   
/*    */   public AntiAFK() {
/* 17 */     super("AntiAFK", ModuleType.Player);
/* 18 */     addSettings(new Value[] { (Value)this.delay });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 23 */     this.antiafkTimer.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 28 */     if (!GameSettings.isKeyDown(mc.gameSettings.keyBindForward))
/* 29 */       KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false); 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void EventPreUpdate(EventPreUpdate event) {
/* 34 */     KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
/* 35 */     if (this.antiafkTimer.isDelayComplete(((Double)this.delay.getValue()).intValue())) {
/* 36 */       mc.thePlayer.rotationYaw += 180.0F;
/* 37 */       this.antiafkTimer.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\anti\AntiAFK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */