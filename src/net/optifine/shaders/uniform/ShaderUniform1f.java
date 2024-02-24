/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform1f
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private float[] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform1f(String name) {
/* 12 */     super(name);
/* 13 */     resetValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(float valueNew) {
/* 18 */     int i = getProgram();
/* 19 */     float f = this.programValues[i];
/*    */     
/* 21 */     if (valueNew != f) {
/*    */       
/* 23 */       this.programValues[i] = valueNew;
/* 24 */       int j = getLocation();
/*    */       
/* 26 */       if (j >= 0) {
/*    */         
/* 28 */         ARBShaderObjects.glUniform1fARB(j, valueNew);
/* 29 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue() {
/* 36 */     int i = getProgram();
/* 37 */     float f = this.programValues[i];
/* 38 */     return f;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 43 */     if (program >= this.programValues.length) {
/*    */       
/* 45 */       float[] afloat = this.programValues;
/* 46 */       float[] afloat1 = new float[program + 10];
/* 47 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/*    */       
/* 49 */       for (int i = afloat.length; i < afloat1.length; i++)
/*    */       {
/* 51 */         afloat1[i] = -3.4028235E38F;
/*    */       }
/*    */       
/* 54 */       this.programValues = afloat1;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 60 */     this.programValues = new float[] { -3.4028235E38F };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniform1f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */