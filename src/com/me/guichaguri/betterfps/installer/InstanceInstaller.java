/*     */ package com.me.guichaguri.betterfps.installer;
/*     */ import com.me.guichaguri.betterfps.installer.json.JSONArray;
/*     */ import com.me.guichaguri.betterfps.installer.json.JSONObject;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class InstanceInstaller {
/*     */   private static final String LIBRARY_IDENTIFIER = "betterfps";
/*     */   private static final String LIBRARY_NAME = "BetterFps";
/*     */   private static final String VERSION_NAME = "1.2.1";
/*     */   private static final String TWEAKER = "com.com.me.guichaguri.betterfps.tweaker.BetterFpsTweaker";
/*  22 */   private static final String[] LIBRARIES_NAMES = new String[] { "org.ow2.asm:asm-all:5.0.3", "net.minecraft:launchwrapper:1.11" }; private final File mcFolder; private final File versionsFolder;
/*     */   private final String version;
/*     */   private final File oldVersionFolder;
/*     */   
/*     */   public static List<String> getVersions(File mcFolder) {
/*  27 */     File versionFolder = new File(mcFolder, "versions");
/*  28 */     if (!versionFolder.exists() || !versionFolder.isDirectory()) {
/*  29 */       return null;
/*     */     }
/*  31 */     List<String> versions = new ArrayList<>();
/*  32 */     for (File f : versionFolder.listFiles()) {
/*  33 */       if (f.isDirectory() && 
/*  34 */         f.getName().startsWith("1.8.9"))
/*  35 */         versions.add(f.getName()); 
/*     */     } 
/*  37 */     return versions;
/*     */   }
/*     */   
/*     */   public static void install(File mcFolder, String version) throws Exception {
/*  41 */     InstanceInstaller installer = new InstanceInstaller(mcFolder, version);
/*  42 */     installer.setupJson();
/*  43 */     installer.copyLibrary();
/*  44 */     installer.saveNewVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public static File getSuggestedMinecraftFolder() {
/*  49 */     String userHomeDir = System.getProperty("user.home", ".");
/*  50 */     String osType = System.getProperty("os.name").toLowerCase();
/*  51 */     if (osType.contains("win") && System.getenv("APPDATA") != null)
/*  52 */       return new File(System.getenv("APPDATA"), ".minecraft"); 
/*  53 */     if (osType.contains("mac")) {
/*  54 */       return new File(userHomeDir, "Library/Application Support/minecraft");
/*     */     }
/*  56 */     return new File(userHomeDir, ".minecraft");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private File versionFolder = null;
/*     */   private final JSONObject versionJson;
/*     */   
/*     */   private InstanceInstaller(File mcFolder, String version) throws Exception {
/*  68 */     this.mcFolder = mcFolder;
/*  69 */     this.version = version;
/*  70 */     this.versionsFolder = new File(mcFolder, "versions");
/*  71 */     this.oldVersionFolder = new File(this.versionsFolder, version);
/*     */     
/*  73 */     File versionJsonFile = new File(this.oldVersionFolder, version + ".json");
/*  74 */     if (!versionJsonFile.exists()) {
/*  75 */       throw new FileNotFoundException();
/*     */     }
/*  77 */     BufferedReader br = new BufferedReader(new FileReader(versionJsonFile));
/*     */     
/*  79 */     StringBuilder json = new StringBuilder(); String line;
/*  80 */     while ((line = br.readLine()) != null) {
/*  81 */       json.append(line);
/*     */     }
/*  83 */     br.close();
/*  84 */     this.versionJson = new JSONObject(json.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupJson() {
/*  89 */     JSONArray libraries = this.versionJson.getJSONArray("libraries");
/*  90 */     JSONArray newArray = new JSONArray();
/*     */     
/*  92 */     JSONObject betterfpsLib = new JSONObject();
/*  93 */     betterfpsLib.put("name", "betterfps:BetterFps:1.2.1");
/*  94 */     newArray.put(betterfpsLib);
/*     */     
/*  96 */     String[] libNames = new String[LIBRARIES_NAMES.length];
/*  97 */     int i = 0;
/*  98 */     for (String name : LIBRARIES_NAMES) {
/*  99 */       JSONObject lib = new JSONObject();
/* 100 */       lib.put("name", name);
/* 101 */       newArray.put(lib);
/* 102 */       libNames[i] = name.split(":")[1];
/* 103 */       i++;
/*     */     } 
/*     */ 
/*     */     
/* 107 */     for (i = 0; i < libraries.length(); i++) {
/* 108 */       JSONObject o = libraries.getJSONObject(i);
/* 109 */       String name = o.getString("name").split(":")[1];
/* 110 */       String[] arrayOfString = libNames; int j = arrayOfString.length; byte b = 0; while (true) { if (b < j) { String ln = arrayOfString[b];
/* 111 */           if (name.equals(ln))
/*     */             break;  b++; continue; }
/* 113 */          newArray.put(o); break; }
/*     */     
/*     */     } 
/* 116 */     this.versionJson.put("libraries", newArray);
/*     */     
/* 118 */     this.versionJson.put("mainClass", "net.minecraft.launchwrapper.Launch");
/* 119 */     String jar = this.versionJson.has("jar") ? this.versionJson.getString("jar") : this.version;
/* 120 */     this.versionJson.put("jar", jar);
/* 121 */     String arguments = this.versionJson.getString("minecraftArguments");
/* 122 */     arguments = arguments + " --tweakClass com.com.me.guichaguri.betterfps.tweaker.BetterFpsTweaker";
/* 123 */     this.versionJson.put("minecraftArguments", arguments);
/*     */   }
/*     */   
/*     */   public void copyLibrary() throws Exception {
/* 127 */     URL modFile = BetterFpsInstaller.class.getProtectionDomain().getCodeSource().getLocation();
/* 128 */     File libraries = new File(this.mcFolder, "libraries");
/* 129 */     File libraryDir = new File(libraries, "betterfps/BetterFps/1.2.1");
/* 130 */     libraryDir.mkdirs();
/* 131 */     File library = new File(libraryDir, "BetterFps-1.2.1.jar");
/* 132 */     InputStream is = modFile.openStream();
/* 133 */     OutputStream os = Files.newOutputStream(library.toPath(), new java.nio.file.OpenOption[0]);
/* 134 */     byte[] buffer = new byte[1024];
/*     */     int length;
/* 136 */     while ((length = is.read(buffer)) > 0) {
/* 137 */       os.write(buffer, 0, length);
/*     */     }
/* 139 */     is.close();
/* 140 */     os.close();
/*     */   }
/*     */   
/*     */   public void saveNewVersion() throws Exception {
/* 144 */     String versionName = this.version + "-BetterFps-" + "1.2.1";
/* 145 */     this.versionJson.put("id", versionName);
/* 146 */     this.versionFolder = new File(this.versionsFolder, versionName);
/* 147 */     this.versionFolder.mkdirs();
/* 148 */     File json = new File(this.versionFolder, versionName + ".json");
/* 149 */     BufferedWriter bw = new BufferedWriter(new FileWriter(json));
/* 150 */     bw.write(this.versionJson.toString());
/* 151 */     bw.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\installer\InstanceInstaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */