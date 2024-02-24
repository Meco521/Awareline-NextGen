/*     */ package com.me.guichaguri.betterfps;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.nio.file.Files;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BetterFpsHelper
/*     */ {
/*     */   public static final String MC_VERSION = "1.8.9";
/*     */   public static final String VERSION = "1.2.1";
/*  21 */   public static final LinkedHashMap<String, String> helpers = new LinkedHashMap<>();
/*     */ 
/*     */   
/*  24 */   public static final LinkedHashMap<String, String> displayHelpers = new LinkedHashMap<>();
/*     */   
/*     */   static {
/*  27 */     helpers.put("vanilla", "VanillaMath");
/*  28 */     helpers.put("rivens", "RivensMath");
/*  29 */     helpers.put("taylors", "TaylorMath");
/*  30 */     helpers.put("libgdx", "LibGDXMath");
/*  31 */     helpers.put("rivens-full", "RivensFullMath");
/*  32 */     helpers.put("rivens-half", "RivensHalfMath");
/*  33 */     helpers.put("java", "JavaMath");
/*  34 */     helpers.put("random", "RandomMath");
/*     */     
/*  36 */     displayHelpers.put("vanilla", "Vanilla Algorithm");
/*  37 */     displayHelpers.put("rivens", "Riven's Algorithm");
/*  38 */     displayHelpers.put("taylors", "Taylor's Algorithm");
/*  39 */     displayHelpers.put("libgdx", "LibGDX's Algorithm");
/*  40 */     displayHelpers.put("rivens-full", "Riven's \"Full\" Algorithm");
/*  41 */     displayHelpers.put("rivens-half", "Riven's \"Half\" Algorithm");
/*  42 */     displayHelpers.put("java", "Java Math");
/*  43 */     displayHelpers.put("random", "Random Math");
/*     */   }
/*     */   
/*     */   public static File LOC;
/*  47 */   public static File MCDIR = null;
/*  48 */   private static File CONFIG_FILE = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {}
/*     */ 
/*     */   
/*     */   public static BetterFpsConfig loadConfig() {
/*  56 */     if (MCDIR == null) {
/*  57 */       CONFIG_FILE = new File("config" + File.pathSeparator + "betterfps.json");
/*     */     } else {
/*  59 */       CONFIG_FILE = new File(MCDIR, "config" + File.pathSeparator + "betterfps.json");
/*     */     } 
/*     */     
/*     */     try {
/*  63 */       if (CONFIG_FILE.exists()) {
/*  64 */         Gson gson = new Gson();
/*  65 */         BetterFpsConfig.instance = (BetterFpsConfig)gson.fromJson(new FileReader(CONFIG_FILE), BetterFpsConfig.class);
/*     */       } else {
/*  67 */         BetterFpsConfig.instance = new BetterFpsConfig();
/*     */       } 
/*  69 */     } catch (Exception ex) {
/*  70 */       ex.printStackTrace();
/*     */     } 
/*     */     
/*     */     try {
/*     */       File oldConfigFile;
/*  75 */       Properties prop = new Properties();
/*     */       
/*  77 */       if (MCDIR == null) {
/*  78 */         oldConfigFile = new File("betterfps.txt");
/*     */       } else {
/*  80 */         oldConfigFile = new File(MCDIR, "betterfps.txt");
/*     */       } 
/*  82 */       if (oldConfigFile.exists() && !CONFIG_FILE.exists()) {
/*  83 */         prop.load(Files.newInputStream(oldConfigFile.toPath(), new java.nio.file.OpenOption[0]));
/*  84 */         BetterFpsConfig.instance.algorithm = prop.getProperty("algorithm", "rivens-half");
/*     */       } 
/*  86 */     } catch (Exception ex) {
/*  87 */       System.err.println("Could not import the old config format");
/*     */     } 
/*     */ 
/*     */     
/*  91 */     saveConfig();
/*     */     
/*  93 */     return BetterFpsConfig.instance;
/*     */   }
/*     */   public static void saveConfig() {
/*     */     try {
/*  97 */       if (!CONFIG_FILE.exists()) CONFIG_FILE.createNewFile(); 
/*  98 */       Gson gson = new Gson();
/*  99 */       FileUtils.writeStringToFile(CONFIG_FILE, gson.toJson(BetterFpsConfig.instance));
/* 100 */     } catch (Exception ex) {
/* 101 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\BetterFpsHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */