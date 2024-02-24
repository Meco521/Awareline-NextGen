/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import awareline.main.utility.timer.TimerUtil;
/*    */ 
/*    */ public class PacketMotior
/*    */   extends Module {
/*    */   private int packetcount;
/* 14 */   private final TimerUtil time = new TimerUtil();
/*    */   
/*    */   public PacketMotior() {
/* 17 */     super("PacketMotior", ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPacket(EventPacketSend e) {
/* 22 */     if (e.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/* 23 */       this.packetcount++;
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPostUpdate event) {
/* 29 */     if (this.time.hasReached(1000.0D)) {
/* 30 */       setSuffix("PPS:" + this.packetcount);
/* 31 */       if (this.packetcount > 22) {
/* 32 */         Helper.sendMessage("C03PacketPlayer is not sending packets normally  (" + this.packetcount + "/22)");
/*    */       }
/* 34 */       this.packetcount = 0;
/* 35 */       this.time.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\PacketMotior.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */