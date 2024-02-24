/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S03PacketTimeUpdate
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private long totalWorldTime;
/*    */   private long worldTime;
/*    */   
/*    */   public S03PacketTimeUpdate() {}
/*    */   
/*    */   public S03PacketTimeUpdate(long totalWorldTimeIn, long totalTimeIn, boolean doDayLightCycle) {
/* 18 */     this.totalWorldTime = totalWorldTimeIn;
/* 19 */     this.worldTime = totalTimeIn;
/*    */     
/* 21 */     if (!doDayLightCycle) {
/*    */       
/* 23 */       this.worldTime = -this.worldTime;
/*    */       
/* 25 */       if (this.worldTime == 0L)
/*    */       {
/* 27 */         this.worldTime = -1L;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 36 */     this.totalWorldTime = buf.readLong();
/* 37 */     this.worldTime = buf.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 44 */     buf.writeLong(this.totalWorldTime);
/* 45 */     buf.writeLong(this.worldTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 53 */     handler.handleTimeUpdate(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getTotalWorldTime() {
/* 58 */     return this.totalWorldTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getWorldTime() {
/* 63 */     return this.worldTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S03PacketTimeUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */