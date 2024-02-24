/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S13PacketDestroyEntities
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int[] entityIDs;
/*    */   
/*    */   public S13PacketDestroyEntities() {}
/*    */   
/*    */   public S13PacketDestroyEntities(int... entityIDsIn) {
/* 17 */     this.entityIDs = entityIDsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 24 */     this.entityIDs = new int[buf.readVarIntFromBuffer()];
/*    */     
/* 26 */     for (int i = 0; i < this.entityIDs.length; i++)
/*    */     {
/* 28 */       this.entityIDs[i] = buf.readVarIntFromBuffer();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 36 */     buf.writeVarIntToBuffer(this.entityIDs.length);
/*    */     
/* 38 */     for (int i = 0; i < this.entityIDs.length; i++)
/*    */     {
/* 40 */       buf.writeVarIntToBuffer(this.entityIDs[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 49 */     handler.handleDestroyEntities(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getEntityIDs() {
/* 54 */     return this.entityIDs;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S13PacketDestroyEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */