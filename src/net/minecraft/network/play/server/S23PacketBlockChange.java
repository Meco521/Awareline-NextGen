/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class S23PacketBlockChange
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos blockPosition;
/*    */   private IBlockState blockState;
/*    */   
/*    */   public S23PacketBlockChange() {}
/*    */   
/*    */   public S23PacketBlockChange(World worldIn, BlockPos blockPositionIn) {
/* 22 */     this.blockPosition = blockPositionIn;
/* 23 */     this.blockState = worldIn.getBlockState(blockPositionIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 30 */     this.blockPosition = buf.readBlockPos();
/* 31 */     this.blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(buf.readVarIntFromBuffer());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 38 */     buf.writeBlockPos(this.blockPosition);
/* 39 */     buf.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.blockState));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handleBlockChange(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState() {
/* 52 */     return this.blockState;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getBlockPosition() {
/* 57 */     return this.blockPosition;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S23PacketBlockChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */