/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ public class RenderScale
/*    */ {
/*  5 */   private float scale = 1.0F;
/*  6 */   private float offsetX = 0.0F;
/*  7 */   private float offsetY = 0.0F;
/*    */ 
/*    */   
/*    */   public RenderScale(float scale, float offsetX, float offsetY) {
/* 11 */     this.scale = scale;
/* 12 */     this.offsetX = offsetX;
/* 13 */     this.offsetY = offsetY;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getScale() {
/* 18 */     return this.scale;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getOffsetX() {
/* 23 */     return this.offsetX;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getOffsetY() {
/* 28 */     return this.offsetY;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 33 */     return this.scale + ", " + this.offsetX + ", " + this.offsetY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\RenderScale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */