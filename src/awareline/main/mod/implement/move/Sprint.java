/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.world.Scaffold;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ public class Sprint extends Module {
/*    */   public static Sprint getInstance;
/* 14 */   private final Option<Boolean> Sneak = new Option("Sneak", Boolean.valueOf(true)), UsingItem = new Option("UsingItem", Boolean.valueOf(true)), MultiDirection = new Option("MultiDir", 
/* 15 */       Boolean.valueOf(true)), Blindness = new Option("Blindness", Boolean.valueOf(true)),
/* 16 */      Hunger = new Option("Hunger", Boolean.valueOf(true)), AtWall = new Option("AtWall", Boolean.valueOf(true));
/*    */   
/*    */   public Sprint() {
/* 19 */     super("Sprint", ModuleType.Movement);
/* 20 */     addSettings(new Value[] { (Value)this.MultiDirection, (Value)this.Sneak, (Value)this.UsingItem, (Value)this.Blindness, (Value)this.Hunger, (Value)this.AtWall });
/* 21 */     getInstance = this;
/* 22 */     setEnabled(true);
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onUpdate(EventPreUpdate event) {
/* 28 */     if (Scaffold.getInstance.isEnabled()) {
/*    */       return;
/*    */     }
/* 31 */     if (!isMoving() || (mc.thePlayer.moveForward < 0.8F && 
/* 32 */       !((Boolean)this.MultiDirection.getValue()).booleanValue()) || (mc.thePlayer
/* 33 */       .isSneaking() && !((Boolean)this.Sneak.getValue()).booleanValue()) || (mc.thePlayer
/* 34 */       .isUsingItem() && !((Boolean)this.UsingItem.getValue()).booleanValue()) || (mc.thePlayer.isCollidedHorizontally && 
/* 35 */       !((Boolean)this.AtWall.getValue()).booleanValue()) || (mc.thePlayer
/* 36 */       .isPotionActive(Potion.blindness) && !((Boolean)this.Blindness.getValue()).booleanValue()) || (mc.thePlayer
/* 37 */       .getFoodStats().getFoodLevel() <= 6.0F && !((Boolean)this.Hunger.getValue()).booleanValue())) {
/* 38 */       mc.thePlayer.setSprinting(false);
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     if (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F)
/* 43 */       mc.thePlayer.setSprinting(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\Sprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */