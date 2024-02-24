/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ShaderUniforms
/*    */ {
/*  8 */   private final List<ShaderUniformBase> listUniforms = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 12 */     for (int i = 0; i < this.listUniforms.size(); i++) {
/*    */       
/* 14 */       ShaderUniformBase shaderuniformbase = this.listUniforms.get(i);
/* 15 */       shaderuniformbase.setProgram(program);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 21 */     for (int i = 0; i < this.listUniforms.size(); i++) {
/*    */       
/* 23 */       ShaderUniformBase shaderuniformbase = this.listUniforms.get(i);
/* 24 */       shaderuniformbase.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniform1i make1i(String name) {
/* 30 */     ShaderUniform1i shaderuniform1i = new ShaderUniform1i(name);
/* 31 */     this.listUniforms.add(shaderuniform1i);
/* 32 */     return shaderuniform1i;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniform2i make2i(String name) {
/* 37 */     ShaderUniform2i shaderuniform2i = new ShaderUniform2i(name);
/* 38 */     this.listUniforms.add(shaderuniform2i);
/* 39 */     return shaderuniform2i;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniform4i make4i(String name) {
/* 44 */     ShaderUniform4i shaderuniform4i = new ShaderUniform4i(name);
/* 45 */     this.listUniforms.add(shaderuniform4i);
/* 46 */     return shaderuniform4i;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniform1f make1f(String name) {
/* 51 */     ShaderUniform1f shaderuniform1f = new ShaderUniform1f(name);
/* 52 */     this.listUniforms.add(shaderuniform1f);
/* 53 */     return shaderuniform1f;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniform3f make3f(String name) {
/* 58 */     ShaderUniform3f shaderuniform3f = new ShaderUniform3f(name);
/* 59 */     this.listUniforms.add(shaderuniform3f);
/* 60 */     return shaderuniform3f;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniform4f make4f(String name) {
/* 65 */     ShaderUniform4f shaderuniform4f = new ShaderUniform4f(name);
/* 66 */     this.listUniforms.add(shaderuniform4f);
/* 67 */     return shaderuniform4f;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderUniformM4 makeM4(String name) {
/* 72 */     ShaderUniformM4 shaderuniformm4 = new ShaderUniformM4(name);
/* 73 */     this.listUniforms.add(shaderuniformm4);
/* 74 */     return shaderuniformm4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniforms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */