/*    */ package com.me.theresa.fontRenderer.font.opengl.renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Renderer
/*    */ {
/*    */   public static final int IMMEDIATE_RENDERER = 1;
/*    */   public static final int VERTEX_ARRAY_RENDERER = 2;
/*    */   public static final int DEFAULT_LINE_STRIP_RENDERER = 3;
/*    */   public static final int QUAD_BASED_LINE_STRIP_RENDERER = 4;
/* 15 */   private static SGL renderer = new ImmediateModeOGLRenderer();
/*    */   
/* 17 */   private static LineStripRenderer lineStripRenderer = new DefaultLineStripRenderer();
/*    */ 
/*    */   
/*    */   public static void setRenderer(int type) {
/* 21 */     switch (type) {
/*    */       case 1:
/* 23 */         renderer = new ImmediateModeOGLRenderer();
/*    */         return;
/*    */       case 2:
/* 26 */         renderer = new VAOGLRenderer();
/*    */         return;
/*    */     } 
/*    */     
/* 30 */     throw new RuntimeException("Unknown renderer type: " + type);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setLineStripRenderer(int type) {
/* 35 */     switch (type) {
/*    */       case 3:
/* 37 */         lineStripRenderer = new DefaultLineStripRenderer();
/*    */         return;
/*    */       case 4:
/* 40 */         lineStripRenderer = new QuadBasedLineStripRenderer();
/*    */         return;
/*    */     } 
/*    */     
/* 44 */     throw new RuntimeException("Unknown line strip renderer type: " + type);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setLineStripRenderer(LineStripRenderer renderer) {
/* 49 */     lineStripRenderer = renderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setRenderer(SGL r) {
/* 54 */     renderer = r;
/*    */   }
/*    */ 
/*    */   
/*    */   public static SGL get() {
/* 59 */     return renderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public static LineStripRenderer getLineStripRenderer() {
/* 64 */     return lineStripRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\renderer\Renderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */