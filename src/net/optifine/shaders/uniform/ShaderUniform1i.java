/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform1i
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private int[] programValues;
/*    */   private static final int VALUE_UNKNOWN = -2147483648;
/*    */   
/*    */   public ShaderUniform1i(String name) {
/* 12 */     super(name);
/* 13 */     resetValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(int valueNew) {
/* 18 */     int i = getProgram();
/* 19 */     int j = this.programValues[i];
/*    */     
/* 21 */     if (valueNew != j) {
/*    */       
/* 23 */       this.programValues[i] = valueNew;
/* 24 */       int k = getLocation();
/*    */       
/* 26 */       if (k >= 0) {
/*    */         
/* 28 */         ARBShaderObjects.glUniform1iARB(k, valueNew);
/* 29 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 36 */     int i = getProgram();
/* 37 */     int j = this.programValues[i];
/* 38 */     return j;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 43 */     if (program >= this.programValues.length) {
/*    */       
/* 45 */       int[] aint = this.programValues;
/* 46 */       int[] aint1 = new int[program + 10];
/* 47 */       System.arraycopy(aint, 0, aint1, 0, aint.length);
/*    */       
/* 49 */       for (int i = aint.length; i < aint1.length; i++)
/*    */       {
/* 51 */         aint1[i] = Integer.MIN_VALUE;
/*    */       }
/*    */       
/* 54 */       this.programValues = aint1;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 60 */     this.programValues = new int[] { Integer.MIN_VALUE };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniform1i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */