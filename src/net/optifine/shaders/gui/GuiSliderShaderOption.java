/*    */ package net.optifine.shaders.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.optifine.shaders.config.ShaderOption;
/*    */ 
/*    */ public class GuiSliderShaderOption
/*    */   extends GuiButtonShaderOption {
/* 11 */   private float sliderValue = 1.0F;
/*    */   public boolean dragging;
/* 13 */   private ShaderOption shaderOption = null;
/*    */ 
/*    */   
/*    */   public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
/* 17 */     super(buttonId, x, y, w, h, shaderOption, text);
/* 18 */     this.shaderOption = shaderOption;
/* 19 */     this.sliderValue = shaderOption.getIndexNormalized();
/* 20 */     this.displayString = GuiShaderOptions.getButtonText(shaderOption, this.width);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getHoverState(boolean mouseOver) {
/* 29 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 37 */     if (this.visible) {
/*    */       
/* 39 */       if (this.dragging && !GuiScreen.isShiftKeyDown()) {
/*    */         
/* 41 */         this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 42 */         this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
/* 43 */         this.shaderOption.setIndexNormalized(this.sliderValue);
/* 44 */         this.sliderValue = this.shaderOption.getIndexNormalized();
/* 45 */         this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
/*    */       } 
/*    */       
/* 48 */       mc.getTextureManager().bindTexture(buttonTextures);
/* 49 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 50 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 51 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 61 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/*    */       
/* 63 */       this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 64 */       this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
/* 65 */       this.shaderOption.setIndexNormalized(this.sliderValue);
/* 66 */       this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
/* 67 */       this.dragging = true;
/* 68 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY) {
/* 81 */     this.dragging = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void valueChanged() {
/* 86 */     this.sliderValue = this.shaderOption.getIndexNormalized();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSwitchable() {
/* 91 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\gui\GuiSliderShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */