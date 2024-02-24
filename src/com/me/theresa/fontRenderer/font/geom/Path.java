/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Path
/*     */   extends Shape
/*     */ {
/*   8 */   private ArrayList localPoints = new ArrayList();
/*     */   
/*     */   private float cx;
/*     */   
/*     */   private float cy;
/*     */   
/*     */   private boolean closed;
/*     */   
/*  16 */   private final ArrayList holes = new ArrayList();
/*     */   
/*     */   private ArrayList hole;
/*     */ 
/*     */   
/*     */   public Path(float sx, float sy) {
/*  22 */     this.localPoints.add(new float[] { sx, sy });
/*  23 */     this.cx = sx;
/*  24 */     this.cy = sy;
/*  25 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startHole(float sx, float sy) {
/*  30 */     this.hole = new ArrayList();
/*  31 */     this.holes.add(this.hole);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lineTo(float x, float y) {
/*  36 */     if (this.hole != null) {
/*  37 */       this.hole.add(new float[] { x, y });
/*     */     } else {
/*  39 */       this.localPoints.add(new float[] { x, y });
/*     */     } 
/*  41 */     this.cx = x;
/*  42 */     this.cy = y;
/*  43 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  48 */     this.closed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2) {
/*  53 */     curveTo(x, y, cx1, cy1, cx2, cy2, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2, int segments) {
/*  59 */     if (this.cx == x && this.cy == y) {
/*     */       return;
/*     */     }
/*     */     
/*  63 */     Curve curve = new Curve(new Vector2f(this.cx, this.cy), new Vector2f(cx1, cy1), new Vector2f(cx2, cy2), new Vector2f(x, y));
/*  64 */     float step = 1.0F / segments;
/*     */     
/*  66 */     for (int i = 1; i < segments + 1; i++) {
/*  67 */       float t = i * step;
/*  68 */       Vector2f p = curve.pointAt(t);
/*  69 */       if (this.hole != null) {
/*  70 */         this.hole.add(new float[] { p.x, p.y });
/*     */       } else {
/*  72 */         this.localPoints.add(new float[] { p.x, p.y });
/*     */       } 
/*  74 */       this.cx = p.x;
/*  75 */       this.cy = p.y;
/*     */     } 
/*  77 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/*  82 */     this.points = new float[this.localPoints.size() << 1];
/*  83 */     for (int i = 0; i < this.localPoints.size(); i++) {
/*  84 */       float[] p = this.localPoints.get(i);
/*  85 */       this.points[i << 1] = p[0];
/*  86 */       this.points[(i << 1) + 1] = p[1];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/*  92 */     Path p = new Path(this.cx, this.cy);
/*  93 */     p.localPoints = transform(this.localPoints, transform);
/*  94 */     for (int i = 0; i < this.holes.size(); i++) {
/*  95 */       p.holes.add(transform(this.holes.get(i), transform));
/*     */     }
/*  97 */     p.closed = this.closed;
/*     */     
/*  99 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   private ArrayList transform(ArrayList pts, Transform t) {
/* 104 */     float[] in = new float[pts.size() << 1];
/* 105 */     float[] out = new float[pts.size() << 1];
/*     */     
/* 107 */     for (int i = 0; i < pts.size(); i++) {
/* 108 */       in[i << 1] = ((float[])pts.get(i))[0];
/* 109 */       in[(i << 1) + 1] = ((float[])pts.get(i))[1];
/*     */     } 
/* 111 */     t.transform(in, 0, out, 0, pts.size());
/*     */     
/* 113 */     ArrayList<float[]> outList = new ArrayList();
/* 114 */     for (int j = 0; j < pts.size(); j++) {
/* 115 */       outList.add(new float[] { out[j << 1], out[(j << 1) + 1] });
/*     */     } 
/*     */     
/* 118 */     return outList;
/*     */   }
/*     */   
/*     */   public boolean closed() {
/* 122 */     return this.closed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Path.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */