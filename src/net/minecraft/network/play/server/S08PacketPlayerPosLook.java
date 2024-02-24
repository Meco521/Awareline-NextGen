/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S08PacketPlayerPosLook
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public float yaw;
/*     */   public float pitch;
/*     */   private Set<EnumFlags> field_179835_f;
/*     */   
/*     */   public S08PacketPlayerPosLook() {}
/*     */   
/*     */   public S08PacketPlayerPosLook(double xIn, double yIn, double zIn, float yawIn, float pitchIn, Set<EnumFlags> p_i45993_9_) {
/*  25 */     this.x = xIn;
/*  26 */     this.y = yIn;
/*  27 */     this.z = zIn;
/*  28 */     this.yaw = yawIn;
/*  29 */     this.pitch = pitchIn;
/*  30 */     this.field_179835_f = p_i45993_9_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  37 */     this.x = buf.readDouble();
/*  38 */     this.y = buf.readDouble();
/*  39 */     this.z = buf.readDouble();
/*  40 */     this.yaw = buf.readFloat();
/*  41 */     this.pitch = buf.readFloat();
/*  42 */     this.field_179835_f = EnumFlags.func_180053_a(buf.readUnsignedByte());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  49 */     buf.writeDouble(this.x);
/*  50 */     buf.writeDouble(this.y);
/*  51 */     buf.writeDouble(this.z);
/*  52 */     buf.writeFloat(this.yaw);
/*  53 */     buf.writeFloat(this.pitch);
/*  54 */     buf.writeByte(EnumFlags.func_180056_a(this.field_179835_f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  62 */     handler.handlePlayerPosLook(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  67 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  72 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  77 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYaw() {
/*  82 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch() {
/*  87 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EnumFlags> func_179834_f() {
/*  92 */     return this.field_179835_f;
/*     */   }
/*     */   
/*     */   public enum EnumFlags
/*     */   {
/*  97 */     X(0),
/*  98 */     Y(1),
/*  99 */     Z(2),
/* 100 */     Y_ROT(3),
/* 101 */     X_ROT(4);
/*     */     
/*     */     private final int field_180058_f;
/*     */ 
/*     */     
/*     */     EnumFlags(int p_i45992_3_) {
/* 107 */       this.field_180058_f = p_i45992_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     private int func_180055_a() {
/* 112 */       return 1 << this.field_180058_f;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_180054_b(int p_180054_1_) {
/* 117 */       return ((p_180054_1_ & func_180055_a()) == func_180055_a());
/*     */     }
/*     */ 
/*     */     
/*     */     public static Set<EnumFlags> func_180053_a(int p_180053_0_) {
/* 122 */       Set<EnumFlags> set = EnumSet.noneOf(EnumFlags.class);
/*     */       
/* 124 */       for (EnumFlags s08packetplayerposlook$enumflags : values()) {
/*     */         
/* 126 */         if (s08packetplayerposlook$enumflags.func_180054_b(p_180053_0_))
/*     */         {
/* 128 */           set.add(s08packetplayerposlook$enumflags);
/*     */         }
/*     */       } 
/*     */       
/* 132 */       return set;
/*     */     }
/*     */ 
/*     */     
/*     */     public static int func_180056_a(Set<EnumFlags> p_180056_0_) {
/* 137 */       int i = 0;
/*     */       
/* 139 */       for (EnumFlags s08packetplayerposlook$enumflags : p_180056_0_)
/*     */       {
/* 141 */         i |= s08packetplayerposlook$enumflags.func_180055_a();
/*     */       }
/*     */       
/* 144 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S08PacketPlayerPosLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */