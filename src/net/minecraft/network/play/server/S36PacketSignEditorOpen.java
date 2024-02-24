/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ public class S36PacketSignEditorOpen
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos signPosition;
/*    */   
/*    */   public S36PacketSignEditorOpen() {}
/*    */   
/*    */   public S36PacketSignEditorOpen(BlockPos signPositionIn) {
/* 18 */     this.signPosition = signPositionIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 26 */     handler.handleSignEditorOpen(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 33 */     this.signPosition = buf.readBlockPos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 40 */     buf.writeBlockPos(this.signPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSignPosition() {
/* 45 */     return this.signPosition;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S36PacketSignEditorOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */