/*     */ package awareline.main.ui.gui.verify.jump;
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.fontmanager.UnicodeFontRenderer;
/*     */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientMainMenu;
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import java.awt.AWTException;
/*     */ import java.awt.SystemTray;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.TrayIcon;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("maximum")
/*     */ @StringEncryption("maximum")
/*     */ @StringEncryptionType("fast")
/*     */ public class GuiWelcome extends GuiScreen {
/*     */   private long startTime;
/*  27 */   private float alpha = 1.0F;
/*     */   private boolean needBlack;
/*     */   private final String text;
/*     */   final UnicodeFontRenderer fontwel;
/*     */   
/*     */   private void ShowSystemNotification(String title, String text, TrayIcon.MessageType type, Long delay) throws AWTException {
/*  33 */     if (SystemTray.isSupported()) {
/*  34 */       TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("icon.png"), title);
/*  35 */       trayIcon.setImageAutoSize(true);
/*  36 */       SystemTray.getSystemTray().add(trayIcon);
/*  37 */       trayIcon.displayMessage(title, text, type);
/*  38 */       (new Thread(() -> {
/*     */             try {
/*     */               Thread.sleep(delay.longValue());
/*  41 */             } catch (InterruptedException ex) {
/*     */               ex.printStackTrace();
/*     */             } 
/*     */             SystemTray.getSystemTray().remove(trayIcon);
/*  45 */           })).start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  52 */     if (VerifyData.instance.UserName.equals("ho3") || VerifyData.instance.UserName
/*  53 */       .equals("Margele")) {
/*  54 */       Minecraft.getMinecraft().shutdown();
/*     */     } else {
/*     */       try {
/*  57 */         Client.instance.getClass(); ShowSystemNotification("Welcome to " + "Awareline", "Logged in as " + VerifyData.instance.UserName, TrayIcon.MessageType.INFO, Long.valueOf(3000L));
/*  58 */       } catch (AWTException e) {
/*  59 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiWelcome() {
/*  70 */     this.fontwel = Client.instance.FontManager.RobotoLight40;
/*     */     String[] bye2 = { "Welcome to Awareline Client!" };
/*     */     Random r = new Random();
/*     */     this.text = bye2[r.nextInt(bye2.length)]; } public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  74 */     if (this.startTime == 0L) {
/*  75 */       this.startTime = System.currentTimeMillis();
/*     */     }
/*     */     
/*  78 */     RenderUtil.drawImage(new ResourceLocation("client/guimainmenu/BG.mc"), -30.0F, -30.0F, (this.width + 60), (this.height + 60));
/*     */     
/*  80 */     this.fontwel.drawCenteredString(this.text, this.width / 2, this.height / 2 - 24, ColorUtils.WHITE.c);
/*  81 */     Client.instance.FontManager.RobotoLight18.drawCenteredString("Logged as " + VerifyData.instance.UserName, this.width / 2, this.height / 2, ColorUtils.WHITE.c);
/*  82 */     drawRect(0, 0, this.width, this.height, RenderUtil.reAlpha(ColorUtils.BLACK.c, Math.abs(this.alpha)));
/*     */     
/*  84 */     if (this.needBlack) {
/*  85 */       if (this.alpha != 1.0F) {
/*  86 */         this.alpha = AnimationUtil.getAnimationStateFlux(this.alpha, 1.0F, 3.0F);
/*     */       } else {
/*  88 */         this.mc.displayGuiScreen((GuiScreen)new ClientMainMenu());
/*     */       } 
/*     */     } else {
/*  91 */       if (this.alpha != 0.0F && this.startTime + 1000L <= System.currentTimeMillis()) {
/*  92 */         this.alpha = AnimationUtil.getAnimationStateFlux(this.alpha, 0.0F, 3.0F);
/*     */       }
/*  94 */       if (this.alpha <= 0.0F && this.startTime + 5000L <= System.currentTimeMillis()) {
/*  95 */         this.alpha = 0.0F;
/*  96 */         this.needBlack = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 103 */     this.needBlack = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\verify\jump\GuiWelcome.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */