/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.optifine.gui.GuiButtonOF;
/*     */ import net.optifine.gui.GuiScreenCapeOF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCustomizeSkin
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private String title;
/*     */   
/*     */   public GuiCustomizeSkin(GuiScreen parentScreenIn) {
/*  18 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  27 */     int i = 0;
/*  28 */     this.title = I18n.format("options.skinCustomisation.title", new Object[0]);
/*     */     
/*  30 */     for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
/*     */       
/*  32 */       this.buttonList.add(new ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts));
/*  33 */       i++;
/*     */     } 
/*     */     
/*  36 */     if (i % 2 == 1)
/*     */     {
/*  38 */       i++;
/*     */     }
/*     */     
/*  41 */     this.buttonList.add(new GuiButtonOF(210, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("of.options.skinCustomisation.ofCape", new Object[0])));
/*  42 */     i += 2;
/*  43 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  50 */     if (button.enabled) {
/*     */       
/*  52 */       if (button.id == 210)
/*     */       {
/*  54 */         this.mc.displayGuiScreen((GuiScreen)new GuiScreenCapeOF(this));
/*     */       }
/*     */       
/*  57 */       if (button.id == 200) {
/*     */         
/*  59 */         this.mc.gameSettings.saveOptions();
/*  60 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*  62 */       else if (button instanceof ButtonPart) {
/*     */         
/*  64 */         EnumPlayerModelParts enumplayermodelparts = ((ButtonPart)button).playerModelParts;
/*  65 */         this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
/*  66 */         button.displayString = func_175358_a(enumplayermodelparts);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  76 */     drawDefaultBackground();
/*  77 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
/*  78 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   String func_175358_a(EnumPlayerModelParts playerModelParts) {
/*     */     String s;
/*  85 */     if (this.mc.gameSettings.getModelParts().contains(playerModelParts)) {
/*     */       
/*  87 */       s = I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/*  91 */       s = I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/*  94 */     return playerModelParts.func_179326_d().getFormattedText() + ": " + s;
/*     */   }
/*     */   
/*     */   class ButtonPart
/*     */     extends GuiButton
/*     */   {
/*     */     final EnumPlayerModelParts playerModelParts;
/*     */     
/*     */     ButtonPart(int p_i45514_2_, int p_i45514_3_, int p_i45514_4_, int p_i45514_5_, int p_i45514_6_, EnumPlayerModelParts playerModelParts) {
/* 103 */       super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(playerModelParts));
/* 104 */       this.playerModelParts = playerModelParts;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiCustomizeSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */