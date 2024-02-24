/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ public class S24PacketBlockAction
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos blockPosition;
/*    */   private int instrument;
/*    */   private int pitch;
/*    */   private Block block;
/*    */   
/*    */   public S24PacketBlockAction() {}
/*    */   
/*    */   public S24PacketBlockAction(BlockPos blockPositionIn, Block blockIn, int instrumentIn, int pitchIn) {
/* 22 */     this.blockPosition = blockPositionIn;
/* 23 */     this.instrument = instrumentIn;
/* 24 */     this.pitch = pitchIn;
/* 25 */     this.block = blockIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 32 */     this.blockPosition = buf.readBlockPos();
/* 33 */     this.instrument = buf.readUnsignedByte();
/* 34 */     this.pitch = buf.readUnsignedByte();
/* 35 */     this.block = Block.getBlockById(buf.readVarIntFromBuffer() & 0xFFF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 42 */     buf.writeBlockPos(this.blockPosition);
/* 43 */     buf.writeByte(this.instrument);
/* 44 */     buf.writeByte(this.pitch);
/* 45 */     buf.writeVarIntToBuffer(Block.getIdFromBlock(this.block) & 0xFFF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 53 */     handler.handleBlockAction(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getBlockPosition() {
/* 58 */     return this.blockPosition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getData1() {
/* 66 */     return this.instrument;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getData2() {
/* 74 */     return this.pitch;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block getBlockType() {
/* 79 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S24PacketBlockAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */