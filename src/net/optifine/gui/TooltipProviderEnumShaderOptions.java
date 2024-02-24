/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.optifine.shaders.config.EnumShaderOption;
/*    */ import net.optifine.shaders.gui.GuiButtonEnumShaderOption;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TooltipProviderEnumShaderOptions
/*    */   implements TooltipProvider
/*    */ {
/*    */   public Rectangle getTooltipBounds(GuiScreen guiScreen, int x, int y) {
/* 15 */     int i = guiScreen.width - 450;
/* 16 */     int j = 35;
/*    */     
/* 18 */     if (i < 10)
/*    */     {
/* 20 */       i = 10;
/*    */     }
/*    */     
/* 23 */     if (y <= j + 94)
/*    */     {
/* 25 */       j += 100;
/*    */     }
/*    */     
/* 28 */     int k = i + 150 + 150;
/* 29 */     int l = j + 84 + 10;
/* 30 */     return new Rectangle(i, j, k - i, l - j);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRenderBorder() {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getTooltipLines(GuiButton btn, int width) {
/* 40 */     if (btn instanceof net.optifine.shaders.gui.GuiButtonDownloadShaders)
/*    */     {
/* 42 */       return TooltipProviderOptions.getTooltipLines("of.options.shaders.DOWNLOAD");
/*    */     }
/* 44 */     if (!(btn instanceof GuiButtonEnumShaderOption))
/*    */     {
/* 46 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 50 */     GuiButtonEnumShaderOption guibuttonenumshaderoption = (GuiButtonEnumShaderOption)btn;
/* 51 */     EnumShaderOption enumshaderoption = guibuttonenumshaderoption.getEnumShaderOption();
/* 52 */     String[] astring = getTooltipLines(enumshaderoption);
/* 53 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private String[] getTooltipLines(EnumShaderOption option) {
/* 59 */     return TooltipProviderOptions.getTooltipLines(option.getResourceKey());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\TooltipProviderEnumShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */