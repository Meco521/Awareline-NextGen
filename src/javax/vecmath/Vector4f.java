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
/*     */ public class Vector4f
/*     */   extends Tuple4f
/*     */ {
/*     */   static final long serialVersionUID = 8749319902347760659L;
/*     */   
/*     */   public Vector4f(float x, float y, float z, float w) {
/*  49 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(float[] v) {
/*  59 */     super(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Vector4f v1) {
/*  69 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Vector4d v1) {
/*  79 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Tuple4f t1) {
/*  89 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Tuple4d t1) {
/*  99 */     super(t1);
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
/*     */   public Vector4f(Tuple3f t1) {
/* 113 */     super(t1.x, t1.y, t1.z, 0.0F);
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
/*     */   public Vector4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 134 */     this.x = t1.x;
/* 135 */     this.y = t1.y;
/* 136 */     this.z = t1.z;
/* 137 */     this.w = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float length() {
/* 147 */     return 
/* 148 */       (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float lengthSquared() {
/* 158 */     return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float dot(Vector4f v1) {
/* 169 */     return this.x * v1.x + this.y * v1.y + this.z * v1.z + this.w * v1.w;
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
/*     */   public final void normalize(Vector4f v1) {
/* 181 */     float norm = (float)(1.0D / Math.sqrt((v1.x * v1.x + v1.y * v1.y + v1.z * v1.z + v1.w * v1.w)));
/*     */     
/* 183 */     v1.x *= norm;
/* 184 */     v1.y *= norm;
/* 185 */     v1.z *= norm;
/* 186 */     v1.w *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 197 */     float norm = (float)(1.0D / Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w)));
/*     */     
/* 199 */     this.x *= norm;
/* 200 */     this.y *= norm;
/* 201 */     this.z *= norm;
/* 202 */     this.w *= norm;
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
/*     */   public final float angle(Vector4f v1) {
/* 215 */     double vDot = (dot(v1) / length() * v1.length());
/* 216 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 217 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 218 */     return (float)Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Vector4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */