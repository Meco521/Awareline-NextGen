/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.vec;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class Vec3 {
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   
/*    */   public Vec3(double x, double y, double z) {
/* 11 */     this.x = x;
/* 12 */     this.y = y;
/* 13 */     this.z = z;
/*    */   }
/*    */   
/*    */   public Vec3(BlockPos pos) {
/* 17 */     this.x = pos.getX();
/* 18 */     this.y = pos.getY();
/* 19 */     this.z = pos.getZ();
/*    */   }
/*    */   
/*    */   public double getX() {
/* 23 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 27 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 31 */     return this.z;
/*    */   }
/*    */   
/*    */   public Vec3 addVector(double x, double y, double z) {
/* 35 */     return new Vec3(this.x + x, this.y + y, this.z + z);
/*    */   }
/*    */   
/*    */   public Vec3 floor() {
/* 39 */     return new Vec3(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
/*    */   }
/*    */   
/*    */   public double squareDistanceTo(Vec3 v) {
/* 43 */     return Math.pow(v.x - this.x, 2.0D) + Math.pow(v.y - this.y, 2.0D) + Math.pow(v.z - this.z, 2.0D);
/*    */   }
/*    */   
/*    */   public double distanceTo(Vec3 vec) {
/* 47 */     double d0 = vec.x - this.x;
/* 48 */     double d1 = vec.y - this.y;
/* 49 */     double d2 = vec.z - this.z;
/* 50 */     double var8 = d0 * d0 + d1 * d1 + d2 * d2;
/* 51 */     return Math.sqrt(var8);
/*    */   }
/*    */   
/*    */   public Vec3 add(Vec3 v) {
/* 55 */     return addVector(v.x, v.y, v.z);
/*    */   }
/*    */   
/*    */   public net.minecraft.util.Vec3 mc() {
/* 59 */     return new net.minecraft.util.Vec3(this.x, this.y, this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     return "[" + this.x + ";" + this.y + ";" + this.z + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\liquidbounce\vec\Vec3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */