/*     */ package javax.vecmath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Point3d
/*     */   extends Tuple3d
/*     */ {
/*     */   static final long serialVersionUID = 5718062286069042927L;
/*     */   
/*     */   public Point3d(double x, double y, double z) {
/*  48 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(double[] p) {
/*  58 */     super(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Point3d p1) {
/*  68 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Point3f p1) {
/*  78 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Tuple3f t1) {
/*  88 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Tuple3d t1) {
/*  98 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceSquared(Point3d p1) {
/* 119 */     double dx = this.x - p1.x;
/* 120 */     double dy = this.y - p1.y;
/* 121 */     double dz = this.z - p1.z;
/* 122 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distance(Point3d p1) {
/* 135 */     double dx = this.x - p1.x;
/* 136 */     double dy = this.y - p1.y;
/* 137 */     double dz = this.z - p1.z;
/* 138 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceL1(Point3d p1) {
/* 150 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + 
/* 151 */       Math.abs(this.z - p1.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceLinf(Point3d p1) {
/* 164 */     double tmp = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/*     */     
/* 166 */     return Math.max(tmp, Math.abs(this.z - p1.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void project(Point4d p1) {
/* 179 */     double oneOw = 1.0D / p1.w;
/* 180 */     this.x = p1.x * oneOw;
/* 181 */     this.y = p1.y * oneOw;
/* 182 */     this.z = p1.z * oneOw;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Point3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */