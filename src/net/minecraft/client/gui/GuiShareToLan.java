/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class GuiShareToLan
/*     */   extends GuiScreen {
/*     */   private final GuiScreen field_146598_a;
/*     */   private GuiButton field_146596_f;
/*     */   private GuiButton field_146597_g;
/*  14 */   private String field_146599_h = "survival";
/*     */   
/*     */   private boolean field_146600_i;
/*     */   
/*     */   public GuiShareToLan(GuiScreen p_i1055_1_) {
/*  19 */     this.field_146598_a = p_i1055_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  28 */     this.buttonList.clear();
/*  29 */     this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
/*  30 */     this.buttonList.add(new GuiButton(102, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  31 */     this.buttonList.add(this.field_146597_g = new GuiButton(104, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  32 */     this.buttonList.add(this.field_146596_f = new GuiButton(103, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  33 */     func_146595_g();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146595_g() {
/*  38 */     this.field_146597_g.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + " " + I18n.format("selectWorld.gameMode." + this.field_146599_h, new Object[0]);
/*  39 */     this.field_146596_f.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";
/*     */     
/*  41 */     if (this.field_146600_i) {
/*     */       
/*  43 */       this.field_146596_f.displayString += I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/*  47 */       this.field_146596_f.displayString += I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  55 */     if (button.id == 102) {
/*     */       
/*  57 */       this.mc.displayGuiScreen(this.field_146598_a);
/*     */     }
/*  59 */     else if (button.id == 104) {
/*     */       
/*  61 */       if (this.field_146599_h.equals("spectator")) {
/*     */         
/*  63 */         this.field_146599_h = "creative";
/*     */       }
/*  65 */       else if (this.field_146599_h.equals("creative")) {
/*     */         
/*  67 */         this.field_146599_h = "adventure";
/*     */       }
/*  69 */       else if (this.field_146599_h.equals("adventure")) {
/*     */         
/*  71 */         this.field_146599_h = "survival";
/*     */       }
/*     */       else {
/*     */         
/*  75 */         this.field_146599_h = "spectator";
/*     */       } 
/*     */       
/*  78 */       func_146595_g();
/*     */     }
/*  80 */     else if (button.id == 103) {
/*     */       
/*  82 */       this.field_146600_i = !this.field_146600_i;
/*  83 */       func_146595_g();
/*     */     }
/*  85 */     else if (button.id == 101) {
/*     */       ChatComponentText chatComponentText;
/*  87 */       this.mc.displayGuiScreen((GuiScreen)null);
/*  88 */       String s = this.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
/*     */ 
/*     */       
/*  91 */       if (s != null) {
/*     */         
/*  93 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.publish.started", new Object[] { s });
/*     */       }
/*     */       else {
/*     */         
/*  97 */         chatComponentText = new ChatComponentText("commands.publish.failed");
/*     */       } 
/*     */       
/* 100 */       this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatComponentText);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 109 */     drawDefaultBackground();
/* 110 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), this.width / 2, 50, 16777215);
/* 111 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), this.width / 2, 82, 16777215);
/* 112 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiShareToLan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */