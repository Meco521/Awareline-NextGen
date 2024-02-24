/*    */ package awareline.main.mod.implement.player.anti;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ public class AntiDebuff extends Module {
/* 11 */   private final Option<Boolean> blind = new Option("NoBlind", Boolean.valueOf(true));
/* 12 */   private final Option<Boolean> slowdown = new Option("NoSlowdown", Boolean.valueOf(false));
/*    */   
/*    */   public AntiDebuff() {
/* 15 */     super("AntiDebuff", ModuleType.Player);
/* 16 */     addSettings(new Value[] { (Value)this.blind, (Value)this.slowdown });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onUpdate(EventPreUpdate event) {
/* 21 */     if (((Boolean)this.blind.get()).booleanValue()) {
/* 22 */       mc.thePlayer.removePotionEffectClient(Potion.blindness.getId());
/*    */     }
/* 24 */     if (((Boolean)this.slowdown.get()).booleanValue()) {
/* 25 */       mc.thePlayer.removePotionEffectClient(Potion.moveSlowdown.getId());
/*    */     }
/* 27 */     mc.thePlayer.removePotionEffectClient(9);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\anti\AntiDebuff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */