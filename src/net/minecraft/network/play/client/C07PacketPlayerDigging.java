/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C07PacketPlayerDigging
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private BlockPos position;
/*    */   private EnumFacing facing;
/*    */   private Action status;
/*    */   
/*    */   public C07PacketPlayerDigging() {}
/*    */   
/*    */   public C07PacketPlayerDigging(Action statusIn, BlockPos posIn, EnumFacing facingIn) {
/* 23 */     this.status = statusIn;
/* 24 */     this.position = posIn;
/* 25 */     this.facing = facingIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 32 */     this.status = (Action)buf.readEnumValue(Action.class);
/* 33 */     this.position = buf.readBlockPos();
/* 34 */     this.facing = EnumFacing.getFront(buf.readUnsignedByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 41 */     buf.writeEnumValue(this.status);
/* 42 */     buf.writeBlockPos(this.position);
/* 43 */     buf.writeByte(this.facing.getIndex());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 51 */     handler.processPlayerDigging(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 56 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumFacing getFacing() {
/* 61 */     return this.facing;
/*    */   }
/*    */ 
/*    */   
/*    */   public Action getStatus() {
/* 66 */     return this.status;
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 71 */     START_DESTROY_BLOCK,
/* 72 */     ABORT_DESTROY_BLOCK,
/* 73 */     STOP_DESTROY_BLOCK,
/* 74 */     DROP_ALL_ITEMS,
/* 75 */     DROP_ITEM,
/* 76 */     RELEASE_USE_ITEM;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C07PacketPlayerDigging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */