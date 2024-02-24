/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C00PacketKeepAlive
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public int key;
/*    */   
/*    */   public C00PacketKeepAlive() {}
/*    */   
/*    */   public C00PacketKeepAlive(int key) {
/* 17 */     this.key = key;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 25 */     handler.processKeepAlive(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 32 */     this.key = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 39 */     buf.writeVarIntToBuffer(this.key);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getKey() {
/* 44 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C00PacketKeepAlive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */