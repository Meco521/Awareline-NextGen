/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S0APacketUseBed
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int playerID;
/*    */   private BlockPos bedPos;
/*    */   
/*    */   public S0APacketUseBed() {}
/*    */   
/*    */   public S0APacketUseBed(EntityPlayer player, BlockPos bedPosIn) {
/* 23 */     this.playerID = player.getEntityId();
/* 24 */     this.bedPos = bedPosIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 31 */     this.playerID = buf.readVarIntFromBuffer();
/* 32 */     this.bedPos = buf.readBlockPos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 39 */     buf.writeVarIntToBuffer(this.playerID);
/* 40 */     buf.writeBlockPos(this.bedPos);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 48 */     handler.handleUseBed(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPlayer getPlayer(World worldIn) {
/* 53 */     return (EntityPlayer)worldIn.getEntityByID(this.playerID);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getBedPosition() {
/* 58 */     return this.bedPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S0APacketUseBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */