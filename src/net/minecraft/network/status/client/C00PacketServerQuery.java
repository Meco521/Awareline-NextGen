/*    */ package net.minecraft.network.status.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.status.INetHandlerStatusServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C00PacketServerQuery
/*    */   implements Packet<INetHandlerStatusServer>
/*    */ {
/*    */   public void readPacketData(PacketBuffer buf) {}
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {}
/*    */   
/*    */   public void processPacket(INetHandlerStatusServer handler) {
/* 26 */     handler.processServerQuery(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\status\client\C00PacketServerQuery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */