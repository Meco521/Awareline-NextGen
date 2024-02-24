/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ public class S12PacketEntityVelocity
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityID;
/*     */   public int motionX;
/*     */   public int motionY;
/*     */   public int motionZ;
/*     */   
/*     */   public S12PacketEntityVelocity() {}
/*     */   
/*     */   public S12PacketEntityVelocity(Entity entityIn) {
/*  21 */     this(entityIn.getEntityId(), entityIn.motionX, entityIn.motionY, entityIn.motionZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public S12PacketEntityVelocity(int entityIDIn, double motionXIn, double motionYIn, double motionZIn) {
/*  26 */     this.entityID = entityIDIn;
/*  27 */     double d0 = 3.9D;
/*     */     
/*  29 */     if (motionXIn < -d0)
/*     */     {
/*  31 */       motionXIn = -d0;
/*     */     }
/*     */     
/*  34 */     if (motionYIn < -d0)
/*     */     {
/*  36 */       motionYIn = -d0;
/*     */     }
/*     */     
/*  39 */     if (motionZIn < -d0)
/*     */     {
/*  41 */       motionZIn = -d0;
/*     */     }
/*     */     
/*  44 */     if (motionXIn > d0)
/*     */     {
/*  46 */       motionXIn = d0;
/*     */     }
/*     */     
/*  49 */     if (motionYIn > d0)
/*     */     {
/*  51 */       motionYIn = d0;
/*     */     }
/*     */     
/*  54 */     if (motionZIn > d0)
/*     */     {
/*  56 */       motionZIn = d0;
/*     */     }
/*     */     
/*  59 */     this.motionX = (int)(motionXIn * 8000.0D);
/*  60 */     this.motionY = (int)(motionYIn * 8000.0D);
/*  61 */     this.motionZ = (int)(motionZIn * 8000.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  68 */     this.entityID = buf.readVarIntFromBuffer();
/*  69 */     this.motionX = buf.readShort();
/*  70 */     this.motionY = buf.readShort();
/*  71 */     this.motionZ = buf.readShort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  78 */     buf.writeVarIntToBuffer(this.entityID);
/*  79 */     buf.writeShort(this.motionX);
/*  80 */     buf.writeShort(this.motionY);
/*  81 */     buf.writeShort(this.motionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  89 */     handler.handleEntityVelocity(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/*  94 */     return this.entityID;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMotionX() {
/*  99 */     return this.motionX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMotionY() {
/* 104 */     return this.motionY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMotionZ() {
/* 109 */     return this.motionZ;
/*     */   }
/*     */   
/*     */   public void setX(int motionX) {
/* 113 */     this.motionX = motionX;
/*     */   }
/*     */   
/*     */   public void setY(int motionY) {
/* 117 */     this.motionY = motionY;
/*     */   }
/*     */   
/*     */   public void setZ(int motionZ) {
/* 121 */     this.motionZ = motionZ;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 125 */     return this.motionX;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 129 */     return this.motionY;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 133 */     return this.motionZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S12PacketEntityVelocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */