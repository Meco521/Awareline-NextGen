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
/*     */ public abstract class Tuple4f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 7068460319248845763L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float w;
/*     */   
/*     */   public Tuple4f(float x, float y, float z, float w) {
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
/*     */   public Tuple4f(float[] t) {
/*  82 */     this.x = t[0];
/*  83 */     this.y = t[1];
/*  84 */     this.z = t[2];
/*  85 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4f t1) {
/*  95 */     this.x = t1.x;
/*  96 */     this.y = t1.y;
/*  97 */     this.z = t1.z;
/*  98 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4d t1) {
/* 108 */     this.x = (float)t1.x;
/* 109 */     this.y = (float)t1.y;
/* 110 */     this.z = (float)t1.z;
/* 111 */     this.w = (float)t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f() {
/* 120 */     this.x = 0.0F;
/* 121 */     this.y = 0.0F;
/* 122 */     this.z = 0.0F;
/* 123 */     this.w = 0.0F;
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
/*     */   public final void set(float x, float y, float z, float w) {
/* 136 */     this.x = x;
/* 137 */     this.y = y;
/* 138 */     this.z = z;
/* 139 */     this.w = w;
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
/*     */   public final void set(Tuple4f t1) {
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
/*     */   public final void set(Tuple4d t1) {
/* 176 */     this.x = (float)t1.x;
/* 177 */     this.y = (float)t1.y;
/* 178 */     this.z = (float)t1.z;
/* 179 */     this.w = (float)t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 189 */     t[0] = this.x;
/* 190 */     t[1] = this.y;
/* 191 */     t[2] = this.z;
/* 192 */     t[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4f t) {
/* 202 */     t.x = this.x;
/* 203 */     t.y = this.y;
/* 204 */     t.z = this.z;
/* 205 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f t1, Tuple4f t2) {
/* 216 */     t1.x += t2.x;
/* 217 */     t1.y += t2.y;
/* 218 */     t1.z += t2.z;
/* 219 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f t1) {
/* 229 */     this.x += t1.x;
/* 230 */     this.y += t1.y;
/* 231 */     this.z += t1.z;
/* 232 */     this.w += t1.w;
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
/*     */   public final void sub(Tuple4f t1, Tuple4f t2) {
/* 244 */     t1.x -= t2.x;
/* 245 */     t1.y -= t2.y;
/* 246 */     t1.z -= t2.z;
/* 247 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4f t1) {
/* 258 */     this.x -= t1.x;
/* 259 */     this.y -= t1.y;
/* 260 */     this.z -= t1.z;
/* 261 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4f t1) {
/* 271 */     this.x = -t1.x;
/* 272 */     this.y = -t1.y;
/* 273 */     this.z = -t1.z;
/* 274 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 283 */     this.x = -this.x;
/* 284 */     this.y = -this.y;
/* 285 */     this.z = -this.z;
/* 286 */     this.w = -this.w;
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
/*     */   public final void scale(float s, Tuple4f t1) {
/* 298 */     this.x = s * t1.x;
/* 299 */     this.y = s * t1.y;
/* 300 */     this.z = s * t1.z;
/* 301 */     this.w = s * t1.w;
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
/* 312 */     this.x *= s;
/* 313 */     this.y *= s;
/* 314 */     this.z *= s;
/* 315 */     this.w *= s;
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
/*     */   public final void scaleAdd(float s, Tuple4f t1, Tuple4f t2) {
/* 328 */     this.x = s * t1.x + t2.x;
/* 329 */     this.y = s * t1.y + t2.y;
/* 330 */     this.z = s * t1.z + t2.z;
/* 331 */     this.w = s * t1.w + t2.w;
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
/*     */   public final void scaleAdd(float s, Tuple4f t1) {
/* 343 */     this.x = s * this.x + t1.x;
/* 344 */     this.y = s * this.y + t1.y;
/* 345 */     this.z = s * this.z + t1.z;
/* 346 */     this.w = s * this.w + t1.w;
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
/* 358 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Tuple4f t1) {
/*     */     try {
/* 370 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z && this.w == t1.w);
/*     */     } catch (NullPointerException e2) {
/*     */       
/* 373 */       return false;
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
/* 387 */       Tuple4f t2 = (Tuple4f)t1;
/* 388 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z && this.w == t2.w);
/*     */     } catch (NullPointerException|ClassCastException e2) {
/*     */       
/* 391 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple4f t1, float epsilon) {
/* 409 */     float diff = this.x - t1.x;
/* 410 */     if (Float.isNaN(diff)) return false; 
/* 411 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 413 */     diff = this.y - t1.y;
/* 414 */     if (Float.isNaN(diff)) return false; 
/* 415 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 417 */     diff = this.z - t1.z;
/* 418 */     if (Float.isNaN(diff)) return false; 
/* 419 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 421 */     diff = this.w - t1.w;
/* 422 */     if (Float.isNaN(diff)) return false; 
/* 423 */     return (((diff < 0.0F) ? -diff : diff) <= epsilon);
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
/* 437 */     long bits = 1L;
/* 438 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 439 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 440 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 441 */     bits = VecMathUtil.hashFloatBits(bits, this.w);
/* 442 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public final void clamp(float min, float max, Tuple4f t) {
/* 455 */     if (t.x > max)
/* 456 */     { this.x = max; }
/* 457 */     else { this.x = Math.max(t.x, min); }
/*     */     
/* 459 */     if (t.y > max)
/* 460 */     { this.y = max; }
/* 461 */     else { this.y = Math.max(t.y, min); }
/*     */     
/* 463 */     if (t.z > max)
/* 464 */     { this.z = max; }
/* 465 */     else { this.z = Math.max(t.z, min); }
/*     */     
/* 467 */     if (t.w > max)
/* 468 */     { this.w = max; }
/* 469 */     else { this.w = Math.max(t.w, min); }
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
/*     */   public final void clampMin(float min, Tuple4f t) {
/* 482 */     this.x = Math.max(t.x, min);
/*     */     
/* 484 */     this.y = Math.max(t.y, min);
/*     */     
/* 486 */     this.z = Math.max(t.z, min);
/*     */     
/* 488 */     this.w = Math.max(t.w, min);
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
/*     */   public final void clampMax(float max, Tuple4f t) {
/* 502 */     this.x = Math.min(t.x, max);
/*     */     
/* 504 */     this.y = Math.min(t.y, max);
/*     */     
/* 506 */     this.z = Math.min(t.z, max);
/*     */     
/* 508 */     if (t.w > max) {
/* 509 */       this.w = max;
/*     */     } else {
/* 511 */       this.w = t.z;
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
/*     */   public final void absolute(Tuple4f t) {
/* 524 */     this.x = Math.abs(t.x);
/* 525 */     this.y = Math.abs(t.y);
/* 526 */     this.z = Math.abs(t.z);
/* 527 */     this.w = Math.abs(t.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 538 */     if (this.x > max) {
/* 539 */       this.x = max;
/* 540 */     } else if (this.x < min) {
/* 541 */       this.x = min;
/*     */     } 
/*     */     
/* 544 */     if (this.y > max) {
/* 545 */       this.y = max;
/* 546 */     } else if (this.y < min) {
/* 547 */       this.y = min;
/*     */     } 
/*     */     
/* 550 */     if (this.z > max) {
/* 551 */       this.z = max;
/* 552 */     } else if (this.z < min) {
/* 553 */       this.z = min;
/*     */     } 
/*     */     
/* 556 */     if (this.w > max) {
/* 557 */       this.w = max;
/* 558 */     } else if (this.w < min) {
/* 559 */       this.w = min;
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
/* 571 */     if (this.x < min) this.x = min; 
/* 572 */     if (this.y < min) this.y = min; 
/* 573 */     if (this.z < min) this.z = min; 
/* 574 */     if (this.w < min) this.w = min;
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
/* 585 */     if (this.x > max) this.x = max; 
/* 586 */     if (this.y > max) this.y = max; 
/* 587 */     if (this.z > max) this.z = max; 
/* 588 */     if (this.w > max) this.w = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 598 */     this.x = Math.abs(this.x);
/* 599 */     this.y = Math.abs(this.y);
/* 600 */     this.z = Math.abs(this.z);
/* 601 */     this.w = Math.abs(this.w);
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
/*     */   public void interpolate(Tuple4f t1, Tuple4f t2, float alpha) {
/* 614 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 615 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 616 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
/* 617 */     this.w = (1.0F - alpha) * t1.w + alpha * t2.w;
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
/*     */   public void interpolate(Tuple4f t1, float alpha) {
/* 630 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 631 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 632 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
/* 633 */     this.w = (1.0F - alpha) * this.w + alpha * t1.w;
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
/* 649 */       return super.clone();
/* 650 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 652 */       throw new InternalError();
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
/*     */   public final float getX() {
/* 664 */     return this.x;
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
/* 676 */     this.x = x;
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
/* 688 */     return this.y;
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
/* 700 */     this.y = y;
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
/* 711 */     return this.z;
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
/* 723 */     this.z = z;
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
/*     */   public final float getW() {
/* 735 */     return this.w;
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
/*     */   public final void setW(float w) {
/* 747 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Tuple4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */