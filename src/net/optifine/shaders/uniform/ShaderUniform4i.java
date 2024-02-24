/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform4i
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private int[][] programValues;
/*    */   private static final int VALUE_UNKNOWN = -2147483648;
/*    */   
/*    */   public ShaderUniform4i(String name) {
/* 12 */     super(name);
/* 13 */     resetValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(int v0, int v1, int v2, int v3) {
/* 18 */     int i = getProgram();
/* 19 */     int[] aint = this.programValues[i];
/*    */     
/* 21 */     if (aint[0] != v0 || aint[1] != v1 || aint[2] != v2 || aint[3] != v3) {
/*    */       
/* 23 */       aint[0] = v0;
/* 24 */       aint[1] = v1;
/* 25 */       aint[2] = v2;
/* 26 */       aint[3] = v3;
/* 27 */       int j = getLocation();
/*    */       
/* 29 */       if (j >= 0) {
/*    */         
/* 31 */         ARBShaderObjects.glUniform4iARB(j, v0, v1, v2, v3);
/* 32 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getValue() {
/* 39 */     int i = getProgram();
/* 40 */     int[] aint = this.programValues[i];
/* 41 */     return aint;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 46 */     if (program >= this.programValues.length) {
/*    */       
/* 48 */       int[][] aint = this.programValues;
/* 49 */       int[][] aint1 = new int[program + 10][];
/* 50 */       System.arraycopy(aint, 0, aint1, 0, aint.length);
/* 51 */       this.programValues = aint1;
/*    */     } 
/*    */     
/* 54 */     if (this.programValues[program] == null) {
/*    */       
/* 56 */       (new int[4])[0] = Integer.MIN_VALUE; (new int[4])[1] = Integer.MIN_VALUE; (new int[4])[2] = Integer.MIN_VALUE; (new int[4])[3] = Integer.MIN_VALUE; this.programValues[program] = new int[4];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 62 */     this.programValues = new int[][] { { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE } };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniform4i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */