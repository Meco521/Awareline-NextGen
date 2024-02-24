/*     */ package net.minecraft.VLOUBOOS.javax.vecmath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Quat4d
/*     */   extends Tuple4d
/*     */ {
/*     */   static final long serialVersionUID = 7577479888820201099L;
/*     */   static final double EPS = 1.0E-12D;
/*     */   static final double EPS2 = 1.0E-30D;
/*     */   static final double PIO2 = 1.57079632679D;
/*     */   
/*     */   public Quat4d(double x, double y, double z, double w) {
/*  53 */     double mag = 1.0D / Math.sqrt(x * x + y * y + z * z + w * w);
/*  54 */     this.x = x * mag;
/*  55 */     this.y = y * mag;
/*  56 */     this.z = z * mag;
/*  57 */     this.w = w * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(double[] q) {
/*  68 */     double mag = 1.0D / Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
/*  69 */     this.x = q[0] * mag;
/*  70 */     this.y = q[1] * mag;
/*  71 */     this.z = q[2] * mag;
/*  72 */     this.w = q[3] * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4d q1) {
/*  82 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4f q1) {
/*  91 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Tuple4f t1) {
/* 102 */     double mag = 1.0D / Math.sqrt((t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w));
/* 103 */     this.x = t1.x * mag;
/* 104 */     this.y = t1.y * mag;
/* 105 */     this.z = t1.z * mag;
/* 106 */     this.w = t1.w * mag;
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
/*     */   public Quat4d(Tuple4d t1) {
/* 118 */     double mag = 1.0D / Math.sqrt(t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w);
/* 119 */     this.x = t1.x * mag;
/* 120 */     this.y = t1.y * mag;
/* 121 */     this.z = t1.z * mag;
/* 122 */     this.w = t1.w * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate(Quat4d q1) {
/* 140 */     this.x = -q1.x;
/* 141 */     this.y = -q1.y;
/* 142 */     this.z = -q1.z;
/* 143 */     this.w = q1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate() {
/* 152 */     this.x = -this.x;
/* 153 */     this.y = -this.y;
/* 154 */     this.z = -this.z;
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
/*     */   public final void mul(Quat4d q1, Quat4d q2) {
/* 167 */     if (this != q1 && this != q2) {
/* 168 */       this.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 169 */       this.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 170 */       this.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 171 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/*     */     }
/*     */     else {
/*     */       
/* 175 */       double w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 176 */       double x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 177 */       double y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 178 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/* 179 */       this.w = w;
/* 180 */       this.x = x;
/* 181 */       this.y = y;
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
/*     */   public final void mul(Quat4d q1) {
/* 195 */     double w = this.w * q1.w - this.x * q1.x - this.y * q1.y - this.z * q1.z;
/* 196 */     double x = this.w * q1.x + q1.w * this.x + this.y * q1.z - this.z * q1.y;
/* 197 */     double y = this.w * q1.y + q1.w * this.y - this.x * q1.z + this.z * q1.x;
/* 198 */     this.z = this.w * q1.z + q1.w * this.z + this.x * q1.y - this.y * q1.x;
/* 199 */     this.w = w;
/* 200 */     this.x = x;
/* 201 */     this.y = y;
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
/*     */   public final void mulInverse(Quat4d q1, Quat4d q2) {
/* 214 */     Quat4d tempQuat = new Quat4d(q2);
/*     */     
/* 216 */     tempQuat.inverse();
/* 217 */     mul(q1, tempQuat);
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
/*     */   public final void mulInverse(Quat4d q1) {
/* 229 */     Quat4d tempQuat = new Quat4d(q1);
/*     */     
/* 231 */     tempQuat.inverse();
/* 232 */     mul(tempQuat);
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
/*     */   public final void inverse(Quat4d q1) {
/* 244 */     double norm = 1.0D / (q1.w * q1.w + q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/* 245 */     this.w = norm * q1.w;
/* 246 */     this.x = -norm * q1.x;
/* 247 */     this.y = -norm * q1.y;
/* 248 */     this.z = -norm * q1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inverse() {
/* 258 */     double norm = 1.0D / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
/* 259 */     this.w *= norm;
/* 260 */     this.x *= -norm;
/* 261 */     this.y *= -norm;
/* 262 */     this.z *= -norm;
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
/*     */   public final void normalize(Quat4d q1) {
/* 275 */     double norm = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z + q1.w * q1.w;
/*     */     
/* 277 */     if (norm > 0.0D) {
/* 278 */       norm = 1.0D / Math.sqrt(norm);
/* 279 */       this.x = norm * q1.x;
/* 280 */       this.y = norm * q1.y;
/* 281 */       this.z = norm * q1.z;
/* 282 */       this.w = norm * q1.w;
/*     */     } else {
/* 284 */       this.x = 0.0D;
/* 285 */       this.y = 0.0D;
/* 286 */       this.z = 0.0D;
/* 287 */       this.w = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 298 */     double norm = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 300 */     if (norm > 0.0D) {
/* 301 */       norm = 1.0D / Math.sqrt(norm);
/* 302 */       this.x *= norm;
/* 303 */       this.y *= norm;
/* 304 */       this.z *= norm;
/* 305 */       this.w *= norm;
/*     */     } else {
/* 307 */       this.x = 0.0D;
/* 308 */       this.y = 0.0D;
/* 309 */       this.z = 0.0D;
/* 310 */       this.w = 0.0D;
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
/*     */   public final void set(Matrix4f m1) {
/* 322 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 324 */     if (ww >= 0.0D) {
/* 325 */       if (ww >= 1.0E-30D) {
/* 326 */         this.w = Math.sqrt(ww);
/* 327 */         ww = 0.25D / this.w;
/* 328 */         this.x = (m1.m21 - m1.m12) * ww;
/* 329 */         this.y = (m1.m02 - m1.m20) * ww;
/* 330 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 334 */       this.w = 0.0D;
/* 335 */       this.x = 0.0D;
/* 336 */       this.y = 0.0D;
/* 337 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 341 */     this.w = 0.0D;
/* 342 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 343 */     if (ww >= 0.0D) {
/* 344 */       if (ww >= 1.0E-30D) {
/* 345 */         this.x = Math.sqrt(ww);
/* 346 */         ww = 1.0D / 2.0D * this.x;
/* 347 */         this.y = m1.m10 * ww;
/* 348 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 352 */       this.x = 0.0D;
/* 353 */       this.y = 0.0D;
/* 354 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 358 */     this.x = 0.0D;
/* 359 */     ww = 0.5D * (1.0D - m1.m22);
/* 360 */     if (ww >= 1.0E-30D) {
/* 361 */       this.y = Math.sqrt(ww);
/* 362 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 366 */     this.y = 0.0D;
/* 367 */     this.z = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix4d m1) {
/* 378 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 380 */     if (ww >= 0.0D) {
/* 381 */       if (ww >= 1.0E-30D) {
/* 382 */         this.w = Math.sqrt(ww);
/* 383 */         ww = 0.25D / this.w;
/* 384 */         this.x = (m1.m21 - m1.m12) * ww;
/* 385 */         this.y = (m1.m02 - m1.m20) * ww;
/* 386 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 390 */       this.w = 0.0D;
/* 391 */       this.x = 0.0D;
/* 392 */       this.y = 0.0D;
/* 393 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 397 */     this.w = 0.0D;
/* 398 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 399 */     if (ww >= 0.0D) {
/* 400 */       if (ww >= 1.0E-30D) {
/* 401 */         this.x = Math.sqrt(ww);
/* 402 */         ww = 0.5D / this.x;
/* 403 */         this.y = m1.m10 * ww;
/* 404 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 408 */       this.x = 0.0D;
/* 409 */       this.y = 0.0D;
/* 410 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 414 */     this.x = 0.0D;
/* 415 */     ww = 0.5D * (1.0D - m1.m22);
/* 416 */     if (ww >= 1.0E-30D) {
/* 417 */       this.y = Math.sqrt(ww);
/* 418 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 422 */     this.y = 0.0D;
/* 423 */     this.z = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3f m1) {
/* 434 */     double ww = 0.25D * ((m1.m00 + m1.m11 + m1.m22) + 1.0D);
/*     */     
/* 436 */     if (ww >= 0.0D) {
/* 437 */       if (ww >= 1.0E-30D) {
/* 438 */         this.w = Math.sqrt(ww);
/* 439 */         ww = 0.25D / this.w;
/* 440 */         this.x = (m1.m21 - m1.m12) * ww;
/* 441 */         this.y = (m1.m02 - m1.m20) * ww;
/* 442 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 446 */       this.w = 0.0D;
/* 447 */       this.x = 0.0D;
/* 448 */       this.y = 0.0D;
/* 449 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 453 */     this.w = 0.0D;
/* 454 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 455 */     if (ww >= 0.0D) {
/* 456 */       if (ww >= 1.0E-30D) {
/* 457 */         this.x = Math.sqrt(ww);
/* 458 */         ww = 0.5D / this.x;
/* 459 */         this.y = m1.m10 * ww;
/* 460 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 464 */       this.x = 0.0D;
/* 465 */       this.y = 0.0D;
/* 466 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 470 */     this.x = 0.0D;
/* 471 */     ww = 0.5D * (1.0D - m1.m22);
/* 472 */     if (ww >= 1.0E-30D) {
/* 473 */       this.y = Math.sqrt(ww);
/* 474 */       this.z = m1.m21 / 2.0D * this.y;
/*     */     } 
/*     */     
/* 477 */     this.y = 0.0D;
/* 478 */     this.z = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3d m1) {
/* 489 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + 1.0D);
/*     */     
/* 491 */     if (ww >= 0.0D) {
/* 492 */       if (ww >= 1.0E-30D) {
/* 493 */         this.w = Math.sqrt(ww);
/* 494 */         ww = 0.25D / this.w;
/* 495 */         this.x = (m1.m21 - m1.m12) * ww;
/* 496 */         this.y = (m1.m02 - m1.m20) * ww;
/* 497 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 501 */       this.w = 0.0D;
/* 502 */       this.x = 0.0D;
/* 503 */       this.y = 0.0D;
/* 504 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 508 */     this.w = 0.0D;
/* 509 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 510 */     if (ww >= 0.0D) {
/* 511 */       if (ww >= 1.0E-30D) {
/* 512 */         this.x = Math.sqrt(ww);
/* 513 */         ww = 0.5D / this.x;
/* 514 */         this.y = m1.m10 * ww;
/* 515 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 519 */       this.x = 0.0D;
/* 520 */       this.y = 0.0D;
/* 521 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 525 */     this.x = 0.0D;
/* 526 */     ww = 0.5D * (1.0D - m1.m22);
/* 527 */     if (ww >= 1.0E-30D) {
/* 528 */       this.y = Math.sqrt(ww);
/* 529 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 533 */     this.y = 0.0D;
/* 534 */     this.z = 1.0D;
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
/*     */   public final void set(AxisAngle4f a) {
/* 548 */     double amag = Math.sqrt((a.x * a.x + a.y * a.y + a.z * a.z));
/* 549 */     if (amag < 1.0E-12D) {
/* 550 */       this.w = 0.0D;
/* 551 */       this.x = 0.0D;
/* 552 */       this.y = 0.0D;
/* 553 */       this.z = 0.0D;
/*     */     } else {
/* 555 */       double mag = Math.sin(a.angle / 2.0D);
/* 556 */       amag = 1.0D / amag;
/* 557 */       this.w = Math.cos(a.angle / 2.0D);
/* 558 */       this.x = a.x * amag * mag;
/* 559 */       this.y = a.y * amag * mag;
/* 560 */       this.z = a.z * amag * mag;
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
/*     */   public final void set(AxisAngle4d a) {
/* 575 */     double amag = Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
/* 576 */     if (amag < 1.0E-12D) {
/* 577 */       this.w = 0.0D;
/* 578 */       this.x = 0.0D;
/* 579 */       this.y = 0.0D;
/* 580 */       this.z = 0.0D;
/*     */     } else {
/* 582 */       amag = 1.0D / amag;
/* 583 */       double mag = Math.sin(a.angle / 2.0D);
/* 584 */       this.w = Math.cos(a.angle / 2.0D);
/* 585 */       this.x = a.x * amag * mag;
/* 586 */       this.y = a.y * amag * mag;
/* 587 */       this.z = a.z * amag * mag;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4d q1, double alpha) {
/* 609 */     double s1, s2, dot = this.x * q1.x + this.y * q1.y + this.z * q1.z + this.w * q1.w;
/*     */     
/* 611 */     if (dot < 0.0D) {
/*     */       
/* 613 */       q1.x = -q1.x;
/* 614 */       q1.y = -q1.y;
/* 615 */       q1.z = -q1.z;
/* 616 */       q1.w = -q1.w;
/* 617 */       dot = -dot;
/*     */     } 
/*     */     
/* 620 */     if (1.0D - dot > 1.0E-12D) {
/* 621 */       double om = Math.acos(dot);
/* 622 */       double sinom = Math.sin(om);
/* 623 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 624 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 626 */       s1 = 1.0D - alpha;
/* 627 */       s2 = alpha;
/*     */     } 
/*     */     
/* 630 */     this.w = s1 * this.w + s2 * q1.w;
/* 631 */     this.x = s1 * this.x + s2 * q1.x;
/* 632 */     this.y = s1 * this.y + s2 * q1.y;
/* 633 */     this.z = s1 * this.z + s2 * q1.z;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4d q1, Quat4d q2, double alpha) {
/* 653 */     double s1, s2, dot = q2.x * q1.x + q2.y * q1.y + q2.z * q1.z + q2.w * q1.w;
/*     */     
/* 655 */     if (dot < 0.0D) {
/*     */       
/* 657 */       q1.x = -q1.x;
/* 658 */       q1.y = -q1.y;
/* 659 */       q1.z = -q1.z;
/* 660 */       q1.w = -q1.w;
/* 661 */       dot = -dot;
/*     */     } 
/*     */     
/* 664 */     if (1.0D - dot > 1.0E-12D) {
/* 665 */       double om = Math.acos(dot);
/* 666 */       double sinom = Math.sin(om);
/* 667 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 668 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 670 */       s1 = 1.0D - alpha;
/* 671 */       s2 = alpha;
/*     */     } 
/* 673 */     this.w = s1 * q1.w + s2 * q2.w;
/* 674 */     this.x = s1 * q1.x + s2 * q2.x;
/* 675 */     this.y = s1 * q1.y + s2 * q2.y;
/* 676 */     this.z = s1 * q1.z + s2 * q2.z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Quat4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */