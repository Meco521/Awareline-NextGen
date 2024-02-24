/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S46PacketSetCompressionLevel
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int threshold;
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 15 */     this.threshold = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 22 */     buf.writeVarIntToBuffer(this.threshold);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 30 */     handler.handleSetCompressionLevel(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getThreshold() {
/* 35 */     return this.threshold;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S46PacketSetCompressionLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */