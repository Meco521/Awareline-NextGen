/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C0BPacketEntityAction
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int entityID;
/*    */   private Action action;
/*    */   private int auxData;
/*    */   
/*    */   public C0BPacketEntityAction() {}
/*    */   
/*    */   public C0BPacketEntityAction(Entity entity, Action action) {
/* 20 */     this(entity, action, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public C0BPacketEntityAction(Entity entity, Action action, int auxData) {
/* 25 */     this.entityID = entity.getEntityId();
/* 26 */     this.action = action;
/* 27 */     this.auxData = auxData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 34 */     this.entityID = buf.readVarIntFromBuffer();
/* 35 */     this.action = (Action)buf.readEnumValue(Action.class);
/* 36 */     this.auxData = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 43 */     buf.writeVarIntToBuffer(this.entityID);
/* 44 */     buf.writeEnumValue(this.action);
/* 45 */     buf.writeVarIntToBuffer(this.auxData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 53 */     handler.processEntityAction(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 58 */     return this.action;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAuxData() {
/* 63 */     return this.auxData;
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 68 */     START_SNEAKING,
/* 69 */     STOP_SNEAKING,
/* 70 */     STOP_SLEEPING,
/* 71 */     START_SPRINTING,
/* 72 */     STOP_SPRINTING,
/* 73 */     RIDING_JUMP,
/* 74 */     OPEN_INVENTORY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C0BPacketEntityAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */