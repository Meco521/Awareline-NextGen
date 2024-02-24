/*     */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*     */ 
/*     */ import awareline.main.mod.manager.FileManager;
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*     */ import java.io.IOException;
/*     */ import java.net.Proxy;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiAddAlt
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiAltManager manager;
/*     */   private GuiPasswordField password;
/*  21 */   private String status = "§eWaiting...";
/*     */   private GuiTextField username;
/*     */   
/*     */   public GuiAddAlt(GuiAltManager manager) {
/*  25 */     this.manager = manager;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  30 */     switch (button.id) {
/*     */       case 0:
/*  32 */         if (!this.username.getText().isEmpty()) {
/*     */           
/*  34 */           AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
/*  35 */           login.start();
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 1:
/*  40 */         this.mc.displayGuiScreen(this.manager);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int i, int j, float f) {
/*  48 */     drawDefaultBackground();
/*  49 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString("Add Alt", this.width / 2.0F, 20.0F, -1);
/*  50 */     this.username.drawTextBox();
/*  51 */     this.password.drawTextBox();
/*  52 */     if (this.username.getText().isEmpty()) {
/*  53 */       (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("Username / E-Mail", this.width / 2.0F - 96.0F, 66.0F, -7829368);
/*     */     }
/*  55 */     if (this.password.getText().isEmpty()) {
/*  56 */       (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("Password", this.width / 2.0F - 96.0F, 106.0F, -7829368);
/*     */     }
/*  58 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString(this.status, this.width / 2.0F, 30.0F, -1);
/*  59 */     super.drawScreen(i, j, f);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  64 */     Keyboard.enableRepeatEvents(true);
/*  65 */     this.buttonList.clear();
/*  66 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Login"));
/*  67 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
/*  68 */     this.username = new GuiTextField(1, (Minecraft.getMinecraft()).fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  69 */     this.password = new GuiPasswordField((Minecraft.getMinecraft()).fontRendererObj, this.width / 2 - 100, 100, 200, 20);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char par1, int par2) {
/*  74 */     this.username.textboxKeyTyped(par1, par2);
/*  75 */     this.password.textboxKeyTyped(par1, par2);
/*  76 */     if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) {
/*  77 */       this.username.setFocused(!this.username.isFocused());
/*  78 */       this.password.setFocused(!this.password.isFocused());
/*     */     } 
/*  80 */     if (par1 == '\r') {
/*  81 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int par1, int par2, int par3) {
/*     */     try {
/*  88 */       super.mouseClicked(par1, par2, par3);
/*  89 */     } catch (IOException e) {
/*  90 */       e.printStackTrace();
/*     */     } 
/*  92 */     this.username.mouseClicked(par1, par2, par3);
/*  93 */     this.password.mouseClicked(par1, par2, par3);
/*     */   }
/*     */ 
/*     */   
/*     */   private class AddAltThread
/*     */     extends Thread
/*     */   {
/*     */     private final String password;
/*     */     
/*     */     private final String username;
/*     */     
/*     */     public AddAltThread(String username, String password) {
/* 105 */       this.username = username;
/* 106 */       this.password = password;
/* 107 */       GuiAddAlt.this.status = "§7Waiting...";
/*     */     }
/*     */     
/*     */     private void checkAndAddAlt(String username, String password) {
/* 111 */       YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
/* 112 */       YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
/* 113 */       auth.setUsername(username);
/* 114 */       auth.setPassword(password);
/*     */       try {
/* 116 */         auth.logIn();
/* 117 */         AltManager.getAlts().add(new Alt(username, password));
/* 118 */         FileManager.saveAlts();
/* 119 */         GuiAddAlt.this.status = "§aAlt added. (" + username + ")";
/* 120 */       } catch (AuthenticationException e) {
/* 121 */         GuiAddAlt.this.status = "§cAlt failed!";
/* 122 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 128 */       if (this.password.isEmpty()) {
/* 129 */         AltManager.getAlts().add(new Alt(this.username, ""));
/* 130 */         FileManager.saveAlts();
/* 131 */         GuiAddAlt.this.status = "§aAlt added. (" + this.username + " - offline name)";
/*     */         return;
/*     */       } 
/* 134 */       GuiAddAlt.this.status = "§eTrying alt...";
/* 135 */       checkAndAddAlt(this.username, this.password);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\GuiAddAlt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */