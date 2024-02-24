/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import awareline.main.ui.gui.altmanager.oldaltmanager.AltLoginThread;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class GuiDisconnected
/*     */   extends GuiScreen {
/*     */   private final String reason;
/*     */   private final IChatComponent message;
/*     */   private final GuiScreen parentScreen;
/*     */   private List<String> multilineMessage;
/*     */   private int field_175353_i;
/*     */   private boolean isTrueRec;
/*     */   
/*     */   public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp) {
/*  20 */     this.parentScreen = screen;
/*  21 */     this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
/*  22 */     this.message = chatComp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  37 */     this.buttonList.clear();
/*  38 */     this.buttonList.add(new GuiButton(997, 5, 8, 98, 20, "AltManager", true));
/*  39 */     this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
/*  40 */     this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
/*  41 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, 
/*  42 */           I18n.format("gui.toMenu", new Object[0])));
/*  43 */     this.buttonList.add(new GuiButton(7723, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 22, "Reconnect"));
/*     */     
/*  45 */     this.buttonList.add(new GuiButton(7725, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 44, "Random Name and Reconnect"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  53 */     if (button.id == 0) {
/*  54 */       this.mc.displayGuiScreen(this.parentScreen);
/*  55 */     } else if (button.id != 997) {
/*     */       
/*  57 */       if (button.id == 7723) {
/*     */         try {
/*  59 */           GuiMultiplayer gui = (GuiMultiplayer)this.parentScreen;
/*  60 */           gui.connectToSelected();
/*  61 */         } catch (Exception e) {
/*  62 */           e.printStackTrace();
/*     */         } 
/*  64 */       } else if (button.id == 7725) {
/*     */         try {
/*  66 */           String abcdefged = SimpleRender.abcdefg();
/*  67 */           AltLoginThread thread = new AltLoginThread(abcdefged, "");
/*  68 */           thread.start();
/*  69 */           GuiMultiplayer gui = (GuiMultiplayer)this.parentScreen;
/*  70 */           gui.connectToSelected();
/*  71 */         } catch (Exception e) {
/*  72 */           e.printStackTrace();
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
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  84 */     drawDefaultBackground();
/*  85 */     FontLoader.PF16.drawCenteredStringWithShadow(this.reason, this.width / 2.0F, this.height / 2.0F - this.field_175353_i / 2.0F - (this.fontRendererObj.FONT_HEIGHT * 2), 11184810);
/*     */     
/*  87 */     int i = this.height / 2 - this.field_175353_i / 2;
/*     */     
/*  89 */     if (this.multilineMessage != null) {
/*  90 */       for (String s : this.multilineMessage) {
/*     */         
/*  92 */         FontLoader.PF16.drawCenteredStringWithShadow(s, this.width / 2.0F, i, 16777215);
/*  93 */         i += this.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */     }
/*  96 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen2(int mouseX, int mouseY, float partialTicks) {
/* 101 */     drawDefaultBackground();
/* 102 */     drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - (this.fontRendererObj.FONT_HEIGHT << 1), 11184810);
/* 103 */     int i = this.height / 2 - this.field_175353_i / 2;
/*     */     
/* 105 */     if (this.multilineMessage != null) {
/* 106 */       for (String s : this.multilineMessage) {
/* 107 */         drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
/* 108 */         i += this.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */     }
/*     */     
/* 112 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiDisconnected.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */