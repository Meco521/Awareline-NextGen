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
/*     */ 
/*     */ 
/*     */ public class Point4f
/*     */   extends Tuple4f
/*     */ {
/*     */   static final long serialVersionUID = 4643134103185764459L;
/*     */   
/*     */   public Point4f(float x, float y, float z, float w) {
/*  50 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(float[] p) {
/*  60 */     super(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Point4f p1) {
/*  70 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Point4d p1) {
/*  80 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Tuple4f t1) {
/*  90 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Tuple4d t1) {
/* 100 */     super(t1);
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
/*     */   
/*     */   public Point4f(Tuple3f t1) {
/* 114 */     super(t1.x, t1.y, t1.z, 1.0F);
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
/*     */   public Point4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 135 */     this.x = t1.x;
/* 136 */     this.y = t1.y;
/* 137 */     this.z = t1.z;
/* 138 */     this.w = 1.0F;
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
/*     */   public final float distanceSquared(Point4f p1) {
/* 151 */     float dx = this.x - p1.x;
/* 152 */     float dy = this.y - p1.y;
/* 153 */     float dz = this.z - p1.z;
/* 154 */     float dw = this.w - p1.w;
/* 155 */     return dx * dx + dy * dy + dz * dz + dw * dw;
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
/*     */   public final float distance(Point4f p1) {
/* 168 */     float dx = this.x - p1.x;
/* 169 */     float dy = this.y - p1.y;
/* 170 */     float dz = this.z - p1.z;
/* 171 */     float dw = this.w - p1.w;
/* 172 */     return (float)Math.sqrt((dx * dx + dy * dy + dz * dz + dw * dw));
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
/*     */   public final float distanceL1(Point4f p1) {
/* 185 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + Math.abs(this.z - p1.z) + Math.abs(this.w - p1.w);
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
/*     */   
/*     */   public final float distanceLinf(Point4f p1) {
/* 199 */     float t1 = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/* 200 */     float t2 = Math.max(Math.abs(this.z - p1.z), Math.abs(this.w - p1.w));
/*     */     
/* 202 */     return Math.max(t1, t2);
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
/*     */   
/*     */   public final void project(Point4f p1) {
/* 216 */     float oneOw = 1.0F / p1.w;
/* 217 */     p1.x *= oneOw;
/* 218 */     p1.y *= oneOw;
/* 219 */     p1.z *= oneOw;
/* 220 */     this.w = 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Point4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */