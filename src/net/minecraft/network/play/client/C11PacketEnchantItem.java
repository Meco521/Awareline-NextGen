/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C11PacketEnchantItem
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int windowId;
/*    */   private int button;
/*    */   
/*    */   public C11PacketEnchantItem() {}
/*    */   
/*    */   public C11PacketEnchantItem(int windowId, int button) {
/* 18 */     this.windowId = windowId;
/* 19 */     this.button = button;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 27 */     handler.processEnchantItem(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 34 */     this.windowId = buf.readByte();
/* 35 */     this.button = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 42 */     buf.writeByte(this.windowId);
/* 43 */     buf.writeByte(this.button);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 48 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getButton() {
/* 53 */     return this.button;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C11PacketEnchantItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */