/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.io.IOException;
/*     */ import java.net.IDN;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenAddServer
/*     */   extends GuiScreen {
/*     */   private final GuiScreen parentScreen;
/*     */   private final ServerData serverData;
/*     */   private GuiTextField serverIPField;
/*     */   private GuiTextField serverNameField;
/*     */   private GuiButton serverResourcePacks;
/*     */   
/*  18 */   private final Predicate<String> field_181032_r = new Predicate<String>()
/*     */     {
/*     */       public boolean apply(String p_apply_1_)
/*     */       {
/*  22 */         if (p_apply_1_.isEmpty())
/*     */         {
/*  24 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  28 */         String[] astring = p_apply_1_.split(":");
/*     */         
/*  30 */         if (astring.length == 0)
/*     */         {
/*  32 */           return true;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  38 */           String s = IDN.toASCII(astring[0]);
/*  39 */           return true;
/*     */         }
/*  41 */         catch (IllegalArgumentException var4) {
/*     */           
/*  43 */           return false;
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenAddServer(GuiScreen p_i1033_1_, ServerData p_i1033_2_) {
/*  52 */     this.parentScreen = p_i1033_1_;
/*  53 */     this.serverData = p_i1033_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  61 */     this.serverNameField.updateCursorCounter();
/*  62 */     this.serverIPField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  71 */     Keyboard.enableRepeatEvents(true);
/*  72 */     this.buttonList.clear();
/*  73 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, I18n.format("addServer.add", new Object[0])));
/*  74 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, I18n.format("gui.cancel", new Object[0])));
/*  75 */     this.buttonList.add(this.serverResourcePacks = new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, I18n.format("addServer.resourcePack", new Object[0]) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText()));
/*  76 */     this.serverNameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
/*  77 */     this.serverNameField.setFocused(true);
/*  78 */     this.serverNameField.setText(this.serverData.serverName);
/*  79 */     this.serverIPField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
/*  80 */     this.serverIPField.setMaxStringLength(128);
/*  81 */     this.serverIPField.setText(this.serverData.serverIP);
/*  82 */     this.serverIPField.setValidator(this.field_181032_r);
/*  83 */     ((GuiButton)this.buttonList.get(0)).enabled = (!this.serverIPField.getText().isEmpty() && (this.serverIPField.getText().split(":")).length > 0 && !this.serverNameField.getText().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  91 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  98 */     if (button.enabled)
/*     */     {
/* 100 */       if (button.id == 2) {
/*     */         
/* 102 */         this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % (ServerData.ServerResourceMode.values()).length]);
/* 103 */         this.serverResourcePacks.displayString = I18n.format("addServer.resourcePack", new Object[0]) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText();
/*     */       }
/* 105 */       else if (button.id == 1) {
/*     */         
/* 107 */         this.parentScreen.confirmClicked(false, 0);
/*     */       }
/* 109 */       else if (button.id == 0) {
/*     */         
/* 111 */         this.serverData.serverName = this.serverNameField.getText();
/* 112 */         this.serverData.serverIP = this.serverIPField.getText();
/* 113 */         this.parentScreen.confirmClicked(true, 0);
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
/* 124 */     this.serverNameField.textboxKeyTyped(typedChar, keyCode);
/* 125 */     this.serverIPField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/* 127 */     if (keyCode == 15) {
/*     */       
/* 129 */       this.serverNameField.setFocused(!this.serverNameField.isFocused());
/* 130 */       this.serverIPField.setFocused(!this.serverIPField.isFocused());
/*     */     } 
/*     */     
/* 133 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 135 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 138 */     ((GuiButton)this.buttonList.get(0)).enabled = (!this.serverIPField.getText().isEmpty() && (this.serverIPField.getText().split(":")).length > 0 && !this.serverNameField.getText().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 146 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 147 */     this.serverIPField.mouseClicked(mouseX, mouseY, mouseButton);
/* 148 */     this.serverNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 156 */     drawDefaultBackground();
/* 157 */     drawCenteredString(this.fontRendererObj, I18n.format("addServer.title", new Object[0]), this.width / 2, 17, 16777215);
/* 158 */     drawString(this.fontRendererObj, I18n.format("addServer.enterName", new Object[0]), this.width / 2 - 100, 53, 10526880);
/* 159 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 94, 10526880);
/* 160 */     this.serverNameField.drawTextBox();
/* 161 */     this.serverIPField.drawTextBox();
/* 162 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiScreenAddServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */