/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform3f
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private float[][] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform3f(String name) {
/* 12 */     super(name);
/* 13 */     resetValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(float v0, float v1, float v2) {
/* 18 */     int i = getProgram();
/* 19 */     float[] afloat = this.programValues[i];
/*    */     
/* 21 */     if (afloat[0] != v0 || afloat[1] != v1 || afloat[2] != v2) {
/*    */       
/* 23 */       afloat[0] = v0;
/* 24 */       afloat[1] = v1;
/* 25 */       afloat[2] = v2;
/* 26 */       int j = getLocation();
/*    */       
/* 28 */       if (j >= 0) {
/*    */         
/* 30 */         ARBShaderObjects.glUniform3fARB(j, v0, v1, v2);
/* 31 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] getValue() {
/* 38 */     int i = getProgram();
/* 39 */     float[] afloat = this.programValues[i];
/* 40 */     return afloat;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 45 */     if (program >= this.programValues.length) {
/*    */       
/* 47 */       float[][] afloat = this.programValues;
/* 48 */       float[][] afloat1 = new float[program + 10][];
/* 49 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/* 50 */       this.programValues = afloat1;
/*    */     } 
/*    */     
/* 53 */     if (this.programValues[program] == null) {
/*    */       
/* 55 */       (new float[3])[0] = -3.4028235E38F; (new float[3])[1] = -3.4028235E38F; (new float[3])[2] = -3.4028235E38F; this.programValues[program] = new float[3];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 61 */     this.programValues = new float[][] { { -3.4028235E38F, -3.4028235E38F, -3.4028235E38F } };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniform3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */