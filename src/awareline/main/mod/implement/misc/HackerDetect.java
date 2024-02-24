/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ public class HackerDetect
/*    */   extends Module {
/*    */   public HackerDetect() {
/* 14 */     super("HackerDetect", ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventTick e) {
/* 19 */     for (Entity entity : mc.theWorld.loadedEntityList) {
/* 20 */       if (!(entity instanceof EntityPlayer) || 
/* 21 */         entity == mc.thePlayer)
/* 22 */         continue;  EntityPlayer player = (EntityPlayer)entity;
/*    */ 
/*    */       
/* 25 */       if (player.getSpeed() >= player.getBaseMoveSpeed() * 0.95D && (player.moveForward < 0.0F || (player.moveForward == 0.0F && player.moveStrafing != 0.0F))) {
/* 26 */         player.detect.sprint++;
/* 27 */         msg(player.getName() + " Omni Sprint - VL:" + player.detect.sprint);
/*    */       } 
/*    */ 
/*    */       
/* 31 */       if (player.isUsingItem() && player.onGround && player.hurtTime == 0 && !player.isPotionActive(Potion.jump) && player.getSpeed() >= player.getBaseMoveSpeed() * 0.9D && !mc.thePlayer.getHeldItem().getDisplayName().toLowerCase().contains("bow")) {
/* 32 */         player.detect.noSlow++;
/* 33 */         msg(player.getName() + " No Slow - VL:" + player.detect.noSlow);
/*    */       } 
/*    */       
/* 36 */       if (player.detect.flight >= 3 && !player.detect.isFlying) {
/* 37 */         notiWarning("Hacker Detector", player.getName() + " - Fly!");
/* 38 */         player.detect.isFlying = true;
/*    */       } 
/*    */       
/* 41 */       if (player.detect.sprint >= 5 && !player.detect.allDirectionSprint) {
/* 42 */         notiWarning("Hacker Detector", player.getName() + " - Omni Sprint!");
/* 43 */         player.detect.allDirectionSprint = true;
/*    */       } 
/*    */       
/* 46 */       if (player.detect.noSlow >= 5 && !player.detect.isNoSlow) {
/* 47 */         notiWarning("Hacker Detector", player.getName() + " - No Slow!");
/* 48 */         player.detect.isNoSlow = true;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHacker(EntityPlayer player) {
/* 55 */     return (player.detect.isFlying || player.detect.allDirectionSprint || player.detect.isNoSlow);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\HackerDetect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */