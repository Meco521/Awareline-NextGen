/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ 
/*    */ public class ShaderMacro
/*    */ {
/*    */   private final String name;
/*    */   private final String value;
/*    */   
/*    */   public ShaderMacro(String name, String value) {
/* 10 */     this.name = name;
/* 11 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 16 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 21 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 26 */     return "#define " + this.name + " " + this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return getSourceLine();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderMacro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */