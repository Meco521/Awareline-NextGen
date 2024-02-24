/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiYesNo
/*     */   extends GuiScreen
/*     */ {
/*     */   protected GuiYesNoCallback parentScreen;
/*     */   protected String messageLine1;
/*     */   private final String messageLine2;
/*  16 */   private final List<String> field_175298_s = Lists.newArrayList();
/*     */   
/*     */   protected String confirmButtonText;
/*     */   
/*     */   protected String cancelButtonText;
/*     */   
/*     */   protected int parentButtonClickedId;
/*     */   
/*     */   private int ticksUntilEnable;
/*     */ 
/*     */   
/*     */   public GuiYesNo(GuiYesNoCallback p_i1082_1_, String p_i1082_2_, String p_i1082_3_, int p_i1082_4_) {
/*  28 */     this.parentScreen = p_i1082_1_;
/*  29 */     this.messageLine1 = p_i1082_2_;
/*  30 */     this.messageLine2 = p_i1082_3_;
/*  31 */     this.parentButtonClickedId = p_i1082_4_;
/*  32 */     this.confirmButtonText = I18n.format("gui.yes", new Object[0]);
/*  33 */     this.cancelButtonText = I18n.format("gui.no", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiYesNo(GuiYesNoCallback p_i1083_1_, String p_i1083_2_, String p_i1083_3_, String p_i1083_4_, String p_i1083_5_, int p_i1083_6_) {
/*  38 */     this.parentScreen = p_i1083_1_;
/*  39 */     this.messageLine1 = p_i1083_2_;
/*  40 */     this.messageLine2 = p_i1083_3_;
/*  41 */     this.confirmButtonText = p_i1083_4_;
/*  42 */     this.cancelButtonText = p_i1083_5_;
/*  43 */     this.parentButtonClickedId = p_i1083_6_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  52 */     this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 155, this.height / 6 + 96, this.confirmButtonText));
/*  53 */     this.buttonList.add(new GuiOptionButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.cancelButtonText));
/*  54 */     this.field_175298_s.clear();
/*  55 */     this.field_175298_s.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  62 */     this.parentScreen.confirmClicked((button.id == 0), this.parentButtonClickedId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  70 */     drawDefaultBackground();
/*  71 */     drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
/*  72 */     int i = 90;
/*     */     
/*  74 */     for (String s : this.field_175298_s) {
/*     */       
/*  76 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
/*  77 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */     
/*  80 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setButtonDelay(int p_146350_1_) {
/*  88 */     this.ticksUntilEnable = p_146350_1_;
/*     */     
/*  90 */     for (GuiButton guibutton : this.buttonList)
/*     */     {
/*  92 */       guibutton.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 101 */     super.updateScreen();
/*     */     
/* 103 */     if (--this.ticksUntilEnable == 0)
/*     */     {
/* 105 */       for (GuiButton guibutton : this.buttonList)
/*     */       {
/* 107 */         guibutton.enabled = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiYesNo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */