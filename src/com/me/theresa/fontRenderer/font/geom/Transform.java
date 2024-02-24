/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.log.FastTrig;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Transform
/*     */ {
/*     */   private float[] matrixPosition;
/*     */   
/*     */   public Transform() {
/*  12 */     this.matrixPosition = new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
/*     */   }
/*     */ 
/*     */   
/*     */   public Transform(Transform other) {
/*  17 */     this.matrixPosition = new float[9];
/*  18 */     System.arraycopy(other.matrixPosition, 0, this.matrixPosition, 0, 9);
/*     */   }
/*     */ 
/*     */   
/*     */   public Transform(Transform t1, Transform t2) {
/*  23 */     this(t1);
/*  24 */     concatenate(t2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Transform(float[] matrixPosition) {
/*  29 */     if (matrixPosition.length != 6) {
/*  30 */       throw new RuntimeException("The parameter must be a float array of length 6.");
/*     */     }
/*  32 */     this.matrixPosition = new float[] { matrixPosition[0], matrixPosition[1], matrixPosition[2], matrixPosition[3], matrixPosition[4], matrixPosition[5], 0.0F, 0.0F, 1.0F };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(float point00, float point01, float point02, float point10, float point11, float point12) {
/*  39 */     this.matrixPosition = new float[] { point00, point01, point02, point10, point11, point12, 0.0F, 0.0F, 1.0F };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void transform(float[] source, int sourceOffset, float[] destination, int destOffset, int numberOfPoints) {
/*  45 */     float[] result = (source == destination) ? new float[numberOfPoints << 1] : destination;
/*     */     int i;
/*  47 */     for (i = 0; i < numberOfPoints << 1; i += 2) {
/*  48 */       for (int j = 0; j < 6; j += 3) {
/*  49 */         result[i + j / 3] = source[i + sourceOffset] * this.matrixPosition[j] + source[i + sourceOffset + 1] * this.matrixPosition[j + 1] + 1.0F * this.matrixPosition[j + 2];
/*     */       }
/*     */     } 
/*     */     
/*  53 */     if (source == destination)
/*     */     {
/*  55 */       for (i = 0; i < numberOfPoints << 1; i += 2) {
/*  56 */         destination[i + destOffset] = result[i];
/*  57 */         destination[i + destOffset + 1] = result[i + 1];
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Transform concatenate(Transform tx) {
/*  64 */     float[] mp = new float[9];
/*  65 */     float n00 = this.matrixPosition[0] * tx.matrixPosition[0] + this.matrixPosition[1] * tx.matrixPosition[3];
/*  66 */     float n01 = this.matrixPosition[0] * tx.matrixPosition[1] + this.matrixPosition[1] * tx.matrixPosition[4];
/*  67 */     float n02 = this.matrixPosition[0] * tx.matrixPosition[2] + this.matrixPosition[1] * tx.matrixPosition[5] + this.matrixPosition[2];
/*  68 */     float n10 = this.matrixPosition[3] * tx.matrixPosition[0] + this.matrixPosition[4] * tx.matrixPosition[3];
/*  69 */     float n11 = this.matrixPosition[3] * tx.matrixPosition[1] + this.matrixPosition[4] * tx.matrixPosition[4];
/*  70 */     float n12 = this.matrixPosition[3] * tx.matrixPosition[2] + this.matrixPosition[4] * tx.matrixPosition[5] + this.matrixPosition[5];
/*  71 */     mp[0] = n00;
/*  72 */     mp[1] = n01;
/*  73 */     mp[2] = n02;
/*  74 */     mp[3] = n10;
/*  75 */     mp[4] = n11;
/*  76 */     mp[5] = n12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.matrixPosition = mp;
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  91 */     String result = "Transform[[" + this.matrixPosition[0] + "," + this.matrixPosition[1] + "," + this.matrixPosition[2] + "][" + this.matrixPosition[3] + "," + this.matrixPosition[4] + "," + this.matrixPosition[5] + "][" + this.matrixPosition[6] + "," + this.matrixPosition[7] + "," + this.matrixPosition[8] + "]]";
/*     */ 
/*     */ 
/*     */     
/*  95 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getMatrixPosition() {
/* 100 */     return this.matrixPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Transform createRotateTransform(float angle) {
/* 105 */     return new Transform((float)FastTrig.cos(angle), -((float)FastTrig.sin(angle)), 0.0F, (float)FastTrig.sin(angle), (float)FastTrig.cos(angle), 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Transform createRotateTransform(float angle, float x, float y) {
/* 110 */     Transform temp = createRotateTransform(angle);
/* 111 */     float sinAngle = temp.matrixPosition[3];
/* 112 */     float oneMinusCosAngle = 1.0F - temp.matrixPosition[4];
/* 113 */     temp.matrixPosition[2] = x * oneMinusCosAngle + y * sinAngle;
/* 114 */     temp.matrixPosition[5] = y * oneMinusCosAngle - x * sinAngle;
/*     */     
/* 116 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Transform createTranslateTransform(float xOffset, float yOffset) {
/* 121 */     return new Transform(1.0F, 0.0F, xOffset, 0.0F, 1.0F, yOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Transform createScaleTransform(float xScale, float yScale) {
/* 126 */     return new Transform(xScale, 0.0F, 0.0F, 0.0F, yScale, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2f transform(Vector2f pt) {
/* 131 */     float[] in = { pt.x, pt.y };
/* 132 */     float[] out = new float[2];
/*     */     
/* 134 */     transform(in, 0, out, 0, 1);
/*     */     
/* 136 */     return new Vector2f(out[0], out[1]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Transform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */