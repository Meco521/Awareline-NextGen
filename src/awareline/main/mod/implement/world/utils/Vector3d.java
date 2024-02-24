/*    */ package awareline.main.mod.implement.world.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Vector3d
/*    */ {
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   
/*    */   public Vector3d(double x, double y, double z) {
/* 14 */     this.x = x;
/* 15 */     this.y = y;
/* 16 */     this.z = z;
/*    */   }
/*    */   
/*    */   public Vector3d add(double x, double y, double z) {
/* 20 */     return new Vector3d(this.x + x, this.y + y, this.z + z);
/*    */   }
/*    */   
/*    */   public Vector3d add(Vector3d vector) {
/* 24 */     return add(vector.x, vector.y, vector.z);
/*    */   }
/*    */   
/*    */   public Vector3d subtract(double x, double y, double z) {
/* 28 */     return add(-x, -y, -z);
/*    */   }
/*    */   
/*    */   public Vector3d subtract(Vector3d vector) {
/* 32 */     return add(-vector.x, -vector.y, -vector.z);
/*    */   }
/*    */   
/*    */   public double length() {
/* 36 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*    */   }
/*    */   
/*    */   public double getX() {
/* 40 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 44 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 48 */     return this.z;
/*    */   }
/*    */   
/*    */   public Vector3d multiply(double v) {
/* 52 */     return new Vector3d(this.x * v, this.y * v, this.z * v);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 57 */     if (!(obj instanceof Vector3d)) return false;
/*    */     
/* 59 */     Vector3d vector = (Vector3d)obj;
/* 60 */     return (Math.floor(this.x) == Math.floor(vector.x) && Math.floor(this.y) == Math.floor(vector.y) && Math.floor(this.z) == Math.floor(vector.z));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\Vector3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */