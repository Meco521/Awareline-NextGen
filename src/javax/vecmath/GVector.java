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
/*     */ public class GVector
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private int length;
/*     */   double[] values;
/*     */   static final long serialVersionUID = 1398850036893875112L;
/*     */   
/*     */   public GVector(int length) {
/*  52 */     this.length = length;
/*  53 */     this.values = new double[length];
/*  54 */     for (int i = 0; i < length; ) { this.values[i] = 0.0D; i++; }
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
/*     */   public GVector(double[] vector) {
/*  68 */     this.length = vector.length;
/*  69 */     this.values = new double[vector.length];
/*  70 */     for (int i = 0; i < this.length; ) { this.values[i] = vector[i]; i++; }
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
/*     */   public GVector(GVector vector) {
/*  82 */     this.values = new double[vector.length];
/*  83 */     this.length = vector.length;
/*  84 */     for (int i = 0; i < this.length; ) { this.values[i] = vector.values[i]; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple2f tuple) {
/*  94 */     this.values = new double[2];
/*  95 */     this.values[0] = tuple.x;
/*  96 */     this.values[1] = tuple.y;
/*  97 */     this.length = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3f tuple) {
/* 107 */     this.values = new double[3];
/* 108 */     this.values[0] = tuple.x;
/* 109 */     this.values[1] = tuple.y;
/* 110 */     this.values[2] = tuple.z;
/* 111 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3d tuple) {
/* 121 */     this.values = new double[3];
/* 122 */     this.values[0] = tuple.x;
/* 123 */     this.values[1] = tuple.y;
/* 124 */     this.values[2] = tuple.z;
/* 125 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4f tuple) {
/* 135 */     this.values = new double[4];
/* 136 */     this.values[0] = tuple.x;
/* 137 */     this.values[1] = tuple.y;
/* 138 */     this.values[2] = tuple.z;
/* 139 */     this.values[3] = tuple.w;
/* 140 */     this.length = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4d tuple) {
/* 150 */     this.values = new double[4];
/* 151 */     this.values[0] = tuple.x;
/* 152 */     this.values[1] = tuple.y;
/* 153 */     this.values[2] = tuple.z;
/* 154 */     this.values[3] = tuple.w;
/* 155 */     this.length = 4;
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
/*     */   public GVector(double[] vector, int length) {
/* 171 */     this.length = length;
/* 172 */     this.values = new double[length];
/* 173 */     for (int i = 0; i < length; i++) {
/* 174 */       this.values[i] = vector[i];
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
/*     */   public final double norm() {
/* 186 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 189 */     for (int i = 0; i < this.length; i++) {
/* 190 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */     
/* 193 */     return Math.sqrt(sq);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double normSquared() {
/* 204 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 207 */     for (int i = 0; i < this.length; i++) {
/* 208 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */     
/* 211 */     return sq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(GVector v1) {
/* 220 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 223 */     if (this.length != v1.length)
/* 224 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector0")); 
/*     */     int i;
/* 226 */     for (i = 0; i < this.length; i++) {
/* 227 */       sq += v1.values[i] * v1.values[i];
/*     */     }
/*     */ 
/*     */     
/* 231 */     double invMag = 1.0D / Math.sqrt(sq);
/*     */     
/* 233 */     for (i = 0; i < this.length; i++) {
/* 234 */       this.values[i] = v1.values[i] * invMag;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 244 */     double sq = 0.0D;
/*     */     
/*     */     int i;
/* 247 */     for (i = 0; i < this.length; i++) {
/* 248 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */ 
/*     */     
/* 252 */     double invMag = 1.0D / Math.sqrt(sq);
/*     */     
/* 254 */     for (i = 0; i < this.length; i++) {
/* 255 */       this.values[i] = this.values[i] * invMag;
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
/*     */   public final void scale(double s, GVector v1) {
/* 269 */     if (this.length != v1.length) {
/* 270 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector1"));
/*     */     }
/* 272 */     for (int i = 0; i < this.length; i++) {
/* 273 */       this.values[i] = v1.values[i] * s;
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
/*     */   public final void scale(double s) {
/* 285 */     for (int i = 0; i < this.length; i++) {
/* 286 */       this.values[i] = this.values[i] * s;
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
/*     */   public final void scaleAdd(double s, GVector v1, GVector v2) {
/* 302 */     if (v2.length != v1.length) {
/* 303 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector2"));
/*     */     }
/* 305 */     if (this.length != v1.length) {
/* 306 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector3"));
/*     */     }
/* 308 */     for (int i = 0; i < this.length; i++) {
/* 309 */       this.values[i] = v1.values[i] * s + v2.values[i];
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
/*     */   public final void add(GVector vector) {
/* 322 */     if (this.length != vector.length) {
/* 323 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector4"));
/*     */     }
/* 325 */     for (int i = 0; i < this.length; i++) {
/* 326 */       this.values[i] = this.values[i] + vector.values[i];
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
/*     */   public final void add(GVector vector1, GVector vector2) {
/* 340 */     if (vector1.length != vector2.length) {
/* 341 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector5"));
/*     */     }
/* 343 */     if (this.length != vector1.length) {
/* 344 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector6"));
/*     */     }
/* 346 */     for (int i = 0; i < this.length; i++) {
/* 347 */       this.values[i] = vector1.values[i] + vector2.values[i];
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
/*     */   public final void sub(GVector vector) {
/* 359 */     if (this.length != vector.length) {
/* 360 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector7"));
/*     */     }
/* 362 */     for (int i = 0; i < this.length; i++) {
/* 363 */       this.values[i] = this.values[i] - vector.values[i];
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
/*     */   public final void sub(GVector vector1, GVector vector2) {
/* 378 */     if (vector1.length != vector2.length) {
/* 379 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector8"));
/*     */     }
/* 381 */     if (this.length != vector1.length) {
/* 382 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector9"));
/*     */     }
/* 384 */     for (int i = 0; i < this.length; i++) {
/* 385 */       this.values[i] = vector1.values[i] - vector2.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mul(GMatrix m1, GVector v1) {
/*     */     double[] v;
/* 395 */     if (m1.getNumCol() != v1.length) {
/* 396 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector10"));
/*     */     }
/* 398 */     if (this.length != m1.getNumRow()) {
/* 399 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector11"));
/*     */     }
/*     */     
/* 402 */     if (v1 != this) {
/* 403 */       v = v1.values;
/*     */     } else {
/* 405 */       v = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 408 */     for (int j = this.length - 1; j >= 0; j--) {
/* 409 */       this.values[j] = 0.0D;
/* 410 */       for (int i = v1.length - 1; i >= 0; i--) {
/* 411 */         this.values[j] = this.values[j] + m1.values[j][i] * v[i];
/*     */       }
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
/*     */   public final void mul(GVector v1, GMatrix m1) {
/*     */     double[] v;
/* 427 */     if (m1.getNumRow() != v1.length) {
/* 428 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector12"));
/*     */     }
/* 430 */     if (this.length != m1.getNumCol()) {
/* 431 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector13"));
/*     */     }
/*     */     
/* 434 */     if (v1 != this) {
/* 435 */       v = v1.values;
/*     */     } else {
/* 437 */       v = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 440 */     for (int j = this.length - 1; j >= 0; j--) {
/* 441 */       this.values[j] = 0.0D;
/* 442 */       for (int i = v1.length - 1; i >= 0; i--) {
/* 443 */         this.values[j] = this.values[j] + m1.values[i][j] * v[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 452 */     for (int i = this.length - 1; i >= 0; i--) {
/* 453 */       this.values[i] = this.values[i] * -1.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void zero() {
/* 461 */     for (int i = 0; i < this.length; i++) {
/* 462 */       this.values[i] = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSize(int length) {
/*     */     int max;
/* 473 */     double[] tmp = new double[length];
/*     */ 
/*     */     
/* 476 */     if (this.length < length) {
/* 477 */       max = this.length;
/*     */     } else {
/* 479 */       max = length;
/*     */     } 
/* 481 */     for (int i = 0; i < max; i++) {
/* 482 */       tmp[i] = this.values[i];
/*     */     }
/* 484 */     this.length = length;
/*     */     
/* 486 */     this.values = tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] vector) {
/* 497 */     if (this.length - 1 + 1 >= 0) System.arraycopy(vector, 0, this.values, 0, this.length - 1 + 1);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(GVector vector) {
/* 507 */     if (this.length < vector.length) {
/* 508 */       this.length = vector.length;
/* 509 */       this.values = new double[this.length];
/* 510 */       for (int i = 0; i < this.length; i++)
/* 511 */         this.values[i] = vector.values[i]; 
/*     */     } else {
/* 513 */       int i; for (i = 0; i < vector.length; i++)
/* 514 */         this.values[i] = vector.values[i]; 
/* 515 */       for (i = vector.length; i < this.length; i++) {
/* 516 */         this.values[i] = 0.0D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f tuple) {
/* 526 */     if (this.length < 2) {
/* 527 */       this.length = 2;
/* 528 */       this.values = new double[2];
/*     */     } 
/* 530 */     this.values[0] = tuple.x;
/* 531 */     this.values[1] = tuple.y;
/* 532 */     for (int i = 2; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f tuple) {
/* 542 */     if (this.length < 3) {
/* 543 */       this.length = 3;
/* 544 */       this.values = new double[3];
/*     */     } 
/* 546 */     this.values[0] = tuple.x;
/* 547 */     this.values[1] = tuple.y;
/* 548 */     this.values[2] = tuple.z;
/* 549 */     for (int i = 3; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d tuple) {
/* 558 */     if (this.length < 3) {
/* 559 */       this.length = 3;
/* 560 */       this.values = new double[3];
/*     */     } 
/* 562 */     this.values[0] = tuple.x;
/* 563 */     this.values[1] = tuple.y;
/* 564 */     this.values[2] = tuple.z;
/* 565 */     for (int i = 3; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f tuple) {
/* 574 */     if (this.length < 4) {
/* 575 */       this.length = 4;
/* 576 */       this.values = new double[4];
/*     */     } 
/* 578 */     this.values[0] = tuple.x;
/* 579 */     this.values[1] = tuple.y;
/* 580 */     this.values[2] = tuple.z;
/* 581 */     this.values[3] = tuple.w;
/* 582 */     for (int i = 4; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d tuple) {
/* 591 */     if (this.length < 4) {
/* 592 */       this.length = 4;
/* 593 */       this.values = new double[4];
/*     */     } 
/* 595 */     this.values[0] = tuple.x;
/* 596 */     this.values[1] = tuple.y;
/* 597 */     this.values[2] = tuple.z;
/* 598 */     this.values[3] = tuple.w;
/* 599 */     for (int i = 4; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSize() {
/* 608 */     return this.values.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getElement(int index) {
/* 618 */     return this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setElement(int index, double value) {
/* 629 */     this.values[index] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 638 */     StringBuffer buffer = new StringBuffer(this.length << 3);
/*     */ 
/*     */ 
/*     */     
/* 642 */     for (int i = 0; i < this.length; i++) {
/* 643 */       buffer.append(this.values[i]).append(" ");
/*     */     }
/*     */     
/* 646 */     return buffer.toString();
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
/*     */   public int hashCode() {
/* 662 */     long bits = 1L;
/*     */     
/* 664 */     for (int i = 0; i < this.length; i++) {
/* 665 */       bits = VecMathUtil.hashDoubleBits(bits, this.values[i]);
/*     */     }
/*     */     
/* 668 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(GVector vector1) {
/*     */     try {
/* 681 */       if (this.length != vector1.length) return false;
/*     */       
/* 683 */       for (int i = 0; i < this.length; i++) {
/* 684 */         if (this.values[i] != vector1.values[i]) return false;
/*     */       
/*     */       } 
/* 687 */       return true;
/*     */     } catch (NullPointerException e2) {
/* 689 */       return false;
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
/*     */   public boolean equals(Object o1) {
/*     */     
/* 703 */     try { GVector v2 = (GVector)o1;
/*     */       
/* 705 */       if (this.length != v2.length) return false;
/*     */       
/* 707 */       for (int i = 0; i < this.length; i++) {
/* 708 */         if (this.values[i] != v2.values[i]) return false; 
/*     */       } 
/* 710 */       return true; }
/*     */     catch (ClassCastException e1)
/* 712 */     { return false; }
/* 713 */     catch (NullPointerException e2) { return false; }
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
/*     */   public boolean epsilonEquals(GVector v1, double epsilon) {
/* 730 */     if (this.length != v1.length) return false;
/*     */     
/* 732 */     for (int i = 0; i < this.length; i++) {
/* 733 */       double diff = this.values[i] - v1.values[i];
/* 734 */       if (((diff < 0.0D) ? -diff : diff) > epsilon) return false; 
/*     */     } 
/* 736 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(GVector v1) {
/* 746 */     if (this.length != v1.length) {
/* 747 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector14"));
/*     */     }
/* 749 */     double result = 0.0D;
/* 750 */     for (int i = 0; i < this.length; i++) {
/* 751 */       result += this.values[i] * v1.values[i];
/*     */     }
/* 753 */     return result;
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
/*     */   public final void SVDBackSolve(GMatrix U, GMatrix W, GMatrix V, GVector b) {
/* 770 */     if (U.nRow != b.getSize() || U.nRow != U.nCol || U.nRow != W.nRow)
/*     */     {
/*     */       
/* 773 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector15"));
/*     */     }
/*     */     
/* 776 */     if (W.nCol != this.values.length || W.nCol != V.nCol || W.nCol != V.nRow)
/*     */     {
/*     */       
/* 779 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector23"));
/*     */     }
/*     */     
/* 782 */     GMatrix tmp = new GMatrix(U.nRow, W.nCol);
/* 783 */     tmp.mul(U, V);
/* 784 */     tmp.mulTransposeRight(U, W);
/* 785 */     tmp.invert();
/* 786 */     mul(tmp, b);
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
/*     */   public final void LUDBackSolve(GMatrix LU, GVector b, GVector permutation) {
/* 804 */     int size = LU.nRow * LU.nCol;
/*     */     
/* 806 */     double[] temp = new double[size];
/* 807 */     double[] result = new double[size];
/* 808 */     int[] row_perm = new int[b.getSize()];
/*     */ 
/*     */     
/* 811 */     if (LU.nRow != b.getSize()) {
/* 812 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector16"));
/*     */     }
/*     */     
/* 815 */     if (LU.nRow != permutation.getSize()) {
/* 816 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector24"));
/*     */     }
/*     */     
/* 819 */     if (LU.nRow != LU.nCol) {
/* 820 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector25"));
/*     */     }
/*     */     int i;
/* 823 */     for (i = 0; i < LU.nRow; i++) {
/* 824 */       for (int j = 0; j < LU.nCol; j++) {
/* 825 */         temp[i * LU.nCol + j] = LU.values[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 829 */     for (i = 0; i < size; ) { result[i] = 0.0D; i++; }
/* 830 */      for (i = 0; i < LU.nRow; ) { result[i * LU.nCol] = b.values[i]; i++; }
/* 831 */      for (i = 0; i < LU.nCol; ) { row_perm[i] = (int)permutation.values[i]; i++; }
/*     */     
/* 833 */     GMatrix.luBacksubstitution(LU.nRow, temp, row_perm, result);
/*     */     
/* 835 */     for (i = 0; i < LU.nRow; ) { this.values[i] = result[i * LU.nCol]; i++; }
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
/*     */   public final double angle(GVector v1) {
/* 847 */     return Math.acos(dot(v1) / norm() * v1.norm());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, GVector v2, float alpha) {
/* 855 */     interpolate(v1, v2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, float alpha) {
/* 863 */     interpolate(v1, alpha);
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
/*     */   public final void interpolate(GVector v1, GVector v2, double alpha) {
/* 876 */     if (v2.length != v1.length) {
/* 877 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector20"));
/*     */     }
/* 879 */     if (this.length != v1.length) {
/* 880 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector21"));
/*     */     }
/* 882 */     for (int i = 0; i < this.length; i++) {
/* 883 */       this.values[i] = (1.0D - alpha) * v1.values[i] + alpha * v2.values[i];
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
/*     */   public final void interpolate(GVector v1, double alpha) {
/* 895 */     if (v1.length != this.length) {
/* 896 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector22"));
/*     */     }
/* 898 */     for (int i = 0; i < this.length; i++) {
/* 899 */       this.values[i] = (1.0D - alpha) * this.values[i] + alpha * v1.values[i];
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
/*     */   public Object clone() {
/* 913 */     GVector v1 = null;
/*     */     try {
/* 915 */       v1 = (GVector)super.clone();
/* 916 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 918 */       throw new InternalError();
/*     */     } 
/*     */ 
/*     */     
/* 922 */     v1.values = new double[this.length];
/* 923 */     System.arraycopy(this.values, 0, v1.values, 0, this.length);
/*     */     
/* 925 */     return v1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\GVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */