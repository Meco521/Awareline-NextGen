/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ 
/*     */ public class Rectangle
/*     */   extends Shape
/*     */ {
/*     */   protected float width;
/*     */   protected float height;
/*     */   
/*     */   public Rectangle(float x, float y, float width, float height) {
/*  11 */     this.x = x;
/*  12 */     this.y = y;
/*  13 */     this.width = width;
/*  14 */     this.height = height;
/*  15 */     this.maxX = x + width;
/*  16 */     this.maxY = y + height;
/*  17 */     checkPoints();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(float xp, float yp) {
/*  22 */     if (xp <= getX()) {
/*  23 */       return false;
/*     */     }
/*  25 */     if (yp <= getY()) {
/*  26 */       return false;
/*     */     }
/*  28 */     if (xp >= this.maxX) {
/*  29 */       return false;
/*     */     }
/*  31 */     return (yp < this.maxY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(Rectangle other) {
/*  36 */     setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(float x, float y, float width, float height) {
/*  41 */     setX(x);
/*  42 */     setY(y);
/*  43 */     setSize(width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(float width, float height) {
/*  48 */     setWidth(width);
/*  49 */     setHeight(height);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWidth() {
/*  54 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeight() {
/*  59 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(float h, float v) {
/*  64 */     setX(getX() - h);
/*  65 */     setY(getY() - v);
/*  66 */     setWidth(getWidth() + h * 2.0F);
/*  67 */     setHeight(getHeight() + v * 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scaleGrow(float h, float v) {
/*  72 */     grow(getWidth() * (h - 1.0F), getHeight() * (v - 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/*  77 */     if (width != this.width) {
/*  78 */       this.pointsDirty = true;
/*  79 */       this.width = width;
/*  80 */       this.maxX = this.x + width;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeight(float height) {
/*  86 */     if (height != this.height) {
/*  87 */       this.pointsDirty = true;
/*  88 */       this.height = height;
/*  89 */       this.maxY = this.y + height;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(Shape shape) {
/*  95 */     if (shape instanceof Rectangle) {
/*  96 */       Rectangle other = (Rectangle)shape;
/*  97 */       if (this.x > other.x + other.width || this.x + this.width < other.x) {
/*  98 */         return false;
/*     */       }
/* 100 */       return (this.y <= other.y + other.height && this.y + this.height >= other.y);
/* 101 */     }  if (shape instanceof Circle) {
/* 102 */       return intersects((Circle)shape);
/*     */     }
/* 104 */     return super.intersects(shape);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/* 109 */     float useWidth = this.width;
/* 110 */     float useHeight = this.height;
/* 111 */     this.points = new float[8];
/*     */     
/* 113 */     this.points[0] = this.x;
/* 114 */     this.points[1] = this.y;
/*     */     
/* 116 */     this.points[2] = this.x + useWidth;
/* 117 */     this.points[3] = this.y;
/*     */     
/* 119 */     this.points[4] = this.x + useWidth;
/* 120 */     this.points[5] = this.y + useHeight;
/*     */     
/* 122 */     this.points[6] = this.x;
/* 123 */     this.points[7] = this.y + useHeight;
/*     */     
/* 125 */     this.maxX = this.points[2];
/* 126 */     this.maxY = this.points[5];
/* 127 */     this.minX = this.points[0];
/* 128 */     this.minY = this.points[1];
/*     */     
/* 130 */     findCenter();
/* 131 */     calculateRadius();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean intersects(Circle other) {
/* 136 */     return other.intersects(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     return "[Rectangle " + this.width + "x" + this.height + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(float xp, float yp, float xr, float yr, float widthr, float heightr) {
/* 147 */     return (xp >= xr && yp >= yr && xp <= xr + widthr && yp <= yr + heightr);
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 152 */     checkPoints();
/*     */     
/* 154 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 156 */     float[] result = new float[this.points.length];
/* 157 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 158 */     resultPolygon.points = result;
/* 159 */     resultPolygon.findCenter();
/* 160 */     resultPolygon.checkPoints();
/*     */     
/* 162 */     return resultPolygon;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Rectangle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */