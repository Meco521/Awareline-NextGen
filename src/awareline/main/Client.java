/*     */ package awareline.main;
/*     */ 
/*     */ import awareline.antileak.JumpMainMenuCheck;
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.antileak.web.HttpUtil;
/*     */ import awareline.main.cmd.CommandManager;
/*     */ import awareline.main.component.impl.BadPacketsComponent;
/*     */ import awareline.main.component.impl.BlinkComponent;
/*     */ import awareline.main.component.impl.ParticleDistanceComponent;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.antibots.BotManager;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.RotationUtils;
/*     */ import awareline.main.mod.implement.misc.Spammer;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.NameProtect;
/*     */ import awareline.main.mod.implement.visual.sucks.info.CombatManager;
/*     */ import awareline.main.mod.manager.ConfigManager;
/*     */ import awareline.main.mod.manager.FileManager;
/*     */ import awareline.main.mod.manager.FriendManager;
/*     */ import awareline.main.mod.manager.ModuleManager;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.api.StreamTexture;
/*     */ import awareline.main.ui.draghud.Draggable;
/*     */ import awareline.main.ui.draghud.component.DraggableComponent;
/*     */ import awareline.main.ui.font.FontLoaders;
/*     */ import awareline.main.ui.font.fontmanager.FontManager;
/*     */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientMainMenu;
/*     */ import awareline.main.ui.gui.hud.tabgui.SideTabGui;
/*     */ import java.awt.Color;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Client
/*     */ {
/*  48 */   public static final Client instance = new Client();
/*     */   
/*  50 */   public final String clientName = "Awareline";
/*     */ 
/*     */   
/*  53 */   protected final Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*  55 */   private final String CLIENT_FILE_PATH = this.mc.mcDataDir.getAbsolutePath() + "/" + "Awareline" + "/";
/*  56 */   private final File clientDirectory = new File(this.mc.mcDataDir, "Awareline/"); public File getClientDirectory() { return this.clientDirectory; }
/*     */   
/*  58 */   public String hudName = "Awareline";
/*  59 */   public final String lastName = this.hudName;
/*     */   
/*  61 */   public String prefix = "."; public SideTabGui sideTabGui; public RotationUtils rotationUtils; public Draggable draggable; public FontLoaders FontLoaders; public StreamTexture customBackground;
/*  62 */   public final String lastPrefix = this.prefix;
/*     */   public BadPacketsComponent badPacketsComponent;
/*     */   public BlinkComponent blinkComponent;
/*     */   public ParticleDistanceComponent particleDistanceComponent;
/*     */   public FontManager FontManager;
/*     */   
/*     */   public StreamTexture getCustomBackground() {
/*  69 */     return this.customBackground;
/*     */   }
/*     */   public FriendManager friendManager; public BotManager botManager; public CombatManager combatManager; public ConfigManager configManager; private CommandManager commandmanager;
/*     */   private EventManager eventManager;
/*     */   private ModuleManager modulemanager;
/*     */   private ScaledResolution scaledResolution;
/*     */   private boolean pretend;
/*     */   
/*     */   public BotManager getBotManager() {
/*  78 */     return this.botManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventManager getEventManager() {
/*  83 */     return this.eventManager;
/*     */   }
/*     */   public ScaledResolution getScaledResolution() {
/*  86 */     return this.scaledResolution;
/*     */   }
/*     */   public boolean isPretend() {
/*  89 */     return this.pretend;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  97 */     this.mc.drawSplashScreen(45);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.mc.drawSplashScreen(55);
/*     */     
/* 133 */     this.FontLoaders = new FontLoaders();
/* 134 */     this.mc.drawSplashScreen(60);
/*     */     
/* 136 */     this.mc.gameSettings.guiScale = 2;
/*     */     
/* 138 */     this.scaledResolution = new ScaledResolution(this.mc);
/*     */     
/* 140 */     this.mc.gameSettings.ofSmartAnimations = true;
/* 141 */     this.mc.gameSettings.ofSmoothFps = false;
/* 142 */     if (blurEnabled()) {
/* 143 */       this.mc.gameSettings.ofFastRender = false;
/*     */     }
/* 145 */     (new JumpMainMenuCheck()).init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean blurEnabled() {
/* 153 */     return (HUD.getInstance != null && ((Boolean)HUD.getInstance.blur.get()).booleanValue() && Config.isFastRender());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initAllManager() {
/*     */     try {
/* 165 */       this.eventManager = new EventManager();
/* 166 */       this.commandmanager = new CommandManager();
/* 167 */       this.commandmanager.init();
/* 168 */       this.friendManager = new FriendManager();
/* 169 */       this.friendManager.init();
/* 170 */       this.modulemanager = new ModuleManager();
/* 171 */       this.modulemanager.init();
/* 172 */     } catch (RuntimeException e) {
/* 173 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initTabGui() {
/* 182 */     this.sideTabGui = new SideTabGui();
/* 183 */     EventManager.register(new Object[] { this.sideTabGui, this });
/* 184 */     this.sideTabGui.init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registUtils() {
/* 193 */     EventManager.register(new Object[] { this.rotationUtils });
/* 194 */     EventManager.register(new Object[] { this.botManager });
/* 195 */     EventManager.register(new Object[] { this.badPacketsComponent });
/* 196 */     EventManager.register(new Object[] { this.blinkComponent });
/* 197 */     EventManager.register(new Object[] { this.particleDistanceComponent });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCustomBackground() {
/* 205 */     File file = new File(this.CLIENT_FILE_PATH + "background.png");
/*     */     
/* 207 */     if (file.exists() && file.isFile()) {
/* 208 */       System.err.println("[Background] Load custom background " + file.getAbsolutePath());
/*     */       
/*     */       try {
/* 211 */         this.customBackground = new StreamTexture(file.toURI().toURL().openStream(), "custom_background");
/* 212 */       } catch (Exception e) {
/* 213 */         System.err.println("Load custom background is null or bad?, please check ./minecraft/awareline/ package");
/* 214 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ModuleManager getModuleManager() {
/* 220 */     return this.modulemanager;
/*     */   }
/*     */   
/*     */   public CommandManager getCommandManager() {
/* 224 */     return this.commandmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveConfigForMods() {
/* 231 */     saveConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveConfig() {
/* 241 */     StringBuilder values = new StringBuilder();
/* 242 */     for (Module m : this.modulemanager.getModules()) {
/* 243 */       for (Value v : m.getValues()) {
/* 244 */         values.append(String.format("%s:%s:%s%s", new Object[] { m.getName(), v.getName(), v.getValue(), System.lineSeparator() }));
/*     */       } 
/*     */     } 
/* 247 */     FileManager.save("Values.txt", values.toString(), false);
/*     */     
/* 249 */     StringBuilder enabled = new StringBuilder();
/* 250 */     for (Module m : this.modulemanager.getModules()) {
/* 251 */       if (!m.isEnabled()) {
/*     */         continue;
/*     */       }
/* 254 */       enabled.append(String.format("%s%s", new Object[] { m.getName(), System.lineSeparator() }));
/*     */     } 
/* 256 */     FileManager.save("Enabled.txt", enabled.toString(), false);
/*     */     
/* 258 */     (new File(FileManager.dir + "/CustomName.txt")).delete();
/* 259 */     StringBuilder name = new StringBuilder();
/* 260 */     for (Module m2 : this.modulemanager.getModules()) {
/* 261 */       if (m2.getCustomName() == null)
/* 262 */         continue;  name.append(String.format("%s:%s%s", new Object[] { m2.getName(), m2.getCustomName(), System.lineSeparator() }));
/*     */     } 
/* 264 */     FileManager.save("CustomName.txt", name.toString(), false);
/*     */     
/* 266 */     String bind = "";
/*     */     
/* 268 */     for (Iterator<Module> iterator = this.modulemanager.getModules().iterator(); iterator.hasNext(); bind = bind + String.format("%s:%s%s", new Object[] { m
/* 269 */           .getName(), Keyboard.getKeyName(m.getKey()), System.lineSeparator() })) {
/* 270 */       Module m = iterator.next();
/*     */     }
/*     */     
/* 273 */     FileManager.save("Binds.txt", bind, false);
/*     */     
/* 275 */     String spammerMessage = (Spammer.bindmessage != null) ? ("Version<1.0.0>" + Spammer.bindmessage) : "";
/* 276 */     FileManager.save("Spammer.txt", spammerMessage, false);
/*     */     
/* 278 */     String nameProtected = (NameProtect.getInstance.name != null) ? ("Version<1.0.0>" + NameProtect.getInstance.name) : "Version<1.0.0>User";
/* 279 */     FileManager.save("Name.txt", nameProtected, false);
/*     */     
/* 281 */     StringBuilder Hiddens = new StringBuilder();
/* 282 */     for (Module m2 : this.modulemanager.getModules()) {
/* 283 */       if (m2.wasHide()) {
/* 284 */         Hiddens.append(m2.getName()).append(System.lineSeparator());
/*     */       }
/*     */     } 
/* 287 */     FileManager.save("Hidden.txt", Hiddens.toString(), false);
/*     */     
/* 289 */     String shadercfg = ClientMainMenu.useShader ? "true" : "false";
/* 290 */     FileManager.save("Shader.txt", shadercfg, false);
/* 291 */     String drag = "";
/* 292 */     for (DraggableComponent dragging : this.draggable.getComponents()) {
/* 293 */       drag = String.valueOf(drag) + String.format("%s:%s:%s%s", new Object[] { dragging.getName(), Float.valueOf(dragging.getX()), Float.valueOf(dragging.getY()), System.lineSeparator() });
/*     */     } 
/* 295 */     FileManager.save("Drag.cfg", drag, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBasicModuleConfig() {
/* 302 */     List<String> shadercfg = FileManager.read("Shader.txt");
/* 303 */     for (String shaders : shadercfg) {
/* 304 */       ClientMainMenu.useShader = shaders.toLowerCase().contains("true");
/*     */     }
/* 306 */     List<String> sMessage = FileManager.read("Spammer.txt");
/* 307 */     for (String b : sMessage) {
/* 308 */       if (!b.contains("Version<Version>")) {
/*     */         return;
/*     */       }
/* 311 */       Spammer.bindmessage = b.substring(21);
/*     */     } 
/* 313 */     List<String> name = FileManager.read("Name.txt");
/* 314 */     for (String b : name) {
/* 315 */       if (!b.contains("Version<Version>")) {
/*     */         return;
/*     */       }
/* 318 */       NameProtect.getInstance.name = b.substring(21);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void shutdown() {
/* 326 */     this.mc.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void msg(String title, String msg) {
/* 333 */     JOptionPane.showInputDialog(null, title, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tryRequest() {
/*     */     try {
/* 341 */       if (!getRequest(VerifyData.instance.strGetHWIDURL).contains(VerifyData.instance.hwidUtils.getHWID(230801))) {
/* 342 */         JOptionPane.showMessageDialog(null, "HWID error ping type", "Warning", 2);
/* 343 */         shutdown();
/*     */       } 
/* 345 */     } catch (RuntimeException e) {
/* 346 */       e.printStackTrace();
/* 347 */       shutdown();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getRequest(String url) {
/* 356 */     if (url.isEmpty()) {
/* 357 */       return StringUtils.randomStringDefault(43);
/*     */     }
/*     */     try {
/* 360 */       return HttpUtil.performGetRequest(new URL(url));
/* 361 */     } catch (Exception e) {
/* 362 */       e.printStackTrace();
/* 363 */       shutdown();
/*     */ 
/*     */       
/* 366 */       return StringUtils.randomStringDefault(34);
/*     */     } 
/*     */   }
/*     */   public int getBlueColor() {
/* 370 */     return (new Color(47, 154, 241)).getRGB();
/*     */   }
/*     */   
/*     */   public Color getBlueColor(int alpha) {
/* 374 */     return new Color(47, 154, 241, alpha);
/*     */   }
/*     */   
/*     */   public Color getClientColorRGB(int Alpha) {
/* 378 */     Color color = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), Alpha);
/* 379 */     return ((Boolean)HUD.rainbow.get()).booleanValue() ? HUD.getInstance.rainbowToEffect() : color;
/*     */   }
/*     */   
/*     */   public Color getClientColorNoRainbowRGB(int Alpha) {
/* 383 */     return new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), Alpha);
/*     */   }
/*     */   
/*     */   public int getClientColor(int Alpha) {
/* 387 */     Color color = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), Alpha);
/* 388 */     return ((Boolean)HUD.rainbow.get()).booleanValue() ? HUD.getInstance.rainbowToEffect().getRGB() : color.getRGB();
/*     */   }
/*     */   
/*     */   public Color getClientColorNoHash(int Alpha) {
/* 392 */     Color color = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), Alpha);
/* 393 */     return ((Boolean)HUD.rainbow.get()).booleanValue() ? HUD.getInstance.rainbowToEffect() : color;
/*     */   }
/*     */   
/*     */   public int getClientColorNoRainbow(int Alpha) {
/* 397 */     Color color = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), Alpha);
/* 398 */     return color.getRGB();
/*     */   }
/*     */   
/*     */   public Color getClientColorAlphaNoHash(int Alpha) {
/* 402 */     Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 70, 25);
/* 403 */     return ((Boolean)HUD.dynamicColor.get()).booleanValue() ? (((Boolean)HUD.rainbow.get()).booleanValue() ? getClientColorNoHash(Alpha) : Ranbow) : getClientColorNoHash(Alpha);
/*     */   }
/*     */   
/*     */   public int getClientColorAlphaNoRainbow(int Alpha) {
/* 407 */     Color color = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), Alpha);
/* 408 */     Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 70, 25);
/* 409 */     return ((Boolean)HUD.dynamicColor.get()).booleanValue() ? Ranbow.getRGB() : color.getRGB();
/*     */   }
/*     */   
/*     */   public int getClientColorAlpha(int Alpha) {
/* 413 */     Color color = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), Alpha);
/* 414 */     Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 70, 25);
/* 415 */     return ((Boolean)HUD.dynamicColor.get()).booleanValue() ? (
/* 416 */       ((Boolean)HUD.rainbow.get()).booleanValue() ? HUD.getInstance.rainbowToEffect().getRGB() : Ranbow.getRGB()) : (
/* 417 */       ((Boolean)HUD.rainbow.get()).booleanValue() ? HUD.getInstance.rainbowToEffect().getRGB() : color.getRGB());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\Client.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */