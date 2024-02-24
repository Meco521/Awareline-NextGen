/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C10PacketCreativeInventoryAction
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int slotId;
/*    */   private ItemStack stack;
/*    */   
/*    */   public C10PacketCreativeInventoryAction() {}
/*    */   
/*    */   public C10PacketCreativeInventoryAction(int slotIdIn, ItemStack stackIn) {
/* 21 */     this.slotId = slotIdIn;
/* 22 */     this.stack = (stackIn != null) ? stackIn.copy() : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 30 */     handler.processCreativeInventoryAction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.slotId = buf.readShort();
/* 39 */     this.stack = buf.readItemStackFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 46 */     buf.writeShort(this.slotId);
/* 47 */     buf.writeItemStackToBuffer(this.stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSlotId() {
/* 52 */     return this.slotId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getStack() {
/* 57 */     return this.stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C10PacketCreativeInventoryAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */