/*     */ package javax.vecmath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   
/*     */   public Quat4d(double x, double y, double z, double w) {
/*  55 */     double mag = 1.0D / Math.sqrt(x * x + y * y + z * z + w * w);
/*  56 */     this.x = x * mag;
/*  57 */     this.y = y * mag;
/*  58 */     this.z = z * mag;
/*  59 */     this.w = w * mag;
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
/*  70 */     double mag = 1.0D / Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
/*  71 */     this.x = q[0] * mag;
/*  72 */     this.y = q[1] * mag;
/*  73 */     this.z = q[2] * mag;
/*  74 */     this.w = q[3] * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4d q1) {
/*  84 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4f q1) {
/*  93 */     super(q1);
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
/* 104 */     double mag = 1.0D / Math.sqrt((t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w));
/* 105 */     this.x = t1.x * mag;
/* 106 */     this.y = t1.y * mag;
/* 107 */     this.z = t1.z * mag;
/* 108 */     this.w = t1.w * mag;
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
/* 120 */     double mag = 1.0D / Math.sqrt(t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w);
/* 121 */     this.x = t1.x * mag;
/* 122 */     this.y = t1.y * mag;
/* 123 */     this.z = t1.z * mag;
/* 124 */     this.w = t1.w * mag;
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
/*     */   public Quat4d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mul(Quat4d q1, Quat4d q2) {
/* 145 */     if (this != q1 && this != q2) {
/* 146 */       this.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 147 */       this.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 148 */       this.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 149 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/*     */     }
/*     */     else {
/*     */       
/* 153 */       double w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 154 */       double x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 155 */       double y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 156 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/* 157 */       this.w = w;
/* 158 */       this.x = x;
/* 159 */       this.y = y;
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
/* 173 */     double w = this.w * q1.w - this.x * q1.x - this.y * q1.y - this.z * q1.z;
/* 174 */     double x = this.w * q1.x + q1.w * this.x + this.y * q1.z - this.z * q1.y;
/* 175 */     double y = this.w * q1.y + q1.w * this.y - this.x * q1.z + this.z * q1.x;
/* 176 */     this.z = this.w * q1.z + q1.w * this.z + this.x * q1.y - this.y * q1.x;
/* 177 */     this.w = w;
/* 178 */     this.x = x;
/* 179 */     this.y = y;
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
/* 192 */     Quat4d tempQuat = new Quat4d(q2);
/*     */     
/* 194 */     tempQuat.inverse();
/* 195 */     mul(q1, tempQuat);
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
/*     */   public final void mulInverse(Quat4d q1) {
/* 208 */     Quat4d tempQuat = new Quat4d(q1);
/*     */     
/* 210 */     tempQuat.inverse();
/* 211 */     mul(tempQuat);
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
/* 223 */     double norm = 1.0D / (q1.w * q1.w + q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/* 224 */     this.w = norm * q1.w;
/* 225 */     this.x = -norm * q1.x;
/* 226 */     this.y = -norm * q1.y;
/* 227 */     this.z = -norm * q1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inverse() {
/* 238 */     double norm = 1.0D / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
/* 239 */     this.w *= norm;
/* 240 */     this.x *= -norm;
/* 241 */     this.y *= -norm;
/* 242 */     this.z *= -norm;
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
/* 255 */     double norm = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z + q1.w * q1.w;
/*     */     
/* 257 */     if (norm > 0.0D) {
/* 258 */       norm = 1.0D / Math.sqrt(norm);
/* 259 */       this.x = norm * q1.x;
/* 260 */       this.y = norm * q1.y;
/* 261 */       this.z = norm * q1.z;
/* 262 */       this.w = norm * q1.w;
/*     */     } else {
/* 264 */       this.x = 0.0D;
/* 265 */       this.y = 0.0D;
/* 266 */       this.z = 0.0D;
/* 267 */       this.w = 0.0D;
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
/*     */   public final void normalize() {
/* 279 */     double norm = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 281 */     if (norm > 0.0D) {
/* 282 */       norm = 1.0D / Math.sqrt(norm);
/* 283 */       this.x *= norm;
/* 284 */       this.y *= norm;
/* 285 */       this.z *= norm;
/* 286 */       this.w *= norm;
/*     */     } else {
/* 288 */       this.x = 0.0D;
/* 289 */       this.y = 0.0D;
/* 290 */       this.z = 0.0D;
/* 291 */       this.w = 0.0D;
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
/* 303 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 305 */     if (ww >= 0.0D) {
/* 306 */       if (ww >= 1.0E-30D) {
/* 307 */         this.w = Math.sqrt(ww);
/* 308 */         ww = 0.25D / this.w;
/* 309 */         this.x = (m1.m21 - m1.m12) * ww;
/* 310 */         this.y = (m1.m02 - m1.m20) * ww;
/* 311 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 315 */       this.w = 0.0D;
/* 316 */       this.x = 0.0D;
/* 317 */       this.y = 0.0D;
/* 318 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 322 */     this.w = 0.0D;
/* 323 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 324 */     if (ww >= 0.0D) {
/* 325 */       if (ww >= 1.0E-30D) {
/* 326 */         this.x = Math.sqrt(ww);
/* 327 */         ww = 1.0D / 2.0D * this.x;
/* 328 */         this.y = m1.m10 * ww;
/* 329 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 333 */       this.x = 0.0D;
/* 334 */       this.y = 0.0D;
/* 335 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 339 */     this.x = 0.0D;
/* 340 */     ww = 0.5D * (1.0D - m1.m22);
/* 341 */     if (ww >= 1.0E-30D) {
/* 342 */       this.y = Math.sqrt(ww);
/* 343 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 347 */     this.y = 0.0D;
/* 348 */     this.z = 1.0D;
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
/* 359 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 361 */     if (ww >= 0.0D) {
/* 362 */       if (ww >= 1.0E-30D) {
/* 363 */         this.w = Math.sqrt(ww);
/* 364 */         ww = 0.25D / this.w;
/* 365 */         this.x = (m1.m21 - m1.m12) * ww;
/* 366 */         this.y = (m1.m02 - m1.m20) * ww;
/* 367 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 371 */       this.w = 0.0D;
/* 372 */       this.x = 0.0D;
/* 373 */       this.y = 0.0D;
/* 374 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 378 */     this.w = 0.0D;
/* 379 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 380 */     if (ww >= 0.0D) {
/* 381 */       if (ww >= 1.0E-30D) {
/* 382 */         this.x = Math.sqrt(ww);
/* 383 */         ww = 0.5D / this.x;
/* 384 */         this.y = m1.m10 * ww;
/* 385 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 389 */       this.x = 0.0D;
/* 390 */       this.y = 0.0D;
/* 391 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 395 */     this.x = 0.0D;
/* 396 */     ww = 0.5D * (1.0D - m1.m22);
/* 397 */     if (ww >= 1.0E-30D) {
/* 398 */       this.y = Math.sqrt(ww);
/* 399 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 403 */     this.y = 0.0D;
/* 404 */     this.z = 1.0D;
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
/* 415 */     double ww = 0.25D * ((m1.m00 + m1.m11 + m1.m22) + 1.0D);
/*     */     
/* 417 */     if (ww >= 0.0D) {
/* 418 */       if (ww >= 1.0E-30D) {
/* 419 */         this.w = Math.sqrt(ww);
/* 420 */         ww = 0.25D / this.w;
/* 421 */         this.x = (m1.m21 - m1.m12) * ww;
/* 422 */         this.y = (m1.m02 - m1.m20) * ww;
/* 423 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 427 */       this.w = 0.0D;
/* 428 */       this.x = 0.0D;
/* 429 */       this.y = 0.0D;
/* 430 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 434 */     this.w = 0.0D;
/* 435 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 436 */     if (ww >= 0.0D) {
/* 437 */       if (ww >= 1.0E-30D) {
/* 438 */         this.x = Math.sqrt(ww);
/* 439 */         ww = 0.5D / this.x;
/* 440 */         this.y = m1.m10 * ww;
/* 441 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 445 */       this.x = 0.0D;
/* 446 */       this.y = 0.0D;
/* 447 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 451 */     this.x = 0.0D;
/* 452 */     ww = 0.5D * (1.0D - m1.m22);
/* 453 */     if (ww >= 1.0E-30D) {
/* 454 */       this.y = Math.sqrt(ww);
/* 455 */       this.z = m1.m21 / 2.0D * this.y;
/*     */     } 
/*     */     
/* 458 */     this.y = 0.0D;
/* 459 */     this.z = 1.0D;
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
/* 470 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + 1.0D);
/*     */     
/* 472 */     if (ww >= 0.0D) {
/* 473 */       if (ww >= 1.0E-30D) {
/* 474 */         this.w = Math.sqrt(ww);
/* 475 */         ww = 0.25D / this.w;
/* 476 */         this.x = (m1.m21 - m1.m12) * ww;
/* 477 */         this.y = (m1.m02 - m1.m20) * ww;
/* 478 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 482 */       this.w = 0.0D;
/* 483 */       this.x = 0.0D;
/* 484 */       this.y = 0.0D;
/* 485 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 489 */     this.w = 0.0D;
/* 490 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 491 */     if (ww >= 0.0D) {
/* 492 */       if (ww >= 1.0E-30D) {
/* 493 */         this.x = Math.sqrt(ww);
/* 494 */         ww = 0.5D / this.x;
/* 495 */         this.y = m1.m10 * ww;
/* 496 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 500 */       this.x = 0.0D;
/* 501 */       this.y = 0.0D;
/* 502 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 506 */     this.x = 0.0D;
/* 507 */     ww = 0.5D * (1.0D - m1.m22);
/* 508 */     if (ww >= 1.0E-30D) {
/* 509 */       this.y = Math.sqrt(ww);
/* 510 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 514 */     this.y = 0.0D;
/* 515 */     this.z = 1.0D;
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
/* 529 */     double amag = Math.sqrt((a.x * a.x + a.y * a.y + a.z * a.z));
/* 530 */     if (amag < 1.0E-12D) {
/* 531 */       this.w = 0.0D;
/* 532 */       this.x = 0.0D;
/* 533 */       this.y = 0.0D;
/* 534 */       this.z = 0.0D;
/*     */     } else {
/* 536 */       double mag = Math.sin(a.angle / 2.0D);
/* 537 */       amag = 1.0D / amag;
/* 538 */       this.w = Math.cos(a.angle / 2.0D);
/* 539 */       this.x = a.x * amag * mag;
/* 540 */       this.y = a.y * amag * mag;
/* 541 */       this.z = a.z * amag * mag;
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
/* 556 */     double amag = Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
/* 557 */     if (amag < 1.0E-12D) {
/* 558 */       this.w = 0.0D;
/* 559 */       this.x = 0.0D;
/* 560 */       this.y = 0.0D;
/* 561 */       this.z = 0.0D;
/*     */     } else {
/* 563 */       amag = 1.0D / amag;
/* 564 */       double mag = Math.sin(a.angle / 2.0D);
/* 565 */       this.w = Math.cos(a.angle / 2.0D);
/* 566 */       this.x = a.x * amag * mag;
/* 567 */       this.y = a.y * amag * mag;
/* 568 */       this.z = a.z * amag * mag;
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
/*     */   public final void interpolate(Quat4d q1, double alpha) {
/* 589 */     double s1, s2, dot = this.x * q1.x + this.y * q1.y + this.z * q1.z + this.w * q1.w;
/*     */     
/* 591 */     if (dot < 0.0D) {
/*     */       
/* 593 */       q1.x = -q1.x; q1.y = -q1.y; q1.z = -q1.z; q1.w = -q1.w;
/* 594 */       dot = -dot;
/*     */     } 
/*     */     
/* 597 */     if (1.0D - dot > 1.0E-12D) {
/* 598 */       double om = Math.acos(dot);
/* 599 */       double sinom = Math.sin(om);
/* 600 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 601 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 603 */       s1 = 1.0D - alpha;
/* 604 */       s2 = alpha;
/*     */     } 
/*     */     
/* 607 */     this.w = s1 * this.w + s2 * q1.w;
/* 608 */     this.x = s1 * this.x + s2 * q1.x;
/* 609 */     this.y = s1 * this.y + s2 * q1.y;
/* 610 */     this.z = s1 * this.z + s2 * q1.z;
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
/*     */   public final void interpolate(Quat4d q1, Quat4d q2, double alpha) {
/* 629 */     double s1, s2, dot = q2.x * q1.x + q2.y * q1.y + q2.z * q1.z + q2.w * q1.w;
/*     */     
/* 631 */     if (dot < 0.0D) {
/*     */       
/* 633 */       q1.x = -q1.x; q1.y = -q1.y; q1.z = -q1.z; q1.w = -q1.w;
/* 634 */       dot = -dot;
/*     */     } 
/*     */     
/* 637 */     if (1.0D - dot > 1.0E-12D) {
/* 638 */       double om = Math.acos(dot);
/* 639 */       double sinom = Math.sin(om);
/* 640 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 641 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 643 */       s1 = 1.0D - alpha;
/* 644 */       s2 = alpha;
/*     */     } 
/* 646 */     this.w = s1 * q1.w + s2 * q2.w;
/* 647 */     this.x = s1 * q1.x + s2 * q2.x;
/* 648 */     this.y = s1 * q1.y + s2 * q2.y;
/* 649 */     this.z = s1 * q1.z + s2 * q2.z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Quat4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */