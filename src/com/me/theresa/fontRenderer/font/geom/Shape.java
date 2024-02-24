/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Shape
/*     */   implements Serializable
/*     */ {
/*     */   protected float[] points;
/*     */   protected float[] center;
/*     */   protected float x;
/*     */   protected float y;
/*     */   protected float maxX;
/*     */   protected float maxY;
/*     */   protected float minX;
/*     */   protected float minY;
/*     */   protected float boundingCircleRadius;
/*     */   protected boolean pointsDirty = true;
/*     */   protected transient Triangulator tris;
/*     */   protected boolean trianglesDirty;
/*     */   
/*     */   public void setLocation(float x, float y) {
/*  39 */     setX(x);
/*  40 */     setY(y);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Shape transform(Transform paramTransform);
/*     */ 
/*     */   
/*     */   protected abstract void createPoints();
/*     */ 
/*     */   
/*     */   public float getX() {
/*  51 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setX(float x) {
/*  56 */     if (x != this.x) {
/*  57 */       float dx = x - this.x;
/*  58 */       this.x = x;
/*     */       
/*  60 */       if (this.points == null || this.center == null) {
/*  61 */         checkPoints();
/*     */       }
/*     */       
/*  64 */       for (int i = 0; i < this.points.length / 2; i++) {
/*  65 */         this.points[i << 1] = this.points[i << 1] + dx;
/*     */       }
/*  67 */       this.center[0] = this.center[0] + dx;
/*  68 */       x += dx;
/*  69 */       this.maxX += dx;
/*  70 */       this.minX += dx;
/*  71 */       this.trianglesDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setY(float y) {
/*  77 */     if (y != this.y) {
/*  78 */       float dy = y - this.y;
/*  79 */       this.y = y;
/*     */       
/*  81 */       if (this.points == null || this.center == null) {
/*  82 */         checkPoints();
/*     */       }
/*     */       
/*  85 */       for (int i = 0; i < this.points.length / 2; i++) {
/*  86 */         this.points[(i << 1) + 1] = this.points[(i << 1) + 1] + dy;
/*     */       }
/*  88 */       this.center[1] = this.center[1] + dy;
/*  89 */       y += dy;
/*  90 */       this.maxY += dy;
/*  91 */       this.minY += dy;
/*  92 */       this.trianglesDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getY() {
/*  98 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocation(Vector2f loc) {
/* 103 */     setX(loc.x);
/* 104 */     setY(loc.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCenterX() {
/* 109 */     checkPoints();
/*     */     
/* 111 */     return this.center[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCenterX(float centerX) {
/* 116 */     if (this.points == null || this.center == null) {
/* 117 */       checkPoints();
/*     */     }
/*     */     
/* 120 */     float xDiff = centerX - getCenterX();
/* 121 */     setX(this.x + xDiff);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCenterY() {
/* 126 */     checkPoints();
/*     */     
/* 128 */     return this.center[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCenterY(float centerY) {
/* 133 */     if (this.points == null || this.center == null) {
/* 134 */       checkPoints();
/*     */     }
/*     */     
/* 137 */     float yDiff = centerY - getCenterY();
/* 138 */     setY(this.y + yDiff);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxX() {
/* 143 */     checkPoints();
/* 144 */     return this.maxX;
/*     */   }
/*     */   
/*     */   public float getMaxY() {
/* 148 */     checkPoints();
/* 149 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinX() {
/* 154 */     checkPoints();
/* 155 */     return this.minX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinY() {
/* 160 */     checkPoints();
/* 161 */     return this.minY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBoundingCircleRadius() {
/* 166 */     checkPoints();
/* 167 */     return this.boundingCircleRadius;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getCenter() {
/* 172 */     checkPoints();
/* 173 */     return this.center;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getPoints() {
/* 178 */     checkPoints();
/* 179 */     return this.points;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPointCount() {
/* 184 */     checkPoints();
/* 185 */     return this.points.length / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getPoint(int index) {
/* 190 */     checkPoints();
/*     */     
/* 192 */     float[] result = new float[2];
/*     */     
/* 194 */     result[0] = this.points[index << 1];
/* 195 */     result[1] = this.points[(index << 1) + 1];
/*     */     
/* 197 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getNormal(int index) {
/* 202 */     float[] current = getPoint(index);
/* 203 */     float[] prev = getPoint((index - 1 < 0) ? (getPointCount() - 1) : (index - 1));
/* 204 */     float[] next = getPoint((index + 1 >= getPointCount()) ? 0 : (index + 1));
/*     */     
/* 206 */     float[] t1 = getNormal(prev, current);
/* 207 */     float[] t2 = getNormal(current, next);
/*     */     
/* 209 */     if (index == 0 && !closed()) {
/* 210 */       return t2;
/*     */     }
/* 212 */     if (index == getPointCount() - 1 && !closed()) {
/* 213 */       return t1;
/*     */     }
/*     */     
/* 216 */     float tx = (t1[0] + t2[0]) / 2.0F;
/* 217 */     float ty = (t1[1] + t2[1]) / 2.0F;
/* 218 */     float len = (float)Math.sqrt((tx * tx + ty * ty));
/* 219 */     return new float[] { tx / len, ty / len };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Shape other) {
/* 224 */     if (other.intersects(this)) {
/* 225 */       return false;
/*     */     }
/*     */     
/* 228 */     for (int i = 0; i < other.getPointCount(); i++) {
/* 229 */       float[] pt = other.getPoint(i);
/* 230 */       if (!contains(pt[0], pt[1])) {
/* 231 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 235 */     return true;
/*     */   }
/*     */   
/*     */   private float[] getNormal(float[] start, float[] end) {
/* 239 */     float dx = start[0] - end[0];
/* 240 */     float dy = start[1] - end[1];
/* 241 */     float len = (float)Math.sqrt((dx * dx + dy * dy));
/* 242 */     dx /= len;
/* 243 */     dy /= len;
/* 244 */     return new float[] { -dy, dx };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean includes(float x, float y) {
/* 249 */     if (this.points.length == 0) {
/* 250 */       return false;
/*     */     }
/*     */     
/* 253 */     checkPoints();
/*     */     
/* 255 */     Line testLine = new Line(0.0F, 0.0F, 0.0F, 0.0F);
/* 256 */     Vector2f pt = new Vector2f(x, y);
/*     */     
/* 258 */     for (int i = 0; i < this.points.length; i += 2) {
/* 259 */       int n = i + 2;
/* 260 */       if (n >= this.points.length) {
/* 261 */         n = 0;
/*     */       }
/* 263 */       testLine.set(this.points[i], this.points[i + 1], this.points[n], this.points[n + 1]);
/*     */       
/* 265 */       if (testLine.on(pt)) {
/* 266 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(float x, float y) {
/* 275 */     for (int i = 0; i < this.points.length; i += 2) {
/* 276 */       if (this.points[i] == x && this.points[i + 1] == y) {
/* 277 */         return i / 2;
/*     */       }
/*     */     } 
/*     */     
/* 281 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(float x, float y) {
/* 286 */     checkPoints();
/* 287 */     if (this.points.length == 0) {
/* 288 */       return false;
/*     */     }
/*     */     
/* 291 */     boolean result = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     int npoints = this.points.length;
/*     */     
/* 298 */     float xold = this.points[npoints - 2];
/* 299 */     float yold = this.points[npoints - 1];
/* 300 */     for (int i = 0; i < npoints; i += 2) {
/* 301 */       float x1, y1, x2, y2, xnew = this.points[i];
/* 302 */       float ynew = this.points[i + 1];
/* 303 */       if (xnew > xold) {
/* 304 */         x1 = xold;
/* 305 */         x2 = xnew;
/* 306 */         y1 = yold;
/* 307 */         y2 = ynew;
/*     */       } else {
/* 309 */         x1 = xnew;
/* 310 */         x2 = xold;
/* 311 */         y1 = ynew;
/* 312 */         y2 = yold;
/*     */       } 
/* 314 */       if (((xnew < x) ? true : false) == ((x <= xold) ? true : false) && (y - y1) * (x2 - x1) < (y2 - y1) * (x - x1))
/*     */       {
/*     */         
/* 317 */         result = !result;
/*     */       }
/* 319 */       xold = xnew;
/* 320 */       yold = ynew;
/*     */     } 
/*     */     
/* 323 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(Shape shape) {
/* 329 */     checkPoints();
/*     */     
/* 331 */     boolean result = false;
/* 332 */     float[] points = getPoints();
/* 333 */     float[] thatPoints = shape.getPoints();
/* 334 */     int length = points.length;
/* 335 */     int thatLength = thatPoints.length;
/*     */ 
/*     */ 
/*     */     
/* 339 */     if (!closed()) {
/* 340 */       length -= 2;
/*     */     }
/* 342 */     if (!shape.closed()) {
/* 343 */       thatLength -= 2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     for (int i = 0; i < length; i += 2) {
/* 355 */       int iNext = i + 2;
/* 356 */       if (iNext >= points.length) {
/* 357 */         iNext = 0;
/*     */       }
/*     */       
/* 360 */       for (int j = 0; j < thatLength; j += 2) {
/* 361 */         int jNext = j + 2;
/* 362 */         if (jNext >= thatPoints.length) {
/* 363 */           jNext = 0;
/*     */         }
/*     */         
/* 366 */         double unknownA = ((points[iNext] - points[i]) * (thatPoints[j + 1] - points[i + 1]) - ((points[iNext + 1] - points[i + 1]) * (thatPoints[j] - points[i]))) / ((points[iNext + 1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j]) - (points[iNext] - points[i]) * (thatPoints[jNext + 1] - thatPoints[j + 1]));
/*     */ 
/*     */ 
/*     */         
/* 370 */         double unknownB = ((thatPoints[jNext] - thatPoints[j]) * (thatPoints[j + 1] - points[i + 1]) - ((thatPoints[jNext + 1] - thatPoints[j + 1]) * (thatPoints[j] - points[i]))) / ((points[iNext + 1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j]) - (points[iNext] - points[i]) * (thatPoints[jNext + 1] - thatPoints[j + 1]));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 375 */         if (unknownA >= 0.0D && unknownA <= 1.0D && unknownB >= 0.0D && unknownB <= 1.0D) {
/* 376 */           result = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 380 */       if (result) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 385 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasVertex(float x, float y) {
/* 390 */     if (this.points.length == 0) {
/* 391 */       return false;
/*     */     }
/*     */     
/* 394 */     checkPoints();
/*     */     
/* 396 */     for (int i = 0; i < this.points.length; i += 2) {
/* 397 */       if (this.points[i] == x && this.points[i + 1] == y) {
/* 398 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 402 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void findCenter() {
/* 407 */     this.center = new float[] { 0.0F, 0.0F };
/* 408 */     int length = this.points.length;
/* 409 */     for (int i = 0; i < length; i += 2) {
/* 410 */       this.center[0] = this.center[0] + this.points[i];
/* 411 */       this.center[1] = this.center[1] + this.points[i + 1];
/*     */     } 
/* 413 */     this.center[0] = this.center[0] / (length / 2);
/* 414 */     this.center[1] = this.center[1] / (length / 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void calculateRadius() {
/* 419 */     this.boundingCircleRadius = 0.0F;
/*     */     
/* 421 */     for (int i = 0; i < this.points.length; i += 2) {
/* 422 */       float temp = (this.points[i] - this.center[0]) * (this.points[i] - this.center[0]) + (this.points[i + 1] - this.center[1]) * (this.points[i + 1] - this.center[1]);
/*     */       
/* 424 */       this.boundingCircleRadius = (this.boundingCircleRadius > temp) ? this.boundingCircleRadius : temp;
/*     */     } 
/* 426 */     this.boundingCircleRadius = (float)Math.sqrt(this.boundingCircleRadius);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void calculateTriangles() {
/* 431 */     if (!this.trianglesDirty && this.tris != null) {
/*     */       return;
/*     */     }
/* 434 */     if (this.points.length >= 6) {
/* 435 */       boolean clockwise = true;
/* 436 */       float area = 0.0F; int i;
/* 437 */       for (i = 0; i < this.points.length / 2 - 1; i++) {
/* 438 */         float x1 = this.points[i << 1];
/* 439 */         float y1 = this.points[(i << 1) + 1];
/* 440 */         float x2 = this.points[(i << 1) + 2];
/* 441 */         float y2 = this.points[(i << 1) + 3];
/*     */         
/* 443 */         area += x1 * y2 - y1 * x2;
/*     */       } 
/* 445 */       area /= 2.0F;
/* 446 */       clockwise = (area > 0.0F);
/*     */       
/* 448 */       this.tris = new NeatTriangulator();
/* 449 */       for (i = 0; i < this.points.length; i += 2) {
/* 450 */         this.tris.addPolyPoint(this.points[i], this.points[i + 1]);
/*     */       }
/* 452 */       this.tris.triangulate();
/*     */     } 
/*     */     
/* 455 */     this.trianglesDirty = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void increaseTriangulation() {
/* 460 */     checkPoints();
/* 461 */     calculateTriangles();
/*     */     
/* 463 */     this.tris = new OverTriangulator(this.tris);
/*     */   }
/*     */ 
/*     */   
/*     */   public Triangulator getTriangles() {
/* 468 */     checkPoints();
/* 469 */     calculateTriangles();
/* 470 */     return this.tris;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void checkPoints() {
/* 475 */     if (this.pointsDirty) {
/* 476 */       createPoints();
/* 477 */       findCenter();
/* 478 */       calculateRadius();
/*     */       
/* 480 */       if (this.points.length > 0) {
/* 481 */         this.maxX = this.points[0];
/* 482 */         this.maxY = this.points[1];
/* 483 */         this.minX = this.points[0];
/* 484 */         this.minY = this.points[1];
/* 485 */         for (int i = 0; i < this.points.length / 2; i++) {
/* 486 */           this.maxX = Math.max(this.points[i << 1], this.maxX);
/* 487 */           this.maxY = Math.max(this.points[(i << 1) + 1], this.maxY);
/* 488 */           this.minX = Math.min(this.points[i << 1], this.minX);
/* 489 */           this.minY = Math.min(this.points[(i << 1) + 1], this.minY);
/*     */         } 
/*     */       } 
/* 492 */       this.pointsDirty = false;
/* 493 */       this.trianglesDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void preCache() {
/* 499 */     checkPoints();
/* 500 */     getTriangles();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean closed() {
/* 505 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape prune() {
/* 510 */     Polygon result = new Polygon();
/*     */     
/* 512 */     for (int i = 0; i < getPointCount(); i++) {
/* 513 */       int next = (i + 1 >= getPointCount()) ? 0 : (i + 1);
/* 514 */       int prev = (i - 1 < 0) ? (getPointCount() - 1) : (i - 1);
/*     */       
/* 516 */       float dx1 = getPoint(i)[0] - getPoint(prev)[0];
/* 517 */       float dy1 = getPoint(i)[1] - getPoint(prev)[1];
/* 518 */       float dx2 = getPoint(next)[0] - getPoint(i)[0];
/* 519 */       float dy2 = getPoint(next)[1] - getPoint(i)[1];
/*     */       
/* 521 */       float len1 = (float)Math.sqrt((dx1 * dx1 + dy1 * dy1));
/* 522 */       float len2 = (float)Math.sqrt((dx2 * dx2 + dy2 * dy2));
/* 523 */       dx1 /= len1;
/* 524 */       dy1 /= len1;
/* 525 */       dx2 /= len2;
/* 526 */       dy2 /= len2;
/*     */       
/* 528 */       if (dx1 != dx2 || dy1 != dy2) {
/* 529 */         result.addPoint(getPoint(i)[0], getPoint(i)[1]);
/*     */       }
/*     */     } 
/*     */     
/* 533 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape[] subtract(Shape other) {
/* 538 */     return (new GeomUtil()).subtract(this, other);
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape[] union(Shape other) {
/* 543 */     return (new GeomUtil()).union(this, other);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 548 */     return this.maxX - this.minX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 553 */     return this.maxY - this.minY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Shape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */