/*    */ package net.minecraft.client.renderer.culling;
/*    */ 
/*    */ public class ClippingHelper
/*    */ {
/*  5 */   public float[][] frustum = new float[6][4];
/*  6 */   public float[] projectionMatrix = new float[16];
/*  7 */   public float[] modelviewMatrix = new float[16];
/*  8 */   public float[] clippingMatrix = new float[16];
/*    */   
/*    */   public boolean disabled = false;
/*    */   
/*    */   private float dot(float[] p_dot_1_, float p_dot_2_, float p_dot_3_, float p_dot_4_) {
/* 13 */     return p_dot_1_[0] * p_dot_2_ + p_dot_1_[1] * p_dot_3_ + p_dot_1_[2] * p_dot_4_ + p_dot_1_[3];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBoxInFrustum(double p_78553_1_, double p_78553_3_, double p_78553_5_, double p_78553_7_, double p_78553_9_, double p_78553_11_) {
/* 21 */     if (this.disabled)
/*    */     {
/* 23 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 27 */     float f = (float)p_78553_1_;
/* 28 */     float f1 = (float)p_78553_3_;
/* 29 */     float f2 = (float)p_78553_5_;
/* 30 */     float f3 = (float)p_78553_7_;
/* 31 */     float f4 = (float)p_78553_9_;
/* 32 */     float f5 = (float)p_78553_11_;
/*    */     
/* 34 */     for (int i = 0; i < 6; i++) {
/*    */       
/* 36 */       float[] afloat = this.frustum[i];
/* 37 */       float f6 = afloat[0];
/* 38 */       float f7 = afloat[1];
/* 39 */       float f8 = afloat[2];
/* 40 */       float f9 = afloat[3];
/*    */       
/* 42 */       if (f6 * f + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f5 + f9 <= 0.0F)
/*    */       {
/* 44 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBoxInFrustumFully(double p_isBoxInFrustumFully_1_, double p_isBoxInFrustumFully_3_, double p_isBoxInFrustumFully_5_, double p_isBoxInFrustumFully_7_, double p_isBoxInFrustumFully_9_, double p_isBoxInFrustumFully_11_) {
/* 54 */     if (this.disabled)
/*    */     {
/* 56 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 60 */     float f = (float)p_isBoxInFrustumFully_1_;
/* 61 */     float f1 = (float)p_isBoxInFrustumFully_3_;
/* 62 */     float f2 = (float)p_isBoxInFrustumFully_5_;
/* 63 */     float f3 = (float)p_isBoxInFrustumFully_7_;
/* 64 */     float f4 = (float)p_isBoxInFrustumFully_9_;
/* 65 */     float f5 = (float)p_isBoxInFrustumFully_11_;
/*    */     
/* 67 */     for (int i = 0; i < 6; i++) {
/*    */       
/* 69 */       float[] afloat = this.frustum[i];
/* 70 */       float f6 = afloat[0];
/* 71 */       float f7 = afloat[1];
/* 72 */       float f8 = afloat[2];
/* 73 */       float f9 = afloat[3];
/*    */       
/* 75 */       if (i < 4) {
/*    */         
/* 77 */         if (f6 * f + f7 * f1 + f8 * f2 + f9 <= 0.0F || f6 * f3 + f7 * f1 + f8 * f2 + f9 <= 0.0F || f6 * f + f7 * f4 + f8 * f2 + f9 <= 0.0F || f6 * f3 + f7 * f4 + f8 * f2 + f9 <= 0.0F || f6 * f + f7 * f1 + f8 * f5 + f9 <= 0.0F || f6 * f3 + f7 * f1 + f8 * f5 + f9 <= 0.0F || f6 * f + f7 * f4 + f8 * f5 + f9 <= 0.0F || f6 * f3 + f7 * f4 + f8 * f5 + f9 <= 0.0F)
/*    */         {
/* 79 */           return false;
/*    */         }
/*    */       }
/* 82 */       else if (f6 * f + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f5 + f9 <= 0.0F) {
/*    */         
/* 84 */         return false;
/*    */       } 
/*    */     } 
/*    */     
/* 88 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\culling\ClippingHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */