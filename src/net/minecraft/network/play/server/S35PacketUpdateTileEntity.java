/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S35PacketUpdateTileEntity
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   private int metadata;
/*    */   private NBTTagCompound nbt;
/*    */   
/*    */   public S35PacketUpdateTileEntity() {}
/*    */   
/*    */   public S35PacketUpdateTileEntity(BlockPos blockPosIn, int metadataIn, NBTTagCompound nbtIn) {
/* 25 */     this.blockPos = blockPosIn;
/* 26 */     this.metadata = metadataIn;
/* 27 */     this.nbt = nbtIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 35 */     this.blockPos = buf.readBlockPos();
/* 36 */     this.metadata = buf.readUnsignedByte();
/* 37 */     this.nbt = buf.readNBTTagCompoundFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 44 */     buf.writeBlockPos(this.blockPos);
/* 45 */     buf.writeByte((byte)this.metadata);
/* 46 */     buf.writeNBTTagCompoundToBuffer(this.nbt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 54 */     handler.handleUpdateTileEntity(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPos() {
/* 59 */     return this.blockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTileEntityType() {
/* 64 */     return this.metadata;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound getNbtCompound() {
/* 69 */     return this.nbt;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S35PacketUpdateTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */