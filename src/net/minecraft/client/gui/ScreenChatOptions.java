/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class ScreenChatOptions
/*    */   extends GuiScreen {
/*  8 */   private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[] { GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO };
/*    */   
/*    */   private final GuiScreen parentScreen;
/*    */   private final GameSettings game_settings;
/*    */   private String field_146401_i;
/*    */   
/*    */   public ScreenChatOptions(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/* 15 */     this.parentScreen = parentScreenIn;
/* 16 */     this.game_settings = gameSettingsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 25 */     int i = 0;
/* 26 */     this.field_146401_i = I18n.format("options.chat.title", new Object[0]);
/*    */     
/* 28 */     for (GameSettings.Options gamesettings$options : field_146399_a) {
/*    */       
/* 30 */       if (gamesettings$options.getEnumFloat()) {
/*    */         
/* 32 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options));
/*    */       }
/*    */       else {
/*    */         
/* 36 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options, this.game_settings.getKeyBinding(gamesettings$options)));
/*    */       } 
/*    */       
/* 39 */       i++;
/*    */     } 
/*    */     
/* 42 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 120, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 49 */     if (button.enabled) {
/*    */       
/* 51 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/*    */         
/* 53 */         this.game_settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/* 54 */         button.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*    */       } 
/*    */       
/* 57 */       if (button.id == 200) {
/*    */         
/* 59 */         this.mc.gameSettings.saveOptions();
/* 60 */         this.mc.displayGuiScreen(this.parentScreen);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 70 */     drawDefaultBackground();
/* 71 */     drawCenteredString(this.fontRendererObj, this.field_146401_i, this.width / 2, 20, 16777215);
/* 72 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\ScreenChatOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */