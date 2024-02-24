/*    */ package awareline.main.mod.implement.combat;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*    */ 
/*    */ public class ArmorBreaker
/*    */   extends Module {
/*    */   public ArmorBreaker() {
/* 14 */     super("ArmorBreaker", ModuleType.Combat);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacket(EventPacketSend e) {
/* 19 */     if (e.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)e.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
/* 20 */       breaker();
/*    */     }
/*    */   }
/*    */   
/*    */   public void breaker() {
/* 25 */     if (!mc.thePlayer.onGround) {
/*    */       return;
/*    */     }
/* 28 */     ItemStack current = mc.thePlayer.getHeldItem();
/* 29 */     for (int i = 0; i < 45; i++) {
/* 30 */       ItemStack toSwitch = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/* 31 */       if (current != null && toSwitch != null && toSwitch.getItem() instanceof net.minecraft.item.ItemSword)
/* 32 */         mc.playerController.windowClick(0, i, mc.thePlayer.inventory.currentItem, 2, (EntityPlayer)mc.thePlayer); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\ArmorBreaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */