/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform4f
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private float[][] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform4f(String name) {
/* 12 */     super(name);
/* 13 */     resetValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(float v0, float v1, float v2, float v3) {
/* 18 */     int i = getProgram();
/* 19 */     float[] afloat = this.programValues[i];
/*    */     
/* 21 */     if (afloat[0] != v0 || afloat[1] != v1 || afloat[2] != v2 || afloat[3] != v3) {
/*    */       
/* 23 */       afloat[0] = v0;
/* 24 */       afloat[1] = v1;
/* 25 */       afloat[2] = v2;
/* 26 */       afloat[3] = v3;
/* 27 */       int j = getLocation();
/*    */       
/* 29 */       if (j >= 0) {
/*    */         
/* 31 */         ARBShaderObjects.glUniform4fARB(j, v0, v1, v2, v3);
/* 32 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] getValue() {
/* 39 */     int i = getProgram();
/* 40 */     float[] afloat = this.programValues[i];
/* 41 */     return afloat;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 46 */     if (program >= this.programValues.length) {
/*    */       
/* 48 */       float[][] afloat = this.programValues;
/* 49 */       float[][] afloat1 = new float[program + 10][];
/* 50 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/* 51 */       this.programValues = afloat1;
/*    */     } 
/*    */     
/* 54 */     if (this.programValues[program] == null) {
/*    */       
/* 56 */       (new float[4])[0] = -3.4028235E38F; (new float[4])[1] = -3.4028235E38F; (new float[4])[2] = -3.4028235E38F; (new float[4])[3] = -3.4028235E38F; this.programValues[program] = new float[4];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 62 */     this.programValues = new float[][] { { -3.4028235E38F, -3.4028235E38F, -3.4028235E38F, -3.4028235E38F } };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniform4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */