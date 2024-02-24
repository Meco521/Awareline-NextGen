/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiYesNo;
/*    */ import net.minecraft.client.gui.GuiYesNoCallback;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiOtherSettingsOF
/*    */   extends GuiScreen {
/*    */   private final GuiScreen prevScreen;
/* 14 */   private static final GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.SHOW_FPS, GameSettings.Options.ADVANCED_TOOLTIPS, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.ANAGLYPH, GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.SCREENSHOT_SIZE, GameSettings.Options.SHOW_GL_ERRORS }; protected String title;
/*    */   private final GameSettings settings;
/* 16 */   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
/*    */ 
/*    */   
/*    */   public GuiOtherSettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
/* 20 */     this.prevScreen = guiscreen;
/* 21 */     this.settings = gamesettings;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 30 */     this.title = I18n.format("of.options.otherTitle", new Object[0]);
/* 31 */     this.buttonList.clear();
/*    */     
/* 33 */     for (int i = 0; i < enumOptions.length; i++) {
/*    */       
/* 35 */       GameSettings.Options gamesettings$options = enumOptions[i];
/* 36 */       int j = this.width / 2 - 155 + i % 2 * 160;
/* 37 */       int k = this.height / 6 + 21 * i / 2 - 12;
/*    */       
/* 39 */       if (!gamesettings$options.getEnumFloat()) {
/*    */         
/* 41 */         this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*    */       }
/*    */       else {
/*    */         
/* 45 */         this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
/* 50 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton guibutton) {
/* 58 */     if (guibutton.enabled) {
/*    */       
/* 60 */       if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
/*    */         
/* 62 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
/* 63 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/*    */       } 
/*    */       
/* 66 */       if (guibutton.id == 200) {
/*    */         
/* 68 */         this.mc.gameSettings.saveOptions();
/* 69 */         this.mc.displayGuiScreen(this.prevScreen);
/*    */       } 
/*    */       
/* 72 */       if (guibutton.id == 210) {
/*    */         
/* 74 */         this.mc.gameSettings.saveOptions();
/* 75 */         GuiYesNo guiyesno = new GuiYesNo((GuiYesNoCallback)this, I18n.format("of.message.other.reset", new Object[0]), "", 9999);
/* 76 */         this.mc.displayGuiScreen((GuiScreen)guiyesno);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void confirmClicked(boolean flag, int i) {
/* 83 */     if (flag)
/*    */     {
/* 85 */       this.mc.gameSettings.resetSettings();
/*    */     }
/*    */     
/* 88 */     this.mc.displayGuiScreen(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int x, int y, float f) {
/* 96 */     drawDefaultBackground();
/* 97 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/* 98 */     super.drawScreen(x, y, f);
/* 99 */     this.tooltipManager.drawTooltips(x, y, this.buttonList);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiOtherSettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */