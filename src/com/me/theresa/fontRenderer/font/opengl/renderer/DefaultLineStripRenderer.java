/*    */ package com.me.theresa.fontRenderer.font.opengl.renderer;
/*    */ 
/*    */ public class DefaultLineStripRenderer
/*    */   implements LineStripRenderer {
/*  5 */   private final SGL GL = Renderer.get();
/*    */ 
/*    */   
/*    */   public void end() {
/*  9 */     this.GL.glEnd();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAntiAlias(boolean antialias) {
/* 14 */     if (antialias) {
/* 15 */       this.GL.glEnable(2848);
/*    */     } else {
/* 17 */       this.GL.glDisable(2848);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setWidth(float width) {
/* 23 */     this.GL.glLineWidth(width);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 28 */     this.GL.glBegin(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void vertex(float x, float y) {
/* 33 */     this.GL.glVertex2f(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void color(float r, float g, float b, float a) {
/* 38 */     this.GL.glColor4f(r, g, b, a);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLineCaps(boolean caps) {}
/*    */ 
/*    */   
/*    */   public boolean applyGLLineFixes() {
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\renderer\DefaultLineStripRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */