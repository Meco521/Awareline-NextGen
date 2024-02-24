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
/*      */ public class Matrix4f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = -8405036035410109353L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m03;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m13;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   public float m23;
/*      */   public float m30;
/*      */   public float m31;
/*      */   public float m32;
/*      */   public float m33;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
/*  146 */     this.m00 = m00;
/*  147 */     this.m01 = m01;
/*  148 */     this.m02 = m02;
/*  149 */     this.m03 = m03;
/*      */     
/*  151 */     this.m10 = m10;
/*  152 */     this.m11 = m11;
/*  153 */     this.m12 = m12;
/*  154 */     this.m13 = m13;
/*      */     
/*  156 */     this.m20 = m20;
/*  157 */     this.m21 = m21;
/*  158 */     this.m22 = m22;
/*  159 */     this.m23 = m23;
/*      */     
/*  161 */     this.m30 = m30;
/*  162 */     this.m31 = m31;
/*  163 */     this.m32 = m32;
/*  164 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(float[] v) {
/*  175 */     this.m00 = v[0];
/*  176 */     this.m01 = v[1];
/*  177 */     this.m02 = v[2];
/*  178 */     this.m03 = v[3];
/*      */     
/*  180 */     this.m10 = v[4];
/*  181 */     this.m11 = v[5];
/*  182 */     this.m12 = v[6];
/*  183 */     this.m13 = v[7];
/*      */     
/*  185 */     this.m20 = v[8];
/*  186 */     this.m21 = v[9];
/*  187 */     this.m22 = v[10];
/*  188 */     this.m23 = v[11];
/*      */     
/*  190 */     this.m30 = v[12];
/*  191 */     this.m31 = v[13];
/*  192 */     this.m32 = v[14];
/*  193 */     this.m33 = v[15];
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
/*      */   public Matrix4f(Quat4f q1, Vector3f t1, float s) {
/*  208 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/*  209 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  210 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  212 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  213 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/*  214 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  216 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  217 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  218 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/*  220 */     this.m03 = t1.x;
/*  221 */     this.m13 = t1.y;
/*  222 */     this.m23 = t1.z;
/*      */     
/*  224 */     this.m30 = 0.0F;
/*  225 */     this.m31 = 0.0F;
/*  226 */     this.m32 = 0.0F;
/*  227 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(Matrix4d m1) {
/*  238 */     this.m00 = (float)m1.m00;
/*  239 */     this.m01 = (float)m1.m01;
/*  240 */     this.m02 = (float)m1.m02;
/*  241 */     this.m03 = (float)m1.m03;
/*      */     
/*  243 */     this.m10 = (float)m1.m10;
/*  244 */     this.m11 = (float)m1.m11;
/*  245 */     this.m12 = (float)m1.m12;
/*  246 */     this.m13 = (float)m1.m13;
/*      */     
/*  248 */     this.m20 = (float)m1.m20;
/*  249 */     this.m21 = (float)m1.m21;
/*  250 */     this.m22 = (float)m1.m22;
/*  251 */     this.m23 = (float)m1.m23;
/*      */     
/*  253 */     this.m30 = (float)m1.m30;
/*  254 */     this.m31 = (float)m1.m31;
/*  255 */     this.m32 = (float)m1.m32;
/*  256 */     this.m33 = (float)m1.m33;
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
/*      */   public Matrix4f(Matrix4f m1) {
/*  268 */     this.m00 = m1.m00;
/*  269 */     this.m01 = m1.m01;
/*  270 */     this.m02 = m1.m02;
/*  271 */     this.m03 = m1.m03;
/*      */     
/*  273 */     this.m10 = m1.m10;
/*  274 */     this.m11 = m1.m11;
/*  275 */     this.m12 = m1.m12;
/*  276 */     this.m13 = m1.m13;
/*      */     
/*  278 */     this.m20 = m1.m20;
/*  279 */     this.m21 = m1.m21;
/*  280 */     this.m22 = m1.m22;
/*  281 */     this.m23 = m1.m23;
/*      */     
/*  283 */     this.m30 = m1.m30;
/*  284 */     this.m31 = m1.m31;
/*  285 */     this.m32 = m1.m32;
/*  286 */     this.m33 = m1.m33;
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
/*      */   public Matrix4f(Matrix3f m1, Vector3f t1, float s) {
/*  302 */     this.m00 = m1.m00 * s;
/*  303 */     this.m01 = m1.m01 * s;
/*  304 */     this.m02 = m1.m02 * s;
/*  305 */     this.m03 = t1.x;
/*      */     
/*  307 */     this.m10 = m1.m10 * s;
/*  308 */     this.m11 = m1.m11 * s;
/*  309 */     this.m12 = m1.m12 * s;
/*  310 */     this.m13 = t1.y;
/*      */     
/*  312 */     this.m20 = m1.m20 * s;
/*  313 */     this.m21 = m1.m21 * s;
/*  314 */     this.m22 = m1.m22 * s;
/*  315 */     this.m23 = t1.z;
/*      */     
/*  317 */     this.m30 = 0.0F;
/*  318 */     this.m31 = 0.0F;
/*  319 */     this.m32 = 0.0F;
/*  320 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f() {
/*  330 */     this.m00 = 0.0F;
/*  331 */     this.m01 = 0.0F;
/*  332 */     this.m02 = 0.0F;
/*  333 */     this.m03 = 0.0F;
/*      */     
/*  335 */     this.m10 = 0.0F;
/*  336 */     this.m11 = 0.0F;
/*  337 */     this.m12 = 0.0F;
/*  338 */     this.m13 = 0.0F;
/*      */     
/*  340 */     this.m20 = 0.0F;
/*  341 */     this.m21 = 0.0F;
/*  342 */     this.m22 = 0.0F;
/*  343 */     this.m23 = 0.0F;
/*      */     
/*  345 */     this.m30 = 0.0F;
/*  346 */     this.m31 = 0.0F;
/*  347 */     this.m32 = 0.0F;
/*  348 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(org.lwjgl.util.vector.Matrix4f matrix4f) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  360 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
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
/*  372 */     this.m00 = 1.0F;
/*  373 */     this.m01 = 0.0F;
/*  374 */     this.m02 = 0.0F;
/*  375 */     this.m03 = 0.0F;
/*      */     
/*  377 */     this.m10 = 0.0F;
/*  378 */     this.m11 = 1.0F;
/*  379 */     this.m12 = 0.0F;
/*  380 */     this.m13 = 0.0F;
/*      */     
/*  382 */     this.m20 = 0.0F;
/*  383 */     this.m21 = 0.0F;
/*  384 */     this.m22 = 1.0F;
/*  385 */     this.m23 = 0.0F;
/*      */     
/*  387 */     this.m30 = 0.0F;
/*  388 */     this.m31 = 0.0F;
/*  389 */     this.m32 = 0.0F;
/*  390 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int row, int column, float value) {
/*  401 */     switch (row) {
/*      */       
/*      */       case 0:
/*  404 */         switch (column) {
/*      */           
/*      */           case 0:
/*  407 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  410 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  413 */             this.m02 = value;
/*      */             return;
/*      */           case 3:
/*  416 */             this.m03 = value;
/*      */             return;
/*      */         } 
/*  419 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  424 */         switch (column) {
/*      */           
/*      */           case 0:
/*  427 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  430 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  433 */             this.m12 = value;
/*      */             return;
/*      */           case 3:
/*  436 */             this.m13 = value;
/*      */             return;
/*      */         } 
/*  439 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  444 */         switch (column) {
/*      */           
/*      */           case 0:
/*  447 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  450 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  453 */             this.m22 = value;
/*      */             return;
/*      */           case 3:
/*  456 */             this.m23 = value;
/*      */             return;
/*      */         } 
/*  459 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  464 */         switch (column) {
/*      */           
/*      */           case 0:
/*  467 */             this.m30 = value;
/*      */             return;
/*      */           case 1:
/*  470 */             this.m31 = value;
/*      */             return;
/*      */           case 2:
/*  473 */             this.m32 = value;
/*      */             return;
/*      */           case 3:
/*  476 */             this.m33 = value;
/*      */             return;
/*      */         } 
/*  479 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  484 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
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
/*  496 */     switch (row) {
/*      */       
/*      */       case 0:
/*  499 */         switch (column) {
/*      */           
/*      */           case 0:
/*  502 */             return this.m00;
/*      */           case 1:
/*  504 */             return this.m01;
/*      */           case 2:
/*  506 */             return this.m02;
/*      */           case 3:
/*  508 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  514 */         switch (column) {
/*      */           
/*      */           case 0:
/*  517 */             return this.m10;
/*      */           case 1:
/*  519 */             return this.m11;
/*      */           case 2:
/*  521 */             return this.m12;
/*      */           case 3:
/*  523 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  530 */         switch (column) {
/*      */           
/*      */           case 0:
/*  533 */             return this.m20;
/*      */           case 1:
/*  535 */             return this.m21;
/*      */           case 2:
/*  537 */             return this.m22;
/*      */           case 3:
/*  539 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  546 */         switch (column) {
/*      */           
/*      */           case 0:
/*  549 */             return this.m30;
/*      */           case 1:
/*  551 */             return this.m31;
/*      */           case 2:
/*  553 */             return this.m32;
/*      */           case 3:
/*  555 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  564 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector4f v) {
/*  573 */     if (row == 0) {
/*  574 */       v.x = this.m00;
/*  575 */       v.y = this.m01;
/*  576 */       v.z = this.m02;
/*  577 */       v.w = this.m03;
/*  578 */     } else if (row == 1) {
/*  579 */       v.x = this.m10;
/*  580 */       v.y = this.m11;
/*  581 */       v.z = this.m12;
/*  582 */       v.w = this.m13;
/*  583 */     } else if (row == 2) {
/*  584 */       v.x = this.m20;
/*  585 */       v.y = this.m21;
/*  586 */       v.z = this.m22;
/*  587 */       v.w = this.m23;
/*  588 */     } else if (row == 3) {
/*  589 */       v.x = this.m30;
/*  590 */       v.y = this.m31;
/*  591 */       v.z = this.m32;
/*  592 */       v.w = this.m33;
/*      */     } else {
/*  594 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*  605 */     if (row == 0) {
/*  606 */       v[0] = this.m00;
/*  607 */       v[1] = this.m01;
/*  608 */       v[2] = this.m02;
/*  609 */       v[3] = this.m03;
/*  610 */     } else if (row == 1) {
/*  611 */       v[0] = this.m10;
/*  612 */       v[1] = this.m11;
/*  613 */       v[2] = this.m12;
/*  614 */       v[3] = this.m13;
/*  615 */     } else if (row == 2) {
/*  616 */       v[0] = this.m20;
/*  617 */       v[1] = this.m21;
/*  618 */       v[2] = this.m22;
/*  619 */       v[3] = this.m23;
/*  620 */     } else if (row == 3) {
/*  621 */       v[0] = this.m30;
/*  622 */       v[1] = this.m31;
/*  623 */       v[2] = this.m32;
/*  624 */       v[3] = this.m33;
/*      */     } else {
/*  626 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*      */   public final void getColumn(int column, Vector4f v) {
/*  638 */     if (column == 0) {
/*  639 */       v.x = this.m00;
/*  640 */       v.y = this.m10;
/*  641 */       v.z = this.m20;
/*  642 */       v.w = this.m30;
/*  643 */     } else if (column == 1) {
/*  644 */       v.x = this.m01;
/*  645 */       v.y = this.m11;
/*  646 */       v.z = this.m21;
/*  647 */       v.w = this.m31;
/*  648 */     } else if (column == 2) {
/*  649 */       v.x = this.m02;
/*  650 */       v.y = this.m12;
/*  651 */       v.z = this.m22;
/*  652 */       v.w = this.m32;
/*  653 */     } else if (column == 3) {
/*  654 */       v.x = this.m03;
/*  655 */       v.y = this.m13;
/*  656 */       v.z = this.m23;
/*  657 */       v.w = this.m33;
/*      */     } else {
/*  659 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*  671 */     if (column == 0) {
/*  672 */       v[0] = this.m00;
/*  673 */       v[1] = this.m10;
/*  674 */       v[2] = this.m20;
/*  675 */       v[3] = this.m30;
/*  676 */     } else if (column == 1) {
/*  677 */       v[0] = this.m01;
/*  678 */       v[1] = this.m11;
/*  679 */       v[2] = this.m21;
/*  680 */       v[3] = this.m31;
/*  681 */     } else if (column == 2) {
/*  682 */       v[0] = this.m02;
/*  683 */       v[1] = this.m12;
/*  684 */       v[2] = this.m22;
/*  685 */       v[3] = this.m32;
/*  686 */     } else if (column == 3) {
/*  687 */       v[0] = this.m03;
/*  688 */       v[1] = this.m13;
/*  689 */       v[2] = this.m23;
/*  690 */       v[3] = this.m33;
/*      */     } else {
/*  692 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*      */   public final void setScale(float scale) {
/*  706 */     double[] tmp_rot = new double[9];
/*  707 */     double[] tmp_scale = new double[3];
/*  708 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  710 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  711 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  712 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  714 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  715 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  716 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  718 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  719 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  720 */     this.m22 = (float)(tmp_rot[8] * scale);
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
/*      */   public final void get(Matrix3d m1) {
/*  732 */     double[] tmp_rot = new double[9];
/*  733 */     double[] tmp_scale = new double[3];
/*      */     
/*  735 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  737 */     m1.m00 = tmp_rot[0];
/*  738 */     m1.m01 = tmp_rot[1];
/*  739 */     m1.m02 = tmp_rot[2];
/*      */     
/*  741 */     m1.m10 = tmp_rot[3];
/*  742 */     m1.m11 = tmp_rot[4];
/*  743 */     m1.m12 = tmp_rot[5];
/*      */     
/*  745 */     m1.m20 = tmp_rot[6];
/*  746 */     m1.m21 = tmp_rot[7];
/*  747 */     m1.m22 = tmp_rot[8];
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
/*      */   public final void get(Matrix3f m1) {
/*  759 */     double[] tmp_rot = new double[9];
/*  760 */     double[] tmp_scale = new double[3];
/*      */     
/*  762 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  764 */     m1.m00 = (float)tmp_rot[0];
/*  765 */     m1.m01 = (float)tmp_rot[1];
/*  766 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  768 */     m1.m10 = (float)tmp_rot[3];
/*  769 */     m1.m11 = (float)tmp_rot[4];
/*  770 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  772 */     m1.m20 = (float)tmp_rot[6];
/*  773 */     m1.m21 = (float)tmp_rot[7];
/*  774 */     m1.m22 = (float)tmp_rot[8];
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
/*      */   public final float get(Matrix3f m1, Vector3f t1) {
/*  789 */     double[] tmp_rot = new double[9];
/*  790 */     double[] tmp_scale = new double[3];
/*      */     
/*  792 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  794 */     m1.m00 = (float)tmp_rot[0];
/*  795 */     m1.m01 = (float)tmp_rot[1];
/*  796 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  798 */     m1.m10 = (float)tmp_rot[3];
/*  799 */     m1.m11 = (float)tmp_rot[4];
/*  800 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  802 */     m1.m20 = (float)tmp_rot[6];
/*  803 */     m1.m21 = (float)tmp_rot[7];
/*  804 */     m1.m22 = (float)tmp_rot[8];
/*      */     
/*  806 */     t1.x = this.m03;
/*  807 */     t1.y = this.m13;
/*  808 */     t1.z = this.m23;
/*      */     
/*  810 */     return (float)Matrix3d.max3(tmp_scale);
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
/*      */   public final void get(Quat4f q1) {
/*  822 */     double[] tmp_rot = new double[9];
/*  823 */     double[] tmp_scale = new double[3];
/*  824 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  828 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  829 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  830 */       q1.w = (float)Math.sqrt(ww);
/*  831 */       ww = 0.25D / q1.w;
/*  832 */       q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
/*  833 */       q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
/*  834 */       q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  838 */     q1.w = 0.0F;
/*  839 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  840 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  841 */       q1.x = (float)Math.sqrt(ww);
/*  842 */       ww = 0.5D / q1.x;
/*  843 */       q1.y = (float)(tmp_rot[3] * ww);
/*  844 */       q1.z = (float)(tmp_rot[6] * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  848 */     q1.x = 0.0F;
/*  849 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  850 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  851 */       q1.y = (float)Math.sqrt(ww);
/*  852 */       q1.z = (float)(tmp_rot[7] / 2.0D * q1.y);
/*      */       
/*      */       return;
/*      */     } 
/*  856 */     q1.y = 0.0F;
/*  857 */     q1.z = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3f trans) {
/*  868 */     trans.x = this.m03;
/*  869 */     trans.y = this.m13;
/*  870 */     trans.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f m1) {
/*  880 */     m1.m00 = this.m00; m1.m01 = this.m01; m1.m02 = this.m02;
/*  881 */     m1.m10 = this.m10; m1.m11 = this.m11; m1.m12 = this.m12;
/*  882 */     m1.m20 = this.m20; m1.m21 = this.m21; m1.m22 = this.m22;
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
/*      */   public final float getScale() {
/*  894 */     double[] tmp_rot = new double[9];
/*  895 */     double[] tmp_scale = new double[3];
/*      */     
/*  897 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  899 */     return (float)Matrix3d.max3(tmp_scale);
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
/*      */   public final void setRotationScale(Matrix3f m1) {
/*  911 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02;
/*  912 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12;
/*  913 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22;
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
/*      */   public final void setRow(int row, float x, float y, float z, float w) {
/*  927 */     switch (row) {
/*      */       case 0:
/*  929 */         this.m00 = x;
/*  930 */         this.m01 = y;
/*  931 */         this.m02 = z;
/*  932 */         this.m03 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  936 */         this.m10 = x;
/*  937 */         this.m11 = y;
/*  938 */         this.m12 = z;
/*  939 */         this.m13 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  943 */         this.m20 = x;
/*  944 */         this.m21 = y;
/*  945 */         this.m22 = z;
/*  946 */         this.m23 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  950 */         this.m30 = x;
/*  951 */         this.m31 = y;
/*  952 */         this.m32 = z;
/*  953 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/*  957 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector4f v) {
/*  968 */     switch (row) {
/*      */       case 0:
/*  970 */         this.m00 = v.x;
/*  971 */         this.m01 = v.y;
/*  972 */         this.m02 = v.z;
/*  973 */         this.m03 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  977 */         this.m10 = v.x;
/*  978 */         this.m11 = v.y;
/*  979 */         this.m12 = v.z;
/*  980 */         this.m13 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  984 */         this.m20 = v.x;
/*  985 */         this.m21 = v.y;
/*  986 */         this.m22 = v.z;
/*  987 */         this.m23 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  991 */         this.m30 = v.x;
/*  992 */         this.m31 = v.y;
/*  993 */         this.m32 = v.z;
/*  994 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/*  998 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/*      */   public final void setRow(int row, float[] v) {
/* 1010 */     switch (row) {
/*      */       case 0:
/* 1012 */         this.m00 = v[0];
/* 1013 */         this.m01 = v[1];
/* 1014 */         this.m02 = v[2];
/* 1015 */         this.m03 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1019 */         this.m10 = v[0];
/* 1020 */         this.m11 = v[1];
/* 1021 */         this.m12 = v[2];
/* 1022 */         this.m13 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1026 */         this.m20 = v[0];
/* 1027 */         this.m21 = v[1];
/* 1028 */         this.m22 = v[2];
/* 1029 */         this.m23 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1033 */         this.m30 = v[0];
/* 1034 */         this.m31 = v[1];
/* 1035 */         this.m32 = v[2];
/* 1036 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1040 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/*      */   public final void setColumn(int column, float x, float y, float z, float w) {
/* 1054 */     switch (column) {
/*      */       case 0:
/* 1056 */         this.m00 = x;
/* 1057 */         this.m10 = y;
/* 1058 */         this.m20 = z;
/* 1059 */         this.m30 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1063 */         this.m01 = x;
/* 1064 */         this.m11 = y;
/* 1065 */         this.m21 = z;
/* 1066 */         this.m31 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1070 */         this.m02 = x;
/* 1071 */         this.m12 = y;
/* 1072 */         this.m22 = z;
/* 1073 */         this.m32 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1077 */         this.m03 = x;
/* 1078 */         this.m13 = y;
/* 1079 */         this.m23 = z;
/* 1080 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1084 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector4f v) {
/* 1095 */     switch (column) {
/*      */       case 0:
/* 1097 */         this.m00 = v.x;
/* 1098 */         this.m10 = v.y;
/* 1099 */         this.m20 = v.z;
/* 1100 */         this.m30 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1104 */         this.m01 = v.x;
/* 1105 */         this.m11 = v.y;
/* 1106 */         this.m21 = v.z;
/* 1107 */         this.m31 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1111 */         this.m02 = v.x;
/* 1112 */         this.m12 = v.y;
/* 1113 */         this.m22 = v.z;
/* 1114 */         this.m32 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1118 */         this.m03 = v.x;
/* 1119 */         this.m13 = v.y;
/* 1120 */         this.m23 = v.z;
/* 1121 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1125 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
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
/* 1136 */     switch (column) {
/*      */       case 0:
/* 1138 */         this.m00 = v[0];
/* 1139 */         this.m10 = v[1];
/* 1140 */         this.m20 = v[2];
/* 1141 */         this.m30 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1145 */         this.m01 = v[0];
/* 1146 */         this.m11 = v[1];
/* 1147 */         this.m21 = v[2];
/* 1148 */         this.m31 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1152 */         this.m02 = v[0];
/* 1153 */         this.m12 = v[1];
/* 1154 */         this.m22 = v[2];
/* 1155 */         this.m32 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1159 */         this.m03 = v[0];
/* 1160 */         this.m13 = v[1];
/* 1161 */         this.m23 = v[2];
/* 1162 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1166 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/* 1176 */     this.m00 += scalar;
/* 1177 */     this.m01 += scalar;
/* 1178 */     this.m02 += scalar;
/* 1179 */     this.m03 += scalar;
/* 1180 */     this.m10 += scalar;
/* 1181 */     this.m11 += scalar;
/* 1182 */     this.m12 += scalar;
/* 1183 */     this.m13 += scalar;
/* 1184 */     this.m20 += scalar;
/* 1185 */     this.m21 += scalar;
/* 1186 */     this.m22 += scalar;
/* 1187 */     this.m23 += scalar;
/* 1188 */     this.m30 += scalar;
/* 1189 */     this.m31 += scalar;
/* 1190 */     this.m32 += scalar;
/* 1191 */     this.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar, Matrix4f m1) {
/* 1202 */     m1.m00 += scalar;
/* 1203 */     m1.m01 += scalar;
/* 1204 */     m1.m02 += scalar;
/* 1205 */     m1.m03 += scalar;
/* 1206 */     m1.m10 += scalar;
/* 1207 */     m1.m11 += scalar;
/* 1208 */     m1.m12 += scalar;
/* 1209 */     m1.m13 += scalar;
/* 1210 */     m1.m20 += scalar;
/* 1211 */     m1.m21 += scalar;
/* 1212 */     m1.m22 += scalar;
/* 1213 */     m1.m23 += scalar;
/* 1214 */     m1.m30 += scalar;
/* 1215 */     m1.m31 += scalar;
/* 1216 */     m1.m32 += scalar;
/* 1217 */     m1.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1, Matrix4f m2) {
/* 1227 */     m1.m00 += m2.m00;
/* 1228 */     m1.m01 += m2.m01;
/* 1229 */     m1.m02 += m2.m02;
/* 1230 */     m1.m03 += m2.m03;
/*      */     
/* 1232 */     m1.m10 += m2.m10;
/* 1233 */     m1.m11 += m2.m11;
/* 1234 */     m1.m12 += m2.m12;
/* 1235 */     m1.m13 += m2.m13;
/*      */     
/* 1237 */     m1.m20 += m2.m20;
/* 1238 */     m1.m21 += m2.m21;
/* 1239 */     m1.m22 += m2.m22;
/* 1240 */     m1.m23 += m2.m23;
/*      */     
/* 1242 */     m1.m30 += m2.m30;
/* 1243 */     m1.m31 += m2.m31;
/* 1244 */     m1.m32 += m2.m32;
/* 1245 */     m1.m33 += m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1) {
/* 1255 */     this.m00 += m1.m00;
/* 1256 */     this.m01 += m1.m01;
/* 1257 */     this.m02 += m1.m02;
/* 1258 */     this.m03 += m1.m03;
/*      */     
/* 1260 */     this.m10 += m1.m10;
/* 1261 */     this.m11 += m1.m11;
/* 1262 */     this.m12 += m1.m12;
/* 1263 */     this.m13 += m1.m13;
/*      */     
/* 1265 */     this.m20 += m1.m20;
/* 1266 */     this.m21 += m1.m21;
/* 1267 */     this.m22 += m1.m22;
/* 1268 */     this.m23 += m1.m23;
/*      */     
/* 1270 */     this.m30 += m1.m30;
/* 1271 */     this.m31 += m1.m31;
/* 1272 */     this.m32 += m1.m32;
/* 1273 */     this.m33 += m1.m33;
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
/*      */   public final void sub(Matrix4f m1, Matrix4f m2) {
/* 1285 */     m1.m00 -= m2.m00;
/* 1286 */     m1.m01 -= m2.m01;
/* 1287 */     m1.m02 -= m2.m02;
/* 1288 */     m1.m03 -= m2.m03;
/*      */     
/* 1290 */     m1.m10 -= m2.m10;
/* 1291 */     m1.m11 -= m2.m11;
/* 1292 */     m1.m12 -= m2.m12;
/* 1293 */     m1.m13 -= m2.m13;
/*      */     
/* 1295 */     m1.m20 -= m2.m20;
/* 1296 */     m1.m21 -= m2.m21;
/* 1297 */     m1.m22 -= m2.m22;
/* 1298 */     m1.m23 -= m2.m23;
/*      */     
/* 1300 */     m1.m30 -= m2.m30;
/* 1301 */     m1.m31 -= m2.m31;
/* 1302 */     m1.m32 -= m2.m32;
/* 1303 */     m1.m33 -= m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4f m1) {
/* 1313 */     this.m00 -= m1.m00;
/* 1314 */     this.m01 -= m1.m01;
/* 1315 */     this.m02 -= m1.m02;
/* 1316 */     this.m03 -= m1.m03;
/*      */     
/* 1318 */     this.m10 -= m1.m10;
/* 1319 */     this.m11 -= m1.m11;
/* 1320 */     this.m12 -= m1.m12;
/* 1321 */     this.m13 -= m1.m13;
/*      */     
/* 1323 */     this.m20 -= m1.m20;
/* 1324 */     this.m21 -= m1.m21;
/* 1325 */     this.m22 -= m1.m22;
/* 1326 */     this.m23 -= m1.m23;
/*      */     
/* 1328 */     this.m30 -= m1.m30;
/* 1329 */     this.m31 -= m1.m31;
/* 1330 */     this.m32 -= m1.m32;
/* 1331 */     this.m33 -= m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1341 */     float temp = this.m10;
/* 1342 */     this.m10 = this.m01;
/* 1343 */     this.m01 = temp;
/*      */     
/* 1345 */     temp = this.m20;
/* 1346 */     this.m20 = this.m02;
/* 1347 */     this.m02 = temp;
/*      */     
/* 1349 */     temp = this.m30;
/* 1350 */     this.m30 = this.m03;
/* 1351 */     this.m03 = temp;
/*      */     
/* 1353 */     temp = this.m21;
/* 1354 */     this.m21 = this.m12;
/* 1355 */     this.m12 = temp;
/*      */     
/* 1357 */     temp = this.m31;
/* 1358 */     this.m31 = this.m13;
/* 1359 */     this.m13 = temp;
/*      */     
/* 1361 */     temp = this.m32;
/* 1362 */     this.m32 = this.m23;
/* 1363 */     this.m23 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4f m1) {
/* 1372 */     if (this != m1) {
/* 1373 */       this.m00 = m1.m00;
/* 1374 */       this.m01 = m1.m10;
/* 1375 */       this.m02 = m1.m20;
/* 1376 */       this.m03 = m1.m30;
/*      */       
/* 1378 */       this.m10 = m1.m01;
/* 1379 */       this.m11 = m1.m11;
/* 1380 */       this.m12 = m1.m21;
/* 1381 */       this.m13 = m1.m31;
/*      */       
/* 1383 */       this.m20 = m1.m02;
/* 1384 */       this.m21 = m1.m12;
/* 1385 */       this.m22 = m1.m22;
/* 1386 */       this.m23 = m1.m32;
/*      */       
/* 1388 */       this.m30 = m1.m03;
/* 1389 */       this.m31 = m1.m13;
/* 1390 */       this.m32 = m1.m23;
/* 1391 */       this.m33 = m1.m33;
/*      */     } else {
/* 1393 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/* 1403 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/* 1404 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1405 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1407 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1408 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/* 1409 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1411 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1412 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1413 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */     
/* 1415 */     this.m03 = 0.0F;
/* 1416 */     this.m13 = 0.0F;
/* 1417 */     this.m23 = 0.0F;
/*      */     
/* 1419 */     this.m30 = 0.0F;
/* 1420 */     this.m31 = 0.0F;
/* 1421 */     this.m32 = 0.0F;
/* 1422 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/* 1432 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 1433 */     if (mag < 1.0E-8D) {
/* 1434 */       this.m00 = 1.0F;
/* 1435 */       this.m01 = 0.0F;
/* 1436 */       this.m02 = 0.0F;
/*      */       
/* 1438 */       this.m10 = 0.0F;
/* 1439 */       this.m11 = 1.0F;
/* 1440 */       this.m12 = 0.0F;
/*      */       
/* 1442 */       this.m20 = 0.0F;
/* 1443 */       this.m21 = 0.0F;
/* 1444 */       this.m22 = 1.0F;
/*      */     } else {
/* 1446 */       mag = 1.0F / mag;
/* 1447 */       float ax = a1.x * mag;
/* 1448 */       float ay = a1.y * mag;
/* 1449 */       float az = a1.z * mag;
/*      */       
/* 1451 */       float sinTheta = (float)Math.sin(a1.angle);
/* 1452 */       float cosTheta = (float)Math.cos(a1.angle);
/* 1453 */       float t = 1.0F - cosTheta;
/*      */       
/* 1455 */       float xz = ax * az;
/* 1456 */       float xy = ax * ay;
/* 1457 */       float yz = ay * az;
/*      */       
/* 1459 */       this.m00 = t * ax * ax + cosTheta;
/* 1460 */       this.m01 = t * xy - sinTheta * az;
/* 1461 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1463 */       this.m10 = t * xy + sinTheta * az;
/* 1464 */       this.m11 = t * ay * ay + cosTheta;
/* 1465 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1467 */       this.m20 = t * xz - sinTheta * ay;
/* 1468 */       this.m21 = t * yz + sinTheta * ax;
/* 1469 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/* 1471 */     this.m03 = 0.0F;
/* 1472 */     this.m13 = 0.0F;
/* 1473 */     this.m23 = 0.0F;
/*      */     
/* 1475 */     this.m30 = 0.0F;
/* 1476 */     this.m31 = 0.0F;
/* 1477 */     this.m32 = 0.0F;
/* 1478 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/* 1488 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1489 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1490 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1492 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1493 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1494 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1496 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1497 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1498 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1500 */     this.m03 = 0.0F;
/* 1501 */     this.m13 = 0.0F;
/* 1502 */     this.m23 = 0.0F;
/*      */     
/* 1504 */     this.m30 = 0.0F;
/* 1505 */     this.m31 = 0.0F;
/* 1506 */     this.m32 = 0.0F;
/* 1507 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/* 1517 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/* 1519 */     if (mag < 1.0E-8D) {
/* 1520 */       this.m00 = 1.0F;
/* 1521 */       this.m01 = 0.0F;
/* 1522 */       this.m02 = 0.0F;
/*      */       
/* 1524 */       this.m10 = 0.0F;
/* 1525 */       this.m11 = 1.0F;
/* 1526 */       this.m12 = 0.0F;
/*      */       
/* 1528 */       this.m20 = 0.0F;
/* 1529 */       this.m21 = 0.0F;
/* 1530 */       this.m22 = 1.0F;
/*      */     } else {
/* 1532 */       mag = 1.0D / mag;
/* 1533 */       double ax = a1.x * mag;
/* 1534 */       double ay = a1.y * mag;
/* 1535 */       double az = a1.z * mag;
/*      */       
/* 1537 */       float sinTheta = (float)Math.sin(a1.angle);
/* 1538 */       float cosTheta = (float)Math.cos(a1.angle);
/* 1539 */       float t = 1.0F - cosTheta;
/*      */       
/* 1541 */       float xz = (float)(ax * az);
/* 1542 */       float xy = (float)(ax * ay);
/* 1543 */       float yz = (float)(ay * az);
/*      */       
/* 1545 */       this.m00 = t * (float)(ax * ax) + cosTheta;
/* 1546 */       this.m01 = t * xy - sinTheta * (float)az;
/* 1547 */       this.m02 = t * xz + sinTheta * (float)ay;
/*      */       
/* 1549 */       this.m10 = t * xy + sinTheta * (float)az;
/* 1550 */       this.m11 = t * (float)(ay * ay) + cosTheta;
/* 1551 */       this.m12 = t * yz - sinTheta * (float)ax;
/*      */       
/* 1553 */       this.m20 = t * xz - sinTheta * (float)ay;
/* 1554 */       this.m21 = t * yz + sinTheta * (float)ax;
/* 1555 */       this.m22 = t * (float)(az * az) + cosTheta;
/*      */     } 
/* 1557 */     this.m03 = 0.0F;
/* 1558 */     this.m13 = 0.0F;
/* 1559 */     this.m23 = 0.0F;
/*      */     
/* 1561 */     this.m30 = 0.0F;
/* 1562 */     this.m31 = 0.0F;
/* 1563 */     this.m32 = 0.0F;
/* 1564 */     this.m33 = 1.0F;
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
/* 1576 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/* 1577 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1578 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1580 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1581 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/* 1582 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1584 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1585 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1586 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/* 1588 */     this.m03 = (float)t1.x;
/* 1589 */     this.m13 = (float)t1.y;
/* 1590 */     this.m23 = (float)t1.z;
/*      */     
/* 1592 */     this.m30 = 0.0F;
/* 1593 */     this.m31 = 0.0F;
/* 1594 */     this.m32 = 0.0F;
/* 1595 */     this.m33 = 1.0F;
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
/* 1607 */     this.m00 = s * (1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z);
/* 1608 */     this.m10 = s * 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1609 */     this.m20 = s * 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1611 */     this.m01 = s * 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1612 */     this.m11 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z);
/* 1613 */     this.m21 = s * 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1615 */     this.m02 = s * 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1616 */     this.m12 = s * 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1617 */     this.m22 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y);
/*      */     
/* 1619 */     this.m03 = t1.x;
/* 1620 */     this.m13 = t1.y;
/* 1621 */     this.m23 = t1.z;
/*      */     
/* 1623 */     this.m30 = 0.0F;
/* 1624 */     this.m31 = 0.0F;
/* 1625 */     this.m32 = 0.0F;
/* 1626 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/* 1636 */     this.m00 = (float)m1.m00;
/* 1637 */     this.m01 = (float)m1.m01;
/* 1638 */     this.m02 = (float)m1.m02;
/* 1639 */     this.m03 = (float)m1.m03;
/*      */     
/* 1641 */     this.m10 = (float)m1.m10;
/* 1642 */     this.m11 = (float)m1.m11;
/* 1643 */     this.m12 = (float)m1.m12;
/* 1644 */     this.m13 = (float)m1.m13;
/*      */     
/* 1646 */     this.m20 = (float)m1.m20;
/* 1647 */     this.m21 = (float)m1.m21;
/* 1648 */     this.m22 = (float)m1.m22;
/* 1649 */     this.m23 = (float)m1.m23;
/*      */     
/* 1651 */     this.m30 = (float)m1.m30;
/* 1652 */     this.m31 = (float)m1.m31;
/* 1653 */     this.m32 = (float)m1.m32;
/* 1654 */     this.m33 = (float)m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/* 1664 */     this.m00 = m1.m00;
/* 1665 */     this.m01 = m1.m01;
/* 1666 */     this.m02 = m1.m02;
/* 1667 */     this.m03 = m1.m03;
/*      */     
/* 1669 */     this.m10 = m1.m10;
/* 1670 */     this.m11 = m1.m11;
/* 1671 */     this.m12 = m1.m12;
/* 1672 */     this.m13 = m1.m13;
/*      */     
/* 1674 */     this.m20 = m1.m20;
/* 1675 */     this.m21 = m1.m21;
/* 1676 */     this.m22 = m1.m22;
/* 1677 */     this.m23 = m1.m23;
/*      */     
/* 1679 */     this.m30 = m1.m30;
/* 1680 */     this.m31 = m1.m31;
/* 1681 */     this.m32 = m1.m32;
/* 1682 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4f m1) {
/* 1693 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1701 */     invertGeneral(this);
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
/*      */   final void invertGeneral(Matrix4f m1) {
/* 1713 */     double[] temp = new double[16];
/* 1714 */     double[] result = new double[16];
/* 1715 */     int[] row_perm = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1722 */     temp[0] = m1.m00;
/* 1723 */     temp[1] = m1.m01;
/* 1724 */     temp[2] = m1.m02;
/* 1725 */     temp[3] = m1.m03;
/*      */     
/* 1727 */     temp[4] = m1.m10;
/* 1728 */     temp[5] = m1.m11;
/* 1729 */     temp[6] = m1.m12;
/* 1730 */     temp[7] = m1.m13;
/*      */     
/* 1732 */     temp[8] = m1.m20;
/* 1733 */     temp[9] = m1.m21;
/* 1734 */     temp[10] = m1.m22;
/* 1735 */     temp[11] = m1.m23;
/*      */     
/* 1737 */     temp[12] = m1.m30;
/* 1738 */     temp[13] = m1.m31;
/* 1739 */     temp[14] = m1.m32;
/* 1740 */     temp[15] = m1.m33;
/*      */ 
/*      */     
/* 1743 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1745 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4f12"));
/*      */     }
/*      */ 
/*      */     
/* 1749 */     for (int i = 0; i < 16; ) { result[i] = 0.0D; i++; }
/* 1750 */      result[0] = 1.0D; result[5] = 1.0D; result[10] = 1.0D; result[15] = 1.0D;
/* 1751 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1753 */     this.m00 = (float)result[0];
/* 1754 */     this.m01 = (float)result[1];
/* 1755 */     this.m02 = (float)result[2];
/* 1756 */     this.m03 = (float)result[3];
/*      */     
/* 1758 */     this.m10 = (float)result[4];
/* 1759 */     this.m11 = (float)result[5];
/* 1760 */     this.m12 = (float)result[6];
/* 1761 */     this.m13 = (float)result[7];
/*      */     
/* 1763 */     this.m20 = (float)result[8];
/* 1764 */     this.m21 = (float)result[9];
/* 1765 */     this.m22 = (float)result[10];
/* 1766 */     this.m23 = (float)result[11];
/*      */     
/* 1768 */     this.m30 = (float)result[12];
/* 1769 */     this.m31 = (float)result[13];
/* 1770 */     this.m32 = (float)result[14];
/* 1771 */     this.m33 = (float)result[15];
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
/* 1798 */     double[] row_scale = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1806 */     int ptr = 0;
/* 1807 */     int rs = 0;
/*      */ 
/*      */     
/* 1810 */     int i = 4;
/* 1811 */     while (i-- != 0) {
/* 1812 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1815 */       int k = 4;
/* 1816 */       while (k-- != 0) {
/* 1817 */         double temp = matrix0[ptr++];
/* 1818 */         temp = Math.abs(temp);
/* 1819 */         if (temp > big) {
/* 1820 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1825 */       if (big == 0.0D) {
/* 1826 */         return false;
/*      */       }
/* 1828 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1836 */     int mtx = 0;
/*      */ 
/*      */     
/* 1839 */     for (int j = 0; j < 4; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1845 */       for (k = 0; k < j; k++) {
/* 1846 */         int target = mtx + 4 * k + j;
/* 1847 */         double sum = matrix0[target];
/* 1848 */         int m = k;
/* 1849 */         int p1 = mtx + 4 * k;
/* 1850 */         int p2 = mtx + j;
/* 1851 */         while (m-- != 0) {
/* 1852 */           sum -= matrix0[p1] * matrix0[p2];
/* 1853 */           p1++;
/* 1854 */           p2 += 4;
/*      */         } 
/* 1856 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1861 */       double big = 0.0D;
/* 1862 */       int imax = -1;
/* 1863 */       for (k = j; k < 4; k++) {
/* 1864 */         int target = mtx + 4 * k + j;
/* 1865 */         double sum = matrix0[target];
/* 1866 */         int m = j;
/* 1867 */         int p1 = mtx + 4 * k;
/* 1868 */         int p2 = mtx + j;
/* 1869 */         while (m-- != 0) {
/* 1870 */           sum -= matrix0[p1] * matrix0[p2];
/* 1871 */           p1++;
/* 1872 */           p2 += 4;
/*      */         } 
/* 1874 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1877 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1878 */           big = temp;
/* 1879 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1883 */       if (imax < 0) {
/* 1884 */         throw new RuntimeException(VecMathI18N.getString("Matrix4f13"));
/*      */       }
/*      */ 
/*      */       
/* 1888 */       if (j != imax) {
/*      */         
/* 1890 */         int m = 4;
/* 1891 */         int p1 = mtx + 4 * imax;
/* 1892 */         int p2 = mtx + 4 * j;
/* 1893 */         while (m-- != 0) {
/* 1894 */           double temp = matrix0[p1];
/* 1895 */           matrix0[p1++] = matrix0[p2];
/* 1896 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1900 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1904 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1907 */       if (matrix0[mtx + 4 * j + j] == 0.0D) {
/* 1908 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1912 */       if (j != 3) {
/* 1913 */         double temp = 1.0D / matrix0[mtx + 4 * j + j];
/* 1914 */         int target = mtx + 4 * (j + 1) + j;
/* 1915 */         k = 3 - j;
/* 1916 */         while (k-- != 0) {
/* 1917 */           matrix0[target] = matrix0[target] * temp;
/* 1918 */           target += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1924 */     return true;
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
/* 1954 */     int rp = 0;
/*      */ 
/*      */     
/* 1957 */     for (int k = 0; k < 4; k++) {
/*      */       
/* 1959 */       int cv = k;
/* 1960 */       int ii = -1;
/*      */ 
/*      */       
/* 1963 */       for (int i = 0; i < 4; i++) {
/*      */ 
/*      */         
/* 1966 */         int ip = row_perm[rp + i];
/* 1967 */         double sum = matrix2[cv + 4 * ip];
/* 1968 */         matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
/* 1969 */         if (ii >= 0) {
/*      */           
/* 1971 */           int m = i << 2;
/* 1972 */           for (int j = ii; j <= i - 1; j++) {
/* 1973 */             sum -= matrix1[m + j] * matrix2[cv + 4 * j];
/*      */           }
/*      */         }
/* 1976 */         else if (sum != 0.0D) {
/* 1977 */           ii = i;
/*      */         } 
/* 1979 */         matrix2[cv + 4 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1984 */       int rv = 12;
/* 1985 */       matrix2[cv + 12] = matrix2[cv + 12] / matrix1[rv + 3];
/*      */       
/* 1987 */       rv -= 4;
/* 1988 */       matrix2[cv + 8] = (matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
/*      */ 
/*      */       
/* 1991 */       rv -= 4;
/* 1992 */       matrix2[cv + 4] = (matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
/*      */ 
/*      */ 
/*      */       
/* 1996 */       rv -= 4;
/* 1997 */       matrix2[cv] = (matrix2[cv] - matrix1[rv + 1] * matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv];
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
/*      */   public final float determinant() {
/* 2014 */     float det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/*      */     
/* 2016 */     det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/*      */     
/* 2018 */     det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/*      */     
/* 2020 */     det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */ 
/*      */     
/* 2023 */     return det;
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
/* 2035 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02; this.m03 = 0.0F;
/* 2036 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12; this.m13 = 0.0F;
/* 2037 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22; this.m23 = 0.0F;
/* 2038 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
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
/* 2050 */     this.m00 = (float)m1.m00; this.m01 = (float)m1.m01; this.m02 = (float)m1.m02; this.m03 = 0.0F;
/* 2051 */     this.m10 = (float)m1.m10; this.m11 = (float)m1.m11; this.m12 = (float)m1.m12; this.m13 = 0.0F;
/* 2052 */     this.m20 = (float)m1.m20; this.m21 = (float)m1.m21; this.m22 = (float)m1.m22; this.m23 = 0.0F;
/* 2053 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 2063 */     this.m00 = scale;
/* 2064 */     this.m01 = 0.0F;
/* 2065 */     this.m02 = 0.0F;
/* 2066 */     this.m03 = 0.0F;
/*      */     
/* 2068 */     this.m10 = 0.0F;
/* 2069 */     this.m11 = scale;
/* 2070 */     this.m12 = 0.0F;
/* 2071 */     this.m13 = 0.0F;
/*      */     
/* 2073 */     this.m20 = 0.0F;
/* 2074 */     this.m21 = 0.0F;
/* 2075 */     this.m22 = scale;
/* 2076 */     this.m23 = 0.0F;
/*      */     
/* 2078 */     this.m30 = 0.0F;
/* 2079 */     this.m31 = 0.0F;
/* 2080 */     this.m32 = 0.0F;
/* 2081 */     this.m33 = 1.0F;
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
/* 2092 */     this.m00 = m[0];
/* 2093 */     this.m01 = m[1];
/* 2094 */     this.m02 = m[2];
/* 2095 */     this.m03 = m[3];
/* 2096 */     this.m10 = m[4];
/* 2097 */     this.m11 = m[5];
/* 2098 */     this.m12 = m[6];
/* 2099 */     this.m13 = m[7];
/* 2100 */     this.m20 = m[8];
/* 2101 */     this.m21 = m[9];
/* 2102 */     this.m22 = m[10];
/* 2103 */     this.m23 = m[11];
/* 2104 */     this.m30 = m[12];
/* 2105 */     this.m31 = m[13];
/* 2106 */     this.m32 = m[14];
/* 2107 */     this.m33 = m[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3f v1) {
/* 2117 */     this.m00 = 1.0F;
/* 2118 */     this.m01 = 0.0F;
/* 2119 */     this.m02 = 0.0F;
/* 2120 */     this.m03 = v1.x;
/*      */     
/* 2122 */     this.m10 = 0.0F;
/* 2123 */     this.m11 = 1.0F;
/* 2124 */     this.m12 = 0.0F;
/* 2125 */     this.m13 = v1.y;
/*      */     
/* 2127 */     this.m20 = 0.0F;
/* 2128 */     this.m21 = 0.0F;
/* 2129 */     this.m22 = 1.0F;
/* 2130 */     this.m23 = v1.z;
/*      */     
/* 2132 */     this.m30 = 0.0F;
/* 2133 */     this.m31 = 0.0F;
/* 2134 */     this.m32 = 0.0F;
/* 2135 */     this.m33 = 1.0F;
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
/*      */   public final void set(float scale, Vector3f t1) {
/* 2147 */     this.m00 = scale;
/* 2148 */     this.m01 = 0.0F;
/* 2149 */     this.m02 = 0.0F;
/* 2150 */     this.m03 = t1.x;
/*      */     
/* 2152 */     this.m10 = 0.0F;
/* 2153 */     this.m11 = scale;
/* 2154 */     this.m12 = 0.0F;
/* 2155 */     this.m13 = t1.y;
/*      */     
/* 2157 */     this.m20 = 0.0F;
/* 2158 */     this.m21 = 0.0F;
/* 2159 */     this.m22 = scale;
/* 2160 */     this.m23 = t1.z;
/*      */     
/* 2162 */     this.m30 = 0.0F;
/* 2163 */     this.m31 = 0.0F;
/* 2164 */     this.m32 = 0.0F;
/* 2165 */     this.m33 = 1.0F;
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
/*      */   public final void set(Vector3f t1, float scale) {
/* 2177 */     this.m00 = scale;
/* 2178 */     this.m01 = 0.0F;
/* 2179 */     this.m02 = 0.0F;
/* 2180 */     this.m03 = scale * t1.x;
/*      */     
/* 2182 */     this.m10 = 0.0F;
/* 2183 */     this.m11 = scale;
/* 2184 */     this.m12 = 0.0F;
/* 2185 */     this.m13 = scale * t1.y;
/*      */     
/* 2187 */     this.m20 = 0.0F;
/* 2188 */     this.m21 = 0.0F;
/* 2189 */     this.m22 = scale;
/* 2190 */     this.m23 = scale * t1.z;
/*      */     
/* 2192 */     this.m30 = 0.0F;
/* 2193 */     this.m31 = 0.0F;
/* 2194 */     this.m32 = 0.0F;
/* 2195 */     this.m33 = 1.0F;
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
/* 2208 */     this.m00 = m1.m00 * scale;
/* 2209 */     this.m01 = m1.m01 * scale;
/* 2210 */     this.m02 = m1.m02 * scale;
/* 2211 */     this.m03 = t1.x;
/*      */     
/* 2213 */     this.m10 = m1.m10 * scale;
/* 2214 */     this.m11 = m1.m11 * scale;
/* 2215 */     this.m12 = m1.m12 * scale;
/* 2216 */     this.m13 = t1.y;
/*      */     
/* 2218 */     this.m20 = m1.m20 * scale;
/* 2219 */     this.m21 = m1.m21 * scale;
/* 2220 */     this.m22 = m1.m22 * scale;
/* 2221 */     this.m23 = t1.z;
/*      */     
/* 2223 */     this.m30 = 0.0F;
/* 2224 */     this.m31 = 0.0F;
/* 2225 */     this.m32 = 0.0F;
/* 2226 */     this.m33 = 1.0F;
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
/*      */   public final void set(Matrix3d m1, Vector3d t1, double scale) {
/* 2239 */     this.m00 = (float)(m1.m00 * scale);
/* 2240 */     this.m01 = (float)(m1.m01 * scale);
/* 2241 */     this.m02 = (float)(m1.m02 * scale);
/* 2242 */     this.m03 = (float)t1.x;
/*      */     
/* 2244 */     this.m10 = (float)(m1.m10 * scale);
/* 2245 */     this.m11 = (float)(m1.m11 * scale);
/* 2246 */     this.m12 = (float)(m1.m12 * scale);
/* 2247 */     this.m13 = (float)t1.y;
/*      */     
/* 2249 */     this.m20 = (float)(m1.m20 * scale);
/* 2250 */     this.m21 = (float)(m1.m21 * scale);
/* 2251 */     this.m22 = (float)(m1.m22 * scale);
/* 2252 */     this.m23 = (float)t1.z;
/*      */     
/* 2254 */     this.m30 = 0.0F;
/* 2255 */     this.m31 = 0.0F;
/* 2256 */     this.m32 = 0.0F;
/* 2257 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3f trans) {
/* 2268 */     this.m03 = trans.x;
/* 2269 */     this.m13 = trans.y;
/* 2270 */     this.m23 = trans.z;
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
/*      */   public final void rotX(float angle) {
/* 2283 */     float sinAngle = (float)Math.sin(angle);
/* 2284 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2286 */     this.m00 = 1.0F;
/* 2287 */     this.m01 = 0.0F;
/* 2288 */     this.m02 = 0.0F;
/* 2289 */     this.m03 = 0.0F;
/*      */     
/* 2291 */     this.m10 = 0.0F;
/* 2292 */     this.m11 = cosAngle;
/* 2293 */     this.m12 = -sinAngle;
/* 2294 */     this.m13 = 0.0F;
/*      */     
/* 2296 */     this.m20 = 0.0F;
/* 2297 */     this.m21 = sinAngle;
/* 2298 */     this.m22 = cosAngle;
/* 2299 */     this.m23 = 0.0F;
/*      */     
/* 2301 */     this.m30 = 0.0F;
/* 2302 */     this.m31 = 0.0F;
/* 2303 */     this.m32 = 0.0F;
/* 2304 */     this.m33 = 1.0F;
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
/* 2316 */     float sinAngle = (float)Math.sin(angle);
/* 2317 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2319 */     this.m00 = cosAngle;
/* 2320 */     this.m01 = 0.0F;
/* 2321 */     this.m02 = sinAngle;
/* 2322 */     this.m03 = 0.0F;
/*      */     
/* 2324 */     this.m10 = 0.0F;
/* 2325 */     this.m11 = 1.0F;
/* 2326 */     this.m12 = 0.0F;
/* 2327 */     this.m13 = 0.0F;
/*      */     
/* 2329 */     this.m20 = -sinAngle;
/* 2330 */     this.m21 = 0.0F;
/* 2331 */     this.m22 = cosAngle;
/* 2332 */     this.m23 = 0.0F;
/*      */     
/* 2334 */     this.m30 = 0.0F;
/* 2335 */     this.m31 = 0.0F;
/* 2336 */     this.m32 = 0.0F;
/* 2337 */     this.m33 = 1.0F;
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
/* 2349 */     float sinAngle = (float)Math.sin(angle);
/* 2350 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2352 */     this.m00 = cosAngle;
/* 2353 */     this.m01 = -sinAngle;
/* 2354 */     this.m02 = 0.0F;
/* 2355 */     this.m03 = 0.0F;
/*      */     
/* 2357 */     this.m10 = sinAngle;
/* 2358 */     this.m11 = cosAngle;
/* 2359 */     this.m12 = 0.0F;
/* 2360 */     this.m13 = 0.0F;
/*      */     
/* 2362 */     this.m20 = 0.0F;
/* 2363 */     this.m21 = 0.0F;
/* 2364 */     this.m22 = 1.0F;
/* 2365 */     this.m23 = 0.0F;
/*      */     
/* 2367 */     this.m30 = 0.0F;
/* 2368 */     this.m31 = 0.0F;
/* 2369 */     this.m32 = 0.0F;
/* 2370 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 2379 */     this.m00 *= scalar;
/* 2380 */     this.m01 *= scalar;
/* 2381 */     this.m02 *= scalar;
/* 2382 */     this.m03 *= scalar;
/* 2383 */     this.m10 *= scalar;
/* 2384 */     this.m11 *= scalar;
/* 2385 */     this.m12 *= scalar;
/* 2386 */     this.m13 *= scalar;
/* 2387 */     this.m20 *= scalar;
/* 2388 */     this.m21 *= scalar;
/* 2389 */     this.m22 *= scalar;
/* 2390 */     this.m23 *= scalar;
/* 2391 */     this.m30 *= scalar;
/* 2392 */     this.m31 *= scalar;
/* 2393 */     this.m32 *= scalar;
/* 2394 */     this.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar, Matrix4f m1) {
/* 2405 */     m1.m00 *= scalar;
/* 2406 */     m1.m01 *= scalar;
/* 2407 */     m1.m02 *= scalar;
/* 2408 */     m1.m03 *= scalar;
/* 2409 */     m1.m10 *= scalar;
/* 2410 */     m1.m11 *= scalar;
/* 2411 */     m1.m12 *= scalar;
/* 2412 */     m1.m13 *= scalar;
/* 2413 */     m1.m20 *= scalar;
/* 2414 */     m1.m21 *= scalar;
/* 2415 */     m1.m22 *= scalar;
/* 2416 */     m1.m23 *= scalar;
/* 2417 */     m1.m30 *= scalar;
/* 2418 */     m1.m31 *= scalar;
/* 2419 */     m1.m32 *= scalar;
/* 2420 */     m1.m33 *= scalar;
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
/*      */   public final void mul(Matrix4f m1) {
/* 2435 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20 + this.m03 * m1.m30;
/*      */     
/* 2437 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21 + this.m03 * m1.m31;
/*      */     
/* 2439 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22 + this.m03 * m1.m32;
/*      */     
/* 2441 */     float m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + this.m02 * m1.m23 + this.m03 * m1.m33;
/*      */ 
/*      */     
/* 2444 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20 + this.m13 * m1.m30;
/*      */     
/* 2446 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21 + this.m13 * m1.m31;
/*      */     
/* 2448 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22 + this.m13 * m1.m32;
/*      */     
/* 2450 */     float m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + this.m12 * m1.m23 + this.m13 * m1.m33;
/*      */ 
/*      */     
/* 2453 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20 + this.m23 * m1.m30;
/*      */     
/* 2455 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21 + this.m23 * m1.m31;
/*      */     
/* 2457 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22 + this.m23 * m1.m32;
/*      */     
/* 2459 */     float m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + this.m22 * m1.m23 + this.m23 * m1.m33;
/*      */ 
/*      */     
/* 2462 */     float m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + this.m32 * m1.m20 + this.m33 * m1.m30;
/*      */     
/* 2464 */     float m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + this.m32 * m1.m21 + this.m33 * m1.m31;
/*      */     
/* 2466 */     float m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + this.m32 * m1.m22 + this.m33 * m1.m32;
/*      */     
/* 2468 */     float m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + this.m32 * m1.m23 + this.m33 * m1.m33;
/*      */ 
/*      */     
/* 2471 */     this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2472 */     this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2473 */     this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2474 */     this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4f m1, Matrix4f m2) {
/* 2485 */     if (this != m1 && this != m2) {
/*      */       
/* 2487 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/*      */       
/* 2489 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/*      */       
/* 2491 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/*      */       
/* 2493 */       this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */ 
/*      */       
/* 2496 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/*      */       
/* 2498 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/*      */       
/* 2500 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/*      */       
/* 2502 */       this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */ 
/*      */       
/* 2505 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/*      */       
/* 2507 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/*      */       
/* 2509 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/*      */       
/* 2511 */       this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */ 
/*      */       
/* 2514 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/*      */       
/* 2516 */       this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/*      */       
/* 2518 */       this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/*      */       
/* 2520 */       this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2527 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2528 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2529 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2530 */       float m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2532 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2533 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2534 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2535 */       float m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2537 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2538 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2539 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2540 */       float m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2542 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2543 */       float m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2544 */       float m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2545 */       float m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2547 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2548 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2549 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2550 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeBoth(Matrix4f m1, Matrix4f m2) {
/* 2562 */     if (this != m1 && this != m2) {
/* 2563 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2564 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2565 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2566 */       this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2568 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2569 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2570 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2571 */       this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2573 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2574 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2575 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2576 */       this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2578 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2579 */       this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2580 */       this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2581 */       this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2588 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2589 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2590 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2591 */       float m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2593 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2594 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2595 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2596 */       float m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2598 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2599 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2600 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2601 */       float m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2603 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2604 */       float m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2605 */       float m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2606 */       float m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2608 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2609 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2610 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2611 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeRight(Matrix4f m1, Matrix4f m2) {
/* 2624 */     if (this != m1 && this != m2) {
/* 2625 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2626 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2627 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2628 */       this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2630 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2631 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2632 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2633 */       this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2635 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2636 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2637 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2638 */       this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2640 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2641 */       this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2642 */       this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2643 */       this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2650 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2651 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2652 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2653 */       float m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2655 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2656 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2657 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2658 */       float m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2660 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2661 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2662 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2663 */       float m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2665 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2666 */       float m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2667 */       float m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2668 */       float m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2670 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2671 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2672 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2673 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeLeft(Matrix4f m1, Matrix4f m2) {
/* 2687 */     if (this != m1 && this != m2) {
/* 2688 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2689 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2690 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2691 */       this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2693 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2694 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2695 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2696 */       this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2698 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2699 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2700 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2701 */       this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2703 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2704 */       this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2705 */       this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2706 */       this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2715 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2716 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2717 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2718 */       float m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2720 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2721 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2722 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2723 */       float m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2725 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2726 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2727 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2728 */       float m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2730 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2731 */       float m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2732 */       float m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2733 */       float m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2735 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2736 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2737 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2738 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public boolean equals(Matrix4f m1) {
/*      */     try {
/* 2753 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && this.m33 == m1.m33);
/*      */ 
/*      */     
/*      */     }
/*      */     catch (NullPointerException e2) {
/*      */ 
/*      */       
/* 2760 */       return false;
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
/*      */     try {
/* 2775 */       Matrix4f m2 = (Matrix4f)t1;
/* 2776 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && this.m33 == m2.m33);
/*      */ 
/*      */     
/*      */     }
/*      */     catch (ClassCastException|NullPointerException e1) {
/*      */ 
/*      */       
/* 2783 */       return false;
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
/*      */   public boolean epsilonEquals(Matrix4f m1, float epsilon) {
/* 2798 */     boolean status = (Math.abs(this.m00 - m1.m00) <= epsilon);
/*      */     
/* 2800 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 2801 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false; 
/* 2802 */     if (Math.abs(this.m03 - m1.m03) > epsilon) status = false;
/*      */     
/* 2804 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 2805 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 2806 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false; 
/* 2807 */     if (Math.abs(this.m13 - m1.m13) > epsilon) status = false;
/*      */     
/* 2809 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 2810 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 2811 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false; 
/* 2812 */     if (Math.abs(this.m23 - m1.m23) > epsilon) status = false;
/*      */     
/* 2814 */     if (Math.abs(this.m30 - m1.m30) > epsilon) status = false; 
/* 2815 */     if (Math.abs(this.m31 - m1.m31) > epsilon) status = false; 
/* 2816 */     if (Math.abs(this.m32 - m1.m32) > epsilon) status = false; 
/* 2817 */     if (Math.abs(this.m33 - m1.m33) > epsilon) status = false;
/*      */     
/* 2819 */     return status;
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
/* 2834 */     long bits = 1L;
/* 2835 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 2836 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 2837 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 2838 */     bits = VecMathUtil.hashFloatBits(bits, this.m03);
/* 2839 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 2840 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 2841 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 2842 */     bits = VecMathUtil.hashFloatBits(bits, this.m13);
/* 2843 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 2844 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 2845 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 2846 */     bits = VecMathUtil.hashFloatBits(bits, this.m23);
/* 2847 */     bits = VecMathUtil.hashFloatBits(bits, this.m30);
/* 2848 */     bits = VecMathUtil.hashFloatBits(bits, this.m31);
/* 2849 */     bits = VecMathUtil.hashFloatBits(bits, this.m32);
/* 2850 */     bits = VecMathUtil.hashFloatBits(bits, this.m33);
/* 2851 */     return VecMathUtil.hashFinish(bits);
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
/*      */   public final void transform(Tuple4f vec, Tuple4f vecOut) {
/* 2864 */     float x = this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w;
/*      */     
/* 2866 */     float y = this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w;
/*      */     
/* 2868 */     float z = this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w;
/*      */     
/* 2870 */     vecOut.w = this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w;
/*      */     
/* 2872 */     vecOut.x = x;
/* 2873 */     vecOut.y = y;
/* 2874 */     vecOut.z = z;
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
/*      */   public final void transform(Tuple4f vec) {
/* 2887 */     float x = this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w;
/*      */     
/* 2889 */     float y = this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w;
/*      */     
/* 2891 */     float z = this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w;
/*      */     
/* 2893 */     vec.w = this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w;
/*      */     
/* 2895 */     vec.x = x;
/* 2896 */     vec.y = y;
/* 2897 */     vec.z = z;
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
/*      */   public final void transform(Point3f point, Point3f pointOut) {
/* 2910 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 2911 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 2912 */     pointOut.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 2913 */     pointOut.x = x;
/* 2914 */     pointOut.y = y;
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
/* 2927 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 2928 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 2929 */     point.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 2930 */     point.x = x;
/* 2931 */     point.y = y;
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
/* 2944 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 2945 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 2946 */     normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 2947 */     normalOut.x = x;
/* 2948 */     normalOut.y = y;
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
/* 2961 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 2962 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 2963 */     normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 2964 */     normal.x = x;
/* 2965 */     normal.y = y;
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
/*      */   public final void setRotation(Matrix3d m1) {
/* 2981 */     double[] tmp_rot = new double[9];
/* 2982 */     double[] tmp_scale = new double[3];
/*      */     
/* 2984 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 2986 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 2987 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 2988 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 2990 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 2991 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 2992 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 2994 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 2995 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 2996 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
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
/*      */   public final void setRotation(Matrix3f m1) {
/* 3011 */     double[] tmp_rot = new double[9];
/* 3012 */     double[] tmp_scale = new double[3];
/*      */     
/* 3014 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3016 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 3017 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 3018 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 3020 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 3021 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 3022 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 3024 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 3025 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 3026 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
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
/* 3040 */     double[] tmp_rot = new double[9];
/* 3041 */     double[] tmp_scale = new double[3];
/* 3042 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3044 */     this.m00 = (float)((1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z) * tmp_scale[0]);
/* 3045 */     this.m10 = (float)((2.0F * (q1.x * q1.y + q1.w * q1.z)) * tmp_scale[0]);
/* 3046 */     this.m20 = (float)((2.0F * (q1.x * q1.z - q1.w * q1.y)) * tmp_scale[0]);
/*      */     
/* 3048 */     this.m01 = (float)((2.0F * (q1.x * q1.y - q1.w * q1.z)) * tmp_scale[1]);
/* 3049 */     this.m11 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z) * tmp_scale[1]);
/* 3050 */     this.m21 = (float)((2.0F * (q1.y * q1.z + q1.w * q1.x)) * tmp_scale[1]);
/*      */     
/* 3052 */     this.m02 = (float)((2.0F * (q1.x * q1.z + q1.w * q1.y)) * tmp_scale[2]);
/* 3053 */     this.m12 = (float)((2.0F * (q1.y * q1.z - q1.w * q1.x)) * tmp_scale[2]);
/* 3054 */     this.m22 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y) * tmp_scale[2]);
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
/*      */   public final void setRotation(Quat4d q1) {
/* 3070 */     double[] tmp_rot = new double[9];
/* 3071 */     double[] tmp_scale = new double[3];
/*      */     
/* 3073 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3075 */     this.m00 = (float)((1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z) * tmp_scale[0]);
/* 3076 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0]);
/* 3077 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0]);
/*      */     
/* 3079 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1]);
/* 3080 */     this.m11 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z) * tmp_scale[1]);
/* 3081 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1]);
/*      */     
/* 3083 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2]);
/* 3084 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2]);
/* 3085 */     this.m22 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y) * tmp_scale[2]);
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
/*      */   public final void setRotation(AxisAngle4f a1) {
/* 3099 */     double[] tmp_rot = new double[9];
/* 3100 */     double[] tmp_scale = new double[3];
/*      */     
/* 3102 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3104 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 3105 */     if (mag < 1.0E-8D) {
/* 3106 */       this.m00 = 1.0F;
/* 3107 */       this.m01 = 0.0F;
/* 3108 */       this.m02 = 0.0F;
/*      */       
/* 3110 */       this.m10 = 0.0F;
/* 3111 */       this.m11 = 1.0F;
/* 3112 */       this.m12 = 0.0F;
/*      */       
/* 3114 */       this.m20 = 0.0F;
/* 3115 */       this.m21 = 0.0F;
/* 3116 */       this.m22 = 1.0F;
/*      */     } else {
/* 3118 */       mag = 1.0D / mag;
/* 3119 */       double ax = a1.x * mag;
/* 3120 */       double ay = a1.y * mag;
/* 3121 */       double az = a1.z * mag;
/*      */       
/* 3123 */       double sinTheta = Math.sin(a1.angle);
/* 3124 */       double cosTheta = Math.cos(a1.angle);
/* 3125 */       double t = 1.0D - cosTheta;
/*      */       
/* 3127 */       double xz = (a1.x * a1.z);
/* 3128 */       double xy = (a1.x * a1.y);
/* 3129 */       double yz = (a1.y * a1.z);
/*      */       
/* 3131 */       this.m00 = (float)((t * ax * ax + cosTheta) * tmp_scale[0]);
/* 3132 */       this.m01 = (float)((t * xy - sinTheta * az) * tmp_scale[1]);
/* 3133 */       this.m02 = (float)((t * xz + sinTheta * ay) * tmp_scale[2]);
/*      */       
/* 3135 */       this.m10 = (float)((t * xy + sinTheta * az) * tmp_scale[0]);
/* 3136 */       this.m11 = (float)((t * ay * ay + cosTheta) * tmp_scale[1]);
/* 3137 */       this.m12 = (float)((t * yz - sinTheta * ax) * tmp_scale[2]);
/*      */       
/* 3139 */       this.m20 = (float)((t * xz - sinTheta * ay) * tmp_scale[0]);
/* 3140 */       this.m21 = (float)((t * yz + sinTheta * ax) * tmp_scale[1]);
/* 3141 */       this.m22 = (float)((t * az * az + cosTheta) * tmp_scale[2]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3152 */     this.m00 = 0.0F;
/* 3153 */     this.m01 = 0.0F;
/* 3154 */     this.m02 = 0.0F;
/* 3155 */     this.m03 = 0.0F;
/* 3156 */     this.m10 = 0.0F;
/* 3157 */     this.m11 = 0.0F;
/* 3158 */     this.m12 = 0.0F;
/* 3159 */     this.m13 = 0.0F;
/* 3160 */     this.m20 = 0.0F;
/* 3161 */     this.m21 = 0.0F;
/* 3162 */     this.m22 = 0.0F;
/* 3163 */     this.m23 = 0.0F;
/* 3164 */     this.m30 = 0.0F;
/* 3165 */     this.m31 = 0.0F;
/* 3166 */     this.m32 = 0.0F;
/* 3167 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3175 */     this.m00 = -this.m00;
/* 3176 */     this.m01 = -this.m01;
/* 3177 */     this.m02 = -this.m02;
/* 3178 */     this.m03 = -this.m03;
/* 3179 */     this.m10 = -this.m10;
/* 3180 */     this.m11 = -this.m11;
/* 3181 */     this.m12 = -this.m12;
/* 3182 */     this.m13 = -this.m13;
/* 3183 */     this.m20 = -this.m20;
/* 3184 */     this.m21 = -this.m21;
/* 3185 */     this.m22 = -this.m22;
/* 3186 */     this.m23 = -this.m23;
/* 3187 */     this.m30 = -this.m30;
/* 3188 */     this.m31 = -this.m31;
/* 3189 */     this.m32 = -this.m32;
/* 3190 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4f m1) {
/* 3200 */     this.m00 = -m1.m00;
/* 3201 */     this.m01 = -m1.m01;
/* 3202 */     this.m02 = -m1.m02;
/* 3203 */     this.m03 = -m1.m03;
/* 3204 */     this.m10 = -m1.m10;
/* 3205 */     this.m11 = -m1.m11;
/* 3206 */     this.m12 = -m1.m12;
/* 3207 */     this.m13 = -m1.m13;
/* 3208 */     this.m20 = -m1.m20;
/* 3209 */     this.m21 = -m1.m21;
/* 3210 */     this.m22 = -m1.m22;
/* 3211 */     this.m23 = -m1.m23;
/* 3212 */     this.m30 = -m1.m30;
/* 3213 */     this.m31 = -m1.m31;
/* 3214 */     this.m32 = -m1.m32;
/* 3215 */     this.m33 = -m1.m33;
/*      */   }
/*      */   
/*      */   private void getScaleRotate(double[] scales, double[] rots) {
/* 3219 */     double[] tmp = new double[9];
/* 3220 */     tmp[0] = this.m00;
/* 3221 */     tmp[1] = this.m01;
/* 3222 */     tmp[2] = this.m02;
/*      */     
/* 3224 */     tmp[3] = this.m10;
/* 3225 */     tmp[4] = this.m11;
/* 3226 */     tmp[5] = this.m12;
/*      */     
/* 3228 */     tmp[6] = this.m20;
/* 3229 */     tmp[7] = this.m21;
/* 3230 */     tmp[8] = this.m22;
/*      */     
/* 3232 */     Matrix3d.compute_svd(tmp, scales, rots);
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
/*      */     Matrix4f m1;
/*      */     try {
/* 3248 */       m1 = (Matrix4f)super.clone();
/* 3249 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3251 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3254 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM00() {
/* 3265 */     return this.m00;
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
/* 3276 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM01() {
/* 3287 */     return this.m01;
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
/* 3298 */     this.m01 = m01;
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
/* 3309 */     return this.m02;
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
/* 3320 */     this.m02 = m02;
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
/* 3331 */     return this.m10;
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
/* 3342 */     this.m10 = m10;
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
/* 3353 */     return this.m11;
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
/* 3364 */     this.m11 = m11;
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
/* 3375 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float m12) {
/* 3386 */     this.m12 = m12;
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
/* 3397 */     return this.m20;
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
/* 3408 */     this.m20 = m20;
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
/* 3419 */     return this.m21;
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
/* 3430 */     this.m21 = m21;
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
/* 3441 */     return this.m22;
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
/* 3452 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM03() {
/* 3463 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(float m03) {
/* 3474 */     this.m03 = m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM13() {
/* 3485 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(float m13) {
/* 3496 */     this.m13 = m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM23() {
/* 3507 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(float m23) {
/* 3518 */     this.m23 = m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM30() {
/* 3529 */     return this.m30;
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
/*      */   public final void setM30(float m30) {
/* 3541 */     this.m30 = m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM31() {
/* 3552 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(float m31) {
/* 3563 */     this.m31 = m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM32() {
/* 3574 */     return this.m32;
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
/*      */   public final void setM32(float m32) {
/* 3586 */     this.m32 = m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM33() {
/* 3597 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(float m33) {
/* 3608 */     this.m33 = m33;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Matrix4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */