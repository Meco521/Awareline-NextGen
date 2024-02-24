/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.optifine.Lang;
/*    */ 
/*    */ 
/*    */ public class TooltipProviderOptions
/*    */   implements TooltipProvider
/*    */ {
/*    */   public Rectangle getTooltipBounds(GuiScreen guiScreen, int x, int y) {
/* 16 */     int i = guiScreen.width / 2 - 150;
/* 17 */     int j = guiScreen.height / 6 - 7;
/*    */     
/* 19 */     if (y <= j + 98)
/*    */     {
/* 21 */       j += 105;
/*    */     }
/*    */     
/* 24 */     int k = i + 150 + 150;
/* 25 */     int l = j + 84 + 10;
/* 26 */     return new Rectangle(i, j, k - i, l - j);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRenderBorder() {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getTooltipLines(GuiButton btn, int width) {
/* 36 */     if (!(btn instanceof IOptionControl))
/*    */     {
/* 38 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 42 */     IOptionControl ioptioncontrol = (IOptionControl)btn;
/* 43 */     GameSettings.Options gamesettings$options = ioptioncontrol.getOption();
/* 44 */     String[] astring = getTooltipLines(gamesettings$options.getEnumString());
/* 45 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String[] getTooltipLines(String key) {
/* 51 */     List<String> list = new ArrayList<>();
/*    */     
/* 53 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 55 */       String s = key + ".tooltip." + (i + 1);
/* 56 */       String s1 = Lang.get(s, (String)null);
/*    */       
/* 58 */       if (s1 == null) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 63 */       list.add(s1);
/*    */     } 
/*    */     
/* 66 */     if (list.size() <= 0)
/*    */     {
/* 68 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 72 */     String[] astring = list.<String>toArray(new String[0]);
/* 73 */     return astring;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\TooltipProviderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */