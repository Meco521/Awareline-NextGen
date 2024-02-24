/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S00PacketKeepAlive
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public S00PacketKeepAlive() {}
/*    */   
/*    */   public S00PacketKeepAlive(int idIn) {
/* 17 */     this.id = idIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 25 */     handler.handleKeepAlive(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 32 */     this.id = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 39 */     buf.writeVarIntToBuffer(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149134_c() {
/* 44 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S00PacketKeepAlive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */