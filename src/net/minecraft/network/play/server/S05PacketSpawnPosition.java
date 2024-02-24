/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ public class S05PacketSpawnPosition
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos spawnBlockPos;
/*    */   
/*    */   public S05PacketSpawnPosition() {}
/*    */   
/*    */   public S05PacketSpawnPosition(BlockPos spawnBlockPosIn) {
/* 18 */     this.spawnBlockPos = spawnBlockPosIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 25 */     this.spawnBlockPos = buf.readBlockPos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 32 */     buf.writeBlockPos(this.spawnBlockPos);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 40 */     handler.handleSpawnPosition(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSpawnPos() {
/* 45 */     return this.spawnBlockPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S05PacketSpawnPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */