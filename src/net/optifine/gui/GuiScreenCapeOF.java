/*     */ package net.optifine.gui;
/*     */ 
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ 
/*     */ 
/*     */ public class GuiScreenCapeOF
/*     */   extends GuiScreenOF
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private String title;
/*     */   private String message;
/*     */   private long messageHideTimeMs;
/*     */   private String linkUrl;
/*     */   private GuiButtonOF buttonCopyLink;
/*     */   private final FontRenderer fontRenderer;
/*     */   
/*     */   public GuiScreenCapeOF(GuiScreen parentScreenIn) {
/*  27 */     this.fontRenderer = (Config.getMinecraft()).fontRendererObj;
/*  28 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  37 */     int i = 0;
/*  38 */     this.title = I18n.format("of.options.capeOF.title", new Object[0]);
/*  39 */     i += 2;
/*  40 */     this.buttonList.add(new GuiButtonOF(210, this.width / 2 - 155, this.height / 6 + 24 * (i >> 1), 150, 20, I18n.format("of.options.capeOF.openEditor", new Object[0])));
/*  41 */     this.buttonList.add(new GuiButtonOF(220, this.width / 2 - 155 + 160, this.height / 6 + 24 * (i >> 1), 150, 20, I18n.format("of.options.capeOF.reloadCape", new Object[0])));
/*  42 */     i += 6;
/*  43 */     this.buttonCopyLink = new GuiButtonOF(230, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), 200, 20, I18n.format("of.options.capeOF.copyEditorLink", new Object[0]));
/*  44 */     this.buttonCopyLink.visible = (this.linkUrl != null);
/*  45 */     this.buttonList.add(this.buttonCopyLink);
/*  46 */     i += 4;
/*  47 */     this.buttonList.add(new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  55 */     if (button.enabled) {
/*     */       
/*  57 */       if (button.id == 200)
/*     */       {
/*  59 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*     */       
/*  62 */       if (button.id == 210) {
/*     */         
/*     */         try {
/*     */           
/*  66 */           String s = this.mc.getSession().getProfile().getName();
/*  67 */           String s1 = this.mc.getSession().getProfile().getId().toString().replace("-", "");
/*  68 */           String s2 = this.mc.getSession().getToken();
/*  69 */           Random random = new Random();
/*  70 */           Random random1 = new Random(System.identityHashCode(new Object()));
/*  71 */           BigInteger biginteger = new BigInteger(128, random);
/*  72 */           BigInteger biginteger1 = new BigInteger(128, random1);
/*  73 */           BigInteger biginteger2 = biginteger.xor(biginteger1);
/*  74 */           String s3 = biginteger2.toString(16);
/*  75 */           this.mc.getSessionService().joinServer(this.mc.getSession().getProfile(), s2, s3);
/*  76 */           String s4 = "https://optifine.net/capeChange?u=" + s1 + "&n=" + s + "&s=" + s3;
/*  77 */           boolean flag = Config.openWebLink(new URI(s4));
/*     */           
/*  79 */           if (flag)
/*     */           {
/*  81 */             showMessage(Lang.get("of.message.capeOF.openEditor"), 10000L);
/*     */           }
/*     */           else
/*     */           {
/*  85 */             showMessage(Lang.get("of.message.capeOF.openEditorError"), 10000L);
/*  86 */             setLinkUrl(s4);
/*     */           }
/*     */         
/*  89 */         } catch (InvalidCredentialsException invalidcredentialsexception) {
/*     */           
/*  91 */           Config.showGuiMessage(I18n.format("of.message.capeOF.error1", new Object[0]), I18n.format("of.message.capeOF.error2", new Object[] { invalidcredentialsexception.getMessage() }));
/*  92 */           Config.warn("Mojang authentication failed");
/*  93 */           Config.warn(invalidcredentialsexception.getClass().getName() + ": " + invalidcredentialsexception.getMessage());
/*     */         }
/*  95 */         catch (Exception exception) {
/*     */           
/*  97 */           Config.warn("Error opening OptiFine cape link");
/*  98 */           Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */         } 
/*     */       }
/*     */       
/* 102 */       if (button.id == 220) {
/*     */         
/* 104 */         showMessage(Lang.get("of.message.capeOF.reloadCape"), 15000L);
/*     */         
/* 106 */         if (this.mc.thePlayer != null) {
/*     */           
/* 108 */           long i = 15000L;
/* 109 */           long j = System.currentTimeMillis() + i;
/* 110 */           this.mc.thePlayer.setReloadCapeTimeMs(j);
/*     */         } 
/*     */       } 
/*     */       
/* 114 */       if (button.id == 230 && this.linkUrl != null)
/*     */       {
/* 116 */         setClipboardString(this.linkUrl);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void showMessage(String msg, long timeMs) {
/* 123 */     this.message = msg;
/* 124 */     this.messageHideTimeMs = System.currentTimeMillis() + timeMs;
/* 125 */     setLinkUrl((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 133 */     drawDefaultBackground();
/* 134 */     drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
/*     */     
/* 136 */     if (this.message != null) {
/*     */       
/* 138 */       drawCenteredString(this.fontRenderer, this.message, this.width / 2, this.height / 6 + 60, 16777215);
/*     */       
/* 140 */       if (System.currentTimeMillis() > this.messageHideTimeMs) {
/*     */         
/* 142 */         this.message = null;
/* 143 */         setLinkUrl((String)null);
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLinkUrl(String linkUrl) {
/* 152 */     this.linkUrl = linkUrl;
/* 153 */     this.buttonCopyLink.visible = (linkUrl != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiScreenCapeOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */