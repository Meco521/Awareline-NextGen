/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.globals.Shader;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.guimainmenu.ColorCreator;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class SessionInfo extends Module {
/*     */   public static SessionInfo getInstance;
/*  25 */   public final Option<Boolean> blur = new Option("Blur", 
/*  26 */       Boolean.valueOf(true));
/*  27 */   public final Option<Boolean> shadow = new Option("Shadow", Boolean.valueOf(true));
/*  28 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Line", "Round", "Current", "Fence" }, "Line");
/*  29 */   private final CFontRenderer icon = Client.instance.FontLoaders.sessionInfo16;
/*  30 */   private final CFontRenderer font = Client.instance.FontLoaders.regular15;
/*     */   private float x;
/*     */   
/*     */   public SessionInfo() {
/*  34 */     super("SessionInfo", ModuleType.Render);
/*  35 */     addSettings(new Value[] { (Value)this.mode, (Value)this.blur, (Value)this.shadow });
/*  36 */     getInstance = this;
/*     */   }
/*     */   private float y;
/*     */   
/*     */   public void onDisable() {
/*  41 */     this.x = this.y = 0.0F;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void bloom(EventShader event) {
/*  46 */     if (mc.gameSettings.showDebugInfo) {
/*     */       return;
/*     */     }
/*     */     
/*  50 */     if (!Shader.getInstance.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  54 */     if (event.onBloom() && !((Boolean)this.shadow.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     if (event.onBlur() && !((Boolean)this.blur.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  64 */     awareline.main.ui.draghud.component.impl.SessionInfo dwm = (awareline.main.ui.draghud.component.impl.SessionInfo)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.SessionInfo.class);
/*     */     
/*  66 */     dwm.setWidth(142.0F);
/*  67 */     dwm.setHeight(64);
/*  68 */     this.x = AnimationUtil.moveUDFaster(this.x, dwm.getX());
/*  69 */     this.y = AnimationUtil.moveUDFaster(this.y, dwm.getY());
/*     */     
/*  71 */     if (this.mode.is("Fence")) {
/*     */ 
/*     */       
/*  74 */       String time = Client.instance.combatManager.getHour() + "h " + Client.instance.combatManager.getMinute() + "m " + Client.instance.combatManager.getSecond() + "s";
/*     */       
/*  76 */       RoundedUtil.drawRound(this.x, this.y, 96.0F + time.length() / 2.0F, 58.0F, 5.0F, Client.instance
/*  77 */           .getClientColorNoHash(255));
/*     */     }
/*  79 */     else if (this.mode.is("Line")) {
/*  80 */       GL11.glPushMatrix();
/*  81 */       if (!((Boolean)HUD.rainbow.get()).booleanValue()) {
/*  82 */         if (((Boolean)HUD.dynamicColor.get()).booleanValue()) {
/*  83 */           Color Ranbow2 = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 160);
/*  84 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 80);
/*  85 */           RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, Ranbow
/*  86 */               .getRGB(), Ranbow2.getRGB());
/*  87 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */         } else {
/*  89 */           RenderUtil.drawRect(this.x, -1.0F + this.y, 95.0F + this.x + 60.0F, this.y, 
/*  90 */               hudColor());
/*     */         } 
/*     */       } else {
/*  93 */         RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, HUD.getInstance
/*  94 */             .rainbow(), ColorCreator.createRainbowFromOffset(-6000, 5));
/*  95 */         RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */       } 
/*     */       
/*  98 */       RenderUtil.drawRect(this.x, this.y, 95.0F + this.x + 60.0F, 60.0F + this.y + 16.0F, Client.instance.getClientColor(255));
/*     */       
/* 100 */       GL11.glPopMatrix();
/* 101 */     } else if (this.mode.is("Current")) {
/* 102 */       GL11.glPushMatrix();
/*     */       
/* 104 */       if (!((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 105 */         if (((Boolean)HUD.dynamicColor.get()).booleanValue()) {
/* 106 */           Color Ranbow2 = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 160);
/* 107 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 80);
/* 108 */           RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, Ranbow
/* 109 */               .getRGB(), Ranbow2.getRGB());
/* 110 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */         } else {
/* 112 */           RenderUtil.drawRect(this.x, -1.0F + this.y, 95.0F + this.x + 60.0F, this.y, 
/* 113 */               hudColor());
/*     */         } 
/*     */       } else {
/* 116 */         RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, HUD.getInstance
/* 117 */             .rainbow(), ColorCreator.createRainbowFromOffset(-6000, 5));
/* 118 */         RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */       } 
/*     */       
/* 121 */       RenderUtil.drawRect(this.x, this.y, 95.0F + this.x + 60.0F, 60.0F + this.y + 16.0F, Client.instance
/* 122 */           .getClientColor(255));
/*     */       
/* 124 */       GL11.glPopMatrix();
/* 125 */     } else if (this.mode.is("Round")) {
/* 126 */       GL11.glPushMatrix();
/*     */       
/* 128 */       RoundedUtil.drawRound(this.x, this.y - 2.0F, 115.0F, 69.0F, 3.0F, Client.instance
/* 129 */           .getClientColorNoHash(255));
/*     */       
/* 131 */       if (!((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 132 */         if (((Boolean)HUD.dynamicColor.get()).booleanValue()) {
/* 133 */           Color Ranbow2 = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 140);
/* 134 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 80);
/* 135 */           RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y + 14.0F), (55.0F + this.x + 60.0F), (this.y + 14.0F), Ranbow
/* 136 */               .getRGB(), Ranbow2.getRGB());
/*     */         } else {
/* 138 */           RenderUtil.drawRect(this.x, -1.0F + this.y + 14.0F, 55.0F + this.x + 60.0F, this.y + 14.0F, 
/* 139 */               hudColor());
/*     */         } 
/*     */       } else {
/* 142 */         RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y + 14.0F), (55.0F + this.x + 60.0F), (this.y + 14.0F), HUD.getInstance
/* 143 */             .rainbow(), ColorCreator.createRainbowFromOffset(-6000, 5));
/*     */       } 
/* 145 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */ 
/*     */       
/* 148 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRenderGui(EventRender2D event) {
/* 155 */     if (mc.gameSettings.showDebugInfo) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 160 */     awareline.main.ui.draghud.component.impl.SessionInfo dwm = (awareline.main.ui.draghud.component.impl.SessionInfo)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.SessionInfo.class);
/* 161 */     dwm.setWidth(142.0F);
/* 162 */     dwm.setHeight(64);
/* 163 */     this.x = AnimationUtil.moveUDFaster(this.x, dwm.getX());
/* 164 */     this.y = AnimationUtil.moveUDFaster(this.y, dwm.getY());
/* 165 */     if (System.currentTimeMillis() - Client.instance.combatManager.getStartTime() > 1000L) {
/* 166 */       Client.instance.combatManager.setSecond(Client.instance.combatManager.getSecond() + 1);
/* 167 */       Client.instance.combatManager.setStartTime(System.currentTimeMillis());
/*     */     } 
/* 169 */     if (Client.instance.combatManager.getSecond() > 59) {
/* 170 */       Client.instance.combatManager.setSecond(0);
/* 171 */       Client.instance.combatManager.setMinute(Client.instance.combatManager.getMinute() + 1);
/*     */     } 
/* 173 */     if (Client.instance.combatManager.getMinute() > 59) {
/* 174 */       Client.instance.combatManager.setMinute(0);
/* 175 */       Client.instance.combatManager.setHour(Client.instance.combatManager.getHour() + 1);
/*     */     } 
/* 177 */     if (this.mode.is("Fence")) {
/* 178 */       double xDist = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
/* 179 */       double zDist = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
/* 180 */       double lastDist = Math.sqrt(Math.pow(xDist, 2.0D) + Math.pow(zDist, 2.0D));
/*     */       
/* 182 */       String time = Client.instance.combatManager.getHour() + "h " + Client.instance.combatManager.getMinute() + "m " + Client.instance.combatManager.getSecond() + "s";
/*     */       
/* 184 */       RoundedUtil.drawRound(this.x, this.y, 96.0F + time.length() / 2.0F, 58.0F, 5.0F, new Color(0, 0, 0, 150));
/*     */       
/* 186 */       this.icon.drawString("B", 6.0F + this.x, 5.0F + this.y, -1);
/* 187 */       RenderUtil.startGlScissor((int)this.x, (int)this.y, 96 + time.length() / 2, 58);
/* 188 */       Client.instance.FontLoaders.regular15.drawString("Session Info", 17.0F + this.x, 4.0F + this.y, -1);
/*     */       
/* 190 */       Client.instance.FontLoaders.regular13.drawString("Time Played " + time, 16.0F + this.x, 19.5F + this.y, -1);
/*     */ 
/*     */       
/* 193 */       Client.instance.FontLoaders.regular13.drawString("Speed " + String.format("%.2f bps", new Object[] { Double.valueOf(lastDist * 20.0D * mc.timer.timerSpeed) }), 16.0F + this.x, 29.5F + this.y, -1);
/*     */       
/* 195 */       Client.instance.FontLoaders.regular13.drawString("Winner / Total " + Client.instance.combatManager.getWin() + "/" + Client.instance.combatManager.getTotalPlayed(), 16.0F + this.x, 39.5F + this.y, -1);
/*     */       
/* 197 */       Client.instance.FontLoaders.regular13.drawString("Kills " + KillAura.getInstance.killed, 16.0F + this.x, 49.5F + this.y, -1);
/*     */       
/* 199 */       RenderUtil.stopGlScissor();
/* 200 */       this.icon.drawString("A", 7.5F + this.x - 1.0F, 20.0F + this.y, -1);
/* 201 */       this.icon.drawString("F", 6.0F + this.x - 1.0F, 30.0F + this.y, -1);
/* 202 */       this.icon.drawString("D", 6.5F + this.x - 1.0F, 40.5F + this.y, -1);
/* 203 */       this.icon.drawString("C", 5.0F + this.x - 1.0F, 50.5F + this.y, -1);
/* 204 */     } else if (this.mode.is("Line")) {
/* 205 */       GL11.glPushMatrix();
/* 206 */       double xDist = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
/* 207 */       double zDist = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
/* 208 */       double lastDist = Math.sqrt(Math.pow(xDist, 2.0D) + Math.pow(zDist, 2.0D));
/* 209 */       float leftX = -2.0F;
/* 210 */       if (!((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 211 */         if (((Boolean)HUD.dynamicColor.get()).booleanValue()) {
/* 212 */           Color Ranbow2 = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 160);
/* 213 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 80);
/* 214 */           RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, Ranbow
/* 215 */               .getRGB(), Ranbow2.getRGB());
/* 216 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */         } else {
/* 218 */           RenderUtil.drawRect(this.x, -1.0F + this.y, 95.0F + this.x + 60.0F, this.y, 
/* 219 */               hudColor());
/*     */         } 
/*     */       } else {
/* 222 */         RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, HUD.getInstance
/* 223 */             .rainbow(), ColorCreator.createRainbowFromOffset(-6000, 5));
/* 224 */         RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       RenderUtil.drawRect(this.x, this.y, 95.0F + this.x + 60.0F, 60.0F + this.y + 16.0F, (new Color(0, 0, 0, 180))
/* 233 */           .getRGB());
/* 234 */       if (((Boolean)HUD.dynamicColor.get()).booleanValue() || ((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 235 */         char[] charArray = "Session".toCharArray();
/* 236 */         int length = 0;
/* 237 */         for (char charIndex : charArray) {
/* 238 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, length + 80);
/* 239 */           Client.instance.FontLoaders.regular18.drawString(String.valueOf(charIndex), (18 + length) + this.x - 15.0F, 3.5F + this.y, 
/*     */               
/* 241 */               ((Boolean)HUD.rainbow.get()).booleanValue() ? HUD.getInstance.rainbow() : Ranbow.getRGB());
/* 242 */           length += Client.instance.FontLoaders.regular18.getCharWidth(charIndex);
/*     */         } 
/*     */       } else {
/* 245 */         Client.instance.FontLoaders.regular18.drawString("Session ", 18.0F + this.x - 15.0F, 3.5F + this.y, 
/*     */             
/* 247 */             hudColor());
/*     */       } 
/* 249 */       Client.instance.FontLoaders.regular18.drawString("Info", 18.0F + this.x - 15.0F + Client.instance.FontLoaders.regular16
/* 250 */           .getStringWidth("Session "), 3.5F + this.y, -1);
/*     */ 
/*     */       
/* 253 */       Client.instance.FontLoaders.sessionInfo22.drawString("A", 7.5F + this.x + -2.0F, 20.0F + this.y - 1.0F, hudColor());
/* 254 */       Client.instance.FontLoaders.sessionInfo20.drawString("F", 6.0F + this.x + -2.0F - 0.5F, 30.0F + this.y + 5.0F - 1.0F, hudColor());
/* 255 */       Client.instance.FontLoaders.sessionInfo22.drawString("D", 6.5F + this.x + -2.0F - 1.0F, 40.0F + this.y + 10.0F - 1.0F, hudColor());
/* 256 */       Client.instance.FontLoaders.sessionInfo19.drawString("C", 5.0F + this.x + -2.0F - 0.0F, 50.0F + this.y + 15.0F - 1.0F, hudColor());
/*     */       
/* 258 */       this.font.drawString("Time Played: " + Client.instance.combatManager.getHour() + "h " + Client.instance.combatManager.getMinute() + "m " + Client.instance.combatManager.getSecond() + "s", 16.0F + this.x, 19.0F + this.y, -1);
/*     */ 
/*     */       
/* 261 */       this.font.drawString("Speed: " + String.format("%.2f bps", new Object[] { Double.valueOf(lastDist * 20.0D * mc.timer.timerSpeed) }), 16.0F + this.x, 29.0F + this.y + 5.0F, -1);
/*     */ 
/*     */       
/* 264 */       this.font.drawString("Winner / Total: " + Client.instance.combatManager.getWin() + "/" + Client.instance.combatManager.getTotalPlayed(), 16.0F + this.x, 39.0F + this.y + 10.0F, -1);
/*     */ 
/*     */       
/* 267 */       this.font.drawString("Kills: " + KillAura.getInstance.killed, 16.0F + this.x, 49.0F + this.y + 15.0F, -1);
/*     */ 
/*     */       
/* 270 */       GL11.glPopMatrix();
/* 271 */     } else if (this.mode.is("Current")) {
/* 272 */       GL11.glPushMatrix();
/*     */       
/* 274 */       if (!((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 275 */         if (((Boolean)HUD.dynamicColor.get()).booleanValue()) {
/* 276 */           Color Ranbow2 = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 160);
/* 277 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 80);
/* 278 */           RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, Ranbow
/* 279 */               .getRGB(), Ranbow2.getRGB());
/* 280 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */         } else {
/* 282 */           RenderUtil.drawRect(this.x, -1.0F + this.y, 95.0F + this.x + 60.0F, this.y, 
/* 283 */               hudColor());
/*     */         } 
/*     */       } else {
/* 286 */         RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y), (95.0F + this.x + 60.0F), this.y, HUD.getInstance
/* 287 */             .rainbow(), ColorCreator.createRainbowFromOffset(-6000, 5));
/* 288 */         RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       RenderUtil.drawRect(this.x, this.y, 95.0F + this.x + 60.0F, 60.0F + this.y + 16.0F, (new Color(0, 0, 0, 150))
/* 297 */           .getRGB());
/* 298 */       if (((Boolean)HUD.dynamicColor.get()).booleanValue() || ((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 299 */         char[] charArray = "Session Info".toCharArray();
/* 300 */         int length = 0;
/* 301 */         for (char charIndex : charArray) {
/* 302 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, length + 80);
/* 303 */           Client.instance.FontLoaders.regular18.drawString(String.valueOf(charIndex), (18 + length) + this.x - 15.0F, 3.5F + this.y, 
/*     */               
/* 305 */               ((Boolean)HUD.rainbow.get()).booleanValue() ? HUD.getInstance.rainbow() : Ranbow.getRGB());
/* 306 */           length += Client.instance.FontLoaders.regular18.getCharWidth(charIndex);
/*     */         } 
/*     */       } else {
/* 309 */         Client.instance.FontLoaders.regular18.drawString("Session Info", 18.0F + this.x - 15.0F, 3.5F + this.y, 
/*     */             
/* 311 */             hudColor());
/*     */       } 
/* 313 */       this.font.drawString("Playing Time " + Client.instance.combatManager.getHour() + "h " + Client.instance.combatManager
/* 314 */           .getMinute() + "m " + Client.instance.combatManager.getSecond() + "s", 5.0F + this.x, 19.0F + this.y, -1);
/*     */       
/* 316 */       this.font.drawString("Hurt Time " + mc.thePlayer.hurtResistantTime, 5.0F + this.x, 29.0F + this.y + 5.0F, -1);
/*     */       
/* 318 */       this.font.drawString("Winner / Total " + Client.instance.combatManager.getWin() + "/" + Client.instance.combatManager.getTotalPlayed(), 5.0F + this.x, 39.0F + this.y + 10.0F, -1);
/*     */       
/* 320 */       this.font.drawString("Player Killed " + KillAura.getInstance.killed, 5.0F + this.x, 49.0F + this.y + 15.0F, -1);
/*     */       
/* 322 */       GL11.glPopMatrix();
/* 323 */     } else if (this.mode.is("Round")) {
/* 324 */       GL11.glPushMatrix();
/* 325 */       double xDist = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
/* 326 */       double zDist = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
/* 327 */       double lastDist = Math.sqrt(Math.pow(xDist, 2.0D) + Math.pow(zDist, 2.0D));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       RoundedUtil.drawRound(this.x, this.y - 2.0F, 115.0F, 69.0F, 3.0F, new Color(20, 20, 20));
/*     */       
/* 336 */       Client.instance.FontLoaders.guiicons22.drawCenteredString("C", 58.0F + this.x, 2.0F + this.y, -1);
/*     */ 
/*     */       
/* 339 */       this.font.drawString("FPS " + HUD.getInstance.getFPS(), 11.0F + this.x, 15.0F + this.y + 8.0F, -1);
/*     */       
/* 341 */       this.font.drawString("Move " + String.format("%.2f bps", new Object[] { Double.valueOf(lastDist * 20.0D * mc.timer.timerSpeed) }), 11.0F + this.x, 30.0F + this.y + 8.0F, -1);
/*     */       
/* 343 */       this.font.drawString("Kills " + KillAura.getInstance.killed, 11.0F + this.x, 45.0F + this.y + 8.0F, -1);
/*     */       
/* 345 */       if (!((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 346 */         if (((Boolean)HUD.dynamicColor.get()).booleanValue()) {
/* 347 */           Color Ranbow2 = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 140);
/* 348 */           Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, 80);
/* 349 */           RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y + 14.0F), (55.0F + this.x + 60.0F), (this.y + 14.0F), Ranbow
/* 350 */               .getRGB(), Ranbow2.getRGB());
/*     */         } else {
/* 352 */           RenderUtil.drawRect(this.x, -1.0F + this.y + 14.0F, 55.0F + this.x + 60.0F, this.y + 14.0F, 
/* 353 */               hudColor());
/*     */         } 
/*     */       } else {
/* 356 */         RenderUtil.drawGradientSideways(this.x, (-1.0F + this.y + 14.0F), (55.0F + this.x + 60.0F), (this.y + 14.0F), HUD.getInstance
/* 357 */             .rainbow(), ColorCreator.createRainbowFromOffset(-6000, 5));
/*     */       } 
/* 359 */       RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */       
/* 361 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int hudColor() {
/* 367 */     if (!((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 368 */       return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*     */     }
/* 370 */     return HUD.getInstance.rainbow();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\SessionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */