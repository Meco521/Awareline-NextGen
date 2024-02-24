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
/*     */ public abstract class Tuple2d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 6205762482756093838L;
/*     */   public double x;
/*     */   public double y;
/*     */   
/*     */   public Tuple2d(double x, double y) {
/*  57 */     this.x = x;
/*  58 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(double[] t) {
/*  68 */     this.x = t[0];
/*  69 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(Tuple2d t1) {
/*  79 */     this.x = t1.x;
/*  80 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(Tuple2f t1) {
/*  90 */     this.x = t1.x;
/*  91 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d() {
/*  99 */     this.x = 0.0D;
/* 100 */     this.y = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double x, double y) {
/* 111 */     this.x = x;
/* 112 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 123 */     this.x = t[0];
/* 124 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2d t1) {
/* 134 */     this.x = t1.x;
/* 135 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f t1) {
/* 145 */     this.x = t1.x;
/* 146 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 155 */     t[0] = this.x;
/* 156 */     t[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2d t1, Tuple2d t2) {
/* 167 */     t1.x += t2.x;
/* 168 */     t1.y += t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2d t1) {
/* 178 */     this.x += t1.x;
/* 179 */     this.y += t1.y;
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
/*     */   public final void sub(Tuple2d t1, Tuple2d t2) {
/* 191 */     t1.x -= t2.x;
/* 192 */     t1.y -= t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2d t1) {
/* 203 */     this.x -= t1.x;
/* 204 */     this.y -= t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2d t1) {
/* 214 */     this.x = -t1.x;
/* 215 */     this.y = -t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 224 */     this.x = -this.x;
/* 225 */     this.y = -this.y;
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
/*     */   public final void scale(double s, Tuple2d t1) {
/* 237 */     this.x = s * t1.x;
/* 238 */     this.y = s * t1.y;
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
/* 249 */     this.x *= s;
/* 250 */     this.y *= s;
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
/*     */   public final void scaleAdd(double s, Tuple2d t1, Tuple2d t2) {
/* 263 */     this.x = s * t1.x + t2.x;
/* 264 */     this.y = s * t1.y + t2.y;
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
/*     */   public final void scaleAdd(double s, Tuple2d t1) {
/* 276 */     this.x = s * this.x + t1.x;
/* 277 */     this.y = s * this.y + t1.y;
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
/* 292 */     long bits = 1L;
/* 293 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 294 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 295 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple2d t1) {
/*     */     try {
/* 308 */       return (this.x == t1.x && this.y == t1.y);
/*     */     } catch (NullPointerException e2) {
/* 310 */       return false;
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
/*     */     
/* 325 */     try { Tuple2d t2 = (Tuple2d)t1;
/* 326 */       return (this.x == t2.x && this.y == t2.y); }
/*     */     catch (NullPointerException e2)
/* 328 */     { return false; }
/* 329 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple2d t1, double epsilon) {
/* 346 */     double diff = this.x - t1.x;
/* 347 */     if (Double.isNaN(diff)) return false; 
/* 348 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 350 */     diff = this.y - t1.y;
/* 351 */     if (Double.isNaN(diff)) return false; 
/* 352 */     return (((diff < 0.0D) ? -diff : diff) <= epsilon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 363 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public final void clamp(double min, double max, Tuple2d t) {
/* 376 */     if (t.x > max) {
/* 377 */       this.x = max;
/* 378 */     } else if (t.x < min) {
/* 379 */       this.x = min;
/*     */     } else {
/* 381 */       this.x = t.x;
/*     */     } 
/*     */     
/* 384 */     if (t.y > max) {
/* 385 */       this.y = max;
/* 386 */     } else if (t.y < min) {
/* 387 */       this.y = min;
/*     */     } else {
/* 389 */       this.y = t.y;
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
/*     */   public final void clampMin(double min, Tuple2d t) {
/* 403 */     if (t.x < min) {
/* 404 */       this.x = min;
/*     */     } else {
/* 406 */       this.x = t.x;
/*     */     } 
/*     */     
/* 409 */     if (t.y < min) {
/* 410 */       this.y = min;
/*     */     } else {
/* 412 */       this.y = t.y;
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
/*     */   public final void clampMax(double max, Tuple2d t) {
/* 426 */     if (t.x > max) {
/* 427 */       this.x = max;
/*     */     } else {
/* 429 */       this.x = t.x;
/*     */     } 
/*     */     
/* 432 */     if (t.y > max) {
/* 433 */       this.y = max;
/*     */     } else {
/* 435 */       this.y = t.y;
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
/*     */   public final void absolute(Tuple2d t) {
/* 448 */     this.x = Math.abs(t.x);
/* 449 */     this.y = Math.abs(t.y);
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
/*     */   public final void clamp(double min, double max) {
/* 461 */     if (this.x > max) {
/* 462 */       this.x = max;
/* 463 */     } else if (this.x < min) {
/* 464 */       this.x = min;
/*     */     } 
/*     */     
/* 467 */     if (this.y > max) {
/* 468 */       this.y = max;
/* 469 */     } else if (this.y < min) {
/* 470 */       this.y = min;
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
/*     */   public final void clampMin(double min) {
/* 482 */     if (this.x < min) this.x = min; 
/* 483 */     if (this.y < min) this.y = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 493 */     if (this.x > max) this.x = max; 
/* 494 */     if (this.y > max) this.y = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 503 */     this.x = Math.abs(this.x);
/* 504 */     this.y = Math.abs(this.y);
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
/*     */   public final void interpolate(Tuple2d t1, Tuple2d t2, double alpha) {
/* 517 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 518 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
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
/*     */   public final void interpolate(Tuple2d t1, double alpha) {
/* 530 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 531 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
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
/*     */   public Object clone() {
/*     */     try {
/* 547 */       return super.clone();
/* 548 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 550 */       throw new InternalError();
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
/*     */   public final double getX() {
/* 563 */     return this.x;
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
/* 575 */     this.x = x;
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
/* 587 */     return this.y;
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
/* 599 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Tuple2d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */