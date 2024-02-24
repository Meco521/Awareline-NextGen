/*      */ package net.minecraft.VLOUBOOS.javax.vecmath;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import net.minecraft.util.MathHelper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  111 */     this.m00 = m00;
/*  112 */     this.m01 = m01;
/*  113 */     this.m02 = m02;
/*      */     
/*  115 */     this.m10 = m10;
/*  116 */     this.m11 = m11;
/*  117 */     this.m12 = m12;
/*      */     
/*  119 */     this.m20 = m20;
/*  120 */     this.m21 = m21;
/*  121 */     this.m22 = m22;
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
/*  132 */     this.m00 = v[0];
/*  133 */     this.m01 = v[1];
/*  134 */     this.m02 = v[2];
/*      */     
/*  136 */     this.m10 = v[3];
/*  137 */     this.m11 = v[4];
/*  138 */     this.m12 = v[5];
/*      */     
/*  140 */     this.m20 = v[6];
/*  141 */     this.m21 = v[7];
/*  142 */     this.m22 = v[8];
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
/*  153 */     this.m00 = (float)m1.m00;
/*  154 */     this.m01 = (float)m1.m01;
/*  155 */     this.m02 = (float)m1.m02;
/*      */     
/*  157 */     this.m10 = (float)m1.m10;
/*  158 */     this.m11 = (float)m1.m11;
/*  159 */     this.m12 = (float)m1.m12;
/*      */     
/*  161 */     this.m20 = (float)m1.m20;
/*  162 */     this.m21 = (float)m1.m21;
/*  163 */     this.m22 = (float)m1.m22;
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
/*  175 */     this.m00 = m1.m00;
/*  176 */     this.m01 = m1.m01;
/*  177 */     this.m02 = m1.m02;
/*      */     
/*  179 */     this.m10 = m1.m10;
/*  180 */     this.m11 = m1.m11;
/*  181 */     this.m12 = m1.m12;
/*      */     
/*  183 */     this.m20 = m1.m20;
/*  184 */     this.m21 = m1.m21;
/*  185 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f() {
/*  195 */     this.m00 = 0.0F;
/*  196 */     this.m01 = 0.0F;
/*  197 */     this.m02 = 0.0F;
/*      */     
/*  199 */     this.m10 = 0.0F;
/*  200 */     this.m11 = 0.0F;
/*  201 */     this.m12 = 0.0F;
/*      */     
/*  203 */     this.m20 = 0.0F;
/*  204 */     this.m21 = 0.0F;
/*  205 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  215 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
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
/*  226 */     this.m00 = 1.0F;
/*  227 */     this.m01 = 0.0F;
/*  228 */     this.m02 = 0.0F;
/*      */     
/*  230 */     this.m10 = 0.0F;
/*  231 */     this.m11 = 1.0F;
/*  232 */     this.m12 = 0.0F;
/*      */     
/*  234 */     this.m20 = 0.0F;
/*  235 */     this.m21 = 0.0F;
/*  236 */     this.m22 = 1.0F;
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
/*  247 */     double[] tmp_rot = new double[9];
/*  248 */     double[] tmp_scale = new double[3];
/*      */     
/*  250 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  252 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  253 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  254 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  256 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  257 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  258 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  260 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  261 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  262 */     this.m22 = (float)(tmp_rot[8] * scale);
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
/*  274 */     switch (row) {
/*      */       
/*      */       case 0:
/*  277 */         switch (column) {
/*      */           
/*      */           case 0:
/*  280 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  283 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  286 */             this.m02 = value;
/*      */             return;
/*      */         } 
/*  289 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  294 */         switch (column) {
/*      */           
/*      */           case 0:
/*  297 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  300 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  303 */             this.m12 = value;
/*      */             return;
/*      */         } 
/*  306 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  311 */         switch (column) {
/*      */           
/*      */           case 0:
/*  314 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  317 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  320 */             this.m22 = value;
/*      */             return;
/*      */         } 
/*      */         
/*  324 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  329 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector3f v) {
/*  339 */     if (row == 0) {
/*  340 */       v.x = this.m00;
/*  341 */       v.y = this.m01;
/*  342 */       v.z = this.m02;
/*  343 */     } else if (row == 1) {
/*  344 */       v.x = this.m10;
/*  345 */       v.y = this.m11;
/*  346 */       v.z = this.m12;
/*  347 */     } else if (row == 2) {
/*  348 */       v.x = this.m20;
/*  349 */       v.y = this.m21;
/*  350 */       v.z = this.m22;
/*      */     } else {
/*  352 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
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
/*  363 */     if (row == 0) {
/*  364 */       v[0] = this.m00;
/*  365 */       v[1] = this.m01;
/*  366 */       v[2] = this.m02;
/*  367 */     } else if (row == 1) {
/*  368 */       v[0] = this.m10;
/*  369 */       v[1] = this.m11;
/*  370 */       v[2] = this.m12;
/*  371 */     } else if (row == 2) {
/*  372 */       v[0] = this.m20;
/*  373 */       v[1] = this.m21;
/*  374 */       v[2] = this.m22;
/*      */     } else {
/*  376 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
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
/*  388 */     if (column == 0) {
/*  389 */       v.x = this.m00;
/*  390 */       v.y = this.m10;
/*  391 */       v.z = this.m20;
/*  392 */     } else if (column == 1) {
/*  393 */       v.x = this.m01;
/*  394 */       v.y = this.m11;
/*  395 */       v.z = this.m21;
/*  396 */     } else if (column == 2) {
/*  397 */       v.x = this.m02;
/*  398 */       v.y = this.m12;
/*  399 */       v.z = this.m22;
/*      */     } else {
/*  401 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
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
/*  413 */     if (column == 0) {
/*  414 */       v[0] = this.m00;
/*  415 */       v[1] = this.m10;
/*  416 */       v[2] = this.m20;
/*  417 */     } else if (column == 1) {
/*  418 */       v[0] = this.m01;
/*  419 */       v[1] = this.m11;
/*  420 */       v[2] = this.m21;
/*  421 */     } else if (column == 2) {
/*  422 */       v[0] = this.m02;
/*  423 */       v[1] = this.m12;
/*  424 */       v[2] = this.m22;
/*      */     } else {
/*  426 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
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
/*  439 */     switch (row) {
/*      */       
/*      */       case 0:
/*  442 */         switch (column) {
/*      */           
/*      */           case 0:
/*  445 */             return this.m00;
/*      */           case 1:
/*  447 */             return this.m01;
/*      */           case 2:
/*  449 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  455 */         switch (column) {
/*      */           
/*      */           case 0:
/*  458 */             return this.m10;
/*      */           case 1:
/*  460 */             return this.m11;
/*      */           case 2:
/*  462 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  469 */         switch (column) {
/*      */           
/*      */           case 0:
/*  472 */             return this.m20;
/*      */           case 1:
/*  474 */             return this.m21;
/*      */           case 2:
/*  476 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  485 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));
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
/*  497 */     switch (row) {
/*      */       case 0:
/*  499 */         this.m00 = x;
/*  500 */         this.m01 = y;
/*  501 */         this.m02 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  505 */         this.m10 = x;
/*  506 */         this.m11 = y;
/*  507 */         this.m12 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  511 */         this.m20 = x;
/*  512 */         this.m21 = y;
/*  513 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  517 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
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
/*  528 */     switch (row) {
/*      */       case 0:
/*  530 */         this.m00 = v.x;
/*  531 */         this.m01 = v.y;
/*  532 */         this.m02 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  536 */         this.m10 = v.x;
/*  537 */         this.m11 = v.y;
/*  538 */         this.m12 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  542 */         this.m20 = v.x;
/*  543 */         this.m21 = v.y;
/*  544 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  548 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
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
/*  559 */     switch (row) {
/*      */       case 0:
/*  561 */         this.m00 = v[0];
/*  562 */         this.m01 = v[1];
/*  563 */         this.m02 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  567 */         this.m10 = v[0];
/*  568 */         this.m11 = v[1];
/*  569 */         this.m12 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  573 */         this.m20 = v[0];
/*  574 */         this.m21 = v[1];
/*  575 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  579 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
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
/*  592 */     switch (column) {
/*      */       case 0:
/*  594 */         this.m00 = x;
/*  595 */         this.m10 = y;
/*  596 */         this.m20 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  600 */         this.m01 = x;
/*  601 */         this.m11 = y;
/*  602 */         this.m21 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  606 */         this.m02 = x;
/*  607 */         this.m12 = y;
/*  608 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  612 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
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
/*  623 */     switch (column) {
/*      */       case 0:
/*  625 */         this.m00 = v.x;
/*  626 */         this.m10 = v.y;
/*  627 */         this.m20 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  631 */         this.m01 = v.x;
/*  632 */         this.m11 = v.y;
/*  633 */         this.m21 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  637 */         this.m02 = v.x;
/*  638 */         this.m12 = v.y;
/*  639 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  643 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
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
/*  654 */     switch (column) {
/*      */       case 0:
/*  656 */         this.m00 = v[0];
/*  657 */         this.m10 = v[1];
/*  658 */         this.m20 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  662 */         this.m01 = v[0];
/*  663 */         this.m11 = v[1];
/*  664 */         this.m21 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  668 */         this.m02 = v[0];
/*  669 */         this.m12 = v[1];
/*  670 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  674 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
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
/*  688 */     double[] tmp_rot = new double[9];
/*  689 */     double[] tmp_scale = new double[3];
/*  690 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  692 */     return (float)Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/*  702 */     this.m00 += scalar;
/*  703 */     this.m01 += scalar;
/*  704 */     this.m02 += scalar;
/*  705 */     this.m10 += scalar;
/*  706 */     this.m11 += scalar;
/*  707 */     this.m12 += scalar;
/*  708 */     this.m20 += scalar;
/*  709 */     this.m21 += scalar;
/*  710 */     this.m22 += scalar;
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
/*  721 */     m1.m00 += scalar;
/*  722 */     m1.m01 += scalar;
/*  723 */     m1.m02 += scalar;
/*  724 */     m1.m10 += scalar;
/*  725 */     m1.m11 += scalar;
/*  726 */     m1.m12 += scalar;
/*  727 */     m1.m20 += scalar;
/*  728 */     m1.m21 += scalar;
/*  729 */     m1.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1, Matrix3f m2) {
/*  739 */     m1.m00 += m2.m00;
/*  740 */     m1.m01 += m2.m01;
/*  741 */     m1.m02 += m2.m02;
/*      */     
/*  743 */     m1.m10 += m2.m10;
/*  744 */     m1.m11 += m2.m11;
/*  745 */     m1.m12 += m2.m12;
/*      */     
/*  747 */     m1.m20 += m2.m20;
/*  748 */     m1.m21 += m2.m21;
/*  749 */     m1.m22 += m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1) {
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
/*      */   public final void sub(Matrix3f m1, Matrix3f m2) {
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
/*      */   public final void sub(Matrix3f m1) {
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
/*  820 */     float temp = this.m10;
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
/*      */   public final void transpose(Matrix3f m1) {
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
/*      */   public final void set(Quat4f q1) {
/*  862 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/*  863 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/*  864 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  866 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/*  867 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/*  868 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  870 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/*  871 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/*  872 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/*  882 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*  883 */     if (mag < 1.0E-8D) {
/*  884 */       this.m00 = 1.0F;
/*  885 */       this.m01 = 0.0F;
/*  886 */       this.m02 = 0.0F;
/*      */       
/*  888 */       this.m10 = 0.0F;
/*  889 */       this.m11 = 1.0F;
/*  890 */       this.m12 = 0.0F;
/*      */       
/*  892 */       this.m20 = 0.0F;
/*  893 */       this.m21 = 0.0F;
/*  894 */       this.m22 = 1.0F;
/*      */     } else {
/*  896 */       mag = 1.0F / mag;
/*  897 */       float ax = a1.x * mag;
/*  898 */       float ay = a1.y * mag;
/*  899 */       float az = a1.z * mag;
/*      */       
/*  901 */       float sinTheta = MathHelper.sin(a1.angle);
/*  902 */       float cosTheta = MathHelper.cos(a1.angle);
/*  903 */       float t = 1.0F - cosTheta;
/*      */       
/*  905 */       float xz = ax * az;
/*  906 */       float xy = ax * ay;
/*  907 */       float yz = ay * az;
/*      */       
/*  909 */       this.m00 = t * ax * ax + cosTheta;
/*  910 */       this.m01 = t * xy - sinTheta * az;
/*  911 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  913 */       this.m10 = t * xy + sinTheta * az;
/*  914 */       this.m11 = t * ay * ay + cosTheta;
/*  915 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  917 */       this.m20 = t * xz - sinTheta * ay;
/*  918 */       this.m21 = t * yz + sinTheta * ax;
/*  919 */       this.m22 = t * az * az + cosTheta;
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
/*  931 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*  932 */     if (mag < 1.0E-8D) {
/*  933 */       this.m00 = 1.0F;
/*  934 */       this.m01 = 0.0F;
/*  935 */       this.m02 = 0.0F;
/*      */       
/*  937 */       this.m10 = 0.0F;
/*  938 */       this.m11 = 1.0F;
/*  939 */       this.m12 = 0.0F;
/*      */       
/*  941 */       this.m20 = 0.0F;
/*  942 */       this.m21 = 0.0F;
/*  943 */       this.m22 = 1.0F;
/*      */     } else {
/*  945 */       mag = 1.0D / mag;
/*  946 */       double ax = a1.x * mag;
/*  947 */       double ay = a1.y * mag;
/*  948 */       double az = a1.z * mag;
/*      */       
/*  950 */       double sinTheta = Math.sin(a1.angle);
/*  951 */       double cosTheta = Math.cos(a1.angle);
/*  952 */       double t = 1.0D - cosTheta;
/*      */       
/*  954 */       double xz = ax * az;
/*  955 */       double xy = ax * ay;
/*  956 */       double yz = ay * az;
/*      */       
/*  958 */       this.m00 = (float)(t * ax * ax + cosTheta);
/*  959 */       this.m01 = (float)(t * xy - sinTheta * az);
/*  960 */       this.m02 = (float)(t * xz + sinTheta * ay);
/*      */       
/*  962 */       this.m10 = (float)(t * xy + sinTheta * az);
/*  963 */       this.m11 = (float)(t * ay * ay + cosTheta);
/*  964 */       this.m12 = (float)(t * yz - sinTheta * ax);
/*      */       
/*  966 */       this.m20 = (float)(t * xz - sinTheta * ay);
/*  967 */       this.m21 = (float)(t * yz + sinTheta * ax);
/*  968 */       this.m22 = (float)(t * az * az + cosTheta);
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
/*  980 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  981 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  982 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  984 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  985 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  986 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  988 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  989 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  990 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
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
/* 1001 */     this.m00 = m[0];
/* 1002 */     this.m01 = m[1];
/* 1003 */     this.m02 = m[2];
/*      */     
/* 1005 */     this.m10 = m[3];
/* 1006 */     this.m11 = m[4];
/* 1007 */     this.m12 = m[5];
/*      */     
/* 1009 */     this.m20 = m[6];
/* 1010 */     this.m21 = m[7];
/* 1011 */     this.m22 = m[8];
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
/* 1023 */     this.m00 = m1.m00;
/* 1024 */     this.m01 = m1.m01;
/* 1025 */     this.m02 = m1.m02;
/*      */     
/* 1027 */     this.m10 = m1.m10;
/* 1028 */     this.m11 = m1.m11;
/* 1029 */     this.m12 = m1.m12;
/*      */     
/* 1031 */     this.m20 = m1.m20;
/* 1032 */     this.m21 = m1.m21;
/* 1033 */     this.m22 = m1.m22;
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
/* 1045 */     this.m00 = (float)m1.m00;
/* 1046 */     this.m01 = (float)m1.m01;
/* 1047 */     this.m02 = (float)m1.m02;
/*      */     
/* 1049 */     this.m10 = (float)m1.m10;
/* 1050 */     this.m11 = (float)m1.m11;
/* 1051 */     this.m12 = (float)m1.m12;
/*      */     
/* 1053 */     this.m20 = (float)m1.m20;
/* 1054 */     this.m21 = (float)m1.m21;
/* 1055 */     this.m22 = (float)m1.m22;
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
/* 1067 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1075 */     invertGeneral(this);
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
/*      */   private final void invertGeneral(Matrix3f m1) {
/* 1087 */     double[] temp = new double[9];
/* 1088 */     double[] result = new double[9];
/* 1089 */     int[] row_perm = new int[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     temp[0] = m1.m00;
/* 1097 */     temp[1] = m1.m01;
/* 1098 */     temp[2] = m1.m02;
/*      */     
/* 1100 */     temp[3] = m1.m10;
/* 1101 */     temp[4] = m1.m11;
/* 1102 */     temp[5] = m1.m12;
/*      */     
/* 1104 */     temp[6] = m1.m20;
/* 1105 */     temp[7] = m1.m21;
/* 1106 */     temp[8] = m1.m22;
/*      */ 
/*      */ 
/*      */     
/* 1110 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1112 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));
/*      */     }
/*      */ 
/*      */     
/* 1116 */     for (int i = 0; i < 9; ) { result[i] = 0.0D; i++; }
/* 1117 */      result[0] = 1.0D; result[4] = 1.0D; result[8] = 1.0D;
/* 1118 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1120 */     this.m00 = (float)result[0];
/* 1121 */     this.m01 = (float)result[1];
/* 1122 */     this.m02 = (float)result[2];
/*      */     
/* 1124 */     this.m10 = (float)result[3];
/* 1125 */     this.m11 = (float)result[4];
/* 1126 */     this.m12 = (float)result[5];
/*      */     
/* 1128 */     this.m20 = (float)result[6];
/* 1129 */     this.m21 = (float)result[7];
/* 1130 */     this.m22 = (float)result[8];
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
/* 1157 */     double[] row_scale = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     int ptr = 0;
/* 1166 */     int rs = 0;
/*      */ 
/*      */     
/* 1169 */     int i = 3;
/* 1170 */     while (i-- != 0) {
/* 1171 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1174 */       int k = 3;
/* 1175 */       while (k-- != 0) {
/* 1176 */         double temp = matrix0[ptr++];
/* 1177 */         temp = Math.abs(temp);
/* 1178 */         if (temp > big) {
/* 1179 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1184 */       if (big == 0.0D) {
/* 1185 */         return false;
/*      */       }
/* 1187 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1195 */     int mtx = 0;
/*      */ 
/*      */     
/* 1198 */     for (int j = 0; j < 3; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1204 */       for (k = 0; k < j; k++) {
/* 1205 */         int target = mtx + 3 * k + j;
/* 1206 */         double sum = matrix0[target];
/* 1207 */         int m = k;
/* 1208 */         int p1 = mtx + 3 * k;
/* 1209 */         int p2 = mtx + j;
/* 1210 */         while (m-- != 0) {
/* 1211 */           sum -= matrix0[p1] * matrix0[p2];
/* 1212 */           p1++;
/* 1213 */           p2 += 3;
/*      */         } 
/* 1215 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1220 */       double big = 0.0D;
/* 1221 */       int imax = -1;
/* 1222 */       for (k = j; k < 3; k++) {
/* 1223 */         int target = mtx + 3 * k + j;
/* 1224 */         double sum = matrix0[target];
/* 1225 */         int m = j;
/* 1226 */         int p1 = mtx + 3 * k;
/* 1227 */         int p2 = mtx + j;
/* 1228 */         while (m-- != 0) {
/* 1229 */           sum -= matrix0[p1] * matrix0[p2];
/* 1230 */           p1++;
/* 1231 */           p2 += 3;
/*      */         } 
/* 1233 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1236 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1237 */           big = temp;
/* 1238 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1242 */       if (imax < 0) {
/* 1243 */         throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));
/*      */       }
/*      */ 
/*      */       
/* 1247 */       if (j != imax) {
/*      */         
/* 1249 */         int m = 3;
/* 1250 */         int p1 = mtx + 3 * imax;
/* 1251 */         int p2 = mtx + 3 * j;
/* 1252 */         while (m-- != 0) {
/* 1253 */           double temp = matrix0[p1];
/* 1254 */           matrix0[p1++] = matrix0[p2];
/* 1255 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1259 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1263 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1266 */       if (matrix0[mtx + 3 * j + j] == 0.0D) {
/* 1267 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1271 */       if (j != 2) {
/* 1272 */         double temp = 1.0D / matrix0[mtx + 3 * j + j];
/* 1273 */         int target = mtx + 3 * (j + 1) + j;
/* 1274 */         k = 2 - j;
/* 1275 */         while (k-- != 0) {
/* 1276 */           matrix0[target] = matrix0[target] * temp;
/* 1277 */           target += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1283 */     return true;
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
/* 1313 */     int rp = 0;
/*      */ 
/*      */     
/* 1316 */     for (int k = 0; k < 3; k++) {
/*      */       
/* 1318 */       int cv = k;
/* 1319 */       int ii = -1;
/*      */ 
/*      */       
/* 1322 */       for (int i = 0; i < 3; i++) {
/*      */ 
/*      */         
/* 1325 */         int ip = row_perm[rp + i];
/* 1326 */         double sum = matrix2[cv + 3 * ip];
/* 1327 */         matrix2[cv + 3 * ip] = matrix2[cv + 3 * i];
/* 1328 */         if (ii >= 0) {
/*      */           
/* 1330 */           int m = i * 3;
/* 1331 */           for (int j = ii; j <= i - 1; j++) {
/* 1332 */             sum -= matrix1[m + j] * matrix2[cv + 3 * j];
/*      */           }
/*      */         }
/* 1335 */         else if (sum != 0.0D) {
/* 1336 */           ii = i;
/*      */         } 
/* 1338 */         matrix2[cv + 3 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1343 */       int rv = 6;
/* 1344 */       matrix2[cv + 6] = matrix2[cv + 6] / matrix1[rv + 2];
/*      */       
/* 1346 */       rv -= 3;
/* 1347 */       matrix2[cv + 3] = (matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 1];
/*      */ 
/*      */       
/* 1350 */       rv -= 3;
/* 1351 */       matrix2[cv] = (matrix2[cv] - matrix1[rv + 1] * matrix2[cv + 3] - matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv];
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
/* 1364 */     float total = this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
/*      */ 
/*      */     
/* 1367 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 1377 */     this.m00 = scale;
/* 1378 */     this.m01 = 0.0F;
/* 1379 */     this.m02 = 0.0F;
/*      */     
/* 1381 */     this.m10 = 0.0F;
/* 1382 */     this.m11 = scale;
/* 1383 */     this.m12 = 0.0F;
/*      */     
/* 1385 */     this.m20 = 0.0F;
/* 1386 */     this.m21 = 0.0F;
/* 1387 */     this.m22 = scale;
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
/* 1399 */     float sinAngle = MathHelper.sin(angle);
/* 1400 */     float cosAngle = MathHelper.cos(angle);
/*      */     
/* 1402 */     this.m00 = 1.0F;
/* 1403 */     this.m01 = 0.0F;
/* 1404 */     this.m02 = 0.0F;
/*      */     
/* 1406 */     this.m10 = 0.0F;
/* 1407 */     this.m11 = cosAngle;
/* 1408 */     this.m12 = -sinAngle;
/*      */     
/* 1410 */     this.m20 = 0.0F;
/* 1411 */     this.m21 = sinAngle;
/* 1412 */     this.m22 = cosAngle;
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
/* 1424 */     float sinAngle = MathHelper.sin(angle);
/* 1425 */     float cosAngle = MathHelper.cos(angle);
/*      */     
/* 1427 */     this.m00 = cosAngle;
/* 1428 */     this.m01 = 0.0F;
/* 1429 */     this.m02 = sinAngle;
/*      */     
/* 1431 */     this.m10 = 0.0F;
/* 1432 */     this.m11 = 1.0F;
/* 1433 */     this.m12 = 0.0F;
/*      */     
/* 1435 */     this.m20 = -sinAngle;
/* 1436 */     this.m21 = 0.0F;
/* 1437 */     this.m22 = cosAngle;
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
/* 1449 */     float sinAngle = MathHelper.sin(angle);
/* 1450 */     float cosAngle = MathHelper.cos(angle);
/*      */     
/* 1452 */     this.m00 = cosAngle;
/* 1453 */     this.m01 = -sinAngle;
/* 1454 */     this.m02 = 0.0F;
/*      */     
/* 1456 */     this.m10 = sinAngle;
/* 1457 */     this.m11 = cosAngle;
/* 1458 */     this.m12 = 0.0F;
/*      */     
/* 1460 */     this.m20 = 0.0F;
/* 1461 */     this.m21 = 0.0F;
/* 1462 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 1471 */     this.m00 *= scalar;
/* 1472 */     this.m01 *= scalar;
/* 1473 */     this.m02 *= scalar;
/*      */     
/* 1475 */     this.m10 *= scalar;
/* 1476 */     this.m11 *= scalar;
/* 1477 */     this.m12 *= scalar;
/*      */     
/* 1479 */     this.m20 *= scalar;
/* 1480 */     this.m21 *= scalar;
/* 1481 */     this.m22 *= scalar;
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
/* 1492 */     this.m00 = scalar * m1.m00;
/* 1493 */     this.m01 = scalar * m1.m01;
/* 1494 */     this.m02 = scalar * m1.m02;
/*      */     
/* 1496 */     this.m10 = scalar * m1.m10;
/* 1497 */     this.m11 = scalar * m1.m11;
/* 1498 */     this.m12 = scalar * m1.m12;
/*      */     
/* 1500 */     this.m20 = scalar * m1.m20;
/* 1501 */     this.m21 = scalar * m1.m21;
/* 1502 */     this.m22 = scalar * m1.m22;
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
/* 1517 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1518 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1519 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1521 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1522 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1523 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1525 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1526 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1527 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
/*      */     
/* 1529 */     this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1530 */     this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1531 */     this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/* 1542 */     if (this != m1 && this != m2) {
/* 1543 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1544 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1545 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1547 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1548 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1549 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1551 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1552 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1553 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1559 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1560 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1561 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1563 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1564 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1565 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1567 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1568 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1569 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1571 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1572 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1573 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/*      */   public final void mulNormalize(Matrix3f m1) {
/* 1585 */     double[] tmp = new double[9];
/* 1586 */     double[] tmp_rot = new double[9];
/* 1587 */     double[] tmp_scale = new double[3];
/*      */     
/* 1589 */     tmp[0] = (this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20);
/* 1590 */     tmp[1] = (this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21);
/* 1591 */     tmp[2] = (this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22);
/*      */     
/* 1593 */     tmp[3] = (this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20);
/* 1594 */     tmp[4] = (this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21);
/* 1595 */     tmp[5] = (this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22);
/*      */     
/* 1597 */     tmp[6] = (this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20);
/* 1598 */     tmp[7] = (this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21);
/* 1599 */     tmp[8] = (this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22);
/*      */     
/* 1601 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1603 */     this.m00 = (float)tmp_rot[0];
/* 1604 */     this.m01 = (float)tmp_rot[1];
/* 1605 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1607 */     this.m10 = (float)tmp_rot[3];
/* 1608 */     this.m11 = (float)tmp_rot[4];
/* 1609 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1611 */     this.m20 = (float)tmp_rot[6];
/* 1612 */     this.m21 = (float)tmp_rot[7];
/* 1613 */     this.m22 = (float)tmp_rot[8];
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
/*      */   public final void mulNormalize(Matrix3f m1, Matrix3f m2) {
/* 1626 */     double[] tmp = new double[9];
/* 1627 */     double[] tmp_rot = new double[9];
/* 1628 */     double[] tmp_scale = new double[3];
/*      */ 
/*      */     
/* 1631 */     tmp[0] = (m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20);
/* 1632 */     tmp[1] = (m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21);
/* 1633 */     tmp[2] = (m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22);
/*      */     
/* 1635 */     tmp[3] = (m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20);
/* 1636 */     tmp[4] = (m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21);
/* 1637 */     tmp[5] = (m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22);
/*      */     
/* 1639 */     tmp[6] = (m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20);
/* 1640 */     tmp[7] = (m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21);
/* 1641 */     tmp[8] = (m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22);
/*      */     
/* 1643 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1645 */     this.m00 = (float)tmp_rot[0];
/* 1646 */     this.m01 = (float)tmp_rot[1];
/* 1647 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1649 */     this.m10 = (float)tmp_rot[3];
/* 1650 */     this.m11 = (float)tmp_rot[4];
/* 1651 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1653 */     this.m20 = (float)tmp_rot[6];
/* 1654 */     this.m21 = (float)tmp_rot[7];
/* 1655 */     this.m22 = (float)tmp_rot[8];
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
/* 1666 */     if (this != m1 && this != m2) {
/* 1667 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1668 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1669 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1671 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1672 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1673 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1675 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1676 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1677 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1683 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1684 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1685 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1687 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1688 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1689 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1691 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1692 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1693 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1695 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1696 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1697 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/* 1711 */     if (this != m1 && this != m2) {
/* 1712 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1713 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1714 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1716 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1717 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1718 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1720 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1721 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1722 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1728 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1729 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1730 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1732 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1733 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1734 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1736 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1737 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1738 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1740 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1741 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1742 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/* 1754 */     if (this != m1 && this != m2) {
/* 1755 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1756 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1757 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1759 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1760 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1761 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1763 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1764 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1765 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1771 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1772 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1773 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1775 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1776 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1777 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1779 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1780 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1781 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1783 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1784 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1785 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1794 */     double[] tmp_rot = new double[9];
/* 1795 */     double[] tmp_scale = new double[3];
/* 1796 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1798 */     this.m00 = (float)tmp_rot[0];
/* 1799 */     this.m01 = (float)tmp_rot[1];
/* 1800 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1802 */     this.m10 = (float)tmp_rot[3];
/* 1803 */     this.m11 = (float)tmp_rot[4];
/* 1804 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1806 */     this.m20 = (float)tmp_rot[6];
/* 1807 */     this.m21 = (float)tmp_rot[7];
/* 1808 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize(Matrix3f m1) {
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
/* 1834 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1836 */     this.m00 = (float)tmp_rot[0];
/* 1837 */     this.m01 = (float)tmp_rot[1];
/* 1838 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1840 */     this.m10 = (float)tmp_rot[3];
/* 1841 */     this.m11 = (float)tmp_rot[4];
/* 1842 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1844 */     this.m20 = (float)tmp_rot[6];
/* 1845 */     this.m21 = (float)tmp_rot[7];
/* 1846 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP() {
/* 1855 */     float mag = 1.0F / (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20));
/* 1856 */     this.m00 *= mag;
/* 1857 */     this.m10 *= mag;
/* 1858 */     this.m20 *= mag;
/*      */     
/* 1860 */     mag = 1.0F / (float)Math.sqrt((this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21));
/* 1861 */     this.m01 *= mag;
/* 1862 */     this.m11 *= mag;
/* 1863 */     this.m21 *= mag;
/*      */     
/* 1865 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1866 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1867 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP(Matrix3f m1) {
/* 1878 */     float mag = 1.0F / (float)Math.sqrt((m1.m00 * m1.m00 + m1.m10 * m1.m10 + m1.m20 * m1.m20));
/* 1879 */     m1.m00 *= mag;
/* 1880 */     m1.m10 *= mag;
/* 1881 */     m1.m20 *= mag;
/*      */     
/* 1883 */     mag = 1.0F / (float)Math.sqrt((m1.m01 * m1.m01 + m1.m11 * m1.m11 + m1.m21 * m1.m21));
/* 1884 */     m1.m01 *= mag;
/* 1885 */     m1.m11 *= mag;
/* 1886 */     m1.m21 *= mag;
/*      */     
/* 1888 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1889 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1890 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
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
/* 1904 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22);
/*      */     }
/*      */     catch (NullPointerException e2) {
/*      */       
/* 1908 */       return false;
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
/*      */     
/* 1924 */     try { Matrix3f m2 = (Matrix3f)o1;
/* 1925 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22); }
/*      */     
/*      */     catch (ClassCastException e1)
/*      */     
/* 1929 */     { return false; }
/* 1930 */     catch (NullPointerException e2) { return false; }
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
/*      */   public boolean epsilonEquals(Matrix3f m1, float epsilon) {
/* 1944 */     boolean status = (Math.abs(this.m00 - m1.m00) <= epsilon);
/*      */     
/* 1946 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 1947 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false;
/*      */     
/* 1949 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 1950 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 1951 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false;
/*      */     
/* 1953 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 1954 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 1955 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false;
/*      */     
/* 1957 */     return status;
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
/* 1972 */     long bits = 1L;
/* 1973 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 1974 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 1975 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 1976 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 1977 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 1978 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 1979 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 1980 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 1981 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 1982 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 1991 */     this.m00 = 0.0F;
/* 1992 */     this.m01 = 0.0F;
/* 1993 */     this.m02 = 0.0F;
/*      */     
/* 1995 */     this.m10 = 0.0F;
/* 1996 */     this.m11 = 0.0F;
/* 1997 */     this.m12 = 0.0F;
/*      */     
/* 1999 */     this.m20 = 0.0F;
/* 2000 */     this.m21 = 0.0F;
/* 2001 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 2010 */     this.m00 = -this.m00;
/* 2011 */     this.m01 = -this.m01;
/* 2012 */     this.m02 = -this.m02;
/*      */     
/* 2014 */     this.m10 = -this.m10;
/* 2015 */     this.m11 = -this.m11;
/* 2016 */     this.m12 = -this.m12;
/*      */     
/* 2018 */     this.m20 = -this.m20;
/* 2019 */     this.m21 = -this.m21;
/* 2020 */     this.m22 = -this.m22;
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
/* 2031 */     this.m00 = -m1.m00;
/* 2032 */     this.m01 = -m1.m01;
/* 2033 */     this.m02 = -m1.m02;
/*      */     
/* 2035 */     this.m10 = -m1.m10;
/* 2036 */     this.m11 = -m1.m11;
/* 2037 */     this.m12 = -m1.m12;
/*      */     
/* 2039 */     this.m20 = -m1.m20;
/* 2040 */     this.m21 = -m1.m21;
/* 2041 */     this.m22 = -m1.m22;
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
/* 2052 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2053 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2054 */     float z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2055 */     t.set(x, y, z);
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
/* 2066 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2067 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2068 */     result.z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2069 */     result.x = x;
/* 2070 */     result.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getScaleRotate(double[] scales, double[] rot) {
/* 2078 */     double[] tmp = new double[9];
/* 2079 */     tmp[0] = this.m00;
/* 2080 */     tmp[1] = this.m01;
/* 2081 */     tmp[2] = this.m02;
/* 2082 */     tmp[3] = this.m10;
/* 2083 */     tmp[4] = this.m11;
/* 2084 */     tmp[5] = this.m12;
/* 2085 */     tmp[6] = this.m20;
/* 2086 */     tmp[7] = this.m21;
/* 2087 */     tmp[8] = this.m22;
/* 2088 */     Matrix3d.compute_svd(tmp, scales, rot);
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
/*      */   public Object clone() {
/* 2104 */     Matrix3f m1 = null;
/*      */     try {
/* 2106 */       m1 = (Matrix3f)super.clone();
/* 2107 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 2109 */       throw new InternalError();
/*      */     } 
/* 2111 */     return m1;
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
/* 2123 */     return this.m00;
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
/* 2134 */     this.m00 = m00;
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
/* 2146 */     return this.m01;
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
/* 2157 */     this.m01 = m01;
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
/* 2168 */     return this.m02;
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
/* 2179 */     this.m02 = m02;
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
/* 2190 */     return this.m10;
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
/* 2201 */     this.m10 = m10;
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
/* 2212 */     return this.m11;
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
/* 2223 */     this.m11 = m11;
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
/* 2234 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float m12) {
/* 2243 */     this.m12 = m12;
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
/* 2254 */     return this.m20;
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
/* 2265 */     this.m20 = m20;
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
/* 2276 */     return this.m21;
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
/* 2287 */     this.m21 = m21;
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
/* 2298 */     return this.m22;
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
/* 2309 */     this.m22 = m22;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Matrix3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */