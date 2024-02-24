/*    */ package net.minecraft.client.renderer.culling;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ClippingHelperImpl
/*    */   extends ClippingHelper
/*    */ {
/* 11 */   private static final ClippingHelperImpl instance = new ClippingHelperImpl();
/* 12 */   private final FloatBuffer projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
/* 13 */   private final FloatBuffer modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
/* 14 */   private final FloatBuffer field_78564_h = GLAllocation.createDirectFloatBuffer(16);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ClippingHelper getInstance() {
/* 21 */     instance.init();
/* 22 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   private void normalize(float[] p_180547_1_) {
/* 27 */     float f = MathHelper.sqrt_float(p_180547_1_[0] * p_180547_1_[0] + p_180547_1_[1] * p_180547_1_[1] + p_180547_1_[2] * p_180547_1_[2]);
/* 28 */     p_180547_1_[0] = p_180547_1_[0] / f;
/* 29 */     p_180547_1_[1] = p_180547_1_[1] / f;
/* 30 */     p_180547_1_[2] = p_180547_1_[2] / f;
/* 31 */     p_180547_1_[3] = p_180547_1_[3] / f;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 36 */     this.projectionMatrixBuffer.clear();
/* 37 */     this.modelviewMatrixBuffer.clear();
/* 38 */     this.field_78564_h.clear();
/* 39 */     GlStateManager.getFloat(2983, this.projectionMatrixBuffer);
/* 40 */     GlStateManager.getFloat(2982, this.modelviewMatrixBuffer);
/* 41 */     float[] afloat = this.projectionMatrix;
/* 42 */     float[] afloat1 = this.modelviewMatrix;
/* 43 */     this.projectionMatrixBuffer.flip().limit(16);
/* 44 */     this.projectionMatrixBuffer.get(afloat);
/* 45 */     this.modelviewMatrixBuffer.flip().limit(16);
/* 46 */     this.modelviewMatrixBuffer.get(afloat1);
/* 47 */     this.clippingMatrix[0] = afloat1[0] * afloat[0] + afloat1[1] * afloat[4] + afloat1[2] * afloat[8] + afloat1[3] * afloat[12];
/* 48 */     this.clippingMatrix[1] = afloat1[0] * afloat[1] + afloat1[1] * afloat[5] + afloat1[2] * afloat[9] + afloat1[3] * afloat[13];
/* 49 */     this.clippingMatrix[2] = afloat1[0] * afloat[2] + afloat1[1] * afloat[6] + afloat1[2] * afloat[10] + afloat1[3] * afloat[14];
/* 50 */     this.clippingMatrix[3] = afloat1[0] * afloat[3] + afloat1[1] * afloat[7] + afloat1[2] * afloat[11] + afloat1[3] * afloat[15];
/* 51 */     this.clippingMatrix[4] = afloat1[4] * afloat[0] + afloat1[5] * afloat[4] + afloat1[6] * afloat[8] + afloat1[7] * afloat[12];
/* 52 */     this.clippingMatrix[5] = afloat1[4] * afloat[1] + afloat1[5] * afloat[5] + afloat1[6] * afloat[9] + afloat1[7] * afloat[13];
/* 53 */     this.clippingMatrix[6] = afloat1[4] * afloat[2] + afloat1[5] * afloat[6] + afloat1[6] * afloat[10] + afloat1[7] * afloat[14];
/* 54 */     this.clippingMatrix[7] = afloat1[4] * afloat[3] + afloat1[5] * afloat[7] + afloat1[6] * afloat[11] + afloat1[7] * afloat[15];
/* 55 */     this.clippingMatrix[8] = afloat1[8] * afloat[0] + afloat1[9] * afloat[4] + afloat1[10] * afloat[8] + afloat1[11] * afloat[12];
/* 56 */     this.clippingMatrix[9] = afloat1[8] * afloat[1] + afloat1[9] * afloat[5] + afloat1[10] * afloat[9] + afloat1[11] * afloat[13];
/* 57 */     this.clippingMatrix[10] = afloat1[8] * afloat[2] + afloat1[9] * afloat[6] + afloat1[10] * afloat[10] + afloat1[11] * afloat[14];
/* 58 */     this.clippingMatrix[11] = afloat1[8] * afloat[3] + afloat1[9] * afloat[7] + afloat1[10] * afloat[11] + afloat1[11] * afloat[15];
/* 59 */     this.clippingMatrix[12] = afloat1[12] * afloat[0] + afloat1[13] * afloat[4] + afloat1[14] * afloat[8] + afloat1[15] * afloat[12];
/* 60 */     this.clippingMatrix[13] = afloat1[12] * afloat[1] + afloat1[13] * afloat[5] + afloat1[14] * afloat[9] + afloat1[15] * afloat[13];
/* 61 */     this.clippingMatrix[14] = afloat1[12] * afloat[2] + afloat1[13] * afloat[6] + afloat1[14] * afloat[10] + afloat1[15] * afloat[14];
/* 62 */     this.clippingMatrix[15] = afloat1[12] * afloat[3] + afloat1[13] * afloat[7] + afloat1[14] * afloat[11] + afloat1[15] * afloat[15];
/* 63 */     float[] afloat2 = this.frustum[0];
/* 64 */     afloat2[0] = this.clippingMatrix[3] - this.clippingMatrix[0];
/* 65 */     afloat2[1] = this.clippingMatrix[7] - this.clippingMatrix[4];
/* 66 */     afloat2[2] = this.clippingMatrix[11] - this.clippingMatrix[8];
/* 67 */     afloat2[3] = this.clippingMatrix[15] - this.clippingMatrix[12];
/* 68 */     normalize(afloat2);
/* 69 */     float[] afloat3 = this.frustum[1];
/* 70 */     afloat3[0] = this.clippingMatrix[3] + this.clippingMatrix[0];
/* 71 */     afloat3[1] = this.clippingMatrix[7] + this.clippingMatrix[4];
/* 72 */     afloat3[2] = this.clippingMatrix[11] + this.clippingMatrix[8];
/* 73 */     afloat3[3] = this.clippingMatrix[15] + this.clippingMatrix[12];
/* 74 */     normalize(afloat3);
/* 75 */     float[] afloat4 = this.frustum[2];
/* 76 */     afloat4[0] = this.clippingMatrix[3] + this.clippingMatrix[1];
/* 77 */     afloat4[1] = this.clippingMatrix[7] + this.clippingMatrix[5];
/* 78 */     afloat4[2] = this.clippingMatrix[11] + this.clippingMatrix[9];
/* 79 */     afloat4[3] = this.clippingMatrix[15] + this.clippingMatrix[13];
/* 80 */     normalize(afloat4);
/* 81 */     float[] afloat5 = this.frustum[3];
/* 82 */     afloat5[0] = this.clippingMatrix[3] - this.clippingMatrix[1];
/* 83 */     afloat5[1] = this.clippingMatrix[7] - this.clippingMatrix[5];
/* 84 */     afloat5[2] = this.clippingMatrix[11] - this.clippingMatrix[9];
/* 85 */     afloat5[3] = this.clippingMatrix[15] - this.clippingMatrix[13];
/* 86 */     normalize(afloat5);
/* 87 */     float[] afloat6 = this.frustum[4];
/* 88 */     afloat6[0] = this.clippingMatrix[3] - this.clippingMatrix[2];
/* 89 */     afloat6[1] = this.clippingMatrix[7] - this.clippingMatrix[6];
/* 90 */     afloat6[2] = this.clippingMatrix[11] - this.clippingMatrix[10];
/* 91 */     afloat6[3] = this.clippingMatrix[15] - this.clippingMatrix[14];
/* 92 */     normalize(afloat6);
/* 93 */     float[] afloat7 = this.frustum[5];
/* 94 */     afloat7[0] = this.clippingMatrix[3] + this.clippingMatrix[2];
/* 95 */     afloat7[1] = this.clippingMatrix[7] + this.clippingMatrix[6];
/* 96 */     afloat7[2] = this.clippingMatrix[11] + this.clippingMatrix[10];
/* 97 */     afloat7[3] = this.clippingMatrix[15] + this.clippingMatrix[14];
/* 98 */     normalize(afloat7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\culling\ClippingHelperImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */