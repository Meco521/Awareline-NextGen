/*    */ package awareline.main.mod.implement.move;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class Parkour extends Module {
/*    */   public Parkour() {
/* 10 */     super("Parkour", ModuleType.Movement);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 15 */     if (mc.thePlayer.moving() && mc.thePlayer.onGround && !mc.thePlayer.isSneaking() && 
/* 16 */       !mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown() && mc.theWorld
/* 17 */       .getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(-0.001D, 0.0D, -0.001D)).isEmpty())
/* 18 */       mc.thePlayer.jump(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\Parkour.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */