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
/*     */     
/* 327 */     try { Tuple2f t2 = (Tuple2f)t1;
/* 328 */       return (this.x == t2.x && this.y == t2.y); }
/*     */     catch (NullPointerException e2)
/* 330 */     { return false; }
/* 331 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple2f t1, float epsilon) {
/* 348 */     float diff = this.x - t1.x;
/* 349 */     if (Float.isNaN(diff)) return false; 
/* 350 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 352 */     diff = this.y - t1.y;
/* 353 */     if (Float.isNaN(diff)) return false; 
/* 354 */     return (((diff < 0.0F) ? -diff : diff) <= epsilon);
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
/* 365 */     return "(" + this.x + ", " + this.y + ")";
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
/* 378 */     if (t.x > max) {
/* 379 */       this.x = max;
/* 380 */     } else if (t.x < min) {
/* 381 */       this.x = min;
/*     */     } else {
/* 383 */       this.x = t.x;
/*     */     } 
/*     */     
/* 386 */     if (t.y > max) {
/* 387 */       this.y = max;
/* 388 */     } else if (t.y < min) {
/* 389 */       this.y = min;
/*     */     } else {
/* 391 */       this.y = t.y;
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
/*     */   public final void clampMin(float min, Tuple2f t) {
/* 405 */     if (t.x < min) {
/* 406 */       this.x = min;
/*     */     } else {
/* 408 */       this.x = t.x;
/*     */     } 
/*     */     
/* 411 */     if (t.y < min) {
/* 412 */       this.y = min;
/*     */     } else {
/* 414 */       this.y = t.y;
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
/*     */   public final void clampMax(float max, Tuple2f t) {
/* 428 */     if (t.x > max) {
/* 429 */       this.x = max;
/*     */     } else {
/* 431 */       this.x = t.x;
/*     */     } 
/*     */     
/* 434 */     if (t.y > max) {
/* 435 */       this.y = max;
/*     */     } else {
/* 437 */       this.y = t.y;
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
/*     */   public final void absolute(Tuple2f t) {
/* 450 */     this.x = Math.abs(t.x);
/* 451 */     this.y = Math.abs(t.y);
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
/* 463 */     if (this.x > max) {
/* 464 */       this.x = max;
/* 465 */     } else if (this.x < min) {
/* 466 */       this.x = min;
/*     */     } 
/*     */     
/* 469 */     if (this.y > max) {
/* 470 */       this.y = max;
/* 471 */     } else if (this.y < min) {
/* 472 */       this.y = min;
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
/* 484 */     if (this.x < min) this.x = min; 
/* 485 */     if (this.y < min) this.y = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 495 */     if (this.x > max) this.x = max; 
/* 496 */     if (this.y > max) this.y = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 505 */     this.x = Math.abs(this.x);
/* 506 */     this.y = Math.abs(this.y);
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
/* 519 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 520 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
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
/* 534 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 535 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
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
/* 551 */       return super.clone();
/* 552 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 554 */       throw new InternalError();
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
/* 567 */     return this.x;
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
/* 579 */     this.x = x;
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
/* 591 */     return this.y;
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
/* 603 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Tuple2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */