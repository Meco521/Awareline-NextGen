/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiPerformanceSettingsOF
/*    */   extends GuiScreen {
/*    */   private final GuiScreen prevScreen;
/*    */   protected String title;
/*    */   private final GameSettings settings;
/* 14 */   private static final GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.RENDER_REGIONS, GameSettings.Options.LAZY_CHUNK_LOADING, GameSettings.Options.SMART_ANIMATIONS };
/* 15 */   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
/*    */ 
/*    */   
/*    */   public GuiPerformanceSettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
/* 19 */     this.prevScreen = guiscreen;
/* 20 */     this.settings = gamesettings;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 29 */     this.title = I18n.format("of.options.performanceTitle", new Object[0]);
/* 30 */     this.buttonList.clear();
/*    */     
/* 32 */     for (int i = 0; i < enumOptions.length; i++) {
/*    */       
/* 34 */       GameSettings.Options gamesettings$options = enumOptions[i];
/* 35 */       int j = this.width / 2 - 155 + i % 2 * 160;
/* 36 */       int k = this.height / 6 + 21 * i / 2 - 12;
/*    */       
/* 38 */       if (!gamesettings$options.getEnumFloat()) {
/*    */         
/* 40 */         this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*    */       }
/*    */       else {
/*    */         
/* 44 */         this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton guibutton) {
/* 56 */     if (guibutton.enabled) {
/*    */       
/* 58 */       if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
/*    */         
/* 60 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
/* 61 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/*    */       } 
/*    */       
/* 64 */       if (guibutton.id == 200) {
/*    */         
/* 66 */         this.mc.gameSettings.saveOptions();
/* 67 */         this.mc.displayGuiScreen(this.prevScreen);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int x, int y, float f) {
/* 77 */     drawDefaultBackground();
/* 78 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/* 79 */     super.drawScreen(x, y, f);
/* 80 */     this.tooltipManager.drawTooltips(x, y, this.buttonList);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiPerformanceSettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */