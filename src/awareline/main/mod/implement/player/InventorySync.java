/*    */ package awareline.main.mod.implement.player;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*    */ 
/*    */ public class InventorySync
/*    */   extends Module {
/*    */   public short action;
/*    */   
/*    */   public InventorySync() {
/* 16 */     super("InventorySync", ModuleType.Player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPacket(PacketEvent event) {
/* 21 */     Packet<?> packet = event.getPacket();
/*    */     
/* 23 */     if (event.getState() == PacketEvent.State.INCOMING && 
/* 24 */       packet instanceof S32PacketConfirmTransaction) {
/* 25 */       S32PacketConfirmTransaction wrapper = (S32PacketConfirmTransaction)packet;
/* 26 */       Container inventory = mc.thePlayer.inventoryContainer;
/*    */       
/* 28 */       if (wrapper.getWindowId() == inventory.windowId) {
/* 29 */         this.action = wrapper.getActionNumber();
/*    */         
/* 31 */         if (this.action > 0 && this.action < inventory.transactionID)
/* 32 */           inventory.transactionID = (short)(this.action + 1); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\InventorySync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */