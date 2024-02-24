/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public class Polygon
/*     */   extends Shape
/*     */ {
/*     */   private boolean allowDups = false;
/*     */   private boolean closed = true;
/*     */   
/*     */   public Polygon(float[] points) {
/*  13 */     int length = points.length;
/*     */     
/*  15 */     this.points = new float[length];
/*  16 */     this.maxX = -1.4E-45F;
/*  17 */     this.maxY = -1.4E-45F;
/*  18 */     this.minX = Float.MAX_VALUE;
/*  19 */     this.minY = Float.MAX_VALUE;
/*  20 */     this.x = Float.MAX_VALUE;
/*  21 */     this.y = Float.MAX_VALUE;
/*     */     
/*  23 */     for (int i = 0; i < length; i++) {
/*  24 */       this.points[i] = points[i];
/*  25 */       if (i % 2 == 0) {
/*  26 */         if (points[i] > this.maxX) {
/*  27 */           this.maxX = points[i];
/*     */         }
/*  29 */         if (points[i] < this.minX) {
/*  30 */           this.minX = points[i];
/*     */         }
/*  32 */         if (points[i] < this.x) {
/*  33 */           this.x = points[i];
/*     */         }
/*     */       } else {
/*  36 */         if (points[i] > this.maxY) {
/*  37 */           this.maxY = points[i];
/*     */         }
/*  39 */         if (points[i] < this.minY) {
/*  40 */           this.minY = points[i];
/*     */         }
/*  42 */         if (points[i] < this.y) {
/*  43 */           this.y = points[i];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  48 */     findCenter();
/*  49 */     calculateRadius();
/*  50 */     this.pointsDirty = true;
/*     */   }
/*     */   
/*     */   public Polygon() {
/*  54 */     this.points = new float[0];
/*  55 */     this.maxX = -1.4E-45F;
/*  56 */     this.maxY = -1.4E-45F;
/*  57 */     this.minX = Float.MAX_VALUE;
/*  58 */     this.minY = Float.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllowDuplicatePoints(boolean allowDups) {
/*  63 */     this.allowDups = allowDups;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPoint(float x, float y) {
/*  68 */     if (hasVertex(x, y) && !this.allowDups) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     ArrayList<Float> tempPoints = new ArrayList();
/*  73 */     for (int i = 0; i < this.points.length; i++) {
/*  74 */       tempPoints.add(new Float(this.points[i]));
/*     */     }
/*  76 */     tempPoints.add(new Float(x));
/*  77 */     tempPoints.add(new Float(y));
/*  78 */     int length = tempPoints.size();
/*  79 */     this.points = new float[length];
/*  80 */     for (int j = 0; j < length; j++) {
/*  81 */       this.points[j] = ((Float)tempPoints.get(j)).floatValue();
/*     */     }
/*  83 */     if (x > this.maxX) {
/*  84 */       this.maxX = x;
/*     */     }
/*  86 */     if (y > this.maxY) {
/*  87 */       this.maxY = y;
/*     */     }
/*  89 */     if (x < this.minX) {
/*  90 */       this.minX = x;
/*     */     }
/*  92 */     if (y < this.minY) {
/*  93 */       this.minY = y;
/*     */     }
/*  95 */     findCenter();
/*  96 */     calculateRadius();
/*     */     
/*  98 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 103 */     checkPoints();
/*     */     
/* 105 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 107 */     float[] result = new float[this.points.length];
/* 108 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 109 */     resultPolygon.points = result;
/* 110 */     resultPolygon.findCenter();
/* 111 */     resultPolygon.closed = this.closed;
/*     */     
/* 113 */     return resultPolygon;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setX(float x) {
/* 118 */     super.setX(x);
/*     */     
/* 120 */     this.pointsDirty = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setY(float y) {
/* 125 */     super.setY(y);
/*     */     
/* 127 */     this.pointsDirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createPoints() {}
/*     */ 
/*     */   
/*     */   public boolean closed() {
/* 136 */     return this.closed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClosed(boolean closed) {
/* 141 */     this.closed = closed;
/*     */   }
/*     */ 
/*     */   
/*     */   public Polygon copy() {
/* 146 */     float[] copyPoints = new float[this.points.length];
/* 147 */     System.arraycopy(this.points, 0, copyPoints, 0, copyPoints.length);
/*     */     
/* 149 */     return new Polygon(copyPoints);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Polygon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */