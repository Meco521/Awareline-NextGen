/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.me.guichaguri.betterfps.gui.GuiBetterFpsConfig;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ 
/*     */ public class GuiOptions
/*     */   extends GuiScreen {
/*  15 */   private static final GameSettings.Options[] field_146440_f = new GameSettings.Options[] { GameSettings.Options.FOV };
/*     */ 
/*     */   
/*     */   private final GuiScreen field_146441_g;
/*     */   
/*     */   private final GameSettings game_settings_1;
/*     */   
/*  22 */   protected String field_146442_a = "Options";
/*     */   private GuiButton field_175357_i;
/*     */   private GuiLockIconButton field_175356_r;
/*     */   
/*     */   public GuiOptions(GuiScreen p_i1046_1_, GameSettings p_i1046_2_) {
/*  27 */     this.field_146441_g = p_i1046_1_;
/*  28 */     this.game_settings_1 = p_i1046_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     int i = 0;
/*  37 */     this.field_146442_a = I18n.format("options.title", new Object[0]);
/*     */     
/*  39 */     for (GameSettings.Options gamesettings$options : field_146440_f) {
/*  40 */       if (gamesettings$options.getEnumFloat()) {
/*  41 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options));
/*     */       } else {
/*  43 */         GuiOptionButton guioptionbutton = new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options, this.game_settings_1.getKeyBinding(gamesettings$options));
/*  44 */         this.buttonList.add(guioptionbutton);
/*     */       } 
/*     */       
/*  47 */       i++;
/*     */     } 
/*     */     
/*  50 */     if (this.mc.theWorld != null) {
/*  51 */       EnumDifficulty enumdifficulty = this.mc.theWorld.getDifficulty();
/*  52 */       this.field_175357_i = new GuiButton(108, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), 170, 20, func_175355_a(enumdifficulty));
/*  53 */       this.buttonList.add(this.field_175357_i);
/*     */       
/*  55 */       if (this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*  56 */         this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - 20);
/*  57 */         this.field_175356_r = new GuiLockIconButton(109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
/*  58 */         this.buttonList.add(this.field_175356_r);
/*  59 */         this.field_175356_r.func_175229_b(this.mc.theWorld.getWorldInfo().isDifficultyLocked());
/*  60 */         this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
/*  61 */         this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
/*     */       } else {
/*  63 */         this.field_175357_i.enabled = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.buttonList.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
/*  75 */     this.buttonList.add(new GuiButton(8675309, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "Activate Next Shader"));
/*  76 */     this.buttonList.add(new GuiButton(106, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
/*     */     
/*  78 */     this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
/*  79 */     this.buttonList.add(new GuiButton(100, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
/*  80 */     this.buttonList.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
/*  81 */     this.buttonList.add(new GuiButton(103, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.chat.title", new Object[0])));
/*  82 */     this.buttonList.add(new GuiButton(105, this.width / 2 - 155, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
/*  83 */     this.buttonList.add(new GuiButton(104, this.width / 2 + 5, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
/*  84 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*  85 */     this.buttonList.add(new GuiButton(114517, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, "BetterFPS Setting"));
/*     */   }
/*     */   
/*     */   public String func_175355_a(EnumDifficulty p_175355_1_) {
/*  89 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*  90 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation("options.difficulty", new Object[0]));
/*  91 */     chatComponentText.appendText(": ");
/*  92 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
/*  93 */     return chatComponentText.getFormattedText();
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  97 */     this.mc.displayGuiScreen(this);
/*     */     
/*  99 */     if (id == 109 && result && this.mc.theWorld != null) {
/* 100 */       this.mc.theWorld.getWorldInfo().setDifficultyLocked(true);
/* 101 */       this.field_175356_r.func_175229_b(true);
/* 102 */       this.field_175356_r.enabled = false;
/* 103 */       this.field_175357_i.enabled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 111 */     if (button.enabled) {
/* 112 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/* 113 */         GameSettings.Options gamesettings$options = ((GuiOptionButton)button).returnEnumOptions();
/* 114 */         this.game_settings_1.setOptionValue(gamesettings$options, 1);
/* 115 */         button.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/* 118 */       if (button.id == 108) {
/* 119 */         this.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.theWorld.getDifficulty().getDifficultyId() + 1));
/* 120 */         this.field_175357_i.displayString = func_175355_a(this.mc.theWorld.getDifficulty());
/*     */       } 
/*     */       
/* 123 */       if (button.id == 109) {
/* 124 */         this.mc.displayGuiScreen(new GuiYesNo(this, (new ChatComponentTranslation("difficulty.lock.title", new Object[0])).getFormattedText(), (new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(this.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) })).getFormattedText(), 109));
/*     */       }
/*     */       
/* 127 */       if (button.id == 110) {
/* 128 */         this.mc.gameSettings.saveOptions();
/* 129 */         this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
/*     */       } 
/*     */       
/* 132 */       if (button.id == 8675309) {
/* 133 */         this.mc.entityRenderer.activateNextShader();
/*     */       }
/*     */       
/* 136 */       if (button.id == 101) {
/* 137 */         this.mc.gameSettings.saveOptions();
/* 138 */         this.mc.displayGuiScreen((GuiScreen)new GuiVideoSettings(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 141 */       if (button.id == 100) {
/* 142 */         this.mc.gameSettings.saveOptions();
/* 143 */         this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 146 */       if (button.id == 102) {
/* 147 */         this.mc.gameSettings.saveOptions();
/* 148 */         this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.getLanguageManager()));
/*     */       } 
/*     */       
/* 151 */       if (button.id == 103) {
/* 152 */         this.mc.gameSettings.saveOptions();
/* 153 */         this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 156 */       if (button.id == 104) {
/* 157 */         this.mc.gameSettings.saveOptions();
/* 158 */         this.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 161 */       if (button.id == 200) {
/* 162 */         this.mc.gameSettings.saveOptions();
/* 163 */         this.mc.displayGuiScreen(this.field_146441_g);
/*     */       } 
/* 165 */       if (button.id == 114517) {
/* 166 */         this.mc.gameSettings.saveOptions();
/* 167 */         this.mc.displayGuiScreen((GuiScreen)new GuiBetterFpsConfig());
/*     */       } 
/* 169 */       if (button.id == 105) {
/* 170 */         this.mc.gameSettings.saveOptions();
/* 171 */         this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
/*     */       } 
/*     */       
/* 174 */       if (button.id == 106) {
/* 175 */         this.mc.gameSettings.saveOptions();
/* 176 */         this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 179 */       if (button.id == 107) {
/* 180 */         this.mc.gameSettings.saveOptions();
/* 181 */         JOptionPane.showMessageDialog(null, "This button option is disabled.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 190 */     drawDefaultBackground();
/* 191 */     drawCenteredString(this.fontRendererObj, this.field_146442_a, this.width / 2, 15, 16777215);
/* 192 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */