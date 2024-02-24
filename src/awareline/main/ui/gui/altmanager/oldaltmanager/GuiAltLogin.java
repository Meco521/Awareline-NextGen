/*     */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiAltLogin
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiPasswordField password;
/*     */   private final GuiScreen previousScreen;
/*     */   private AltLoginThread thread;
/*     */   private GuiTextField username;
/*     */   
/*     */   public GuiAltLogin(GuiScreen previousScreen) {
/*  19 */     this.previousScreen = previousScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  24 */     switch (button.id) {
/*     */       case 1:
/*  26 */         this.mc.displayGuiScreen(this.previousScreen);
/*     */         break;
/*     */       
/*     */       case 0:
/*  30 */         this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
/*  31 */         this.thread.start();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int x, int y, float z) {
/*  39 */     drawDefaultBackground();
/*  40 */     this.username.drawTextBox();
/*  41 */     this.password.drawTextBox();
/*  42 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString("Alt Login", this.width / 2.0F, 20.0F, -1);
/*  43 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString((this.thread == null) ? "§eWaiting..." : this.thread.getStatus(), this.width / 2.0F, 29.0F, -1);
/*  44 */     if (this.username.getText().isEmpty()) {
/*  45 */       (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("Username / E-Mail", this.width / 2.0F - 96.0F, 66.0F, -7829368);
/*     */     }
/*  47 */     if (this.password.getText().isEmpty()) {
/*  48 */       (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("Password", this.width / 2.0F - 96.0F, 106.0F, -7829368);
/*     */     }
/*  50 */     super.drawScreen(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  55 */     int var3 = this.height / 4 + 24;
/*  56 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
/*  57 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
/*  58 */     this.username = new GuiTextField(1, (Minecraft.getMinecraft()).fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  59 */     this.password = new GuiPasswordField((Minecraft.getMinecraft()).fontRendererObj, this.width / 2 - 100, 100, 200, 20);
/*  60 */     this.username.setFocused(true);
/*  61 */     this.username.setMaxStringLength(200);
/*  62 */     this.password.func_146203_f(200);
/*  63 */     Keyboard.enableRepeatEvents(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char character, int key) {
/*     */     try {
/*  69 */       super.keyTyped(character, key);
/*  70 */     } catch (IOException e) {
/*  71 */       e.printStackTrace();
/*     */     } 
/*  73 */     if (character == '\t' && (this.username.isFocused() || this.password.isFocused())) {
/*  74 */       this.username.setFocused(!this.username.isFocused());
/*  75 */       this.password.setFocused(!this.password.isFocused());
/*     */     } 
/*  77 */     if (character == '\r') {
/*  78 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*  80 */     this.username.textboxKeyTyped(character, key);
/*  81 */     this.password.textboxKeyTyped(character, key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int x, int y, int button) {
/*     */     try {
/*  87 */       super.mouseClicked(x, y, button);
/*  88 */     } catch (IOException e) {
/*  89 */       e.printStackTrace();
/*     */     } 
/*  91 */     this.username.mouseClicked(x, y, button);
/*  92 */     this.password.mouseClicked(x, y, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  97 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 102 */     this.username.updateCursorCounter();
/* 103 */     this.password.updateCursorCounter();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\GuiAltLogin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */