/*     */ package net.minecraft.VLOUBOOS.javax.vecmath;
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
/*     */     
/* 374 */     try { Tuple3d t2 = (Tuple3d)t1;
/* 375 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z); }
/*     */     catch (ClassCastException e1)
/* 377 */     { return false; }
/* 378 */     catch (NullPointerException e2) { return false; }
/*     */   
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
/* 395 */     double diff = this.x - t1.x;
/* 396 */     if (Double.isNaN(diff)) return false; 
/* 397 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 399 */     diff = this.y - t1.y;
/* 400 */     if (Double.isNaN(diff)) return false; 
/* 401 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 403 */     diff = this.z - t1.z;
/* 404 */     if (Double.isNaN(diff)) return false; 
/* 405 */     return (((diff < 0.0D) ? -diff : diff) <= epsilon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max, Tuple3d t) {
/* 414 */     clamp(min, max, t);
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
/* 426 */     if (t.x > max) {
/* 427 */       this.x = max;
/* 428 */     } else if (t.x < min) {
/* 429 */       this.x = min;
/*     */     } else {
/* 431 */       this.x = t.x;
/*     */     } 
/*     */     
/* 434 */     if (t.y > max) {
/* 435 */       this.y = max;
/* 436 */     } else if (t.y < min) {
/* 437 */       this.y = min;
/*     */     } else {
/* 439 */       this.y = t.y;
/*     */     } 
/*     */     
/* 442 */     if (t.z > max) {
/* 443 */       this.z = max;
/* 444 */     } else if (t.z < min) {
/* 445 */       this.z = min;
/*     */     } else {
/* 447 */       this.z = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min, Tuple3d t) {
/* 457 */     clampMin(min, t);
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
/* 468 */     if (t.x < min) {
/* 469 */       this.x = min;
/*     */     } else {
/* 471 */       this.x = t.x;
/*     */     } 
/*     */     
/* 474 */     if (t.y < min) {
/* 475 */       this.y = min;
/*     */     } else {
/* 477 */       this.y = t.y;
/*     */     } 
/*     */     
/* 480 */     if (t.z < min) {
/* 481 */       this.z = min;
/*     */     } else {
/* 483 */       this.z = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max, Tuple3d t) {
/* 493 */     clampMax(max, t);
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
/* 504 */     if (t.x > max) {
/* 505 */       this.x = max;
/*     */     } else {
/* 507 */       this.x = t.x;
/*     */     } 
/*     */     
/* 510 */     if (t.y > max) {
/* 511 */       this.y = max;
/*     */     } else {
/* 513 */       this.y = t.y;
/*     */     } 
/*     */     
/* 516 */     if (t.z > max) {
/* 517 */       this.z = max;
/*     */     } else {
/* 519 */       this.z = t.z;
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
/*     */   public final void absolute(Tuple3d t) {
/* 532 */     this.x = Math.abs(t.x);
/* 533 */     this.y = Math.abs(t.y);
/* 534 */     this.z = Math.abs(t.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 543 */     clamp(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double min, double max) {
/* 553 */     if (this.x > max) {
/* 554 */       this.x = max;
/* 555 */     } else if (this.x < min) {
/* 556 */       this.x = min;
/*     */     } 
/*     */     
/* 559 */     if (this.y > max) {
/* 560 */       this.y = max;
/* 561 */     } else if (this.y < min) {
/* 562 */       this.y = min;
/*     */     } 
/*     */     
/* 565 */     if (this.z > max) {
/* 566 */       this.z = max;
/* 567 */     } else if (this.z < min) {
/* 568 */       this.z = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min) {
/* 578 */     clampMin(min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min) {
/* 587 */     if (this.x < min) this.x = min; 
/* 588 */     if (this.y < min) this.y = min; 
/* 589 */     if (this.z < min) this.z = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 598 */     clampMax(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 607 */     if (this.x > max) this.x = max; 
/* 608 */     if (this.y > max) this.y = max; 
/* 609 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 618 */     this.x = Math.abs(this.x);
/* 619 */     this.y = Math.abs(this.y);
/* 620 */     this.z = Math.abs(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, Tuple3d t2, float alpha) {
/* 628 */     interpolate(t1, t2, alpha);
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
/* 640 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 641 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
/* 642 */     this.z = (1.0D - alpha) * t1.z + alpha * t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, float alpha) {
/* 650 */     interpolate(t1, alpha);
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
/* 661 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 662 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
/* 663 */     this.z = (1.0D - alpha) * this.z + alpha * t1.z;
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
/* 678 */       return super.clone();
/* 679 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 681 */       throw new InternalError();
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
/* 693 */     return this.x;
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
/* 705 */     this.x = x;
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
/* 717 */     return this.y;
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
/* 729 */     this.y = y;
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
/* 740 */     return this.z;
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
/* 752 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Tuple3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */