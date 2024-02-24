/*    */ package awareline.main.component.impl;
/*    */ 
/*    */ import awareline.main.InstanceAccess;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*    */ 
/*    */ 
/*    */ public final class BadPacketsComponent
/*    */   implements InstanceAccess
/*    */ {
/*    */   private boolean slot;
/*    */   private boolean attack;
/*    */   private boolean swing;
/*    */   private boolean block;
/*    */   private boolean inventory;
/*    */   
/*    */   public boolean bad() {
/* 20 */     return bad(true, true, true, true, true);
/*    */   }
/*    */   
/*    */   public boolean bad(boolean slot, boolean attack, boolean swing, boolean block, boolean inventory) {
/* 24 */     return ((this.slot && slot) || (this.attack && attack) || (this.swing && swing) || (this.block && block) || (this.inventory && inventory));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(0)
/*    */   public void onPacketSend(PacketEvent event) {
/* 33 */     Packet<?> packet = event.getPacket();
/*    */     
/* 35 */     if (event.getState() == PacketEvent.State.OUTGOING)
/*    */     {
/* 37 */       if (packet instanceof net.minecraft.network.play.client.C09PacketHeldItemChange) {
/* 38 */         this.slot = true;
/* 39 */       } else if (packet instanceof net.minecraft.network.play.client.C0APacketAnimation) {
/* 40 */         this.swing = true;
/* 41 */       } else if (packet instanceof net.minecraft.network.play.client.C02PacketUseEntity) {
/* 42 */         this.attack = true;
/* 43 */       } else if (packet instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement || packet instanceof net.minecraft.network.play.client.C07PacketPlayerDigging) {
/* 44 */         this.block = true;
/* 45 */       } else if (packet instanceof net.minecraft.network.play.client.C0EPacketClickWindow || (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet)
/* 46 */         .getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) || packet instanceof net.minecraft.network.play.client.C0DPacketCloseWindow) {
/*    */         
/* 48 */         this.inventory = true;
/* 49 */       } else if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/* 50 */         this.slot = false;
/* 51 */         this.swing = false;
/* 52 */         this.attack = false;
/* 53 */         this.block = false;
/* 54 */         this.inventory = false;
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\component\impl\BadPacketsComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */