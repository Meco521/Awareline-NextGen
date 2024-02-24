/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.font.fontmanager.font.FontManager;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiSnooper
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen field_146608_a;
/*     */   private final GameSettings game_settings_2;
/*  18 */   final java.util.List<String> field_146604_g = Lists.newArrayList();
/*  19 */   final java.util.List<String> field_146609_h = Lists.newArrayList();
/*     */   
/*     */   private String field_146610_i;
/*     */   private String[] field_146607_r;
/*     */   private List field_146606_s;
/*     */   private GuiButton field_146605_t;
/*     */   
/*     */   public GuiSnooper(GuiScreen p_i1061_1_, GameSettings p_i1061_2_) {
/*  27 */     this.field_146608_a = p_i1061_1_;
/*  28 */     this.game_settings_2 = p_i1061_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  37 */     this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
/*  38 */     String s = I18n.format("options.snooper.desc", new Object[0]);
/*  39 */     java.util.List<String> list = Lists.newArrayList();
/*     */     
/*  41 */     list.addAll(this.fontRendererObj.listFormattedStringToWidth(s, this.width - 30));
/*     */     
/*  43 */     this.field_146607_r = list.<String>toArray(new String[0]);
/*  44 */     this.field_146604_g.clear();
/*  45 */     this.field_146609_h.clear();
/*  46 */     this.buttonList.add(this.field_146605_t = new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
/*  47 */     this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
/*  48 */     boolean flag = (this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null);
/*     */     
/*  50 */     for (Map.Entry<String, String> entry : (new TreeMap<>(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
/*     */       
/*  52 */       this.field_146604_g.add((flag ? "C " : "") + (String)entry.getKey());
/*  53 */       this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry.getValue(), this.width - 220));
/*     */     } 
/*     */     
/*  56 */     if (flag)
/*     */     {
/*  58 */       for (Map.Entry<String, String> entry1 : (new TreeMap<>(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
/*     */         
/*  60 */         this.field_146604_g.add("S " + (String)entry1.getKey());
/*  61 */         this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry1.getValue(), this.width - 220));
/*     */       } 
/*     */     }
/*     */     
/*  65 */     this.field_146606_s = new List();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  73 */     super.handleMouseInput();
/*  74 */     this.field_146606_s.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  81 */     if (button.enabled) {
/*     */       
/*  83 */       if (button.id == 2) {
/*     */         
/*  85 */         this.game_settings_2.saveOptions();
/*  86 */         this.game_settings_2.saveOptions();
/*  87 */         this.mc.displayGuiScreen(this.field_146608_a);
/*     */       } 
/*     */       
/*  90 */       if (button.id == 1) {
/*     */         
/*  92 */         this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
/*  93 */         this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 103 */     drawDefaultBackground();
/* 104 */     this.field_146606_s.drawScreen(mouseX, mouseY, partialTicks);
/* 105 */     drawCenteredString(this.fontRendererObj, this.field_146610_i, this.width / 2, 8, 16777215);
/* 106 */     int i = 22;
/*     */     
/* 108 */     for (String s : this.field_146607_r) {
/*     */       
/* 110 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 8421504);
/* 111 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */     
/* 114 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot
/*     */   {
/*     */     public List() {
/* 121 */       super(GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 126 */       return GuiSnooper.this.field_146604_g.size();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 135 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 144 */       FontManager.system14.drawString(GuiSnooper.this.field_146604_g.get(entryID), 10.0D, p_180791_3_, 16777215);
/* 145 */       FontManager.system14.drawString(GuiSnooper.this.field_146609_h.get(entryID), 230.0D, p_180791_3_, 16777215);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 152 */       return this.width - 10;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiSnooper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */