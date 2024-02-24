/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Tuple3d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5542096614926168415L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   
/*     */   public Tuple3d(double x, double y, double z) {
/*  63 */     this.x = x;
/*  64 */     this.y = y;
/*  65 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(double[] t) {
/*  74 */     this.x = t[0];
/*  75 */     this.y = t[1];
/*  76 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3d t1) {
/*  85 */     this.x = t1.x;
/*  86 */     this.y = t1.y;
/*  87 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3f t1) {
/*  96 */     this.x = t1.x;
/*  97 */     this.y = t1.y;
/*  98 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d() {
/* 106 */     this.x = 0.0D;
/* 107 */     this.y = 0.0D;
/* 108 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double x, double y, double z) {
/* 119 */     this.x = x;
/* 120 */     this.y = y;
/* 121 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 131 */     this.x = t[0];
/* 132 */     this.y = t[1];
/* 133 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 142 */     this.x = t1.x;
/* 143 */     this.y = t1.y;
/* 144 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 153 */     this.x = t1.x;
/* 154 */     this.y = t1.y;
/* 155 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 165 */     t[0] = this.x;
/* 166 */     t[1] = this.y;
/* 167 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3d t) {
/* 177 */     t.x = this.x;
/* 178 */     t.y = this.y;
/* 179 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d t1, Tuple3d t2) {
/* 190 */     t1.x += t2.x;
/* 191 */     t1.y += t2.y;
/* 192 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d t1) {
/* 202 */     this.x += t1.x;
/* 203 */     this.y += t1.y;
/* 204 */     this.z += t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d t1, Tuple3d t2) {
/* 215 */     t1.x -= t2.x;
/* 216 */     t1.y -= t2.y;
/* 217 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d t1) {
/* 227 */     this.x -= t1.x;
/* 228 */     this.y -= t1.y;
/* 229 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3d t1) {
/* 239 */     this.x = -t1.x;
/* 240 */     this.y = -t1.y;
/* 241 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 250 */     this.x = -this.x;
/* 251 */     this.y = -this.y;
/* 252 */     this.z = -this.z;
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
/*     */   public final void scale(double s, Tuple3d t1) {
/* 264 */     this.x = s * t1.x;
/* 265 */     this.y = s * t1.y;
/* 266 */     this.z = s * t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(double s) {
/* 277 */     this.x *= s;
/* 278 */     this.y *= s;
/* 279 */     this.z *= s;
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
/*     */   public final void scaleAdd(double s, Tuple3d t1, Tuple3d t2) {
/* 292 */     this.x = s * t1.x + t2.x;
/* 293 */     this.y = s * t1.y + t2.y;
/* 294 */     this.z = s * t1.z + t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, Tuple3f t1) {
/* 302 */     scaleAdd(s, new Point3d(t1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, Tuple3d t1) {
/* 313 */     this.x = s * this.x + t1.x;
/* 314 */     this.y = s * this.y + t1.y;
/* 315 */     this.z = s * this.z + t1.z;
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
/*     */   public String toString() {
/* 327 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */   public int hashCode() {
/* 341 */     long bits = 1L;
/* 342 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 343 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 344 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 345 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple3d t1) {
/*     */     try {
/* 358 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
/*     */     } catch (NullPointerException e2) {
/* 360 */       return false;
/*     */     } 
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
/*     */   public boolean equals(Object t1) {
/*     */     try {
/* 374 */       Tuple3d t2 = (Tuple3d)t1;
/* 375 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
/*     */     } catch (ClassCastException|NullPointerException e1) {
/* 377 */       return false;
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public boolean epsilonEquals(Tuple3d t1, double epsilon) {
/* 394 */     double diff = this.x - t1.x;
/* 395 */     if (Double.isNaN(diff)) return false; 
/* 396 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 398 */     diff = this.y - t1.y;
/* 399 */     if (Double.isNaN(diff)) return false; 
/* 400 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 402 */     diff = this.z - t1.z;
/* 403 */     if (Double.isNaN(diff)) return false; 
/* 404 */     return (((diff < 0.0D) ? -diff : diff) <= epsilon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max, Tuple3d t) {
/* 413 */     clamp(min, max, t);
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
/*     */   public final void clamp(double min, double max, Tuple3d t) {
/* 425 */     if (t.x > max)
/* 426 */     { this.x = max; }
/* 427 */     else { this.x = Math.max(t.x, min); }
/*     */     
/* 429 */     if (t.y > max)
/* 430 */     { this.y = max; }
/* 431 */     else { this.y = Math.max(t.y, min); }
/*     */     
/* 433 */     if (t.z > max)
/* 434 */     { this.z = max; }
/* 435 */     else { this.z = Math.max(t.z, min); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min, Tuple3d t) {
/* 444 */     clampMin(min, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min, Tuple3d t) {
/* 455 */     this.x = Math.max(t.x, min);
/*     */     
/* 457 */     this.y = Math.max(t.y, min);
/*     */     
/* 459 */     this.z = Math.max(t.z, min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max, Tuple3d t) {
/* 468 */     clampMax(max, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max, Tuple3d t) {
/* 479 */     this.x = Math.min(t.x, max);
/*     */     
/* 481 */     this.y = Math.min(t.y, max);
/*     */     
/* 483 */     this.z = Math.min(t.z, max);
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
/*     */   public final void absolute(Tuple3d t) {
/* 495 */     this.x = Math.abs(t.x);
/* 496 */     this.y = Math.abs(t.y);
/* 497 */     this.z = Math.abs(t.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 506 */     clamp(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double min, double max) {
/* 516 */     if (this.x > max) {
/* 517 */       this.x = max;
/* 518 */     } else if (this.x < min) {
/* 519 */       this.x = min;
/*     */     } 
/*     */     
/* 522 */     if (this.y > max) {
/* 523 */       this.y = max;
/* 524 */     } else if (this.y < min) {
/* 525 */       this.y = min;
/*     */     } 
/*     */     
/* 528 */     if (this.z > max) {
/* 529 */       this.z = max;
/* 530 */     } else if (this.z < min) {
/* 531 */       this.z = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min) {
/* 541 */     clampMin(min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min) {
/* 550 */     if (this.x < min) this.x = min; 
/* 551 */     if (this.y < min) this.y = min; 
/* 552 */     if (this.z < min) this.z = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 561 */     clampMax(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 570 */     if (this.x > max) this.x = max; 
/* 571 */     if (this.y > max) this.y = max; 
/* 572 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 581 */     this.x = Math.abs(this.x);
/* 582 */     this.y = Math.abs(this.y);
/* 583 */     this.z = Math.abs(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, Tuple3d t2, float alpha) {
/* 591 */     interpolate(t1, t2, alpha);
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
/*     */   public final void interpolate(Tuple3d t1, Tuple3d t2, double alpha) {
/* 603 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 604 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
/* 605 */     this.z = (1.0D - alpha) * t1.z + alpha * t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, float alpha) {
/* 613 */     interpolate(t1, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, double alpha) {
/* 624 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 625 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
/* 626 */     this.z = (1.0D - alpha) * this.z + alpha * t1.z;
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
/*     */   public Object clone() {
/*     */     try {
/* 641 */       return super.clone();
/* 642 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 644 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getX() {
/* 656 */     return this.x;
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
/*     */   public final void setX(double x) {
/* 668 */     this.x = x;
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
/*     */   public final double getY() {
/* 680 */     return this.y;
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
/*     */   public final void setY(double y) {
/* 692 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getZ() {
/* 703 */     return this.z;
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
/*     */   public final void setZ(double z) {
/* 715 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Tuple3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */