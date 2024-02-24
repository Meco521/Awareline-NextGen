/*     */ package awareline.main.mod.implement.visual.sucks.ParticlesUtils;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ public class Location {
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   
/*     */   public Location(double x, double y, double z, float yaw, float pitch) {
/*  16 */     this.x = x;
/*  17 */     this.y = y;
/*  18 */     this.z = z;
/*  19 */     this.yaw = yaw;
/*  20 */     this.pitch = pitch;
/*     */   }
/*     */   
/*     */   public Location(double x, double y, double z) {
/*  24 */     this.x = x;
/*  25 */     this.y = y;
/*  26 */     this.z = z;
/*  27 */     this.yaw = 0.0F;
/*  28 */     this.pitch = 0.0F;
/*     */   }
/*     */   
/*     */   public Location(BlockPos pos) {
/*  32 */     this.x = pos.getX();
/*  33 */     this.y = pos.getY();
/*  34 */     this.z = pos.getZ();
/*  35 */     this.yaw = 0.0F;
/*  36 */     this.pitch = 0.0F;
/*     */   }
/*     */   
/*     */   public Location(int x, int y, int z) {
/*  40 */     this.x = x;
/*  41 */     this.y = y;
/*  42 */     this.z = z;
/*  43 */     this.yaw = 0.0F;
/*  44 */     this.pitch = 0.0F;
/*     */   }
/*     */   
/*     */   public Location(EntityLivingBase entity) {
/*  48 */     this.x = entity.posX;
/*  49 */     this.y = entity.posY;
/*  50 */     this.z = entity.posZ;
/*  51 */     this.yaw = 0.0F;
/*  52 */     this.pitch = 0.0F;
/*     */   }
/*     */   
/*     */   public Location add(int x, int y, int z) {
/*  56 */     this.x += x;
/*  57 */     this.y += y;
/*  58 */     this.z += z;
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public Location add(double x, double y, double z) {
/*  63 */     this.x += x;
/*  64 */     this.y += y;
/*  65 */     this.z += z;
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public Location subtract(int x, int y, int z) {
/*  70 */     this.x -= x;
/*  71 */     this.y -= y;
/*  72 */     this.z -= z;
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public Location subtract(double x, double y, double z) {
/*  77 */     this.x -= x;
/*  78 */     this.y -= y;
/*  79 */     this.z -= z;
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/*  84 */     return (Minecraft.getMinecraft()).theWorld.getBlockState(toBlockPos()).getBlock();
/*     */   }
/*     */   
/*     */   public double getX() {
/*  88 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/*  92 */     this.x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  96 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/* 100 */     this.y = y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 104 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setZ(double z) {
/* 108 */     this.z = z;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/* 112 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public Location setYaw(float yaw) {
/* 116 */     this.yaw = yaw;
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/* 121 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public Location setPitch(float pitch) {
/* 125 */     this.pitch = pitch;
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   public BlockPos toBlockPos() {
/* 130 */     return new BlockPos(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public double distanceTo(Location loc) {
/* 134 */     double dx = loc.x - this.x;
/* 135 */     double dz = loc.z - this.z;
/* 136 */     double dy = loc.y - this.y;
/* 137 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\sucks\ParticlesUtils\Location.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */