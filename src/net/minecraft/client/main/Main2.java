/*     */ package net.minecraft.client.main;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import java.io.File;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.net.Authenticator;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.Proxy;
/*     */ import java.util.List;
/*     */ import javax.swing.JOptionPane;
/*     */ import joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import joptsimple.NonOptionArgumentSpec;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import joptsimple.OptionSpec;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.Session;
/*     */ 
/*     */ public class Main2
/*     */ {
/*     */   private static boolean noverify;
/*     */   public boolean junkCode;
/*     */   public boolean junkCode1;
/*     */   public boolean junkCode2;
/*     */   public boolean junkCode3;
/*     */   public boolean junkCode4;
/*     */   public boolean junkCode5;
/*     */   public boolean junkCode6;
/*     */   public boolean junkCode7;
/*     */   public boolean junkCode8;
/*     */   public boolean junkCode9;
/*     */   public boolean junkCode10;
/*     */   public boolean junkCode11;
/*     */   
/*     */   public static void main(String[] p_main_0_) {
/*  40 */     RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
/*  41 */     List<String> arguments = runtimeMxBean.getInputArguments();
/*  42 */     for (String str : arguments) {
/*  43 */       if (str.contains("Xverify:none")) {
/*  44 */         noverify = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  48 */     if (!noverify) {
/*  49 */       JOptionPane.showInputDialog(null, "Please add jvm parameters", "-noverify");
/*     */       
/*  51 */       Runtime.getRuntime().gc();
/*  52 */       Runtime.getRuntime().exit(-1);
/*     */       
/*     */       return;
/*     */     } 
/*  56 */     Thread.currentThread().setPriority(1);
/*     */     
/*  58 */     System.setProperty("java.net.preferIPv4Stack", "true");
/*  59 */     OptionParser optionparser = new OptionParser();
/*  60 */     optionparser.allowsUnrecognizedOptions();
/*  61 */     optionparser.accepts("demo");
/*  62 */     optionparser.accepts("fullscreen");
/*  63 */     optionparser.accepts("checkGlErrors");
/*  64 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec1 = optionparser.accepts("server").withRequiredArg();
/*  65 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec2 = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(25565), (Object[])new Integer[0]);
/*  66 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec3 = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), (Object[])new File[0]);
/*  67 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec4 = optionparser.accepts("assetsDir").withRequiredArg().ofType(File.class);
/*  68 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec5 = optionparser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
/*  69 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec6 = optionparser.accepts("proxyHost").withRequiredArg();
/*  70 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec7 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", (Object[])new String[0]).ofType(Integer.class);
/*  71 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec8 = optionparser.accepts("proxyUser").withRequiredArg();
/*  72 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec9 = optionparser.accepts("proxyPass").withRequiredArg();
/*  73 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec10 = optionparser.accepts("username").withRequiredArg().defaultsTo("GodLike457", (Object[])new String[0]);
/*  74 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec11 = optionparser.accepts("uuid").withRequiredArg();
/*  75 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec12 = optionparser.accepts("accessToken").withRequiredArg().required();
/*  76 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec13 = optionparser.accepts("version").withRequiredArg().required();
/*  77 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec14 = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(1920), (Object[])new Integer[0]);
/*  78 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec15 = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(1080), (Object[])new Integer[0]);
/*  79 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec16 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}", (Object[])new String[0]);
/*  80 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec17 = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}", (Object[])new String[0]);
/*  81 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec18 = optionparser.accepts("assetIndex").withRequiredArg();
/*  82 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec19 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy", (Object[])new String[0]);
/*  83 */     NonOptionArgumentSpec nonOptionArgumentSpec = optionparser.nonOptions();
/*  84 */     OptionSet optionset = optionparser.parse(p_main_0_);
/*  85 */     List<String> list = optionset.valuesOf((OptionSpec)nonOptionArgumentSpec);
/*  86 */     if (!list.isEmpty()) {
/*  87 */       System.out.println("Completely ignored arguments: " + list);
/*     */     }
/*     */     
/*  90 */     String s = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec6);
/*  91 */     Proxy proxy = Proxy.NO_PROXY;
/*     */     
/*  93 */     if (s != null) {
/*     */       try {
/*  95 */         proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(s, ((Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec7)).intValue()));
/*  96 */       } catch (Exception e) {
/*  97 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 101 */     final String s1 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec8);
/* 102 */     final String s2 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec9);
/*     */     
/*     */     try {
/* 105 */       if (!proxy.equals(Proxy.NO_PROXY) && isNullOrEmpty(s1) && isNullOrEmpty(s2)) {
/* 106 */         Authenticator.setDefault(new Authenticator() {
/*     */               protected PasswordAuthentication getPasswordAuthentication() {
/* 108 */                 return new PasswordAuthentication(s1, s2.toCharArray());
/*     */               }
/*     */             });
/*     */       }
/* 112 */     } catch (RuntimeException e) {
/* 113 */       e.printStackTrace();
/*     */     } 
/* 115 */     int i = ((Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec14)).intValue();
/* 116 */     int j = ((Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec15)).intValue();
/* 117 */     boolean flag = optionset.has("fullscreen");
/* 118 */     boolean flag1 = optionset.has("checkGlErrors");
/* 119 */     boolean flag2 = optionset.has("demo");
/* 120 */     String s3 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec13);
/* 121 */     Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create();
/* 122 */     PropertyMap propertymap = (PropertyMap)gson.fromJson((String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec16), PropertyMap.class);
/* 123 */     PropertyMap propertymap1 = (PropertyMap)gson.fromJson((String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec17), PropertyMap.class);
/* 124 */     File file1 = (File)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec3);
/* 125 */     File file2 = optionset.has((OptionSpec)argumentAcceptingOptionSpec4) ? (File)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec4) : new File(file1, "assets/");
/* 126 */     File file3 = optionset.has((OptionSpec)argumentAcceptingOptionSpec5) ? (File)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec5) : new File(file1, "resourcepacks/");
/* 127 */     String s4 = optionset.has((OptionSpec)argumentAcceptingOptionSpec11) ? (String)argumentAcceptingOptionSpec11.value(optionset) : (String)argumentAcceptingOptionSpec10.value(optionset);
/* 128 */     String s5 = optionset.has((OptionSpec)argumentAcceptingOptionSpec18) ? (String)argumentAcceptingOptionSpec18.value(optionset) : null;
/* 129 */     String s6 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec1);
/* 130 */     Integer integer = (Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec2);
/* 131 */     Session session = new Session((String)argumentAcceptingOptionSpec10.value(optionset), s4, (String)argumentAcceptingOptionSpec12.value(optionset), (String)argumentAcceptingOptionSpec19.value(optionset));
/* 132 */     GameConfiguration gameconfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertymap, propertymap1, proxy), new GameConfiguration.DisplayInformation(i, j, flag, flag1), new GameConfiguration.FolderInformation(file1, file3, file2, s5), new GameConfiguration.GameInformation(flag2, s3), new GameConfiguration.ServerInformation(s6, integer.intValue()));
/* 133 */     Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
/*     */           public void run() {
/* 135 */             Minecraft.stopIntegratedServer();
/*     */           }
/*     */         });
/* 138 */     Thread.currentThread().setName("Client thread");
/* 139 */     (new Minecraft(gameconfiguration)).run();
/*     */   }
/*     */   
/*     */   private static boolean isNullOrEmpty(String str) {
/* 143 */     return (str != null && !str.isEmpty());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 147 */     return "Main{junkCode=" + this.junkCode + ", junkCode1=" + this.junkCode1 + ", junkCode2=" + this.junkCode2 + ", junkCode3=" + this.junkCode3 + ", junkCode4=" + this.junkCode4 + ", junkCode5=" + this.junkCode5 + ", junkCode6=" + this.junkCode6 + ", junkCode7=" + this.junkCode7 + ", junkCode8=" + this.junkCode8 + ", junkCode9=" + this.junkCode9 + ", junkCode10=" + this.junkCode10 + ", junkCode11=" + this.junkCode11 + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\main\Main2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */