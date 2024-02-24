/*    */ package net.optifine.shaders.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.optifine.shaders.Shaders;
/*    */ import net.optifine.shaders.config.EnumShaderOption;
/*    */ 
/*    */ public class GuiButtonEnumShaderOption
/*    */   extends GuiButton {
/* 10 */   private EnumShaderOption enumShaderOption = null;
/*    */ 
/*    */   
/*    */   public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int x, int y, int widthIn, int heightIn) {
/* 14 */     super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
/* 15 */     this.enumShaderOption = enumShaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumShaderOption getEnumShaderOption() {
/* 20 */     return this.enumShaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getButtonText(EnumShaderOption eso) {
/* 25 */     String s = I18n.format(eso.getResourceKey(), new Object[0]) + ": ";
/*    */     
/* 27 */     switch (eso) {
/*    */       
/*    */       case ANTIALIASING:
/* 30 */         return s + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
/*    */       
/*    */       case NORMAL_MAP:
/* 33 */         return s + GuiShaders.toStringOnOff(Shaders.configNormalMap);
/*    */       
/*    */       case SPECULAR_MAP:
/* 36 */         return s + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
/*    */       
/*    */       case RENDER_RES_MUL:
/* 39 */         return s + GuiShaders.toStringQuality(Shaders.configRenderResMul);
/*    */       
/*    */       case SHADOW_RES_MUL:
/* 42 */         return s + GuiShaders.toStringQuality(Shaders.configShadowResMul);
/*    */       
/*    */       case HAND_DEPTH_MUL:
/* 45 */         return s + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
/*    */       
/*    */       case CLOUD_SHADOW:
/* 48 */         return s + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
/*    */       
/*    */       case OLD_HAND_LIGHT:
/* 51 */         return s + Shaders.configOldHandLight.getUserValue();
/*    */       
/*    */       case OLD_LIGHTING:
/* 54 */         return s + Shaders.configOldLighting.getUserValue();
/*    */       
/*    */       case SHADOW_CLIP_FRUSTRUM:
/* 57 */         return s + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
/*    */       
/*    */       case TWEAK_BLOCK_DAMAGE:
/* 60 */         return s + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
/*    */     } 
/*    */     
/* 63 */     return s + Shaders.getEnumShaderOption(eso);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateButtonText() {
/* 69 */     this.displayString = getButtonText(this.enumShaderOption);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\gui\GuiButtonEnumShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */