/*     */ package awareline.main.ui.gui.guimainmenu.mainmenu;
/*     */ import awareline.antileak.JumpMainMenuCheck;
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.guimainmenu.GuiGoodBye;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.button.FlatButton;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.button.FlatButtonNoBold;
/*     */ import awareline.main.ui.gui.guimainmenu.shader.MenuShader;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiLabel;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSelectWorld;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ @StringEncryption("enable")
/*     */ @StringEncryptionType("fast")
/*     */ public class ClientMainMenu extends GuiScreen {
/*  32 */   protected final List<GuiButton> guiMainButtonList = Lists.newArrayList(); public static boolean useShader;
/*  33 */   private final MenuShader menuShader = new MenuShader();
/*     */   public int textAlpha;
/*     */   public boolean Bye;
/*     */   public float lastPercent;
/*     */   public float percent;
/*     */   public float percent2;
/*     */   public float lastPercent2;
/*  40 */   public float alpha = 1.0F;
/*     */   
/*     */   boolean isJumpToClientSettingGui;
/*     */   private float textY;
/*     */   private boolean isCheckOut;
/*     */   private int impalpha;
/*     */   private int impalpha2;
/*     */   
/*     */   public void initGui() {
/*  49 */     if (!this.isCheckOut) {
/*  50 */       if (VerifyData.instance.UserName.equals("User") || VerifyData.instance.UserName.equals("12345") || VerifyData.instance.UserName.equals("1234") || VerifyData.instance.UserName.equals("null") || VerifyData.instance.UserName.equals("Null")) {
/*  51 */         JOptionPane.showInputDialog((Component)null, "User name Bad", "User name is bad, ID 2000CMM");
/*  52 */         Minecraft.getMinecraft().shutdown();
/*     */         
/*     */         return;
/*     */       } 
/*  56 */       if (VerifyData.instance.UserName == null || VerifyData.instance.UserName.isEmpty()) {
/*  57 */         JOptionPane.showInputDialog((Component)null, "User name null", "User name is empty, ID 2001CMM");
/*  58 */         this.mc.shutdown();
/*     */         
/*     */         return;
/*     */       } 
/*  62 */       if (VerifyData.instance.UserName.equals("User") || VerifyData.instance.UserName.equals("12345") || VerifyData.instance.UserName.equals("1234") || VerifyData.instance.UserName.equals("null") || VerifyData.instance.UserName.equals("Null")) {
/*  63 */         JOptionPane.showInputDialog((Component)null, "User name bad", "User name is bad, ID 2002CMM");
/*  64 */         this.mc.shutdown();
/*     */         
/*     */         return;
/*     */       } 
/*  68 */       if (VerifyData.instance.UserName == null || VerifyData.instance.UserName.isEmpty()) {
/*  69 */         JOptionPane.showInputDialog((Component)null, "User name null", "User name is empty, ID 2003CMM");
/*  70 */         this.mc.shutdown();
/*     */         
/*     */         return;
/*     */       } 
/*  74 */       if (VerifyData.instance.UserName.equals("User") || VerifyData.instance.UserName.equals("12345") || VerifyData.instance.UserName.equals("1234") || VerifyData.instance.UserName.equals("null") || VerifyData.instance.UserName.equals("Null")) {
/*  75 */         JOptionPane.showInputDialog((Component)null, "User name bad", "锟揭诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟脚革拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�, User name is bad, ID 9999NMSL");
/*  76 */         this.mc.shutdown();
/*     */         
/*     */         return;
/*     */       } 
/*  80 */       JumpMainMenuCheck jumpMainMenuCheck = new JumpMainMenuCheck();
/*  81 */       if (jumpMainMenuCheck.clientIsLeakingOrInCracked) {
/*  82 */         JOptionPane.showInputDialog((Component)null, "HWID Error", "Your hwid is error, ID 9998NMSL");
/*  83 */         this.mc.shutdown();
/*     */         
/*     */         return;
/*     */       } 
/*  87 */       this.isCheckOut = true;
/*     */     } 
/*     */     
/*  90 */     this.guiMainButtonList.clear();
/*  91 */     this.percent = 1.33F;
/*  92 */     this.lastPercent = 1.0F;
/*  93 */     this.percent2 = 1.33F;
/*  94 */     this.lastPercent2 = 1.0F;
/*  95 */     this.alpha = 1.0F;
/*  96 */     this.textY = -15.0F;
/*  97 */     String singleplayer = "Single Player";
/*  98 */     String multiplayer = "Switch Player";
/*  99 */     this.guiMainButtonList.add(new FlatButton(0, (this.width / 6), (this.height / 2 + 12 - 56 - 1), 162, 16, singleplayer));
/*     */     
/* 101 */     this.guiMainButtonList.add(new FlatButtonNoBold(1, (this.width / 6), (this.height / 2 + 12 + 20 - 56 + 20), 162, 16, multiplayer));
/*     */     
/* 103 */     super.initGui();
/*     */   }
/*     */   
/*     */   public float smoothTrans(float current, float last) {
/* 107 */     int limitFPS = Minecraft.getDebugFPS();
/* 108 */     int FPS = Math.abs(Math.max(limitFPS, 30));
/* 109 */     return current + (last - current) / (FPS / 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 114 */     updateTextAlpha();
/*     */     
/* 116 */     float height = this.height;
/* 117 */     float width = this.width;
/*     */     
/* 119 */     this.percent = smoothTrans(this.percent, this.lastPercent);
/* 120 */     this.percent2 = smoothTrans(this.percent2, this.lastPercent2);
/* 121 */     if (this.percent > 0.981F) {
/* 122 */       GlStateManager.translate(width / 2.0F, height / 2.0F, 0.0F);
/* 123 */       GlStateManager.scale(this.percent, this.percent, 0.0F);
/*     */     } else {
/* 125 */       this.percent2 = smoothTrans(this.percent2, this.lastPercent2);
/* 126 */       GlStateManager.translate(width / 2.0F, height / 2.0F, 0.0F);
/* 127 */       GlStateManager.scale(this.percent2, this.percent2, 0.0F);
/*     */     } 
/*     */     
/* 130 */     GlStateManager.translate(-width / 2.0F, -height / 2.0F, 0.0F);
/* 131 */     drawBackground(mouseX, mouseY);
/* 132 */     drawMainMenu();
/* 133 */     drawButton(mouseX, mouseY);
/*     */     
/* 135 */     if (!this.Bye) {
/* 136 */       this.alpha = AnimationUtil.getAnimationStateFlux(this.alpha, 0.0F, 3.0F);
/* 137 */     } else if (this.alpha == 1.0F) {
/* 138 */       if (this.isJumpToClientSettingGui) {
/* 139 */         this.mc.displayGuiScreen(new GuiClientSetting(this, true));
/* 140 */         this.isJumpToClientSettingGui = false;
/*     */       } else {
/* 142 */         this.mc.displayGuiScreen((GuiScreen)new GuiGoodBye());
/*     */       } 
/*     */     } else {
/* 145 */       this.alpha = AnimationUtil.getAnimationStateFlux(this.alpha, 1.0F, 3.0F);
/*     */     } 
/*     */     
/* 148 */     drawRect(0.0D, 0.0D, width, height, RenderUtil.reAlpha(ColorUtils.BLACK.c, Math.abs(this.alpha)));
/*     */     
/* 150 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {
/* 156 */     if (!((Boolean)ClientSetting.shaderBG.get()).booleanValue()) {
/* 157 */       if (Client.instance.getCustomBackground() != null) {
/* 158 */         Client.instance.getCustomBackground().load().bind().draw(0.0D, 0.0D, this.width, this.height);
/*     */       } else {
/* 160 */         RenderUtil.drawImage(new ResourceLocation("client/RandomBG/4.png"), 0.0F, 0.0F, this.width, this.height);
/*     */       } 
/*     */     } else {
/* 163 */       this.menuShader.render(new ScaledResolution(this.mc), true);
/*     */     } 
/*     */ 
/*     */     
/* 167 */     renderRightText(mouseX, mouseY);
/*     */     
/* 169 */     for (GuiButton guiButton : this.guiMainButtonList) {
/* 170 */       guiButton.drawButton(this.mc, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderRightText(int mouseX, int mouseY) {
/* 176 */     boolean isOverSettings = (mouseX > this.width - 100 && mouseX < this.width - 77 + Client.instance.FontLoaders.Comfortaa16.getStringWidth("Settings") && mouseY > 10 && mouseY < 22);
/*     */ 
/*     */     
/* 179 */     boolean isOverAlt = (mouseX > this.width - 180 && mouseX < this.width - 106 + Client.instance.FontLoaders.Comfortaa22.getStringWidth("Alts /") && mouseY > 10 && mouseY < 22);
/*     */     
/* 181 */     this.impalpha = (int)AnimationUtil.moveUD(this.impalpha, isOverSettings ? 229.0F : 178.0F, 0.2F, 0.15F);
/* 182 */     this.impalpha2 = (int)AnimationUtil.moveUD(this.impalpha2, isOverAlt ? 229.0F : 178.0F, 0.2F, 0.15F);
/*     */     
/* 184 */     Client.instance.FontLoaders.Comfortaa22.drawString("Settings", (this.width - 77), 13.0F, (new Color(255, 255, 255, this.impalpha))
/* 185 */         .getRGB());
/* 186 */     Client.instance.FontLoaders.Comfortaa22.drawString("Alts /", (this.width - 106), 13.0F, (new Color(255, 255, 255, this.impalpha2))
/* 187 */         .getRGB());
/*     */     
/* 189 */     RenderUtil.drawIcon((this.width - 27), 10.5F, 13, 13, new ResourceLocation("client/guimainmenu/close.png"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawButton(int mouseX, int mouseY) {
/* 194 */     for (GuiLabel guiLabel : this.labelList) {
/* 195 */       guiLabel.drawLabel(mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateTextAlpha() {
/* 200 */     if (this.textAlpha != 200) {
/* 201 */       this.textAlpha = (int)AnimationUtil.moveUD(this.textAlpha, 200.0F, 0.2F, 0.15F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawMainMenu() {
/* 206 */     float h = this.height;
/* 207 */     float w = this.width;
/*     */ 
/*     */     
/* 210 */     this.textY = AnimationUtil.moveUD(this.textY, 0.0F, 0.11F, 0.1F);
/* 211 */     VerifyData.instance.getClass(); Client.instance.FontLoaders.bold16.drawString("Awareline " + (
/* 212 */         !VerifyData.instance.UserName.isEmpty() ? "NextGen" : "NG"), 8.0F, h - 12.0F - this.textY, 
/* 213 */         textColor());
/*     */     
/* 215 */     String text2 = "Welcome , " + VerifyData.instance.UserName;
/* 216 */     Client.instance.FontLoaders.bold16.drawString(text2, w - Client.instance.FontLoaders.bold16.getStringWidth(text2) - 8.0F, h - 12.0F, textColor());
/*     */   }
/*     */   
/*     */   public int textColor() {
/* 220 */     return (new Color(233, 233, 233, this.textAlpha)).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 225 */     boolean isOverSettings = (mouseX > this.width - 100 && mouseX < this.width - 82 + Client.instance.FontLoaders.Comfortaa16.getStringWidth("Settings") && mouseY > 10 && mouseY < 22);
/* 226 */     boolean isOverAlt = (mouseX > this.width - 180 && mouseX < this.width - 162 + Client.instance.FontLoaders.Comfortaa22.getStringWidth("AltManager") && mouseY > 10 && mouseY < 22);
/* 227 */     boolean isOverExit = (mouseX > this.width - 27 && mouseX < this.width - 27 + 13 && mouseY > 10 && mouseY < 24);
/* 228 */     if (mouseButton == 0 && isOverSettings) {
/* 229 */       this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */       
/* 231 */       this.Bye = this.isJumpToClientSettingGui = true;
/*     */     } 
/* 233 */     if (mouseButton == 0 && isOverAlt) {
/* 234 */       this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */     }
/*     */ 
/*     */     
/* 238 */     if (mouseButton == 0 && isOverExit) {
/* 239 */       Client.instance.saveConfig();
/* 240 */       this.Bye = true;
/*     */     } 
/* 242 */     if (mouseButton == 0) {
/* 243 */       for (GuiButton guibutton : this.guiMainButtonList) {
/* 244 */         if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
/* 245 */           this.selectedButton = guibutton;
/* 246 */           guibutton.playPressSound(this.mc.getSoundHandler());
/* 247 */           actionPerformed(guibutton);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 252 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 257 */     if (button.id == 779) {
/* 258 */       initGui();
/*     */     }
/*     */     
/* 261 */     if (button.id == 0) {
/* 262 */       this.mc.displayGuiScreen((GuiScreen)new GuiSelectWorld(this));
/*     */     }
/*     */     
/* 265 */     if (button.id == 1) {
/* 266 */       this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer(this));
/*     */     }
/*     */     
/* 269 */     super.actionPerformed(button);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\mainmenu\ClientMainMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */