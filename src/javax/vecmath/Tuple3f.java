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
/*     */ public abstract class Tuple3f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5019834619484343712L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public Tuple3f(float x, float y, float z) {
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
/*     */   
/*     */   public Tuple3f(float[] t) {
/*  75 */     this.x = t[0];
/*  76 */     this.y = t[1];
/*  77 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3f t1) {
/*  87 */     this.x = t1.x;
/*  88 */     this.y = t1.y;
/*  89 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3d t1) {
/*  99 */     this.x = (float)t1.x;
/* 100 */     this.y = (float)t1.y;
/* 101 */     this.z = (float)t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f() {
/* 110 */     this.x = 0.0F;
/* 111 */     this.y = 0.0F;
/* 112 */     this.z = 0.0F;
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
/* 123 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */   public final void set(float x, float y, float z) {
/* 135 */     this.x = x;
/* 136 */     this.y = y;
/* 137 */     this.z = z;
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
/* 148 */     this.x = t[0];
/* 149 */     this.y = t[1];
/* 150 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 160 */     this.x = t1.x;
/* 161 */     this.y = t1.y;
/* 162 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 172 */     this.x = (float)t1.x;
/* 173 */     this.y = (float)t1.y;
/* 174 */     this.z = (float)t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 184 */     t[0] = this.x;
/* 185 */     t[1] = this.y;
/* 186 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3f t) {
/* 196 */     t.x = this.x;
/* 197 */     t.y = this.y;
/* 198 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f t1, Tuple3f t2) {
/* 209 */     t1.x += t2.x;
/* 210 */     t1.y += t2.y;
/* 211 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f t1) {
/* 221 */     this.x += t1.x;
/* 222 */     this.y += t1.y;
/* 223 */     this.z += t1.z;
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
/*     */   public final void sub(Tuple3f t1, Tuple3f t2) {
/* 235 */     t1.x -= t2.x;
/* 236 */     t1.y -= t2.y;
/* 237 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3f t1) {
/* 248 */     this.x -= t1.x;
/* 249 */     this.y -= t1.y;
/* 250 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3f t1) {
/* 260 */     this.x = -t1.x;
/* 261 */     this.y = -t1.y;
/* 262 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 271 */     this.x = -this.x;
/* 272 */     this.y = -this.y;
/* 273 */     this.z = -this.z;
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
/*     */   public final void scale(float s, Tuple3f t1) {
/* 285 */     this.x = s * t1.x;
/* 286 */     this.y = s * t1.y;
/* 287 */     this.z = s * t1.z;
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
/* 298 */     this.x *= s;
/* 299 */     this.y *= s;
/* 300 */     this.z *= s;
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
/*     */   public final void scaleAdd(float s, Tuple3f t1, Tuple3f t2) {
/* 313 */     this.x = s * t1.x + t2.x;
/* 314 */     this.y = s * t1.y + t2.y;
/* 315 */     this.z = s * t1.z + t2.z;
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
/*     */   public final void scaleAdd(float s, Tuple3f t1) {
/* 328 */     this.x = s * this.x + t1.x;
/* 329 */     this.y = s * this.y + t1.y;
/* 330 */     this.z = s * this.z + t1.z;
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
/*     */   public boolean equals(Tuple3f t1) {
/*     */     try {
/* 344 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
/*     */     } catch (NullPointerException e2) {
/* 346 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     try {
/* 359 */       Tuple3f t2 = (Tuple3f)t1;
/* 360 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
/*     */     } catch (NullPointerException|ClassCastException e2) {
/* 362 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple3f t1, float epsilon) {
/* 379 */     float diff = this.x - t1.x;
/* 380 */     if (Float.isNaN(diff)) return false; 
/* 381 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 383 */     diff = this.y - t1.y;
/* 384 */     if (Float.isNaN(diff)) return false; 
/* 385 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 387 */     diff = this.z - t1.z;
/* 388 */     if (Float.isNaN(diff)) return false; 
/* 389 */     return (((diff < 0.0F) ? -diff : diff) <= epsilon);
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
/* 404 */     long bits = 1L;
/* 405 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 406 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 407 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 408 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public final void clamp(float min, float max, Tuple3f t) {
/* 422 */     if (t.x > max)
/* 423 */     { this.x = max; }
/* 424 */     else { this.x = Math.max(t.x, min); }
/*     */     
/* 426 */     if (t.y > max)
/* 427 */     { this.y = max; }
/* 428 */     else { this.y = Math.max(t.y, min); }
/*     */     
/* 430 */     if (t.z > max)
/* 431 */     { this.z = max; }
/* 432 */     else { this.z = Math.max(t.z, min); }
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
/*     */   public final void clampMin(float min, Tuple3f t) {
/* 445 */     this.x = Math.max(t.x, min);
/*     */     
/* 447 */     this.y = Math.max(t.y, min);
/*     */     
/* 449 */     this.z = Math.max(t.z, min);
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
/*     */   public final void clampMax(float max, Tuple3f t) {
/* 462 */     this.x = Math.min(t.x, max);
/*     */     
/* 464 */     this.y = Math.min(t.y, max);
/*     */     
/* 466 */     this.z = Math.min(t.z, max);
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
/*     */   public final void absolute(Tuple3f t) {
/* 478 */     this.x = Math.abs(t.x);
/* 479 */     this.y = Math.abs(t.y);
/* 480 */     this.z = Math.abs(t.z);
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
/* 492 */     if (this.x > max) {
/* 493 */       this.x = max;
/* 494 */     } else if (this.x < min) {
/* 495 */       this.x = min;
/*     */     } 
/*     */     
/* 498 */     if (this.y > max) {
/* 499 */       this.y = max;
/* 500 */     } else if (this.y < min) {
/* 501 */       this.y = min;
/*     */     } 
/*     */     
/* 504 */     if (this.z > max) {
/* 505 */       this.z = max;
/* 506 */     } else if (this.z < min) {
/* 507 */       this.z = min;
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
/* 519 */     if (this.x < min) this.x = min; 
/* 520 */     if (this.y < min) this.y = min; 
/* 521 */     if (this.z < min) this.z = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 532 */     if (this.x > max) this.x = max; 
/* 533 */     if (this.y > max) this.y = max; 
/* 534 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 544 */     this.x = Math.abs(this.x);
/* 545 */     this.y = Math.abs(this.y);
/* 546 */     this.z = Math.abs(this.z);
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
/*     */   public final void interpolate(Tuple3f t1, Tuple3f t2, float alpha) {
/* 560 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 561 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 562 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
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
/*     */   public final void interpolate(Tuple3f t1, float alpha) {
/* 576 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 577 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 578 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
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
/*     */   public Object clone() {
/*     */     try {
/* 595 */       return super.clone();
/* 596 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 598 */       throw new InternalError();
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
/* 611 */     return this.x;
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
/* 623 */     this.x = x;
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
/* 635 */     return this.y;
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
/* 647 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getZ() {
/* 658 */     return this.z;
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
/*     */   public final void setZ(float z) {
/* 670 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Tuple3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */