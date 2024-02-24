/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class S22PacketMultiBlockChange
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   ChunkCoordIntPair chunkPosCoord;
/*     */   private BlockUpdateData[] changedBlocks;
/*     */   
/*     */   public S22PacketMultiBlockChange() {}
/*     */   
/*     */   public S22PacketMultiBlockChange(int p_i45181_1_, short[] crammedPositionsIn, Chunk chunkIn) {
/*  23 */     this.chunkPosCoord = new ChunkCoordIntPair(chunkIn.xPosition, chunkIn.zPosition);
/*  24 */     this.changedBlocks = new BlockUpdateData[p_i45181_1_];
/*     */     
/*  26 */     for (int i = 0; i < this.changedBlocks.length; i++)
/*     */     {
/*  28 */       this.changedBlocks[i] = new BlockUpdateData(crammedPositionsIn[i], chunkIn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  36 */     this.chunkPosCoord = new ChunkCoordIntPair(buf.readInt(), buf.readInt());
/*  37 */     this.changedBlocks = new BlockUpdateData[buf.readVarIntFromBuffer()];
/*     */     
/*  39 */     for (int i = 0; i < this.changedBlocks.length; i++)
/*     */     {
/*  41 */       this.changedBlocks[i] = new BlockUpdateData(buf.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(buf.readVarIntFromBuffer()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  49 */     buf.writeInt(this.chunkPosCoord.chunkXPos);
/*  50 */     buf.writeInt(this.chunkPosCoord.chunkZPos);
/*  51 */     buf.writeVarIntToBuffer(this.changedBlocks.length);
/*     */     
/*  53 */     for (BlockUpdateData s22packetmultiblockchange$blockupdatedata : this.changedBlocks) {
/*     */       
/*  55 */       buf.writeShort(s22packetmultiblockchange$blockupdatedata.func_180089_b());
/*  56 */       buf.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(s22packetmultiblockchange$blockupdatedata.getBlockState()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  65 */     handler.handleMultiBlockChange(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockUpdateData[] getChangedBlocks() {
/*  70 */     return this.changedBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public class BlockUpdateData
/*     */   {
/*     */     private final short chunkPosCrammed;
/*     */     private final IBlockState blockState;
/*     */     
/*     */     public BlockUpdateData(short p_i45984_2_, IBlockState state) {
/*  80 */       this.chunkPosCrammed = p_i45984_2_;
/*  81 */       this.blockState = state;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockUpdateData(short p_i45985_2_, Chunk chunkIn) {
/*  86 */       this.chunkPosCrammed = p_i45985_2_;
/*  87 */       this.blockState = chunkIn.getBlockState(getPos());
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getPos() {
/*  92 */       return new BlockPos((Vec3i)S22PacketMultiBlockChange.this.chunkPosCoord.getBlock(this.chunkPosCrammed >> 12 & 0xF, this.chunkPosCrammed & 0xFF, this.chunkPosCrammed >> 8 & 0xF));
/*     */     }
/*     */ 
/*     */     
/*     */     public short func_180089_b() {
/*  97 */       return this.chunkPosCrammed;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getBlockState() {
/* 102 */       return this.blockState;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S22PacketMultiBlockChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */