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
/*     */     
/* 387 */     try { Tuple4f t2 = (Tuple4f)t1;
/* 388 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z && this.w == t2.w); }
/*     */     catch (NullPointerException e2)
/*     */     
/* 391 */     { return false; }
/* 392 */     catch (ClassCastException e1) { return false; }
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
/*     */   
/*     */   public boolean epsilonEquals(Tuple4f t1, float epsilon) {
/* 410 */     float diff = this.x - t1.x;
/* 411 */     if (Float.isNaN(diff)) return false; 
/* 412 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 414 */     diff = this.y - t1.y;
/* 415 */     if (Float.isNaN(diff)) return false; 
/* 416 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 418 */     diff = this.z - t1.z;
/* 419 */     if (Float.isNaN(diff)) return false; 
/* 420 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 422 */     diff = this.w - t1.w;
/* 423 */     if (Float.isNaN(diff)) return false; 
/* 424 */     return (((diff < 0.0F) ? -diff : diff) <= epsilon);
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
/* 438 */     long bits = 1L;
/* 439 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 440 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 441 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 442 */     bits = VecMathUtil.hashFloatBits(bits, this.w);
/* 443 */     return VecMathUtil.hashFinish(bits);
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
/* 456 */     if (t.x > max) {
/* 457 */       this.x = max;
/* 458 */     } else if (t.x < min) {
/* 459 */       this.x = min;
/*     */     } else {
/* 461 */       this.x = t.x;
/*     */     } 
/*     */     
/* 464 */     if (t.y > max) {
/* 465 */       this.y = max;
/* 466 */     } else if (t.y < min) {
/* 467 */       this.y = min;
/*     */     } else {
/* 469 */       this.y = t.y;
/*     */     } 
/*     */     
/* 472 */     if (t.z > max) {
/* 473 */       this.z = max;
/* 474 */     } else if (t.z < min) {
/* 475 */       this.z = min;
/*     */     } else {
/* 477 */       this.z = t.z;
/*     */     } 
/*     */     
/* 480 */     if (t.w > max) {
/* 481 */       this.w = max;
/* 482 */     } else if (t.w < min) {
/* 483 */       this.w = min;
/*     */     } else {
/* 485 */       this.w = t.w;
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
/*     */   public final void clampMin(float min, Tuple4f t) {
/* 499 */     if (t.x < min) {
/* 500 */       this.x = min;
/*     */     } else {
/* 502 */       this.x = t.x;
/*     */     } 
/*     */     
/* 505 */     if (t.y < min) {
/* 506 */       this.y = min;
/*     */     } else {
/* 508 */       this.y = t.y;
/*     */     } 
/*     */     
/* 511 */     if (t.z < min) {
/* 512 */       this.z = min;
/*     */     } else {
/* 514 */       this.z = t.z;
/*     */     } 
/*     */     
/* 517 */     if (t.w < min) {
/* 518 */       this.w = min;
/*     */     } else {
/* 520 */       this.w = t.w;
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
/*     */   public final void clampMax(float max, Tuple4f t) {
/* 535 */     if (t.x > max) {
/* 536 */       this.x = max;
/*     */     } else {
/* 538 */       this.x = t.x;
/*     */     } 
/*     */     
/* 541 */     if (t.y > max) {
/* 542 */       this.y = max;
/*     */     } else {
/* 544 */       this.y = t.y;
/*     */     } 
/*     */     
/* 547 */     if (t.z > max) {
/* 548 */       this.z = max;
/*     */     } else {
/* 550 */       this.z = t.z;
/*     */     } 
/*     */     
/* 553 */     if (t.w > max) {
/* 554 */       this.w = max;
/*     */     } else {
/* 556 */       this.w = t.z;
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
/* 569 */     this.x = Math.abs(t.x);
/* 570 */     this.y = Math.abs(t.y);
/* 571 */     this.z = Math.abs(t.z);
/* 572 */     this.w = Math.abs(t.w);
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
/* 583 */     if (this.x > max) {
/* 584 */       this.x = max;
/* 585 */     } else if (this.x < min) {
/* 586 */       this.x = min;
/*     */     } 
/*     */     
/* 589 */     if (this.y > max) {
/* 590 */       this.y = max;
/* 591 */     } else if (this.y < min) {
/* 592 */       this.y = min;
/*     */     } 
/*     */     
/* 595 */     if (this.z > max) {
/* 596 */       this.z = max;
/* 597 */     } else if (this.z < min) {
/* 598 */       this.z = min;
/*     */     } 
/*     */     
/* 601 */     if (this.w > max) {
/* 602 */       this.w = max;
/* 603 */     } else if (this.w < min) {
/* 604 */       this.w = min;
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
/* 616 */     if (this.x < min) this.x = min; 
/* 617 */     if (this.y < min) this.y = min; 
/* 618 */     if (this.z < min) this.z = min; 
/* 619 */     if (this.w < min) this.w = min;
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
/* 630 */     if (this.x > max) this.x = max; 
/* 631 */     if (this.y > max) this.y = max; 
/* 632 */     if (this.z > max) this.z = max; 
/* 633 */     if (this.w > max) this.w = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 643 */     this.x = Math.abs(this.x);
/* 644 */     this.y = Math.abs(this.y);
/* 645 */     this.z = Math.abs(this.z);
/* 646 */     this.w = Math.abs(this.w);
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
/* 659 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 660 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 661 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
/* 662 */     this.w = (1.0F - alpha) * t1.w + alpha * t2.w;
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
/* 675 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 676 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 677 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
/* 678 */     this.w = (1.0F - alpha) * this.w + alpha * t1.w;
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
/* 694 */       return super.clone();
/* 695 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 697 */       throw new InternalError();
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
/* 709 */     return this.x;
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
/* 721 */     this.x = x;
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
/* 733 */     return this.y;
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
/* 745 */     this.y = y;
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
/* 756 */     return this.z;
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
/* 768 */     this.z = z;
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
/* 780 */     return this.w;
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
/* 792 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Tuple4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */