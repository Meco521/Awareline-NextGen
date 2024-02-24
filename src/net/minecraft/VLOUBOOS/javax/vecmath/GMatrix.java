/*      */ package net.minecraft.VLOUBOOS.javax.vecmath;
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
/*      */ public class GMatrix
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 2777097312029690941L;
/*      */   private static final boolean debug = false;
/*      */   int nRow;
/*      */   int nCol;
/*      */   double[][] values;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public GMatrix(int nRow, int nCol) {
/*      */     int l;
/*   60 */     this.values = new double[nRow][nCol];
/*   61 */     this.nRow = nRow;
/*   62 */     this.nCol = nCol;
/*      */     
/*      */     int i;
/*   65 */     for (i = 0; i < nRow; i++) {
/*   66 */       for (int j = 0; j < nCol; j++) {
/*   67 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*   72 */     if (nRow < nCol) {
/*   73 */       l = nRow;
/*      */     } else {
/*   75 */       l = nCol;
/*      */     } 
/*   77 */     for (i = 0; i < l; i++) {
/*   78 */       this.values[i][i] = 1.0D;
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
/*      */   
/*      */   public GMatrix(int nRow, int nCol, double[] matrix) {
/*   96 */     this.values = new double[nRow][nCol];
/*   97 */     this.nRow = nRow;
/*   98 */     this.nCol = nCol;
/*      */ 
/*      */     
/*  101 */     for (int i = 0; i < nRow; i++) {
/*  102 */       for (int j = 0; j < nCol; j++) {
/*  103 */         this.values[i][j] = matrix[i * nCol + j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GMatrix(GMatrix matrix) {
/*  115 */     this.nRow = matrix.nRow;
/*  116 */     this.nCol = matrix.nCol;
/*  117 */     this.values = new double[this.nRow][this.nCol];
/*      */ 
/*      */     
/*  120 */     for (int i = 0; i < this.nRow; i++) {
/*  121 */       for (int j = 0; j < this.nCol; j++) {
/*  122 */         this.values[i][j] = matrix.values[i][j];
/*      */       }
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
/*      */   public final void mul(GMatrix m1) {
/*  136 */     if (this.nCol != m1.nRow || this.nCol != m1.nCol) {
/*  137 */       throw new MismatchedSizeException(
/*  138 */           VecMathI18N.getString("GMatrix0"));
/*      */     }
/*  140 */     double[][] tmp = new double[this.nRow][this.nCol];
/*      */     
/*  142 */     for (int i = 0; i < this.nRow; i++) {
/*  143 */       for (int j = 0; j < this.nCol; j++) {
/*  144 */         tmp[i][j] = 0.0D;
/*  145 */         for (int k = 0; k < this.nCol; k++) {
/*  146 */           tmp[i][j] = tmp[i][j] + this.values[i][k] * m1.values[k][j];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  151 */     this.values = tmp;
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
/*      */   public final void mul(GMatrix m1, GMatrix m2) {
/*  164 */     if (m1.nCol != m2.nRow || this.nRow != m1.nRow || this.nCol != m2.nCol) {
/*  165 */       throw new MismatchedSizeException(
/*  166 */           VecMathI18N.getString("GMatrix1"));
/*      */     }
/*  168 */     double[][] tmp = new double[this.nRow][this.nCol];
/*      */     
/*  170 */     for (int i = 0; i < m1.nRow; i++) {
/*  171 */       for (int j = 0; j < m2.nCol; j++) {
/*  172 */         tmp[i][j] = 0.0D;
/*  173 */         for (int k = 0; k < m1.nCol; k++) {
/*  174 */           tmp[i][j] = tmp[i][j] + m1.values[i][k] * m2.values[k][j];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  179 */     this.values = tmp;
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
/*      */   public final void mul(GVector v1, GVector v2) {
/*  194 */     if (this.nRow < v1.getSize()) {
/*  195 */       throw new MismatchedSizeException(
/*  196 */           VecMathI18N.getString("GMatrix2"));
/*      */     }
/*  198 */     if (this.nCol < v2.getSize()) {
/*  199 */       throw new MismatchedSizeException(
/*  200 */           VecMathI18N.getString("GMatrix3"));
/*      */     }
/*  202 */     for (int i = 0; i < v1.getSize(); i++) {
/*  203 */       for (int j = 0; j < v2.getSize(); j++) {
/*  204 */         this.values[i][j] = v1.values[i] * v2.values[j];
/*      */       }
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
/*      */   public final void add(GMatrix m1) {
/*  217 */     if (this.nRow != m1.nRow) {
/*  218 */       throw new MismatchedSizeException(
/*  219 */           VecMathI18N.getString("GMatrix4"));
/*      */     }
/*  221 */     if (this.nCol != m1.nCol) {
/*  222 */       throw new MismatchedSizeException(
/*  223 */           VecMathI18N.getString("GMatrix5"));
/*      */     }
/*  225 */     for (int i = 0; i < this.nRow; i++) {
/*  226 */       for (int j = 0; j < this.nCol; j++) {
/*  227 */         this.values[i][j] = this.values[i][j] + m1.values[i][j];
/*      */       }
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
/*      */   public final void add(GMatrix m1, GMatrix m2) {
/*  241 */     if (m2.nRow != m1.nRow) {
/*  242 */       throw new MismatchedSizeException(
/*  243 */           VecMathI18N.getString("GMatrix6"));
/*      */     }
/*  245 */     if (m2.nCol != m1.nCol) {
/*  246 */       throw new MismatchedSizeException(
/*  247 */           VecMathI18N.getString("GMatrix7"));
/*      */     }
/*  249 */     if (this.nCol != m1.nCol || this.nRow != m1.nRow) {
/*  250 */       throw new MismatchedSizeException(
/*  251 */           VecMathI18N.getString("GMatrix8"));
/*      */     }
/*  253 */     for (int i = 0; i < this.nRow; i++) {
/*  254 */       for (int j = 0; j < this.nCol; j++) {
/*  255 */         this.values[i][j] = m1.values[i][j] + m2.values[i][j];
/*      */       }
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
/*      */   public final void sub(GMatrix m1) {
/*  268 */     if (this.nRow != m1.nRow) {
/*  269 */       throw new MismatchedSizeException(
/*  270 */           VecMathI18N.getString("GMatrix9"));
/*      */     }
/*  272 */     if (this.nCol != m1.nCol) {
/*  273 */       throw new MismatchedSizeException(
/*  274 */           VecMathI18N.getString("GMatrix28"));
/*      */     }
/*  276 */     for (int i = 0; i < this.nRow; i++) {
/*  277 */       for (int j = 0; j < this.nCol; j++) {
/*  278 */         this.values[i][j] = this.values[i][j] - m1.values[i][j];
/*      */       }
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
/*      */   public final void sub(GMatrix m1, GMatrix m2) {
/*  292 */     if (m2.nRow != m1.nRow) {
/*  293 */       throw new MismatchedSizeException(
/*  294 */           VecMathI18N.getString("GMatrix10"));
/*      */     }
/*  296 */     if (m2.nCol != m1.nCol) {
/*  297 */       throw new MismatchedSizeException(
/*  298 */           VecMathI18N.getString("GMatrix11"));
/*      */     }
/*  300 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/*  301 */       throw new MismatchedSizeException(
/*  302 */           VecMathI18N.getString("GMatrix12"));
/*      */     }
/*  304 */     for (int i = 0; i < this.nRow; i++) {
/*  305 */       for (int j = 0; j < this.nCol; j++) {
/*  306 */         this.values[i][j] = m1.values[i][j] - m2.values[i][j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/*  317 */     for (int i = 0; i < this.nRow; i++) {
/*  318 */       for (int j = 0; j < this.nCol; j++) {
/*  319 */         this.values[i][j] = -this.values[i][j];
/*      */       }
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
/*      */   public final void negate(GMatrix m1) {
/*  332 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/*  333 */       throw new MismatchedSizeException(
/*  334 */           VecMathI18N.getString("GMatrix13"));
/*      */     }
/*  336 */     for (int i = 0; i < this.nRow; i++) {
/*  337 */       for (int j = 0; j < this.nCol; j++) {
/*  338 */         this.values[i][j] = -m1.values[i][j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*      */     int l;
/*      */     int i;
/*  349 */     for (i = 0; i < this.nRow; i++) {
/*  350 */       for (int j = 0; j < this.nCol; j++) {
/*  351 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  356 */     if (this.nRow < this.nCol) {
/*  357 */       l = this.nRow;
/*      */     } else {
/*  359 */       l = this.nCol;
/*      */     } 
/*  361 */     for (i = 0; i < l; i++) {
/*  362 */       this.values[i][i] = 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/*  372 */     for (int i = 0; i < this.nRow; i++) {
/*  373 */       for (int j = 0; j < this.nCol; j++) {
/*  374 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void identityMinus() {
/*      */     int l;
/*      */     int i;
/*  387 */     for (i = 0; i < this.nRow; i++) {
/*  388 */       for (int j = 0; j < this.nCol; j++) {
/*  389 */         this.values[i][j] = -this.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  394 */     if (this.nRow < this.nCol) {
/*  395 */       l = this.nRow;
/*      */     } else {
/*  397 */       l = this.nCol;
/*      */     } 
/*  399 */     for (i = 0; i < l; i++) {
/*  400 */       this.values[i][i] = this.values[i][i] + 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/*  410 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(GMatrix m1) {
/*  420 */     invertGeneral(m1);
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
/*      */   public final void copySubMatrix(int rowSource, int colSource, int numRow, int numCol, int rowDest, int colDest, GMatrix target) {
/*  445 */     if (this != target) {
/*  446 */       for (int i = 0; i < numRow; i++) {
/*  447 */         for (int j = 0; j < numCol; j++) {
/*  448 */           target.values[rowDest + i][colDest + j] = this.values[rowSource + i][colSource + j];
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  453 */       double[][] tmp = new double[numRow][numCol]; int i;
/*  454 */       for (i = 0; i < numRow; i++) {
/*  455 */         for (int j = 0; j < numCol; j++) {
/*  456 */           tmp[i][j] = this.values[rowSource + i][colSource + j];
/*      */         }
/*      */       } 
/*  459 */       for (i = 0; i < numRow; i++) {
/*  460 */         for (int j = 0; j < numCol; j++) {
/*  461 */           target.values[rowDest + i][colDest + j] = tmp[i][j];
/*      */         }
/*      */       } 
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
/*      */   public final void setSize(int nRow, int nCol) {
/*      */     int maxRow, maxCol;
/*  476 */     double[][] tmp = new double[nRow][nCol];
/*      */ 
/*      */     
/*  479 */     if (this.nRow < nRow) {
/*  480 */       maxRow = this.nRow;
/*      */     } else {
/*  482 */       maxRow = nRow;
/*      */     } 
/*  484 */     if (this.nCol < nCol) {
/*  485 */       maxCol = this.nCol;
/*      */     } else {
/*  487 */       maxCol = nCol;
/*      */     } 
/*  489 */     for (int i = 0; i < maxRow; i++) {
/*  490 */       for (int j = 0; j < maxCol; j++) {
/*  491 */         tmp[i][j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/*  495 */     this.nRow = nRow;
/*  496 */     this.nCol = nCol;
/*      */     
/*  498 */     this.values = tmp;
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
/*      */   public final void set(double[] matrix) {
/*  513 */     for (int i = 0; i < this.nRow; i++) {
/*  514 */       for (int j = 0; j < this.nCol; j++) {
/*  515 */         this.values[i][j] = matrix[this.nCol * i + j];
/*      */       }
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
/*      */   public final void set(Matrix3f m1) {
/*  528 */     if (this.nCol < 3 || this.nRow < 3) {
/*  529 */       this.nCol = 3;
/*  530 */       this.nRow = 3;
/*  531 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     
/*  534 */     this.values[0][0] = m1.m00;
/*  535 */     this.values[0][1] = m1.m01;
/*  536 */     this.values[0][2] = m1.m02;
/*      */     
/*  538 */     this.values[1][0] = m1.m10;
/*  539 */     this.values[1][1] = m1.m11;
/*  540 */     this.values[1][2] = m1.m12;
/*      */     
/*  542 */     this.values[2][0] = m1.m20;
/*  543 */     this.values[2][1] = m1.m21;
/*  544 */     this.values[2][2] = m1.m22;
/*      */     
/*  546 */     for (int i = 3; i < this.nRow; i++) {
/*  547 */       for (int j = 3; j < this.nCol; j++) {
/*  548 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/*  559 */     if (this.nRow < 3 || this.nCol < 3) {
/*  560 */       this.values = new double[3][3];
/*  561 */       this.nRow = 3;
/*  562 */       this.nCol = 3;
/*      */     } 
/*      */     
/*  565 */     this.values[0][0] = m1.m00;
/*  566 */     this.values[0][1] = m1.m01;
/*  567 */     this.values[0][2] = m1.m02;
/*      */     
/*  569 */     this.values[1][0] = m1.m10;
/*  570 */     this.values[1][1] = m1.m11;
/*  571 */     this.values[1][2] = m1.m12;
/*      */     
/*  573 */     this.values[2][0] = m1.m20;
/*  574 */     this.values[2][1] = m1.m21;
/*  575 */     this.values[2][2] = m1.m22;
/*      */     
/*  577 */     for (int i = 3; i < this.nRow; i++) {
/*  578 */       for (int j = 3; j < this.nCol; j++) {
/*  579 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/*  591 */     if (this.nRow < 4 || this.nCol < 4) {
/*  592 */       this.values = new double[4][4];
/*  593 */       this.nRow = 4;
/*  594 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  597 */     this.values[0][0] = m1.m00;
/*  598 */     this.values[0][1] = m1.m01;
/*  599 */     this.values[0][2] = m1.m02;
/*  600 */     this.values[0][3] = m1.m03;
/*      */     
/*  602 */     this.values[1][0] = m1.m10;
/*  603 */     this.values[1][1] = m1.m11;
/*  604 */     this.values[1][2] = m1.m12;
/*  605 */     this.values[1][3] = m1.m13;
/*      */     
/*  607 */     this.values[2][0] = m1.m20;
/*  608 */     this.values[2][1] = m1.m21;
/*  609 */     this.values[2][2] = m1.m22;
/*  610 */     this.values[2][3] = m1.m23;
/*      */     
/*  612 */     this.values[3][0] = m1.m30;
/*  613 */     this.values[3][1] = m1.m31;
/*  614 */     this.values[3][2] = m1.m32;
/*  615 */     this.values[3][3] = m1.m33;
/*      */     
/*  617 */     for (int i = 4; i < this.nRow; i++) {
/*  618 */       for (int j = 4; j < this.nCol; j++) {
/*  619 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/*  630 */     if (this.nRow < 4 || this.nCol < 4) {
/*  631 */       this.values = new double[4][4];
/*  632 */       this.nRow = 4;
/*  633 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  636 */     this.values[0][0] = m1.m00;
/*  637 */     this.values[0][1] = m1.m01;
/*  638 */     this.values[0][2] = m1.m02;
/*  639 */     this.values[0][3] = m1.m03;
/*      */     
/*  641 */     this.values[1][0] = m1.m10;
/*  642 */     this.values[1][1] = m1.m11;
/*  643 */     this.values[1][2] = m1.m12;
/*  644 */     this.values[1][3] = m1.m13;
/*      */     
/*  646 */     this.values[2][0] = m1.m20;
/*  647 */     this.values[2][1] = m1.m21;
/*  648 */     this.values[2][2] = m1.m22;
/*  649 */     this.values[2][3] = m1.m23;
/*      */     
/*  651 */     this.values[3][0] = m1.m30;
/*  652 */     this.values[3][1] = m1.m31;
/*  653 */     this.values[3][2] = m1.m32;
/*  654 */     this.values[3][3] = m1.m33;
/*      */     
/*  656 */     for (int i = 4; i < this.nRow; i++) {
/*  657 */       for (int j = 4; j < this.nCol; j++) {
/*  658 */         this.values[i][j] = 0.0D;
/*      */       }
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
/*      */   public final void set(GMatrix m1) {
/*  671 */     if (this.nRow < m1.nRow || this.nCol < m1.nCol) {
/*  672 */       this.nRow = m1.nRow;
/*  673 */       this.nCol = m1.nCol;
/*  674 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     int i;
/*  677 */     for (i = 0; i < Math.min(this.nRow, m1.nRow); i++) {
/*  678 */       for (int j = 0; j < Math.min(this.nCol, m1.nCol); j++) {
/*  679 */         this.values[i][j] = m1.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/*  683 */     for (i = m1.nRow; i < this.nRow; i++) {
/*  684 */       for (int j = m1.nCol; j < this.nCol; j++) {
/*  685 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumRow() {
/*  696 */     return this.nRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumCol() {
/*  705 */     return this.nCol;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getElement(int row, int column) {
/*  716 */     return this.values[row][column];
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
/*      */   public final void setElement(int row, int column, double value) {
/*  728 */     this.values[row][column] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, double[] array) {
/*  738 */     if (this.nCol >= 0) System.arraycopy(this.values[row], 0, array, 0, this.nCol);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, GVector vector) {
/*  748 */     if (vector.getSize() < this.nCol) {
/*  749 */       vector.setSize(this.nCol);
/*      */     }
/*  751 */     if (this.nCol >= 0) System.arraycopy(this.values[row], 0, vector.values, 0, this.nCol);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int col, double[] array) {
/*  761 */     for (int i = 0; i < this.nRow; i++) {
/*  762 */       array[i] = this.values[i][col];
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
/*      */   public final void getColumn(int col, GVector vector) {
/*  774 */     if (vector.getSize() < this.nRow) {
/*  775 */       vector.setSize(this.nRow);
/*      */     }
/*  777 */     for (int i = 0; i < this.nRow; i++) {
/*  778 */       vector.values[i] = this.values[i][col];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3d m1) {
/*  789 */     if (this.nRow < 3 || this.nCol < 3) {
/*  790 */       m1.setZero();
/*  791 */       if (this.nCol > 0) {
/*  792 */         if (this.nRow > 0) {
/*  793 */           m1.m00 = this.values[0][0];
/*  794 */           if (this.nRow > 1) {
/*  795 */             m1.m10 = this.values[1][0];
/*  796 */             if (this.nRow > 2) {
/*  797 */               m1.m20 = this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  801 */         if (this.nCol > 1) {
/*  802 */           if (this.nRow > 0) {
/*  803 */             m1.m01 = this.values[0][1];
/*  804 */             if (this.nRow > 1) {
/*  805 */               m1.m11 = this.values[1][1];
/*  806 */               if (this.nRow > 2) {
/*  807 */                 m1.m21 = this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  811 */           if (this.nCol > 2 && 
/*  812 */             this.nRow > 0) {
/*  813 */             m1.m02 = this.values[0][2];
/*  814 */             if (this.nRow > 1) {
/*  815 */               m1.m12 = this.values[1][2];
/*  816 */               if (this.nRow > 2) {
/*  817 */                 m1.m22 = this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  825 */       m1.m00 = this.values[0][0];
/*  826 */       m1.m01 = this.values[0][1];
/*  827 */       m1.m02 = this.values[0][2];
/*      */       
/*  829 */       m1.m10 = this.values[1][0];
/*  830 */       m1.m11 = this.values[1][1];
/*  831 */       m1.m12 = this.values[1][2];
/*      */       
/*  833 */       m1.m20 = this.values[2][0];
/*  834 */       m1.m21 = this.values[2][1];
/*  835 */       m1.m22 = this.values[2][2];
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
/*      */   public final void get(Matrix3f m1) {
/*  847 */     if (this.nRow < 3 || this.nCol < 3) {
/*  848 */       m1.setZero();
/*  849 */       if (this.nCol > 0) {
/*  850 */         if (this.nRow > 0) {
/*  851 */           m1.m00 = (float)this.values[0][0];
/*  852 */           if (this.nRow > 1) {
/*  853 */             m1.m10 = (float)this.values[1][0];
/*  854 */             if (this.nRow > 2) {
/*  855 */               m1.m20 = (float)this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  859 */         if (this.nCol > 1) {
/*  860 */           if (this.nRow > 0) {
/*  861 */             m1.m01 = (float)this.values[0][1];
/*  862 */             if (this.nRow > 1) {
/*  863 */               m1.m11 = (float)this.values[1][1];
/*  864 */               if (this.nRow > 2) {
/*  865 */                 m1.m21 = (float)this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  869 */           if (this.nCol > 2 && 
/*  870 */             this.nRow > 0) {
/*  871 */             m1.m02 = (float)this.values[0][2];
/*  872 */             if (this.nRow > 1) {
/*  873 */               m1.m12 = (float)this.values[1][2];
/*  874 */               if (this.nRow > 2) {
/*  875 */                 m1.m22 = (float)this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  883 */       m1.m00 = (float)this.values[0][0];
/*  884 */       m1.m01 = (float)this.values[0][1];
/*  885 */       m1.m02 = (float)this.values[0][2];
/*      */       
/*  887 */       m1.m10 = (float)this.values[1][0];
/*  888 */       m1.m11 = (float)this.values[1][1];
/*  889 */       m1.m12 = (float)this.values[1][2];
/*      */       
/*  891 */       m1.m20 = (float)this.values[2][0];
/*  892 */       m1.m21 = (float)this.values[2][1];
/*  893 */       m1.m22 = (float)this.values[2][2];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix4d m1) {
/*  904 */     if (this.nRow < 4 || this.nCol < 4) {
/*  905 */       m1.setZero();
/*  906 */       if (this.nCol > 0) {
/*  907 */         if (this.nRow > 0) {
/*  908 */           m1.m00 = this.values[0][0];
/*  909 */           if (this.nRow > 1) {
/*  910 */             m1.m10 = this.values[1][0];
/*  911 */             if (this.nRow > 2) {
/*  912 */               m1.m20 = this.values[2][0];
/*  913 */               if (this.nRow > 3) {
/*  914 */                 m1.m30 = this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  919 */         if (this.nCol > 1) {
/*  920 */           if (this.nRow > 0) {
/*  921 */             m1.m01 = this.values[0][1];
/*  922 */             if (this.nRow > 1) {
/*  923 */               m1.m11 = this.values[1][1];
/*  924 */               if (this.nRow > 2) {
/*  925 */                 m1.m21 = this.values[2][1];
/*  926 */                 if (this.nRow > 3) {
/*  927 */                   m1.m31 = this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*  932 */           if (this.nCol > 2) {
/*  933 */             if (this.nRow > 0) {
/*  934 */               m1.m02 = this.values[0][2];
/*  935 */               if (this.nRow > 1) {
/*  936 */                 m1.m12 = this.values[1][2];
/*  937 */                 if (this.nRow > 2) {
/*  938 */                   m1.m22 = this.values[2][2];
/*  939 */                   if (this.nRow > 3) {
/*  940 */                     m1.m32 = this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*  945 */             if (this.nCol > 3 && 
/*  946 */               this.nRow > 0) {
/*  947 */               m1.m03 = this.values[0][3];
/*  948 */               if (this.nRow > 1) {
/*  949 */                 m1.m13 = this.values[1][3];
/*  950 */                 if (this.nRow > 2) {
/*  951 */                   m1.m23 = this.values[2][3];
/*  952 */                   if (this.nRow > 3) {
/*  953 */                     m1.m33 = this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  963 */       m1.m00 = this.values[0][0];
/*  964 */       m1.m01 = this.values[0][1];
/*  965 */       m1.m02 = this.values[0][2];
/*  966 */       m1.m03 = this.values[0][3];
/*      */       
/*  968 */       m1.m10 = this.values[1][0];
/*  969 */       m1.m11 = this.values[1][1];
/*  970 */       m1.m12 = this.values[1][2];
/*  971 */       m1.m13 = this.values[1][3];
/*      */       
/*  973 */       m1.m20 = this.values[2][0];
/*  974 */       m1.m21 = this.values[2][1];
/*  975 */       m1.m22 = this.values[2][2];
/*  976 */       m1.m23 = this.values[2][3];
/*      */       
/*  978 */       m1.m30 = this.values[3][0];
/*  979 */       m1.m31 = this.values[3][1];
/*  980 */       m1.m32 = this.values[3][2];
/*  981 */       m1.m33 = this.values[3][3];
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
/*      */   public final void get(Matrix4f m1) {
/*  994 */     if (this.nRow < 4 || this.nCol < 4) {
/*  995 */       m1.setZero();
/*  996 */       if (this.nCol > 0) {
/*  997 */         if (this.nRow > 0) {
/*  998 */           m1.m00 = (float)this.values[0][0];
/*  999 */           if (this.nRow > 1) {
/* 1000 */             m1.m10 = (float)this.values[1][0];
/* 1001 */             if (this.nRow > 2) {
/* 1002 */               m1.m20 = (float)this.values[2][0];
/* 1003 */               if (this.nRow > 3) {
/* 1004 */                 m1.m30 = (float)this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 1009 */         if (this.nCol > 1) {
/* 1010 */           if (this.nRow > 0) {
/* 1011 */             m1.m01 = (float)this.values[0][1];
/* 1012 */             if (this.nRow > 1) {
/* 1013 */               m1.m11 = (float)this.values[1][1];
/* 1014 */               if (this.nRow > 2) {
/* 1015 */                 m1.m21 = (float)this.values[2][1];
/* 1016 */                 if (this.nRow > 3) {
/* 1017 */                   m1.m31 = (float)this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/* 1022 */           if (this.nCol > 2) {
/* 1023 */             if (this.nRow > 0) {
/* 1024 */               m1.m02 = (float)this.values[0][2];
/* 1025 */               if (this.nRow > 1) {
/* 1026 */                 m1.m12 = (float)this.values[1][2];
/* 1027 */                 if (this.nRow > 2) {
/* 1028 */                   m1.m22 = (float)this.values[2][2];
/* 1029 */                   if (this.nRow > 3) {
/* 1030 */                     m1.m32 = (float)this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1035 */             if (this.nCol > 3 && 
/* 1036 */               this.nRow > 0) {
/* 1037 */               m1.m03 = (float)this.values[0][3];
/* 1038 */               if (this.nRow > 1) {
/* 1039 */                 m1.m13 = (float)this.values[1][3];
/* 1040 */                 if (this.nRow > 2) {
/* 1041 */                   m1.m23 = (float)this.values[2][3];
/* 1042 */                   if (this.nRow > 3) {
/* 1043 */                     m1.m33 = (float)this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1053 */       m1.m00 = (float)this.values[0][0];
/* 1054 */       m1.m01 = (float)this.values[0][1];
/* 1055 */       m1.m02 = (float)this.values[0][2];
/* 1056 */       m1.m03 = (float)this.values[0][3];
/*      */       
/* 1058 */       m1.m10 = (float)this.values[1][0];
/* 1059 */       m1.m11 = (float)this.values[1][1];
/* 1060 */       m1.m12 = (float)this.values[1][2];
/* 1061 */       m1.m13 = (float)this.values[1][3];
/*      */       
/* 1063 */       m1.m20 = (float)this.values[2][0];
/* 1064 */       m1.m21 = (float)this.values[2][1];
/* 1065 */       m1.m22 = (float)this.values[2][2];
/* 1066 */       m1.m23 = (float)this.values[2][3];
/*      */       
/* 1068 */       m1.m30 = (float)this.values[3][0];
/* 1069 */       m1.m31 = (float)this.values[3][1];
/* 1070 */       m1.m32 = (float)this.values[3][2];
/* 1071 */       m1.m33 = (float)this.values[3][3];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(GMatrix m1) {
/*      */     int nc;
/*      */     int nr;
/* 1084 */     if (this.nCol < m1.nCol) {
/* 1085 */       nc = this.nCol;
/*      */     } else {
/* 1087 */       nc = m1.nCol;
/*      */     } 
/* 1089 */     if (this.nRow < m1.nRow) {
/* 1090 */       nr = this.nRow;
/*      */     } else {
/* 1092 */       nr = m1.nRow;
/*      */     }  int i;
/* 1094 */     for (i = 0; i < nr; i++) {
/* 1095 */       for (int k = 0; k < nc; k++) {
/* 1096 */         m1.values[i][k] = this.values[i][k];
/*      */       }
/*      */     } 
/* 1099 */     for (i = nr; i < m1.nRow; i++) {
/* 1100 */       for (int k = 0; k < m1.nCol; k++) {
/* 1101 */         m1.values[i][k] = 0.0D;
/*      */       }
/*      */     } 
/* 1104 */     for (int j = nc; j < m1.nCol; j++) {
/* 1105 */       for (i = 0; i < nr; i++) {
/* 1106 */         m1.values[i][j] = 0.0D;
/*      */       }
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
/*      */   public final void setRow(int row, double[] array) {
/* 1120 */     if (this.nCol >= 0) System.arraycopy(array, 0, this.values[row], 0, this.nCol);
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
/*      */   public final void setRow(int row, GVector vector) {
/* 1132 */     if (this.nCol >= 0) System.arraycopy(vector.values, 0, this.values[row], 0, this.nCol);
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
/*      */   public final void setColumn(int col, double[] array) {
/* 1144 */     for (int i = 0; i < this.nRow; i++) {
/* 1145 */       this.values[i][col] = array[i];
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
/*      */   public final void setColumn(int col, GVector vector) {
/* 1158 */     for (int i = 0; i < this.nRow; i++) {
/* 1159 */       this.values[i][col] = vector.values[i];
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
/*      */   public final void mulTransposeBoth(GMatrix m1, GMatrix m2) {
/* 1174 */     if (m1.nRow != m2.nCol || this.nRow != m1.nCol || this.nCol != m2.nRow) {
/* 1175 */       throw new MismatchedSizeException(
/* 1176 */           VecMathI18N.getString("GMatrix14"));
/*      */     }
/* 1178 */     if (m1 == this || m2 == this) {
/* 1179 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1180 */       for (int i = 0; i < this.nRow; i++) {
/* 1181 */         for (int j = 0; j < this.nCol; j++) {
/* 1182 */           tmp[i][j] = 0.0D;
/* 1183 */           for (int k = 0; k < m1.nRow; k++) {
/* 1184 */             tmp[i][j] = tmp[i][j] + m1.values[k][i] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
/* 1188 */       this.values = tmp;
/*      */     } else {
/* 1190 */       for (int i = 0; i < this.nRow; i++) {
/* 1191 */         for (int j = 0; j < this.nCol; j++) {
/* 1192 */           this.values[i][j] = 0.0D;
/* 1193 */           for (int k = 0; k < m1.nRow; k++) {
/* 1194 */             this.values[i][j] = this.values[i][j] + m1.values[k][i] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void mulTransposeRight(GMatrix m1, GMatrix m2) {
/* 1211 */     if (m1.nCol != m2.nCol || this.nCol != m2.nRow || this.nRow != m1.nRow) {
/* 1212 */       throw new MismatchedSizeException(
/* 1213 */           VecMathI18N.getString("GMatrix15"));
/*      */     }
/* 1215 */     if (m1 == this || m2 == this) {
/* 1216 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1217 */       for (int i = 0; i < this.nRow; i++) {
/* 1218 */         for (int j = 0; j < this.nCol; j++) {
/* 1219 */           tmp[i][j] = 0.0D;
/* 1220 */           for (int k = 0; k < m1.nCol; k++) {
/* 1221 */             tmp[i][j] = tmp[i][j] + m1.values[i][k] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
/* 1225 */       this.values = tmp;
/*      */     } else {
/* 1227 */       for (int i = 0; i < this.nRow; i++) {
/* 1228 */         for (int j = 0; j < this.nCol; j++) {
/* 1229 */           this.values[i][j] = 0.0D;
/* 1230 */           for (int k = 0; k < m1.nCol; k++) {
/* 1231 */             this.values[i][j] = this.values[i][j] + m1.values[i][k] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void mulTransposeLeft(GMatrix m1, GMatrix m2) {
/* 1250 */     if (m1.nRow != m2.nRow || this.nCol != m2.nCol || this.nRow != m1.nCol) {
/* 1251 */       throw new MismatchedSizeException(
/* 1252 */           VecMathI18N.getString("GMatrix16"));
/*      */     }
/* 1254 */     if (m1 == this || m2 == this) {
/* 1255 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1256 */       for (int i = 0; i < this.nRow; i++) {
/* 1257 */         for (int j = 0; j < this.nCol; j++) {
/* 1258 */           tmp[i][j] = 0.0D;
/* 1259 */           for (int k = 0; k < m1.nRow; k++) {
/* 1260 */             tmp[i][j] = tmp[i][j] + m1.values[k][i] * m2.values[k][j];
/*      */           }
/*      */         } 
/*      */       } 
/* 1264 */       this.values = tmp;
/*      */     } else {
/* 1266 */       for (int i = 0; i < this.nRow; i++) {
/* 1267 */         for (int j = 0; j < this.nCol; j++) {
/* 1268 */           this.values[i][j] = 0.0D;
/* 1269 */           for (int k = 0; k < m1.nRow; k++) {
/* 1270 */             this.values[i][j] = this.values[i][j] + m1.values[k][i] * m2.values[k][j];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void transpose() {
/* 1285 */     if (this.nRow != this.nCol) {
/*      */       
/* 1287 */       int i = this.nRow;
/* 1288 */       this.nRow = this.nCol;
/* 1289 */       this.nCol = i;
/* 1290 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1291 */       for (i = 0; i < this.nRow; i++) {
/* 1292 */         for (int j = 0; j < this.nCol; j++) {
/* 1293 */           tmp[i][j] = this.values[j][i];
/*      */         }
/*      */       } 
/* 1296 */       this.values = tmp;
/*      */     } else {
/*      */       
/* 1299 */       for (int i = 0; i < this.nRow; i++) {
/* 1300 */         for (int j = 0; j < i; j++) {
/* 1301 */           double swap = this.values[i][j];
/* 1302 */           this.values[i][j] = this.values[j][i];
/* 1303 */           this.values[j][i] = swap;
/*      */         } 
/*      */       } 
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
/*      */   public final void transpose(GMatrix m1) {
/* 1317 */     if (this.nRow != m1.nCol || this.nCol != m1.nRow) {
/* 1318 */       throw new MismatchedSizeException(
/* 1319 */           VecMathI18N.getString("GMatrix17"));
/*      */     }
/* 1321 */     if (m1 != this) {
/* 1322 */       for (int i = 0; i < this.nRow; i++) {
/* 1323 */         for (int j = 0; j < this.nCol; j++) {
/* 1324 */           this.values[i][j] = m1.values[j][i];
/*      */         }
/*      */       } 
/*      */     } else {
/* 1328 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1339 */     StringBuffer buffer = new StringBuffer(this.nRow * this.nCol * 8);
/*      */ 
/*      */ 
/*      */     
/* 1343 */     for (int i = 0; i < this.nRow; i++) {
/* 1344 */       for (int j = 0; j < this.nCol; j++) {
/* 1345 */         buffer.append(this.values[i][j]).append(" ");
/*      */       }
/* 1347 */       buffer.append("\n");
/*      */     } 
/*      */     
/* 1350 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkMatrix(GMatrix m) {
/* 1357 */     for (int i = 0; i < m.nRow; i++) {
/* 1358 */       for (int j = 0; j < m.nCol; j++) {
/* 1359 */         if (Math.abs(m.values[i][j]) < 1.0E-10D) {
/* 1360 */           System.out.print(" 0.0     ");
/*      */         } else {
/* 1362 */           System.out.print(" " + m.values[i][j]);
/*      */         } 
/*      */       } 
/* 1365 */       System.out.print("\n");
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
/*      */   public int hashCode() {
/* 1381 */     long bits = 1L;
/*      */     
/* 1383 */     bits = VecMathUtil.hashLongBits(bits, this.nRow);
/* 1384 */     bits = VecMathUtil.hashLongBits(bits, this.nCol);
/*      */     
/* 1386 */     for (int i = 0; i < this.nRow; i++) {
/* 1387 */       for (int j = 0; j < this.nCol; j++) {
/* 1388 */         bits = VecMathUtil.hashDoubleBits(bits, this.values[i][j]);
/*      */       }
/*      */     } 
/*      */     
/* 1392 */     return VecMathUtil.hashFinish(bits);
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
/*      */   public boolean equals(GMatrix m1) {
/*      */     try {
/* 1407 */       if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/* 1408 */         return false;
/*      */       }
/* 1410 */       for (int i = 0; i < this.nRow; i++) {
/* 1411 */         for (int j = 0; j < this.nCol; j++) {
/* 1412 */           if (this.values[i][j] != m1.values[i][j])
/* 1413 */             return false; 
/*      */         } 
/*      */       } 
/* 1416 */       return true;
/*      */     }
/* 1418 */     catch (NullPointerException e2) {
/* 1419 */       return false;
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
/*      */   public boolean equals(Object o1) {
/*      */     try {
/* 1434 */       GMatrix m2 = (GMatrix)o1;
/*      */       
/* 1436 */       if (this.nRow != m2.nRow || this.nCol != m2.nCol) {
/* 1437 */         return false;
/*      */       }
/* 1439 */       for (int i = 0; i < this.nRow; i++) {
/* 1440 */         for (int j = 0; j < this.nCol; j++) {
/* 1441 */           if (this.values[i][j] != m2.values[i][j])
/* 1442 */             return false; 
/*      */         } 
/*      */       } 
/* 1445 */       return true;
/*      */     }
/* 1447 */     catch (ClassCastException e1) {
/* 1448 */       return false;
/*      */     }
/* 1450 */     catch (NullPointerException e2) {
/* 1451 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(GMatrix m1, float epsilon) {
/* 1459 */     return epsilonEquals(m1, epsilon);
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
/*      */   public boolean epsilonEquals(GMatrix m1, double epsilon) {
/* 1475 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/* 1476 */       return false;
/*      */     }
/* 1478 */     for (int i = 0; i < this.nRow; i++) {
/* 1479 */       for (int j = 0; j < this.nCol; j++) {
/* 1480 */         double diff = this.values[i][j] - m1.values[i][j];
/* 1481 */         if (((diff < 0.0D) ? -diff : diff) > epsilon)
/* 1482 */           return false; 
/*      */       } 
/*      */     } 
/* 1485 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double trace() {
/*      */     int l;
/* 1497 */     if (this.nRow < this.nCol) {
/* 1498 */       l = this.nRow;
/*      */     } else {
/* 1500 */       l = this.nCol;
/*      */     } 
/* 1502 */     double t = 0.0D;
/* 1503 */     for (int i = 0; i < l; i++) {
/* 1504 */       t += this.values[i][i];
/*      */     }
/* 1506 */     return t;
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
/*      */   public final int SVD(GMatrix U, GMatrix W, GMatrix V) {
/* 1528 */     if (this.nCol != V.nCol || this.nCol != V.nRow) {
/* 1529 */       throw new MismatchedSizeException(
/* 1530 */           VecMathI18N.getString("GMatrix18"));
/*      */     }
/*      */     
/* 1533 */     if (this.nRow != U.nRow || this.nRow != U.nCol) {
/* 1534 */       throw new MismatchedSizeException(
/* 1535 */           VecMathI18N.getString("GMatrix25"));
/*      */     }
/*      */     
/* 1538 */     if (this.nRow != W.nRow || this.nCol != W.nCol) {
/* 1539 */       throw new MismatchedSizeException(
/* 1540 */           VecMathI18N.getString("GMatrix26"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1553 */     if (this.nRow == 2 && this.nCol == 2 && 
/* 1554 */       this.values[1][0] == 0.0D) {
/* 1555 */       U.setIdentity();
/* 1556 */       V.setIdentity();
/*      */       
/* 1558 */       if (this.values[0][1] == 0.0D) {
/* 1559 */         return 2;
/*      */       }
/*      */       
/* 1562 */       double[] sinl = new double[1];
/* 1563 */       double[] sinr = new double[1];
/* 1564 */       double[] cosl = new double[1];
/* 1565 */       double[] cosr = new double[1];
/* 1566 */       double[] single_values = new double[2];
/*      */       
/* 1568 */       single_values[0] = this.values[0][0];
/* 1569 */       single_values[1] = this.values[1][1];
/*      */       
/* 1571 */       compute_2X2(this.values[0][0], this.values[0][1], this.values[1][1], single_values, sinl, cosl, sinr, cosr, 0);
/*      */ 
/*      */       
/* 1574 */       update_u(0, U, cosl, sinl);
/* 1575 */       update_v(0, V, cosr, sinr);
/*      */       
/* 1577 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1582 */     return computeSVD(this, U, W, V);
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
/*      */   public final int LUD(GMatrix LU, GVector permutation) {
/* 1606 */     int size = LU.nRow * LU.nCol;
/* 1607 */     double[] temp = new double[size];
/* 1608 */     int[] even_row_exchange = new int[1];
/* 1609 */     int[] row_perm = new int[LU.nRow];
/*      */ 
/*      */     
/* 1612 */     if (this.nRow != this.nCol) {
/* 1613 */       throw new MismatchedSizeException(
/* 1614 */           VecMathI18N.getString("GMatrix19"));
/*      */     }
/*      */     
/* 1617 */     if (this.nRow != LU.nRow) {
/* 1618 */       throw new MismatchedSizeException(
/* 1619 */           VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */     
/* 1622 */     if (this.nCol != LU.nCol) {
/* 1623 */       throw new MismatchedSizeException(
/* 1624 */           VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */     
/* 1627 */     if (LU.nRow != permutation.getSize()) {
/* 1628 */       throw new MismatchedSizeException(
/* 1629 */           VecMathI18N.getString("GMatrix20"));
/*      */     }
/*      */     int i;
/* 1632 */     for (i = 0; i < this.nRow; i++) {
/* 1633 */       for (int j = 0; j < this.nCol; j++) {
/* 1634 */         temp[i * this.nCol + j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1639 */     if (!luDecomposition(LU.nRow, temp, row_perm, even_row_exchange))
/*      */     {
/* 1641 */       throw new SingularMatrixException(
/* 1642 */           VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */     
/* 1645 */     for (i = 0; i < this.nRow; i++) {
/* 1646 */       for (int j = 0; j < this.nCol; j++) {
/* 1647 */         LU.values[i][j] = temp[i * this.nCol + j];
/*      */       }
/*      */     } 
/*      */     
/* 1651 */     for (i = 0; i < LU.nRow; i++) {
/* 1652 */       permutation.values[i] = row_perm[i];
/*      */     }
/*      */     
/* 1655 */     return even_row_exchange[0];
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
/*      */     int l;
/* 1667 */     if (this.nRow < this.nCol) {
/* 1668 */       l = this.nRow;
/*      */     } else {
/* 1670 */       l = this.nCol;
/*      */     }  int i;
/* 1672 */     for (i = 0; i < this.nRow; i++) {
/* 1673 */       for (int j = 0; j < this.nCol; j++) {
/* 1674 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */     
/* 1678 */     for (i = 0; i < l; i++) {
/* 1679 */       this.values[i][i] = scale;
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
/*      */   final void invertGeneral(GMatrix m1) {
/* 1692 */     int size = m1.nRow * m1.nCol;
/* 1693 */     double[] temp = new double[size];
/* 1694 */     double[] result = new double[size];
/* 1695 */     int[] row_perm = new int[m1.nRow];
/* 1696 */     int[] even_row_exchange = new int[1];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1701 */     if (m1.nRow != m1.nCol)
/*      */     {
/* 1703 */       throw new MismatchedSizeException(
/* 1704 */           VecMathI18N.getString("GMatrix22"));
/*      */     }
/*      */     
/*      */     int i;
/* 1708 */     for (i = 0; i < this.nRow; i++) {
/* 1709 */       for (int j = 0; j < this.nCol; j++) {
/* 1710 */         temp[i * this.nCol + j] = m1.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1715 */     if (!luDecomposition(m1.nRow, temp, row_perm, even_row_exchange))
/*      */     {
/* 1717 */       throw new SingularMatrixException(
/* 1718 */           VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */ 
/*      */     
/* 1722 */     for (i = 0; i < size; i++) {
/* 1723 */       result[i] = 0.0D;
/*      */     }
/* 1725 */     for (i = 0; i < this.nCol; i++) {
/* 1726 */       result[i + i * this.nCol] = 1.0D;
/*      */     }
/* 1728 */     luBacksubstitution(m1.nRow, temp, row_perm, result);
/*      */     
/* 1730 */     for (i = 0; i < this.nRow; i++) {
/* 1731 */       for (int j = 0; j < this.nCol; j++) {
/* 1732 */         this.values[i][j] = result[i * this.nCol + j];
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(int dim, double[] matrix0, int[] row_perm, int[] even_row_xchg) {
/* 1757 */     double[] row_scale = new double[dim];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1764 */     int ptr = 0;
/* 1765 */     int rs = 0;
/* 1766 */     even_row_xchg[0] = 1;
/*      */ 
/*      */     
/* 1769 */     int i = dim;
/* 1770 */     while (i-- != 0) {
/* 1771 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1774 */       int k = dim;
/* 1775 */       while (k-- != 0) {
/* 1776 */         double temp = matrix0[ptr++];
/* 1777 */         temp = Math.abs(temp);
/* 1778 */         if (temp > big) {
/* 1779 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1784 */       if (big == 0.0D) {
/* 1785 */         return false;
/*      */       }
/* 1787 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */     
/* 1791 */     int mtx = 0;
/* 1792 */     for (int j = 0; j < dim; j++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1798 */       for (i = 0; i < j; i++) {
/* 1799 */         int target = mtx + dim * i + j;
/* 1800 */         double sum = matrix0[target];
/* 1801 */         int k = i;
/* 1802 */         int p1 = mtx + dim * i;
/* 1803 */         int p2 = mtx + j;
/* 1804 */         while (k-- != 0) {
/* 1805 */           sum -= matrix0[p1] * matrix0[p2];
/* 1806 */           p1++;
/* 1807 */           p2 += dim;
/*      */         } 
/* 1809 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1814 */       double big = 0.0D;
/* 1815 */       int imax = -1;
/* 1816 */       for (i = j; i < dim; i++) {
/* 1817 */         int target = mtx + dim * i + j;
/* 1818 */         double sum = matrix0[target];
/* 1819 */         int k = j;
/* 1820 */         int p1 = mtx + dim * i;
/* 1821 */         int p2 = mtx + j;
/* 1822 */         while (k-- != 0) {
/* 1823 */           sum -= matrix0[p1] * matrix0[p2];
/* 1824 */           p1++;
/* 1825 */           p2 += dim;
/*      */         } 
/* 1827 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1830 */         if ((temp = row_scale[i] * Math.abs(sum)) >= big) {
/* 1831 */           big = temp;
/* 1832 */           imax = i;
/*      */         } 
/*      */       } 
/*      */       
/* 1836 */       if (imax < 0) {
/* 1837 */         throw new RuntimeException(VecMathI18N.getString("GMatrix24"));
/*      */       }
/*      */ 
/*      */       
/* 1841 */       if (j != imax) {
/*      */         
/* 1843 */         int k = dim;
/* 1844 */         int p1 = mtx + dim * imax;
/* 1845 */         int p2 = mtx + dim * j;
/* 1846 */         while (k-- != 0) {
/* 1847 */           double temp = matrix0[p1];
/* 1848 */           matrix0[p1++] = matrix0[p2];
/* 1849 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1853 */         row_scale[imax] = row_scale[j];
/* 1854 */         even_row_xchg[0] = -even_row_xchg[0];
/*      */       } 
/*      */ 
/*      */       
/* 1858 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1861 */       if (matrix0[mtx + dim * j + j] == 0.0D) {
/* 1862 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1866 */       if (j != dim - 1) {
/* 1867 */         double temp = 1.0D / matrix0[mtx + dim * j + j];
/* 1868 */         int target = mtx + dim * (j + 1) + j;
/* 1869 */         i = dim - 1 - j;
/* 1870 */         while (i-- != 0) {
/* 1871 */           matrix0[target] = matrix0[target] * temp;
/* 1872 */           target += dim;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1878 */     return true;
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
/*      */   
/*      */   static void luBacksubstitution(int dim, double[] matrix1, int[] row_perm, double[] matrix2) {
/* 1909 */     int rp = 0;
/*      */ 
/*      */     
/* 1912 */     for (int k = 0; k < dim; k++) {
/*      */       
/* 1914 */       int cv = k;
/* 1915 */       int ii = -1;
/*      */       
/*      */       int i;
/* 1918 */       for (i = 0; i < dim; i++) {
/*      */ 
/*      */         
/* 1921 */         int ip = row_perm[rp + i];
/* 1922 */         double sum = matrix2[cv + dim * ip];
/* 1923 */         matrix2[cv + dim * ip] = matrix2[cv + dim * i];
/* 1924 */         if (ii >= 0) {
/*      */           
/* 1926 */           int rv = i * dim;
/* 1927 */           for (int j = ii; j <= i - 1; j++) {
/* 1928 */             sum -= matrix1[rv + j] * matrix2[cv + dim * j];
/*      */           }
/*      */         }
/* 1931 */         else if (sum != 0.0D) {
/* 1932 */           ii = i;
/*      */         } 
/* 1934 */         matrix2[cv + dim * i] = sum;
/*      */       } 
/*      */ 
/*      */       
/* 1938 */       for (i = 0; i < dim; i++) {
/* 1939 */         int ri = dim - 1 - i;
/* 1940 */         int rv = dim * ri;
/* 1941 */         double tt = 0.0D;
/* 1942 */         for (int j = 1; j <= i; j++) {
/* 1943 */           tt += matrix1[rv + dim - j] * matrix2[cv + dim * (dim - j)];
/*      */         }
/* 1945 */         matrix2[cv + dim * ri] = (matrix2[cv + dim * ri] - tt) / matrix1[rv + ri];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int computeSVD(GMatrix mat, GMatrix U, GMatrix W, GMatrix V) {
/*      */     int eLength, sLength, vecLength;
/* 1958 */     GMatrix tmp = new GMatrix(mat.nRow, mat.nCol);
/* 1959 */     GMatrix u = new GMatrix(mat.nRow, mat.nCol);
/* 1960 */     GMatrix v = new GMatrix(mat.nRow, mat.nCol);
/* 1961 */     GMatrix m = new GMatrix(mat);
/*      */ 
/*      */     
/* 1964 */     if (m.nRow >= m.nCol) {
/* 1965 */       sLength = m.nCol;
/* 1966 */       eLength = m.nCol - 1;
/*      */     } else {
/* 1968 */       sLength = m.nRow;
/* 1969 */       eLength = m.nRow;
/*      */     } 
/*      */     
/* 1972 */     if (m.nRow > m.nCol) {
/* 1973 */       vecLength = m.nRow;
/*      */     } else {
/* 1975 */       vecLength = m.nCol;
/*      */     } 
/* 1977 */     double[] vec = new double[vecLength];
/* 1978 */     double[] single_values = new double[sLength];
/* 1979 */     double[] e = new double[eLength];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1985 */     int rank = 0;
/*      */     
/* 1987 */     U.setIdentity();
/* 1988 */     V.setIdentity();
/*      */     
/* 1990 */     int nr = m.nRow;
/* 1991 */     int nc = m.nCol;
/*      */ 
/*      */     
/* 1994 */     for (int si = 0; si < sLength; si++) {
/*      */ 
/*      */       
/* 1997 */       if (nr > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2004 */         double mag = 0.0D; int k;
/* 2005 */         for (k = 0; k < nr; k++) {
/* 2006 */           mag += m.values[k + si][si] * m.values[k + si][si];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2013 */         mag = Math.sqrt(mag);
/* 2014 */         if (m.values[si][si] == 0.0D) {
/* 2015 */           vec[0] = mag;
/*      */         } else {
/* 2017 */           vec[0] = m.values[si][si] + d_sign(mag, m.values[si][si]);
/*      */         } 
/*      */         
/* 2020 */         for (k = 1; k < nr; k++) {
/* 2021 */           vec[k] = m.values[si + k][si];
/*      */         }
/*      */         
/* 2024 */         double scale = 0.0D;
/* 2025 */         for (k = 0; k < nr; k++)
/*      */         {
/*      */ 
/*      */           
/* 2029 */           scale += vec[k] * vec[k];
/*      */         }
/*      */         
/* 2032 */         scale = 2.0D / scale;
/*      */         
/*      */         int j;
/*      */         
/* 2036 */         for (j = si; j < m.nRow; j++) {
/* 2037 */           for (int n = si; n < m.nRow; n++) {
/* 2038 */             u.values[j][n] = -scale * vec[j - si] * vec[n - si];
/*      */           }
/*      */         } 
/*      */         
/* 2042 */         for (k = si; k < m.nRow; k++) {
/* 2043 */           u.values[k][k] = u.values[k][k] + 1.0D;
/*      */         }
/*      */ 
/*      */         
/* 2047 */         double t = 0.0D;
/* 2048 */         for (k = si; k < m.nRow; k++) {
/* 2049 */           t += u.values[si][k] * m.values[k][si];
/*      */         }
/* 2051 */         m.values[si][si] = t;
/*      */ 
/*      */         
/* 2054 */         for (j = si; j < m.nRow; j++) {
/* 2055 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2056 */             tmp.values[j][n] = 0.0D;
/* 2057 */             for (k = si; k < m.nCol; k++) {
/* 2058 */               tmp.values[j][n] = tmp.values[j][n] + u.values[j][k] * m.values[k][n];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2063 */         for (j = si; j < m.nRow; j++) {
/* 2064 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2065 */             m.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2075 */         for (j = si; j < m.nRow; j++) {
/* 2076 */           for (int n = 0; n < m.nCol; n++) {
/* 2077 */             tmp.values[j][n] = 0.0D;
/* 2078 */             for (k = si; k < m.nCol; k++) {
/* 2079 */               tmp.values[j][n] = tmp.values[j][n] + u.values[j][k] * U.values[k][n];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2084 */         for (j = si; j < m.nRow; j++) {
/* 2085 */           for (int n = 0; n < m.nCol; n++) {
/* 2086 */             U.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2097 */         nr--;
/*      */       } 
/*      */       
/* 2100 */       if (nc > 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2106 */         double mag = 0.0D; int k;
/* 2107 */         for (k = 1; k < nc; k++) {
/* 2108 */           mag += m.values[si][si + k] * m.values[si][si + k];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2116 */         mag = Math.sqrt(mag);
/* 2117 */         if (m.values[si][si + 1] == 0.0D) {
/* 2118 */           vec[0] = mag;
/*      */         } else {
/* 2120 */           vec[0] = m.values[si][si + 1] + 
/* 2121 */             d_sign(mag, m.values[si][si + 1]);
/*      */         } 
/*      */         
/* 2124 */         for (k = 1; k < nc - 1; k++) {
/* 2125 */           vec[k] = m.values[si][si + k + 1];
/*      */         }
/*      */ 
/*      */         
/* 2129 */         double scale = 0.0D;
/* 2130 */         for (k = 0; k < nc - 1; k++)
/*      */         {
/* 2132 */           scale += vec[k] * vec[k];
/*      */         }
/*      */         
/* 2135 */         scale = 2.0D / scale;
/*      */         
/*      */         int j;
/*      */         
/* 2139 */         for (j = si + 1; j < nc; j++) {
/* 2140 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2141 */             v.values[j][n] = -scale * vec[j - si - 1] * vec[n - si - 1];
/*      */           }
/*      */         } 
/*      */         
/* 2145 */         for (k = si + 1; k < m.nCol; k++) {
/* 2146 */           v.values[k][k] = v.values[k][k] + 1.0D;
/*      */         }
/*      */         
/* 2149 */         double t = 0.0D;
/* 2150 */         for (k = si; k < m.nCol; k++) {
/* 2151 */           t += v.values[k][si + 1] * m.values[si][k];
/*      */         }
/* 2153 */         m.values[si][si + 1] = t;
/*      */ 
/*      */         
/* 2156 */         for (j = si + 1; j < m.nRow; j++) {
/* 2157 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2158 */             tmp.values[j][n] = 0.0D;
/* 2159 */             for (k = si + 1; k < m.nCol; k++) {
/* 2160 */               tmp.values[j][n] = tmp.values[j][n] + v.values[k][n] * m.values[j][k];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2165 */         for (j = si + 1; j < m.nRow; j++) {
/* 2166 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2167 */             m.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2178 */         for (j = 0; j < m.nRow; j++) {
/* 2179 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2180 */             tmp.values[j][n] = 0.0D;
/* 2181 */             for (k = si + 1; k < m.nCol; k++) {
/* 2182 */               tmp.values[j][n] = tmp.values[j][n] + v.values[k][n] * V.values[j][k];
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2190 */         for (j = 0; j < m.nRow; j++) {
/* 2191 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2192 */             V.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2201 */         nc--;
/*      */       } 
/*      */     } 
/*      */     int i;
/* 2205 */     for (i = 0; i < sLength; i++) {
/* 2206 */       single_values[i] = m.values[i][i];
/*      */     }
/*      */     
/* 2209 */     for (i = 0; i < eLength; i++) {
/* 2210 */       e[i] = m.values[i][i + 1];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2223 */     if (m.nRow == 2 && m.nCol == 2) {
/* 2224 */       double[] cosl = new double[1];
/* 2225 */       double[] cosr = new double[1];
/* 2226 */       double[] sinl = new double[1];
/* 2227 */       double[] sinr = new double[1];
/*      */       
/* 2229 */       compute_2X2(single_values[0], e[0], single_values[1], single_values, sinl, cosl, sinr, cosr, 0);
/*      */ 
/*      */       
/* 2232 */       update_u(0, U, cosl, sinl);
/* 2233 */       update_v(0, V, cosr, sinr);
/*      */       
/* 2235 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/* 2239 */     compute_qr(0, e.length - 1, single_values, e, U, V);
/*      */ 
/*      */     
/* 2242 */     rank = single_values.length;
/*      */ 
/*      */ 
/*      */     
/* 2246 */     return rank;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void compute_qr(int start, int end, double[] s, double[] e, GMatrix u, GMatrix v) {
/* 2255 */     double[] cosl = new double[1];
/* 2256 */     double[] cosr = new double[1];
/* 2257 */     double[] sinl = new double[1];
/* 2258 */     double[] sinr = new double[1];
/* 2259 */     GMatrix m = new GMatrix(u.nCol, v.nRow);
/*      */     
/* 2261 */     int MAX_INTERATIONS = 2;
/* 2262 */     double CONVERGE_TOL = 4.89E-15D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2286 */     double c_b48 = 1.0D;
/* 2287 */     double c_b71 = -1.0D;
/* 2288 */     boolean converged = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2293 */     double f = 0.0D;
/* 2294 */     double g = 0.0D;
/*      */     
/* 2296 */     for (int k = 0; k < 2 && !converged; k++) {
/* 2297 */       int j; for (j = start; j <= end; j++) {
/*      */ 
/*      */         
/* 2300 */         if (j == start) {
/* 2301 */           int sl; if (e.length == s.length) {
/* 2302 */             sl = end;
/*      */           } else {
/* 2304 */             sl = end + 1;
/*      */           } 
/* 2306 */           double shift = compute_shift(s[sl - 1], e[end], s[sl]);
/*      */ 
/*      */           
/* 2309 */           f = (Math.abs(s[j]) - shift) * (d_sign(c_b48, s[j]) + shift / s[j]);
/* 2310 */           g = e[j];
/*      */         } 
/*      */         
/* 2313 */         double r = compute_rot(f, g, sinr, cosr);
/* 2314 */         if (j != start) {
/* 2315 */           e[j - 1] = r;
/*      */         }
/* 2317 */         f = cosr[0] * s[j] + sinr[0] * e[j];
/* 2318 */         e[j] = cosr[0] * e[j] - sinr[0] * s[j];
/* 2319 */         g = sinr[0] * s[j + 1];
/* 2320 */         s[j + 1] = cosr[0] * s[j + 1];
/*      */ 
/*      */         
/* 2323 */         update_v(j, v, cosr, sinr);
/*      */ 
/*      */ 
/*      */         
/* 2327 */         r = compute_rot(f, g, sinl, cosl);
/* 2328 */         s[j] = r;
/* 2329 */         f = cosl[0] * e[j] + sinl[0] * s[j + 1];
/* 2330 */         s[j + 1] = cosl[0] * s[j + 1] - sinl[0] * e[j];
/*      */         
/* 2332 */         if (j < end) {
/*      */           
/* 2334 */           g = sinl[0] * e[j + 1];
/* 2335 */           e[j + 1] = cosl[0] * e[j + 1];
/*      */         } 
/*      */ 
/*      */         
/* 2339 */         update_u(j, u, cosl, sinl);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2345 */       if (s.length == e.length) {
/* 2346 */         double r = compute_rot(f, g, sinr, cosr);
/* 2347 */         f = cosr[0] * s[j] + sinr[0] * e[j];
/* 2348 */         e[j] = cosr[0] * e[j] - sinr[0] * s[j];
/* 2349 */         s[j + 1] = cosr[0] * s[j + 1];
/*      */         
/* 2351 */         update_v(j, v, cosr, sinr);
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
/*      */       
/* 2364 */       while (end - start > 1 && Math.abs(e[end]) < 4.89E-15D) {
/* 2365 */         end--;
/*      */       }
/*      */ 
/*      */       
/* 2369 */       for (int n = end - 2; n > start; n--) {
/* 2370 */         if (Math.abs(e[n]) < 4.89E-15D) {
/* 2371 */           compute_qr(n + 1, end, s, e, u, v);
/* 2372 */           end = n - 1;
/*      */ 
/*      */           
/* 2375 */           while (end - start > 1 && 
/* 2376 */             Math.abs(e[end]) < 4.89E-15D) {
/* 2377 */             end--;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2385 */       if (end - start <= 1 && Math.abs(e[start + 1]) < 4.89E-15D) {
/* 2386 */         converged = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2396 */     if (Math.abs(e[1]) < 4.89E-15D) {
/* 2397 */       compute_2X2(s[start], e[start], s[start + 1], s, sinl, cosl, sinr, cosr, 0);
/*      */       
/* 2399 */       e[start] = 0.0D;
/* 2400 */       e[start + 1] = 0.0D;
/*      */     } 
/*      */ 
/*      */     
/* 2404 */     int i = start;
/* 2405 */     update_u(i, u, cosl, sinl);
/* 2406 */     update_v(i, v, cosr, sinr);
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
/*      */   private static void print_se(double[] s, double[] e) {
/* 2418 */     System.out.println("\ns =" + s[0] + " " + s[1] + " " + s[2]);
/* 2419 */     System.out.println("e =" + e[0] + " " + e[1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v(int index, GMatrix v, double[] cosr, double[] sinr) {
/* 2427 */     for (int j = 0; j < v.nRow; j++) {
/* 2428 */       double vtemp = v.values[j][index];
/* 2429 */       v.values[j][index] = cosr[0] * vtemp + sinr[0] * v.values[j][index + 1];
/*      */       
/* 2431 */       v.values[j][index + 1] = -sinr[0] * vtemp + cosr[0] * v.values[j][index + 1];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void chase_up(double[] s, double[] e, int k, GMatrix v) {
/* 2438 */     double[] cosr = new double[1];
/* 2439 */     double[] sinr = new double[1];
/*      */     
/* 2441 */     GMatrix t = new GMatrix(v.nRow, v.nCol);
/* 2442 */     GMatrix m = new GMatrix(v.nRow, v.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2454 */     double f = e[k];
/* 2455 */     double g = s[k];
/*      */     int i;
/* 2457 */     for (i = k; i > 0; i--) {
/* 2458 */       double r = compute_rot(f, g, sinr, cosr);
/* 2459 */       f = -e[i - 1] * sinr[0];
/* 2460 */       g = s[i - 1];
/* 2461 */       s[i] = r;
/* 2462 */       e[i - 1] = e[i - 1] * cosr[0];
/* 2463 */       update_v_split(i, k + 1, v, cosr, sinr, t, m);
/*      */     } 
/*      */     
/* 2466 */     s[i + 1] = compute_rot(f, g, sinr, cosr);
/* 2467 */     update_v_split(i, k + 1, v, cosr, sinr, t, m);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void chase_across(double[] s, double[] e, int k, GMatrix u) {
/* 2472 */     double[] cosl = new double[1];
/* 2473 */     double[] sinl = new double[1];
/*      */     
/* 2475 */     GMatrix t = new GMatrix(u.nRow, u.nCol);
/* 2476 */     GMatrix m = new GMatrix(u.nRow, u.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2488 */     double g = e[k];
/* 2489 */     double f = s[k + 1];
/*      */     int i;
/* 2491 */     for (i = k; i < u.nCol - 2; i++) {
/* 2492 */       double r = compute_rot(f, g, sinl, cosl);
/* 2493 */       g = -e[i + 1] * sinl[0];
/* 2494 */       f = s[i + 2];
/* 2495 */       s[i + 1] = r;
/* 2496 */       e[i + 1] = e[i + 1] * cosl[0];
/* 2497 */       update_u_split(k, i + 1, u, cosl, sinl, t, m);
/*      */     } 
/*      */     
/* 2500 */     s[i + 1] = compute_rot(f, g, sinl, cosl);
/* 2501 */     update_u_split(k, i + 1, u, cosl, sinl, t, m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v_split(int topr, int bottomr, GMatrix v, double[] cosr, double[] sinr, GMatrix t, GMatrix m) {
/* 2510 */     for (int j = 0; j < v.nRow; j++) {
/* 2511 */       double vtemp = v.values[j][topr];
/* 2512 */       v.values[j][topr] = cosr[0] * vtemp - sinr[0] * v.values[j][bottomr];
/* 2513 */       v.values[j][bottomr] = sinr[0] * vtemp + cosr[0] * v.values[j][bottomr];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2527 */     System.out.println("topr    =" + topr);
/* 2528 */     System.out.println("bottomr =" + bottomr);
/* 2529 */     System.out.println("cosr =" + cosr[0]);
/* 2530 */     System.out.println("sinr =" + sinr[0]);
/* 2531 */     System.out.println("\nm =");
/* 2532 */     checkMatrix(m);
/* 2533 */     System.out.println("\nv =");
/* 2534 */     checkMatrix(t);
/* 2535 */     m.mul(m, t);
/* 2536 */     System.out.println("\nt*m =");
/* 2537 */     checkMatrix(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u_split(int topr, int bottomr, GMatrix u, double[] cosl, double[] sinl, GMatrix t, GMatrix m) {
/* 2546 */     for (int j = 0; j < u.nCol; j++) {
/* 2547 */       double utemp = u.values[topr][j];
/* 2548 */       u.values[topr][j] = cosl[0] * utemp - sinl[0] * u.values[bottomr][j];
/* 2549 */       u.values[bottomr][j] = sinl[0] * utemp + cosl[0] * u.values[bottomr][j];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2562 */     System.out.println("\nm=");
/* 2563 */     checkMatrix(m);
/* 2564 */     System.out.println("\nu=");
/* 2565 */     checkMatrix(t);
/* 2566 */     m.mul(t, m);
/* 2567 */     System.out.println("\nt*m=");
/* 2568 */     checkMatrix(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u(int index, GMatrix u, double[] cosl, double[] sinl) {
/* 2576 */     for (int j = 0; j < u.nCol; j++) {
/* 2577 */       double utemp = u.values[index][j];
/* 2578 */       u.values[index][j] = cosl[0] * utemp + sinl[0] * u.values[index + 1][j];
/*      */       
/* 2580 */       u.values[index + 1][j] = -sinl[0] * utemp + cosl[0] * u.values[index + 1][j];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void print_m(GMatrix m, GMatrix u, GMatrix v) {
/* 2586 */     GMatrix mtmp = new GMatrix(m.nCol, m.nRow);
/*      */     
/* 2588 */     mtmp.mul(u, mtmp);
/* 2589 */     mtmp.mul(mtmp, v);
/* 2590 */     System.out.println("\n m = \n" + toString(mtmp));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toString(GMatrix m) {
/* 2596 */     StringBuffer buffer = new StringBuffer(m.nRow * m.nCol * 8);
/*      */ 
/*      */     
/* 2599 */     for (int i = 0; i < m.nRow; i++) {
/* 2600 */       for (int j = 0; j < m.nCol; j++) {
/* 2601 */         if (Math.abs(m.values[i][j]) < 1.0E-9D) {
/* 2602 */           buffer.append("0.0000 ");
/*      */         } else {
/* 2604 */           buffer.append(m.values[i][j]).append(" ");
/*      */         } 
/*      */       } 
/* 2607 */       buffer.append("\n");
/*      */     } 
/* 2609 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void print_svd(double[] s, double[] e, GMatrix u, GMatrix v) {
/* 2615 */     GMatrix mtmp = new GMatrix(u.nCol, v.nRow);
/*      */     
/* 2617 */     System.out.println(" \ns = "); int i;
/* 2618 */     for (i = 0; i < s.length; i++) {
/* 2619 */       System.out.println(" " + s[i]);
/*      */     }
/*      */     
/* 2622 */     System.out.println(" \ne = ");
/* 2623 */     for (i = 0; i < e.length; i++) {
/* 2624 */       System.out.println(" " + e[i]);
/*      */     }
/*      */     
/* 2627 */     System.out.println(" \nu  = \n" + u);
/* 2628 */     System.out.println(" \nv  = \n" + v);
/*      */     
/* 2630 */     mtmp.setIdentity();
/* 2631 */     for (i = 0; i < s.length; i++) {
/* 2632 */       mtmp.values[i][i] = s[i];
/*      */     }
/* 2634 */     for (i = 0; i < e.length; i++) {
/* 2635 */       mtmp.values[i][i + 1] = e[i];
/*      */     }
/* 2637 */     System.out.println(" \nm  = \n" + mtmp);
/*      */     
/* 2639 */     mtmp.mulTransposeLeft(u, mtmp);
/* 2640 */     mtmp.mulTransposeRight(mtmp, v);
/*      */     
/* 2642 */     System.out.println(" \n u.transpose*m*v.transpose  = \n" + mtmp);
/*      */   }
/*      */ 
/*      */   
/*      */   static double max(double a, double b) {
/* 2647 */     if (a > b) {
/* 2648 */       return a;
/*      */     }
/* 2650 */     return b;
/*      */   }
/*      */   
/*      */   static double min(double a, double b) {
/* 2654 */     if (a < b) {
/* 2655 */       return a;
/*      */     }
/* 2657 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_shift(double f, double g, double h) {
/* 2665 */     double ssmin, fa = Math.abs(f);
/* 2666 */     double ga = Math.abs(g);
/* 2667 */     double ha = Math.abs(h);
/* 2668 */     double fhmn = min(fa, ha);
/* 2669 */     double fhmx = max(fa, ha);
/*      */     
/* 2671 */     if (fhmn == 0.0D) {
/* 2672 */       ssmin = 0.0D;
/* 2673 */       if (fhmx != 0.0D)
/*      */       {
/* 2675 */         double d__1 = min(fhmx, ga) / max(fhmx, ga);
/*      */       }
/*      */     }
/* 2678 */     else if (ga < fhmx) {
/* 2679 */       double as = fhmn / fhmx + 1.0D;
/* 2680 */       double at = (fhmx - fhmn) / fhmx;
/* 2681 */       double d__1 = ga / fhmx;
/* 2682 */       double au = d__1 * d__1;
/* 2683 */       double c = 2.0D / (Math.sqrt(as * as + au) + Math.sqrt(at * at + au));
/* 2684 */       ssmin = fhmn * c;
/*      */     } else {
/* 2686 */       double au = fhmx / ga;
/* 2687 */       if (au == 0.0D) {
/* 2688 */         ssmin = fhmn * fhmx / ga;
/*      */       } else {
/* 2690 */         double as = fhmn / fhmx + 1.0D;
/* 2691 */         double at = (fhmx - fhmn) / fhmx;
/* 2692 */         double d__1 = as * au;
/* 2693 */         double d__2 = at * au;
/*      */         
/* 2695 */         double c = 1.0D / (Math.sqrt(d__1 * d__1 + 1.0D) + Math.sqrt(d__2 * d__2 + 1.0D));
/* 2696 */         ssmin = fhmn * c * au;
/* 2697 */         ssmin += ssmin;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2702 */     return ssmin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compute_2X2(double f, double g, double h, double[] single_values, double[] snl, double[] csl, double[] snr, double[] csr, int index) {
/* 2709 */     double c_b3 = 2.0D;
/* 2710 */     double c_b4 = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2722 */     double ssmax = single_values[0];
/* 2723 */     double ssmin = single_values[1];
/* 2724 */     double clt = 0.0D;
/* 2725 */     double crt = 0.0D;
/* 2726 */     double slt = 0.0D;
/* 2727 */     double srt = 0.0D;
/* 2728 */     double tsign = 0.0D;
/*      */     
/* 2730 */     double ft = f;
/* 2731 */     double fa = Math.abs(ft);
/* 2732 */     double ht = h;
/* 2733 */     double ha = Math.abs(h);
/*      */     
/* 2735 */     int pmax = 1;
/* 2736 */     boolean swap = (ha > fa);
/*      */     
/* 2738 */     if (swap) {
/* 2739 */       pmax = 3;
/* 2740 */       double temp = ft;
/* 2741 */       ft = ht;
/* 2742 */       ht = temp;
/* 2743 */       temp = fa;
/* 2744 */       fa = ha;
/* 2745 */       ha = temp;
/*      */     } 
/*      */ 
/*      */     
/* 2749 */     double gt = g;
/* 2750 */     double ga = Math.abs(gt);
/* 2751 */     if (ga == 0.0D) {
/* 2752 */       single_values[1] = ha;
/* 2753 */       single_values[0] = fa;
/* 2754 */       clt = 1.0D;
/* 2755 */       crt = 1.0D;
/* 2756 */       slt = 0.0D;
/* 2757 */       srt = 0.0D;
/*      */     } else {
/* 2759 */       boolean gasmal = true;
/* 2760 */       if (ga > fa) {
/* 2761 */         pmax = 2;
/* 2762 */         if (fa / ga < 1.0E-10D) {
/* 2763 */           gasmal = false;
/* 2764 */           ssmax = ga;
/*      */           
/* 2766 */           if (ha > 1.0D) {
/* 2767 */             ssmin = fa / ga / ha;
/*      */           } else {
/* 2769 */             ssmin = fa / ga * ha;
/*      */           } 
/* 2771 */           clt = 1.0D;
/* 2772 */           slt = ht / gt;
/* 2773 */           srt = 1.0D;
/* 2774 */           crt = ft / gt;
/*      */         } 
/*      */       } 
/* 2777 */       if (gasmal) {
/* 2778 */         double l, r, d = fa - ha;
/* 2779 */         if (d == fa) {
/*      */           
/* 2781 */           l = 1.0D;
/*      */         } else {
/* 2783 */           l = d / fa;
/*      */         } 
/*      */         
/* 2786 */         double m = gt / ft;
/* 2787 */         double t = 2.0D - l;
/* 2788 */         double mm = m * m;
/* 2789 */         double tt = t * t;
/* 2790 */         double s = Math.sqrt(tt + mm);
/*      */         
/* 2792 */         if (l == 0.0D) {
/* 2793 */           r = Math.abs(m);
/*      */         } else {
/* 2795 */           r = Math.sqrt(l * l + mm);
/*      */         } 
/*      */         
/* 2798 */         double a = (s + r) * 0.5D;
/* 2799 */         if (ga > fa) {
/* 2800 */           pmax = 2;
/* 2801 */           if (fa / ga < 1.0E-10D) {
/* 2802 */             gasmal = false;
/* 2803 */             ssmax = ga;
/* 2804 */             if (ha > 1.0D) {
/* 2805 */               ssmin = fa / ga / ha;
/*      */             } else {
/* 2807 */               ssmin = fa / ga * ha;
/*      */             } 
/* 2809 */             clt = 1.0D;
/* 2810 */             slt = ht / gt;
/* 2811 */             srt = 1.0D;
/* 2812 */             crt = ft / gt;
/*      */           } 
/*      */         } 
/* 2815 */         if (gasmal) {
/* 2816 */           d = fa - ha;
/* 2817 */           if (d == fa) {
/* 2818 */             l = 1.0D;
/*      */           } else {
/* 2820 */             l = d / fa;
/*      */           } 
/*      */           
/* 2823 */           m = gt / ft;
/* 2824 */           t = 2.0D - l;
/*      */           
/* 2826 */           mm = m * m;
/* 2827 */           tt = t * t;
/* 2828 */           s = Math.sqrt(tt + mm);
/*      */           
/* 2830 */           if (l == 0.0D) {
/* 2831 */             r = Math.abs(m);
/*      */           } else {
/* 2833 */             r = Math.sqrt(l * l + mm);
/*      */           } 
/*      */           
/* 2836 */           a = (s + r) * 0.5D;
/* 2837 */           ssmin = ha / a;
/* 2838 */           ssmax = fa * a;
/*      */           
/* 2840 */           if (mm == 0.0D) {
/* 2841 */             if (l == 0.0D) {
/* 2842 */               t = d_sign(c_b3, ft) * d_sign(c_b4, gt);
/*      */             } else {
/* 2844 */               t = gt / d_sign(d, ft) + m / t;
/*      */             } 
/*      */           } else {
/* 2847 */             t = (m / (s + t) + m / (r + l)) * (a + 1.0D);
/*      */           } 
/*      */           
/* 2850 */           l = Math.sqrt(t * t + 4.0D);
/* 2851 */           crt = 2.0D / l;
/* 2852 */           srt = t / l;
/* 2853 */           clt = (crt + srt * m) / a;
/* 2854 */           slt = ht / ft * srt / a;
/*      */         } 
/*      */       } 
/* 2857 */       if (swap) {
/* 2858 */         csl[0] = srt;
/* 2859 */         snl[0] = crt;
/* 2860 */         csr[0] = slt;
/* 2861 */         snr[0] = clt;
/*      */       } else {
/* 2863 */         csl[0] = clt;
/* 2864 */         snl[0] = slt;
/* 2865 */         csr[0] = crt;
/* 2866 */         snr[0] = srt;
/*      */       } 
/*      */       
/* 2869 */       if (pmax == 1)
/*      */       {
/* 2871 */         tsign = d_sign(c_b4, csr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, f);
/*      */       }
/* 2873 */       if (pmax == 2)
/*      */       {
/* 2875 */         tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, g);
/*      */       }
/* 2877 */       if (pmax == 3)
/*      */       {
/* 2879 */         tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, snl[0]) * d_sign(c_b4, h);
/*      */       }
/*      */       
/* 2882 */       single_values[index] = d_sign(ssmax, tsign);
/* 2883 */       double d__1 = tsign * d_sign(c_b4, f) * d_sign(c_b4, h);
/* 2884 */       single_values[index + 1] = d_sign(ssmin, d__1);
/*      */     } 
/*      */     
/* 2887 */     return 0;
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
/*      */   static double compute_rot(double f, double g, double[] sin, double[] cos) {
/* 2899 */     double cs, sn, r, safmn2 = 2.002083095183101E-146D;
/* 2900 */     double safmx2 = 4.994797680505588E145D;
/*      */     
/* 2902 */     if (g == 0.0D) {
/* 2903 */       cs = 1.0D;
/* 2904 */       sn = 0.0D;
/* 2905 */       r = f;
/* 2906 */     } else if (f == 0.0D) {
/* 2907 */       cs = 0.0D;
/* 2908 */       sn = 1.0D;
/* 2909 */       r = g;
/*      */     } else {
/* 2911 */       double f1 = f;
/* 2912 */       double g1 = g;
/* 2913 */       double scale = max(Math.abs(f1), Math.abs(g1));
/* 2914 */       if (scale >= 4.994797680505588E145D) {
/* 2915 */         int count = 0;
/* 2916 */         while (scale >= 4.994797680505588E145D) {
/* 2917 */           count++;
/* 2918 */           f1 *= 2.002083095183101E-146D;
/* 2919 */           g1 *= 2.002083095183101E-146D;
/* 2920 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2922 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2923 */         cs = f1 / r;
/* 2924 */         sn = g1 / r;
/* 2925 */         int i__1 = count;
/* 2926 */         for (int i = 1; i <= count; i++) {
/* 2927 */           r *= 4.994797680505588E145D;
/*      */         }
/* 2929 */       } else if (scale <= 2.002083095183101E-146D) {
/* 2930 */         int count = 0;
/* 2931 */         while (scale <= 2.002083095183101E-146D) {
/* 2932 */           count++;
/* 2933 */           f1 *= 4.994797680505588E145D;
/* 2934 */           g1 *= 4.994797680505588E145D;
/* 2935 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2937 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2938 */         cs = f1 / r;
/* 2939 */         sn = g1 / r;
/* 2940 */         int i__1 = count;
/* 2941 */         for (int i = 1; i <= count; i++) {
/* 2942 */           r *= 2.002083095183101E-146D;
/*      */         }
/*      */       } else {
/* 2945 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2946 */         cs = f1 / r;
/* 2947 */         sn = g1 / r;
/*      */       } 
/* 2949 */       if (Math.abs(f) > Math.abs(g) && cs < 0.0D) {
/* 2950 */         cs = -cs;
/* 2951 */         sn = -sn;
/* 2952 */         r = -r;
/*      */       } 
/*      */     } 
/* 2955 */     sin[0] = sn;
/* 2956 */     cos[0] = cs;
/* 2957 */     return r;
/*      */   }
/*      */ 
/*      */   
/*      */   static double d_sign(double a, double b) {
/* 2962 */     double x = (a >= 0.0D) ? a : -a;
/* 2963 */     return (b >= 0.0D) ? x : -x;
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
/* 2976 */     GMatrix m1 = null;
/*      */     try {
/* 2978 */       m1 = (GMatrix)super.clone();
/* 2979 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 2981 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 2985 */     m1.values = new double[this.nRow][this.nCol];
/* 2986 */     for (int i = 0; i < this.nRow; i++) {
/* 2987 */       if (this.nCol >= 0) System.arraycopy(this.values[i], 0, m1.values[i], 0, this.nCol);
/*      */     
/*      */     } 
/* 2990 */     return m1;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\GMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */