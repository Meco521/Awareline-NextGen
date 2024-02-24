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
/*     */     
/* 359 */     try { Tuple3f t2 = (Tuple3f)t1;
/* 360 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z); }
/*     */     catch (NullPointerException e2)
/* 362 */     { return false; }
/* 363 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple3f t1, float epsilon) {
/* 380 */     float diff = this.x - t1.x;
/* 381 */     if (Float.isNaN(diff)) return false; 
/* 382 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 384 */     diff = this.y - t1.y;
/* 385 */     if (Float.isNaN(diff)) return false; 
/* 386 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 388 */     diff = this.z - t1.z;
/* 389 */     if (Float.isNaN(diff)) return false; 
/* 390 */     return (((diff < 0.0F) ? -diff : diff) <= epsilon);
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
/* 405 */     long bits = 1L;
/* 406 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 407 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 408 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 409 */     return VecMathUtil.hashFinish(bits);
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
/* 423 */     if (t.x > max) {
/* 424 */       this.x = max;
/* 425 */     } else if (t.x < min) {
/* 426 */       this.x = min;
/*     */     } else {
/* 428 */       this.x = t.x;
/*     */     } 
/*     */     
/* 431 */     if (t.y > max) {
/* 432 */       this.y = max;
/* 433 */     } else if (t.y < min) {
/* 434 */       this.y = min;
/*     */     } else {
/* 436 */       this.y = t.y;
/*     */     } 
/*     */     
/* 439 */     if (t.z > max) {
/* 440 */       this.z = max;
/* 441 */     } else if (t.z < min) {
/* 442 */       this.z = min;
/*     */     } else {
/* 444 */       this.z = t.z;
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
/*     */   public final void clampMin(float min, Tuple3f t) {
/* 458 */     if (t.x < min) {
/* 459 */       this.x = min;
/*     */     } else {
/* 461 */       this.x = t.x;
/*     */     } 
/*     */     
/* 464 */     if (t.y < min) {
/* 465 */       this.y = min;
/*     */     } else {
/* 467 */       this.y = t.y;
/*     */     } 
/*     */     
/* 470 */     if (t.z < min) {
/* 471 */       this.z = min;
/*     */     } else {
/* 473 */       this.z = t.z;
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
/*     */   public final void clampMax(float max, Tuple3f t) {
/* 487 */     if (t.x > max) {
/* 488 */       this.x = max;
/*     */     } else {
/* 490 */       this.x = t.x;
/*     */     } 
/*     */     
/* 493 */     if (t.y > max) {
/* 494 */       this.y = max;
/*     */     } else {
/* 496 */       this.y = t.y;
/*     */     } 
/*     */     
/* 499 */     if (t.z > max) {
/* 500 */       this.z = max;
/*     */     } else {
/* 502 */       this.z = t.z;
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
/*     */   public final void absolute(Tuple3f t) {
/* 515 */     this.x = Math.abs(t.x);
/* 516 */     this.y = Math.abs(t.y);
/* 517 */     this.z = Math.abs(t.z);
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
/* 529 */     if (this.x > max) {
/* 530 */       this.x = max;
/* 531 */     } else if (this.x < min) {
/* 532 */       this.x = min;
/*     */     } 
/*     */     
/* 535 */     if (this.y > max) {
/* 536 */       this.y = max;
/* 537 */     } else if (this.y < min) {
/* 538 */       this.y = min;
/*     */     } 
/*     */     
/* 541 */     if (this.z > max) {
/* 542 */       this.z = max;
/* 543 */     } else if (this.z < min) {
/* 544 */       this.z = min;
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
/* 556 */     if (this.x < min) this.x = min; 
/* 557 */     if (this.y < min) this.y = min; 
/* 558 */     if (this.z < min) this.z = min;
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
/* 569 */     if (this.x > max) this.x = max; 
/* 570 */     if (this.y > max) this.y = max; 
/* 571 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3f t1, Tuple3f t2, float alpha) {
/* 597 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 598 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 599 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
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
/* 613 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 614 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 615 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
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
/* 632 */       return super.clone();
/* 633 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 635 */       throw new InternalError();
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
/* 648 */     return this.x;
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
/* 660 */     this.x = x;
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
/* 672 */     return this.y;
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
/* 684 */     this.y = y;
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
/* 695 */     return this.z;
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
/* 707 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Tuple3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */