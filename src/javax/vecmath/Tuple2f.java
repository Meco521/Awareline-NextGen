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
/*     */ public abstract class Tuple2f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 9011180388985266884L;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public Tuple2f(float x, float y) {
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
/*     */   public Tuple2f(float[] t) {
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
/*     */   public Tuple2f(Tuple2f t1) {
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
/*     */   public Tuple2f(Tuple2d t1) {
/*  90 */     this.x = (float)t1.x;
/*  91 */     this.y = (float)t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f() {
/* 100 */     this.x = 0.0F;
/* 101 */     this.y = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float x, float y) {
/* 112 */     this.x = x;
/* 113 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float[] t) {
/* 124 */     this.x = t[0];
/* 125 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f t1) {
/* 135 */     this.x = t1.x;
/* 136 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2d t1) {
/* 146 */     this.x = (float)t1.x;
/* 147 */     this.y = (float)t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 157 */     t[0] = this.x;
/* 158 */     t[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f t1, Tuple2f t2) {
/* 169 */     t1.x += t2.x;
/* 170 */     t1.y += t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f t1) {
/* 180 */     this.x += t1.x;
/* 181 */     this.y += t1.y;
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
/*     */   public final void sub(Tuple2f t1, Tuple2f t2) {
/* 193 */     t1.x -= t2.x;
/* 194 */     t1.y -= t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2f t1) {
/* 205 */     this.x -= t1.x;
/* 206 */     this.y -= t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2f t1) {
/* 216 */     this.x = -t1.x;
/* 217 */     this.y = -t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 226 */     this.x = -this.x;
/* 227 */     this.y = -this.y;
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
/*     */   public final void scale(float s, Tuple2f t1) {
/* 239 */     this.x = s * t1.x;
/* 240 */     this.y = s * t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(float s) {
/* 251 */     this.x *= s;
/* 252 */     this.y *= s;
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
/*     */   public final void scaleAdd(float s, Tuple2f t1, Tuple2f t2) {
/* 265 */     this.x = s * t1.x + t2.x;
/* 266 */     this.y = s * t1.y + t2.y;
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
/*     */   public final void scaleAdd(float s, Tuple2f t1) {
/* 278 */     this.x = s * this.x + t1.x;
/* 279 */     this.y = s * this.y + t1.y;
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
/* 294 */     long bits = 1L;
/* 295 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 296 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 297 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple2f t1) {
/*     */     try {
/* 310 */       return (this.x == t1.x && this.y == t1.y);
/*     */     } catch (NullPointerException e2) {
/* 312 */       return false;
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
/* 327 */       Tuple2f t2 = (Tuple2f)t1;
/* 328 */       return (this.x == t2.x && this.y == t2.y);
/*     */     } catch (NullPointerException|ClassCastException e2) {
/* 330 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple2f t1, float epsilon) {
/* 347 */     float diff = this.x - t1.x;
/* 348 */     if (Float.isNaN(diff)) return false; 
/* 349 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 351 */     diff = this.y - t1.y;
/* 352 */     if (Float.isNaN(diff)) return false; 
/* 353 */     return (((diff < 0.0F) ? -diff : diff) <= epsilon);
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
/* 364 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public final void clamp(float min, float max, Tuple2f t) {
/* 377 */     if (t.x > max)
/* 378 */     { this.x = max; }
/* 379 */     else { this.x = Math.max(t.x, min); }
/*     */     
/* 381 */     if (t.y > max)
/* 382 */     { this.y = max; }
/* 383 */     else { this.y = Math.max(t.y, min); }
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
/*     */   public final void clampMin(float min, Tuple2f t) {
/* 396 */     this.x = Math.max(t.x, min);
/*     */     
/* 398 */     this.y = Math.max(t.y, min);
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
/*     */   public final void clampMax(float max, Tuple2f t) {
/* 411 */     this.x = Math.min(t.x, max);
/*     */     
/* 413 */     this.y = Math.min(t.y, max);
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
/*     */   public final void absolute(Tuple2f t) {
/* 425 */     this.x = Math.abs(t.x);
/* 426 */     this.y = Math.abs(t.y);
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
/*     */   public final void clamp(float min, float max) {
/* 438 */     if (this.x > max) {
/* 439 */       this.x = max;
/* 440 */     } else if (this.x < min) {
/* 441 */       this.x = min;
/*     */     } 
/*     */     
/* 444 */     if (this.y > max) {
/* 445 */       this.y = max;
/* 446 */     } else if (this.y < min) {
/* 447 */       this.y = min;
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
/*     */   public final void clampMin(float min) {
/* 459 */     if (this.x < min) this.x = min; 
/* 460 */     if (this.y < min) this.y = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 470 */     if (this.x > max) this.x = max; 
/* 471 */     if (this.y > max) this.y = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 480 */     this.x = Math.abs(this.x);
/* 481 */     this.y = Math.abs(this.y);
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
/*     */   public final void interpolate(Tuple2f t1, Tuple2f t2, float alpha) {
/* 494 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 495 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
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
/*     */   public final void interpolate(Tuple2f t1, float alpha) {
/* 509 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 510 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
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
/* 526 */       return super.clone();
/* 527 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 529 */       throw new InternalError();
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
/*     */   public final float getX() {
/* 542 */     return this.x;
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
/*     */   public final void setX(float x) {
/* 554 */     this.x = x;
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
/*     */   public final float getY() {
/* 566 */     return this.y;
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
/*     */   public final void setY(float y) {
/* 578 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Tuple2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */