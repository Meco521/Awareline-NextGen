/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.Language;
/*     */ import net.minecraft.client.resources.LanguageManager;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiLanguage
/*     */   extends GuiScreen
/*     */ {
/*     */   protected GuiScreen parentScreen;
/*     */   private List list;
/*     */   final GameSettings game_settings_3;
/*     */   final LanguageManager languageManager;
/*     */   GuiOptionButton forceUnicodeFontBtn;
/*     */   GuiOptionButton confirmSettingsBtn;
/*     */   
/*     */   public GuiLanguage(GuiScreen screen, GameSettings gameSettingsObj, LanguageManager manager) {
/*  39 */     this.parentScreen = screen;
/*  40 */     this.game_settings_3 = gameSettingsObj;
/*  41 */     this.languageManager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  50 */     this.buttonList.add(this.forceUnicodeFontBtn = new GuiOptionButton(100, this.width / 2 - 155, this.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
/*  51 */     this.buttonList.add(this.confirmSettingsBtn = new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done", new Object[0])));
/*  52 */     this.list = new List(this.mc);
/*  53 */     this.list.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  61 */     super.handleMouseInput();
/*  62 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  69 */     if (button.enabled) {
/*     */       
/*  71 */       switch (button.id) {
/*     */         case 5:
/*     */           return;
/*     */ 
/*     */         
/*     */         case 6:
/*  77 */           this.mc.displayGuiScreen(this.parentScreen);
/*     */ 
/*     */         
/*     */         case 100:
/*  81 */           if (button instanceof GuiOptionButton) {
/*     */             
/*  83 */             this.game_settings_3.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  84 */             button.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/*  85 */             ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  86 */             int i = scaledresolution.getScaledWidth();
/*  87 */             int j = scaledresolution.getScaledHeight();
/*  88 */             setWorldAndResolution(this.mc, i, j);
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  94 */       this.list.actionPerformed(button);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 104 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 105 */     drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), this.width / 2, 16, 16777215);
/* 106 */     drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", this.width / 2, this.height - 56, 8421504);
/* 107 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot {
/* 112 */     private final java.util.List<String> langCodeList = Lists.newArrayList();
/* 113 */     private final Map<String, Language> languageMap = Maps.newHashMap();
/*     */ 
/*     */     
/*     */     public List(Minecraft mcIn) {
/* 117 */       super(mcIn, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);
/*     */       
/* 119 */       for (Language language : GuiLanguage.this.languageManager.getLanguages()) {
/*     */         
/* 121 */         this.languageMap.put(language.getLanguageCode(), language);
/* 122 */         this.langCodeList.add(language.getLanguageCode());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 128 */       return this.langCodeList.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 133 */       Language language = this.languageMap.get(this.langCodeList.get(slotIndex));
/* 134 */       GuiLanguage.this.languageManager.setCurrentLanguage(language);
/* 135 */       GuiLanguage.this.game_settings_3.language = language.getLanguageCode();
/* 136 */       this.mc.refreshResources();
/* 137 */       GuiLanguage.this.fontRendererObj.setUnicodeFlag((GuiLanguage.this.languageManager.isCurrentLocaleUnicode() || GuiLanguage.this.game_settings_3.forceUnicodeFont));
/* 138 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
/* 139 */       GuiLanguage.this.confirmSettingsBtn.displayString = I18n.format("gui.done", new Object[0]);
/* 140 */       GuiLanguage.this.forceUnicodeFontBtn.displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/* 141 */       GuiLanguage.this.game_settings_3.saveOptions();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 146 */       return ((String)this.langCodeList.get(slotIndex)).equals(GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 151 */       return getSize() * 18;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 156 */       GuiLanguage.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 161 */       GuiLanguage.this.fontRendererObj.setBidiFlag(true);
/*     */       
/* 163 */       FontLoader.PF16.drawCenteredString(((Language)this.languageMap.get(this.langCodeList.get(entryID))).toString(), this.width / 2.0F, (p_180791_3_ + 3), 16777215);
/*     */       
/* 165 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiLanguage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */