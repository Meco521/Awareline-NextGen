/*    */ package com.me.theresa.fontRenderer.font.geom;
/*    */ 
/*    */ public class Point
/*    */   extends Shape {
/*    */   public Point(float x, float y) {
/*  6 */     this.x = x;
/*  7 */     this.y = y;
/*  8 */     checkPoints();
/*    */   }
/*    */ 
/*    */   
/*    */   public Shape transform(Transform transform) {
/* 13 */     float[] result = new float[this.points.length];
/* 14 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/*    */     
/* 16 */     return new Point(this.points[0], this.points[1]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createPoints() {
/* 21 */     this.points = new float[2];
/* 22 */     this.points[0] = getX();
/* 23 */     this.points[1] = getY();
/*    */     
/* 25 */     this.maxX = this.x;
/* 26 */     this.maxY = this.y;
/* 27 */     this.minX = this.x;
/* 28 */     this.minY = this.y;
/*    */     
/* 30 */     findCenter();
/* 31 */     calculateRadius();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void findCenter() {
/* 36 */     this.center = new float[2];
/* 37 */     this.center[0] = this.points[0];
/* 38 */     this.center[1] = this.points[1];
/*    */   }
/*    */ 
/*    */   
/*    */   protected void calculateRadius() {
/* 43 */     this.boundingCircleRadius = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Point.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */