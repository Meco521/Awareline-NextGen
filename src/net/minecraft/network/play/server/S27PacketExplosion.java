/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S27PacketExplosion
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public double posX;
/*     */   public double posY;
/*     */   public double posZ;
/*     */   private float strength;
/*     */   private List<BlockPos> affectedBlockPositions;
/*     */   public float field_149152_f;
/*     */   public float field_149153_g;
/*     */   public float field_149159_h;
/*     */   
/*     */   public S27PacketExplosion() {}
/*     */   
/*     */   public S27PacketExplosion(double p_i45193_1_, double y, double z, float strengthIn, List<BlockPos> affectedBlocksIn, Vec3 p_i45193_9_) {
/*  30 */     this.posX = p_i45193_1_;
/*  31 */     this.posY = y;
/*  32 */     this.posZ = z;
/*  33 */     this.strength = strengthIn;
/*  34 */     this.affectedBlockPositions = Lists.newArrayList(affectedBlocksIn);
/*     */     
/*  36 */     if (p_i45193_9_ != null) {
/*     */       
/*  38 */       this.field_149152_f = (float)p_i45193_9_.xCoord;
/*  39 */       this.field_149153_g = (float)p_i45193_9_.yCoord;
/*  40 */       this.field_149159_h = (float)p_i45193_9_.zCoord;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  48 */     this.posX = buf.readFloat();
/*  49 */     this.posY = buf.readFloat();
/*  50 */     this.posZ = buf.readFloat();
/*  51 */     this.strength = buf.readFloat();
/*  52 */     int i = buf.readInt();
/*  53 */     this.affectedBlockPositions = Lists.newArrayListWithCapacity(i);
/*  54 */     int j = (int)this.posX;
/*  55 */     int k = (int)this.posY;
/*  56 */     int l = (int)this.posZ;
/*     */     
/*  58 */     for (int i1 = 0; i1 < i; i1++) {
/*     */       
/*  60 */       int j1 = buf.readByte() + j;
/*  61 */       int k1 = buf.readByte() + k;
/*  62 */       int l1 = buf.readByte() + l;
/*  63 */       this.affectedBlockPositions.add(new BlockPos(j1, k1, l1));
/*     */     } 
/*     */     
/*  66 */     this.field_149152_f = buf.readFloat();
/*  67 */     this.field_149153_g = buf.readFloat();
/*  68 */     this.field_149159_h = buf.readFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  75 */     buf.writeFloat((float)this.posX);
/*  76 */     buf.writeFloat((float)this.posY);
/*  77 */     buf.writeFloat((float)this.posZ);
/*  78 */     buf.writeFloat(this.strength);
/*  79 */     buf.writeInt(this.affectedBlockPositions.size());
/*  80 */     int i = (int)this.posX;
/*  81 */     int j = (int)this.posY;
/*  82 */     int k = (int)this.posZ;
/*     */     
/*  84 */     for (BlockPos blockpos : this.affectedBlockPositions) {
/*     */       
/*  86 */       int l = blockpos.getX() - i;
/*  87 */       int i1 = blockpos.getY() - j;
/*  88 */       int j1 = blockpos.getZ() - k;
/*  89 */       buf.writeByte(l);
/*  90 */       buf.writeByte(i1);
/*  91 */       buf.writeByte(j1);
/*     */     } 
/*     */     
/*  94 */     buf.writeFloat(this.field_149152_f);
/*  95 */     buf.writeFloat(this.field_149153_g);
/*  96 */     buf.writeFloat(this.field_149159_h);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 104 */     handler.handleExplosion(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_149149_c() {
/* 109 */     return this.field_149152_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_149144_d() {
/* 114 */     return this.field_149153_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_149147_e() {
/* 119 */     return this.field_149159_h;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/* 124 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 129 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 134 */     return this.posZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrength() {
/* 139 */     return this.strength;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getAffectedBlockPositions() {
/* 144 */     return this.affectedBlockPositions;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMotionX() {
/* 149 */     return this.field_149152_f;
/*     */   }
/*     */   
/*     */   public float getMotionY() {
/* 153 */     return this.field_149153_g;
/*     */   }
/*     */   
/*     */   public float getMotionZ() {
/* 157 */     return this.field_149159_h;
/*     */   }
/*     */   
/*     */   public void setX(double posX) {
/* 161 */     this.posX = posX;
/*     */   }
/*     */   
/*     */   public void setY(double posY) {
/* 165 */     this.posY = posY;
/*     */   }
/*     */   
/*     */   public void setZ(double posZ) {
/* 169 */     this.posZ = posZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S27PacketExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */