/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S49PacketUpdateEntityNBT
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private NBTTagCompound tagCompound;
/*    */   
/*    */   public S49PacketUpdateEntityNBT() {}
/*    */   
/*    */   public S49PacketUpdateEntityNBT(int entityIdIn, NBTTagCompound tagCompoundIn) {
/* 23 */     this.entityId = entityIdIn;
/* 24 */     this.tagCompound = tagCompoundIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.entityId = buf.readVarIntFromBuffer();
/* 33 */     this.tagCompound = buf.readNBTTagCompoundFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 40 */     buf.writeVarIntToBuffer(this.entityId);
/* 41 */     buf.writeNBTTagCompoundToBuffer(this.tagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 49 */     handler.handleEntityNBT(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound getTagCompound() {
/* 54 */     return this.tagCompound;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity(World worldIn) {
/* 59 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S49PacketUpdateEntityNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */