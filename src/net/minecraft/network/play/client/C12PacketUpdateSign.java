/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ public class C12PacketUpdateSign
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private BlockPos pos;
/*    */   private IChatComponent[] lines;
/*    */   
/*    */   public C12PacketUpdateSign() {}
/*    */   
/*    */   public C12PacketUpdateSign(BlockPos pos, IChatComponent[] lines) {
/* 20 */     this.pos = pos;
/* 21 */     this.lines = new IChatComponent[] { lines[0], lines[1], lines[2], lines[3] };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 28 */     this.pos = buf.readBlockPos();
/* 29 */     this.lines = new IChatComponent[4];
/*    */     
/* 31 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 33 */       String s = buf.readStringFromBuffer(384);
/* 34 */       IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 35 */       this.lines[i] = ichatcomponent;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 43 */     buf.writeBlockPos(this.pos);
/*    */     
/* 45 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 47 */       IChatComponent ichatcomponent = this.lines[i];
/* 48 */       String s = IChatComponent.Serializer.componentToJson(ichatcomponent);
/* 49 */       buf.writeString(s);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 58 */     handler.processUpdateSign(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 63 */     return this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent[] getLines() {
/* 68 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C12PacketUpdateSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */