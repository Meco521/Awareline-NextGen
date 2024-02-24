/*    */ package com.me.theresa.fontRenderer.font.geom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Curve
/*    */   extends Shape
/*    */ {
/*    */   private final Vector2f p1;
/*    */   private final Vector2f c1;
/*    */   private final Vector2f c2;
/*    */   private final Vector2f p2;
/*    */   private final int segments;
/*    */   
/*    */   public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2) {
/* 18 */     this(p1, c1, c2, p2, 20);
/*    */   }
/*    */ 
/*    */   
/*    */   public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2, int segments) {
/* 23 */     this.p1 = new Vector2f(p1);
/* 24 */     this.c1 = new Vector2f(c1);
/* 25 */     this.c2 = new Vector2f(c2);
/* 26 */     this.p2 = new Vector2f(p2);
/*    */     
/* 28 */     this.segments = segments;
/* 29 */     this.pointsDirty = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector2f pointAt(float t) {
/* 34 */     float a = 1.0F - t;
/* 35 */     float b = t;
/*    */     
/* 37 */     float f1 = a * a * a;
/* 38 */     float f2 = 3.0F * a * a * b;
/* 39 */     float f3 = 3.0F * a * b * b;
/* 40 */     float f4 = b * b * b;
/*    */     
/* 42 */     float nx = this.p1.x * f1 + this.c1.x * f2 + this.c2.x * f3 + this.p2.x * f4;
/* 43 */     float ny = this.p1.y * f1 + this.c1.y * f2 + this.c2.y * f3 + this.p2.y * f4;
/*    */     
/* 45 */     return new Vector2f(nx, ny);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createPoints() {
/* 50 */     float step = 1.0F / this.segments;
/* 51 */     this.points = new float[this.segments + 1 << 1];
/* 52 */     for (int i = 0; i < this.segments + 1; i++) {
/* 53 */       float t = i * step;
/*    */       
/* 55 */       Vector2f p = pointAt(t);
/* 56 */       this.points[i << 1] = p.x;
/* 57 */       this.points[(i << 1) + 1] = p.y;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Shape transform(Transform transform) {
/* 63 */     float[] pts = new float[8];
/* 64 */     float[] dest = new float[8];
/* 65 */     pts[0] = this.p1.x;
/* 66 */     pts[1] = this.p1.y;
/* 67 */     pts[2] = this.c1.x;
/* 68 */     pts[3] = this.c1.y;
/* 69 */     pts[4] = this.c2.x;
/* 70 */     pts[5] = this.c2.y;
/* 71 */     pts[6] = this.p2.x;
/* 72 */     pts[7] = this.p2.y;
/* 73 */     transform.transform(pts, 0, dest, 0, 4);
/*    */     
/* 75 */     return new Curve(new Vector2f(dest[0], dest[1]), new Vector2f(dest[2], dest[3]), new Vector2f(dest[4], dest[5]), new Vector2f(dest[6], dest[7]));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean closed() {
/* 81 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Curve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */