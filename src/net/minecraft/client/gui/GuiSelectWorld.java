/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatComparator;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiSelectWorld
/*     */   extends GuiScreen {
/*  24 */   private static final Logger logger = LogManager.getLogger();
/*  25 */   final DateFormat field_146633_h = new SimpleDateFormat();
/*     */   protected GuiScreen parentScreen;
/*  27 */   protected String screenTitle = "Select world";
/*     */   
/*     */   private boolean field_146634_i;
/*     */   
/*     */   int selectedIndex;
/*     */   java.util.List<SaveFormatComparator> field_146639_s;
/*     */   private List availableWorlds;
/*     */   String field_146637_u;
/*     */   String field_146636_v;
/*  36 */   String[] field_146635_w = new String[4];
/*     */   
/*     */   private boolean confirmingDelete;
/*     */   GuiButton deleteButton;
/*     */   GuiButton selectButton;
/*     */   GuiButton renameButton;
/*     */   GuiButton recreateButton;
/*     */   
/*     */   public GuiSelectWorld(GuiScreen parentScreenIn) {
/*  45 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  54 */     this.screenTitle = I18n.format("selectWorld.title", new Object[0]);
/*     */ 
/*     */     
/*     */     try {
/*  58 */       loadLevelList();
/*     */     }
/*  60 */     catch (AnvilConverterException anvilconverterexception) {
/*     */       
/*  62 */       logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*  63 */       this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", anvilconverterexception.getMessage()));
/*     */       
/*     */       return;
/*     */     } 
/*  67 */     this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
/*  68 */     this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
/*  69 */     this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
/*  70 */     this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
/*  71 */     this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
/*  72 */     this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
/*  73 */     this.availableWorlds = new List(this.mc);
/*  74 */     this.availableWorlds.registerScrollButtons(4, 5);
/*  75 */     addWorldSelectionButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  83 */     super.handleMouseInput();
/*  84 */     this.availableWorlds.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadLevelList() throws AnvilConverterException {
/*  92 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  93 */     this.field_146639_s = isaveformat.getSaveList();
/*  94 */     Collections.sort(this.field_146639_s);
/*  95 */     this.selectedIndex = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_146621_a(int p_146621_1_) {
/* 100 */     return ((SaveFormatComparator)this.field_146639_s.get(p_146621_1_)).getFileName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_146614_d(int p_146614_1_) {
/* 105 */     String s = ((SaveFormatComparator)this.field_146639_s.get(p_146614_1_)).getDisplayName();
/*     */     
/* 107 */     if (StringUtils.isEmpty(s))
/*     */     {
/* 109 */       s = I18n.format("selectWorld.world", new Object[0]) + " " + (p_146614_1_ + 1);
/*     */     }
/*     */     
/* 112 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWorldSelectionButtons() {
/* 117 */     this.buttonList.add(this.selectButton = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
/* 118 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/* 119 */     this.buttonList.add(this.renameButton = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
/* 120 */     this.buttonList.add(this.deleteButton = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
/* 121 */     this.buttonList.add(this.recreateButton = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
/* 122 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
/* 123 */     this.selectButton.enabled = false;
/* 124 */     this.deleteButton.enabled = false;
/* 125 */     this.renameButton.enabled = false;
/* 126 */     this.recreateButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 133 */     if (button.enabled)
/*     */     {
/* 135 */       if (button.id == 2) {
/*     */         
/* 137 */         String s = func_146614_d(this.selectedIndex);
/*     */         
/* 139 */         if (s != null)
/*     */         {
/* 141 */           this.confirmingDelete = true;
/* 142 */           GuiYesNo guiyesno = makeDeleteWorldYesNo(this, s, this.selectedIndex);
/* 143 */           this.mc.displayGuiScreen(guiyesno);
/*     */         }
/*     */       
/* 146 */       } else if (button.id == 1) {
/*     */         
/* 148 */         func_146615_e(this.selectedIndex);
/*     */       }
/* 150 */       else if (button.id == 3) {
/*     */         
/* 152 */         this.mc.displayGuiScreen(new GuiCreateWorld(this));
/*     */       }
/* 154 */       else if (button.id == 6) {
/*     */         
/* 156 */         this.mc.displayGuiScreen(new GuiRenameWorld(this, func_146621_a(this.selectedIndex)));
/*     */       }
/* 158 */       else if (button.id == 0) {
/*     */         
/* 160 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 162 */       else if (button.id == 7) {
/*     */         
/* 164 */         GuiCreateWorld guicreateworld = new GuiCreateWorld(this);
/* 165 */         ISaveHandler isavehandler = this.mc.getSaveLoader().getSaveLoader(func_146621_a(this.selectedIndex), false);
/* 166 */         WorldInfo worldinfo = isavehandler.loadWorldInfo();
/* 167 */         isavehandler.flush();
/* 168 */         guicreateworld.recreateFromExistingWorld(worldinfo);
/* 169 */         this.mc.displayGuiScreen(guicreateworld);
/*     */       }
/*     */       else {
/*     */         
/* 173 */         this.availableWorlds.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146615_e(int p_146615_1_) {
/* 180 */     this.mc.displayGuiScreen((GuiScreen)null);
/*     */     
/* 182 */     if (!this.field_146634_i) {
/*     */       
/* 184 */       this.field_146634_i = true;
/* 185 */       String s = func_146621_a(p_146615_1_);
/*     */       
/* 187 */       if (s == null)
/*     */       {
/* 189 */         s = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 192 */       String s1 = func_146614_d(p_146615_1_);
/*     */       
/* 194 */       if (s1 == null)
/*     */       {
/* 196 */         s1 = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 199 */       if (this.mc.getSaveLoader().canLoadWorld(s))
/*     */       {
/* 201 */         this.mc.launchIntegratedServer(s, s1, (WorldSettings)null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 208 */     if (this.confirmingDelete) {
/*     */       
/* 210 */       this.confirmingDelete = false;
/*     */       
/* 212 */       if (result) {
/*     */         
/* 214 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 215 */         isaveformat.flushCache();
/* 216 */         isaveformat.deleteWorldDirectory(func_146621_a(id));
/*     */ 
/*     */         
/*     */         try {
/* 220 */           loadLevelList();
/*     */         }
/* 222 */         catch (AnvilConverterException anvilconverterexception) {
/*     */           
/* 224 */           logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*     */         } 
/*     */       } 
/*     */       
/* 228 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 237 */     this.availableWorlds.drawScreen(mouseX, mouseY, partialTicks);
/* 238 */     drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 20, 16777215);
/* 239 */     super.drawScreen(mouseX, mouseY, partialTicks);
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
/*     */   
/*     */   public static GuiYesNo makeDeleteWorldYesNo(GuiYesNoCallback selectWorld, String name, int id) {
/* 253 */     String s = I18n.format("selectWorld.deleteQuestion", new Object[0]);
/* 254 */     String s1 = "'" + name + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
/* 255 */     String s2 = I18n.format("selectWorld.deleteButton", new Object[0]);
/* 256 */     String s3 = I18n.format("gui.cancel", new Object[0]);
/* 257 */     GuiYesNo guiyesno = new GuiYesNo(selectWorld, s, s1, s2, s3, id);
/* 258 */     return guiyesno;
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot
/*     */   {
/*     */     public List(Minecraft mcIn) {
/* 265 */       super(mcIn, GuiSelectWorld.this.width, GuiSelectWorld.this.height, 32, GuiSelectWorld.this.height - 64, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 270 */       return GuiSelectWorld.this.field_146639_s.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 275 */       GuiSelectWorld.this.selectedIndex = slotIndex;
/* 276 */       boolean flag = (GuiSelectWorld.this.selectedIndex >= 0 && GuiSelectWorld.this.selectedIndex < getSize());
/* 277 */       GuiSelectWorld.this.selectButton.enabled = flag;
/* 278 */       GuiSelectWorld.this.deleteButton.enabled = flag;
/* 279 */       GuiSelectWorld.this.renameButton.enabled = flag;
/* 280 */       GuiSelectWorld.this.recreateButton.enabled = flag;
/*     */       
/* 282 */       if (isDoubleClick && flag)
/*     */       {
/* 284 */         GuiSelectWorld.this.func_146615_e(slotIndex);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 290 */       return (slotIndex == GuiSelectWorld.this.selectedIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 295 */       return GuiSelectWorld.this.field_146639_s.size() * 36;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 300 */       GuiSelectWorld.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 305 */       SaveFormatComparator saveformatcomparator = GuiSelectWorld.this.field_146639_s.get(entryID);
/* 306 */       String s = saveformatcomparator.getDisplayName();
/*     */       
/* 308 */       if (StringUtils.isEmpty(s))
/*     */       {
/* 310 */         s = GuiSelectWorld.this.field_146637_u + " " + (entryID + 1);
/*     */       }
/*     */       
/* 313 */       String s1 = saveformatcomparator.getFileName();
/* 314 */       s1 = s1 + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(saveformatcomparator.getLastTimePlayed()));
/* 315 */       s1 = s1 + ")";
/* 316 */       String s2 = "";
/*     */       
/* 318 */       if (saveformatcomparator.requiresConversion()) {
/*     */         
/* 320 */         s2 = GuiSelectWorld.this.field_146636_v + " " + s2;
/*     */       }
/*     */       else {
/*     */         
/* 324 */         s2 = GuiSelectWorld.this.field_146635_w[saveformatcomparator.getEnumGameType().getID()];
/*     */         
/* 326 */         if (saveformatcomparator.isHardcoreModeEnabled())
/*     */         {
/* 328 */           s2 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
/*     */         }
/*     */         
/* 331 */         if (saveformatcomparator.getCheatsEnabled())
/*     */         {
/* 333 */           s2 = s2 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
/*     */         }
/*     */       } 
/* 336 */       FontLoader.PF16.drawString(s, (p_180791_2_ + 2), p_180791_3_ + 1 + 2, 16777215);
/* 337 */       FontLoader.PF16.drawString(s1, (p_180791_2_ + 2), p_180791_3_ + 12 + 2, 8421504);
/* 338 */       FontLoader.PF16.drawString(s2, (p_180791_2_ + 2), p_180791_3_ + 22 + 2, 8421504);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiSelectWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */