/*     */ package awareline.main.mod.manager;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.ui.gui.altmanager.oldaltmanager.Alt;
/*     */ import awareline.main.ui.gui.altmanager.oldaltmanager.AltManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class FileManager {
/*     */   static {
/*  18 */     File mcDataDir = (Minecraft.getMinecraft()).mcDataDir;
/*  19 */     Client.instance.getClass(); dir = new File(mcDataDir, "Awareline");
/*  20 */   } private static final File ALT = getConfigFile("Alts"); public static final File dir;
/*  21 */   private static final File LASTALT = getConfigFile("LastAlt");
/*     */ 
/*     */   
/*     */   public static void saveLastAlt() {
/*     */     try {
/*  26 */       PrintWriter printWriter = new PrintWriter(LASTALT);
/*  27 */       Alt alt = AltManager.getLastAlt();
/*  28 */       if (alt != null) {
/*  29 */         if (alt.getMask().isEmpty()) {
/*  30 */           printWriter.println(alt.getUsername() + ":" + alt.getPassword());
/*     */         } else {
/*  32 */           printWriter.println(alt.getMask() + "    " + alt.getUsername() + ":" + alt.getPassword());
/*     */         } 
/*     */       }
/*  35 */       printWriter.close();
/*  36 */     } catch (FileNotFoundException e) {
/*  37 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void saveAlts() {
/*     */     try {
/*  43 */       PrintWriter printWriter = new PrintWriter(ALT);
/*  44 */       for (Alt alt : AltManager.getAlts()) {
/*  45 */         if (alt.getMask().isEmpty()) {
/*  46 */           printWriter.println(alt.getUsername() + ":" + alt.getPassword()); continue;
/*     */         } 
/*  48 */         printWriter.println(alt.getMask() + "    " + alt.getUsername() + ":" + alt.getPassword());
/*     */       } 
/*     */       
/*  51 */       printWriter.close();
/*  52 */     } catch (FileNotFoundException e) {
/*  53 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static File getConfigFile(String name) {
/*  58 */     File file = new File(dir, String.format("%s.txt", new Object[] { name }));
/*  59 */     if (!file.exists()) {
/*     */       try {
/*  61 */         file.createNewFile();
/*  62 */       } catch (IOException e) {
/*  63 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  66 */     return file;
/*     */   }
/*     */   
/*     */   public static void init() {
/*  70 */     if (!dir.exists()) {
/*  71 */       dir.mkdir();
/*     */     }
/*     */   }
/*     */   
/*     */   public static List<String> read(String file) {
/*  76 */     List<String> out = new ArrayList<>();
/*     */     try {
/*  78 */       if (!dir.exists()) {
/*  79 */         dir.mkdir();
/*     */       }
/*  81 */       File f = new File(dir, file);
/*  82 */       if (!f.exists()) {
/*  83 */         f.createNewFile();
/*     */       }
/*  85 */       try (FileInputStream fis = new FileInputStream(f)) {
/*  86 */         try(InputStreamReader isr = new InputStreamReader(fis); 
/*  87 */             BufferedReader br = new BufferedReader(isr)) {
/*     */           String line;
/*  89 */           while ((line = br.readLine()) != null) {
/*  90 */             out.add(line);
/*     */           }
/*     */         } 
/*     */         
/*  94 */         fis.close();
/*  95 */         return out;
/*     */       } 
/*  97 */     } catch (IOException e) {
/*  98 */       e.printStackTrace();
/*     */       
/* 100 */       return out;
/*     */     } 
/*     */   }
/*     */   public static void save(String file, String content, boolean append) {
/*     */     try {
/* 105 */       File f = new File(dir, file);
/* 106 */       if (!f.exists()) {
/* 107 */         f.createNewFile();
/*     */       }
/* 109 */       try (FileWriter writer = new FileWriter(f, append)) {
/* 110 */         writer.write(content);
/*     */       } 
/* 112 */     } catch (IOException e) {
/* 113 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<String> read(File dir, String file) {
/* 118 */     List<String> out = new ArrayList<>();
/*     */     try {
/* 120 */       if (!dir.exists()) {
/* 121 */         return null;
/*     */       }
/* 123 */       File f = new File(FileManager.dir, file);
/* 124 */       if (!f.exists()) {
/* 125 */         f.createNewFile();
/*     */       }
/*     */       
/* 128 */       try (FileInputStream fis = new FileInputStream(f)) {
/* 129 */         try(InputStreamReader isr = new InputStreamReader(fis); 
/* 130 */             BufferedReader br = new BufferedReader(isr)) {
/*     */           String line;
/*     */           
/* 133 */           while ((line = br.readLine()) != null) {
/* 134 */             out.add(line);
/*     */           }
/*     */         } finally {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 141 */         fis.close();
/* 142 */         return out;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       finally {}
/*     */ 
/*     */     
/*     */     }
/* 151 */     catch (IOException e) {
/* 152 */       e.printStackTrace();
/*     */       
/* 154 */       return out;
/*     */     } 
/*     */   }
/*     */   public static void save(File dir, String file, String content, boolean append) {
/*     */     try {
/* 159 */       File f = new File(dir, file);
/* 160 */       if (!f.exists()) {
/* 161 */         f.createNewFile();
/*     */       }
/*     */       
/* 164 */       try (FileWriter writer = new FileWriter(f, append)) {
/* 165 */         writer.write(content);
/*     */       
/*     */       }
/*     */       finally {}
/*     */     
/*     */     }
/* 171 */     catch (IOException e) {
/* 172 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\manager\FileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */