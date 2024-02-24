/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C18PacketSpectate
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private UUID id;
/*    */   
/*    */   public C18PacketSpectate() {}
/*    */   
/*    */   public C18PacketSpectate(UUID id) {
/* 21 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 28 */     this.id = buf.readUuid();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 35 */     buf.writeUuid(this.id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 43 */     handler.handleSpectate(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity(WorldServer worldIn) {
/* 48 */     return worldIn.getEntityFromUuid(this.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C18PacketSpectate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */