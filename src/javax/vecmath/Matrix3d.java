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
/*      */ public class Matrix3d
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 6837536777072402710L;
/*      */   public double m00;
/*      */   public double m01;
/*      */   public double m02;
/*      */   public double m10;
/*      */   public double m11;
/*      */   public double m12;
/*      */   public double m20;
/*      */   public double m21;
/*      */   public double m22;
/*      */   private static final double EPS = 1.110223024E-16D;
/*      */   
/*      */   public Matrix3d(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
/*  106 */     this.m00 = m00;
/*  107 */     this.m01 = m01;
/*  108 */     this.m02 = m02;
/*      */     
/*  110 */     this.m10 = m10;
/*  111 */     this.m11 = m11;
/*  112 */     this.m12 = m12;
/*      */     
/*  114 */     this.m20 = m20;
/*  115 */     this.m21 = m21;
/*  116 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(double[] v) {
/*  127 */     this.m00 = v[0];
/*  128 */     this.m01 = v[1];
/*  129 */     this.m02 = v[2];
/*      */     
/*  131 */     this.m10 = v[3];
/*  132 */     this.m11 = v[4];
/*  133 */     this.m12 = v[5];
/*      */     
/*  135 */     this.m20 = v[6];
/*  136 */     this.m21 = v[7];
/*  137 */     this.m22 = v[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(Matrix3d m1) {
/*  148 */     this.m00 = m1.m00;
/*  149 */     this.m01 = m1.m01;
/*  150 */     this.m02 = m1.m02;
/*      */     
/*  152 */     this.m10 = m1.m10;
/*  153 */     this.m11 = m1.m11;
/*  154 */     this.m12 = m1.m12;
/*      */     
/*  156 */     this.m20 = m1.m20;
/*  157 */     this.m21 = m1.m21;
/*  158 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(Matrix3f m1) {
/*  169 */     this.m00 = m1.m00;
/*  170 */     this.m01 = m1.m01;
/*  171 */     this.m02 = m1.m02;
/*      */     
/*  173 */     this.m10 = m1.m10;
/*  174 */     this.m11 = m1.m11;
/*  175 */     this.m12 = m1.m12;
/*      */     
/*  177 */     this.m20 = m1.m20;
/*  178 */     this.m21 = m1.m21;
/*  179 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d() {
/*  188 */     this.m00 = 0.0D;
/*  189 */     this.m01 = 0.0D;
/*  190 */     this.m02 = 0.0D;
/*      */     
/*  192 */     this.m10 = 0.0D;
/*  193 */     this.m11 = 0.0D;
/*  194 */     this.m12 = 0.0D;
/*      */     
/*  196 */     this.m20 = 0.0D;
/*  197 */     this.m21 = 0.0D;
/*  198 */     this.m22 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  208 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
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
/*  219 */     this.m00 = 1.0D;
/*  220 */     this.m01 = 0.0D;
/*  221 */     this.m02 = 0.0D;
/*      */     
/*  223 */     this.m10 = 0.0D;
/*  224 */     this.m11 = 1.0D;
/*  225 */     this.m12 = 0.0D;
/*      */     
/*  227 */     this.m20 = 0.0D;
/*  228 */     this.m21 = 0.0D;
/*  229 */     this.m22 = 1.0D;
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
/*      */   public final void setScale(double scale) {
/*  241 */     double[] tmp_rot = new double[9];
/*  242 */     double[] tmp_scale = new double[3];
/*      */     
/*  244 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  246 */     this.m00 = tmp_rot[0] * scale;
/*  247 */     this.m01 = tmp_rot[1] * scale;
/*  248 */     this.m02 = tmp_rot[2] * scale;
/*      */     
/*  250 */     this.m10 = tmp_rot[3] * scale;
/*  251 */     this.m11 = tmp_rot[4] * scale;
/*  252 */     this.m12 = tmp_rot[5] * scale;
/*      */     
/*  254 */     this.m20 = tmp_rot[6] * scale;
/*  255 */     this.m21 = tmp_rot[7] * scale;
/*  256 */     this.m22 = tmp_rot[8] * scale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int row, int column, double value) {
/*  267 */     switch (row) {
/*      */       
/*      */       case 0:
/*  270 */         switch (column) {
/*      */           
/*      */           case 0:
/*  273 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  276 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  279 */             this.m02 = value;
/*      */             return;
/*      */         } 
/*  282 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  287 */         switch (column) {
/*      */           
/*      */           case 0:
/*  290 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  293 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  296 */             this.m12 = value;
/*      */             return;
/*      */         } 
/*  299 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  305 */         switch (column) {
/*      */           
/*      */           case 0:
/*  308 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  311 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  314 */             this.m22 = value;
/*      */             return;
/*      */         } 
/*  317 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  322 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
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
/*      */   public final double getElement(int row, int column) {
/*  335 */     switch (row) {
/*      */       
/*      */       case 0:
/*  338 */         switch (column) {
/*      */           
/*      */           case 0:
/*  341 */             return this.m00;
/*      */           case 1:
/*  343 */             return this.m01;
/*      */           case 2:
/*  345 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  351 */         switch (column) {
/*      */           
/*      */           case 0:
/*  354 */             return this.m10;
/*      */           case 1:
/*  356 */             return this.m11;
/*      */           case 2:
/*  358 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  365 */         switch (column) {
/*      */           
/*      */           case 0:
/*  368 */             return this.m20;
/*      */           case 1:
/*  370 */             return this.m21;
/*      */           case 2:
/*  372 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  382 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector3d v) {
/*  391 */     if (row == 0) {
/*  392 */       v.x = this.m00;
/*  393 */       v.y = this.m01;
/*  394 */       v.z = this.m02;
/*  395 */     } else if (row == 1) {
/*  396 */       v.x = this.m10;
/*  397 */       v.y = this.m11;
/*  398 */       v.z = this.m12;
/*  399 */     } else if (row == 2) {
/*  400 */       v.x = this.m20;
/*  401 */       v.y = this.m21;
/*  402 */       v.z = this.m22;
/*      */     } else {
/*  404 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, double[] v) {
/*  415 */     if (row == 0) {
/*  416 */       v[0] = this.m00;
/*  417 */       v[1] = this.m01;
/*  418 */       v[2] = this.m02;
/*  419 */     } else if (row == 1) {
/*  420 */       v[0] = this.m10;
/*  421 */       v[1] = this.m11;
/*  422 */       v[2] = this.m12;
/*  423 */     } else if (row == 2) {
/*  424 */       v[0] = this.m20;
/*  425 */       v[1] = this.m21;
/*  426 */       v[2] = this.m22;
/*      */     } else {
/*  428 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
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
/*      */   public final void getColumn(int column, Vector3d v) {
/*  440 */     if (column == 0) {
/*  441 */       v.x = this.m00;
/*  442 */       v.y = this.m10;
/*  443 */       v.z = this.m20;
/*  444 */     } else if (column == 1) {
/*  445 */       v.x = this.m01;
/*  446 */       v.y = this.m11;
/*  447 */       v.z = this.m21;
/*  448 */     } else if (column == 2) {
/*  449 */       v.x = this.m02;
/*  450 */       v.y = this.m12;
/*  451 */       v.z = this.m22;
/*      */     } else {
/*  453 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
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
/*      */   public final void getColumn(int column, double[] v) {
/*  465 */     if (column == 0) {
/*  466 */       v[0] = this.m00;
/*  467 */       v[1] = this.m10;
/*  468 */       v[2] = this.m20;
/*  469 */     } else if (column == 1) {
/*  470 */       v[0] = this.m01;
/*  471 */       v[1] = this.m11;
/*  472 */       v[2] = this.m21;
/*  473 */     } else if (column == 2) {
/*  474 */       v[0] = this.m02;
/*  475 */       v[1] = this.m12;
/*  476 */       v[2] = this.m22;
/*      */     } else {
/*  478 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
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
/*      */   public final void setRow(int row, double x, double y, double z) {
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
/*  513 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector3d v) {
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
/*  544 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, double[] v) {
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
/*  575 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
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
/*      */   public final void setColumn(int column, double x, double y, double z) {
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
/*  608 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector3d v) {
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
/*  639 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, double[] v) {
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
/*  670 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
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
/*      */   public final double getScale() {
/*  684 */     double[] tmp_scale = new double[3];
/*  685 */     double[] tmp_rot = new double[9];
/*  686 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  688 */     return max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar) {
/*  698 */     this.m00 += scalar;
/*  699 */     this.m01 += scalar;
/*  700 */     this.m02 += scalar;
/*      */     
/*  702 */     this.m10 += scalar;
/*  703 */     this.m11 += scalar;
/*  704 */     this.m12 += scalar;
/*      */     
/*  706 */     this.m20 += scalar;
/*  707 */     this.m21 += scalar;
/*  708 */     this.m22 += scalar;
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
/*      */   public final void add(double scalar, Matrix3d m1) {
/*  720 */     m1.m00 += scalar;
/*  721 */     m1.m01 += scalar;
/*  722 */     m1.m02 += scalar;
/*      */     
/*  724 */     m1.m10 += scalar;
/*  725 */     m1.m11 += scalar;
/*  726 */     m1.m12 += scalar;
/*      */     
/*  728 */     m1.m20 += scalar;
/*  729 */     m1.m21 += scalar;
/*  730 */     m1.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3d m1, Matrix3d m2) {
/*  740 */     m1.m00 += m2.m00;
/*  741 */     m1.m01 += m2.m01;
/*  742 */     m1.m02 += m2.m02;
/*      */     
/*  744 */     m1.m10 += m2.m10;
/*  745 */     m1.m11 += m2.m11;
/*  746 */     m1.m12 += m2.m12;
/*      */     
/*  748 */     m1.m20 += m2.m20;
/*  749 */     m1.m21 += m2.m21;
/*  750 */     m1.m22 += m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3d m1) {
/*  759 */     this.m00 += m1.m00;
/*  760 */     this.m01 += m1.m01;
/*  761 */     this.m02 += m1.m02;
/*      */     
/*  763 */     this.m10 += m1.m10;
/*  764 */     this.m11 += m1.m11;
/*  765 */     this.m12 += m1.m12;
/*      */     
/*  767 */     this.m20 += m1.m20;
/*  768 */     this.m21 += m1.m21;
/*  769 */     this.m22 += m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3d m1, Matrix3d m2) {
/*  780 */     m1.m00 -= m2.m00;
/*  781 */     m1.m01 -= m2.m01;
/*  782 */     m1.m02 -= m2.m02;
/*      */     
/*  784 */     m1.m10 -= m2.m10;
/*  785 */     m1.m11 -= m2.m11;
/*  786 */     m1.m12 -= m2.m12;
/*      */     
/*  788 */     m1.m20 -= m2.m20;
/*  789 */     m1.m21 -= m2.m21;
/*  790 */     m1.m22 -= m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3d m1) {
/*  800 */     this.m00 -= m1.m00;
/*  801 */     this.m01 -= m1.m01;
/*  802 */     this.m02 -= m1.m02;
/*      */     
/*  804 */     this.m10 -= m1.m10;
/*  805 */     this.m11 -= m1.m11;
/*  806 */     this.m12 -= m1.m12;
/*      */     
/*  808 */     this.m20 -= m1.m20;
/*  809 */     this.m21 -= m1.m21;
/*  810 */     this.m22 -= m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/*  820 */     double temp = this.m10;
/*  821 */     this.m10 = this.m01;
/*  822 */     this.m01 = temp;
/*      */     
/*  824 */     temp = this.m20;
/*  825 */     this.m20 = this.m02;
/*  826 */     this.m02 = temp;
/*      */     
/*  828 */     temp = this.m21;
/*  829 */     this.m21 = this.m12;
/*  830 */     this.m12 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix3d m1) {
/*  839 */     if (this != m1) {
/*  840 */       this.m00 = m1.m00;
/*  841 */       this.m01 = m1.m10;
/*  842 */       this.m02 = m1.m20;
/*      */       
/*  844 */       this.m10 = m1.m01;
/*  845 */       this.m11 = m1.m11;
/*  846 */       this.m12 = m1.m21;
/*      */       
/*  848 */       this.m20 = m1.m02;
/*  849 */       this.m21 = m1.m12;
/*  850 */       this.m22 = m1.m22;
/*      */     } else {
/*  852 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/*  862 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/*  863 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  864 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  866 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  867 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/*  868 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  870 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  871 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  872 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/*  882 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/*  884 */     if (mag < 1.110223024E-16D) {
/*  885 */       this.m00 = 1.0D;
/*  886 */       this.m01 = 0.0D;
/*  887 */       this.m02 = 0.0D;
/*      */       
/*  889 */       this.m10 = 0.0D;
/*  890 */       this.m11 = 1.0D;
/*  891 */       this.m12 = 0.0D;
/*      */       
/*  893 */       this.m20 = 0.0D;
/*  894 */       this.m21 = 0.0D;
/*  895 */       this.m22 = 1.0D;
/*      */     } else {
/*  897 */       mag = 1.0D / mag;
/*  898 */       double ax = a1.x * mag;
/*  899 */       double ay = a1.y * mag;
/*  900 */       double az = a1.z * mag;
/*      */       
/*  902 */       double sinTheta = Math.sin(a1.angle);
/*  903 */       double cosTheta = Math.cos(a1.angle);
/*  904 */       double t = 1.0D - cosTheta;
/*      */       
/*  906 */       double xz = ax * az;
/*  907 */       double xy = ax * ay;
/*  908 */       double yz = ay * az;
/*      */       
/*  910 */       this.m00 = t * ax * ax + cosTheta;
/*  911 */       this.m01 = t * xy - sinTheta * az;
/*  912 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  914 */       this.m10 = t * xy + sinTheta * az;
/*  915 */       this.m11 = t * ay * ay + cosTheta;
/*  916 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  918 */       this.m20 = t * xz - sinTheta * ay;
/*  919 */       this.m21 = t * yz + sinTheta * ax;
/*  920 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/*  931 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/*  932 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  933 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  935 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  936 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/*  937 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  939 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  940 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  941 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/*  951 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*  952 */     if (mag < 1.110223024E-16D) {
/*  953 */       this.m00 = 1.0D;
/*  954 */       this.m01 = 0.0D;
/*  955 */       this.m02 = 0.0D;
/*      */       
/*  957 */       this.m10 = 0.0D;
/*  958 */       this.m11 = 1.0D;
/*  959 */       this.m12 = 0.0D;
/*      */       
/*  961 */       this.m20 = 0.0D;
/*  962 */       this.m21 = 0.0D;
/*  963 */       this.m22 = 1.0D;
/*      */     } else {
/*  965 */       mag = 1.0D / mag;
/*  966 */       double ax = a1.x * mag;
/*  967 */       double ay = a1.y * mag;
/*  968 */       double az = a1.z * mag;
/*  969 */       double sinTheta = Math.sin(a1.angle);
/*  970 */       double cosTheta = Math.cos(a1.angle);
/*  971 */       double t = 1.0D - cosTheta;
/*      */       
/*  973 */       double xz = ax * az;
/*  974 */       double xy = ax * ay;
/*  975 */       double yz = ay * az;
/*      */       
/*  977 */       this.m00 = t * ax * ax + cosTheta;
/*  978 */       this.m01 = t * xy - sinTheta * az;
/*  979 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  981 */       this.m10 = t * xy + sinTheta * az;
/*  982 */       this.m11 = t * ay * ay + cosTheta;
/*  983 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  985 */       this.m20 = t * xz - sinTheta * ay;
/*  986 */       this.m21 = t * yz + sinTheta * ax;
/*  987 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f m1) {
/*  998 */     this.m00 = m1.m00;
/*  999 */     this.m01 = m1.m01;
/* 1000 */     this.m02 = m1.m02;
/*      */     
/* 1002 */     this.m10 = m1.m10;
/* 1003 */     this.m11 = m1.m11;
/* 1004 */     this.m12 = m1.m12;
/*      */     
/* 1006 */     this.m20 = m1.m20;
/* 1007 */     this.m21 = m1.m21;
/* 1008 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/* 1018 */     this.m00 = m1.m00;
/* 1019 */     this.m01 = m1.m01;
/* 1020 */     this.m02 = m1.m02;
/*      */     
/* 1022 */     this.m10 = m1.m10;
/* 1023 */     this.m11 = m1.m11;
/* 1024 */     this.m12 = m1.m12;
/*      */     
/* 1026 */     this.m20 = m1.m20;
/* 1027 */     this.m21 = m1.m21;
/* 1028 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double[] m) {
/* 1039 */     this.m00 = m[0];
/* 1040 */     this.m01 = m[1];
/* 1041 */     this.m02 = m[2];
/*      */     
/* 1043 */     this.m10 = m[3];
/* 1044 */     this.m11 = m[4];
/* 1045 */     this.m12 = m[5];
/*      */     
/* 1047 */     this.m20 = m[6];
/* 1048 */     this.m21 = m[7];
/* 1049 */     this.m22 = m[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix3d m1) {
/* 1060 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1068 */     invertGeneral(this);
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
/*      */   private final void invertGeneral(Matrix3d m1) {
/* 1080 */     double[] result = new double[9];
/* 1081 */     int[] row_perm = new int[3];
/*      */     
/* 1083 */     double[] tmp = new double[9];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1089 */     tmp[0] = m1.m00;
/* 1090 */     tmp[1] = m1.m01;
/* 1091 */     tmp[2] = m1.m02;
/*      */     
/* 1093 */     tmp[3] = m1.m10;
/* 1094 */     tmp[4] = m1.m11;
/* 1095 */     tmp[5] = m1.m12;
/*      */     
/* 1097 */     tmp[6] = m1.m20;
/* 1098 */     tmp[7] = m1.m21;
/* 1099 */     tmp[8] = m1.m22;
/*      */ 
/*      */ 
/*      */     
/* 1103 */     if (!luDecomposition(tmp, row_perm))
/*      */     {
/* 1105 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3d12"));
/*      */     }
/*      */ 
/*      */     
/* 1109 */     for (int i = 0; i < 9; ) { result[i] = 0.0D; i++; }
/* 1110 */      result[0] = 1.0D; result[4] = 1.0D; result[8] = 1.0D;
/* 1111 */     luBacksubstitution(tmp, row_perm, result);
/*      */     
/* 1113 */     this.m00 = result[0];
/* 1114 */     this.m01 = result[1];
/* 1115 */     this.m02 = result[2];
/*      */     
/* 1117 */     this.m10 = result[3];
/* 1118 */     this.m11 = result[4];
/* 1119 */     this.m12 = result[5];
/*      */     
/* 1121 */     this.m20 = result[6];
/* 1122 */     this.m21 = result[7];
/* 1123 */     this.m22 = result[8];
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
/* 1150 */     double[] row_scale = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1158 */     int ptr = 0;
/* 1159 */     int rs = 0;
/*      */ 
/*      */     
/* 1162 */     int i = 3;
/* 1163 */     while (i-- != 0) {
/* 1164 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1167 */       int k = 3;
/* 1168 */       while (k-- != 0) {
/* 1169 */         double temp = matrix0[ptr++];
/* 1170 */         temp = Math.abs(temp);
/* 1171 */         if (temp > big) {
/* 1172 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1177 */       if (big == 0.0D) {
/* 1178 */         return false;
/*      */       }
/* 1180 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1188 */     int mtx = 0;
/*      */ 
/*      */     
/* 1191 */     for (int j = 0; j < 3; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1197 */       for (k = 0; k < j; k++) {
/* 1198 */         int target = mtx + 3 * k + j;
/* 1199 */         double sum = matrix0[target];
/* 1200 */         int m = k;
/* 1201 */         int p1 = mtx + 3 * k;
/* 1202 */         int p2 = mtx + j;
/* 1203 */         while (m-- != 0) {
/* 1204 */           sum -= matrix0[p1] * matrix0[p2];
/* 1205 */           p1++;
/* 1206 */           p2 += 3;
/*      */         } 
/* 1208 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1213 */       double big = 0.0D;
/* 1214 */       int imax = -1;
/* 1215 */       for (k = j; k < 3; k++) {
/* 1216 */         int target = mtx + 3 * k + j;
/* 1217 */         double sum = matrix0[target];
/* 1218 */         int m = j;
/* 1219 */         int p1 = mtx + 3 * k;
/* 1220 */         int p2 = mtx + j;
/* 1221 */         while (m-- != 0) {
/* 1222 */           sum -= matrix0[p1] * matrix0[p2];
/* 1223 */           p1++;
/* 1224 */           p2 += 3;
/*      */         } 
/* 1226 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1229 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1230 */           big = temp;
/* 1231 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1235 */       if (imax < 0) {
/* 1236 */         throw new RuntimeException(VecMathI18N.getString("Matrix3d13"));
/*      */       }
/*      */ 
/*      */       
/* 1240 */       if (j != imax) {
/*      */         
/* 1242 */         int m = 3;
/* 1243 */         int p1 = mtx + 3 * imax;
/* 1244 */         int p2 = mtx + 3 * j;
/* 1245 */         while (m-- != 0) {
/* 1246 */           double temp = matrix0[p1];
/* 1247 */           matrix0[p1++] = matrix0[p2];
/* 1248 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1252 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1256 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1259 */       if (matrix0[mtx + 3 * j + j] == 0.0D) {
/* 1260 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1264 */       if (j != 2) {
/* 1265 */         double temp = 1.0D / matrix0[mtx + 3 * j + j];
/* 1266 */         int target = mtx + 3 * (j + 1) + j;
/* 1267 */         k = 2 - j;
/* 1268 */         while (k-- != 0) {
/* 1269 */           matrix0[target] = matrix0[target] * temp;
/* 1270 */           target += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1276 */     return true;
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
/* 1306 */     int rp = 0;
/*      */ 
/*      */     
/* 1309 */     for (int k = 0; k < 3; k++) {
/*      */       
/* 1311 */       int cv = k;
/* 1312 */       int ii = -1;
/*      */ 
/*      */       
/* 1315 */       for (int i = 0; i < 3; i++) {
/*      */ 
/*      */         
/* 1318 */         int ip = row_perm[rp + i];
/* 1319 */         double sum = matrix2[cv + 3 * ip];
/* 1320 */         matrix2[cv + 3 * ip] = matrix2[cv + 3 * i];
/* 1321 */         if (ii >= 0) {
/*      */           
/* 1323 */           int m = i * 3;
/* 1324 */           for (int j = ii; j <= i - 1; j++) {
/* 1325 */             sum -= matrix1[m + j] * matrix2[cv + 3 * j];
/*      */           }
/*      */         }
/* 1328 */         else if (sum != 0.0D) {
/* 1329 */           ii = i;
/*      */         } 
/* 1331 */         matrix2[cv + 3 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1336 */       int rv = 6;
/* 1337 */       matrix2[cv + 6] = matrix2[cv + 6] / matrix1[rv + 2];
/*      */       
/* 1339 */       rv -= 3;
/* 1340 */       matrix2[cv + 3] = (matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 1];
/*      */ 
/*      */       
/* 1343 */       rv -= 3;
/* 1344 */       matrix2[cv + 0] = (matrix2[cv + 0] - matrix1[rv + 1] * matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 0];
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
/*      */   public final double determinant() {
/* 1359 */     double total = this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
/*      */ 
/*      */     
/* 1362 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double scale) {
/* 1372 */     this.m00 = scale;
/* 1373 */     this.m01 = 0.0D;
/* 1374 */     this.m02 = 0.0D;
/*      */     
/* 1376 */     this.m10 = 0.0D;
/* 1377 */     this.m11 = scale;
/* 1378 */     this.m12 = 0.0D;
/*      */     
/* 1380 */     this.m20 = 0.0D;
/* 1381 */     this.m21 = 0.0D;
/* 1382 */     this.m22 = scale;
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
/*      */   public final void rotX(double angle) {
/* 1394 */     double sinAngle = Math.sin(angle);
/* 1395 */     double cosAngle = Math.cos(angle);
/*      */     
/* 1397 */     this.m00 = 1.0D;
/* 1398 */     this.m01 = 0.0D;
/* 1399 */     this.m02 = 0.0D;
/*      */     
/* 1401 */     this.m10 = 0.0D;
/* 1402 */     this.m11 = cosAngle;
/* 1403 */     this.m12 = -sinAngle;
/*      */     
/* 1405 */     this.m20 = 0.0D;
/* 1406 */     this.m21 = sinAngle;
/* 1407 */     this.m22 = cosAngle;
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
/*      */   public final void rotY(double angle) {
/* 1419 */     double sinAngle = Math.sin(angle);
/* 1420 */     double cosAngle = Math.cos(angle);
/*      */     
/* 1422 */     this.m00 = cosAngle;
/* 1423 */     this.m01 = 0.0D;
/* 1424 */     this.m02 = sinAngle;
/*      */     
/* 1426 */     this.m10 = 0.0D;
/* 1427 */     this.m11 = 1.0D;
/* 1428 */     this.m12 = 0.0D;
/*      */     
/* 1430 */     this.m20 = -sinAngle;
/* 1431 */     this.m21 = 0.0D;
/* 1432 */     this.m22 = cosAngle;
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
/*      */   public final void rotZ(double angle) {
/* 1444 */     double sinAngle = Math.sin(angle);
/* 1445 */     double cosAngle = Math.cos(angle);
/*      */     
/* 1447 */     this.m00 = cosAngle;
/* 1448 */     this.m01 = -sinAngle;
/* 1449 */     this.m02 = 0.0D;
/*      */     
/* 1451 */     this.m10 = sinAngle;
/* 1452 */     this.m11 = cosAngle;
/* 1453 */     this.m12 = 0.0D;
/*      */     
/* 1455 */     this.m20 = 0.0D;
/* 1456 */     this.m21 = 0.0D;
/* 1457 */     this.m22 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar) {
/* 1466 */     this.m00 *= scalar;
/* 1467 */     this.m01 *= scalar;
/* 1468 */     this.m02 *= scalar;
/*      */     
/* 1470 */     this.m10 *= scalar;
/* 1471 */     this.m11 *= scalar;
/* 1472 */     this.m12 *= scalar;
/*      */     
/* 1474 */     this.m20 *= scalar;
/* 1475 */     this.m21 *= scalar;
/* 1476 */     this.m22 *= scalar;
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
/*      */   public final void mul(double scalar, Matrix3d m1) {
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
/*      */   public final void mul(Matrix3d m1) {
/* 1513 */     double m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1514 */     double m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1515 */     double m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1517 */     double m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1518 */     double m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1519 */     double m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1521 */     double m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1522 */     double m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1523 */     double m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
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
/*      */   public final void mul(Matrix3d m1, Matrix3d m2) {
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
/* 1555 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1556 */       double m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1557 */       double m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1559 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1560 */       double m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1561 */       double m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1563 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1564 */       double m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1565 */       double m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
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
/*      */   public final void mulNormalize(Matrix3d m1) {
/* 1581 */     double[] tmp = new double[9];
/* 1582 */     double[] tmp_rot = new double[9];
/* 1583 */     double[] tmp_scale = new double[3];
/*      */     
/* 1585 */     tmp[0] = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1586 */     tmp[1] = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1587 */     tmp[2] = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1589 */     tmp[3] = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1590 */     tmp[4] = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1591 */     tmp[5] = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1593 */     tmp[6] = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1594 */     tmp[7] = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1595 */     tmp[8] = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
/*      */     
/* 1597 */     compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1599 */     this.m00 = tmp_rot[0];
/* 1600 */     this.m01 = tmp_rot[1];
/* 1601 */     this.m02 = tmp_rot[2];
/*      */     
/* 1603 */     this.m10 = tmp_rot[3];
/* 1604 */     this.m11 = tmp_rot[4];
/* 1605 */     this.m12 = tmp_rot[5];
/*      */     
/* 1607 */     this.m20 = tmp_rot[6];
/* 1608 */     this.m21 = tmp_rot[7];
/* 1609 */     this.m22 = tmp_rot[8];
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
/*      */   public final void mulNormalize(Matrix3d m1, Matrix3d m2) {
/* 1623 */     double[] tmp = new double[9];
/* 1624 */     double[] tmp_rot = new double[9];
/* 1625 */     double[] tmp_scale = new double[3];
/*      */     
/* 1627 */     tmp[0] = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1628 */     tmp[1] = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1629 */     tmp[2] = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */     
/* 1631 */     tmp[3] = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1632 */     tmp[4] = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1633 */     tmp[5] = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */     
/* 1635 */     tmp[6] = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1636 */     tmp[7] = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1637 */     tmp[8] = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/* 1639 */     compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1641 */     this.m00 = tmp_rot[0];
/* 1642 */     this.m01 = tmp_rot[1];
/* 1643 */     this.m02 = tmp_rot[2];
/*      */     
/* 1645 */     this.m10 = tmp_rot[3];
/* 1646 */     this.m11 = tmp_rot[4];
/* 1647 */     this.m12 = tmp_rot[5];
/*      */     
/* 1649 */     this.m20 = tmp_rot[6];
/* 1650 */     this.m21 = tmp_rot[7];
/* 1651 */     this.m22 = tmp_rot[8];
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
/*      */   public final void mulTransposeBoth(Matrix3d m1, Matrix3d m2) {
/* 1663 */     if (this != m1 && this != m2) {
/* 1664 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1665 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1666 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1668 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1669 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1670 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1672 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1673 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1674 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1680 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1681 */       double m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1682 */       double m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1684 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1685 */       double m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1686 */       double m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1688 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1689 */       double m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1690 */       double m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1692 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1693 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1694 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/*      */   public final void mulTransposeRight(Matrix3d m1, Matrix3d m2) {
/* 1707 */     if (this != m1 && this != m2) {
/* 1708 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1709 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1710 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1712 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1713 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1714 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1716 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1717 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1718 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1724 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1725 */       double m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1726 */       double m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1728 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1729 */       double m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1730 */       double m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1732 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1733 */       double m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1734 */       double m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1736 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1737 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1738 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/*      */   public final void mulTransposeLeft(Matrix3d m1, Matrix3d m2) {
/* 1750 */     if (this != m1 && this != m2) {
/* 1751 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1752 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1753 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1755 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1756 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1757 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1759 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1760 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1761 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1767 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1768 */       double m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1769 */       double m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1771 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1772 */       double m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1773 */       double m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1775 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1776 */       double m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1777 */       double m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1779 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1780 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1781 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1791 */     double[] tmp_rot = new double[9];
/* 1792 */     double[] tmp_scale = new double[3];
/*      */     
/* 1794 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1796 */     this.m00 = tmp_rot[0];
/* 1797 */     this.m01 = tmp_rot[1];
/* 1798 */     this.m02 = tmp_rot[2];
/*      */     
/* 1800 */     this.m10 = tmp_rot[3];
/* 1801 */     this.m11 = tmp_rot[4];
/* 1802 */     this.m12 = tmp_rot[5];
/*      */     
/* 1804 */     this.m20 = tmp_rot[6];
/* 1805 */     this.m21 = tmp_rot[7];
/* 1806 */     this.m22 = tmp_rot[8];
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
/*      */   public final void normalize(Matrix3d m1) {
/* 1818 */     double[] tmp = new double[9];
/* 1819 */     double[] tmp_rot = new double[9];
/* 1820 */     double[] tmp_scale = new double[3];
/*      */     
/* 1822 */     tmp[0] = m1.m00;
/* 1823 */     tmp[1] = m1.m01;
/* 1824 */     tmp[2] = m1.m02;
/*      */     
/* 1826 */     tmp[3] = m1.m10;
/* 1827 */     tmp[4] = m1.m11;
/* 1828 */     tmp[5] = m1.m12;
/*      */     
/* 1830 */     tmp[6] = m1.m20;
/* 1831 */     tmp[7] = m1.m21;
/* 1832 */     tmp[8] = m1.m22;
/*      */     
/* 1834 */     compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1836 */     this.m00 = tmp_rot[0];
/* 1837 */     this.m01 = tmp_rot[1];
/* 1838 */     this.m02 = tmp_rot[2];
/*      */     
/* 1840 */     this.m10 = tmp_rot[3];
/* 1841 */     this.m11 = tmp_rot[4];
/* 1842 */     this.m12 = tmp_rot[5];
/*      */     
/* 1844 */     this.m20 = tmp_rot[6];
/* 1845 */     this.m21 = tmp_rot[7];
/* 1846 */     this.m22 = tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP() {
/* 1856 */     double mag = 1.0D / Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
/* 1857 */     this.m00 *= mag;
/* 1858 */     this.m10 *= mag;
/* 1859 */     this.m20 *= mag;
/*      */     
/* 1861 */     mag = 1.0D / Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
/* 1862 */     this.m01 *= mag;
/* 1863 */     this.m11 *= mag;
/* 1864 */     this.m21 *= mag;
/*      */     
/* 1866 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1867 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1868 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP(Matrix3d m1) {
/* 1879 */     double mag = 1.0D / Math.sqrt(m1.m00 * m1.m00 + m1.m10 * m1.m10 + m1.m20 * m1.m20);
/* 1880 */     m1.m00 *= mag;
/* 1881 */     m1.m10 *= mag;
/* 1882 */     m1.m20 *= mag;
/*      */     
/* 1884 */     mag = 1.0D / Math.sqrt(m1.m01 * m1.m01 + m1.m11 * m1.m11 + m1.m21 * m1.m21);
/* 1885 */     m1.m01 *= mag;
/* 1886 */     m1.m11 *= mag;
/* 1887 */     m1.m21 *= mag;
/*      */     
/* 1889 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1890 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1891 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Matrix3d m1) {
/*      */     try {
/* 1903 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22);
/*      */     }
/*      */     catch (NullPointerException e2) {
/*      */       
/* 1907 */       return false;
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
/*      */   public boolean equals(Object t1) {
/*      */     
/* 1922 */     try { Matrix3d m2 = (Matrix3d)t1;
/* 1923 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22); }
/*      */     
/*      */     catch (ClassCastException e1)
/*      */     
/* 1927 */     { return false; }
/* 1928 */     catch (NullPointerException e2) { return false; }
/*      */   
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
/*      */   public boolean epsilonEquals(Matrix3d m1, double epsilon) {
/* 1945 */     double diff = this.m00 - m1.m00;
/* 1946 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1948 */     diff = this.m01 - m1.m01;
/* 1949 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1951 */     diff = this.m02 - m1.m02;
/* 1952 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1954 */     diff = this.m10 - m1.m10;
/* 1955 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1957 */     diff = this.m11 - m1.m11;
/* 1958 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1960 */     diff = this.m12 - m1.m12;
/* 1961 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1963 */     diff = this.m20 - m1.m20;
/* 1964 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1966 */     diff = this.m21 - m1.m21;
/* 1967 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1969 */     diff = this.m22 - m1.m22;
/* 1970 */     return (((diff < 0.0D) ? -diff : diff) <= epsilon);
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
/*      */   public int hashCode() {
/* 1984 */     long bits = 1L;
/* 1985 */     bits = VecMathUtil.hashDoubleBits(bits, this.m00);
/* 1986 */     bits = VecMathUtil.hashDoubleBits(bits, this.m01);
/* 1987 */     bits = VecMathUtil.hashDoubleBits(bits, this.m02);
/* 1988 */     bits = VecMathUtil.hashDoubleBits(bits, this.m10);
/* 1989 */     bits = VecMathUtil.hashDoubleBits(bits, this.m11);
/* 1990 */     bits = VecMathUtil.hashDoubleBits(bits, this.m12);
/* 1991 */     bits = VecMathUtil.hashDoubleBits(bits, this.m20);
/* 1992 */     bits = VecMathUtil.hashDoubleBits(bits, this.m21);
/* 1993 */     bits = VecMathUtil.hashDoubleBits(bits, this.m22);
/* 1994 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 2003 */     this.m00 = 0.0D;
/* 2004 */     this.m01 = 0.0D;
/* 2005 */     this.m02 = 0.0D;
/*      */     
/* 2007 */     this.m10 = 0.0D;
/* 2008 */     this.m11 = 0.0D;
/* 2009 */     this.m12 = 0.0D;
/*      */     
/* 2011 */     this.m20 = 0.0D;
/* 2012 */     this.m21 = 0.0D;
/* 2013 */     this.m22 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 2022 */     this.m00 = -this.m00;
/* 2023 */     this.m01 = -this.m01;
/* 2024 */     this.m02 = -this.m02;
/*      */     
/* 2026 */     this.m10 = -this.m10;
/* 2027 */     this.m11 = -this.m11;
/* 2028 */     this.m12 = -this.m12;
/*      */     
/* 2030 */     this.m20 = -this.m20;
/* 2031 */     this.m21 = -this.m21;
/* 2032 */     this.m22 = -this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix3d m1) {
/* 2043 */     this.m00 = -m1.m00;
/* 2044 */     this.m01 = -m1.m01;
/* 2045 */     this.m02 = -m1.m02;
/*      */     
/* 2047 */     this.m10 = -m1.m10;
/* 2048 */     this.m11 = -m1.m11;
/* 2049 */     this.m12 = -m1.m12;
/*      */     
/* 2051 */     this.m20 = -m1.m20;
/* 2052 */     this.m21 = -m1.m21;
/* 2053 */     this.m22 = -m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3d t) {
/* 2064 */     double x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2065 */     double y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2066 */     double z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2067 */     t.set(x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3d t, Tuple3d result) {
/* 2078 */     double x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2079 */     double y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2080 */     result.z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2081 */     result.x = x;
/* 2082 */     result.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void getScaleRotate(double[] scales, double[] rots) {
/* 2090 */     double[] tmp = new double[9];
/*      */     
/* 2092 */     tmp[0] = this.m00;
/* 2093 */     tmp[1] = this.m01;
/* 2094 */     tmp[2] = this.m02;
/*      */     
/* 2096 */     tmp[3] = this.m10;
/* 2097 */     tmp[4] = this.m11;
/* 2098 */     tmp[5] = this.m12;
/*      */     
/* 2100 */     tmp[6] = this.m20;
/* 2101 */     tmp[7] = this.m21;
/* 2102 */     tmp[8] = this.m22;
/* 2103 */     compute_svd(tmp, scales, rots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void compute_svd(double[] m, double[] outScale, double[] outRot) {
/* 2111 */     double[] u1 = new double[9];
/* 2112 */     double[] v1 = new double[9];
/* 2113 */     double[] t1 = new double[9];
/* 2114 */     double[] t2 = new double[9];
/*      */     
/* 2116 */     double[] tmp = t1;
/* 2117 */     double[] single_values = t2;
/*      */     
/* 2119 */     double[] rot = new double[9];
/* 2120 */     double[] e = new double[3];
/* 2121 */     double[] scales = new double[3];
/*      */     
/* 2123 */     int negCnt = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */     
/* 2130 */     for (i = 0; i < 9; i++) {
/* 2131 */       rot[i] = m[i];
/*      */     }
/*      */ 
/*      */     
/* 2135 */     if (m[3] * m[3] < 1.110223024E-16D) {
/* 2136 */       u1[0] = 1.0D; u1[1] = 0.0D; u1[2] = 0.0D;
/* 2137 */       u1[3] = 0.0D; u1[4] = 1.0D; u1[5] = 0.0D;
/* 2138 */       u1[6] = 0.0D; u1[7] = 0.0D; u1[8] = 1.0D;
/* 2139 */     } else if (m[0] * m[0] < 1.110223024E-16D) {
/* 2140 */       tmp[0] = m[0];
/* 2141 */       tmp[1] = m[1];
/* 2142 */       tmp[2] = m[2];
/* 2143 */       m[0] = m[3];
/* 2144 */       m[1] = m[4];
/* 2145 */       m[2] = m[5];
/*      */       
/* 2147 */       m[3] = -tmp[0];
/* 2148 */       m[4] = -tmp[1];
/* 2149 */       m[5] = -tmp[2];
/*      */       
/* 2151 */       u1[0] = 0.0D; u1[1] = 1.0D; u1[2] = 0.0D;
/* 2152 */       u1[3] = -1.0D; u1[4] = 0.0D; u1[5] = 0.0D;
/* 2153 */       u1[6] = 0.0D; u1[7] = 0.0D; u1[8] = 1.0D;
/*      */     } else {
/* 2155 */       double g = 1.0D / Math.sqrt(m[0] * m[0] + m[3] * m[3]);
/* 2156 */       double c1 = m[0] * g;
/* 2157 */       double s1 = m[3] * g;
/* 2158 */       tmp[0] = c1 * m[0] + s1 * m[3];
/* 2159 */       tmp[1] = c1 * m[1] + s1 * m[4];
/* 2160 */       tmp[2] = c1 * m[2] + s1 * m[5];
/*      */       
/* 2162 */       m[3] = -s1 * m[0] + c1 * m[3];
/* 2163 */       m[4] = -s1 * m[1] + c1 * m[4];
/* 2164 */       m[5] = -s1 * m[2] + c1 * m[5];
/*      */       
/* 2166 */       m[0] = tmp[0];
/* 2167 */       m[1] = tmp[1];
/* 2168 */       m[2] = tmp[2];
/* 2169 */       u1[0] = c1; u1[1] = s1; u1[2] = 0.0D;
/* 2170 */       u1[3] = -s1; u1[4] = c1; u1[5] = 0.0D;
/* 2171 */       u1[6] = 0.0D; u1[7] = 0.0D; u1[8] = 1.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2176 */     if (m[6] * m[6] >= 1.110223024E-16D) {
/* 2177 */       if (m[0] * m[0] < 1.110223024E-16D) {
/* 2178 */         tmp[0] = m[0];
/* 2179 */         tmp[1] = m[1];
/* 2180 */         tmp[2] = m[2];
/* 2181 */         m[0] = m[6];
/* 2182 */         m[1] = m[7];
/* 2183 */         m[2] = m[8];
/*      */         
/* 2185 */         m[6] = -tmp[0];
/* 2186 */         m[7] = -tmp[1];
/* 2187 */         m[8] = -tmp[2];
/*      */         
/* 2189 */         tmp[0] = u1[0];
/* 2190 */         tmp[1] = u1[1];
/* 2191 */         tmp[2] = u1[2];
/* 2192 */         u1[0] = u1[6];
/* 2193 */         u1[1] = u1[7];
/* 2194 */         u1[2] = u1[8];
/*      */         
/* 2196 */         u1[6] = -tmp[0];
/* 2197 */         u1[7] = -tmp[1];
/* 2198 */         u1[8] = -tmp[2];
/*      */       } else {
/* 2200 */         double g = 1.0D / Math.sqrt(m[0] * m[0] + m[6] * m[6]);
/* 2201 */         double c2 = m[0] * g;
/* 2202 */         double s2 = m[6] * g;
/* 2203 */         tmp[0] = c2 * m[0] + s2 * m[6];
/* 2204 */         tmp[1] = c2 * m[1] + s2 * m[7];
/* 2205 */         tmp[2] = c2 * m[2] + s2 * m[8];
/*      */         
/* 2207 */         m[6] = -s2 * m[0] + c2 * m[6];
/* 2208 */         m[7] = -s2 * m[1] + c2 * m[7];
/* 2209 */         m[8] = -s2 * m[2] + c2 * m[8];
/* 2210 */         m[0] = tmp[0];
/* 2211 */         m[1] = tmp[1];
/* 2212 */         m[2] = tmp[2];
/*      */         
/* 2214 */         tmp[0] = c2 * u1[0];
/* 2215 */         tmp[1] = c2 * u1[1];
/* 2216 */         u1[2] = s2;
/*      */         
/* 2218 */         tmp[6] = -u1[0] * s2;
/* 2219 */         tmp[7] = -u1[1] * s2;
/* 2220 */         u1[8] = c2;
/* 2221 */         u1[0] = tmp[0];
/* 2222 */         u1[1] = tmp[1];
/* 2223 */         u1[6] = tmp[6];
/* 2224 */         u1[7] = tmp[7];
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2229 */     if (m[2] * m[2] < 1.110223024E-16D) {
/* 2230 */       v1[0] = 1.0D; v1[1] = 0.0D; v1[2] = 0.0D;
/* 2231 */       v1[3] = 0.0D; v1[4] = 1.0D; v1[5] = 0.0D;
/* 2232 */       v1[6] = 0.0D; v1[7] = 0.0D; v1[8] = 1.0D;
/* 2233 */     } else if (m[1] * m[1] < 1.110223024E-16D) {
/* 2234 */       tmp[2] = m[2];
/* 2235 */       tmp[5] = m[5];
/* 2236 */       tmp[8] = m[8];
/* 2237 */       m[2] = -m[1];
/* 2238 */       m[5] = -m[4];
/* 2239 */       m[8] = -m[7];
/*      */       
/* 2241 */       m[1] = tmp[2];
/* 2242 */       m[4] = tmp[5];
/* 2243 */       m[7] = tmp[8];
/*      */       
/* 2245 */       v1[0] = 1.0D; v1[1] = 0.0D; v1[2] = 0.0D;
/* 2246 */       v1[3] = 0.0D; v1[4] = 0.0D; v1[5] = -1.0D;
/* 2247 */       v1[6] = 0.0D; v1[7] = 1.0D; v1[8] = 0.0D;
/*      */     } else {
/* 2249 */       double g = 1.0D / Math.sqrt(m[1] * m[1] + m[2] * m[2]);
/* 2250 */       double c3 = m[1] * g;
/* 2251 */       double s3 = m[2] * g;
/* 2252 */       tmp[1] = c3 * m[1] + s3 * m[2];
/* 2253 */       m[2] = -s3 * m[1] + c3 * m[2];
/* 2254 */       m[1] = tmp[1];
/*      */       
/* 2256 */       tmp[4] = c3 * m[4] + s3 * m[5];
/* 2257 */       m[5] = -s3 * m[4] + c3 * m[5];
/* 2258 */       m[4] = tmp[4];
/*      */       
/* 2260 */       tmp[7] = c3 * m[7] + s3 * m[8];
/* 2261 */       m[8] = -s3 * m[7] + c3 * m[8];
/* 2262 */       m[7] = tmp[7];
/*      */       
/* 2264 */       v1[0] = 1.0D; v1[1] = 0.0D; v1[2] = 0.0D;
/* 2265 */       v1[3] = 0.0D; v1[4] = c3; v1[5] = -s3;
/* 2266 */       v1[6] = 0.0D; v1[7] = s3; v1[8] = c3;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2271 */     if (m[7] * m[7] >= 1.110223024E-16D) {
/* 2272 */       if (m[4] * m[4] < 1.110223024E-16D) {
/* 2273 */         tmp[3] = m[3];
/* 2274 */         tmp[4] = m[4];
/* 2275 */         tmp[5] = m[5];
/* 2276 */         m[3] = m[6];
/* 2277 */         m[4] = m[7];
/* 2278 */         m[5] = m[8];
/*      */         
/* 2280 */         m[6] = -tmp[3];
/* 2281 */         m[7] = -tmp[4];
/* 2282 */         m[8] = -tmp[5];
/*      */         
/* 2284 */         tmp[3] = u1[3];
/* 2285 */         tmp[4] = u1[4];
/* 2286 */         tmp[5] = u1[5];
/* 2287 */         u1[3] = u1[6];
/* 2288 */         u1[4] = u1[7];
/* 2289 */         u1[5] = u1[8];
/*      */         
/* 2291 */         u1[6] = -tmp[3];
/* 2292 */         u1[7] = -tmp[4];
/* 2293 */         u1[8] = -tmp[5];
/*      */       } else {
/*      */         
/* 2296 */         double g = 1.0D / Math.sqrt(m[4] * m[4] + m[7] * m[7]);
/* 2297 */         double c4 = m[4] * g;
/* 2298 */         double s4 = m[7] * g;
/* 2299 */         tmp[3] = c4 * m[3] + s4 * m[6];
/* 2300 */         m[6] = -s4 * m[3] + c4 * m[6];
/* 2301 */         m[3] = tmp[3];
/*      */         
/* 2303 */         tmp[4] = c4 * m[4] + s4 * m[7];
/* 2304 */         m[7] = -s4 * m[4] + c4 * m[7];
/* 2305 */         m[4] = tmp[4];
/*      */         
/* 2307 */         tmp[5] = c4 * m[5] + s4 * m[8];
/* 2308 */         m[8] = -s4 * m[5] + c4 * m[8];
/* 2309 */         m[5] = tmp[5];
/*      */         
/* 2311 */         tmp[3] = c4 * u1[3] + s4 * u1[6];
/* 2312 */         u1[6] = -s4 * u1[3] + c4 * u1[6];
/* 2313 */         u1[3] = tmp[3];
/*      */         
/* 2315 */         tmp[4] = c4 * u1[4] + s4 * u1[7];
/* 2316 */         u1[7] = -s4 * u1[4] + c4 * u1[7];
/* 2317 */         u1[4] = tmp[4];
/*      */         
/* 2319 */         tmp[5] = c4 * u1[5] + s4 * u1[8];
/* 2320 */         u1[8] = -s4 * u1[5] + c4 * u1[8];
/* 2321 */         u1[5] = tmp[5];
/*      */       } 
/*      */     }
/* 2324 */     single_values[0] = m[0];
/* 2325 */     single_values[1] = m[4];
/* 2326 */     single_values[2] = m[8];
/* 2327 */     e[0] = m[1];
/* 2328 */     e[1] = m[5];
/*      */     
/* 2330 */     if (e[0] * e[0] >= 1.110223024E-16D || e[1] * e[1] >= 1.110223024E-16D)
/*      */     {
/*      */       
/* 2333 */       compute_qr(single_values, e, u1, v1);
/*      */     }
/*      */     
/* 2336 */     scales[0] = single_values[0];
/* 2337 */     scales[1] = single_values[1];
/* 2338 */     scales[2] = single_values[2];
/*      */ 
/*      */ 
/*      */     
/* 2342 */     if (almostEqual(Math.abs(scales[0]), 1.0D) && 
/* 2343 */       almostEqual(Math.abs(scales[1]), 1.0D) && 
/* 2344 */       almostEqual(Math.abs(scales[2]), 1.0D)) {
/*      */ 
/*      */       
/* 2347 */       for (i = 0; i < 3; i++) {
/* 2348 */         if (scales[i] < 0.0D)
/* 2349 */           negCnt++; 
/*      */       } 
/* 2351 */       if (negCnt == 0 || negCnt == 2) {
/*      */         
/* 2353 */         outScale[2] = 1.0D; outScale[1] = 1.0D; outScale[0] = 1.0D;
/* 2354 */         for (i = 0; i < 9; i++) {
/* 2355 */           outRot[i] = rot[i];
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 2362 */     transpose_mat(u1, t1);
/* 2363 */     transpose_mat(v1, t2);
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
/* 2377 */     svdReorder(m, t1, t2, scales, outRot, outScale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void svdReorder(double[] m, double[] t1, double[] t2, double[] scales, double[] outRot, double[] outScale) {
/* 2384 */     int[] out = new int[3];
/* 2385 */     int[] in = new int[3];
/*      */     
/* 2387 */     double[] mag = new double[3];
/* 2388 */     double[] rot = new double[9];
/*      */ 
/*      */ 
/*      */     
/* 2392 */     if (scales[0] < 0.0D) {
/* 2393 */       scales[0] = -scales[0];
/* 2394 */       t2[0] = -t2[0];
/* 2395 */       t2[1] = -t2[1];
/* 2396 */       t2[2] = -t2[2];
/*      */     } 
/* 2398 */     if (scales[1] < 0.0D) {
/* 2399 */       scales[1] = -scales[1];
/* 2400 */       t2[3] = -t2[3];
/* 2401 */       t2[4] = -t2[4];
/* 2402 */       t2[5] = -t2[5];
/*      */     } 
/* 2404 */     if (scales[2] < 0.0D) {
/* 2405 */       scales[2] = -scales[2];
/* 2406 */       t2[6] = -t2[6];
/* 2407 */       t2[7] = -t2[7];
/* 2408 */       t2[8] = -t2[8];
/*      */     } 
/*      */     
/* 2411 */     mat_mul(t1, t2, rot);
/*      */ 
/*      */     
/* 2414 */     if (almostEqual(Math.abs(scales[0]), Math.abs(scales[1])) && 
/* 2415 */       almostEqual(Math.abs(scales[1]), Math.abs(scales[2]))) {
/* 2416 */       int i; for (i = 0; i < 9; i++) {
/* 2417 */         outRot[i] = rot[i];
/*      */       }
/* 2419 */       for (i = 0; i < 3; i++) {
/* 2420 */         outScale[i] = scales[i];
/*      */       }
/*      */     } else {
/*      */       int in0, in1, in2;
/*      */ 
/*      */       
/* 2426 */       if (scales[0] > scales[1]) {
/* 2427 */         if (scales[0] > scales[2]) {
/* 2428 */           if (scales[2] > scales[1]) {
/* 2429 */             out[0] = 0; out[1] = 2; out[2] = 1;
/*      */           } else {
/* 2431 */             out[0] = 0; out[1] = 1; out[2] = 2;
/*      */           } 
/*      */         } else {
/* 2434 */           out[0] = 2; out[1] = 0; out[2] = 1;
/*      */         }
/*      */       
/* 2437 */       } else if (scales[1] > scales[2]) {
/* 2438 */         if (scales[2] > scales[0]) {
/* 2439 */           out[0] = 1; out[1] = 2; out[2] = 0;
/*      */         } else {
/* 2441 */           out[0] = 1; out[1] = 0; out[2] = 2;
/*      */         } 
/*      */       } else {
/* 2444 */         out[0] = 2; out[1] = 1; out[2] = 0;
/*      */       } 
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
/* 2456 */       mag[0] = m[0] * m[0] + m[1] * m[1] + m[2] * m[2];
/* 2457 */       mag[1] = m[3] * m[3] + m[4] * m[4] + m[5] * m[5];
/* 2458 */       mag[2] = m[6] * m[6] + m[7] * m[7] + m[8] * m[8];
/*      */       
/* 2460 */       if (mag[0] > mag[1]) {
/* 2461 */         if (mag[0] > mag[2]) {
/* 2462 */           if (mag[2] > mag[1]) {
/*      */             
/* 2464 */             in0 = 0; in2 = 1; in1 = 2;
/*      */           } else {
/*      */             
/* 2467 */             in0 = 0; in1 = 1; in2 = 2;
/*      */           } 
/*      */         } else {
/*      */           
/* 2471 */           in2 = 0; in0 = 1; in1 = 2;
/*      */         }
/*      */       
/* 2474 */       } else if (mag[1] > mag[2]) {
/* 2475 */         if (mag[2] > mag[0]) {
/*      */           
/* 2477 */           in1 = 0; in2 = 1; in0 = 2;
/*      */         } else {
/*      */           
/* 2480 */           in1 = 0; in0 = 1; in2 = 2;
/*      */         } 
/*      */       } else {
/*      */         
/* 2484 */         in2 = 0; in1 = 1; in0 = 2;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2489 */       int index = out[in0];
/* 2490 */       outScale[0] = scales[index];
/*      */       
/* 2492 */       index = out[in1];
/* 2493 */       outScale[1] = scales[index];
/*      */       
/* 2495 */       index = out[in2];
/* 2496 */       outScale[2] = scales[index];
/*      */ 
/*      */       
/* 2499 */       index = out[in0];
/* 2500 */       outRot[0] = rot[index];
/*      */       
/* 2502 */       index = out[in0] + 3;
/* 2503 */       outRot[3] = rot[index];
/*      */       
/* 2505 */       index = out[in0] + 6;
/* 2506 */       outRot[6] = rot[index];
/*      */       
/* 2508 */       index = out[in1];
/* 2509 */       outRot[1] = rot[index];
/*      */       
/* 2511 */       index = out[in1] + 3;
/* 2512 */       outRot[4] = rot[index];
/*      */       
/* 2514 */       index = out[in1] + 6;
/* 2515 */       outRot[7] = rot[index];
/*      */       
/* 2517 */       index = out[in2];
/* 2518 */       outRot[2] = rot[index];
/*      */       
/* 2520 */       index = out[in2] + 3;
/* 2521 */       outRot[5] = rot[index];
/*      */       
/* 2523 */       index = out[in2] + 6;
/* 2524 */       outRot[8] = rot[index];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compute_qr(double[] s, double[] e, double[] u, double[] v) {
/* 2533 */     double[] cosl = new double[2];
/* 2534 */     double[] cosr = new double[2];
/* 2535 */     double[] sinl = new double[2];
/* 2536 */     double[] sinr = new double[2];
/* 2537 */     double[] m = new double[9];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2542 */     int MAX_INTERATIONS = 10;
/* 2543 */     double CONVERGE_TOL = 4.89E-15D;
/*      */     
/* 2545 */     double c_b48 = 1.0D;
/* 2546 */     double c_b71 = -1.0D;
/*      */     
/* 2548 */     boolean converged = false;
/*      */ 
/*      */     
/* 2551 */     int first = 1;
/*      */     
/* 2553 */     if (Math.abs(e[1]) < 4.89E-15D || Math.abs(e[0]) < 4.89E-15D) converged = true;
/*      */     
/* 2555 */     for (int k = 0; k < 10 && !converged; k++) {
/* 2556 */       double shift = compute_shift(s[1], e[1], s[2]);
/* 2557 */       double f = (Math.abs(s[0]) - shift) * (d_sign(c_b48, s[0]) + shift / s[0]);
/* 2558 */       double g = e[0];
/* 2559 */       double r = compute_rot(f, g, sinr, cosr, 0, first);
/* 2560 */       f = cosr[0] * s[0] + sinr[0] * e[0];
/* 2561 */       e[0] = cosr[0] * e[0] - sinr[0] * s[0];
/* 2562 */       g = sinr[0] * s[1];
/* 2563 */       s[1] = cosr[0] * s[1];
/*      */       
/* 2565 */       r = compute_rot(f, g, sinl, cosl, 0, first);
/* 2566 */       first = 0;
/* 2567 */       s[0] = r;
/* 2568 */       f = cosl[0] * e[0] + sinl[0] * s[1];
/* 2569 */       s[1] = cosl[0] * s[1] - sinl[0] * e[0];
/* 2570 */       g = sinl[0] * e[1];
/* 2571 */       e[1] = cosl[0] * e[1];
/*      */       
/* 2573 */       r = compute_rot(f, g, sinr, cosr, 1, first);
/* 2574 */       e[0] = r;
/* 2575 */       f = cosr[1] * s[1] + sinr[1] * e[1];
/* 2576 */       e[1] = cosr[1] * e[1] - sinr[1] * s[1];
/* 2577 */       g = sinr[1] * s[2];
/* 2578 */       s[2] = cosr[1] * s[2];
/*      */       
/* 2580 */       r = compute_rot(f, g, sinl, cosl, 1, first);
/* 2581 */       s[1] = r;
/* 2582 */       f = cosl[1] * e[1] + sinl[1] * s[2];
/* 2583 */       s[2] = cosl[1] * s[2] - sinl[1] * e[1];
/* 2584 */       e[1] = f;
/*      */ 
/*      */       
/* 2587 */       double utemp = u[0];
/* 2588 */       u[0] = cosl[0] * utemp + sinl[0] * u[3];
/* 2589 */       u[3] = -sinl[0] * utemp + cosl[0] * u[3];
/* 2590 */       utemp = u[1];
/* 2591 */       u[1] = cosl[0] * utemp + sinl[0] * u[4];
/* 2592 */       u[4] = -sinl[0] * utemp + cosl[0] * u[4];
/* 2593 */       utemp = u[2];
/* 2594 */       u[2] = cosl[0] * utemp + sinl[0] * u[5];
/* 2595 */       u[5] = -sinl[0] * utemp + cosl[0] * u[5];
/*      */       
/* 2597 */       utemp = u[3];
/* 2598 */       u[3] = cosl[1] * utemp + sinl[1] * u[6];
/* 2599 */       u[6] = -sinl[1] * utemp + cosl[1] * u[6];
/* 2600 */       utemp = u[4];
/* 2601 */       u[4] = cosl[1] * utemp + sinl[1] * u[7];
/* 2602 */       u[7] = -sinl[1] * utemp + cosl[1] * u[7];
/* 2603 */       utemp = u[5];
/* 2604 */       u[5] = cosl[1] * utemp + sinl[1] * u[8];
/* 2605 */       u[8] = -sinl[1] * utemp + cosl[1] * u[8];
/*      */ 
/*      */ 
/*      */       
/* 2609 */       double vtemp = v[0];
/* 2610 */       v[0] = cosr[0] * vtemp + sinr[0] * v[1];
/* 2611 */       v[1] = -sinr[0] * vtemp + cosr[0] * v[1];
/* 2612 */       vtemp = v[3];
/* 2613 */       v[3] = cosr[0] * vtemp + sinr[0] * v[4];
/* 2614 */       v[4] = -sinr[0] * vtemp + cosr[0] * v[4];
/* 2615 */       vtemp = v[6];
/* 2616 */       v[6] = cosr[0] * vtemp + sinr[0] * v[7];
/* 2617 */       v[7] = -sinr[0] * vtemp + cosr[0] * v[7];
/*      */       
/* 2619 */       vtemp = v[1];
/* 2620 */       v[1] = cosr[1] * vtemp + sinr[1] * v[2];
/* 2621 */       v[2] = -sinr[1] * vtemp + cosr[1] * v[2];
/* 2622 */       vtemp = v[4];
/* 2623 */       v[4] = cosr[1] * vtemp + sinr[1] * v[5];
/* 2624 */       v[5] = -sinr[1] * vtemp + cosr[1] * v[5];
/* 2625 */       vtemp = v[7];
/* 2626 */       v[7] = cosr[1] * vtemp + sinr[1] * v[8];
/* 2627 */       v[8] = -sinr[1] * vtemp + cosr[1] * v[8];
/*      */ 
/*      */       
/* 2630 */       m[0] = s[0]; m[1] = e[0]; m[2] = 0.0D;
/* 2631 */       m[3] = 0.0D; m[4] = s[1]; m[5] = e[1];
/* 2632 */       m[6] = 0.0D; m[7] = 0.0D; m[8] = s[2];
/*      */       
/* 2634 */       if (Math.abs(e[1]) < 4.89E-15D || Math.abs(e[0]) < 4.89E-15D) converged = true;
/*      */     
/*      */     } 
/* 2637 */     if (Math.abs(e[1]) < 4.89E-15D) {
/* 2638 */       compute_2X2(s[0], e[0], s[1], s, sinl, cosl, sinr, cosr, 0);
/*      */       
/* 2640 */       double utemp = u[0];
/* 2641 */       u[0] = cosl[0] * utemp + sinl[0] * u[3];
/* 2642 */       u[3] = -sinl[0] * utemp + cosl[0] * u[3];
/* 2643 */       utemp = u[1];
/* 2644 */       u[1] = cosl[0] * utemp + sinl[0] * u[4];
/* 2645 */       u[4] = -sinl[0] * utemp + cosl[0] * u[4];
/* 2646 */       utemp = u[2];
/* 2647 */       u[2] = cosl[0] * utemp + sinl[0] * u[5];
/* 2648 */       u[5] = -sinl[0] * utemp + cosl[0] * u[5];
/*      */ 
/*      */ 
/*      */       
/* 2652 */       double vtemp = v[0];
/* 2653 */       v[0] = cosr[0] * vtemp + sinr[0] * v[1];
/* 2654 */       v[1] = -sinr[0] * vtemp + cosr[0] * v[1];
/* 2655 */       vtemp = v[3];
/* 2656 */       v[3] = cosr[0] * vtemp + sinr[0] * v[4];
/* 2657 */       v[4] = -sinr[0] * vtemp + cosr[0] * v[4];
/* 2658 */       vtemp = v[6];
/* 2659 */       v[6] = cosr[0] * vtemp + sinr[0] * v[7];
/* 2660 */       v[7] = -sinr[0] * vtemp + cosr[0] * v[7];
/*      */     } else {
/* 2662 */       compute_2X2(s[1], e[1], s[2], s, sinl, cosl, sinr, cosr, 1);
/*      */       
/* 2664 */       double utemp = u[3];
/* 2665 */       u[3] = cosl[0] * utemp + sinl[0] * u[6];
/* 2666 */       u[6] = -sinl[0] * utemp + cosl[0] * u[6];
/* 2667 */       utemp = u[4];
/* 2668 */       u[4] = cosl[0] * utemp + sinl[0] * u[7];
/* 2669 */       u[7] = -sinl[0] * utemp + cosl[0] * u[7];
/* 2670 */       utemp = u[5];
/* 2671 */       u[5] = cosl[0] * utemp + sinl[0] * u[8];
/* 2672 */       u[8] = -sinl[0] * utemp + cosl[0] * u[8];
/*      */ 
/*      */ 
/*      */       
/* 2676 */       double vtemp = v[1];
/* 2677 */       v[1] = cosr[0] * vtemp + sinr[0] * v[2];
/* 2678 */       v[2] = -sinr[0] * vtemp + cosr[0] * v[2];
/* 2679 */       vtemp = v[4];
/* 2680 */       v[4] = cosr[0] * vtemp + sinr[0] * v[5];
/* 2681 */       v[5] = -sinr[0] * vtemp + cosr[0] * v[5];
/* 2682 */       vtemp = v[7];
/* 2683 */       v[7] = cosr[0] * vtemp + sinr[0] * v[8];
/* 2684 */       v[8] = -sinr[0] * vtemp + cosr[0] * v[8];
/*      */     } 
/*      */     
/* 2687 */     return 0;
/*      */   }
/*      */   static double max(double a, double b) {
/* 2690 */     if (a > b) {
/* 2691 */       return a;
/*      */     }
/* 2693 */     return b;
/*      */   }
/*      */   static double min(double a, double b) {
/* 2696 */     if (a < b) {
/* 2697 */       return a;
/*      */     }
/* 2699 */     return b;
/*      */   }
/*      */   
/*      */   static double d_sign(double a, double b) {
/* 2703 */     double x = (a >= 0.0D) ? a : -a;
/* 2704 */     return (b >= 0.0D) ? x : -x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_shift(double f, double g, double h) {
/* 2712 */     double ssmin, fa = Math.abs(f);
/* 2713 */     double ga = Math.abs(g);
/* 2714 */     double ha = Math.abs(h);
/* 2715 */     double fhmn = min(fa, ha);
/* 2716 */     double fhmx = max(fa, ha);
/* 2717 */     if (fhmn == 0.0D) {
/* 2718 */       ssmin = 0.0D;
/* 2719 */       if (fhmx != 0.0D)
/*      */       {
/* 2721 */         double d__1 = min(fhmx, ga) / max(fhmx, ga);
/*      */       }
/*      */     }
/* 2724 */     else if (ga < fhmx) {
/* 2725 */       double as = fhmn / fhmx + 1.0D;
/* 2726 */       double at = (fhmx - fhmn) / fhmx;
/* 2727 */       double d__1 = ga / fhmx;
/* 2728 */       double au = d__1 * d__1;
/* 2729 */       double c = 2.0D / (Math.sqrt(as * as + au) + Math.sqrt(at * at + au));
/* 2730 */       ssmin = fhmn * c;
/*      */     } else {
/* 2732 */       double au = fhmx / ga;
/* 2733 */       if (au == 0.0D) {
/* 2734 */         ssmin = fhmn * fhmx / ga;
/*      */       } else {
/* 2736 */         double as = fhmn / fhmx + 1.0D;
/* 2737 */         double at = (fhmx - fhmn) / fhmx;
/* 2738 */         double d__1 = as * au;
/* 2739 */         double d__2 = at * au;
/* 2740 */         double c = 1.0D / (Math.sqrt(d__1 * d__1 + 1.0D) + Math.sqrt(d__2 * d__2 + 1.0D));
/* 2741 */         ssmin = fhmn * c * au;
/* 2742 */         ssmin += ssmin;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2747 */     return ssmin;
/*      */   }
/*      */ 
/*      */   
/*      */   static int compute_2X2(double f, double g, double h, double[] single_values, double[] snl, double[] csl, double[] snr, double[] csr, int index) {
/* 2752 */     double c_b3 = 2.0D;
/* 2753 */     double c_b4 = 1.0D;
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
/* 2765 */     double ssmax = single_values[0];
/* 2766 */     double ssmin = single_values[1];
/* 2767 */     double clt = 0.0D;
/* 2768 */     double crt = 0.0D;
/* 2769 */     double slt = 0.0D;
/* 2770 */     double srt = 0.0D;
/* 2771 */     double tsign = 0.0D;
/*      */     
/* 2773 */     double ft = f;
/* 2774 */     double fa = Math.abs(ft);
/* 2775 */     double ht = h;
/* 2776 */     double ha = Math.abs(h);
/*      */     
/* 2778 */     int pmax = 1;
/* 2779 */     boolean swap = (ha > fa);
/*      */     
/* 2781 */     if (swap) {
/* 2782 */       pmax = 3;
/* 2783 */       double temp = ft;
/* 2784 */       ft = ht;
/* 2785 */       ht = temp;
/* 2786 */       temp = fa;
/* 2787 */       fa = ha;
/* 2788 */       ha = temp;
/*      */     } 
/*      */     
/* 2791 */     double gt = g;
/* 2792 */     double ga = Math.abs(gt);
/* 2793 */     if (ga == 0.0D) {
/*      */       
/* 2795 */       single_values[1] = ha;
/* 2796 */       single_values[0] = fa;
/* 2797 */       clt = 1.0D;
/* 2798 */       crt = 1.0D;
/* 2799 */       slt = 0.0D;
/* 2800 */       srt = 0.0D;
/*      */     } else {
/* 2802 */       boolean gasmal = true;
/*      */       
/* 2804 */       if (ga > fa) {
/* 2805 */         pmax = 2;
/* 2806 */         if (fa / ga < 1.110223024E-16D) {
/*      */           
/* 2808 */           gasmal = false;
/* 2809 */           ssmax = ga;
/* 2810 */           if (ha > 1.0D) {
/* 2811 */             ssmin = fa / ga / ha;
/*      */           } else {
/* 2813 */             ssmin = fa / ga * ha;
/*      */           } 
/* 2815 */           clt = 1.0D;
/* 2816 */           slt = ht / gt;
/* 2817 */           srt = 1.0D;
/* 2818 */           crt = ft / gt;
/*      */         } 
/*      */       } 
/* 2821 */       if (gasmal) {
/*      */         
/* 2823 */         double l, r, d = fa - ha;
/* 2824 */         if (d == fa) {
/*      */           
/* 2826 */           l = 1.0D;
/*      */         } else {
/* 2828 */           l = d / fa;
/*      */         } 
/*      */         
/* 2831 */         double m = gt / ft;
/*      */         
/* 2833 */         double t = 2.0D - l;
/*      */         
/* 2835 */         double mm = m * m;
/* 2836 */         double tt = t * t;
/* 2837 */         double s = Math.sqrt(tt + mm);
/*      */         
/* 2839 */         if (l == 0.0D) {
/* 2840 */           r = Math.abs(m);
/*      */         } else {
/* 2842 */           r = Math.sqrt(l * l + mm);
/*      */         } 
/*      */         
/* 2845 */         double a = (s + r) * 0.5D;
/*      */         
/* 2847 */         if (ga > fa) {
/* 2848 */           pmax = 2;
/* 2849 */           if (fa / ga < 1.110223024E-16D) {
/*      */             
/* 2851 */             gasmal = false;
/* 2852 */             ssmax = ga;
/* 2853 */             if (ha > 1.0D) {
/* 2854 */               ssmin = fa / ga / ha;
/*      */             } else {
/* 2856 */               ssmin = fa / ga * ha;
/*      */             } 
/* 2858 */             clt = 1.0D;
/* 2859 */             slt = ht / gt;
/* 2860 */             srt = 1.0D;
/* 2861 */             crt = ft / gt;
/*      */           } 
/*      */         } 
/* 2864 */         if (gasmal) {
/*      */           
/* 2866 */           d = fa - ha;
/* 2867 */           if (d == fa) {
/*      */             
/* 2869 */             l = 1.0D;
/*      */           } else {
/* 2871 */             l = d / fa;
/*      */           } 
/*      */           
/* 2874 */           m = gt / ft;
/*      */           
/* 2876 */           t = 2.0D - l;
/*      */           
/* 2878 */           mm = m * m;
/* 2879 */           tt = t * t;
/* 2880 */           s = Math.sqrt(tt + mm);
/*      */           
/* 2882 */           if (l == 0.0D) {
/* 2883 */             r = Math.abs(m);
/*      */           } else {
/* 2885 */             r = Math.sqrt(l * l + mm);
/*      */           } 
/*      */           
/* 2888 */           a = (s + r) * 0.5D;
/*      */ 
/*      */           
/* 2891 */           ssmin = ha / a;
/* 2892 */           ssmax = fa * a;
/* 2893 */           if (mm == 0.0D) {
/*      */             
/* 2895 */             if (l == 0.0D) {
/* 2896 */               t = d_sign(c_b3, ft) * d_sign(c_b4, gt);
/*      */             } else {
/* 2898 */               t = gt / d_sign(d, ft) + m / t;
/*      */             } 
/*      */           } else {
/* 2901 */             t = (m / (s + t) + m / (r + l)) * (a + 1.0D);
/*      */           } 
/* 2903 */           l = Math.sqrt(t * t + 4.0D);
/* 2904 */           crt = 2.0D / l;
/* 2905 */           srt = t / l;
/* 2906 */           clt = (crt + srt * m) / a;
/* 2907 */           slt = ht / ft * srt / a;
/*      */         } 
/*      */       } 
/* 2910 */       if (swap) {
/* 2911 */         csl[0] = srt;
/* 2912 */         snl[0] = crt;
/* 2913 */         csr[0] = slt;
/* 2914 */         snr[0] = clt;
/*      */       } else {
/* 2916 */         csl[0] = clt;
/* 2917 */         snl[0] = slt;
/* 2918 */         csr[0] = crt;
/* 2919 */         snr[0] = srt;
/*      */       } 
/*      */       
/* 2922 */       if (pmax == 1) {
/* 2923 */         tsign = d_sign(c_b4, csr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, f);
/*      */       }
/* 2925 */       if (pmax == 2) {
/* 2926 */         tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, g);
/*      */       }
/* 2928 */       if (pmax == 3) {
/* 2929 */         tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, snl[0]) * d_sign(c_b4, h);
/*      */       }
/* 2931 */       single_values[index] = d_sign(ssmax, tsign);
/* 2932 */       double d__1 = tsign * d_sign(c_b4, f) * d_sign(c_b4, h);
/* 2933 */       single_values[index + 1] = d_sign(ssmin, d__1);
/*      */     } 
/*      */ 
/*      */     
/* 2937 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_rot(double f, double g, double[] sin, double[] cos, int index, int first) {
/* 2948 */     double cs, sn, r, safmn2 = 2.002083095183101E-146D;
/* 2949 */     double safmx2 = 4.994797680505588E145D;
/*      */     
/* 2951 */     if (g == 0.0D) {
/* 2952 */       cs = 1.0D;
/* 2953 */       sn = 0.0D;
/* 2954 */       r = f;
/* 2955 */     } else if (f == 0.0D) {
/* 2956 */       cs = 0.0D;
/* 2957 */       sn = 1.0D;
/* 2958 */       r = g;
/*      */     } else {
/* 2960 */       double f1 = f;
/* 2961 */       double g1 = g;
/* 2962 */       double scale = max(Math.abs(f1), Math.abs(g1));
/* 2963 */       if (scale >= 4.994797680505588E145D) {
/* 2964 */         int count = 0;
/* 2965 */         while (scale >= 4.994797680505588E145D) {
/* 2966 */           count++;
/* 2967 */           f1 *= 2.002083095183101E-146D;
/* 2968 */           g1 *= 2.002083095183101E-146D;
/* 2969 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2971 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2972 */         cs = f1 / r;
/* 2973 */         sn = g1 / r;
/* 2974 */         int i__1 = count;
/* 2975 */         for (int i = 1; i <= count; i++) {
/* 2976 */           r *= 4.994797680505588E145D;
/*      */         }
/* 2978 */       } else if (scale <= 2.002083095183101E-146D) {
/* 2979 */         int count = 0;
/* 2980 */         while (scale <= 2.002083095183101E-146D) {
/* 2981 */           count++;
/* 2982 */           f1 *= 4.994797680505588E145D;
/* 2983 */           g1 *= 4.994797680505588E145D;
/* 2984 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2986 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2987 */         cs = f1 / r;
/* 2988 */         sn = g1 / r;
/* 2989 */         int i__1 = count;
/* 2990 */         for (int i = 1; i <= count; i++) {
/* 2991 */           r *= 2.002083095183101E-146D;
/*      */         }
/*      */       } else {
/* 2994 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2995 */         cs = f1 / r;
/* 2996 */         sn = g1 / r;
/*      */       } 
/* 2998 */       if (Math.abs(f) > Math.abs(g) && cs < 0.0D) {
/* 2999 */         cs = -cs;
/* 3000 */         sn = -sn;
/* 3001 */         r = -r;
/*      */       } 
/*      */     } 
/* 3004 */     sin[index] = sn;
/* 3005 */     cos[index] = cs;
/* 3006 */     return r;
/*      */   }
/*      */ 
/*      */   
/*      */   static void print_mat(double[] mat) {
/* 3011 */     for (int i = 0; i < 3; i++) {
/* 3012 */       System.out.println(mat[i * 3 + 0] + " " + mat[i * 3 + 1] + " " + mat[i * 3 + 2] + "\n");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void print_det(double[] mat) {
/* 3019 */     double det = mat[0] * mat[4] * mat[8] + mat[1] * mat[5] * mat[6] + mat[2] * mat[3] * mat[7] - mat[2] * mat[4] * mat[6] - mat[0] * mat[5] * mat[7] - mat[1] * mat[3] * mat[8];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3025 */     System.out.println("det= " + det);
/*      */   }
/*      */   
/*      */   static void mat_mul(double[] m1, double[] m2, double[] m3) {
/* 3029 */     double[] tmp = new double[9];
/*      */     
/* 3031 */     tmp[0] = m1[0] * m2[0] + m1[1] * m2[3] + m1[2] * m2[6];
/* 3032 */     tmp[1] = m1[0] * m2[1] + m1[1] * m2[4] + m1[2] * m2[7];
/* 3033 */     tmp[2] = m1[0] * m2[2] + m1[1] * m2[5] + m1[2] * m2[8];
/*      */     
/* 3035 */     tmp[3] = m1[3] * m2[0] + m1[4] * m2[3] + m1[5] * m2[6];
/* 3036 */     tmp[4] = m1[3] * m2[1] + m1[4] * m2[4] + m1[5] * m2[7];
/* 3037 */     tmp[5] = m1[3] * m2[2] + m1[4] * m2[5] + m1[5] * m2[8];
/*      */     
/* 3039 */     tmp[6] = m1[6] * m2[0] + m1[7] * m2[3] + m1[8] * m2[6];
/* 3040 */     tmp[7] = m1[6] * m2[1] + m1[7] * m2[4] + m1[8] * m2[7];
/* 3041 */     tmp[8] = m1[6] * m2[2] + m1[7] * m2[5] + m1[8] * m2[8];
/*      */     
/* 3043 */     for (int i = 0; i < 9; i++)
/* 3044 */       m3[i] = tmp[i]; 
/*      */   }
/*      */   
/*      */   static void transpose_mat(double[] in, double[] out) {
/* 3048 */     out[0] = in[0];
/* 3049 */     out[1] = in[3];
/* 3050 */     out[2] = in[6];
/*      */     
/* 3052 */     out[3] = in[1];
/* 3053 */     out[4] = in[4];
/* 3054 */     out[5] = in[7];
/*      */     
/* 3056 */     out[6] = in[2];
/* 3057 */     out[7] = in[5];
/* 3058 */     out[8] = in[8];
/*      */   }
/*      */   static double max3(double[] values) {
/* 3061 */     if (values[0] > values[1]) {
/* 3062 */       if (values[0] > values[2]) {
/* 3063 */         return values[0];
/*      */       }
/* 3065 */       return values[2];
/*      */     } 
/* 3067 */     if (values[1] > values[2]) {
/* 3068 */       return values[1];
/*      */     }
/* 3070 */     return values[2];
/*      */   }
/*      */ 
/*      */   
/*      */   private static final boolean almostEqual(double a, double b) {
/* 3075 */     if (a == b) {
/* 3076 */       return true;
/*      */     }
/* 3078 */     double EPSILON_ABSOLUTE = 1.0E-6D;
/* 3079 */     double EPSILON_RELATIVE = 1.0E-4D;
/* 3080 */     double diff = Math.abs(a - b);
/* 3081 */     double absA = Math.abs(a);
/* 3082 */     double absB = Math.abs(b);
/* 3083 */     double max = (absA >= absB) ? absA : absB;
/*      */     
/* 3085 */     if (diff < 1.0E-6D) {
/* 3086 */       return true;
/*      */     }
/* 3088 */     return (diff / max < 1.0E-4D);
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
/*      */   public Object clone() {
/* 3101 */     Matrix3d m1 = null;
/*      */     try {
/* 3103 */       m1 = (Matrix3d)super.clone();
/* 3104 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3106 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 3110 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM00() {
/* 3119 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(double m00) {
/* 3130 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM01() {
/* 3141 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(double m01) {
/* 3152 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM02() {
/* 3163 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(double m02) {
/* 3174 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM10() {
/* 3185 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(double m10) {
/* 3196 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM11() {
/* 3207 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(double m11) {
/* 3218 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM12() {
/* 3229 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(double m12) {
/* 3240 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM20() {
/* 3251 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(double m20) {
/* 3262 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM21() {
/* 3273 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(double m21) {
/* 3284 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM22() {
/* 3295 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(double m22) {
/* 3306 */     this.m22 = m22;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Matrix3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */