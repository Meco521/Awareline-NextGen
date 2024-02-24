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
/*     */ public class Quat4f
/*     */   extends Tuple4f
/*     */ {
/*     */   static final long serialVersionUID = 2675933778405442383L;
/*     */   static final double EPS = 1.0E-6D;
/*     */   static final double EPS2 = 1.0E-30D;
/*     */   
/*     */   public Quat4f(float x, float y, float z, float w) {
/*  54 */     float mag = (float)(1.0D / Math.sqrt((x * x + y * y + z * z + w * w)));
/*  55 */     this.x = x * mag;
/*  56 */     this.y = y * mag;
/*  57 */     this.z = z * mag;
/*  58 */     this.w = w * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(float[] q) {
/*  69 */     float mag = (float)(1.0D / Math.sqrt((q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3])));
/*  70 */     this.x = q[0] * mag;
/*  71 */     this.y = q[1] * mag;
/*  72 */     this.z = q[2] * mag;
/*  73 */     this.w = q[3] * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Quat4f q1) {
/*  84 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Quat4d q1) {
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
/*     */   public Quat4f(Tuple4f t1) {
/* 104 */     float mag = (float)(1.0D / Math.sqrt((t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w)));
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
/*     */   public Quat4f(Tuple4d t1) {
/* 120 */     double mag = 1.0D / Math.sqrt(t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w);
/* 121 */     this.x = (float)(t1.x * mag);
/* 122 */     this.y = (float)(t1.y * mag);
/* 123 */     this.z = (float)(t1.z * mag);
/* 124 */     this.w = (float)(t1.w * mag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mul(Quat4f q1, Quat4f q2) {
/* 144 */     if (this != q1 && this != q2) {
/* 145 */       this.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 146 */       this.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 147 */       this.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 148 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/*     */     }
/*     */     else {
/*     */       
/* 152 */       float w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 153 */       float x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 154 */       float y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 155 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/* 156 */       this.w = w;
/* 157 */       this.x = x;
/* 158 */       this.y = y;
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
/*     */   public final void mul(Quat4f q1) {
/* 172 */     float w = this.w * q1.w - this.x * q1.x - this.y * q1.y - this.z * q1.z;
/* 173 */     float x = this.w * q1.x + q1.w * this.x + this.y * q1.z - this.z * q1.y;
/* 174 */     float y = this.w * q1.y + q1.w * this.y - this.x * q1.z + this.z * q1.x;
/* 175 */     this.z = this.w * q1.z + q1.w * this.z + this.x * q1.y - this.y * q1.x;
/* 176 */     this.w = w;
/* 177 */     this.x = x;
/* 178 */     this.y = y;
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
/*     */   public final void mulInverse(Quat4f q1, Quat4f q2) {
/* 191 */     Quat4f tempQuat = new Quat4f(q2);
/*     */     
/* 193 */     tempQuat.inverse();
/* 194 */     mul(q1, tempQuat);
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
/*     */   public final void mulInverse(Quat4f q1) {
/* 206 */     Quat4f tempQuat = new Quat4f(q1);
/*     */     
/* 208 */     tempQuat.inverse();
/* 209 */     mul(tempQuat);
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
/*     */   public final void inverse(Quat4f q1) {
/* 221 */     float norm = 1.0F / (q1.w * q1.w + q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/* 222 */     this.w = norm * q1.w;
/* 223 */     this.x = -norm * q1.x;
/* 224 */     this.y = -norm * q1.y;
/* 225 */     this.z = -norm * q1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inverse() {
/* 235 */     float norm = 1.0F / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
/* 236 */     this.w *= norm;
/* 237 */     this.x *= -norm;
/* 238 */     this.y *= -norm;
/* 239 */     this.z *= -norm;
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
/*     */   public final void normalize(Quat4f q1) {
/* 252 */     float norm = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z + q1.w * q1.w;
/*     */     
/* 254 */     if (norm > 0.0F) {
/* 255 */       norm = 1.0F / (float)Math.sqrt(norm);
/* 256 */       this.x = norm * q1.x;
/* 257 */       this.y = norm * q1.y;
/* 258 */       this.z = norm * q1.z;
/* 259 */       this.w = norm * q1.w;
/*     */     } else {
/* 261 */       this.x = 0.0F;
/* 262 */       this.y = 0.0F;
/* 263 */       this.z = 0.0F;
/* 264 */       this.w = 0.0F;
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
/* 275 */     float norm = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 277 */     if (norm > 0.0F) {
/* 278 */       norm = 1.0F / (float)Math.sqrt(norm);
/* 279 */       this.x *= norm;
/* 280 */       this.y *= norm;
/* 281 */       this.z *= norm;
/* 282 */       this.w *= norm;
/*     */     } else {
/* 284 */       this.x = 0.0F;
/* 285 */       this.y = 0.0F;
/* 286 */       this.z = 0.0F;
/* 287 */       this.w = 0.0F;
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
/* 299 */     float ww = 0.25F * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 301 */     if (ww >= 0.0F) {
/* 302 */       if (ww >= 1.0E-30D) {
/* 303 */         this.w = (float)Math.sqrt(ww);
/* 304 */         ww = 0.25F / this.w;
/* 305 */         this.x = (m1.m21 - m1.m12) * ww;
/* 306 */         this.y = (m1.m02 - m1.m20) * ww;
/* 307 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 311 */       this.w = 0.0F;
/* 312 */       this.x = 0.0F;
/* 313 */       this.y = 0.0F;
/* 314 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 318 */     this.w = 0.0F;
/* 319 */     ww = -0.5F * (m1.m11 + m1.m22);
/*     */     
/* 321 */     if (ww >= 0.0F) {
/* 322 */       if (ww >= 1.0E-30D) {
/* 323 */         this.x = (float)Math.sqrt(ww);
/* 324 */         ww = 1.0F / 2.0F * this.x;
/* 325 */         this.y = m1.m10 * ww;
/* 326 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 330 */       this.x = 0.0F;
/* 331 */       this.y = 0.0F;
/* 332 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 336 */     this.x = 0.0F;
/* 337 */     ww = 0.5F * (1.0F - m1.m22);
/*     */     
/* 339 */     if (ww >= 1.0E-30D) {
/* 340 */       this.y = (float)Math.sqrt(ww);
/* 341 */       this.z = m1.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 345 */     this.y = 0.0F;
/* 346 */     this.z = 1.0F;
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
/* 357 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 359 */     if (ww >= 0.0D) {
/* 360 */       if (ww >= 1.0E-30D) {
/* 361 */         this.w = (float)Math.sqrt(ww);
/* 362 */         ww = 0.25D / this.w;
/* 363 */         this.x = (float)((m1.m21 - m1.m12) * ww);
/* 364 */         this.y = (float)((m1.m02 - m1.m20) * ww);
/* 365 */         this.z = (float)((m1.m10 - m1.m01) * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 369 */       this.w = 0.0F;
/* 370 */       this.x = 0.0F;
/* 371 */       this.y = 0.0F;
/* 372 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 376 */     this.w = 0.0F;
/* 377 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 378 */     if (ww >= 0.0D) {
/* 379 */       if (ww >= 1.0E-30D) {
/* 380 */         this.x = (float)Math.sqrt(ww);
/* 381 */         ww = 0.5D / this.x;
/* 382 */         this.y = (float)(m1.m10 * ww);
/* 383 */         this.z = (float)(m1.m20 * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 387 */       this.x = 0.0F;
/* 388 */       this.y = 0.0F;
/* 389 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 393 */     this.x = 0.0F;
/* 394 */     ww = 0.5D * (1.0D - m1.m22);
/* 395 */     if (ww >= 1.0E-30D) {
/* 396 */       this.y = (float)Math.sqrt(ww);
/* 397 */       this.z = (float)(m1.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 401 */     this.y = 0.0F;
/* 402 */     this.z = 1.0F;
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
/* 413 */     float ww = 0.25F * (m1.m00 + m1.m11 + m1.m22 + 1.0F);
/*     */     
/* 415 */     if (ww >= 0.0F) {
/* 416 */       if (ww >= 1.0E-30D) {
/* 417 */         this.w = (float)Math.sqrt(ww);
/* 418 */         ww = 0.25F / this.w;
/* 419 */         this.x = (m1.m21 - m1.m12) * ww;
/* 420 */         this.y = (m1.m02 - m1.m20) * ww;
/* 421 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 425 */       this.w = 0.0F;
/* 426 */       this.x = 0.0F;
/* 427 */       this.y = 0.0F;
/* 428 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 432 */     this.w = 0.0F;
/* 433 */     ww = -0.5F * (m1.m11 + m1.m22);
/* 434 */     if (ww >= 0.0F) {
/* 435 */       if (ww >= 1.0E-30D) {
/* 436 */         this.x = (float)Math.sqrt(ww);
/* 437 */         ww = 0.5F / this.x;
/* 438 */         this.y = m1.m10 * ww;
/* 439 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 443 */       this.x = 0.0F;
/* 444 */       this.y = 0.0F;
/* 445 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 449 */     this.x = 0.0F;
/* 450 */     ww = 0.5F * (1.0F - m1.m22);
/* 451 */     if (ww >= 1.0E-30D) {
/* 452 */       this.y = (float)Math.sqrt(ww);
/* 453 */       this.z = m1.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 457 */     this.y = 0.0F;
/* 458 */     this.z = 1.0F;
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
/* 469 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + 1.0D);
/*     */     
/* 471 */     if (ww >= 0.0D) {
/* 472 */       if (ww >= 1.0E-30D) {
/* 473 */         this.w = (float)Math.sqrt(ww);
/* 474 */         ww = 0.25D / this.w;
/* 475 */         this.x = (float)((m1.m21 - m1.m12) * ww);
/* 476 */         this.y = (float)((m1.m02 - m1.m20) * ww);
/* 477 */         this.z = (float)((m1.m10 - m1.m01) * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 481 */       this.w = 0.0F;
/* 482 */       this.x = 0.0F;
/* 483 */       this.y = 0.0F;
/* 484 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 488 */     this.w = 0.0F;
/* 489 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 490 */     if (ww >= 0.0D) {
/* 491 */       if (ww >= 1.0E-30D) {
/* 492 */         this.x = (float)Math.sqrt(ww);
/* 493 */         ww = 0.5D / this.x;
/* 494 */         this.y = (float)(m1.m10 * ww);
/* 495 */         this.z = (float)(m1.m20 * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 499 */       this.x = 0.0F;
/* 500 */       this.y = 0.0F;
/* 501 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 505 */     this.x = 0.0F;
/* 506 */     ww = 0.5D * (1.0D - m1.m22);
/* 507 */     if (ww >= 1.0E-30D) {
/* 508 */       this.y = (float)Math.sqrt(ww);
/* 509 */       this.z = (float)(m1.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 513 */     this.y = 0.0F;
/* 514 */     this.z = 1.0F;
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
/*     */   public final void set(AxisAngle4f a) {
/* 527 */     float amag = (float)Math.sqrt((a.x * a.x + a.y * a.y + a.z * a.z));
/* 528 */     if (amag < 1.0E-6D) {
/* 529 */       this.w = 0.0F;
/* 530 */       this.x = 0.0F;
/* 531 */       this.y = 0.0F;
/* 532 */       this.z = 0.0F;
/*     */     } else {
/* 534 */       amag = 1.0F / amag;
/* 535 */       float mag = (float)Math.sin(a.angle / 2.0D);
/* 536 */       this.w = (float)Math.cos(a.angle / 2.0D);
/* 537 */       this.x = a.x * amag * mag;
/* 538 */       this.y = a.y * amag * mag;
/* 539 */       this.z = a.z * amag * mag;
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
/* 554 */     float amag = (float)(1.0D / Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z));
/*     */     
/* 556 */     if (amag < 1.0E-6D) {
/* 557 */       this.w = 0.0F;
/* 558 */       this.x = 0.0F;
/* 559 */       this.y = 0.0F;
/* 560 */       this.z = 0.0F;
/*     */     } else {
/* 562 */       amag = 1.0F / amag;
/* 563 */       float mag = (float)Math.sin(a.angle / 2.0D);
/* 564 */       this.w = (float)Math.cos(a.angle / 2.0D);
/* 565 */       this.x = (float)a.x * amag * mag;
/* 566 */       this.y = (float)a.y * amag * mag;
/* 567 */       this.z = (float)a.z * amag * mag;
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
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4f q1, float alpha) {
/* 591 */     double s1, s2, dot = (this.x * q1.x + this.y * q1.y + this.z * q1.z + this.w * q1.w);
/*     */     
/* 593 */     if (dot < 0.0D) {
/*     */       
/* 595 */       q1.x = -q1.x;
/* 596 */       q1.y = -q1.y;
/* 597 */       q1.z = -q1.z;
/* 598 */       q1.w = -q1.w;
/* 599 */       dot = -dot;
/*     */     } 
/*     */     
/* 602 */     if (1.0D - dot > 1.0E-6D) {
/* 603 */       double om = Math.acos(dot);
/* 604 */       double sinom = Math.sin(om);
/* 605 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 606 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 608 */       s1 = 1.0D - alpha;
/* 609 */       s2 = alpha;
/*     */     } 
/*     */     
/* 612 */     this.w = (float)(s1 * this.w + s2 * q1.w);
/* 613 */     this.x = (float)(s1 * this.x + s2 * q1.x);
/* 614 */     this.y = (float)(s1 * this.y + s2 * q1.y);
/* 615 */     this.z = (float)(s1 * this.z + s2 * q1.z);
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
/*     */   
/*     */   public final void interpolate(Quat4f q1, Quat4f q2, float alpha) {
/* 637 */     double s1, s2, dot = (q2.x * q1.x + q2.y * q1.y + q2.z * q1.z + q2.w * q1.w);
/*     */     
/* 639 */     if (dot < 0.0D) {
/*     */       
/* 641 */       q1.x = -q1.x;
/* 642 */       q1.y = -q1.y;
/* 643 */       q1.z = -q1.z;
/* 644 */       q1.w = -q1.w;
/* 645 */       dot = -dot;
/*     */     } 
/*     */     
/* 648 */     if (1.0D - dot > 1.0E-6D) {
/* 649 */       double om = Math.acos(dot);
/* 650 */       double sinom = Math.sin(om);
/* 651 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 652 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 654 */       s1 = 1.0D - alpha;
/* 655 */       s2 = alpha;
/*     */     } 
/* 657 */     this.w = (float)(s1 * q1.w + s2 * q2.w);
/* 658 */     this.x = (float)(s1 * q1.x + s2 * q2.x);
/* 659 */     this.y = (float)(s1 * q1.y + s2 * q2.y);
/* 660 */     this.z = (float)(s1 * q1.z + s2 * q2.z);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Quat4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */