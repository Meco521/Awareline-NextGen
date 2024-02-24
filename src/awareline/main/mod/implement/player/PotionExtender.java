/*    */ package awareline.main.mod.implement.player;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.move.Flight;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ 
/*    */ public class PotionExtender
/*    */   extends Module {
/*    */   private boolean canStop;
/*    */   private int goodPotions;
/*    */   private int badPotions;
/*    */   
/*    */   public PotionExtender() {
/* 17 */     super("PotionExtender", ModuleType.Player);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 22 */     Helper.sendMessage("PotionExtender: Only not movement and not change body yaw pitch can save potion effect.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 27 */     super.onDisable();
/* 28 */     this.canStop = false;
/* 29 */     this.goodPotions = 0;
/* 30 */     this.badPotions = 0;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacket(EventPacketReceive e) {
/* 35 */     if (Flight.getInstance.isEnabled()) {
/*    */       return;
/*    */     }
/* 38 */     if (e.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer && !(e.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition) && !(e.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook) && !(e.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook) && mc.thePlayer != null && !mc.thePlayer.isUsingItem())
/* 39 */       e.setCancelled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\PotionExtender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */