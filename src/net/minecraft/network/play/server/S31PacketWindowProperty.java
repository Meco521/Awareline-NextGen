/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S31PacketWindowProperty
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int windowId;
/*    */   private int varIndex;
/*    */   private int varValue;
/*    */   
/*    */   public S31PacketWindowProperty() {}
/*    */   
/*    */   public S31PacketWindowProperty(int windowIdIn, int varIndexIn, int varValueIn) {
/* 19 */     this.windowId = windowIdIn;
/* 20 */     this.varIndex = varIndexIn;
/* 21 */     this.varValue = varValueIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 29 */     handler.handleWindowProperty(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 36 */     this.windowId = buf.readUnsignedByte();
/* 37 */     this.varIndex = buf.readShort();
/* 38 */     this.varValue = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 45 */     buf.writeByte(this.windowId);
/* 46 */     buf.writeShort(this.varIndex);
/* 47 */     buf.writeShort(this.varValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 52 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVarIndex() {
/* 57 */     return this.varIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVarValue() {
/* 62 */     return this.varValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S31PacketWindowProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */