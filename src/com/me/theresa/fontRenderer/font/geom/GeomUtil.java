/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public class GeomUtil
/*     */ {
/*   8 */   public float EPSILON = 1.0E-4F;
/*     */   
/*  10 */   public float EDGE_SCALE = 1.0F;
/*     */   
/*  12 */   public int MAX_POINTS = 10000;
/*     */   
/*     */   public GeomUtilListener listener;
/*     */ 
/*     */   
/*     */   public Shape[] subtract(Shape target, Shape missing) {
/*  18 */     target = target.transform(new Transform());
/*  19 */     missing = missing.transform(new Transform());
/*     */     
/*  21 */     int count = 0;
/*  22 */     for (int i = 0; i < target.getPointCount(); i++) {
/*  23 */       if (missing.contains(target.getPoint(i)[0], target.getPoint(i)[1])) {
/*  24 */         count++;
/*     */       }
/*     */     } 
/*     */     
/*  28 */     if (count == target.getPointCount()) {
/*  29 */       return new Shape[0];
/*     */     }
/*     */     
/*  32 */     if (!target.intersects(missing)) {
/*  33 */       return new Shape[] { target };
/*     */     }
/*     */     
/*  36 */     int found = 0; int j;
/*  37 */     for (j = 0; j < missing.getPointCount(); j++) {
/*  38 */       if (target.contains(missing.getPoint(j)[0], missing.getPoint(j)[1]) && 
/*  39 */         !onPath(target, missing.getPoint(j)[0], missing.getPoint(j)[1])) {
/*  40 */         found++;
/*     */       }
/*     */     } 
/*     */     
/*  44 */     for (j = 0; j < target.getPointCount(); j++) {
/*  45 */       if (missing.contains(target.getPoint(j)[0], target.getPoint(j)[1]) && 
/*  46 */         !onPath(missing, target.getPoint(j)[0], target.getPoint(j)[1])) {
/*  47 */         found++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  52 */     if (found < 1) {
/*  53 */       return new Shape[] { target };
/*     */     }
/*     */     
/*  56 */     return combine(target, missing, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onPath(Shape path, float x, float y) {
/*  61 */     for (int i = 0; i < path.getPointCount() + 1; i++) {
/*  62 */       int n = rationalPoint(path, i + 1);
/*  63 */       Line line = getLine(path, rationalPoint(path, i), n);
/*  64 */       if (line.distance(new Vector2f(x, y)) < this.EPSILON * 100.0F) {
/*  65 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(GeomUtilListener listener) {
/*  74 */     this.listener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape[] union(Shape target, Shape other) {
/*  79 */     target = target.transform(new Transform());
/*  80 */     other = other.transform(new Transform());
/*     */     
/*  82 */     if (!target.intersects(other)) {
/*  83 */       return new Shape[] { target, other };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  88 */     boolean touches = false;
/*  89 */     int buttCount = 0; int i;
/*  90 */     for (i = 0; i < target.getPointCount(); i++) {
/*  91 */       if (other.contains(target.getPoint(i)[0], target.getPoint(i)[1]) && 
/*  92 */         !other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])) {
/*  93 */         touches = true;
/*     */         
/*     */         break;
/*     */       } 
/*  97 */       if (other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])) {
/*  98 */         buttCount++;
/*     */       }
/*     */     } 
/* 101 */     for (i = 0; i < other.getPointCount(); i++) {
/* 102 */       if (target.contains(other.getPoint(i)[0], other.getPoint(i)[1]) && 
/* 103 */         !target.hasVertex(other.getPoint(i)[0], other.getPoint(i)[1])) {
/* 104 */         touches = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     if (!touches && buttCount < 2) {
/* 111 */       return new Shape[] { target, other };
/*     */     }
/*     */ 
/*     */     
/* 115 */     return combine(target, other, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private Shape[] combine(Shape target, Shape other, boolean subtract) {
/* 120 */     if (subtract) {
/* 121 */       ArrayList<Shape> shapes = new ArrayList();
/* 122 */       ArrayList<Vector2f> used = new ArrayList();
/*     */       
/*     */       int j;
/*     */       
/* 126 */       for (j = 0; j < target.getPointCount(); j++) {
/* 127 */         float[] point = target.getPoint(j);
/* 128 */         if (other.contains(point[0], point[1])) {
/* 129 */           used.add(new Vector2f(point[0], point[1]));
/* 130 */           if (this.listener != null) {
/* 131 */             this.listener.pointExcluded(point[0], point[1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 136 */       for (j = 0; j < target.getPointCount(); j++) {
/* 137 */         float[] point = target.getPoint(j);
/* 138 */         Vector2f pt = new Vector2f(point[0], point[1]);
/*     */         
/* 140 */         if (!used.contains(pt)) {
/* 141 */           Shape result = combineSingle(target, other, true, j);
/* 142 */           shapes.add(result);
/* 143 */           for (int k = 0; k < result.getPointCount(); k++) {
/* 144 */             float[] kpoint = result.getPoint(k);
/* 145 */             Vector2f kpt = new Vector2f(kpoint[0], kpoint[1]);
/* 146 */             used.add(kpt);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 151 */       return shapes.<Shape>toArray(new Shape[0]);
/*     */     } 
/* 153 */     for (int i = 0; i < target.getPointCount(); i++) {
/* 154 */       if (!other.contains(target.getPoint(i)[0], target.getPoint(i)[1]) && 
/* 155 */         !other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])) {
/* 156 */         Shape shape = combineSingle(target, other, false, i);
/* 157 */         return new Shape[] { shape };
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 162 */     return new Shape[] { other };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Shape combineSingle(Shape target, Shape missing, boolean subtract, int start) {
/* 168 */     Shape current = target;
/* 169 */     Shape other = missing;
/* 170 */     int point = start;
/* 171 */     int dir = 1;
/*     */     
/* 173 */     Polygon poly = new Polygon();
/* 174 */     boolean first = true;
/*     */     
/* 176 */     int loop = 0;
/*     */ 
/*     */     
/* 179 */     float px = current.getPoint(point)[0];
/* 180 */     float py = current.getPoint(point)[1];
/*     */     
/* 182 */     while (!poly.hasVertex(px, py) || first || current != target) {
/* 183 */       first = false;
/* 184 */       loop++;
/* 185 */       if (loop > this.MAX_POINTS) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 190 */       poly.addPoint(px, py);
/* 191 */       if (this.listener != null) {
/* 192 */         this.listener.pointUsed(px, py);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       Line line = getLine(current, px, py, rationalPoint(current, point + dir));
/* 199 */       HitResult hit = intersect(other, line);
/*     */       
/* 201 */       if (hit != null) {
/* 202 */         Line hitLine = hit.line;
/* 203 */         Vector2f pt = hit.pt;
/* 204 */         px = pt.x;
/* 205 */         py = pt.y;
/*     */         
/* 207 */         if (this.listener != null) {
/* 208 */           this.listener.pointIntersected(px, py);
/*     */         }
/*     */         
/* 211 */         if (other.hasVertex(px, py)) {
/* 212 */           point = other.indexOf(pt.x, pt.y);
/* 213 */           dir = 1;
/* 214 */           px = pt.x;
/* 215 */           py = pt.y;
/*     */           
/* 217 */           Shape shape = current;
/* 218 */           current = other;
/* 219 */           other = shape;
/*     */           
/*     */           continue;
/*     */         } 
/* 223 */         float dx = hitLine.getDX() / hitLine.length();
/* 224 */         float dy = hitLine.getDY() / hitLine.length();
/* 225 */         dx *= this.EDGE_SCALE;
/* 226 */         dy *= this.EDGE_SCALE;
/*     */         
/* 228 */         if (current.contains(pt.x + dx, pt.y + dy)) {
/*     */ 
/*     */           
/* 231 */           if (subtract) {
/* 232 */             if (current == missing) {
/* 233 */               point = hit.p2;
/* 234 */               dir = -1;
/*     */             } else {
/* 236 */               point = hit.p1;
/* 237 */               dir = 1;
/*     */             }
/*     */           
/* 240 */           } else if (current == target) {
/* 241 */             point = hit.p2;
/* 242 */             dir = -1;
/*     */           } else {
/* 244 */             point = hit.p2;
/* 245 */             dir = -1;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 250 */           Shape shape = current;
/* 251 */           current = other;
/* 252 */           other = shape; continue;
/* 253 */         }  if (current.contains(pt.x - dx, pt.y - dy)) {
/* 254 */           if (subtract) {
/* 255 */             if (current == target) {
/* 256 */               point = hit.p2;
/* 257 */               dir = -1;
/*     */             } else {
/* 259 */               point = hit.p1;
/* 260 */               dir = 1;
/*     */             }
/*     */           
/* 263 */           } else if (current == missing) {
/* 264 */             point = hit.p1;
/* 265 */             dir = 1;
/*     */           } else {
/* 267 */             point = hit.p1;
/* 268 */             dir = 1;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 273 */           Shape shape = current;
/* 274 */           current = other;
/* 275 */           other = shape;
/*     */           continue;
/*     */         } 
/* 278 */         if (subtract) {
/*     */           break;
/*     */         }
/* 281 */         point = hit.p1;
/* 282 */         dir = 1;
/* 283 */         Shape temp = current;
/* 284 */         current = other;
/* 285 */         other = temp;
/*     */         
/* 287 */         point = rationalPoint(current, point + dir);
/* 288 */         px = current.getPoint(point)[0];
/* 289 */         py = current.getPoint(point)[1];
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 294 */       point = rationalPoint(current, point + dir);
/* 295 */       px = current.getPoint(point)[0];
/* 296 */       py = current.getPoint(point)[1];
/*     */     } 
/*     */ 
/*     */     
/* 300 */     poly.addPoint(px, py);
/* 301 */     if (this.listener != null) {
/* 302 */       this.listener.pointUsed(px, py);
/*     */     }
/*     */     
/* 305 */     return poly;
/*     */   }
/*     */ 
/*     */   
/*     */   public HitResult intersect(Shape shape, Line line) {
/* 310 */     float distance = Float.MAX_VALUE;
/* 311 */     HitResult hit = null;
/*     */     
/* 313 */     for (int i = 0; i < shape.getPointCount(); i++) {
/* 314 */       int next = rationalPoint(shape, i + 1);
/* 315 */       Line local = getLine(shape, i, next);
/*     */       
/* 317 */       Vector2f pt = line.intersect(local, true);
/* 318 */       if (pt != null) {
/* 319 */         float newDis = pt.distance(line.getStart());
/* 320 */         if (newDis < distance && newDis > this.EPSILON) {
/* 321 */           hit = new HitResult();
/* 322 */           hit.pt = pt;
/* 323 */           hit.line = local;
/* 324 */           hit.p1 = i;
/* 325 */           hit.p2 = next;
/* 326 */           distance = newDis;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return hit;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int rationalPoint(Shape shape, int p) {
/* 336 */     while (p < 0) {
/* 337 */       p += shape.getPointCount();
/*     */     }
/* 339 */     while (p >= shape.getPointCount()) {
/* 340 */       p -= shape.getPointCount();
/*     */     }
/*     */     
/* 343 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public Line getLine(Shape shape, int s, int e) {
/* 348 */     float[] start = shape.getPoint(s);
/* 349 */     float[] end = shape.getPoint(e);
/*     */     
/* 351 */     Line line = new Line(start[0], start[1], end[0], end[1]);
/* 352 */     return line;
/*     */   }
/*     */ 
/*     */   
/*     */   public Line getLine(Shape shape, float sx, float sy, int e) {
/* 357 */     float[] end = shape.getPoint(e);
/*     */     
/* 359 */     Line line = new Line(sx, sy, end[0], end[1]);
/* 360 */     return line;
/*     */   }
/*     */   
/*     */   public class HitResult {
/*     */     public Line line;
/*     */     public int p1;
/*     */     public int p2;
/*     */     public Vector2f pt;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\GeomUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */