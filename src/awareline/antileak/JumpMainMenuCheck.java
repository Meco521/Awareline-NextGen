/*     */ package awareline.antileak;
/*     */ 
/*     */ import awareline.main.component.impl.BadPacketsComponent;
/*     */ import awareline.main.component.impl.BlinkComponent;
/*     */ import awareline.main.component.impl.ParticleDistanceComponent;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.antibots.BotManager;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.RotationUtils;
/*     */ import awareline.main.mod.implement.visual.sucks.info.CombatManager;
/*     */ import awareline.main.mod.manager.ConfigManager;
/*     */ import awareline.main.mod.manager.FileManager;
/*     */ import awareline.main.ui.draghud.Draggable;
/*     */ import awareline.main.ui.font.fontmanager.FontManager;
/*     */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientSetting;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import com.github.creeper123123321.viafabric.ViaFabric;
/*     */ import com.me.guichaguri.betterfps.BetterFpsClient;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("maximum")
/*     */ @StringEncryption("enable")
/*     */ @StringEncryptionType("fast")
/*     */ public class JumpMainMenuCheck
/*     */   implements VerifyInstance
/*     */ {
/*     */   public boolean clientIsLeakingOrInCracked;
/*     */   
/*     */   public final void init() {
/*     */     try {
/*  46 */       jumpToClientMainMenu();
/*     */       return;
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     } 
/*     */   }
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
/*     */   public final void jumpToClientMainMenu() throws IOException {
/*  65 */     AntiDump.ENABLE = false;
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
/*     */     try {
/*  94 */       FileManager.init();
/*     */       
/*  96 */       instance.FontManager = new FontManager();
/*  97 */       instance.FontManager.init();
/*     */       
/*  99 */       instance.draggable = new Draggable();
/*     */       
/* 101 */       instance.initAllManager();
/* 102 */       instance.getEventManager().init();
/*     */       
/* 104 */       instance.initTabGui();
/*     */       
/* 106 */       instance.badPacketsComponent = new BadPacketsComponent();
/* 107 */       instance.blinkComponent = new BlinkComponent();
/* 108 */       instance.particleDistanceComponent = new ParticleDistanceComponent();
/*     */       
/* 110 */       instance.combatManager = new CombatManager();
/* 111 */       instance.rotationUtils = new RotationUtils();
/* 112 */       instance.botManager = new BotManager();
/* 113 */       instance.configManager = new ConfigManager();
/* 114 */       instance.registUtils();
/*     */       
/* 116 */       instance.loadCustomBackground();
/*     */       
/* 118 */       if (((Boolean)ClientSetting.betterFPS.get()).booleanValue()) {
/* 119 */         BetterFpsClient.start(mc);
/*     */       }
/*     */       
/* 122 */       instance.setBasicModuleConfig();
/*     */       
/* 124 */       (new ViaFabric()).onInitialize();
/* 125 */       Runtime.getRuntime().gc();
/*     */       
/* 127 */       VerifyData.instance.getClass();
/* 128 */       Display.setTitle("Awareline NextGenF");
/*     */ 
/*     */     
/*     */     }
/* 132 */     catch (RuntimeException e) {
/* 133 */       e.printStackTrace();
/*     */     } 
/*     */   }
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
/*     */   private void window(String title, String msg) {
/* 151 */     JOptionPane.showInputDialog(null, title, msg);
/*     */   }
/*     */ 
/*     */   
/*     */   private void shutdown() {
/* 156 */     VerifyData.instance.UserName = StringUtils.randomString("-124-12-4-124-12-412-4-12411920748912759-812u5akjsbfiuawghrfiuawhiufhawiufhawiuhfaiwuhfiuawhfawfaw2", 40 + (int)MathUtil.getRandomInRange(-5.0F, 5.0F));
/* 157 */     this.clientIsLeakingOrInCracked = true;
/* 158 */     mc.shutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(String sendURL, int id) throws IOException {
/* 163 */     if (id != 230801) {
/* 164 */       mc.shutdown();
/* 165 */       return StringUtils.randomStringHeavy();
/*     */     } 
/* 167 */     URL url = new URL(sendURL);
/* 168 */     if (url.toString().isEmpty()) {
/* 169 */       shutdown();
/* 170 */       return StringUtils.randomStringDefault((int)MathUtil.getRandomInRange(10.0F, 20.0F));
/*     */     } 
/* 172 */     Validate.notNull(url);
/* 173 */     HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
/* 174 */     httpurlconnection.setRequestMethod("GET");
/* 175 */     BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 176 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*     */     String s;
/* 179 */     while ((s = bufferedreader.readLine()) != null) {
/* 180 */       stringbuilder.append(s);
/* 181 */       stringbuilder.append('\r');
/*     */     } 
/*     */     
/* 184 */     bufferedreader.close();
/* 185 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\JumpMainMenuCheck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */