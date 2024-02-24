/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.item.EntityPainting;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ public class S10PacketSpawnPainting
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityID;
/*    */   private BlockPos position;
/*    */   private EnumFacing facing;
/*    */   private String title;
/*    */   
/*    */   public S10PacketSpawnPainting() {}
/*    */   
/*    */   public S10PacketSpawnPainting(EntityPainting painting) {
/* 23 */     this.entityID = painting.getEntityId();
/* 24 */     this.position = painting.getHangingPosition();
/* 25 */     this.facing = painting.facingDirection;
/* 26 */     this.title = painting.art.title;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 33 */     this.entityID = buf.readVarIntFromBuffer();
/* 34 */     this.title = buf.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
/* 35 */     this.position = buf.readBlockPos();
/* 36 */     this.facing = EnumFacing.getHorizontal(buf.readUnsignedByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 43 */     buf.writeVarIntToBuffer(this.entityID);
/* 44 */     buf.writeString(this.title);
/* 45 */     buf.writeBlockPos(this.position);
/* 46 */     buf.writeByte(this.facing.getHorizontalIndex());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 54 */     handler.handleSpawnPainting(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 59 */     return this.entityID;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 64 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumFacing getFacing() {
/* 69 */     return this.facing;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 74 */     return this.title;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S10PacketSpawnPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */