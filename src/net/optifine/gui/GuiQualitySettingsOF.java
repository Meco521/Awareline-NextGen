/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiQualitySettingsOF
/*    */   extends GuiScreen {
/*    */   private final GuiScreen prevScreen;
/*    */   protected String title;
/*    */   private final GameSettings settings;
/* 15 */   private static final GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_ENTITIES, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY, GameSettings.Options.CUSTOM_ITEMS, GameSettings.Options.CUSTOM_ENTITY_MODELS, GameSettings.Options.CUSTOM_GUIS, GameSettings.Options.EMISSIVE_TEXTURES };
/* 16 */   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
/*    */ 
/*    */   
/*    */   public GuiQualitySettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
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
/* 30 */     this.title = I18n.format("of.options.qualityTitle", new Object[0]);
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
/* 49 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton guibutton) {
/* 57 */     if (guibutton.enabled) {
/*    */       
/* 59 */       if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
/*    */         
/* 61 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
/* 62 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/*    */       } 
/*    */       
/* 65 */       if (guibutton.id == 200) {
/*    */         
/* 67 */         this.mc.gameSettings.saveOptions();
/* 68 */         this.mc.displayGuiScreen(this.prevScreen);
/*    */       } 
/*    */       
/* 71 */       if (guibutton.id != GameSettings.Options.AA_LEVEL.ordinal()) {
/*    */         
/* 73 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 74 */         setWorldAndResolution(this.mc, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int x, int y, float f) {
/* 84 */     drawDefaultBackground();
/* 85 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/* 86 */     super.drawScreen(x, y, f);
/* 87 */     this.tooltipManager.drawTooltips(x, y, this.buttonList);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiQualitySettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */