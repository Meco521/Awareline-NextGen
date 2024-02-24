/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C0CPacketInput
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private float strafeSpeed;
/*    */   private float forwardSpeed;
/*    */   private boolean jumping;
/*    */   private boolean sneaking;
/*    */   
/*    */   public C0CPacketInput() {}
/*    */   
/*    */   public C0CPacketInput(float strafeSpeed, float forwardSpeed, boolean jumping, boolean sneaking) {
/* 23 */     this.strafeSpeed = strafeSpeed;
/* 24 */     this.forwardSpeed = forwardSpeed;
/* 25 */     this.jumping = jumping;
/* 26 */     this.sneaking = sneaking;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 33 */     this.strafeSpeed = buf.readFloat();
/* 34 */     this.forwardSpeed = buf.readFloat();
/* 35 */     byte b0 = buf.readByte();
/* 36 */     this.jumping = ((b0 & 0x1) > 0);
/* 37 */     this.sneaking = ((b0 & 0x2) > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 44 */     buf.writeFloat(this.strafeSpeed);
/* 45 */     buf.writeFloat(this.forwardSpeed);
/* 46 */     byte b0 = 0;
/*    */     
/* 48 */     if (this.jumping)
/*    */     {
/* 50 */       b0 = (byte)(b0 | 0x1);
/*    */     }
/*    */     
/* 53 */     if (this.sneaking)
/*    */     {
/* 55 */       b0 = (byte)(b0 | 0x2);
/*    */     }
/*    */     
/* 58 */     buf.writeByte(b0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 66 */     handler.processInput(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getStrafeSpeed() {
/* 71 */     return this.strafeSpeed;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getForwardSpeed() {
/* 76 */     return this.forwardSpeed;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isJumping() {
/* 81 */     return this.jumping;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSneaking() {
/* 86 */     return this.sneaking;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C0CPacketInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */