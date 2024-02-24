/*     */ package net.minecraft.client.gui;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.GuiAnimationSettingsOF;
/*     */ import net.optifine.gui.GuiDetailSettingsOF;
/*     */ import net.optifine.gui.GuiOtherSettingsOF;
/*     */ import net.optifine.gui.GuiPerformanceSettingsOF;
/*     */ import net.optifine.gui.GuiQualitySettingsOF;
/*     */ import net.optifine.shaders.gui.GuiShaders;
/*     */ 
/*     */ public class GuiVideoSettings extends GuiScreenOF {
/*  13 */   protected String screenTitle = "Video Settings";
/*     */   
/*     */   private final GuiScreen parentGuiScreen;
/*     */   private final GameSettings guiGameSettings;
/*  17 */   private static final GameSettings.Options[] videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.DYNAMIC_LIGHTS, GameSettings.Options.DYNAMIC_FOV };
/*  18 */   private final TooltipManager tooltipManager = new TooltipManager((GuiScreen)this, (TooltipProvider)new TooltipProviderOptions());
/*     */ 
/*     */   
/*     */   public GuiVideoSettings(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/*  22 */     this.parentGuiScreen = parentScreenIn;
/*  23 */     this.guiGameSettings = gameSettingsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  32 */     this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
/*  33 */     this.buttonList.clear();
/*     */     
/*  35 */     for (int i = 0; i < videoOptions.length; i++) {
/*     */       
/*  37 */       GameSettings.Options gamesettings$options = videoOptions[i];
/*     */       
/*  39 */       int j = this.width / 2 - 155 + i % 2 * 160;
/*  40 */       int k = this.height / 6 + 21 * i / 2 - 12;
/*     */       
/*  42 */       if (gamesettings$options.getEnumFloat()) {
/*     */         
/*  44 */         this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*     */       }
/*     */       else {
/*     */         
/*  48 */         this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.guiGameSettings.getKeyBinding(gamesettings$options)));
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     int l = this.height / 6 + 21 * videoOptions.length / 2 - 12;
/*     */     
/*  54 */     int i1 = this.width / 2 - 155;
/*  55 */     this.buttonList.add(new GuiOptionButton(231, i1, l, Lang.get("of.options.shaders")));
/*  56 */     i1 = this.width / 2 - 155 + 160;
/*  57 */     this.buttonList.add(new GuiOptionButton(202, i1, l, Lang.get("of.options.quality")));
/*  58 */     l += 21;
/*  59 */     i1 = this.width / 2 - 155;
/*  60 */     this.buttonList.add(new GuiOptionButton(201, i1, l, Lang.get("of.options.details")));
/*  61 */     i1 = this.width / 2 - 155 + 160;
/*  62 */     this.buttonList.add(new GuiOptionButton(212, i1, l, Lang.get("of.options.performance")));
/*  63 */     l += 21;
/*  64 */     i1 = this.width / 2 - 155;
/*  65 */     this.buttonList.add(new GuiOptionButton(211, i1, l, Lang.get("of.options.animations")));
/*  66 */     i1 = this.width / 2 - 155 + 160;
/*  67 */     this.buttonList.add(new GuiOptionButton(222, i1, l, Lang.get("of.options.other")));
/*  68 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  75 */     actionPerformed(button, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton p_actionPerformedRightClick_1_) {
/*  80 */     if (p_actionPerformedRightClick_1_.id == GameSettings.Options.GUI_SCALE.ordinal())
/*     */     {
/*  82 */       actionPerformed(p_actionPerformedRightClick_1_, -1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void actionPerformed(GuiButton p_actionPerformed_1_, int p_actionPerformed_2_) {
/*  88 */     if (p_actionPerformed_1_.enabled) {
/*     */       
/*  90 */       int i = this.guiGameSettings.guiScale;
/*     */       
/*  92 */       if (p_actionPerformed_1_.id < 200 && p_actionPerformed_1_ instanceof GuiOptionButton) {
/*     */         
/*  94 */         this.guiGameSettings.setOptionValue(((GuiOptionButton)p_actionPerformed_1_).returnEnumOptions(), p_actionPerformed_2_);
/*  95 */         p_actionPerformed_1_.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(p_actionPerformed_1_.id));
/*     */       } 
/*     */       
/*  98 */       if (p_actionPerformed_1_.id == 200) {
/*     */         
/* 100 */         this.mc.gameSettings.saveOptions();
/* 101 */         this.mc.displayGuiScreen(this.parentGuiScreen);
/*     */       } 
/*     */       
/* 104 */       if (this.guiGameSettings.guiScale != i) {
/*     */         
/* 106 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 107 */         int j = scaledresolution.getScaledWidth();
/* 108 */         int k = scaledresolution.getScaledHeight();
/* 109 */         setWorldAndResolution(this.mc, j, k);
/*     */       } 
/*     */       
/* 112 */       if (p_actionPerformed_1_.id == 201) {
/*     */         
/* 114 */         this.mc.gameSettings.saveOptions();
/* 115 */         GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 116 */         this.mc.displayGuiScreen((GuiScreen)guidetailsettingsof);
/*     */       } 
/*     */       
/* 119 */       if (p_actionPerformed_1_.id == 202) {
/*     */         
/* 121 */         this.mc.gameSettings.saveOptions();
/* 122 */         GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF((GuiScreen)this, this.guiGameSettings);
/* 123 */         this.mc.displayGuiScreen((GuiScreen)guiqualitysettingsof);
/*     */       } 
/*     */       
/* 126 */       if (p_actionPerformed_1_.id == 211) {
/*     */         
/* 128 */         this.mc.gameSettings.saveOptions();
/* 129 */         GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 130 */         this.mc.displayGuiScreen((GuiScreen)guianimationsettingsof);
/*     */       } 
/*     */       
/* 133 */       if (p_actionPerformed_1_.id == 212) {
/*     */         
/* 135 */         this.mc.gameSettings.saveOptions();
/* 136 */         GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 137 */         this.mc.displayGuiScreen((GuiScreen)guiperformancesettingsof);
/*     */       } 
/*     */       
/* 140 */       if (p_actionPerformed_1_.id == 222) {
/*     */         
/* 142 */         this.mc.gameSettings.saveOptions();
/* 143 */         GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 144 */         this.mc.displayGuiScreen((GuiScreen)guiothersettingsof);
/*     */       } 
/*     */       
/* 147 */       if (p_actionPerformed_1_.id == 231) {
/*     */         
/* 149 */         if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
/*     */           
/* 151 */           Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
/*     */           
/*     */           return;
/*     */         } 
/* 155 */         if (Config.isAnisotropicFiltering()) {
/*     */           
/* 157 */           Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
/*     */           
/*     */           return;
/*     */         } 
/* 161 */         if (Config.isFastRender()) {
/*     */           
/* 163 */           Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
/*     */           
/*     */           return;
/*     */         } 
/* 167 */         if ((Config.getGameSettings()).anaglyph) {
/*     */           
/* 169 */           Config.showGuiMessage(Lang.get("of.message.shaders.an1"), Lang.get("of.message.shaders.an2"));
/*     */           
/*     */           return;
/*     */         } 
/* 173 */         this.mc.gameSettings.saveOptions();
/* 174 */         GuiShaders guishaders = new GuiShaders((GuiScreen)this, this.guiGameSettings);
/* 175 */         this.mc.displayGuiScreen((GuiScreen)guishaders);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 185 */     drawDefaultBackground();
/* 186 */     drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
/*     */ 
/*     */     
/* 189 */     String s = "OptiFine HD M6_pre1 Ultra";
/*     */     
/* 191 */     drawString(this.fontRendererObj, s, 2, this.height - 10, 8421504);
/* 192 */     String s2 = "Minecraft 1.8.9 ";
/* 193 */     int i = this.fontRendererObj.getStringWidth(s2);
/* 194 */     drawString(this.fontRendererObj, s2, this.width - i - 2, this.height - 10, 8421504);
/* 195 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 196 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getButtonWidth(GuiButton p_getButtonWidth_0_) {
/* 201 */     return p_getButtonWidth_0_.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getButtonHeight(GuiButton p_getButtonHeight_0_) {
/* 206 */     return p_getButtonHeight_0_.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawGradientRect(GuiScreen p_drawGradientRect_0_, int p_drawGradientRect_1_, int p_drawGradientRect_2_, int p_drawGradientRect_3_, int p_drawGradientRect_4_, int p_drawGradientRect_5_, int p_drawGradientRect_6_) {
/* 211 */     p_drawGradientRect_0_.drawGradientRect(p_drawGradientRect_1_, p_drawGradientRect_2_, p_drawGradientRect_3_, p_drawGradientRect_4_, p_drawGradientRect_5_, p_drawGradientRect_6_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getGuiChatText(GuiChat p_getGuiChatText_0_) {
/* 216 */     return p_getGuiChatText_0_.inputField.getText();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiVideoSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */