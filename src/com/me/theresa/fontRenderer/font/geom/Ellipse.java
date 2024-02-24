/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.log.FastTrig;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ellipse
/*     */   extends Shape
/*     */ {
/*     */   protected static final int DEFAULT_SEGMENT_COUNT = 50;
/*     */   private final int segmentCount;
/*     */   private float radius1;
/*     */   private float radius2;
/*     */   
/*     */   public Ellipse(float centerPointX, float centerPointY, float radius1, float radius2) {
/*  21 */     this(centerPointX, centerPointY, radius1, radius2, 50);
/*     */   }
/*     */ 
/*     */   
/*     */   public Ellipse(float centerPointX, float centerPointY, float radius1, float radius2, int segmentCount) {
/*  26 */     this.x = centerPointX - radius1;
/*  27 */     this.y = centerPointY - radius2;
/*  28 */     this.radius1 = radius1;
/*  29 */     this.radius2 = radius2;
/*  30 */     this.segmentCount = segmentCount;
/*  31 */     checkPoints();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadii(float radius1, float radius2) {
/*  36 */     setRadius1(radius1);
/*  37 */     setRadius2(radius2);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRadius1() {
/*  42 */     return this.radius1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadius1(float radius1) {
/*  47 */     if (radius1 != this.radius1) {
/*  48 */       this.radius1 = radius1;
/*  49 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRadius2() {
/*  55 */     return this.radius2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadius2(float radius2) {
/*  60 */     if (radius2 != this.radius2) {
/*  61 */       this.radius2 = radius2;
/*  62 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/*  68 */     ArrayList<Float> tempPoints = new ArrayList();
/*     */     
/*  70 */     this.maxX = -1.4E-45F;
/*  71 */     this.maxY = -1.4E-45F;
/*  72 */     this.minX = Float.MAX_VALUE;
/*  73 */     this.minY = Float.MAX_VALUE;
/*     */     
/*  75 */     float start = 0.0F;
/*  76 */     float end = 359.0F;
/*     */     
/*  78 */     float cx = this.x + this.radius1;
/*  79 */     float cy = this.y + this.radius2;
/*     */     
/*  81 */     int step = 360 / this.segmentCount;
/*     */     float a;
/*  83 */     for (a = start; a <= end + step; a += step) {
/*  84 */       float ang = a;
/*  85 */       if (ang > end) {
/*  86 */         ang = end;
/*     */       }
/*  88 */       float newX = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * this.radius1);
/*  89 */       float newY = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * this.radius2);
/*     */       
/*  91 */       if (newX > this.maxX) {
/*  92 */         this.maxX = newX;
/*     */       }
/*  94 */       if (newY > this.maxY) {
/*  95 */         this.maxY = newY;
/*     */       }
/*  97 */       if (newX < this.minX) {
/*  98 */         this.minX = newX;
/*     */       }
/* 100 */       if (newY < this.minY) {
/* 101 */         this.minY = newY;
/*     */       }
/*     */       
/* 104 */       tempPoints.add(new Float(newX));
/* 105 */       tempPoints.add(new Float(newY));
/*     */     } 
/* 107 */     this.points = new float[tempPoints.size()];
/* 108 */     for (int i = 0; i < this.points.length; i++) {
/* 109 */       this.points[i] = ((Float)tempPoints.get(i)).floatValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 115 */     checkPoints();
/*     */     
/* 117 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 119 */     float[] result = new float[this.points.length];
/* 120 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 121 */     resultPolygon.points = result;
/* 122 */     resultPolygon.checkPoints();
/*     */     
/* 124 */     return resultPolygon;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void findCenter() {
/* 129 */     this.center = new float[2];
/* 130 */     this.center[0] = this.x + this.radius1;
/* 131 */     this.center[1] = this.y + this.radius2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void calculateRadius() {
/* 136 */     this.boundingCircleRadius = (this.radius1 > this.radius2) ? this.radius1 : this.radius2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Ellipse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */