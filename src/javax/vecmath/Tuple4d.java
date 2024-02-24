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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Tuple4d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -4748953690425311052L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double w;
/*     */   
/*     */   public Tuple4d(double x, double y, double z, double w) {
/*  69 */     this.x = x;
/*  70 */     this.y = y;
/*  71 */     this.z = z;
/*  72 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(double[] t) {
/*  83 */     this.x = t[0];
/*  84 */     this.y = t[1];
/*  85 */     this.z = t[2];
/*  86 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4d t1) {
/*  96 */     this.x = t1.x;
/*  97 */     this.y = t1.y;
/*  98 */     this.z = t1.z;
/*  99 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4f t1) {
/* 109 */     this.x = t1.x;
/* 110 */     this.y = t1.y;
/* 111 */     this.z = t1.z;
/* 112 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d() {
/* 121 */     this.x = 0.0D;
/* 122 */     this.y = 0.0D;
/* 123 */     this.z = 0.0D;
/* 124 */     this.w = 0.0D;
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
/*     */   public final void set(double x, double y, double z, double w) {
/* 137 */     this.x = x;
/* 138 */     this.y = y;
/* 139 */     this.z = z;
/* 140 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 150 */     this.x = t[0];
/* 151 */     this.y = t[1];
/* 152 */     this.z = t[2];
/* 153 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d t1) {
/* 163 */     this.x = t1.x;
/* 164 */     this.y = t1.y;
/* 165 */     this.z = t1.z;
/* 166 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f t1) {
/* 176 */     this.x = t1.x;
/* 177 */     this.y = t1.y;
/* 178 */     this.z = t1.z;
/* 179 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 190 */     t[0] = this.x;
/* 191 */     t[1] = this.y;
/* 192 */     t[2] = this.z;
/* 193 */     t[3] = this.w;
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
/*     */   public final void get(Tuple4d t) {
/* 205 */     t.x = this.x;
/* 206 */     t.y = this.y;
/* 207 */     t.z = this.z;
/* 208 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d t1, Tuple4d t2) {
/* 219 */     t1.x += t2.x;
/* 220 */     t1.y += t2.y;
/* 221 */     t1.z += t2.z;
/* 222 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d t1) {
/* 232 */     this.x += t1.x;
/* 233 */     this.y += t1.y;
/* 234 */     this.z += t1.z;
/* 235 */     this.w += t1.w;
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
/*     */   public final void sub(Tuple4d t1, Tuple4d t2) {
/* 247 */     t1.x -= t2.x;
/* 248 */     t1.y -= t2.y;
/* 249 */     t1.z -= t2.z;
/* 250 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4d t1) {
/* 261 */     this.x -= t1.x;
/* 262 */     this.y -= t1.y;
/* 263 */     this.z -= t1.z;
/* 264 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4d t1) {
/* 274 */     this.x = -t1.x;
/* 275 */     this.y = -t1.y;
/* 276 */     this.z = -t1.z;
/* 277 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 286 */     this.x = -this.x;
/* 287 */     this.y = -this.y;
/* 288 */     this.z = -this.z;
/* 289 */     this.w = -this.w;
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
/*     */   public final void scale(double s, Tuple4d t1) {
/* 301 */     this.x = s * t1.x;
/* 302 */     this.y = s * t1.y;
/* 303 */     this.z = s * t1.z;
/* 304 */     this.w = s * t1.w;
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
/* 315 */     this.x *= s;
/* 316 */     this.y *= s;
/* 317 */     this.z *= s;
/* 318 */     this.w *= s;
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
/*     */   public final void scaleAdd(double s, Tuple4d t1, Tuple4d t2) {
/* 331 */     this.x = s * t1.x + t2.x;
/* 332 */     this.y = s * t1.y + t2.y;
/* 333 */     this.z = s * t1.z + t2.z;
/* 334 */     this.w = s * t1.w + t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(float s, Tuple4d t1) {
/* 343 */     scaleAdd(s, t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, Tuple4d t1) {
/* 354 */     this.x = s * this.x + t1.x;
/* 355 */     this.y = s * this.y + t1.y;
/* 356 */     this.z = s * this.z + t1.z;
/* 357 */     this.w = s * this.w + t1.w;
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
/* 369 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
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
/*     */   public boolean equals(Tuple4d t1) {
/*     */     try {
/* 382 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z && this.w == t1.w);
/*     */     } catch (NullPointerException e2) {
/*     */       
/* 385 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     try {
/* 400 */       Tuple4d t2 = (Tuple4d)t1;
/* 401 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z && this.w == t2.w);
/*     */     } catch (NullPointerException|ClassCastException e2) {
/*     */       
/* 404 */       return false;
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
/*     */   
/*     */   public boolean epsilonEquals(Tuple4d t1, double epsilon) {
/* 422 */     double diff = this.x - t1.x;
/* 423 */     if (Double.isNaN(diff)) return false; 
/* 424 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 426 */     diff = this.y - t1.y;
/* 427 */     if (Double.isNaN(diff)) return false; 
/* 428 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 430 */     diff = this.z - t1.z;
/* 431 */     if (Double.isNaN(diff)) return false; 
/* 432 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 434 */     diff = this.w - t1.w;
/* 435 */     if (Double.isNaN(diff)) return false; 
/* 436 */     return (((diff < 0.0D) ? -diff : diff) <= epsilon);
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
/*     */   public int hashCode() {
/* 451 */     long bits = 1L;
/* 452 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 453 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 454 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 455 */     bits = VecMathUtil.hashDoubleBits(bits, this.w);
/* 456 */     return VecMathUtil.hashFinish(bits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max, Tuple4d t) {
/* 464 */     clamp(min, max, t);
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
/*     */   public final void clamp(double min, double max, Tuple4d t) {
/* 476 */     if (t.x > max)
/* 477 */     { this.x = max; }
/* 478 */     else { this.x = Math.max(t.x, min); }
/*     */     
/* 480 */     if (t.y > max)
/* 481 */     { this.y = max; }
/* 482 */     else { this.y = Math.max(t.y, min); }
/*     */     
/* 484 */     if (t.z > max)
/* 485 */     { this.z = max; }
/* 486 */     else { this.z = Math.max(t.z, min); }
/*     */     
/* 488 */     if (t.w > max)
/* 489 */     { this.w = max; }
/* 490 */     else { this.w = Math.max(t.w, min); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min, Tuple4d t) {
/* 499 */     clampMin(min, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min, Tuple4d t) {
/* 510 */     this.x = Math.max(t.x, min);
/*     */     
/* 512 */     this.y = Math.max(t.y, min);
/*     */     
/* 514 */     this.z = Math.max(t.z, min);
/*     */     
/* 516 */     this.w = Math.max(t.w, min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max, Tuple4d t) {
/* 525 */     clampMax(max, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max, Tuple4d t) {
/* 536 */     this.x = Math.min(t.x, max);
/*     */     
/* 538 */     this.y = Math.min(t.y, max);
/*     */     
/* 540 */     this.z = Math.min(t.z, max);
/*     */     
/* 542 */     if (t.w > max) {
/* 543 */       this.w = max;
/*     */     } else {
/* 545 */       this.w = t.z;
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
/*     */   public final void absolute(Tuple4d t) {
/* 558 */     this.x = Math.abs(t.x);
/* 559 */     this.y = Math.abs(t.y);
/* 560 */     this.z = Math.abs(t.z);
/* 561 */     this.w = Math.abs(t.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 571 */     clamp(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double min, double max) {
/* 581 */     if (this.x > max) {
/* 582 */       this.x = max;
/* 583 */     } else if (this.x < min) {
/* 584 */       this.x = min;
/*     */     } 
/*     */     
/* 587 */     if (this.y > max) {
/* 588 */       this.y = max;
/* 589 */     } else if (this.y < min) {
/* 590 */       this.y = min;
/*     */     } 
/*     */     
/* 593 */     if (this.z > max) {
/* 594 */       this.z = max;
/* 595 */     } else if (this.z < min) {
/* 596 */       this.z = min;
/*     */     } 
/*     */     
/* 599 */     if (this.w > max) {
/* 600 */       this.w = max;
/* 601 */     } else if (this.w < min) {
/* 602 */       this.w = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min) {
/* 612 */     clampMin(min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min) {
/* 621 */     if (this.x < min) this.x = min; 
/* 622 */     if (this.y < min) this.y = min; 
/* 623 */     if (this.z < min) this.z = min; 
/* 624 */     if (this.w < min) this.w = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 632 */     clampMax(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 641 */     if (this.x > max) this.x = max; 
/* 642 */     if (this.y > max) this.y = max; 
/* 643 */     if (this.z > max) this.z = max; 
/* 644 */     if (this.w > max) this.w = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 654 */     this.x = Math.abs(this.x);
/* 655 */     this.y = Math.abs(this.y);
/* 656 */     this.z = Math.abs(this.z);
/* 657 */     this.w = Math.abs(this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, Tuple4d t2, float alpha) {
/* 666 */     interpolate(t1, t2, alpha);
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
/*     */   public void interpolate(Tuple4d t1, Tuple4d t2, double alpha) {
/* 678 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 679 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
/* 680 */     this.z = (1.0D - alpha) * t1.z + alpha * t2.z;
/* 681 */     this.w = (1.0D - alpha) * t1.w + alpha * t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, float alpha) {
/* 689 */     interpolate(t1, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, double alpha) {
/* 700 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 701 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
/* 702 */     this.z = (1.0D - alpha) * this.z + alpha * t1.z;
/* 703 */     this.w = (1.0D - alpha) * this.w + alpha * t1.w;
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
/* 718 */       return super.clone();
/* 719 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 721 */       throw new InternalError();
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
/* 733 */     return this.x;
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
/* 745 */     this.x = x;
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
/* 757 */     return this.y;
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
/* 769 */     this.y = y;
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
/* 780 */     return this.z;
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
/* 792 */     this.z = z;
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
/*     */   public final double getW() {
/* 804 */     return this.w;
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
/*     */   public final void setW(double w) {
/* 816 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Tuple4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */