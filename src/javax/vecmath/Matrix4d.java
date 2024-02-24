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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Matrix4d
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 8223903484171633710L;
/*      */   public double m00;
/*      */   public double m01;
/*      */   public double m02;
/*      */   public double m03;
/*      */   public double m10;
/*      */   public double m11;
/*      */   public double m12;
/*      */   public double m13;
/*      */   public double m20;
/*      */   public double m21;
/*      */   public double m22;
/*      */   public double m23;
/*      */   public double m30;
/*      */   public double m31;
/*      */   public double m32;
/*      */   public double m33;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public Matrix4d(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
/*  153 */     this.m00 = m00;
/*  154 */     this.m01 = m01;
/*  155 */     this.m02 = m02;
/*  156 */     this.m03 = m03;
/*      */     
/*  158 */     this.m10 = m10;
/*  159 */     this.m11 = m11;
/*  160 */     this.m12 = m12;
/*  161 */     this.m13 = m13;
/*      */     
/*  163 */     this.m20 = m20;
/*  164 */     this.m21 = m21;
/*  165 */     this.m22 = m22;
/*  166 */     this.m23 = m23;
/*      */     
/*  168 */     this.m30 = m30;
/*  169 */     this.m31 = m31;
/*  170 */     this.m32 = m32;
/*  171 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(double[] v) {
/*  182 */     this.m00 = v[0];
/*  183 */     this.m01 = v[1];
/*  184 */     this.m02 = v[2];
/*  185 */     this.m03 = v[3];
/*      */     
/*  187 */     this.m10 = v[4];
/*  188 */     this.m11 = v[5];
/*  189 */     this.m12 = v[6];
/*  190 */     this.m13 = v[7];
/*      */     
/*  192 */     this.m20 = v[8];
/*  193 */     this.m21 = v[9];
/*  194 */     this.m22 = v[10];
/*  195 */     this.m23 = v[11];
/*      */     
/*  197 */     this.m30 = v[12];
/*  198 */     this.m31 = v[13];
/*  199 */     this.m32 = v[14];
/*  200 */     this.m33 = v[15];
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
/*      */   public Matrix4d(Quat4d q1, Vector3d t1, double s) {
/*  215 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  216 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  217 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  219 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  220 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  221 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  223 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  224 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  225 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/*  227 */     this.m03 = t1.x;
/*  228 */     this.m13 = t1.y;
/*  229 */     this.m23 = t1.z;
/*      */     
/*  231 */     this.m30 = 0.0D;
/*  232 */     this.m31 = 0.0D;
/*  233 */     this.m32 = 0.0D;
/*  234 */     this.m33 = 1.0D;
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
/*      */   public Matrix4d(Quat4f q1, Vector3d t1, double s) {
/*  249 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  250 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  251 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  253 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  254 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  255 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  257 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  258 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  259 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/*  261 */     this.m03 = t1.x;
/*  262 */     this.m13 = t1.y;
/*  263 */     this.m23 = t1.z;
/*      */     
/*  265 */     this.m30 = 0.0D;
/*  266 */     this.m31 = 0.0D;
/*  267 */     this.m32 = 0.0D;
/*  268 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4d m1) {
/*  279 */     this.m00 = m1.m00;
/*  280 */     this.m01 = m1.m01;
/*  281 */     this.m02 = m1.m02;
/*  282 */     this.m03 = m1.m03;
/*      */     
/*  284 */     this.m10 = m1.m10;
/*  285 */     this.m11 = m1.m11;
/*  286 */     this.m12 = m1.m12;
/*  287 */     this.m13 = m1.m13;
/*      */     
/*  289 */     this.m20 = m1.m20;
/*  290 */     this.m21 = m1.m21;
/*  291 */     this.m22 = m1.m22;
/*  292 */     this.m23 = m1.m23;
/*      */     
/*  294 */     this.m30 = m1.m30;
/*  295 */     this.m31 = m1.m31;
/*  296 */     this.m32 = m1.m32;
/*  297 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4f m1) {
/*  308 */     this.m00 = m1.m00;
/*  309 */     this.m01 = m1.m01;
/*  310 */     this.m02 = m1.m02;
/*  311 */     this.m03 = m1.m03;
/*      */     
/*  313 */     this.m10 = m1.m10;
/*  314 */     this.m11 = m1.m11;
/*  315 */     this.m12 = m1.m12;
/*  316 */     this.m13 = m1.m13;
/*      */     
/*  318 */     this.m20 = m1.m20;
/*  319 */     this.m21 = m1.m21;
/*  320 */     this.m22 = m1.m22;
/*  321 */     this.m23 = m1.m23;
/*      */     
/*  323 */     this.m30 = m1.m30;
/*  324 */     this.m31 = m1.m31;
/*  325 */     this.m32 = m1.m32;
/*  326 */     this.m33 = m1.m33;
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
/*      */   public Matrix4d(Matrix3f m1, Vector3d t1, double s) {
/*  341 */     this.m00 = m1.m00 * s;
/*  342 */     this.m01 = m1.m01 * s;
/*  343 */     this.m02 = m1.m02 * s;
/*  344 */     this.m03 = t1.x;
/*      */     
/*  346 */     this.m10 = m1.m10 * s;
/*  347 */     this.m11 = m1.m11 * s;
/*  348 */     this.m12 = m1.m12 * s;
/*  349 */     this.m13 = t1.y;
/*      */     
/*  351 */     this.m20 = m1.m20 * s;
/*  352 */     this.m21 = m1.m21 * s;
/*  353 */     this.m22 = m1.m22 * s;
/*  354 */     this.m23 = t1.z;
/*      */     
/*  356 */     this.m30 = 0.0D;
/*  357 */     this.m31 = 0.0D;
/*  358 */     this.m32 = 0.0D;
/*  359 */     this.m33 = 1.0D;
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
/*      */   public Matrix4d(Matrix3d m1, Vector3d t1, double s) {
/*  374 */     this.m00 = m1.m00 * s;
/*  375 */     this.m01 = m1.m01 * s;
/*  376 */     this.m02 = m1.m02 * s;
/*  377 */     this.m03 = t1.x;
/*      */     
/*  379 */     this.m10 = m1.m10 * s;
/*  380 */     this.m11 = m1.m11 * s;
/*  381 */     this.m12 = m1.m12 * s;
/*  382 */     this.m13 = t1.y;
/*      */     
/*  384 */     this.m20 = m1.m20 * s;
/*  385 */     this.m21 = m1.m21 * s;
/*  386 */     this.m22 = m1.m22 * s;
/*  387 */     this.m23 = t1.z;
/*      */     
/*  389 */     this.m30 = 0.0D;
/*  390 */     this.m31 = 0.0D;
/*  391 */     this.m32 = 0.0D;
/*  392 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d() {
/*  401 */     this.m00 = 0.0D;
/*  402 */     this.m01 = 0.0D;
/*  403 */     this.m02 = 0.0D;
/*  404 */     this.m03 = 0.0D;
/*      */     
/*  406 */     this.m10 = 0.0D;
/*  407 */     this.m11 = 0.0D;
/*  408 */     this.m12 = 0.0D;
/*  409 */     this.m13 = 0.0D;
/*      */     
/*  411 */     this.m20 = 0.0D;
/*  412 */     this.m21 = 0.0D;
/*  413 */     this.m22 = 0.0D;
/*  414 */     this.m23 = 0.0D;
/*      */     
/*  416 */     this.m30 = 0.0D;
/*  417 */     this.m31 = 0.0D;
/*  418 */     this.m32 = 0.0D;
/*  419 */     this.m33 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  429 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
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
/*      */   public final void setIdentity() {
/*  441 */     this.m00 = 1.0D;
/*  442 */     this.m01 = 0.0D;
/*  443 */     this.m02 = 0.0D;
/*  444 */     this.m03 = 0.0D;
/*      */     
/*  446 */     this.m10 = 0.0D;
/*  447 */     this.m11 = 1.0D;
/*  448 */     this.m12 = 0.0D;
/*  449 */     this.m13 = 0.0D;
/*      */     
/*  451 */     this.m20 = 0.0D;
/*  452 */     this.m21 = 0.0D;
/*  453 */     this.m22 = 1.0D;
/*  454 */     this.m23 = 0.0D;
/*      */     
/*  456 */     this.m30 = 0.0D;
/*  457 */     this.m31 = 0.0D;
/*  458 */     this.m32 = 0.0D;
/*  459 */     this.m33 = 1.0D;
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
/*  470 */     switch (row) {
/*      */       
/*      */       case 0:
/*  473 */         switch (column) {
/*      */           
/*      */           case 0:
/*  476 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  479 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  482 */             this.m02 = value;
/*      */             return;
/*      */           case 3:
/*  485 */             this.m03 = value;
/*      */             return;
/*      */         } 
/*  488 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  493 */         switch (column) {
/*      */           
/*      */           case 0:
/*  496 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  499 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  502 */             this.m12 = value;
/*      */             return;
/*      */           case 3:
/*  505 */             this.m13 = value;
/*      */             return;
/*      */         } 
/*  508 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  513 */         switch (column) {
/*      */           
/*      */           case 0:
/*  516 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  519 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  522 */             this.m22 = value;
/*      */             return;
/*      */           case 3:
/*  525 */             this.m23 = value;
/*      */             return;
/*      */         } 
/*  528 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  533 */         switch (column) {
/*      */           
/*      */           case 0:
/*  536 */             this.m30 = value;
/*      */             return;
/*      */           case 1:
/*  539 */             this.m31 = value;
/*      */             return;
/*      */           case 2:
/*  542 */             this.m32 = value;
/*      */             return;
/*      */           case 3:
/*  545 */             this.m33 = value;
/*      */             return;
/*      */         } 
/*  548 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  553 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
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
/*      */   public final double getElement(int row, int column) {
/*  565 */     switch (row) {
/*      */       
/*      */       case 0:
/*  568 */         switch (column) {
/*      */           
/*      */           case 0:
/*  571 */             return this.m00;
/*      */           case 1:
/*  573 */             return this.m01;
/*      */           case 2:
/*  575 */             return this.m02;
/*      */           case 3:
/*  577 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  583 */         switch (column) {
/*      */           
/*      */           case 0:
/*  586 */             return this.m10;
/*      */           case 1:
/*  588 */             return this.m11;
/*      */           case 2:
/*  590 */             return this.m12;
/*      */           case 3:
/*  592 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  599 */         switch (column) {
/*      */           
/*      */           case 0:
/*  602 */             return this.m20;
/*      */           case 1:
/*  604 */             return this.m21;
/*      */           case 2:
/*  606 */             return this.m22;
/*      */           case 3:
/*  608 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  615 */         switch (column) {
/*      */           
/*      */           case 0:
/*  618 */             return this.m30;
/*      */           case 1:
/*  620 */             return this.m31;
/*      */           case 2:
/*  622 */             return this.m32;
/*      */           case 3:
/*  624 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  633 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector4d v) {
/*  642 */     if (row == 0) {
/*  643 */       v.x = this.m00;
/*  644 */       v.y = this.m01;
/*  645 */       v.z = this.m02;
/*  646 */       v.w = this.m03;
/*  647 */     } else if (row == 1) {
/*  648 */       v.x = this.m10;
/*  649 */       v.y = this.m11;
/*  650 */       v.z = this.m12;
/*  651 */       v.w = this.m13;
/*  652 */     } else if (row == 2) {
/*  653 */       v.x = this.m20;
/*  654 */       v.y = this.m21;
/*  655 */       v.z = this.m22;
/*  656 */       v.w = this.m23;
/*  657 */     } else if (row == 3) {
/*  658 */       v.x = this.m30;
/*  659 */       v.y = this.m31;
/*  660 */       v.z = this.m32;
/*  661 */       v.w = this.m33;
/*      */     } else {
/*  663 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
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
/*  674 */     if (row == 0) {
/*  675 */       v[0] = this.m00;
/*  676 */       v[1] = this.m01;
/*  677 */       v[2] = this.m02;
/*  678 */       v[3] = this.m03;
/*  679 */     } else if (row == 1) {
/*  680 */       v[0] = this.m10;
/*  681 */       v[1] = this.m11;
/*  682 */       v[2] = this.m12;
/*  683 */       v[3] = this.m13;
/*  684 */     } else if (row == 2) {
/*  685 */       v[0] = this.m20;
/*  686 */       v[1] = this.m21;
/*  687 */       v[2] = this.m22;
/*  688 */       v[3] = this.m23;
/*  689 */     } else if (row == 3) {
/*  690 */       v[0] = this.m30;
/*  691 */       v[1] = this.m31;
/*  692 */       v[2] = this.m32;
/*  693 */       v[3] = this.m33;
/*      */     } else {
/*      */       
/*  696 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
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
/*      */   public final void getColumn(int column, Vector4d v) {
/*  709 */     if (column == 0) {
/*  710 */       v.x = this.m00;
/*  711 */       v.y = this.m10;
/*  712 */       v.z = this.m20;
/*  713 */       v.w = this.m30;
/*  714 */     } else if (column == 1) {
/*  715 */       v.x = this.m01;
/*  716 */       v.y = this.m11;
/*  717 */       v.z = this.m21;
/*  718 */       v.w = this.m31;
/*  719 */     } else if (column == 2) {
/*  720 */       v.x = this.m02;
/*  721 */       v.y = this.m12;
/*  722 */       v.z = this.m22;
/*  723 */       v.w = this.m32;
/*  724 */     } else if (column == 3) {
/*  725 */       v.x = this.m03;
/*  726 */       v.y = this.m13;
/*  727 */       v.z = this.m23;
/*  728 */       v.w = this.m33;
/*      */     } else {
/*  730 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
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
/*      */   public final void getColumn(int column, double[] v) {
/*  745 */     if (column == 0) {
/*  746 */       v[0] = this.m00;
/*  747 */       v[1] = this.m10;
/*  748 */       v[2] = this.m20;
/*  749 */       v[3] = this.m30;
/*  750 */     } else if (column == 1) {
/*  751 */       v[0] = this.m01;
/*  752 */       v[1] = this.m11;
/*  753 */       v[2] = this.m21;
/*  754 */       v[3] = this.m31;
/*  755 */     } else if (column == 2) {
/*  756 */       v[0] = this.m02;
/*  757 */       v[1] = this.m12;
/*  758 */       v[2] = this.m22;
/*  759 */       v[3] = this.m32;
/*  760 */     } else if (column == 3) {
/*  761 */       v[0] = this.m03;
/*  762 */       v[1] = this.m13;
/*  763 */       v[2] = this.m23;
/*  764 */       v[3] = this.m33;
/*      */     } else {
/*  766 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
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
/*      */   
/*      */   public final void get(Matrix3d m1) {
/*  782 */     double[] tmp_rot = new double[9];
/*  783 */     double[] tmp_scale = new double[3];
/*  784 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  786 */     m1.m00 = tmp_rot[0];
/*  787 */     m1.m01 = tmp_rot[1];
/*  788 */     m1.m02 = tmp_rot[2];
/*      */     
/*  790 */     m1.m10 = tmp_rot[3];
/*  791 */     m1.m11 = tmp_rot[4];
/*  792 */     m1.m12 = tmp_rot[5];
/*      */     
/*  794 */     m1.m20 = tmp_rot[6];
/*  795 */     m1.m21 = tmp_rot[7];
/*  796 */     m1.m22 = tmp_rot[8];
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
/*      */   public final void get(Matrix3f m1) {
/*  809 */     double[] tmp_rot = new double[9];
/*  810 */     double[] tmp_scale = new double[3];
/*      */     
/*  812 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  814 */     m1.m00 = (float)tmp_rot[0];
/*  815 */     m1.m01 = (float)tmp_rot[1];
/*  816 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  818 */     m1.m10 = (float)tmp_rot[3];
/*  819 */     m1.m11 = (float)tmp_rot[4];
/*  820 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  822 */     m1.m20 = (float)tmp_rot[6];
/*  823 */     m1.m21 = (float)tmp_rot[7];
/*  824 */     m1.m22 = (float)tmp_rot[8];
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
/*      */   public final double get(Matrix3d m1, Vector3d t1) {
/*  838 */     double[] tmp_rot = new double[9];
/*  839 */     double[] tmp_scale = new double[3];
/*  840 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  842 */     m1.m00 = tmp_rot[0];
/*  843 */     m1.m01 = tmp_rot[1];
/*  844 */     m1.m02 = tmp_rot[2];
/*      */     
/*  846 */     m1.m10 = tmp_rot[3];
/*  847 */     m1.m11 = tmp_rot[4];
/*  848 */     m1.m12 = tmp_rot[5];
/*      */     
/*  850 */     m1.m20 = tmp_rot[6];
/*  851 */     m1.m21 = tmp_rot[7];
/*  852 */     m1.m22 = tmp_rot[8];
/*      */     
/*  854 */     t1.x = this.m03;
/*  855 */     t1.y = this.m13;
/*  856 */     t1.z = this.m23;
/*      */     
/*  858 */     return Matrix3d.max3(tmp_scale);
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
/*      */   public final double get(Matrix3f m1, Vector3d t1) {
/*  872 */     double[] tmp_rot = new double[9];
/*  873 */     double[] tmp_scale = new double[3];
/*  874 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  876 */     m1.m00 = (float)tmp_rot[0];
/*  877 */     m1.m01 = (float)tmp_rot[1];
/*  878 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  880 */     m1.m10 = (float)tmp_rot[3];
/*  881 */     m1.m11 = (float)tmp_rot[4];
/*  882 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  884 */     m1.m20 = (float)tmp_rot[6];
/*  885 */     m1.m21 = (float)tmp_rot[7];
/*  886 */     m1.m22 = (float)tmp_rot[8];
/*      */     
/*  888 */     t1.x = this.m03;
/*  889 */     t1.y = this.m13;
/*  890 */     t1.z = this.m23;
/*      */     
/*  892 */     return Matrix3d.max3(tmp_scale);
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
/*      */   public final void get(Quat4f q1) {
/*  905 */     double[] tmp_rot = new double[9];
/*  906 */     double[] tmp_scale = new double[3];
/*  907 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  911 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  912 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  913 */       q1.w = (float)Math.sqrt(ww);
/*  914 */       ww = 0.25D / q1.w;
/*  915 */       q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
/*  916 */       q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
/*  917 */       q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  921 */     q1.w = 0.0F;
/*  922 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  923 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  924 */       q1.x = (float)Math.sqrt(ww);
/*  925 */       ww = 0.5D / q1.x;
/*  926 */       q1.y = (float)(tmp_rot[3] * ww);
/*  927 */       q1.z = (float)(tmp_rot[6] * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  931 */     q1.x = 0.0F;
/*  932 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  933 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  934 */       q1.y = (float)Math.sqrt(ww);
/*  935 */       q1.z = (float)(tmp_rot[7] / 2.0D * q1.y);
/*      */       
/*      */       return;
/*      */     } 
/*  939 */     q1.y = 0.0F;
/*  940 */     q1.z = 1.0F;
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
/*      */   public final void get(Quat4d q1) {
/*  952 */     double[] tmp_rot = new double[9];
/*  953 */     double[] tmp_scale = new double[3];
/*      */     
/*  955 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  959 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  960 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  961 */       q1.w = Math.sqrt(ww);
/*  962 */       ww = 0.25D / q1.w;
/*  963 */       q1.x = (tmp_rot[7] - tmp_rot[5]) * ww;
/*  964 */       q1.y = (tmp_rot[2] - tmp_rot[6]) * ww;
/*  965 */       q1.z = (tmp_rot[3] - tmp_rot[1]) * ww;
/*      */       
/*      */       return;
/*      */     } 
/*  969 */     q1.w = 0.0D;
/*  970 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  971 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  972 */       q1.x = Math.sqrt(ww);
/*  973 */       ww = 0.5D / q1.x;
/*  974 */       q1.y = tmp_rot[3] * ww;
/*  975 */       q1.z = tmp_rot[6] * ww;
/*      */       
/*      */       return;
/*      */     } 
/*  979 */     q1.x = 0.0D;
/*  980 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  981 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  982 */       q1.y = Math.sqrt(ww);
/*  983 */       q1.z = tmp_rot[7] / 2.0D * q1.y;
/*      */       
/*      */       return;
/*      */     } 
/*  987 */     q1.y = 0.0D;
/*  988 */     q1.z = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3d trans) {
/*  997 */     trans.x = this.m03;
/*  998 */     trans.y = this.m13;
/*  999 */     trans.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f m1) {
/* 1009 */     m1.m00 = (float)this.m00; m1.m01 = (float)this.m01; m1.m02 = (float)this.m02;
/* 1010 */     m1.m10 = (float)this.m10; m1.m11 = (float)this.m11; m1.m12 = (float)this.m12;
/* 1011 */     m1.m20 = (float)this.m20; m1.m21 = (float)this.m21; m1.m22 = (float)this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3d m1) {
/* 1021 */     m1.m00 = this.m00; m1.m01 = this.m01; m1.m02 = this.m02;
/* 1022 */     m1.m10 = this.m10; m1.m11 = this.m11; m1.m12 = this.m12;
/* 1023 */     m1.m20 = this.m20; m1.m21 = this.m21; m1.m22 = this.m22;
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
/*      */   public final double getScale() {
/* 1036 */     double[] tmp_rot = new double[9];
/* 1037 */     double[] tmp_scale = new double[3];
/* 1038 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1040 */     return Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3d m1) {
/* 1051 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02;
/* 1052 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12;
/* 1053 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3f m1) {
/* 1063 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02;
/* 1064 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12;
/* 1065 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(double scale) {
/* 1076 */     double[] tmp_rot = new double[9];
/* 1077 */     double[] tmp_scale = new double[3];
/*      */     
/* 1079 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1081 */     this.m00 = tmp_rot[0] * scale;
/* 1082 */     this.m01 = tmp_rot[1] * scale;
/* 1083 */     this.m02 = tmp_rot[2] * scale;
/*      */     
/* 1085 */     this.m10 = tmp_rot[3] * scale;
/* 1086 */     this.m11 = tmp_rot[4] * scale;
/* 1087 */     this.m12 = tmp_rot[5] * scale;
/*      */     
/* 1089 */     this.m20 = tmp_rot[6] * scale;
/* 1090 */     this.m21 = tmp_rot[7] * scale;
/* 1091 */     this.m22 = tmp_rot[8] * scale;
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
/*      */   public final void setRow(int row, double x, double y, double z, double w) {
/* 1105 */     switch (row) {
/*      */       case 0:
/* 1107 */         this.m00 = x;
/* 1108 */         this.m01 = y;
/* 1109 */         this.m02 = z;
/* 1110 */         this.m03 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1114 */         this.m10 = x;
/* 1115 */         this.m11 = y;
/* 1116 */         this.m12 = z;
/* 1117 */         this.m13 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1121 */         this.m20 = x;
/* 1122 */         this.m21 = y;
/* 1123 */         this.m22 = z;
/* 1124 */         this.m23 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1128 */         this.m30 = x;
/* 1129 */         this.m31 = y;
/* 1130 */         this.m32 = z;
/* 1131 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1135 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/*      */   public final void setRow(int row, Vector4d v) {
/* 1147 */     switch (row) {
/*      */       case 0:
/* 1149 */         this.m00 = v.x;
/* 1150 */         this.m01 = v.y;
/* 1151 */         this.m02 = v.z;
/* 1152 */         this.m03 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1156 */         this.m10 = v.x;
/* 1157 */         this.m11 = v.y;
/* 1158 */         this.m12 = v.z;
/* 1159 */         this.m13 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1163 */         this.m20 = v.x;
/* 1164 */         this.m21 = v.y;
/* 1165 */         this.m22 = v.z;
/* 1166 */         this.m23 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1170 */         this.m30 = v.x;
/* 1171 */         this.m31 = v.y;
/* 1172 */         this.m32 = v.z;
/* 1173 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1177 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/* 1188 */     switch (row) {
/*      */       case 0:
/* 1190 */         this.m00 = v[0];
/* 1191 */         this.m01 = v[1];
/* 1192 */         this.m02 = v[2];
/* 1193 */         this.m03 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1197 */         this.m10 = v[0];
/* 1198 */         this.m11 = v[1];
/* 1199 */         this.m12 = v[2];
/* 1200 */         this.m13 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1204 */         this.m20 = v[0];
/* 1205 */         this.m21 = v[1];
/* 1206 */         this.m22 = v[2];
/* 1207 */         this.m23 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1211 */         this.m30 = v[0];
/* 1212 */         this.m31 = v[1];
/* 1213 */         this.m32 = v[2];
/* 1214 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1218 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/*      */   public final void setColumn(int column, double x, double y, double z, double w) {
/* 1232 */     switch (column) {
/*      */       case 0:
/* 1234 */         this.m00 = x;
/* 1235 */         this.m10 = y;
/* 1236 */         this.m20 = z;
/* 1237 */         this.m30 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1241 */         this.m01 = x;
/* 1242 */         this.m11 = y;
/* 1243 */         this.m21 = z;
/* 1244 */         this.m31 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1248 */         this.m02 = x;
/* 1249 */         this.m12 = y;
/* 1250 */         this.m22 = z;
/* 1251 */         this.m32 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1255 */         this.m03 = x;
/* 1256 */         this.m13 = y;
/* 1257 */         this.m23 = z;
/* 1258 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1262 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector4d v) {
/* 1273 */     switch (column) {
/*      */       case 0:
/* 1275 */         this.m00 = v.x;
/* 1276 */         this.m10 = v.y;
/* 1277 */         this.m20 = v.z;
/* 1278 */         this.m30 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1282 */         this.m01 = v.x;
/* 1283 */         this.m11 = v.y;
/* 1284 */         this.m21 = v.z;
/* 1285 */         this.m31 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1289 */         this.m02 = v.x;
/* 1290 */         this.m12 = v.y;
/* 1291 */         this.m22 = v.z;
/* 1292 */         this.m32 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1296 */         this.m03 = v.x;
/* 1297 */         this.m13 = v.y;
/* 1298 */         this.m23 = v.z;
/* 1299 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1303 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
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
/* 1314 */     switch (column) {
/*      */       case 0:
/* 1316 */         this.m00 = v[0];
/* 1317 */         this.m10 = v[1];
/* 1318 */         this.m20 = v[2];
/* 1319 */         this.m30 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1323 */         this.m01 = v[0];
/* 1324 */         this.m11 = v[1];
/* 1325 */         this.m21 = v[2];
/* 1326 */         this.m31 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1330 */         this.m02 = v[0];
/* 1331 */         this.m12 = v[1];
/* 1332 */         this.m22 = v[2];
/* 1333 */         this.m32 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1337 */         this.m03 = v[0];
/* 1338 */         this.m13 = v[1];
/* 1339 */         this.m23 = v[2];
/* 1340 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1344 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar) {
/* 1354 */     this.m00 += scalar;
/* 1355 */     this.m01 += scalar;
/* 1356 */     this.m02 += scalar;
/* 1357 */     this.m03 += scalar;
/* 1358 */     this.m10 += scalar;
/* 1359 */     this.m11 += scalar;
/* 1360 */     this.m12 += scalar;
/* 1361 */     this.m13 += scalar;
/* 1362 */     this.m20 += scalar;
/* 1363 */     this.m21 += scalar;
/* 1364 */     this.m22 += scalar;
/* 1365 */     this.m23 += scalar;
/* 1366 */     this.m30 += scalar;
/* 1367 */     this.m31 += scalar;
/* 1368 */     this.m32 += scalar;
/* 1369 */     this.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar, Matrix4d m1) {
/* 1380 */     m1.m00 += scalar;
/* 1381 */     m1.m01 += scalar;
/* 1382 */     m1.m02 += scalar;
/* 1383 */     m1.m03 += scalar;
/* 1384 */     m1.m10 += scalar;
/* 1385 */     m1.m11 += scalar;
/* 1386 */     m1.m12 += scalar;
/* 1387 */     m1.m13 += scalar;
/* 1388 */     m1.m20 += scalar;
/* 1389 */     m1.m21 += scalar;
/* 1390 */     m1.m22 += scalar;
/* 1391 */     m1.m23 += scalar;
/* 1392 */     m1.m30 += scalar;
/* 1393 */     m1.m31 += scalar;
/* 1394 */     m1.m32 += scalar;
/* 1395 */     m1.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d m1, Matrix4d m2) {
/* 1405 */     m1.m00 += m2.m00;
/* 1406 */     m1.m01 += m2.m01;
/* 1407 */     m1.m02 += m2.m02;
/* 1408 */     m1.m03 += m2.m03;
/*      */     
/* 1410 */     m1.m10 += m2.m10;
/* 1411 */     m1.m11 += m2.m11;
/* 1412 */     m1.m12 += m2.m12;
/* 1413 */     m1.m13 += m2.m13;
/*      */     
/* 1415 */     m1.m20 += m2.m20;
/* 1416 */     m1.m21 += m2.m21;
/* 1417 */     m1.m22 += m2.m22;
/* 1418 */     m1.m23 += m2.m23;
/*      */     
/* 1420 */     m1.m30 += m2.m30;
/* 1421 */     m1.m31 += m2.m31;
/* 1422 */     m1.m32 += m2.m32;
/* 1423 */     m1.m33 += m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d m1) {
/* 1432 */     this.m00 += m1.m00;
/* 1433 */     this.m01 += m1.m01;
/* 1434 */     this.m02 += m1.m02;
/* 1435 */     this.m03 += m1.m03;
/*      */     
/* 1437 */     this.m10 += m1.m10;
/* 1438 */     this.m11 += m1.m11;
/* 1439 */     this.m12 += m1.m12;
/* 1440 */     this.m13 += m1.m13;
/*      */     
/* 1442 */     this.m20 += m1.m20;
/* 1443 */     this.m21 += m1.m21;
/* 1444 */     this.m22 += m1.m22;
/* 1445 */     this.m23 += m1.m23;
/*      */     
/* 1447 */     this.m30 += m1.m30;
/* 1448 */     this.m31 += m1.m31;
/* 1449 */     this.m32 += m1.m32;
/* 1450 */     this.m33 += m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d m1, Matrix4d m2) {
/* 1461 */     m1.m00 -= m2.m00;
/* 1462 */     m1.m01 -= m2.m01;
/* 1463 */     m1.m02 -= m2.m02;
/* 1464 */     m1.m03 -= m2.m03;
/*      */     
/* 1466 */     m1.m10 -= m2.m10;
/* 1467 */     m1.m11 -= m2.m11;
/* 1468 */     m1.m12 -= m2.m12;
/* 1469 */     m1.m13 -= m2.m13;
/*      */     
/* 1471 */     m1.m20 -= m2.m20;
/* 1472 */     m1.m21 -= m2.m21;
/* 1473 */     m1.m22 -= m2.m22;
/* 1474 */     m1.m23 -= m2.m23;
/*      */     
/* 1476 */     m1.m30 -= m2.m30;
/* 1477 */     m1.m31 -= m2.m31;
/* 1478 */     m1.m32 -= m2.m32;
/* 1479 */     m1.m33 -= m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d m1) {
/* 1490 */     this.m00 -= m1.m00;
/* 1491 */     this.m01 -= m1.m01;
/* 1492 */     this.m02 -= m1.m02;
/* 1493 */     this.m03 -= m1.m03;
/*      */     
/* 1495 */     this.m10 -= m1.m10;
/* 1496 */     this.m11 -= m1.m11;
/* 1497 */     this.m12 -= m1.m12;
/* 1498 */     this.m13 -= m1.m13;
/*      */     
/* 1500 */     this.m20 -= m1.m20;
/* 1501 */     this.m21 -= m1.m21;
/* 1502 */     this.m22 -= m1.m22;
/* 1503 */     this.m23 -= m1.m23;
/*      */     
/* 1505 */     this.m30 -= m1.m30;
/* 1506 */     this.m31 -= m1.m31;
/* 1507 */     this.m32 -= m1.m32;
/* 1508 */     this.m33 -= m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1518 */     double temp = this.m10;
/* 1519 */     this.m10 = this.m01;
/* 1520 */     this.m01 = temp;
/*      */     
/* 1522 */     temp = this.m20;
/* 1523 */     this.m20 = this.m02;
/* 1524 */     this.m02 = temp;
/*      */     
/* 1526 */     temp = this.m30;
/* 1527 */     this.m30 = this.m03;
/* 1528 */     this.m03 = temp;
/*      */     
/* 1530 */     temp = this.m21;
/* 1531 */     this.m21 = this.m12;
/* 1532 */     this.m12 = temp;
/*      */     
/* 1534 */     temp = this.m31;
/* 1535 */     this.m31 = this.m13;
/* 1536 */     this.m13 = temp;
/*      */     
/* 1538 */     temp = this.m32;
/* 1539 */     this.m32 = this.m23;
/* 1540 */     this.m23 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4d m1) {
/* 1549 */     if (this != m1) {
/* 1550 */       this.m00 = m1.m00;
/* 1551 */       this.m01 = m1.m10;
/* 1552 */       this.m02 = m1.m20;
/* 1553 */       this.m03 = m1.m30;
/*      */       
/* 1555 */       this.m10 = m1.m01;
/* 1556 */       this.m11 = m1.m11;
/* 1557 */       this.m12 = m1.m21;
/* 1558 */       this.m13 = m1.m31;
/*      */       
/* 1560 */       this.m20 = m1.m02;
/* 1561 */       this.m21 = m1.m12;
/* 1562 */       this.m22 = m1.m22;
/* 1563 */       this.m23 = m1.m32;
/*      */       
/* 1565 */       this.m30 = m1.m03;
/* 1566 */       this.m31 = m1.m13;
/* 1567 */       this.m32 = m1.m23;
/* 1568 */       this.m33 = m1.m33;
/*      */     } else {
/* 1570 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double[] m) {
/* 1581 */     this.m00 = m[0];
/* 1582 */     this.m01 = m[1];
/* 1583 */     this.m02 = m[2];
/* 1584 */     this.m03 = m[3];
/* 1585 */     this.m10 = m[4];
/* 1586 */     this.m11 = m[5];
/* 1587 */     this.m12 = m[6];
/* 1588 */     this.m13 = m[7];
/* 1589 */     this.m20 = m[8];
/* 1590 */     this.m21 = m[9];
/* 1591 */     this.m22 = m[10];
/* 1592 */     this.m23 = m[11];
/* 1593 */     this.m30 = m[12];
/* 1594 */     this.m31 = m[13];
/* 1595 */     this.m32 = m[14];
/* 1596 */     this.m33 = m[15];
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
/* 1608 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02; this.m03 = 0.0D;
/* 1609 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12; this.m13 = 0.0D;
/* 1610 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22; this.m23 = 0.0D;
/* 1611 */     this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
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
/* 1623 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02; this.m03 = 0.0D;
/* 1624 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12; this.m13 = 0.0D;
/* 1625 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22; this.m23 = 0.0D;
/* 1626 */     this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/* 1636 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/* 1637 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1638 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1640 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1641 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/* 1642 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1644 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1645 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1646 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */     
/* 1648 */     this.m03 = 0.0D;
/* 1649 */     this.m13 = 0.0D;
/* 1650 */     this.m23 = 0.0D;
/*      */     
/* 1652 */     this.m30 = 0.0D;
/* 1653 */     this.m31 = 0.0D;
/* 1654 */     this.m32 = 0.0D;
/* 1655 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/* 1665 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/* 1667 */     if (mag < 1.0E-10D) {
/* 1668 */       this.m00 = 1.0D;
/* 1669 */       this.m01 = 0.0D;
/* 1670 */       this.m02 = 0.0D;
/*      */       
/* 1672 */       this.m10 = 0.0D;
/* 1673 */       this.m11 = 1.0D;
/* 1674 */       this.m12 = 0.0D;
/*      */       
/* 1676 */       this.m20 = 0.0D;
/* 1677 */       this.m21 = 0.0D;
/* 1678 */       this.m22 = 1.0D;
/*      */     } else {
/* 1680 */       mag = 1.0D / mag;
/* 1681 */       double ax = a1.x * mag;
/* 1682 */       double ay = a1.y * mag;
/* 1683 */       double az = a1.z * mag;
/*      */       
/* 1685 */       double sinTheta = Math.sin(a1.angle);
/* 1686 */       double cosTheta = Math.cos(a1.angle);
/* 1687 */       double t = 1.0D - cosTheta;
/*      */       
/* 1689 */       double xz = ax * az;
/* 1690 */       double xy = ax * ay;
/* 1691 */       double yz = ay * az;
/*      */       
/* 1693 */       this.m00 = t * ax * ax + cosTheta;
/* 1694 */       this.m01 = t * xy - sinTheta * az;
/* 1695 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1697 */       this.m10 = t * xy + sinTheta * az;
/* 1698 */       this.m11 = t * ay * ay + cosTheta;
/* 1699 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1701 */       this.m20 = t * xz - sinTheta * ay;
/* 1702 */       this.m21 = t * yz + sinTheta * ax;
/* 1703 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */     
/* 1706 */     this.m03 = 0.0D;
/* 1707 */     this.m13 = 0.0D;
/* 1708 */     this.m23 = 0.0D;
/*      */     
/* 1710 */     this.m30 = 0.0D;
/* 1711 */     this.m31 = 0.0D;
/* 1712 */     this.m32 = 0.0D;
/* 1713 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/* 1723 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/* 1724 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1725 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1727 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1728 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/* 1729 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1731 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1732 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1733 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */     
/* 1735 */     this.m03 = 0.0D;
/* 1736 */     this.m13 = 0.0D;
/* 1737 */     this.m23 = 0.0D;
/*      */     
/* 1739 */     this.m30 = 0.0D;
/* 1740 */     this.m31 = 0.0D;
/* 1741 */     this.m32 = 0.0D;
/* 1742 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/* 1752 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*      */     
/* 1754 */     if (mag < 1.0E-10D) {
/* 1755 */       this.m00 = 1.0D;
/* 1756 */       this.m01 = 0.0D;
/* 1757 */       this.m02 = 0.0D;
/*      */       
/* 1759 */       this.m10 = 0.0D;
/* 1760 */       this.m11 = 1.0D;
/* 1761 */       this.m12 = 0.0D;
/*      */       
/* 1763 */       this.m20 = 0.0D;
/* 1764 */       this.m21 = 0.0D;
/* 1765 */       this.m22 = 1.0D;
/*      */     } else {
/* 1767 */       mag = 1.0D / mag;
/* 1768 */       double ax = a1.x * mag;
/* 1769 */       double ay = a1.y * mag;
/* 1770 */       double az = a1.z * mag;
/*      */       
/* 1772 */       double sinTheta = Math.sin(a1.angle);
/* 1773 */       double cosTheta = Math.cos(a1.angle);
/* 1774 */       double t = 1.0D - cosTheta;
/*      */       
/* 1776 */       double xz = ax * az;
/* 1777 */       double xy = ax * ay;
/* 1778 */       double yz = ay * az;
/*      */       
/* 1780 */       this.m00 = t * ax * ax + cosTheta;
/* 1781 */       this.m01 = t * xy - sinTheta * az;
/* 1782 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1784 */       this.m10 = t * xy + sinTheta * az;
/* 1785 */       this.m11 = t * ay * ay + cosTheta;
/* 1786 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1788 */       this.m20 = t * xz - sinTheta * ay;
/* 1789 */       this.m21 = t * yz + sinTheta * ax;
/* 1790 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/* 1792 */     this.m03 = 0.0D;
/* 1793 */     this.m13 = 0.0D;
/* 1794 */     this.m23 = 0.0D;
/*      */     
/* 1796 */     this.m30 = 0.0D;
/* 1797 */     this.m31 = 0.0D;
/* 1798 */     this.m32 = 0.0D;
/* 1799 */     this.m33 = 1.0D;
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
/*      */   public final void set(Quat4d q1, Vector3d t1, double s) {
/* 1811 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1812 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1813 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1815 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1816 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1817 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1819 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1820 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1821 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1823 */     this.m03 = t1.x;
/* 1824 */     this.m13 = t1.y;
/* 1825 */     this.m23 = t1.z;
/*      */     
/* 1827 */     this.m30 = 0.0D;
/* 1828 */     this.m31 = 0.0D;
/* 1829 */     this.m32 = 0.0D;
/* 1830 */     this.m33 = 1.0D;
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
/*      */   public final void set(Quat4f q1, Vector3d t1, double s) {
/* 1842 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1843 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1844 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1846 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1847 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1848 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1850 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1851 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1852 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1854 */     this.m03 = t1.x;
/* 1855 */     this.m13 = t1.y;
/* 1856 */     this.m23 = t1.z;
/*      */     
/* 1858 */     this.m30 = 0.0D;
/* 1859 */     this.m31 = 0.0D;
/* 1860 */     this.m32 = 0.0D;
/* 1861 */     this.m33 = 1.0D;
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
/*      */   public final void set(Quat4f q1, Vector3f t1, float s) {
/* 1873 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1874 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1875 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1877 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1878 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1879 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1881 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1882 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1883 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1885 */     this.m03 = t1.x;
/* 1886 */     this.m13 = t1.y;
/* 1887 */     this.m23 = t1.z;
/*      */     
/* 1889 */     this.m30 = 0.0D;
/* 1890 */     this.m31 = 0.0D;
/* 1891 */     this.m32 = 0.0D;
/* 1892 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/* 1902 */     this.m00 = m1.m00;
/* 1903 */     this.m01 = m1.m01;
/* 1904 */     this.m02 = m1.m02;
/* 1905 */     this.m03 = m1.m03;
/*      */     
/* 1907 */     this.m10 = m1.m10;
/* 1908 */     this.m11 = m1.m11;
/* 1909 */     this.m12 = m1.m12;
/* 1910 */     this.m13 = m1.m13;
/*      */     
/* 1912 */     this.m20 = m1.m20;
/* 1913 */     this.m21 = m1.m21;
/* 1914 */     this.m22 = m1.m22;
/* 1915 */     this.m23 = m1.m23;
/*      */     
/* 1917 */     this.m30 = m1.m30;
/* 1918 */     this.m31 = m1.m31;
/* 1919 */     this.m32 = m1.m32;
/* 1920 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/* 1930 */     this.m00 = m1.m00;
/* 1931 */     this.m01 = m1.m01;
/* 1932 */     this.m02 = m1.m02;
/* 1933 */     this.m03 = m1.m03;
/*      */     
/* 1935 */     this.m10 = m1.m10;
/* 1936 */     this.m11 = m1.m11;
/* 1937 */     this.m12 = m1.m12;
/* 1938 */     this.m13 = m1.m13;
/*      */     
/* 1940 */     this.m20 = m1.m20;
/* 1941 */     this.m21 = m1.m21;
/* 1942 */     this.m22 = m1.m22;
/* 1943 */     this.m23 = m1.m23;
/*      */     
/* 1945 */     this.m30 = m1.m30;
/* 1946 */     this.m31 = m1.m31;
/* 1947 */     this.m32 = m1.m32;
/* 1948 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4d m1) {
/* 1959 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1967 */     invertGeneral(this);
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
/*      */   final void invertGeneral(Matrix4d m1) {
/* 1979 */     double[] result = new double[16];
/* 1980 */     int[] row_perm = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1985 */     double[] tmp = new double[16];
/*      */     
/* 1987 */     tmp[0] = m1.m00;
/* 1988 */     tmp[1] = m1.m01;
/* 1989 */     tmp[2] = m1.m02;
/* 1990 */     tmp[3] = m1.m03;
/*      */     
/* 1992 */     tmp[4] = m1.m10;
/* 1993 */     tmp[5] = m1.m11;
/* 1994 */     tmp[6] = m1.m12;
/* 1995 */     tmp[7] = m1.m13;
/*      */     
/* 1997 */     tmp[8] = m1.m20;
/* 1998 */     tmp[9] = m1.m21;
/* 1999 */     tmp[10] = m1.m22;
/* 2000 */     tmp[11] = m1.m23;
/*      */     
/* 2002 */     tmp[12] = m1.m30;
/* 2003 */     tmp[13] = m1.m31;
/* 2004 */     tmp[14] = m1.m32;
/* 2005 */     tmp[15] = m1.m33;
/*      */ 
/*      */     
/* 2008 */     if (!luDecomposition(tmp, row_perm))
/*      */     {
/* 2010 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4d10"));
/*      */     }
/*      */ 
/*      */     
/* 2014 */     for (int i = 0; i < 16; ) { result[i] = 0.0D; i++; }
/* 2015 */      result[0] = 1.0D; result[5] = 1.0D; result[10] = 1.0D; result[15] = 1.0D;
/* 2016 */     luBacksubstitution(tmp, row_perm, result);
/*      */     
/* 2018 */     this.m00 = result[0];
/* 2019 */     this.m01 = result[1];
/* 2020 */     this.m02 = result[2];
/* 2021 */     this.m03 = result[3];
/*      */     
/* 2023 */     this.m10 = result[4];
/* 2024 */     this.m11 = result[5];
/* 2025 */     this.m12 = result[6];
/* 2026 */     this.m13 = result[7];
/*      */     
/* 2028 */     this.m20 = result[8];
/* 2029 */     this.m21 = result[9];
/* 2030 */     this.m22 = result[10];
/* 2031 */     this.m23 = result[11];
/*      */     
/* 2033 */     this.m30 = result[12];
/* 2034 */     this.m31 = result[13];
/* 2035 */     this.m32 = result[14];
/* 2036 */     this.m33 = result[15];
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
/* 2063 */     double[] row_scale = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2071 */     int ptr = 0;
/* 2072 */     int rs = 0;
/*      */ 
/*      */     
/* 2075 */     int i = 4;
/* 2076 */     while (i-- != 0) {
/* 2077 */       double big = 0.0D;
/*      */ 
/*      */       
/* 2080 */       int k = 4;
/* 2081 */       while (k-- != 0) {
/* 2082 */         double temp = matrix0[ptr++];
/* 2083 */         temp = Math.abs(temp);
/* 2084 */         if (temp > big) {
/* 2085 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2090 */       if (big == 0.0D) {
/* 2091 */         return false;
/*      */       }
/* 2093 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2101 */     int mtx = 0;
/*      */ 
/*      */     
/* 2104 */     for (int j = 0; j < 4; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2110 */       for (k = 0; k < j; k++) {
/* 2111 */         int target = mtx + 4 * k + j;
/* 2112 */         double sum = matrix0[target];
/* 2113 */         int m = k;
/* 2114 */         int p1 = mtx + 4 * k;
/* 2115 */         int p2 = mtx + j;
/* 2116 */         while (m-- != 0) {
/* 2117 */           sum -= matrix0[p1] * matrix0[p2];
/* 2118 */           p1++;
/* 2119 */           p2 += 4;
/*      */         } 
/* 2121 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2126 */       double big = 0.0D;
/* 2127 */       int imax = -1;
/* 2128 */       for (k = j; k < 4; k++) {
/* 2129 */         int target = mtx + 4 * k + j;
/* 2130 */         double sum = matrix0[target];
/* 2131 */         int m = j;
/* 2132 */         int p1 = mtx + 4 * k;
/* 2133 */         int p2 = mtx + j;
/* 2134 */         while (m-- != 0) {
/* 2135 */           sum -= matrix0[p1] * matrix0[p2];
/* 2136 */           p1++;
/* 2137 */           p2 += 4;
/*      */         } 
/* 2139 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 2142 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 2143 */           big = temp;
/* 2144 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 2148 */       if (imax < 0) {
/* 2149 */         throw new RuntimeException(VecMathI18N.getString("Matrix4d11"));
/*      */       }
/*      */ 
/*      */       
/* 2153 */       if (j != imax) {
/*      */         
/* 2155 */         int m = 4;
/* 2156 */         int p1 = mtx + 4 * imax;
/* 2157 */         int p2 = mtx + 4 * j;
/* 2158 */         while (m-- != 0) {
/* 2159 */           double temp = matrix0[p1];
/* 2160 */           matrix0[p1++] = matrix0[p2];
/* 2161 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 2165 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 2169 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 2172 */       if (matrix0[mtx + 4 * j + j] == 0.0D) {
/* 2173 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 2177 */       if (j != 3) {
/* 2178 */         double temp = 1.0D / matrix0[mtx + 4 * j + j];
/* 2179 */         int target = mtx + 4 * (j + 1) + j;
/* 2180 */         k = 3 - j;
/* 2181 */         while (k-- != 0) {
/* 2182 */           matrix0[target] = matrix0[target] * temp;
/* 2183 */           target += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2189 */     return true;
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
/* 2219 */     int rp = 0;
/*      */ 
/*      */     
/* 2222 */     for (int k = 0; k < 4; k++) {
/*      */       
/* 2224 */       int cv = k;
/* 2225 */       int ii = -1;
/*      */ 
/*      */       
/* 2228 */       for (int i = 0; i < 4; i++) {
/*      */ 
/*      */         
/* 2231 */         int ip = row_perm[rp + i];
/* 2232 */         double sum = matrix2[cv + 4 * ip];
/* 2233 */         matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
/* 2234 */         if (ii >= 0) {
/*      */           
/* 2236 */           int m = i << 2;
/* 2237 */           for (int j = ii; j <= i - 1; j++) {
/* 2238 */             sum -= matrix1[m + j] * matrix2[cv + 4 * j];
/*      */           }
/*      */         }
/* 2241 */         else if (sum != 0.0D) {
/* 2242 */           ii = i;
/*      */         } 
/* 2244 */         matrix2[cv + 4 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2249 */       int rv = 12;
/* 2250 */       matrix2[cv + 12] = matrix2[cv + 12] / matrix1[rv + 3];
/*      */       
/* 2252 */       rv -= 4;
/* 2253 */       matrix2[cv + 8] = (matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
/*      */ 
/*      */       
/* 2256 */       rv -= 4;
/* 2257 */       matrix2[cv + 4] = (matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
/*      */ 
/*      */ 
/*      */       
/* 2261 */       rv -= 4;
/* 2262 */       matrix2[cv + 0] = (matrix2[cv + 0] - matrix1[rv + 1] * matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 0];
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
/*      */ 
/*      */   
/*      */   public final double determinant() {
/* 2279 */     double det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/*      */     
/* 2281 */     det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/*      */     
/* 2283 */     det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/*      */     
/* 2285 */     det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */ 
/*      */     
/* 2288 */     return det;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double scale) {
/* 2298 */     this.m00 = scale;
/* 2299 */     this.m01 = 0.0D;
/* 2300 */     this.m02 = 0.0D;
/* 2301 */     this.m03 = 0.0D;
/*      */     
/* 2303 */     this.m10 = 0.0D;
/* 2304 */     this.m11 = scale;
/* 2305 */     this.m12 = 0.0D;
/* 2306 */     this.m13 = 0.0D;
/*      */     
/* 2308 */     this.m20 = 0.0D;
/* 2309 */     this.m21 = 0.0D;
/* 2310 */     this.m22 = scale;
/* 2311 */     this.m23 = 0.0D;
/*      */     
/* 2313 */     this.m30 = 0.0D;
/* 2314 */     this.m31 = 0.0D;
/* 2315 */     this.m32 = 0.0D;
/* 2316 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3d v1) {
/* 2326 */     this.m00 = 1.0D;
/* 2327 */     this.m01 = 0.0D;
/* 2328 */     this.m02 = 0.0D;
/* 2329 */     this.m03 = v1.x;
/*      */     
/* 2331 */     this.m10 = 0.0D;
/* 2332 */     this.m11 = 1.0D;
/* 2333 */     this.m12 = 0.0D;
/* 2334 */     this.m13 = v1.y;
/*      */     
/* 2336 */     this.m20 = 0.0D;
/* 2337 */     this.m21 = 0.0D;
/* 2338 */     this.m22 = 1.0D;
/* 2339 */     this.m23 = v1.z;
/*      */     
/* 2341 */     this.m30 = 0.0D;
/* 2342 */     this.m31 = 0.0D;
/* 2343 */     this.m32 = 0.0D;
/* 2344 */     this.m33 = 1.0D;
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
/*      */   public final void set(double scale, Vector3d v1) {
/* 2356 */     this.m00 = scale;
/* 2357 */     this.m01 = 0.0D;
/* 2358 */     this.m02 = 0.0D;
/* 2359 */     this.m03 = v1.x;
/*      */     
/* 2361 */     this.m10 = 0.0D;
/* 2362 */     this.m11 = scale;
/* 2363 */     this.m12 = 0.0D;
/* 2364 */     this.m13 = v1.y;
/*      */     
/* 2366 */     this.m20 = 0.0D;
/* 2367 */     this.m21 = 0.0D;
/* 2368 */     this.m22 = scale;
/* 2369 */     this.m23 = v1.z;
/*      */     
/* 2371 */     this.m30 = 0.0D;
/* 2372 */     this.m31 = 0.0D;
/* 2373 */     this.m32 = 0.0D;
/* 2374 */     this.m33 = 1.0D;
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
/*      */   public final void set(Vector3d v1, double scale) {
/* 2386 */     this.m00 = scale;
/* 2387 */     this.m01 = 0.0D;
/* 2388 */     this.m02 = 0.0D;
/* 2389 */     this.m03 = scale * v1.x;
/*      */     
/* 2391 */     this.m10 = 0.0D;
/* 2392 */     this.m11 = scale;
/* 2393 */     this.m12 = 0.0D;
/* 2394 */     this.m13 = scale * v1.y;
/*      */     
/* 2396 */     this.m20 = 0.0D;
/* 2397 */     this.m21 = 0.0D;
/* 2398 */     this.m22 = scale;
/* 2399 */     this.m23 = scale * v1.z;
/*      */     
/* 2401 */     this.m30 = 0.0D;
/* 2402 */     this.m31 = 0.0D;
/* 2403 */     this.m32 = 0.0D;
/* 2404 */     this.m33 = 1.0D;
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
/*      */   public final void set(Matrix3f m1, Vector3f t1, float scale) {
/* 2417 */     this.m00 = (m1.m00 * scale);
/* 2418 */     this.m01 = (m1.m01 * scale);
/* 2419 */     this.m02 = (m1.m02 * scale);
/* 2420 */     this.m03 = t1.x;
/*      */     
/* 2422 */     this.m10 = (m1.m10 * scale);
/* 2423 */     this.m11 = (m1.m11 * scale);
/* 2424 */     this.m12 = (m1.m12 * scale);
/* 2425 */     this.m13 = t1.y;
/*      */     
/* 2427 */     this.m20 = (m1.m20 * scale);
/* 2428 */     this.m21 = (m1.m21 * scale);
/* 2429 */     this.m22 = (m1.m22 * scale);
/* 2430 */     this.m23 = t1.z;
/*      */     
/* 2432 */     this.m30 = 0.0D;
/* 2433 */     this.m31 = 0.0D;
/* 2434 */     this.m32 = 0.0D;
/* 2435 */     this.m33 = 1.0D;
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
/*      */   public final void set(Matrix3d m1, Vector3d t1, double scale) {
/* 2449 */     this.m00 = m1.m00 * scale;
/* 2450 */     this.m01 = m1.m01 * scale;
/* 2451 */     this.m02 = m1.m02 * scale;
/* 2452 */     this.m03 = t1.x;
/*      */     
/* 2454 */     this.m10 = m1.m10 * scale;
/* 2455 */     this.m11 = m1.m11 * scale;
/* 2456 */     this.m12 = m1.m12 * scale;
/* 2457 */     this.m13 = t1.y;
/*      */     
/* 2459 */     this.m20 = m1.m20 * scale;
/* 2460 */     this.m21 = m1.m21 * scale;
/* 2461 */     this.m22 = m1.m22 * scale;
/* 2462 */     this.m23 = t1.z;
/*      */     
/* 2464 */     this.m30 = 0.0D;
/* 2465 */     this.m31 = 0.0D;
/* 2466 */     this.m32 = 0.0D;
/* 2467 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3d trans) {
/* 2478 */     this.m03 = trans.x;
/* 2479 */     this.m13 = trans.y;
/* 2480 */     this.m23 = trans.z;
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
/* 2492 */     double sinAngle = Math.sin(angle);
/* 2493 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2495 */     this.m00 = 1.0D;
/* 2496 */     this.m01 = 0.0D;
/* 2497 */     this.m02 = 0.0D;
/* 2498 */     this.m03 = 0.0D;
/*      */     
/* 2500 */     this.m10 = 0.0D;
/* 2501 */     this.m11 = cosAngle;
/* 2502 */     this.m12 = -sinAngle;
/* 2503 */     this.m13 = 0.0D;
/*      */     
/* 2505 */     this.m20 = 0.0D;
/* 2506 */     this.m21 = sinAngle;
/* 2507 */     this.m22 = cosAngle;
/* 2508 */     this.m23 = 0.0D;
/*      */     
/* 2510 */     this.m30 = 0.0D;
/* 2511 */     this.m31 = 0.0D;
/* 2512 */     this.m32 = 0.0D;
/* 2513 */     this.m33 = 1.0D;
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
/* 2525 */     double sinAngle = Math.sin(angle);
/* 2526 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2528 */     this.m00 = cosAngle;
/* 2529 */     this.m01 = 0.0D;
/* 2530 */     this.m02 = sinAngle;
/* 2531 */     this.m03 = 0.0D;
/*      */     
/* 2533 */     this.m10 = 0.0D;
/* 2534 */     this.m11 = 1.0D;
/* 2535 */     this.m12 = 0.0D;
/* 2536 */     this.m13 = 0.0D;
/*      */     
/* 2538 */     this.m20 = -sinAngle;
/* 2539 */     this.m21 = 0.0D;
/* 2540 */     this.m22 = cosAngle;
/* 2541 */     this.m23 = 0.0D;
/*      */     
/* 2543 */     this.m30 = 0.0D;
/* 2544 */     this.m31 = 0.0D;
/* 2545 */     this.m32 = 0.0D;
/* 2546 */     this.m33 = 1.0D;
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
/* 2558 */     double sinAngle = Math.sin(angle);
/* 2559 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2561 */     this.m00 = cosAngle;
/* 2562 */     this.m01 = -sinAngle;
/* 2563 */     this.m02 = 0.0D;
/* 2564 */     this.m03 = 0.0D;
/*      */     
/* 2566 */     this.m10 = sinAngle;
/* 2567 */     this.m11 = cosAngle;
/* 2568 */     this.m12 = 0.0D;
/* 2569 */     this.m13 = 0.0D;
/*      */     
/* 2571 */     this.m20 = 0.0D;
/* 2572 */     this.m21 = 0.0D;
/* 2573 */     this.m22 = 1.0D;
/* 2574 */     this.m23 = 0.0D;
/*      */     
/* 2576 */     this.m30 = 0.0D;
/* 2577 */     this.m31 = 0.0D;
/* 2578 */     this.m32 = 0.0D;
/* 2579 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar) {
/* 2588 */     this.m00 *= scalar;
/* 2589 */     this.m01 *= scalar;
/* 2590 */     this.m02 *= scalar;
/* 2591 */     this.m03 *= scalar;
/* 2592 */     this.m10 *= scalar;
/* 2593 */     this.m11 *= scalar;
/* 2594 */     this.m12 *= scalar;
/* 2595 */     this.m13 *= scalar;
/* 2596 */     this.m20 *= scalar;
/* 2597 */     this.m21 *= scalar;
/* 2598 */     this.m22 *= scalar;
/* 2599 */     this.m23 *= scalar;
/* 2600 */     this.m30 *= scalar;
/* 2601 */     this.m31 *= scalar;
/* 2602 */     this.m32 *= scalar;
/* 2603 */     this.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar, Matrix4d m1) {
/* 2614 */     m1.m00 *= scalar;
/* 2615 */     m1.m01 *= scalar;
/* 2616 */     m1.m02 *= scalar;
/* 2617 */     m1.m03 *= scalar;
/* 2618 */     m1.m10 *= scalar;
/* 2619 */     m1.m11 *= scalar;
/* 2620 */     m1.m12 *= scalar;
/* 2621 */     m1.m13 *= scalar;
/* 2622 */     m1.m20 *= scalar;
/* 2623 */     m1.m21 *= scalar;
/* 2624 */     m1.m22 *= scalar;
/* 2625 */     m1.m23 *= scalar;
/* 2626 */     m1.m30 *= scalar;
/* 2627 */     m1.m31 *= scalar;
/* 2628 */     m1.m32 *= scalar;
/* 2629 */     m1.m33 *= scalar;
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
/*      */   public final void mul(Matrix4d m1) {
/* 2644 */     double m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20 + this.m03 * m1.m30;
/*      */     
/* 2646 */     double m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21 + this.m03 * m1.m31;
/*      */     
/* 2648 */     double m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22 + this.m03 * m1.m32;
/*      */     
/* 2650 */     double m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + this.m02 * m1.m23 + this.m03 * m1.m33;
/*      */ 
/*      */     
/* 2653 */     double m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20 + this.m13 * m1.m30;
/*      */     
/* 2655 */     double m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21 + this.m13 * m1.m31;
/*      */     
/* 2657 */     double m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22 + this.m13 * m1.m32;
/*      */     
/* 2659 */     double m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + this.m12 * m1.m23 + this.m13 * m1.m33;
/*      */ 
/*      */     
/* 2662 */     double m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20 + this.m23 * m1.m30;
/*      */     
/* 2664 */     double m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21 + this.m23 * m1.m31;
/*      */     
/* 2666 */     double m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22 + this.m23 * m1.m32;
/*      */     
/* 2668 */     double m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + this.m22 * m1.m23 + this.m23 * m1.m33;
/*      */ 
/*      */     
/* 2671 */     double m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + this.m32 * m1.m20 + this.m33 * m1.m30;
/*      */     
/* 2673 */     double m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + this.m32 * m1.m21 + this.m33 * m1.m31;
/*      */     
/* 2675 */     double m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + this.m32 * m1.m22 + this.m33 * m1.m32;
/*      */     
/* 2677 */     double m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + this.m32 * m1.m23 + this.m33 * m1.m33;
/*      */ 
/*      */     
/* 2680 */     this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2681 */     this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2682 */     this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2683 */     this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4d m1, Matrix4d m2) {
/* 2694 */     if (this != m1 && this != m2) {
/*      */       
/* 2696 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/*      */       
/* 2698 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/*      */       
/* 2700 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/*      */       
/* 2702 */       this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */ 
/*      */       
/* 2705 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/*      */       
/* 2707 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/*      */       
/* 2709 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/*      */       
/* 2711 */       this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */ 
/*      */       
/* 2714 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/*      */       
/* 2716 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/*      */       
/* 2718 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/*      */       
/* 2720 */       this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */ 
/*      */       
/* 2723 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/*      */       
/* 2725 */       this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/*      */       
/* 2727 */       this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/*      */       
/* 2729 */       this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2738 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2739 */       double m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2740 */       double m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2741 */       double m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2743 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2744 */       double m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2745 */       double m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2746 */       double m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2748 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2749 */       double m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2750 */       double m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2751 */       double m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2753 */       double m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2754 */       double m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2755 */       double m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2756 */       double m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2758 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2759 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2760 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2761 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeBoth(Matrix4d m1, Matrix4d m2) {
/* 2774 */     if (this != m1 && this != m2) {
/* 2775 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2776 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2777 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2778 */       this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2780 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2781 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2782 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2783 */       this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2785 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2786 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2787 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2788 */       this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2790 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2791 */       this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2792 */       this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2793 */       this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2800 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2801 */       double m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2802 */       double m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2803 */       double m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2805 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2806 */       double m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2807 */       double m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2808 */       double m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2810 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2811 */       double m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2812 */       double m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2813 */       double m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2815 */       double m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2816 */       double m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2817 */       double m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2818 */       double m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2820 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2821 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2822 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2823 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeRight(Matrix4d m1, Matrix4d m2) {
/* 2838 */     if (this != m1 && this != m2) {
/* 2839 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2840 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2841 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2842 */       this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2844 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2845 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2846 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2847 */       this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2849 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2850 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2851 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2852 */       this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2854 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2855 */       this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2856 */       this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2857 */       this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2864 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2865 */       double m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2866 */       double m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2867 */       double m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2869 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2870 */       double m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2871 */       double m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2872 */       double m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2874 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2875 */       double m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2876 */       double m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2877 */       double m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2879 */       double m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2880 */       double m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2881 */       double m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2882 */       double m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2884 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2885 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2886 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2887 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeLeft(Matrix4d m1, Matrix4d m2) {
/* 2900 */     if (this != m1 && this != m2) {
/* 2901 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2902 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2903 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2904 */       this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2906 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2907 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2908 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2909 */       this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2911 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2912 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2913 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2914 */       this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2916 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2917 */       this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2918 */       this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2919 */       this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2928 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2929 */       double m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2930 */       double m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2931 */       double m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2933 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2934 */       double m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2935 */       double m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2936 */       double m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2938 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2939 */       double m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2940 */       double m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2941 */       double m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2943 */       double m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2944 */       double m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2945 */       double m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2946 */       double m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2948 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2949 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2950 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2951 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public boolean equals(Matrix4d m1) {
/*      */     try {
/* 2966 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && this.m33 == m1.m33);
/*      */ 
/*      */     
/*      */     }
/*      */     catch (NullPointerException e2) {
/*      */ 
/*      */       
/* 2973 */       return false;
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
/* 2988 */     try { Matrix4d m2 = (Matrix4d)t1;
/* 2989 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && this.m33 == m2.m33);
/*      */       
/*      */        }
/*      */     
/*      */     catch (ClassCastException e1)
/*      */     
/*      */     { 
/* 2996 */       return false; }
/* 2997 */     catch (NullPointerException e2) { return false; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix4d m1, float epsilon) {
/* 3004 */     return epsilonEquals(m1, epsilon);
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
/*      */   public boolean epsilonEquals(Matrix4d m1, double epsilon) {
/* 3019 */     double diff = this.m00 - m1.m00;
/* 3020 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3022 */     diff = this.m01 - m1.m01;
/* 3023 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3025 */     diff = this.m02 - m1.m02;
/* 3026 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3028 */     diff = this.m03 - m1.m03;
/* 3029 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3031 */     diff = this.m10 - m1.m10;
/* 3032 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3034 */     diff = this.m11 - m1.m11;
/* 3035 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3037 */     diff = this.m12 - m1.m12;
/* 3038 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3040 */     diff = this.m13 - m1.m13;
/* 3041 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3043 */     diff = this.m20 - m1.m20;
/* 3044 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3046 */     diff = this.m21 - m1.m21;
/* 3047 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3049 */     diff = this.m22 - m1.m22;
/* 3050 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3052 */     diff = this.m23 - m1.m23;
/* 3053 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3055 */     diff = this.m30 - m1.m30;
/* 3056 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3058 */     diff = this.m31 - m1.m31;
/* 3059 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3061 */     diff = this.m32 - m1.m32;
/* 3062 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3064 */     diff = this.m33 - m1.m33;
/* 3065 */     return (((diff < 0.0D) ? -diff : diff) <= epsilon);
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
/*      */   public int hashCode() {
/* 3078 */     long bits = 1L;
/* 3079 */     bits = VecMathUtil.hashDoubleBits(bits, this.m00);
/* 3080 */     bits = VecMathUtil.hashDoubleBits(bits, this.m01);
/* 3081 */     bits = VecMathUtil.hashDoubleBits(bits, this.m02);
/* 3082 */     bits = VecMathUtil.hashDoubleBits(bits, this.m03);
/* 3083 */     bits = VecMathUtil.hashDoubleBits(bits, this.m10);
/* 3084 */     bits = VecMathUtil.hashDoubleBits(bits, this.m11);
/* 3085 */     bits = VecMathUtil.hashDoubleBits(bits, this.m12);
/* 3086 */     bits = VecMathUtil.hashDoubleBits(bits, this.m13);
/* 3087 */     bits = VecMathUtil.hashDoubleBits(bits, this.m20);
/* 3088 */     bits = VecMathUtil.hashDoubleBits(bits, this.m21);
/* 3089 */     bits = VecMathUtil.hashDoubleBits(bits, this.m22);
/* 3090 */     bits = VecMathUtil.hashDoubleBits(bits, this.m23);
/* 3091 */     bits = VecMathUtil.hashDoubleBits(bits, this.m30);
/* 3092 */     bits = VecMathUtil.hashDoubleBits(bits, this.m31);
/* 3093 */     bits = VecMathUtil.hashDoubleBits(bits, this.m32);
/* 3094 */     bits = VecMathUtil.hashDoubleBits(bits, this.m33);
/* 3095 */     return VecMathUtil.hashFinish(bits);
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
/*      */   public final void transform(Tuple4d vec, Tuple4d vecOut) {
/* 3108 */     double x = this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w;
/*      */     
/* 3110 */     double y = this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w;
/*      */     
/* 3112 */     double z = this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w;
/*      */     
/* 3114 */     vecOut.w = this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w;
/*      */     
/* 3116 */     vecOut.x = x;
/* 3117 */     vecOut.y = y;
/* 3118 */     vecOut.z = z;
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
/*      */   public final void transform(Tuple4d vec) {
/* 3130 */     double x = this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w;
/*      */     
/* 3132 */     double y = this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w;
/*      */     
/* 3134 */     double z = this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w;
/*      */     
/* 3136 */     vec.w = this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w;
/*      */     
/* 3138 */     vec.x = x;
/* 3139 */     vec.y = y;
/* 3140 */     vec.z = z;
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
/*      */   public final void transform(Tuple4f vec, Tuple4f vecOut) {
/* 3152 */     float x = (float)(this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w);
/*      */     
/* 3154 */     float y = (float)(this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w);
/*      */     
/* 3156 */     float z = (float)(this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w);
/*      */     
/* 3158 */     vecOut.w = (float)(this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w);
/*      */     
/* 3160 */     vecOut.x = x;
/* 3161 */     vecOut.y = y;
/* 3162 */     vecOut.z = z;
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
/*      */   public final void transform(Tuple4f vec) {
/* 3174 */     float x = (float)(this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w);
/*      */     
/* 3176 */     float y = (float)(this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w);
/*      */     
/* 3178 */     float z = (float)(this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w);
/*      */     
/* 3180 */     vec.w = (float)(this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w);
/*      */     
/* 3182 */     vec.x = x;
/* 3183 */     vec.y = y;
/* 3184 */     vec.z = z;
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
/*      */   public final void transform(Point3d point, Point3d pointOut) {
/* 3198 */     double x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3199 */     double y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3200 */     pointOut.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3201 */     pointOut.x = x;
/* 3202 */     pointOut.y = y;
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
/*      */   public final void transform(Point3d point) {
/* 3216 */     double x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3217 */     double y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3218 */     point.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3219 */     point.x = x;
/* 3220 */     point.y = y;
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
/*      */   public final void transform(Point3f point, Point3f pointOut) {
/* 3235 */     float x = (float)(this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03);
/* 3236 */     float y = (float)(this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13);
/* 3237 */     pointOut.z = (float)(this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23);
/* 3238 */     pointOut.x = x;
/* 3239 */     pointOut.y = y;
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
/*      */   public final void transform(Point3f point) {
/* 3252 */     float x = (float)(this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03);
/* 3253 */     float y = (float)(this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13);
/* 3254 */     point.z = (float)(this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23);
/* 3255 */     point.x = x;
/* 3256 */     point.y = y;
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
/*      */   public final void transform(Vector3d normal, Vector3d normalOut) {
/* 3269 */     double x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3270 */     double y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3271 */     normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3272 */     normalOut.x = x;
/* 3273 */     normalOut.y = y;
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
/*      */   public final void transform(Vector3d normal) {
/* 3286 */     double x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3287 */     double y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3288 */     normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3289 */     normal.x = x;
/* 3290 */     normal.y = y;
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
/*      */   public final void transform(Vector3f normal, Vector3f normalOut) {
/* 3303 */     float x = (float)(this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z);
/* 3304 */     float y = (float)(this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z);
/* 3305 */     normalOut.z = (float)(this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z);
/* 3306 */     normalOut.x = x;
/* 3307 */     normalOut.y = y;
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
/*      */   public final void transform(Vector3f normal) {
/* 3320 */     float x = (float)(this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z);
/* 3321 */     float y = (float)(this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z);
/* 3322 */     normal.z = (float)(this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z);
/* 3323 */     normal.x = x;
/* 3324 */     normal.y = y;
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
/*      */   public final void setRotation(Matrix3d m1) {
/* 3338 */     double[] tmp_rot = new double[9];
/* 3339 */     double[] tmp_scale = new double[3];
/*      */     
/* 3341 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3343 */     this.m00 = m1.m00 * tmp_scale[0];
/* 3344 */     this.m01 = m1.m01 * tmp_scale[1];
/* 3345 */     this.m02 = m1.m02 * tmp_scale[2];
/*      */     
/* 3347 */     this.m10 = m1.m10 * tmp_scale[0];
/* 3348 */     this.m11 = m1.m11 * tmp_scale[1];
/* 3349 */     this.m12 = m1.m12 * tmp_scale[2];
/*      */     
/* 3351 */     this.m20 = m1.m20 * tmp_scale[0];
/* 3352 */     this.m21 = m1.m21 * tmp_scale[1];
/* 3353 */     this.m22 = m1.m22 * tmp_scale[2];
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
/*      */   public final void setRotation(Matrix3f m1) {
/* 3371 */     double[] tmp_rot = new double[9];
/* 3372 */     double[] tmp_scale = new double[3];
/* 3373 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3375 */     this.m00 = m1.m00 * tmp_scale[0];
/* 3376 */     this.m01 = m1.m01 * tmp_scale[1];
/* 3377 */     this.m02 = m1.m02 * tmp_scale[2];
/*      */     
/* 3379 */     this.m10 = m1.m10 * tmp_scale[0];
/* 3380 */     this.m11 = m1.m11 * tmp_scale[1];
/* 3381 */     this.m12 = m1.m12 * tmp_scale[2];
/*      */     
/* 3383 */     this.m20 = m1.m20 * tmp_scale[0];
/* 3384 */     this.m21 = m1.m21 * tmp_scale[1];
/* 3385 */     this.m22 = m1.m22 * tmp_scale[2];
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
/*      */   public final void setRotation(Quat4f q1) {
/* 3399 */     double[] tmp_rot = new double[9];
/* 3400 */     double[] tmp_scale = new double[3];
/* 3401 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3403 */     this.m00 = (1.0D - (2.0F * q1.y * q1.y) - (2.0F * q1.z * q1.z)) * tmp_scale[0];
/* 3404 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0];
/* 3405 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0];
/*      */     
/* 3407 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1];
/* 3408 */     this.m11 = (1.0D - (2.0F * q1.x * q1.x) - (2.0F * q1.z * q1.z)) * tmp_scale[1];
/* 3409 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1];
/*      */     
/* 3411 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2];
/* 3412 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2];
/* 3413 */     this.m22 = (1.0D - (2.0F * q1.x * q1.x) - (2.0F * q1.y * q1.y)) * tmp_scale[2];
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
/*      */   public final void setRotation(Quat4d q1) {
/* 3430 */     double[] tmp_rot = new double[9];
/* 3431 */     double[] tmp_scale = new double[3];
/* 3432 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3434 */     this.m00 = (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z) * tmp_scale[0];
/* 3435 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0];
/* 3436 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0];
/*      */     
/* 3438 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1];
/* 3439 */     this.m11 = (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z) * tmp_scale[1];
/* 3440 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1];
/*      */     
/* 3442 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2];
/* 3443 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2];
/* 3444 */     this.m22 = (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y) * tmp_scale[2];
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
/*      */   public final void setRotation(AxisAngle4d a1) {
/* 3460 */     double[] tmp_rot = new double[9];
/* 3461 */     double[] tmp_scale = new double[3];
/*      */     
/* 3463 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3465 */     double mag = 1.0D / Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/* 3466 */     double ax = a1.x * mag;
/* 3467 */     double ay = a1.y * mag;
/* 3468 */     double az = a1.z * mag;
/*      */     
/* 3470 */     double sinTheta = Math.sin(a1.angle);
/* 3471 */     double cosTheta = Math.cos(a1.angle);
/* 3472 */     double t = 1.0D - cosTheta;
/*      */     
/* 3474 */     double xz = a1.x * a1.z;
/* 3475 */     double xy = a1.x * a1.y;
/* 3476 */     double yz = a1.y * a1.z;
/*      */     
/* 3478 */     this.m00 = (t * ax * ax + cosTheta) * tmp_scale[0];
/* 3479 */     this.m01 = (t * xy - sinTheta * az) * tmp_scale[1];
/* 3480 */     this.m02 = (t * xz + sinTheta * ay) * tmp_scale[2];
/*      */     
/* 3482 */     this.m10 = (t * xy + sinTheta * az) * tmp_scale[0];
/* 3483 */     this.m11 = (t * ay * ay + cosTheta) * tmp_scale[1];
/* 3484 */     this.m12 = (t * yz - sinTheta * ax) * tmp_scale[2];
/*      */     
/* 3486 */     this.m20 = (t * xz - sinTheta * ay) * tmp_scale[0];
/* 3487 */     this.m21 = (t * yz + sinTheta * ax) * tmp_scale[1];
/* 3488 */     this.m22 = (t * az * az + cosTheta) * tmp_scale[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3497 */     this.m00 = 0.0D;
/* 3498 */     this.m01 = 0.0D;
/* 3499 */     this.m02 = 0.0D;
/* 3500 */     this.m03 = 0.0D;
/* 3501 */     this.m10 = 0.0D;
/* 3502 */     this.m11 = 0.0D;
/* 3503 */     this.m12 = 0.0D;
/* 3504 */     this.m13 = 0.0D;
/* 3505 */     this.m20 = 0.0D;
/* 3506 */     this.m21 = 0.0D;
/* 3507 */     this.m22 = 0.0D;
/* 3508 */     this.m23 = 0.0D;
/* 3509 */     this.m30 = 0.0D;
/* 3510 */     this.m31 = 0.0D;
/* 3511 */     this.m32 = 0.0D;
/* 3512 */     this.m33 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3520 */     this.m00 = -this.m00;
/* 3521 */     this.m01 = -this.m01;
/* 3522 */     this.m02 = -this.m02;
/* 3523 */     this.m03 = -this.m03;
/* 3524 */     this.m10 = -this.m10;
/* 3525 */     this.m11 = -this.m11;
/* 3526 */     this.m12 = -this.m12;
/* 3527 */     this.m13 = -this.m13;
/* 3528 */     this.m20 = -this.m20;
/* 3529 */     this.m21 = -this.m21;
/* 3530 */     this.m22 = -this.m22;
/* 3531 */     this.m23 = -this.m23;
/* 3532 */     this.m30 = -this.m30;
/* 3533 */     this.m31 = -this.m31;
/* 3534 */     this.m32 = -this.m32;
/* 3535 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4d m1) {
/* 3545 */     this.m00 = -m1.m00;
/* 3546 */     this.m01 = -m1.m01;
/* 3547 */     this.m02 = -m1.m02;
/* 3548 */     this.m03 = -m1.m03;
/* 3549 */     this.m10 = -m1.m10;
/* 3550 */     this.m11 = -m1.m11;
/* 3551 */     this.m12 = -m1.m12;
/* 3552 */     this.m13 = -m1.m13;
/* 3553 */     this.m20 = -m1.m20;
/* 3554 */     this.m21 = -m1.m21;
/* 3555 */     this.m22 = -m1.m22;
/* 3556 */     this.m23 = -m1.m23;
/* 3557 */     this.m30 = -m1.m30;
/* 3558 */     this.m31 = -m1.m31;
/* 3559 */     this.m32 = -m1.m32;
/* 3560 */     this.m33 = -m1.m33;
/*      */   }
/*      */   private final void getScaleRotate(double[] scales, double[] rots) {
/* 3563 */     double[] tmp = new double[9];
/* 3564 */     tmp[0] = this.m00;
/* 3565 */     tmp[1] = this.m01;
/* 3566 */     tmp[2] = this.m02;
/*      */     
/* 3568 */     tmp[3] = this.m10;
/* 3569 */     tmp[4] = this.m11;
/* 3570 */     tmp[5] = this.m12;
/*      */     
/* 3572 */     tmp[6] = this.m20;
/* 3573 */     tmp[7] = this.m21;
/* 3574 */     tmp[8] = this.m22;
/*      */     
/* 3576 */     Matrix3d.compute_svd(tmp, scales, rots);
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
/*      */   public Object clone() {
/* 3591 */     Matrix4d m1 = null;
/*      */     try {
/* 3593 */       m1 = (Matrix4d)super.clone();
/* 3594 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3596 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3599 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM00() {
/* 3610 */     return this.m00;
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
/*      */   public final void setM00(double m00) {
/* 3622 */     this.m00 = m00;
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
/* 3633 */     return this.m01;
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
/* 3644 */     this.m01 = m01;
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
/* 3655 */     return this.m02;
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
/* 3666 */     this.m02 = m02;
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
/* 3677 */     return this.m10;
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
/* 3688 */     this.m10 = m10;
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
/* 3699 */     return this.m11;
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
/* 3710 */     this.m11 = m11;
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
/* 3721 */     return this.m12;
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
/*      */   public final void setM12(double m12) {
/* 3733 */     this.m12 = m12;
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
/* 3744 */     return this.m20;
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
/* 3755 */     this.m20 = m20;
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
/* 3766 */     return this.m21;
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
/* 3777 */     this.m21 = m21;
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
/* 3788 */     return this.m22;
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
/* 3799 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM03() {
/* 3810 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(double m03) {
/* 3821 */     this.m03 = m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM13() {
/* 3832 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(double m13) {
/* 3843 */     this.m13 = m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM23() {
/* 3854 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(double m23) {
/* 3865 */     this.m23 = m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM30() {
/* 3876 */     return this.m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM30(double m30) {
/* 3887 */     this.m30 = m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM31() {
/* 3898 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(double m31) {
/* 3909 */     this.m31 = m31;
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
/*      */   public final double getM32() {
/* 3921 */     return this.m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM32(double m32) {
/* 3932 */     this.m32 = m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM33() {
/* 3943 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(double m33) {
/* 3954 */     this.m33 = m33;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Matrix4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */