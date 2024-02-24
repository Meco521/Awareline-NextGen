/*    */ package awareline.main.utility.shader.ketaUtils.render;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class GLShader
/*    */ {
/*    */   private int program;
/*    */   
/*    */   public int getProgram() {
/* 12 */     return this.program;
/*    */   }
/*    */   
/* 15 */   private final Map<String, Integer> uniformLocationMap = new HashMap<>();
/*    */   
/*    */   public GLShader(String vertexSource, String fragSource) {
/* 18 */     this.program = GL20.glCreateProgram();
/*    */ 
/*    */     
/* 21 */     GL20.glAttachShader(this.program, createShader(vertexSource, 35633));
/* 22 */     GL20.glAttachShader(this.program, createShader(fragSource, 35632));
/*    */     
/* 24 */     GL20.glLinkProgram(this.program);
/*    */     
/* 26 */     int status = GL20.glGetProgrami(this.program, 35714);
/*    */     
/* 28 */     if (status == 0) {
/*    */       
/* 30 */       this.program = -1;
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     setupUniforms();
/*    */   }
/*    */   
/*    */   private static int createShader(String source, int type) {
/* 38 */     int shader = GL20.glCreateShader(type);
/* 39 */     GL20.glShaderSource(shader, source);
/* 40 */     GL20.glCompileShader(shader);
/*    */     
/* 42 */     int status = GL20.glGetShaderi(shader, 35713);
/*    */     
/* 44 */     if (status == 0) {
/* 45 */       return -1;
/*    */     }
/*    */     
/* 48 */     return shader;
/*    */   }
/*    */ 
/*    */   
/*    */   public void use() {
/* 53 */     GL20.glUseProgram(this.program);
/* 54 */     updateUniforms();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {}
/*    */ 
/*    */   
/*    */   public void updateUniforms() {}
/*    */   
/*    */   public void setupUniform(String uniform) {
/* 64 */     this.uniformLocationMap.put(uniform, Integer.valueOf(GL20.glGetUniformLocation(this.program, uniform)));
/*    */   }
/*    */   
/*    */   public int getUniformLocation(String uniform) {
/* 68 */     return ((Integer)this.uniformLocationMap.get(uniform)).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\ketaUtils\render\GLShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */