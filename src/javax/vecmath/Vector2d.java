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
/*     */ public class Vector2d
/*     */   extends Tuple2d
/*     */ {
/*     */   static final long serialVersionUID = 8572646365302599857L;
/*     */   
/*     */   public final double dot(Vector2d v1) {
/*  47 */     return this.x * v1.x + this.y * v1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double length() {
/*  57 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double lengthSquared() {
/*  66 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(Vector2d v1) {
/*  77 */     double norm = 1.0D / Math.sqrt(v1.x * v1.x + v1.y * v1.y);
/*  78 */     v1.x *= norm;
/*  79 */     v1.y *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/*  89 */     double norm = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y);
/*  90 */     this.x *= norm;
/*  91 */     this.y *= norm;
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
/*     */   public final double angle(Vector2d v1) {
/* 103 */     double vDot = dot(v1) / length() * v1.length();
/* 104 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 105 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 106 */     return Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Vector2d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */