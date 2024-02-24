/*      */ package javax.vecmath;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Matrix3f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 329697160112089834L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix3f(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
/*  107 */     this.m00 = m00;
/*  108 */     this.m01 = m01;
/*  109 */     this.m02 = m02;
/*      */     
/*  111 */     this.m10 = m10;
/*  112 */     this.m11 = m11;
/*  113 */     this.m12 = m12;
/*      */     
/*  115 */     this.m20 = m20;
/*  116 */     this.m21 = m21;
/*  117 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(float[] v) {
/*  128 */     this.m00 = v[0];
/*  129 */     this.m01 = v[1];
/*  130 */     this.m02 = v[2];
/*      */     
/*  132 */     this.m10 = v[3];
/*  133 */     this.m11 = v[4];
/*  134 */     this.m12 = v[5];
/*      */     
/*  136 */     this.m20 = v[6];
/*  137 */     this.m21 = v[7];
/*  138 */     this.m22 = v[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(Matrix3d m1) {
/*  149 */     this.m00 = (float)m1.m00;
/*  150 */     this.m01 = (float)m1.m01;
/*  151 */     this.m02 = (float)m1.m02;
/*      */     
/*  153 */     this.m10 = (float)m1.m10;
/*  154 */     this.m11 = (float)m1.m11;
/*  155 */     this.m12 = (float)m1.m12;
/*      */     
/*  157 */     this.m20 = (float)m1.m20;
/*  158 */     this.m21 = (float)m1.m21;
/*  159 */     this.m22 = (float)m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(Matrix3f m1) {
/*  171 */     this.m00 = m1.m00;
/*  172 */     this.m01 = m1.m01;
/*  173 */     this.m02 = m1.m02;
/*      */     
/*  175 */     this.m10 = m1.m10;
/*  176 */     this.m11 = m1.m11;
/*  177 */     this.m12 = m1.m12;
/*      */     
/*  179 */     this.m20 = m1.m20;
/*  180 */     this.m21 = m1.m21;
/*  181 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f() {
/*  191 */     this.m00 = 0.0F;
/*  192 */     this.m01 = 0.0F;
/*  193 */     this.m02 = 0.0F;
/*      */     
/*  195 */     this.m10 = 0.0F;
/*  196 */     this.m11 = 0.0F;
/*  197 */     this.m12 = 0.0F;
/*      */     
/*  199 */     this.m20 = 0.0F;
/*  200 */     this.m21 = 0.0F;
/*  201 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  211 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  222 */     this.m00 = 1.0F;
/*  223 */     this.m01 = 0.0F;
/*  224 */     this.m02 = 0.0F;
/*      */     
/*  226 */     this.m10 = 0.0F;
/*  227 */     this.m11 = 1.0F;
/*  228 */     this.m12 = 0.0F;
/*      */     
/*  230 */     this.m20 = 0.0F;
/*  231 */     this.m21 = 0.0F;
/*  232 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(float scale) {
/*  243 */     double[] tmp_rot = new double[9];
/*  244 */     double[] tmp_scale = new double[3];
/*      */     
/*  246 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  248 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  249 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  250 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  252 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  253 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  254 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  256 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  257 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  258 */     this.m22 = (float)(tmp_rot[8] * scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int row, int column, float value) {
/*  270 */     switch (row) {
/*      */       
/*      */       case 0:
/*  273 */         switch (column) {
/*      */           
/*      */           case 0:
/*  276 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  279 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  282 */             this.m02 = value;
/*      */             return;
/*      */         } 
/*  285 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  290 */         switch (column) {
/*      */           
/*      */           case 0:
/*  293 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  296 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  299 */             this.m12 = value;
/*      */             return;
/*      */         } 
/*  302 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  307 */         switch (column) {
/*      */           
/*      */           case 0:
/*  310 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  313 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  316 */             this.m22 = value;
/*      */             return;
/*      */         } 
/*      */         
/*  320 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  325 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector3f v) {
/*  335 */     if (row == 0) {
/*  336 */       v.x = this.m00;
/*  337 */       v.y = this.m01;
/*  338 */       v.z = this.m02;
/*  339 */     } else if (row == 1) {
/*  340 */       v.x = this.m10;
/*  341 */       v.y = this.m11;
/*  342 */       v.z = this.m12;
/*  343 */     } else if (row == 2) {
/*  344 */       v.x = this.m20;
/*  345 */       v.y = this.m21;
/*  346 */       v.z = this.m22;
/*      */     } else {
/*  348 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, float[] v) {
/*  359 */     if (row == 0) {
/*  360 */       v[0] = this.m00;
/*  361 */       v[1] = this.m01;
/*  362 */       v[2] = this.m02;
/*  363 */     } else if (row == 1) {
/*  364 */       v[0] = this.m10;
/*  365 */       v[1] = this.m11;
/*  366 */       v[2] = this.m12;
/*  367 */     } else if (row == 2) {
/*  368 */       v[0] = this.m20;
/*  369 */       v[1] = this.m21;
/*  370 */       v[2] = this.m22;
/*      */     } else {
/*  372 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int column, Vector3f v) {
/*  384 */     if (column == 0) {
/*  385 */       v.x = this.m00;
/*  386 */       v.y = this.m10;
/*  387 */       v.z = this.m20;
/*  388 */     } else if (column == 1) {
/*  389 */       v.x = this.m01;
/*  390 */       v.y = this.m11;
/*  391 */       v.z = this.m21;
/*  392 */     } else if (column == 2) {
/*  393 */       v.x = this.m02;
/*  394 */       v.y = this.m12;
/*  395 */       v.z = this.m22;
/*      */     } else {
/*  397 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int column, float[] v) {
/*  409 */     if (column == 0) {
/*  410 */       v[0] = this.m00;
/*  411 */       v[1] = this.m10;
/*  412 */       v[2] = this.m20;
/*  413 */     } else if (column == 1) {
/*  414 */       v[0] = this.m01;
/*  415 */       v[1] = this.m11;
/*  416 */       v[2] = this.m21;
/*  417 */     } else if (column == 2) {
/*  418 */       v[0] = this.m02;
/*  419 */       v[1] = this.m12;
/*  420 */       v[2] = this.m22;
/*      */     } else {
/*  422 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getElement(int row, int column) {
/*  435 */     switch (row) {
/*      */       
/*      */       case 0:
/*  438 */         switch (column) {
/*      */           
/*      */           case 0:
/*  441 */             return this.m00;
/*      */           case 1:
/*  443 */             return this.m01;
/*      */           case 2:
/*  445 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  451 */         switch (column) {
/*      */           
/*      */           case 0:
/*  454 */             return this.m10;
/*      */           case 1:
/*  456 */             return this.m11;
/*      */           case 2:
/*  458 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  465 */         switch (column) {
/*      */           
/*      */           case 0:
/*  468 */             return this.m20;
/*      */           case 1:
/*  470 */             return this.m21;
/*      */           case 2:
/*  472 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  481 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, float x, float y, float z) {
/*  493 */     switch (row) {
/*      */       case 0:
/*  495 */         this.m00 = x;
/*  496 */         this.m01 = y;
/*  497 */         this.m02 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  501 */         this.m10 = x;
/*  502 */         this.m11 = y;
/*  503 */         this.m12 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  507 */         this.m20 = x;
/*  508 */         this.m21 = y;
/*  509 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  513 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector3f v) {
/*  524 */     switch (row) {
/*      */       case 0:
/*  526 */         this.m00 = v.x;
/*  527 */         this.m01 = v.y;
/*  528 */         this.m02 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  532 */         this.m10 = v.x;
/*  533 */         this.m11 = v.y;
/*  534 */         this.m12 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  538 */         this.m20 = v.x;
/*  539 */         this.m21 = v.y;
/*  540 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  544 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, float[] v) {
/*  555 */     switch (row) {
/*      */       case 0:
/*  557 */         this.m00 = v[0];
/*  558 */         this.m01 = v[1];
/*  559 */         this.m02 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  563 */         this.m10 = v[0];
/*  564 */         this.m11 = v[1];
/*  565 */         this.m12 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  569 */         this.m20 = v[0];
/*  570 */         this.m21 = v[1];
/*  571 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  575 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, float x, float y, float z) {
/*  588 */     switch (column) {
/*      */       case 0:
/*  590 */         this.m00 = x;
/*  591 */         this.m10 = y;
/*  592 */         this.m20 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  596 */         this.m01 = x;
/*  597 */         this.m11 = y;
/*  598 */         this.m21 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  602 */         this.m02 = x;
/*  603 */         this.m12 = y;
/*  604 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  608 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector3f v) {
/*  619 */     switch (column) {
/*      */       case 0:
/*  621 */         this.m00 = v.x;
/*  622 */         this.m10 = v.y;
/*  623 */         this.m20 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  627 */         this.m01 = v.x;
/*  628 */         this.m11 = v.y;
/*  629 */         this.m21 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  633 */         this.m02 = v.x;
/*  634 */         this.m12 = v.y;
/*  635 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  639 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, float[] v) {
/*  650 */     switch (column) {
/*      */       case 0:
/*  652 */         this.m00 = v[0];
/*  653 */         this.m10 = v[1];
/*  654 */         this.m20 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  658 */         this.m01 = v[0];
/*  659 */         this.m11 = v[1];
/*  660 */         this.m21 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  664 */         this.m02 = v[0];
/*  665 */         this.m12 = v[1];
/*  666 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  670 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getScale() {
/*  684 */     double[] tmp_rot = new double[9];
/*  685 */     double[] tmp_scale = new double[3];
/*  686 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  688 */     return (float)Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/*  698 */     this.m00 += scalar;
/*  699 */     this.m01 += scalar;
/*  700 */     this.m02 += scalar;
/*  701 */     this.m10 += scalar;
/*  702 */     this.m11 += scalar;
/*  703 */     this.m12 += scalar;
/*  704 */     this.m20 += scalar;
/*  705 */     this.m21 += scalar;
/*  706 */     this.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar, Matrix3f m1) {
/*  717 */     m1.m00 += scalar;
/*  718 */     m1.m01 += scalar;
/*  719 */     m1.m02 += scalar;
/*  720 */     m1.m10 += scalar;
/*  721 */     m1.m11 += scalar;
/*  722 */     m1.m12 += scalar;
/*  723 */     m1.m20 += scalar;
/*  724 */     m1.m21 += scalar;
/*  725 */     m1.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1, Matrix3f m2) {
/*  735 */     m1.m00 += m2.m00;
/*  736 */     m1.m01 += m2.m01;
/*  737 */     m1.m02 += m2.m02;
/*      */     
/*  739 */     m1.m10 += m2.m10;
/*  740 */     m1.m11 += m2.m11;
/*  741 */     m1.m12 += m2.m12;
/*      */     
/*  743 */     m1.m20 += m2.m20;
/*  744 */     m1.m21 += m2.m21;
/*  745 */     m1.m22 += m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1) {
/*  755 */     this.m00 += m1.m00;
/*  756 */     this.m01 += m1.m01;
/*  757 */     this.m02 += m1.m02;
/*      */     
/*  759 */     this.m10 += m1.m10;
/*  760 */     this.m11 += m1.m11;
/*  761 */     this.m12 += m1.m12;
/*      */     
/*  763 */     this.m20 += m1.m20;
/*  764 */     this.m21 += m1.m21;
/*  765 */     this.m22 += m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f m1, Matrix3f m2) {
/*  776 */     m1.m00 -= m2.m00;
/*  777 */     m1.m01 -= m2.m01;
/*  778 */     m1.m02 -= m2.m02;
/*      */     
/*  780 */     m1.m10 -= m2.m10;
/*  781 */     m1.m11 -= m2.m11;
/*  782 */     m1.m12 -= m2.m12;
/*      */     
/*  784 */     m1.m20 -= m2.m20;
/*  785 */     m1.m21 -= m2.m21;
/*  786 */     m1.m22 -= m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f m1) {
/*  796 */     this.m00 -= m1.m00;
/*  797 */     this.m01 -= m1.m01;
/*  798 */     this.m02 -= m1.m02;
/*      */     
/*  800 */     this.m10 -= m1.m10;
/*  801 */     this.m11 -= m1.m11;
/*  802 */     this.m12 -= m1.m12;
/*      */     
/*  804 */     this.m20 -= m1.m20;
/*  805 */     this.m21 -= m1.m21;
/*  806 */     this.m22 -= m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/*  816 */     float temp = this.m10;
/*  817 */     this.m10 = this.m01;
/*  818 */     this.m01 = temp;
/*      */     
/*  820 */     temp = this.m20;
/*  821 */     this.m20 = this.m02;
/*  822 */     this.m02 = temp;
/*      */     
/*  824 */     temp = this.m21;
/*  825 */     this.m21 = this.m12;
/*  826 */     this.m12 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix3f m1) {
/*  835 */     if (this != m1) {
/*  836 */       this.m00 = m1.m00;
/*  837 */       this.m01 = m1.m10;
/*  838 */       this.m02 = m1.m20;
/*      */       
/*  840 */       this.m10 = m1.m01;
/*  841 */       this.m11 = m1.m11;
/*  842 */       this.m12 = m1.m21;
/*      */       
/*  844 */       this.m20 = m1.m02;
/*  845 */       this.m21 = m1.m12;
/*  846 */       this.m22 = m1.m22;
/*      */     } else {
/*  848 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/*  858 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/*  859 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/*  860 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  862 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/*  863 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/*  864 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  866 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/*  867 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/*  868 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/*  878 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*  879 */     if (mag < 1.0E-8D) {
/*  880 */       this.m00 = 1.0F;
/*  881 */       this.m01 = 0.0F;
/*  882 */       this.m02 = 0.0F;
/*      */       
/*  884 */       this.m10 = 0.0F;
/*  885 */       this.m11 = 1.0F;
/*  886 */       this.m12 = 0.0F;
/*      */       
/*  888 */       this.m20 = 0.0F;
/*  889 */       this.m21 = 0.0F;
/*  890 */       this.m22 = 1.0F;
/*      */     } else {
/*  892 */       mag = 1.0F / mag;
/*  893 */       float ax = a1.x * mag;
/*  894 */       float ay = a1.y * mag;
/*  895 */       float az = a1.z * mag;
/*      */       
/*  897 */       float sinTheta = (float)Math.sin(a1.angle);
/*  898 */       float cosTheta = (float)Math.cos(a1.angle);
/*  899 */       float t = 1.0F - cosTheta;
/*      */       
/*  901 */       float xz = ax * az;
/*  902 */       float xy = ax * ay;
/*  903 */       float yz = ay * az;
/*      */       
/*  905 */       this.m00 = t * ax * ax + cosTheta;
/*  906 */       this.m01 = t * xy - sinTheta * az;
/*  907 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  909 */       this.m10 = t * xy + sinTheta * az;
/*  910 */       this.m11 = t * ay * ay + cosTheta;
/*  911 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  913 */       this.m20 = t * xz - sinTheta * ay;
/*  914 */       this.m21 = t * yz + sinTheta * ax;
/*  915 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/*  927 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*  928 */     if (mag < 1.0E-8D) {
/*  929 */       this.m00 = 1.0F;
/*  930 */       this.m01 = 0.0F;
/*  931 */       this.m02 = 0.0F;
/*      */       
/*  933 */       this.m10 = 0.0F;
/*  934 */       this.m11 = 1.0F;
/*  935 */       this.m12 = 0.0F;
/*      */       
/*  937 */       this.m20 = 0.0F;
/*  938 */       this.m21 = 0.0F;
/*  939 */       this.m22 = 1.0F;
/*      */     } else {
/*  941 */       mag = 1.0D / mag;
/*  942 */       double ax = a1.x * mag;
/*  943 */       double ay = a1.y * mag;
/*  944 */       double az = a1.z * mag;
/*      */       
/*  946 */       double sinTheta = Math.sin(a1.angle);
/*  947 */       double cosTheta = Math.cos(a1.angle);
/*  948 */       double t = 1.0D - cosTheta;
/*      */       
/*  950 */       double xz = ax * az;
/*  951 */       double xy = ax * ay;
/*  952 */       double yz = ay * az;
/*      */       
/*  954 */       this.m00 = (float)(t * ax * ax + cosTheta);
/*  955 */       this.m01 = (float)(t * xy - sinTheta * az);
/*  956 */       this.m02 = (float)(t * xz + sinTheta * ay);
/*      */       
/*  958 */       this.m10 = (float)(t * xy + sinTheta * az);
/*  959 */       this.m11 = (float)(t * ay * ay + cosTheta);
/*  960 */       this.m12 = (float)(t * yz - sinTheta * ax);
/*      */       
/*  962 */       this.m20 = (float)(t * xz - sinTheta * ay);
/*  963 */       this.m21 = (float)(t * yz + sinTheta * ax);
/*  964 */       this.m22 = (float)(t * az * az + cosTheta);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/*  976 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  977 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  978 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  980 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  981 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  982 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  984 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  985 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  986 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float[] m) {
/*  997 */     this.m00 = m[0];
/*  998 */     this.m01 = m[1];
/*  999 */     this.m02 = m[2];
/*      */     
/* 1001 */     this.m10 = m[3];
/* 1002 */     this.m11 = m[4];
/* 1003 */     this.m12 = m[5];
/*      */     
/* 1005 */     this.m20 = m[6];
/* 1006 */     this.m21 = m[7];
/* 1007 */     this.m22 = m[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f m1) {
/* 1019 */     this.m00 = m1.m00;
/* 1020 */     this.m01 = m1.m01;
/* 1021 */     this.m02 = m1.m02;
/*      */     
/* 1023 */     this.m10 = m1.m10;
/* 1024 */     this.m11 = m1.m11;
/* 1025 */     this.m12 = m1.m12;
/*      */     
/* 1027 */     this.m20 = m1.m20;
/* 1028 */     this.m21 = m1.m21;
/* 1029 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/* 1041 */     this.m00 = (float)m1.m00;
/* 1042 */     this.m01 = (float)m1.m01;
/* 1043 */     this.m02 = (float)m1.m02;
/*      */     
/* 1045 */     this.m10 = (float)m1.m10;
/* 1046 */     this.m11 = (float)m1.m11;
/* 1047 */     this.m12 = (float)m1.m12;
/*      */     
/* 1049 */     this.m20 = (float)m1.m20;
/* 1050 */     this.m21 = (float)m1.m21;
/* 1051 */     this.m22 = (float)m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix3f m1) {
/* 1063 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1071 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void invertGeneral(Matrix3f m1) {
/* 1083 */     double[] temp = new double[9];
/* 1084 */     double[] result = new double[9];
/* 1085 */     int[] row_perm = new int[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1092 */     temp[0] = m1.m00;
/* 1093 */     temp[1] = m1.m01;
/* 1094 */     temp[2] = m1.m02;
/*      */     
/* 1096 */     temp[3] = m1.m10;
/* 1097 */     temp[4] = m1.m11;
/* 1098 */     temp[5] = m1.m12;
/*      */     
/* 1100 */     temp[6] = m1.m20;
/* 1101 */     temp[7] = m1.m21;
/* 1102 */     temp[8] = m1.m22;
/*      */ 
/*      */ 
/*      */     
/* 1106 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1108 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));
/*      */     }
/*      */ 
/*      */     
/* 1112 */     for (int i = 0; i < 9; ) { result[i] = 0.0D; i++; }
/* 1113 */      result[0] = 1.0D; result[4] = 1.0D; result[8] = 1.0D;
/* 1114 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1116 */     this.m00 = (float)result[0];
/* 1117 */     this.m01 = (float)result[1];
/* 1118 */     this.m02 = (float)result[2];
/*      */     
/* 1120 */     this.m10 = (float)result[3];
/* 1121 */     this.m11 = (float)result[4];
/* 1122 */     this.m12 = (float)result[5];
/*      */     
/* 1124 */     this.m20 = (float)result[6];
/* 1125 */     this.m21 = (float)result[7];
/* 1126 */     this.m22 = (float)result[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(double[] matrix0, int[] row_perm) {
/* 1153 */     double[] row_scale = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1161 */     int ptr = 0;
/* 1162 */     int rs = 0;
/*      */ 
/*      */     
/* 1165 */     int i = 3;
/* 1166 */     while (i-- != 0) {
/* 1167 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1170 */       int k = 3;
/* 1171 */       while (k-- != 0) {
/* 1172 */         double temp = matrix0[ptr++];
/* 1173 */         temp = Math.abs(temp);
/* 1174 */         if (temp > big) {
/* 1175 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1180 */       if (big == 0.0D) {
/* 1181 */         return false;
/*      */       }
/* 1183 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1191 */     int mtx = 0;
/*      */ 
/*      */     
/* 1194 */     for (int j = 0; j < 3; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1200 */       for (k = 0; k < j; k++) {
/* 1201 */         int target = mtx + 3 * k + j;
/* 1202 */         double sum = matrix0[target];
/* 1203 */         int m = k;
/* 1204 */         int p1 = mtx + 3 * k;
/* 1205 */         int p2 = mtx + j;
/* 1206 */         while (m-- != 0) {
/* 1207 */           sum -= matrix0[p1] * matrix0[p2];
/* 1208 */           p1++;
/* 1209 */           p2 += 3;
/*      */         } 
/* 1211 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1216 */       double big = 0.0D;
/* 1217 */       int imax = -1;
/* 1218 */       for (k = j; k < 3; k++) {
/* 1219 */         int target = mtx + 3 * k + j;
/* 1220 */         double sum = matrix0[target];
/* 1221 */         int m = j;
/* 1222 */         int p1 = mtx + 3 * k;
/* 1223 */         int p2 = mtx + j;
/* 1224 */         while (m-- != 0) {
/* 1225 */           sum -= matrix0[p1] * matrix0[p2];
/* 1226 */           p1++;
/* 1227 */           p2 += 3;
/*      */         } 
/* 1229 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1232 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1233 */           big = temp;
/* 1234 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1238 */       if (imax < 0) {
/* 1239 */         throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));
/*      */       }
/*      */ 
/*      */       
/* 1243 */       if (j != imax) {
/*      */         
/* 1245 */         int m = 3;
/* 1246 */         int p1 = mtx + 3 * imax;
/* 1247 */         int p2 = mtx + 3 * j;
/* 1248 */         while (m-- != 0) {
/* 1249 */           double temp = matrix0[p1];
/* 1250 */           matrix0[p1++] = matrix0[p2];
/* 1251 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1255 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1259 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1262 */       if (matrix0[mtx + 3 * j + j] == 0.0D) {
/* 1263 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1267 */       if (j != 2) {
/* 1268 */         double temp = 1.0D / matrix0[mtx + 3 * j + j];
/* 1269 */         int target = mtx + 3 * (j + 1) + j;
/* 1270 */         k = 2 - j;
/* 1271 */         while (k-- != 0) {
/* 1272 */           matrix0[target] = matrix0[target] * temp;
/* 1273 */           target += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1279 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void luBacksubstitution(double[] matrix1, int[] row_perm, double[] matrix2) {
/* 1309 */     int rp = 0;
/*      */ 
/*      */     
/* 1312 */     for (int k = 0; k < 3; k++) {
/*      */       
/* 1314 */       int cv = k;
/* 1315 */       int ii = -1;
/*      */ 
/*      */       
/* 1318 */       for (int i = 0; i < 3; i++) {
/*      */ 
/*      */         
/* 1321 */         int ip = row_perm[rp + i];
/* 1322 */         double sum = matrix2[cv + 3 * ip];
/* 1323 */         matrix2[cv + 3 * ip] = matrix2[cv + 3 * i];
/* 1324 */         if (ii >= 0) {
/*      */           
/* 1326 */           int m = i * 3;
/* 1327 */           for (int j = ii; j <= i - 1; j++) {
/* 1328 */             sum -= matrix1[m + j] * matrix2[cv + 3 * j];
/*      */           }
/*      */         }
/* 1331 */         else if (sum != 0.0D) {
/* 1332 */           ii = i;
/*      */         } 
/* 1334 */         matrix2[cv + 3 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1339 */       int rv = 6;
/* 1340 */       matrix2[cv + 6] = matrix2[cv + 6] / matrix1[rv + 2];
/*      */       
/* 1342 */       rv -= 3;
/* 1343 */       matrix2[cv + 3] = (matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 1];
/*      */ 
/*      */       
/* 1346 */       rv -= 3;
/* 1347 */       matrix2[cv] = (matrix2[cv] - matrix1[rv + 1] * matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float determinant() {
/* 1360 */     float total = this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
/*      */ 
/*      */     
/* 1363 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 1373 */     this.m00 = scale;
/* 1374 */     this.m01 = 0.0F;
/* 1375 */     this.m02 = 0.0F;
/*      */     
/* 1377 */     this.m10 = 0.0F;
/* 1378 */     this.m11 = scale;
/* 1379 */     this.m12 = 0.0F;
/*      */     
/* 1381 */     this.m20 = 0.0F;
/* 1382 */     this.m21 = 0.0F;
/* 1383 */     this.m22 = scale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotX(float angle) {
/* 1395 */     float sinAngle = (float)Math.sin(angle);
/* 1396 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1398 */     this.m00 = 1.0F;
/* 1399 */     this.m01 = 0.0F;
/* 1400 */     this.m02 = 0.0F;
/*      */     
/* 1402 */     this.m10 = 0.0F;
/* 1403 */     this.m11 = cosAngle;
/* 1404 */     this.m12 = -sinAngle;
/*      */     
/* 1406 */     this.m20 = 0.0F;
/* 1407 */     this.m21 = sinAngle;
/* 1408 */     this.m22 = cosAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotY(float angle) {
/* 1420 */     float sinAngle = (float)Math.sin(angle);
/* 1421 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1423 */     this.m00 = cosAngle;
/* 1424 */     this.m01 = 0.0F;
/* 1425 */     this.m02 = sinAngle;
/*      */     
/* 1427 */     this.m10 = 0.0F;
/* 1428 */     this.m11 = 1.0F;
/* 1429 */     this.m12 = 0.0F;
/*      */     
/* 1431 */     this.m20 = -sinAngle;
/* 1432 */     this.m21 = 0.0F;
/* 1433 */     this.m22 = cosAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotZ(float angle) {
/* 1445 */     float sinAngle = (float)Math.sin(angle);
/* 1446 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1448 */     this.m00 = cosAngle;
/* 1449 */     this.m01 = -sinAngle;
/* 1450 */     this.m02 = 0.0F;
/*      */     
/* 1452 */     this.m10 = sinAngle;
/* 1453 */     this.m11 = cosAngle;
/* 1454 */     this.m12 = 0.0F;
/*      */     
/* 1456 */     this.m20 = 0.0F;
/* 1457 */     this.m21 = 0.0F;
/* 1458 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 1467 */     this.m00 *= scalar;
/* 1468 */     this.m01 *= scalar;
/* 1469 */     this.m02 *= scalar;
/*      */     
/* 1471 */     this.m10 *= scalar;
/* 1472 */     this.m11 *= scalar;
/* 1473 */     this.m12 *= scalar;
/*      */     
/* 1475 */     this.m20 *= scalar;
/* 1476 */     this.m21 *= scalar;
/* 1477 */     this.m22 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar, Matrix3f m1) {
/* 1488 */     this.m00 = scalar * m1.m00;
/* 1489 */     this.m01 = scalar * m1.m01;
/* 1490 */     this.m02 = scalar * m1.m02;
/*      */     
/* 1492 */     this.m10 = scalar * m1.m10;
/* 1493 */     this.m11 = scalar * m1.m11;
/* 1494 */     this.m12 = scalar * m1.m12;
/*      */     
/* 1496 */     this.m20 = scalar * m1.m20;
/* 1497 */     this.m21 = scalar * m1.m21;
/* 1498 */     this.m22 = scalar * m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3f m1) {
/* 1513 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1514 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1515 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1517 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1518 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1519 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1521 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1522 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1523 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
/*      */     
/* 1525 */     this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1526 */     this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1527 */     this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3f m1, Matrix3f m2) {
/* 1538 */     if (this != m1 && this != m2) {
/* 1539 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1540 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1541 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1543 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1544 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1545 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1547 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1548 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1549 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1555 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1556 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1557 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1559 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1560 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1561 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1563 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1564 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1565 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1567 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1568 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1569 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeBoth(Matrix3f m1, Matrix3f m2) {
/* 1581 */     if (this != m1 && this != m2) {
/* 1582 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1583 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1584 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1586 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1587 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1588 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1590 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1591 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1592 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1598 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1599 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1600 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1602 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1603 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1604 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1606 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1607 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1608 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1610 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1611 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1612 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeRight(Matrix3f m1, Matrix3f m2) {
/* 1626 */     if (this != m1 && this != m2) {
/* 1627 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1628 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1629 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1631 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1632 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1633 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1635 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1636 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1637 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1643 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1644 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1645 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1647 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1648 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1649 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1651 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1652 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1653 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1655 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1656 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1657 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeLeft(Matrix3f m1, Matrix3f m2) {
/* 1669 */     if (this != m1 && this != m2) {
/* 1670 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1671 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1672 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1674 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1675 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1676 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1678 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1679 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1680 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1686 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1687 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1688 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1690 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1691 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1692 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1694 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1695 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1696 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1698 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1699 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1700 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1709 */     double[] tmp_rot = new double[9];
/* 1710 */     double[] tmp_scale = new double[3];
/* 1711 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1713 */     this.m00 = (float)tmp_rot[0];
/* 1714 */     this.m01 = (float)tmp_rot[1];
/* 1715 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1717 */     this.m10 = (float)tmp_rot[3];
/* 1718 */     this.m11 = (float)tmp_rot[4];
/* 1719 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1721 */     this.m20 = (float)tmp_rot[6];
/* 1722 */     this.m21 = (float)tmp_rot[7];
/* 1723 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize(Matrix3f m1) {
/* 1733 */     double[] tmp = new double[9];
/* 1734 */     double[] tmp_rot = new double[9];
/* 1735 */     double[] tmp_scale = new double[3];
/*      */     
/* 1737 */     tmp[0] = m1.m00;
/* 1738 */     tmp[1] = m1.m01;
/* 1739 */     tmp[2] = m1.m02;
/*      */     
/* 1741 */     tmp[3] = m1.m10;
/* 1742 */     tmp[4] = m1.m11;
/* 1743 */     tmp[5] = m1.m12;
/*      */     
/* 1745 */     tmp[6] = m1.m20;
/* 1746 */     tmp[7] = m1.m21;
/* 1747 */     tmp[8] = m1.m22;
/*      */     
/* 1749 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1751 */     this.m00 = (float)tmp_rot[0];
/* 1752 */     this.m01 = (float)tmp_rot[1];
/* 1753 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1755 */     this.m10 = (float)tmp_rot[3];
/* 1756 */     this.m11 = (float)tmp_rot[4];
/* 1757 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1759 */     this.m20 = (float)tmp_rot[6];
/* 1760 */     this.m21 = (float)tmp_rot[7];
/* 1761 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Matrix3f m1) {
/*      */     try {
/* 1775 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22);
/*      */     }
/*      */     catch (NullPointerException e2) {
/*      */       
/* 1779 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o1) {
/*      */     try {
/* 1795 */       Matrix3f m2 = (Matrix3f)o1;
/* 1796 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22);
/*      */     }
/*      */     catch (ClassCastException|NullPointerException e1) {
/*      */       
/* 1800 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix3f m1, float epsilon) {
/* 1814 */     boolean status = (Math.abs(this.m00 - m1.m00) <= epsilon);
/*      */     
/* 1816 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 1817 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false;
/*      */     
/* 1819 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 1820 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 1821 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false;
/*      */     
/* 1823 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 1824 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 1825 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false;
/*      */     
/* 1827 */     return status;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1842 */     long bits = 1L;
/* 1843 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 1844 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 1845 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 1846 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 1847 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 1848 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 1849 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 1850 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 1851 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 1852 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 1861 */     this.m00 = 0.0F;
/* 1862 */     this.m01 = 0.0F;
/* 1863 */     this.m02 = 0.0F;
/*      */     
/* 1865 */     this.m10 = 0.0F;
/* 1866 */     this.m11 = 0.0F;
/* 1867 */     this.m12 = 0.0F;
/*      */     
/* 1869 */     this.m20 = 0.0F;
/* 1870 */     this.m21 = 0.0F;
/* 1871 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 1880 */     this.m00 = -this.m00;
/* 1881 */     this.m01 = -this.m01;
/* 1882 */     this.m02 = -this.m02;
/*      */     
/* 1884 */     this.m10 = -this.m10;
/* 1885 */     this.m11 = -this.m11;
/* 1886 */     this.m12 = -this.m12;
/*      */     
/* 1888 */     this.m20 = -this.m20;
/* 1889 */     this.m21 = -this.m21;
/* 1890 */     this.m22 = -this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix3f m1) {
/* 1901 */     this.m00 = -m1.m00;
/* 1902 */     this.m01 = -m1.m01;
/* 1903 */     this.m02 = -m1.m02;
/*      */     
/* 1905 */     this.m10 = -m1.m10;
/* 1906 */     this.m11 = -m1.m11;
/* 1907 */     this.m12 = -m1.m12;
/*      */     
/* 1909 */     this.m20 = -m1.m20;
/* 1910 */     this.m21 = -m1.m21;
/* 1911 */     this.m22 = -m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3f t) {
/* 1922 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 1923 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 1924 */     float z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 1925 */     t.set(x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3f t, Tuple3f result) {
/* 1936 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 1937 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 1938 */     result.z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 1939 */     result.x = x;
/* 1940 */     result.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getScaleRotate(double[] scales, double[] rot) {
/* 1948 */     double[] tmp = new double[9];
/* 1949 */     tmp[0] = this.m00;
/* 1950 */     tmp[1] = this.m01;
/* 1951 */     tmp[2] = this.m02;
/* 1952 */     tmp[3] = this.m10;
/* 1953 */     tmp[4] = this.m11;
/* 1954 */     tmp[5] = this.m12;
/* 1955 */     tmp[6] = this.m20;
/* 1956 */     tmp[7] = this.m21;
/* 1957 */     tmp[8] = this.m22;
/* 1958 */     Matrix3d.compute_svd(tmp, scales, rot);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     Matrix3f m1;
/*      */     try {
/* 1974 */       m1 = (Matrix3f)super.clone();
/* 1975 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 1977 */       throw new InternalError();
/*      */     } 
/* 1979 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM00() {
/* 1991 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(float m00) {
/* 2002 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM01() {
/* 2014 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(float m01) {
/* 2025 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM02() {
/* 2036 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(float m02) {
/* 2047 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM10() {
/* 2058 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(float m10) {
/* 2069 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM11() {
/* 2080 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(float m11) {
/* 2091 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM12() {
/* 2102 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float m12) {
/* 2111 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM20() {
/* 2122 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(float m20) {
/* 2133 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM21() {
/* 2144 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(float m21) {
/* 2155 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM22() {
/* 2166 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(float m22) {
/* 2177 */     this.m22 = m22;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Matrix3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */