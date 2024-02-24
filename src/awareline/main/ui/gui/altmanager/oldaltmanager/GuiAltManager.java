/*     */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*     */ 
/*     */ import awareline.main.mod.manager.FileManager;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiAltManager
/*     */   extends GuiScreen {
/*  17 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*     */   private GuiButton login;
/*     */   private GuiButton remove;
/*     */   private GuiButton rename;
/*     */   public static AltLoginThread loginThread;
/*     */   private int offset;
/*     */   public Alt selectedAlt;
/*  24 */   private String status = "§eWaiting...";
/*     */   
/*     */   public GuiAltManager() {
/*  27 */     FileManager.saveAlts(); } public void actionPerformed(GuiButton button) { String user; Alt randomAlt; Alt lastAlt; String pass;
/*     */     String user1;
/*     */     String user2;
/*     */     String pass1;
/*     */     String pass2;
/*  32 */     switch (button.id) {
/*     */       case 0:
/*  34 */         if (loginThread == null) {
/*  35 */           mc.displayGuiScreen(null);
/*     */           break;
/*     */         } 
/*  38 */         if (!loginThread.getStatus().equals("Logging in...") && !loginThread.getStatus().equals("Do not hit back! Logging in...")) {
/*  39 */           mc.displayGuiScreen(null);
/*     */           break;
/*     */         } 
/*  42 */         loginThread.setStatus("Do not hit back! Logging in...");
/*     */         break;
/*     */       
/*     */       case 1:
/*  46 */         user = this.selectedAlt.getUsername();
/*  47 */         pass = this.selectedAlt.getPassword();
/*  48 */         loginThread = new AltLoginThread(user, pass);
/*  49 */         loginThread.start();
/*     */         break;
/*     */       
/*     */       case 2:
/*  53 */         if (loginThread != null) {
/*  54 */           loginThread = null;
/*     */         }
/*  56 */         AltManager.getAlts().remove(this.selectedAlt);
/*  57 */         this.status = "§cRemoved.";
/*  58 */         this.selectedAlt = null;
/*  59 */         FileManager.saveAlts();
/*     */         break;
/*     */       
/*     */       case 3:
/*  63 */         mc.displayGuiScreen(new GuiAddAlt(this));
/*     */         break;
/*     */       
/*     */       case 4:
/*  67 */         mc.displayGuiScreen(new GuiAltLogin(this));
/*     */         break;
/*     */       
/*     */       case 5:
/*  71 */         randomAlt = AltManager.alts.get((new Random()).nextInt(AltManager.alts.size()));
/*  72 */         user1 = randomAlt.getUsername();
/*  73 */         pass1 = randomAlt.getPassword();
/*  74 */         loginThread = new AltLoginThread(user1, pass1);
/*  75 */         loginThread.start();
/*     */         break;
/*     */       
/*     */       case 6:
/*  79 */         mc.displayGuiScreen(new GuiRenameAlt(this));
/*     */         break;
/*     */       
/*     */       case 7:
/*  83 */         lastAlt = AltManager.lastAlt;
/*  84 */         if (lastAlt == null) {
/*  85 */           if (loginThread == null) {
/*  86 */             this.status = "?cThere is no last used alt!";
/*     */             break;
/*     */           } 
/*  89 */           loginThread.setStatus("?cThere is no last used alt!");
/*     */           break;
/*     */         } 
/*  92 */         user2 = lastAlt.getUsername();
/*  93 */         pass2 = lastAlt.getPassword();
/*  94 */         loginThread = new AltLoginThread(user2, pass2);
/*  95 */         loginThread.start();
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int par1, int par2, float par3) {
/* 103 */     drawDefaultBackground();
/* 104 */     if (Mouse.hasWheel()) {
/* 105 */       int wheel = Mouse.getDWheel();
/* 106 */       if (wheel < 0) {
/* 107 */         this.offset += 26;
/* 108 */         if (this.offset < 0) {
/* 109 */           this.offset = 0;
/*     */         }
/* 111 */       } else if (wheel > 0) {
/* 112 */         this.offset -= 26;
/* 113 */         if (this.offset < 0) {
/* 114 */           this.offset = 0;
/*     */         }
/*     */       } 
/*     */     } 
/* 118 */     drawDefaultBackground();
/* 119 */     (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(mc.session.getUsername(), 10.0F, 10.0F, -7829368);
/* 120 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString("Account Manager - " + AltManager.getAlts().size() + " alts", this.width / 2.0F, 10.0F, -1);
/* 121 */     (Minecraft.getMinecraft()).fontRendererObj.drawCenteredString((loginThread == null) ? this.status : loginThread.getStatus(), this.width / 2.0F, 20.0F, -1);
/* 122 */     GL11.glPushMatrix();
/* 123 */     prepareScissorBox(0.0F, 33.0F, this.width, (this.height - 50));
/* 124 */     GL11.glEnable(3089);
/* 125 */     int y = 38;
/* 126 */     for (Alt alt : AltManager.getAlts()) {
/* 127 */       if (!isAltInArea(y))
/* 128 */         continue;  String name = alt.getMask().isEmpty() ? alt.getUsername() : alt.getMask();
/* 129 */       StringBuilder pass = new StringBuilder();
/* 130 */       if (alt.getPassword().isEmpty()) {
/* 131 */         pass = new StringBuilder("§cCracked");
/*     */       } else {
/* 133 */         for (char ignored : alt.getPassword().toCharArray()) {
/* 134 */           pass.append("*");
/*     */         }
/*     */       } 
/* 137 */       if (alt == this.selectedAlt) {
/* 138 */         if (isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
/* 139 */           RenderUtil.drawBorderedRect(52.0F, (y - this.offset - 4), (this.width - 52), (y - this.offset + 20), 1.0F, -16777216, -2142943931);
/* 140 */         } else if (isMouseOverAlt(par1, par2, y - this.offset)) {
/* 141 */           RenderUtil.drawBorderedRect(52.0F, (y - this.offset - 4), (this.width - 52), (y - this.offset + 20), 1.0F, -16777216, -2142088622);
/*     */         } else {
/* 143 */           RenderUtil.drawBorderedRect(52.0F, (y - this.offset - 4), (this.width - 52), (y - this.offset + 20), 1.0F, -16777216, -2144259791);
/*     */         } 
/* 145 */       } else if (isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
/* 146 */         RenderUtil.drawBorderedRect(52.0F, (y - this.offset - 4), (this.width - 52), (y - this.offset + 20), 1.0F, -16777216, -2146101995);
/* 147 */       } else if (isMouseOverAlt(par1, par2, y - this.offset)) {
/* 148 */         RenderUtil.drawBorderedRect(52.0F, (y - this.offset - 4), (this.width - 52), (y - this.offset + 20), 1.0F, -16777216, -2145180893);
/*     */       } 
/* 150 */       mc.fontRendererObj.drawCenteredString(name, this.width / 2.0F, (y - this.offset), -1);
/* 151 */       mc.fontRendererObj.drawCenteredString(pass.toString(), this.width / 2.0F, (y - this.offset + 10), 5592405);
/* 152 */       y += 26;
/*     */     } 
/* 154 */     GL11.glDisable(3089);
/* 155 */     GL11.glPopMatrix();
/* 156 */     super.drawScreen(par1, par2, par3);
/* 157 */     if (this.selectedAlt == null) {
/* 158 */       this.login.enabled = false;
/* 159 */       this.remove.enabled = false;
/* 160 */       this.rename.enabled = false;
/*     */     } else {
/* 162 */       this.login.enabled = true;
/* 163 */       this.remove.enabled = true;
/* 164 */       this.rename.enabled = true;
/*     */     } 
/* 166 */     if (Keyboard.isKeyDown(200)) {
/* 167 */       this.offset -= 26;
/* 168 */       if (this.offset < 0) {
/* 169 */         this.offset = 0;
/*     */       }
/* 171 */     } else if (Keyboard.isKeyDown(208)) {
/* 172 */       this.offset += 26;
/* 173 */       if (this.offset < 0) {
/* 174 */         this.offset = 0;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 181 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 24, 75, 20, "Back"));
/* 182 */     this.login = new GuiButton(1, this.width / 2 - 154, this.height - 48, 70, 20, "Login");
/* 183 */     this.buttonList.add(this.login);
/* 184 */     this.remove = new GuiButton(2, this.width / 2 - 74, this.height - 24, 70, 20, "Remove");
/* 185 */     this.buttonList.add(this.remove);
/* 186 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 76, this.height - 48, 75, 20, "Add"));
/* 187 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 74, this.height - 48, 70, 20, "Direct Login"));
/* 188 */     this.buttonList.add(new GuiButton(5, this.width / 2 + 4, this.height - 48, 70, 20, "Random"));
/* 189 */     this.rename = new GuiButton(6, this.width / 2 + 4, this.height - 24, 70, 20, "Edit");
/* 190 */     this.buttonList.add(this.rename);
/* 191 */     this.buttonList.add(new GuiButton(7, this.width / 2 - 154, this.height - 24, 70, 20, "Last Alt"));
/* 192 */     this.login.enabled = false;
/* 193 */     this.remove.enabled = false;
/*     */   }
/*     */   
/*     */   private boolean isAltInArea(int y) {
/* 197 */     return (y - this.offset <= this.height - 50);
/*     */   }
/*     */   
/*     */   private boolean isMouseOverAlt(int x, int y, int y1) {
/* 201 */     return (x >= 52 && y >= y1 - 4 && x <= this.width - 52 && y <= y1 + 20 && y >= 33 && x <= this.width && y <= this.height - 50);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int par1, int par2, int par3) {
/* 206 */     if (this.offset < 0) {
/* 207 */       this.offset = 0;
/*     */     }
/* 209 */     int y = 38 - this.offset;
/* 210 */     for (Alt alt : AltManager.getAlts()) {
/* 211 */       if (isMouseOverAlt(par1, par2, y)) {
/* 212 */         if (alt == this.selectedAlt) {
/* 213 */           actionPerformed(this.buttonList.get(1));
/*     */           return;
/*     */         } 
/* 216 */         this.selectedAlt = alt;
/*     */       } 
/* 218 */       y += 26;
/*     */     } 
/*     */     try {
/* 221 */       super.mouseClicked(par1, par2, par3);
/* 222 */     } catch (IOException e) {
/* 223 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void prepareScissorBox(float x, float y, float x2, float y2) {
/* 228 */     int factor = (new ScaledResolution(mc)).getScaleFactor();
/* 229 */     GL11.glScissor((int)(x * factor), (int)(((new ScaledResolution(mc)).getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\GuiAltManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */