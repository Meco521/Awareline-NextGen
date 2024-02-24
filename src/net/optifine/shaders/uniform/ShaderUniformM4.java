/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ 
/*    */ public class ShaderUniformM4
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private boolean transpose;
/*    */   private FloatBuffer matrix;
/*    */   
/*    */   public ShaderUniformM4(String name) {
/* 14 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(boolean transpose, FloatBuffer matrix) {
/* 19 */     this.transpose = transpose;
/* 20 */     this.matrix = matrix;
/* 21 */     int i = getLocation();
/*    */     
/* 23 */     if (i >= 0) {
/*    */       
/* 25 */       ARBShaderObjects.glUniformMatrix4ARB(i, transpose, matrix);
/* 26 */       checkGLError();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue(int row, int col) {
/* 32 */     if (this.matrix == null)
/*    */     {
/* 34 */       return 0.0F;
/*    */     }
/*    */ 
/*    */     
/* 38 */     int i = this.transpose ? ((col << 2) + row) : ((row << 2) + col);
/* 39 */     float f = this.matrix.get(i);
/* 40 */     return f;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 50 */     this.matrix = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniformM4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */