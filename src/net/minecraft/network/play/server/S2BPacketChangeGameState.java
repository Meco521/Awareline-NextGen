/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S2BPacketChangeGameState implements Packet<INetHandlerPlayClient> {
/*  9 */   public static final String[] MESSAGE_NAMES = new String[] { "tile.bed.notValid" };
/*    */   
/*    */   private int state;
/*    */   
/*    */   private float field_149141_c;
/*    */ 
/*    */   
/*    */   public S2BPacketChangeGameState() {}
/*    */   
/*    */   public S2BPacketChangeGameState(int stateIn, float p_i45194_2_) {
/* 19 */     this.state = stateIn;
/* 20 */     this.field_149141_c = p_i45194_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 27 */     this.state = buf.readUnsignedByte();
/* 28 */     this.field_149141_c = buf.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 35 */     buf.writeByte(this.state);
/* 36 */     buf.writeFloat(this.field_149141_c);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 44 */     handler.handleChangeGameState(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGameState() {
/* 49 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_149137_d() {
/* 54 */     return this.field_149141_c;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S2BPacketChangeGameState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */