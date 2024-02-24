/*    */ package net.optifine.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class GuiMessage
/*    */   extends GuiScreen
/*    */ {
/*    */   private final GuiScreen parentScreen;
/*    */   private final String messageLine1;
/*    */   private final String messageLine2;
/* 17 */   private final List listLines2 = Lists.newArrayList();
/*    */   
/*    */   protected String confirmButtonText;
/*    */   private int ticksUntilEnable;
/*    */   
/*    */   public GuiMessage(GuiScreen parentScreen, String line1, String line2) {
/* 23 */     this.parentScreen = parentScreen;
/* 24 */     this.messageLine1 = line1;
/* 25 */     this.messageLine2 = line2;
/* 26 */     this.confirmButtonText = I18n.format("gui.done", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 35 */     this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 74, this.height / 6 + 96, this.confirmButtonText));
/* 36 */     this.listLines2.clear();
/* 37 */     this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 44 */     Config.getMinecraft().displayGuiScreen(this.parentScreen);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 52 */     drawDefaultBackground();
/* 53 */     drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
/* 54 */     int i = 90;
/*    */     
/* 56 */     for (Object s : this.listLines2) {
/*    */       
/* 58 */       drawCenteredString(this.fontRendererObj, (String)s, this.width / 2, i, 16777215);
/* 59 */       i += this.fontRendererObj.FONT_HEIGHT;
/*    */     } 
/*    */     
/* 62 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setButtonDelay(int ticksUntilEnable) {
/* 67 */     this.ticksUntilEnable = ticksUntilEnable;
/*    */     
/* 69 */     for (GuiButton guibutton : this.buttonList)
/*    */     {
/* 71 */       guibutton.enabled = false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 80 */     super.updateScreen();
/*    */     
/* 82 */     if (--this.ticksUntilEnable == 0)
/*    */     {
/* 84 */       for (GuiButton guibutton : this.buttonList)
/*    */       {
/* 86 */         guibutton.enabled = true;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */