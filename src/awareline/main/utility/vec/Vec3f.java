/*     */ package awareline.main.utility.vec;
/*     */ 
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public final class Vec3f {
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   
/*  10 */   public double getX() { return this.x; }
/*  11 */   public double getY() { return this.y; } public double getZ() {
/*  12 */     return this.z;
/*     */   }
/*     */   public Vec3f() {
/*  15 */     this(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public Vec3f(Vec3f vec) {
/*  19 */     this(vec.x, vec.y, vec.z);
/*     */   }
/*     */   
/*     */   public Vec3f(double x, double y, double z) {
/*  23 */     this.x = x;
/*  24 */     this.y = y;
/*  25 */     this.z = z;
/*     */   }
/*     */   
/*     */   public Vec3f setX(double x) {
/*  29 */     this.x = x;
/*  30 */     return this;
/*     */   }
/*     */   
/*     */   public Vec3f setY(double y) {
/*  34 */     this.y = y;
/*  35 */     return this;
/*     */   }
/*     */   
/*     */   public Vec3f setZ(double z) {
/*  39 */     this.z = z;
/*  40 */     return this;
/*     */   }
/*     */   
/*     */   public Vec3f add(Vec3f vec) {
/*  44 */     return add(vec.x, vec.y, vec.z);
/*     */   }
/*     */   
/*     */   public Vec3f add(double x, double y, double z) {
/*  48 */     return new Vec3f(this.x + x, this.y + y, this.z + z);
/*     */   }
/*     */   
/*     */   public Vec3f sub(Vec3f vec) {
/*  52 */     return new Vec3f(this.x - vec.x, this.y - vec.y, this.z - vec.z);
/*     */   }
/*     */   
/*     */   public Vec3f sub(double x, double y, double z) {
/*  56 */     return new Vec3f(this.x - x, this.y - y, this.z - z);
/*     */   }
/*     */   
/*     */   public Vec3f scale(float scale) {
/*  60 */     return new Vec3f(this.x * scale, this.y * scale, this.z * scale);
/*     */   }
/*     */   
/*     */   public Vec3f copy() {
/*  64 */     return new Vec3f(this);
/*     */   }
/*     */   
/*     */   public Vec3f transfer(Vec3f vec) {
/*  68 */     this.x = vec.x;
/*  69 */     this.y = vec.y;
/*  70 */     this.z = vec.z;
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   public double distanceTo(Vec3f vec) {
/*  75 */     double dx = this.x - vec.x;
/*  76 */     double dy = this.y - vec.y;
/*  77 */     double dz = this.z - vec.z;
/*  78 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */   
/*     */   public Vec2f rotationsTo(Vec3f vec) {
/*  82 */     double[] diff = { vec.x - this.x, vec.y - this.y, vec.z - this.z };
/*  83 */     double hDist = Math.sqrt(diff[0] * diff[0] + diff[2] * diff[2]);
/*  84 */     return new Vec2f(Math.toDegrees(Math.atan2(diff[2], diff[0])) - 90.0D, -Math.toDegrees(Math.atan2(diff[1], hDist)));
/*     */   }
/*     */   
/*     */   public Vec3f toScreen() {
/*  88 */     return GLUtils.toScreen(this);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  92 */     return "Vec3{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
/*     */   }
/*     */   
/*     */   public Vec3 subtract(Vec3 vec) {
/*  96 */     return subtract(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */   
/*     */   public Vec3 addVector(double x, double y, double z) {
/* 100 */     return new Vec3(this.x + x, this.y + y, this.z + z);
/*     */   }
/*     */   
/*     */   public Vec3 subtract(double x, double y, double z) {
/* 104 */     return addVector(-x, -y, -z);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\vec\Vec3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */