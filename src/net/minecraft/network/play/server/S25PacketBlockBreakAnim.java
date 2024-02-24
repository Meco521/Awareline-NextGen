/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ public class S25PacketBlockBreakAnim
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int breakerId;
/*    */   private BlockPos position;
/*    */   private int progress;
/*    */   
/*    */   public S25PacketBlockBreakAnim() {}
/*    */   
/*    */   public S25PacketBlockBreakAnim(int breakerId, BlockPos pos, int progress) {
/* 20 */     this.breakerId = breakerId;
/* 21 */     this.position = pos;
/* 22 */     this.progress = progress;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 29 */     this.breakerId = buf.readVarIntFromBuffer();
/* 30 */     this.position = buf.readBlockPos();
/* 31 */     this.progress = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 38 */     buf.writeVarIntToBuffer(this.breakerId);
/* 39 */     buf.writeBlockPos(this.position);
/* 40 */     buf.writeByte(this.progress);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 48 */     handler.handleBlockBreakAnim(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBreakerId() {
/* 53 */     return this.breakerId;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 58 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getProgress() {
/* 63 */     return this.progress;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S25PacketBlockBreakAnim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */