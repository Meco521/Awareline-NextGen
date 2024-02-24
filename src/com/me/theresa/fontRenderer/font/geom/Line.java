/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Line
/*     */   extends Shape
/*     */ {
/*     */   private Vector2f start;
/*     */   private Vector2f end;
/*     */   private Vector2f vec;
/*     */   private float lenSquared;
/*  14 */   private final Vector2f loc = new Vector2f(0.0F, 0.0F);
/*     */   
/*  16 */   private final Vector2f v = new Vector2f(0.0F, 0.0F);
/*     */   
/*  18 */   private final Vector2f v2 = new Vector2f(0.0F, 0.0F);
/*     */   
/*  20 */   private final Vector2f proj = new Vector2f(0.0F, 0.0F);
/*     */ 
/*     */   
/*  23 */   private final Vector2f closest = new Vector2f(0.0F, 0.0F);
/*     */   
/*  25 */   private final Vector2f other = new Vector2f(0.0F, 0.0F);
/*     */ 
/*     */   
/*     */   private final boolean outerEdge = true;
/*     */   
/*     */   private final boolean innerEdge = true;
/*     */ 
/*     */   
/*     */   public Line(float x, float y, boolean inner, boolean outer) {
/*  34 */     this(0.0F, 0.0F, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public Line(float x, float y) {
/*  39 */     this(x, y, true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Line(float x1, float y1, float x2, float y2) {
/*  44 */     this(new Vector2f(x1, y1), new Vector2f(x2, y2));
/*     */   }
/*     */ 
/*     */   
/*     */   public Line(float x1, float y1, float dx, float dy, boolean dummy) {
/*  49 */     this(new Vector2f(x1, y1), new Vector2f(x1 + dx, y1 + dy));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line(float[] start, float[] end) {
/*  56 */     set(start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line(Vector2f start, Vector2f end) {
/*  63 */     set(start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float[] start, float[] end) {
/*  68 */     set(start[0], start[1], end[0], end[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2f getStart() {
/*  73 */     return this.start;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2f getEnd() {
/*  78 */     return this.end;
/*     */   }
/*     */ 
/*     */   
/*     */   public float length() {
/*  83 */     return this.vec.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public float lengthSquared() {
/*  88 */     return this.vec.lengthSquared();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(Vector2f start, Vector2f end) {
/*  93 */     this.pointsDirty = true;
/*  94 */     if (this.start == null) {
/*  95 */       this.start = new Vector2f();
/*     */     }
/*  97 */     this.start.set(start);
/*     */     
/*  99 */     if (this.end == null) {
/* 100 */       this.end = new Vector2f();
/*     */     }
/* 102 */     this.end.set(end);
/*     */     
/* 104 */     this.vec = new Vector2f(end);
/* 105 */     this.vec.sub(start);
/*     */     
/* 107 */     this.lenSquared = this.vec.lengthSquared();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float sx, float sy, float ex, float ey) {
/* 112 */     this.pointsDirty = true;
/* 113 */     this.start.set(sx, sy);
/* 114 */     this.end.set(ex, ey);
/* 115 */     float dx = ex - sx;
/* 116 */     float dy = ey - sy;
/* 117 */     this.vec.set(dx, dy);
/*     */     
/* 119 */     this.lenSquared = dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDX() {
/* 124 */     return this.end.getX() - this.start.getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDY() {
/* 129 */     return this.end.getY() - this.start.getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getX() {
/* 134 */     return getX1();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getY() {
/* 139 */     return getY1();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getX1() {
/* 144 */     return this.start.getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getY1() {
/* 149 */     return this.start.getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getX2() {
/* 154 */     return this.end.getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getY2() {
/* 159 */     return this.end.getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public float distance(Vector2f point) {
/* 164 */     return (float)Math.sqrt(distanceSquared(point));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean on(Vector2f point) {
/* 169 */     getClosestPoint(point, this.closest);
/*     */     
/* 171 */     return point.equals(this.closest);
/*     */   }
/*     */ 
/*     */   
/*     */   public float distanceSquared(Vector2f point) {
/* 176 */     getClosestPoint(point, this.closest);
/* 177 */     this.closest.sub(point);
/*     */     
/* 179 */     float result = this.closest.lengthSquared();
/*     */     
/* 181 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getClosestPoint(Vector2f point, Vector2f result) {
/* 186 */     this.loc.set(point);
/* 187 */     this.loc.sub(this.start);
/*     */     
/* 189 */     float projDistance = this.vec.dot(this.loc);
/*     */     
/* 191 */     projDistance /= this.vec.lengthSquared();
/*     */     
/* 193 */     if (projDistance < 0.0F) {
/* 194 */       result.set(this.start);
/*     */       return;
/*     */     } 
/* 197 */     if (projDistance > 1.0F) {
/* 198 */       result.set(this.end);
/*     */       
/*     */       return;
/*     */     } 
/* 202 */     result.x = this.start.getX() + projDistance * this.vec.getX();
/* 203 */     result.y = this.start.getY() + projDistance * this.vec.getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 208 */     return "[Line " + this.start + "," + this.end + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2f intersect(Line other) {
/* 213 */     return intersect(other, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2f intersect(Line other, boolean limit) {
/* 218 */     Vector2f temp = new Vector2f();
/*     */     
/* 220 */     if (!intersect(other, limit, temp)) {
/* 221 */       return null;
/*     */     }
/*     */     
/* 224 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersect(Line other, boolean limit, Vector2f result) {
/* 229 */     float dx1 = this.end.getX() - this.start.getX();
/* 230 */     float dx2 = other.end.getX() - other.start.getX();
/* 231 */     float dy1 = this.end.getY() - this.start.getY();
/* 232 */     float dy2 = other.end.getY() - other.start.getY();
/* 233 */     float denom = dy2 * dx1 - dx2 * dy1;
/*     */     
/* 235 */     if (denom == 0.0F) {
/* 236 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 240 */     float ua = dx2 * (this.start.getY() - other.start.getY()) - dy2 * (this.start.getX() - other.start.getX());
/* 241 */     ua /= denom;
/*     */     
/* 243 */     float ub = dx1 * (this.start.getY() - other.start.getY()) - dy1 * (this.start.getX() - other.start.getX());
/* 244 */     ub /= denom;
/*     */     
/* 246 */     if (limit && (ua < 0.0F || ua > 1.0F || ub < 0.0F || ub > 1.0F)) {
/* 247 */       return false;
/*     */     }
/*     */     
/* 250 */     float u = ua;
/*     */     
/* 252 */     float ix = this.start.getX() + u * (this.end.getX() - this.start.getX());
/* 253 */     float iy = this.start.getY() + u * (this.end.getY() - this.start.getY());
/*     */     
/* 255 */     result.set(ix, iy);
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/* 261 */     this.points = new float[4];
/* 262 */     this.points[0] = getX1();
/* 263 */     this.points[1] = getY1();
/* 264 */     this.points[2] = getX2();
/* 265 */     this.points[3] = getY2();
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 270 */     float[] temp = new float[4];
/* 271 */     createPoints();
/* 272 */     transform.transform(this.points, 0, temp, 0, 2);
/*     */     
/* 274 */     return new Line(temp[0], temp[1], temp[2], temp[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean closed() {
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(Shape shape) {
/* 284 */     if (shape instanceof Circle) {
/* 285 */       return shape.intersects(this);
/*     */     }
/* 287 */     return super.intersects(shape);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Line.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */