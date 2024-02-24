/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
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
/*    */ public class C0APacketAnimation
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public void readPacketData(PacketBuffer buf) {}
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {}
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 26 */     handler.handleAnimation(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C0APacketAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */