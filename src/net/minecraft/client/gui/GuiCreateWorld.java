/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCreateWorld
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private GuiTextField worldNameField;
/*     */   private GuiTextField worldSeedField;
/*     */   private String saveDirName;
/*  21 */   private String gameMode = "survival";
/*     */   
/*     */   private String savedGameMode;
/*     */   
/*     */   private boolean generateStructuresEnabled = true;
/*     */   
/*     */   private boolean allowCheats;
/*     */   
/*     */   private boolean allowCheatsWasSetByUser;
/*     */   
/*     */   private boolean bonusChestEnabled;
/*     */   
/*     */   private boolean hardCoreMode;
/*     */   
/*     */   private boolean alreadyGenerated;
/*     */   
/*     */   private boolean inMoreWorldOptionsDisplay;
/*     */   
/*     */   private GuiButton btnGameMode;
/*     */   
/*     */   private GuiButton btnMoreOptions;
/*     */   
/*     */   private GuiButton btnMapFeatures;
/*     */   
/*     */   private GuiButton btnBonusItems;
/*     */   
/*     */   private GuiButton btnMapType;
/*     */   private GuiButton btnAllowCommands;
/*     */   private GuiButton btnCustomizeType;
/*     */   private String gameModeDesc1;
/*     */   private String gameModeDesc2;
/*     */   private String worldSeed;
/*     */   private String worldName;
/*     */   private int selectedIndex;
/*  55 */   public String chunkProviderSettingsJson = "";
/*     */ 
/*     */   
/*  58 */   private static final String[] disallowedFilenames = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
/*     */ 
/*     */   
/*     */   public GuiCreateWorld(GuiScreen p_i46320_1_) {
/*  62 */     this.parentScreen = p_i46320_1_;
/*  63 */     this.worldSeed = "";
/*  64 */     this.worldName = I18n.format("selectWorld.newWorld", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  72 */     this.worldNameField.updateCursorCounter();
/*  73 */     this.worldSeedField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  82 */     Keyboard.enableRepeatEvents(true);
/*  83 */     this.buttonList.clear();
/*  84 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  85 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  86 */     this.buttonList.add(this.btnGameMode = new GuiButton(2, this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  87 */     this.buttonList.add(this.btnMoreOptions = new GuiButton(3, this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
/*  88 */     this.buttonList.add(this.btnMapFeatures = new GuiButton(4, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
/*  89 */     this.btnMapFeatures.visible = false;
/*  90 */     this.buttonList.add(this.btnBonusItems = new GuiButton(7, this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
/*  91 */     this.btnBonusItems.visible = false;
/*  92 */     this.buttonList.add(this.btnMapType = new GuiButton(5, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
/*  93 */     this.btnMapType.visible = false;
/*  94 */     this.buttonList.add(this.btnAllowCommands = new GuiButton(6, this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  95 */     this.btnAllowCommands.visible = false;
/*  96 */     this.buttonList.add(this.btnCustomizeType = new GuiButton(8, this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
/*  97 */     this.btnCustomizeType.visible = false;
/*  98 */     this.worldNameField = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  99 */     this.worldNameField.setFocused(true);
/* 100 */     this.worldNameField.setText(this.worldName);
/* 101 */     this.worldSeedField = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/* 102 */     this.worldSeedField.setText(this.worldSeed);
/* 103 */     showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
/* 104 */     calcSaveDirName();
/* 105 */     updateDisplayState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calcSaveDirName() {
/* 113 */     this.saveDirName = this.worldNameField.getText().trim();
/*     */     
/* 115 */     for (char c0 : ChatAllowedCharacters.allowedCharactersArray)
/*     */     {
/* 117 */       this.saveDirName = this.saveDirName.replace(c0, '_');
/*     */     }
/*     */     
/* 120 */     if (StringUtils.isEmpty(this.saveDirName))
/*     */     {
/* 122 */       this.saveDirName = "World";
/*     */     }
/*     */     
/* 125 */     this.saveDirName = getUncollidingSaveDirName(this.mc.getSaveLoader(), this.saveDirName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateDisplayState() {
/* 133 */     this.btnGameMode.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
/* 134 */     this.gameModeDesc1 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
/* 135 */     this.gameModeDesc2 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
/* 136 */     this.btnMapFeatures.displayString = I18n.format("selectWorld.mapFeatures", new Object[0]) + " ";
/*     */     
/* 138 */     if (this.generateStructuresEnabled) {
/*     */       
/* 140 */       this.btnMapFeatures.displayString += I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 144 */       this.btnMapFeatures.displayString += I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 147 */     this.btnBonusItems.displayString = I18n.format("selectWorld.bonusItems", new Object[0]) + " ";
/*     */     
/* 149 */     if (this.bonusChestEnabled && !this.hardCoreMode) {
/*     */       
/* 151 */       this.btnBonusItems.displayString += I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 155 */       this.btnBonusItems.displayString += I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 158 */     this.btnMapType.displayString = I18n.format("selectWorld.mapType", new Object[0]) + " " + I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslateName(), new Object[0]);
/* 159 */     this.btnAllowCommands.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";
/*     */     
/* 161 */     if (this.allowCheats && !this.hardCoreMode) {
/*     */       
/* 163 */       this.btnAllowCommands.displayString += I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 167 */       this.btnAllowCommands.displayString += I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
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
/*     */   public static String getUncollidingSaveDirName(ISaveFormat saveLoader, String name) {
/* 181 */     StringBuilder nameBuilder = new StringBuilder(name.replaceAll("[\\./\"]", "_"));
/* 182 */     for (String s : disallowedFilenames) {
/*     */       
/* 184 */       if (nameBuilder.toString().equalsIgnoreCase(s))
/*     */       {
/* 186 */         nameBuilder = new StringBuilder("_" + nameBuilder + "_");
/*     */       }
/*     */     } 
/* 189 */     name = nameBuilder.toString();
/*     */     
/* 191 */     while (saveLoader.getWorldInfo(name) != null)
/*     */     {
/* 193 */       name = name + "-";
/*     */     }
/*     */     
/* 196 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 204 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 211 */     if (button.enabled)
/*     */     {
/* 213 */       if (button.id == 1) {
/*     */         
/* 215 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 217 */       else if (button.id == 0) {
/*     */         
/* 219 */         this.mc.displayGuiScreen((GuiScreen)null);
/*     */         
/* 221 */         if (this.alreadyGenerated) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 226 */         this.alreadyGenerated = true;
/* 227 */         long i = (new Random()).nextLong();
/* 228 */         String s = this.worldSeedField.getText();
/*     */         
/* 230 */         if (!StringUtils.isEmpty(s)) {
/*     */           
/*     */           try {
/*     */             
/* 234 */             long j = Long.parseLong(s);
/*     */             
/* 236 */             if (j != 0L)
/*     */             {
/* 238 */               i = j;
/*     */             }
/*     */           }
/* 241 */           catch (NumberFormatException var7) {
/*     */             
/* 243 */             i = s.hashCode();
/*     */           } 
/*     */         }
/*     */         
/* 247 */         WorldSettings.GameType worldsettings$gametype = WorldSettings.GameType.getByName(this.gameMode);
/* 248 */         WorldSettings worldsettings = new WorldSettings(i, worldsettings$gametype, this.generateStructuresEnabled, this.hardCoreMode, WorldType.worldTypes[this.selectedIndex]);
/* 249 */         worldsettings.setWorldName(this.chunkProviderSettingsJson);
/*     */         
/* 251 */         if (this.bonusChestEnabled && !this.hardCoreMode)
/*     */         {
/* 253 */           worldsettings.enableBonusChest();
/*     */         }
/*     */         
/* 256 */         if (this.allowCheats && !this.hardCoreMode)
/*     */         {
/* 258 */           worldsettings.enableCommands();
/*     */         }
/*     */         
/* 261 */         this.mc.launchIntegratedServer(this.saveDirName, this.worldNameField.getText().trim(), worldsettings);
/*     */       }
/* 263 */       else if (button.id == 3) {
/*     */         
/* 265 */         toggleMoreWorldOptions();
/*     */       }
/* 267 */       else if (button.id == 2) {
/*     */         
/* 269 */         if (this.gameMode.equals("survival")) {
/*     */           
/* 271 */           if (!this.allowCheatsWasSetByUser)
/*     */           {
/* 273 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 276 */           this.hardCoreMode = false;
/* 277 */           this.gameMode = "hardcore";
/* 278 */           this.hardCoreMode = true;
/* 279 */           this.btnAllowCommands.enabled = false;
/* 280 */           this.btnBonusItems.enabled = false;
/* 281 */           updateDisplayState();
/*     */         }
/* 283 */         else if (this.gameMode.equals("hardcore")) {
/*     */           
/* 285 */           if (!this.allowCheatsWasSetByUser)
/*     */           {
/* 287 */             this.allowCheats = true;
/*     */           }
/*     */           
/* 290 */           this.hardCoreMode = false;
/* 291 */           this.gameMode = "creative";
/* 292 */           updateDisplayState();
/* 293 */           this.hardCoreMode = false;
/* 294 */           this.btnAllowCommands.enabled = true;
/* 295 */           this.btnBonusItems.enabled = true;
/*     */         }
/*     */         else {
/*     */           
/* 299 */           if (!this.allowCheatsWasSetByUser)
/*     */           {
/* 301 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 304 */           this.gameMode = "survival";
/* 305 */           updateDisplayState();
/* 306 */           this.btnAllowCommands.enabled = true;
/* 307 */           this.btnBonusItems.enabled = true;
/* 308 */           this.hardCoreMode = false;
/*     */         } 
/*     */         
/* 311 */         updateDisplayState();
/*     */       }
/* 313 */       else if (button.id == 4) {
/*     */         
/* 315 */         this.generateStructuresEnabled = !this.generateStructuresEnabled;
/* 316 */         updateDisplayState();
/*     */       }
/* 318 */       else if (button.id == 7) {
/*     */         
/* 320 */         this.bonusChestEnabled = !this.bonusChestEnabled;
/* 321 */         updateDisplayState();
/*     */       }
/* 323 */       else if (button.id == 5) {
/*     */         
/* 325 */         this.selectedIndex++;
/*     */         
/* 327 */         if (this.selectedIndex >= WorldType.worldTypes.length)
/*     */         {
/* 329 */           this.selectedIndex = 0;
/*     */         }
/*     */         
/* 332 */         while (!canSelectCurWorldType()) {
/*     */           
/* 334 */           this.selectedIndex++;
/*     */           
/* 336 */           if (this.selectedIndex >= WorldType.worldTypes.length)
/*     */           {
/* 338 */             this.selectedIndex = 0;
/*     */           }
/*     */         } 
/*     */         
/* 342 */         this.chunkProviderSettingsJson = "";
/* 343 */         updateDisplayState();
/* 344 */         showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
/*     */       }
/* 346 */       else if (button.id == 6) {
/*     */         
/* 348 */         this.allowCheatsWasSetByUser = true;
/* 349 */         this.allowCheats = !this.allowCheats;
/* 350 */         updateDisplayState();
/*     */       }
/* 352 */       else if (button.id == 8) {
/*     */         
/* 354 */         if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
/*     */           
/* 356 */           this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
/*     */         }
/*     */         else {
/*     */           
/* 360 */           this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSelectCurWorldType() {
/* 372 */     WorldType worldtype = WorldType.worldTypes[this.selectedIndex];
/* 373 */     return (worldtype != null && worldtype.getCanBeCreated()) ? ((worldtype == WorldType.DEBUG_WORLD) ? isShiftKeyDown() : true) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void toggleMoreWorldOptions() {
/* 383 */     showMoreWorldOptions(!this.inMoreWorldOptionsDisplay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showMoreWorldOptions(boolean toggle) {
/* 391 */     this.inMoreWorldOptionsDisplay = toggle;
/*     */     
/* 393 */     if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
/*     */       
/* 395 */       this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
/* 396 */       this.btnGameMode.enabled = false;
/*     */       
/* 398 */       if (this.savedGameMode == null)
/*     */       {
/* 400 */         this.savedGameMode = this.gameMode;
/*     */       }
/*     */       
/* 403 */       this.gameMode = "spectator";
/* 404 */       this.btnMapFeatures.visible = false;
/* 405 */       this.btnBonusItems.visible = false;
/* 406 */       this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
/* 407 */       this.btnAllowCommands.visible = false;
/* 408 */       this.btnCustomizeType.visible = false;
/*     */     }
/*     */     else {
/*     */       
/* 412 */       this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
/* 413 */       this.btnGameMode.enabled = true;
/*     */       
/* 415 */       if (this.savedGameMode != null) {
/*     */         
/* 417 */         this.gameMode = this.savedGameMode;
/* 418 */         this.savedGameMode = null;
/*     */       } 
/*     */       
/* 421 */       this.btnMapFeatures.visible = (this.inMoreWorldOptionsDisplay && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED);
/* 422 */       this.btnBonusItems.visible = this.inMoreWorldOptionsDisplay;
/* 423 */       this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
/* 424 */       this.btnAllowCommands.visible = this.inMoreWorldOptionsDisplay;
/* 425 */       this.btnCustomizeType.visible = (this.inMoreWorldOptionsDisplay && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED));
/*     */     } 
/*     */     
/* 428 */     updateDisplayState();
/*     */     
/* 430 */     if (this.inMoreWorldOptionsDisplay) {
/*     */       
/* 432 */       this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 436 */       this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 446 */     if (this.worldNameField.isFocused() && !this.inMoreWorldOptionsDisplay) {
/*     */       
/* 448 */       this.worldNameField.textboxKeyTyped(typedChar, keyCode);
/* 449 */       this.worldName = this.worldNameField.getText();
/*     */     }
/* 451 */     else if (this.worldSeedField.isFocused() && this.inMoreWorldOptionsDisplay) {
/*     */       
/* 453 */       this.worldSeedField.textboxKeyTyped(typedChar, keyCode);
/* 454 */       this.worldSeed = this.worldSeedField.getText();
/*     */     } 
/*     */     
/* 457 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 459 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 462 */     ((GuiButton)this.buttonList.get(0)).enabled = !this.worldNameField.getText().isEmpty();
/* 463 */     calcSaveDirName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 471 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 473 */     if (this.inMoreWorldOptionsDisplay) {
/*     */       
/* 475 */       this.worldSeedField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     else {
/*     */       
/* 479 */       this.worldNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 488 */     drawDefaultBackground();
/* 489 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), this.width / 2, 20, -1);
/*     */     
/* 491 */     if (this.inMoreWorldOptionsDisplay) {
/*     */       
/* 493 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 494 */       drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), this.width / 2 - 100, 85, -6250336);
/*     */       
/* 496 */       if (this.btnMapFeatures.visible)
/*     */       {
/* 498 */         drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), this.width / 2 - 150, 122, -6250336);
/*     */       }
/*     */       
/* 501 */       if (this.btnAllowCommands.visible)
/*     */       {
/* 503 */         drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), this.width / 2 - 150, 172, -6250336);
/*     */       }
/*     */       
/* 506 */       this.worldSeedField.drawTextBox();
/*     */       
/* 508 */       if (WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice())
/*     */       {
/* 510 */         this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslatedInfo(), new Object[0]), this.btnMapType.xPosition + 2, this.btnMapType.yPosition + 22, this.btnMapType.getButtonWidth(), 10526880);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 515 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 516 */       drawString(this.fontRendererObj, I18n.format("selectWorld.resultFolder", new Object[0]) + " " + this.saveDirName, this.width / 2 - 100, 85, -6250336);
/* 517 */       this.worldNameField.drawTextBox();
/* 518 */       drawString(this.fontRendererObj, this.gameModeDesc1, this.width / 2 - 100, 137, -6250336);
/* 519 */       drawString(this.fontRendererObj, this.gameModeDesc2, this.width / 2 - 100, 149, -6250336);
/*     */     } 
/*     */     
/* 522 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateFromExistingWorld(WorldInfo original) {
/* 534 */     this.worldName = I18n.format("selectWorld.newWorld.copyOf", new Object[] { original.getWorldName() });
/* 535 */     this.worldSeed = String.valueOf(original.getSeed());
/* 536 */     this.selectedIndex = original.getTerrainType().getWorldTypeID();
/* 537 */     this.chunkProviderSettingsJson = original.getGeneratorOptions();
/* 538 */     this.generateStructuresEnabled = original.isMapFeaturesEnabled();
/* 539 */     this.allowCheats = original.areCommandsAllowed();
/*     */     
/* 541 */     if (original.isHardcoreModeEnabled()) {
/*     */       
/* 543 */       this.gameMode = "hardcore";
/*     */     }
/* 545 */     else if (original.getGameType().isSurvivalOrAdventure()) {
/*     */       
/* 547 */       this.gameMode = "survival";
/*     */     }
/* 549 */     else if (original.getGameType().isCreative()) {
/*     */       
/* 551 */       this.gameMode = "creative";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiCreateWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */