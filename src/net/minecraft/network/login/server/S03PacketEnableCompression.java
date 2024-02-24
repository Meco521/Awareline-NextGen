/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ 
/*    */ 
/*    */ public class S03PacketEnableCompression
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private int compressionTreshold;
/*    */   
/*    */   public S03PacketEnableCompression() {}
/*    */   
/*    */   public S03PacketEnableCompression(int compressionTresholdIn) {
/* 17 */     this.compressionTreshold = compressionTresholdIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 24 */     this.compressionTreshold = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 31 */     buf.writeVarIntToBuffer(this.compressionTreshold);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 39 */     handler.handleEnableCompression(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCompressionTreshold() {
/* 44 */     return this.compressionTreshold;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\login\server\S03PacketEnableCompression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */