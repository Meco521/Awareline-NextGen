/*    */ package net.minecraft.client.shader;
/*    */ 
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.util.JsonException;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ShaderLinkHelper
/*    */ {
/* 10 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/*    */   private static ShaderLinkHelper staticShaderLinkHelper;
/*    */   
/*    */   public static void setNewStaticShaderLinkHelper() {
/* 15 */     staticShaderLinkHelper = new ShaderLinkHelper();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderLinkHelper getStaticShaderLinkHelper() {
/* 20 */     return staticShaderLinkHelper;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteShader(ShaderManager p_148077_1_) {
/* 25 */     p_148077_1_.getFragmentShaderLoader().deleteShader(p_148077_1_);
/* 26 */     p_148077_1_.getVertexShaderLoader().deleteShader(p_148077_1_);
/* 27 */     OpenGlHelper.glDeleteProgram(p_148077_1_.getProgram());
/*    */   }
/*    */ 
/*    */   
/*    */   public int createProgram() throws JsonException {
/* 32 */     int i = OpenGlHelper.glCreateProgram();
/*    */     
/* 34 */     if (i <= 0)
/*    */     {
/* 36 */       throw new JsonException("Could not create shader program (returned program ID " + i + ")");
/*    */     }
/*    */ 
/*    */     
/* 40 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public void linkProgram(ShaderManager manager) {
/* 45 */     manager.getFragmentShaderLoader().attachShader(manager);
/* 46 */     manager.getVertexShaderLoader().attachShader(manager);
/* 47 */     OpenGlHelper.glLinkProgram(manager.getProgram());
/* 48 */     int i = OpenGlHelper.glGetProgrami(manager.getProgram(), OpenGlHelper.GL_LINK_STATUS);
/*    */     
/* 50 */     if (i == 0) {
/*    */       
/* 52 */       logger.warn("Error encountered when linking program containing VS " + manager.getVertexShaderLoader().getShaderFilename() + " and FS " + manager.getFragmentShaderLoader().getShaderFilename() + ". Log output:");
/* 53 */       logger.warn(OpenGlHelper.glGetProgramInfoLog(manager.getProgram(), 32768));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\shader\ShaderLinkHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */