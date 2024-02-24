/*    */ package awareline.main.mod.implement.player.auto;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityFishHook;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*    */ 
/*    */ public class AutoFish
/*    */   extends Module {
/*    */   public AutoFish() {
/* 16 */     super("AutoFish", ModuleType.Player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPacketReceive e) {
/* 21 */     if (e.getPacket() instanceof S12PacketEntityVelocity) {
/* 22 */       S12PacketEntityVelocity packet = (S12PacketEntityVelocity)e.getPacket();
/* 23 */       Entity ent = mc.theWorld.getEntityByID(packet.getEntityID());
/* 24 */       if (ent instanceof EntityFishHook) {
/* 25 */         EntityFishHook fishHook = (EntityFishHook)ent;
/* 26 */         if (fishHook.angler.getEntityId() == mc.thePlayer.getEntityId()) {
/* 27 */           if (mc.thePlayer.inventory.currentItem != grabRodSlot()) {
/*    */             return;
/*    */           }
/* 30 */           if (packet.getMotionX() == 0 && packet.getMotionY() != 0 && packet.getMotionZ() == 0) {
/* 31 */             msg("Auto fishing.....");
/* 32 */             ClientNotification.sendClientMessage(getHUDName(), "Auto fishing..... ", 3500L, ClientNotification.Type.INFO);
/*    */             
/* 34 */             mc.rightClickMouse();
/* 35 */             mc.rightClickMouse();
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private int grabRodSlot() {
/* 43 */     for (int i2 = 0; i2 < 9; i2++) {
/* 44 */       ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i2];
/* 45 */       if (itemStack != null && itemStack.getItem() instanceof net.minecraft.item.ItemFishingRod) {
/* 46 */         return i2;
/*    */       }
/*    */     } 
/* 49 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\auto\AutoFish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */