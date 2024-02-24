/*    */ package com.me.theresa.fontRenderer.font.geom;
/*    */ 
/*    */ public class OverTriangulator
/*    */   implements Triangulator
/*    */ {
/*    */   private final float[][] triangles;
/*    */   
/*    */   public OverTriangulator(Triangulator tris) {
/*  9 */     this.triangles = new float[tris.getTriangleCount() * 6 * 3][2];
/*    */     
/* 11 */     int tcount = 0;
/* 12 */     for (int i = 0; i < tris.getTriangleCount(); i++) {
/* 13 */       float cx = 0.0F;
/* 14 */       float cy = 0.0F; int p;
/* 15 */       for (p = 0; p < 3; p++) {
/* 16 */         float[] pt = tris.getTrianglePoint(i, p);
/* 17 */         cx += pt[0];
/* 18 */         cy += pt[1];
/*    */       } 
/*    */       
/* 21 */       cx /= 3.0F;
/* 22 */       cy /= 3.0F;
/*    */       
/* 24 */       for (p = 0; p < 3; p++) {
/* 25 */         int n = p + 1;
/* 26 */         if (n > 2) {
/* 27 */           n = 0;
/*    */         }
/*    */         
/* 30 */         float[] pt1 = tris.getTrianglePoint(i, p);
/* 31 */         float[] pt2 = tris.getTrianglePoint(i, n);
/*    */         
/* 33 */         pt1[0] = (pt1[0] + pt2[0]) / 2.0F;
/* 34 */         pt1[1] = (pt1[1] + pt2[1]) / 2.0F;
/*    */         
/* 36 */         this.triangles[tcount * 3 + 0][0] = cx;
/* 37 */         this.triangles[tcount * 3 + 0][1] = cy;
/* 38 */         this.triangles[tcount * 3 + 1][0] = pt1[0];
/* 39 */         this.triangles[tcount * 3 + 1][1] = pt1[1];
/* 40 */         this.triangles[tcount * 3 + 2][0] = pt2[0];
/* 41 */         this.triangles[tcount * 3 + 2][1] = pt2[1];
/* 42 */         tcount++;
/*    */       } 
/*    */       
/* 45 */       for (p = 0; p < 3; p++) {
/* 46 */         int n = p + 1;
/* 47 */         if (n > 2) {
/* 48 */           n = 0;
/*    */         }
/*    */         
/* 51 */         float[] pt1 = tris.getTrianglePoint(i, p);
/* 52 */         float[] pt2 = tris.getTrianglePoint(i, n);
/*    */         
/* 54 */         pt2[0] = (pt1[0] + pt2[0]) / 2.0F;
/* 55 */         pt2[1] = (pt1[1] + pt2[1]) / 2.0F;
/*    */         
/* 57 */         this.triangles[tcount * 3 + 0][0] = cx;
/* 58 */         this.triangles[tcount * 3 + 0][1] = cy;
/* 59 */         this.triangles[tcount * 3 + 1][0] = pt1[0];
/* 60 */         this.triangles[tcount * 3 + 1][1] = pt1[1];
/* 61 */         this.triangles[tcount * 3 + 2][0] = pt2[0];
/* 62 */         this.triangles[tcount * 3 + 2][1] = pt2[1];
/* 63 */         tcount++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addPolyPoint(float x, float y) {}
/*    */ 
/*    */   
/*    */   public int getTriangleCount() {
/* 74 */     return this.triangles.length / 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] getTrianglePoint(int tri, int i) {
/* 79 */     float[] pt = this.triangles[tri * 3 + i];
/*    */     
/* 81 */     return new float[] { pt[0], pt[1] };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startHole() {}
/*    */ 
/*    */   
/*    */   public boolean triangulate() {
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\OverTriangulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */