/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ 
/*     */ public class Circle
/*     */   extends Ellipse
/*     */ {
/*     */   public float radius;
/*     */   
/*     */   public strictfp Circle(float centerPointX, float centerPointY, float radius) {
/*  10 */     this(centerPointX, centerPointY, radius, 50);
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Circle(float centerPointX, float centerPointY, float radius, int segmentCount) {
/*  15 */     super(centerPointX, centerPointY, radius, radius, segmentCount);
/*  16 */     this.x = centerPointX - radius;
/*  17 */     this.y = centerPointY - radius;
/*  18 */     this.radius = radius;
/*  19 */     this.boundingCircleRadius = radius;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float getCenterX() {
/*  24 */     return getX() + this.radius;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float getCenterY() {
/*  29 */     return getY() + this.radius;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp void setRadius(float radius) {
/*  34 */     if (radius != this.radius) {
/*  35 */       this.pointsDirty = true;
/*  36 */       this.radius = radius;
/*  37 */       setRadii(radius, radius);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float getRadius() {
/*  43 */     return this.radius;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp boolean intersects(Shape shape) {
/*  48 */     if (shape instanceof Circle) {
/*  49 */       Circle other = (Circle)shape;
/*  50 */       float totalRad2 = this.radius + other.radius;
/*     */       
/*  52 */       if (Math.abs(other.getCenterX() - getCenterX()) > totalRad2) {
/*  53 */         return false;
/*     */       }
/*  55 */       if (Math.abs(other.getCenterY() - getCenterY()) > totalRad2) {
/*  56 */         return false;
/*     */       }
/*     */       
/*  59 */       totalRad2 *= totalRad2;
/*     */       
/*  61 */       float dx = Math.abs(other.getCenterX() - getCenterX());
/*  62 */       float dy = Math.abs(other.getCenterY() - getCenterY());
/*     */       
/*  64 */       return (totalRad2 >= dx * dx + dy * dy);
/*  65 */     }  if (shape instanceof Rectangle) {
/*  66 */       return intersects((Rectangle)shape);
/*     */     }
/*  68 */     return super.intersects(shape);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp boolean contains(float x, float y) {
/*  74 */     return ((x - getX()) * (x - getX()) + (y - getY()) * (y - getY()) < this.radius * this.radius);
/*     */   }
/*     */ 
/*     */   
/*     */   private strictfp boolean contains(Line line) {
/*  79 */     return (contains(line.getX1(), line.getY1()) && contains(line.getX2(), line.getY2()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected strictfp void findCenter() {
/*  84 */     this.center = new float[2];
/*  85 */     this.center[0] = this.x + this.radius;
/*  86 */     this.center[1] = this.y + this.radius;
/*     */   }
/*     */ 
/*     */   
/*     */   protected strictfp void calculateRadius() {
/*  91 */     this.boundingCircleRadius = this.radius;
/*     */   }
/*     */ 
/*     */   
/*     */   private strictfp boolean intersects(Rectangle other) {
/*  96 */     Rectangle box = other;
/*  97 */     Circle circle = this;
/*     */     
/*  99 */     if (box.contains(this.x + this.radius, this.y + this.radius)) {
/* 100 */       return true;
/*     */     }
/*     */     
/* 103 */     float x1 = box.getX();
/* 104 */     float y1 = box.getY();
/* 105 */     float x2 = box.getX() + box.getWidth();
/* 106 */     float y2 = box.getY() + box.getHeight();
/*     */     
/* 108 */     Line[] lines = new Line[4];
/* 109 */     lines[0] = new Line(x1, y1, x2, y1);
/* 110 */     lines[1] = new Line(x2, y1, x2, y2);
/* 111 */     lines[2] = new Line(x2, y2, x1, y2);
/* 112 */     lines[3] = new Line(x1, y2, x1, y1);
/*     */     
/* 114 */     float r2 = circle.radius * circle.radius;
/*     */     
/* 116 */     Vector2f pos = new Vector2f(circle.getCenterX(), circle.getCenterY());
/*     */     
/* 118 */     for (int i = 0; i < 4; i++) {
/* 119 */       float dis = lines[i].distanceSquared(pos);
/* 120 */       if (dis < r2) {
/* 121 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private strictfp boolean intersects(Line other) {
/* 131 */     Vector2f closest, lineSegmentStart = new Vector2f(other.getX1(), other.getY1());
/* 132 */     Vector2f lineSegmentEnd = new Vector2f(other.getX2(), other.getY2());
/* 133 */     Vector2f circleCenter = new Vector2f(getCenterX(), getCenterY());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     Vector2f segv = lineSegmentEnd.copy().sub(lineSegmentStart);
/* 139 */     Vector2f ptv = circleCenter.copy().sub(lineSegmentStart);
/* 140 */     float segvLength = segv.length();
/* 141 */     float projvl = ptv.dot(segv) / segvLength;
/* 142 */     if (projvl < 0.0F) {
/* 143 */       closest = lineSegmentStart;
/* 144 */     } else if (projvl > segvLength) {
/* 145 */       closest = lineSegmentEnd;
/*     */     } else {
/* 147 */       Vector2f projv = segv.copy().scale(projvl / segvLength);
/* 148 */       closest = lineSegmentStart.copy().add(projv);
/*     */     } 
/* 150 */     boolean intersects = (circleCenter.copy().sub(closest).lengthSquared() <= this.radius * this.radius);
/*     */     
/* 152 */     return intersects;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Circle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */