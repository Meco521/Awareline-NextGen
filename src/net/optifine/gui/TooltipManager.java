/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ public class TooltipManager
/*    */ {
/*    */   private final GuiScreen guiScreen;
/*    */   private final TooltipProvider tooltipProvider;
/* 17 */   private int lastMouseX = 0;
/* 18 */   private int lastMouseY = 0;
/* 19 */   private long mouseStillTime = 0L;
/*    */ 
/*    */   
/*    */   public TooltipManager(GuiScreen guiScreen, TooltipProvider tooltipProvider) {
/* 23 */     this.guiScreen = guiScreen;
/* 24 */     this.tooltipProvider = tooltipProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawTooltips(int x, int y, List<GuiButton> buttonList) {
/* 29 */     if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
/*    */       
/* 31 */       int i = 700;
/*    */       
/* 33 */       if (System.currentTimeMillis() >= this.mouseStillTime + i) {
/*    */         
/* 35 */         GuiButton guibutton = GuiScreenOF.getSelectedButton(x, y, buttonList);
/*    */         
/* 37 */         if (guibutton != null) {
/*    */           
/* 39 */           Rectangle rectangle = this.tooltipProvider.getTooltipBounds(this.guiScreen, x, y);
/* 40 */           String[] astring = this.tooltipProvider.getTooltipLines(guibutton, rectangle.width);
/*    */           
/* 42 */           if (astring != null) {
/*    */             
/* 44 */             if (astring.length > 8) {
/*    */               
/* 46 */               astring = Arrays.<String>copyOf(astring, 8);
/* 47 */               astring[astring.length - 1] = astring[astring.length - 1] + " ...";
/*    */             } 
/*    */             
/* 50 */             if (this.tooltipProvider.isRenderBorder()) {
/*    */               
/* 52 */               int j = -528449408;
/* 53 */               drawRectBorder(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, j);
/*    */             } 
/*    */             
/* 56 */             Gui.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, -536870912);
/*    */             
/* 58 */             for (int l = 0; l < astring.length; l++)
/*    */             {
/* 60 */               String s = astring[l];
/* 61 */               int k = 14540253;
/*    */               
/* 63 */               if (!s.isEmpty() && s.charAt(s.length() - 1) == '!')
/*    */               {
/* 65 */                 k = 16719904;
/*    */               }
/*    */               
/* 68 */               FontRenderer fontrenderer = (Minecraft.getMinecraft()).fontRendererObj;
/* 69 */               fontrenderer.drawStringWithShadow(s, (rectangle.x + 5), (rectangle.y + 5 + l * 11), k);
/*    */             }
/*    */           
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } else {
/*    */       
/* 77 */       this.lastMouseX = x;
/* 78 */       this.lastMouseY = y;
/* 79 */       this.mouseStillTime = System.currentTimeMillis();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void drawRectBorder(int x1, int y1, int x2, int y2, int col) {
/* 85 */     Gui.drawRect(x1, y1 - 1, x2, y1, col);
/* 86 */     Gui.drawRect(x1, y2, x2, y2 + 1, col);
/* 87 */     Gui.drawRect(x1 - 1, y1, x1, y2, col);
/* 88 */     Gui.drawRect(x2, y1, x2 + 1, y2, col);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\TooltipManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */