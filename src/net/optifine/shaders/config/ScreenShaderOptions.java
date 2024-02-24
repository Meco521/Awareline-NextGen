/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ 
/*    */ public class ScreenShaderOptions
/*    */ {
/*    */   private final String name;
/*    */   private final ShaderOption[] shaderOptions;
/*    */   private final int columns;
/*    */   
/*    */   public ScreenShaderOptions(String name, ShaderOption[] shaderOptions, int columns) {
/* 11 */     this.name = name;
/* 12 */     this.shaderOptions = shaderOptions;
/* 13 */     this.columns = columns;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderOption[] getShaderOptions() {
/* 23 */     return this.shaderOptions;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColumns() {
/* 28 */     return this.columns;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ScreenShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */