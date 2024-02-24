/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ public class GuiRenameWorld
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private GuiTextField field_146583_f;
/*     */   private final String saveName;
/*     */   
/*     */   public GuiRenameWorld(GuiScreen parentScreenIn, String saveNameIn) {
/*  18 */     this.parentScreen = parentScreenIn;
/*  19 */     this.saveName = saveNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  27 */     this.field_146583_f.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     Keyboard.enableRepeatEvents(true);
/*  37 */     this.buttonList.clear();
/*  38 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
/*  39 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  40 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  41 */     WorldInfo worldinfo = isaveformat.getWorldInfo(this.saveName);
/*  42 */     String s = worldinfo.getWorldName();
/*  43 */     this.field_146583_f = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  44 */     this.field_146583_f.setFocused(true);
/*  45 */     this.field_146583_f.setText(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  53 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  60 */     if (button.enabled)
/*     */     {
/*  62 */       if (button.id == 1) {
/*     */         
/*  64 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*  66 */       else if (button.id == 0) {
/*     */         
/*  68 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  69 */         isaveformat.renameWorld(this.saveName, this.field_146583_f.getText().trim());
/*  70 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  81 */     this.field_146583_f.textboxKeyTyped(typedChar, keyCode);
/*  82 */     ((GuiButton)this.buttonList.get(0)).enabled = !this.field_146583_f.getText().trim().isEmpty();
/*     */     
/*  84 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/*  86 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  95 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  96 */     this.field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 104 */     drawDefaultBackground();
/* 105 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), this.width / 2, 20, 16777215);
/* 106 */     drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, 10526880);
/* 107 */     this.field_146583_f.drawTextBox();
/* 108 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiRenameWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */