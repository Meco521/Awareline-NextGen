/*    */ package net.minecraft.network.play.server;
/*    */ 
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
/*    */ 
/*    */ public class S28PacketEffect
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int soundType;
/*    */   private BlockPos soundPos;
/*    */   private int soundData;
/*    */   private boolean serverWide;
/*    */   
/*    */   public S28PacketEffect() {}
/*    */   
/*    */   public S28PacketEffect(int soundTypeIn, BlockPos soundPosIn, int soundDataIn, boolean serverWideIn) {
/* 25 */     this.soundType = soundTypeIn;
/* 26 */     this.soundPos = soundPosIn;
/* 27 */     this.soundData = soundDataIn;
/* 28 */     this.serverWide = serverWideIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 35 */     this.soundType = buf.readInt();
/* 36 */     this.soundPos = buf.readBlockPos();
/* 37 */     this.soundData = buf.readInt();
/* 38 */     this.serverWide = buf.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 45 */     buf.writeInt(this.soundType);
/* 46 */     buf.writeBlockPos(this.soundPos);
/* 47 */     buf.writeInt(this.soundData);
/* 48 */     buf.writeBoolean(this.serverWide);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 56 */     handler.handleEffect(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSoundServerwide() {
/* 61 */     return this.serverWide;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSoundType() {
/* 66 */     return this.soundType;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSoundData() {
/* 71 */     return this.soundData;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSoundPos() {
/* 76 */     return this.soundPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S28PacketEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */