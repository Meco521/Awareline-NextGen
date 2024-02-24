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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  152 */     this.m00 = m00;
/*  153 */     this.m01 = m01;
/*  154 */     this.m02 = m02;
/*  155 */     this.m03 = m03;
/*      */     
/*  157 */     this.m10 = m10;
/*  158 */     this.m11 = m11;
/*  159 */     this.m12 = m12;
/*  160 */     this.m13 = m13;
/*      */     
/*  162 */     this.m20 = m20;
/*  163 */     this.m21 = m21;
/*  164 */     this.m22 = m22;
/*  165 */     this.m23 = m23;
/*      */     
/*  167 */     this.m30 = m30;
/*  168 */     this.m31 = m31;
/*  169 */     this.m32 = m32;
/*  170 */     this.m33 = m33;
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
/*  181 */     this.m00 = v[0];
/*  182 */     this.m01 = v[1];
/*  183 */     this.m02 = v[2];
/*  184 */     this.m03 = v[3];
/*      */     
/*  186 */     this.m10 = v[4];
/*  187 */     this.m11 = v[5];
/*  188 */     this.m12 = v[6];
/*  189 */     this.m13 = v[7];
/*      */     
/*  191 */     this.m20 = v[8];
/*  192 */     this.m21 = v[9];
/*  193 */     this.m22 = v[10];
/*  194 */     this.m23 = v[11];
/*      */     
/*  196 */     this.m30 = v[12];
/*  197 */     this.m31 = v[13];
/*  198 */     this.m32 = v[14];
/*  199 */     this.m33 = v[15];
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
/*  214 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/*  215 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  216 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  218 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  219 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/*  220 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  222 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  223 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  224 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/*  226 */     this.m03 = t1.x;
/*  227 */     this.m13 = t1.y;
/*  228 */     this.m23 = t1.z;
/*      */     
/*  230 */     this.m30 = 0.0F;
/*  231 */     this.m31 = 0.0F;
/*  232 */     this.m32 = 0.0F;
/*  233 */     this.m33 = 1.0F;
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
/*  244 */     this.m00 = (float)m1.m00;
/*  245 */     this.m01 = (float)m1.m01;
/*  246 */     this.m02 = (float)m1.m02;
/*  247 */     this.m03 = (float)m1.m03;
/*      */     
/*  249 */     this.m10 = (float)m1.m10;
/*  250 */     this.m11 = (float)m1.m11;
/*  251 */     this.m12 = (float)m1.m12;
/*  252 */     this.m13 = (float)m1.m13;
/*      */     
/*  254 */     this.m20 = (float)m1.m20;
/*  255 */     this.m21 = (float)m1.m21;
/*  256 */     this.m22 = (float)m1.m22;
/*  257 */     this.m23 = (float)m1.m23;
/*      */     
/*  259 */     this.m30 = (float)m1.m30;
/*  260 */     this.m31 = (float)m1.m31;
/*  261 */     this.m32 = (float)m1.m32;
/*  262 */     this.m33 = (float)m1.m33;
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
/*  274 */     this.m00 = m1.m00;
/*  275 */     this.m01 = m1.m01;
/*  276 */     this.m02 = m1.m02;
/*  277 */     this.m03 = m1.m03;
/*      */     
/*  279 */     this.m10 = m1.m10;
/*  280 */     this.m11 = m1.m11;
/*  281 */     this.m12 = m1.m12;
/*  282 */     this.m13 = m1.m13;
/*      */     
/*  284 */     this.m20 = m1.m20;
/*  285 */     this.m21 = m1.m21;
/*  286 */     this.m22 = m1.m22;
/*  287 */     this.m23 = m1.m23;
/*      */     
/*  289 */     this.m30 = m1.m30;
/*  290 */     this.m31 = m1.m31;
/*  291 */     this.m32 = m1.m32;
/*  292 */     this.m33 = m1.m33;
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
/*  308 */     this.m00 = m1.m00 * s;
/*  309 */     this.m01 = m1.m01 * s;
/*  310 */     this.m02 = m1.m02 * s;
/*  311 */     this.m03 = t1.x;
/*      */     
/*  313 */     this.m10 = m1.m10 * s;
/*  314 */     this.m11 = m1.m11 * s;
/*  315 */     this.m12 = m1.m12 * s;
/*  316 */     this.m13 = t1.y;
/*      */     
/*  318 */     this.m20 = m1.m20 * s;
/*  319 */     this.m21 = m1.m21 * s;
/*  320 */     this.m22 = m1.m22 * s;
/*  321 */     this.m23 = t1.z;
/*      */     
/*  323 */     this.m30 = 0.0F;
/*  324 */     this.m31 = 0.0F;
/*  325 */     this.m32 = 0.0F;
/*  326 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f() {
/*  336 */     this.m00 = 0.0F;
/*  337 */     this.m01 = 0.0F;
/*  338 */     this.m02 = 0.0F;
/*  339 */     this.m03 = 0.0F;
/*      */     
/*  341 */     this.m10 = 0.0F;
/*  342 */     this.m11 = 0.0F;
/*  343 */     this.m12 = 0.0F;
/*  344 */     this.m13 = 0.0F;
/*      */     
/*  346 */     this.m20 = 0.0F;
/*  347 */     this.m21 = 0.0F;
/*  348 */     this.m22 = 0.0F;
/*  349 */     this.m23 = 0.0F;
/*      */     
/*  351 */     this.m30 = 0.0F;
/*  352 */     this.m31 = 0.0F;
/*  353 */     this.m32 = 0.0F;
/*  354 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  364 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
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
/*  376 */     this.m00 = 1.0F;
/*  377 */     this.m01 = 0.0F;
/*  378 */     this.m02 = 0.0F;
/*  379 */     this.m03 = 0.0F;
/*      */     
/*  381 */     this.m10 = 0.0F;
/*  382 */     this.m11 = 1.0F;
/*  383 */     this.m12 = 0.0F;
/*  384 */     this.m13 = 0.0F;
/*      */     
/*  386 */     this.m20 = 0.0F;
/*  387 */     this.m21 = 0.0F;
/*  388 */     this.m22 = 1.0F;
/*  389 */     this.m23 = 0.0F;
/*      */     
/*  391 */     this.m30 = 0.0F;
/*  392 */     this.m31 = 0.0F;
/*  393 */     this.m32 = 0.0F;
/*  394 */     this.m33 = 1.0F;
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
/*  405 */     switch (row) {
/*      */       
/*      */       case 0:
/*  408 */         switch (column) {
/*      */           
/*      */           case 0:
/*  411 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  414 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  417 */             this.m02 = value;
/*      */             return;
/*      */           case 3:
/*  420 */             this.m03 = value;
/*      */             return;
/*      */         } 
/*  423 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  428 */         switch (column) {
/*      */           
/*      */           case 0:
/*  431 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  434 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  437 */             this.m12 = value;
/*      */             return;
/*      */           case 3:
/*  440 */             this.m13 = value;
/*      */             return;
/*      */         } 
/*  443 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  448 */         switch (column) {
/*      */           
/*      */           case 0:
/*  451 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  454 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  457 */             this.m22 = value;
/*      */             return;
/*      */           case 3:
/*  460 */             this.m23 = value;
/*      */             return;
/*      */         } 
/*  463 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  468 */         switch (column) {
/*      */           
/*      */           case 0:
/*  471 */             this.m30 = value;
/*      */             return;
/*      */           case 1:
/*  474 */             this.m31 = value;
/*      */             return;
/*      */           case 2:
/*  477 */             this.m32 = value;
/*      */             return;
/*      */           case 3:
/*  480 */             this.m33 = value;
/*      */             return;
/*      */         } 
/*  483 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  488 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
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
/*  500 */     switch (row) {
/*      */       
/*      */       case 0:
/*  503 */         switch (column) {
/*      */           
/*      */           case 0:
/*  506 */             return this.m00;
/*      */           case 1:
/*  508 */             return this.m01;
/*      */           case 2:
/*  510 */             return this.m02;
/*      */           case 3:
/*  512 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  518 */         switch (column) {
/*      */           
/*      */           case 0:
/*  521 */             return this.m10;
/*      */           case 1:
/*  523 */             return this.m11;
/*      */           case 2:
/*  525 */             return this.m12;
/*      */           case 3:
/*  527 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  534 */         switch (column) {
/*      */           
/*      */           case 0:
/*  537 */             return this.m20;
/*      */           case 1:
/*  539 */             return this.m21;
/*      */           case 2:
/*  541 */             return this.m22;
/*      */           case 3:
/*  543 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  550 */         switch (column) {
/*      */           
/*      */           case 0:
/*  553 */             return this.m30;
/*      */           case 1:
/*  555 */             return this.m31;
/*      */           case 2:
/*  557 */             return this.m32;
/*      */           case 3:
/*  559 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  568 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector4f v) {
/*  577 */     if (row == 0) {
/*  578 */       v.x = this.m00;
/*  579 */       v.y = this.m01;
/*  580 */       v.z = this.m02;
/*  581 */       v.w = this.m03;
/*  582 */     } else if (row == 1) {
/*  583 */       v.x = this.m10;
/*  584 */       v.y = this.m11;
/*  585 */       v.z = this.m12;
/*  586 */       v.w = this.m13;
/*  587 */     } else if (row == 2) {
/*  588 */       v.x = this.m20;
/*  589 */       v.y = this.m21;
/*  590 */       v.z = this.m22;
/*  591 */       v.w = this.m23;
/*  592 */     } else if (row == 3) {
/*  593 */       v.x = this.m30;
/*  594 */       v.y = this.m31;
/*  595 */       v.z = this.m32;
/*  596 */       v.w = this.m33;
/*      */     } else {
/*  598 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*  609 */     if (row == 0) {
/*  610 */       v[0] = this.m00;
/*  611 */       v[1] = this.m01;
/*  612 */       v[2] = this.m02;
/*  613 */       v[3] = this.m03;
/*  614 */     } else if (row == 1) {
/*  615 */       v[0] = this.m10;
/*  616 */       v[1] = this.m11;
/*  617 */       v[2] = this.m12;
/*  618 */       v[3] = this.m13;
/*  619 */     } else if (row == 2) {
/*  620 */       v[0] = this.m20;
/*  621 */       v[1] = this.m21;
/*  622 */       v[2] = this.m22;
/*  623 */       v[3] = this.m23;
/*  624 */     } else if (row == 3) {
/*  625 */       v[0] = this.m30;
/*  626 */       v[1] = this.m31;
/*  627 */       v[2] = this.m32;
/*  628 */       v[3] = this.m33;
/*      */     } else {
/*  630 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*  642 */     if (column == 0) {
/*  643 */       v.x = this.m00;
/*  644 */       v.y = this.m10;
/*  645 */       v.z = this.m20;
/*  646 */       v.w = this.m30;
/*  647 */     } else if (column == 1) {
/*  648 */       v.x = this.m01;
/*  649 */       v.y = this.m11;
/*  650 */       v.z = this.m21;
/*  651 */       v.w = this.m31;
/*  652 */     } else if (column == 2) {
/*  653 */       v.x = this.m02;
/*  654 */       v.y = this.m12;
/*  655 */       v.z = this.m22;
/*  656 */       v.w = this.m32;
/*  657 */     } else if (column == 3) {
/*  658 */       v.x = this.m03;
/*  659 */       v.y = this.m13;
/*  660 */       v.z = this.m23;
/*  661 */       v.w = this.m33;
/*      */     } else {
/*  663 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*  675 */     if (column == 0) {
/*  676 */       v[0] = this.m00;
/*  677 */       v[1] = this.m10;
/*  678 */       v[2] = this.m20;
/*  679 */       v[3] = this.m30;
/*  680 */     } else if (column == 1) {
/*  681 */       v[0] = this.m01;
/*  682 */       v[1] = this.m11;
/*  683 */       v[2] = this.m21;
/*  684 */       v[3] = this.m31;
/*  685 */     } else if (column == 2) {
/*  686 */       v[0] = this.m02;
/*  687 */       v[1] = this.m12;
/*  688 */       v[2] = this.m22;
/*  689 */       v[3] = this.m32;
/*  690 */     } else if (column == 3) {
/*  691 */       v[0] = this.m03;
/*  692 */       v[1] = this.m13;
/*  693 */       v[2] = this.m23;
/*  694 */       v[3] = this.m33;
/*      */     } else {
/*  696 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*  710 */     double[] tmp_rot = new double[9];
/*  711 */     double[] tmp_scale = new double[3];
/*  712 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  714 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  715 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  716 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  718 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  719 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  720 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  722 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  723 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  724 */     this.m22 = (float)(tmp_rot[8] * scale);
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
/*  736 */     double[] tmp_rot = new double[9];
/*  737 */     double[] tmp_scale = new double[3];
/*      */     
/*  739 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  741 */     m1.m00 = tmp_rot[0];
/*  742 */     m1.m01 = tmp_rot[1];
/*  743 */     m1.m02 = tmp_rot[2];
/*      */     
/*  745 */     m1.m10 = tmp_rot[3];
/*  746 */     m1.m11 = tmp_rot[4];
/*  747 */     m1.m12 = tmp_rot[5];
/*      */     
/*  749 */     m1.m20 = tmp_rot[6];
/*  750 */     m1.m21 = tmp_rot[7];
/*  751 */     m1.m22 = tmp_rot[8];
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
/*  763 */     double[] tmp_rot = new double[9];
/*  764 */     double[] tmp_scale = new double[3];
/*      */     
/*  766 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  768 */     m1.m00 = (float)tmp_rot[0];
/*  769 */     m1.m01 = (float)tmp_rot[1];
/*  770 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  772 */     m1.m10 = (float)tmp_rot[3];
/*  773 */     m1.m11 = (float)tmp_rot[4];
/*  774 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  776 */     m1.m20 = (float)tmp_rot[6];
/*  777 */     m1.m21 = (float)tmp_rot[7];
/*  778 */     m1.m22 = (float)tmp_rot[8];
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
/*  793 */     double[] tmp_rot = new double[9];
/*  794 */     double[] tmp_scale = new double[3];
/*      */     
/*  796 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  798 */     m1.m00 = (float)tmp_rot[0];
/*  799 */     m1.m01 = (float)tmp_rot[1];
/*  800 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  802 */     m1.m10 = (float)tmp_rot[3];
/*  803 */     m1.m11 = (float)tmp_rot[4];
/*  804 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  806 */     m1.m20 = (float)tmp_rot[6];
/*  807 */     m1.m21 = (float)tmp_rot[7];
/*  808 */     m1.m22 = (float)tmp_rot[8];
/*      */     
/*  810 */     t1.x = this.m03;
/*  811 */     t1.y = this.m13;
/*  812 */     t1.z = this.m23;
/*      */     
/*  814 */     return (float)Matrix3d.max3(tmp_scale);
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
/*  826 */     double[] tmp_rot = new double[9];
/*  827 */     double[] tmp_scale = new double[3];
/*  828 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  832 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  833 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  834 */       q1.w = (float)Math.sqrt(ww);
/*  835 */       ww = 0.25D / q1.w;
/*  836 */       q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
/*  837 */       q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
/*  838 */       q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  842 */     q1.w = 0.0F;
/*  843 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  844 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  845 */       q1.x = (float)Math.sqrt(ww);
/*  846 */       ww = 0.5D / q1.x;
/*  847 */       q1.y = (float)(tmp_rot[3] * ww);
/*  848 */       q1.z = (float)(tmp_rot[6] * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  852 */     q1.x = 0.0F;
/*  853 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  854 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  855 */       q1.y = (float)Math.sqrt(ww);
/*  856 */       q1.z = (float)(tmp_rot[7] / 2.0D * q1.y);
/*      */       
/*      */       return;
/*      */     } 
/*  860 */     q1.y = 0.0F;
/*  861 */     q1.z = 1.0F;
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
/*  872 */     trans.x = this.m03;
/*  873 */     trans.y = this.m13;
/*  874 */     trans.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f m1) {
/*  884 */     m1.m00 = this.m00; m1.m01 = this.m01; m1.m02 = this.m02;
/*  885 */     m1.m10 = this.m10; m1.m11 = this.m11; m1.m12 = this.m12;
/*  886 */     m1.m20 = this.m20; m1.m21 = this.m21; m1.m22 = this.m22;
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
/*  898 */     double[] tmp_rot = new double[9];
/*  899 */     double[] tmp_scale = new double[3];
/*      */     
/*  901 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  903 */     return (float)Matrix3d.max3(tmp_scale);
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
/*  915 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02;
/*  916 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12;
/*  917 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22;
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
/*  931 */     switch (row) {
/*      */       case 0:
/*  933 */         this.m00 = x;
/*  934 */         this.m01 = y;
/*  935 */         this.m02 = z;
/*  936 */         this.m03 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  940 */         this.m10 = x;
/*  941 */         this.m11 = y;
/*  942 */         this.m12 = z;
/*  943 */         this.m13 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  947 */         this.m20 = x;
/*  948 */         this.m21 = y;
/*  949 */         this.m22 = z;
/*  950 */         this.m23 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  954 */         this.m30 = x;
/*  955 */         this.m31 = y;
/*  956 */         this.m32 = z;
/*  957 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/*  961 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/*  972 */     switch (row) {
/*      */       case 0:
/*  974 */         this.m00 = v.x;
/*  975 */         this.m01 = v.y;
/*  976 */         this.m02 = v.z;
/*  977 */         this.m03 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  981 */         this.m10 = v.x;
/*  982 */         this.m11 = v.y;
/*  983 */         this.m12 = v.z;
/*  984 */         this.m13 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  988 */         this.m20 = v.x;
/*  989 */         this.m21 = v.y;
/*  990 */         this.m22 = v.z;
/*  991 */         this.m23 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  995 */         this.m30 = v.x;
/*  996 */         this.m31 = v.y;
/*  997 */         this.m32 = v.z;
/*  998 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1002 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/* 1014 */     switch (row) {
/*      */       case 0:
/* 1016 */         this.m00 = v[0];
/* 1017 */         this.m01 = v[1];
/* 1018 */         this.m02 = v[2];
/* 1019 */         this.m03 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1023 */         this.m10 = v[0];
/* 1024 */         this.m11 = v[1];
/* 1025 */         this.m12 = v[2];
/* 1026 */         this.m13 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1030 */         this.m20 = v[0];
/* 1031 */         this.m21 = v[1];
/* 1032 */         this.m22 = v[2];
/* 1033 */         this.m23 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1037 */         this.m30 = v[0];
/* 1038 */         this.m31 = v[1];
/* 1039 */         this.m32 = v[2];
/* 1040 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1044 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/* 1058 */     switch (column) {
/*      */       case 0:
/* 1060 */         this.m00 = x;
/* 1061 */         this.m10 = y;
/* 1062 */         this.m20 = z;
/* 1063 */         this.m30 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1067 */         this.m01 = x;
/* 1068 */         this.m11 = y;
/* 1069 */         this.m21 = z;
/* 1070 */         this.m31 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1074 */         this.m02 = x;
/* 1075 */         this.m12 = y;
/* 1076 */         this.m22 = z;
/* 1077 */         this.m32 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1081 */         this.m03 = x;
/* 1082 */         this.m13 = y;
/* 1083 */         this.m23 = z;
/* 1084 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1088 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
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
/* 1099 */     switch (column) {
/*      */       case 0:
/* 1101 */         this.m00 = v.x;
/* 1102 */         this.m10 = v.y;
/* 1103 */         this.m20 = v.z;
/* 1104 */         this.m30 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1108 */         this.m01 = v.x;
/* 1109 */         this.m11 = v.y;
/* 1110 */         this.m21 = v.z;
/* 1111 */         this.m31 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1115 */         this.m02 = v.x;
/* 1116 */         this.m12 = v.y;
/* 1117 */         this.m22 = v.z;
/* 1118 */         this.m32 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1122 */         this.m03 = v.x;
/* 1123 */         this.m13 = v.y;
/* 1124 */         this.m23 = v.z;
/* 1125 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1129 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
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
/* 1140 */     switch (column) {
/*      */       case 0:
/* 1142 */         this.m00 = v[0];
/* 1143 */         this.m10 = v[1];
/* 1144 */         this.m20 = v[2];
/* 1145 */         this.m30 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1149 */         this.m01 = v[0];
/* 1150 */         this.m11 = v[1];
/* 1151 */         this.m21 = v[2];
/* 1152 */         this.m31 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1156 */         this.m02 = v[0];
/* 1157 */         this.m12 = v[1];
/* 1158 */         this.m22 = v[2];
/* 1159 */         this.m32 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1163 */         this.m03 = v[0];
/* 1164 */         this.m13 = v[1];
/* 1165 */         this.m23 = v[2];
/* 1166 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1170 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/* 1180 */     this.m00 += scalar;
/* 1181 */     this.m01 += scalar;
/* 1182 */     this.m02 += scalar;
/* 1183 */     this.m03 += scalar;
/* 1184 */     this.m10 += scalar;
/* 1185 */     this.m11 += scalar;
/* 1186 */     this.m12 += scalar;
/* 1187 */     this.m13 += scalar;
/* 1188 */     this.m20 += scalar;
/* 1189 */     this.m21 += scalar;
/* 1190 */     this.m22 += scalar;
/* 1191 */     this.m23 += scalar;
/* 1192 */     this.m30 += scalar;
/* 1193 */     this.m31 += scalar;
/* 1194 */     this.m32 += scalar;
/* 1195 */     this.m33 += scalar;
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
/* 1206 */     m1.m00 += scalar;
/* 1207 */     m1.m01 += scalar;
/* 1208 */     m1.m02 += scalar;
/* 1209 */     m1.m03 += scalar;
/* 1210 */     m1.m10 += scalar;
/* 1211 */     m1.m11 += scalar;
/* 1212 */     m1.m12 += scalar;
/* 1213 */     m1.m13 += scalar;
/* 1214 */     m1.m20 += scalar;
/* 1215 */     m1.m21 += scalar;
/* 1216 */     m1.m22 += scalar;
/* 1217 */     m1.m23 += scalar;
/* 1218 */     m1.m30 += scalar;
/* 1219 */     m1.m31 += scalar;
/* 1220 */     m1.m32 += scalar;
/* 1221 */     m1.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1, Matrix4f m2) {
/* 1231 */     m1.m00 += m2.m00;
/* 1232 */     m1.m01 += m2.m01;
/* 1233 */     m1.m02 += m2.m02;
/* 1234 */     m1.m03 += m2.m03;
/*      */     
/* 1236 */     m1.m10 += m2.m10;
/* 1237 */     m1.m11 += m2.m11;
/* 1238 */     m1.m12 += m2.m12;
/* 1239 */     m1.m13 += m2.m13;
/*      */     
/* 1241 */     m1.m20 += m2.m20;
/* 1242 */     m1.m21 += m2.m21;
/* 1243 */     m1.m22 += m2.m22;
/* 1244 */     m1.m23 += m2.m23;
/*      */     
/* 1246 */     m1.m30 += m2.m30;
/* 1247 */     m1.m31 += m2.m31;
/* 1248 */     m1.m32 += m2.m32;
/* 1249 */     m1.m33 += m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1) {
/* 1259 */     this.m00 += m1.m00;
/* 1260 */     this.m01 += m1.m01;
/* 1261 */     this.m02 += m1.m02;
/* 1262 */     this.m03 += m1.m03;
/*      */     
/* 1264 */     this.m10 += m1.m10;
/* 1265 */     this.m11 += m1.m11;
/* 1266 */     this.m12 += m1.m12;
/* 1267 */     this.m13 += m1.m13;
/*      */     
/* 1269 */     this.m20 += m1.m20;
/* 1270 */     this.m21 += m1.m21;
/* 1271 */     this.m22 += m1.m22;
/* 1272 */     this.m23 += m1.m23;
/*      */     
/* 1274 */     this.m30 += m1.m30;
/* 1275 */     this.m31 += m1.m31;
/* 1276 */     this.m32 += m1.m32;
/* 1277 */     this.m33 += m1.m33;
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
/* 1289 */     m1.m00 -= m2.m00;
/* 1290 */     m1.m01 -= m2.m01;
/* 1291 */     m1.m02 -= m2.m02;
/* 1292 */     m1.m03 -= m2.m03;
/*      */     
/* 1294 */     m1.m10 -= m2.m10;
/* 1295 */     m1.m11 -= m2.m11;
/* 1296 */     m1.m12 -= m2.m12;
/* 1297 */     m1.m13 -= m2.m13;
/*      */     
/* 1299 */     m1.m20 -= m2.m20;
/* 1300 */     m1.m21 -= m2.m21;
/* 1301 */     m1.m22 -= m2.m22;
/* 1302 */     m1.m23 -= m2.m23;
/*      */     
/* 1304 */     m1.m30 -= m2.m30;
/* 1305 */     m1.m31 -= m2.m31;
/* 1306 */     m1.m32 -= m2.m32;
/* 1307 */     m1.m33 -= m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4f m1) {
/* 1317 */     this.m00 -= m1.m00;
/* 1318 */     this.m01 -= m1.m01;
/* 1319 */     this.m02 -= m1.m02;
/* 1320 */     this.m03 -= m1.m03;
/*      */     
/* 1322 */     this.m10 -= m1.m10;
/* 1323 */     this.m11 -= m1.m11;
/* 1324 */     this.m12 -= m1.m12;
/* 1325 */     this.m13 -= m1.m13;
/*      */     
/* 1327 */     this.m20 -= m1.m20;
/* 1328 */     this.m21 -= m1.m21;
/* 1329 */     this.m22 -= m1.m22;
/* 1330 */     this.m23 -= m1.m23;
/*      */     
/* 1332 */     this.m30 -= m1.m30;
/* 1333 */     this.m31 -= m1.m31;
/* 1334 */     this.m32 -= m1.m32;
/* 1335 */     this.m33 -= m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1345 */     float temp = this.m10;
/* 1346 */     this.m10 = this.m01;
/* 1347 */     this.m01 = temp;
/*      */     
/* 1349 */     temp = this.m20;
/* 1350 */     this.m20 = this.m02;
/* 1351 */     this.m02 = temp;
/*      */     
/* 1353 */     temp = this.m30;
/* 1354 */     this.m30 = this.m03;
/* 1355 */     this.m03 = temp;
/*      */     
/* 1357 */     temp = this.m21;
/* 1358 */     this.m21 = this.m12;
/* 1359 */     this.m12 = temp;
/*      */     
/* 1361 */     temp = this.m31;
/* 1362 */     this.m31 = this.m13;
/* 1363 */     this.m13 = temp;
/*      */     
/* 1365 */     temp = this.m32;
/* 1366 */     this.m32 = this.m23;
/* 1367 */     this.m23 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4f m1) {
/* 1376 */     if (this != m1) {
/* 1377 */       this.m00 = m1.m00;
/* 1378 */       this.m01 = m1.m10;
/* 1379 */       this.m02 = m1.m20;
/* 1380 */       this.m03 = m1.m30;
/*      */       
/* 1382 */       this.m10 = m1.m01;
/* 1383 */       this.m11 = m1.m11;
/* 1384 */       this.m12 = m1.m21;
/* 1385 */       this.m13 = m1.m31;
/*      */       
/* 1387 */       this.m20 = m1.m02;
/* 1388 */       this.m21 = m1.m12;
/* 1389 */       this.m22 = m1.m22;
/* 1390 */       this.m23 = m1.m32;
/*      */       
/* 1392 */       this.m30 = m1.m03;
/* 1393 */       this.m31 = m1.m13;
/* 1394 */       this.m32 = m1.m23;
/* 1395 */       this.m33 = m1.m33;
/*      */     } else {
/* 1397 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/* 1407 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/* 1408 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1409 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1411 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1412 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/* 1413 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1415 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1416 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1417 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */     
/* 1419 */     this.m03 = 0.0F;
/* 1420 */     this.m13 = 0.0F;
/* 1421 */     this.m23 = 0.0F;
/*      */     
/* 1423 */     this.m30 = 0.0F;
/* 1424 */     this.m31 = 0.0F;
/* 1425 */     this.m32 = 0.0F;
/* 1426 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/* 1436 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 1437 */     if (mag < 1.0E-8D) {
/* 1438 */       this.m00 = 1.0F;
/* 1439 */       this.m01 = 0.0F;
/* 1440 */       this.m02 = 0.0F;
/*      */       
/* 1442 */       this.m10 = 0.0F;
/* 1443 */       this.m11 = 1.0F;
/* 1444 */       this.m12 = 0.0F;
/*      */       
/* 1446 */       this.m20 = 0.0F;
/* 1447 */       this.m21 = 0.0F;
/* 1448 */       this.m22 = 1.0F;
/*      */     } else {
/* 1450 */       mag = 1.0F / mag;
/* 1451 */       float ax = a1.x * mag;
/* 1452 */       float ay = a1.y * mag;
/* 1453 */       float az = a1.z * mag;
/*      */       
/* 1455 */       float sinTheta = MathHelper.sin(a1.angle);
/* 1456 */       float cosTheta = MathHelper.cos(a1.angle);
/* 1457 */       float t = 1.0F - cosTheta;
/*      */       
/* 1459 */       float xz = ax * az;
/* 1460 */       float xy = ax * ay;
/* 1461 */       float yz = ay * az;
/*      */       
/* 1463 */       this.m00 = t * ax * ax + cosTheta;
/* 1464 */       this.m01 = t * xy - sinTheta * az;
/* 1465 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1467 */       this.m10 = t * xy + sinTheta * az;
/* 1468 */       this.m11 = t * ay * ay + cosTheta;
/* 1469 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1471 */       this.m20 = t * xz - sinTheta * ay;
/* 1472 */       this.m21 = t * yz + sinTheta * ax;
/* 1473 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/* 1475 */     this.m03 = 0.0F;
/* 1476 */     this.m13 = 0.0F;
/* 1477 */     this.m23 = 0.0F;
/*      */     
/* 1479 */     this.m30 = 0.0F;
/* 1480 */     this.m31 = 0.0F;
/* 1481 */     this.m32 = 0.0F;
/* 1482 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/* 1492 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1493 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1494 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1496 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1497 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1498 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1500 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1501 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1502 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1504 */     this.m03 = 0.0F;
/* 1505 */     this.m13 = 0.0F;
/* 1506 */     this.m23 = 0.0F;
/*      */     
/* 1508 */     this.m30 = 0.0F;
/* 1509 */     this.m31 = 0.0F;
/* 1510 */     this.m32 = 0.0F;
/* 1511 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/* 1521 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/* 1523 */     if (mag < 1.0E-8D) {
/* 1524 */       this.m00 = 1.0F;
/* 1525 */       this.m01 = 0.0F;
/* 1526 */       this.m02 = 0.0F;
/*      */       
/* 1528 */       this.m10 = 0.0F;
/* 1529 */       this.m11 = 1.0F;
/* 1530 */       this.m12 = 0.0F;
/*      */       
/* 1532 */       this.m20 = 0.0F;
/* 1533 */       this.m21 = 0.0F;
/* 1534 */       this.m22 = 1.0F;
/*      */     } else {
/* 1536 */       mag = 1.0D / mag;
/* 1537 */       double ax = a1.x * mag;
/* 1538 */       double ay = a1.y * mag;
/* 1539 */       double az = a1.z * mag;
/*      */       
/* 1541 */       float sinTheta = (float)Math.sin(a1.angle);
/* 1542 */       float cosTheta = (float)Math.cos(a1.angle);
/* 1543 */       float t = 1.0F - cosTheta;
/*      */       
/* 1545 */       float xz = (float)(ax * az);
/* 1546 */       float xy = (float)(ax * ay);
/* 1547 */       float yz = (float)(ay * az);
/*      */       
/* 1549 */       this.m00 = t * (float)(ax * ax) + cosTheta;
/* 1550 */       this.m01 = t * xy - sinTheta * (float)az;
/* 1551 */       this.m02 = t * xz + sinTheta * (float)ay;
/*      */       
/* 1553 */       this.m10 = t * xy + sinTheta * (float)az;
/* 1554 */       this.m11 = t * (float)(ay * ay) + cosTheta;
/* 1555 */       this.m12 = t * yz - sinTheta * (float)ax;
/*      */       
/* 1557 */       this.m20 = t * xz - sinTheta * (float)ay;
/* 1558 */       this.m21 = t * yz + sinTheta * (float)ax;
/* 1559 */       this.m22 = t * (float)(az * az) + cosTheta;
/*      */     } 
/* 1561 */     this.m03 = 0.0F;
/* 1562 */     this.m13 = 0.0F;
/* 1563 */     this.m23 = 0.0F;
/*      */     
/* 1565 */     this.m30 = 0.0F;
/* 1566 */     this.m31 = 0.0F;
/* 1567 */     this.m32 = 0.0F;
/* 1568 */     this.m33 = 1.0F;
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
/* 1580 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/* 1581 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1582 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1584 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1585 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/* 1586 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1588 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1589 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1590 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/* 1592 */     this.m03 = (float)t1.x;
/* 1593 */     this.m13 = (float)t1.y;
/* 1594 */     this.m23 = (float)t1.z;
/*      */     
/* 1596 */     this.m30 = 0.0F;
/* 1597 */     this.m31 = 0.0F;
/* 1598 */     this.m32 = 0.0F;
/* 1599 */     this.m33 = 1.0F;
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
/* 1611 */     this.m00 = s * (1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z);
/* 1612 */     this.m10 = s * 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1613 */     this.m20 = s * 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1615 */     this.m01 = s * 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1616 */     this.m11 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z);
/* 1617 */     this.m21 = s * 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1619 */     this.m02 = s * 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1620 */     this.m12 = s * 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1621 */     this.m22 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y);
/*      */     
/* 1623 */     this.m03 = t1.x;
/* 1624 */     this.m13 = t1.y;
/* 1625 */     this.m23 = t1.z;
/*      */     
/* 1627 */     this.m30 = 0.0F;
/* 1628 */     this.m31 = 0.0F;
/* 1629 */     this.m32 = 0.0F;
/* 1630 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/* 1640 */     this.m00 = (float)m1.m00;
/* 1641 */     this.m01 = (float)m1.m01;
/* 1642 */     this.m02 = (float)m1.m02;
/* 1643 */     this.m03 = (float)m1.m03;
/*      */     
/* 1645 */     this.m10 = (float)m1.m10;
/* 1646 */     this.m11 = (float)m1.m11;
/* 1647 */     this.m12 = (float)m1.m12;
/* 1648 */     this.m13 = (float)m1.m13;
/*      */     
/* 1650 */     this.m20 = (float)m1.m20;
/* 1651 */     this.m21 = (float)m1.m21;
/* 1652 */     this.m22 = (float)m1.m22;
/* 1653 */     this.m23 = (float)m1.m23;
/*      */     
/* 1655 */     this.m30 = (float)m1.m30;
/* 1656 */     this.m31 = (float)m1.m31;
/* 1657 */     this.m32 = (float)m1.m32;
/* 1658 */     this.m33 = (float)m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/* 1668 */     this.m00 = m1.m00;
/* 1669 */     this.m01 = m1.m01;
/* 1670 */     this.m02 = m1.m02;
/* 1671 */     this.m03 = m1.m03;
/*      */     
/* 1673 */     this.m10 = m1.m10;
/* 1674 */     this.m11 = m1.m11;
/* 1675 */     this.m12 = m1.m12;
/* 1676 */     this.m13 = m1.m13;
/*      */     
/* 1678 */     this.m20 = m1.m20;
/* 1679 */     this.m21 = m1.m21;
/* 1680 */     this.m22 = m1.m22;
/* 1681 */     this.m23 = m1.m23;
/*      */     
/* 1683 */     this.m30 = m1.m30;
/* 1684 */     this.m31 = m1.m31;
/* 1685 */     this.m32 = m1.m32;
/* 1686 */     this.m33 = m1.m33;
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
/* 1697 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1705 */     invertGeneral(this);
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
/* 1717 */     double[] temp = new double[16];
/* 1718 */     double[] result = new double[16];
/* 1719 */     int[] row_perm = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1726 */     temp[0] = m1.m00;
/* 1727 */     temp[1] = m1.m01;
/* 1728 */     temp[2] = m1.m02;
/* 1729 */     temp[3] = m1.m03;
/*      */     
/* 1731 */     temp[4] = m1.m10;
/* 1732 */     temp[5] = m1.m11;
/* 1733 */     temp[6] = m1.m12;
/* 1734 */     temp[7] = m1.m13;
/*      */     
/* 1736 */     temp[8] = m1.m20;
/* 1737 */     temp[9] = m1.m21;
/* 1738 */     temp[10] = m1.m22;
/* 1739 */     temp[11] = m1.m23;
/*      */     
/* 1741 */     temp[12] = m1.m30;
/* 1742 */     temp[13] = m1.m31;
/* 1743 */     temp[14] = m1.m32;
/* 1744 */     temp[15] = m1.m33;
/*      */ 
/*      */     
/* 1747 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1749 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4f12"));
/*      */     }
/*      */ 
/*      */     
/* 1753 */     for (int i = 0; i < 16; ) { result[i] = 0.0D; i++; }
/* 1754 */      result[0] = 1.0D; result[5] = 1.0D; result[10] = 1.0D; result[15] = 1.0D;
/* 1755 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1757 */     this.m00 = (float)result[0];
/* 1758 */     this.m01 = (float)result[1];
/* 1759 */     this.m02 = (float)result[2];
/* 1760 */     this.m03 = (float)result[3];
/*      */     
/* 1762 */     this.m10 = (float)result[4];
/* 1763 */     this.m11 = (float)result[5];
/* 1764 */     this.m12 = (float)result[6];
/* 1765 */     this.m13 = (float)result[7];
/*      */     
/* 1767 */     this.m20 = (float)result[8];
/* 1768 */     this.m21 = (float)result[9];
/* 1769 */     this.m22 = (float)result[10];
/* 1770 */     this.m23 = (float)result[11];
/*      */     
/* 1772 */     this.m30 = (float)result[12];
/* 1773 */     this.m31 = (float)result[13];
/* 1774 */     this.m32 = (float)result[14];
/* 1775 */     this.m33 = (float)result[15];
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
/* 1802 */     double[] row_scale = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1810 */     int ptr = 0;
/* 1811 */     int rs = 0;
/*      */ 
/*      */     
/* 1814 */     int i = 4;
/* 1815 */     while (i-- != 0) {
/* 1816 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1819 */       int k = 4;
/* 1820 */       while (k-- != 0) {
/* 1821 */         double temp = matrix0[ptr++];
/* 1822 */         temp = Math.abs(temp);
/* 1823 */         if (temp > big) {
/* 1824 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1829 */       if (big == 0.0D) {
/* 1830 */         return false;
/*      */       }
/* 1832 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1840 */     int mtx = 0;
/*      */ 
/*      */     
/* 1843 */     for (int j = 0; j < 4; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1849 */       for (k = 0; k < j; k++) {
/* 1850 */         int target = mtx + 4 * k + j;
/* 1851 */         double sum = matrix0[target];
/* 1852 */         int m = k;
/* 1853 */         int p1 = mtx + 4 * k;
/* 1854 */         int p2 = mtx + j;
/* 1855 */         while (m-- != 0) {
/* 1856 */           sum -= matrix0[p1] * matrix0[p2];
/* 1857 */           p1++;
/* 1858 */           p2 += 4;
/*      */         } 
/* 1860 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1865 */       double big = 0.0D;
/* 1866 */       int imax = -1;
/* 1867 */       for (k = j; k < 4; k++) {
/* 1868 */         int target = mtx + 4 * k + j;
/* 1869 */         double sum = matrix0[target];
/* 1870 */         int m = j;
/* 1871 */         int p1 = mtx + 4 * k;
/* 1872 */         int p2 = mtx + j;
/* 1873 */         while (m-- != 0) {
/* 1874 */           sum -= matrix0[p1] * matrix0[p2];
/* 1875 */           p1++;
/* 1876 */           p2 += 4;
/*      */         } 
/* 1878 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1881 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1882 */           big = temp;
/* 1883 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1887 */       if (imax < 0) {
/* 1888 */         throw new RuntimeException(VecMathI18N.getString("Matrix4f13"));
/*      */       }
/*      */ 
/*      */       
/* 1892 */       if (j != imax) {
/*      */         
/* 1894 */         int m = 4;
/* 1895 */         int p1 = mtx + 4 * imax;
/* 1896 */         int p2 = mtx + 4 * j;
/* 1897 */         while (m-- != 0) {
/* 1898 */           double temp = matrix0[p1];
/* 1899 */           matrix0[p1++] = matrix0[p2];
/* 1900 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1904 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1908 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1911 */       if (matrix0[mtx + 4 * j + j] == 0.0D) {
/* 1912 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1916 */       if (j != 3) {
/* 1917 */         double temp = 1.0D / matrix0[mtx + 4 * j + j];
/* 1918 */         int target = mtx + 4 * (j + 1) + j;
/* 1919 */         k = 3 - j;
/* 1920 */         while (k-- != 0) {
/* 1921 */           matrix0[target] = matrix0[target] * temp;
/* 1922 */           target += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1928 */     return true;
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
/* 1958 */     int rp = 0;
/*      */ 
/*      */     
/* 1961 */     for (int k = 0; k < 4; k++) {
/*      */       
/* 1963 */       int cv = k;
/* 1964 */       int ii = -1;
/*      */ 
/*      */       
/* 1967 */       for (int i = 0; i < 4; i++) {
/*      */ 
/*      */         
/* 1970 */         int ip = row_perm[rp + i];
/* 1971 */         double sum = matrix2[cv + 4 * ip];
/* 1972 */         matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
/* 1973 */         if (ii >= 0) {
/*      */           
/* 1975 */           int m = i << 2;
/* 1976 */           for (int j = ii; j <= i - 1; j++) {
/* 1977 */             sum -= matrix1[m + j] * matrix2[cv + 4 * j];
/*      */           }
/*      */         }
/* 1980 */         else if (sum != 0.0D) {
/* 1981 */           ii = i;
/*      */         } 
/* 1983 */         matrix2[cv + 4 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1988 */       int rv = 12;
/* 1989 */       matrix2[cv + 12] = matrix2[cv + 12] / matrix1[rv + 3];
/*      */       
/* 1991 */       rv -= 4;
/* 1992 */       matrix2[cv + 8] = (matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
/*      */ 
/*      */       
/* 1995 */       rv -= 4;
/* 1996 */       matrix2[cv + 4] = (matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
/*      */ 
/*      */ 
/*      */       
/* 2000 */       rv -= 4;
/* 2001 */       matrix2[cv] = (matrix2[cv] - matrix1[rv + 1] * matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv];
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
/* 2018 */     float det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/*      */     
/* 2020 */     det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/*      */     
/* 2022 */     det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/*      */     
/* 2024 */     det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */ 
/*      */     
/* 2027 */     return det;
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
/* 2039 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02; this.m03 = 0.0F;
/* 2040 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12; this.m13 = 0.0F;
/* 2041 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22; this.m23 = 0.0F;
/* 2042 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
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
/* 2054 */     this.m00 = (float)m1.m00; this.m01 = (float)m1.m01; this.m02 = (float)m1.m02; this.m03 = 0.0F;
/* 2055 */     this.m10 = (float)m1.m10; this.m11 = (float)m1.m11; this.m12 = (float)m1.m12; this.m13 = 0.0F;
/* 2056 */     this.m20 = (float)m1.m20; this.m21 = (float)m1.m21; this.m22 = (float)m1.m22; this.m23 = 0.0F;
/* 2057 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 2067 */     this.m00 = scale;
/* 2068 */     this.m01 = 0.0F;
/* 2069 */     this.m02 = 0.0F;
/* 2070 */     this.m03 = 0.0F;
/*      */     
/* 2072 */     this.m10 = 0.0F;
/* 2073 */     this.m11 = scale;
/* 2074 */     this.m12 = 0.0F;
/* 2075 */     this.m13 = 0.0F;
/*      */     
/* 2077 */     this.m20 = 0.0F;
/* 2078 */     this.m21 = 0.0F;
/* 2079 */     this.m22 = scale;
/* 2080 */     this.m23 = 0.0F;
/*      */     
/* 2082 */     this.m30 = 0.0F;
/* 2083 */     this.m31 = 0.0F;
/* 2084 */     this.m32 = 0.0F;
/* 2085 */     this.m33 = 1.0F;
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
/* 2096 */     this.m00 = m[0];
/* 2097 */     this.m01 = m[1];
/* 2098 */     this.m02 = m[2];
/* 2099 */     this.m03 = m[3];
/* 2100 */     this.m10 = m[4];
/* 2101 */     this.m11 = m[5];
/* 2102 */     this.m12 = m[6];
/* 2103 */     this.m13 = m[7];
/* 2104 */     this.m20 = m[8];
/* 2105 */     this.m21 = m[9];
/* 2106 */     this.m22 = m[10];
/* 2107 */     this.m23 = m[11];
/* 2108 */     this.m30 = m[12];
/* 2109 */     this.m31 = m[13];
/* 2110 */     this.m32 = m[14];
/* 2111 */     this.m33 = m[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3f v1) {
/* 2121 */     this.m00 = 1.0F;
/* 2122 */     this.m01 = 0.0F;
/* 2123 */     this.m02 = 0.0F;
/* 2124 */     this.m03 = v1.x;
/*      */     
/* 2126 */     this.m10 = 0.0F;
/* 2127 */     this.m11 = 1.0F;
/* 2128 */     this.m12 = 0.0F;
/* 2129 */     this.m13 = v1.y;
/*      */     
/* 2131 */     this.m20 = 0.0F;
/* 2132 */     this.m21 = 0.0F;
/* 2133 */     this.m22 = 1.0F;
/* 2134 */     this.m23 = v1.z;
/*      */     
/* 2136 */     this.m30 = 0.0F;
/* 2137 */     this.m31 = 0.0F;
/* 2138 */     this.m32 = 0.0F;
/* 2139 */     this.m33 = 1.0F;
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
/* 2151 */     this.m00 = scale;
/* 2152 */     this.m01 = 0.0F;
/* 2153 */     this.m02 = 0.0F;
/* 2154 */     this.m03 = t1.x;
/*      */     
/* 2156 */     this.m10 = 0.0F;
/* 2157 */     this.m11 = scale;
/* 2158 */     this.m12 = 0.0F;
/* 2159 */     this.m13 = t1.y;
/*      */     
/* 2161 */     this.m20 = 0.0F;
/* 2162 */     this.m21 = 0.0F;
/* 2163 */     this.m22 = scale;
/* 2164 */     this.m23 = t1.z;
/*      */     
/* 2166 */     this.m30 = 0.0F;
/* 2167 */     this.m31 = 0.0F;
/* 2168 */     this.m32 = 0.0F;
/* 2169 */     this.m33 = 1.0F;
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
/* 2181 */     this.m00 = scale;
/* 2182 */     this.m01 = 0.0F;
/* 2183 */     this.m02 = 0.0F;
/* 2184 */     this.m03 = scale * t1.x;
/*      */     
/* 2186 */     this.m10 = 0.0F;
/* 2187 */     this.m11 = scale;
/* 2188 */     this.m12 = 0.0F;
/* 2189 */     this.m13 = scale * t1.y;
/*      */     
/* 2191 */     this.m20 = 0.0F;
/* 2192 */     this.m21 = 0.0F;
/* 2193 */     this.m22 = scale;
/* 2194 */     this.m23 = scale * t1.z;
/*      */     
/* 2196 */     this.m30 = 0.0F;
/* 2197 */     this.m31 = 0.0F;
/* 2198 */     this.m32 = 0.0F;
/* 2199 */     this.m33 = 1.0F;
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
/* 2212 */     this.m00 = m1.m00 * scale;
/* 2213 */     this.m01 = m1.m01 * scale;
/* 2214 */     this.m02 = m1.m02 * scale;
/* 2215 */     this.m03 = t1.x;
/*      */     
/* 2217 */     this.m10 = m1.m10 * scale;
/* 2218 */     this.m11 = m1.m11 * scale;
/* 2219 */     this.m12 = m1.m12 * scale;
/* 2220 */     this.m13 = t1.y;
/*      */     
/* 2222 */     this.m20 = m1.m20 * scale;
/* 2223 */     this.m21 = m1.m21 * scale;
/* 2224 */     this.m22 = m1.m22 * scale;
/* 2225 */     this.m23 = t1.z;
/*      */     
/* 2227 */     this.m30 = 0.0F;
/* 2228 */     this.m31 = 0.0F;
/* 2229 */     this.m32 = 0.0F;
/* 2230 */     this.m33 = 1.0F;
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
/* 2243 */     this.m00 = (float)(m1.m00 * scale);
/* 2244 */     this.m01 = (float)(m1.m01 * scale);
/* 2245 */     this.m02 = (float)(m1.m02 * scale);
/* 2246 */     this.m03 = (float)t1.x;
/*      */     
/* 2248 */     this.m10 = (float)(m1.m10 * scale);
/* 2249 */     this.m11 = (float)(m1.m11 * scale);
/* 2250 */     this.m12 = (float)(m1.m12 * scale);
/* 2251 */     this.m13 = (float)t1.y;
/*      */     
/* 2253 */     this.m20 = (float)(m1.m20 * scale);
/* 2254 */     this.m21 = (float)(m1.m21 * scale);
/* 2255 */     this.m22 = (float)(m1.m22 * scale);
/* 2256 */     this.m23 = (float)t1.z;
/*      */     
/* 2258 */     this.m30 = 0.0F;
/* 2259 */     this.m31 = 0.0F;
/* 2260 */     this.m32 = 0.0F;
/* 2261 */     this.m33 = 1.0F;
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
/* 2272 */     this.m03 = trans.x;
/* 2273 */     this.m13 = trans.y;
/* 2274 */     this.m23 = trans.z;
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
/* 2287 */     float sinAngle = MathHelper.sin(angle);
/* 2288 */     float cosAngle = MathHelper.cos(angle);
/*      */     
/* 2290 */     this.m00 = 1.0F;
/* 2291 */     this.m01 = 0.0F;
/* 2292 */     this.m02 = 0.0F;
/* 2293 */     this.m03 = 0.0F;
/*      */     
/* 2295 */     this.m10 = 0.0F;
/* 2296 */     this.m11 = cosAngle;
/* 2297 */     this.m12 = -sinAngle;
/* 2298 */     this.m13 = 0.0F;
/*      */     
/* 2300 */     this.m20 = 0.0F;
/* 2301 */     this.m21 = sinAngle;
/* 2302 */     this.m22 = cosAngle;
/* 2303 */     this.m23 = 0.0F;
/*      */     
/* 2305 */     this.m30 = 0.0F;
/* 2306 */     this.m31 = 0.0F;
/* 2307 */     this.m32 = 0.0F;
/* 2308 */     this.m33 = 1.0F;
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
/* 2320 */     float sinAngle = MathHelper.sin(angle);
/* 2321 */     float cosAngle = MathHelper.cos(angle);
/*      */     
/* 2323 */     this.m00 = cosAngle;
/* 2324 */     this.m01 = 0.0F;
/* 2325 */     this.m02 = sinAngle;
/* 2326 */     this.m03 = 0.0F;
/*      */     
/* 2328 */     this.m10 = 0.0F;
/* 2329 */     this.m11 = 1.0F;
/* 2330 */     this.m12 = 0.0F;
/* 2331 */     this.m13 = 0.0F;
/*      */     
/* 2333 */     this.m20 = -sinAngle;
/* 2334 */     this.m21 = 0.0F;
/* 2335 */     this.m22 = cosAngle;
/* 2336 */     this.m23 = 0.0F;
/*      */     
/* 2338 */     this.m30 = 0.0F;
/* 2339 */     this.m31 = 0.0F;
/* 2340 */     this.m32 = 0.0F;
/* 2341 */     this.m33 = 1.0F;
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
/* 2353 */     float sinAngle = MathHelper.sin(angle);
/* 2354 */     float cosAngle = MathHelper.cos(angle);
/*      */     
/* 2356 */     this.m00 = cosAngle;
/* 2357 */     this.m01 = -sinAngle;
/* 2358 */     this.m02 = 0.0F;
/* 2359 */     this.m03 = 0.0F;
/*      */     
/* 2361 */     this.m10 = sinAngle;
/* 2362 */     this.m11 = cosAngle;
/* 2363 */     this.m12 = 0.0F;
/* 2364 */     this.m13 = 0.0F;
/*      */     
/* 2366 */     this.m20 = 0.0F;
/* 2367 */     this.m21 = 0.0F;
/* 2368 */     this.m22 = 1.0F;
/* 2369 */     this.m23 = 0.0F;
/*      */     
/* 2371 */     this.m30 = 0.0F;
/* 2372 */     this.m31 = 0.0F;
/* 2373 */     this.m32 = 0.0F;
/* 2374 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 2383 */     this.m00 *= scalar;
/* 2384 */     this.m01 *= scalar;
/* 2385 */     this.m02 *= scalar;
/* 2386 */     this.m03 *= scalar;
/* 2387 */     this.m10 *= scalar;
/* 2388 */     this.m11 *= scalar;
/* 2389 */     this.m12 *= scalar;
/* 2390 */     this.m13 *= scalar;
/* 2391 */     this.m20 *= scalar;
/* 2392 */     this.m21 *= scalar;
/* 2393 */     this.m22 *= scalar;
/* 2394 */     this.m23 *= scalar;
/* 2395 */     this.m30 *= scalar;
/* 2396 */     this.m31 *= scalar;
/* 2397 */     this.m32 *= scalar;
/* 2398 */     this.m33 *= scalar;
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
/* 2409 */     m1.m00 *= scalar;
/* 2410 */     m1.m01 *= scalar;
/* 2411 */     m1.m02 *= scalar;
/* 2412 */     m1.m03 *= scalar;
/* 2413 */     m1.m10 *= scalar;
/* 2414 */     m1.m11 *= scalar;
/* 2415 */     m1.m12 *= scalar;
/* 2416 */     m1.m13 *= scalar;
/* 2417 */     m1.m20 *= scalar;
/* 2418 */     m1.m21 *= scalar;
/* 2419 */     m1.m22 *= scalar;
/* 2420 */     m1.m23 *= scalar;
/* 2421 */     m1.m30 *= scalar;
/* 2422 */     m1.m31 *= scalar;
/* 2423 */     m1.m32 *= scalar;
/* 2424 */     m1.m33 *= scalar;
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
/* 2439 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20 + this.m03 * m1.m30;
/*      */     
/* 2441 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21 + this.m03 * m1.m31;
/*      */     
/* 2443 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22 + this.m03 * m1.m32;
/*      */     
/* 2445 */     float m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + this.m02 * m1.m23 + this.m03 * m1.m33;
/*      */ 
/*      */     
/* 2448 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20 + this.m13 * m1.m30;
/*      */     
/* 2450 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21 + this.m13 * m1.m31;
/*      */     
/* 2452 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22 + this.m13 * m1.m32;
/*      */     
/* 2454 */     float m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + this.m12 * m1.m23 + this.m13 * m1.m33;
/*      */ 
/*      */     
/* 2457 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20 + this.m23 * m1.m30;
/*      */     
/* 2459 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21 + this.m23 * m1.m31;
/*      */     
/* 2461 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22 + this.m23 * m1.m32;
/*      */     
/* 2463 */     float m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + this.m22 * m1.m23 + this.m23 * m1.m33;
/*      */ 
/*      */     
/* 2466 */     float m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + this.m32 * m1.m20 + this.m33 * m1.m30;
/*      */     
/* 2468 */     float m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + this.m32 * m1.m21 + this.m33 * m1.m31;
/*      */     
/* 2470 */     float m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + this.m32 * m1.m22 + this.m33 * m1.m32;
/*      */     
/* 2472 */     float m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + this.m32 * m1.m23 + this.m33 * m1.m33;
/*      */ 
/*      */     
/* 2475 */     this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2476 */     this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2477 */     this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2478 */     this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/* 2489 */     if (this != m1 && this != m2) {
/*      */       
/* 2491 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/*      */       
/* 2493 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/*      */       
/* 2495 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/*      */       
/* 2497 */       this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */ 
/*      */       
/* 2500 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/*      */       
/* 2502 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/*      */       
/* 2504 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/*      */       
/* 2506 */       this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */ 
/*      */       
/* 2509 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/*      */       
/* 2511 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/*      */       
/* 2513 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/*      */       
/* 2515 */       this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */ 
/*      */       
/* 2518 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/*      */       
/* 2520 */       this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/*      */       
/* 2522 */       this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/*      */       
/* 2524 */       this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2531 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2532 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2533 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2534 */       float m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2536 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2537 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2538 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2539 */       float m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2541 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2542 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2543 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2544 */       float m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2546 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2547 */       float m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2548 */       float m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2549 */       float m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2551 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2552 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2553 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2554 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/* 2566 */     if (this != m1 && this != m2) {
/* 2567 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2568 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2569 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2570 */       this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2572 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2573 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2574 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2575 */       this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2577 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2578 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2579 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2580 */       this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2582 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2583 */       this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2584 */       this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2585 */       this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2592 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2593 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2594 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2595 */       float m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2597 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2598 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2599 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2600 */       float m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2602 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2603 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2604 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2605 */       float m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2607 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2608 */       float m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2609 */       float m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2610 */       float m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2612 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2613 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2614 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2615 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/* 2628 */     if (this != m1 && this != m2) {
/* 2629 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2630 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2631 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2632 */       this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2634 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2635 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2636 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2637 */       this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2639 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2640 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2641 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2642 */       this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2644 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2645 */       this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2646 */       this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2647 */       this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2654 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2655 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2656 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2657 */       float m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2659 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2660 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2661 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2662 */       float m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2664 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2665 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2666 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2667 */       float m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2669 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2670 */       float m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2671 */       float m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2672 */       float m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2674 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2675 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2676 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2677 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/* 2691 */     if (this != m1 && this != m2) {
/* 2692 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2693 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2694 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2695 */       this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2697 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2698 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2699 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2700 */       this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2702 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2703 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2704 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2705 */       this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2707 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2708 */       this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2709 */       this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2710 */       this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2719 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2720 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2721 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2722 */       float m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2724 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2725 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2726 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2727 */       float m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2729 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2730 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2731 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2732 */       float m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2734 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2735 */       float m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2736 */       float m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2737 */       float m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2739 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2740 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2741 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2742 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/* 2757 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && this.m33 == m1.m33);
/*      */ 
/*      */     
/*      */     }
/*      */     catch (NullPointerException e2) {
/*      */ 
/*      */       
/* 2764 */       return false;
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
/* 2779 */     try { Matrix4f m2 = (Matrix4f)t1;
/* 2780 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && this.m33 == m2.m33);
/*      */       
/*      */        }
/*      */     
/*      */     catch (ClassCastException e1)
/*      */     
/*      */     { 
/* 2787 */       return false; }
/* 2788 */     catch (NullPointerException e2) { return false; }
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
/*      */   public boolean epsilonEquals(Matrix4f m1, float epsilon) {
/* 2803 */     boolean status = (Math.abs(this.m00 - m1.m00) <= epsilon);
/*      */     
/* 2805 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 2806 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false; 
/* 2807 */     if (Math.abs(this.m03 - m1.m03) > epsilon) status = false;
/*      */     
/* 2809 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 2810 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 2811 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false; 
/* 2812 */     if (Math.abs(this.m13 - m1.m13) > epsilon) status = false;
/*      */     
/* 2814 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 2815 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 2816 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false; 
/* 2817 */     if (Math.abs(this.m23 - m1.m23) > epsilon) status = false;
/*      */     
/* 2819 */     if (Math.abs(this.m30 - m1.m30) > epsilon) status = false; 
/* 2820 */     if (Math.abs(this.m31 - m1.m31) > epsilon) status = false; 
/* 2821 */     if (Math.abs(this.m32 - m1.m32) > epsilon) status = false; 
/* 2822 */     if (Math.abs(this.m33 - m1.m33) > epsilon) status = false;
/*      */     
/* 2824 */     return status;
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
/* 2839 */     long bits = 1L;
/* 2840 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 2841 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 2842 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 2843 */     bits = VecMathUtil.hashFloatBits(bits, this.m03);
/* 2844 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 2845 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 2846 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 2847 */     bits = VecMathUtil.hashFloatBits(bits, this.m13);
/* 2848 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 2849 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 2850 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 2851 */     bits = VecMathUtil.hashFloatBits(bits, this.m23);
/* 2852 */     bits = VecMathUtil.hashFloatBits(bits, this.m30);
/* 2853 */     bits = VecMathUtil.hashFloatBits(bits, this.m31);
/* 2854 */     bits = VecMathUtil.hashFloatBits(bits, this.m32);
/* 2855 */     bits = VecMathUtil.hashFloatBits(bits, this.m33);
/* 2856 */     return VecMathUtil.hashFinish(bits);
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
/* 2869 */     float x = this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w;
/*      */     
/* 2871 */     float y = this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w;
/*      */     
/* 2873 */     float z = this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w;
/*      */     
/* 2875 */     vecOut.w = this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w;
/*      */     
/* 2877 */     vecOut.x = x;
/* 2878 */     vecOut.y = y;
/* 2879 */     vecOut.z = z;
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
/* 2892 */     float x = this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w;
/*      */     
/* 2894 */     float y = this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w;
/*      */     
/* 2896 */     float z = this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w;
/*      */     
/* 2898 */     vec.w = this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w;
/*      */     
/* 2900 */     vec.x = x;
/* 2901 */     vec.y = y;
/* 2902 */     vec.z = z;
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
/* 2915 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 2916 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 2917 */     pointOut.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 2918 */     pointOut.x = x;
/* 2919 */     pointOut.y = y;
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
/* 2932 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 2933 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 2934 */     point.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 2935 */     point.x = x;
/* 2936 */     point.y = y;
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
/* 2949 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 2950 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 2951 */     normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 2952 */     normalOut.x = x;
/* 2953 */     normalOut.y = y;
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
/* 2966 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 2967 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 2968 */     normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 2969 */     normal.x = x;
/* 2970 */     normal.y = y;
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
/* 2986 */     double[] tmp_rot = new double[9];
/* 2987 */     double[] tmp_scale = new double[3];
/*      */     
/* 2989 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 2991 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 2992 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 2993 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 2995 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 2996 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 2997 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 2999 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 3000 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 3001 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
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
/* 3016 */     double[] tmp_rot = new double[9];
/* 3017 */     double[] tmp_scale = new double[3];
/*      */     
/* 3019 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3021 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 3022 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 3023 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 3025 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 3026 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 3027 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 3029 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 3030 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 3031 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
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
/* 3045 */     double[] tmp_rot = new double[9];
/* 3046 */     double[] tmp_scale = new double[3];
/* 3047 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3049 */     this.m00 = (float)((1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z) * tmp_scale[0]);
/* 3050 */     this.m10 = (float)((2.0F * (q1.x * q1.y + q1.w * q1.z)) * tmp_scale[0]);
/* 3051 */     this.m20 = (float)((2.0F * (q1.x * q1.z - q1.w * q1.y)) * tmp_scale[0]);
/*      */     
/* 3053 */     this.m01 = (float)((2.0F * (q1.x * q1.y - q1.w * q1.z)) * tmp_scale[1]);
/* 3054 */     this.m11 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z) * tmp_scale[1]);
/* 3055 */     this.m21 = (float)((2.0F * (q1.y * q1.z + q1.w * q1.x)) * tmp_scale[1]);
/*      */     
/* 3057 */     this.m02 = (float)((2.0F * (q1.x * q1.z + q1.w * q1.y)) * tmp_scale[2]);
/* 3058 */     this.m12 = (float)((2.0F * (q1.y * q1.z - q1.w * q1.x)) * tmp_scale[2]);
/* 3059 */     this.m22 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y) * tmp_scale[2]);
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
/* 3075 */     double[] tmp_rot = new double[9];
/* 3076 */     double[] tmp_scale = new double[3];
/*      */     
/* 3078 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3080 */     this.m00 = (float)((1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z) * tmp_scale[0]);
/* 3081 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0]);
/* 3082 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0]);
/*      */     
/* 3084 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1]);
/* 3085 */     this.m11 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z) * tmp_scale[1]);
/* 3086 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1]);
/*      */     
/* 3088 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2]);
/* 3089 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2]);
/* 3090 */     this.m22 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y) * tmp_scale[2]);
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
/* 3104 */     double[] tmp_rot = new double[9];
/* 3105 */     double[] tmp_scale = new double[3];
/*      */     
/* 3107 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3109 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 3110 */     if (mag < 1.0E-8D) {
/* 3111 */       this.m00 = 1.0F;
/* 3112 */       this.m01 = 0.0F;
/* 3113 */       this.m02 = 0.0F;
/*      */       
/* 3115 */       this.m10 = 0.0F;
/* 3116 */       this.m11 = 1.0F;
/* 3117 */       this.m12 = 0.0F;
/*      */       
/* 3119 */       this.m20 = 0.0F;
/* 3120 */       this.m21 = 0.0F;
/* 3121 */       this.m22 = 1.0F;
/*      */     } else {
/* 3123 */       mag = 1.0D / mag;
/* 3124 */       double ax = a1.x * mag;
/* 3125 */       double ay = a1.y * mag;
/* 3126 */       double az = a1.z * mag;
/*      */       
/* 3128 */       double sinTheta = MathHelper.sin(a1.angle);
/* 3129 */       double cosTheta = MathHelper.cos(a1.angle);
/* 3130 */       double t = 1.0D - cosTheta;
/*      */       
/* 3132 */       double xz = (a1.x * a1.z);
/* 3133 */       double xy = (a1.x * a1.y);
/* 3134 */       double yz = (a1.y * a1.z);
/*      */       
/* 3136 */       this.m00 = (float)((t * ax * ax + cosTheta) * tmp_scale[0]);
/* 3137 */       this.m01 = (float)((t * xy - sinTheta * az) * tmp_scale[1]);
/* 3138 */       this.m02 = (float)((t * xz + sinTheta * ay) * tmp_scale[2]);
/*      */       
/* 3140 */       this.m10 = (float)((t * xy + sinTheta * az) * tmp_scale[0]);
/* 3141 */       this.m11 = (float)((t * ay * ay + cosTheta) * tmp_scale[1]);
/* 3142 */       this.m12 = (float)((t * yz - sinTheta * ax) * tmp_scale[2]);
/*      */       
/* 3144 */       this.m20 = (float)((t * xz - sinTheta * ay) * tmp_scale[0]);
/* 3145 */       this.m21 = (float)((t * yz + sinTheta * ax) * tmp_scale[1]);
/* 3146 */       this.m22 = (float)((t * az * az + cosTheta) * tmp_scale[2]);
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
/* 3157 */     this.m00 = 0.0F;
/* 3158 */     this.m01 = 0.0F;
/* 3159 */     this.m02 = 0.0F;
/* 3160 */     this.m03 = 0.0F;
/* 3161 */     this.m10 = 0.0F;
/* 3162 */     this.m11 = 0.0F;
/* 3163 */     this.m12 = 0.0F;
/* 3164 */     this.m13 = 0.0F;
/* 3165 */     this.m20 = 0.0F;
/* 3166 */     this.m21 = 0.0F;
/* 3167 */     this.m22 = 0.0F;
/* 3168 */     this.m23 = 0.0F;
/* 3169 */     this.m30 = 0.0F;
/* 3170 */     this.m31 = 0.0F;
/* 3171 */     this.m32 = 0.0F;
/* 3172 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3180 */     this.m00 = -this.m00;
/* 3181 */     this.m01 = -this.m01;
/* 3182 */     this.m02 = -this.m02;
/* 3183 */     this.m03 = -this.m03;
/* 3184 */     this.m10 = -this.m10;
/* 3185 */     this.m11 = -this.m11;
/* 3186 */     this.m12 = -this.m12;
/* 3187 */     this.m13 = -this.m13;
/* 3188 */     this.m20 = -this.m20;
/* 3189 */     this.m21 = -this.m21;
/* 3190 */     this.m22 = -this.m22;
/* 3191 */     this.m23 = -this.m23;
/* 3192 */     this.m30 = -this.m30;
/* 3193 */     this.m31 = -this.m31;
/* 3194 */     this.m32 = -this.m32;
/* 3195 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4f m1) {
/* 3205 */     this.m00 = -m1.m00;
/* 3206 */     this.m01 = -m1.m01;
/* 3207 */     this.m02 = -m1.m02;
/* 3208 */     this.m03 = -m1.m03;
/* 3209 */     this.m10 = -m1.m10;
/* 3210 */     this.m11 = -m1.m11;
/* 3211 */     this.m12 = -m1.m12;
/* 3212 */     this.m13 = -m1.m13;
/* 3213 */     this.m20 = -m1.m20;
/* 3214 */     this.m21 = -m1.m21;
/* 3215 */     this.m22 = -m1.m22;
/* 3216 */     this.m23 = -m1.m23;
/* 3217 */     this.m30 = -m1.m30;
/* 3218 */     this.m31 = -m1.m31;
/* 3219 */     this.m32 = -m1.m32;
/* 3220 */     this.m33 = -m1.m33;
/*      */   }
/*      */   
/*      */   private final void getScaleRotate(double[] scales, double[] rots) {
/* 3224 */     double[] tmp = new double[9];
/* 3225 */     tmp[0] = this.m00;
/* 3226 */     tmp[1] = this.m01;
/* 3227 */     tmp[2] = this.m02;
/*      */     
/* 3229 */     tmp[3] = this.m10;
/* 3230 */     tmp[4] = this.m11;
/* 3231 */     tmp[5] = this.m12;
/*      */     
/* 3233 */     tmp[6] = this.m20;
/* 3234 */     tmp[7] = this.m21;
/* 3235 */     tmp[8] = this.m22;
/*      */     
/* 3237 */     Matrix3d.compute_svd(tmp, scales, rots);
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
/* 3252 */     Matrix4f m1 = null;
/*      */     try {
/* 3254 */       m1 = (Matrix4f)super.clone();
/* 3255 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3257 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3260 */     return m1;
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
/* 3271 */     return this.m00;
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
/* 3282 */     this.m00 = m00;
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
/* 3293 */     return this.m01;
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
/* 3304 */     this.m01 = m01;
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
/* 3315 */     return this.m02;
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
/* 3326 */     this.m02 = m02;
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
/* 3337 */     return this.m10;
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
/* 3348 */     this.m10 = m10;
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
/* 3359 */     return this.m11;
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
/* 3370 */     this.m11 = m11;
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
/* 3381 */     return this.m12;
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
/* 3392 */     this.m12 = m12;
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
/* 3403 */     return this.m20;
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
/* 3414 */     this.m20 = m20;
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
/* 3425 */     return this.m21;
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
/* 3436 */     this.m21 = m21;
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
/* 3447 */     return this.m22;
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
/* 3458 */     this.m22 = m22;
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
/* 3469 */     return this.m03;
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
/* 3480 */     this.m03 = m03;
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
/* 3491 */     return this.m13;
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
/* 3502 */     this.m13 = m13;
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
/* 3513 */     return this.m23;
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
/* 3524 */     this.m23 = m23;
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
/* 3535 */     return this.m30;
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
/* 3547 */     this.m30 = m30;
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
/* 3558 */     return this.m31;
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
/* 3569 */     this.m31 = m31;
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
/* 3580 */     return this.m32;
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
/* 3592 */     this.m32 = m32;
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
/* 3603 */     return this.m33;
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
/* 3614 */     this.m33 = m33;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Matrix4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */