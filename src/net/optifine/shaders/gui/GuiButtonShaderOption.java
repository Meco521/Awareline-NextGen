/*    */ package net.optifine.shaders.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.optifine.shaders.config.ShaderOption;
/*    */ 
/*    */ public class GuiButtonShaderOption
/*    */   extends GuiButton {
/*  8 */   private ShaderOption shaderOption = null;
/*    */ 
/*    */   
/*    */   public GuiButtonShaderOption(int buttonId, int x, int y, int widthIn, int heightIn, ShaderOption shaderOption, String text) {
/* 12 */     super(buttonId, x, y, widthIn, heightIn, text);
/* 13 */     this.shaderOption = shaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderOption getShaderOption() {
/* 18 */     return this.shaderOption;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void valueChanged() {}
/*    */ 
/*    */   
/*    */   public boolean isSwitchable() {
/* 27 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\gui\GuiButtonShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */