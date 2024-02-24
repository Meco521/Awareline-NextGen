/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform2f
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private float[][] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform2f(String name) {
/* 12 */     super(name);
/* 13 */     resetValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(float v0, float v1) {
/* 18 */     int i = getProgram();
/* 19 */     float[] afloat = this.programValues[i];
/*    */     
/* 21 */     if (afloat[0] != v0 || afloat[1] != v1) {
/*    */       
/* 23 */       afloat[0] = v0;
/* 24 */       afloat[1] = v1;
/* 25 */       int j = getLocation();
/*    */       
/* 27 */       if (j >= 0) {
/*    */         
/* 29 */         ARBShaderObjects.glUniform2fARB(j, v0, v1);
/* 30 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] getValue() {
/* 37 */     int i = getProgram();
/* 38 */     float[] afloat = this.programValues[i];
/* 39 */     return afloat;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 44 */     if (program >= this.programValues.length) {
/*    */       
/* 46 */       float[][] afloat = this.programValues;
/* 47 */       float[][] afloat1 = new float[program + 10][];
/* 48 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/* 49 */       this.programValues = afloat1;
/*    */     } 
/*    */     
/* 52 */     if (this.programValues[program] == null) {
/*    */       
/* 54 */       (new float[2])[0] = -3.4028235E38F; (new float[2])[1] = -3.4028235E38F; this.programValues[program] = new float[2];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 60 */     this.programValues = new float[][] { { -3.4028235E38F, -3.4028235E38F } };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniform2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */