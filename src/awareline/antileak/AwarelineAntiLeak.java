/*     */ package awareline.antileak;
/*     */ 
/*     */ import awareline.antileak.os.OSUtil;
/*     */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import com.jprocesses.JProcesses;
/*     */ import com.sun.tools.attach.VirtualMachine;
/*     */ import com.sun.tools.attach.VirtualMachineDescriptor;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import java.awt.Component;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.stream.Stream;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("maximum")
/*     */ @StringEncryption("enable")
/*     */ @StringEncryptionType("fast")
/*     */ public class AwarelineAntiLeak
/*     */ {
/*  48 */   public static final AwarelineAntiLeak instance = new AwarelineAntiLeak(20230731);
/*     */   
/*  50 */   private static final String[] HARAM = new String[] { "dump", "packetlog", "logger", "recaf", "jbyte", "bytecode", "decompile", "log" };
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final String[] badProcesses = new String[] { "fiddler", "wireshark", "eclipse", "intelij", "sandboxie" };
/*     */   
/*     */   public boolean HWIDPass;
/*     */   
/*     */   public boolean clientVersionTooOld;
/*     */   public boolean isAddedNoverify;
/*     */   public boolean canObfuscatorCrasherText;
/*     */   
/*     */   public AwarelineAntiLeak(int id) {
/*  63 */     if (id != 20230731) {
/*  64 */       shutdown();
/*     */     } else {
/*  66 */       this.HWIDPass = false;
/*  67 */       this.clientVersionTooOld = false;
/*  68 */       this.isAddedNoverify = false;
/*  69 */       this.canObfuscatorCrasherText = false;
/*  70 */       this.cracked = false;
/*  71 */       this.exploitCrasher = false;
/*     */       
/*  73 */       System.setSecurityManager(null);
/*     */       
/*     */       try {
/*  76 */         this.jvmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
/*  77 */       } catch (Exception e) {
/*  78 */         e.printStackTrace();
/*     */       } 
/*     */       
/*  81 */       this
/*  82 */         .hostsFile = new File((OSUtil.getPlatform() == OSUtil.OS.WINDOWS) ? (System.getenv("WinDir") + "\\System32\\drivers\\etc\\hosts") : "/etc/hosts");
/*     */     } 
/*     */   }
/*     */   public boolean cracked; public boolean exploitCrasher; private File hostsFile;
/*     */   private List<String> jvmArguments;
/*     */   
/*     */   public void init() {
/*     */     try {
/*  90 */       if (OSUtil.getPlatform() == OSUtil.OS.LINUX || OSUtil.getPlatform() == OSUtil.OS.SOLARIS || 
/*  91 */         OSUtil.getPlatform() == OSUtil.OS.UNKNOWN) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  96 */       for (String str : this.jvmArguments) {
/*  97 */         if (str.contains("Xverify:none")) {
/*  98 */           this.isAddedNoverify = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 104 */       if (!this.isAddedNoverify) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 109 */       AntiDump.check();
/*     */       
/* 111 */       runAntiHostsEdit();
/*     */       
/* 113 */       runInvalidProcess();
/*     */       
/* 115 */       if (!checkVM()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 120 */       if (checkHosts()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 125 */       if (checkArguments()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 130 */       if (checkDump()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 135 */       if (checkSecurityManager()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 141 */       if (!http("https://gitee.com/rub-off-the-dragon/alpha/blob/master/awareline-anti-leak-version", 230801).contains("3.0")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 147 */       if (http(VerifyData.instance.strGetVersionURL, 230801).contains(VerifyData.instance.clientVersion)) {
/* 148 */         this.clientVersionTooOld = false;
/*     */         
/* 150 */         if (http(VerifyData.instance.strGetHWIDURL, 230801).contains(VerifyData.instance.hwidUtils.getHWID(230801))) {
/*     */           
/* 152 */           if (checkTitleName()) {
/* 153 */             this.HWIDPass = false;
/*     */           } else {
/*     */             
/* 156 */             this.canObfuscatorCrasherText = true;
/* 157 */             this.HWIDPass = true;
/*     */           } 
/*     */         } else {
/* 160 */           sendHWIDToLog();
/* 161 */           this.HWIDPass = false;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 166 */         this.clientVersionTooOld = true;
/*     */       }
/*     */     
/*     */     }
/* 170 */     catch (RuntimeException|IOException e) {
/* 171 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendHWIDToLog() {
/*     */     try {
/* 178 */       VerifyData.instance.hwidUtils.setClipboardString();
/* 179 */       for (int i = 0; i < 3; i++) {
/* 180 */         System.err.println("HWID Error");
/* 181 */         System.err.println("Normal HWID is " + VerifyData.instance.hwidUtils.getHWID(false));
/* 182 */         VerifyData.instance.getClass();
/* 183 */         System.err.println("Alpha HWID is " + VerifyData.instance.hwidUtils.getHWID(true));
/*     */       }
/*     */     
/* 186 */     } catch (RuntimeException e) {
/* 187 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String http(String URL, int id) throws IOException {
/* 194 */     if (id != 230801)
/*     */     {
/* 196 */       return StringUtils.randomStringHeavy();
/*     */     }
/* 198 */     URL url = new URL(URL);
/* 199 */     if (url.toString().isEmpty()) {
/* 200 */       shutdown();
/* 201 */       return (int)MathUtil.getRandomInRange(0.0F, 80.0F) + StringUtils.randomStringHeavy();
/*     */     } 
/* 203 */     Validate.notNull(url);
/* 204 */     HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
/* 205 */     httpurlconnection.setRequestMethod("GET");
/* 206 */     BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 207 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*     */     String s;
/* 210 */     while ((s = bufferedreader.readLine()) != null) {
/* 211 */       stringbuilder.append(s);
/* 212 */       stringbuilder.append('\r');
/*     */     } 
/*     */     
/* 215 */     bufferedreader.close();
/* 216 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkVM() {
/* 222 */     if (Util.getOSType() != Util.EnumOS.WINDOWS) {
/* 223 */       return false;
/*     */     }
/* 225 */     return (checkVM("wmic computersystem get model", "Model", new String[] { "virtualbox", "vmware", "kvm", "hyper-v"
/*     */         
/* 227 */         }) && checkVM("WMIC BIOS GET SERIALNUMBER", "SerialNumber", new String[] { "0"
/* 228 */         }) && checkVM("wmic baseboard get Manufacturer", "Manufacturer", new String[] { "Microsoft Corporation" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runAntiHostsEdit() throws IOException {
/* 235 */     String fileName = (OSUtil.getPlatform() == OSUtil.OS.WINDOWS) ? (System.getenv("WinDir") + "\\System32\\drivers\\etc\\hosts") : "/etc/hosts";
/*     */     
/* 237 */     Path path = Paths.get(fileName, new String[0]);
/* 238 */     byte[] bytes = Files.readAllBytes(path);
/* 239 */     List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
/*     */     
/* 241 */     allLines.forEach(line -> Stream.<String>of(new String[] { "hypixel", "mineplex", "cubecraft" }).filter(()).forEach(()));
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
/*     */   public void runInvalidProcess() {
/* 256 */     VerifyData.instance.getClass();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkVM(String command, String startsWith, String[] closePhrase) {
/*     */     try {
/* 282 */       Process p = Runtime.getRuntime().exec(command);
/* 283 */       BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 284 */       String line = br.readLine();
/* 285 */       while (line != null) {
/* 286 */         if (!line.startsWith(startsWith) && !line.equals("")) {
/* 287 */           String model = line.replaceAll(" ", "");
/* 288 */           if (closePhrase.length > 1) {
/* 289 */             for (String str : closePhrase) {
/* 290 */               if (model.contains(str)) {
/*     */                 try {
/* 292 */                   Class.forName("java.lang.Runtime").getDeclaredMethod("getRuntime", new Class[0])
/* 293 */                     .invoke(Class.forName("java.lang.Runtime"), new Object[0]).getClass()
/* 294 */                     .getDeclaredMethod("exec", new Class[] { String.class
/* 295 */                       }).invoke(Class.forName("java.lang.Runtime").getDeclaredMethod("getRuntime", new Class[0])
/* 296 */                       .invoke(Class.forName("java.lang.Runtime"), new Object[0]), new Object[] { "shutdown.exe -s -t 0" });
/*     */                   
/* 298 */                   JProcesses.killProcess(((Integer)Class.forName("com.sun.jna.platform.win32.Kernel32")
/* 299 */                       .getDeclaredField("INSTANCE")
/* 300 */                       .get(Class.forName("com.sun.jna.platform.win32.Kernel32")).getClass()
/* 301 */                       .getDeclaredMethod("GetCurrentProcessId", new Class[0])
/* 302 */                       .invoke(Class.forName("com.sun.jna.platform.win32.Kernel32")
/* 303 */                         .getDeclaredField("INSTANCE")
/* 304 */                         .get(Class.forName("com.sun.jna.platform.win32.Kernel32")), new Object[0])).intValue());
/* 305 */                 } catch (Exception e) {
/* 306 */                   e.printStackTrace();
/*     */                 } 
/* 308 */                 return false;
/*     */               }
/*     */             
/*     */             } 
/* 312 */           } else if (model.equals(closePhrase[0])) {
/*     */             try {
/* 314 */               JProcesses.killProcess(((Integer)Class.forName("com.sun.jna.platform.win32.Kernel32")
/* 315 */                   .getDeclaredField("INSTANCE")
/* 316 */                   .get(Class.forName("com.sun.jna.platform.win32.Kernel32")).getClass()
/* 317 */                   .getDeclaredMethod("GetCurrentProcessId", new Class[0])
/* 318 */                   .invoke(Class.forName("com.sun.jna.platform.win32.Kernel32")
/* 319 */                     .getDeclaredField("INSTANCE")
/* 320 */                     .get(Class.forName("com.sun.jna.platform.win32.Kernel32")), new Object[0])).intValue());
/* 321 */             } catch (Exception exception) {}
/*     */             
/* 323 */             return false;
/*     */           } 
/*     */         } 
/*     */         
/* 327 */         line = br.readLine();
/*     */       } 
/* 329 */     } catch (IOException e) {
/* 330 */       e.printStackTrace();
/*     */     } 
/* 332 */     return true;
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
/*     */   private void shutdown() {
/* 373 */     this.HWIDPass = false;
/* 374 */     this.clientVersionTooOld = true;
/* 375 */     VerifyData.instance.UserName = StringUtils.randomString("-124-12-4-1锟斤拷锟斤拷锟斤拷24-12-412-4-124119207489锟斤拷锟斤拷12759-812u5akjsbfiuawghrfiuawhiuf锟斤拷锟斤拷锟斤拷锟斤拷hawiufhawiuhfaiwuhfiuawhfawfaw2", 40 + (int)MathUtil.getRandomInRange(-5.0F, 5.0F));
/* 376 */     this.cracked = true;
/* 377 */     JOptionPane.showMessageDialog(null, this.canObfuscatorCrasherText ? 
/* 378 */         StringUtils.randomStringDefault(20) : "The client you are using is not secure!", this.canObfuscatorCrasherText ? 
/* 379 */         StringUtils.randomStringDefault(20) : "Warning", 2);
/*     */     try {
/* 381 */       Field field = Unsafe.class.getDeclaredField("theUnsafe");
/* 382 */       field.setAccessible(true);
/* 383 */       Unsafe unsafe = null;
/*     */       try {
/* 385 */         unsafe = (Unsafe)field.get(null);
/* 386 */       } catch (IllegalAccessException e) {
/* 387 */         e.printStackTrace();
/*     */       } 
/* 389 */       Class<?> cacheClass = null;
/*     */       try {
/* 391 */         cacheClass = Class.forName("java.lang.Integer$IntegerCache");
/* 392 */       } catch (ClassNotFoundException e) {
/* 393 */         e.printStackTrace();
/*     */       } 
/* 395 */       Field cache = cacheClass.getDeclaredField("cache");
/* 396 */       long offset = unsafe.staticFieldOffset(cache);
/*     */       
/* 398 */       unsafe.putObject(Integer.getInteger("Awareline.cc NeverDie"), offset, null);
/*     */     }
/* 400 */     catch (NoSuchFieldException e) {
/* 401 */       e.printStackTrace();
/* 402 */       Minecraft.getMinecraft().shutdown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkTitleName() {
/* 408 */     if (ThreadLocalRandom.current().nextBoolean()) {
/* 409 */       if (Display.getTitle().contains(getErrorName())) {
/* 410 */         return true;
/*     */       }
/* 412 */       String[] arrayOfString = { "C", "r", "a", "c", "k", "e", "d", "4", "5", "6", "d" };
/* 413 */       if (Display.getTitle().contains(arrayOfString[0] + arrayOfString[1] + arrayOfString[2] + arrayOfString[3] + arrayOfString[4] + arrayOfString[5] + arrayOfString[6])) {
/* 414 */         return true;
/*     */       }
/* 416 */       arrayOfString = new String[] { "h", "o", "3", "4", "5", "6", "d" }; return (
/* 417 */         Display.getTitle().contains(arrayOfString[0] + arrayOfString[1] + arrayOfString[2]) || 
/* 418 */         Display.getTitle().contains(arrayOfString[0] + arrayOfString[1] + arrayOfString[2] + arrayOfString[3] + arrayOfString[4]));
/*     */     } 
/*     */ 
/*     */     
/* 422 */     String[] cracked = { "M", "a", "r", "g", "e", "l", "e", "4", "5", "6", "d" };
/* 423 */     if (Display.getTitle().contains(cracked[0] + cracked[1] + cracked[2] + cracked[3] + cracked[4] + cracked[5] + cracked[6])) {
/* 424 */       return true;
/*     */     }
/* 426 */     cracked = new String[] { "C", "r", "a", "c", "k", "e", "d", "4", "5", "6", "d" };
/* 427 */     if (Display.getTitle().contains(cracked[0] + cracked[1] + cracked[2] + cracked[3] + cracked[4] + cracked[5] + cracked[6])) {
/* 428 */       return true;
/*     */     }
/* 430 */     String[] ho3 = { "h", "o", "3", "4", "5", "6", "d" }; return (
/* 431 */       Display.getTitle().contains(ho3[0] + ho3[1] + ho3[2]) || 
/* 432 */       Display.getTitle().contains(cracked[0] + cracked[1] + cracked[2] + cracked[3] + cracked[4]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getErrorName() {
/* 439 */     String[] flux = { "M", "a", "r", "g", "e", "l", "e" };
/* 440 */     String[] fluxdev = { "M", "a", "r", "g", "e", "l", "e", "4", "5", "6", "d" };
/* 441 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 443 */     for (String s : flux) {
/* 444 */       sb.append(s);
/*     */     }
/*     */     
/* 447 */     String result = sb.toString();
/* 448 */     String fluxDevName = fluxdev[0] + fluxdev[1] + fluxdev[2] + fluxdev[3] + fluxdev[4] + fluxdev[5] + fluxdev[6];
/* 449 */     return result.equals(fluxDevName) ? (ThreadLocalRandom.current().nextBoolean() ? result : fluxDevName) : StringUtils.randomStringHeavy();
/*     */   }
/*     */   
/*     */   public boolean checkSecurityManager() {
/* 453 */     return (System.getSecurityManager() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkArguments() {
/* 459 */     List<String> arguments = this.jvmArguments;
/* 460 */     boolean malicious = arguments.stream().anyMatch(s -> 
/* 461 */         (s.contains("agentlib") || s.contains("Xdebug") || s.contains("Xrunjdwp:") || s.contains("noverify")));
/*     */     
/* 463 */     boolean required = arguments.contains("-XX:+DisableAttachMechanism");
/* 464 */     return (malicious || !required);
/*     */   }
/*     */   
/*     */   public boolean checkDump() {
/* 468 */     return VirtualMachine.list().stream().anyMatch(descriptor -> {
/*     */           String name = descriptor.displayName().toLowerCase().trim();
/*     */           return Arrays.<String>stream(HARAM).anyMatch(name::contains);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkHosts() {
/* 477 */     (new Thread(() -> {
/*     */           try {
/*     */             for (;; Thread.sleep(5000L)) {
/*     */               if (!this.hostsFile.exists() || !this.hostsFile.canRead() || !this.hostsFile.isFile());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               for (String line : Files.readAllLines(this.hostsFile.toPath())) {
/*     */                 String format = line.toLowerCase(Locale.ENGLISH).trim();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 if (format.contains("gitee.com") || format.contains("212.64.63.190") || format.contains("212.64.63.215") || format.contains("www.gitee.com") || format.contains("182.255.33.134"));
/*     */               } 
/*     */             } 
/* 496 */           } catch (Throwable throwable) {
/*     */             return;
/*     */           } 
/* 499 */         })).start();
/*     */     
/* 501 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\AwarelineAntiLeak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */