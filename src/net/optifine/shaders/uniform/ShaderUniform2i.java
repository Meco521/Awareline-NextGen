/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform2i
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private int[][] programValues;
/*    */   private static final int VALUE_UNKNOWN = -2147483648;
/*    */   
/*    */   public ShaderUniform2i(String name) {
/* 12 */     super(name);
/* 13 */     resetValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(int v0, int v1) {
/* 18 */     int i = getProgram();
/* 19 */     int[] aint = this.programValues[i];
/*    */     
/* 21 */     if (aint[0] != v0 || aint[1] != v1) {
/*    */       
/* 23 */       aint[0] = v0;
/* 24 */       aint[1] = v1;
/* 25 */       int j = getLocation();
/*    */       
/* 27 */       if (j >= 0) {
/*    */         
/* 29 */         ARBShaderObjects.glUniform2iARB(j, v0, v1);
/* 30 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getValue() {
/* 37 */     int i = getProgram();
/* 38 */     int[] aint = this.programValues[i];
/* 39 */     return aint;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 44 */     if (program >= this.programValues.length) {
/*    */       
/* 46 */       int[][] aint = this.programValues;
/* 47 */       int[][] aint1 = new int[program + 10][];
/* 48 */       System.arraycopy(aint, 0, aint1, 0, aint.length);
/* 49 */       this.programValues = aint1;
/*    */     } 
/*    */     
/* 52 */     if (this.programValues[program] == null) {
/*    */       
/* 54 */       (new int[2])[0] = Integer.MIN_VALUE; (new int[2])[1] = Integer.MIN_VALUE; this.programValues[program] = new int[2];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 60 */     this.programValues = new int[][] { { Integer.MIN_VALUE, Integer.MIN_VALUE } };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniform2i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */