/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderPackDefault
/*    */   implements IShaderPack
/*    */ {
/*    */   public void close() {}
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/* 13 */     return ShaderPackDefault.class.getResourceAsStream(resName);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return "(internal)";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 23 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ShaderPackDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */