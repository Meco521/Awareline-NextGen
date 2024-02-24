/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class ShaderOptionScreen
/*    */   extends ShaderOption
/*    */ {
/*    */   public ShaderOptionScreen(String name) {
/*  9 */     super(name, (String)null, (String)null, new String[0], (String)null, (String)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getNameText() {
/* 14 */     return Shaders.translate("screen." + getName(), getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescriptionText() {
/* 19 */     return Shaders.translate("screen." + getName() + ".comment", (String)null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderOptionScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */