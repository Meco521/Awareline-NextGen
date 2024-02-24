/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ public class C14PacketTabComplete
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String message;
/*    */   private BlockPos targetBlock;
/*    */   
/*    */   public C14PacketTabComplete() {}
/*    */   
/*    */   public C14PacketTabComplete(String msg) {
/* 20 */     this(msg, (BlockPos)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public C14PacketTabComplete(String msg, BlockPos target) {
/* 25 */     this.message = msg;
/* 26 */     this.targetBlock = target;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 33 */     this.message = buf.readStringFromBuffer(32767);
/* 34 */     boolean flag = buf.readBoolean();
/*    */     
/* 36 */     if (flag)
/*    */     {
/* 38 */       this.targetBlock = buf.readBlockPos();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 46 */     buf.writeString(StringUtils.substring(this.message, 0, 32767));
/* 47 */     boolean flag = (this.targetBlock != null);
/* 48 */     buf.writeBoolean(flag);
/*    */     
/* 50 */     if (flag)
/*    */     {
/* 52 */       buf.writeBlockPos(this.targetBlock);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 61 */     handler.processTabComplete(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 66 */     return this.message;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getTargetBlock() {
/* 71 */     return this.targetBlock;
/*    */   }
/*    */   
/*    */   public void setMessage(String s) {
/* 75 */     this.message = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C14PacketTabComplete.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */